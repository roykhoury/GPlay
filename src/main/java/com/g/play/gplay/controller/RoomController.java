package com.g.play.gplay.controller;

import com.g.play.gplay.model.Room;
import com.g.play.gplay.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * Endpoint to create a new room and save it to the database
     *
     * @param name The name of the room
     * @return The response entity with appropriate message and Http status
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Room> createRoom(@RequestBody String name) {
        try {
            Room newRoom = roomService.createRoom(name);
            return new ResponseEntity<>(newRoom, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to end the current user's room and delete it from the database
     *
     * @return The response entity with appropriate message and Http status
     */
    @RequestMapping(value = "/end", method = RequestMethod.POST)
    public ResponseEntity<Boolean> endRoom() {
        try {
            return new ResponseEntity<>(roomService.endRoom(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Endpoint to join room for a logged in user
     *
     * @param id the id of the room
     * @return The response entity with appropriate message and Http status
     */
    @RequestMapping(value = "/join/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Room> joinRoom(@PathVariable("id") @NotNull Long id) {
        try {
            Room room = roomService.joinRoom(id);
            if (room != null) {
                return new ResponseEntity<>(room, HttpStatus.OK);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to leave room for a logged in user
     *
     * @param id the id of the room
     * @return The response entity with appropriate message and Http status
     */
    @RequestMapping(value = "/leave/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> leaveRoom(@PathVariable("id") @NotNull Long id) {
        try {
            if (roomService.leaveRoom(id)) {
                return new ResponseEntity<>("Successfully left!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to leave room!", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to leave room, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Room>> search(@RequestBody @NotNull String keyword) {
        try {
            return new ResponseEntity<>(roomService.getRooms(keyword), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}

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
    public ResponseEntity<String> createRoom(@RequestBody String name) {
        try {
            Room newRoom = roomService.createRoom(name);
            if (newRoom != null) {
                return new ResponseEntity<>("Successfully created room " + newRoom.getName(), HttpStatus.OK);
            } else {
                throw new NullPointerException("Could not create room!");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating room, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to end the current user's room and delete it from the database
     *
     * @return The response entity with appropriate message and Http status
     */
    @RequestMapping(value = "/end", method = RequestMethod.POST)
    public ResponseEntity<String> endRoom() {
        try {
            if (roomService.endRoom()) {
                return new ResponseEntity<>("Successfully ended room!", HttpStatus.OK);
            } else {
                throw new NullPointerException("Could not end room!");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error while ending room, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Endpoint to join room for a logged in user
     *
     * @param id the id of the room
     * @return The response entity with appropriate message and Http status
     */
    @RequestMapping(value = "/join/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> joinRoom(@PathVariable("id") @NotNull Long id) {
        try {
            if (roomService.joinRoom(id)) {
                return new ResponseEntity<>("Successfully joined!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to join room!", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to join room, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
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

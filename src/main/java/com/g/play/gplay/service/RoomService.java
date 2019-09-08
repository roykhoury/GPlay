package com.g.play.gplay.service;

import com.g.play.gplay.model.Room;
import com.g.play.gplay.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is the service that provides all needed room functionality
 * including parsing, filtering and sorting of the data
 */
@Service
public class RoomService {

    private Room currentRoom;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    /**
     * Get currently hosted room
     *
     * @return the Room object
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Create a room and store it in the mongoDB instance
     *
     * @param name the name of the room
     * @return the Room created
     */
    public Room createRoom(String name) {
        if (userService.getLoggedInUser() == null) {
            return null;
        }
        currentRoom = new Room()
                .withId(sequenceGeneratorService.generateSequence(Room.SEQUENCE_NAME))
                .withHost(userService.getLoggedInUser())
                .withMemberIds(new ArrayList<>())
                .withName(name.toLowerCase().trim());

        return roomRepository.save(currentRoom);
    }

    /**
     * End a room and stop the session
     *
     * @return true if room is successfully ended
     */
    public boolean endRoom() {
        Room currentRoom = roomRepository.findRoomByHost(this.userService.getLoggedInUser());
        if (currentRoom != null) {
            roomRepository.deleteById(currentRoom.getId());
            return true;
        }
        return false;
    }

    /**
     * This method updates the room object to include
     * the currently logged user in the room's members list
     *
     * @param id the room ID
     * @return true if room was successfully updated
     */
    public Room joinRoom(long id) throws Exception {
        if (userService.getLoggedInUser() == null) {
            throw new Exception();
        }

        /*
          Make sure that the room exists,
          Make sure that the user is not already in that room and finally
          Make sure that the user is not trying to join his/her own room
         */
        Optional<Room> roomToJoin = roomRepository.findById(id);
        if (roomToJoin.isPresent() && !alreadyJoined(roomToJoin.get()) && userService.getLoggedInUser().getId() != roomToJoin.get().getHost().getId()) {
            roomRepository.save(roomToJoin.get().addMember(this.userService.getLoggedInUser().getId()));
            return roomToJoin.orElse(null);
        }
        return null;
    }

    /**
     * This method updates the room object to exclude
     * the currently logged user from the room's members list
     *
     * @param id the room ID
     * @return true if room was successfully updated
     */
    public boolean leaveRoom(long id) {
        if (userService.getLoggedInUser() == null) {
            return false;
        }

        Optional<Room> roomToJoin = roomRepository.findById(id);
        if (roomToJoin.isPresent() && alreadyJoined(roomToJoin.get())) {
            roomRepository.save(
                    roomToJoin.get().removeMember(this.userService.getLoggedInUser().getId())
            );
            return true;
        }
        return false;
    }

    /**
     * Check if logged in user is already in a room
     *
     * @param room the room to checkl
     * @return true if user is already in room
     */
    private boolean alreadyJoined(Room room) {
        for (long id : room.getMemberIds()) {
            if (userService.getLoggedInUser().getId() == id) {
                return true;
            }
        }
        return false;
    }

    public List<Room> getRooms(String keyword) {
        return roomRepository.findRoomsByNameLike(keyword.toLowerCase().trim());
    }
}

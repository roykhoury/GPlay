package com.g.play.gplay.repository;

import com.g.play.gplay.model.Room;
import com.g.play.gplay.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This interface is used to communicate with the MongoDB instance and
 * retrieve physical copies of the rooms stored
 */
public interface RoomRepository extends MongoRepository<Room, Long> {

    /**
     * Find rooms
     *
     * @param name the name of the room
     * @return the List of rooms
     */
    List<Room> findRoomsByNameLike(String name);

    /**
     * Find a room
     *
     * @param host the host user
     * @return the Room
     */
    Room findRoomByHost(User host);
}

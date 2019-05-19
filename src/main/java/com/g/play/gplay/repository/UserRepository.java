package com.g.play.gplay.repository;

import com.g.play.gplay.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * This interface is used to communicate with the MongoDB instance and
 * retrieve physical copies of the user information stored
 */
public interface UserRepository extends MongoRepository<User, Long> {

    /**
     * Find a user
     *
     * @param username the user username
     * @return the User object
     */
    User findUserByUsername(String username);

    /**
     * Find a user
     *
     * @param email the user email
     * @return the User object
     */
    User findUserByEmail(String email);
}

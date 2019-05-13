package com.g.play.gplay.repository;

import com.g.play.gplay.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * This interface is used to communicate with the MongoDB instance and
 * retrieve physical copies of the user information stored
 */
public interface UserRepository extends MongoRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserByEmail(String username);
}

package com.g.play.gplay.controller;

import com.g.play.gplay.model.PlayList;
import com.g.play.gplay.model.User;
import com.g.play.gplay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * List of endpoints related to users
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint to register as a new user for the application
     *
     * @param user the user object containing all user information
     * @return the Response Entity feedback message and the Http status code
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            User newUser = userService.register(user.getUsername(), user.getPassword(), user.getEmail());
            return new ResponseEntity<>("Successfully created user: " + newUser.getUsername(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating user, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to login as a user and have access to the application content
     *
     * @param user the user information (user/pass)
     * @return the Response Entity feedback message and the Http status code
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user) {
        try {
            User newUser = userService.login(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to Logout of the application to close the sesscion
     *
     * @return the Response Entity feedback message and the Http status code
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout() {
        try {
            String username = userService.logout();
            return new ResponseEntity<>("Successfully logged out, See you next time " + username + "!", HttpStatus.OK);
        } catch (NullPointerException ex) {
            return new ResponseEntity<>("Cannot logout if not logged in dummy!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while logging out, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to display the user profile information
     *
     * @param userId the user ID that belongs to the user object in the database
     * @return the Response Entity feedback message and the Http status code
     */
    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> profile(@PathVariable("userId") long userId) {
        return new ResponseEntity<>(userService.getProfile(userId), HttpStatus.OK);
    }

    /**
     * Endpoint to add a friend in the logged in user's friend list
     *
     * @param friendId the user friend ID to add
     * @return the Response Entity feedback message and the Http status code
     */
    @RequestMapping(value = "/friend/add/{friendId}", method = RequestMethod.PUT)
    public ResponseEntity<String> addFriend(@PathVariable("friendId") long friendId) {
        try {
            return new ResponseEntity<>("Successfully added friend: " + userService.addFriend(friendId).getUsername(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("User id: " + friendId + " was not added as a friend!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to remove a friend from the logged in user's friends list
     *
     * @param friendId the friend ID to remove
     * @return the Response Entity feedback message and the Http status code
     */
    @RequestMapping(value = "/friend/remove/{friendId}", method = RequestMethod.PUT)
    public ResponseEntity<String> removeFriend(@PathVariable("friendId") long friendId) {
        try {
            return new ResponseEntity<>("Successfully removed friend: " + userService.removeFriend(friendId).getUsername(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("User id: " + friendId + " was not removed as a friend!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to create a new play list for the logged in user
     *
     * @param playList the playlist object to add to the user
     * @return the Response Entity feedback message and the Http status code
     */
    @RequestMapping(value = "/createPlayList", method = RequestMethod.PUT)
    public ResponseEntity<String> createPlayList(@RequestBody() PlayList playList) {
        try {
            if (userService.createPlayList(playList) != null) {
                return new ResponseEntity<>("Successfully created playlist " + playList.getName(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Playlist already exists!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating playlist, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a user playlist
     *
     * @param playListName the name of the playlist to delete
     * @return the updated user object
     */
    @RequestMapping(value = "/deletePlayList", method = RequestMethod.PUT)
    public ResponseEntity<String> deletePlayList(@RequestBody() String playListName) {
        try {
            if (userService.deletePlayList(playListName) != null) {
                return new ResponseEntity<>("Successfully deleted playlist " + playListName, HttpStatus.OK);
            }
            return new ResponseEntity<>("Playlist does not exist!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating playlist, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to retrieve friends list for a logged in user
     *
     * @return the List of User objects that are friends with logged in user
     */
    @RequestMapping(value = "/friend/all", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getFriendsList() {
        try {
            return new ResponseEntity<>(userService.getFriendsList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}


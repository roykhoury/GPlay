package com.g.play.gplay.service;

import com.g.play.gplay.model.PlayList;
import com.g.play.gplay.model.User;
import com.g.play.gplay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This service is used to bridge the interactions between the endpoints and the repositories
 * This handles all of the parsing and transformations needed to prepare communications with the database.
 */
@Service
public class UserService {

    private User loggedInUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private RoomService roomService;

    /**
     * Get the logged in user
     *
     * @return the User object
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Register a new User in the system
     *
     * @param username the user name
     * @param password the user password
     * @param email    the user email address
     * @return the successfully created account (User object)
     */
    public User register(String username, String password, String email) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User()
                .withId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME))
                .withUsername(username)
                .withPassword(bCryptPasswordEncoder.encode(password))
                .withEmail(email)
                .withPlaylists(new ArrayList<>())
                .withFriendsList(new ArrayList<>());

        return userRepository.save(user);
    }

    /**
     * Log in and start a session for a certain user
     *
     * @param username the user name
     * @param password the user password
     * @return the logged in user object
     */
    public User login(String username, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findUserByUsername(username);
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            this.loggedInUser = user;
            return user;
        }
        return null;
    }

    /**
     * Logout and close a user session
     *
     * @return The user name
     */
    public String logout() {
        final String username = this.loggedInUser.getUsername();
        this.loggedInUser = null;
        return username;
    }

    /**
     * Get profile information of a user
     *
     * @param userId the user ID
     * @return the User object
     */
    public User getProfile(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    /**
     * Add a user in an other user's friend list
     * Verifies that friend is not current user, friend exists and is in not already in the user's list
     * When a user is added into the friend's list,
     * both users are updated to add each other in their respective friend's lists
     *
     * @param friendId the user ID of the friend to add
     * @return the added friend object
     */
    public User addFriend(long friendId) {
        Optional<User> friend = userRepository.findById(friendId);
        if (friend.isPresent() && !this.loggedInUser.getFriendIdsList().contains(friendId) && friendId != this.loggedInUser.getId()) {
            this.loggedInUser.addFriendId(friendId);
            friend.get().addFriendId(this.loggedInUser.getId());

            userRepository.save(this.loggedInUser);
            userRepository.save(friend.get());

            return friend.get();
        }
        return null;
    }

    /**
     * Remove a user in an other user's friend list
     * Verifies that friend is not current user, friend exists and is in the user's list
     * When a user is removed from the friend's list,
     * both users are updated to remove each other in their respective friend's lists
     *
     * @param friendId the user ID of the friend to remove
     * @return the removed friend object
     */
    public User removeFriend(long friendId) {
        Optional<User> friend = userRepository.findById(friendId);
        if (friend.isPresent() && this.loggedInUser.getFriendIdsList().contains(friendId) && friendId != this.loggedInUser.getId()) {
            this.loggedInUser.removeFriendId(friendId);
            friend.get().removeFriendId(this.loggedInUser.getId());

            userRepository.save(friend.get());
            userRepository.save(this.loggedInUser);

            return friend.get();
        }
        return null;
    }

    /**
     * Create a playlist to the user's playlists
     * Verifies that a playlist with the same name does not already exists
     *
     * @param playList Playlist to create
     * @return the updated User object
     */
    public User createPlayList(PlayList playList) {
        for (PlayList list : this.loggedInUser.getPlayLists()) {
            if (list.getName().toLowerCase().trim().equals(playList.getName().toLowerCase().trim())) {
                return null;
            }
        }

        this.loggedInUser.addToPlayLists(playList);
        userRepository.save(this.loggedInUser);
        return this.loggedInUser;
    }

    /**
     * Delete a playlist to the user's playlists
     * Verifies that a playlist with the same name already exists
     *
     * @param playListName the name of the playlist to delete
     * @return the updated User object
     */
    public User deletePlayList(String playListName) {
        for (PlayList list : this.loggedInUser.getPlayLists()) {
            if (list.getName().toLowerCase().trim().equals(playListName.toLowerCase().trim())) {
                this.loggedInUser.removeFromPlayLists(playListName);
                userRepository.save(this.loggedInUser);
                return this.loggedInUser;
            }
        }
        return null;
    }

    /**
     * Parse the list of friend IDs into the list of User objects using the user repository
     *
     * @return the list of users
     */
    public List<User> getFriendsList() {
        List<User> friends = new ArrayList<>();
        for (long id : this.loggedInUser.getFriendIdsList()) {
            Optional<User> friend = userRepository.findById(id);
            friend.ifPresent(friends::add);
        }
        return friends;
    }
}

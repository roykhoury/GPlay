package com.g.play.gplay.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * This class is used as a collection in the mongoDB database
 * The collection stores all information about users
 * This also contains information about their play lists, friends and their songs
 */
@Document(collection = "user")
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;

    @Indexed(unique = true)
    private String username;

    private String password;

    @Indexed(unique = true)
    private String email;

    private List friendIdsList;

    private List<PlayList> playLists;

    public long getId() {
        return id;
    }

    public User withId(long id) {
        this.id = id;
        return this;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List getFriendIdsList() {
        return friendIdsList;
    }

    public User withFriendsList(List friendIdsList) {
        this.friendIdsList = friendIdsList;
        return this;
    }

    public User addFriendId(long friendId) {
        this.friendIdsList.add(friendId);
        return this;
    }

    public User removeFriendId(long friendId) {
        this.friendIdsList.remove(friendId);
        return this;
    }

    public void setFriendIdsList(List friendIdsList) {
        this.friendIdsList = friendIdsList;
    }

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public User withPlaylists(List<PlayList> playlists) {
        this.playLists = playlists;
        return this;
    }

    public User addToPlayLists(PlayList playList) {
        this.playLists.add(playList);
        return this;
    }

    public User removeFromPlayLists(String playListName) {
        final PlayList[] listToRemove = {null};
        this.playLists.forEach(list -> {
            if (list.getName().toLowerCase().trim().equals(playListName.toLowerCase().trim())) {
                listToRemove[0] = list;
            }
        });

        this.playLists.remove(listToRemove[0]);
        return this;
    }

    public void setPlayLists(List<PlayList> playLists) {
        this.playLists = playLists;
    }
}

package com.g.play.gplay.model;

import java.util.List;

/**
 * This is a class to hold playlist information locally
 * after retrieving it from the user's collection in MongoDB database
 */
public class PlayList {

    private String name;
    private List<Song> songs;

    public String getName() {
        return name;
    }

    public PlayList withName(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public PlayList withSongs(List<Song> songs) {
        this.songs = songs;
        return this;
    }

    public PlayList addSong(Song song) {
        this.songs.add(song);
        return this;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}

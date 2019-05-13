package com.g.play.gplay.model;

/**
 * This class is used to hold information retrieve the songs file names and IDs
 * originating from the user's collection
 */
public class Song {

    private String objectId;
    private String name;

    public String getObjectId() {
        return objectId;
    }

    public Song withObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public Song withName(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
}

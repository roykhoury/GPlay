package com.g.play.gplay.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Room class is used to represent live stream rooms
 * Rooms can have members and a host user that has his playlist playing and streamed to the other members
 */
@Document(collection = "room")
public class Room {

    @Transient
    public static final String SEQUENCE_NAME = "rooms_sequence";

    @Id
    private long id;

    @Indexed
    private String name;

    @Indexed(unique = true)
    private User host;

    @Indexed(sparse = true)
    private List<Long> memberIds;

    public long getId() {
        return id;
    }

    public Room withId(long id) {
        this.id = id;
        return this;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Room withName(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getHost() {
        return host;
    }

    public Room withHost(User host) {
        this.host = host;
        return this;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public Room withMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
        return this;
    }

    public Room addMember(long memberId) {
        this.memberIds.add(memberId);
        return this;
    }

    public Room removeMember(long memberId) {
        this.memberIds.remove(memberId);
        return this;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}

package com.g.play.gplay.service.Pattern.Observer;

import java.io.IOException;

/**
 * The observer interface is used to create an observer object and
 * to give the option to run certain commands when an observable object has changed
 */
public interface Observer {

    /**
     * Execute the update commands to be executed when
     * an observable object notifies its observers
     *
     */
    void update() throws IOException;
}

package com.g.play.gplay.service.Pattern.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer design pattern used to register and notify registered observers
 * based on certain events
 */
public abstract class Observable {

    protected List<Observer> observers;

    /**
     * Default constructor
     */
    public Observable() {
        observers = new ArrayList<>();
    }

    /**
     * Add an observer to the list of observers to notify
     *
     * @param observer the observer to add
     */
    void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer to be opted out from the notify list
     *
     * @param observer the observer to remove
     */
    void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notify the observers and execute their update functions
     */
    protected abstract void notifyObservers() throws Exception;
}


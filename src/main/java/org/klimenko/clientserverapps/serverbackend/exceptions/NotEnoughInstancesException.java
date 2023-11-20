package org.klimenko.clientserverapps.serverbackend.exceptions;

public class NotEnoughInstancesException extends Exception {
    @Override
    public String toString() {
        return "Not enough stuff in store";
    }

    public NotEnoughInstancesException() {
        super();
    }
}

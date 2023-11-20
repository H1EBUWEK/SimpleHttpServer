package org.klimenko.clientserverapps.serverbackend.exceptions;

public class NameAlreadyTakenException extends Exception {
    @Override
    public String toString() {
        return "NameAlreadyTakenException";
    }

    public NameAlreadyTakenException() {
    }
}

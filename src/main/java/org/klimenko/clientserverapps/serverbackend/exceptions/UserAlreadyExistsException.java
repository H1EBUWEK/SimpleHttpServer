package org.klimenko.clientserverapps.serverbackend.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super();
    }

    public String toString() {
        return "This UserName is already taken";
    }
}

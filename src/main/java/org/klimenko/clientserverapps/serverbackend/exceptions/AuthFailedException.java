package org.klimenko.clientserverapps.serverbackend.exceptions;

public class AuthFailedException extends Exception {
    @Override
    public String toString() {
        return "AuthFailedException";
    }

    public AuthFailedException() {
        super();
    }
}

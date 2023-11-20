package org.klimenko.clientserverapps.serverbackend.exceptions;

public class TokenOutOfDateException extends Exception {
    public TokenOutOfDateException() {
        super();
    };

    public String toString() {
        return "Token is out of date. Use RefreshToken to get new AccessToken";
    }
}

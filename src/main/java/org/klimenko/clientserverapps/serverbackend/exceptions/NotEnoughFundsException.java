package org.klimenko.clientserverapps.serverbackend.exceptions;

public class NotEnoughFundsException extends Exception {
    @Override
    public String toString() {
        return "NotEnoughFundsException";
    }

    public NotEnoughFundsException() {
        super();
    }
}

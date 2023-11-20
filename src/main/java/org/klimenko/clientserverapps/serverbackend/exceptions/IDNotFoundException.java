package org.klimenko.clientserverapps.serverbackend.exceptions;

public class IDNotFoundException extends Exception {
    private int id;

    public IDNotFoundException(int id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return "There is no such ID: " + id;
    }
}

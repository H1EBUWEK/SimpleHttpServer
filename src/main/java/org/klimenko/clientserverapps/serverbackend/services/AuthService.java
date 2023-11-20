package org.klimenko.clientserverapps.serverbackend.services;

import org.klimenko.clientserverapps.serverbackend.models.User;
import org.klimenko.clientserverapps.serverbackend.exceptions.AuthFailedException;
import org.klimenko.clientserverapps.serverbackend.exceptions.TokenOutOfDateException;
import org.klimenko.clientserverapps.serverbackend.exceptions.UserAlreadyExistsException;

public interface AuthService {
    public abstract void registerUser(String name, String balance, String hash) throws UserAlreadyExistsException;
    public abstract String authUser(String hash, String salt, String name) throws AuthFailedException;
    public abstract User checkToken(String AToken) throws TokenOutOfDateException, AuthFailedException;
    public abstract String refreshToken(String RToken) throws TokenOutOfDateException, AuthFailedException;

}

package org.klimenko.clientserverapps.serverbackend.services;

import org.klimenko.clientserverapps.serverbackend.models.Token;
import org.klimenko.clientserverapps.serverbackend.models.User;

public interface TokenService {
    public abstract String createJWT(User user, long durationDays);
    public abstract Token verifyToken(String token);
}

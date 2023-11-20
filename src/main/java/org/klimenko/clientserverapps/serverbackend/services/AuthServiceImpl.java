package org.klimenko.clientserverapps.serverbackend.services;

import com.google.common.hash.Hashing;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.klimenko.clientserverapps.serverbackend.exceptions.AuthFailedException;
import org.klimenko.clientserverapps.serverbackend.exceptions.TokenOutOfDateException;
import org.klimenko.clientserverapps.serverbackend.exceptions.UserAlreadyExistsException;
import org.klimenko.clientserverapps.serverbackend.dao.TrayDAO;
import org.klimenko.clientserverapps.serverbackend.dao.UserDAO;
import org.klimenko.clientserverapps.serverbackend.models.Token;
import org.klimenko.clientserverapps.serverbackend.models.User;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;


@Service
public class AuthServiceImpl implements AuthService {

    private final Long ACCESS_TOKEN_DURATION = 1L;
    private final Long REFRESH_TOKEN_DURATION = 31L;


    @Autowired
    UserDAO userDAO;

    @Autowired
    TokenService tokenService;

    @Autowired
    TrayDAO trayDAO;

    @Override
    public void registerUser(String name, String balance, String hash) throws UserAlreadyExistsException {
        if (userDAO.doesUserExist(name))
            throw new UserAlreadyExistsException();
        this.userDAO.addUser(name, new BigDecimal(balance), hash);
        // register a tray for user
        this.trayDAO.createTray(userDAO.getUser(name));
    }


    // creating a user
    @Override
    public String authUser(String hash, String salt, String name) throws AuthFailedException {
        User user = userDAO.getUser(name);
        if (user == null)
            throw new AuthFailedException();
        String hashed = Hashing.sha256().hashString(user.getHash() + salt, StandardCharsets.UTF_8).toString();
        if (hashed.equals(hash)) {
            String accessToken = tokenService.createJWT(user, ACCESS_TOKEN_DURATION);
            String refreshToken = tokenService.createJWT(user, REFRESH_TOKEN_DURATION);
            return accessToken + " " + refreshToken;
        }
        throw new AuthFailedException();
    }

    @Override
    public User checkToken(String AToken) throws TokenOutOfDateException, AuthFailedException{
        Token t = tokenService.verifyToken(AToken);
        if (t == null)
            throw new AuthFailedException();
        // uhm... yeah, time's a bit tricky. hope this monster works
        if (t.getExpires().isAfter(new DateTime(new org.joda.time.Instant(Calendar.getInstance().getTimeInMillis()))))
            throw new TokenOutOfDateException();
        return userDAO.getUser(t.getUserId());
    }

    @Override
    public String refreshToken(String RToken) throws TokenOutOfDateException, AuthFailedException {
        Token t = tokenService.verifyToken(RToken);
        if (t == null)
            throw new AuthFailedException();
        User user = this.userDAO.getUser(t.getUserId());
        String accessToken = tokenService.createJWT(user, ACCESS_TOKEN_DURATION);
        String refreshToken = tokenService.createJWT(user, REFRESH_TOKEN_DURATION);
        return accessToken + " " + refreshToken;
    }
}

package org.klimenko.clientserverapps.serverbackend.controllers;


import org.klimenko.clientserverapps.serverbackend.models.Response;
import org.klimenko.clientserverapps.serverbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.klimenko.clientserverapps.serverbackend.exceptions.AuthFailedException;
import org.klimenko.clientserverapps.serverbackend.exceptions.TokenOutOfDateException;
import org.klimenko.clientserverapps.serverbackend.exceptions.UserAlreadyExistsException;
import org.klimenko.clientserverapps.serverbackend.services.AuthService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * Registers user in the system
     * @param name Unique username
     * @param hash login:password hash. Unencrypted at the moment
     * @param balance BigDecimal User's balance
     * @return String success/fail message
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Response register(@RequestParam("name") String name, @RequestParam("hash") String hash, @RequestParam("balance") String balance)
    {
        try {
            authService.registerUser(name, balance, hash);
            return new Response(HttpServletResponse.SC_OK, null);
        } catch (UserAlreadyExistsException e) {
            return new Response(HttpServletResponse.SC_CONFLICT, e.toString());
        }

    }

    /**
     * Authenticates user in the system
     * @param name Username
     * @param hash Username's SHA256(SHA256(login:password) + salt)
     * @param salt Salt
     * @return Tokens of auth in a form of string: "authToken refreshToken"
     */
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    @ResponseBody
    public Response auth(@RequestParam("name") String name, @RequestParam("hash") String hash, @RequestParam("salt") String salt)
    {
        try {
            return new Response(HttpServletResponse.SC_OK, authService.authUser(hash, salt, name));
        } catch (AuthFailedException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        }
    }

    /**
     * Refershes tokens
     * @param rToken Refresh token
     * @return Tokens of auth in a form of string: "authToken refreshToken"
     */
    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    @ResponseBody
    public Response refresh(@RequestParam("rtoken") String rToken)
    {
        try {
            User user = authService.checkToken(rToken);
            return new Response(HttpServletResponse.SC_OK, authService.refreshToken(rToken));
        } catch (TokenOutOfDateException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        } catch (AuthFailedException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        }
    }
}
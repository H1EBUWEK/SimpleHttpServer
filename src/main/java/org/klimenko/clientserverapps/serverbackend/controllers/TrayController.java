package org.klimenko.clientserverapps.serverbackend.controllers;

import org.klimenko.clientserverapps.serverbackend.models.Response;
import org.klimenko.clientserverapps.serverbackend.models.User;
import org.klimenko.clientserverapps.serverbackend.services.AuthService;
import org.klimenko.clientserverapps.serverbackend.services.TrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.klimenko.clientserverapps.serverbackend.exceptions.AuthFailedException;
import org.klimenko.clientserverapps.serverbackend.exceptions.IDNotFoundException;
import org.klimenko.clientserverapps.serverbackend.exceptions.NotEnoughInstancesException;
import org.klimenko.clientserverapps.serverbackend.exceptions.TokenOutOfDateException;

import javax.servlet.http.HttpServletResponse;

@Controller
public class TrayController {

    @Autowired
    private TrayService trayService;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "tray", method = RequestMethod.GET)
    @ResponseBody
    public Response tray(@RequestParam("token") String token)
    {
        try {
            User user = authService.checkToken(token);
            return new Response(HttpServletResponse.SC_OK, trayService.getTrayContent(user));
        } catch (TokenOutOfDateException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        } catch (AuthFailedException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        }
    }

    @RequestMapping(value = "tray", method = RequestMethod.PUT)
    @ResponseBody
    public Response checkout(@RequestParam("token") String token)
    {
        try {
            User user = authService.checkToken(token);
            trayService.checkout(user);
            return new Response(HttpServletResponse.SC_OK, null);
        } catch (IDNotFoundException e) {
            return new Response(HttpServletResponse.SC_BAD_REQUEST, e.toString());
        } catch (NotEnoughInstancesException e) {
            return new Response(HttpServletResponse.SC_BAD_REQUEST, e.toString());
        } catch (TokenOutOfDateException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        } catch (AuthFailedException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        }
    }

    @RequestMapping(value = "tray/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(@PathVariable int id, @RequestParam("token") String token)
    {
        try {
            User user = authService.checkToken(token);
            trayService.removeFromTray(user, id);
            return new Response(HttpServletResponse.SC_OK, null);
        } catch (TokenOutOfDateException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        } catch (IDNotFoundException e) {
            return new Response(HttpServletResponse.SC_BAD_REQUEST, e.toString());
        } catch (AuthFailedException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        }
    }
}

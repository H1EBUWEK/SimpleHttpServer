package org.klimenko.clientserverapps.serverbackend.controllers;

import org.klimenko.clientserverapps.serverbackend.services.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.klimenko.clientserverapps.serverbackend.exceptions.AuthFailedException;
import org.klimenko.clientserverapps.serverbackend.exceptions.IDNotFoundException;
import org.klimenko.clientserverapps.serverbackend.exceptions.NotEnoughInstancesException;
import org.klimenko.clientserverapps.serverbackend.exceptions.TokenOutOfDateException;
import org.klimenko.clientserverapps.serverbackend.models.Response;
import org.klimenko.clientserverapps.serverbackend.models.User;
import org.klimenko.clientserverapps.serverbackend.services.AuthService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class StuffController {

    @Autowired
    private StuffService stuffService;

    @Autowired
    private AuthService authService;

    /**
     * Gets a list of pets
     * @return list of pets
     */
    @RequestMapping(value = "stuff", method = RequestMethod.GET)
    @ResponseBody
    public Response pets()
    {
        return new Response(HttpServletResponse.SC_OK, stuffService.getStuffs());
    }


    /**
     * Gets specific info about a pet
     * @param id pet it
     * @return pet info
     */
    @RequestMapping(value = "stuff/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Response pet(@PathVariable int id)
    {
        try {
            return new Response(HttpServletResponse.SC_OK, stuffService.getStuff(id));
        } catch (IDNotFoundException e)
        {
            return new Response(HttpServletResponse.SC_BAD_REQUEST, null);
        }
    }

    /**
     * Adds to cart a specific amount of a pet
     * @param id pet id
     * @param amount amount id
     * @param token user's token
     * @return Status
     */
    @RequestMapping(value = "stuff/{id}/buy", method = RequestMethod.GET)
    @ResponseBody
    public Response buyPet(@PathVariable int id, @RequestParam("amount") int amount, @RequestParam("token") String token)
    {
        try {
            User user = authService.checkToken(token);
            stuffService.buyStuff(id, amount, user);
            return new Response(HttpServletResponse.SC_OK, null);
        } catch (TokenOutOfDateException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        } catch (IDNotFoundException e) {
            return new Response(HttpServletResponse.SC_BAD_REQUEST, e.toString());
        } catch (AuthFailedException e) {
            return new Response(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        } catch (NotEnoughInstancesException e) {
            return new Response(HttpServletResponse.SC_BAD_REQUEST, e.toString());
        }
    }
}

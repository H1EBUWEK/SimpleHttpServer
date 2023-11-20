package org.klimenko.clientserverapps.serverbackend.services;

import org.klimenko.clientserverapps.serverbackend.exceptions.IDNotFoundException;
import org.klimenko.clientserverapps.serverbackend.exceptions.NotEnoughInstancesException;
import org.klimenko.clientserverapps.serverbackend.models.Stuff;
import org.klimenko.clientserverapps.serverbackend.models.User;

import java.util.List;

public interface StuffService {
    public abstract Stuff getStuff(int id) throws IDNotFoundException;
    public abstract List<Stuff> getStuffs();
    public abstract void buyStuff(int id, int amount, User user) throws IDNotFoundException, NotEnoughInstancesException;
}

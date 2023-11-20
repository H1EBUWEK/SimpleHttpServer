package org.klimenko.clientserverapps.serverbackend.services;

import org.klimenko.clientserverapps.serverbackend.exceptions.IDNotFoundException;
import org.klimenko.clientserverapps.serverbackend.exceptions.NotEnoughInstancesException;
import org.klimenko.clientserverapps.serverbackend.models.ProductTrayWrapper;
import org.klimenko.clientserverapps.serverbackend.models.User;

import java.util.List;

public interface TrayService {
    public abstract void removeFromTray(User user, int id) throws IDNotFoundException;
    public abstract List<ProductTrayWrapper> getTrayContent(User user);
    public abstract void checkout(User user) throws IDNotFoundException, NotEnoughInstancesException;
}

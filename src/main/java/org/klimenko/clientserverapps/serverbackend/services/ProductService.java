package org.klimenko.clientserverapps.serverbackend.services;

import org.klimenko.clientserverapps.serverbackend.models.Product;
import org.klimenko.clientserverapps.serverbackend.models.ProductWrapper;
import org.klimenko.clientserverapps.serverbackend.models.User;
import org.klimenko.clientserverapps.serverbackend.exceptions.IDNotFoundException;
import org.klimenko.clientserverapps.serverbackend.exceptions.NotEnoughInstancesException;

import java.util.List;

public interface ProductService {
    public abstract Product getProduct(int id) throws IDNotFoundException;
    public abstract List<ProductWrapper> getProducts();
    public abstract void buyProduct(int id, int amount, User user) throws IDNotFoundException, NotEnoughInstancesException;
}

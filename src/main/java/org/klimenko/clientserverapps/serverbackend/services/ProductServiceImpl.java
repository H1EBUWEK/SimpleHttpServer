package org.klimenko.clientserverapps.serverbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.klimenko.clientserverapps.serverbackend.exceptions.IDNotFoundException;
import org.klimenko.clientserverapps.serverbackend.exceptions.NotEnoughInstancesException;
import org.klimenko.clientserverapps.serverbackend.dao.TrayDAO;
import org.klimenko.clientserverapps.serverbackend.models.ProductWrapper;
import org.klimenko.clientserverapps.serverbackend.dao.ProductDAO;
import org.klimenko.clientserverapps.serverbackend.models.Product;
import org.klimenko.clientserverapps.serverbackend.models.ProductTrayWrapper;
import org.klimenko.clientserverapps.serverbackend.models.User;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private TrayDAO trayDAO;

    @Override
    public Product getProduct(int id) throws IDNotFoundException {
        Product product = productDAO.getPet(id);
        if (product == null)
            throw new IDNotFoundException(id);
        return product;
    }

    @Override
    public List<ProductWrapper> getProducts() {
        return productDAO.getPets();
    }

    @Override
    public void buyProduct(int id, int amount, User user) throws IDNotFoundException, NotEnoughInstancesException {
        Product product = productDAO.getPet(id);
        if (product == null)
            throw new IDNotFoundException(id);
        if (product.getCount() < amount)
            throw new NotEnoughInstancesException();
        productDAO.buyPet(id, product.getCount() - amount);
        // working with trayDAO
        ProductTrayWrapper productTrayWrapper = new ProductTrayWrapper(product.getId(), amount, "TypePet");
        if (trayDAO.isInCart(user, productTrayWrapper))
            trayDAO.alterProductAmount(user, productTrayWrapper);
        else
            trayDAO.addToCart(user, new ProductTrayWrapper(product.getId(), amount, "TypePet"));
    }
}

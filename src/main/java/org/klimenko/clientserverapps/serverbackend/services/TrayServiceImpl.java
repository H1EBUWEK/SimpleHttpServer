package org.klimenko.clientserverapps.serverbackend.services;

import org.klimenko.clientserverapps.serverbackend.dao.ProductDAO;
import org.klimenko.clientserverapps.serverbackend.dao.StuffDAO;
import org.klimenko.clientserverapps.serverbackend.dao.TrayDAO;
import org.klimenko.clientserverapps.serverbackend.enums.ItemType;
import org.klimenko.clientserverapps.serverbackend.models.ProductTrayWrapper;
import org.klimenko.clientserverapps.serverbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.klimenko.clientserverapps.serverbackend.exceptions.IDNotFoundException;
import org.klimenko.clientserverapps.serverbackend.exceptions.NotEnoughInstancesException;
import org.klimenko.clientserverapps.serverbackend.dao.UserDAO;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TrayServiceImpl implements TrayService{
    @Autowired
    private TrayDAO trayDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private StuffDAO stuffDAO;

    public TrayServiceImpl() {
    }

    @Override
    public void removeFromTray(User user, int id) throws IDNotFoundException {
        this.trayDAO.removeFromCart(user, id);
    }

    @Override
    public List<ProductTrayWrapper> getTrayContent(User user) {
        return trayDAO.getTrayItems(user);
    }

    @Override
    public void checkout(User user) throws IDNotFoundException, NotEnoughInstancesException {
        BigDecimal price = new BigDecimal("0");
        List<ProductTrayWrapper> productTrayWrappers = trayDAO.getTrayItems(user);
        for (ProductTrayWrapper a : productTrayWrappers) {
            if (a.getItemType().equals(ItemType.TypeProduct.toString()))
            {
                String p = productDAO.getPet(trayDAO.getItem(user, a.getId()).getProductID()).getPrice();
                BigDecimal dbg = new BigDecimal(p);
                price = price.add(new BigDecimal(p));
                price = price.multiply(new BigDecimal(a.getCount()));
            }
            if (a.getItemType().equals(ItemType.TypeStuff.toString()))
            {
                String p = stuffDAO.getStuff(trayDAO.getItem(user, a.getId()).getProductID()).getPrice();
                BigDecimal dbg = new BigDecimal(p);
                price = price.add(new BigDecimal(p));
                price = price.multiply(new BigDecimal(a.getCount()));
            }
        }
        if (new BigDecimal(user.getBalance()).compareTo(price) >= 0)
            userDAO.alterBalance(user, (new BigDecimal(user.getBalance()).subtract(price)).toString());
        else
            throw new NotEnoughInstancesException();
        trayDAO.emptyTray(user);
    }
}

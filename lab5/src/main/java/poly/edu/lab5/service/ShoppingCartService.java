package poly.edu.lab5.service;

import java.util.Collection;
import poly.edu.lab5.entity.Item;

public interface ShoppingCartService {
    Item add(Integer id);

    void remove(Integer id);

    Item update(Integer id, int qty);

    void clear();

    Collection<Item> getItems();

    int getCount();

    double getAmount();
}

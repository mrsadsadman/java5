package poly.edu.lab5.entity;

import java.util.HashMap;
import java.util.Map;

public class DB {
    public static Map<Integer, Item> items = new HashMap<>();

    static {
        items.put(1, new Item(1, "Samsung", 10.0, 1));
        items.put(2, new Item(2, "Nokia", 20.5, 1));
        items.put(3, new Item(3, "iPhone", 90.0, 1));
    }
}

package domain.pos.model;

import domain.pos.type.Category;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Table {

    private final int number;
    private int totalPrice;
    private final Map<Menu, Integer> orderHistory = new HashMap<>();

    public Table(final int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Map<Menu, Integer> getOrderHistory() {
        return orderHistory;
    }

    public int getTotalPrice() {
        return totalPrice;
    }


    public void init() {
        this.totalPrice = 0;
        this.orderHistory.clear();
    }

    public void addOrder(Menu menu, int quantity) {
        totalPrice += (menu.getPrice() * quantity);
        addOrderHistory(menu, quantity);
    }

    private void addOrderHistory(Menu menu, int quantity) {
        if (orderHistory.containsKey(menu)) {
            orderHistory.replace(menu, orderHistory.get(menu) + quantity);
        } else {
            orderHistory.put(menu, quantity);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}

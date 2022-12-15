package domain.pos.dto;

import domain.pos.model.Menu;

public class OrderHistoryResponse {

    private String menu;
    private int quantity;
    private int price;

    public OrderHistoryResponse(String menu, int quantity, int price) {
        this.menu = menu;
        this.quantity = quantity;
        this.price = price;
    }

    public String getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return menu + " " + quantity + " " + price;
    }
}

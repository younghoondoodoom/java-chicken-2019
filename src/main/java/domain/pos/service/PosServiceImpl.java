package domain.pos.service;

import domain.pos.dto.OrderHistoryResponse;
import domain.pos.exception.PosErrorCode;
import domain.pos.model.Menu;
import domain.pos.model.Table;
import domain.pos.repository.MenuRepository;
import domain.pos.repository.TableRepository;
import domain.pos.type.Category;
import domain.pos.type.PayType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PosServiceImpl implements PosService {

    private final MenuRepository menuRepository;
    private final TableRepository tableRepository;
    private static final int CHICKEN_DISCOUNT_PRICE = 10_000;
    private static final int CHICKEN_DISCOUNT_MINIMUM_UNIT = 10;
    private static final double CASH_DISCOUNT_RATE = 0.95;

    public PosServiceImpl(MenuRepository menuRepository, TableRepository tableRepository) {
        this.menuRepository = menuRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public boolean order(final int tableNumber, final int menuNumber, final int quantity)
        throws IllegalArgumentException {
        Table table = getTableByTableNumber(tableNumber);
        Menu menu = getMenuByMenuNumber(menuNumber);
        table.addOrder(menu, quantity);
        return true;
    }

    @Override
    public int pay(final int tableNumber, final PayType payType) throws IllegalArgumentException {
        Table table = getTableByTableNumber(tableNumber);
        int totalPrice = table.getTotalPrice();
        totalPrice -= getChickenDiscountPrice(table.getOrderHistory());
        if (payType.equals(PayType.CASH)) {
            totalPrice = (int) (totalPrice * CASH_DISCOUNT_RATE);
        }
        table.init();
        return totalPrice;
    }

    @Override
    public List<OrderHistoryResponse> getOrderHistory(int tableNumber) {
        List<OrderHistoryResponse> result = new ArrayList<>();
        Map<Menu, Integer> orderHistory = getTableByTableNumber(tableNumber).getOrderHistory();
        for (Menu menu : orderHistory.keySet()) {
            result.add(new OrderHistoryResponse(menu.getName(), orderHistory.get(menu),
                orderHistory.get(menu) * menu.getPrice()));
        }
        return result;
    }

    private int getChickenDiscountPrice(Map<Menu, Integer> orderHistory) {
        int count = 0;
        for (Menu menu : orderHistory.keySet()) {
            if (menu.getCategory().equals(Category.CHICKEN)) {
                count++;
            }
        }
        return count / CHICKEN_DISCOUNT_MINIMUM_UNIT * CHICKEN_DISCOUNT_PRICE;
    }

    private Table getTableByTableNumber(int tableNumber) {
        return tableRepository.findByNumber(tableNumber).orElseThrow(
            () -> new IllegalArgumentException(PosErrorCode.TABLE_NOT_FOUND.getMessage()));
    }

    private Menu getMenuByMenuNumber(int menuNumber) {
        return menuRepository.findByNumber(menuNumber).orElseThrow(
            () -> new IllegalArgumentException(PosErrorCode.MENU_NOT_FOUND.getMessage()));
    }
}

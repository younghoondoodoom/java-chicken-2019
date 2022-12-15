package domain.pos.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.pos.dto.OrderHistoryResponse;
import domain.pos.exception.PosErrorCode;
import domain.pos.model.Menu;
import domain.pos.model.Table;
import domain.pos.repository.MenuRepository;
import domain.pos.repository.TableRepository;
import domain.pos.type.Category;
import domain.pos.type.PayType;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PosServiceITest {

    private PosService posService;

    private Table table = new Table(1);
    private Menu menu = new Menu(1, "치킨", Category.CHICKEN, 10_000);

    class SuccessTableRepository implements TableRepository {

        @Override
        public Optional<Table> findByNumber(int number) {
            return Optional.of(table);
        }
    }

    class SuccessMenuRepository implements MenuRepository {

        @Override
        public Optional<Menu> findByNumber(int number) {
            return Optional.of(menu);
        }
    }

    static class FailureTableRepository implements TableRepository {

        @Override
        public Optional<Table> findByNumber(int number) {
            return Optional.empty();
        }
    }

    static class FailureMenuRepository implements MenuRepository {

        @Override
        public Optional<Menu> findByNumber(int number) {
            return Optional.empty();
        }
    }

    @Test
    public void orderSuccess() {
        //given
        posService = new PosServiceImpl(new SuccessMenuRepository(), new SuccessTableRepository());
        int quantity = 10;

        //when
        posService.order(table.getNumber(), menu.getNumber(), quantity);

        //then
        assertThat(table.getTotalPrice()).isEqualTo(menu.getPrice() * quantity);
        assertThat(table.getOrderHistory().get(menu)).isEqualTo(quantity);
    }

    @Test
    public void orderTableNotFoundFailure() {
        //given
        posService = new PosServiceImpl(new SuccessMenuRepository(), new FailureTableRepository());

        //when
        //then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> posService.order(table.getNumber(), menu.getNumber(), 1));
        assertThat(exception.getMessage()).isEqualTo(PosErrorCode.TABLE_NOT_FOUND.getMessage());
    }

    @Test
    public void orderMenuNotFoundFailure() {
        //given
        posService = new PosServiceImpl(new FailureMenuRepository(), new SuccessTableRepository());

        //when
        //then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> posService.order(table.getNumber(), menu.getNumber(), 1));
        assertThat(exception.getMessage()).isEqualTo(PosErrorCode.MENU_NOT_FOUND.getMessage());
    }

    @Test
    public void payOnlyChickenDiscountSuccess() {
        //given
        posService = new PosServiceImpl(new SuccessMenuRepository(), new SuccessTableRepository());
        int quantity = 10;
        table.addOrder(menu, quantity);
        PayType payType = PayType.CARD;

        //when
        int result = posService.pay(table.getNumber(), payType);

        //then
        assertThat(result).isEqualTo(90_000);
    }

    @Test
    public void payOnlyCashDiscountSuccess() {
        //given
        menu = new Menu(1, "콜라", Category.BEVERAGE, 10_000);
        posService = new PosServiceImpl(new SuccessMenuRepository(), new SuccessTableRepository());
        int quantity = 10;
        table.addOrder(menu, quantity);
        PayType payType = PayType.CASH;

        //when
        int result = posService.pay(table.getNumber(), payType);

        //then
        assertThat(result).isEqualTo(95_000);
    }

    @Test
    public void payChickenAndCashDiscountSuccess() {
        //given
        posService = new PosServiceImpl(new SuccessMenuRepository(), new SuccessTableRepository());
        int quantity = 10;
        table.addOrder(menu, quantity);
        PayType payType = PayType.CASH;

        //when
        int result = posService.pay(table.getNumber(), payType);

        //then
        assertThat(result).isEqualTo(85_500);
    }

    @Test
    public void getOrderHistorySuccess() {
        //given
        posService = new PosServiceImpl(new SuccessMenuRepository(), new SuccessTableRepository());
        Menu menu1 = new Menu(1, "후라이드 치킨", Category.CHICKEN, 18_000);
        int quantity1 = 10;
        Menu menu2 = new Menu(2, "콜라", Category.BEVERAGE, 8_000);
        int quantity2 = 8;
        table.addOrder(menu1, quantity1);
        table.addOrder(menu2, quantity2);

        //when
        List<OrderHistoryResponse> orderHistoryResponses = posService.getOrderHistory(1);

        //then
        for (OrderHistoryResponse orderHistoryResponse : orderHistoryResponses) {
            if (orderHistoryResponse.getMenu().equals(menu1.getName())) {
                assertThat(orderHistoryResponse.getPrice()).isEqualTo(menu1.getPrice() * quantity1);
                assertThat(orderHistoryResponse.getQuantity()).isEqualTo(quantity1);
            }
            if (orderHistoryResponse.getMenu().equals(menu2.getName())) {
                assertThat(orderHistoryResponse.getPrice()).isEqualTo(menu2.getPrice() * quantity2);
                assertThat(orderHistoryResponse.getQuantity()).isEqualTo(quantity2);
            }
        }
    }
}
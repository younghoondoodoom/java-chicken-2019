package domain.pos.model;

import static org.assertj.core.api.Assertions.assertThat;

import domain.pos.type.Category;
import org.junit.jupiter.api.Test;

class TableTest {

    @Test
    public void addOrderWhenEmpty() {
        //given
        Table table = new Table(1);
        Menu menu = new Menu(1, "치킨", Category.CHICKEN, 10_000);
        int quantity = 10;

        //when
        table.addOrder(menu, quantity);

        //then
        assertThat(table.getTotalPrice()).isEqualTo(menu.getPrice() * quantity);
        assertThat(table.getOrderHistory().get(menu)).isEqualTo(quantity);
    }

    @Test
    public void addOrderWhenNotEmpty() {
        //given
        Table table = new Table(1);
        Menu menu1 = new Menu(1, "후라이드 치킨", Category.CHICKEN, 10_000);
        int quantity1 = 10;
        table.addOrder(menu1, quantity1);

        Menu menu2 = new Menu(2, "양념 치킨", Category.CHICKEN, 20_000);
        int quantity2 = 5;

        //when
        table.addOrder(menu2, quantity2);

        //then
        assertThat(table.getTotalPrice()).isEqualTo(
            menu1.getPrice() * quantity1 + menu2.getPrice() * quantity2);
        assertThat(table.getOrderHistory().get(menu1)).isEqualTo(quantity1);
        assertThat(table.getOrderHistory().get(menu2)).isEqualTo(quantity2);
    }
}
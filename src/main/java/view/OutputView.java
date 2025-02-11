package view;

import domain.pos.dto.OrderHistoryResponse;
import domain.pos.model.Menu;
import domain.pos.model.Table;

import java.util.List;

public class OutputView {
    private static final String TOP_LINE = "┌ ─ ┐";
    private static final String TABLE_FORMAT = "| %s |";
    private static final String BOTTOM_LINE = "└ ─ ┘";

    public static void printTables(final List<Table> tables) {
        System.out.println("## 테이블 목록");
        final int size = tables.size();
        printLine(TOP_LINE, size);
        printTableNumbers(tables);
        printLine(BOTTOM_LINE, size);
    }

    public static void printMenus(final List<Menu> menus) {
        for (final Menu menu : menus) {
            System.out.println(menu);
        }
    }

    public static void printMain() {
        System.out.println("## 메인화면");
        System.out.println("1 - 주문등록");
        System.out.println("2 - 결제하기");
        System.out.println("3 - 프로그램 종료");
    }

    public static void printExit() {
        System.out.println("포스가 종료 되었습니다.");
    }

    public static void printOrderHistory(List<OrderHistoryResponse> orderHistoryResponses) {
        System.out.println("메뉴 수량 금액");
        for (OrderHistoryResponse orderHistoryResponse : orderHistoryResponses) {
            System.out.println(orderHistoryResponse.toString());
        }
    }

    public static void printFinalPrice(int finalPrice) {
        System.out.println("## 최종 결제할 금액");
        System.out.println(finalPrice + "원");
        System.out.println();
    }

    private static void printLine(final String line, final int count) {
        for (int index = 0; index < count; index++) {
            System.out.print(line);
        }
        System.out.println();
    }

    private static void printTableNumbers(final List<Table> tables) {
        for (final Table table : tables) {
            System.out.printf(TABLE_FORMAT, table);
        }
        System.out.println();
    }
}

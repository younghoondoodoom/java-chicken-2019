package view;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String INPUT_TABLE_NUMBER_INFORMATION = "## 주문할 테이블을 선택하세요.";
    private static final String INPUT_MAIN_OPTION_NUMBER_INFORMATION = "## 원하는 기능을 선택하세요.";
    private static final String INPUT_MENU_QUANTITY_INFORMATION = "## 메뉴의 수량을 입력하세요.";
    private static final String TABLE_PAY_INFORMATION = "## %s번 테이블의 결제를 진행합니다.\n";
    private static final String PAY_TYPE_INFORMATION = "## 신용 카드는 1번, 현금은 2번";

    public static String inputMainOptionNumber() {
        return inputForm(INPUT_MAIN_OPTION_NUMBER_INFORMATION);
    }

    public static String inputTableNumber() {
        return inputForm(INPUT_TABLE_NUMBER_INFORMATION);
    }

    public static String inputMenuQuantity() {
        return inputForm(INPUT_MENU_QUANTITY_INFORMATION);
    }

    public static String inputPayType(int tableNumber) {
        System.out.printf(TABLE_PAY_INFORMATION, tableNumber);
        System.out.println(PAY_TYPE_INFORMATION);
        return scanner.next();
    }

    private static String inputForm(String information) {
        System.out.println(information);
        return scanner.next();
    }
}

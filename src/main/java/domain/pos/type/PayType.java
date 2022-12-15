package domain.pos.type;

import java.util.Arrays;

public enum PayType {
    CARD("1"), CASH("2");

    private final String number;
    private static final String PAY_TYPE_NOT_FOUND = "해당 결제 방식은 존재하지 않습니다.";

    PayType(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public static PayType findByNumber(String number) {
        return Arrays.stream(PayType.values()).filter(payType -> payType.getNumber().equals(number)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException(PAY_TYPE_NOT_FOUND));
    }
}

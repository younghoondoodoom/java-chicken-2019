package domain.pos.exception;

public enum PosErrorCode {
    MENU_NOT_FOUND("존재하지 않는 메뉴입니다."),
    TABLE_NOT_FOUND("존재하지 않는 테이블입니다.");
    private final String message;

    PosErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

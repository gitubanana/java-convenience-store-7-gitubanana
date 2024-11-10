package store.constant;

public enum ErrorMessage {
    NOT_A_NUMBER("숫자가 아닙니다."),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
    INVALID_PERIOD("기간이 올바르지 않습니다."),
    CANNOT_FIND_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_ORDER_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    EXCEEDED_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    BUY_COUNT_NOT_POSITIVE("증정 조건이 올바르지 않습니다."),
    GET_COUNT_NOT_POSITIVE("증정 개수가 올바르지 않습니다."),
    DUPLICATED_ORDER("한 물건에 대한 주문이 중복됩니다. 한 곳에 모아서 써주세요."),
    PRICE_NEGATIVE("가격이 음수입니다."),
    QUANTITY_NEGATIVE("수량이 음수입니다."),
    WRONG_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    ;

    private static final String ERROR_PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = ERROR_PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}

package store.constant;

public class OutputInfo {
    public static final String NEW_LINE = "%n";
    public static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    public static final String PRODUCT_START_MESSAGE = "현재 보유하고 있는 상품입니다.";
    public static final String PRODUCT_FORMAT = "- %s %s원 %s" + NEW_LINE;
    public static final String PROMOTION_PRODUCT_FORMAT = "- %s %s원 %s %s" + NEW_LINE;
    public static final String NO_QUANTITY = "재고 없음";
    public static final String QUANTITY_FORMAT = "%d개";
    public static final String MAY_I_TAKE_YOUR_ORDER = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    public static final String FREE_GETTABLE_COUNT_QUESTION =
            "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)" + NEW_LINE;
    public static final String BUY_COUNT_WITHOUT_PROMOTION_QUESTION =
            "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)" + NEW_LINE;
    public static final String MEMBERSHIP_DISCOUNT = "멤버십 할인을 받으시겠습니까? (Y/N)";
    public static final String RECEIPT_START_MESSAGE = "==============W 편의점================" + NEW_LINE;
    public static final String RECEIPT_PURCHASE_INFO_START_MESSAGE = "상품명\t\t수량\t금액" + NEW_LINE;
    public static final String RECEIPT_PURCHASE_INFO_FORMAT = "%s\t\t%d \t%s" + NEW_LINE;
    public static final String RECEIPT_FREE_START_MESSAGE = "=============증\t정===============" + NEW_LINE;
    public static final String RECEIPT_FREE_PRODUCT_FORMAT = "%s\t\t%d" + NEW_LINE;
    public static final String RECEIPT_PAY_START_MESSAGE = "====================================" + NEW_LINE;
    public static final String RECEIPT_TOTAL_PRICE_FORMAT = "총구매액\t\t%d\t%s" + NEW_LINE;
    public static final String RECEIPT_PROMOTION_DISCOUNT_FORMAT = "행사할인\t\t\t-%s" + NEW_LINE;
    public static final String RECEIPT_MEMBERSHIP_DISCOUNT_FORMAT = "멤버십할인\t\t\t-%s" + NEW_LINE;
    public static final String RECEIPT_AMOUNT_OF_PAY_FORMAT = "내실돈\t\t\t %s" + NEW_LINE;
    public static final String WANNA_BUY_MORE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
}

package store.model.order;

import static store.constant.ErrorMessage.WRONG_INPUT;

public class Order {
    private final String productName;
    private int buyCount;

    public Order(String productName, final int buyCount) {
        validateNotNegative(buyCount);
        this.productName = productName;
        this.buyCount = buyCount;
    }

    private void validateNotNegative(final int buyCount) {
        if (buyCount < 0) { // TODO: 0도 처리? (컵라면 5개일 때 취소하면 오류)
            throw new IllegalArgumentException(WRONG_INPUT.getMessage());
        }
    }

    public String getProductName() {
        return productName;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void add(final int toAdd) {
        buyCount += toAdd;
    }

    public void cancel(final int toSub) {
        buyCount -= toSub;
    }
}

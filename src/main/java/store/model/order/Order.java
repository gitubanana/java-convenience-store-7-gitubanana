package store.model.order;

import static store.constant.ErrorMessage.WRONG_INPUT;

public class Order {
    protected final String productName;
    protected int buyCount;

    public Order(String productName, final int buyCount) {
        validateNotNegative(buyCount);
        this.productName = productName;
        this.buyCount = buyCount;
    }

    private void validateNotNegative(final int buyCount) {
        if (buyCount < 0) {
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

    public void cancel(final int toCancel) {
        buyCount -= toCancel;
    }
}

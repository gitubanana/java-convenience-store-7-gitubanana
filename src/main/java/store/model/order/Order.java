package store.model.order;

import static store.constant.ErrorMessage.WRONG_INPUT;

public class Order {
    private final String productName;
    private int buyCount;

    public Order(String productName, final int buyCount) {
        validate(buyCount);
        this.productName = productName;
        this.buyCount = buyCount;
    }

    private void validate(final int buyCount) {
        validateNotNegative(buyCount);
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

    public void addBuyCount(final int toAdd) {
        buyCount += toAdd;
    }

    public void subBuyCount(final int toSub) {
        buyCount -= toSub;
    }
}

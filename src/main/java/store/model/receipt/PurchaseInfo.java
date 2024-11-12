package store.model.receipt;

import store.model.order.Order;

public class PurchaseInfo extends Order {
    private final int price;
    private final int freeCount;

    public PurchaseInfo(String name, final int buyCount, final int price, final int freeCount) {
        super(name, buyCount);
        this.price = price;
        this.freeCount = freeCount;
    }

    public PurchaseInfo(Order order, final int price, final int freeCount) {
        this(order.getProductName(), order.getBuyCount(), price, freeCount);
    }

    public int getFreeCount() {
        return freeCount;
    }

    public int getTotalAmount() {
        return buyCount * price;
    }

    public int getPromotionDiscountAmount() {
        return freeCount * price;
    }

    public boolean isPromotionDiscounted() {
        return freeCount > 0;
    }

    public boolean isNotPromotionDiscounted() {
        return !isPromotionDiscounted();
    }
}

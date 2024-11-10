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

    public int getPrice() {
        return price;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public int getTotalPriceWithoutDiscount() {
        return buyCount * price;
    }

    public int getDiscountPrice() {
        return freeCount * price;
    }

    public boolean isNotDiscounted() {
        return freeCount == 0;
    }

    public boolean isDiscounted() {
        return !isNotDiscounted();
    }
}

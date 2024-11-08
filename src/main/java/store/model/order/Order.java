package store.model.order;

public class Order {
    private final String productName;
    private final int buyCount;

    public Order(String productName, final int buyCount) {
        this.productName = productName;
        this.buyCount = buyCount;
    }
}

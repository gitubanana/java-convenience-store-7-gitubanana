package store.model.product;

public class PromotionProduct extends Product {
    private final String promotionName;

    public PromotionProduct(String name, int price, int quantity, String promotionName) {
        super(name, price, quantity);
        this.promotionName = promotionName;
    }

    public String getPromotionName() {
        return promotionName;
    }
}

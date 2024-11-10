package store.model.product;

import static store.constant.ErrorMessage.PRICE_NEGATIVE;
import static store.constant.ErrorMessage.QUANTITY_NEGATIVE;

public class Product {
    protected final String name;
    protected final int price;
    protected int quantity;

    public Product(String name, final int price, final int quantity) {
        validate(price, quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    private void validate(final int price, final int quantity) {
        validatePriceNotNegative(price);
        validateQuantityNotNegative(quantity);
    }

    private void validatePriceNotNegative(final int price) {
        if (price < 0) {
            throw new IllegalArgumentException(PRICE_NEGATIVE.getMessage());
        }
    }

    private void validateQuantityNotNegative(final int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(QUANTITY_NEGATIVE.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void sell(final int amount) {
        quantity -= amount;
    }
}

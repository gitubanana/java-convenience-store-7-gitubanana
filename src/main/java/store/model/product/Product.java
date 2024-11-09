package store.model.product;

public class Product {
    protected final String name;
    protected final int price;
    protected int quantity;

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public void sell(int amount) {
        quantity -= amount;
    }
}

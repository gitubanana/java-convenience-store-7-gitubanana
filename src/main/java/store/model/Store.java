package store.model;

import store.model.product.Products;
import store.model.product.PromotionProducts;
import store.model.promotion.Promotions;

public class Store {
    private final Promotions promotions;
    private final Products products;
    private final PromotionProducts promotionProducts;

    public Store(Promotions promotions, Products products, PromotionProducts promotionProducts) {
        this.promotions = promotions;
        this.products = products;
        this.promotionProducts = promotionProducts;
    }

    public Products getProducts() {
        return products;
    }

    public PromotionProducts getPromotionProducts() {
        return promotionProducts;
    }
}

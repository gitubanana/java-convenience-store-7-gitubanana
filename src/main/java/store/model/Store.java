package store.model;

import static store.constant.ErrorMessage.CANNOT_FIND_PRODUCT;
import static store.constant.ErrorMessage.EXCEEDED_QUANTITY;

import store.model.order.Order;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.PromotionProduct;
import store.model.product.PromotionProducts;
import store.model.receipt.PurchaseInfo;

public class Store {
    private final Products products;
    private final PromotionProducts promotionProducts;

    public Store(Products products, PromotionProducts promotionProducts) {
        this.products = products;
        this.promotionProducts = promotionProducts;
    }

    public Products getProducts() {
        return products;
    }

    public PromotionProducts getPromotionProducts() {
        return promotionProducts;
    }

    public void checkAvailability(Order order) {
        checkProductName(order);
        checkStock(order);
    }

    private void checkProductName(Order order) {
        Product product = products.getCorrespondingTo(order);
        if (product == null) {
            throw new IllegalArgumentException(CANNOT_FIND_PRODUCT.getMessage());
        }
    }

    private void checkStock(Order order) {
        Product product = products.getCorrespondingTo(order);
        PromotionProduct promotionProduct = promotionProducts.getCorrespondingTo(order);
        int totalQuantity = product.getQuantity();

        if (isAvailable(promotionProduct)) {
            totalQuantity += promotionProduct.getQuantity();
        }

        if (totalQuantity < order.getBuyCount()) {
            throw new IllegalArgumentException(EXCEEDED_QUANTITY.getMessage());
        }
    }

    public int getFreeGettableCount(Order order) {
        PromotionProduct promotionProduct = promotionProducts.getCorrespondingTo(order);
        if (!isAvailable(promotionProduct)) {
            return 0;
        }

        return promotionProduct.getFreeGettableCount(order.getBuyCount());
    }

    private boolean isAvailable(PromotionProduct promotionProduct) {
        return promotionProduct != null && promotionProduct.isAvailable();
    }

    public int getBuyCountWithoutPromotion(Order order) {
        PromotionProduct promotionProduct = promotionProducts.getCorrespondingTo(order);
        if (!isAvailable(promotionProduct)) {
            return 0;
        }

        if (promotionProduct.getQuantity() >= order.getBuyCount()) {
            return 0;
        }

        int promotionEffectCount = promotionProduct.getPromotionEffectCount(promotionProduct.getQuantity());
        return order.getBuyCount() - promotionEffectCount;
    }

    public PurchaseInfo sell(Order order) {
        int freeCount = 0;
        int remainSellCount = order.getBuyCount();
        PromotionProduct promotionProduct = promotionProducts.getCorrespondingTo(order);

        if (isAvailable(promotionProduct)) {
            final int promotionSellCount = Math.min(remainSellCount, promotionProduct.getQuantity());

            freeCount = promotionProduct.getFreeCountIn(promotionSellCount);
            promotionProduct.sell(promotionSellCount);
            remainSellCount -= promotionSellCount;
        }

        Product product = products.getCorrespondingTo(order);
        product.sell(remainSellCount);

        return new PurchaseInfo(order, product.getPrice(), freeCount);
    }
}

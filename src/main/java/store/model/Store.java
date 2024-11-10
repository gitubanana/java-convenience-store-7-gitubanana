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

    private static boolean isAvailable(PromotionProduct promotionProduct) {
        return promotionProduct != null && promotionProduct.isAvailable();
    }

    public void checkAvailability(Order order) {
        checkProductName(order);
        checkQuantity(order);
    }

    private void checkProductName(Order order) {
        Product product = products.getNameWith(order.getProductName());
        if (product == null) {
            throw new IllegalArgumentException(CANNOT_FIND_PRODUCT.getMessage());
        }
    }

    private void checkQuantity(Order order) {
        Product product = products.getNameWith(order.getProductName());
        PromotionProduct promotionProduct = promotionProducts.getNameWith(order.getProductName());
        int totalQuantity = product.getQuantity();
        if (isAvailable(promotionProduct)) {
            totalQuantity += promotionProduct.getQuantity();
        }

        if (totalQuantity < order.getBuyCount()) {
            throw new IllegalArgumentException(EXCEEDED_QUANTITY.getMessage());
        }
    }

    public int getFreeGettableCount(Order order) {
        PromotionProduct promotionProduct = promotionProducts.getNameWith(order.getProductName());

        if (!isAvailable(promotionProduct)) {
            return 0;
        }
        return promotionProduct.getFreeGettableCount(order.getBuyCount());
    }

    public int getBuyCountWithoutPromotion(Order order) {
        PromotionProduct promotionProduct = promotionProducts.getNameWith(order.getProductName());

        if (!isAvailable(promotionProduct) || order.getBuyCount() <= promotionProduct.getQuantity()) {
            return 0;
        }
        return order.getBuyCount() - promotionProduct.getPromotionEffectCount();
    }

    public PurchaseInfo sell(Order order) {
        Seller seller = new Seller(
                order.getBuyCount(),
                products.getNameWith(order.getProductName()),
                promotionProducts.getNameWith(order.getProductName())
        );

        seller.sell();
        return new PurchaseInfo(order, seller.getPrice(), seller.getFreeCount());
    }

    private static class Seller {
        private int freeCount;
        private int remainSellCount;
        private final Product product;
        private final PromotionProduct promotionProduct;

        public Seller(final int remainSellCount, Product product, PromotionProduct promotionProduct) {
            this.remainSellCount = remainSellCount;
            this.product = product;
            this.promotionProduct = promotionProduct;
        }

        public void sell() {
            sellPromotionProduct();
            sellProduct();
        }

        private void sellPromotionProduct() {
            if (!isAvailable(promotionProduct)) {
                return;
            }

            final int promotionSellCount = Math.min(remainSellCount, promotionProduct.getQuantity());

            freeCount = promotionProduct.getFreeCountIn(promotionSellCount);
            remainSellCount -= promotionSellCount;
            promotionProduct.sell(promotionSellCount);
        }

        private void sellProduct() {
            product.sell(remainSellCount);
        }

        public int getFreeCount() {
            return freeCount;
        }

        public int getPrice() {
            return product.getPrice();
        }
    }
}

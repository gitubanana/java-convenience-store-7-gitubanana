package store.model.product;

import store.model.promotion.Promotion;

public class PromotionProduct extends Product {
    private final Promotion promotion;

    public PromotionProduct(String name, int price, int quantity, Promotion promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }

    public boolean isAvailable() {
        return promotion.isInProgress() && quantity > 0;
    }

    public String getPromotionName() {
        return promotion.getName();
    }

    public int getFreeGettableCount(final int buyCount) {
        final int freeGettableCount = promotion.getFreeGettableCount(buyCount);
        if (buyCount + freeGettableCount > quantity) {
            return 0;
        }

        return freeGettableCount;
    }

    public int getFreeCountIn(final int buyCount) {
        return promotion.getFreeCountIn(buyCount);
    }

    public int getPromotionEffectCount(final int buyCount) {
        return promotion.getFreePolicyEffectCount(buyCount);
    }
}

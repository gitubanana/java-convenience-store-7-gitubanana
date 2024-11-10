package store.model.product;

import store.model.promotion.Promotion;

public class PromotionProduct extends Product {
    private final Promotion promotion;

    public PromotionProduct(String name, final int price, final int quantity, Promotion promotion) {
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
        final int buyCountIncludingFreeGettableCount = buyCount + freeGettableCount;

        if (buyCountIncludingFreeGettableCount > quantity) {
            return 0;
        }
        return freeGettableCount;
    }

    public int getFreeCountIn(final int buyCount) {
        return promotion.getFreeCountIn(buyCount);
    }

    public int getPromotionEffectCount() {
        return promotion.getFreePolicyEffectCount(quantity);
    }
}

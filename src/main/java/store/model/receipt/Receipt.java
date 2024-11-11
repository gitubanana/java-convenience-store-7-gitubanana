package store.model.receipt;

import static store.constant.StoreInfo.MAX_MEMBERSHIP_DISCOUNT_AMOUNT;
import static store.constant.StoreInfo.MEMBERSHIP_DISCOUNT_PERCENTAGE;

public class Receipt {
    private final boolean applyMembershipDiscount;
    PurchaseInfos purchaseInfos;

    public Receipt(final boolean applyMembershipDiscount, PurchaseInfos purchaseInfos) {
        this.applyMembershipDiscount = applyMembershipDiscount;
        this.purchaseInfos = purchaseInfos;
    }

    public PurchaseInfos getPurchaseInfos() {
        return purchaseInfos;
    }

    public int getTotalAmount() {
        return purchaseInfos.getTotalAmount();
    }

    public int getPromotionDiscountAmount() {
        return purchaseInfos.getPromotionDiscountAmount();
    }

    public int getMembershipDiscountAmount() {
        if (!applyMembershipDiscount) {
            return 0;
        }

        return (int) Math.min(
                MAX_MEMBERSHIP_DISCOUNT_AMOUNT,
                MEMBERSHIP_DISCOUNT_PERCENTAGE * purchaseInfos.getNotPromotionDiscountAppliedProductsAmount()
        );
    }

    public int getAmountOfPay() {
        return getTotalAmount()
                - getPromotionDiscountAmount()
                - getMembershipDiscountAmount();
    }

    public int getTotalBuyCount() {
        return purchaseInfos.getTotalBuyCount();
    }
}

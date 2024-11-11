package store.model.receipt;

import java.util.List;

public class PurchaseInfos {
    private final List<PurchaseInfo> purchaseInfos;

    public PurchaseInfos(List<PurchaseInfo> purchaseInfos) {
        this.purchaseInfos = purchaseInfos;
    }

    public List<PurchaseInfo> toList() {
        return List.copyOf(purchaseInfos);
    }

    public List<PurchaseInfo> toPromotionDiscountedList() {
        return purchaseInfos.stream()
                .filter(PurchaseInfo::isPromotionDiscounted)
                .toList();
    }

    public int getTotalBuyCount() {
        return purchaseInfos.stream()
                .mapToInt(PurchaseInfo::getBuyCount)
                .sum();
    }

    public int getTotalAmount() {
        return purchaseInfos.stream()
                .mapToInt(PurchaseInfo::getTotalAmount)
                .sum();
    }

    public int getPromotionDiscountAmount() {
        return toPromotionDiscountedList().stream()
                .mapToInt(PurchaseInfo::getPromotionDiscountAmount)
                .sum();
    }

    public int getNotPromotionDiscountAppliedProductsAmount() {
        return purchaseInfos.stream()
                .filter(PurchaseInfo::isNotPromotionDiscounted)
                .mapToInt(PurchaseInfo::getTotalAmount)
                .sum();
    }
}

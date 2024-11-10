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

    public List<PurchaseInfo> toDiscountedList() {
        return purchaseInfos.stream()
                .filter(PurchaseInfo::isDiscounted)
                .toList();
    }

    public int getTotalBuyCount() {
        return purchaseInfos.stream()
                .mapToInt(PurchaseInfo::getBuyCount)
                .sum();
    }

    public int getTotalPriceWithoutDiscount() {
        return purchaseInfos.stream()
                .mapToInt(PurchaseInfo::getTotalPriceWithoutDiscount)
                .sum();
    }

    public int getDiscountedPrice() {
        return toDiscountedList().stream()
                .mapToInt(PurchaseInfo::getDiscountPrice)
                .sum();
    }

    public int getNotDiscountedPrice() {
        return getTotalPriceWithoutDiscount()
                - getDiscountedPrice();
    }
}

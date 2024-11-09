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

    public int getTotalPrice() {
        return purchaseInfos.stream()
                .mapToInt(info -> info.getBuyCount() * info.getPrice())
                .sum();
    }

    public int getDiscountedPrice() {
        return toDiscountedList().stream()
                .mapToInt(info -> info.getFreeCount() * info.getPrice())
                .sum();
    }

    public int getNotDiscountedPrice() {
        return purchaseInfos.stream()
                .filter(PurchaseInfo::isNotDiscounted)
                .mapToInt(info -> info.getBuyCount() * info.getPrice())
                .sum();
    }
}

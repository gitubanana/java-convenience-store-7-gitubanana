package store.model.product;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PromotionProducts {
    private final Map<String, PromotionProduct> promotionProductByName;

    public PromotionProducts(Map<String, PromotionProduct> promotionProductByName) {
        this.promotionProductByName = promotionProductByName;
    }

    public PromotionProducts(List<PromotionProduct> products) {
        this(
                products.stream()
                        .collect(Collectors.toMap(PromotionProduct::getName, Function.identity()))
        );
    }

    public PromotionProduct getNameWith(String toFind) {
        return promotionProductByName.get(toFind);
    }

    public List<PromotionProduct> toList() {
        return promotionProductByName.values().stream().toList();
    }
}

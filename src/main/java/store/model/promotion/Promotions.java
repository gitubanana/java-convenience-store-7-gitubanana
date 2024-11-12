package store.model.promotion;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Promotions {
    private final Map<String, Promotion> promotionByName;

    public Promotions(Map<String, Promotion> promotionByName) {
        this.promotionByName = promotionByName;
    }

    public Promotions(List<Promotion> promotions) {
        this(
                promotions.stream()
                        .collect(Collectors.toMap(Promotion::getName, Function.identity()))
        );
    }

    public Promotion getNameWith(String toFind) {
        return promotionByName.get(toFind);
    }
}

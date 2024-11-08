package store.model.product;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Products {
    private final Map<String, Product> productByName;

    public Products(Map<String, Product> productByName) {
        this.productByName = productByName;
    }

    public Products(List<Product> products) {
        this(
                products.stream()
                        .collect(Collectors.toMap(Product::getName, Function.identity()))
        );
    }

    public Product getNameWith(String toFind) {
        return productByName.get(toFind);
    }

    public List<Product> toList() {
        return productByName.values().stream().toList();
    }
}

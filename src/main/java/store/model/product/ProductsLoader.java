package store.model.product;

import static store.constant.StoreInfo.NO_PROMOTION;
import static store.constant.StoreInfo.PRODUCTS_DELIMITER;

import java.util.ArrayList;
import java.util.List;
import store.model.promotion.Promotions;
import store.util.Converter;
import store.util.FileLines;
import store.util.Spliter;

// name,price,quantity,promotion 형식으로 읽는다.
public class ProductsLoader {
    private final List<Product> products;
    private final List<PromotionProduct> promotionProducts;

    public ProductsLoader(String file, Promotions promotions) {
        FileLines fileLines = new FileLines(file);
        fileLines.nextLine();

        products = new ArrayList<>();
        promotionProducts = new ArrayList<>();
        while (true) {
            String line = fileLines.nextLine();
            if (line == null) {
                break;
            }

            addProduct(line, promotions);
        }
        addProductsOnlyInPromotionProducts();
    }

    private void addProduct(String line, Promotions promotions) {
        Spliter spliter = new Spliter(line, PRODUCTS_DELIMITER);
        String name = spliter.nextToken();
        int price = Converter.toInteger(spliter.nextToken());
        int quantity = Converter.toInteger(spliter.nextToken());
        String promotionName = spliter.nextToken();

        if (promotionName.equals(NO_PROMOTION)) {
            products.add(new Product(name, price, quantity));
            return;
        }

        promotionProducts.add(new PromotionProduct(name, price, quantity, promotions.getNameWith(promotionName)));
    }

    private void addProductsOnlyInPromotionProducts() {
        List<String> productNames = products.stream().map(Product::getName).toList();

        promotionProducts.stream()
                .filter(promotionProduct -> !productNames.contains(promotionProduct.getName()))
                .forEach(promotionProduct -> products.add(
                        new Product(promotionProduct.getName(), promotionProduct.getPrice(), 0)));
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<PromotionProduct> getPromotionProducts() {
        return promotionProducts;
    }
}

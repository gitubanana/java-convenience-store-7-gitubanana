package store.view;

import static store.constant.OutputMessage.NEW_LINE;
import static store.constant.OutputMessage.NO_QUANTITY;
import static store.constant.OutputMessage.PRODUCT_FORMAT;
import static store.constant.OutputMessage.PROMOTION_PRODUCT_FORMAT;
import static store.constant.OutputMessage.QUANTITY_FORMAT;

import java.text.NumberFormat;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.PromotionProduct;
import store.model.product.PromotionProducts;

public class OutputView {
    public static final NumberFormat numberFormat = NumberFormat.getInstance();

    public static void println(String message) {
        System.out.printf(message);
        System.out.printf(NEW_LINE);
    }

    public static void printProducts(Products products) {
        for (Product product : products.toList()) {
            System.out.printf(
                    PRODUCT_FORMAT,
                    product.getName(),
                    numberFormat.format(product.getPrice()),
                    getQuantityMessage(product.getQuantity())
            );
        }
    }

    public static void printPromotionProducts(PromotionProducts promotionProducts) {
        for (PromotionProduct promotionProduct : promotionProducts.toList()) {
            System.out.printf(
                    PROMOTION_PRODUCT_FORMAT,
                    promotionProduct.getName(),
                    numberFormat.format(promotionProduct.getPrice()),
                    getQuantityMessage(promotionProduct.getQuantity()),
                    promotionProduct.getPromotionName()
            );
        }
    }

    private static String getQuantityMessage(int quantity) {
        if (quantity == 0) {
            return NO_QUANTITY;
        }

        return String.format(QUANTITY_FORMAT, quantity);
    }
}

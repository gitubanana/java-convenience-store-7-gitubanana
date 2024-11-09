package store.view;

import static store.constant.OutputInfo.BUY_COUNT_WITHOUT_PROMOTION_QUESTION;
import static store.constant.OutputInfo.FREE_GETTABLE_COUNT_QUESTION;
import static store.constant.OutputInfo.NEW_LINE;
import static store.constant.OutputInfo.NO_QUANTITY;
import static store.constant.OutputInfo.PRODUCT_FORMAT;
import static store.constant.OutputInfo.PROMOTION_PRODUCT_FORMAT;
import static store.constant.OutputInfo.QUANTITY_FORMAT;
import static store.constant.OutputInfo.RECEIPT_AMOUNT_OF_PAY_FORMAT;
import static store.constant.OutputInfo.RECEIPT_FREE_PRODUCT_FORMAT;
import static store.constant.OutputInfo.RECEIPT_FREE_START_MESSAGE;
import static store.constant.OutputInfo.RECEIPT_MEMBERSHIP_DISCOUNT_FORMAT;
import static store.constant.OutputInfo.RECEIPT_PAY_START_MESSAGE;
import static store.constant.OutputInfo.RECEIPT_PROMOTION_DISCOUNT_FORMAT;
import static store.constant.OutputInfo.RECEIPT_PURCHASE_INFO_FORMAT;
import static store.constant.OutputInfo.RECEIPT_PURCHASE_INFO_START_MESSAGE;
import static store.constant.OutputInfo.RECEIPT_START_MESSAGE;
import static store.constant.OutputInfo.RECEIPT_TOTAL_PRICE_FORMAT;

import java.text.NumberFormat;
import store.model.order.Order;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.PromotionProduct;
import store.model.product.PromotionProducts;
import store.model.receipt.PurchaseInfo;
import store.model.receipt.PurchaseInfos;
import store.model.receipt.Receipt;

public class OutputView {
    public static final NumberFormat number = NumberFormat.getInstance();

    public static void println(String message) {
        System.out.printf(message);
        System.out.printf(NEW_LINE);
    }

    public static void printProducts(Products products) {
        for (Product product : products.toList()) {
            System.out.printf(
                    PRODUCT_FORMAT,
                    product.getName(),
                    number.format(product.getPrice()),
                    getQuantityMessage(product.getQuantity())
            );
        }
    }

    public static void printPromotionProducts(PromotionProducts promotionProducts) {
        for (PromotionProduct promotionProduct : promotionProducts.toList()) {
            System.out.printf(
                    PROMOTION_PRODUCT_FORMAT,
                    promotionProduct.getName(),
                    number.format(promotionProduct.getPrice()),
                    getQuantityMessage(promotionProduct.getQuantity()),
                    promotionProduct.getPromotionName()
            );
        }
    }

    private static String getQuantityMessage(final int quantity) {
        if (quantity == 0) {
            return NO_QUANTITY;
        }
        return String.format(QUANTITY_FORMAT, quantity);
    }

    public static void printQuestionToFreeGettableCount(Order order, final int freeGettableCount) {
        System.out.printf(
                FREE_GETTABLE_COUNT_QUESTION,
                order.getProductName(),
                freeGettableCount
        );
    }

    public static void printQuestionToBuyCountWithoutPromotion(Order order, final int buyCountWithoutPromotion) {
        System.out.printf(
                BUY_COUNT_WITHOUT_PROMOTION_QUESTION,
                order.getProductName(),
                buyCountWithoutPromotion
        );
    }

}

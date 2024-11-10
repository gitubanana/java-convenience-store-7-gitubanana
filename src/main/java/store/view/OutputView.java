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
    public static final NumberFormat NUMBER = NumberFormat.getInstance();

    public static void println(String message) {
        System.out.printf(message);
        System.out.printf(NEW_LINE);
    }

    public static void printProducts(Products products) {
        for (Product product : products.toList()) {
            System.out.printf(
                    PRODUCT_FORMAT,
                    product.getName(),
                    NUMBER.format(product.getPrice()),
                    getQuantityMessage(product.getQuantity())
            );
        }
    }

    public static void printPromotionProducts(PromotionProducts promotionProducts) {
        for (PromotionProduct promotionProduct : promotionProducts.toList()) {
            System.out.printf(
                    PROMOTION_PRODUCT_FORMAT,
                    promotionProduct.getName(),
                    NUMBER.format(promotionProduct.getPrice()),
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

    public static void printReceipt(Receipt receipt) {
        System.out.printf(RECEIPT_START_MESSAGE);
        printPurchaseInfos(receipt.getPurchaseInfos());
        printFreePurchaseInfos(receipt.getPurchaseInfos());
        printPays(receipt);
    }

    private static void printPurchaseInfos(PurchaseInfos purchaseInfos) {
        System.out.printf(RECEIPT_PURCHASE_INFO_START_MESSAGE);
        for (PurchaseInfo purchaseInfo : purchaseInfos.toList()) {
            System.out.printf(
                    RECEIPT_PURCHASE_INFO_FORMAT,
                    purchaseInfo.getProductName(),
                    purchaseInfo.getBuyCount(),
                    NUMBER.format(purchaseInfo.getTotalPriceWithoutDiscount())
            );
        }
    }

    private static void printFreePurchaseInfos(PurchaseInfos purchaseInfos) {
        System.out.printf(RECEIPT_FREE_START_MESSAGE);
        for (PurchaseInfo purchaseInfo : purchaseInfos.toDiscountedList()) {
            System.out.printf(
                    RECEIPT_FREE_PRODUCT_FORMAT,
                    purchaseInfo.getProductName(),
                    purchaseInfo.getFreeCount()
            );
        }
    }

    private static void printPays(Receipt receipt) {
        System.out.printf(RECEIPT_PAY_START_MESSAGE);
        printTotalPrice(receipt);
        printPromotionDiscount(receipt);
        printMemberShipDiscount(receipt);
        printAmountOfPay(receipt);
    }

    private static void printTotalPrice(Receipt receipt) {
        System.out.printf(
                RECEIPT_TOTAL_PRICE_FORMAT,
                receipt.getTotalBuyCount(),
                NUMBER.format(receipt.getTotalPriceWithoutDiscount())
        );
    }

    private static void printPromotionDiscount(Receipt receipt) {
        System.out.printf(
                RECEIPT_PROMOTION_DISCOUNT_FORMAT,
                NUMBER.format(receipt.getDiscountPrice())
        );
    }

    private static void printMemberShipDiscount(Receipt receipt) {
        System.out.printf(
                RECEIPT_MEMBERSHIP_DISCOUNT_FORMAT,
                NUMBER.format(receipt.getMembershipDiscountAmount())
        );
    }

    private static void printAmountOfPay(Receipt receipt) {
        System.out.printf(
                RECEIPT_AMOUNT_OF_PAY_FORMAT,
                NUMBER.format(receipt.getAmountOfPay())
        );
    }
}

package store.controller;

import static store.constant.Answer.NO;
import static store.constant.Answer.YES;
import static store.constant.OutputInfo.MAY_I_TAKE_YOUR_ORDER;
import static store.constant.OutputInfo.MEMBERSHIP_DISCOUNT;
import static store.constant.OutputInfo.PRODUCT_START_MESSAGE;
import static store.constant.OutputInfo.WANNA_BUY_MORE;
import static store.constant.OutputInfo.WELCOME_MESSAGE;
import static store.constant.StoreInfo.PRODUCTS_FILE;
import static store.constant.StoreInfo.PROMOTIONS_FILE;

import store.constant.Answer;
import store.model.Store;
import store.model.order.Order;
import store.model.order.Orders;
import store.model.product.Products;
import store.model.product.ProductsLoader;
import store.model.product.PromotionProducts;
import store.model.promotion.Promotions;
import store.model.promotion.PromotionsLoader;
import store.model.receipt.PurchaseInfos;
import store.model.receipt.Receipt;
import store.util.Task;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    public void run() {
        Store store = loadFilesAndMakeStore();

        while (true) {
            takeOrdersAndSellProducts(store);
            if (answerToMoreOrders() == NO) {
                break;
            }
        }
    }

    private Store loadFilesAndMakeStore() {
        PromotionsLoader promotionsLoader = new PromotionsLoader(PROMOTIONS_FILE);
        ProductsLoader productsLoader = new ProductsLoader(PRODUCTS_FILE,
                new Promotions(promotionsLoader.getPromotions()));

        return new Store(
                new Products(productsLoader.getProducts()),
                new PromotionProducts(productsLoader.getPromotionProducts())
        );
    }

    private void takeOrdersAndSellProducts(Store store) {
        printWelcomeAndAllProducts(store);
        Orders orders = readAvailableOrders(store);

        readAnswerToFreeGettableCount(store, orders);
        readAnswerToBuyProductsWithoutPromotion(store, orders);
        Receipt receipt = new Receipt(
                answerToMembershipDiscount() == YES,
                sellAllProducts(store, orders)
        );

        OutputView.printReceipt(receipt);
    }

    private void printWelcomeAndAllProducts(Store store) {
        OutputView.println(WELCOME_MESSAGE);
        OutputView.println(PRODUCT_START_MESSAGE);
        OutputView.printProducts(store.getProducts());
        OutputView.printPromotionProducts(store.getPromotionProducts());
    }

    private Orders readAvailableOrders(Store store) {
        return Task.retryTillNoException(() -> {
            OutputView.println(MAY_I_TAKE_YOUR_ORDER);
            Orders orders = new Orders(InputView.readOrders());

            for (Order order : orders.toList()) {
                store.checkAvailability(order);
            }
            return orders;
        });
    }

    private void readAnswerToFreeGettableCount(Store store, Orders orders) {
        for (Order order : orders.toList()) {
            final int freeGettableCount = store.getFreeGettableCount(order);
            if (freeGettableCount == 0) {
                continue;
            }

            if (answerToGetFreeProducts(order, freeGettableCount) == YES) {
                order.add(freeGettableCount);
            }
        }
    }

    private Answer answerToGetFreeProducts(Order order, final int freeGettableCount) {
        return Task.retryTillNoException(() -> {
            OutputView.printQuestionToFreeGettableCount(order, freeGettableCount);
            return InputView.readAnswer();
        });
    }

    private void readAnswerToBuyProductsWithoutPromotion(Store store, Orders orders) {
        for (Order order : orders.toList()) {
            final int buyCountWithoutPromotion = store.getBuyCountWithoutPromotion(order);
            if (buyCountWithoutPromotion == 0) {
                continue;
            }

            if (answerToBuyWithoutPromotion(order, buyCountWithoutPromotion) == NO) {
                order.cancel(buyCountWithoutPromotion);
            }
        }
    }

    private Answer answerToBuyWithoutPromotion(Order order, final int buyCountWithoutPromotion) {
        return Task.retryTillNoException(() -> {
            OutputView.printQuestionToBuyCountWithoutPromotion(order, buyCountWithoutPromotion);
            return InputView.readAnswer();
        });
    }

    private Answer answerToMembershipDiscount() {
        return Task.retryTillNoException(() -> {
            OutputView.println(MEMBERSHIP_DISCOUNT);
            return InputView.readAnswer();
        });
    }

    private Answer answerToMoreOrders() {
        return Task.retryTillNoException(() -> {
            OutputView.println(WANNA_BUY_MORE);
            return InputView.readAnswer();
        });
    }

    private PurchaseInfos sellAllProducts(Store store, Orders orders) {
        return new PurchaseInfos(
                orders.toList().stream()
                        .map(store::sell)
                        .toList()
        );
    }
}

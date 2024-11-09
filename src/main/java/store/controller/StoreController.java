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

import java.util.ArrayList;
import java.util.List;
import store.constant.Answer;
import store.model.Store;
import store.model.order.Order;
import store.model.order.Orders;
import store.model.product.Products;
import store.model.product.ProductsLoader;
import store.model.product.PromotionProducts;
import store.model.promotion.Promotions;
import store.model.promotion.PromotionsLoader;
import store.model.receipt.PurchaseInfo;
import store.model.receipt.PurchaseInfos;
import store.model.receipt.Receipt;
import store.util.Task;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    public void run() {
        Store store = loadFilesAndMakeStore();

        while (true) {
            printWelcomeAndAllProducts(store);
            Orders orders = getOrders();
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

    private void printWelcomeAndAllProducts(Store store) {
        OutputView.println(WELCOME_MESSAGE);
        OutputView.println(PRODUCT_START_MESSAGE);
        OutputView.printProducts(store.getProducts());
        OutputView.printPromotionProducts(store.getPromotionProducts());
    }

    private Orders getOrders() {
    private Orders readAvailableOrders(Store store) {
        return Task.retryTillNoException(() -> {
            OutputView.println(MAY_I_TAKE_YOUR_ORDER.getMessage());
            return new Orders(InputView.readOrders());
            OutputView.println(MAY_I_TAKE_YOUR_ORDER);
            Orders orders = new Orders(InputView.readOrders());

            for (Order order : orders.toList()) {
                store.checkAvailability(order);
            }
            return orders;
        });
    }
        });
    }
}

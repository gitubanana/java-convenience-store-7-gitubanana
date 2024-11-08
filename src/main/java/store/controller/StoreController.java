package store.controller;

import static store.constant.FileInfo.PRODUCTS_FILE;
import static store.constant.FileInfo.PROMOTIONS_FILE;
import static store.constant.OutputMessage.WELCOME_MESSAGE;
import static store.constant.PromptMessage.MAY_I_TAKE_YOUR_ORDER;

import store.model.Store;
import store.model.order.Orders;
import store.model.product.Products;
import store.model.product.ProductsLoader;
import store.model.product.PromotionProducts;
import store.model.promotion.Promotions;
import store.model.promotion.PromotionsLoader;
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
        ProductsLoader productsLoader = new ProductsLoader(PRODUCTS_FILE);
        PromotionsLoader promotionsLoader = new PromotionsLoader(PROMOTIONS_FILE);

        return new Store(
                new Promotions(promotionsLoader.getPromotions()),
                new Products(productsLoader.getProducts()),
                new PromotionProducts(productsLoader.getPromotionProducts())
        );
    }

    private void printWelcomeAndAllProducts(Store store) {
        OutputView.println(WELCOME_MESSAGE);
        OutputView.printProducts(store.getProducts());
        OutputView.printPromotionProducts(store.getPromotionProducts());
    }

    private Orders getOrders() {
        return Task.retryTillNoException(() -> {
            OutputView.println(MAY_I_TAKE_YOUR_ORDER.getMessage());
            return new Orders(InputView.readOrders());
        });
    }
}

package store.view;

import static store.constant.ErrorMessage.INVALID_ORDER_FORMAT;
import static store.constant.OrderInfo.ORDER_DELIMITER;
import static store.constant.OrderInfo.ORDER_PATTERN;
import static store.constant.OrderInfo.PRODUCT_COUNT_GROUP;
import static store.constant.OrderInfo.PRODUCT_NAME_GROUP;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import store.model.order.Order;
import store.util.Spliter;

public class InputView {
    public static List<Order> readOrders() {
        Spliter spliter = new Spliter(Console.readLine(), ORDER_DELIMITER);
        List<Order> orders = new ArrayList<>();

        while (spliter.hasMoreToken()) {
            Matcher matcher = ORDER_PATTERN.matcher(spliter.nextToken());
            if (!matcher.matches()) {
                throw new IllegalArgumentException(INVALID_ORDER_FORMAT.getMessage());
            }

            orders.add(
                    new Order(
                            matcher.group(PRODUCT_NAME_GROUP),
                            Integer.parseInt(matcher.group(PRODUCT_COUNT_GROUP))
                    )
            );
        }

        return orders;
    }
}

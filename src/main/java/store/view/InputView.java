package store.view;

import static store.constant.ErrorMessage.INVALID_ORDER_FORMAT;
import static store.constant.ErrorMessage.WRONG_INPUT;
import static store.constant.OrderInfo.ORDER_DELIMITER;
import static store.constant.OrderInfo.ORDER_PATTERN;
import static store.constant.OrderInfo.PRODUCT_COUNT_GROUP;
import static store.constant.OrderInfo.PRODUCT_NAME_GROUP;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import store.constant.Answer;
import store.model.order.Order;
import store.util.Converter;
import store.util.Spliter;

public class InputView {
    public static List<Order> readOrders() {
        Spliter spliter = new Spliter(Console.readLine(), ORDER_DELIMITER);
        List<Order> orders = new ArrayList<>();

        while (spliter.hasMoreTokens()) {
            String nextToken = spliter.nextToken();

            orders.add(makeOrder(nextToken));
        }
        return orders;
    }

    private static Order makeOrder(String order) {
        Matcher matcher = ORDER_PATTERN.matcher(order);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(INVALID_ORDER_FORMAT.getMessage());
        }

        return new Order(
                matcher.group(PRODUCT_NAME_GROUP),
                Converter.toInteger(matcher.group(PRODUCT_COUNT_GROUP))
        );
    }

    public static Answer readAnswer() {
        Answer answer = Answer.getMatchingAnswer(Console.readLine());

        if (answer == null) {
            throw new IllegalArgumentException(WRONG_INPUT.getMessage());
        }
        return answer;
    }
}

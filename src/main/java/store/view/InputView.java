package store.view;

import static store.constant.ErrorMessage.WRONG_INPUT;
import static store.constant.OrderInfo.ORDER_DELIMITER;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
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

            orders.add(Converter.toOrder(nextToken));
        }
        return orders;
    }

    public static Answer readAnswer() {
        Answer answer = Answer.getMatchingAnswer(Console.readLine());

        if (answer == null) {
            throw new IllegalArgumentException(WRONG_INPUT.getMessage());
        }
        return answer;
    }
}

package store.util;

import static store.constant.ErrorMessage.INVALID_ORDER_FORMAT;
import static store.constant.ErrorMessage.WRONG_INPUT;
import static store.constant.OrderInfo.ORDER_PATTERN;
import static store.constant.OrderInfo.PRODUCT_COUNT_GROUP;
import static store.constant.OrderInfo.PRODUCT_NAME_GROUP;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import store.model.order.Order;

public class Converter {
    public static int toInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(WRONG_INPUT.getMessage());
        }
    }

    public static LocalDateTime toLocalDateTime(String localDate, String dateFormat) {
        return LocalDate.parse(
                        localDate,
                        DateTimeFormatter.ofPattern(dateFormat)
                )
                .atStartOfDay();
    }

    public static Order toOrder(String order) {
        Matcher matcher = ORDER_PATTERN.matcher(order);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(INVALID_ORDER_FORMAT.getMessage());
        }

        return new Order(
                matcher.group(PRODUCT_NAME_GROUP),
                Converter.toInteger(matcher.group(PRODUCT_COUNT_GROUP))
        );
    }
}

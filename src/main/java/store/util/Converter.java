package store.util;

import static store.constant.ErrorMessage.WRONG_INPUT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {
    public static int toInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(WRONG_INPUT.getMessage());
        }
    }

    public static LocalDateTime toLocalDateTime(String localDate, String dateFormat) {
        return LocalDateTime.of(
                LocalDate.parse(
                        localDate,
                        DateTimeFormatter.ofPattern(dateFormat)
                ),
                LocalDateTime.MIN.toLocalTime()
        );
    }
}

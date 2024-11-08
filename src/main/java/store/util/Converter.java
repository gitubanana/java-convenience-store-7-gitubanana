package store.util;

import static store.constant.ErrorMessage.NOT_A_NUMBER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {
    private Converter() {
    }

    public static int toInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_A_NUMBER.getMessage());
        }
    }

    public static LocalDateTime toLocalDateTime(String localDate, String dateFormat) {
        return LocalDateTime.of(
                LocalDate.parse( // 예외 가능
                        localDate,
                        DateTimeFormatter.ofPattern(dateFormat)
                ),
                LocalDateTime.MIN.toLocalTime()
        );
    }
}

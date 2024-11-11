package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static store.constant.ErrorMessage.INVALID_ORDER_FORMAT;
import static store.constant.ErrorMessage.WRONG_INPUT;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.order.Order;

@DisplayName("== Converter 테스트 ==")
public class ConverterTest {
    @ParameterizedTest
    @DisplayName("문자열을 정수로 바꿀 수 있다.")
    @MethodSource("getToIntegerArguments")
    void 문자열을_정수로_변경(String string, int integer) {
        assertThat(Converter.toInteger(string))
                .isEqualTo(integer);
    }

    static Stream<Arguments> getToIntegerArguments() {
        return Stream.of(
                Arguments.of("0", 0),
                Arguments.of("1", 1),
                Arguments.of("23", 23),
                Arguments.of("42", 42),
                Arguments.of("-1", -1),
                Arguments.of("-100", -100)
        );
    }

    @ParameterizedTest
    @DisplayName("문자열이 정수가 아닐 경우 예외를 발생시킬 수 있다.")
    @ValueSource(strings = {"hi", "1e9", "   "})
    void 정수가_아닐_경우_예외(String string) {
        assertThatThrownBy(() -> Converter.toInteger(string))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(WRONG_INPUT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("문자열을 LocalDateTime으로 변경할 수 있다.")
    @MethodSource("getToLocalDateTimeArguments")
    void 문자열을_LocalDateTime으로_변경(String localDate, final int year, final int month, final int day) {
        final String DATE_FORMAT = "yyyy/MM/dd";
        LocalDateTime localDateTime = Converter.toLocalDateTime(localDate, DATE_FORMAT);

        assertAll(
                () -> assertThat(localDateTime.getYear()).isEqualTo(year),
                () -> assertThat(localDateTime.getMonthValue()).isEqualTo(month),
                () -> assertThat(localDateTime.getDayOfMonth()).isEqualTo(day)
        );
    }

    static Stream<Arguments> getToLocalDateTimeArguments() {
        return Stream.of(
                Arguments.of("1919/03/01", 1919, 3, 1),
                Arguments.of("1945/08/15", 1945, 8, 15),
                Arguments.of("1953/07/27", 1953, 7, 27),
                Arguments.of("2024/11/11", 2024, 11, 11)
        );
    }

    @ParameterizedTest
    @DisplayName("주문 형식에 맞지 않을 경우 예외를 발생시킬 수 있다.")
    @ValueSource(strings = {"[]", "[hi-hi]", ""})
    void 주문_형식_예외(String order) {
        assertThatThrownBy(() -> Converter.toOrder(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_ORDER_FORMAT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("주문 제품과 수량을 파싱할 수 있다.")
    @MethodSource("getToOrderArguments")
    void 제품_수량_파싱(String orderString, String productName, final int buyCount) {
        Order order = Converter.toOrder(orderString);

        assertAll(
                () -> {
                    assertThat(order.getProductName())
                            .isEqualTo(productName);
                },
                () -> {
                    assertThat(order.getBuyCount())
                            .isEqualTo(buyCount);
                }
        );
    }

    static Stream<Arguments> getToOrderArguments() {
        return Stream.of(
                Arguments.of("[껌-5]", "껌", 5),
                Arguments.of("[김밥-7]", "김밥", 7),
                Arguments.of("[콜라-10]", "콜라", 10)
        );
    }
}

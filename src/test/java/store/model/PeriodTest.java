package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ErrorMessage.INVALID_PERIOD;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.model.promotion.Period;

@DisplayName("== Period 테스트 ==")
public class PeriodTest {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

    static LocalDateTime toLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDate localDate = LocalDate.parse(date, formatter);

        return LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime());
    }

    @Nested
    @DisplayName("-- 기능 테스트 --")
    class FeatureTest {
        @ParameterizedTest
        @DisplayName("기간이 시간을 포함하고 있는지 확인할 수 있다.")
        @MethodSource("getIncludeArguments")
        void 기간이_시간_포함하는지_확인(String start, String end, String time, final boolean expected) {
            Period period = new Period(toLocalDateTime(start), toLocalDateTime(end));

            assertThat(period.includes(toLocalDateTime(time)))
                    .isEqualTo(expected);
        }

        static Stream<Arguments> getIncludeArguments() {
            return Stream.of(
                    Arguments.of("2024-01-01", "2024-01-02", "2024-01-01", true),
                    Arguments.of("2024-01-01", "2024-01-02", "2024-01-02", true),
                    Arguments.of("2024-01-01", "2024-12-31", "2024-11-11", true),
                    Arguments.of("2024-01-01", "2024-12-31", "2025-01-01", false)
            );
        }
    }

    @Nested
    @DisplayName("-- 예외 테스트 --")
    class ExceptionTest {
        @ParameterizedTest
        @DisplayName("잘못된 기간은 예외처리할 수 있다.")
        @MethodSource("getInvalidPeriodArguments")
        void 잘못된_기간_예외처리(String start, String end) {
            assertThatThrownBy(() -> new Period(toLocalDateTime(start), toLocalDateTime(end)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INVALID_PERIOD.getMessage());
        }

        static Stream<Arguments> getInvalidPeriodArguments() {
            return Stream.of(
                    Arguments.of("2024-11-11", "2002-06-22"),
                    Arguments.of("2024-11-11", "2024-11-10"),
                    Arguments.of("2024-11-11", "2024-11-11")
            );
        }
    }
}

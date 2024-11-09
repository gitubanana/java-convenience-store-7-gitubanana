package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ErrorMessage.BUY_COUNT_NOT_POSITIVE;
import static store.constant.ErrorMessage.GET_COUNT_NOT_POSITIVE;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.promotion.FreePolicy;

@DisplayName("== FreePolicy 테스트 ==")
public class FreePolicyTest {
    @Nested
    @DisplayName("-- 기능 테스트 --")
    class FeatureTest {
        @ParameterizedTest
        @DisplayName("고객이 구매한 개수를 토대로 더 증정받을 수 있는 개수를 구할 수 있다.")
        @MethodSource("getCalculateGettableCountArguments")
        void 더_증정_개수(final int buyCount, final int getCount, final int customerBuyCount, final int expected) {
            FreePolicy freePolicy = new FreePolicy(buyCount, getCount);

            assertThat(freePolicy.calculateGettableCount(customerBuyCount))
                    .isEqualTo(expected);
        }

        static Stream<Arguments> getCalculateGettableCountArguments() {
            return Stream.of(
                    Arguments.of(1, 1, 1, 1),
                    Arguments.of(1, 1, 2, 0),
                    Arguments.of(1, 1, 3, 1),
                    Arguments.of(1, 2, 1, 2),
                    Arguments.of(1, 2, 2, 1),
                    Arguments.of(1, 2, 3, 0),
                    Arguments.of(2, 1, 1, 0),
                    Arguments.of(2, 1, 2, 1),
                    Arguments.of(2, 1, 3, 0),
                    Arguments.of(3, 1, 1, 0),
                    Arguments.of(3, 1, 2, 0),
                    Arguments.of(3, 1, 3, 1),
                    Arguments.of(3, 1, 4, 0),
                    Arguments.of(3, 1, 5, 0),
                    Arguments.of(3, 1, 6, 0),
                    Arguments.of(3, 1, 7, 1),
                    Arguments.of(1, 5, 1, 5),
                    Arguments.of(1, 5, 2, 4),
                    Arguments.of(1, 5, 3, 3),
                    Arguments.of(1, 5, 4, 2),
                    Arguments.of(1, 5, 5, 1),
                    Arguments.of(1, 5, 6, 0)
            );
        }

        @ParameterizedTest
        @DisplayName("고객이 구매한 개수를 토대로 증정되는 개수를 구할 수 있다.")
        @MethodSource("getCalculateFreeCountInArguments")
        void 증정되는_개수(final int buyCount, final int getCount, final int customerBuyCount, final int expected) {
            FreePolicy freePolicy = new FreePolicy(buyCount, getCount);

            assertThat(freePolicy.calculateFreeCountIn(customerBuyCount))
                    .isEqualTo(expected);
        }

        static Stream<Arguments> getCalculateFreeCountInArguments() {
            return Stream.of(
                    Arguments.of(1, 1, 1, 0),
                    Arguments.of(1, 1, 2, 1),
                    Arguments.of(1, 1, 3, 1),
                    Arguments.of(1, 1, 4, 2),
                    Arguments.of(2, 1, 1, 0),
                    Arguments.of(2, 1, 2, 0),
                    Arguments.of(2, 1, 3, 1),
                    Arguments.of(2, 1, 4, 1),
                    Arguments.of(1, 2, 1, 0),
                    Arguments.of(1, 2, 2, 1),
                    Arguments.of(1, 2, 3, 2),
                    Arguments.of(1, 2, 4, 2),
                    Arguments.of(1, 2, 5, 3),
                    Arguments.of(1, 2, 6, 4),
                    Arguments.of(1, 2, 7, 4)
            );
        }
    }

    @Nested
    @DisplayName("-- 예외 테스트 --")
    class ExceptionTest {
        @ParameterizedTest
        @DisplayName("증정 조건이 양수가 아니라면 예외를 발생시킬 수 있다.")
        @ValueSource(ints = {-5, -1, 0})
        void 양수_아닌_증정_조건(final int buyCount) {
            final int dummyGetCount = 1;

            assertThatThrownBy(() -> new FreePolicy(buyCount, dummyGetCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(BUY_COUNT_NOT_POSITIVE.getMessage());
        }

        @ParameterizedTest
        @DisplayName("증정 개수가 양수가 아니라면 예외를 발생시킬 수 있다.")
        @ValueSource(ints = {-42, -7, 0})
        void 양수_아닌_증정_개수(final int getCount) {
            final int dummyBuyCount = 1;

            assertThatThrownBy(() -> new FreePolicy(dummyBuyCount, getCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(GET_COUNT_NOT_POSITIVE.getMessage());
        }
    }
}

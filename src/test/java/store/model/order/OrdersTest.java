package store.model.order;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ErrorMessage.WRONG_INPUT;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("== Orders 테스트 ==")
public class OrdersTest {
    static final int DUMMY_BUY_COUNT = 1;

    @ParameterizedTest
    @DisplayName("상품명이 중복된 주문이 있을 경우 예외를 발생시킨다.")
    @MethodSource("getDuplicatedProductNameArguments")
    void 상품명_중복(List<Order> orders) {
        assertThatThrownBy(() -> new Orders(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(WRONG_INPUT.getMessage());
    }

    static Stream<Arguments> getDuplicatedProductNameArguments() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Order("삼각김밥", DUMMY_BUY_COUNT),
                                new Order("삼각김밥", DUMMY_BUY_COUNT)
                        )
                ),
                Arguments.of(
                        List.of(
                                new Order("알밥", DUMMY_BUY_COUNT),
                                new Order("알밥", DUMMY_BUY_COUNT)
                        )
                ),
                Arguments.of(
                        List.of(
                                new Order("알로에", DUMMY_BUY_COUNT),
                                new Order("알로에", DUMMY_BUY_COUNT),
                                new Order("껌", DUMMY_BUY_COUNT)
                        )
                )
        );
    }
}

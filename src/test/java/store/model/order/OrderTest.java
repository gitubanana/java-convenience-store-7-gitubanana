package store.model.order;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ErrorMessage.WRONG_INPUT;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("== Order 테스트 ==")
public class OrderTest {
    @ParameterizedTest
    @DisplayName("주문 수량이 음수일 경우 예외를 발생시킬 수 있다.")
    @ValueSource(ints = {-1, -2, -3})
    void 음수_주문_수량(final int buyCount) {
        final String DUMMY_NAME = "삼각김밥";

        assertThatThrownBy(() -> new Order(DUMMY_NAME, buyCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(WRONG_INPUT.getMessage());
    }
}

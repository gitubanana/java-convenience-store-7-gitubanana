package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static store.constant.ErrorMessage.CANNOT_FIND_PRODUCT;
import static store.constant.ErrorMessage.EXCEEDED_QUANTITY;
import static store.constant.StoreInfo.PROMOTIONS_DATE_FORMAT;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.model.order.Order;
import store.model.product.Product;
import store.model.product.Products;
import store.model.product.PromotionProduct;
import store.model.product.PromotionProducts;
import store.model.promotion.FreePolicy;
import store.model.promotion.Period;
import store.model.promotion.Promotion;
import store.util.Converter;

@DisplayName("== Store 테스트 ==")
public class StoreTest {
    static Store store;
    static final Promotion ONE_PLUS_ONE = new Promotion(
            "1+1",
            new FreePolicy(1, 1),
            new Period(
                    Converter.toLocalDateTime("2024-10-15", PROMOTIONS_DATE_FORMAT),
                    Converter.toLocalDateTime("2024-11-11", PROMOTIONS_DATE_FORMAT)
            )
    );
    static final Promotion ONE_PLUS_TWO = new Promotion(
            "1+2",
            new FreePolicy(1, 2),
            new Period(
                    Converter.toLocalDateTime("2024-11-10", PROMOTIONS_DATE_FORMAT),
                    Converter.toLocalDateTime("2024-11-11", PROMOTIONS_DATE_FORMAT)
            )
    );
    static final Promotion TWO_PLUS_ONE = new Promotion(
            "2+1",
            new FreePolicy(2, 1),
            new Period(
                    Converter.toLocalDateTime("2002-01-01", PROMOTIONS_DATE_FORMAT),
                    Converter.toLocalDateTime("2002-12-31", PROMOTIONS_DATE_FORMAT)
            )
    );

    @BeforeEach
    void initStore() {
        store = new Store(
                new Products(
                        List.of(
                                new Product("사이다", 1_000, 1),
                                new Product("삼각김밥", 2_000, 5),
                                new Product("정식도시락", 10_000, 10)
                        )
                ),
                new PromotionProducts(
                        List.of(
                                new PromotionProduct("사이다", 1_000, 3, ONE_PLUS_ONE),
                                new PromotionProduct("삼각김밥", 2_000, 6, ONE_PLUS_TWO),
                                new PromotionProduct("정식도시락", 10_000, 5, TWO_PLUS_ONE)
                        )
                )
        );
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 제품의 주문은 예외를 발생시킬 수 있다.")
    @MethodSource("getCannotFindProductArguments")
    void 존재하지_않는_제품_예외(Order order) {
        assertThatThrownBy(() -> store.checkAvailability(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CANNOT_FIND_PRODUCT.getMessage());
    }

    static Stream<Order> getCannotFindProductArguments() {
        return Stream.of(
                new Order("허니버터칩", 10),
                new Order("고양이", 1),
                new Order("개발자", 100)
        );
    }

    @ParameterizedTest
    @DisplayName("구매 수량이 재고 수량을 초과한 경우 예외를 발생시킬 수 있다.")
    @MethodSource("getExceededQuantityArguments")
    void 재고_수량_초과(Order order) {
        assertNowTest(
                () -> {
                    assertThatThrownBy(() -> store.checkAvailability(order))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage(EXCEEDED_QUANTITY.getMessage());
                },
                LocalDate.of(2024, 11, 11).atStartOfDay()
        );
    }

    static Stream<Order> getExceededQuantityArguments() {
        return Stream.of(
                new Order("사이다", 5),
                new Order("삼각김밥", 12),
                new Order("정식도시락", 11) // 프로모션 기간이 아닌 프로모션 제품은 재고로 판단하지 않는다.
        );
    }

    @ParameterizedTest
    @DisplayName("더 증정받을 수 있는 개수를 가져올 수 있다.")
    @MethodSource("getFreeGettableCountArguments")
    void 더_증정받는_개수(Order order, final int expected) {
        assertNowTest(
                () -> {
                    assertThat(store.getFreeGettableCount(order)).
                            isEqualTo(expected);
                },
                LocalDate.of(2024, 11, 11).atStartOfDay()
        );
    }

    static Stream<Arguments> getFreeGettableCountArguments() {
        return Stream.of(
                Arguments.of(
                        new Order("사이다", 1),
                        1
                ),
                Arguments.of(
                        new Order("삼각김밥", 1),
                        2
                ),
                Arguments.of(
                        new Order("삼각김밥", 2),
                        1
                ),
                Arguments.of(
                        new Order("정식도시락", 2), // 프로모션 기간이 아니다.
                        0
                )
        );
    }

    @ParameterizedTest
    @DisplayName("프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 정가로 계산해야하는 일부 수량을 구할 수 있다.")
    @MethodSource("getBuyCountWithoutPromotionArguments")
    void 정가_일부_수량(Order order, final int expected) {
        assertNowTest(
                () -> {
                    assertThat(store.getBuyCountWithoutPromotion(order)).
                            isEqualTo(expected);
                },
                LocalDate.of(2024, 11, 11).atStartOfDay()
        );
    }

    static Stream<Arguments> getBuyCountWithoutPromotionArguments() {
        return Stream.of(
                Arguments.of(
                        new Order("사이다", 4),
                        2
                ),
                Arguments.of(
                        new Order("삼각김밥", 7),
                        1
                ),
                Arguments.of(
                        new Order("정식도시락", 5), // 프로모션 기간이 아니다.
                        0
                )
        );
    }

    @ParameterizedTest
    @DisplayName("재고를 차감함으로써 시스템은 최신 재고 상태를 유지할 수 있다.")
    @MethodSource("getSellArguments")
    void 재고_차감(String productName, final int buyCount, final int remainProductCount,
               final int remainPromotionProductCount) {
        Order order = new Order(productName, buyCount);
        Products products = store.getProducts();
        PromotionProducts promotionProducts = store.getPromotionProducts();

        assertNowTest(
                () -> {
                    store.sell(order);
                    assertAll(
                            () -> {
                                assertThat(products.getNameWith(productName).getQuantity())
                                        .isEqualTo(remainProductCount);
                            },
                            () -> {
                                assertThat(promotionProducts.getNameWith(productName).getQuantity())
                                        .isEqualTo(remainPromotionProductCount);
                            }
                    );
                },
                LocalDate.of(2024, 11, 11).atStartOfDay()
        );
    }

    static Stream<Arguments> getSellArguments() {
        return Stream.of(
                Arguments.of("사이다", 2, 1, 1),
                Arguments.of("삼각김밥", 8, 3, 0),
                Arguments.of("정식도시락", 10, 0, 5)
        );
    }
}

package store.model.receipt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("== PurchaseInfos 테스트 ==")
public class PurchaseInfosTest {
    private static final int DUMMY_NUMBER = 1;

    @ParameterizedTest
    @DisplayName("전체 상품 개수를 구할 수 있다.")
    @MethodSource("getTotalBuyCountArguments")
    void 전체_상품_개수(List<PurchaseInfo> purchaseInfoList, final int expected) {
        PurchaseInfos purchaseInfos = new PurchaseInfos(purchaseInfoList);

        assertThat(purchaseInfos.getTotalBuyCount())
                .isEqualTo(expected);
    }

    static Stream<Arguments> getTotalBuyCountArguments() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new PurchaseInfo("라면", 1, DUMMY_NUMBER, DUMMY_NUMBER),
                                new PurchaseInfo("김치", 2, DUMMY_NUMBER, DUMMY_NUMBER)
                        ),
                        3
                ),
                Arguments.of(
                        List.of(
                                new PurchaseInfo("콜라", 3, DUMMY_NUMBER, DUMMY_NUMBER),
                                new PurchaseInfo("에너지바", 5, DUMMY_NUMBER, DUMMY_NUMBER)
                        ),
                        8
                ),
                Arguments.of(
                        List.of(
                                new PurchaseInfo("콜라", 10, DUMMY_NUMBER, DUMMY_NUMBER)
                        ),
                        10
                )
        );
    }

    @ParameterizedTest
    @DisplayName("총금액을 구할 수 있다.")
    @MethodSource("getTotalAmountArguments")
    void 총금액(List<PurchaseInfo> purchaseInfoList, final int expected) {
        PurchaseInfos purchaseInfos = new PurchaseInfos(purchaseInfoList);

        assertThat(purchaseInfos.getTotalAmount())
                .isEqualTo(expected);
    }

    static Stream<Arguments> getTotalAmountArguments() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new PurchaseInfo("라면", 1, 1_000, DUMMY_NUMBER),
                                new PurchaseInfo("김치", 2, 500, DUMMY_NUMBER)
                        ),
                        2_000
                ),
                Arguments.of(
                        List.of(
                                new PurchaseInfo("콜라", 3, 1_000, DUMMY_NUMBER),
                                new PurchaseInfo("에너지바", 5, 2_000, DUMMY_NUMBER)
                        ),
                        13_000
                ),
                Arguments.of(
                        List.of(
                                new PurchaseInfo("콜라", 10, 1_000, DUMMY_NUMBER)
                        ),
                        10_000
                )
        );
    }

    @ParameterizedTest
    @DisplayName("할인된 금액을 구할 수 있다.")
    @MethodSource("getDiscountedPriceArguments")
    void 할인된_금액(List<PurchaseInfo> purchaseInfoList, final int expected) {
        PurchaseInfos purchaseInfos = new PurchaseInfos(purchaseInfoList);

        assertThat(purchaseInfos.getPromotionDiscountAmount())
                .isEqualTo(expected);
    }

    static Stream<Arguments> getDiscountedPriceArguments() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new PurchaseInfo("라면", DUMMY_NUMBER, 1_000, 1),
                                new PurchaseInfo("김치", DUMMY_NUMBER, 500, 2)
                        ),
                        2_000
                ),
                Arguments.of(
                        List.of(
                                new PurchaseInfo("콜라", DUMMY_NUMBER, 1_000, 3),
                                new PurchaseInfo("에너지바", DUMMY_NUMBER, 2_000, 0)
                        ),
                        3_000
                ),
                Arguments.of(
                        List.of(
                                new PurchaseInfo("콜라", DUMMY_NUMBER, 1_000, 10)
                        ),
                        10_000
                )
        );
    }
}

package store.model.receipt;

import static org.assertj.core.api.Assertions.assertThat;
import static store.constant.StoreInfo.MAX_MEMBERSHIP_DISCOUNT_AMOUNT;
import static store.constant.StoreInfo.MEMBERSHIP_DISCOUNT_PERCENTAGE;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("== Receipt 테스트 ==")
public class ReceiptTest {
    @ParameterizedTest
    @DisplayName("멤버십 할인금액을 구할 수 있다. (최대 8,000원)")
    @MethodSource("getMembershipDiscountAmountArguments")
    void 멤버십_할인금액(final boolean applyMembershipDiscount, PurchaseInfos purchaseInfos, final int expected) {
        Receipt receipt = new Receipt(applyMembershipDiscount, purchaseInfos);

        assertThat(receipt.getMembershipDiscountAmount())
                .isEqualTo(expected);
    }

    static Stream<Arguments> getMembershipDiscountAmountArguments() {
        return Stream.of(
                Arguments.of(
                        false,
                        new PurchaseInfos(
                                List.of(
                                        new PurchaseInfo("콜라", 3, 1_000, 1),
                                        new PurchaseInfo("에너지바", 5, 2_000, 0)
                                )
                        ),
                        0
                ),
                Arguments.of(
                        true,
                        new PurchaseInfos(
                                List.of(
                                        new PurchaseInfo("콜라", 3, 1_000, 1),
                                        new PurchaseInfo("에너지바", 5, 2_000, 0)
                                )
                        ),
                        (int) ((5 * 2_000) * MEMBERSHIP_DISCOUNT_PERCENTAGE)
                ),
                Arguments.of(
                        true,
                        new PurchaseInfos(
                                List.of(
                                        new PurchaseInfo("콜라", 10, 1_000, 2)
                                )
                        ),
                        0
                ),
                Arguments.of(
                        true,
                        new PurchaseInfos(
                                List.of(
                                        new PurchaseInfo("물", 2, 500, 0),
                                        new PurchaseInfo("정식도시락", 1, 6_400, 0)
                                )
                        ),
                        (int) ((2 * 500 + 1 * 6_400) * MEMBERSHIP_DISCOUNT_PERCENTAGE)
                ),
                Arguments.of(
                        true,
                        new PurchaseInfos(
                                List.of(
                                        new PurchaseInfo("정식도시락", 8, 6_400, 0)
                                )
                        ),
                        MAX_MEMBERSHIP_DISCOUNT_AMOUNT
                )
        );
    }
}

package store.model.promotion;

import static store.constant.ErrorMessage.BUY_COUNT_NOT_POSITIVE;
import static store.constant.ErrorMessage.GET_COUNT_NOT_POSITIVE;

// Buy {buyCount}, Get {getCount} free
public class FreePolicy {
    private final int buyCount;
    private final int getCount;

    public FreePolicy(final int buyCount, final int getCount) {
        validate(buyCount, getCount);
        this.buyCount = buyCount;
        this.getCount = getCount;
    }

    private void validate(final int buyCount, final int getCount) {
        validateBuyCountIsPositive(buyCount);
        validateGetCountIsPositive(getCount);
    }

    private void validateBuyCountIsPositive(final int buyCount) {
        if (buyCount <= 0) {
            throw new IllegalArgumentException(BUY_COUNT_NOT_POSITIVE.getMessage());
        }
    }

    private void validateGetCountIsPositive(final int getCount) {
        if (getCount <= 0) {
            throw new IllegalArgumentException(GET_COUNT_NOT_POSITIVE.getMessage());
        }
    }

    // customerBuyCount에서 더 증정할 수 있는 개수 구하기
    public int calculateGettableCount(final int customerBuyCount) {
        final int freeUnit = buyCount + getCount;
        final int toGetDiscount = customerBuyCount % freeUnit;

        if (toGetDiscount < buyCount) {
            return 0;
        }

        final int alreadyGotCount = toGetDiscount - buyCount;

        return getCount - alreadyGotCount;
    }

    // customerBuyCount안에 몇 개가 증정되는지 구하기
    public int calculateFreeCountIn(final int customerBuyCount) {
        final int freeUnit = buyCount + getCount;
        final int discountedCount = customerBuyCount / freeUnit;
        final int toGetDiscount = customerBuyCount % freeUnit;
        int freeCount = getCount * discountedCount;

        if (toGetDiscount >= buyCount) {
            freeCount += toGetDiscount - buyCount;
        }

        return freeCount;
    }

    public int calculateFreePolicyEffectCount(final int customerBuyCount) {
        final int freeUnit = buyCount + getCount;
        final int discountedCount = customerBuyCount / freeUnit;

        return freeUnit * discountedCount;
    }
}

package store.model.promotion;

import camp.nextstep.edu.missionutils.DateTimes;

public class Promotion {
    private final String name;
    private final Period period;
    private final FreePolicy freePolicy;

    public Promotion(String name, FreePolicy freePolicy, Period period) {
        this.name = name;
        this.period = period;
        this.freePolicy = freePolicy;
    }

    public String getName() {
        return name;
    }

    public boolean isInProgress() {
        return period.includes(DateTimes.now());
    }

    public int getFreeCountIn(final int buyCount) {
        return freePolicy.calculateFreeCountIn(buyCount);
    }

    public int getFreeGettableCount(final int buyCount) {
        return freePolicy.calculateGettableCount(buyCount);
    }

    public int getFreePolicyEffectCount(final int buyCount) {
        return freePolicy.calculateFreePolicyEffectCount(buyCount);
    }
}

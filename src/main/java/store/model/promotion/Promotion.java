package store.model.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import store.model.FreePolicy;
import store.model.Period;

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

    public int getFreeCountIn(int buyCount) {
        return freePolicy.calculateFreeCountIn(buyCount);
    }

    public int getGettableFreeCount(int buyCount) {
        return freePolicy.calculateFreeCountIn(buyCount);
    }
}

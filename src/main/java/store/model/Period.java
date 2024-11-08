package store.model;

import static store.constant.ErrorMessage.INVALID_PERIOD;

import java.time.LocalDateTime;

public class Period {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Period(LocalDateTime start, LocalDateTime end) {
        validate(start, end);
        this.start = start;
        this.end = end;
    }

    private void validate(LocalDateTime start, LocalDateTime end) {
        validateStartIsBeforeEnd(start, end);
    }

    private void validateStartIsBeforeEnd(LocalDateTime start, LocalDateTime end) {
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException(INVALID_PERIOD.getMessage());
        }
    }

    public boolean includes(LocalDateTime time) {
        if (start.isEqual(time) || end.isEqual(time)) { // 시작과 끝 시간까지 포함
            return true;
        }

        return time.isAfter(start) && time.isBefore(end);
    }
}

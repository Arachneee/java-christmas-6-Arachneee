package christmas.constant;

import christmas.domain.order.OrderDay;
import java.util.List;

public enum Star {
    STAR_DAY,
    NON_STAR_DAY;

    private static final List<Integer> STAR_DAYS = List.of(3, 10, 17, 24, 25, 31);

    public static Star from(final OrderDay orderDay) {
        if (orderDay.in(STAR_DAYS)) {
            return STAR_DAY;
        }

        return NON_STAR_DAY;
    }

    public boolean isStarDay() {
        return this.equals(STAR_DAY);
    }
}

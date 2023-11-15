package christmas.domain.event;

import java.util.Arrays;
import java.util.Comparator;

public enum Badge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NONE("", 0);

    private final String title;
    private final int minPrice;

    Badge(final String title, final int minPrice) {
        this.title = title;
        this.minPrice = minPrice;
    }

    public static Badge from(final int totalDiscount) {
        return Arrays.stream(values())
                .sorted(Comparator.comparing(Badge::getMinPrice).reversed())
                .filter(badge -> badge.isSatisfiedCondition(totalDiscount))
                .findFirst()
                .orElse(NONE);
    }

    private boolean isSatisfiedCondition(final int totalDiscount) {
        return minPrice <= totalDiscount;
    }

    public String getTitle() {
        return title;
    }

    public int getMinPrice() {
        return minPrice;
    }
}

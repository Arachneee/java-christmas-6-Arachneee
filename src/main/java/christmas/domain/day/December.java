package christmas.domain.day;

public enum December {
    START_DAY(1),
    CHRISTMAS_DAY(25),
    END_DAY(31);

    private final int day;

    December(final int day) {
        this.day = day;
    }

    public static boolean in(final int day) {
        return day >= START_DAY.getDay() && day <= END_DAY.getDay();
    }

    public static boolean isChristMas(final int day) {
        return day == CHRISTMAS_DAY.getDay();
    }

    public int getDay() {
        return day;
    }
}
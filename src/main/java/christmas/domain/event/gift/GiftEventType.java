package christmas.domain.event.gift;

import static java.util.stream.Collectors.toMap;

import christmas.domain.event.Event;
import christmas.domain.order.Order;
import christmas.domain.order.menu.Menu;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.Predicate;

public enum GiftEventType implements Event {
    PRESENTATION("증정 이벤트", order -> order.isTotalPriceUnder(Amount.PRESENTATION_MIN.value),
            Menu.CHAMPAGNE, 1);

    private final String title;
    private final Predicate<Order> unavailable;
    private final Menu menu;
    private final int count;

    GiftEventType(final String title, final Predicate<Order> unavailable, final Menu menu, final int count) {
        this.title = title;
        this.unavailable = unavailable;
        this.menu = menu;
        this.count = count;
    }

    public static EnumMap<GiftEventType, Integer> applyAll(final Order order) {
        return Arrays.stream(values())
                .collect(toMap(eventType -> eventType,
                        eventType -> eventType.calculateBenefits(order),
                        Integer::sum,
                        () -> new EnumMap<>(GiftEventType.class)));
    }

    @Override
    public int calculateBenefits(final Order order) {
        if (unavailable.test(order)) {
            return Amount.ZERO.value;
        }

        return calculateMenuTotalPrice();
    }

    private int calculateMenuTotalPrice() {
        return menu.getPrice() * count;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getMenuTitle() {
        return menu.getTitle();
    }

    public Menu getMenu() {
        return menu;
    }

    public int getCount() {
        return count;
    }

    private enum Amount {
        PRESENTATION_MIN(120_000),
        ZERO(0);

        private final int value;

        Amount(int value) {
            this.value = value;
        }
    }

}

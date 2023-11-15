package christmas.domain.event.gift;

import christmas.domain.event.Event;
import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
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

    public int getCount() {
        return count;
    }

    private enum Amount {
        PRESENTATION_MIN(120_000),
        ZERO(0);

        private final int value;

        Amount(final int value) {
            this.value = value;
        }
    }

}

package christmas.domain.event.constant;

import christmas.domain.event.ChristmasEvent;
import christmas.domain.event.Event;
import christmas.domain.event.GiftEvent;
import christmas.domain.event.SpecialEvent;
import christmas.domain.event.WeekEvent;
import christmas.domain.order.Order;
import christmas.domain.result.DiscountResult;
import christmas.domain.result.DiscountResults;
import java.util.Arrays;

public enum EventType {

    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인", new ChristmasEvent(), true),
    WEEKDAY_DISCOUNT("평일 할인", new WeekEvent(), true),
    WEEKEND_DISCOUNT("주말 할인", new WeekEvent(), true),
    SPECIAL_DISCOUNT("특별 할인",new SpecialEvent(), true),
    PRESENTATION("증정 이벤트",new GiftEvent(), false);

    private final String title;
    private final Event event;
    private final boolean isDiscount;

    EventType(final String title, final Event event, final boolean isDiscount) {
        this.title = title;
        this.event = event;
        this.isDiscount = isDiscount;
    }

    public static DiscountResults discountAll(final Order order) {
        return DiscountResults.from(Arrays.stream(values())
                .map(eventType -> DiscountResult.of(eventType, eventType.getApply(order)))
                .toList());
    }

    private int getApply(final Order order) {
        return this.event.apply(order);
    }

    public String getTitle() {
        return title;
    }

    public boolean isDiscount() {
        return isDiscount;
    }
}

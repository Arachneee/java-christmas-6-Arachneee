package christmas.domain.event.gift;

import christmas.domain.event.EventResult;

public class Gift implements EventResult {

    private final GiftEventType giftEventType;
    private final int amount;

    private Gift(final GiftEventType giftEventType, final int amount) {
        this.giftEventType = giftEventType;
        this.amount = amount;
    }

    public static Gift of(final GiftEventType giftEventType, final int amount) {
        return new Gift(giftEventType, amount);
    }

    public String getMenuTitle() {
        return giftEventType.getMenuTitle();
    }

    public int getMenuCount() {
        return giftEventType.getCount();
    }

    @Override
    public boolean isActive() {
        return amount != ZERO;
    }

    @Override
    public String getEventTitle() {
        return giftEventType.getTitle();
    }

    @Override
    public int getAmount() {
        return amount;
    }
}

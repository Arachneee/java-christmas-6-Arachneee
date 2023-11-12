package christmas.response;

import java.util.List;

public record EventDetailResponse(
        int priceBeforeEvent,
        List<GiftMenuResponse> giftMenuResponses,
        List<EventResponse> activeEvents,
        int totalBenefitsAmount,
        int priceAfterEvent,
        String badge
) {

    public static class EventDetailResponseBuilder {

        int priceBeforeEvent;
        List<GiftMenuResponse> giftMenuResponses;
        List<EventResponse> activeEvents;
        int totalBenefitsAmount;
        int priceAfterEvent;
        String badge;

        public static EventDetailResponseBuilder builder() {
            return new EventDetailResponseBuilder();
        }

        public EventDetailResponseBuilder priceBeforeEvent(final int priceBeforeEvent) {
            this.priceBeforeEvent = priceBeforeEvent;
            return this;
        }

        public EventDetailResponseBuilder giftMenuResponses(final List<GiftMenuResponse> giftMenuResponses) {
            this.giftMenuResponses = giftMenuResponses;
            return this;
        }

        public EventDetailResponseBuilder activeEvents(final List<EventResponse> activeEvents) {
            this.activeEvents = activeEvents;
            return this;
        }

        public EventDetailResponseBuilder totalBenefitsAmount(final int totalBenefitsAmount) {
            this.totalBenefitsAmount = totalBenefitsAmount;
            return this;
        }

        public EventDetailResponseBuilder priceAfterEvent(final int priceAfterEvent) {
            this.priceAfterEvent = priceAfterEvent;
            return this;
        }

        public EventDetailResponseBuilder badge(final String badge) {
            this.badge = badge;
            return this;
        }

        public EventDetailResponse build() {
            return new EventDetailResponse(priceBeforeEvent, giftMenuResponses, activeEvents,
                    totalBenefitsAmount, priceAfterEvent, badge);
        }
    }
}

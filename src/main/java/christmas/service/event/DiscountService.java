package christmas.service.event;

import christmas.domain.event.EventResult;
import christmas.domain.event.discount.Discount;
import christmas.domain.event.discount.DiscountEventType;
import christmas.domain.event.discount.DiscountRepository;
import christmas.domain.order.Order;
import christmas.response.EventResponse;
import java.util.List;

public class DiscountService {

    private DiscountRepository discountRepository;

    public void createDiscount(final Order order) {
        final List<Discount> discounts = DiscountEventType.discountAll(order);
        discountRepository = DiscountRepository.create(discounts);
    }

    public int calculateTotalAmount() {
        return discountRepository.calculateTotal();
    }

    public List<EventResponse> getEventResult() {

        return discountRepository.getDiscountResult().stream()
                .filter(EventResult::isActive)
                .map(eventResult -> EventResponse.from(eventResult.getEventTitle(), eventResult.getAmount()))
                .toList();
    }
}

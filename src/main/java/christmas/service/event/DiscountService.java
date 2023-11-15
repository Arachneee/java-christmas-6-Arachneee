package christmas.service.event;

import christmas.domain.event.discount.DiscountEventType;
import christmas.domain.event.discount.DiscountRepository;

public class DiscountService extends EventService<DiscountEventType> {

    public DiscountService(final DiscountRepository discountRepository) {
        super(discountRepository);
    }
}

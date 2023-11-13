package christmas.service.event;

import christmas.domain.event.discount.DiscountEventType;
import christmas.domain.event.discount.DiscountRepository;
import christmas.domain.order.Order;
import java.util.EnumMap;

public class DiscountService extends EventService<DiscountEventType> {

    private final DiscountRepository discountRepository;

    public DiscountService(final DiscountRepository discountRepository) {
        super(discountRepository);
        this.discountRepository = discountRepository;
    }

    @Override
    public void applyEventAll(final Order order) {
        final EnumMap<DiscountEventType, Integer> discountAmounts = DiscountEventType.discountAll(order);
        discountRepository.init(discountAmounts);
    }
}

package christmas.service.event;

import christmas.domain.event.discount.Discount;
import christmas.domain.event.discount.DiscountEventType;
import christmas.domain.event.discount.DiscountRepository;
import christmas.domain.order.Order;
import java.util.List;

public class DiscountService extends EventService<Discount> {

    private final DiscountRepository discountRepository;

    public DiscountService(final DiscountRepository discountRepository) {
        super(discountRepository);
        this.discountRepository = discountRepository;
    }

    @Override
    public void applyEventAll(final Order order) {
        final List<Discount> discounts = DiscountEventType.discountAll(order);
        discountRepository.init(discounts);
    }
}

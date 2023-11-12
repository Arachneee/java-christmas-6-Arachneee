package christmas.domain.event.discount;

import christmas.domain.event.EventRepository;
import java.util.Collections;
import java.util.List;

public class DiscountRepository implements EventRepository {

    private final List<Discount> discounts;

    private DiscountRepository(final List<Discount> discounts) {
        this.discounts = discounts;
    }

    public static DiscountRepository create(final List<Discount> discounts) {
        return new DiscountRepository(discounts);
    }

    @Override
    public int calculateTotal() {
        return discounts.stream()
                .mapToInt(Discount::getAmount)
                .sum();
    }

    public List<Discount> getDiscountResult() {
        return Collections.unmodifiableList(discounts);
    }
}

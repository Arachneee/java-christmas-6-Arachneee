package christmas.domain.event.discount;

import static java.util.stream.Collectors.toMap;

import christmas.domain.event.EventRepository;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

public class DiscountRepository implements EventRepository {

    private EnumMap<DiscountEventType, Integer> discountAmounts = new EnumMap<>(DiscountEventType.class);

    public void init(final EnumMap<DiscountEventType, Integer> discountAmounts) {
        this.discountAmounts = discountAmounts;
    }

    @Override
    public int calculateTotal() {
        return discountAmounts.values().stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }

    @Override
    public Map<String, Integer> getActiveResult() {
        return discountAmounts.entrySet().stream()
                .filter(this::isActive)
                .collect(toMap(this::getTitle, Entry::getValue));
    }

    private boolean isActive(final Entry<DiscountEventType, Integer> entry) {
        return entry.getValue() != ZERO;
    }

    private String getTitle(final Entry<DiscountEventType, Integer> entry) {
        return entry.getKey().getTitle();
    }
}

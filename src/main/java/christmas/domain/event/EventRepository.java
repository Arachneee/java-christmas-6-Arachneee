package christmas.domain.event;

import java.util.Map;


public interface EventRepository {

    int ZERO = 0;

    int calculateTotal();

    Map<String, Integer> getActiveResult();
}

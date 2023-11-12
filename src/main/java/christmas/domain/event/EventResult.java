package christmas.domain.event;

public interface EventResult {

    int ZERO = 0;

    boolean isActive();
    String getEventTitle();
    int getAmount();
}

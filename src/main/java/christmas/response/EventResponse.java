package christmas.response;

public record EventResponse(String title, int amount) {

    public static EventResponse of(final String title, final int amount) {
        return new EventResponse(title, amount);
    }
}

package christmas.response;

public record GiftMenuResponse(String title, int count) {

    public static GiftMenuResponse of(final String title, final int count) {
        return new GiftMenuResponse(title, count);
    }
}

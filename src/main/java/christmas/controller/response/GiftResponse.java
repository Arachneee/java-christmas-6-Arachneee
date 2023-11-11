package christmas.controller.response;

public record GiftResponse(String title, int count) {

    public static GiftResponse of(final String title, final int count) {
        return new GiftResponse(title, count);
    }
}

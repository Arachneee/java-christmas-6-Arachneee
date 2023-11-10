package christmas.controller.dto;

public record GiftDto(String title, int count) {

    public static GiftDto of(final String title, final int count) {
        return new GiftDto(title, count);
    }
}

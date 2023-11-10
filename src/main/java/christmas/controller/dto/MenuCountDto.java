package christmas.controller.dto;

public record MenuCountDto(
        String title,
        int count
) {

    public static MenuCountDto of(final String title, final int count) {
        return new MenuCountDto(title, count);
    }

}

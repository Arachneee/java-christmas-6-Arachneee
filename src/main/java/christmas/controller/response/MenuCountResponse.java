package christmas.controller.response;

public record MenuCountResponse(
        String title,
        int count
) {

    public static MenuCountResponse of(final String title, final int count) {
        return new MenuCountResponse(title, count);
    }

}

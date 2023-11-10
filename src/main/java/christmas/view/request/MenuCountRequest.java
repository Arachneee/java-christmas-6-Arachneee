package christmas.view.request;

public record MenuCountRequest(
        String title,
        int count
) {

    public static MenuCountRequest of(final String title, final int count) {
        return new MenuCountRequest(title, count);
    }

}

package christmas.controller.dto;

public record MenuCountDto(
        String name,
        int count
) {

    public static MenuCountDto of(final String name, final int count) {
        return new MenuCountDto(name, count);
    }

}

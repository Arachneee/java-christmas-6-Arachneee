package christmas.view;

public interface Writer {

    void println(final String input);

    void printf(final String format, Object... args);
}

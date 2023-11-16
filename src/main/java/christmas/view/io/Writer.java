package christmas.view.io;

public interface Writer {

    void println(final String input);

    void printf(final String format, final Object... args);
}

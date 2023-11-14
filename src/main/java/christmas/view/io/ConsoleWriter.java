package christmas.view.io;

public class ConsoleWriter implements Writer {

    @Override
    public void println(final String input) {
        System.out.println(input);
    }

    @Override
    public void printf(final String format, final Object... args) {
        System.out.printf(format, args);
    }
}

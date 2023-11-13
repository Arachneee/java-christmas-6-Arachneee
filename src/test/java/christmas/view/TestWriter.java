package christmas.view;

import static java.lang.System.lineSeparator;

public class TestWriter implements Writer {

    private static StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void println(String input) {
        stringBuilder.append(input).append(lineSeparator());
    }

    @Override
    public void printf(String format, Object... args) {
        stringBuilder.append(String.format(format, args));
    }

    public void clear() {
        stringBuilder = new StringBuilder();
    }

    public String getString() {
        return stringBuilder.toString();
    }
}
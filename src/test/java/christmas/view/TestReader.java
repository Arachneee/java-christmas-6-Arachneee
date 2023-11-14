package christmas.view;

import christmas.view.io.Reader;

public class TestReader implements Reader {

    private final String input;

    public TestReader(final String input) {
        this.input = input;
    }

    @Override
    public String readLine() {
        return input;
    }

    @Override
    public void close() {
    }
}

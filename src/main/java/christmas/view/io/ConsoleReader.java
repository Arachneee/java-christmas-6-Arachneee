package christmas.view.io;

import camp.nextstep.edu.missionutils.Console;
import christmas.view.Reader;

public class ConsoleReader implements Reader {

    @Override
    public String readLine() {
        return Console.readLine();
    }

    @Override
    public void close() {
        Console.close();
    }
}

package christmas.view.converter;

import static christmas.exception.constant.ErrorMessage.INVALID_ORDER;
import static java.util.stream.Collectors.toMap;

import christmas.exception.OrderException;
import christmas.exception.constant.ErrorMessage;
import java.util.Arrays;
import java.util.Map;

public class Parser {

    private static final String MENU_SPLIT_SIGNAL = ",";
    private static final String COUNT_SPLIT_SIGNAL = "-";
    private static final int TITLE_INDEX = 0;
    private static final int COUNT_INDEX = 1;

    private Parser() {
    }

    public static int dayToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw OrderException.from(ErrorMessage.INVALID_DAY);
        }
    }

    public static int orderCountToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw OrderException.from(INVALID_ORDER);
        }
    }

    public static Map<String, Integer> orderInputToStringIntegerMap(final String input) {
        try {
            return Arrays.stream(input.split(MENU_SPLIT_SIGNAL))
                    .map(Parser::splitMenuCount)
                    .collect(toMap(menu -> menu[TITLE_INDEX],
                            menu -> orderCountToInt(menu[COUNT_INDEX])));
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw OrderException.from(INVALID_ORDER);
        }

    }

    private static String[] splitMenuCount(final String menuCount) {
        return menuCount.split(COUNT_SPLIT_SIGNAL);
    }
}

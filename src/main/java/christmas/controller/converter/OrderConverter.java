package christmas.controller.converter;

import static christmas.exception.ErrorMessage.INVALID_ORDER;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toMap;

import christmas.domain.order.Day;
import christmas.domain.order.constant.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.OrderException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderConverter {

    private static final Pattern MENU_COUNT_PATTERN = compile("^[가-힣]+-\\d+(?:,[가-힣]+-\\d+)*$");
    private static final String MENU_SPLIT_SIGNAL = ",";
    private static final String COUNT_SPLIT_SIGNAL = "-";
    private static final String INVALID_START = "0";
    private static final int TITLE_INDEX = 0;
    private static final int COUNT_INDEX = 1;

    private OrderConverter() {
    }

    public static Day convertToDay(final String input) {
        try {
            return Day.from(Integer.parseInt(input));
        } catch (NumberFormatException numberFormatException) {
            throw OrderException.from(ErrorMessage.INVALID_DAY);
        }
    }

    public static EnumMap<Menu, Integer> convertToMenu(final String input) {
        validateFormat(input);

        return createMenuEnumMap(input);
    }

    private static void validateFormat(final String input) {
        final Matcher matcher = MENU_COUNT_PATTERN.matcher(input);

        if (matcher.matches()) {
            return;
        }

        throw OrderException.from(INVALID_ORDER);
    }

    private static EnumMap<Menu, Integer> createMenuEnumMap(final String input) {

        return Arrays.stream(input.split(MENU_SPLIT_SIGNAL))
                .map(menuCount -> menuCount.split(COUNT_SPLIT_SIGNAL))
                .collect(toMap(menuCount -> Menu.from(menuCount[TITLE_INDEX]),
                        menuCount -> convertCountToInt(menuCount[COUNT_INDEX]),
                        throwingDuplicateMenuException(),
                        () -> new EnumMap<>(Menu.class)));
    }

    private static int convertCountToInt(final String input) {
        validateStartZero(input);

        return Integer.parseInt(input);
    }

    private static void validateStartZero(final String input) {
        if (input.startsWith(INVALID_START)) {
            throw OrderException.from(INVALID_ORDER);
        }
    }

    private static <T> BinaryOperator<T> throwingDuplicateMenuException() {
        return (menu, other) -> {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        };
    }
}

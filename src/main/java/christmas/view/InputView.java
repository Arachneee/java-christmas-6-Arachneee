package christmas.view;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static christmas.view.constant.Request.MENU_AND_COUNT;
import static christmas.view.constant.Request.VISIT_DAY;

import christmas.view.converter.Parser;
import java.util.Map;

public class InputView {

    public int readDate() {
        System.out.println(VISIT_DAY.getMessage());

        return Parser.dayToInt(readLine());
    }

    public Map<String, Integer> readMenuAndCount() {
        System.out.println(MENU_AND_COUNT.getMessage());

        return Parser.orderInputToStringIntegerMap(readLine());
    }
}

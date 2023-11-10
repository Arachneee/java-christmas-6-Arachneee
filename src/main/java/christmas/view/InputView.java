package christmas.view;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static christmas.view.constant.Request.MENU_AND_COUNT;
import static christmas.view.constant.Request.VISIT_DAY;

import christmas.view.converter.RequestConverter;
import christmas.view.request.MenuCountRequest;
import java.util.List;

public class InputView {

    public int readDate() {
        System.out.println(VISIT_DAY.getMessage());

        return RequestConverter.convertDayToInt(readLine());
    }

    public List<MenuCountRequest> readMenuAndCount() {
        System.out.println(MENU_AND_COUNT.getMessage());

        return RequestConverter.convertMenuCountRequests(readLine());
    }
}

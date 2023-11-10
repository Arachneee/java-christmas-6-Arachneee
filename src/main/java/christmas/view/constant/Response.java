package christmas.view.constant;

public enum Response {

    PREVIEW_EVENT("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),
    MENU_COUNT("%s %d개"),
    POSITIVE_MONEY("%,d원"),
    NEGATIVE_MONEY("-%,d원");

    private final String message;

    Response(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

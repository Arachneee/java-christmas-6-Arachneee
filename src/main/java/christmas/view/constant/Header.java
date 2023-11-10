package christmas.view.constant;

public enum Header {
    HELLO("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."),
    MENU("<주문 메뉴>"),
    BEFORE_TOTAL_PRICE("<할인 전 총주문 금액>"),
    GIFT("<증정 메뉴>"),
    DISCOUNT("<혜택 내역>"),
    TOTAL_DISCOUNT("<총혜택 금액>"),
    AFTER_PAYMENT("<할인 후 예상 결제 금액>"),
    BADGE("<12월 이벤트 배지>");

    private final String message;

    Header(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}

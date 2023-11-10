package christmas.exception;

import christmas.exception.constant.ErrorMessage;

public class OrderException extends IllegalArgumentException {

    private OrderException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    public static OrderException from(final ErrorMessage errorMessage) {
        return new OrderException(errorMessage);
    }


}

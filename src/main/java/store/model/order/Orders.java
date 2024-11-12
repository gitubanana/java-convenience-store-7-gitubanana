package store.model.order;

import static store.constant.ErrorMessage.WRONG_INPUT;

import java.util.List;

public class Orders {
    private final List<Order> orders;

    public Orders(List<Order> orders) {
        validate(orders);
        this.orders = orders;
    }

    private void validate(List<Order> orders) {
        validateOrderNotDuplicated(orders);
    }

    private void validateOrderNotDuplicated(List<Order> orders) {
        int distinctCount = (int) orders.stream().map(Order::getProductName).distinct().count();
        if (distinctCount != orders.size()) {
            throw new IllegalArgumentException(WRONG_INPUT.getMessage());
        }
    }

    public List<Order> toList() {
        return List.copyOf(orders);
    }
}

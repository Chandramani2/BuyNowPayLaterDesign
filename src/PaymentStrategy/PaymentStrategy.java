package PaymentStrategy;

import Order.Order;
import Interaction.User;

public interface PaymentStrategy {
    boolean processPayment(User user, Order order);
}

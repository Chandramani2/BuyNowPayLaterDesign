package PaymentStrategy;

import Order.Order;
import Interaction.User;

public class PrepaidPayment implements PaymentStrategy{
    @Override
    public boolean processPayment(User user, Order order) {
        if (order.product.quantity >= order.quantity) {
            user.addOrder(order);
            order.product.quantity -= order.quantity;
            return true;
        } else {
            System.out.println("Insufficient stock. Order not placed.");
            return false;
        }
    }
}

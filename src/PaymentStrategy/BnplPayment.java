package PaymentStrategy;

import Order.Order;
import Interaction.User;

public class BnplPayment implements PaymentStrategy{
    @Override
    public boolean processPayment(User user, Order order) {
        if (user.getBnplLimit() >= order.product.price * order.quantity) {
            user.setBnplLimit(user.getBnplLimit() - order.product.price * order.quantity);
            user.addOrder(order);
            order.product.quantity -= order.quantity;
            if(order.quantity<0) return false;
            return true;
        } else {
            System.out.println("BNPL limit exceeded. Order not placed.");
            return false;
        }
    }
}

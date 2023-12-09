package Order;

import Interaction.Product;
import Interaction.User;
import PaymentStrategy.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {
    public boolean placeOrder(User user, Map<Product, Integer> items, PaymentStrategy paymentMethod, String dateOfPurchase) {
        List<Order> newOrders = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            Order newOrder = new Order(product, quantity, paymentMethod, dateOfPurchase);
            boolean orderPlaced = newOrder.processPayment(user);

            if (orderPlaced) {
                newOrders.add(newOrder);
            } else {
                // Rollback previous orders if any new order fails
                for (Order failedOrder : newOrders) {
                    failedOrder.product.quantity += failedOrder.quantity;
                    user.getOrders().remove(failedOrder);
                }
                return false;
            }
        }

        return true;
    }
}

package Order;

import Interaction.Product;
import PaymentStrategy.PaymentStrategy;
import Interaction.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order {
    public Product product;
    public int quantity;
    PaymentStrategy paymentMethod;
    public String date;
    OrderStatus status;

    public PaymentStrategy getPaymentMethod() {
        return paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Order(Product product, int quantity, PaymentStrategy paymentMethod, String date) {
        this.product = product;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.status = OrderStatus.PENDING;
    }

    public boolean processPayment(User user) {
        boolean paymentResult = paymentMethod.processPayment(user, this);
        if (!paymentResult) {
            this.status = OrderStatus.DELAYED;
        }
        return paymentResult;
    }
}

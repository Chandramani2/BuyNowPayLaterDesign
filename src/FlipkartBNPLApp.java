
import Order.Order;
import Order.OrderService;
import Interaction.Product;
import Interaction.User;
import PaymentStrategy.BnplPayment;
import PaymentStrategy.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Inventory.Inventory.viewInventory;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class FlipkartBNPLApp {
    public static void main(String[] args) throws Exception {
        Product shoes = new Product("Shoes", 5, 200);
        Product watch = new Product("Watch", 10, 1000);
        Product tShirt = new Product("T-Shirt", 14, 300);

        viewInventory(shoes, watch, tShirt);

        User Chandramani = new User("Chandramani", 8500);
        Map<Product, Integer> orderItems = Map.of(shoes, 6, watch, 5, tShirt, 10);
        PaymentStrategy bnplPayment = new BnplPayment();
        String dateOfPurchase = "20-Nov-2023";
        OrderService orderService = new OrderService();
        boolean orderPlaced = orderService.placeOrder(Chandramani, orderItems, bnplPayment, dateOfPurchase);
        if (orderPlaced) {
            System.out.println("Order placed successfully!");
            Chandramani.viewDues();
            List<Order> ordersToClear = new ArrayList<>(Chandramani.getOrders()); // Clear all orders
            String dateOfClearing = "20-Nov-2023";
            Chandramani.clearDues(ordersToClear, dateOfClearing);

        } else {
            System.out.println("Order placement failed.");
        }

        viewInventory(shoes, watch, tShirt);
        Chandramani.viewOrderHistory();
        Chandramani.viewDues();

//        Order order1 = new Order(shoes, 2, new BnplPayment(), "20-Oct-2021");
//        Order order2 = new Order(watch, 4, new BnplPayment(), "20-Nov-2021");
//        Order order3 = new Order(tShirt, 4, new BnplPayment(), "20-Dec-2021");
//
//        order1.processPayment(Chandramani);
//        order2.processPayment(Chandramani);
//        order3.processPayment(Chandramani);
//        viewInventory(shoes, watch, tShirt);
//        Chandramani.viewDues();
        }
    }

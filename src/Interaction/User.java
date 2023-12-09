package Interaction;

import Order.Order;
import Order.OrderStatus;
import PaymentStrategy.PaymentStrategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class User {
    String name;
    double bnplLimit;

    List<Order> orders = new ArrayList<>();
    List<Order> ordersToKeep = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }
    public User(String name, double bnplLimit) {
        this.name = name;
        this.bnplLimit = bnplLimit;
    }

    public double getBnplLimit() {
        return bnplLimit;
    }

    public void setBnplLimit(double bnplLimit) {
        this.bnplLimit = bnplLimit;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void viewOrderHistory() {
        System.out.println("Order History for " + name + ":");
        if(ordersToKeep.size()>0){
            for (Order order : ordersToKeep) {
                System.out.println(order.date + " - " + order.product.name + " - Quantity: " +
                        order.quantity + ", Payment Method: " + order.getPaymentMethod().getClass().getSimpleName() +
                        ", Status: " + order.getStatus());
            }
        }
        else{
            for (Order order : orders) {
                System.out.println(order.date + " - " + order.product.name + " - Quantity: " +
                        order.quantity + ", Payment Method: " + order.getPaymentMethod().getClass().getSimpleName() +
                        ", Status: " + order.getStatus());
            }
        }

    }

    public void clearDues(List<Order> ordersToClear, String dateOfClearing) {
        double totalDue = 0;
        for (Order order : orders) {
            if (!isDelayed(order.date)) {
                ordersToKeep.add(order);
            } else {
                totalDue += order.product.price * order.quantity;
            }
        }

        System.out.println("Dues cleared on " + dateOfClearing + ". Total amount: " + totalDue);

        for (Order order : ordersToClear) {
            totalDue += order.product.price * order.quantity;
            order.setStatus(OrderStatus.COMPLETED);
            orders.remove(order);
        }
        bnplLimit += totalDue;
    }

    public void viewDues() {
        if(orders.size()>0){
            Collections.sort(orders, (o1, o2) -> o1.date.compareTo(o2.date));
            for (Order order : orders) {
                System.out.println(order.date);
                System.out.println(order.product.price * order.quantity);
                System.out.println("Due By: " + order.date);
                System.out.println("Status: " + (isDelayed(order.date) ? "DELAYED" : "PENDING"));
                System.out.println();
            }
        }
        else{
            System.out.println("All Dues cleared for " + name);
        }

    }

    private boolean isDelayed(String orderDate) {
        // Assuming the date format is "dd-MMM-yyyy"
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            LocalDate purchaseDate = LocalDate.parse(orderDate, formatter);
            LocalDate currentDate = LocalDate.now();
            return purchaseDate.plusDays(30).isBefore(currentDate);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}

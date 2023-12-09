package Inventory;

import Interaction.Product;

public class Inventory {
    public static void viewInventory(Product... products) {
        System.out.println("Current Inventory:");
        for (Product product : products) {
            System.out.println(product.name + " - Quantity: " + product.quantity + ", Price: " + product.price);
        }
    }

}

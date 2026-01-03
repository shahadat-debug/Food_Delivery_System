package com.example.myapplication;

public class OrderItem {
    private String itemName;
    private String size;
    private int quantity;
    private int price;

    public OrderItem(String itemName, String size, int quantity, int price) {
        this.itemName = itemName;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return quantity + "x " + itemName + " (" + size + ") - PKR " + (price * quantity);
    }
}

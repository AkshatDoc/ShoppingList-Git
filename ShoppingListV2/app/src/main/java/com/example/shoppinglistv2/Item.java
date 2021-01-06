package com.example.shoppinglistv2;

import android.os.Bundle;

public class Item {

    private String itemName;
    private int qty;
    private double cost;
    private boolean purchased;

    Item(String name, int quantity, double price, boolean isPurchased) {
        itemName = name;
        qty = quantity;
        cost = price;
        purchased = isPurchased;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public int getQty() {
        return qty;
    }

    public double getCost() {
        return cost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String toString() {
        return itemName;
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("NAME", itemName);
        bundle.putInt("QTY",qty);
        bundle.putDouble("PRICE",cost);
        bundle.putBoolean("PURCHASED",purchased);
        return bundle;
    }
}

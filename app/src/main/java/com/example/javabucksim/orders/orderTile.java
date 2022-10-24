package com.example.javabucksim.orders;

public class orderTile {
    String itemName;
    String itemquantity;

    public orderTile(String itemName, String itemquantity) {
        this.itemName = itemName;
        this.itemquantity = itemquantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemquantity(String itemquantity) {
        this.itemquantity = itemquantity;
    }
    //String picture;
    //String Total;
}

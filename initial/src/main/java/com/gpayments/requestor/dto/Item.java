package com.gpayments.requestor.sample_requestor.dto;

/**
 * @author GPayments ON 31/07/2018
 * Item for the shopping site
 */
public class Item {
    private String name;
    private String description;
    private int quantity;
    private float price;
    private String imagePath;

    public Item(String name, String description, int quantity, float price, String imagePath) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Item(Item item) {
        this.name = item.getName();
        this.description = item.getDescription();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.imagePath = item.getImagePath();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}

package com.gpayments.requestor.dto;


/**
 * @author GPayments ON 31/07/2018 Item for the shopping site
 */
public class Item {

    private String name;
    private String description;
    private int quantity;
  private double unitPrice;
    private String imagePath;

  public Item(String name, String description, int quantity, double unitPrice, String imagePath) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    this.unitPrice = unitPrice;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public double getTotalPrice() {
    return quantity * unitPrice;
  }

  public void inclQuantity(int quantity) {
    this.quantity += quantity;
  }

    @Override
    public String toString() {
      return "Item{"
          + "name='"
          + name
          + '\''
          + ", description='"
          + description
          + '\''
          + ", quantity="
          + quantity
          + ", unitPrice="
          + unitPrice
          + ", imagePath='"
          + imagePath
          + '\''
          + '}';
    }
}

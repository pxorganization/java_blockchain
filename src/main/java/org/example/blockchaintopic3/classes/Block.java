package org.example.blockchaintopic3.classes;

public class Block {
    private int id;
    private String productCode;
    private String title;
    private long timestamp;
    private double price;
    private String description;
    private String category;
    private int previousRecordId;

    public Block(int id, String productCode, String title, long timestamp, double price, String description, String category, int previousRecordId) {
        this.id = id;
        this.productCode = productCode;
        this.title = title;
        this.timestamp = timestamp;
        this.price = price;
        this.description = description;
        this.category = category;
        this.previousRecordId = previousRecordId;
    }

    //getter - setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {

    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getPreviousRecordId() {
        return previousRecordId;
    }
    public void setPreviousRecordId(int previousRecordId) {
        this.previousRecordId = previousRecordId;
    }
}

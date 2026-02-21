package models.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetASingleProductResponse {
    private String id;
    private String title;
    private double price;
    private int stock;
    private double total;
    private double discountPercentage;
    private String thumbnail;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public double getTotal() {
        return total;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}

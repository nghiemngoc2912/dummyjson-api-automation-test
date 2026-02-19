package models.products;
public class GetASingleProductResponse {
    private String id;
    private String title;
    private double price;
    private int quantity;
    private double total;
    private double discountPercentage;
    private double discountedPrice;
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

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}

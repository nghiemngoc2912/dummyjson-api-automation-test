package models.carts;

import java.util.List;

public class AddANewCartResponse {
    private String id;
    private List<Product> products;
    private double total;
    private double discountedTotal;
    int userId;
    int totalProducts;
    int totalQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(double discountedTotal) {
        this.discountedTotal = discountedTotal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public static class Product {
        private String id;
        private String title;
        private double price;
        private int quantity;
        private double total;
        private double discountPercentage;
        private double discountedPrice;
        private String thumbnail;

        public Product() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(double discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public double getDiscountedPrice() {
            return discountedPrice;
        }

        public void setDiscountedPrice(double discountedPrice) {
            this.discountedPrice = discountedPrice;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}

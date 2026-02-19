package models.carts;

import java.util.List;

public class AddANewCartRequest {
    private String userId;
    private List<Product> products;

    public AddANewCartRequest() {
    }

    public AddANewCartRequest(String userId, List<Product> products) {
        this.userId = userId;
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public static class Product{
        private String id;
        private String quantity;

        public Product(String id, String quantity) {
            this.id = id;
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

}

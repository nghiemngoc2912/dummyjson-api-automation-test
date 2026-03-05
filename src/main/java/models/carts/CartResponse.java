package models.carts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private String id;
    private List<Product> products;
    private double total;
    private double discountedTotal;
    int userId;
    int totalProducts;
    int totalQuantity;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Product {
        private String id;
        private String title;
        private double price;
        private int quantity;
        private double total;
        private double discountPercentage;
        private double discountedPrice;
        private String thumbnail;
        @JsonIgnoreProperties(ignoreUnknown = true)
        private double discountedTotal;

    }
}

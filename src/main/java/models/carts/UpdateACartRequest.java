package models.carts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class UpdateACartRequest {
    Boolean merge;
    List<Product> products;

    @Data
    @AllArgsConstructor
    public static class Product{
        private String id;
        private String quantity;
    }
}

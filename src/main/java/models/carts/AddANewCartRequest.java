package models.carts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddANewCartRequest {
    private String userId;
    private List<Product> products;

    @AllArgsConstructor
    @Data
    public static class Product{
        private String id;
        private String quantity;
    }

}

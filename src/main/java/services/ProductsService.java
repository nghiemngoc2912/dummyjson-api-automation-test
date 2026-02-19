package services;

import apis.ProductsApi;
import io.restassured.response.Response;

public class ProductsService {
    private final ProductsApi client = new ProductsApi();
    public Response getASingleProduct(String id) {
        return client.getASingleProduct(id);
    }
} 

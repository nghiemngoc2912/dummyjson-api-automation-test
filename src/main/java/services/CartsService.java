package services;

import apis.CartsApi;
import io.restassured.response.Response;
import models.carts.AddANewCartRequest;

public class CartsService {
    private final CartsApi client = new CartsApi();

    public Response addANewCart(AddANewCartRequest request) {
        return client.addANewCart(request);
    }

} 

package services;

import apis.CartsApi;
import io.restassured.response.Response;
import models.carts.AddANewCartRequest;
import models.carts.UpdateACartRequest;

import java.util.List;
import java.util.Random;

public class CartsService {
    private final CartsApi client = new CartsApi();
    private final Random random = new Random();

    public Response addANewCart(AddANewCartRequest request) {
        return client.addANewCart(request);
    }

    public Response updateACart(String cartId, UpdateACartRequest request) {
        return client.updateACart(cartId, request);
    }

    public String getAValidCartId() {
        List<Integer> carts = client.getAllCart().body().jsonPath().getList("carts.id");
        return Integer.toString(carts.get(random.nextInt(carts.size())));
    }

    public Response getASingleCart(String cartId){
        return client.getASingleCart(cartId);
    }
} 

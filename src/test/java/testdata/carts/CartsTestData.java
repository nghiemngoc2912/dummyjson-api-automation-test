package testdata.carts;

import models.carts.AddANewCartRequest;
import services.UsersService;

import java.util.ArrayList;
import java.util.List;

public class CartsTestData {
    private static UsersService usersService = new UsersService();

    public static AddANewCartRequest validAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                new AddANewCartRequest.Product("1", "99"),
                new AddANewCartRequest.Product("2", "1")
        )));
        return request;
    }

}

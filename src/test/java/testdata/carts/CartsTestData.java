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

    public static AddANewCartRequest userIdNotExistAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId("-1");
        request.setProducts(
                new ArrayList<>(List.of(
                new AddANewCartRequest.Product("1", "99"),
                new AddANewCartRequest.Product("2", "1")
        )));
        return request;
    }

    public static AddANewCartRequest productIdNotExistAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("-1", "99"),
                        new AddANewCartRequest.Product("2", "1")
                )));
        return request;
    }

    public static AddANewCartRequest productIdNotExistAddANewCartRequestAfterRemovingInvalidProductId(String userId) {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("2", "1")
                )));
        request.setUserId(userId);
        return request;
    }

    public static AddANewCartRequest userIdNullAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(null);
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("1", "99"),
                        new AddANewCartRequest.Product("2", "1")
                )));
        return request;
    }


    public static AddANewCartRequest productIdNullAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product(null, "99")
                )));
        return request;
    }

    public static AddANewCartRequest quantityNullAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("1", null),
                        new AddANewCartRequest.Product("2", "1")
                )));
        return request;
    }

    public static AddANewCartRequest quantityLt0AddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("1", "-1"),
                        new AddANewCartRequest.Product("2", "1")
                )));
        return request;
    }
}

package testdata.carts;

import models.carts.AddANewCartRequest;
import models.carts.UpdateACartRequest;
import models.products.GetASingleProductResponse;
import services.ProductsService;
import services.UsersService;

import java.util.ArrayList;
import java.util.List;

public class CartsTestData {
    private static UsersService usersService = new UsersService();
    private static ProductsService productsService = new ProductsService();

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

    public static AddANewCartRequest quantityEq0AddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("1", "0"),
                        new AddANewCartRequest.Product("2", "1")
                )));
        return request;
    }

    public static AddANewCartRequest quantityGtStockAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("1", Integer.toString(productsService.getASingleProduct("1").as(GetASingleProductResponse.class).getStock()+1)),
                        new AddANewCartRequest.Product("2", "1")
                )));
        return request;
    }

    public static AddANewCartRequest quantityNaNAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("1", "a"),
                        new AddANewCartRequest.Product("2", "1")
                )));
        return request;
    }

    public static AddANewCartRequest quantityNotIntAddANewCartRequest() {
        AddANewCartRequest request = new AddANewCartRequest();
        request.setUserId(usersService.getAValidUserId());
        request.setProducts(
                new ArrayList<>(List.of(
                        new AddANewCartRequest.Product("1", "1.5"),
                        new AddANewCartRequest.Product("2", "1")
                )));
        return request;
    }

    public static UpdateACartRequest validUpdateACartRequest() {
        UpdateACartRequest request = new UpdateACartRequest();
        request.setMerge(false);
        request.setProducts(
                new ArrayList<>(List.of(
                        new UpdateACartRequest.Product("1", "99"),
                        new UpdateACartRequest.Product("2", "1")
                )));
        return request;
    }

}

package assertions.carts;

import constants.carts.CartsConstant;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.carts.AddANewCartRequest;
import models.carts.CartResponse;
import models.carts.UpdateACartRequest;
import models.products.GetASingleProductResponse;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import services.CartsService;
import services.ProductsService;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;

public class CartsAssertion {
    private static ProductsService productsService = new ProductsService();
    private static CartsService cartsService = new CartsService();

    private static void verifyProductInformation(
            SoftAssert softAssert,
            CartResponse.Product responseProduct,
            int expectedQuantity
    ) {
        String id = responseProduct.getId();

        Response productInform = productsService.getASingleProduct(id);
        GetASingleProductResponse product = productInform.as(GetASingleProductResponse.class);

        double expectedTotal = product.getPrice() * expectedQuantity;
        double expectedDiscountedPrice =
                Math.round(expectedQuantity * product.getPrice() * (1 - product.getDiscountPercentage() / 100));

        softAssert.assertEquals(responseProduct.getQuantity(), expectedQuantity, "Quantity mismatch id=" + id);
        softAssert.assertEquals(responseProduct.getTitle(), product.getTitle(), "Title mismatch id=" + id);
        softAssert.assertEquals(responseProduct.getPrice(), product.getPrice(), "Price mismatch id=" + id);
        softAssert.assertEquals(responseProduct.getTotal(), expectedTotal, "Total mismatch id=" + id);
        softAssert.assertEquals(responseProduct.getDiscountPercentage(), product.getDiscountPercentage());
        softAssert.assertEquals(responseProduct.getDiscountedPrice(), expectedDiscountedPrice);
        softAssert.assertEquals(responseProduct.getThumbnail(), product.getThumbnail());
    }

    private static Map<String, Integer> buildRequestMap(List<? extends Object> products) {

        Map<String, Integer> map = new HashMap<>();

        if (products == null) return map;

        for (Object obj : products) {

            String id;
            String quantity;

            if (obj instanceof AddANewCartRequest.Product p) {
                id = p.getId();
                quantity = p.getQuantity();
            } else {
                UpdateACartRequest.Product p = (UpdateACartRequest.Product) obj;
                id = p.getId();
                quantity = p.getQuantity();
            }

            map.put(id, Integer.parseInt(quantity));
        }

        return map;
    }

    public static void verifyAddToCartSuccessful(Response addANewCartResponse, AddANewCartRequest addANewCartRequest) {
        verifyAddToCartResponseSuccessCommon(addANewCartResponse);
        SoftAssert softAssert = new SoftAssert();

        CartResponse response = addANewCartResponse.as(CartResponse.class);
        Map<String, Integer> requestMap = buildRequestMap(addANewCartRequest.getProducts());

        double expectedTotal = 0;
        double expectedDiscountedTotal = 0;
        int expectedTotalQuantity = 0;

        for (CartResponse.Product p : response.getProducts()) {
            String id = p.getId();

            if (!requestMap.containsKey(id)) {
                softAssert.fail("Unexpected product in response id=" + id);
                continue;
            }

            int quantity = requestMap.get(id);

            verifyProductInformation(softAssert, p, quantity);

            expectedTotal += p.getTotal();
            expectedDiscountedTotal += p.getDiscountedPrice();
            expectedTotalQuantity += quantity;
        }

        softAssert.assertEquals(response.getUserId(), Integer.parseInt(addANewCartRequest.getUserId()), "UserId incorrect");
        softAssert.assertEquals(response.getTotal(), expectedTotal, "Total incorrect");
        softAssert.assertEquals(response.getDiscountedTotal(), expectedDiscountedTotal, "DiscountedTotal incorrect");
        softAssert.assertEquals(response.getTotalQuantity(), expectedTotalQuantity, "TotalQuantity incorrect");
        softAssert.assertEquals(response.getTotalProducts(), response.getProducts().size(), "TotalProducts incorrect");

        softAssert.assertAll();
    }

    private static void verifyAddToCartResponseSuccessCommon(Response addANewCartResponse) {
        addANewCartResponse
                .then()
                .log().ifValidationFails()
                .statusCode(201) // check status code = 201
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(CartsConstant.CART_SUCCESS_SCHEMA)) // check response success schema
        ;
    }

    public static void verifyAddToCartUnsuccessful(Response addANewCartResponse, String message, int statusCode) {
        addANewCartResponse
                .then()
                .log().ifValidationFails()
                .statusCode(statusCode)
                .body("message", equalTo(message));

    }

    public static void verifyUpdateACartSuccessful_MergeEqFalse(Response updateACartResponse, UpdateACartRequest updateACartRequest, Response oldCartResponse) {
        verifyUpdateACartResponseSuccessCommon(updateACartResponse);
        SoftAssert softAssert = new SoftAssert();

        CartResponse response = updateACartResponse.as(CartResponse.class);
        CartResponse oldCart = oldCartResponse.as(CartResponse.class);
        Map<String, Integer> requestMap = buildRequestMap(updateACartRequest.getProducts());

        Assert.assertEquals(response.getProducts().size(), requestMap.size(),
                "Product size mismatch");
        double expectedTotal = 0;
        double expectedDiscountedTotal = 0;
        int expectedTotalQuantity = 0;

        for (CartResponse.Product p : response.getProducts()) {
            String id = p.getId();

            if (!requestMap.containsKey(id)) {
                softAssert.fail("Unexpected product in response id=" + id);
                continue;
            }

            int quantity = requestMap.get(id);

            verifyProductInformation(softAssert, p, quantity);

            expectedTotal += p.getTotal();
            expectedDiscountedTotal += p.getDiscountedPrice();
            expectedTotalQuantity += quantity;
        }

        softAssert.assertEquals(response.getUserId(), oldCart.getUserId(), "UserId incorrect");
        softAssert.assertEquals(response.getTotal(), expectedTotal, "Total incorrect");
        softAssert.assertEquals(response.getDiscountedTotal(), expectedDiscountedTotal, "DiscountedTotal incorrect");
        softAssert.assertEquals(response.getTotalQuantity(), expectedTotalQuantity, "TotalQuantity incorrect");
        softAssert.assertEquals(response.getTotalProducts(), response.getProducts().size(), "TotalProducts incorrect");
        softAssert.assertAll();
    }

    private static void verifyUpdateACartResponseSuccessCommon(Response updateACartResponse) {
        updateACartResponse
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(CartsConstant.CART_SUCCESS_SCHEMA));
    }

    public static void verifyUpdateACartSuccessful_MergeEqTrue(
            Response updateACartResponse,
            UpdateACartRequest updateACartRequest,
            Response oldCartResponse
    ) {

        verifyUpdateACartResponseSuccessCommon(updateACartResponse);
        SoftAssert softAssert = new SoftAssert();

        CartResponse response = updateACartResponse.as(CartResponse.class);
        CartResponse oldCart = oldCartResponse.as(CartResponse.class);

        Map<String, Integer> requestMap = buildRequestMap(updateACartRequest.getProducts());
        Map<String, CartResponse.Product> oldMap = new HashMap<>();

        for (CartResponse.Product p : oldCart.getProducts()) {
            oldMap.put(p.getId(), p);
        }

        int expectedTotalQuantity = 0;
        double expectedTotal = 0;
        double expectedDiscountedTotal = 0;

        for (CartResponse.Product responseProduct : response.getProducts()) {

            String id = responseProduct.getId();

            CartResponse.Product oldProduct = oldMap.get(id);
            Integer requestQuantity = requestMap.get(id);

            // CASE 1: product only in old cart
            if (oldProduct != null && requestQuantity == null) {

                softAssert.assertEquals(responseProduct.getQuantity(), oldProduct.getQuantity(),
                        "Old product quantity changed id=" + id);

                softAssert.assertEquals(responseProduct.getTitle(), oldProduct.getTitle());
                softAssert.assertEquals(responseProduct.getPrice(), oldProduct.getPrice());
                softAssert.assertEquals(responseProduct.getDiscountPercentage(), oldProduct.getDiscountPercentage());
                softAssert.assertEquals(responseProduct.getThumbnail(), oldProduct.getThumbnail());
            }

            // CASE 2: merge quantity
            else if (oldProduct != null) {

                int expectedQuantity = oldProduct.getQuantity() + requestQuantity;

                softAssert.assertEquals(responseProduct.getQuantity(), expectedQuantity,
                        "Merged quantity incorrect id=" + id);

                softAssert.assertEquals(responseProduct.getTitle(), oldProduct.getTitle());
                softAssert.assertEquals(responseProduct.getPrice(), oldProduct.getPrice());
                softAssert.assertEquals(responseProduct.getDiscountPercentage(), oldProduct.getDiscountPercentage());
                softAssert.assertEquals(responseProduct.getThumbnail(), oldProduct.getThumbnail());
            }

            // CASE 3: new product
            else {

                int expectedQuantity = requestQuantity;

                verifyProductInformation(softAssert, responseProduct, expectedQuantity);
            }

            expectedTotal += responseProduct.getTotal();
            expectedDiscountedTotal += responseProduct.getDiscountedPrice();
            expectedTotalQuantity += responseProduct.getQuantity();
        }

        softAssert.assertEquals(response.getUserId(), oldCart.getUserId());
        softAssert.assertEquals(response.getTotalQuantity(), expectedTotalQuantity);
        softAssert.assertEquals(response.getTotal(), expectedTotal);
        softAssert.assertEquals(response.getDiscountedTotal(), expectedDiscountedTotal);
        softAssert.assertEquals(response.getTotalProducts(), response.getProducts().size());

        softAssert.assertAll();
    }
}

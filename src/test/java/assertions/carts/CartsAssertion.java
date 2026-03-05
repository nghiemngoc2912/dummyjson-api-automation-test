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

    public static void verifyAddToCartSuccessful(Response addANewCartResponse, AddANewCartRequest addANewCartRequest) {
        verifyAddToCartResponseSuccessCommon(addANewCartResponse);
        SoftAssert softAssert = new SoftAssert();

        CartResponse response = addANewCartResponse.as(CartResponse.class);
        //verify list product size in request and response
        List<CartResponse.Product> productsResponse = response.getProducts();
        List<AddANewCartRequest.Product> productsRequest = addANewCartRequest.getProducts();

        double expectedTotal = 0;
        double expectedDiscountedTotal = 0;
        int expectedTotalQuantity = 0;
        if (productsRequest == null && productsResponse.isEmpty()) {

        } else {
            Assert.assertEquals(productsResponse.size(), productsRequest.size(), "The quantity of products in response is different");

            //sort 2 lists by id
            productsResponse.sort(Comparator.comparing(CartResponse.Product::getId));
            productsRequest.sort(Comparator.comparing(AddANewCartRequest.Product::getId));

            //verify information
            Response productInform;
            GetASingleProductResponse product;
            int expectedQuantity;
            double expectedDiscountedPrice;
            for (int i = 0; i < productsResponse.size(); i++) {
                if (!productsResponse.get(i).getId().equals(productsRequest.get(i).getId())) {
                    softAssert.fail("Id not match");
                } else {
                    String id = productsResponse.get(i).getId();
                    expectedQuantity = Integer.parseInt(productsRequest.get(i).getQuantity());
                    expectedTotalQuantity += expectedQuantity;
                    softAssert.assertEquals(productsResponse.get(i).getQuantity(), expectedQuantity, "Quantity not match in product with id = " + id);
                    productInform = productsService.getASingleProduct(id);
                    product = productInform.as(GetASingleProductResponse.class);
                    expectedTotal += product.getPrice() * expectedQuantity;
                    expectedDiscountedPrice = Math.round(expectedQuantity * product.getPrice() * (1 - product.getDiscountPercentage() / 100));
                    expectedDiscountedTotal += expectedDiscountedPrice;
                    softAssert.assertEquals(productsResponse.get(i).getTitle(), product.getTitle(), "Title not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getPrice(), product.getPrice(), "Price not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getTotal(), product.getPrice() * expectedQuantity, "Total not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getDiscountPercentage(), product.getDiscountPercentage(), "DiscountPercentage not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getDiscountedPrice(), expectedDiscountedPrice, "DiscountedPrice not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getThumbnail(), product.getThumbnail(), "Thumbnail not match in product with id = " + id);
                }
            }
        }

        softAssert.assertEquals(response.getUserId(), Integer.parseInt(addANewCartRequest.getUserId()), "UserId incorrect");
        softAssert.assertEquals(response.getTotal(), expectedTotal, "Total incorrect");
        softAssert.assertEquals(response.getDiscountedTotal(), expectedDiscountedTotal, "DiscountedTotal incorrect");
        softAssert.assertEquals(response.getTotalQuantity(), expectedTotalQuantity, "TotalQuantity incorrect");
        softAssert.assertEquals(response.getTotalProducts(), productsRequest != null ? productsRequest.size() : 0, "TotalProducts incorrect");

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
        //verify list product size in request and response
        List<CartResponse.Product> productsResponse = response.getProducts();
        List<UpdateACartRequest.Product> productsRequest = updateACartRequest.getProducts();

        double expectedTotal = 0;
        double expectedDiscountedTotal = 0;
        int expectedTotalQuantity = 0;

        if (productsRequest == null && productsResponse.isEmpty()) {

        } else {
            Assert.assertEquals(productsResponse.size(), productsRequest.size(), "The quantity of products in response is different");

            //sort 2 lists by id
            productsResponse.sort(Comparator.comparing(CartResponse.Product::getId));
            productsRequest.sort(Comparator.comparing(UpdateACartRequest.Product::getId));

            //verify information
            Response productInform;
            GetASingleProductResponse product;
            int expectedQuantity;
            double expectedDiscountedPrice = 0;
            for (int i = 0; i < productsResponse.size(); i++) {
                if (!productsResponse.get(i).getId().equals(productsRequest.get(i).getId())) {
                    softAssert.fail("Id not match");
                } else {
                    String id = productsResponse.get(i).getId();
                    expectedQuantity = Integer.parseInt(productsRequest.get(i).getQuantity());
                    expectedTotalQuantity += expectedQuantity;
                    softAssert.assertEquals(productsResponse.get(i).getQuantity(), expectedQuantity, "Quantity not match in product with id = " + id);
                    productInform = productsService.getASingleProduct(id);
                    product = productInform.as(GetASingleProductResponse.class);
                    expectedTotal += product.getPrice() * expectedQuantity;
                    expectedDiscountedPrice = Math.round(expectedQuantity * product.getPrice() * (1 - product.getDiscountPercentage() / 100));
                    expectedDiscountedTotal += expectedDiscountedPrice;
                    softAssert.assertEquals(productsResponse.get(i).getTitle(), product.getTitle(), "Title not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getPrice(), product.getPrice(), "Price not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getTotal(), product.getPrice() * expectedQuantity, "Total not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getDiscountPercentage(), product.getDiscountPercentage(), "DiscountPercentage not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getDiscountedPrice(), expectedDiscountedPrice, "DiscountedPrice not match in product with id = " + id);
                    softAssert.assertEquals(productsResponse.get(i).getThumbnail(), product.getThumbnail(), "Thumbnail not match in product with id = " + id);
                }
            }
        }
        softAssert.assertEquals(response.getUserId(), oldCart.getUserId(), "UserId incorrect");
        softAssert.assertEquals(response.getTotal(), expectedTotal, "Total incorrect");
        softAssert.assertEquals(response.getDiscountedTotal(), expectedDiscountedTotal, "DiscountedTotal incorrect");
        softAssert.assertEquals(response.getTotalQuantity(), expectedTotalQuantity, "TotalQuantity incorrect");
        softAssert.assertEquals(response.getTotalProducts(), productsRequest != null ? productsRequest.size() : 0, "TotalProducts incorrect");
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

        List<CartResponse.Product> responseProducts = response.getProducts();
        List<CartResponse.Product> oldProducts = oldCart.getProducts();
        List<UpdateACartRequest.Product> requestProducts = updateACartRequest.getProducts();

        // Convert old cart to map
        Map<String, CartResponse.Product> oldMap = new HashMap<>();
        if (oldProducts != null) {
            for (CartResponse.Product p : oldProducts) {
                oldMap.put(p.getId(), p);
            }
        }

        // Convert request to map
        Map<String, Integer> requestMap = new HashMap<>();
        if (requestProducts != null) {
            for (UpdateACartRequest.Product p : requestProducts) {
                requestMap.put(p.getId(), Integer.parseInt(p.getQuantity()));
            }
        }

        double expectedTotal = 0;
        double expectedDiscountedTotal = 0;
        int expectedTotalQuantity = 0;

        for (CartResponse.Product responseProduct : responseProducts) {

            String id = responseProduct.getId();

            CartResponse.Product oldProduct = oldMap.get(id);
            Integer requestQuantity = requestMap.get(id);

            // CASE 1: product just in old cart
            if (oldProduct != null && requestQuantity == null) {

                softAssert.assertEquals(responseProduct.getQuantity(), oldProduct.getQuantity(),
                        "Old product quantity changed id=" + id);

                softAssert.assertEquals(responseProduct.getTitle(), oldProduct.getTitle());
                softAssert.assertEquals(responseProduct.getPrice(), oldProduct.getPrice());
                softAssert.assertEquals(responseProduct.getDiscountPercentage(), oldProduct.getDiscountPercentage());
                softAssert.assertEquals(responseProduct.getThumbnail(), oldProduct.getThumbnail());
            }

            // CASE 2: product in update cart with id exist in old cart (merge quantity)
            else if (oldProduct != null) {

                int expectedQuantity = oldProduct.getQuantity() + requestQuantity;

                softAssert.assertEquals(responseProduct.getQuantity(), expectedQuantity,
                        "Merged quantity incorrect id=" + id);

                // giữ nguyên thông tin cũ
                softAssert.assertEquals(responseProduct.getTitle(), oldProduct.getTitle());
                softAssert.assertEquals(responseProduct.getPrice(), oldProduct.getPrice());
                softAssert.assertEquals(responseProduct.getDiscountPercentage(), oldProduct.getDiscountPercentage());
                softAssert.assertEquals(responseProduct.getThumbnail(), oldProduct.getThumbnail());
            }

            // CASE 3: product new
            else {

                int expectedQuantity = requestQuantity;

                Response productInform = productsService.getASingleProduct(id);
                GetASingleProductResponse product = productInform.as(GetASingleProductResponse.class);

                softAssert.assertEquals(responseProduct.getQuantity(), expectedQuantity);
                softAssert.assertEquals(responseProduct.getTitle(), product.getTitle());
                softAssert.assertEquals(responseProduct.getPrice(), product.getPrice());
                softAssert.assertEquals(responseProduct.getDiscountPercentage(), product.getDiscountPercentage());
                softAssert.assertEquals(responseProduct.getThumbnail(), product.getThumbnail());
            }

            expectedTotal += responseProduct.getTotal();
            expectedDiscountedTotal += responseProduct.getDiscountedPrice();
            expectedTotalQuantity += responseProduct.getQuantity();
        }

        // verify cart level
        softAssert.assertEquals(response.getUserId(), oldCart.getUserId());
        softAssert.assertEquals(response.getTotalQuantity(), expectedTotalQuantity);
        softAssert.assertEquals(response.getTotalProducts(), responseProducts.size());

        softAssert.assertAll();
    }
}

package assertions.carts;

import constants.carts.CartsConstant;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.carts.AddANewCartRequest;
import models.carts.AddANewCartResponse;
import models.products.GetASingleProductResponse;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import services.ProductsService;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CartsAssertion {
    private static ProductsService productsService = new ProductsService();

    public static void verifyAddToCartSuccessful(Response addANewCartResponse, AddANewCartRequest addANewCartRequest) {

        addANewCartResponse
                .then()
                .log().ifValidationFails()
                .statusCode(201) // check status code = 201
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(CartsConstant.ADD_A_NEW_CART_SUCCESS_SCHEMA)) // check response success schema
        ;
        SoftAssert softAssert = new SoftAssert();

        AddANewCartResponse response = addANewCartResponse.as(AddANewCartResponse.class);
        //verify list product size in request and response
        List<AddANewCartResponse.Product> productsResponse = response.getProducts();
        List<AddANewCartRequest.Product> productsRequest = addANewCartRequest.getProducts();
        if (productsRequest == null && productsResponse.isEmpty()) {

            softAssert.assertEquals(response.getUserId(), Integer.parseInt(addANewCartRequest.getUserId()), "UserId incorrect");
            softAssert.assertEquals(response.getTotal(), 0D, "Total incorrect");
            softAssert.assertEquals(response.getDiscountedTotal(), 0D, "DiscountedTotal incorrect");
            softAssert.assertEquals(response.getTotalQuantity(), 0, "TotalQuantity incorrect");
            softAssert.assertEquals(response.getTotalProducts(), 0, "TotalProducts incorrect");

            softAssert.assertAll();
        } else {
            Assert.assertEquals(productsResponse.size(), productsRequest.size(), "The quantity of products in response is different");

            //sort 2 lists by id
            productsResponse.sort(Comparator.comparing(AddANewCartResponse.Product::getId));
            productsRequest.sort(Comparator.comparing(AddANewCartRequest.Product::getId));

            //verify information
            Response productInform;
            GetASingleProductResponse product;
            int expectedQuantity;
            double expectedTotal = 0;
            double expectedDiscountedPrice = 0;
            double expectedDiscountedTotal = 0;
            int expectedTotalQuantity = 0;
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
            softAssert.assertEquals(response.getUserId(), Integer.parseInt(addANewCartRequest.getUserId()), "UserId incorrect");
            softAssert.assertEquals(response.getTotal(), expectedTotal, "Total incorrect");
            softAssert.assertEquals(response.getDiscountedTotal(), expectedDiscountedTotal, "DiscountedTotal incorrect");
            softAssert.assertEquals(response.getTotalQuantity(), expectedTotalQuantity, "TotalQuantity incorrect");
            softAssert.assertEquals(response.getTotalProducts(), productsRequest.size(), "TotalProducts incorrect");

            softAssert.assertAll();
        }
    }

    public static void verifyAddToCartUnsuccessful(Response addANewCartResponse, String message, int statusCode) {
        addANewCartResponse
                .then()
                .log().ifValidationFails()
                .statusCode(statusCode)
                .body("message", equalTo(message));

    }
}

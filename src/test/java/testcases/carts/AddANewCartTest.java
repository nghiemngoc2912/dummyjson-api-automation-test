package testcases.carts;

import assertions.carts.CartsAssertion;
import base.BaseTest;
import constants.carts.AddANewCartMessage;
import io.restassured.response.Response;
import models.carts.AddANewCartRequest;
import org.testng.annotations.Test;
import services.CartsService;
import testdata.carts.CartsTestData;

import java.util.ArrayList;

public class AddANewCartTest extends BaseTest {
    CartsService cartsService = new CartsService();

    @Test(description = "carts_aanc_001 - Add A New Cart Successfully")
    public void carts_aanc_001_addANewCartSuccessfully() {
        //test data
        AddANewCartRequest addANewCartRequest = CartsTestData.validAddANewCartRequest();
        //call api add new cart
        Response addANewCartResponse = cartsService.addANewCart(addANewCartRequest);

        //verify cart response success
        CartsAssertion.verifyAddToCartSuccessful(addANewCartResponse, addANewCartRequest);
    }

    @Test(description = "carts_aanc_002 - Add A New Cart Unsuccessfully: UserId not exist")
    public void carts_aanc_002_userIdNotExist() {
        //test data
        AddANewCartRequest addANewCartRequest = CartsTestData.userIdNotExistAddANewCartRequest();
        //call api add new cart
        Response addANewCartResponse = cartsService.addANewCart(addANewCartRequest);

        //verify cart response
        CartsAssertion.verifyAddToCartUnsuccessful(addANewCartResponse, String.format(AddANewCartMessage.USERID_NOT_EXIST, addANewCartRequest.getUserId()), 404);
    }

    @Test(description = "carts_aanc_003 - Add A New Cart Successfully: ProductId not exist")
    public void carts_aanc_003_productIdNotExist() {
        //test data
        AddANewCartRequest addANewCartRequest = CartsTestData.productIdNotExistAddANewCartRequest();
        //call api add new cart
        Response addANewCartResponse = cartsService.addANewCart(addANewCartRequest);

        //verify cart response
        CartsAssertion.verifyAddToCartSuccessful(addANewCartResponse, CartsTestData.productIdNotExistAddANewCartRequestAfterRemovingInvalidProductId(addANewCartRequest.getUserId()));
    }

    @Test(description = "carts_aanc_004 - Add A New Cart UnSuccessfully: UserId null")
    public void carts_aanc_004_userIdNull() {
        //test data
        AddANewCartRequest addANewCartRequest = CartsTestData.userIdNullAddANewCartRequest();
        //call api add new cart
        Response addANewCartResponse = cartsService.addANewCart(addANewCartRequest);

        //verify cart response
        CartsAssertion.verifyAddToCartUnsuccessful(addANewCartResponse, AddANewCartMessage.USERID_MISSING, 400);
    }

    @Test(description = "carts_aanc_005 - Add A New Cart Successfully: ProductId null")
    public void carts_aanc_005_productIdNull() {
        //test data
        AddANewCartRequest addANewCartRequest = CartsTestData.productIdNullAddANewCartRequest();
        //call api add new cart
        Response addANewCartResponse = cartsService.addANewCart(addANewCartRequest);

        addANewCartRequest.setProducts(
                new ArrayList<>(
                        addANewCartRequest.getProducts()
                                .stream()
                                .filter(p -> p.getId() != null)
                                .toList()
                )
        );

        //verify cart response
        CartsAssertion.verifyAddToCartSuccessful(addANewCartResponse, addANewCartRequest);
    }

    @Test(description = "carts_aanc_006 - Add A New Cart Successfully: Quantity null")
    public void carts_aanc_006_quantityNull() {
        //test data
        AddANewCartRequest addANewCartRequest = CartsTestData.quantityNullAddANewCartRequest();
        //call api add new cart
        Response addANewCartResponse = cartsService.addANewCart(addANewCartRequest);

        addANewCartRequest.setProducts(
                new ArrayList<>(
                        addANewCartRequest.getProducts()
                                .stream()
                                .map(p -> {
                                    if (p.getQuantity() == null) {
                                        p.setQuantity("1");
                                    }
                                    return p;
                                })
                                .toList()
                )
        );

        //verify cart response
        CartsAssertion.verifyAddToCartSuccessful(addANewCartResponse, addANewCartRequest);
    }

}

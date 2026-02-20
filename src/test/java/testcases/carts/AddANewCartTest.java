package testcases.carts;

import assertions.auth.AuthAssertion;
import assertions.carts.CartsAssertion;
import base.BaseTest;
import constants.auth.LoginMessage;
import constants.carts.CartsMessage;
import io.restassured.response.Response;
import models.auth.LoginRequest;
import models.carts.AddANewCartRequest;
import org.testng.annotations.Test;
import services.AuthService;
import services.CartsService;
import testdata.auth.AuthTestData;
import testdata.carts.CartsTestData;

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
        CartsAssertion.verifyAddToCartUnsuccessful(addANewCartResponse, String.format(CartsMessage.USERID_NOT_EXIST, addANewCartRequest.getUserId()), 404);
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

}

package testcases.carts;

import assertions.auth.AuthAssertion;
import assertions.carts.CartsAssertion;
import base.BaseTest;
import constants.auth.LoginMessage;
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
        //call api login
        Response addANewCartResponse = cartsService.addANewCart(addANewCartRequest);

        //verify cart response success
        CartsAssertion.verifyAddToCartSuccessful(addANewCartResponse, addANewCartRequest);
    }

}

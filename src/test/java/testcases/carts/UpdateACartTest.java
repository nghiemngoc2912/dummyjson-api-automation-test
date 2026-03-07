package testcases.carts;

import assertions.carts.CartsAssertion;
import base.BaseTest;
import io.restassured.response.Response;
import models.carts.UpdateACartRequest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.CartsService;
import services.ProductsService;
import testdata.carts.CartsTestData;

public class UpdateACartTest extends BaseTest {
    CartsService cartsService = new CartsService();
    ProductsService productsService = new ProductsService();

    @DataProvider(name = "mergeDataFalse")
    public Object[] mergeDataFalse() {
        return new Object[]{
                false,
                null,
                ""
        };
    }

    @Test(dataProvider = "mergeDataFalse", description = "carts_uac_001, carts_uac_003 - Update a Cart Successfully: merge = false/null/empty")
    public void carts_uac_001_UpdateACartSuccessfully_MergeEqFalse(Object mergeValue) {
        //test data
        String cartId = cartsService.getAValidCartId();
        UpdateACartRequest updateACartRequest = CartsTestData.validUpdateACartRequest();
        updateACartRequest.setMerge(mergeValue);

        //call api get the old cart
        Response oldCartResponse = cartsService.getASingleCart(cartId);
        //call api update new cart
        Response updateACartResponse = cartsService.updateACart(cartId, updateACartRequest);
        //verify cart response success
        CartsAssertion.verifyUpdateACartSuccessful_MergeEqFalse(updateACartResponse, updateACartRequest, oldCartResponse);
    }

    @DataProvider(name = "mergeDataTrue")
    public Object[] mergeDataTrue() {
        return new Object[]{
                true,
                "abc"
        };
    }
    @Test(dataProvider = "mergeDataTrue", description = "carts_uac_002, carts_uac_004 - Update a Cart Successfully: merge = true/<>true/false")
    public void carts_uac_002_UpdateACartSuccessfully_MergeEqTrue(Object mergeValue) {
        //test data
        String cartId = cartsService.getAValidCartId();
        UpdateACartRequest updateACartRequest = CartsTestData.validUpdateACartRequest();
        updateACartRequest.setMerge(mergeValue);

        //call api get the old cart
        Response oldCartResponse = cartsService.getASingleCart(cartId);
        //call api update new cart
        Response updateACartResponse = cartsService.updateACart(cartId, updateACartRequest);

        //verify cart response success
        CartsAssertion.verifyUpdateACartSuccessful_MergeEqTrue(updateACartResponse, updateACartRequest, oldCartResponse);
    }
}

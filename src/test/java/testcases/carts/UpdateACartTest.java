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
    public void carts_uac_001_003_UpdateACartSuccessfully_MergeEqFalse(Object mergeValue) {
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
    public Object[][] mergeDataTrue() {
        return new Object[][]{
                {true, false},   // merge=true
                {"abc", false},  // merge<>true/false
                {true, true}
        };
    }

    @Test(dataProvider = "mergeDataTrue", description = "carts_uac_002, carts_uac_004, carts_uac_007 - Update a Cart Successfully: merge = true/<>true/false")
    public void carts_uac_002_004_007_UpdateACartSuccessfully_MergeEqTrue(Object mergeValue, boolean useOldProduct) {
        //test data
        String cartId = cartsService.getAValidCartId();
        UpdateACartRequest updateACartRequest;

        if (useOldProduct) {
            // carts_uac_007
            updateACartRequest = CartsTestData.productExistInOldCartUpdateRequest(cartId);
        } else {
            updateACartRequest = CartsTestData.validUpdateACartRequest();
        }
        updateACartRequest.setMerge(mergeValue);

        //call api get the old cart
        Response oldCartResponse = cartsService.getASingleCart(cartId);
        //call api update new cart
        Response updateACartResponse = cartsService.updateACart(cartId, updateACartRequest);

        //verify cart response success
        CartsAssertion.verifyUpdateACartSuccessful_MergeEqTrue(updateACartResponse, updateACartRequest, oldCartResponse);
    }

    @DataProvider(name = "invalidProductIdData")
    public Object[][] invalidProductIdData() {
        return new Object[][]{
                {"999999999"},
                {null}
        };
    }

    @Test(dataProvider = "invalidProductIdData",
            description = "carts_uac_005, carts_uac_006 - Update a Cart Successfully: productId invalid")
    public void carts_uac_005_006_invalidProductId(Object productId) {

        // test data
        String cartId = cartsService.getAValidCartId();
        UpdateACartRequest updateACartRequest = CartsTestData.validUpdateACartRequest();

        // set invalid productId
        updateACartRequest.getProducts().get(0).setId((String) productId);

        // call api get the old cart
        Response oldCartResponse = cartsService.getASingleCart(cartId);

        // call api update new cart
        Response updateACartResponse = cartsService.updateACart(cartId, updateACartRequest);

        // verify cart response success
        CartsAssertion.verifyUpdateACartSuccessful_MergeEqFalse(
                updateACartResponse,
                updateACartRequest,
                oldCartResponse
        );
    }

    @DataProvider(name = "invalidQuantityData")
    public Object[][] invalidQuantityData() {
        return new Object[][]{
                {null},
                {"-1"},
                {"0"},
                {"NaN"},
                {"abc"}
        };
    }

    @Test(dataProvider = "invalidQuantityData", description = "Update a Cart Successfully: quantity invalid")
    public void carts_ua008_009_010_011_012_invalid_quantity(String quantity) {

        String cartId = cartsService.getAValidCartId();

        UpdateACartRequest updateACartRequest =
                CartsTestData.validUpdateACartRequest();
        updateACartRequest.getProducts().get(0).setQuantity(quantity);

        // call api get the old cart
        Response oldCartResponse = cartsService.getASingleCart(cartId);

        // call api update new cart
        Response updateACartResponse = cartsService.updateACart(cartId, updateACartRequest);

        // verify cart response success
        CartsAssertion.verifyUpdateACartSuccessful_MergeEqFalse(
                updateACartResponse,
                updateACartRequest,
                oldCartResponse
        );
    }

    @Test(description = "Update a Cart Successfully: quantity exceed stock")
    public void carts_ua013_invalid_quantity_exceed_stock() {

        String cartId = cartsService.getAValidCartId();

        UpdateACartRequest updateACartRequest =
                CartsTestData.quantityGtStockUpdateACartRequest();

        // call api get the old cart
        Response oldCartResponse = cartsService.getASingleCart(cartId);

        // call api update new cart
        Response updateACartResponse = cartsService.updateACart(cartId, updateACartRequest);

        // verify cart response success
        CartsAssertion.verifyUpdateACartSuccessful_MergeEqFalse(
                updateACartResponse,
                updateACartRequest,
                oldCartResponse
        );
    }
}

package apis;

import constants.carts.CartsConstant;
import io.restassured.response.Response;

public class CartsApi extends BaseApi{
    public Response addANewCart(Object body){
        return request()
                .body(body)
                .post(CartsConstant.ADD_A_NEW_CART_ENDPOINT);
    }
}

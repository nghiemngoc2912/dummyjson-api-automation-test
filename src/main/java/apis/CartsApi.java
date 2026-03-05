package apis;

import constants.carts.CartsConstant;
import io.restassured.response.Response;

public class CartsApi extends BaseApi {
    public Response addANewCart(Object body) {
        return request()
                .body(body)
                .post(CartsConstant.ADD_A_NEW_CART_ENDPOINT);
    }

    public Response updateACart(String cartId, Object body) {
        return request()
                .body(body)
                .put(CartsConstant.COMMON_CART_ENDPOINT + "/" + cartId);
    }

    public Response getAllCart(){
        return request()
                .get(CartsConstant.COMMON_CART_ENDPOINT);
    }

    public Response getASingleCart(String cartId){
        return request()
                .get(CartsConstant.COMMON_CART_ENDPOINT + "/" + cartId);
    }
}

package apis;

import constants.products.ProductsConstant;
import io.restassured.response.Response;

public class ProductsApi extends BaseApi {
    public Response getASingleProduct(String id) {
        return request()
                .get(ProductsConstant.GET_A_SINGLE_PRODUCT_ENDPOINT + "/" + id);
    }
}

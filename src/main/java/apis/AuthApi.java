package apis;

import constants.auth.AuthConstant;
import io.restassured.response.Response;

public class AuthApi extends BaseApi {
    public Response login(Object body) {
        return request()
                .body(body)
                .post(AuthConstant.LOGIN_ENDPOINT);
    }

    public Response getCurrentAuthUser(String token) {
        return request()
                .header("Authorization", "Bearer " + token)
                .get(AuthConstant.GET_CURRENT_USER_ENDPOINT);
    }
}

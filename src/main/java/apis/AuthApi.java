package apis;

import apis.BaseApi;
import constants.auth.AuthConstants;
import io.restassured.response.Response;

public class AuthApi extends BaseApi {
    public Response login(Object body) {
        return request()
                .body(body)
                .post(AuthConstants.LOGIN_ENDPOINT);
    }

    public Response getCurrentAuthUser(String token) {
        return request()
                .header("Authorization", "Bearer " + token)
                .get(AuthConstants.GET_CURRENT_USER_ENDPOINT);
    }
}

package apis;

import constants.users.UsersConstant;
import io.restassured.response.Response;

public class UsersApi extends BaseApi{
    public Response getAllUsers(){
        return request()
                .get(UsersConstant.GET_ALL_USERS_ENDPOINT);
    }
}

package testcases.auth;

import assertions.auth.AuthAssertion;
import base.BaseTest;
import constants.auth.GetCurrentUserMessage;
import io.restassured.response.Response;
import models.auth.LoginRequest;
import org.testng.annotations.Test;
import services.AuthService;
import testdata.auth.AuthTestData;

public class GetCurrentUserTest extends BaseTest {
    AuthService authService = new AuthService();

    @Test(description = "auth_getCurrentUser_001 - Verify get current user response successful")
    public void auth_getCurrentUser_001_loginSuccessfully() {
        //test data
        LoginRequest loginRequest = AuthTestData.validLoginRequest();
        //call api login
        Response loginResponse = authService.login(loginRequest);

        //call api current user

        Response currentUserResponse = authService.getCurrentUser(loginResponse);

        //verify current user correct
        AuthAssertion.verifyCurrentUser(currentUserResponse, loginResponse);
    }

    @Test(description = "auth_getCurrentUser_002 - Verify get current user response unsuccessful: Token Null")
    public void auth_getCurrentUser_002_tokenNull() {
        //call api current user
        String token = null;
        Response currentUserResponse = authService.getCurrentUser(token);

        //verify current user correct
        AuthAssertion.verifyCurrentUserUnsuccessful(currentUserResponse, GetCurrentUserMessage.TOKEN_INVALID);
    }

    @Test(description = "auth_getCurrentUser_003 - Verify get current user response unsuccessful: Token Not Match")
    public void auth_getCurrentUser_003_tokenNotMatch() {
        //call api current user
        String token = "@";
        Response currentUserResponse = authService.getCurrentUser(token);

        //verify current user correct
        AuthAssertion.verifyCurrentUserUnsuccessful(currentUserResponse, GetCurrentUserMessage.TOKEN_INVALID);
    }

}

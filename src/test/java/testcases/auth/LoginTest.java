package testcases.auth;

import assertions.auth.AuthAssertion;
import base.BaseTest;
import io.restassured.response.Response;
import models.auth.LoginRequest;
import org.testng.annotations.Test;
import services.AuthService;
import testdata.auth.AuthTestData;

public class LoginTest extends BaseTest {
    AuthService authService = new AuthService();

    @Test(description = "auth_login_001 - Login successfully")
    public void auth_login_001_loginSuccessfully(){
        //test data
        LoginRequest loginRequest = AuthTestData.validLoginRequest();
        //call api login
        Response loginResponse = authService.login(loginRequest);

        //verify login response success
        AuthAssertion.verifyLoginSuccess(loginResponse, loginRequest);

        //call api current user

        Response currentUserResponse = authService.getCurrentUser(loginResponse);

        //verify current user correct
        AuthAssertion.verifyCurrentUser(currentUserResponse, loginResponse);
    }
}

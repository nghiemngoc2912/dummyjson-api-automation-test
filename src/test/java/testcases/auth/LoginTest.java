package testcases.auth;

import assertions.auth.AuthAssertion;
import base.BaseTest;
import constants.auth.LoginMessage;
import io.restassured.response.Response;
import models.auth.LoginRequest;
import org.testng.annotations.Test;
import services.AuthService;
import testdata.auth.AuthTestData;

public class LoginTest extends BaseTest {
    AuthService authService = new AuthService();

    @Test(description = "auth_login_001 - Login successfully")
    public void auth_login_001_loginSuccessfully() {
        //test data
        LoginRequest loginRequest = AuthTestData.validLoginRequest();
        //call api login
        Response loginResponse = authService.login(loginRequest);

        //verify login response success
        AuthAssertion.verifyLoginSuccess(loginResponse, loginRequest);
    }

    @Test(description = "auth_login_002 - Login unsuccessfully - username null")
    public void auth_login_002_usernameNull() {
        //test data
        LoginRequest loginRequest = AuthTestData.nullUsernameLoginRequest();
        //call api login
        Response loginResponse = authService.login(loginRequest);
        //verify login response unsuccess
        AuthAssertion.verifyLoginUnsuccess(loginResponse, LoginMessage.USERNAME_PASSWORD_REQUIRED);
    }

    @Test(description = "auth_login_003 - Login unsuccessfully - password null")
    public void auth_login_003_passwordNull() {
        //test data
        LoginRequest loginRequest = AuthTestData.nullPasswordLoginRequest();
        //call api login
        Response loginResponse = authService.login(loginRequest);
        //verify login response unsuccess
        AuthAssertion.verifyLoginUnsuccess(loginResponse, LoginMessage.USERNAME_PASSWORD_REQUIRED);
    }

    @Test(description = "auth_login_004 - Login unsuccessfully - Username not exist")
    public void auth_login_004_usernameNotExist() {
        //test data
        LoginRequest loginRequest = AuthTestData.userNameNotExistLoginRequest();
        //call api login
        Response loginResponse = authService.login(loginRequest);
        //verify login response unsuccess
        AuthAssertion.verifyLoginUnsuccess(loginResponse, LoginMessage.ACCOUNT_NOT_EXIST);
    }

    @Test(description = "auth_login_005 - Login unsuccessfully - Password not match")
    public void auth_login_005_passwordNotMatch() {
        //test data
        LoginRequest loginRequest = AuthTestData.passwordNotMatchLoginRequest();
        //call api login
        Response loginResponse = authService.login(loginRequest);
        //verify login response unsuccess
        AuthAssertion.verifyLoginUnsuccess(loginResponse, LoginMessage.ACCOUNT_NOT_EXIST);
    }

}

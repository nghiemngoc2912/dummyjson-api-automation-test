package testdata.auth;

import models.auth.LoginRequest;

public class AuthTestData {

    public static LoginRequest validLoginRequest() {
        String username = "emilys", password = "emilyspass";
        return new LoginRequest(username, password);
    }

    public static LoginRequest nullUsernameLoginRequest() {
        String username = null, password = "emilyspass";
        return new LoginRequest(username, password);
    }

}

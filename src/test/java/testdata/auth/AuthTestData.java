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

    public static LoginRequest nullPasswordLoginRequest() {
        String username = "emilys", password = null;
        return new LoginRequest(username, password);
    }

    public static LoginRequest userNameNotExistLoginRequest() {
        String username = "emily", password = "emilyspass";
        return new LoginRequest(username, password);
    }

    public static LoginRequest passwordNotMatchLoginRequest() {
        String username = "emilys", password = "emilyspasss";
        return new LoginRequest(username, password);
    }
}

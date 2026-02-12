package services;

import apis.AuthApi;
import io.restassured.response.Response;
import models.auth.LoginRequest;

public class AuthService {
    private final AuthApi client = new AuthApi();

    public Response login(LoginRequest request) {
        return client.login(request);
    }

    public Response getCurrentUser(Response loginResponse) {
        String token = loginResponse.jsonPath().getString("accessToken");
        return client.getCurrentAuthUser(token);
    }

    public Response getCurrentUser(String token) {
        return client.getCurrentAuthUser(token);
    }

} 

package assertions.auth;

import constants.auth.AuthConstants;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.auth.LoginRequest;

import static org.hamcrest.Matchers.equalTo;

public class AuthAssertion {

    public static void verifyLoginSuccess(Response loginResponse, LoginRequest loginRequest) {
        loginResponse.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(AuthConstants.LOGIN_SUCCESS_SCHEMA))
                .body("username", equalTo(loginRequest.getUsername()))
        ;
    }

    public static void verifyCurrentUser(Response currentUserResponse, Response loginResponse) {
        var loginJson = loginResponse.jsonPath();

        currentUserResponse.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("id", equalTo(loginJson.getInt("id")))
                .body("username", equalTo(loginJson.getString("username")))
                .body("email", equalTo(loginJson.getString("email")))
                .body("firstName", equalTo(loginJson.getString("firstName")))
                .body("lastName", equalTo(loginJson.getString("lastName")))
                .body("gender", equalTo(loginJson.getString("gender")))
                .body("image", equalTo(loginJson.getString("image")))
        ;
    }
}

package apis;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi {
    protected RequestSpecification request(){
        return RestAssured.given()
                .baseUri(ConfigManager.get("base.url"))
                .contentType("application/json");
    }
}

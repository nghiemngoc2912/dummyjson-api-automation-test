package services;

import apis.UsersApi;
import io.restassured.response.Response;

import java.util.List;
import java.util.Random;

public class UsersService {
    private Random random = new Random();
    private final UsersApi client = new UsersApi();

    public Response getAllUsers() {
        return client.getAllUsers();
    }

    public String getAValidUserId() {
        List<Integer> users = client.getAllUsers().body().jsonPath().getList("users.id");
        return Integer.toString(users.get(random.nextInt(users.size())));
    }
} 

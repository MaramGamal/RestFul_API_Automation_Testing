package Utils;
import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class TokenGenerator {
    public static String generateToken() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", ConfigReader.get("username"));
        credentials.put("password", ConfigReader.get("password"));

        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(credentials)
                .post("/auth");

        return response.jsonPath().getString("token");
    }

}

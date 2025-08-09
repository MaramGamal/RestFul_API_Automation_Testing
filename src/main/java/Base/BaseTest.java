package Base;
import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.config.OAuthConfig;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Utils.TokenGenerator;

public class BaseTest {
    protected RequestSpecification request;

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = ConfigReader.get("base.url");
        String token = TokenGenerator.generateToken();
        request = RestAssured
                .given()
                .contentType("application/json")
                .header("Cookie", "token=" + token);
    }
}



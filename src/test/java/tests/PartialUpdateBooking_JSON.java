package tests;

import Base.BaseTest;
import Utils.TokenGenerator;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PartialUpdateBooking_JSON extends BaseTest {
    @Test
    @Description("Partial update booking with JSON body using valid basic auth")
    public void partialUpdateBooking_JSON() {
        String token = TokenGenerator.generateToken();


        String partialUpdateBody = """
            {
                "firstname" : "James",
                "lastname" : "Brown"
            }
        """;

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .cookie("token", token)
                .body(partialUpdateBody)
                .when()
                .patch("https://restful-booker.herokuapp.com/booking/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("James"))
                .body("lastname", equalTo("Brown"));
    }
}

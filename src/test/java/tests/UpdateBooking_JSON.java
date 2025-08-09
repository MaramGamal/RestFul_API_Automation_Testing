package tests;
import Base.BaseTest;
import Utils.TokenGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;


@Epic("Booking API Tests")
@Feature("UpdateBooking Endpoint Tests")
public class UpdateBooking_JSON extends BaseTest {
    @Story("UpdateBooking_JSON")
    @Test(description ="Update booking with valid token and valid data")
    public void UpdateBooking_JSON() {

        String token = TokenGenerator.generateToken();

        String updatedBody = """
                {
                    "firstname" : "James",
                    "lastname" : "Brown",
                    "totalprice" : 111,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2018-01-01",
                        "checkout" : "2019-01-01"
                    },
                    "additionalneeds" : "Breakfast"
                }
        """;

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .cookie("token", token)
                .body(updatedBody)
                .when()
                .put("/booking/" + 1)
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("James"))
                .body("totalprice", equalTo(111));
    }
}

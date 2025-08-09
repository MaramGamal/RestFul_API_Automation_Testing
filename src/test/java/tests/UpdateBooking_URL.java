package tests;

import Base.BaseTest;
import Utils.TokenGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@Epic("Booking(URL) API Tests")
@Feature("UpdateBooking Endpoint Tests")

public class UpdateBooking_URL extends BaseTest {
    @Test(description = "Verify updating booking ID 1 with valid form-urlencoded data and basic auth")
    @Description("This test updates booking ID 1 using form-urlencoded data and verifies the updated fields in the response.")
    @Story("Booking Management - Update booking details with form-urlencoded data")
    public void UpdateBookingWithFormUrlEncoded() {
        String token = TokenGenerator.generateToken();


        given()
                .contentType("application/x-www-form-urlencoded")
                .accept("application/x-www-form-urlencoded")
                .auth().preemptive().basic("admin", "password123")
                .formParam("firstname", "Jim")
                .formParam("lastname", "Brown")
                .formParam("totalprice", 111)
                .formParam("depositpaid", true)
                .formParam("bookingdates[checkin]", "2018-01-01")
                .formParam("bookingdates[checkout]", "2018-01-02")
                .when()
                .put("/booking/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("Jim"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(111));
    }

}

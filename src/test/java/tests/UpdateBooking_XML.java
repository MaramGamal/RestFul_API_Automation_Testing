package tests;

import Base.BaseTest;
import Utils.TokenGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("Booking(XML) API Tests")
@Feature("UpdateBooking Endpoint Tests")
public class UpdateBooking_XML extends BaseTest {
    @Test(description = "Update booking with valid token and XML body, booking ID = 1")
    @Description("This test updates booking ID=1 using a PUT request with XML payload and verifies the response fields.")
    @Story("As a user, I want to update booking details using XML format with valid authentication to keep booking information accurate.")
    public void UpdateBookingWithXML() {
        String token = TokenGenerator.generateToken();

        String updatedBodyXML = """
                <booking>
                   <firstname>James</firstname>
                   <lastname>Brown</lastname>
                   <totalprice>111</totalprice>
                   <depositpaid>true</depositpaid>
                   <bookingdates>
                     <checkin>2018-01-01</checkin>
                     <checkout>2019-01-01</checkout>
                   </bookingdates>
                   <additionalneeds>Breakfast</additionalneeds>
                 </booking>
                """;

        given()
                .header("Content-Type", "text/xml")
                .header("Accept", "application/xml")
                .cookie("token", token)
                .auth().preemptive().basic("admin", "password123")
                .body(updatedBodyXML)
                .when()
                .put("/booking/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("booking.firstname", equalTo("James"))
                .body("booking.totalprice", equalTo(111))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2018-01-01"));
    }


}

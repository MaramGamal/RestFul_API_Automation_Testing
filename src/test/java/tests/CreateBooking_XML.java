package tests;
import Base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
@Epic("Booking API Tests")
@Feature("Create Booking via XML")
public class CreateBooking_XML extends BaseTest {




    @Story("Check response time for Create Booking via XML")
    @Test(description = "Verify the response time of /booking endpoint")
    public void checkResponseTime() {

        String bookingXML = """
                <booking>
                    <firstname>John</firstname>
                    <lastname>Doe</lastname>
                    <totalprice>150</totalprice>
                    <depositpaid>true</depositpaid>
                    <bookingdates>
                        <checkin>2025-08-01</checkin>
                        <checkout>2025-08-05</checkout>
                    </bookingdates>
                    <additionalneeds>Breakfast</additionalneeds>
                </booking>
                """;

        Allure.step("Send POST request and measure response time", () -> {
            given()
                    .contentType("application/xml")
                    .body(bookingXML)
                    .when()
                    .post("/booking")
                    .then()
                    .log().all()
                    .time(lessThan(4000L));
        });

        Allure.step("Validate response time is under 4 seconds");
    }

    @Story("Check bookingid exists and is a string in XML response")
    @Test(description = "Validate bookingid is present in XML response and is a string")
    public void verifyBookingIdIsPresentAndString_XML() {

        String bookingXML = """
                        <firstname>John</firstname>
                    <lastname>Doe</lastname>
                    <totalprice>150</totalprice>
                    <depositpaid>true</depositpaid>
                    <bookingdates>
                        <checkin>2025-08-01</checkin>
                        <checkout>2025-08-05</checkout>
                    </bookingdates>
                    <additionalneeds>Breakfast</additionalneeds>
                </booking>
                """;

        Response response = Allure.step("Send POST request to create booking via XML", () ->
                given()
                        .contentType("application/xml")
                        .accept("application/xml")
                        .body(bookingXML)
                        .when()
                        .post("/booking")
                        .then()
                        .log().all()

                        .extract().response()
        );


        Allure.step("Validate bookingid exists and is a string in XML", () -> {
            String bookingId = response.xmlPath().getString("created-booking.bookingid");

            Assert.assertNotNull(bookingId, "bookingid is missing in the XML response");
            Assert.assertTrue(bookingId instanceof String, "bookingid is not a String");
        });
    }
}

package APITest1;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class getBooking2 {

	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Step("Test cases for GetBooking details")
	public void getBooking() {
		//Get Response with Booking ID
		Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/1");
		response.print();
		
		//Verify Response status code 200
		Assert.assertEquals(response.statusCode(), 200, "Status code should be 200 but it is not");
		
		//Schema Validation
		RestAssured.given()
		.when()
		.get("https://restful-booker.herokuapp.com/booking/1")
		.then()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaWithoutBookingId.json"));
				
	}
}

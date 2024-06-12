package APITest1;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class dummy1 {
	
	@Test
	public void practice1() {
			
		//Create Booking
		JSONObject body = new JSONObject();
		body.put("firstname", "John");
		body.put("lastname", "Antony");
		body.put("totalprice", 500);
		body.put("depositpaid", true);
		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2024-06-03");
		bookingdates.put("checkout", "2024-06-05");
		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "Breakfast");
		
		Response response = RestAssured.given()
							.when()
							.header("Content-Type", "application/json")
							.body(body.toString())
							.post("https://restful-booker.herokuapp.com/booking");
					
			response.print();
					
			//Verification
			Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not.");
					
			int bookingid1 = response.jsonPath().getInt("bookingid");
		
			Response response1 = RestAssured.given()
								 .auth().preemptive().basic("admin", "password123")
								 .delete("https://restful-booker.herokuapp.com/booking/" +bookingid1);
			
			//Verification
			Assert.assertEquals(response1.getStatusCode(), 201, "Status code should be 200 but it is not.");
			
			response1.print();
			
	}
	
}
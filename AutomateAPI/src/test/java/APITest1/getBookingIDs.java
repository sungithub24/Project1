package APITest1;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class getBookingIDs {

	@Test
	public void getBookingIdsTest() {
		//Get Response with Booking ID
		Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
		response.print();
		
		//Verify Response status code 200
		Assert.assertEquals(response.statusCode(), 200, "Status code should be 200 but it is not");
		
		//Verify atleast 1 booking ID in Response
		List<Integer> BookingIds = response.jsonPath().getList("bookingid");
		Assert.assertFalse(BookingIds.isEmpty(), "List of Booking IDs is empty but it shouldn't be");
			
	}
}

package APITest1;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class HealthCheck1 {

	@Test
	public void ApiTestMethod1() {
				 RestAssured.given()
							.when()
							.get("https://restful-booker.herokuapp.com/ping")
							.then()
							.assertThat()
							.statusCode(201);	
	}
}

//given().when()
//.get("https://restful-booker.herokuapp.com/ping")
//.then()
//.assertThat()
//.statusCode(201);
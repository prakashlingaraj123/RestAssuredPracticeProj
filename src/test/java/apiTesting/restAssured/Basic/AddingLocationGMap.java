package apiTesting.restAssured.Basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

public class AddingLocationGMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
		body(Payload.locationdetails()).when().post("/maps/api/place/add/json").
			then().log().all().assertThat().statusCode(200).
			body("scope", equalTo("APP")).header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
		body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\"70 winter walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).header("Server", "Apache/2.4.41 (Ubuntu)");
		
		String responseGET = given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		System.out.println(responseGET);
		JsonPath js1 = new JsonPath(responseGET);
		String addressGET = js1.getString("address");
		System.out.println(addressGET);
		Assert.assertEquals(addressGET, "70 winter walk, USA");
	}

}

package apiTesting.restAssured.Basic;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class LibraryapiTest_JsonParametrization {
	@Test(dataProvider = "BookData")
	public void libraryAPIAddBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type", "application/json").body(LibraryPayload.libpayload(isbn,aisle))
				.when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String id =js.getString("ID");
		System.out.println(id);
		
		given().log().all().header("Content-Type", "application/json").body("{\r\n"
				+ " \r\n"
				+ "\"ID\" : \""+id+"\"\r\n"
				+ " \r\n"
				+ "} \r\n"
				+ "").when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"));
		
		
			
		
		
	}
	@DataProvider(name= "BookData")
	public Object[][] dataSet() {
		
		return new Object[][] {{"kba","9023"},{"cbb","8765"},{"ampp","7654"}};
	}
}

package apiTesting.restAssured.Basic;

public class LibraryPayload {
	public static String libpayload(String isbn, String aisle) {
				
		return	"{\r\n"
			+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
			+ "\"isbn\":\""+isbn+"\",\r\n"
			+ "\"aisle\":\""+aisle+"\",\r\n"
			+ "\"author\":\"John foer\"\r\n"
			+ "}";
			
	}
}

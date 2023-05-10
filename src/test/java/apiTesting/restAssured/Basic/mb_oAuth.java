package apiTesting.restAssured.Basic;
import static io.restassured.RestAssured.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import apiTesting.restAssured.pojo.AA_OnlineCourses_POJO;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
public class mb_oAuth {
	
	@Test
	public void oAuth() {
		//form url to get code.
		//url can be formed by Postman or Developer will give
		//Once url if formed put it in the browser and log in to get code in the usre. Usually this is done using selenium
		//https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVHEtk4p4u49YoHYV9WHz8_NBNWrxJqM0txyQV-gmFFE6SiOw7KbILIY-zECXShxO89kuw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent
	//code=4%2F0AVHEtk4p4u49YoHYV9WHz8_NBNWrxJqM0txyQV-gmFFE6SiOw7KbILIY-zECXShxO89kuw
	/*
	 * given().log().all().queryParam("auth_url",
	 * "https://accounts.google.com/o/oauth2/v2/auth") .queryParam("scope",
	 * "https://www.googleapis.com/auth/userinfo.email") .queryParam("client_id",
	 * "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
	 * .queryParam("response_type", "code") .queryParam("redirect_uri",
	 * "https://rahulshettyacademy.com/getCourse.php")
	 * .when().get("https://accounts.google.com/o/oauth2/v2/auth").then().extract().
	 * response().asString();
	 */
		//Selenium Part
		
		  WebDriverManager.chromedriver().setup(); ChromeOptions option = new
		  ChromeOptions(); option.addArguments("--remote-allow-origins=*"); WebDriver
		  driver = new ChromeDriver(option);
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		  driver.manage().window().maximize(); driver.get(
		  "https://accounts.google.com/o/oauth2/v2/auth?auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php"
		  ); 
		  driver.findElement(By.cssSelector("#identifierId")).sendKeys("yakshitheaswar");
		  driver.findElement(By.xpath("//span[normalize-space()='Next']")).click();
		  driver.findElement(By.xpath("//span[normalize-space()='Next']")).click();
		  
		  String currneURL = driver.getCurrentUrl(); String partURL =
		  currneURL.split("code=")[1]; String code = partURL.split("&scope")[0];
		 
		
		
		//Get access tocken 
	String tock = given().log().all().urlEncodingEnabled(false)
	.queryParam("code",code)
	.queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
	.queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
	.queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
	.queryParam("grant_type","authorization_code")
	.when().post("https://www.googleapis.com/oauth2/v4/token")
	.then().log().all().extract().response().asString();
	
		JsonPath js = new JsonPath(tock);
		String access_tocken = js.get("access_token");
		
		  String response = given().log().all().queryParam("access_tocken", access_tocken)
		  .when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		  
		  System.out.println(response);
		 //System.out.println(response.getLinkedin());
		 //System.out.println(response.getInstructor());
	}
	


}

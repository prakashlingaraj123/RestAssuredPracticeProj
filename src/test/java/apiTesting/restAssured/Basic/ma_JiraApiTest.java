package apiTesting.restAssured.Basic;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.apache.http.client.params.AllClientPNames;
import org.testng.Assert;
public class ma_JiraApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "http://localhost:8090";
		
		SessionFilter session = new SessionFilter();
		//cookie authentication
		 given().log().all().header("Content-Type","application/json").body("{ \"username\": \"yakshitheaswar\", \"password\": \"Prakash3@\" }")
		.filter(session).when().post("/rest/auth/1/session")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		//Create Issue in Jira
		 String response = given().log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"RES\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Create Bug\",\r\n"
				+ "       \"description\": \"Creating of an issue using IDs for projects and issue types using the REST API\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}")
				.filter(session).when().post("/rest/api/2/issue")
				.then().log().all().assertThat().statusCode(201).extract().response().asString();
		 
		 JsonPath js = new JsonPath(response);
		 String id = js.get("id");
		 //add comment 
		 String responseComment = given().log().all().pathParam("id",""+id+"").header("Content-Type","application/json").body("{\r\n"
		 		+ "    \"body\": \"Adding Comments from Rest assured API.\",\r\n"
		 		+ "    \"visibility\": {\r\n"
		 		+ "        \"type\": \"role\",\r\n"
		 		+ "        \"value\": \"Administrators\"\r\n"
		 		+ "    }\r\n"
		 		+ "}")
		 	.filter(session).when().post("/rest/api/2/issue/{id}/comment")
		 	.then().log().all().assertThat().statusCode(201).extract().response().asString();
		 JsonPath js2 = new JsonPath(responseComment);
		 String commentId = js2.getString("id");
		 
		 //add attachments
		 given().log().all().filter(session).header("X-Atlassian-Token", "no-check")
		 	.pathParam("id", ""+id+"").multiPart("file", new File("C:\\Users\\pli6cob\\workspace\\restAssured\\Jira.txt"))
		 	.when().post("/rest/api/2/issue/{id}/attachments")
		 	.then().log().all().assertThat().statusCode(200);
		 
		 //get issue details (Comments)
		 
		 String responseGET = given().log().all().filter(session).pathParam("id", ""+id+"").queryParam("fields", "comment")
		 .when().get("/rest/api/2/issue/{id}")
		 .then().log().all().assertThat().statusCode(200).extract().response().asString();
		 
		 JsonPath js1= new JsonPath(responseGET);
		 int noOfComments = js1.getInt("fields.comment.comments.size()");
		 for(int i=0;i<noOfComments;i++) {
			 if(js1.getString("fields.comment.comments["+i+"].id").equalsIgnoreCase(commentId)) {
				 String actualcomment = js1.getString("fields.comment.comments["+i+"].body");
				 System.out.println(actualcomment);
				 Assert.assertEquals(actualcomment, "Adding Comments from Rest assured API.");
			 }
			 
		 }
		 
	}

	
}

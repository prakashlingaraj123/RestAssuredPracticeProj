package apiTesting.restAssured.Basic;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class ComplexPlayBookPrasingPractice {
	@Test
	public void  payloadParsing(){
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(ComplexPayload.compPlayBook());
		
		//1. Print No of courses returned by API
		
		  int numofCourses = js.get("courses.size()");
		  System.out.println(numofCourses);
		 
		//2.Print Purchase Amount
		 int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		 System.out.println(purchaseAmount);
		//3. Print Title of the first course
		  System.out.println(js.getString("courses[0].title"));

		//4. Print All course titles and their respective Prices
		  for(int i=0;i<numofCourses;i++) {
			 System.out.println(js.getString("courses["+i+"].title"));
			 System.out.println(js.getInt("courses["+i+"].price"));
		  }
		//5. Print no of copies sold by RPA Course
		  for(int i=0;i<numofCourses;i++) {
			  if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
				  System.out.println(js.getInt("courses["+i+"].copies"));
			  }
		  }

		//6. Verify if Sum of all Course prices matches with Purchase Amount
		  int sum=0;
		  for(int i=0;i<numofCourses;i++) {
			  int price = js.getInt("courses["+i+"].price");
			  int copies = js.getInt("courses["+i+"].copies");
			  int amount = price*copies;
			  System.out.println(amount);
			  sum=sum+amount;
			  
		  }
		  System.out.println(sum);
		  
		  Assert.assertEquals(sum, purchaseAmount);
		  
	}

}

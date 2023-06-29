package fithub;

import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import org.testng.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.Scanner;

public class Basic {

	public static void main(String[] args) {

				Scanner scanner = new Scanner(System.in) ;
				System.out.println("What page you want to go :");
				int page = scanner.nextInt();
			    System.out.println("Which id you want to know the detail :");
				int detail = scanner.nextInt();
				Scanner nameScanner = new Scanner(System.in);
				System.out.println("What is your name :");
				String name = nameScanner.nextLine();
				Scanner jobScanner = new Scanner(System.in);
				System.out.println("What is your job :");
				String job = jobScanner.nextLine();
				Scanner updateName = new Scanner(System.in);
			    System.out.println("What name you need to update:");
			    String nameUpdated = updateName.nextLine();
			    Scanner updateJob = new Scanner(System.in);
			    System.out.println("What job you need to update:");
			    String jobUpdated = updateJob.nextLine();
			    
//GET
				// TODO Auto-generated method stub
			RestAssured.baseURI= "https://reqres.in";
			String response = given()
			.log().all()
			.queryParam("page", page).when().get("/api/users")
			.then().assertThat()
			.log().all()
			.statusCode(200)
			.extract().response().asString();
				
			JsonPath jsonPath = new JsonPath(response);
			int totalData = jsonPath.getInt("data.size()");
			
			for(int i = 0; i < totalData; i ++) {
				int id = jsonPath.getInt("data["+i+"].id");
				if(id == detail) {
					String email = jsonPath.getString("data["+i+"].email");
					String firstName = jsonPath.getString("data["+i+"].first_name");
					String lastName = jsonPath.getString("data["+i+"].last_name");
					String avatar = jsonPath.getString("data["+i+"].avatar");
					System.out.println("Email : " + email);
					System.out.println("First Name : " + firstName);
					System.out.println("Last Name : " + lastName);
					System.out.println("Avatar : " + avatar);
					
					
				}

			}
			
			
//POST
				
			String postResponse= given()
		    .log().all().header("Content-Type","application/json")
		    .body("{\r\n"
		        + "    \"name\": \"" + name + "\",\r\n"
		        + "    \"job\": \"" + job + "\"\r\n"
		        + "}")
		    .when().post("https://reqres.in/api/users")
		    .then().assertThat()
		    .log().all()
		    .statusCode(201)
		    .extract().response().asString();
			JsonPath postJson = new JsonPath(postResponse);
			String nameResponse = postJson.getString("name");
			String jobResponse = postJson.getString("job");
			Assert.assertEquals(nameResponse,name);
			Assert.assertEquals(jobResponse,job);
			
			
//PUT
				

			String updateResponse = given()
	        .log().all()
	        .header("Content-Type", "application/json")
		    .body("{\r\n"
		        + "    \"name\": \"" + nameUpdated + "\",\r\n"
			    + "    \"job\": \"" + jobUpdated + "\"\r\n"
			    + "}")
			 .when().put("https://reqres.in/api/users/2")
			 .then().assertThat()
			 .log().all()
			 .statusCode(200)
			 .extract().response().asString();
	   }
		
	

}

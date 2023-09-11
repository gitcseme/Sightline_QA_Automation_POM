package legalhold.legalholdpagefactory.login_logout;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

public class LoginWithToken {
    WebDriver driver;

    public void loginToLegalHoldWithToken() throws UnsupportedEncodingException {
        String baseUrl = "https://LVSVQDSQLLH01:5656";

        driver=new ChromeDriver();

        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = baseUrl;
        Response response =
                given()
                        .contentType("application/json")
                        .header("x-api-key", "vdFklU5xBN62IaCVqi0KCFi5mJ8pvLIcSd2AYhSqRXH9JCmUGOVIZUkOUxp8DIjX")
                        .body("{\n" +
                                "    \"userId\":\"7859\",\n" +
                                "    \"role\":\"sysadmin\",\n" +
                                "    \"tenantId\":\"854\",\n" +
                                "    \"email\":\"syslegalhold@gmail.com\",\n" +
                                "    \"FirstName\":\"System\",\n" +
                                "    \"LastName\":\"Admin\"\n" +
                                "}")
                        .when()
                        .post("/api/sso/request_token")
                        .then().assertThat().statusCode(200).extract().response();

        JsonPath resObj = response.jsonPath();
        System.out.println(resObj);

        String token = URLEncoder.encode(resObj.get("Token"), StandardCharsets.US_ASCII.toString());;


        driver.get("https://legalholdqa.consiliotest.com" + "?token=" + token);
    }
}

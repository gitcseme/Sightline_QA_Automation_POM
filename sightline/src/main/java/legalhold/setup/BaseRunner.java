package legalhold.setup;

import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import legalhold.legalholdpagefactory.login_logout.LogoutLegalHold;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import pageFactory.*;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static io.restassured.RestAssured.given;


public class BaseRunner {
    protected Driver driver;
    protected WebDriverWait wait;
    protected SoftAssert softAssert;
    protected LoginToSightline loginToSightline;
    protected LogoutLegalHold logoutLegalHold;


    public BaseRunner() throws ParseException, IOException, InterruptedException {
        Input in = new Input();
        in.loadEnvConfig();
        driver = new Driver();
        loginToSightline = new LoginToSightline(driver);
        logoutLegalHold = new LogoutLegalHold(driver);
        wait = new CustomWebDriverWait(driver.getWebDriver(), 30);
        softAssert = new SoftAssert();
    }

    /* This method logs in to "Infinity Domain Expansion" tenant
    as system admin from Sightline. Uncomment the BeforeClass annotation
    before merging */

//    @BeforeClass(alwaysRun = true)
    public void login() throws IOException {
        loginToSightline.loginAsSystemAdmin("syslegalhold@gmail.com", "amikhelbona#2023", "LH Automation 1");
    }


    /* This method logs in to "Infinity Domain Expansion" tenant directly
    as System Admin using token. You can use this while developing. comment the BeforeClass annotation
    before merging */

    @BeforeClass(alwaysRun = true)
    public void sysAdminLoginToLegalHoldWithToken() throws UnsupportedEncodingException {
        String baseUrl = "https://LVSVQDSQLLH01:5656";
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = baseUrl;
        Response response =
                given()
                        .contentType("application/json")
                        .header("x-api-key", "vdFklU5xBN62IaCVqi0KCFi5mJ8pvLIcSd2AYhSqRXH9JCmUGOVIZUkOUxp8DIjX")
                        .body("{\n" +
                                "    \"userId\":\"7859\",\n" +
                                "    \"role\":\"sysadmin\",\n" +
                                "    \"tenantId\":\"866\",\n" +
                                "    \"email\":\"syslegalhold@gmail.com\",\n" +
                                "    \"FirstName\":\"System\",\n" +
                                "    \"LastName\":\"Admin\"\n" +
                                "}")
                        .when()
                        .post("/api/sso/request_token")
                        .then().assertThat().statusCode(200).extract().response();

        JsonPath resObj = response.jsonPath();
        System.out.println("The api response is: " + resObj.toString());
        String token = URLEncoder.encode(resObj.get("Token"), StandardCharsets.US_ASCII.toString());
        driver.get("https://legalholdqa.consiliotest.com" + "?token=" + token);
    }

//    @AfterClass(alwaysRun = true)
    public void closeBrowser() throws InterruptedException {
        logoutLegalHold.logOutFromLegalHold();
        logoutLegalHold.logOutFromSightline();
        driver.close();
    }

}

package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReporsCommunication_26 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SessionSearch ss;
	SoftAssert soft;
	ReportsPage report;
	CommunicationExplorerPage exp;
	TagsAndFoldersPage tag;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}
	
	
	/**
	 * @Author :Aathith 
	 * date: 08/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate on page filter for Tag with other filters on Communication explorer report 
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-56879",dataProvider ="PaRmu" ,enabled = true, groups = {"regression" })
	public void validateFilterForTagAndOther(String user, String pass) throws InterruptedException  {
		
		soft = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-56879");
		base.stepInfo("Validate on page filter for Tag with other filters on Communication explorer report");
		
		loginPage.loginToSightLine(user, pass);
		base.stepInfo("Logined user : "+ user);
		
		exp = new CommunicationExplorerPage(driver);
		
		//navigate commicationexplor page
		exp.navigateToCommunicationExpPage();
		driver.waitForPageToBeReady();
		
		exp.generateReport_DefaultSG();
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication report should generate successfully");
		
		//tag
		exp.filterDocumentBy("Tags", false, 2);
		exp.removeFilters();
		
		exp.filterDocumentBy("Tags", false, 2);
		exp.filterDocumentBy("CustodianName", false, 2);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication map should be generated for the documents excluding for the selected tags and included/excluded Custodian name.");
		
		exp.removeFilters();
		exp.filterDocumentBy("Tags", true, 2);
		exp.filterDocumentBy("EmailAllDomains", false, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication map should be generated for the documents part of the selected tags and included/excluded Domains.");
		
		exp.removeFilters();
		exp.filterDocumentBy("Tags", true, 2);
		exp.filterDocumentBy("EmailToAddresses", false, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication map should be generated for the documents part of the selected tags and included/excluded EmailToAddresses.");
		
		exp.removeFilters();
		exp.filterDocumentBy("Tags", false, 2);
		exp.filterDocumentBy("EmailCCAddresses", false, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication map should be generated for the documents excluding for the selected tags and included/excluded EmailCCAddresses");
		
		exp.removeFilters();
		exp.filterDocumentBy("Tags", true, 2);
		exp.filterDocumentBy("EmailBCCAddresses", false, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication map should be generated for the documents part of the selected tags and included/excluded selected EmailBCCAddresses.");
		
		exp.removeFilters();
		exp.filterDocumentBy("Tags", false, 2);
		exp.filterDocumentBy("EmailRecipientNames", false, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication map should be generated for the documents excluding for the selected tags and included/excluded EmailRecipientNames.");
		
		exp.removeFilters();
		exp.filterDocumentBy("Tags", true, 2);
		exp.filterDocumentByDate("MasterDate", 1);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication map should be generated for the documents excluding for the selected tags and for the selected EmailSentDates.");
		
		exp.removeFilters();
		exp.filterDocumentBy("Tags", false, 2);
		exp.filterDocumentByDate("EmailSentDate", 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Communication map should be generated for the documents excluding for the selected tags and for the selected EmailSentDates.");
		
		
		soft.assertAll();
		base.passedStep("Validated on page filter for Tag with other filters on Communication explorer report");
		loginPage.logout();
	}
	
	
	
	
	@DataProvider(name = "PaRmu")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password }};
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Reports Communication EXECUTED******");

	}
}

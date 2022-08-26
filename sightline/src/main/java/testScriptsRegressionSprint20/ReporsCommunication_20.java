package testScriptsRegressionSprint20;

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

public class ReporsCommunication_20 {

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
	 * @Description :Verify in 'SHOW' dropdown in  Communication Explorer 
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-63572",enabled = true, groups = {"regression" })
	public void verifyNOnDomainProjectInNotDomain() throws InterruptedException  {
		
		soft = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-63572");
		base.stepInfo("Verify in 'SHOW' dropdown in  Communication Explorer ");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		String expectedText = "Select which documents you want to appear in your "
				+ "Communications Explorer report by picking whether to show the Top 8,"
				+ " Top 20 or Top 50 bubble nodes, sent communications or received communications "
				+ "or both (exchanged) communications, and whether bubble nodes should "
				+ "represent domains (ex. enron.com ) or email addresses of individual "
				+ "email accounts (ex. john.smith@gmail.com)";
		
		exp = new CommunicationExplorerPage(driver);
		
		//navigate commicationexplor page
		exp.navigateToCommunicationExpPage();
		driver.waitForPageToBeReady();
		
		//get show help text and verify
		exp.getShowHelpIcon().waitAndClick(5);
		String actualtext = exp.getShowHelpIcon().GetAttribute("data-content");
		soft.assertEquals(actualtext, expectedText);
		base.passedStep("help text is verified");
		
		//enable securitygroup perform filter and verify
		exp.generateReport_DefaultSG();
		exp.selectShowOptions("8", "Domain", "Exchange Volume");
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(exp.getfindAllNodes().isElementAvailable(3));
		soft.assertTrue(exp.getfindAllNodes().size()<10);
		base.passedStep("Communication explorer report is generated successfully and Top 8 nodes should be displays");
		
		//for node 20
		exp.selectShowOptions("20", "Domain", "Exchange Volume");
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(exp.getfindAllNodes().isElementAvailable(3));
		soft.assertTrue(exp.getfindAllNodes().size()<22);
		base.passedStep("Communication explorer report is generated successfully and Top 20 nodes should be displays");
		
		//for node 50
		exp.selectShowOptions("50", "Domain", "Exchange Volume");
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(exp.getfindAllNodes().isElementAvailable(3));
		soft.assertTrue(exp.getfindAllNodes().size()<52);
		base.passedStep("Communication explorer report is generated successfully and Top 50 nodes should be displays");
		
		soft.assertAll();
		base.passedStep("Verified in 'SHOW' dropdown in  Communication Explorer ");
		loginPage.logout();
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
	
	/**
	 * @Author :Aathith 
	 * date: 08/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate onpage filter for CustodianName and MasterDate on Communication Explorer Report 
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-56897",dataProvider ="PaRmu" ,enabled = true, groups = {"regression" })
	public void validateFilterForMasterDateAndCustodian(String user, String pass) throws InterruptedException  {
		
		soft = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-56897");
		base.stepInfo("Validate onpage filter for CustodianName and MasterDate on Communication Explorer Report");
		
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
		
		exp.filterDocumentByDate("MasterDate", "On");
		exp.filterDocumentBy("CustodianName", true, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Documents should be filtered based on the applied filters.");
		
		exp.removeFilters();
		exp.filterDocumentByDate("MasterDate", "Before");
		exp.filterDocumentBy("CustodianName", false, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Documents should be filtered based on the applied filters.");
		
		soft.assertAll();
		base.passedStep("Validate onpage filter for CustodianName and MasterDate on Communication Explorer Report");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate onpage filter for EmailToAddresses and EmailCCAddresses on Communication Explorer Report 
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-56899",dataProvider ="PaRmu" ,enabled = true, groups = {"regression" })
	public void validateFilterForEmailToAddressesAndEmailCCAddresses(String user, String pass) throws InterruptedException  {
		
		soft = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-56899");
		base.stepInfo("Validate onpage filter for EmailToAddresses and EmailCCAddresses on Communication Explorer Report");
		
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
		
		exp.filterDocumentBy("EmailCCAddresses", true, 2);
		exp.filterDocumentBy("EmailToAddresses", true, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Documents should be filtered based on the applied filters.");
		
		exp.removeFilters();
		exp.filterDocumentBy("EmailCCAddresses", false, 2);
		exp.filterDocumentBy("EmailToAddresses", true, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Documents should be filtered based on the applied filters.");
		
		soft.assertAll();
		base.passedStep("Validate onpage filter for EmailToAddresses and EmailCCAddresses on Communication Explorer Report");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate onpage filter for EmailBCCAddresses and EmailToAddresses on Communication Explorer Report 
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-56900",dataProvider ="PaRmu" ,enabled = true, groups = {"regression" })
	public void validateFilterForEmailToAddressesAndEmailBCCAddresses(String user, String pass) throws InterruptedException  {
		
		soft = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-56900");
		base.stepInfo("Validate onpage filter for EmailBCCAddresses and EmailToAddresses on Communication Explorer Report");
		
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
		
		exp.filterDocumentBy("EmailBCCAddresses", true, 2);
		exp.filterDocumentBy("EmailToAddresses", true, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Documents should be filtered based on the applied filters.");
		
		exp.removeFilters();
		exp.filterDocumentBy("EmailBCCAddresses", false, 2);
		exp.filterDocumentBy("EmailToAddresses", true, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Documents should be filtered based on the applied filters.");
		
		soft.assertAll();
		base.passedStep("Validated onpage filter for EmailBCCAddresses and EmailToAddresses on Communication Explorer Report");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate onpage filter for EmailCCAddresses  and EmailRecipientNames on Communication Explorer Report 
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-56901",dataProvider ="PaRmu" ,enabled = true, groups = {"regression" })
	public void validateFilterForEmailToAddressesAndEmailRecipientNames(String user, String pass) throws InterruptedException  {
		
		soft = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-56901");
		base.stepInfo("Validate onpage filter for EmailCCAddresses  and EmailRecipientNames on Communication Explorer Report");
		
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
		
		exp.filterDocumentBy("EmailCCAddresses", false, 2);
		exp.filterDocumentBy("EmailRecipientNames", true, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Documents should be filtered based on the applied filters.");
		
		exp.removeFilters();
		exp.filterDocumentBy("EmailCCAddresses", false, 2);
		exp.filterDocumentBy("EmailRecipientNames", false, 2);
		exp.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("Documents Across").isElementAvailable(3));
		base.passedStep("Documents should be filtered based on the applied filters.");
		
		soft.assertAll();
		base.passedStep("Validated onpage filter for EmailCCAddresses  and EmailRecipientNames on Communication Explorer Report");
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

package testScriptsRegressionSprint21;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import org.openqa.selenium.WebElement;
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
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CommunicationEXp_21 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;
	CommunicationExplorerPage communicationExpPage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();

		sessionSearch = new SessionSearch(driver);
		reports = new ReportsPage(driver);
		conceptExplorer = new ConceptExplorerPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);
		communicationExpPage = new CommunicationExplorerPage(driver);
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @author Jayanthi
	 * @Description :Validate onpage filter for EmailCCAddresses and
	 *              EmailRecipientNames on Communication Explorer Report
	 */

	@Test(description = "RPMXCON-56901", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_CCAdress_ERNAme(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id:RPMXCON-56901 ");
		baseClass.stepInfo(
				"Validate onpage filter for EmailCCAddresses  and EmailRecipientNames on Communication Explorer Report");

		String emailreciepient = Input.emailReciepientName2;

		String emailCCAddress1 = Input.emailCCAdressOption1;
		String emailCCAddress2 = Input.emailCCAdressOption2;
		String sourceToSelect = "Security Groups";

		// Login
		baseClass.stepInfo("**Step-1 Login as " + role + " **");
		loginPage.loginToSightLine(userName, password);
		String[] columnsToSelect = { Input.emailRecipientName, Input.emailCCAdress };

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include/Exclude filter criteria and click “Apply filter”");

		conceptExplorer.filterAction(emailCCAddress1, Input.emailCCAdress, emailCCAddress2, false);
		conceptExplorer.filterAction(emailreciepient, Input.emailRecipientName, null, true);

		communicationExpPage.clickApplyBtn();

		// Select nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 3);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();

		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailCCAdress = dlPage.getColumnValue(Input.emailCCAdress, false);
		List<String> emailRecipientName_excl = dlPage.getColumnValue(Input.emailRecipientName, false);
		conceptExplorer.verifyExcludeIcludeFiltersLikeOR_Operator(emailCCAdress, emailRecipientName_excl,
				emailCCAddress1, emailCCAddress2, emailreciepient);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply Exclude filter
		baseClass.stepInfo("** Set the Exclude/Iclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(emailCCAddress1, Input.emailCCAdress, emailCCAddress2, false);
		conceptExplorer.filterAction(emailreciepient, Input.emailRecipientName, null, false);
		communicationExpPage.clickApplyBtn();
		baseClass.stepInfo("Report Generated.");

		// Select node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 2);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		baseClass.waitTime(3);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		driver.waitForPageToBeReady();

		// validation of exclude/Include filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailCCAdress_excl = dlPage.getColumnValue(Input.emailCCAdress, false);
		List<String> emailRecipientName_exclude = dlPage.getColumnValue(Input.emailRecipientName, false);
		conceptExplorer.verifyExcludeFiltersLikeOR_Operator(emailCCAdress_excl, emailRecipientName_exclude,
				emailCCAddress1, emailCCAddress2, emailreciepient);

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @Description :Validate onpage filter for EmailAuthorName and
	 *              EmailRecipientNames on Communication Explorer Report
	 */

	@Test(description = "RPMXCON-56896", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_EAName_ERNAme(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id:RPMXCON-56896");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorName and EmailRecipientNames on Communication Explorer Report");

		String emailreciepient1 = Input.emailRecipientName1;
		String emailreciepient2 = Input.emailReciepientName2;
		String emailAuthourInclude = Input.emailAllDomainOption;
		String emailAuthorName = "Phillip K Allen";

		String sourceToSelect = "Security Groups";

		// Login
		baseClass.stepInfo("**Step-1 Login as " + role + " **");
		loginPage.loginToSightLine(userName, password);
		String[] columnsToSelect = { Input.emailRecipientName, Input.MetaDataEAName };

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include/Exclude filter criteria and click “Apply filter”");

		conceptExplorer.filterAction(emailreciepient1, Input.emailRecipientName, emailreciepient2, true);
		conceptExplorer.filterAction(emailAuthourInclude, Input.MetaDataEAName, null, true);

		communicationExpPage.clickApplyBtn();

		// Select nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 3);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();

		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailEAName_Incl = dlPage.getColumnValue(Input.MetaDataEAName, false);
		List<String> emailRecipientName_incl = dlPage.getColumnValue(Input.emailRecipientName, false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailRecipientName_incl, emailEAName_Incl, emailreciepient1,
				emailreciepient2, emailAuthourInclude);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply Exclude filter
		baseClass.stepInfo("** Set the Exclude/Iclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(emailreciepient1, Input.emailRecipientName, emailreciepient2, false);
		conceptExplorer.filterAction(emailAuthorName, Input.MetaDataEAName, null, true);
		communicationExpPage.clickApplyBtn();
		baseClass.stepInfo("Report Generated.");

		// Select node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 2);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		baseClass.waitTime(3);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		driver.waitForPageToBeReady();

		// validation of exclude/Include filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailEAName_excl = dlPage.getColumnValue(Input.MetaDataEAName, false);
		List<String> emailRecipientName_excl = dlPage.getColumnValue(Input.emailRecipientName, false);
		conceptExplorer.verifyExcludeIcludeFiltersLikeOR_Operator(emailRecipientName_excl, emailEAName_excl,
				emailreciepient1, emailreciepient2, emailAuthorName);

		// Logout
		loginPage.logout();
	}

	@Test(description = "RPMXCON-56899", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_ToAdress_CcAdress(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id:RPMXCON-56899 ");
		baseClass.stepInfo("Validate onpage filter for EmailToAddresses and"
				+ " EmailCCAddresses on Communication Explorer Report");

		String emailCCAddress1 = "Gouri.Dhavalikar@symphonyteleca.com";
		String emailCCAddress2 = "Satish.pawal@consilio.com";

		String emailToAdress = "Swapnal.Sonawane@symphonyteleca.com";

		String sourceToSelect = "Security Groups";

		// Login
		baseClass.stepInfo("**Step-1 Login as " + role + " **");
		loginPage.loginToSightLine(userName, password);
		String[] columnsToSelect = { Input.emailToAdress, Input.emailCCAdress };

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include/Exclude filter criteria and click “Apply filter”");

		conceptExplorer.filterAction(emailCCAddress1, Input.emailCCAdress, emailCCAddress2, true);
		conceptExplorer.filterAction(emailToAdress, Input.emailToAdress, null, true);

		communicationExpPage.clickApplyBtn();

		// Select nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 3);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();

		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailcc_Incl = dlPage.getColumnValue(Input.emailCCAdress, false);
		List<String> emailTo_incl = dlPage.getColumnValue(Input.emailToAdress, false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailcc_Incl, emailTo_incl, emailCCAddress1, emailCCAddress2,
				emailToAdress);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply Exclude filter
		baseClass.stepInfo("** Set the Exclude/Iclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(emailCCAddress1, Input.emailCCAdress, emailCCAddress2, false);
		conceptExplorer.filterAction(emailToAdress, Input.emailToAdress, null, true);
		communicationExpPage.clickApplyBtn();
		baseClass.stepInfo("Report Generated.");

		// Select node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 2);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		baseClass.waitTime(3);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		driver.waitForPageToBeReady();

		// validation of exclude/Include filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailto_incl = dlPage.getColumnValue(Input.emailToAdress, false);
		List<String> emailcc_excl = dlPage.getColumnValue(Input.emailCCAdress, false);
		conceptExplorer.verifyExcludeIcludeFiltersLikeOR_Operator(emailcc_excl, emailto_incl, emailCCAddress1,
				emailCCAddress2, emailToAdress);

		// Logout
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/26/2022 Modified date:NA Modified by:
	 * @Description :Verify in 'SHOW' dropdown in Communication Explorer
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-63572", dataProvider = "paRmuUsers", enabled = true, groups = { "regression" })
	public void verifySHOW_DropDown(String userName, String password, String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63572");
		baseClass.stepInfo("Verify in 'SHOW' dropdown in  Communication Explorer ");

		baseClass.stepInfo("**Step-1 Login as " + role + " **");
		loginPage.loginToSightLine(userName, password);

		String expectedText = "Select which documents you want to appear in your "
				+ "Communications Explorer report by picking whether to show the Top 8,"
				+ " Top 20 or Top 50 bubble nodes, sent communications or received communications "
				+ "or both (exchanged) communications, and whether bubble nodes should "
				+ "represent domains (ex. enron.com ) or email addresses of individual "
				+ "email accounts (ex. john.smith@gmail.com)";

		baseClass.selectproject(Input.additionalDataProject);

		// navigate communicationexplor page
		communicationExpPage.navigateToCommunicationExpPage();
		driver.waitForPageToBeReady();

		// get show help text and verify
		communicationExpPage.getShowHelpIcon().waitAndClick(5);
		String actualtext = communicationExpPage.getShowHelpIcon().GetAttribute("data-content");
		baseClass.textCompareEquals(actualtext, expectedText, "SHOW drop down help icon text verified asa expcted",
				"SHOW drop down help icon text verified not as expcted");

		// enable securitygroup perform filter and verify
		communicationExpPage.generateReport_DefaultSG();
		communicationExpPage.selectShowOptions("8", "Domain", "Exchange Volume");
		communicationExpPage.clickApplyBtn();
		communicationExpPage.verifyNodesCount(8);
		// for node 20
		communicationExpPage.selectShowOptions("20", "Domain", "Exchange Volume");
		communicationExpPage.clickApplyBtn();
		communicationExpPage.verifyNodesCount(20);
		// for node 50
		communicationExpPage.selectShowOptions("50", "Domain", "Exchange Volume");
		communicationExpPage.clickApplyBtn();
		communicationExpPage.verifyNodesCount(50);

		baseClass.passedStep("Verified  'SHOW' dropdown in  Communication Explorer ");
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Communication explorer sprint 21**");
	}
}

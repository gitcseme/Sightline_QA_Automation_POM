package sightline.reports;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CommunicationExplorer_Phase2_Regression {
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
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
//		 in = new Input();
//		 in.loadEnvConfig();
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

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@DataProvider(name = "paRmuRevUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rev1userName, Input.rev1password, "REV" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @Author :Aathith date: 08/26/2022 Modified date:NA Modified by:
	 * @Description :Validate on page filter for Tag with other filters on
	 *              Communication explorer report
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56879", dataProvider = "PaRmu", enabled = true, groups = { "regression" })
	public void validateFilterForTagAndOther(String user, String pass) throws InterruptedException {

		SoftAssert soft = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-56879");
		baseClass.stepInfo("Validate on page filter for Tag with other filters on Communication explorer report");

		loginPage.loginToSightLine(user, pass);
		baseClass.stepInfo("Logined user : " + user);

		communicationExpPage = new CommunicationExplorerPage(driver);

		// navigate commicationexplor page
		communicationExpPage.navigateToCommunicationExpPage();
		driver.waitForPageToBeReady();

		communicationExpPage.generateReport_DefaultSG();
		communicationExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep("Communication report should generate successfully");

		// tag
		communicationExpPage.filterDocumentBy("Tags", false, 2);
		communicationExpPage.removeFilters();

		communicationExpPage.filterDocumentBy("Tags", false, 2);
		communicationExpPage.filterDocumentBy("CustodianName", false, 2);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep(
				"Communication map should be generated for the documents excluding for the selected tags and included/excluded Custodian name.");

		communicationExpPage.removeFilters();
		communicationExpPage.filterDocumentBy("Tags", true, 2);
		communicationExpPage.filterDocumentBy("EmailAllDomains", false, 2);
		communicationExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep(
				"Communication map should be generated for the documents part of the selected tags and included/excluded Domains.");

		communicationExpPage.removeFilters();
		communicationExpPage.filterDocumentBy("Tags", true, 2);
		communicationExpPage.filterDocumentBy("EmailToAddresses", false, 2);
		communicationExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep(
				"Communication map should be generated for the documents part of the selected tags and included/excluded EmailToAddresses.");

		communicationExpPage.removeFilters();
		communicationExpPage.filterDocumentBy("Tags", false, 2);
		communicationExpPage.filterDocumentBy("EmailCCAddresses", false, 2);
		communicationExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep(
				"Communication map should be generated for the documents excluding for the selected tags and included/excluded EmailCCAddresses");

		communicationExpPage.removeFilters();
		communicationExpPage.filterDocumentBy("Tags", true, 2);
		communicationExpPage.filterDocumentBy("EmailBCCAddresses", false, 2);
		communicationExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep(
				"Communication map should be generated for the documents part of the selected tags and included/excluded selected EmailBCCAddresses.");

		communicationExpPage.removeFilters();
		communicationExpPage.filterDocumentBy("Tags", false, 2);
		communicationExpPage.filterDocumentBy("EmailRecipientNames", false, 2);
		communicationExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep(
				"Communication map should be generated for the documents excluding for the selected tags and included/excluded EmailRecipientNames.");

		communicationExpPage.removeFilters();
		communicationExpPage.filterDocumentBy("Tags", true, 2);
		communicationExpPage.filterDocumentByDate("MasterDate", 1);
		communicationExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep(
				"Communication map should be generated for the documents excluding for the selected tags and for the selected EmailSentDates.");

		communicationExpPage.removeFilters();
		communicationExpPage.filterDocumentBy("Tags", false, 2);
		communicationExpPage.filterDocumentByDate("EmailSentDate", 2);
		communicationExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(baseClass.text("Documents Across").isElementAvailable(3));
		baseClass.passedStep(
				"Communication map should be generated for the documents excluding for the selected tags and for the selected EmailSentDates.");

		soft.assertAll();
		baseClass.passedStep("Validated on page filter for Tag with other filters on Communication explorer report");
		loginPage.logout();
	}

	@DataProvider(name = "PaRmu")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
	}

	/**
	 * @author Jeevitha
	 * @Description : Validate onpage filter for EmailAuthorDomains and
	 *              EmailAllDomain on Communication Explorer Report [RPMXCON-56903]
	 */
	@Test(description = "RPMXCON-56903", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_EmailAuthor(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String sourceToSelect = "Security Groups";
		String[] columnsToSelect = { Input.MetaDataEAName, Input.emailAllDomain };

		baseClass.stepInfo("Test case Id: RPMXCON-56903 Report/Communication Map");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorDomains and EmailAllDomain on Communication Explorer Report");

		// Login
		loginPage.loginToSightLine(userName, password);

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter As EmailAuthorName: Exclude EmailAllDomain: Include
		baseClass.stepInfo("** Set the Include filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.emailAllDomainOption, Input.MetaDataEAName, Input.emailReciepientName2,
				false);
		conceptExplorer.filterAction(Input.domainNameConsilio, Input.emailAllDomain, null, true);
		communicationExpPage.clickApplyBtn();

		// Select All nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), null);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), null);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();

		// validation for exclude & include filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		conceptExplorer.filterValidation("ExcludeAndInclude", Input.MetaDataEAName, Input.emailAllDomain,
				Input.emailAllDomainOption, Input.emailReciepientName2, Input.domainNameConsilio, false, "");

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		driver.waitForPageToBeReady();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter as EmailAuthorName: Include EmailAllDomain: Exclude
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.emailAllDomainOption, Input.MetaDataEAName, Input.EmailAuthourName, true);
		conceptExplorer.filterAction(Input.MetaDataDomainName, Input.emailAllDomain, null, false);

		communicationExpPage.clickApplyBtn();
		baseClass.stepInfo("Report Generated.");

		// Select All node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), null);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), null);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		baseClass.waitTime(3);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		driver.waitForPageToBeReady();

		// validation of exclude & include filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		conceptExplorer.filterValidation("IncludeAndExclude", Input.MetaDataEAName, Input.emailAllDomain,
				Input.emailAllDomainOption, Input.EmailAuthourName, Input.MetaDataDomainName, false, "");

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Validate on page filter for EmailAllDomains and MasterDate on
	 *              Communication Explorer Report [RPMXCON-56905]
	 */
	@Test(description = "RPMXCON-56905", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_Masterdate(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String sourceToSelect = "Security Groups";
		String[] columnsToSelect = { Input.masterDateText, Input.emailAllDomain };
		String masterDate = "1980/01/01";

		baseClass.stepInfo("Test case Id: RPMXCON-56905 Report/Communication Map");
		baseClass
				.stepInfo("Validate onpage filter for EmailAllDomains and MasterDate on Communication Explorer Report");

		// Login
		loginPage.loginToSightLine(userName, password);

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter As MasterDate : ON EmailAllDomain: Include
		baseClass.stepInfo("** Set the Include filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.domainNameConsilio, Input.emailAllDomain, Input.domainNameSymphony, true);

		// Masterdate selection
		communicationExpPage.masterDateSelection(masterDate, "", "On");
		communicationExpPage.clickApplyBtn();

		// Select All nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), null);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), null);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();

		// validation for include & include filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		conceptExplorer.filterValidation("IncludeAndInclude", Input.emailAllDomain, Input.masterDateText,
				Input.domainNameConsilio, Input.domainNameSymphony, masterDate, false, "");

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		driver.waitForPageToBeReady();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter as MasterDate : ON EmailAllDomain: Exclude
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.MetaDataDomainName, Input.emailAllDomain, Input.filterDataInput3, false);

		// Masterdate selection
		communicationExpPage.masterDateSelection(masterDate, "", "On");

		communicationExpPage.clickApplyBtn();
		baseClass.stepInfo("Report Generated.");

		// Select All node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), null);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), null);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		baseClass.waitTime(3);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		driver.waitForPageToBeReady();

		// validation of exclude & include filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		conceptExplorer.filterValidation("ExcludeAndInclude", Input.emailAllDomain, Input.masterDateText,
				Input.MetaDataDomainName, Input.filterDataInput3, masterDate, false, "");

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Date: 08/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate Masterdate range on Communication Explorer report.
	 *              RPMXCON-56886
	 */
	@Test(description = "RPMXCON-56886", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyMasterDateFiltersFunctionality_CommunicationExplorer(String userName, String password,
			String role) throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56886");
		baseClass.stepInfo("Validate Masterdate range on Communication Explorer report");

		String searchText = Input.searchString10;
		String sourceToSelect = "Searches";
		String sgToSelect = Input.mySavedSearch;
		String savedSearchName = "SavedSearch-" + UtilityLog.dynamicNameAppender();
		String[] selectSourceList = { "Security Groups", "Searches", "Project", "Folders" };
		String[] conditionsToCheck = { "Before", "After", "On", "Between" };
		String expectedDateInput = Input.expectedDateInput;
		String expectedToDateInput = Input.expectedToDateInput;

		// Login as PA
		baseClass.stepInfo("**Step-1 Login as Project Admin**");
		loginPage.loginToSightLine(userName, password);

		// Pre-requesties
		baseClass.stepInfo("Pre-requesties search creation");
		sessionSearch.basicContentSearch(searchText);
		sessionSearch.saveSearchAtAnyRootGroup(savedSearchName, sgToSelect);

		// masterDateVerifications via Concept explorer - DocList
		communicationExpPage.masterDateVerifications(selectSourceList, sourceToSelect, sgToSelect, savedSearchName,
				expectedDateInput, expectedToDateInput, conditionsToCheck);

		// Delete search
		savedSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @Description : Validate onpage filter for EmailRecipientNames and
	 *              EmailAllDomains on Communication Explorer Report
	 */
	@Test(description = "RPMXCON-56898", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_CommunicationExplorer(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56898");
		baseClass.stepInfo(
				"Validate onpage filter for EmailRecipientNames  and EmailAllDomains on Communication Explorer Report");

		String sourceToSelect = "Security Groups";
		String recipientName1 = Input.emailRecipientName1;
		String recipientName2 = Input.emailReciepientName2;
		String domainName = Input.domainNameConsilio;
		// Login
		baseClass.stepInfo("**Step-1 Login as " + role + " **");
		loginPage.loginToSightLine(userName, password);
		String[] columnsToSelect = { "EmailAllDomains", "EmailRecipientNames" };

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include filter criteria and click “Apply filter”");

		conceptExplorer.filterAction(recipientName1, "EmailRecipientNames", recipientName2, true);
		conceptExplorer.filterAction(domainName, "EmailAllDomains", null, true);

		communicationExpPage.clickApplyBtn();
		// Select nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 2);
		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain = dlPage.getColumnValue("EmailAllDomains", false);
		List<String> emailRecipients = dlPage.getColumnValue("EmailRecipientNames", false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailRecipients, emailAllDomain, recipientName1,
				recipientName2, domainName);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply Exclude filter
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(recipientName1, "EmailRecipientNames", recipientName2, false);
		conceptExplorer.filterAction(domainName, "EmailAllDomains", null, false);
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

		// validation of exclude filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain_Excl = dlPage.getColumnValue("EmailAllDomains", false);
		List<String> emailRecipients_Excl = dlPage.getColumnValue("EmailRecipientNames", false);
		conceptExplorer.verifyExcludeFiltersLikeOR_Operator(emailRecipients_Excl, emailAllDomain_Excl, recipientName1,
				recipientName2, domainName);

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @Description :Validate onpage filter for EmailAllDomains and CustodianName
	 *              Communication Explorer Report
	 */
	@Test(description = "RPMXCON-56904", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_CusNAme(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56904");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAllDomains  and CustodianName on Communication Explorer Report");
		String domainName1 = Input.domainNameConsilio;
		String domainName2 = Input.domainNameSymphony;

		String cusNAme = Input.custodianName;
		String sourceToSelect = "Security Groups";

		// Login
		baseClass.stepInfo("**Step-1 Login as " + role + " **");
		loginPage.loginToSightLine(userName, password);
		String[] columnsToSelect = { Input.emailAllDomain, Input.metaDataName };

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include filter criteria and click “Apply filter”");

		conceptExplorer.filterAction(domainName1, Input.emailAllDomain, domainName2, true);
		conceptExplorer.filterAction(Input.custodianName, Input.metaDataName, null, true);

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
		List<String> custodianName = dlPage.getColumnValue(Input.metaDataName, false);
		List<String> emailAllDomain = dlPage.getColumnValue(Input.emailAllDomain, false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailAllDomain, custodianName, domainName1, domainName1,
				cusNAme);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply Exclude filter
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(domainName1, Input.emailAllDomain, domainName2, false);
		conceptExplorer.filterAction(Input.custodianName, Input.metaDataName, null, false);
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

		// validation of exclude filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain_Excl = dlPage.getColumnValue("EmailAllDomains", false);
		List<String> cusNAme_Excl = dlPage.getColumnValue("CustodianName", false);
		conceptExplorer.verifyExcludeFiltersLikeOR_Operator(emailAllDomain_Excl, cusNAme_Excl, domainName1, domainName2,
				cusNAme);

		// Logout
		loginPage.logout();
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

	/**
	 * @Author : date: 08/26/2022 Modified date:9/19/22 Modified by:Raghuram A
	 * @Description :Validate onpage filter for CustodianName and MasterDate on
	 *              Communication Explorer Report
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56897", dataProvider = "paRmuUsers", enabled = true, groups = { "regression" })
	public void validateFilterForMasterDateAndCustodian(String userName, String password, String role)
			throws InterruptedException {

		String sourceToSelect = "Security Groups";
		String masterDate = "1980/01/01";

		baseClass.stepInfo("Test case Id: RPMXCON-56897");
		baseClass.stepInfo("Validate onpage filter for CustodianName and MasterDate on Communication Explorer Report");

		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logined user : " + role);

		// Navigation to Communications Explorer - IncludeAndExclude
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include/Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.metaDataCustodianNameInput, Input.metaDataName, Input.metaDataCN, true);

		// Masterdate selection
		communicationExpPage.masterDateSelection(masterDate, "", "On");

		// Select node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 2);

		// Perform View in DocView List Action
		communicationExpPage.viewInDocListAction();

		// Data validation
		conceptExplorer.filterValidation("IncludeAndInclude", Input.metaDataName, Input.masterDateText,
				Input.metaDataCustodianNameInput, Input.metaDataCN, masterDate, false, "");

		// Navigation to Communications Explorer - ExcludeAndInclude
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include/Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.metaDataCustodianNameInput, Input.metaDataName, Input.metaDataCN, false);

		// Masterdate selection
		communicationExpPage.masterDateSelection(masterDate, "", "On");

		// Select node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 2);

		// Perform View in DocView List Action
		communicationExpPage.viewInDocListAction();

		// Data validation
		conceptExplorer.filterValidation("ExcludeAndInclude", Input.metaDataName, Input.masterDateText,
				Input.metaDataCustodianNameInput, Input.metaDataCN, masterDate, false, "");

		// Logout
		loginPage.logout();

	}

	@Test(description = "RPMXCON-64257", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyViewInDocviewInNewtabFromCommunicationExplorerReportPage(String username, String password,
			String role) throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-64257");
		baseClass.stepInfo(
				"Verify that on selecting action as 'View in DocView in new tab' from communication explorer should redirect to"
						+ " DocView in new tab with correct document count");
		loginPage.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		CommunicationExplorerPage comExpPage = new CommunicationExplorerPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		comExpPage.generateReportusingDefaultSG();
		comExpPage.clickReport();
		for (int i = 1; i <= 4; i++) {
			String docCount = comExpPage.selectedDocsCount();
			comExpPage.viewinDocViewInNewTab();
			driver.waitForPageToBeReady();
			baseClass.switchTab(i);
			driver.waitForPageToBeReady();
			baseClass.verifyPageNavigation("DocumentViewer/DocView");
			docview.verifyingDocCount(docCount);
			baseClass.switchTab(0);
			if ((baseClass.getWarningsMsgHeader().isElementAvailable(1))
					|| (baseClass.getWarningsMsgHeader().isElementAvailable(1)) == false) {
				baseClass.passedStep("No warning message or error message occurs in communication explorer tab");
			} else {
				baseClass.failedStep(baseClass.getWarningMsg().getText() + " is occurred");
			}
			if (i == 4) {
				baseClass.passedStep(
						"Successfully verified four times selecting action as 'View in DocView in new tab' from communication explorer "
								+ "should redirect to DocView in new tab with correct document count as " + role);
			}
		}
		loginPage.logout();

	}

	@Test(description = "RPMXCON-48695", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyBulkActionsFromCommunicationExplorerReportPage(String username, String password, String role)
			throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48695");
		baseClass.stepInfo(
				"To Verify as an Admin or RMU in the Communication Explorer can select a node and Perform bulk tag, "
						+ "bulk folder, bulk assign documents and view in doc list");
		String assignmentName = "comExp" + Utility.dynamicNameAppender();
		String tagName = "comExp" + Utility.dynamicNameAppender();
		String folderName = "comExp" + Utility.dynamicNameAppender();
		String reportName = "comExp" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(username, password);

		CommunicationExplorerPage comExpPage = new CommunicationExplorerPage(driver);
		SessionSearch search = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);

		comExpPage.navigateToCommunicationExpPage();
		comExpPage.include(Input.metaDataCustodianNameInput, Input.metaDataName, null, false);
		comExpPage.filterCriteriaVerify(Input.metaDataName, Input.metaDataCustodianNameInput, true);
		comExpPage.generateReport_DefaultSG();
		comExpPage.selectShowOptions("8", "Email Address", "Exchange Volume");

		comExpPage.clickApplyBtn();
		comExpPage.saveReport(reportName);

		comExpPage.clickReport();
		String docCount = comExpPage.selectedDocsCount();

		comExpPage.clickActionBtn(true, false, false);
		String taggedDocs = search.BulkActions_Tag(tagName);

		comExpPage.clickActionBtn(false, true, false);
		String folderedDocs = search.BulkActions_Folder(folderName);

		DocListPage dlPage = new DocListPage(driver);
		comExpPage.viewinDoclist();
		List<String> docIDs = new ArrayList<String>();
		docIDs = dlPage.gettingAllDocIDs();
		if (username.equals(Input.rmu1userName)) {
			comExpPage.getBackToSourceButton().Click();
			driver.waitForPageToBeReady();
			baseClass.verifyPageNavigation("CommunicationExplorerReport");

			comExpPage.clickReport();
			comExpPage.clickActionBtn(false, false, true);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(10);
			baseClass.selectproject();
			baseClass.stepInfo("Created a assignment by assigning saved search documents from search term report page -"
					+ assignmentName);
			String ActualCount = agnmt.verifydocsCountInAssgnPage(assignmentName);
			softAssertion.assertEquals(docCount, ActualCount);
		}
		int docCountInDocList = docIDs.size();

		// validating bulk actioned docs[tag/folders/assign/doc list] are same as
		// documents
		// selected in communications explorer page.
		softAssertion.assertEquals(docCount, taggedDocs);
		softAssertion.assertEquals(docCount, folderedDocs);
		softAssertion.assertEquals(docCount, String.valueOf(docCountInDocList));

		softAssertion.assertAll();
		baseClass.passedStep(
				"Document Count associated to selected node doc count in communication explorer report is same  as"
						+ " excpeted in nulk tagged tag/bulk foldered folder/"
						+ "manage assignments page after assigning the docs to assignment and in mini doc list page.");
		ReportsPage reportPage = new ReportsPage(driver);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		reportPage.verifyCustomReportDisplaydetails(reportName);
		reportPage.deleteCustomReport(reportName);

		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllTags(tagName);
		tagPage.deleteAllFolders(folderName);
		loginPage.logout();

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
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

package sightline.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Tally_Phase2_Regression {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionsearch;
	BaseClass bc;
	SoftAssert softAssertion;
	TallyPage tp;
	String hitsCountPA;
	int hitsCount;

	String SearchName = "Tally" + Utility.dynamicNameAppender();
	String assgnName = "Tally" + Utility.dynamicNameAppender();
	String folderName = "Tally" + Utility.dynamicNameAppender();
	String securityGrpName = "Tally" + Utility.dynamicNameAppender();
	String expectedCusName;
	String expectedEAName;
	String expectedDocFileType;
	String expectedEAAdress;
	

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");


		// Input in = new Input();
		// in.loadEnvConfig();


		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		// Search and save it
		SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
		SessionSearch ss = new SessionSearch(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		ss.basicContentSearch(Input.TallySearch);
		hitsCountPA = ss.verifyPureHitsCount();
	    ss.saveSearchAtAnyRootGroup(SearchName, Input.shareSearchDefaultSG);
		ss.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assgnName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created a assignment " + assgnName);
		driver.waitForPageToBeReady();
		DocListPage dp = new DocListPage(driver);
		ss.ViewInDocListWithOutPureHit();
		dp.SelectColumnDisplayByRemovingExistingOnes();
		// getting metadata list for search term so that we can use this list as
		// expected value when we generate tally report(Bar chart).
		expectedCusName = dp.duplicateCheckList1(dp.getColumnValue(Input.metaDataName, true));
		expectedEAName = dp.duplicateCheckList1(dp.getColumnValue(Input.MetaDataEAName, true));
		expectedDocFileType = dp.duplicateCheckList1(dp.getColumnValue(Input.docFileType, true));
		expectedEAAdress = dp.duplicateCheckList1(dp.getColumnValue("EmailAuthorAddress", true));
		driver.getWebDriver().get(Input.url + "Search/Searches");
		ss.bulkFolderWithOutHitADD(folderName);
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityPage.AddSecurityGroup(securityGrpName);
		ss.basicContentSearch(Input.TallySearch);
		hitsCount = Integer.parseInt(ss.verifyPureHitsCount());
		ss.bulkRelease(securityGrpName);
		bc.stepInfo("Created a security group" + securityGrpName + "Bulk Realese is done");
		lp.logout();
		lp.quitBrowser();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-56206", dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifySubTally_docview(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56206");
		bc.stepInfo("To Verify Admin/RMU having a graphical representation "
				+ "of results in a tally using Tally By Options.");
		String[][] tallyBy = { { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" },
				{ "EmailAuthorName", "CustodianName", "DocFileType", "EmailAuthorAddress" },
				{ "DocFileType", "EmailAuthorName", "EmailAuthorAddress", "CustodianName" },
				{ "EmailAuthorAddress", "CustodianName", "DocFileType", "EmailAuthorName" } };
		String[] expectedMetaData = { expectedCusName, expectedEAName, expectedDocFileType, expectedEAAdress };
		String[] sourceName_RMU = { assgnName, folderName, SearchName, "Default Security Group" };
		String[] sourceName_PA = { Input.projectName, folderName, SearchName, "Default Security Group" };
		String[] sourceNames = new String[4];
		if (role == "RMU") {
			sourceNames = sourceName_RMU;
		}
		if (role == "PA") {
			sourceNames = sourceName_PA;
		}
		SoftAssert softAssertion = new SoftAssert();
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as " + role);

		TallyPage tp = new TallyPage(driver);

		tp.navigateTo_Tallypage();
		// iterating this for loop to change the source for every iteration of loop
		for (int k = 0; k < expectedMetaData.length; k++) {
			tp.sourceSelectionUsers(role, sourceNames, k);
			// iterating this for loop to change the tally by metadata value for every
			// iteration of loop
			for (int i = 0; i < tallyBy.length; i++) {
				String metadataTally = tallyBy[i][0];
				bc.stepInfo("**selecting " + metadataTally + " as tally by option.**");
				tp.selectTallyByMetaDataField(metadataTally);
				tp.validateMetaDataFieldName(metadataTally);
				String metadataBarchartTally = tp.verifyTallyChartMetadata();
				if ((!sourceNames[k].equalsIgnoreCase(Input.projectName))
						&& (!sourceNames[k].equalsIgnoreCase(Input.securityGroup))) {
					bc.stepInfo("Expected MetaData to be displayed in horizontal barchart is : ");
					bc.stepInfo(expectedMetaData[i]);
					String passMsg = "if we select tally by option as " + metadataTally
							+ " Horizontal Bar Chart Graph  " + "get populated in View with expected values.";
					String failMSg = "if we select tally by option as " + metadataTally
							+ " Horizontal Bar Chart Graph  " + "not getting populated in View with expected values.";
					bc.textCompareEquals(expectedMetaData[i], metadataBarchartTally.toLowerCase(), passMsg, failMSg);
					// doc count from bar chart validation.
					softAssertion.assertEquals(hitsCountPA, Integer.toString(tp.verifyDocCountBarChart()));
				}
			}
		}

		softAssertion.assertAll();
	}
	
	@Test(description = "RPMXCON-48757", dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyTally_WorkProduct(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-48757");
		bc.stepInfo("To verify that Tally is working correctly with work product");

		lp.loginToSightLine(username, password);

		String tallyType = "folder";

		String folderTallyByName = "folderTally" + Utility.dynamicNameAppender();
		String tagTallyName = "tagTally" + Utility.dynamicNameAppender();
		String assignmentTallyName = "AssignTally" + Utility.dynamicNameAppender();

		DocListPage dlPage = new DocListPage(driver);
		ConceptExplorerPage CEPage = new ConceptExplorerPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);

		int expDocCount = sessionsearch.basicContentSearch(Input.TallySearch);
		sessionsearch.bulkFolder(folderTallyByName);
		bc.stepInfo("Created a folder " + folderTallyByName
				+ " to use as  tally by option[Work product-tally By-tags] in tally report.");

		tp.navigateTo_Tallypage();
		tp.verifySourceList(username);
		tp.SelectSource_SecurityGroup(Input.securityGroup);
		
		//source verification
		tp.verifySourceSelected();
		tp.applyFilterToTallyBy(Input.metaDataName, "exclude", Input.custodianName_Andrew); // Applying filter 'Include'
		bc.stepInfo(
				"**Applying  Tally by field as " + folderTallyByName + " under TallyBy work product/Folder option ");
		tp.selectTallyBy(tallyType, folderTallyByName);
		List<String> ListOfMetaData = tp.verifyTallyChart();
		softAssertion.assertEquals(expDocCount, tp.verifyDocCountBarChart());
		String actualValue = folderTallyByName.trim().toUpperCase();
		String expValue = ListOfMetaData.get(0).trim();

		if (actualValue.equalsIgnoreCase(expValue)) {
			bc.passedStep("Displayed Tally  by value in tally report is " + expValue + " Which is expected.");
		} else {
			bc.failedStep("Displayed Tally  by value " + expValue + " which is not expected. ");
		}

		// bulk tag
		tp.tallyActions();
		bc.waitTime(2);
		bc.stepInfo("**Bulk Tag**");
		tp.bulkTag(tagTallyName, 1);
		bc.stepInfo("Bulk Tag Action done successfully from tally page using Work prodcut as Tally by option.");

		// doc list
		tp.tallyActions();
		tp.tallyToDocList();
		bc.verifyPageNavigation("DocList");
		List<String> docIDs = new ArrayList<String>();
		docIDs = dlPage.gettingAllDocIDs();
		int docCountInDocList = docIDs.size();

		// validation of doc count in doc list page and docs selected from tally page.
		softAssertion.assertEquals(expDocCount, docCountInDocList,
				"Docs count in doc list not matching with docs selected from tally page");
		bc.stepInfo("Navigation to doclist done successfully from tally page using Work prodcut as Tally by option.");

		// Bulk assign
		if (username.equals(Input.rmu1userName)) {
			// clicking back to source button in doc list page in order
			// to navigate back to tally page.
			CEPage.getBackToSourceBtn().Click();

			bc.stepInfo("**Bulk Assign**");
			tp.tallyActions();
			tp.bulkAssign(1);

			// assignment creation verification of doc count .
			agnmt.assignMentCreationDeletionBasedOnUser(true, role, assignmentTallyName, false, "RMU", false, "");
			bc.stepInfo("Bulk Assign Action done successfully from tally page using Work prodcut as Tally by option.");
			// deletion of assignment created sucesfullt
			agnmt.assignMentCreationDeletionBasedOnUser(false, role, assignmentTallyName, true, "RMU", false, "");

		}
		softAssertion.assertAll();

		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllTags(tagTallyName);
		tagPage.deleteAllFolders(folderTallyByName);

		lp.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-56195", dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyTally_FolderAsSource(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56195");
		bc.stepInfo(
				"To Verify Admin/RMU will have a tally report with document counts by Workproduct fields for Folder");

		String[] tallyBy = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };
		String[] expectedMetaData = { expectedCusName, expectedDocFileType, expectedEAName, expectedEAAdress };

		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as " + role);

		// Navigating to tally page
		tp.navigateTo_Tallypage();

		// selecting source as Work PRoduct -Folder
		tp.SelectSource_Folder(folderName);

		// iterating this for loop to change the tally by metadata value for every
		for (int i = 0; i < tallyBy.length; i++) {
			String metadataTally = tallyBy[i];
			bc.stepInfo("**selecting " + metadataTally + " as tally by option.**");
			tp.selectTallyByMetaDataField(metadataTally);
			tp.validateMetaDataFieldName(metadataTally);
			String metadataBarchartTally = tp.verifyTallyChartMetadata();

			bc.stepInfo("Expected MetaData to be displayed in horizontal barchart is : ");
			bc.stepInfo(expectedMetaData[i]);

			String passMsg = "if we select tally by option as " + metadataTally + " Horizontal Bar Chart Graph  "
					+ "get populated in View with expected values.";
			String failMSg = "if we select tally by option as " + metadataTally + " Horizontal Bar Chart Graph  "
					+ "not getting populated in View with expected values.";
			bc.textCompareEquals(expectedMetaData[i], metadataBarchartTally.toLowerCase(), passMsg, failMSg);
		}
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
		tp = new TallyPage(driver);
		sessionsearch = new SessionSearch(driver);
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
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = {{ Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Tally Regression2_1**");

	}
}

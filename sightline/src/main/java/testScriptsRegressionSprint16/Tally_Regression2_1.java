package testScriptsRegressionSprint16;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Tally_Regression2_1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass bc;
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

	//	Input in = new Input();
	//	in.loadEnvConfig();

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

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
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
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Tally Regression2_1**");

	}
}

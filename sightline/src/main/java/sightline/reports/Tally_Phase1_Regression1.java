
package sightline.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.testng.ITestResult;
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

public class Tally_Phase1_Regression1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass bc;
	String hitsCountPA;
	int hitsCount;

	String SearchName1 = "AATally" + Utility.dynamicNameAppender();
	String folderName1 = "AATally" + Utility.dynamicNameAppender();

	String SearchName2 = "AATally" + Utility.dynamicNameAppender();
	String folderName2 = "AATally" + Utility.dynamicNameAppender();

	String assgnTallyBy = "AATally" + Utility.dynamicNameAppender();
	String tagTallyBy = "AATally" + Utility.dynamicNameAppender();
	String secGrpName = "AATally" + Utility.dynamicNameAppender();
	String CusName;
	int count;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
		bc = new BaseClass(driver);
		lp = new LoginPage(driver);
	}

	@Test(description = "RPMXCON-48694", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void tallyEndToEnd(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-48694");
		bc.stepInfo("To Verify Tally End to End Flow.");
		SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
		SessionSearch ss = new SessionSearch(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String FilterType = "Include";
		lp.loginToSightLine(username, password);
		if (role.equalsIgnoreCase("PA")) {
			// Pre-requesites block
			// lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
			securityPage.AddSecurityGroup(secGrpName);
			ss.basicContentSearch(Input.TallySearch);
			hitsCount = Integer.parseInt(ss.verifyPureHitsCount());
			ss.bulkRelease(secGrpName);
			bc.stepInfo("Created a security group" + secGrpName + "Bulk Relaese is done");
			// lp.logout();
		}
		if (role.equalsIgnoreCase("RMU")) {
			// Pre-requesites block
			ss.basicContentSearch(Input.TallySearch);
			hitsCount = Integer.parseInt(ss.verifyPureHitsCount());
			ss.saveSearchAtAnyRootGroup(SearchName1, Input.shareSearchDefaultSG);
			bc.stepInfo("Created a SavedSearch " + SearchName1);
			ss.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignment_fromAssignUnassignPopup(assgnTallyBy, Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			bc.stepInfo("Created a assignment " + assgnTallyBy);
			driver.waitForPageToBeReady();

			driver.getWebDriver().get(Input.url + "Search/Searches");
			ss.bulkFolderWithOutHitADD(folderName1);
			bc.stepInfo("Created a Folder " + folderName1);
			driver.getWebDriver().get(Input.url + "Search/Searches");
			ss.verifyBulkTag(tagTallyBy);
			bc.stepInfo("Created a Tag " + tagTallyBy);
			// getting expected value for meta data(to verify subtally report after applying
			// filter)
			// by applying filter for custodian name from doc list page.
			DocListPage dp = new DocListPage(driver);
			driver.getWebDriver().get(Input.url + "Search/Searches");
			ss.ViewInDocListWithOutPureHit();
			CusName = dp.getColumnValue(Input.metaDataName, false).get(0);
			dp.applyCustodianNameFilter(CusName);
			bc.waitForElement(dp.getApplyFilters());
			dp.getApplyFilters().Click();
			count = dp.getColumnValue(Input.metaDataName, false).size();// expected doc count after applying filter
			bc.selectproject();
			ss.basicContentSearch(Input.searchStringStar);
			ss.saveSearchAtAnyRootGroup(SearchName2, Input.shareSearchDefaultSG);
			bc.stepInfo("Created a SavedSearch " + SearchName2);
			driver.getWebDriver().get(Input.url + "Search/Searches");
			ss.bulkFolder(folderName2);
			bc.stepInfo("Created a Folder " + folderName2);
		}
		/*
		 * String[][] sourceNames = { { SearchName2, SearchName1 }, { folderName1,
		 * folderName2 }, { secGrpName, Input.securityGroup }, { Input.projectName, null
		 * } };
		 */
		String[][] sourceNames = null;
		String[][] sourceNames_PA = { { SearchName2, SearchName1 }, { folderName1, folderName2 },
				{ secGrpName, Input.securityGroup }, { Input.projectName, null } };
		String[][] sourceNames_RMU = { { SearchName2, SearchName1 }, { folderName1, folderName2 },
				{ secGrpName, Input.securityGroup } };
		String[][] tallyByName = { { assgnTallyBy, folderName1, tagTallyBy, Input.metaDataName },
				{ folderName1, tagTallyBy, assgnTallyBy, Input.metaDataName },
				{ tagTallyBy, assgnTallyBy, folderName1, Input.metaDataName } };

		String[][] tallyType = { { "assignment", "folder", "tag", "metadata" },
				{ "folder", "tag", "assignment", "metadata" }, { "tag", "assignment", "folder", "metadata" } };
		SoftAssert assertion = new SoftAssert();
		TallyPage tp = new TallyPage(driver);
		if (role.equalsIgnoreCase("PA")) {
			sourceNames = sourceNames_PA;
		}
		if (role.equalsIgnoreCase("RMU")) {
			sourceNames = sourceNames_RMU;
		}
		for (int k = 0; k < sourceNames.length; k++) {

			for (int i = 0; i < tallyByName.length; i++) {
				String[] source = { sourceNames[k][0], sourceNames[k][1] };
				tp.navigateTo_Tallypage();
				tp.selectmultipleSources(k, source);
				tp.verifySourceSelected();
				bc.stepInfo("**Applying Tally by field as " + tallyType[i][0] + " " + tallyByName[i][0] + "");
				tp.selectTallyBy(tallyType[i][0], tallyByName[i][0]);
				List<String> ListOfMetaData = tp.verifyTallyChart();
				assertion.assertEquals(hitsCount, tp.verifyDocCountBarChart());
				String actualValue = tallyByName[i][0].trim().toUpperCase();
				String expValue = ListOfMetaData.get(0).trim();
				System.out.println(actualValue);
				System.out.println(expValue);
				if (actualValue.equalsIgnoreCase(expValue)) {
					bc.passedStep("Displayed Tally  by value " + expValue + " Which is expected.");
				} else {
					bc.failedStep("Displayed Tally  by value " + expValue + " which is not expected. ");
				}
				tp.tallyActions();
				bc.waitTime(2);
				tp.getTally_actionSubTally().Click();
				for (int j = 1; j < tallyByName[i].length; j++) {
					bc.stepInfo("**Applying Sub-Tally by field as " + tallyType[i][0] + " " + tallyByName[i][j] + "");
					tp.selectSubTallyBy(tallyType[i][j], tallyByName[i][j]);
					tp.applyFilterToSubTallyBy(CusName, Input.metaDataName, FilterType);
					int subTallyDocCount = tp.getDocCountSubTally();
					assertion.assertEquals(subTallyDocCount, count);
					System.out.println(subTallyDocCount);
					tp.clearingActiveFiltersInTallyBy().waitAndClick(7);
				}
			}
			assertion.assertAll();
			bc.stepInfo("Sucessfully Verified Tally End to End Flow.");
		}

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
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			lp.quitBrowser();
		}
		try {
			lp.logout();
			// LoginPage.clearBrowserCache();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Tally Regression1**");

	}
}

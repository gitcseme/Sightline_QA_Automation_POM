package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearchRegression_2_20 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * @author
	 * @Date: 30/08/2022
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To check that we have 7 (seven) defautl pre-created saved
	 *              search groups/models under the \"Pre-Built Models\" Search Group
	 *              i.e, DEP IP Theft, Discrimination,FCPA, Harassment, PII, PRIV,
	 *              NR Detection.
	 */

	@Test(description = "RPMXCON-64863", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifySevenDefaultPreCreatedSavedSearchGroupsPresentUnderPreBuildModelsSG(String username,
			String password, String fullname) {

		List<String> ListOfSavedSearchGroup = new ArrayList<String>(Arrays.asList(Input.DEPIPTheft,
				Input.Discrimination, Input.FCPA, Input.Harassment, Input.NR_Detection, Input.PII, Input.PRIV));
		base.stepInfo("Test case Id: RPMXCON-64863 - Saved Search");
		base.stepInfo(
				"To check that we have 7 (seven) defautl pre-created saved search groups/models under the \"Pre-Built Models\" Search Group i.e, DEP IP Theft, Discrimination,FCPA, Harassment, PII, PRIV, NR Detection.");
		base.failedMessage("Make sure the project has valid pre-build testdatas for verification");

		// Login as user
		login.loginToSightLine(username, password);
		base.stepInfo("Logged In As : " + fullname);

		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);
		base.passedStep(
				"Verified that we have a new default Search Group added called \"Pre-Built Models\" Search Group under \"Shared with Default Security Group\".");
		saveSearch.getExpansionArrow(Input.preBuilt).waitAndClick(5);

		// Verify that we have 7 (seven) dafautl pre-created saved search groups/models
		// under the "Pre-Built Models" Search Group i.e., DEP IP Theft, Discrimination,
		// FCPA, Harassment, PII, PRIV, NR Detection.
		saveSearch.verifyPresenceOfListOfSavedSearchGroups(ListOfSavedSearchGroup, Input.preBuilt);
		base.passedStep(
				"Verified that we have 7 (seven) dafautl pre-created saved search groups/models under the \"Pre-Built Models\" Search Group i.e., DEP IP Theft, Discrimination, FCPA, Harassment, PII, PRIV, NR Detection.");

		// Logout Application
		login.logout();
	}

	/**
	 * @author
	 * @Date: 30/08/2022
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To check that we have little arrow present at 6 (six) default
	 *              pre-created saved search groups/models present under the
	 *              \"Pre-Built Models\" Search Group and its clickable excluding NR
	 *              Detection.
	 */
	@Test(description = "RPMXCON-64876", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyLittleArrowPresentAtSixSavedSearchGroupsPresentUnderPreBuiltModelsSGAndClickableExcludingNRDetection(
			String username, String password, String fullname) {
		Map<String, String> parentGroupAndChildGroupPair = Map.of(Input.DEPIPTheft, "DEP IP Theft Keywords",
				Input.Discrimination, "Antisemitism", Input.FCPA, "FCPA Keywords", Input.Harassment, "Cartoons",
				Input.PII, "EU PII", Input.PRIV, "Expletives");
		base.stepInfo("Test case Id: RPMXCON-64876 - Saved Search");
		base.stepInfo(
				"To check that we have little arrow present at  6 (six) default pre-created saved search groups/models present under the \"Pre-Built Models\" Search Group and its clickable excluding NR Detection.");
		base.failedMessage("Make sure the project has valid pre-build testdatas for verification");

		// Login as user
		login.loginToSightLine(username, password);
		base.stepInfo("Logged In As : " + fullname);

		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group and arrow Clickable
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), "Pre-Built Models");
		base.passedStep(
				"Verified that a new default Search Group added called 'Pre-Built Models' Search Group under \"Shared with Default Security Group\".");
		saveSearch.getExpansionArrow(Input.preBuilt).waitAndClick(5);
		base.ValidateElement_Presence(saveSearch.getMainFolders(Input.DEPIPTheft), "'DEP IP Theft' saved search group");
		base.stepInfo("Verified that we have a little arrow at 'Pre-Built Models' Search Group and it clickable.");

		// Verify that little arrow Not present at 'NR Detection' saved search group ls
		// present under the 'Pre-Built Models' Search Group and its clickable
		base.ValidateElement_Absence(saveSearch.getExpansionArrow(Input.NR_Detection),
				"'NR Detection' saved search group Arrow ");

		// Verify that we have little arrow present at 6 (six) default pre-created saved
		// search groups/models present under the "Pre-Built Models" Search Group and
		// its clickable excluding NR Detection.
		saveSearch.verifyPresenceOfExpansionArrowAndClickableOfChildSavedSGsUnderPreBuiltModelsSG(
				parentGroupAndChildGroupPair);
		base.passedStep(
				"Verified that we have little arrow present at  6 (six) default pre-created saved search groups/models present under the \"Pre-Built Models\" Search Group and its clickable excluding NR Detection.");

		// Logout Application
		login.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}

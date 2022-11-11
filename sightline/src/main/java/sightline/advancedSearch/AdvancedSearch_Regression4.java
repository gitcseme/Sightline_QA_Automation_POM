package sightline.advancedSearch;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Regression4 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass baseClass;
	String hitsCountPA;
	int hitsCount;


	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException, AWTException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that while performing conceptual search, slider
	 *              value(Precision Sensitivity) and Precision Sensitivity text box
	 *              value remains same after running the searches. [RPMXCON-63568]
	 */
	@Test(description = "RPMXCON-63568", dataProvider = "Users", groups = {
			"regression" })
	public void verifyConceptualPS(String username, String password) throws InterruptedException {
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();

		SessionSearch session = new SessionSearch(driver);

		lp.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-63568 Advanced Search");
		baseClass.stepInfo(
				"Verify that while performing conceptual search, slider value(Precision Sensitivity) and Precision Sensitivity text box value remains same after running the searches.");

		session.navigateToAdvancedSearchPage();
		String precisionValueBeforeSearch = session.verifyingPrecisionValue(true, Input.conceptualSearchString1 ,true, true);
		session.saveAndReturnPureHitCount();

		session.getModifyASearch().waitAndClick(10);
		String precisionValueAfterSearch = session.verifyingPrecisionValue(false, null , false , false);

		String passMsg = precisionValueBeforeSearch + " is the Precision value Before Search and After search is "
				+ precisionValueAfterSearch;
		String failMsg = "Precision value befor and after search is not Same";
		baseClass.textCompareEquals(precisionValueAfterSearch, precisionValueBeforeSearch, passMsg, failMsg);

		lp.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that while performing conceptual search, slider
	 *              value(Precision Sensitivity) and Precision Sensitivity text box
	 *              value is same in its default value as well as when we change its
	 *              slider value. [RPMXCON-63566]
	 */
	//@Test(description = "RPMXCON-63566", dataProvider = "Users", groups = {
			//"regression" })
	public void verifyConceptualDefaultPS(String username, String password) throws InterruptedException {
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();

		SessionSearch session = new SessionSearch(driver);

		lp.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-63566 Advanced Search");
		baseClass.stepInfo(
				"Verify that while performing conceptual search, slider value(Precision Sensitivity) and Precision Sensitivity text box value is same in its default value as well as when we change its slider value.");

		session.navigateToAdvancedSearchPage();
		String precisionValueBeforeSearch = session.verifyingPrecisionValue(true, Input.conceptualSearchString1 , true , true);
		session.saveAndReturnPureHitCount();

		lp.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that while performing conceptual search, slider
	 *              value(Precision Sensitivity) is displaying its default value
	 *              i.e,70. [RPMXCON-63556]
	 */
	//@Test(description = "RPMXCON-63556", dataProvider = "Users", groups = {
			//"regression" })
	public void verifyConceptualFlexible(String username, String password) throws InterruptedException {
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();

		SessionSearch session = new SessionSearch(driver);

		lp.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-63556 Advanced Search");
		baseClass.stepInfo(
				"Verify that while performing conceptual search, slider value(Precision Sensitivity) is displaying its default value i.e,70.");

		session.navigateToAdvancedSearchPage();
		String precisionValueBeforeSearch = session.verifyingPrecisionValue(true, Input.conceptualSearchString1, true , true);
		session.saveAndReturnPureHitCount();

		lp.logout();
	}

	
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		baseClass = new BaseClass(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			lp.logoutWithoutAssert();

		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Advanced search Regression1**");
	}
}

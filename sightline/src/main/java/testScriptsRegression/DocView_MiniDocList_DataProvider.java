
package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;



public class DocView_MiniDocList_DataProvider {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	MiniDocListPage miniDocListpage;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	ReusableDocViewPage reusableDocViewPage;
	SavedSearch savedSearch;
	CodingForm codingForm;
	DocListPage docListPage;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;

	String assignmentNameToChoose;
    String hitsCount;
    String savedSearchs = "AsavedToDocview" + Utility.dynamicNameAppender();
    String assgn = "Assignment" + Utility.dynamicNameAppender();
	String projectFieldINT = "AAMini" + Utility.dynamicNameAppender();
	String assgnWindow = "AAWindow" + Utility.dynamicNameAppender();
	String assgnOne = "AAOneBy" + Utility.dynamicNameAppender();
	
	
	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		miniDocListpage = new MiniDocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		codingForm = new CodingForm(driver);
		docListPage = new DocListPage(driver);
		softAssertion = new SoftAssert();
		projectPage = new ProjectPage(driver);
		securityGroupPage=new SecurityGroupsPage(driver);
	}

	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "paRmuRole")
	public Object[][] paRmuRole() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password } };
	}

	@DataProvider(name = "userDetailss")
	public Object[][] userLoginSaPaRmu() {
		return new Object[][] { { "rmu", Input.rmu1userName, Input.rmu1password, "rev" },
				{ "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	@DataProvider(name = "twoLogins")
	public Object[][] userRmuRev() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "sixRole")
	public Object[][] sixRole() {
		return new Object[][] { { "pa", Input.pa1userName, Input.pa1password },
				{ "rmu", Input.rmu1userName, Input.rmu1password }, { "rev", Input.rev1userName, Input.rev1password },
				{ "pa1", Input.pa1userName, Input.pa1password }, { "rmu1", Input.rmu1userName, Input.rmu1password },
				{ "rev1", Input.rev1userName, Input.rev1password } };
	}


	/**
	 * Author : Raghuram A date: 8/15/21 NA Modified date: 8/18/21 Modified by:
	 * Raghuram A Description : Verify on gear icon to open configure mini doc list
	 * is clicked multiple times then repetitive 'Selected Fields' should not be
	 * displayed on optimized Sort order tab Id:RPMXCON-51889 Sprint : 1
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 1)
	public void repeatativeFieldsCheckonOptimizedSortTab(String fullName, String userName, String password)
			throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51889");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo(
				"Verify on gear icon to open configure mini doc list is clicked multiple times then repetitive 'Selected Fields' should not be displayed on optimized Sort order tab");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		baseClass.stepInfo("Step 1 : Route for Doc View mini list");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 1 : Click Gear icon for multiple times");
		// Click Gear icon for multiple times
		miniDocListpage.clickingGearIconMultipletimes(2);

		baseClass.stepInfo("Step 2 : Verify Mini docList popup");
		// Mini doc list tab
		miniDocListpage.miniDoclistTabHeader();

		baseClass.stepInfo("Step 3 : Verifying that no duplication of selected fields listed");
		// main method for RPMXCON-51890
		miniDocListpage.verifyDuplicatefieldsInBothtabs();

		baseClass.stepInfo("Step 4 : Repeat the flow via Child Window 'multiple clicks on gear icon'");
		// Gear icon mutiple times through childwindow
		miniDocListpage.accessingGearIconMultipletimesviaChildWindow();

		baseClass.stepInfo("Step 5 : Verifying that no duplication of selected fields listed");
		// main method for RPMXCON-51890
		miniDocListpage.verifyDuplicatefieldsInBothtabs();

		loginPage.logout();

	}

	/**
	 * Author : Raghuram A date: 8/15/21 NA Modified date: 8/18/21 Modified by:
	 * Raghuram A Description : Verify on gear icon to open configure mini doc list
	 * is clicked multiple times then repetitive 'Selected Fields' should not be
	 * displayed on Manual Sort order tab Id:RPMXCON-51890 Sprint : 1
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 2)
	public void repeatativeFieldsonManualSortTab(String fullName, String userName, String password) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51890");
		driver.Manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		baseClass.stepInfo(
				"Verify on gear icon to open configure mini doc list is clicked multiple times then repetitive 'Selected Fields' should not be displayed on Manual Sort order tab");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		baseClass.stepInfo(
				"#### RPMXCON-51889 Verify on gear icon to open configure mini doc list is clicked multiple times then repetitive 'Selected Fields' should not be displayed on optimized Sort order tab ####");

		baseClass.stepInfo("Step 1 : Route for Doc View mini list");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 1 : Click Gear icon for multiple times");
		// Click Gear icon for multiple times
		miniDocListpage.clickingGearIconMultipletimes(2);

		baseClass.stepInfo("Step 2 : Verify Mini docList popup");
		// Mini doc list tab
		miniDocListpage.miniDoclistTabHeader();

		baseClass.stepInfo("Step 3 : Verifying for 'Optimized sort order'  no duplication of selected fields listed");
		driver.waitForPageToBeReady();
		// Pick column Display field list
		miniDocListpage.pickColumnDisplayfieldsDuplicatescheck();

		// Close minidoclist popup
		driver.waitForPageToBeReady();
		baseClass.waitForElement(miniDocListpage.getMiniDocListcloseButton());
		miniDocListpage.getMiniDocListcloseButton().Click();
		baseClass.stepInfo("Closed Mini Doclist");

		baseClass.stepInfo("Step 4 : Repeat the flow via Child Window 'multiple clicks on gear icon'");
		// Gear icon mutiple times through childwindow
		miniDocListpage.accessingGearIconMultipletimesviaChildWindow();

		baseClass.stepInfo("Step 5 : Verifying that no duplication of selected fields listed");
		// Pick column Display field list
		miniDocListpage.pickColumnDisplayfieldsDuplicatescheck();

		// Close mini doclist popup
		baseClass.waitForElement(miniDocListpage.getMiniDocListcloseButton());
		miniDocListpage.getMiniDocListcloseButton().Click();

		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify the default columns from mini doc list of doc view
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 3)
	public void verifyDefaultHeaderValue(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51429");

//      login as @user		
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

//		search and go to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

//		verify header value
		reusableDocViewPage.defaultHeaderValue();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * @Author : Baskar date: 08/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify that if user navigates to doc view from the Basic
	 *              search, then user can select 4 webfields from preselected list
	 *              to display in the panel of mini doc list in manual mode
	 */
	@DataProvider(name = "userDetailTwoLogin")
	public Object[][] userDetailTwoLogin() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	@Test(enabled = true, dataProvider = "userDetailTwoLogin", groups = { "regression" }, priority = 4)
	public void basicSearchToDocViewToSelect4WebFields(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50881");

//	     login as @user		
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();

		miniDocListpage.fromBasicSearchToSelectWebField();

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 07/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify after impersonation user is allowed to select up to
	 *              4 webfields from a preselected list to display in the panel of
	 *              mini doc list in the manual mode
	 */

	@Test(enabled = true, dataProvider = "userDetailss", groups = { "regression" }, priority = 5)
	public void verifyAfterImpersonateToSelect4WebFieldsManualMode(String roll, String userName, String password,
			String impersonate) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50880");
		String assignmentNameToManual = null;
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);

		if (roll.equalsIgnoreCase("rmu")) {
			// search to Assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentNameToManual = miniDocListpage.assignmentCreationWithManualSortForDisToRMUAndRe();
			System.out.println("assignmentNameToChoose");
			baseClass.stepInfo("Created Assignment " + assignmentNameToManual);
		}
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			}

		case "rev":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			}
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}

		if (roll.equalsIgnoreCase("rmu")) {
			assignmentPage.SelectAssignmentByReviewer(assignmentNameToManual);
			baseClass.stepInfo("User on the doc view after selecting the assignment");

			miniDocListpage.afterImpersonateWebFieldsSelectionManualMode();
			driver.waitForPageToBeReady();
		} else if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.ViewInDocView();
			miniDocListpage.afterImpersonateWebFieldsSelectionManualMode();
			driver.waitForPageToBeReady();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify that if user navigates to doc view from the Saved
	 *              search, then user can select 4 webfields from preselected list
	 *              to display in the panel of mini doc list in manual mode
	 */

	@Test(enabled = true, dataProvider = "userDetailTwoLogin", groups = { "regression" }, priority = 6)
	public void savedSearchToDocViewToSelect4WebFields(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50882");
		String savedSearchs = "AsavedToDocview" + Utility.dynamicNameAppender();

//	     login as @user		
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		// Saved search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedSearchs);
		savedSearch.savedSearchToDocView(savedSearchs);
		baseClass.stepInfo("User navigated to docviiew page from saved search page");

		driver.waitForPageToBeReady();

		miniDocListpage.fromSavedSearchToSelectWebField();

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 13/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Configure Mini DocList modal window should be launched on
	 *              click of the small gear icon in the Mini DocList panel outside
	 *              of an assignment
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 7)
	public void repeatativeFieldsCheckonOptimizedSortTa(String fullName, String userName, String password)
			throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51336");
		baseClass.stepInfo("Verify on gear icon to open configure mini doc list popup window");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

//		Session search to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		miniDocListpage.configureMiniDocListPopupOpen();

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 13/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify user can select 'Remove Code Same' from mini doc list
	 *              child window
	 */

	@Test(enabled = true, dataProvider = "userDetailTwoLogin", groups = { "regression" }, priority = 8)
	public void removeCodeSameAsFromChildWindow(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51231");
		String Asssignment = "AssignmentRemove" + Utility.dynamicNameAppender();
		System.out.println(Asssignment);

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

//		Session search to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		miniDocListpage.removeCodeSameAsChildWindow();

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 13/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify user after impersonation should able to 'Remove Code
	 *              same as this' on document selection from mini doc list
	 */

	@Test(enabled = true, dataProvider = "userDetailss", groups = { "regression" }, priority = 9)
	public void removeCodeSameAsAfterImpersonate(String roll, String userName, String password, String impersonate)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51228");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			}

		case "rev":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			}
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}

		if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu") || roll.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.ViewInDocView();
			miniDocListpage.removeCodeSameAsAfterImpersonate();
			driver.waitForPageToBeReady();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 07/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify after impersonation user is allowed to select up to
	 *              4 webfields from a preselected list to display in mini doc list-
	 *              optimized mode
	 */

	@Test(enabled = true, dataProvider = "userDetailss", groups = { "regression" }, priority = 10)
	public void verifyAfterImpersonateToSelect4WebFields(String roll, String userName, String password,
			String impersonate) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50887");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);

		if (roll.equalsIgnoreCase("rmu")) {
			// search to Assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentNameToChoose = miniDocListpage.assignmentCreationWithManualSortForDisToRMUAndRe();
			System.out.println("assignmentNameToChoose");
			baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);
		}
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			}

		case "rev":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			}
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}

		if (roll.equalsIgnoreCase("rmu")) {
			assignmentPage.SelectAssignmentByReviewer(assignmentNameToChoose);
			baseClass.stepInfo("User on the doc view after selecting the assignment");

			miniDocListpage.afterImpersonateWebFieldsSelectionOptimizedMode();
			driver.waitForPageToBeReady();
		} else if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.ViewInDocView();
			miniDocListpage.afterImpersonateWebFieldsSelectionOptimizedMode();
			driver.waitForPageToBeReady();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify the 'Selected Fields' for 'Family Relationship' field
	 *              from Optimized Sort Order of Configure mini doc list pop up
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 11)
	public void verifySelectdFieldsToRemove(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51653");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

//		Go to docview via session search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		miniDocListpage.removingFieldsInPopUpWindow();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when switch from Optimized to Custom, the display
	 *              fields set in the Optimized Sort are displayed for custom sort
	 *              from mini doc list child window
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 12)
	public void verifySelectedFieldInChildWindow(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51742");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

//		Go to docview via session search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		miniDocListpage.selectedFieldInChildWindow();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when switched from custom to optimized sort, then
	 *              optimized sort should display the default fields of same sort
	 *              order
	 */

	@DataProvider(name = "revImpersoante")
	public Object[][] userLoginPaRmuRev() {
		return new Object[][] { { "rmu", Input.rmu1userName, Input.rmu1password, "rev" } };
	}

	@Test(enabled = true, dataProvider = "revImpersoante", groups = { "regression" }, priority = 13)
	public void verifySwitchedFromCustomToOptimizedSortRemoval(String roll, String userName, String password,
			String impersonate) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51738");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

		if (roll.equalsIgnoreCase("rmu")) {
			// search to Assignment creation
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.bulkAssign();
			assignmentNameToChoose = miniDocListpage.assignmentCreationWithManualSortForDisToRMUAndRe();
			System.out.println("assignmentNameToChoose");
			baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);
		}
		switch (impersonate) {
		case "rev":
			if (roll.equalsIgnoreCase("rmu") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}
		if (roll.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			assignmentPage.SelectAssignmentByReviewer(assignmentNameToChoose);
			driver.waitForPageToBeReady();
			baseClass.stepInfo("User on the doc view after selecting the assignment");
		}
		miniDocListpage.customToOptimizedSortDefaultDisplayAfterRemoval();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when switch from Optimized to Custom, the display
	 *              fields set in the Optimized Sort are displayed for custom sort
	 *              in context of an assignment
	 */

	@Test(enabled = true, dataProvider = "revImpersoante", groups = { "regression" }, priority = 14)
	public void verifySwitchedFromCustomToOptimizedSort(String roll, String userName, String password,
			String impersonate) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51737");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

		if (roll.equalsIgnoreCase("rmu")) {
			// search to Assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentNameToChoose = miniDocListpage.assignmentCreationWithManualSortForDisToRMUAndRe();
			System.out.println("assignmentNameToChoose");
			baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);
		}
		switch (impersonate) {
		case "rev":
			if (roll.equalsIgnoreCase("rmu") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}
		if (roll.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			assignmentPage.SelectAssignmentByReviewer(assignmentNameToChoose);
			driver.waitForPageToBeReady();
			baseClass.stepInfo("User on the doc view after selecting the assignment");
		}
		miniDocListpage.customToOptimizedSortDefaultDisplay();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when switch from Optimized to Custom, the display
	 *              fields set in the Optimized Sort are displayed for custom sort
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 15)
	public void verifySwitchedFromOptimizedSortToCustom(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51736");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

//      Go to docview via session search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		miniDocListpage.OptimizedSortToCustomDefaultDisplay();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when user opens Configure Mini DocList modal
	 *              window, whatever columns are presently presented in Mini DocList
	 *              will be presented in the field selection tab
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 16)
	public void verifyDefaultValueInOptimizedSort(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51739");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

//      Go to docview via session search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		miniDocListpage.verifyDefaultValueInOptimizedSort();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Veirfy that whatever the current sort order is in Mini DocList
	 *              will be presented in the Sort Order tab for selected manual sort
	 *              order
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 17)
	public void verifyCurrentSortOrderInMiniDocList(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51740");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

//      Go to docview via session search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		miniDocListpage.currentSortOrderShouldBeDisplayed();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when the user changes the fields for selection in
	 *              the column presentation, and/or modifies the sort order, the app
	 *              honors t hose new selections
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 18)
	public void verifyChangesColumnPresentation(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51741");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

//      Go to docview via session search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		miniDocListpage.currentSortOrderShouldBeDisplayed();
		driver.waitForPageToBeReady();
		driver.getWebDriver().get(Input.url + "Search/Searches");

		sessionSearch.ViewInDocView();
		miniDocListpage.againDocviewToViewNewChanges();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 15/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that document should be loaded successfully when
	 *              document viewed from mini doc list from Translations tab of
	 *              previous document
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 19)
	public void verifyTranslationsTab(String fullName, String userName, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51683");
		baseClass.stepInfo("Verify that document should be loaded successfully when document viewed "
				+ "from mini doc list from Translations tab of previous document");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

		// searching for document and go to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.verifyAllTabsForDocument();
		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 16/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify when user select action as 'Remove code same' for
	 *              documents which are not marked as code same as this from mini
	 *              doc list
	 */

	@Test(enabled = true, dataProvider = "twoLogins", groups = { "regression" }, priority = 20)
	public void verifyWarningCodeSameAs(String fullName, String userName, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51223");
		baseClass.stepInfo("To verify when user select action as 'Remove code same' for "
				+ "documents which are not marked as code same as this from mini doc list");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

		// searching for document and go to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.warningMsgForCodeSameAs();

		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify user can view main document in default view panel
	 *              with having some rules on work product
	 */

	@Test(enabled = true, dataProvider = "userDetailTwoLogin", groups = { "regression" }, priority = 21)
	public void savedSearchAndBasicToDocView(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50904");
		baseClass.stepInfo("To verify user can view main document in default "
				+ "view panel with having some rules on work product");
		String savedSearchs = "AsavedToDocview" + Utility.dynamicNameAppender();
		String verifyCoding = "Test" + Utility.dynamicNameAppender();

//	    login as @user		
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as User: " + fullName);

		// Saved search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedSearchs);
		savedSearch.savedSearchToDocView(savedSearchs);
		baseClass.stepInfo("User navigated to docview page from saved search page");

		String docId = docViewPage.editSavedSearchCodingForm(verifyCoding);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.ViewInDocView();
		String CodingFormText = docViewPage.reVerifyCodingForm(docId);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(verifyCoding, CodingFormText);
		baseClass.passedStep("Document updated successfully when navigates from basic search");
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify that if user navigates to doc view from the Save
	 *              search, then user can alter the sort sequence on the mini doc
	 *              list in the manual mode
	 */

	@Test(enabled = true, dataProvider = "userDetailTwoLogin", groups = { "regression" }, priority = 22)
	public void verifySavedSearchToSortSequence(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50894");
		baseClass.stepInfo("To verify that if user navigates to doc view from the Save search,"
				+ " then user can alter the sort sequence on the mini doc list in the manual mode");
		String savedSearchs = "AsavedToDocview" + Utility.dynamicNameAppender();

//	    login as @user		
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as User: " + fullName);

		// Saved search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedSearchs);
		savedSearch.savedSearchToDocView(savedSearchs);
		baseClass.stepInfo("User navigated to docview page from saved search page");

		miniDocListpage.savedSearchToSortSequence();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify that if user navigates to doc view from the Basic
	 *              search, then user can alter the sort sequence on the mini doc
	 *              list in the manual mode
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 23)
	public void verifyBasicSearchToSortSequence(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50893");
		baseClass.stepInfo("To verify that if user navigates to doc view from the Save search,"
				+ " then user can alter the sort sequence on the mini doc list in the manual mode");

//	    login as @user		
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as User: " + fullName);

		// Saved search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("User navigated to docview page from basic page");

		miniDocListpage.savedSearchToSortSequence();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 07/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify after impersonation user is allowed to alter the
	 *              sort sequence on the mini doc list in the manual mode when
	 *              redirects from Manage Assignment page
	 */

	@Test(enabled = true, dataProvider = "userDetailss", groups = { "regression" }, priority = 25)
	public void afterImpSortSequenceFromAssignment(String roll, String userName, String password, String impersonate)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50892");
		String assignmentNameToManual = null;
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);

		if (roll.equalsIgnoreCase("rmu")) {
			// search to Assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentNameToManual = miniDocListpage.assignmentCreationWithManualSortForDisToRMUAndRe();
			System.out.println("assignmentNameToChoose");
			baseClass.stepInfo("Created Assignment " + assignmentNameToManual);
		}
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			}

		case "rev":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			}
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}

		if (roll.equalsIgnoreCase("rmu")) {
			assignmentPage.SelectAssignmentByReviewer(assignmentNameToManual);
			baseClass.stepInfo("User on the doc view after selecting the assignment");

			miniDocListpage.savedSearchToSortSequence();
			driver.waitForPageToBeReady();
		} else if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.ViewInDocView();
			miniDocListpage.savedSearchToSortSequence();
			driver.waitForPageToBeReady();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify sort sequence from mini doc list when user redirects
	 *              from doc list page
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 26)
	public void verifySortSequenceDocListToDocView(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50895");
		baseClass.stepInfo("To verify sort sequence from mini doc list when user " + "redirects from doc list page");

//	    login as @user		
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as User: " + fullName);

		// Saved search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("User navigated to docview page from doclist page");

//		Selecting document in doclist page
		docListPage.selectAllDocumentsInCurrentPageOnly();
		LinkedList<String> docListtext = docListPage.VerifyDocsInAscendingorder();
		docListPage.docListToDocView();
		LinkedList<String> miniDocListtext = reusableDocViewPage.miniDocListSortSequence();
		softAssertion.assertEquals(docListtext, miniDocListtext);
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 25/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify scrolling from mini doc list child window when document
	 *              is selected from mini doc list after scrolling down with less
	 *              number of documents
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 27)
	public void scrollDownWithLessDocument(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51610");
		baseClass.stepInfo("Verify scrolling from mini doc list child window when document is selected from "
				+ " mini doc list after scrolling down with less number of documents");

//      login as @user		
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

//		search and go to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

//		Scroll down with less number of document
		docViewPage.verifyScrollingDocumentLess();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 25/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that document should be loaded successfully when
	 *              document viewed from mini doc list from Text tab of previous
	 *              document
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 28)
	public void verifyTextTab(String fullName, String userName, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51682");
		baseClass.stepInfo("Verify that document should be loaded successfully when document "
				+ " viewed from mini doc list from Text tab of previous document");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		driver.waitForPageToBeReady();

		// searching for document and go to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.verifyTextTabsForDocument();
		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 17/12/21 NA Modified date: NA Modified by:NA Description
	 * : Verify that EmailAuthorNameAndAddress field should be displayed on the
	 * metadata panel of doc view Test CaseId: 'RPMXCON-51523' Sprint : 8
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 29)
	public void verifyEmailAuthorNameAndAddressField(String fullName, String userName, String password)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51523");

		baseClass.stepInfo(
				"Verify that EmailAuthorNameAndAddress field should be displayed on the metadata panel of doc view");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 2: Search for documents and go to doc view");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		baseClass.stepInfo("Step 3: Check the metadata panel for the EmailAuthorNameAndAddress field");

		driver.scrollingToElementofAPage(docViewPage.getDocView_MetaDataPanel_EmailAuthorNameAndAddress());
		softAssertion.assertTrue(docViewPage.getDocView_MetaDataPanel_EmailAuthorNameAndAddress().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"EmailAuthorNameAndAddress field is displayed on metadata panel and field is a concatenation of EmailAuthorName and EmailAddress fields.");

		baseClass.stepInfo(
				"Step 4: Click 'Browse All Metadata' button from metadata panel and check for the  EmailAuthorNameAndAddress field  from the pop up");

		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(docViewPage.getDocView_MetaDataPanel_BrowseAllMetaDataPanel());
		baseClass.waitForElement(docViewPage.getDocView_MetaDataPanel_BrowseAllMetaDataPanel());
		docViewPage.getDocView_MetaDataPanel_BrowseAllMetaDataPanel().waitAndClick(3);

		driver.waitForPageToBeReady();
		softAssertion
				.assertTrue(docViewPage.getDocView_MetaDataPanel_BrowseAllMetaDataPanel_PopupField().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("All Meta Data Panel opened successfully");

		docViewPage.verifyEmailAuthorNameAndAddressFieldInMetaDataPanel();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 17/12/21 NA Modified date: NA Modified by:NA Description
	 * : Verify on click of 'Save Configuration' mini doc list should be displayed
	 * with the selected webfields for Set Document Sorting Test CaseId:
	 * 'RPMXCON-51335' Sprint : 8
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 30)
	public void verifyMiniDoclistWebFieldAfterChangingThem(String fullName, String userName, String password)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51335");

		baseClass.stepInfo(
				"Verify on click of 'Save Configuration' mini doc list should be displayed with the selected webfields for Set Document Sorting");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		MiniDocListPage miniDocListPage = new MiniDocListPage(driver);
		String fieldName = "SourceDocID";

		baseClass.stepInfo("Step 2: Search for documents and go to doc view");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				"Step 3: Drag the fields from available fields to selected fields and click 'Save Configuration'");
		miniDocListPage.verifyDefaultValueInOptimizedSort();
		miniDocListPage.selectSourceDocIdInAvailableField();

		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(miniDocListPage.getDocView_MiniDoclist_Header_Webfields(fieldName));
		softAssertion.assertTrue(miniDocListPage.getDocView_MiniDoclist_Header_Webfields(fieldName).isDisplayed());
		
		baseClass.passedStep("Mini doc list is displayed with the selected fields for Set Document Sorting");
		softAssertion.assertAll();
		loginPage.logout();
		

	}

	/**
	 * @author Raghuram A Date: 1/4/21 Modified date:N/A Modified by: N/A
	 *         Description : Verify application loads the Doc ID when clicking the
	 *         document from the history which is present in the mini doc
	 *         list-RPMXCON-51297 Sprint 09
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 31)
	public void verifyDocIdInDocVIewPanel(String fullName, String userName, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		List<String> docIDlist = new ArrayList<>();
		int docLimit = 3;

		baseClass.stepInfo("Test case Id: RPMXCON-51297 - DocView/MiniDocList Sprint 09");
		baseClass.stepInfo(
				"Verify application loads the Doc ID when clicking the document from the history which is present in the mini doc list");

		// Login As user
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + fullName + "'");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("Select Docview At Action");
		sessionSearch.ViewInDocView();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		int sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		// validate checkmark icon and code same as last button
		miniDocListpage.actionOnDocsFromHistoryDropDown(docLimit, sizeofList, docIDlist, true, "", true, false, false,
				null);

		loginPage.logout();

	}

	/**
	 * @author Raghuram A Date: 1/4/21 Modified date:N/A Modified by: N/A
	 *         Description : In DocView's Mini DocList panel, the prior document
	 *         viewed history dropdown should work on click of "clock
	 *         icon"-RPMXCON-51295 Sprint 09
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 32)
	public void verifypriorDocumentViewedHistory(String fullName, String userName, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		List<String> docIDlist = new ArrayList<>();
		int docLimit = 3;

		baseClass.stepInfo("Test case Id: RPMXCON-51295 - DocView/MiniDocList Sprint 09");
		baseClass.stepInfo(
				"In DocView's Mini DocList panel, the prior document viewed history dropdown should work on click of \"clock icon\"");

		// Login As User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + fullName + "'");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("Select Docview At Action");
		sessionSearch.ViewInDocView();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		int sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		// validate checkmark icon and code same as last button
		miniDocListpage.actionOnDocsFromHistoryDropDown(docLimit, sizeofList, docIDlist, true, "", false, true, false,
				null);

		loginPage.logout();

	}

	/**
	 * @Author jeevitha
	 * @Description : Verify the 'Available Fields' for 'Family Relationship' field
	 *              from Manual Sort Order > Pick Column Display and Set Document
	 *              Sorting tab of configure mini doc list pop up [RPMXCON-51654]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 33)
	public void verifyAvailableFields(String fullName, String username, String password) throws InterruptedException {
		String fieldValue = "FamilyRelationship";

		// Login as a User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-51654");
		baseClass.stepInfo(
				"Verify the 'Available Fields' for 'Family Relationship' field from Manual Sort Order > Pick Column Display and Set Document Sorting tab of configure mini doc list pop up");

		// basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		miniDocListpage.clickManualSortButton();

		// verify fields in manual sort available Tab
		miniDocListpage.verifyWhetherFieldAvailableInTab(fieldValue, true);
		miniDocListpage.getDocview_MiniDoc_GearIconCancelBtn().waitAndClick(10);
		loginPage.logout();

	}

	@DataProvider(name = "ContentAndAudio")
	public Object[][] ContentAndAudio() {
		Object[][] ContentAndAudio = { { "Basic" }, { "Audio" }, };
		return ContentAndAudio;
	}

	/**
	 * @Author Jeevitha
	 * @Description : DocView loading next('>') document from mini doc list
	 *              [RPMXCON-51656]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "ContentAndAudio", groups = { "regression" }, priority = 55)
	public void docviewLoadingNext(String method) throws InterruptedException {

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-51656 DocView/miniDoclist Sprint 09");
		baseClass.stepInfo("DocView loading next('>') document from mini doc list");

		// Basic Search
		if (method.equals("Basic")) {
			sessionSearch.basicContentSearch(Input.searchString2);
		} else if (method.equals("Audio")) {
			sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		}
		sessionSearch.ViewInDocView();

		docViewPage.verifyUserNavigatedToNextDocument();
		String docID = docViewPage.getDocView_CurrentDocId().getText().trim();
		miniDocListpage.verifySelectedDocHighlight(docID);

	}

	/**
	 * @Author Jeevitha
	 * @Description : DocView loading next('>') document from mini doc list with
	 *              scroll when document is selected when Loading displays in mini
	 *              doc list [RPMXCON-51657]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "ContentAndAudio", groups = { "regression" }, priority = 56)
	public void docviewLoadingNextAfterScroll(String method) throws InterruptedException {
		List<String> docIDlist = null;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-51657 DocView/miniDoclist Sprint 09");
		baseClass.stepInfo(
				"DocView loading next('>') document from mini doc list with scroll when document is selected when Loading displays in mini doc list");

		// Basic Search
		if (method.equals("Basic")) {
			sessionSearch.basicContentSearch(Input.searchString2);
		} else if (method.equals("Audio")) {
			sessionSearch.audioSearch(Input.audioSearchString1, "International English");
		}

		sessionSearch.ViewInDocView();

		docViewPage.scrollingDocumentInMiniDocList();
		driver.waitForPageToBeReady();

		miniDocListpage.actionOnDocsFromHistoryDropDown(1, 0, docIDlist, false, "", true, false, false, null);
		String docID = docViewPage.performNextFuncn();
		miniDocListpage.verifySelectedDocHighlight(docID);

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param fullName
	 * @param userName
	 * @param password
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "twoLogins", groups = { "regression" }, priority = 57)
	public void savedSearchAndBasicAndDocListToDocView(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50888");
		baseClass.stepInfo("To verify that if user navigates to doc view from the Basic search/Saved search/DocList, "
				+ "Optimized mode from mini doc list should be selected");
		String savedSearchs = "AsavedToDocview" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(userName, password);

		baseClass.stepInfo("Successfully login as '" + fullName);
		// basic search to doc view
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedSearchs);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User navigated to docview page from basic search page");
		miniDocListpage.verifyOptimizedSortIsSelected(); // validation part

		sessionSearch.ViewInDocList();
		baseClass.stepInfo("User navigated to doclist page from basic search page");
		DocListPage docListPage = new DocListPage(driver);
		// Selecting document in doclist page and navigate to doc view
		docListPage.selectAllDocumentsInCurrentPageOnly();
		docListPage.docListToDocView();
		baseClass.stepInfo("User navigated to docview page from DocList page");
		miniDocListpage.verifyOptimizedSortIsSelected(); // validation part

		savedSearch.savedSearch_Searchandclick(savedSearchs);
		savedSearch.getToDocView().waitAndClick(5);
		baseClass.stepInfo("User navigated to docview page from SavedSearch page");
		miniDocListpage.verifyOptimizedSortIsSelected(); // validation part
		
	}

	/**
	 * Author : Raghuram A date: 01/17/21 Modified date:N/A Modified by: N/A
	 * Description : To verify that selected document with details is displayed on
	 * parent window.
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 58)
	public void verifySelectedDocsInParentWindow(String fullName, String userName, String password) throws Exception {

		DocViewMetaDataPage dovViewMeteData = new DocViewMetaDataPage(driver);

		List<String> headerList1 = new ArrayList<>();
		List<String> headerList2 = new ArrayList<>();

		baseClass.stepInfo("Test case Id: RPMXCON-50897");
		baseClass.stepInfo("To verify that selected document with details is displayed on parent window.");

		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as : " + fullName);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab());
		docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		driver.waitForPageToBeReady();
		headerList1 = docViewPage.listOfAvailableDatasToCheck(headerList1, headerList2, false);

		// Launching Child Window
		miniDocListpage.launchingMindoclistChildWindow();

		// Main method
		String childWindoSelectedDocId = miniDocListpage.selectDocFromChildWindow();
		driver.waitForPageToBeReady();
		String currentDOcID = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("main Window doc id : " + currentDOcID);

		baseClass.textCompareEquals(currentDOcID, childWindoSelectedDocId,
				"On changing the document from child window, parent window displays the selected document in document view panel",
				"Document Id Mimatches");

		driver.waitForPageToBeReady();
		//driver.scrollingToBottomofAPage();
		baseClass.ValidateElement_Presence(dovViewMeteData.getParentDocID(currentDOcID),
				"ParentDocID : " + currentDOcID);

		//driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab());
		docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		driver.waitForPageToBeReady();
		docViewPage.listOfAvailableDatasToCheck(headerList2, headerList1, true);
		
		loginPage.logout();

	}
	/**
	 * @author Jayanthi.ganesan
	 * @param fullName
	 * @param userName
	 * @param password
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 59)
	public void BasicSearchToDocView_PanelVerify(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50811");
		baseClass.stepInfo("To verify Mini DocList Panel from doc view page for user when redirects from"
				+ " Basic Search > View all in doc view");
		String expectedURL=Input.url+"DocumentViewer/DocView";
		loginPage.loginToSightLine(userName, password);

		baseClass.stepInfo("Successfully login as '" + fullName);
		// basic search to doc view
		sessionSearch.basicContentSearch(Input.testData1);
		hitsCount=sessionSearch.verifyPureHitsCount();
		sessionSearch.ViewInDocView();
	    driver.waitForPageToBeReady();
		if(driver.getUrl().equals(expectedURL)) {
			baseClass.passedStep("User navigated to docview page from Basic search page");
			List<String> listOfData= new ArrayList<>();
			listOfData=reusableDocViewPage.miniDocList();
			List<String> listOfDataAfterSort=  new ArrayList<>();
			listOfDataAfterSort=reusableDocViewPage.miniDocList(); 
			Collections.sort(listOfDataAfterSort);
			baseClass.stepInfo("Pure Hits Count--"+hitsCount);
			baseClass.stepInfo("Docs Count in DocView Page --"+listOfData.size());
			softAssertion.assertEquals(listOfData,listOfDataAfterSort);
			softAssertion.assertEquals(listOfData.size(),Integer.parseInt(hitsCount));
			softAssertion.assertAll();
			baseClass.passedStep("Documents  listed as per the saved into the search and documents  sorted as per the document ID  ");
		}
		else{
			baseClass.failedStep("Application not redirected to the  doc view page ");
		}
	
		loginPage.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 * @param fullName
	 * @param userName
	 * @param password
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 60)
	public void savedSearchToDocView_PanelVerify(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50809");
		baseClass.stepInfo("To verify Mini DocList Panel from doc view page for user when redirects from saved search");
		
		String expectedURL=Input.url+"DocumentViewer/DocView";
		loginPage.loginToSightLine(userName, password);

		baseClass.stepInfo("Successfully login as '" + fullName);
		if(userName==Input.pa1userName) {
		// saved search search to doc view
		sessionSearch.basicContentSearch(Input.TallySearch);
		hitsCount=sessionSearch.verifyPureHitsCount();
		sessionSearch.saveSearchAtAnyRootGroup(savedSearchs, Input.shareSearchDefaultSG);
		}
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		savedSearch.savedSearch_SearchandSelect(savedSearchs, "Yes");
		savedSearch.getToDocView().waitAndClick(5);
		driver.waitForPageToBeReady();
		if(driver.getUrl().equals(expectedURL)) {
			baseClass.passedStep("User navigated to docview page from SavedSearch  page");
			List<String> listOfData= new ArrayList<>();
			listOfData=reusableDocViewPage.miniDocList();
			List<String> listOfDataAfterSort=  new ArrayList<>();
			listOfDataAfterSort=reusableDocViewPage.miniDocList(); 
			Collections.sort(listOfDataAfterSort);
			baseClass.stepInfo("Pure Hits Count--"+hitsCount);
			baseClass.stepInfo("Docs Count in DocView Page --"+listOfData.size());
			softAssertion.assertEquals(listOfData,listOfDataAfterSort);
			softAssertion.assertEquals(listOfData.size(),Integer.parseInt(hitsCount));
			softAssertion.assertAll();
			baseClass.passedStep("Documents  listed as per the saved into the search and documents  sorted as per the document ID  ");
		}
		else{
			baseClass.failedStep("Application not redirected to the  doc view page ");
		}
	
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date: 27/01/2022 Modified date: NA Modified by: Baskar
	 * @Description : To verify user can select Multiple documents in Mini Doc List
	 *              from dockout screens and Select Action as 'Folder'
	 */

	@Test(enabled = true, dataProvider = "paRmuRole", groups = { "regression" }, priority = 61)
	public void validateFolderFromMiniDocList(String fullName, String userName, String password)
			throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51132");
		baseClass.stepInfo("To verify user can select Multiple documents in Mini Doc List "
				+ "from dockout screens and Select Action as 'Folder'");

		String folderName = "AFolder" + Utility.dynamicNameAppender();

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		// Session search to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// validate Folder action
		docViewPage.performFolderAction(folderName, 2);

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 27/01/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify scrolling in mini doc list should work when user
	 *                     completes the document from mini doc list
	 */
	@Test(enabled = true, dataProvider = "twoLogins", groups = { "regression" }, priority = 62)
	public void validateScrollAndLastDocsCursor(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51088");
		baseClass.stepInfo("Verify scrolling in mini doc list should "
				+ "work when user completes the document from mini doc list");
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assgn, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assgn);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assgn);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validate last docs navigtion in assignment
		docViewPage.verifyThatIsLastDoc();
		String lastDocs = docViewPage.getVerifyPrincipalDocument().getText();
		baseClass.stepInfo("Completing last docs");
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		String lastDocsAfterComplete = docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertEquals(lastDocs, lastDocsAfterComplete);
		softAssertion.assertAll();
		baseClass.passedStep("Cursor still in the last document after completed the document");

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 27/01/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify scrolling in mini doc list should work when user enters
	 *                     document ID in document navigation
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 63)
	public void validateScrollIsScrollableWhenDocIdEntered(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51087");
		baseClass.stepInfo("Verify scrolling in mini doc list should work when "
				+ "user enters document ID in document navigation");

		// login as
		loginPage.loginToSightLine(userName, password);

		// Session search to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// validate using docid and scroll bar
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(docViewPage.getDocumetCountMiniDocList());
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();

		// Before scroll position
		Long value = (Long) jse.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		System.out.println(value);

		// enter document number
		docViewPage.getDocView_NumTextBox().SendKeys("20" + Keys.ENTER);
		baseClass.stepInfo("Entering the document number in docview panel");
		driver.waitForPageToBeReady();

		// After scroll position
		Long value1 = (Long) jse.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		System.out.println(value1);
		softAssertion.assertNotEquals(value, value1);
		softAssertion.assertAll();
		baseClass.stepInfo("Scroll bar is scrollable");
		baseClass.passedStep("Scrolling working in minidoclist when document number entered");
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 27/01/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify scrolling in mini doc list should work when user clicks
	 *                     to go to first, last , prev and next document from
	 *                     document navigation
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 64)
	public void validateNavigationOptions(String fullName, String userName, String password)
			throws InterruptedException,AWTException{
		baseClass.stepInfo("Test case Id: RPMXCON-51086");
		baseClass.stepInfo("Verify scrolling in mini doc list should work when user clicks "
				+ "to go to first, last , prev and next document from document navigation");

		// login as
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Session search to docview
		int size = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// validate using docid and scroll bar
		driver.waitForPageToBeReady();
		docViewPage.navigationOptionValidation(size);

		// logout
		loginPage.logout();

	}


	/**
	 * @Author : Baskar date: 28/01/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can scroll down the mini doc list to see the
	 *                     additional documents
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 65)
	public void validateScrollBarIsScrollable(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51085");
		baseClass.stepInfo("Verify user can scroll down the mini doc " + "list to see the additional documents");

		// login as
		loginPage.loginToSightLine(userName, password);

		// Session search to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// validate using docid and scroll bar
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(docViewPage.getDocumetCountMiniDocList());
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();

		// Before scroll position
		Long value = (Long) jse.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		System.out.println(value);

		// scrolling docs till end
		docViewPage.scrollingDocumentInMiniDocList();

		// After scroll position
		Long value1 = (Long) jse.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		System.out.println(value1);
		softAssertion.assertNotEquals(value, value1);
		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:28/01/2022 Modified by: Baskar
	 * Description : To verify fields from optimized/manual mode when with/without
	 * project fields mapping to a security group
	 */

	@Test(enabled = true, dataProvider = "sixRole", groups = { "regression" }, priority = 66)
	public void validateProjectFieldFromMiniDocList(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50958");
		baseClass.stepInfo("To verify fields from optimized/manual mode when "
				+ "with/without project fields mapping to a security group");
		UtilityLog.info("Started Execution for prerequisite");

		loginPage.loginToSightLine(userName, password);
		// project field created but not mapped
		if (fullName.equalsIgnoreCase("pa")) {
			// Custom Field created with INT DataType
			projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
			baseClass.stepInfo("Project field created with INT datatype");
			baseClass.stepInfo("Project field not mapped to SG");
		}
		// Mapping project field to SG
		if (fullName.equalsIgnoreCase("pa1")) {
			// Custom Field Assign to SecurityGroup
			securityGroupPage.addProjectFieldtoSG(projectFieldINT);
			System.out.println(projectFieldINT);
			baseClass.stepInfo("Project field assign to security group");

		}
		// validation in minidoclist popup window
		if (fullName.equalsIgnoreCase("rmu") || fullName.equalsIgnoreCase("rev") || fullName.equalsIgnoreCase("rmu1")
				|| fullName.equalsIgnoreCase("rev1")) {
			// Session search to doc view Coding Form
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.ViewInDocView();
		}
		// Without mapping validation
		if (fullName.equalsIgnoreCase("rmu") || fullName.equalsIgnoreCase("rev")) {
			driver.waitForPageToBeReady();
			baseClass.waitForElement(miniDocListpage.getGearIcon());
			miniDocListpage.getGearIcon().waitAndClick(5);
			baseClass.stepInfo("Minidoclist popup window opened");
			baseClass.stepInfo("Optimized sort order button is selected");
			// Without mapping validation in optimized mode
			if (fullName.equalsIgnoreCase("rmu") || fullName.equalsIgnoreCase("rev")) {
				if (miniDocListpage.getAvailableFieldsDisplay(projectFieldINT).isElementAvailable(3)) {
					boolean flagOptimize = miniDocListpage.getAvailableFieldsDisplay(projectFieldINT).isDisplayed();
					softAssertion.assertFalse(flagOptimize);	
				}
			}
			// With mapping validation in optimized mode
			else if (fullName.equalsIgnoreCase("rmu1") || fullName.equalsIgnoreCase("rev1")) {
				if (miniDocListpage.getAvailableFieldsDisplay(projectFieldINT).isElementAvailable(3)) {
					boolean flagOptimize = miniDocListpage.getAvailableFieldsDisplay(projectFieldINT).isDisplayed();
					softAssertion.assertTrue(flagOptimize);	
				}
			}
			baseClass.waitForElement(miniDocListpage.getManualSortRadioButton());
			miniDocListpage.getManualSortRadioButton().waitAndClick(5);
			baseClass.stepInfo("Manual sort order button opened");
			// Without mapping validation in Manual mode
			if (fullName.equalsIgnoreCase("rmu") || fullName.equalsIgnoreCase("rev")) {
				if (miniDocListpage.getAvailableFieldsDisplay(projectFieldINT).isElementAvailable(3)) {
				boolean flagManual = miniDocListpage.getAvailableFieldsDisplay(projectFieldINT).isDisplayed();
				softAssertion.assertFalse(flagManual);
			}
		}
			// With mapping validation in Manual mode
			else if (fullName.equalsIgnoreCase("rmu1") || fullName.equalsIgnoreCase("rev1")) {
				if (miniDocListpage.getAvailableFieldsDisplay(projectFieldINT).isElementAvailable(3)) {
				boolean flagManual = miniDocListpage.getAvailableFieldsDisplay(projectFieldINT).isDisplayed();
				softAssertion.assertTrue(flagManual);
			}
		}
	}
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 31/01/2022 Modified date: NA Modified by: Baskar
	 * @Description : To verify that user can 'popout panels' on Doc View, if
	 *              preferences is set as 'Enabled' from the assignment.
	 */

	@Test(enabled = true, dataProvider = "twoLogins", groups = { "regression" }, priority = 67)
	public void validatePopOutPanelAtAssgnLevel(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50840");
		baseClass.stepInfo("To verify that user can 'popout panels' on Doc View, "
				+ "if preferences is set as 'Enabled' from the assignment.");

		String rmu = "RMU";
		String miniDocListChild = Input.url + "DocumentViewer/DocViewChild";
		String analyticalChild = Input.url + "DocumentViewer/AnalyticsChildWindow";
		String metaDataChild = Input.url + "DocumentViewer/MetaDataChildWindow";

//      Login As
		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assgnWindow, Input.codingFormName);
			assignmentPage.checkForToggleEnable(assignmentPage.getAssgn_PopOutPanelToggle());
			driver.scrollPageToTop();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assgnWindow);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assgnWindow);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// opening all child window
		driver.waitForPageToBeReady();
//		MiniDoclist child window
		baseClass.waitForElement(docViewPage.getDocView_EditMode());
		docViewPage.getDocView_EditMode().waitAndClick(10);
		docViewPage.getDocView_HdrMinDoc().WaitUntilPresent().ScrollTo();
		baseClass.waitForElement(docViewPage.getDocView_HdrMinDoc());
		docViewPage.getDocView_HdrMinDoc().waitAndClick(5);
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String miniDocListUrl = driver.getUrl();
		softAssertion.assertEquals(miniDocListChild, miniDocListUrl);
		if (driver.getUrl().equalsIgnoreCase(miniDocListChild)) {
			baseClass.passedStep("User can able to popup out the minidcolist child window");
			docViewPage.closeWindow(1);
		}
		docViewPage.switchToNewWindow(1);
//		Analytical child window
		docViewPage.getDocView_HdrAnalytics().WaitUntilPresent().ScrollTo();
		baseClass.waitForElement(docViewPage.getDocView_HdrAnalytics());
		docViewPage.getDocView_HdrAnalytics().waitAndClick(5);
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String analyticalUrl = driver.getUrl();
		softAssertion.assertEquals(analyticalChild, analyticalUrl);
		if (driver.getUrl().equalsIgnoreCase(analyticalChild)) {
			baseClass.passedStep("User can able to popup out the analytical child window");
			docViewPage.closeWindow(1);
		}
		docViewPage.switchToNewWindow(1);
//		Metadata child window
		docViewPage.getDocView_HdrMetaData().WaitUntilPresent().ScrollTo();
		baseClass.waitForElement(docViewPage.getDocView_HdrMetaData());
		docViewPage.getDocView_HdrMetaData().waitAndClick(5);
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String metaDataUrl = driver.getUrl();
		softAssertion.assertEquals(metaDataChild, metaDataUrl);
		if (driver.getUrl().equalsIgnoreCase(metaDataChild)) {
			baseClass.passedStep("User can able to popup out the analytical child window");
			docViewPage.closeWindow(1);
		}
		docViewPage.switchToNewWindow(1);
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 31/01/2022 Modified date: NA Modified by: Baskar
	 * @Description : To verify user can see the document one by one in doc view by
	 *              selecting document from mini doc list
	 */

	@Test(enabled = true, dataProvider = "twoLogins", groups = { "regression" }, priority = 68)
	public void validateUserViewingOneByOne(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50835");
		baseClass.stepInfo("To verify user can see the document one by one "
				+ "in doc view by selecting document from mini doc list");

		String rmu = "RMU";

//      Login As
		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assgnOne, Input.codingFormName);
			assignmentPage.checkForToggleEnable(assignmentPage.getAssgn_PopOutPanelToggle());
			driver.scrollPageToTop();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assgnOne);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assgnOne);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// validating user viewing one by one docs
		List<String> addData = new LinkedList<String>();
		List<String> docviewPanelDocId = new LinkedList<String>();
		for (int i = 1; i <= 4; i++) {
			docViewPage.getMiniDocList_IterationDocs(i).waitAndClick(5);
			driver.waitForPageToBeReady();
			String activeDocId = docViewPage.getDocView_CurrentDocId().getText();
			docviewPanelDocId.add(activeDocId);
		}
		for (String docId : docviewPanelDocId) {
			docViewPage.getDociD(docId).waitAndClick(5);
			driver.waitForPageToBeReady();
			String pnDocs = docViewPage.getVerifyPrincipalDocument().getText();
			addData.add(pnDocs);
			softAssertion.assertEquals(pnDocs, docId);
		}
		softAssertion.assertEquals(addData, docviewPanelDocId);
		softAssertion.assertAll();
		baseClass.passedStep("User can able to see the document one by one in docview panel");
		
		// logout
		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
//			loginPage.logoutWithoutAssert();
		}
	
			loginPage.quitBrowser();
		
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV & DOCVIEW/REDACTIONS EXECUTED******");
		try {
//			loginPage.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}

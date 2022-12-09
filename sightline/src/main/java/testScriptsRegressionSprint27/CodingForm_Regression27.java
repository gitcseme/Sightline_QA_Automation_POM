package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingForm_Regression27 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	AssignmentsPage assignPage;
	CodingForm codingForm;
	SoftAssert softAssert;

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
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		codingForm = new CodingForm(driver);

	}
	
	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-64668 
	 * Description :To check that when user clicks on "Set Security Group Coding form" button the 
	 * "Add/remove coding form in this security group" pop-up should get displayed.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64668", enabled = true, groups = { "regression" })
	public void verifyAddOrRemoveCodingFormPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-64668");
		baseClass.stepInfo("Verify that add/remove coding form popup displayed.");
		// Login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "navigated to manage CF page",
				"not in manage CF page");
		baseClass.stepInfo("verify button present in manage coding form page");
		codingForm.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		codingForm.verifyAddOrRemoveCFpopup();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-64669 
	 * Description:To check that when user clicks on "X" icon from the "Add/remove coding form in 
	 * this security group" pop-up the PopUp should get removed.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64669", enabled = true, groups = { "regression" })
	public void verifyXIconFromCodingFormPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-64669");
		baseClass.stepInfo("Verify the functionality of 'X' icon from coding form popup.");
		// Login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "navigated to manage CF page",
				"not in manage CF page");
		baseClass.stepInfo("verify button present in manage coding form page");
		codingForm.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		codingForm.verifyAddOrRemoveCFpopup();
		baseClass.stepInfo("verify functionality of 'X' icon");
		codingForm.verifyButtonsFunctionalityInAddCFPopup("X");
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-64670 
	 * Description:To check that when user clicks on "Cancel" button from the "Add/remove coding form 
	 * in this security group" pop-up the PopUp should get cancelled.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64670", enabled = true, groups = { "regression" })
	public void verifyCancelButtonFromCodingFormPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-64670");
		baseClass.stepInfo("Verify the functionality of cancel button from coding form popup.");
		// Login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "navigated to manage CF page",
				"not in manage CF page");
		baseClass.stepInfo("verify button present in manage coding form page");
		codingForm.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		codingForm.verifyAddOrRemoveCFpopup();
		baseClass.stepInfo("verify functionality of 'Cancel' icon");
		codingForm.verifyButtonsFunctionalityInAddCFPopup("Cancel");
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-64667 
	 * Description:To check  that "Set Security Group Coding form" button is present in 
	 * "Manage Coding Form" page and its enable.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64667", enabled = true, groups = { "regression" })
	public void verifySetSecurityGroupCodingFormButton() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-64667");
		baseClass.stepInfo("Verify that 'Set Security Group Coding form' button is present");
		// Login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "navigated to manage CF page",
				"not in manage CF page");
		baseClass.stepInfo("verify button present in manage coding form page");
		codingForm.verifyButtonPresentInManageCFPage();
		baseClass.passedStep("'Set Security Group Coding form' button is present and enabled");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 09/12/2022 TestCase Id:RPMXCON-65463 
	 * Description:Verify that we have a note present with instructional text in 
	 * "Add/remove coding form in this security group" pop-up page (UI).
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-65463", enabled = true, groups = { "regression" })
	public void verifyInstructionalTextInPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-65463");
		baseClass.stepInfo("Verify instructional text present in add/remove cf popup");
		String expectedNote = "You can configure up to 15 coding forms for a security group.";
		
		// Login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "navigated to manage CF page",
				"not in manage CF page");
		baseClass.stepInfo("verify button present in manage coding form page");
		codingForm.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		codingForm.verifyAddOrRemoveCFpopup();
		baseClass.stepInfo("verify note present in popup");
		if(codingForm.getCodingFormNote().isElementAvailable(10)) {
			baseClass.passedStep("Instructional note present in the add/remove coding form popup");
			String actual = codingForm.getCodingFormNote().getText();
			baseClass.stepInfo("Note present in popup-"+actual);
			baseClass.compareTextViaContains(actual, expectedNote, "Expected info present in the instruction note", 
					"expected note not present in the instruction");
		}
		else {
			baseClass.failedStep("Instruction note not present in the add/remove coding form popup");
		}
		//closing the popup and log out
		baseClass.waitForElement(codingForm.getCfPopUpCancel());
		codingForm.getCfPopUpCancel().waitAndClick(10);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 09/12/2022 TestCase Id:RPMXCON-64671 
	 * Description:To check that "Add/remove coding form in this security group" coding form pop-up 
	 * should have two column table "CODING FORM NAME" and "SET AS DEFAULT (REQUIRED)" with 
	 * check box and radio button(UI).
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64671", enabled = true, groups = { "regression" })
	public void verifyColumnTableInPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-64671");
		baseClass.stepInfo("Verify elements and column present in add/remove CF popup table");
		
		// Login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "navigated to manage CF page",
				"not in manage CF page");
		baseClass.stepInfo("verify button present in manage coding form page");
		codingForm.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		codingForm.verifyAddOrRemoveCFpopup();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify column table present with checkbox and radio button");
		codingForm.verifyElementsPresentInAddCFPopup();
		//closing the popup and log out
		baseClass.waitForElement(codingForm.getCfPopUpCancel());
		codingForm.getCfPopUpCancel().waitAndClick(10);
		loginPage.logout();
	}
	
	
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");

	}
}

package testScriptsRegressionSprint25;

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

public class CodingForm_Regression25 {

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
	 * Author :Arunkumar date: 11/11/2022 TestCase Id:RPMXCON-65186 Description
	 * :Verify that proper message is displayed in "security group form" tool tip
	 * present in "Manage Coding Forms" page
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-65186", enabled = true, groups = { "regression" })
	public void verifyToolTipInManageCFPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-65186");
		baseClass.stepInfo("To verify tool tip present in manage coding form page");
		// Login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "navigated to manage CF page",
				"not in manage CF page");
		baseClass.stepInfo("Click on security group form icon and verify tooltip message");
		codingForm.validateSGFormHelpIconAndMessage();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 11/11/2022 TestCase Id:RPMXCON-65185 Description
	 * :Verify that Check box is present at "CODING FORM NAME" column.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-65185", enabled = true, groups = { "regression" })
	public void verifyCheckboxPresentInCFColumn() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-65185");
		baseClass.stepInfo("Verify that Check box is present at 'CODING FORM NAME' column.");
		// Login as PA
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
		baseClass.stepInfo("verify checkbox present in column");
		baseClass.waitForElement(codingForm.getPopUpCheckBox());
		baseClass.ValidateElement_Presence(codingForm.getPopUpCheckBox(), "Coding form name checkbox");
		baseClass.passedStep("checkbox present at coding form name column");
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 14/11/2022 TestCase Id:RPMXCON-64908 Description
	 * :Verify that Help text icon is present at " SET AS DEFAULT (REQUIRED) "
	 * column present in "Add/remove coding form in this security group" pop-up page
	 * (UI).
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64908", enabled = true, groups = { "regression" })
	public void verifyHelpTextIconPresentInPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-64908");
		baseClass.stepInfo("Verify that Help text icon is present in add coding form popup");
		// Login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.stepInfo("verify button present in manage coding form page");
		codingForm.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		codingForm.verifyAddOrRemoveCFpopup();
		baseClass.waitForElement(codingForm.getSetAsDefaultHelpIcon());
		if (codingForm.getSetAsDefaultHelpIcon().isDisplayed()) {
			baseClass.passedStep("help text icon for 'set as default' option displayed");
		} else {
			baseClass.failedStep("help text icon for 'set as default' not displayed");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 14/11/2022 TestCase Id:RPMXCON-64909 Description
	 * :Verify that when user click on the "SET AS DEFAULT" Help text icon required
	 * text information should get displayed in pop-up.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64909", enabled = true, groups = { "regression" })
	public void verifyTextInformationDisplayedInPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-64909");
		baseClass.stepInfo("verify 'SET AS DEFAULT' Help text icon information displayed in popup");
		// Login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Navigate to manage-coding form");
		codingForm.navigateToCodingFormPage();
		baseClass.stepInfo("verify button present in manage coding form page");
		codingForm.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		codingForm.verifyAddOrRemoveCFpopup();
		baseClass.stepInfo("verify help icon message displayed in tool tip popup");
		codingForm.verifySetAsDefaultHelpIconMessage();
		loginPage.logout();
	}

	/**
	 * @author Date:NA ModifyDate:NA RPMXCON-64893
	 * @throws Exception
	 * @Description Verify that when none of the \"CODING FORM NAME\" is selected
	 *              \"Next Stage Sort Order\" button is disabled(UI)
	 */
	@Test(description = "RPMXCON-64893", enabled = true, groups = { "regression" })
	public void verifyCfSelectedNextButtonIsDisable() throws Exception {
		BaseClass base = new BaseClass(driver);

		base.stepInfo("Test case Id: RPMXCON-64893");
		base.stepInfo(
				"Verify that when none of the \"CODING FORM NAME\" is selected \"Next Stage Sort Order\" button is disabled(UI)");
		CodingForm cf = new CodingForm(driver);
		SoftAssert soft = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		soft.assertTrue(cf.getSetCodingFormToSG().isElementPresent());
		boolean showHide = cf.getShowHide().Enabled();
		soft.assertTrue(showHide);
		base.stepInfo("Set security group coding form and show/hide are present and ist in enable state");
		cf.getSetCodingFormToSG().waitAndClick(5);
		base.waitForElement(cf.getStep1CfPopUp());
		boolean flagPopup1 = cf.getStep1CfPopUp().isElementAvailable(2);
		base.stepInfo("Add / Remove Coding Forms in this Security Group popup is displayed successfully");
		soft.assertTrue(flagPopup1);
		int unCheck = cf.getCfUnChecBoxUsingSize().size();
		for (int i = 0; i < unCheck; i++) {
			List<WebElement> element = cf.getCfUnChecBoxUsingSize().FindWebElements();
			element.get(i).click();
		}
		base.waitForElement(cf.sortOrderNxtDisableBtn());
		if (cf.sortOrderNxtDisableBtn().isElementPresent()) {
			base.passedStep(
					"CODING FORM NAME is selected After Next Stage Sort Order button is disabled(UI) as expected");

		} else {
			base.failedStep("Next Stage Sort Order button is not disabled");
		}
		base.waitTillElemetToBeClickable(cf.getCfPopUpCancel());
		cf.getCfPopUpCancel().waitAndClick(10);
		soft.assertAll();

		loginPage.logout();
	}

	/**
	 * @author Date:NA ModifyDate:NA RPMXCON-64907
	 * @throws Exception
	 * @Description Verify that when user UnCheck on the "CODING FORM NAME" check
	 *              box from "Add/remove coding form in this security group" coding
	 *              form pop-up all the present coding form should get Unchecked
	 *              from the column.
	 */
	@Test(description = "RPMXCON-64907", enabled = true, groups = { "regression" })
	public void verifyCfPopUpAllSelectedFromColumn() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-64907");
		base.stepInfo(
				"Verify that when user UnCheck on the \"CODING FORM NAME\" check box from \"Add/remove coding form in this security group\" coding form pop-up all the present coding form should get Unchecked from the column.");
		CodingForm cf = new CodingForm(driver);
		SoftAssert soft = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.checkingBelow15CFCheckboxForSG();
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		soft.assertTrue(cf.getSetCodingFormToSG().isElementPresent());
		boolean showHide = cf.getShowHide().Enabled();
		soft.assertTrue(showHide);
		base.stepInfo("Set security group coding form and show/hide are present and ist in enable state");
		cf.getSetCodingFormToSG().waitAndClick(5);
		base.waitForElement(cf.getStep1CfPopUp());
		boolean flagPopup1 = cf.getStep1CfPopUp().isElementAvailable(2);
		base.stepInfo("Add / Remove Coding Forms in this Security Group popup is displayed successfully");
		soft.assertTrue(flagPopup1);
		base.waitForElement(cf.getPopUpCheckBox());
		cf.getPopUpCheckBox().waitAndClick(5);
		base.waitForElement(cf.getPopUpCheckBox());
		cf.getPopUpCheckBox().waitAndClick(5);
		driver.waitForPageToBeReady();
		int Check = cf.getCfChecBoxUsingSize().size();
		System.out.println(Check);
		for (int i = 0; i < Check; i++) {
			List<WebElement> element = cf.getCfChecBoxUsingSize().FindWebElements();
			element.get(i).click();
		}
		soft.assertTrue(cf.sortOrderNxtBtn().isElementPresent());
		base.passedStep("all the present coding form has been get selected As expected");
		int unCheck = cf.getCfChecBoxUsingSize().size();
		System.out.println(unCheck);
		for (int i = 0; i < unCheck; i++) {
			List<WebElement> element = cf.getCfChecBoxUsingSize().FindWebElements();
			element.get(i).click();
		}
		base.waitForElement(cf.sortOrderNxtDisableBtn());
		if (cf.sortOrderNxtDisableBtn().isElementPresent()) {
			base.passedStep(" All the present coding form has been get Unchecked from the column as expected.");

		} else {
			base.failedStep("Not unchecked from the column");
		}
		base.waitTillElemetToBeClickable(cf.getCfPopUpCancel());
		cf.getCfPopUpCancel().waitAndClick(10);
		soft.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Date:NA ModifyDate:NA RPMXCON-65188
	 * @throws Exception
	 * @Description Verify that when default is not set to selected Coding form
	 *              "Next Stage Sort Order" button is disabled(UI)
	 */
	@Test(description = "RPMXCON-65188", enabled = true, groups = { "regression" })
	public void verifyCfDefaultNotSelectedNextDisable() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-65188");
		base.stepInfo(
				"Verify that when default is not set to selected Coding form \"Next Stage Sort Order\" button is disabled(UI)");
		CodingForm cf = new CodingForm(driver);
		SoftAssert soft = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.checkingBelow15CFCheckboxForSG();
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		soft.assertTrue(cf.getSetCodingFormToSG().isElementPresent());
		boolean showHide = cf.getShowHide().Enabled();
		soft.assertTrue(showHide);
		base.stepInfo("Set security group coding form and show/hide are present and ist in enable state");
		cf.getSetCodingFormToSG().waitAndClick(5);
		base.waitForElement(cf.getStep1CfPopUp());
		boolean flagPopup1 = cf.getStep1CfPopUp().isElementAvailable(2);
		base.stepInfo("Add / Remove Coding Forms in this Security Group popup is displayed successfully");
		soft.assertTrue(flagPopup1);
		base.waitForElement(cf.getPopUpCheckBox());
		cf.getPopUpCheckBox().waitAndClick(5);
		base.waitForElement(cf.getPopUpCheckBox());
		cf.getPopUpCheckBox().waitAndClick(5);
		driver.waitForPageToBeReady();
		int Check = cf.getCfChecBoxUsingSize().size();
		System.out.println(Check);
		for (int i = 0; i < Check; i++) {
			List<WebElement> element = cf.getCfChecBoxUsingSize().FindWebElements();
			element.get(i).click();
		}
		base.passedStep("Selected less than 15 coding form  As expected");
		int unCheck = cf.getCfUnChecBoxUsingSize().size();
		System.out.println(unCheck);
		for (int i = 0; i < unCheck; i++) {
			List<WebElement> element = cf.getCfUnChecBoxUsingSize().FindWebElements();
			element.get(i).click();
		}
		base.stepInfo(" Default is not set to selected Coding form");
		base.waitForElement(cf.sortOrderNxtDisableBtn());
		if (cf.sortOrderNxtDisableBtn().isElementPresent()) {
			base.passedStep(" Next Stage Sort Order  button is disabled as expected.");

		} else {
			base.failedStep("button is not disabled");
		}
		base.waitTillElemetToBeClickable(cf.getCfPopUpCancel());
		cf.getCfPopUpCancel().waitAndClick(10);
		soft.assertAll();
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

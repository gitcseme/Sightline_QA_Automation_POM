package sightline.codingForms;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingForm_Regression_Phase2_2 {

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
	 * @author  Date:NA ModifyDate:NA RPMXCON-64674
	 * @throws Exception
	 * @Description To check that when user clicks on "Back" button from "Sort
	 *              Coding Form Order" Popup it should revert back to "Add/remove
	 *              coding form in this security group" Popup.
	 */
	@Test(description = "RPMXCON-64674", enabled = true, groups = { "regression" })
	public void verifyClickBackBtnSortCodingFromOrderItRevertBack() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-64674");
		base.stepInfo(
				"To check that when user clicks on \"Back\" button from \"Sort Coding Form Order\" Popup it should revert back to \"Add/remove coding form in this security group\" Popup..");
		CodingForm cf = new CodingForm(driver);
		SoftAssert soft = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify button present in manage coding form page");
		cf.verifyButtonPresentInManageCFPage();
		base.stepInfo(
				"Set Security Group Coding form button is present in Manage Coding Form page just before to Show/Hide column and its enable as expected");
		cf.verifyAddOrRemoveCFpopup();
		if (cf.checkDefaultCodingFormIsSelected().GetAttribute("checked") == null) {
			cf.getDefaultCodingFormInputBox().waitAndClick(5);
		}
		if (cf.checkDefaultCodingFormRadioBtnIsSelected().GetAttribute("checked") == null) {
			cf.getDefaultCodingFormRadioBtn().waitAndClick(5);
		}
		base.stepInfo("Select Default coding form as expected");
		base.waitForElement(cf.sortOrderNxtBtn());
		cf.sortOrderNxtBtn().waitAndClick(5);
		base.waitForElement(cf.getCodingFormOrder());
		boolean SortCodingFormOrder = cf.getCodingFormOrder().isElementAvailable(2);
		base.passedStep("Sort Coding Form Order page is displayed successfully");
		soft.assertTrue(SortCodingFormOrder);
		base.waitForElement(cf.getCodingForm_BackButton());
		cf.getCodingForm_BackButton().waitAndClick(5);
		base.stepInfo("Cf back button is clicked");
		driver.waitForPageToBeReady();
		base.waitForElement(cf.getStep1CfPopUp());
		boolean AddRemovePopUp = cf.getStep1CfPopUp().isElementAvailable(2);
		base.passedStep(
				"After Clicked back btn Revert back to \"Add/remove coding form in this security group\" Popup page is displayed successfully");
		soft.assertTrue(AddRemovePopUp);
		loginPage.logout();
	}

	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-65616
	 * @throws Exception
	 * @Description Verify that proper success message is displayed when user clicks
	 *              on "Save" button from "Step 02 : Sort Coding Form Order" page
	 *              present under "Manage Coding Forms".
	 */
	@Test(description = "RPMXCON-65616", enabled = true, groups = { "regression" })
	public void verifySuccessMsgIsDisplayedClickSaveBtnOnCf() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-65616");
		base.stepInfo(
				"Verify that proper success message is displayed when user clicks on \"Save\" button from \"Step 02 : Sort Coding Form Order\" page present under \"Manage Coding Forms\".");
		CodingForm cf = new CodingForm(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify button present in manage coding form page");
		cf.verifyButtonPresentInManageCFPage();
		base.stepInfo(
				"Set Security Group Coding form button is present in Manage Coding Form page just before to Show/Hide column and its enable as expected");
		driver.waitForPageToBeReady();
		cf.verifyAddOrRemoveCFpopup();
		if (cf.checkDefaultCodingFormIsSelected().GetAttribute("checked") == null) {
			cf.getDefaultCodingFormInputBox().waitAndClick(5);
		}
		if (cf.checkDefaultCodingFormRadioBtnIsSelected().GetAttribute("checked") == null) {
			cf.getDefaultCodingFormRadioBtn().waitAndClick(5);
		}
		base.stepInfo("Select Default coding form as expected");
		base.waitForElement(cf.sortOrderNxtBtn());
		cf.sortOrderNxtBtn().waitAndClick(5);
		base.stepInfo("Click on next button");
		cf.verifySelectedCodingFormSaved();
		loginPage.logout();
	}

	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-64676
	 * @throws Exception
	 * @Description To check that when user clicks on "X" icon from the "Sort Coding
	 *              Form Order" pop-up the PopUp should get removed.
	 */
	@Test(description = "RPMXCON-64676", enabled = true, groups = { "regression" })
	public void verifyClicksXIconCfOrderPopUpRemoved() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-64676");
		base.stepInfo(
				"To check that when user clicks on \"X\" icon from the \"Sort Coding Form Order\" pop-up the PopUp should get removed.");
		CodingForm cf = new CodingForm(driver);
		SoftAssert soft = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();
		String Cfname = cf.getCodingForm_Name(1).getText();
		System.out.println(Cfname);
		String SgCf = cf.getCodingFormSecurityGroupFormColumnValue(Cfname).getText();
		System.out.println(SgCf);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify button present in manage coding form page");
		cf.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		cf.verifyAddOrRemoveCFpopup();
		base.stepInfo(
				"Set Security Group Coding form button is present in Manage Coding Form page just before to Show/Hide column and its enable as expected");
		cf.verifyAddSelectedCodingFormOnDefault(Cfname);
		base.waitForElement(cf.sortOrderNxtBtn());
		cf.sortOrderNxtBtn().waitAndClick(5);
		if (cf.getCodingFormOrderXIcon().isElementAvailable(5)) {
			cf.getCodingFormOrderXIcon().waitAndClick(5);
			base.passedStep("Coding Form order X icon is present and Clicked as expected");

		} else {
			base.failedStep("Coding Form order X icon is not present");

		}
		base.passedStep("After Clicked X icon Popup page is Removed successfully");
		driver.waitForPageToBeReady();
		String Cfname1 = cf.getCodingForm_Name(1).getText();
		System.out.println(Cfname1);
		soft.assertEquals(Cfname, Cfname1);
		String SgCf1 = cf.getCodingFormSecurityGroupFormColumnValue(Cfname1).getText();
		System.out.println(SgCf1);
		soft.assertEquals(SgCf, SgCf1);
		base.stepInfo("Both the codingforms are same "+SgCf+":"+SgCf1);
		base.passedStep("No changes are done in SecurityGroup Form column in Manage CodingFormpage As expected");
		soft.assertAll();
		loginPage.logout();

	}

	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-64676
	 * @throws Exception
	 * @Description To check that when user clicks on "Save" button from "Sort
	 *              Coding Form Order" PopUp after selecting the required fields it
	 *              should get reflect to "Security Group Form" column in "Manage
	 *              Coding Form" page.
	 */
	@Test(description = "RPMXCON-64675", enabled = true, groups = { "regression" })
	public void verifyClicksSaveBtnCfOrderSelectingFieldGetReflect() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-64675");
		base.stepInfo(
				"To check that when user clicks on \"Save\" button from \"Sort Coding Form Order\" PopUp after selecting the required fields it should get reflect to \"Security Group Form\" column in \"Manage Coding Form\" page.");
		CodingForm cf = new CodingForm(driver);
		SoftAssert soft = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		String Cfname = cf.getCodingForm_Name(1).getText();
		baseClass.stepInfo("verify button present in manage coding form page");
		cf.verifyButtonPresentInManageCFPage();
		baseClass.stepInfo("click on set coding form button and validate popup");
		cf.verifyAddOrRemoveCFpopup();
		base.stepInfo(
				"Set Security Group Coding form button is present in Manage Coding Form page just before to Show/Hide column and its enable as expected");
		cf.verifyAddSelectedCodingFormOnDefault(Cfname);
		base.waitForElement(cf.sortOrderNxtBtn());
		cf.sortOrderNxtBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		cf.verifySelectedCodingFormSaved();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		String Cfname1 = cf.getCodingForm_Name(1).getText();
		System.out.println(Cfname1);
		soft.assertEquals(Cfname, Cfname1);
		String SgCf1 = cf.getCodingFormSecurityGroupFormColumnValue(Cfname1).getText();
		System.out.println(SgCf1);
		soft.assertEquals(SgCf1, "YES (Default)");
		base.passedStep("No changes are done in SecurityGroup Form column in Manage CodingFormpage As expected");
		soft.assertAll();
		cf.selectDefaultProjectCodingForm();
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
	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-64891
	 * @throws Exception
	 * @Description Verify that when user select more then 15 coding form and clicks
	 *              on "Next Stage Sort Order" button from "Add/remove coding form
	 *              in this security group" it should give Error message.
	 */
	@Test(description = "RPMXCON-64891", enabled = true, groups = { "regression" })
	public void verify15CfClicksOnNextBtnGiveErrorMsg() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-64891");
		base.stepInfo(
				"Verify that when user select more then 15 coding form and clicks on \"Next Stage Sort Order\" button from \"Add/remove coding form in this security group\" it should give Error message.");
		CodingForm cf = new CodingForm(driver);
		AssignmentsPage assgnpage = new AssignmentsPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		cf.verifyAddExpectedCf(16);
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		baseClass.stepInfo("verify button present in manage coding form page");
		cf.verifyButtonPresentInManageCFPage();
		base.stepInfo(
				"Set Security Group Coding form button is present in Manage Coding Form page just before to Show/Hide column and its enable as expected");
		cf.verifyAddOrRemoveCFpopup();
		base.waitForElement(cf.getPopUpCheckBox());
		cf.getPopUpCheckBox().waitAndClick(5);
		base.waitForElement(assgnpage.sortOrderNxtBtn());
		assgnpage.sortOrderNxtBtn().ScrollTo();
		base.waitForElement(assgnpage.sortOrderNxtBtn());
		assgnpage.sortOrderNxtBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		String errormsg = cf.getErrorMsgMore15CF().getText();
		if (cf.getErrorMsgMore15CF().isElementAvailable(5)) {
			base.passedStep(errormsg + "Error message is displayed add more than 15 coding form as expected");
		} else {
			base.failedStep("Error message is not displayed add more than 15 coding form ");

		}

	}

	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-64752
	 * @throws Exception
	 * @Description To check that the default set Coding form should always display
	 *              at the top row of "Step 02 : Sort Coding Form Order" pop-up
	 *              table followed by other coding form if selected any(UI)
	 */
	@Test(description = "RPMXCON-64752", enabled = true, groups = { "regression" })
	public void verifyDefaultSetCfDisplayAtTopRow() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-64752");
		base.stepInfo(
				"To check that the default set Coding form should always display at the top row of \"Step 02 : Sort Coding Form Order\" pop-up table followed by other coding form if selected any(UI)");
		CodingForm cf = new CodingForm(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		String Cfname = cf.getCodingForm_Name(1).getText();
		cf.verifyAddExpectedCf(10);
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		baseClass.stepInfo("verify button present in manage coding form page");
		cf.verifyButtonPresentInManageCFPage();
		base.stepInfo(
				"Set Security Group Coding form button is present in Manage Coding Form page just before to Show/Hide column and its enable as expected");
		cf.verifyAddOrRemoveCFpopup();
		cf.checkingBelow15CFCheckboxForSG();
		cf.verifyAddSelectedCodingFormOnDefault(Cfname);
		base.stepInfo("Select less then 16 Cf and make any one as default coding form as expected");
		baseClass.waitTime(1);
		cf.sortOrderNxtBtn().ScrollTo();
		cf.sortOrderNxtBtn().Click();
		base.waitTime(5);
		List<String> listOfCodingFormSelected = base.availableListofElements(cf.sortOrderHamBurger());
		base.stepInfo(listOfCodingFormSelected + " selected list Cf in codingformorder");
		String defaultname = listOfCodingFormSelected.get(0);
		System.out.println(defaultname);
		driver.waitForPageToBeReady();
		if (defaultname.contains("(Set as Default)")) {
			base.passedStep(
					defaultname + "Default set Coding form has been display at the top row Sort Coding Form Order"
							+ " pop-up table followed by other cf if selected any other than Default as expected.");

		} else {
			base.failedStep("default set Coding form has been not display at the top row Sort Coding Form Order");

		}
	}

	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-64911
	 * @throws Exception
	 * @Description Verify that in "Sort Coding Form Order" pop-up page each Coding
	 *              Forms are draggable(excluding Default Coding Form) to sort the
	 *              order of the coding form.
	 */
	@Test(description = "RPMXCON-64911", enabled = true, groups = { "regression" })
	public void verifSortCodingFormOrderPopUpEachDraggableCf() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-64911");
		base.stepInfo(
				"Verify that in \"Sort Coding Form Order\" pop-up page each Coding Forms are draggable(excluding Default Coding Form) to sort the order of the coding form.");
		CodingForm cf = new CodingForm(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		String Cfname = cf.getCodingForm_Name(1).getText();
		cf.verifyAddExpectedCf(14);
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		baseClass.stepInfo("verify button present in manage coding form page");
		cf.verifyButtonPresentInManageCFPage();
		base.stepInfo(
				"Set Security Group Coding form button is present in Manage Coding Form page just before to Show/Hide column and its enable as expected");
		cf.verifyAddOrRemoveCFpopup();
		cf.checkingBelow15CFCheckboxForSG();
		cf.verifyAddSelectedCodingFormOnDefault(Cfname);
		base.stepInfo("Select less then 16 Cf and make any one as default coding form as expected");
		baseClass.waitTime(1);
		cf.sortOrderNxtBtn().ScrollTo();
		cf.sortOrderNxtBtn().Click();
		base.waitTime(5);
		List<String> listOfCodingFormSelected = base.availableListofElements(cf.sortOrderHamBurger());
		base.stepInfo(listOfCodingFormSelected + " selected list Cf in codingformorder");
		String defaultname = listOfCodingFormSelected.get(0);
		System.out.println(defaultname);
		driver.waitForPageToBeReady();
		if (defaultname.contains("(Set as Default)")) {
			base.passedStep(
					defaultname + "Default set Coding form has been display at the top row Sort Coding Form Order"
							+ " pop-up table followed by other cf if selected any other than Default as expected.");

		} else {
			base.failedStep("default set Coding form has been not display at the top row Sort Coding Form Order");

		}
		cf.verifySelectedCfOrferIsDraggable(2, 5, 27);
		driver.waitForPageToBeReady();
		cf.verifySelectedCfOrferIsDraggable(3, 5, 27);
		base.passedStep(
				" Each Coding Forms are draggable  (excluding Default Coding Form) to sort the order of the coding form as expected");

	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that CF for Special Objects (Radio Group) saved with default action and field logic.
	 */
	@Test(description = "RPMXCON-54542",enabled = true, groups = { "regression" })
	public void verifydefaultActionAndFieldLogicValidation() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		CodingForm codingForm = new CodingForm(driver);
	    baseClass.stepInfo("Test case Id: RPMXCON-54542");
	    baseClass.stepInfo("Verify that CF for Special Objects (Radio Group) saved with default action and field logic.");
	    String actionName = "Make this Required";
	    String codingform = "CF"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		SoftAssert softAssertion = new SoftAssert();
		
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);	    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.required);
	    codingForm.enterErrorAndHelpMsg(4, "Yes", "Responsiveness", "You must select one of these options");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    //validations
	    codingForm.editCodingForm(codingform);		 
		 //validate
	    codingForm.clickPreviewButon();
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(5);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form working as expected");
		//delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that CF for Verify that default action and field logic (Special Objects - Radio Group) works in the context of a document review in Parent Window
	 */
	@Test(description = "RPMXCON-54543",enabled = true, groups = { "regression" })
	public void verifydefaultActionAndFieldLogicValidationInParentWindow() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		CodingForm codingForm = new CodingForm(driver);
	    baseClass.stepInfo("Test case Id: RPMXCON-54543");
	    baseClass.stepInfo("Verify that default action and field logic (Special Objects - Radio Group) works in the context of a document review in Parent Window");
	    String actionName = "Make this Required";
	    String codingform = "CF"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		SoftAssert softAssertion = new SoftAssert();
		SessionSearch sessionSearch = new SessionSearch(driver);	
		ReusableDocViewPage rd = new ReusableDocViewPage(driver);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);	    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.required);
	    codingForm.enterErrorAndHelpMsg(4, "Yes", "Responsiveness", "You must select one of these options");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    //validations
	    codingForm.assignCodingFormToSG(codingform);
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(25);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form is working in parent window as expected");
	    driver.Navigate().refresh();
	    baseClass.handleAlert();
	    codingForm.assignCodingFormToSG(Input.codeFormName);
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that default action and field logic (Special Objects - Radio Group) works in the context of a document review in Child Window.
	 */
	@Test(description = "RPMXCON-54544",enabled = true, groups = { "regression" })
	public void verifydefaultActionAndFieldLogicValidationInChildWindow() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		CodingForm codingForm = new CodingForm(driver);
	    baseClass.stepInfo("Test case Id: RPMXCON-54544");
	    baseClass.stepInfo("Verify that default action and field logic (Special Objects - Radio Group) works in the context of a document review in Child Window.");
	    String actionName = "Make this Required";
	    String codingform = "CF"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		SoftAssert softAssertion = new SoftAssert();
		SessionSearch sessionSearch = new SessionSearch(driver);	
		ReusableDocViewPage rd = new ReusableDocViewPage(driver);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);	    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.required);
	    codingForm.enterErrorAndHelpMsg(4, "Yes", "Responsiveness", "You must select one of these options");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    //validations
	    codingForm.assignCodingFormToSG(codingform);
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		rd.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = rd.switchTochildWindow();
		baseClass.stepInfo("Child window is opened");
		baseClass.waitForElement(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(25);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form is working in child window as expected");
	    rd.childWindowToParentWindowSwitching(parentWindow);
	    driver.Navigate().refresh();
	    baseClass.handleAlert();
	    codingForm.assignCodingFormToSG(Input.codeFormName);
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that Preview displays correctly and properly for 
	 * Tag/Comments/Metadata objects along with Check/Radio Group and Check Item for new coding form
	 */
	@Test(description = "RPMXCON-54075",enabled = true, groups = { "regression" })
	public void verifyPreviewAlongWithTagCommentMetadata() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54075");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Check Item for new coding form");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.staticText);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectTagType(Input.checkItem, 0, 1);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.optional);
		 cf.selectDefaultActions(5, Input.optional);
		 
		 //save codingform
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 
		 //validate
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(5).isElementAvailable(3));
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Check Item for new coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that Preview displays correctly and properly for 
	 * Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item for New coding form
	 */
	@Test(description = "RPMXCON-54076",enabled = true, groups = { "regression" })
	public void verifyPreviewCrcltyForAllItemActionType() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54076");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item for New coding form");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.staticText);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectTagType(Input.checkItem, 0, 1);
		 cf.selectDefaultActions(1, Input.required);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.required);
		 cf.selectDefaultActions(5, Input.required);
		 
		 //validate cf
		 cf.validateCodingForm();
		 
		 //validate preview
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(5).isElementAvailable(3));
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item for New coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for Tags objects 
	 * along with Selected and "Not Selected" condition for New coding form
	 */
	@Test(description = "RPMXCON-54079",enabled = true, groups = { "regression" })
	public void validatePriviewCorrectlySelectNotSelected() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54079");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for "
	    		+ "Tags objects along with Selected and \"Not Selected\" condition for New coding form");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.addcodingFormAddButton();
		 cf.addTwoCheckBox(Input.tag);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.addcodingFormAddButton();
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.addcodingFormAddButton();
		 
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectTagType("radio item", 0, 1);
		 cf.selectTagType("radio item", 1, 2);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.selectDefaultActions(3, Input.required);
		 
		 //add logic
		 driver.scrollPageToTop();
		 cf.addLogicOptionWithIndex(2, 1, Input.select, Input.thisOptional);
		 cf.addLogicOptionWithIndex(3, 1, Input.notSelect, Input.thisOptional);

		 //validate cf
		 cf.validateCodingForm();
		 
		 //validate
		 cf.clickPreviewButon();
		 baseClass.moveWaitAndClick(cf.getPreview1stRadioBtn(), 15);
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewRadioBtn().isElementAvailable(3));
		 soft.assertTrue(cf.getPreview1stRadioBtn().Selected());
		 soft.assertFalse(cf.getPreview2ndRadioBtn().Selected());
		 baseClass.passedStep("Preview Should get displayed correctly");
		 
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "Tags objects along with Selected and \"Not Selected\" condition for New coding form");
	    loginPage.logout();
	}	
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for 
	 * Metadata objects along with "Not Selected" condition for New coding form
	 */
	@Test(description = "RPMXCON-54080",enabled = true, groups = { "regression" })
	public void validatePriviewCorrectlyMetaDataSelectNotSelected() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54080");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for"
	    		+ " Metadata objects along with \"Not Selected\" condition for New coding form");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.addCodingFormName(cfName);
		
		//add cf field
		 cf.firstCheckBox(Input.metaData);
		 cf.addcodingFormAddButton();
		 cf.addTwoCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.selectDefaultActions(3, Input.required);
		 
		 //add logic
		 driver.scrollPageToTop();
		 cf.addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisRequired);

		 //validate cf
		 cf.validateCodingForm();
		 
		 //validate
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isDisplayed());
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isDisplayed());
		 soft.assertFalse(cf.getCodingForm_PreviewText(1).Selected());
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "Metadata objects along with \"Not Selected\" condition for New coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that Preview displays correctly and properly for all 
	 * objects along with all condition and Radio Item for New coding form
	 */
	@Test(description = "RPMXCON-54081",enabled = true, groups = { "regression" })
	public void varifyPreviewCrctlyAlongWithCondition() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54081");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for all "
	    		+ "objects along with all condition and Radio Item for New coding form");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.staticText);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectTagType("radio item", 0, 1);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.required);
		 cf.selectDefaultActions(5, Input.required);
		 
		 //add logic
		 driver.scrollPageToTop();
		 cf.addLogicOptionWithIndex(1, 1, Input.select, Input.thisHidden);
		 cf.addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisReadOnly);
		 cf.addLogicOptionWithIndexWithoutIncreace(4, 1, Input.select, Input.thisOptional);
		 cf.addLogicOptionWithIndexWithoutIncreace(5, 1, Input.notSelect, Input.thisRequired);
		 
		 //validate cf
		 cf.validateCodingForm();
		 
		 //validate preview
		 cf.clickPreviewButon();
		 soft.assertTrue(cf.getPreviewComment().isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewMetaData().isElementAvailable(3));
		 soft.assertTrue(baseClass.text("Static Text").isElementAvailable(3));
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for all objects "
	    		+ "along with all condition and Radio Item for New coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for all objects 
	 * along with all condition and Check Item for new coding form
	 */
	@Test(description = "RPMXCON-54082",enabled = true, groups = { "regression" })
	public void verifyPreviewCrctlyAllCondiotionAlongWithCheckItem() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54082");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for all objects "
	    		+ "along with all condition and Check Item for new coding form");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.staticText);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectTagType(Input.checkItem, 0, 1);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.required);
		 cf.selectDefaultActions(5, Input.required);
		 
		 //add logic
		 driver.scrollPageToTop();
		 cf.addLogicOptionWithIndex(1, 1, Input.select, Input.thisHidden);
		 cf.addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisReadOnly);
		 cf.addLogicOptionWithIndexWithoutIncreace(4, 1, Input.select, Input.thisOptional);
		 cf.addLogicOptionWithIndexWithoutIncreace(5, 1, Input.notSelect, Input.thisRequired);
		 cf.saveCodingForm();
		 
		 //validate cf
		 cf.validateCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 
		 //validate
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isElementAvailable(1));
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertFalse(cf.getCodingForm_PreviewText(4).isElementAvailable(1));
		 soft.assertTrue(cf.getCodingForm_PreviewText(5).isElementAvailable(3));
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "all objects along with all condition and Check Item for new coding form");
	    loginPage.logout();
	}
	/**
	 * @throws ParseException 
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify validation from preview of coding form when coding form is created
	 *                with the editable metadata field of data type DateOnly
	 */
	
	@Test(description = "RPMXCON-54413",enabled = true, groups = { "regression" })
	public void validateNonDateFormatInPreviewCf() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-54413");
		baseClass.stepInfo("Verify validation from preview of coding form when coding form is created with the editable metadata field of data type DateOnly");
	    String codingform = "CFDate"+Utility.dynamicNameAppender();
	    String date = "Date" + Utility.dynamicNameAppender();
	    System.out.println(date);
	    CodingForm codingForm = new CodingForm(driver);
	    ProjectPage projectPage = new ProjectPage(driver);
	    SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);
	    SoftAssert softAssertion = new SoftAssert();
	    DocViewPage docviewPg = new DocViewPage(driver);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = ldf.parse(sdf.format(new Date()));
		String d2 = ldf.format(d1);
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,null,null,date,"metadata");
		codingForm.addcodingFormAddButton();
		codingForm.saveCodingForm();
		baseClass.stepInfo("Coding form saved successfully");
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(docviewPg.getDateFormat());
		docviewPg.getDateFormat().SendKeys(d2);
		Actions action = new Actions(driver.getWebDriver());
		action.sendKeys(Keys.TAB).build().perform();
		baseClass.waitTillElemetToBeClickable(codingForm.getTestUrCodeClick());
		codingForm.getTestUrCodeClick().waitAndClick(5);
		String errorMessage = codingForm.geErrMsgInPreviewBox().getText();
		softAssertion.assertEquals(errorMessage,"Invalid DateTime");
		softAssertion.assertAll();
		baseClass.passedStep("When passing non formatted date, getting the error messsage as "+errorMessage+" successfully as expected");
		codingForm.deleteCodingForm(codingform, codingform);
		loginPage.logout();
	}
	/**
	 * @throws ParseException 
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify validation from preview of coding form when coding form is created with the editable metadata field of data type DateTime
	 */
	
	@Test(description = "RPMXCON-54412",enabled = true, groups = { "regression" })
	public void validateNonDateTimeFormatInPreviewCf() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-54412");
		baseClass.stepInfo("Verify validation from preview of coding form when coding form is created with the editable metadata field of data type DateTime");
	    String codingform = "CFDate"+Utility.dynamicNameAppender();
	    String dateTime = "DateTime" + Utility.dynamicNameAppender();
	    System.out.println(dateTime);
	    CodingForm codingForm = new CodingForm(driver);
	    ProjectPage projectPage = new ProjectPage(driver);
	    SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);
	    SoftAssert softAssertion = new SoftAssert();
	    DocViewPage docviewPg = new DocViewPage(driver);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = ldf.parse(sdf.format(new Date()));
		String d2 = ldf.format(d1);
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DateTime DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DateTime datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,null,null,dateTime,"metadata");
		codingForm.addcodingFormAddButton();
		codingForm.saveCodingForm();
		baseClass.stepInfo("Coding form saved successfully");
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(docviewPg.getDateFormat());
		docviewPg.getDateFormat().SendKeys(d2);
		Actions action = new Actions(driver.getWebDriver());
		action.sendKeys(Keys.TAB).build().perform();
		baseClass.waitTillElemetToBeClickable(codingForm.getTestUrCodeClick());
		codingForm.getTestUrCodeClick().waitAndClick(5);
		String errorMessage = codingForm.geErrMsgInPreviewBox().getText();
		softAssertion.assertEquals(errorMessage,"Invalid DateTime");
		softAssertion.assertAll();
		baseClass.passedStep("When passing non formatted datetime, getting the error messsage as "+errorMessage+" successfully as expected");
		codingForm.deleteCodingForm(codingform, codingform);
		loginPage.logout();
	}
	

	/**
	 * @Author :Baskar
	 * @Description : Verify that Preview displays correctly and properly when Tag 
	 *                   and Check group combined along with Check Item for New coding form
	 */
	@Test(description = "RPMXCON-54074", enabled = true, groups = { "regression" })
	public void verifyPreviewAlongWithCheckItem() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54074");
		baseClass.stepInfo("Verify that Preview displays correctly and properly when Tag and"
				+ " Check group combined along with Check Item for New coding form");
		CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag"+Utility.dynamicNameAppender();
		int index = 1;
		String expectedCheckGrp = "checkgroup_"+index+"";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		baseClass.stepInfo("Created the tag sucessfully");
		
		// creating codingform
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		cf.addNewCodingFormButton();
		baseClass.waitForElement(cf.getCodingFormName());
		cf.getCodingFormName().SendKeys(codingform);
		cf.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		cf.addcodingFormAddButton();
		cf.specialObjectsBox(Input.checkGroup);
		cf.addcodingFormAddButton();
		cf.selectTagTypeByIndex("check item",index,0);
		cf.getRGDefaultAction().ScrollTo();
		baseClass.waitForElement(cf.getRGDefaultAction());
		cf.getRGDefaultAction().selectFromDropdown().selectByVisibleText(Input.notSelectable);
		baseClass.stepInfo("check group is associated to the created tag");
		driver.scrollPageToTop();
		
		// validating the success message in validate button
		baseClass.waitForElement(cf.getCFValidateBtn());
		cf.getCFValidateBtn().waitAndClick(5);
		String validationExpected="Coding Form Validation Successful.";
		String validationActual=cf.getCFValidateMsg().getText();
		soft.assertEquals(validationActual, validationExpected);
		baseClass.passedStep("without any error ,successfull validation message displayed");
		baseClass.waitForElement(cf.getCFValidationPopUpOkBtn());
		cf.getCFValidationPopUpOkBtn().waitAndClick(5);
		baseClass.waitForElement(cf.getSaveCFBtn());
		cf.getSaveCFBtn().waitAndClick(5);
		baseClass.waitForElement(cf.getValidationButtonYes());
		cf.getValidationButtonYes().waitAndClick(5);
		baseClass.stepInfo("Coding form saved successfully");
		
		//Edit the existing coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		//verify the radio group associated with tag
		cf.editCodingForm(codingform);
		baseClass.stepInfo("Edited the coding form sucessfully");
		
		// preview validation
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(cf.getCF_PreviewButton());
		cf.getCF_PreviewButton().waitAndClick(10);
		boolean notSelect=cf.getDisabledTagsInPreviewBox(tagname).isElementAvailable(2);
		soft.assertTrue(notSelect);
		baseClass.waitForElement(cf.getTagGroupValues(index));
		String actualCheckGroup = cf.getTagGroupValues(index).GetAttribute("systemcontrolname");
		soft.assertEquals(expectedCheckGrp, actualCheckGroup);
		soft.assertAll();
		baseClass.passedStep("The check group associated in tag is successfully reflected after editing the saved coding form");
		baseClass.passedStep("preview display correctly along with checkgroup and tag in notselectable state");
		
		//Deleting the tag and cf
		cf.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.deleteAllTags(tagname);

		loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that if comment or metadata objects have no configured help text, 
	 * no mouse over tool tip should appear for that field in either Coding Form Preview or Coding Form panel of DocView
	 */
	@Test(description = "RPMXCON-54267",enabled = true, groups = { "regression" })
	public void verifyNoToolTipMsg() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54267");
	    baseClass.stepInfo("Verify that if comment or metadata objects have no configured help text, no mouse over tool tip "
	    		+ "should appear for that field in either Coding Form Preview or Coding Form panel of DocView");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		DocViewPage docview = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.addcodingFormAddButton();
		 cf.saveCodingForm();
		 
		 //validate in preview
		 cf.clickPreviewButon();
		 soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(0)), "");
		 soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(1)), "");
		 baseClass.passedStep("No tool tip is displayed on mouse over of the objects");
		 
	    cf.AssignCFstoSG(cfName);
	    sessionSearch.basicContentSearch(Input.testData1);
	    sessionSearch.ViewInDocViews();
	    
	    //validate in docview
	    soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(0)), "");
		soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(1)), "");
		baseClass.passedStep("No tool tip is displayed on mouse over of the objects");
		
		//validate in docview child window
		docview.clickGearIconOpenCodingFormChildWindow();
		docview.switchToNewWindow(2);
		soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(0)), "");
		soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(1)), "");
		baseClass.passedStep("No tool tip is displayed on mouse over of the objects");
		driver.close();
		docview.switchToNewWindow(1);
		
		//restore default codingform
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		docview.acceptBrowserAlert(true);
		cf.AssigndefaultCFstoSG(Input.codeFormName);
	    
		//delete created cf
		cf.deleteCodingForm(cfName, cfName);
		
	    soft.assertAll();
	    baseClass.passedStep("Verify that if comment or metadata objects have no configured help text, no mouse over tool tip "
	    		+ "should appear for that field in either Coding Form Preview or Coding Form panel of DocView");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that if comment or metadata objects have configured help text, on mouse over tool tip should 
	 * appear for that field in either Coding Form Preview or Coding Form panel of DocView
	 */
	@Test(description = "RPMXCON-54268",enabled = true, groups = { "regression" })
	public void verifyToolTipMsg() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54268");
	    baseClass.stepInfo("Verify that if comment or metadata objects have configured help text, on mouse over tool tip should"
	    		+ " appear for that field in either Coding Form Preview or Coding Form panel of DocView");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		DocViewPage docview = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.addcodingFormAddButton();
		 cf.enterErrorAndHelpMsg(0, "Yes", Input.helpText, Input.errorMsg);
		 cf.enterErrorAndHelpMsg(1, "Yes", Input.helpText, Input.errorMsg);
		 cf.saveCodingForm();
		 
		 //validate in preview
		 cf.clickPreviewButon();
		 soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(0)), Input.helpText);
		 soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(1)), Input.helpText);
		 baseClass.passedStep("Configured tool tip is displayed on mouse over of the objects");
		 
	    cf.AssignCFstoSG(cfName);
	    sessionSearch.basicContentSearch(Input.testData1);
	    sessionSearch.ViewInDocViews();
	    
	    //validate in docview
	    driver.waitForPageToBeReady();
	    soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(0)), Input.helpText);
		soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(1)), Input.helpText);
		baseClass.passedStep("Configured tool tip is displayed on mouse over of the objects");
		
		//validate in docview child window
		docview.clickGearIconOpenCodingFormChildWindow();
		docview.switchToNewWindow(2);
		soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(0)), Input.helpText);
		soft.assertEquals(baseClass.getToolTipMsg(cf.getCodingForm_PreviewText(1)), Input.helpText);
		baseClass.passedStep("Configured tool tip is displayed on mouse over of the objects");
		driver.close();
		docview.switchToNewWindow(1);
		
		//restore default codingform
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		docview.acceptBrowserAlert(true);
		cf.AssigndefaultCFstoSG(Input.codeFormName);
	    
		//delete created cf
		cf.deleteCodingForm(cfName, cfName);
		
	    soft.assertAll();
	    baseClass.passedStep("Verify that if comment or metadata objects have configured help text, on mouse over tool tip "
	    		+ "should appear for that field in either Coding Form Preview or Coding Form panel of DocView");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.
	 */
	@Test(description = "RPMXCON-54536",enabled = true, groups = { "regression" })
	public void verifyDefaultActionFeildLogicInTechIssueGroup() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54536");
	    baseClass.stepInfo("Verify that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		//open cf in edit mode
		cf.navigateToCodingFormPage();
		cf.editCodingForm(Input.codingFormName);
		
		//expand tech issue group
		baseClass.waitForElement(cf.getCfObjectHeader("Tech Issue Group"));
		cf.getCfObjectHeader("Tech Issue Group").waitAndClick(5);
		driver.waitForPageToBeReady();
		
		//verify 
		soft.assertTrue(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").isElementAvailable(3));
		soft.assertTrue(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Field Logic").isElementAvailable(3));
		soft.assertTrue(cf.getCfObjectHeader("Tech Issue Group").getText().contains("CHECKGROUP"));
		baseClass.passedStep("CheckGroup's Default Action along with field logic NOT miss in coding form.     For - CheckGroup's  -"
				+ " Default action and field logic presented as is (Special Objects) when user edit the same Coding Form.");
		
	    soft.assertAll();
	    baseClass.passedStep("Verified that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.");
	    loginPage.logout();
	    
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.
	 */
	@Test(description = "RPMXCON-54537",enabled = true, groups = { "regression" })
	public void verifyDefaultActionFeildLogicInTechIssueGroupCheckGroup() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54537");
	    baseClass.stepInfo("Verify that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//open cf in edit mode
		cf.navigateToCodingFormPage();
		cf.editCodingForm(Input.codingFormName);
		baseClass.stepInfo("coding form open in edit mode");
		
		//expand texh issue group
		driver.waitForPageToBeReady();
		baseClass.waitForElement(cf.getCfObjectHeader("Tech Issue Group"));
		cf.getCfObjectHeader("Tech Issue Group").waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Tch Issue group was expanded");
		
		//verify default action feild logic values 
		soft.assertTrue(cf.getCfObjectHeader("Tech Issue Group").getText().contains("CHECKGROUP"));
		soft.assertEquals(cf.getCfObjectDefaultActionLabelInput("Tech Issue Group", "Error Message").GetAttribute("value"),
				"If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");
		soft.assertEquals(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").selectFromDropdown().getFirstSelectedOption().getText(), Input.hidden);
		soft.assertEquals(cf.getCfObjectFeildLogicDropDrownByIndex("Tech Issue Group", 2).selectFromDropdown().getFirstSelectedOption().getText(), Input.select);
		soft.assertEquals(cf.getCfObjectFeildLogicDropDrownByIndex("Tech Issue Group", 3).selectFromDropdown().getFirstSelectedOption().getText(), Input.thisRequired);
		baseClass.passedStep("As per Attachment - CF for Special Objects (Check Group) should saved with default action and field logic.");
		
	    soft.assertAll();
	    baseClass.passedStep("Verified that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.");
	    loginPage.logout();
	    
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for Tag/Comments/Metadata objects 
	 * along with Check/Radio Group and Radio Item on coding form screen
	 */
	@Test(description = "RPMXCON-54061",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplaysCorrecly() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54061");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Tag/Comments/Metadata"
	    		+ " objects along with Check/Radio Group and Radio Item on coding form screen");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.staticText);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter details
		 cf.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Check Item");
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectDefaultActions(1, Input.required);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.hidden);
		 cf.selectDefaultActions(5, Input.notSelectable);
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 cf.clickPreviewButon();
		 
		 //verify
		 soft.assertTrue(cf.getPreviewComment().isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewMetaData().isElementAvailable(3));
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for Tag/Comments/Metadata "
	    		+ "objects along with Check/Radio Group and Radio Item on coding form screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that default action and field logic (Special Objects - Check Group) works in the context of a document review in Parent Window
	 */
	@Test(description = "RPMXCON-54538",enabled = true, groups = { "regression" })
	public void verifyCheckGroupInDocView() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54538");
	    baseClass.stepInfo("Verify that default action and field logic (Special Objects - Check Group) works in the context of a document review in Parent Window");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//check default codingform in default
		cf.selectDefaultProjectCodingForm();
		
		//drag and drop query
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.viewInDocView();
		
		//verify
		driver.waitForPageToBeReady();
		docview.Hot_DocCheckBox().waitAndClick(1);
		soft.assertFalse(baseClass.text("Processing_Issue").isDisplayed()," progressing_issue");
		soft.assertFalse(baseClass.text("Processing_Issue").isDisplayed(), "Foreign_Language");
		baseClass.passedStep("Tech issue group check box is as per default action");
		
	    soft.assertAll();
	    baseClass.passedStep("Verified that default action and field logic (Special Objects - Check Group) works in the context of a document review in Parent Window");
	    loginPage.logout();
	    
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that comment label should be allowed to be upto 255 characters in length and should wrap around properly if goes beyond the edge of the box
	 */
	@Test(description = "RPMXCON-54522",enabled = true, groups = { "regression" })
	public void verifyCommentTextWrapProperly() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54522");
	    baseClass.stepInfo("Verify that comment label should be allowed to be upto 255 characters in length and should wrap around properly if goes beyond the edge of the box");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		DocViewPage docview = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		String moreChar  = Utility.randomCharacterAppender(300);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.comments);
		 cf.addcodingFormAddButton();
		 cf.enterErrorAndHelpMsg(0, "Yes", Input.helpText, Input.errorMsg);
		 String cmdLabel = cf.getCodingForm_TagNames(1).getText().trim();
		 
		 //validate in preview
		 cf.clickPreviewButon();
		 soft.assertEquals(cf.getCodingForm_PreviewText(0).getText().trim(), cmdLabel);
		 baseClass.passedStep("Coding form saved  Presentation of the Comment Field Name should be immediately above the Comment Box in Coding Form Preview pop up");
		 
		 //edit comment more than 255
		 driver.waitForPageToBeReady();
		 cf.getPopUpCloseBtn().waitAndClick(5);
		 cf.getCF_objectName(0).SendKeys(moreChar);
		 cf.saveCodingForm();
		 cf.clickPreviewButon();
		 
		 //validate length 
		 soft.assertTrue(cf.getCodingForm_TagNames(1).getText().length()<256);
		 baseClass.passedStep("Comment label should be allowed to be upto 255 characters in length");
		 
		 //text wrap verification
		 soft.assertTrue(cf.getCodingForm_PreviewText(0).getWebElement().getSize().height>=19, "preview wrap check");
		 baseClass.passedStep("Comment label should wrap around around properly in cf preview");
		 
		 
	    cf.AssignCFstoSG(cfName);
	    sessionSearch.basicContentSearch(Input.testData1);
	    sessionSearch.ViewInDocViews();
	    
	    //pre-req-save stamp
	    baseClass.waitForElement(docview.getDocument_CommentsTextBox());
		docview.getDocument_CommentsTextBox().SendKeys("add some comment for cf stamp");
		docview.stampColourSelection("stamp"+Utility.dynamicNameAppender(), Input.stampColour);
		
	    //validate in docview
	    driver.waitForPageToBeReady();
	    soft.assertTrue(cf.getCodingForm_PreviewText(0).getWebElement().getSize().height>=19, "docview parent window check");
	    baseClass.passedStep("Comment label should wrap around around properly in parent window");
		
		//validate in docview child window
		docview.clickGearIconOpenCodingFormChildWindow();
		docview.switchToNewWindow(2);
		soft.assertTrue(cf.getCodingForm_PreviewText(0).getWebElement().getSize().height>=19, "docview child window check");
		baseClass.passedStep("Comment label should wrap around around properly in child window");
		
		//stamp check
		docview.lastAppliedStamp(Input.stampColour);
		soft.assertTrue(cf.getCodingForm_PreviewText(0).getWebElement().getSize().height>=19, "docview check from stamp");
		baseClass.passedStep("Comment label should wrap around around properly for stamp");
		
		driver.close();
		docview.switchToNewWindow(1);
		
		//restore default codingform
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		docview.acceptBrowserAlert(true);
		cf.AssigndefaultCFstoSG(Input.codeFormName);
		soft.assertAll();
	    
		//delete created cf
		cf.deleteCodingForm(cfName, cfName);
		
	    
	    baseClass.passedStep("Verified that comment label should be allowed to be upto 255 characters in length and should wrap around properly if goes beyond the edge of the box");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that when user edit the Coding Form then User can edit default action and field logic Special Objects.(Radio Group)
	 */
	@Test(description = "RPMXCON-54540",enabled = true, groups = { "regression" })
	public void verifyUserCanEditDAandFL() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54540");
	    baseClass.stepInfo("Verify that when user edit the Coding Form then User can edit default action and field logic Special Objects.(Radio Group)");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String codingform = "Default Project Coding Form_Copy"+ Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//create cf as per attachment
		cf.createCodingFormLikeDefaultCodingForm(codingform);
		
		//edit cf
		cf.editCodingForm(codingform);
		
		//expand
		baseClass.waitForElement(cf.getCfObjectHeader("Tech Issue Group"));
		cf.getCfObjectHeader("Tech Issue Group").waitAndClick(5);
		driver.waitForPageToBeReady();
		
		//verify user can edit
		cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").selectFromDropdown().selectByVisibleText(Input.required);
		cf.getCfObjectFeildLogicDropDrownByIndex("Tech Issue Group", 3).selectFromDropdown().selectByVisibleText(Input.thisOptional);
		driver.waitForPageToBeReady();
		soft.assertEquals(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").selectFromDropdown().getFirstSelectedOption().getText(), Input.required);
		soft.assertEquals(cf.getCfObjectFeildLogicDropDrownByIndex("Tech Issue Group", 3).selectFromDropdown().getFirstSelectedOption().getText(), Input.thisOptional);
		baseClass.passedStep("When user edit the Coding Form then User  able to  edit default action and field logic Special Objects.(Radio Group)");
		
		//delete created cf
		cf.deleteCodingForm(codingform, codingform);
		
	    soft.assertAll();
	    baseClass.passedStep("Verify that when user edit the Coding Form then User can edit default action and field logic Special Objects.(Radio Group)");
	    loginPage.logout();
	    
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that default action and field logic presented as is (Special Objects - Radio Group) when user edit the same Coding Form.
	 */
	@Test(description = "RPMXCON-54541",enabled = true, groups = { "regression" })
	public void verifyDefaultActionFieldLogic() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54541");
	    baseClass.stepInfo("Verify that default action and field logic presented as is (Special Objects - Radio Group) when user edit the same Coding Form.");
	    
	    CodingForm cf = new CodingForm(driver);
	    SoftAssert soft  = new SoftAssert();
		
		String codingform = "Copy_of_Default Project Coding Form"+ Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//create cf as per attachment
		cf.createCodingFormLikeDefaultCodingForm(codingform);
		
		//edit cf
		cf.editCodingForm(codingform);
		
		//expand
		baseClass.waitForElement(cf.getCfObjectHeader("Tech Issue Group"));
		cf.getCfObjectHeader("Tech Issue Group").waitAndClick(5);
		driver.waitForPageToBeReady();
		
		System.out.println(cf.getCfObjectHeader("Tech Issue Group").getText());
		soft.assertTrue(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").isElementAvailable(3) , " default action present");
		soft.assertTrue(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Field Logic").isElementAvailable(3), " field logic present");
		soft.assertTrue(cf.getCfObjectHeader("Tech Issue Group").getText().contains("RADIOGROUP"), "spl obj verification");
		baseClass.passedStep("Radio Group's Default Action along with field logic should NOT miss in coding form.     For - Radio Group's - "
				+ "Default action and field logic presented as is (Special Objects) when user edit the same Coding Form.");
		
		//delete created cf
		cf.deleteCodingForm(codingform, codingform);
		
	    soft.assertAll();
	    baseClass.passedStep("Verified that default action and field logic presented as is (Special Objects - Radio Group) when user edit the same Coding Form.");
	    loginPage.logout();
	    
	}
	/**
	 * @Author
	 * @Description : Verify that when user try to delete default set coding form an
	 *              error message occurred \"Error ! A coding form configured as
	 *              default coding form for a security group or an assignment cannot
	 *              be deleted\".
	 */
	@Test(description = "RPMXCON-65190", enabled = true, groups = { "regression" })
	public void verifyUserTryDeleteDefaultSetCodingFormErrorMessageOccured() {
		String codingform = "codingForm" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-65190 CodingForm");
		baseClass.stepInfo(
				"Verify that when user try to delete default set coding form an error message occurred \"Error ! A coding form configured as default coding form for a security group or an assignment cannot be deleted\". ");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating new coding Form
		baseClass.stepInfo("creating new coding Form.");
		codingForm.navigateToCodingFormPage();
		codingForm.createCodingFormWithoutObjects(codingform);

		// setting newly created coding Form as Default Coding Form
		baseClass.stepInfo("setting newly created coding Form as Default Coding Form.");
		codingForm.AssignCFstoSG(codingform);

		// performing Delete action
		baseClass.stepInfo(" performing Delete action on  default set coding form.");
		codingForm.deleteCodingForm(codingform, codingform);

		// Verify that an error message occurred
		String expectedErrorMessage = "A coding form configured as default coding form for a security group or an assignment cannot be deleted.";
		baseClass.VerifyErrorMessage(expectedErrorMessage);
		baseClass
				.passedStep("Verified that when user try to delete default set coding form an error message occurred.");

		// deleting created coding form
		codingForm.selectDefaultProjectCodingForm();
		codingForm.deleteCodingForm(codingform, codingform);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws InterruptedException
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that the slected coding form are messaged as "YES",
	 *              default as "YES(Default)", remaining as "NO" in "Security Group
	 *              Form" column in "Manage Coding Form" page(UI).RPMXCON-64912
	 */
	@Test(description = "RPMXCON-64912", enabled = true, groups = { "regression" })
	public void verifySelectedCodingFormMessagedAsYesRemainingAsNoDefaultAsYesDefaultInSecurityGroupFormColumn()
			throws InterruptedException {

		AssignmentsPage assignPage = new AssignmentsPage(driver);
		List<String> defaultSetSecurityGroupForm = new ArrayList<String>(Arrays.asList(Input.codingFormName));
		baseClass.stepInfo("Test case Id: RPMXCON-64912 CodingForm");
		baseClass.stepInfo(
				"Verify that the slected coding form are messaged as \"YES\", default as \"YES(Default)\", remaining as \"NO\" in \"Security Group Form\" column in \"Manage Coding Form\" page(UI).");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create CodingForm Based On Condition
		List<String> listOfCodingFormCreated = codingForm.createCodingformBasedOnCondition(10);

		// getting list of all coding form in manage codingForm page.
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		codingForm.getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
		List<String> listOfTotalCodingFormName = baseClass.availableListofElements(codingForm.getCFnames());

		// Select the required coding form (less than 16) and make any one as default
		// coding form
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		codingForm.selectGivenNoOfCodingForms(3, Input.codingFormName, false);
		baseClass
				.stepInfo("Selecting the required coding form (less than 16) and make any one as default coding form.");

		// getting list of coding form selected
		List<String> listOfCodingFormSelected = baseClass.availableListofElements(codingForm.sortOrderHamBurger());
		listOfCodingFormSelected.remove(0);
		listOfCodingFormSelected.add(Input.codeFormName);

		// getting list of coding form not selected
		List<String> listOfCodingFormNotSelected = baseClass.removeItemsFromList(listOfTotalCodingFormName,
				listOfCodingFormSelected);
		listOfCodingFormSelected.remove(Input.codeFormName);
		assignPage.sortCodeFormOrderSaveBtn().waitAndClick(5);
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitTime(2);

		//
		codingForm.getCodingForm_NumberToShow().ScrollTo();
		baseClass.waitForElement(codingForm.getCodingForm_NumberToShow());
		codingForm.getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");

		// Verify that the selected coding form are messaged as "YES" in "Security Group
		// Form" column in "Manage Coding Form" page.
		codingForm.verifyCFSecurityGroupFormColumnValue(listOfCodingFormSelected, "YES");
		baseClass.stepInfo(
				"Verified that the selected coding form are messaged as \"YES\" in \"Security Group Form\" column in \"Manage Coding Form\" page.");

		// Verify that the unselected coding form are messaged as "NO" in "Security
		// Group Form" column in "Manage Coding Form" page.
		codingForm.verifyCFSecurityGroupFormColumnValue(defaultSetSecurityGroupForm, "YES (Default)");
		baseClass.stepInfo(
				"Verified that the unselected coding form are messaged as \"NO\" in \"Security Group Form\" column in \"Manage Coding Form\" page.");

		// Verify that the selected and set as default coding form are messaged as
		// "YES(Default)" in "Security Group Form" column in "Manage Coding Form" page.
		codingForm.verifyCFSecurityGroupFormColumnValue(listOfCodingFormNotSelected, "NO");
		baseClass.stepInfo(
				"Verified that the selected and set as default coding form are messaged as \"YES(Default)\" in \"Security Group Form\" column in \"Manage Coding Form\" page.");

		// delete created codingForms
		codingForm.DeleteMultipleCodingform(listOfCodingFormCreated);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that Sorting is disabled for Default set coding form
	 *              (UI).RPMXCON-65187
	 */
	@Test(description = "RPMXCON-65187", enabled = true, groups = { "regression" })
	public void verifySortingDisabledForDefaultSetCodingForm() {

		String codingform = "codingForm" + Utility.dynamicNameAppender();
		int noOfCFSelected = 2;
		baseClass.stepInfo("Test case Id: RPMXCON-65187 CodingForm");
		baseClass.stepInfo("Verify that Sorting is disabled for Default set coding form (UI).");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating new coding Form
		baseClass.stepInfo("creating new coding Form.");
		codingForm.navigateToCodingFormPage();
		codingForm.createCodingFormWithoutObjects(codingform);

		// Selecting less than 15 coding form and make any one as default coding form
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		codingForm.selectGivenNoOfCodingForms(noOfCFSelected, codingform, false);
		baseClass.stepInfo("Selecting less than 15 coding form and make any one as default coding form.");

		// Verify that the default set Coding form should always display at the top row
		// of Sort Coding Form Order pop-up table
		List<String> listOfCFInCodingFormOrder = baseClass.availableListofElements(codingForm.sortOrderHamBurger());
		baseClass.compareTextViaContains(listOfCFInCodingFormOrder.get(0), codingform,
				"Verified that the default set Coding form is display at the top row of 'Sort Coding Form Order' pop-up table.",
				"default set Coding form is Not display at the top row of 'Sort Coding Form Order' pop-up table.");

		// Verify that Sorting is disabled for Default set coding form (i.e. Hamburger
		// icon is disabled)
		baseClass.ValidateElement_Presence(codingForm.getDefaultSetCodingFormHamburgerIconsState(codingform),
				"Default Set Coding Form Hamburger Icons State.");
		baseClass.passedStep(
				"Verified that Sorting is disabled for Default set coding form (i.e. Hamburger icon is disabled)");

		// deleting created coding form
		codingForm.selectDefaultProjectCodingForm();
		codingForm.deleteCodingForm(codingform, codingform);

		// logOut
		loginPage.logout();
	}
	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-64894
	 * @throws Exception
	 * @Description Verify that when user check on the "CODING FORM NAME" check box
	 *              from "Add/remove coding form in this security group" coding form
	 *              pop-up all the present coding form should get selected from the
	 *              column.
	 */
	@Test(description = "RPMXCON-64894", enabled = true, groups = { "regression" })
	public void verifyPopUpAllGetSelectedFromColumn() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-64894");
		base.stepInfo(
				"Verify that when user check on the \"CODING FORM NAME\" check box from \"Add/remove coding form in this security group\" coding form pop-up all the present coding form should get selected from the column.");
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
		base.stepInfo("Codeform name check box is clicked to select all the codingforms");
		driver.waitForPageToBeReady();
		int Check = cf.getCfChecBoxUsingSize().size();
		System.out.println(Check);
		for (int i = 0; i < Check; i++) {
			List<WebElement> element = cf.getCfChecBoxUsingSize().FindWebElements();
			element.get(i).click();
		}
		base.waitTime(3);
        if (cf.checkDefaultCodingFormIsSelected().GetAttribute("checked") == null) {
            cf.getDefaultCodingFormInputBox().waitAndClick(5);
        }
        if (cf.checkDefaultCodingFormRadioBtnIsSelected().GetAttribute("checked") == null) {
            cf.getDefaultCodingFormRadioBtn().waitAndClick(5);
        }
		base.stepInfo("checked all the codingforms");
		base.waitTime(5);
		soft.assertFalse(cf.sortOrderNxtDisableBtn().isElementAvailable(10));
		soft.assertAll();
		base.stepInfo("Next button is disabled as expected");
		base.passedStep("All the present coding form has been get selected As expected when we checked code form names");
		cf.getCfPopUpCancel().waitAndClick(10);
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that when we have more than 10 coding form scroll bar should be
	 *  present in the table of \"Add/remove coding form in this security group\" pop-up page (UI).RPMXCON-64910
	 */
	
	@Test(description = "RPMXCON-64910",enabled = true, groups = { "regression" })
	public void verifyMoreThanTenCodingFormScrollBarPresentInTableOfAddOrRemoveCFSecurityGroupPopUp() throws InterruptedException {
		
		softAssert = new SoftAssert();
		
		baseClass.stepInfo("Test case Id: RPMXCON-64910 CodingForm");
		baseClass.stepInfo("Verify that when we have more than 10 coding form scroll bar should be present in the table of \"Add/remove coding form in this security group\" pop-up page (UI).");
		
		//login as RMU
		 loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 
		 // create CodingForm Based On Condition
		codingForm.navigateToCodingFormPage();
		List<String> listOfCodingFormCreated = codingForm.createCodingformBasedOnCondition(11);
		baseClass.stepInfo("creating more than 10 coding form.");
		
		// Clicking on  "Set Security Group Coding form" button.
		codingForm.navigateToCodingFormPage();
		baseClass.waitForElement(codingForm.getSetCFButton());
		codingForm.getSetCFButton().waitAndClick(10);
		baseClass.stepInfo(" \"Set Security Group Coding form\" button is present in \"Manage Coding Form\" page.");
		
		// Verify that when we have more than 10 coding form the scroll bar should be present in the table of "Add/remove coding form in this security group" pop-up page
		boolean verifyScrollBar = codingForm.verifyAddRemoveCodingFormSecurityGroupPopUpScrollBar();
		softAssert.assertEquals(verifyScrollBar,true);
		softAssert.assertAll();
		baseClass.passedStep("Verified that when we have more than 10 coding form the scroll bar should be present in the table of \"Add/remove coding form in this security group\" pop-up page.");
		
		// delete created codingForms
		driver.Navigate().refresh();
		codingForm.DeleteMultipleCodingform(listOfCodingFormCreated);

		// logOut
		loginPage.logout();
	}


	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-50967
	 * @throws Exception
	 * @Description Verify when user navigates from Basic Search/Saved search/Doc
	 *              List and coding form is assigned to security group, custom
	 *              coding form is editable
	 */
	@Test(description = "RPMXCON-50967", enabled = true, groups = { "regression" })
	public void verifyCustomCfIsEditable() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-50967");
		base.stepInfo(
				"Verify when user navigates from Basic Search/Saved search/Doc List and coding form is assigned to security group, custom coding form is editable");
		CodingForm cf = new CodingForm(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert soft = new SoftAssert();
		String cfName = "coding" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();

		// Creating Coding Form
		cf.createCodingform(cfName);
		// setting newly created coding Form as Default Coding Form
		baseClass.stepInfo("setting newly created coding Form as Default Coding Form.");
		driver.waitForPageToBeReady();
		cf.assignCodingFormToSG(cfName);

		// Searching Content document go to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.viewInDocView();

		// custom CF is editable
		driver.waitForPageToBeReady();
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> CFInDocView = baseClass.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListWithOnlyOneString(CFInDocView, cfName,
				cfName + "  custom coding forms is displayed on doc view page ",
				"creating coding forms is not displayed on doc view page");
		driver.waitForPageToBeReady();
		base.waitForElement(docView.getDocument_AddComment());
		soft.assertTrue(docView.getDocument_AddComment().isElementPresent());
		Boolean commentbox = docView.getDocument_AddComment().Enabled();
		if (commentbox == true) {
			base.waitForElement(docView.getDocument_AddComment());
			docView.getDocument_AddComment().Click();
			docView.getDocument_AddComment().SendKeys("comment");
			base.passedStep("Custom CF comment box is Editable on selected document as expected");
		} else {
			base.failedStep("custom CF comment box is not Editable");
		}
		soft.assertTrue(docView.getAttorney_ClientCheckBox().isElementPresent());
		Boolean checkbox = docView.getAttorney_ClientCheckBox().Enabled();
		if (checkbox == true) {
			base.waitForElement(docView.getAttorney_ClientCheckBox());
			docView.getAttorney_ClientCheckBox().waitAndClick(5);
			base.passedStep("Custom CF checkbox box is Editable on selected document as expected");
		} else {
			base.failedStep("Custom CF check box is not Editable");
		}
		base.passedStep(cfName + "   Custom coding form has been editable for the document successfully");
		soft.assertAll();
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Successfully login as Reviewer '" + Input.rev1userName + "'");
		// Searching Content document go to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		// custom CF is editable
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> CFInDocView1 = baseClass.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListWithOnlyOneString(CFInDocView1, cfName,
				cfName + "  Custom coding forms is displayed on doc view page ",
				"Custom coding forms is not displayed on doc view page");
		driver.waitForPageToBeReady();
		base.waitForElement(docView.getDocument_AddComment());
		soft.assertTrue(docView.getDocument_AddComment().isElementPresent());
		Boolean commentbox1 = docView.getDocument_AddComment().Enabled();
		if (commentbox1 == true) {
			base.waitForElement(docView.getDocument_AddComment());
			docView.getDocument_AddComment().Click();
			docView.getDocument_AddComment().SendKeys("comment");
			base.passedStep("Custom CF comment box is Editable on selected document as expected");
		} else {
			base.failedStep("Custom CF comment box is not Editable");
		}
		soft.assertTrue(docView.getAttorney_ClientCheckBox().isElementPresent());
		Boolean checkbox1 = docView.getAttorney_ClientCheckBox().Enabled();
		if (checkbox1 == true) {
			base.waitForElement(docView.getAttorney_ClientCheckBox());
			docView.getAttorney_ClientCheckBox().waitAndClick(5);
			base.passedStep("Custom CF checkbox box is Editable on selected document as expected");
		} else {
			base.failedStep("Custom CF check box is not Editable");
		}
		base.passedStep(cfName + "   Custom coding form has been editable for the document successfully");
		soft.assertAll();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		cf.assignCodingFormToSG(Input.codeFormName);
		loginPage.logout();

	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview(Radio Button) works Properly in the context of a document review in Child Window in DocView
	 */
	@Test(description = "RPMXCON-54558",enabled = true, groups = { "regression" })
	public void verifyCfPreviewAsRadioBtn() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54558");
	    baseClass.stepInfo("Verify that Coding Form Preview(Radio Button) works Properly in the context of a document review in Child Window in DocView");
	    String actionName = "Make this Required";
	    String codingform = "CF"+Utility.dynamicNameAppender();	
	    DocViewPage docview = new DocViewPage(driver);
	    SessionSearch sessionSearch = new SessionSearch(driver);
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		SoftAssert softAssertion = new SoftAssert();
		
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();	    
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);	    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.optional);
	    codingForm.enterErrorAndHelpMsg(4, "No", "Responsiveness", null);
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.selectTagTypeByIndex("check item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingform);	    
	    sessionSearch.basicContentSearch("null");    
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");
	    docview.clickGearIconOpenCodingFormChildWindow();
		String parentwindow=docview.switchTochildWindow();
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(5);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form working as expected");
	    codingForm.selectObjectsInPreviewBox("Responsive").waitAndClick(15);
	    baseClass.waitTillElemetToBeClickable(docview.getCodingFormSaveButton());
	    docview.getCodingFormSaveButton().waitAndClick(10);
	    docview.childWindowToParentWindowSwitching(parentwindow);
	    codingForm.assignCodingFormToSG(Input.codeFormName);
		//delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview(CheckBox) works Properly in the context of a document review in Child Window in DocView
	 */
	@Test(description = "RPMXCON-54557",enabled = true, groups = { "regression" })
	public void verifyCfPreviewAsCheckBox() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54557");
	    baseClass.stepInfo("Verify that Coding Form Preview(CheckBox) works Properly in the context of a document review in Child Window in DocView");
	    String actionName = "Make this Required";
	    String codingform = "CF"+Utility.dynamicNameAppender();	  
	    DocViewPage docview = new DocViewPage(driver);
	    SessionSearch sessionSearch=new SessionSearch(driver);
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		SoftAssert softAssertion = new SoftAssert();
		
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();	    
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.optional);
	    codingForm.enterErrorAndHelpMsg(4, "No", "Responsiveness", null);
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);	
	   
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingform);	    
	    sessionSearch.basicContentSearch("null");    
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");
	    docview.clickGearIconOpenCodingFormChildWindow();
		String parentwindow=docview.switchTochildWindow();
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(5);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form working as expected");
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(15);
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.waitTillElemetToBeClickable(docview.getCodingFormSaveButton());
	    docview.getCodingFormSaveButton().waitAndClick(10);
	    docview.childWindowToParentWindowSwitching(parentwindow);
	    codingForm.assignCodingFormToSG(Input.codeFormName);
		//delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify on deleting of the assigned coding form/coding form objects message should be displayed.
	 */
	@Test(description = "RPMXCON-54163",enabled = true, groups = { "regression" })
	public void verifyErrorMsgDeletingCfAssignedSG() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54163");
	    baseClass.stepInfo("Verify on deleting of the assigned coding form/coding form objects message should be displayed.");
	    String codingformSG = "CF"+Utility.dynamicNameAppender();	 
	    String assignCf = "CF"+Utility.dynamicNameAppender();	 
	    String assignmentName = "assignment"+Utility.dynamicNameAppender();	 
	    DocViewPage docview = new DocViewPage(driver);
	    SessionSearch sessionSearch=new SessionSearch(driver);
	    AssignmentsPage assignPage=new AssignmentsPage(driver);
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingformSG);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingformSG,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();	    
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(1, "Responsive Group");
	    codingForm.selectDefaultActions(1, Input.optional);
	    codingForm.enterErrorAndHelpMsg(1, "No", "Responsiveness", null);
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.saveCodingForm();
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(assignCf);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(assignCf,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();	    
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(1, "Responsive Group");
	    codingForm.selectDefaultActions(1, Input.optional);
	    codingForm.enterErrorAndHelpMsg(1, "No", "Responsiveness", null);
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingformSG);	    
	    sessionSearch.basicContentSearch("crammer");    
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");	    
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    codingForm.selectObjectsInPreviewBox("Responsive").waitAndClick(15);
	    baseClass.waitTillElemetToBeClickable(docview.getCodingFormSaveButton());
	    docview.getCodingFormSaveButton().waitAndClick(10);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.getCodingForm_Search().SendKeys(codingformSG);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCodingForm_DeleteButton(codingformSG));
		codingForm.getCodingForm_DeleteButton(codingformSG).waitAndClick(10);
		baseClass.VerifyErrorMessage("A coding form configured as default coding form for a security group or an assignment cannot be deleted.");
		baseClass.passedStep("Error message verified sucessfully when the coding form assigned to security group as expected");
		codingForm.assignCodingFormToSG(Input.codeFormName);		
		// Session search to doc view to create assignment
		baseClass.selectproject();
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignPage.assignmentCreation(assignmentName, assignCf);
		assignPage.assignmentDistributingToReviewer();
		// logout
		loginPage.logout();
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		// Assignment Selection
		assignPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		baseClass.stepInfo("Navigated to doc view page");
		baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
		codingForm.selectObjectsInPreviewBox("Responsive").waitAndClick(15);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.getCodingForm_Search().SendKeys(assignCf);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCodingForm_DeleteButton(assignCf));
		codingForm.getCodingForm_DeleteButton(assignCf).waitAndClick(10);
		baseClass.VerifyErrorMessage(
				"A coding form configured as default coding form for a security group or an assignment cannot be deleted.");
		baseClass.passedStep("Error message verified sucessfully when coding form assigned to an assignment as expected");
		//delete codingform
	    codingForm.deleteCodingForm(codingformSG, codingformSG);
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

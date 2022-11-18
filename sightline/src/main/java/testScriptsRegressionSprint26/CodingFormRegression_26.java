package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class CodingFormRegression_26 {

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

package testScriptsRegressionSprint28;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import automationLibrary.ElementCollection;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingForm_Regression28 {
	
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

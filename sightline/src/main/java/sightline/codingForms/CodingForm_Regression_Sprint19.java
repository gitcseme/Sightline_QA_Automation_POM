package sightline.codingForms;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import views.html.helper.input;

public class CodingForm_Regression_Sprint19 {

	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	DocExplorerPage docExplorerPage;

	List<String> cfName = null;
	String codingform = "CFA" + Utility.dynamicNameAppender();
	String cfTwo = "CFB" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	@DataProvider(name = "rmuRev")
	public Object[][] rmuRev() {
		Object[][] users = { { "rmu", Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ "rev", Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that we have instructional text present in "Sort Coding
	 *              Form Order" pop-up page (UI).
	 */
	@Test(description = "RPMXCON-65465", enabled = true, groups = { "regression" })
	public void verifyHamBurgerIcon() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-65465");
		baseClass.stepInfo(
				"Verify that we have instructional text present in \"Sort Coding Form Order\" pop-up page (UI).");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		docExplorerPage = new DocExplorerPage(driver);
		savedSearch = new SavedSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Navigate to manage coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		boolean setCF = codingForm.getSetCodingFormToSG().Enabled();
		softAssertion.assertTrue(setCF);
		boolean showHide = codingForm.getShowHide().Enabled();
		softAssertion.assertTrue(showHide);
		baseClass.stepInfo("Set security group coding form and show/hide are present and ist in enable state");

		// click on set security group
		List<String>allCfName=new LinkedList<String>();
		// Navigate to manage coding form
		for (int i = 0; i <15; i++) {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.	addNewCodingFormButton();
			String name=codingForm.passingCodingFormName("ZCF" + Utility.dynamicNameAppender());
			allCfName.add(name);
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
		}
		codingForm.checkingBelow15CFCheckboxForSG();
		baseClass.waitTime(1);
		codingForm.getSelectCodeFormRadioBtn(Input.codeFormName).Click();
		baseClass.waitTime(1);
		codingForm.sortOrderNxtBtn().ScrollTo();
		codingForm.sortOrderNxtBtn().Click();
//		baseClass.waitForElement(codingForm.getStep2CfPopUp());
//		boolean flagPopup2 = codingForm.getStep2CfPopUp().isElementAvailable(2);
//		softAssertion.assertTrue(flagPopup2);
		baseClass.stepInfo("Step 02: Sort Coding Form Order");
		List<String> actual = new ArrayList<String>();
		List<WebElement> beforeDrag = codingForm.sortOrderHamBurger().FindWebElements();
		for (WebElement webElement : beforeDrag) {
			actual.add(webElement.getText().toString());
		}
		String frst = beforeDrag.get(2).getText();
		// interchanging the cf using hamburger icon
		Actions actions = new Actions(driver.getWebDriver());
		// Drag and Drop to x,y
		actions.clickAndHold(codingForm.getHamBurgerDrag(frst).getWebElement());
		actions.moveToElement(codingForm.getHamBurgerDrag(frst).getWebElement(), -10, 10);
		actions.moveToElement(codingForm.getHamBurgerDrag(frst).getWebElement(), -10, 30);
		actions.moveToElement(codingForm.getHamBurgerDrag(frst).getWebElement(), 5, 27).build().perform();
		actions.release();
		actions.build().perform();

		baseClass.waitTime(2);
		List<String> expected = new ArrayList<String>();
		List<WebElement> afterDrag = codingForm.sortOrderHamBurger().FindWebElements();
		for (WebElement webElement : afterDrag) {
			expected.add(webElement.getText().toString());
		}
		softAssertion.assertNotEquals(actual, expected);
		softAssertion.assertAll();
		// deleting the cf
		for (String cfDelete : allCfName) {
			codingForm.deleteCodingForm(cfDelete, cfDelete);
		}
		baseClass.passedStep("Using hamburger icon we can able to interchange the cf as per needed");
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that we have instructional text present in "Add/remove
	 *              coding form in this security group" pop-up page (UI).
	 */
	@Test(description = "RPMXCON-65464", enabled = true, groups = { "regression" })
	public void verifyConfigureUpto15Cf() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-65464");
		baseClass.stepInfo(
				"Verify that we have instructional text present in \"Add/remove coding form in this security group\" pop-up page (UI).");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		docExplorerPage = new DocExplorerPage(driver);
		savedSearch = new SavedSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		List<String>allCfName=new LinkedList<String>();
		// Navigate to manage coding form
		for (int i = 0; i <15; i++) {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.	addNewCodingFormButton();
			String name=codingForm.passingCodingFormName("ZCF" + Utility.dynamicNameAppender());
			allCfName.add(name);
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
		}
		// Navigate to manage coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		boolean setCF = codingForm.getSetCodingFormToSG().Enabled();
		softAssertion.assertTrue(setCF);
		boolean showHide = codingForm.getShowHide().Enabled();
		softAssertion.assertTrue(showHide);
		baseClass.stepInfo("Set security group coding form and show/hide are present and ist in enable state");

		// click on set security group
		codingForm.configureBelow15Cf(Input.codingFormName);
		codingForm.makingDefaultCfToSg(Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.validatingDefaultSgFromManageScreen(Input.codingFormName);
		softAssertion.assertAll();
		// deleting the cf
		for (String cfDelete : allCfName) {
			codingForm.deleteCodingForm(cfDelete, cfDelete);
		}
		baseClass.passedStep("User can able to configure only 15 cf in set security group coding form");
		baseClass.passedStep("Manage coding form screen default cf displayed");
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that when user clicks on "Next Stage Sort Order" button
	 *              from the Add/remove coding form pop-up after selecting 15 or
	 *              less than 15 coding form and keeping any one as default user
	 *              should go to Next page without any error.
	 */
	@Test(description = "RPMXCON-64892", enabled = true, groups = { "regression" })
	public void verifyAfterConfigureWithoutError() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-64892");
		baseClass.stepInfo("Verify that when user clicks on \"Next Stage Sort Order\" button "
				+ "from the Add/remove coding form pop-up after selecting 15 or less "
				+ "than 15 coding form and keeping any one as default user should go "
				+ "to Next page without any error.");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		docExplorerPage = new DocExplorerPage(driver);
		savedSearch = new SavedSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		List<String>allCfName=new LinkedList<String>();
		// Navigate to manage coding form
		for (int i = 0; i <15; i++) {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.	addNewCodingFormButton();
			String name=codingForm.passingCodingFormName("ZCF" + Utility.dynamicNameAppender());
			allCfName.add(name);
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
		}
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		boolean setCF = codingForm.getSetCodingFormToSG().Enabled();
		softAssertion.assertTrue(setCF);
		boolean showHide = codingForm.getShowHide().Enabled();
		softAssertion.assertTrue(showHide);
		baseClass.stepInfo("Set security group coding form and show/hide are present and ist in enable state");

		// click on set security group
		codingForm.configureBelow15Cf(Input.codingFormName);
		codingForm.makingDefaultCfToSg(Input.codingFormName);
		baseClass.stepInfo("user navigated without any error when configured with less that 15 cf");
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.validatingDefaultSgFromManageScreen(Input.codingFormName);
		baseClass.passedStep("user navigated without any error when configured with less that 15 cf");
		softAssertion.assertAll();
		// deleting the cf
		for (String cfDelete : allCfName) {
			codingForm.deleteCodingForm(cfDelete, cfDelete);	
		}
		
		
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that the check box and radio button present under
	 *              "CODING FORM NAME" and "SET AS DEFAULT (REQUIRED)" column in
	 *              "Add/remove coding form in this security group" coding form
	 *              pop-up can be checked and Uncheck.
	 */
	@Test(description = "RPMXCON-64673", enabled = true, groups = { "regression" })
	public void verifyCheckBoxAndRadioOption() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-64673");
		baseClass.stepInfo("Verify that the check box and radio button present under \"CODING FORM NAME\""
				+ " and \"SET AS DEFAULT (REQUIRED)\" column in \"Add/remove coding form in"
				+ " this security group\" coding form pop-up can be checked and Uncheck.");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		docExplorerPage = new DocExplorerPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Navigate to manage coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		boolean setCF = codingForm.getSetCodingFormToSG().Enabled();
		softAssertion.assertTrue(setCF);
		boolean showHide = codingForm.getShowHide().Enabled();
		softAssertion.assertTrue(showHide);
		baseClass.stepInfo("Set security group coding form and show/hide are present and ist in enable state");

		// click on set security group
		baseClass.waitForElement(codingForm.getSetCFButton());
		codingForm.getSetCFButton().ScrollTo();
		codingForm.getSetCFButton().waitAndClick(10);
		baseClass.waitForElement(codingForm.getStep1CfPopUp());
		boolean flagPopup1 = codingForm.getStep1CfPopUp().isElementAvailable(2);
		baseClass.stepInfo("Step 01 : Add / Remove Coding Forms in this Security Group");
		softAssertion.assertTrue(flagPopup1);
		codingForm.getPopUpCheckBox().waitAndClick(5);
		baseClass.waitForElement(codingForm.getPopUpCheckBox());
		boolean checkbox=codingForm.getPopUpCheckBox().GetAttribute("class").contains("checkbox");
		softAssertion.assertTrue(checkbox);
		baseClass.stepInfo("Coding Form name diaplayed at top checkbox in popup");
		int unCheck=codingForm.getCfUnChecBoxUsingSize().size();
		System.out.println(unCheck);
		baseClass.passedStep("user navigated without any error when configured with less that 15 cf");
		System.out.println(codingForm.getSetDefaultSG().getText().trim());
		boolean sg=codingForm.getSetDefaultSG().getText().trim().contains("SET AS DEFAULT (REQUIRED)");
		softAssertion.assertTrue(sg);
		baseClass.stepInfo("Set as default(Required) in popup window");
		baseClass.waitForElement(codingForm.getVerifyRadioBtn(Input.codeFormName));
		boolean radio=codingForm.getVerifyRadioBtn(Input.codeFormName).GetAttribute("class").contains("radio");
		softAssertion.assertTrue(radio);
		baseClass.passedStep("Radio btn option available in below set as default");
		softAssertion.assertAll();
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

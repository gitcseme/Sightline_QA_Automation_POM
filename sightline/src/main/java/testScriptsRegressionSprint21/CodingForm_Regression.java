package testScriptsRegressionSprint21;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
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

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

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

public class CodingForm_Regression {

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

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify the character limit for the additional field of Radio
	 *              Group and Check Group
	 */
	@Test(description = "RPMXCON-54303", enabled = true, groups = { "regression" })
	public void verifyCharacterLimit() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54303");
		baseClass.stepInfo("Verify the character limit for the additional field of Radio Group and Check Group");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		
		// passing 500 character for radio instrument
		boolean radio=codingForm.getCodingForm_HelpMsg(0).isElementAvailable(2);
		softAssertion.assertTrue(radio);
		baseClass.stepInfo("Instrumental text present for radio group");
		String radioText=baseClass.passingCharacterBasedOnSize(500);
		codingForm.getCodingForm_HelpMsg(0).SendKeys(radioText);
		String radioOutput=codingForm.getCodingForm_HelpMsg(0).GetAttribute("value");
		int size=radioOutput.length();
		softAssertion.assertEquals(Integer.toString(size), "500");
		baseClass.passedStep("500 character are able to pass inside radio instrument text box");
		
		// passing 500 character for check instrument
		boolean check=codingForm.getCodingForm_HelpMsg(1).isElementAvailable(2);
		softAssertion.assertTrue(check);
		baseClass.stepInfo("Instrumental text present for check group");
		String checkText=baseClass.passingCharacterBasedOnSize(500);
		codingForm.getCodingForm_HelpMsg(1).SendKeys(checkText);
		String checkOutput=codingForm.getCodingForm_HelpMsg(1).GetAttribute("value");
		int sizeTwo=checkOutput.length();
		softAssertion.assertEquals(Integer.toString(sizeTwo), "500");
		baseClass.passedStep("500 character are able to pass inside check instrument text box");
		// Assertion
		softAssertion.assertAll();
		loginPage.logout();
	}

	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that additional field to both Radio Groups and Check Groups 
	 *                should appear in the Coding Form
	 */
	@Test(description = "RPMXCON-54302", enabled = true, groups = { "regression" })
	public void verifyInstrumentText() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54302");
		baseClass.stepInfo("Verify that additional field to both Radio Groups and Check Groups "
				+ "should appear in the Coding Form");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		
		// validating the additional fields
		boolean radio=codingForm.getCodingForm_HelpMsg(0).isElementAvailable(2);
		softAssertion.assertTrue(radio);
		baseClass.stepInfo("Instrumental text present for radio group");
		boolean check=codingForm.getCodingForm_HelpMsg(1).isElementAvailable(2);
		softAssertion.assertTrue(check);
		baseClass.stepInfo("Instrumental text present for check group");
		baseClass.passedStep("Additional fields are displayed for both radio and check group");
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

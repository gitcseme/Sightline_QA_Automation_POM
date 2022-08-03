package testScriptsRegressionSprint18;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebElement;
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

public class DocviewCodingForm_Regression {
	
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
	TagsAndFoldersPage  tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	
	String codingform = "CFTag"+Utility.dynamicNameAppender();
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
		Object[][] users = { 
				{ "rmu",Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{"rev",Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that when user navigates from one doc to another doc, 
	 *                irrespective of which coding form the was on for the previous document, 
	 *                for the next doc, it always shows the default coding form set.
	 */
	@Test(description = "RPMXCON-65632",enabled = true,dataProvider = "rmuRev", groups = { "regression" })
	public void validationForDefaultCfWhenNavigates(String roll,String username, String password, String fullName) throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-65632");
	    baseClass.stepInfo("Verify that when user navigates from one doc to another doc, "
	    		+ "irrespective of which coding form the was on for the previous document, "
	    		+ "for the next doc, it always shows the default coding form set.");
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch=new SessionSearch(driver);
	    
	    loginPage.loginToSightLine(username, password);
		UtilityLog.info("Logged in as User: " + username);
		List<String> cfName=null;
		// creating codingform
		if (roll=="rmu") {
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    codingForm.createWithOneMetaData(codingform);
		cfName=codingForm.checkingBelow15CFCheckboxForSG();
		System.out.println(cfName);
		codingForm.makingDefaultCfToSg(Input.codingFormName);
		codingForm.validatingDefaultSgFromManageScreen(Input.codingFormName);
		}
		// navigating to docview page
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview page");
		
		// clicking cf panel dropdown
		List<String>expected=new ArrayList<String>();
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
		docViewPage.getDocViewDrpDwnCf().waitAndClick(5);
		List<WebElement> cfDocviewPnael=docViewPage.getDocViewDrpDwnCf().selectFromDropdown().getOptions();
		for (WebElement webElement : cfDocviewPnael) {
			expected.add(webElement.getText().trim().toString());
		}
		baseClass.stepInfo("Assigned coding form to sg are displaying in cf panel dropdown");
		// selecting different codingform
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
		docViewPage.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText(codingform);
		// navigating to otherdocument
		baseClass.waitForElement(docViewPage.getClickDocviewID(2));
		docViewPage.getClickDocviewID(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		// validating when navigating to other docs default cf should assign
		String currentDRp=baseClass.getCurrentDropdownValue(docViewPage.getDocViewDrpDwnCf());
		softAssertion.assertEquals(currentDRp, Input.codeFormName);
		baseClass.passedStep("Default coding form assigned in cf panel,when navigate to other docs");
		
		// validating for child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		docViewPage.switchToNewWindow(2);
		// selecting different codingform
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCfChild());
		docViewPage.getDocViewDrpDwnCfChild().selectFromDropdown().selectByVisibleText(codingform);
		// navigating to otherdocument
		docViewPage.switchToNewWindow(1);
		baseClass.waitForElement(docViewPage.getClickDocviewID(2));
		docViewPage.getClickDocviewID(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.switchToNewWindow(2);
		// validating when navigating to other docs default cf should assign
		String currentDRpChild = baseClass.getCurrentDropdownValue(docViewPage.getDocViewDrpDwnCfChild());
		softAssertion.assertEquals(currentDRpChild, Input.codeFormName);
		baseClass.passedStep("Default coding form assigned in cf panel,when navigate to other docs");
        docViewPage.closeWindow(1);
        docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
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

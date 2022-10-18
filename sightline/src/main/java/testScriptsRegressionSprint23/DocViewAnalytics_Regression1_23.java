package testScriptsRegressionSprint23;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAnalytics_Regression1_23 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

//		Input in = new Input();
//		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	
	
	
	/**
	 * Author :Iyappan date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63780 Verify that when Copy menu is selected from doc view and
	 * 'View Document' action selects from Analytics Panel then selected
	 * panels/menus should be retain
	 * 
	 * 
	 */
	@Test(description = "RPMXCON-63780",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyCopyMenuSelectedDocViewViewDocSelectFromAnalyticalPanel() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63780");
		baseClass.stepInfo("Verify that when Copy menu is selected from doc view and 'View Document' action selects from Analytics Panel then selected panels/menus should be retain");
		DocViewPage docView = new DocViewPage(driver);
		String docid = "T2541D";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		docexp = new DocExplorerPage(driver);
		DocListPage doclist = new DocListPage(driver);

		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys("Text");
		doclist.getApplyFilter().waitAndClick(10);
		
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		driver.waitForPageToBeReady();
		if (doclist.getYesAllPageDocs().isDisplayed()) {
            doclist.getYesAllPageDocs().waitAndClick(5);
            doclist.getPopUpOkBtn().waitAndClick(5);           
        }
        baseClass.waitTime(5);
		
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		driver.Navigate().refresh();
		docView.ScrollAndSelectDocument(docid);
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.waitForPageToBeReady();
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		
		//verify Doc Selected in analytical panel view doc
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupe_Doc(1));
		docView.getDocView_Analytics_NearDupe_Doc(1).waitAndClick(5);
		baseClass.stepInfo("Document is selected in analytical panel");
		baseClass.waitForElement(docView.getDocView_ChildWindow_ActionButton());
		docView.getDocView_ChildWindow_ActionButton().waitAndClick(15);
		baseClass.waitForElement(docView.getViewDocumentNearDupe());
		docView.getViewDocumentNearDupe().waitAndClick(10);
		baseClass.stepInfo("Select action as View Document in docview page");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.waitForElement(docView.getCopyPasteIcon());
		if (docView.getCopyPasteIcon().Enabled()) {
			baseClass.passedStep("'Copy' menu is retained in selected document");
			
		}else {
			baseClass.failedStep("not retained");
		}
		
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.waitForPageToBeReady();
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		
	}
	



	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}

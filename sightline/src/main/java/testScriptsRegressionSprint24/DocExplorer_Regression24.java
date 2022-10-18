package testScriptsRegressionSprint24;

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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression24 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);

	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:17/10/2022 RPMXCON-54735
	 * @throws Exception
	 * @Description Verify that “EmailReceipients” Column header Filter with CJK characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54735", enabled = true, groups = { "regression" })
	public void verifyEmailReceipientsColumnHeaderFilterWithCJKChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54735");
		baseClass.stepInfo(
				"Verify that “EmailReceipients” Column header Filter with CJK characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] cjkChar = {"好","人","良い","一个","一個"};
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);
		
		//verify EmailRecipient names in CJK Chars
		docexp.verifyEmailRecipientValuesInDocExp(cjkChar);
		baseClass.passedStep("Verify that “EmailReceipients” Column header Filter with CJK characters is working Successfully on Doc Explorer list");
		
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:17/10/2022 RPMXCON-54732
	 * @throws Exception
	 * @Description Verify that “EmailSubject/Filename” Column header Filter with CJK characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54732", enabled = true, groups = { "regression" })
	public void verifyEmailSubjectFilenameColumnHeaderFilterWithCJKChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54732");
		baseClass.stepInfo(
				"Verify that “EmailSubject/Filename” Column header Filter with CJK characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver); 
		String[] cjkChar = {"让","我","打","电","话","给","你","延","长","石","油","集","团"};
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);
		
		//verify EmailRecipient names in CJK Chars
		docexp.verifyEmailSubjectFilenameValuesInDocExp(cjkChar);
		baseClass.passedStep("Verify that “EmailSubject/Filename” Column header Filter with CJK characters is working Successfully on Doc Explorer list");
		
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani  date: 17/10/2022 TestCase Id:RPMXCON-54633
	 * Description :Verify that user can select a single document from List view, and select action as Bulk Assign from Actions drop down to Assign document to new assignment.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54633",enabled = true, groups = { "regression" })
	public void verifySingleDocumentBulkAssignInListViewDoxExplorer() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54633");
		baseClass.stepInfo("Verify that user can select a single document from List view, and select action as Bulk Assign from Actions drop down to Assign document to new assignment");
		String assignName = "assign"+Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);

		//Login As RMU 
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulk assign action");
		docExplorer.selectDocument(1);
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");
		
		assignment.assignwithSamplemethod(assignName,"Count of Selected Docs","1");
		baseClass.passedStep("User can select single doc and bulk assign to new assignment");
		
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani  date: 17/10/2022 TestCase Id:RPMXCON-54634
	 * Description :Verify that user can select multiple documents from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54634",enabled = true, groups = { "regression" })
	public void verifyMultipleDocumentBulkAssignInListViewDoxExplorer() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54634");
		baseClass.stepInfo("Verify that user can select multiple documents from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment");
		String assignName = "assign"+Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);
		
		//Login As RMU 
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulk assign action");
		docExplorer.selectDocument(5);
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");
		
		assignment.assignwithSamplemethod(assignName,"Count of Selected Docs","5");
		baseClass.passedStep("User can select single doc and bulk assign to existing assignment");
		loginPage.logout();
	
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
}

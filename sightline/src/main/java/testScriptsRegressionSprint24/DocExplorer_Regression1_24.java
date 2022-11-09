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
import org.testng.annotations.DataProvider;
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
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression1_24 {

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
	 * Author :Vijaya.Rani date: 25/10/2022 TestCase Id:RPMXCON-54624 Description
	 * :Verify that user can select all documents from all pages from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54624", enabled = true, groups = { "regression" })
	public void verifySelectAllDocumentBulkAssignInExistingAssignFromListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54624");
		baseClass.stepInfo(
				"Verify that user can select all documents from all pages from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment.");
		String assignName = "assign" + Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a All document and select bulk assign action");
		docExplorer.SelectingAllDocuments("No");
		driver.scrollingToBottomofAPage();
		String count = docExplorer.getDocExp_DocumentList_info().getText().toString();
		String []Doc=count.split(" ");
		String ListViewCount=Doc[3];
		System.out.println(ListViewCount);
		driver.scrollPageToTop();
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");

		assignment.assignwithSamplemethod(assignName, "Count of Selected Docs",ListViewCount);
		baseClass.passedStep("User can select All Document and bulk assign to existing assignment");
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani date: 25/10/2022 TestCase Id:RPMXCON-54637 
	 * Description:Verify that user should be able to remove documents from Folder from Doc Explorer.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54637", enabled = true, groups = { "regression" })
	public void verifyRevomeDocumentsFromFolderDocExplorerPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54637");
		baseClass.stepInfo(
				"Verify that user should be able to remove documents from Folder from Doc Explorer.");
		TagsAndFoldersPage tagAndFolder=new TagsAndFoldersPage(driver);
		String folderName="folder"+ Utility.dynamicNameAppender();
		sessionSearch = new SessionSearch(driver);
	
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		tagAndFolder.navigateToTagsAndFolderPage();
		tagAndFolder.CreateFolderInRMU(folderName);
		
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulkFolder in unfolder action");
		docExplorer.selectDocument(10);
		docExplorer.bulkFolderExistingInUnFolderDocs(folderName);
		baseClass.passedStep(
				"Records saved successfully  Documents is unfoldered from the selected folder Successfully");
		
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagAndFolder.verifyFolderDocCount(folderName, 0);

		loginPage.logout();
		
	}
	
	/**
	 * Author :Vijaya.Rani date: 25/10/2022 TestCase Id:RPMXCON-54640 Description
	 * :Verify that user should be able to unassign documents from assignment(s)
	 * from doc explorer page.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54640", enabled = true, groups = { "regression" })
	public void verifyUnAssignDocsFromAssignmentFromDocExplorerPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54640");
		baseClass.stepInfo(
				"Verify that user should be able to unassign documents from assignment(s) from doc explorer page.");
		String assignName = "assign" + Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SavedSearch saveSearch =new SavedSearch(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulk unassign action");
		docExplorer.selectDocument(10);
		docExplorer.docExp_BulkAssign();
		assignment.UnassignDocsfromExistingAssgn(assignName);
		int initialBg = baseClass.initialBgCount();

		// verify Notification 
		baseClass.checkNotificationCount(initialBg,1);
		baseClass.waitForElement(baseClass.getBullHornIcon());
		baseClass.getBullHornIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		String actStatus = saveSearch.getNotificationStatus(1).getText();
		System.out.println(actStatus);
		String expStatus = "Your Bulkaction-Unassign with Bulkaction-Unassign Id";

		if (actStatus.contains(expStatus) && actStatus.contains("COMPLETED")) {
			baseClass.passedStep("selected documents is unassigned from the selected assignment");
		} else {
			baseClass.failedStep("selected documents is not unassigned from the selected assignmen");
		}
		baseClass.passedStep(
				"Verified - as an RMU login into the Application, When user applying Unassign documents from Assignment,"
						+ " user will be able to see the background task in notification window");
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
	
	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
}

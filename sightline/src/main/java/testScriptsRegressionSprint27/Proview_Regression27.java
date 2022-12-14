package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Proview_Regression27 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch session;
	BaseClass baseClass;
	SoftAssert softAssert;
	Categorization categorize;
	TagsAndFoldersPage tagsAndFolder;
	AssignmentsPage assignPage;
	SavedSearch savedSearch;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}
	
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		session = new SessionSearch(driver);
	}
	
	/**
	 * Author :Arunkumar date: 01/12/2022 TestCase Id:RPMXCON-54118
	 * Description :To verify that RMU can view the selected Assignments.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54118",enabled = true, groups = { "regression" })
	public void verifyRmuViewAssignments() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54118");
		baseClass.stepInfo("verify that RMU can view the selected Assignments");
		String assignmentName = "assign"+Utility.dynamicNameAppender();
		
		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("perform search and action as bulk assign");
		int purehit=session.basicContentSearch(Input.testData1);
		session.bulkAssign();
		baseClass.stepInfo("create assignment,finalize and save");
		assignPage = new AssignmentsPage(driver);
		assignPage.FinalizeAssignmentAfterBulkAssign();
		assignPage.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		assignPage.getAssignmentSaveButton().waitAndClick(10);
		baseClass.waitForElement(assignPage.getNumberOfAssignmentsToBeShown());
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify redirected url after saving assignment");
		baseClass.verifyUrlLanding(Input.url + "en-us/Assignment/ManageAssignment", "Redirected to manage-assignment page", 
				"Not redirected to assignment page");
		baseClass.stepInfo("Edit assignment and verify details available");
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.waitForElement(assignPage.getAssignmentName());
		String actual =assignPage.getAssignmentName().getText();
		baseClass.compareTextViaContains(assignmentName, actual, "Added assignment name displayed", 
				"Added assignment name detail mismatched");
		baseClass.waitForElement(assignPage.getDistributeTab());
        assignPage.getDistributeTab().waitAndClick(10);
        baseClass.waitForElement(assignPage.getEditAggn_Distribute_Totaldoc());
        int actualCount = Integer.parseInt(assignPage.getEditAggn_Distribute_Totaldoc().getText());
        assignPage.getAssignmentSaveButton().waitAndClick(10);
        baseClass.digitCompareEquals(purehit, actualCount, "assigned docs available in assignment", 
        		"assigned docs not available in assignment");
        baseClass.passedStep("All assignment details displayed");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/12/2022 TestCase Id:RPMXCON-54119
	 * Description :To verify that RMU can view the selected Save Search Result.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54119",enabled = true, groups = { "regression" })
	public void verifyRmuViewSavedSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54119");
		baseClass.stepInfo("verify that RMU can view the selected Save Search Result.");
		String SearchName = "search"+Utility.dynamicNameAppender();
		
		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("perform search");
		int purehit=session.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Enter search details and save");
		session.saveSearch(SearchName);
		baseClass.stepInfo("navigate to saved search page");
		savedSearch = new SavedSearch(driver);
		savedSearch.navigateToSavedSearchPage();
		baseClass.waitForElement(savedSearch.getSavedSearch_ApplyFilterButton());
		baseClass.ValidateElement_Presence(savedSearch.getSelectSavedSearch(SearchName), "saved search");
		baseClass.passedStep("last saved search displayed");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/12/2022 TestCase Id:RPMXCON-54120
	 * Description :To verify that on clicking on 'Cancel' button, folders should not be displayed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54120",enabled = true, groups = { "regression" })
	public void verifyCancelBtnOnFolderCreation() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54120");
		baseClass.stepInfo("verify that on clicking on 'Cancel' button, folders should not be displayed.");
		String folderName = "folder"+Utility.dynamicNameAppender();
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to manage-Tags and folder");
		tagsAndFolder = new TagsAndFoldersPage(driver);
		tagsAndFolder.navigateToTagsAndFolderPage();
		baseClass.stepInfo("Click on folder tab and verify");
		tagsAndFolder.verifyExistingFoldersAvailabilityUnderRoot();
		baseClass.stepInfo("select new folder option from action dropdown and verify");
		tagsAndFolder.verifyNewFolderCreationPopup();
		baseClass.stepInfo("Enter folder name details and click on cancel");
		baseClass.waitForElement(tagsAndFolder.getFolderName());
		tagsAndFolder.getFolderName().SendKeys(folderName);
		baseClass.waitForElement(tagsAndFolder.getbtnFolderCancel());
		tagsAndFolder.getbtnFolderCancel().Click();
		baseClass.stepInfo("verify cancelled folder availability");
		baseClass.ValidateElement_Absence(tagsAndFolder.SelectFolderCheckbox(folderName), "cancelled folder");
		baseClass.passedStep("new folder not displayed on folder tree");
		loginPage.logout();
	}
	/**
	 * @author Brundha.T Test case id : RPMXCON-54257
	 * @Description :Verify that Categorization (Training Set : Folder) is working
	 *              correctly and properly
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54257", enabled = true, groups = { "regression" })
	public void verifyingFolderSetInProview() throws InterruptedException {

		baseClass.stepInfo("RPMXC0N-54257 Proview");
		baseClass.stepInfo("Verify that Categorization (Training Set : Folder) is working correctly and properly");

		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		String[] UserName = { Input.pa1userName, Input.rmu1userName };
		String[] Password = { Input.pa1password, Input.rmu1password };

		for (int i = 0; i < UserName.length; i++) {
			String folderName = "Folder" + Utility.dynamicNameAppender();
			// Login as User
			loginPage.loginToSightLine(UserName[i], Password[i]);
			baseClass.stepInfo("Logged in As " + UserName[i]);

			baseClass.stepInfo("Creating new bulkfolder");
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolder(folderName);

			baseClass.stepInfo("Navigating to categorize page");
			categorize.navigateToCategorizePage();

			baseClass.stepInfo("Selecting training set and doc to be analyzed");
			categorize.fillingTrainingSetSection("Folder", folderName, null, null);
			categorize.fillingStep2CorpusTab("folder", folderName, null, true);

			final BaseClass bc = new BaseClass(driver);
			final int Bgcount = bc.initialBgCount();
			System.out.println(Bgcount);

			driver.waitForPageToBeReady();
			baseClass.stepInfo("Set cohesion level as 50%");
			categorize.getCohesionLevel().SendKeys("50");

			baseClass.stepInfo("Run Categorize and click on 'NO'");
			categorize.runCategorization("No");
			bc.checkNotificationCount(Bgcount, 1);
			bc.getBackGroudTaskIcon().waitAndClick(10);
			categorize.getProviewNotification().waitAndClick(10);
			baseClass.waitTime(3);
			String DocCount = categorize.getResultCount().getText();
			String[] Document = DocCount.split(" ");
			String ResultCoount = Document[1];
			System.out.println(ResultCoount);
			
			String ResultCount = categorize.getDocCount().getText();
			System.out.println(ResultCount);
			baseClass.stepInfo("verifying cohesion level");
			if (Integer.valueOf(ResultCoount) == 50) {
				baseClass.passedStep("Categorize page is with selected cohesion level");
			} else {
				baseClass.failedStep("Categorize page not with selected cohesion level");
			}
			categorize.ViewInDocLIst();

			baseClass.waitTime(3);
			baseClass.stepInfo("verifying document count in doclist page");
			DocListPage doc = new DocListPage(driver);
			baseClass.waitForElement(doc.getTableFooterDocListCount());
			String DocListCount = doc.getTableFooterDocListCount().getText();
			String[] doccount = DocListCount.split(" ");
			String DocumentCount = doccount[5];

			baseClass.digitCompareEquals(Integer.valueOf(ResultCount), Integer.valueOf(DocumentCount),
					"Document count is displayed as expected", "Document count is not displayed");

			loginPage.logout();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {

			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@AfterClass(alwaysRun = true)
	public void close() {

		System.out.println("Executed :Proview-sprint27 ");
	}
}

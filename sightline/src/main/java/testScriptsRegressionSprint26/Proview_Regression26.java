package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

public class Proview_Regression26 {
	Driver driver;
	LoginPage loginPage;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	Categorization Categorization;
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
		Categorization = new Categorization(driver);

	}

	
	/**
	 * @author Brundha.T TestCaseId : RPMXCON-54136
	 * @throws Exception
	 * @DescriptionTo verify that documents found should be zero if user clicks on
	 *                Manage->Categorize.
	 */
	@Test(description = "RPMXCON-54136", enabled = true, groups = { "regression" })
	public void verifyingZeroInProviewPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54136");
		baseClass.stepInfo("To verify that documents found should be zero if user clicks on Manage->Categorize.");


		String[] Username = { Input.pa1userName, Input.rmu1userName };
		String[] Password = { Input.pa1password, Input.rmu1password };

		for (int i = 0; i < Username.length; i++) {
			loginPage.loginToSightLine(Username[i], Password[i]);
			baseClass.stepInfo("Logged in As " + Username[i]);

			Categorization Categorization = new Categorization(driver);

			baseClass.stepInfo("Navigating to Categorize page");
			Categorization.navigateToCategorizePage();

			baseClass.stepInfo("verifying document count is zero");
			String DocCount = Categorization.getResultCount().getText();
			String[] Document = DocCount.split(" ");
			String ResultCoount = Document[1];
			System.out.println(ResultCoount);
			if (Integer.valueOf(ResultCoount) == 0) {
				baseClass.passedStep("Documents Found is zero as expected");
			} else {
				baseClass.failedStep("Document count is not Zero");
			}
			loginPage.logout();
		}
	}

	/**
	 * @author Brundha.T TestCaseId : RPMXCON-54124
	 * @throws Exception
	 * @DescriptionTo verify that project Admin can view all the existing tags.
	 */
	@Test(description = "RPMXCON-54124", enabled = true, groups = { "regression" })
	public void verifyingExistingTags() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54124");
		baseClass.stepInfo("To verify that project Admin can view all the existing tags.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As " + Input.pa1userName + "");

		Categorization Categorization = new Categorization(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Navigating to Tags and folders page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		if (baseClass.getDefaultTag().isDisplayed()) {
			baseClass.getDefaultTag().waitAndClick(5);
		}
		baseClass.waitForElementCollection(baseClass.getAllTags());
		List<String> ListOfTagInTagsPage = baseClass.getAvailableListofElements(baseClass.getAllTags());
		baseClass.stepInfo("All the tags in Tags and folders page" + ListOfTagInTagsPage);
		System.out.println(ListOfTagInTagsPage);

		baseClass.stepInfo("Navigating to Categorize page");
		Categorization.navigateToCategorizePage();
		baseClass.waitForElement(Categorization.getSelectIdentifyByTags());
		Categorization.getSelectIdentifyByTags().waitAndClick(2);

		baseClass.waitForElementCollection(baseClass.getAllTags());
		List<String> ListOfTagInProviewPage = baseClass.availableListofElements(baseClass.getAllTags());
		System.out.println(ListOfTagInProviewPage);
		baseClass.stepInfo("All the tags in proview page" + ListOfTagInProviewPage);

		if (ListOfTagInTagsPage.equals(ListOfTagInProviewPage)) {
			baseClass.passedStep("all existing tags is displayed.");
		} else {
			baseClass.failedStep("all existing tags is not displayed.");
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha.T TestCaseId : RPMXCON-54122
	 * @throws Exception
	 * @DescriptionTo verify that RMU can remove the Assignment.
	 */
	@Test(description = "RPMXCON-54122", enabled = true, groups = { "regression" })
	public void verifyAssignmentIsRemoved() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54122");
		baseClass.stepInfo("To verify that RMU can remove the Assignment.");

		// Login As PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As " + Input.rmu1userName + "");

		String assignmentName = "assignmentName" + Utility.dynamicNameAppender();

		Categorization Categorization = new Categorization(driver);
		SessionSearch search = new SessionSearch(driver);
		AssignmentsPage Assign = new AssignmentsPage(driver);

		int PureHit = search.basicContentSearch(Input.testData1);
		search.bulkAssign();
		Assign.assignDocstoNewAssgn(assignmentName, Input.codeFormName, PureHit);

		baseClass.stepInfo("Navigating to Proview page");
		Categorization.navigateToCategorizePage();

		baseClass.stepInfo("filling Training set");
		Categorization.fillingTrainingSetSection("Assignment", assignmentName, null, null);

		Categorization.fillingStep2CorpusTab("Assignment", assignmentName, null, true);
		baseClass.ValidateElement_Presence(Categorization.getAssignedName(assignmentName), "Assignment name");
		baseClass.waitForElement(Categorization.getSelectedAnalyzedXBtn());
		Categorization.getSelectedAnalyzedXBtn().waitAndClick(5);
		baseClass.stepInfo("Clicled x icon in selected Assignment");

		driver.waitForPageToBeReady();
		if (!Categorization.getAssignedName(assignmentName).isElementAvailable(2)) {
			baseClass.passedStep("Assignment is removed");
		} else {
			baseClass.failedStep("Assignment is not removed");
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha.T Test case id : RPMXCON-54129
	 * @Description :To verify that after impersonating as Project Admin, system
	 *              admin can run the Proview.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54129", enabled = true, groups = { "regression" })
	public void verifyUserCanRemoveFolder() throws InterruptedException {

		baseClass.stepInfo("RPMXC0N-54129 Proview");
		baseClass.stepInfo("To verify that after impersonating as Project Admin, system admin can run the Proview.");

		String folderName = "Folder" + Utility.dynamicNameAppender();

		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login as User
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName + "");

		baseClass.stepInfo("Impersonating SA as PA");
		baseClass.impersonateSAtoPA();

		int Purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolder(folderName);

		baseClass.stepInfo("Selecting training set and doc to be analyzed");
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);
		categorize.fillingStep2CorpusTab("folder", folderName, null, true);
		categorize.runCategorization("Yes");
		String ResultCount = categorize.getDocCount().getText();
		System.out.println(ResultCount);

		baseClass.stepInfo("verifying result in proview page is displaying correctly");
		baseClass.digitCompareEquals(Purehit, Integer.valueOf(ResultCount),
				"Result is displayed correctly with docCount as" + ResultCount, "Selected Docs are not Displayed");

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
		System.out.println("**Executed Proview_Regression26**");
	}

}

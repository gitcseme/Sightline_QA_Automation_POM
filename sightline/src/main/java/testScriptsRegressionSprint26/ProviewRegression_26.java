package testScriptsRegressionSprint26;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProviewRegression_26 {
	Driver driver;
	LoginPage login;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssert;
	Categorization categorize;
	TagsAndFoldersPage tagsAndFolder;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}

	@DataProvider(name = "USERS")
	public Object[][] USERS() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	/**
	 * @Author Krishna Date:NA ModifyDate:NA RPMXCON-54117
	 * @Description : To verify that RMU can view the selected folders.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54117", enabled = true, groups = { "regression" })
	public void verifyRmuCanViewSelectedFolder() throws InterruptedException {

		base.stepInfo("RPMXC0N-54117  Proview");
		base.stepInfo("TTo verify that RMU can view the selected folders.");
		String folderName = "FOLDER" + Utility.dynamicNameAppender();
		SoftAssert softassert = new SoftAssert();
		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// basic Content search
		sessionSearch.basicContentSearch(Input.testData1);

		// perform Bulk Folder
		sessionSearch.bulkFolder(folderName);

		// select Folder in Corpus Section
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);
		categorize.fillingStep2CorpusTab("folder", folderName, null, true);
		driver.waitForPageToBeReady();
		String selectedFolder = categorize.getSelectedAnalyzedSub().getText();
		softassert.assertEquals(folderName, selectedFolder);
		base.stepInfo("Selected folder is displayed successfully");

		login.logout();
	}

	/**
	 * @Author Krishna Date:NA ModifyDate:NA RPMXCON-54121
	 * @Description : To verify that user can remove the folder.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54121", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyUserCanRemoveFolder(String username, String password, String userRole)
			throws InterruptedException {

		base.stepInfo("RPMXC0N-54121  Proview");
		base.stepInfo("To verify that user can remove the folder.");
		String folderName = "FOLDER" + Utility.dynamicNameAppender();
		SoftAssert softassert = new SoftAssert();
		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login as User
		login.loginToSightLine(username, password);
		base.stepInfo("Logged in as : " + userRole);

		// basic Content search
		sessionSearch.basicContentSearch(Input.testData1);

		// perform Bulk Folder
		sessionSearch.bulkFolder(folderName);
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("folder", folderName, null, true);
		driver.waitForPageToBeReady();
		String selectedFolder = categorize.getSelectedAnalyzedSub().getText();
		softassert.assertEquals(folderName, selectedFolder);
		base.stepInfo("Folder is selected in analyzed as expected");

		// verify selected data is removed
		base.waitForElement(categorize.getSelectedAnalyzedXBtn());
		categorize.getSelectedAnalyzedXBtn().waitAndClick(5);
		base.stepInfo("Clicled x icon in selected folder");
		driver.waitForPageToBeReady();
		base.ValidateElement_AbsenceReturn(categorize.getSelectedAnalyzedSub());
		base.passedStep("Data is removed successfully as expected");
		softassert.assertAll();
		login.logout();
	}
	
	/**
	 * @throws AWTException
	 * @Author Krishna Date:NA ModifyDate:NA RPMXCON-54116
	 * @Description : To verify that on clicking on 'Production Set' icon, all
	 *              existing sets should be displayed.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54116", enabled = true, groups = { "regression" })
	public void verifyProductionSetExistingSetDisplayed() throws InterruptedException, AWTException {

		base.stepInfo("RPMXC0N-54116  Proview");
		base.stepInfo("To verify that on clicking on 'Production Set' icon, all existing sets should be displayed.");
		String folderName = "FOLDER" + Utility.dynamicNameAppender();
		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// basic Content search
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolder(folderName);
		ProductionPage page = new ProductionPage(driver);
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.scrollingToBottomofAPage();
		base.waitForElementCollection(page.getProductionItem());
		List<String> actualprodPresent = base.availableListofElements(page.getProductionItem());
		base.stepInfo(actualprodPresent+"  production docs ");

		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);

		// select production sets
		categorize.selectTrainingSet("Analyze Select Production Sets");
		base.stepInfo("Analyze Select Production Sets  Results Sets Expanded");
		base.waitForElement(categorize.getProductionSelectionPopUp());
		categorize.getProductionSelectionPopUp().waitAndClick(5);

		base.waitForElementCollection(categorize.getProductionSets());
		List<String> folderPresentInCategorize = base.availableListofElements(categorize.getProductionSets());
		base.stepInfo(folderPresentInCategorize+"  production docs in analyze section");

		base.waitTime(5);
        for (int i = 0; i <=10; i++) {
            base.compareListWithOnlyOneString(folderPresentInCategorize, actualprodPresent.get(i),
                    actualprodPresent.get(i) + "All existing production is displayed in Analyze section.",
                    actualprodPresent.get(i)+"Not present");
 
        }


	}

	/**
	 * @Author Krishna Date:NA ModifyDate:NA RPMXCON-54133
	 * @Description : To verify that if system admin remove the 'Categorize' rights,
	 *              user cannot view the proview page.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54133", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyUserCanRemove(String username, String password, String userRole) throws Exception {

		base.stepInfo("RPMXC0N-54133  Proview");
		base.stepInfo(
				"To verify that if system admin remove the 'Categorize' rights, user cannot view the proview page.");
		Categorization categorize = new Categorization(driver);
		UserManagement user = new UserManagement(driver);
		String categorizetab = "Categorize";

		// Login As SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");
		user.getUserSelectedFunctionalyTabIsUncheck(username, Input.projectName, categorizetab);
		login.logout();

		// Login as
		login.loginToSightLine(username, password);
		base.stepInfo("Login as " + userRole);
		driver.waitForPageToBeReady();
		if (categorize.getCategorizeMenu().isDisplayed()) {
			base.failedStep("Categorize option is visible for the user.");
		} else {
			base.passedStep("Categorize option is not visible for the user as expected.");
		}
		login.logout();

		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.getUserSelectedFunctionalyTabIsUncheck(username, Input.projectName, categorizetab);
		login.logout();
	}
	
	/**
	 * @Author  Date:NA ModifyDate:NA RPMXCON-54135
	 * @Description : To verify that “Select Docs to be Analyzed“ frame enabled once
	 *              any data selected “Identify the Training Set” frame.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54135", enabled = true, groups = { "regression" })
	public void verifySelectDoxAnalyzedFrameEnabled() throws InterruptedException {

		base.stepInfo("RPMXC0N-54135  Proview");
		base.stepInfo(
				"To verify that “Select Docs to be Analyzed“ frame  enabled once any data selected “Identify the Training Set” frame.");
		String folderName = "FOLDER" + Utility.dynamicNameAppender();
		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softassert = new SoftAssert();
		String assign = "btnAssign";
		String prod = "btnProduction";
		String folder = "btnFolder";
		String savedsearch = "btnSavedSearch";

		// Login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// basic Content search
		sessionSearch.basicContentSearch(Input.testData1);

		// perform Bulk Folder
		sessionSearch.bulkFolder(folderName);
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);
		driver.waitForPageToBeReady();

		// select corpus to analyzed sets
		base.waitTime(5);
		categorize.getAnalyzeSelectFolders().waitAndClick(5);
		softassert.assertFalse(categorize.getAnalyzeSelectBtnDisabled(folder).isElementAvailable(5));
		categorize.selectTrainingSet("Analyze Select Assignments");
		softassert.assertFalse(categorize.getAnalyzeSelectBtnDisabled(assign).isElementAvailable(5));
		categorize.selectTrainingSet("Analyze Select Production Sets");
		softassert.assertFalse(categorize.getAnalyzeSelectBtnDisabled(prod).isElementAvailable(5));
		categorize.selectTrainingSet("Analyze Select Saved Search Results Sets");
		softassert.assertFalse(categorize.getAnalyzeSelectBtnDisabled(savedsearch).isElementAvailable(5));
		softassert.assertFalse(categorize.getRunBtnDisabled().isElementAvailable(5));
		base.passedStep("Selected Docs to be Analyzed frame has been enabled as expected");
		softassert.assertAll();

	}
	
	
	/**
	 * @Author  Date:NA ModifyDate:NA RPMXCON-54131
	 * @Description : To verify that 'Doc to be analyzed' should be display warning
	 *              message if training set is not selected.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54131", enabled = true, groups = { "regression" })
	public void verifyDocsToBeAnalyzedWarningMsg() throws InterruptedException {

		base.stepInfo("RPMXC0N-54131  Proview");
		base.stepInfo(
				"To verify that 'Doc to be analyzed' should be display warning message if training set is not selected.");
		Categorization categorize = new Categorization(driver);

		// Login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		categorize.navigateToCategorizePage();

		driver.scrollingToBottomofAPage();
		categorize.getGotoStep2().waitAndClick(5);
		base.stepInfo("To be analyzed is clicked as expected");
		boolean warningStatus = base.getWarningsMsgHeader().isElementAvailable(3);
		System.out.println(warningStatus);
		if (warningStatus == true) {
			base.VerifyWarningMessage("Please select a training set");
			base.passedStep("Warning message is displayed successfully");
			
		} else {
			base.failedStep("warning msg is not displayed");
		}
	}


	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssert = new SoftAssert();
		categorize = new Categorization(driver);
		session = new SessionSearch(driver);
		tagsAndFolder = new TagsAndFoldersPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {

			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@AfterClass(alwaysRun = true)
	public void close() {

		System.out.println("Executed :Proview-sprint26 ");
	}
}


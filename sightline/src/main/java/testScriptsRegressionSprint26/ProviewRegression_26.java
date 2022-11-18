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

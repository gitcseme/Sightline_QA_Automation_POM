package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
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
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Proview_Regression {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionSearch;
	String pureHit;
	String pureHit1;
	BaseClass bc;

	String tagName = "CatTag2" + Utility.dynamicNameAppender();
	String folderName = "CatFolder2" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}

	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 1)
	public void validateCategorization(String username, String password, String role) throws InterruptedException {
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("RPMXC0N-54272");
		bc.stepInfo("To verify that Categorization functionality is working correctly and properly");
		if (role == "RMU") {
			// Search for any content on basic search screen
			sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(Input.testData1);
			pureHit = sessionSearch.getPureHitsCount().getText();
			// Create Bulk Tag
			sessionSearch.bulkTag(tagName);
			bc.selectproject();
			sessionSearch.basicContentSearch(Input.testData1);
			pureHit1 = sessionSearch.getPureHitsCount().getText();
			// Create bulk folder
			sessionSearch.bulkFolder(folderName);
		}
		Categorization cat = new Categorization(driver);
		cat.CategorizationFunctionalityVerification(tagName, folderName);
		cat.ViewInDocLIst();
		DocListPage doclistPage = new DocListPage(driver);
		List<String> docids = new ArrayList<>();
		docids = bc.getAvailableListofElements(doclistPage.getDocIds());
		System.out.println(docids.size());

		if (role == "PA") {
			TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tp.deleteAllTags("CatTag2");
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tp.deleteAllFolders("CatFolder2");
		}
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		bc = new BaseClass(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			lp.logout();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		System.out.println("Executed :Reviewer Result Report Regression ");
	}
}

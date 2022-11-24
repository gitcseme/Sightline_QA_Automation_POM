package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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

public class DocList_Regression26 {
	
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

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}
	
	/**
	 * @author: TestCase id:RPMXCON-66462
	 * @Description Verify that if user switches to thumbnail view after applying
	 *              filter should show same docs
	 */
	   @Test(description = "RPMXCON-66462", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyUserSwitchesThumbnailViewShowSameDocs(String userName, String password, String fullName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-66462 DocList Component");
		baseClass
				.stepInfo("Verify that if user switches to thumbnail view after applying filter should show same docs");
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login As User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged In as : " + fullName);

		// search and go to docList page
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(docList.getDocIds());
		List<String> docIdsList = baseClass.availableListofElements(docList.getDocIds());
		System.out.println(docIdsList);

		baseClass.stepInfo("applying include filter and verifying the applied filter");
		docList.applyCustodianNameFilter(Input.metaDataCN);
		baseClass.waitTillElemetToBeClickable(docList.getApplyFilters());
		docList.getApplyFilters().Click();
		int index = baseClass.getIndex(docList.getTableRowHeaderInDocList(), "CUSTODIANNAME");
		System.out.println(index);
		baseClass.waitTime(3);
		List<WebElement> element = docList.getColumValues(index).FindWebElements();
		for (WebElement ele : element) {
			driver.waitForPageToBeReady();
			String text = ele.getText().trim();
			if (text.contains(Input.metaDataCN)) {
				System.out.println("Applied filter for" + Input.metaDataCN + " is displayed in list view");
			} else {
				baseClass.failedStep("Applied filter for" + Input.metaDataCN + " is not displayed in list view");
			}
		}
		driver.Navigate().refresh();
		baseClass.waitTime(3);
		baseClass.stepInfo("Navigating to Tile view page and selecting checkboxes");
		docList.verifySelectAllInTileView();
		baseClass.stepInfo("applying include filter in Thumbnail view and verifying the applied filter");
		docList.applyCustodianNameFilter(Input.metaDataCN);
		baseClass.waitTillElemetToBeClickable(docList.getApplyFilters());
		docList.getApplyFilters().Click();
		baseClass.waitTime(5);
		docList.verifyingThumbnailBoxesValues(Input.metaDataCN);
		for (int i = 1; i < docList.getInfoBtn().size(); i++) {
			driver.waitForPageToBeReady();
			docList.getInfoBtnInThumbnailBoxes(i).waitAndClick(2);
			String ActualString = docList.getDocIdTileView().getText();
			System.out.println(ActualString);
			baseClass.compareListWithOnlyOneString(docIdsList, ActualString,
					"Filter are applied for thumbnail boxes same docids" + ActualString + "is displayed",
					"Same docids is not displayed");
		}
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
		System.out.println("**Executed  DocList_Regression26.**");
	}

}

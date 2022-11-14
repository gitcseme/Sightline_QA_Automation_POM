package testScriptsRegressionSprint25;

import java.awt.AWTException;
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

public class DocList_Regression1_25 {

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
	 * @author Vijaya.Rani ModifyDate:14/11/2022 RPMXCON-66459
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that option to view more docs (thumbnails) per page same
	 *              options exist now.
	 */
	@Test(description = "RPMXCON-66459", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyThumbnailsViewInDocListPageExistNow(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-66459");
		baseClass.stepInfo("Verify that option to view more docs (thumbnails) per page same options exist now.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("DocList Page is get displayed in List View with page view as 10");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getTileView());
		docList.getTileView().waitAndClick(5);
		softAssert.assertTrue(docList.getThumbnailsView().Displayed());
		baseClass.passedStep("thumbnails view is displayed");
		softAssert.assertAll();
		String[] tag= {"10","50","100","500"};
		for(int i=0;i<tag.length;i++) {
			driver.waitForPageToBeReady();
			docList.getTileView().waitAndClick(5);
			docList.selectPageLengthInDocList(tag[i]);
			baseClass.stepInfo("thumbnails view is displayed with pagination of"+tag[i]);
		}
		
		baseClass.passedStep(
				"Verify that option to view more docs by (10/50/100/500) option (thumbnails) per page same options exist now same in drop down format ");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:14/11/2022 RPMXCON-66463
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that if user applies the View by 10/50/100/500 option in
	 *              one view and switches to the other view, the same setting should
	 *              be retained and applied.
	 */
	@Test(description = "RPMXCON-66463", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifypageLengthInListViewAndTileViewPage(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-66463");
		baseClass.stepInfo(
				"Verify that if user applies the View by 10/50/100/500 option in one view and switches to the other view, the same setting should be retained and applied.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("DocList Page is get displayed in List View with page view as 10");

		String[] pageLen= {"10","50","100","500"};
		for(int i=0;i<pageLen.length;i++) {
			driver.waitForPageToBeReady();
			docList.getTileView().waitAndClick(5);
			docList.selectPageLengthInDocList(pageLen[i]);
			baseClass.stepInfo("thumbnails view is displayed with pagination of"+pageLen[i]);
			docList.getGridViewIcon().waitAndClick(5);
			docList.selectPageLengthInDocList(pageLen[i]);
			baseClass.stepInfo("List view is displayed with pagination of"+pageLen[i]);
		}
		baseClass.passedStep("thumbnails view is displayed with pagination of 50,100,500");
		// one view and switches to the other view for PageLength
		for(int j=0;j<pageLen.length;j++) {
			driver.waitForPageToBeReady();
			docList.getGridViewIcon().waitAndClick(5);
			docList.selectPageLengthInDocList(pageLen[j]);
			baseClass.stepInfo("List view is displayed with pagination of"+pageLen[j]);
			docList.getTileView().waitAndClick(5);
			docList.selectPageLengthInDocList(pageLen[j]);
			baseClass.stepInfo("thumbnails view is displayed with pagination of"+pageLen[j]);
		}
		baseClass.passedStep(
				"Verify that if user applies the View by 10/50/100/500 option in one view and switches to the other view, the same setting");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:14/11/2022 RPMXCON-66471
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that after performing bulk action, check box checked
	 *              should be unchecked from thumbnail view.
	 */
	@Test(description = "RPMXCON-66471",enabled = true, groups = { "regression" })
	public void verifyAfterBulkActionChechBoxUnCheckedFromTaumbnailview()
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-66471");
		baseClass.stepInfo(
				"Verify that after performing bulk action, check box checked should be unchecked from thumbnail view.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);

		loginPage.logout();
		
		String[]username= {Input.pa1userName,Input.rmu1userName,Input.rev1userName};
        String[]password= {Input.pa1password,Input.rmu1password,Input.rev1password};
        
        for(int j=0;j<username.length;j++) {
        loginPage.loginToSightLine(username[j],password[j]);
        
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		softAssert.assertTrue(docList.getDocListViewsInRow().isDisplayed());
		baseClass.passedStep(
				" \"List View\" and \"Tile View\" icon is displayed the same row of \"Select Column\" , \"Save to Profile\" button.");
		softAssert.assertAll();      
		docList.getTileView().waitAndClick(5);
		baseClass.stepInfo("Verify that thumbnail generation is attempted and Viewed");
		// Select all thumbnail box
		docList.verifySelectAllInTileView();
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname);
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		if(! docList.getSelectAllCheckBox().Selected()) {
		baseClass.passedStep("Verify that after performing bulk action, check boxs are unchecked from thumbnail view");
		}else {
			baseClass.failedStep("Checkboxs are checked");
		}
		loginPage.logout();
        }
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
	
	
	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
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

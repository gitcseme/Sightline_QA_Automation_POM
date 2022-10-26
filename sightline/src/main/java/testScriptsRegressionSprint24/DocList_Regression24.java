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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression24 {

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
	 * @author Vijaya.Rani ModifyDate:21/10/2022 RPMXCON-54519
	 * @throws Exception
	 * @Description Validate onpage filter for EmailAuthorName with any special charatcers (,/"/-/_ /) on DocList page.
	 */
	@Test(description = "RPMXCON-54519", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterForEmailAuthorNameWithAnySpecialCharaters(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54519");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorName with any special charatcers (,/\"/-/_ /) on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String domain1 = "(#NOS OCRM FKNMS-ALL);";
		String domain2="Amol.Gawande/,@consilio.com";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// EmailRecipientnames Include
		baseClass.stepInfo("EmailAuthorName Include");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.include(domain1);
		driver.waitForPageToBeReady();
		if(baseClass.text(domain1).isDisplayed()) {
		baseClass.passedStep("Documents containing only the selected email IDs only filtered");
		}else {
			baseClass.failedStep("Documents containing selected email IDs not filtered");
		}

		docList.getClearAllBtn().waitAndClick(5);
		baseClass.stepInfo("ClearAll Button Is clicked");
		// EmailRecipientnames Exclude
		baseClass.stepInfo("EmailAuthorName Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.excludeDoclist(domain2);
		softAssertion.assertTrue(docList.getEmailAuthorNameNoRestultData().isDisplayed());
		baseClass.passedStep("Documents containing the selected email IDs should not be filtered");
		softAssertion.assertAll();

		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:21/10/2022 RPMXCON-53853
	 * @throws Exception
	 * @Description To verify, as an Reviewer user login, When I will select all the documents in Doc List, I will be able to see this documents on Doc View page, from View Document action button.
	 */
	@Test(description = "RPMXCON-53853", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSelectAllDocsInDocListPageGotoDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53853");
		baseClass.stepInfo(
				"To verify, as an Reviewer user login, When I will select all the documents in Doc List, I will be able to see this documents on Doc View page, from View Document action button.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docView=new DocViewPage(driver);

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select All Documents
		docList.selectingAllDocuments();
		driver.scrollingToBottomofAPage();
		String DocListCount = docList.getTableFooterDocListCount().getText();
		System.out.println(DocListCount);
		String[] doccount = DocListCount.split(" ");
		String DoclistDocCount = doccount[3];
		System.out.println("doclist page document count is" + DoclistDocCount);
		
		//DocView Form Doclist
		docList.docListToDocView();
		softAssert.assertTrue(docView.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		//verify Select docs display
		baseClass.waitForElementCollection(docView.getDocumetCountMiniDocList());
		int miniDocListCount = docView.getDocumetCountMiniDocList().WaitUntilPresent().size();
		System.out.println(miniDocListCount);
		baseClass.digitCompareEquals(Integer.valueOf(DoclistDocCount),Integer.valueOf(miniDocListCount), "Document count is displayed As expected from DocListPage",
				"DocCount is Not Displayed as expected");
		softAssert.assertAll();
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:25/10/2022 RPMXCON-53671
	 * @throws Exception
	 * @Description To verify as a Reviewer user login, I will be able to sort (Both
	 *              Ascending & Descending) a column from grid of Doc List page.
	 */
	@Test(description = "RPMXCON-53671", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSelectColumnAscendingAndDescending() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53671");
		baseClass.stepInfo(
				"To verify as a Reviewer user login, I will be able to sort (Both Ascending & Descending) a column from grid of Doc List page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();

		// sort in Ascending Order
		docList.sufflingColumnValueInDocListPage();

		// sort in Descending Order
		docList.verifyingDescendingOrderSortingInColumn();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/10/2022 RPMXCON-53657
	 * @throws Exception
	 * @Description To Verify, As a Admin user login, In Doc List page maximum 500
	 *              Documents will show per page.
	 */
	@Test(description = "RPMXCON-53657", enabled = true, groups = { "regression" })
	public void verifyAdminUserDocListPageDocsCount() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53657 DocList Component");
		baseClass.stepInfo(
				"To Verify, As a Admin user login, In Doc List page maximum 500 Documents will show per page");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Selecting page length in doclist page");
		docList.getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(Input.pageLength);
		driver.scrollingToBottomofAPage();
		driver.scrollingToElementofAPage(docList.getTableFooterDocListCount());
		driver.waitForPageToBeReady();
		String DocListCount = docList.getTableFooterDocListCount().getText();
		System.out.println(DocListCount);
		String[] doccount = DocListCount.split(" ");
		String Document = doccount[3];
		System.out.println("doclist page document count is" + Document);
		baseClass.textCompareEquals(Input.pageLength, Document, Input.pageLength + "is displayedas expected",
				Input.pageLength + "is not displayed as expected");
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

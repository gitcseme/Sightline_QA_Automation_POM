package testScriptsRegressionSprint28;

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
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Doclist_Regression28 {
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
		System.out.println("**Executed  DocList_Regression28.**");
	}

	/**
	 * @author sowndarya RPMXCON-61219
	 * @throws Exception
	 * @Description Verify that in "Doc Explorer" Pages/Export Data Popup - Provide
	 *              a Sticky Add-to-Selected btn while Scrolling Down.
	 */
	@Test(description = "RPMXCON-61219", enabled = true, groups = { "regression" })
	public void verifyExportDataPopup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-61219");
		baseClass.stepInfo(
				"Verify that in Doc Explorer Pages/Export Data Popup - Provide a Sticky Add-to-Selected btn while Scrolling Down.");
		DocListPage docList = new DocListPage(driver);
		DataSets data = new DataSets(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		String folderName = "Folder" + Utility.dynamicNameAppender();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// create folder and add save search in folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);
		
		// navigate to doc explorer
		docExp.navigateToDocExplorerPage();

		// select documents and navigate to export data
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		baseClass.passedStep(
				"Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");

		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");

		baseClass.waitForElement(docExp.actionDropdown());
		docExp.actionDropdown().waitAndClick(10);
		baseClass.waitForElement(docExp.exportDataFromActionDropdown());
		docExp.exportDataFromActionDropdown().waitAndClick(10);
		baseClass.stepInfo("selected documents and navigated to export data");

		// verify sticky notes 'Add to selected' position - Before scrolling
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		String beforeScrolling = docExp.exportWindow_AddToSelectedButton().GetAttribute("style");
		if (beforeScrolling.contains("text-align: center")) {
			baseClass.passedStep("“Add to Selected” btn  of Available Objects appears with a fixed position");
		}

		docExp.SelectExportDataWithMetadataAndWorkproduct(Input.sortDataBy, folderName);

		driver.scrollingToBottomofAPage();
		// verify sticky notes 'Add to selected' position - After scrolling
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		String afterScrolling = docExp.exportWindow_AddToSelectedButton().GetAttribute("style");
		if (afterScrolling.contains("text-align: center")) {
			baseClass.passedStep("“Add to Selected” btn  of Available Objects appears with a fixed position");
		}

		baseClass.textCompareEquals(beforeScrolling, afterScrolling, "success", "Failure");

		// run report
		driver.scrollPageToTop();
		baseClass.waitForElement(docExp.exportWindow_RunExport());
		docExp.exportWindow_RunExport().waitAndClick(10);

		baseClass.waitForElement(docExp.exportWindow_closeButton());
		docExp.exportWindow_closeButton().waitAndClick(10);

		driver.waitForPageToBeReady();
		data.getBullHornIcon().waitAndClick(10);
		String downloadMsg = data.getNotificationMsg().getText();
		String expected = "Your export is ready please click here to download";
		softAssert.assertEquals(downloadMsg, expected);
		baseClass.passedStep(
				"Notification is displayed and on click of the same download the file  Make sure that in downloaded file is selected all documents from all pages");
		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * @author sowndarya RPMXCON-61237
	 * @throws Exception
	 * @Description Verify that in "Doc List" Pages/Export Data Popup - Provide a
	 *              Sticky Add-to-Selected btn while Scrolling Down.
	 */
	@Test(description = "RPMXCON-61237", enabled = true, groups = { "regression" })
	public void verifyDocListPopup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-61237");
		baseClass.stepInfo(
				"Verify that in Doc List Pages/Export Data Popup - Provide a Sticky Add-to-Selected btn while Scrolling Down.");
		DocListPage docList = new DocListPage(driver);
		DataSets data = new DataSets(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		String folderName = "Folder" + Utility.dynamicNameAppender();
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// create folder and add save search in folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);
		// navigate to doc explorer
		docExp.navigateToDocExplorerPage();

		// select documents and navigate to export data
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		baseClass.passedStep(
				"Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");

		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");

		docExp.docExpViewInDocList();
		docList.documentSelection(5);
		baseClass.stepInfo("selected documents and navigated to Doclist");

		baseClass.waitForElement(docList.getActionDropDownButton());
		docList.getActionDropDownButton().waitAndClick(10);
		baseClass.waitForElement(docList.getExportData());
		docList.getExportData().waitAndClick(10);
		baseClass.stepInfo("selected documents and navigated to export data");

		// verify sticky notes 'Add to selected' position - Before scrolling
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		String beforeScrolling = docExp.exportWindow_AddToSelectedButton().GetAttribute("style");
		if (beforeScrolling.contains("text-align: center")) {
			baseClass.passedStep("“Add to Selected” btn  of Available Objects appears with a fixed position");
		}

		docExp.SelectExportDataWithMetadataAndWorkproduct(Input.sortDataBy, folderName);

		driver.scrollingToBottomofAPage();
		// verify sticky notes 'Add to selected' position - After scrolling
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		String afterScrolling = docExp.exportWindow_AddToSelectedButton().GetAttribute("style");
		if (afterScrolling.contains("text-align: center")) {
			baseClass.passedStep("“Add to Selected” btn  of Available Objects appears with a fixed position");
		}

		baseClass.textCompareEquals(beforeScrolling, afterScrolling, "success", "Failure");

		// run report
		driver.scrollPageToTop();
		baseClass.waitForElement(docExp.exportWindow_RunExport());
		docExp.exportWindow_RunExport().waitAndClick(10);

		baseClass.waitForElement(docExp.getBtnCloseWindow());
		docExp.getBtnCloseWindow().waitAndClick(10);

		baseClass.waitForElement(data.getBullHornIcon());
		data.getBullHornIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		String downloadMsg = data.getNotificationMsg().getText();
		String expected = "Your export is ready please click here to download";
		softAssert.assertEquals(downloadMsg, expected);
		baseClass.passedStep(
				"Notification is displayed and on click of the same download the file  Make sure that in downloaded file is selected all documents from all pages");
		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * @author sowndarya RPMXCON-65049
	 * @throws Exception
	 * @Description Verify that error message should be displayed when 'Share by
	 *              email' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer >
	 *              Export > Share Report
	 */
	@Test(description = "RPMXCON-65049", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInSharedReport() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-65049");
		baseClass.stepInfo(
				"Verify that error message should be displayed when 'Share by email' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Share Report");
		DocListPage docList = new DocListPage(driver);
		DataSets data = new DataSets(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		ReportsPage report = new ReportsPage(driver);
		String emailname = "< > * ; ‘ / ( ) # & ";
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// navigate to doc explorer
		docExp.navigateToDocExplorerPage();

		// select documents and navigate to export data
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		baseClass.passedStep(
				"Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");

		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");

		baseClass.waitForElement(docExp.actionDropdown());
		docExp.actionDropdown().waitAndClick(10);
		baseClass.waitForElement(docExp.exportDataFromActionDropdown());
		docExp.exportDataFromActionDropdown().waitAndClick(10);
		baseClass.stepInfo("selected documents and navigated to export data");

		baseClass.waitForElement(report.getMetaDataChoose(Input.sortDataBy));
		report.getMetaDataChoose(Input.sortDataBy).Click();

		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		docExp.exportWindow_AddToSelectedButton().waitAndClick(10);

		baseClass.waitForElement(docExp.getExportSchedulerButton());
		docExp.getExportSchedulerButton().waitAndClick(5);
		baseClass.stepInfo("Schedule Report pop up is open Successfully");

		baseClass.waitForElement(docExp.getExportShareEmail());
		docExp.getExportShareEmail().SendKeys(emailname);
		baseClass.passedStep(
				"'Share by email'accepted with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Share Report");
	}
}

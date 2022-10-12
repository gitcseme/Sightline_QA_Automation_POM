package testScriptsRegressionSprint23;

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
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression23 {

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
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-54697
	 * @throws Exception
	 * @Description Verify that “IngestionName” (Only For PA) Filter with "Exclude"
	 *              functionality is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54697", enabled = true, groups = { "regression" })
	public void verifyIngestionNameWithExcludeInDocExplorerList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54697");
		baseClass.stepInfo(
				"Verify that “IngestionName” (Only For PA) Filter with \"Exclude\" functionality is working correctly on Doc Explorer list.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String random = Input.ingestionAutomationAllSource;
		String random1 = Input.JanMultiPTIFF;

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.performExculdeIngestionNameFilter(random);

		baseClass.stepInfo("Verify documents after applying exclude functionality by DocFileType");
		docExplorer.verifyExcludeFunctionlityForIngestionName();

		baseClass.stepInfo("Refresh page");
		docExplorer.refreshPage();

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.performExculdeIngestionNameFilter(random);
		docExplorer.performUpdateExculdeIngestionNameFilter(random1);

		baseClass.stepInfo("Verify documents after applying exclude functionality by DocFileType");
		docExplorer.verifyExcludeFunctionlityForIngestionName();

		loginPage.logout();

	}
	/**
	 * Author : date: 06/10/2022 TestCase Id:RPMXCON-54681 Description
	 * :Verify that “Folders” Filter with "Include" functionality is working
	 * correctly on Doc Explorer list.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54681", enabled = true, groups = { "regression" })
	public void verifyFoldersIncludeFunctionalityWorking() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54681");
		baseClass.stepInfo(
				"Verify that “Folders” Filter with \"Include\" functionality is working correctly on Doc Explorer list.");

		String random = Input.atternoyClient + Utility.dynamicNameAppender();
		String random2 = Input.confidential + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFolder.CreateFolder(random, Input.securityGroup);

		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFolder.CreateFolder(random2, Input.securityGroup);

		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.waitForPageToBeReady();
		String Docs = docexp.getDocExp_DocID().getText();
		
		docexp.selectDocumentsAndBulkFolder(1, random);

		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		docexp.selectDocumentsAndBulkFolder(1, random2);

		baseClass.stepInfo("Perform include filter by folder");
		driver.waitForPageToBeReady();
		docexp.performIncludeFolderFilter(random);

		baseClass.stepInfo("Verify documents after applying include functionality by folder");
		driver.waitForPageToBeReady();
		docexp.verifyIncludeFunctionlityForDocFileType(Docs);

		baseClass.stepInfo("Refresh page");
		docexp.refreshPage();

		baseClass.stepInfo("Perform include filter by folder");
		driver.waitForPageToBeReady();
		docexp.performIncludeFolderFilter(random);

		baseClass.stepInfo("Perform another include filter by folder");
		driver.waitForPageToBeReady();
		docexp.performUpdateIncludeFolderFilter(random2);

		baseClass.stepInfo("Verify documents after applying include functionality by folder");
		driver.waitForPageToBeReady();
		docexp.verifyIncludeFunctionlityForDocFileType(Docs);
		loginPage.logout();
	}

	/**
	 * Author : date: 06/10/2022 TestCase Id:RPMXCON-54956 Description
	 * :Verify that Include filter functionality works properly when TAG name
	 * contains word between on Concept Explorer Report screen
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54956", enabled = true, groups = { "regression" })
	public void verifyIncludeFilterFunctionalityWorksTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54956");
		baseClass.stepInfo(
				"Verify that Include filter functionality works properly when TAG name contains word between on Concept Explorer Report screen");

		String Tagname = " Docs between 1 and 5  " + Utility.dynamicNameAppender();
		SessionSearch sessionSearch = new SessionSearch(driver);
		CommunicationExplorerPage communiExplore = new CommunicationExplorerPage(driver);
		ConceptExplorerPage CEPage = new ConceptExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		sessionSearch.basicContentSearch(Input.testData1);
		String Doccount = sessionSearch.getPureHitsCount().getText();
		sessionSearch.bulkTag(Tagname);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CEPage.ValidateConceptExplorerreport();
		communiExplore.getfilterDocumentBy("Tags", true, Tagname);
		driver.waitForPageToBeReady();

		// verify Include filter is work
		baseClass.waitForElement(communiExplore.getReportPanelText());
		String Tagcount = communiExplore.getReportPanelText().getText();
		if (Tagcount.contains(Doccount)) {
			baseClass.passedStep(
					"Include filter functionality is work properly and Records filtered on Concept Explorer Reportscreen ");

		} else {
			baseClass.failedStep("Include filter is not worked");

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
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
}

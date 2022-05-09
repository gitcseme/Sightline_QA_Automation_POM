package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.asserts.SoftAssert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
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
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.IngestionPage_Indium;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import views.html.helper.input;

public class Ingestion_IndiumRegression {
	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	IngestionPage_Indium ingestionPage;
	UserManagement user;
	DocListPage doclist;
	DataSets dataSets;
	DocViewPage docview;

	String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws ParseException, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);

	}

	/**
	 * Author :Baskar date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description:Verify Email metadata automatically released to Security Group
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 01)
	public void verifyEmailMetadataWithIngestionName() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.regressionRun);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage = new IngestionPage_Indium(driver);
		savedSearch = new SavedSearch(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		sessionSearch = new SessionSearch(driver);
		UserManagement user = new UserManagement(driver);
		DocListPage doclist = new DocListPage(driver);

		String newSg = "Sg" + Utility.dynamicNameAppender();
		String toDocList = "toDoclist" + Utility.dynamicNameAppender();
		String[] metaData = { "EmailCCNamesAndAddresses", "EmailBCCNamesAndAddresses", "EmailToNamesAndAddresses",
				"EmailAuthorNameAndAddress" };

		String securityTab = "Shared with " + newSg;
		String ingestionName = "41AD_IngestionEmailData_20220321091148016";
		baseClass.stepInfo("Test case Id: RPMXCON-49561");
		baseClass.stepInfo("Verify Email metadata automatically released to Security Group");
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		// creating new sg
		securityGroupPage.createSecurityGroups(newSg);
		// assign project field to sg
		for (int i = 0; i < metaData.length; i++) {
			System.out.println(metaData[i]);
			securityGroupPage.addProjectFieldtoSG(newSg, metaData[i]);
		}
		baseClass.stepInfo("Email metadata project field are assigned to :" + newSg);

		// releasing document to new sg
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkRelease(newSg);

		// searching by ingestion name in metadata
		Actions act = new Actions(driver.getWebDriver());
		act.moveToElement(sessionSearch.getMouseOver_ToTextBoxString(Input.searchString1).getWebElement()).build()
				.perform();
		sessionSearch.getDeletePreviousSearch().waitAndClick(5);
		sessionSearch.metaDataSearchInBasicSearch("IngestionName", ingestionName);
		// saving the ingestion name as pa users
		sessionSearch.saveSearchAction();
		baseClass.waitForElement(sessionSearch.getSaveSearchPopupFolderName(securityTab));
		sessionSearch.getSaveSearchPopupFolderName(securityTab).ScrollTo();
		sessionSearch.getSaveSearchPopupFolderName(securityTab).waitAndClick(5);
		baseClass.waitForElement(sessionSearch.getSaveSearch_Name());
		sessionSearch.getSaveSearch_Name().SendKeys(toDocList);
		baseClass.waitForElement(sessionSearch.getSaveSearch_SaveButton());
		sessionSearch.getSaveSearch_SaveButton().Click();

		Reporter.log("Saved the search with name '" + toDocList + "'", true);
		UtilityLog.info("Saved search with name - " + toDocList);

		// access to rmu user new sg
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		user.passingUserName(Input.rmu1userName);
		user.applyFilter();
		baseClass.waitTime(3);
		user.editLoginUser();
		user.addingSGToUser(Input.securityGroup, newSg);
//		user.getSubmit().waitAndClick(5);

		// logout as pa
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectsecuritygroup(newSg);
		
		// navigating to doclist
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		baseClass.waitForElement(sessionSearch.getSaveSearchPopupFolderName(securityTab));
		sessionSearch.getSaveSearchPopupFolderName(securityTab).waitAndClick(5);
		savedSearch.savedSearchToDocList(toDocList);
		doclist.verifyTheMetaDataPresences();
		baseClass.selectsecuritygroup(Input.securityGroup);
		
		// logout as rmu
		loginPage.logout();

	}
	
	/**
	 * Author :Baskar date: 09/05/2022 Modified date: NA Modified by: NA
	 * Description:To Verify for Audio longer than 1 hour, in Docview, "Zoom In/Zoom Out" 
	 * should be available so user could switch between the short and long wave forms.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void verifyMoreThanHourAudioDocs() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		String audioDocsIngestionName="41AD_SSAudioSpeech_Transcript_20220321085634270";
		String moreThanHour=Input.ingestionOneHour;
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-48241");
		baseClass.stepInfo("To Verify for Audio longer than 1 hour, in Docview, \"Zoom In/Zoom Out\" should be "
				+ "available so user could switch between the short and long wave forms.");
		
		baseClass.selectproject("Indium_Regressionrun");
		String ingestionFullName = dataSets.isDataSetisAvailable(audioDocsIngestionName);
		if(ingestionFullName!=null) {
			driver.waitForPageToBeReady();
			sessionSearch.MetaDataSearchInBasicSearch("IngestionName", audioDocsIngestionName);
			sessionSearch.viewInDocView();
			baseClass.waitForElement(docview.getDocumentByid(moreThanHour));
			docview.getDocumentByid(moreThanHour).waitAndClick(5);
			
			// verifying more than one hour audio docs
			String overAllAudioTime = docview.getDocview_Audio_EndTime().getText();
			String[] splitData = overAllAudioTime.split(":");
			String data = splitData[0].toString();
			System.out.println(data);
			if (Integer.parseInt(data) >= 01) {
				baseClass.stepInfo("Audio docs have more than:" + overAllAudioTime + " hour to check zoom function");
			} else {
				baseClass.failedMessage("Lesser than one hour");
			}
			
			// checking zoom in function working for more than one hour audio docs
			docview.getAudioDocZoom().waitAndClick(5);
			boolean zoomBar = docview.getAudioZoomBar().Displayed();
			softAssertion.assertTrue(zoomBar);
			baseClass.passedStep("Zoom functionality working for more than one hour of document");
		}
		else {
			baseClass.failedStep("Ingestion not available, Need to ingest for this project");
		}
		softAssertion.assertAll();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Baskar date: 09/05/2022 Modified date: NA Modified by: NA
	 * Description:To verify for Audio less than 1 hour, in Docview, "Zoom In/Zoom Out" is disabled or hidden.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 3)
	public void verifyLessThanHourAudioDocs() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		String audioDocsIngestionName="41AD_SSAudioSpeech_Transcript_20220321085634270";
		String moreThanHour=Input.ingestionLessThanHour;
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-48239");
		baseClass.stepInfo("To verify for Audio less than 1 hour, in Docview, \"Zoom In/Zoom Out\" is disabled or hidden.");
		
		baseClass.selectproject("Indium_Regressionrun");
		String ingestionFullName = dataSets.isDataSetisAvailable(audioDocsIngestionName);
		if(ingestionFullName!=null) {
			sessionSearch.MetaDataSearchInBasicSearch("IngestionName", audioDocsIngestionName);
			sessionSearch.viewInDocView();
			baseClass.waitForElement(docview.getDocumentByid(moreThanHour));
			docview.getDocumentByid(moreThanHour).waitAndClick(5);
			
			// verifying less than one hour audio docs
			String overAllAudioTime = docview.getDocview_Audio_EndTime().getText();
			String[] splitData = overAllAudioTime.split(":");
			String data = splitData[0].toString();
			System.out.println(data);
			if (Integer.parseInt(data) >= 01) {
				baseClass.failedStep("more than one hour docs selected");
			} else {
				baseClass.stepInfo("Audio docs have less than:" + overAllAudioTime + " hour to check zoom function");
			}
			
			// checking zoom in function working for less than one hour audio docs
			boolean zoomBar = (boolean) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.querySelector(\"div.col-md-2.disabledbutton div:nth-child(3) > div\").hidden;");
			softAssertion.assertFalse(zoomBar);
			baseClass.passedStep("Zoom functionality not working for less than one hour of document,Zoom is hidden");
		}
		else {
			baseClass.failedStep("Ingestion not available, Need to ingest for this project");
		}
		softAssertion.assertAll();
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

		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
	}

}

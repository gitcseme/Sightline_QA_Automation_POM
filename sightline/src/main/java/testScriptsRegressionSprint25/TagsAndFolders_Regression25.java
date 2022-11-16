package testScriptsRegressionSprint25;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagsAndFolders_Regression25 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;
	String tagname;
	String foldername;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-53448
	 * @Description:Verify that Tag propagation must be based on the ingested MD5Hash field when Tag is saved from doc view coding form
	 **/
	@Test(description = "RPMXCON-53448",enabled = true, groups = { "regression" })
	public void verifyTagProgationInCodingForm() throws Exception {

		foldername = "Folder" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo("Verify that Tag propagation must be based on the ingested MD5Hash field when Tag is saved from doc view coding form");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As " + Input.rmu1FullName);
		base.selectproject(Input.additionalDataProject);
		String tag="Tag"+ Utility.dynamicNameAppender();
		String codingForm="CF"+ Utility.dynamicNameAppender();
		String MD5Hashdocument="H136960011001001.txt";
		
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagNotSave(tag,Input.tagNamePrev);
		base.waitForElement(tagsAndFolderPage.getPropTagExactDuplic());
		tagsAndFolderPage.getPropTagExactDuplic().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		System.out.println(tag);
		base.VerifySuccessMessage("Tag added successfully");
		base.stepInfo("Tag : " + tag + " Created Successfully With Exact Duplicate (MD5Hash)");

		CodingForm form =new CodingForm(driver);
		form.navigateToCodingFormPage();
		form.saveCodingFormWithRedioGroup(codingForm, tag);
		driver.waitForPageToBeReady();
		form.assignCodingFormToSG(codingForm);
		
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.docFileName,MD5Hashdocument);
		sessionSearch.ViewInDocView();
		
		DocViewPage docview= new DocViewPage(driver);
		base.waitForElement(docview.getDocView_MiniDocListIds(1));
		docview.getDocView_MiniDocListIds(1).waitAndClick(10);
		docview.saveAndNextNewCodingFormSelectingTags();
		base.passedStep("Document with same MD5Hash as of saved document is tag propagated successfully");
		
		driver.waitForPageToBeReady();
		form.navigateToCodingFormPage();
		form.assignCodingFormToSG(Input.codingFormName);
		loginPage.logout();
	}
}

package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression26 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	DocViewPage docView;
	Utility utility;
	DocExplorerPage docexp;


	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}



	/**
	 * Author : date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63743 Verify that when Copy menu is selected from doc view then on
	 * navigating to another document from document navigation previously selected
	 * panels/menus should retain
	 */

	@Test(description = "RPMXCON-63743", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyCopyMenuSelectedDocViewNavigateToDocNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63743");
		baseClass.stepInfo(
				"Verify that when Copy menu is selected from doc view then on navigating to another document from document navigation previously selected panels/menus should retain");
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		String docid = "ID00000152";

		// login as RMU
        loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
        baseClass.stepInfo("Login as Rmu");
        docexp = new DocExplorerPage(driver);
        DocListPage doclist = new DocListPage(driver);
 
        // DocExploer to viewindocView Page
        baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
        this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
        baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
        docexp.getDocExp_DocFiletypeSearchName().SendKeys("Text");
        doclist.getApplyFilter().waitAndClick(10);
        baseClass.waitTime(3);
        docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
        docexp.getDocExp_SelectAllDocs().Click();
        driver.waitForPageToBeReady();
        docexp.docExpViewInDocView();
        driver.waitForPageToBeReady();
        docView.selectSourceDocIdInAvailableField("SourceDocID");
        driver.Navigate().refresh();
        docView.ScrollAndSelectDocument(docid);
        docView.getDocView_CodingFormlist().waitAndClick(5);
        docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
        docView.verifyCopyAndPasteRedacTextOnCommentBox();
        docView.getCodingFormSaveThisForm().waitAndClick(2);
        baseClass.stepInfo("Document saved successfully");
 
        // verify select icon is loaded
        baseClass.waitForElement(docView.getDocView_Next());
        docView.getDocView_Next().waitAndClick(5);
        driver.waitForPageToBeReady();
        String docId1 = docView.getDocView_CurrentDocId().getText();
        String nextDoc = docView.getDocView_Next().GetAttribute("value");
        System.out.println(nextDoc);
        baseClass.stepInfo("Navigated to next document is loaded in viewer ");
        baseClass.waitTime(5);
        docView.verifyCopyandPasteIconStatus();
        baseClass.waitForElement(docView.getDocView_Last());
        docView.getDocView_Last().waitAndClick(5);
        baseClass.waitTime(5);
        String docId2 = docView.getDocView_CurrentDocId().getText();
        System.out.println(docId2);
        String lastDoc = docView.getDocView_NumTextBox().GetAttribute("value");
        System.out.println(lastDoc);
        softassertion.assertEquals(lastDoc, "14");
        baseClass.stepInfo("Navigated to last document is loaded in viewer ");
        baseClass.waitTime(5);
        docView.verifyCopyandPasteIconStatus();
        baseClass.waitForElement(docView.getDocView_Previous());
        docView.getDocView_Previous().waitAndClick(5);
        driver.waitForPageToBeReady();
        String docId3 = docView.getDocView_CurrentDocId().getText();
        String previousDoc = docView.getDocView_NumTextBox().GetAttribute("value");
        softassertion.assertEquals(previousDoc, "13");
        baseClass.stepInfo("Navigated to previous document is loaded in viewer");
        baseClass.waitTime(5);
        docView.verifyCopyandPasteIconStatus();
        baseClass.waitForElement(docView.getDocView_First());
        docView.getDocView_First().waitAndClick(5);
        driver.waitForPageToBeReady();
        String docId4 = docView.getDocView_CurrentDocId().getText();
        String FirstDoc = docView.getDocView_NumTextBox().GetAttribute("value");
        softassertion.assertEquals(FirstDoc, "1");
        baseClass.stepInfo("Navigated to first document is loaded in viewer ");
        baseClass.waitTime(5);
        docView.verifyCopyandPasteIconStatus();
 
        // Verify Select docs in History button
        baseClass.waitTime(5);
        String[] docids = { docId1, docId2, docId3, docId4 };
        baseClass.waitForElement(docView.getDocView_HistoryButton());
        docView.getDocView_HistoryButton().waitAndClick(5);
        baseClass.passedStep("User clicked clock icon in minidoclist");
        baseClass.waitTime(5);
        int count = docView.getselectDocsFromClockIcon().size();
        ArrayList<String> dataList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            String docs = docView.getselectDocHistory(i).getText();
            System.out.println(docs);
            dataList.add(docs);
        }
        System.out.println(dataList);
        for (int j = 0; j < docids.length; j++) {
            baseClass.compareListWithOnlyOneString(dataList, docids[j], docids[j] + "is present in the history",
                    docids[j]+" is not present in the history");
        }
 
        baseClass.waitForElement(docView.getselectDocFromClckIcon());
        docView.getselectDocFromClckIcon().waitAndClick(5);
        baseClass.passedStep("User selected the document from history drop down as expected");
        baseClass.waitTime(5);
        docView.verifyCopyandPasteIconStatus();
        softassertion.assertAll();
	}
}
package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Consilio_2 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocListPage docList;
	DocExplorerPage docExplorer;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		
		  Input in = new Input(); in.loadEnvConfig();
		 

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		docList = new DocListPage(driver);
		docExplorer = new DocExplorerPage(driver);
	}

	@Test(description = "RPMXCON-70311", enabled = true, groups = { "regression" })
	public void verifyInfieldWithNodata() throws Exception {

		baseClass.stepInfo(
				"RPMXCON-70311-Verify that when user enters a text in the field which doesnot have respective documents to filter it should show "
						+ "\"Your query returned no data\" message with zero document.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("select all docs from Doc Explorer");

		docExplorer.SelectingAllDocuments("Yes");
		baseClass.stepInfo("Navigate to Doc list page from Doc Explorer");
		docExplorer.docExpViewInDocList();

		baseClass.stepInfo("Enter filter query Z in Doc Id");
		docList.getDocList_DocId().SendKeys("Z");

		baseClass.stepInfo("Apply Filter");

		docList.getApplyFilter().Click();

		baseClass.passedStep("Filterapplied");
		System.out.println(docList.getDocList_NoDataFilterResult().getText());
		Assert.assertEquals(docList.getDocList_NoDataFilterResult().getText(), "Your query returned no data");
		baseClass.passedStep("Displayed Query returned no data");

		loginPage.logout();

	}

	@Test(description = "RPMXCON-70309", enabled = true, groups = { "regression" })
	public void verifyClearAllAfterFilters() throws Exception {

		baseClass.stepInfo(
				"RPMXCON-70309-Verify that after applying filter When user selects Clear All, all the new filters on the page will be "
						+ "reset (along with the green bubble filters)");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Select all docs from docexplorer and navigate to Doc List");
		docExplorer.SelectingAllDocuments("Yes");
		docExplorer.docExpViewInDocList();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Enter Text under Docfile type");
		docList.getDocList_DocfiletypeFilter().SendKeys("Doc");

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Apply Filter in Doc filters by with Custodian Name P Allen");
		docList.applyCustodianNameFilter("P Allen");

		docList.getDocList_DocfiletypeFilter().Click();
		docList.getDocList_DocfiletypeFilter().SendKeys("Doc");
		docList.getApplyFilter().Click();
		baseClass.passedStep("Filterapplied");

		docList.clearAllAppliedFilters();

		baseClass.passedStep("Cleared all applied filters");
		Assert.assertEquals(docList.getDocList_DocfiletypeFilter().getText(), "");
		baseClass.passedStep("Page got refreshed with no text values in it");

		loginPage.logout();

	}

	@Test(description = "RPMXCON-70309", enabled = true, groups = { "regression" })
	public void verifyInlineFilterAndFilterDocsBy() throws Exception {

		baseClass.stepInfo(
				"RPMXCON-70407-Verify 'Inline filter' and filter in 'Filter Documents By'  working as Expected)");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Select all docs from docexplorer and navigate to Doc List");
		docExplorer.SelectingAllDocuments("Yes");
		baseClass.stepInfo("Navigate to Doc list page from Doc Explorer");
		docExplorer.docExpViewInDocList();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Enter Text under Docfile type");
		docList.getDocList_DocfiletypeFilter().SendKeys("Doc");

		baseClass.stepInfo("Apply Filter in Doc filters by with Custodian Name P Allen");
		docList.applyCustodianNameFilter("P Allen");

		docList.getApplyFilter().Click();
		baseClass.passedStep("Filterapplied");

		
         driver.waitForPageToBeReady();


		Assert.assertEquals(docList.getDocList_CustodianFirstRowValue().getText(),"P Allen");
		Assert.assertEquals(docList.getDocList_DocfiletypeFirstRowValue().getText(),"MS Word 97-2003 Document (OLE)");
		
		baseClass.passedStep("Docs returned matching with given filter");

		loginPage.logout();

	}
	
	@Test(description = "RPMXCON-70370", enabled = true, groups = { "regression" })
	public void verifyInlineFilterWithAlpha() throws Exception {

		baseClass.stepInfo(
				"RPMXCON-70370-Verify field value text containing alpha is returning correct result as per filter");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Select all docs from docexplorer and navigate to Doc List");
		docExplorer.SelectingAllDocuments("Yes");
		baseClass.stepInfo("Navigate to Doc list page from Doc Explorer");
		docExplorer.docExpViewInDocList();
		
		driver.waitForPageToBeReady();
		

		baseClass.stepInfo("Apply Filter in Doc filters by with Custodian Name P Allen");
		docList.getDocList_CustodianName().SendKeys("Allen");

		docList.getApplyFilter().Click();
		baseClass.passedStep("Filterapplied");

		
         driver.waitForPageToBeReady();


		Assert.assertEquals(docList.getDocList_CustodianFirstRowValue().getText(),"P Allen");
		
		
		baseClass.passedStep("Docs returned matching with given filter");

		loginPage.logout();

	}
	

	@Test(description = "RPMXCON-70406", enabled = true, groups = { "regression" })
	public void verifyMasterDateFilter() throws Exception {

		baseClass.stepInfo(
				"RPMXCON-70406-Verify field value text containing DateTime type is returning correct result as per filter)");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Select all docs from docexplorer and navigate to Doc List");
		docExplorer.SelectingAllDocuments("Yes");
		baseClass.stepInfo("Navigate to Doc list page from Doc Explorer");
		docExplorer.docExpViewInDocList();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Select Master Date Value from first row");
		String date = docList.getDocList_MasterDateFilterValue().getText().substring(0, 10);
		String time= docList.getDocList_MasterDateFilterValue().getText().substring(10, 19);
		System.out.println(date);
		baseClass.stepInfo("Giving Master Date value in field");
		docList.getDocList_MasterDateFilter().SendKeys(date+time);
		
		
		docList.getApplyFilter().Click();
		baseClass.passedStep("Filterapplied");

		Assert.assertEquals(date+time,"2010/04/06 22:18:00");
		baseClass.passedStep("Docs returned matching filter values");

		loginPage.logout();
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

}
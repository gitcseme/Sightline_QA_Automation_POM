package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import testScriptsSmoke.Input;

public class DocList_Consilio_2 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
    DocExplorerPage docexp;
    DocListPage docList;
	DocExplorerPage docExplorer = new DocExplorerPage(driver);

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		/*
		 * Input in = new Input(); in.loadEnvConfig();
		 */
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		docList = new DocListPage(driver);
		docexp=new DocExplorerPage(driver);
	}
	@Test(description = "RPMXCON-70311", enabled = true, groups = { "regression" })
	public void verifyextinfieldwithnodata() throws Exception {

		baseClass.stepInfo("RPMXCON-70311-Verify that when user enters a text in the field which doesnot have respective documents to filter it should show "
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
		Assert.assertEquals(docList.getDocList_NoDataFilterResult().getText(),"Your query returned no data");
		baseClass.passedStep("Displayed Query returned no data");
		
		loginPage.logout();

		

}
	
	@Test(description = "RPMXCON-70309", enabled = true, groups = { "regression" })
	public void verifyClearAllAfterFilters() throws Exception {

		baseClass.stepInfo("RPMXCON-70309-Verify that after applying filter When user selects Clear All, all the new filters on the page will be "
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
	   Assert.assertEquals(docList.getDocList_DocfiletypeFilter().getText(),"" );
		baseClass.passedStep("Page got refreshed with no text values in it");

		loginPage.logout();


}
}
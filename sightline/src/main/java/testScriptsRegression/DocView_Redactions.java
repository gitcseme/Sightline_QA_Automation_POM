package testScriptsRegression;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import DataBase.DatabaseConnection;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import java.sql.*;

public class DocView_Redactions {
	Driver driver;
	LoginPage lp;
	DocViewPage docView;
	HomePage hm;
	BaseClass bc;
	RedactionPage redact;
	ProductionPage page;

	String redactname = "Redaction" + Utility.dynamicNameAppender();
	String production1 = "P" + Utility.dynamicNameAppender();
	String production2 = "P" + Utility.dynamicNameAppender();
	String PrefixID1 = "A_" + Utility.dynamicNameAppender();
	String SuffixID1 = "_P" + Utility.dynamicNameAppender();
	String PrefixID2 = "B_" + Utility.dynamicNameAppender();
	String SuffixID2 = "_N" + Utility.dynamicNameAppender();
	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String Tagname = "Privileged";
	String databasename = "EAutoP0C8A";
	String docID = "1426";
	String documentid= "ID00000130";

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, ParseException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

		// Open browser
		driver = new Driver();

		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		page = new ProductionPage(driver);

//		  lp.loginToSightLine(Input.rev1userName, Input.rev1password);
//		  
//		  docView = new DocViewPage(driver);
//		  
//		  SavedSearch ss = new SavedSearch(driver);
//		  ss.savedSearchToDocView("RedactTest");
//		  
//		  
//		  
//		//  docView.getDocView_DocId(documentid).waitAndClick(10);
//		  
//		  driver.WaitUntil((new Callable<Boolean>() { public Boolean call() { return
//		  docView.getDocView_Redact_Rectangle().Displayed();}}), Input.wait30);
//		  docView.getDocView_RedactIcon().Click(); Thread.sleep(1000);
//		  
//		  driver.WaitUntil((new Callable<Boolean>() { public Boolean call() {return
//		  docView.getDocView_Redact_Rectangle().Displayed();}}), Input.wait30);
//		  docView.getDocView_Redact_Rectangle().Click(); Thread.sleep(2000);
//		 

	}

   // @Test(dataProvider="Datasets",priority=1)
	public void AddRedaction(int off1, int off2, int k) throws InterruptedException {

		docView.redactbyrectangle(off1, off2, k, "R1");
	}

	// @Test(priority=2)
	public void ModifyRedaction() throws InterruptedException {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getDocView_Redact_Rectangle().Displayed();
			}
		}), Input.wait30);
		docView.getDocView_Redact_Rectangle().Click();
		Thread.sleep(2000);
		docView.editredaction(300, 207, 4, "R4");
		docView.editredaction(500, 210, 6, "R6");
	}

	// @Test(priority=3)
	public void AddRedaction1() throws InterruptedException {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getDocView_Redact_Rectangle().Displayed();
			}
		}), Input.wait30);
		docView.getDocView_Redact_Rectangle().waitAndClick(10);
		Thread.sleep(2000);
		docView.redactbyrectangle(600, 100, 7, "R1");
		docView.redactbyrectangle(700, 150, 8, "R2");
	}

	//@Test(priority=4)
	public void DeleteRedaction() throws InterruptedException {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getDocView_Redact_Rectangle().Displayed();
			}
		}), Input.wait30);
		docView.getDocView_Redact_Rectangle().Click();
		Thread.sleep(2000);
		docView.Deleteredaction(150, 202, 2, "R1");
		docView.Deleteredaction(200, 205, 3, "R2");
	}

	// @Test(priority=5)
	public void AddRedactionnextpage() throws InterruptedException {
		driver.scrollPageToTop();
		// docView.getDocView_Next().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getDocView_Redact_Rectangle().Displayed();
			}
		}), Input.wait30);
		docView.getDocView_Redact_Rectangle().waitAndClick(10);
		Thread.sleep(2000);
		docView.redactbyrectangle(250, 300, 9, "R1");
		docView.redactbyrectangle(150, 480, 10, "R2");
		// docView.getDocView_Next().Click();
	}

	@Test(priority = 6)
	public void ProductionAllRedaction() throws Exception {

		// driver.scrollPageToTop();
		// docView.VerifyFolderTab("ProdRedact", 9);
		// lp.logout();
		 lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		page.Productionwithallredactions(production1, PrefixID1, SuffixID1, "ProdRedact", Tagname);
	}

	@Test(priority = 7)
	public void ProductionSomeRedaction() throws Exception {

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		page.Productionwithsomeredactions(production2, PrefixID2, SuffixID2, "ProdRedact", Tagname);
	}

	@Test(priority = 8)
	public void PDFComparision() throws Exception {

		bc.TestPDFCompare();

	}

	//@Test(priority = 9)
	public void DatabaseValidation() throws Exception {
		DatabaseConnection db = new DatabaseConnection();
		db.setUp(databasename);
		String query = "SELECT	* from " + databasename + ".dbo.AutoRedactionTestResults";
		db.getqueryresults(query);
	}

	@DataProvider(name = "Datasets")
	public static Object[][] offsets() {

		return new Object[][] { { 100, 200, 1 }, { 150, 202, 2 }, { 200, 205, 3 }, { 300, 207, 4 }, { 400, 208, 5 },
				{ 500, 210, 6 } };
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			lp.logout();
		} finally {
			lp.quitBrowser();
			LoginPage.clearBrowserCache();
		}

	}
}

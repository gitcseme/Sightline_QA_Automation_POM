package sightline.ingestion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_Consilio {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	Input ip;
	DocExplorerPage docExplorer;
	String ingestionDataName;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip = new Input();
		ip.loadEnvConfig();
		ingestionDataName = Input.IngestionName_PT;
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		ingestionPage = new IngestionPage_Indium(driver);
		loginPage = new LoginPage(driver);	
		
	}
	
	@Test(enabled = true, groups = { "regression" })
	public void mappedIngestion() throws Exception {
		UserManagement userManagement=new UserManagement(driver);
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject();
		
				ingestionPage = new IngestionPage_Indium(driver);
				baseClass.stepInfo("Ingestion Access Verification");
				userManagement.verifyIngestionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);
				ingestionPage.navigateToIngestionPage();
				ingestionPage.IngestionSmoke(Input.AllSourcesFolder);
				ingestionPage.publishedIngestionName();
				 SessionSearch search = new SessionSearch(driver);
				 search.basicContentSearch(ingestionPage.IngestionName);
				 search.bulkRelease("Default Security Group");
				 loginPage.logout();
	}
	
	@Test(enabled = true, groups = { "regression" })
	public void DragNDropUpload() throws Exception {
		UserManagement userManagement=new UserManagement(driver);
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String datasetname=Input.SmokeDatasetUploadData;
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		baseClass.selectproject();
				baseClass.stepInfo("Dataset Access Verification");
				userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);
				baseClass.stepInfo("Ingestion started for dataset ****"+datasetname+"***");
		 		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\"+datasetname+"";
		 		baseClass.stepInfo(testFolderPath);
		 		baseClass.passedStep("User logged in successfully for PA user");

		 		dataSets=new DataSets(driver);
		 		
		 		Actions actions = new Actions(driver.getWebDriver());
		 		baseClass.waitForElement(dataSets.getdatasetleftmenuBtn());
				actions.moveToElement(dataSets.getdatasetleftmenuBtn().getWebElement()).clickAndHold().build().perform();
				dataSets.getdatasetleftsubmenuBtn().Click();
				
		 		String dataset = datasetname+ Utility.dynamicNameAppender();
		 		String dcustodian ="Auto" + Utility.dynamicNameAppender();
		 		String ddisc = datasetname+ Utility.dynamicNameAppender();
		 		dataSets.setdatasetdetails(dataset,dcustodian, ddisc);
		 		baseClass.passedStep("Dataset has been created successfully");
				driver.waitForPageToBeReady();
				System.out.println(testFolderPath);
				
				dataSets.uploadFilesByFolder(testFolderPath);
				baseClass.passedStep(datasetname+" folder has been uploaded");
				Thread.sleep(2000);
				dataSets.getUploadFilesBtn().waitAndClick(10);
				dataSets.getInitatePopupYesBtn().waitAndClick(20);
				 baseClass.passedStep("Clicked on Yes button");
				 baseClass.passedStep("Processing has been started"); 
				 System.out.println("Processing has been started");

				
				driver.getWebDriver().get(Input.url+"DocExplorer/Explorer");
				dataSets.navigateToDataSetsPage();
				driver.waitForPageToBeReady();
				baseClass.waitForElement(dataSets.getSearchTheFile());
				dataSets.getSearchTheFile().SendKeys(dataset);
				baseClass.waitForElement(dataSets.getClkSearch());
				dataSets.getClkSearch().waitAndClick(5);
				 System.out.println(dataset);
				dataSets.VerifyLastStatusOfCollection("Published", 25, dataset);

	}
	
	@Test(enabled = true, groups = { "regression" })
	public void createProjectAndAssignUser() {
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
        baseClass.stepInfo("logged In  as SA user");
        ProjectPage project=new ProjectPage(driver);
        String ProjectName="smokeProject"+ Utility.dynamicNameAppender();
        project.navigateToProductionPage();
        project.AddSmokeDomainProject(ProjectName, Input.domainName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.pa1FullName);
        user.AssignUserToProject(ProjectName, "Review Manager", Input.rmu1FullName);
        user.AssignUserToProject(ProjectName, "Reviewer", Input.rev1FullName);
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

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR INGESTION EXECUTED******");

	}
	
}

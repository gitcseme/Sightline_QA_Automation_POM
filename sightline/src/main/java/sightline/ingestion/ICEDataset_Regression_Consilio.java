package sightline.ingestion;

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
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ICEDataset_Regression_Consilio {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass bc;
	DataSets dataSets;
	Input ip;

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		ip=new Input();
		ip.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
	

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		
		bc = new BaseClass(driver);
		dataSets=new DataSets(driver);
		loginPage = new LoginPage(driver);
		
	}
	
	@Test(description="RPMXCON-69054",dataProvider="dataSetDetails",groups={"regression"})
 	public void ICECreateDatasetsDetailsWIthSplChars(String dataset,String dcustodian,String ddisc) throws Exception
 	{
		bc.stepInfo("Verify that error message does not display and application accepts - when Dataset name is entered with special characters < > & ‘");
		bc.stepInfo("Test case Id:RPMXCON-69054");
 		
		// Login as PA
 		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
 		bc.passedStep("User logged in successfully for PA user");
 		
 		// navigate to Dataset page
		dataSets.navigateToDataSetsPage();	

 		
 		//create dataset with special characters
 		dataSets.setdatasetdetails(dataset,dcustodian, ddisc);
 		bc.passedStep("dataSet details creation failed for special chars");
 		
 		loginPage.logout();
 	}
	@Test(description="RPMXCON-69055",dataProvider="dataSetDetails",groups={"regression"})
 	public void ICEEditDatasetsDetailsWIthSplChars(String dataset,String dcustodian,String ddisc) throws Exception
 	{
		bc.stepInfo("Verify that error message display and application does NOT accepts - when Dataset & project password is edited with special characters < > & ‘");
		bc.stepInfo("Test case Id:RPMXCON-69055");
 		
		// Login as PA
 		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
 		bc.passedStep("User logged in successfully for PA user");
 		
 		// navigate to Dataset page
		dataSets.navigateToDataSetsPage();	
		
		String CreateDataset= "CreateData"+Utility.dynamicNameAppender();
		String CreateCustodian= "CreateCust"+Utility.dynamicNameAppender();
		String CreateDescription= "CreateDescription"+Utility.dynamicNameAppender();
		
		//create dataset with special characters
 		dataSets.setdatasetdetails(CreateDataset,CreateCustodian, CreateDescription);
 		
 		
 		
 		
 		//update dataset with special characters
 		dataSets.updateDatasetDetails(dataset,dcustodian, ddisc);
 		bc.passedStep("update dataset details failed for special chars");
 		
 		String ProjectPwd="Password"+Utility.dynamicNameAppender()+"<'>&";
 		
 		//update dataset project pwd with special characters
 		dataSets.updateProjectPwds(ProjectPwd);
 		bc.passedStep("Update dataSet project pwd details passed for special chars");
 		
 		loginPage.logout();
 	}
	
	@DataProvider(name = "dataSetDetails")
	public Object[][] dataSetDetails() {
		Object[][] dataSetDetails = { 
				{"datasetname"+ Utility.dynamicNameAppender()+"<'>&","Auto" + Utility.dynamicNameAppender(),"datasetname"+ Utility.dynamicNameAppender()},
				 {"datasetname"+ Utility.dynamicNameAppender(),"Auto" + Utility.dynamicNameAppender()+"<'>&","datasetname"+ Utility.dynamicNameAppender()},
				 {"datasetname"+ Utility.dynamicNameAppender(),"Auto" + Utility.dynamicNameAppender(),"datasetname"+ Utility.dynamicNameAppender()+"<'>&"}
				 };
		return dataSetDetails;
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
		System.out.println("**Executed  QuickBatchRegression2_1 Regression5**");
	}}

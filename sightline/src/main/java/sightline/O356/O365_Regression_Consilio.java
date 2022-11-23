package sightline.O356;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;

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
import pageFactory.BaseClass;
import pageFactory.CollectionPage;
import pageFactory.DataSets;
import pageFactory.LoginPage;
import pageFactory.SourceLocationPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class O365_Regression_Consilio {
	Driver driver;
	LoginPage login;
	BaseClass base;
	UserManagement userManagement;
	DataSets dataSets;
	CollectionPage collection;
	SourceLocationPage source;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		userManagement = new UserManagement(driver);
		dataSets = new DataSets(driver);
		collection = new CollectionPage(driver);
		source = new SourceLocationPage(driver);
	}
	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager" },

		};
		return users;
	}
	
	@Test(description = "RPMXCON-69262",dataProvider = "PaAndRmuUser",enabled = true, groups = { "regression" })
	public void verifySplCharsInEditSourceLocName(String userName, String password, String role) throws Exception {
		
		String[][] userRolesData = { { userName, role, "SA" } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		
		// Login and Pre-requesties
		base.stepInfo("**Step-1 Login as Project Admin/RMU **");
		login.loginToSightLine(userName, password);
		base.stepInfo("User Role : " + role);
		base.stepInfo("**Step-2 Pre-requisites: Collections should be added **");

		// Pre-requesties - Access verification
		base.stepInfo("**Step-3 Go to Datasets > Collections page **");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);
		
		// navigate to source location page
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		
		//check for existing source location to edit
		
		if (!(source.getSourceLocationPageFirstCollectionSelect().isElementAvailable(5))) {
			driver.waitForPageToBeReady();
			base.waitForElement(source.getNewSrcLocationBtn());
			source.getNewSrcLocationBtn().waitAndClick(10);
			base.stepInfo("Clicked create new source location button");
			collection.performAddNewSource(null, dataSourceName, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);
		}
		
		//edit source location for special chars
			
		dataSourceName=source.getSourceLocationPageFirstdataSourceSelect().getText();
		System.out.println("dataSourceName :-"+dataSourceName);
		base.waitForElement(source.getSrcActionBtn(dataSourceName, "Edit"));
		(source.getSrcActionBtn(dataSourceName, "Edit"
				)).waitAndClick(10);
		base.waitForElement(source.getSetUpASourceLocationEditYesButton());
		source.getSetUpASourceLocationEditYesButton().waitAndClick(10);	
		
		String EditSourcelocation=dataSourceName+"<&'>";
		source.performEditSource(null, EditSourcelocation, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);		
		
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}

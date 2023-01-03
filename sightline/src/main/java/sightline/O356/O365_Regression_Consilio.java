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
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.CollectionPage;
import pageFactory.DataSets;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
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
	@DataProvider(name = "PaAndRmuUseWithFullName")
	public Object[][] PaAndRmuUseWithFullNamer() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator",Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager",Input.rmu1FullName },

		};
		return users;
	}
	
	@Test(description = "RPMXCON-69262",dataProvider = "PaAndRmuUser",enabled = true, groups = { "regression" })
	public void verifySplCharsInEditSourceLocName(String userName, String password, String role) throws Exception {
		base.stepInfo("Verify that error message does not display and application accepts - when edit Source location name in collection is entered with special characters < > & ‘");
		base.stepInfo("Test case Id:RPMXCON-69262");
		
		String[][] userRolesData = { { userName, role, "SA" } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		
		// Login and Pre-requesties
		base.stepInfo("**Step-1 Login as Project Admin/RMU **");
		login.loginToSightLine(userName, password);
		base.stepInfo("logged in as "+role+" user");
		base.stepInfo("User Role : " + role);
		

		// Pre-requesties - Access verification
		base.stepInfo("**Step-2 Go to Datasets > Collections page **");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);
		
		
		// navigate to source location page
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		
		//check for existing source location to edit
		
		if (!(source.getSourceLocationPageFirstCollectionSelect().isElementAvailable(5))) {
			base.stepInfo("**Step-3 Pre-requisites: Collections should be added **");
			driver.waitForPageToBeReady();
			base.waitForElement(source.getNewSrcLocationBtn());
			source.getNewSrcLocationBtn().waitAndClick(10);
			base.stepInfo("Clicked create new source location button");
			collection.performAddNewSource(null, dataSourceName, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);
		}
		
		//edit source location for special chars
		base.stepInfo("**Step-4 : edit the source location with special characters**");
		dataSourceName=source.getSourceLocationPageFirstdataSourceSelect().getText();
		System.out.println("dataSourceName :-"+dataSourceName);
		base.waitForElement(source.getSrcActionBtn(dataSourceName, "Edit"));
		(source.getSrcActionBtn(dataSourceName, "Edit"
				)).waitAndClick(10);
		base.waitForElement(source.getSetUpASourceLocationEditYesButton());
		source.getSetUpASourceLocationEditYesButton().waitAndClick(10);	
		
		String SourcelocationWithSplChars="dataSourceName<&'>"+Utility.dynamicNameAppender();
		String TenantIDWithSplChars= Input.TenantID+"<&'>";
		String ApplicationIDWithSplChars=Input.ApplicationID+"<&'>";
		String ApplicationKeyWithSplChars=Input.ApplicationKey+"<&'>";
		source.performEditSource(null, SourcelocationWithSplChars, TenantIDWithSplChars, ApplicationIDWithSplChars, ApplicationKeyWithSplChars);		
		
	}
	@Test(description = "RPMXCON-69186",dataProvider = "PaAndRmuUser",enabled = true, groups = { "regression" })
	public void verifyCollectionIsSuccessfulWithDirectoryNameWhiteSpaces(String userName, String password, String role) throws Exception {
		base.stepInfo("Verify Collection if directory name contains leading and trailing white spaces");
		base.stepInfo("Test case Id:RPMXCON-69186");
		base.stepInfo("logged in as "+role+" user");
		
		String[][] userRolesData = { { userName, role, "SA" } };
		String collectionEmailId = Input.collectionJebEmailId;
		String firstName = "Jeb";
		String lastName = "Bush";
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "05 May 2002 Public.pst";
		String subFolderName="QA Space folder";
		String headerListDS[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.collectionDataHeader4, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String CollectionName = "Collection" + Utility.dynamicNameAppender();
		String TenantID=Input.TenantIDJeb;
		String ApplicationID=Input.ApplicationIDJeb;
		String ApplicationKey=Input.ApplicationKeyJeb;
		
		// Login and Pre-requesties
				login.loginToSightLine(userName, password);
				base.stepInfo("User Role : " + role);

				// Pre-requesties - Access verification
				base.stepInfo("Collection Access Verification");
				userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

				// navigate to Collection page
				dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

				// Click create New Collection
				collection.performCreateNewCollection();

				// create Jeb Bush source as per Test data
				collection.performAddNewSource(null, dataSourceName, TenantID, ApplicationID, ApplicationKey);

				// click created source location and verify navigated page
				HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(dataSourceName, CollectionName,
						false);
				System.out.println("collectionInfoPage:-"+collectionInfoPage);

				
				// Initiate collection process
				collection.selectInitiateCollectionOrClickNext(true, true, true);
 
				// DataSet creation with selected subFolder as per Test data
				collection.fillinDS(CollectionName, firstName, lastName, collectionEmailId, selectedApp, collectionInfoPage,
						selectedFolder, headerListDS, "Button", 3, false, "Save", true, subFolderName);

				// Start A Collection
				collection.clickOnNextAndStartAnCollection();
				dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
				driver.waitForPageToBeReady();

				// Verify Collection presence with expected Status
				collection.verifyExpectedCollectionStatus(false, headerListDataSets, CollectionName, statusListToVerify, 20,
						true, false, "", "");

				// Completed status check
				collection.verifyStatusUsingContainsTypeII(headerListDataSets, CollectionName, statusList, 10);
				driver.waitForPageToBeReady();

				
		
	}
	@Test(description = "RPMXCON-69052",dataProvider = "PaAndRmuUser",enabled = true, groups = { "regression" })
	public void verifySplCharsWhileEnteringSourceLocName(String userName, String password, String role) throws Exception {
		base.stepInfo("Verify that error message does not display and application accepts - when Source location name in collection is entered with special characters < > & ‘");
		base.stepInfo("Test case Id:RPMXCON-69052");
		
		String[][] userRolesData = { { userName, role, "SA" } };
		
		// Login and Pre-requesties
		base.stepInfo("**Step-1 Login as Project Admin/RMU **");
		login.loginToSightLine(userName, password);
		base.stepInfo("logged in as "+role+" user");
		base.stepInfo("User Role : " + role);
		

		// Pre-requesties - Access verification
		base.stepInfo("**Step-2 Go to Datasets > Collections page **");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);
		
		// navigate to source location page
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		
		//check for special chars in source location while entering new source name
		
		String SourcelocationWithSplChars="dataSourceName<&'>"+Utility.dynamicNameAppender();
		String TenantIDWithSplChars= Input.TenantID+"<&'>";
		String ApplicationIDWithSplChars=Input.ApplicationID+"<&'>";
		String ApplicationKeyWithSplChars=Input.ApplicationKey+"<&'>";
		
		base.stepInfo("**Step-3 : Add source location with special characters **");
		driver.waitForPageToBeReady();
		base.waitForElement(source.getNewSrcLocationBtn());
		source.getNewSrcLocationBtn().waitAndClick(10);
		base.stepInfo("Clicked create new source location button");
		collection.performAddNewSource(null, SourcelocationWithSplChars, TenantIDWithSplChars, ApplicationIDWithSplChars, ApplicationKeyWithSplChars);
		
	}
	
	@Test(description = "RPMXCON-70320",dataProvider="PaAndRmuUser",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONForExistingProjectCanClickOnSPCbtn(String userName,String password,String role) throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is ON for existing project,Open Sightline Collect button should be displayed under a new section in source location page");
        base.stepInfo("Test case Id:RPMXCON-70320");
        boolean SCpbOToggle=true;
        String directUrl=Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        ProjectPage p=new ProjectPage(driver);
        p.EnableSightlineOnnaToggle(Input.projectName);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);

    }
	
	@Test(description = "RPMXCON-70319",dataProvider="PaAndRmuUser",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFForExistingProjectCanClickOnSPCbtn(String userName,String password,String role) throws Exception {
        base.stepInfo("Verify when “Sightline Collect, Powered by Onna” (SCpbO) toggle is OFF for existing project, 'Open Sightline Collect' button should not be displayed under a new section in source location page");
        base.stepInfo("Test case Id:RPMXCON-70319");
        boolean SCpbOToggle=false;
        String directUrl=Input.OnnaDirectUrl;
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        ProjectPage p=new ProjectPage(driver);
        p.DisableSightlineOnnaToggle(Input.projectName);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);

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

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
	public Object[][] PaAndRmuUseWithFullName() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator",Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager",Input.rmu1FullName },

		};
		return users;
	}
	
//	@Test(description = "RPMXCON-69262",dataProvider = "PaAndRmuUser",enabled = true, groups = { "regression" })
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
	
	@Test(description = "RPMXCON-70318",dataProvider="PaAndRmuUseWithFullName",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFForNewlyCreatedProjectCanClickOnSPCbtn(String userName,String password,String role,String fullName) throws Exception {
        base.stepInfo("Verify when “Sightline Collect, Powered by Onna” (SCpbO) toggle is OFF for newly created project, 'Open Sightline Collect' button should not be displayed under a new section in source location page");
        base.stepInfo("Test case Id:RPMXCON-70318");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String DomainId="DomainId" + Utility.dynamicNameAppender();
        String ClientName="C" + Utility.dynamicNameAppender();
        String ProjectName="ProjectName" + Utility.dynamicNameAppender();
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        ProjectPage project=new ProjectPage(driver);
        ClientsPage client=new ClientsPage(driver);
        client.AddDomainClient(ClientName, DomainId);
        project.navigateToProductionPage();
        project.AddDomainProject(ProjectName, ClientName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, role, fullName);
        toggleIsDisabled=project.DisableSightlineOnnaToggle(ProjectName,toggleIsDisabled);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        base.selectproject(ProjectName);
        base.stepInfo("Navigated to Manage Users page for User Role " +role);
        userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is off for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is off for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        driver.waitForPageToBeReady();  
        
        if(toggleIsDisabled) {
        	login.logout();
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was disabled as part of this TC");
        	project.navigateToProductionPage();
        	project.EnableSightlineOnnaToggle(ProjectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
    }
	
	@Test(description = "RPMXCON-70319",dataProvider="PaAndRmuUser",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFForExistingProjectCanClickOnSPCbtn(String userName,String password,String role) throws Exception {
        base.stepInfo("Verify when “Sightline Collect, Powered by Onna” (SCpbO) toggle is OFF for existing project, 'Open Sightline Collect' button should not be displayed under a new section in source location page");
        base.stepInfo("Test case Id:RPMXCON-70319");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        ProjectPage p=new ProjectPage(driver);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        base.stepInfo("Navigated to Manage Users page for User Role " +role);
        userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is off for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is off for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        driver.waitForPageToBeReady();
       
        if(toggleIsDisabled) {
        	login.logout();
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was disabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}
	
	@Test(description = "RPMXCON-70320",dataProvider="PaAndRmuUser",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONForExistingProjectCanClickOnSPCbtn(String userName,String password,String role) throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is ON for existing project,Open Sightline Collect button should be displayed under a new section in source location page");
        base.stepInfo("Test case Id:RPMXCON-70320");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        ProjectPage p=new ProjectPage(driver);
        p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        base.stepInfo("Navigated to Manage Users page for User Role " +role);
        userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is  Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
        
        if(toggleIsEnabled) {
        	
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was enabled as part of this TC");
        	p.navigateToProductionPage();
        	p.DisableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+Input.projectName);
        	
        }

    }
	@Test(description = "RPMXCON-70317",dataProvider="PaAndRmuUseWithFullName",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONForNewlyCreatedProjectCanClickOnSPCbtn(String userName,String password,String role,String fullName) throws Exception {
        base.stepInfo("Verify when “Sightline Collect, Powered by Onna” (SCpbO) toggle is ON for newly created project, 'Open Sightline Collect' button should be displayed under a new section in source location page");
        base.stepInfo("Test case Id:RPMXCON-70317");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        String DomainId="DomainId" + Utility.dynamicNameAppender();
        String ClientName="C" + Utility.dynamicNameAppender();
        String ProjectName="ProjectName" + Utility.dynamicNameAppender();
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        ProjectPage project=new ProjectPage(driver);
        ClientsPage client=new ClientsPage(driver);
        client.AddDomainClient(ClientName, DomainId);
        project.navigateToProductionPage();
        project.AddDomainProject(ProjectName, ClientName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, role, fullName);
        project.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        
        base.selectproject(ProjectName);
        base.stepInfo("Navigated to Manage Users page for User Role " +role);
        userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is  Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
        if(toggleIsEnabled) {
        	
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was enabled as part of this TC");
        	project.navigateToProductionPage();
        	project.DisableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+Input.projectName);
        	
        }

    }
	
	@Test(description = "RPMXCON-70379",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFExistingProjectbySAUserImpersonatedToPA_RMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is OFF for existing project, \"Open Sightline Collect\" button should not be displayed under a new section in source location page  for SA user when impersonated as PA/RMU role");
        base.stepInfo("Test case Id:RPMXCON-70379");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String[][] userRolesDataPA = { { Input.pa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
     


        // Login and Pre-requesties
        base.impersonateSAtoPA(Input.projectName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.sa1userName, Input.sa1password, Input.pa1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is off for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is off for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        driver.waitForPageToBeReady();
        login.logout();
        
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged Out and logged In  as SA user");
        base.impersonateSAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is off for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is off for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        driver.waitForPageToBeReady();
       
        if(toggleIsDisabled) {
        	login.logout();
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was disabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}
	@Test(description = "RPMXCON-70380",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFExistingProjectbyPAUserImpersonatedToRMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is OFF for existing project, \"Open Sightline Collect\" button should not be displayed under a new section in source location page  for PA user when impersonated as RMU role");
        base.stepInfo("Test case Id:RPMXCON-70380");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        base.stepInfo("Diabled the Sightline Connect toggle for "+Input.projectName);
        login.logout();
        base.stepInfo("logged Out  as SA user");


        // Login and Pre-requesties
        login.loginToSightLine(Input.pa1userName, Input.pa1password);
        base.stepInfo(" logged In as PA user");
        base.stepInfo("User Role : Project Administrator");
        
        base.impersonatePAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is off for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is off for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        driver.waitForPageToBeReady();
       
        if(toggleIsDisabled) {
        	login.logout();
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was disabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}
	
	@Test(description = "RPMXCON-70377",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFNewlyCreatedProjectbySAUserImpersonatedToPA_RMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is OFF for newly created project, \"Open Sightline Collect\" button should not be displayed under a new section in source location page  for SA user when impersonated as PA/RMU rolee");
        base.stepInfo("Test case Id:RPMXCON-70377");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String ProjectName="ProjectName" + Utility.dynamicNameAppender();
        String[][] userRolesDataPA = { { Input.pa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage project=new ProjectPage(driver);
        project.navigateToProductionPage();
        project.AddDomainProject(ProjectName, Input.domainName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.pa1FullName);
        toggleIsDisabled=project.DisableSightlineOnnaToggle(ProjectName,toggleIsDisabled);
     


        // Login and Pre-requesties
        base.impersonateSAtoPA(ProjectName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.sa1userName, Input.sa1password, Input.pa1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is off for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is off for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        driver.waitForPageToBeReady();
        login.logout();
        
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged Out and logged In  as SA user");
        base.impersonateSAtoRMU(ProjectName);
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is off for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is off for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        driver.waitForPageToBeReady();
       
        if(toggleIsDisabled) {
        	login.logout();
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was disabled as part of this TC");
        	project.navigateToProductionPage();
        	project.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}
	@Test(description = "RPMXCON-70382",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONExistingProjectbyPAUserImpersonatedToRMU() throws Exception {
        base.stepInfo("Verify when  (SCpbO) toggle is ON for existing project, \"Open Sightline Collect\" button should be displayed under a new section in source location page  for PA user when impersonated as RMU role");
        base.stepInfo("Test case Id:RPMXCON-70382");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage project=new ProjectPage(driver);
        toggleIsEnabled=project.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        login.logout();
        base.stepInfo("logged Out  as SA user");
        


        // Login and Pre-requesties
        login.loginToSightLine(Input.pa1userName, Input.pa1password);
        base.stepInfo(" logged In as PA user");
        base.stepInfo("User Role : Project Administrator");
        
        base.impersonatePAtoRMU(Input.projectName);
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Enplayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
        if(toggleIsEnabled) {
        	
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was enabled as part of this TC");
        	project.navigateToProductionPage();
        	project.DisableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+Input.projectName);
        	
        }

       
	}
	@Test(description = "RPMXCON-70381",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONExistingProjectbySAUserImpersonatedToPA_RMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is ON for existing project, \"Open Sightline Collect\" button should be displayed under a new section in source location page  when clicked should navigate to ONNA page for SA user when impersonated as PA/RMU role");
        base.stepInfo("Test case Id:RPMXCON-70381");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        String[][] userRolesDataPA = { { Input.pa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsEnabled=p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
     


        // Login and Pre-requesties
        base.impersonateSAtoPA(Input.projectName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.sa1userName, Input.sa1password, Input.pa1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
        
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged Out and logged In  as SA user");
        base.impersonateSAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
       
        if(toggleIsEnabled) {
        	
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was Enabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+Input.projectName);
        	
        }
       
	}
	@Test(description = "RPMXCON-70331",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONExistingProjectbyDAUserImpersonatedToPA_RMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is ON for existing project, \"Open Sightline Collect\" button should be displayed under a new section in source location page when clicked should navigate to ONNA page for DA user when impersonated as PA/RMU role");
        base.stepInfo("Test case Id:RPMXCON-70331");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        String[][] userRolesDataPA = { { Input.pa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
        String[][] userRolesDataDA = { { Input.da1userName,"Domain Administrator", "SA" } };
        login.loginToSightLine(Input.da1userName, Input.da1password);
        userManagement.verifyDomainProjectsAccess(userRolesDataDA, Input.sa1userName, Input.sa1password, Input.da1password);
        base.stepInfo("logged In  as DA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsEnabled=p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
     


        // Login and Pre-requesties
        base.impersonateDAtoPA();
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.sa1userName, Input.sa1password, Input.pa1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
        
        login.loginToSightLine(Input.da1userName, Input.da1password);
        base.stepInfo("logged Out and logged In  as DA user");
        base.impersonateDAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
       
        if(toggleIsEnabled) {
        	
            login.loginToSightLine(Input.da1userName, Input.da1password);
            base.stepInfo("logged out and logged In as DA user");
        	base.stepInfo("Toggle was Enabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+Input.projectName);
        	
        }
       
	}
	@Test(description = "RPMXCON-70441",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFExistingProjectbyDAUserImpersonatedToPA_RMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is OFF for existing project, \"Open Sightline Collect\" button should not be displayed under a new section in source location page for DA user when impersonated as PA/RMU role");
        base.stepInfo("Test case Id:RPMXCON-70441");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        
        String[][] userRolesDataPA = { { Input.pa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
        String[][] userRolesDataDA = { { Input.da1userName,"Domain Administrator", "SA" } };
        login.loginToSightLine(Input.da1userName, Input.da1password);
        userManagement.verifyDomainProjectsAccess(userRolesDataDA, Input.sa1userName, Input.sa1password, Input.da1password);
        base.stepInfo("logged In  as DA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
     


        // Login and Pre-requesties
        base.impersonateDAtoPA();
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.sa1userName, Input.sa1password, Input.pa1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is OFF for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is OFF for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        login.logout();
        login.loginToSightLine(Input.da1userName, Input.da1password);
        base.stepInfo("logged Out and logged In  as DA user");
        base.impersonateDAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is OFF for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is OFF for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        login.logout();
       
        if(toggleIsDisabled) {
        	
            login.loginToSightLine(Input.da1userName, Input.da1password);
            base.stepInfo("logged out and logged In as DA user");
        	base.stepInfo("Toggle was Disabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}
	
	@Test(description = "RPMXCON-70378",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFNewlyCreatedProjectbyPAUserImpersonatedToRMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is OFF for newly created project, \"Open Sightline Collect\" button should not be displayed under a new section in source location page  for PA user when impersonated as RMU role");
        base.stepInfo("Test case Id:RPMXCON-70378");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String ProjectName="ProjectName" + Utility.dynamicNameAppender();
        String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage project=new ProjectPage(driver);
        project.navigateToProductionPage();
        project.AddDomainProject(ProjectName, Input.domainName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.pa1FullName);
        project.DisableSightlineOnnaToggle(ProjectName,toggleIsDisabled);
        login.logout();
        base.stepInfo("logged Out  as SA user");


        // Login and Pre-requesties
        login.loginToSightLine(Input.pa1userName, Input.pa1password);
        base.stepInfo(" logged In as PA user");
        base.stepInfo("User Role : Project Administrator");
        
        base.impersonatePAtoRMU(ProjectName);
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is OFF for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is OFF for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        base.switchTab(0);
        login.logout();
        if(toggleIsDisabled) {
     
            login.loginToSightLine(Input.sa1userName, Input.sa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was Disabled as part of this TC");
        	project.navigateToProductionPage();
        	project.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}
	
	@Test(description = "RPMXCON-70376",enabled = true, groups = { "regression" })
	 public void verifySCpbOToggleONNewlyCreatedProjectbyPAUserImpersonatedToRMU() throws Exception {
		base.stepInfo("Verify when (SCpbO) toggle is ON for newly created project, \"Open Sightline Collect\" button should be displayed under a new section in source location page when clicked should navigate to ONNA for PA user when impersonated as RMU role");
		base.stepInfo("Test case Id:RPMXCON-70376");
		boolean SCpbOToggle=true;
		
		boolean toggleIsEnabled=false;
		String directUrl=Input.url+Input.OnnaDirectUrl;
		String OnnaUrl=Input.OnnaUrl;
		String ProjectName="ProjectName" + Utility.dynamicNameAppender();
		System.out.println(ProjectName);
		String[][] userRolesDataRMU = { { Input.rmu1userName,"Review Manager", "SA" } };
		
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("logged In  as SA user");
		ProjectPage project=new ProjectPage(driver);
		project.navigateToProductionPage();
		project.AddDomainProject(ProjectName, Input.domainName);
		UserManagement user=new UserManagement(driver);
		user.navigateToUsersPAge();
		user.AssignUserToProject(ProjectName, "Project Administrator", Input.pa1FullName);
		user.AssignUserToProject(ProjectName, "Review Manager", Input.rmu1FullName);
		toggleIsEnabled=project.EnableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
		login.logout();
		base.stepInfo("logged Out  as SA user");

	// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo(" logged In as PA user");
		base.stepInfo("User Role : Project Administrator");

		base.impersonatePAtoRMU(ProjectName);
		base.stepInfo("Impersonated as Review Manager user");
		base.stepInfo("User Role : Review Manager");
		base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
		userManagement.verifyCollectionAccess(userRolesDataRMU, Input.sa1userName, Input.sa1password, Input.rmu1password);
		// navigate to source location page2
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.verifySightlineConnectONNAbutton(SCpbOToggle);
		base.stepInfo("Onna button is Enabled when toggle is ON for the project");
		source.verifySightlineConnectONNAText(SCpbOToggle);
		base.stepInfo("Onna Text is  Displayed when toggle is ON for the project");
		source.verifyConnectToONNAbeforeclickingbtn(directUrl);
		base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
		source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
	    base.stepInfo("Navigating to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
			
			if(toggleIsEnabled) {
				login.loginToSightLine(Input.sa1userName, Input.sa1password);
				base.stepInfo("logged out and logged In as SA user");
				base.stepInfo("Toggle was Enabled as part of this TC");
				project.navigateToProductionPage();
				project.EnableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
				base.stepInfo("Toggle is now made Disabled for the project "+Input.projectName);
			
			}
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

//	@AfterClass(alwaysRun = true)
	public void close() {

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}

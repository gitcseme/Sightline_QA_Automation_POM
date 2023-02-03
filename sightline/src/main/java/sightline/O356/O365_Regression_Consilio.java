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
	@DataProvider(name = "PaAndRmuOnnaUser")
	public Object[][] PaAndRmuOnnaUser() {
		Object[][] users = { { Input.Onnapa1userName, Input.Onnapa1password, "Project Administrator" },
				{ Input.Onnarmu1userName, Input.Onnarmu1password, "Review Manager" },

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
	@DataProvider(name = "PaAndRmuOnnaUserWithFullName")
	public Object[][] PaAndRmuOnnaUserWithFullName() {
		Object[][] users = { { Input.Onnapa1userName, Input.Onnapa1password, "Project Administrator",Input.Onnapa1FullName },
				{ Input.Onnarmu1userName, Input.Onnarmu1password, "Review Manager",Input.Onnarmu1FullName },

		};
		return users;
	}
	@DataProvider(name = "PaAndRmuOnnaEditUserWithFullName")
	public Object[][] PaAndRmuOnnaEditUserWithFullName() {
		Object[][] users = { { Input.Onnaeditpa1userName, Input.Onnaeditpa1password, "Project Administrator",Input.Onnaeditpa1FullName },
				{ Input.Onnaeditrmu1userName, Input.Onnaeditrmu1password, "Review Manager",Input.Onnaeditrmu1FullName },

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
		base.selectprojectICE();
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
				base.selectprojectICE();

				// Pre-requesties - Access verification
				base.stepInfo("Collection Access Verification");
				userManagement.verifyCollectionAccess(Input.ICEProjectName,userRolesData, Input.sa1userName, Input.sa1password, password);
				base.selectprojectICE();
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
		base.selectprojectICE();
		base.stepInfo("logged in as "+role+" user");
		base.stepInfo("User Role : " + role);
		

		// Pre-requesties - Access verification
		base.stepInfo("**Step-2 Go to Datasets > Collections page **");
		userManagement.verifyCollectionAccess(Input.ICEProjectName,userRolesData, Input.sa1userName, Input.sa1password, password);
		base.selectprojectICE();
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
	
	@Test(description = "RPMXCON-70318",dataProvider="PaAndRmuOnnaUserWithFullName",enabled = true, groups = { "regression" })
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
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
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
        userManagement.verifyCollectionAccess(userRolesData, Input.Onnasa1userName, Input.Onnasa1password, password);

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
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was disabled as part of this TC");
        	project.navigateToProductionPage();
        	project.EnableSightlineOnnaToggle(ProjectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
    }
	
	@Test(description = "RPMXCON-70319",dataProvider="PaAndRmuOnnaUser",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFForExistingProjectCanClickOnSPCbtn(String userName,String password,String role) throws Exception {
        base.stepInfo("Verify when “Sightline Collect, Powered by Onna” (SCpbO) toggle is OFF for existing project, 'Open Sightline Collect' button should not be displayed under a new section in source location page");
        base.stepInfo("Test case Id:RPMXCON-70319");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        ProjectPage p=new ProjectPage(driver);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        base.stepInfo("Navigated to Manage Users page for User Role " +role);
        userManagement.verifyCollectionAccess(userRolesData, Input.Onnasa1userName, Input.Onnasa1password, password);
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
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was disabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}
	
	@Test(description = "RPMXCON-70320",dataProvider="PaAndRmuOnnaUser",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONForExistingProjectCanClickOnSPCbtn(String userName,String password,String role) throws Exception {
    
    	base.stepInfo("Verify when (SCpbO) toggle is ON for existing project,Open Sightline Collect button should be displayed under a new section in source location page");
        
    	base.stepInfo("Test case Id:RPMXCON-70320");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        ProjectPage p=new ProjectPage(driver);
        p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        base.stepInfo("Navigated to Manage Users page for User Role " +role);
        userManagement.verifyCollectionAccess(userRolesData, Input.Onnasa1userName, Input.Onnasa1password, password);

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
        	
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was enabled as part of this TC");
        	p.navigateToProductionPage();
        	p.DisableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+Input.projectName);
        	
        }

    }
	@Test(description = "RPMXCON-70317",dataProvider="PaAndRmuOnnaUserWithFullName",enabled = true, groups = { "regression" })
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
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        ProjectPage project=new ProjectPage(driver);
        ClientsPage client=new ClientsPage(driver);
        client.AddDomainClient(ClientName, DomainId);
        project.navigateToProductionPage();
        project.AddDomainProject(ProjectName, ClientName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, role, fullName);
        toggleIsEnabled=project.EnableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
        login.logout();


        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        
        base.selectproject(ProjectName);
        base.stepInfo("Navigated to Manage Users page for User Role " +role);
        userManagement.verifyCollectionAccess(userRolesData, Input.Onnasa1userName, Input.Onnasa1password, password);

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
        	
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
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
        String[][] userRolesDataPA = { { Input.Onnapa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
     


        // Login and Pre-requesties
        base.impersonateSAtoPA(Input.projectName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnapa1password);
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
        
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged Out and logged In  as SA user");
        base.impersonateSAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
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
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        base.stepInfo("Diabled the Sightline Connect toggle for "+Input.projectName);
        login.logout();
        base.stepInfo("logged Out  as SA user");


        // Login and Pre-requesties
        login.loginToSightLine(Input.Onnapa1userName, Input.Onnapa1password);
        base.stepInfo(" logged In as PA user");
        base.stepInfo("User Role : Project Administrator");
        
        base.impersonatePAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
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
        String[][] userRolesDataPA = { { Input.Onnapa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage project=new ProjectPage(driver);
        project.navigateToProductionPage();
        project.AddDomainProject(ProjectName, Input.domainName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.Onnapa1FullName);
        toggleIsDisabled=project.DisableSightlineOnnaToggle(ProjectName,toggleIsDisabled);
     


        // Login and Pre-requesties
        base.impersonateSAtoPA(ProjectName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnapa1password);
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
        
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged Out and logged In  as SA user");
        base.impersonateSAtoRMU(ProjectName);
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
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
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        System.out.println();
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage project=new ProjectPage(driver);
        toggleIsEnabled=project.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        login.logout();
        base.stepInfo("logged Out  as SA user");
        


        // Login and Pre-requesties
        login.loginToSightLine(Input.Onnapa1userName, Input.Onnapa1password);
        base.stepInfo(" logged In as PA user");
        base.stepInfo("User Role : Project Administrator");
        
        base.impersonatePAtoRMU(Input.projectName);
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
        	
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
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
        String[][] userRolesDataPA = { { Input.Onnapa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsEnabled=p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
     


        // Login and Pre-requesties
        base.impersonateSAtoPA(Input.projectName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnapa1password);
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
        
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged Out and logged In  as SA user");
        base.impersonateSAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
        	
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
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
        String[][] userRolesDataPA = { { Input.Onnapa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        String[][] userRolesDataDA = { { Input.Onnada1userName,"Domain Administrator", "SA" } };
        login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
        userManagement.verifyDomainProjectsAccess(userRolesDataDA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnada1password);
        base.stepInfo("logged In  as DA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsEnabled=p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
     


        // Login and Pre-requesties
        base.impersonateDAtoPA();
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnapa1password);
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
        
        login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
        base.stepInfo("logged Out and logged In  as DA user");
        base.impersonateDAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
        	
            login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
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
        
        String[][] userRolesDataPA = { { Input.Onnapa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        String[][] userRolesDataDA = { { Input.Onnada1userName,"Domain Administrator", "SA" } };
        login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
        userManagement.verifyDomainProjectsAccess(userRolesDataDA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnada1password);
        base.stepInfo("logged In  as DA user");
        ProjectPage p=new ProjectPage(driver);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
     


        // Login and Pre-requesties
        base.impersonateDAtoPA();
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnapa1password);
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
        login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
        base.stepInfo("logged Out and logged In  as DA user");
        base.impersonateDAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
        	
            login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
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
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage project=new ProjectPage(driver);
        project.navigateToProductionPage();
        project.AddDomainProject(ProjectName, Input.domainName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.Onnapa1FullName);
        project.DisableSightlineOnnaToggle(ProjectName,toggleIsDisabled);
        login.logout();
        base.stepInfo("logged Out  as SA user");


        // Login and Pre-requesties
        login.loginToSightLine(Input.Onnapa1userName, Input.Onnapa1password);
        base.stepInfo(" logged In as PA user");
        base.stepInfo("User Role : Project Administrator");
        
        base.impersonatePAtoRMU(ProjectName);
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
     
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was Disabled as part of this TC");
        	project.navigateToProductionPage();
        	project.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+ProjectName);
        	
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
		String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
		
		login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
		base.stepInfo("logged In  as SA user");
		ProjectPage project=new ProjectPage(driver);
		project.navigateToProductionPage();
		project.AddDomainProject(ProjectName, Input.domainName);
		UserManagement user=new UserManagement(driver);
		user.navigateToUsersPAge();
		System.out.println(Input.Onnapa1FullName);
		System.out.println(Input.Onnarmu1FullName);
		user.AssignUserToProject(ProjectName, "Project Administrator", Input.Onnapa1FullName);
		user.AssignUserToProject(ProjectName, "Review Manager", Input.Onnarmu1FullName);
		toggleIsEnabled=project.EnableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
		login.logout();
		base.stepInfo("logged Out  as SA user");

	// Login and Pre-requesties
		login.loginToSightLine(Input.Onnapa1userName, Input.Onnapa1password);
		base.stepInfo(" logged In as PA user");
		base.stepInfo("User Role : Project Administrator");

		base.impersonatePAtoRMU(ProjectName);
		base.stepInfo("Impersonated as Review Manager user");
		base.stepInfo("User Role : Review Manager");
		base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
		userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
				login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
				base.stepInfo("logged out and logged In as SA user");
				base.stepInfo("Toggle was Enabled as part of this TC");
				project.navigateToProductionPage();
				project.EnableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
				base.stepInfo("Toggle is now made Disabled for the project "+ProjectName);
			
			}
	}

	@Test(description = "RPMXCON-70375",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONNewlyCreatedProjectbySAUserImpersonatedToPA_RMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is ON for newly created project, \"Open Sightline Collect\" button should be displayed under a new section in source location page  for SA user when impersonated as PA/RMU role");
        base.stepInfo("Test case Id:RPMXCON-70375");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String OnnaUrl=Input.OnnaUrl;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String[][] userRolesDataPA = { { Input.Onnapa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        String ProjectName="ProjectName" + Utility.dynamicNameAppender();
		System.out.println(ProjectName);
	
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged In  as SA user");
        ProjectPage p=new ProjectPage(driver);
		p.navigateToProductionPage();
		p.AddDomainProject(ProjectName, Input.domainName);
		base.stepInfo("new project is successfully created");
		UserManagement user=new UserManagement(driver);
		user.navigateToUsersPAge();
		user.AssignUserToProject(ProjectName, "Project Administrator", Input.Onnapa1FullName);
		user.AssignUserToProject(ProjectName, "Review Manager", Input.Onnarmu1FullName);
        toggleIsEnabled=p.EnableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
     


        // Login and Pre-requesties
        base.impersonateSAtoPA(Input.projectName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnapa1password);
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
      
        
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged Out and logged In  as SA user");
        base.impersonateSAtoRMU();
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
	    base.stepInfo("Navigated to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
       
        if(toggleIsEnabled) {
//        	login.logout();
            login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was Enabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+ProjectName);
        	
        }
       
	}
	
	@Test(description = "RPMXCON-70439",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleOFFNewlyCreatedProjectbyDAUserImpersonatedToPA_RMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is OFF for newly created project, \"Open Sightline Collect\" button should not be displayed under a new section in source location page for DA user when impersonated as PA/RMU role");
        base.stepInfo("Test case Id:RPMXCON-70439");
        boolean SCpbOToggle=false;
        boolean toggleIsDisabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        
        String[][] userRolesDataPA = { { Input.Onnapa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        String[][] userRolesDataDA = { { Input.Onnada1userName,"Domain Administrator", "SA" } };
        login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
        userManagement.verifyDomainProjectsAccess(userRolesDataDA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnada1password);
        base.stepInfo("logged In  as DA user");
        ProjectPage p=new ProjectPage(driver);
        String ProjectName="ProjectName" + Utility.dynamicNameAppender();
		System.out.println(ProjectName);
	

        p.navigateToProductionPage();
		p.AddProjectByDomainUser(ProjectName);
		base.stepInfo("new project is successfully created");
		UserManagement user=new UserManagement(driver);
		user.navigateToUsersPAge();
		user.AssignUserToProject(ProjectName, "Project Administrator", Input.Onnapa1FullName);
		user.AssignUserToProject(ProjectName, "Review Manager", Input.Onnarmu1FullName);
        toggleIsDisabled=p.DisableSightlineOnnaToggle(ProjectName,toggleIsDisabled);
     


        // Login and Pre-requesties
        base.impersonateDAtoPA(ProjectName,Input.domainName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnapa1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Disabled when toggle is OFF for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is not Displayed when toggle is OFF for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        login.logout();
        login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
        base.stepInfo("logged Out and logged In  as DA user");
        base.impersonateDAtoRMU(ProjectName,Input.domainName);
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
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
        	
            login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
            base.stepInfo("logged out and logged In as DA user");
        	base.stepInfo("Toggle was Disabled as part of this TC");
        	p.navigateToProductionPage();
        	p.EnableSightlineOnnaToggle(Input.projectName,toggleIsDisabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}

	@Test(description = "RPMXCON-70440",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONNewlyCreatedProjectbyDAUserImpersonatedToPA_RMU() throws Exception {
        base.stepInfo("Verify when (SCpbO) toggle is ON for newly created project, \"Open Sightline Collect\" button should be displayed under a new section in source location page for DA user when impersonated as PA/RMU role");
        base.stepInfo("Test case Id:RPMXCON-70440");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        String[][] userRolesDataPA = { { Input.Onnapa1userName,"Project Administrator", "SA" } };
        String[][] userRolesDataRMU = { { Input.Onnarmu1userName,"Review Manager", "SA" } };
        String[][] userRolesDataDA = { { Input.Onnada1userName,"Domain Administrator", "SA" } };
        login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
        userManagement.verifyDomainProjectsAccess(userRolesDataDA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnada1password);
        base.stepInfo("logged In  as DA user");
        ProjectPage p=new ProjectPage(driver);
        String ProjectName="ProjectName" + Utility.dynamicNameAppender();
		System.out.println(ProjectName);
	
        
        p.navigateToProductionPage();
		p.AddProjectByDomainUser(ProjectName);
		base.stepInfo("new project is successfully created");
		UserManagement user=new UserManagement(driver);
		user.navigateToUsersPAge();
		user.AssignUserToProject(ProjectName, "Project Administrator", Input.Onnapa1FullName);
		user.AssignUserToProject(ProjectName, "Review Manager", Input.Onnarmu1FullName);
		toggleIsEnabled=p.EnableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
     


        // Login and Pre-requesties
        base.impersonateDAtoPA(ProjectName,Input.domainName);
        base.stepInfo("Impersonated as PA user");
        base.stepInfo("User Role : Project Administrator");
        base.stepInfo("Navigated to Manage Users page for User Role Project Administrator");
        
        userManagement.verifyCollectionAccess(userRolesDataPA, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnapa1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
	    base.stepInfo("Navigated to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
        login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
        base.stepInfo("logged Out and logged In  as DA user");
        base.impersonateDAtoRMU(ProjectName,Input.domainName);
        base.stepInfo("Impersonated as Review Manager user");
        base.stepInfo("User Role : Review Manager");
        base.stepInfo("Navigated to Manage Users page for User Role Review Manager");
        userManagement.verifyCollectionAccess(userRolesDataRMU, Input.Onnasa1userName, Input.Onnasa1password, Input.Onnarmu1password);
        // navigate to source location page
        dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
        source.verifySightlineConnectONNAbutton(SCpbOToggle);
        base.stepInfo("Onna button is Enabled when toggle is ON for the project");
        source.verifySightlineConnectONNAText(SCpbOToggle);
        base.stepInfo("Onna Text is Displayed when toggle is ON for the project");
        source.verifyConnectToONNAbeforeclickingbtn(directUrl);
        base.stepInfo("Navigating to following URL "+Input.OnnaDirectUrl +" in new tab denied");
        source.verifyConnectToONNAAfterclickingbtn(OnnaUrl);
	    base.stepInfo("Navigated to following URL "+Input.OnnaUrl +" in new tab & logged In successfully");
       
        if(toggleIsEnabled) {
        	
            login.loginToSightLine(Input.Onnada1userName, Input.Onnada1password);
            base.stepInfo("logged out and logged In as DA user");
        	base.stepInfo("Toggle was Disabled as part of this TC");
        	p.navigateToProductionPage();
        	p.DisableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Enabled for the project "+Input.projectName);
        	
        }
       
	}
	
	@Test(description = "RPMXCON-70368",dataProvider="PaAndRmuOnnaEditUserWithFullName",enabled = true, groups = { "regression" })
    public void verifySCpbOToggleONNewlyCreatedNExistingProjectAfterEditingSLUser(String userName,String password,String role,String fullName) throws Exception {
        base.stepInfo("Edit the existing PA/RMU user details assign it to a new/existing project, (SCpbO) toggle is ON, \"Open Sightline Collect\" button should be displayed under a new section in source location page, after clicking the button it should authenticate to ONNA.");
        base.stepInfo("Test case Id:RPMXCON-70368");
        boolean SCpbOToggle=true;
        boolean toggleIsEnabled=false;
        String directUrl=Input.url+Input.OnnaDirectUrl;
        String OnnaUrl=Input.OnnaUrl;
        UserManagement userManage = new UserManagement(driver);
        String ProjectName="ProjectName" + Utility.dynamicNameAppender();
        System.out.println(ProjectName);
        String[][] userRolesData = { { userName, role, "SA" } };
        login.loginToSightLine(Input.Onnasa1userName, Input.Onnasa1password);
        base.stepInfo("logged In as a SA user");
        ProjectPage project=new ProjectPage(driver);
        project.navigateToProductionPage();
        project.AddDomainProject(ProjectName,Input.domainName);
        userManage.navigateToUsersPAge();
        userManage.AssignUserToProject(ProjectName, role, fullName);
        base.stepInfo("New project is created and users are assigned to it Successfully");
        userManage.navigateToUsersPAge();
		userManage.filterByName(userName);
		String originalUserFirstName = userManage.getUserTableValuesFromManageUserTable("First Name", 1);
		String originalUserLastName = userManage.getUserTableValuesFromManageUserTable("Last Name", 2);
		userManage.selectEditUserUsingPagination(ProjectName, null, null);
		userManage.editFirstAndLastNameInEditUserPopup(originalUserFirstName+"edit", originalUserLastName+"edit");
		base.stepInfo("users for assigned project are edited Successfully");
		toggleIsEnabled= project.EnableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
		 base.stepInfo("Enabled toggle for newly created project");
        login.logout();
        base.stepInfo("logged out of SA user successfully");

        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        
        base.selectproject(ProjectName);
        base.stepInfo("Navigated to Manage Users page for User Role " +role);
        userManagement.verifyCollectionAccess(userRolesData, Input.Onnasa1userName, Input.Onnasa1password, password);

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
        	project.DisableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+ProjectName);
        	
        }
        toggleIsEnabled= project.EnableSightlineOnnaToggle(Input.projectName,toggleIsEnabled);
        base.stepInfo("Enabled toggle for existing project");
        login.logout();
        base.stepInfo("logged out of SA user successfully");

        // Login and Pre-requesties
        base.stepInfo("**Step-1 Login as Project Admin/RMU **");
        login.loginToSightLine(userName, password);
        base.stepInfo("logged in as "+role+" user");
        base.stepInfo("User Role : " + role);
        
        base.selectproject(Input.projectName);
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
        login.loginToSightLine(Input.sa1userName, Input.sa1password);
        if(toggleIsEnabled) {
            base.stepInfo("logged out and logged In as SA user");
        	base.stepInfo("Toggle was enabled as part of this TC");
        	project.navigateToProductionPage();
        	project.DisableSightlineOnnaToggle(ProjectName,toggleIsEnabled);
        	base.stepInfo("Toggle is now made Disabled for the project "+Input.projectName);	
        }
        userManage.navigateToUsersPAge();
		userManage.filterByName(userName);
		userManage.selectEditUserUsingPagination(ProjectName, null, null);
		userManage.editFirstAndLastNameInEditUserPopup(originalUserFirstName, originalUserLastName);
		base.stepInfo("FirstName & LastName of user is now set back to orginal names");
		login.logout();
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

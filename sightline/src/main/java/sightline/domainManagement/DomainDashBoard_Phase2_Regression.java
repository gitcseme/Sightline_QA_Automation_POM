package sightline.domainManagement;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainDashBoard_Phase2_Regression {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;
	DomainDashboard dash;
	ProjectPage project;
	
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
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	}
	
	/**
	 * @author Brundha.T date: 2/11/2022 TestCase Id:RPMXCON-53118 Description
	 *         Verify Active users widget should not be visible for RMU/Reviewer
	 *         user
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53118", enabled = true, groups = { "regression" })
	public void verifyAddingExistingUserUnderSameProject() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53118");
		baseClass.stepInfo("Verify Active users widget should not be visible for RMU/Reviewer user");
		userManage = new UserManagement(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as " + Input.rmu1userName);

		DomainDashboard dash = new DomainDashboard(driver);

		driver.waitForPageToBeReady();
		dash.getEditIcon().waitAndClick(5);
		baseClass.waitForElement(dash.getSelectAddWidget());
		dash.getSelectAddWidget().waitAndClick(5);
		
		baseClass.stepInfo("verifying Active user invisiblity in Widget");
		if (!dash.getWidget().isElementAvailable(2)) {
			baseClass.passedStep(
					"Active user widget is not visible in " + Input.rmu1userName + " dashboard as expected");
		} else {
			baseClass.failedStep("Active user widget is  visible in " + Input.rmu1userName + " dashboard as expected");
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha.T date: 2/11/2022 TestCase Id:RPMXCON-53481 Description
	 *         Validate CustodianNames value
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53481", enabled = true, groups = { "regression" })
	public void validatingCustodiansNamesValue() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53481");
		baseClass.stepInfo("Validate CustodianNames value");
		userManage = new UserManagement(driver);

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in as " + Input.da1userName);
		baseClass.selectproject(Input.domainName);
		
		DomainDashboard dash = new DomainDashboard(driver);
		String FilterPrjt= Input.projectName;
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Selecting the project");
		dash.filterProject(FilterPrjt);
		
		baseClass.waitTime(3);
		baseClass.stepInfo("Verifying the presence of custodians for prject in beehive table");
		List<WebElement> element = dash.getColumValue(baseClass.getIndex(dash.getTableHeader(), "CUSTODIANS (#)"))
				.FindWebElements();
		String Custodians = element.get(0).getText().trim();
		System.out.println(Custodians);
		if (Integer.valueOf(Custodians)>=1 && Integer.valueOf(Custodians) != null) {
			baseClass.passedStep("custodians is displayed for selected project");
		} else {
			baseClass.failedStep("custodians is not displayed for selected project");
		}
		loginPage.logout();

	}
	
	/**
	 * @author Sakthivel RPMXCON-53161
	 * @Description :Validate Project filter
	 */
	@Test(description = "RPMXCON-53161", enabled = true, groups = { "regression" })
    public void verifyingProjectFilter() throws Exception {
 
        base.stepInfo("Test case Id: RPMXCON-53161");
        base.stepInfo("Validate Project filter");
        DomainDashboard domainDash = new DomainDashboard(driver);
        // login as DAU
        loginPage.loginToSightLine(Input.da1userName, Input.da1password);
        base.stepInfo("Successfully login as da user'" + Input.da1userName + "'");
        base.stepInfo("Domain dashboard  is loaded by default as expected");
        driver.waitForPageToBeReady();
        base.selectdomain(Input.domainName);
        base.waitTime(5);
        domainDash.filterProject(Input.projectName);
        base.waitForElement(domainDash.projectStatusFirstValue());
        String actualActiveStatus = domainDash.projectStatusFirstValue().getText();
        if(actualActiveStatus.equalsIgnoreCase("active")) {
            base.passedStep("The staus of active project is displayed ACTIVE as expected");
        }else {
            base.failedStep("The staus of active project is not displayed as ACTIVE");
        }    
        domainDash.clearProjectSearchFilter();
        driver.scrollingToElementofAPage(domainDash.getInactiveProjectToggle());
        domainDash.getInactiveProjectToggle().waitAndClick(10);
        driver.waitForPageToBeReady();
        domainDash.projectStatusTab().waitAndClick(10);
        base.waitTime(5);
        String status = domainDash.projectStatusFirstValue().getText();
        System.out.println(status);
        if (status.equalsIgnoreCase("inactive")) {
            base.waitTime(5);
            String projectname = domainDash.projectNameFirstValue().getText();
            System.out.println(projectname);
            base.waitTime(10);
            domainDash.filterProject(projectname);
            base.waitForElement(domainDash.projectStatusFirstValue());
            base.waitTime(10);
            String actualInActiveStatus = domainDash.projectStatusFirstValue().getText();
            System.out.println(actualInActiveStatus);
            if(actualInActiveStatus.equalsIgnoreCase("inactive")) {
                base.passedStep("The staus of inactive project is displayed INACTIVE as expected");
            }else {
                base.failedStep("The staus of inactive project is not displayed as INACTIVE");
            }    
        } else {
            base.passedStep("No inactive projects in Domain");
        }
    }

	/**
	 * @author Brundha.T date: 2/11/2022 TestCase Id:RPMXCON-53146 
	 * Description To verify if user customize the column list but does
	 *  not save the changes,then previous list should be displayed when 
	 *  user login for the same domain
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53146", enabled = true, groups = { "regression" })
	public void validatingProjectFilter() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53146");
		baseClass.stepInfo("To verify if user customize the column list but does not save the changes,then previous list should be displayed when user login for the same domain");
		
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in as " + Input.da1userName);
		
		baseClass.selectdomain(Input.domainName);
		userManage = new UserManagement(driver);
		
		DomainDashboard dash = new DomainDashboard(driver);
		String[] columns = {"NoOfSecurityGroup","NoOfIngestion"};
		driver.waitForPageToBeReady();

		baseClass.stepInfo("adding the columns in beehive table and save");
		dash.AddOrRemoveColum(columns);
		dash.getSavebtn().waitAndClick(10);

		baseClass.stepInfo("Removing the column without saving it");
		driver.waitForPageToBeReady();
		dash.AddOrRemoveColum(columns);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Relogin as " + Input.da1userName);
		
		baseClass.waitTime(3);
		baseClass.stepInfo("Verifying the presence of selected column in beehive table");
		List<String> tableheadervalues = dash.getColumValues(dash.getTableHeader());
		System.out.println(tableheadervalues);
		
		baseClass.waitTime(2);
		if(tableheadervalues.contains("SECURITY GROUPS (#)")&& tableheadervalues.contains("INGESTIONS (#)")) {
			baseClass.passedStep("User is presented with previous existing column list");
		}else {
			baseClass.failedStep("User is not presented with previous existing column list");
		}
		
		driver.waitForPageToBeReady();
		dash.AddOrRemoveColum(columns);
		dash.getSavebtn().waitAndClick(10);
		
		loginPage.logout();

	}
	
	/**
	 * @Author :Aathith 
	 * date: 21-06-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify Active users widget should not be removed from widget
	 */
	@Test(description = "RPMXCON-53119",enabled = true, groups = {"regression" })
	public void verifyActiveUserNotRemovedByWidget() {
		
		base.stepInfo("Test case Id: RPMXCON-53119");
		base.stepInfo("Verify Active users widget should not be removed from widget");
		
		//login as Da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		dash.getGearbtn().ScrollTo();
		dash.getGearbtn().waitAndClick(10);
		if(!base.textValue("add").isElementAvailable(1)) {
			base.passedStep("Widget add is not be available");
		}else {
			base.failedStep("Widget add is be available");
		}
		if(!base.textValue("remove").isElementAvailable(1)) {
			base.passedStep("Widget remove is not be available");
		}else {
			base.failedStep("Widget remove is be available");
		}
		
		base.passedStep("Verified Active users widget should not be removed from widget");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 21-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate exported values for project grid details from Domain Dashboard for Domain Admin Login
	 */
	@Test(description = "RPMXCON-53131",enabled = true, groups = {"regression" })
	public void validateExportDetails() throws IOException {
		
		base.stepInfo("Test case Id: RPMXCON-53131");
		base.stepInfo("Validate exported values for project grid details from Domain Dashboard for Domain Admin Login");
		
		//login as Da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		String[] colums = {"NoOfCustodian","NoOfIngestion"};
		
		//verify export
		base.clearBullHornNotification();
		driver.scrollingToElementofAPage(dash.getExportbtn());
		dash.getExportbtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		
		//verify nofication, backgroud task
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		int n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		//perfore colum add remove
		dash.AddOrRemoveColum(colums);
		
		//verify notification, backgroud task
		dash.getBackGround().waitAndClick(10);
		dash.getExportbtn().waitAndClick(10);
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		base.passedStep("Validated exported values for project grid details from Domain Dashboard for Domain Admin Login");
		loginPage.logout();
	}
	
	/** 
	 * @Author :Aathith 
	 * date: 21-06-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate exported values for project grid details from Domain Dashboard for Domain Admin Login(Impersonate from System Admin)
	 */
	@Test(description = "RPMXCON-53132",enabled = true, groups = {"regression" })
	public void validateExportDetailsAsSA() throws IOException {
		
		base.stepInfo("Test case Id: RPMXCON-53132");
		base.stepInfo("Validate exported values for project grid details from Domain Dashboard for Domain Admin Login(Impersonate from System Admin)");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a Da user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		String[] colums = {"NoOfCustodian","NoOfIngestion"};
		
		//verify export
		base.impersonateSAtoDA(Input.domainName);
		base.clearBullHornNotification();
		driver.scrollingToElementofAPage(dash.getExportbtn());
		dash.getExportbtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		
		//verify nofication, backgroud task
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		int n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		//perfore colum add remove
		dash.AddOrRemoveColum(colums);
		
		//verify notification, backgroud task
		dash.getBackGround().waitAndClick(10);
		dash.getExportbtn().waitAndClick(10);
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		base.passedStep("Validated exported values for project grid details from Domain Dashboard for Domain Admin Login(Impersonate from System Admin)");
		loginPage.logout();
	}
	
	/**
	 * @throws IOException 
	 * @Author :Aathith date: NA Modified date:NA Modified by:
	 * @Description :Validate exported values for project grid details from Domain Dashboard for Domain Admin user having access to multiple Domains
	 */
	@Test(description = "RPMXCON-53133",enabled = true, groups = {"regression" })
	public void validateExportDetailsAsMultiDomainUser() throws IOException {
		
		base.stepInfo("Test case Id: RPMXCON-53133");
		base.stepInfo("Validate exported values for project grid details from Domain Dashboard for Domain Admin user having access to multiple Domains");
		
		//login as sa
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		String[] colums = {"NoOfCustodian","NoOfIngestion"};
		
		//verify export
		base.impersonateSAtoDA(Input.domainName);
		base.clearBullHornNotification();
		driver.scrollingToElementofAPage(dash.getExportbtn());
		dash.getExportbtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		
		//verify nofication, backgroud task
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		int n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		//perfore colum add remove
		base.switchDomain();
		dash.AddOrRemoveColum(colums);
		
		//verify notification, backgroud task
		dash.getBackGround().waitAndClick(10);
		dash.getExportbtn().waitAndClick(10);
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		base.passedStep("Validated exported values for project grid details from Domain Dashboard for Domain Admin user having access to multiple Domains");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 22-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate Utilization by type details under Utilization widget on Domain dashboard 
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 */
	@Test(description = "RPMXCON-53135",enabled = true, groups = {"regression" })
	public void validateUtilationDetails()  {
		
		base.stepInfo("Test case Id: RPMXCON-53135");
		base.stepInfo("Validate Utilization by type details under Utilization widget on Domain dashboard "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		String[] colums = {"NoOfCustodian","LastUpdatedOn","NoOfPublishedDocument","NoOfReleasedDocument","TotalDBDiskSize","TotalIndexSize","TotalWorkspaceSize"};
		
		dash.makeDomainDashBoardTableDefaultOrder();
		dash.AddOrRemoveColum(colums);
		driver.waitForPageToBeReady();
		base.waitTime(4);
		int n = base.getIndex(dash.getTableHeader(), "TOTAL UTILIZED DISK SIZE (GB)");
		int n1 = base.getIndex(dash.getTableHeader(), "TOTAL DB SIZE (GB)");
		int n2 = base.getIndex(dash.getTableHeader(), "TOTAL SEARCH INDEX SIZE (GB)");
		int n3 = base.getIndex(dash.getTableHeader(), "TOTAL WORKSPACE SIZE (GB)");
		
		//verify total size of utilized size
		dash.verifyTotalUtilizedDiskSpace(dash.getColumValues(dash.getColumValue(n)), dash.getColumValues(dash.getColumValue(n1)), 
				dash.getColumValues(dash.getColumValue(n2)), dash.getColumValues(dash.getColumValue(n3)));
		
		//login as sa
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		dash.makeDomainDashBoardTableDefaultOrder();
		dash.AddOrRemoveColum(colums);
		driver.waitForPageToBeReady();
		base.waitTime(4);		
		//verify total size of utilized size
		dash.verifyTotalUtilizedDiskSpace(dash.getColumValues(dash.getColumValue(n)), dash.getColumValues(dash.getColumValue(n1)), 
				dash.getColumValues(dash.getColumValue(n2)), dash.getColumValues(dash.getColumValue(n3)));
		
		base.passedStep("Validated exported values for project grid details from Domain Dashboard for Domain Admin user having access to multiple Domains");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 23-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate column customization of project grid on Domain dashboard screen (Domain Admin login and System Admin impersonate as Domain Admin)
	 */
	@Test(description = "RPMXCON-53140",enabled = true, groups = {"regression" })
	public void verifyCustomizedProjectGrid()  {
		
		base.stepInfo("Test case Id: RPMXCON-53140");
		base.stepInfo("Validate column customization of project grid on Domain dashboard screen (Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		String[] colums = {"NoOfCustodian","LastUpdatedOn","NoOfPublishedDocument","NoOfReleasedDocument","TotalDBDiskSize","TotalIndexSize","TotalWorkspaceSize"};
		String[] availableColum = {"PROJECT NAME", "STATUS","TOTAL UTILIZED DISK SIZE (GB)","TOTAL DB SIZE (GB)", "TOTAL SEARCH INDEX SIZE (GB)", "TOTAL WORKSPACE SIZE (GB)"};
		dash.makeDomainDashBoardTableDefaultOrder();
		dash.AddOrRemoveColum(colums);
		dash.waitForDomainDashBoardIsReady();
		for(String availablestatus:availableColum) {
			dash.isTitleIsAvailable(availablestatus);
		}
		
		//login as sa
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a Sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		dash.waitForDomainDashBoardIsReady();
		
		dash.makeDomainDashBoardTableDefaultOrder();
		dash.AddOrRemoveColum(colums);
		dash.waitForDomainDashBoardIsReady();
		for(String availablestatus:availableColum) {
			dash.isTitleIsAvailable(availablestatus);
		}
		
		base.passedStep("Validated column customization of project grid on Domain dashboard screen (Domain Admin login and System Admin impersonate as Domain Admin)");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 23-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate column customization of project grid on Domain dashboard screen after changing to different Domain
	 */
	@Test(description = "RPMXCON-53142",enabled = true, groups = {"regression" })
	public void verifyCustomizedProjectGridDiffDomain()  {
		
		base.stepInfo("Test case Id: RPMXCON-53142");
		base.stepInfo("Validate column customization of project grid on Domain dashboard screen after changing to different Domain");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		String[] colums = {"NoOfCustodian","LastUpdatedOn","NoOfPublishedDocument","NoOfReleasedDocument","TotalDBDiskSize","TotalIndexSize","TotalWorkspaceSize"};
		String[] availableColum = {"TOTAL DB SIZE (GB)", "TOTAL SEARCH INDEX SIZE (GB)", "TOTAL WORKSPACE SIZE (GB)"};
		dash.waitForDomainDashBoardIsReady();
		dash.makeDomainDashBoardTableDefaultOrder();
		dash.AddOrRemoveColum(colums);
		dash.waitForDomainDashBoardIsReady();
		for(String availablestatus:availableColum) {
			dash.isTitleIsAvailable(availablestatus);
		}
		
		base.switchDomain();
		dash.waitForDomainDashBoardIsReady();
		dash.makeDomainDashBoardTableDefaultOrder();
		dash.AddOrRemoveColum(colums);
		dash.waitForDomainDashBoardIsReady();
		for(String availablestatus:availableColum) {
			dash.isTitleIsAvailable(availablestatus);
		}
		
		base.passedStep("Validate column customization of project grid on Domain dashboard screen after changing to different Domain");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 29-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify customized column list is saved for selected entity but not for all entities of that user
	 */
	@Test(description = "RPMXCON-53144",enabled = true, groups = {"regression" })
	public void zverifyzCustomizedColumNotForAllEntity()  {
		
		base.stepInfo("Test case Id: RPMXCON-53144");
		base.stepInfo("Verify customized column list is saved for selected entity but not for all entities of that user");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		String[] colums = {"NoOfCustodian","LastUpdatedOn","NoOfPublishedDocument","NoOfReleasedDocument","TotalDBDiskSize","TotalIndexSize","TotalWorkspaceSize"};
		String[] availableColum = {"PROJECT NAME","STATUS","CORPORATE CLIENT","CREATED DATE","CREATED BY","TOTAL UTILIZED DISK SIZE (GB)",
				"TOTAL DB SIZE (GB)", "TOTAL SEARCH INDEX SIZE (GB)", "TOTAL WORKSPACE SIZE (GB)"};
		String[] customColum = {"TOTAL DB SIZE (GB)", "TOTAL SEARCH INDEX SIZE (GB)", "TOTAL WORKSPACE SIZE (GB)"};
		dash.makeDomainDashBoardTableDefaultOrder();
		dash.AddOrRemoveColum(colums);
		driver.waitForPageToBeReady();
		dash.isAllColumsAreAvailable(availableColum);
		dash.getSavebtn().waitAndClick(10);
		
		//verify for another domain
		String previousproject = base.switchDomain();
		dash.waitForDomainDashBoardIsReady();
		base.isTextAreNotAvailableInWebPage(customColum);
		
		//verify for previous domain
		base.selectproject(previousproject);
		dash.waitForDomainDashBoardIsReady();
		dash.isAllColumsAreAvailable(availableColum);
		
		//restore default
		dash.AddOrRemoveColum(colums);
		dash.getSavebtn().waitAndClick(10);
		
		base.passedStep("Verified customized column list is saved for selected entity but not for all entities of that user");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 29-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify column order is not customizable
	 */
	@Test(description = "RPMXCON-53147",enabled = true, groups = {"regression" })
	public void verifyColumnOrderNotCustomizable()  {
		
		base.stepInfo("Test case Id: RPMXCON-53147");
		base.stepInfo("To verify column order is not customizable");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		String[] colums = {"ProjectLabel","IsActive","LastUpdatedOn","NoOfCustodian","NoOfPublishedDocument",
				"NoOfReleasedDocument", "CorpClient", "ProjectCreatedOn", "ProjectCreatedBy", "TotalUtilizedDiskSize",
				"NoOfIngestion","NoOfSecurityGroup","Firm"};
		String[] availableColm = {"FIRM", "SECURITY GROUPS (#)", "INGESTIONS (#)"};
		
		//remove exciting colum and add new colum in non sequencing order
		dash.AddOrRemoveColum(colums);
		List<String> tablehearvalues =dash.getColumValues(dash.getTableHeader());
		
		//verify colum sequnce is not costomizable
		for(int i=0;i<3;i++) {
			if(!availableColm[i].equals(tablehearvalues.get(i))) {
				System.out.println(availableColm[i]+" "+tablehearvalues.get(i));
				base.failedStep(availableColm[i]+"colume order verification failed"+tablehearvalues.get(i));
			}
		}
		base.passedStep("Whichever column is added is in same sequence as seen on settings tab pop up");
		
		base.passedStep("To verify column order is not customizable");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 29-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate customize column for Project Summary grid on Domain Dashboard screen 
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53151",enabled = true, groups = {"regression" })
	public void verifyCostimizeColum() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53151");
		base.stepInfo("Validate customize column for Project Summary grid on Domain Dashboard screen "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		String[] colums = {"ProjectLabel","IsActive","LastUpdatedOn","NoOfCustodian","NoOfPublishedDocument",
				"NoOfReleasedDocument", "CorpClient", "ProjectCreatedOn", "ProjectCreatedBy", "TotalUtilizedDiskSize",
				"NoOfIngestion","NoOfSecurityGroup","Firm"};
		String[] availableColm = {"FIRM", "SECURITY GROUPS (#)", "INGESTIONS (#)"};
		
		//remove exciting colum and add new colum in non sequencing order
		dash.AddOrRemoveColum(colums);
		dash.isAllColumsAreAvailable(availableColm);
		
		//check for another domain
		base.switchDomain();
		dash.AddOrRemoveColum(colums);
		dash.isAllColumsAreAvailable(availableColm);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.da1userName);
		
		base.impersonateSAtoDA(Input.domainName);
		dash.AddOrRemoveColum(colums);
		dash.isAllColumsAreAvailable(availableColm);
		
		base.passedStep("Validated customize column for Project Summary grid on Domain Dashboard screen "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 29-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate customize column retains column selection even after performing any actions on the grid 
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53152",enabled = true, groups = {"regression" })
	public void verifyCostimizeColumRatainAfterPerformAnyAction() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53152");
		base.stepInfo("Validate customize column retains column selection even after performing any actions on the grid "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		String[] colums = {"ProjectLabel","IsActive","LastUpdatedOn","NoOfCustodian","NoOfPublishedDocument",
				"NoOfReleasedDocument", "CorpClient", "ProjectCreatedOn", "ProjectCreatedBy", "TotalUtilizedDiskSize",
				"NoOfIngestion","NoOfSecurityGroup","Firm"};
		String[] availableColm = {"FIRM", "SECURITY GROUPS (#)", "INGESTIONS (#)"};
		
		//remove exciting colum and add new colum in non sequencing order
		dash.AddOrRemoveColum(colums);
		dash.isAllColumsAreAvailable(availableColm);
		dash.getExportbtn().waitAndClick(10);
		dash.isAllColumsAreAvailable(availableColm);
		base.stepInfo("After perform action also costomize colum retain");
		
		//login as SA
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		dash.AddOrRemoveColum(colums);
		dash.isAllColumsAreAvailable(availableColm);
		dash.getExportbtn().waitAndClick(10);
		dash.isAllColumsAreAvailable(availableColm);
		base.stepInfo("After perform action also costomize colum retain");
		
		base.passedStep("Validated customize column retains column selection even after performing any actions on the grid "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 29-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify Inactive projects should not display in active users widget
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53153",enabled = true, groups = {"regression" })
	public void verifyInactiveProjectNotDisplayedInActiveUser() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53153");
		base.stepInfo("Verify Inactive projects should not display in active users widget");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		dash = new DomainDashboard(driver);
		
		String projectName = "AAA"+Utility.dynamicNameAppender();
		if(!dash.isInactiveProjectAvailable()) {
			base.clearBullHornNotification();
			dash.create_a_project_From_Domain(projectName);
			project.waitTillProjectCreated();
			dash.naviageToDomainDashBoardPage();
			dash.deactivateProject(projectName);
		}
		
		String inActiveProjectName = dash.getInactiveProjectName();
		if(!dash.getActiveprojectTableValue(inActiveProjectName).isElementAvailable(1)) {
			base.passedStep("Inactive projects is not show in list of projects on Active users widget");
		}else {
			base.failedStep("project is displayed");
		}
		
		base.passedStep("Verified Inactive projects should not display in active users widget");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 29-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate project summary grid columns on Domain dashboard
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53154",enabled = true, groups = {"regression" })
	public void validateProjectGridColum() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53154");
		base.stepInfo("Validate project summary grid columns on Domain dashboard");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		dash = new DomainDashboard(driver);
		
		String projectName = "AAA"+Utility.dynamicNameAppender();
		
		dash.waitForDomainDashBoardIsReady();
		if(!dash.isInactiveProjectAvailable()) {
			base.clearBullHornNotification();
			dash.create_a_project_From_Domain(projectName);
			project.waitTillProjectCreated();
			dash.naviageToDomainDashBoardPage();
			dash.deactivateProject(projectName);
		}
		String[] availableColm = {"PROJECT NAME","STATUS","DATA AS OF (UTC)",
				"DOCS PUBLISHED (#)","DOCS RELEASED (#)","CORPORATE CLIENT","CREATED DATE",
				"CREATED BY","TOTAL UTILIZED DISK SIZE (GB)"};
		
		dash.waitForDomainDashBoardIsReady();
		dash.makeDomainDashBoardTableDefaultOrder();
		for(String column:availableColm) {
		dash.isTitleIsAvailable(column);
		}
		dash.isActiveInactiveListed();
		
		base.passedStep("Validate project summary grid columns on Domain dashboard");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 06-07-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate for Display inactive Project toggle on Dashboard screen 
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53145",enabled = true, groups = {"regression" })
	public void vaildateDisplayInactiveToggle() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53145");
		base.stepInfo("Validate for Display inactive Project toggle on Dashboard screen "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		dash = new DomainDashboard(driver);
		
		dash.createAndDeactivateProject();
		dash.isActiveInactiveListed();
		dash.clickInactiveProjectName();
		dash.getInactiveProjectToggle().waitAndClick(10);
		dash.verifyOnlyActiveProjectListed();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		dash.isActiveInactiveListed();
		dash.clickInactiveProjectName();
		dash.getInactiveProjectToggle().waitAndClick(10);
		dash.verifyOnlyActiveProjectListed();
		
		base.passedStep("Validated for Display inactive Project toggle on Dashboard screen "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 06-07-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate deactivating a project from Dashboard summary grid
	 */
	@Test(description = "RPMXCON-53160",enabled = true, groups = {"regression" })
	public void validateDeactivateProjectFromDD() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53160");
		base.stepInfo("Validate deactivating a project from Dashboard summary grid");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		softAssertion = new SoftAssert();
		
		String projectName = Input.randomText+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		base.clearBullHornNotification();
		dash.create_a_project_From_Domain(projectName);
		base.waitForNotification();
		dash.naviageToDomainDashBoardPage();
		
		base.stepInfo("project was created");
		
		//cancel the inActivate
		dash.doYouDeactivateProject(projectName);
		base.waitForElement(dash.getPopupMsg());
		String text = dash.getPopupMsg().getText().trim();
		softAssertion.assertEquals(text, "Do you really want to deactivate this project?");
		dash.getNoBtn().waitAndClick(10);
		base.stepInfo("cancel inActivate a project");
		
		
		//deactivate a project
		dash.doYouDeactivateProject(projectName);
		dash.getYesBtn().waitAndClick(10);
		base.stepInfo("deactivate a project");
		
		//verify ascending descending order
		dash.clearProjectSearchFilter();
		dash.verifyAscendingDescendingOrder();
		
		base.passedStep("Validated deactivating a project from Dashboard summary grid");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 06-07-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Domain Dashboard - Deactivating project from grid list
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 */
	@Test(description = "RPMXCON-53128",enabled = true, groups = {"regression" })
	public void verifyDeactivateProject() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53128");
		base.stepInfo("Domain Dashboard - Deactivating project from grid list"
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		softAssertion = new SoftAssert();
		
		String projectName = Input.randomText+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		base.clearBullHornNotification();
		dash.create_a_project_From_Domain(projectName);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName);
		dash.naviageToDomainDashBoardPage();
		
		//cancel the inActivate
		dash.doYouDeactivateProject(projectName);
		dash.getNoBtn().waitAndClick(10);
		base.stepInfo("Action was cancelled and user should be on Domain Dashboard screen");
		
		//deactivate a project
		dash.doYouDeactivateProject(projectName);
		dash.getYesBtn().waitAndClick(10);
		base.stepInfo("project was deactivated");
		
		
		//verify
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		dash.enableInActiveProject();
		base.waitTime(5);
		dash.filterProject(projectName);
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		List<WebElement> element = dash.getColumValue(base.getIndex(dash.getTableHeader(), "STATUS")).FindWebElements();
		String status = element.get(0).getText().trim();
		softAssertion.assertEquals(status, "Inactive");
		System.out.println(status);
		base.passedStep("Project is in Inactive status");
		
		//login as sa
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		projectName = Input.randomText+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		base.clearBullHornNotification();
		dash.create_a_project_From_Domain(projectName);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName);
		dash.naviageToDomainDashBoardPage();
		
		//cancel the inActivate
		dash.doYouDeactivateProject(projectName);
		dash.getNoBtn().waitAndClick(10);
		base.stepInfo("Action was cancelled and user should be on Domain Dashboard screen");
				
		//deactivate a project
		dash.doYouDeactivateProject(projectName);
		dash.getYesBtn().waitAndClick(10);
		base.stepInfo("project was deactivated");
	
		//filter
		driver.waitForPageToBeReady();
		dash.enableInActiveProject();
		base.waitTime(5);
		dash.filterProject(projectName);
		driver.waitForPageToBeReady();
		
		//verify
		dash.waitForDomainDashBoardIsReady();
		List<WebElement> ele = dash.getColumValue(base.getIndex(dash.getTableHeader(), "STATUS")).FindWebElements();
		String status1 = ele.get(0).getText().trim();
		softAssertion.assertEquals(status1, "Inactive");
		System.out.println(status1);
		base.passedStep("Project is in Inactive status");
		softAssertion.assertAll();
		
		base.passedStep("Domain Dashboard - Deactivating project from grid list"
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 06-07-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate creating new Project from Domain dashboard screen 
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 */
	@Test(description = "RPMXCON-53139",enabled = true, groups = {"regression" })
	public void validateNewlyCreatedProject() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53139");
		base.stepInfo("Validate creating new Project from Domain dashboard screen (Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		project = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		
		String projectName = "AAA"+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		base.clearBullHornNotification();
		project.AddDomainProjectViaDaUser(projectName);
		base.waitForNotification();
		dash.naviageToDomainDashBoardPage();
		dash.getNotificationMessage(0, projectName);
		base.stepInfo(projectName+" project was created");
		
		//verify is available
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		dash.enableInActiveProject();
		dash.filterProject(projectName);
		driver.waitForPageToBeReady();
		List<WebElement> element = dash.getColumValue(base.getIndex(dash.getTableHeader(), "PROJECT NAME")).FindWebElements();
		String status = element.get(0).getText().trim();
		System.out.println(status);
		System.out.println(projectName);
		softAssertion.assertEquals(status, projectName);
		base.passedStep("Newly created project is available in the list");
		
		//login as sa
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		projectName = "AAA"+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		base.clearBullHornNotification();
		project.AddDomainProjectViaDaUser(projectName);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName);
		dash.naviageToDomainDashBoardPage();
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		dash.filterProject(projectName);
		
		//verify project is availavle
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		dash.enableInActiveProject();
		dash.filterProject(projectName);
		driver.waitForPageToBeReady();
		List<WebElement> ele = dash.getColumValue(base.getIndex(dash.getTableHeader(), "PROJECT NAME")).FindWebElements();
		String status1 = ele.get(0).getText().trim();
		softAssertion.assertEquals(status1, projectName);
		base.passedStep("Newly created project is available in the list");
		softAssertion.assertAll();
		
		base.passedStep("Validated creating new Project from Domain dashboard screen "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		loginPage.logout();
	}	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}

}

package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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

public class DomainDashBoard_Regression_01 {
	
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

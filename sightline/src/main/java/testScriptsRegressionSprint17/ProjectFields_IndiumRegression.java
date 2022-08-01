package testScriptsRegressionSprint17;

import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

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

import com.aventstack.extentreports.model.Author;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DataSets;

public class ProjectFields_IndiumRegression {
	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	DataSets data;
	IngestionPage_Indium ingestionPage;

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @Author :Baskar Date:14/07/2022
	 * @Description : To verify that Project Field Listing page is displayed.
	 * @throws : Exception
	 */
	@Test(description = "RPMXCON-55594", enabled = true, groups = { "regression" })
	public void verifyProjectFieldHomePage() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55594");
		baseClass.stepInfo(
				"To verify that Project Field Listing page is displayed.");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields=new ProjectFieldsPage(driver);
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName);
		this.driver.getWebDriver().get(Input.url + "ProjectFields/ProjectFieldsList");
		baseClass.stepInfo("User landed on the  ProjectFields page");
		
		// validating all tabs
	    boolean flagManage=driver.getPageSource().contains("Manage Project Fields");
	    softAssertion.assertTrue(flagManage);
	    boolean flagAdd=driver.getPageSource().contains("Add Project Field");
	    softAssertion.assertTrue(flagAdd);
	    boolean flagGroup=driver.getPageSource().contains("Add Project Field Group");
	    softAssertion.assertTrue(flagGroup);
	    List<WebElement> headerData=fields.getProjectFieldHeader().FindWebElements();
	    List<String> find=new LinkedList<String>();
	    List<String> actual =Arrays.asList("FIELD NAME", "DATA TYPE","IS TALLYABLE","IS SEARCHABLE","IS ACTIVE","ACTION");
	    for (WebElement webElement : headerData) {
	    	String value=webElement.getText().toString();
	    	find.add(value);
		}
	    for (int i = 0; i < find.size(); i++) {
	    	 for (int j = 0; j < actual.size(); j++) {
	    	softAssertion.assertEquals(actual.get(j), find.get(i));
	    	i++;
		}  
	    	 } 
	    boolean flagEdit=driver.getPageSource().contains("Edit");
	    softAssertion.assertTrue(flagEdit);
	    boolean flagInactive=driver.getPageSource().contains("Inactive");
	    softAssertion.assertTrue(flagInactive);
	    boolean flagPagination=fields.getPagination().isElementAvailable(2);
	    softAssertion.assertTrue(flagPagination);
	    softAssertion.assertAll();
	    
	    baseClass.passedStep("All functionality tab are displed in projectfieldpages");
	    loginPage.logout();
		
		
	}
	
	/**
	 * @Author :Baskar Date:14/07/2022
	 * @Description : To verify Edit functionality for 'Project Field'.
	 * @throws : Exception
	 */
	@Test(description = "RPMXCON-55607", enabled = true, groups = { "regression" })
	public void validatingEditFunctionalInProjectField() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55607");
		baseClass.stepInfo(
				"To verify Edit functionality for 'Project Field'.");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields=new ProjectFieldsPage(driver);
		String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();
		String editDesc = "edit" + Utility.dynamicNameAppender();
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		baseClass.stepInfo("Adding new project fields");
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");
		fields.applyFilterByFilterName(projectFieldINT);
		
		// validating the edit functionality
		fields.editprojectFieldFieldDescription(projectFieldINT,editDesc);
		baseClass.passedStep("Edit functionality for project fields, working properly");
		
		// verifying the updated value
		fields.applyFilterByFilterName(projectFieldINT);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(fields.getFieldNameEdititButton(projectFieldINT));
		fields.getFieldNameEdititButton(projectFieldINT).waitAndClick(10);
		baseClass.waitForElement(fields.getFieldDescriptionTextArea());
		String updateValue=fields.getFieldDescriptionTextArea().getText();
		softAssertion.assertEquals(updateValue, editDesc);
		softAssertion.assertAll();
		baseClass.stepInfo("updated value saved successfully");
		
	    loginPage.logout();
		
		
	}
	
	/**
	 * @Author :Baskar Date:14/07/2022
	 * @Description : To verify when Sys Admin Impersonate as Project Admin and adds Project Fields
	 * @throws : Exception
	 */
	@Test(description = "RPMXCON-55610", enabled = true, groups = { "regression" })
	public void validatingSAImpPAImp() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55610");
		baseClass.stepInfo(
				"To verify when Sys Admin Impersonate as Project Admin and adds Project Fields");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields=new ProjectFieldsPage(driver);
		String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();
		
		// Login as a PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.sa1userName + "'");
		
		baseClass.impersonateSAtoPA();

		// Custom Field created with INT DataType
		baseClass.stepInfo("Adding new project fields");
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");
		
		// validating project fields added in display
		fields.applyFilterByFilterName(projectFieldINT);
		fields.verifyAppliedFieldNameIsAddedInProjectFieldsTable(projectFieldINT);
		
	    loginPage.logout();
		
		
	}
	
	/**
	 * @Author :Baskar Date:15/07/2022
	 * @Description : To verify functionality of 'Cancel' button, from 'Project Field Group'.
	 * @throws : Exception
	 */
	@Test(description = "RPMXCON-55611", enabled = true, groups = { "regression" })
	public void verifyCancelBtnForProjectGroup() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55611");
		baseClass.stepInfo(
				"To verify functionality of 'Cancel' button, from 'Project Field Group'.");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields=new ProjectFieldsPage(driver);
		String groupName = "GroupName" + Utility.dynamicNameAppender();
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
		
		// validating function of cancel button for project group
		baseClass.stepInfo("Clicking add project field group");
		this.driver.getWebDriver().get(Input.url + "ProjectFields/ProjectFieldsList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(fields.getAddProjectFieldGroup());
		fields.getAddProjectFieldGroup().waitAndClick(5);
		fields.getAddFieldGroupName().SendKeys(groupName);
		fields.getGroupCancelBtn().waitAndClick(5);
		
		// verify after cancel, text should not present in drop down
		baseClass.waitForElement(projectPage.getAddProjectButton());
		projectPage.getAddProjectButton().waitAndClick(5);
		fields.getFieldGroupNameDp_Dwn().waitAndClick(5);
		List<WebElement> allValue=fields.getFieldGroupNameDp_DwnValue().FindWebElements();
		for (WebElement webElement : allValue) {
			boolean flag=webElement.getText().equals(groupName);
			if (!flag) {
				baseClass.passedStep("Group name not displyed in drop down");
				baseClass.stepInfo("Cancel button functionality its wotking fine");
			}
			else {
				baseClass.failedStep("Cancel button functionality not as expected");
			}
		}
		
	    loginPage.logout();
	}
	
	/**
	 * @Author :Baskar Date:13/07/2022
	 * @Description : Verify list of project fields that are automatically released to New Security Group
	 */
	//@Test(description = "RPMXCON-55930", enabled = true, groups = { "regression" })
	public void verifyMetaDataFieldsInDocView() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55930");
		baseClass.stepInfo(
				"Verify list of project fields that are automatically released to New Security Group");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		data = new DataSets(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		sessionSearch = new SessionSearch(driver);
		ingestionPage = new IngestionPage_Indium(driver);
		userManagementPage=new UserManagement(driver);
		String projectName = "CodingFormCloneProject" + Utility.dynamicNameAppender();
		String securityGroup= "demoSg" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);

		// cloning the project
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "");
//		baseClass.waitTime(200);
		data.getNotificationMessage(0, projectName);
		baseClass.stepInfo("Cloning a project");

		// giveing access to pa and rmu user for new project
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		baseClass.waitTime(3);
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "",
				false, false);
		baseClass.stepInfo("Access to PA user");
		users.ProjectSelectionForUser(projectName, Input.rmu1FullName, "Review Manager", "Default Security Group",
				false, true);
		baseClass.stepInfo("Access to RMU user");
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManagementPage.getBulkUserAccessTab());
		userManagementPage.getBulkUserAccessTab().waitAndClick(5);
		baseClass.waitForElement(userManagementPage.getSelectRollId());
		userManagementPage.getSelectRollId().selectFromDropdown().selectByVisibleText("Project Administrator");
		userManagementPage.defaultSelectionCheckboxForAllRole(true, true, true, false, true, true, true, true, true,
				true, true, true, true, true, true);
		driver.scrollingToElementofAPage(userManagementPage.getEnableRadioBtn());
		userManagementPage.getEnableRadioBtn().waitAndClick(5);
		baseClass.waitForElement(userManagementPage.getSelectingProject());
		userManagementPage.getSelectingProject().waitAndClick(5);
		baseClass.waitForElement(userManagementPage.getSelectDropProject(projectName));
		userManagementPage.getSelectDropProject(projectName).waitAndClick(10);
		driver.scrollingToElementofAPage(userManagementPage.getSelectBulkUser(Input.pa1FullName));
		baseClass.waitForElement(userManagementPage.getSelectBulkUser(Input.pa1FullName));
		userManagementPage.getSelectBulkUser(Input.pa1FullName).waitAndClick(5);
		baseClass.waitForElement(userManagementPage.getBulkUserSaveBtn());
		userManagementPage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		loginPage.logout();

		// login as pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password,projectName);
		baseClass.selectproject(projectName);
		driver.waitForPageToBeReady();
		
		// Ingestioning the documents
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Doing ingestion for new project creation");
		ingestionPage.IngestionOnlyForDatFile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		String ingestionFullName = data.isDataSetisAvailable(Input.UniCodeFilesFolder);
		System.out.println(ingestionFullName);
		
		// Create security group
		securityGroupPage.navigateToSecurityGropusPageURL();
		securityGroupPage.AddSecurityGroup(securityGroup);

		// access to security group to RMU
		userManagementPage.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		sessionSearch.basicContentSearch(ingestionFullName);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String docId=docViewPage.getVerifyPrincipalDocument().getText();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.waitForElement(sessionSearch.getBulkActionButton());
		sessionSearch.getBulkActionButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getBulkReleaseAction());
		sessionSearch.getBulkReleaseAction().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getBulkRelDefaultSecurityGroup_CheckBox(securityGroup));
		sessionSearch.getBulkRelDefaultSecurityGroup_CheckBox(securityGroup).waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getBulkRelease_ButtonRelease());
		sessionSearch.getBulkRelease_ButtonRelease().waitAndClick(20);
		if (sessionSearch.getFinalizeButton().isDisplayed()) {
			baseClass.waitForElement(sessionSearch.getFinalizeButton());
			sessionSearch.getFinalizeButton().waitAndClick(10);

		}
		
		//logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, projectName);
		baseClass.selectsecuritygroup(securityGroup);
		sessionSearch.basicContentSearch(docId);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		
		// validating the metadatafield
		List<String> expected;
		expected=Arrays.asList("AllCustodians","AllProductionBatesRanges","AppointmentEndDate","AppointmentEndDateOnly","AppointmentStartDate","AppointmentStartDateOnly","AttachCount","AttachDocIDs","Container","CreateDate",
				"CustodianName","DataSource","DateAccessedDateOnly","DateCreatedDateOnly","DateEditedDateOnly","DateModifiedDateOnly","DatePrintedDateOnly","DateReceivedDateOnly","DateSavedDateOnly",
				"DocDate","DocDateDateOnly","DocFileExtension","DocFileExtensionCorrected","DocFileName","DocFileSize","DocFileType","DocID","DocLanguages","DocPages","DocPrimaryLanguage",
				"DupeCount","DupeCustodianCount","DupeCustodians","EmailAllDomainCount","EmailAllDomains","EmailAuthorAddress","EmailAuthorDomain","EmailAuthorName","EmailAuthorNameAndAddress","EmailBCCAddresses","EmailBCCNames",
				"EmailBCCNamesAndAddresses","EmailCCAddresses","EmailCCNames","EmailCCNamesAndAddresses",
				"EmailConversationIndex","EmailDateSentDateOnly","EmailDateSentTimeOnly","EmailDuplicateDocIDs","EmailFamilyConversationIndex","EmailInclusiveReason","EmailInclusiveScore",  
				"EmailMessageID","EmailMessageType","EmailReceivedDate","EmailRecipientAddresses","EmailRecipientCount","EmailRecipientDomainCount","EmailRecipientDomains",  
				"EmailRecipientNames","EmailSentDate","EmailSubject","EmailThreadID","EmailToAddresses","EmailToNames","EmailToNamesAndAddresses","ExcelProtectedSheets","ExcelProtectedWorkbook","ExceptionDescription",
				"ExceptionResolution","ExceptionStatus","FamilyID","FamilyMemberCount","FamilyRelationship","FileDescription","FullPath","HiddenProperties","LastAccessDate","LastEditDate","LastModifiedDate",
				"LastPrintedDate","LastSaveDate","MasterDate","MasterDateDateOnly","MD5HASH","NearDupeCount","NearDupePrincipalDocID","ParentDocID","PhysicalMedia","RecordType",
				"SourceAttachDocIDs","SourceDocID","SourceParentDocID","VideoPlayerReady");
		baseClass.waitForElementCollection(docViewPage.getMetaDataText(1));
		List<WebElement> data=docViewPage.getMetaDataText(1).FindWebElements();
		Set<String> duplicate = new LinkedHashSet<String>();
		for (WebElement allElement : data) {
			String metaDataText=allElement.getText();
			System.out.println(metaDataText);
			boolean flag=expected.contains(metaDataText);
			if (flag) {
				duplicate.add(metaDataText);
				System.out.println(duplicate);
			}
		}
		if (duplicate.toString().contains(expected.toString())) {
			baseClass.passedStep("Metadata fields are dispalyed correctly, when user created new project.with new security group with ingestion");
			System.out.println(duplicate.toString());
			System.out.println(expected.toString());
		}
		else {
			baseClass.failedStep("Metadata fields are not displayed as expected");
		}
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Baskar Date:13/07/2022
	 * @Description : Verify list of project fields that are automatically released to Default Security Group
	 */
	//@Test(description = "RPMXCON-55929", enabled = true, groups = { "regression" })
	public void verifyMetaDataFieldsInDocViewDSG() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55929");
		baseClass.stepInfo(
				"Verify list of project fields that are automatically released to Default Security Group");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		data = new DataSets(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		sessionSearch = new SessionSearch(driver);
		ingestionPage = new IngestionPage_Indium(driver);
		userManagementPage=new UserManagement(driver);
		String projectName = "CodingFormCloneProject" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);

		// cloning the project
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "");
		data.getNotificationMessage(0, projectName);
		baseClass.stepInfo("Cloning a project");

		// giveing access to pa and rmu user for new project
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "",
				false, false);
		baseClass.stepInfo("Access to PA user");
		users.ProjectSelectionForUser(projectName, Input.rmu1FullName, "Review Manager", "Default Security Group",
				false, true);
		baseClass.stepInfo("Access to RMU user");
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManagementPage.getBulkUserAccessTab());
		userManagementPage.getBulkUserAccessTab().waitAndClick(5);
		userManagementPage.getSelectRollId().waitAndClick(5);
		userManagementPage.getSelectRollId().selectFromDropdown().selectByVisibleText("Project Administrator");
		userManagementPage.defaultSelectionCheckboxForAllRole(true, true, true, false, true, true, true, true, true,
				true, true, true, true, true, true);
		driver.scrollingToElementofAPage(userManagementPage.getEnableRadioBtn());
		userManagementPage.getEnableRadioBtn().waitAndClick(5);
		userManagementPage.getSelectingProject().waitAndClick(5);
		userManagementPage.getSelectDropProject(projectName).waitAndClick(10);
		driver.scrollingToElementofAPage(userManagementPage.getSelectBulkUser(Input.pa1FullName));
		userManagementPage.getSelectBulkUser(Input.pa1FullName).waitAndClick(5);
		userManagementPage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		loginPage.logout();

		// login as pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		
		// Ingestioning the documents
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Doing ingestion for new project creation");
		ingestionPage.IngestionOnlyForDatFile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		String ingestionFullName = data.isDataSetisAvailable(Input.UniCodeFilesFolder);
		System.out.println(ingestionFullName);
		
		// access to security group to RMU
		sessionSearch.basicContentSearch(ingestionFullName);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String docId=docViewPage.getVerifyPrincipalDocument().getText();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		sessionSearch.getBulkActionButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		sessionSearch.getBulkReleaseAction().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getBulkRelDefaultSecurityGroup_CheckBox("Default Security Group"));
		sessionSearch.getBulkRelDefaultSecurityGroup_CheckBox("Default Security Group").waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getBulkRelease_ButtonRelease());
		sessionSearch.getBulkRelease_ButtonRelease().waitAndClick(20);
		if (sessionSearch.getFinalizeButton().isDisplayed()) {
			baseClass.waitForElement(sessionSearch.getFinalizeButton());
			sessionSearch.getFinalizeButton().waitAndClick(10);

		}
		//logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, projectName);
		baseClass.selectsecuritygroup("Default Security Group");
		sessionSearch.basicContentSearch(docId);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		
		// validating the metadatafield
		List<String> expected;
		expected=Arrays.asList("AllCustodians","AllProductionBatesRanges","AppointmentEndDate","AppointmentEndDateOnly","AppointmentStartDate","AppointmentStartDateOnly","AttachCount","AttachDocIDs","Container","CreateDate",
				"CustodianName","DataSource","DateAccessedDateOnly","DateCreatedDateOnly","DateEditedDateOnly","DateModifiedDateOnly","DatePrintedDateOnly","DateReceivedDateOnly","DateSavedDateOnly",
				"DocDate","DocDateDateOnly","DocFileExtension","DocFileExtensionCorrected","DocFileName","DocFileSize","DocFileType","DocID","DocLanguages","DocPages","DocPrimaryLanguage",
				"DupeCount","DupeCustodianCount","DupeCustodians","EmailAllDomainCount","EmailAllDomains","EmailAuthorAddress","EmailAuthorDomain","EmailAuthorName","EmailAuthorNameAndAddress","EmailBCCAddresses","EmailBCCNames",
				"EmailBCCNamesAndAddresses","EmailCCAddresses","EmailCCNames","EmailCCNamesAndAddresses",
				"EmailConversationIndex","EmailDateSentDateOnly","EmailDateSentTimeOnly","EmailDuplicateDocIDs","EmailFamilyConversationIndex","EmailInclusiveReason","EmailInclusiveScore",  
				"EmailMessageID","EmailMessageType","EmailReceivedDate","EmailRecipientAddresses","EmailRecipientCount","EmailRecipientDomainCount","EmailRecipientDomains",  
				"EmailRecipientNames","EmailSentDate","EmailSubject","EmailThreadID","EmailToAddresses","EmailToNames","EmailToNamesAndAddresses","ExcelProtectedSheets","ExcelProtectedWorkbook","ExceptionDescription",
				"ExceptionResolution","ExceptionStatus","FamilyID","FamilyMemberCount","FamilyRelationship","FileDescription","FullPath","HiddenProperties","LastAccessDate","LastEditDate","LastModifiedDate",
				"LastPrintedDate","LastSaveDate","MasterDate","MasterDateDateOnly","MD5HASH","NearDupeCount","NearDupePrincipalDocID","ParentDocID","PhysicalMedia","RecordType",
				"SourceAttachDocIDs","SourceDocID","SourceParentDocID","VideoPlayerReady");
		baseClass.waitForElementCollection(docViewPage.getMetaDataText(1));
		List<WebElement> data=docViewPage.getMetaDataText(1).FindWebElements();
		Set<String> duplicate = new LinkedHashSet<String>();
		for (WebElement allElement : data) {
			String metaDataText=allElement.getText();
			boolean flag=expected.contains(metaDataText);
			if (flag) {
				duplicate.add(metaDataText);
			}
		}
		if (duplicate.toString().contains(expected.toString())) {
			baseClass.passedStep("Metadata fields are dispalyed correctly, when user created new project.with Default security group with ingestion");
			System.out.println(duplicate.toString());
			System.out.println(expected.toString());
		}
		else {
			baseClass.failedStep("Metadata fields are not displayed as expected");
		}
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
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
	}
}

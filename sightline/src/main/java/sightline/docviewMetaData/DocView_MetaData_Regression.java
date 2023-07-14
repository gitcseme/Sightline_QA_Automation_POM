package sightline.docviewMetaData;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProjectFieldsPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_MetaData_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws  IOException,InterruptedException, ParseException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
		
		driver = new Driver();
		loginPage = new LoginPage(driver);
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :    51313 - Verify 'Export' option should be displayed on history pop up from doc view
	 * 					50853 - To verify that user can view 'History' tab by default.
	 * 					51312 - Verify pagination from the document history pop up
	 * 					51311 - Verify 'Release to Security Group' should be displayed as per logged in users assinged security group	
	 *					51130 - To verify that user can select documents and view the added folder.
	 * Description : To Verify History and folder Popups From DocView
	 */
	@Test(description ="RPMXCON-51313,RPMXCON-50853,RPMXCON-51312,RPMXCON-51311,RPMXCON-51130",alwaysRun = true,groups={"regression"} )
	public void verifyHistoryPopupFromDocView() throws Exception {		
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51313, RPMXCON-50853, RPMXCON-51312, RPMXCON-51311, RPMXCON-51130 DocView/MetaData Sprint 01");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify History Popup From Doc View ####");
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Get tab on doc view by using text on tab :: Ex - History");
		docViewMetaDataPage.getTabOnDocViewByText(Input.historyTab);
		
		baseClass.stepInfo("Verifying history tab is displayed in doc view page.");
		docViewMetaDataPage.verifyHistoryTabIsDisplayedByDefault();
		
		baseClass.stepInfo("Verifying export tab is displayed in browse history pop up in  doc view page.");
		docViewMetaDataPage.verifyExportOptionIsDisplayed();
		
		baseClass.stepInfo("Verifying history pop up table is paginated in  doc view page.");
		docViewMetaDataPage.verifyHistoryPopIsPaginated();
		
		baseClass.stepInfo("Verifying release to security group is displayed in history table");
		docViewMetaDataPage.verifyReleaseToSecurityGroupisDisplayed();
	
		baseClass.stepInfo("Select paginated Document from Doc list and perform pagination");
		docViewMetaDataPage.selectPaginatedDocAndPerformPagination();
		
		baseClass.stepInfo("Create new folder and add selected document to it");
		docViewMetaDataPage.createNewFolderAndAddSelectedDocument(Input.randomText+String.valueOf(Utility.dynamicNameAppender()));
		loginPage.logout();
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 50856 - To verify that user can view the 'Folder' action on Doc View, if preferences is set as 'Enabled' from the assignment.
	 * 				 50855 - To verify that user can not view the History' tab on Doc View, if preferences set as 'Disabled' from the assignment.
	 * 				 50854 - To verify that user can view the 'History' on Doc View, if preferences is set as 'Enabled' from the assignent.
	 * 				 50857 - To verify that user can not view the 'Folder' action on Doc View, if preferences is set as 'Disabled'  from the assignent.	
	 * Description : To Verify Assignment operations reflect on Doc View
	 */
	@Test(description ="RPMXCON-50856,RPMXCON-50855,RPMXCON-50854,RPMXCON-50857",alwaysRun = true,groups={"regression"} )
	public void verifyAssignmentsForDocView() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50856, RPMXCON-50855, RPMXCON-50854, RPMXCON-50857- DocView/MetaData Sprint 01");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		utility= new Utility(driver);
		baseClass.stepInfo("#### Verify Assignments For Doc View ####");
		manageAssignment = new ManageAssignment(driver);
		
		baseClass.stepInfo("Navigate To Assignments Page URL");
		manageAssignment.navigateToAssignmentsPageURL();
		
		int row = manageAssignment.getDocViewRowNum();
		
		baseClass.stepInfo("Enable Meta data panel toogle in assignment and verifying meta data panel enabled in doc view");
		manageAssignment.editAssignment(row);
		manageAssignment.disableMetaTabPanel(false);
		manageAssignment.getBackToManageButton().Click();
		
		baseClass.stepInfo("Disable folder toogle in assignment and verifying folder disabled in doc view");
		manageAssignment.selectRow(row);
		manageAssignment.editAssignment(row);
		manageAssignment.disableDisplayFolder(true);
		manageAssignment.getBackToManageButton().Click();
		manageAssignment.navigateToDocView(row);
		docViewMetaDataPage.verifyFolderTabIsDisabled();
		docViewMetaDataPage.navigateBack();
		
		baseClass.stepInfo("Enable folder toogle in assignment and verifying folder enabled in doc view");
		manageAssignment.selectRow(row);
		manageAssignment.editAssignment(row);
		manageAssignment.disableDisplayFolder(false);
		manageAssignment.getBackToManageButton().Click();
		manageAssignment.navigateToDocView(row);
		docViewMetaDataPage.verifyFolderTabIsEnabled();
		docViewMetaDataPage.navigateBack();
		
		baseClass.stepInfo("Disable history toogle in assignment and verifying history disabled in doc view");
		manageAssignment.selectRow(row);
		manageAssignment.editAssignment(row);
		manageAssignment.disableHistoryFolder(true);
		manageAssignment.getBackToManageButton().Click();
		manageAssignment.navigateToDocView(row);
		docViewMetaDataPage.verifyHistoryTabIsDisabled();
		docViewMetaDataPage.navigateBack();
		
		baseClass.stepInfo("Enable history toogle in assignment and verifying history enabled in doc view");
		manageAssignment.selectRow(row);
		manageAssignment.editAssignment(row);
		manageAssignment.disableHistoryFolder(false);
		manageAssignment.getBackToManageButton().Click();
		manageAssignment.navigateToDocView(row);
		docViewMetaDataPage.verifyHistoryTabIsEnabled();
		loginPage.logout();
	}



	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  52001 - Verify Hidden Property Metadata label warning from Doc view page does not appear when property is blank
	 * Description : To Verify Hidden properties alert box not displayed on history
	 */
	@Test(description ="RPMXCON-52001",alwaysRun = true,groups={"regression"} )
	public void verifyHiddenPropertiesOnHistory() throws Exception {
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52001- DocView/MetaData Sprint 01");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		utility= new Utility(driver);
		baseClass.stepInfo("#### verify Hidden Properties in Doc View ####");
		manageAssignment = new ManageAssignment(driver);
		
		baseClass.stepInfo("Navigate To Assignments Page URL");
		manageAssignment.navigateToAssignmentsPageURL();
		
		int row = manageAssignment.getDocViewRowNum();
		
		baseClass.stepInfo("Enable Meta data panel toogle in assignment and verifying meta data panel enabled in doc view");
		manageAssignment.editAssignment(row);
		manageAssignment.disableMetaTabPanel(false);
		manageAssignment.getBackToManageButton().Click();
		
		baseClass.stepInfo("Navigate to Doc view by selecting assignment by row in manage assignment page");
		manageAssignment.navigateToDocView(row);
		
		baseClass.stepInfo("Verifying hidden properties is blank from Doc view page");
		docViewMetaDataPage.verifyHiddenPropertiesIsBlank();
		loginPage.logout();
		
	}
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  51021 - To verify that remarks activity should be displayed in history record of document.
	 * Description : To verify Remarks Activity Displayed On History Record
	 */
	@Test(description ="RPMXCON-51021",alwaysRun = true,groups={"regression"})
	public void verifyRemarksActivityDisplayedOnHistoryRecord()throws Exception {		
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51021- DocView/MetaData Sprint 01");
		docView = new DocViewPage(driver);
		utility= new Utility(driver);
		String remark = Input.randomText+Utility.dynamicNameAppender();
		String editRemark = Input.randomText+Utility.dynamicNameAppender();
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Vierify that remarks activity should be displayed in history record of document ####");
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Add Remark To Non Audio Document");
		baseClass.waitTime(3);
		docView.addRemarkToNonAudioDocument(5,55, remark);
		
		baseClass.stepInfo("Verify Remark Actions To History .");
		docViewMetaDataPage.verifyRemarkActionsToHistory();
		
		baseClass.stepInfo("Edit Remark");
		docView.editRemark(editRemark);
	
		loginPage.logout();		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  51144 - Verify that updated metadata should be displayed on Metadata panel from doc view.
	 * Description : To verify Updated Meta Data Displayed On Meta Data Table 
	 */
	@Test(description ="RPMXCON-51144",alwaysRun = true,groups={"regression"} )
	public void verifyUpdatedMetaDataDisplayedOnMetaDataTable()throws Exception {
		 
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51144- DocView/MetaData Sprint 01");
		utility = new Utility(driver);
		String random = Input.randomText+String.valueOf(utility.dynamicNameAppender());
		final String random1 = random;
		final String random2 = random;
		baseClass.stepInfo("#### Verify Updated Meta Data Displayed On Meta Data Table ####");
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		ProjectFieldsPage projectFields = new ProjectFieldsPage(driver);
		
		baseClass.stepInfo("Navigate To Project Field sPage");
		projectFields.navigateToProjectFieldsPage();
		
		baseClass.stepInfo("Add a new project field as project administrator");
		projectFields.addProjectField(random1, random2, Input.fldClassification, Input.fldDescription, Input.fieldType,Input.fieldLength);
		
		baseClass.stepInfo("Apply filter on project field table.");
		projectFields.applyFilterByFilterName(random1);
		
		baseClass.stepInfo("Verify applied field name is added in project fields table. ");
		projectFields.verifyAppliedFieldNameIsAddedInProjectFieldsTable(random1);
		SecurityGroupsPage securityGroup = new SecurityGroupsPage(driver);
		
		baseClass.stepInfo("Navigate To Security Gropus Page URL");
		securityGroup.navigateToSecurityGropusPageURL();
		
		baseClass.stepInfo("Select project field in security groups page.");
		securityGroup.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);
		
		baseClass.stepInfo("Add project field to security group");
		securityGroup.addProjectFieldToSecurityGroup(random2);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with RMU");
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		CodingForm codingForm = new CodingForm(driver);
		
		baseClass.stepInfo("Navigate To Coding Form Page");
		codingForm.navigateToCodingFormPage();
		
		baseClass.stepInfo("Search and edit defalut code form");
		codingForm.searchAndEditCodeForm(Input.codeFormName,random2);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Entering any value in project field text field");
		docViewMetaDataPage.enterProjectFieldAndclickOnSave(random2,random1);
		
		baseClass.stepInfo("Verify project field entered in project field text field value is added to meta data");
		docViewMetaDataPage.verifyProjectFieldEnteredInMetadataTable(random2);
		loginPage.logout();
		}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id :  50837 - To verify that RMU can not view the 'MetaData' tab on Doc View, if preferences is set as 'Disabled' from the assignment.
	 * 				  50836 - To verify that RMU can view the 'MetaData' tab on Doc View, if preferences is set as 'Enabled' from the assignment.
	 * Description : To Verify Assignment operations reflect on Doc View
	 */
	@Test(description ="RPMXCON-50837,RPMXCON-50836",alwaysRun = true,groups={"regression"} )
	public void verifyAssignmentsForMetaDataTabOnDocView() throws Exception {
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50837, RPMXCON-50836 DocView/MetaData Sprint 02");
		utility= new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify Assignments For Doc View ####");
		manageAssignment = new ManageAssignment(driver);
		
		baseClass.stepInfo("Navigate To Assignments Page URL");
		manageAssignment.navigateToAssignmentsPageURL();
		
		int row = manageAssignment.getDocViewRowNum();
		
		baseClass.stepInfo("Disable Meta data panel toogle in assignment and verifying meta data panel disabled in doc view");
		manageAssignment.editAssignment(row);
		manageAssignment.disableMetaTabPanel(true);
		manageAssignment.getBackToManageButton().Click();
		manageAssignment.navigateToDocView(row);
		docViewMetaDataPage.verifyMetaDataTabIsDisabled();
		docViewMetaDataPage.navigateBack();
		
		baseClass.stepInfo("Enable Meta data panel toogle in assignment and verifying meta data panel enabled in doc view");
		manageAssignment.selectRow(row);
		manageAssignment.editAssignment(row);
		manageAssignment.disableMetaTabPanel(false);
		manageAssignment.getBackToManageButton().Click();
		manageAssignment.navigateToDocView(row);
		docViewMetaDataPage.verifyMetaDataPanelIsEnabled();
		docViewMetaDataPage.navigateBack();
		loginPage.logout();
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  50807 - To verify user can redirect to Doc View page, can view History, Metadata tab when Sys Admin impersonates.
	 * Description : To Verify History and folder Popups From DocView
	 */
	@Test(description ="RPMXCON-50807",alwaysRun = true,groups={"regression"} )
	public void verifyDocViewPageComponentsAsSystemAdmin() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50807- DocView/MetaData Sprint 02");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify Doc View Page Components As System Admin ####");
		loginPage = new LoginPage(driver);
		
		//Commented by Gopinath - Uncomment if need to execute using System admin credentials.
		/*loginPage.logout();
		
		baseClass.stepInfo("Login with system administrator");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);*/
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Get tab on doc view by using text on tab :: Ex - History");
		docViewMetaDataPage.getTabOnDocViewByText(Input.historyTab);
		
		baseClass.stepInfo("Verifying history tab is displayed in doc view page.");
		docViewMetaDataPage.verifyHistoryTabIsDisplayedByDefault();
		
		baseClass.stepInfo("Verifying export tab is displayed in browse history pop up in  doc view page.");
		docViewMetaDataPage.verifyExportOptionIsDisplayed();
		
		baseClass.stepInfo("Close history pop up on Doc View");
		docViewMetaDataPage.closeHistoryPopup();
		
		baseClass.stepInfo("Verify performed Doc actions added to history table.");
		docViewMetaDataPage.verifyPerformedDocActionsAddedToHistoryTable();
		
		baseClass.stepInfo("Verify history table column headers");
		docViewMetaDataPage.verifyHistoryTableColumnHeaders(Input.actionColumnName, Input.nameColumnName, Input.userColumnName, Input.timeStampColumnName);
		
		baseClass.stepInfo("Verify Metadata tab from Doc View page.");
		docViewMetaDataPage.verifyMetadataTabFromDocViewPage();
		
		baseClass.stepInfo("Verify Metadata tab pop up");
		docViewMetaDataPage.verifyMetaDataPopup();
		loginPage.logout();
		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  50803 - To verify 'Folder' tab from doc view page on document pagination for Reviewer..
	 * Description : Verify Folder tab is displayed by Document Pagination On Doc View.
	 */
	@Test(description ="RPMXCON-50803",alwaysRun = true,groups={"regression"} )
	public void verifyFolderTabDisplayedByDocPaginationOnDocView()throws Exception {
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50803- DocView/MetaData Sprint 02");
		docView = new DocViewPage(driver);
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify Folder tab is displayed by Document Pagination On Doc View. ####");
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Select paginated Document from Doc list and perform pagination");
		docViewMetaDataPage.selectPaginatedDocAndPerformPagination();
		
		baseClass.stepInfo("Create new folder and add selected document to it");
		docViewMetaDataPage.createNewFolderAndAddSelectedDocument(Input.randomText+String.valueOf(utility.dynamicNameAppender()));
		//loginPage.logout();		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  50806 - To verify when Project Admin impersonates to Reviewer role and clicks to view the 'Folders' tab from doc view page
	 * Description : To Verify Project Admin impersonates to Reviewer role and clicks to view the 'Folders' tab from doc view page.
	 */

	@Test(description ="RPMXCON-50806",alwaysRun = true,groups={"regression"} )

	public void verifyProjectAdminImpersonatesToReviewer() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50806- DocView/MetaData Sprint 02");
		String random = Input.randomText+Utility.dynamicNameAppender();
		final String foldername = random;
		
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		
		baseClass.stepInfo("#### Verify Project Admin impersonates to Reviewer role and clicks to view the 'Folders' tab from doc view page ####");
		loginPage = new LoginPage(driver);
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		baseClass.stepInfo("Navigate To Tags And Folder Page ");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		baseClass.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		
		baseClass.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);
		
		baseClass.stepInfo("Impersonate RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Verify added folder is added to folder tree in Doc view page");
		docViewMetaDataPage.verifyAddedFolderDisplayedOnFolderTree(foldername);
		
		baseClass.stepInfo("Impersonate Reviewer to RMU");
		baseClass.impersonateReviewertoRMU();
		loginPage.logout();
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  50791 - To verify pagination from list of metadata pop up.
	 * Description : To verify pagination from list of metadata pop up.
	 */
	@Test(description ="RPMXCON-50791",alwaysRun = true,groups={"regression"} )
	public void verifyPaginationFromListOfMetadataPopup() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50791- DocView/MetaData Sprint 02");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### To verify pagination from list of metadata pop up ####");
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Verifying metadata pop up table is paginated in  doc view page.");
		docViewMetaDataPage.verifyMetaDataPopIsPaginated();
		loginPage.logout();		
	}
	 
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  50792 - To verify when Reviewer clicks the 'History' tab from doc view page.
	 * Description : To verify when Reviewer clicks the 'History' tab from doc view page.
	 */
	@Test(description ="RPMXCON-50792",alwaysRun = true,groups={"regression"} )
	public void verifyHistoryTabOnDocViewPage() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50792- DocView/MetaData Sprint 02");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### To verify when Reviewer clicks the 'History' tab from doc view page ####");
		
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		Reporter.log("Logged in as User: " + Input.rev1userName);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Get tab on doc view by using text on tab :: Ex - History");
		docViewMetaDataPage.getTabOnDocViewByText(Input.historyTab);
		
		baseClass.stepInfo("Verifying history tab is displayed in doc view page.");
		docViewMetaDataPage.verifyHistoryTabIsDisplayedByDefault();
		
		baseClass.stepInfo("Verifying all browser history button is displayed in doc view page.");
		docViewMetaDataPage.verifyBrowseAllHistoryButtonIsDisplayed();
		
		baseClass.stepInfo("Verify performed Doc actions added to history table.");
		docViewMetaDataPage.verifyPerformedDocActionsAddedToHistoryTable();
		
		baseClass.stepInfo("Verify history table column headers");
		docViewMetaDataPage.verifyHistoryTableColumnHeaders(Input.actionColumnName, Input.nameColumnName, Input.userColumnName, Input.timeStampColumnName);
		loginPage.logout();		
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id :  50788 - To verify Metadata tab from Doc view page for Reviewer user.
	 * Description : To Verify meta data tab as reviewer.
	 */
	@Test(description ="RPMXCON-50788",alwaysRun = true,groups={"regression"} )
	public void verifyMetadataTabFromDocViewPage() throws Exception {
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50788 DocView/MetaData Sprint 03");
		utility= new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify Metadata tab from Doc view page for Reviewer user ####");
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		Reporter.log("Logged in as User: " + Input.rev1userName);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Verify Metadata tab should be displayed with default metadata fields");
		docViewMetaDataPage.verifyMetaDataColumns();
	
		baseClass.stepInfo("Verify Metadata tab from Doc View page.");
		docViewMetaDataPage.verifyMetadataTabFromDocViewPage();
		
		baseClass.stepInfo("Close meta data popup");
		docViewMetaDataPage.closeMetaDataPopup();
		
		baseClass.stepInfo("Edit coding form on Doc view");
		docViewMetaDataPage.editCodingFormOnDocView(Input.comment);
		
		baseClass.stepInfo("Open Doc view meta data pop up on doc view page.");
		docViewMetaDataPage.openDocViewMetaDataPopup();
		
		baseClass.stepInfo("Verify Metadata tab pop up");
		docViewMetaDataPage.verifyMetaDataPopup();
		
		baseClass.stepInfo("Close meta data popup");
		docViewMetaDataPage.closeMetaDataPopup();
		
		baseClass.stepInfo("Save edited coding form");
		docViewMetaDataPage.saveEditedCodingForm();
		
		baseClass.stepInfo("Edit coding form on Doc view");
		docViewMetaDataPage.editCodingFormOnDocView(Input.comment);
		
		baseClass.stepInfo("Open Doc view meta data pop up on doc view page.");
		docViewMetaDataPage.openDocViewMetaDataPopup();
		
		baseClass.stepInfo("Verify Metadata tab pop up");
		docViewMetaDataPage.verifyMetaDataPopup();
		
		baseClass.stepInfo("Close meta data popup");
		docViewMetaDataPage.closeMetaDataPopup();
		
		baseClass.stepInfo("Save complete edited coding form and verify user navigated to present document to next document");
		docViewMetaDataPage.saveCompleteEditedCodingFormAndVerifyNavigatedToNextDoc();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Edit coding form on Doc view");
		docViewMetaDataPage.editCodingFormOnDocView(Input.comment);
		
		baseClass.stepInfo("Open Doc view meta data pop up on doc view page.");
		docViewMetaDataPage.openDocViewMetaDataPopup();
		
		baseClass.stepInfo("Verify Metadata tab pop up");
		docViewMetaDataPage.verifyMetaDataPopup();
		
		baseClass.stepInfo("Close meta data popup");
		docViewMetaDataPage.closeMetaDataPopup();
		
		baseClass.stepInfo("Save complete edited coding form and verify user navigated to present document to next document");
		docViewMetaDataPage.saveCompleteEditedCodingFormAndVerifyNavigatedToNextDoc();
		
		loginPage.logout();		
	}
	
	
	/**
	 * @author Gopinath
	 * @throws InterruptedException 
	 * @Testcase_Id : RPMXCON-50805 : To verify when Project Admin impersonates to RMU role and clicks to view the 'Folders' tab from doc view page.
	 * @description : Verify when Project Admin impersonates to RMU role and clicks to view the 'Folders' tab from doc view page.
	 */

	@Test(description ="RPMXCON-50805",groups = { "regression" })
	public void verifyFoldertabDisplayedWithExpandCollapseHirearcy() throws InterruptedException {
		baseClass=new BaseClass(driver);
		loginPage = new LoginPage(driver);
		try {
			baseClass.stepInfo("RPMXCON-50805 Production-sprint:08");
			baseClass.stepInfo("#### Verify when Project Admin impersonates to RMU role and clicks to view the 'Folders' tab from doc view page. ####");
			String assignmentName = Input.randomText + Utility.dynamicNameAppender();
			
			
			loginPage.logout();
			
			baseClass.stepInfo("Login with project administrator");
			loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
			baseClass.stepInfo("Logged in as User: " + Input.pa2FullName);
			
			baseClass.stepInfo("Impersonate PA to RMU");
			baseClass.impersonatePAtoRMU_SelectedSG(Input.securityGroup);
			
			AssignmentsPage assgnPage = new AssignmentsPage(driver);
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Create Assignment");
			assgnPage.createAssignment(assignmentName, Input.codeFormName);
			
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Bulk Assign Existing");
			search.bulkAssignExisting(assignmentName);
			
			baseClass.stepInfo("Created a assignment " + assignmentName);
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Select assignment to view in doc view");
			assgnPage.selectAssignmentToViewinDocview(assignmentName);
			
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Verify folder tab is displayed with expand/collapse hirercy.");
			docViewMetaDataPage.verifyFoldertabDisplayedWithExpandCollapse();
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Delete Assgnmnt Using Pagination");
			assgnPage.deleteAssignment(assignmentName);
			
			baseClass.stepInfo("Impersonate PA to RMU");
			baseClass.impersonateDAtoPA();
		}catch(Exception e) {
			e.printStackTrace();
			loginPage.logout();
	
			baseClass.stepInfo("Login with project administrator");
			loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
			
			baseClass.impersonateDAtoPA();
		}
		loginPage.logout();
	}
	
	
	
	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: NA
	 * @TestCase id : 50802 - To verify 'Folder' tab from doc view page on document navigation.
	 * @Description : Verify 'Folder' tab from doc view page on document navigation.
	 */
	@Test(description ="RPMXCON-50802",alwaysRun = true,groups={"regression"} )
	public void verifyCreatedFolderIsAddedToFolderTreeHirercy() throws Exception {		
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50802 DocView/MetaData Sprint 08");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify 'Folder' tab from doc view page on document navigation. ####");
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
	
		baseClass.stepInfo("Select documents from mini doc list in doc view.");
		docViewMetaDataPage.selectDocumentsFromMiniDocListFromDocView(3);
		
		baseClass.stepInfo("Create new folder and add selected document to it");
		docViewMetaDataPage.createNewFolderAndAddSelectedDocument(Input.randomText+Utility.dynamicNameAppender());
		loginPage.logout();
	}
	
	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: NA
	 * @TestCase id : 50801 - To verify that InActive fields is not displayed on Doc View-MetaData tab.
	 * @Description : Verify that InActive fields is not displayed on Doc View-MetaData tab.
	 */
	@Test(description ="RPMXCON-50801",alwaysRun = true,groups={"regression"} )
	public void verifyInactiveFieldsNotDisplayedonDocViewMetadata() throws Exception {		
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50801 DocView/MetaData Sprint 08");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that InActive fields is not displayed on Doc View-MetaData tab. ####");
		String random = Input.randomText+Utility.dynamicNameAppender();
		final String random1 = random;
		final String random2 = random;
		
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		ProjectFieldsPage projectFields = new ProjectFieldsPage(driver);
		
		Reporter.log("Navigate To Project Fields Page");
		projectFields.navigateToProjectFieldsPage();
		
		baseClass.stepInfo("Add a new project field as project administrator");
		projectFields.addProjectField(random1, random2, Input.fldClassification, Input.fldDescription, Input.fieldType,Input.fieldLength);

		baseClass.stepInfo("Apply Filter By Filter Name");
		projectFields.applyFilterByFilterName(random1);
		
		baseClass.stepInfo("In Active Project Field");
		projectFields.inActiveProjectField(random1);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
	
		baseClass.stepInfo("Verify Inactive Project Field Not Displayed In MetadataTab.");
		docViewMetaDataPage.verifyInactiveProjectFieldNotDisplayedInMetadataTab(random1);
		loginPage.logout();
	}
	
	
	
	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @Testcase_Id : RPMXCON-50800 : To verify when Reviewer clicks the 'Folders' tab from doc view page when BulkFolder action is performed by PA/RMU from search result.
	 * @Description : Verify when Reviewer clicks the 'Folders' tab from doc view page when BulkFolder action is performed by PA/RMU from search result
	 */
	@Test(description ="RPMXCON-50800",groups = { "regression" })
	public void verifyReviwerGetFolderByBulkFolderRMU() throws Exception {
		baseClass=new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("Test case Id: RPMXCON-50800 -Production Sprint 08");
		baseClass.stepInfo("#### Verify when Reviewer clicks the 'Folders' tab from doc view page when BulkFolder action is performed by PA/RMU from search result. ####");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		baseClass.stepInfo("Navigate To Tags And Folder Page ");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		baseClass.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		
		baseClass.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);
		
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login as Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		Reporter.log("Logged in as User: " + Input.rev1userName);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Verify Added Folder Displayed On Folder Tree");
		docViewMetaDataPage.verifyAddedFolderDisplayedOnFolderTree(foldername);
		loginPage.logout();
	}
	
	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @Testcase_Id : RPMXCON-50799 : To verify when RMU/Reviewer clicks the 'Folders' tab from doc view page when BulkFolder action is not performed from search result.
	 * @Description : To verify when RMU/Reviewer clicks the 'Folders' tab from doc view page when BulkFolder action is not performed from search result.
	 */
	@Test(description ="RPMXCON-50799",groups = { "regression" })
	public void verifyFolderTabWithoutPerformingBulkFolder() throws Exception {
		baseClass=new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("Test case Id: RPMXCON-50799 -Production Sprint 08");
		baseClass.stepInfo("#### To verify when RMU/Reviewer clicks the 'Folders' tab from doc view page when BulkFolder action is not performed from search result. ####");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.searchBulletDocId);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Verify folder tab does not have any folders except default all folders.");
		docViewMetaDataPage.verifyFolderTabNotContainsFolders();
		loginPage.logout();
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  50796 - To verify when Reviewer clicks the 'Browse All History' button from History tab from doc view page.
	 * Description : To verify when Reviewer clicks the 'Browse All History' button from History tab from doc view page.
	 */
	@Test(description ="RPMXCON-50796",alwaysRun = true,groups={"regression"})
	public void verifyBrowseAllHistoryOnHistoryTabDocView() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50796- DocView/MetaData Sprint 08");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### To verify when Reviewer clicks the 'Browse All History' button from History tab from doc view page. ####");
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login as Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		Reporter.log("Logged in as User: " + Input.rev1userName);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Get tab on doc view by using text on tab :: Ex - History");
		docViewMetaDataPage.getTabOnDocViewByText(Input.historyTab);
		
		baseClass.stepInfo("Verifying history tab is displayed in doc view page.");
		docViewMetaDataPage.verifyHistoryTabIsDisplayedByDefault();
		
		baseClass.stepInfo("Verifying export tab is displayed in browse history pop up in  doc view page.");
		docViewMetaDataPage.verifyExportOptionIsDisplayed();
		
		baseClass.stepInfo("Close history pop up on Doc View");
		docViewMetaDataPage.closeHistoryPopup();
		
		baseClass.stepInfo("Verify performed Doc actions added to history table.");
		docViewMetaDataPage.verifyPerformedDocActionsAddedToHistoryTable();
		
		baseClass.stepInfo("Verify history table column headers");
		docViewMetaDataPage.verifyHistoryTableColumnHeaders(Input.actionColumnName, Input.nameColumnName, Input.userColumnName, Input.timeStampColumnName);
		loginPage.logout();		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  50795 - To verify when RMU/Reviewer clicks the 'Browse All History' button from History tab from doc view page.
	 * Description : Verify when RMU/Reviewer clicks the 'Browse All History' button from History tab from doc view page.
	 */
	@Test(description ="RPMXCON-50795",alwaysRun = true,groups={"regression"} )
	public void verifyBrowseAllHistoryOnHistoryTabDocViewFromAssignments() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50795- DocView/MetaData Sprint 08");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### To verify when RMU/Reviewer clicks the 'Browse All History' button from History tab from doc view page ####");
		
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		manageAssignment = new ManageAssignment(driver);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);
		
		SessionSearch search = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		search.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		
		baseClass.stepInfo("Bulk Assign Existing");
		search.bulkAssignExisting(assignmentName);
		
		baseClass.stepInfo("Created a assignment " + assignmentName);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Edit Assignment");
		assgnPage.editAssignment(assignmentName);
		
		baseClass.stepInfo("Enable history folder");
		manageAssignment.disableHistoryFolder(false);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Select assignment to view in doc view");
		assgnPage.selectAssignmentToViewinDocview(assignmentName);
		
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();
		
		baseClass.stepInfo("Get tab on doc view by using text on tab :: Ex - History");
		docViewMetaDataPage.getTabOnDocViewByText(Input.historyTab);
		
		baseClass.stepInfo("Verifying history tab is displayed in doc view page.");
		docViewMetaDataPage.verifyHistoryTabIsDisplayedByDefault();
		
		baseClass.stepInfo("Verifying export tab is displayed in browse history pop up in  doc view page.");
		docViewMetaDataPage.verifyExportOptionIsDisplayed();
		
		baseClass.stepInfo("Close history pop up on Doc View");
		docViewMetaDataPage.closeHistoryPopup();
		
		baseClass.stepInfo("Verify performed Doc actions added to history table.");
		docViewMetaDataPage.verifyPerformedDocActionsAddedToHistoryTable();
		
		baseClass.stepInfo("Verify history table column headers");
		docViewMetaDataPage.verifyHistoryTableColumnHeaders(Input.actionColumnName, Input.nameColumnName, Input.userColumnName, Input.timeStampColumnName);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssignment(assignmentName);
		loginPage.logout();		
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  50793 - To verify when user clicks the 'Folders' tab from doc view page.
	 * Description : Verify when user clicks the 'Folders' tab from doc view page
	 */
	@Test(description ="RPMXCON-50793",alwaysRun = true,groups={"regression"} )
	public void verifyUserClicksFoldersTabFromDocView() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50793- DocView/MetaData Sprint 08");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify when user clicks the 'Folders' tab from doc view page ####");
		
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		manageAssignment = new ManageAssignment(driver);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);
		
		SessionSearch search = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		search.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		
		baseClass.stepInfo("Bulk Assign Existing");
		search.bulkAssignExisting(assignmentName);
		
		baseClass.stepInfo("Created a assignment " + assignmentName);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Select assignment to view in doc view");
		assgnPage.selectAssignmentToViewinDocview(assignmentName);
		
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();
		
		baseClass.stepInfo("Verify folder tab contains folders and expand collapse button is displayed.");
		docViewMetaDataPage.verifyFolderTabContainsFolders();
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssignment(assignmentName);
		
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		search.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		search.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Verify folder tab contains folders and expand collapse button is displayed.");
		docViewMetaDataPage.verifyFolderTabContainsFolders();
		loginPage.logout();		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  48689 - To verify when user clicks the 'History' tab from doc view page.
	 * Description : Verify when user clicks the 'History' tab from doc view page.
	 */
	@Test(description ="RPMXCON-48689",alwaysRun = true,groups={"regression"})
	public void verifyHistoryTabDocViewFromAssignments() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48689- DocView/MetaData Sprint 08");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify when user clicks the 'History' tab from doc view page. ####");
		
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		manageAssignment = new ManageAssignment(driver);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);
		
		SessionSearch search = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		search.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		
		baseClass.stepInfo("Bulk Assign Existing");
		search.bulkAssignExisting(assignmentName);
		
		baseClass.stepInfo("Created a assignment " + assignmentName);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Edit Assignment");
		assgnPage.editAssignment(assignmentName);
		
		baseClass.stepInfo("Enable history folder");
		manageAssignment.disableHistoryFolder(false);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Select assignment to view in doc view");
		assgnPage.selectAssignmentToViewinDocview(assignmentName);
		
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();
		
		baseClass.stepInfo("Get tab on doc view by using text on tab :: Ex - History");
		docViewMetaDataPage.getTabOnDocViewByText(Input.historyTab);
		
		baseClass.stepInfo("Verifying history tab is displayed in doc view page.");
		docViewMetaDataPage.verifyHistoryTabIsDisplayedByDefault();
		
		baseClass.stepInfo("Verifying export tab is displayed in browse history pop up in  doc view page.");
		docViewMetaDataPage.verifyExportOptionIsDisplayed();
		
		baseClass.stepInfo("Close history pop up on Doc View");
		docViewMetaDataPage.closeHistoryPopup();
		
		baseClass.stepInfo("Verify performed Doc actions added to history table.");
		docViewMetaDataPage.verifyPerformedDocActionsAddedToHistoryTable();
		
		baseClass.stepInfo("Verify history table column headers");
		docViewMetaDataPage.verifyHistoryTableColumnHeaders(Input.actionColumnName, Input.nameColumnName, Input.userColumnName, Input.timeStampColumnName);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssignment(assignmentName);
		loginPage.logout();		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  48688 - To verify Metadata tab from doc view page for user.
	 * Description : Verify Metadata tab from doc view page for user.
	 */
	@Test(description ="RPMXCON-48688",alwaysRun = true,groups={"regression"} )
	public void verifyMetadataTabDocViewFromAssignments() throws Exception {	
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48688- DocView/MetaData Sprint 08");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### To verify Metadata tab from doc view page for user ####");
		
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		manageAssignment = new ManageAssignment(driver);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);
		
		SessionSearch search = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();
		
		baseClass.stepInfo("Basic meta data search");
		search.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
		
		baseClass.stepInfo("Bulk Assign Existing");
		search.bulkAssignExisting(assignmentName);
		
		baseClass.stepInfo("Created a assignment " + assignmentName);
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Select assignment to view in doc view");
		assgnPage.selectAssignmentToViewinDocview(assignmentName);
		
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();
		
		baseClass.stepInfo("Verify Metadata tab from Doc View page.");
		docViewMetaDataPage.verifyMetadataTabFromDocViewPage();
		
		baseClass.stepInfo("Verify meta data columns are displayed");
		docViewMetaDataPage.verifyMetaDataColumns();
		
		baseClass.stepInfo("Verify Metadata tab pop up");
		docViewMetaDataPage.verifyMetaDataPopup();
		
		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssignment(assignmentName);
		loginPage.logout();
		
	}
	
	/**
	 * @author Gopinath
	 * @testCase Id:50790 To verify sorting from list of metadata pop up
	 * @description:  To verify sorting from list of metadata pop up
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50790",alwaysRun = true,groups={"regression"})
	public void verifyMetadataPopUpColumns() throws InterruptedException {
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50790 sprint-11");
		baseClass.stepInfo("###To verify sorting from list of metadata pop up###");
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);
		SessionSearch session = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(Input.searchString1);
		
		baseClass.stepInfo("View serached docs in Docview");
		session.ViewInDocView();
		
		baseClass.stepInfo("opening metadata popup");
		docViewMetaData.openDocViewMetaDataPopup();
		
		baseClass.stepInfo("verify metadapop list and columns");
		docViewMetaData.verifyMetadataPopUpColumns();
		
	}
	

	/**
	 * @author Gopinath
	 * @throws InterruptedException 
	 * @Testcase_Id : RPMXCON-48802 : Verify that DocView Metadata Panel data should not go missing in an assignment.
	 * @description : Verify that DocView Metadata Panel data should not go missing in an assignment.
	 */

	@Test(description ="RPMXCON-48802",groups = { "regression" })
	public void verifyMeataDataPanelDisplayedByAssignmentsView() throws InterruptedException {
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-48802 DocView/Meatadata-sprint:08");
		baseClass.stepInfo("#### Verify that DocView Metadata Panel data should not go missing in an assignment.. ####");
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		AssignmentsPage assgnPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Bulk Assign Existing");
		search.bulkAssignExisting(assignmentName);

		baseClass.stepInfo("Created a assignment " + assignmentName);

		baseClass.selectproject();

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Edit Assignment Using Pagination Concept");
		assgnPage.editAssignmentUsingPaginationConcept(assignmentName);

		baseClass.stepInfo("Add Reviewer And Distribute Docs");
		assgnPage.addReviewerAndDistributeDocs();

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Select assignment to view in doc view");
		assgnPage.selectAssignmentToViewinDocview(assignmentName);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Meta Data Panel Is Enabled");
		docViewMetaDataPage.verifyMetaDataPanelIsEnabled();

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssignment(assignmentName);
	}

	/**
	 * @author Gopinath
	 * @TestCase Id:60556 Verify whether Metadata fields are sorted in ascending order in DocView Page
	 * @Description:To Verify whether Metadata fields are sorted in ascending order in DocView Page
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60556",enabled = true, groups = { "regression" })
	public void verifyMetaDataListSortingOrder() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-60556");
		baseClass.stepInfo("Verify whether Metadata fields are sorted in ascending order in DocView Page");
		utility = new Utility(driver);
		DocExplorerPage docExpPage=new DocExplorerPage(driver);
		docView = new DocViewPage(driver);
		ReusableDocViewPage reUsableDocPage = new ReusableDocViewPage(driver);
		
		baseClass.stepInfo("Navigating to docExplorer page");
		docExpPage.navigateToDocExplorerPage();
		
		baseClass.stepInfo("Select document in doc explorer page");
		docExpPage.selectAllDocumentsFromCurrentPage();
		
		baseClass.stepInfo("View in DocView");
		docExpPage.docExpViewInDocView();
		
		baseClass.stepInfo("verify meta data fields are sorted in ascending oredr");
		docView.verifyMetaDataFieldNameSorting();
		
		baseClass.stepInfo("open meta data list in child window");
		docView.popOutMetaDataPanel();
		
		baseClass.stepInfo("verify meta data fields are sorted in ascending oredr in child window");
		String patentWindowId = reUsableDocPage.switchTochildWindow();
		docView.verifyMetaDataFieldNameSorting();
		reUsableDocPage.childWindowToParentWindowSwitching(patentWindowId);
		
		loginPage.logout();
	}
	
	/**
	 * @author Gopinath
	 * @TestCase Id:52002 Verify Hidden Property Metadata label warning from Doc view page hidden comments
	 * @Description:To Verify Hidden Property Metadata label warning from Doc view page hidden comments
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-52002",enabled = true, groups = { "regression" })
	public void verifyHiddenPropWarningMsgAndValue() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String docId=Input.warningMsgDocId;
		String project="AutomationAdditionalDataProject";
		baseClass.selectproject(project);
		baseClass.stepInfo("Test case Id: RPMXCON-52002");
		baseClass.stepInfo("###Verify Hidden Property Metadata label warning from Doc view page hidden comments####");
		  
		baseClass.stepInfo("basic content search");
		sessionsearch.basicContentSearch(docId);
		
		baseClass.stepInfo("view in docView");
		sessionsearch.viewInDocView();
		
		baseClass.stepInfo("verify warning message for hidden properties document and metadat value not empty");
		docView.verifyWarningMsgAndHiddenPropValue(docId);
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
}


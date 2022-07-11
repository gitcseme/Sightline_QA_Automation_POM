package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class UserManagement {
	Driver driver;
	BaseClass bc;
	SoftAssert softAssertion;

	public Element getAddUserBtn() {
		return driver.FindElementById("addNewUser");
	}

	public Element getFirstName() {
		return driver.FindElementByXPath("//*[@tabindex='1']");
	}

	public Element getLastName() {
		return driver.FindElementByXPath("//*[@tabindex='2']");
	}

	public Element getSelectRole() {
		return driver.FindElementByXPath("//*[@tabindex='3']");
	}

	public Element getEmail() {
		return driver.FindElementByXPath("//*[@tabindex='4']");
	}

	public Element getSelectLanguage() {
		return driver.FindElementByXPath("//*[@tabindex='6']");
	}

	public Element getSelectDomain() {
		return driver.FindElementByXPath("//*[@tabindex='8']");
	}

	public Element getSelectProject() {
		return driver.FindElementByXPath("//*[@tabindex='7']");
	}

	public Element getSecurityGroup() {
		return driver.FindElementByXPath("(//*[@tabindex='8'])");
	}

	public Element getSave() {
		return driver.FindElementById("SaveUser");
	}

	// set password
	public Element getSetPassword() {
		return driver.FindElementById("NewPassword");
	}

	public Element getConfirmPassword() {
		return driver.FindElementById("ConfirmPassword");
	}

	public Element getSavePassword() {
		return driver.FindElementById("btnSubmit");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	// delete user
	public Element getManageFilterByDate() {
		return driver.FindElementByXPath("//input[@name='startdate']");
	}

	public Element getFilerApplyBtn() {
		return driver.FindElementById("btnAppyFilter");
	}

	public ElementCollection getRowsInTable() {
		return driver.FindElementsByXPath("//*[@id='dtUserList']/tbody/tr");
	}

	public Element getConfirmDelete() {
		return driver.FindElementByXPath("//button[@id='bot2-Msg1']");
	}

	// edit user
	public Element getFunctionalityTab() {
		return driver.FindElementByLinkText("Functionality");
	}

	public Element getManageproject() {
		return driver.FindElementByXPath("//*[@id='s2']//label[contains(.,'Manage Project')]/i");
	}

	public Element getSubmitChanges() {
		return driver.FindElementById("/btnsubmit");
	}

	// Search user
	public Element getUserNameFilter() {
		return driver.FindElementById("txtsearchUser");
	}

	public ElementCollection getNumberofUsers() {
		return driver.FindElementsByXPath("//*[@id='dtUserList']/tbody/tr/td[1]");
	}

	public Element getUserFirstName(int row) {
		return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[" + row + "]/td[1]");
	}

	public Element getUserLastName(int row) {
		return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[" + row + "]/td[2]");
	}

	public Element getUserState(int row) {
		return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[" + row + "]/td[4]");
	}

	public Element getActiveInactiveBtn() {
		return driver.FindElementById("activeUserObject");
	}

	public Element getSelectRoleToFilter() {
		return driver.FindElementById("ddlRoles");
	}

	// Functionality tab
	public Element getManageRole_Functionality_Manage() {
		return driver.FindElementByXPath("//*[@id='s2']//input[@id='UserRights_CanManage']/following-sibling::i");
	}

	public Element getManageRole_Functionality_ManageDomain() {
		return driver.FindElementByXPath("//*[@id='s2']//label[contains(.,'Manage Domain Projects')]/i");
	}

	public Element getManageRole_Functionality_Searching() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Searching')]/i");
	}

	public Element getManageRole_Functionality_ConceptExplr() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Concept Explorer')]/i");
	}

	public Element getManageRole_Functionality_CommExplr() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Communications Explorer')]/i");
	}

	public Element getManageRole_Functionality_Categorize() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Categoriz')]/i");
	}

	public Element getManageRole_Functionality_AnalyticPanel() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Analytics Panels')]/i");
	}

	public Element getManageRole_Functionality_Native() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Download Native')]/i");
	}

	public Element getManageRole_Functionality_AllReport() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'All Reports')]/i");
	}

	public Element getManageRole_Functionality_Production() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Productions')]/i");
	}

	public Element getManageRole_Functionality_Ingestion() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Ingestions')]/i");
	}

	public Element getManageRole_Functionality_Dataset() {
		return driver.FindElementByXPath("//*[@id='s2']//label[contains(.,'Datasets')]/i");
	}

	public Element getManageRole_Functionality_Redactions() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Redactions')]/i");
	}

	public Element getManageRole_Functionality_Highlighting() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Highlighting')]/i");
	}

	public Element getManageRole_Functionality_Remarks() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Reviewer Remarks')]/i");
	}

	public Element getManageRole_Functionality_Datasetcheckbox() {
		return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Concept Explorer')]/input[1]");
	}

	// attorney profile for rmu
	public Element getAttorneyprofilecheckbox() {
		return driver.FindElementByXPath("//*[@id='IsAttorneyProfile']/ancestor::label");
	}

	// Assign User Objects
	public Element getAssignUserButton() {
		return driver.FindElementById("btnAssign");
	}

	public Element getSelectDomainname() {
		return driver.FindElementById("lstDomains");
	}

	public Element getSelectusertoassignindomain() {
		return driver.FindElementById("UnAssignedUsersForDomain");
	}

	public Element getrightBtndomainuser() {
		return driver.FindElementByXPath("//a[@id='btnRightUserMaappingForDomain']");
	}

	public Element getsavedomainuser() {
		return driver.FindElementById("btnSave");
	}

	// added by Narendra - Lock Account
	public Element getEdit(int i) {
		return driver.FindElementByXPath("//table[@id='dtUserList']//tr[" + i + "]/td[7]/a[contains(text(),'Edit')]");
	}

	public Element getIsLock() {
		return driver.FindElementById("Ischklocked");
	}

	public Element getSubmit() {
		return driver.FindElementById("btnsubmit");
	}

	public Element getCancel() {
		return driver.FindElementById("submit");
	}

	public Element getLock() {
		return driver.FindElementByXPath("//div[@class='tab-pane fade in active']//div[10]//div[1]//label[1]//i[1]");
	}

	public Element getRMUasPASecurityGroup() {
		return driver.FindElementByXPath("(//*[@id='SysAdminSecGroup'][@tabindex='7'])");
	}

	public Element getRMUasRMUSecurityGroup() {
		return driver.FindElementByXPath("(//*[@id='SysAdminSecGroup'][@tabindex='6'])");
	}

	public Element manageBtn() {
		return driver.FindElementByXPath("//*[@id=\"2\"]/i");
	}

	public Element manageUser() {
		return driver.FindElementByXPath("//*[@id=\"LeftMenu\"]/li[3]/ul/li[6]/a");
	}

	public Element userTextInput() {
		return driver.FindElementByXPath("//*[@id=\"txtsearchUser\"]");
	}

	public Element userFiletersBtn() {
		return driver.FindElementByXPath("//*[@id=\"btnAppyFilter\"]");
	}

	public Element userEditBtn() {
		return driver.FindElementByXPath("//table[@id='dtUserList']//tr[1]//td[7]//a[text()='Edit']");
	}

	public Element userSelectSecurityGroup() {
		return driver.FindElementByXPath("//select[@id='ddlSg']");
	}

	// Added by Gopinath - 19/01/2022
	public Element getSelectProject(String projectName) {
		return driver.FindElementByXPath("//*[@tabindex='7']//option[@title='" + projectName + "']");
	}

	public Element getSecurityDropDown() {
		return driver.FindElementByXPath("//select[@id='ddlSysAdminSecGroup']");
	}

	public Element getRowByFirstName(int rowNum) {
		return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[" + rowNum + "]/td[1]");
	}

	public Element getDeleteButtonByRow(int rowNum) {
		return driver.FindElementByXPath("//table[@id='dtUserList']//tr[" + rowNum
				+ "]/td[8]/a[contains(text(),'Delete')] | //table[@id='dtUserList']//tr[" + rowNum
				+ "]/td[9]/a[contains(text(),'Delete')]");
	}

	// Added by baskar
	public Element getSelectUserToEdit(String projectName) {
		return driver.FindElementByXPath(
				"//table[@id='dtUserList']//tr//td[text()='" + projectName + "']//..//a[contains(text(),'Edit')]");
	}

	public Element getNativeDownLoadCheck() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanDownloadNative']//parent::label//i");
	}

	public Element getSaveEditUser() {
		return driver.FindElementByXPath("//button[normalize-space()='Save']");
	}

	public Element getLoginUserEdit() {
		return driver.FindElementByXPath("//table[@id='dtUserList']//tr//td//a[text()='Edit']");
	}

	public Element getIngestion() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanIngestions']//parent::label//i");
	}

	public Element getIngestionTab() {
		return driver.FindElementByXPath("//label[text()='Ingestions']//parent::a");
	}

	public Element getManageCheck() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanManage']//parent::label//i");
	}

	public Element getManageTab() {
		return driver.FindElementByXPath("//label[text()='Manage']//parent::a");
	}

	public Element getStatusCategorize() {
		return driver.FindElementByXPath("//label[normalize-space()='Categorize']");
	}

	public Element getCategorizeCheck() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanProview']//parent::label//i");
	}

	public Element getCategorizeTab() {
		return driver.FindElementByXPath("//label[text()='Categorize']//parent::a");
	}

	public Element getFirstNameTab() {
		return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[1]/td[1]");
	}

	public Element getLastNameTab() {
		return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[1]/td[2]");
	}

	public Element getPopUpMessageEditUser() {
		return driver.FindElementByXPath("//p[text()='User profile was successfully modified']");
	}

	public ElementCollection getSecurityGroupList() {
		return driver.FindElementsByXPath("//select[@id='ddlSg']//option");
	}

	public Element getCategorizeStatusCheck() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanProview'][@checked='checked']");
	}

	public Element getSelectUserToSaUserEdit() {
		return driver.FindElementByXPath("//table[@id='dtUserList']//tr//td//..//a[contains(text(),'Edit')]");
	}

	public Element getIngestionStatus() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanIngestions'][@checked='checked']");
	}

	public Element getSearchCheck() {
		return driver.FindElementByXPath("(//input[@id='UserRights_CanSearching'][position()=1])");
	}

	public Element getSearchTab() {
		return driver.FindElementByXPath("//label[text()='Search']//parent::a");
	}

	public Element getBulkUserAccessTab() {
		return driver.FindElementById("btnBulkUserAccessControl");
	}

	public Element getSelectRollId() {
		return driver.FindElementByXPath("//select[@id='ddlBulkUserRoles']");
	}

	public Element getBulkManage() {
		return driver.FindElementByXPath("//label[@id='lblCanManage']//i");
	}

	public Element getBulkIngestion() {
		return driver.FindElementByXPath("//label[@id='lblCanIngestion']//i");
	}

	public Element getBulkProduction() {
		return driver.FindElementByXPath("//label[@id='lblCanProductions']//i");
	}

	public Element getBulkSearch() {
		return driver.FindElementByXPath("//label[@id='lblCanSearching']//i");
	}

	public Element getBulkExplorer() {
		return driver.FindElementByXPath("//label[@id='lblCanConceptExplorer']//i");
	}

	public Element getBulkComExp() {
		return driver.FindElementByXPath("//label[@id='lblCanCommunicationsExplorer']//i");
	}

	public Element getBulkCatagories() {
		return driver.FindElementByXPath("//input[@id='chkCanProview']//parent::label//i");
	}

	public Element getBulkDataSet() {
		return driver.FindElementByXPath("//input[@id='chkCanDataSets']//parent::label//i");
	}

	public Element getBulkCollection() {
		return driver.FindElementByXPath("//label[@id='lblCanCollections']//i");
	}

	public Element getBulkReport() {
		return driver.FindElementByXPath("//label[@id='lblCanAllReports']//i");
	}

	public Element getBulkDownLoadNative() {
		return driver.FindElementByXPath("//label[@id='lblCanDownloadNative']//i");
	}

	public Element getBulkRedaction() {
		return driver.FindElementByXPath("//label[@id='lblCanRedactions']//i");
	}

	public Element getBulkHighlighting() {
		return driver.FindElementByXPath("//label[@id='lblCanHighlighting']//i");
	}

	public Element getBulkReviewerRemark() {
		return driver.FindElementByXPath("//label[@id='lblCanReviewerRemarks']//i");
	}

	public Element getBulkAnalyticalPanel() {
		return driver.FindElementByXPath("//label[@id='lblCanAnalyticsPanels']//i");
	}

	public Element getSelectingProject() {
		return driver.FindElementByXPath("//select[@id='ddlBulkUserProjects']");
	}

	public Element getEnableRadioBtn() {
		return driver.FindElementByXPath("//input[@id='rbEnable']//parent::label");
	}

	public Element getDisableRadioBtn() {
		return driver.FindElementByXPath("//input[@id='rbDisable']//parent::label");
	}

	public Element getSelectBulkUser(String userName) {
		return driver.FindElementByXPath("//div[@id='divBulkUserList']//label[contains(text(),'" + userName + "')]//i");
	}

	public Element getSelectDropProject(String projectName) {
		return driver.FindElementByXPath("//option[@title='" + projectName + "']");
	}

	public Element getBulkUserSaveBtn() {
		return driver.FindElementById("btnSaveBulkAccessControls");
	}

	public Element getBulkUserSecurityGroup() {
		return driver.FindElementById("ddlBulkUserSecurityGroup");
	}

	public Element getSelectDropSG(String sgName) {
		return driver.FindElementByXPath("//option[.='" + sgName + "']");
	}

	public Element getAllReportTab() {
		return driver.FindElementByXPath("//label[text()='Reports']//parent::a");
	}

	// add by Aathith
	public ElementCollection unAssigneduserlist() {
		return driver.FindElementsByXPath("//select[@id='UnAssignedUsersForDomain']//option");
	}

	public Element VerifyingAssignedUser(String UserName) {
		return driver.FindElementByXPath(
				"//select[@id='AssignedUsersForDomain']//option[contains(text(),'" + UserName + "')]");
	}

	public Element getProjectBtn() {
		return driver.FindElementByXPath("//a[text()='Projects']");
	}

	public Element gettDomainBtn() {
		return driver.FindElementByXPath("//a[text()='Domains']");
	}

	public Element getBellyBandMsg() {
		return driver.FindElementByXPath("//p[@class='pText']");
	}

	public Element getLeftArrow() {
		return driver.FindElementById("btnLeftUserMaappingForDomain");
	}

	public Element getSelectuserassignindomain() {
		return driver.FindElementByXPath("//select[@id='AssignedUsersForDomain']");
	}

	public Element getSelectAssignedUserDomain() {
		return driver.FindElementByXPath("//select[@id='AssignedUsersForDomain']");
	}

	public ElementCollection userDetailsTableHeader() {
		return driver.FindElementsByXPath("//*[@id='dtUserList_wrapper']/div/div/div/table/thead/tr/th");
	}

	public Element tableValue(int row, int colum) {
		return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[" + row + "]/td[" + colum + "]");
	}

	public Element getbellyBandMsg() {
		return driver.FindElementByXPath("//p[@class='pText']");
	}

	public ElementCollection getAssignedUserName(String userName) {
		return driver.FindElementsByXPath(
				"//select[@id='AssignedUsersForDomain']//option[contains(text(),'" + userName + "')]");
	}

	public Element getDomaintab() {
		return driver.FindElementByXPath("//a[@id='tabExiting']");
	}

	public Element getProjectTab() {
		return driver.FindElementByXPath("//a[@id='tabNew']");
	}

	public Element getPopUpCloseBtn() {
		return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getLeftBtndomainuser() {
		return driver.FindElementByXPath("//a[@id='btnLeftUserMaappingForDomain']");
	}

	public ElementCollection getUserManageTab() {
		return driver.FindElementsByXPath("//table[@class='table dataTable no-footer']//tr//th");
	}

	public ElementCollection getTableColumnData(int i) {
		return driver.FindElementsByXPath("//table[@id='dtUserList']//tbody//tr//td[" + i + "]");

	}

	public Element getSearchIconCheck() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanSearching']//parent::label//i");
	}

	public Element getSearchStatusCheck() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanSearching'][@checked='checked']");
	}

	public Element getDashBoardReviewer() {
		return driver.FindElementByXPath("//h1[text()[normalize-space()='My Dashboard']]");
	}

	public Element getPaHomePage() {
		return driver.FindElementByXPath("//li//span[text()='Project Administrator']");
	}

	public Element getRedaction() {
		return driver.FindElementByXPath("//a//span[text()='REDACTIONS']");
	}

	public Element getBulkUserCancelBtn() {
		return driver.FindElementById("btnCancelBulkAccessControls");
	}

	public Element getAllReportIconCheck() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanAllReports']//parent::label//i");
	}

	public Element getAllReportStatusCheck() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanAllReports'][@checked='checked']");
	}

	public Element getDataSetStatus() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanDatasets'][@checked='checked']");
	}

	public Element getDataSet() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanDatasets']//parent::label//i");
	}

	public ElementCollection getRowValues(int rowNum) {
		return driver.FindElementsByXPath("//*[@id='dtUserList']/tbody/tr/td[" + rowNum + "]");
	}

	public Element getEditCancel() {
		return driver.FindElementByXPath("//input[@value='Cancel']");
	}

	public Element getCheckingAssignedUserSG(String FullName) {
		return driver.FindElementByXPath("//select[@id='AssignedUser']//option[contains(text(),'" + FullName + "')]");
	}

	public Element getAssignUserProjectDrp_Dwn() {
		return driver.FindElementById("lstProjects");
	}

	public Element getAdvancedSearchAudioRemarkIcon() {
		return driver.FindElementByXPath("//*[@id='remarks-btn-audio-view']/a/span/i[2]");
	}

	public Element getCalendarOption() {
		return driver.FindElementByXPath("//i[@class='icon-append fa fa-calendar']");
	}

	public Element getYearDropDown() {
		return driver.FindElementByXPath("//select[@class='ui-datepicker-year']");
	}

	public Element getMonthDropDown() {
		return driver.FindElementByXPath("//select[@class='ui-datepicker-month']");
	}

	public Element getSlectDate(String SendValue) {
		return driver.FindElementByXPath("//a[@class='ui-state-default'][text()=" + SendValue + "]");
	}

	public Element getAddNewUserPopUpWindow() {
		return driver.FindElementByXPath("//span[@id='ui-id-1']");
	}

	public Element getBilliableUserText() {
		return driver.FindElementByXPath("//label[normalize-space(.)='Billable User:']");
	}

	public Element getBilliableUserCheckBox() {
		return driver.FindElementByXPath("//input[@id='IsBillableCheckbox']//..//i");
	}

	public Element getUnAssignedDomainUser() {
		return driver.FindElementByXPath("//select[@id='UnAssignedUser']");
	}

	public Element getDomainRole() {
		return driver.FindElementByXPath("//select[@id='lstRoles']");
	}

	public Element getDomainSG() {
		return driver.FindElementByXPath("//select[@id='lstSecurityGroup']");
	}

	public Element getDomainUserRightArrow() {
		return driver.FindElementByXPath("//a[@id='btnRightUserMaapping']");
	}

	public Element getDomainUserCancelButton() {
		return driver.FindElementById("btnCancel");
	}

	public ElementCollection getProjectCollection() {
		return driver.FindElementsByXPath("//ul[@id='ddlProject11']//a");
	}

	public Element getRoleAccess(String roll) {
		return driver.FindElementByXPath("//li[@class='username']//span[text()='" + roll + "']");
	}

	public Element getClientNameTextBox() {
		return driver.FindElementByXPath("//input[@id='txtClientEntityLabel']");
	}

	public Element getAssignedDomain(String domain) {
		return driver.FindElementByXPath(
				"//select[@id='AssignedUsersForDomain']//option[contains(text(),'" + domain + "')]");
	}

	// Added by Mohan
	public Element getManageBtn() {
		return driver.FindElementByName("Manage");
	}

	public Element getConfirmTab() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public Element getSecurityTab() {
		return driver.FindElementById("ddlSg");
	}

	public Element getFunctionalityButton() {
		return driver.FindElementByXPath("//a[contains(normalize-space(.),'Functionality')]");
	}

	public Element getSaveButtonInFuctionalitiesTab() {
		return driver.FindElementByXPath("//button[@id='btnsubmit']");
	}

	public Element getSelectFuctionalitiesCheckBox(String tabNames) {
		return driver.FindElementByXPath("//label[contains(normalize-space(.),'" + tabNames + "')]//i");
	}

	public Element getEditButtonFromUserManagentPage() {
		return driver.FindElementByXPath("//*[@id='dtUserList']//tr[1]//td//a[text()='Edit']");
	}

	public Element getUserChangeDropDown() {
		return driver.FindElementByXPath("//select[@name='Role']");
	}

	
	public Element getProjectNameFromUserManagment(String projectName) {
		return driver.FindElementByXPath("//*[@id='dtUserList']//td[text()='" + projectName + "']");
	}

	public Element getUserListNextButton() {
		return driver.FindElementByXPath("//a[text()='Next']");
	}

	public ElementCollection getAssgnPaginationCount() {
		return driver.FindElementsByCssSelector("li[class*='paginate_button '] a");
	}
	
	//Added by Aathith
	public Element getUserRole(String role) {
		return driver.FindElementByXPath("//select[@id='ddlAdminCreateUserRoles']/option[text()='"+role+"']");
	}

	public UserManagement(Driver driver) {

		this.driver = driver;
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
		// this.driver.getWebDriver().get(Input.url+ "User/UserListView");
	}

	public void findUsers(String name, String role, String userSate) {

		if (userSate.equalsIgnoreCase("inactive")) {
			driver.scrollPageToTop();
			getActiveInactiveBtn().waitAndClick(10);
		}
		getUserNameFilter().SendKeys(name);
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		getFilerApplyBtn().Click();

		boolean nextPage = true;

		while (nextPage) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNumberofUsers().Visible();
				}
			}), Input.wait30);
			System.out.println("Number of records in a current page : " + getNumberofUsers().size());
			System.out.println("Validation started..please wait");

			for (int i = 1; i < getNumberofUsers().size(); i++) {
				if (userSate.endsWith("active")) {
					Assert.assertTrue((getUserFirstName(i).getText().contains(name)
							|| getUserLastName(i).getText().contains(name))
							&& getUserState(i).getText().equals("Active"));
					// System.out.println(getUserFirstName(i).getText()+"
					// "+getUserLastName(i).getText() + "matched");

				} else if (userSate.equalsIgnoreCase("inactive")) {
					Assert.assertTrue((getUserFirstName(i).getText().contains(name)
							|| getUserLastName(i).getText().contains(name))
							&& (getUserState(i).getText().equals("Active")
									|| getUserState(i).getText().equals("In Active")));
					// System.out.println(getUserFirstName(i).getText()+"
					// "+getUserLastName(i).getText() + "matched");

				}

			}
			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}

	}

	public void setPassword(String pwd) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSetPassword().Visible();
			}
		}), Input.wait30);
		getSetPassword().SendKeys(pwd);
		getConfirmPassword().SendKeys(pwd);
		getSavePassword().Click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// successPasswordSet();

	}

	public void createUser(String firstName, String lastName, String role, String emailId, String domain,
			String project) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddUserBtn().Visible();
			}
		}), Input.wait30);
		getAddUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFirstName().Visible();
			}
		}), Input.wait30);
		getFirstName().SendKeys(firstName);
		getLastName().SendKeys(lastName);
		getSelectRole().selectFromDropdown().selectByVisibleText(role);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDomain().Visible();
			}
		}), Input.wait30);
		if (role.equalsIgnoreCase("Domain Administrator")) {
			getSelectDomain().selectFromDropdown().selectByIndex(1);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEmail().Exists();
			}
		}), Input.wait30);
		getEmail().SendKeys(emailId);
		getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		if (role.equalsIgnoreCase("Project Administrator") || role.equalsIgnoreCase("Review Manager")
				|| role.equalsIgnoreCase("Reviewer")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectProject().Visible();
				}
			}), Input.wait30);
			// getSelectDomain().SendKeys(domain);
			getSelectProject().selectFromDropdown().selectByVisibleText(project);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSecurityGroup().Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (role.equalsIgnoreCase("Review Manager") || role.equalsIgnoreCase("Reviewer")) {
			getSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			;

		}
		getSave().Click();
		bc.VerifySuccessMessage("User profile was successfully created");

	}

	// 00AutoRev510105322
	public void selectUserToEdit(String firstName) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getManageFilterByDate().Visible();
			}
		}), Input.wait30);
		getManageFilterByDate().SendKeys("" + Keys.ENTER);

		getFilerApplyBtn().Click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getRowsInTable().Visible() ;}}), Input.wait30);
		 */
		for (int i = 1; i <= getRowsInTable().size(); i++) {
			// System.out.println(driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+i+"]/td[1]").getText());
			if (driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[" + i + "]/td[1]").getText()
					.equals(firstName)) {
				driver.FindElementByXPath("//table[@id='dtUserList']//tr[" + i + "]/td[8]/a[contains(text(),'Edit')]")
						.Click();
				break;
			}
		}

	}

	public void modifyUserRights(String rightName, String value) {
		// TODO Auto-generated method stub

		if (rightName.equalsIgnoreCase("Manage Project") && value.equalsIgnoreCase("checkIn")) {
			getManageproject().checkIn();
		}
		BaseClass bc = new BaseClass(driver);
		getSubmitChanges().Click();
		bc.VerifySuccessMessage("User profile was successfully modified");

	}

	// 00AutoRev510105322
	//modified delete button xpath on 07-07-2022 by Aathith
	public void deleteUser(String firstName) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getManageFilterByDate().Visible();
			}
		}), Input.wait30);
		getManageFilterByDate().SendKeys("" + Keys.ENTER);

		getFilerApplyBtn().Click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getRowsInTable().Visible() ;}}), Input.wait30);
		 */
		for (int i = 1; i <= getRowsInTable().size(); i++) {
			// System.out.println(driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+i+"]/td[1]").getText());
			if (driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr[" + i + "]/td[1]").getText()
					.equals(firstName)) {
				driver.FindElementByXPath("//table[@id='dtUserList']//tr[" + i + "]/td[9]/a[contains(text(),'Delete')]").waitAndClick(10);
				break;
			}
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmDelete().Visible();
			}
		}), Input.wait30);
		getConfirmDelete().waitAndClick(5);
		bc.VerifySuccessMessage("User has been deactivated");

	}

	public void RMUUserwithAttorneyProfile(String firstName, String lastName, String role, String emailId,
			String domain, String project) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddUserBtn().Visible();
			}
		}), Input.wait30);
		getAddUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFirstName().Visible();
			}
		}), Input.wait30);
		getFirstName().SendKeys(firstName);
		getLastName().SendKeys(lastName);
		getSelectRole().selectFromDropdown().selectByVisibleText(role);
		if (role.equalsIgnoreCase("Review Manager")) {
			getAttorneyprofilecheckbox().WaitUntilPresent();
			Assert.assertTrue(getAttorneyprofilecheckbox().Displayed());
			getAttorneyprofilecheckbox().waitAndClick(10);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDomain().Visible();
			}
		}), Input.wait30);
		if (role.equalsIgnoreCase("Domain Administrator")) {
			getSelectDomain().selectFromDropdown().selectByIndex(1);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEmail().Exists();
			}
		}), Input.wait30);
		getEmail().SendKeys(emailId);
		getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		if (role.equalsIgnoreCase("Project Administrator") || role.equalsIgnoreCase("Review Manager")
				|| role.equalsIgnoreCase("Reviewer")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectProject().Visible();
				}
			}), Input.wait30);
			// getSelectDomain().SendKeys(domain);
			getSelectProject().selectFromDropdown().selectByVisibleText(project);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSecurityGroup().Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		if (role.equalsIgnoreCase("Review Manager") || role.equalsIgnoreCase("Reviewer")) {
			getSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			;

		}
		getSave().Click();
		bc.VerifySuccessMessage("User profile was successfully created");

	}

	public void checkUserRights(String role) {

		if (role.equalsIgnoreCase("Review Manager")) {
			String a = getManageRole_Functionality_Dataset().GetAttribute("value");
			System.out.println(a);

			if (getManageRole_Functionality_Dataset().GetAttribute("value").contains("diabled"))
				System.out.println("Test passed");
			else
				System.out.println("Test failed");
			// Assert.assertFalse(getManageRole_Functionality_Dataset().GetAttribute("value"));
			Assert.assertFalse(getManageRole_Functionality_ConceptExplr().Enabled());
			Assert.assertFalse(getManageRole_Functionality_CommExplr().Enabled());
			Assert.assertFalse(getManageRole_Functionality_Categorize().Enabled());
			Assert.assertFalse(getManageRole_Functionality_AnalyticPanel().Enabled());
			Assert.assertFalse(getManageRole_Functionality_AllReport().Enabled());
			Assert.assertFalse(getManageRole_Functionality_Native().Enabled());
			Assert.assertFalse(getManageRole_Functionality_Production().Enabled());
			Assert.assertFalse(getManageRole_Functionality_Ingestion().Enabled());
			Assert.assertFalse(getManageRole_Functionality_Dataset().Enabled());
			Assert.assertFalse(getManageRole_Functionality_Redactions().Enabled());
			Assert.assertFalse(getManageRole_Functionality_Highlighting().Enabled());
			Assert.assertFalse(getManageRole_Functionality_Remarks().Enabled());
		}
	}

	public void Assignusertodomain(String clientname) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignUserButton().Visible();
			}
		}), Input.wait30);
		getAssignUserButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDomainname().Visible();
			}
		}), Input.wait30);
		getSelectDomainname().selectFromDropdown().selectByVisibleText(clientname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectusertoassignindomain().Visible();
			}
		}), Input.wait30);
		getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText("0QADA1 DA1");

		getrightBtndomainuser().waitAndClick(10);

		getsavedomainuser().waitAndClick(10);

	}

	// added by Narendra

	public void lockAccount(String name, String role, String userSate) throws InterruptedException {

		if (userSate.equalsIgnoreCase("inactive")) {
			driver.scrollPageToTop();
			getActiveInactiveBtn().waitAndClick(10);
		}
		getUserNameFilter().SendKeys(name);
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		getFilerApplyBtn().Click();

		boolean nextPage = true;

		while (nextPage) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNumberofUsers().Visible();
				}
			}), Input.wait30);
			System.out.println("Number of records in a current page : " + getNumberofUsers().size());
			UtilityLog.info("Number of records in a current page : " + getNumberofUsers().size());
			System.out.println("Validation started..please wait");
			UtilityLog.info("Validation started..please wait");

			for (int i = 1; i < getNumberofUsers().size(); i++) {
				if (userSate.endsWith("active")) {
					getEdit(i).waitAndClick(10);
					Thread.sleep(3000);
					if (getIsLock().GetAttribute("checked") != null) {
						System.out.println("Already Cheacked");
						UtilityLog.info("Already Cheacked");
						getCancel().waitAndClick(10);
					} else {
						getIsLock().Click();
						getSubmit().waitAndClick(10);
					}

				} else if (userSate.equalsIgnoreCase("inactive")) {

					System.out.println("Users are Inactive");
					UtilityLog.info("Users are Inactive");

				}

			}

			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}
	}

	public void UnlockAccount(String name, String role, String userSate) throws InterruptedException {

		if (userSate.equalsIgnoreCase("inactive")) {
			driver.scrollPageToTop();
			getActiveInactiveBtn().waitAndClick(10);
		}
		getUserNameFilter().SendKeys(name);
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		getFilerApplyBtn().Click();

		boolean nextPage = true;

		while (nextPage) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNumberofUsers().Visible();
				}
			}), Input.wait30);
			System.out.println("Number of records in a current page : " + getNumberofUsers().size());
			System.out.println("Validation started..please wait");

			for (int i = 1; i < getNumberofUsers().size(); i++) {
				if (userSate.endsWith("active")) {
					getEdit(i).waitAndClick(10);
					Thread.sleep(3000);
					if (getIsLock().GetAttribute("checked") != null) {
						getLock().Click();
						getSubmit().waitAndClick(10);
						System.out.println("Account is unlocked now");
					} else {
						System.out.println("Already unlocked");
						getCancel().waitAndClick(10);
					}

				} else if (userSate.equalsIgnoreCase("inactive")) {

					System.out.println("Users are Inactive");

				}

			}

			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}
	}

	public void RMUUserAttorneyasPA(String firstName, String lastName, String role, String emailId, String domain) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddUserBtn().Visible();
			}
		}), Input.wait30);
		getAddUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFirstName().Visible();
			}
		}), Input.wait30);
		getFirstName().SendKeys(firstName);
		getLastName().SendKeys(lastName);
		getSelectRole().selectFromDropdown().selectByVisibleText(role);
		if (role.equalsIgnoreCase("Review Manager")) {
			getAttorneyprofilecheckbox().WaitUntilPresent();
			Assert.assertTrue(getAttorneyprofilecheckbox().Displayed());
			getAttorneyprofilecheckbox().waitAndClick(10);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDomain().Visible();
			}
		}), Input.wait30);
		if (role.equalsIgnoreCase("Domain Administrator")) {
			getSelectDomain().selectFromDropdown().selectByIndex(1);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEmail().Exists();
			}
		}), Input.wait30);
		getEmail().SendKeys(emailId);
		getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		if (role.equalsIgnoreCase("Project Administrator") || role.equalsIgnoreCase("Review Manager")
				|| role.equalsIgnoreCase("Reviewer")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectProject().Visible();
				}
			}), Input.wait30);
			// getSelectDomain().SendKeys(domain);
			// getSelectProject().selectFromDropdown().selectByVisibleText(project);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRMUasPASecurityGroup().Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		if (role.equalsIgnoreCase("Review Manager") || role.equalsIgnoreCase("Reviewer")) {
			getRMUasPASecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			;

		}
		getSave().Click();
		bc.VerifySuccessMessage("User profile was successfully created");

	}

	public void RMUUserAttorneyasRMU(String firstName, String lastName, String role, String emailId, String domain) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddUserBtn().Visible();
			}
		}), Input.wait30);
		getAddUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFirstName().Visible();
			}
		}), Input.wait30);
		getFirstName().SendKeys(firstName);
		getLastName().SendKeys(lastName);
		getSelectRole().selectFromDropdown().selectByVisibleText(role);
		if (role.equalsIgnoreCase("Review Manager")) {
			getAttorneyprofilecheckbox().WaitUntilPresent();
			Assert.assertTrue(getAttorneyprofilecheckbox().Displayed());
			getAttorneyprofilecheckbox().waitAndClick(10);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDomain().Visible();
			}
		}), Input.wait30);
		if (role.equalsIgnoreCase("Domain Administrator")) {
			getSelectDomain().selectFromDropdown().selectByIndex(1);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEmail().Exists();
			}
		}), Input.wait30);
		getEmail().SendKeys(emailId);
		getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		if (role.equalsIgnoreCase("Project Administrator") || role.equalsIgnoreCase("Review Manager")
				|| role.equalsIgnoreCase("Reviewer")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectProject().Visible();
				}
			}), Input.wait30);
			// getSelectDomain().SendKeys(domain);
			// getSelectProject().selectFromDropdown().selectByVisibleText(project);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRMUasRMUSecurityGroup().Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		if (role.equalsIgnoreCase("Review Manager") || role.equalsIgnoreCase("Reviewer")) {
			getRMUasRMUSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			;

		}
		getSave().Click();
		bc.VerifySuccessMessage("User profile was successfully created");

	}

	/**
	 * @author Indium-Baskar date: 30/9/2021 Modified date: 6/12/2021
	 * @modified By Jeevitha
	 * @Description: this method used for giving access to security group
	 */
	public void assignAccessToSecurityGroups(String SgName, String username) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		driver.scrollPageToTop();
		Thread.sleep(3000);// Required
		wait.until(ExpectedConditions.elementToBeClickable(manageBtn().getWebElement()));
		actions.moveToElement(manageBtn().getWebElement());
		actions.click().build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(manageUser().getWebElement()));
		actions.moveToElement(manageUser().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(userTextInput().getWebElement());
		actions.click();
		actions.sendKeys(username);
		actions.build().perform();
		actions.moveToElement(userFiletersBtn().getWebElement());
		actions.click().build().perform();
		Thread.sleep(4000);
		wait.until(ExpectedConditions.elementToBeClickable(userEditBtn().getWebElement()));
		actions.moveToElement(userEditBtn().getWebElement());
		actions.click();
		actions.build().perform();
		Thread.sleep(4000);// Required
		driver.scrollingToBottomofAPage();
		Select assignSG1 = new Select(userSelectSecurityGroup().getWebElement());
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		assignSG1.selectByVisibleText(SgName);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		if (getSavePassword().isElementAvailable(3)) {
			getSavePassword().waitAndClick(3);
			driver.waitForPageToBeReady();
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for creating new user.
	 */
	public void createNewUser(String firstName, String lastName, String role, String emailId, String domain,
			String project) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddUserBtn().Visible();
			}
		}), Input.wait30);
		getAddUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFirstName().Visible();
			}
		}), Input.wait30);
		getFirstName().SendKeys(firstName);
		getLastName().SendKeys(lastName);
		getSelectRole().selectFromDropdown().selectByVisibleText(role);

		if (role.equalsIgnoreCase("Domain Administrator")) {
			getSelectDomain().isElementAvailable(10);
			getSelectDomain().selectFromDropdown().selectByIndex(1);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEmail().Exists();
			}
		}), Input.wait30);
		getEmail().SendKeys(emailId);
		getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		if (role.equalsIgnoreCase("Project Administrator") || role.equalsIgnoreCase("Review Manager")
				|| role.equalsIgnoreCase("Reviewer")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectProject().Visible();
				}
			}), Input.wait30);
			getSelectProject().Click();
			getSelectProject(project).Click();
		}

		if (role.equalsIgnoreCase("Review Manager") || role.equalsIgnoreCase("Reviewer")) {
			getSecurityDropDown().isElementAvailable(10);
			getSecurityDropDown().selectFromDropdown().selectByVisibleText("Default Security Group");

		}
		getSave().waitAndClick(10);
		bc.VerifySuccessMessage("User profile was successfully created");

	}

	/**
	 * @author Gopinath
	 * @Description : Method for deleting added user.
	 * @param firstName : firstName is String value that first name of user need to
	 *                  delete.
	 */
	public void deleteAddedUser(String firstName) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getManageFilterByDate().Visible();
				}
			}), Input.wait30);
			getManageFilterByDate().SendKeys("" + Keys.ENTER);

			getFilerApplyBtn().Click();
			bc.waitTime(3);
			for (int i = 1; i <= getRowsInTable().size(); i++) {
				if (getRowByFirstName(i).getText().equals(firstName)) {
					getDeleteButtonByRow(i).isElementAvailable(10);
					driver.waitForPageToBeReady();
					getDeleteButtonByRow(i).Click();
					break;
				}
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getConfirmDelete().Visible();
				}
			}), Input.wait30);
			getConfirmDelete().isElementAvailable(10);
			getConfirmDelete().waitAndClick(5);
			bc.VerifySuccessMessage("User has been deactivated");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while deleting added user" + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods for passing user name
	 * @param username
	 */
	public void passingUserName(String username) throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getUserNameFilter());
		getUserNameFilter().SendKeys(username);
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods for apply filter button
	 */
	public void applyFilter() throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getFilerApplyBtn());
		getFilerApplyBtn().waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods for Edit user
	 */
	public void editSelectedUser(String project) throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getSelectUserToEdit(project));
		getSelectUserToEdit(project).waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods for Edit functionality
	 */
	public void editFunctionality(String project) throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getSelectUserToEdit(project));
		getSelectUserToEdit(project).waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods for Edit functionality
	 */
	public void editLoginUser() throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getLoginUserEdit());
		getLoginUserEdit().waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods Native download checkbox
	 */
	public void nativeDownload() throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getNativeDownLoadCheck());
		getNativeDownLoadCheck().waitAndClick(5);
		bc.waitForElement(getSaveEditUser());
		getSaveEditUser().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods Ingestion checkbox
	 */
	public void verifyIngestion() throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getIngestion());
		getIngestion().waitAndClick(5);
		bc.waitForElement(getSaveEditUser());
		getSaveEditUser().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods for ingestion validation icon
	 * @param downloadFalse [False will return ingestion icon not present]
	 * @param downloadTrue  [True will return ingestion icon present]
	 */

	public void verifyIngestionIcon(boolean downloadFalse, boolean downloadTrue) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (getIngestionTab().isElementAvailable(4)) {
				bc.failedStep("Ingestion tab icon available");
			} else {
				bc.passedStep("Ingestion tab icon not present in left of the menu");
			}
		} else if (downloadTrue == true) {
			if (getIngestionTab().isElementAvailable(4)) {
				bc.passedStep("Ingestion tab icon not present in left of the menu");
			} else {
				bc.failedStep("Ingestion tab icon available");
			}
		}
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Baskar date: 08/03/2022 Modified date: 08/03/2022
	 * @Description:Methods for Manage checkbox
	 */
	public void verifyManageCheckBox() throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getManageCheck());
		getManageCheck().waitAndClick(5);
		bc.waitForElement(getSaveEditUser());
		getSaveEditUser().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar date: 08/03/2022 Modified date: 08/03/2022
	 * @Description:Methods for Manage icon validation
	 * @param downloadFalse [False will return Manage icon not present]
	 * @param downloadTrue  [True will return Manage icon present]
	 */

	public void verifyManageIcon(boolean downloadFalse, boolean downloadTrue) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (getManageTab().isElementAvailable(4)) {
				bc.failedStep("Manage tab icon available");
			} else {
				bc.passedStep("Manage tab icon not present in left of the menu");
			}
		} else if (downloadTrue == true) {
			if (getManageTab().isElementAvailable(4)) {
				bc.passedStep("Manage tab icon present in left of the menu");
			} else {
				bc.failedStep("Manage tab icon available");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 08/03/2022 Modified date: 08/03/2022
	 * @Description:Methods for Categorize checkbox
	 */
	public void verifyCategorizeCheckBox() throws Exception {
		driver.waitForPageToBeReady();
		bc.waitForElement(getCategorizeCheck());
		getCategorizeCheck().waitAndClick(5);
		bc.waitForElement(getSaveEditUser());
		getSaveEditUser().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar date: 08/03/2022 Modified date: 08/03/2022
	 * @Description:Methods for Categorize icon validation
	 * @param downloadFalse [False will return Categorize icon not present]
	 * @param downloadTrue  [True will return Categorize icon present]
	 */

	public void verifyCategorizeIcon(boolean downloadFalse, boolean downloadTrue) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (getCategorizeTab().isElementAvailable(4)) {
				bc.failedStep("Categorize tab icon available");
			} else {
				bc.passedStep("Categorize tab icon not present in left of the menu");
			}
		} else if (downloadTrue == true) {
			if (getCategorizeTab().isElementAvailable(4)) {
				bc.passedStep("Categorize tab icon present in left of the menu");
			} else {
				bc.failedStep("Categorize tab icon available");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 08/03/2022 Modified date: 08/03/2022
	 * @Description:Methods for Search icon validation
	 * @param downloadFalse [False will return Categorize icon not present]
	 * @param downloadTrue  [True will return Categorize icon present]
	 */

	public void verifySearchIcon(boolean downloadFalse, boolean downloadTrue, String rollUser) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (getSearchTab().isElementAvailable(4)) {
				bc.failedStep("Search tab icon available:" + rollUser);
			} else {
				bc.passedStep("Search tab icon not present in left of the menu:" + rollUser);
			}
		} else if (downloadTrue == true) {
			if (getSearchTab().isElementAvailable(4)) {
				bc.passedStep("Search tab icon  present in left of the menu:" + rollUser);
			} else {
				bc.failedStep("Search tab icon available:" + rollUser);
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 08/03/2022 Modified date: 08/03/2022
	 * @Description:Methods for Categorize icon validation
	 * @param downloadFalse [False will return Categorize icon not present]
	 * @param downloadTrue  [True will return Categorize icon present]
	 */

	public void verifyBulkCategorizeIcon(boolean downloadFalse, boolean downloadTrue, String rollUser) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (getCategorizeTab().isElementAvailable(4)) {
				bc.failedStep("Categorize tab icon available :" + rollUser + "");
			} else {
				bc.passedStep("Categorize tab icon not present in left of the menu :" + rollUser + "");
			}
		} else if (downloadTrue == true) {
			if (getCategorizeTab().isElementAvailable(4)) {
				bc.passedStep("Categorize tab icon present in left of the menu :" + rollUser + "");
			} else {
				bc.failedStep("Categorize tab icon available :" + rollUser + "");
			}
		}
	}

	public void defaultSelectionCheckboxForAllRole(boolean manage, boolean ingestion, boolean production,
			boolean search, boolean explorer, boolean comExplorer, boolean catagories, boolean dataSet,
			boolean collection, boolean report, boolean downloadNative, boolean redaction, boolean highlighted,
			boolean reviewerRemark, boolean analyticalPanel) {
		driver.waitForPageToBeReady();
		if (manage == true) {
			getBulkManage().waitAndClick(5);
		}
		if (ingestion == true) {
			getBulkIngestion().waitAndClick(5);
		}
		if (production == true) {
			getBulkProduction().waitAndClick(5);
		}
		if (search == true) {
			getBulkSearch().waitAndClick(5);
		}
		if (explorer == true) {
			getBulkExplorer().waitAndClick(5);
		}
		if (comExplorer == true) {
			getBulkComExp().waitAndClick(5);
		}
		if (catagories == true) {
			getBulkCatagories().waitAndClick(5);
		}
		if (dataSet == true) {
			getBulkDataSet().waitAndClick(5);
		}
		if (collection == true) {
			getBulkCollection().waitAndClick(5);
		}
		if (report == true) {
			getBulkReport().waitAndClick(5);
		}
		if (downloadNative == true) {
			getBulkDownLoadNative().waitAndClick(5);
		}
		if (redaction == true) {
			getBulkRedaction().waitAndClick(5);
		}
		if (highlighted == true) {
			getBulkHighlighting().waitAndClick(5);
		}
		if (reviewerRemark == true) {
			getBulkReviewerRemark().waitAndClick(5);
		}
		if (analyticalPanel == true) {
			getBulkAnalyticalPanel().waitAndClick(5);
		}
	}

	/**
	 * @author Indium-Baskar date: 08/03/2022 Modified date: 08/03/2022
	 * @Description:Methods for Manage icon validation
	 * @param downloadFalse [False will return Manage icon not present]
	 * @param downloadTrue  [True will return Manage icon present]
	 */

	public void verifyBulkManageIcon(boolean downloadFalse, boolean downloadTrue, String rollUser) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (getManageTab().isElementAvailable(4)) {
				bc.failedStep("Manage tab icon available:" + rollUser + "");
			} else {
				bc.passedStep("Manage tab icon not present in left of the menu:" + rollUser + "");
			}
		} else if (downloadTrue == true) {
			if (getManageTab().isElementAvailable(4)) {
				bc.passedStep("Manage tab icon present in left of the menu:" + rollUser + "");
			} else {
				bc.failedStep("Manage tab icon available:" + rollUser + "");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 21/03/2022 Modified date: 21/03/2022
	 * @Description:Methods for ingestion validation icon
	 * @param downloadFalse [False will return ingestion icon not present]
	 * @param downloadTrue  [True will return ingestion icon present]
	 */

	public void verifyBulkUserIngestionIcon(boolean downloadFalse, boolean downloadTrue, String rollUser) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (getIngestionTab().isElementAvailable(4)) {
				bc.failedStep("Ingestion tab icon available:" + rollUser + "");
			} else {
				bc.passedStep("Ingestion tab icon not present in left of the menu:" + rollUser + "");
			}
		} else if (downloadTrue == true) {
			if (getIngestionTab().isElementAvailable(4)) {
				bc.passedStep("Ingestion tab icon present in left of the menu:" + rollUser + "");
			} else {
				bc.failedStep("Ingestion tab icon available:" + rollUser + "");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 21/03/2022 Modified date: 21/03/2022
	 * @Description:Methods for All report validation icon
	 * @param downloadFalse [False will return report icon not present]
	 * @param downloadTrue  [True will return report icon present]
	 */

	public void verifyBulkUserAllReportIcon(boolean downloadFalse, boolean downloadTrue, String rollUser) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (getAllReportTab().isElementAvailable(4)) {
				bc.failedStep("AllReport tab icon available:" + rollUser + "");
			} else {
				bc.passedStep("AllReport tab icon not present in left of the menu:" + rollUser + "");
			}
		} else if (downloadTrue == true) {
			if (getAllReportTab().isElementAvailable(4)) {
				bc.passedStep("AllReport tab icon present in left of the menu:" + rollUser + "");
			} else {
				bc.failedStep("AllReport tab icon available:" + rollUser + "");
			}
		}
	}

	/**
	 * @author Raghuram.A
	 */
	public void saveSecurityGroup() {
		try {
			if (getSavePassword().isElementAvailable(3)) {
				getSavePassword().waitAndClick(3);
				driver.waitForPageToBeReady();
			} else {
				System.out.println("No Btn");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods Ingestion status checkbox
	 */
	public void verifyStatusIngestion(String status) throws Exception {
		driver.waitForPageToBeReady();

		boolean flagChecked = getIngestionStatus().isElementAvailable(3);
		System.out.println(flagChecked);
		if (flagChecked == false) {
			bc.stepInfo("Ingestion checkbox is unchecked");
		}
		if (flagChecked == true && status == "false") {
			bc.waitForElement(getIngestion());
			getIngestion().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
		}
		if (flagChecked == false && status == "true") {
			bc.waitForElement(getIngestion());
			getIngestion().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
			bc.stepInfo("Ingestion checkbox is checked");
		}

	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods Categorize status checkbox
	 */
	public void verifyStatusCategorize(String status) throws Exception {
		driver.waitForPageToBeReady();

		boolean flagChecked = getCategorizeStatusCheck().isElementAvailable(3);
		System.out.println(flagChecked);
		if (flagChecked == false) {
			bc.stepInfo("Ingestion checkbox is unchecked");
		}
		if (flagChecked == true && status == "false") {
			bc.waitForElement(getCategorizeCheck());
			getCategorizeCheck().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
		}
		if (flagChecked == false && status == "true") {
			bc.waitForElement(getIngestion());
			getCategorizeCheck().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
			bc.stepInfo("Ingestion checkbox is checked");
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param saUser
	 * @Description verify that sa user not in the un unassigned user
	 */
	public void verifySaUserNotInUnAssigneduser(String saUser) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignUserButton());
		getAssignUserButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		List<WebElement> elementList = null;

		elementList = unAssigneduserlist().FindWebElements();
		for (WebElement unassign : elementList) {

			String userName = unassign.getText().trim();

			if (userName.equalsIgnoreCase(saUser)) {
				bc.failedStep("verification failed");
				break;
			}
		}
		bc.passedStep("unAssigned user not contain System Admin user");
	}

	/**
	 * @author Vijaya.Rani
	 * @Description : Method for creating Exiting user.
	 */
	public void createExitingUser(String firstName, String lastName, String role, String emailId, String domain,
			String project) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddUserBtn().Visible();
			}
		}), Input.wait30);
		getAddUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFirstName().Visible();
			}
		}), Input.wait30);
		getFirstName().SendKeys(firstName);
		getLastName().SendKeys(lastName);
		getSelectRole().selectFromDropdown().selectByVisibleText(role);

		if (role.equalsIgnoreCase("Domain Administrator")) {
			getSelectDomain().isElementAvailable(10);
			getSelectDomain().selectFromDropdown().selectByIndex(1);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEmail().Exists();
			}
		}), Input.wait30);
		getEmail().SendKeys(emailId);
		getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		if (role.equalsIgnoreCase("Project Administrator") || role.equalsIgnoreCase("Review Manager")
				|| role.equalsIgnoreCase("Reviewer")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectProject().Visible();
				}
			}), Input.wait30);
			getSelectProject().Click();
			getSelectProject(project).Click();
		}

		if (role.equalsIgnoreCase("Review Manager") || role.equalsIgnoreCase("Reviewer")) {
			getSecurityDropDown().isElementAvailable(10);
			getSecurityDropDown().selectFromDropdown().selectByVisibleText("Default Security Group");

		}
		getSave().waitAndClick(10);
		bc.VerifyErrorMessage("20001000021 : user cannot be created at this level");

	}

	/**
	 * @author Brundha
	 * @param projectName
	 * @param UserName
	 * @param UserName1
	 * @Description verifying the belly band message and assign the user
	 */
	public void verifyingBellyBandMessageInAssignUser(String DomainName, String UserName, String UserName1) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignUserButton());
		getAssignUserButton().waitAndClick(10);
		bc.waitForElement(getSelectDomainname());
		getSelectDomainname().selectFromDropdown().selectByVisibleText(DomainName);
		bc.waitTime(1);
		if (getAssignedDomain(UserName).isElementAvailable(3)) {
			getSelectuserassignindomain().selectFromDropdown().selectByVisibleText(UserName);
			getLeftArrow().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText(UserName1);
		getrightBtndomainuser().waitAndClick(10);
		getProjectBtn().waitAndClick(10);
		String ActualText = getBellyBandMsg().getText();
		String bandMsg = "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?";
		bc.textCompareEquals(bandMsg, ActualText, "Belly band message is displayed as expected",
				"Belly band message is not displayed as expected");
		bc.getYesBtn().waitAndClick(10);
		gettDomainBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		getSelectDomainname().selectFromDropdown().selectByVisibleText(DomainName);
		if (VerifyingAssignedUser(UserName).isDisplayed()) {
			bc.passedStep("The changes made on the user is  modified as expected");
		} else {
			bc.failedStep("The changes made on user is not  modified");

		}

	}

	/**
	 * @author Brundha
	 * 
	 * @param projectName
	 * @param UserName
	 * @Description verifying the belly band message and un assign the user
	 */
	public void selectingConfirmButtonToUnAssignTheAssignedUser(String DomainName, String UserName) {
		driver.waitForPageToBeReady();
		getSelectDomainname().selectFromDropdown().selectByVisibleText(DomainName);
		bc.waitTime(2);
		getSelectuserassignindomain().selectFromDropdown().selectByVisibleText(UserName);
		getLeftArrow().waitAndClick(5);
		getProjectBtn().waitAndClick(5);
		getConfirmDelete().waitAndClick(10);
		driver.waitForPageToBeReady();
		gettDomainBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		getSelectDomainname().selectFromDropdown().selectByVisibleText(DomainName);
		if (VerifyingAssignedUser(UserName).isDisplayed()) {
			bc.passedStep("The changes made on the user is not  modified as expected");
		} else {
			bc.failedStep("The changes made on user is modified");

		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param domainName
	 * @param unAssigedUserName
	 * @Description Assign domain to domain user
	 */
	public void AssignUserToDomain(String domainName, String unAssigedUserName) {
		bc.waitForElement(getAssignUserButton());
		getAssignUserButton().waitAndClick(10);

		bc.waitForElement(getSelectDomainname());
		getSelectDomainname().selectFromDropdown().selectByVisibleText(domainName);

		bc.waitForElement(getSelectusertoassignindomain());
		bc.waitTime(5);
		getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText(unAssigedUserName);
		bc.waitForElement(getrightBtndomainuser());
		getrightBtndomainuser().waitAndClick(10);
		getsavedomainuser().waitAndClick(10);
		bc.VerifySuccessMessage("User Mapping Successful");
		bc.stepInfo("Domain user Assiged succesfully");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param domainName
	 * @param AssigedUserName
	 * @Description unassign domain to domain user
	 */
	public void unAssignUserToDomain(String domainName, String AssigedUserName) {
		bc.waitForElement(getAssignUserButton());
		getAssignUserButton().waitAndClick(10);

		bc.waitForElement(getSelectDomainname());
		getSelectDomainname().selectFromDropdown().selectByVisibleText(domainName);

		if (getAssignedDomain(AssigedUserName).isElementAvailable(3)) {
			getSelectuserassignindomain().selectFromDropdown().selectByVisibleText(AssigedUserName);
			getLeftBtndomainuser().waitAndClick(10);
		}

		getsavedomainuser().waitAndClick(10);
		bc.VerifySuccessMessage("User Mapping Successful");
		bc.stepInfo("Domain user unAssiged succesfully");

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param ColumName
	 * @param row
	 * @return
	 * @Description get data from tableuser table
	 */
	public String getTableData(String ColumName, int row) {
		int colum = bc.getIndex(userDetailsTableHeader(), ColumName);
		String data = tableValue(row, colum).getText().trim();
		return data;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param domainName
	 * @param AssigedUserName
	 * @Description verify status of belly band message yes
	 */
	public void verifyUnAssignUserToAssignUserBellyBandYes(String domainName, String AssigedUserName) {
		bc.waitForElement(getAssignUserButton());
		getAssignUserButton().waitAndClick(10);

		bc.waitForElement(getSelectDomainname());
		getSelectDomainname().selectFromDropdown().selectByVisibleText(domainName);

		bc.waitForElement(getSelectusertoassignindomain());
		getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText(AssigedUserName);
		driver.waitForPageToBeReady();
		getrightBtndomainuser().waitAndClick(10);
		if (getAssignedUserName(AssigedUserName).isElementPresent()) {
			bc.passedStep("Selected User is displayed in the Assign user list");
		} else {
			bc.failedStep("verification failed");
		}
		getProjectTab().waitAndClick(10);
		bc.waitForElement(getbellyBandMsg());
		String msg = getbellyBandMsg().getText();
		String expectedText = "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?";

		bc.textCompareEquals(msg, expectedText, "Bully Band message is displayed as expect", "verification failed");
		bc.getYesBtn().waitAndClick(10);
		bc.stepInfo("clicked yes");

		getDomaintab().waitAndClick(10);
		bc.waitForElement(getSelectDomainname());
		getSelectDomainname().selectFromDropdown().selectByVisibleText(domainName);
		if (getAssignedUserName(AssigedUserName).isElementPresent()) {
			bc.passedStep("Selected User is displayed in the Assign user list");
		} else {
			bc.failedStep("verification failed");
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param domainName
	 * @param AssigedUserName
	 * @Description verify belly Band msg if no
	 */
	public void verifyUnAssignUserToAssignUserBellyBandNo(String domainName, String AssigedUserName) {
		bc.waitForElement(getAssignUserButton());
		getAssignUserButton().waitAndClick(10);

		bc.waitForElement(getSelectDomainname());
		getSelectDomainname().selectFromDropdown().selectByVisibleText(domainName);

		bc.waitForElement(getSelectusertoassignindomain());
		getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText(AssigedUserName);
		driver.waitForPageToBeReady();
		getrightBtndomainuser().waitAndClick(10);
		if (getAssignedUserName(AssigedUserName).isElementPresent()) {
			bc.passedStep("Selected User is displayed in the Assign user list");
		} else {
			bc.failedStep("verification failed");
		}
		getProjectTab().waitAndClick(10);
		bc.waitForElement(getbellyBandMsg());
		String msg = getbellyBandMsg().getText();
		String expectedText = "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?";

		bc.textCompareEquals(msg, expectedText, "Bully Band message is displayed as expect", "verification failed");
		getConfirmDelete().waitAndClick(10);
		bc.stepInfo("clicked No");

		getDomaintab().waitAndClick(10);
		bc.waitForElement(getSelectDomainname());
		getSelectDomainname().selectFromDropdown().selectByVisibleText(domainName);
		if (!getAssignedUserName(AssigedUserName).isElementAvailable(1)) {
			bc.passedStep("Selected User is displayed in the Assign user list");
		} else {
			bc.failedStep("verification failed");
		}

	}

	/**
	 * @author Indium-Baskar This method will get Values from user table particular
	 *         column based on index passed.
	 * @param eleName[Name of coulmn from which value needs to be extracted.]
	 * @return
	 */
	public List<String> getTableCoumnValue(String eleName) {
		int index = bc.getIndex(getUserManageTab(), eleName);
		List<String> tableValue = bc.availableListofElements(getTableColumnData(index));
		return tableValue;
	}

	/**
	 * @author Indium-Baskar date: 07/03/2022 Modified date: 07/03/2022
	 * @Description:Methods Search status checkbox
	 */
	public void verifyStatusSearch(String status) throws Exception {
		driver.waitForPageToBeReady();

		boolean flagChecked = getSearchStatusCheck().isElementAvailable(3);
		System.out.println(flagChecked);
		if (flagChecked == false) {
			bc.stepInfo("Search checkbox is unchecked");
		}
		if (flagChecked == true) {
			bc.stepInfo("Search checkbox is checked");
		}
		if (flagChecked == true && status == "false") {
			bc.waitForElement(getSearchIconCheck());
			getSearchIconCheck().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
		}
		if (flagChecked == false && status == "true") {
			bc.waitForElement(getSearchIconCheck());
			getSearchIconCheck().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
			bc.stepInfo("Search checkbox is checked");
		}
	}

	/**
	 * @author Indium-Baskar date: 24/43/2022 Modified date:24/43/2022
	 * @Description:Methods for validating the left icon based on parameter passing
	 * @param elementName   [Element name for checking and unchecking the checkbox]
	 * @param menuName      [leftmenu name passing inside passed step]
	 * @param downloadFalse [False will return icon not present]
	 * @param downloadTrue  [True will return icon present]
	 */

	public void verifyLeftIcon(Element elementName, String menuName, boolean downloadFalse, boolean downloadTrue) {
		driver.waitForPageToBeReady();
		if (downloadFalse == false) {
			if (elementName.isElementAvailable(4)) {
				bc.failedStep(menuName + " tab icon available after unchecking the " + menuName + "");
			} else {
				bc.passedStep(
						menuName + "tab icon not present in left of the menu after unchecking the " + menuName + "");
			}
		} else if (downloadTrue == true) {
			if (elementName.isElementAvailable(4)) {
				bc.passedStep(menuName + "tab icon present in left of the menu after checking the " + menuName + "");
			} else {
				bc.failedStep(menuName + " tab icon not available after checking the " + menuName + "");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 25/04/2022 Modified date: 25/04/2022
	 * @Description:Methods for checking the checkedbox is checked or not
	 * @param elementName
	 * @param elementStatus
	 * @param menuName      [leftmenu name passing inside step info]
	 * @param status        [based on the status we can check and uncheck the
	 *                      checkbox True will check and false will uncheck]
	 */
	public void verifyCheckboxStatusBasedOnCondition(Element elementStatus, Element elementName, String menuName,
			String status) throws Exception {
		driver.waitForPageToBeReady();

		boolean flagChecked = elementStatus.isElementAvailable(3);
		System.out.println(flagChecked);
		if (flagChecked == false) {
			bc.stepInfo(menuName + " checkbox is unchecked");
		}
		if (flagChecked == true) {
			bc.stepInfo(menuName + " checkbox is checked");
		}
		if (flagChecked == true && status == "false") {
			bc.waitForElement(elementName);
			elementName.waitAndClick(10);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
			bc.stepInfo(menuName + " checkbox is unchecked");
		}
		if (flagChecked == false && status == "true") {
			bc.waitForElement(elementName);
			elementName.waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
			bc.stepInfo(menuName + " checkbox is checked");
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param status
	 * @throws Exception
	 * @Description Verify DataSet is checked/unchecked perform
	 */
	public void verifyStatusDataSet(String status) throws Exception {
		driver.waitForPageToBeReady();

		boolean flagChecked = getDataSetStatus().isElementAvailable(3);
		System.out.println(flagChecked);
		if (flagChecked == false) {
			bc.stepInfo("DataSet checkbox is unchecked");
		}
		if (flagChecked == true && status == "false") {
			bc.waitForElement(getDataSet());
			getDataSet().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
		}
		if (flagChecked == false && status == "true") {
			bc.waitForElement(getDataSet());
			getDataSet().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
			bc.stepInfo("DataSet checkbox is checked");
		}

	}

	/**
	 * 
	 * @author Aathith.Senthilkumar
	 * @Description verify user has more than one project availabe
	 */
	public void verifyUserHasMoreThanOneProject() {

		List<WebElement> projects = getRowValues(bc.getIndex(userDetailsTableHeader(), "PROJECT")).FindWebElements();
		int projectAvailable = projects.size();
		System.out.println(projectAvailable);
		if (projectAvailable > 1) {
			bc.stepInfo("this user has more than one project");
		} else {
			bc.failedStep("please use a multi project rmu user");
		}
	}

	/**
	 * @author Indium-Baskar date: 26/04/2022 Modified date: 26/04/2022
	 * @throws AWTException
	 * @Description:Methods for adding sg to user
	 */

	public void addingSGToUser(String defaultName, String newSG) throws InterruptedException, AWTException {
		Select selectSG = new Select(userSelectSecurityGroup().getWebElement());
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		selectSG.selectByVisibleText(defaultName);
		selectSG.selectByVisibleText(newSG);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000); // needed for selecting 2 SGs simultaniously

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
		bc.passedStep("Given access for these SG's " + defaultName + " " + newSG + "  for this user" + " Rmu user");
	}

	/**
	 * @author Brundha
	 * @Description:Methods to apply filter
	 * @param Value
	 */
	public void verifyingFilterOptionInManageUser(String Value) {
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		bc.waitForElement(getUserNameFilter());
		getUserNameFilter().SendKeys(Input.rmu1userName);
		bc.waitForElement(getSelectRoleToFilter());
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(Input.userRole);
		bc.waitForElement(getCalendarOption());
		getCalendarOption().Click();
		bc.waitForElement(getYearDropDown());
		getYearDropDown().selectFromDropdown().selectByVisibleText(Input.filterYear);
		bc.waitForElement(getMonthDropDown());
		getMonthDropDown().selectFromDropdown().selectByVisibleText(Input.filterMonth);
		getSlectDate(Value).waitAndClick(10);
		getFilerApplyBtn().waitAndClick(10);

	}

	/**
	 * @author Brundha
	 * @Description:Methods to validate the applied filter
	 * @param Value
	 */
	public void validateFilterOptionInUserManage(String Header, String ValidatingText) {
		driver.waitForPageToBeReady();
		List<WebElement> Role = getRowValues(bc.getIndex(userDetailsTableHeader(), Header)).FindWebElements();

		for (WebElement RowData : Role) {
			String getRole = RowData.getText();
			System.out.println(getRole);
			bc.compareTextViaContains(getRole, ValidatingText,
					"Filter is applied successfully in manage user page for '" + Header + "'",
					"Filter is not applied successfully");
		}
	}

	/**
	 * @author Indium-Baskar
	 * @Description:Methods to domain project admin user
	 * @param selectProject for selecting project
	 * @param fullName      pass fullname as user
	 * @param roll          account role as login user
	 * @param account       selecting sg
	 * @param status
	 * @param rollStatus
	 */

	public void domainProjectuser(String selectProject, String fullName, String roll, String account, boolean status,
			boolean rollStatus) {
		bc.waitForElement(getAssignUserButton());
		getAssignUserButton().waitAndClick(5);
		bc.waitForElement(getAssignUserProjectDrp_Dwn());
		getAssignUserProjectDrp_Dwn().waitAndClick(5);
		bc.waitForElement(getSelectDropProject(selectProject));
		getSelectDropProject(selectProject).waitAndClick(5);
		boolean projectStatus = getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		if (projectStatus == true) {
			bc.waitForElement(getDomainUserCancelButton());
			getDomainUserCancelButton().waitAndClick(5);
			bc.stepInfo("User already assigned to project");
		}
		if (projectStatus == false && status == false) {
			getUnAssignedDomainUser().selectFromDropdown().selectByVisibleText(fullName);
			getDomainRole().selectFromDropdown().selectByVisibleText(roll);
			if (rollStatus == true) {
				getDomainRole().selectFromDropdown().selectByVisibleText(account);
			}
			getDomainUserRightArrow().waitAndClick(5);
			bc.waitForElement(getsavedomainuser());
			getsavedomainuser().waitAndClick(5);
			bc.stepInfo("User successfullt added into the project");
		}

	}

	/**
	 * @Author Jeevitha
	 */
	public void navigateToUsersPAge() {
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		System.out.println("Navigated to Users Page");
		bc.stepInfo("Navigated to Users Page");

	}

	/**
	 * @Author Jeevitha
	 */
	public void saveButtonOfFunctionTab() {
		if (getSaveEditUser().isElementAvailable(3)) {
			getSaveEditUser().waitAndClick(10);
		}
	}

	/**
	 * @author Mohan
	 * @Description:Methods to give project access to user
	 * @param selectProject for selecting project
	 * @param fullName      pass fullname as user
	 * @param roll          account role as login user
	 * @param account       selecting sg
	 * @param status
	 * @param rollStatus
	 */

	public void ProjectSelectionForUser(String selectProject, String fullName, String roll, String account,
			boolean status, boolean rollStatus) {
		bc.waitForElement(getAssignUserButton());
		getAssignUserButton().waitAndClick(5);
		bc.waitForElement(getProjectTab());
		getProjectTab().waitAndClick(5);
		bc.waitForElement(getAssignUserProjectDrp_Dwn());
		getAssignUserProjectDrp_Dwn().waitAndClick(5);
		bc.waitForElement(getSelectDropProject(selectProject));
		getSelectDropProject(selectProject).waitAndClick(5);
		boolean projectStatus = getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		if (projectStatus == true) {
			bc.waitForElement(getDomainUserCancelButton());
			getDomainUserCancelButton().waitAndClick(5);
			bc.stepInfo("User already assigned to project");
		}
		if (projectStatus == false && status == false) {
			getUnAssignedDomainUser().selectFromDropdown().selectByVisibleText(fullName);
			bc.waitForElement(getDomainRole());
			getDomainRole().selectFromDropdown().selectByVisibleText(roll);
			if (rollStatus == true) {
				getDomainSG().selectFromDropdown().selectByVisibleText(account);
			}
			getDomainUserRightArrow().waitAndClick(5);
			bc.waitForElement(getsavedomainuser());
			getsavedomainuser().waitAndClick(5);
			bc.stepInfo("User successfullt added into the project");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @Description To verify Assigned project is present for all kind of users
	 */
	public void projectIsPresentForAllUsers(String username, String role, String projectName) {

		driver.waitForPageToBeReady();
		bc.waitTime(20);
		bc.waitForElement(getUserNameFilter());
		getUserNameFilter().SendKeys(username);
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		getFilerApplyBtn().Click();

		if (getProjectNameFromUserManagment(projectName).isElementAvailable(5)) {
			bc.passedStep(
					"The User haven't activate himself and " + projectName + " project is present in " + username + "");

		} else if (getUserListNextButton().isElementAvailable(5)) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssgnPaginationCount().Visible();
				}
			}), Input.wait30);
			int count = ((getAssgnPaginationCount().size()) - 2);
			for (int i = 0; i < count; i++) {
				Boolean status = getProjectNameFromUserManagment(projectName).isElementAvailable(5);
				if (status == true) {
					bc.passedStep("The User haven't activate himself and " + projectName + " project is present in "
							+ username + "");
					break;
				} else {
					getUserListNextButton().isElementAvailable(5);
					getUserListNextButton().waitAndClick(5);
					bc.stepInfo("Expected user " + projectName + " not found in the page " + i);
				}
			}

		} else {

			bc.failedStep("The User is not present in the UserManagement Page");
		}

	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param firstName
	 * @param lastName
	 * @param role
	 * @param emailId
	 * @param domain
	 * @param project
	 * @Description add new user in sa credential but it's complete with verify the success message
	 */
	public void addNewUserWithoutVerifySuccesMsg(String firstName, String lastName, String role, String emailId, String domain,
			String project) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddUserBtn().Visible();
			}
		}), Input.wait30);
		getAddUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFirstName().Visible();
			}
		}), Input.wait30);
		getFirstName().SendKeys(firstName);
		getLastName().SendKeys(lastName);
		getSelectRole().selectFromDropdown().selectByVisibleText(role);

		if (role.equalsIgnoreCase("Domain Administrator")) {
			getSelectDomain().isElementAvailable(10);
			getSelectDomain().selectFromDropdown().selectByIndex(1);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEmail().Exists();
			}
		}), Input.wait30);
		getEmail().SendKeys(emailId);
		getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		if (role.equalsIgnoreCase("Project Administrator") || role.equalsIgnoreCase("Review Manager")
				|| role.equalsIgnoreCase("Reviewer")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectProject().Visible();
				}
			}), Input.wait30);
			getSelectProject().Click();
			getSelectProject(project).Click();
		}

		if (role.equalsIgnoreCase("Review Manager") || role.equalsIgnoreCase("Reviewer")) {
			getSecurityDropDown().isElementAvailable(10);
			getSecurityDropDown().selectFromDropdown().selectByVisibleText("Default Security Group");

		}
		getSave().waitAndClick(10);
		bc.stepInfo("add new user details was filed and clicked save button");
	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param role
	 * @Description filter the users with role.
	 */
	public void filterTheRole(String role) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getSelectRoleToFilter());
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		bc.waitForElement(getFilerApplyBtn());
		getFilerApplyBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.stepInfo("applied fileter the for role : "+role);
	}
	
	
	/**
	 * @author Mohan.Venugopal
	 * @description: To change role from Reviewer and Reviewer Manager to PA and Reviewer Manager
	 * @param username
	 * @param role
	 */
	public void editRoleOfAnUser(String username, String role) {

		driver.waitForPageToBeReady();
		bc.waitForElement(getUserNameFilter());
		getUserNameFilter().SendKeys(username);
		bc.waitForElement(getSelectRoleToFilter());
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		bc.waitForElement(getFilerApplyBtn());
		getFilerApplyBtn().waitAndClick(5);

		bc.waitForElement(getEditButtonFromUserManagentPage());
		getEditButtonFromUserManagentPage().waitAndClick(10);

		if (role.contains("Reviewer")) {
			bc.waitForElement(getUserChangeDropDown());
			getUserChangeDropDown().selectFromDropdown().selectByVisibleText("Review Manager");

			bc.waitForElement(getConfirmTab());
			getConfirmTab().waitAndClick(5);

			driver.scrollingToBottomofAPage();
			bc.waitForElement(getSecurityTab());
			getSecurityTab().selectFromDropdown().selectByVisibleText("Default Security Group");

			bc.waitForElement(getFunctionalityButton());
			getFunctionalityButton().waitAndClick(5);

			bc.waitForElement(getSelectFuctionalitiesCheckBox("Manage"));
			getSelectFuctionalitiesCheckBox("Manage").waitAndClick(5);

			bc.waitForElement(getSaveButtonInFuctionalitiesTab());
			getSaveButtonInFuctionalitiesTab().waitAndClick(5);

			bc.VerifySuccessMessage("User profile was successfully modified");
			bc.passedStep("Manage is checked and enabled and Save button is clicked");

		} else if (role.contains("Review Manager")) {
			driver.waitForPageToBeReady();
			bc.waitForElement(getUserChangeDropDown());
			getUserChangeDropDown().selectFromDropdown().selectByVisibleText("Project Administrator");

			bc.waitForElement(getConfirmTab());
			getConfirmTab().waitAndClick(5);

			bc.waitForElement(getFunctionalityButton());
			getFunctionalityButton().waitAndClick(5);

			bc.waitForElement(getSelectFuctionalitiesCheckBox("Manage"));
			getSelectFuctionalitiesCheckBox("Manage").waitAndClick(5);
			getSelectFuctionalitiesCheckBox("Manage").waitAndClick(5);

			bc.waitForElement(getSaveButtonInFuctionalitiesTab());
			getSaveButtonInFuctionalitiesTab().waitAndClick(5);

			bc.VerifySuccessMessage("User profile was successfully modified");
			bc.passedStep("Manage is checked and enabled and Save button is clicked");

		} else {
			bc.failedStep("User is unable to edit the details of the user");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To change role from RMU,PA and Reviewer to reviewer and PA
	 * @param username
	 * @param role
	 */
	public void editRoleForRMUANdPAUsers(String username, String role) {
		driver.waitForPageToBeReady();
		bc.waitTime(20);
		bc.waitForElement(getUserNameFilter());
		getUserNameFilter().SendKeys(username);
		bc.waitForElement(getSelectRoleToFilter());
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		bc.waitForElement(getFilerApplyBtn());
		getFilerApplyBtn().waitAndClick(5);

		bc.waitForElement(getEditButtonFromUserManagentPage());
		getEditButtonFromUserManagentPage().waitAndClick(10);

		if (role.contains("Review Manager") || role.contains("Project Administrator")) {
			bc.waitForElement(getUserChangeDropDown());
			getUserChangeDropDown().Click();
			getUserChangeDropDown().selectFromDropdown().selectByVisibleText("Reviewer");
			bc.waitForElement(getConfirmTab());
			getConfirmTab().waitAndClick(5);
			
			driver.scrollingToBottomofAPage();
			bc.waitForElement(getSecurityTab());
			getSecurityTab().selectFromDropdown().selectByVisibleText("Default Security Group");

			bc.waitForElement(getFunctionalityTab());
			getFunctionalityTab().waitAndClick(5);

			String checkBoxStatus = getSelectFuctionalitiesCheckBox("Manage").GetAttribute("style");
			System.out.println(checkBoxStatus);

			if (checkBoxStatus.contains("grey")) {

				bc.passedStep("Manage is unchecked and disable and Save button is clicked");
				bc.waitForElement(getSaveButtonInFuctionalitiesTab());
				getSaveButtonInFuctionalitiesTab().waitAndClick(5);

				bc.VerifySuccessMessage("User profile was successfully modified");
			} else {
				bc.failedStep("Manage is checked and enabled for the user");
			}
		}
			else if (role.contains("Reviewer")) {
				bc.waitForElement(getUserChangeDropDown());
				getUserChangeDropDown().Click();
				getUserChangeDropDown().selectFromDropdown().selectByVisibleText("Project Administrator");
				bc.waitForElement(getConfirmTab());
				getConfirmTab().waitAndClick(5);
				
				bc.waitForElement(getFunctionalityTab());
				getFunctionalityTab().waitAndClick(5);
				
				bc.waitForElement(getSelectFuctionalitiesCheckBox("Manage"));
				getSelectFuctionalitiesCheckBox("Manage").waitAndClick(5);

				bc.waitForElement(getSaveButtonInFuctionalitiesTab());
				getSaveButtonInFuctionalitiesTab().waitAndClick(5);

				bc.VerifySuccessMessage("User profile was successfully modified");
				bc.passedStep("Manage is checked and enabled and Save button is clicked");
			}
		 else {
			bc.failedStep("User is unable to edit the details of the user");
		}
	}
	
	/**
	 * @author Mohan.Venugopal
	 * @description: To change role from PA to reviewer manager and Reviewer
	 * @param username
	 * @param role
	 */
	public void editRoleFromPAToRMU(String username, String role) {
		driver.waitForPageToBeReady();
		bc.waitTime(20);
		bc.waitForElement(getUserNameFilter());
		getUserNameFilter().SendKeys(username);
		bc.waitForElement(getSelectRoleToFilter());
		getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		bc.waitForElement(getFilerApplyBtn());
		getFilerApplyBtn().waitAndClick(5);

		bc.waitForElement(getEditButtonFromUserManagentPage());
		getEditButtonFromUserManagentPage().waitAndClick(10);

		if (role.contains("Project Administrator")) {
			bc.waitForElement(getUserChangeDropDown());
			getUserChangeDropDown().selectFromDropdown().selectByVisibleText("Review Manager");
			bc.waitForElement(getConfirmTab());
			getConfirmTab().waitAndClick(5);

			bc.waitForElement(getSecurityTab());
			getSecurityTab().selectFromDropdown().selectByVisibleText("Default Security Group");

			bc.waitForElement(getFunctionalityTab());
			getFunctionalityTab().waitAndClick(5);

			bc.waitForElement(getSelectFuctionalitiesCheckBox("Manage"));
			getSelectFuctionalitiesCheckBox("Manage").waitAndClick(5);
			getSelectFuctionalitiesCheckBox("Manage").waitAndClick(5);

			bc.waitForElement(getSaveButtonInFuctionalitiesTab());
			getSaveButtonInFuctionalitiesTab().waitAndClick(5);

			bc.VerifySuccessMessage("User profile was successfully modified");
			bc.passedStep("Manage is checked and enabled for the user");

		} else if (role.contains("Reviewer")) {
			bc.waitForElement(getUserChangeDropDown());
			getUserChangeDropDown().selectFromDropdown().selectByVisibleText("Project Administrator");
			bc.waitForElement(getConfirmTab());
			getConfirmTab().waitAndClick(5);

			bc.waitForElement(getSecurityTab());
			getSecurityTab().selectFromDropdown().selectByVisibleText("Default Security Group");

			bc.waitForElement(getFunctionalityTab());
			getFunctionalityTab().waitAndClick(5);

			bc.waitForElement(getSelectFuctionalitiesCheckBox("Manage"));
			getSelectFuctionalitiesCheckBox("Manage").waitAndClick(5);
			getSelectFuctionalitiesCheckBox("Manage").waitAndClick(5);

			bc.waitForElement(getSaveButtonInFuctionalitiesTab());
			getSaveButtonInFuctionalitiesTab().waitAndClick(5);

			bc.VerifySuccessMessage("User profile was successfully modified");
			bc.passedStep("Manage is checked and enabled for the user");

		} else {
			bc.failedStep("User is unable to edit the details of the user");
		}
	}
}

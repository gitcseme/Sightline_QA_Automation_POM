package pageFactory;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
		return driver.FindElementByXPath(
				"//table[@id='dtUserList']//tr//td//..//a[contains(text(),'Edit')]");
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
				driver.FindElementByXPath("//table[@id='dtUserList']//tr[" + i + "]/td[8]/a[contains(text(),'Delete')]")
						.Click();
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
		getSave().Click();
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
		getSelectUserToEdit(project).waitAndClick(5);
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
		
	    boolean flagChecked=getIngestionStatus().isElementAvailable(3);
	    System.out.println(flagChecked);
	    if (flagChecked==false) {
	    	bc.stepInfo("Ingestion checkbox is unchecked");
		}
	    if (flagChecked==true&& status=="false") {
	    	bc.waitForElement(getIngestion());
			getIngestion().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
		}
	    if (flagChecked==false&& status=="true") {
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
		
	    boolean flagChecked=getCategorizeStatusCheck().isElementAvailable(3);
	    System.out.println(flagChecked);
	    if (flagChecked==false) {
	    	bc.stepInfo("Ingestion checkbox is unchecked");
		}
	    if (flagChecked==true&& status=="false") {
	    	bc.waitForElement(getCategorizeCheck());
			getCategorizeCheck().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
		}
	    if (flagChecked==false&& status=="true") {
	    	bc.waitForElement(getIngestion());
	    	getCategorizeCheck().waitAndClick(5);
			bc.waitForElement(getSaveEditUser());
			getSaveEditUser().waitAndClick(10);
			bc.stepInfo("Ingestion checkbox is checked");
		}
		
	}
	

}

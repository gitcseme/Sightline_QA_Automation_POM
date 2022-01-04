package testScriptsRegression;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions redact;
	SecurityGroupsPage security;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  52167 - Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction in context of assignment.
	 * Description : To Verify the tool tip displayed 'Code same as last' when document having rectangle redaction in context of assignment
	 */
	////@Test(alwaysRun = true,groups={"regression"},priority = 1)
	public void verifyToolTipDisplayedWhenDocumentHavingRedaction() throws Exception {		
		baseClass=new BaseClass(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-52167");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction in context of assignment ####");
		
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchText);
		
		baseClass.stepInfo("Bulk assign with new assignment");
		sessionSearch.bulkAssignWithNewAssignment();
	
		baseClass.stepInfo("Create assignment by bulk assign operationfrom Session search");
		agnmt.createAssignmentByBulkAssignOperation(assignmentName, Input.codeFormName);
		
		baseClass.stepInfo("Edit assignment by assignment name");
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		
		baseClass.stepInfo("Enabling redactions toogle in assignment page");
		agnmt.enableToogleToEnableRedactions(true);
		
		baseClass.stepInfo("Select assignment to view in Doc view");
		agnmt.selectAssignmentToViewinDocview(assignmentName);
		
		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();
		
		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15,Input.defaultRedactionTag);
		
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);
		
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  52166 - Verify that tool tip displayed on mouse over of 'Code same as last' when document having all page redaction
	 * Description : To Verify the tool tip displayed 'Code same as last' when document having rectangle redaction.
	 */
	////@Test(alwaysRun = true,groups={"regression"},priority = 2)
	public void verifyToolTipMessageDisplayedWhenDocHavingRedaction() throws Exception {		
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52166");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction by Doc view basic session search ####");
		
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();
		
		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15,Input.defaultRedactionTag);
		
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);
		
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  52164 - Verify that tool tip displayed on mouse over of 'Code same as last' when document having all page redaction
	 * Description : To Verify the tool tip displayed 'Code same as last' when document having this page redaction.
	 */
	////@Test(alwaysRun = true,groups={"regression"},priority = 3)
	public void verifyToolTipMsgDisplayedWhenDocHavingThisPageRedaction() throws Exception {		
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52166");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having this redaction by Doc view basic session search ####");
		
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();
		
		redact = new DocViewRedactions(driver);
		
		baseClass.stepInfo("Set this page reduct in doc");
		redact.performThisPageRedaction(Input.defaultRedactionTag);
		
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);
		
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  52163 - Verify that tool tip displayed on mouse over of 'Code same as last' when document having all page redaction
	 * Description : To Verify the tool tip displayed 'Code same as last' when document having multi page redaction.
	 */
	////@Test(alwaysRun = true,groups={"regression"},priority = 4)
	public void verifyToolTipMsgDisplayedWhenDocHavingMultiRedaction() throws Exception {		
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52163");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having multiple page redaction by Doc view basic session search ####");
		
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();
		
		redact = new DocViewRedactions(driver);
		
		baseClass.stepInfo("Set multi page reduct in doc");
		redact.performAllPagesMultiPageRedaction(Input.defaultRedactionTag);
		
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);
		
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		
	}
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  52151 - Verify that when applied redaction tag is unassigned then blank row should be displayed in edit redaction pop up on doc view
	 * Description : To Verify that blank row should be displayed on the drop down when redaction tag is unassigned from security group.
	 */
	////@Test(alwaysRun = true,groups={"regression"},priority = 5)
	public void verifyBlankRowDisplayedAppliedRedactionIsUnTaggedFromSecurityGroup() throws Exception {		
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52151 DocView Sprint 03");
		utility = new Utility(driver);
		String redactnam = "ATest"+utility.dynamicNameAppender();
		final String redactname = redactnam;
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that when applied redaction tag is unassigned then blank row should be displayed in edit redaction pop up on doc view ####");
		
		docView = new DocViewPage(driver);
		RedactionPage redactTag = new RedactionPage(driver);
		
		baseClass.stepInfo("Add redaction tag");
		redactTag.AddRedaction(redactname,"RMU");
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		
		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();
		
		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15,redactname);
		
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		
		security = new SecurityGroupsPage(driver);
		
		baseClass.stepInfo("Un tag redaction from security group");
		security.unTagFromRedatctionTags(redactname);
		
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.addDocsMetCriteriaToActionBoard();	
		
		redact = new DocViewRedactions(driver);
		
		baseClass.stepInfo("Unredact on performed redaction");
		redact.clickOnLastPerformedRedactionOnCurrentDoc();
		
		baseClass.stepInfo("Verify selected option from untag select dropdown is blank");
		redact.verifySelectedOptionFromUnRedactionDropdownIsBlank();
		
	}
	
	 /**
		 * Author : Gopinath date: NA Modified date: NA Modified by: NA
		 * Test Case Id: RPMXCON-52152 :: Verify that when adding redaction in audio document blank row should not be displayed.
		 * Description : Verify blank row should not displayed while redaction.
		 */

		////@Test(groups = { "regression" }, priority = 6)
		public void verifyBlankRowNotDisplayedFromDefaultRedactionDropdown() throws Exception {
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
			baseClass = new BaseClass(driver);
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			
			baseClass.stepInfo("Test case Id: RPMXCON-52152");
			baseClass.stepInfo("#### Verify that when adding redaction in audio document blank row should not be displayed. ####");
			
			SessionSearch sessionsearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Audio search for audio document in advanced search");
			sessionsearch.audioSearch("Morning", Input.language);
			
			baseClass.stepInfo("View searched for audio docs in Doc view");
			sessionsearch.ViewInDocView();
			
			baseClass.stepInfo("Click on add redaction for audio document");
			docViewRedact.clickOnAddRedactionForAudioDocument();
			
			baseClass.stepInfo("Verify redaction dropdown for audio document is not blank");
			docViewRedact.verifyWeatherAudioRedactionDropdownIsBlank(false);
		}
		
		
		 /**
		 * Author : Gopinath date: NA Modified date: NA Modified by: NA
		 * Test Case Id: RPMXCON-52153 :: Verify that when applied redaction tag is unassigned then blank row should be displayed in edit redaction pop up on audio doc view.
		 * Description : Verify blank row should displayed while selected redaction tag uassigned from security groups.
		 */

		////@Test(groups = { "regression" }, priority = 7)
		public void verifyBlankRowDisplayedWhenUnAssginedRedactionTag() throws Exception {
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
			baseClass = new BaseClass(driver);
			utility = new Utility(driver);
			
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			String redactnam = "ATest"+utility.dynamicNameAppender();
			final String redactname = redactnam;
			baseClass.stepInfo("Test case Id: RPMXCON-52153- Sprint 3-DocView");
			baseClass.stepInfo("#### Verify that when applied redaction tag is unassigned then blank row should be displayed in edit redaction pop up on audio doc view. ####");
			RedactionPage redactTag = new RedactionPage(driver);
			
			baseClass.stepInfo("Add redaction tag");
			redactTag.AddRedaction(redactname,"RMU");
			
			SessionSearch sessionsearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Audio search for audio document in advanced search");
			sessionsearch.audioSearch("Morning", Input.language);
			
			baseClass.stepInfo("View searched for audio docs in Doc view");
			sessionsearch.ViewInDocView();
			
			baseClass.stepInfo("Delete all applied redactions");
			docViewRedact.deleteAllAppliedRedactions();

			baseClass.stepInfo("Click on add redaction for audio document");
			docViewRedact.clickOnAddRedactionForAudioDocument();
			
			baseClass.stepInfo("Add audio redaction");
			docViewRedact.addAudioRedaction(Input.startTime, Input.endTime, redactname);
			
			loginPage = new LoginPage(driver);
			loginPage.logout();
			
			baseClass.stepInfo("Login with project administrator");
			loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
			Reporter.log("Logged in as User: " + Input.pa2userName);
			
			security = new SecurityGroupsPage(driver);
			
			baseClass.stepInfo("Un tag redaction from security group");
			security.unTagFromRedatctionTags(redactname);
			
			loginPage = new LoginPage(driver);
			loginPage.logout();
			
			baseClass.stepInfo("Login with RMU");
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			
			sessionsearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Audio search for audio document in advanced search");
			sessionsearch.audioSearch("Morning", Input.language);
			
			baseClass.stepInfo("View searched for audio docs in Doc view");
			sessionsearch.ViewInDocView();
			
			baseClass.stepInfo("Edit applied redaction");
			docViewRedact.editAppliedRedaction();
			
			baseClass.stepInfo("Verify redaction dropdown for audio document is blank");
			docViewRedact.verifyWeatherAudioRedactionDropdownIsBlank(true);
		}
		
		/**
		 * Author : Krishna Created date: NA Modified date: NA Modified by:Krishna
		 * TestCase id :  52169 & 52168 - Verify the tool tip displayed on mouse over of 'Code same as last' when document having page Redaction
		 * Description : Page Range Redaction in context of assignment
		 */
		////@Test(alwaysRun = true,groups={"regression"},priority = 8)
		public void verifyToolTip() throws Exception {
			Robot robot = new Robot();
			baseClass=new BaseClass(driver);
			String assignmentName = "assignment" + Utility.dynamicNameAppender();
			baseClass.stepInfo("Test case Id: RPMXCON-52169");
			baseClass.stepInfo("Test case Id: RPMXCON-52168");
			utility = new Utility(driver);
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo("#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction in context of assignment ####");	
			docView = new DocViewPage(driver);
			agnmt = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
	// searching for docs and bulk assigning		
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);		
			baseClass.stepInfo("Bulk assign with new assignment");
			sessionSearch.bulkAssignWithNewAssignment();		
			baseClass.stepInfo("Create assignment by bulk assign operationfrom Session search");
			agnmt.createAssignmentByBulkAssignOperation(assignmentName, Input.codeFormName);
	// selecting assignment to view in DocView		
			baseClass.stepInfo("Edit assignment by assignment name");
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);		
			baseClass.stepInfo("Enabling redactions toogle in assignment page");
			agnmt.enableToogleToEnableRedactions(true);		
			baseClass.stepInfo("Select assignment to view in Doc view");
			agnmt.selectAssignmentToViewinDocview(assignmentName);
	// Clicking redaction Icon and selecting page range		
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			docViewRedact.clickingRedactionIcon();
			Actions actions = new Actions(driver.getWebDriver());
			actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
			actions.click().build().perform();
			docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);				
			baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
			docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);		
			baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
			docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
	// Clicking redaction Icon and selecting this page redaction
			actions.moveToElement(docViewRedact.thisPageRedaction().getWebElement()).click();
			actions.build().perform();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
			docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);			
			baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
			docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);			
		}
		
		/**
		 * Author : Krishna Created date: NA Modified date: NA Modified by:Krishna
		 * TestCase id :  52165 - Verify the tool tip displayed on mouse over of 'Code same as last' when document having page Redaction
		 * Description : In context of SG for page range Redaction
		 */
		////@Test(alwaysRun = true,groups={"regression"},priority = 9)
		public void verifyToolTipForSG() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-52165");
			utility = new Utility(driver);
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo("#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction in context of assignment ####");	
			docView = new DocViewPage(driver);
			agnmt = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);		
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.ViewInDocView();
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
            docViewRedact.clickingRedactionIcon();
			Actions actions = new Actions(driver.getWebDriver());
			actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
			actions.click().build().perform();
			docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);	
			baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
			docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);		
			baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
			docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);			
		}
		
		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by: NA Testcase
		 * Testcase id : 51619 - Verify that the undocked windows should be docked to parent window when the user refreshes the DocView page.
		 * Description : Verify that the undocked windows should be docked to parent window when the user refreshes the DocView page.
		 * @throws AWTException
		 */
		////@Test(groups = { "regression" }, priority = 10)
		public void verifyAssignSingleDocumentOnDocList() throws InterruptedException {
			baseClass=new BaseClass(driver);
			
			baseClass.stepInfo("Test case Id: RPMXCON-51619 DocView/MetaData Sprint 06");
			
			utility= new Utility(driver); 
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo("#### Verify that the undocked windows should be docked to parent window when the user refreshes the DocView page. ####");
			manageAssignment = new ManageAssignment(driver);
			int row = manageAssignment.getDocViewRowNum();
			
			baseClass.stepInfo("Selecting assignment from assignment group table");
			manageAssignment.selectRow(row);
			
			baseClass.stepInfo("Navigate to doc view page from assignments page");
			manageAssignment.navigateToDocView(row);
			
			baseClass.stepInfo("Handling new windows in docview page");
			docViewMetaDataPage.verifyingTheNewWindowsInDocView();

		}
		
		/**
		 * @Author : Gopinath Created date: NA Modified date: NA Modified by: NA.
		 * ////@Testcase_Id : 47028 - Verify that on click of Save from 'Multi-Page Redactions' pop up, all pages other than pages which are excluded should be redacted successfully.
		 * @Description : Verify that on click of Save from 'Multi-Page Redactions' pop up, all pages other than pages which are excluded should be redacted successfully.
		 */
		////@Test(groups = { "regression" }, priority = 11)
		public void verifyMultiplePageRedactionsByExcludePages() throws InterruptedException {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-47028 DocView Sprint 06");
			
			utility= new Utility(driver); 
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo("#### Verify that on click of Save from 'Multi-Page Redactions' pop up, all pages other than pages which are excluded should be redacted successfully. ####");
			loginPage = new LoginPage(driver);
			loginPage.logout();
			
			baseClass.stepInfo("Login with project administrator");
			loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
			Reporter.log("Logged in as User: " + Input.pa2userName);
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate to session search");
			sessionSearch.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic content search");
			sessionSearch.basicContentSearch(Input.paginationDocId);
			
			baseClass.stepInfo("Bulk Release");
			sessionSearch.bulkRelease(Input.securityGroup);
			
			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			
			sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate to session search");
			sessionSearch.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic content search");
			sessionSearch.basicContentSearch(Input.paginationDocId);
			sessionSearch.addDocsMetCriteriaToActionBoard();

			redact = new DocViewRedactions(driver);
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Perform exclude Multiple Page Redaction by page range");
			redact.performMultiplePageRedactionForExclude(Input.defaultRedactionTag,Input.pageRange);
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			 
			baseClass.stepInfo("Perform exclude Multiple Page Redaction by page number");
			redact.performMultiplePageRedactionForExclude(Input.defaultRedactionTag,Input.pageNumber);
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Perform exclude Multiple Page Redaction by page number and page range");
			redact.performMultiplePageRedactionForExclude(Input.defaultRedactionTag,Input.pageNumber+","+Input.pageRange+","+Input.documentIdNum);
		}
	
		/**
		 * @Author : Gopinath Created date: NA Modified date: NA Modified by: NA.
		 * ////@Testcase_Id : 47029 - Verify that pages which are included for multi page redaction should be redacted successfully with changed redaction tag from what is presented in pop up.
		 * @Description : Verify that pages which are included for multi page redaction should be redacted successfully with changed redaction tag from what is presented in pop up.
		 */
		////@Test(groups = { "regression" }, priority = 12)
		public void verifyMultiplePageRedactionsIncludeWithDiffRedTag() throws InterruptedException {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-47029 DocView Sprint 06");
			String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
			utility= new Utility(driver); 
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo("#### Verify that pages which are included for multi page redaction should be redacted successfully with changed redaction tag from what is presented in pop up. ####");
			loginPage = new LoginPage(driver);
			loginPage.logout();
			
			baseClass.stepInfo("Login with project administrator");
			loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
			Reporter.log("Logged in as User: " + Input.pa2userName);
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate to session search");
			sessionSearch.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic content search");
			sessionSearch.basicContentSearch(Input.paginationDocId);
			
			baseClass.stepInfo("Bulk Release");
			sessionSearch.bulkRelease(Input.securityGroup);
			
			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			
			RedactionPage redactionpage=new RedactionPage(driver);
			redactionpage.navigateToRedactionsPageURL();
			redactionpage.manageRedactionTagsPage(Redactiontag1);
			
			sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate to session search");
			sessionSearch.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic content search");
			sessionSearch.basicContentSearch(Input.paginationDocId);
			sessionSearch.addDocsMetCriteriaToActionBoard();

			redact = new DocViewRedactions(driver);
			
			baseClass.stepInfo("Perform exclude Multiple Page Redaction by page range");
			redact.performMultiplePageRedactionForInclude(Redactiontag1,Input.pageRange);
			
		}
		/**
		 * @author Brundha created on:NA modified by:NA
		 * ////@Testcase_Id : RPMXCON-51903 
		 * @Description : Verify that after impersonation icon should be displayed on doc view header for expand/collapse
		 */
		////@Test(groups = { "regression" }, priority = 13)
		public void verifyingReviewManagerChangesInDocView () throws Exception {
			UtilityLog.info(Input.prodPath);
			baseClass.stepInfo("Test case Id: RPMXCON-51903 -Production Sprint 07");
			baseClass.stepInfo("#### Verify that after impersonation icon should be displayed on doc view header for expand/collapse. ####");
			baseClass.stepInfo("Impersnated from RMU to Reviewer");
			baseClass.impersonateRMUtoReviewers();
		
			SessionSearch sessionSearch = new SessionSearch(driver);
			baseClass.stepInfo("Navigate to session search");
			sessionSearch.navigateToSessionSearchPageURL();
			
			sessionSearch.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("navigating to docview page"); 
			sessionSearch.addDocsMetCriteriaToActionBoard();
			
			baseClass.stepInfo("Refreshing the page");
	 		driver.Navigate().refresh();
	 		
	 		baseClass.stepInfo("impersnated from reviewer to RMU");
	 		baseClass.impersonationReviewertoRMU();
	 		
		}
		
		/**
		 * @author Gopinath created on:NA modified by:NA.
		 * ////@Testcase ID : RPMXCON-52206 : Verify that Producing a TIFF with reviewer remarks working properly and not eliminate any text characters in the produced document.
		 * @Description : Verify that Producing a TIFF with reviewer remarks working properly and not eliminate any text characters in the produced document.
		 */
		////@Test(groups = { "regression" }, priority = 13)
		public void verifyProducingTIFFWithReviewerRemarksWorkingProperly() throws Exception {
			baseClass=new BaseClass(driver);
			UtilityLog.info(Input.prodPath);
			baseClass.stepInfo("RPMXCON-52206 Production-sprint:07");
			baseClass.stepInfo("####  Verify that Producing a TIFF with reviewer remarks working properly and not eliminate any text characters in the produced document. ####");
			
			String tagname = Input.randomText + Utility.dynamicNameAppender();
			String foldername = Input.randomText + Utility.dynamicNameAppender();
			String productionname = Input.randomText+ Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			String remark = Input.randomText + Utility.dynamicNameAppender();
			// Pre-requisites
			// create tag
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			
			baseClass.stepInfo("Navigate To Tags And Folder Page");
			tagsAndFolderPage.navigateToTagsAndFolderPage();
			
			baseClass.stepInfo("Create Tag with Classification");
			tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
			
			baseClass.stepInfo("Create Folder");
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

			// search for the created folder and check the pure hit count
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.addDocsMetCriteriaToActionBoard();
			
			//Add Remark
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Add Remark To Non Audio Document");
			docView.addRemarkToNonAudioDocument(12, 25,remark);
			
			baseClass.stepInfo("Verify Remark Is Added");
			docView.verifyRemarkIsAdded(remark);
			
			search = new SessionSearch(driver);
			
			search.bulkFolderExisting(foldername);
			
			// create production and fill dat field and verify specify controls
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			
			ProductionPage page = new ProductionPage(driver);
			
			baseClass.stepInfo("Select default security group");
			page.selectingDefaultSecurityGroup();
			
			baseClass.stepInfo("Navigate To Production Page");
			page.navigateToProductionPage();
			
			baseClass.stepInfo("Add new production");
			page.addANewProduction(productionname);
			
			baseClass.stepInfo("Filling DAT Section");
			page.fillingDATSection();
			
			baseClass.stepInfo("Filling Native Section");
			page.fillingNativeSection();
			
			baseClass.stepInfo("Filling TIFF Section with Burn Redaction");
			page.fillingTIFFSectionwithBurnRedaction(tagname);
			
			baseClass.stepInfo("Filling text section");
			page.fillingTextSection();
			
			baseClass.stepInfo("Navigate to next section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Numbering And Sorting Page");
			page.fillingNumberingAndSortingPage(prefixID, suffixID);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Document Selection Page");
			page.fillingDocumentSelectionPage(foldername);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Priv Guard Page");
			page.fillingPrivGuardPage();
			
			baseClass.stepInfo("Filling Production Location Page And Passing Text");
	 		page.fillingProductionLocationPageAndPassingText(productionname);
	 		
	 		baseClass.stepInfo("Navigate To Next Section");
	 		page.navigateToNextSection();
	 		
	 		baseClass.stepInfo("Filling Summary And Preview");
	 		page.fillingSummaryAndPreview();
	 		
	 		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
	 		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		}
		
		
		/**
		 * @author Gopinath created on:NA modified by:NA.
		 * ////@Testcase ID : RPMXCON-52205 :Verify that Producing a PDF with reviewer remarks working properly and not eliminate any text characters in the produced document.
		 * @Description : Verify that Producing a PDF with reviewer remarks working properly and not eliminate any text characters in the produced document.
		 */
		////@Test(groups = { "regression" }, priority = 14)
		public void verifyProducingPDFWithReviewerRemarksWorkingProperly() throws Exception {
			baseClass=new BaseClass(driver);
			UtilityLog.info(Input.prodPath);
			baseClass.stepInfo("RPMXCON-52205 Production-sprint:07");
			baseClass.stepInfo("#### Verify that Producing a PDF with reviewer remarks working properly and not eliminate any text characters in the produced document. ####");
			
			String tagname = Input.randomText + Utility.dynamicNameAppender();
			String foldername = Input.randomText + Utility.dynamicNameAppender();
			String productionname = Input.randomText+ Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			String remark = Input.randomText + Utility.dynamicNameAppender();
			// Pre-requisites
			// create tag
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			
			baseClass.stepInfo("Navigate To Tags And Folder Page");
			tagsAndFolderPage.navigateToTagsAndFolderPage();
			
			baseClass.stepInfo("Create Tag with Classification");
			tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
			
			baseClass.stepInfo("Create Folder");
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

			// search for the created folder and check the pure hit count
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.addDocsMetCriteriaToActionBoard();
			
			//Add Remark
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Add Remark To Non Audio Document");
			docView.addRemarkToNonAudioDocument(12, 25,remark);
			
			baseClass.stepInfo("Verify Remark Is Added");
			docView.verifyRemarkIsAdded(remark);
			
			search = new SessionSearch(driver);
			
			search.bulkFolderExisting(foldername);
			
			// create production and fill dat field and verify specify controls
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			
			ProductionPage page = new ProductionPage(driver);
			
			baseClass.stepInfo("Select default security group");
			page.selectingDefaultSecurityGroup();
			
			baseClass.stepInfo("Navigate To Production Page");
			page.navigateToProductionPage();
			
			baseClass.stepInfo("Add new production");
			page.addANewProduction(productionname);
			
			baseClass.stepInfo("Filling DAT Section");
			page.fillingDATSection();
			
			baseClass.stepInfo("Filling Native Section");
			page.fillingNativeSection();
			
			baseClass.stepInfo("Filling TIFF Section with Burn Redaction");
			page.fillingPDFSectionwithBurnRedaction(tagname);
			
			baseClass.stepInfo("Filling text section");
			page.fillingTextSection();
			
			baseClass.stepInfo("Navigate to next section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Numbering And Sorting Page");
			page.fillingNumberingAndSortingPage(prefixID, suffixID);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Document Selection Page");
			page.fillingDocumentSelectionPage(foldername);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Priv Guard Page");
			page.fillingPrivGuardPage();
			
			baseClass.stepInfo("Filling Production Location Page And Passing Text");
	 		page.fillingProductionLocationPageAndPassingText(productionname);
	 		
	 		baseClass.stepInfo("Navigate To Next Section");
	 		page.navigateToNextSection();
	 		
	 		baseClass.stepInfo("Filling Summary And Preview");
	 		page.fillingSummaryAndPreview();
	 		
	 		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
	 		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		}
		
		/**
		 * @author Gopinath
		 * @throws InterruptedException 
		 * ////@Testcase_Id : RPMXCON-51855 : Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document.
		 * @description : Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document after applying the coding stamp from coding form child window.
		 */

		////@Test(groups = { "regression" }, priority = 15)
		public void verifyPersistentHitsPanelDisplayedNavigatedToNextDoc() throws InterruptedException {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51855 Production-sprint:07");
			baseClass.stepInfo("#### Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document after applying the coding stamp from coding form child window. ####");
			String assignmentName = Input.randomText + Utility.dynamicNameAppender();
			
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
			
			baseClass.stepInfo("Click on persistant hit eye icon.");
			docView.clickOnPersistantHitEyeIcon();
			
			baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
			docView.verifyPersistentHitsPanelDisplayed();
			
			baseClass.stepInfo("Editing Coding Form And Entering To Next Document");
			docView.editingCodingFormAndEnteringToNextDocument(Input.randomText);
			
			baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
			docView.verifyPersistentHitsPanelDisplayed();
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Delete Assgnmnt Using Pagination");
			assgnPage.deleteAssignment(assignmentName);
		}
		
		/**
		 * @author Gopinath
		 * @throws InterruptedException 
		 * ////@Testcase_Id : RPMXCON-51761 : Verify that toggle to not to view the 0 hit terms does not show when there are no terms at all in the panel.
		 * @description : Verify that toggle to not to view the 0 hit terms does not show when there are no terms at all in the panel (when there are no search terms or keywords that apply to this document).
		 */

		////@Test(groups = { "regression" }, priority = 16)
		public void verify0HitTermsToogleNotDisplayedToAdvancedWorkProduct() throws InterruptedException {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51761 Production-sprint:07");
			baseClass.stepInfo("#### Verify that toggle to not to view the 0 hit terms does not show when there are no terms at all in the panel (when there are no search terms or keywords that apply to this document). ####");
			String folderName = Input.randomText + Utility.dynamicNameAppender();
			String assignmentName = Input.randomText + Utility.dynamicNameAppender();
			TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
			
			baseClass.stepInfo("Navigate To Tags And Folder Page");
			tagPage.navigateToTagsAndFolderPage();
			
			baseClass.stepInfo("Create Folder");
			tagPage.CreateFolder(folderName, Input.securityGroup);
			
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.searchString1);
			
			baseClass.stepInfo("Bulk Folder Existing");
			search.bulkFolderExisting(folderName);
			
			baseClass.stepInfo("Log out");
			loginPage.logout();
			
			baseClass.stepInfo("Login To SightLine");
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			
			baseClass.stepInfo("Advance Search By Folder");
			search.advanceSearchByFolder(folderName);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.ViewInDocViews();
			
			docView = new DocViewPage(driver) ;
			
			baseClass.stepInfo("Click On Persistant Hit Eye Icon");
			docView.clickOnPersistantHitEyeIcon();
			
			baseClass.stepInfo("Persistent Hits Toogle Not Displayed");
			docView.verifyPersistentHitsToogleNotDisplayed();
		
			baseClass.stepInfo("Log out");
			loginPage.logout();
			
			baseClass.stepInfo("Login To SightLine");
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			
			AssignmentsPage assgnPage = new AssignmentsPage(driver);
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Create Assignment");
			assgnPage.createAssignment(assignmentName, Input.codeFormName);
			
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
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Click on persistant hit eye icon.");
			docView.clickOnPersistantHitEyeIcon();
			
			baseClass.stepInfo("Persistent Hits Toogle Not Displayed");
			docView.verifyPersistentHitsToogleNotDisplayed();
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Delete Assgnmnt Using Pagination");
			assgnPage.deleteAssignment(assignmentName);
		}
		
		
		/**
		 * @author Gopinath
		 * @throws InterruptedException 
		 * ////@Testcase_Id : RPMXCON-51760 : Verify when navigated to doc view with two different ad-hoc results with term/phrase are highlighted which are exists in the document.
		 * @description : Verify when navigated to doc view with two different ad-hoc results with term/phrase are highlighted which are exists in the document.
		 */

		////@Test(groups = { "regression" }, priority = 17)
		public void verifyTwoDifferentAdhocResultsWithTermAndPhrase() throws InterruptedException {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51760 Production-sprint:07");
			baseClass.stepInfo("#### Verify when navigated to doc view with two different ad-hoc results with term/phrase are highlighted which are exists in the document ####");
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.searchString1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.ViewInDocViews();
			
			docView = new DocViewPage(driver) ;
			
			baseClass.stepInfo("Get Persistent Hit");
			docView.getPersistentHit(Input.searchString1);
			
			baseClass.stepInfo("Log out");
			loginPage.logout();
			
			baseClass.stepInfo("Login To SightLine");
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch("\""+Input.thankyouText+"\"");
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.ViewInDocViews();
			
			baseClass.stepInfo("Get Persistent Hit");
			docView.getPersistentHit(Input.thankyouText);
			
			baseClass.stepInfo("Log out");
			loginPage.logout();
			
			baseClass.stepInfo("Login To SightLine");
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearchForTwoItems("\""+Input.thankyouText+"\"",Input.searchString1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.ViewInDocViews();
			
			baseClass.stepInfo("Get Persistent Hit");
			docView.getPersistentHit(Input.searchString1);
			docView.getPersistentHit(Input.thankyouText);
			
		}
		
		/**
		 * @author Gopinath
		 * @throws InterruptedException 
		 * ////@Testcase_Id : RPMXCON-52200 : Verify that Producing a TIFF with Block Redaction  burning properly and not eliminate any characters in the produced document..
		 * @description : Verify that Producing a TIFF with Block Redaction  burning properly and not eliminate any characters in the produced document.
		 */

		////@Test(groups = { "regression" }, priority = 18)
		public void verifyThatProducingTiffWithBlockRedaction() throws InterruptedException {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-52200 Production-sprint:07");
			baseClass.stepInfo("#### Verify that Producing a TIFF with Block Redaction  burning properly and not eliminate any characters in the produced document. ####");
			String redactiontag1 = Input.randomText+Utility.dynamicNameAppender();
			String foldername = Input.randomText + Utility.dynamicNameAppender();
			String productionname = Input.randomText+ Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			
			RedactionPage redactionpage=new RedactionPage(driver);
			
			baseClass.stepInfo("Navigate To Redactions Page URL");
			redactionpage.navigateToRedactionsPageURL();
			
			baseClass.stepInfo("Manage Redaction Tags Page");
			redactionpage.manageRedactionTagsPage(redactiontag1);
			
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.addDocsMetCriteriaToActionBoard();
			
			docViewMetaDataPage = new DocViewMetaDataPage(driver) ;
			
			baseClass.stepInfo("Click on reduction button ");
			docViewMetaDataPage.clickOnRedactAndRectangle();
			
			baseClass.stepInfo("Set rectangle reduct in doc");
			docViewMetaDataPage.redactbyrectangle(10, 15,redactiontag1);
			
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			
			baseClass.stepInfo("Navigate To Tags And Folder Page");
			tagsAndFolderPage.navigateToTagsAndFolderPage();
			
			baseClass.stepInfo("Create Folder");
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Bulk Folder Existing");
			search.bulkFolderExisting(foldername);
			
			// create production and fill dat field and verify specify controls
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			
			ProductionPage page = new ProductionPage(driver);
			
			baseClass.stepInfo("Select default security group");
			page.selectingDefaultSecurityGroup();
			
			baseClass.stepInfo("Navigate To Production Page");
			page.navigateToProductionPage();
			
			baseClass.stepInfo("Add new production");
			page.addANewProduction(productionname);
			
			baseClass.stepInfo("Filling DAT Section");
			page.fillingDATSection();
			
			baseClass.stepInfo("Filling Native Section");
			page.fillingNativeSection();
			
			baseClass.stepInfo("Filling TIFF Section with Burn Redaction");
			page.fillingTIFFSectionwithBurnRedaction(redactiontag1);
			
			baseClass.stepInfo("Filling text section");
			page.fillingTextSection();
			
			baseClass.stepInfo("Navigate to next section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Numbering And Sorting Page");
			page.fillingNumberingAndSortingPage(prefixID, suffixID);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Document Selection Page");
			page.fillingDocumentSelectionPage(foldername);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Priv Guard Page");
			page.fillingPrivGuardPage();
			
			baseClass.stepInfo("Filling Production Location Page And Passing Text");
	 		page.fillingProductionLocationPageAndPassingText(productionname);
	 		
	 		baseClass.stepInfo("Navigate To Next Section");
	 		page.navigateToNextSection();
	 		
	 		baseClass.stepInfo("Filling Summary And Preview");
	 		page.fillingSummaryAndPreview();
	 		
	 		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
	 		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
			
		}
		
		/**
		 * @author Gopinath
		 * @throws InterruptedException 
		 * ////@Testcase_Id : RPMXCON-52199 :Verify that Producing a TIFF with Page Redaction  burning properly and not eliminate any characters in the produced document.
		 * @description : Verify that Producing a TIFF with Page Redaction  burning properly and not eliminate any characters in the produced document.
		 */

		////@Test(groups = { "regression" }, priority = 19)
		public void verifyThatProducingTiffWithPageRedaction() throws InterruptedException {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-52199 Production-sprint:07");
			baseClass.stepInfo("#### Verify that Producing a TIFF with Page Redaction  burning properly and not eliminate any characters in the produced document. ####");
			String redactiontag1 = Input.randomText+Utility.dynamicNameAppender();
			String foldername = Input.randomText + Utility.dynamicNameAppender();
			String productionname = Input.randomText+ Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			
			RedactionPage redactionpage=new RedactionPage(driver);
			
			baseClass.stepInfo("Navigate To Redactions Page URL");
			redactionpage.navigateToRedactionsPageURL();
			
			baseClass.stepInfo("Manage Redaction Tags Page");
			redactionpage.manageRedactionTagsPage(redactiontag1);
			
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.addDocsMetCriteriaToActionBoard();
			
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			
			baseClass.stepInfo("Clicking Redaction Icon");
			docViewRedact.clickingRedactionIcon();
			
			baseClass.stepInfo("Perform This Page Redaction");
			docViewRedact.performThisPageRedaction(redactiontag1);
			
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			
			baseClass.stepInfo("Navigate To Tags And Folder Page");
			tagsAndFolderPage.navigateToTagsAndFolderPage();
			
			baseClass.stepInfo("Create Folder");
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Bulk Folder Existing");
			search.bulkFolderExisting(foldername);
			
			// create production and fill dat field and verify specify controls
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			
			ProductionPage page = new ProductionPage(driver);
			
			baseClass.stepInfo("Select default security group");
			page.selectingDefaultSecurityGroup();
			
			baseClass.stepInfo("Navigate To Production Page");
			page.navigateToProductionPage();
			
			baseClass.stepInfo("Add new production");
			page.addANewProduction(productionname);
			
			baseClass.stepInfo("Filling DAT Section");
			page.fillingDATSection();
			
			baseClass.stepInfo("Filling Native Section");
			page.fillingNativeSection();
			
			baseClass.stepInfo("Filling TIFF Section with Burn Redaction");
			page.fillingTIFFSectionwithBurnRedaction(redactiontag1);
			
			baseClass.stepInfo("Filling text section");
			page.fillingTextSection();
			
			baseClass.stepInfo("Navigate to next section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Numbering And Sorting Page");
			page.fillingNumberingAndSortingPage(prefixID, suffixID);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Document Selection Page");
			page.fillingDocumentSelectionPage(foldername);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Priv Guard Page");
			page.fillingPrivGuardPage();
			
			baseClass.stepInfo("Filling Production Location Page And Passing Text");
	 		page.fillingProductionLocationPageAndPassingText(productionname);
	 		
	 		baseClass.stepInfo("Navigate To Next Section");
	 		page.navigateToNextSection();
	 		
	 		baseClass.stepInfo("Filling Summary And Preview");
	 		page.fillingSummaryAndPreview();
	 		
	 		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
	 		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
			
		}
		/** 
		 * Author : Brundha Created date: NA Modified date: NA Modified by:NA
		 * TestCase id : 52196 -Verify that Producing a PDF with Page Redaction burning properly and not eliminate any characters in the produced document.
		 * Description :Verify that Producing a PDF with Page Redaction burning properly and not eliminate any characters in the produced document.
		 */
		//@Test(alwaysRun = true,groups={"regression"},priority = 18)
		public void verifyTheRedactionOfTagInProduction() {
			try {
				baseClass=new BaseClass(driver);
				baseClass.stepInfo("RPMXCON-52196 Production-sprint:07");
				
				String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
				String prefixID = "A_" + Utility.dynamicNameAppender();
				String suffixID = "_P" + Utility.dynamicNameAppender();
				String foldername = "FolderProd" + Utility.dynamicNameAppender();		
				baseClass.stepInfo("#### Verify that Producing a PDF with Page Redaction burning properly and not eliminate any characters in the produced document. ####");
				
				RedactionPage redactionpage=new RedactionPage(driver);
				redactionpage.navigateToRedactionsPageURL();
				redactionpage.manageRedactionTagsPage(Redactiontag1);
				
				SessionSearch sessionSearch = new SessionSearch(driver);		
				baseClass.stepInfo("Basic meta data search");
				sessionSearch.basicContentSearch(Input.testData1);
				sessionSearch.ViewInDocView();
				DocViewPage doc=new DocViewPage(driver);
				doc.nonAudioPageRedaction(Redactiontag1);
				String productionname;
				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
				
				SessionSearch sessionSearch1 = new SessionSearch(driver);
				sessionSearch1 = new SessionSearch(driver);
				sessionSearch1.bulkFolderExisting(foldername);

				// create production with DAT,Native,PDF& ingested Text
				ProductionPage page = new ProductionPage(driver);
				productionname = "p" + Utility.dynamicNameAppender();
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingNativeSection();
				page.fillingPDFSection();
				page.disablePrivDocAndEnableBurnRedaction();
				page.burnRedactionWithRedactionTag(Redactiontag1);
				page.fillingTextSection();
				page.navigateToNextSection();
				page.fillingNumberingAndSortingPage(prefixID, suffixID);
				page.navigateToNextSection();
				page.fillingDocumentSelectionPage(foldername);
				page.navigateToNextSection();
				page.fillingPrivGuardPage();
				page.fillingProductionLocationPage(productionname);
				page.navigateToNextSection();
				page.fillingSummaryAndPreview();

				// Wait until Generate button enables
				baseClass.waitForElement(page.getbtnProductionGenerate());
				page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
			} catch (InterruptedException e) {
				baseClass.stepInfo("Exception occured while handling  Producing a PDF with Page Redaction"+e.getMessage());
				e.printStackTrace();
			}
			
		}
		

		
		
		
		
		/**
		 * Author : Brundha Created date: NA Modified date: NA Modified by:NA
		 * TestCase id : 52197 -Verify that Producing a PDF with Block Redaction burning properly and not eliminate any characters in the produced document.
		 * Description :Verify that Producing a PDF with Block Redaction burning properly and not eliminate any characters in the produced document.
		 * 
		 */
		//@Test(alwaysRun = true,groups={"regression"},priority =19)
		public void verifyThRectangleeRedactionOfTagInProduction() {
			try {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-52197 Production-sprint:07");
			
			String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
			String prefixID = "A_" + Utility.dynamicNameAppender();
			String suffixID = "_P" + Utility.dynamicNameAppender();
			baseClass.stepInfo("#### Verify that Producing a PDF with Block Redaction burning properly and not eliminate any characters in the produced document. ####");
			
			RedactionPage redactionpage=new RedactionPage(driver);
			redactionpage.navigateToRedactionsPageURL();
			redactionpage.manageRedactionTagsPage(Redactiontag1);
			
			SessionSearch sessionSearch = new SessionSearch(driver);		
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.ViewInDocView();
			
			DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
			docViewRedactions.redactRectangleUsingOffset(10,10,100,100);
			driver.waitForPageToBeReady();
			docViewRedactions.selectingRedactionTag(Redactiontag1);
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String productionname;
		    TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
			SessionSearch sessionSearch1 = new SessionSearch(driver);
			sessionSearch1 = new SessionSearch(driver);
			sessionSearch1.bulkFolderExisting(foldername);
			
			// create production with DAT,Native,PDF& ingested Text
			ProductionPage page = new ProductionPage(driver);
			productionname = "p" + Utility.dynamicNameAppender();
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			page.fillingDATSection();
			page.fillingNativeSection();
			page.fillingPDFSection();
			page.disablePrivDocAndEnableBurnRedaction();
			page.burnRedactionWithRedactionTag(Redactiontag1);
			page.fillingTextSection();
			page.navigateToNextSection();
			page.fillingNumberingAndSortingPage(prefixID, suffixID);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingProductionLocationPage(productionname);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();

			// Wait until Generate button enables
			baseClass.waitForElement(page.getbtnProductionGenerate());
			page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
			}
			catch(Exception e) {
				baseClass.stepInfo("Exception occured while Producing a PDF with Block Redaction burning"+e.getMessage());
				e.printStackTrace();
			}
		}

		/**
		 * @author Gopinath
		 * @throws Exception 
		 * //@Testcase_Id : RPMXCON-52195 :Verify that Producing a PDF with text redaction burning properly and not eliminate any text characters in the produced document..
		 * @description : Verify that Producing a PDF with text redaction burning properly and not eliminate any text characters in the produced document.
		 */

		//@Test(groups = { "regression" }, priority = 20)
		public void verifyThatProducingPDFWithTextRedaction() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-52195 Production-sprint:07");
			baseClass.stepInfo("#### Verify that Producing a PDF with text redaction burning properly and not eliminate any text characters in the produced document. ####");
			String redactiontag1 = Input.randomText+Utility.dynamicNameAppender();
			String foldername = Input.randomText + Utility.dynamicNameAppender();
			String productionname = Input.randomText+ Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			
			RedactionPage redactionpage=new RedactionPage(driver);
			
			baseClass.stepInfo("Navigate To Redactions Page URL");
			redactionpage.navigateToRedactionsPageURL();
			
			baseClass.stepInfo("Manage Redaction Tags Page");
			redactionpage.manageRedactionTagsPage(redactiontag1);
			
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.addDocsMetCriteriaToActionBoard();
			
			redact = new DocViewRedactions(driver);
			
			baseClass.stepInfo("Redact Text Using Off set");
			redact.redactTextUsingOffset();
			
			baseClass.stepInfo("Selecting Redaction Tag");
			redact.selectingRedactionTag2(redactiontag1);
			
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			
			baseClass.stepInfo("Navigate To Tags And Folder Page");
			tagsAndFolderPage.navigateToTagsAndFolderPage();
			
			baseClass.stepInfo("Create Folder");
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Bulk Folder Existing");
			search.bulkFolderExisting(foldername);
			
			// create production and fill dat field and verify specify controls
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			
			ProductionPage page = new ProductionPage(driver);
			
			baseClass.stepInfo("Select default security group");
			page.selectingDefaultSecurityGroup();
			
			baseClass.stepInfo("Navigate To Production Page");
			page.navigateToProductionPage();
			
			baseClass.stepInfo("Add new production");
			page.addANewProduction(productionname);
			
			baseClass.stepInfo("Filling DAT Section");
			page.fillingDATSection();
			
			baseClass.stepInfo("Filling Native Section");
			page.fillingNativeSection();
			
			baseClass.stepInfo("Filling PDF Section with Burn Redaction");
			page.fillingPDFSectionwithBurnRedaction(redactiontag1);
			
			baseClass.stepInfo("Filling text section");
			page.fillingTextSection();
			
			baseClass.stepInfo("Navigate to next section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Numbering And Sorting Page");
			page.fillingNumberingAndSortingPage(prefixID, suffixID);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Document Selection Page");
			page.fillingDocumentSelectionPage(foldername);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Priv Guard Page");
			page.fillingPrivGuardPage();
			
			baseClass.stepInfo("Filling Production Location Page And Passing Text");
	 		page.fillingProductionLocationPageAndPassingText(productionname);
	 		
	 		baseClass.stepInfo("Navigate To Next Section");
	 		page.navigateToNextSection();
	 		
	 		baseClass.stepInfo("Filling Summary And Preview");
	 		page.fillingSummaryAndPreview();
	 		
	 		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
	 		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		}
		
		
		/**
		 * @author Gopinath
		 * @throws Exception 
		 * //@Testcase_Id : RPMXCON-52198 : Verify that Producing a TIFF with text redaction burning properly and not eliminate any text characters in the produced document.
		 * @description : Verify that Producing a TIFF with text redaction burning properly and not eliminate any text characters in the produced document.
		 */

		//@Test(groups = { "regression" }, priority = 21)
		public void verifyThatProducingTiffWithTextRedaction() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-52198 Production-sprint:07");
			baseClass.stepInfo("#### Verify that Producing a TIFF with text redaction burning properly and not eliminate any text characters in the produced document. ####");
			String redactiontag1 = Input.randomText+Utility.dynamicNameAppender();
			String foldername = Input.randomText + Utility.dynamicNameAppender();
			String productionname = Input.randomText+ Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			
			RedactionPage redactionpage=new RedactionPage(driver);
			
			baseClass.stepInfo("Navigate To Redactions Page URL");
			redactionpage.navigateToRedactionsPageURL();
			
			baseClass.stepInfo("Manage Redaction Tags Page");
			redactionpage.manageRedactionTagsPage(redactiontag1);
			
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.addDocsMetCriteriaToActionBoard();
			
			redact = new DocViewRedactions(driver);
			
			baseClass.stepInfo("Redact Text Using Off set");
			redact.redactTextUsingOffset();
			
			baseClass.stepInfo("Selecting Redaction Tag");
			redact.selectingRedactionTag2(redactiontag1);
			
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			
			baseClass.stepInfo("Navigate To Tags And Folder Page");
			tagsAndFolderPage.navigateToTagsAndFolderPage();
			
			baseClass.stepInfo("Create Folder");
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Bulk Folder Existing");
			search.bulkFolderExisting(foldername);
			
			// create production and fill dat field and verify specify controls
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			
			ProductionPage page = new ProductionPage(driver);
			
			baseClass.stepInfo("Select default security group");
			page.selectingDefaultSecurityGroup();
			
			baseClass.stepInfo("Navigate To Production Page");
			page.navigateToProductionPage();
			
			baseClass.stepInfo("Add new production");
			page.addANewProduction(productionname);
			
			baseClass.stepInfo("Filling DAT Section");
			page.fillingDATSection();
			
			baseClass.stepInfo("Filling Native Section");
			page.fillingNativeSection();
			
			baseClass.stepInfo("Filling PDF Section with Burn Redaction");
			page.fillingTIFFSectionwithBurnRedaction(redactiontag1);
			
			baseClass.stepInfo("Filling text section");
			page.fillingTextSection();
			
			baseClass.stepInfo("Navigate to next section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Numbering And Sorting Page");
			page.fillingNumberingAndSortingPage(prefixID, suffixID);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Document Selection Page");
			page.fillingDocumentSelectionPage(foldername);
			
			baseClass.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();
			
			baseClass.stepInfo("Filling Priv Guard Page");
			page.fillingPrivGuardPage();
			
			baseClass.stepInfo("Filling Production Location Page And Passing Text");
	 		page.fillingProductionLocationPageAndPassingText(productionname);
	 		
	 		baseClass.stepInfo("Navigate To Next Section");
	 		page.navigateToNextSection();
	 		
	 		baseClass.stepInfo("Filling Summary And Preview");
	 		page.fillingSummaryAndPreview();
	 		
	 		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
	 		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		}
		
		/**
		 * @author Gopinath
		 * //@Testcase_Id : RPMXCON-51542 : Verify that same user with two different tabs in the same browser, and confirm that able to delete reviewer remark to the same records successfully
		 * @description : Verify that same user with two different tabs in the same browser, and confirm that able to delete reviewer remark to the same records successfully.
		 */

		//@Test(groups = { "regression" }, priority = 22)
		public void verifyReamrkByDifferentTabsOnSameBrowser() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51542 Production-sprint:07");
			baseClass.stepInfo("#### Verify that same user with two different tabs in the same browser, and confirm that able to delete reviewer remark to the same records successfully. ####");
			String remark = Input.randomText + Utility.dynamicNameAppender();
			SessionSearch search = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			search.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic Content Search");
			search.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Add Docs Met Criteria To Action Board");
			search.addDocsMetCriteriaToActionBoard();
			
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Add Remark To Non Audio Document");
			docView.addRemarkToNonAudioDocument(10, 20,remark);
			
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			
			baseClass.stepInfo("Clicking Redaction Icon");
			docViewRedact.clickingRedactionIcon();
			
			driver.waitForPageToBeReady();
			
			baseClass.stepInfo("perform This Page Redaction");
			docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
			
			baseClass.stepInfo("Open Duplicate Tab Of Already Opened Tab");
			docViewMetaDataPage.openDuplicateTabOfAlreadyOpenedTab();
			
			ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
			
			baseClass.stepInfo("Switch To child Window");
			String parentWindow = reusableDocView.switchTochildWindow();
			
			baseClass.stepInfo("Add Remark To Non Audio Document");
			docView.addRemarkToNonAudioDocument(10, 20,remark);
			
			baseClass.stepInfo("Switch to parent window from child window");
			reusableDocView.childWindowToParentWindowSwitching(parentWindow);
			
			baseClass.stepInfo("Click On Remark Button");
			docViewMetaDataPage.clickOnRemarkButton();
			
			baseClass.stepInfo("Verify Another user applied message displayed by adding remark in duplicate window");
			docViewMetaDataPage.verifyAnotherUserAppliedRemarkMsgDisplayed(Input.anotherRemarkMessage);
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Verify Remark Is Added");
			docView.verifyRemarkIsAdded(remark);
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Add Remark To Non Audio Document");
			docView.addRemarkToNonAudioDocument(10, 20,remark);
			
			baseClass.stepInfo("Open Duplicate Tab Of Already Opened Tab");
			docViewMetaDataPage.openDuplicateTabOfAlreadyOpenedTab();
			
			baseClass.stepInfo("Switch To child Window");
			parentWindow = reusableDocView.switchTochildWindow();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Delete remark");
			docView.deleteReamark(remark);
			
			baseClass.stepInfo("Switch to parent window from child window");
			reusableDocView.childWindowToParentWindowSwitching(parentWindow);
			
			baseClass.stepInfo("Verify Another user applied message displayed by adding remark in duplicate window");
			docViewMetaDataPage.verifyAnotherUserAppliedRemarkMsgDisplayed(Input.anotherRemarkMessage);
		
		}
		
		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
		 * TestCase id :  51446 - To verify documents should be Folder when document outside of the reviewers batch is viewed from analytics panel.
		 * Description : To verify documents should be Folder when document outside of the reviewers batch is viewed from analytics panel.
		 */
		//@Test(alwaysRun = true,groups={"regression"},priority = 23)
		public void verifyDocumentsShouldFolderbyFamilyMembersTab() throws Exception {		
			baseClass=new BaseClass(driver);
			String assignmentName = Input.randomText + Utility.dynamicNameAppender();
			String folderName = Input.randomText + Utility.dynamicNameAppender();
			baseClass.stepInfo("Test case Id: RPMXCON-51446");
			utility = new Utility(driver);
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo("#### To verify documents should be Folder when document outside of the reviewers batch is viewed from analytics panel. ####");
			
			docView = new DocViewPage(driver);
			agnmt = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.familyMembersDocId);
			
			baseClass.stepInfo("Bulk assign with new assignment");
			sessionSearch.bulkAssignWithNewAssignment();
		
			driver.waitForPageToBeReady();
			
			baseClass.stepInfo("Create assignment by bulk assign operationfrom Session search");
			agnmt.createAssignmentByBulkAssignOperation(assignmentName, Input.codeFormName);
			
			baseClass.stepInfo("Select assignment to view in Doc view");
			agnmt.selectAssignmentToViewinDocview(assignmentName);
			
			baseClass.stepInfo("Select Doc From Family Members And Create Folder");
			docViewMetaDataPage.selectDocFromFamilyMembersAndCreateFolder(Input.familyMembersDocId,folderName);
			
		}
		
		/**
		 * Author : Brundha Created date: NA Modified date: NA Modified by:NA
		 * TestCase id :RPMXCON- 51931 
		 * Description :Verify that when user is viewing a document in DocView, the entry for the same 
		 * document in mini-DocList child window must always present fully in the visible area of the mini-DocList (to the user)
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 24)
		public void verifyTheDocIdWithMiniDoclistDocId() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51931 docview-sprint:07");
			baseClass.stepInfo("#### Verify that when user is viewing a document in DocView, the entry for the same document in mini-DocList child window  ####");
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("navigating to docview page");
			sessionSearch.ViewInDocView();
			
			DocViewPage doc=new DocViewPage(driver);
			
			baseClass.stepInfo("verifying the Document selected on minidoclist in main window");
			doc.selectMiniDocListDocAndComparingWithParentWindow();
		}
		
		
		
		/**
		 * Author : Brundha Created date: NA Modified date: NA Modified by:NA
		 * TestCase id :RPMXCON- 51932 
		 * Description: Verify that when performing doc-to-doc navigation the entry for the same 
		 * document in mini-DocList child window must always present fully in the visible area
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 25)
		public void afterNavigationMiniDocListDocumentDefaultDisplay() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51932 docview-sprint:07");
			baseClass.stepInfo("#### Verify that when performing doc-to-doc navigation the entry for the same document in mini-DocList child window  ####");
			
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("navigating to docview page");
			sessionSearch.ViewInDocView();
			DocViewPage doc=new DocViewPage(driver);
			
			baseClass.stepInfo("Verification of navigated DocID with parent window DocID");
			doc.switchFromChildWindowToParentWindow();
		}
		
		
		/**
		 * Author : Brundha Created date: NA Modified date: NA Modified by:NA
		 * TestCase id :RPMXCON- 51308
		 * Description:Verify persistent Hit panel of DocView should present only content terms, not operators when navigating from basic search  
		 * 
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 18)
		public void verifyPersistentHitPanelOfDocView() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51308 docview-sprint:07");
			baseClass.stepInfo("#### Verify persistent Hit panel of DocView should present only content terms, not operators when navigating from basic search  ####");
			
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();
			
			DocViewPage doc=new DocViewPage(driver);
			
			baseClass.stepInfo("verify persistent Hit panel of docview contains basic search string");
			doc.getPersistentHit(Input.testData1);
			doc.SearchStringVerification();
		}
		
		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
		 * TestCase id :RPMXCON- 51304 : Verify persistent Hit panel of DocView should present only content terms, not metadata terms when navigating from basic search.
		 * Description: Verify persistent Hit panel of DocView should present only content terms, not metadata terms when navigating from basic search.
		 * 
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 19)
		public void verifyPersistentHitPanelOfDocViewByMetaData() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51304 docview-sprint:08");
			baseClass.stepInfo("####Verify persistent Hit panel of DocView should present only content terms, not metadata terms when navigating from basic search ####");
			
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
			sessionSearch.addDocsMetCriteriaToActionBoard();
			
			DocViewPage doc=new DocViewPage(driver);
			
			baseClass.stepInfo("verify persistent Hit panel of docview contains basic search string");
			doc.verifyPersistentPanelNotContainsTerm(Input.metaDataCN);
		}
		
		
		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
		 * TestCase id :RPMXCON- 51307 : Verify persistent Hit panel of DocView should present only content terms, not Comment/Remark when navigating from basic search.
		 * Description: Verify persistent Hit panel of DocView should present only content terms, not Comment/Remark when navigating from basic search.
		 * 
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 20)
		public void verifyPersistentHitPanelOfDocViewByRemark() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51307 docview-sprint:08");
			baseClass.stepInfo("#### Verify persistent Hit panel of DocView should present only content terms, not Comment/Remark when navigating from basic search ####");
			String remark = Input.randomText + Utility.dynamicNameAppender();
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();
			
			DocViewPage docView=new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Add Remark To Non Audio Document");
			docView.addRemarkToNonAudioDocument(10, 20,remark);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			sessionSearch.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();
			
			baseClass.stepInfo("verify persistent Hit panel of docview contains remark");
			docView.verifyPersistentPanelNotContainsTerm(remark);
			
			
		}
		
		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
		 * TestCase id :RPMXCON- 51906 : Verify that when user in on Images tab and navigates to next document then should be on Images tab for next document.
		 * Description: Verify that when user in on Images tab and navigates to next document then should be on Images tab for next document.
		 * 
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 21)
		public void verifyImageTabEnabledWhenNavigatesToNextDocument() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51906 docview-sprint:08");
			baseClass.stepInfo("#### Verify that when user in on Images tab and navigates to next document then should be on Images tab for next document ####");
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			
			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();
			
			DocViewPage docView=new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Click On Image Tab");
			docView.clickOnImageTab();
			
			baseClass.stepInfo("Verify Image Tab Is Enabled");
			docView.verifyImageTabIsEnabled();
			
			baseClass.stepInfo("Verify user navigated to next document.");
			docView.verifyUserNavigatedToNextDocument();
			
			baseClass.stepInfo("Verify Image Tab Is Enabled");
			docView.verifyImageTabIsEnabled();
		}
		
		
		/**
		 * @Author : Gopinath 
		 * @testcase_id :RPMXCON-51930 : Verify that when completing the documents on applying the stamp the entry for the navigated document in mini-DocList. 
		 * @Description : Verify that when completing the documents on applying the stamp the entry for the navigated document in mini-DocList.
		 */

		@Test(enabled = true, groups = { "regression" }, priority = 22)
		public void verifyingCodingStampPostFixColourChildWindowPopUp() throws InterruptedException {
			baseClass.stepInfo("Test case Id: RPMXCON-51930");
			String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
			String comment = Input.randomText + Utility.dynamicNameAppender();
			String fieldText = Input.randomText  + Utility.dynamicNameAppender();
			
			baseClass.stepInfo("#### Verify that when completing the documents on applying the stamp the entry for the navigated document in mini-DocList. ####");
			AssignmentsPage assignmentPage = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			// searching document for assignmnet creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
			assignmentPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			assignmentPage.SelectAssignmentByReviewer(AssignStamp);
			
			docView = new DocViewPage(driver);
			
			docView.stampCompleteNavigateNextDocumumentVerification(comment, fieldText, Input.stampSelection);
			}

		
		/**
		 * @author Gopinath
		 * TestCase id : 51907 - Verify that when user in on Images tab and folder few documents then 
		 *                       on loading of document should be on Images tab of document
		 *  Description : Verify that when user in on Images tab and folder few documents then on loading
		 *                of document should be on Images tab of document                     
		 */
		@Test(enabled = true, groups = { "regression" }, priority = 23)
		public void  verifyUserOnImagesTabAfterCreatingFolder() throws InterruptedException {
				String folderName = Input.randomText+Utility.dynamicNameAppender();
				int RowNumber =1;
				baseClass=new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-51907");
				baseClass.stepInfo("#### Verify that when user in on Images tab and folder few documents then on loading of document should be on Images tab of document ####");
				
				docView = new DocViewPage(driver);
				SessionSearch session = new SessionSearch(driver);
				
				baseClass.stepInfo("Basic Basic content search");
				session.basicContentSearch(Input.searchString1);
			
				baseClass.stepInfo("Navigate to  DocView page");
				session.ViewInDocView();
				
				baseClass.stepInfo("Document selected");
				docView.selectRowFromMiniDocList(RowNumber);
				
				baseClass.stepInfo("Verify image tab is enabled if folder is added.");
				docView.verifyImageEnabledAfterCreatedFolder(folderName, RowNumber);
		}
		
		/**
		 * @Author : Gopinath
		 * @Testcase_Id : RPMXCON-51935 : Verify that when completing the documents on applying the stamp the entry for the navigated document in mini-DocList child window.
		 * @Description : Verify that when completing the documents on applying the stamp the entry for the navigated document in mini-DocList child window .
		 */

		@Test(enabled = true, groups = { "regression" }, priority = 24)
		public void verifyCursorNavigatedProperlyBySavedCodingStampApplied() throws InterruptedException {
			baseClass.stepInfo("Test case Id: RPMXCON-51935 Sprint 09");
			baseClass.stepInfo("#### Verify that when completing the documents on applying the stamp the entry for the navigated document in mini-DocList child window ####");
			String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
			String assgnColour = Input.randomText  + Utility.dynamicNameAppender();
			AssignmentsPage assignmentPage = new AssignmentsPage(driver);
			MiniDocListPage miniDoc = new MiniDocListPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			// searching document for assignmnet creation
			
			baseClass.stepInfo("Basic Content Search");
			sessionSearch.basicContentSearch(Input.searchString2);
			
			baseClass.stepInfo("Bulk Assign");
			sessionSearch.bulkAssign();
			
			baseClass.stepInfo("Assignment Creation");
			assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			assignmentPage.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assignmentPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Selecting the assignment");
			assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Coding Stamp For Saved Document");
			docView.codingStampForSavedDocument(assgnColour, Input.stampSelection);
			
			baseClass.stepInfo("Verify cursor navigated to child window clicking on saved stamp.");
			miniDoc.verifyCursorNavigatedToChildWindowClickingOnSavedStamp(assgnColour, Input.stampSelection);
			
		}
		
		/**
		 * @Author : Gopinath
		 * @Testcase_Id : RPMXCON-51928 : erify that when completing the documents the entry for the navigated document in mini-DocList.
		 * @Description : Verify that when completing the documents the entry for the navigated document in mini-DocList.
		 */

		@Test(enabled = true, groups = { "regression" }, priority = 25)
		public void verifyCursorNavigatedProperlyByComplete() throws InterruptedException {
			baseClass.stepInfo("Test case Id: RPMXCON-51928 Sprint 09");
			baseClass.stepInfo("#### erify that when completing the documents the entry for the navigated document in mini-DocList ####");
			String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
			AssignmentsPage assignmentPage = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			// searching document for assignmnet creation
			
			baseClass.stepInfo("Basic Content Search");
			sessionSearch.basicContentSearch(Input.searchString2);
			
			baseClass.stepInfo("Bulk Assign");
			sessionSearch.bulkAssign();
			
			baseClass.stepInfo("Assignment Creation");
			assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			assignmentPage.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assignmentPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Selecting the assignment");
			assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Complete coding form and verify cursor navigated to next document.");
			docView.completeCodingFormAndVerifyCursorNavigateToNextDoc();
			
		}
		
		
		/**
		 * @author Gopinath
		 * TestCase ID : 50912 - Verify when user navigates to the document from Text tab
		 * Description :To verify when user navigates to the document from Text tab
		 * @throws InterruptedException 
		 */
		@Test (enabled = true, groups = { "regression" }, priority = 26)
		public void  verifyUserNavigateToDocumentFromTextTab() throws InterruptedException {
			
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-50912 spint-09");
			baseClass.stepInfo("#### verify when user navigates to the document from Text tab ####");
			
			docView = new DocViewPage(driver);
			SessionSearch session = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic Basic content search completed");
			session.basicContentSearch(Input.searchString1);
			
			baseClass.stepInfo("Navigated to  DocView page");
			session.ViewInDocView();
			
			baseClass.stepInfo("Verifying document displayed in text view.");
			docView.verifyDocumentDisplayedInTextView();
			
			baseClass.stepInfo("Verifying document loaded in default view.");
			docView.verifyDocumentLoadedInDefaultView();
		
		}
		
		/**
		 * @author Gopinath
		 * TestCase Id: 51933 Verify that when completing the documents the entry for the navigated document in mini-DocList child window.
		 * Description: Verify that when completing the documents the entry for the navigated document in mini-DocList child window.                  
		 */
		@Test(enabled = true, groups = { "regression" }, priority = 26)
		public void verifyMiniDocNavigateTonExtDocAfterDOcComplete() {
			baseClass.stepInfo("Test case Id: RPMXCON-51933 Sprint-09");
			String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
			
			baseClass.stepInfo("#### Verify that when completing the documents the entry for the navigated document in mini-DocList child window ####");
			AssignmentsPage assignmentPage = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			// searching document for assignmnet creation
			
			baseClass.stepInfo("Basic content search");
			sessionSearch.basicContentSearch(Input.searchString2);
			
			baseClass.stepInfo("Bulk Assign");
			sessionSearch.bulkAssign();
			
			baseClass.stepInfo("Assignment Creation");
			assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assignmentPage.assignmentDistributingToReviewer();

			loginPage.logout();
	
			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Select Assignment By Reviewer");
			assignmentPage.SelectAssignmentByReviewer(AssignStamp);
			
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Verify weather mini docList Document is navigate to next doc in mini doc list child window after doc complete.");
			docView.verifyNavegatingofDocInMiniDocLIstAfterComplete();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
		}
		
		
		/**
		 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
		 * id :RPMXCON- 51433 Description: Verify that if the document native is being
		 * presented, the "N" icon with the accompanying mouse over tool tip must appear
		 * 
		 */
		@Test(alwaysRun = true, groups = { "regression" }, priority = 23)

		public void verifyNIconAndToolTipInDocView() throws Exception {

			baseClass = new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51433 docview-sprint:9");
			baseClass.stepInfo(
					"#### Verify that if the document native is being presented, the 'N' icon with the accompanying mouse over tool tip must appear ####");
			
	        String DocId="ID00001155";
			String ExpectedText = "Native file variant of the document being displayed";
			baseClass.stepInfo("Basic meta data search");
			SessionSearch sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(DocId);

			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();

			DocViewPage docView = new DocViewPage(driver);
			docView.navigateToDocViewPageURL();

			baseClass.stepInfo("Verify Native Document Tooltip");
			docView.verifyingToolTipPopupMessage(DocId, ExpectedText);

			loginPage.logout();
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

			sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(DocId);

			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();

			docView = new DocViewPage(driver);
			docView.navigateToDocViewPageURL();

			baseClass.stepInfo("Verify Native Document Tooltip");
			docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
			loginPage.logout();

			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password );

			sessionSearch = new SessionSearch(driver);
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(DocId);

			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();

			docView = new DocViewPage(driver);
			docView.navigateToDocViewPageURL();

			baseClass.stepInfo("Verify Native Document Tooltip");
			docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
			
		}

		/**
		 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
		 * id :RPMXCON- 51434 Description: Verify that if the document PDF is being
		 * presented, the "P" icon with the accompanying mouse over tool tip must appear
		 * 
		 */
		@Test(alwaysRun = true, groups = { "regression" }, priority = 24)

		public void verifyPIconAndToolTipInDocView() throws Exception {

			
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-51434 docview-sprint:9");
			baseClass.stepInfo(
					"#### Verify that if the document PDF is being presented, the 'P' icon with the accompanying mouse over tool tip must appear ####");
			SessionSearch sessionSearch = new SessionSearch(driver);

			String ExpectedText = "PDF file variant of the document being displayed";
			String Doc="ID00001036";
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Doc);

			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();

			DocViewPage docView = new DocViewPage(driver);

			docView.navigateToDocViewPageURL();

			baseClass.stepInfo("Verify PDF Document Tooltip");
			docView.verifyingToolTipPopupMessage(Doc, ExpectedText);
			
			loginPage.logout();
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

			sessionSearch = new SessionSearch(driver);
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Doc);

			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();

			docView = new DocViewPage(driver);
			docView.navigateToDocViewPageURL();

			baseClass.stepInfo("Verify PDF Document Tooltip");
			docView.verifyingToolTipPopupMessage(Doc, ExpectedText);
			loginPage.logout();
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password );

			sessionSearch = new SessionSearch(driver);
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Doc);

			baseClass.stepInfo("Navigating to docview page");
			sessionSearch.ViewInDocView();

			docView = new DocViewPage(driver);
			docView.navigateToDocViewPageURL();

			baseClass.stepInfo("Verify PDF Document Tooltip");
			docView.verifyingToolTipPopupMessage(Doc, ExpectedText);
			
		}

		/**
		 * @Author : Gopinath
		 * @Testcase_Id : RPMXCON-51850 : Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document same as last.
		 * @Description : Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document same as last.
		 */

		@Test(enabled = true, groups = { "regression" }, priority = 27)
		public void verifyPersistentPanelNotRetainPreviouslyViewedHitsByComplete() throws InterruptedException {
			baseClass.stepInfo("Test case Id: RPMXCON-51850 Sprint 09");
			baseClass.stepInfo("#### Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document same as last ####");
			String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
			AssignmentsPage assignmentPage = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			// searching document for assignmnet creation
			
			baseClass.stepInfo("Basic Content Search");
			sessionSearch.basicContentSearch(Input.searchString2);
			
			baseClass.stepInfo("Bulk Assign");
			sessionSearch.bulkAssign();
			
			baseClass.stepInfo("Assignment Creation");
			assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			assignmentPage.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assignmentPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Selecting the assignment");
			assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Navigate To Doc View Page URL");
			docView.navigateToDocViewPageURL();
			
			baseClass.stepInfo("Click on persistant hit eye icon.");
			docView.clickOnPersistantHitEyeIcon();
			
			baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
			docView.verifyPersistentHitsPanelDisplayed();
		
			baseClass.stepInfo("Complete coding form and verify cursor navigated to next document.");
			docView.completeCodingFormAndVerifyCursorNavigateToNextDoc();
			
			baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
			docView.verifyPersistentHitsPanelDisplayed();
			
		}
		
		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
		 * TestCase id :  51728 - Verify that persistent hits should be highligted when documents are assigned to existing assignment from Basic Search.
		 * Description : Verify that persistent hits should be highligted when documents are assigned to existing assignment from Basic Search.
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 28)
		public void verifyPersistentHitsDisplayedByContextOfAssignment() throws Exception {	
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51728- DocView Sprint 09 ");
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			String assignStamp = Input.randomText + Utility.dynamicNameAppender();
			baseClass.stepInfo("#### Verify that persistent hits should be highligted when documents are assigned to existing assignment from Basic Search ####");
			
			AssignmentsPage assgnPage = new AssignmentsPage(driver);
			manageAssignment = new ManageAssignment(driver);
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Create Assignment");
			assgnPage.createAssignment(assignStamp, Input.codeFormName);
			
			SessionSearch search = new SessionSearch(driver);

			baseClass.stepInfo("Basic meta data search");
			search.basicContentSearch(Input.testData1);

			baseClass.stepInfo("Selected Doclist");
			search.ViewInDocList();

			DocListPage docPage = new DocListPage(driver);

			baseClass.stepInfo("Select The Document in Doclistpage");
			docPage.documentSelection(3);

			baseClass.stepInfo("Bulk Assign the selected Document");
			docPage.bulkAssignWithPersistantHit(assignStamp);
			
			baseClass.stepInfo("Created a assignment " + assignStamp);
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Edit Assignment");
			assgnPage.editAssignment(assignStamp);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			assgnPage.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assgnPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Selecting the assignment");
			assgnPage.SelectAssignmentByReviewer(assignStamp);
		
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Click on persistant hit eye icon.");
			docView.clickOnPersistantHitEyeIcon();
			
			baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
			docView.verifyPersistentHitsPanelDisplayed();
			
			baseClass.stepInfo("Verifying persistent Hits Displayed.");
			docView.verifyPersistentHitsDisplayed(Input.testData1);
		}
		
		/**
		 * @author Gopinath
		 * @throws InterruptedException 
		 * @TestCase Id: 51926 Verify that when user is viewing a document in DocView, the entry for the same document in mini-DocList.
		 * @Description: To Verify that when user is viewing a document in DocView, the entry for the same document in mini-DocList.
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 29)
		public void verifyDocFullyInVisibleAreaOfMiniDocList() throws InterruptedException {
			int rowNumber =2;
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51926");
			baseClass.stepInfo("#### Verify that when user is viewing a document in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList (to the user) ####");
			
			docView = new DocViewPage(driver);
			SessionSearch session = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic Basic content search");
			session.basicContentSearch(Input.searchString1);
			
			baseClass.stepInfo("Navigate to  DocView page");
			session.ViewInDocView();
			
			baseClass.stepInfo("Select Document From Mini Doc LIst");
			docView.selectDocumentFromMiniDocList(rowNumber);
		}
		
		/**
		 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
		 * @Testcase_id : 51727 - Verify that persistent hits should be highligted when documents are assigned to new assignment from Saved Search.
		 * @Description : Verify that persistent hits should be highligted when documents are assigned to new assignment from Saved Search.
		 */
		@Test(groups = { "regression" }, priority = 30)
		public void verifyPersistantHitByReviewerByCreatingNewAssignment() throws InterruptedException {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51727 spint 09");

			AssignmentsPage assgnPage = new AssignmentsPage(driver);
			SessionSearch search = new SessionSearch(driver);
			String searchname1 = Input.randomText + Utility.dynamicNameAppender();
			final String assignStamp = Input.randomText + Utility.dynamicNameAppender();
			final String searchname = searchname1;

			baseClass.stepInfo("####  Verify that persistent hits should be highligted when documents are assigned to new assignment from Saved Search. ####");

			baseClass.stepInfo("Basic Search");
			search.basicContentSearch(Input.searchString1);

			baseClass.stepInfo("Save searched content");
			search.saveSearch(searchname);

			SavedSearch savedSearch = new SavedSearch(driver);

			baseClass.stepInfo("Open Doc list from Saved search page");
			savedSearch.savedSearchToDocList(searchname);

			DocListPage docList = new DocListPage(driver);

			baseClass.stepInfo("Selecting all documents in current page");
			docList.selectAllDocumentsInCurrentPageOnly();
			
			baseClass.stepInfo("Assignment Creation");
			assgnPage.assignmentCreationWithPersistantHitDocList(assignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			assgnPage.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assgnPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Selecting the assignment");
			assgnPage.SelectAssignmentByReviewer(assignStamp);
		
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Click on persistant hit eye icon.");
			docView.clickOnPersistantHitEyeIcon();
			
			baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
			docView.verifyPersistentHitsPanelDisplayed();
			
			baseClass.stepInfo("Verifying persistent Hits Displayed.");
			docView.verifyPersistentHitsDisplayed(Input.searchString1);
		}
		
		/**
		 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
		 * @Testcase_id : 51726 - Verify that persistent hits should be highligted when documents are assigned to new assignment from Advanced Search.
		 * @Description : Verify that persistent hits should be highligted when documents are assigned to new assignment from Advanced Search
		 */
		@Test(groups = { "regression" }, priority = 31)
		public void verifyPersistantHitPanelByReviewerByAdvanceSearch() throws InterruptedException {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51726 spint 09");

			AssignmentsPage assgnPage = new AssignmentsPage(driver);
			SessionSearch search = new SessionSearch(driver);
			final String assignStamp = Input.randomText + Utility.dynamicNameAppender();
			

			baseClass.stepInfo("####  Verify that persistent hits should be highligted when documents are assigned to new assignment from Advanced Search ####");

			baseClass.stepInfo("Advance metadata Search");
			search.metaDataSearchInAdvancedSearch(Input.metaDataName, Input.metaDataCN);


			baseClass.stepInfo("Selected Doclist");
			search.ViewInDocList();

			DocListPage docList = new DocListPage(driver);

			baseClass.stepInfo("Selecting all documents in current page");
			docList.selectAllDocumentsInCurrentPageOnly();
			
			baseClass.stepInfo("Assignment Creation");
			assgnPage.assignmentCreationWithPersistantHitDocList(assignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			assgnPage.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assgnPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Selecting the assignment");
			assgnPage.SelectAssignmentByReviewer(assignStamp);
		
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Click on persistant hit eye icon.");
			docView.clickOnPersistantHitEyeIcon();
			
			baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
			docView.verifyPersistentHitsPanelDisplayed();
		}
		
		
		/**
		 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
		 * @Testcase_id : 51725 - Verify that persistent hits should be highligted when documents are assigned to new assignment from Basic Search.
		 * @Description : Verify that persistent hits should be highligted when documents are assigned to new assignment from Basic Search.
		 */
		@Test(groups = { "regression" }, priority = 32)
		public void verifyPersistantHitPanelByReviewerByBasicSearch() throws InterruptedException {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51725 spint 09");

			AssignmentsPage assgnPage = new AssignmentsPage(driver);
			SessionSearch search = new SessionSearch(driver);
			final String assignStamp = Input.randomText + Utility.dynamicNameAppender();

			baseClass.stepInfo("#### Verify that persistent hits should be highligted when documents are assigned to new assignment from Basic Search. ####");

			baseClass.stepInfo("Basic Search");
			search.basicContentSearch(Input.searchString1);

			baseClass.stepInfo("Selected Doclist");
			search.ViewInDocList();

			DocListPage docList = new DocListPage(driver);

			baseClass.stepInfo("Selecting all documents in current page");
			docList.selectAllDocumentsInCurrentPageOnly();
			
			baseClass.stepInfo("Assignment Creation");
			assgnPage.assignmentCreationWithPersistantHitDocList(assignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			assgnPage.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assgnPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Selecting the assignment");
			assgnPage.SelectAssignmentByReviewer(assignStamp);
		
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Click on persistant hit eye icon.");
			docView.clickOnPersistantHitEyeIcon();
			
			baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
			docView.verifyPersistentHitsPanelDisplayed();
			
			baseClass.stepInfo("Verifying persistent Hits Displayed.");
			docView.verifyPersistentHitsDisplayed(Input.searchString1);
		}
		
		
		/**
		 * @author Gopinath
		 * TestCase Id: 51757 Verify that all relevant hits should be displayed on persistent hits panel when navigated to doc view.
		 * Description:To Verify that all relevant hits should be displayed on persistent hits panel when navigated to doc view.             
		 * @throws InterruptedException 
		 */
		@Test(groups = { "regression" }, priority = 32)
		public void verifySearchTermDisplayOnPersistentHitPanal() throws InterruptedException {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51757");
			String keywordname = Input.randomText +Utility.dynamicNameAppender();
			String keyword = Input.randomText +Utility.dynamicNameAppender();
			baseClass.stepInfo("#### Verify that all relevant hits should be displayed on persistent hits panel when navigated to doc view with ad hoc search and keywords highlighting ####");

			KeywordPage keywordPage = new KeywordPage(driver);
			
			baseClass.stepInfo("Navigate to keyword page");
			keywordPage.navigateToKeywordPage();
		
			baseClass.stepInfo("Add keyword");
			keywordPage.AddKeyword(keywordname, keyword);
			
			baseClass.stepInfo("Get All Keywords in keywords lsit table");
			List<String> keywords = keywordPage.getAllKeywords();
			
			docView = new DocViewPage(driver);
			SessionSearch session = new SessionSearch(driver);

			baseClass.stepInfo("Basic Basic content search");
			session.basicContentSearch(Input.searchString1);
			
			baseClass.stepInfo("Navigate to  DocView page");
			session.ViewInDocView();
			
			baseClass.stepInfo("Persistent Hit With search string");
			docView.persistenHitWithSearchString(Input.searchString1);
			
			baseClass.stepInfo("Verify Persistant Hits With Doc View");
			docView.verifyPersistantHitsWithDocView(keywords);
			
			baseClass.stepInfo("Navigate to keyword page");
			keywordPage.navigateToKeywordPage();
			
			baseClass.stepInfo("Delete keyword");
			keywordPage.deleteKeywordByName(keyword);
		}
		
		/**
		 * @author Gopinath
		 * @TestCase Id : 51730 Verify that persistent hits should be highligted when documents are assigned to existing assignment from Saved Search > Doc List
		 * @Description : To Verify that persistent hits should be highligted when documents are assigned to existing assignment from Saved Search > Doc List              
		 * @throws InterruptedException 
		 */
		@Test(groups = { "regression" }, priority = 33)
		public void verifyPersistentHItAfterAssignedToExistingAssignment() throws InterruptedException {
			String BasicSearchName = Input.randomText + Utility.dynamicNameAppender();
			String AssignName = Input.randomText + Utility.dynamicNameAppender();
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51926");
			baseClass.stepInfo("#### Verify that when user is viewing a document in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList (to the user) ####");
			
			docView = new DocViewPage(driver);
			SessionSearch session = new SessionSearch(driver);
			SavedSearch savesearch=new SavedSearch(driver);
			AssignmentsPage assignmentPage = new AssignmentsPage(driver);
			DocListPage doclistpage = new DocListPage(driver);
			assignmentPage.createAssignment(AssignName, Input.codingFormName);
			baseClass.stepInfo("New Assignment created");
			
			session.basicContentSearch(Input.searchString2);
			baseClass.stepInfo("Basic Basic content search completed");
			
			session.saveSearch(BasicSearchName);
			baseClass.stepInfo("Basic search saved succssfully");
			
			savesearch.savedSearchToDocList(BasicSearchName);
			baseClass.stepInfo("Navigated to DocList Page");
			
			doclistpage.DoclisttobulkAssign(Input.randomText,"10");
			baseClass.stepInfo("All Doc list selected and navigated bulk assign");
			
			assignmentPage.assignDocstoExisting(AssignName);
			baseClass.stepInfo("Docs are assign to existing assignment");
			
			assignmentPage.editAssignment(AssignName);
			baseClass.stepInfo("Assignment selected and navigated to edit assignment page");
			
			assignmentPage.assignmentDistributingToReviewer();
			baseClass.stepInfo("Reviews added and distributed to Reviewer");
			
			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			assignmentPage.SelectAssignmentByReviewer(AssignName);
			
			docView.persistenHitWithSearchString(Input.searchString2);
			
		}
		
		
		/**
		 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
		 * @Testcase_id : 51117 -Verify user can download the redacted document when 'Allow reviewers to print docs to PDF' is on at an assigment level.
		 * @Description : Verify user can download the redacted document when 'Allow reviewers to print docs to PDF' is on at an assigment level.
		 */
		@Test(groups = { "regression" }, priority = 34)
		public void verifyDownloadDocumentByAllowingToPrintDocumentsAsPDF() throws InterruptedException {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51117 spint 09");
			ManageAssignment mngAssign = new ManageAssignment(driver);
			AssignmentsPage assgnPage = new AssignmentsPage(driver);
			DocViewMetaDataPage metaData = new DocViewMetaDataPage(driver);
			SessionSearch search = new SessionSearch(driver);
			final String assignStamp = Input.randomText + Utility.dynamicNameAppender();

			baseClass.stepInfo("#### Verify user can download the redacted document when 'Allow reviewers to print docs to PDF' is on at an assigment level. ####");

			baseClass.stepInfo("Basic Search");
			search.basicContentSearch(Input.searchString1);
			
			baseClass.stepInfo("Bulk Assign");
			search.bulkAssign();
			
			baseClass.stepInfo("Assignment Creation");
			assgnPage.assignmentCreation(assignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			assgnPage.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Disable print button toogle");
			mngAssign.disablePrintButton(false);
			
			baseClass.stepInfo("Assignment Distributing To Reviewer");
			assgnPage.assignmentDistributingToReviewer();

			loginPage.logout();
			baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

			// Login As Reviewer
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			// selecting the assignment
			baseClass.stepInfo("Selecting the assignment");
			assgnPage.SelectAssignmentByReviewer(assignStamp);
		
			docView = new DocViewPage(driver);
			
			baseClass.stepInfo("Click on persistant hit eye icon.");
			metaData.verifyDocumenetPrintOnDocView();
			
			baseClass.stepInfo("Navigate To Assignments Page");
			assgnPage.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Delete Assgnmnt Using Pagination");
			assgnPage.deleteAssignment(assignStamp);
			
		}
		

		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
		 * TestCase id :  51116 - Verify user can not download the native files when 'Allow reviewers to download natives' is off at an assigment level.
		 * Description : Verify user can not download the native files when 'Allow reviewers to download natives' is off at an assigment level
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 35)
		public void verifyNativeDownloadButtonDiabledWhenToogleOFF() throws Exception {		
			baseClass=new BaseClass(driver);
			String assignmentName = Input.randomText + Utility.dynamicNameAppender();
			baseClass.stepInfo("Test case Id: RPMXCON-51116 sprint 09");
			utility = new Utility(driver);
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo("#### Verify user can not download the native files when 'Allow reviewers to download natives' is off at an assigment level ####");
			
			ManageAssignment mngAssign = new ManageAssignment(driver);
			docView = new DocViewPage(driver);
			agnmt = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.searchText);
			
			baseClass.stepInfo("Bulk Assign");
			sessionSearch.bulkAssign();
			
			baseClass.stepInfo("Assignment Creation");
			agnmt.assignmentCreation(assignmentName, Input.codingFormName);
			
			baseClass.stepInfo("Toggle Coding Stamp Enabled");
			agnmt.toggleCodingStampEnabled();
			
			baseClass.stepInfo("Disable native download toogle");
			mngAssign.disableNativeDownloadButton(true);
		
			baseClass.stepInfo("Select assignment to view in Doc view");
			agnmt.selectAssignmentToViewinDocview(assignmentName);
			
			baseClass.stepInfo("Verify download button is not displayed.");
			docViewMetaDataPage.verifyingDownloadButtonIsNotDisplayed();
			
			baseClass.stepInfo("Navigate To Assignments Page");
			agnmt.navigateToAssignmentsPage();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Delete Assgnmnt Using Pagination");
			agnmt.deleteAssignment(assignmentName);
			
		}
		
		
		/**
		 * @author Gopinath
		 * @TestCase Id:51309 Verify persistent Hit panel of DocView should present only content terms,
		 *                   not operators when navigating from advance search
		 * @throws InterruptedException 
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 36)
		public void verifysearchTermsDisplayOnPHPanelWithoutOperator() throws InterruptedException {
			String operator="OR";
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51309");
			baseClass.stepInfo("#### Verify persistent Hit panel of DocView should present only content terms, not operators when navigating from advance searchVerify persistent Hit panel of DocView should present only content terms, not operators when navigating from advance search ####");
			
			docView = new DocViewPage(driver);
			SessionSearch session = new SessionSearch(driver);
			
			baseClass.stepInfo(" Advanced search with operator");
			session.advancedContentSearchWithOperator(Input.searchString1,operator,Input.testData1);
			
			baseClass.stepInfo("Navigating to  DocView page");
			session.ViewInDocView();
			
			baseClass.stepInfo("verifying search terms displayed on persistaent hit panel without operator or not");
			docView.persistenHitWithSearchStringWithOutOperator(Input.searchString1, Input.testData1,operator);
			
		}
		
		/**
		 * @author Gopinath
		 * TestCase Id:51120 Verify user can see the tiff images when 'Allow Production / Images view'.
		 * Description :To Verify user can see the tiff images when 'Allow Production / Images View' is on at an assigment level                  
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 36)
		public void verifyImageTabIsDisplayedAOnDocViewPanel() {
			
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51120");
			String AssignStamp = Input.randomText + Utility.dynamicNameAppender();

			baseClass.stepInfo("#### Verify user can see the tiff images when 'Allow Production / Images View' is on at an assigment level####");
					
			DocViewPage docView = new DocViewPage(driver);
			AssignmentsPage assignmentPage = new AssignmentsPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);

			baseClass.stepInfo("searching document for assignmnet creation");
			sessionSearch.basicContentSearch(Input.searchString2);

			baseClass.stepInfo("performing bulkAssign");
			sessionSearch.bulkAssign();

			baseClass.stepInfo("Creating assignment");
			assignmentPage.createAssignmentWithNativeDownload(AssignStamp, Input.codingFormName);
			
			baseClass.stepInfo("Select document to view in doc view");
			assignmentPage.selectAssignmentToViewinDocview(AssignStamp);
			
			baseClass.stepInfo("Verify image tab in doc view.");
			docView.verifyImageTabIsNotDisplayed();
		}
		
		@AfterMethod(alwaysRun = true)
		public void close() {
			try {
				loginPage.logout();
			} finally {
				loginPage.closeBrowser();
				LoginPage.clearBrowserCache();
			} 
		}
		
	     @AfterMethod(alwaysRun = true)
		 public void takeScreenShot(ITestResult result) {
	 	 if(ITestResult.FAILURE==result.getStatus()){
	 		 
	 		Utility bc = new Utility(driver);
	 		bc.screenShot(result);
	 		try{ //if any tc failed and dint logout!
	 		loginPage.logout();
	 		}catch (Exception e) {
				// TODO: handle exception
			}
	 	}
	 	 System.out.println("Executed :" + result.getMethod().getMethodName());
	 	
	     }
     
}

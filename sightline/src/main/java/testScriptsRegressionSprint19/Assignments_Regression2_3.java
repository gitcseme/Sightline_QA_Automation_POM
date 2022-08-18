package testScriptsRegressionSprint19;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignments_Regression2_3 {

	
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssertion;
	SecurityGroupsPage securityGroupsPage;
	TagsAndFoldersPage tagsAndFolderPage;
	DocViewRedactions docViewRedact;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

//		in = new Input();
//		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);

	}
	
	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}
	
	/**
	 * @author krishna
	 * @throws InterruptedException
	 * @Description :To Verify that on selecting Parent object then all its child object gets selected
	 * RPMXCON-53907
	 */
	
	
	 @Test(description = "RPMXCON-53907", enabled = true, groups = { "regression" })
		public void verifyAllChildSelectOnceSlctPrntGrp() throws Exception{
		   String assignmentName1 = "AR2Assignment" + Utility.dynamicNameAppender();	
		   String parentAssgnGrp = "Root";
		        
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Logged in as :"+ Input.rmu1userName);
			baseClass.stepInfo("Test case Id: RPMXCON-53907 Assignments");
			baseClass.stepInfo("To Verify that on selecting Parent object then all its child object gets selected");
				
			assignPage.createAssignment(assignmentName1, Input.codeFormName);
			sessionSearch.basicContentSearch(Input.searchString1);
			baseClass.waitForElement(sessionSearch.getDocsMetYourCriteriaLabel());
			baseClass.dragAndDrop(sessionSearch.getDocsMetYourCriteriaLabel(), sessionSearch.getActionPad());  
		    driver.waitForPageToBeReady();
		    sessionSearch.bulkAssign();
		    
		    // Select An Assignment in Assign/Unassign Popup
		    baseClass.waitForElement(sessionSearch.getSelectAssignmentExisting(assignmentName1));
		    sessionSearch.getSelectAssignmentExisting(assignmentName1).Click(); 
		    
		    //Verifying Only Selected Assignment gets Selected
		    baseClass.waitForElement(sessionSearch.getSelectedExistingAssignments());
		   if(sessionSearch.getSelectedExistingAssignments().isElementAvailable(6)) {
			   String assgn = sessionSearch.getSelectedExistingAssignments().getText();
			   if(assgn.equalsIgnoreCase(assignmentName1)) {
		    		baseClass.passedStep("selected Assignment gets selected");
		    	}else{
		    		baseClass.failedStep("Not Selected Assignment Gets Selected");
		    	}
		   }else{
			   baseClass.failedStep("No Assignment Selected in Popup");
		   }
		   	    	
		    // DeSelect the Assignment from Assign/UnAssign PopUp
		    baseClass.waitForElement(sessionSearch.getSelectAssignmentExisting(assignmentName1));
		    sessionSearch.getSelectAssignmentExisting(assignmentName1).Click();    
		    
		    //Verifying All its downline objects get selected (Assignment Group and Assignment) Once Parent Grp Selected
		    baseClass.waitForElement(sessionSearch.getSelectAssignmentExisting(parentAssgnGrp));
		    sessionSearch.getSelectAssignmentExisting(parentAssgnGrp).Click();		    
		    baseClass.waitForElementCollection(sessionSearch.getassign_ExistingAssignments());	    
		    for(WebElement ele : sessionSearch.getassign_ExistingAssignments().FindWebElements()){
		    	String status = ele.getAttribute("aria-selected");
		    	if(status.equalsIgnoreCase("false")) {
		    		baseClass.failedStep("Not All its downline objects get selected (Assignment Group and Assignment).");
		    	}}
		    baseClass.stepInfo("All its downline objects get selected (Assignment Group and Assignment).");
		    
		  //Verifying All its downline objects get Deselected (Assignment Group and Assignment) Once Parent Grp DeSelected
		    baseClass.waitForElement(sessionSearch.getSelectAssignmentExisting(parentAssgnGrp));
		    sessionSearch.getSelectAssignmentExisting(parentAssgnGrp).Click();
		    baseClass.waitForElementCollection(sessionSearch.getassign_ExistingAssignments());	    
		    for(WebElement ele : sessionSearch.getassign_ExistingAssignments().FindWebElements()){
		    	String status = ele.getAttribute("aria-selected");
		    	if(status.equalsIgnoreCase("true")) {
		    		baseClass.failedStep("Not All its downline objects get deselected (Assignment Group and Assignment).");	    		
		    	}}
		    baseClass.stepInfo("All its downline objects get deselected (Assignment Group and Assignment).");	
		    
		    baseClass.passedStep("Verified - that on selecting Parent object then all its child object gets selected.");
		    assignPage.deleteAssgnmntUsingPagination(assignmentName1);
		    loginPage.logout();		    
	   }
	 
	 /**
		 * @author krishna
		 * @throws InterruptedException
		 * @Description :To verify that RMU can perform Persistent Search Hits from saved search
		 * RPMXCON-53948
		 */
	   
	   @Test(description = "RPMXCON-53948", enabled = true, groups = { "regression" })
		public void verifyRMUPerformPersisSearchHitSS() throws Exception{
		   
		   String assignmentName1 = "AR2Assignment" + Utility.dynamicNameAppender();	
		   String searchName = "Search" + Utility.dynamicNameAppender();
		   SavedSearch savedSearch = new SavedSearch(driver);
		   
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Logged in as :"+ Input.rmu1userName);
			baseClass.stepInfo("Test case Id: RPMXCON-53948.");
			baseClass.stepInfo("To verify that RMU can perform Persistent Search Hits from saved search");
			assignPage.createAssignment(assignmentName1, Input.codeFormName);			

			savedSearch.navigateToSavedSearchPage();
		    String Node = savedSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.saveSearchInNewNode(searchName, Node);
			
			savedSearch.navigateToSavedSearchPage();
			savedSearch.selectRootGroupTab(Input.mySavedSearch);
			savedSearch.selectNode1(Node);
			savedSearch.savedSearch_SearchandSelect(searchName, Input.yesButton);
			
			baseClass.waitForElement(savedSearch.getSavedSearchToBulkAssign());
			savedSearch.getSavedSearchToBulkAssign().Click();
			
			assignPage.assignwithSamplemethod(assignmentName1, "Percentage", "10");
			assignPage.navigateToAssignmentsPage();
			assignPage.SelectAssignmentToViewinDocview(assignmentName1);
			
			DocViewPage docView = new DocViewPage(driver);
			docView.clickOnPersistantHitEyeIcon();
			String highlighTxt = docView.getPersistentHitWithoutClickingEyeIcon(Input.searchString1);
			System.out.println(highlighTxt);
			
			if(highlighTxt.equalsIgnoreCase(Input.searchString1)) {
				baseClass.passedStep("Text which is enter while performing search that text gets highlighted.");
			}else {
				baseClass.failedStep("Text which is enter while performing search that text Not gets highlighted.");
			}
			baseClass.passedStep("Verified -  that search text should get highlighted in Doc View");
			assignPage.deleteAssgnmntUsingPagination(assignmentName1);
			savedSearch.deleteNode(Input.mySavedSearch, Node);
			baseClass.passedStep("verified - that RMU can perform Persistent Search Hits from saved search");
			loginPage.logout();
	   }
	   
	   /**
		 * @author krishna
		 * @throws InterruptedException
		 * @Description :Verify that Application disallow special characters in Edit Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.
		 * RPMXCON-54440
		 */
	   
	   @Test(description = "RPMXCON-54440", groups = { "regression" })
		public void verifyErrorMsgForSpclCharEditAssignScr() throws InterruptedException {
		   String assignmentName1 = "AR2Assignment" + Utility.dynamicNameAppender();
		   BaseClass baseClass = new BaseClass(driver);
		   
			String dataSet[][] = { { "AssignmentNameWith<test" }, { "AssignmentNameWith(test" },
					{ "AssignmentNameWith)test" }, { "AssignmentNameWith[test" }, { "AssignmentNameWith]test" },
					{ "AssignmentNameWith{test" }, { "AssignmentNameWith}test" }, { "AssignmentNameWith:test" },
					{ "AssignmentNameWith\"test" }, { "AssignmentNameWith'test" }, { "AssignmentNameWith~test" },
					{ "AssignmentNameWith*test" }, { "AssignmentNameWith?test" }, { "AssignmentNameWith&test" },
					{ "AssignmentNameWith$test" }, { "AssignmentNameWith#test" }, { "AssignmentNameWith@test" },
					{ "AssignmentNameWith!test" }, { "AssignmentNameWith-test" }, { "AssignmentNameWith_test" } };
			
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
			baseClass.stepInfo("Test case Id: RPMXCON-54440");
			baseClass.stepInfo(
					"Verify that Application disallow special characters in Edit Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
			assignPage.createAssignment(assignmentName1, Input.codeFormName);
			assignPage.editAssignmentUsingPaginationConcept(assignmentName1);
			for (int i = 0; i < dataSet.length; i++) {
				int j = 0;

				String renameAssign = dataSet[i][j];			
				baseClass.waitForElement(assignPage.getAssignmentName());
				assignPage.getAssignmentName().Clear();
				baseClass.copyandPasteString(renameAssign, assignPage.getAssignmentName());
				assignPage.getAssignmentSaveButton().waitAndClick(5);
				assignPage.verifyErrorMsginAssignmentName();
			}
			assignPage.deleteAssgnmntUsingPagination(assignmentName1);
			baseClass.passedStep(
					"Verified - that Application disallow special characters in Edit Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
			loginPage.logout();
		}
	   
	   /**
		 * @author krishna
		 * @throws InterruptedException
		 * @Description :To check that when user clicks on \"Cancel\" button from the "
		   		+ "\"Add/remove coding form in this Assignment\" pop-up the PopUp should get cancelled
		 * RPMXCON-65131
		 */
	   @Test(description = "RPMXCON-65131", groups = { "regression" })
		public void verifyPopupCancelBtnSortCoding() throws InterruptedException {	  
		   CodingForm cf = new CodingForm(driver);
		   
		   loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		   baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
		   baseClass.stepInfo("Test case Id: RPMXCON-65131");
		   baseClass.stepInfo("To check that when user clicks on \"Cancel\" button from the "
		   		+ "\"Add/remove coding form in this Assignment\" pop-up the PopUp should get cancelled");
					
		   assignPage.navigateToAssignmentsPage();
		   assignPage.selectAssignmentGroup("Root");
		   assignPage.NavigateToNewEditAssignmentPage("New");
		   
		   // Verifying Select and sort Coding form button present in New Assignment Page
		   if(assignPage.getSelectSortCodingForm_Tab().isElementAvailable(4)) {
		   baseClass.passedStep("Select and sort Coding form button present in New Assignment Page");
		   assignPage.getSelectSortCodingForm_Tab().ScrollTo();
		   assignPage.getSelectSortCodingForm_Tab().waitAndClick(10);	
		   baseClass.stepInfo("Select and sort Coding form button Clicked");
		   }else {
		   baseClass.failedStep("Select and sort Coding form button Not present in New Assignment Page");
		   }
		   
		   //Verifying Add / Remove Coding Forms in this Assignment Pop Up displaying or not
		   if(assignPage.SelectCFPopUp_Step1().Visible()) {
		   baseClass.passedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up displaying.");
		   }else {
		   baseClass.failedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up Not displaying.");
		   }
		   
		   baseClass.waitForElement(assignPage.getCFCancelBtn());
		   assignPage.getCFCancelBtn().Click();
		   baseClass.stepInfo("CLicked Cancel button in Popup");
		   
		   //Verifying Add / Remove Coding Forms in this Assignment Pop Up displaying or not
		   if(assignPage.SelectCFPopUp_Step1().Visible()) {
		   baseClass.failedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up displaying.");
		   }else {
		   baseClass.passedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up Not displaying.");
		   }
		   
		   driver.getWebDriver().get(Input.url + "CodingForm/Create");
		   baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "Successfully Navigated to Manage Coding Form Page", "Not Navigated to Manage Coding Form Page");
		   
		// Verifying Select and sort Coding form button present in Manage Coding Form Page
		   if(cf.getSetCodingFormToSG().isElementAvailable(4)) {
		   baseClass.passedStep("Select and sort Coding form button present in Manage Coding Form Page");
		   cf.getSetCodingFormToSG().ScrollTo();
		   cf.getSetCodingFormToSG().waitAndClick(10);	
		   baseClass.stepInfo("Select and sort Coding form button Clicked");
		   }else {
		   baseClass.failedStep("Select and sort Coding form button Not present in Manage Coding Form Page");
		   }
		   
		   //Verifying Add / Remove Coding Forms in this Assignment Pop Up displaying or not in Manage Coding Form Page
		   if(cf.getStep1CfPopUp().Visible()) {
		   baseClass.passedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up displaying.");
		   }else {
		   baseClass.failedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up Not displaying.");
		   }
			   
		   baseClass.waitForElement(cf.getCfPopUpCancel());
		   cf.getCfPopUpCancel().Click();
		   baseClass.stepInfo("CLicked Cancel button in Popup");
		   
		 //Verifying Add / Remove Coding Forms in this Assignment Pop Up displaying or not in Manage Coding Form Page
		   if(cf.getStep1CfPopUp().Visible()) {
		   baseClass.failedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up displaying.");
		   }else {
		   baseClass.passedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up Not displaying.");
		   }
		   baseClass.passedStep("To check that when user clicks on \"Cancel\" button from the "
		   		+ "\"Add/remove coding form in this Assignment\" pop-up the PopUp should get cancelled");
		   
		   loginPage.logout();
	   }
	   /**
		 * @author jayanthi
		 * @throws InterruptedException
		 * @Description :To verify that RMU can unassign the documents from Assignments for Saved Search.
		 */
		@Test(description = "RPMXCON-53644", enabled = true, groups = { "regression" })
		public void verifyUNassignedDocsCount_savedsearch() throws InterruptedException {

			baseClass.stepInfo("Test case Id: RPMXCON-53644");
			baseClass.stepInfo("To verify that RMU can unassign the documents from Assignments for Saved Search.");
			String assgnName = "Assgn" + Utility.dynamicNameAppender();
			String searchName = "searchAssgn" + Utility.dynamicNameAppender();
			// Login as Reviewer Manager
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("**Pre Req-Creating saved search and creating/assigning docs to Assignment**");
			
			// performing basic search
			sessionSearch.basicContentSearch(Input.searchString1);
		    sessionSearch.returnPurehitCount();
			sessionSearch.saveSearch(searchName);
			baseClass.stepInfo("Saved the search with name "+searchName);
			
			SavedSearch savedsearch=new  SavedSearch(driver);
			savedsearch.savedSearch_Searchandclick(searchName);
			savedsearch.getSavedSearchToBulkAssign().waitAndClick(10);
			sessionSearch.bulkAssign();
			assignPage.FinalizeAssignmentAfterBulkAssign();
			assignPage.createAssignment_fromAssignUnassignPopup(assgnName, Input.codeFormName);
			assignPage.getAssignmentSaveButton().waitAndClick(5);
			baseClass.passedStep(
					"Assignment is created and docs assigned from saved search with name -" +assgnName );
			baseClass.stepInfo("Un Assign The docs from assingment "+assgnName+" from saved search page.");
			savedsearch.savedSearch_Searchandclick(searchName);
			savedsearch.getSavedSearchToBulkAssign().waitAndClick(10);
			sessionSearch.UnAssignExistingAssignment(assgnName);
			
			int ActualDocCount;
			ActualDocCount = Integer.parseInt(assignPage.verifydocsCountInAssgnPage(assgnName));
			baseClass.digitCompareEquals(ActualDocCount, 0, "Assigned Docs Count is zero for assignment- " + assgnName,
					"Assigned DocsCount is not zero");
			baseClass.passedStep("Sucessfully verified doc counts un-assigned in Assignment " + assgnName);
			assignPage.deleteAssgnmntUsingPagination(assgnName);
			savedsearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
			// logout
			loginPage.logout();
		}
		/**
		 * @author Iyappan.Kasinathan
		 * @description Verify the Default live sequence while creating the new assignment
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-49086",enabled = true, groups = { "regression" })
		public void verifyDefaultLiveSequenceInNewAssignment() throws InterruptedException {
			baseClass.stepInfo("Verify the Default live sequence while creating the new assignment");
			baseClass.stepInfo("Test case Id:RPMXCON-49086");
			String assignmentName = "assgn" + Utility.dynamicNameAppender();
			SoftAssert sa = new SoftAssert();
			List<String> expectedLiveSequenceOrder = new ArrayList<>();
			List<String> actualLiveSequenceOrder = new ArrayList<>();
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			assignPage.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		    expectedLiveSequenceOrder =baseClass.getAvailableListofElements(assignPage.getLiveSequenceMetadatas());
			baseClass.waitTillElemetToBeClickable(assignPage.SaveButton());
			assignPage.SaveButton().waitAndClick(10);
			assignPage.editAssignmentUsingPaginationConcept(assignmentName);
			actualLiveSequenceOrder =baseClass.getAvailableListofElements(assignPage.getLiveSequenceMetadatas());
			sa.assertEquals(expectedLiveSequenceOrder, actualLiveSequenceOrder);
			sa.assertAll();
			baseClass.passedStep("The order of live sequence are selected during creation of new assignment is reflected in edit assignment successfully");
			assignPage.deleteAssgnmntUsingPagination(assignmentName);
			loginPage.logout();
		}
		/**
		 * @author Iyappan.Kasinathan
		 * @description To verify that if there is no documents in Assignments then instead of draw from pool link it will display zero
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-53886",enabled = true, groups = { "regression" })
		public void verifyDrawPoolLinkIsNotDisplayed() throws InterruptedException {
			baseClass.stepInfo("To verify that if there is no documents in Assignments then instead of draw from pool link it will display zero");
			baseClass.stepInfo("Test case Id:RPMXCON-53886");
			String assignmentName = "assgn" + Utility.dynamicNameAppender();
			SoftAssert sa = new SoftAssert();
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			assignPage.createAssignment(assignmentName, Input.codeFormName);
			assignPage.editAssignmentUsingPaginationConcept(assignmentName);
			driver.waitForPageToBeReady();
			baseClass.waitForElement(assignPage.getAssignment_ManageReviewersTab());
			baseClass.waitTillElemetToBeClickable(assignPage.getAssignment_ManageReviewersTab());
			assignPage.getAssignment_ManageReviewersTab().waitAndClick(10);
			baseClass.waitForElement(assignPage.getAddReviewersBtn());
			baseClass.waitTillElemetToBeClickable(assignPage.getAddReviewersBtn());
			assignPage.getAddReviewersBtn().waitAndClick(10);
			baseClass.waitForElement(assignPage.getSelectUserToAssig());
			assignPage.getSelectUserToAssig().WaitUntilPresent().ScrollTo();
			baseClass.waitTillElemetToBeClickable(assignPage.getSelectUserToAssig());
			assignPage.getSelectUserToAssig().waitAndClick(10);
			baseClass.waitForElement(assignPage.getAdduserBtn());
			baseClass.waitTillElemetToBeClickable(assignPage.getAdduserBtn());
			assignPage.getAdduserBtn().waitAndClick(10);
			loginPage.logout();
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			assignPage.verifyDrawPoolToggledisplay(assignmentName, "disabled");
			baseClass.waitForElement(assignPage.getTotalDocsCountInReviewerDashboard(assignmentName));
			String ActualDocs_value = assignPage.getTotalDocsCountInReviewerDashboard(assignmentName).getText().trim();
			String TotslDocs = ActualDocs_value.substring(6,9).trim();
			sa.assertEquals("0", TotslDocs);
			sa.assertAll();
			baseClass.passedStep("Total documents are 0 and no draw pool link is displayed when no documents are assigned to an assignment");
			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			assignPage.deleteAssgnmntUsingPagination(assignmentName);
			loginPage.logout();
		}
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  Assignments_Regression2_2 .**");
	}

}

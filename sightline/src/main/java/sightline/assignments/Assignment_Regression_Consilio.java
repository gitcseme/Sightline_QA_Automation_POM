package sightline.assignments;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ClassificationPage;
import pageFactory.ClientsPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Regression_Consilio {

	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	AssignmentsPage agnmt;
	Input in;
	IngestionPage_Indium ingestionPage;
	ClientsPage clientsPage;
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		agnmt = new AssignmentsPage(driver);
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
		search = new SessionSearch(driver);
		clientsPage = new ClientsPage(driver);
		

	}
	
	@Test(description ="RPMXCON-54960",groups = { "regression" })
	public void validateHelpPopUpWhenHoveringInClientPg() throws InterruptedException, ParseException, IOException {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		clientsPage = new ClientsPage(driver);
		bc.stepInfo("Test case Id: RPMXCON-54960");
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.stepInfo("Logged in as SA user");
		clientsPage.verifyHelpTextPopUpWhenHovering();
		lp.logout();
	}

	@Test(description ="RPMXCON-54958,RPMXCON-54959",groups = { "regression" })
	public void verifyHelpTextPopUpWhenClickingInClientPg() throws InterruptedException, ParseException, IOException {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		clientsPage = new ClientsPage(driver);
		bc.stepInfo("Test case Id: RPMXCON-54958, RPMXCON-54959");
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.stepInfo("Logged in as SA user");
		clientsPage.verifyHelpTextPopUpWhenClicking();
		lp.logout();
	}
	
	@Test(description = "RPMXCON-69084",dataProvider="AssignmentSplChars", groups = { "regression" })
	public void verifyCreateNEditAssignmentGrpWithSplChars(String AssignmentGrpNameSplChars) throws InterruptedException {
			bc.stepInfo("Verify that error message display and application does NOT accepts - when user add & Edits Assignment group with special characters < > & ‘");
			bc.stepInfo("Test case Id:RPMXCON-69084");	
			lp.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			String AssignmentGrpName="AssignmentGrp"+ Utility.dynamicNameAppender();
			this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			System.out.println(AssignmentGrpName);
			
			agnmt.createAssgnGroup(AssignmentGrpName);
			
			//create Assignment group name with special characters

			agnmt.createAssgnGroupWithSplChars(AssignmentGrpNameSplChars);	
						
			//edit assignment group name with special chars
			
			agnmt.EditAssgnGroupSplChars(AssignmentGrpName);
			
			
	}
	
	@Test(description = "RPMXCON-69085",dataProvider="AssignmentSplChars", groups = { "regression" })
	public void verifyCreateNEditAssignmentNameWithSplChars(String AssignmentNameSplChars) throws InterruptedException {
			bc.stepInfo("Verify that error message display and application does NOT accepts - when user add & Edits Assignment name with special characters < > & ‘ ");
			bc.stepInfo("Test case Id:RPMXCON-69085");	
			lp.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			String AssignmentName="AssignmentName"+ Utility.dynamicNameAppender();	
			
			agnmt.createAssignment(AssignmentName, Input.codingFormName);
			
			//create Assignment name with special characters
			
			agnmt.createAssignmentWithSplChars(AssignmentNameSplChars);			
			
			//edit assignment name with special characterss		
		
			agnmt.editAssignmentWithSplChars(AssignmentName);

			
	}
	
	@Test(description = "RPMXCON-69087", enabled = true, groups = { "regression" })
	public void verifyRMUCanEnterSplCharsInClassificationField() throws Exception {
		bc.stepInfo("Verify that error message display and application does NOT accepts - when user add names in manage classification with special characters < > & ‘ ");
		bc.stepInfo("Test case Id:RPMXCON-69087");
		String maxQualified = "2";
		LoginPage lp = new LoginPage(driver);
		ClassificationPage clssp = new ClassificationPage(driver);
		Assertion a = new Assertion();
		lp.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		bc.stepInfo("Logged in as :" + Input.rmu2userName);

		bc.selectproject();
		
		clssp.navigateClassificationPage();
		
		bc.passedStep("Navigated to classification page");
		
		clssp.getMaxQualified().selectFromDropdown().selectByValue(maxQualified);
		int size=Integer.parseInt(maxQualified);
		for(int i=1;i<=size;i++) {
			String name=i+"LR<'>&";
			String RateVal="5<'>&";
			clssp.updateLevelClassificDetailsNotSave(i, name, "$ - USD", RateVal);
			String errorclassName=clssp.getClassificationNameQCErrormsg().getText();
			System.out.println("errorclassName :- "+errorclassName);
			a.assertEquals(errorclassName, "You cannot specify any special characters in the field value");
			String errorClassVal=clssp.getClassificationRatevalueErrormsgQC().getText();
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("errorClassVal :- "+errorClassVal);
			a.assertEquals(errorClassVal, "The currency value specified is invalid.");
			
		}
		clssp.updateQCClassificDetailsNotSave("3LR<'>&", "$ - USD", "15<'>&");
		String errorclassName=clssp.getClassificationNameQCErrormsg().getText();
		a.assertEquals(errorclassName, "You cannot specify any special characters in the field value");
		String errorClassVal=clssp.getClassificationRatevalueErrormsgQC().getText();
		a.assertEquals(errorClassVal, "The currency value specified is invalid.");
		bc.passedStep("Entered classification fields with special characters and it failed to accept");
}

	@Test(description = "RPMXCON-68793", enabled = true, groups = { "regression" })
	public void verifyEmailThreadEnabledSortByCustodian() throws Exception {
		
		bc.stepInfo("Verify that when Email Threads Together is enabled, presentation sequence with Sort by metadata as Custodian, the draw will keep the entire email thread together irrespective of the batch.");
		bc.stepInfo("Test case Id:RPMXCON-68793");
		String assignmentName = "ThreadedAssignment" + Utility.dynamicNameAppender();
		LoginPage lp = new LoginPage(driver);
		AssignmentsPage asgn=new AssignmentsPage(driver);
		SessionSearch search= new SessionSearch(driver);
		String[][] userRolesData = { { Input.pa2userName, "Project Administrator", "SA" } };
		lp.loginToSightLine(Input.pa2userName, Input.pa2password);
		
		String SearchName1 = "emailConcat" + Utility.dynamicNameAppender();
		String ingestionDataName=Input.IngestionName_PT;
		ingestionPage = new IngestionPage_Indium(driver);
		UserManagement userManagement=new UserManagement(driver);
		userManagement.verifyIngestionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa2password);
		ingestionPage.navigateToIngestionPage();
		boolean status = ingestionPage.verifyIngestionpublish(Input.EmailConcatenatedDataFolder);
		 if (status == true) {
	            ingestionDataName=ingestionPage.getPublishedIngestionName(Input.EmailConcatenatedDataFolder);
	        }
		search.metadataSearchesUsingOperators(Input.metadataIngestion, ingestionDataName, "AND", "EmailThreadID",
				Input.sameThreadDocs_EmailThreadID, true);
		int count = search.serarchWP();
		search.saveSearchAtAnyRootGroup(SearchName1, Input.shareSearchDefaultSG);
		bc.stepInfo("Created a SavedSearch " + SearchName1);
		lp.logout();
		// assignment Creation with draw pool toggle OFF
		lp.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		SavedSearch savedSearch = new SavedSearch(driver);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		savedSearch.savedSearch_SearchandSelect(SearchName1, "Yes");
		savedSearch.getSavedSearchToBulkAssign().ScrollTo();
		savedSearch.getSavedSearchToBulkAssign().Click();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.waitForPageToBeReady();
		// Draw pool Toggle Disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created a Assignment " + assignmentName + " with draw pool toggle disabled");

		// Edit Assignment
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.Assgnwithdocumentsequence("CustodianName", "Descending");
		// Draw pool Toggle Enable and making draw limit as 20
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// email Thread Toggles enable
		agnmt.getKeepEmailThreadTogether_Text().ScrollTo();
		String emailThread = agnmt.getKeepEmailThreadTogether_Text().getText();
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgn_keepEmailThreadTogetherToggle(),
				emailThread, false);
		// Keep family Toggles disable
		String familyMem = agnmt.getKeepFamilyTogetther_Text().getText();
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgn_keepFamiliesTogetherToggle(), familyMem,
				false);
		driver.scrollPageToTop();
		// distributing docs
		agnmt.distributeTheGivenDocCountToReviewer("2",Input.rev2userName);
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);

		lp.logout();
		lp.loginToSightLine(Input.rev2userName, Input.rev2password);
		bc.stepInfo("Logged in as reviewer user");
		// navigating from Dashboard to DocView
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.getDashboardButton().waitAndClick(5);
		bc.waitForElement(agnmt.getAssignmentsInreviewerPg());
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		bc.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName));
		// Validation as per test step-7.Verification of draw limit.
		if (agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName).isElementPresent() == true) {
			bc.passedStep("Draw pool link is displayed");
			bc.waitTillElemetToBeClickable(agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName));
			driver.scrollingToElementofAPage(agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName));
			agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName).waitAndClick(5);
			driver.Navigate().refresh();
			bc.waitForElement(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName));
			String docCountInTODO = agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText();
			if (Integer.parseInt(docCountInTODO) == count) {
				bc.stepInfo(
						"Doc Count displayed in ToDo Column after  clicking Draw Pool link is " + docCountInTODO);
				bc.passedStep(
						"Sucessfully Verified  draw from pool after editing the assignment with keep email thread "
								+ "toggles ON and keep families togle OFF.");

			} else {
				bc.failedStep("Doc Count displayed in ToDo Column after  clicking Draw Pool link is "
						+ docCountInTODO + " Which is not expected.");

			}
		} else {
			bc.failedStep("Draw pool link is not displayed");
		}
		//verifying Docs in assignment is sorted as per metadata selected
		try {
			docViewPage.getDashboardButton().waitAndClick(5);
			agnmt.verifyDescendingMetaDataSorting_DocList(assignmentName, Input.metaDataName);
			bc.passedStep("sucessfully verified that whether RMU can create Assignments from assign/unassign documents"
					+ "with descending meta data sorting");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occured,while verifying  whether RMU can create Assignments from assign/unassign documents"
							+ "with descending meta data sorting");

		}
	}

		@Test(description = "RPMXCON-68803", enabled = true, groups = { "regression" })
		public void verifyFamilyMemberNEmailThreadEnabledSortByCustodian() throws Exception {
			
			bc.stepInfo("Verify that when Email Threads Together is enabled, presentation sequence with Sort by metadata as Custodian, the draw will keep the entire email thread together irrespective of the batch.");
			bc.stepInfo("Test case Id:RPMXCON-68793");
			String assignmentName = "ThreadedAssignment" + Utility.dynamicNameAppender();
			LoginPage lp = new LoginPage(driver);
			AssignmentsPage asgn=new AssignmentsPage(driver);
			SessionSearch search= new SessionSearch(driver);
			String[][] userRolesData = { { Input.pa2userName, "Project Administrator", "SA" } };
			lp.loginToSightLine(Input.pa2userName, Input.pa2password);
			
			String SearchName1 = "emailConcat" + Utility.dynamicNameAppender();
			String ingestionDataName=Input.IngestionName_PT;
			ingestionPage = new IngestionPage_Indium(driver);
			UserManagement userManagement=new UserManagement(driver);
			userManagement.verifyIngestionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa2password);
			ingestionPage.navigateToIngestionPage();
			boolean status = ingestionPage.verifyIngestionpublish(Input.EmailConcatenatedDataFolder);
			 if (status == true) {
		            ingestionDataName=ingestionPage.getPublishedIngestionName(Input.EmailConcatenatedDataFolder);
		        }
			search.metadataSearchesUsingOperators(Input.metadataIngestion, ingestionDataName, "AND", "EmailThreadID",
					Input.sameThreadDocs_EmailThreadID, true);
			int count = search.serarchWP();
			search.saveSearchAtAnyRootGroup(SearchName1, Input.shareSearchDefaultSG);
			bc.stepInfo("Created a SavedSearch " + SearchName1);
			lp.logout();
			// assignment Creation with draw pool toggle OFF
			lp.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			SavedSearch savedSearch = new SavedSearch(driver);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
			savedSearch.savedSearch_SearchandSelect(SearchName1, "Yes");
			savedSearch.getSavedSearchToBulkAssign().ScrollTo();
			savedSearch.getSavedSearchToBulkAssign().Click();
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
			driver.waitForPageToBeReady();
			// Draw pool Toggle Disable
			agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
					"Draw From Pool", false);
			driver.scrollPageToTop();
			bc.waitForElement(agnmt.getAssignmentSaveButton());
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			bc.stepInfo("Created a Assignment " + assignmentName + " with draw pool toggle disabled");

			// Edit Assignment
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);
			agnmt.Assgnwithdocumentsequence("CustodianName", "Descending");
			// Draw pool Toggle Enable and making draw limit as 20
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);
			agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
					"Draw From Pool", false);
			agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
			// email Thread Toggles enable
			agnmt.getKeepEmailThreadTogether_Text().ScrollTo();
			String emailThread = agnmt.getKeepEmailThreadTogether_Text().getText();
			agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgn_keepEmailThreadTogetherToggle(),
					emailThread, false);
			// Keep family Toggles disable
			String familyMem = agnmt.getKeepFamilyTogetther_Text().getText();
			agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgn_keepFamiliesTogetherToggle(), familyMem,
					false);
			driver.scrollPageToTop();
			// distributing docs
			agnmt.distributeTheGivenDocCountToReviewer("2");
			driver.scrollPageToTop();
			bc.waitForElement(agnmt.getAssignmentSaveButton());
			agnmt.getAssignmentSaveButton().waitAndClick(5);

			lp.logout();
			lp.loginToSightLine(Input.rev2userName, Input.rev2password);
			bc.stepInfo("Logged in as reviewer user");
			// navigating from Dashboard to DocView
			DocViewPage docViewPage = new DocViewPage(driver);
			docViewPage.getDashboardButton().waitAndClick(5);
			bc.waitForElement(agnmt.getAssignmentsInreviewerPg());
			agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
			bc.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName));
			// Validation as per test step-7.Verification of draw limit.
			if (agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName).isElementPresent() == true) {
				bc.passedStep("Draw pool link is displayed");
				bc.waitTillElemetToBeClickable(agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName));
				driver.scrollingToElementofAPage(agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName));
				agnmt.getAssignmentsDrawPoolInreviewerPg1(assignmentName).waitAndClick(5);
				driver.Navigate().refresh();
				bc.waitForElement(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName));
				String docCountInTODO = agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText();
				if (Integer.parseInt(docCountInTODO) == count) {
					bc.stepInfo(
							"Doc Count displayed in ToDo Column after  clicking Draw Pool link is " + docCountInTODO);
					bc.passedStep(
							"Sucessfully Verified  draw from pool after editing the assignment with keep email thread "
									+ "toggles ON and keep families togle OFF.");

				} else {
					bc.failedStep("Doc Count displayed in ToDo Column after  clicking Draw Pool link is "
							+ docCountInTODO + " Which is not expected.");

				}
			} else {
				bc.failedStep("Draw pool link is not displayed");
			}
			//verifying Docs in assignment is sorted as per metadata selected
			try {
				docViewPage.getDashboardButton().waitAndClick(5);
				agnmt.verifyDescendingMetaDataSorting_DocList(assignmentName, Input.metaDataName);
				bc.passedStep("sucessfully verified that whether RMU can create Assignments from assign/unassign documents"
						+ "with descending meta data sorting");
			} catch (Exception e) {
				e.printStackTrace();
				bc.failedStep(
						"Exception occured,while verifying  whether RMU can create Assignments from assign/unassign documents"
								+ "with descending meta data sorting");

			}
		
}
	
	
	@DataProvider(name = "AssignmentSplChars")
	public Object[][] AssignmentSplChars() {
		Object[][] users = { { "Assignment<'>&" } };
		return users;
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
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  QuickBatchRegression2_1 Regression5**");
	}
}

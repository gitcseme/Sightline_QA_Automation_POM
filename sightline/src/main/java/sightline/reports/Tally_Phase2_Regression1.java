package sightline.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Tally_Phase2_Regression1 {
	Driver driver;
	LoginPage lp;
	BaseClass bc;
	TallyPage tp;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@Test(description = "RPMXCON-56215", dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyTally_WorkProduct(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56215");
		bc.stepInfo("To Verify Proper Notification for Not Selecting Any Value from Tally BY in Tally");

		lp.loginToSightLine(username, password);

		String[] tallyBy = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };

		// iterating this for loop to change the tally by metadata value
		for (int i = 0; i < tallyBy.length; i++) {
			bc.stepInfo("**Navigating to tally Page and Selecting " + tallyBy[i] + " as tally by option.**");
			tp.navigateTo_Tallypage();
			tp.selectTallyByMetaDataField(tallyBy[i]);
			bc.VerifyErrorMessage("Please select a source to run the report");

			bc.passedStep("if we select tally by option as " + tallyBy[i] + " with out seelcting any source"
					+ "we wil get the error message as expected.");

		}
	}

	/**
	 *   @author Jayanthi.ganesan     @throws InterruptedException      
	 */

	@Test(description = "RPMXCON-56216", groups = { "regression" })
	public void verifyErrorMsg_BulkAssign() throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56216");
		bc.stepInfo("To Verify Proper Notification for Not Selecting Any documents and "
				+ "performing Action \"Assign\" in Tally");
		
		String SearchName = "Tally" + Utility.dynamicNameAppender();
		String assgnName = "Tally" + Utility.dynamicNameAppender();
		String folderName = "Tally" + Utility.dynamicNameAppender();

		String[] sourceName_RMU = { assgnName, folderName, SearchName, "Default Security Group" };

		String[] tallyBy = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch ss = new SessionSearch(driver);
		TagsAndFoldersPage tfPage = new TagsAndFoldersPage(driver);

		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as RMU.");

		bc.stepInfo("**Pre Requisite Creation [folders/searches/assignment].**");
		
		ss.basicContentSearch(Input.TallySearch);

		ss.saveSearchAtAnyRootGroup(SearchName, Input.mySavedSearch);

		ss.bulkFolder(folderName);

		ss.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assgnName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created a assignment " + assgnName);
		driver.waitForPageToBeReady();
		
		bc.stepInfo("**PreRequisite Creation Ended.**");

// iterating this for loop to change the sources for every iteration of loop
		for (int k = 0; k < sourceName_RMU.length; k++) {
			
			// iterating this for loop to change the tally by option for every iteration of loop
			for (int i = 0; i < tallyBy.length; i++) {
				tp.navigateTo_Tallypage();
				tp.sourceSelectionUsers("RMU", sourceName_RMU, k);
				tp.selectTallyByMetaDataField(tallyBy[i]);
				tp.verifyTallyChart();
				tp.getTally_tallyactionbtn().ScrollTo();
				tp.getTally_tallyactionbtn().waitAndClick(10);
				tp.getBulkAssignAction(1).ScrollTo();
				tp.getBulkAssignAction(1).Click();

				bc.VerifyErrorMessage("Please select documents");

				bc.passedStep("if we select source option as " + sourceName_RMU[k] + ""
						+ " selecting tally by field as " + tallyBy[i]
						+ " with out selecting docs if we click 'Assign' button error message displayed as expected.");
			}
		}
		agnmt.deleteAssgnmntUsingPagination(assgnName);
		tfPage.navigateToTagsAndFolderPage();
		tfPage.deleteAllFolders(folderName);
	}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		tp = new TallyPage(driver);
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

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Tally_Regression_sprint22**");

	}
}

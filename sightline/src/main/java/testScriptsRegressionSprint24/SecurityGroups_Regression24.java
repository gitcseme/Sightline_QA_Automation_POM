package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroups_Regression24 {
	

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
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

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

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
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
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
	
	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description :To verify
	 *         that project admin can assign/unassign Comments for selected security
	 *         group.
	 */
	@Test(description = "RPMXCON-53685", enabled = true, groups = { "regression" })
	public void verifyPAAssignUnAssignCommentsForSelectingSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53685");
		baseClass.stepInfo("To verify that project admin can assign/unassign Comments for selected security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		CommentsPage comments = new CommentsPage(driver);
		String addComment = "comment" + Utility.dynamicNameAppender();
		SoftAssert softassert = new SoftAssert();
		String comment = "Comments";
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		comments.AddComments(addComment);
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.createSecurityGroups(SGname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.stepInfo(SGname + "..Selected as expected");
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(comment));
		sgpage.getSGSelectAccessControlTages(comment).waitAndClick(3);

		// verify Comments selected and assign/UnAssign
		sgpage.verifySelectCommentisAssignedInSelectedList(addComment);
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitTime(5);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedCommentsCheckBox(addComment));
		sgpage.getSelectedCommentsCheckBox(addComment).waitAndClick(5);
		baseClass.waitForElement(sgpage.getSG_Comment_Left());
		sgpage.getSG_Comment_Left().waitAndClick(3);
		baseClass.waitForElement(sgpage.getCommentsCheckBox(addComment));
		if (sgpage.getCommentsCheckBox(addComment).isElementAvailable(5)) {
			baseClass.passedStep(addComment + "is displayed on available list");

		} else {
			baseClass.failedMessage("folder is not displayed");

		}
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitForElement(sgpage.getCommentsCheckBox(addComment));
		softassert.assertTrue(sgpage.getCommentsCheckBox(addComment).isElementAvailable(5));
		baseClass.passedStep(addComment + "is unassigned from security group successfully");
		driver.waitForPageToBeReady();
		sgpage.deleteSecurityGroups(SGname);
		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		comments.DeleteComments(addComment);

	}

	/**
	 * @author Sakthivel Date: Modified date:N/A Modified by: Description :To verify
	 *         when Project Admin assigns/unassigns keywords to selected security
	 *         group
	 */
	@Test(description = "RPMXCON-53689", enabled = true, groups = { "regression" })
	public void verifyPAAssignUnAssignKeywordsForSelectingSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53689");
		baseClass.stepInfo("To verify when Project Admin assigns/unassigns keywords to selected security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		SoftAssert softassert = new SoftAssert();
		String keywordname = "AAKeyword" + Utility.dynamicNameAppender();
		String colour = "Blue";
		String keyword = "Keywords";
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		// Add keywords
		keywordPage.navigateToKeywordPage();
		baseClass.stepInfo("Add keyword");
		keywordPage.addKeyword(keywordname, colour);
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.createSecurityGroups(SGname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.stepInfo(SGname + "..Selected as expected");
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(keyword));
		sgpage.getSGSelectAccessControlTages(keyword).waitAndClick(3);

		// verify keywords selected and assign/UnAssign
		sgpage.verifySelectKeywordsAssignedInSelectedList(keywordname);
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.stepInfo(SGname + "..Selected As expected");
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(keyword));
		sgpage.getSGSelectAccessControlTages(keyword).waitAndClick(3);
		softassert.assertTrue(sgpage.getKeyWordsCheckBox(keywordname).isElementAvailable(5));
		baseClass.stepInfo(keywordname + "is Displayed on available list");
		baseClass.waitTime(5);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedKeyWordCheckBox(keywordname));
		sgpage.getSelectedKeyWordCheckBox(keywordname).waitAndClick(5);
		baseClass.waitForElement(sgpage.getSG_Keyword_Left());
		sgpage.getSG_Keyword_Left().waitAndClick(3);
		baseClass.waitForElement(sgpage.getKeyWordsCheckBox(keywordname));
		if (sgpage.getKeyWordsCheckBox(keywordname).isElementAvailable(5)) {
			baseClass.passedStep(keywordname + "is selected and displayed on available list");

		} else {
			baseClass.failedMessage("folder is not displayed");

		}
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitForElement(sgpage.getKeyWordsCheckBox(keywordname));
		softassert.assertTrue(sgpage.getKeyWordsCheckBox(keywordname).isElementAvailable(5));
		baseClass.passedStep(keywordname + "is unrealesed from " + SGname + " successfully");
		softassert.assertAll();
		driver.waitForPageToBeReady();
		// delete keyword word
		sgpage.deleteSecurityGroups(SGname);
		driver.waitForPageToBeReady();
		keywordPage.navigateToKeywordPage();
		keywordPage.deleteKeyword(keywordname);
	}
}

	
	




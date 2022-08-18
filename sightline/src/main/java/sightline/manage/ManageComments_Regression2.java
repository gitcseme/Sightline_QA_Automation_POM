package sightline.manage;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ManageComments_Regression2 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	UserManagement userManage;
	CommentsPage commentsPage;
	
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
		commentsPage = new CommentsPage(driver);
		loginPage = new LoginPage(driver);

	}
	
	
	/**
	 * @author Mohan.Venugopal Created Date:12/07/2022 RPMXCON-52514
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when RMU deletes the comment
	 */
	@Test(description = "RPMXCON-52514", enabled = true, groups = { "regression" })
	public void verifyDeleteOptionForCommentsInRMU() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52514");
		baseClass.stepInfo("To verify when RMU deletes the comment");
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.sa1userName + "");
		
		//Navigate to Comments Page
		String commentName = "Doc_Comment"+Utility.dynamicNameAppender();
		commentsPage.navigateToCommentsPage();
		commentsPage.addComments(commentName);
		commentsPage.deleteCommentsAndClickOnCancelButton(commentName);
		commentsPage.DeleteComments(commentName);
		loginPage.logout();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		
		commentsPage.navigateToCommentsPage();
		
		if (commentsPage.getCommentname(commentName).isDisplayed()) {
			baseClass.failedStep("Comment is present in PA User");
		}else {
			baseClass.passedStep("Comments is not present in CommentsPage when user logged in as PA");
		}
		
		loginPage.logout();
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:12/07/2022 RPMXCON-52508
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when RMU click to edit the comment [RPMXCON-11149]
	 */
	@Test(description = "RPMXCON-52508", enabled = true, groups = { "regression" })
	public void verifyRMUClicksEditForComments() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52508");
		baseClass.stepInfo("To verify when RMU click to edit the comment [RPMXCON-11149]");
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.sa1userName + "");
		
		//Navigate to Comments Page
		commentsPage.EditCommentsIsDisabled();
		
		loginPage.logout();
		
		
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:12/07/2022 RPMXCON-52725
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that comment name should not accept more than 128 characters
	 */
	@Test(description = "RPMXCON-52725", enabled = true, groups = { "regression" })
	public void verifyCommentsNameMoreThan128CharaTerms() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52725");
		baseClass.stepInfo("Verify that comment name should not accept more than 128 characters");
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		String commentsName = "@hotmail.com"+ Utility.randomCharacterAppender(129);
		
		
		// Navigate to Comments and create CommentsName more than 128
		commentsPage.AddCommentsWithErrorMsg(commentsName);
		baseClass.passedStep("Error message is displayed when comment name entered with more than 128 characters.");
		loginPage.logout();
		
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		// Navigate to Comments and create CommentsName more than 128
		commentsPage.AddCommentsWithErrorMsg(commentsName);
		baseClass.passedStep("Error message is displayed when comment name entered with more than 128 characters.");
		loginPage.logout();
	}
	
	
	/**
	 * @author Mohan.Venugopal Created Date:12/07/2022 RPMXCON-52724
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that after impersonation space, special characters should not be allowed to add comment
	 */
	@Test(description = "RPMXCON-52724", enabled = true, groups = { "regression" })
	public void verifyCommentsNameWithSpecialCharacters() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52724");
		baseClass.stepInfo("Verify that after impersonation space, special characters should not be allowed to add comment");
		
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		String commentsName = "@,* ";
		
		baseClass.impersonateSAtoPA();
		// Navigate to Comments and create CommentsName more than 128
		commentsPage.AddCommentsWithErrorMsg(commentsName);
		baseClass.passedStep("Error message is displayed as 'Only alphanumeric characters and underscore are allowed successfully.'");
		loginPage.logout();
		
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		
		baseClass.impersonateSAtoRMU();
		// Navigate to Comments and create CommentsName more than 128
		commentsPage.AddCommentsWithErrorMsg(commentsName);
		baseClass.passedStep("Error message is displayed as 'Only alphanumeric characters and underscore are allowed successfully.'");
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}



}

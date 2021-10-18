package testScriptsSmoke;

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

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;
import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class TS_018_DocViewAudio {
	Driver driver;
	LoginPage lp;
	String codingfrom = "Audio CF" + Utility.dynamicNameAppender();
	String RedactName = "AudioRedaction" + Utility.dynamicNameAppender();
	String remark = "Audio Remark" + Utility.dynamicNameAppender();
	String Comments = "Audio comment";
	DocViewPage docview;

	@BeforeClass(alwaysRun = true)
	public void before() throws InterruptedException, ParseException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		driver = new Driver();
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// add comment field
		CommentsPage comments = new CommentsPage(driver);
		comments.AddComments("Comment" + Utility.dynamicNameAppender());

		// Create coding for for assignment
		CodingForm cf = new CodingForm(driver);
		cf.createCodingform(codingfrom);

		cf.CodingformToSecurityGroup(codingfrom);

		RedactionPage redpage = new RedactionPage(driver);
		redpage.AddRedaction(RedactName, "RMU");

		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		SessionSearch search = new SessionSearch(driver);
		search.audioSearch("morning", "North American English");
		search.ViewInDocView();
		docview = new DocViewPage(driver);

	}

	@Test(groups = { "smoke", "regression" })
	public void addPlay() throws ParseException, InterruptedException {

		docview.playAudio();

	}

	@Test(groups = { "smoke", "regression" })
	public void addRemark() throws ParseException, InterruptedException {

		docview.audioRemark(remark);
	}

	@Test(groups = { "smoke", "regression" })
	public void addComment() throws ParseException, InterruptedException {

		docview.audioComment(remark);

	}

	@Test(groups = { "smoke", "regression" })
	public void addreduction() throws ParseException, InterruptedException {

		docview.audioRedaction();

	}

	@Test(groups = { "smoke", "regression" })
	public void AudioDownload() throws ParseException, InterruptedException {

		docview.audioDownload();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	//	ExtentTestManager.getTest().log(Status.INFO, this.getClass().getSimpleName()+"/"+testMethod.getName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			lp.logout();
			// lp.quitBrowser();
		} finally {
			lp.quitBrowser();

		}
		LoginPage.clearBrowserCache();
	}
}

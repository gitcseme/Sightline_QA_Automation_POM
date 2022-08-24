package testScriptsRegressionSprint18;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.BaseClass;
import pageFactory.CollectionPage;
import pageFactory.DataSets;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.SourceLocationPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression01 {
	
	Driver driver;
	LoginPage login;
	BaseClass base;
	SessionSearch sessionSearch;
	DocViewPage docviewPage;
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docviewPage = new DocViewPage(driver);
	}

	
	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify the automatically selection of the redaction tag for audio redaction when Project Admin impersonates as RMU/Reviewer
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52013", enabled = true, groups = { "regression" })
	public void verifyAutomaticSelectionOfRedactionTagForAudioDocsAsPA() throws Exception {
	
		base.stepInfo("Test case Id: RPMXCON-52013 - DocViewAudio");
		base.stepInfo("Verify the automatically selection of the redaction tag for audio redaction when Project Admin impersonates as RMU/Reviewer");


		// Login as PA
		login.loginToSightLine(Input.pa1userName,Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.pa1userName + "");
		
		//Impersonate from PA to RMU and Reviewer
		base.impersonatePAtoRMU();
		
		//Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		
		//Navigate to DocView Page
		sessionSearch.ViewInDocViews();
		
		//Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		
		//logout
		login.logout();
		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		base.impersonatePAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// logout
		login.logout();
		
		
		
		
		
	}
	
	

	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify the automatically selection of the redaction tag for audio redaction when Domain Admin impersonates as RMU/Reviewer
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52012", enabled = true, groups = { "regression" })
	public void verifyAutomaticSelectionOfRedactionTagForAudioDocsAsDA() throws Exception {
	
		base.stepInfo("Test case Id: RPMXCON-52012 - DocViewAudio");
		base.stepInfo("Verify the automatically selection of the redaction tag for audio redaction when Domain Admin impersonates as RMU/Reviewer");


		// Login as DA
		login.loginToSightLine(Input.da1userName,Input.da1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.da1userName + "");
		
		//Impersonate from PA to RMU and Reviewer
		base.impersonateDAtoRMU();
		
		//Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		
		//Navigate to DocView Page
		sessionSearch.ViewInDocViews();
		
		//Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		base.passedStep("'Default Redaction Tag' is saved for the redaction successfully");
		
		//logout
		login.logout();
		
		// Login as DA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.da1userName + "");
				
		// Impersonate from PA to RMU and Reviewer
		base.impersonateDAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		base.passedStep("'Default Redaction Tag' is saved for the redaction successfully");
		// logout
		login.logout();
		
		
		
		
		
	}
	
	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify the automatically selection of the redaction tag for audio redaction when System Admin impersonates as RMU/Reviewer
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52011", enabled = true, groups = { "regression" })
	public void verifyAutomaticSelectionOfRedactionTagForAudioDocsAsSA() throws Exception {
	
		base.stepInfo("Test case Id: RPMXCON-52011 - DocViewAudio");
		base.stepInfo("Verify the automatically selection of the redaction tag for audio redaction when System Admin impersonates as RMU/Reviewer");


		// Login as SA
		login.loginToSightLine(Input.sa1userName,Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.sa1userName + "");
		
		//Impersonate from PA to RMU and Reviewer
		base.impersonateDAtoRMU();
		
		//Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		
		//Navigate to DocView Page
		sessionSearch.ViewInDocViews();
		
		//Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		
		//logout
		login.logout();
		
		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.sa1userName + "");
				
		// Impersonate from PA to RMU and Reviewer
		base.impersonateDAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// logout
		login.logout();
		
		
		
		
		
	}
	
	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify that for SA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52003", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserShouldAllowOneRedactionTagForAudioDocsAsSA() throws Exception {
	
		base.stepInfo("Test case Id: RPMXCON-52003 - DocViewAudio");
		base.stepInfo("Verify that for SA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction");

		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.pa1userName + "");
		
		String annotationLayerName = "AudioRedaction"+Utility.randomCharacterAppender(3);
		
		//Pre-requsite.. Add an AnnotatioLayer under SG
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		annotationLayer.AddAnnotation(annotationLayerName);
		
		login.logout();
		

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.sa1userName + "");
		
		//Impersonate from PA to RMU and Reviewer
		base.impersonateSAtoRMU();
		
		//Navigate to Advance Search Page and search for audio docs
		
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		
		//Navigate to DocView Page
		sessionSearch.ViewInDocViews();
		
		//Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		base.passedStep("Only one redaction tag is selected for the redaction");
		
		//logout
		login.logout();
		
		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.sa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		base.impersonateSAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		base.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		login.logout();
		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Pre-requsite.. Add an AnnotatioLayer under SG
		annotationLayer.deleteAnnotationByPagination(annotationLayerName);

		login.logout();
		
		
		
		
		
	}
	
	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify that for SA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52004", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserShouldAllowOneRedactionTagForAudioDocsWithEditAsSA() throws Exception {
	
		base.stepInfo("Test case Id: RPMXCON-52004 - DocViewAudio");
		base.stepInfo("Verify that for SA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction");

		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.pa1userName + "");
		
		String annotationLayerName = "AudioRedaction"+Utility.randomCharacterAppender(3);
		
		//Pre-requsite.. Add an AnnotatioLayer under SG
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		annotationLayer.AddAnnotation(annotationLayerName);
		
		login.logout();
		

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.sa1userName + "");
		
		//Impersonate from PA to RMU and Reviewer
		base.impersonateSAtoRMU();
		
		//Navigate to Advance Search Page and search for audio docs
		
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		
		//Navigate to DocView Page
		sessionSearch.ViewInDocViews();
		
		//Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRangeWithEditRedactionTag(1, 1, 2);
		base.passedStep("Only one redaction tag is selected for the redaction");
		
		//logout
		login.logout();
		
		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.sa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		base.impersonateSAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRangeWithEditRedactionTag(1, 1, 2);
		base.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		login.logout();
		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Pre-requsite.. Add an AnnotatioLayer under SG
		annotationLayer.deleteAnnotationByPagination(annotationLayerName);

		login.logout();
		
		
		
		
		
	}
	
	
	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify that for DA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52005", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserShouldAllowOneRedactionTagForAudioDocsAsDA() throws Exception {
	
		base.stepInfo("Test case Id: RPMXCON-52005 - DocViewAudio");
		base.stepInfo("Verify that for DA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction");

		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.pa1userName + "");
		
		String annotationLayerName = "AudioRedaction"+Utility.randomCharacterAppender(3);
		
		//Pre-requsite.. Add an AnnotatioLayer under SG
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		annotationLayer.AddAnnotation(annotationLayerName);
		
		login.logout();
		

		// Login as SA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.da1userName + "");
		
		//Impersonate from PA to RMU and Reviewer
		base.impersonateDAtoRMU();
		
		//Navigate to Advance Search Page and search for audio docs
		
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		
		//Navigate to DocView Page
		sessionSearch.ViewInDocViews();
		
		//Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		base.passedStep("Only one redaction tag is selected for the redaction");
		
		//logout
		login.logout();
		
		// Login as SA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.da1userName + "");

		// Impersonate from PA to RMU and Reviewer
		base.impersonateSAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		base.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		login.logout();
		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Pre-requsite.. Add an AnnotatioLayer under SG
		annotationLayer.deleteAnnotationByPagination(annotationLayerName);

		login.logout();
		
		
		
		
		
	}
	
	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify that for PA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52007", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserShouldAllowOneRedactionTagForAudioDocsAsPA() throws Exception {
	
		base.stepInfo("Test case Id: RPMXCON-52007 - DocViewAudio");
		base.stepInfo("Verify that for PA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction");

		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.pa1userName + "");
		
		String annotationLayerName = "AudioRedaction"+Utility.randomCharacterAppender(3);
		
		//Pre-requsite.. Add an AnnotatioLayer under SG
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		annotationLayer.AddAnnotation(annotationLayerName);
		
		login.logout();
		

		// Login as SA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " +Input.pa1userName + "");
		
		//Impersonate from PA to RMU and Reviewer
		base.impersonateDAtoRMU();
		
		//Navigate to Advance Search Page and search for audio docs
		
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		
		//Navigate to DocView Page
		sessionSearch.ViewInDocViews();
		
		//Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		base.passedStep("Only one redaction tag is selected for the redaction");
		
		//logout
		login.logout();
		
		// Login as SA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		base.impersonateSAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		base.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		login.logout();
		
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Pre-requsite.. Add an AnnotatioLayer under SG
		annotationLayer.deleteAnnotationByPagination(annotationLayerName);

		login.logout();
		
		
		
		
		
	}
	
	
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}

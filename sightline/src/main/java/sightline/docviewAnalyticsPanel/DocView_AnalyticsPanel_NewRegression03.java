package sightline.docviewAnalyticsPanel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanel_NewRegression03 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	DocViewRedactions docViewRedact;
	SavedSearch savedSearch;

	public void preCondition() throws InterruptedException, IOException, ParseException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws Exception, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
	}
	
	/**
	 * Author : Mohan date: 24/11/21 NA Modified date: NA Modified by: N/A
	 * Description : DataProvider for Different User Login
	 */
	@DataProvider(name = "userDetailss")
	public Object[][] userLoginSaPaRmu() {
		return new Object[][] { { "rmu", Input.rmu1userName, Input.rmu1password, "rev" },
				{ "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "specificUsers")
	public Object[][] userLoginWithSpecificCredentials() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password }, };
	}

	@DataProvider(name = "multiUsers")
	public Object[][] userLoginWithMultiCredentials() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	/**
	 * Author : Vijaya.Rani date: 07/04/2022 Modified date: NA Modified by: NA
	 * 
	 * @description: Verify that thread map presents only emails when threaded
	 *               document contains email attachment. 'RPMXCON-51509' Sprint-13
	 * 
	 */
	@Test(description ="RPMXCON-51509",enabled = true, groups = { "regression" })
	public void verifyThreadMapEmailsInDocViewEmailAttachment()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51509");
		baseClass.stepInfo(	"Verify that thread map presents only emails when threaded document contains email attachment.");

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName01);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Step 2 : Search for Meta Data Docs and go to Docview");
		sessionSearch.SearchMetaData("IngestionName", Input.DocViewNativesIngestion);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Configure Mini DocList Add AttachCount
		baseClass.stepInfo("Step 3 :Select Configure MiniDocList AttachCount Fields");
		docView.selectAttachCountDocIdInAvailableField();

		baseClass.stepInfo("Step 4 :Select Email Attach Docs in MiniDocList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_MiniDoc_Selectdoc(2));
		docView.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		baseClass.stepInfo("Successfully Selected Email Attach Count Document");

		// MetaData AttachIds Display
		String id = docView.selectAttachCountDocIdDisplayMetaData();

		// AttachIds Not DisPlay AnaltyticsPanel
		driver.waitForPageToBeReady();
		docView.selectAttachEmailIdDisplayInAnalyticsPanel(id);

		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 07/04/2022 Modified date: NA Modified by: NA
	 * 
	 * @description: Verify that thread map presents only threaded emails when
	 *               threaded document contains non email attachment.
	 *               'RPMXCON-51510' Sprint-13
	 * 
	 * 
	 */
	@Test(description ="RPMXCON-51510",enabled = true, groups = { "regression" })
	public void verifyThreadedMapEmailsPresentsContainsEmailAttachment()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51510");
		baseClass.stepInfo(
				"Verify that thread map presents only emails when threaded document contains email attachment.");

		// login as RMU
	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName01);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo(	"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Step 2 : Search for Meta Data Docs and go to Docview");
		sessionSearch.SearchMetaData("IngestionName", Input.DocViewNativesIngestion);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Configure Mini DocList Add AttachCount
		baseClass.stepInfo("Step 3 :Select Configure MiniDocList AttachCount Fields");
		docView.selectAttachCountDocIdInAvailableField();

		baseClass.stepInfo("Step 4 :Select Email Attach Docs in MiniDocList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_MiniDoc_Selectdoc(4));
		docView.getDocView_MiniDoc_Selectdoc(4).waitAndClick(5);
		baseClass.stepInfo("Successfully Selected Email Attach Count Document");

		// MetaData AttachIds Display
		String id = docView.selectAttachCountDocIdDisplayMetaData();

		// AttachIds Not DisPlay AnaltyticsPanel
		driver.waitForPageToBeReady();
		docView.selectAttachEmailIdDisplayInAnalyticsPanel(id);

		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 12/04/2022 Modified date: NA Modified by: NA
	 * 
	 * @description:Verify that thread map presents only threaded emails when threaded document contains email attachment which is not parent/child
	 * of any other document [non threaded email attachment]. 'RPMXCON-51511' Sprint-13
	 * 
	 * 
	 */
	@Test(description ="RPMXCON-51511",enabled = true, groups = { "regression" })
	public void verifyThreadedMapEmailsPresentsContainsNonThreadedEmailAttachment() throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51511");
		baseClass.stepInfo(
				"Verify that thread map presents only threaded emails when threaded document contains email attachment which is not parent/child of any other document [non threaded email attachment].");

		// login as RMU
	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName01);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo(	"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		//baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Step 2 : Search for Meta Data Docs and go to Docview");
		sessionSearch.SearchMetaData("IngestionName", Input.DocViewNativesIngestion);
		sessionSearch.ViewThreadedDocsInDocViews();

		//Select Configure Mini DocList Add AttachCount
		baseClass.stepInfo("Step 3 :Select Configure MiniDocList AttachCount Fields");
		docView.selectAttachCountDocIdInAvailableField();
		
		baseClass.stepInfo("Step 4 :Select Email Attach Docs in MiniDocList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_MiniDoc_Selectdoc(15));
		docView.getDocView_MiniDoc_Selectdoc(15).waitAndClick(5);
		baseClass.stepInfo("Successfully Selected Email Attach Count Document");
		
		//MetaData AttachIds Display
		String id = docView.selectAttachCountDocIdDisplayMetaData();
		
		//AttachIds Not DisPlay AnaltyticsPanel
		driver.waitForPageToBeReady();
		docView.selectAttachEmailIdDisplayInAnalyticsPanel(id);

		loginPage.logout();
	}
	
	/**
	 * Author : Vijaya.Rani date: 12/04/2022 Modified date: NA Modified by: NA
	 * 
	 * @description:Verify that thread map presents only threaded emails when threaded document contains email attachment which is
	 * parent/child of any other document [threaded email attachment]. 'RPMXCON-51512' Sprint-13
	 * 
	 * 
	 */
	@Test(description ="RPMXCON-51512",enabled = true, groups = { "regression" })
	public void verifyThreadedMapEmailsPresentsContainsParentThreadedEmail() throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51512");
		baseClass.stepInfo("Verify that thread map presents only threaded emails when threaded document contains email attachment which is parent/child of any other document [threaded email attachment].");

		// login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName01);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Step 2 : Search for Meta Data Docs and go to Docview");
		sessionSearch.SearchMetaData("IngestionName", Input.DocViewNativesIngestion);
		sessionSearch.ViewThreadedDocsInDocViews();

		//Select Configure Mini DocList Add AttachCount
		baseClass.stepInfo("Step 3 :Select Configure MiniDocList AttachCount Fields");
		docView.selectAttachCountDocIdInAvailableField();
		
		baseClass.stepInfo("Step 4 :Select Email Attach Docs in MiniDocList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_MiniDoc_Selectdoc(6));
		docView.getDocView_MiniDoc_Selectdoc(6).waitAndClick(5);
		baseClass.stepInfo("Successfully Selected Email Attach Count Document");
		
		//MetaData AttachIds Display
		String id = docView.selectAttachCountDocIdDisplayMetaData();
		
		//AttachIds Not DisPlay AnaltyticsPanel
		driver.waitForPageToBeReady();
		docView.selectAttachEmailIdDisplayInAnalyticsPanel(id);

		loginPage.logout();
	}
	/**
	* Author : Aathith date: 13/04/2022 Modified date: NA Modified by: NA
	*
	* @description:Verify that thread map presents only emails and doesn't present any attachments of type
	* email that itself is a parent for other docs. 'RPMXCON-51513' Sprint-13
	*
	*
	*/
	@Test(description ="RPMXCON-51513",enabled = true, groups = { "regression" })
	public void verifyThreadedMapEmailsDoesnotPresentEmailsdocs() throws ParseException, InterruptedException, IOException {

	baseClass.stepInfo("Test case Id: RPMXCON-51513");
	baseClass.stepInfo("Verify that thread map presents only emails and doesn't present any attachments of type email that itself is a parent for other docs.");

	// login as RMU
	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName01);
	UtilityLog.info("Logged in as User: " + Input.pa1userName);
	baseClass.stepInfo("User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

    baseClass.stepInfo("Step 2 : Search for Meta Data Docs and go to Docview");
	sessionSearch.SearchMetaData("IngestionName", Input.DocViewNativesIngestion);
	sessionSearch.ViewThreadedDocsInDocViews();

	//Select Configure Mini DocList Add AttachCount
	baseClass.stepInfo("Step 3 :Select Configure MiniDocList AttachCount Fields");
	docView.selectAttachCountDocIdInAvailableField();

	baseClass.stepInfo("Step 4 :Select Email Attach Docs in MiniDocList");
	driver.waitForPageToBeReady();
	baseClass.waitForElement(docView.getDocView_MiniDoc_Selectdoc(7));
	docView.getDocView_MiniDoc_Selectdoc(7).waitAndClick(10);
	baseClass.stepInfo("Successfully Selected Email Attach Count Document");

	//MetaData AttachIds Display
	String id = docView.selectAttachCountDocIdDisplayMetaData();

	//AttachIds Not DisPlay AnaltyticsPanel
	driver.waitForPageToBeReady();
	docView.selectAttachEmailIdDisplayInAnalyticsPanel(id);

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

		loginPage.quitBrowser();

	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV & DOCVIEW/REDACTIONS EXECUTED******");

	}

}

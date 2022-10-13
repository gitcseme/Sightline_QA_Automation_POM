package testScriptsRegressionSprint23;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regression23 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	TallyPage tally;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

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
		UtilityLog.info(testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);
		tally = new TallyPage(driver);
	}

	/**
	 * @author Vijaya.Rani ModifyDate:13/10/2022 RPMXCON-55729
	 * @throws Exception
	 * @Description Verify that notification has a clear message about the project
	 *              creation or failure.
	 */
	@Test(description = "RPMXCON-55729", enabled = true, groups = { "regression" })
	public void verifyNotificationClearMsgInProjectCreation() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-55729");
		baseClass.stepInfo("Verify that notification has a clear message about the project creation or failure.");
		
		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "Project"+Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");
		
		projectPage.navigateToProductionPage();
        projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName,"0");
        DataSets data = new DataSets(driver);
        data.getNotificationMessage(0,projectName);
        
        baseClass.passedStep("Verify that notification has a clear message about the project creation Successfully");
        loginPage.logout();
        
	}

	/**
	 * @author Vijaya.Rani ModifyDate:13/10/2022 RPMXCON-56174
	 * @throws Exception
	 * @Description Verify SAU can create a new domain client by selecting 'Processing Engine Type' as 'ICE-NUIX'.
	 */
	@Test(description = "RPMXCON-56174", enabled = true, groups = { "regression" })
	public void verifySACreateNewDomainClientSelectProcessingEngineType() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-56174");
		baseClass.stepInfo("Verify SAU can create a new domain client by selecting 'Processing Engine Type' as 'ICE-NUIX'.");
		
		ClientsPage client = new ClientsPage(driver);
		String domianName = Utility.randomCharacterAppender(4)+Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");
		
		//Add Domain client
		client.AddDomainClientProcessingEngine(domianName, domianName, "Small (less than 1000 documents)");
		baseClass.getSuccessMsgHeader().isElementAvailable(10);
		baseClass.VerifySuccessMessage("The new client was added successfully");
		baseClass.CloseSuccessMsgpopup();
		client.filterClient(domianName);
		driver.waitForPageToBeReady();
		if(baseClass.text(domianName).isDisplayed()) {
			baseClass.passedStep("Newly created Domain client details is displayed in the Grid view");
		}else {
			baseClass.failedStep("Newly created Domain client details is not displays in the Grid view");
		}
		//delete client
		client.deleteClinet(domianName);
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
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
}

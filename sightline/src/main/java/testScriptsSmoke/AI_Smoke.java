package testScriptsSmoke;
//package sightline.ai;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.internal.matchers.InstanceOf;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
import pageFactory.AIPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;

public class AI_Smoke {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	BatchRedactionPage batch;
	AIPage ai;
	AssignmentsPage as;
	TagsAndFoldersPage tnfpage;
	DocViewPage docview;
	DocListPage doclist;
	SoftAssert softAssertion;
	RedactionPage redact;

	DocViewMetaDataPage docviewMetadata;
	DocViewRedactions DCRedactions;

	String Tag = "Tag "+Utility.dynamicNameAppender();
	String Tag1 = "Tag1 "+Utility.dynamicNameAppender();
	String Folder = "Folder "+Utility.dynamicNameAppender();
	String CfName = "CF "+Utility.dynamicNameAppender();
	String AsName = "AS "+Utility.dynamicNameAppender();
	String ProjectName = Input.AIprojectName;
	
	
	HashMap<String, String> scores = new HashMap();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();		
		in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
		batch = new BatchRedactionPage(driver);
		ai = new AIPage(driver);
		as = new AssignmentsPage(driver);
		tnfpage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		docview = new DocViewPage(driver);
		redact = new RedactionPage(driver);
		docviewMetadata = new DocViewMetaDataPage(driver);
		DCRedactions = new DocViewRedactions(driver);
	}

	/**
	 * @author Swathi Description : Verify that AI enabled Project got created successfully
	 *         default Responsive model is created
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-68222", enabled = true, groups = { "regression","smoke" })
	public void verifyAIEnabledProject() throws InterruptedException {
		// will login as System Admin
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Tescase ID :RPMXCON-68222");
		base.stepInfo(
				"Verify that AI enabled Project got created successfully'"
						+ " and default Responsive model is created");
		
		ai.verifyAIEnabled(Input.AIprojectName);
		base.impersonateSAtoRMUAI();
		base.stepInfo("Impersonated as RMU");
		ai.verifyResponsiveModel();
		base.stepInfo("Default Responsive Model is displaying");
		login.logout();
	}

	/**
	 * @author Swathi Description : Verify that Training and scoring got completed in newly created AI Tag.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-75689",enabled = true, groups = { "regression","smoke" })
	public void verifyAIScoreInNewTag() throws InterruptedException {

		// will login as System Admin
		login.loginToSightLineAI(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-75689");
		base.stepInfo("Verify that Training and scoring got completed in newly created AI Tag");

		ai.bulkFolder(Folder);
		ai.bulkTag(Tag);
		ai.bulkTag(Tag1);
		ai.EnableAIAllDcos(Tag);
		base.waitTime(4);
		ai.EnableAI(Tag1,Folder);
//		ai.verifyScoringProgress(Tag);
//		ai.verifyScoringComplete(Tag);

		login.logout();
	}

	/**
	 * @author Swathi Description : Verify AI Scores in DocList.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-70399",enabled = true, groups = { "regression","smoke" })
	public void verifyAI_ScoresInDocList() throws InterruptedException {

		// will login as System Admin
		login.loginToSightLineAI(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-70399");
		base.stepInfo("Verify that AI Scores are displaying in DocList.");

		ai.verifyScoringProgress(Tag);
		ai.verifyScoringComplete(Tag);

		base.stepInfo("Navigating to Doc Explorer screen.");
		this.driver.getWebDriver().get(Input.url+"DocExplorer/Explorer");
		base.waitTime(2);

		base.stepInfo("Selecting trained documents using tag filter");
		ai.SelectDocsInDocExplorer(Tag);
		base.stepInfo("Checking scores in DocList screen.");
		ai.verifyScoringsInDocList(Tag);
		base.stepInfo("Storing top 10 AI Scores along with IDs");

		List<WebElement> aiScores = ai.getAIScoresinDocList().FindWebElements();
		List<WebElement> docIDs = ai.getDocIdsinDocList().FindWebElements();
		for (int i=0;i<aiScores.size();i++){
			scores.put(docIDs.get(i).getText(),aiScores.get(i).getText());
		}
		System.out.println("Top 10 AI Scores along with DocIDs from DocList ");
		System.out.println(scores);
		base.stepInfo("Top 10 AI Scores along with DocIDs from DocList ");
		for(Map.Entry<String, String> entry:scores.entrySet()) {
			base.stepInfo( entry.getKey() + " = " + entry.getValue());
		}

		login.logout();

	}


	/**
	 * @author Swathi Description : Verify Scores and Prediction Hints in DocView from RMU Dashboard.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-68572",enabled = true, groups = { "regression","smoke" })
	public void verify_PredictionHints_InDocView_AIScores() throws InterruptedException {

		// will login as System Admin
		login.loginToSightLineAI(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-68572");
		
		base.stepInfo("Verify that Coding form and Assignment is created successfully.");

		base.stepInfo("Navigating to Manage Codingforms screen to create a Codingform");
		this.driver.getWebDriver().get(Input.url+"CodingForm/Create");

		ai.CreateCodingform(CfName,Tag);
		as.createAssignment(AsName, CfName);
		ai.bulkAssign();
		base.stepInfo("Assigning documents to cretaed assignement");
		as.assignDocstoExisting(AsName);
		base.stepInfo("Navigating to Edit Assignment screen to distribute documents to review manager");
		as.editAssignment(AsName);
		base.stepInfo("Selecting AI Tag from SORT BY AI SCORE tab");
		ai.SortByAIScore(Tag);
		base.stepInfo("Adding Review Manager and distributing documents");
		as.addRevieManagerAndDistributeDocs(AsName, 100);
		
		base.stepInfo("Verify that Scores and Prediction Hints are displaying in DocView from RMU Dashboard.");

		base.stepInfo("Navigating to RMU Dashboard screen.");
		this.driver.getWebDriver().get(Input.url+"Dashboard/Dashboard");

		base.stepInfo("Navigating to DocView Screen.");
		ai.navigateToDocViewFromRMUDashboard(AsName);
		base.stepInfo("Checking if DocIDs are matching");
		base.waitTime(3);
		String One = ai.get1stDocID().getWebElement().getText();
		if(scores.containsKey(One)) {
			base.passedStep("Document with highest AI Score is displaying 1st in DocView screen.");
		}
		else {
			base.failedStep("Document with highest AI Score is not displaying 1st in DocView screen.");
		}

		String Five = ai.get5thDocID().getWebElement().getText();
		if(scores.containsKey(Five)) {
			base.passedStep("Documents with top AI Scores are displaying.");
		}
		else {
			base.failedStep("Documents with top AI Scores are not displaying.");
		}

		base.stepInfo("Verifying Prediction Hints");
		try {
			String prediction = ai.getPrediction().getWebElement().getText();
			if(Double.parseDouble(scores.get(One)) >= 0.86 && Double.parseDouble(scores.get(One)) <= 1) {
				Assert.assertEquals(prediction, "Predicted Highly Likely");
				Reporter.log(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
				System.out.println(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
				UtilityLog.info(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
			}
			else if(Double.parseDouble(scores.get(One)) >= 0.71 && Double.parseDouble(scores.get(One)) <= 0.85) {
				Assert.assertEquals(prediction, "Predicted Likely");
				Reporter.log(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
				System.out.println(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
				UtilityLog.info(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
			}
			else if(Double.parseDouble(scores.get(One)) >= 0.16 && Double.parseDouble(scores.get(One)) <= 0.30) {
				Assert.assertEquals(prediction, "Predicted Unlikely");
				Reporter.log(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
				System.out.println(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
				UtilityLog.info(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
			}
			else if(Double.parseDouble(scores.get(One)) >= 0.0 && Double.parseDouble(scores.get(One)) <= 0.15) {
				Assert.assertEquals(prediction, "Predicted Highly Unlikely");
				Reporter.log(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
				System.out.println(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
				UtilityLog.info(prediction + " is displaying for "+One+" with AI Score "+scores.get(One));
			}

		}catch (Exception e) {
			// TODO: handle exception
			base.stepInfo("Prediction Hint is not displaying.");
		}


		login.logout();

	}
	
	/**
	 * @author Swathi Description : Verify AI Model is deleted and Scores/Predictions also deleted for that tag.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-75691",enabled = true, groups = { "regression","smoke" })
	public void verify_Scores_Deleted() throws InterruptedException {

		// will login as System Admin
		login.loginToSightLineAI(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-75691");
		base.stepInfo("Verify AI Model is deleted and Scores/Predictions also deleted for that tag.");

		//ai.verifyScoringProgress(Tag1);
		ai.verifyScoringComplete(Tag1);
		
		base.stepInfo("Deleting AI Model");
		ai.deleteAIModel(Tag1);
		base.stepInfo("Verifying if AI is disabled for Tag");
		ai.verifyAIDisabled(Tag1);
		base.stepInfo("Verifying if scores also deleted");
		ai.verifyAIScoreDeletion(Tag1);
		
		base.passedStep("Verified Successfully");
		
		login.logout();
	}
	
	/**
	 * @author Swathi Description : Verify that T/S is working for subsequent run.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-75690",enabled = true, groups = { "regression","smoke" })
	public void verify_Subsequent_AI_Train_Score() throws InterruptedException {

		// will login as System Admin
		login.loginToSightLineAI(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-75690");
		base.stepInfo("Verify that T/S is working for subsequent run.");

		base.stepInfo("Navigating to Doc Explorer screen.");
		this.driver.getWebDriver().get(Input.url+"DocExplorer/Explorer");
		base.waitTime(2);

		base.stepInfo("Selecting trained documents using tag filter");
		ai.SelectDocsInDocExplorerExclude(Tag);
		driver.waitForPageToBeReady();
		base.waitForElement(ai.SelectColumnBtn());
		ai.SelectDocumentsForSubsequentRun();
		
		ai.bulkTagExisting(Tag);
		ai.verifyScoringProgress(Tag);
		ai.verifyScoringComplete(Tag);
		base.passedStep("Subsequent T/S is completed successfully");
	}


	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
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
			login.logoutWithoutAssert();

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			login.quitBrowser();
		} finally {
			// login.clearBrowserCache();
			
		}
	}

}



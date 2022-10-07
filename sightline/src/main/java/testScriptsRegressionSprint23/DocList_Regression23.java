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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression23 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
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
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);

	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53767
	 * @throws Exception
	 * @Description To Verify, As an Project Admin user login, I will be able to
	 *              Un-release multiple documents from some security group from
	 *              DocList page from Current search.
	 */
	@Test(description = "RPMXCON-53767", enabled = true, groups = { "regression" })
	public void verifyAsPAInDocListPageUnreleaseDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53767");
		baseClass.stepInfo(
				"To Verify, As an Project Admin user login, I will be able to Un-release multiple documents from some security group from DocList page from Current search.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String SG = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create SG
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(SG);

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.documentSelection(5);
		// BulkUnrelease
		docList.bulkUnRelease(SG);
		baseClass.passedStep("Documents will Un-release from security group successfully");
		// deleteSG
		sg.deleteSecurityGroups(SG);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53769
	 * @throws Exception
	 * @Description To Verify, As an Project Admin user login, I will be able to
	 *              Un-release all documents from security group from DocList page
	 *              from Current search.
	 */
	@Test(description = "RPMXCON-53769", enabled = true, groups = { "regression" })
	public void verifyAsPAInDocListPageSelectAllDocsUnreleaseDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53769");
		baseClass.stepInfo(
				"To Verify, As an Project Admin user login, I will be able to Un-release all documents from security group from DocList page from Current search.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String SG = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create SG
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(SG);

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.selectAllDocs();
		// BulkUnrelease
		docList.bulkUnRelease(SG);
		baseClass.passedStep("All Documents will Un-release from security group successfully");
		// deleteSG
		sg.deleteSecurityGroups(SG);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53786
	 * @throws Exception
	 * @Description To verify, as an Reviewer user login, I can select multiple
	 *              document from Doc list & I can Tag all the selected documents.
	 */
	@Test(description = "RPMXCON-53786", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSelectMultipleDocInDocListInTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53786");
		baseClass.stepInfo(
				"To verify, as an Reviewer user login, I can select multiple document from Doc list & I can Tag all the selected documents.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String tagName = "tag" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Select Tag Classification");
		loginPage.logout();
		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.documentSelection(5);
		// BulkTag
		docList.bulkTagExisting(tagName);
		baseClass.passedStep("Tag will apply on the selected documents Successfully");

		// logout
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

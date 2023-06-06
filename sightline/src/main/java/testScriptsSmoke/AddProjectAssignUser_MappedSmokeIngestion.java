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

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;

public class AddProjectAssignUser_MappedSmokeIngestion {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	Input ip;
	DocExplorerPage docExplorer;
	String ingestionDataName;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip = new Input();
		ip.loadEnvConfig();
		ingestionDataName = Input.IngestionName_PT;
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		ingestionPage = new IngestionPage_Indium(driver);
		loginPage = new LoginPage(driver);	
		
	}
	
	@Test(enabled = true, groups = { "regression" },priority=2)
	public void mappedIngestion() throws Exception {
		UserManagement userManagement=new UserManagement(driver);
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject();
		
				ingestionPage = new IngestionPage_Indium(driver);
				baseClass.stepInfo("Ingestion Access Verification");
				userManagement.verifyIngestionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);
				ingestionPage.navigateToIngestionPage();
				ingestionPage.IngestionSmoke(Input.AllSourcesFolder);
				ingestionPage.publishedIngestionName();
				 SessionSearch search = new SessionSearch(driver);
				 search.basicContentSearch(ingestionPage.IngestionName);
				 search.bulkRelease("Default Security Group");
				 loginPage.logout();
	}
	@Test(enabled = true, groups = { "regression" },priority=1)
	public void createProjectAndAssignUser() {
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
        baseClass.stepInfo("logged In  as SA user");
        ProjectPage project=new ProjectPage(driver);
        String ProjectName=Input.projectName;
        project.navigateToProductionPage();
        project.AddSmokeDomainProject(ProjectName, Input.domainName);
        UserManagement user=new UserManagement(driver);
        user.navigateToUsersPAge();
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.pa1FullName);
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.pa2FullName);
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.pa3FullName);
        user.AssignUserToProject(ProjectName, "Project Administrator", Input.pa4FullName);
        user.AssignUserToProject(ProjectName, "Review Manager", Input.rmu1FullName);
        user.AssignUserToProject(ProjectName, "Review Manager", Input.rmu2FullName);
        user.AssignUserToProject(ProjectName, "Review Manager", Input.rmu2FullName);
        user.AssignUserToProject(ProjectName, "Review Manager", Input.rmu2FullName);
        user.AssignUserToProject(ProjectName, "Reviewer", Input.rev1FullName);
        user.AssignUserToProject(ProjectName, "Reviewer", Input.rev2FullName);
        user.AssignUserToProject(ProjectName, "Reviewer", Input.rev2FullName);
        user.AssignUserToProject(ProjectName, "Reviewer", Input.rev2FullName);
        user.AssignUserToDomain(Input.domainName, Input.da1FullName);
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
		System.out.println("******TEST CASES FOR INGESTION EXECUTED******");

	}

}

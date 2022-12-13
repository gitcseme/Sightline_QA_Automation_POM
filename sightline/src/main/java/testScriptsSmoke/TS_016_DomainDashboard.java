package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;

public class TS_016_DomainDashboard {
	Driver driver;
	LoginPage lp;
	BaseClass bc;

	String projectnamenondomain = "AutoNdomain" + Utility.dynamicNameAppender();
	String projectnamedomain = "Autodomain" + Utility.dynamicNameAppender();
	String Clientnamedomain = "C" + Utility.dynamicNameAppender();
	String domainid = "D" + Utility.dynamicNameAppender();
	String hcode = "H" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		// bt = new BaseTest();
		// Open browser
		driver = new Driver();
		// Login as PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		bc = new BaseClass(driver);
		ClientsPage client = new ClientsPage(driver);
		client.AddDomainClient(Clientnamedomain, domainid);

		ProjectPage project = new ProjectPage(driver);
		project.AddNonDomainProject(projectnamenondomain, hcode);
		project.AddDomainProject(projectnamedomain, Clientnamedomain);

		UserManagement user = new UserManagement(driver);
		user.Assignusertodomain(Clientnamedomain,Input.da1FullName);

	}

	@Test(groups = { "regression" })
	public void dataRefreshDate() throws InterruptedException {

		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.impersonateSAtoDA("Any");
		DomainDashboard dd = new DomainDashboard(driver);
		Assert.assertTrue(
				dd.getDataRefresh_info().getText().contains(java.time.LocalDate.now().toString().replaceAll("-", "/")));

	}

	@Test(groups = { "regression" })
	public void ImpersonatetoPAforDomainProject() throws InterruptedException {

		lp.logout();
		lp.loginToSightLine(Input.da1userName, Input.da1password);
		DomainDashboard dd = new DomainDashboard(driver);
		bc.selectdomain(Clientnamedomain);
		dd.ImpersonatetoPAforDomainProject();

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}
}

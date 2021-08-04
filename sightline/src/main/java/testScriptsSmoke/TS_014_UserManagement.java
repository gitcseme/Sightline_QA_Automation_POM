package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.LoginPage;
import pageFactory.UserManagement;
import pageFactory.Utility;

public class TS_014_UserManagement {

	Driver driver;
	LoginPage lp;
	UserManagement um;
	static String firstName;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input in = new Input();
		in.loadEnvConfig();
		// Open browser
		driver = new Driver();

	}

	@Test(groups = { "smoke", "regression" })
	public void asSAaddNewReviwer() throws InterruptedException {
		// user'smail account
		String newUserid = "a.ut.o.m.a.t.io.n.sightl.inec.onsilio@gmail.com";
		String newUserPwd = "consilio@123";
		// sightline password
		String sightlinepwd = "consilio@123";

		// Login as SA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// create reviewer
		um = new UserManagement(driver);
		firstName = "00AutoRev" + Utility.dynamicNameAppender();
		um.createUser(firstName, "Rev1", "Reviewer", newUserid, " ", Input.projectName);
		// activate account
		driver.Navigate().to(LoginPage.readGmailMail("Welcome ", firstName, "ActivationLink", newUserid, newUserPwd));
		driver.waitForPageToBeReady();

		// set password and login as a new user
		um.setPassword(sightlinepwd);
		lp.loginToSightLine(newUserid, sightlinepwd);
		lp.logout();
		// as a SA delete newly created user
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		um.deleteUser(firstName);
	}

	@SuppressWarnings("static-access")
	@Test(groups = { "smoke", "regression" })
	public void asSAaddNewDA() throws InterruptedException {
		// user'smail account
		String newUserid = "a.ut.o.m.a.t.io.n.s.i.gh.tl.inec.onsilio@gmail.com";
		String newUserPwd = "consilio@123";
		// sightline password
		String sightlinepwd = "consilio@123";

		// Login as SA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// create reviewer
		um = new UserManagement(driver);
		firstName = "00DA" + Utility.dynamicNameAppender();
		um.createUser(firstName, "DA", "Domain Administrator", newUserid, " ", Input.projectName);
		// activate account
		driver.Navigate().to(LoginPage.readGmailMail("Welcome ", firstName, "ActivationLink", newUserid, newUserPwd));
		driver.waitForPageToBeReady();

		// set password and login as a new user
		um.setPassword(sightlinepwd);
		// lp.logout();
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		// Edit rights
		um.selectUserToEdit(firstName);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return um.getFunctionalityTab().Visible();
			}
		}), Input.wait30);
		um.getFunctionalityTab().waitAndClick(5);
		um.modifyUserRights("Manage Project", "checkOut");
		lp.logout();

		lp.loginToSightLine(newUserid, sightlinepwd);
		// try creating project!!

		// Delete user
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		um.deleteUser(firstName);

	}

	@SuppressWarnings("static-access")
	@Test(groups = { "smoke", "regression" })
	public void asSAaddNewPA() throws InterruptedException {
		// user'smail account
		String newUserid = "a.ut.o.m.a.t.io.n.s.i.gh.tl.inec.onsilio@gmail.com";
		String newUserPwd = "consilio@123";
		// sightline password
		String sightlinepwd = "consilio@123";

		// Login as SA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// create Project admin
		um = new UserManagement(driver);
		firstName = "00DA" + Utility.dynamicNameAppender();
		um.createUser(firstName, "PA", "Project Administrator", newUserid, " ", Input.projectName);
		// activate account
		driver.Navigate().to(LoginPage.readGmailMail("Welcome ", firstName, "ActivationLink", newUserid, newUserPwd));
		driver.waitForPageToBeReady();

		// set password and login as a new user
		um.setPassword(sightlinepwd);
		// lp.logout();
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		// Edit rights
		um.selectUserToEdit(firstName);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return um.getFunctionalityTab().Visible();
			}
		}), Input.wait30);
		um.getFunctionalityTab().waitAndClick(5);
		um.modifyUserRights("Manage Project", "checkOut");
		lp.logout();

		lp.loginToSightLine(newUserid, sightlinepwd);
		// try creating project!!

		// Delete user
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		um.deleteUser(firstName);

	}

	@SuppressWarnings("static-access")
	@Test(groups = { "smoke", "regression" })
	public void asSAaddNewRMU() throws InterruptedException {
		// user'smail account
		String newUserid = "a.ut.o.m.a.t.io.n.s.i.gh.tl.inec.onsilio@gmail.com";
		String newUserPwd = "consilio@123";
		// sightline password
		String sightlinepwd = "consilio@123";

		// Login as SA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// create RMU
		um = new UserManagement(driver);
		firstName = "00DA" + Utility.dynamicNameAppender();
		um.createUser(firstName, "RMU", "Review Manager", newUserid, " ", Input.projectName);
		// activate account
		driver.Navigate().to(LoginPage.readGmailMail("Welcome ", firstName, "ActivationLink", newUserid, newUserPwd));
		driver.waitForPageToBeReady();

		// set password and login as a new user
		um.setPassword(sightlinepwd);
		// lp.logout();
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		// Edit rights
		um.selectUserToEdit(firstName);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return um.getFunctionalityTab().Visible();
			}
		}), Input.wait30);
		um.getFunctionalityTab().waitAndClick(5);
		um.modifyUserRights("Manage Project", "checkOut");
		lp.logout();

		lp.loginToSightLine(newUserid, sightlinepwd);
		// try creating project!!

		// Delete user
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		um.deleteUser(firstName);

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

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// lp.logout();
			// lp.quitBrowser();
		} finally {
			/*
			 * lp.quitBrowser(); lp.clearBrowserCache();
			 */
		}
	}
}

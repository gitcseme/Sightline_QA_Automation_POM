package testScriptsRegressionSprint22;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
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
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_IndiumRegression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	UserManagement userManage;
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;
	DomainDashboard domainDashboard;

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @Author :Indium-Baskar date: NA Modified date:23/09/2022 Modified by:23/09/2022
	 * @Description :To verify that if user is DA for one or more domains then it should 
	 *                displayed in different rows for that user
	 */
	@Test(description ="RPMXCON-52796",alwaysRun = true, groups = { "regression" })
	public void verifyingDomainNameInDiffRow() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52796");
		baseClass.stepInfo(
				"To verify that if user is DA for one or more domains then it "
				+ "should displayed in different rows for that user");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		// login as Da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(Input.da1userName);
		userManage.applyFilter();
		baseClass.waitTime(3);
		
		// verifying the grid for domain header
		int count=userManage.getTableColumnData(7).size();
		List<WebElement> data=userManage.getTableColumnData(7).FindWebElements();
		List<String> domainInt=new ArrayList<String>();
		for (WebElement webElement : data) {
			String domain=webElement.getText().toString();
			domainInt.add(domain);
		}
		if (count==domainInt.size()&&count<=2) {
			baseClass.passedStep("Mutiple rows displayed for domain header, when different domain access for same username");
		}
		else {
			baseClass.failedStep("All domain username displed in single row");
		}
	
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar date: NA Modified date:23/09/2022 Modified by:23/09/2022
	 * @Description :To verify that the Currently logged in Domain will be shown in header 
	 *                at top as selected value in Dropdown
	 */
	@Test(description ="RPMXCON-52799",alwaysRun = true, groups = { "regression" })
	public void verifyingDomainDrpDwnValue() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52799");
		baseClass.stepInfo(
				"To verify that the Currently logged in Domain will be "
				+ "shown in header at top as selected value in Dropdown");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		domainDashboard =new DomainDashboard(driver);
		
		// login as Da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.selectdomain(Input.domainName);
		
		// validation for dropdown and current value
		boolean drpdwn=domainDashboard.getDomainDrpDwn().isElementAvailable(2);
		softAssertion.assertTrue(drpdwn);
		baseClass.stepInfo("Domain dropdwon displayed in Da user");
		boolean domain=domainDashboard.getCurrentDomainValue(Input.domainName).isElementAvailable(2);
		softAssertion.assertTrue(domain);
		baseClass.stepInfo("Current domain value displayed in dropdown");
		softAssertion.assertAll();
		baseClass.passedStep("To verify that the Currently logged in Domain will be"
				+ "shown in header at top as selected value in Dropdown");
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.
	 */
	@Test(description = "RPMXCON-52792",enabled = true, groups = { "regression" })
	public void verifyDomainNameEmpty() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-52792");
	    baseClass.stepInfo("To verify that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
		
	    UserManagement user = new UserManagement(driver);
	    SoftAssert soft = new SoftAssert();
				
		String email = Input.randomText+Utility.dynamicNameAppender()+"@consilio.com";
		
		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as sa user'" + Input.sa1userName + "'");
		
		user.createNewUser(Input.randomText, Input.randomText, Input.ProjectAdministrator, email, null, Input.NonDomainProject);
		user.filterByName(email);
		driver.waitForPageToBeReady();
		soft.assertEquals(user.getTableData("DOMAIN",1),"");
		baseClass.passedStep("Domain column value is blank.");
		
		user.deleteAddedUser(Input.randomText);
	    
	    soft.assertAll();
	    baseClass.passedStep("verified that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.
	 */
	@Test(description = "RPMXCON-52793",enabled = true, groups = { "regression" })
	public void verifyDomainDisplayCrctly() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-52793");
	    baseClass.stepInfo("To verify that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
		
	    UserManagement user = new UserManagement(driver);
	    SoftAssert soft = new SoftAssert();
				
		String email = Input.randomText+Utility.dynamicNameAppender()+"@consilio.com";
		
		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as sa user'" + Input.sa1userName + "'");
		
		user.createNewUser(Input.randomText, Input.randomText, Input.ProjectAdministrator, email, Input.domainName, Input.projectName);
		user.filterByName(email);
		driver.waitForPageToBeReady();
		soft.assertEquals(user.getTableData("DOMAIN",1), Input.domainName);
		baseClass.passedStep("Domain column is populated with a correct value ");
		
		user.deleteAddedUser(Input.randomText);
	    
	    soft.assertAll();
	    baseClass.passedStep("verified that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
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
		try {
			// LoginPage.clearBrowserCache();
		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}

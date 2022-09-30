package testScriptsRegressionSprint22;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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
	

	@DataProvider(name = "sada")
	public Object[][] sada() {
		Object[][] users = {
				{ Input.sa1userName, Input.sa1password ,"sa"  },
				{ Input.da1userName, Input.da1password,"da"} };
		return users;
	}

	
	/**
	 * @Author :Indium-Baskar date: NA Modified date:26/09/2022 Modified by:26/09/2022
	 * @Description :Verify that 'Bulk User Access Control' is accessible to SA and DA
	 */
	@Test(description ="RPMXCON-52802",alwaysRun = true, dataProvider = "sada", groups = { "regression" })
	public void verifyingBulkUserTab(String userName,String password,String role) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52802");
		baseClass.stepInfo(
				"Verify that 'Bulk User Access Control' is accessible to SA and DA");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		// login as 
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		boolean bulktab=userManage.getBulkUserAccessTab().isElementAvailable(2);
		softAssertion.assertTrue(bulktab);
		baseClass.stepInfo("Bulk user tab icon displayed in"+role+" user");
		userManage.getBulkUserAccessTab().waitAndClick(5);
		boolean popup=userManage.getBulkUserPopUp().isDisplayed();
		softAssertion.assertTrue(popup);
		baseClass.stepInfo("Bulk user tab accessible for"+role+" user");
		softAssertion.assertAll();
		baseClass.passedStep("Verified that 'Bulk User Access Control' is accessible to"+role+"");
		
		// logout
		loginPage.logout();
	}
	

	/**
	 * @Author :Indium-Baskar date: NA Modified date:26/09/2022 Modified by:26/09/2022
	 * @Description :Verify that for System Admin, project drop down should show all the projects in the instances
	 */
	@Test(description ="RPMXCON-52804",alwaysRun = true,groups = { "regression" })
	public void verifyProjectDropDown() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52804");
		baseClass.stepInfo(
				"Verify that for System Admin, project drop down should show all the projects in the instances");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		// login as 
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "Project/Project");
		List<String> firstValue=new ArrayList<String>();
		String count=userManage.getProjectPageLastNumber().getText().toString();	
		int foo = Integer.parseInt(count);
		for (int i = 0; i<foo; i++) {
			boolean status=userManage.getProjectName(1).isElementAvailable(4);
			if (status == true) {
				baseClass.waitTime(3);
				List<WebElement> firstNames =userManage.getProjectName(1).FindWebElements();
				for (WebElement webElement : firstNames) {
					String assignUser= webElement.getText();
					System.out.println(assignUser);
					firstValue.add(assignUser);
				}
					if (userManage.getUserPaginationNextButton().isElementAvailable(5)) {
						userManage.getUserPaginationNextButton().waitAndClick(5);
						
					}
				}
		
			}
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.waitForElement(userManage.getBulkUserAccessTab());
		userManage.getBulkUserAccessTab().waitAndClick(5);
		String dataSet[][] = { {"Project Administrator","pa"}, {"Review Manager","rmu"},
				{ "Reviewer","rev"}};
		String role;
		String login;
		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			login = dataSet[i][j];
			j++;
			role = dataSet[i][j];
			if (role=="pa"||role=="rmu"||role=="rev") {
				baseClass.waitForElement(userManage.getSelectRollId());
				userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(login);
				baseClass.stepInfo("Selecting the role as "+login+"");
				baseClass.waitForElement(userManage.getSelectingProject());
				userManage.getSelectingProject().waitAndClick(5);
				baseClass.waitForElement(userManage.getSelectingProject());
				List<WebElement> data=userManage.getSelectingProject().selectFromDropdown().getOptions();
				baseClass.passedStep("All project are displayed in dropdown in bulk user control popup");
				if (role=="pa") {
					baseClass.waitForElement(userManage.getPaSecurityGroupDisabled());
					boolean sgDisabled=userManage.getPaSecurityGroupDisabled().isElementAvailable(2);
					softAssertion.assertTrue(sgDisabled);
					baseClass.passedStep("Security group drop down disabled for"+login+"");
				}
				if (role=="rmu"||role=="rev") {
					baseClass.waitForElement(userManage.getPaSecurityGroupDisabled());
					boolean enabled=userManage.getPaSecurityGroupDisabled().isElementAvailable(2);
					softAssertion.assertFalse(enabled);
					baseClass.passedStep("Security group drop down enabled for"+login+"");
				}
				
			}
		}
		softAssertion.assertAll();
		
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
		
		user.filterTodayCreatedUser();
		user.filterByName(email);
		user.deleteUser();
	    
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
		
		user.filterTodayCreatedUser();
		user.filterByName(email);
		user.deleteUser();
	    
	    soft.assertAll();
	    baseClass.passedStep("verified that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
	    loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:27/09/2021 Modified by: Baskar
	 * Description :Verify that error /validation message should be displayed when 
	 *              domain admin user adds system admin as domain admin user
	 */
	@Test(description ="RPMXCON-52838",alwaysRun = true, groups = { "regression" })
	public void createNewUserForDomain() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Domain Administrator";
		String emailId = Input.sa1userName;
		baseClass.stepInfo("Test case Id: RPMXCON-52838");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify that error /validation message should be displayed "
				+ "when domain admin user adds system admin as domain admin user");
		userManage = new UserManagement(driver);
		softAssertion =new SoftAssert();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		Reporter.log("Logged in as User: " + Input.da1userName);

		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.stepInfo("Create new user for domain administration, validating error message");
		userManage.validateErrorMsgForNewUser(firstName, lastName, role, emailId, " ", Input.projectName);
		baseClass.passedStep("Error validation message displayed when creating a new domain user with sa emailid");
		loginPage.logout();

	}

	/**
	* Author : Baskar date: NA Modified date:28/09/2021 Modified by: Baskar
	 * @Description : Verify that for DA user, rights should be greyed out
	 *                 as per the selected role from drop down
	 */
	@Test(description = "RPMXCON-52806",enabled = true, groups = { "regression" })
	public void verifyRightInGrayForSelectedRole() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-52806");
	    baseClass.stepInfo("Verify that for DA user, rights should be greyed out "
	    		+ "as per the selected role from drop down");
		
	    userManage = new UserManagement(driver);
		softAssertion =new SoftAssert();
		DomainDashboard dash =  new DomainDashboard(driver);		
		
		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.da1userName + "'");
		
		baseClass.selectproject(Input.domainName);
		dash.waitForDomainDashBoardIsReady();
		
		//check for rmu
		userManage.navigateToUsersPAge();
		userManage.selectRoleBulkUserAccessControl(Input.ReviewManager, Input.projectName, Input.securityGroup);
		driver.waitForPageToBeReady();
		softAssertion.assertTrue(userManage.getBulkIngestion().GetAttribute("style").contains("grey"));
		baseClass.passedStep("For RMU 'Ingestion' should be greyed out.");
		
		//check with rev
		userManage.navigateToUsersPAge();
		userManage.selectRoleBulkUserAccessControl(Input.Reviewer, Input.projectName, Input.securityGroup);
		driver.waitForPageToBeReady();
		softAssertion.assertTrue(userManage.getBulkManage().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkIngestion().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkProduction().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkCatagories().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkDataSet().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkReport().GetAttribute("style").contains("grey"));
		baseClass.passedStep("For Reviewer below rights greyed out  - Manage  - Ingestion  - Productions  - Categorize  - DataSets  - All Reports");
		softAssertion.assertAll();
	    baseClass.passedStep("Verified that for DA user, rights should be greyed out as per the selected role from drop down");
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

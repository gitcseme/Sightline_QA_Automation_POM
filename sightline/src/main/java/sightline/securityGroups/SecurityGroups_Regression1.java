package sightline.securityGroups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroups_Regression1 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
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

	/**
	 * @author Krishna Date: 01/18/21 Modified date:N/A Modified by: Description
	 *         Verify that in Domain Dashboard page, on clicking on Project from
	 *         'Active Projects' widgets, should take the DAU to Default SG in the
	 *         project
	 * 
	 */
	@Test(description = "RPMXCON-54806", enabled = true, groups = { "regression" })
	public void verifyDANavigationToProjectFromActiveProjects() throws Exception {
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54806");
		baseClass.stepInfo(
				"Verify that in Domain Dashboard page, on clicking on Project from 'Active Projects' widgets, should take the DAU to Default SG in the project");
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.selectdomain(Input.domainName);
		driver.waitForPageToBeReady();
		WebElement mytable = sgpage.daFirstBlock().getWebElement();
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		// To calculate no of rows In table.
		int rows_count = rows_table.size();
		System.out.println(rows_count);
		baseClass.waitTillElemetToBeClickable(sgpage.daFirstTabledata(rows_count - 1));
		sgpage.daFirstTabledata(rows_count - 1).waitAndClick(5);
		baseClass.waitForElement(sgpage.securityGroupTab());
		String getAttribute = sgpage.securityGroupTab().GetAttribute("title");
		if (getAttribute.equalsIgnoreCase("Default Security Group")) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain admin home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
	}

	/**
	 * @author Krishna Date: 01/18/21 Modified date:N/A Modified by: Description
	 *         Verify that as DA user, clicking on project should redirect to
	 *         default security group and able to navigate back to the Domain
	 *         Dashboard
	 * 
	 */
	@Test(description = "RPMXCON-54820", enabled = true, groups = { "regression" })
	public void verifyDANavigationToProject2() throws Exception {
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54820");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and able to navigate back to the Domain Dashboard");
		// Verifying as SA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.selectdomain(Input.domainName);
		WebElement mytable1 = sgpage.daSecondBlock().getWebElement();
		List<WebElement> rows_table = mytable1.findElements(By.tagName("tr"));
		// To calculate no of rows In table.
		int rows_count1 = rows_table.size();
		System.out.println(rows_count1);
		baseClass.waitTillElemetToBeClickable(sgpage.daSecondTabledata(rows_count1 - 1));
		sgpage.daSecondTabledata(rows_count1 - 1).waitAndClick(5);
		baseClass.waitForElement(sgpage.securityGroupTab());
		String getAttribute = sgpage.securityGroupTab().GetAttribute("title");
		if (getAttribute.equalsIgnoreCase("Default Security Group")) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain admin home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
	}

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description Verify that
	 *         On Produciton home page, Security group should displayed same as RMU
	 *         is logged in
	 * 
	 */
	@Test(description = "RPMXCON-54770", enabled = true, groups = { "regression" })
	public void verifySGForProduction() throws Exception {
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54770");
		baseClass.stepInfo(
				"Verify that On Produciton home page, Security group should displayed same as RMU is logged in");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.waitForElement(sgpage.securityGroupTab());
		String getAttribute = sgpage.securityGroupTab().GetAttribute("title");
		if (getAttribute.equalsIgnoreCase("Default Security Group")) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		baseClass.waitTillElemetToBeClickable(sgpage.productionPageSelectedSG());
		String sgname = sgpage.productionPageSelectedSG().getText();
		if (sgname.equalsIgnoreCase(getAttribute)) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}

		
	}

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description To verify by
	 *         Production is accessible by default to RMU user
	 * 
	 */
	@Test(description = "RPMXCON-54736", enabled = true, groups = { "regression" })
	public void verifyProductionOptionForRMU() throws Exception {
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54770");
		baseClass.stepInfo("To verify by Production is accessible by default to RMU user");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		if (sgpage.productionLeftMenu().Displayed()) {
			baseClass.passedStep("Productions left menu is accessible for RMU");
		} else {
			baseClass.failedStep("Productions left menu is NOT accessible for RMU");
		}
	}
	
	/**
	 * @author  Date: Modified date:N/A Modified by: Description Verify
	 *         that RMU can delete the production
	 * 
	 */
	@Test(description = "RPMXCON-54741", enabled = true, groups = { "regression" })
	public void verifyRmuCanDeleteTheProduction() throws Exception {
		baseClass.stepInfo("RPMXCON-54741");
		baseClass.stepInfo("Verify that RMU can delete the production");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String productionname2 = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");

		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname2);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		page.deleteProduction(productionname2);
	}

}

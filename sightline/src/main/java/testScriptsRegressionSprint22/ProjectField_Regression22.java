package testScriptsRegressionSprint22;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProjectField_Regression22 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	DocListPage docList;
	Input ip;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip = new Input();
		ip.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @author Brundha.T TestCase id:55695 DATE:23/09/2022
	 * @Description: To Verify For No Error Message when making inactive fields
	 *               active
	 */
	@Test(description = "RPMXCON-55695", enabled = true, groups = { "regression" })
	public void verifyingInActiveFields() throws Exception {
		baseClass.stepInfo("RPMXCON-55695 -Project fields");
		baseClass.stepInfo("To Verify For No Error Message when making inactive fields active");
		String FieldName = "AllCustodians";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProjectFieldsPage projectField = new ProjectFieldsPage(driver);
		baseClass.stepInfo("Navigating to project field page");
		projectField.navigateToProjectFieldsPage();

		if (projectField.getProjectFieldInactiveButton(FieldName).isDisplayed()) {
			projectField.inActiveProjectField(FieldName);

		}
		baseClass.stepInfo("Active the field");
		driver.waitForPageToBeReady();
		projectField.getProjectFieldactiveButton(FieldName).isElementAvailable(2);
		projectField.getProjectFieldactiveButton(FieldName).waitAndClick(5);
		projectField.getConfirmOkButton().waitAndClick(5);
		projectField.getReindexAlertOkButton().isElementAvailable(1);
		projectField.getReindexAlertOkButton().Click();
		baseClass.VerifySuccessMessage("Toggle Active State Save Success");

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(5);
		sessionSearch.getSelectMetaData().waitAndClick(5);
		baseClass.ValidateElement_Presence(sessionSearch.SelectFromDropDown(FieldName), FieldName);

		baseClass.passedStep("Verified no Error Message when making inactive fields active");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T TestCase id:55921 DATE:23/09/2022
	 * @Description: Verify that all the default fields are correctly released to a
	 *               security group whether it is Default Security group or user
	 *               created security group
	 */
	@Test(description = "RPMXCON-55921", enabled = true, groups = { "regression" })
	public void verifyingMasterDateInDoclistPage() throws Exception {
		baseClass.stepInfo("RPMXCON-55921 -Project Field");
		baseClass.stepInfo(
				"Verify that all the default fields are correctly released to a security group whether it is Default Security group or user created security group");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String ExpectedString = "MASTERDATE";

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");

		baseClass.stepInfo("Creating new Security Group");
		sg.createSecurityGroups(securityGroup);
		System.out.println(securityGroup);

		baseClass.stepInfo("Releasing the document to " + securityGroup + " in search page");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkRelease(securityGroup);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Validting the default field in Default security Group");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);

		baseClass.ValidateElementCollection_Presence(doc.getHeaderText(), "fields in doclist");
		List<String> CompareString = baseClass.availableListofElements(doc.getHeaderText());
		if (CompareString.contains(ExpectedString)) {
			baseClass.passedStep("" + ExpectedString + " is Displayed as expecetd");
		} else {
			baseClass.failedStep("" + ExpectedString + " is Displayed as expecetd");
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		baseClass.stepInfo("Assigning newly created securityGroup to RMU");
		baseClass.SelectSecurityGrp(Input.rmu1userName, securityGroup);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Validting the default field in " + securityGroup + "");
		baseClass.selectsecuritygroup(securityGroup);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		List<String> ListString = baseClass.availableListofElements(doc.getHeaderText());
		if (ListString.contains(ExpectedString)) {
			baseClass.passedStep("" + ExpectedString + " is Displayed as expecetd");
		} else {
			baseClass.failedStep("" + ExpectedString + " is Displayed as expecetd");
		}
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T TestCase id:55922 DATE:27/09/2022
	 * @Description: Verify that MasterDate is correctly showing with the values in the security group
	 */
	@Test(description = "RPMXCON-55922", enabled = true, groups = { "regression" })
	public void verifyingMasterDateValuesInDoclistPage() throws Exception {
		baseClass.stepInfo("RPMXCON-55922 -Project Field");
		baseClass.stepInfo("Verify that MasterDate is correctly showing with the values in the security group");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String ExpectedString = "MASTERDATE";

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");

		baseClass.stepInfo("Creating new Security Group");
		sg.createSecurityGroups(securityGroup);

		baseClass.stepInfo("Releasing the document to " + securityGroup + " in search page");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkRelease(securityGroup);
		sessionSearch.ViewInDocList();
		
		DocListPage doc = new DocListPage(driver);
		baseClass.ValidateElementCollection_Presence(doc.getHeaderText(), "Column Header in doclist");
		int ColValue = baseClass.getIndex(doc.getHeaderText(),ExpectedString);
		List<String> GetMasterDateValues = baseClass.availableListofElements(doc.GetColumnData(ColValue));
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Validating Master date values in Default security Group");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		doc = new DocListPage(driver);

		List<String> CompareString = baseClass.availableListofElements(doc.getHeaderText());
		if (CompareString.contains(ExpectedString)) {
            baseClass.passedStep("" + ExpectedString + " is Displayed as expecetd");
        } else {
            baseClass.failedStep("" + ExpectedString + " is Displayed as expecetd");
        }		
		int MasterDateValuesInRMU = baseClass.getIndex(doc.getHeaderText(),ExpectedString);
		List<String> GetMasterDateValuesRMU = baseClass.availableListofElements(doc.GetColumnData(MasterDateValuesInRMU));
		baseClass.listCompareEquals(GetMasterDateValues, GetMasterDateValuesRMU, "MasterDate values are displayed", "MasterDate values are not available");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		
		baseClass.stepInfo("Assigning newly created securityGroup to RMU");
		baseClass.SelectSecurityGrp(Input.rmu1userName, securityGroup);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Validating Master date values  in " + securityGroup + "");
		baseClass.selectsecuritygroup(securityGroup);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		List<String> ListString = baseClass.availableListofElements(doc.getHeaderText());
		if (ListString.contains(ExpectedString)) {
            baseClass.passedStep("" + ExpectedString + " is Displayed as expecetd");
        } else {
            baseClass.failedStep("" + ExpectedString + " is Displayed as expecetd");
        }			
		int MasterDateValuesInRMUNewSG = baseClass.getIndex(doc.getHeaderText(),ExpectedString);
		List<String> GetMasterDateValuesRMUNewSG = baseClass.availableListofElements(doc.GetColumnData(MasterDateValuesInRMUNewSG));
		
		baseClass.listCompareEquals(GetMasterDateValues, GetMasterDateValuesRMUNewSG, "MasterDate values are displayed", "MasterDate values are not available");
		baseClass.selectsecuritygroup(Input.securityGroup);
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
		System.out.println("******TEST CASES FOR PROJECTFIELD EXECUTED******");

	}
}

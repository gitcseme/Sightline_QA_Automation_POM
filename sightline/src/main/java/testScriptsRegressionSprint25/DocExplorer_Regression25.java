package testScriptsRegressionSprint25;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression25 {

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
	 * @author Vijaya.Rani ModifyDate:03/11/2022 RPMXCON-54693
	 * @throws Exception
	 * @Description Verify that “EmailRecipientNames” Filter with "Exclude"
	 *              functionality is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54693", enabled = true, groups = { "regression" })
	public void verifyEmailRecipientNamesWithExcludeInDocExplorerList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54693");
		baseClass.stepInfo(
				"Verify that “EmailRecipientNames” Filter with \"Exclude\" functionality is working correctly on Doc Explorer list.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String random = "Amit.Bandal@consilio.com";
		String random1 = "Ajay.Tiwari@symphonyteleca.com";

		driver.waitForPageToBeReady();
		String Docs = docExplorer.getDocExp_DocID().getText();
		baseClass.stepInfo("Perform exclude filter by EmailRecipientNames");
		docExplorer.performExculdeEmailRecipientNamesFilter(random,null);

		baseClass.stepInfo("Verify documents after applying exclude functionality by EmailRecipientNames");
		docExplorer.verifyExcludeFunctionlityForEmailRecipientNames(Docs);

		baseClass.stepInfo("Refresh page");
		docExplorer.refreshPage();

		baseClass.stepInfo("Perform exclude filter by EmailRecipientNames");
		docExplorer.performExculdeEmailRecipientNamesFilter(random,random1);

		baseClass.stepInfo("Verify documents after applying exclude functionality by EmailRecipientNames");
		docExplorer.verifyExcludeFunctionlityForEmailRecipientNames(Docs);

		loginPage.logout();

	}
	/**
	 * @author Brundha.T Date:04/11/2022 RPMXCON-54646
	 * @throws Exception
	 * @Description Verify the list of fields from the list view should be displayed
	 *              in capital
	 */
	@Test(description = "RPMXCON-54646", enabled = true, groups = { "regression" })
	public void verifyingCapitalInListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54646");
		baseClass.stepInfo("Verify the list of fields from the list view should be displayed in capital");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);

		docExplorer.navigateToDocExplorerPage();
		for(int i=2;i<=9;i++) {
			driver.waitForPageToBeReady();
			String ColValue=docExplorer.getListViewHeader(i).getText();
			System.out.println(ColValue);
			
			baseClass.stepInfo("The list view field is:"+ColValue);
			String CompareString=ColValue.toLowerCase();
			System.out.println(CompareString);
			
			baseClass.stepInfo("The list view field Compare value:"+CompareString);
			if(!ColValue.equals(CompareString)) {
				baseClass.passedStep("The list of fields from the list view is displayed in capital");
			}
			else {
				baseClass.failedStep("The list of fields from the list view is not displayed in capital");
			}
		}
		loginPage.logout();
	}
	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-54653
	 * @throws Exception
	 * @Description Verify that each family should be displayed together with same color coding of the rows in list view of doc explorer
	 */
	@Test(description = "RPMXCON-54653", enabled = true, groups = { "regression" })
	public void verifyFamilyDisplayedColorCodingRowsInListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54653");
		baseClass.stepInfo(
				"Verify that each family should be displayed together with same color coding of the rows in list view of doc explorer");
		String folderNumber="13";
		String folderName="Enron Data (23)";
		String folderName1="Amol (2)";

		DocExplorerPage docexp = new DocExplorerPage(driver);
		
		// Login As Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		docexp.navigateToDocExplorerPage();
		
		baseClass.stepInfo("verifying the count of documents of folder in tree strecture");
		docexp.verifyDOcExplorerFolderDocCount(folderNumber);
		
		String color = docexp.getfolderFromTreeByName(folderName).getWebElement().getCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = docexp.getfolderFromTreeByName(folderName1).getWebElement().getCssValue("background-color");
		System.out.println(ExpectedColor);
		if (color.equals(ExpectedColor)) {
			baseClass.passedStep("Each family is displayed together with same color coding of the rows in the list view of doc explorer as expected");
		} else {
			baseClass.failedStep("not displayed ");
		}
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:15/11/2022 RPMXCON-54733
	 * @throws Exception
	 * @Description Verify that “CustodianName” Column header Filter with CJK characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54733", enabled = true, groups = { "regression" })
	public void verifyCustodianNameColumnHeaderFilterWithCJKChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54733");
		baseClass.stepInfo(
				"Verify that “CustodianName” Column header Filter with CJK characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] custoName = {"新","华","社","记","者","吴","晶"};
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);

		// verify EmailRecipient names in CJK Chars
		docexp.verifyCustodianNameValuesInDocExp(custoName);
		baseClass.passedStep(
				"Verify that “CustodianName” Column header Filter with CJK characters is working Successfully on Doc Explorer list");

		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:15/11/2022 RPMXCON-54730
	 * @throws Exception
	 * @Description Verify that “EmailReceipients” Column header Filter with special characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54730", enabled = true, groups = { "regression" })
	public void verifyEmailReceipientsColumnHeaderFilterWithSpecialChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54730");
		baseClass.stepInfo(
				"Verify that “EmailReceipients” Column header Filter with special characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] specialChars = {"`","@","&",".",":","(", ")","-","'",",","/","_",};

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);

		// verify EmailRecipient names in CJK Chars
		docexp.verifyEmailRecipientValuesInDocExp(specialChars);
		baseClass.passedStep(
				"Verify that “EmailReceipients” Column header Filter with special characters is working Successfully on Doc Explorer list");

		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani  date: 15/11/2022 TestCase Id:RPMXCON-54949 
	 * Description:Verify that Exclude filter functionality works properly when Folder name contains word between on Doc Explorer screen.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54949", enabled = true, groups = { "regression" })
	public void verifyFoldersExcludeFunctionalityWorkingDocExplorer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54949");
		baseClass.stepInfo(
				"Verify that Exclude filter functionality works properly when Folder name contains word between on Doc Explorer screen.");

		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFolder.CreateFolder(random, Input.securityGroup);

		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.waitForPageToBeReady();
		String Docs = docexp.getDocExp_DocID().getText();

		baseClass.stepInfo("Perform Exclude filter by folder");
		driver.waitForPageToBeReady();
		docexp.performExculdeFolderFilter(random);

		baseClass.stepInfo("Verify documents after applying Exclude functionality by folder");
		driver.waitForPageToBeReady();
		docexp.verifyIncludeFunctionlityForDocFileType(Docs);
		baseClass.passedStep("Exclude filter functionality is work properly and Records is filtered according to earlier steps on Doc Explorer screen");

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

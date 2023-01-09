package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;


import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Consilio {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	ProjectPage projectPage;
	SoftAssert softAssertion;
	DocListPage docList;
	SavedSearch savedSearch;
	
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
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		docexp = new DocExplorerPage(driver);
		docList = new DocListPage(driver);
		savedSearch = new SavedSearch(driver);
	}

	@Test(description = "RPMXCON-70306", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterfieldsInDocList(String username, String password, String role) throws InterruptedException {
		baseClass = new BaseClass(driver);
		String searchnameA = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchnameA;
		
		baseClass.stepInfo("Test case Id: RPMXCON-70306: Verify that a filter box to each column are present below each field in DocList page/List view screen. ");
		
		//Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		docexp.navigateToDocExplorerPage();
		baseClass.stepInfo("Navigated to Doc Explorer page");
		driver.waitForPageToBeReady();
		docexp.SelectingAllDocuments("yes");
		docexp.docExpViewInDocList();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docList.getTileView().Visible();
			}
		}), Input.wait60);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(docList.getFilterFileds().isDisplayed(),true);
		baseClass.passedStep("Verified that filter box is present in DocList page");
				
		baseClass.waitTime(3);
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.saveSearch(searchname);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Navigated to DocList Page");
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(docList.getFilterFileds().isDisplayed(),true);
		baseClass.passedStep("Verified that filter box is present in DocList page");
		
		baseClass.waitTime(3);
		savedSearch.savedSearchToDocList(searchname);
		docList = new DocListPage(driver);
		baseClass.stepInfo("Navigated to DocList Page from Saved search");
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(docList.getFilterFileds().isDisplayed(),true);
		baseClass.passedStep("Verified that filter box is present in DocList page");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	@Test(description = "RPMXCON-70307", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyApplyFilterInDocList(String username, String password, String role) throws InterruptedException {
        String custodianexp= Input.custodianName_Andrew;
		baseClass.stepInfo("Test case Id: RPMXCON-70307: Verify that to apply the filters, user can either hit the ‘Apply Filter’ button or hit the ‘enter’ key on the keyboard. ");
		
		//Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		docexp.navigateToDocExplorerPage();
		baseClass.stepInfo("Navigated to Doc Explorer page");
		driver.waitForPageToBeReady();
		docexp.SelectingAllDocuments("yes");
		docexp.docExpViewInDocList();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docList.getTileView().Visible();
			}
		}), Input.wait60);
		baseClass.stepInfo("Navigated to DocList page");
		driver.waitForPageToBeReady();
		docList.custodianFilter(Input.custodianName_Andrew);
		docList.getApplyFilter().waitAndClick(3);;
		driver.waitForPageToBeReady();
		String custodianactual = docList.AfterFilterverifyCustodianName(Input.custodianName_Andrew);
		softAssertion.assertEquals(custodianexp,custodianactual);
		System.out.println(custodianactual);
		baseClass.passedStep("Filter is performed with Custodian Name : "+custodianexp);
		baseClass.passedStep("Filter is applied and Verified");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	@Test(description = "RPMXCON-70310", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
		public void verifyApplyFilterWithTWoFieldsInDocList(String username, String password, String role) throws InterruptedException {
	        String custodianexp= Input.metaDataCN;
	        String fileType= "Text File";
			baseClass.stepInfo("Test case Id: RPMXCON-70310: Verify that User can filter across multiple field names.This action should be treated as an ‘AND’ operator to filter the documents.");
			
			//Login As user
			loginPage.loginToSightLine(username, password);
			baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
			
			docexp.navigateToDocExplorerPage();
			baseClass.stepInfo("Navigated to Doc Explorer page");
			driver.waitForPageToBeReady();
			docexp.SelectingAllDocuments("yes");
			docexp.docExpViewInDocList();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return docList.getTileView().Visible();
				}
			}), Input.wait60);
			baseClass.stepInfo("Navigated to DocList page");
			driver.waitForPageToBeReady();
			docList.custodianFilter(Input.metaDataCN);
			docList.docfiletypeFilter(fileType);
			docList.getApplyFilter().waitAndClick(3);
			
			driver.waitForPageToBeReady();
			String custodianactual = docList.AfterFilterverifyCustodianName(Input.metaDataCN);
			softAssertion.assertEquals(custodianexp,custodianactual);
			System.out.println(custodianactual);
			
			String actualFiletype = docList.AfterFilterverifyDocFileType(fileType);
			softAssertion.assertEquals(fileType,actualFiletype);
			System.out.println(actualFiletype);
			baseClass.passedStep("Filter is performed with Custodian Name : "+custodianexp+" and DocFiletype : "+actualFiletype);
			baseClass.passedStep("Filter is applied and Verified");
			softAssertion.assertAll();
			loginPage.logout();
		}
	
		@Test(description = "RPMXCON-70308", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
		public void verifyAfterApplyFilterInDocListandClearField(String username, String password, String role) throws InterruptedException {
	        String custodianexp= Input.custodianName_Andrew;
			baseClass.stepInfo("Test case Id: RPMXCON-70308: Verify that When user deletes text from field and hits apply/enter, the DocList grid should refresh and load the default view of the page/List");
			
			//Login As user
			loginPage.loginToSightLine(username, password);
			baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
			
			docexp.navigateToDocExplorerPage();
			baseClass.stepInfo("Navigated to Doc Explorer page");
			driver.waitForPageToBeReady();
			docexp.SelectingAllDocuments("yes");
			docexp.docExpViewInDocList();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return docList.getTileView().Visible();
				}
			}), Input.wait60);
			baseClass.stepInfo("Navigated to DocList page");
			driver.waitForPageToBeReady();
			docList.custodianFilter(Input.custodianName_Andrew);
			docList.getApplyFilter().waitAndClick(3);
			String custodianactual = docList.AfterFilterverifyCustodianName(Input.custodianName_Andrew);
			softAssertion.assertEquals(custodianexp,custodianactual);
			System.out.println(custodianactual);
			baseClass.passedStep("Filter is performed with Custodian Name : "+custodianexp);
			baseClass.passedStep("Filter is applied and Verified");
			softAssertion.assertAll();
			
			driver.waitForPageToBeReady();
			docList.CustodianNameFilterFileds().Clear();
			docList.getApplyFilter().waitAndClick(3);
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(docList.getDocListDefaultTable().isDisplayed(),true);
			baseClass.passedStep("Removed applied text from the filter fields and Verified the Default view of the page/List");
			softAssertion.assertAll();
			loginPage.logout();
		}
		
		
		@Test(description = "RPMXCON-70335", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
		public void verifyCJKFilterInDocList(String username, String password, String role) throws InterruptedException {
	        String custodianexp= "新华社记者吴晶";
			baseClass.stepInfo("Test case Id: RPMXCON-70335: Verify that filter is working with the CJK text in DocList page.");
			
			//Login As user
			loginPage.loginToSightLine(username, password);
			baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
			baseClass.selectprojectCjk();
			
			docexp.navigateToDocExplorerPage();
			baseClass.stepInfo("Navigated to Doc Explorer page");
			driver.waitForPageToBeReady();
			docexp.SelectingAllDocuments("yes");
			docexp.docExpViewInDocList();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return docList.getTileView().Visible();
				}
			}), Input.wait60);
			baseClass.stepInfo("Navigated to DocList page");
			driver.waitForPageToBeReady();
			docList.custodianFilter(custodianexp);
			docList.getApplyFilter().waitAndClick(3);
			String custodianactual = docList.AfterFilterverifyCustodianName(custodianexp);
			softAssertion.assertEquals(custodianexp,custodianactual);
			System.out.println(custodianactual);
			baseClass.passedStep("Filter is performed with Custodian Name : "+custodianexp);
			baseClass.passedStep("Filter is applied and Verified");
			softAssertion.assertAll();
			loginPage.logout();
		}
		
	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}

package testScriptsRegressionSprint21;

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
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_6 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	SecurityGroupsPage securityGroup;
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
		sessionSearch = new SessionSearch(driver);
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 09/09/2022 TestCase Id:RPMXCON-63253
	 * Description :Validate whether all the DAT fields from DAT File selected during "Over lay" ingestion is displayed
	 * under Source DAT fields in Configure Field Mapping of Ingestion Wizard Page.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63253",enabled = true, groups = { "regression" })
	public void verifyRepeatedDestinationFieldErrorWhenOverlay() throws InterruptedException {
		
		String[] ExpectedDatFields={"DocID","RecBegAttach","RecEndAttach","ProdBeg","ProdEnd","ProdBegAttach","ProdEndAttach","AttachCount","DocPages","Custodian","Date",
				"MasterDate","Author","AuthorAddress","RecipientName","RecipientAddress","CCName","CCAddress","BCCName","BCCAddress","Subject","Container","DataSet","Datasource",
				"FullPath","NativeLink","FileType","FileName","FileExt","FileSize","M_Duration","ReceivedDate","ApptSTDate","ApptEndDate","MessageID","MessageType","CreateDate",
				"LastAccDate","LastEditDate","LastSaveDate","LastPrintedDate","MD5Hash","Title","Comments","Company","Keywords","Manager","Category","RevNumber",
				"EmailCCNameAndAddress","EmailBCCNameAndAddress","EmailAuthorNameandAddress","DocumentSubject","EmailConversationIndex","EmailFamilyConversationIndex",
				"EmailToNameAndAddress","Native Path","Extracted Text","FamilyVirusStatus","ProcessingPlaceholders","ZzrecipientsID","ZzsenderID"};

		baseClass.stepInfo("Test case Id: RPMXCON-63253");
		baseClass.stepInfo("Verify DAT fields in configure mapping page.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion with type as 'Overlay'");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.nuix, Input.sourceLocation, 
				Input.folder61759);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile6, Input.prodBeg);
		ingestionPage.selectDateAndTimeForamt(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify dat fields available in source field mapping in configuring page" );
		baseClass.waitForElement(ingestionPage.getMappingSourceField(2));
		for(int i=2;i<ExpectedDatFields.length;i++) {
			ingestionPage.getMappingSourceField(i).selectFromDropdown().selectByVisibleText(ExpectedDatFields[i]);
			String field =ingestionPage.getMappingSourceField(i).selectFromDropdown().getFirstSelectedOption().getText();
			if(field.equalsIgnoreCase(ExpectedDatFields[i])) {
				baseClass.passedStep("Dat field available :"+ExpectedDatFields[i]);
			}
			else {
				baseClass.failedStep("Dat field not available"+ExpectedDatFields[i]);
			}
		}
		baseClass.passedStep("All the DAT fields available in configure mapping page");
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

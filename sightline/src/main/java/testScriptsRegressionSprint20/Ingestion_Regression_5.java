package testScriptsRegressionSprint20;

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
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_5 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
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
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49773
	 * Description :Verify concatenated email value should be displayed correctly for CCName and CCAddress 
	 * fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49773",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValueForCCField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49773");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailCCAddresses","EmailCCNames","EmailCCNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.datLoadFile4,Input.nativeLoadFile3,
					Input.textLoadFile3);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Click dataset and go to doclist");
		dataSets = new DataSets(driver);
		dataSets.selectDataSetWithName(ingestionName);
		baseClass.stepInfo("verify concatenated email values in doclist");
		ingestionPage.verifyConcatenatedEmailValueDisplayed(values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49774
	 * Description :Verify concatenated email value should be displayed correctly for 
	 * BCCName and BCCAddress fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49774",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValueForBCCField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49774");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailBCCAddresses","EmailBCCNames","EmailBCCNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.datLoadFile4,Input.nativeLoadFile3,
					Input.textLoadFile3);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Click dataset and go to doclist");
		dataSets = new DataSets(driver);
		dataSets.selectDataSetWithName(ingestionName);
		baseClass.stepInfo("verify concatenated email values in doclist");
		ingestionPage.verifyConcatenatedEmailValueDisplayed(values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49775
	 * Description :Verify concatenated email value should be displayed correctly for ToName and ToAddress 
	 * fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49775",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValueForToField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49775");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailToAddresses","EmailToNames","EmailToNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.datLoadFile4,Input.nativeLoadFile3,
					Input.textLoadFile3);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Click dataset and go to doclist");
		dataSets = new DataSets(driver);
		dataSets.selectDataSetWithName(ingestionName);
		baseClass.stepInfo("verify concatenated email values in doclist");
		ingestionPage.verifyConcatenatedEmailValueDisplayed(values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49776
	 * Description :Verify concatenated email value should be displayed correctly for AuthorName and AuthorAddress 
	 * fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49776",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValueForAuthorField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49776");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailAuthorAddress","EmailAuthorName","EmailAuthorNameAndAddress"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.datLoadFile4,Input.nativeLoadFile3,
					Input.textLoadFile3);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Click dataset and go to doclist");
		dataSets = new DataSets(driver);
		dataSets.selectDataSetWithName(ingestionName);
		baseClass.stepInfo("verify concatenated email values in doclist");
		ingestionPage.verifyConcatenatedEmailValueDisplayed(values);
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

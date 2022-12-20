package testScriptsRegressionSprint28;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_11 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	Input ip;
	TallyPage tally;
	DocExplorerPage docExplorer;
	ProjectPage project;
	UserManagement userManage;
	SecurityGroupsPage securityGroup;

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
		ingestionPage = new IngestionPage_Indium(driver);
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 18/12/2022 TestCase Id:RPMXCON-49072 
	 * Description :To verify that Ingestion overlay with DAT only
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49072", enabled = true, groups = { "regression" })
	public void verifyIngestionOverlaywithUpdatedDatOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49072");
		baseClass.stepInfo("To verify that Ingestion overlay with DAT only.");
		String[] metadata = {Input.metaDataName};
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with overwrite option as 'Add only'.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.Collection1KFolder);
		if (status == false) {
			ingestionPage.performCollection1kTallyIngestion(Input.sourceSystem, Input.datLoadFile3, Input.textFile1);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.Collection1KFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.Collection1KFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion with modified dat file");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.Collection1KFolder, Input.overlayDatFile,
				Input.docId);
		ingestionPage.updateMappingSection(2,Input.custodian,Input.ingDocBasic, Input.documentKeyCName);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay ingestion performed successfully");
		baseClass.stepInfo("verify updated metadata");
		int count =sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.ViewInDocList();
		docList = new DocListPage(driver);
		baseClass.stepInfo("select columns");
		docList.SelectColumnDisplayByRemovingAddNewValues(metadata);
		String updatedData=docList.getDoclistMetaDataValue(count,4);
		baseClass.passedStep("updated metadata value--"+updatedData);
		baseClass.compareTextViaContains(updatedData, "OverlayCName" , 
				"updated metadata values displayed", "updated metadata values not displayed");
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 19/12/2022 TestCase Id:RPMXCON-68079 
	 * Description :Unrelease only few of the documents from user created SG and run security group 
	 * analytics still the project field should be searchable while running analytics & analytics 
	 * should complete
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-68079", enabled = true, groups = { "regression" })
	public void verifyUnreleaseFewDocsAndPerformAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-68079");
		baseClass.stepInfo("Unrelease only few of the documents from user created SG and run security group analytics");
		String userSG = "userCreatedSG"+Utility.dynamicNameAppender();
		String ingestionName = null;
		String[] metadata = {"EmailDuplicateDocIDs","EmailInclusiveReason","EmailInclusiveScore"};
		List<String> metaValue = new ArrayList<>();
		securityGroup = new SecurityGroupsPage(driver);
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");	
		//Pre-Requisite
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.AddSecurityGroup(userSG);
		baseClass.stepInfo("docs should be ingested and release to user created SG");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, 
					Input.specialCjkDat);
			baseClass.stepInfo("Publish docs");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(userSG);
		//verification
		baseClass.stepInfo("unrelease few docs from user created SG");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.documentSelection(1);
		docList.bulkUnRelease(userSG);
		baseClass.stepInfo("get value for project fields search");
		docList.SelectColumnDisplayByRemovingExistingOnes(metadata);
		for(int i=4;i<=6;i++) {
			String value =docList.getDoclistMetaDataValue(count, i);
			metaValue.add(value);
		}
		System.out.println(metaValue);
		baseClass.stepInfo("add SG analytics to background");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.startRegenerateAnalyticsAtSgLevel(userSG);
		baseClass.stepInfo("verify project fields should be searchable");
		for(int j=0;j<metadata.length;j++) {
			int result = sessionSearch.metaDataSearchInBasicSearch(metadata[j],metaValue.get(j));
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(result);
			baseClass.passedStep(metadata[j]+"--Project field is searchable while running analytics");
			sessionSearch.addNewSearch();
		}
		baseClass.stepInfo("verify analytics SG completed");
		securityGroup.verifyRegenerateAnalyticsStatus();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/12/2022 TestCase Id:RPMXCON-68077 
	 * Description :Unrelease all the documents from user created SG and run security group analytics 
	 * still the project field should be searchable while running analytics
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-68077", enabled = true, groups = { "regression" })
	public void verifyUnreleaseAllDocsAndPerformAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-68077");
		baseClass.stepInfo("Unrelease all the documents from user created SG and run security group analytics");
		String userSG = "userCreatedSG"+Utility.dynamicNameAppender();
		String ingestionName = null;
		String[] metadata = {"EmailDuplicateDocIDs","EmailInclusiveReason","EmailInclusiveScore"};
		List<String> metaValue = new ArrayList<>();
		securityGroup = new SecurityGroupsPage(driver);
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");	
		//Pre-Requisite
		baseClass.stepInfo("project field created and release to created SG");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.AddSecurityGroup(userSG);
		baseClass.stepInfo("docs should be ingested and release to SG");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, 
					Input.specialCjkDat);
			baseClass.stepInfo("Publish docs");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(userSG);
		//verification
		baseClass.stepInfo("unrelease all docs from user created SG");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.documentSelection(count);
		docList.bulkUnRelease(userSG);
		baseClass.stepInfo("get value for project fields search");
		docList.SelectColumnDisplayByRemovingExistingOnes(metadata);
		for(int i=4;i<=6;i++) {
			String value =docList.getDoclistMetaDataValue(count, i);
			metaValue.add(value);
		}
		System.out.println(metaValue);
		baseClass.stepInfo("add SG analytics to background");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.startRegenerateAnalyticsAtSgLevel(userSG);
		baseClass.stepInfo("verify project fields should be searchable");
		for(int j=0;j<metadata.length;j++) {
			int result = sessionSearch.MetaDataSearchInBasicSearch(metadata[j],metaValue.get(j));
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(result);
			baseClass.passedStep(metadata[j]+"--Project field is searchable while running analytics");
			sessionSearch.addNewSearch();
		}
		baseClass.stepInfo("verify analytics SG failed");
		securityGroup.verifyRegenerateAnalyticsFailedStatus();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/12/2022 TestCase Id:RPMXCON-67981 
	 * Description :create a EmailInclusiveScore or EmailDuplicateDocIDs or EmailInclusiveReason 
	 * project field should be searchable while default security group analytics is running.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-67981", enabled = true, groups = { "regression" })
	public void verifyreleaseFewDocsAndPerformAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-67981");
		baseClass.stepInfo("Unrelease all the documents from user created SG and run security group analytics");
		String ingestionName = null;
		securityGroup = new SecurityGroupsPage(driver);
		String[] metadata = {"EmailDuplicateDocIDs","EmailInclusiveReason","EmailInclusiveScore"};
		List<String> metaValue = new ArrayList<>();
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");	
		//Pre-Requisite
		baseClass.stepInfo("docs should be ingested and no docs release to SG");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, 
					Input.specialCjkDat);
			baseClass.stepInfo("Publish docs");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		//verification
		baseClass.stepInfo("release few docs to default SG");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.documentSelection(1);
		docList.docListToBulkRelease(Input.securityGroup);
		baseClass.stepInfo("get value for project fields search");
		docList.SelectColumnDisplayByRemovingExistingOnes(metadata);
		for(int i=4;i<=6;i++) {
			String value =docList.getDoclistMetaDataValue(count, i);
			metaValue.add(value);
		}
		System.out.println(metaValue);
		baseClass.stepInfo("add SG analytics to background");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.startRegenerateAnalyticsAtSgLevel(Input.securityGroup);
		baseClass.stepInfo("verify project fields should be searchable");
		for(int j=0;j<metadata.length;j++) {
			int result = sessionSearch.MetaDataSearchInBasicSearch(metadata[j],metaValue.get(j));
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(result);
			baseClass.passedStep(metadata[j]+"--Project field is searchable while running analytics");
			sessionSearch.addNewSearch();
		}
		baseClass.stepInfo("verify analytics SG completed");
		securityGroup.verifyRegenerateAnalyticsStatus();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/12/2022 TestCase Id:RPMXCON-68078 
	 * Description :Unrelease only few of the documents from default SG and run security group 
	 * analytics still the project field should be searchable while running analytics & analytics 
	 * should complete
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-68078", enabled = true, groups = { "regression" })
	public void verifyUnreleaseFewDocsFromDefaultSGAndPerformAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-68078");
		baseClass.stepInfo("Unrelease only few of the documents from default SG and run security group analytics");
		
		String ingestionName = null;
		String[] metadata = {"EmailDuplicateDocIDs","EmailInclusiveReason","EmailInclusiveScore"};
		List<String> metaValue = new ArrayList<>();
		securityGroup = new SecurityGroupsPage(driver);
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");	
		//Pre-Requisite
		baseClass.stepInfo("docs should be ingested and release to SG");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, 
					Input.specialCjkDat);
			baseClass.stepInfo("Publish docs");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(Input.securityGroup);
		//verification
		baseClass.stepInfo("unrelease few docs from SG");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.documentSelection(1);
		docList.bulkUnRelease(Input.securityGroup);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("get value for project fields search");
		docList.SelectColumnDisplayByRemovingExistingOnes(metadata);
		for(int i=4;i<=6;i++) {
			String value =docList.getDoclistMetaDataValue(count, i);
			metaValue.add(value);
		}
		System.out.println(metaValue);
		baseClass.stepInfo("add SG analytics to background");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.startRegenerateAnalyticsAtSgLevel(Input.securityGroup);
		baseClass.stepInfo("verify project fields should be searchable");
		for(int j=0;j<metadata.length;j++) {
			int result = sessionSearch.MetaDataSearchInBasicSearch(metadata[j],metaValue.get(j));
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(result);
			baseClass.passedStep(metadata[j]+"--Project field is searchable while running analytics");
			sessionSearch.addNewSearch();
		}
		baseClass.stepInfo("verify analytics SG completed");
		securityGroup.verifyRegenerateAnalyticsStatus();
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

package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_10 {

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
	 * Author :Arunkumar date: 05/12/2022 TestCase Id:RPMXCON-47400
	 * Description :To verify Preview Records pop up display.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47400",enabled = true, groups = { "regression" })
	public void verifyPreviewRecordIngestionPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47400");
		baseClass.stepInfo("verify Preview Records pop up display.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details and click on next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.AllSourcesFolder,Input.DATFile1);
		baseClass.stepInfo("perform mapping and verify mapping section");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg,Input.prodBeg,Input.custodian);
		ingestionPage.verifyAutoPopulatedSourceFieldInMappingSection();
		baseClass.passedStep("Source field gets auto populated as per the fields available in the DAT file.");
		//preview record popup verification
		baseClass.stepInfo("verify values present in preview record popup and click on back");
		ingestionPage.verifyHeaderCountInPreviewRecordPopupPage();
		baseClass.elementNotdisplayed(ingestionPage.previewRecordPopup(), "preview record popup");
		baseClass.passedStep("Preview record popup closed successfully without any errors");
		baseClass.stepInfo("Run ingestion and verify URL");
		String UrlBeforeRun = driver.getUrl();
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		String UrlAfterRun = driver.getUrl();
		baseClass.textCompareNotEquals(UrlBeforeRun, UrlAfterRun, "URL changed after running ingestion", 
				"Url not changed after run ingestion");
		baseClass.verifyUrlLanding(Input.url + "en-us/Ingestion/Home", 
				"Navigated to Ingestion home page from wizard page successfully", "not in ingestion home page");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/12/2022 TestCase Id:RPMXCON-47567
	 * Description :To Verify Ingestion Status in Ingestion Detail Page .
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47567",enabled = true, groups = { "regression" })
	public void verifyIngestionStatusInDetailPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47567");
		baseClass.stepInfo("To Verify Ingestion Status in Ingestion Detail Page .");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with dat and text file");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.attachDocFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datFile7, Input.sourceDocIdSearch);
		baseClass.stepInfo("Selecting Text file");
		ingestionPage.selectTextSource("Text.lst", false);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("verify ingestion status in detail popup");
		ingestionPage.ingestionAtCatlogState(Input.attachDocFolder);
		ingestionPage.verifyFailedIngestionStatusInPopup();
		loginPage.logout();
	}
	
	@DataProvider(name = "users")
	public Object[][] Users() {
		return new Object[][] { { Input.pa1userName, Input.pa1password, "PA" },
								{ Input.rmu1userName, Input.rmu1password, "RMU" } };
	}

	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-48926
	 * Description :Validate new metadata field DocLanguages
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48926",dataProvider = "users",enabled = true, groups = { "regression" })
	public void verifyDocLanguagesMetadata(String userName, String password, String role) throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48926");
		baseClass.stepInfo("Validate new metadata field DocLanguages");
		String field = "DocLanguages";
		String tagName = "tag"+Utility.dynamicNameAppender();
		tally = new TallyPage(driver);
		docExplorer = new DocExplorerPage(driver);
		
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as "+role);
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("go to search and check field availability in metadata");
		sessionSearch.navigateToSessionSearchPageURL();
		baseClass.waitForElement(sessionSearch.getBasicSearch_MetadataBtn());
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(10);
		baseClass.ValidateElement_Presence(sessionSearch.getMetaDataInDropdown(field), "doclanaguages metadata");
		baseClass.stepInfo("go to report-tally and check field availability in metadata");
		tally.verifyMetaDataAvailabilityInTallyReport(field);
		baseClass.stepInfo("perform tally by metadata");
		if(role.equalsIgnoreCase("PA")) {
		tally.verifyTallyReportGenerationForMetadata(field, "project");
		}
		else if(role.equalsIgnoreCase("RMU")){
			tally.verifyTallyReportGenerationForMetadata(field, "security group");
		}
		//verify purehit count with tally report for metadata
		tally.performTallyAndSearchForMetadata(field);
		baseClass.passedStep("pure hit count displayed correctly for metadata"+field);
		baseClass.stepInfo("perform tally by tags");
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion("DocLanguagesExistingData", "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.selectAllDocsAndBulkTagFromDoclist(tagName);
		//docview verification
		baseClass.stepInfo("perform tally by tagname and subtally with metadata");
		tally.performTallyByTagAndSubTallyByMetadata(role,tagName,field);
		baseClass.waitForElement(tally.getTally_btnSubTallyAll());
		tally.getTally_btnSubTallyAll().waitAndClick(10);
		baseClass.stepInfo("Navigate to docview and verify doclanguages metadata");
		tally.subTallyNavigation("docview");
		ingestionPage.verifyDocLanguagesMetadata("docview",count);
		//doclist verification
		baseClass.stepInfo("perform tally by tagname and subtally with metadata");
		tally.performTallyByTagAndSubTallyByMetadata(role,tagName,field);
		baseClass.waitForElement(tally.getTally_btnSubTallyAll());
		tally.getTally_btnSubTallyAll().waitAndClick(10);
		baseClass.stepInfo("Navigate to doclist and verify doclanguages metadata");
		tally.subTallyNavigation("doclist");
		ingestionPage.verifyDocLanguagesMetadata("doclist",count);
		loginPage.logout();		
	}
	
	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-48305
	 * Description :To verify that new ingested field "EmailConversationIndex", value will be 
	 * ingested from NUIX, should be done successfully. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48305",enabled = true, groups = { "regression" })
	public void verifyEmailConversationIndexNuixValue() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48305");
		baseClass.stepInfo("To verify that 'EmailConversationIndex' value ingested from NUIX.");
		String ingestionName = null;
		String[] value = {"EmailConversationIndex"};
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with PDF");
		boolean status = ingestionPage.verifyIngestionpublish("EmailConversationIndex_GD");
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix,
					Input.sourceLocation, "EmailConversationIndex_GD");
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource("GDEmailConversation_DAT.dat", Input.documentKey);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName("EmailConversationIndex_GD");
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		//performing search with ingestion name to filter only the docs with emailconversation index
		baseClass.stepInfo("perform search and navigate to doclist");
		int count =sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.ViewInDocList();
		docList = new DocListPage(driver);
		baseClass.stepInfo("select columns");
		docList.SelectColumnDisplayByRemovingAddNewValues(value);
		for(int i=1;i<=count;i++) {
			String emailIndex = docList.getDataInDoclist(1,4).getText();
			baseClass.stepInfo("Email conversation index value-"+emailIndex);
			if(emailIndex.isEmpty()) {
				baseClass.failedStep("Email conversation index value not displayed");
			}
			else {
				baseClass.passedStep("Email conversation index value displayed");
				break;
			}
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-60895
	 * Description :check user should not obtain any error in any stage of ingestion phase while 
	 * ingesting DAT file with fullpath metadata containing less than 400 chars in size 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60895",enabled = true, groups = { "regression" })
	public void verifyDatIngestionLessThan400Chars() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-60895");
		baseClass.stepInfo("Verify ingestion Dat file data having less than 400 chars.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion with less than 400 char dat");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
			baseClass.stepInfo("Perform catalog,copy,indexing and approve ingestion");
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ignoreErrorsAndIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			baseClass.stepInfo("Publish ingestion");
			ingestionPage.runFullAnalysisAndPublish();
			baseClass.passedStep("Ingestion Published Successfully");
		}
		else {
			baseClass.passedStep("Ingestion already published in the project"+Input.ingestDataProject);
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 08/12/2022 TestCase Id:RPMXCON-48585
	 * Description :Verify that PA Users go to the Advance Search screen,  the Advance Search page 
	 * will show a count of unique DocIDs that are in the Project 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48585",enabled = true, groups = { "regression" })
	public void verifyUniqueDocIdsCountDisplayAsPA() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48585");
		baseClass.stepInfo("Verify count of unique DocIDs present in Project as PA user");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem, Input.DATFile1, Input.prodBeg);
			baseClass.stepInfo("Publish docs");
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Navigate to search>>Advanced search");
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("verify count of unique DocIDs present in project");
		sessionSearch.getUniqueDocCountAsDifferentUser("PA");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 08/12/2022 TestCase Id:RPMXCON-48586
	 * Description :check user should not obtain any error in any stage of ingestion phase while 
	 * ingesting DAT file with fullpath metadata containing less than 400 chars in size 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48586",enabled = true, groups = { "regression" })
	public void verifyUniqueDocIdsCountDisplayAsRMU() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48586");
		baseClass.stepInfo("Verify count of unique DocIDs present in Project as RMU user");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem, Input.DATFile1, Input.prodBeg);
			baseClass.stepInfo("Publish docs");
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Release published docs to security group");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkRelease(Input.securityGroup);
		loginPage.logout();
		baseClass.stepInfo("Login to the application as Rmu");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("navigate to search screen and verify");
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.getUniqueDocCountAsDifferentUser("RMU");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/12/2022 TestCase Id:RPMXCON-47391
	 * Description :To verify that on Ingestion Home page, on scrolling down next 10 tiles are 
	 * loaded and displayed and with sort options
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47391",enabled = true, groups = { "regression" })
	public void verifyDefault10TilesAndLoadMoreOption() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47391");
		baseClass.stepInfo("Verify default tiles count in ingestion home page");
		int tilesCount;
		int ingestionCount;
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		driver.waitForPageToBeReady();
		//pre-requisite -checking number of ingestion tiles is more than 10
		ingestionPage.applyFilters();
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		baseClass.waitForElement(ingestionPage.getTotalIngestionCount());
		ingestionCount = Integer.parseInt(ingestionPage.getTotalIngestionCount().getText());
		baseClass.stepInfo("Ingestion tiles present  in home page--"+ingestionCount);
		if(ingestionCount<=10) {
			baseClass.stepInfo("need to add ingestion");
			for(int i=ingestionCount+1;i<=11;i++) {
				ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
						Input.sourceLocation, Input.HiddenPropertiesFolder);
				ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
				baseClass.stepInfo("Selecting Dat and Native file");
				ingestionPage.selectDATSource(Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
				ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
				ingestionPage.clickOnNextButton();
				baseClass.waitForElement(ingestionPage.getIngestion_SaveAsDraft());
				ingestionPage.getIngestion_SaveAsDraft().waitAndClick(10);
				if(ingestionPage.getApproveMessageOKButton().isElementAvailable(10)) {
					ingestionPage.getApproveMessageOKButton().waitAndClick(5);
					}
				baseClass.VerifySuccessMessage("Your changes to the ingestion were successfully saved.");
			}
			baseClass.stepInfo("Navigate to ingestion home screen");
			ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
			ingestionPage.applyFilters();
		}
		else if(ingestionCount>10) {
			baseClass.passedStep("Ingestion home page have more than 10 tiles");
		}
		baseClass.stepInfo("scroll down and verify default tiles count");
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		tilesCount = ingestionPage.getIngestionTilesCount().size();
		baseClass.stepInfo("Default tiles Count--"+tilesCount);
		baseClass.stepInfo("Click on load more button and verify ingestion tiles count");
		ingestionPage.clickOnLoadMoreAndRefresh();
		tilesCount = ingestionPage.getIngestionTilesCount().size();
		baseClass.stepInfo("Ingestion tiles count--"+tilesCount);
		ingestionCount = Integer.parseInt(ingestionPage.getTotalIngestionCount().getText());
		baseClass.stepInfo("Number of ingestion present--"+tilesCount);
		baseClass.digitCompareEquals(tilesCount, ingestionCount, 
				"All the existing ingestions displayed", "existing ingestions not displayed");
		baseClass.stepInfo("Select each option in sort by dropdown and verify default tiles count");
		ingestionPage.verifyDefaultTilesCountForDifferentSortOptions();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/12/2022 TestCase Id:RPMXCON-47301
	 * Description :To Verify the reload of the ingestion with the 'Refresh' option.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47301",enabled = true, groups = { "regression" })
	public void verifyIngestionRefreshOption() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47301");
		baseClass.stepInfo("Verify the reload of the ingestion with the 'Refresh' option.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("try to perform ingestion once");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem, Input.DATFile1, Input.prodBeg);
			baseClass.stepInfo("Publish docs");
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("after performing ingestion,select all filter");
		ingestionPage.navigateToIngestionPage();
		ingestionPage.applyFilters();
		baseClass.stepInfo("click on refresh link available in home Page");
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		baseClass.waitForElement(ingestionPage.getRefreshButton());
		ingestionPage.getRefreshButton().waitAndClick(10);
		baseClass.stepInfo("verify count and other details after reload ingestion page");
		int tilesCount = ingestionPage.getIngestionTilesCount().size();
		baseClass.stepInfo("Ingestion tiles count--"+tilesCount);
		int ingestionCount = Integer.parseInt(ingestionPage.getTotalIngestionCount().getText());
		baseClass.stepInfo("Number of ingestion present in project--"+tilesCount);
		if((tilesCount>0) && (ingestionCount>0)){
			baseClass.passedStep("Ingestion tiles and count updated successfully.");
		}
		else {
			baseClass.failedStep("Count not updated successfully");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/12/2022 TestCase Id:RPMXCON-54734
	 * Description :Verify that “EmailAuthor” Column header Filter with CJK characters is working 
	 * correctly on Doc Explorer list
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54734",enabled = true, groups = { "regression" })
	public void verifyEmailAuthorColumnHeader() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54734");
		baseClass.stepInfo("Verify that “EmailAuthor” Column header Filter is working correctly");
		//taken the cjk char present in dat file mentioned in testdata
		String[] cjkChar = {"?","ã","ą","æ","ç","ĉ","ć","č","ð","è","é","ú",
				"ŭ","ü","ý","ż","ž","€","£","¥"};
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, 
					Input.specialCjkDat);
			baseClass.stepInfo("Publish docs");
			ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("perform email author header filter");
		docExplorer.verifyEmailAuthorValuesInDocExp(cjkChar);
		baseClass.passedStep("Email author header filter is working correctly");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/12/2022 TestCase Id:RPMXCON-48075
	 * Description :To Verify SourceAttachDocIDs and AttachDocIDs  in Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48075",enabled = true, groups = { "regression" })
	public void verifyAttachDocIdsInSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48075");
		baseClass.stepInfo("To Verify SourceAttachDocIDs and AttachDocIDs in Search.");
		String[] field ={"AttachDocIDs"};
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting project which have the pre-requisite data
		baseClass.selectproject(Input.additionalDataProject);
		//getting the attachdocid to perform search
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion("Zip_DocView_20Family_20Threaded", "yes", "doclist");
		docList = new DocListPage(driver);
		baseClass.stepInfo("select columns");
		docList.SelectColumnDisplayByRemovingAddNewValues(field);
		driver.waitForPageToBeReady();
		String attachDocIds =docList.getDoclistMetaDataValue(count);	
		baseClass.stepInfo("navigate to project field and verify");
		ProjectFieldsPage projectFieldPage = new ProjectFieldsPage(driver);
		projectFieldPage.navigateToProjectFieldsPage();
		projectFieldPage.applyFilterByFilterName(field[0]);
		projectFieldPage.verifyExpectedFieldStatus(field[0],"false","true","active");
		baseClass.passedStep(field[0]+"is searchable and not tallyable");
		baseClass.stepInfo("perform metadata search and verify purehit");
		int purehit =sessionSearch.MetaDataSearchInBasicSearch(field[0],attachDocIds);
		baseClass.stepInfo("docs returned for the search--"+purehit);
		sessionSearch.verifySearchResultReturnsForConfiguredQuery(purehit);
		baseClass.passedStep(field[0]+"is searchable");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/12/2022 TestCase Id:RPMXCON-48077
	 * Description :To Verify FamilyRelationship in Tally and Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48077",enabled = true, groups = { "regression" })
	public void verifyFamilyRelationShipInSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48077");
		baseClass.stepInfo("To Verify FamilyRelationship in Tally and Search.");
		String metadata ="FamilyRelationship";
		String value ="Parent";
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting project which have the pre-requisite data
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		List<String> values = tally.verifyTallyChart();
		HashMap<String, Integer> map = tally.getDocsCountFortallyReport();
		int tallyResult = map.get(value);
		baseClass.stepInfo("Tally report status for parent value--"+tallyResult);
		baseClass.stepInfo("perform search and verify pure hit count");
		int searchResult = sessionSearch.MetaDataSearchInBasicSearch(metadata, value);
		baseClass.stepInfo("purehit for parent value--"+searchResult);
		baseClass.digitCompareEquals(tallyResult, searchResult, 
				"pure hit count matches with the tally result", 
				"search and tally result count not matched");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 12/12/2022 TestCase Id:RPMXCON-46890
	 * Description :To Verify In Ingestions Overlay for Audio Doc with Trimmed audio duration 
	 * fields should be successfull.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-46890",enabled = true, groups = { "regression" })
	public void verifyOverlayWithTrimmedAudioDuration() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-46890");
		baseClass.stepInfo("Verify Overlay for Audio Doc with Trimmed audio duration.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.H13696smallSetFolder);
		if (status == false) {
			ingestionPage.performSmallSetGdIngestion(Input.ingestionType);
			ingestionPage.publishAddonlyIngestion(Input.H13696smallSetFolder);
		}
		baseClass.stepInfo("perform overlay with trimmed audio duration field");
		ingestionPage.performSmallSetGdIngestion(Input.overlayOnly);
		ingestionPage.publishAudioOverlayIngestion(Input.H13696smallSetFolder);
		baseClass.passedStep("Overlay ingestion performed successfully");
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

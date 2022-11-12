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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression25 {

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
	 * @author Vijaya.Rani ModifyDate:03/11/2022 RPMXCON-53852
	 * @throws Exception
	 * @Description To verify, as an RM user login, When I will select all the documents in Doc List, I will be able to see this documents on Doc View page, from View Document action button.
	 */
	@Test(description = "RPMXCON-53852", enabled = true, groups = { "regression" })
	public void verifyAsRMUGotoDocViewFromDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53852");
		baseClass.stepInfo(
				"To verify, as an RM user login, When I will select all the documents in Doc List, I will be able to see this documents on Doc View page, from View Document action button.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();

		// select All Documents
		driver.waitForPageToBeReady();
		docList.selectAllDocs();
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will land in Doc view page with all the selected documents as a view in Doc view page Successfully");
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:03/11/2022 RPMXCON-54291
	 * @throws Exception
	 * @Description To verify that Audio file should play in Doc List irrespective of DocFileExtension.
	 */
	@Test(description = "RPMXCON-54291", enabled = true, groups = { "regression" })
	public void verifyAudioFilePlayInDocFileExtension() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54291");
		baseClass.stepInfo(
				"To verify that Audio file should play in Doc List irrespective of DocFileExtension.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
	
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocList();

		// previewDocument and play
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify preview Doc list of audio document");
		docList.DoclistPreviewAudio();
		baseClass.passedStep("Audio file is played in Preview window successfully.");
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T ModifyDate:4/11/2022 RPMXCON-54347
	 * @throws Exception
	 * @Description Verify from Doc View- mini doc list navigation and presentation
	 *              to DocList with selected documents, should complete in less than
	 *              8 seconds
	 */
	@Test(description = "RPMXCON-54347", enabled = true, groups = { "regression" })
	public void verifyingLoadingOfDocListPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54347");
		baseClass.stepInfo(
				"Verify from Doc View- mini doc list navigation and presentation to DocList with selected documents, should complete in less than 8 seconds");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage doc=new DocViewPage(driver);
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");

		// Searching Content document go to docview
		int Purehit=sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		long start = System.currentTimeMillis();
		
		baseClass.stepInfo("Navigating to doclist page");
		baseClass.waitForElement(doc.getViewDocAllList());
		doc.getViewDocAllList().waitAndClick(2);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getTableFooterDocListCount());
		String DocListCount = docList.getTableFooterDocListCount().getText();
		
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println(totalTime);

		String[] doccount = DocListCount.split(" ");
		String Document = doccount[5];
		baseClass.digitCompareEquals(Integer.valueOf(Document), Purehit, "Documents are loaded Successfully", "Documents are not loaded");
		
		baseClass.stepInfo("verifying the document loaded within 8 secs");
		if (totalTime < 8000) {
			baseClass.passedStep("Selected Documents are loaded in doclist page within 8 secs");
		}else {
			baseClass.failedStep("Selected Documents are not loaded in doclist page within 8 secs");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Krishna Date:NA ModifyDate:NA RPMXCON-54274
	 * @throws Exception
	 * @Description To verify that if any criteria is "Included" in the filters, the
	 *              results should include the docs that match/have the specified
	 *              criteria
	 */
	@Test(description = "RPMXCON-54274", enabled = true, groups = { "regression" })
	public void verifyIncludedFiltersResultDocsSpecifiedCriteria() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54274");
		baseClass.stepInfo(
				"To verify that if any criteria is \"Included\" in the filters, the results should include the docs that match/have the specified criteria");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();

		// verify applied include custodian filter is added.
		docList.applyCustodianNameFilter(Input.metaDataCN);
		driver.waitForPageToBeReady();
		docList.verifyAppliedIncludeCustodianNameFilterIsAdded(Input.metaDataCN);
	}

	
	/**
	 * @author sowndarya.velraj
	 * @Description :Creation and assigning documents in Folders for Categorization.[RPMXCON-54250]
	 */
	@Test(description = "RPMXCON-54250", enabled = true, groups = { "regression" })
	public void createandCategorizeFolders() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54250");
		baseClass.stepInfo("Creation and assigning documents in Folders for Categorization");
		
		DocListPage docList = new DocListPage(driver);
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
//		SavedSearch savedSearch=new SavedSearch(driver);
		Categorization categorize= new Categorization(driver);
		String folder= "Folder"+ Utility.dynamicNameAppender();
		String searchName="search"+ Utility.dynamicNameAppender();
	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tags.CreateFolder(folder, Input.securityGroup);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearch(searchName);
		sessionSearch.bulkFolderExisting(folder);
		

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Folder In Analyse Section
		categorize.fillingTrainingSetSection("Folder", folder, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("search", searchName, Input.mySavedSearch, true);

		// verify Run categorization
		categorize.runCategorization("YES");
		
		categorize.ViewInDocLIst();
		
		driver.waitForPageToBeReady();
		String docCount = docList.verifyingDocCount();
		
		softAssert.assertEquals(purehit,Integer.parseInt(docCount));
		softAssert.assertAll();
		baseClass.passedStep("Same number of documents moved in DocList Screen");
		
		}

	/**
	 * @author sowndarya.velraj
	 * @Description :Creation and assigning documents in Tags for Categorization.[RPMXCON-54249]
	 */
	@Test(description = "RPMXCON-54249", enabled = true, groups = { "regression" })
	public void createandCategorizeTags() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54249");
		baseClass.stepInfo("Creation and assigning documents in Tags for Categorization");
		
		DocListPage docList = new DocListPage(driver);
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		Categorization categorize= new Categorization(driver);
		String tag= "Tag"+ Utility.dynamicNameAppender();
		String searchName="search"+ Utility.dynamicNameAppender();
	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tags.CreateTag(tag, Input.securityGroup);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearch(searchName);
		sessionSearch.bulkTagExisting(tag);
		
		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Folder In Analyse Section
		categorize.fillingTrainingSetSection("Tag", tag, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("search", searchName, Input.mySavedSearch, true);

		// verify Run categorization
		categorize.runCategorization("YES");
		
		categorize.ViewInDocLIst();
		
		driver.waitForPageToBeReady();
		String docCount = docList.verifyingDocCount();
		
		softAssert.assertEquals(purehit,Integer.parseInt(docCount));
		softAssert.assertAll();
		baseClass.passedStep("Same number of documents moved in DocList Screen");
		
		}

	/**
	 * @author Vijaya.Rani ModifyDate:09/11/2022 RPMXCON-54365
	 * @throws Exception
	 * @Description Validate doc List limitation from Sub Tally report - View less than or equal to 500K documents.
	 */
	@Test(description = "RPMXCON-54365", enabled = true, groups = { "regression" })
	public void verifyDocListFromSubtallyInLessDocuments() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54365");
		baseClass.stepInfo(
				"Validate doc List limitation from Sub Tally report - View less than or equal to 500K documents");
		Utility utility = new Utility(driver);
		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		final String random1 = random;
		TagsAndFoldersPage tagAndFol = new TagsAndFoldersPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		baseClass.stepInfo("Create a new Folder contains word between");
		tagAndFol.CreateFolder(random1, Input.securityGroup);

		baseClass.stepInfo("Verify created folder is added to folder structure");
		driver.Navigate().refresh();
		tagAndFol.verifyFolderNameIsAddedToStructure(random1);

		baseClass.stepInfo("Select documets from doc explorer table and bulk folder selected documents");
		docexp.selectDocumentsAndBulkFolder(10, random1);

		SecurityGroupsPage securityGroup = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Navigate to security group");
		securityGroup.navigateToSecurityGropusPageURL();

		baseClass.stepInfo("Add Folder to security group");
		securityGroup.addFolderToSecurityGroup(Input.securityGroup, random1);

		TallyPage tally = new TallyPage(driver);

		baseClass.stepInfo("Navigate to tally page");
		tally.navigateTo_Tallypage();

		baseClass.stepInfo("Select project as souce for tally");
		tally.selectSourceByProject();

		baseClass.stepInfo("Select Tally by Meta Data Field");
		tally.selectTallyByMetaDataField(Input.metaDataName);

		baseClass.stepInfo("Select documents and click on action drop down");
		tally.tallyActions();

		baseClass.stepInfo("Selecting sub tally from drop down");
		tally.selectSubTallyFromActionDropDown();

		baseClass.stepInfo("Applying subtally from sub tally field");
		tally.applyingSubTallyFolderField(random1);

		baseClass.stepInfo("Verify documents count and go to doclistpage");
		tally.tallyToDocListPage();
		baseClass.passedStep("User is not be prompted with any message and navigate successfully to DocList screen with selected number of documents.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/11/2022 RPMXCON-54287
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that using the Download option, any of the file variant associated with this document including the MP3 file variant from preview document of doc list.
	 */
	@Test(description = "RPMXCON-54287", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentPerviewDocsAndDownload(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54287");
		baseClass.stepInfo(
				"Verify that using the Download option, any of the file variant associated with this document including the MP3 file variant from preview document of doc list.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.ViewInDocList();
		
		baseClass.waitForElement(docList.getDocListPerviewBtn());
		docList.getDocListPerviewBtn().waitAndClick(5);
		baseClass.waitForElement(docList.getAudioDownloadIcon());
		baseClass.waitTillElemetToBeClickable(docList.getAudioDownloadIcon());
		docList.getAudioDownloadIcon().Click();
		baseClass.waitForElement(docList.getAudioOptionToDownload());
		baseClass.waitTillElemetToBeClickable(docList.getAudioOptionToDownload());
		docList.getAudioOptionToDownload().Click();
		docList. getPreviewAudioDocCloseButton().waitAndClick(5);;
		baseClass.waitUntilFileDownload();
        String fileName = baseClass.GetFileName();
        System.out.println(fileName);
        if(fileName != null) {
        	baseClass.passedStep("Audio document is download successfully from preview document of doc list");
        }else {
        	baseClass.failedStep("No such Download file");
        }
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
	
	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	} 
	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
}

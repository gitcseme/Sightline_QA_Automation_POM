package testScriptsSmoke;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import pageFactory.CollectionPage;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.ReviewerResultReportPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;

public class smoke_Indium {
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
	TagsAndFoldersPage tagsAndFolderPage;
	AssignmentsPage agnmt;
	TagsAndFoldersPage page;
	KeywordPage kp;
	DataSets dataSets;

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
	String productionname;
	String exportname;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String foldername;
	String tagname;
	String assignmentNew = "Assignment06" + Utility.dynamicNameAppender();
	String assignmentComplete = "Assignment" + Utility.dynamicNameAppender();
	List<String> exp1 =Arrays.asList("2", "Gavin","Jaydeep","Owen","P Allen","Sai","Tyler","ViKas Mestry");
    List<String> exp2=Arrays.asList("Document", "Microsoft Word Document", "MS Excel Worksheet/Template (OLE)", "MS Outlook Message");
	List<String> exp3=Arrays.asList("","gouri.dhavalikar@symphonyteleca.com", "Jaydeep Gatlewar", "Jaydeep Gatlewar@symphonyteleca.com","Sai Theodare", "Sai.Theodare@symphonyteleca.com","ViKas Mestry","Vikas.Mestry@symphonyteleca.com","Vishal.Parikh@symphonyteleca.com");
	List<String> exp4=Arrays.asList("","gouri.dhavalikar@symphonyteleca.com","Jaydeep.Gatlewar@symphonyteleca.com","Sai.Theodare@symphonyteleca.com","Vikas.Mestry@symphonyteleca.com","Vishal.Parikh@symphonyteleca.com");
	String SearchName="Tally"+Utility.dynamicNameAppender();
	String  assgnName="Tally"+Utility.dynamicNameAppender();
	String  folderName="Tally"+Utility.dynamicNameAppender();
	String securityGrpName="Tally"+Utility.dynamicNameAppender(); 
	String projectName=Input.projectName;
	String[] sourceName_RMU = { assgnName, folderName,SearchName,"Default Security Group"};
	String[] sourceName_PA = {Input.projectName, folderName,SearchName,"Default Security Group"};
	String expectedCusName;
	String expectedEAName;
	String expectedDocFileType;
	String expectedEAAdress;
	String hitsCountPA;
	int hitsCount;
	SecurityGroupsPage securityPage;
	

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
	Input in = new Input(); 		in.loadEnvConfig();
	
		
		}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
	
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		dataSets=new DataSets(driver);
		
	}
	
	@Test
	public void releaseDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48683");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
    	SessionSearch search = new SessionSearch(driver);
    	search.basicContentSearch("*");
    	search.bulkRelease("Default Security Group");
    	System.out.println("Docs released to default security group!");
    	UtilityLog.info("Docs released to default security group!");
    	loginPage.logout();
    	}
	
	/**
	 * @author Krishna D date: NA Modified date: 25/08/2021 by: Krishna D Krishna
	 *         Test Case Id: 51758 Description : Verify persitent hit panel As RMU,
	 *         go to DocView from assignments with key word associated
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "smoke", "regression" }, priority = 0)

	public void verifyPersistantHitPanal() throws Exception {
   		baseClass.stepInfo("Test case Id: RPMXCON-51758");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt = new AssignmentsPage(driver);
		kp = new KeywordPage(driver);
		String[] keywords = {"test","hi","this","to","all"};
		
		  for(int i=0;i<keywords.length;i++) 
		  { System.out.println(keywords[i]);
		  try {
		  kp.AddKeyword("AutoKey"+i, keywords[i]); } catch(Exception e) {
		  System.out.println("keyword already exist"); } }
		 
		
		String assignmentkey="Keyword"+Utility.dynamicNameAppender();
		agnmt.createAssignmentwithkeywords(assignmentkey,Input.codingFormName,keywords);
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		  
		search.bulkAssign();
		agnmt.assignDocstoExisting(assignmentkey);
		agnmt.SelectAssignmentToViewinDocview(assignmentkey);
		
		DocViewPage docview = new DocViewPage(driver);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			docview.getDocView_info().Visible()  ;}}), Input.wait60);
    	for(int j=0;j<keywords.length;j++)
		{
    		docview.VerifyPersistentHit(keywords[j]);
		}
    	
		/*
		docViewRedact.manageBtn().Click();
		actions.moveToElement(docViewRedact.manageAssignments().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Successfully navigated to assignments page");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.AssignmentDocCountTgl().Visible()
						&& docViewRedact.AssignmentDocCountTgl().Enabled();
			}
		}), Input.wait30);
		docViewRedact.AssignmentDocCountTgl().waitAndClick(12);
		Thread.sleep(2000);
		actions.moveToElement(docViewRedact.testTable().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Successfully selected the test assignment");
//Selecting actions from assignments and clicking the persistent hit icon
		actions.moveToElement(docViewRedact.actionDropDownAssignments().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.actionDropDownAssignmentsViewInDocView().getWebElement());
		actions.click().build().perform();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.persistantHitBtn().Visible() && docViewRedact.persistantHitBtn().Enabled();
			}
		}), Input.wait30);
		docViewRedact.persistantHitBtn().waitAndClick(30);
		baseClass.waitForElement(docViewRedact.persistantHitToggle());
		
		if (docViewRedact.persistantHitToggle().Displayed() && docViewRedact.persistantHitToggle().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The persistent hit panel is visible");
		} else {
			assertTrue(false);
		}*/
		loginPage.logout();
	}

/**
	 * @author Krishna D D date: NA Modified date: NA Modified by: Krishna D Krishna
	 *         Test Case Id: RPMXCON-51863 Description : Verify text Remarks as RMU
	 *         through documents Get document list using search input and view in
	 *         DocView
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "smoke", "regression" }, priority = 1)

	public void verifyTextRemarks() throws Exception {
// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON 51863");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicMetaDataSearch("DocID", null, "ID00000101", null);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.remarksIcon().Visible() && docViewRedact.remarksIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.remarksIcon().waitAndClick(25);
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		 try {
			 docView.getDocView_Remark_DeleteIcon().waitAndClick(15);
			 baseClass.getPopupYesBtn().waitAndClick(5);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			System.out.println("Remark not present");
 		}		
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
				.moveByOffset(100, 20).release().build().perform();
		baseClass.stepInfo("text for remarks has been selected");
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		baseClass.waitTillElemetToBeClickable(docViewRedact.addRemarksTextArea());
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys("Remark by RMU");
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		baseClass.waitForElement(docViewRedact.deleteRemarksBtn());
		if (docViewRedact.deleteRemarksBtn().Displayed() && docViewRedact.deleteRemarksBtn().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The Remark has been saved by RMU");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}
	
	
	
	/**
	 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Test Case
	 *         Id: RPMXCON-52030 Description : login as RMU and impersonate as
	 *         reviewer use search term to get document and redact
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "smoke", "regression" }, priority = 2)
	public void verifyRedactionasReviewer() throws Exception {
		
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON 52030");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
// Impersonating as Reviewer and searching with text input		
		baseClass.impersonateRMUtoReviewer();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
// Redacting using rectangular redaction
		docViewRedact.redactRectangleUsingOffset(100, 100, 50, 20);
		baseClass.stepInfo("A rectangle redaction has been applied");
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.stepInfo("A rectangle redaction has been saved under Default Redaction Tag");
		if (docViewRedact.redactionIcon().getWebElement().isDisplayed()
				&& docViewRedact.redactionIcon().getWebElement().isEnabled()) {
			assertTrue(true);
			baseClass.passedStep("The Rectangular redaction has been applied and saved as Reviewer");
		} else {
			assertFalse(false);
		}
		loginPage.logout();
	}
	
	@Test(description = "RPMXCON-53892", enabled = true, groups = { "regression" })
	public void verifyAsReviewerPerformAllActinsFromDocLsitPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53892");
		baseClass.stepInfo(
				"To verify, As an Reviewer user login, I will be able to perform all actions from Doc List page, when I will go from Saved search to Doc List.");
		SoftAssert softAssert=new SoftAssert();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocViewPage docview=new DocViewPage(driver);
		String tagName = "tag" + UtilityLog.dynamicNameAppender();
		String folderName = "folder" + UtilityLog.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");
		sessionsearch.basicContentSearch(Input.searchStringStar);
		// Save searched content
		sessionsearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);

		// select multiple Documents and bulktag
		docList.documentSelection(3);
		sessionsearch.bulkTagExisting(tagName);
		baseClass.stepInfo("DocListpage Action Bulktag is created");
		// select multiple Documents and bulkfolder
		driver.waitForPageToBeReady();
		docList.documentSelection(3);
		docList.bulkFolderExisting(folderName);
		baseClass.stepInfo("DocListpage Action Bulkfolder is created");
		// filter action
		driver.waitForPageToBeReady();
		docList.applyCustodianNameFilter("P Allen");
		baseClass.stepInfo("DocListpage Action filters is performed");
		// select multiple Documents and bulkfolder
		driver.waitForPageToBeReady();
		docList.documentSelection(5);
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will be able to perform all Doc List actions Successfully");
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_47968
	 * @Description:To verify In Production user can select the only DAT and TIFF component and Production should generated successfully
	 */                      
	//@Test(groups = { "smoke", "regression" }, priority = 3)
	public void generateProductionWithDATAndTIFF() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47968 Production- Sprint 04");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNameTechnical = Input.tagNameTechnical;
		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		//search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);
		//create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		baseClass.passedStep("To verify In Production user can select the only DAT and TIFF component and Production should generated successfully");	
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname,"Default Security Group");
		loginPage.logout();
	}
	
	/**
	* @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	* No:RPMXCON-48317
	* @Description:To verify the Production for Audio files which include redaction
	* from beginning, middle and end of the audio file
	*
	*/
	@Test(enabled = true, groups = { "smoke", "regression" }, priority = 4)
	public void verifyAudioFilesWithRedactedDocuments() throws InterruptedException {
 	baseClass.stepInfo("RPMXCON_48317 Production- Sprint 06");
    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

	String foldername1 = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();



	// Pre-requisites
	// create folder and tag
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername1,Input.securityGroup);



	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
	sessionSearch.bulkFolderExisting(foldername1);



	sessionSearch.ViewInDocView();



	docView = new DocViewPage(driver);
	docView.addAudioRedaction();



	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	String productionname1 = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname1);
	page.fillingDATSection();
	page.advancedProductionComponentsMP3WithBurnReductionTag();
	page.navigateToNextSection();
	page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername1, productionname1,
	beginningBates);
	baseClass.passedStep("verified Audio files by redacting them in beginning,middle and end");


/*
	// To delete tag and folder
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername,Input.securityGroup); */
	loginPage.logout();



	}
	
	/**
	 * Author : Baskar date: NA Modified date: 13/01/2022 Modified by: Baskar
	 * Description:Verify user can select and apply code same as this for the audio
	 * files
	 */

	@Test(enabled = true, groups = { "smoke", "regression" }, priority = 5)
	public void validationCodeSameAsAudioDocs() throws InterruptedException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51077");
		baseClass.stepInfo("Verify user can select and apply code same as this for the audio files");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// CodingStamp popup verify
		docViewPage.validationAudioDocsCheckMark(comment);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:13/01/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify that document navigation should work from audio doc
	 *              view
	 */

	@Test(enabled = true, groups = { "smoke", "regression" }, priority = 6)
	public void validateLastNavigationOption() throws Exception {
		
		loginPage = new LoginPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51482");
		baseClass.stepInfo("Verify that document navigation should work from audio doc view");

		// Login As
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view mini list");

		// validation navigation option
		docViewPage.verifyThatIsLastDoc();

		// logout
		loginPage.logout();

	}
	
	/**
	 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 *         Test Case Id: RPMXCON-50779 Description : Verify that user can
	 *         navigate through documents Get document list using search input and
	 *         view in DocView Navigate to first and last document
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "smoke", "regression" }, priority = 7)

	public void verifyDocNavigationRMU() throws Exception {
// Selecting Document from Session search
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON 50779");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.forwardNextDocBtn().Visible() && docViewRedact.forwardNextDocBtn().Enabled();
			}
		}), Input.wait30);
		docViewRedact.forwardNextDocBtn().waitAndClick(20);
		baseClass.stepInfo("Moved to next document using naviagtion option");
		docViewRedact.forwardNextDocBtn().waitAndClick(2);
		actions.moveToElement(docViewRedact.forwardToLastDoc().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Moved to last document in the list using naviagtion option");
		actions.moveToElement(docViewRedact.backwardPriviousDocBtn().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Moved to previous document using naviagtion option");
		actions.moveToElement(docViewRedact.backwardToFirstDoc().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Moved to first document in the list document using naviagtion option");
		if (docViewRedact.backwardToFirstDoc().getWebElement().isDisplayed()
				&& docViewRedact.backwardToFirstDoc().getWebElement().isEnabled()) {
			assertTrue(true);
		} else {
			assertFalse(false);
		}
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date: 24/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify check mark icon should be displayed when document is
	 *              completed after selecting 'Code same as' action' from mini doc
	 *              list
	 */
	@Test(enabled = true, groups = { "smoke", "regression" }, priority = 8)
	public void checkMarkIconShouldDispalyed() throws InterruptedException {
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48714- DocView/MiniDocList Sprint 02");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString2);

		sessionsearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assignmentNew, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewerManager();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// Impersonate to Reviewer
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("User '" + Input.rmu1userName + "' impersonate as Reviewer");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentNew);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// verifying check mark icon
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.verifyingCheckMarkIconInMiniDocList();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
		loginPage.logout();

	}
	
	/**
	 * Author : Baskar date: NA Modified date: NA Modified by: Baskar
	 * Description:Coding Form Child window: Verify that user can save the coding
	 * stamp after coding form selection for a document in context of security group
	 * RPMXCON-52043 Sprint 02
	 */
	@Test(enabled = true, groups = { "smoke", "regression" }, priority = 9)
	public void codingFormChildWindowStampColourSelection() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52043");
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");
		DocViewPage docViewPage = new DocViewPage(driver);
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.audioSearch(Input.audioSearchString1, Input.language);
		
		//docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
	//	sessionsearch.advancedNewContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionsearch.ViewInDocView();

		// CodingForm ChildWindow Stamp selection
	
		docViewPage.stampSelectionCodingForm(Input.stampColour);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  52426 - To verify when assigns user to different project with different role of existing project.
	 * Description : To verify when assigns user to different project with different role of existing project.
	 */
	@Test(alwaysRun = true,groups = { "smoke", "regression" },priority = 10)
	public void createUserWithDifferentProjectAndRole() throws Exception {		
		baseClass=new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Review Manager";
		String role2 = "Reviewer";
		String project1 = Input.projectName;
		String project2 = Input.ICEProjectName;
		String emailId = Input.randomText + Utility.dynamicNameAppender()+"@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52426");
		utility = new Utility(driver);
		baseClass.stepInfo("#### To verify when assigns user to different project with different role of existing project ####");
		userManage = new UserManagement(driver);
		System.out.println(firstName+"....."+lastName);
		
		baseClass.stepInfo("Create new user");
		userManage.createNewUser(firstName, lastName, role, emailId, " ",project1 );
		
		
		baseClass.stepInfo("Create new user with same username with different project and different role as previously created");
		userManage.createNewUser(firstName, lastName, role2, emailId, " ", project2);
		
		baseClass.passedStep("Created new user with same username with different project and different role as previously created successfully..");
	
		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
	}
	

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  55728 - Verify when the project is created, it should send to the background with the appropriate message to the user.
	 * Description : Verify when the project is created, it should send to the background with the appropriate message to the user.
	 */
	//commented because it is creating project in production with existing client 
	//@Test(alwaysRun = true,groups = { "smoke", "regression" },priority = 11)
	public void createUserWithDifferentProjectAndRole2() throws Exception {		
		baseClass=new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		String projectnamenondomain = Input.randomText + Utility.dynamicNameAppender();
		String hcode =Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-55728");
		utility = new Utility(driver);
		baseClass.stepInfo("#### Verify when the project is created, it should send to the background with the appropriate message to the user. ####");
		
		ProjectPage project = new ProjectPage(driver);
		
		baseClass.stepInfo("Navigate to production page");
		project.navigateToProductionPage();
		
		baseClass.stepInfo("Add Non Domain Project");
		project.AddNonDomainProject(projectnamenondomain, hcode);
	}
	
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	//@Test(dataProvider = "Users_PARMU",groups = { "smoke", "regression" }, priority = 12)
	public void verifyTallyDropDown(String username, String password, String role) throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-56196");
		baseClass.stepInfo("To Verify \"Tally By\" Drop Down And \"Apply\" button on Tally Page");
		String[] metadata = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as " + role);
		SoftAssert softAssertion = new SoftAssert();
		TallyPage tp = new TallyPage(driver);
		for(int k=0;k<4;k++) {
			tp.navigateTo_Tallypage();
			if(role=="PA") {
			tp.selectSources_PA(k,sourceName_PA[k]);}
			if(role=="RMU") {
				tp.selectSources_RMU(k,sourceName_RMU[k]);}
		for (int i = 0; i < metadata.length; i++) {
			tp.selectTallyByMetaDataField(metadata[i]);
			tp.validateMetaDataFieldName(metadata[i]);
			 tp.verifyTallyChart();
			softAssertion.assertAll();
		}
		baseClass.passedStep("Sucessfully verified tally by drop down and apply button if source is-- "+sourceName_PA[k] );
		}
		}
	
	
	/**
	 * Author : Baskar date: NA Modified date:21/01/2021 Modified by: Baskar
	 * Description :Verify that System Admin can create user with Domain Admin role
	 */
	@Test(alwaysRun = true,groups = { "smoke", "regression" },priority = 13)
	public void createNewUserForDomain() throws Exception {		
		baseClass=new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Domain Administrator";
		String emailId = Input.randomText + Utility.dynamicNameAppender()+"@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52773");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify that System Admin can create user with Domain Admin role");
		userManage = new UserManagement(driver);
		
		baseClass.stepInfo("Create new user for domain administration");
		userManage.createNewUser(firstName, lastName, role, emailId, " ",Input.projectName );
		
		baseClass.stepInfo("Domain admin role displayed in dropdown field");
		baseClass.passedStep("Created new user with Domain admin rule");
	
		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
	}
	
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 * @throws ParseException 
	 */
	@Test(groups = { "smoke", "regression" }, priority = 14)
	public void verifyReviewerResultReportShedule()
			throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-56455");
		baseClass.stepInfo("To verify that users can schedule the Review Result Report.");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine( Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Logged in as -PA");
		ReviewerResultReportPage revResultpage = new ReviewerResultReportPage(driver);
		revResultpage.navigateToReviewerResultReportPage();
		driver.waitForPageToBeReady();
		revResultpage.generateReport();
		revResultpage.getScheduleBtn().Click();
		revResultpage.scheduleReport();
		SchedulesPage sp =new SchedulesPage(driver);
		//sp.verifyScheduledTime_Reports(1);
		sp.checkStatusComplete_reports();
		loginPage.logout();
	}
	
	@Test(description="RPMXCON-47478",enabled = true,groups = { "smoke","regression" })
	public void verifyingGenerationOfExport() throws Exception {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass = new BaseClass(driver);
	UtilityLog.info(Input.prodPath);
	baseClass.stepInfo("RPMXCON-47478 -Export component");
	baseClass.stepInfo("To Verify Export in production, basic Export without Production.");
	
	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();
	String newExport = "Ex" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);
	
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);

	
	ProductionPage page = new ProductionPage(driver);
	String productionname = "p" + Utility.dynamicNameAppender();
	String subBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	String text = page.getProdExport_ProductionSets().getText();
	if (text.contains("Export Set")) {
		page.selectExportSetFromDropDown();
	} else {
		page.createNewExport(newExport);
	}
	page.addANewExport(productionname);
	page.fillingDATSection();
	page.fillingTIFFSection(tagname);
	page.navigateToNextSection();
	page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingExportLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
	loginPage.logout();
	}
	
	@Test(description ="RPMXCON-56969",dataProvider = "Users_PARMU", groups = { "smoke","regression" }, enabled = true)
	public void verifyReportGeneration_searches(String username, String password, String role)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56969");
		baseClass.stepInfo("Verify and generate Communications Explorer with source as Search");
		loginPage.loginToSightLine(username, password);
		SoftAssert softAssertion = new SoftAssert();
		String saveSearchName = "ST" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Logged in as -" + role);
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		String hitsCount = search.verifyPureHitsCount();
		search.saveSearch(saveSearchName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		CommunicationExplorerPage comExpPage = new CommunicationExplorerPage(driver);
		comExpPage.generateReportusingSearch();
		baseClass.waitForElement(comExpPage.getCommunicationExplorer_ApplyResult());
		if (comExpPage.getCommunicationExplorer_ApplyResult().Displayed()) {
			softAssertion.assertTrue(true);
			baseClass.passedStep("After Selecting the My searches in select sources tab Report is generated.");

		} else {
			softAssertion.assertTrue(false);
			baseClass.failedStep("After Selecting the My searches in select sources tab Report is not generated.");

		}
		softAssertion.assertAll();
		 SavedSearch savedSearch = new SavedSearch(driver);
		 savedSearch.SaveSearchDelete(saveSearchName);
		 loginPage.logout();

	}
	@Test(description = "RPMXCON-59791", enabled = true,dataProvider="userDetails", groups = { "smoke","regression" })
	public void validatePlayFunctionInVideoDocs(String fullName,String userName,String password) throws InterruptedException {
		SoftAssert softAssertion=new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-59791");
		baseClass.stepInfo("Verify that Video files Play functionality is working "
				+ "properly inside Doc view screen");
		// Login as 
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as '" + userName + "'");
		docView = new DocViewPage(driver);
		SessionSearch search=new SessionSearch(driver);
		// search to docview
		search.basicMetaDataSearch("VideoPlayerReady", null, "1", "");
		search.ViewInDocView();
		
		// validating video player docs
		baseClass.waitTime(10);
		Long beforeTime=(long) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.querySelector('#docVideo').currentTime;");
		System.out.println("beforeTime :- "+beforeTime);
		((JavascriptExecutor) driver.getWebDriver()).executeScript("document.querySelector('#docVideo').play()");
		baseClass.waitTime(10);
		Double afterTime=(Double) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.querySelector('#docVideo').currentTime;");
		System.out.println("afterTime :- "+afterTime);
        softAssertion.assertNotEquals(Long.toString(beforeTime), Double.toString(afterTime));
        baseClass.passedStep("Video play functionality working properly inside docview screen");
		
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}
	/**
	 * @Author Jeevitha
	 * @Description : Verify that 'View Datasets' of a collection functionality is
	 *              working proper on Collection’s home page.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61295", dataProvider = "PaAndRmuUser", enabled = true, groups = { "smoke","regression" })
	public void verifyViewDatasetsIsAsExpected(String usernameDetail, String password, String fullname)
			throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		List<String> custodianDetails = new ArrayList();

		String collectionEmailId = Input.collectionDataEmailId;
		String custodianListval=Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Inbox";
		String headerListDS[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.collectionDataHeader4, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		UserManagement userManagement=new UserManagement(driver);
		String[][] userRolesData = { { usernameDetail, fullname, fullname } };
		String collectionNewName = "CollectionNew" + Utility.dynamicNameAppender();
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String actualCollectionName;
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		CollectionPage collection=new CollectionPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-61295 - O365");
		baseClass.stepInfo(
				"Verify that 'View Datasets' of a collection functionality is working proper on Collection’s home page.");

		// Login as User
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		
		userManage = new UserManagement(driver);
		userManage.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		loginPage.logout();

		// Login as User
		loginPage.loginToSightLine(usernameDetail, password);
		baseClass.selectprojectICE();

		userManage.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// get username
		String username = loginPage.getCurrentUserName();

//		// get other dataset tile view
		dataSets.navigateToDataSetsPage();
		String otherTileView = dataSets.getTileViewType();

		// Add New Collection Or get Already Present completed Collection Details
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
//		collectionData = collection.createNewCollection(collectionData, collectionName, true, "Completed", true);

		// navigate to Collection page and get the data
		collectionName = baseClass.returnKey(collectionData, "", false);

		// Click View Dataset or Create collection and click View Dataset
		if (collection.getNameBasedOnCollectionName(collectionName, username).isElementAvailable(3)) {
			baseClass.stepInfo(collectionName + " : is Completed and Displayed in Collections Page");
			driver.waitForPageToBeReady();
			collection.clickViewDataset(collectionName);
			driver.waitForPageToBeReady();
			actualCollectionName = collectionName;
		} else {
			
			dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
			// Click create New Collection
			collection.performCreateNewCollection();

			// create Jeb Bush source as per Test data
			collection.performAddNewSource(null, dataSourceName, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

			// click created source location and verify navigated page
			HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(dataSourceName, collectionNewName,
					false);
			System.out.println("collectionInfoPage:-"+collectionInfoPage);

			
			// Initiate collection process
			collection.selectInitiateCollectionOrClickNext(true, true, true);
			
			collection.fillinDS(collectionNewName,custodianListval, firstName, lastName, collectionEmailId, selectedApp, collectionInfoPage,
					selectedFolder1, headerListDS, "Button", 3, false, "Save", true, false,false,"",false,"", null);

			
			
		
			collection.clickOnNextAndStartAnCollection();
			dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
			driver.waitForPageToBeReady();

			// Verify Collection presence with expected Status
			collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionNewName, statusListToVerify, 40,
					true, false, "", "");

			// Completed status check
			collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionNewName, statusList, 20);
			collection.clickViewDataset(collectionNewName);
			driver.waitForPageToBeReady();
			actualCollectionName = collectionNewName;

		}

		// verify is it navigating to datasets page
		driver.waitForPageToBeReady();
		baseClass.verifyUrlLanding(Input.url + "en-us/ICE/Datasets", "Navigated To Dataset Page",
				"Navigation is not as expected");

		// verify completed collection is displayed in datasets page
		dataSets.verifysearchBoxValue(actualCollectionName, otherTileView);

		// Logout
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}
	
	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] User = { 
				{ Input.rmu1userName, Input.rmu1password, "Review Manager" },{ Input.pa1userName, Input.pa1password, "Project Administrator" }
		};
		return User;
	}
	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW/REDACTIONS EXECUTED******");

	}

	
	

}

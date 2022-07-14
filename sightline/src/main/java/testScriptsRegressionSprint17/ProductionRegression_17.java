
package testScriptsRegressionSprint17;

import java.io.File;
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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProductionRegression_17 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		base = new BaseClass(driver);
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}
	
	/**
	 * @author Brundha RPMXCON-48178
	 * @Description To Verify For Multimedia file group native Generation without
	 *              any Priv Tag Classification.
	 */
	@Test(description = "RPMXCON-48178", enabled = true, groups = { "regression" })

	public void verifyingGenerationOfMp3File() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48178 -Production component");
		base.stepInfo("To Verify For Multimedia file group native Generation without any Priv Tag Classification.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID",Input.fileGroup);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname,Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.extractFile();
		driver.waitForPageToBeReady();
		File TiffFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_TIFF.OPT");
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_DAT.dat");
		File NativeFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_Native.lst");

		if (TiffFile.exists()) {
			base.passedStep("Tiff file is generated as expected");
		} else {base.failedStep("Tiff file is not generated as expected");}

		if (DatFile.exists()) {
			base.passedStep("Dat file is generated as expected");
		} else {base.failedStep("Dat file is not generated as expected");}
		base.waitTime(2);
		if (NativeFile.exists()) {
			base.passedStep("Native file is generated as expected");
		} else {base.failedStep("Native file is not generated as expected");}

		loginPage.logout();

	}
	
	
/**
 * @author Brundha RPMXCON-49336
 * @Description To verify that 'Tech Issue placeholdering' is available on Tiff/PDF on Production-Component section
 */
@Test(description = "RPMXCON-49336", enabled = true, groups = { "regression" })

public void verifyTechIssuePlaceholderInComponentTab() throws Exception {

	base = new BaseClass(driver);
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-49336 -Production component");
	base.stepInfo("To verify that 'Tech Issue placeholdering' is available on Tiff/PDF on Production-Component section");
	String productionname = "P" + Utility.dynamicNameAppender();

	ProductionPage page = new ProductionPage(driver);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.getTIFFTab().waitAndClick(5);
	driver.scrollingToElementofAPage(page.getTechissue_toggle());
	base.ValidateElement_Presence(page.getTechissue_toggle(),"TechIssue Toggle");
	page.getTechissue_toggle().waitAndClick(10);
	base.ValidateElement_Presence(page.getTechissue_SelectTagButton(),"TechIssue Select Tags");
	base.ValidateElement_Presence(page.getTechIssuePlaceHolder(),"TechIssue Paceholder");
	base.ValidateElement_Presence(page.getTechInsertMetadataField(),"TechIssue Insert Metadata Field");
	loginPage.logout();
	
	
	
	
}
/**
 * @author Brundha Test case id-RPMXCON-48162
 * @Description To Verify Availability of MP3 Files Option In Production Component Section under Advance Production Component.
 */
@Test(description="RPMXCON-48162",enabled = true, groups = { "regression" })
public void asVerifyProductionGeneratedwithNonRedactedArea() throws Exception {

	UtilityLog.info(Input.prodPath);

	base.stepInfo("RPMXCON-48162 -Production component");
	loginPage.logout();
	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	base.stepInfo(
			"To Verify Availability of MP3 Files Option In Production Component Section under Advance Production Component.");

	String redactiontag = "Redaction" + Utility.dynamicNameAppender();
	String foldername = "Folder" + Utility.dynamicNameAppender();
	String productionname = "p" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

	RedactionPage redactionpage = new RedactionPage(driver);
	driver.waitForPageToBeReady();

	redactionpage.manageRedactionTagsPage(redactiontag);
	System.out.println("First Redaction Tag is created" + redactiontag);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.audioSearch("morning", "North American English");
	sessionSearch.bulkFolderExisting(foldername);
	driver.waitForPageToBeReady();
	sessionSearch.ViewInDocViewWithoutPureHit();

	DocViewPage docViewPage = new DocViewPage(driver);
	docViewPage.navigateToDocViewPageURL();
	docViewPage.documentSelection(3); 

	DocViewRedactions redact = new DocViewRedactions(driver);
	
	redact.deleteAllAppliedRedactions();
	redact.clickOnAddRedactionForAudioDocument();
	redact.addAudioRedaction(Input.startTime, Input.endTime, redactiontag);

	loginPage.logout();
	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.burnRedactionWithRedactionTagInTiffSection(redactiontag);
	page.navigateToNextSection();
	page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	
	driver.waitForPageToBeReady();
	String home = System.getProperty("user.home");
	page.extractFile();

	driver.waitForPageToBeReady();
	File file = new File(home + "/Downloads/VOL0001/Natives/0001");
	File imageFile = new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
	page.isfileisExists(imageFile);
	if (file.exists()) {
		base.passedStep("Mp3 document generatd successfully");
	} else {
		base.failedStep("Document not generated");
	}
	
	loginPage.logout();
}

/**
 * @author Brundha Test case id-RPMXCON-49238
 * @Description To verify that on clicking on link, it displays next available bates number in all patterns
 * 
 */
@Test(description="RPMXCON-49238",enabled = true, groups = { "regression" })

public void verifyClickHereLinkInSortingPage() throws Exception {

	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-49238 -Production Component");
	base.stepInfo(
			"To verify that on clicking on link, it displays next available bates number in all patterns");

	String foldername = "Folder" + Utility.dynamicNameAppender();
	String productionname = "p" + Utility.dynamicNameAppender();
	String productionname1 = "p" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	page = new ProductionPage(driver);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	driver.waitForPageToBeReady();
	page.fillingGeneratePageWithContinueGenerationPopup();
	
	page = new ProductionPage(driver);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname1);
	page.fillingDATSection();
	page.navigateToNextSection();
	driver.waitForPageToBeReady();
	page.verifyAvailbleLinkAtNumberingAndSorting();
	page.getClickHereLink().waitAndClick(5);
	driver.waitForPageToBeReady();
	base.ValidateElement_Presence(page.NextBatesNumberPopup(),"Next Bates Number Popup");
	base.ValidateElement_Presence(page.getNextBatesNumberInSortingPage(),"Next Bates Number");
	base.ValidateElement_Presence(page.getNextBatesNumber(),"Select Button");
	
	loginPage.logout();
}



/**
 * @author Brundha Test case id-RPMXCON-47941
 * @Description To Verify Place holder should get generated for selected File Types.
 * 
 */
@Test(description="RPMXCON-47941",enabled = true, groups = { "regression" })

public void verifyFileTypeInGeneratedProduction() throws Exception {

	UtilityLog.info(Input.prodPath);
	

	base.stepInfo("RPMXCON-47941 -Production Component");
	base.stepInfo(
			"To Verify Place holder should get generated for selected File Types.");

	String foldername = "Folder" + Utility.dynamicNameAppender();
	String productionname = "p" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);

	SessionSearch sessionSearch = new SessionSearch(driver);
	int pureHit = sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);

	ProductionPage page = new ProductionPage(driver);
	page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	int firstFile=Integer.valueOf(beginningBates);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.fillingNativePlaceHolderFileTypeInTiffSection(Input.searchString1);
	page.navigateToNextSection();
	page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	driver.waitForPageToBeReady();
	String home = System.getProperty("user.home");
	base.waitTime(2);
	page.deleteFiles();
	page.extractFile();
	driver.waitForPageToBeReady();
	File dir = new File(home + "/Downloads/VOL0001/Natives/0001/");
	File[] dir_contents = dir.listFiles();
	System.out.println(dir_contents.length);
	int nativefile = dir_contents.length;

	
	if (pureHit != nativefile) {
		base.passedStep("Placeholder is generated for selected File Types.");
	} else {
		base.failedStep("Placeholder is not generated for selected filetype");
	}
	page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, nativefile, prefixID, suffixID,Input.searchString1);
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	loginPage.logout();
}
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}


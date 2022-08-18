
package sightline.productions;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.util.LoadLibs;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import net.sourceforge.tess4j.Tesseract1;
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
	String tagname = "Folder" + Utility.dynamicNameAppender();
	String productionname = "p" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.createNewTagwithClassification(tagname,"Select Tag Classification");

	RedactionPage redactionpage = new RedactionPage(driver);
	driver.waitForPageToBeReady();

	redactionpage.manageRedactionTagsPage(redactiontag);
	System.out.println("First Redaction Tag is created" + redactiontag);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);
	driver.waitForPageToBeReady();
	sessionSearch.ViewInDocView();

	DocViewRedactions redact = new DocViewRedactions(driver);
	DocViewPage docViewPage = new DocViewPage(driver);
	docViewPage.navigateToDocViewPageURL();
	base.waitTime(2);
	docViewPage.documentSelection(3); 
	
	
	redact.deleteAllAppliedRedactions();
	driver.waitForPageToBeReady();
	redact.clickOnAddRedactionForAudioDocument();
	redact.addAudioRedaction(Input.startTime, Input.endTime, redactiontag);

	driver.waitForPageToBeReady();
	docViewPage.getViewDocAllList().waitAndClick(10);
	driver.waitForPageToBeReady();
	DocListPage doclist = new DocListPage(driver);
	doclist.documentSelection(1);
	doclist.bulkTagExisting(tagname);
	
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
	page.fillingDocumentSelectionWithTag(tagname);
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





/**
 * @author Brundha Date:7/15/2022 TestCase Id:RPMXCON-49246
 * @Description:Verify that when production components step is completed then
 *                     DAT component should retain the 'TIFFPageCount'
 */
@Test(description = "RPMXCON-49246", enabled = true, groups = { "regression" })
public void verifyDatFiledWithTiffPageCount() throws Exception {
	UtilityLog.info(Input.prodPath);

	base.stepInfo("Test case id : RPMXCON-49246 from production component ");
	base.stepInfo(
			"Verify that when production components step is completed then DAT component should retain the 'TIFFPageCount'");

	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);
	sessionSearch.bulkTagExisting(tagname);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
	page.fillingTIFFSectionPrivDocs(tagname,Input.searchString1);
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
	page.extractFile();

	driver.waitForPageToBeReady();
	String home = System.getProperty("user.home");
	String name = page.getProduction().getText().trim();
	driver.waitForPageToBeReady();
	File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
	if (DatFile.exists()) {
		base.passedStep("Dat file is exists in generated production");
	} else {
		base.failedStep("Dat file is not displayed as expected");
	}

	int TiffPageCount = 1;
	String line;
	List<String> lines = new ArrayList<>();
	BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
	while ((line = brReader.readLine()) != null) {
		lines.add(line);
	}
	System.out.println(lines.get(1));
	String[] arrOfStr = lines.get(1).split("þ");
	String output = arrOfStr[3];
	if (TiffPageCount == Integer.parseInt(output)) {
		base.passedStep("Tiff page count is displayed as expected");
	} else {
		base.failedStep("verification failed");
	}

	brReader.close();

	loginPage.logout();
}

/**
 * @author Brundha.T Date:7/15/2022 TestCase Id :RPMXCON-48029 
 * Description :To Verify by default "No Rotation" is selected for the pages in TIFFing.
 * 
 */
@Test(description = "RPMXCON-48029", enabled = true, groups = { "regression" })
public void verifyingDefaultNoRotationOptionInTiffSection() throws Exception {

	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-48029 -Production Component");
	base.stepInfo("To Verify by default 'No Rotation' is selected for the pages in TIFFing.");
			
	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();
	BaseClass base = new BaseClass(driver);

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname,"Select Tag Classification");
	
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.SearchMetaData(Input.sourceDocIdSearch,Input.sourceDocument);
	sessionSearch.bulkFolderExisting(foldername);
	sessionSearch.bulkTagExisting(tagname);
	
	ProductionPage page = new ProductionPage(driver);
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.selectGenerateOption(false);
	page.fillingNativeDocsPlaceholder(tagname);
	driver.scrollPageToTop();
	page.getRotationDropDown();
	Select select = new Select(page.getRotationDropDown().getWebElement());
	String option = select.getFirstSelectedOption().getText();
	System.out.println("the value is " + option);
	base.textCompareEquals(option,"No Rotation","No Rotation option is selected by default in Tiff Section","No Rotation option is not selecetd by default");
	
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	loginPage.logout();
	
}


/**
 * @author Brundha.T Date:7/15/2022 TestCase Id :RPMXCON-48024 
 * Description :To Verify Generated TIFF and PDF (Page Content) should be correct.
 * 
 */
@Test(description = "RPMXCON-48024", enabled = true, groups = { "regression" })
public void verifyingTheTiffPageContentInGeneratedProduction() throws Exception {

	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-48024 -Production Component");
	base.stepInfo(
			"To Verify Generated TIFF and PDF (Page Content) should be correct.");
	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	BaseClass base = new BaseClass(driver);

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname,"Select Tag Classification");
	
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	int PureHit=sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);
	sessionSearch.bulkTagExisting(tagname);
	
	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.selectGenerateOption(false);
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
	File dir = new File(home + "/Downloads/VOL0001/Images/0001/");
	File[] dir_contents = dir.listFiles();
	System.out.println(dir_contents.length);
	int TiffFile = dir_contents.length;
	
	if (PureHit <TiffFile) {
		base.passedStep("Tiff image is generated for all pages");
	} else {
		base.failedStep("Tiff image is not generated for all pages");
	}
	String firstFile = prefixID + beginningBates + suffixID;
	File file = new File(home + "/Downloads/VOL0001/Images/0001/" + firstFile + ".tiff");
	page.isfileisExists(file);
	page.OCR_Verification_In_Generated_Tiff_tess4j(file,"Crammer");
	
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

	loginPage.logout();

}

/**
 * @author Brundha.T TestCase Id:RPMXCON-63189 Date:7/19/2022
 * @Description:Verify that template should display with default native
 *                     placeholder by default under TIFF/PDF section and
 *                     production should be generated successfully using same
 *                     template
 **/

@Test(description = "RPMXCON-63189", enabled = true ,groups = { "regression" })
public void verifyTheProductionGenerationUsingTemplate() throws Exception {

	base = new BaseClass(driver);
	base.stepInfo("Test case Id:RPMXCON-63189- Production component");
	base.stepInfo(
			"Verify that template should display with default native placeholder by default under TIFF/PDF section and production should be generated successfully using same template");
	UtilityLog.info(Input.prodPath);

	String tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = "P" + Utility.dynamicNameAppender();
	String prefixID1 = "P" + Utility.dynamicNameAppender();
	String suffixID = "S" + Utility.dynamicNameAppender();
	String suffixID2 = "S" + Utility.dynamicNameAppender();
	String Templatename = "Temp" + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

	SessionSearch sessionSearch = new SessionSearch(driver);
	this.driver.getWebDriver().get(Input.url + "Search/Searches");
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.addNewSearch();
	sessionSearch.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
	sessionSearch.addPureHit();

	sessionSearch.ViewInDocList();
	DocListPage doclist = new DocListPage(driver);
	doclist.documentSelection(6);
	doclist.bulkTagExisting(tagname);

	ProductionPage page = new ProductionPage(driver);
	page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
    driver.waitForPageToBeReady();
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProductionAndSave(productionname);
	page.fillingDATSection();
	page.verifyingTheDefaultSelectedOptionInNative();
	page.verifyingNativeSectionFileType(Input.MetaDataFileType);
	page.verifyingNativeSectionFileType(Input.NativeFileType);
	page.navigateToNextSection();
	page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionWithTag(tagname);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();

	driver.waitForPageToBeReady();
	page = new ProductionPage(driver);
	String productionname1 = "p" + Utility.dynamicNameAppender();
	page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
	page.verifyingComponentTabSection();
	driver.waitForPageToBeReady();
	page = new ProductionPage(driver);
	page.baseInfoLoadTemplate(productionname1, Templatename);
	page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
	page.getCheckBoxCheckedVerification(page.chkIsTIFFSelected());
	page.verifyingTheDefaultSelectedOptionInNative();
	page.navigateToNextSection();
	page.fillingNumberingAndSorting(prefixID1, suffixID2, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionWithTag(tagname);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname1);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();

	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	loginPage.logout();
}

/**
 * @author Brundha Test case id-RPMXCON-47967  Date:7/19/2022
 * @Description To Verify Native Path are populated in an Export/Production
 * 
 */
@Test(description = "RPMXCON-47967", enabled = true, groups = { "regression" })

public void verifyNativePathInGenerationOfDATFile() throws Exception {

	UtilityLog.info(Input.prodPath);

	base.stepInfo("RPMXCON-47967 -Production Component");
	base.stepInfo("To Verify Native Path are populated in an Export/Production");

	String foldername = "Folder" + Utility.dynamicNameAppender();
	String productionname = "p" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);
	sessionSearch.bulkTagExisting(tagname);

	ProductionPage page = new ProductionPage(driver);
	page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.addDATFieldAtSecondRow(Input.DatFieldClassification, Input.DatSourceClassification, Input.randomText);
	page.fillingNativeSection();
	page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
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
	page.extractFile();
	driver.waitForPageToBeReady();
	File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_DAT.dat");
	page.isdatFileExist();

	String line;
	List<String> lines = new ArrayList<>();
	BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
	while ((line = brReader.readLine()) != null) {
		lines.add(line);
	}
	for (String a : lines) {
		System.out.println(a);
	}
	String NativePath = "Z:\\VOL0001\\Natives\\0001";
	base.compareTextViaContains(lines.get(4), NativePath, "Native path is dispalyed as expected", "Native path is not displayed in DAT file");
	brReader.close();
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	loginPage.logout();
}

/**
 * @author Brundha Test case id-RPMXCON-47940  Date:7/19/2022
 * @Description To Verify in production, branding based on the tag types
 * 
 */

@Test(description = "RPMXCON-47940", enabled = true, groups = { " regression" })

public void tiffSectionWithBrandingTags() throws Exception {
	base.stepInfo("Test case Id: RPMXCON-47940- Production Component");

	UtilityLog.info(Input.prodPath);
	base.stepInfo(
			"#### To Verify in production, branding based on the tag types ####");

	String tagname = "tag" + Utility.dynamicNameAppender();
	String productionname = "production" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	int PureHit = sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkTagExisting(tagname);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	int FirstFile = Integer.valueOf(beginningBates);
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.verifyTheTagOnLeftBranding(tagname, Input.tagNamePrev);
	page.navigateToNextSection();
	page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionWithTag(tagname);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();

	page.extractFile();
	int LastFile = PureHit + FirstFile;
	driver.waitForPageToBeReady();
	String home = System.getProperty("user.home");
	driver.waitForPageToBeReady();
	File TiffFile = new File(
			home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
	if (TiffFile.exists()) {
		base.passedStep("Tiff  file is displayed as expected");
	} else {
		base.failedStep("Tiff file is not displayed as expected");}
	
	page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.tagNamePrev);

	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	loginPage.logout();
}





/**
 * @author Brundha.T 
 * TestCase Id :RPMXCON-47943  Date:7/19/2022
 *  Description :To Verify Include Families Doc counts should get Generated in Production
 *        
 * 
 */
@Test(description = "RPMXCON-47943", enabled = true, groups = { "regression" })
public void asVerifyingIncludingFamilyDocsProducedInGenratedFile() throws Exception {

	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-47943 -Production Component");
	base.stepInfo("To Verify Include Families Doc counts should get Generated in Production");
	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	BaseClass base = new BaseClass(driver);

	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	int PureHit=sessionSearch.basicContentSearch(Input.telecaSearchString);
	sessionSearch.bulkFolderExisting(foldername);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.fillingTIFFSectionPrivDocs(tagname,Input.tagNamePrev);
	page.navigateToNextSection();
	page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	driver.scrollingToBottomofAPage();
	base.waitForElement(page.getIncludeFamilies());
	page.getIncludeFamilies().Click();
	driver.scrollPageToTop();
	page.getMarkCompleteLink().waitAndClick(10);
	base.waitTime(2);
	String DocCount = page.getDocumentSelectionLink().getText();
	System.out.println(DocCount);
	if(Integer.valueOf(DocCount)!=PureHit) {
		base.passedStep("Family Documents are included");
	}else {
		base.failedStep("Family Documents are not included");}
	driver.scrollPageToTop();
	page.getNextButton().waitAndClick(10);
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
	int NativeFile = dir_contents.length;
	
	if (Integer.valueOf(DocCount) <=(NativeFile)) {
		base.passedStep("Family Document is included in generated file");
	} else {
		base.failedStep("Family Document is not included");
	}

	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

	loginPage.logout();

}

/**
 * @author Brundha.T Date:7/21/2022 TestCase Id :RPMXCON-48030 Description :To
 *         Verify "No Rotation" is selected, pages are not rotated and are
 *         branded in the existing layout.(portrait/landscape layout remains as
 *         it is).
 * 
 */
@Test(description = "RPMXCON-48030", enabled = true, groups = { "regression" })
public void verifyTheNonRotatedDocumentInTiffImage() throws Exception {

	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-48030 -Production Component");
	base.stepInfo(
			"To Verify 'No Rotation' is selected, pages are not rotated and are branded in the existing layout.(portrait/landscape layout remains as it is).");
	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	BaseClass base = new BaseClass(driver);

	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.telecaSearchString);
	sessionSearch.bulkFolderExisting(foldername);
	sessionSearch.bulkTagExisting(tagname);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.fillingTIFFSectionPrivDocs(tagname, Input.tagNamePrev);
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
	page.extractFile();
	System.out.println("Unzipped the downloaded files");
	driver.waitForPageToBeReady();
	String home = System.getProperty("user.home");
	String firstFile = prefixID + beginningBates + suffixID;
	File file = new File(home + "/Downloads/VOL0001/Images/0001/" + firstFile + ".tiff");
	driver.waitForPageToBeReady();
	BufferedImage bimg = ImageIO.read(file);
	int width = bimg.getWidth();
	int height = bimg.getHeight();
	driver.waitForPageToBeReady();
	if (width < height) {
		base.passedStep("Tiff image is not rotated as expected");
	} else {
		base.failedStep("Tiff image is rotated");
	}

	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

	loginPage.logout();

}

/**
 * @author Brundha.T Date:7/21/2022 TestCase Id :RPMXCON-48205 Description :To
 *         Verify In Productions, Date format of the DateOnly fields should not
 *         include time portion
 * 
 */
@Test(description = "RPMXCON-48205", enabled = true, groups = { "regression" })
public void verifyingMasterDateOnlyInDownloadedFile() throws Exception {

	UtilityLog.info(Input.prodPath);
	BaseClass base = new BaseClass(driver);
	base.stepInfo("RPMXCON-48205 -Production Component");
	base.stepInfo("To Verify In Productions, Date format of the DateOnly fields should not include time portion");

	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.selectGenerateOption(false);
	page.slipSheetToggleEnable();
	page.fillingSlipSheetInTiffSection("MasterDateDateOnly");
	page.fillingSlipSheetInTiffSection("DocID");
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
	page.extractFile();
	driver.waitForPageToBeReady();
	String home = System.getProperty("user.home");
	String firstFile = prefixID + beginningBates + suffixID;
	File file = new File(home + "/Downloads/VOL0001/Images/0001/" + firstFile + ".ss.tiff");

	ITesseract instance = new Tesseract1();
	File tessDataFolder = LoadLibs.extractTessResources("tessdata");
	instance.setDatapath(tessDataFolder.getPath());
	driver.waitForPageToBeReady();
	String result = instance.doOCR(file);
	System.out.println(result);

	String[] MasterDateDateOnly = result.split(":");
	String Output = MasterDateDateOnly[1];

	String MasterDate = Output.trim().replaceAll("[a-zA-Z]", "");
	int MasterDateSize = MasterDate.length();
	if (MasterDateSize >= 11) {
		base.passedStep("MasterDateOnly displayed as expected");
	} else {
		base.failedStep("MasterDate is not displayed as expected");
	}

	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	loginPage.logout();

}

/**
 * @author Brundha.T Date:7/21/2022 TestCase Id :RPMXCON-47980 Description :To
 *         Verify In Productions, Document Level Numbering options Sub-bates
 *         number and min. number length should not be mandatory
 * 
 */
@Test(description = "RPMXCON-47980", enabled = true, groups = { "regression" })
public void productionGenerationWithoutDocumentLevel() throws Exception {

	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-47980 -Production Component");
	base.stepInfo(
			"To Verify In Productions, Document Level Numbering options Sub-bates number and min. number length should not be mandatory");
	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	 base = new BaseClass(driver);

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	driver.waitForPageToBeReady();
	page.getNumbering_Document_RadioButton().waitAndClick(10);
	page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	loginPage.logout();
}

/**
 * @author Brundha.T Date:7/21/2022 TestCase Id :RPMXCON-48286 Description :To
 *         Verify In Production , Selection of PDF or TIFF for Rotation on
 *         Component Page should not throw any error message.
 * 
 */
@Test(description = "RPMXCON-48286", enabled = true, groups = { "regression" })
public void verifyingSuccessMessageInComponentTab() throws Exception {

	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-48286 -Production Component");
	base.stepInfo(
			"To Verify In Production , Selection of PDF or TIFF for Rotation on Component Page should not throw any error message.");
	
	String tagname = "Tag" + Utility.dynamicNameAppender();
	BaseClass base = new BaseClass(driver);

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkTagExisting(tagname);

	ProductionPage page = new ProductionPage(driver);
	String productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.fillingPDFSection(tagname,Input.searchString1);
	page.getRotationLandScapeDropdown().waitAndClick(10);
	driver.scrollPageToTop();
	base.waitTillElemetToBeClickable(page.getMarkCompleteLink());
	page.getMarkCompleteLink().waitAndClick(10);
	if (page.gettext("Rotation sections in PDF and TIFF components must have the same configuration").isElementAvailable(2)) {
		base.failedStep("Error Message is Displayed");
	}else if (base.getSuccessMsgHeader().isElementAvailable(2)) {
		base.passedStep("Success message is displayed as expected");
		base.VerifySuccessMessage("Mark Complete successful");
		page.getNextButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.visibleCheck("Numbering and Sorting");
	}

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


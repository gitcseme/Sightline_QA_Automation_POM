package sightline.productions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.Color;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.asprise.ocr.Ocr;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression2 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	String searchText;
	SessionSearch search;
	SavedSearch savedSearch;
	DocListPage docList;
	TagsAndFoldersPage tagsAndFolderPage;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManagement;
	AssignmentsPage assignmentsPage;
	String foldername;
	String tagname;
	String productionname;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();
		base = new BaseClass(driver);
		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

	}

	/**
	 * @author Brundha Test case id-RPMXCON-55945
	 * @Description Verify that REDACTED text should displayd by default if user
	 *              selects the Redaction Tag in PDF
	 * 
	 */
	@Test(description="RPMXCON-55945",enabled = true, groups = { "regression" })
	public void verifyPlaceholderInPDFBurnRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55945 -Production Sprint 10");
		base.stepInfo("Verify that REDACTED text should displayd by default if user selects the Redaction Tag in PDF");

		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		page.selectGenerateOption(true);
		page.verifyPlaceholderTextInBurnRedaction(Input.defaultRedactionTag);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-55926
	 * @Description Verify changes in Text component section
	 * 
	 */
	@Test(description="RPMXCON-55926",enabled = true, groups = { "regression" })
	public void verifyTextInTextSection() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55926 -Production Sprint 10");
		base.stepInfo("Verify changes in Text component section");

		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.verifyTextInTextSection();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55947
	 * @Description:Verify if user included branding that is based on the tag types
	 *                     only then branding should be display on 'Preview'
	 *                     document and on produced documents also
	 */
	@Test(description="RPMXCON-55947",enabled = true, groups = { "regression" })
	public void verifyBrandingSectionInGenration() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55947 -Production Sprint 10");
		base.stepInfo(
				"Verify if user included branding that is based on the tag types only then branding should be display on 'Preview' document and on produced documents also");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(2);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(4);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.selectBrandingInTiffAndPdfSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		String beginningBates1 = page.getRandomNumber(1);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectBrandingInTiffAndPdfSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-55976
	 * @Description Verify that after Pregen check completed it should displays
	 *              'Reserving Bates Range' status on Production Geneartion page
	 * 
	 */
	@Test(description="RPMXCON-55976",enabled = true, groups = { "regression" })
	public void verifyStatusInGeneratePage() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55976 -Production Sprint 10");
		base.stepInfo(
				"Verify that after Pregen check completed it should displays 'Reserving Bates Range' status on Production Geneartion page");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
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
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Pre-Generation Checks Completed");
		page.verifyProductionStatusInGenPage("Reserving Bates Range");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55977
	 * @Description: Verify that it should displays 'Pre-Gen Check - 19999/20000
	 *               docs' status on Progress bar in Production Tile View
	 */
	@Test(description="RPMXCON-55977",enabled = true, groups = { "regression" })
	public void preGenChecksStatusVerifyOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55977 -Production Sprint 10");
		base.stepInfo(
				"Verify that it should displays 'Pre-Gen Check - 19999/20000 docs' status on Progress bar in Production Tile View");
		String testData1 = Input.testData1;
		String foldername = "FolderProd" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify Pre-gen checks is in progress status on Tile view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(3);
		productionname = "p" + Utility.dynamicNameAppender();
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
		page.clickGenarateWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Pre-Gen Checks - 6/6 Docs", productionname);

		// Deleting folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);

		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-55954
	 * @Description Verify that use cannot access the Production deatils by copying
	 *              the Production URL if user does not have Production rights
	 * 
	 */
	@Test(description="RPMXCON-55954",enabled = true, groups = { "regression" })
	public void verifyingProductionAccess() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("RPMXCON-55954 -Production Sprint 10");
		base.stepInfo(
				"Verify that use cannot access the Production deatils by copying the Production URL if user does not have Production rights");

		base = new BaseClass(driver);
		base.UnSelectTheProductionChecKboxInUser(Input.pa1userName);
		driver.Navigate().refresh();
		base.impersonateSAtoPA();

		driver.waitForPageToBeReady();
		String productionname="prod"+Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		driver.waitForPageToBeReady();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not  displayed as expected");
		}
		driver.Navigate().back();

		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base = new BaseClass(driver);
		base.UnSelectTheProductionChecKboxInUser(Input.pa1userName);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47775
	 * @Description To Verify the availability of 'Delete' Option in drop down
	 *              action menu For(Draft Mode)
	 * 
	 */
	@Test(description="RPMXCON-47775",enabled = true, groups = { "regression" })
	public void AvailabilityOfDeleteOptionInDraftMode() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47775 -Production Sprint 10");
		base.stepInfo("To Verify the availability of 'Delete' Option in drop down action menu For(Draft Mode");
		String productionname = "p" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.navigateToNextSection();

		base.stepInfo("Navigating back to Production home page");
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		base.stepInfo("Deleting the Drafted production");
		page.deleteProduction(productionname);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48271
	 * @Description To Verify Produced PDFs should be available for being presented
	 *              in the DocView for the document
	 * 
	 */
	@Test(description="RPMXCON-48271",enabled = true, groups = { "regression" })
	public void PDFDocumentDisplayedInProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48271 -Production Sprint 10");
		base.stepInfo(
				"To Verify Produced PDFs should be available for being presented in the DocView for the document");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(3);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		base.stepInfo("View searched for  docs in Doc view");
		sessionsearch.ViewInDocViewWithoutPureHit();

		driver.waitForPageToBeReady();
		DocViewPage doc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		doc.clickOnImageTab();
		driver.waitForPageToBeReady();
		doc.verifyProductionNameForPDFFileInDocView(productionname);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47749
	 * @Description:Admin able to view production guard information on the self
	 *                    production wizard
	 */
	@Test(description="RPMXCON-47749",enabled = true, groups = { "regression" })
	public void verifyProductionGuardInformation() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47749 -Production Sprint 10");

		base.stepInfo("Admin able to view production guard information on the self production wizard");
		String testData1 = Input.testData1;
		String tagname = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(10);
		driver.scrollingToBottomofAPage();

		page.getTIFFTab().waitAndClick(10);

		page.fillingPrivledgedDocForPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.getAddRule().waitAndClick(10);
		base.waitTillElemetToBeClickable(page.getRemoveLink());
		page.getRemoveLink().Click();

		if (page.getRemoveLink().isDisplayed()) {
			base.failedStep("rules popup screen is displayed");
		} else {
			base.passedStep("Rules popup screen is not displayed as expected");
		}

		page.AddRuleAndRemoveRule(tagname);
		String Doc = page.getDocumentSelectionLink().getText();
		base.stepInfo("Navigating to Docview page");
		driver.waitForPageToBeReady();
		base.digitCompareEquals(purehit, Integer.parseInt(Doc), "Priv guard and purehit Documents count are equal",
				"Priv guard and purehit Documents count are not  equal");

		page.getDocView().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();

		MiniDocListPage miniDocListPage = new MiniDocListPage(driver);
		base.waitTillElemetToBeClickable(miniDocListPage.getDocumentCountFromDocView());
		driver.waitForPageToBeReady();
		if (miniDocListPage.getDocumentCountFromDocView().Displayed()) {
			String sizeofList = miniDocListPage.getDocumentCountFromDocView().getWebElement().getText();
			driver.waitForPageToBeReady();
			System.out.println("Doc view" + sizeofList);

			String[] doccount = sizeofList.split(" ");
			String Document = doccount[1];
			System.out.println("doclist page document count is" + Document);

			base.digitCompareEquals(Integer.parseInt(Doc), Integer.parseInt(Document),
					"Document count is equal as expected", "Count Mismatches with the Documents");
		}

		driver.waitForPageToBeReady();
		driver.Navigate().back();
		driver.waitForPageToBeReady();
		for (int i = 0; i < 3; i++) {
			driver.waitForPageToBeReady();
			page.getNextButton().waitAndClick(10);
		}
		String docCount = page.VerifyingDocListCountWithPrivGaurdCount();

		DocListPage doc = new DocListPage(driver);

		
		base.stepInfo("Navigated  to doclist page and verifying the DocCount");
		String DocumentCount = doc.verifyingDocCount();

		base.textCompareEquals(docCount, DocumentCount, "The document count is equal as expected",
				"The document count is not equal as expected");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-55955
	 * @Description Verify that RMU can access the Production by copying the
	 *              Production URL if user is part of that security group/Project
	 * 
	 */
	@Test(description="RPMXCON-55955",enabled = true, groups = { "regression" })
	public void verifyingProductionPageAccessUsingSecurityGroup() throws Exception {
		base.stepInfo("RPMXCON-55955 -Production Sprint 10");
		base.stepInfo(
				"Verify that RMU can access the Production by copying the Production URL if user is part of that security group/Project");

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		String securityGroup ="Production_Security_Group"+Utility.dynamicNameAppender();
		sg.createSecurityGroups(securityGroup);
		System.out.println(securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		
		base = new BaseClass(driver);
		base.SelectSecurityGrp(Input.rmu1userName,securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(3);
		page = new ProductionPage(driver);
		page.addANewProduction(productionname);
		page.fillingDATSection();

		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		driver.waitForPageToBeReady();
		String currentURL = driver.getWebDriver().getCurrentUrl();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		page = new ProductionPage(driver);
		page.addANewProduction(productionname1);
		page.fillingDATSection();

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		if (page.getBeginningBates().isDisplayed()) {
			base.passedStep("Navigated to the previous  production URL as expected");
		} else {
			base.failedStep(" Not Navigated to the previous production URL as expected");
		}

		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base = new BaseClass(driver);
		driver.Navigate().refresh();
		base.SelectDefaultSecurityGrp(Input.rmu1userName);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55943
	 * @Description:Verify that Production should be generated successfully if PDF
	 *                     documents are ICE processed with Upload set
	 */
	@Test(description="RPMXCON-55943",enabled = true, groups = { "regression" })
	public void generateTheProdcutionForPDFFiles() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-55943 -Production Sprint 10");
		base.stepInfo(
				"Verify that Production should be generated successfully if PDF documents are ICE processed with Upload set");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(4);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55918
	 * @Description:To verify that when Tech Issue placeholdering is enabled, the
	 *                 text file should exported with the placeholder text.
	 */
	@Test(description="RPMXCON-55918",enabled = true, groups = { "regression" })
	public void GenerateTheProductionForTechIssuePlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55918-from Production");
		base.stepInfo(
				"To verify that when Tech Issue placeholdering is enabled, the text file should exported with the placeholder text.");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47175
	 * @Description:Verify that produced PDF/TIFF files should not be split when
	 *                     'Split Sub Folders' is ON with split count as 10 and
	 *                     selected documents <= 10
	 */
	 @Test(description="RPMXCON-47175",enabled = true, groups = { "regression" })
	public void verifyTheSubFolderAfterGenration() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47175 -Production Sprint 10");
		base.stepInfo(
				"Verify that produced PDF/TIFF files should not be split when 'Split Sub Folders' is ON with split count as 10 and selected documents <= 10");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// document for pdf section
		ProductionPage page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// document for tiff section
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkTagExisting(tagname1);

		// document for pdf section
		page = new ProductionPage(driver);
		String productionname2 = "p" + Utility.dynamicNameAppender();
		String beginningBates3 = page.getRandomNumber(2);
		page.addANewProduction(productionname2);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname1, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates3);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname1);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname2);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// document for tiff section
		page = new ProductionPage(driver);
		String productionname3 = "p" + Utility.dynamicNameAppender();
		String beginningBates4 = page.getRandomNumber(2);
		page.addANewProduction(productionname3);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates4);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname1);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname3);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutDownload();
		base.passedStep(
				"Verify that produced PDF/TIFF files should not be split when 'Split Sub Folders' is ON with split count as 10 and selected documents <= 10");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname1, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-55956
	 * @Description Verify that if 'Volume Included' toggle is ON then "Archive for
	 *              FTP" should archive everything in the Production Directory
	 *              including ''Volume Included'' subfolder
	 * 
	 */
	 @Test(description="RPMXCON-55956",enabled = true, groups = { "regression" })
	public void verifyingTheProductionOnVolumeIncludedToggle() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-55956 -Production Sprint 10");
		base.stepInfo(
				"Verify that if 'Volume Included' toggle is ON then 'Archive for FTP' should archive everything in the Production Directory including ''Volume Included'' subfolder");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
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
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.verifyVolumeIncludedToggleInProductionSelection();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49901
	 * @Description Verify and generate Production with Search as source
	 * 
	 */
	 @Test(description="RPMXCON-49901",enabled = true, groups = { "regression" })
	public void verifyProductionGenerationWithSearches() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-49901 -Production Sprint 10");
		base.stepInfo("Verify and generate Production with Search as source");

		String SearchName = "Search" + Utility.dynamicNameAppender();
		String SearchName1 = "Search" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearchAtAnyRootGroup(SearchName, Input.shareSearchDefaultSG);
		sessionSearch.saveSearchAtAnyRootGroup(SearchName1, Input.shareSearchPA);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(SearchName, SearchName1);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48577
	 * @Description To verify that Export Bates option is available on
	 *              Production-Generate tab
	 * 
	 */
	@Test(description="RPMXCON-48577",enabled = true, groups = { "regression" })
	public void verifyExportBatesOptionInGenerateTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48577 - from Production");
		base.stepInfo("To verify that Export Bates option is available on Production-Generate tab");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		String beginningBates = page.getRandomNumber(2);
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
		page.getExportBatesButton().ScrollTo();
		if (page.getExportBatesButton().isDisplayed()) {
			base.passedStep("Export Bates Option is visible in Generate tab as Expected");
		} else {
			base.failedStep("Export Bates Option is not visible in Generate tab as Expected");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48601
	 * @Description To verify that Export bates is disabled if pre gen check is not
	 *              completed
	 * 
	 */
	@Test(description="RPMXCON-48601",enabled = true, groups = { "regression" })
	public void verifyExportBatesOptionDisabledInGenerateTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48601 - from Production");
		base.stepInfo("To verify that Export bates is disabled if pre gen check is not completed");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
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
		page.getbtnProductionGenerate().isDisplayed();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");
		String color = page.getExportBatesButton().GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#3276b1";
		base.textCompareEquals(ActualColor, ExpectedColor,
				"Export Bates option is disabled before pre gen check as Expected",
				"Export Bates option is not disabled before pre gen check as Expected");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48642
	 * @Description:To verify that on Tiff OR PDF section, 'Do Not Produce TIFFs for
	 *                 Natively Produced Docs' option is disabled by default
	 */
	@Test(description="RPMXCON-48642",enabled = true, groups = { "regression" })
	public void verifyingNativelyProducedDocsToggleIsDisAbledInTiffAndPDFSection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48642 - from Production");
		base.stepInfo(
				"To verify that on Tiff OR PDF section, 'Do Not Produce TIFFs for Natively Produced Docs' option is disabled by default");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);

		base.stepInfo("Verifying 'Do Not Produce TIFFs for Natively Produced Docs' in TIFF section");
		page.verifyingNativelyProducedToggle();

		base.stepInfo("Verifying 'Do Not Produce TIFFs for Natively Produced Docs' in PDF section");
		page.getPDFGenerateRadioButton().waitAndClick(10);

		String color = page.getDoNotProduceFullContentTiff().GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#e54036";
		base.textCompareEquals(ActualColor, ExpectedColor,
				"Do Not Produce TIFFs for Natively Produced Docs' option is disabled by default  as Expected",
				" Do Not Produce TIFFs for Natively Produced Docs' option is not  disabled by default as Expected");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48604
	 * @Description Verify the exported CSV data
	 * 
	 */
	@Test(description="RPMXCON-48604",enabled = true, groups = { "regression" })
	public void verifyExportBatesGeneratedFile() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48604 - from Production-sprint 11");
		base.stepInfo("Verify the exported CSV data");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
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
		page.getbtnProductionGenerate().isDisplayed();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getExportBatesButton().isDisplayed();
		page.getExportBatesButton().waitAndClick(5);
		BaseClass base = new BaseClass(driver);
		base.VerifySuccessMessage(
				"Export bates range has been added to background services. You will get notification once completed");
		driver.waitForPageToBeReady();
		page.getNotificationLink().waitAndClick(5);
		page.getViewAll().waitAndClick(10);
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
		}
		page.getDownloadLinkforExport().Click();
		base.csvFileVerification();
		base.passedStep("Verified the exported CSV data");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48603
	 * @DescriptionTo verify that user can download the CSV file once
	 *                Production-Generate-Export is completed
	 * 
	 */
	@Test(description="RPMXCON-48603",enabled = true, groups = { "regression" })
	public void verifyExportBatesGeneratedFileInNotification() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48603 - from Production sprint 11");
		base.stepInfo("To verify that user can download the CSV file once Production-Generate-Export is completed");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
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
		page.getbtnProductionGenerate().isDisplayed();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");

		page.getExportBatesButton().waitAndClick(5);
		BaseClass base = new BaseClass(driver);
		base.VerifySuccessMessage(
				"Export bates range has been added to background services. You will get notification once completed");
		page.getNotificationLink().waitAndClick(5);
		page.verifyExportedCSVFile();
		base = new BaseClass(driver);
		base.csvFileVerification();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48654
	 * @Description To verify that if "Do Not Produce PDFs for Natively Produced
	 *              Docs" is Enabled, then only Native should be produced
	 * 
	 */
	@Test(description="RPMXCON-48654",enabled = true, groups = { "regression" })
	public void verifyNativelyProducedRToggleForGeneration() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48654-Production Sprint 11");
		base.stepInfo(
				"To verify that if 'Do Not Produce PDFs for Natively Produced Docs' is Enabled, then only Native should be produced");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(Input.telecaSearchString, Input.docFile);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTIFFSection(tagname, Input.searchString4);
		driver.scrollPageToTop();
		page.getDoNotProduceFullContentTiff().isDisplayed();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.verifyVolumeIncludedToggleInProductionSelection();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48307
	 * @Description To verify that the order of docs in OPT is matching the order of
	 *              docs in DAT.
	 * 
	 */
	@Test(description="RPMXCON-48307",enabled = true, groups = { "regression" })
	public void verifyOrderOfDATAndOPTInGeneratedProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48307-Production Sprint 11");
		base.stepInfo("To verify that the order of docs in OPT is matching the order of docs in DAT.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.fillingAdvancedInTiffSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48530
	 * @Description:To verify that by Blank Page Removal option is OFF by default in
	 *                 Tiff/PDF section and should display confirmation message when
	 *                 it is enable
	 * 
	 */
	@Test(description="RPMXCON-48530",enabled = true, groups = { "regression" })
	public void verifyBlankPageRemovalToggle() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48530 -Production Sprint 11");
		base.stepInfo(
				"To verify that by Blank Page Removal option is OFF by default in Tiff/PDF section and should display confirmation message when it is enable.");

		String productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.verifyBlankPageRemovalMeassage();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47173
	 * @Description:Verify that TIFF files should be copied to folder when 'Split
	 *                     Sub Folders' is ON with split count as 10
	 */
	@Test(description="RPMXCON-47173",enabled = true, groups = { "regression" })
	public void verifyTheSubFolderInProductionGeneration() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47173 -Production Sprint 11");
		base.stepInfo(
				"Verify that TIFF files should be copied to folder when 'Split Sub Folders' is ON with split count as 10");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.verifySubFolderToggle();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48196
	 * @Description:To Verify In Productions, production template Should retain the
	 *                 redaction tags configured in the production.
	 */
	@Test(description="RPMXCON-48196",enabled = true, groups = { "regression" })
	public void verifySaveTemplateAndRetainedValueInBurnRedaction() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-48196 -Production Sprint 11");
		base.stepInfo(
				"To Verify In Productions, production template Should retain the redaction tags configured in the production.");

		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);

		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingMP3FileWithBurnRedaction(Redactiontag1);
		page.navigateToNextSection();

		ProductionPage Page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		driver.Navigate().refresh();
		Page.savedTemplateAndNewProdcution(productionname, Templatename);
		Page.baseInfoLoadTemplate(productionname1, Templatename);
		page.verifyingMP3FileBurnRedaction(Redactiontag1);
		page.getCheckBoxCheckedVerification(page.getMP3FileSelectRedactionTags());
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48285
	 * @Description:To Verify Production Using Template Should be able to load the
	 *                 configuration of Rotation in PDF and TIFF.
	 */
	@Test(description="RPMXCON-48285",enabled = true, groups = { "regression" })
	public void verifySaveTemplateAndRetainedValueInRotationConfiguration() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-48285 -Production Sprint 11");
		base.stepInfo(
				"To Verify Production Using Template Should be able to load the configuration of Rotation in PDF and TIFF");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		driver.scrollPageToTop();
		page.getRotationLandScapeDropdown().Click();
		page.navigateToNextSection();

		ProductionPage Page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		driver.Navigate().refresh();
		Page.savedTemplateAndNewProdcution(productionname, Templatename);
		Page.baseInfoLoadTemplate(productionname1, Templatename);
		page.verifyRotationInComponentTab("pdf");
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47976
	 * @Description: To Verify Keep Docs w/ No Master Date on Numbering and Sorting
	 *               Page.
	 */
	@Test(description="RPMXCON-47976",enabled = true, groups = { "regression" })
	public void verifyTheMasterDateInGeneratedProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47976 -Production Sprint 11");
		base.stepInfo("To Verify Keep Docs w/ No Master Date on Numbering and Sorting Page.");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(testData1, Input.newNearDupeDocId);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
		page.verifyingComponentTabOnMarkComplete();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.verifyMasterDateRetainedOnMarkComplete();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-46895
	 * @Description:Verify the Production for Audio Files for International Language
	 *                     Package.
	 * 
	 */
	@Test(description="RPMXCON-46895",enabled = true, groups = { "regression" })
	public void ProductionGenerateForAudioFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-46895 -Production Sprint 11");
		base.stepInfo("Verify the Production for Audio Files for International Language Package.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.SelectMP3FileAndVerifyLstFile();
		driver.scrollPageToTop();
		page.getSaveOption().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48301
	 * @Description:To verify that in Production, the order of Documents is
	 *                 maintained even when the user has not "checked" the option to
	 *                 "club family members together".
	 */
	@Test(description="RPMXCON-48301",enabled = true, groups = { "regression" })
	public void verifyOrderOfDocumentsInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48301 -Production Sprint 11");
		base.stepInfo(
				"To verify that in Production, the order of Documents is maintained even when the user has not 'checked' the option to 'club family members together'.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "335ID00000005");
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.selectingAllDocuments();
		driver.waitForPageToBeReady();
		doc.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingWithoutFamilyMember(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47966
	 * @DescriptionTo Verify The OPT/LOG file generated in a production have all
	 *                required information
	 * 
	 */
	@Test(description="RPMXCON-47966",enabled = true, groups = { "regression" })
	public void verifyOPTInGeneratedProduction() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-47966-Production component");
		base.stepInfo("To Verify The OPT/LOG file generated in a production have all required information.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(1);
		String suffixID = Utility.randomCharacterAppender(1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(4);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.fillingAdvancedInTiffSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		String name = page.getProduction().getText().trim();
		String home = System.getProperty("user.home");
        page.extractFile();

		driver.waitForPageToBeReady();
		for (String line : Files
				.readAllLines(Paths.get(home + "/Downloads/" + "VOL0001/Load Files/" + name + "_TIFF.OPT"))) {
			for (String part : line.split("\\s+")) {

				System.out.println("the value is" + part);

				if (part.contains("Z:\\VOL0001\\Images") && part.contains("tiff")) {
					System.out.println("Text is displayed as expected");
				} else {
					base.failedStep("the text is not displayed as expected");
				}
			}
		}
		base.passedStep("Text is displayed as expected");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();


	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-56054
	 * @Description: Verify that after Post Geneation is completed, it will displays
	 *               status on Production Grid View page as 'Post Generation QC
	 *               Check Complete'
	 */
	@Test(description="RPMXCON-56054",enabled = true, groups = { "regression" })
	public void verifyPostGenCompleteStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56054 - from Production component");
		base.stepInfo(
				"Verify that after Post Geneation is completed, it will displays status on Production Grid View page as 'Post Generation QC Check Complete'");

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
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
		page.clickGenarateWithoutWait();
		page.getbtnContinueGeneration().isElementAvailable(150);
		page.getbtnContinueGeneration().isDisplayed();
		base.waitForElement(page.getbtnContinueGeneration());
		page.getbtnContinueGeneration().waitAndClick(10);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Post-Gen QC Checks Complete", productionname);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48876
	 * @Description:To verify that belly band message is displays if user map the
	 *                 one source field to multiple DAT fields
	 */
	@Test(description="RPMXCON-48876",enabled = true, groups = { "regression" })
	public void verifyPreGenStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48876 - from Production component");
		base.stepInfo(
				"To verify that belly band message is displays if user map the one source field to multiple DAT fields");

		String Batesnumber = "B" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, Input.batesNumber, Batesnumber);

		base.stepInfo("Verifying belly band message in production component tab");
		page.verifyingBellyBandMessageInDATSection(Batesnumber);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-46870
	 * @Description:To Verify generation of redacted TIFFs.
	 */
	@Test(description="RPMXCON-46870",enabled = true, groups = { "regression" })
	public void verifyGenerationInRedactedTiff() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-46870 - from Production component");
		base.stepInfo("To Verify generation of redacted TIFFs.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);

		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		// create folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTags(Input.defaultRedactionTag, Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47931
	 * @Description:To Verify, when Include Family Members is selected, should not
	 *                 give error message on click of Mark Complete
	 */
	@Test(description="RPMXCON-47931",enabled = true, groups = { "regression" })
	public void verifyingIncludeFamiliesToggleInDocumentSelectionPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47931 - from Production component");
		base.stepInfo(
				"To Verify, when Include Family Members is selected, should not give error message on click of Mark Complete");

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		driver.waitForPageToBeReady();
		page.visibleCheck("Production Components");
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.visibleCheck("Numbering and Sorting");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSlectionTab(foldername);
		page.verifyingIncludeFamiliesToggleInDocumentSelectionPage();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47750
	 * @Description:Admin able to view and enter production guard information on the
	 *                    self production wizard.
	 */
	@Test(description="RPMXCON-47750",enabled = true, groups = { "regression" })
	public void verifyProductionGuardInformationinProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47750-Production component");

		base.stepInfo("Admin able to view and enter production guard information on the self production wizard.");
		String testData1 = Input.testData1;
		String tagname = Input.randomText + Utility.dynamicNameAppender();

		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for Tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.getTIFFTab().waitAndClick(10);
		page.fillingPrivledgedDocForPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		String DocCount = page.getDocumentSelectionLink().getText();
		base.digitCompareEquals(purehit, Integer.parseInt(DocCount),
				"Document count is displayed correctly as expected",
				"Document count is not displayed correctly as expected");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48206
	 * @Description:To Verify In Productions, the validation of specific file types
	 *                 when entering placeholder text for TIFF/PDF.
	 */
	@Test(description="RPMXCON-48206",enabled = true, groups = { "regression" })
	public void verifyingWarningMessageInProductionComponentTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48206-Production component");
		base.stepInfo(
				"To Verify In Productions, the validation of specific file types when entering placeholder text for TIFF/PDF.");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithNativelyProducedDocsFileType(Input.randomText);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48632
	 * @Description To verify that exported CSV should be sorted by BegBates
	 * 
	 */
	@Test(description="RPMXCON-48632",enabled = true, groups = { "regression" })
	public void verifyExportCSVFileSorted() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48632 - from Production-sprint 11");
		base.stepInfo("To verify that exported CSV should be sorted by BegBates");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		base.waitTillElemetToBeClickable(page.getExportBatesButton());
		page.getExportBatesButton().waitAndClick(10);
		BaseClass base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		page.getNotificationLink().isDisplayed();
		page.getNotificationLink().waitAndClick(5);
		page.getViewAll().waitAndClick(10);
		for (int i = 0; i < 10; i++) {
			if (page.getDownloadLinkforExport().isDisplayed()) {
				page.getDownloadLinkforExport().Click();
				break;
			} else {
				driver.Navigate().refresh();
			}
		}
        base.waitTime(3);
		base.VerifyingCSVFileDownloadedAndSorted();
		base.passedStep("verified the exported CSV is sorted by BegBates");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48167
	 * @Description:To Verify All MP3 Files info should get loaded when using (Saved
	 *                 )Template for Production.
	 */
	@Test(description="RPMXCON-48167",enabled = true, groups = { "regression" })
	public void verifySaveTemplateAndRetainedValueInComponentTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48167 -Production component");
		base.stepInfo("To Verify All MP3 Files info should get loaded when using (Saved )Template for Production.");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.fillingMP3FileWithBurnRedaction();
		page.navigateToNextSection();

		ProductionPage Page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		driver.Navigate().refresh();
		Page.savedTemplateAndNewProdcution(productionname, Templatename);
		Page.baseInfoLoadTemplate(productionname1, Templatename);

		// Method to verify the retained value in Mp3 File
		page.verifyingMP3FileBurnRedaction(Input.defaultRedactionTag);
		page.getCheckBoxCheckedVerification(page.getMP3FileSelectRedactionTags());
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48006
	 * @Description To Verify Produced PDFs are being presented in the DocView for
	 *              the document
	 * 
	 */
	@Test(description="RPMXCON-48006",enabled = true, groups = { "regression" })
	public void PDFDocumentDisplayedInDocViewPage() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48006 -Production Component");
		base.stepInfo("To Verify Produced PDFs are being presented in the DocView for the document");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		base.stepInfo("Navigate to docview");
		sessionsearch.ViewInDocViewWithoutPureHit();

		driver.waitForPageToBeReady();
		DocViewPage doc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		doc.clickOnImageTab();
		driver.waitForPageToBeReady();
		doc.verifyProductionNameForPDFFileInDocView(productionname);
		driver.waitForPageToBeReady();
		doc.getSelectedAreaElement().isElementAvailable(10);
		if (doc.getSelectedAreaElement().isDisplayed()) {
			base.passedStep("Produced PDFs  is presented in the DocView for the document");
		} else {
			base.failedStep("Produced PDFs is not presented in the DocView for the document");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47993
	 * @Description To Verify The Volume specified in the Production output step
	 *              should be created where it is specified,
	 * 
	 */
	@Test(description="RPMXCON-47993",enabled = true, groups = { "regression" })
	public void VerifyingVolumeInProductionOutput() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47993 -Production Component");
		base.stepInfo(
				"To Verify The Volume specified in the Production output step should be created where it is specified,");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String VolumeName = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.GetVolumeName().Click();
		page.GetVolumeName().SendKeys(VolumeName);
		page.GetVolumeLocation().isDisplayed();
		page.GetVolumeLocation().selectFromDropdown().selectByVisibleText("In Delivery Folder");
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		String home= System.getProperty("user.home");
		page.extractFile();

		driver.waitForPageToBeReady();
		File file = new File(home + "/Downloads/" + VolumeName + "/Natives/0001");
		File Textfile = new File(home + "/Downloads/" + VolumeName + "/Text/0001");
		File loadfile = new File(home + "/Downloads/" + VolumeName + "/Load Files");

		if (file.exists()) {
			base.passedStep("Volume is displayed in  the given production directory");
		} else {
			base.failedStep("Volume is displayed  not in the given production directory");
		}

		if (Textfile.exists()) {
			base.passedStep("Volume is displayed in  the given production directory");
		} else {
			base.failedStep("Volume is displayed  not in the given production directory");
		}

		if (loadfile.exists()) {
			base.passedStep("Volume is displayed in  the given production directory");
		} else {
			base.failedStep("Volume is displayed  not in the given production directory");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48032
	 * @Description To Verify file group type (.mdb/.mdf) selection under Native for
	 *              Production Should work fine.
	 * 
	 */
	@Test(description="RPMXCON-48032",enabled = true, groups = { "regression" })
	public void verifyingNativeFileType() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48032 -Production Component");
		base.stepInfo("To Verify file group type (.mdb/.mdf) selection under Native for Production Should work fine.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "STC4_00000992");
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);

		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectingNativeFileType();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		String home= System.getProperty("user.home");
		page.extractFile();

		driver.waitForPageToBeReady();
		File file = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".mdb");

		if (file.exists()) {
			base.passedStep("Volume is displayed in  the given production directory");
		} else {
			base.failedStep("Volume is displayed  not in the given production directory");
		}
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49539
	 * @Description Validate Production with tiff and text options
	 * 
	 */
	@Test(description="RPMXCON-49539",enabled = true, groups = { "regression" })
	public void verifyingTheGenerationOfProdcution() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-49539 -Production Component");
		base.stepInfo("Validate Production with tiff and text options");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(3);
		doc.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		String name = page.getProduction().getText().trim();
		String home= System.getProperty("user.home");
		page.extractFile();


		driver.waitForPageToBeReady();
		File file = new File(home + "/Downloads/VOL0001/Natives/0001/");
		File Textfile = new File(home + "/Downloads/VOL0001/Text/0001/" + prefixID + beginningBates + suffixID + ".txt");
		File TiffFile = new File(home + "/Downloads/" + "VOL0001/Load Files/" + name + "_TIFF.OPT");
		System.out.println("BATES" + prefixID + beginningBates + suffixID);

		if (file.exists()) {
			base.passedStep("filetype is displayed as expected");
		} else {
			base.failedStep("filetype is not displayed as expected");
		}

		if (Textfile.exists()) {
			base.passedStep("Text is generated successfully");
		} else {
			base.failedStep("Text is not generated successfully");
		}

		if (TiffFile.exists()) {
			base.passedStep("Tiff is generated successfully");
		} else {
			base.failedStep("Tiff is not generated successfully");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");

		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47997
	 * @Description To Verify In Productions, the workproduct fields for slip sheets
	 * 
	 */
	@Test(description="RPMXCON-47997",enabled = true, groups = { "regression" })
	public void verifyingWorkProductFieldInProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-47997 -Production Component");
		base.stepInfo("To Verify In Productions, the workproduct fields for slip sheets");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String BatesNumber = "B" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow("Doc Basic", "DocID", BatesNumber);
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.FillingWorkProductInTiffSection(foldername);
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
		String home= System.getProperty("user.home");
		page.extractFile();
		File imageFile = new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".SS.tiff");
	       
	     ITesseract instance = new Tesseract1();
	     File tessDataFolder = LoadLibs.extractTessResources("tessdata");
	     instance.setDatapath(tessDataFolder.getPath());

	    String result = instance.doOCR(imageFile);
		System.out.println(result);

		if (result.contains(foldername)) {
			base.passedStep("Workproduct  is displayed as expected");
		} else {
			base.failedStep("Workproduct is not displayed as expected");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

		
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48646
	 * @Description To verify that if "Do Not Produce TIFFs for Natively Produced
	 *              Docs" is Enabled, then only Native should be produced
	 * 
	 */
	@Test(description="RPMXCON-48646",enabled = true, groups = { "regression" })
	public void verifyingNativelyProducedDocumentInProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48646 -Production Component");
		base.stepInfo(
				"To verify that if 'Do Not Produce TIFFs for Natively Produced Docs' is Enabled, then only Native should be produced");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		int pureHit = sessionSearch.basicContentSearchForTwoItems(Input.telecaSearchString, Input.TiffDocId);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		driver.scrollPageToTop();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
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
		String home= System.getProperty("user.home");
		page.extractFile();
		driver.waitForPageToBeReady();
		File dir = new File(home + "/Downloads/VOL0001/Natives/0001/");
		File[] dir_contents = dir.listFiles();
		System.out.println(dir_contents.length);
		int nativefile = dir_contents.length;

		if (pureHit != nativefile) {
			base.passedStep("Natively produced document is generated successfully as expected");
		} else {
			base.failedStep("Natively produced document is not generated successfully as expected");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49868
	 * @Description Verify that Production for redacted PDF should generated
	 *              successfully if PDF documents are ICE processed with Mapped set
	 * 
	 */
	@Test(description="RPMXCON-49868",enabled = true, groups = { "regression" })
	public void verifyingTheGenerationOfProduction() throws Exception {

		SessionSearch session = new SessionSearch(driver);

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49868 -Production Component");
		base.stepInfo(
				"Verify that Production for redacted PDF should generated successfully if PDF documents are ICE processed with Mapped set");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		
		DataSets dataset = new DataSets(driver);
		DocListPage doc = new DocListPage(driver);
		session.basicMetaDataSearch(Input.docFileExt, null, "PDF", null);
		session.ViewInDocList();
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		doc.bulkTagExistingFromDoclist(tagname);

		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.selectDefaultSecurityGroup();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		driver.scrollPageToTop();
		base = new BaseClass(driver);
		driver.Navigate().refresh();
		base.impersonatePAtoRMU();
		session.switchToWorkproduct();
		session.selectTagInASwp(tagname);
		driver.waitForPageToBeReady();
		session.getQuerySearchButton().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		session.getPureHitAddButton().waitAndClick(10);
		session.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.selectDoc1();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48647
	 * @Description To verify that if "Do not produce full content TIFF / PDFs or
	 *              placeholder TIFF / PDFs for Natively Produced Docs" is enabled,
	 *              then TIFFs with Placeholder should not produced, It should
	 *              export only Natives.
	 * 
	 */
	@Test(description="RPMXCON-48647",enabled = true, groups = { "regression" })
	public void verifyingNativelyProducedDocInProducedFile() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48647 -Production Component");
		base.stepInfo(
				"To verify that if 'Do not produce full content TIFF / PDFs or placeholder TIFF / PDFs for Natively Produced Docs' is enabled, then TIFFs with Placeholder should not produced, It should export only Natives.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		int pureHit = sessionSearch.basicContentSearchForTwoItems(Input.telecaSearchString, Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);

		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithNativelyProducedDocs(Input.tagNameTechnical);
		driver.scrollPageToTop();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
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
		String home= System.getProperty("user.home");
		driver.waitForPageToBeReady();
		page.extractFile();
		driver.waitForPageToBeReady();
		File dir = new File(home + "/Downloads/VOL0001/Natives/0001/");
		File[] dir_contents = dir.listFiles();
		System.out.println(dir_contents.length);
		int nativefile = dir_contents.length;

		if (pureHit != nativefile) {
			base.passedStep("Natively produced document is generated successfully as expected");
		} else {
			base.failedStep("Natively produced document is not generated successfully as expected");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48179
	 * @DescriptionTo Verify For Multimedia file group Production Generation with
	 *                some doc having Priv Tag Classification.
	 * 
	 */
	@Test(description="RPMXCON-48179",enabled = true, groups = { "regression" })
	public void verifyTheGenerationOfProductionWithNativeWithoutPrivFile() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48179 -Production Component");
		base.stepInfo(
				"To Verify For Multimedia file group Production Generation with some doc having Priv Tag Classification");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String VolumeName = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", Input.fileGroup);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.GetVolumeName().Click();
		page.GetVolumeName().SendKeys(VolumeName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		driver.waitForPageToBeReady();
		String home= System.getProperty("user.home");
		page.extractFile();
		driver.waitForPageToBeReady();
		File file = new File(home + "/Downloads/" + VolumeName + "/Natives/0001");
		File TiffFile = new File(home + "/Downloads/" + VolumeName + "/Load Files/" + productionname + "_TIFF.OPT");
		File DatFile = new File(home + "/Downloads/" + VolumeName + "/Load Files/" + productionname + "_DAT.dat");
		File NativeFile = new File(home + "/Downloads/" + VolumeName + "/Load Files/" + productionname + "_Native.lst");

		if (!file.exists()) {
			base.passedStep("Native file is not copied to priv file as expected");
		} else {
			base.failedStep("Native file is  copied to priv file ");
		}

		if (TiffFile.exists()) {
			base.passedStep("Tiff file is generated as expected");
		} else {
			base.failedStep("Tiff file is not generated as expected");
		}

		if (DatFile.exists()) {
			base.passedStep("Dat file is generated as expected");
		} else {
			base.failedStep("Dat file is not generated as expected");
		}

		if (NativeFile.exists()) {
			base.passedStep("Native file is generated as expected");
		} else {
			base.failedStep("Native file is not generated as expected");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-49079
	 * @DescriptionProduction Document Selection to DocList with Child Documents
	 * 
	 */
	@Test(description="RPMXCON-49079",enabled = true, groups = { "regression" })
	public void verifyingTheParentAndChildDocumentInDoclistPage() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49079 -Production Component");
		base.stepInfo("Production Document Selection to DocList with Child Documents");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.comments);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.selectingParentDocument();
		doc.bulkTagExistingFromDoclist(tagname);
		driver.waitForPageToBeReady();
		doc.getSelectDropDown().waitAndClick(10);
		driver.waitForPageToBeReady();
		String ParentDocId=doc.getParentDocumentDocId().getText();
		System.out.println(ParentDocId);
		doc.getSelectParentDocument().waitAndClick(10);
		
		
		int DocumentId = base.getIndex(doc.getChildHeader(), "DOCID");
		doc.getChildTableRow(DocumentId);
		System.out.println(DocumentId);
		ArrayList<String> DocumentIdInDoclist = doc.GettingChildDocumentInDocListPage(DocumentId);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.getIncludeFamilies().waitAndClick(10);
		page.navigatingToDocViewPage();
		doc.getSelectParentDocument().isElementAvailable(1);
		if (doc.getSelectDocument(ParentDocId).isDisplayed()) {
			driver.scrollingToBottomofAPage();
			doc.getSelectDocument(ParentDocId).waitAndClick(10);
			ArrayList<String> selectedDocs = doc.GettingChildDocumentInDocListPage(DocumentId);
			if (DocumentIdInDoclist.equals(selectedDocs)) {
				base.passedStep("Parent And Child document is displayed as expected");
			} else {
				base.failedStep("Parent and child document is not displayed as expected");
			}
		} else {
			base.failedStep("Parent and child document is not displayed as expected");
		}
		doc.getBackToSourceBtn().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		page.getMarkIncompleteButton().Click();
		page.getIncludeFamilies().waitAndClick(10);
		 page.navigatingToDocViewPage();
		base.waitTime(1);
	
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			doc.getSelectDocument(ParentDocId).waitAndClick(10);
			driver.waitForPageToBeReady();
			ArrayList<String> IncludeFamilyMembDocs = doc.GettingChildDocumentInDocListPage(DocumentId);
			if (DocumentIdInDoclist.equals(IncludeFamilyMembDocs)) {
				base.passedStep("Include family member doc is displayed as expected");
			} else {
				base.failedStep("Include family member doc is not displayed as expected");
			}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha.T created on:NA modified by:NA TESTCASE No:RPMXCON-48063
	 * @Description:To Verify placeholders of the docs of the selected file types
	 *                 are produced in Production.
	 */
	@Test(description="RPMXCON-48063",enabled = true, groups = { "regression" })
	public void verifyTheFileTypeInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case id : RPMXCON-48063-production component ");
		base.stepInfo("To Verify placeholders of the docs of the selected file types are produced in Production.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Text = Input.searchString2;
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);

		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithNativelyProducedDocs(Input.fileTypeInNativeDocs, Text);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		String DocPages = page.getDocPages().getText();
		String[] doccount = DocPages.split(" ");
		String DocumentCount = doccount[0];
		int DocCount = Integer.parseInt(DocumentCount);
		int lastFile = DocCount;
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();

		String home = System.getProperty("user.home");
		

		for(int i=firstFile; i<lastFile ;i++) {
		
		File imageFile = new File(home+"/Downloads/VOL0001/Images/0001/"+prefixID+i+suffixID+".tiff");
	     
	         ITesseract instance = new Tesseract1();
	         File tessDataFolder = LoadLibs.extractTessResources("tessdata");
	         instance.setDatapath(tessDataFolder.getPath());
	            String result = instance.doOCR(imageFile);
	            System.out.println(result);
	            if (result.contains(Text)) {
					base.passedStep(Text+" is displayed in "+prefixID+i+suffixID+".tiff"+" file as expected");
				} else {
					base.failedStep(Text+" verification failed");
				}
	       
		}

		for (int i = firstFile; i < lastFile; i++) {
			File Native = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + i + suffixID + ".doc");

			if (Native.exists()) {
				base.passedStep("Native file are generated correctly : " + prefixID + i + suffixID + ".doc");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
			}
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @authorBrundha created on:NA modified by:NA TESTCASE No:RPMXCON-49102
	 * @Description:To verify that In DAT file, Email metadata should not be
	 *                 displayed if document is redacted and privileged
	 */
	@Test(description="RPMXCON-49102",enabled = true, groups = { "regression" })
	public void verifyEndBatesInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case id : RPMXCON-49102 ");
		base.stepInfo(
				"To verify that In DAT file, Email metadata should not be displayed if document is redacted and privileged");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(testData1, Input.document);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.selectDoc1();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(1, "Email", "EmailAuthorNameAndAddress");
		page.selectingCheckboxInDatSection(1);
		page.addNewFieldOnDAT();
		page.addDatField(2, "Email", "EmailToNamesAndAddresses");
		page.selectingCheckboxInDatSection(2);
		page.addNewFieldOnDAT();
		page.addDatField(3, "Email", "EmailCCNamesAndAddresses");
		page.selectingCheckboxInDatSection(3);
		page.addNewFieldOnDAT();
		page.addDatField(4, "Email", "EmailBCCNamesAndAddresses");
		page.selectingCheckboxInDatSection(4);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.getIncludeFamilies().waitAndClick(10);
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
			base.passedStep("Dat file is displayed as expected");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}

		String valueString = "allen";
		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
		while ((line = brReader.readLine()) != null) {
			lines.add(line);
		}
		for (String a : lines) {
			System.out.println(a);
			String[] arrOfStr = a.split(" ");
			for (String text : arrOfStr) {
				if (!text.equalsIgnoreCase(valueString)) {
					base.passedStep("Dat file value is displayed as expected");
				} else {
					base.failedStep("Dat file value is not displayed as expected");
				}
			}
		}
		brReader.close();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

		loginPage.logout();
	}
	
	
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
	base = new BaseClass(driver);
	Reporter.setCurrentTestResult(result);
	if (ITestResult.FAILURE == result.getStatus()) {
	Utility baseClass = new Utility(driver);
	baseClass.screenShot(result);
	}
	loginPage.quitBrowser();
	}
	@AfterClass(alwaysRun = true)

	public void close() {
	System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");



	}
}

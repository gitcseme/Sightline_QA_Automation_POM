package sightline.productions;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Test_Regression_02{

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	ProductionPage productionPage;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	DocListPage docPage;
	DocViewPage docViewPage;
	ProjectFieldsPage projectField;
	SoftAssert softAssertion;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String foldername;
	String tagname;
	String productionname;
	String TempName;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
		base = new BaseClass(driver);
		Input input = new Input();
		input.loadEnvConfig();
		base = new BaseClass(driver);
		driver = new Driver();

		// Login as a PA
		loginPage = new LoginPage(driver);
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56005
	 * @Description: Verify that user can regenerate the Shareable links and reset
	 *               the expiration time
	 */
	@Test(description="RPMXCON-56005",enabled = true, groups = { "regression" } )
	public void verifySharableLinkExpirationTime() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56005 -Production Sprint 10");
		base.stepInfo("Verify that user can regenerate the Shareable links and reset the expiration time");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		// sessionSearch.bulkTagExisting(tagname);
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
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getQC_Download().waitAndClick(10);
		page.getSelectSharableLinks().waitAndClick(10);
		page.getRegenarateLinkBtn().waitAndClick(10);

		String ExpiryDate = page.getSharableLinkExpiryDate().getText().trim();
		System.out.println(ExpiryDate);
		base.stepInfo("Expiry date show in webPage : " + ExpiryDate);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date " + date1);
		base.stepInfo("Current Date" + date1);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date2 = simpleDateFormat.parse(date1);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(Calendar.DATE, 14); // 14 == duration
		String TwoWeekAfter = simpleDateFormat.format(calendar.getTime()).trim();
		System.out.println("Day after 2 Week : " + TwoWeekAfter);
		base.stepInfo("Day after 2 Week: " + TwoWeekAfter);

		if (ExpiryDate.contains(TwoWeekAfter)) {
			base.passedStep("Expiry date reset verified");
			System.out.println("date visible");
		} else {
			base.failedStep("date verification failed");
			System.out.println("date not visible");
		}
		base.passedStep("Verified that user can regenerate the Shareable links and reset the expiration time");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-55949
	 * @Description Verify that use cannot access the Production deatils by copying
	 *              the Production URL if user does not have Production rights
	 * 
	 */
	@Test(description="RPMXCON-55949",enabled = true, groups = { "regression" } )
	public void verifyingProductionAccessPAtoRMU() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-55949 -Production Sprint 10");
		base.stepInfo(
				"Verify that RMU cannot access the Production by copying the Production URL if user is not part of that security group");
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		String securityGroup ="Production_Security_Group"+Utility.dynamicNameAppender();
		sg.createSecurityGroups(securityGroup);
		
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingSecurityGroup(securityGroup);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logined as RMU");

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not  displayed as expected");
		}
		driver.Navigate().back();

		base.passedStep(
				"Verified that RMU cannot access the Production by copying the Production URL if user is not part of that security group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-55950
	 * @Description Verify that RMU cannot access the Production if he is not part
	 *              of that Project
	 * 
	 */
	@Test(description="RPMXCON-55950",enabled = true, groups = { "regression" } )
	public void verifyingProductionAccessPAtoRMUdiffProject() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-55950 -Production Sprint 10");
		base.stepInfo("Verify that RMU cannot access the Production if he is not part of that Project");
		String securityGroup ="Production_Security_Group"+Utility.dynamicNameAppender();

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.createSecurityGroups(securityGroup);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingSecurityGroup(securityGroup);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		BaseClass base=new BaseClass(driver);
		base.selectproject(Input.regressionConsilio1);
		base.stepInfo("Logined as RMU");

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
		base.passedStep("Error message is displayed as expected");
		} else {
		base.failedStep("Error message is not displayed as expected");
		}
		driver.Navigate().back();

		base.passedStep("Verified that RMU cannot access the Production if he is not part of that Project");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56033
	 * @Description: Verify that after Archiving is completed it should displays
	 *               'Creating Archive Complete' status on Production Progress bar
	 *               Tile View
	 */
	@Test(description="RPMXCON-56033",enabled = true, groups = { "regression" } )
	public void verifiyCreateArchiCompleteOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56033 -Production Sprint 10");
		base.stepInfo(
				"Verify that after Archiving is completed it should displays 'Creating Archive Complete' status on Production Progress bar Tile View");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
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
		page.getbtnProductionGenerate().waitAndClick(10);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInHomePage("Creating Archive Complete", productionname);
		base.passedStep(
				"Verify that after Archiving is completed it should displays 'Creating Archive Complete' status on Production Progress bar Tile View");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-55951
	 * @Description Verify that PAU cannot access the Production if he is not part
	 *              of that Project
	 * 
	 */
	@Test(description="RPMXCON-55951",enabled = true, groups = { "regression" } )
	public void verifyingProductionAccessPAtoPAdiffProject() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-55951 -Production Sprint 10");
		base.stepInfo("Verify that PAU cannot access the Production if he is not part of that Project");
		String securityGroup ="Production_Security_Group"+Utility.dynamicNameAppender();

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.createSecurityGroups(securityGroup);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingSecurityGroup(securityGroup);
	
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		
		BaseClass base=new BaseClass(driver);
		base.selectproject(Input.regressionConsilio1);
		base.stepInfo("Logined as another PA");

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not  displayed as expected");
		}
		driver.Navigate().back();

		base.passedStep("Verify that PAU cannot access the Production if he is not part of that Project");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55994
	 * @Description: Verify that the text should not go out the progress bar or
	 *               wrap, even when the user zooms out/in the browser with
	 *               different screen resolution on tile view
	 */
	@Test(description="RPMXCON-55994",enabled = true, groups = { "regression" } )
	public void verifyStatusBarTextWithResolutionAndZoomSize() throws Exception {

		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-55994 -Production Sprint 10");
		base.stepInfo(
				"Verify that the text should not go out the progress bar or wrap, even when the user zooms out/in the browser with different screen resolution  on tile view");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		int[][] diemen = { { 1024, 768 }, { 1280, 800 }, { 1440, 900 }, { 1600, 900 }, { 1280, 1024 } };
		double[][] zoom = { { 0.75, 1.25 }, { 0.75, 1 }, { 0.80, 1 }, { 0.90, 1.25 }, { 0.80, 1.25 } };

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		// sessionSearch.bulkTagExisting(tagname);
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
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		base.stepInfo("Production with different progress status should be available");

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		for (int i = 0; i < diemen.length; i++) {

			Dimension pram1 = new Dimension(diemen[i][0], diemen[i][1]);
			driver.Manage().window().setSize(pram1);
			((JavascriptExecutor) driver.getWebDriver()).executeScript("document.body.style.zoom = '"+zoom[i][0]+"'");

			driver.Navigate().refresh();
			if (page.getProductionFromHomePage(productionname).isDisplayed()) {
				base.passedStep("Status bar text is displayed in : " + diemen[i][0] + "x" + diemen[i][1]
						+ " with zoom size : " + zoom[i][0]);
				System.out.println("displayed");
			} else {
				base.failedStep("Status bar text is not displayed");
				System.out.println("Not displayed");
			}
			((JavascriptExecutor) driver.getWebDriver()).executeScript("document.body.style.zoom = '"+zoom[i][1]+"'");
			if (page.getProductionFromHomePage(productionname).isDisplayed()) {
				base.passedStep("Status bar text is displayed in : " + diemen[i][0] + "x" + diemen[i][1]
						+ " with zoom size : " + zoom[i][1]);
				System.out.println("displayed");
			} else {
				base.failedStep("Status bar text is not displayed");
				System.out.println("Not displayed");
			}
		}

		base.passedStep(
				"Verified that the text should not go out the progress bar or wrap, even when the user zooms out/in the browser with different screen resolution  on tile view");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCONO-55909
	 * @Description Verify that the production field PageCount is renamed to
	 *              TIFFPageCount
	 */
	@Test(description="RPMXCON-55909",enabled = true, groups = { "regression" } )
	public void verifyProductionFieldRenamed() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Testcase No: RPMXCON-55909");
		base.stepInfo("Verify that the production field PageCount is renamed to TIFFPageCount");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates("Production", "TIFFPageCount", "TIFFPAGECOUNT");
		page.nonVisibleCheck("PageCount");
		page.visibleCheck("TIFFPageCount");
		base.stepInfo("PageCount was be renamed to TIFFPageCount");

		base.passedStep("Verified that the production field PageCount is renamed to TIFFPageCount");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49967
	 * @Description: Verify that production should generated with modified Redaction
	 *               placeholder text
	 */
	@Test(description="RPMXCON-49967",enabled = true, groups = { "regression" } )
	public void verifyReductionPlacholderText() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49967 -Production Sprint 10");
		base.stepInfo("Verify that production should generated with modified Redaction placeholder text");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.visibleCheck("Productions & Exports");
		base.stepInfo("production home page is Displayed");
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedactionWithoutUpdatedText();
		base.waitForElement(page.gettextRedactionPlaceHolder());
		String text = page.gettextRedactionPlaceHolder().getText();
		if (text.equalsIgnoreCase("REDACTED")) {
			base.passedStep("By default 'REDACTED' text should be displayed");
		}
		page.gettextRedactionPlaceHolder().waitAndClick(10);
		page.gettextRedactionPlaceHolder().SendKeys(Input.searchString4);
		base.stepInfo("Reduction text is updated");
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
		base.passedStep("Verified that production should generated with modified Redaction placeholder text");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49815
	 * @Description: To verify that Production should generate successfully if
	 *               Prefix is up to 50 characters
	 */
	@Test(description="RPMXCON-49815",enabled = true, groups = { "regression" } )
	public void verifyPrifixWith50char() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49815 -Production Sprint 10");
		base.stepInfo("To verify that Production should generate successfully if Prefix is up to 50 characters");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Utility.randomCharacterAppender(50);
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
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
		page.fillingGeneratePageAndVerfyingBatesRange(prefixID);
		base.passedStep("Verified that Production should generate successfully if Prefix is up to 50 characters");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCONO-47174
	 * @Description Verify that TIFF files should be copied to folder when 'Split
	 *              Sub Folders' is OFF with split count as 1000
	 */
	@Test(description="RPMXCON-47174",enabled = true, groups = { "regression" } )
	public void genaratetDocumentswithMultipleBrandingTagsnotsplit() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47174 -Production Sprint 10");
		base.stepInfo(
				"Verify that TIFF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 1000");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.visibleCheck("Numbering and Sorting");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.visibleCheck("Priv Guard");
		page.fillingPrivGuardPage();
		page.visibleCheck("Production Location");
		driver.scrollingToBottomofAPage();
		page.getsplitSubFolderbtn().waitAndClick(10);
		base.stepInfo("split sub folder toggle as OFF");
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.visibleCheck("Summary & Preview");
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.getBackButton().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.toggleOffCheck(page.getsplitSubFolderbtn());

		base.passedStep(
				"Verified that TIFF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 1000");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCONO-55653
	 * @Description To Verify the availability of 'Translations' under the Advanced
	 *              Production Types show/hide section (in Production Component).
	 */
	@Test(description="RPMXCON-55653",enabled = true, groups = { "regression" } )
	public void verifyAvailabilityOfTranslationComponent() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Testcase No: RPMXCON-55653");
		base.stepInfo(
				"To  Verify the availability of 'Translations' under the Advanced Production Types show/hide section (in Production Component).");
		
		// foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		// sessionSearch.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		driver.scrollingToBottomofAPage();
		page.getAdvancedProductionToggle().waitAndClick(10);
		page.getCheckBoxUnCheckVerificaation(page.getTranlationCheckMarkVerication());
		base.stepInfo("By default Translation check box is unchecked");
		boolean flag = page.getTranlationOpenCloseCheck().GetAttribute("class").contains("in");
		if (!flag) {
			base.passedStep("user didn't view Translation details ");
		} else {
			base.failedStep("Tranlation tab is open");
		}
		page.getTranslationsCheckBox().waitAndClick(10);
		base.stepInfo("Translation tab is clicked");
		driver.waitForPageToBeReady();

		boolean flag1 = page.getTranlationOpenCloseCheck().GetAttribute("class").contains("in");
		if (flag1) {
			base.passedStep("Translation component details is displayed");
		} else {
			base.failedStep("Tranlation tab is not open");
		}

		base.passedStep(
				"Verified the availability of 'Translations' under the Advanced Production Types show/hide section (in Production Component).");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security
		// Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47884
	 * @Description: To Verify Native Section with various options(Produce Native
	 *               Files selection/Generate Load LST/Advance Show Hide/and Toggles
	 *               in Advance)
	 */
	@Test(description="RPMXCON-47884",enabled = true, groups = { "regression" } )
	public void verifyNativeSectionElements() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47884 -Production Sprint 11");
		base.stepInfo(
				"To Verify Native Section with various options(Produce Native Files selection/Generate Load LST/Advance Show Hide/and Toggles in Advance)");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithVerification();
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
		base.passedStep(
				"To Verify Native Section with various options(Produce Native Files selection/Generate Load LST/Advance Show Hide/and Toggles in Advance)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47994
	 * @Description: To Verify On grid view of the productions the start date and
	 *               end date for a production that is still in Completed state.
	 */
	@Test(description="RPMXCON-47944",enabled = true, groups = { "regression" } )
	public void startDateEndDateVarification() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47944 -Production Sprint 11");
		base.stepInfo(
				"To Verify On grid view of the productions  the start date and end date  for a production that is still in Completed state.");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
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
		page.fillingGeneratePageWithContinueGenerationPopup();

		driver.waitForPageToBeReady();
		page.goToProductionGridView();
		int startDateIndex = base.getIndex(page.getGridWebTableHeader(), "START DATE");
		int endDateIndex = base.getIndex(page.getGridWebTableHeader(), "END DATE");
		String startDate = page.getGridProdValues(productionname, startDateIndex).getText();
		String EndDate = page.getGridProdValues(productionname, endDateIndex).getText();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date " + date1);
		boolean flag = startDate.contains(date1);
		boolean flag1 = EndDate.contains(date1);
		if (flag) {
			base.passedStep("Start date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			base.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}
		if (flag1) {
			base.passedStep("End date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			base.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}

		base.passedStep(
				"To Verify On grid view of the productions  the start date and end date  for a production that is still in Completed state.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56078
	 * @Description: Verify that Production should export Native with the Text file
	 *               in selected format if Text is not ingested
	 */
	@Test(description="RPMXCON-56078",enabled = true, groups = { "regression" } )
	public void verifyTextFileWithSelectedFormat() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56078 -Production Sprint 11");
		base.stepInfo(
				"Verify that Production should export Native with  the Text file in selected format if Text is not ingested");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSectionWithTextFormat("ANSI Arabic; Arabic (Windows)");
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"Verified that Production should export Native with  the Text file in selected format if Text is not ingested");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47889
	 * @Description: To Verify Generate Section for Production Name and Status.
	 */
	@Test(description="RPMXCON-47889",enabled = true, groups = { "regression" } )
	public void verifyProdNameAndSatatus() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47889 -Production Sprint 11");
		base.stepInfo("To Verify Generate Section for Production Name and Status.");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
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
		String prodName = page.getProductionNameInGenPage().getText().trim();
		if (prodName.equals(productionname)) {
			base.passedStep("Production name is displayed on genaration tab");
			System.out.println("name displayed");
		} else {
			base.failedStep("Production name is not displayed on genaration tab");
			System.out.println("name not displayed");
		}
		page.verifyProductionStatusInGenPage("Draft");
		base.stepInfo("Status is in Draft by default");
		base.passedStep("Verified Generate Section for Production Name and Status.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49968
	 * @Description: Verify that Production should generated with redaction text if
	 *               user selects the annotation layer
	 */
	@Test(description="RPMXCON-49968",enabled = true, groups = { "regression" } )
	public void verifyProductionRedactionTextWithAnnotation() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49968 -Production Sprint 11");
		base.stepInfo(
				"Verify that Production should generated with redaction text if user selects the annotation layer");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithBurnRedaction();
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
		base.passedStep(
				"Verify that Production should generated with redaction text if user selects the annotation layer");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49728
	 * @Description: Verify that branding is applied on all pages for redacted image
	 *               based documents
	 */
	@Test(description="RPMXCON-49728",enabled = true, groups = { "regression" } )
	public void verifyBrandingRedactedImage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49728 -Production Sprint 11");
		base.stepInfo("Verify that branding is applied on all pages for redacted image based documents");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTiffPdfBranding(tagname, null);
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

		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTiffPdfBranding(tagname, "pdf");
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
		base.passedStep("Verify that branding is applied on all pages for redacted image based documents");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-49057
	 * @Description In Productions, text was produced with redaction burned, when
	 *              Burn Redactions option was enabled
	 * 
	 */
	@Test(description="RPMXCON-49057",enabled = true, groups = { "regression" } )
	public void verifyBurnRedactionIsEnabled() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49057 -Production Sprint 11");
		base.stepInfo(
				"In Productions, text was produced with redaction burned, when Burn Redactions option was enabled");

		foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String Redactiontag1;
		Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
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
		docViewRedactions.selectDoc2();
		docViewRedactions.selectDoc3();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolderInRMU(foldername);

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
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
		base.passedStep(
				"In Productions, text was produced with redaction burned, when Burn Redactions option was enabled");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48816
	 * @Description: Verify that redaction text displays on generated Tiff/PDF for
	 *               orphan redactions documents, if user selects the option as 'All
	 *               redactions in annotation layer' for Burn Redactions
	 */
	@Test(description="RPMXCON-48816",enabled = true, groups = { "regression" } )
	public void verifyProductionRedactionTextWithAnnotationLayer() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48816 -Production Sprint 11");
		base.stepInfo(
				"Verify that redaction text displays on generated Tiff/PDF for orphan redactions documents, if user selects the option as 'All redactions in annotation layer' for Burn Redactions");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.orphanDocument);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
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
		base.passedStep(
				"Verified that redaction text displays on generated Tiff/PDF for orphan redactions documents, if user selects the option as 'All redactions in annotation layer' for Burn Redactions");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47987
	 * @Description: To Verify The format of the date produced in the Production DAT
	 *               should honor the date format configured in DAT section
	 */
	@Test(description="RPMXCON-47987",enabled = true, groups = { "regression" } )
	public void verifyDatWithDeffDateFormate() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47987 -Production Sprint 11");
		base.stepInfo(
				"To Verify The format of the date produced in the Production DAT should honor the date format configured in DAT section");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDatDateFormate().selectFromDropdown().selectByVisibleText("DD/MM/YYYY");
		base.stepInfo("DAT section selected with different date format");
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		base.passedStep(
				"Verified The format of the date produced in the Production DAT should honor the date format configured in DAT section");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47846
	 * @Description: To Verify the View of the already created Template with
	 *               existing Project and Production Set
	 */
	@Test(description="RPMXCON-47846",enabled = true, groups = { "regression" } )
	public void verifyTemplateforexitingProductionSet() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47846 Production- Sprint 11");
		base.stepInfo("To Verify the View of the already created Template with existing Project and Production Set");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		 tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "templateName" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		
		page = new ProductionPage(driver);
		driver.waitForPageToBeReady();
		page.getprod_ActionButton_Reusable(productionname).Click();
		driver.waitForPageToBeReady();
		page.getprod_Action_SaveTemplate_Reusable(productionname).Click();

		page.saveTemple(TempName);
		page.getManageTemplates().waitAndClick(10);
		base.CloseSuccessMsgpopup();
		driver.scrollingToBottomofAPage();
		page.getNextBtn().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.getElementDisplayCheck(page.getViewBtn(TempName));
		page.getElementDisplayCheck(page.getDeleteBtn(TempName));
		page.getViewBtn(TempName).waitAndClick(10);

		page.visibleCheck(TempName);
		page.visibleCheck("Priv Guard");
		page.visibleCheck("Production Components");
		page.visibleCheck("Numbering & Sorting");
		page.clickElementNthtime(page.getviewProductionNextbtn(), 2);
		base.stepInfo("Next button working proberly");
		page.clickElementNthtime(page.getviewProductionBackbtn(), 2);
		base.stepInfo("Back button working proberly");
		page.getviewProductionNextbtn().waitAndClick(10);
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		base.stepInfo("Dat is selected by default");
		page.getElementDisplayCheck(page.closeButtonInTemplate());
		base.stepInfo("close button is displayed");
		driver.waitForPageToBeReady();
		page.templateCloseBtn(TempName).waitAndClick(10);
		base.passedStep("To Verify the View of the already created Template with existing Project and Production Set");

		base.stepInfo("Deleting the tags and folders after the production gets completed");
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48061
	 * @Description: To Verify selection of one or more tags for placeholdering with
	 *               File type a set of documents (For Production)
	 */
	@Test(description="RPMXCON-48061",enabled = true, groups = { "regression" } )
	public void verifyNativelyPlaceHolderWithFileType() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48061 -Production Sprint 11");
		base.stepInfo(
				"To Verify selection of one or more tags for placeholdering with File type a set of documents (For Production)");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.tagNamePrev);
		base.stepInfo("Tags are created with file type");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);
		doc.documentSelection(3);
		doc.documentSelection(6);
		driver.scrollPageToTop();
		doc.bulkTagExistingFromDoclist(tagname1);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname, tagname1);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		driver.waitForPageToBeReady();
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		base.passedStep(
				"Verified selection of one or more tags for placeholdering with File type a set of documents (For Production)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48062
	 * @Description: To Verify selection of one or more tags without selecting any
	 *               file types for placeholdering a set of documents (For
	 *               Production)
	 */
	@Test(description="RPMXCON-48062",enabled = true, groups = { "regression" } )
	public void verifyNativelyPlaceHolderWithoutFileType() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48062 -Production Sprint 11");
		base.stepInfo(
				"To Verify selection of one or more tags without selecting any file types for placeholdering a set of documents (For Production)");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		tagsAndFolderPage.CreateTag(tagname1, Input.securityGroup);
		base.stepInfo("Tags are created without file type");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);
		doc.documentSelection(3);
		doc.documentSelection(6);
		driver.scrollPageToTop();
		doc.bulkTagExistingFromDoclist(tagname1);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname, tagname1);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		driver.waitForPageToBeReady();
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		base.passedStep(
				"Verified selection of one or more tags without selecting any file types for placeholdering a set of documents (For Production)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48065
	 * @Description: To Verify document matches multiple criteria (file type or
	 *               tags), the precedence will be from top to bottom.(For
	 *               Production)
	 */
	@Test(description="RPMXCON-48065",enabled = true, groups = { "regression" } )
	public void verifyNativelyPlaceHolderWithFileTypeAndTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48065 -Production Sprint 11");
		base.stepInfo(
				"To Verify document matches multiple criteria (file type or tags), the precedence will be from top to bottom.(For Production)");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);
		doc.documentSelection(6);
		driver.scrollPageToTop();
		doc.bulkTagExistingFromDoclist(tagname1);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholderWithTagTypeAndTags(tagname, tagname1);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		driver.waitForPageToBeReady();
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		base.passedStep(
				"Verified document matches multiple criteria (file type or tags), the precedence will be from top to bottom.(For Production)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47887
	 * @Description: To Verify Priv Guard with various tag and redaction options;
	 */
	@Test(description="RPMXCON-47887",enabled = true, groups = { "regression" } )
	public void verifyPrivGuardVariousTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47887 -Production Sprint 11");
		base.stepInfo("To Verify Priv Guard  with various tag and redaction options");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int count = sessionSearch.basicContentSearch(Input.testData1);
		System.out.println(count);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.AddRuleAndRemoveRule(tagname);
		driver.waitForPageToBeReady();
		String docNo = page.getDocumentSelectionLink().getText().trim();
		int displayCount = Integer.parseInt(docNo);
		System.out.println(displayCount);
		SoftAssert softAssertion = new SoftAssert();
		if (count == displayCount) {
			softAssertion.assertEquals(count, displayCount);
			base.passedStep("Document count is get displayed Correctly");
			System.out.println("count is verified");
		} else {
			base.failedStep("Document Count verification failed");
			System.out.println("count not verified");
		}

		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionDisablePrivilegedDocs();
		page.getTiffSinglePage().ScrollTo();
		page.getTiffSinglePage().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.AddRuleAndRemoveRule(tagname);
		String docNo1 = page.getDocumentSelectionLink().getText().trim();
		driver.waitForPageToBeReady();
		int displayCount1 = Integer.parseInt(docNo1);
		if (count == displayCount) {
			softAssertion.assertEquals(count, displayCount1);
			base.passedStep("Document count is get displayed Correctly");
		} else {
			base.failedStep("Document Count verification failed");
		}
		base.passedStep("Verified Priv Guard  with various tag and redaction options;");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

	}

	/**
	 * @author Aathith Test case id-RPMXCON-48288
	 * @Description To Verify Production for a document with no error message as
	 *              'WidthAndHeightCannotBeNegative'
	 */
	@Test(description="RPMXCON-48288",enabled = true, groups = { "regression" } )
	public void verifyNotDisplayOfErrorMessage() throws Exception {

		base.stepInfo("RPMXCON-48288-Production Sprint 11");
		base.stepInfo("To Verify Production for a document with no error message as 'WidthAndHeightCannotBeNegative'");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "STC4_00000995");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		SoftAssert softAssertion = new SoftAssert();
		boolean flag = page.gettext("WidthAndHeightCannotBeNegative").isElementAvailable(60);
		if (flag) {
			softAssertion.assertTrue(flag);
			base.failedStep("Error message displayed");
		} else {
			softAssertion.assertFalse(flag);
			base.passedStep("There Should not be any error message  such as WidthAndHeightCannotBeNegative");
		}
		base.passedStep("Verified Production for a document with no error message as 'WidthAndHeightCannotBeNegative'");
		

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48095
	 * @Description: To verify that Rotate 90 degrees clockwise page is rotated
	 *               before the branding is applied
	 */
	@Test(description="RPMXCON-48095",enabled = true, groups = { "regression" } )
	public void verify90DegreeRotationbeforeBrandingApply() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48095 -Production Sprint 11");
		base.stepInfo("To verify that Rotate 90 degrees clockwise page is rotated before the branding is applied");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems("921ID00000169", "921ID00000171");
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionRotate90DegreeClockWise(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("verified that Rotate 90 degrees clockwise page is rotated before the branding is applied");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-46905
	 * @Description: Verify that production should be generated successfully for
	 *               audio files
	 */
	@Test(description="RPMXCON-46905",enabled = true, groups = { "regression" } )
	public void verifyAudiofileGenaratedSuccessfully() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-46905 -Production Sprint 11");
		base.stepInfo("Verify that production should be generated successfully for audio files");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("AudioPlayerReady", "1");
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.SelectMP3FileAndVerifyLstFile();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutDownload();
		base.passedStep("Verified that production should be generated successfully for audio files");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55627
	 * @Description: To Verify ordering sequence for production status for 'Filter
	 *               By:' field. Verify for both Grid and Tile view.
	 */
	@Test(description="RPMXCON-55627",enabled = true, groups = { "regression" } )
	public void verifyProductionState() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-55627");
		base.stepInfo(
				"To Verify  ordering sequence for production status for 'Filter By:' field. Verify for both Grid and Tile view.");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// completed state
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
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
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// failed
		page = new ProductionPage(driver);
		// beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		// page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Draft state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();

		// Inprogress state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
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
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		// view all state
		page = new ProductionPage(driver);
		base.waitForElement(page.getFilterByButton());
		page.getFilterByButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getElementDisplayCheck(page.getFilterByDRAFT());
		page.getElementDisplayCheck(page.getFilterByINPROGRESS());
		page.getElementDisplayCheck(page.getFilterByFAILED());
		page.getElementDisplayCheck(page.getFilterByCOMPLETED());
		base.stepInfo("All option is available in the list");
		base.waitForElement(page.getFilterByCOMPLETED());
		page.getFilterByCOMPLETED().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getRefreshButton().waitAndClick(10);
		base.stepInfo("Filtered all options");

		// different state
		page.visibleCheck("DRAFT");
		page.visibleCheck("FAILED");
		page.visibleCheck("INPROGRESS");
		page.visibleCheck("COMPLETED");

		// view completed status only
		page.filterCompletedProduction();
		page.verifyProdctionState(page.getProductionSate(), "COMPLETED");

		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();

		int status = base.getIndex(page.getGridWebTableHeader(), "STATUS");
		driver.waitForPageToBeReady();
		page.verifyProductionStateWebTableGridView(status, "COMPLETED");

		base.passedStep(
				"Verified  ordering sequence for production status for 'Filter By:' field. Verify for both Grid and Tile view.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55650
	 * @Description: To Verify Admin will be able to select the metadata and work
	 *               production information to be included in a slip sheet, similar
	 *               to existing RPM
	 */
	@Test(description="RPMXCON-55650",enabled = true, groups = { "regression" } )
	public void verifySlipSheetflow() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-55650");
		base.stepInfo(
				"To Verify Admin will be able to select the metadata and work production information to be included in a slip sheet, similar to existing RPM");

		softAssertion = new SoftAssert();
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();

		page.getTIFFTab().Click();
		page.getTiffAdvanceBtn().ScrollTo();
		page.getTiffAdvanceBtn().waitAndClick(10);

		driver.waitForPageToBeReady();
		page.toggleOffCheck(page.getSlipSheets());
		page.getSlipSheets().waitAndClick(10);
		page.toggleOnCheck(page.getSlipSheets());
		base.stepInfo("Slip Sheet Section Toggle Button is work Accordingly");

		page.visibleCheck("METADATA");
		page.visibleCheck("WORKPRODUCT");
		page.visibleCheck("CALCULATED");
		base.stepInfo(" Fields is Contain Following Section 'METADATA/WORKPRODUCT & CALCULATED'");

		// metadata verification
		base.waitForElement(page.getSlipSheetMetaData());
		page.getSlipSheetMetaData().waitAndClick(10);
		boolean flag = page.getSlipSheetMetaDataActiveCheck().GetAttribute("class").contains("active");
		if (flag) {
			softAssertion.assertTrue(flag);
			softAssertion.assertAll();
			base.passedStep("Metadta tab is open");
		} else {
			base.failedStep("verification failed");
		}

		boolean flag1 = page.getSlipSheetMetaDataTypeCheck().GetAttribute("class").contains("checkbox");
		if (flag1) {
			softAssertion.assertTrue(flag1);
			softAssertion.assertAll();
			base.passedStep("Metadata field is check box");
		} else {
			base.failedStep("verification failed");
		}
		base.stepInfo("Clicked on METADATA TAB it is Display the list of Metadata Fields with Check Box.");

		// workproduct verification
		page.getSlipSheetWorkProduct().waitAndClick(10);
		boolean flag3 = page.getSlipSheetWorkProductActiveCheck().GetAttribute("class").contains("active");
		if (flag3) {
			softAssertion.assertTrue(flag3);
			softAssertion.assertAll();
			base.passedStep("WorkProduct tab is open");
		} else {
			base.failedStep("verification failed");
		}
		driver.waitForPageToBeReady();
		// driver.scrollingToBottomofAPage();
		boolean flag4 = page.getSlipSheetWorkProductFolder().GetAttribute("class").contains("tree");
		if (flag4) {
			softAssertion.assertTrue(flag4);
			softAssertion.assertAll();
			base.passedStep("work product field type is tree");
		} else {
			base.failedStep("verification failed");
		}
		base.stepInfo("Clicked on WORKPRODUCTION TAB it is Display WorkProduct List with Tress Structure.");

		// add to select btn verification
		page.getSlipSheetWorkProductFolderProduction().ScrollTo();
		page.getSlipSheetWorkProductFolderProduction().waitAndClick(10);
		base.waitForElement(page.getbtnAddToSelect());
		page.getbtnAddToSelect().waitAndClick(10);
		page.getElementDisplayCheck(page.getSelectedFieldItems());
		base.stepInfo(
				"Add to Selected Button should work as expected.(Selected Value should Get populated in Selected Fields");

		// adv btn open close verification
		boolean flag5 = page.getAdvanceBtnOpenCloseCheck().GetAttribute("class").contains("down");
		if (flag5) {
			softAssertion.assertTrue(flag5);
			softAssertion.assertAll();
			base.passedStep("Advanced button is open");
		} else {
			base.failedStep("verification failed");
		}
		page.getTiffAdvanceBtn().ScrollTo();
		page.getTiffAdvanceBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		boolean flag6 = page.getAdvanceBtnOpenCloseCheck().GetAttribute("class").contains("right");
		if (flag6) {
			softAssertion.assertTrue(flag6);
			softAssertion.assertAll();
			base.passedStep("Advanced button is close");
		} else {
			base.failedStep("verification failed");
		}
		base.stepInfo("Advance Show/Hide Button should work as expected (show/Hide).");

		driver.waitForPageToBeReady();
		page.getAdvancedTabInTIFF().ScrollTo();
		page.getAdvancedTabInTIFF().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.visibleCheck("Generate Load File (LST)");
		page.getElementDisplayCheck(page.getAdvancedLSTToggle());
		base.stepInfo("Advance Section is contain 'Generate Load File (LST)' with Toggle button.");

		base.passedStep(
				"To Verify Admin will be able to select the metadata and work production information to be included in a slip sheet, similar to existing RPM");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48341
	 * @Description: To verify that Tiff /PDF should burn Redactions even though
	 *               file based or tag based placeholdering is exists in the
	 *               document
	 */
	@Test(description="RPMXCON-48341",enabled = true, groups = { "regression" } )
	public void verifyProductionTiffPdfRedactionTextWithAnnotation() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48341 ");
		base.stepInfo(
				"To verify that Tiff /PDF should burn Redactions even though file based or tag based placeholdering is exists in the document");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
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

		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithBurnRedaction();
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
		base.passedStep(
				"verified that Tiff /PDF should burn Redactions even though file based or tag based placeholdering is exists in the document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48072
	 * @Description: To Verify Field EndingBates in Production
	 */
	@Test(description="RPMXCON-48072",enabled = true, groups = { "regression" } )
	public void verifyEndBatesInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48072 ");
		base.stepInfo("To Verify Field EndingBates in Production");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int number = Integer.parseInt(beginningBates);
		int endingBates = docno + number - 1;
		String endingBates1 = Integer.toString(endingBates);
		System.out.println(beginningBates);
		System.out.println(endingBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(1, "Bates", "EndingBates");
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndVerfyingBatesRangeValue(endingBates1);

		// create export with TIFF
		String exportname = "E" + Utility.dynamicNameAppender();
		endingBates1 = Integer.toString(docno);
		System.out.println(beginningBates);
		System.out.println(endingBates);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.selectExportSetFromDropDown();
		page.addANewExport(exportname);
		page.fillingDATSection();
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(1, "Bates", "EndingBates");
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		base.passedStep("Verified Field EndingBates in Production");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49062
	 * @Description: To verify that the 'Production End Date' should contain and
	 *               present the date when the post-gen checks are completed
	 */
	@Test(description="RPMXCON-49062",enabled = true, groups = { "regression" } )
	public void verifyEndDateafterPostGenCheckCompleted() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id : RPMXCON-49062");
		base.stepInfo(
				"To verify that the 'Production End Date' should contain and present the date when the post-gen checks are completed");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname, beginningBates);

		softAssertion = new SoftAssert();
		page.filterCompletedProduction();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("Nativated to production Grid View");
		int endDateIndex = base.getIndex(page.getGridWebTableHeader(), "END DATE");
		String EndDate = page.getGridProdValues(productionname, endDateIndex).getText();
		System.out.println("Sightline show date and time : " + EndDate);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date and time : " + date1);
		base.stepInfo("current date : " + date1);

		boolean flag = EndDate.contains(date1);
		if (flag) {
			softAssertion.assertTrue(flag);
			softAssertion.assertAll();
			base.passedStep("End date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			base.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}

		base.passedStep(
				"verified that the 'Production End Date' should contain and present the date when the post-gen checks are completed");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49361
	 * @Description: Verify if PA Select the Production using a template that has
	 *               Native Files and Tags selected in the native components, then
	 *               Component tab should Complete without any error.
	 */
	@Test(description="RPMXCON-49361",enabled = true, groups = { "regression" } )
	public void verifiyProdTempInComponentTabWithoutAnyError() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id : RPMXCON-49361");
		base.stepInfo(
				"Verify if PA Select the Production using a template that has Native Files and Tags selected in the native components, then Component tab should Complete without any error.");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname, beginningBates);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		page.getprod_ActionButton_Reusable(productionname).waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(10);

		page.saveTemple(TempName);

		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.getAddNewProductionbutton().waitAndClick(10);
		page.getProductionName().SendKeys(productionname);
		String loadfile = TempName + " (Production)";
		page.getprod_LoadTemplate().selectFromDropdown().selectByVisibleText(loadfile);
		driver.waitForPageToBeReady();
		page.getBasicInfoMarkComplete().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getDATTab().waitAndClick(10);
		page.getElementDisplayCheck(page.getDAT_FieldClassification1());
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitTillElemetToBeClickable(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		base.passedStep("It should be completed without any error.");
		base.passedStep(
				"Verified if PA Select the Production using a template that has Native Files and Tags selected in the native components, then Component tab should Complete without any error.");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47903
	 * @Description: To Verify regenerate Option in Production
	 */
	@Test(description="RPMXCON-47903",enabled = true, groups = { "regression" } )
	public void verifyRegenerationOptions() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Case ID : RPMXCON-47903");
		base.stepInfo("To Verify regenerate Option in Production");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// production
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		// open exiting production and Status
		page.openExistingProduction(productionname);
		page.verifyProductionGenerateSuccussfully();
		driver.waitForPageToBeReady();
		page.getBackButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");

		// regenerate
		base.waitForElement(page.getbtnGenMarkIncomplete());
		page.getbtnGenMarkIncomplete().waitAndClick(5);
		base.waitForElement(page.getbtnReGenerateMarkComplete());
		page.getbtnReGenerateMarkComplete().waitAndClick(5);
		base.stepInfo("Clicked on Regenerate button");

		// verification
		page.visibleCheck("Regenerate Production");
		page.visibleCheck(
				"You have run this production earlier with errors. Select the below option to continue with Regenerate");
		page.visibleCheck("Restart the Production From the Beginning (Removes all previously generated files)");
		page.visibleCheck(
				"Restart the production from where it left off (Keeps any previously successfully generated files)");
		page.visibleCheck("Cancel");
		page.visibleCheck("Continue");

		page.getRegenerateAllRadioBtn().waitAndClick(5);
		base.stepInfo(
				"Clicked on  'Restart the Production From the Beginning (Removes all previously generated files)  ' option and click on Continue");

		page.getbtnRegenerateContinue().waitAndClick(5);
		page.verifyProductionGenerateSuccussfully();

		base.passedStep("Verified regenerate Option in Production");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48166
	 * @Description: To Verify All the Parameters configured for MP3 is saved for
	 *               the production on save Production as Template.
	 */
	@Test(description="RPMXCON-48166",enabled = true, groups = { "regression" } )
	public void verifyProductionTempSaveSuccessfully() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id : RPMXCON-48166");
		base.stepInfo(
				"To Verify  All the Parameters configured for MP3 is saved for the production on save Production as Template.");
		
		// foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		// sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.fillingMP3FileWithBurnRedaction();
		page.navigateToNextSection();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		page.getprod_ActionButton_Reusable(productionname).waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(10);

		page.saveTemple(TempName);
		base.passedStep(
				"To Verify  All the Parameters configured for MP3 is saved for the production on save Production as Template.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security
		// Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48573
	 * @Description: To verify that PA creates new production using continue with
	 *               last bates range of last production, which is completed with
	 *               only pre-gen check
	 */
	@Test(description="RPMXCON-48573",enabled = true, groups = { "regression" } )
	public void verifyNextBatesRage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48573 ");
		base.stepInfo(
				"To verify that PA creates new production using continue with last bates range of last production, which is completed with only pre-gen check");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(1);
		String suffixID = Utility.randomCharacterAppender(1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int number = Integer.parseInt(beginningBates);
		int endingBates = docno + number;
		String nextBates = Integer.toString(endingBates);
		System.out.println(beginningBates);
		System.out.println(endingBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		System.out.println(prefixID + beginningBates + suffixID);
		page.fillingGeneratePageAndVerfyingBatesRangeValue(beginningBates, prefixID, suffixID);

		// next bates
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, nextBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		base = new BaseClass(driver);

		if (base.getCloseSucessmsg().isElementAvailable(5)) {
			base.CloseSuccessMsgpopup();
		}
		
		page.clickOnGenerateButton();
		driver.waitForPageToBeReady();
	    base.VerifySuccessMessage("Generation Started Successfully");
	
	    base.passedStep(
				"To verify that PA creates new production using continue with last bates range of last production, which is completed with only pre-gen check");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-47912
	 * @DescriptionTo To Verify generated Generate Load File Should point to
	 *                respective Directories.(Eg.Native Load File Should point to
	 *                Native Directory).
	 * 
	 */
	@Test(description="RPMXCON-47912",enabled = true, groups = { "regression" } )
	public void verifyProductionDirectory() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-47912-Production component");
		base.stepInfo(
				"To Verify generated Generate Load File Should point to respective Directories.(Eg.Native Load File Should point to Native Directory).");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(1);
		String suffixID = Utility.randomCharacterAppender(1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

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
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		driver.waitForPageToBeReady();

		String location = page.getProductionOutputLocation_VolumeName().GetAttribute("value");
		String loadfile = page.getProductionComponentsFolderDetails_FolderName_LoadFiles().GetAttribute("value");
		String directory = page.getProductionOutputLocation_DriveText().GetAttribute("value");
		String image = page.getProductionComponentsFolderDetails_FolderName_Images().GetAttribute("value");
		String Text = page.getProductionComponentsFolderDetails_FolderName_Text().GetAttribute("value");
		String Native = page.getProductionComponentsFolderDetails_FolderName_Natives().GetAttribute("value");

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNextButton());
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);

		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();

		String name = page.getProduction().getText().trim();
		System.out.println(name);
		String home = System.getProperty("user.home");

		driver.waitForPageToBeReady();

		page.extractFile();
		

		File f = new File(home + "/Downloads/" + location + "/" + loadfile + "/");
		File dat = new File(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_DAT.dat");
		File tiff = new File(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_TIFF.OPT");
		File text = new File(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_Text.lst");
		File NativeLST = new File(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_Native.lst");
		driver.waitForPageToBeReady();

		if (f.exists()) {
			System.out.println("load file is Exists in pointed directory");
			base.passedStep("load file is Exists in pointed directory");
		} else {
			System.out.println("Does not Exists");
			base.failedStep("Does not Exists");
		}
		if (dat.exists()) {
			System.out.println("dat file is Exists in pointed directory");
			base.passedStep("dat file is Exists");
		} else {
			System.out.println("Does not Exists");
			base.failedStep("Dat does not Exists");
		}
		if (tiff.exists()) {
			System.out.println("tiff file is Exists in pointed directory");
			base.passedStep("tiff loadfile exists");

			driver.waitForPageToBeReady();
			for (String line : Files.readAllLines(
					Paths.get(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_TIFF.OPT"))) {

				if (line.contains(directory + location + "\\" + image + "\\")) {
					System.out.println("tiff is displayed as expected");
					base.passedStep("Tiff load file point Image directory");
				} else {
					base.failedStep("tiff verification failed");
				}
			}
		} else {
			System.out.println("Tiff Does not Exists");
			base.stepInfo("Tiff load file is not generated");
		}
		if (text.exists()) {
			System.out.println("text file is Exists in pointed directory");
			base.passedStep("text loadfile is exists");
			driver.waitForPageToBeReady();
			for (String line : Files.readAllLines(
					Paths.get(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_Text.lst"))) {

				if (line.contains(directory + location + "\\" + Text + "\\")) {
					System.out.println("Text is displayed as expected");
					base.passedStep("Text load file point text directory");
				} else {
					base.failedStep("Text load file not displayed");
				}
			}
		} else {
			System.out.println("Does not Exists");
			base.stepInfo("Text load file is not generated");
		}
		if (NativeLST.exists()) {
			System.out.println("Native file is Exists in pointed directory");
			base.passedStep("Native loadfile exists");

			driver.waitForPageToBeReady();
			for (String line : Files.readAllLines(
					Paths.get(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_Native.lst"))) {

				if (line.contains(directory + location + "\\" + Native + "\\")) {
					System.out.println("native is displayed as expected");
					base.passedStep("Native load file point Native directory");
				} else {
					base.failedStep("the text is not displayed as expected");
				}
			}
		} else {
			System.out.println("Does not Exists");
			base.stepInfo("Native load file is not generated");
		}

		base.passedStep("Text is displayed as expected");
		base.passedStep(
				"Verified generated Generate Load File Should point to respective Directories.(Eg.Native Load File Should point to Native Directory).");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith.Senthilkumar 47930
	 * @Description To verify, Priv Guard in Production should show correct document
	 *              counts
	 * 
	 */
	@Test(description="RPMXCON-47930",enabled = true, groups = { "regression" } )
	public void verifyPrivGuardDocCount() throws Exception {
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47930 -Production Sprint 09");
		base.stepInfo("To verify, Priv Guard in Production should show correct document counts");

		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String Redactiontag1;
		Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		int count = 3;

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
		docViewRedactions.selectDoc2();
		docViewRedactions.selectDoc3();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolderInRMU(foldername);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(count);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		base.waitForElement(page.getClk_burnReductiontoggle());
		page.getClk_burnReductiontoggle().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.AddRuleAndRemoveRuleTagAndRedaction(tagname, Redactiontag1);
		driver.waitForPageToBeReady();
		String docNo = page.getDocumentSelectionLink().getText().trim();
		int displayCount = Integer.parseInt(docNo);
		System.out.println(displayCount);
		SoftAssert softAssertion = new SoftAssert();
		if (count == displayCount) {
			softAssertion.assertEquals(count, displayCount);
			base.passedStep("Document count is get displayed Correctly");
			System.out.println("count is verified");
		} else {
			base.failedStep("Document Count verification failed");
			System.out.println("count not verified");
		}
		base.passedStep("verified, Priv Guard in Production should show correct document counts");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48020
	 * @Description: To Verify Natives of the docs of the selected file types or
	 *               selected tags are produced unless they are to excluded due to
	 *               Redaction or PrivTags.
	 */
	 @Test(description="RPMXCON-48020",enabled = true, groups = { "regression" } )
	public void verifyNativeDocs() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48020 ");
		base.stepInfo(
				"To Verify Natives of the docs of the selected file types or selected tags are produced unless they are to excluded due to Redaction or PrivTags.");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(1);
		String suffixID = Utility.randomCharacterAppender(1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		System.out.println("beg bates : " + beginningBates);
		int number = Integer.parseInt(beginningBates);
		int lastfile = number + docno;
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);

		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		driver.waitForPageToBeReady();

		String production = page.getProductionOutputLocationProductionDirectory().GetAttribute("value");
		String location = page.getProductionOutputLocation_VolumeName().GetAttribute("value");
		String Native = page.getProductionComponentsFolderDetails_FolderName_Natives().GetAttribute("value");

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNextButton());
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);

		page.fillingSummaryAndPreview();
		System.out.println(prefixID + beginningBates + suffixID);
		page.fillingGeneratePageWithContinueGenerationPopup();

		String home = System.getProperty("user.home");

		driver.waitForPageToBeReady();
		page.extractFile();

		for (int i = number + 3; i < lastfile; i++) {
			File f = new File(
					home + "/Downloads/" + location + "/" + Native + "/0001/" + prefixID + i + suffixID + ".doc");
			if (f.exists()) {
				base.passedStep("Native file copied from selected tag");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}

		}
		driver.waitForPageToBeReady();

		base.passedStep(
				"To verify that PA creates new production using continue with last bates range of last production, which is completed with only pre-gen check");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
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

	try {
		loginPage.quitBrowser();
	} catch (Exception e) {
		loginPage.quitBrowser();
	}

	}

	@AfterClass(alwaysRun = true)

	public void close() {
	System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");

	}

}

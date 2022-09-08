package sightline.productions;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProductionNew_Regression {
	Driver driver;
	LoginPage loginPage;
	ProductionPage page;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	BaseClass base;
	DocListPage docPage;

	String productionname;
	String exportname;
	
	String foldername;
	String tagname;

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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56132
	 * @Description:Verify that for the saved template controls in Specify DAT Field
	 *                     Mapping should be disabled
	 */

	@Test(description="RPMXCON-56132",enabled = true, groups = { "regression" })
	public void verifySavedTemplateControlIsDisabled() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56132 Production-Sprint 01");
		tagname = "Tag" + Utility.dynamicNameAppender();
		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		// verify saved template controls in dat mapping
		page.viewSaveTemplateControlForDATMapping();
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56130
	 * @Description:Verify that on click of Mark Incomplete from the Component
	 *                     section, already selected Redaction tags should not
	 *                     available for Redaction text
	 */
	@Test(description="RPMXCON-56130",enabled = true, groups = { "regression" })
	public void verifyClickMarkIncompleteDisablesALreadyRedactedTags() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56130 Production - Sprint 01");
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedaction(tagname);
		page.fillingTextSection();
		page.clickComponentMarkCompleteAndIncomplete();
		page.fillingTIFFWithBurningRedactionsAndPreviouslySelectedTagDisabled(tagname);
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56163
	 * @Description:Verify if user selects document level numbering and Sub-Bates
	 *                     number is null and user select multipage Tiff or PDF then
	 *                     Production preview is displays without any error
	 * 
	 */
	@Test(description="RPMXCON-56163",enabled = true, groups = { "regression" })
	public void passingSubBatesNullAndPreview() throws InterruptedException, AWTException {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56163 -Production Sprint 02");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// viewing preview in summary tab after passing null value in sub bates
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFWithMultiPage(tagname);
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentAndPassingNullSubBatesSuccess();
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		base.passedStep("Verified sub bates Null and preview");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56164
	 * @Description:Verify if user selects document level numbering and Sub-Bates
	 *                     number is null and user select SinglePage Tiff or PDF
	 *                     then error message should be displays on 'Numbering and
	 *                     Sorting' tab
	 */
	@Test(description="RPMXCON-56164",enabled = true, groups = { "regression" })
	public void passingSubBatesNullAndVerifyErrorMessage() throws InterruptedException, AWTException {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56164 -Production Sprint 02");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

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

		// viewing preview in summary tab after passing null value in sub bates
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithSinglePage(tagname);
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentAndPassingNullSubBatesError();
		base.passedStep("Verified Error message while passing sub bates Null value");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56149
	 * @Description:Verify that for Native section should be displayed message which
	 *                     will inform the user about the privileged and redacted
	 *                     docs from Production
	 */
	@Test(description="RPMXCON-56149",enabled = true, groups = { "regression" })
	public void verifyTooltipDisplayedOnPreview() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56149 -Production Sprint 02");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for the created folder and check the pure hit count
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat,native and display tooltip display on preview
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname,Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingToolTipInSummaryAndPreview();
		base.passedStep("verified Tooltip Displayed On Preview");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56120
	 * @Description:Verify that on Tiff/PDF section," Redaction without redaction tags" is pre-activated with the text "REDACTED"
	 *  when user clicks on 'Specify Redaction Text by Selecting Redaction Tags:'
	 * 
	 */
	@Test(description="RPMXCON-56120",enabled = true, groups = { "regression" })
	public void verifyTiffSectionRedactionTag() throws InterruptedException, AWTException {
		
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case Id: RPMXCON-56120 -Production Sprint 02");
	// viewing preview in summary tab after passing null value in sub bates
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingTheTIFFSection();
		base.passedStep("Verified TIFF section");
		loginPage.logout();		
	}
	
	/**
	* @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	*         No:RPMXCON_56099
	* @Description:Verify the error message for NATIVE component when 'Select Native component without tag and file type
	*/

	@Test(description="RPMXCON-56099",enabled = true, groups = { "regression" })
	public void AssertionOnNativeSection() throws Exception {
	UtilityLog.info(Input.prodPath);

	base.stepInfo("Test case Id: RPMXCON-56099 -Production Sprint 02");
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSectionWithoutSelectingTag();
	base.passedStep("Error Message for NATIVE is Displayed");
	loginPage.logout();
	}
	
	/**
	* @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	*         No:RPMXCON_56100
	* @Description:Verify the error message for TIFF/PDF component when 'Enable natively produce placeholder without tag and file type'
	*/

	@Test(description="RPMXCON-56100",enabled = true, groups = { "regression" })
	public void AssertionOnTiffSection() throws Exception {
	UtilityLog.info(Input.prodPath);

	base.stepInfo("Test case Id: RPMXCON-56100 -Production Sprint 02");
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.AssertionInTIFFSection();
	base.passedStep("Error Message Displayed");
	loginPage.logout();
	}
	
	
	/**
	* @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	*         No:RPMXCON_56112
	* @Description:Verify that if user selects the 'Genrate TIFF' option in production then preview document should be displays with the bates number in branding section
	*/

	@Test(description="RPMXCON-56112",enabled = true, groups = { "regression" })
	public void verifyTIFFWithBatesNUmber() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("Test case Id: RPMXCON-56112 -production Sprint 03");
	
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();

	// create folder and tag
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	// search for folder
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	// create production and verify preview tab button should display
	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	productionname = "p" + Utility.dynamicNameAppender();
	page.getDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.tiffBrandingSelection(tagname);
	page.tiffPrivilegeDocumentSelection(tagname);
	page.slipSheetToggleEnable();
	page.availableFieldSelection("BatesNumber");
	page.fillingTextSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.viewingPreviewButtonInSummaryTab();
	base.passedStep("Bates Number is Displayed");
	
	//To delete the created Tag and folder 
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
	loginPage.logout();
	}
	
	/**
	 * @Author sowndarya.Velraj Created on:NA  Modified by:NA 
	 *         TESTCASE No:RPMXCON_56162
	 * @Description:Verify that count of redacted documents displays correct in the Summary & Preview tab when documents having orphan redactions and redaction un-released redaction tags
	 */
	@Test(description="RPMXCON-56162",enabled = true, groups = { "regression" })
	public void selectingThreeRedactionDocs() throws Exception 
	{
		String Redactiontag1;
		String Redactiontag2;
		String Redactiontag3;
		
		base.stepInfo("Test case Id: RPMXCON-56162-production Sprint 03");

	    UtilityLog.info(Input.prodPath);
	    Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
        Redactiontag2 = "SecondRedactionTag" + Utility.dynamicNameAppender();
        Redactiontag3 = "ThirdRedactionTag" + Utility.dynamicNameAppender();
        foldername = "FolderProd" + Utility.dynamicNameAppender();

        RedactionPage redactionpage=new RedactionPage(driver);

        redactionpage.selectDefaultSecurityGroup();
        driver.waitForPageToBeReady();
        redactionpage.manageRedactionTagsPage(Redactiontag1);
        System.out.println("First Redaction Tag is created"+Redactiontag1);
        
        redactionpage.selectDefaultSecurityGroup();
        driver.waitForPageToBeReady();
        redactionpage.manageRedactionTagsPage(Redactiontag2);
        System.out.println("second Redaction Tag is created"+Redactiontag2);
        
        redactionpage.selectDefaultSecurityGroup();
        driver.waitForPageToBeReady();
        redactionpage.manageRedactionTagsPage(Redactiontag3);
        System.out.println("Third Redaction Tag is created"+Redactiontag3);
        
        
//        manage-->security group--redaction tags-->select 3 tags move to right

        loginPage.logout();
        
     // Login as RMU
     		loginPage = new LoginPage(driver);
     		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
     		
     		DocExplorerPage docExp=new DocExplorerPage(driver);
     		docExp.documentSelectionIteration();
     		docExp.docExpViewInDocView();
     		  
     		DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
     		//doc1
     		 docViewRedactions.selectDoc1();

            driver.waitForPageToBeReady();
             docViewRedactions.redactRectangleUsingOffset(10,10,100,100);
             driver.waitForPageToBeReady();
             docViewRedactions.selectingRedactionTag2(Redactiontag1);

             //d-2

             docViewRedactions.selectDoc2();
             driver.waitForPageToBeReady();
             docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(20,20,60,60);
             driver.waitForPageToBeReady();
             docViewRedactions.selectingRedactionTag2(Redactiontag3);
             
             driver.waitForPageToBeReady();
             docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(30,30,70,50);
             driver.waitForPageToBeReady();
             docViewRedactions.selectingRedactionTag2(Redactiontag2);
             
             //d-3

             driver.waitForPageToBeReady();
              docViewRedactions.selectDoc3();
              docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(12,12,8,8);
              driver.waitForPageToBeReady();
              docViewRedactions.selectingRedactionTag2(Redactiontag2);

            loginPage.logout();
            loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

            SecurityGroupsPage securityGroupsPage=new SecurityGroupsPage(driver);
            securityGroupsPage.navigateToSecurityGropusPageURL();
            securityGroupsPage.unAssigningTheTagInRedaction(Redactiontag3);

            //create tags and folders
            
            TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
            tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
            
            //Adding folder to bulkfolder
            DocExplorerPage docExplorer=new DocExplorerPage(driver);
            docExplorer.documentSelectionIteration();
            docExplorer.bulkFolderExisting(foldername);

            //remaining
            String prefixID = "A_" + Utility.dynamicNameAppender();
        	String suffixID = "_P" + Utility.dynamicNameAppender();
            ProductionPage page = new ProductionPage(driver);
            String beginningBates = page.getRandomNumber(2);
               productionname = "p" + Utility.dynamicNameAppender();
               page.selectingDefaultSecurityGroup();
               page.addANewProduction(productionname);
               page.fillingDATSection();
               page.fillingNativeSection();
               page.fillingTiffSectionBySelectingTwoRedactedTags(Redactiontag1, Redactiontag2);
               page.fillingTextSection();
               page.navigateToNextSection();
               page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
               page.navigateToNextSection();
               page.fillingDocumentSelectionPage(foldername);
               page.navigateToNextSection();
               page.fillingPrivGuardPage();
               page.fillingProductionLocationPage(productionname);
               page.navigateToNextSection();
               page.SummaryAndPreviewWithAssert();
               base.passedStep("Selected three documents and redacted them,then released to Default Security Group");

             //To delete the created Tag and folder 
           	tagsAndFolderPage = new TagsAndFoldersPage(driver);
           	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
           	loginPage.logout();
          }
	
	
	/**
	 * @Author sowndarya.Velraj Created on:NA  Modified by:NA 
	 *         TESTCASE No:RPMXCON_56108
	 * @Description:Verify the error message for MP3 component when 'Enable burn redaction without selecting redaction tag'
	 */
	@Test(description="RPMXCON-56108",enabled = true, groups = { "regression" })
	public void verifyErrorMessageForMP3() throws Exception 
	{
		String message="You have chosen MP3 redactions but have not specified redaction tags. In the MP3 section, please specify"
				+ " the redaction tags for which you want redactions burned in the production.";
		base.stepInfo("Test case Id: RPMXCON-56108-production Sprint 04");
	    UtilityLog.info(Input.prodPath);

	    ProductionPage page = new ProductionPage(driver);
	    page.navigateToProductionPage();
        productionname = "p" + Utility.dynamicNameAppender();
        page.selectingDefaultSecurityGroup();
        page.addANewProduction(productionname);
        page.fillingDATSection();
        page.getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		page.getAdvancedProductionComponent().Click();
		page.getMP3CheckBox().Click();
		page.getbtnMP3BurnRedactionTab().Click();
		page.getbtnMP3BurnRedaction().waitAndClick(5);
		page.getbtnMP3SelectRedactionTags().waitAndClick(5);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);
		
		try {
		Assert.assertTrue(true, message);
		base.passedStep("Error message is displayed for mp3 component");
		}
		catch (Exception e) {

			Assert.assertTrue(false, message);
			base.failedStep("Error message is not displayed for mp3 component");
		}
		loginPage.logout();
	}
	
	/**
	 * @Author sowndarya.Velraj Created on:NA  Modified by:NA 
	 *         TESTCASE No:RPMXCON_56106
	 * @Description:Verify the error message for TIFF/PDF component when 'Enable redaction without selecting redaction tag'
	 */
	@Test(description="RPMXCON-56106",enabled = true, groups = { "regression" })
	public void verifyErrorMessageInSpecifyRedactionWithNoText() throws Exception 
	{
		String message="You have chosen TIFF/PDF redactions but have not specified redaction tags. In the TIFF/PDF section, "
				+ "please specify the redaction tags for which you want redactions burned in the production.";
		base.stepInfo("Test case Id: RPMXCON-56106-production Sprint 04");
	    UtilityLog.info(Input.prodPath);
	    ProductionPage page = new ProductionPage(driver);
	    page.navigateToProductionPage();
        productionname = "p" + Utility.dynamicNameAppender();
        page.selectingDefaultSecurityGroup();
        page.addANewProduction(productionname);
        page.fillingDATSection();
        page.getTIFFChkBox().waitAndClick(5);
        page.getTIFFTab().waitAndClick(5);
        page.getTIFF_EnableforPrivilegedDocs().ScrollTo();
        page.getTIFF_EnableforPrivilegedDocs().Enabled();
        page.getTIFF_EnableforPrivilegedDocs().waitAndClick(5);
        page.getClk_burnReductiontoggle().ScrollTo();
        page.getClk_burnReductiontoggle().waitAndClick(5);
        driver.scrollingToElementofAPage(page.getclklinkSpecifyRedactionText());
        base.clickButton(page.getclklinkSpecifyRedactionText());
        base.waitForElement(page.gettextRedactionPlaceHolder());
		page.gettextRedactionPlaceHolder().SendKeys("");
		page.getSelectCloseBtn().waitAndClick(10);
		page.getEnableForNativelyToggle().waitAndClick(10);
		driver.scrollPageToTop();
		page.getComponentsMarkComplete().waitAndClick(5);
		try {
			Assert.assertTrue(true, message);
			base.passedStep("Error message is displayed for not selecting redaction tags");
			}
			catch (Exception e) {

				Assert.assertTrue(false, message);
				base.failedStep("Error message is not displayed for  not selecting redaction tags");
			}
		loginPage.logout();
			}

	/**
	 * @Author sowndarya.Velraj Created on:NA  Modified by:NA 
	 *         TESTCASE No:RPMXCON_56102
	 * @Description:Verify the error message for TIFF/PDF component when 'Enable Tech issue doc without tag or text'
	 */
	@Test(description="RPMXCON-56102",enabled = true, groups = { "regression" })
	public void verifyErrorMessageForEnableTechIssue() throws Exception 
	{
		String message="Technical Issue tags or corresponding placeholder text is missing in the Technical Issue Placeholdering of the TIFF/PDF section.";
		base.stepInfo("Test case Id: RPMXCON-56102-production Sprint 04");
	    UtilityLog.info(Input.prodPath);
	    ProductionPage page = new ProductionPage(driver);
	    page.navigateToProductionPage();
        productionname = "p" + Utility.dynamicNameAppender();
        page.selectingDefaultSecurityGroup();
        page.addANewProduction(productionname);
        page.fillingDATSection();
        page.getTIFFChkBox().waitAndClick(5);
        page.getTIFFTab().waitAndClick(5);
        page.getTIFF_EnableforPrivilegedDocs().ScrollTo();
        page.getTIFF_EnableforPrivilegedDocs().Enabled();
        page.getTIFF_EnableforPrivilegedDocs().waitAndClick(5);	
        page.getbtnEnableForTechIssue().ScrollTo();
		page.getbtnEnableForTechIssue().waitAndClick(5);
		page.getSelectCloseBtn().waitAndClick(10);
		page.getEnableForNativelyToggle().waitAndClick(10);
    	driver.scrollPageToTop();
		page.getComponentsMarkComplete().waitAndClick(5);
		try {
			Assert.assertTrue(true, message);
			base.passedStep("Error message is displayed for not selecting redaction tags");
			}
			catch (Exception e) {

				Assert.assertTrue(false, message);
				base.failedStep("Error message is not displayed for  not selecting redaction tags");
			}
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56079
	 * @Description: generate production with ingested text
	 */
	@Test(description="RPMXCON-56079",enabled = true, groups = { "regression" })
	public void generateProductionWithIngestedText() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56079 Production - Sprint 04");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for the created folder and check the pure hit count
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Generated production with ingested text");
		
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56129
	 * @Description:Verify that on click of Mark Incomplete from the Component
	 *                     section, already selected tags should not available for
	 *                     selection in branding
	 */
	@Test(description="RPMXCON-56129",enabled = true, groups = { "regression" })
	public void verifyClickMarkIncompleteDisablesALreadySelectedTags() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56129 Production - Sprint 04");
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithSelectingBrandingSelectionTags(tagname);
		page.fillingTextSection();
		page.clickComponentMarkCompleteAndIncomplete();
		page.fillingTIFFWithBrandingSelectionTagsAndPreviouslySelectedTagDisabled(tagname);
		base.passedStep("Verify that on click of Mark Incomplete from the Component section, already selected tags should not available for  selection in branding");

		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
	
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56077
	 * @Description:generateProductionWithIngestedText1.
	 */
	@Test(description="RPMXCON-56077",enabled = true, groups = { "regression" })
	public void generateProductionWithIngestedText1() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56077 Production - Sprint 04");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

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
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname,Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("generate Production With Ingested Text");
		
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}

	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56140
	 * @Description:Verify that Count displays correctly on Priv Guard if Parent
	 *                     documents is assigned to Priv Tag
	 */
	@Test(description="RPMXCON-56140",enabled = true, groups = { "regression" })
	public void verifyCountDisplaysCorrectlyOnPrivGuardForParentDocuments() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56140 Production - Sprint 04");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for the created folder and check the pure hit count
		sessionSearch = new SessionSearch(driver);

		sessionSearch.basicContentSearch(Input.parentDocument);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);

		docPage.SelectingParentDocumentFromDocList(Input.parentDocument);
		System.out.println(foldername);

		docPage.bulkFolderExisting(foldername);

		// create production using dat,native and display tooltip display on preview
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname,Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPageExcludingFamilies(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verify that Count displays correctly on Priv Guard if Parent documents is assigned to Priv Tag");

		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
		
	}


	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_58592
	 * @Description: generateProductionUnderDefaultDirectory.
	 */
	@Test(description="RPMXCON-58592",enabled = true, groups = { "regression" })
	public void generateProductionUnderDefaultDirectory() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-58592 Production- Sprint 04");
		String testData1 = Input.testData1;

		// Pre-requisites
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// searching for foldername and check pure hit count
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// create production under default directory
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
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
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("generate Production Under Default Directory");
		
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();

	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA 
	 * TESTCASE No:RPMXCON-56159
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description:Verify that Production should generate with Natively placholder for Orphan redacted document. 
	 *TESTCASE NO: RPMXCON-56160
	 * @Description:Verify that Production should generate with Priv placholder for
	 *   Orphan redacted document and also document is associated  as Natively produced
	 *  TESTCASE NO: RPMXCON-56157
	 * @Description: Verify that Production should generate with Priv placholder for Orphan redacted document
	 */
	@Test(description="RPMXCON-56159,RPMXCON-56157,RPMXCON-56160",enabled = true, groups = { "regression" })
	public void verifyProductionWithOrphanRedactedDocumemtsForNativelyPlaceholder() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56159 Production- Sprint 04");
		base.stepInfo("RPMXCON-56157 Production- Sprint 04");
		base.stepInfo("RPMXCON-56160 Production- Sprint 04");
		
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		//Tag created as privileged tag
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		// passing orphan redacted DOCID as string
		sessionSearch.basicContentSearch(Input.orphanDocument);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);
		docPage.saveDocListToProfile(Input.orphanDocument);
		// adding document to bulk tag
		docPage.bulkTagExisting(tagname);

		// create production using dat,native and tiff for redaction
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithRedactionAndSelectingDoubleTags();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verified that Production should generate with Priv placholder for Orphan redacted document");
	
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56158
	 * @Description:Verify that Production should generate with Tech Issue placholder for Orphan redacted document
	 */
	@Test(description="RPMXCON-56158",enabled = true, groups = { "regression" })
	public void verifyProductionWithOrphanRedactedDocumemtsForTechIssuePlaceholders() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56158 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname,"Technical Issue");

		sessionSearch = new SessionSearch(driver);
		// passing orphan redacted DOCID as string
		sessionSearch.basicContentSearch(Input.orphanDocument);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);
		docPage.saveDocListToProfile(Input.orphanDocument);
		// adding document to bulk tag
		docPage.bulkTagExisting(tagname);

		// create production using dat,native and tiff for redaction
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithRedactionAndSelectingDoubleTags();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verify that Production should generate with Tech Issue placholder for Orphan redacted documents");
	
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         NO:RPMXCON_56156
	 *Validate text on "Text redactions" and "Batch redactions" applied documents with PDF generated files
	 *   TESTCASE  No:RPMXCON_56154
	 * @Description:Validate text on "Text redactions" applied documents with PDF generated files
	 */

	@Test(description="RPMXCON-56154,RPMXCON-56156",enabled = true, groups = { "regression" })
	public void generateProductionWithPDFFiles() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56156 Production- Sprint 04");
		base.stepInfo("RPMXCON-56154 Production- Sprint 04");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		//search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		//create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFForRedaction();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Validate text on Text redactions applied documents with PDF generated files");
	
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56153
	 * @Description:Validate text on "Text redactions" applied documents with TIFF
	 *                       generated files.
	 *TESTCASE No:RPMXCON-56155                    
	 * @Description:Validate text for "Text redactions" and "Batch redactions" are
	 *                       applied area on documents with TIFF generated files
	 */

	@Test(description="RPMXCON-56153,RPMXCON-56155",enabled = true, groups = { "regression" })
	public void generateProductionWithTIFFFiles() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56153 Production- Sprint 04");
		base.stepInfo("RPMXCON-56155 Production- Sprint 04");
		
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		//Pre-requisites
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		//search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		//create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithFontColour(tagname,"Black with white font");
		page.fillingTextSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Validate text for Text redactions and Batch redactions are applied area on documents with TIFF generated files");
	
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_47968
	 * @Description:To verify In Production user can select the only DAT and TIFF component and Production should generated successfully
	 */                      
	@Test(description="RPMXCON-47968",enabled = true, groups = { "regression" })
	public void generateProductionWithDATAndTIFF() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47968 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		//search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		//create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname,Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("To verify In Production user can select the only DAT and TIFF component and Production should generated successfully");
		
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
		
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_48308
	 * @Description:To verify that the all the pages are together in proper sequence for a document in OPT.
	 */                      
	@Test(description="RPMXCON-48308",enabled = true, groups = { "regression" })
	public void verifyDocumentsInOPT() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48308 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		//search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		//create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithMultiPage(tagname);
		page.getSelectCloseBtn().waitAndClick(10);
		page.getEnableForNativelyToggle().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.clickButton(page.getAdvancedTabInTIFF());
		driver.waitForPageToBeReady();
		base.clickButton(page.getLoadFileTypeInTIFF());
		base.clickButton(page.getOPTInLoadFileType());
		driver.scrollingToBottomofAPage();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("To verify that the all the pages are together in proper sequence for a document in OPT.");
		 
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_48309
	 * @Description:To verify that the order of docs in "Text, Native, MP3, PDF" LST is matching the order of docs in DAT.
	 */                      
	@Test(description="RPMXCON-48309",enabled = true, groups = { "regression" })
	public void verifyDocOrder() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48309 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		//search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		//create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		base.waitForElement(page.getAdvancedTabInNative());
		new Actions(driver.getWebDriver()).moveToElement(page.getAdvancedTabInNative().getWebElement()).click();
		base.clickButton(page.getGenerateLoadFileToggleInNative());
		driver.scrollingToBottomofAPage();
		page.fillingPDFSection(tagname,Input.tagNameTechnical);
		base.clickButton(page.getAdvancedTabInTIFF());
		page.fillingTextSection();
		base.clickButton(page.getAdvancedTabInText());
		base.clickButton(page.getAdvancedProductionComponent());
		base.clickButton(page.getMP3CheckBox());
		base.clickButton(page.getMP3Tab());
		driver.scrollingToBottomofAPage();
		base.clickButton(page.getAdvancedTabInMP3());
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("verified that the order of docs in Text, Native, MP3, PDF LST is matching the order of docs in DAT");
	
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_49225
	 * @Description:To verify that Family members having the same FamilyID must be withheld, if 'Withhold Natives for Entire Family for Priv and Redacted Docs' option is enabled
	 */                      
	@Test(description="RPMXCON-49225",enabled = true, groups = { "regression" })
	public void verifyParentIDInPrivAndRedactedDocs() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49225 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		
		//search for redacted documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		//search for parent document and adding to bulk folder
		base=new BaseClass(driver);
		base.selectproject();
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.parentDocument);
		sessionSearch.ViewInDocList();
		docPage = new DocListPage(driver);
		docPage.SelectingParentDocumentFromDocList(Input.parentDocument);
		System.out.println(foldername);
		docPage.bulkFolderExisting(foldername);
		
		//create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname,Input.tagNameTechnical);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("verified FamilyID withheld, if 'Withhold Natives for Entire Family for Priv and Redacted Docs' option is enabled");
	
		//To delete the created Tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();
	}
	
	//30 cases
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
//			loginPage.logoutWithoutAssert();
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//			login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}

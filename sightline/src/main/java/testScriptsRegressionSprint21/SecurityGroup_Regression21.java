package testScriptsRegressionSprint21;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroup_Regression21 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	
	/**
	 * @author Vijaya.Rani ModifyDate:12/09/2022 RPMXCON-54738
	 * @throws Exception
	 * @Description Verify that RMU can save the Production template.
	 * 
	 */
	@Test(description = "RPMXCON-54738", enabled = true, groups = { "regression" })
	public void verifyingTemplateInProductioninRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54738");
		baseClass.stepInfo("Verify that RMU can save the Production template.");
		baseClass = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();

		page.navigateToProductionPage();
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/09/2022 RPMXCON-54743
	 * @throws Exception
	 * @Description Verify that if DAU impersonate as RMU,can create and generate
	 *              the new production.
	 * 
	 */
	@Test(description = "RPMXCON-54743", enabled = true, groups = { "regression" })
	public void verifyDAImpersonateRMUGenerateProduction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54743");
		baseClass.stepInfo("Verify that if DAU impersonate as RMU,can create and generate the new production.");

		userManage = new UserManagement(driver);
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		baseClass.stepInfo("Impersonate DA to RMU");
		baseClass.impersonateDAtoRMU();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNameTechnical);
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

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/09/2022 RPMXCON-54744
	 * @throws Exception
	 * @Description Verify that RMU can create new production using template which
	 *              is created by PAU in same security group.
	 * 
	 */
	@Test(description = "RPMXCON-54744", enabled = true, groups = { "regression" })
	public void verifyProductionGenerationUsingTemplate() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54744");
		baseClass.stepInfo(
				"Verify that RMU can create new production using template which is created by PAU in same security group.");
		baseClass = new BaseClass(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

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
	 * @author Vijaya.Rani ModifyDate:12/09/2022 RPMXCON-54745
	 * @throws Exception
	 * @Description Verify that after commting the production by RMU,produced documents can view the in doc view.
	 * 
	 */
	@Test(description="RPMXCON-54745",enabled = true, groups = { "regression" })
	public void verifyDocumentDisplayedInProductionInDocViewPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54745");
		baseClass.stepInfo(
				"Verify that after commting the production by RMU,produced documents can view the in doc view.");
		baseClass = new BaseClass(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		UtilityLog.info(Input.prodPath);
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
		baseClass.stepInfo("View searched for  docs in Doc view");
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
	 * @author Vijaya.Rani ModifyDate:13/09/2022 RPMXCON-54750
	 * @throws Exception
	 * @Description Verify that RMU can regenerate the production.
	 * 
	 */
	@Test(description = "RPMXCON-54750", enabled = true, groups = { "regression" })
	public void verifyRMURegenerateTheProduction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54750");
		baseClass.stepInfo("Verify that RMU can regenerate the production.");

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		UtilityLog.info(Input.prodPath);
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int nativefile = Integer.parseInt(beginningBates);
		int NativeDocStart = nativefile + 3;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		driver.waitForPageToBeReady();
		page.clickBackBtnandSelectingNative(7, tagname);
		driver.scrollingToBottomofAPage();
		page.getTIFF_EnableforPrivilegedDocs().isDisplayed();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.clickMArkCompleteMutipleTimes(3);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopup();

		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:13/09/2022 RPMXCON-54771
	 * @throws Exception
	 * @Description Verify that if RMU changes the SG from header ,Productions menu should be displays.
	 * 
	 */
	@Test(description = "RPMXCON-54771", enabled = true, groups = { "regression" })
	public void verifyRMUHeaderSGProductionDisplay() throws Exception {

		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54771");
		baseClass.stepInfo("Verify that if RMU changes the SG from header ,Productions menu should be displays.");

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		baseClass.waitForElement(sgpage.securityGroupTab());
		String getAttribute = sgpage.securityGroupTab().GetAttribute("title");
		if (getAttribute.equalsIgnoreCase("Default Security Group")) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		baseClass.waitTillElemetToBeClickable(sgpage.productionPageSelectedSG());
		String sgname = sgpage.productionPageSelectedSG().getText();
		if (sgname.equalsIgnoreCase(getAttribute)) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}

		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:13/09/2022 RPMXCON-54784
	 * @throws Exception
	 * @Description Verify that if SAU impersonate as RMU,and changes the Project from header drop down should take to Default SG in the selected project.
	 * 
	 */
    @Test(description = "RPMXCON-54784", enabled = true, groups = { "regression" })
	public void verifySAImpersonateAsRMUHomePageProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54784");
		baseClass.stepInfo(
				"Verify that if SAU impersonate as RMU,and changes the Project from header drop down should take to Default SG in the selected project.");
		
		UserManagement userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU();
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		baseClass.stepInfo("verify RMU Home Page Selected project");
		String actualProject=Input.projectName;
		String expectedProject=baseClass.getProjectNames().getText();
		softassert.assertEquals(actualProject, expectedProject);
		baseClass.passedStep("RMU Dashboard Successfully Clicked the Slected project");
		softassert.assertAll();
		loginPage.logout();
    }
    
    /**
	 * @author Vijaya.Rani ModifyDate:14/09/2022 RPMXCON-54785
	 * @throws Exception
	 * @Description Verify thatif SAU user impersonate as RMU,and changes the SG in SG Dropdown then it should take the corresponding SG in the same project.
	 * 
	 */
    @Test(description = "RPMXCON-54785", enabled = true, groups = { "regression" })
	public void verifySAImpersonateAsRMUDropDownSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54785");
		baseClass.stepInfo(
				"Verify thatif SAU user impersonate as RMU,and changes the SG in SG Dropdown then it should take the corresponding SG in the same project.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU();
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
		loginPage.logout();
    }
    
    /**
	 * @author Vijaya.Rani ModifyDate:14/09/2022 RPMXCON-54786
	 * @throws Exception
	 * @Description Verify that when PAU impersonate as RMU,and changes the Project from header drop down should take to Default SG in the selected project.
	 * 
	 */
    @Test(description = "RPMXCON-54786", enabled = true, groups = { "regression" })
	public void verifyPAImpersonateAsRMUSelectedProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54786");
		baseClass.stepInfo(
				"Verify that when PAU impersonate as RMU,and changes the Project from header drop down should take to Default SG in the selected project.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SoftAssert softassert = new SoftAssert();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Impersonate PA to RMU");
		baseClass.impersonatePAtoRMU();
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		baseClass.stepInfo("verify RMU Home Page Selected project");
		String actualProject=Input.projectName;
		String expectedProject=baseClass.getProjectNames().getText();
		softassert.assertEquals(actualProject, expectedProject);
		baseClass.passedStep("RMU Dashboard Successfully Clicked the Slected project");
		softassert.assertAll();
		loginPage.logout();
    }
    
    /**
	 * @author Vijaya.Rani ModifyDate:15/09/2022 RPMXCON-54306
	 * @throws Exception
	 * @Description To verify that if user select option 'Use Security Group specific Email Inclusive and Email Duplicate data', belly band message should be displayed.
	 * 
	 */
	@Test(description = "RPMXCON-54306", enabled = true, groups = { "regression" })
	public void verifySelectSGEmailInclusiveDataInBellyBandMsg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54306");
		baseClass.stepInfo(
				"To verify that if user select option 'Use Security Group specific Email Inclusive and Email Duplicate data', belly band message should be displayed.");

		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		savedsearch = new SavedSearch(driver);
		SoftAssert softassert = new SoftAssert();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		driver.waitForPageToBeReady();
		sgpage.createSecurityGroups(SGname);
		baseClass.stepInfo(SGname + "is created successfully");
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(2));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(5);
		baseClass.stepInfo("Use Security Group-Specific Email inclusive check mark is selected");
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.passedStep("Generate button is clicked successfully");
		driver.waitForPageToBeReady();

		// verify warning message
		String Actualwarningmsg = sgpage.getVerifySG_EmailGenerateWarningMsg().getText();
		String expectWarningmsg = "You have elected to regenerate and overwrite all previous values for the four Security Group-specific email inclusiveness and email duplicate fields. This will wipe away all prior stored data for these attributes, and will overlay new values for each record currently in the Security Group. No prior work product or logic will be undone, which may mean that your Assignments and workflow may need to be altered. Do you want to proceed?";
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		baseClass.passedStep("Warning message is displayed successfully");
		baseClass.getNOBtn().Click();
		sgpage.getSG_AnnSaveButton().waitAndClick(5);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.stepInfo("Successfully Clicked Save Button");
		softassert.assertAll();

		sgpage.deleteSecurityGroups(SGname);
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:15/09/2022 RPMXCON-54314
	 * @throws Exception
	 * @Description To verify that when the SG is set to use project level fields that Tag and Folder Propagation happens using EmailDuplicateDocIDs attribute.
	 * 
	 */
	@Test(description = "RPMXCON-54314", enabled = true, groups = { "regression" })
	public void verifySGTagUsingEmailDuplicateDocIdInDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54314");
		baseClass.stepInfo(
				"To verify that when the SG is set to use project level fields that Tag and Folder Propagation happens using EmailDuplicateDocIDs attribute.");

		userManage = new UserManagement(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage doclist = new DocListPage(driver);
		String tagname = "Tag" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		doclist.documentSelection(1);
		doclist.addNewBulkTagEmailDuplicates(tagname);
		
		baseClass.passedStep("All EmailDuplicateDocIds' including the selected document is tagged.");
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-54740 Date:9/19/2022
	 * @Description:Verify that RMU can view the existing Proudction Export details
	 */
	@Test(description = "RPMXCON-54740", enabled = true, groups = { "regression" })

	public void verifyingNativeFilesInGenreratedExport() throws Exception {
		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-54740-from Security Groups");
		base.stepInfo("Verify that RMU can view the existing Proudction Export details");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = Input.randomText + Utility.dynamicNameAppender();
		String exportName1 = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(page.getProductionNameLink(exportName), exportName);
		base.ValidateElement_Presence(page.getProductionNameLink(exportName1), exportName1);
		baseClass.passedStep("RMU can view the existing Proudction Export details");
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}

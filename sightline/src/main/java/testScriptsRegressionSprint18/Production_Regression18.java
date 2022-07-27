package testScriptsRegressionSprint18;
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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression18 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;

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
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-47939 
	 * Description :To Verify in the tree structure of tags, disable and grey out the tags that are not of type Privileged.
	 * 
	 */
	@Test(description = "RPMXCON-47939", enabled = true, groups = { "regression" })
	public void verifyPrivilegedSelectedAndOtherTagDisabled() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47939 -Production Component");
		base.stepInfo("To Verify in the tree structure of tags, disable and grey out the tags that are not of type Privileged.");

		String Tagname = "Tag" + Utility.dynamicNameAppender();
		String Tagname1 = "Tag" + Utility.dynamicNameAppender();
		String Tagname2 = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(Tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(Tagname1,"Select Tag Classification");
		tagsAndFolderPage.createNewTagwithClassification(Tagname2,"Select Tag Classification");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.verifyPrivTagSelectionAndDisableOfOtherTag(Tagname,Tagname1,Tagname2);
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname1, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname2, Input.securityGroup);
		loginPage.logout();
	}

	
	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-47891
	 *  Description :To Verify Manage Template Tab
	 * 
	 */
	@Test(description = "RPMXCON-47891", enabled = true, groups = { "regression" })
	public void verifyingManageTemplateInProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47891 -Production Component");
		base.stepInfo("To Verify Manage Template Tab");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		
		driver.waitForPageToBeReady();
		page = new ProductionPage(driver);
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		page.verifyingComponentTabSection();
		

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
		
	}
	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49240
	 *  Description :To verify that Project Admin can enter the information for the bates number fields.
	 * 
	 */
	@Test(description = "RPMXCON-49240", enabled = true, groups = { "regression" })
	public void verifyingAdminCanEnterBatesNumber() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base=new BaseClass(driver);
		base.stepInfo("RPMXCON-49240 -Production Component");
		base.stepInfo("To verify that Project Admin can enter the information for the bates number fields.");
		
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		driver.scrollPageToTop();
		page.getSaveOption().waitAndClick(10);
		base.VerifySuccessMessage("Information Saved Successfully");
		String BatesNumber =page. gettxtBeginningBatesIDPrefix().GetAttribute("value");
		String BatesNumbers =page. gettxtBeginningBatesIDSuffix().GetAttribute("value");
		String[] BatesNumberInSortingTab = { BatesNumber,BatesNumbers};
		
		for (int i = 0; i < BatesNumberInSortingTab.length; i++) {
			System.out.println(BatesNumberInSortingTab[i]);
			if(BatesNumberInSortingTab[i] !=null) {
				base.passedStep("Project admin can enter the bates number field");
			}else {
				base.failedStep("Project admin cannot enter the bates number field");
			}
		}
		loginPage.logout();
		
			}
	
	
	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49249
	 *  Description :To verify that if user click on 'X' icon then Next Bates Number should not be populated into the bates number fields
	 * 
	 */
	@Test(description = "RPMXCON-49249", enabled = true, groups = { "regression" })
	public void validatingNextBatesNumberNotPopulatedInSortingTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49249 -Production Component");
		base.stepInfo("To verify that if user click on 'X' icon then Next Bates Number should not be populated into the bates number fields");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
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
		
		page = new ProductionPage(driver);
		String SubBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.getNumbering_Document_RadioButton().waitAndClick(10);
		page.getNumbering_Document_BeginningBates_Text().SendKeys(SubBates);
        page.verifyAvailbleLinkAtNumberingAndSorting();
		base.waitForElement(page.getClickHereLink());
		page.getClickHereLink().Click();
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(page.getNextBatesNumber(),"Next Bates Number");
        page.getCloseIconInManageTemplate().waitAndClick(10);;
        String actualText = page.getBeginningBates().GetAttribute("value");
		System.out.println("The actual txt" + actualText);
		base.digitCompareEquals(Integer.valueOf(actualText), 0, "Bates Number are not auto populated as expected","Bates Number are auto populated");
        
		// delete folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49964
	 *  Description :Verify that if Redaction placeholder text is blank then error should be displayed
	 * 
	 */
	@Test(description = "RPMXCON-49964", enabled = true, groups = { "regression" })
	public void verifyingBurnRedactionPlaceholderErrorMsg() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base=new BaseClass(driver);
		base.stepInfo("RPMXCON-49964 -Production Component");
		base.stepInfo("Verify that if Redaction placeholder text is blank then error should be displayed");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.verifyPlaceholderTextInBurnRedaction(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();
		page.gettextRedactionPlaceHolder().Clear();
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifyErrorMessage("You must specify the redaction text that you want to see in TIFF/PDF burned redactions.");
		
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




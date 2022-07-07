package testScriptsRegressionSprint16;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.constraints.Size;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Securitygroups_Regression2 {

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

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

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
		loginPage = new LoginPage(driver);
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

	/**
	 * @author Date: Modified date:N/A Modified by: Description Verify that RMU can
	 *         create and genearte new Production successfully for his security
	 *         group
	 * 
	 */
	@Test(description = "RPMXCON-54737", enabled = true, groups = { "regression" })
	public void verifyRMUCreateGenarateNewProductionForSg() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-54737");
		baseClass
				.stepInfo("Verify that RMU can create and genearte new Production successfully for his security group");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "p" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		baseClass.stepInfo("New Tag created with classification " + tagname);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.fillingMP3FileWithBurnRedaction(Input.defaultRedactionTag);
		page.getMP3FilesBurnRedactionTag(Input.defaultRedactionTag).ScrollTo();
		page.getMP3FilesBurnRedactionTag(Input.defaultRedactionTag).waitAndClick(10);
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
		baseClass.stepInfo("Production generate completed successfully for new SG");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description :To verify after
	 *         impersonating as Project Admin , sys admin can create security group.
	 */
	@Test(description = "RPMXCON-53628", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonatingPaSaCreateSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53628");
		baseClass.stepInfo("To verify after impersonating as Project Admin , sys admin can create security group.");
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login as PAU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Loggedin As SA");
		baseClass.impersonateSAtoPA();
		sgpage.navigateToSecurityGropusPageURL();
		// Create Security group
		sgpage.createSecurityGroups(SGname);
		baseClass.stepInfo("Added new Security group");
		System.out.println(SGname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		List<WebElement> SGgrpname = sgpage.getSecurityGroupList().selectFromDropdown().getOptions();
		int size = SGgrpname.size();
		for (int i = 0; i <= size; i++) {
			String option = SGgrpname.get(i).getText();
			if (SGname.equals(option)) {
				baseClass.stepInfo(SGname + "name is saved and displayed");
				break;
			} else {
				System.out.println("checking next security");
			}
		}
		sgpage.getSecurityGroupList().waitAndClick(3);
		baseClass.waitForElement((sgpage.getSGGrpList(SGname)));
		if (sgpage.getSGGrpList(SGname).isElementAvailable(5)) {
			baseClass.passedStep(SGname + "is displayed successfully");

		} else {
			baseClass.failedStep("sg name not saved");

		}
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("Added Security group has been deleted");

	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description :Verify that when
	 *         RMU changes the Security Group from Dropdown then it should take the
	 *         corresponding SG of the same project
	 */
	@Test(description = "RPMXCON-54790", enabled = true, groups = { "regression" })
	public void verifyRmuChangeSgFromDropDownTheSameProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54790");
		baseClass.stepInfo(
				"Verify that when RMU changes the Security Group from Dropdown then it should take the corresponding SG of the same project");
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);
		SoftAssert softassert = new SoftAssert();
		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Create security group
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(SGname);
		baseClass.stepInfo("Added new Security group");

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(SGname, Input.rmu1userName);
		userManagement.saveSecurityGroup();
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As RMU");
		driver.waitForPageToBeReady();
		docViewMetaDataPage.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.stepInfo(SGname + "from header drop down for current project on rmu");
		baseClass.waitForElement(docViewMetaDataPage.getSecurityGroup(SGname));
		softassert.assertTrue(docViewMetaDataPage.getSecurityGroup(SGname).isDisplayed());
		if (docViewMetaDataPage.getSecurityGroup(SGname).isDisplayed()) {
			baseClass.passedStep("corressponding " + SGname + "on the same project is displayed successfully");

		} else {
			baseClass.failedStep("SG not displayed");

		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("Added Security group has been deleted");
	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description Verify that RMU can
	 *         export the documents in his Security Group
	 * 
	 */
	@Test(description = "RPMXCON-54739", enabled = true, groups = { "regression" })
	public void verifyRMUExportTheDocInSg() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-54739");
		baseClass.stepInfo("Verify that RMU can export the documents in his Security Group");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = "p" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		baseClass.stepInfo("New Tag created with classification " + tagname);

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
		page.selectGenerateOption(false);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

	}

}

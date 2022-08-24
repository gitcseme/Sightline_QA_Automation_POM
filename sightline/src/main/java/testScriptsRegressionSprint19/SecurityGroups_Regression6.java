package testScriptsRegressionSprint19;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
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

public class SecurityGroups_Regression6 {
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
	 * @Author Krishna Date: 8/08/2022
	 * @Description : Verify if RMU is select the other project in which he is PAU
	 *              role, it should take to SG in the that project
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54848", enabled = true, groups = { "regression" })
	public void verifyRmuSelectOtherProjectInPaRoleTakeToSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54848");
		baseClass.stepInfo(
				"Verify if RMU is select the other project in which he is PAU role, it should take to SG in the that project");
		SoftAssert softassert = new SoftAssert();
		docexp = new DocExplorerPage(driver);
		DataSets data = new DataSets(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);
		// Login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in Rmu");
		softassert.assertTrue(docexp.getDocExplorerTabAfterDashBoard().isDisplayed());
		baseClass.stepInfo("Rmu is redirect to dashboard page");
		loginPage.logout();

		// create project
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		String projectName = "SecurityGroupProject" + Utility.dynamicNameAppender();
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "1");
		data.getNotificationMessage(0, projectName);
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		System.out.println(projectName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		driver.waitForPageToBeReady();
		baseClass.selectproject(projectName);
		softassert.assertEquals(projectName, projectName);
		baseClass.passedStep(projectName + "..RMU Select project in which is user is PAU is selected successfully");
		driver.waitForPageToBeReady();
		docViewMetaData.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(Input.securityGroup));
		if (docViewMetaData.getSecurityGroup(Input.securityGroup).isDisplayed()) {
			baseClass.passedStep(Input.securityGroup + ".. is successfully displayed in selected project");
		} else {
			baseClass.failedStep("Sg is not displayed");

		}
		softassert.assertAll();
	}

	/**
	 * @Author Sakthivel Date: 9/08/2022
	 * @Description :Verify that as DA user,clicking on project should redirect to
	 *              default security group and then on changing the Project from
	 *              header drop down should take to Default SG in the selected
	 *              project
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54773", enabled = true, groups = { "regression" })
	public void verifyDAClickingProductDsgChangingSelectedProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54773");
		baseClass.stepInfo(
				"Verify that as DA user,clicking on project should redirect to default security group and then on changing the Project from header drop down should take to Default SG in the selected project");
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default in selected project");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Click on other project");
		baseClass.stepInfo("verify default security group in selected project");
		String ExpectedString1 = baseClass.getsgNames().getText();
		System.out.println(ExpectedString1);
		if (actualString.equals(ExpectedString1)) {
			baseClass.passedStep("DAU to Default SG in the selected project Successfully..");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
	}
	
	/**
		 * @author Krishna ModifyDate:04/08/2022 RPMXCON-54915
		 * @throws Exception
		 * @Description To verify that project admin can navigate to tags for selected
		 *              security group and observe no duplicates.
		 * 
		 */
		@Test(description = "RPMXCON-54915", enabled = true, groups = { "regression" })
		public void verifyPANavigateTagsSelectedSgObserveNoDuplicates() throws Exception {
			baseClass.stepInfo("Test case Id: RPMXCON-54915");
			baseClass.stepInfo(
					"To verify that when the SG is set to use SG-specific attributes then Folder Propagation should happens using EmailDuplicateDocIDs attribute.");
			SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
			String tags = "Tags";
			String defaulttags = "Default Tags";

			// Login as PA
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("Login as PA");
			sgpage.navigateToSecurityGropusPageURL();
			baseClass.stepInfo("navigated to security group as expected");

			baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
			sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);
			sgpage.verifySelectTagsAssignedInSelectedList(defaulttags);
			driver.waitForPageToBeReady();
			int totFolderCount = sgpage.getSgDefaultTag().size();
			if (totFolderCount == 1) {
				baseClass.passedStep("Default Tags labels Duplicates is NOT appear on Security Group mapping screen.");

			} else {
				baseClass.failedStep("Default Tags labels Duplicates is appear on mappling screen");
			}
			driver.Navigate().refresh();
			baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
			sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);
			sgpage.verifySelectTagsAssignedInSelectedList(defaulttags);
			driver.waitForPageToBeReady();
			int totFolderCount1 = sgpage.getSgDefaultTag().size();
			if (totFolderCount1 == 1) {
				baseClass.passedStep("Default Tags labels Duplicates is NOT appear on Security Group mapping screen.");

			} else {
				baseClass.failedStep("Default Tags labels Duplicates is appear on mappling screen");
			}
		}
		
		/**
		 * @author Krishna Date-08/11/22 RPMXCON-54867
		 * @throws Exception
		 * @Description Verify after impersonated AS PAU in domain project where DAU is
		 *              PAU and select the other domain project/non-domain from header
		 *              drop down ,should redirect to the PAU home page for the selected
		 *              project
		 * 
		 */
		@Test(description = "RPMXCON-54867", enabled = true, groups = { "regression" })
		public void verifyAfterImpersonatedDAURedirectToPAUHomePage() throws Exception {
			baseClass.stepInfo("Test case Id: RPMXCON-54867");
			baseClass.stepInfo(
					"Verify after impersonated AS PAU in domain project where DAU is PAU and select the other domain project/non-domain from header drop down ,should redirect to the PAU home page for the selected project");
			DataSets data = new DataSets(driver);

			// Login As DA
			loginPage.loginToSightLine(Input.da1userName, Input.da1password);
			baseClass.stepInfo("Logged in DAU");
			
			baseClass.stepInfo("Impersonate DA to PA");
			driver.waitForPageToBeReady();
			baseClass.impersonateDAtoPA();

			baseClass.stepInfo("Select other Domain project");
			driver.waitForPageToBeReady();
			baseClass.selectproject(Input.projectName);

			driver.waitForPageToBeReady();
			if (data.getDatasetBtn().isDisplayed()) {
				baseClass.passedStep("It is redirect to the PAU home page for the selected project Successfully");
			} else {
				baseClass.failedStep("verification failed");
			}
			loginPage.logout();
		}

		
		/**
		 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
		 * Id:RPMXCON-63768 Verify Production should generated without comments if user
		 * saved Comments in the documents with 'Copy' menu in DocView
		 * 
		 * 
		 */
		@Test(description="RPMXCON-63768",enabled = true, alwaysRun = true, groups = { "regression" })
		public void verifyProductionGenerateWithoutCommentsDocsWithCopMenu() throws Exception {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-63768");
			baseClass.stepInfo(
					"Verify Production should generated without comments if user saved Comments in the documents with 'Copy' menu in DocView");
			DocViewPage docView = new DocViewPage(driver);
			String docid = "ID00003432";
			// login as RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Login as Rmu");
			docexp = new DocExplorerPage(driver);
			DocListPage doclist = new DocListPage(driver);
			// DocExploer to viewindocView Page
			baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
			this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
			baseClass.waitForElement(docexp.getDocIdColumnTextField());
			docexp.getDocIdColumnTextField().SendKeys(docid);
			doclist.getApplyFilter().waitAndClick(10);
			baseClass.waitTime(3);
			docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
			docexp.getDocExp_SelectAllDocs().Click();
			driver.waitForPageToBeReady();
			docexp.docExpViewInDocView();
			driver.waitForPageToBeReady();
			docView.getDocView_CodingFormlist().waitAndClick(5);
			docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
			baseClass.waitTime(3);
			docView.verifyCopyAndPasteRedacTextOnCommentBox();
			baseClass.waitTime(3);
			docView.getAddComment1().isElementAvailable(5);
			String Commenttext = docView.getAddComment1().getText();
			System.out.println(Commenttext);
			docView.getCodingFormSaveThisForm().waitAndClick(2);
			baseClass.stepInfo("Document saved successfully");

			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String tagname = "Tag" + Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();

			// create tag and folder
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

			SessionSearch sessionSearch = new SessionSearch(driver);
			int PureHit = sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolderExisting(foldername);

			ProductionPage page = new ProductionPage(driver);
			String beginningBates = page.getRandomNumber(2);
			int FirstFile = Integer.valueOf(beginningBates);
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
			int LastFile = PureHit + FirstFile;
			driver.waitForPageToBeReady();
			String home = System.getProperty("user.home");
			driver.waitForPageToBeReady();
			File TiffFile = new File(
					home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
			page.isfileisExists(TiffFile);
			page.OCR_Verification_In_Generated_Tiff_tess4jNotDisplayed(FirstFile, LastFile, prefixID, suffixID,Commenttext);
		}
		
		/**
		 * @author Krishna Date: Modified date:N/A Modified by: Description Verify
		 *         that if PAU impersonate as RMU,can create and generate the new
		 *         production
		 * 
		 */
		@Test(description = "RPMXCON-54869", enabled = true, groups = { "regression" })
		public void verifyPAUImpersonateRmuGenerateNewProduction() throws Exception {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-54869");
			baseClass
					.stepInfo("Verify that if PAU impersonate as RMU,can create and generate the new production");
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String tagname = "Tag" + Utility.dynamicNameAppender();
			String productionname = "p" + Utility.dynamicNameAppender();
			String prefixID = "p" + Utility.dynamicNameAppender();
			String suffixID = "S" + Utility.dynamicNameAppender();
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

			// Login as PAU
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("Login as PAU");
			baseClass.impersonatePAtoRMU();
			baseClass.stepInfo("Redirect to Rmu dashboard");
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
			baseClass.passedStep("Production generate completed successfully");
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
			loginPage.logout();
		}
		
		/**
		 * @author krishna ModifyDate:02/08/2022 RPMXCON-54307
		 * @throws Exception
		 * @Description To verify that when the SG is set to use SG-specific attributes
		 *              then Folder Propagation should happens using
		 *              EmailDuplicateDocIDs attribute.
		 * 
		 */
		@Test(description = "RPMXCON-54307", enabled = true, groups = { "regression" })
		public void verifySgAttributesFolderPropogationUsingEmailDuplicateDocIds() throws Exception {

			baseClass.stepInfo("Test case Id: RPMXCON-54307");
			baseClass.stepInfo(
					"To verify that when the SG is set to use SG-specific attributes then Folder Propagation should happens using EmailDuplicateDocIDs attribute.");
			SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
			SessionSearch sessionsearch = new SessionSearch(driver);
			String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
			String Foldername = "Test" + UtilityLog.dynamicNameAppender();

			// Login as PA
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("Login as PA");
			sgpage.navigateToSecurityGropusPageURL();
			baseClass.stepInfo("navigated to security group as expected");
			sgpage.createSecurityGroups(SGname);
			baseClass.stepInfo(SGname + "is created successfully");
			baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
			sgpage.getSG_GenerateEmailRadioButton(1).waitAndClick(3);

			// Release the doc to security group
			sessionsearch.basicContentSearch(Input.searchStringStar);
			sessionsearch.bulkRelease(SGname);
			baseClass.passedStep("  Document released to Selected.." + SGname);
			driver.waitForPageToBeReady();
			sgpage.navigateToSecurityGropusPageURL();
			driver.waitForPageToBeReady();
			baseClass.waitForElement(sgpage.getSecurityGroupList());
			sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
			baseClass.stepInfo(SGname + "..Selected as expected");
			driver.waitForPageToBeReady();
			baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
			sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(3);
			baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
			sgpage.getSG_GenerateEmailButton().waitAndClick(3);
			baseClass.passedStep("Generate button is clicked successfully");
			baseClass.waitForElement(sgpage.getYesButton());
			sgpage.getYesButton().waitAndClick(2);
			baseClass.waitTime(5);
			sgpage.getNotificationMessage(0);
			driver.waitForPageToBeReady();
			String Expected = sgpage.getSGBgCompletedTask().getText();
			System.out.println(Expected);
			baseClass.waitForElement(sgpage.getSecurityGroupBgViewAll());
			sgpage.getSecurityGroupBgViewAll().waitAndClick(3);
			baseClass.stepInfo("navigated to My Background task page");
			driver.waitForPageToBeReady();
			sgpage.getSgBgStatusDropDown().selectFromDropdown().selectByVisibleText("COMPLETED");
			baseClass.waitForElement(sgpage.getSgBgFilterBtn());
			sgpage.getSgBgFilterBtn().waitAndClick(2);
			driver.waitForPageToBeReady();
			baseClass.waitForElement(sgpage.getSGBgCompletedId());
			String Actual = sgpage.getSGBgCompletedId().getText();
			System.out.println(Actual);
			if (Expected.contains(Actual)) {
				baseClass.passedStep(Actual + ".. status is displayed as Completed successfully");

			} else {
				baseClass.failedMessage("failed");
			}
			loginPage.logout();

			// Login as RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Login as RMU");

			// Release the doc to security group
			sessionsearch.basicContentSearch(Input.testData1);
			sessionsearch.bulkFolderDuplicateEmailDocs(Foldername);
			baseClass.passedStep(
					Foldername + "...Selected documents email duplicates to selected folder is successfully associated");
			loginPage.logout();
		}

		
		


}

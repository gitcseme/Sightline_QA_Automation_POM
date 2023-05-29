package testScriptsSmoke;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.BatchPrintPage;
import pageFactory.Categorization;
import pageFactory.ClientsPage;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.Dashboard;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;

public class ProductionsandIngestions {

	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage  tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	Utility utility;
	DocExplorerPage docexp;
	DocViewPage docView;
	Dashboard dashBoard;
	DomainDashboard dash;
	ProductionPage page;
	Categorization categorize;
	ProjectFieldsPage projectFieldsPage;
	String helpMsg1 = "Is there some reason that review cannot determined?";
	String helpMsg2 = "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed";
	String helpMsg3 = "Does this doc contain some language besides what you can review?";
	String errorMsg = "If the document has technical issue cannot be reviewed,you must select reason why from the list above";
	String navigationConfirmationMsg ="This action will not save your edits, please save your changes before navigating away from Doc View. Do you want to still navigate away without saving your changes ?";
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}
	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		page = new ProductionPage(driver);
}
	@Test(description = "RPMXCON-53992",enabled = true, groups = { "regression" })
	public void verifyRmuAlbleToEditCF() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53992");
	    baseClass.stepInfo("To verify, As an RMU login, I will be able to Edit a Coding Form");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu3userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
	    baseClass.stepInfo("User will land in Coding Forms page");
	    String cfname = cf.getCodingFormTableValues(1, 1).getText().trim();
	    
	    cf.getEditClick().waitAndClick(5);
	    driver.waitForPageToBeReady();
	    soft.assertTrue(driver.getPageSource().contains("Create/Edit Coding Form"));
	    soft.assertTrue(driver.getPageSource().contains("Coding Form Editor"));
	    soft.assertTrue(baseClass.text(cfname).isElementAvailable(3));
	    baseClass.passedStep("After Edit option selected, User redirect to Create/Edit page with the selected coding form");
	    
	    
	    soft.assertAll();
	    baseClass.passedStep("verified, As an RMU login, I  able to Edit a Coding Form");
	    loginPage.logout();
	}
	
	@Test(description = "RPMXCON-54556",enabled = true, groups = { "regression" })
	public void verifyCFLogicValidationsWithRadioInDocViewParentWindow() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54556");
	    baseClass.stepInfo("Verify that Coding Form Preview (Radio Button) works Properly in the context of a document review in Parent Window in DocView");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.selectTagTypeByIndex("check item",1,3);	    
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5, defaultAction);	    
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");	    
	    codingForm.enterErrorAndHelpMsg(1, "No", helpMsg1,null);
	    codingForm.enterErrorAndHelpMsg(2, "No",helpMsg2,null);
	    codingForm.enterErrorAndHelpMsg(3, "No",helpMsg3,null);
	    baseClass.waitForElement(codingForm.getCodingForm_ErrorMsg(5));
	    codingForm.getCodingForm_ErrorMsg(5).SendKeys(errorMsg);
	    codingForm.enterErrorAndHelpMsg(4, "No","Responsiveness",null);
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingform);
	    sessionSearch = new SessionSearch(driver);
	    sessionSearch.basicContentSearch(Input.searchString2);
	    sessionSearch.ViewInDocView();
	    //validations
	    reusableDocView = new ReusableDocViewPage(driver);
	    codingForm.verifyCFlogicValidationsInDocViewPg("radio-group");
	    baseClass.stepInfo("All the validations of parent window are successfully verified");
	    reusableDocView.clickGearIconOpenCodingFormChildWindow();
	    String parentId = reusableDocView.switchTochildWindow();
	    codingForm.verifyCFlogicValidationsInDocViewPg("radio-group");
	    baseClass.stepInfo("All the validations of child window are successfully verified");
	    reusableDocView.childWindowToParentWindowSwitching(parentId);
	    
	    driver.Navigate().refresh();
	    baseClass.handleAlert();
		//delete codingform
	    baseClass.waitTime(5);
	    codingForm.assignCodingFormToSG("Default Project Coding Form");
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	@Test(description = "RPMXCON-54212", enabled = true, groups = { "regression" })
	public void verifyEyeToEyeWidget() throws Exception {

		// verified with Edge for the same in local
		baseClass.stepInfo("Test case Id:RPMXCON-54212 Dashboard Component Sprint 17");
		baseClass.stepInfo("To verify that details in End to End is displayed correctly.");
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		// Adding End to End Widget
		Dashboard dashBoard = new Dashboard(driver);
		dashBoard.AddNewWidgetToDashboard(Input.EndtoEnd);
		System.out.println("Released Count :" + dashBoard.releasedCount_EndToEnd().getText());
		System.out.println("Not Reviewed Count :" + dashBoard.notReviewedCount_EndToEnd().getText());
		System.out.println("Reviewed Count :" + dashBoard.reviewedCount_EndToEnd().getText());
		System.out.println("Total produced Count :" + dashBoard.totalProducedCount().getText());

		// Verifying Overall image is centered within the widget.
		baseClass.waitTime(4);
		String expected = "50%";
		String actual = dashBoard.checkIMageAtCenter();
		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		softAssertion.assertAll();
		loginPage.logout();
	}
	@Test(description = "RPMXCON-53154",enabled = true, groups = {"regression" })
	public void validateProjectGridColum() throws InterruptedException  {
		
		baseClass.stepInfo("Test case Id: RPMXCON-53154");
		baseClass.stepInfo("Validate project summary grid columns on Domain dashboard");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a da user :"+Input.da1userName);
		
		
		projectPage = new ProjectPage(driver);
		dash = new DomainDashboard(driver);
		
		String projectName = "AAA"+Utility.dynamicNameAppender();
		
		dash.waitForDomainDashBoardIsReady();
		if(!dash.isInactiveProjectAvailable()) {
			baseClass.clearBullHornNotification();
			dash.create_a_project_From_Domain(projectName);
			projectPage.waitTillProjectCreated();
			dash.naviageToDomainDashBoardPage();
			dash.deactivateProject(projectName);
		}
		String[] availableColm = {"PROJECT NAME","STATUS","DATA AS OF (UTC)",
				"DOCS PUBLISHED (#)","DOCS RELEASED (#)","CORPORATE CLIENT","CREATED DATE",
				"CREATED BY","TOTAL UTILIZED DISK SIZE (GB)"};
		
		dash.waitForDomainDashBoardIsReady();
		dash.makeDomainDashBoardTableDefaultOrder();
		for(String column:availableColm) {
		dash.isTitleIsAvailable(column);
		}
		dash.isActiveInactiveListed();
		
		baseClass.passedStep("Validate project summary grid columns on Domain dashboard");
		loginPage.logout();
	}
	@Test(description = "RPMXCON-52886", enabled = true, groups = { "regression" })
	public void verifyingDomainClient() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52886");
		baseClass.stepInfo("Validate new project creation of Domain client type by Domain Admin");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.selectdomain(Input.domainName);
		ProjectPage project = new ProjectPage(driver);
		UserManagement user = new UserManagement(driver);

		String ProjectName = "P" + Utility.dynamicNameAppender();
		String CorporateClient = "P" + Utility.dynamicNameAppender();
		String Firm = "P" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating new domain project");
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.getAddProjectBtn().waitAndClick(10);

		baseClass.stepInfo("verifying HCode and Settings tab is not displayed");
		baseClass.elementNotdisplayed(project.getHCode(), "HCode TextBox");
		baseClass.elementNotdisplayed(project.getAddProject_SettingsTab(), "Settings Tab");

		baseClass.stepInfo("verifying Clientname and clienttype is auto populated and disabled");
		baseClass.elementDisplayCheck(project.getClientTypeDisableCheck());
		String option = baseClass.getCurrentDropdownValue(project.getSelectEntityType());
		baseClass.textCompareEquals(option, "Domain", "Client type is auto populated","Client type is not auto populated");

		baseClass.elementDisplayCheck(project.getClientNameDisable());
		String ClientName = baseClass.getCurrentDropdownValue(project.getSelectEntity());
		baseClass.textCompareNotEquals(ClientName, "--Select--", "Client name is auto populated","Clientname is not auto populated");
				

		baseClass.stepInfo("verifying Database and workspace is autopopulated for domain");
		baseClass.elementDisplayCheck(project.getDBServerDisable());
		String DBServer = baseClass.getCurrentDropdownValue(project.getProjectDBServerDropdown());
		baseClass.textCompareNotEquals(DBServer, "--Select--", "Client name is auto populated","Clientname is not auto populated");
		baseClass.elementDisplayCheck(project.sizeOfProjectDataBaseDisable());

		baseClass.stepInfo("verifying ingestion,project and project folders are autopopulated with '\'");
		project.verifyingFolderName(project.getIngestionFolder());
		String ProjectFolder = project.verifyingFolderName(project.getProjectFolder());
		project.verifyingFolderName(project.getProductionFolder());
		user.navigateToUsersPAge();
		
		baseClass.stepInfo("Switching to another domain");
		baseClass.switchDomain();

		project.navigateToProductionPage();
		project.getAddProjectBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		String ClientNameInSwitchedDomain = baseClass.getCurrentDropdownValue(project.getSelectEntity());
		baseClass.textCompareNotEquals(ClientName, ClientNameInSwitchedDomain, "Clientname is refreshed for switched domain",
				"Clientname is not refreshed");
		String ProjectFolderInSwitchedDomain = project.getProjectFolder().GetAttribute("value");
		baseClass.textCompareNotEquals(ProjectFolderInSwitchedDomain, ProjectFolder,
				"Database and Workspace is changed as per the selected domain",
				" Database and Workspace is not refreshed for selected domain");
		
        baseClass.stepInfo("Save the project and verifying the notification");
		project.navigateToProductionPage();
		project.CreatProjectInDA(ProjectName);
		project.filterTheProject(ProjectName);
		baseClass.waitTime(3);
		int n = baseClass.getIndex(project.getProjectTableHeader(), "NAME");
		String Project = project.getColumValue(n).getText().trim();
		baseClass.waitTime(2);
		baseClass.textCompareEquals(Project, ProjectName, "Newly created project is available under selected domain",
				"newly created project is not displayed");

		project.getEditBtn().waitAndClick(10);
		project.getCorpClientTextBox().SendKeys(CorporateClient);
		project.getFirmTextBox().SendKeys(Firm);
		baseClass.waitForElement(project.getButtonSaveProject());
		project.getButtonSaveProject().waitAndClick(10);
		baseClass.VerifySuccessMessage("Project updated successfully");

		project.editProject(ProjectName);
		String CorpName = project.getCorpClientTextBox().GetAttribute("value");
		baseClass.textCompareEquals(CorpName, CorporateClient, "Corp name is updated with alpha numeric"," Corpname is not updated");

		String FirmName = project.getFirmTextBox().GetAttribute("value");
		baseClass.textCompareEquals(Firm, FirmName, "Firm name is updated with alpha numeric"," Firm Name is not updated");

		loginPage.logout();

	}
	
	@Test(description = "RPMXCON-50686", enabled = true, groups = { "regression" })
	public void verifyGeneratePDF_MultiPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50686");
		baseClass.stepInfo("Verify If TIFF is produced in the production which is selected as the basis for export then in export user can select 'Generate PDF',export should complete sucessfully");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateTagwithClassification(tagName,Input.tagNamePrev);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName);

		baseClass = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		System.out.println("created production "+ productionname);
		page.fillingDATSection();
		page.fillingPDFWithMultiPage(tagName);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.waitTime(2);
		
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		
		BatchPrintPage batch= new BatchPrintPage(driver);
		String extractFile = batch.extractFile(name+".zip");
		System.out.println(extractFile);

		driver.waitForPageToBeReady();

		page.verifyDownloadPDFFileCount(extractFile, false);
		loginPage.logout();
	}
	@Test(description = "RPMXCON-63078", enabled = true)
	public void verifyingTheGeneratedFileType() throws Exception {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id:RPMXCON-63078- Production component");
		baseClass.stepInfo(
				"Verify that production should be generated successfully with default enabled native placeholder under TIFF section");
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.addNewSearch();
		sessionSearch.SearchMetaData("DocFileType", "Spreadsheet");
		sessionSearch.addPureHit();

		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(3);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
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
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File Native = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		System.out.println(Native);
		if (Native.exists()) {
			baseClass.passedStep("Native file are generated correctly : " + prefixID + beginningBates + suffixID + ".xls");
		} else {
			baseClass.failedStep("verification failed");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}
	@Test(description = "RPMXCON-47066", enabled = true, groups = { "regression" })
	public void verifyFilterFieldsByClickingApplyAndEnterButton() {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47066");
		baseClass.stepInfo("Verify that after entering 'Filter Fields By' and on click of 'Apply' should search Project Fields.");
		
		String textField = Input.fieldByValue;
		baseClass.stepInfo("Step 1: Login as Project Admin");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password, Input.projectName);
		
		
		baseClass.stepInfo("Step 2: Go to Manage > Project Fields");
		projectFieldsPage = new ProjectFieldsPage(driver);
		projectFieldsPage.navigateToProjectFieldsPage();
		baseClass.passedStep("Navigated to Project Field Page successfully");
		
		baseClass.stepInfo("Step 3: Enter the text in 'Filter Fields By' and click on Apply");
		projectFieldsPage.applyFilterByFilterName(textField);
		projectFieldsPage.validateFilterFieldsByContainsValueInTheGrid(projectFieldsPage.getProjectGridFieldNameValue(textField),textField);
		
		baseClass.stepInfo("Step 4: Enter the text in 'Filter Fields By' and hit enter key");
		projectFieldsPage.enterFilterFieldValueAndClickEnter(textField);
		projectFieldsPage.validateFilterFieldsByContainsValueInTheGrid(projectFieldsPage.getProjectGridFieldNameValue(textField),textField);
		
		loginPage.logout();
		
	}
	@Test(description = "RPMXCON-55607", enabled = true, groups = { "regression" })
	public void validatingEditFunctionalInProjectField() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55607");
		baseClass.stepInfo("To verify Edit functionality for 'Project Field'.");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields = new ProjectFieldsPage(driver);
		String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();
		String editDesc = "edit" + Utility.dynamicNameAppender();

		// Login as a PA
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa3userName + "'");

		// Custom Field created with INT DataType
		baseClass.stepInfo("Adding new project fields");
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");
		fields.applyFilterByFilterName(projectFieldINT);

		// validating the edit functionality
		fields.editprojectFieldFieldDescription(projectFieldINT, editDesc);
		baseClass.passedStep("Edit functionality for project fields, working properly");

		// verifying the updated value
		fields.applyFilterByFilterName(projectFieldINT);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(fields.getFieldNameEdititButton(projectFieldINT));
		fields.getFieldNameEdititButton(projectFieldINT).waitAndClick(10);
		baseClass.waitForElement(fields.getFieldDescriptionTextArea());
		String updateValue = fields.getFieldDescriptionTextArea().getText();
		softAssertion.assertEquals(updateValue, editDesc);
		softAssertion.assertAll();
		baseClass.stepInfo("updated value saved successfully");

		loginPage.logout();

	}
	@Test(description = "RPMXCON-55921", enabled = true, groups = { "regression" })
	public void verifyingMasterDateInDoclistPage() throws Exception {
		baseClass.stepInfo("RPMXCON-55921 -Project Field");
		baseClass.stepInfo(
				"Verify that all the default fields are correctly released to a security group whether it is Default Security group or user created security group");

		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String ExpectedString = "MASTERDATE";

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");

		baseClass.stepInfo("Creating new Security Group");
		sg.createSecurityGroups(securityGroup);
		System.out.println(securityGroup);

		baseClass.stepInfo("Releasing the document to " + securityGroup + " in search page");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkRelease(securityGroup);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Validting the default field in Default security Group");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);

		baseClass.ValidateElementCollection_Presence(doc.getHeaderText(), "fields in doclist");
		List<String> CompareString = baseClass.availableListofElements(doc.getHeaderText());
		if (CompareString.contains(ExpectedString)) {
			baseClass.passedStep("" + ExpectedString + " is Displayed as expecetd");
		} else {
			baseClass.failedStep("" + ExpectedString + " is Displayed as expecetd");
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		baseClass.stepInfo("Assigning newly created securityGroup to RMU");
		baseClass.SelectSecurityGrp(Input.rmu3userName, securityGroup);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Validting the default field in " + securityGroup + "");
		baseClass.selectsecuritygroup(securityGroup);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		List<String> ListString = baseClass.availableListofElements(doc.getHeaderText());
		if (ListString.contains(ExpectedString)) {
			baseClass.passedStep("" + ExpectedString + " is Displayed as expecetd");
		} else {
			baseClass.failedStep("" + ExpectedString + " is Displayed as expecetd");
		}
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();

	}
	
	@Test(description = "RPMXCON-55570", enabled = true, groups = { "regression" })
	public void verifyUserCanDeleteClient() throws Exception {
		ProjectPage projectPage = new ProjectPage(driver);
		ClientsPage client = new ClientsPage(driver);
		SoftAssert asserts = new SoftAssert();

		String clientname = "" + Utility.dynamicNameAppender();
		String shrtType = "" + Utility.randomCharacterAppender(4);

		baseClass.stepInfo("RPMXCON-55570");
		baseClass.stepInfo("To verify that user can delete the Client.");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		projectPage.navigateToClientFromHomePage();
		projectPage.addNewClient(clientname, shrtType, "Domain");

		client.filterClient(clientname);
		baseClass.waitForElement(client.getClientDeleteBtn(clientname));
		asserts.assertTrue(client.getClientDeleteBtn(clientname).isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo(clientname + " Client Created Successfully");
		client.deleteClinet(clientname);
		client.filterClient(clientname);
		baseClass.waitForElement(client.getClientDeleteBtn(clientname));
		asserts.assertFalse(client.getClientDeleteBtn(clientname).isElementAvailable(2));
		asserts.assertAll();
		baseClass.stepInfo(clientname + " Client Deleted Successfully");
		baseClass.passedStep("Verified - that user can delete the Client.");
		loginPage.logout();
	}
	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu3userName, Input.rmu3password, "RMU" },
				{ Input.pa3userName, Input.pa3password, "PA" } };
		return users;
	}

	@Test(description = "RPMXCON-54138", enabled = true, groups = { "regression" })
	public void verifyDocsFromCategorizeToDoclist()
			throws InterruptedException {
		String folderName = "FOLDER" + Utility.dynamicNameAppender();

		Categorization categorize = new Categorization(driver);
		sessionSearch = new SessionSearch(driver);
		DocListPage doclistPage = new DocListPage(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);

		baseClass.stepInfo("RPMXC0N-54138 Proview");
		baseClass.stepInfo("To verify that user can view the total no of documents from Categorization to doc list.");

		// basic Content search
		sessionSearch.basicContentSearch(Input.testData1);

		// perform Bulk Folder
		sessionSearch.bulkFolder(folderName);

		// RUN CATEGORIZATION
		categorize.navigateToCategorizePage();
		categorize.CategorizationFunctionalityVerification(Input.securityGroup, folderName, "SG");

		// navigate to doclist
		categorize.ViewInDocLIst();
		String docCount = doclistPage.verifyingDocCount();
		baseClass.textCompareNotEquals(docCount, "0", "Documents Displayed in doclistPage : " + docCount,
				"Documentts Are Not Dispalyed");

		// verify Selected Docs In Categorize page
		String currentUrl = driver.getUrl();
		String passMsg = "Navigated to Categorization page ";
		baseClass.compareTextViaContains(currentUrl, "/Proview/Proview", passMsg, "");
		baseClass.ValidateElement_Presence(categorize.getSelectedCorpusToAnalyze(folderName),
				"Selected folder : " + folderName);

		String resultCount = categorize.getDocCount().getText();
		baseClass.textCompareEquals(resultCount, docCount,
				"Previously Selected Docs Remains Selected With DocCount : " + resultCount,
				"Selected Docs are not Displayed");

		loginPage.logout();
	}
	@Test(description = "RPMXCON-54140", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyResultForTrainingSetAsTagAndFold(String username, String password, String userRole)
			throws InterruptedException {
		String folderName = "Folder" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();

		// Login as User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + userRole);

		baseClass.stepInfo("RPMXC0N-54140  Proview");
		baseClass.stepInfo(
				"To verify that Proview result displays if training set select as Tag and documents to be analyzed as Folder.");
		sessionSearch = new SessionSearch(driver);
		categorize = new Categorization(driver);
		// configure query & Bulk assign
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTag(tagName);
		sessionSearch.bulkFolderWithOutHitADD(folderName);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Tag in Training Set
		categorize.fillingTrainingSetSection("Tag", tagName, null, null);

		// select doc to be analyzed from Folder in Corpus Section
		categorize.fillingStep2CorpusTab("Folder", folderName, null, true);

		// Run & verify result Displayed
		categorize.runCategorization("YES");

		// logout
		loginPage.logout();
	}
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
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
}

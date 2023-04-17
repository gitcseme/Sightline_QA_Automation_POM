package sightline.docview;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;


public class DocView_Regression7 {

	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions docViewRedact;
	Input ip;
	ProductionPage page;
	TagsAndFoldersPage tagsAndFolderPage;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	SessionSearch sessionsearch;
	DocExplorerPage docExp;
	KeywordPage keywordPage;
	SavedSearch savedSearch;
	BatchRedactionPage bacth;
	ReusableDocViewPage reusableDocView;
	SoftAssert softAssertion;

	String namesg2 = null;
	String namesg3 = null;
	String AnnotationLayerNew = null;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		Input in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
	}


	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52236 - Verify that Project Admin prints the document without redaction
	 * which is released to two different security groups and redaction added in
	 * first security group only with different annotation layer and shared
	 * redaction tags. Description : Verify that Project Admin prints the document
	 * without redaction which is released to two different security groups and
	 * redaction added in first security group only with different annotation layer
	 * and shared redaction tags.
	 */
	@Test(description = "RPMXCON-52236", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifPrintDocumentPARedactionForFirstSecurityGroup() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52236");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		 
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		driver.waitForPageToBeReady();

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.navigateToSecurityGropusPageURL();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew2);
		
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.selectSecurityGroup(namesg2);
//		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		
		securityGroupsPage.selectSecurityGroup(namesg3);
//		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew2);
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew2);
		//baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		
		securityGroupsPage.selectSecurityGroup(namesg2);
//		securityGroupsPage.clickOnReductionTagAndSelectReduction(Redactiontag1);
		securityGroupsPage.assignRedactionTagtoSG(Redactiontag1);
		securityGroupsPage.assignRedactionTagtoSG("Default Redaction Tag");
//		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		
		securityGroupsPage.selectSecurityGroup(namesg3);
//		securityGroupsPage.clickOnReductionTagAndSelectReduction(Redactiontag1);
		securityGroupsPage.assignRedactionTagtoSG(Redactiontag1);
		securityGroupsPage.assignRedactionTagtoSG("Default Redaction Tag");
		//baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, Input.rmu2userName);
		docViewRedact.assignAccesstoSGs(namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag1);

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();
		docViewMetaDataPage.verifyPrintOnDocView();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52237 - Verify that Project Admin prints the document without redaction
	 * which is released to two different security groups and redaction added in
	 * both security groups with different annotation layer. Description : Verify
	 * that Project Admin prints the document without redaction which is released to
	 * two different security groups and redaction added in both security groups
	 * with different annotation layer.
	 */
	@Test(description = "RPMXCON-52237", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifPrintDocumentWithoutRedactionAsPA() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52237");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in both security groups with different annotation layer. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew2);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew2);
		//baseClass.CloseSuccessMsgpopup();
		
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		securityGroupsPage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();\
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		securityGroupsPage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}
	
	/**
	 * Author : Steffy Created date: NA Modified date: NA Modified by:NA TestCase id
	 * : 51052 - Verify RMU/Reviewer can see the annotations of document on doc view
	 * page in different security group if the same annotation layer is mapped to
	 * different security groups
	 * stabilization done
	 */
	@Test(description = "RPMXCON-51052", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyAnnotationAcrossSecurityGroupsWhnSameAnnotationLayerIsMapped() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		sessionsearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51052");
		baseClass.stepInfo(
				"Verify RMU/Reviewer can see the annotations of document on doc view page in different security group if the same annotation layer is mapped to different security groups");
		utility = new Utility(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User : " + Input.pa1userName + " to create prerequisite SG's and annotation layer");

		baseClass.stepInfo("Creation of Prerequisites");
		baseClass.stepInfo("Creation of two different security groups");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		baseClass.stepInfo("Creation of annotation layer");
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		baseClass.stepInfo("Sharing same annotation layer to different security groups");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();

		baseClass.stepInfo("Docs are released to both security groups");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);

		baseClass.stepInfo("Access has been given to RMU and Rev to these security groups");
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rev1userName);

		loginPage.logout();

		baseClass.stepInfo("Adding annotation to document by RMU user as a prequisite");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();
		driver.waitForPageToBeReady();

		loginPage.logout();
		baseClass.stepInfo("Prerequisite creation is completed successfully");

		baseClass.stepInfo(
				"Login as RMU user , Selecting the first SG and verify whether the annotation is displayed in the document");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyAddedAnnotationToDocument(0);
		loginPage.logout();

		baseClass.stepInfo(
				"Login as RMU user , Selecting the second SG and verify whether the annotation is displayed in the same document");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyAddedAnnotationToDocument(0);
		loginPage.logout();

		baseClass.stepInfo(
				"Login as REV user , Selecting the first SG and verify whether the annotation is displayed in the document");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyAddedAnnotationToDocument(0);
		loginPage.logout();

		baseClass.stepInfo(
				"Login as REV user , Selecting the second SG and verify whether the annotation is displayed in the same document");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyAddedAnnotationToDocument(0);
	
		baseClass.stepInfo("Log out");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Select security group for REVIEWER");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("Reviewer Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}
	

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51447 - Verify that annotation should be saved for the document when
	 * same annotation layer is mapped to different security groups. Description :
	 * Verify that annotation should be saved for the document when same annotation
	 * layer is mapped to different security groups.
	 */
	@Test(description = "RPMXCON-51447", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyAnnotationSavedWithDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51447");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that annotation should be saved for the document when same annotation layer is mapped to different security groups. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Get Annotation Layer Count On Current Document");
		int layers = docView.getAnnotationLayerCountOnCurrentDocument();

		baseClass.stepInfo("Perform non audio annotation");
		docView.performNonAudioAnnotation();

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Verify Annotation Added To Document");
		docView.verifyAnnotationAddedToDocument(layers);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52244 - Verify RMU prints the document without redaction which is
	 * released to two different security groups and redaction added in first
	 * security group only with different annotation layer and shared redaction
	 * tags. Description : Verify RMU prints the document without redaction which is
	 * released to two different security groups and redaction added in first
	 * security group only with different annotation layer and shared redaction
	 * tags.
	 */
	@Test(description = "RPMXCON-52244", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyDocumentPrintsDifferentRedactionsBySecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52244");
		utility = new Utility(driver);
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag2 = Input.randomText + Utility.dynamicNameAppender();
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify RMU prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		
		baseClass.stepInfo("Navigate to security groups page");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		
		baseClass.stepInfo("Navigate to security groups page");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		redactionpage.manageRedactionTagsPage(Redactiontag2);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		docViewRedact.performThisPageRedaction(Redactiontag1);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag2);

		// Releasing Docs to SGs
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		driver.waitForPageToBeReady();
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}

	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52245 - Verify RMU prints the document with redaction considering the
	 * security group which is released to two different security groups and
	 * redaction added in both security groups with different annotation layer.
	 * Description : Verify the security group which is released to two different
	 * security groups and redaction added in both security groups with different
	 * annotation layer.
	 */
	@Test(description = "RPMXCON-52245", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyDocumentPrintsRedactionBySecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52245");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify RMU prints the document with redaction considering the security group which is released to two different security groups and redaction added in both security groups with different annotation layer. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		
		baseClass.stepInfo("Navigate to security groups page");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		
		baseClass.stepInfo("Navigate to security groups page");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();  
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);

		// Releasing Docs to SGs
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		driver.waitForPageToBeReady();
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52246 -Verify RMU prints the document with redaction which is released
	 * to two different security groups and redaction added in first security group
	 * only with shared annotation layer. Description : Verify RMU prints the
	 * document with redaction which is released to two different security groups
	 * and redaction added in first security group only with shared annotation
	 * layer.
	 */
	@Test(description = "RPMXCON-52246", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyDocumentPrintsRedactionsReleasedToSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52246");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify RMU prints the document with redaction which is released to two different security groups and redaction added in first security group only with shared annotation layer. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		
		baseClass.stepInfo("Navigate to security groups page");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		
		baseClass.stepInfo("Navigate to security groups page");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		
		baseClass.stepInfo("Navigate to session search url");
		sessionsearch.navigateToSessionSearchPageURL();
		
		sessionsearch.addDocsMetCriteriaToActionBoard();
		// Print is not working properly
		docViewMetaDataPage.verifyPrintOnDocView();

		driver.waitForPageToBeReady();
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}

	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51449 - Verify that after editing the annotation should be saved for the
	 * document when same annotation layer is mapped to different security groups.
	 * Description : Verify that after editing the annotation should be saved for
	 * the document when same annotation layer is mapped to different security
	 * groups.
	 */
	@Test(description = "RPMXCON-51449", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyEditAnnotationSavedWithDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51449");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after editing the annotation should be saved for the document when same annotation layer is mapped to different security groups ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Editing annotation layer of current document.");
		docView.editAnnotationLayerOfCurrentDocument();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52247 - Verify RMU prints the document with redaction which is released
	 * to two different security groups and redaction added in both security groups
	 * with shared annotation layer and redaction tags. Description : Verify RMU
	 * prints the document with redaction which is released to two different
	 * security groups and redaction added in both security groups with shared
	 * annotation layer and redaction tags.
	 */
	@Test(description = "RPMXCON-52247", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyPrintsRedactionsReleasedToDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52247");
		utility = new Utility(driver);
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag2 = Input.randomText + Utility.dynamicNameAppender();
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify RMU prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		
		baseClass.stepInfo("Navigate to security groups page");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		
		driver.Navigate().refresh();
		
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		
		baseClass.stepInfo("Navigate to security groups page");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		RedactionPage redactionpage = new RedactionPage(driver);
		
		baseClass.stepInfo("Navigate To Redactions Page URL");
		redactionpage.navigateToRedactionsPageURL();
		
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		redactionpage.manageRedactionTagsPage(Redactiontag2);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag1);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag2);

		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		driver.waitForPageToBeReady();
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}
	
	
	/**
	 * Author : Steffy Created date: NA Modified date: NA Modified by:NA TestCase id
	 * : 48251 - Same doc in different security groups: Verify without shared
	 * annotation layer and shared redaction tags, no redaction info (coordinates,
	 * tags and history) shown in the doc in 2nd sec group
	 * stabilization done
	 */
	@Test(description = "RPMXCON-48251",  enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyRedactionInfoAcrossSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew1 = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48251");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
   
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"Same doc in different security groups: Verify without shared annotation layer and shared redaction tags, no redaction info (coordinates, tags and history) shown in the doc in 2nd sec group");

		// creating two new security groups and adding annotation layer
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew1);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew1);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rev1userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Perform Redaction in SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.verifyWhetherRedactionIsSaved(false);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew1);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Select security group for REVIEWER");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("Reviewer Assigned to Default Security Group");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51546 - Verify that when two different users under different security
	 * group with different annotation layer adds/edit/delete reviewer remark to the
	 * same record successfully. Description : Verify that when two different users
	 * under different security group with different annotation layer
	 * adds/edit/delete reviewer remark to the same record successfully.
	 */
	@Test(description = "RPMXCON-51546", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyRemarkAddedInSGRefectedToOtherSGWithDiffAnnotation() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51546");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that when two different users under different security group with different annotation layer adds reviewer remark to the same record successfully ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew2);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
//		Added on_12_04
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		
		
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew2);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
	
		securityGroupsPage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		securityGroupsPage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		
		securityGroupsPage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		securityGroupsPage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, Input.rmu2userName);
		docViewRedact.assignAccesstoSGs(namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		// Add Remark
		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
//		Added on 
		baseClass.waitTime(3);
		docView.addRemarkToNonAudioDocument(5, 10, remark);
		docView.verifyRemarkIsAdded(remark); 

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);

		// Switch To SG1 for verifying edited remark is added
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		driver.waitForPageToBeReady();
		baseClass.waitTime(4);
		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		loginPage.logout();
	}
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51450 - Verify that after editing the remark should be saved for the
	 * document when same annotation layer is mapped to different security groups.
	 * Description : Verify that after editing the remark should be saved for the
	 * document when same annotation layer is mapped to different security groups.
	 */
	@Test(description = "RPMXCON-51450", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyRemarkAddedInSGRefectedToOtherSGWithSameAnnotation() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51450");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after editing the remark should be saved for the document when same annotation layer is mapped to different security groups. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
//		Added on 12
		securityGroupsPage.selectSecurityGroup(namesg2);
	
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);

		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();
		
		
		docViewRedact.assignAccesstoSGs(namesg2, Input.rmu2userName);
		docViewRedact.assignAccesstoSGs(namesg3, Input.rmu2userName);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		// Add Remark
		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
		
//		docView.addRemarkByText(remark);
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		docView.addRemarkToNonAudioDocument(5, 10, remark);
		docView.verifyRemarkIsAdded(remark);

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);
		docView.editRemark(remark);

		// Switch To SG1 for verifying edited remark is added
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52256 -Verify that after deleting the annotation layer by PA user under
	 * 'AllSecurityGroups' should not present respective
	 * redactions/annotations/remarks on documents. Description : Verify that after
	 * deleting the annotation layer by PA user under 'AllSecurityGroups' should not
	 * present respective redactions/annotations/remarks on documents.
	 */
	@Test(description = "RPMXCON-52256", enabled = true,alwaysRun = true, groups = { "regression" })
	public void verifyRemarkOnSecucityGroupByAnnotationlayerDeleted() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52256");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after deleting the annotation layer by PA user under 'AllSecurityGroups' should not present respective redactions/annotations/remarks on documents ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		//baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		//baseClass.CloseSuccessMsgpopup();

		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		//baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.telecom);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Perfrompage redaction");
		docView.nonAudioPageRedaction(Input.defaultRedactionTag);

		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		baseClass.stepInfo("Verify redaction,annotation and remark buttons are not displayed.");
		docView.verifyRedactionAnnotaionAndRemarkButtonsAreDisabled();

		baseClass.stepInfo("Verify applied redaction and annotation is not displayed.");
		docView.verifyAppliedRedactionAndAnnotationIsNotDisplayed();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		if (!(namesg2== null)) {
		securityGroupsPage.deleteSecurityGroups(namesg2);
		}
		if (!(namesg3== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg3);
		}
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
	}

	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
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
	UtilityLog.info("Executed Docview Regression 3_2 class successfully");		
	}

}

package testScriptsRegressionSprint21;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import junit.framework.Assert;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingForm_Regression {

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
	TagsAndFoldersPage tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	DocExplorerPage docExplorerPage;

	List<String> cfName = null;
	String codingform = "CFA" + Utility.dynamicNameAppender();
	String cfTwo = "CFB" + Utility.dynamicNameAppender();
	List<String> allCf;

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {

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

	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify the character limit for the additional field of Radio
	 *              Group and Check Group
	 */
	@Test(description = "RPMXCON-54303", enabled = true, groups = { "regression" })
	public void verifyCharacterLimit() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54303");
		baseClass.stepInfo("Verify the character limit for the additional field of Radio Group and Check Group");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		
		// passing 500 character for radio instrument
		boolean radio=codingForm.getCodingForm_HelpMsg(0).isElementAvailable(2);
		softAssertion.assertTrue(radio);
		baseClass.stepInfo("Instrumental text present for radio group");
		String radioText=baseClass.passingCharacterBasedOnSize(500);
		codingForm.getCodingForm_HelpMsg(0).SendKeys(radioText);
		String radioOutput=codingForm.getCodingForm_HelpMsg(0).GetAttribute("value");
		int size=radioOutput.length();
		softAssertion.assertEquals(Integer.toString(size), "500");
		baseClass.passedStep("500 character are able to pass inside radio instrument text box");
		
		// passing 500 character for check instrument
		boolean check=codingForm.getCodingForm_HelpMsg(1).isElementAvailable(2);
		softAssertion.assertTrue(check);
		baseClass.stepInfo("Instrumental text present for check group");
		String checkText=baseClass.passingCharacterBasedOnSize(500);
		codingForm.getCodingForm_HelpMsg(1).SendKeys(checkText);
		String checkOutput=codingForm.getCodingForm_HelpMsg(1).GetAttribute("value");
		int sizeTwo=checkOutput.length();
		softAssertion.assertEquals(Integer.toString(sizeTwo), "500");
		baseClass.passedStep("500 character are able to pass inside check instrument text box");
		// Assertion
		softAssertion.assertAll();
		loginPage.logout();
	}

	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that additional field to both Radio Groups and Check Groups 
	 *                should appear in the Coding Form
	 */
	@Test(description = "RPMXCON-54302", enabled = true, groups = { "regression" })
	public void verifyInstrumentText() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54302");
		baseClass.stepInfo("Verify that additional field to both Radio Groups and Check Groups "
				+ "should appear in the Coding Form");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		
		// validating the additional fields
		boolean radio=codingForm.getCodingForm_HelpMsg(0).isElementAvailable(2);
		softAssertion.assertTrue(radio);
		baseClass.stepInfo("Instrumental text present for radio group");
		boolean check=codingForm.getCodingForm_HelpMsg(1).isElementAvailable(2);
		softAssertion.assertTrue(check);
		baseClass.stepInfo("Instrumental text present for check group");
		baseClass.passedStep("Additional fields are displayed for both radio and check group");
		softAssertion.assertAll();
		loginPage.logout();
	}

	
	/**
	 * @Author :Indium-Baskar
	 * @Description : To verify as an RMU login, user will be able to see 100 records on
	 *                selecting 100 from drop-down in manage coding form page
	 */
	@Test(description = "RPMXCON-54020", enabled = true, groups = { "regression" })
	public void verify100RecordsInCf() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54020");
		baseClass.stepInfo("To verify as an RMU login, user will be able to see 100 records on "
				+ "selecting 100 from drop-down in manage coding form page");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		allCf = new LinkedList<String>();
		// creating codingform
		for (int i = 1; i <=100 ; i++) {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.addNewCodingFormButton();
			String name=codingForm.passingCodingFormName("cf" + Utility.dynamicNameAppender());
			allCf.add(name);
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
		}
		// validating 100 records
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		codingForm.getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
		baseClass.stepInfo("Selecting the dropdown as 100");
		baseClass.waitTime(5);
		int count=codingForm.getCFnames().size();
		softAssertion.assertEquals(Integer.toString(count), "100");
		baseClass.passedStep("Manage codingform screen 100 records are displayed when user selected dropdown as 100");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : To verify as an RMU login, user will be able to see 25 records 
	 *                on selecting 25 from drop-down in manage coding form page
	 */
	@Test(description = "RPMXCON-54018", enabled = true, groups = { "regression" })
	public void verify25RecordsInCf() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54018");
		baseClass.stepInfo("To verify as an RMU login, user will be able to see 25 records "
				+ "on selecting 25 from drop-down in manage coding form page");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// validating 25 records
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		codingForm.getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("25");
		baseClass.stepInfo("Selecting the dropdown as 25");
		baseClass.waitTime(5);
		int count=codingForm.getCFnames().size();
		softAssertion.assertEquals(Integer.toString(count), "25");
		baseClass.passedStep("Manage codingform screen 25 records are displayed when user selected dropdown as 25");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : To verify as an RMU login, user will be able to see 50 records on selecting 
	 *                50 from drop-down in manage coding form page
	 */
	@Test(description = "RPMXCON-54019", enabled = true, groups = { "regression" })
	public void verify50RecordsInCf() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54019");
		baseClass.stepInfo("To verify as an RMU login, user will be able to see 50 records on "
				+ "selecting 50 from drop-down in manage coding form page");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// validating 25 records
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		codingForm.getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("50");
		baseClass.stepInfo("Selecting the dropdown as 50");
		baseClass.waitTime(5);
		int count=codingForm.getCFnames().size();
		softAssertion.assertEquals(Integer.toString(count), "50");
		baseClass.passedStep("Manage codingform screen 50 records are displayed when user selected dropdown as 50");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : Verify that radio group association should be saved for the Tag when coding form is saved
	 */
	@Test(description = "RPMXCON-54500", enabled = true, groups = { "regression" })
	public void verifyRadioGrpAssociationOfSavedCF() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54500");
		baseClass.stepInfo("Verify that radio group association should be saved for the Tag when coding form is saved");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag"+Utility.dynamicNameAppender();
		int index = 1;
		String expectedRadioGrp = "radiogroup_"+index+""; 
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		// Create tag
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		baseClass.stepInfo("Created the tag sucessfully");
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("radio item",index,0);
		baseClass.stepInfo("Radio group is associated to the created tag");
		codingForm.saveCodingForm();
		baseClass.stepInfo("Coding form saved successfully");
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		baseClass.waitForElement(codingForm.getTagGroupValues(index));
		String actualRadioGroup = codingForm.getTagGroupValues(index).GetAttribute("systemcontrolname");
		softAssertion.assertEquals(expectedRadioGrp, actualRadioGroup);
		softAssertion.assertAll();
		baseClass.passedStep("The radio group associated in tag is successfully reflected after saving the coding form");
		codingForm.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.deleteAllTags(tagname);
		loginPage.logout();
	}
	/**
	 * @Author :Iyappan.Kasinathan
	 * @Description : Verify that radio group association should be saved for the Tag when coding form is edited
	 */
	@Test(description = "RPMXCON-54501", enabled = true, groups = { "regression" })
	public void verifyRadioGrpAssociationOfEditedCF() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54501");
		baseClass.stepInfo("Verify that radio group association should be saved for the Tag when coding form is edited");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag"+Utility.dynamicNameAppender();
		int index = 1;
		String expectedRadioGrp = "radiogroup_"+index+"";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		baseClass.stepInfo("Created the tag sucessfully");
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("radio item",index,0);
		baseClass.stepInfo("Radio group is associated to the created tag");
		codingForm.saveCodingForm();
		baseClass.stepInfo("Coding form saved successfully");
		//Edit the existing coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		//verify the radio group associated with tag
		codingForm.editCodingForm(codingform);
		baseClass.stepInfo("Edited the coding form sucessfully");
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		baseClass.waitForElement(codingForm.getTagGroupValues(index));
		String actualRadioGroup = codingForm.getTagGroupValues(index).GetAttribute("systemcontrolname");
		softAssertion.assertEquals(expectedRadioGrp, actualRadioGroup);
		softAssertion.assertAll();
		baseClass.passedStep("The radio group associated in tag is successfully reflected after editing the saved coding form");
		//Deleting the tag and cf
		codingForm.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.deleteAllTags(tagname);
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that "Save Confirmation" message appears on "Coding Form" Screen
	 */
	@Test(description = "RPMXCON-54007", enabled = true, groups = { "regression" })
	public void verifySaveConfrmMsg() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54007");
		baseClass.stepInfo("Verify that \"Save Confirmation\" message appears on \"Coding Form\" Screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		tagsAndFoldersPage=new TagsAndFoldersPage(driver);
		String tag="cf" + Utility.dynamicNameAppender();
		String cfName="cfName" + Utility.dynamicNameAppender();
		String fdName="fname" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		tagsAndFoldersPage.CreateTag(tag, Input.securityGroup);
		// creating
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.addNewCodingFormButton();
		codingForm.basedOnCreatingNewObject(tag, null, null, "tag");
		codingForm.addcodingFormAddButton();
		codingForm.passingCodingFormName(cfName);
		codingForm.saveCodingForm();
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		// editing the codingform
		codingForm.editCodingForm(cfName);
		baseClass.waitForElement(codingForm.getTag_Object(tag));
		codingForm.getTag_Object(tag).waitAndClick(5);
		codingForm.getCF_objectName(0).SendKeys(fdName);
		baseClass.stepInfo("editing the friendly label for tag");
		codingForm.getManageCodingFormButton().waitAndClick(5);
		baseClass.stepInfo("clicking on manage coding form tab");
		// validation of confirm message
		boolean flag=codingForm.getSaveWarningMsg().isElementAvailable(2);
		softAssertion.assertTrue(flag);
		codingForm.getValidationButtonYes().waitAndClick(5);
		boolean flagCnfrm=codingForm.getSaveConformMsg().isElementAvailable(2);
		softAssertion.assertTrue(flagCnfrm);
		baseClass.passedStep("confirmation displayed after user edit label when clicked manage coding form tab");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description :Verify that duplicate customized coding form does not get 
	 *                created on Using Copy functionality "Manage Coding Forms" screen
	 */
	@Test(description = "RPMXCON-54011", enabled = true, groups = { "regression" })
	public void verifyDuplicateCF() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54011");
		baseClass.stepInfo("Verify that duplicate customized coding form does not get created on Using "
				+ "Copy functionality \"Manage Coding Forms\" screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		tagsAndFoldersPage=new TagsAndFoldersPage(driver);
		String cfName="cfName" + Utility.dynamicNameAppender();
		String rename="cfName" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// creating
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.addNewCodingFormButton();
		codingForm.passingCodingFormName(cfName);
		driver.scrollPageToTop();
		baseClass.waitForElement(codingForm.getSaveCFBtn());
		codingForm.getSaveCFBtn().waitAndClick(5);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		// copying the codingform
		for (int i = 1; i <=4; i++) {
			if (i==3) {
				codingForm.editCodingForm(cfName);
				codingForm.passingCodingFormName(rename);
				baseClass.waitForElement(codingForm.getSaveCFBtn());
				codingForm.getSaveCFBtn().waitAndClick(5);
			}
			if (i==1||i==2||i==4) {
				if (i==1||i==2) {
					codingForm.copyCodingForm(cfName);
				}
				if (i==4) {
					codingForm.copyCodingForm(rename);
				}
				if (i==1||i==2) {
					baseClass.waitForElement(codingForm.getCodingForm_Search());
					codingForm.getCodingForm_Search().SendKeys(cfName);
				}
				if (i==4) {
					baseClass.waitForElement(codingForm.getCodingForm_Search());
					codingForm.getCodingForm_Search().SendKeys(rename);
				}
				if (i==1||i==2||i==4) {
				baseClass.waitTime(3);
				List<String> manageScreen = new LinkedList<String>();
				List<WebElement>data=codingForm.getCFnames().FindWebElements();
				for (WebElement codingName : data) {
					String value = codingName.getText().toString();
					if (i==1||i==2) {
						if (value.equals(cfName)) {
						}
						else {
							manageScreen.add(value);
						}
					}
					if (i==4) {
						if (value.equals(rename)) {
						}
						else {
							manageScreen.add(value);
						}
					}
					
				}
				if (i==2) {
					manageScreen.remove(0);
				}
				String datas=manageScreen.get(0).toString();
				System.out.println(datas);
				baseClass.stepInfo("while copying codingform name generated with new name as '"+datas+"'" );
				softAssertion.assertNotEquals(cfName, datas);
				manageScreen.remove(0);
				}
			}
		}
		baseClass.passedStep("No duplicated coding form present at manage codingform screen while copying");
		baseClass.passedStep("Successfull message received as ,Coding form copied successfully");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Baskar
	 * @Description : Verify that after removing the radio/check group association of the tag, 
	 *                tag label should be displayed in preview of coding form
	 */
	@Test(description = "RPMXCON-54305", enabled = true, groups = { "regression" })
	public void verifyTagLabelAfterRemovingGroup() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54305");
		baseClass.stepInfo("Verify that after removing the radio/check group association of the tag, tag label should be displayed in preview of coding form");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag"+Utility.dynamicNameAppender();
		String tagTwo = "tag"+Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		tagsAndFoldersPage.CreateTag(tagname, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagTwo, Input.securityGroup);
		baseClass.stepInfo("Created the tag sucessfully");
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		codingForm.CreateCodingFormWithParameter(codingform,tagTwo,null,null,"tag");
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("radio item",1,0);
		baseClass.stepInfo("Radio group is associated to the created tag");
		codingForm.selectTagTypeByIndex("check item",1,1);
		baseClass.stepInfo("check group is associated to the created tag");
		codingForm.saveCodingForm();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Coding form saved successfully");
		
		// validation for radio and checkgroup button
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		boolean radio=codingForm.getCF_Preview_Radio(tagname).isElementAvailable(5);
		System.out.println(radio);
		baseClass.stepInfo("In Preview validation radio button are displayed");
		softAssertion.assertTrue(radio);
		boolean checkBox=codingForm.getCF_Preview_CheckBox(tagTwo).isElementAvailable(5);
		System.out.println(checkBox);
		baseClass.stepInfo("In Preview validation checkbox button are displayed");
		softAssertion.assertTrue(checkBox);
		codingForm.getCF_Preview_OkBtn().waitAndClick(5);
		codingForm.getCodingForm_SelectRemoveLink(2).ScrollTo();
		baseClass.waitForElement(codingForm.getCodingForm_SelectRemoveLink(2));
		codingForm.getCodingForm_SelectRemoveLink(2).waitAndClick(5);
		codingForm.getCodingForm_SelectRemoveLink(3).ScrollTo();
		baseClass.waitForElement(codingForm.getCodingForm_SelectRemoveLink(3));
		codingForm.getCodingForm_SelectRemoveLink(3).waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Removed both radio and checkbox special objects");
		
		// validation of tag label after remove group
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		baseClass.waitTime(5);
		driver.waitForPageToBeReady();
		boolean checkBoxOne=codingForm.getCF_Preview_TagLabel(tagname,0).isElementAvailable(5);
		System.out.println(checkBoxOne);
		Assert.assertTrue(checkBoxOne);
		boolean checkBoxTwo=codingForm.getCF_Preview_TagLabel(tagTwo,1).isElementAvailable(5);
		System.out.println(checkBoxTwo);
		softAssertion.assertTrue(checkBoxTwo);
		codingForm.getCF_Preview_OkBtn().waitAndClick(5);
		
		softAssertion.assertAll();
		baseClass.passedStep("After removing the group tag, Tag label displayed as expected");
		//Deleting the tag and cf
		codingForm.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFoldersPage.deleteAllTags(tagname);
		tagsAndFoldersPage.deleteAllTags(tagTwo);
		loginPage.logout();
	}
	
	
	/**
	 * @Author :Indium-Baskar
	 * @Description :Verify that Proper validation message appears 
	 *               when a user deletes added object(s) from coding form screen
	 */
	@Test(description = "RPMXCON-54226", enabled = true, groups = { "regression" })
	public void verifyRemoveAllTabsInAsc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54226");
		baseClass.stepInfo("Verify that Proper validation message appears when a user "
				+ "deletes added object(s) from coding form screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		tagsAndFoldersPage=new TagsAndFoldersPage(driver);
		String metadata = "DataINT" + Utility.dynamicNameAppender();
		String tag = "tag" + Utility.dynamicNameAppender();

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
		// creating metadata
		List<String> collectionData=new ArrayList<String>();
		projectPage.addCustomFieldProjectDataType(metadata, "INT");
		securityGroupPage.addProjectFieldtoSG(metadata);
		collectionData.add(metadata);
		// logout
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// creating comments
		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		String comments = commentsPage.addComments("com1" + Utility.dynamicNameAppender());
		collectionData.add(comments);
		// creating tags
		tagsAndFoldersPage.CreateTag(tag, Input.codeFormName);
		collectionData.add(tag);
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.basedOnCreatingNewObject(tag, null, null, "tag");
		baseClass.stepInfo("Adding tag into coding form structure");
		codingForm.basedOnCreatingNewObject(null, comments, null, "comment");
		baseClass.stepInfo("Adding comment into coding form structure");
		codingForm.basedOnCreatingNewObject(null, null, metadata, "metadata");
		baseClass.stepInfo("Adding metadata into coding form structure");
		codingForm.addcodingFormAddButton();
		boolean flagMeta = codingForm.getCf_MetaDataDisabled(metadata).isElementAvailable(2);
		softAssertion.assertTrue(flagMeta);
		boolean flagTag = codingForm.getCf_TagDisabled(tag).isElementAvailable(2);
		softAssertion.assertTrue(flagTag);
		boolean flagComment = codingForm.getCf_CommentsDisabled(comments).isElementAvailable(2);
		softAssertion.assertTrue(flagComment);
		baseClass.stepInfo("Added object inside the coding form structure are disappeared");
		// removing the all data using remove link
		for (int i = 0; i <= 2; i++) {
			codingForm.selectRemoveLinkWithValidation(i);
		}
		baseClass.stepInfo("Removing all data from codingform structure");
		// After removing all data , values should appear
		boolean flagMetaFalse = codingForm.getCf_MetaDataDisabled(metadata).isElementAvailable(2);
		softAssertion.assertFalse(flagMetaFalse);
		boolean flagTagFalse = codingForm.getCf_TagDisabled(tag).isElementAvailable(2);
		softAssertion.assertFalse(flagTagFalse);
		boolean flagCommentFalse = codingForm.getCf_CommentsDisabled(comments).isElementAvailable(2);
		softAssertion.assertFalse(flagCommentFalse);
		baseClass.passedStep("Removed  from the coding from structure, value are appeared in left tab");
		softAssertion.assertAll();
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
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
	}

}

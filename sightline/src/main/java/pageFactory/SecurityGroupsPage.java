package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class SecurityGroupsPage {

	Driver driver;
	BaseClass bc;

	public Element getSecurityGroupCreateButton() {
		return driver.FindElementByXPath("//button[text()='Create']");
	}

	public Element getSecurityGroupName() {
		return driver.FindElementById("txtSecurityGroupName");
	}

	public Element getSecurityGroupList() {
		return driver.FindElementById("ddlSecurityGroupsList");
	}

	public Element getSecurityGroupSaveButton() {
		return driver.FindElementById("btnSaveSecurityGroup");
	}

	public Element getSG_AnnLayerbutton() {
		return driver.FindElementByXPath("//*[@id='myTab1']//a[contains(text(),'Annotation Layers')]");
	}

	public Element getSG_AddAnnLayer() {
		return driver.FindElementByXPath("//*[@id=\"annotationJSTree\"]/ul/li/ul/li[1]");
	}

	public Element getSG_AddAnnLayer_Right() {
		return driver.FindElementByXPath("//*[@onclick='AnnotationRightShift();']");
	}

	public Element getSG_AnnSaveButton() {
		return driver.FindElementById("btnSaveAccessControls");
	}

	public Element clickManage() {
		return driver.FindElementByName("Manage");
	}

	public Element clickSecurityGroup() {
		return driver.FindElementByXPath("//*[@class='a-menu' and @name='Security']");
	}

	public Element securityGroupCreate() {
		return driver.FindElementById("btnNewSecurityGroup");
	}

	public Element securityGroupText() {
		return driver.FindElementByXPath("//input[@id='txtSecurityGroupName']");
	}

	public Element securityGroupSave() {
		return driver.FindElementById("btnSaveSecurityGroup");
	}

	public Element getProjectFieldLabel() {
		return driver.FindElementByXPath("//a[text()='Project Fields']");
	}

	public Element getSG_ProjectField(String projectTag) {
		return driver.FindElementByXPath("//div[@id='fieldJSTree']/ul/li/ul/li/a[text()='" + projectTag + "']");
	}

	public Element getSG_ProjectField_Right() {
		return driver.FindElementByXPath("//*[@onclick='FieldRightShift();']");
	}

	public Element getSG_ProjectFieldButton() {
		return driver.FindElementByXPath("//button[@id='btnSaveAccessControls']");
	}

	// For Meta data project fields doc view
	public Element getProjectFieldsLink() {
		return driver.FindElementByXPath("//ul[@ID='myTab1']//a[text()='Project Fields']");
	}

	public Element getProjectField(String projectFieldName) {
		return driver.FindElementByXPath(
				"//div[@id='fieldJSTree']//ul[@class='jstree-children']/li/a[text()='" + projectFieldName + "']");
	}

	public ElementCollection getProjectFields() {
		return driver.FindElementsByXPath("//ul[@class='jstree-children']//li");
	}

	public Element getFieldRightShiftButton() {
		return driver.FindElementByXPath("//div[@class='tab-pane fade active in']/div[1]/div[2]/a[1]");
	}

	public Element getProjectLevelEmailCheckBox() {
		return driver.FindElementByXPath("//span[text()='Use Project-level Email Inclusive and Email Duplicate Data']");
	}

	public Element getYesButton() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getSelectSecurityGroup() {
		return driver.FindElementByXPath("//select[@id='ddlSecurityGroupsList']");

	}

	public Element getAllFolderCheckBox() {
		return driver.FindElementByXPath("//div[@id='folderJSTree']//a[@data-content='All Folders']/i[1]");
	}

	public Element getFolderRightShiftButton() {
		return driver.FindElementByXPath("//div[@class='col-md-2']/a[1]");
	}

	// added by Jayanthi-Indium-23/8/21
	public Element selectSGFromDropdown(String dropDownName) {
		return driver.FindElementByXPath("//option[text()='" + dropDownName + "']");
	}

	public Element SG_deleteButton() {
		return driver.FindElementById("btnDeleteSecurityGroup");
	}

	public Element SGdeleteMessage(String SGlabel) {
		return driver.FindElementByXPath("//label[text()=' Security Group: " + SGlabel + "']");
	}

	// Gopinath
	public Element getFolder(String folderName) {
		return driver.FindElementByXPath(
				"//div[@id='folderJSTree']//ul[@class='jstree-container-ul jstree-children']/li//a[text()='"
						+ folderName + "']");
	}

	// Added by Gopinath - 01-09-2021
	public Element getTagName(String tagName) {
		return driver.FindElementByXPath("//div[@id='tagsJSTree']//a[@data-content = '" + tagName
				+ "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getTagRightShiftButton() {
		return driver.FindElementByXPath("//div[@id='s3']/div[1]/div[2]/a[1]");
	}

	public Element getTagsLink() {
		return driver.FindElementByXPath("//ul[@id='myTab1']//a[text()='Tags']");
	}

	public Element getTagBoard() {
		return driver.FindElementById("tagsJSTree");
	}

	// Added by Gopinath - 06-09-2021
	public Element getAnnotationLayerLink() {
		return driver.FindElementByXPath("//ul[@id='myTab1']//a[text()='Annotation Layers']");
	}

	public Element getAnnotation(String annotation) {
		return driver.FindElementByXPath("//div[@id='annotationJSTree']//a[@data-content = '" + annotation
				+ "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getAnnotationRightShiftButton() {
		return driver.FindElementByXPath("//div[@class='tab-pane fade active in']/div[1]/div[2]/a[1]");
	}

	// Added by Gopinath - 07-09-2021
	public Element getAnnatationLayerTable() {
		return driver.FindElementById("annotationJSTree");
	}

	public Element getReductionTagLink() {
		return driver.FindElementByXPath("//ul[@id='myTab1']//a[text()='Redaction Tags']");
	}

	public Element getReductionTag(String reductionTag) {
		return driver.FindElementByXPath("//div[@id='redactionJSTree']//a[@data-content = '" + reductionTag
				+ "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getReductionTagRightShiftButton() {
		return driver.FindElementByXPath("//div[@class='tab-pane fade active in']/div[1]/div[2]/a[1]");
	}

	public Element getReductionLayerTable() {
		return driver.FindElementById("redactionJSTree");
	}

	// Added by Gopinath -- 22/09/2021
	public Element getUnReductionTag(String unReductionTag) {
		return driver.FindElementByXPath("//div[@id='redactionJSTree_Selected']//a[@data-content = '" + unReductionTag
				+ "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getUnReductionLayerTable() {
		return driver.FindElementById("redactionJSTree_Selected");
	}

	public Element getReductionTagLeftShiftButton() {
		return driver.FindElementByXPath("//div[@class='tab-pane fade active in']/div[1]/div[2]/a[2]");
	}

	// added by sowndarya.velraj
	public Element redactionTags() {
		return driver.FindElementByXPath("//li//a[text()='Redaction Tags']");
	}

	public Element SelectredactionCheckBox() {
		return driver.FindElementByXPath(" //div[@id='redactionJSTree']/ul/li/a[text()='All Redaction Tags']");
	}

	public Element SelectredactionCheckBox(String Tag) {
		return driver.FindElementByXPath(" //div[@id='redactionJSTree_Selected']/ul/li//a[text()='" + Tag + "']");
	}

	public Element getSG_AddAnnLayer_Left() {
		return driver.FindElementByXPath(" //a[@onclick='RedactionLeftShift();']");
	}

	public Element get_CurrentSG() {
		return driver.FindElementByXPath("//span[@id='SecurityGroup-selector']");
	}

	// Added by gopinath - 21/01/2022
	public Element getCommentCheckBox(String name) {
		return driver.FindElementByXPath("//div[@id='commentJSTree']//a[@data-content = '" + name
				+ "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getCommentLink() {
		return driver.FindElementByXPath("//ul[@id='myTab1']//a[text()='Comments']");
	}

	public Element getCommentRightShiftButton() {
		return driver.FindElementByXPath("//div[@id='s5']/div[1]/div[2]/a[1]");
	}

	public Element getCommentBoard() {
		return driver.FindElementById("commentJSTree");
	}

	// Added by Gopinath - 09/02/2022
	public Element getKeywordsLink() {
		return driver.FindElementByXPath("//ul[@id='myTab1']//a[text()='Keywords']");
	}

	public Element getKeywordCheckBox(String name) {
		return driver.FindElementByXPath("//div[@id='myTabContent1']//a[@data-content = '" + name
				+ "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getKeywordRightShiftButton() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary' and contains(@onclick,'KeywordRightshift')]");
	}

	public Element getKeywordBoard() {
		return driver.FindElementById("keywordJSTree");
	}

	public ElementCollection getAvailableSGlist() {
		return driver.FindElementsByXPath("//select[@id='ddlSecurityGroupsList']//option");
	}

	public Element getAnnotationDD() {
		return driver.FindElementByXPath("//div[@id='annotationJSTree']//ul[@class='jstree-children']");
	}

	// Added by Krishna

	public Element daFirstBlock() {
		return driver.FindElementByXPath("//div[@id = 'rmu-wid-id-0']//*[@id=\"taskbasic\"]");
	}

	public Element daFirstTabledata(int i) {
		return driver.FindElementByXPath("//div[@id = 'rmu-wid-id-0']//*[@id='taskbasic']/tbody/tr[" + i + "]/td[1]/a");
	}

	public Element daSecondBlock() {
		return driver.FindElementByXPath("//div[@id = 'rmu-wid-id-2']//*[@id=\"taskbasic\"]");
	}

	public Element daSecondTabledata(int i) {
		return driver
				.FindElementByXPath("//div[@id = 'rmu-wid-id-2']//*[@id='taskbasic']/tbody/tr[\" + i + \"]/td[1]/a");
	}

	public ElementCollection daFirstTableList() {
		return driver.FindElementsByXPath("//div[@id = 'rmu-wid-id-0']//*[@id=\\\"taskbasic\\\"]/thead/tr/th[1]");
	}

	public Element productionPageSelectedSG() {
		return driver.FindElementByXPath("//*[@id=\"SecurityGrpList\"]/option[@selected = \"selected\"]");
	}

	public Element securityGroupTab() {
		return driver.FindElementById("SecurityGroup-selector");
	}

	public Element backToDomain() {
		return driver.FindElementById("BackToDomain");
	}

	public Element productionLeftMenu() {
		return driver.FindElementByXPath("//a[@name = 'Productions']");
	}

	public ElementCollection getTolSecurityGroupCount() {
		return driver.FindElementsByXPath("//select[@id='ddlSecurityGroupsList']/option");
	}

	// Added by Mohan

	public Element getAllFolder(String treeName, String fieldTab) {
		return driver.FindElementByXPath("//div[@id='" + treeName + "']//a[text()='" + fieldTab + "']");
	}

	public Element getAllSecurityPageTabsInProjects(String projectFieldValue) {
		return driver.FindElementByXPath("//a[text()='" + projectFieldValue + "']");
	}

	public Element getProjectFieldsAvailableInProjects(String projectFieldValue) {
		return driver.FindElementByXPath("//div[@id='fieldJSTree']//a[text()='" + projectFieldValue + "']");
	}

	public Element getProjectFieldsAddToSecurityGroup(String projectFieldValue) {
		return driver.FindElementByXPath("//div[@id='fieldJSTree_Selected']//a[text()='" + projectFieldValue + "']");
	}

//Added by Krishna

	public Element getSGGrpList(String sgname) {
		return driver.FindElementByXPath("//select[@id='ddlSecurityGroupsList']//option[text()='" + sgname + "']");
	}

	public Element getSGSelectAccessControlTages(String TagName) {
		return driver.FindElementByXPath("//ul[@id='myTab1']//a[text()='" + TagName + "']");
	}

	public Element getFoldersCheckBox(String folder) {
		return driver.FindElementByXPath(
				"//*[@id='folderJSTree']//a[text()='" + folder + "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSG_Folder_Right() {
		return driver.FindElementByXPath("//*[@onclick='FolderRightShift();']");
	}

	public Element getSG_Folder_Left() {
		return driver.FindElementByXPath("//*[@onclick='FolderLeftShift();']");
	}

	public Element getSelectedFoldersCheckBox(String folder) {
		return driver.FindElementByXPath("//*[@id='folderJSTree_Selected']//a[text()='" + folder
				+ "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getReductionCheckBox(String Reduction) {
		return driver.FindElementByXPath(
				"//*[@id='redactionJSTree']//a[text()='" + Reduction + "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSelectedReductionCheckBox(String Reduction) {
		return driver.FindElementByXPath("//*[@id='redactionJSTree_Selected']//a[text()='" + Reduction
				+ "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSG_Reduction_Right() {
		return driver.FindElementByXPath("//*[@onclick='RedactionRightShift();']");
	}

	public Element getSG_Reduction_Left() {
		return driver.FindElementByXPath("//*[@onclick='RedactionLeftShift();']");
	}

	public Element getTagsCheckBox(String Tag) {
		return driver.FindElementByXPath(
				"//*[@id='tagsJSTree']//a[text()='" + Tag + "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSelectedTagsCheckBox(String Tag) {
		return driver.FindElementByXPath(
				"//*[@id='tagsJSTree_Selected']//a[text()='" + Tag + "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSG_Tag_Right() {
		return driver.FindElementByXPath("//*[@onclick='TagRightShift();']");
	}

	public Element getSG_Tag_Left() {
		return driver.FindElementByXPath("//*[@onclick='TagLeftShift();']");
	}

	public Element getSG_CancelBtn() {
		return driver.FindElementById("btnCancelAccessControls");
	}

	public Element getSG_GenerateEmailRadioButton(int RatioBtn) {
		return driver.FindElementByXPath("(//*[@id='content']//section//div/label/i)[" + RatioBtn + "]");
	}

	public Element getVerifySG_EmailGenerateWarningMsg() {
		return driver.FindElementByXPath("//*[@id='Msg1']/div/p/b");
	}

	public Element getSG_GenerateEmailButton() {
		return driver.FindElementById("btnRegenerateEmail");
	}

	public Element getSG_AnnotationLayer(String annotation) {
		return driver.FindElementByXPath("//*[@id='annotationJSTree']//a[text()='" + annotation
				+ "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSGAddAnnonationLayer() {
		return driver.FindElementByXPath("//*[@id='annotationJSTree_Selected']/ul/li/ul/li[1]");
	}

	public Element getProjectFieldCheckBox(String field) {
		return driver.FindElementByXPath(
				"//*[@id='fieldJSTree']//a[text()='" + field + "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSG_Field_Right() {
		return driver.FindElementByXPath("//*[@onclick='FieldRightShift();']");
	}

	public ElementCollection getSgDefaultTag() {
		return driver.FindElementsByXPath("//div[@id='tagsJSTree_Selected']//a[contains(text(),'Default Tags')]");
	}

	public Element getSGBgCompletedTask() {
		return driver.FindElementByXPath("//*[@id='bgTask']/ul/li/span");
	}

	public Element getSGBgCompletedId() {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr//td");
	}

	public Element getSgBgStatusDropDown() {
		return driver.FindElementById("ddlStatusType");
	}

	public Element getSgBgFilterBtn() {
		return driver.FindElementById("btnAppyFilter");
	}

	public Element getSecurityGroupBgViewAll() {
		return driver.FindElementById("btnViewAll");
	}

	public Element getSG_EmailGenerateWarningMsgBulkReleaseText(String text) {
		return driver.FindElementByXPath("//*[@id='Msg1']/div/p[contains(text(),'" + text + "')]");

	}

	public Element getSG_EmailGenerateWarningMsgBulkRelease() {
		return driver.FindElementByXPath("//*[@id='Msg1']/div/p");

	}

	public Element getVerifySG_EmailGenerateWarningMsgText(String text) {
		return driver.FindElementByXPath("//*[@id='Msg1']/div/p/b[contains(text(),'" + text + "')]");

	}

	public Element getSelectedAnnotationLayerCheckBox(String Annotation) {
		return driver.FindElementByXPath("//*[@id='annotationJSTree_Selected']//a[text()='" + Annotation
				+ "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSG_AddAnnotationLayer_Left() {
		return driver.FindElementByXPath("//*[@onclick='AnnotationLeftShift();']");
	}

	public Element getAnnotationLayerCheckBox(String Annotation) {
		return driver.FindElementByXPath("//*[@id='annotationJSTree']//a[text()='" + Annotation
				+ "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getRenameBtn() {
		return driver.FindElementById("btnRenameSecurtyGroup");
	}

	public Element getRadioBn() {
		return driver.FindElementByXPath("//input[@id='projlevelemail']");

	}

	public Element getProjectSelector() {
		return driver.FindElementById("project-selector");
	}
	
	public Element getCommentsCheckBox(String comment) {
		return driver.FindElementByXPath(
				"//*[@id='commentJSTree']//a[text()='" + comment + "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSelectedCommentsCheckBox(String comment) {
		return driver.FindElementByXPath("//*[@id='commentJSTree_Selected']//a[text()='" + comment
				+ "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSG_Comment_Right() {
		return driver.FindElementByXPath("//*[@onclick='CommentRightShift();']");
	}

	public Element getSG_Comment_Left() {
		return driver.FindElementByXPath("//*[@onclick='CommentLeftShift();']");
	}

	public Element getKeyWordsCheckBox(String keyword) {
		return driver.FindElementByXPath(
				"//*[@id='keywordJSTree']//a[text()='" + keyword + "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSelectedKeyWordCheckBox(String Keyword) {
		return driver.FindElementByXPath("//*[@id='keywordJSTree_Selected']//a[text()='" + Keyword
				+ "']/./i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSG_Keyword_Right() {
		return driver.FindElementByXPath("//*[@onclick='KeywordRightshift();']");
	}
	

	public Element getSG_Keyword_Left() {
		return driver.FindElementByXPath("//*[@onclick='KeywordLeftShift();']");
	}

	public SecurityGroupsPage(Driver driver) {

		this.driver = driver;
		bc = new BaseClass(driver);
		// this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		driver.waitForPageToBeReady();

	}

	// modified for stabilisation
	public void AddSecurityGroup(String securitygroupname) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSecurityGroupCreateButton().Visible();
			}
		}), Input.wait60);
		getSecurityGroupCreateButton().isElementAvailable(15);
		getSecurityGroupCreateButton().waitAndClick(5);
		bc.waitTime(2);
		Actions action = new Actions(driver.getWebDriver());
		action.moveToElement(driver.getWebDriver().findElement(By.xpath("//button[text()='Create']"))).click()
				.perform();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSecurityGroupName().Visible();
			}
		}), Input.wait30);
		getSecurityGroupName().isElementAvailable(15);
		getSecurityGroupName().SendKeys(securitygroupname);
		bc.waitTime(2);
		getSecurityGroupSaveButton().isElementAvailable(15);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSecurityGroupSaveButton().Visible();
			}
		}), Input.wait30);
		getSecurityGroupSaveButton().Click();

//    	bc.VerifySuccessMessage("Security group added successfully");
		Reporter.log("Security Group created :" + securitygroupname, true);
		UtilityLog.info("Security Group created :-" + securitygroupname);

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getSecurityGroupList().Visible() ;}}), Input.wait30);
		 * getSecurityGroupList().selectFromDropdown().selectByVisibleText(
		 * securitygroupname);
		 */
	}

	public List<String> GetSecurityGrouplist() {

		List<WebElement> allvalues = getSecurityGroupList().selectFromDropdown().getOptions();

		List<String> all = new ArrayList<String>();
		for (int j = 0; j < allvalues.size(); j++) {
			all.add(allvalues.get(j).getText());

		}
		return all;

	}

	public void addlayertosg() {

		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSG_AnnLayerbutton().Visible();
			}
		}), Input.wait30);
		getSG_AnnLayerbutton().waitAndClick(5);

		getSG_AddAnnLayer().waitAndClick(15);

		getSG_AddAnnLayer_Right().waitAndClick(15);
		try {
			bc.VerifyWarningMessage("Cannot add more than one security group. "
					+ "A security group can have only one annotation layer at a time.");

			getSG_AnnSaveButton().waitAndClick(15);
			bc.VerifySuccessMessage("Your selections were saved successfully");
			bc.CloseSuccessMsgpopup();
		} catch (Exception e) {
			System.out.println("Annotation layer already added");
			UtilityLog.info("Annotation layer already added");
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for clicking project field link on Security group page..
	 * 
	 */
	public void selectSecurityGroupAndClickOnProjectFldLink(String securityGroupName) {
		try {
			driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroupName);
			driver.waitForPageToBeReady();
			bc.waitForElement(getProjectFieldsLink());
			getProjectFieldsLink().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while clicking on project field link" + e.getMessage());

		}
	}

	public void createSecurityGroups(String securityGroupName) {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
		Actions actions = new Actions(driver.getWebDriver());
		wait.until(ExpectedConditions.elementToBeClickable(clickManage().getWebElement()));
		actions.moveToElement(clickManage().getWebElement());
		actions.click().build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(clickSecurityGroup().getWebElement()));
		actions.moveToElement(clickSecurityGroup().getWebElement());
		actions.click().build().perform();
		securityGroupCreate().waitAndClick(5);
		securityGroupText().waitAndClick(5);
		securityGroupText().SendKeys(securityGroupName);
		securityGroupSave().waitAndClick(5);
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for adding previously project field to security field..
	 * @param projectFieldName : projectFieldName is a string value that need to add
	 *                         to security group.
	 */
	public void addProjectFieldToSecurityGroup(String projectFieldName) {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getProjectField(projectFieldName));
			getProjectField(projectFieldName).Click();
			getFieldRightShiftButton().Click();
			getProjectLevelEmailCheckBox().Click();
			if (getYesButton().isElementAvailable(0)) {
				getYesButton().Click();
			}
			driver.scrollingToBottomofAPage();
			if (getSG_AnnSaveButton().isElementAvailable(3))
				getSG_AnnSaveButton().Click();

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while clicking on project field link" + e.getMessage());

		}
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 23/8/2021 Modified
	 *         by:Baskar.
	 * @Description:project field assign to security group
	 */

	public void addProjectFieldtoSG(String projectTag) {
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		bc.waitForElement(getSecurityGroupList());
		getSecurityGroupList().selectFromDropdown().selectByVisibleText("Default Security Group");
		bc.waitForElement(getProjectFieldLabel());
		bc.waitTillElemetToBeClickable(getProjectFieldLabel());
		getProjectFieldLabel().waitAndClick(10);
		bc.waitForElement(getSG_ProjectField(projectTag));
		bc.waitTillElemetToBeClickable(getSG_ProjectField(projectTag));
		getSG_ProjectField(projectTag).waitAndClick(10);
		bc.waitForElement(getSG_ProjectField_Right());
		bc.waitTillElemetToBeClickable(getSG_ProjectField_Right());
		getSG_ProjectField_Right().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getSG_ProjectFieldButton());
		bc.waitTillElemetToBeClickable(getSG_ProjectFieldButton());
		getSG_ProjectFieldButton().waitAndClick(10);
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for adding folders to security field..
	 * @param securityGroupName -- (securityGroupName is a string value that need to
	 *                          add to security group).
	 * @param folderName        -- (folderName is a string value that name of
	 *                          folder)
	 * 
	 * @Modifed On : 22/03/2022 change try catch to if else on the button
	 *          getYesButton()
	 */
	public void addFolderToSecurityGroup(String securityGroupName, String folderName) {
		try {
			bc.waitForElement(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroupName);
			driver.waitForPageToBeReady();
			bc.waitForElement(getFolder(folderName));
			for (int i = 0; i < 7; i++) {
				try {
					bc.waitForElement(getFolder(folderName));
					getFolder(folderName).waitAndClick(10);
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
			bc.waitForElement(getFolderRightShiftButton());
			getFolderRightShiftButton().Click();
			getProjectLevelEmailCheckBox().Click();
			if (getYesButton().isElementAvailable(1)) {
				getYesButton().waitAndClick(10);
			} else {
				getSG_AnnSaveButton().waitAndClick(10);
			}
			bc.waitForElement(bc.getSuccessMsg());
			bc.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals("Success message is not displayed", true,
					bc.getSuccessMsg().getWebElement().isDisplayed());
			if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully");
				bc.CloseSuccessMsgpopup();
			}

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while adding folder to security group" + e.getMessage());

		}
	}

	/**
	 * @author : Jayanthi Created date: 23/8/21 Modified date: NA.
	 * @Description: Method for Deleting security group.
	 * @param SGName -- (securityGroupName is a string value that needs to be
	 *               deleted).
	 */
	public void deleteSecurityGroups(String SGName) {
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		driver.waitForPageToBeReady();
		bc.waitForElement(getSecurityGroupList());
		getSecurityGroupList().waitAndClick(3);
		bc.waitForElement(selectSGFromDropdown(SGName));
		selectSGFromDropdown(SGName).waitAndClick(2);
		bc.waitForElement(SG_deleteButton());
		SG_deleteButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return SGdeleteMessage(SGName).Visible();
			}
		}), Input.wait30);
		if (SGdeleteMessage(SGName).isElementPresent() == true) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getYesButton().Visible();
				}
			}), Input.wait30);
			getYesButton().Click();
		} else {
			Assert.fail("Failed to delete the security group");
		}
		bc.VerifySuccessMessage("Security group deleted successfully");

	}

	/**
	 * @author : Gopinath Created date: 08-09-2021 Modified date: NA Modified
	 *         by:Gopinath.
	 * @Description: Method for adding tag to security field..
	 * @param securityGroupName -- (securityGroupName is a string value that need to
	 *                          add to security group).
	 * @param tagName           -- (tagName is a string value that name of tag)
	 */
	public void addTagToSecurityGroup(String securityGroupName, String tagName) {
		try {
			bc.waitForElement(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroupName);
			driver.waitForPageToBeReady();
			bc.waitForElement(getTagsLink());
			bc.waitTillElemetToBeClickable(getTagsLink());
			getTagsLink().Click();
			bc.waitForElement(getTagBoard());
			getTagBoard().Click();
			for (int i = 0; i < 20; i++) {
				try {
					getTagName(tagName).getWebElement().click();
					break;
				} catch (Exception e) {
					bc.waitForElement(getTagName(tagName));
					bc.waitTillElemetToBeClickable(getTagName(tagName));
				}
			}
			bc.waitForElement(getTagRightShiftButton());
			getTagRightShiftButton().Click();
			getProjectLevelEmailCheckBox().Click();
			driver.scrollingToBottomofAPage();
			getSG_AnnSaveButton().Click();
			bc.waitForElement(bc.getSuccessMsg());
			bc.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals("Success message is not displayed", true,
					bc.getSuccessMsg().getWebElement().isDisplayed());
			if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully");
				bc.CloseSuccessMsgpopup();
			}

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while adding folder to security group" + e.getMessage());

		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting security group.
	 * @param securityGroupName : (securityGroupName is String value that name of
	 *                          security group).
	 * 
	 */
	public void selectSecurityGroup(String securityGroupName) {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getSelectSecurityGroup());
			bc.waitTillElemetToBeClickable(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroupName);

			bc.stepInfo("Selected " + securityGroupName + " from available SG's");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting security group" + e.getMessage());

		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for click on annotation link and select annotation.
	 * @param annotation : (annotation is String value that name of annotation).
	 * 
	 */
	public void clickOnAnnotationLinkAndSelectAnnotation(String annotation) {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getAnnotationLayerLink());
			bc.waitTillElemetToBeClickable(getAnnotationLayerLink());
			getAnnotationLayerLink().waitAndClick(5);
			bc.waitForElement(getAnnatationLayerTable());
			bc.waitTillElemetToBeClickable(getAnnatationLayerTable());
			getAnnatationLayerTable().waitAndClick(5);

			bc.waitForElement(getAnnotation(annotation));
			bc.waitTillElemetToBeClickable(getAnnotation(annotation));
			getAnnotation(annotation).waitAndClick(5);
			bc.waitForElement(getAnnotationRightShiftButton());
			getAnnotationRightShiftButton().waitAndClick(5);
			getProjectLevelEmailCheckBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			for (int i = 0; i < 15; i++) {
				try {
					Thread.sleep(2000);
					getSG_AnnSaveButton().waitAndFind(10);
					getSG_AnnSaveButton().waitAndClick(5);
					break;
				} catch (Exception e) {
					bc.waitForElement(getSG_AnnSaveButton());
					bc.waitTillElemetToBeClickable(getSG_AnnSaveButton());
				}
			}
			bc.waitForElement(bc.getSuccessMsg());
			bc.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals("Success message is not displayed", true,
					bc.getSuccessMsg().getWebElement().isDisplayed());
			if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting security group" + e.getMessage());

		}
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for click on reduction Tag link and select reduction
	 *               Tag.
	 * @param reductionTag : (reductionTag is String value that name of
	 *                     reductionTag).
	 * 
	 */
	public void clickOnReductionTagAndSelectReduction(String reductionTag) {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getReductionTagLink());
			bc.waitTillElemetToBeClickable(getReductionTagLink());
			getReductionTagLink().Click();
			bc.waitForElement(getReductionLayerTable());
			bc.waitTillElemetToBeClickable(getReductionLayerTable());
			getReductionLayerTable().Click();
			bc.waitForElement(getReductionTag(reductionTag));
			bc.waitTillElemetToBeClickable(getReductionTag(reductionTag));
			getReductionTag(reductionTag).Click();
			bc.waitForElement(getReductionTagRightShiftButton());
			getReductionTagRightShiftButton().Click();
			getProjectLevelEmailCheckBox().Click();
			driver.scrollingToBottomofAPage();
			for (int i = 0; i < 15; i++) {
				try {
					Thread.sleep(2000);
					getSG_AnnSaveButton().waitAndFind(10);
					getSG_AnnSaveButton().Click();
					break;
				} catch (Exception e) {
					bc.waitForElement(getSG_AnnSaveButton());
					bc.waitTillElemetToBeClickable(getSG_AnnSaveButton());
				}
			}
			bc.waitForElement(bc.getSuccessMsg());
			bc.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals("Success message is not displayed", true,
					bc.getSuccessMsg().getWebElement().isDisplayed());
			if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully for this reduction " + reductionTag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting reduction tag" + e.getMessage());

		}
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Gopinath
	 * @param tagName : (tagName is string value that name of tag need to untag ).
	 * @description This method for selecting assignment to view in doc view.
	 */
	public void unTagFromRedatctionTags(String tagName) {
		try {
			driver.waitForPageToBeReady();
			bc.waitTime(2);
			bc.waitForElement(getReductionTagLink());
			bc.waitTillElemetToBeClickable(getReductionTagLink());
			getReductionTagLink().Click();
			bc.waitTime(2);
			bc.waitForElement(getUnReductionLayerTable());
			bc.waitTillElemetToBeClickable(getUnReductionLayerTable());
			getUnReductionLayerTable().Click();
			bc.waitTime(2);
			bc.waitForElement(getUnReductionTag(tagName));
			bc.waitTillElemetToBeClickable(getUnReductionTag(tagName));
			getUnReductionTag(tagName).Click();
			bc.waitTime(2);
			bc.waitForElement(getReductionTagLeftShiftButton());
			getReductionTagLeftShiftButton().Click();
			bc.waitTime(2);
			getProjectLevelEmailCheckBox().Click();
			driver.scrollingToBottomofAPage();
			for (int i = 0; i < 15; i++) {
				try {
					bc.waitTime(2);
					getSG_AnnSaveButton().waitAndFind(10);
					getSG_AnnSaveButton().Click();
					break;
				} catch (Exception e) {
					bc.waitForElement(getSG_AnnSaveButton());
					bc.waitTillElemetToBeClickable(getSG_AnnSaveButton());
				}
			}
			bc.waitForElement(bc.getSuccessMsg());
			bc.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals("Success message is not displayed", true,
					bc.getSuccessMsg().getWebElement().isDisplayed());
			if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully");
				bc.passedStep("Untagged from security group successfully of tag name :: " + tagName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting assignment to view in Doc view" + e.getMessage());

		}

	}

	/**
	 * @Author : Sowndarya.Velraj Created date: NA Modified date: NA Modified
	 *         by:Gopinath.
	 */
	public void unAssigningTheTagInRedaction(String Tag)

	{
		driver.waitForPageToBeReady();
		bc.waitForElement(redactionTags());
		redactionTags().waitAndClick(10);

		bc.waitForElement(SelectredactionCheckBox(Tag));
		SelectredactionCheckBox(Tag).waitAndClick(10);

		bc.waitForElement(getSG_AddAnnLayer_Left());
		getSG_AddAnnLayer_Left().waitAndClick(10);

		bc.waitForElement(getSG_AnnSaveButton());
		getSG_AnnSaveButton().waitAndClick(10);
	}

	/**
	 * @author Jeevitha Description : Assigning Annotation layer to The Security
	 *         Group
	 * @param annotation
	 */
	public void assignAnnotationToSG(String annotation) {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);

		driver.waitForPageToBeReady();
		bc.waitForElement(getAnnotationLayerLink());
		getAnnotationLayerLink().waitAndClick(10);

		Actions actions = new Actions(driver.getWebDriver());
		actions.sendKeys(Keys.SPACE).build().perform();

		bc.waitTime(2);
		bc.waitForElement(getAnnotationDD());
		getAnnotationDD().waitAndClick(10);

		bc.waitForElement(getAnnotation(annotation));
		getAnnotation(annotation).waitAndClick(10);

		System.out.println("Selected Annotation layer : " + annotation);
		bc.stepInfo("Selected Annotation layer : " + annotation);

		bc.waitForElement(getAnnotationRightShiftButton());
		getAnnotationRightShiftButton().waitAndClick(10);

		driver.scrollingToBottomofAPage();
		bc.waitForElement(getSG_AnnSaveButton());
		getSG_AnnSaveButton().waitAndClick(20);

		bc.VerifySuccessMessage("Your selections were saved successfully");
		bc.CloseSuccessMsgpopup();
	}

	/*
	 * @author Jeevitha
	 */
	public void assignRedactionTagtoSG(String Tag) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getReductionTagLink());
		getReductionTagLink().waitAndClick(10);

		bc.waitForElement(SelectredactionCheckBox());
		SelectredactionCheckBox().waitAndClick(5);

		driver.scrollPageToTop();

		bc.waitForElement(SelectredactionCheckBox());
		SelectredactionCheckBox().waitAndClick(5);

		driver.waitForPageToBeReady();
		bc.waitForElement(getReductionTag(Tag));
		getReductionTag(Tag).waitAndClick(20);
		System.out.println("Selected Redaction Tag : " + Tag);
		bc.stepInfo("Selected Redaction Tag : " + Tag);

		bc.waitForElement(getReductionTagRightShiftButton());
		getReductionTagRightShiftButton().waitAndClick(20);

		driver.scrollingToBottomofAPage();
		bc.waitForElement(getSG_AnnSaveButton());
		getSG_AnnSaveButton().waitAndClick(20);
		try {
			bc.VerifySuccessMessage("Your selections were saved successfully");
		} catch (Exception e) {
			System.out.println("Success Message Not Displayed");
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating to security groups page.
	 */
	public void navigateToSecurityGropusPageURL() {
		try {
			driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while navigating to security groups is failed" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: 08-09-2021 Modified date: NA Modified
	 *         by:Gopinath.
	 * @Description: Method for adding comment to security field..
	 * @param securityGroupName -- (securityGroupName is a string value that need to
	 *                          add to security group).
	 * @param commentName       -- (commentName is a string value that name of
	 *                          comment field)
	 */
	public void addCommentToSecurityGroup(String securityGroupName, String commentName) {
		try {
			bc.waitForElement(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroupName);
			driver.waitForPageToBeReady();
			bc.waitForElement(getCommentLink());
			bc.waitTillElemetToBeClickable(getCommentLink());
			getCommentLink().isElementAvailable(10);
			getCommentLink().Click();
			driver.waitForPageToBeReady();
			bc.waitForElement(getCommentBoard());
			getCommentBoard().isElementAvailable(10);
			getCommentBoard().Click();
			for (int i = 0; i < 20; i++) {
				try {
					getCommentCheckBox(commentName).isElementAvailable(10);
					getCommentCheckBox(commentName).getWebElement().click();
					break;
				} catch (Exception e) {
					bc.waitForElement(getCommentCheckBox(commentName));
				}
			}
			bc.waitForElement(getCommentRightShiftButton());
			getCommentRightShiftButton().isElementAvailable(10);
			getCommentRightShiftButton().Click();
			driver.scrollingToBottomofAPage();
			getProjectLevelEmailCheckBox().Click();
			driver.waitForPageToBeReady();
			bc.waitForElement(getSG_AnnSaveButton());
			getSG_AnnSaveButton().isElementAvailable(10);
			getSG_AnnSaveButton().Click();
			bc.waitForElement(bc.getSuccessMsg());
			bc.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals("Success message is not displayed", true,
					bc.getSuccessMsg().getWebElement().isDisplayed());
			if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while adding comment to security group" + e.getMessage());

		}
	}

	/**
	 * @author : Gopinath Created date: 08-09-2021 Modified date: NA Modified
	 *         by:Gopinath.
	 * @Description: Method for adding keyword to security field..
	 * @param securityGroupName -- (securityGroupName is a string value that need to
	 *                          add to security group).
	 * @param keyword           -- (keyword is a string value that name of keyword)
	 */
	public void addKeywordToSecurityGroup(String securityGroupName, String keyword) {
		try {
			bc.waitForElement(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroupName);
			driver.waitForPageToBeReady();
			bc.waitForElement(getKeywordsLink());
			bc.waitTillElemetToBeClickable(getKeywordsLink());
			getKeywordsLink().isElementAvailable(10);
			getKeywordsLink().Click();
			driver.waitForPageToBeReady();
			bc.waitForElement(getKeywordBoard());
			getKeywordBoard().isElementAvailable(10);
			getKeywordBoard().Click();
			for (int i = 0; i < 20; i++) {
				try {
					bc.waitTime(3);
					getKeywordCheckBox(keyword).isElementAvailable(10);
					getKeywordCheckBox(keyword).getWebElement().click();
					break;
				} catch (Exception e) {
					bc.waitForElement(getKeywordCheckBox(keyword));
				}
			}
			bc.waitForElement(getKeywordRightShiftButton());
			getKeywordRightShiftButton().isElementAvailable(10);
			getKeywordRightShiftButton().Click();
			driver.scrollingToBottomofAPage();
			getProjectLevelEmailCheckBox().Click();
			driver.waitForPageToBeReady();
			bc.waitForElement(getSG_AnnSaveButton());
			getSG_AnnSaveButton().isElementAvailable(10);
			getSG_AnnSaveButton().Click();
			bc.waitForElement(bc.getSuccessMsg());
			bc.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals("Success message is not displayed", true,
					bc.getSuccessMsg().getWebElement().isDisplayed());
			bc.passedStep("Success message is displayed successfully");

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while adding keyword to security group" + e.getMessage());

		}
	}

	/**
	 * @author Raghuram.A
	 * @param securitygroupname - security group name to create or pick
	 * @return
	 */
	public String createOrPickSG(String securitygroupname) {
		Boolean defaultSG = true;
		List<String> sgList = new ArrayList<>();
		sgList = bc.availableListofElements(getAvailableSGlist());
		int listSize = sgList.size();
		System.out.println(listSize);
		if (listSize > 1) {
			while (defaultSG) {
				int indexTOSelect = bc.randNumber(listSize);
				securitygroupname = sgList.get(indexTOSelect);
				System.out.println(securitygroupname);
				if (!securitygroupname.equalsIgnoreCase(Input.securityGroup)) {
					defaultSG = false;
				}
			}
		} else {
			AddSecurityGroup(securitygroupname);
			driver.waitForPageToBeReady();
		}
		return securitygroupname;
	}

	/**
	 * @author Indium-Baskar date:5/05/2021 Modified date: 23/8/2021 Modified
	 *         by:Baskar.
	 * @Description:project field assign to security group
	 */

	public void addProjectFieldtoSG(String securityGroup, String projectTag) {
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		bc.waitForElement(getSecurityGroupList());
		getSecurityGroupList().selectFromDropdown().selectByVisibleText(securityGroup);
		bc.waitForElement(getProjectFieldLabel());
		bc.waitTillElemetToBeClickable(getProjectFieldLabel());
		getProjectFieldLabel().waitAndClick(10);
		bc.waitForElement(getSG_ProjectField(projectTag));
		bc.waitTillElemetToBeClickable(getSG_ProjectField(projectTag));
		getSG_ProjectField(projectTag).waitAndClick(10);
		bc.waitForElement(getSG_ProjectField_Right());
		bc.waitTillElemetToBeClickable(getSG_ProjectField_Right());
		getSG_ProjectField_Right().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getSG_ProjectFieldButton());
		bc.waitTillElemetToBeClickable(getSG_ProjectFieldButton());
		getSG_ProjectFieldButton().waitAndClick(10);
	}

	/**
	 * Method to select project from DAU
	 * 
	 * @author Sai.Duvvuru
	 */

	public void selectProjectFromDA(String projectname) {
		WebElement mytable = daFirstBlock().getWebElement();
		List<String> projects = new ArrayList<String>();
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		// To calculate no of rows In table.
		int rows_count = rows_table.size();
		System.out.println(rows_count);
		for (int row = 1; row < rows_count; row++) {
			projects.add(daFirstTabledata(row).getText());
		}
		System.out.println(projects);
		int index = projects.indexOf(projectname);
		System.out.println(index);
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(daFirstTabledata(index + 1));
		daFirstTabledata(index + 1).waitAndClick(5);
		bc.waitForElement(securityGroupTab());
	}

	/*
	 * @author Vijaya.Rani
	 * 
	 */
	public void validateSecurityGroupsCount() {

		driver.waitForPageToBeReady();
		bc.waitForElement(getSecurityGroupList());
		getSecurityGroupList().waitAndClick(5);

		driver.waitForPageToBeReady();
		ElementCollection totFolderCount = getTolSecurityGroupCount();
		int totFolderSize = totFolderCount.size();
		if (totFolderSize > 3) {
			bc.passedStep(
					"There are more than 2 security Groups(1-1 in Security Group) is exists in source template project.");

		} else {
			bc.failedStep("There are no SecurityGroup in this project");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @Description To verify UnassignField is not present in the newly created
	 *              project
	 */
	public void verifyUnAssignedInSecurityGroup(String fieldName) {

		bc.waitForElement(getProjectFieldLabel());
		getProjectFieldLabel().waitAndClick(5);

		bc.waitTime(2);

		bc.waitForElement(getProjectFieldsAddToSecurityGroup(fieldName));
		if (getProjectFieldsAddToSecurityGroup(fieldName).isElementAvailable(5)) {
			bc.failedStep("UnReleased Field is Available in newly created Project.");

		} else {
			bc.passedStep("UnReleased Field is not Available in newly created Project.");
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @Description To verify AssignField is present in the newly created project
	 */
	public void verifyAssignedInSecurityGroup(String fieldName) {

		bc.waitForElement(getProjectFieldLabel());
		getProjectFieldLabel().waitAndClick(5);

		bc.waitForElement(getProjectFieldsAddToSecurityGroup(fieldName));
		if (getProjectFieldsAddToSecurityGroup(fieldName).isElementAvailable(5)
				&& getProjectFieldsAvailableInProjects(fieldName).isElementAvailable(5)) {
			bc.passedStep("Released Field is Available in newly created Project.");

		} else {
			bc.failedStep("Released Field is not Available in newly created Project.");
		}
	}

	public void verifyAllFieldsArePresentInSecurityHomePage() {

		driver.waitForPageToBeReady();
		bc.waitForElement(getSecurityGroupList());
		getSecurityGroupList().selectFromDropdown().selectByVisibleText("Default Security Group");

		if (getAllFolder("folderJSTree", "All Folders").isElementPresent()) {
			bc.passedStep("Folders Tab is clicked and all folders are present under Folders tab");
		} else {
			bc.failedStep("All Foders are nor present under Folders Tab");
		}
		bc.waitForElement(getAllSecurityPageTabsInProjects("Keywords"));
		getAllSecurityPageTabsInProjects("Keywords").waitAndClick(5);
		if (getAllFolder("keywordJSTree", "All Keywords").isElementPresent()) {
			bc.passedStep("Keyword Tab is clicked and all keywords are present under Keyword tab");
		} else {
			bc.failedStep("All Keyword are nor present under Keyword Tab");
		}

		bc.waitForElement(getAllSecurityPageTabsInProjects("Tags"));
		getAllSecurityPageTabsInProjects("Tags").waitAndClick(5);
		if (getAllFolder("tagsJSTree", "All Tags").isElementPresent()) {
			bc.passedStep("Tags Tab is clicked and all Tags are present under Tags tab");
		} else {
			bc.failedStep("All Tags are nor present under Tags Tab");
		}

		bc.waitForElement(getAllSecurityPageTabsInProjects("Comments"));
		getAllSecurityPageTabsInProjects("Comments").waitAndClick(5);
		if (getAllFolder("commentJSTree", "All Comments").isElementPresent()) {
			bc.passedStep("Comments Tab is clicked and all Comments are present under Comments tab");
		} else {
			bc.failedStep("All Comments are nor present under Comments Tab");
		}

		bc.waitForElement(getAllSecurityPageTabsInProjects("Annotation Layers"));
		getAllSecurityPageTabsInProjects("Annotation Layers").waitAndClick(5);
		if (getAllFolder("annotationJSTree", "All AnnotationLayers").isElementPresent()) {
			bc.passedStep(
					"AnnotationLayers Tab is clicked and all AnnotationLayers are present under AnnotationLayers tab");
		} else {
			bc.failedStep("All AnnotationLayers are nor present under AnnotationLayers Tab");
		}

		bc.waitForElement(getAllSecurityPageTabsInProjects("Project Fields"));
		getAllSecurityPageTabsInProjects("Project Fields").waitAndClick(5);

		if (getAllFolder("fieldJSTree", "All Fields").isElementAvailable(5)) {
			bc.passedStep("Project Fields Tab is clicked and all Project Fields are present under Project Fields tab");
		} else {
			bc.failedStep("All Project Fields are nor present under Fields Tab");
		}

		bc.waitForElement(getAllSecurityPageTabsInProjects("Redaction Tags"));
		getAllSecurityPageTabsInProjects("Redaction Tags").waitAndClick(5);

		if (getAllFolder("redactionJSTree", "All Redaction Tags").isElementAvailable(5)) {
			bc.passedStep("Redaction Tags Tab is clicked and all Redaction Tags are present under Redaction Tags tab");
		} else {
			bc.failedStep("All Redaction Tags are nor present under Redaction Tags Tab");
		}

	}

	/**
	 * @author date:Modified date:
	 * @Description: verify selected folder on avalaible field and assign selected
	 *               fields
	 */
	public void verifySelectFolderisAssignedInSelectedList(String foldername) {
		driver.waitForPageToBeReady();
		SoftAssert softassert = new SoftAssert();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFoldersCheckBox(foldername).Visible();
			}
		}), Input.wait90);
		System.out.println(foldername);
		bc.waitTillElemetToBeClickable(getFoldersCheckBox(foldername));
		getFoldersCheckBox(foldername).waitAndClick(5);
		driver.waitForPageToBeReady();
		getSG_Folder_Right().waitAndClick(5);
		bc.waitForElement(getSelectedFoldersCheckBox(foldername));
		System.out.println(foldername);
		softassert.assertTrue(getSelectedFoldersCheckBox(foldername).isElementAvailable(5));
		if (getSelectedFoldersCheckBox(foldername).isElementAvailable(5)) {
			bc.passedStep(foldername + "is displayed on Rightside list");

		} else {
			bc.failedMessage("folder is not displayed");
		}
	}

	/**
	 * @author Krishna date:Modified date:
	 * @Description: verify selected Tags on available field and assign selected
	 *               fields
	 */
	public void verifySelectTagsAssignedInSelectedList(String Tagname) {
		driver.waitForPageToBeReady();
		SoftAssert softassert = new SoftAssert();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsCheckBox(Tagname).Visible();
			}
		}), Input.wait90);
		System.out.println(Tagname);
		softassert.assertTrue((getTagsCheckBox(Tagname).isDisplayed()));
		bc.stepInfo("Selected Tags is displayed on available list");
		bc.waitTime(5);
		bc.waitTillElemetToBeClickable(getTagsCheckBox(Tagname));
		getTagsCheckBox(Tagname).waitAndClick(5);
		driver.waitForPageToBeReady();
		getSG_Tag_Right().waitAndClick(5);
		bc.waitForElement(getSelectedTagsCheckBox(Tagname));
		System.out.println(Tagname);
		softassert.assertTrue(getSelectedTagsCheckBox(Tagname).isElementAvailable(5));
		if (getSelectedTagsCheckBox(Tagname).isElementAvailable(5)) {
			bc.passedStep(Tagname + "is displayed on Rightside Selected list");

		} else {
			bc.failedMessage("folder is not displayed");
		}
	}

	/**
	 * @author Krishna date:Modified date:
	 * @Description: selected Tags on available field
	 */
	public void getTagsInSelectedList(String Tagname) {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsCheckBox(Tagname).Visible();
			}
		}), Input.wait90);
		bc.waitTillElemetToBeClickable(getTagsCheckBox(Tagname));
		getTagsCheckBox(Tagname).waitAndClick(5);

	}

	/**
	 * @author Krishna date:Modified date:
	 * @Description: verify selected Reduction tag on available field and assign
	 *               selected fields
	 */
	public void verifySelectReductionAssignedInSelectedList(String Redacname) {
		driver.waitForPageToBeReady();
		SoftAssert softassert = new SoftAssert();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReductionCheckBox(Redacname).Visible();
			}
		}), Input.wait90);
		System.out.println(Redacname);
		softassert.assertTrue((getReductionCheckBox(Redacname).isDisplayed()));
		bc.stepInfo("Selected Tags is displayed on available list");
		bc.waitTime(5);
		bc.waitTillElemetToBeClickable(getReductionCheckBox(Redacname));
		getReductionCheckBox(Redacname).waitAndClick(5);
		driver.waitForPageToBeReady();
		getSG_Reduction_Right().waitAndClick(5);
		bc.waitForElement(getSelectedReductionCheckBox(Redacname));
		System.out.println(Redacname);
		softassert.assertTrue(getSelectedReductionCheckBox(Redacname).isElementAvailable(5));
		if (getSelectedReductionCheckBox(Redacname).isElementAvailable(5)) {
			bc.passedStep(Redacname + "is displayed on Rightside Selected list");

		} else {
			bc.failedMessage("folder is not displayed");
		}
	}

	/*
	 * @author Vijaya.Rani
	 * 
	 */
	public void addAnnotationlayertosg(String securityGroup, String annotation) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		bc.waitForElement(getSecurityGroupList());
		getSecurityGroupList().selectFromDropdown().selectByVisibleText(securityGroup);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSG_AnnLayerbutton().Visible();
			}
		}), Input.wait30);
		getSG_AnnLayerbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSG_AnnotationLayer(annotation).Visible();
			}
		}), Input.wait90);
		System.out.println(annotation);
		bc.waitTime(5);
		bc.waitTillElemetToBeClickable(getSG_AnnotationLayer(annotation));
		getSG_AnnotationLayer(annotation).waitAndClick(5);
		driver.waitForPageToBeReady();
		getSG_AddAnnLayer_Right().waitAndClick(5);

		getSG_AnnSaveButton().waitAndClick(15);
		bc.VerifySuccessMessage("Your selections were saved successfully");
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Krishna date:Modified date:
	 * @Description: verify selected Project filed available field and assign
	 *               selected fields
	 */
	public void verifySelectedProjectField(String field) {
		driver.waitForPageToBeReady();
		SoftAssert softassert = new SoftAssert();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProjectFieldCheckBox(field).Visible();
			}
		}), Input.wait90);
		System.out.println(field);
		softassert.assertTrue((getProjectFieldCheckBox(field).isDisplayed()));
		bc.passedStep("Project' list is getting populated for ProjectFields is successfully");
		bc.waitTime(5);
		bc.waitTillElemetToBeClickable(getProjectFieldCheckBox(field));
		getProjectFieldCheckBox(field).waitAndClick(5);
		driver.waitForPageToBeReady();
		getSG_Field_Right().waitAndClick(5);
		bc.passedStep("Field is displayed on all available list ");
	}

	/**
	 * @author Krishna
	 * @description: Get notification msg
	 * @param bgCountBefore
	 */
	public int getNotificationMessage(int bgCountBefore) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == bgCountBefore + 1;
			}
		}), Input.wait120);
		final int bgCountAfter = bc.initialBgCount();

		if (bgCountAfter > bgCountBefore) {
			bc.getBullHornIcon().waitAndClick(10);

		} else {
			driver.Navigate().refresh();
		}
		return bgCountAfter;
	}

	/**
	 * @author Krishna Modified date:NA
	 * @Description: selected annotation layer is moved in selected list
	 */
	public void getAssignedAnnotationLayerAddedSg(String annotationTag) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSG_AnnLayerbutton().Visible();
			}
		}), Input.wait30);
		getSG_AnnLayerbutton().waitAndClick(5);
		bc.stepInfo("Selected Annotation layer");
		bc.waitForElement(getAnnotationLayerCheckBox(annotationTag));
		getAnnotationLayerCheckBox(annotationTag).waitAndClick(10);
		bc.waitForElement(getSG_AddAnnLayer_Right());
		getSG_AddAnnLayer_Right().waitAndClick(15);
		if (getAnnotationLayerCheckBox(annotationTag).isElementAvailable(5)) {
			bc.passedStep(annotationTag + "   Selected Annotation Layer is displayed in the Selected list. ");

		} else {
			bc.failedMessage("Annotation layer is not displayed");
		}
	}

	/**
	 * @author Krishna Modified date:NA
	 * @Description: Unassigned selected annotation layer is removed in selected
	 *               list
	 */
	public void unAssigningTheTagInAnnotation(String Sgannotation, String annotation) {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectedAnnotationLayerCheckBox(Sgannotation).Visible();
			}
		}), Input.wait90);
		System.out.println(Sgannotation);
		bc.waitTime(3);
		bc.waitForElement(getSelectedAnnotationLayerCheckBox(Sgannotation));
		getSelectedAnnotationLayerCheckBox(Sgannotation).waitAndClick(10);
		bc.waitForElement(getSG_AddAnnotationLayer_Left());
		getSG_AddAnnotationLayer_Left().waitAndClick(10);
		bc.waitTime(3);
		bc.waitForElement(getAnnotationLayerCheckBox(annotation));
		if (getAnnotationLayerCheckBox(annotation).isDisplayed()) {
			bc.passedStep(annotation + "After removed from selected list is displayed on Available list");

		} else {
			bc.failedStep(annotation + "After removed annotation layer is not displayed on available list");

		}

	}

	/**
	 * @author Krishna Modified date:NA
	 * @Description: selected annotation layer is moved in selected list getting
	 *               warning msg
	 */
	public void verifyAssignedlayertoSgInWarningMsg() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSG_AddAnnLayer().Visible();
			}
		}), Input.wait30);
		getSG_AddAnnLayer().waitAndClick(15);
		System.out.println("Selected another Annotation layer");
		bc.stepInfo("Selected another Annotation layer");
		getSG_AddAnnLayer_Right().waitAndClick(15);
		try {
			bc.VerifyWarningMessage("Cannot add more than one security group. "
					+ "A security group can have only one annotation layer at a time.");
			bc.CloseSuccessMsgpopup();
		} catch (Exception e) {
			System.out.println("Annotation layer already added");
			UtilityLog.info("Annotation layer already added");
		}

	}
	
	/**
	 * @Author jeevitha
	 * @Dsecription : unmap Tag from SG
	 * @param securityGrp
	 * @param tagName
	 */
	public void unmapTagFromSecurityGrp(String securityGrp, String tagName) {
		selectSecurityGroup(securityGrp);

		bc.waitForElement(getTagsLink());
		bc.waitTillElemetToBeClickable(getTagsLink());
		getTagsLink().waitAndClick(10);

		bc.waitForElement(getSelectedTagsCheckBox(tagName));
		bc.waitTillElemetToBeClickable(getSelectedTagsCheckBox(tagName));
		getSelectedTagsCheckBox(tagName).waitAndClick(5);
		bc.stepInfo("Selected Tag : " + tagName);

		bc.waitForElement(getSG_Tag_Left());
		getSG_Tag_Left().waitAndClick(10);

		bc.waitForElement(getSG_AnnSaveButton());
		bc.waitTillElemetToBeClickable(getSG_AnnSaveButton());
		getSG_AnnSaveButton().waitAndClick(10);
		bc.VerifySuccessMessage("Your selections were saved successfully");
		bc.passedStep("Unmapped Tag " + tagName + " from : " + securityGrp);
	}
	
	/**
	 * @author Krishna date:NA Modified date:NA
	 * @Description: verify selected comments on avalaible field and assign selected
	 *               fields
	 */
	public void verifySelectCommentisAssignedInSelectedList(String foldername) {
		driver.waitForPageToBeReady();
		SoftAssert softassert = new SoftAssert();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCommentsCheckBox(foldername).Visible();
			}
		}), Input.wait90);
		System.out.println(foldername);
		softassert.assertTrue((getCommentsCheckBox(foldername).isDisplayed()));
		bc.stepInfo("Selected comments is displayed on available list");
		bc.waitTillElemetToBeClickable(getCommentsCheckBox(foldername));
		getCommentsCheckBox(foldername).waitAndClick(5);
		driver.waitForPageToBeReady();
		getSG_Comment_Right().waitAndClick(5);
		bc.waitForElement(getSelectedCommentsCheckBox(foldername));
		System.out.println(foldername);
		softassert.assertTrue(getSelectedCommentsCheckBox(foldername).isElementAvailable(5));
		if (getSelectedCommentsCheckBox(foldername).isElementAvailable(5)) {
			bc.passedStep(foldername + "is displayed on Rightside Selected list");

		} else {
			bc.failedMessage("folder is not displayed");
		}
	}

	/**
	 * @author Krishna date:NA Modified date:NA
	 * @Description: verify selected Keywords on avalaible field and assign selected
	 *               fields
	 */
	public void verifySelectKeywordsAssignedInSelectedList(String foldername) {
		driver.waitForPageToBeReady();
		SoftAssert softassert = new SoftAssert();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getKeyWordsCheckBox(foldername).Visible();
			}
		}), Input.wait90);
		System.out.println(foldername);
		softassert.assertTrue((getKeyWordsCheckBox(foldername).isDisplayed()));
		bc.stepInfo("Selected comments is displayed on available list");
		bc.waitTillElemetToBeClickable(getKeyWordsCheckBox(foldername));
		getKeyWordsCheckBox(foldername).waitAndClick(5);
		driver.waitForPageToBeReady();
		getSG_Keyword_Right().waitAndClick(5);
		bc.waitForElement(getSelectedKeyWordCheckBox(foldername));
		System.out.println(foldername);
		softassert.assertTrue(getSelectedKeyWordCheckBox(foldername).isElementAvailable(5));
		if (getSelectedKeyWordCheckBox(foldername).isElementAvailable(5)) {
			bc.passedStep(foldername + "is displayed on Rightside Selected list");

		} else {
			bc.failedMessage("folder is not displayed");
		}
	}
}
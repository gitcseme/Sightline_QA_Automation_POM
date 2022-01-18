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
		return driver.FindElementById("ddlSecurityGroupsList");
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

	public SecurityGroupsPage(Driver driver) {

		this.driver = driver;
		bc = new BaseClass(driver);
		// this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		driver.waitForPageToBeReady();

	}

	//modified for stabilisation
	public void AddSecurityGroup(String securitygroupname) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSecurityGroupCreateButton().Visible();
			}
		}), Input.wait60);
		getSecurityGroupCreateButton().waitAndClick(5);

		Actions action = new Actions(driver.getWebDriver());
		action.moveToElement(driver.getWebDriver().findElement(By.xpath("//button[text()='Create']"))).click()
				.perform();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSecurityGroupName().Visible();
			}
		}), Input.wait30);
		getSecurityGroupName().SendKeys(securitygroupname);

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
		securityGroupCreate().Click();
		securityGroupText().Click();
		securityGroupText().SendKeys(securityGroupName);
		securityGroupSave().Click();
		securityGroupText().Click();
		securityGroupText().SendKeys(securityGroupName);
		securityGroupSave().Click();
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
	 */
	public void addFolderToSecurityGroup(String securityGroupName, String folderName) {
		try {
			bc.waitForElement(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroupName);
			driver.waitForPageToBeReady();
			bc.waitForElement(getFolder(folderName));
			for (int i = 0; i < 7; i++) {
				try {
					getFolder(folderName).Click();
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
			bc.waitForElement(getFolderRightShiftButton());
			getFolderRightShiftButton().Click();
			getProjectLevelEmailCheckBox().Click();
			try {
				getYesButton().Click();
			} catch (Exception e) {
				getSG_AnnSaveButton().Click();
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
			bc.stepInfo("Selected "+securityGroupName+" from available SG's");
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
			getAnnotationLayerLink().Click();
			bc.waitForElement(getAnnatationLayerTable());
			bc.waitTillElemetToBeClickable(getAnnatationLayerTable());
			getAnnatationLayerTable().Click();

			bc.waitForElement(getAnnotation(annotation));
			bc.waitTillElemetToBeClickable(getAnnotation(annotation));
			getAnnotation(annotation).Click();
			bc.waitForElement(getAnnotationRightShiftButton());
			getAnnotationRightShiftButton().Click();
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
				bc.passedStep("Success message is displayed successfully for this reduction "+reductionTag);
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
			bc.waitForElement(getReductionTagLink());
			bc.waitTillElemetToBeClickable(getReductionTagLink());
			getReductionTagLink().Click();
			bc.waitForElement(getUnReductionLayerTable());
			bc.waitTillElemetToBeClickable(getUnReductionLayerTable());
			getUnReductionLayerTable().Click();
			bc.waitForElement(getUnReductionTag(tagName));
			bc.waitTillElemetToBeClickable(getUnReductionTag(tagName));
			getUnReductionTag(tagName).Click();
			bc.waitForElement(getReductionTagLeftShiftButton());
			getReductionTagLeftShiftButton().Click();
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
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return redactionTags().Enabled();
			}
		}), Input.wait30);
		redactionTags().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return

				SelectredactionCheckBox(Tag).Enabled();
			}
		}), Input.wait30);
		SelectredactionCheckBox(Tag).waitAndClick(10);
		SelectredactionCheckBox(Tag).ScrollTo();
		driver.waitForPageToBeReady();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSG_AddAnnLayer_Left().Enabled();
			}
		}), Input.wait120);
		getSG_AddAnnLayer_Left().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSG_AnnSaveButton().Enabled();
			}
		}), Input.wait120);
		getSG_AnnSaveButton().Click();
	}

	/**
	 * @author Jeevitha Description : Assigning Annotation layer to The Security
	 *         Group
	 * @param annotation
	 */
	public void assignAnnotationToSG(String annotation) {

		driver.waitForPageToBeReady();
		bc.waitForElement(getAnnotationLayerLink());
		getAnnotationLayerLink().waitAndClick(10);

		Actions actions = new Actions(driver.getWebDriver());
		actions.sendKeys(Keys.SPACE).build().perform();

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

}
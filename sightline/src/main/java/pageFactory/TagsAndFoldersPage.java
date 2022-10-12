package pageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class TagsAndFoldersPage {

	Driver driver;
	BaseClass base;
	LoginPage lp;
	int j, k;

	public Element getTag_ToggleDocCount() {
		return driver.FindElementById("tagDocCount");
	}

	public Element getTagandCount(String TagName, int count) {
		return driver.FindElementByXPath("//*[@id='-1']/ul/li//a[contains(text(),'" + TagName + " (" + count + ")')]");
	}

	public Element getFolder_ToggleDocCount() {
		return driver.FindElementById("folderDocCount");
	}

	public Element getFolderandCount(String FolderName, int count) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-children']//a[contains(text(),'" + FolderName + " (" + count + ")')]");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	public Element getAllTagRoot() {
		return driver.FindElementById("-1_anchor");
	}

	public Element getAddTag() {
		return driver.FindElementById("aAddTag");
	}

	public Element getTagName() {
		return driver.FindElementByXPath("//input[@name='txtTagName']");
	}

	public Element getSaveTag() {
		return driver.FindElementById("btnAddTag");
	}

	public Element getTagActionDropDownArrow() {
		return driver.FindElementByXPath(".//*[@id='tabs-a']/div[1]/div/button[2]");
	}

	public Element getFoldersTab() {
		return driver.FindElementByXPath("//*[@class='tags-folders']//a[contains(text(),'Folders')]");
	}

	public Element getTagsTab() {
		return driver.FindElementById("ui-id-1");
	}

	public Element getAllFolderRoot() {
		return driver.FindElementByXPath("//a[contains(text(),'All Folders')]");
	}

	public Element getSecurityGroup() {
		return driver.FindElementById("ddlSecurityGroupFolder");
	}

	public Element getAddFolder() {
		return driver.FindElementById("aAddFolder");
	}

	public Element getFolderName() {
		return driver.FindElementByXPath("//input[@name='txtFolderName']");
	}

	public Element getSaveFolder() {
		return driver.FindElementById("btnAddFolder");
	}

	public Element getFolderActionDropDownArrow() {
		return driver.FindElementByXPath("//*[@id='tabs-b']/div[1]/div/button[2]");
	}

	// added on 4th feb
	public Element getTagName(String TagName) {
		return driver.FindElementByXPath("//a[contains(text(),'" + TagName + "')]");
	}

	public Element getFolderName(String FolderName) {
		return driver.FindElementByXPath("//a[contains(text(),'" + FolderName + "')]");
	}

	public Element getDeleteTag() {
		return driver.FindElementById("aDeleteTagTagGroup");
	}

	public Element getDeleteFolder() {
		return driver.FindElementById("aDeleteFolderFolderGroup");
	}

	// Security group select while creating tag and folder
	public Element getTagSecutiryGroup() {
		return driver.FindElementById("ddlSecurityGroupTag");
	}

	public Element getFolderSecutiryGroup() {
		return driver.FindElementById("ddlSecurityGroupFolder");
	}

	// added on 05/06
	public Element getSecurityGroupTag() {
		return driver.FindElementById("ddlSecurityGroupTag");
	}

	public Element getTagClassification() {
		return driver.FindElementById("ddlTagClassificationName");
	}

	public Element getTagViewDoclist() {
		return driver.FindElementById("aViewTagInDocList");
	}

	public Element getTagViewDocView() {
		return driver.FindElementById("aViewTagInDocView");
	}

	public Element getFolderViewDoclist() {
		return driver.FindElementById("aViewFolderInDocList");
	}

	public Element getFolderViewDocView() {
		return driver.FindElementById("aViewFolderInDocView");
	}

	// added- System Level Template - Narendra
	public Element getDefaultTag() {
		return driver.FindElementByXPath("//a[contains(text(),'Default Tags')]");
	}

	public Element getDefaultTagsArrow() {
		return driver.FindElementByXPath("//li[@id='1']//i[@class='jstree-icon jstree-ocl']");
	}

	public ElementCollection getDefaultTagsCount() {
		return driver.FindElementsByXPath("//a[@data-content='Default Tags']/following-sibling::ul/li");
	}

	public Element getDefaultSecgrp() {
		return driver.FindElementByXPath(
				"//select[@id='ddlSecurityGroupTag']//option[contains(text(),'Default Security Group')]");
	}

	public Element action() {
		return driver.FindElementByXPath("//button[@data-toggle='dropdown']/following-sibling::ul/li[3]");
	}

	public Element actionarrow() {
		return driver.FindElementByXPath("//button[@data-toggle='dropdown']");
	}

	public Element getSelectFolders() {
		return driver.FindElementByXPath("//div[starts-with(@id,'tabs')]/ul/li/a[contains(text(),'Folders')]");
	}

	public Element getAllFolders() {
		return driver.FindElementByXPath("//a[@id='-1_anchor']");
	}

	public ElementCollection getFolderList() {
		return driver.FindElementsByXPath("//div[starts-with(@id,'folderJSTree')]//ul//li");
	}

	public Element getDefaultSecgrpClick() {
		return driver.FindElementByXPath("//select[@id='ddlSecurityGroupFolder']");
	}

	public Element getFoldersDefaultSecgrp() {
		return driver.FindElementByXPath(
				"//select[@id='ddlSecurityGroupFolder']//option[contains(text(),'Default Security Group')]");
	}

	public Element getreductionclick() {
		return driver.FindElementByXPath(
				"//div[starts-with(@id,'tagsJSTree')]/ul/li/a[contains(text(),'All Redaction Tags')]");
	}

	public ElementCollection getReductionList() {
		return driver.FindElementsByXPath("//div[starts-with(@id,'tagsJSTree')]//ul//li");
	}

	public Element getRedAllgrp() {
		return driver.FindElementByXPath("//select[@id='ddlSecurityGroupRedaction']");
	}

	public Element getRedDefaultSecgrp() {
		return driver.FindElementByXPath(
				"//select[@id='ddlSecurityGroupRedaction']//option[contains(text(),'Default Security Group')]");
	}

	public Element rightarrow() {
		return driver.FindElementByXPath("(//span[@class='caret'])[1]");
	}

	public Element getEditClick() {
		return driver.FindElementByXPath("//a[@id='aEditTagTagGroup']");
	}

	public Element getLayerClicked() {
		return driver.FindElementById("AnnotationsDatatable");
	}

	public ElementCollection getLayerList() {
		return driver.FindElementsByXPath("//table[@id='AnnotationsDatatable']//tbody//tr//td[@class='sorting_1']");
	}

	public Element getAnnoDefaultSecgrp() {
		return driver.FindElementByXPath(
				"//select[@id='ddlSecurityGroup']//option[contains(text(),'Default Security Group')]");
	}

	public Element getDefaulttagallele(String ele) {
		return driver.FindElementByXPath("//a[@data-content='" + ele + "']");
	}

	public Element getTagclick() {
		return driver.FindElementByXPath("//select[@id='ddlTagClassificationName']");
	}

	public ElementCollection getAllTagClassification() {
		return driver.FindElementsByXPath("//select[@id='ddlTagClassificationName']//option");
	}

	public Element getCancel() {
		return driver.FindElementByXPath("//button[@id='btnUpdateTagCancel']");
	}

	public Element getAllFolderClick() {
		return driver.FindElementByXPath("//a[@id='-1_anchor']");
	}

	public Element getCancelTag() {
		return driver.FindElementById("btnAddTagCancel");
	}

	public Element getErrorMsg() {
		return driver.FindElementById("tagErrorMsg");
	}

	public ElementCollection getTagGroup() {
		return driver.FindElementsByXPath("//a[contains(@class,'jstree-anchor tag-groups')]");
	}

	public Element getEditTag() {
		return driver.FindElementById("aEditTagTagGroup");
	}

	public Element getTagGroupName() {
		return driver.FindElementById("txtTagGroupName");
	}

	// added by sowndariya
	
	public Element getPropFolderExactDuplic() {
		return driver.FindElementByXPath("//input[@id='chkFolderExactDuplicates']//following-sibling::i");
	}
	
	public Element getErrorMsgPopup() {
		return driver.FindElementByXPath("//div[@id='tagErrorMsg']");
	}
	
	public Element getBulkTagConfirmationButton() {
		return driver.FindElementByXPath("//button[contains(text(),'Ok')]");
	}
	
	public Element getContinueCount() {
		return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']");
	}

	public Element getContinueButton() {
		return driver.FindElementByXPath(".//*[@id='divBulkAction']//button[contains(.,'Continue')]");
	}

	public Element getEnterTagName() {
		return driver.FindElementById("txtTagName");
	}
	
	public Element getTagErrorMsg() {
		return driver.FindElementByXPath("//div[text()='Please enter tag name']");
	}

	public Element getHelpButton() {
		return driver.FindElementByXPath("//h1[text()='Tags and Folders']//following-sibling::a");
	}

	public Element getHelpText() {
		return driver.FindElementByXPath("//div[@class='popover-content contains-table']");
	}
	public Element getPropTagFamilyMember() {
		return driver.FindElementByXPath("//input[@id='chkTagFamilyMember']//following-sibling::i");
	}
	public Element getPropTagExactDuplic() {
		return driver.FindElementByXPath("//input[@id='chkTagExactDuplicates']//following-sibling::i");
	}
	public Element getProbTagEmailThred() {
		return driver.FindElementByXPath("//input[@id='chkTagEmailThreads']//following-sibling::i");
	}
	public Element getProbTagEmailDupli() {
		return driver.FindElementByXPath("//input[@id='chkTagEmailDuplicates']//following-sibling::i");
	}
	public ElementCollection getAllProbTags() {
		return driver.FindElementsByXPath("//div[@id='modalTagAdd']//label[@class='checkbox']");
	}
	
	public Element getAllTags() {
		return driver.FindElementByXPath("//div[@id='tagsJSTree']//a[contains(text(),'All Tags')]");
	}
	public Element getNewTag() {
		return driver.FindElementByXPath("//ul[@id='Tab1']//a[contains(text(),'New Tag')]");
	}
	public Element getCreatedTag(String tag) {
		return driver.FindElementByXPath("//div[@id='tagsJSTree']//a[contains(text(),'" + tag +"')]");
	}

	public Element gettxtTagClassification() {
		return driver.FindElementByXPath("//*[contains (text(),'Tag Classification: ')]");
	}

	public Element getbtnTagCancel() {
		return driver.FindElementById("btnAddTagCancel");
	}

	public Element gettxtPropogateFolderTo() {
		return driver.FindElementByXPath("//label[contains(text(),'Propagate Folder To: ')]");
	}

	public Element getbtnFolderCancel() {
		return driver.FindElementById("btnAddFolderCancel");
	}

	// Added by Gopinath

	public Element getTagNameDataCon(String tagName) {
		return driver.FindElementByXPath("//li[@role='treeitem']//a[@data-content='" + tagName + "']");
	}

	// added by sowndariya

	public Element getAddTagTable() {
		return driver.FindElementById("tagsJSTree");
	}

	// Added by Gopinath -- 29-09-2021
	/**
	 * public Element getTag(int rowNum) { return driver.FindElementByXPath(
	 * "//a[contains(@data-content,'All Tags')]/..//i[@class='jstree-icon
	 * jstree-themeicon fa fa-tags jstree-themeicon-custom']//..//..//li[" + rowNum
	 * + "]//a"); }
	 **/

	public Element getTag(int rowNum) {
		return driver
				.FindElementByXPath("//a[contains(@data-content,'All Tags')]/../descendant::li[" + rowNum + "]//a");
	}

	public ElementCollection getTagsRows() {
		return driver.FindElementsByXPath(
				"//a[contains(@data-content,'All Tags')]/..//i[@class='jstree-icon jstree-themeicon fa fa-tags jstree-themeicon-custom']//..//..//li//a");
	}

	public Element getShowDocumentsCountToogle() {
		return driver.FindElementByXPath("//input[@id='IsActiveTagCount']/following-sibling::i");
	}

	public Element getTagsTree() {
		return driver.FindElementById("tagsJSTree");
	}

	public Element getOpenDefaultTag() {
		return driver.FindElementByXPath(
				"//a[contains(@data-content,'Default Tags')]/..//i[@class='jstree-icon jstree-themeicon fa fa-tags jstree-themeicon-custom']//..//..//i[@class='jstree-icon jstree-ocl']");
	}

	// Added by gopinath -- 04/10/2021
	public Element SelectFolderCheckbox(String folder) {
		return driver.FindElementByXPath("//div[@id='folderJSTree']//a[contains(text(),'" + folder + "')]");
	}

	// Added by Raghuram
	public Element getBulkReleaseFolder() {
		return driver.FindElementByXPath("//a[@id='aReleaseFolder']");
	}

	public Element getBulkReleaseAction() {
		return driver.FindElementByXPath("//a[@id='aReleaseTag']");
	}

	public Element getNewTagGroupAction() {
		return driver.FindElementByXPath("//a[@id='aAddTagGroup']");
	}

	public Element getNewTagGroupPopup() {
		return driver.FindElementByXPath("//span[text()='Add Tag Group']");
	}

	public Element getEditTagGroupPopup() {
		return driver.FindElementByXPath("//span[text()='Edit Tag Group']");
	}

	public Element getNewTagGroupInputTextBox() {
		return driver.FindElementByXPath("//input[@id='txtTagGroupName']");
	}

	public Element getNewTagGroupSaveBtn() {
		return driver.FindElementByXPath("//button[@id='btnAddTagGroup']");
	}

	public Element getUpdateTagGroupSaveBtn() {
		return driver.FindElementByXPath("//button[@id='btnUpdateTagGroup']");
	}

	public Element getNewFOlderGroupAction() {
		return driver.FindElementByXPath("//a[@id='aAddFolderGroup']");
	}

	public Element getNewFolderGroupInputTextBox() {
		return driver.FindElementByXPath("//input[@id='txtFolderGroupName']");
	}

	public Element getNewFolderGroupSaveBtn() {
		return driver.FindElementByXPath("//button[@id='btnAddFolderGroup']");
	}

	public Element getNewFolderGroupPopup() {
		return driver.FindElementByXPath("//span[text()='New Folder Group']");
	}

	public Element getEditFolderGroup() {
		return driver.FindElementByXPath("//a[@id='aEditFolderFolderGroup']");
	}

	public Element getEditFolderGroupPopup() {
		return driver.FindElementByXPath("//span[text()='Edit Folder Group']");
	}

	public Element getUpdateFolderGroupSaveBtn() {
		return driver.FindElementByXPath("//button[@id='btnUpdateFolderGroup']");
	}

	// a[@class='jstree-anchor tag-groups' and text()='Check']
	// getTagNameDataCon

	////

	public Element getBulkRelDefaultSecurityGroup_CheckBox(String SG) {
		return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[text()='" + SG + "']/../div[1]/label/i");
	}

	public Element getBulkRelOther_CheckBox(String SGname) {
		return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[text()='" + SGname + "']/../div[1]/label/i");
	}

	public Element getBulkRelease_ButtonRelease() {
		return driver.FindElementById("btnRelease");
	}

	public Element getFinalCount() {
		return driver.FindElementByXPath("//span[@id='spanTotal']");
	}

	public Element getFinalizeButton() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	// added by jayanthi
	public Element getTagOrFolderSelected(String tagName) {
		return driver.FindElementByXPath(
				"//a[contains(text(),'" + tagName + "') and @class='jstree-anchor jstree-clicked']");
	}

	public ElementCollection getAllTagsOrFolder(String tagName) {
		return driver.FindElementsByXPath("//a[contains(text(),'" + tagName + "')]");
	}

	public Element getTagOrFolder(String tagName) {
		return driver.FindElementByXPath("(//a[contains(text(),'" + tagName + "')])");
	}

	public Element getEditFolderPopup() {
		return driver.FindElementByXPath("//span[text()='Edit Folder']");
	}

	public Element getNewFolderInputTextBox() {
		return driver.FindElementByXPath("//input[@id='txtFolderName']");
	}

	public Element getUpdateFolderSaveBtn() {
		return driver.FindElementByXPath("//button[@id='btnUpdateFolder']");
	}

	public Element getEditFolderWaringMessagePopupTitle() {
		return driver.FindElementByXPath("//span[text()='Edit Folder' and @class='MsgTitle']");
	}

	public Element getEditFolderWaringMessageText() {
		return driver.FindElementByXPath("//span[text()='Edit Folder' and @class='MsgTitle']/../following-sibling::p/label");
	}

	public Element getEditTagPopup() {
		return driver.FindElementByXPath("//span[text()='Edit Tag']");
	}

	public Element getUpdateTagSaveBtn() {
		return driver.FindElementByXPath("//button[@id='btnUpdateTag']");
	}

	public Element getEditTagWaringMessagePopupTitle() {
		return driver.FindElementByXPath("//span[@class='MsgTitle' and text()='Edit Tag']");
	}

	public Element getEditTagWaringMessageText() {
		return driver.FindElementByXPath("//span[text()='Edit Tag' and @class='MsgTitle']/../following-sibling::p/label");
	}

//Added by Krishna
	public ElementCollection saMenuItems() {
		return driver.FindElementsByXPath("//a[@class = \"a-menu\"]");
	}

	public Element saManageTab() {
		return driver.FindElementByXPath("//a[@name = 'Manage']");
	}

	public ElementCollection leftMenuItems() {
		return driver.FindElementsByXPath("//a[@class = 'a-menu']");
	}

	// Added by Mohan
	public ElementCollection getTotFolderCount() {
		return driver.FindElementsByXPath("//ul[@class='jstree-children']//a");
	}

	public Element getFolderExpand() {
		return driver.FindElementByXPath("//div[@id='folderJSTree']/ul/li/a/following-sibling::ul");
	}

	public Element getFolderExpandIcon() {
		return driver.FindElementByXPath("//div[@id='folderJSTree']/ul/li/i");
	}
	// Added by arun
	public Element getReleaseCancelButton() {
		return driver.FindElementById("btnReleaseCancel");
	}
	
	public Element getBulkRelease_ButtonUnRelease() {
        return driver.FindElementById("btnUnrelease");
    }
	public Element getTagPropagatePopup() {
		return driver.FindElementByXPath("//*[contains(text(),'Propagate Tag To: ')]/..//a");
	}

	public Element getTextInTag() {
		return driver.FindElementByXPath("//*[contains(text(),'Propagate Tag To: ')]/..//p");
	}
	public TagsAndFoldersPage(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
//		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
	}

	// securityGroup parameter value will be secGroup name else 'All Groups'
	public void CreateTag(String strtag, String securityGroup) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsTab().Visible();
			}
		}), Input.wait30);
		try {
			getTagsTab().waitAndClick(10);
		} catch (Exception e) {
			// may be in tag already
		}
		try {
			// Select secGroup
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTagSecutiryGroup().Visible();
				}
			}), Input.wait30);
			getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Select root all
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAllTagRoot().Visible();
			}
		}), Input.wait30);
		getAllTagRoot().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagActionDropDownArrow().Visible();
			}
		}), Input.wait30);
		getTagActionDropDownArrow().waitAndClick(10);

		Thread.sleep(1000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddTag().Visible();
			}
		}), Input.wait30);
		getAddTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagName().Visible();
			}
		}), Input.wait30);
		getTagName().SendKeys(strtag);

		Thread.sleep(1000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveTag().Visible();
			}
		}), Input.wait30);
		getSaveTag().waitAndClick(10);
		base.VerifySuccessMessage("Tag added successfully");
		Reporter.log("Tag '" + strtag + "' is added successfully to security group " + securityGroup, true);
		UtilityLog.info("Tag Successful");

		base.CloseSuccessMsgpopup();

	}

	public void CreateFolder(String strFolder, String securityGroup) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFoldersTab().Enabled();
			}
		}), Input.wait60);

		try {
			getFoldersTab().waitAndClick(10);
		} catch (Exception e) {
			// may be in folder already
		}
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSecurityGroup().Enabled();
				}
			}), Input.wait30);
			getSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		} catch (Exception e) {
			// TODO: handle exception
		}

		base.waitForElement(getAllFolderRoot());

		driver.waitForPageToBeReady();
		getAllFolderRoot().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderActionDropDownArrow().Enabled();
			}
		}), Input.wait30);
		getFolderActionDropDownArrow().waitAndClick(10);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);

		base.waitForElement(getAddFolder());
		getAddFolder().waitAndClick(5);

		if (!gettxtPropogateFolderTo().Enabled()) {

			base.waitForElement(getbtnFolderCancel());
			getbtnFolderCancel().Click();

			base.waitForElement(getAddFolder());
			getAddFolder().Click();

		}

		base.waitForElement(getFolderName());
		getFolderName().SendKeys(strFolder);

		base.stepInfo("New folder created");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveFolder().Enabled();
			}
		}), Input.wait30);
		getSaveFolder().waitAndClick(10);

		base.VerifySuccessMessage("Folder added successfully");
		base.CloseSuccessMsgpopup();
		Reporter.log("Folder " + strFolder + " added to security group -" + securityGroup, true);
		UtilityLog.info("Folder Successful");
	}

	/*
	 * @author: Indium Aathith method for creating folders in RMU user
	 *
	 */
	public void CreateFolderInRMU(String strFolder) throws InterruptedException {
		navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFoldersTab().Enabled();
			}
		}), Input.wait60);

		try {
			getFoldersTab().waitAndClick(10);
		} catch (Exception e) {
			// may be in folder already
		}

		base.waitForElement(getAllFolderRoot());

		driver.waitForPageToBeReady();
		getAllFolderRoot().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderActionDropDownArrow().Enabled();
			}
		}), Input.wait30);
		getFolderActionDropDownArrow().waitAndClick(10);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);

		base.waitForElement(getAddFolder());
		getAddFolder().waitAndClick(5);

		if (!gettxtPropogateFolderTo().Enabled()) {

			base.waitForElement(getbtnFolderCancel());
			getbtnFolderCancel().Click();

			base.waitForElement(getAddFolder());
			getAddFolder().Click();

		}

		base.waitForElement(getFolderName());
		getFolderName().SendKeys(strFolder);

		base.stepInfo("New folder created");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveFolder().Enabled();
			}
		}), Input.wait30);
		getSaveFolder().waitAndClick(10);

		base.VerifySuccessMessage("Folder added successfully");
		base.CloseSuccessMsgpopup();
		Reporter.log("Folder " + strFolder + " added", true);
		UtilityLog.info("Folder Successful");
	}

//Modified by jayanthi-Indium 24/8/21 added driver.scrollPageToTop();
	public void DeleteTag(final String strtag, String securityGroup) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsTab().Visible();
			}
		}), Input.wait30);
		getTagsTab().waitAndClick(10);

		// Select secGroup
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTagSecutiryGroup().Visible();
				}
			}), Input.wait30);
			getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagName(strtag).Visible();
			}
		}), Input.wait30);
		getTagName(strtag).waitAndClick(10);
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagActionDropDownArrow().Visible();
			}
		}), Input.wait30);
		getTagActionDropDownArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDeleteTag().Visible();
			}
		}), Input.wait30);
		getDeleteTag().waitAndClick(10);

		base.getYesBtn().waitAndClick(10);

		base.VerifySuccessMessage("Tag deleted successfully");
		base.CloseSuccessMsgpopup();

		Reporter.log(strtag + "tag delete Successful", true);
		UtilityLog.info("Tag delete Successful");

	}

//Modified by jayanthi 24/8/21 Added ScrollPageToTop 
	public void DeleteFolder(final String strFolder, String securityGroup) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFoldersTab().Visible();
			}
		}), Input.wait30);
		getFoldersTab().waitAndClick(10);

		// Select secGroup
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFolderSecutiryGroup().Visible();
				}
			}), Input.wait30);
			getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		} catch (Exception e) {
			// TODO: handle exception
		}

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderName(strFolder).Visible();
			}
		}), Input.wait30);
		getFolderName(strFolder).waitAndClick(10);
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderActionDropDownArrow().Visible();
			}
		}), Input.wait30);
		getFolderActionDropDownArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDeleteFolder().Visible();
			}
		}), Input.wait30);
		getDeleteFolder().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.getYesBtn().Visible();
			}
		}), Input.wait30);
		base.getYesBtn().waitAndClick(10);

		base.VerifySuccessMessage("Folder deleted successfully");
		base.CloseSuccessMsgpopup();
		Reporter.log(strFolder + " Folder delete Successful", true);
		UtilityLog.info("Folder delete Successful");

	}

	/**
	 * @author Indium sowndarya This method deletes the folder created in security
	 *         groups
	 * @param strFolder
	 * @param securityGroup
	 */
	public void DeleteFolderWithSecurityGroup(final String strFolder, String securityGroup) {

		navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();

		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);

		// Select secGroup
		try {
			base.waitForElement(getFolderSecutiryGroup());
			getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);

			driver.scrollingToBottomofAPage();

			base.waitForElement(getFolderName(strFolder));
			getFolderName(strFolder).waitAndClick(5);

			driver.scrollPageToTop();

			base.waitForElement(getFolderActionDropDownArrow());
			getFolderActionDropDownArrow().waitAndClick(5);

			base.waitForElement(getDeleteFolder());
			getDeleteFolder().waitAndClick(5);

			base.waitForElement(base.getYesBtn());
			base.getYesBtn().waitAndClick(5);

		} catch (Exception e) {
//			e.printStackTrace();
			base.waitForElement(getFolderName(strFolder));
			getFolderName(strFolder).waitAndClick(5);

			driver.scrollPageToTop();

			base.waitForElement(getFolderActionDropDownArrow());
			getFolderActionDropDownArrow().waitAndClick(5);

			base.waitForElement(getDeleteFolder());
			getDeleteFolder().waitAndClick(5);

			base.waitForElement(base.getYesBtn());
			base.getYesBtn().waitAndClick(5);
		}

		base.VerifySuccessMessage("Folder deleted successfully");
		base.CloseSuccessMsgpopup();
		Reporter.log(strFolder + " Folder delete Successfully", true);
		UtilityLog.info("Folder deleted Successfully");
	}

	/**
	 * @author Indium Aathith This method deletes the folder created in security
	 *         groups for RMU user.Modified on 03/08/2022
	 * @param strFolder
	 * @param securityGroup
	 */
	public void DeleteFolderWithSecurityGroupInRMU(final String strFolder) {
		navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();

		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);

		try {

			driver.scrollingToBottomofAPage();

			base.waitForElement(getFolderName(strFolder));
			getFolderName(strFolder).waitAndClick(5);

			driver.scrollPageToTop();

			base.waitForElement(getFolderActionDropDownArrow());
			getFolderActionDropDownArrow().waitAndClick(5);

			base.waitForElement(getDeleteFolder());
			getDeleteFolder().waitAndClick(5);
			driver.waitForPageToBeReady();
			if (base.getYesBtn().Displayed()) {
				base.waitForElement(base.getYesBtn());
				base.getYesBtn().waitAndClick(5);
				base.VerifySuccessMessage("Folder deleted successfully");
				base.CloseSuccessMsgpopup();
				Reporter.log(strFolder + " Folder delete Successful", true);
				UtilityLog.info("Folder deleted Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Indium sowndarya This method deletes the tag created with certain
	 *         classifications
	 * @param strtag
	 * @param securityGroup
	 */
	public void DeleteTagWithClassification(final String strtag, String securityGroup) {

		navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();

		base.waitForElement(getTagsTab());
		getTagsTab().waitAndClick(5);

		// Select secGroup
		try {
			base.waitForElement(getTagSecutiryGroup());
			getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);

			driver.scrollingToBottomofAPage();

			base.waitForElement(getTagName(strtag));
			getTagName(strtag).waitAndClick(5);

			driver.scrollPageToTop();

			base.waitForElement(getTagActionDropDownArrow());
			getTagActionDropDownArrow().waitAndClick(5);

			base.waitForElement(getDeleteTag());
			getDeleteTag().waitAndClick(10);

			base.getYesBtn().waitAndClick(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// driver.waitForPageToBeReady();
		// base.VerifySuccessMessage("Tag deleted successfully");
		// base.CloseSuccessMsgpopup();

		Reporter.log(strtag + "tag delete Successful", true);
		UtilityLog.info("Tag deleted Successfully");

	}

	/**
	 * MOdified on 03/08/2022
	 * 
	 * @author Indium Aathith This method deletes the tag created with certain
	 *         classifications in RMU user
	 * @param strtag
	 * @param securityGroup
	 */
	public void DeleteTagWithClassificationInRMU(final String strtag) {

		navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		base.waitForElement(getTagsTab());
		getTagsTab().waitAndClick(5);
		try {

			driver.scrollingToBottomofAPage();
			getTagName(strtag).ScrollTo();
			base.waitTillElemetToBeClickable(getTagName(strtag));
			getTagName(strtag).waitAndClick(10);
			driver.scrollPageToTop();
			base.waitForElement(getTagActionDropDownArrow());
			getTagActionDropDownArrow().waitAndClick(10);
			base.waitForElement(getDeleteTag());
			getDeleteTag().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (base.getYesBtn().isDisplayed()) {
				driver.waitForPageToBeReady();
				base.getYesBtn().waitAndClick(10);
				base.VerifySuccessMessage("Tag deleted successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void CreateTagwithClassification(String strtag, String classificationname) throws InterruptedException

	{

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSecurityGroupTag().Enabled();
			}
		}), Input.wait30);
		getSecurityGroupTag().selectFromDropdown().selectByVisibleText("Default Security Group");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAllTagRoot().Enabled() && getAllTagRoot().Displayed();
			}
		}), Input.wait30);
		getAllTagRoot().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagActionDropDownArrow().Enabled();
			}
		}), Input.wait30);
		getTagActionDropDownArrow().waitAndClick(10);

		base.waitForElement(getAddTag());
		getAddTag().waitAndClick(10);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);
		if (!gettxtTagClassification().Displayed()) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getbtnTagCancel().Enabled() && getbtnTagCancel().Visible();
				}
			}), Input.wait30);
			getbtnTagCancel().waitAndClick(10);

			driver.waitForPageToBeReady();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAddTag().Enabled();
				}
			}), Input.wait30);
			getAddTag().waitAndClick(10);
		}

		driver.waitForPageToBeReady();

		base.waitForElement(getTagName());
		getTagName().SendKeys(strtag);
		driver.waitForPageToBeReady();
		base.stepInfo("New tag created under security group");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagClassification().Enabled();
			}
		}), Input.wait30);
		getTagClassification().selectFromDropdown().selectByVisibleText(classificationname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveTag().Enabled() && getSaveTag().Visible();
			}
		}), Input.wait60);
		getSaveTag().Click();
		base.VerifySuccessMessage("Tag added successfully");
		Reporter.log("Tag " + strtag + " is as/under " + classificationname, true);
		base.CloseSuccessMsgpopup();

	}

	public void ViewinDocViewthrTag(final String strtag) {
		driver.getWebDriver().navigate().to(Input.url + "TagsAndFolders/TagsAndFolders");

		getTagActionDropDownArrow().waitAndClick(10);

		/*
		 * Assert.assertFalse(getTagViewDocView().Enabled());
		 * Assert.assertFalse(getTagViewDoclist().Enabled());
		 */

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagName(strtag).Visible();
			}
		}), Input.wait30);
		getTagName(strtag).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagActionDropDownArrow().Visible();
			}
		}), Input.wait30);
		getTagActionDropDownArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagViewDocView().Visible();
			}
		}), Input.wait30);
		getTagViewDocView().waitAndClick(10);

	}

	/**
	 * @Modified By : Jeevitha
	 * @param strtag
	 */
	public void ViewinDocListthrTag(final String strtag) {
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
		getTagName(strtag).ScrollTo();
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagName(strtag).Visible();
			}
		}), Input.wait30);
		getTagName(strtag).waitAndClick(10);

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		base.waitForElement(getTagActionDropDownArrow());
		getTagActionDropDownArrow().waitAndClick(10);

		base.waitForElement(getTagViewDoclist());
		getTagViewDoclist().waitAndClick(10);
	}

	public void ViewinDocListthrFolder(final String strFolder) {
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFoldersTab().Visible();
			}
		}), Input.wait30);
		getFoldersTab().waitAndClick(10);

		// Select secGroup
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFolderSecutiryGroup().Visible();
				}
			}), Input.wait30);
			getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderName(strFolder).Visible();
			}
		}), Input.wait30);
		getFolderName(strFolder).waitAndClick(10);
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderActionDropDownArrow().Visible();
			}
		}), Input.wait30);
		getFolderActionDropDownArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderViewDoclist().Visible();
			}
		}), Input.wait30);
		getFolderViewDoclist().waitAndClick(10);
	}

	public void ViewinDocViewthrFolder(final String strFolder) {
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFoldersTab().Visible();
			}
		}), Input.wait30);
		getFoldersTab().waitAndClick(10);

		// Select secGroup
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFolderSecutiryGroup().Visible();
				}
			}), Input.wait30);
			getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderName(strFolder).Visible();
			}
		}), Input.wait30);
		getFolderName(strFolder).waitAndClick(10);
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderActionDropDownArrow().Visible();
			}
		}), Input.wait30);
		getFolderActionDropDownArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderViewDoclist().Visible();
			}
		}), Input.wait30);
		getFolderViewDocView().waitAndClick(10);
	}

	// Code added by Narendra

	public void provisionedTags() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDefaultTag().Visible();
			}
		}), Input.wait30);
		getDefaultTagsArrow().waitAndClick(10);
		Thread.sleep(3000);
		List<String> test1 = new ArrayList<String>();
		List<WebElement> deflttags1 = getDefaultTagsCount().FindWebElements();
		for (j = 0; j < deflttags1.size(); j++) {
			test1.add(deflttags1.get(j).getText());
		}

		List<String> test2 = Arrays.asList("Attorney_Client", "Attorney_WorkProduct", "Confidential",
				"Foreign_Language", "Hidden_Content", "Highly_Confidential", "Hot_Doc", "Not_Privileged",
				"Not_Responsive", "Privileged", "Processing_Issue", "Responsive", "Technical_Issue");

		// Comparison
		Assert.assertEquals(test1, test2);
		System.out.println("Verified provisioned Tags are available under Default Tags ");

		// Now Default Security Group
		getDefaultSecgrp().waitAndClick(10);
		Thread.sleep(3000);
		getDefaultTagsArrow().waitAndClick(10);
		Thread.sleep(3000);
		List<String> test3 = new ArrayList<String>();
		List<WebElement> deflttags2 = getDefaultTagsCount().FindWebElements();
		for (k = 0; k < deflttags2.size(); k++) {
			test3.add(deflttags2.get(k).getText());
		}
		Assert.assertEquals(test2, test3);
		System.out.println("Verified provisioned Tags are available under Default Security Group ");

	}

	public void provisionedTagsAsRMU() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDefaultTag().Visible();
			}
		}), Input.wait30);
		getDefaultTagsArrow().waitAndClick(10);
		Thread.sleep(3000);
		List<String> protaglist3 = new ArrayList<String>();
		List<WebElement> deflttags3 = getDefaultTagsCount().FindWebElements();
		for (j = 0; j < deflttags3.size(); j++) {
			protaglist3.add(deflttags3.get(j).getText());
		}
		List<String> prolist2 = Arrays.asList("Attorney_Client", "Attorney_WorkProduct", "Confidential",
				"Foreign_Language", "Hidden_Content", "Highly_Confidential", "Hot_Doc", "Not_Privileged",
				"Not_Responsive", "Privileged", "Processing_Issue", "Responsive", "Technical_Issue");
		Assert.assertEquals(prolist2, protaglist3);
		System.out.println("Verified provisioned Tags are available under Default Tags as RMU User");
	}

	public void tagsClassifications() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDefaultTag().Visible();
			}
		}), Input.wait30);
		// getDefaultSecgrp().waitAndClick(10);
		getDefaultTagsArrow().waitAndClick(10);
		List<WebElement> allvalues1 = getDefaultTagsCount().FindWebElements();
		List<String> test12 = Arrays.asList("Select Tag Classification", "Technical Issue", "Private Data",
				"Privileged", "Responsive");
		for (WebElement element : allvalues1) {
			element.click();
			Thread.sleep(1000);
			driver.scrollPageToTop();
			new Actions(driver.getWebDriver()).moveToElement(rightarrow().getWebElement()).click();
			// rightarrow().waitAndClick(10);
			rightarrow().waitAndClick(10);
			Thread.sleep(1000);
			getEditClick().waitAndClick(10);
			Thread.sleep(1000);
			getTagclick().waitAndClick(10);
			Thread.sleep(1000);
			List<String> tags1 = new ArrayList<String>();
			List<WebElement> tagsclss = getAllTagClassification().FindWebElements();
			for (k = 0; k < tagsclss.size(); k++) {
				tags1.add(tagsclss.get(k).getText());
			}
			Assert.assertEquals(tags1, test12);
			getCancel().waitAndClick(10);
		}
		System.out.println("Verified Tag Classification under All Tags");

		// Now Default Security Group
		getDefaultSecgrp().waitAndClick(10);
		getDefaultTagsArrow().waitAndClick(10);
		List<WebElement> allvalues2 = getDefaultTagsCount().FindWebElements();
		for (WebElement element : allvalues2) {
			element.click();
			Thread.sleep(1000);
			driver.scrollPageToTop();
			new Actions(driver.getWebDriver()).moveToElement(rightarrow().getWebElement()).click();
			// rightarrow().waitAndClick(10);
			rightarrow().waitAndClick(10);
			Thread.sleep(1000);
			getEditClick().waitAndClick(10);
			Thread.sleep(1000);
			getTagclick().waitAndClick(10);
			Thread.sleep(1000);
			List<String> tags2 = new ArrayList<String>();
			List<WebElement> tagsclss = getAllTagClassification().FindWebElements();
			for (k = 0; k < tagsclss.size(); k++) {
				tags2.add(tagsclss.get(k).getText());
			}
			Assert.assertEquals(tags2, test12);
			getCancel().waitAndClick(10);
		}
		System.out.println("Verified Tag Classification under Default Security Group");

	}

	public void tagsClassificationsAsRMU() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDefaultTag().Visible();
			}
		}), Input.wait30);
		// getDefaultSecgrp().waitAndClick(10);
		getDefaultTagsArrow().waitAndClick(10);
		List<String> test13 = Arrays.asList("Select Tag Classification", "Technical Issue", "Private Data",
				"Privileged", "Responsive");
		List<WebElement> allvalues3 = getDefaultTagsCount().FindWebElements();
		// List<String> test12 = Arrays.asList("Select Tag Classification","Technical
		// Issue","Private Data","Privileged","Responsive");
		for (WebElement element : allvalues3) {
			element.click();
			Thread.sleep(1000);
			driver.scrollPageToTop();
			new Actions(driver.getWebDriver()).moveToElement(rightarrow().getWebElement()).click();
			// rightarrow().waitAndClick(10);
			rightarrow().waitAndClick(10);
			Thread.sleep(1000);
			getEditClick().waitAndClick(10);
			Thread.sleep(1000);
			getTagclick().waitAndClick(10);
			Thread.sleep(1000);
			List<String> tags3 = new ArrayList<String>();
			List<WebElement> tagsclss = getAllTagClassification().FindWebElements();
			for (k = 0; k < tagsclss.size(); k++) {
				tags3.add(tagsclss.get(k).getText());
			}
			Assert.assertEquals(tags3, test13);
			getCancel().waitAndClick(10);
		}
		System.out.println("Verified Tag Classification under All Tags for RMU User");
	}

	public void layerAnnotations() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "/Annotations/Annotations");
		// getLayerClicked().waitAndClick(10);
		Thread.sleep(3000);
		List<String> layerann1 = new ArrayList<String>();
		List<WebElement> layertags1 = getLayerList().FindWebElements();
		for (k = 0; k < layertags1.size(); k++) {
			layerann1.add(layertags1.get(k).getText());
		}
		Assert.assertTrue(layerann1.contains("Default Annotation Layer"));
		System.out.println("Verified provisioned Annotation Layer is available in the Project");

		// Now Default Security Group
		getAnnoDefaultSecgrp().waitAndClick(10);
		Thread.sleep(3000);
		List<String> layerann3 = new ArrayList<String>();
		List<WebElement> layertags3 = getLayerList().FindWebElements();
		for (k = 0; k < layertags3.size(); k++) {
			layerann3.add(layertags3.get(k).getText());
		}
		Assert.assertTrue(layerann3.contains("Default Annotation Layer"));
		System.out.println("Verified provisioned Annotation Layer is available in Default Security Group");

	}

	public void layerAnnotationsAsRMU() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "/Annotations/Annotations");
		Thread.sleep(3000);
		List<String> layerann2 = new ArrayList<String>();
		List<WebElement> layertags2 = getLayerList().FindWebElements();
		for (k = 0; k < layertags2.size(); k++) {
			layerann2.add(layertags2.get(k).getText());
		}
		Assert.assertTrue(layerann2.contains("Default Annotation Layer"));
		System.out.println("Verified provisioned Annotation Layer is available in the Project as RMU User");
	}

	public void provisionedFolder() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		getSelectFolders().waitAndClick(10);
		Thread.sleep(3000);
		getAllFolders().waitAndClick(10);
		List<String> test4 = new ArrayList<String>();
		List<WebElement> defltfolders1 = getFolderList().FindWebElements();

		for (j = 0; j < defltfolders1.size(); j++) {
			test4.add(defltfolders1.get(j).getText());
		}
		Assert.assertTrue(test4.contains("Productions"));
		System.out.println("Verified Productions is available under All Folders ");

	}

	public void provisionedFolderAsRMU() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		getSelectFolders().waitAndClick(10);
		Thread.sleep(3000);
		List<String> test5 = new ArrayList<String>();
		List<WebElement> defltfolders2 = getFolderList().FindWebElements();

		for (k = 0; k < defltfolders2.size(); k++) {
			test5.add(defltfolders2.get(k).getText());
		}

		Assert.assertTrue(test5.contains("Productions"));
		System.out.println("Verified Productions is available under under All Folders as RMU User ");
	}

	public void redactionTags() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "Redaction/Redaction");
		getreductionclick().waitAndClick(10);
		Thread.sleep(3000);
		List<String> test6 = new ArrayList<String>();
		List<WebElement> red1 = getReductionList().FindWebElements();
		for (j = 0; j < red1.size(); j++) {
			test6.add(red1.get(j).getText());
		}
		Assert.assertTrue(test6.contains("Default Redaction Tag") && test6.contains("Redacted Privacy")
				&& test6.contains("Redacted Privilege"));
		System.out.println("Verified provisioned Redaction Tags are available in the Project");

		// Now Default Security Group

		// getRedAllgrp().waitAndClick(10);
		getRedDefaultSecgrp().waitAndClick(10);
		Thread.sleep(3000);
		getDefaultTagsArrow().waitAndClick(10);
		Thread.sleep(3000);
		List<String> test7 = new ArrayList<String>();
		List<WebElement> red2 = getReductionList().FindWebElements();
		for (j = 0; j < red2.size(); j++) {
			test7.add(red2.get(j).getText());
		}
		Assert.assertTrue(test7.contains("Default Redaction Tag") && test7.contains("Redacted Privacy")
				&& test7.contains("Redacted Privilege"));
		System.out.println("Verified provisioned Redaction Tags are available under Default Security Group ");

	}

	public void redactionTagsAsRMU() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Redaction/Redaction");
		getreductionclick().waitAndClick(10);
		Thread.sleep(3000);
		List<String> redlist3 = new ArrayList<String>();
		List<WebElement> red3 = getReductionList().FindWebElements();
		for (j = 0; j < red3.size(); j++) {
			redlist3.add(red3.get(j).getText());
		}
		Assert.assertTrue(redlist3.contains("Default Redaction Tag") && redlist3.contains("Redacted Privacy")
				&& redlist3.contains("Redacted Privilege"));
		System.out.println("Verified provisioned Redaction Tags are available in the Project as RMU User");
	}

	public void provisionedFolderGroup() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		getSelectFolders().waitAndClick(10);
		Thread.sleep(3000);
		List<String> test8 = new ArrayList<String>();
		List<WebElement> dgrp1 = getFolderList().FindWebElements();
		for (j = 0; j < dgrp1.size(); j++) {
			test8.add(dgrp1.get(j).getText());
		}
		Assert.assertTrue(test8.contains("Productions"));
		System.out.println("Productions is available under All Folders");
		Thread.sleep(3000);

		// Now select Default Security Group
		getFoldersDefaultSecgrp().waitAndClick(10);
		Thread.sleep(3000);
		getAllFolderClick().waitAndClick(10);// Default Security Group is getting disabled
		List<String> test9 = new ArrayList<String>();
		List<WebElement> defltfldrgroup2 = getFolderList().FindWebElements();

		for (k = 0; k < defltfldrgroup2.size(); k++) {
			test9.add(defltfldrgroup2.get(k).getText());
		}

		Assert.assertTrue(test9.contains("Productions"));
		System.out.println("Productions is available under Default Security Group");

	}

	public void Tags(String strtag, String securityGroup) {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsTab().Visible();
			}
		}), Input.wait30);
		try {
			getTagsTab().waitAndClick(10);
		} catch (Exception e) {
			// may be in tag already
		}
		try {
			// Select secGroup
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTagSecutiryGroup().Visible();
				}
			}), Input.wait30);
			getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Select root all
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAllTagRoot().Visible();
			}
		}), Input.wait30);
		getAllTagRoot().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagActionDropDownArrow().Visible();
			}
		}), Input.wait30);
		getTagActionDropDownArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddTag().Visible();
			}
		}), Input.wait30);
		getAddTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagName().Visible();
			}
		}), Input.wait30);
		getTagName().SendKeys(strtag);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCancelTag().Visible();
			}
		}), Input.wait30);
		getCancelTag().waitAndClick(10);
		System.out.println("Tag addition cancel successfully");

		// Select root all
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAllTagRoot().Visible();
			}
		}), Input.wait30);
		getAllTagRoot().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagActionDropDownArrow().Visible();
			}
		}), Input.wait30);
		getTagActionDropDownArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddTag().Visible();
			}
		}), Input.wait30);
		getAddTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagName().Visible();
			}
		}), Input.wait30);
		getTagName().SendKeys("");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCancelTag().Visible();
			}
		}), Input.wait30);
		getSaveTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getErrorMsg().Visible();
			}
		}), Input.wait30);
		String ExpectedErrorMsg = "Please enter tag name";
		String ErrorMsg = getErrorMsg().getText();
		Assert.assertEquals(ErrorMsg, ExpectedErrorMsg);
		System.out.println("Verified Error message successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCancelTag().Visible();
			}
		}), Input.wait30);
		getCancelTag().waitAndClick(10);

		base.CloseSuccessMsgpopup();

	}

	public boolean isElementDisplayed(String objectLocator, int timeOut) {
		driver.Manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		boolean flag = false;
		try {
			flag = driver.FindElement(By.id(objectLocator)).Displayed();
			return flag;
		} catch (NoSuchElementException e) {
			System.out.println("Executed" + e);
			return flag;
		} catch (Exception e) {
			return flag;
		}
	}

	public void TagGroup(String securityGroup) {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsTab().Visible();
			}
		}), Input.wait30);
		try {
			getTagsTab().waitAndClick(10);
		} catch (Exception e) {
			// may be in tag already
		}
		try {
			// Select secGroup
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTagSecutiryGroup().Visible();
				}
			}), Input.wait30);
			getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Select root all
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAllTagRoot().Visible();
			}
		}), Input.wait30);
		getAllTagRoot().waitAndClick(10);

		List<WebElement> groupele = getTagGroup().FindWebElements();
		for (WebElement element : groupele) {

			if (groupele.get(j).getText() == "Default Tags") {
				System.out.println("Do Nothing");
			} else {
				element.click();
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getTagActionDropDownArrow().Visible();
					}
				}), Input.wait30);
				getTagActionDropDownArrow().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getEditTag().Visible();
					}
				}), Input.wait30);
				getEditTag().waitAndClick(10);
				String tggrp = getTagGroupName().getText();
				System.out.println(tggrp);
			}
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying tag is added..
	 * @param tagName -- (tagName is a string value that name of tag).
	 */
	public void verifyTagNameIsAddedToStructure(String tagName) {
		try {
			try {
				getTagName(tagName).isElementAvailable(10);
				if (getTagName(tagName).getWebElement().isDisplayed()) {
					base.passedStep("Tag : " + tagName + " is displayed in tags structure successfully");
				}
			} catch (NoSuchElementException e) {
				base.failedStep("Tag : " + tagName + " is not displayed in tags structure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying tag is added" + e.getMessage());

		}
	}

	/**
	 * Modified by sowndarya on 22/9/2021
	 * 
	 * @author Indium-Baskar
	 * @Description : Creating new tag with classification for production
	 */
	public void createNewTagwithClassification(String strtag, String classificationname) throws InterruptedException {

		try {
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");

			base.waitForElement(getSecurityGroupTag());

			getSecurityGroupTag().selectFromDropdown().selectByVisibleText("Default Security Group");
			base.waitForElement(getAddTagTable());
			getAddTagTable().waitAndClick(5);
			driver.scrollPageToTop();
			base.waitForElement(getAllTagRoot());
			Thread.sleep(1000);
			getAllTagRoot().waitAndClick(10);
			base.waitForElement(getTagActionDropDownArrow());
			getTagActionDropDownArrow().waitAndClick(10);
			base.waitForElement(getAddTag());
			getAddTag().waitAndClick(10);
//         added thread.sleep to avoid exception while executing in batch
			Thread.sleep(1000);
			if (!gettxtTagClassification().Displayed()) {
				base.waitForElement(getbtnTagCancel());
				getbtnTagCancel().waitAndClick(10);
				driver.waitForPageToBeReady();
				base.waitForElement(getAddTag());
				getAddTag().waitAndClick(10);
			}
			driver.waitForPageToBeReady();
			base.waitForElement(getTagName());
			Thread.sleep(2000);
			getTagName().SendKeys(strtag);
			driver.waitForPageToBeReady();
			base.stepInfo("New tag created under security group");
			base.waitForElement(getTagClassification());
			getTagClassification().selectFromDropdown().selectByVisibleText(classificationname);
			base.waitForElement(getSaveTag());
			getSaveTag().waitAndClick(10);

			if (getSuccessMsgHeader().getText().contains("Error")) {
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");

				base.waitForElement(getSecurityGroupTag());

				getSecurityGroupTag().selectFromDropdown().selectByVisibleText("Default Security Group");
				base.waitForElement(getAddTagTable());
				getAddTagTable().Click();
				base.waitForElement(getAllTagRoot());
				Thread.sleep(1000);
				getAllTagRoot().waitAndClick(10);
				base.waitForElement(getTagActionDropDownArrow());
				getTagActionDropDownArrow().waitAndClick(10);
				base.waitForElement(getAddTag());
				getAddTag().waitAndClick(10);
//         added thread.sleep to avoid exception while executing in batch
				Thread.sleep(1000);
				if (!gettxtTagClassification().Displayed()) {
					base.waitForElement(getbtnTagCancel());
					getbtnTagCancel().waitAndClick(10);
					driver.waitForPageToBeReady();
					base.waitForElement(getAddTag());
					getAddTag().waitAndClick(10);
				}
				driver.waitForPageToBeReady();
				base.waitForElement(getTagName());
				Thread.sleep(2000);
				getTagName().SendKeys(strtag);
				driver.waitForPageToBeReady();
				base.stepInfo("New tag created under security group");
				base.waitForElement(getTagClassification());
				getTagClassification().selectFromDropdown().selectByVisibleText(classificationname);
				base.waitForElement(getSaveTag());
				getSaveTag().waitAndClick(10);

			}
			base.VerifySuccessMessage("Tag added successfully");
			Reporter.log("Tag " + strtag + " is as/under " + classificationname, true);
			base.CloseSuccessMsgpopup();
		}

		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/*
	 * @author: Aathith
	 * 
	 * @Description: method for creating Tag in RMU user
	 */
	public void createNewTagwithClassificationInRMU(String strtag, String classificationname)
			throws InterruptedException {

		try {
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");

			// base.waitForElement(getSecurityGroupTag());

			// getSecurityGroupTag().selectFromDropdown().selectByVisibleText("Default
			// Security Group");
			base.waitForElement(getAddTagTable());
			getAddTagTable().Click();
			driver.scrollPageToTop();
			base.waitForElement(getAllTagRoot());
			Thread.sleep(1000);
			getAllTagRoot().waitAndClick(10);
			base.waitForElement(getTagActionDropDownArrow());
			getTagActionDropDownArrow().waitAndClick(10);
			base.waitForElement(getAddTag());
			getAddTag().waitAndClick(10);
//         added thread.sleep to avoid exception while executing in batch
			Thread.sleep(1000);
			if (!gettxtTagClassification().Displayed()) {
				base.waitForElement(getbtnTagCancel());
				getbtnTagCancel().waitAndClick(10);
				driver.waitForPageToBeReady();
				base.waitForElement(getAddTag());
				getAddTag().waitAndClick(10);
			}
			driver.waitForPageToBeReady();
			base.waitForElement(getTagName());
			Thread.sleep(2000);
			getTagName().SendKeys(strtag);
			driver.waitForPageToBeReady();
			base.stepInfo("New tag created under security group");
			base.waitForElement(getTagClassification());
			getTagClassification().selectFromDropdown().selectByVisibleText(classificationname);
			base.waitForElement(getSaveTag());
			getSaveTag().waitAndClick(10);

			if (getSuccessMsgHeader().getText().contains("Error")) {
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");

				base.waitForElement(getSecurityGroupTag());

				getSecurityGroupTag().selectFromDropdown().selectByVisibleText("Default Security Group");
				base.waitForElement(getAddTagTable());
				getAddTagTable().Click();
				base.waitForElement(getAllTagRoot());
				Thread.sleep(1000);
				getAllTagRoot().waitAndClick(10);
				base.waitForElement(getTagActionDropDownArrow());
				getTagActionDropDownArrow().waitAndClick(10);
				base.waitForElement(getAddTag());
				getAddTag().waitAndClick(10);
//         added thread.sleep to avoid exception while executing in batch
				Thread.sleep(1000);
				if (!gettxtTagClassification().Displayed()) {
					base.waitForElement(getbtnTagCancel());
					getbtnTagCancel().waitAndClick(10);
					driver.waitForPageToBeReady();
					base.waitForElement(getAddTag());
					getAddTag().waitAndClick(10);
				}
				driver.waitForPageToBeReady();
				base.waitForElement(getTagName());
				Thread.sleep(2000);
				getTagName().SendKeys(strtag);
				driver.waitForPageToBeReady();
				base.stepInfo("New tag created under security group");
				base.waitForElement(getTagClassification());
				getTagClassification().selectFromDropdown().selectByVisibleText(classificationname);
				base.waitForElement(getSaveTag());
				getSaveTag().waitAndClick(10);

			}
			base.VerifySuccessMessage("Tag added successfully");
			Reporter.log("Tag " + strtag + " is as/under " + classificationname, true);
			base.CloseSuccessMsgpopup();
		}

		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify documents count assigned for a tag.
	 * @param tagName           : (tagName is String value that name of tag)
	 * @param numberOfDocuments : (numberOfDocuments is integer value that expected
	 *                          documents count assigned to tag).
	 */
	public void verifyTagContainedDocumentsCount(String tagName, int numberOfDocuments) {
		try {
			driver.waitForPageToBeReady();
			int rowNum = 0;
			base.waitForElement(getTagsTree());
			base.waitTillElemetToBeClickable(getTagsTree());
			getTagsTree().Click();
			Thread.sleep(2000);
			List<WebElement> tagsRows = getTagsRows().FindWebElements();
			for (int row = 0; row < tagsRows.size(); row++) {
				String rowChar = getTag(row + 1).GetAttribute("data-content");
				if (rowChar == null) {
				} else if (rowChar.trim().equalsIgnoreCase(tagName)) {
					rowNum = row + 1;
					break;
				}
			}
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitForElement(getShowDocumentsCountToogle());
			base.waitTillElemetToBeClickable(getShowDocumentsCountToogle());
			getShowDocumentsCountToogle().Click();
			clickOnDefaultTag();
			base.waitForElement(getTagsTree());
			base.waitTillElemetToBeClickable(getTagsTree());
			getTagsTree().Click();
			Thread.sleep(2000);
			base.waitForElement(getTag(rowNum));
			base.waitTillElemetToBeClickable(getTag(rowNum));
			base.stepInfo("Tag documents with count -- " + getTag(rowNum).GetAttribute("data-content").trim()
					+ " on row number :: " + rowNum);
			String[] tagContainedCount = getTag(rowNum).GetAttribute("data-content").trim().split("\\(", 2);
			String[] count = tagContainedCount[1].split("\\)", 2);
			int docCount = Integer.parseInt(count[0].trim());
			for (int i = 0; i < 10; i++) {
				if (docCount != numberOfDocuments) {
					refreshPage();
					driver.waitForPageToBeReady();
					driver.scrollPageToTop();
					base.waitForElement(getShowDocumentsCountToogle());
					base.waitTillElemetToBeClickable(getShowDocumentsCountToogle());
					getShowDocumentsCountToogle().Click();
					clickOnDefaultTag();
					base.waitForElement(getTagsTree());
					base.waitTillElemetToBeClickable(getTagsTree());
					getTagsTree().Click();
					base.waitForElement(getTag(rowNum));
					base.waitTillElemetToBeClickable(getTag(rowNum));
					base.stepInfo("Tag documents with count -- " + getTag(rowNum).GetAttribute("data-content").trim()
							+ " on row number :: " + rowNum);
					tagContainedCount = getTag(rowNum).GetAttribute("data-content").trim().split("\\(", 2);
					count = tagContainedCount[1].split("\\)", 2);
					docCount = Integer.parseInt(count[0].trim());
				} else {
					break;
				}

			}
			if (docCount == numberOfDocuments) {
				base.passedStep(
						"Documents count got for '" + tagName + "' tag is '" + numberOfDocuments + "' as expected");
			} else {
				base.failedStep("Documents count got for '" + tagName + "' tag is '" + numberOfDocuments + "' but got '"
						+ docCount + "' not as expected");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying documents count assigned for a tag" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to click on default tag.
	 */
	public void clickOnDefaultTag() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			base.waitForElement(getTagsTree());
			base.waitTillElemetToBeClickable(getTagsTree());
			getTagsTree().Click();
			js.executeScript("arguments[0].scrollIntoView(true);", getOpenDefaultTag().getWebElement());
			js.executeScript("window.scrollBy(0,-250)");
			driver.waitForPageToBeReady();
			base.waitForElement(getOpenDefaultTag());
			base.waitTillElemetToBeClickable(getOpenDefaultTag());
			getOpenDefaultTag().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while clicking on default tag" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to refresh page.
	 */
	public void refreshPage() {
		try {
			driver.Navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while refreshing page" + e.getMessage());
		}
	}

	/**
	 * @author Raghuram.A
	 */
	public void selectActionArrow(String actionType) {
		driver.scrollPageToTop();
		base.waitTime(2); // to handle abnormal waits
		driver.waitForPageToBeReady();
		rightarrow().waitAndClick(10);
		base.waitTime(2);
		driver.waitForPageToBeReady();

		if (actionType.equalsIgnoreCase("Bulk release")) {
			getBulkReleaseAction().waitAndClick(5);
		} else if (actionType.equalsIgnoreCase("New Tag Group")) {
			getNewTagGroupAction().waitAndClick(5);
		} else if (actionType.equalsIgnoreCase("Edit")) {
			getEditClick().waitAndClick(5);
		}
	}

	/**
	 * @author Raghuram.A
	 */
	public int verifyBulk_releaseCount(String SecGroup) throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getBulkRelDefaultSecurityGroup_CheckBox(SecGroup));
		getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(10);
		base.waitForElement(getBulkRelease_ButtonRelease());
		getBulkRelease_ButtonRelease().waitAndClick(20);
		base.waitForElement(getFinalizeButton());
		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		int newPurehit = Integer.parseInt(getFinalCount().getText());
		Thread.sleep(5000);
		getFinalizeButton().waitAndClick(20);
		base.VerifySuccessMessageB("Records saved successfully");
		return newPurehit;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param tagName
	 * @description This method will randomly delete a set of tags if we follow
	 *              unique naming strategy.
	 */

	public void deleteAllTags(String tagName) {
		try {
			base.waitForElement(getTagsTab());
			getTagsTab().waitAndClick(5);
			List<String> elementNames = new ArrayList<>();
			elementNames = base.availableListofElements(getAllTagsOrFolder(tagName));
			System.out.println(elementNames.size());
			System.out.println(elementNames);
			for (int i = 1; i <= elementNames.size(); i++) {
				driver.scrollingToElementofAPage(getTagOrFolder(tagName));
				base.waitTillElemetToBeClickable(getTagOrFolder(tagName));
				getTagOrFolder(tagName).waitAndClick(5);
				System.out.println(getTagOrFolder(tagName).GetAttribute("class"));
				if (getTagOrFolder(tagName).GetAttribute("class").contains("jstree-clicked")) {
					driver.scrollPageToTop();
					base.waitForElement(getTagActionDropDownArrow());
					getTagActionDropDownArrow().waitAndClick(5);
					base.waitForElement(getDeleteTag());
					getDeleteTag().waitAndClick(10);
					base.getYesBtn().waitAndClick(10);
					base.VerifySuccessMessage("Tag deleted successfully");
					base.CloseSuccessMsgpopup();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to delete tags");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param folderName
	 */
	public void deleteAllFolders(String folderName) {
		try {
			base.waitForElement(getFoldersTab());
			getFoldersTab().waitAndClick(5);
			for (int i = 1; i <= getAllTagsOrFolder(folderName).size(); i++) {
				base.waitTillElemetToBeClickable(getTagOrFolder(folderName));
				getTagOrFolder(folderName).waitAndClick(5);
				if (getTagOrFolder(folderName).GetAttribute("class").contains("jstree-clicked")) {
					driver.scrollPageToTop();
					base.waitForElement(getFolderActionDropDownArrow());
					getFolderActionDropDownArrow().waitAndClick(5);
					base.waitForElement(getDeleteFolder());
					getDeleteFolder().waitAndClick(5);
					base.waitForElement(base.getYesBtn());
					base.getYesBtn().waitAndClick(5);
					base.VerifySuccessMessage("Folder deleted successfully");
					base.CloseSuccessMsgpopup();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to delete folder");
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating to tags and folder page.
	 */
	public void navigateToTagsAndFolderPage() {
		try {
			driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to tags and folder page" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Verifying The Count of document as expected count given in
	 *              doclist .
	 */
	public void selectingFolderAndVerifyingTheDocCount(String folder, int noOfDocuments) {

		try {
			base.waitForElement(getFoldersTab());
			getFoldersTab().waitAndClick(20);
			base.waitTillElemetToBeClickable(getFolder_ToggleDocCount());
			getFolder_ToggleDocCount().Click();
			base.waitForElement(SelectFolderCheckbox(folder));
			SelectFolderCheckbox(folder).ScrollTo();
			String docCountToggleEnable = SelectFolderCheckbox(folder).getText();
			System.out.print(docCountToggleEnable);
			driver.waitForPageToBeReady();
			String[] docCount = docCountToggleEnable.split("\\(", 2);
			String[] docCount1 = docCount[1].split("\\)", 2);
			for (int i = 0; i < 5; i++) {
				if (!docCount1[0].equals(String.valueOf(noOfDocuments))) {
					driver.Navigate().refresh();
					driver.waitForPageToBeReady();
					base.waitForElement(getFoldersTab());
					base.waitTillElemetToBeClickable(getFoldersTab());
					driver.waitForPageToBeReady();
					getFoldersTab().Click();
					base.waitTillElemetToBeClickable(getFolder_ToggleDocCount());
					getFolder_ToggleDocCount().Click();
					base.waitForElement(SelectFolderCheckbox(folder));
					SelectFolderCheckbox(folder).ScrollTo();
					// SelectFolderCheckbox(folder).Click();
					docCountToggleEnable = SelectFolderCheckbox(folder).getText();
					System.out.print(docCountToggleEnable);
					driver.waitForPageToBeReady();
					docCount = docCountToggleEnable.split("\\(", 2);
					docCount1 = docCount[1].split("\\)", 2);

				} else {
					break;
				}
			}

			if (docCount1[0].equals(String.valueOf(noOfDocuments))) {
				base.passedStep("The Actual Doccount is:" + docCount1[0] + " and  Expected Doccount :" + noOfDocuments
						+ " is Equal");
			} else {
				base.failedStep("The Actual Doccount is:" + docCount1[0] + " and  Expected Doccount is:" + noOfDocuments
						+ "Not Equal");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("The Count of Document is Not As Expected" + e.getMessage());
		}
	}

	/**
	 * @author Raghuram.A date: 01/18/21 Modified date: 1/24/21 Modified by:
	 *         Raghuram A
	 */
	public void createTagGroup(String securityGroup, String tagGroupName, String verifyNotification,
			Boolean additionalValue) {

		selectActionArrow("New Tag Group");

		if (getNewTagGroupPopup().isElementAvailable(2)) {
			System.out.println("Create new tag group pop-up opened");

			getNewTagGroupInputTextBox().SendKeys(tagGroupName);
			getNewTagGroupSaveBtn().waitAndClick(3);

			if (verifyNotification.equalsIgnoreCase("Success")) {
				base.VerifySuccessMessage("Tag group added successfully");
			}
		}

		driver.waitForPageToBeReady();
		if (getTagNameDataCon(tagGroupName).isElementAvailable(5)) {
			System.out.println("Created Tag Group : " + tagGroupName + " is available");
			base.stepInfo("Created Tag Group : " + tagGroupName + " is available");
		}

	}

	/**
	 * @author Raghuram.A date: 01/18/21 Modified date: 1/24/21 Modified by:
	 *         Raghuram A
	 */
	public void editTagGroup(String securityGroup, String selectTagName, String retagGroupName,
			String verifyNotification, Boolean additionalValue) {

		getTagNameDataCon(selectTagName).waitAndClick(5);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		selectActionArrow("Edit");

		if (getEditTagGroupPopup().isElementAvailable(4)) {
			System.out.println("Edit Popup opened");

			getNewTagGroupInputTextBox().SendKeys(retagGroupName);
			getUpdateTagGroupSaveBtn().waitAndClick(3);

			if (verifyNotification.equalsIgnoreCase("Success")) {
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Tag group updated successfully");
				base.stepInfo("TagGroup name updated successfully : " + retagGroupName);
				System.out.println("TagGroup name updated successfully : " + retagGroupName);
			} else if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
				driver.waitForPageToBeReady();
				base.VerifyErrorMessage("80001000004 : You don't have access to perform modify operation.");
			}

		}

	}

	/**
	 * @author Raghuram.A date: 01/18/21 NA Modified date: N/A Modified by:
	 */
	public void deleteAllTagsGroups(String tagName, String verifyNotification) {
		base.waitForElement(getTagsTab());
		getTagsTab().waitAndClick(5);
		List<String> elementNames = new ArrayList<>();
		elementNames = base.availableListofElements(getAllTagsOrFolder(tagName));
		System.out.println(elementNames.size());
		System.out.println(elementNames);
		for (int i = 1; i <= elementNames.size(); i++) {
			driver.scrollingToElementofAPage(getTagOrFolder(tagName));
			base.waitTillElemetToBeClickable(getTagOrFolder(tagName));
			getTagOrFolder(tagName).waitAndClick(5);
			System.out.println(getTagOrFolder(tagName).GetAttribute("class"));
			if (getTagOrFolder(tagName).GetAttribute("class").contains("jstree-clicked")) {
				driver.scrollPageToTop();
				base.waitForElement(getTagActionDropDownArrow());
				getTagActionDropDownArrow().waitAndClick(5);
				base.waitForElement(getDeleteTag());
				getDeleteTag().waitAndClick(10);
				base.getYesBtn().waitAndClick(10);
				driver.waitForPageToBeReady();
				if (verifyNotification.equalsIgnoreCase("Success")) {
					base.VerifySuccessMessage("Tag group deleted successfully");
					base.CloseSuccessMsgpopup();
				} else if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
					base.VerifyErrorMessage("80001000004 : You don't have access to perform modify operation.");
				}
			}
		}
	}

	/**
	 * @author Raghuram.A date: 01/18/21 Modified date: 1/24/21 Modified by:
	 *         Raghuram A
	 */
	public void createFolderGroup(String securityGroup, String folderGroupName, String verifyNotification,
			Boolean additionalValue) {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getFolderActionDropDownArrow().waitAndClick(10);
		base.waitTime(3); // To handle abnormal loading
		driver.waitForPageToBeReady();
		getNewFOlderGroupAction().waitAndClick(5);

		if (getNewFolderGroupPopup().isElementAvailable(2)) {
			System.out.println("Create new tag group pop-up opened");

			getNewFolderGroupInputTextBox().SendKeys(folderGroupName);
			getNewFolderGroupSaveBtn().waitAndClick(3);

			if (verifyNotification.equalsIgnoreCase("Success")) {
				base.VerifySuccessMessage("Folder group added successfully");
			}
		}

		driver.waitForPageToBeReady();
		if (getTagNameDataCon(folderGroupName).isElementAvailable(5)) {
			System.out.println("Created Folder Group : " + folderGroupName + " is available");
			base.stepInfo("Created Folder Group : " + folderGroupName + " is available");
		}

	}

	/**
	 * @author Raghuram.A date: 01/18/21 Modified date: 1/24/21 Modified by:
	 *         Raghuram A
	 */
	public void editFolderGroup(String securityGroup, String selectFolderName, String reFolderGroupName,
			String verifyNotification, Boolean additionalValue) {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getFolderActionDropDownArrow().waitAndClick(10);
		base.waitTime(3);
		driver.waitForPageToBeReady();
		getEditFolderGroup().waitAndClick(5);

		if (getEditFolderGroupPopup().isElementAvailable(4)) {
			System.out.println("Edit Popup opened");

			getNewFolderGroupInputTextBox().SendKeys(reFolderGroupName);
			getUpdateFolderGroupSaveBtn().waitAndClick(3);

			if (verifyNotification.equalsIgnoreCase("Success")) {
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Folder group updated successfully");
				base.stepInfo("Folder name updated successfully : " + reFolderGroupName);
				System.out.println("Folder name updated successfully : " + reFolderGroupName);
			} else if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
				driver.waitForPageToBeReady();
				base.CloseSuccessMsgpopup();
				base.VerifyErrorMessage("80001000004 : You don't have access to perform modify operation.");
			}

		}

	}

	/**
	 * @author Raghuram.A date: 01/18/21 NA Modified date: N/A Modified by:
	 */
	public void deleteAllFolderGroup(String folderName, String verifyNotification) {
		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);
		for (int i = 1; i <= getAllTagsOrFolder(folderName).size(); i++) {
			base.waitTillElemetToBeClickable(getTagOrFolder(folderName));
			getTagOrFolder(folderName).waitAndClick(5);
			if (getTagOrFolder(folderName).GetAttribute("class").contains("jstree-clicked")) {
				driver.scrollPageToTop();
				base.waitForElement(getFolderActionDropDownArrow());
				getFolderActionDropDownArrow().waitAndClick(5);
				base.waitForElement(getDeleteFolder());
				getDeleteFolder().waitAndClick(5);
				base.waitForElement(base.getYesBtn());
				base.getYesBtn().waitAndClick(5);
				driver.waitForPageToBeReady();
				if (verifyNotification.equalsIgnoreCase("Success")) {
					base.VerifySuccessMessage("Folder group deleted successfully");
					base.CloseSuccessMsgpopup();
				} else if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
					driver.waitForPageToBeReady();
					base.VerifyErrorMessage("80001000004 : You don't have access to perform modify operation.");
				}
			}
		}
	}

	/**
	 * @author Raghuram.A date: 01/24/21 NA Modified date: N/A Modified by:
	 */
	public void editFolder(String securityGroup, String selectFolderName, String reFolderName,
			String verifyNotification, Boolean additionalValue) {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getFolderActionDropDownArrow().waitAndClick(10);
		base.waitTime(3);
		driver.waitForPageToBeReady();
		getEditFolderGroup().waitAndClick(5);

		if (getEditFolderPopup().isElementAvailable(4)) {
			System.out.println("Edit Popup opened");

			getNewFolderInputTextBox().SendKeys(reFolderName);
			getUpdateFolderSaveBtn().waitAndClick(3);

			if (getEditFolderWaringMessagePopupTitle().isElementPresent()) {
				System.out.println("Edit Folder Warning Message appeared");
				String sourceString = getEditFolderWaringMessageText().getText();
				String compreString = "You are about to change the name of the folder. All existing searches that are using the existing folder name in their queries may no longer execute correctly. Do you want to continue?";
				base.textCompareEquals(sourceString, compreString,
						"Edit Folder Warning Message Text is Varified Successfully",
						"Edit Folder Warning Message Text is not Varified");
				base.getYesBtn().waitAndClick(10);
			}
			if (verifyNotification.equalsIgnoreCase("Success")) {
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Folder updated successfully");
				base.CloseSuccessMsgpopup();
				base.stepInfo("Folder name updated successfully : " + reFolderName);
				System.out.println("Folder name updated successfully : " + reFolderName);
			} else if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
				driver.waitForPageToBeReady();
				base.VerifyErrorMessage("80001000004 : You don't have access to perform modify operation.");
				base.CloseSuccessMsgpopup();
			}

		}
	}

	/**
	 * @author Raghuram.A date: 01/24/21 NA Modified date: N/A Modified by:
	 */
	public void selectallFolderRoot() {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();

		getFoldersTab().waitAndClick(10);
		// Select root all
		getAllFolderRoot().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Raghuram.A date: 01/24/21 NA Modified date: N/A Modified by:
	 */
	public void selectallTagRoot() {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();

		getTagsTab().waitAndClick(10);
		// Select root all
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAllTagRoot().Visible();
			}
		}), Input.wait30);
		getAllTagRoot().waitAndClick(10);
	}

	/**
	 * @author Raghuram.A date: 01/24/21 NA Modified date: N/A Modified by:
	 */
	public boolean verifyNodePresent(String toSelectName, Boolean click, String type) {
		Boolean present = false;
		driver.waitForPageToBeReady();
		if (getTagNameDataCon(toSelectName).isElementAvailable(5)) {
			base.stepInfo("Created " + type + " : " + toSelectName + " is available");
			present = true;
		} else {
			base.failedStep("Created " + type + " : " + toSelectName + " is not available");
		}
		if (click) {
			getTagNameDataCon(toSelectName).waitAndClick(5);
		}

		return present;
	}

	/**
	 * @author Raghuram.A date: 01/24/21 NA Modified date: N/A Modified by:
	 */
	public void editTag(String securityGroup, String selectTagName, String reTagName, String verifyNotification,
			Boolean additionalValue) {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		selectActionArrow("Edit");

		if (getEditTagPopup().isElementAvailable(4)) {
			System.out.println("Edit Popup opened");

			getTagName().SendKeys(reTagName);
			getUpdateTagSaveBtn().waitAndClick(3);

			if (getEditTagWaringMessagePopupTitle().isElementPresent()) {
				System.out.println("Edit Tag Warning Message appeared");
				String sourceString = getEditTagWaringMessageText().getText();
				String compreString = "You are about to change the name of the tag. All existing searches that are using the existing tag name in their queries may no longer execute correctly. Do you want to continue?";
				base.textCompareEquals(sourceString, compreString,
						"Edit Tag Warning Message Text is Verified Successfully",
						"Edit Tag Warning Message Text is not Verified");
				base.getYesBtn().waitAndClick(10);
			}
			if (verifyNotification.equalsIgnoreCase("Success")) {
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Tag updated successfully");
				base.CloseSuccessMsgpopup();
				base.stepInfo("Tag name updated successfully : " + reTagName);
				System.out.println("Tag name updated successfully : " + reTagName);
			} else if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
				driver.waitForPageToBeReady();
				base.VerifyErrorMessage("80001000004 : You don't have access to perform modify operation.");
				base.CloseSuccessMsgpopup();
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param tagName
	 */
	public void verifyTagDocCount(String tagName, int count) {
		base.waitForElement(getTag_ToggleDocCount());
		getTag_ToggleDocCount().waitAndClick(20);
		base.waitTime(2);
		if (getTagandCount(tagName, count).isElementAvailable(3)) {
			base.passedStep(tagName + " with " + count + " count  could be seen under tags and folder page -"
					+ getTagandCount(tagName, count).getText());
		} else {
			base.failedStep(tagName + " with " + count + " count is not displayed");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param folderName
	 * @param count
	 */
	public void verifyFolderDocCount(String folderName, int count) {
		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(10);
		getFolder_ToggleDocCount().waitAndClick(10);
		getFolderandCount(folderName, count).WaitUntilPresent();
		if (getFolderandCount(folderName, count).isElementAvailable(3)) {
			base.passedStep(folderName + " could be seen under tags and folder page "
					+ getFolderandCount(folderName, count).getText());
		} else {
			base.failedStep(folderName + " with zero count is not displayed");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param toSelectName - node to check
	 * @param passMsg      - pass message to print
	 * @param FailMsg      - fail message to print
	 */
	public void verifyNodeNotPresent(String toSelectName, String passMsg, String FailMsg) {
		driver.waitForPageToBeReady();
		if (getTagNameDataCon(toSelectName).isElementAvailable(3)) {
			base.failedStep(toSelectName + " : " + FailMsg);
		} else {
			base.stepInfo(toSelectName + " : " + passMsg);
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param strFolder
	 * @Description select folder and perform action in view in docview
	 */
	public void selectFolderViewInDocView(String strFolder) {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);

		driver.scrollingToBottomofAPage();
		getFolderName(strFolder).ScrollTo();
		base.waitForElement(getFolderName(strFolder));
		getFolderName(strFolder).waitAndClick(10);
		driver.waitForPageToBeReady();

		driver.scrollPageToTop();
		base.waitForElement(getFolderActionDropDownArrow());
		getFolderActionDropDownArrow().waitAndClick(10);
		getFolderViewDocView().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Raghuram.A
	 * @param securityGroupName - List of security groups to release - Folder
	 */
	public void bulkReleaseFolder(List<String> securityGroupName) {

		driver.waitForPageToBeReady();
		getFolderActionDropDownArrow().waitAndClick(10);
		getBulkReleaseFolder().waitAndClick(10);

		for (int i = 0; i < securityGroupName.size(); i++) {
			getBulkRelOther_CheckBox(securityGroupName.get(i)).waitAndClick(10);
		}

		getBulkRelease_ButtonRelease().waitAndClick(10);
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		base.VerifySuccessMessage("Records saved successfully");

	}

	/**
	 * @author Raghuram.A
	 * @param securityGroupName - List of security groups to release - Tag
	 */
	public void bulkReleaseTag(List<String> securityGroupName) {

		driver.waitForPageToBeReady();
		actionarrow().waitAndClick(10);
		getBulkReleaseAction().waitAndClick(10);

		for (int i = 0; i < securityGroupName.size(); i++) {
			getBulkRelOther_CheckBox(securityGroupName.get(i)).waitAndClick(10);
		}

		getBulkRelease_ButtonRelease().waitAndClick(10);
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		base.VerifySuccessMessage("Records saved successfully");

	}

	/**
	 * @author Raghuram.A
	 * @param verifyNotification - notification to verify
	 * @param type               - type Tag/Folder/Tag group/FOlder group
	 * @param additional1        - additional parameter for future use
	 */
	public void verifyNotificationMsg(String verifyNotification, String type, String additional1, String additional2) {
		if (verifyNotification.equalsIgnoreCase("deleteSuccess")) {
			base.VerifySuccessMessage(type + " deleted successfully");
			base.CloseSuccessMsgpopup();
		} else if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
			base.CloseSuccessMsgpopup();
			base.VerifyErrorMessage("80001000004 : You don't have access to perform modify operation.");
			base.CloseSuccessMsgpopup();
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param strFolder
	 */
	public void selectFolderViewInDocList(String strFolder) {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);

		driver.scrollingToBottomofAPage();
		getFolderName(strFolder).ScrollTo();
		base.waitForElement(getFolderName(strFolder));
		getFolderName(strFolder).waitAndClick(10);
		driver.waitForPageToBeReady();

		driver.scrollPageToTop();
		base.waitForElement(getFolderActionDropDownArrow());
		getFolderActionDropDownArrow().waitAndClick(10);
		getFolderViewDoclist().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	public void validateTagsPageCount() {

		driver.waitForPageToBeReady();

		ElementCollection totFolderCount = getTotFolderCount();
		int totFolderSize = totFolderCount.size();
		if (totFolderSize > 3) {
			base.passedStep(
					"There are more than 2 Tags in each security Groups(1-1 in Security Group) is exists in source template project.");

		} else {
			base.failedStep("There are no Tags in this project");
		}

	}

	public void validateFolderPageCount() {

		driver.waitForPageToBeReady();
		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);

		ElementCollection totFolderCount = getTotFolderCount();
		int totFolderSize = totFolderCount.size();
		if (totFolderSize > 3) {
			base.passedStep(
					"There are more than 2 Folders in each security Groups(1-1 in Security Group) is exists in source template project.");

		} else {
			base.failedStep("There are no Folders in this project");
		}

	}

	/**
	 * @author
	 * @param createTag
	 * @param username
	 * @param tagName
	 * @param securityGroup
	 * @param deleteTag
	 * @param expectedUserRole
	 * @param additional
	 * @param additional1
	 * @throws InterruptedException
	 * @description - tag Creation/Deletion Based On User role
	 */
	public void tagCreationDeletionBasedOnUser(Boolean createTag, String username, String tagName, String securityGroup,
			Boolean deleteTag, String expectedUserRole, Boolean additional, String additional1)
			throws InterruptedException {
		if (createTag) {
			if (username.equals(expectedUserRole)) {
				CreateTag(tagName, securityGroup);
			}
		}

		if (deleteTag) {
			if (username.equals(expectedUserRole)) {
				navigateToTagsAndFolderPage();
				DeleteTag(tagName, securityGroup);
			}
		}
	}

	/**
	 * @author Vijaya.Rani date: 09/08/22 Modified date: NA Modified by:
	 * 
	 */
	public void createTagGroupAndCancel(String securityGroup, String tagGroupName, Boolean additionalValue) {

		selectActionArrow("New Tag Group");

		if (getNewTagGroupPopup().isElementAvailable(2)) {
			System.out.println("Create new tag group pop-up opened");

			getNewTagGroupInputTextBox().SendKeys(tagGroupName);
			base.getCancelbutton().waitAndClick(3);
		}
		base.passedStep("Tag Group is not Created");

	}

	public void createFolderGroupAndCancel(String securityGroup, String folderGroupName, Boolean additionalValue) {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getFolderActionDropDownArrow().waitAndClick(10);
		base.waitTime(3); // To handle abnormal loading
		driver.waitForPageToBeReady();
		getNewFOlderGroupAction().waitAndClick(5);

		if (getNewFolderGroupPopup().isElementAvailable(2)) {
			System.out.println("Create new tag group pop-up opened");

			getNewFolderGroupInputTextBox().SendKeys(folderGroupName);
			base.getCancelbutton().waitAndClick(3);
		}
		base.passedStep("Folder Group is not Created");

	}

	public void createDuplicateTagGroup(String securityGroup, String tagGroupName, String verifyNotification,
			Boolean additionalValue) {

		selectActionArrow("New Tag Group");

		if (getNewTagGroupPopup().isElementAvailable(2)) {
			System.out.println("Create new tag group pop-up opened");

			getNewTagGroupInputTextBox().SendKeys(tagGroupName);
			getNewTagGroupSaveBtn().waitAndClick(3);

			if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
				base.CloseSuccessMsgpopup();
				base.VerifyErrorMessage("80001000001 : An object with same name already exists.");
				base.CloseSuccessMsgpopup();
			}
		}

	}

	public void createDuplicateFolderGroup(String securityGroup, String selectFolderName, String verifyNotification,
			Boolean additionalValue) {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getFolderActionDropDownArrow().waitAndClick(10);
		base.waitTime(3); // To handle abnormal loading
		driver.waitForPageToBeReady();
		getNewFOlderGroupAction().waitAndClick(5);

		if (getNewFolderGroupPopup().isElementAvailable(2)) {
			System.out.println("Create new tag group pop-up opened");

			getNewFolderGroupInputTextBox().SendKeys(selectFolderName);
			getNewFolderGroupSaveBtn().waitAndClick(3);

			if (verifyNotification.equalsIgnoreCase("Failure-Error")) {
				base.CloseSuccessMsgpopup();
				base.VerifyErrorMessage("80001000001 : An object with same name already exists.");
				base.CloseSuccessMsgpopup();
			}
		}

	}

	public void deleteAllTagsGroupsClickNo(String tagName) {
		base.waitForElement(getTagsTab());
		getTagsTab().waitAndClick(5);
		List<String> elementNames = new ArrayList<>();
		elementNames = base.availableListofElements(getAllTagsOrFolder(tagName));
		System.out.println(elementNames.size());
		System.out.println(elementNames);
		for (int i = 1; i <= elementNames.size(); i++) {
			driver.scrollingToElementofAPage(getTagOrFolder(tagName));
			base.waitTillElemetToBeClickable(getTagOrFolder(tagName));
			getTagOrFolder(tagName).waitAndClick(5);
			System.out.println(getTagOrFolder(tagName).GetAttribute("class"));
			if (getTagOrFolder(tagName).GetAttribute("class").contains("jstree-clicked")) {
				driver.scrollPageToTop();
				base.waitForElement(getTagActionDropDownArrow());
				getTagActionDropDownArrow().waitAndClick(5);
				base.waitForElement(getDeleteTag());
				getDeleteTag().waitAndClick(5);
				base.getNOBtn().waitAndClick(5);

			}
		}
	}

	public void deleteAllFolderGroupClickNo(String folderName) {
		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);
		for (int i = 1; i <= getAllTagsOrFolder(folderName).size(); i++) {
			base.waitTillElemetToBeClickable(getTagOrFolder(folderName));
			getTagOrFolder(folderName).waitAndClick(5);
			if (getTagOrFolder(folderName).GetAttribute("class").contains("jstree-clicked")) {
				driver.scrollPageToTop();
				base.waitForElement(getFolderActionDropDownArrow());
				getFolderActionDropDownArrow().waitAndClick(5);
				base.waitForElement(getDeleteFolder());
				getDeleteFolder().waitAndClick(5);
				base.waitForElement(base.getYesBtn());
				base.getNOBtn().waitAndClick(5);
			}
		}
	}

	public void showDocsCountisOFFTags() {
		driver.waitForPageToBeReady();
		base.waitForElement(getShowDocumentsCountToogle());
		base.waitTillElemetToBeClickable(getShowDocumentsCountToogle());
		if (!base.text("All Tags (").isDisplayed()) {
			base.passedStep("Documents Count is not Displayed");
		} else {
			base.failedStep("Documents Count is Displayed");
		}
	}

	public void showDocsCountisONTags() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getShowDocumentsCountToogle());
		base.waitTillElemetToBeClickable(getShowDocumentsCountToogle());
		getShowDocumentsCountToogle().waitAndClick(5);
		driver.waitForPageToBeReady();
		Thread.sleep(10);
		if (!base.text("All Tags (").isDisplayed()) {
			base.passedStep("Documents Count is Displayed");
		} else {
			base.failedStep("Documents Count is not Displayed");
		}
	}

	public void showDocsCountisOFFFolder() {
		driver.waitForPageToBeReady();
		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);
		base.waitForElement(getShowDocumentsCountToogle());
		base.waitTillElemetToBeClickable(getShowDocumentsCountToogle());
		if (!base.text("All Folders (").isDisplayed()) {
			base.passedStep("Documents Count is not Displayed");
		} else {
			base.failedStep("Documents Count is Displayed");
		}
	}

	public void showDocsCountisONFolder() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getFoldersTab());
		getFoldersTab().waitAndClick(5);
		base.waitForElement(getShowDocumentsCountToogle());
		base.waitTillElemetToBeClickable(getShowDocumentsCountToogle());
		getShowDocumentsCountToogle().waitAndClick(5);
		driver.waitForPageToBeReady();
		Thread.sleep(1000);
		if (!base.text("All Folders (").isDisplayed()) {
			base.passedStep("Documents Count is Displayed");
		} else {
			base.failedStep("Documents Count is not Displayed");
		}
	}
	
	/**
	 * @author: Arun Created Date: 20/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the warning message when tag/folder release without selecting SG               
	 */
	public void verifyWarningMessageForTagOrFolderReleaseWithoutSG(String type,String name) {
		
		if(type.equalsIgnoreCase("folder")) {
			base.waitForElement(getFoldersTab());
			getFoldersTab().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			getFolderName(name).ScrollTo();
			base.waitForElement(getFolderName(name));
			getFolderName(name).waitAndClick(10);
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitForElement(getFolderActionDropDownArrow());
			getFolderActionDropDownArrow().waitAndClick(10);
			base.waitForElement(getBulkReleaseFolder());
			getBulkReleaseFolder().waitAndClick(10);
			getBulkRelease_ButtonRelease().waitAndClick(10);
			base.VerifyWarningMessage("Please select Security Group");
			base.CloseSuccessMsgpopup();
			base.waitForElement(getReleaseCancelButton());
			getReleaseCancelButton().waitAndClick(10);
		}
		else if(type.equalsIgnoreCase("tag")) {
				base.waitForElement(getTagsTab());
				getTagsTab().waitAndClick(5);
				driver.scrollingToBottomofAPage();
				getTagName(name).ScrollTo();
				base.waitForElement(getTagName(name));
				getTagName(name).waitAndClick(10);
				driver.waitForPageToBeReady();
				driver.scrollPageToTop();
				base.waitForElement(getTagActionDropDownArrow());
				getTagActionDropDownArrow().waitAndClick(10);
				base.waitForElement(getBulkReleaseAction());
				getBulkReleaseAction().waitAndClick(10);
				getBulkRelease_ButtonRelease().waitAndClick(10);
				base.VerifyWarningMessage("Please select Security Group");
				base.CloseSuccessMsgpopup();
				base.waitForElement(getReleaseCancelButton());
				getReleaseCancelButton().waitAndClick(10);
			}
		}
	
	/**
	 * @author: Arun Created Date: 20/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform bulk release folder action to SG               
	 */
	public void bulkReleaseFolderToSecurityGroup(String securityGroupName) {

		driver.waitForPageToBeReady();
		getFolderActionDropDownArrow().waitAndClick(10);
		base.waitForElement(getBulkReleaseFolder());
		getBulkReleaseFolder().waitAndClick(10);
		base.waitForElement(getBulkRelOther_CheckBox(securityGroupName));
		getBulkRelOther_CheckBox(securityGroupName).waitAndClick(10);
		base.waitForElement(getBulkRelease_ButtonRelease());
		getBulkRelease_ButtonRelease().waitAndClick(10);
		base.waitForElement(getFinalizeButton());
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Records saved successfully");

	}
	
	/**
	 * @author Krishna
	 * @Description - Tag is Unreleased to securitygroup - Folder
	 * @param - SecGroup
	 */
	public void verifyBulk_UnreleaseTagInSg(String SecGroup) {
		driver.waitForPageToBeReady();
		base.waitForElement(getBulkRelDefaultSecurityGroup_CheckBox(SecGroup));
		getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(8);
		base.waitForElement(getBulkRelease_ButtonUnRelease());
		getBulkRelease_ButtonUnRelease().waitAndClick(8);
		base.VerifySuccessMessageB("Records saved successfully");
		base.CloseSuccessMsgpopup();

	}

	/**
	 * @author Krishna
	 * @Description - Folder is released to securitygroup - Folder
	 * @param - Foldername securityGroupName
	 */
	public void bulkReleaseFolderInSg(String Foldername, String securityGroupName) {
		driver.waitForPageToBeReady();
		base.waitForElement(getFolderName(Foldername));
		getFolderName(Foldername).waitAndClick(5);
		base.waitForElement(getFolderActionDropDownArrow());
		getFolderActionDropDownArrow().waitAndClick(10);
		base.waitForElement(getBulkReleaseFolder());
		getBulkReleaseFolder().waitAndClick(10);
		base.waitForElement(getBulkRelOther_CheckBox(securityGroupName));
		getBulkRelOther_CheckBox(securityGroupName).waitAndClick(10);
		getBulkRelease_ButtonRelease().waitAndClick(10);
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Records saved successfully");
		base.CloseSuccessMsgpopup();
	}
	
	/**
	 * @author Krishna
	 * @Description - Folder is unreleased to securitygroup - Folder
	 * @param - SecGroup
	 */

	public void verifyBulk_UnreleaseFolderInSg(String SecGroup) {
		driver.waitForPageToBeReady();
		base.waitForElement(getFolderActionDropDownArrow());
		getFolderActionDropDownArrow().waitAndClick(10);
		base.waitForElement(getBulkReleaseFolder());
		getBulkReleaseFolder().waitAndClick(10);
		base.waitForElement(getBulkRelDefaultSecurityGroup_CheckBox(SecGroup));
		getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(8);
		base.waitForElement(getBulkRelease_ButtonUnRelease());
		getBulkRelease_ButtonUnRelease().waitAndClick(8);
		base.VerifySuccessMessageB("Records saved successfully");
		base.CloseSuccessMsgpopup();

	}
	/**
	 * @author Brundha.T
	 * @param securityGroup
	 * @Description: verifying Propagate popup in Tags
	 */
	public void verifyingPopupInTags(String securityGroup) {

		base.waitTillElemetToBeClickable(getSecurityGroupTag());
		getSecurityGroupTag().selectFromDropdown().selectByVisibleText(securityGroup);
		base.waitForElement(getAddTagTable());
		getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		getAllTagRoot().waitAndClick(5);
		base.waitForElement(getTagActionDropDownArrow());
		getTagActionDropDownArrow().waitAndClick(5);
		getAddTag().waitAndClick(10);
		base.waitTillElemetToBeClickable(getTagPropagatePopup());
		getTagPropagatePopup().waitAndClick(2);
		String PropagateCompareString = "Please specify the relevant options for tag/folder propagation. For example, select the 'Family Members' option, "
				+ "if you would like the same tag/folder to be propagated/applied to all the other family members of this document, when a document "
				+ "is being tagged/foldered. Similarly, you can propagate this tag to Exact Duplicates, Email Threads (all other documents with the same ThreadID) "
				+ "and Email Duplicates by selecting the corresponding options. Note that Exact Duplicates propagates tag/folder to all other docs having the same"
				+ " MD5Hash metadata, irrespective of whether these docs are parents, children or standalone docs.";
		String PropagatePopupText = getTextInTag().getText();
		base.compareTextViaContains(PropagatePopupText, PropagateCompareString,
				"" + PropagatePopupText + " is displayed as expected",
				"" + PropagatePopupText + " not displayed as expected");

	}
/**
 * @author Brundha.T
 * @param securityGroup
 * @param tagGroupName
 * @Description:creating new tag group
 */
	public void CreatingNewTagGroup(String securityGroup,String tagGroupName) {
		base.waitTillElemetToBeClickable(getSecurityGroupTag());
		getSecurityGroupTag().selectFromDropdown().selectByVisibleText(securityGroup);
		base.waitForElement(getAddTagTable());
		getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(getAllTagRoot());
		getAllTagRoot().waitAndClick(5);
		selectActionArrow("New Tag Group");
		getNewTagGroupInputTextBox().SendKeys(tagGroupName);
		getNewTagGroupSaveBtn().waitAndClick(3);

	}
	/**
	 * @author Brundha.T
	 * @description: verifying all tag group
	 */
public void verifyingAllTagsGroup(String TagGroup) {
	String ActualText=getAllTagRoot().GetAttribute("data-content");
	System.out.println(ActualText);
	String ExpectedText="All Tags";
	base.textCompareEquals(ActualText, ExpectedText, "All Tags group is displayed as expected","All tag group is not displayed");
	base.ValidateElement_Presence(getTagName(TagGroup),TagGroup);
	
}


/**
 * @author Sowndarya.velraj
 * @description: Create a new tag from popup window
 */
public void createNewTagFromActionPopup(String tagname, String classificationname) {

	driver.waitForPageToBeReady();
	base.waitForElement(getNewTag());
	getNewTag().waitAndClick(10);
	
	base.waitForElement(getAllTags());
	getAllTags().waitAndClick(10);
	
	base.waitForElement(getEnterTagName());
	getEnterTagName().SendKeys(tagname);

	base.waitForElement(getTagClassification());
	getTagClassification().selectFromDropdown().selectByVisibleText(Input.technicalIssue);

	base.waitForElement(getContinueButton());
	getContinueButton().waitAndClick(10);

}

}

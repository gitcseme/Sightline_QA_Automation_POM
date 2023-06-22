package pageFactory;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
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

public class DocViewMetaDataPage {

	Driver driver;
	public static int pureHit;
	BaseClass base;
	SoftAssert softAssert;

	public Element getTab(String tabName) {
		return driver.FindElementByXPath("//span[text()='" + tabName + "']//..//self::a[@class='a-style']");
	}

	public Element getTabById(String tabId) {
		return driver.FindElementById(tabId);
	}

	public Element getBrowseAllHistoryButton() {
		return driver.FindElementById("btnViewAllHistory");
	}

	public Element getExportButton() {
		return driver.FindElementById("btnExportHistoryExcel");
	}

	public Element getCloseButton() {
		return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getFirstRowCheckBox(String rowNum) {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentConceptuallySimilar']/tbody[1]/tr[" + rowNum + "]/td[1]/label[1]/i[1]");
	}

	public Element getAnalyticActionButton() {
		return driver.FindElementById("btnAnalyticAction");
	}

	public Element getFolderOption() {
		return driver.FindElementById("liConceptualBulkFolder");
	}

	public Element getNewFolderLink() {
		return driver.FindElementByXPath("//a[@id='tabNew']  | //a[text()='New Folder']");
	}

	public Element getSelectAllFoldersOption() {
		return driver.FindElementByXPath("//div[@id='folderJSTree']//a[@class='jstree-anchor']");
	}

	public Element getNameTextField() {
		return driver.FindElementById("txtFolderName");
	}

	public Element getContinueButton() {
		return driver.FindElementById("btnAdd");
	}

	public ElementCollection getActionRequestToogles() {
		return driver.FindElementsByXPath("//input[@name='checkbox-toggle']");
	}

	public Element getFinalizeAssignmentButton() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	public Element getFolderName(String folderName) {
		return driver.FindElementByXPath("//li[@role='treeitem']//a['" + folderName + "']");
	}

	public Element getFolderButton() {
		return driver.FindElementById("liDocumentFolder");
	}

	public Element getFolderBtn() {
		return driver.FindElementById("liDocumentFolder");
	}

	public Element getFieldNameTableColumnHeader() {
		return driver.FindElementByXPath("//th[text()='Field Name']");
	}

	public Element getHistoryTab() {
		return driver.FindElementById("liDocumentHistory");
	};

	public Element getChkIncludeFamilyMemeber() {
		return driver.FindElementByXPath("//input[@id='chkIncludeFamilyMemeber']/following-sibling::i");
	};

	public Element getChkIncludeEmailThreads() {
		return driver.FindElementByXPath("//input[@id='chkIncludeEmailThreads']/following-sibling::i");
	};

	public Element getChkIncludeNearDuplicates() {
		return driver.FindElementByXPath("//input[@id='chkIncludeNearDuplicates']/following-sibling::i");
	};

	public Element getExpandFolderTree() {
		return driver.FindElementByXPath(
				"//li[@class='jstree-node  jstree-closed jstree-last']//i[@class='jstree-icon jstree-ocl']");
	};

	public Element getFolderName() {
		return driver.FindElementByXPath("//ul[@class='jstree-container-ul jstree-children']//ul//a");
	}

	public Element getDocSecondRow() {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//tr[2]//td[2]");
	}

	public Element getHistoryTabPaginate() {
		return driver.FindElementByXPath("//div[@id='dtDocumentAllHistory_paginate']");
	}

	public Element getNameHeaderOnTable() {
		return driver.FindElementByXPath("//table[@id='dtDocumentHistory']//th[text()='Name']");
	}

	public Element getName(int rowNumber) {
		return driver
				.FindElementByXPath("//table[@id='dtDocumentHistory']//tr[" + String.valueOf(rowNumber) + "]//td[2]");
	}

	public Element getAction(int rowNumber) {
		return driver
				.FindElementByXPath("//table[@id='dtDocumentHistory']//tr[" + String.valueOf(rowNumber) + "]//td[1]");
	}

	public ElementCollection getRowsHistoryTable() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentHistory']//tr//td[2]");
	}

	public Element getHiddenProperties() {
		return driver
				.FindElementByXPath("//table[@id='MetaDataDT']//td[text()='HiddenProperties']/following-sibling::td");
	}

	public Element getDocView_Redact_Rectangle() {
		return driver.FindElementById("blackRectRedact_divDocViewer");
	}

	public Element getRedactIcon() {
		return driver.FindElementByXPath("//li[@id='gray-tab']//a[@title='Redact']");
	}

	public Element getRedactRectBoard() {
		return driver.FindElementByCssSelector("svg:nth-child(1) > rect:nth-child(1)");
	}

	public Element getSelectReductionTagDropDown() {
		return driver.FindElementById("ddlRedactionTagsForPopup");
	}

	public Element getSaveButton() {
		return driver.FindElementById("btnSave");
	}

	public Element getYellowUnRedactRectButton() {
		return driver.FindElementByXPath("//li[@id='yellow-tab']/a");
	}

	public Element getUntaggedRectangleButton() {
		return driver.FindElementById("highlightRectangle_divDocViewer");
	}

	public ElementCollection getUntaggedRedacctions() {
		return driver.FindElementsByCssSelector("g[data-pcc-mark*='mark']");
	}

	public Element getUnTaggedDeleteButton() {
		return driver.FindElementByXPath("//div[@class='pcc-pull-right']//button[@id='btn_delete']");
	}

	public Element getUnTaggedDropDown() {
		return driver.FindElementByXPath("//div[@class='pcc-tab-content pcc-open']//select[@id='ddlRedactionTags']");
	}

	public Element getMetaDataTab() {
		return driver.FindElementById("liDocumentMetadata");
	};

	public Element getTimeStamp() {
		return driver.FindElementByXPath("//th[text()='Time Stamp']");
	}

	public Element getActionByRowInHistory(int row) {
		return driver.FindElementByXPath(
				"//div[@class='dt-toolbar']/following-sibling::table//tbody/tr[" + String.valueOf(row) + "]/td[1]");
	}

	public Element getProjectFieldTextField(String projectFieldName) {
		return driver.FindElementByXPath("//input[@projectfieldname='" + projectFieldName + "']");
	}

	public Element getSaveLink() {
		return driver.FindElementByXPath("//a[@id='Save']");
	}

	public Element getFieldNameInTable(String projectField) {
		return driver.FindElementByXPath("//td[text()='" + projectField + "']");
	}

	public ElementCollection getHistoryTableColumns() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentHistory']//th");
	}

	public Element getBrowseAllMetaDataButton() {
		return driver.FindElementById("btnViewAllMetaData");
	}

	public Element getMetaDataPopupHeader() {
		return driver.FindElementByXPath("//span[text()='All Metadata for Document']");
	}

	public Element getFieldNameHeaderOnMetadataPopup() {
		return driver.FindElementByXPath("//table[@class='table dataTable no-footer']//th[text()='Field Name']");
	}

	public Element getFieldValueHeaderOnMetadataPopup() {
		return driver.FindElementByXPath("//table[@class='table dataTable no-footer']//th[text()='Field Value']");
	}

	public Element getMetaDataTableOnPopup() {
		return driver.FindElementByXPath("//table[@id='MetaDataDT1']");
	}

	public ElementCollection getExcelFileDoc() {
		return driver.FindElementsByXPath("//table[@id='SearchDataTable']//tr//td[2]");
	}

	public Element getTotalPageCountSelectedDoc() {
		return driver.FindElementById("lblTotalPageCount_divDocViewer");
	}

	public Element getNextButtonForDoc() {
		return driver.FindElementById("nextPage_divDocViewer");
	}

	public Element getPageNumberTextFieldDocView() {
		return driver.FindElementById("nextPage_divDocViewer");
	}

	public Element getActionButton() {
		return driver.FindElementById("btnAction");
	}

	public Element getFolderOption2() {
		return driver.FindElementById("liMiniDocListBulkFolder");
	}

	public ElementCollection getSelectedFileCheckBox() {
		return driver.FindElementsByXPath("//tr//td[contains(text(),'MS Excel Worksheet')]/preceding-sibling::td//i");
	}

	public Element getSuccessMsgCloseButton() {
		return driver.FindElementByXPath("//i[@class='botClose fa fa-times']");
	}

	// Added By Gopinath - 01-09-2021
	public Element getMetaDataListPaginated() {
		return driver.FindElementByXPath("//ul[@class='pagination pagination-sm']");
	}

	public Element getThirdPageMetaDataList() {
		return driver.FindElementByXPath("//li[@class='paginate_button ']//a[text()='3']");
	}

	// Added by Gopinath - 07-09-2021
	public Element getSecurityGroupDropDownButton() {
		return driver.FindElementById("dropdownclick");
	}

	public Element getSecurityGroup(String securityGroup) {
		return driver.FindElementByXPath("//a[@title='" + securityGroup + "']");
	}

	public Element getDocView_Print() {
		return driver.FindElementByXPath("//*[@id='print_divDocViewer']//a");
	}

	public Element getbackgroundDownLoadLink() {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]/td[contains(.,'Document Print')]");
	}

	public Element getBatchPrintStatus() {
		return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[1]/td[text()='COMPLETED']");
	}

	public Element getDownLoadLink() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[1]//a[text()='View File']");
	}

	// Added by Gopinath - 17-09-2021
	public Element getResponsivenessField() {
		return driver.FindElementByXPath("//span[@id='l_it_1']/following-sibling::div");
	}

	public Element getHotDocCheckBox() {
		return driver.FindElementByXPath("//input[@name='TAG' and @id='8_checkbox']/following-sibling::span");
	}

	public Element getPrivilegedField() {
		return driver.FindElementByXPath(
				"//span[@id='l_it_11']/following-sibling::div | //span[text()='Privileged']/following-sibling::div");
	}

	public Element getPrivilegeTypeField() {
		return driver.FindElementByXPath(
				"//span[@id='l_it_14']/following-sibling::div | //span[text()='Privilege Type']/following-sibling::div");
	}

	public Element getConfidentialityField() {
		return driver.FindElementByXPath(
				"//span[@id='l_it_17']/following-sibling::div | //span[text()='Confidentiality']/following-sibling::div");
	}

	public Element getCommentTextField() {
		return driver.FindElementByXPath("//textarea[@name='COMMENT']");
	}

	public Element getTechnicalIssueRadioButton() {
		return driver.FindElementByXPath("//input[@class='radiobox' and @id='14_radio']/following-sibling::span");
	}

	public Element getForeignLanguageCheckBox() {
		return driver.FindElementByXPath("//input[@name='checkgroup_10' and @id='5_checkbox']/following-sibling::span");
	}

	public Element getPrivilegedRadioButton() {
		return driver.FindElementByXPath("//input[@name='radiogroup_11' and @id='11_radio']/following-sibling::span");
	}

	public Element getAttorneyClientCheckBox() {
		return driver.FindElementByXPath("//input[@name='checkgroup_14' and @id='2_checkbox']/following-sibling::span");
	}

	public Element getConfidentialRadioButton() {
		return driver.FindElementByXPath("//input[@name='radiogroup_17' and @id='4_radio']/following-sibling::span");
	}

	public Element getSaveAndNextLink() {
		return driver.FindElementById("SaveAndNext");
	}

	public Element getPresentDocumentSelectedID() {
		return driver.FindElementByXPath("//tr[contains(@class,'doc_current')]//td[2]");
	}

	public Element getNextDocumentIdToSelectedDoc() {
		return driver.FindElementByXPath("//tr[contains(@class,'doc_current')]/following-sibling::tr[1]//td[2]");
	}

	// Added By Jeevitha
	public Element getBrowseAllHistoryBtn() {
		return driver.FindElementByXPath("//button[contains(text(),'Browse All History')]");
	}

	// modified By Jeevitha 27/12/2022
	public Element getTimeStampInAllActivities() {
		return driver.FindElementByXPath("//div[@id='dtDocumentAllHistory_wrapper']//th[text()='Time Stamp']");
	}

	// modified By Jeevitha 27/12/2022
	public Element getFirstRowOfActionColumn() {
		return driver.FindElementByXPath("//table[@id='dtDocumentAllHistory']//tr[1]//td[1]");
	}

	//modified by Jeevitha
	public Element getCloseButtonInAllActivities() {
		return driver.FindElementByXPath(
				"//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getAllRedactionCount() {
		return driver.FindElementById("counterAll");
	}

	public Element getHistoryTag() {
		return driver.FindElementByXPath("//span[text()='History']");
	}

	// added by jayanthi
	public Element getRowsOfActionColumn(int i) {
		return driver.FindElementByXPath("//table[@id='dtDocumentAllHistory']/tbody/tr[" + i + "]/td[1]");
	}

	// Added by Gopinath - 25/11/2021
	public Element getGearToggle() {
		return driver.FindElementByXPath("//a[@id='wEdit']//i");
	}

	public Element getDocumentListMaximizeBtn() {
		return driver.FindElementByXPath("//header[@id='HdrMiniDoc']//div[@class='button-icon jarviswidget-pop-btn']");
	}

	public Element getReviewMetaData() {
		return driver.FindElementByXPath("//span[@class='bold-gray-title']");
	}

	public Element getCodingMaximizeBtn() {
		return driver
				.FindElementByXPath("//header[@id='HdrCoddingForm']//div[@class='button-icon jarviswidget-pop-btn']");
	}

	public Element getCodingNewWindow() {
		return driver.FindElementByXPath("//option[contains(text(),'Default Project Coding Form')]");

	}

	public Element getDocumentMetaDataMaximizeBtn() {
		return driver.FindElementByXPath("//header[@id='HdrMetaData']//div[@class='button-icon jarviswidget-pop-btn']");
	}

	public Element getDocumentDataNewWindowTitle() {
		return driver.FindElementByXPath("//span[@class='text-uppercase tab-text']");
	}

	public Element getSaveBtn() {
		return driver.FindElementByXPath("//a[@id='wSave']/i");
	}

	// Added by gopinath - 08/12/2021
	public Element getNonAudioRemarkBtn() {
		return driver.FindElementById("remarks-btn");
	}

	public Element getSignoutMenu() {
		return driver.FindElementByXPath("//*[@id='user-selector']");
	}

	public Element getAnotherRemarkMessage() {
		return driver.FindElementByXPath("//div[@id='disableRedactionWarningForRemarks']//span[1]");
	}

	// Added by Gopinath - 09/12/2021
	public Element getFirstRowCheckBoxFamilyMem() {
		return driver.FindElementByXPath("//table[@id='dtDocumentFamilyMembers']//tr[1]//td[1]//i");
	}

	public Element getFamilyMemberTab() {
		return driver.FindElementById("liDocumentFamilyMember");
	}

	public Element getFamilyMemFolderButton() {
		return driver.FindElementByXPath("//div[@id='hb2']//a[text()='Folder']");
	}

	public Element getRowFromMiniDoclist(String docID) {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//tr//td[text()='" + docID + "']");
	}

	// Added by gopinath - 09/12/2021
	public Element currentDocument() {
		return driver.FindElementByXPath("//div[@class='igViewerGraphics']");
	}

	public Element HighliteIcon() {
		return driver.FindElementByXPath("//li[@id='yellow-tab']");
	}

	public ElementCollection getAppliedAnnotation() {
		return driver.FindElementsByCssSelector("rect[data-pcc-mark*='mark'][style*='255']");
	}

	public Element getRectangleButton() {
		return driver.FindElementById("highlightRectangle_divDocViewer");
	}

	// Added by gopinath - 10/Dec/2021
	public ElementCollection getDocumentsToSelect() {
		return driver.FindElementsByXPath("//table[@id='SearchDataTable']//tr//td[1]//i[contains(@style,'top')]");
	}

	public Element getProjectFieldInMetaData(String projectField) {
		return driver.FindElementByXPath("//table[@id='MetaDataDT']//td[text()='" + projectField + "']");
	}

	public Element getAllFolders() {
		return driver.FindElementByXPath("//div[@id='LavelOneDocumentFolders']//a[text()='All Folders']");
	}

	// Added by Gopinath-10/12/2021
	public Element getAllFieldNameColumn() {
		return driver.FindElementByXPath("//div[@id='MetaDataDT_wrapper']//th[text()='Field Name']");
	}

	public Element getAllFieldValueColumn() {
		return driver.FindElementByXPath("//div[@id='MetaDataDT_wrapper']//th[text()='Field Value']");
	}

	// Added by Gopinath - 14/Dec/2021
	public Element getMetaDataPanel() {
		return driver.FindElementByXPath("(//div[@id='rightPalette_MetaData']//div)[1]");
	}

	// Added by Gopinath - 14/Dec/2021
	public Element getDownloadOption() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[1]//td[2]");
	}

	public Element getDownloadButton() {
		return driver.FindElementByXPath("//li[@id='liDocumentTypeDropDown']");
	}

	// Added by Gopinath - 11/01/2022

	public Element getAddRemarkbtn() {
		return driver.FindElementById("addRemarks");
	}

	public Element getRemarkTextArea() {
		return driver.FindElementById("txt_remark");
	}

	public Element getSaveRemark() {
		return driver.FindElementByXPath("(//span[@id='remarksSaveCancelControls']/i[2])[1]");
	}

	public Element getParentDocID(String docID) {
		return driver.FindElementByXPath(
				"//table[@id='MetaDataDT']//td[text()='ParentDocID']//..//td[text()='" + docID + "']");
	}

	// Added by Gopinath
	public ElementCollection getAddedRemark() {
		return driver.FindElementsByCssSelector("g[data-pcc-mark*='highlighttextannotation']");
	}

	public ElementCollection getDocViewAppliedAnnotation() {
		return driver.FindElementsByCssSelector("rect[data-pcc-mark*='mark'][style*='rgb(255, 255, 0)']");
	}

	// Added by Gopinath - 01/02/2021
	public Element getMetaDataTableOnPopupFieldName() {
		return driver.FindElementByXPath("//div[@id='MetaDataDT1_wrapper']/descendant::th[text()='Field Name']");
	}

	public Element getMetaDataTableOnPopupFieldValue() {
		return driver.FindElementByXPath("//div[@id='MetaDataDT1_wrapper']/descendant::th[text()='Field Value']");
	}

	public ElementCollection getMetadataPopUpFieldnameList() {
		return driver.FindElementsByXPath("//table[@id='MetaDataDT1']//td[@class='sorting_1']");
	}

	public Element getTimeStampHeader() {
		return driver.FindElementByXPath("// div[@id='dtDocumentAllHistory_wrapper']//th[text()='Time Stamp']");
	}

	public Element getRemarkData() {
		return driver.FindElementByXPath("//table[@id='dtDocumentAllHistory']//td[text()='UpdatedRemark9115399']");
	}

	public ElementCollection getAHeaderList() {
		return driver.FindElementsByXPath(
				"// div[@class='dataTables_scrollHeadInner']//table[@class='table std-table dataTable no-footer']//th");
	}

	public Element getResult(String remark, int index) {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentAllHistory']//td[text()='" + remark + "']//..//td[" + index + "]");
	}

	// added by arun
	public Element getDocLanguageMetadataValue() {
		return driver.FindElementByXPath(
				"//table[@id='MetaDataDT']//td[contains(text(),'DocLanguages')]//following-sibling::td");
	}

	public Element selectDocument(int row) {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//tbody//tr['" + row + "']");
	}

	public DocViewMetaDataPage(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		softAssert = new SoftAssert();
	}

	/**
	 * @author Gopinath Modified by : Gopinath Modified date : 24-08-2021 Method to
	 *         get Tab OnDoc View By Text
	 * @param tabName - (tabName is string value that name of tab.)
	 */
	public void getTabOnDocViewByText(String tabName) {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();

			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			getTab(tabName).ScrollTo();
			Point p = getTab(tabName).getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			Thread.sleep(Input.wait30 / 15);
			getTab(tabName).ScrollTo();
			driver.scrollingToElementofAPage(getTab(tabName));
			getTab(tabName).isElementAvailable(15);
			base.waitForElement(getTab(tabName));
			getTab(tabName).Displayed();
			getTab(tabName).Click();
		} catch (Exception e) {
			UtilityLog.info("Tab on doc view by text is failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to get Tab OnDoc View By ID
	 * @param tabName - (tabId is string value that name of tab.)
	 */
	public void getTabOnDocViewById(String tabId) {
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getTabById(tabId).getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			Thread.sleep(Input.wait30 / 10);
			getTabById(tabId).isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTabById(tabId).Displayed();
				}
			}), Input.wait90);
			getTabById(tabId).isElementAvailable(15);
			Assert.assertEquals("Conceptial tab is not displayed", true,
					getTabById(tabId).getWebElement().isDisplayed());
			getTabById(tabId).Click();
		} catch (Exception e) {
			UtilityLog.info("Get tab on Doc view by Id is failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Modified By : Gopinath Modified date : 24-08-2021. Method to
	 *         Verify Export Option Is Displayed
	 */
	public void verifyExportOptionIsDisplayed() {
		try {
			driver.scrollingToBottomofAPage();
			getBrowseAllHistoryButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getBrowseAllHistoryButton().Displayed();
				}
			}), Input.wait90);
			getBrowseAllHistoryButton().Displayed();
			getBrowseAllHistoryButton().waitAndClick(15);
			driver.scrollingToElementofAPage(getExportButton());
			getExportButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getExportButton().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Export button is not displayed successfully", true,
					getExportButton().getWebElement().isDisplayed());
			if (getExportButton().getWebElement().isDisplayed()) {
				base.passedStep("Export button is displayed successfully");
			}
		} catch (Exception e) {
			UtilityLog.info("Failed to Perform advanced Search With Content And MetaData Option");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to Verify History Pop Is Paginated
	 */
	public void verifyHistoryPopIsPaginated() {
		try {
			getHistoryTabPaginate().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getHistoryTabPaginate().Enabled();
				}
			}), Input.wait90);
			Thread.sleep(Input.wait30 / 10);
			if (getHistoryTabPaginate().getWebElement().isDisplayed()) {
				base.passedStep("Pagination is displayed in history pop up successfully");

			}
			Assert.assertEquals("Pagination is not loaded/displayed in history pop up", true,
					getHistoryTabPaginate().getWebElement().isDisplayed());
			getCloseButton().isElementAvailable(15);
			getCloseButton().Displayed();
			getCloseButton().Click();
		} catch (Exception e) {
			UtilityLog.info("Failed to Perform advanced Search With Content And MetaData Option");
			e.printStackTrace();
		}

	}

	/**
	 * @author Gopinath Modified By- Gopinath Modified date : 24-08-2021 Method to
	 *         select Doc And Create Folder
	 * @param tabId      - (tabId is string value that name of tab.)
	 * @param folderName - (folderName is string value that name of folder.)
	 */
	public void selectDocAndCreateFolder(String tabId, String folderName) {
		try {
			boolean flag = false;
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			getTabOnDocViewById(tabId);
			try {
				driver.scrollingToElementofAPage(getFirstRowCheckBox("1"));
				getFirstRowCheckBox("1").isElementAvailable(20);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getFirstRowCheckBox("1").Enabled();
					}
				}), Input.wait90);
				getFirstRowCheckBox(String.valueOf(1)).isElementAvailable(15);
				base.waitForElement(getFirstRowCheckBox(String.valueOf(1)));
				getFirstRowCheckBox(String.valueOf(1)).waitAndClick(15);
				flag = true;
			} catch (Exception e) {
				base.failedStep("Documents are not present in conceptual similar table");
			}
			if (flag == true) {
				driver.scrollingToElementofAPage(getAnalyticActionButton());
				getAnalyticActionButton().isElementAvailable(15);
				base.waitForElement(getAnalyticActionButton());
				getFolderOption().isElementAvailable(15);
				selectValueFromDropDown(getAnalyticActionButton(), getFolderOption());
				getNewFolderLink().isElementAvailable(15);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getNewFolderLink().Displayed();
					}
				}), Input.wait90);
				getNewFolderLink().Displayed();
				getNewFolderLink().Click();
				getSelectAllFoldersOption().isElementAvailable(15);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSelectAllFoldersOption().Displayed();
					}
				}), Input.wait90);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSelectAllFoldersOption().Enabled();
					}
				}), Input.wait90);

				getSelectAllFoldersOption().Displayed();
				getSelectAllFoldersOption().Click();

				try {
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_F11);
					robot.keyRelease(KeyEvent.VK_F11);
				} catch (Exception e) {
					UtilityLog.info("Failed to full size screen");
					e.printStackTrace();
				}
				getNameTextField().isElementAvailable(15);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getNameTextField().Enabled();
					}
				}), Input.wait90);
				getNameTextField().SendKeys(folderName);
				getContinueButton().Displayed();
				getContinueButton().Click();

				enableActionRequestToogles();
				getFinalizeAssignmentButton().isElementAvailable(15);
				getFinalizeAssignmentButton().Click();
				base.getSuccessMsg().isElementAvailable(15);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return base.getSuccessMsg().Displayed();
					}
				}), Input.wait90);
				base.getSuccessMsg().isElementAvailable(10);
				Assert.assertEquals("Success message is not displayed", true,
						base.getSuccessMsg().getWebElement().isDisplayed());
				if (base.getSuccessMsg().getWebElement().isDisplayed()) {
					UtilityLog.info("Success message is displayed successfully");
					Reporter.log("Success message is displayed successfully");
				}
				driver.getWebDriver().navigate().refresh();
				driver.waitForPageToBeReady();
				driver.scrollPageToTop();
				getDocSecondRow().isElementAvailable(15);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getDocSecondRow().Enabled();
					}
				}), Input.wait90);
				base.waitForElement(getDocSecondRow());
				getDocSecondRow().waitAndClick(15);
				driver.scrollingToBottomofAPage();
				Point p = getFolderButton().getWebElement().getLocation();
				je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
				getFolderButton().isElementAvailable(15);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getFolderButton().Enabled();
					}
				}), Input.wait90);
				driver.scrollingToElementofAPage(getFolderButton());
				base.waitForElement(getFolderButton());
				getFolderButton().isElementAvailable(15);
				verifyAddedFolderNameIsDisplayed(folderName);
			}

		} catch (Exception e) {
			UtilityLog.info("Failed to Perform advanced Search With Content And MetaData Option");
			e.printStackTrace();
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: 23-08-2021 Modified
	 *         by:Gopinath.
	 * @description : This method is used for selecting value from drop down.
	 */
	public void selectValueFromDropDown(Element dropDownButtonElement, Element dropDownValueElement) {
		try {
			Thread.sleep(Input.wait30 / 10);
			dropDownButtonElement.isElementAvailable(15);
			base.waitForElement(dropDownButtonElement);
			dropDownButtonElement.waitAndClick(15);
			Thread.sleep(Input.wait30 / 15);
			dropDownValueElement.isElementAvailable(15);
			base.waitForElement(dropDownValueElement);
			dropDownValueElement.waitAndClick(15);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while clicking on project field link" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Method to Enable Action Request Toggles
	 */
	public void enableActionRequestToogles() {
		try {
			driver.scrollPageToTop();
			getChkIncludeFamilyMemeber().ScrollTo();
			Thread.sleep(Input.wait30 / 10);
			getChkIncludeFamilyMemeber().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getChkIncludeFamilyMemeber().Enabled();
				}
			}), Input.wait90);
			getChkIncludeFamilyMemeber().waitAndClick(15);
			getChkIncludeEmailThreads().waitAndClick(15);
			getChkIncludeNearDuplicates().waitAndClick(15);
		} catch (Exception e) {
			UtilityLog.info("Failed to enable Action Request Toogle");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to verify Added Folder Name Is Displayed
	 * @param folderNam - - (folderNam is string value that name of folder.)
	 */
	public void verifyAddedFolderNameIsDisplayed(String folderNam) {
		try {
			String folderName = null;
			getExpandFolderTree().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getExpandFolderTree().Displayed();
				}
			}), Input.wait90);
			getExpandFolderTree().waitAndClick(15);
			driver.waitForPageToBeReady();
			getFolderName(folderNam).isElementAvailable(15);
			base.waitForElement(getFolderName(folderNam));
			if (getFolderName(folderNam).Displayed()) {
				base.passedStep("Folder name is successfully displayed in Folder structure by name :: " + folderNam);
			}
			softAssert.assertEquals((boolean) getFolderName(folderNam).Displayed(), true,
					"Folder name is not displayed in Folder structure by name :: " + folderNam);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception is occured while finding folder in folder structure");

		}
	}

	/**
	 * @author Gopinath Method to verify Folder Tab Is Disabled
	 */
	public void verifyFolderTabIsDisabled() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToElementofAPage(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().ScrollTo();
			getFieldNameTableColumnHeader().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFieldNameTableColumnHeader().Displayed();
				}
			}), Input.wait90);
			getFolderBtn().isElementAvailable(15);
			String value = getFolderBtn().GetAttribute("style").trim().toLowerCase();
			if (!value.contains("block")) {
				base.passedStep("Display folder is disabled successfully in doc view");
			}
			Assert.assertEquals("Folder tab is not disabled in doc view", false, value.contains("block"));
		} catch (Exception e) {
			UtilityLog.info("Folder tab is not disabled in doc view");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for navigating back from present page.
	 */
	public void navigateBack() {
		try {
			driver.getWebDriver().navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to verify Folder Tab Is Enabled
	 */
	public void verifyFolderTabIsEnabled() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToElementofAPage(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().ScrollTo();
			getFieldNameTableColumnHeader().isElementAvailable(15);
			getFolderBtn().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFolderBtn().Enabled();
				}
			}), Input.wait90);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFolderBtn().Displayed();
				}
			}), Input.wait90);
			getFolderBtn().isElementAvailable(15);
			String value = getFolderBtn().GetAttribute("style").trim().toLowerCase();
			if (value.contains("block")) {
				base.passedStep("Folder tab is successfully enabled in doc view");
			}
			Assert.assertEquals("Folder tab is not enabled in doc view", true, value.contains("block"));
		} catch (Exception e) {
			UtilityLog.info("Folder tab is not enabled in doc view");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for verify History Tab Is Disabled.
	 */
	public void verifyHistoryTabIsDisabled() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToElementofAPage(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().ScrollTo();
			getFieldNameTableColumnHeader().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFieldNameTableColumnHeader().Displayed();
				}
			}), Input.wait90);
			getHistoryTab().ScrollTo();
			getHistoryTab().isElementAvailable(15);
			String value = getHistoryTab().GetAttribute("style").trim().toLowerCase();
			if (!value.contains("block")) {
				base.passedStep("Display History is disabled successfully in doc view");
			}
			Assert.assertEquals("History tab is not disabled in doc view", false, value.contains("block"));
		} catch (Exception e) {
			UtilityLog.info("History tab is not disabled");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for verify History Tab Is Enabled.
	 */
	public void verifyHistoryTabIsEnabled() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToElementofAPage(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().ScrollTo();
			getFieldNameTableColumnHeader().isElementAvailable(15);
			getHistoryTab().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getHistoryTab().Displayed();
				}
			}), Input.wait90);
			String value = getHistoryTab().GetAttribute("style").trim().toLowerCase();
			if (value.contains("block")) {
				base.passedStep("History tab is successfully enabled in doc view");
			}
			Assert.assertEquals("History tab is not enabled in doc view", true, value.contains("block"));
		} catch (Exception e) {
			UtilityLog.info("History tab is not enabled");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for verify History Tab Is Displayed By Default.
	 */
	public void verifyHistoryTabIsDisplayedByDefault() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToElementofAPage(getHistoryTab());
			getHistoryTab().ScrollTo();
			getHistoryTab().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getHistoryTab().Displayed();
				}
			}), Input.wait90);
			Assert.assertEquals("History tab is not displayed by default", true,
					getHistoryTab().WaitUntilPresent().getWebElement().isDisplayed());
			if (getHistoryTab().WaitUntilPresent().getWebElement().isDisplayed()) {
				base.passedStep("History Tab is displayed by default");
			}
		} catch (Exception e) {
			UtilityLog.info("failed to verify History Tab Is Displayed By Default");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for verify Release To Security Group is Displayed.
	 */
	public void verifyReleaseToSecurityGroupisDisplayed() {
		try {
			driver.getWebDriver().navigate().refresh();
			driver.waitForPageToBeReady();
			driver.scrollingToElementofAPage(getHistoryTab());
			getHistoryTab().ScrollTo();
			getHistoryTab().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getHistoryTab().Displayed();
				}
			}), Input.wait90);
			driver.scrollingToBottomofAPage();
			driver.scrollingToElementofAPage(getHistoryTab());
			getHistoryTab().isElementAvailable(15);
			for (int count = 1; count <= getRowsHistoryTable().size(); count++) {
				driver.scrollingToElementofAPage(getName(count));
				getName(count).ScrollTo();
				getName(count).isElementAvailable(15);
				if (getName(count).getText().equalsIgnoreCase("ReleasedToSecuritygroup")) {
					base.passedStep("Release to Security Group is displayed successfully");
				}
			}
			driver.scrollingToBottomofAPage();
		} catch (Exception e) {
			UtilityLog.info("Failed to verify Release To Security Group is Displayed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for verify hidden properties is blank in meta data
	 *         table .
	 */
	public void verifyHiddenPropertiesIsBlank() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			driver.scrollingToElementofAPage(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().ScrollTo();
			base.waitForElement(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().isElementAvailable(15);
			driver.scrollingToBottomofAPage();
			getMetaDataTab().isElementAvailable(15);
			getMetaDataTab().waitAndClick(15);
			driver.scrollingToElementofAPage(getHiddenProperties());
			getHiddenProperties().ScrollTo();
			getHiddenProperties().isElementAvailable(15);
			if (getHiddenProperties().getText().contentEquals("")) {
				base.passedStep("hidden properties is blank");
			}
			Assert.assertEquals("hidden properties is not blank", true,
					getHiddenProperties().getText().contentEquals(""));

		} catch (Exception e) {
			UtilityLog.info("Hidden properties in history table is not blank");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method used for wait till element is clickable .
	 */
	public boolean waitTillElemetToBeClickable(Element element) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			wait.until(ExpectedConditions.elementToBeClickable(element.getWebElement()));
			status = true;
		} catch (Exception e) {
			UtilityLog.info("Exception occured whilr waiting for element is clickable");
		}
		return status;

	}

	/**
	 * @author Gopinath Method for Click on redact and set rectangle .
	 */
	public void clickOnRedactAndRectangle() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getRedactIcon().isElementAvailable(15);
			base.waitForElement(getRedactIcon());
			getRedactIcon().waitAndClick(15);
			base.waitForElement(getDocView_Redact_Rectangle());
			base.waitTillElemetToBeClickable(getDocView_Redact_Rectangle());
			getDocView_Redact_Rectangle().isElementAvailable(15);
			getDocView_Redact_Rectangle().waitAndClick(4);
		} catch (Exception e) {
			UtilityLog.info("Exception occcured while clicking on redact or rectangle" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for Set redact by rectangle by coordinates .
	 */
	public void redactbyrectangle(int off1, int off2, String redactiontag) throws InterruptedException {
		try {
			System.out.println(off1 + "...." + off2);
			Actions actions = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			// WebElement text = getRedactRectBoard().getWebElement();
			WebElement text = driver.getWebDriver().findElement(By.xpath("//div[@class='igViewerGraphics']"));

			actions.moveToElement(text, off1, off2).clickAndHold().moveByOffset(50, 60).release().perform();
			getSelectReductionTagDropDown().isElementAvailable(15);
			base.waitForElement(getSelectReductionTagDropDown());
			getSelectReductionTagDropDown().selectFromDropdown().selectByVisibleText(redactiontag);
			getSaveButton().isElementAvailable(15);
			base.waitForElement(getSaveButton());
			getSaveButton().waitAndClick(4);
			base.VerifySuccessMessage("Redaction tags saved successfully.");
			base.getCloseSucessmsg();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to select redacted area");
		}
	}

	/**
	 * @author Gopinath Modified by: Gopinath Modified Date : 24-08-2021 Method for
	 *         unRedaction on doc.
	 */
	public void unReduction(String redactiontag) throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitTime(3);
			List<WebElement> redact = getUntaggedRedacctions().FindWebElements();
			driver.waitForPageToBeReady();
			Actions actions = new Actions(driver.getWebDriver());
			actions.moveToElement(redact.get(0)).click().build().perform();
			getUnTaggedDropDown().isElementAvailable(15);
			base.waitForElement(getUnTaggedDropDown());
			getUnTaggedDropDown().selectFromDropdown().selectByVisibleText(redactiontag);
			getUnTaggedDeleteButton().isElementAvailable(15);
			base.waitForElement(getUnTaggedDeleteButton());
			getUnTaggedDeleteButton().Click();
			base.getSuccessMsg().isElementAvailable(15);
			base.waitForElement(base.getSuccessMsg());
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
				base.passedStep("Unreduction performed successfully");
			}
			actions.moveToElement(base.getCloseSucessmsg().getWebElement()).click().perform();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to unredact");
		}
	}

	/**
	 * @author Gopinath Method for clicking on unredaction.
	 */
	public void clickOnUnRedact() {
		try {
			driver.scrollPageToTop();
			getYellowUnRedactRectButton().isElementAvailable(15);
			base.waitForElement(getYellowUnRedactRectButton());
			getYellowUnRedactRectButton().Click();
		} catch (Exception e) {
			UtilityLog.info("Exception occcured while clicking on unredact" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Modified by : Gopinath Modified date : 24-8-2020 Method for
	 *         verifying reduction tagged and untagged added to history table.
	 */
	public void verifyReductionTaggedAndUnTaggedAddedToHistory() {
		try {
			String taggedReductionValue = null;
			String unTaggedRedutionVal = null;

			driver.scrollingToElementofAPage(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().ScrollTo();
			getFieldNameTableColumnHeader().isElementAvailable(15);
			base.waitForElement(getFieldNameTableColumnHeader());
			driver.scrollingToBottomofAPage();
			Actions actions = new Actions(driver.getWebDriver());
			driver.scrollingToElementofAPage(getHistoryTab());
			getHistoryTab().ScrollTo();
			getHistoryTab().isElementAvailable(15);
			actions.moveToElement(getHistoryTab().getWebElement()).click().perform();

			for (int count = 0; count < 2; count++) {
				Thread.sleep(Input.wait30 / 10);
				getTimeStamp().isElementAvailable(15);
				base.waitForElement(getTimeStamp());
				getTimeStamp().waitAndClick(15);
			}
			getActionByRowInHistory(2).isElementAvailable(15);
			actions.moveToElement(getActionByRowInHistory(2).getWebElement()).build().perform();
			Thread.sleep(Input.wait30 / 10);

			taggedReductionValue = getActionByRowInHistory(2).getText().toLowerCase().trim();
			getActionByRowInHistory(1).isElementAvailable(15);
			unTaggedRedutionVal = getActionByRowInHistory(1).getText().toLowerCase().trim();

			if (taggedReductionValue.contentEquals("redactiontagged")) {
				base.passedStep("Tagged Redaction tag is displayed in history table");
			} else {
				base.failedStep("Tagged Redaction tag is not displayed in history table");
			}

			if (unTaggedRedutionVal.contentEquals("redactionuntagged")) {
				base.passedStep("UnTagged Redaction tag is diaplayed in history table");
			} else {
				base.failedStep("UnTagged Redaction tag is not displayed in history table");
			}

		} catch (Exception e) {
			base.failedStep("Hidden properties in history table is not blank" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for entering project field in project field text field
	 *               and click on save..
	 * @param projectField : project field is string value that need to enter in
	 *                     project field text field
	 * @param value        : value is string that need to enter in project field
	 *                     text field.
	 */
	public void enterProjectFieldAndclickOnSave(String projectField, String value) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getProjectFieldTextField(projectField));
			getProjectFieldTextField(projectField).isElementAvailable(15);
			getProjectFieldTextField(projectField).SendKeys(value);
			driver.scrollPageToTop();
			getSaveLink().isElementAvailable(15);
			base.waitForElement(getSaveLink());
			getSaveLink().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while entering project field in projectField text field and click on save"
							+ e.getMessage());

		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 *         Description: Method for verifying entered project field in metadata
	 *         table..
	 * @param projectField : project field is string value that need to verify in
	 *                     meta data table
	 */
	public void verifyProjectFieldEnteredInMetadataTable(String projectField) {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getMetaDataTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getMetaDataTab().ScrollTo();
			getMetaDataTab().isElementAvailable(15);
			base.waitForElement(getMetaDataTab());
			getMetaDataTab().waitAndClick(15);
			getFieldNameInTable(projectField).ScrollTo();
			getFieldNameInTable(projectField).isElementAvailable(15);
			base.waitForElement(getFieldNameInTable(projectField));
			if (getFieldNameInTable(projectField).Displayed()) {
				base.passedStep("Project field is displayed in meta data table successfully");
			}
			Assert.assertEquals("Project field is not displayed in meta data table", true,
					getFieldNameInTable(projectField).getWebElement().isDisplayed());

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while entering project field in projectField text field and click on save"
							+ e.getMessage());

		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description : Method for verifying Performed Doc Actions Added To History
	 *              table such as reviewed..
	 */
	public void verifyPerformedDocActionsAddedToHistoryTable() {
		try {
			String reviewedActionRow = null;
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getHistoryTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			base.waitForElement(getHistoryTab());
			getHistoryTab().ScrollTo();
			getHistoryTab().isElementAvailable(15);
			getHistoryTab().waitAndClick(15);
			for (int count = 0; count < 2; count++) {
				Thread.sleep(Input.wait30 / 10);
				getTimeStamp().isElementAvailable(15);
				base.waitForElement(getTimeStamp());
				getTimeStamp().waitAndClick(15);
			}
			getActionByRowInHistory(1).isElementAvailable(15);
			Thread.sleep(Input.wait30 / 10);
			reviewedActionRow = getActionByRowInHistory(1).getText().toLowerCase().trim();

			if (reviewedActionRow.equalsIgnoreCase("Viewed")) {
				base.passedStep("Viewed is displayed in history table");
			} else {
				base.failedStep("Viewed is not displayed in history table");
			}
			Assert.assertEquals("Viewed is not displayed in history table", true,
					reviewedActionRow.equalsIgnoreCase("Viewed"));
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifing performed doc actions added to history" + e.getMessage());

		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying column header fields and validating names
	 *               on history table..
	 * @param actionColumnName    : actionColumnName is a String value that one of
	 *                            column header in history table.
	 * @param nameColumnName      : nameColumnName is a String value that one of
	 *                            column header in history table.
	 * @param userColumnName      : userColumnName is a String value that one of
	 *                            column header in history table.
	 * @param timeStampColumnName : timeStampColumnName is a String value that one
	 *                            of column header in history table.
	 */
	public void verifyHistoryTableColumnHeaders(String actionColumnName, String nameColumnName, String userColumnName,
			String timeStampColumnName) {
		try {
			String columnName = null;
			driver.waitForPageToBeReady();
			List<WebElement> columnElemts = driver.getWebDriver()
					.findElements(By.xpath("//table[@id='dtDocumentHistory']//th"));
			for (int column = 0; column < columnElemts.size(); column++) {
				columnName = columnElemts.get(column).getText().trim();
				if (columnName.equalsIgnoreCase(actionColumnName.trim())) {
					base.passedStep("Action column is displayed in history table header");
				} else if (columnName.equalsIgnoreCase(nameColumnName.trim())) {
					base.passedStep("Name column is displayed in history table header");
				} else if (columnName.equalsIgnoreCase(userColumnName.trim())) {
					base.passedStep("User column is displayed in history table header");
				} else if (columnName.equalsIgnoreCase(timeStampColumnName.trim())) {
					base.passedStep("Time stamp column is displayed in history table header");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifing performed doc actions added to history" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying meta data tab is displayed in doc view
	 *               page by default and verify browse all metadata fields button is
	 *               present..
	 */
	public void verifyMetadataTabFromDocViewPage() {
		try {
			driver.getWebDriver().navigate().refresh();
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getMetaDataTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getMetaDataTab().ScrollTo();
			getMetaDataTab().isElementAvailable(15);
			base.waitForElement(getMetaDataTab());
			Assert.assertEquals("Meta data tab is not displayed", true, getMetaDataTab().getWebElement().isDisplayed());
			getMetaDataTab().waitAndClick(15);
			getBrowseAllMetaDataButton().isElementAvailable(15);
			base.waitForElement(getBrowseAllMetaDataButton());
			Assert.assertEquals("Browse all meta data button is not displayed", true,
					getBrowseAllMetaDataButton().getWebElement().isDisplayed());
			getBrowseAllMetaDataButton().waitAndClick(15);

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifing verifying Metadata tab from Doc View page" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying fields on meta data popup column headers
	 *               of table..
	 * 
	 */
	public void verifyMetaDataPopup() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getMetaDataTableOnPopup().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getMetaDataTableOnPopup().isElementAvailable(15);
			base.waitForElement(getMetaDataTableOnPopup());
			if (getMetaDataTableOnPopup().Displayed()) {
				base.passedStep("Meta data table is displayed on popup successfully");
			}
			Assert.assertEquals("Meta data table is not displayed on popup", true,
					getMetaDataTableOnPopup().getWebElement().isDisplayed());
			getFieldNameHeaderOnMetadataPopup().isElementAvailable(15);
			base.waitForElement(getFieldNameHeaderOnMetadataPopup());
			if (getFieldNameHeaderOnMetadataPopup().Displayed()) {
				base.passedStep("Field name header column name on Meta data table is displayed on popup successfully");
			}
			Assert.assertEquals("Field name header column name on Meta data table is not displayed on popup", true,
					getFieldNameHeaderOnMetadataPopup().getWebElement().isDisplayed());
			getFieldValueHeaderOnMetadataPopup().isElementAvailable(15);
			base.waitForElement(getFieldValueHeaderOnMetadataPopup());
			if (getFieldValueHeaderOnMetadataPopup().Displayed()) {
				base.passedStep("Field value header column name on Meta data table is displayed on popup successfully");
			}
			Assert.assertEquals("Field value header column name on Meta data table is not displayed on popup", true,
					getFieldValueHeaderOnMetadataPopup().getWebElement().isDisplayed());

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifing verifying Metadata tab from Doc View page" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :: Method for verify metadata panel Is Disabled.
	 */
	public void verifyMetaDataTabIsDisabled() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getMetaDataPanel().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getMetaDataPanel().ScrollTo();
			getMetaDataPanel().isElementAvailable(15);
			String meataData = getMetaDataPanel().GetAttribute("id").trim();

			if (!meataData.equalsIgnoreCase("divMetaTab"))
				base.passedStep("Metadata tab is disabled successfully in doc view");
			else
				base.failedStep("Metadata tab is not disabled successfully in doc view");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifing disabling Metadata tab panel from assignment page"
					+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :: Method to verify meta data panel Is Enabled
	 */
	public void verifyMetaDataPanelIsEnabled() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getFieldNameTableColumnHeader().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			driver.scrollingToElementofAPage(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().ScrollTo();
			getFieldNameTableColumnHeader().isElementAvailable(15);
			getMetaDataTab().isElementAvailable(15);
			base.waitForElement(getMetaDataTab());
			String value = getMetaDataTab().GetAttribute("style").trim().toLowerCase();
			if (value.contains("block")) {
				base.passedStep("Meta data tab is successfully enabled in doc view");
			}
			Assert.assertEquals("Meta data tab is not enabled in doc view", true, value.contains("block"));
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifing Metadata tab panel is enabled from Doc view page"
					+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath modified By-Gopinath Modified date- 01-09-2021
	 * @Description :: Method to select paginated document and perform pagination.
	 */
	public void selectPaginatedDocAndPerformPagination() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitTime(3);
			getTotalPageCountSelectedDoc().isElementAvailable(15);
			base.waitForElement(getTotalPageCountSelectedDoc());
			base.waitTime(2);
			List<WebElement> elemts = driver.getWebDriver()
					.findElements(By.xpath("//tr//td[contains(text(),'MS Excel Worksheet')]"));

			for (int count = 0; count < elemts.size(); count++) {
				elemts.get(count).click();
				driver.waitForPageToBeReady();
				getTotalPageCountSelectedDoc().isElementAvailable(15);
				Thread.sleep(Input.wait30 / 15);
				base.waitForElement(getTotalPageCountSelectedDoc());
				String[] pages = getTotalPageCountSelectedDoc().getText().split("of", 2);
				String[] pagesNum = pages[1].split("pages", 2);
				if (Integer.parseInt(pagesNum[0].trim()) > 1) {
					base.passedStep("Selected document is paginated in Doc View");
					List<WebElement> checkBoxs = driver.getWebDriver().findElements(
							By.xpath("//tr//td[contains(text(),'MS Excel Worksheet')]/preceding-sibling::td//i"));
					checkBoxs.get(count).click();
					break;
				}
			}
			getNextButtonForDoc().isElementAvailable(15);
			base.waitForElement(getNextButtonForDoc());
			getNextButtonForDoc().Click();

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while selecting paginated document and perform pagination" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Modified By - Gopinath Modified Date - 26-08-2021
	 * @Description Method to create new folder and adding selected folder to it.
	 */
	public void createNewFolderAndAddSelectedDocument(String folderName) {
		try {
			UserManagement userManage=new UserManagement(driver);
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getActionButton().isElementAvailable(15);
			for (int i = 0; i < 15; i++) {
				try {
					getActionButton().Click();
					break;
				} catch (Exception e) {
					base.waitTillElemetToBeClickable(getActionButton());
					base.waitForElement(getActionButton());
				}
			}
			Actions Act = new Actions(driver.getWebDriver());
			
			getFolderOption2().isElementAvailable(15);
			base.waitForElement(getFolderOption2());
			getFolderOption2().waitAndClick(15);
			getNewFolderLink().isElementAvailable(15);
			base.waitForElement(getNewFolderLink());
			getNewFolderLink().Displayed();
			getNewFolderLink().Click();
			Act.clickAndHold(userManage.getBulkFolderPopupWindowHeader().getWebElement());
			Act.moveToElement(userManage.getBulkFolderPopupWindowHeader().getWebElement(), -10, 10);
			Act.release().build().perform();
			getSelectAllFoldersOption().isElementAvailable(15);
			base.waitForElement(getSelectAllFoldersOption());
			getSelectAllFoldersOption().Displayed();
			getSelectAllFoldersOption().Click();
			getNameTextField().isElementAvailable(15);
			base.waitForElement(getNameTextField());
			getNameTextField().SendKeys(folderName);
			getContinueButton().isElementAvailable(15);
			getContinueButton().Displayed();
			getContinueButton().Click();
			enableActionRequestToogles();
			getFinalizeAssignmentButton().isElementAvailable(15);
			getFinalizeAssignmentButton().waitAndClick(15);
			base.waitForElement(base.getSuccessMsg());
			base.getSuccessMsg().isElementAvailable(10);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
				base.passedStep("Added selected paginated document to folder successfully");
			}
			driver.getWebDriver().navigate().refresh();
			driver.scrollPageToTop();
			base.waitTime(5);
			selectPaginatedDocAndPerformPagination();
			driver.waitForPageToBeReady();
			Thread.sleep(3);
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getFolderButton().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getFolderButton().ScrollTo();
			getFolderButton().isElementAvailable(15);
			base.waitTime(2);
			try {
				getFolderButton().getWebElement().isDisplayed();
			} catch (Exception e) {
				driver.scrollPageToTop();
			}

			driver.scrollingToElementofAPage(getFolderButton());
			base.waitForElement(getFolderButton());
			if (getFolderButton().getWebElement().isDisplayed()) {
				base.passedStep("Folder tab is displayed successfully");
			}
			Assert.assertEquals("Folder tab is not displayed", true, getFolderButton().getWebElement().isDisplayed());
			getFolderButton().isElementAvailable(15);
			for (int i = 0; i < 15; i++) {
				try {
					getFolderButton().Click();
					break;
				} catch (Exception e) {
					base.waitTillElemetToBeClickable(getActionButton());
					base.waitForElement(getActionButton());
				}
			}
			verifyAddedFolderNameIsDisplayed(folderName);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while creating new folder and adding selected folder to it" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description Method to close history popup on DocView.
	 */
	public void closeHistoryPopup() {
		try {
			driver.waitForPageToBeReady();
			getCloseButton().isElementAvailable(15);
			base.waitForElement(getCloseButton());
			Assert.assertEquals("Close button is not displayed", true, getCloseButton().getWebElement().isDisplayed());
			getCloseButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while closing history popup on DocView" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description Method for verifying added folder displayed on folder Tree on
	 *              Doc view page.
	 * @param folderName -- (folderName is a string value that name of folder that
	 *                   added).
	 */
	public void verifyAddedFolderDisplayedOnFolderTree(String folderName) {
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.scrollingToBottomofAPage();
			Point p = getFolderButton().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 200) + ");");
			getFolderButton().isElementAvailable(15);
			base.waitForElement(getFolderButton());
			getFolderButton().Click();
			verifyAddedFolderNameIsDisplayed(folderName);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while closing history popup on DocView" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to Verify metadata Pop Is Paginated
	 */
	public void verifyMetaDataPopIsPaginated() {
		try {
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getMetaDataTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			base.waitForElement(getMetaDataTab());
			getMetaDataTab().isElementAvailable(15);
			Thread.sleep(Input.wait30 / 10);
			getMetaDataTab().waitAndClick(15);
			getBrowseAllMetaDataButton().isElementAvailable(15);
			base.waitForElement(getBrowseAllMetaDataButton());
			base.waitTillElemetToBeClickable(getBrowseAllMetaDataButton());
			getBrowseAllMetaDataButton().Click();
			getMetaDataTableOnPopup().isElementAvailable(15);
			getMetaDataTableOnPopup();
			getMetaDataListPaginated().isElementAvailable(15);
			if (getMetaDataListPaginated().getWebElement().isDisplayed()) {
				getThirdPageMetaDataList().isElementAvailable(15);
				base.waitForElement(getThirdPageMetaDataList());
				getThirdPageMetaDataList().waitAndClick(15);
				base.passedStep("Pagination is displayed in metadata pop up successfully");

			}
			getMetaDataListPaginated().isElementAvailable(15);
			Assert.assertEquals("Pagination is not loaded/displayed in metadata pop up", true,
					getMetaDataListPaginated().getWebElement().isDisplayed());
			getCloseButton().isElementAvailable(15);
			getCloseButton().Displayed();
			getCloseButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while meta data list paginated popup on DocView" + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method to Verify browser all history button is displayed
	 */
	public void verifyBrowseAllHistoryButtonIsDisplayed() {
		try {
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getHistoryTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getHistoryTab().ScrollTo();
			getHistoryTab().isElementAvailable(15);
			for (int i = 0; i < 15; i++) {
				try {
					getHistoryTab().Click();
					getBrowseAllHistoryButton().Displayed();
					base.passedStep("Browse all history button is displayed");
					break;
				} catch (Exception e) {
					base.waitForElement(getBrowseAllHistoryButton());
					base.waitTillElemetToBeClickable(getBrowseAllHistoryButton());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while browse all history button is displayed on DocView" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to selecting security group.
	 * @param securityGroup - (securityGroup is String value that name of security
	 *                      group)
	 */
	public void selectSecurityGroup(String securityGroup) {
		try {
			getSecurityGroupDropDownButton().isElementAvailable(15);
			getSecurityGroup(securityGroup).isElementAvailable(15);
			selectValueFromDropDown(getSecurityGroupDropDownButton(), getSecurityGroup(securityGroup));
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting the security group" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to print document and verify weather document is
	 *              downloded.
	 */
	public void printDocumentAndVerifyDocumentDownloaded() {
		try {
			String status = null;
			getDocView_Print().isElementAvailable(15);
			base.waitForElement(getDocView_Print());
			getDocView_Print().waitAndClick(15);
			base.getSuccessMsg().waitAndClick(15);
			base.waitForElement(base.getSuccessMsg());
			base.getSuccessMsg().isElementAvailable(10);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}

			driver.getWebDriver().get(Input.url + "Background/BackgroundTask");

			for (int i = 0; i < 20; i++) {
				try {
					getbackgroundDownLoadLink().isElementAvailable(10);
					base.waitForElement(getbackgroundDownLoadLink());
					Thread.sleep(Input.wait30 / 10);
					status = getBatchPrintStatus().getText();
					if (getbackgroundDownLoadLink().Displayed() && getbackgroundDownLoadLink().Enabled()
							&& status.equalsIgnoreCase("COMPLETED"))
						Assert.assertEquals("COMPLETED", status);
					break;
				} catch (Exception e) {
					driver.Navigate().refresh();
					System.out.println("Refresh");
				}
			}
			getDownLoadLink().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDownLoadLink().Enabled();
				}
			}), Input.wait30);
			getDownLoadLink().waitAndClick(5);
			// download time
			Thread.sleep(10000);

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while printing documents and downloading documents" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify PDF Document opened in new tab.
	 */
	public void verifyPDFDocumentNewTabOpened() {
		try {
			String parent = driver.getWebDriver().getWindowHandle();
			String child = null;
			Set<String> windows = driver.getWebDriver().getWindowHandles();
			for (String window : windows) {
				driver.getWebDriver().switchTo().window(window);
				child = window;
			}
			base.passedStep("PDF document is opened in new tab Successfully");
			driver.close();
			driver.getWebDriver().switchTo().window(parent);

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying PDF document tab title" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify Metadata tab should be displayed with default
	 *              metadata fields.
	 */
	public void verifyMetadataTabDisplayedWithDefaultFields() {
		try {
			driver.scrollPageToTop();
			driver.scrollingToElementofAPage(getResponsivenessField());
			getResponsivenessField().ScrollTo();
			getResponsivenessField().isElementAvailable(15);
			for (int i = 0; i < 10; i++) {
				try {
					getResponsivenessField().Displayed();
					break;
				} catch (Exception e) {
					base.waitForElement(getResponsivenessField());
				}
			}
			getResponsivenessField().isElementAvailable(15);
			Assert.assertEquals("Responsiveness field is not displayed", true,
					getResponsivenessField().getWebElement().isDisplayed());
			if (getResponsivenessField().getWebElement().isDisplayed()) {
				base.passedStep("Responsiveness field is displayed successfully");
			}
			driver.scrollingToElementofAPage(getHotDocCheckBox());
			Assert.assertEquals("HotDoc check box is not displayed", true,
					getHotDocCheckBox().getWebElement().isDisplayed());
			if (getHotDocCheckBox().getWebElement().isDisplayed()) {
				base.passedStep("HotDoc check box is displayed successfully");
			}
			driver.scrollingToElementofAPage(getPrivilegedField());
			Assert.assertEquals("Privileged field is not displayed", true,
					getPrivilegedField().getWebElement().isDisplayed());
			if (getPrivilegedField().getWebElement().isDisplayed()) {
				base.passedStep("Privileged field is displayed successfully");
			}
			driver.scrollingToElementofAPage(getPrivilegeTypeField());
			Assert.assertEquals("Privileged type field is not displayed", true,
					getPrivilegeTypeField().getWebElement().isDisplayed());
			if (getPrivilegeTypeField().getWebElement().isDisplayed()) {
				base.passedStep("Privileged type field is displayed successfully");
			}
			driver.scrollingToElementofAPage(getConfidentialityField());
			Assert.assertEquals("Confidentiality field is not displayed", true,
					getConfidentialityField().getWebElement().isDisplayed());
			if (getConfidentialityField().getWebElement().isDisplayed()) {
				base.passedStep("Confidentiality field is displayed successfully");
			}
			driver.scrollingToElementofAPage(getCommentTextField());
			Assert.assertEquals("Comment text field is not displayed", true,
					getCommentTextField().getWebElement().isDisplayed());
			if (getCommentTextField().getWebElement().isDisplayed()) {
				base.passedStep("Comment text field is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying Metadata tab should be displayed with default metadata fields"
							+ e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to edit coding form on Doc view.
	 * @param comment : (comment is String value that need to enter in comment text
	 *                field).
	 */
	public void editCodingFormOnDocView(String comment) {
		try {
			driver.scrollPageToTop();
			base.waitForElement(getTechnicalIssueRadioButton());
			getTechnicalIssueRadioButton().isElementAvailable(15);
			getTechnicalIssueRadioButton().javascriptclick(getTechnicalIssueRadioButton());
			getTechnicalIssueRadioButton().isElementAvailable(15);
			if (getTechnicalIssueRadioButton().Selected()) {
				driver.scrollingToElementofAPage(getForeignLanguageCheckBox());
				base.waitTillElemetToBeClickable(getForeignLanguageCheckBox());
				getForeignLanguageCheckBox().Click();
			}
			getHotDocCheckBox().isElementAvailable(15);
			driver.scrollingToElementofAPage(getHotDocCheckBox());
			base.waitTillElemetToBeClickable(getHotDocCheckBox());
			getHotDocCheckBox().Click();
			driver.scrollingToElementofAPage(getPrivilegedRadioButton());
			base.waitTillElemetToBeClickable(getPrivilegedRadioButton());
			getPrivilegedRadioButton().Click();

			if (getTechnicalIssueRadioButton().Selected()) {
				driver.scrollingToElementofAPage(getAttorneyClientCheckBox());
				base.waitTillElemetToBeClickable(getAttorneyClientCheckBox());
				getAttorneyClientCheckBox().Click();
			}
			driver.scrollingToElementofAPage(getConfidentialRadioButton());
			base.waitTillElemetToBeClickable(getConfidentialRadioButton());
			getConfidentialRadioButton().Click();
			driver.scrollingToElementofAPage(getCommentTextField());
			base.waitTillElemetToBeClickable(getCommentTextField());
			getCommentTextField().SendKeys(comment);

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while editing coding form on Doc view" + e.getMessage());

		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for closing meta data pop up..
	 * 
	 */
	public void closeMetaDataPopup() {
		try {
			driver.waitForPageToBeReady();
			getMetaDataTableOnPopup().isElementAvailable(15);
			base.waitForElement(getMetaDataTableOnPopup());
			getCloseButton().waitAndClick(15);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while closing metadata popup" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for save edited coding form..
	 * 
	 */
	public void saveEditedCodingForm() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getSaveLink().isElementAvailable(15);
			base.waitForElement(getSaveLink());
			getSaveLink().Click();
			base.CloseSuccessMsgpopup();
			// base.VerifySuccessMessage("Applied coding saved successfully");
			driver.Navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while saving edited coding form" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method to open doc view meta data pop up ..
	 */
	public void openDocViewMetaDataPopup() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getHistoryTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getMetaDataTab().ScrollTo();
			getMetaDataTab().isElementAvailable(15);
			base.waitForElement(getMetaDataTab());
			Assert.assertEquals("Meta data tab is not displayed", true, getMetaDataTab().getWebElement().isDisplayed());
			getMetaDataTab().Click();
			getBrowseAllMetaDataButton().isElementAvailable(15);
			base.waitForElement(getBrowseAllMetaDataButton());
			Assert.assertEquals("Browse all meta data button is not displayed", true,
					getBrowseAllMetaDataButton().getWebElement().isDisplayed());
			getBrowseAllMetaDataButton().Click();

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while opening doc view meta data pop up" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method to save complete edited coding form and verify user
	 *               navigated to present document to next document..
	 * 
	 */
	public void saveCompleteEditedCodingFormAndVerifyNavigatedToNextDoc() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitForElement(getSaveAndNextLink());
			getSaveAndNextLink().isElementAvailable(15);
			getPresentDocumentSelectedID().isElementAvailable(15);
			String presentDocID1 = getPresentDocumentSelectedID().getText().trim();
			String nextDocIDToPresentDoc = getNextDocumentIdToSelectedDoc().getText().trim();
			getSaveAndNextLink().Click();
			if (base.getSuccessMsgHeader().getText().toString().contains("Success")) {
				base.VerifySuccessMessage("Applied coding saved successfully");
				driver.waitForPageToBeReady();
				driver.scrollPageToTop();
				getPresentDocumentSelectedID().isElementAvailable(15);
				String presentDocID2 = getPresentDocumentSelectedID().getText().trim();
				if (nextDocIDToPresentDoc.contentEquals(presentDocID2)
						&& !(presentDocID1.contentEquals(presentDocID2))) {
					base.passedStep("Navigated to next document of Id :: " + presentDocID2
							+ " from present document of ID :: " + presentDocID1 + " successfully");
				} else {
					base.failedStep("Navigating to next document of Id :: " + presentDocID2
							+ " from present document of ID :: " + presentDocID1 + " is failed");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while save complete edited coding form and verify user navigated to present document to next document"
							+ e.getMessage());
		}
	}

	public String verifyBrowseAllHistory() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getHistoryTag().Visible();
			}
		}), Input.wait30);
		getHistoryTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBrowseAllHistoryBtn().Visible();
			}
		}), Input.wait30);
		getBrowseAllHistoryBtn().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTimeStampInAllActivities().Visible();
			}
		}), Input.wait30);
		getTimeStampInAllActivities().waitAndClick(10);

		Thread.sleep(3000);
		getTimeStampInAllActivities().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFirstRowOfActionColumn().Visible();
			}
		}), Input.wait30);
		Thread.sleep(3000);
		String firstRowValue = getFirstRowOfActionColumn().getText();
		getCloseButtonInAllActivities().waitAndClick(10);

		return firstRowValue;
	}

	/**
	 * @author Jayanthi.Ganesan modified by :Jeevitha modified on: 23/11/2021
	 * @throws InterruptedException
	 */
	public void verifyBrowseAllHistory_RedactionAddedStatus(boolean redaction) throws InterruptedException {
		driver.scrollingToElementofAPage(getHistoryTag());
		base.waitForElement(getHistoryTag());
		getHistoryTag().waitAndClick(10);
		base.waitForElement(getBrowseAllHistoryBtn());
		getBrowseAllHistoryBtn().waitAndClick(10);
		base.waitForElement(getTimeStampInAllActivities());
		getTimeStampInAllActivities().waitAndClick(20);
		getTimeStampInAllActivities().waitAndClick(30);
		base.waitForElement(getFirstRowOfActionColumn());
		driver.waitForPageToBeReady();
		boolean flag = false;
		for (int i = 1; i <= 10; i++) {
			String firstRowValue = getRowsOfActionColumn(i).getText().trim();
			System.out.println(firstRowValue);
			System.out.println(i);
			if (redaction) {
				if (firstRowValue.contentEquals("RedactionTagged")) {
					flag = true;
					base.passedStep(firstRowValue + " is Displayed in history under action column as expected ");
					break;
				} else {
					flag = false;
					continue;
				}
			} else {
				if (firstRowValue.contentEquals("RedactionUnTagged")) {
					flag = true;
					base.passedStep(firstRowValue + " is Displayed in history under action column as expected ");
					break;
				} else {
					flag = false;
					continue;
				}
			}
		}
		if (flag == false) {
			base.failedStep("Redaction status is not Displayed in history under action column as expected");
		}
		getCloseButtonInAllActivities().waitAndClick(10);
	}

	/**
	 * @author Gopinath
	 * @Description : Method to print document and verify weather document is
	 *              downloded.
	 */
	public void verifyPrintOnDocView() {
		try {
			String status = null;
			driver.waitForPageToBeReady();
			base.waitTime(3);
			getDocView_Print().isElementAvailable(15);
			base.waitForElement(getDocView_Print());
			base.waitTillElemetToBeClickable(getDocView_Print());
			getDocView_Print().Click();
			base.waitForElement(base.getSuccessMsg());
			base.getSuccessMsg().isElementAvailable(10);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}

			driver.getWebDriver().get(Input.url + "Background/BackgroundTask");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while printing documents and downloading documents" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath srinivasan
	 * @Description : Method for verifying the new gear windows opened on doc view.
	 * @throws InterruptedException
	 */
	public void verifyingTheNewWindowsInDocView() {
		try {
			driver.waitForPageToBeReady();
			getGearToggle().isElementAvailable(15);
			base.waitForElement(getGearToggle());
			getGearToggle().waitAndClick(15);
			getDocumentListMaximizeBtn().isElementAvailable(15);
			base.waitForElement(getDocumentListMaximizeBtn());
			getDocumentListMaximizeBtn().Click();
			driver.waitForPageToBeReady();
			Set<String> handle = driver.WindowHandles();
			driver.waitForPageToBeReady();
			Iterator<String> it = handle.iterator();
			String parentwindow = it.next();
			String childwindow = it.next();
			driver.switchTo().window(childwindow);
			driver.waitForPageToBeReady();
			getReviewMetaData().isElementAvailable(15);
			String Title = getReviewMetaData().getText();
			System.out.println(Title);
			softAssert.assertEquals("REVIEW MODE", Title);

			System.out.println("child window popup title:" + driver.getTitle());

			driver.close();
			driver.getWebDriver().switchTo().window(parentwindow);
			getCodingMaximizeBtn().isElementAvailable(15);
			base.waitForElement(getCodingMaximizeBtn());
			getCodingMaximizeBtn().Click();

			driver.waitForPageToBeReady();
			Set<String> handle1 = driver.WindowHandles();
			driver.waitForPageToBeReady();
			Iterator<String> iterating = handle1.iterator();
			String parentWindow2 = iterating.next();
			String childwindow2 = iterating.next();
			driver.switchTo().window(childwindow2);
			getCodingNewWindow().isElementAvailable(15);
			String title = getCodingNewWindow().getText();
			System.out.println(title);
			softAssert.assertEquals("Default Project Coding Form", title);
			driver.waitForPageToBeReady();
			System.out.println("child window popup title:" + driver.getTitle());

			driver.close();
			driver.getWebDriver().switchTo().window(parentWindow2);

			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			getDocumentMetaDataMaximizeBtn().ScrollTo();
			getDocumentMetaDataMaximizeBtn().isElementAvailable(15);
			base.waitForElement(getDocumentMetaDataMaximizeBtn());
			getDocumentMetaDataMaximizeBtn().Click();
			// String parentWindow3 = driver.getWebDriver().getWindowHandle();

			driver.waitForPageToBeReady();
			Set<String> Windowhandling = driver.WindowHandles();
			driver.waitForPageToBeReady();
			Iterator<String> iterate = Windowhandling.iterator();
			String parentWindow3 = iterate.next();
			String childwindow3 = iterate.next();
			driver.switchTo().window(childwindow3);
			driver.waitForPageToBeReady();
			getDocumentDataNewWindowTitle().isElementAvailable(15);
			String newwindow = getDocumentDataNewWindowTitle().getText();
			softAssert.assertEquals("metadata", newwindow);
			System.out.println("child window popup title:" + driver.getTitle());
			driver.close();
			driver.getWebDriver().switchTo().window(parentWindow3);
			getSaveBtn().isElementAvailable(15);
			base.waitForElement(getSaveBtn());
			getSaveBtn().Click();
			driver.waitForPageToBeReady();

			driver.Navigate().refresh();
			if (getCodingNewWindow().Displayed()) {
				base.passedStep("The Parent window is displayed");
			} else {
				base.failedStep("the parent window is not displayed as expected");
			}

			if (getGearToggle().Displayed()) {
				base.passedStep("Gear toggle in the Parent window is displayed");
			} else {
				base.failedStep("Gear Toggle in the parent window is not displayed as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method to save complete edited coding form and verify user
	 *               navigated to present document to next document..
	 * 
	 */
	public void editedCodingFormSavedAndVerifyNavigatedToNextDoc() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getSaveAndNextLink().isElementAvailable(15);
			base.waitForElement(getSaveAndNextLink());
			getPresentDocumentSelectedID().isElementAvailable(15);
			String presentDocID1 = getPresentDocumentSelectedID().getText().trim();
			getNextDocumentIdToSelectedDoc().isElementAvailable(15);
			String nextDocIDToPresentDoc = getNextDocumentIdToSelectedDoc().getText().trim();
			getSaveAndNextLink().isElementAvailable(15);
			getSaveAndNextLink().Click();
			base.VerifySuccessMessage("Document saved successfully");
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getPresentDocumentSelectedID().isElementAvailable(15);
			String presentDocID2 = getPresentDocumentSelectedID().getText().trim();
			if (nextDocIDToPresentDoc.contentEquals(presentDocID2) && !(presentDocID1.contentEquals(presentDocID2))) {
				base.passedStep("Navigated to next document of Id :: " + presentDocID2
						+ " from present document of ID :: " + presentDocID1 + " successfully");
			} else {
				base.failedStep("Navigating to next document of Id :: " + presentDocID2
						+ " from present document of ID :: " + presentDocID1 + " is failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while save complete edited coding form and verify user navigated to present document to next document"
							+ e.getMessage());
		}
	}

	/**
	 * @author Gopianth
	 * @Description : This method used for open duplicate tab of already opened tab.
	 * 
	 */
	public void openDuplicateTabOfAlreadyOpenedTab() {
		try {
			Robot robot = new Robot();
			base.waitTime(3);
			robot.keyPress(KeyEvent.VK_ALT);
			driver.waitForPageToBeReady();
			robot.keyPress(KeyEvent.VK_D);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_D);
			robot.keyPress(KeyEvent.VK_ALT);
			driver.waitForPageToBeReady();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ENTER);

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while opening duplicate tab of already opened tab." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath.
	 * @Descrption : Method For clicking remark button on doc view.
	 */
	public void clickOnRemarkButton() {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNonAudioRemarkBtn().isElementAvailable(10);
				}
			}), Input.wait60);
			getNonAudioRemarkBtn().waitAndClick(9);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while clicking remark button on doc view." + e.getMessage());
		}

	}

	/**
	 * @author Gopinath.
	 * @Descrption : Method For verifying another user applied remark message is
	 *             displayed or not.
	 * @param expectedMessage : expectedMessage is string value that expected
	 *                        message need get by performing remarks or redactions.
	 */
	public void verifyAnotherUserAppliedRemarkMsgDisplayed(String expectedMessage) {
		try {
			driver.waitForPageToBeReady();
			getAnotherRemarkMessage().isElementAvailable(15);
			base.waitForElement(getAnotherRemarkMessage());
			String actualMessage = getAnotherRemarkMessage().getText().trim();
			if (actualMessage.toLowerCase().contains(expectedMessage.toLowerCase())) {
				base.passedStep(actualMessage + " -- message successfully displayed");
			} else {
				base.failedStep(actualMessage + " -- messgae is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while verifying another user applied remark message is displayed or not."
					+ e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method to select Doc from family member tab And Create Folder
	 * @param folderName - (folderName is string value that name of folder.)
	 * @param docId      - (docId is string value that document id having family
	 *                   members).
	 */
	public void selectDocFromFamilyMembersAndCreateFolder(String docId, String folderName) {
		try {
			boolean flag = false;
			driver.scrollPageToTop();
			getRowFromMiniDoclist(docId).ScrollTo();
			getRowFromMiniDoclist(docId).isElementAvailable(15);
			driver.waitForPageToBeReady();
			getRowFromMiniDoclist(docId).waitAndClick(8);
			driver.scrollPageToTop();
			// needed sleep due to scroll to element
			base.waitTime(3);
			getFamilyMemberTab().ScrollTo();
			getFamilyMemberTab().isElementAvailable(10);
			getFamilyMemberTab().Click();
			try {
				driver.waitForPageToBeReady();
				base.waitTime(3);
				getFirstRowCheckBoxFamilyMem().ScrollTo();
				driver.scrollingToElementofAPage(getFirstRowCheckBoxFamilyMem());
				getFirstRowCheckBoxFamilyMem().ScrollTo();
				getFirstRowCheckBoxFamilyMem().isElementAvailable(10);
				getFirstRowCheckBoxFamilyMem().Click();
				flag = true;
			} catch (Exception e) {
				base.stepInfo("Documents are not present in family members table");
			}
			if (flag == true) {
				base.waitTime(2);
				getAnalyticActionButton().ScrollTo();
				base.waitForElement(getAnalyticActionButton());
				getAnalyticActionButton().isElementAvailable(10);
				getAnalyticActionButton().Click();
				getFamilyMemFolderButton().ScrollTo();
				getFamilyMemFolderButton().isElementAvailable(10);
				getFamilyMemFolderButton().Click();
				getNewFolderLink().isElementAvailable(15);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getNewFolderLink().Displayed();
					}
				}), Input.wait90);
				getNewFolderLink().Displayed();
				getNewFolderLink().Click();
				getSelectAllFoldersOption().isElementAvailable(15);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSelectAllFoldersOption().Displayed();
					}
				}), Input.wait90);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSelectAllFoldersOption().Enabled();
					}
				}), Input.wait90);
				getSelectAllFoldersOption().isElementAvailable(15);
				getSelectAllFoldersOption().Displayed();
				getSelectAllFoldersOption().Click();
				getNameTextField().isElementAvailable(15);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getNameTextField().Enabled();
					}
				}), Input.wait90);
				getNameTextField().SendKeys(folderName);
				getContinueButton().isElementAvailable(15);
				base.waitTillElemetToBeClickable(getContinueButton());
				getContinueButton().Displayed();
				getContinueButton().Click();

				enableActionRequestToogles();
				getFinalizeAssignmentButton().Click();
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return base.getSuccessMsg().Displayed();
					}
				}), Input.wait90);
				base.getSuccessMsg().isElementAvailable(10);
				Assert.assertEquals("Success message is not displayed", true,
						base.getSuccessMsg().getWebElement().isDisplayed());
				if (base.getSuccessMsg().getWebElement().isDisplayed()) {
					UtilityLog.info("Success message is displayed successfully");
					Reporter.log("Success message is displayed successfully");
					base.stepInfo("Folder created successfuly from family members tab");
				}
			}

		} catch (Exception e) {
			base.failedStep("Exception occured while select Doc from family member tab And Create Folder");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for perform annotation by rectangle.
	 * @param off1 : off1 is integer value that x-ordinate location.
	 * @param off2 : off2 is integer value that y-ordinate location.
	 */
	public void performAnnotationByRectangle(int off1, int off2) {
		try {
			driver.scrollPageToTop();
			base.waitForElement(getRedactIcon());
			getRedactIcon().isElementAvailable(15);
			getRedactIcon().waitAndClick(15);
			HighliteIcon().isElementAvailable(15);
			HighliteIcon().waitAndClick(15);
			getRectangleButton().isElementAvailable(15);
			getRectangleButton().Click();
			System.out.println(off1 + "...." + off2);
			Actions actions = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			WebElement text = currentDocument().getWebElement();
			actions.moveToElement(text, off1, off2).clickAndHold().moveByOffset(200, 200).release().perform();
			base.passedStep("Performed annotation by rectangle");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to select redacted area");
		}
	}

	/**
	 * @author Gopinath Method for move annotation.
	 */
	public void moveAnnotationByRectangle() {
		try {
			Actions actions = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			List<WebElement> text = getAppliedAnnotation().FindWebElements();
			actions.moveToElement(text.get(0)).clickAndHold().moveByOffset(200, 220).release().perform();
			base.passedStep("Moved Annotation successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to select redacted area");
		}
	}

	/**
	 * @author Gopinath Method for verifying folder tab is displayed with
	 *         expand/collapse hirercy.
	 */
	public void verifyFoldertabDisplayedWithExpandCollapse() {
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.scrollingToBottomofAPage();
			Point p = getFolderButton().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getFolderButton().isElementAvailable(15);
			getFolderButton().Click();
			if (getFolderButton().Displayed()) {
				base.passedStep("Folder tab is displayed successfully");
			}
			getExpandFolderTree().isElementAvailable(15);
			if (getExpandFolderTree().Displayed()) {
				base.passedStep("Expand and collapse folder hirearcy is displayed successfully");
			}
			getExpandFolderTree().Click();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to select redacted area");
		}
	}

	/**
	 * @author Gopinath Modified By - Gopinath Modified Date - NA
	 * @Description Method to select documents from mini doc list in doc view.
	 * @param numberOfDocuments : numberOfDocuments is integer value that count that
	 *                          number of documents.
	 */
	public void selectDocumentsFromMiniDocListFromDocView(int numberOfDocuments) {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getTotalPageCountSelectedDoc().isElementAvailable(15);
			List<WebElement> elemts = getDocumentsToSelect().FindWebElements();

			for (int count = 0; count < numberOfDocuments; count++) {
				try {
					driver.waitForPageToBeReady();
					elemts.get(count).click();
				} catch (Exception e) {
					driver.waitForPageToBeReady();
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while selecting documents from mini doc list in doc view." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify Inactive Project Field Not Displayed In
	 *              MetadataTab.
	 * @param projectFiledName : projectFiledName is string value that name of
	 *                         project field.
	 */
	public void verifyInactiveProjectFieldNotDisplayedInMetadataTab(String projectFiledName) {
		try {

			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			try {
				boolean flag = getProjectFieldInMetaData(projectFiledName).isDisplayed();
				if (flag)
					base.failedStep("Inactive Project Field Displayed In MetadataTab");
				else
					base.passedStep("Inactive Project Field is not displayed dn MetadataTab successfully");
			} catch (Exception e) {
				base.passedStep("Inactive Project Field is not displayed dn MetadataTab successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while selecting documents from mini doc list in doc view." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify folder tab does not have any folders except
	 *              default all folders.
	 */
	public void verifyFolderTabNotContainsFolders() {
		try {

			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.scrollingToBottomofAPage();
			Point p = getFolderButton().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 200) + ");");
			getFolderButton().isElementAvailable(10);
			getFolderButton().Click();
			driver.waitForPageToBeReady();
			getAllFolders().isElementAvailable(10);
			if (getAllFolders().Displayed()) {
				base.passedStep("By default 'AllFolders' displayed successfuly.");
			} else {
				base.failedStep("By default 'AllFolders' displayed successfuly.");
			}

			try {
				boolean flag = getExpandFolderTree().isDisplayed();
				if (flag)
					base.stepInfo("Folders are present in folder tab in doc view");
				else
					base.passedStep("Folders are not present in folder tab in doc view");
			} catch (Exception e) {
				base.passedStep("Folders are not present in folder tab in doc view");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verify folder tab does not have any folders except default all folders."
							+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify folder tab contains folders and expand
	 *              collapse button is displayed.
	 */
	public void verifyFolderTabContainsFolders() {
		try {

			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.scrollingToBottomofAPage();
			Point p = getFolderButton().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 200) + ");");
			getFolderButton().isElementAvailable(10);
			getFolderButton().Click();
			driver.waitForPageToBeReady();
			getAllFolders().isElementAvailable(10);
			if (getAllFolders().Displayed()) {
				base.passedStep("By default 'AllFolders' displayed successfuly.");
			} else {
				base.failedStep("By default 'AllFolders' displayed successfuly.");
			}
			getExpandFolderTree().isElementAvailable(10);
			boolean flag = getExpandFolderTree().isDisplayed();
			if (flag)
				base.passedStep("Folders and expand collapse button are present in folder tab in doc view");
			else
				base.passedStep("Folders and expand collapse button are present in folder tab in doc view");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verify folder tab does not have any folders except default all folders."
							+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify meta data columns are displayed.
	 */
	public void verifyMetaDataColumns() {
		try {

			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.scrollingToBottomofAPage();
			Point p = getMetaDataTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 200) + ");");
			getMetaDataTab().isElementAvailable(10);
			getAllFieldNameColumn().isElementAvailable(10);
			if (getAllFieldNameColumn().Displayed()) {
				base.passedStep("Field name column is displayed");
			} else {
				base.failedStep("Field name column is not displayed");
			}
			if (getAllFieldValueColumn().Displayed()) {
				base.passedStep("Field value column is displayed");
			} else {
				base.failedStep("Field value column is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verify folder tab does not have any folders except default all folders."
							+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to print document and verify weather document is
	 *              downloded.
	 */
	public void verifyDocumenetPrintOnDocView() {
		try {
			base.waitForElement(getDocView_Print());
			base.waitTillElemetToBeClickable(getDocView_Print());
			getDocView_Print().Click();
			base.waitForElement(base.getSuccessMsg());
			base.getSuccessMsg().isElementAvailable(10);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}

			driver.getWebDriver().get(Input.url + "Background/BackgroundTask");
			if (getDownloadOption().getText().trim().equalsIgnoreCase("Document Print")) {
				base.passedStep("Downloading document started succuessfully");
			} else {
				base.failedStep("Downloading document is failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while printing documents and downloading documents" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify download button is not displayed.
	 */
	public void verifyingDownloadButtonIsNotDisplayed() {
		try {
			driver.waitForPageToBeReady();
			getDownloadButton().isElementAvailable(5);
			if (getDownloadButton().Enabled()) {
				base.passedStep("Download button is not displayed successfully");
			} else {
				base.failedStep("Download button is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying download button is not displayed." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify remark button is not displayed.
	 */
	public void verifyingRemarkButtonIsNotDisplayed() {
		try {
			driver.waitForPageToBeReady();
			getNonAudioRemarkBtn().isElementAvailable(2);
			if (getNonAudioRemarkBtn().Enabled()) {
				base.passedStep("Remark button is not displayed successfully");
			} else {
				base.failedStep("Remark button is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying remark button is not displayed." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify remark button is displayed.
	 */
	public void verifyingRemarkButtonIsDisplayed() {
		try {
			driver.waitForPageToBeReady();
			getNonAudioRemarkBtn().isElementAvailable(15);
			if (getNonAudioRemarkBtn().isDisplayed()) {
				base.passedStep("Remark button is displayed successfully");
			} else {
				base.failedStep("Remark button is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying remark button is displayed." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath Method for perform remark.
	 * @param off1   : off1 is integer value that x-ordinate location.
	 * @param off2   : off2 is integer value that y-ordinate location.
	 * @param remark : remark is String value that name given to remark.
	 */
	public void performRemarkWithSaveOperation(int off1, int off2, String remark) {
		try {
			driver.scrollPageToTop();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNonAudioRemarkBtn().isElementAvailable(10);
				}
			}), Input.wait60);
			getNonAudioRemarkBtn().waitAndClick(9);
			System.out.println(off1 + "...." + off2);
			Actions actions = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			WebElement text = currentDocument().getWebElement();
			actions.moveToElement(text, off1, off2).clickAndHold().moveByOffset(200, 200).release().perform();
			base.passedStep("Performed remark by rectangle");
			driver.scrollPageToTop();
			getAddRemarkbtn().getWebElement().click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getRemarkTextArea().isElementAvailable(10);
				}
			}), Input.wait30);
			getRemarkTextArea().SendKeys(remark);
			getSaveRemark().Click();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to select redacted area");
		}
	}

	/**
	 * @author Gopinath Method for perform remark.
	 * @param off1   : off1 is integer value that x-ordinate location.
	 * @param off2   : off2 is integer value that y-ordinate location.
	 * @param remark : remark is String value that name given to remark.
	 */
	public void performRemarkWithoutSaveOperation(int off1, int off2, String remark) {
		try {
			driver.scrollPageToTop();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNonAudioRemarkBtn().isElementAvailable(10);
				}
			}), Input.wait60);
			getNonAudioRemarkBtn().waitAndClick(9);
			System.out.println(off1 + "...." + off2);
			Actions actions = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			WebElement text = currentDocument().getWebElement();
			actions.moveToElement(text, off1, off2).clickAndHold().moveByOffset(200, 200).release().perform();
			base.passedStep("Performed remark by rectangle");
			driver.scrollPageToTop();
			getAddRemarkbtn().getWebElement().click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getRemarkTextArea().isElementAvailable(10);
				}
			}), Input.wait30);
			getRemarkTextArea().SendKeys(remark);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to select redacted area");
		}
	}

	/**
	 * @author Gopinath Method for perform remark.
	 * @description : Method for verifying highlighted text for already added remark
	 *              is deleted from document on doc view.
	 */
	public void verifyHighlightedTextRemarkNotPresentOnDoc() {
		try {
			List<WebElement> addedRemarks = null;
			driver.scrollPageToTop();
			try {
				addedRemarks = getAddedRemark().FindWebElements();
				if (addedRemarks.size() == 0) {
					base.passedStep(
							"Highlighted text for already added remark is deleted from document on doc view successfully");
				} else {
					base.failedStep(
							"Highlighted text for already added remark is not deleted from document on doc view");
				}
			} catch (Exception e) {
				base.passedStep(
						"Highlighted text for already added remark is deleted from document on doc view successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verifying highlighted text for already added remark is deleted from document on doc view. "
							+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @description: this method will verify metadata pop with field name and value
	 *               is displayed or not
	 */
	public void verifyMetadataPopUpColumns() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getMetaDataTableOnPopup());
			if (getMetaDataTableOnPopup().getWebElement().isDisplayed()) {
				base.passedStep("metadata popup is displayed");
			} else {
				base.failedStep("metadata popup is not displayed");
			}
			base.waitForElement(getMetaDataTableOnPopupFieldName());
			if (getMetaDataTableOnPopupFieldName().getWebElement().isDisplayed()
					&& getMetadataPopUpFieldnameList().size() > 0) {
				base.passedStep("metadata popup field name with list is  displayed");
				if (getMetaDataTableOnPopupFieldValue().getWebElement().isDisplayed()) {
					base.passedStep("poup is displayed with metadalist with field name and value");
				} else {
					base.failedStep("metadat poup is Not displayed with metadalist with field name and value");
				}
			} else {
				base.failedStep("metadata popup field name with list is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Gopinath
	 * @description: method to remove annotation in document
	 */
	public void unTagAnnotationOfDocument() {
		try {
			Actions actions = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			base.waitForElement(getYellowUnRedactRectButton());
			getYellowUnRedactRectButton().waitAndClick(5);
			List<WebElement> annotation = getDocViewAppliedAnnotation().FindWebElements();
			actions.moveToElement(annotation.get(0)).click().build().perform();
			base.waitForElement(getUnTaggedDeleteButton());
			actions.moveToElement(getUnTaggedDeleteButton().getWebElement()).click().build().perform();
			base.VerifySuccessMessageB("Annotation Removed successfully.");
			base.passedStep("Annotation removed from docuent");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("unable to Remove annotation from document due to " + e.getMessage());

		}
	}

	/**
	 * @author Raghuram.A
	 * @param remarkText
	 */
	public void historyActivityCheck(String remarkText) {
		verifyHistoryTabIsEnabled();
		getHistoryTab().waitAndClick(5);

		base.stepInfo("Verifying history tab is displayed in doc view page.");
		verifyHistoryTabIsDisplayedByDefault();

		base.stepInfo("Verifying export tab is displayed in browse history pop up in  doc view page.");
		verifyExportOptionIsDisplayed();
		driver.waitForPageToBeReady();

		getTimeStampHeader().waitAndClick(5);
		getTimeStampHeader().waitAndClick(5);
		getAHeaderList();
		int actionIndex = base.getIndex(getAHeaderList(), "ACTION");
		int timeIndex = base.getIndex(getAHeaderList(), "TIME STAMP");
		int userIndex = base.getIndex(getAHeaderList(), "USER");

		base.textCompareEquals("RemarksRemoved", getResult(remarkText, actionIndex).getText(),
				"Updated remark is displayed in document’s permanent activity history record", "History not updated");

		getCloseButton().waitAndClick(2);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Gopianth
	 * @Description : This method used for impersonate rmu to reviewer.
	 * @param securityGroup : securityGroup is String value that name of security
	 *                      group.
	 */
	public void impersonateRMUtoReviewer(String securityGroup) throws InterruptedException {
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.getChangeRole().Visible();
			}
		}), Input.wait90);
		base.getChangeRole().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.getSelectRole().Visible();
			}
		}), Input.wait60);
		base.getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
		base.waitTime(3);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.getAvlDomain().Visible();
			}
		}), Input.wait30);
		base.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		base.waitTime(3);
		base.getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		base.waitTime(3);
		base.getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		base.getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from RMU to Reviewer");
		UtilityLog.info("Impersnated from RMU to Reviewer");
	}

	/**
	 * @author Gopinath Modified by : Gopinath Modified date : NA
	 * @Description : Method for verifying Remark Actions To History .
	 */
	public void verifyRemarkActionsToHistory() {
		try {
			String remarkHistory = null;
			String remarkHistory2 = null;

			driver.scrollingToElementofAPage(getFieldNameTableColumnHeader());
			getFieldNameTableColumnHeader().ScrollTo();
			getFieldNameTableColumnHeader().isElementAvailable(15);
			base.waitForElement(getFieldNameTableColumnHeader());
			driver.scrollingToBottomofAPage();
			Actions actions = new Actions(driver.getWebDriver());
			driver.scrollingToElementofAPage(getHistoryTab());
			getHistoryTab().ScrollTo();
			getHistoryTab().isElementAvailable(15);
			actions.moveToElement(getHistoryTab().getWebElement()).click().perform();

			for (int count = 0; count < 2; count++) {
				base.waitTime(3);
				getTimeStamp().isElementAvailable(15);
				base.waitForElement(getTimeStamp());
				getTimeStamp().waitAndClick(15);
			}
			getActionByRowInHistory(2).isElementAvailable(15);
			actions.moveToElement(getActionByRowInHistory(2).getWebElement()).build().perform();
			base.waitTime(3);

			remarkHistory = getActionByRowInHistory(2).getText().toLowerCase().trim();
			getActionByRowInHistory(1).isElementAvailable(15);
			remarkHistory2 = getActionByRowInHistory(1).getText().toLowerCase().trim();

			if (remarkHistory.equalsIgnoreCase("remarksadded") || remarkHistory2.equalsIgnoreCase("remarksadded")) {
				base.passedStep("Remarked history displayed in history table successfully");
			} else {
				base.failedStep("Remarked history not displayed in history table");
			}

		} catch (Exception e) {
			base.failedStep("Hidden properties in history table is not blank" + e.getMessage());
			e.printStackTrace();
		}
		
	}

}

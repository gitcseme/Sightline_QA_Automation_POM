package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import ch.qos.logback.core.joran.action.Action;
import executionMaintenance.Log;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DocListPage {

	Driver driver;
	SessionSearch search;
	BaseClass base;
	DocViewMetaDataPage docViewMetaDataPage;

	public Element getDocList_info() {
		return driver.FindElementById("dtDocList_info");
	}

	public ElementCollection getDocListRows() {
		return driver.FindElementsById("//*[@id='dtDocList']/tbody/tr");
	}

	public Element getColumnText(int row, int col) {
		return driver.FindElementByXPath("//*[@id='dtDocList']/tbody/tr[" + row + "]/td[" + col + "]/a");
	}

	public ElementCollection getElements() {
		return driver.FindElementsByXPath("//*[@class='a-menu']");
	}

	// Filters
	public Element getCustodianFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'CustodianName')]");
	}

	public Element getMasterDateFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'MasterDate')]");
	}

	public Element getDocFileSizeFiler() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'DocFileSize')]");
	}

	public Element getGetDocFIleTypeFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'DocFileType')]");
	}

	public Element getEmailAuthNameFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorName')]");
	}

	public Element getEmailRecNameFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailRecipientNames')]");
	}

	public Element getEmailAuthDomainFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorDomain')]");
	}

	public Element getEmailAllDomainFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAllDomains')]");
	}

	// options
	public Element getMasterDateRange() {
		return driver.FindElementByXPath("(//select[@id='MasterDate-dtop'])[1]");
	}

	public Element getSetFromMasterDate() {
		return driver.FindElementByXPath("(//*[contains(@id,'MasterDate-1-DOC')])[1]");
	}

	public Element getSetToMasterDate() {
		return driver.FindElementByXPath("(//*[@id='MasterDate-2-DOCLIST'])[1]");
	}

	public Element getDocFileSizeOption() {
		return driver.FindElementByXPath("(//select[@id='DocFileSize-op'])[1]");
	}

	public Element getDocFileFromSize() {
		return driver.FindElementByXPath("(//*[@id='DocFileSize-1-DOCLIST'])[1]");
	}

	public Element getDocFileToSize() {
		return driver.FindElementByXPath("(//*[@id='DocFileSize-2-DOCLIST'])[1]");
	}

	// include exclude radio buttons
	public Element getIncludeRadioBtn() {
		return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[1])[1]");
	}

	public Element getExcludeRadioBtn() {
		return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[2])[1]");
	}

	// Serach text
	public Element getSearchTextArea() {
		return driver.FindElementByXPath("//*[@type='search']");
	}

	public Element getSelectFromList() {
		return driver.FindElementByXPath("//*[@type='search']");
	}

	public Element getAddToFilter() {
		return driver.FindElementByXPath("(//*[contains(text(),' Add to Filter')])[1]");
	}

	public Element getApplyFilter() {
		return driver.FindElementByXPath("//*[@id='btnAppyFilter']");
	}

	// Cancel filter
	public Element getCancelFilter1() {
		return driver.FindElementByXPath("//*[@id='Include']/i");
	}

	public Element getCancelFilter2() {
		return driver.FindElementByXPath("//*[@id='undefined']/i");
	}

	// Actions
	// public Element getSelectAll(){ return
	// driver.FindElementByXPath("//*[@id='dtDocList']/thead/tr/th[2]/label"); }
	public Element getSelectAll() {
		return driver.FindElementByXPath("//*[@id='selectAllRows']/following-sibling::i");
	}

	public Element getYesAllPageDocs() {
		return driver.FindElementByXPath("(//*[@id='Yes'])[1]");
	}

	public Element getPopUpOkBtn() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public Element getBackToSourceBtn() {
		return driver.FindElementByXPath("//a[contains(text(),'Back to Source')]");
	}

	// addition by shilpi on 04/26
	public Element getDocList_Previewpage() {
		return driver.FindElementById("divPreviewDocument");
	}

	public Element getDocList_Preview_Pagenotextbox() {
		return driver.FindElementById("PageNumber_divPreviewDocViewer");
	}

	public Element getDocList_Preview_AudioPlay() {
		return driver.FindElementById("btnPlayPause");
	}

	public Element getDocList_Preview_AudioStop() {
		return driver.FindElementByXPath("//*[@class='jp-stop fa fa-stop']");
	}

	public Element getDocList_Preview_AudioStartTime() {
		return driver.FindElementByXPath("//*[@class='jp-current-time start gmt']");
	}

	public Element getDocList_Preview_AudioDuration() {
		return driver.FindElementByXPath(".//*[@class='jp-current-time']");
	}

	public Element getDocList_actionButton() {
		return driver.FindElementById("idAction");
	}

	public Element getDocList_action_BulkAssignButton() {
		return driver.FindElementById("idBulkAssign");
	}

	public Element getDocList_action_BulkReleaseButton() {
		return driver.FindElementById("idBulkRelease");
	}

	public Element getDocList_SelectLenthtobeshown() {
		return driver.FindElementById("idPageLength");
	}

	public Element getDocList_QuickBatch() {
		return driver.FindElementByXPath("//a[contains(text(),'Quick Batch')]");
	}

	public Element getDocList_Preview_CloseButton() {
		return driver.FindElementByXPath("//span[@id='ui-id-1']/following-sibling::button");
	}

	// added by sowndariya
	public Element getSelectExistingTag(String tagname) {
		return driver.FindElementByXPath(
				"//div[@id='divBulkTagJSTree']//a[text()='" + tagname + "']");
	}
	public Element getExistingTagOption() {
		return driver.FindElementByXPath("//a[@id='tabExiting']");
	}
	
	public Element getDocList_Checkbox() {
		return driver.FindElementByXPath("//input[@id='chkDoc_1192']//following-sibling::i");
	}

	public Element getDocList_SaveToProfileButton() {
		return driver.FindElementByXPath("//a[text()='Save to Profile']");
	}

	public Element getDocList_ParentDocumentPopup() {
		return driver.FindElementByXPath(
				"//div[@class='MessageBoxMiddle']//p[text()='Do you want to also select all the child documents?']/..//button[text()=' Yes']");
	}

	public Element getDocList_BulkFolder() {
		return driver.FindElementById("idBulkFolder");
	}

	public Element getclk_Action() {
		return driver.FindElementByXPath(".//button[@id='idDocExplorerActions']");
	}

	public Element getselect_bulkFolder() {
		return driver.FindElementByXPath(".//a[@id='idBulkFolder']");
	}

	public Element getselect_bulkFolderCheckBox() {
		return driver.FindElementByXPath(".//div[@id='divBulkFolderJSTree']//ul/li/ul/li/a[text()='01NewFolderGr']");
	}

	public Element getSelect_continue_btn() {
		return driver.FindElementByXPath(".//button[contains(text(),'Continue')]");
	}

	public Element getSelect_FinalizeFolder() {
		return driver.FindElementByXPath(".//button[contains(text(),'Finalize Folder')]");
	}

	public Element getPureHitAddButton() {
		return driver.FindElementByXPath(".//*[@id='001']/i[2]");
	}

	public Element getBulkFolderAction() {
		return driver.FindElementByXPath("//a[contains(text(),'Bulk Folder')]");
	}

	public Element getSelectFolderExisting1(String folderName) {
		return driver.FindElementByXPath("//*[@id='divBulkFolderJSTree']//a[contains('FolderProd2462676')]/i[1]");
	}

	public Element getSelectFolderExisting(String folderName) {
		return driver.FindElementByXPath(
				"//div[@id='divBulkFolderJSTree']//a[text()='" + folderName + "']/i[contains(@class,'checkbox')]");
	}

	public Element getContinueCount() {
		return driver.FindElementByXPath(
				"//div[@class='well smart-form client-form']//button[normalize-space(text())='Continue']");
	}

	// Bulk tag and folder
	public Element getBulkActionButton() {
		return driver.FindElementById("idAction");
	}

	public Element getFinalCount() {
		return driver.FindElementByXPath("//span[@id='spanTotal']");
	}

	public Element getFinalizeButton() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	public Element getFirstFolder() {
		return driver.FindElementByXPath("//div[contains(@class,'active')]//a[@data-content='All Folders']");
	}

	public Element getFolderUnfolderDocumentsDialogBox() {
		return driver.FindElementByXPath("//span[text()='Folder/Unfolder Documents']");
	}

	public Element getRedactionWithoutRedactionTagsCheckbox() {
		return driver.FindElementByXPath(
				"//form[contains(@class,'client-form')]//label[text()='Redactions without Redaction Tags']/..//i");
	}

	public Element getRedactedTagTextArea() {
		return driver.FindElementByXPath("//p[text()='REDACTED']");
	}

	public Element getSelectTagsRadioButton() {
		return driver.FindElementByXPath("//input[@id='rdbTags']/..//i");
	}

	public Element getSelectTag(String tagname) {
		return driver
				.FindElementByXPath("//div[@id='tagTree']//a[text()='" + tagname + "']/i[contains(@class,'checkbox')]");
	}

	public Element getAllTags() {
		return driver.FindElementByXPath("//div[contains(@class,'active')]//a[@data-content='All Tags']");
	}

	public Element getSelectTagExisting(String tagname) {
		// return
		// driver.FindElementByXPath("//div[@id='divBulkFolderJSTree']//a[text()='FolderProd2462676']/i[contains(@class,'checkbox')]");
		return driver.FindElementByXPath(
				"//div[@id='divBulkTagJSTree']//a[text()='" + tagname + "']/i[contains(@class,'checkbox')]");
	}

	public Element getContinueButton() {
		return driver.FindElementByXPath(
				"//div[@class='well smart-form client-form']//button[normalize-space(text())='Continue']");
	}

	public Element getBulkTagAction() {
		return driver.FindElementByXPath("//a[text()='Bulk Tag']");
	}

	public Element getTagUntagDocumentsDialogBox() {
		return driver.FindElementByXPath("//span[text()='Tag/Untag Documents']");
	}

	public Element getDocListDocCheckbox(String documentID) {
		return driver.FindElementByXPath(
				"//td[text()='" + documentID + "']/..//input[@type='checkbox']/following-sibling::i");
	}

	public Element getMessageBoxYesBtn() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[@id='bot1-Msg1']");
	}

	public Element getNewTagBtn() {
		return driver.FindElementByXPath("//a[text()='New Tag']");
	}

	public Element getSelectTagClassificationDrp() {
		return driver.FindElementByXPath("//select[@id='ddlTagClassificationName']");
	}

	public Element getPrivilegedDrpValue() {
		return driver.FindElementByXPath("//select[@id='ddlTagClassificationName']//option[text()='Privileged']");
	}

	public Element getNameField() {
		return driver.FindElementByXPath("//input[@id='txtTagName']");
	}

	public Element getAction_Bulktag_AllTag() {
		return driver.FindElementByXPath("//*[@id='tagsJSTree']//a[text()='All Tags']");
	}

	public Element getTotalSelectedDocuments() {
		return driver.FindElementByXPath("//div[@id='divBulkAction']//span[@id='spanTotal']");
	}

	public Element getContinueBulkAssign() {
		return driver.FindElementByXPath("//*[@id='divBulkAction']//button[contains(.,'Continue')]");
	}

	// Added by Mohan

	public Element getThreadedDocId(int row) {
		return driver.FindElementByXPath("//*[@id='dtDocList']//tbody//tr[" + row + "]//label//i");
	}

	public Element getViewInDocView() {
		return driver.FindElementById("ViewInDocView");
	}

	// Added by Gopinath
	public Element getSelectAllDocCheckBox() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//th//i");
	}

	public Element getSelectAllPagesNoRadioButton() {
		return driver.FindElementByXPath("//input[@name='AllPages']//../input[2]");
	}

	public Element getSelectAllChildNoRadioButton() {
		return driver.FindElementByXPath("//input[@name='AllPages']//../input[4]");
	}

	public Element getOkButton() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public Element getNextButton() {
		return driver.FindElementByXPath("//a[text()='Next']");
	}

	public Element getSelectPopup() {
		return driver.FindElementById("Msg1");
	}

	public Element getSelectDocsOnAllPages() {
		return driver.FindElementByXPath(
				"//label[@for='SelectedDocs' ] | //label[text()='Do you want to select documents on all pages?'] | //p[@class='pText']/label[1]");
	}

	public Element getSelectAllChildSelectedParents() {
		return driver.FindElementByXPath(
				"//label[@for='SelectedDocsAllChildren' ] | //label[text()=' Do you want to select all children of the selected parents?'] | //p[@class='pText']/label[4]");
	}

	public Element getDocID(int rowNumber) {
		return driver.FindElementByXPath("//table[@id='dtDocList']//tbody//tr[" + rowNumber + "]//td[3]");
	}

	public Element getDocCheckBox(int rowNumber) {
		return driver.FindElementByXPath("//table[@id='dtDocList']//tbody//tr[" + rowNumber + "]//i");
	}

	public Element getActionDropDownButton() {
		return driver.FindElementById("idAction");
	}

	public Element getDocViewFromDropDown() {
		return driver.FindElementById("ViewInDocView");
	}

	public Element getDocIDFromDocView(int rowNumber) {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//tbody//tr[" + rowNumber + "]//td[2]");
	}

	public Element getBackToSourceButton() {
		return driver.FindElementByXPath("//div[@id='c1']//a");
	}

	public Element getSavedSearchTable() {
		return driver.FindElementById("SavedSearchGrid_wrapper");
	}

	public Element getSearchPageHeader() {
		return driver.FindElementByXPath("//h1[@class='page-title' and contains(text(),'Saved Search')]");
	}

	public Element getSavedSearch_SearchName() {
		return driver.FindElementById("txtSearchName");
	}

	public Element getSavedSearch_ApplyFilterButton() {
		return driver.FindElementById("btnApplyFilter");
	}

	public Element getSelectWithName(String serachName) {
		return driver.FindElementByXPath(
				"//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[1]//following::i");
	}

	public Element getToDocList() {
		return driver.FindElementById("btnDocumentList");
	}

	public Element getCancelButton() {
		return driver.FindElementById("bot2-Msg1");
	}

	// Added by Gopinath - 24/09/2021
	public Element getFirstDocumentButton() {
		return driver.FindElementByXPath("//li[@id='firstPage_divPreviewDocViewer']//i");
	}

	public Element getLastDocumentButton() {
		return driver.FindElementByXPath("//li[@id='lastPage_divPreviewDocViewer']//i");
	}

	public Element getNextDocumentButton() {
		return driver.FindElementByXPath("//li[@id='nextPage_divPreviewDocViewer']//i");
	}

	public Element getPrevDocumentButton() {
		return driver.FindElementByXPath("//li[@id='previousPage_divPreviewDocViewer']//i");
	}

	public Element getSearchPageTextField() {
		return driver.FindElementByXPath("//input[@id='PageNumber_divPreviewDocViewer']");
	}

	public Element getZoomInButton() {
		return driver.FindElementByXPath("//li[@id='zoomIn_divPreviewDocViewer']//i");
	}

	public Element getZoomOutButton() {
		return driver.FindElementByXPath("//li[@id='zoomOut_divPreviewDocViewer']//i");
	}

	public Element getResetZoomButton() {
		return driver.FindElementByXPath("//li[@id='fitContent_divPreviewDocViewer']//i");
	}

	public Element getRotateClockWiseButton() {
		return driver.FindElementByXPath("//li[@id='rotateRight_divPreviewDocViewer']//i");
	}

	public Element getRotateAntiClockWiseButton() {
		return driver.FindElementByXPath("//li[@id='rotateLeft_divPreviewDocViewer']//i");
	}

	public Element getPrintIcon() {
		return driver.FindElementByXPath("//li[@id='print_divPreviewDocViewer']//i");
	}

	public Element getDownloadIcon() {
		return driver.FindElementByXPath("//li[@id='liDocumentTypeDropDown']//i");
	}

	public Element getPreviedDocCloseButton() {
		return driver
				.FindElementByXPath("//span[text()='Preview Document']//..//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getFirstRowDocumentID() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//tbody//tr[1]//td[3]");
	}

	public Element getPreviedDocumentId() {
		return driver.FindElementByXPath("//li[@class='doc-id pull-right']//span[2]");
	}

	public Element getPDFOptionToDownload() {
		return driver.FindElementByXPath("//ul[@id='documentTypeDropDown']//li[2]//a");
	}

	public Element getFirstRowFileName() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//tbody//tr[1]//td[5]");
	}

	// Added by Gopinath - 27/09/2021
	public Element getAllPagesYesButton() {
		return driver.FindElementByXPath("//input[@name='AllPages' and @id='Yes']");
	}

	public Element geChildrenYesButton() {
		return driver.FindElementByXPath("//input[@name='AllChildren' and @id='Yes']");
	}

	// Added by Gopinath - 28/09/2021
	public Element getParentTableByRow(int rowNumber) {
		return driver.FindElementByXPath("//table[@id='dtDocList']/tbody/tr[" + (rowNumber + 1) + "]//table");
	}

	public Element getParentRow(int rowNumber) {
		return driver.FindElementByXPath("//table[@id='dtDocList']//tbody//tr[" + (rowNumber + 1) + "]//td[1]");
	}

	public Element getChildByRow(int rowNumber) {
		return driver.FindElementByXPath("//table[@id='dtDocList']/tbody/tr[" + (rowNumber + 1) + "]//table//tr");
	}

	// Added by Gopinath - 29/09/2021
	public Element bulkAddTagOkButton() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element bulkAddTagCancelButton() {
		return driver.FindElementById("bot2-Msg1");
	}

	public Element unTagRadioButton() {
		return driver.FindElementByXPath("//input[@id='toUnassign']//..//i");
	}

	public Element getBulkUntagTree() {
		return driver.FindElementById("divBulkTagJSTree");
	}

	public Element getExistingTagInUntagTable(String existingTagName) {
		return driver.FindElementByXPath("//a[@data-content='" + existingTagName + "']");
	}

	public Element getContinueButtonUntag() {
		return driver.FindElementById("btnAdd");
	}

	// Added by Gopinath - 30/09/2021
	public Element getSelectDownloadFileLnk() {
		return driver.FindElementByXPath("//div[@id='dt_basic_wrapper']//tbody/tr[1]//td[9]/a[1]");
	}

	public Element getSelectDocIdCheckBox() {
		return driver.FindElementByXPath("//thead//th[@class='sorting_disabled']//label//i");
	}

	public Element getRadioBtn1() {
		return driver.FindElementByXPath("//input[@id='Yes'][1]");
	}

	public Element getRadioBtn2() {
		return driver.FindElementByXPath("//input[@id='Yes'][2]");
	}

	public Element getOkBtn() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public Element getExportData() {
		return driver.FindElementById("idExport");
	}

	public Element getSelectDocInCheckBox() {
		return driver.FindElementByXPath("//strong[text()='DocFileName']/..//input/following-sibling::i");
	}

	public Element getAddToSelected() {
		return driver.FindElementByXPath(" //a[@id='addFormObjects-coreList']");
	}

	public Element getRunReport() {
		return driver.FindElementById("btnRunReport");
	}

	public Element getCloseExport() {
		return driver.FindElementByXPath(
				"//div[@class='ui-dialog-titlebar ui-corner-all ui-widget-header ui-helper-clearfix']//button");
	}

	public Element getSelctActivity() {
		return driver.FindElementByXPath("//span[@id='activity']/i");
	}

	public Element getViewAllBtn() {
		return driver.FindElementById("btnViewAll");
	}

	public Element getStatusSuccessTxt() {
		return driver.FindElementByXPath("//span[contains(text(),'Success')]");
	}

	public Element getCompletedStatus() {
		return driver.FindElementByXPath("//div[@id='dt_basic_wrapper']//tr[1]//td[8]");
	}

	public Element getselectDoc(int rowno) {
		return driver.FindElementByXPath(
				"//*[@id='dtDocList_wrapper']//following-sibling::tbody//tr[" + rowno + "]//label/i");
	}

	public Element getNewAssignment() {
		return driver.FindElementByXPath("//a[text()='New Assignment']");
	}

	public Element getSelectingDocCount() {
		return driver.FindElementByXPath(
				"//div[@id='newassignmentdiv']//div//label[text()='Starting Count Of Docs:']/following::div[text()='2']");
	}

	public Element getContinueBtn() {
		return driver.FindElementById("btnAssignComplete");
	}

	public Element getDocCount() {
		return driver.FindElementById("spanTotal");
	}

	public Element getFinalizeAssignment() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	public Element getUnAssignRadioBtn() {
		return driver.FindElementByXPath("//input[@id='toUnassign']/../child::i");
	}

	public Element getSelectCheckBox(String testing) {
		return driver.FindElementByXPath("//div[@id='jstreeUnAssign']//ul//li//ul//li//a[text()='" + testing + "']");
	}

	public Element getCountOfDocs() {
		return driver.FindElementByXPath("//label[text()='Count of Docs:']/../../..//span/following-sibling::div");
	}

	public Element getAssgnName() {
		return driver.FindElementById("AssignmentName");
	}

	public Element getSelectdCodingForm() {
		return driver.FindElementById("SelectedCodingForm");
	}

	public Element getAssgn_Keywordokbutton() {
		return driver.FindElementById("keywordOK");
	}

	public Element getSaveBtn() {
		return driver.FindElementByXPath("//input[@type='submit']");
	}

	public Element getNextBtn() {
		return driver.FindElementByXPath("//div[@id='GridAssignment_paginate']//ul//li[8]/a");
	}

	public Element getNoOfDocuments(String test) {
		return driver.FindElementByXPath("//tr//td[text()='" + test + "']/following-sibling::td[6]");
	}

	public Element getMasterDate() {
		return driver.FindElementByXPath("//li[text()='MasterDate']");
	}

	public Element getSetADateRange() {
		return driver.FindElementByXPath(
				"//div[@class='popover-content']//form[@class='smart-form DOCLIST']//select[@id='MasterDate-dtop']");
	}

	public Element getSelectDateRangeDropDown() {
		return driver.FindElementByXPath(
				"//div[@class='popover-content']//form[@class='smart-form DOCLIST']//select[@id='MasterDate-dtop']//option[text()='On']");
	}

	public Element getSelectADate() {
		return driver.FindElementByXPath("(//input[@id='MasterDate-1-DOCLIST'])[1]");
	}

	public Element getDatePickerMonth() {
		return driver.FindElementByXPath("//select[@class='ui-datepicker-month']");
	}

	public Element getDatePickerYear() {
		return driver.FindElementByXPath("//select[@class='ui-datepicker-year']");
	}

	public Element getPickDate(String Date) {
		return driver.FindElementByXPath("//tr//td//a[text()='" + Date + "']");
	}

	public Element getAddtoFilter() {
		return driver.FindElementById("action-DATETIME--82");
	}

	public Element getApplyFilters() {
		return driver.FindElementById("btnAppyFilter");
	}

	public Element getAssertDate(int colno) {
		return driver.FindElementByXPath("//th[text()='MasterDate']/following::tr[" + colno + "]//td[7]");
	}

	public ElementCollection getRowCount() {
		return driver.FindElementsByXPath("//table[@id='dtDocList']//tbody//tr");
	}

	public Element getActiveFilters() {
		return driver.FindElementById("active-DATETIME--82");
	}

	public Element SelectColumnBtn() {
		return driver.FindElementById("btnSelectColumn");
	}

	public Element getSelectEmailAuthorDomain() {
		return driver.FindElementByXPath("//li//Strong[text()='EmailAuthorDomain']");
	}

	public Element getUpdateColumnBtn() {
		return driver.FindElementById("btnUpdateColumns");
	}

	public Element getAddToSelect() {
		return driver.FindElementByXPath("//a[text()='Add to Selected']");
	}

	public Element getEmailAuthorDomainBth() {
		return driver.FindElementByXPath("//ul[@id='optionFilters']//li[text()='EmailAuthorDomain']");
	}

	public Element getIncludeBtn() {
		return driver.FindElementByXPath("(//div[@id='rbIncExclude']//label//i)[1]");
	}

	public Element getExcludeBtn() {
		return driver.FindElementByXPath("(//div[@id='rbIncExclude']//label//i)[2]");
	}

	public Element getClickToMakeSelection() {
		return driver.FindElementByXPath("//input[@placeholder='Click to make your selection']");
	}

	public Element getSelectAuthor() {
		return driver.FindElementByXPath("//ul[@id='select2-EmailAuthorDomain-results']//li[text()='aol.com']");
	}

	public Element getFilterToDomainName() {
		return driver.FindElementByXPath("//div[@class='popover bottom in']//a[@id='action-NVARCHAR--7']");
	}

	public Element getAssertEmaialAuthorDomain(int col) {
		return driver.FindElementByXPath("//th[text()='EmailAuthorDomain']/following::tr[" + col + "]//td[8]");
	}

	public Element getActiveFiltersinEmailDomain() {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li");
	}

	public Element getClearFilters() {
		return driver.FindElementByXPath("	//a[text()='Clear All']");
	}

	public Element getDomainAuthorName() {
		return driver.FindElementByXPath("//ul[@class='select2-selection__rendered']//li[1]");
	}

	public Element getActiveFilter(String emailDomainName) {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li[contains(text(),'" + emailDomainName
				+ "') and contains(text(),'EmailAuthorDomain - Include')]");
	}

	public Element getCustodianNameFilter() {
		return driver.FindElementByXPath("//li[text()='CustodianName']");
	}

	public Element getFilterByCustodianHeader() {
		return driver.FindElementByXPath("//h3[@class='popover-title']");
	}

	public Element getCustodianIncludeRadioBtn() {
		return driver.FindElementByXPath("//h3[@class='popover-title']/..//input[@id='Include']/following-sibling::i");
	}

	public Element getCustodianTextField() {
		return driver.FindElementByXPath("//input[@class='select2-search__field']");
	}

	public Element getCustodianOption(String custodianName) {
		return driver.FindElementByXPath("//li[text()='" + custodianName + "']");
	}

	public Element getAddToFilterButton() {
		return driver.FindElementByXPath("//div[@class='popover-content']//a[text()=' Add to Filter']");
	}

	public Element getAppliedIncludeCustodianFilter(String name) {
		return driver.FindElementByXPath(
				"//li[contains(text(),'" + name + "') and contains(text(),'CustodianName - Include')]");
	}

	public Element getActiveFiltersElement() {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li[1]");
	}

	// Added by Gopinath - 01/10/2021
	public Element getEmailAllDomainsBtn() {
		return driver.FindElementByXPath("//li[text()='EmailAllDomains']");
	}

	public Element getEmailAllDomainSelectAuthor() {
		return driver.FindElementByXPath("//ul[@id='select2-EmailAllDomains-results']//li[text()='aol.com']");
	}

	public Element getEmailAllDomainsApplyFilter() {
		return driver.FindElementByXPath(" (//a[@id='action-NVARCHAR--19'])[1]");
	}

	public Element getAssertEmailAllDomains(int colno) {
		return driver.FindElementByXPath("//th[text()='EmailAllDomains']/following::tr[" + colno + "]//td[8]");
	}

	public Element getEmailAuthorNameBtn() {
		return driver.FindElementByXPath("//li[text()='EmailAuthorName']");
	}

	public Element getEmailAuthorNameSelectAuthor() {
		return driver.FindElementByXPath("//ul//li[@class='select2-results__option'][5]");
	}

	public Element getEmailAuthorNameApplyFilter() {
		return driver.FindElementByXPath("(//a[@id='action-NVARCHAR--5'])[1]");
	}

	public Element getAssertEmailAuthorName(int colno) {
		return driver.FindElementByXPath("//th[text()='EmailAuthorName']/following::tr[" + colno + "]//td[8]");
	}

	public Element getActiveFilterInEmailALLDomain(String emailDomainName) {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li[contains(text(),'" + emailDomainName
				+ "') and contains(text(),'EmailAllDomains - Include')]");
	}

	public Element getActiveFilterInEmailAuthorName(String emailDomainName) {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li[contains(text(),'" + emailDomainName
				+ "') and contains(text(),'EmailAuthorName - Include')]");
	}

	public Element getSelectEmailAllDomains() {
		return driver.FindElementByXPath("//li//Strong[text()='EmailAllDomains']");
	}

	public Element getSelectEmailAuthorName() {
		return driver.FindElementByXPath("//li//Strong[text()='EmailAuthorName']");
	}

	// Added by Gopinath - 04/10/2021
	public Element getSelectallDocuments() {
		return driver.FindElementByXPath("//input[@id='selectAllRows']/..//i");
	}

	public Element getSelectPopupBtn() {
		return driver.FindElementByXPath("(//div[@class='MessageBoxButtonSection']//button)[1]");
	}

	public Element getNewFolderBtn() {
		return driver.FindElementByXPath("//a[@id='tabNew']");
	}

	public Element getEnterName() {
		return driver.FindElementById("txtFolderName");
	}

	public Element getSelectAllFolderBtn() {
		return driver.FindElementByXPath("//div[@id='folderJSTree']//a[@id='-1_anchor']//i");
	}

	public Element gettotalSelectedDocuments() {
		return driver.FindElementByXPath(
				"//div[contains(text(),'Total Selected Documen')]//span/following-sibling::div[@class='bulkActionsSpanLoderTotal']");
	}

	public Element getUnFolderRadioBtn() {
		return driver.FindElementByXPath("//input[@id='toUnfolder']/..//i");
	}

	public Element getSelectFolderCheckBox(String Folder) {
		return driver.FindElementByXPath("//div[@id='divBulkFolderJSTree']/ul/li/ul/li//a[text()='" + Folder + "']");
	}

	public Element getSelectingOneDocument() {
		return driver.FindElementByXPath("(//table[@id='dtDocList']//tr//td//label/i)[1]");
	}

	public Element getSelectAllFoldersOption() {
		return driver.FindElementById("FolderGroupList");
	}

	// Added by Gopinath - 05/10/2021
	public ElementCollection getCorelist() {
		return driver.FindElementsByXPath("//ol[@id='coreList']//li");
	}

	public Element getDocFileSize() {
		return driver.FindElementByXPath("//li//Strong[text()='DocFileSize']");
	}

	public Element getDocFileSizeColumn() {
		return driver.FindElementByXPath("//thead//tr//th[text()='DocFileSize']");
	}

	public Element getSelectDocFileSize() {
		return driver.FindElementByXPath(" //ul[@id='optionFilters']//li[text()='DocFileSize']");
	}

	public Element getSetAFileSizeRange() {
		return driver.FindElementById("DocFileSize-op");
	}

	public Element getSelectBelow() {
		return driver.FindElementByXPath("(//select[@id='DocFileSize-op']//option[text()='Below'])[1]");
	}

	public Element getAddFileSize() {
		return driver.FindElementById("DocFileSize-1-DOCLIST");
	}

	public Element getAddToFilterBtn() {
		return driver.FindElementById("action-INT--69");
	}

	public Element getDocFileSizeAssertion() {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li");
	}

	public Element getRowCountOfDocFileSize(int colno) {
		return driver.FindElementByXPath("//th[text()='DocFileSize']/following::tr[" + colno + "]//td[8]");
	}

	// Added by Raghuram
	public Element getTableFooterDocListCount() {
		return driver.FindElementByXPath("//div[@class='dataTables_info']");
	}

	// Added by Gopinath - 06/10/2021
	public Element getUnreleaseButton() {
		return driver.FindElementById("btnUnrelease");
	}

	public ElementCollection getColumnCount() {
		return driver.FindElementsByXPath("//th[text()='CustodianName']/following::tr//td[4]");
	}

	public ElementCollection getRowValueAfterSuffling() {
		return driver.FindElementsByXPath("//th[text()='CustodianName']/following::tr//td[3]");
	}

	public ElementCollection getNextColumnCount() {
		return driver.FindElementsByXPath("//th[text()='DocFileType']/following::tr//td[6]");
	}

	public ElementCollection getNextColumnCountAfterSuffling() {
		return driver.FindElementsByXPath("//th[text()='DocFileType']/following::tr//td[5]");
	}

	public Element getTableHeader() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//tr//th[3]");
	}

	public Element getNextTableHeader() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//tr//th[5]");
	}

	public Element getCustodianName() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//th[text()='CustodianName']");
	}

	public Element getDocID() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//th[text()='DocID']");
	}

	public Element getDocFileType() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//th[text()='DocFileType']");
	}

	public Element getDocFileName() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//th[text()='DocFileName']");
	}

	// Added by Gopinath - 09/10/2021
	public Element getRemoveLink() {
		return driver.FindElementByXPath("//span[text()='CustodianName']/following-sibling::a");
	}

	public ElementCollection getColumnHeader() {
		return driver.FindElementsByXPath("//table[@id='dtDocList']//thead//tr//th");
	}

	public Element getLastRow() {
		return driver.FindElementByXPath(
				"//ul[@class='pagination pagination-sm']//li[@class='paginate_button next']/preceding-sibling::li[1]//a");
	}

	public ElementCollection getRowCountforDocList() {
		return driver.FindElementsByXPath("//table[@id='dtDocList']//tbody//tr");
	}

	public Element getSelectDropDown() {
		return driver.FindElementByXPath("//select[@id='idPageLength']//option[text()='500']");
	}

	public Element getParentDocument() {
		return driver.FindElementByXPath("(//tr//td[contains(@class,'details-control')])[1]/..//label//i");
	}

	public Element getSelectParentDocument() {
		return driver.FindElementByXPath("(//tr//td[contains(@class,'details-control')])[1]");
	}

	public ElementCollection getChildDocumentRowCount() {
		return driver.FindElementsByXPath("//div[@id='childlist_160_dtDocList_wrapper']//tbody//tr");
	}

	public Element getChildDocumentCheckBox(int rowno) {
		return driver.FindElementByXPath("//div[@id='childlist_160_dtDocList_wrapper']//tr[" + rowno
				+ "]//label//input[@class='checkboxes']/..//i");
	}

//	    Added by Baskar
	public ElementCollection getDocIds() {
		return driver.FindElementsByXPath("//table[@id='dtDocList']//tbody//tr//td[3]");
	}

	public Element getDocList_Action_Drp_Dwn() {
		return driver.FindElementByXPath("//button[@id='idAction']");
	}

	public Element getDocListViewInDocView() {
		return driver.FindElementByXPath("//ul[@class='dropdown-menu action-dd']//li//a[@id='ViewInDocView']");
	}

	// Added by Gopinath - 25/11/2021
	public Element getChildDocument() {
		return driver.FindElementByXPath("(//td[@class=' details-control'])[1]/../following-sibling::tr[1]//i");
	}

	// added by Brundha
	public Element getRemove() {
		return driver.FindElementByXPath("//span[text()='DocFileType']/following-sibling::a");
	}

	public ElementCollection getNextColumnCountAfterSuffle() {
		return driver.FindElementsByXPath("//th[text()='CustodianName']/following::tr//td[7]");
	}
	
	//Added by Gopinath - 29/12/2021
	public Element getPersistantHitCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkPersistSerachHits']//..//i");
	}
	public Element getSelectAssignmentExisting(String assignmentName) {
		return driver.FindElementByXPath("//*[@id='jstreeComplete']//a[contains(.,'" + assignmentName + "')]");
	}

	public DocListPage(Driver driver) {

		this.driver = driver;
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);
		search = new SessionSearch(driver);
		base = new BaseClass(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		// this.driver.getWebDriver().get(Input.url + "Document/DocList");
	}

	public void include(String data) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeRadioBtn().Visible();
			}
		}), Input.wait30);
		getIncludeRadioBtn().Click();
		getSearchTextArea().SendKeys(data);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
		getAddToFilter().waitAndClick(10);

	}

	public void exclude(String data) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getExcludeRadioBtn().Visible();
			}
		}), Input.wait30);
		getExcludeRadioBtn().Click();
		getSearchTextArea().SendKeys(data);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
		getAddToFilter().Click();

	}

	public void dateFilter(String option, String fromDate, String toDate) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMasterDateFilter().Visible();
			}
		}), Input.wait30);

		getMasterDateFilter().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMasterDateRange().Visible();
			}
		}), Input.wait30);
		getMasterDateRange().selectFromDropdown().selectByValue(option);

		getSetFromMasterDate().SendKeys(fromDate + Keys.TAB);

		if (option.equalsIgnoreCase("between"))
			getSetToMasterDate().SendKeys(toDate + Keys.TAB);

		getAddToFilter().waitAndClick(10);
		getApplyFilter().waitAndClick(10);

	}

	public void removeRpeviousFilters() {
		driver.scrollPageToTop();
		try {
			getCancelFilter2().waitAndClick(10);
		} catch (Exception e) {

			getCancelFilter1().waitAndClick(10);
		}
	}

	public void validateCount(String counts) {
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return !getDocList_info().getText().isEmpty();
			}
		}), Input.wait60);
		// to make sure that not reading previous results and wait for page mask to
		// complete,
		// simply try to click on result text!
		driver.scrollingToBottomofAPage();
		getDocList_info().waitAndClick(30); // works only when results updates!!

		// Now validate results
		Assert.assertTrue(getDocList_info().getText().toString().equalsIgnoreCase(counts));

	}

	public void DoclistPreviewNonAudio() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getColumnText(1, 8).Visible();
			}
		}), Input.wait60);
		getColumnText(1, 8).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_Previewpage().Visible();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocList_Previewpage().Enabled());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_Preview_Pagenotextbox().Visible();
			}
		}), Input.wait30);
		getDocList_Preview_Pagenotextbox().SendKeys("5");
		getDocList_Preview_Pagenotextbox().Enter();

		driver.scrollingToBottomofAPage();
		Thread.sleep(2000);

		driver.scrollPageToTop();
	}

	public void DoclistPreviewAudio() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getColumnText(1, 8).Visible();
			}
		}), Input.wait60);
		getColumnText(1, 8).waitAndClick(10);
		Thread.sleep(5000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_Preview_AudioPlay().Visible();
			}
		}), Input.wait60);
		Assert.assertTrue(getDocList_Preview_AudioPlay().Displayed());

		getDocList_Preview_AudioPlay().waitAndClick(10);
		Thread.sleep(7000);

		getDocList_Preview_AudioPlay().waitAndClick(10);

		String playtime = getDocList_Preview_AudioDuration().getText();
		System.out.println(playtime);

		getDocList_Preview_AudioStop().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_Previewpage().Visible();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocList_Previewpage().Enabled());
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocList_Preview_Pagenotextbox().Visible() ;}}),Input.wait30);
		 * getDocList_Preview_Pagenotextbox().SendKeys("5");
		 * getDocList_Preview_Pagenotextbox().Enter();
		 */

		getDocList_Preview_CloseButton().waitAndClick(10);
	}

	public void DoclisttobulkAssign(String assignmentName, String length) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_SelectLenthtobeshown().Visible();
			}
		}), Input.wait60);

		getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(length);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAll().Visible();
			}
		}), Input.wait60);
		getSelectAll().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopUpOkBtn().Visible();
			}
		}), Input.wait60);
		getPopUpOkBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_actionButton().Visible();
			}
		}), Input.wait60);
		getDocList_actionButton().waitAndClick(10);
		Thread.sleep(3000);

		getDocList_action_BulkAssignButton().waitAndClick(10);

		System.out.println("performing bulk assign");
	}

	public void DoclisttobulkRelease(String SecGroup) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAll().Visible();
			}
		}), Input.wait60);
		getSelectAll().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopUpOkBtn().Visible();
			}
		}), Input.wait60);
		getPopUpOkBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_actionButton().Visible();
			}
		}), Input.wait60);
		getDocList_actionButton().waitAndClick(10);
		Thread.sleep(3000);

		getDocList_action_BulkReleaseButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return search.getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible();
			}
		}), Input.wait60);
		search.getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return search.getBulkRelease_ButtonRelease().Visible();
			}
		}), Input.wait60);
		search.getBulkRelease_ButtonRelease().Click();

		search.getFinalizeButton().waitAndClick(30);

		base.VerifySuccessMessage("Records saved successfully");

		System.out.println("performing bulk release");

	}

	public void Selectpagelength(String length) {
		// driver.getWebDriver().get(Input.url+ "Document/DocList");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_SelectLenthtobeshown().Visible();
			}
		}), Input.wait60);

		getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(length);

	}

	public void DoclisttoQuickbatch(String length) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_SelectLenthtobeshown().Visible();
			}
		}), Input.wait30);

		getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(length);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAll().Visible();
			}
		}), Input.wait60);
		getSelectAll().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopUpOkBtn().Visible();
			}
		}), Input.wait60);
		getPopUpOkBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocList_actionButton().Visible();
			}
		}), Input.wait60);
		getDocList_actionButton().waitAndClick(10);
		Thread.sleep(3000);

		getDocList_QuickBatch().waitAndClick(10);

		System.out.println("performing quick batch");
	}

	public void getIntoFullScreen() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_F11);
		r.keyRelease(KeyEvent.VK_F11);
	}

	public void getExitFullScreen() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_F11);
		r.keyRelease(KeyEvent.VK_F11);
	}

	public void SelectingParentDocumentFromDocList() {

		// selecting parent document checkbox
		base.waitForElement(getDocList_Checkbox());
		getDocList_Checkbox().waitAndClick(10);

		// clicking yes popup to select only parent document
		base.waitForElement(getDocList_ParentDocumentPopup());
		driver.waitForPageToBeReady();
		getDocList_ParentDocumentPopup().waitAndClick(10);

		// saving doc to profile
		base.waitForElement(getDocList_SaveToProfileButton());
		getDocList_SaveToProfileButton().Click();

		// clicking yes popup
		base.waitForElement(getPopUpOkBtn());
		getPopUpOkBtn().Click();
	}

	public void saveDocListToProfile(String orphanDocument) {

		// selecting parent document checkbox
		base.waitForElement(getDocListDocCheckbox(orphanDocument));
		getDocListDocCheckbox(orphanDocument).Click();

		// saving doc to profile
		base.waitForElement(getDocList_SaveToProfileButton());
		getDocList_SaveToProfileButton().waitAndClick(5);

		// clicking yes popup
		base.waitForElement(getPopUpOkBtn());
		getPopUpOkBtn().Click();
	}

	public void bulkFolderExisting(final String folderName) throws AWTException, InterruptedException {

		try {
			// getPureHitAddButton().waitAndClick(10);

		} catch (Exception e) {

			// System.out.println("Pure hit block already moved to action panel");

			UtilityLog.info("Pure hit block already moved to action panel");
			Reporter.log("Pure hit block already moved to action panel", true);

		}

		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getBulkFolderAction().Visible();

			}

		}), Input.wait60);

		getBulkFolderAction().Click();

		driver.Manage().window().fullscreen();
		base.waitForElement(getFolderUnfolderDocumentsDialogBox());

		getFolderUnfolderDocumentsDialogBox().Click();

		System.out.println("Popup is displayed");
		base.hitTabKey(3);
		base.hitEnterKey(2);

		Actions actions = new Actions(driver.getWebDriver());

		while (true) {

			actions.sendKeys(Keys.PAGE_DOWN).build().perform();

			try {

				getSelectFolderExisting(folderName).Click();

				System.out.println("Clicked Folder");

				break;

			} catch (Exception e) {

				System.out.println("Searching for folder");
			}

			Thread.sleep(2000);
		}

		Thread.sleep(10000);
		base.waitForElement(getContinueCount());

		getContinueCount().Click();

		System.out.println("Click continue");
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		base.waitForElement(getFinalizeButton());
		getFinalizeButton().Click();

		driver.Manage().window().maximize();

		base.VerifySuccessMessage("Records saved successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}

		}), Input.wait60);

		// System.out.println("Bulk folder is done, folder is : "+folderName);

		UtilityLog.info("Bulk folder is done, folder is : " + folderName);

		Reporter.log("Bulk folder is done, folder is : " + folderName, true);

		// Since page is freezing after bulk actiononly in automation, lets reload page

		// to avoid it..

		driver.getWebDriver().navigate().refresh();

	}

	public void bulkTagExisting(final String tagname) throws AWTException, InterruptedException {

		try {
			getPureHitAddButton().waitAndClick(10);
		} catch (Exception e) {
			// System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
			Reporter.log("Pure hit block already moved to action panel", true);
		}
		getBulkActionButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderAction().Visible();
			}
		}), Input.wait60);

		getBulkTagAction().Click();

		Thread.sleep(10000);

		getTagUntagDocumentsDialogBox().waitAndClick(10);

		System.out.println("Tag and Untag Popup is displayed");

		Actions actions = new Actions(driver.getWebDriver());
		driver.Manage().window().fullscreen();
		Thread.sleep(2000);

		base.hitTabKey(3);
		base.hitEnterKey(2);

		actions.sendKeys(Keys.PAGE_DOWN).build().perform();
		System.out.println(tagname);
		getSelectTagExisting(tagname).Click();
		System.out.println("Clicked tagname");

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		base.waitForElement(getContinueButton());
		getContinueButton().Click();
		getFinalizeButton().waitAndClick(10);
		driver.Manage().window().maximize();
		base.waitForElement(getPopUpOkBtn());
		getPopUpOkBtn().Click();
		base.VerifySuccessMessage("Records saved successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		// System.out.println("Bulk folder is done, folder is : "+folderName);
		UtilityLog.info("Bulk folder is done, folder is : " + tagname);
		Reporter.log("Bulk folder is done, folder is : " + tagname, true);
		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();
	}

	public void selectChildDocumentAndSave(String documentID) {

		base.waitForElement(getDocListDocCheckbox(documentID));
		getDocListDocCheckbox(documentID).Click();

		base.waitForElement(getMessageBoxYesBtn());
		getMessageBoxYesBtn().Click();
	}

	/**
	 * @author Sowndarya
	 * @param DocumentID
	 */
	public void SelectingParentDocumentFromDocList(String DocumentID) {

		// selecting parent document checkbox

		base.waitForElement(getDocListDocCheckbox(DocumentID));
		getDocListDocCheckbox(DocumentID).Click();

		// clicking yes popup to select only parent document
		base.waitForElement(getDocList_ParentDocumentPopup());
		getDocList_ParentDocumentPopup().Click();

		// saving doc to profile
		base.waitForElement(getDocList_SaveToProfileButton());
		getDocList_SaveToProfileButton().Click();

		// clicking yes popup
		base.waitForElement(getPopUpOkBtn());
		getPopUpOkBtn().Click();
	}
	
	/**
	 * @Author:Indium-Sowndarya 
	 * @param tagname
	 */
	public void bulkTagExistingFromDoclist(final String tagname) throws AWTException, InterruptedException {

		// System.out.println("Pure hit block already moved to action panel");
		UtilityLog.info("Pure hit block already moved to action panel");
		Reporter.log("Pure hit block already moved to action panel", true);

		getBulkActionButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderAction().Visible();
			}
		}), Input.wait60);

		getBulkTagAction().waitAndClick(10);

		getTagUntagDocumentsDialogBox().waitAndClick(10);
		System.out.println("Tag and Untag Popup is displayed");

//		Actions actions = new Actions(driver.getWebDriver());
		driver.Manage().window().fullscreen();
		base.waitTime(2);
		System.out.println(tagname);
		getExistingTagOption().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectExistingTag(tagname));
		getSelectExistingTag(tagname).waitAndClick(10);
		System.out.println("Clicked tagname");

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		base.waitForElement(getContinueButton());
		getContinueButton().Click();
		getFinalizeButton().waitAndClick(10);
		driver.Manage().window().maximize();
		base.waitForElement(getPopUpOkBtn());
		getPopUpOkBtn().Click();
		base.VerifySuccessMessage("Records saved successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		// System.out.println("Bulk folder is done, folder is : "+folderName);
		UtilityLog.info("Bulk folder is done, folder is : " + tagname);
		Reporter.log("Bulk folder is done, folder is : " + tagname, true);
		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();
	}

	/**
	 * @Author:Indium-Sowndarya Modified on 10/27/2021 by Indium-Sowndarya
	 * @param tagname
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void addNewBulkTag(String tagname) throws InterruptedException, AWTException {

		try {
			getPureHitAddButton().waitAndClick(10);
		} catch (Exception e) {
			// System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
			Reporter.log("Pure hit block already moved to action panel", true);
		}
		// click Action button
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);

		// click on Bulk Tag
		base.waitForElement(getBulkTagAction());
		getBulkTagAction().Click();

		base.waitForElement(getTagUntagDocumentsDialogBox());
		getTagUntagDocumentsDialogBox().waitAndClick(5);

		driver.Manage().window().fullscreen();

		base.waitForElement(getNewTagBtn());
		getNewTagBtn().Click();

		base.waitForElement(getAction_Bulktag_AllTag());
		getAction_Bulktag_AllTag().Click();

		driver.waitForPageToBeReady();
		getNameField().SendKeys(tagname);
		System.out.println("New tag is created-" + tagname);

		base.waitForElement(getSelectTagClassificationDrp());
		getSelectTagClassificationDrp().selectFromDropdown().selectByVisibleText("Privileged");

		base.waitTillElemetToBeClickable(getContinueBulkAssign());
		getContinueBulkAssign().waitAndClick(25);

		base.waitForElement(getTotalSelectedDocuments());

		base.waitForElement(getFinalizeButton());
		getFinalizeButton().Click();

		driver.Manage().window().maximize();

	}

	/**
	 * @author Gopinath
	 * @Description : Method to selecting all documents in current page.
	 */
	public void selectAllDocumentsInCurrentPageOnly() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			for (int i = 0; i < 15; i++) {
				try {
					getSelectAllDocCheckBox().Displayed();
					getSelectAllDocCheckBox().Click();
					break;
				} catch (Exception e) {
					base.waitForElement(getSelectAllDocCheckBox());
					base.waitTillElemetToBeClickable(getSelectAllDocCheckBox());
				}
			}

			for (int i = 0; i < 15; i++) {
				try {
					base.waitForElement(getSelectAllPagesNoRadioButton());
					base.waitTillElemetToBeClickable(getSelectAllPagesNoRadioButton());
					break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
			try {
				getSelectAllPagesNoRadioButton().getWebElement().isSelected();
				base.passedStep("Documents on all pages 'No' radio button is already selected");
			} catch (Exception e) {
				getSelectAllPagesNoRadioButton().Click();
				base.passedStep("Documents on all pages 'No' radio button is selected");
			}
			base.waitForElement(getSelectAllChildNoRadioButton());
			base.waitTillElemetToBeClickable(getSelectAllChildNoRadioButton());
			try {
				getSelectAllChildNoRadioButton().getWebElement().isSelected();
				base.passedStep("All children of the selected parents 'No' radio button is already selected");
			} catch (Exception e) {
				getSelectAllChildNoRadioButton().Click();
				base.passedStep("All children of the selected parents 'No' radio button is selected");
			}
			base.waitForElement(getOkButton());
			base.waitTillElemetToBeClickable(getOkButton());
			getOkButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting all documents in current page" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to all documents are selected in current page.
	 */
	public void verifyAllDocumentsAreSelectedInCurrentPageOnly() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			List<WebElement> documentsCheckBoxs = driver.getWebDriver()
					.findElements(By.xpath("//tbody//label[@class='checkbox']//input"));
			for (WebElement docCheckBox : documentsCheckBoxs) {
				if (!docCheckBox.isSelected()) {
					base.failedStep("Check Boxes are not selected in current page");
				}
			}
			base.passedStep("Check Boxes are selected in current page");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting check boxes in current page" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to all documents are not selected other than current
	 *              page.
	 */
	public void verifyAllDocumentsAreNotSelectedOtherThanCurrentPage() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getNextButton());
			base.waitTillElemetToBeClickable(getNextButton());
			getNextButton().Click();
			driver.scrollingToBottomofAPage();
			List<WebElement> documentsCheckBoxs = driver.getWebDriver()
					.findElements(By.xpath("//tbody//label[@class='checkbox']//input"));
			for (WebElement docCheckBox : documentsCheckBoxs) {
				if (docCheckBox.isSelected()) {
					base.failedStep("Check Boxes are selected other than current page");
				}
			}
			base.passedStep("Check Boxes are not selected other than current page");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting check boxes in current page" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify selecting All Documents on all pages And All
	 *              Children Of Parents Are Displayed.
	 */
	public void verifyAllDocumentsAndAllChildrenOfParentsRadioAreDisplayed() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			for (int i = 0; i < 15; i++) {
				try {
					getSelectAllDocCheckBox().Displayed();
					getSelectAllDocCheckBox().Click();
					break;
				} catch (Exception e) {
					base.waitForElement(getSelectAllDocCheckBox());
					base.waitTillElemetToBeClickable(getSelectAllDocCheckBox());
				}
			}

			for (int i = 0; i < 15; i++) {
				try {
					base.waitForElement(getSelectAllPagesNoRadioButton());
					base.waitTillElemetToBeClickable(getSelectAllPagesNoRadioButton());
					break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
			if (getSelectPopup().Displayed() && getSelectDocsOnAllPages().Displayed()
					&& getSelectAllChildSelectedParents().Displayed()) {
				base.passedStep("Selcting All Documents on all pages And All Children Of Parents are displayed");
			}

			base.waitForElement(getCancelButton());
			base.waitTillElemetToBeClickable(getCancelButton());
			getCancelButton().Click();

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verify selecting All Documents on all pages And All Children Of Parents"
							+ e.getMessage());
		}
	}

	public ArrayList<String> selectMultipleDocsFromDocList(int numberOfDocs) {
		ArrayList<String> arList = null;
		try {
			arList = new ArrayList<String>();
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			for (int i = 0; i < 15; i++) {
				try {
					getSelectAllDocCheckBox().Displayed();
					break;
				} catch (Exception e) {
					base.waitForElement(getSelectAllDocCheckBox());
					base.waitTillElemetToBeClickable(getSelectAllDocCheckBox());
				}
			}
			Random rand = new Random();
			for (int i = 0; i < numberOfDocs; i++) {
				final int random1 = rand.nextInt(10);
				driver.scrollingToBottomofAPage();
				if (random1 != 0) {
					getDocCheckBox(random1).Click();
					arList.add(getDocID(random1).getText().trim());
				} else if (random1 == 0) {
					i--;
				}
			}
			System.out.println("Documents selected " + arList);
			base.passedStep("Selcted multiple documents from doc list successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while seleting multiple documents from doclist" + e.getMessage());
		}
		return arList;
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify selected documents on Doc list reflected to
	 *              docview.
	 */
	public void verifySelectedDocumentsReflectedToDocView(ArrayList<String> docIDS) {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			docViewMetaDataPage.selectValueFromDropDown(getActionDropDownButton(), getDocViewFromDropDown());
			base.waitForElement(getDocIDFromDocView(1));
			base.waitTillElemetToBeClickable(getDocIDFromDocView(1));
			List<WebElement> documentsIds = driver.getWebDriver()
					.findElements(By.xpath("//table[@id='SearchDataTable']//tbody//tr"));
			for (int row = 0; row < documentsIds.size(); row++) {
				String docID = getDocIDFromDocView(row + 1).getText().trim();
				if (docIDS.contains(docID)) {
					base.passedStep(docID + " document selected in Doc list is reflected to doc view successfully");
				} else {
					base.failedStep(docID + " document selected in Doc list is not reflected to doc view ");
				}
			}
			base.passedStep("Multiple documents selected in doc list is reflected to Doc view");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying selected documents on Doc list reflected to docview"
					+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify user navigate to Saved search by clicking
	 *              back to source button on DocList page.
	 */
	public void verifyUserNaviagtedFromDocListPageToSavedSearchPage(String searchName, String savedSearchPageTitle) {
		try {

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			base.waitForElement(getSavedSearch_SearchName());
			getSavedSearch_SearchName().SendKeys(searchName);
			Thread.sleep(2000);
			getSavedSearch_ApplyFilterButton().Click();
			base.waitForElement(getSelectWithName(searchName));
			getSelectWithName(searchName).waitAndClick(10);
			getToDocList().waitAndClick(10);
			try {
				base.getYesBtn().waitAndClick(10);
			} catch (Exception e) {
				System.out.println("Pop up message does not appear");
				Log.info("Pop up message does not appear");
			}
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			for (int i = 0; i < 10; i++) {
				try {
					getBackToSourceButton().Click();
					break;
				} catch (Exception e) {
					base.waitForElement(getBackToSourceButton());
					base.waitTillElemetToBeClickable(getBackToSourceButton());
				}
			}
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			for (int i = 0; i < 15; i++) {
				try {
					System.out.println("Navigated to saved search by page title : " + driver.getWebDriver().getTitle());
					if (getSavedSearchTable().Displayed() && getSearchPageHeader().Displayed()
							&& driver.getWebDriver().getTitle().trim().equalsIgnoreCase(savedSearchPageTitle.trim())) {
						base.passedStep("Navigated to Saved search from DocList page successfully");
					}
					break;
				} catch (Exception e) {
					base.waitForElement(getSavedSearchTable());
					base.waitTillElemetToBeClickable(getSavedSearchTable());
				}
				if (i == 15) {
					base.failedStep("Failed to Navigate to Saved search from DocList page");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying user navigate to Saved search by clicking back to source button on DocList page"
							+ e.getMessage());
		}
	}

	/**
	 * @Author Mohan Created on 14/09/2021
	 * @Description To select Threaded doc form doclist and view in Docview
	 * 
	 */
	public void selectThreadedDocAndViewInDocView() {
		try {
			driver.waitForPageToBeReady();
			for (int i = 6; i <= 6; i++) {
				getThreadedDocId(i).ScrollTo();
				getThreadedDocId(i).waitAndClick(10);
			}

			driver.scrollPageToTop();
			base.waitForElement(getDocList_actionButton());
			getDocList_actionButton().waitAndClick(5);

			base.waitForElement(getViewInDocView());
			getViewInDocView().waitAndClick(5);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Doc is viewed DocView");
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify options avaliable on document preview page.
	 */
	public void verifyOptionsAvaliableOnDocPreview() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getFirstDocumentButton());
			base.waitTillElemetToBeClickable(getFirstDocumentButton());
			Assert.assertEquals(true, getFirstDocumentButton().getWebElement().isDisplayed(),
					"Navigate to First Document Button is not displayed");
			if (getFirstDocumentButton().Displayed()) {
				base.passedStep("Navigate to First Document Button is displayed successfully");
			}
			Assert.assertEquals(true, getLastDocumentButton().getWebElement().isDisplayed(),
					"Navigate to last Document Button is not displayed");
			if (getLastDocumentButton().Displayed()) {
				base.passedStep("Navigate to Last Document Button is displayed successfully");
			}
			Assert.assertEquals(true, getNextDocumentButton().getWebElement().isDisplayed(),
					"Navigate to Next Document Button is not displayed");
			if (getNextDocumentButton().Displayed()) {
				base.passedStep("Navigate to next Document Button is displayed successfully");
			}
			Assert.assertEquals(true, getPrevDocumentButton().getWebElement().isDisplayed(),
					"Navigate to Previous Document Button is not displayed");
			if (getPrevDocumentButton().Displayed()) {
				base.passedStep("Navigate to previous Document Button is displayed successfully");
			}
			Assert.assertEquals(true, getSearchPageTextField().getWebElement().isDisplayed(),
					"Search page text field is not displayed");
			if (getSearchPageTextField().Displayed()) {
				base.passedStep("Search page text field is displayed successfully");
			}
			Assert.assertEquals(true, getZoomInButton().getWebElement().isDisplayed(), "Zoom in icon is not displayed");
			if (getZoomInButton().Displayed()) {
				base.passedStep("Zoom in icon is displayed successfully");
			}
			Assert.assertEquals(true, getZoomOutButton().getWebElement().isDisplayed(),
					"Zoom out icon is not displayed");
			if (getZoomOutButton().Displayed()) {
				base.passedStep("Zoom out icon is displayed successfully");
			}
			Assert.assertEquals(true, getResetZoomButton().getWebElement().isDisplayed(),
					"Reset zoom icon is not displayed");
			if (getResetZoomButton().Displayed()) {
				base.passedStep("Reset zoom icon is displayed successfully");
			}
			Assert.assertEquals(true, getRotateClockWiseButton().getWebElement().isDisplayed(),
					"Rotate clock wise icon is not displayed");
			if (getRotateClockWiseButton().Displayed()) {
				base.passedStep("Rotate clock wise icon is displayed successfully");
			}
			Assert.assertEquals(true, getRotateAntiClockWiseButton().getWebElement().isDisplayed(),
					"Rotate anti clock wise icon is not displayed");
			if (getRotateAntiClockWiseButton().Displayed()) {
				base.passedStep("Rotate anti clock wise icon is displayed successfully");
			}
			Assert.assertEquals(true, getPrintIcon().getWebElement().isDisplayed(), "Print icon is not displayed");
			if (getPrintIcon().Displayed()) {
				base.passedStep("Print icon is displayed successfully");
			}
			Assert.assertEquals(true, getDownloadIcon().getWebElement().isDisplayed(),
					"Download icon is not displayed");
			if (getDownloadIcon().Displayed()) {
				base.passedStep("Download icon is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying options avaliable on document preview page on DocList page"
							+ e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to close previewed document.
	 */
	public void closePreviewedDocument() {
		try {
			base.waitForElement(getPreviedDocCloseButton());
			base.waitTillElemetToBeClickable(getPreviedDocCloseButton());
			getPreviedDocCloseButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while close previewed document" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify selected document ID to previewed Doc id.
	 */
	public void verifySelectedDocumetIdToPreviewedDocId() {
		try {
			base.waitForElement(getFirstRowDocumentID());
			base.waitTillElemetToBeClickable(getFirstRowDocumentID());
			String firstRowDocId = getFirstRowDocumentID().getText().trim();
			DoclistPreviewNonAudio();
			base.waitForElement(getPreviedDocumentId());
			base.waitTillElemetToBeClickable(getPreviedDocumentId());
			String previewedDocId = getPreviedDocumentId().getText().trim();
			if (firstRowDocId.equalsIgnoreCase(previewedDocId)) {
				base.passedStep("Selected document ID is same as previewed Doc id");
			} else {
				base.failedStep("Selected document ID is not same as previewed Doc id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying options avaliable on document preview page on DocList page"
							+ e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify downloded pdf file name consists previed Doc
	 *              file name.
	 */
	public void verifyDownlodedFileNameConsistsPreviwedDocFileName() {
		try {
			String mainWindow = driver.getWebDriver().getWindowHandle();
			base.waitForElement(getDownloadIcon());
			base.waitTillElemetToBeClickable(getDownloadIcon());
			getDownloadIcon().Click();
			base.waitForElement(getPDFOptionToDownload());
			base.waitTillElemetToBeClickable(getPDFOptionToDownload());
			getPDFOptionToDownload().Click();

			closePreviewedDocument();

			String actFileName[] = getFirstRowFileName().getText().trim().split(".", 2);
			// open a new tab
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			js.executeScript("window.open()");
			// switch to new tab
			// Switch to new window opened
			for (String winHandle : driver.getWebDriver().getWindowHandles()) {
				driver.getWebDriver().switchTo().window(winHandle);
			}
			// navigate to chrome downloads
			driver.get("chrome://downloads");
			Thread.sleep(3000);
			// get the latest downloaded file name
			String fileName = (String) js.executeScript(
					"return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
			if (fileName.contains(actFileName[0].trim())) {
				base.passedStep("PDF file downloded by name :: " + fileName + " as expected");
			} else {
				base.failedStep("PDF file downloded by name :: " + fileName + " is not as expected");
			}
			driver.getWebDriver().close();
			driver.getWebDriver().switchTo().window(mainWindow);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying downloded pdf file name consists previed Doc file name on DocList page"
							+ e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify non of child documents are repeated.
	 */
	public void verifyNonOfChildDocumentsAreRepeated() {
		try {
			ArrayList<String> docIds = new ArrayList<String>();
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Thread.sleep(2000);
			List<WebElement> parents = driver.getWebDriver().findElements(By.xpath("//td[@class=' details-control']"));
			for (int row = 0; row < parents.size(); row++) {
				js.executeScript("arguments[0].scrollIntoView(true);", parents.get(row));
				js.executeScript("window.scrollBy(0,-250)");
				for (int i = 0; i < 15; i++) {
					try {
						Thread.sleep(1000);
						parents.get(row).click();
						break;
					} catch (Exception e) {
						js.executeScript("window.scrollBy(0,-250)");
					}
				}
				List<WebElement> children = driver.getWebDriver().findElements(
						By.xpath("//tr[contains(@class,'shown')]//..//tbody//td[contains(@class,' details-control')]"));

				if (children.size() > 0) {
					List<WebElement> children2 = driver.getWebDriver().findElements(By.xpath(
							"//tr[contains(@class,'shown')]//..//tbody//td[contains(@class,' details-control')]//..//..//tr//td[3]"));
					for (WebElement childr : children2) {
						if (!docIds.contains(childr.getText().trim())) {
							docIds.add(childr.getText().trim());
						}
					}
					List<WebElement> innerChild = driver.getWebDriver().findElements(By.xpath(
							"//tr[contains(@class,'shown')]//..//tbody//td[contains(@class,' details-control')]//..//td[1]"));
					if (innerChild.size() != 0) {
						js.executeScript("arguments[0].scrollIntoView(true);", innerChild.get(0));
						js.executeScript("window.scrollBy(0,-250)");
						innerChild.get(0).click();
						List<WebElement> innerChild1 = driver.getWebDriver().findElements(By.xpath(
								"//tr[contains(@class,'shown')]//..//tbody//td[contains(@class,' details-control')]//../following-sibling::tr//tbody"));
						if (innerChild1.size() > 0) {
							List<WebElement> innerChild2 = driver.getWebDriver().findElements(By.xpath(
									"//tr[contains(@class,'shown')]//..//tbody//td[contains(@class,' details-control')]//../following-sibling::tr//tbody//tr//td[3]"));
							for (WebElement ele : innerChild2) {
								if (!docIds.contains(ele.getText().trim())) {
									docIds.add(ele.getText().trim());
								}
							}
							base.passedStep("Child documents and inner child documents are not repeated as expected");
							break;
						}
					}
				}

			}
			System.out.println("Documents with child and inner child are :: " + docIds);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying non of child nodes are repeated on DocList page"
					+ e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to select all documents from all pages and all children
	 *              of selected parents.
	 */
	public void selectingAllDocFromAllPagesAndAllChildren() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectAllDocCheckBox());
			base.waitTillElemetToBeClickable(getSelectAllDocCheckBox());
			getSelectAllDocCheckBox().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getAllPagesYesButton());
			base.waitTillElemetToBeClickable(getAllPagesYesButton());
			getAllPagesYesButton().Click();
			geChildrenYesButton().Click();
			getOkButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while selecting all documents from all pages and all children of selected parents."
							+ e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify that already opened parent is not closed by
	 *              opening new parent.
	 */
	public void verifyAlreadyOpenedParentNotClosedByOpeningNewParent() {
		try {
			int rowNum = 0;
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Thread.sleep(2000);

			List<WebElement> rows = driver.getWebDriver().findElements(By.xpath("//table[@id='dtDocList']//tbody//tr"));
			for (int row = 0; row < rows.size(); row++) {
				String rowChar = getParentRow(row).GetAttribute("class").trim();
				if (rowChar.equalsIgnoreCase((" details-control").trim())) {
					rowNum = row + 1;
					break;
				}
			}

			List<WebElement> parents = driver.getWebDriver().findElements(By.xpath("//td[@class=' details-control']"));
			for (int row = 0; row < 2; row++) {
				js.executeScript("arguments[0].scrollIntoView(true);", parents.get(row));
				js.executeScript("window.scrollBy(0,-250)");
				for (int i = 0; i < 15; i++) {
					try {
						Thread.sleep(1000);
						parents.get(row).click();
						break;
					} catch (Exception e) {
						js.executeScript("window.scrollBy(0,-250)");
					}
				}
			}
			js.executeScript("arguments[0].scrollIntoView(true);", getParentTableByRow(rowNum).getWebElement());
			js.executeScript("window.scrollBy(0,-250)");
			boolean flag = getParentTableByRow(rowNum).isElementPresent();
			if (flag)
				base.passedStep("Already opened parent is not closed by opening new parent as expected");
			else
				base.failedStep("Already opened parent is closed by opening new parent");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying that already opened parent is not closed by opening new parent."
							+ e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to Verify that user can view the Child Document on doc
	 *              list.
	 */
	public void verifyUserCanViewChildDocumetOnDocList() {
		try {
			int rowNum = 0;
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Thread.sleep(2000);

			List<WebElement> rows = driver.getWebDriver().findElements(By.xpath("//table[@id='dtDocList']//tbody//tr"));
			for (int row = 0; row < rows.size(); row++) {
				String rowChar = getParentRow(row).GetAttribute("class").trim();
				if (rowChar.equalsIgnoreCase((" details-control").trim())) {
					rowNum = row + 1;
					break;
				}
			}

			List<WebElement> parents = driver.getWebDriver().findElements(By.xpath("//td[@class=' details-control']"));
			js.executeScript("arguments[0].scrollIntoView(true);", parents.get(0));
			js.executeScript("window.scrollBy(0,-250)");
			for (int i = 0; i < 15; i++) {
				try {
					Thread.sleep(1000);
					parents.get(0).click();
					break;
				} catch (Exception e) {
					js.executeScript("window.scrollBy(0,-250)");
				}
			}

			try {
				js.executeScript("arguments[0].scrollIntoView(true);", getChildByRow(rowNum).getWebElement());
				js.executeScript("window.scrollBy(0,-250)");
				boolean flag = getChildByRow(rowNum).getWebElement().isDisplayed();
				if (flag)
					base.passedStep("Child Document for parent on doc list is displayed as expected");
			} catch (Exception e) {
				base.failedStep("Child Document for parent on doc list is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying that user can view the Child Document on doc list."
					+ e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to accepting bulk tag request on pop up.
	 * @param flag :: (flag is boolean value that for accepting or rejecting bulk
	 *             tag request)
	 */
	public void addBulkTagAcceptRequestOnPopup(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			if (flag) {
				base.waitForElement(bulkAddTagOkButton());
				base.waitTillElemetToBeClickable(bulkAddTagOkButton());
				bulkAddTagOkButton().Click();
				base.VerifySuccessMessage("Records saved successfully");
			} else if (flag == false) {
				base.waitForElement(bulkAddTagCancelButton());
				base.waitTillElemetToBeClickable(bulkAddTagCancelButton());
				bulkAddTagCancelButton().Click();
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.stepInfo("Bulk tag pop up is not appeared");

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to untag documents .
	 * @param tagName - (tagName is a String value that name of tag already
	 *                existed).
	 */
	public void unTagDocmuments(String tagName) {
		try {
			try {
				getPureHitAddButton().waitAndClick(10);
			} catch (Exception e) {
				// System.out.println("Pure hit block already moved to action panel");
				UtilityLog.info("Pure hit block already moved to action panel");
				Reporter.log("Pure hit block already moved to action panel", true);
			}
			getBulkActionButton().waitAndClick(10);
			base.waitForElement(getBulkFolderAction());
			base.waitTillElemetToBeClickable(getBulkFolderAction());
			getBulkTagAction().Click();
			base.waitForElement(getTagUntagDocumentsDialogBox());
			getTagUntagDocumentsDialogBox().Click();
			driver.Manage().window().fullscreen();
			base.waitForElement(unTagRadioButton());
			base.waitTillElemetToBeClickable(unTagRadioButton());
			unTagRadioButton().Click();
			base.waitForElement(getBulkUntagTree());
			base.waitTillElemetToBeClickable(getBulkUntagTree());
			getBulkUntagTree().Click();
			base.waitForElement(getExistingTagInUntagTable(tagName));
			base.waitTillElemetToBeClickable(getExistingTagInUntagTable(tagName));
			getExistingTagInUntagTable(tagName).Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getContinueButtonUntag());
			base.waitTillElemetToBeClickable(getContinueButtonUntag());
			for (int i = 0; i < 10; i++) {
				try {
					getContinueButtonUntag().Click();
					break;
				} catch (Exception e) {
					Thread.sleep(1000);
					driver.waitForPageToBeReady();
					base.waitForElement(getContinueButtonUntag());
					base.waitTillElemetToBeClickable(getContinueButtonUntag());
				}
			}
			base.VerifySuccessMessage("Records saved successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while untaging documents" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting all DocId .
	 */

	public void SelectIngAllDocuments() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectDocIdCheckBox());
			base.waitTillElemetToBeClickable(getSelectDocIdCheckBox());
			getSelectDocIdCheckBox().Click();
			base.waitForElement(getRadioBtn1());
			getRadioBtn1().Click();
			base.waitForElement(getRadioBtn2());
			getRadioBtn2().Click();
			base.waitForElement(getOkBtn());
			getOkBtn().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getDocList_actionButton());
			base.waitTillElemetToBeClickable(getDocList_actionButton());
			getDocList_actionButton().Click();
			base.waitForElement(getExportData());
			base.waitTillElemetToBeClickable(getExportData());
			base.waitTillElemetToBeClickable(getExportData());
			getExportData().Click();

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting all documents" + e.getMessage());

		}

	}

	/**
	 * @author Gopinath
	 * @Description :Selecting all File and run report .
	 */
	public void handlingExportDataPopUp() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectDocInCheckBox());
			getSelectDocInCheckBox().ScrollTo();
			base.waitTillElemetToBeClickable(getSelectDocInCheckBox());
			getSelectDocInCheckBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getAddToSelected());
			getAddToSelected().Click();
			base.waitForElement(getRunReport());
			getRunReport().ScrollTo();
			getRunReport().Click();
			base.waitForElement(getCloseExport());
			getCloseExport().Click();
			base.waitForElement(base.getSuccessMsg());
			base.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals(base.getSuccessMsg().getWebElement().isDisplayed(), true,
					"Success message is not displayed");
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
				base.passedStep("Added selected paginated document to folder successfully");
			}
			base.passedStep("Report Generated Successfully ");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured In Export Popup" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Modified by : Gopinath,Modified date : 01/10/2021
	 * @Description :Method for document selection.
	 * @param noOfDocuments : (noOfDocuments is integer value that number of
	 *                      documents need to select).
	 */

	public void documentSelection(int noOfDocuments) throws InterruptedException {
		try {
			for (int row = 0; row < noOfDocuments; row++) {

				driver.waitForPageToBeReady();
				base.waitForElement(getselectDoc(row + 1));
				getselectDoc(row + 1).waitAndClick(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to download selection documents" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath,Modified by: Gopinath,Modified date :: 01/10/2021
	 * @Description :Method for bulk assign.
	 */
	public void bulkassign() throws InterruptedException {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocList_actionButton().Visible();
				}
			}), Input.wait60);
			getDocList_actionButton().waitAndClick(10);
			Thread.sleep(Input.wait30 / 10);

			getDocList_action_BulkAssignButton().waitAndClick(10);

			base.waitForElement(getNewAssignment());
			getNewAssignment().Click();

			for (int i = 0; i < 7; i++) {
				try {
					driver.waitForPageToBeReady();
					base.waitForElement(getContinueBtn());
					base.waitTillElemetToBeClickable(getContinueBtn());
					getContinueBtn().Click();
					break;
				} catch (Exception e) {
					driver.waitForPageToBeReady();
					base.waitForElement(getContinueBtn());
					base.waitTillElemetToBeClickable(getContinueBtn());
				}
			}

			base.waitForElement(getDocCount());
			base.waitForElement(getFinalizeAssignment());
			getFinalizeAssignment().Click();
			driver.waitForPageToBeReady();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to perfrom bulk assign operation" + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description :Method for bulk assign and unassign document
	 * @param testing :: (testing is string value that name of assignment).
	 */
	public void bulkassignAndUnAssignTheDoc(String testing) throws InterruptedException {

		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocList_actionButton().Visible();
				}
			}), Input.wait60);
			getDocList_actionButton().waitAndClick(10);
			Thread.sleep(Input.wait30 / 10);

			getDocList_action_BulkAssignButton().waitAndClick(10);

			base.waitForElement(getUnAssignRadioBtn());
			getUnAssignRadioBtn().Click();

			base.waitForElement(getSelectCheckBox(testing));
			getSelectCheckBox(testing).ScrollTo();
			getSelectCheckBox(testing).Click();

			driver.waitForPageToBeReady();
			base.waitForElement(getCountOfDocs());

			base.waitForElement(getContinueBtn());
			getContinueBtn().Click();
			driver.waitForPageToBeReady();
			Thread.sleep(Input.wait30 / 10);
			SoftAssert softAssertion = new SoftAssert();
			String expectedText = "Success";
			String actualText = getStatusSuccessTxt().getText();
			System.out.println(actualText);
			softAssertion.assertTrue(actualText.contains(expectedText));
			base.passedStep("BulkUnAssign is Generated Successfully ");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to perfrom bulk assign operation" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Method for verifying applied date is shown in table doc list
	 *              page.
	 * @param fullDate :: (fullDate is a string value that date with month and
	 *                 year).
	 */
	public void dateVerificationForDocuments(String fullDate) {
		try {

			String[] fDate = fullDate.split("/", 2);
			String date = fDate[0];
			String[] fDate2 = fDate[1].split("/", 2);
			String month = fDate2[0];
			String year = fDate2[1];

			base.waitForElement(getMasterDate());
			getMasterDate().waitAndClick(10);
			base.waitForElement(getSetADateRange());
			getSetADateRange().Click();
			base.waitForElement(getSelectDateRangeDropDown());
			getSelectDateRangeDropDown().Click();
			base.waitForElement(getSelectADate());
			getSelectADate().Click();
			getDatePickerMonth().selectFromDropdown().selectByVisibleText(month);
			getDatePickerYear().selectFromDropdown().selectByVisibleText(year);
			base.waitForElement(getPickDate(date));
			getPickDate(date).Click();
			base.waitForElement(getAddtoFilter());
			base.waitTillElemetToBeClickable(getAddtoFilter());
			getAddtoFilter().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getApplyFilters());
			base.waitTillElemetToBeClickable(getApplyFilters());
			getApplyFilters().Click();
			String output = null;
			String visibleDate = null;
			driver.waitForPageToBeReady();
			String ApplyFilteDate = getActiveFilters().getText();
			System.out.print(ApplyFilteDate);
			String counts[] = ApplyFilteDate.split(" ");
			visibleDate = counts[2];
			if (getRowCount().size() == 0) {
				base.stepInfo("No documents are avaliable in doc list table on selected date :: " + visibleDate);
			} else if (getRowCount().size() != 0) {
				for (int D = 1; D <= getRowCount().size(); D++) {

					driver.waitForPageToBeReady();
					base.waitForElement(getAssertDate(D));
					String AssertDate = getAssertDate(D).getText();
					System.out.println(AssertDate);
					String nocounts[] = AssertDate.split(" ");
					output = nocounts[0];
					if (!output.equals(visibleDate)) {
						base.failedStep(
								"The Filter Date:" + output + " and  Document Date " + visibleDate + " are not same");
					}
				}

				base.passedStep("The Filter Date:" + output + " and Document Date" + visibleDate + " are same");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to perfrom bulk assign operation" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Method for verifying email author domain name.
	 */
	public void emailAuthorDomainNameVerificationInDoc() {

		try {
			base.waitForElement(SelectColumnBtn());
			SelectColumnBtn().waitAndClick(10);
			base.waitForElement(getSelectEmailAuthorDomain());
			getSelectEmailAuthorDomain().ScrollTo();
			getSelectEmailAuthorDomain().Click();
			base.waitForElement(getAddToSelect());
			getAddToSelect().Click();
			base.waitForElement(getUpdateColumnBtn());
			getUpdateColumnBtn().Click();
			base.waitForElement(getEmailAuthorDomainBth());
			getEmailAuthorDomainBth().waitAndClick(5);
			base.waitForElement(getIncludeBtn());
			getIncludeBtn().Click();
			base.waitForElement(getClickToMakeSelection());
			getClickToMakeSelection().Click();
			base.waitForElement(getSelectAuthor());
			getSelectAuthor().Click();
			String emailDomainName = getDomainAuthorName().getText().trim().replaceAll("[^a-zA-Z.]", "");
			base.waitForElement(getFilterToDomainName());
			getFilterToDomainName().Click();

			base.waitForElement(getApplyFilters());
			getApplyFilters().Click();

			driver.waitForPageToBeReady();
			base.waitForElement(getActiveFilter(emailDomainName));
			base.waitTillElemetToBeClickable(getActiveFilter(emailDomainName));
			boolean activeFilter = getActiveFilter(emailDomainName).isElementPresent();
			if (activeFilter) {
				base.passedStep("Active filter for email author domain by name: " + emailDomainName
						+ " is Present as Expected");
			} else {
				base.failedStep("Active filter for email author domain by name: " + emailDomainName
						+ " is not Present as Expected");
			}

			String AssertDate = null;
			for (int D = 1; D <= getRowCount().size(); D++) {

				driver.waitForPageToBeReady();
				base.waitForElement(getAssertEmaialAuthorDomain(D));
				AssertDate = getAssertEmaialAuthorDomain(D).getText();
				System.out.println(AssertDate);

				if (!AssertDate.contains(emailDomainName)) {
					base.failedStep("The Filter AuthorName:" + AssertDate + " and  Document AuthorName "
							+ emailDomainName + " are not same");
				}
			}

			base.passedStep(
					"The Filter AuthorName:" + AssertDate + " and Document AuthorName" + emailDomainName + " are same");
			driver.waitForPageToBeReady();
			base.waitForElement(getClearFilters());
			getClearFilters().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify email author domain name" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Method for verifying email author domain name by exclude in
	 *              doclist.
	 */
	public void emailAuthorDomainNameInExcludeVerificationInDoc() {

		try {
			base.waitForElement(getEmailAuthorDomainBth());
			getEmailAuthorDomainBth().waitAndClick(5);
			base.waitForElement(getExcludeBtn());
			getExcludeBtn().Click();
			base.waitForElement(getClickToMakeSelection());
			getClickToMakeSelection().Click();
			base.waitForElement(getSelectAuthor());
			getSelectAuthor().Click();
			String emailDomainName = getDomainAuthorName().getText().trim().replaceAll("[^a-zA-Z.]", "");
			base.waitForElement(getFilterToDomainName());
			getFilterToDomainName().Click();
			base.waitForElement(getApplyFilters());
			getApplyFilters().Click();
			String output = null;
			String AssertDate = null;
			for (int D = 1; D <= getRowCount().size(); D++) {

				driver.waitForPageToBeReady();
				base.waitForElement(getAssertEmaialAuthorDomain(D));
				output = getAssertEmaialAuthorDomain(D).getText();
				System.out.println("The Empty Column:" + AssertDate);

				if (output.equals(emailDomainName)) {
					base.failedStep(
							"The Filter AuthorName When Selecting Exclude is not equals to '" + emailDomainName + "'");
				}

			}
			base.passedStep("The Filter AuthorName When Selecting Exclude is equals to null");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify email author domain name by exclude" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to apply custodian name filter.
	 * @param custodianName : (custodianName is String value that name of
	 *                      custodian).
	 */
	public void applyCustodianNameFilter(String custodianName) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getCustodianNameFilter());
			base.waitTillElemetToBeClickable(getCustodianNameFilter());
			getCustodianNameFilter().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getFilterByCustodianHeader());
			base.waitForElement(getCustodianIncludeRadioBtn());
			base.waitTillElemetToBeClickable(getCustodianIncludeRadioBtn());
			getCustodianIncludeRadioBtn().Click();
			base.waitForElement(getCustodianTextField());
			base.waitTillElemetToBeClickable(getCustodianTextField());
			getCustodianTextField().SendKeys(custodianName);
			base.waitForElement(getCustodianOption(custodianName));
			base.waitTillElemetToBeClickable(getCustodianOption(custodianName));
			getCustodianOption(custodianName).Click();
			base.waitForElement(getAddToFilterButton());
			base.waitTillElemetToBeClickable(getAddToFilterButton());
			getAddToFilterButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to apply custodian name filter" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to verify applied include custodian filter is added.
	 * @param custodianName : (custodianName is String value that name of
	 *                      custodian).
	 */
	public void verifyAppliedIncludeCustodianNameFilterIsAdded(String custodianName) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getAppliedIncludeCustodianFilter(custodianName));
			base.waitTillElemetToBeClickable(getAppliedIncludeCustodianFilter(custodianName));
			Assert.assertEquals(getAppliedIncludeCustodianFilter(custodianName).getWebElement().isDisplayed(), true,
					"Applied include custodian filter is not added");
			if (getAppliedIncludeCustodianFilter(custodianName).Displayed()) {
				base.passedStep("Applied include custodian filter is added successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify applied include custodian filter is added" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to clear all applied filters.
	 */
	public void clearAllAppliedFilters() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getClearFilters());
			base.waitTillElemetToBeClickable(getClearFilters());
			getClearFilters().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to clear all applied filters" + e.getMessage());
		}
	}

	/**
	 * @Author : Baskar date: NA Modified date:N/A Modified by: Baskar
	 * @Description:this method used for doclist to release the document to SG
	 */

	public void docListToBulkRelease(String SecGroup) throws InterruptedException {
		base.waitForElement(getDocList_actionButton());
		getDocList_actionButton().waitAndClick(10);
		getDocList_action_BulkReleaseButton().waitAndClick(10);
		base.waitForElement(search.getBulkRelDefaultSecurityGroup_CheckBox(SecGroup));
		search.getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Click();
		base.waitForElement(search.getBulkRelease_ButtonRelease());
		search.getBulkRelease_ButtonRelease().Click();
		base.waitForElement(search.getFinalizeButton());
		search.getFinalizeButton().waitAndClick(30);
		base.VerifySuccessMessage("Records saved successfully");
		System.out.println("performing bulk release");
	}

	/**
	 * @author Gopinath
	 * @Description :Comparing Email all domains name with table Email domain name .
	 */
	public void EmailAllDomainsNameVerificationInDoc() {

		try {
			base.waitForElement(SelectColumnBtn());
			base.waitTillElemetToBeClickable(SelectColumnBtn());
			SelectColumnBtn().waitAndClick(10);
			base.waitForElement(getSelectEmailAllDomains());
			base.waitTillElemetToBeClickable(getSelectEmailAllDomains());
			getSelectEmailAllDomains().ScrollTo();
			getSelectEmailAllDomains().Click();
			base.waitForElement(getAddToSelect());
			base.waitTillElemetToBeClickable(getAddToSelect());
			getAddToSelect().Click();
			base.waitForElement(getUpdateColumnBtn());
			base.waitTillElemetToBeClickable(getUpdateColumnBtn());
			getUpdateColumnBtn().Click();
			base.waitForElement(getEmailAllDomainsBtn());
			base.waitTillElemetToBeClickable(getEmailAllDomainsBtn());
			getEmailAllDomainsBtn().waitAndClick(5);
			base.waitForElement(getIncludeBtn());
			base.waitTillElemetToBeClickable(getIncludeBtn());
			getIncludeBtn().Click();
			base.waitForElement(getClickToMakeSelection());
			base.waitTillElemetToBeClickable(getClickToMakeSelection());
			getClickToMakeSelection().Click();
			base.waitForElement(getEmailAllDomainSelectAuthor());
			base.waitTillElemetToBeClickable(getEmailAllDomainSelectAuthor());
			getEmailAllDomainSelectAuthor().Click();
			String emailDomainName = getDomainAuthorName().getText().trim().replaceAll("[^a-zA-Z.]", "");
			System.out.println(emailDomainName);
			base.waitForElement(getEmailAllDomainsApplyFilter());
			base.waitTillElemetToBeClickable(getEmailAllDomainsApplyFilter());
			getEmailAllDomainsApplyFilter().Click();
			base.waitForElement(getApplyFilters());
			getApplyFilters().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getActiveFilterInEmailALLDomain(emailDomainName));
			base.waitTillElemetToBeClickable(getActiveFilterInEmailALLDomain(emailDomainName));
			boolean activeFilter = getActiveFilterInEmailALLDomain(emailDomainName).isElementPresent();
			if (activeFilter) {
				base.passedStep(
						"Active filter for email all domains by name: " + emailDomainName + " is Present as Expected");
			} else {
				base.failedStep("Active filter for email all domains by name: " + emailDomainName
						+ " is not Present as Expected");
			}

			String AssertDate = null;
			for (int D = 1; D <= getRowCount().size(); D++) {

				driver.waitForPageToBeReady();
				base.waitForElement(getAssertEmailAllDomains(D));
				base.waitTillElemetToBeClickable(getAssertEmailAllDomains(D));
				AssertDate = getAssertEmailAllDomains(D).getText();
				System.out.println("The expected text:" + AssertDate);
				if (!AssertDate.contains(emailDomainName)) {
					base.failedStep("The Filtered Emailalldomain:  " + emailDomainName
							+ "  and  Document AuthorName contains " + AssertDate + "  are not same");
				}
			}

			base.passedStep("The Filtered Emailalldomain: " + emailDomainName + "  and Document AuthorName "
					+ AssertDate + "  are same");
			driver.waitForPageToBeReady();
			base.waitForElement(getClearFilters());
			base.waitTillElemetToBeClickable(getClearFilters());
			getClearFilters().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to comparing Email all domains name with table Email domain name" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :The filtered alldomains when selecting exclude is not equals to
	 *              author name .
	 */
	public void EmailAllDomainsNameInExcludeVerificationInDoc() {
		try {
			base.waitForElement(getEmailAllDomainsBtn());
			base.waitTillElemetToBeClickable(getEmailAllDomainsBtn());
			getEmailAllDomainsBtn().waitAndClick(5);

			base.waitForElement(getExcludeBtn());
			base.waitTillElemetToBeClickable(getExcludeBtn());
			getExcludeBtn().Click();
			base.waitForElement(getClickToMakeSelection());
			base.waitTillElemetToBeClickable(getClickToMakeSelection());
			getClickToMakeSelection().Click();
			base.waitForElement(getEmailAllDomainSelectAuthor());
			base.waitTillElemetToBeClickable(getEmailAllDomainSelectAuthor());
			getEmailAllDomainSelectAuthor().Click();
			String emailDomainName = getDomainAuthorName().getText().trim().replaceAll("[^a-zA-Z.]", "");
			base.waitForElement(getEmailAllDomainsApplyFilter());
			base.waitTillElemetToBeClickable(getEmailAllDomainsApplyFilter());
			getEmailAllDomainsApplyFilter().Click();
			base.waitForElement(getApplyFilters());
			getApplyFilters().Click();
			String output = null;
			String AssertDate = null;
			for (int D = 1; D <= getRowCount().size(); D++) {

				driver.waitForPageToBeReady();
				base.waitForElement(getAssertEmailAllDomains(D));
				base.waitTillElemetToBeClickable(getAssertEmailAllDomains(D));
				output = getAssertEmailAllDomains(D).getText();
				System.out.println("The Empty Column:" + AssertDate);

				if (output.equals(emailDomainName)) {
					base.failedStep(
							"The Filtered AllDomain When Selecting Exclude is not equals to " + emailDomainName + "");
				}
			}
			base.passedStep("The Filtered AllDomain When Selecting Exclude is not equals to" + emailDomainName + " ");
		} catch (Exception e) {

			e.printStackTrace();
			base.failedStep("Failed to filtered alldomains when selecting exclude is not equals to author name"
					+ e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Comparing Email author name with table Email author name .
	 */
	public void EmailAuthorNameVerificationInDoc() {
		try {
			base.waitForElement(SelectColumnBtn());
			SelectColumnBtn().waitAndClick(10);
			base.waitForElement(getSelectEmailAuthorName());
			base.waitTillElemetToBeClickable(getSelectEmailAuthorName());
			getSelectEmailAuthorName().ScrollTo();
			getSelectEmailAuthorName().Click();
			base.waitForElement(getAddToSelect());
			base.waitTillElemetToBeClickable(getAddToSelect());
			getAddToSelect().Click();
			base.waitForElement(getUpdateColumnBtn());
			base.waitTillElemetToBeClickable(getUpdateColumnBtn());
			getUpdateColumnBtn().Click();

			base.waitForElement(getEmailAuthorNameBtn());
			base.waitTillElemetToBeClickable(getEmailAuthorNameBtn());
			getEmailAuthorNameBtn().waitAndClick(5);
			base.waitForElement(getIncludeBtn());
			base.waitTillElemetToBeClickable(getIncludeBtn());
			getIncludeBtn().Click();
			base.waitForElement(getClickToMakeSelection());
			base.waitTillElemetToBeClickable(getClickToMakeSelection());
			getClickToMakeSelection().Click();

			base.waitForElement(getEmailAuthorNameSelectAuthor());
			base.waitTillElemetToBeClickable(getEmailAuthorNameSelectAuthor());
			getEmailAuthorNameSelectAuthor().Click();
			String emailDomainName = getDomainAuthorName().getText().trim().replaceAll("[^a-zA-Z.@]", "");
			System.out.println(emailDomainName);
			base.waitForElement(getEmailAuthorNameApplyFilter());
			base.waitTillElemetToBeClickable(getEmailAuthorNameApplyFilter());
			getEmailAuthorNameApplyFilter().Click();
			base.waitForElement(getApplyFilters());
			getApplyFilters().Click();

			driver.waitForPageToBeReady();
			base.waitForElement(getActiveFilterInEmailAuthorName(emailDomainName));
			base.waitTillElemetToBeClickable(getActiveFilterInEmailAuthorName(emailDomainName));
			boolean activeFilter = getActiveFilterInEmailAuthorName(emailDomainName).isElementPresent();
			if (activeFilter) {
				base.passedStep(
						"Active filter for email all domains by name: " + emailDomainName + " is Present as Expected");
			} else {
				base.failedStep("Active filter for email all domains by name: " + emailDomainName
						+ " is not Present as Expected");
			}

			String AssertDate = null;
			for (int D = 1; D <= getRowCount().size(); D++) {

				driver.waitForPageToBeReady();
				base.waitForElement(getAssertEmailAuthorName(D));
				base.waitTillElemetToBeClickable(getAssertEmailAuthorName(D));
				AssertDate = getAssertEmailAuthorName(D).getText();
				System.out.println("The expected text:" + AssertDate);
				if (!AssertDate.contains(emailDomainName)) {
					base.failedStep("The Filtered Emailalldomain:  " + emailDomainName
							+ "  and  Document AuthorName contains " + AssertDate + "  are not same");
				}
			}

			base.passedStep("The Filtered Emailalldomain: " + emailDomainName + "  and Document AuthorName "
					+ AssertDate + "  are same");
			driver.waitForPageToBeReady();
			base.waitForElement(getClearFilters());
			base.waitTillElemetToBeClickable(getClearFilters());
			getClearFilters().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to comparing Email author name with table Email author name" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :The filtered Email author name when selecting exclude is not
	 *              equals to author name .
	 */
	public void EmailAuthorNameInExcludeVerificationInDoc() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getEmailAuthorNameBtn());
			base.waitTillElemetToBeClickable(getEmailAuthorNameBtn());
			getEmailAuthorNameBtn().waitAndClick(5);
			base.waitForElement(getExcludeBtn());
			base.waitTillElemetToBeClickable(getExcludeBtn());
			getExcludeBtn().Click();
			base.waitForElement(getClickToMakeSelection());
			base.waitTillElemetToBeClickable(getClickToMakeSelection());
			getClickToMakeSelection().Click();
			base.waitForElement(getEmailAuthorNameSelectAuthor());
			base.waitTillElemetToBeClickable(getEmailAuthorNameSelectAuthor());
			getEmailAuthorNameSelectAuthor().Click();
			String emailDomainName = getDomainAuthorName().getText().trim().replaceAll("[^a-zA-Z.@]", "");
			System.out.println(emailDomainName);
			base.waitForElement(getEmailAuthorNameApplyFilter());
			base.waitTillElemetToBeClickable(getEmailAuthorNameApplyFilter());
			getEmailAuthorNameApplyFilter().Click();
			base.waitForElement(getApplyFilters());
			base.waitTillElemetToBeClickable(getApplyFilters());
			getApplyFilters().Click();
			String output = null;
			String AssertDate = null;
			for (int D = 1; D <= getRowCount().size(); D++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getAssertEmailAuthorName(D));
				base.waitTillElemetToBeClickable(getAssertEmailAuthorName(D));
				output = getAssertEmailAuthorName(D).getText();
				System.out.println("The Empty Column:" + AssertDate);
				if (output.equals(emailDomainName)) {
					base.failedStep(
							"The Filtered AllDomain When Selecting Exclude is not equals to " + emailDomainName + "");
				}
			}
			base.passedStep("The Filtered AllDomain When Selecting Exclude is not equals to" + emailDomainName + " ");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to filtered Email author name  when selecting exclude is not equals to author name "
					+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting the documents .
	 */
	public void selectingAllDocuments() {
		try {
			base.waitForElement(getSelectallDocuments());
			getSelectallDocuments().waitAndClick(5);
			base.waitForElement(getSelectPopupBtn());
			getSelectPopupBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while Selecting the documents" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting unfolder structure in bulkfolder and selecting the
	 *              folder .
	 * @param Test : (Test is String value that name of folder).
	 */
	public void selectingUnFoldersAndVerifyingTheDocCount(String Test) throws InterruptedException {

		try {

			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getDocList_actionButton());
			base.waitTillElemetToBeClickable(getDocList_actionButton());
			getDocList_actionButton().Click();
			base.waitForElement(getselect_bulkFolder());
			getselect_bulkFolder().Click();
			base.waitForElement(getUnFolderRadioBtn());
			base.waitTillElemetToBeClickable(getUnFolderRadioBtn());
			getUnFolderRadioBtn().Click();
			base.waitForElement(getSelectFolderCheckBox(Test));
			getSelectFolderCheckBox(Test).ScrollTo();
			getSelectFolderCheckBox(Test).Click();
			driver.waitForPageToBeReady();
			base.waitForElement(gettotalSelectedDocuments());
			base.waitForElement(getContinueCount());
			base.waitTillElemetToBeClickable(getContinueCount());
			getContinueCount().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception is occured while unfoldering the documents" + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method for bulk unrelease of selected documents.
	 * @param securityGroupName : securityGroupName is the string value that name of
	 *                          security need to release documents.
	 */
	public void bulkUnRelease(String securityGroupName) {
		try {
			base.waitForElement(getDocList_actionButton());
			Thread.sleep(Input.wait30 / 10);
			base.waitTillElemetToBeClickable(getDocList_actionButton());
			getDocList_actionButton().waitAndClick(10);
			getDocList_action_BulkReleaseButton().waitAndClick(10);
			base.waitForElement(search.getBulkRelDefaultSecurityGroup_CheckBox(securityGroupName));
			search.getBulkRelDefaultSecurityGroup_CheckBox(securityGroupName).Click();
			base.waitForElement(getUnreleaseButton());
			base.waitTillElemetToBeClickable(getUnreleaseButton());
			getUnreleaseButton().Click();
			base.VerifySuccessMessage("Records saved successfully");
			base.passedStep("performed bulk unrelease successfully");
			System.out.println("performed bulk unrelease successfully");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception is occured while bulk unreleasing of selected documents" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath
	 * @Description : Suffling the column in doclist page.
	 */
	public List<String> suffleTheColumnInDocListPage(String ColName, String Colvalue) throws InterruptedException {
		List<String> value = new ArrayList<String>();
		try {
			driver.Navigate().refresh();
			Thread.sleep(Input.wait30 / 10);
			List<WebElement> allvalues = getColumnCount().FindWebElements();
			List<WebElement> NextColumnValue = getNextColumnCount().FindWebElements();
			System.out.println(allvalues.size());
			int j;
			String counts;
			List<String> NextColData = new ArrayList<String>();
			for (j = 0; j < allvalues.size(); j++) {
				driver.waitForPageToBeReady();
				value.add(allvalues.get(j).getText());
				NextColData.add(NextColumnValue.get(j).getText());
			}

			System.out.println("before:" + value);
			System.out.println("before Rowcount:" + NextColData);
			driver.waitForPageToBeReady();
			Actions act = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			act.dragAndDrop(getCustodianName().getWebElement(), getDocID().getWebElement()).build().perform();
			driver.waitForPageToBeReady();

			List<WebElement> RowCount = getRowValueAfterSuffling().FindWebElements();
			List<String> Rowcounts = new ArrayList<String>();
			for (j = 0; j < RowCount.size(); j++) {
				driver.waitForPageToBeReady();
				Rowcounts.add(RowCount.get(j).getText());
			}
			System.out.println("Another:" + Rowcounts);

			if (Rowcounts.equals(value)) {
				base.passedStep("The Column in doclist page is suffled successfully");
			} else {
				base.failedStep("The column value in doclist page is  not suffled successfully");
			}

			driver.waitForPageToBeReady();
			String ColumnValue = getTableHeader().getText();
			if (ColumnValue.equalsIgnoreCase(ColName)) {
				base.passedStep("The " + ColumnValue + " is suffled succssfully");
			} else {
				base.failedStep("The " + ColumnValue + " is not suffled succssfully");
			}
			Thread.sleep(Input.wait30 / 10);
			driver.waitForPageToBeReady();
			act.dragAndDrop(getDocFileType().getWebElement(), getDocFileName().getWebElement()).build().perform();
			driver.waitForPageToBeReady();

			List<WebElement> NextRowCount = getNextColumnCountAfterSuffling().FindWebElements();
			List<String> Rowcount = new ArrayList<String>();
			for (j = 0; j < NextRowCount.size(); j++) {
				driver.waitForPageToBeReady();
				Rowcount.add(NextRowCount.get(j).getText());
			}
			System.out.println("After suffled:" + Rowcount);
			if (Rowcount.equals(NextColData)) {
				base.passedStep("The Column in doclist page is suffled successfully");
			} else {
				base.failedStep("The column value in doclist page is  not suffled successfully");
			}
			driver.waitForPageToBeReady();
			String NextColumnValues = getNextTableHeader().getText();

			if (NextColumnValues.equalsIgnoreCase(Colvalue)) {
				base.passedStep("The " + NextColumnValues + " is suffled succssfully");
			} else {
				base.failedStep("The " + NextColumnValues + " is not suffled succssfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception is occured while suffling the column in doclist page" + e.getMessage());
		}
		return value;
	}

	/**
	 * @author Gopinath
	 * @Description :Removing the column header and verifying
	 */

	public void verifyingColumnInDocListPage(String DocHeader) {
		try {
			List<WebElement> allvalue = getColumnHeader().FindWebElements();
			System.out.println(allvalue.size());
			int j;
			String counts;
			List<String> value = new ArrayList<String>();
			for (j = 2; j < allvalue.size(); j++) {
				System.out.println(allvalue.get(j).getText());
				counts = allvalue.get(j).getText();
				value.add(allvalue.get(j).getText());
			}
			System.out.println(value);
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			base.waitForElement(SelectColumnBtn());
			base.waitTillElemetToBeClickable(SelectColumnBtn());
			driver.waitForPageToBeReady();
			SelectColumnBtn().Click();
			base.waitForElement(getRemoveLink());
			getRemoveLink().Click();
			base.waitForElement(getUpdateColumnBtn());
			getUpdateColumnBtn().Displayed();
			getUpdateColumnBtn().Click();
			String expectedtext = DocHeader;
			List<WebElement> allvalues = getColumnHeader().FindWebElements();
			System.out.println(allvalues.size());
			List<String> values = new ArrayList<String>();
			for (j = 2; j < allvalues.size(); j++) {

				counts = allvalues.get(j).getText();
				values.add(allvalues.get(j).getText());
				System.out.println(values);
			}
			if (values.contains(expectedtext)) {
				base.failedStep("The Column is not hid");

			} else {
				base.passedStep("The column is successfully hid");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while removing doc header ." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting the document and verifying whether it is selected.
	 */
	public void selectingCheckboxAndVerifyingTheDocSelection() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectDocIdCheckBox());
			base.waitTillElemetToBeClickable(getSelectDocIdCheckBox());
			getSelectDocIdCheckBox().Click();
			base.waitForElement(getRadioBtn1());
			base.waitTillElemetToBeClickable(getRadioBtn1());
			getRadioBtn1().Click();
			base.waitForElement(getOkBtn());
			getOkBtn().Click();
			List<WebElement> NextRowCount = getRowCountforDocList().FindWebElements();
			int j;
			System.out.println(NextRowCount.size());
			for (j = 1; j <= NextRowCount.size(); j++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getselectDoc(j));
				getselectDoc(j).Enabled();
				if (!getselectDoc(j).Enabled()) {
					base.failedStep("The documents in the doclist page is not selected");
				}
			}
			base.passedStep("The documents in the checkbox is selected");
			driver.waitForPageToBeReady();
			base.waitForElement(getLastRow());
			base.waitTillElemetToBeClickable(getLastRow());
			getLastRow().ScrollTo();
			getLastRow().Click();
			Thread.sleep(Input.wait30 / 10);
			List<WebElement> lastPage = getRowCountforDocList().FindWebElements();
			for (j = 0; j < lastPage.size(); j++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getselectDoc(j + 1));
				base.waitTillElemetToBeClickable(getselectDoc(j + 1));
				boolean eleEnabled = getselectDoc(j + 1).Enabled();
				if (eleEnabled == false) {
					base.failedStep("The documents in the doclist page is not selected");
				}
				driver.waitForPageToBeReady();
			}
			base.passedStep("The documents in the doclist last page  is selected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting the Documents ." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting the document and verifying whether it is selected.
	 */
	public void DeselectTheRadioBtnAndVerifyingTheDocCheckbox() {
		try {
			driver.Navigate().refresh();
			Thread.sleep(Input.wait30 / 10);
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectDocIdCheckBox());
			base.waitTillElemetToBeClickable(getSelectDocIdCheckBox());
			getSelectDocIdCheckBox().Click();
			base.waitForElement(getOkBtn());
			getOkBtn().Click();
			Thread.sleep(Input.wait30 / 10);
			List<WebElement> NextRowCount = getRowCountforDocList().FindWebElements();
			int j;
			for (j = 1; j <= NextRowCount.size(); j++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getselectDoc(j));
				base.waitTillElemetToBeClickable(getselectDoc(j));
				getselectDoc(j).Selected();
				if (!getselectDoc(j).Enabled()) {
					base.failedStep("The documents in the doclist page is not selected");
				}
			}
			base.passedStep("The documents in the doclist is selected");
			base.waitForElement(getLastRow());
			Thread.sleep(Input.wait30 / 30);
			getLastRow().ScrollTo();
			getLastRow().Click();
			driver.waitForPageToBeReady();
			List<WebElement> lastPage = getRowCountforDocList().FindWebElements();
			for (j = 1; j <= lastPage.size(); j++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getselectDoc(j));
				base.waitTillElemetToBeClickable(getselectDoc(j));
				getselectDoc(j).ScrollTo();
				getselectDoc(j).Selected();
				if (getselectDoc(j).Selected()) {
					base.failedStep("The documents in the doclist last page is selected");
				}
			}
			base.passedStep("The documents in the doclist last page is not selected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting the Documents ." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting Yes and verifying child document is selected or not.
	 */
	public void SelectDropDownAndVerifyDocCheckbox() throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			driver.Navigate().refresh();
			base.waitForElement(getSelectDropDown());
			base.waitTillElemetToBeClickable(getSelectDropDown());
			getSelectDropDown().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectDocIdCheckBox());
			base.waitTillElemetToBeClickable(getSelectDocIdCheckBox());
			getSelectDocIdCheckBox().Click();
			base.waitForElement(getOkButton());
			base.waitTillElemetToBeClickable(getOkButton());
			getOkButton().Click();
			base.waitForElement(getParentDocument());
			base.waitTillElemetToBeClickable(getParentDocument());
			getParentDocument().ScrollTo();
			getParentDocument().Enabled();
			if (getParentDocument().Enabled()) {
				base.passedStep("The documents in the parent checkbox is selected");
			} else {
				base.failedStep("The parent documents in the   doclist page is not selected");
			}
			base.waitForElement(getSelectParentDocument());
			base.waitTillElemetToBeClickable(getSelectParentDocument());
			getSelectParentDocument().Click();

			int j;
			List<WebElement> lastPage = getChildDocumentRowCount().FindWebElements();
			for (j = 1; j <= lastPage.size(); j++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getChildDocumentCheckBox(j));
				base.waitTillElemetToBeClickable(getChildDocumentCheckBox(j));
				getselectDoc(j).Selected();
				getselectDoc(j).ScrollTo();
				System.out.println(getselectDoc(j).Enabled());
				if (!getselectDoc(j).Enabled()) {
					base.failedStep("The child documents in the doclist page is not selected");
				}
			}
			base.passedStep("The child Documents in the doclist page is selected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while selecting yes and verifying the child Documents ." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting No and verifying child document is selected or not.
	 */
	public void SelectDropDownAndCancelItVerifyDocCheckbox() throws InterruptedException {
		try {
			driver.Navigate().refresh();
			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getSelectDropDown());
			base.waitTillElemetToBeClickable(getSelectDropDown());
			getSelectDropDown().Click();
			driver.waitForPageToBeReady();
			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getSelectDocIdCheckBox());
			base.waitTillElemetToBeClickable(getSelectDocIdCheckBox());
			getSelectDocIdCheckBox().Click();
			base.waitForElement(getCancelButton());
			base.waitTillElemetToBeClickable(getCancelButton());
			getCancelButton().Click();
			base.waitForElement(getParentDocument());
			base.waitTillElemetToBeClickable(getParentDocument());
			getParentDocument().ScrollTo();
			if (getParentDocument().Enabled()) {
				base.passedStep("The Parent checkbox is selected");
			} else {
				base.failedStep("The parent checkbox is not selected");
			}

			base.waitForElement(getSelectParentDocument());
			getSelectParentDocument().Click();

			int j;
			List<WebElement> lastPage = getChildDocumentRowCount().FindWebElements();
			for (j = 1; j <= lastPage.size(); j++) {
				driver.waitForPageToBeReady();
				getChildDocumentCheckBox(j).ScrollTo();
				;
				base.waitForElement(getChildDocumentCheckBox(j));
				base.waitTillElemetToBeClickable(getChildDocumentCheckBox(j));
				getselectDoc(j).Enabled();
				if (getselectDoc(j).Selected()) {
					base.failedStep("The documents in the checkbox is selected");
				}
			}
			base.passedStep("The documents in the doclist page is not selected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while selecting No and verifying the child Documents ." + e.getMessage());
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param ele
	 */
	public void SelectColumnDisplay(Element ele) {

		try {
			base.waitForElement(SelectColumnBtn());
			SelectColumnBtn().waitAndClick(10);
			base.waitForElement(ele);
			ele.ScrollTo();
			ele.Click();
			base.waitForElement(getAddToSelect());
			getAddToSelect().waitAndClick(10);
			base.waitForElement(getUpdateColumnBtn());
			getUpdateColumnBtn().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param emailAuthourName
	 * @param includeStatus
	 */
	public void emailAuthorNameVerificationWithOutFilter(String emailAuthourName, boolean includeStatus) {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getRowCount());
		String AssertAuthourName = null;
		for (int D = 1; D <= getRowCount().size(); D++) {
			driver.waitForPageToBeReady();
			base.waitForElement(getAssertEmailAuthorName(D));
			AssertAuthourName = getAssertEmailAuthorName(D).getText();
			System.out.println(AssertAuthourName);
			if (includeStatus == true) {
				if (!AssertAuthourName.toLowerCase().contains(emailAuthourName)) {
					base.failedStep("The Filter AuthorName:" + AssertAuthourName + " and  Document AuthorName "
							+ emailAuthourName + " are not same");
				}
				base.passedStep("The Filter AuthorName:" + AssertAuthourName + " and Document AuthorName"
						+ emailAuthourName + " are same");
				base.passedStep("Result set include all emails Authour Name that are part of the selected Tags.");
			}
			if (includeStatus == false) {
				if (AssertAuthourName.contains(emailAuthourName)) {
					base.failedStep("The Filter AuthorName:" + AssertAuthourName + " and  Document AuthorName "
							+ emailAuthourName + " are  same");
				}
				base.passedStep("The Filter AuthorName:" + AssertAuthourName + " and Document AuthorName"
						+ emailAuthourName + " are not same");
				base.passedStep("Result set Exclude all emails Authour Name that are part of the selected Tags.");
			}

		}

	}

	/**
	 * @Author : Baskar date: NA Modified date:N/A Modified by: Baskar
	 * @Description:This method used to get the document text in doclist first page
	 */

	public LinkedList<String> VerifyDocsInAscendingorder() {
		driver.waitForPageToBeReady();
		List<WebElement> ascending = getDocIds().FindWebElements();
		LinkedList<String> docIds = new LinkedList<String>();
		for (int i = 0; i < ascending.size(); i++) {
			String s = ascending.get(i).getText();
			docIds.add(s);
		}
		return docIds;
	}

	/**
	 * @Author : Baskar date: NA Modified date:N/A Modified by: Baskar
	 * @Description:This method used to navigate to docview page from doclist page
	 */

	public void docListToDocView() {
		base.waitForElement(getDocList_Action_Drp_Dwn());
		getDocList_Action_Drp_Dwn().waitAndClick(5);
		base.waitForElement(getDocListViewInDocView());
		getDocListViewInDocView().waitAndClick(5);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting Parent document
	 */
	public void selectingParentDocument() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectDropDown());
			getSelectDropDown().Click();
			driver.waitForPageToBeReady();
			// getSelectParentDocument().ScrollTo();
			driver.waitForPageToBeReady();
			base.waitForElement(getParentDocument());
			getParentDocument().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getMessageBoxYesBtn());
			getMessageBoxYesBtn().Click();
			driver.scrollPageToTop();
		}

		catch (Exception e) {
			base.failedStep("Exception occcured while removing doc header ." + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath
	 * @Description :Selecting Child document
	 */

	public void selectingChildDocument() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitForElement(getSelectDropDown());
			getSelectDropDown().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getChildDocument());
			getChildDocument().Click();
			driver.scrollPageToTop();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while removing doc header ." + e.getMessage());
		}
	}

	/**
	 * @author Brundha
	 * @Description :Verify Doclist sorting after shuffling the columns
	 */

	@SuppressWarnings("unchecked")
	public void sufflingColumnValueInDocListPage() {
		try {
			driver.waitForPageToBeReady();
			driver.Navigate().refresh();
			Actions act = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			act.dragAndDrop(getCustodianName().getWebElement(), getDocID().getWebElement()).build().perform();
			driver.waitForPageToBeReady();

			List<WebElement> RowCount = getColumnCount().FindWebElements();
			int j;
			List<String> Rowcounts = new ArrayList<String>();
			for (j = 0; j < RowCount.size(); j++) {
				driver.waitForPageToBeReady();
				Rowcounts.add(RowCount.get(j).getText());
			}
			System.out.println("Column value:" + Rowcounts);

			List temp = new ArrayList();
			temp.addAll(Rowcounts);
			Collections.sort(temp);
			System.out.println("Aftering sorting the column value is:" + temp);

			if (Rowcounts.equals(temp)) {
				base.passedStep("" + Rowcounts + " and " + temp + "is in ascending order as expected");
			} else {
				base.failedStep("" + Rowcounts + "and " + temp + " is not in ascending order as expected");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while removing doc header ." + e.getMessage());
		}
	}

	/**
	 * @author Brundha
	 * @Description :suffling and verifying it in descending order
	 */

	@SuppressWarnings("unchecked")
	public void verifyingDescendingOrderSortingInColumn() {
		try {
			List<WebElement> RowCount = getNextColumnCountAfterSuffle().FindWebElements();
			int j;
			List<String> Rowcounts = new ArrayList<String>();
			for (j = 0; j < RowCount.size(); j++) {
				driver.waitForPageToBeReady();
				String row = RowCount.get(j).getText();
				String nocounts[] = row.split("/");
				String output = nocounts[0];
				Rowcounts.add(output);
				// System.out.println(output);

			}

			System.out.println("The data in the column is:" + Rowcounts);
			List temp = new ArrayList();
			temp.addAll(Rowcounts);
			Collections.sort(temp);
			System.out.println("ascending:" + temp);
			Collections.reverse(temp);
			System.out.println("Descending:" + temp);

			if (Rowcounts.equals(temp)) {
				base.passedStep("" + Rowcounts + " and " + temp + "is in descending order as expected");
			} else {
				base.failedStep("" + Rowcounts + "and " + temp + " is not in descending order as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Brundha
	 * @Description :removing column in doclist page.
	 * 
	 */
	public void removeColumn() {
		try {
			base.waitForElement(SelectColumnBtn());
			base.waitTillElemetToBeClickable(SelectColumnBtn());
			driver.waitForPageToBeReady();
			SelectColumnBtn().Click();
			base.waitForElement(getRemove());
			getRemove().Click();
			base.waitForElement(getUpdateColumnBtn());
			getUpdateColumnBtn().Displayed();
			getUpdateColumnBtn().Click();

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while removing doc header ." + e.getMessage());
		}
	}

	/**
	 * @author Brundha
	 * @Description :addding column in doclist page.
	 * 
	 */
	public void addColumn() {
		try {
			base.waitForElement(SelectColumnBtn());
			SelectColumnBtn().waitAndClick(10);
			base.waitForElement(getSelectEmailAuthorDomain());
			getSelectEmailAuthorDomain().ScrollTo();
			getSelectEmailAuthorDomain().Click();
			base.waitForElement(getAddToSelect());
			getAddToSelect().Click();
			base.waitForElement(getUpdateColumnBtn());
			getUpdateColumnBtn().Click();
			String expectedtext = "EMAILAUTHORDOMAIN";
			List<WebElement> allvalues = getColumnHeader().FindWebElements();
			System.out.println(allvalues.size());
			List<String> values = new ArrayList<String>();
			int j;
			for (j = 2; j < allvalues.size(); j++) {
				String counts;
				counts = allvalues.get(j).getText();
				values.add(allvalues.get(j).getText());
				System.out.println(values);
			}
			if (values.contains(expectedtext)) {

				base.passedStep("" + expectedtext + " column is successfully added");

			} else {
				base.failedStep("" + expectedtext + " Column is not added successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while adding doc header ." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description :filling bulkfolder in doclistpage .
	 * @param Test : (Test is String value that name of folder).
	 */
	public void bulkFolderInDocListPage(String Test) {
		try {
			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getDocList_actionButton());
			base.waitTillElemetToBeClickable(getDocList_actionButton());
			getDocList_actionButton().waitAndClick(10);
			base.waitForElement(getselect_bulkFolder());
			getselect_bulkFolder().waitAndClick(10);
			base.waitForElement(gettotalSelectedDocuments());
			base.waitForElement(getNewFolderBtn());
			getNewFolderBtn().Click();
			base.waitForElement(getSelectAllFoldersOption());
			base.waitTillElemetToBeClickable(getSelectAllFoldersOption());
			getSelectAllFoldersOption().Click();
			base.waitForElement(getSelectAllFolderBtn());
			base.waitTillElemetToBeClickable(getSelectAllFolderBtn());
			getSelectAllFolderBtn().Click();
			base.waitForElement(getEnterName());
			getEnterName().SendKeys(Test);
			base.waitForElement(getSelect_continue_btn());
			getSelect_continue_btn().waitAndClick(10);
			System.out.println("Click continue");
			base.waitTillElemetToBeClickable(getFinalizeButton());
			base.waitForElement(getFinalizeButton());
			getFinalizeButton().Click();
			base.VerifySuccessMessage("Records saved successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception is occured while Handling Bulkfolder in doclistPage" + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description :download the doc and verify.
	 */

	public void DownloadingTheFile() {

		try {
			base.waitForElement(getSelctActivity());
			getSelctActivity().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getViewAllBtn());
			getViewAllBtn().waitAndClick(5);
			;
			for (int i = 0; i < 20; i++) {
				driver.waitForPageToBeReady();
				Thread.sleep(Input.wait30 / 30);
				String status = getCompletedStatus().getWebElement().getText();
				if (status.equalsIgnoreCase("COMPLETED")) {
					driver.waitForPageToBeReady();
					base.waitForElement(getSelectDownloadFileLnk());
					base.waitTillElemetToBeClickable(getSelectDownloadFileLnk());
					getSelectDownloadFileLnk().Click();
					break;
				} else {
					driver.waitForPageToBeReady();
					driver.Navigate().refresh();
				}
			}
			String mainWindow = driver.getWebDriver().getWindowHandle();

			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			js.executeScript("window.open()");
			// switch to new tab
			// Switch to new window opened
			for (String winHandle : driver.getWebDriver().getWindowHandles()) {
				driver.getWebDriver().switchTo().window(winHandle);
			}
			// navigate to chrome downloads
			driver.get("chrome://downloads");
			Thread.sleep(Input.wait30 / 10);
			// get the latest downloaded file name
			String fileName = (String) js.executeScript(
					"return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
			if (fileName.contains("CDR011")) {
				base.passedStep(" file downloded by name :: " + fileName + " as expected");
			} else {
				base.failedStep(" file downloded by name :: " + fileName + " is not as expected");
			}
			driver.getWebDriver().close();
			driver.getWebDriver().switchTo().window(mainWindow);
		} catch (Exception e) {

			e.printStackTrace();
			base.failedStep("Failed to download the file" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for selecting doc file size of document.
	 * @param fileSize        : fileSize is the string value that size of document.
	 * @param docFileSizeName : docFileSizeName is the string value that doc file
	 *                        size column in table.
	 */
	public void selectDocFileSize(String fileSize, String docFileSizeName) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(SelectColumnBtn());
			SelectColumnBtn().waitAndClick(10);
			List<WebElement> allvalues = getCorelist().FindWebElements();
			System.out.println(allvalues.size());
			int j;
			String counts;
			List<String> value = new ArrayList<String>();
			for (j = 0; j < allvalues.size(); j++) {
				System.out.println(allvalues.get(j).getText());
				counts = allvalues.get(j).getText();
				value.add(allvalues.get(j).getText());
			}
			if (value.contains(docFileSizeName)) {

				base.waitForElement(getUpdateColumnBtn());
				getUpdateColumnBtn().Click();

			} else {
				base.waitForElement(getDocFileSize());
				base.waitTillElemetToBeClickable(getDocFileSize());
				getDocFileSize().ScrollTo();
				getDocFileSize().Click();
				base.waitForElement(getAddToSelect());
				base.waitTillElemetToBeClickable(getAddToSelect());
				getAddToSelect().Click();
				base.waitForElement(getUpdateColumnBtn());
				base.waitTillElemetToBeClickable(getUpdateColumnBtn());
				getUpdateColumnBtn().Click();
			}

			String DocFileSize = getDocFileSizeColumn().getText();
			System.out.println("" + DocFileSize + " column is displayed");
			if (getDocFileSizeColumn().Displayed()) {
				base.passedStep("" + DocFileSize + " is Displayed");
			} else {
				base.failedStep("" + DocFileSize + " is not  Displayed");
			}
			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getSelectDocFileSize());
			base.waitTillElemetToBeClickable(getSelectDocFileSize());
			getSelectDocFileSize().waitAndClick(10);
			base.waitForElement(getSetAFileSizeRange());
			base.waitTillElemetToBeClickable(getSetAFileSizeRange());
			getSetAFileSizeRange().Click();
			base.waitForElement(getSelectBelow());
			base.waitTillElemetToBeClickable(getSelectBelow());
			getSelectBelow().Click();
			base.waitTillElemetToBeClickable(getAddFileSize());
			getAddFileSize().Click();
			getAddFileSize().SendKeys(fileSize);
			base.waitForElement(getAddToFilterBtn());
			base.waitTillElemetToBeClickable(getAddToFilterBtn());
			getAddToFilterBtn().Click();
			base.waitTillElemetToBeClickable(getApplyFilters());
			base.waitForElement(getApplyFilters());
			getApplyFilters().Click();
			driver.waitForPageToBeReady();
			getDocFileSizeAssertion().Displayed();
			String DocFileSizeInActiveFilters = getDocFileSizeAssertion().getText();
			String[] doccount = DocFileSizeInActiveFilters.split(" ");
			String DocFileSizeInActiveFilter = doccount[2];

			driver.waitForPageToBeReady();
			String output = null;
			for (int D = 1; D <= getRowCount().size(); D++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getRowCountOfDocFileSize(D));
				output = getRowCountOfDocFileSize(D).getText();

				System.out.println("Doc File size:" + output);
				if (Integer.valueOf(output) < Integer.valueOf(DocFileSizeInActiveFilter)) {
					base.passedStep("The " + DocFileSizeInActiveFilter + "is less than " + output + "");
				} else {
					base.failedStep("The " + DocFileSizeInActiveFilter + "is not  less than " + output + "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception is occured while selecting doc file size of document" + e.getMessage());
		}
	}
	/**
	 * @author Brundha
	 * @Description  : Method for verifying doccount
	 */
	
	public String verifyingDocCount() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getDocList_info());
	    String DocListCount=getDocList_info().getText();
		
		String[] doccount = DocListCount.split(" ");
		String DocumentCount = doccount[5];
		System.out.println("doclist page document count is"+DocumentCount);
		
		driver.scrollPageToTop();
		base.waitForElement(getBackToSourceBtn());
		getBackToSourceBtn().Click();
		driver.waitForPageToBeReady();
		return DocumentCount;
		
		
	}
	
	/**
	 * @author Gopinath,Modified by: Gopinath,Modified date :: 01/10/2021
	 * @Description :Method for bulk assign with persistant.
	 */
	public void bulkAssignWithPersistantHit(String assignmentName) throws InterruptedException {
		try {
			driver.scrollPageToTop();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocList_actionButton().Visible();
				}
			}), Input.wait60);
			getDocList_actionButton().isElementAvailable(15);
			getDocList_actionButton().waitAndClick(10);
			Thread.sleep(Input.wait30 / 10);
			getDocList_action_BulkAssignButton().isElementAvailable(10);
			getDocList_action_BulkAssignButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			UtilityLog.info("performing bulk assign");
			driver.waitForPageToBeReady();
			
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectAssignmentExisting(assignmentName).Visible();
				}
			}), Input.wait60);
			driver.waitForPageToBeReady();
			getSelectAssignmentExisting(assignmentName).Click();
			getPersistantHitCheckBox().isElementAvailable(15);
			getPersistantHitCheckBox().Click();
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			base.waitTillElemetToBeClickable(getContinueButton());
			getContinueButton().waitAndClick(20);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
				}
			}), Input.wait30);
			getFinalizeButton().isElementAvailable(15);
			getFinalizeButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to perfrom bulk assign operation" + e.getMessage());
		}

	}
	
}
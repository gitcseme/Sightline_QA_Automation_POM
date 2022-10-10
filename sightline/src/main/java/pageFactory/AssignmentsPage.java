package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class AssignmentsPage {

	Driver driver;
	Element element;
	BaseClass bc;
	SoftAssert assertion;
	SessionSearch search;
	LoginPage lp;
	KeywordPage keywordPage;

	public Element getAssignmentActionDropdown() {
		return driver.FindElementByXPath("//*[@id='ulActions']/../button[@class='btn btn-defualt dropdown-toggle']");
	}

	public Element getAssignmentAction_NewAssignment() {
		return driver.FindElementByXPath("//*[@id='ulActions']//a[text()='New Assignment']");
	}

	public Element getAssignmentAction_EditAssignment() {
		return driver.FindElementByXPath("//*[@id='ulActions']//a[text()='Edit Assignment']");
	}

	public Element getAssignmentAction_DeleteAssignment() {
		return driver.FindElementByXPath("//*[@id='ulActions']//a[text()='Delete Assignment']");
	}

	public Element getAssignmentName() {
		return driver.FindElementById("AssignmentName");
	}

	public Element getAssignmentNameInDocView() {
		return driver.FindElementById("assignmentName");
	}

	public Element getsampleMethod() {
		return driver.FindElementById("sampleMethod");
	}

	public Element getCountToAssign() {
		return driver.FindElementById("txtCountToAssign");
	}

	public Element getParentAssignmentGroupName() {
		return driver.FindElementById("ParentassignmentGroupName");
	}

	public Element getSelectedClassification() {
		return driver.FindElementById("SelectedClassification");
	}

	public Element getAssignmentCodingFormDropDown() {
		return driver.FindElementById("SelectedCodingForm");
	}

	public Element getAssignmentCodingFormName() {
		return driver.FindElementById("selectedCodingFormString");
	}

	public Element getAssignmentSaveButton() {
		return driver.FindElementByXPath("//input[@value='Save']");
	}

	public Element getSelectAssignmentToBulkAssign(String assignmentName) {
		return driver.FindElementByXPath("//*[@id='jstreeComplete']//a[text()='" + assignmentName + "']");
	}

	public Element getStartingCount() {
		return driver.FindElementByXPath("(//div[@class='col-md-3 bulkActionsSpanLoderTotal'])[2]");
	}

	public Element getContinueBulkAssign() {
		return driver.FindElementByXPath("//*[@id='divBulkAction']//button[contains(.,'Continue')]");
	}

	public Element getFinalCount() {
		return driver.FindElementByXPath("//span[@id='spanTotal']");
	}

	public Element getFinalizeButton() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	public Element getNumberOfAssignmentsToBeShown() {
		return driver.FindElementByXPath("//*[@id='GridAssignment_length']/label/select");
	}

	public Element getSelectAssignment(String assignmentName) {
		return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[td='" + assignmentName + "']");
	}

	// change this name to pop or smtg
	public Element getAssignment_Actionlikepopup() {
		return driver.FindElementByXPath("//span[@class='ui-dialog-title']/div");
	}

	public Element getAssignment_ManageReviewersTab() {
		return driver.FindElementByXPath("//*[contains(text(),'Manage Reviewers')]");
	}

	public Element getSelectAssignmentDocCount(String assignmentName) {
		return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[td='" + assignmentName + "']/td[7]");
	}

	public Element getAssgnCounts(String assignmentName, int colno) {
		return driver.FindElementByXPath(
				"//*[@id='GridAssignment']/tbody//tr[td='" + assignmentName + "']/td[" + colno + "]");
	}

	public Element getPersistCB_ExistAssgn() {
		return driver.FindElementByXPath("//div[@id='existingassignment']//label[@class='checkbox']/i");
	}

	public Element getPersistCB_NewAssgn() {
		return driver.FindElementByXPath("//div[@id='newassignmentdiv']//label[@class='checkbox']/i");
	}

	public Element getSelectSavePermission() {
		return driver.FindElementByXPath("(//label[@class='toggle'])[23]");
	}

	// Assign users to assignment
	public Element getAddReviewersBtn() {
		return driver.FindElementById("btnShowPopup");
	}

	public Element getSelectUserToAssign() {
		return driver.FindElementByXPath(
				"//*[@id='divNotAssignedUsers']//div[contains(.,'" + Input.rmu2userName + "')]/../div/label");
	}

	public Element getAdduserBtn() {
		return driver.FindElementById("btnAddReviewer");
	}

	public Element getDistributeTab() {
		return driver.FindElementById("ui-id-3");
	}

	public Element getSelectUserInDistributeTab() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.rmu2userName + "')]/div/label");
	}

	public Element getDistributeBtn() {
		return driver.FindElementById("btnDistribute");
	}

	public Element getBulkAssign_NewAssignment() {
		return driver.FindElementByXPath("//a[text()='New Assignment']");
	}

	// added by shilpi on 04-01
	public Element getAssignmentAction_ViewinDocView() {
		return driver.FindElementByXPath("//a[text()='View All Docs In DocView']");
	}

	public Element getSelectAssignmentAsReviewer(String assignmentName) {
		return driver.FindElementByXPath(".//*[@id='dt_basic']//strong[contains(.,'" + assignmentName + "')]");
	}

	public Element getAssignmentAction_ViewinDocList() {
		return driver.FindElementByXPath("//a[text()='View All Docs in DocList']");
	}

	// added on 06/03
	public Element getAssignment_Edit_DisplayDocumentHistory() {
		return driver.FindElementByXPath(".//*[@id='CascadingDivision']//label[contains(.,'Document History Tab')]/i");
	}

	public Element getAssignment_Edit_ApplyRedactionbyreviewer() {
		return driver.FindElementByXPath(
				".//*[@id='CascadingDivision']//label[contains(.,'Allow reviewers to apply redactions')]/i");
	}

	public Element getAssignment_Edit_CodingStampApplied() {
		return driver.FindElementByXPath(".//*[@id='CascadingDivision']//label[contains(.,'Coding Stamp Applied')]/i");
	}

	public Element getAssignment_BackToManageButton() {
		return driver.FindElementByXPath("//a[contains(.,'Back to Manage')]");
	}

	// added by shilpi on 8/5/2019
	public Element getAssgn_DocSequenceblock() {
		return driver.FindElementById("doc-seq");
	}

	public Element getAssgn_DocSequencelabel() {
		return driver.FindElementByXPath("//*[@id='doc-seq']/div[1]/label");
	}

	public Element getAssgn_DocSequence_SortbyMetadata() {
		return driver.FindElementByXPath("//a[contains(text(),'Sort by Metadata')]");
	}

	public Element getAssgn_DocSequence_Selectmetadata() {
		return driver.FindElementById("ddlMetaDataField");
	}

	public Element getAssgn_ManageRev_Action() {
		return driver.FindElementById("btnDropdown");
	}

	public Element getAssgn_ManageRev_Action_ViewDocview() {
		return driver.FindElementById("View in Doc View");
	}

	public Element getAssgn_ManageRev_Action_Unassignuser() {
		return driver.FindElementById("Unassign User");
	}

	public Element getAssgn_ManageRev_Action_removedoc() {
		return driver.FindElementById("Remove Documents");
	}

	public Element getAssgn_ManageRev_Action_redistributedoc() {
		return driver.FindElementById("Redistribute Documents");
	}

	public Element getAssgn_ManageRev_Action_tagdoc() {
		return driver.FindElementById("Tag Documents");
	}

	public Element getAssgn_ManageRev_Action_folderdoc() {
		return driver.FindElementById("Folder Documents");
	}

	public Element getAssgn_ManageRev_Action_ViewDoclist() {
		return driver.FindElementById("View in Doc List");
	}

	public ElementCollection getDashboadAssgn() {
		return driver.FindElementsByXPath("//*[@id='dt_basic']/tbody/tr");
	}

	public Element getAssgn_ManageRev_selectrev() {
		return driver.FindElementByXPath(".//*[@id='dt_basic']//td[contains(.,'" + Input.rev1userName + "')]");
	}

	public Element getAssgnAction_Export() {
		return driver.FindElementById("rbnExport");
	}

	public Element getAssgn_LikeDoc_Family() {
		return driver.FindElementByXPath("//*[@class='light-bg toggleSwitch']//div[contains(text(),'Family Members')]");
	}

	public Element getAssgn_LikeDoc_Email() {
		return driver.FindElementByXPath("//*[@class='light-bg toggleSwitch']//div[contains(text(),'Email Threaded')]");
	}

	public Element getAssgn_LikeDoc_Near() {
		return driver.FindElementByXPath("//*[@class='light-bg toggleSwitch']//div[contains(text(),'Near Duplicate')]");
	}

	public Element getAssgGrptionDropdown() {
		return driver.FindElementByXPath("//*[@id='collapseOne']//button[@class='btn btn-defualt dropdown-toggle']");
	}

	public Element getAssgnGrp_Create() {
		return driver.FindElementById("creategroup");
	}

	public Element getAssgnGrp_Edit() {
		return driver.FindElementById("editgroup");
	}

	public Element getAssgnGrp_Delete() {
		return driver.FindElementById("deletegroup");
	}

	public Element getAssgnGrp_Select(String assgname) {
		return driver.FindElementByXPath("//a[contains(text(),'" + assgname + "')]");
	}

	public Element getAssgnGrp_Clicked(String assgname) {
		return driver.FindElementByXPath(
				"//a[contains(text(),'" + assgname + "') and  @class='jstree-anchor jstree-clicked']");
	}

	public Element getAssgnGrp_Create_DrawPooltoggle() {
		return driver.FindElementByXPath("//*[@id='IsDrawFromPool']/following-sibling::i");
	}

	public Element getAssgnGrp_Create_DisplayAnalyticstoggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsShowAnalyticsPanel']/following-sibling::i");
	}

	public Element getAssgnGrp_Create_DrawPoolCount() {
		return driver.FindElementById("DrawFromPoolCount");
	}

	public Element getAssgnGrp_Create_Keepfamilycount() {
		return driver.FindElementById("KeepFamiliesTogetherCount");
	}

	public Element getAssgn_DocSequenceblock_avacriteria(int i) {
		return driver.FindElementByXPath("//*[@class='dd-list']/li[" + i + "]");
	}

	public Element getAssgn_DocSequenceblock_LiveSeq() {
		return driver.FindElementByXPath(".//*[@class='dd-empty']");
	}

	public Element getbulkassgnpopup() {
		return driver.FindElementByXPath("//*[text()='Assign/Unassign Documents']");
	}

	public Element getSelect2ndUserToAssign() {
		return driver.FindElementByXPath(
				"//*[@id='divNotAssignedUsers']//div[contains(.,'" + Input.rev1userName + "')]/../div/label");
	}

	public Element getSelect2ndUserInDistributeTab() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.rev1userName + "')]/div/label");
	}

	public Element getEditAggn_Distribute_Totaldoc() {
		return driver.FindElementById("lblTotalDocuments");
	}

	public Element getEditAggn_Distribute_Unassgndoc() {
		return driver.FindElementById("lblUnAssignedDoc");
	}

	public Element getAssgn_ManageRev_DisDoc(int rowno) {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]/td[" + rowno + "]");
	}

	public Element getAssgnnameErrormsg() {
		return driver.FindElementByCssSelector(".field-validation-error span");
	}

	public Element getUnassignbutton() {
		return driver.FindElementByXPath("//*[@id='toUnassign']/following-sibling::i");
	}

	public Element getAssignmentAction_Completedoc() {
		return driver.FindElementByXPath("//a[contains(text(),'Complete All Documents')]");
	}

	public Element getAssgn_Redistributepopup() {
		return driver.FindElementByXPath("//*[@name='Users']/following-sibling::i");
	}

	public Element getAssgn_Redistributepopup_save() {
		return driver.FindElementById("btnShareSave");
	}

	public Element getAssgn_DocSequence_avacrtiera(int no) {
		return driver.FindElementByCssSelector(".dd-list li:nth-of-type(" + no + ")");
	}

	public Element getAssgn_DocSequence_liveseq() {
		return driver.FindElementByCssSelector(".live-seq");
	}

	public Element getAssignmentAction_CopyAssignment() {
		return driver.FindElementById("CopyAssignmentText");
	}

	public Element getSelectCopyAssignment() {
		return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[contains(.,'Copy')]/td[1]");
	}

	public ElementCollection getSelectcopyAssgnmToBulkAssign() {
		return driver.FindElementsByXPath("//*[@id='jstreeComplete']//a[starts-with(.,'')]");
	}

	public Element getAssgn_permissions(int no) {
		return driver.FindElementByXPath("(//label[@class='toggle'])[" + no + "]//i");
	}

	public Element getAssgngrp_CascadeSetting() {
		return driver.FindElementByXPath(".//*[@id='IsCascadeEnabled']/following-sibling::i");
	}

	public Element getrev_assgnprogress(String assignmentName) {
		return driver.FindElementById(
				".//td[contains(.,'" + assignmentName + "')]//following::span[starts-with(@id,'AssignmentProgress')]");
	}

	public Element getrev_batchprogress(String assignmentName) {
		return driver.FindElementById(".//td[contains(.,'" + assignmentName
				+ "')]//following::div[starts-with(@class,'progress-bar bg-color-green')]");
	}
	// public Element getAssignmentAction_Completedoc(){ return
	// driver.FindElementById("CompleteAllDocuments"); }

	// Quick Batch added by shilpi
	public Element getQuickBatchpopup() {
		return driver.FindElementByXPath("//span[contains(text(),'Quick Batch')]");
	}

	// public Element getQuickBatch_optimizedorder(){ return
	// driver.FindElementByXPath("//*[@id='optimized']/following-sibling::i"); }
	public Element getQuickBatch_chornologicorder() {
		return driver.FindElementByXPath(".//*[@id='chronological']/following-sibling::i");
	}

	public Element getQuickBatch_Doccount() {
		return driver.FindElementByXPath("//label[text()='Selected Documents']/preceding-sibling::div");
	}

	public Element getQuickBatch_Selectallrev() {
		return driver.FindElementByXPath("//*[@id='chkAll']/following-sibling::i");
	}

	public Element getAssgn_LikeDoc_Familytoggle() {
		return driver.FindElementByXPath("//*[@id='chkIncludeFamilyMemeber']/following-sibling::i");
	}

	public Element getAssgn_LikeDoc_Emailtoggle() {
		return driver.FindElementByXPath("//*[@id='chkIncludeEmailThreads']/following-sibling::i");
	}

	public Element getAssgn_LikeDoc_Neartoggle() {
		return driver.FindElementByXPath("//*[@id='chkIncludeNearDuplicates']/following-sibling::i");
	}

	public Element getSelectUserToAssign(String userName) {
		return driver.FindElementByXPath("//*[@id='divNotAssignedUsers']//div[2][contains(.,'" + userName + "')]");
	}

	public Element getQuickBatch_NameErrormsg() {
		return driver.FindElementByXPath("//*[@id='assignmentName']/span/span");
	}

	public Element getQuickBatch_CodingErrormsg() {
		return driver.FindElementByXPath("//*[@id='codingForm']/span/span");
	}

	public Element getQuickBatch_optimizedorder() {
		return driver.FindElementById("optimized");
	}

	public Element getAssgn_ManageRev_revdoccount() {
		return driver.FindElementByXPath(
				".//*[@id='dt_basic']//td[contains(.,'" + Input.rev1userName + "')]/following-sibling::td[2]");
	}

	// Keywords element added by shilpi
	public Element getAssgn_Keywordsbutton() {
		return driver.FindElementById("btnKeywords");
	}

	public Element getAssgn_Keywordspopup() {
		return driver.FindElementById("divkeyword");
	}

	public ElementCollection getAssgn_AllKeywords() {
		return driver.FindElementsByXPath("//*[@id='assKeywordsListDiv']//div/div[1]");
	}

	public ElementCollection getAssgn_AllKeywordscheck() {
		return driver.FindElementsByName("assKeywordsList");
	}

	public Element getAssgn_Keywordokbutton() {
		return driver.FindElementById("keywordOK");
	}

	// Label Toggle

	// replaced by krishna-baskar for consolidation
	public Element getAssgn_DrawFromPoolToggle() {
		return driver.FindElementByXPath("//input[@id='IsDrawFromPool']/following-sibling::i");
	}

	public Element getAssgn_DocumentHistoryTabToggle() {
		return driver
				.FindElementByXPath("//*[@id='AdditionalPreferences_IsExposeDocumentHistory']/following-sibling::i");
	}

	public Element getAssgn_SaveWithoutCompleteingToggle() {
		return driver.FindElementByXPath(
				"//*[@id='AdditionalPreferences_IsAllowSaveWithoutCompletion']/following-sibling::i");
	}

	public Element getAssgn_ApplyRedactionToggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsEnableRedactions']/following-sibling::i");
	}

	public Element getAssgn_CodingStampAplliedToggle() {
		return driver.FindElementByXPath("//*[@id='grayoutToggle']/following-sibling::i");
	}

	public Element getAssgn_OverrideOptimizedSortToggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsAllowManualSort']/following-sibling::i");
	}

	public Element getAssgn_PrintDocsTOPdfToggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsAllowPDFPrinting']/following-sibling::i");
	}

	public Element getAssgn_DownloadNativesToggle() {
		return driver
				.FindElementByXPath("//*[@id='AdditionalPreferences_IsAllowNativeDownloads']/following-sibling::i");
	}

	public Element getAssgn_AnalyticsPanelToggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsShowAnalyticsPanel']/following-sibling::i");
	}

	public Element getAssgn_AddReviewer() {
		return driver.FindElementByXPath("//div//button[text()='Add Reviewers']");
	}

	public Element getSelectUserToAssig() {
		return driver.FindElementByXPath(
				"//*[@id='divNotAssignedUsers']//div[contains(.,'" + Input.rev1userName + "')]/../div/label");
	}

	public Element getSelectUserInDistributeTabs() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.rev1userName + "')]/div/label");
	}

	// krishna
	public Element getAssgn_NewAssignmnet() {
		return driver.FindElementById("tabnewAssignment");
	}

	public Element getAssgn_AssignmentCountToggle() {
		return driver.FindElementById(" assignmentDocCount");
	}

	public Element getAssgn_DocumnetCount() {
		return driver.FindElementByXPath("//input[@id='DrawFromPoolCount']");
	}

	// Pagination
	public Element getAssgn_Pagination() {
		return driver.FindElementByXPath("//div[@id='GridAssignment_paginate']//li//a[text()='Next']");
	}

	// count of Doc
	public Element getAssgn_TotalCount() {
		return driver.FindElementByXPath("//div[@id='divBulkAction']//div//span[@id='spanTotal']");
	}

	public Element getAssgnGrp_Create_DisplayAnalyticstoggleDisable() {
		return driver.FindElementByXPath(
				"//*[@id='AdditionalPreferences_IsShowEmailThreadMaptabAnalyticsPanel']/following-sibling::i");
	}

	// ReviewerManager Assignment
	public Element getSelectUserToAssigReviewerManager() {
		return driver.FindElementByXPath(
				"//*[@id='divNotAssignedUsers']//div[contains(.,'" + Input.rmu1userName + "')]/../div/label");
	}

	public Element getSelectUserInDistributeTabsReviewerManager() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.rmu1userName + "')]/div/label");
	}

	public Element getSelectUserToRedistributeDocsToReviewer() {
		return driver.FindElementByXPath("//*[@id='dt_basic']//tr//td[contains(text(),'Indium ReviewerManager')]");
	}

	// added by Jayanthi-Indium--23/8/21
	public Element getArrowBtn_AssgnGroup(String assgnGroupName) {
		return driver.FindElementByXPath("(//a[contains(text(),'" + assgnGroupName + "')]/parent::li//i)[1]");
	}

	public Element getMoveNotificationMessage() {
		return driver.FindElementByXPath("//p[@class='pText']");
	}

	public Element getCascadeSettingsText() {
		return driver.FindElementByXPath("//strong[@class='text-success']");
	}

	public ElementCollection getAssgnPaginationCount() {
		return driver.FindElementsByCssSelector("li[class*='paginate_button '] a");
	}

	public Element getAssgnPaginationNextButton() {
		return driver.FindElementByCssSelector("li[class='paginate_button next'] a");
	}

	public ElementCollection getAllAssgnReviewers() {
		return driver.FindElementsByXPath("//div[@class='modal-body']//div[@class='col-md-11']");
	}

	public Element getAssgnReviewers(int index) {
		return driver.FindElementByXPath("(//div[@class='modal-body']//div[@class='col-md-11'])[" + index + "]");
	}

	public Element dashBoardPageTitle() {
		return driver.FindElementByXPath("//h1[@class='page-title']");
	}

	public Element assgnInDashBoardPg(String assignmentName) {
		return driver.FindElementByXPath("//strong[text()='" + assignmentName + "']");
	}

	public Element completeBtn() {
		return driver.FindElementById("btnDocumentComplete");
	}

	public Element unCompleteBtn() {
		return driver.FindElementById("btnDocumentUnComplete");
	}

	public Element getTotalRecords() {
		return driver.FindElementById("totalRecords");
	}

	public Element dashBoardBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-lg fa-fw fa-home']");
	}

	public Element completeStatusBtn(String assignmentName) {
		return driver.FindElementByXPath("//strong[text()='" + assignmentName
				+ "']/ancestor::tr[@role='row']//following::td/div[@class='progress-lg']/div[@class='progress-bar bg-color-green'][@style='width: 100%']");
	}

	public Element configureButton() {
		return driver.FindElementById("btnConfigure");
	}

	public Element instructionTextBox() {
		return driver.FindElementByCssSelector("div[class='redactor-editor']");
	}

	public Element instructionOkButton() {
		return driver.FindElementByCssSelector("button[id='insOK']");
	}

	public Element getInstructionTextButton(String assignmentName) {
		return driver.FindElementByXPath(
				"//strong[text()='" + assignmentName + "']/ancestor::td//a[@onclick='showInstructions(this);']");
	}

	public Element getInstructionText() {
		return driver.FindElementByCssSelector("div[class='redactor-editor']");
	}

	public Element SaveButton() {
		return driver.FindElementByCssSelector("input[class='btn btn-primary save-btn']");
	}

	public Element metaDataSort() {
		return driver.FindElementByXPath("//a[text()='Sort by Metadata']/parent::li[@aria-selected='true']");
	}

	public Element sortBymetaDataBtn() {
		return driver.FindElementByXPath("//a[text()='Sort by Metadata']");
	}

	public Element SelectMetadataFromDropDown(String dropdownName) {
		return driver
				.FindElementByXPath("//select[@name='DocPresentationSorting.SelectedMetaDataFieldId']//option[text()='"
						+ dropdownName + "']");
	}

	public Element SelectMetadata() {
		return driver.FindElementByCssSelector("select[name='DocPresentationSorting.SelectedMetaDataFieldId']");
	}

	public Element SelectCodingFormCheckBoxes(String value) {
		return driver.FindElementByXPath("//span[text()='" + value + "']/ancestor::div/label");
	}

	public Element codingFormRadioButton() {
		return driver.FindElementByCssSelector("input[id='13_radio'][checked='checked']");
	}

	public Element getGearIcon() {
		return driver.FindElementByXPath("//a[@id='miniDocListConfig']");
	}

	public ElementCollection metaDatasInSelectedFields_reviewerPg() {
		return driver.FindElementsByCssSelector("ul[id='sortable2PickColumns'] i[class='fa fa-times-circle']");
	}

	public Element metaDatasRemoveBtn_reviewerPg() {
		return driver.FindElementByXPath("(//ul[@id='sortable2PickColumns']//i[@class='fa fa-times-circle'])[1]");
	}

	public Element getMetaData_reviewerPg(String metadata) {
		return driver.FindElementByXPath("//i[@class='fa fa-bars handle' and @value='" + metadata + "']");
	}

	public Element selectedMetaDataFields_reviewerPg() {
		return driver.FindElementById("sortable2PickColumns");
	}

	public Element saveBtn_reviewerPg() {
		return driver.FindElementByXPath("//button[@class='btn btn-primary']");
	}

	public ElementCollection metaDataslist_reviewerPg() {
		return driver.FindElementsByXPath("//div[@id='SearchDataTable_wrapper']//tr[@role='row']/td[position()=2]");
	}

	public Element getmetaDataName_reviewerPg(int index) {
		return driver.FindElementByXPath(
				"(//div[@id='SearchDataTable_wrapper']//tr[@role='row']/td[position()=2])[" + index + "]");
	}

	public Element viewAllDocListBtn_reviewerPg() {
		return driver.FindElementById("btnShowAllInDocList");
	}

	public ElementCollection commonMetaDataslist_DocListreviewerPg() {
		return driver.FindElementsByXPath("//table[@id='dtDocList']//tr[@role='row']/td[position()=4]");
	}

	public Element getcommonMetaDataName_DocListreviewerPg(int index) {
		return driver.FindElementByXPath("(//table[@id='dtDocList']//tr[@role='row']/td[position()=4])[" + index + "]");
	}

	public Element getDocCountInAssgnPg(String assignmentName) {
		return driver.FindElementByXPath("//td[text()='" + assignmentName + "']/ancestor::tr//td[position()=7]");
	}

	public Element getSelectNumberOfDocList() {
		return driver.FindElementById("idPageLength");
	}

	public Element getInstuctionPopup_FormattingToolBar() {
		return driver.FindElementByXPath("//div//ul[@class='redactor-toolbar']");
	}

	public Element DocCountToggle_OFF() {
		return driver.FindElementByXPath("//i[@id='assignmentDocCount' and @class]");
	}

	public Element getRootBTN() {
		return driver.FindElementByXPath("//a[@class='jstree-anchor jstree-clicked']");
	}

	public ElementCollection getFamilyMemeberCnt_InDocList() {
		return driver.FindElementsByXPath("// *[@id='SearchDataTable']//tr['row']/td[5]");
	}

	public Element getUsersListInRedistrubutePopup() {
		return driver.FindElementByXPath("//table[@id='my-table']//tbody//td[2]");
	}

	public Element getRandomRevUserInDistributeTab(String i) {
		return driver
				.FindElementByXPath("(//*[@id='divDistributedDocUsers']//div/div/label[@class='checkbox'])[" + i + "]");
	}

	public Element getselectRandomUserInManageTab(String i) {
		return driver.FindElementByXPath("(//*[@id='divNotAssignedUsers']//../div/label)[" + i + "]");
	}

	public Element getDistributedUserNameInDisTab() {
		return driver.FindElementByXPath(
				"((//div[@id='divDistributedDocUsers']//label[@class='checkbox']/parent::div)/parent::div//div/following-sibling::div)[2]");
	}

	public Element getAssgn_ManageRev_selectReviewer(String Username) {
		return driver.FindElementByXPath(".//*[@id='dt_basic']//td[contains(.,'" + Username + "')]");
	}

	public Element getAssgn_ManageRev_cancelBtn() {
		return driver.FindElementByXPath("//div//button[@class='btn']");
	}

	public Element getAssgn_RedistributeDoc() {
		return driver.FindElementByXPath("//li[@id='Redistribute Documents']");
	}

	public Element liveSequenceSorts(String Value) {
		return driver.FindElementByXPath("//h2[text()='Live Sequence']/following::span[text()='" + Value + "']/i");
	}

	public Element availableCriteriaField() {
		return driver.FindElementByCssSelector("div[id='nestable']");
	}

	public Element availableCriteriadropablefield() {
		return driver.FindElementByXPath("(//i[@class='fa fa-align-justify'])[1]");
	}

	public ElementCollection getAllDocIds_DocListreviewerPg() {
		return driver.FindElementsByXPath("//table[@id='dtDocList']//tr[@role='row']/td[position()=3]");
	}

	public Element getDocId_DocListreviewerPg(int index) {
		return driver.FindElementByXPath("(//table[@id='dtDocList']//tr[@role='row']/td[position()=3])[" + index + "]");
	}

	public Element getErrorMsg_NumberToAssignTxtBox() {
		return driver.FindElementByXPath("//label[@id='lblCountToAssign']");
	}

	public ElementCollection getSampleMethodsAllOptions() {
		return driver.FindElementsByXPath("//select[@id='sampleMethod']//option");
	}

	public Element getSortBymetadata(String value) {
		return driver.FindElementByXPath(
				"//select[@id='ddlMetaDataField']/option[text()='" + value + "' and  @selected='selected']");
	}

	public Element getSortBymetadataType(String value) {
		return driver.FindElementByXPath(
				"//select[@id='ddlMetaDataDirection']/option[text()='" + value + "' and  @selected='selected']");
	}

	public Element getAvailableCriteriaCategory(String value) {
		return driver.FindElementByXPath("//span[text()='" + value + "']//parent::div");
	}

	public Element manageAssignmentHelpIcon() {
		return driver.FindElementByXPath("(//a[@class='helptip fa fa-question-circle'])[2]");
	}

	public Element manageAssignmentHelpIconPopOver() {
		return driver.FindElementByXPath("//div[@class='popover fade right in']");
	}

	public Element ActionBtn() {
		return driver.FindElementByXPath("//*[@id='btnAction']");
	}

	public Element getInstruction_HtmlBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='html']");
	}

	public Element getAssgn_LikeDoc_NeartoggleDocCount() {
		return driver.FindElementByXPath("(//span[@class='label label-default count'])[3]");
	}

	/// Elements to be drag and drop under live Sequence
	public Element dragElement(String text) {
		return driver.FindElementByXPath("//div/span[contains(text(),'" + text + "')]/i");
	}

	public Element dropElement() {
		return driver.FindElementByCssSelector("div[id='nestable']");
	}

	public ElementCollection metaDataList_reviewerPg() {
		return driver.FindElementsByXPath("//div[@id='SearchDataTable_wrapper']//tr[@role='row']/td[position()=3]");
	}

	public Element CatogeryTabClicked() {
		return driver.FindElementByXPath(
				"//*[@id='tabs-sort']/ul/li[@class='ui-tabs-tab ui-corner-top ui-state-default ui-tab ui-tabs-active ui-state-active']//a[@id='ui-id-1']");
	}

	// formatting tool bar elements added by jayanthi

	public Element getInstruction_FormattingBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='formatting']");
	}

	public Element getInstruction_BoldBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='bold']");
	}

	public Element getInstruction_ItalicBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='italic']");
	}

	public Element getInstruction_DeleteBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='deleted']");
	}

	public Element getInstruction_UnorderedListBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='unorderedlist']");
	}

	public Element getInstruction_OrderedListBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='orderedlist']");
	}

	public Element getInstruction_OutdentBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='outdent']");
	}

	public Element getInstruction_IndentBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='indent']");
	}

	public Element getInstruction_LinkBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='link']");
	}

	public Element getInstruction_AlignmentBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='alignment']");
	}

	public Element getInstruction_HorizontalRuleBtn() {
		return driver.FindElementByXPath("//*[@id='redactor-toolbar-0']/li/a[@rel='horizontalrule']");
	}

	public Element getInstructionCancelBtn() {
		return driver.FindElementByXPath("//*[@id='insCancel']");
	}

	public Element getKeywordPopUpCancelBtn() {
		return driver.FindElementByXPath("//*[@id='keywordCancel']");
	}

	public ElementCollection getAssignedMetadataListInAssgnPage() {
		return driver.FindElementsByXPath("//*[@id='AssignedMetaData']//option");
	}

	public ElementCollection getRetainedMetadataListInReviewerPage() {
		return driver.FindElementsByXPath("//*[@id='MetaDataDT']/tbody/tr/td[1]");
	}

	public Element GetSelectMetaDataBtn() {
		return driver.FindElementByXPath("//*[@id='btnSelectMetadata']");
	}

	public Element getAssignmentAction_TagAll() {

		return driver.FindElementByXPath("//a[text()='Tag All Documents']");
	}

	public Element SelectMetaDataPopUpDisabled() {
		return driver.FindElementByXPath("//div[@class='mappingModal smart-form disabledbutton']");
	}

	public Element SelectKeywordPopUpDisabled() {
		return driver.FindElementByXPath("//*[@id='divkeyword' and @class='col-md-12 disabledbutton']");
	}

	public Element SelectInstructionPopUpDisabled() {
		return driver.FindElementByXPath("//*[@id='divconfigure' and @class='widget-body disabledbutton']");
	}

	public Element getEmailThreadsTogetherBtnDisabled() {
		return driver.FindElementByXPath("//*[@id='IsKeepEmailThreadsTogether' and @disabled='disabled']");
	}

	public Element getDraawPoolToggleDisabled() {
		return driver.FindElementByXPath("//*[@id='IsDrawFromPool' and @disabled='disabled']");
	}

	public Element getKeepFamiliesBtnDisabled() {
		return driver.FindElementByXPath("//*[@id='IsKeepFamiliesTogether' and @disabled='disabled']");
	}

	public ElementCollection getPresentationControlTogglesDisabled(int i) {
		return driver.FindElementsByXPath("//*[@id='CascadingDivision']/div/div/div[5]/div/div/div[" + i
				+ "]/div/label/input[2][@disabled='disabled']");
	}

	public Element GetPresentaionControlToggle_Column1(int i, int k) {
		return driver.FindElementByXPath("(//*[@id='CascadingDivision']/div/div/div[5]/div/div/div[1]/div/label/input["
				+ k + "][@disabled='disabled'])[" + i + "]");
	}

	public Element getAssgn_TodoCountInManageRevTab(int i) {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[" + i + "]/td[6]");

	}

	public Element getAssgn_TodoCountInManageRevTabWithUser(String username1) {
		return driver.FindElementByXPath(
				".//*[@id='dt_basic']//td[contains(.,'" + username1 + "')]/following-sibling::td[4]");

	}

	public Element getCountofRedistributeDocs() {
		return driver.FindElementByXPath("//*[@id='txtDistributeDocCount']");
	}

	public Element getSelectuserToRedistributeDocs(String username) {
		return driver.FindElementByXPath(
				"//label[contains(text(),'" + username + "')]/parent::td//preceding-sibling::td//label//i");
	}

	public Element getTotalDocsCountInReviewerDashboard(String assignmentName) {
		return driver.FindElementByXPath(
				"//strong[contains(text(),'" + assignmentName + "')]/ancestor::td//following-sibling::td[2]//span[2]");
	}

	// End of (added by jayanthi 28/9/21)
	// Added by Indium-Mohan

	public Element getMiniDocListToggleButton() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsShowMiniDocList']/following-sibling::i");
	}

	public Element getNearDupeDocumentsIncludeToggleButton() {
		return driver.FindElementByXPath("//input[@id='chkIncludeNearDuplicates']//following-sibling::i");
	}

	public Element getTotalSelectedDocuments() {
		return driver.FindElement(By.id("spanTotal"));
	}

	public Element getSelect2ndUserInDistributeTabForNeardupes() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.rmu2userName + "')]/div/label");
	}

	public Element getManageAssignmnets() {
		return driver.FindElementByXPath("//h1[text()='Manage Assignments']");
	}

	public Element getRedistributeYesPopup() {
		return driver.FindElementByXPath("//*[@class='MessageBoxButtonSection']//button[@id='bot1-Msg1']");
	}

	public Element getSelectTheReviewersDocCheckbox() {
		return driver.FindElementByXPath("//*[@id='my-table']//label//i");
	}

	public Element getAssgn_ShowDefaultViewTabToggleButton() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsShowDefaultViewTab']/following-sibling::i");
	}

	// Added by Raghuram
	public Element getSelectAssignmentHighlightCheck(String assignmentName) {
		return driver.FindElementByXPath(
				"//*[@id='GridAssignment']/tbody//tr[td='" + assignmentName + "' and @class[contains(.,'highlight')]]");
	}

	public Element getSortByMetaDataType() {
		return driver.FindElementByXPath("//select[@id='ddlMetaDataDirection']");
	}

	// Added by Gopinath- 20/09/2021
	public Element getEnableRedactionsToogle() {
		return driver
				.FindElementByXPath("//input[@id='AdditionalPreferences_IsEnableRedactions']/following-sibling::i");
	}

	public Element getSelectAssignmentRow(String assignmentName) {
		return driver.FindElementByXPath(
				"//*[@id='GridAssignment']/tbody//tr[td='" + assignmentName + "' and contains(@class,'highlight')]");
	}

//	Added by baskar
	public Element getWarningTxtDis() {
		return driver.FindElementByXPath("//p[text()='Please enter a valid number to distribute']");
	}

	public Element getAssignmentText(String assignmentName) {
		return driver.FindElementByXPath("//strong[text()='" + assignmentName + "']/ancestor::tr//td[5]//span[2]");
	}

	// Added by Gopinath - 30/09/2021
	public Element getAssgnName() {
		return driver.FindElementById("AssignmentName");
	}

	public Element getSelectdCodingForm() {
		return driver.FindElementById("SelectedCodingForm");
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

	public Element getLastPage() {
		return driver.FindElementByXPath(
				"//li[@id='GridAssignment_ellipsis']/following-sibling::li//a[not(contains(text(),'Next'))]");
	}

	// Added by baskar
	public Element getAssignmentErrorText() {
		return driver.FindElementByXPath("//span[@id='AssignmentName-error']");
	}

	public Element getAssignmentCountErrorText() {
		return driver.FindElementByXPath(
				"//p[text()='Please select an assignment which contains documents assigned to it.']");
	}

	// Added by Iyappan
	public Element getSelectUserToAssignPA() {
		return driver.FindElementByXPath(
				"//*[@id='divNotAssignedUsers']//div[contains(.,'" + Input.pa1userName + "')]/../div/label");
	}

	public Element getSelectUserInDistributeTabsPA() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.pa1userName + "')]/div/label");
	}

	public Element getFamilyDocumentsIncludeToggleButton() {
		return driver.FindElementByXPath("//input[@id='chkIncludeFamilyMemeber']//following-sibling::i");
	}

	public Element unCompleteStatusBtn(String assignmentName) {
		return driver.FindElementByXPath("//strong[text()='" + assignmentName
				+ "']/ancestor::tr[@role='row']//following::td/div[@class='progress-lg']/div[@class='progress-bar bg-color-red'][@style='width: 100%']");

	}

	public Element getsortBymetaData() {
		return driver.FindElementByXPath("//a[text()='Sort by Metadata']/parent::li[@aria-disabled='true']");
	}

	public Element getCategory() {
		return driver.FindElementByXPath("//a[text()='Category']/parent::li[@aria-disabled='true']");
	}

	public Element getRoot() {
		return driver.FindElementByXPath("//a[@data-content='Root']");
	}

	public Element getAssignmentsInreviewerPg() {
		return driver.FindElementByXPath("//h2[contains(text(),'Assignments within Assignment Group >>')]");
	}

	public Element getAssignmentsCompletedCountInreviewerPg(String assignmentName) {
		return driver.FindElementByXPath("//a[text()='" + assignmentName + "']/ancestor::tr/td[6]");
	}

	public Element getAssignmentsTodoCountInreviewerPg(String assignmentName) {
		return driver.FindElementByXPath("//a[text()='" + assignmentName + "']/ancestor::tr/td[2]");
	}

	public Element getAssignmentsBatchProgressInreviewerPg(String assignmentName) {
		return driver.FindElementByXPath("//strong[text()='" + assignmentName
				+ "']/ancestor::tr[@role='row']//following::td/span[@class='progressreport rev-prgress-text']");
	}

	public Element getAssignmentAction_UnCompletedoc() {
		return driver.FindElementByXPath("//a[contains(text(),'Uncomplete All Documents')]");
	}

	public Element getAssgn_keepFamiliesTogetherToggle() {
		return driver.FindElementByXPath("//input[@id='IsKeepFamiliesTogether']/parent::label /i");
	}

	public Element getSelectCopyAssignments(String assignmentName) {
		return driver.FindElementByXPath(
				"(//*[@id='GridAssignment']/tbody//tr/*[contains(text(),'" + assignmentName + "')])[last()]");
	}

	public Element getAssgn_ManageRev_Action_CompleteAllDocs() {
		return driver.FindElementById("Complete Documents");
	}

	public Element getAssgn_ManageRev_Action_UnCompleteAllDocs() {
		return driver.FindElementById("Uncomplete All Documents");
	}

	public Element getAssgn_docsToDistribute() {
		return driver.FindElementById("txtNoOfDoc");
	}

	public Element getAssgn_TodoCountInManageRev(String username) {
		return driver.FindElementByXPath("//td[contains(text(),'" + username + "')]/parent::tr/td[6]");
	}

	// modified 14/09/2022
	public Element getAssignmentsDrawPoolInreviewerPg(String assignmentName) {
		return driver.FindElementByXPath("//table[@id='dt_basic']/tbody/tr/td[3]/a/strong[contains(text(),'"
				+ assignmentName + "')]/parent::a/parent::td/following-sibling::td[2]//a[contains(text(),'Draw')]");
	}

	public Element assignmentNameInDocViewPg(String assignmentName) {
		return driver.FindElementByXPath("//h2[contains(text(),'" + assignmentName + "')]");
	}

	public Element docListPageTitle() {
		return driver.FindElementByXPath("//h1[text()='DocList ']");
	}

	public ElementCollection getAllKeywordsList() {
		return driver.FindElementsByXPath("//div[@id='assKeywordsListDiv']//input");
	}

	public Element getKeywordFromList(int i) {
		return driver.FindElementByXPath("(//div[@id='assKeywordsListDiv']//input)[" + i + "]");
	}

	public Element getSelectReviewerInRedistributedDocsTab(String userName) {
		return driver.FindElementByXPath("//label[contains(text(),'" + userName + "')]/ancestor::tr//i");
	}

	public Element getSelectUserInDistributeTabToReassign(String user) {
		return driver
				.FindElementByXPath("//*[@id='divDistributedDocUsers']//div[contains(.,'" + user + "')]/div/label");
	}

	public Element getSelectUserToAssigTwo() {
		return driver.FindElementByXPath(
				"//*[@id='divNotAssignedUsers']//div[contains(.,'" + Input.rev2userName + "')]/../div/label");
	}

	public Element getSelectUserInDistributeTabsTwo() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.rev2userName + "')]/div/label");
	}

	// added by krishna
	public Element getEditAggn_Distribute_UnassgndocCount(int hits) {
		return driver
				.FindElementByXPath("//label[text()='Unassign Documents:']/parent::div//label[text()='" + hits + "']");
	}

//added by jayanthi
	public Element getAssgn_ManageRev_DocCountsDistributedUser() {
		return driver.FindElementByXPath("//table[@id='dt_basic']/tbody/tr/td[4]");
	}

	public Element getAssg_DocView_DocCOunt() {
		return driver.FindElementByXPath("//li//small[@id='totalRecords']");
	}

	public Element getBackToManageBtn() {
		return driver.FindElementByXPath("//a//i[@class='fa fa-lg fa-fw fa-arrow-circle-left']");
	}

	public Element getDefaultViewToggle() {
		return driver.FindElementByXPath("//input[@name='AdditionalPreferences.IsShowDefaultViewTab']/parent::label/i");
	}

	public ElementCollection KeywordNamesList() {
		return driver.FindElementsByXPath("//div[@id='assKeywordsListDiv']/div/div");
	}

	public ElementCollection GetAssignmentsList(String Value) {
		return driver.FindElementsByXPath("//*[@id='GridAssignment']/tbody//tr/td[contains(text(),'" + Value + "')]");
	}

	public Element GetAssignments(String Value) {
		return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr/td[contains(text(),'" + Value + "')]");
	}

	public ElementCollection getLiveSequenceElements() {
		return driver.FindElementsByXPath("//i[@class='fa fa-align-justify']/parent::span");
	}

	public Element getPresentationControlToggles(String eleName) {
		return driver.FindElementByXPath(
				"(//div[@class='col-md-4']//div[@class='smart-form']//label[text()[normalize-space() = '" + eleName
						+ "']]/i)");
	}

	public ElementCollection getPresentationControlTogglesRowCount() {
		return driver
				.FindElementsByXPath("(//div[@class='col-md-4']//div[@class='smart-form']//label[@class='toggle']/i)");
	}

	// Added by Aathith
	public Element getAllowReviewerCode() {
		return driver.FindElementByXPath("//input[@id='AdditionalPreferences_IsAllowCodingOutsideReviwerBatch']/../i");
	}

	public Element getDocumentCount() {
		return driver.FindElementByXPath("//input[@id='DrawFromPoolCount']");
	}

	public Element getCodingStampToggleForAssgn() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsAllowCodingStamps']/following-sibling::i");
	}

	public Element getAssignmentAction_CompleteDoc() {
		return driver.FindElementByXPath("//a[text()='Complete All Documents']");
	}

	public Element getAssgn_ToggleForAllowCodingOutsideReviwerBatch() {
		return driver.FindElementByXPath(
				"//*[@id='AdditionalPreferences_IsAllowCodingOutsideReviwerBatch']/following-sibling::i");
	}

	public Element getAssgn_ToggleForAllowSaveWithoutCompletion() {
		return driver.FindElementByXPath(
				"//*[@id='AdditionalPreferences_IsAllowSaveWithoutCompletion']/following-sibling::i");
	}

	public Element getResponsiveCheked() {
		return driver
				.FindElementByXPath("//div[@id='item1']//div[@id='0_radiogroup']//div[1]//div[1]//label[1]//span[1]");
	}

	public Element getNonPrivilegeRadio() {
		return driver.FindElementByXPath("//input[@id='9_radio']//parent::label//span");
	}

	public Element getDocument_CommentsTextBox() {
		return driver.FindElementByXPath("//textarea[@id='1_textarea']");
	}

	// Added by gopinath - 05/01/2022
	public Element getAssign_DownloadNativesToggleON() {
		return driver.FindElementByXPath(
				"//*[@id='AdditionalPreferences_IsAllowNativeDownloads']/following-sibling::i[@class='true']");
	}

	public Element getAssign_ViewImagesToggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsAllowProductionView']/following-sibling::i");
	}

	public Element getAssign_ViewImagesToggleOn() {
		return driver.FindElementByXPath(
				"//*[@id='AdditionalPreferences_IsAllowProductionView']/following-sibling::i[@class='true']");
	}

	public Element getDispalyMinidocListToggle() {
		return driver.FindElementByXPath("//input[@name='AdditionalPreferences.IsShowMiniDocList']/parent::label/i");
	}

	// Added by Gopinath - 07/01/2022
	public Element getAssign_AllowUserToSaveToggle() {
		return driver.FindElementByXPath(
				"//*[@id='AdditionalPreferences_IsAllowSaveWithoutCompletion']/following-sibling::i");
	}

	public Element getAssign_AllowUserToSaveToggleON() {
		return driver.FindElementByXPath(
				"//*[@id='AdditionalPreferences_IsAllowSaveWithoutCompletion']/following-sibling::i[@class='false true']");
	}

	// Added by Vijaya.Rani
	public Element getUnComplePopOutYes() {
		return driver.FindElementByXPath("//button[text()=' Yes']");
	}

	public Element getSelectUserToAssignDA() {
		return driver.FindElementByXPath(
				"//*[@id='divNotAssignedUsers']//div[contains(.,'" + Input.da1userName + "')]/../div/label");
	}

	public Element getSelectUserInDistributeDA() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.da1userName + "')]/div/label");
	}

	public Element RMUDashBoardAssgn(String AssgnName) {
		return driver.FindElementByXPath("//strong[text()='" + AssgnName + "']/parent::a");
	}

	public Element getRMUDashBoardBtn() {
		return driver.FindElementByXPath("//a[@title='Dashboard']");
	}

	public Element getDistributedDocs(String userNAme) {
		return driver.FindElementByXPath(
				".//*[@id='dt_basic']//td[contains(.,'" + userNAme + "')]/following-sibling::td[2]");
	}

	public Element getSelectDAUserToAssign() {
		return driver.FindElementByXPath(
				"//*[@id='divNotAssignedUsers']//div[contains(.,'" + Input.da1userName + "')]/../div/label");
	}

	public Element getSelectUserInDistributeTabsDA() {
		return driver.FindElementByXPath(
				"//*[@id='divDistributedDocUsers']//div[contains(.,'" + Input.da1userName + "')]/div/label");
	}

	// Added by Jeevitha
	public Element getKeepFamilyTogetther_Text() {
		return driver
				.FindElementByXPath("//input[@id='IsKeepFamiliesTogether']/parent::label /i//following-sibling::span");
	}

	public Element getAssgn_keepEmailThreadTogetherToggle() {
		return driver.FindElementByXPath("//input[@id='IsKeepEmailThreadsTogether']/parent::label /i");
	}

	public Element getKeepEmailThreadTogether_Text() {
		return driver.FindElementByXPath(
				"//input[@id='IsKeepEmailThreadsTogether']/parent::label /i//following-sibling::span");
	}

	public ElementCollection getAvailableKeywordCheckboxes() {
		return driver.FindElementsByXPath("//div[@id='divkeyword']//label[@class='checkbox']//i");
	}

	public Element getBatchAssignmentBar(String assignmentName) {
		return driver.FindElementByXPath("//strong[text()='" + assignmentName
				+ "']/ancestor::tr[@role='row']//following::td/div[@class='progress-lg']");
	}

	public ElementCollection getKeywordNames() {
		return driver.FindElementsByXPath("//div[contains(@style,'color')]");
	}

	public Element getKeywordCheckBox(String keywordName) {
		return driver.FindElementByXPath("//div[text()='" + keywordName + "']//..//i");
	}

	public Element getAssgn_PopOutPanelToggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsAllowPopoutPanels']/following-sibling::i");
	}

	public ElementCollection getManageAssignmentsHeader() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHeadInner']//tr[@role='row']//th");
	}

	public ElementCollection getManageReviewereTabHeader() {
		return driver.FindElementsByXPath("//table[@id='dt_basic']//thead//td");
	}

	public Element getDistributedDocs(String userNAme, String index) {
		return driver.FindElementByXPath(
				".//*[@id='dt_basic']//td[contains(.,'" + userNAme + "')]/following-sibling::td[" + index + "]");
	}

	public Element getFamilyMembersCount() {
		return driver.FindElementByXPath("//span[@id='CountTextFamilyMem']");
	}

	// Added by Iyappan
	public Element getProgressReport() {
		return driver.FindElementByXPath("//a[text()='Progress Report']");
	}

	public Element getSelectedCodeForm_inSortingPopUp(String CFName) {
		return driver.FindElementByXPath("//div[@id='divSortCodingForm']//li[@value='" + CFName + "']");
	}

	public Element getSelectCodeFormRadioBtn(String CFName) {
		return driver.FindElementByXPath("//div[@id='dtCodingFormList_wrapper']//input[@value='" + CFName
				+ "']/ancestor::td/following-sibling::td//span/label/i");
	}

	public Element getSelectCF_CheckBox(String CFName) {
		return driver.FindElementByXPath(
				"//div[@id='dtCodingFormList_wrapper']//input[@value='" + CFName + "']/parent::label/i");
	}

	public Element SelectCFPopUpSG_Step1() {
		return driver.FindElementByXPath("//span[text()='Add / Remove Coding Forms in this Security Group']");
	}

	public Element SelectCFPopUp_Step1() {
		return driver.FindElementByXPath("//h3[text()='Add / Remove Coding Forms in this Assignment']");
	}

	public Element sortOrderNxtBtn() {
		return driver.FindElementById("btnSortOrderNext");
	}

	public Element sortCodeFormOrderSaveBtn() {
		return driver.FindElementById("btnCodingFormSave");
	}

	public Element getSelectSortCodingForm_Tab() {
		return driver.FindElementById("CodingFormBtn");
	}

	public Element getSelectedCodeForminAssignPAge() {
		return driver.FindElementByXPath(" //label[@id='selectedCodingFormString']");
	}

	public Element getQB_AssignemntName_ErrorMSg() {
		return driver.FindElementByXPath("//span[@id='AssignmentName-error']");
	}

	public Element getSelectUserToAssignAsReviewer(String userName) {
		return driver
				.FindElementByXPath("//*[@id='divNotAssignedUsers']//div[contains(.,'" + userName + "')]/../div/label");
	}

	public Element getAssignmentActionManageTab_ViewinDocList() {
		return driver.FindElementByXPath("//a[text()='View in Doc List']");
	}

	// Added by Iyappan
	public ElementCollection getLiveSequenceMetadatas() {
		return driver.FindElementsByXPath("//ol[@class='dd-list']//i/parent::span");
	}

	public Element getCodingForm_AssignemntName_ErrorMSg() {
		return driver.FindElementByXPath("//span[@id='hidCodingFormList-error']");

	}

	public Element getReviewerPopupCloseBtn() {
		return driver.FindElementByXPath("//header[contains(text(),'Add Reviewers')]//button[@class='close']");
	}

	public Element getCategoryStatus() {
		return driver.FindElementByXPath("//a[text()='Category']/parent::li");
	}

	public Element getunderAvailableCriteria(String data) {
		return driver.FindElementByXPath("//div[@id='nestable']//span[text()='" + data + "']//i");
	}

	public Element getDropElement() {
		return driver.FindElementByCssSelector("div[id='nestable2']");
	}

	public Element getSelectAssignedMDinAssignPage(String metaData) {
		return driver.FindElementByXPath("//select[@id='AssignedMetaData']//option[text()='" + metaData + "']");
	}

	public Element getMetadataFielOkBtn() {
		return driver.FindElementById("MetaDataSave");
	}

	public Element getCFCancelBtn() {
		return driver.FindElementById("btnCodingFormCancel");
	}

	public Element getPoolValueFromDistributeTab() {
		return driver.FindElementByXPath("//label[contains(text(),'draw max')]//following-sibling::div");
	}

	public Element getdrawFromPoolStatus() {
		return driver.FindElementByXPath("//label[contains(text(),'Draw From Pool:')]//following-sibling::div");
	}

	public ElementCollection getDistributeReviewerCount() {
		return driver.FindElementsByXPath(
				"//*[@id='divDistributedDocUsers']//div//label[contains(text(),'distribute documents to:')]//following-sibling::div//label");
	}

	public ElementCollection selectReviewersToDistributeDocs() {
		return driver.FindElementsByXPath(
				"//label[contains(text(),'Select which Reviewers you want to distribute documents to:')]/parent::div//label[@class='checkbox']/i");
	}

	public ElementCollection getHeaders_AssignmentTable() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHeadInner']/table[@role='grid']/thead/tr/th");
	}

	public Element getRowValuesinAssignmentTAble(String assignmentName, int i) {
		return driver.FindElementByXPath("//td[text()='" + assignmentName + "']/ancestor::tr//td[" + i + "]");
	}

	public Element getReviewerInDistributeTab(String userName) {
		return driver.FindElementByXPath("//*[@id='divDistributedDocUsers']//div[contains(text(),'" + userName + "')]");
	}

	public Element getassignmentTabTreeView() {
		return driver.FindElementByXPath("//div[@class='tree-wrapper']//div[@id='jstreeComplete']//ul");
	}

	public Element bulkAssignCancelButton() {
		return driver.FindElementById("btnAssignCancel");
	}

	public Element getCodingFormOrderPopUpHeader() {
		return driver.FindElementByXPath("//h3[text()='Coding Form Order']");
	}

	public Element getCodingFormOrderHamburgerIcons(int listIteam) {
		return driver.FindElementByXPath(
				"//ol[@id='sortedCodingformList']/li[" + listIteam + "]/div[@class='dd-handle ddcf-handle']");
	}

	public ElementCollection listOfCFInCodingFormOrderPopUp() {
		return driver.FindElementsByXPath("//ol[@id='sortedCodingformList']//li");
	}

	public Element getSelectAllCodingForm() {
		return driver.FindElementByXPath("//input[@id='chkSelectAllCodingform']/following-sibling::i");
	}

	public ElementCollection getUnAssignedMetadataListInAssgnPage() {
		return driver.FindElementsByXPath("//*[@id='UnAssignedMetaData']//option");
	}

	public ElementCollection getCheckedKeywordList() {
		return driver.FindElementsByXPath(
				"//input[@name='assKeywordsList' and @checked='checked']/parent::*/following-sibling::div");
	}
	public Element getAssignmentAction_FolderAll() {
		return driver.FindElementByXPath("//a[text()='Folder All Documents']");
	}

	public AssignmentsPage(Driver driver) {

		this.driver = driver;
		bc = new BaseClass(driver);
		// this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		// This initElements method will create all WebElements
		assertion = new SoftAssert();
		search = new SessionSearch(driver);

	}

	public void createAssignment(String assignmentName, String codingForm) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentActionDropdown().Visible();
			}
		}), Input.wait60);
		Thread.sleep(2000);
		getAssignmentActionDropdown().waitAndClick(10);

		getAssignmentAction_NewAssignment().WaitUntilPresent();
		Thread.sleep(2000);
		getAssignmentAction_NewAssignment().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		driver.waitForPageToBeReady();
		getAssignmentName().SendKeys(assignmentName);
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);

		// permissions
		driver.scrollingToBottomofAPage();
		driver.scrollingToBottomofAPage();
		Thread.sleep(2000);
		// give redaction permission to reviewers
		driver.getWebDriver().findElement(By.xpath("(//div[@class = 'smart-form'])[25]")).click();
		Thread.sleep(2000);
		getSelectSavePermission().ScrollTo();
		getSelectSavePermission().waitAndClick(5);
		driver.scrollPageToTop();
		// getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
		getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Assignment " + assignmentName + " created with CF " + codingForm);
		System.out.println("Assignment created");
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);

	}

	public void createAssignmentDisplayAnalyticsPanelEnabled(String assignmentName, String codingForm)
			throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentActionDropdown().Visible() && getAssignmentActionDropdown().Enabled();
			}
		}), Input.wait30);
		getAssignmentActionDropdown().Click();

		getAssignmentAction_NewAssignment().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible() && getAssignmentName().Enabled();
			}
		}), Input.wait30);
		getAssignmentName().SendKeys(assignmentName);
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentCodingFormDropDown().Visible();
				}
			}), Input.wait30);
			getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
		} catch (Exception e) {
			getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
		}

		// permissions
		driver.scrollingToBottomofAPage();

		// Enable Display AnalyticsPanel

		// give redaction permission to reviewers
		driver.getWebDriver().findElement(By.xpath("(//div[@class = 'smart-form'])[25]")).click();
		Thread.sleep(2000);
		getSelectSavePermission().ScrollTo();
		getSelectSavePermission().waitAndClick(5);
		driver.scrollPageToTop();
		// getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
		getAssignmentSaveButton().waitAndClick(5);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);

	}

	public void assignDocstoExisting(final String assignmentName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getStartingCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		getPersistCB_ExistAssgn().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentToBulkAssign(assignmentName).Visible();
			}
		}), Input.wait60);

		System.out.println(getSelectcopyAssgnmToBulkAssign().FindWebElements().size());
		UtilityLog.info(getSelectcopyAssgnmToBulkAssign().FindWebElements().size());
		for (WebElement iterable_element : getSelectcopyAssgnmToBulkAssign().FindWebElements()) {

			if (iterable_element.getText().contains(assignmentName)) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();

				iterable_element.click();
			}
		}
		getContinueBulkAssign().waitAndClick(10);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_Actionlikepopup().Visible();
			}
		}), Input.wait30);

		String assignpop = getAssignment_Actionlikepopup().getText();
		System.out.println(assignpop);
		UtilityLog.info(assignpop);
		Assert.assertEquals("Action Like Documents", assignpop);

		getAssgn_LikeDoc_Family().isDisplayed();
		Assert.assertEquals("Family Members", getAssgn_LikeDoc_Family().getText());
		Assert.assertEquals("Email Threaded Docs", getAssgn_LikeDoc_Email().getText());
		Assert.assertEquals("Near Duplicates", getAssgn_LikeDoc_Near().getText());

		getFinalizeButton().Click();
		bc.VerifySuccessMessage(
				"Bulk Assign has been added to background process. You will get notification on completion.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		bc.stepInfo("Docs assigned to  " + assignmentName);
		UtilityLog.info("Docs assigned to  " + assignmentName);
	}

	public void editAssignment(final String assignmentName) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		Boolean assignmentVisibility = false;
		try {
			assignmentVisibility = getSelectAssignment(assignmentName).isDisplayed();
		} catch (Exception e) {
			System.err.println("Error" + e);
		}
		if (assignmentVisibility) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectAssignment(assignmentName).Visible();
				}
			}), Input.wait60);

		} else {
			bc.waitForElement(getAssgn_Pagination());
			String nextbutton = getAssgn_Pagination().GetAttribute("class").trim();
			do {
				getAssgn_Pagination().waitAndClick(10);
				driver.waitForPageToBeReady();
				try {
					assignmentVisibility = getSelectAssignment(assignmentName).isDisplayed();
				} catch (Exception e) {
					System.err.println("Error" + e);
				}
				nextbutton = getAssgn_Pagination().GetAttribute("class").trim();
				if (nextbutton.equals("paginate_button next disabled")) {
					break;
				}
			} while (!assignmentVisibility);
		}

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();

		if (!getSelectAssignmentHighlightCheck(assignmentName).isDisplayed()) {
			getSelectAssignment(assignmentName).waitAndClick(5);
		}

		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_EditAssignment().Visible();
			}
		}), Input.wait60);
		bc.waitTime(2);
		getAssignmentAction_EditAssignment().isElementAvailable(10);
		getAssignmentAction_EditAssignment().waitAndClick(3);

	}

	public void addReviewerAndDistributeDocs(String assignmentName, int docCount) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait60);
		getAssignment_ManageReviewersTab().waitAndClick(30);

		getAddReviewersBtn().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserToAssign().Visible();
			}
		}), Input.wait60);
		getSelectUserToAssign().waitAndClick(10);
		getAdduserBtn().Click();
		bc.VerifySuccessMessage("Action saved successfully");
		getDistributeTab().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserInDistributeTab().Visible();
			}
		}), Input.wait60);
		getSelectUserInDistributeTab().waitAndClick(20);

		getDistributeBtn().waitAndClick(15);

		getAssignment_BackToManageButton().waitAndClick(10);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		Thread.sleep(2000);

		getAssgnCounts(assignmentName, 9);
		// verify total docs count
		String acttotalcount = getAssgnCounts(assignmentName, 9).getText();
		System.out.println(Integer.parseInt(acttotalcount));
		UtilityLog.info(acttotalcount);
		// assertion.assertEquals(docCount, Integer.parseInt(acttotalcount));

		// verify distributed docs count
		String actdistributedcount = getAssgnCounts(assignmentName, 9).getText();
		System.out.println(Integer.parseInt(actdistributedcount));
		UtilityLog.info(Integer.parseInt(actdistributedcount));
		// assertion.assertEquals(docCount, Integer.parseInt(actdistributedcount));
		// assertion.assertAll();

	}

	public void assignDocstoNewAssgn(final String assignmentName, String codingForm, int purehits) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssign_NewAssignment().Visible();
			}
		}), Input.wait60);
		getBulkAssign_NewAssignment().waitAndClick(20);
		Assert.assertTrue(getbulkassgnpopup().isDisplayed());

		getPersistCB_NewAssgn().waitAndClick(15);

		getContinueBulkAssign().waitAndClick(15);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getFinalizeButton().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assignmentName);
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");

		SelectCodingform(codingForm);

		getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		getAssgnGrp_Create_DisplayAnalyticstoggle().Click();
		driver.scrollPageToTop();
		getAssignmentSaveButton().waitAndClick(30);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();

		Assert.assertEquals(purehits, Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText()));

	}

	// added by Indium Mohan
	public void assgnDocWithEmailThreadMapDisable(final String assignmentName, String codingForm, int purehits) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssign_NewAssignment().Visible();
			}
		}), Input.wait60);
		getBulkAssign_NewAssignment().Click();
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());

		getPersistCB_NewAssgn().waitAndClick(15);

		getContinueBulkAssign().waitAndClick(15);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getFinalizeButton().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assignmentName);
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");

		// select coding form from assignment popup
		SelectCodingform(codingForm);

		getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		getAssgnGrp_Create_DisplayAnalyticstoggle().Click();
		getAssgnGrp_Create_DisplayAnalyticstoggleDisable().waitAndClick(5);

		driver.scrollPageToTop();
		getAssignmentSaveButton().waitAndClick(30);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();

		assertion.assertEquals(purehits, Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText()));

	}

	public void SelectAssignmentToViewinDocview(final String assignmentName) {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();

		getSelectAssignment(assignmentName).waitAndClick(5);
		;
		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_ViewinDocView().Visible();
			}
		}), Input.wait60);
		Assert.assertTrue(getAssgnAction_Export().isDisplayed());
		getAssignmentAction_ViewinDocView().waitAndClick(3);

	}

	public void SelectAssignmentByReviewer(final String assignmentName) {
		driver.getWebDriver().get(Input.url + "ReviewerDashboard/Index");
		driver.waitForPageToBeReady();
		getSelectAssignmentAsReviewer(assignmentName).WaitUntilPresent().ScrollTo();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentAsReviewer(assignmentName).Visible();
			}
		}), Input.wait30);

		getSelectAssignmentAsReviewer(assignmentName).waitAndClick(10);

	}

	public void AssgnToggleButtons() throws InterruptedException {

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_Edit_DisplayDocumentHistory().Visible();
			}
		}), Input.wait60);
		getAssignment_Edit_DisplayDocumentHistory().Click();

		getAssignment_Edit_CodingStampApplied().waitAndClick(10);

		getAssignment_Edit_ApplyRedactionbyreviewer().waitAndClick(10);

		driver.scrollPageToTop();

		getAssignmentSaveButton().waitAndClick(10);
		Thread.sleep(2000);
	}

	public void Assgnwithdocumentsequence() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_DocSequenceblock().isDisplayed();
			}
		}), Input.wait60);
		Assert.assertEquals("DOCUMENT PRESENTATION SEQUENCE", getAssgn_DocSequencelabel().getText());

		Element taget = getAssgn_DocSequence_liveseq();
		for (int i = 1; i <= 4; i++) {
			element.Draganddrop(getAssgn_DocSequence_avacrtiera(i), taget);

		}
		getAssgn_DocSequence_SortbyMetadata().waitAndClick(10);

		getAssgn_DocSequence_Selectmetadata().selectFromDropdown().selectByVisibleText("CustodianName");

		getAssignmentSaveButton().waitAndClick(10);

		bc.VerifySuccessMessage("Assignment updated successfully");
		bc.CloseSuccessMsgpopup();

	}

	public void Assignment_ManageRevtab_ViewinDocView() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait60);
		getAssignment_ManageReviewersTab().waitAndClick(10);

		String count = getAssgn_ManageRev_revdoccount().getText();
		System.out.println(count);
		Actions action = new Actions(driver.getWebDriver());
		bc.waitTime(2);
		action.moveToElement(getAssgn_ManageRev_selectrev().getWebElement()).click().build().perform();
		bc.waitTime(2);
		getAssgn_ManageRev_Action().waitAndClick(10);

		Assert.assertTrue(getAssgn_ManageRev_Action_ViewDocview().isDisplayed());

		getAssgn_ManageRev_Action_ViewDocview().waitAndClick(10);
		bc.getYesBtn().waitAndClick(10);

		DocViewPage docview = new DocViewPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docview.getDocView_info().Visible();
			}
		}), Input.wait60);
		String expcount = docview.getDocView_info().getText();
		System.out.println(expcount);
		Assert.assertTrue(expcount.contains(count));

	}

	public void Assignment_ManageRevtab_ViewinDoclist() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait60);
		getAssignment_ManageReviewersTab().waitAndClick(10);

		String count = getAssgn_ManageRev_revdoccount().getText();
		System.out.println(count);

		getAssgn_ManageRev_selectrev().waitAndClick(10);

		getAssgn_ManageRev_Action().waitAndClick(10);

		assertion.assertTrue(getAssgn_ManageRev_Action_ViewDoclist().isDisplayed());

		getAssgn_ManageRev_Action_ViewDoclist().waitAndClick(10);
		bc.getYesBtn().waitAndClick(10);
	}

	public void AssignmentDashboard() {
		driver.getWebDriver().get(Input.url + "Dashboard/Dashboard");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDashboadAssgn().Visible();
			}
		}), Input.wait30);

		List<WebElement> assgnlist = new ArrayList<WebElement>();
		assgnlist = getDashboadAssgn().FindWebElements();
		System.out.println("Assign list is" + assgnlist.size());

		Assert.assertTrue(assgnlist.size() <= 5);

	}

	public void Assignment_ManageRevtab_TagFolder(String tagname, String foldername)
			throws InterruptedException, AWTException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait30);
		getAssignment_ManageReviewersTab().waitAndClick(10);

		getAssgn_ManageRev_selectrev().waitAndClick(10);

		getAssgn_ManageRev_Action().waitAndClick(10);

		getAssgn_ManageRev_Action_tagdoc().waitAndClick(10);

		bc.getYesBtn().waitAndClick(10);

		search.BulkActions_Tag(tagname);

		// bulk folder

		getAssgn_ManageRev_Action().waitAndClick(20);

		getAssgn_ManageRev_Action_folderdoc().waitAndClick(10);

		bc.getYesBtn().waitAndClick(10);

		search.BulkActions_Folder(foldername);
		assertion.assertAll();

	}

	public void Assignment_ManageRevtab_UnTagUnFolder(String tagname, String foldername) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait30);
		getAssignment_ManageReviewersTab().waitAndClick(10);

		getAssgn_ManageRev_selectrev().waitAndClick(10);

		getAssgn_ManageRev_Action().waitAndClick(10);

		getAssgn_ManageRev_Action_tagdoc().waitAndClick(10);

		bc.getYesBtn().waitAndClick(10);

		search.bulkUnTag(tagname);

		// bulk folder

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait30);
		getAssignment_ManageReviewersTab().waitAndClick(10);

		getAssgn_ManageRev_selectrev().waitAndClick(10);

		bc.getYesBtn().waitAndClick(10);

		search.bulkUnFolder(foldername);
		assertion.assertAll();

	}

	public void createAssgnGroup(String assgngrpName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgGrptionDropdown().Visible();
			}
		}), Input.wait60);
		getAssgGrptionDropdown().waitAndClick(10);

		bc.waitForElement(getAssgnGrp_Create());
		getAssgnGrp_Create().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assgngrpName);
		
		bc.waitForElement(getAssgngrp_CascadeSetting());
		getAssgngrp_CascadeSetting().waitAndClick(10);
		bc.waitForElement(getAssgnGrp_Create_DrawPoolCount());
		getAssgnGrp_Create_DrawPoolCount().SendKeys("100");
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(10);

	}

	public void EditAssgnGroup(String assgngrpName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgGrptionDropdown().Visible();
			}
		}), Input.wait30);

		getAssgnGrp_Select(assgngrpName).waitAndClick(10);

		Thread.sleep(2000);
		getAssgGrptionDropdown().waitAndClick(10);

		getAssgnGrp_Edit().WaitUntilPresent();
		Thread.sleep(2000);

		getAssgnGrp_Edit().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assgngrpName + Utility.dynamicNameAppender());

		try {
			getAssignmentCodingFormDropDown().isDisplayed();
			Assert.fail();
		} catch (org.openqa.selenium.NoSuchElementException e) {

			System.out.println("'getAssignmentCodingFormDropDown' is not displayed");
		}

		for (int k = 1; k <= 4; k++) {
			Actions action = new Actions(driver.getWebDriver());
			action.dragAndDrop(getAssgn_DocSequenceblock_avacriteria(k).getWebElement(),
					getAssgn_DocSequenceblock_LiveSeq().getWebElement());
			Thread.sleep(1000);
		}

		getAssignmentSaveButton().waitAndClick(5);

	}

	// modified by jayanthi -24/8/21 Added scrollPageToTop
	public void DeleteAssgnGroup(String assgngrpName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgGrptionDropdown().Visible();
			}
		}), Input.wait30);
		getAssgnGrp_Select(assgngrpName).waitAndClick(10);
		driver.scrollPageToTop();
		Thread.sleep(2000);
		getAssgGrptionDropdown().waitAndClick(10);

		getAssgnGrp_Delete().WaitUntilPresent();
		Thread.sleep(2000);

		getAssgnGrp_Delete().waitAndClick(20);

		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment group deleted successfully");

	}

	public void add2ReviewerAndDistributeDocs() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait60);
		getAssignment_ManageReviewersTab().Click();

		getAddReviewersBtn().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserToAssign().Visible();
			}
		}), Input.wait60);
		getSelectUserToAssign().Click();
		driver.scrollingToElementofAPage(getSelect2ndUserToAssign());
		getSelect2ndUserToAssign().Click();

		getAdduserBtn().Click();
		bc.VerifySuccessMessage("Action saved successfully");
		getDistributeTab().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserInDistributeTab().Visible();
			}
		}), Input.wait60);

		assertion.assertEquals(Input.searchString1, getEditAggn_Distribute_Totaldoc().getText());

		getSelectUserInDistributeTab().waitAndClick(5);
		getSelect2ndUserInDistributeTab().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
		getDistributeBtn().waitAndClick(3);

		assertion.assertEquals(Input.searchString1, getEditAggn_Distribute_Unassgndoc().getText());
		assertion.assertAll();

	}

	public void RemoveDoc_ReviewerTab(String assignmentName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait60);
		getAssignment_ManageReviewersTab().Click();

		getAddReviewersBtn().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserToAssign().Visible();
			}
		}), Input.wait60);
		getSelectUserToAssign().waitAndClick(10);
		getSelect2ndUserToAssign().waitAndClick(10);

		getAdduserBtn().Click();
		bc.VerifySuccessMessage("Action saved successfully");

		getAssgn_ManageRev_selectrev().waitAndClick(10);

		getAssgn_ManageRev_Action().waitAndClick(10);

		getAssgn_ManageRev_Action_removedoc().waitAndClick(10);
		bc.getYesBtn().waitAndClick(10);
		bc.VerifySuccessMessage("Action saved successfully");

		Assert.assertEquals(0, getAssgn_ManageRev_DisDoc(1).getText());

		getAssignment_BackToManageButton().waitAndClick(10);

		// verify distributed and left to do count
		String actleftcount = getAssgnCounts(assignmentName, 9).getText();
		String actassignedcount = getAssgnCounts(assignmentName, 9).getText();
		Assert.assertEquals(0, Integer.parseInt(actleftcount));
		Assert.assertEquals(0, Integer.parseInt(actassignedcount));

	}

	public void Assgnwithspecialchars(String data) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(data);
		getAssignmentSaveButton().waitAndClick(5);
		Thread.sleep(1000);
		System.out.println(getAssgnnameErrormsg().getText());
		Assert.assertTrue(getAssgnnameErrormsg().getText()
				.equalsIgnoreCase("Please enter an assignment name without using special characters"));
	}

	public void deleteAssignment(final String assignmentName) {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		Boolean assignmentVisibility = false;
		try {
			assignmentVisibility = getSelectAssignment(assignmentName).isDisplayed();
		} catch (Exception e) {
			System.err.println("Error" + e);
		}
		if (assignmentVisibility) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectAssignment(assignmentName).Visible();
				}
			}), Input.wait60);

		} else {
			bc.waitForElement(getAssgn_Pagination());
			String nextbutton = getAssgn_Pagination().GetAttribute("class").trim();
			do {
				getAssgn_Pagination().waitAndClick(10);
				driver.waitForPageToBeReady();
				try {
					assignmentVisibility = getSelectAssignment(assignmentName).isDisplayed();
				} catch (Exception e) {
					System.err.println("Error" + e);
				}
				nextbutton = getAssgn_Pagination().GetAttribute("class").trim();
				if (nextbutton.equals("paginate_button next disabled")) {
					break;
				}
			} while (!assignmentVisibility);
		}

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();
		getSelectAssignment(assignmentName).waitAndClick(5);
		;
		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_DeleteAssignment().Visible();
			}
		}), Input.wait60);

		getAssignmentAction_DeleteAssignment().waitAndClick(10);

		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment deleted successfully");

	}

	public void editAssgnwithselectedassgn() {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		driver.scrollPageToTop();

		getAssignmentActionDropdown().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_EditAssignment().Visible();
			}
		}), Input.wait60);

		getAssignmentAction_EditAssignment().waitAndClick(3);

	}

	public void assignDocswithpercentagemethod(final String assignmentName, String samplemethod) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getStartingCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		if (samplemethod.equalsIgnoreCase("Percentage")) {
			getsampleMethod().selectFromDropdown().selectByVisibleText("Percent of Selected Docs");
			getCountToAssign().SendKeys("10");
		} else if (samplemethod.equalsIgnoreCase("ParentLevel")) {
			getsampleMethod().selectFromDropdown().selectByVisibleText("Parent Level Docs Only");
		}

		else if (samplemethod.equalsIgnoreCase("InclusiveEmail")) {
			getsampleMethod().selectFromDropdown().selectByVisibleText("Inclusive Email");
		}

		getPersistCB_ExistAssgn().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentToBulkAssign(assignmentName).Visible();
			}
		}), Input.wait60);

		getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);

		getContinueBulkAssign().waitAndClick(15);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		getFinalizeButton().Click();
		bc.VerifySuccessMessage(
				"Bulk Assign has been added to background process. You will get notification on completion.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		// verify total docs count
		String acttotalcount = getAssgnCounts(assignmentName, 7).getText();
		System.out.println(Integer.parseInt(acttotalcount));
		Assert.assertEquals(5, Integer.parseInt(acttotalcount));
	}

	public void CompleteAssgn(String assignmentName) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();

		getSelectAssignment(assignmentName).waitAndClick(5);
		;
		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_EditAssignment().Visible();
			}
		}), Input.wait60);

		Assert.assertTrue(getAssignmentAction_Completedoc().isDisplayed());

		getAssignmentAction_Completedoc().waitAndClick(10);
		bc.getYesBtn().waitAndClick(10);
		bc.VerifySuccessMessage("All Documents successfully completed.");

		Assert.assertEquals(0, Integer.parseInt(getAssgnCounts(assignmentName, 10).getText()));
	}

	public void UnassignDocsfromAssgn(final String assignmentName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getStartingCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		getUnassignbutton().waitAndClick(5);
		getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);

		getContinueBulkAssign().waitAndClick(5);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		bc.VerifySuccessMessage(
				"Bulk UnAssign has been added to background process. You will get notification on completion.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs Unassigned from  " + assignmentName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();

		Assert.assertEquals(0, getSelectAssignmentDocCount(assignmentName).getText());
	}

	public void RedistributeDoc_ReviewerTab() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait60);
		getAssignment_ManageReviewersTab().Click();

		getAssgn_ManageRev_selectrev().waitAndClick(10);

		getAssgn_ManageRev_Action().waitAndClick(10);

		Assert.assertTrue(getAssgn_ManageRev_Action_redistributedoc().isDisplayed());
		getAssgn_ManageRev_Action_redistributedoc().waitAndClick(10);
		bc.getYesBtn().waitAndClick(10);

		getAssgn_Redistributepopup().waitAndClick(10);

		getAssgn_Redistributepopup_save().waitAndClick(10);

		bc.VerifySuccessMessage("Action saved successfully");
		Thread.sleep(2000);

		Assert.assertEquals(0, getAssgn_ManageRev_DisDoc(1).getText());
		Assert.assertEquals(50, getAssgn_ManageRev_DisDoc(2).getText());

		getAssgn_ManageRev_selectrev().waitAndClick(10);
		getAssgn_ManageRev_Action().waitAndClick(10);

		getAssgn_ManageRev_Action_redistributedoc().waitAndClick(10);
		bc.VerifyWarningMessage("No documents available to do the selected action");

	}

	public void CopyAssignment(final String assignmentName, String codingfrom) {

		String drawpool = getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class");
		System.out.println(drawpool);

		driver.scrollingToBottomofAPage();
		String permission1 = getAssgn_permissions(14).GetAttribute("class");
		String permission2 = getAssgn_permissions(24).GetAttribute("class");
		String permission3 = getAssgn_permissions(25).GetAttribute("class");
		String permission4 = getAssgn_permissions(29).GetAttribute("class");

		driver.scrollPageToTop();
		getAssignment_BackToManageButton().waitAndClick(10);

		getAssignmentActionDropdown().waitAndClick(10);

		Assert.assertTrue(getAssignmentAction_CopyAssignment().isDisplayed());

		getAssignmentAction_CopyAssignment().waitAndClick(5);

		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Record copied successfully");

		getSelectCopyAssignment().waitAndClick(10);

		getAssignmentActionDropdown().waitAndClick(10);
		getAssignmentAction_EditAssignment().waitAndClick(5);

		Assert.assertTrue(drawpool.equalsIgnoreCase("false"));
		System.out.println(getAssignmentCodingFormDropDown().selectFromDropdown().getFirstSelectedOption().getText());
		Assert.assertTrue(getAssignmentCodingFormDropDown().selectFromDropdown().getFirstSelectedOption().getText()
				.equalsIgnoreCase(codingfrom));

		try {
			Assgnwithspecialchars("AssignmentNameWith@test");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.scrollingToBottomofAPage();
		Assert.assertTrue(permission1.equalsIgnoreCase("false"));
		Assert.assertTrue(permission2.equalsIgnoreCase("true"));
		Assert.assertTrue(permission3.equalsIgnoreCase("false"));
		Assert.assertTrue(permission4.equalsIgnoreCase("false"));
	}

	public void completedocsinassgn(String assignmentName) throws InterruptedException {
		getSelectAssignment(assignmentName).waitAndClick(5);
		;
		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_Completedoc().Visible();
			}
		}), Input.wait60);

		getAssignmentAction_Completedoc().waitAndClick(3);

		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("All Documents successfully completed.");

		bc.impersonateRMUtoReviewer();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentAsReviewer(assignmentName).Visible();
			}
		}), Input.wait30);

		Assert.assertTrue(getSelectAssignmentAsReviewer(assignmentName).isDisplayed());
		System.out.println(getrev_assgnprogress(assignmentName).getText());

		Assert.assertTrue(getrev_assgnprogress(assignmentName).getText().equalsIgnoreCase("100%"));
		Assert.assertTrue(getrev_assgnprogress(assignmentName).getText().equalsIgnoreCase("100%"));

		// Verify Yellow color of highlighted text
		WebElement activeElement = driver.switchTo().activeElement();
		String HighlightedColor = driver.FindElementByCssSelector("rect[style*='#FFFF00']").GetCssValue("fill");
		System.out.println(HighlightedColor);

		Assert.assertEquals(HighlightedColor, "rgb(255, 255, 0)");

		/*
		 * if (HighlightedColor.equalsIgnoreCase("rgb(255, 255, 0)")) {
		 * 
		 * System.out.println("Annotation_Highlight Yellow color displayed properly"); }
		 * else {
		 * System.out.println("Annotation_Highlight Yellow color not displayed properly"
		 * ); }
		 */

	}

    /**
     * @Modified 03/10/2022
     * @param assignmentName
     */
	public void Viewindoclistfromassgn(String assignmentName) {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		assignmentPagination(assignmentName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_ViewinDocList().Visible();
			}
		}), Input.wait60);
		getAssignmentAction_ViewinDocList().Click();

		final DocListPage dp = new DocListPage(driver);
		dp.getDocList_info().WaitUntilPresent();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return !dp.getDocList_info().getText().isEmpty();
			}
		}), Input.wait60);
// Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",",
// ""),"Showing 1 to 10 of "+Input.pureHitSeachString1+" entries");
		System.out.println("Expected docs(" + Input.pureHitSeachString1 + ") are shown in doclist");

	}

	public void Viewindocviewfromassgn(String assignmentName) {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait30);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait30);
		driver.scrollingToBottomofAPage();
		getSelectAssignment(assignmentName).waitAndClick(15);

		driver.scrollPageToTop();

		getAssignmentActionDropdown().waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_ViewinDocView().Visible();
			}
		}), Input.wait60);
		getAssignmentAction_ViewinDocView().Click();

		DocViewPage dv = new DocViewPage(driver);
		dv.getDocView_info().WaitUntilPresent();
		Assert.assertEquals(dv.getDocView_info().getText().toString(), "of " + Input.pureHitSeachString1 + " Docs");
		System.out.println("Expected docs(" + Input.pureHitSeachString1 + ") are shown in docView");
	}

	public void createnewquickbatch_chronologic_withoutReviewer(final String assignmentName, String codingForm) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assignmentName);

		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentCodingFormDropDown().Visible();
				}
			}), Input.wait30);
			getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
		} catch (Exception e) {
			getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertTrue(getQuickBatch_optimizedorder().Selected());
		getQuickBatch_chornologicorder().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQuickBatch_Doccount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		int doccount = Integer.parseInt(getQuickBatch_Doccount().getText());
		System.out.println("Doc Count is :" + doccount);

		getContinueBulkAssign().waitAndClick(30);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getFinalizeButton().waitAndClick(30);

		bc.VerifySuccessMessage(
				"Quick Batch Assign has been added to background process. You will get notification on completion.");
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		bc.BckTaskMessageverify("QuickBatch");

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();
		getSelectAssignment(assignmentName).waitAndClick(15);

		System.out.println(Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText().toString()));

		// Assert.assertEquals(Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText().toString()),doccount);

		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_EditAssignment().Visible();
			}
		}), Input.wait60);

		getAssignmentAction_EditAssignment().waitAndClick(15);
		getAssgnGrp_Create_DrawPoolCount().WaitUntilPresent();

		System.out.println(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"));
		System.out.println(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value").toString());
		Assert.assertEquals(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"), "true");

		Assert.assertEquals(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value").toString(), "100");
		// assertion.assertAll();
	}

	public void createnewquickbatch_Optimized_withReviewer(final String assignmentName, String codingForm,
			String Reviewercheck) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assignmentName);
		SelectCodingform(codingForm);

		if (Reviewercheck.equals("AllRev")) {
			getQuickBatch_Selectallrev().waitAndClick(10);
		} else if (Reviewercheck.equals("selectrmu")) {
			getSelectUserToAssign(Input.rmu1userName).waitAndClick(10);
			getSelectUserToAssign(Input.rmu2userName).waitAndClick(10);
		} else if (Reviewercheck.equals("selectrev")) {
			getSelectUserToAssign(Input.rev1userName).waitAndClick(10);
			getSelectUserToAssign(Input.rev2userName).waitAndClick(10);
		}
		final String doccount = getQuickBatch_Doccount().getText();
		System.out.println(doccount);
		UtilityLog.info(doccount);

		getContinueBulkAssign().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		getAssgn_LikeDoc_Familytoggle().waitAndClick(30);
		getAssgn_LikeDoc_Emailtoggle().waitAndClick(10);
		getAssgn_LikeDoc_Neartoggle().waitAndClick(10);

		String totaldoccount = getFinalCount().getText();
		Integer.parseInt(totaldoccount);
		System.out.println("Doc Count:-" + totaldoccount);
		UtilityLog.info("Doc Count:-" + totaldoccount);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getFinalizeButton().waitAndClick(30);

		bc.VerifySuccessMessage(
				"Quick Batch Assign has been added to background process. You will get notification on completion.");
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);
		UtilityLog.info("Docs assigned to  " + assignmentName);

		bc.BckTaskMessageverify("QuickBatch");

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();
		getSelectAssignment(assignmentName).waitAndClick(15);

		System.out.println(Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText().toString()));
		UtilityLog.info(Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText().toString()));

		assertion.assertEquals(Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText().toString()),
				totaldoccount);

		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_EditAssignment().Visible();
			}
		}), Input.wait60);

		getAssignmentAction_EditAssignment().waitAndClick(15);
		getAssgnGrp_Create_DrawPoolCount().WaitUntilPresent();

		Assert.assertEquals(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"), "true");
		System.out.println(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"));
		UtilityLog.info(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"));
		System.out.println(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value"));
		UtilityLog.info(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value"));

		Assert.assertEquals(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value"), "100");
		// assertion.assertAll();
	}

	public void ValidateReviewerlistquickbatch(String pendinguser) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);

		// verify all users are available
		System.out.println(getSelectUserToAssign(Input.pa1userName).Exists());
		// Assert.assertTrue(getSelectUserToAssign(Input.da1userName).Exists());
		Assert.assertTrue(getSelectUserToAssign(Input.pa1userName).Exists());
		Assert.assertTrue(getSelectUserToAssign(Input.pa2userName).Exists());
		Assert.assertTrue(getSelectUserToAssign(Input.rmu1userName).Exists());
		Assert.assertTrue(getSelectUserToAssign(Input.rmu2userName).Exists());
		Assert.assertTrue(getSelectUserToAssign(Input.rev1userName).Exists());
		Assert.assertTrue(getSelectUserToAssign(Input.rev2userName).Exists());
		Assert.assertTrue(getSelectUserToAssign(pendinguser).Exists());

	}

	public void Quickbatchfailure() {

		// click on continue button without selecting mandatory fields
		getContinueBulkAssign().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQuickBatch_NameErrormsg().Visible();
			}
		}), Input.wait30);
		String expnamemsg = getQuickBatch_NameErrormsg().getText();
		System.out.println(expnamemsg);
		Assert.assertEquals("Name required", expnamemsg);

		String expcodingmsg = getQuickBatch_CodingErrormsg().getText();
		System.out.println(expcodingmsg);
		Assert.assertEquals("Please select Coding Form", expcodingmsg);

		String aasgnname = "AutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTesting";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait30);
		getAssignmentName().SendKeys(aasgnname);

		getAssignmentName().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQuickBatch_NameErrormsg().Visible();
			}
		}), Input.wait30);
		String expmsg = getQuickBatch_NameErrormsg().getText();
		System.out.println(expmsg);
		Assert.assertEquals("Maximum length should be 250", expmsg);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentCodingFormDropDown().Visible();
			}
		}), Input.wait30);
		getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);

		getContinueBulkAssign().waitAndClick(30);
	}

	public void createnewquickbatch(final String assignmentName, String codingForm)
			throws NumberFormatException, InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assignmentName);

		SelectCodingform(codingForm);

		String doccount = getQuickBatch_Doccount().getText();
		Integer.parseInt(doccount);
		System.out.println(doccount);

		getContinueBulkAssign().waitAndClick(30);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getFinalizeButton().waitAndClick(30);

		bc.VerifySuccessMessage(
				"Quick Batch Assign has been added to background process. You will get notification on completion.");
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();
		getSelectAssignment(assignmentName).waitAndClick(15);

		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_EditAssignment().Visible();
			}
		}), Input.wait60);

		getAssignmentAction_EditAssignment().waitAndClick(15);

		getAssignmentName().SendKeys(assignmentName + "New");
		getAssignmentSaveButton().waitAndClick(10);
		bc.VerifySuccessMessage("Assignment updated successfully");
		bc.CloseSuccessMsgpopup();
		Thread.sleep(2000);

		addReviewerAndDistributeDocs(assignmentName + "New", Integer.parseInt(doccount));

		getAssignmentActionDropdown().waitAndClick(10);

		getAssignmentAction_CopyAssignment().waitAndClick(5);

		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Record copied successfully");

		// view all docs in doclist
		Viewindoclistfromassgn(assignmentName + "New");

		// view all docs in docview
		Viewindocviewfromassgn(assignmentName + "New");

		// delete assignment
		deleteAssignment(assignmentName + "New");

	}

	public void createAssignmentwithkeywords(String assignmentName, String codingForm, String[] keys)
			throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentActionDropdown().Visible();
			}
		}), Input.wait60);
		Thread.sleep(2000);
		getAssignmentActionDropdown().waitAndClick(10);

		getAssignmentAction_NewAssignment().WaitUntilPresent();
		Thread.sleep(2000);
		getAssignmentAction_NewAssignment().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assignmentName);
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);

		// verify keywords
		getAssgn_Keywordsbutton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_Keywordspopup().Visible();
			}
		}), Input.wait30);

		List<WebElement> allvalues = getAssgn_AllKeywordscheck().FindWebElements();
		List<String> expkey = new ArrayList<String>();
		for (int j = 1; j <= allvalues.size(); j++) {
			System.out.println(allvalues.get(j).getAttribute("checked").toString());
			System.out.println(allvalues.get(j).getText());
			if (allvalues.get(j).getAttribute("checked").equalsIgnoreCase("checked")) {
				System.out.println("Pass");

			} else {
				System.out.println("FAIL");
			}
		}

		// permissions
		driver.scrollingToBottomofAPage();
		getSelectSavePermission().ScrollTo();
		getSelectSavePermission().waitAndClick(5);
		driver.scrollPageToTop();
		// getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
		getAssignmentSaveButton().waitAndClick(5);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

	}

	public void Impersonateusercompletedoc(String assignmentName) {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();

		getSelectAssignment(assignmentName).waitAndClick(5);
		;
		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_EditAssignment().Visible();
			}
		}), Input.wait60);

		getAssignmentAction_Completedoc().waitAndClick(10);
		bc.VerifyErrorMessage("Impersonated User cant do this operation");

	}

	/**
	 * @Author Indium-Baskar date: 10/8/2021 Modified date: 24/8/2021 Modified
	 *         by:Baskar
	 * @Description:Method created for create new assignment coding stamp applied
	 *                     toggle should off
	 * @param assignmentName
	 * @param codingForm
	 * @throws InterruptedException
	 */
	public void createAssignmentCodingStampToggleOFF(String assignmentName, String codingForm)
			throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_NewAssignmnet().Visible();
			}
		}), Input.wait30);
		getAssgn_NewAssignmnet().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbulkassgnpopup().Visible();
			}
		}), Input.wait30);
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());
		bc.waitTillElemetToBeClickable(getContinueBulkAssign());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueBulkAssign().Visible();
			}
		}), Input.wait30);
		getContinueBulkAssign().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_TotalCount().Visible();
			}
		}), Input.wait30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalizeButton().Visible();
			}
		}), Input.wait30);
		getFinalizeButton().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait30);
		getAssignmentName().SendKeys(assignmentName);
		getParentAssignmentGroupName().isDisplayed();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectedClassification().Visible();
			}
		}), Input.wait30);
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);
		// permissions
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_SaveWithoutCompleteingToggle().Visible();
			}
		}), Input.wait30);
		getAssgn_SaveWithoutCompleteingToggle().waitAndClick(5);
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentSaveButton().Visible();
			}
		}), Input.wait30);
		getAssignmentSaveButton().waitAndClick(5);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait30);
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssgn_Pagination());
		String nextbutton = getAssgn_Pagination().GetAttribute("class");
		while (!nextbutton.equals("paginate_button next disabled")) {
			getAssgn_Pagination().waitAndClick(5);
			nextbutton = getAssgn_Pagination().GetAttribute("class");
			getSelectAssignment(assignmentName).waitAndClick(5);
			break;
		}
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentActionDropdown().Visible();
			}
		}), Input.wait30);
		getAssignmentActionDropdown().waitAndClick(10);
		bc.waitForElement(getAssignmentAction_EditAssignment());
		bc.waitTillElemetToBeClickable(getAssignmentAction_EditAssignment());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_EditAssignment().Visible();
			}
		}), Input.wait30);
		getAssignmentAction_EditAssignment().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment_ManageReviewersTab().Visible();
			}
		}), Input.wait30);
		getAssignment_ManageReviewersTab().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddReviewersBtn().Visible();
			}
		}), Input.wait30);
		getAddReviewersBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserToAssig().Visible();
			}
		}), Input.wait30);
		getSelectUserToAssig().WaitUntilPresent().ScrollTo();
		getSelectUserToAssig().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdduserBtn().Visible();
			}
		}), Input.wait30);
		getAdduserBtn().Click();
		bc.VerifySuccessMessage("Action saved successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDistributeTab().Visible();
			}
		}), Input.wait30);
		getDistributeTab().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserInDistributeTab().Visible();
			}
		}), Input.wait30);
		getSelectUserInDistributeTabs().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDistributeBtn().Visible();
			}
		}), Input.wait30);
		getDistributeBtn().Click();
		bc.VerifySuccessMessage("Action saved successfully");

	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date:30/9/2021 Modified
	 *         by:Baskar *
	 * @Description: Assignment creation
	 * @param assignmentName
	 * @param codingForm
	 */
	// Assignment creating and saving the assignment
	// After Saving From action Drop down selecting the assignment with edit
	// assignment
	// Again coming back to assignment page for editing the assignmnet

	public void assignmentCreation(String assignmentName, String codingForm) {
		bc.waitForElement(getAssgn_NewAssignmnet());
		bc.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
		getAssgn_NewAssignmnet().waitAndClick(5);
		bc.waitForElement(getbulkassgnpopup());
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());
		bc.waitForElement(getPersistCB_NewAssgn());
		getPersistCB_NewAssgn().waitAndClick(15);
		try {
			bc.waitForElement(getContinueBulkAssign());
			bc.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(30);
		} catch (Exception e) {
			bc.waitForElement(getContinueBulkAssign());
			bc.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(30);
		}
		bc.waitForElement(getAssgn_TotalCount());
		bc.waitForElement(getFinalizeButton());
		bc.waitTillElemetToBeClickable(getFinalizeButton());
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		try {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().SendKeys(assignmentName);
		} catch (Exception e) {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().Clear();
			getAssignmentName().SendKeys(assignmentName);
		}
		getParentAssignmentGroupName().isDisplayed();
		bc.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		// bc.waitForElement(getAssignmentCodingFormDropDown());
		SelectCodingform(codingForm);
		bc.waitForElement(getAssignmentSaveButton());
		bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		try {
			if (getAssignmentErrorText().isElementAvailable(5)) {
				driver.waitForPageToBeReady();
				bc.waitForElement(getAssignmentName());
				getAssignmentName().SendKeys(assignmentName);
				bc.waitForElement(getAssignmentSaveButton());
				getAssignmentSaveButton().waitAndClick(5);
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);

		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_Pagination());
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				getSelectAssignment(assignmentName).waitAndClick(5);
				driver.scrollPageToTop();
				bc.waitForElement(getAssignmentActionDropdown());
				getAssignmentActionDropdown().Click();
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				bc.waitForElement(getAssgnPaginationNextButton());
				getAssgnPaginationNextButton().Click();
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_EditAssignment());
		bc.waitTillElemetToBeClickable(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(5);

	}

	/**
	 * @author Indium-Baskar date: 24/8/2021 Modified date: NA
	 * @description: Toggle Enabled for CodingStamp and Save without complete
	 */
//      permission for savewithoutcompleteingToggle
	public void toggleSaveButton() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		getAssgn_SaveWithoutCompleteingToggle().WaitUntilPresent().ScrollTo();
		bc.waitForElement(getAssgn_SaveWithoutCompleteingToggle());
		bc.waitTillElemetToBeClickable(getAssgn_SaveWithoutCompleteingToggle());
		getAssgn_SaveWithoutCompleteingToggle().waitAndClick(10);
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(10);
		bc.CloseSuccessMsgpopup();

	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date: NA <a>
	 * @description: Toggle Enabled for CodingStamp and Save without complete
	 */

	public void toggleCodingStampEnabled() {
		// permissions
		// Toggle enbaled for SaveWithoutCompleteing
		// Toggle enabled for CodingStampAplliedToggle
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_SaveWithoutCompleteingToggle());
		getAssgn_SaveWithoutCompleteingToggle().waitAndClick(5);
		bc.waitForElement(getAssgn_CodingStampAplliedToggle());
		getAssgn_CodingStampAplliedToggle().waitAndClick(5);
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date:28/8/2021 Modified
	 *         by:Baskar
	 * @Description: Assignment Distributing to reviewer
	 */

	public void assignmentDistributingToReviewer() {
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignment_ManageReviewersTab());
		bc.waitTillElemetToBeClickable(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		bc.waitTillElemetToBeClickable(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssig().WaitUntilPresent().ScrollTo();
		bc.waitTillElemetToBeClickable(getSelectUserToAssig());
		getSelectUserToAssig().waitAndClick(10);
		bc.waitForElement(getAdduserBtn());
		bc.waitTillElemetToBeClickable(getAdduserBtn());
		getAdduserBtn().waitAndClick(10);
		bc.waitForElement(getDistributeTab());
		bc.waitTillElemetToBeClickable(getDistributeTab());
		getDistributeTab().waitAndClick(10);
		bc.waitForElement(getSelectUserInDistributeTabs());
		bc.waitTillElemetToBeClickable(getSelectUserInDistributeTabs());
		getSelectUserInDistributeTabs().waitAndClick(10);
		bc.waitForElement(getDistributeBtn());
		bc.waitTillElemetToBeClickable(getDistributeBtn());
		getDistributeBtn().waitAndClick(10);
		if (getWarningTxtDis().isElementAvailable(1)) {
			try {
				String disWarngTxt = getWarningTxtDis().getText().trim();
				String actual = "Please enter a valid number to distribute";
				if (disWarngTxt.equals(actual)) {
					driver.getWebDriver().navigate().refresh();
					driver.waitForPageToBeReady();
					bc.waitForElement(getDistributeBtn());
					getDistributeBtn().waitAndClick(10);
					bc.waitForElement(getSelectUserInDistributeTabs());
					getSelectUserInDistributeTabs().waitAndClick(10);
					bc.waitForElement(getDistributeBtn());
					getDistributeBtn().waitAndClick(10);
				}
			} catch (org.openqa.selenium.NoSuchElementException e) {
				e.printStackTrace();
			}
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date: 28/8/2021
	 * @Description:Assignment Distributing to ReviewerManager and impersonate as
	 *                         Reviewer
	 */

	public void assignmentDistributingToReviewerManager() {
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssigReviewerManager());
		getSelectUserToAssigReviewerManager().WaitUntilPresent().ScrollTo();
		getSelectUserToAssigReviewerManager().waitAndClick(10);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(10);
//		bc.VerifySuccessMessage("Action saved successfully");
//		bc.CloseSuccessMsgpopup();
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(10);
		bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
		getSelectUserInDistributeTabsReviewerManager().waitAndClick(10);
		bc.waitForElement(getDistributeBtn());
		getDistributeBtn().waitAndClick(10);
		if (getWarningTxtDis().isElementAvailable(5)) {
			try {
				String disWarngTxt = getWarningTxtDis().getText().trim();
				String actual = "Please enter a valid number to distribute";
				if (disWarngTxt.equals(actual)) {
					driver.getWebDriver().navigate().refresh();
					driver.waitForPageToBeReady();
					bc.waitForElement(getDistributeBtn());
					getDistributeBtn().waitAndClick(10);
					bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
					getSelectUserInDistributeTabsReviewerManager().waitAndClick(10);
					bc.waitForElement(getDistributeBtn());
					getDistributeBtn().waitAndClick(10);
				}
			} catch (org.openqa.selenium.NoSuchElementException e) {
				e.printStackTrace();
			}
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @Author Indium-Baskar date: 10/8/2021 Modified date: 24/8/2021 Modified
	 *         by:Baskar
	 * @Description: Method created for create new assignment coding stamp applied
	 *               toggle should ON context of assignment DocView/Coding Forms
	 * @param assignmentName
	 * @param codingForm
	 */

	public void createAssignmentNew(String assignmentName, String codingForm) {
		assignmentCreation(assignmentName, codingForm);
		// permissions
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_SaveWithoutCompleteingToggle());
		bc.waitTillElemetToBeClickable(getAssgn_SaveWithoutCompleteingToggle());
		getAssgn_SaveWithoutCompleteingToggle().waitAndClick(10);
		bc.waitForElement(getAssgn_CodingStampAplliedToggle());
		bc.waitTillElemetToBeClickable(getAssgn_CodingStampAplliedToggle());
		getAssgn_CodingStampAplliedToggle().waitAndClick(10);
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(10);
		assignmentDistributingToReviewer();
	}

	/**
	 * @author Jayanthi.ganesan Modified date-3/9/21
	 * @param assgngrpName
	 * @param cascadeSettings ---"Yes" for cascade group creation,"No" for non
	 *                        cascade group creation.
	 * @description This method will create cascade and non cascade assignment group
	 * @throws InterruptedException
	 */
	public void createCascadeNonCascadeAssgnGroup(String assgngrpName, String cascadeSettings)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitForElement(getAssgGrptionDropdown());
		bc.waitTillElemetToBeClickable(getAssgGrptionDropdown());
		getAssgGrptionDropdown().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssgnGrp_Create());
		bc.waitForElement(getAssgnGrp_Create());
		getAssgnGrp_Create().waitAndClick(10);
		bc.waitForElement(getAssignmentName());
		driver.waitForPageToBeReady();
		getAssignmentName().SendKeys(assgngrpName);
		driver.waitForPageToBeReady();
		if ("Yes".equalsIgnoreCase(cascadeSettings)) {
			bc.waitForElement(getAssgngrp_CascadeSetting());
			getAssgngrp_CascadeSetting().waitAndClick(5);
		}
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will select assignment group
	 * @param assgnGrpName
	 */
	public void selectAssignmentGroup(String assgnGrpName) {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssgnGrp_Select(assgnGrpName));
		// driver.scrollingToElementofAPage(getAssgnGrp_Select(assgnGrpName));
		getAssgnGrp_Select(assgnGrpName).waitAndClick(15);

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will select a child assignment group from parent
	 *              group
	 * @param parentAssgnGroupName
	 * @param childAssgnGroupName
	 */
	public void selectChildAssgnGrpFromParentAssgnGroup(String parentAssgnGroupName, String childAssgnGroupName) {

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getArrowBtn_AssgnGroup(parentAssgnGroupName));
		driver.waitForPageToBeReady();
		getArrowBtn_AssgnGroup(parentAssgnGroupName).waitAndClick(3);
		bc.waitForElement(getAssgnGrp_Select(childAssgnGroupName));
		getAssgnGrp_Select(childAssgnGroupName).waitAndClick(3);

	}

	/**
	 * @author Jayanthi.ganesan Modified Date-3/9/21
	 * @param assignmentName
	 * @param codingForm
	 * @throws InterruptedException
	 * @description This method will create a assignment.
	 */
	public void createAssignmentFromAssgnGroup(String assignmentName, String codingForm) throws InterruptedException {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getAssignmentActionDropdown());
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssignmentAction_NewAssignment());
		bc.waitForElement(getAssignmentAction_NewAssignment());
		getAssignmentAction_NewAssignment().waitAndClick(20);
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentName());
		getAssignmentName().SendKeys(assignmentName);
		driver.waitForPageToBeReady();
		getParentAssignmentGroupName().isDisplayed();
		bc.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		driver.waitForPageToBeReady();
		SelectCodingform(codingForm);
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(3);
		bc.stepInfo("Assignment " + assignmentName + " created with CF " + codingForm);

	}

	/**
	 * @author Jayanthi.ganesan modified on-16/9/21
	 * @param assgnGroupName_From
	 * @param assgnGroupName_To
	 * @param this                method will perform a drag and drop between
	 *                            assignment groups.
	 * @throws InterruptedException
	 */
	public void dragAndDrop(String assgnGroupName_From, String assgnGroupName_To) throws InterruptedException {
		driver.scrollingToBottomofAPage();
		driver.scrollingToElementofAPage(getAssgnGrp_Select(assgnGroupName_From));
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(getAssgnGrp_Select(assgnGroupName_From));
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
		wait.until(ExpectedConditions.elementToBeClickable(getAssgnGrp_Select(assgnGroupName_From).getWebElement()));
		Actions actions = new Actions(driver.getWebDriver());
		bc.waitForElement(getAssgnGrp_Select(assgnGroupName_From));
		actions.clickAndHold(getAssgnGrp_Select(assgnGroupName_From).getWebElement());
		actions.moveToElement(getAssgnGrp_Select(assgnGroupName_To).getWebElement());
		actions.release(getAssgnGrp_Select(assgnGroupName_To).getWebElement());
		actions.build().perform();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @decription this method will handle the warning message when a assignment is
	 *             moved to assignment group
	 */
	public void verifyMoveWarning() {
		try {
			SoftAssert softAssertion = new SoftAssert();
			String expectedMessage = "Existing configuration of the assignment will be overwritten by the new parent assignment group's cascaded settings";
			softAssertion.assertEquals(getMoveNotificationMessage().getText(), expectedMessage);
			bc.stepInfo("alert displayed");
			bc.getYesBtn().Click();
			bc.stepInfo("Assignment moved to another parent assignment group as per the test case");
		} catch (Exception e) {
			bc.failedStep("NO alert displayed Failed to verify notification");
		}
	}

	/**
	 * @author Jayanthi.ganesan Modified date-3/9/21
	 * @param assignmentName
	 * @param parentAssgnGroupName
	 * @param childAssgnGroupName
	 * @pram validateDandD---It should be yes if we perform a move between cascade
	 *       to non cascade group(i.e move between assgn groups which doesn't get
	 *       move warning message) No--for move between groups where we get move
	 *       Configuration change warning message.
	 * 
	 * @throws InterruptedException
	 * @description This method will verify Edit assignment functionality after
	 *              performing a Move between any two assignment groups.
	 */
	public void validateEditAssignmentBySelectingAssgnFromChildAssgnGrp(String assignmentName,
			String parentAssgnGroupName, String childAssgnGroupName, String validateDandD) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getArrowBtn_AssgnGroup(parentAssgnGroupName));
		driver.scrollingToBottomofAPage();
		driver.scrollingToElementofAPage(getArrowBtn_AssgnGroup(parentAssgnGroupName));
		getArrowBtn_AssgnGroup(parentAssgnGroupName).waitAndClick(5);
		bc.waitForElement(getAssgnGrp_Select(childAssgnGroupName));
		getAssgnGrp_Select(childAssgnGroupName).waitAndClick(5);
		driver.scrollPageToTop();
		bc.waitForElement(getSelectAssignment(assignmentName));
		getSelectAssignment(assignmentName).waitAndClick(5);
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(5);
		bc.waitForElement(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(5);
		if ("Yes".equalsIgnoreCase(validateDandD)) {
			driver.waitForPageToBeReady();
			validateDragAndDrop();
		}
		bc.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("2LR");
		getAssignmentSaveButton().waitAndClick(3);
		bc.waitForElement(bc.getSuccessMsg());
		bc.VerifySuccessMessage("Assignment updated successfully");
		bc.passedStep("Sucessfully validated the edit assignment functionality after assignment moved "
				+ "between assignments group as per test case ");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(3);
		bc.waitForElement(getAssignmentAction_DeleteAssignment());
		getAssignmentAction_DeleteAssignment().waitAndClick(3);
		bc.getYesBtn().waitAndClick(3);
		bc.VerifySuccessMessage("Assignment deleted successfully");

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method will validate the Drag And Drop action is done
	 *              correctly if assignment group move doesnt display any move
	 *              notification warning popup .
	 */
	public void validateDragAndDrop() {
		String expectedMessage = "Cascade Settings is DISABLED for this Assignment";
		driver.scrollingToElementofAPage(getCascadeSettingsText());
		bc.waitForElement(getCascadeSettingsText());
		if ((getCascadeSettingsText().getText()).equalsIgnoreCase(expectedMessage)) {
			bc.passedStep("Assignment moved to another parent assignment group as per the test case");
		} else {
			bc.failedStep("Drag and Drop Is not performed and Assign Group is not moved as expected");
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param assignmentName
	 * @throws InterruptedException
	 * @description this method will delete assignment.Here I used pagination
	 *              handling concept.
	 */
	public void deleteAssgnmntUsingPagination(final String assignmentName) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				getSelectAssignment(assignmentName).ScrollTo();
				if (!getSelectAssignmentHighlightCheck(assignmentName).isElementAvailable(5)) {
					getSelectAssignment(assignmentName).waitAndClick(3);
					// getSelectAssignment(assignmentName).waitAndClick(5);
				}
				driver.scrollPageToTop();
				bc.waitForElement(getAssignmentActionDropdown());
				getAssignmentActionDropdown().Click();
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				bc.waitForElement(getAssgnPaginationNextButton());
				getAssgnPaginationNextButton().Click();
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_DeleteAssignment());
		getAssignmentAction_DeleteAssignment().waitAndClick(3);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment deleted successfully");

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param assignmentName
	 * @throws InterruptedException
	 * @description this method will edit assignment.Here I used pagination handling
	 *              concept.
	 */
	public void editAssignmentUsingPaginationConcept(final String assignmentName) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		bc.waitForElement(getNumberOfAssignmentsToBeShown());

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				if (!getSelectAssignmentHighlightCheck(assignmentName).isElementAvailable(5)) {
					getSelectAssignment(assignmentName).waitAndClick(5);
				}
				driver.scrollPageToTop();
				getAssignmentActionDropdown().waitAndClick(3);
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(3);
		bc.stepInfo("Assignment edit option is clicked");

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param childAssgnGroupName
	 * @param parentAssgnGroupName
	 * @throws InterruptedException
	 * @description this method will delete child assignment group .
	 */
	public void DeleteChildAssgnGroup(String childAssgnGroupName, String parentAssgnGroupName)
			throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getAssgGrptionDropdown());
		bc.waitForElement(getArrowBtn_AssgnGroup(parentAssgnGroupName));
		getArrowBtn_AssgnGroup(parentAssgnGroupName).waitAndClick(5);
		bc.waitForElement(getAssgnGrp_Select(childAssgnGroupName));
		getAssgnGrp_Select(childAssgnGroupName).waitAndClick(3);
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		getAssgGrptionDropdown().waitAndClick(3);
		getAssgnGrp_Delete().WaitUntilPresent();
		getAssgnGrp_Delete().waitAndClick(3);
		bc.getYesBtn().waitAndClick(3);
		bc.VerifySuccessMessage("Assignment group deleted successfully");

	}

	/**
	 * @author Jayanthi
	 * @param assignmentName
	 * @throws InterruptedException
	 * @description-this methods will complete the distributed docs assigned to
	 *                   reviewer.
	 */
	public void completeDistributesDocsByReviewer(String assignmentName) throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(assignmentName).waitAndClick(2);
		bc.waitForElement(completeBtn());
		getTotalRecords().waitAndClick(15);
		String recordsLabel = getTotalRecords().getText();
		System.out.println("recordslabel " + recordsLabel);
		String[] totalRecords = recordsLabel.split(" ");
		String count = totalRecords[1];
		System.out.println("count " + count);
		int records = Integer.parseInt(count);
		System.out.println("records " + records);
		for (int i = 0; i < records; i++) {
			driver.waitForPageToBeReady();
			if (codingFormRadioButton().isElementAvailable(5) == false) {
				bc.waitForElement(SelectCodingFormCheckBoxes("Responsive"));
				SelectCodingFormCheckBoxes("Responsive").waitAndClick(3);
				bc.waitForElement(SelectCodingFormCheckBoxes("Not_Privileged"));
				SelectCodingFormCheckBoxes("Not_Privileged").waitAndClick(3);
			}
			bc.waitForElement(completeBtn());
			completeBtn().isElementAvailable(10);
			editCodingForm(Input.searchString1);
			completeBtn().waitAndClick(5);
		}
		dashBoardBtn().waitAndClick(2);
		Boolean status = completeStatusBtn(assignmentName).isElementAvailable(5);
		assertion.assertTrue(status);
		bc.stepInfo("All the docs are completed");
	}

	/**
	 * @author Jayanthi.Ganesan Modified Date-7/9/21
	 * @param instruction
	 * @param Type
	 * @throws InterruptedException
	 * @description This method will add instruction(using configure button)to the
	 *              assignment.
	 */

	public void ValidateInstructionPopUpInEditAndNewAssignment(String instruction, String Type)
			throws InterruptedException {
		driver.scrollingToBottomofAPage();
		bc.waitForElement(configureButton());
		configureButton().waitAndClick(3);
		bc.waitForElement(instructionTextBox());
		instructionTextBox().SendKeys(instruction);
		instructionOkButton().waitAndClick(2);
		driver.scrollPageToTop();
		bc.waitTillElemetToBeClickable(SaveButton());
		SaveButton().waitAndClick(15);
		if (Type == "Edit") {
			bc.VerifySuccessMessage("Assignment updated successfully");
			bc.CloseSuccessMsgpopup();
		}
		bc.stepInfo("The text " + instruction + "entered in instruction pop-up textbox is updated in assignment");
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param assignmentName
	 * @param expectedInstruction
	 * @throws InterruptedException
	 * @description This method update will validate whether the instructions added
	 *              to the assignment by RMU is displayed for Reviewer.
	 */
	public void verifyInstructionTextInDistDocs(String assignmentName, String expectedInstruction)
			throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		bc.waitForElement(getInstructionTextButton(assignmentName));
		getInstructionTextButton(assignmentName).waitAndClick(10);
		bc.waitForElement(getInstructionText());
		String actualInstuction = getInstructionText().getText();
		assertion.assertEquals(expectedInstruction, actualInstuction);
		bc.passedStep("The instruction -" + expectedInstruction + " is displayed as updated by RMU user");
		assertion.assertAll();
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param assignmentName
	 * @param codingForm
	 * @throws InterruptedException
	 * @description This method will validate whether the sort metadata option is
	 *              enabled for cascade group.
	 */

	public void validateMetadataOption(String assignmentName, String codingForm) throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		bc.waitForElement(getAssignmentName());
		driver.waitForPageToBeReady();
		getAssignmentName().SendKeys(assignmentName);
		driver.waitForPageToBeReady();
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);
		bc.waitForElement(metaDataSort());
		if (metaDataSort().isDisplayed() == true) {
			bc.passedStep("The sort by metadata option is as enabled by the cascading group options");
		} else {
			bc.failedStep("The sort by metadata option is not enabled by the cascading group options");
			assertion.fail("The sort by metadata option is not enabled by the cascading group options");
		}

	}

	/**
	 * 
	 * @author Jayanthi.Ganesan
	 * @param assgngrpName
	 * @throws InterruptedException
	 * @description this method will create cascade assignment group with sort by
	 *              metadata option enabled.
	 */
	public void createCascadeAssgnGroupWithSortBymetadata(String assgngrpName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitForElement(getAssgGrptionDropdown());
		bc.waitTillElemetToBeClickable(getAssgGrptionDropdown());
		getAssgGrptionDropdown().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssgnGrp_Create());
		bc.waitForElement(getAssgnGrp_Create());
		getAssgnGrp_Create().waitAndClick(10);
		bc.waitForElement(getAssignmentName());
		driver.waitForPageToBeReady();
		getAssignmentName().SendKeys(assgngrpName);
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssgngrp_CascadeSetting());
		getAssgngrp_CascadeSetting().waitAndClick(5);
		sortBymetaDataBtn().waitAndClick(2);
		bc.waitForElement(SelectMetadata());
		SelectMetadata().waitAndClick(3);
		bc.waitForElement(SelectMetadataFromDropDown("DocFileName"));
		SelectMetadataFromDropDown("DocFileName").waitAndClick(3);
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(3);

	}

	/**
	 * @author Jayanthi Modified date-30/8/21
	 * @description this method will assign and distribute docs to reviewer.
	 * @return Reviewers Available list in Assignment module.
	 * @throws InterruptedException
	 * @return it gives the list of reviewers.
	 */
	public ArrayList<String> addReviewerAndDistributeDocs() throws InterruptedException {

		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(5);
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelect2ndUserToAssign());
		ArrayList<String> reviwersList = new ArrayList<String>();
		for (int i = 1; i <= getAllAssgnReviewers().size(); i++) {
			String reviewers = getAssgnReviewers(i).getText();
			reviwersList.add(reviewers);
		}
		Collections.sort(reviwersList);
		driver.scrollingToElementofAPage(getSelect2ndUserToAssign());
		getSelect2ndUserToAssign().waitAndClick(5);
		getAdduserBtn().waitAndClick(3);
		bc.VerifySuccessMessage("Action saved successfully");
		driver.waitForPageToBeReady();
		bc.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		getDistributeTab().Click();
		bc.waitForElement(getSelect2ndUserInDistributeTab());
		getSelect2ndUserInDistributeTab().Click();
		bc.waitForElement(getDistributeBtn());
		getDistributeBtn().Click();
		bc.stepInfo("Assignment distributed to the reviewer successfully");
		return reviwersList;
	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To enable the Analytics panel Toggle present in AssignPage
	 */
	public void toggleEnableAnalyticsPanel() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		String analyticalPanelFlag = getAssgn_AnalyticsPanelToggle().GetAttribute("class");

		if (!analyticalPanelFlag.contains("true")) {
			getAssgn_AnalyticsPanelToggle().Click();
		}
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To enable the Analytics panel and disable thread map Toggle
	 *              present in AssignPage
	 */
	public void toggleDisableThreadMap() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		// bc.waitForElement(getAssgnGrp_Create_DisplayAnalyticstoggle());
		// getAssgnGrp_Create_DisplayAnalyticstoggle().waitAndClick(5);
		bc.waitForElement(getAssgnGrp_Create_DisplayAnalyticstoggleDisable());
		getAssgnGrp_Create_DisplayAnalyticstoggleDisable().waitAndClick(5);
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.CloseSuccessMsgpopup();

	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To enable the Analytics panel and enabled thread map Toggle
	 *              present in AssignPage 'RPMXCON-51845
	 * @param assignmentName
	 */
	public void selectAssignmentToViewinDocviewThreadMap(final String assignmentName) {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		Boolean assignmentVisibility = false;
		driver.scrollingToBottomofAPage();
		/*
		 * try { assignmentVisibility =
		 * getSelectAssignment(assignmentName).isDisplayed(); } catch (Exception e) {
		 * System.err.println("Error" + e); } if (assignmentVisibility) {
		 * driver.WaitUntil((new Callable<Boolean>() { public Boolean call() { return
		 * getSelectAssignment(assignmentName).Visible(); } }), Input.wait60);
		 * 
		 * } else { String nextbutton =
		 * getAssgn_Pagination().GetAttribute("class").trim(); do {
		 * getAssgn_Pagination().waitAndClick(10); driver.waitForPageToBeReady(); try {
		 * assignmentVisibility = getSelectAssignment(assignmentName).isDisplayed(); }
		 * catch (Exception e) { System.err.println("Error" + e); } nextbutton =
		 * getAssgn_Pagination().GetAttribute("class").trim(); if
		 * (nextbutton.equals("paginate_button next disabled")) { break; } } while
		 * (!assignmentVisibility); }
		 */

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
//				getSelectAssignment(assignmentName).waitAndClick(3);
				;
				driver.scrollPageToTop();
				getAssignmentActionDropdown().waitAndClick(3);
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_ViewinDocView());
		getAssignmentAction_ViewinDocView().waitAndClick(5);

	}

	/**
	 * Author : Raghuram A date: 8/25/21 NA Modified date:N/A Modified by:N/A A
	 * Description : Sort by Metadata Sequence *
	 * 
	 * @throws InterruptedException
	 * @param sortBy
	 * @param sortType
	 */
	public void Assgnwithdocumentsequence(String sortBy, String sortType) throws InterruptedException {
		bc.waitForElement(getAssgn_DocSequence_SortbyMetadata());
		getAssgn_DocSequence_SortbyMetadata().waitAndClick(3);
		getAssgn_DocSequence_Selectmetadata().selectFromDropdown().selectByVisibleText(sortBy);
		System.out.println(sortType);
		getSortByMetaDataType().waitAndClick(3);
		getSortByMetaDataType().selectFromDropdown().selectByVisibleText(sortType);
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		// bc.CloseSuccessMsgpopup();

	}

	/**
	 * @Author : Mohan date: 28/8/2021 Modified date:N/A Modified by:N/A
	 * @Description : After creating assignment distributing to Two reviewer account
	 *              *
	 */

	public void add2ReviewerAndDistribute() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssig().waitAndClick(5);
		getSelectUserToAssigReviewerManager().ScrollTo();
		getSelectUserToAssigReviewerManager().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
		getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
		getSelect2ndUserInDistributeTab().waitAndClick(5);
		getDistributeBtn().waitAndClick(5);

	}

	/**
	 * @Author : Mohan date: 01/9/2021 Modified date:N/A Modified by:N/A
	 * @Description : Create assignment for neardupe docs
	 * @param assignmentName
	 * @param codingForm
	 * @param purehits
	 */
	public void assignNearDupeDocstoNewAssgn(final String assignmentName, String codingForm, int purehits) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssign_NewAssignment().Visible();
			}
		}), Input.wait60);
		getBulkAssign_NewAssignment().waitAndClick(20);
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());

		getPersistCB_NewAssgn().waitAndClick(15);

		getContinueBulkAssign().waitAndClick(15);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNearDupeDocumentsIncludeToggleButton().Visible()
						&& getNearDupeDocumentsIncludeToggleButton().isDisplayed();
			}
		}), Input.wait60);

		getNearDupeDocumentsIncludeToggleButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTotalSelectedDocuments().Visible();
			}
		}), Input.wait60);

		purehits = Integer.parseInt(getTotalSelectedDocuments().getText());

		getFinalizeButton().waitAndClick(30);

//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getAssignmentName().Visible();
//			}
//		}), Input.wait60);
		bc.waitForElement(getAssignmentName());
		getAssignmentName().SendKeys(assignmentName);
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");

		SelectCodingform(codingForm);

		String enabled = getAssgnGrp_Create_DrawPooltoggle().Class();
		if (!enabled.equals("true"))
			getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_AnalyticsPanelToggle().Visible();
			}
		}), Input.wait60);

		String analyticalPanelFlag = getAssgn_AnalyticsPanelToggle().GetAttribute("class");

		if (!analyticalPanelFlag.contains("true")) {
			getAssgn_AnalyticsPanelToggle().Click();
		}

		driver.scrollPageToTop();

		getAssignmentSaveButton().waitAndClick(30);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getNumberOfAssignmentsToBeShown().Visible();
//			}
//		}), Input.wait60);

		bc.waitForElement(getNumberOfAssignmentsToBeShown());

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				getSelectAssignment(assignmentName).waitAndClick(3);
				;
				driver.scrollPageToTop();
				getAssignmentActionDropdown().waitAndClick(3);
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(10);
//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getAssignment_ManageReviewersTab().Visible();
//			}
//		}), Input.wait30);
		bc.waitForElement(getAssignment_ManageReviewersTab());
		bc.waitTillElemetToBeClickable(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddReviewersBtn().Visible();
			}
		}), Input.wait30);
		getAddReviewersBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserToAssig().Visible();
			}
		}), Input.wait30);
		driver.waitForPageToBeReady();
		// getSelectUserToAssig().WaitUntilPresent().ScrollTo();
		getSelectUserToAssig().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserToAssigReviewerManager().Visible();
			}
		}), Input.wait30);
		// getSelectUserToAssig().WaitUntilPresent().ScrollTo();
		getSelectUserToAssigReviewerManager().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdduserBtn().Visible();
			}
		}), Input.wait30);
		getAdduserBtn().waitAndClick(3);
		bc.VerifySuccessMessage("Action saved successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDistributeTab().Visible();
			}
		}), Input.wait30);
		getDistributeTab().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUserInDistributeTabs().Visible();
			}
		}), Input.wait30);
		getSelectUserInDistributeTabs().Click();
		getSelectUserInDistributeTabsReviewerManager().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDistributeBtn().Visible();
			}
		}), Input.wait30);
		bc.CloseSuccessMsgpopup();
		getDistributeBtn().waitAndClick(7);
		bc.VerifySuccessMessage("Action saved successfully");

	}

	/**
	 * Modified date-1/10/21
	 * 
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 * @param metaData
	 * @description this method verify metadata sorting in reviewer page.
	 * @throws InterruptedException
	 */
	public void verifyTheMetaDataSortingAsPerRMUUser(String assignmentName, String metaData)
			throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(assignmentName).waitAndClick(2);
		driver.getPageSource();
		driver.waitForPageToBeReady();
		bc.waitForElement(getGearIcon());
		// getGearIcon().ScrollTo();
		try {
			getGearIcon().waitAndClick(5);
		} catch (Exception e) {
			getGearIcon().Click();
		}
		bc.waitForElementCollection(metaDatasInSelectedFields_reviewerPg());
		driver.waitForPageToBeReady();
		int metadatasCount = metaDatasInSelectedFields_reviewerPg().size();
		for (int i = 0; i < metadatasCount; i++) {
			metaDatasRemoveBtn_reviewerPg().Click();
			System.out.println(i);
		}
		if (metaDatasRemoveBtn_reviewerPg().isElementAvailable(5) == false) {
			bc.stepInfo("All the existing metadatas are deselected");
			bc.dragAndDropSpecialElement(getMetaData_reviewerPg(metaData), selectedMetaDataFields_reviewerPg());
			bc.stepInfo(metaData + " is selected for sorting");
			saveBtn_reviewerPg().Click();
		} else {
			assertion.fail();
			bc.failedStep("MetaData is not inserted into mini doc list page");
		}
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return metaDataslist_reviewerPg().Visible();
				}
			}), Input.wait30);
			List<String> originalOrderedList = bc.getAvailableListofElements(metaDataslist_reviewerPg());
			List<String> afterSortList = bc.getAvailableListofElements(metaDataslist_reviewerPg());
			// Collections.sort(afterSortList);
			Collections.sort(afterSortList, Collections.reverseOrder());
			assertion.assertEquals(afterSortList, originalOrderedList);
			System.out.println(originalOrderedList);
			System.out.println(afterSortList);
			bc.passedStep("MetaData-" + metaData + "is displayed in mini doclist page as expected in Ascending order ");
		} catch (Exception e) {
			bc.passedStep(
					"MetaData-" + metaData + "is not  displayed in mini doclist page as expected in Ascending order ");
		}
		assertion.assertAll();
	}

	/**
	 * Modified date-1/10/21
	 * 
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 * @param metaData
	 * @description this method verify metadata sorting in reviewer page.
	 * @throws InterruptedException
	 */
	public void verifyTheMetaDataSortingAsPerRMUUserByAscending(String assignmentName, String metaData)
			throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(assignmentName).waitAndClick(2);
		driver.getPageSource();
		driver.waitForPageToBeReady();
		bc.waitForElement(getGearIcon());
		// getGearIcon().ScrollTo();
		try {
			getGearIcon().waitAndClick(5);
		} catch (Exception e) {
			getGearIcon().Click();
		}
		bc.waitForElementCollection(metaDatasInSelectedFields_reviewerPg());
		driver.waitForPageToBeReady();
		int metadatasCount = metaDatasInSelectedFields_reviewerPg().size();
		for (int i = 0; i < metadatasCount; i++) {
			metaDatasRemoveBtn_reviewerPg().Click();
			System.out.println(i);
		}
		if (metaDatasRemoveBtn_reviewerPg().isElementAvailable(5) == false) {
			bc.stepInfo("All the existing metadatas are deselected");
			bc.dragAndDropSpecialElement(getMetaData_reviewerPg(metaData), selectedMetaDataFields_reviewerPg());
			bc.stepInfo(metaData + " is selected for sorting");
			saveBtn_reviewerPg().Click();
		} else {
			assertion.fail();
			bc.failedStep("MetaData is not inserted into mini doc list page");
		}
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return metaDataslist_reviewerPg().Visible();
				}
			}), Input.wait30);
			List<String> originalOrderedList = bc.getAvailableListofElements(metaDataslist_reviewerPg());
			List<String> afterSortList = bc.getAvailableListofElements(metaDataslist_reviewerPg());
			Collections.sort(afterSortList);
			assertion.assertEquals(afterSortList, originalOrderedList);
			System.out.println(originalOrderedList);
			System.out.println(afterSortList);
			bc.passedStep("MetaData-" + metaData + "is displayed in mini doclist page as expected in Ascending order ");
		} catch (Exception e) {
			bc.passedStep(
					"MetaData-" + metaData + "is not  displayed in mini doclist page as expected in Ascending order ");
		}
		assertion.assertAll();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param metadata
	 * @param EditAssignment_status("yes" for EditAssignment else "NO" for New
	 *                                    Assignment)
	 * @description this method will verify the configure button Display and selects
	 *              select sorts by metadata option
	 * @throws InterruptedException
	 */
	public void verifyConfigureBtnAndSelectSortBymetaData(String metadata, String EditAssignment_status)
			throws InterruptedException {
		driver.scrollingToBottomofAPage();
		bc.waitForElement(configureButton());
		if (configureButton().isDisplayed() == true) {
			bc.passedStep("Configure button is exists");
		} else {
			bc.failedStep("Configure button is not exists");
		}
		driver.getPageSource();
		driver.scrollPageToTop();
		sortBymetaDataBtn().waitAndClick(5);
		bc.waitForElement(SelectMetadata());
		SelectMetadata().waitAndClick(5);
		bc.waitForElement(SelectMetadataFromDropDown(metadata));
		SelectMetadataFromDropDown(metadata).waitAndClick(3);
		bc.stepInfo(metadata + " is selected from the drop down");
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		if (EditAssignment_status.equalsIgnoreCase("yes")) {
			bc.VerifySuccessMessage("Assignment updated successfully");
			bc.CloseSuccessMsgpopup();
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 * @description this method will verify DocCountdisplay in Assignments grid view
	 *              display in manage assignments page.
	 * @throws InterruptedException
	 * @return this method returns the document count of assignment
	 */

	public String verifydocsCountInAssgnPage(String assignmentName) throws InterruptedException {
		String docCount = null;
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				getSelectAssignment(assignmentName).waitAndClick(5);
				docCount = getDocCountInAssgnPg(assignmentName).getText();
				bc.stepInfo("Docs count displayed for " + assignmentName + " assignment is " + docCount);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().Click();
			}
		}
		return docCount;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 * @param codingForm
	 * @description this method will create assignment without clicking on save
	 *              button.
	 * @throws InterruptedException
	 */

	public void createAssignment_withoutSave(String assignmentName, String codingForm) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentAction_NewAssignment());
		bc.waitTillElemetToBeClickable(getAssignmentAction_NewAssignment());
		getAssignmentAction_NewAssignment().waitAndClick(20);
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentName());
		getAssignmentName().SendKeys(assignmentName);
		driver.waitForPageToBeReady();
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will minimise the browser resolution from 100% to
	 *              80%
	 * @throws AWTException
	 */
	public void BrowserResolutionMin() throws AWTException {
		Robot robot = new Robot();
		for (int i = 0; i <= 1; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will maximise the browser resolution from 80% to
	 *              100%.
	 * @throws AWTException
	 */

	public void BrowserResolutionMax() throws AWTException {
		Robot robot = new Robot();
		for (int k = 0; k <= 1; k++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	/**
	 * @author Jayanthi.ganesan Modified on-1011/21
	 * @description this method will verify the formatting tool bar display in
	 *              instruction popup when we click on configure button in create
	 *              assignment/edit assignment page.
	 * @param optionTocheckElements
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyFormattingToolBar_InstructionPopUp(String optionTocheckElements)
			throws InterruptedException, AWTException {
		SoftAssert softAssertion = new SoftAssert();
		bc.waitForElement(configureButton());
		bc.ValidateElement_Presence(configureButton(), "Configure Button");
		driver.scrollingToElementofAPage(configureButton());
		bc.waitForElement(configureButton());
		configureButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		BrowserResolutionMin();
		driver.waitForPageToBeReady();
		Thread.sleep(10000);
		Thread.sleep(10000);
		driver.waitForPageToBeReady();
		driver.getPageSource();
		bc.waitForElement(getInstuctionPopup_FormattingToolBar());
		getInstuctionPopup_FormattingToolBar().ScrollTo();
		if (getInstuctionPopup_FormattingToolBar().isDisplayed() == true) {
			bc.passedStep("Formatting ToolBar exists in isntruction popup when we click on configure button");
			softAssertion.assertTrue(true);
			if (optionTocheckElements == "yes") {
				bc.ValidateElement_Presence(getInstruction_HtmlBtn(), "HTML");
				bc.ValidateElement_Presence(getInstruction_FormattingBtn(), "Formatting Btn");
				bc.ValidateElement_Presence(getInstruction_BoldBtn(), "bold Btn");
				bc.ValidateElement_Presence(getInstruction_ItalicBtn(), "Italics Btn");
				bc.ValidateElement_Presence(getInstruction_DeleteBtn(), "Delete");
				bc.ValidateElement_Presence(getInstruction_UnorderedListBtn(), "UnorderedSort Btn");
				bc.ValidateElement_Presence(getInstruction_OrderedListBtn(), "UnorderedSort Btn");
				bc.ValidateElement_Presence(getInstruction_OutdentBtn(), "Outdent Btn");
				bc.ValidateElement_Presence(getInstruction_IndentBtn(), "Indent Btn");
				bc.ValidateElement_Presence(getInstruction_LinkBtn(), "Link Btn");
				bc.ValidateElement_Presence(getInstruction_AlignmentBtn(), "Alignment Btn");
				bc.ValidateElement_Presence(getInstruction_HorizontalRuleBtn(), "Horizonatal Btn");
				bc.ValidateElement_Presence(getInstructionCancelBtn(), "CancelButton");
				bc.ValidateElement_Presence(instructionOkButton(), "Okay Button");
			}
		} else {
			bc.failedStep("Formatting ToolBar is not displayed");
			softAssertion.assertFalse(false);
		}
		BrowserResolutionMax();
		instructionOkButton().waitAndClick(5);
		driver.scrollPageToTop();
		assertion.assertAll();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param AssignmentName
	 * @description- this method will verify the System Define metadata sorting in
	 *               DocList.
	 */
	public void verifySystemDefineMetaDataSorting_DocList(String AssignmentName) {

		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(AssignmentName).waitAndClick(2);
		driver.getPageSource();
		driver.waitForPageToBeReady();
		List<String> originalOrderedList = bc.getAvailableListofElements(getFamilyMemeberCnt_InDocList());
		List<String> afterSortList = bc.getAvailableListofElements(getFamilyMemeberCnt_InDocList());
		List<Integer> originalOrderedListInt = originalOrderedList.stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		List<Integer> afterSortListInt = afterSortList.stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		Collections.sort(afterSortListInt);
		assertion.assertEquals(afterSortListInt, originalOrderedListInt);
		assertion.assertAll();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will verify display of doc count in assignments
	 *              group name when the DocCount Toggle button is OFF in Manage
	 *              Assignments page.
	 * @throws InterruptedException
	 */
	public void verifyDocCountDisplay() throws InterruptedException {
		String AssgnGroupName = "AssgnGrp" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		createCascadeNonCascadeAssgnGroup(AssgnGroupName, "YES");
		bc.stepInfo("Created a Assignment group");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		if (DocCountToggle_OFF().isDisplayed()) {
			driver.waitForPageToBeReady();
			bc.waitForElement(getAssgnGrp_Select(AssgnGroupName));
			assertion.assertEquals(AssgnGroupName, getAssgnGrp_Select(AssgnGroupName).getText());
			bc.passedStep("Doc Count Toggle Button Present and Document count display was validated as Expected.");
		} else {
			bc.failedStep("Doc CountToggle button not present and Doc count display is not validated");
		}
		DeleteAssgnGroup(AssgnGroupName);
	}

	/**
	 * @throws InterruptedException
	 * @Author : Jayanthi date: 28/8/2021 Modified date:28/10/21 Modified
	 *         by:Jayanthi
	 * @Description :This method will assign two reviewers but distribute document
	 *              to one reviewer.
	 * @return It returns distributed reviewer as string
	 */

	public String addMultipleReviewersAndDistributeToOnereviewer() throws InterruptedException {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssig().waitAndClick(5);
		driver.scrollingToElementofAPage(getSelectUserToAssigReviewerManager());
		getSelectUserToAssigReviewerManager().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
		bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
		bc.getCloseSucessmsg().waitAndClick(10);
		bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
		bc.stepInfo("Assigned two reviewers.");
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
		getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
		String distributedUSer = getDistributedUserNameInDisTab().getText();
		// bc.CloseSuccessMsgpopup();
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo("distributed documents to one reviewer-" + distributedUSer);
		bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
		bc.getCloseSucessmsg().waitAndClick(10);
		bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
		return distributedUSer;
	}

	/**
	 * @throws InterruptedException
	 * @Author : Jayanthi date: 28/8/2021 Modified date:16/9/21 Modified by:Jayanthi
	 * @param Reviewer username
	 * @Description :This method will return available Pending Assigned user to
	 *              redistribute documents.
	 * @return it returns the redistributed user
	 */
	public String VerifyUserNotInListAfterRedistributedDocs(String Username) throws InterruptedException {
		System.out.println(Username);
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_selectReviewer(Username));
		getAssgn_ManageRev_selectReviewer(Username).ScrollTo();
		getAssgn_ManageRev_selectReviewer(Username).waitAndClick(10);
		getAssgn_ManageRev_Action().waitAndClick(20);
		bc.waitForElement(getAssgn_RedistributeDoc());
		getAssgn_RedistributeDoc().waitAndClick(20);
		bc.waitForElement(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(20);
		bc.waitForElement(getUsersListInRedistrubutePopup());
		String reviewersList = getUsersListInRedistrubutePopup().getText();
		getAssgn_ManageRev_cancelBtn().waitAndClick(10);
		bc.stepInfo(
				"Pending Reviewer associated with assignment available to Redistribute the document" + reviewersList);
		return reviewersList;
	}

	/**
	 * @throws InterruptedException
	 * @Author : Jayanthi date: 28/8/2021 Modified date:16/9/21 Modified by:Jayanthi
	 * @param Reviewer username
	 * @Description :This method will return available Pending Assigned user to
	 *              redistribute documents.
	 * @return It returns the reviwer in redistributed popup
	 * 
	 */
	public String VerifyUserNotInListAfterRedistributedDocs() throws InterruptedException {
		// System.out.println(Username);
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_selectReviewer(Input.rmu1userName));
		getAssgn_ManageRev_selectReviewer(Input.rmu1userName).ScrollTo();
		getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
		getAssgn_ManageRev_Action().waitAndClick(20);
		bc.waitTime(2);
		bc.waitForElement(getAssgn_RedistributeDoc());
		getAssgn_RedistributeDoc().waitAndClick(20);
		bc.waitForElement(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(20);
		bc.waitForElement(getUsersListInRedistrubutePopup());
		String reviewersList = getUsersListInRedistrubutePopup().getText();
		getAssgn_ManageRev_cancelBtn().waitAndClick(10);
		bc.stepInfo(
				"Pending Reviewer associated with assignment available to Redistribute the document" + reviewersList);
		return reviewersList;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @Description-This method will verify whether RMU can view View All Docs in
	 *                   DocList option in manage assignment page
	 */
	public void verifViewAllDocsinDocListBtnINActionDD() {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		bc.waitForElement(getRootBTN());
		getRootBTN().Click();
		bc.stepInfo("Clicked Root assignment group ");
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.stepInfo("clicked Action dropdown");
		if (getAssignmentAction_ViewinDocList().isDisplayed()) {
			bc.passedStep("View All Docs in DocList option is displayed");
			bc.passedStep("Sucessfully verified that RMU is able to view option \"View All Docs in DocList\"");
		} else {
			bc.failedStep("View All Docs in DocList is not displayed in manage assignment page under action dropdown");
		}

	}

	/**
	 * @throws InterruptedException
	 * @Author : Jayanthi date: 17/09/2021 Modified date:N/A Modified by:N/A
	 * @param Assignment name
	 * @param coding     form
	 * @param Assignment group
	 * @Description :This method created a assignment from assign/unassgn documents
	 *              popup
	 * 
	 */

	public void createNewAssignmentFromAssgnUnassgnPopUp(String assignmentName, String codingForm, String AssignmentGrp)
			throws InterruptedException {

		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentName());
		getAssignmentName().SendKeys(assignmentName);
		driver.waitForPageToBeReady();
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);
		bc.waitForElement(getParentAssignmentGroupName());
		String value = getParentAssignmentGroupName().GetAttribute("value");
		System.out.println(value);
		System.out.println(AssignmentGrp);
		driver.scrollPageToTop();
		try {
			assertion.assertEquals(value, AssignmentGrp);
			bc.passedStep("The selected assignment group is successfully cascaded to new assignment page");
		} catch (Exception e) {
			bc.failedStep("The selected assignment group is not cascaded to new assignment page");
		}
		getAssignmentSaveButton().waitAndClick(5);
		bc.passedStep("Assignment created successfully from the assgn/unassgn popup");
	}

	/**
	 * @throws InterruptedException
	 * @Author : Jayanthi date: 17/09/2021 Modified date:11/10/21 Modified
	 *         by:Jayanthi
	 * @param meta data
	 * @Description :This method drag and drop the options under document
	 *              presentation sequence from live sequence to available criteria
	 */

	public void dragAndDropLiveSequence(String metaData) throws InterruptedException {
		System.out.println(metaData);
		driver.waitForPageToBeReady();
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 30);
		wait.until(ExpectedConditions.elementToBeClickable(liveSequenceSorts(metaData).getWebElement()));
		bc.waitForElement((liveSequenceSorts(metaData)));
		actions.clickAndHold((liveSequenceSorts(metaData)).getWebElement());
		System.out.println("Click and hold performed");
		actions.moveToElement((dropElement()).getWebElement());
		actions.moveToElement((dragElement(metaData)).getWebElement(), 0, -45);
		actions.release(dropElement().getWebElement());
		actions.release();
		actions.build().perform();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 * @description This method verify DocId's are sorting in reviewer page.
	 * @throws InterruptedException
	 */
	public void verifyTheDocIdListInReviwerPgDocList(String assignmentName) throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(assignmentName).waitAndClick(10);
		driver.getPageSource();
		driver.waitForPageToBeReady();
		bc.waitForElement(viewAllDocListBtn_reviewerPg());
		viewAllDocListBtn_reviewerPg().ScrollTo();
		viewAllDocListBtn_reviewerPg().waitAndClick(10);
		bc.waitForElementCollection(commonMetaDataslist_DocListreviewerPg());
		getSelectNumberOfDocList().selectFromDropdown().selectByVisibleText("500");
		bc.waitForElementCollection(commonMetaDataslist_DocListreviewerPg());
		ArrayList<String> metaDatasListbeforesorting = new ArrayList<String>();
		for (int i = 1; i <= getAllDocIds_DocListreviewerPg().size(); i++) {
			String list = getDocId_DocListreviewerPg(i).getText();
			metaDatasListbeforesorting.add(list);
		}
		ArrayList<String> afterSorting = metaDatasListbeforesorting;
		Collections.sort(afterSorting);
		try {
			assertion.assertEquals(metaDatasListbeforesorting, afterSorting);
			bc.passedStep("The lists are sorted in ascending order");
		} catch (Exception e) {
			bc.failedStep("The lists are not sorted in ascending order");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param sortBy   - metadatas
	 * @param sortType - Ascending/Descending
	 * @description This method validate the metadata and their sort type as
	 *              expected.
	 * @throws InterruptedException
	 */
	public void validatedTheSortByAndSortType(String sortBy, String sortType) throws InterruptedException {
		driver.scrollingToBottomofAPage();
		if (getSortBymetadata(sortBy).isDisplayed() == true) {
			bc.passedStep(
					"The metadata selected from the assignment group successfully cascaded to their respective assignment");
		} else {
			bc.failedStep(
					"The metadata selected from the assignment group is not cascaded to their respective assignment");
			assertion.fail();
		}
		if (getSortBymetadataType(sortType).isDisplayed() == true) {
			bc.passedStep(
					"The metadata type selected from the assignment group successfully cascaded to their respective assignment");
		} else {
			bc.failedStep(
					"The metadata type selected from the assignment group is not cascaded to their respective assignment");
			assertion.fail();
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param option - metadata
	 * @description This method validate the category options placed in as expected.
	 * @throws InterruptedException
	 */

	public void validateTheAvailableCriteriaInCategory(String option) throws InterruptedException {
		driver.scrollingToBottomofAPage();
		if (getAvailableCriteriaCategory(option).isDisplayed() == true) {
			bc.passedStep(
					"The changes of category option in assignment group successfully cascaded to their respective assignment");
		} else {
			bc.failedStep(
					"The changes of category option in assignment group is not cascaded to their respective assignment");
			assertion.fail();
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @Description-This method will verify display of sample methods drop down
	 *                   options in in assign/unassign pop up in advanced search
	 *                   page
	 */
	public void VerifySampleMethodDDOptions() {
		String[] optionsArray = new String[] { "Count of Selected Docs", "Percent of Selected Docs",
				"Parent Level Docs Only", "Inclusive Email" };
		List<String> ExpectedOptionsList = Arrays.asList(optionsArray);
		List<String> OptionsList = bc.getAvailableListofElements(getSampleMethodsAllOptions());
		try {
			assertion.assertEquals(ExpectedOptionsList, OptionsList);
			bc.passedStep("Sample methods Dropdown available options verified");
		} catch (Exception e) {
			bc.failedStep("Sample Methods Dropdown available options are not expected");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @Description-This method will calculate percentage of docs assigned
	 * @return It returns the percentage value
	 */
	public long calculatePercentageOfDocAlloted() {
		int TotalCount = Integer.parseInt(getStartingCount().getText());
		int percentageOfDocsTobeAssigned = 10;
		long percentofdocsAssigned = (long) Math.round(TotalCount * (percentageOfDocsTobeAssigned / 100.0));
		return percentofdocsAssigned;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @Description-This method will assign documents to assignments using sample
	 *                   methods(for one assignment and two assignment) and verify
	 *                   the Doc counts assigned in manage assignment page.
	 * @param assignmentName
	 * @param assignmentName2 (this should be null for single assignment)
	 * @param sampleMethod
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	public void assignAndVerifyDocsCountwithSamplemethod(final String assignmentName, String samplemethod,
			final String assignmentName2) throws NumberFormatException, InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getStartingCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		VerifySampleMethodDDOptions();
		if (samplemethod.equalsIgnoreCase("Percentage")) {
			getsampleMethod().selectFromDropdown().selectByVisibleText("Percent of Selected Docs");
			getCountToAssign().SendKeys("10");
			long PercentageOfDocAssigned = calculatePercentageOfDocAlloted();
			System.out.println(PercentageOfDocAssigned);
			getPersistCB_ExistAssgn().waitAndClick(5);

		} else if (samplemethod.equalsIgnoreCase("Count of Selected Docs")) {

			getsampleMethod().selectFromDropdown().selectByVisibleText("Count of Selected Docs");
			try {
				int startingcountToAssign = Integer.parseInt(getStartingCount().getText());
				int GetCountToAssign = Integer.parseInt(getCountToAssign().GetAttribute("value"));
				String CountToAssign_greater = String.valueOf(startingcountToAssign + 1);
				assertion.assertEquals(startingcountToAssign, GetCountToAssign);
				getCountToAssign().Clear();
				getCountToAssign().SendKeys(CountToAssign_greater);
				bc.waitForElement(getErrorMsg_NumberToAssignTxtBox());
				String ExpectedErrorMsg_greaterNo = getErrorMsg_NumberToAssignTxtBox().getText();
				if (ExpectedErrorMsg_greaterNo
						.equalsIgnoreCase("Number to Assign should be between 1 and Total Document Selected")) {
					bc.passedStep(
							"Sucessfully verified the Error message if value is greater than  starting count of docs to assign");
				} else {
					bc.passedStep(
							" The Error message is not displayed as expected  if value is greataer than starting count of docs to assign");
				}
				getCountToAssign().Clear();
				getCountToAssign().SendKeys("-1");
				if (getErrorMsg_NumberToAssignTxtBox().getText()
						.equalsIgnoreCase("Number to Assign should be between 1 and Total Document Selected")) {
					bc.passedStep("Sucessfully verified the Error message if value is non integer");
				} else {
					bc.passedStep(" The Error message is not displayed as expected  if value is non integer");
				}
				getCountToAssign().Clear();
				getCountToAssign().SendKeys(String.valueOf(startingcountToAssign));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		bc.waitForElement(getSelectAssignmentToBulkAssign(assignmentName));
		getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);
		if (assignmentName2 != null) {
			bc.waitForElement(getSelectAssignmentToBulkAssign(assignmentName2));
			getSelectAssignmentToBulkAssign(assignmentName2).waitAndClick(20);
		}

		getContinueBulkAssign().waitAndClick(15);
		driver.waitForPageToBeReady();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		String FinalCount = getFinalCount().getText();

		getFinalizeButton().Click();
		bc.VerifySuccessMessage(
				"Bulk Assign has been added to background process. You will get notification on completion.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		int ExpectedDocCount = Integer.parseInt(FinalCount);
		try {
			int ActualDocCount = Integer.parseInt(verifydocsCountInAssgnPage(assignmentName));
			assertion.assertEquals(ActualDocCount, ExpectedDocCount);
			if (assignmentName2 != null) {
				int ActualDocCount2 = Integer.parseInt(verifydocsCountInAssgnPage(assignmentName2));
				assertion.assertEquals(ActualDocCount2, ExpectedDocCount);
				bc.passedStep("Sucesfuly verified doc counts assigned in Assignmnets using " + samplemethod
						+ " method for two Assignments");
			} else {
				bc.passedStep("Sucesfully verified doc counts assigned in Assignments using " + samplemethod
						+ "method for  one assignment");
			}
		} catch (Exception e) {
			bc.failedStep("Assigned Doc Count Mismatched");
		}
		assertion.assertAll();
	}

	/**
	 * @author Gopinath
	 * @param assignmentName
	 * @param codingForm
	 * @description This method for creating new assignment for bulk assign
	 *              operation
	 * @throws InterruptedException
	 */

	public void createAssignmentByBulkAssignOperation(String assignmentName, String codingForm)
			throws InterruptedException {
		try {
			bc.waitForElement(dashBoardPageTitle());
			bc.waitForElement(getAssignmentName());
			driver.waitForPageToBeReady();
			getAssignmentName().SendKeys(assignmentName);
			driver.waitForPageToBeReady();
			getParentAssignmentGroupName().isDisplayed();
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			SelectCodingform(codingForm);
			driver.scrollPageToTop();
			bc.waitForElement(getAssignmentSaveButton());
			getAssignmentSaveButton().Click();
			System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
			bc.passedStep("Assignment " + assignmentName + " created with CF " + codingForm);
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while creating new assignment for bulk assign operation" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @param flag : (flag is a boolean value that weather redactions to enable or
	 *             not).
	 * @description This method for enabling redactions toogle in assignments page.
	 * @throws InterruptedException
	 */

	public void enableToogleToEnableRedactions(boolean flag) throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getEnableRedactionsToogle().isDisplayed();
				}
			}), Input.wait90);
			String value = getEnableRedactionsToogle().GetAttribute("class").toLowerCase().trim();
			if (!(value.contains("true")) && flag == true) {
				getEnableRedactionsToogle().isDisplayed();
				getEnableRedactionsToogle().Click();
				bc.passedStep("Enable Redactions toogle is enabled successfully in Edit Assignment page");
			} else if (flag == false && value.contains("true")) {
				getEnableRedactionsToogle().isDisplayed();
				getEnableRedactionsToogle().Click();
				bc.passedStep("Enable Redactions toogle is disabled successfully in Edit Assignment page");
			} else if (flag == true && value.contains("true")) {
				bc.passedStep("Enable Redactions toogle is already enabled in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				bc.passedStep("Enable Redactions toogle is already disabled in Edit Assignment page");
			}
			driver.scrollPageToTop();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentSaveButton().isDisplayed() && getAssignmentSaveButton().Enabled();
				}
			}), Input.wait90);
			getAssignmentSaveButton().isDisplayed();
			getAssignmentSaveButton().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return bc.getSuccessMsg().isDisplayed() && bc.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			assertion.assertEquals(bc.getSuccessMsg().getWebElement().isDisplayed(), true,
					"Success message is not displayed");
			if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();
			assertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while enabling redactions toogle in assignments page" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @param assignmentName : (assignmentName is string value that name of
	 *                       assignment ).
	 * @description This method for selecting assignment to view in doc view.
	 */
	public void selectAssignmentToViewinDocview(final String assignmentName) {
		try {
			bc.selectproject();
			driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

			bc.waitForElement(getNumberOfAssignmentsToBeShown());

			getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssgnPaginationCount().Visible();
				}
			}), Input.wait30);
			int count = ((getAssgnPaginationCount().size()) - 2);
			for (int i = 0; i < count; i++) {
				// driver.waitForPageToBeReady();
				Boolean status = getSelectAssignment(assignmentName).isDisplayed();
				if (status == true) {
					getSelectAssignment(assignmentName).ScrollTo();
					Boolean status1 = getSelectAssignmentRow(assignmentName).isDisplayed();
					if (!status1) {
						getSelectAssignment(assignmentName).waitAndClick(3);
					}
					driver.scrollPageToTop();
					getAssignmentActionDropdown().waitAndClick(4);
					bc.stepInfo("Expected assignment found in the page " + i);
					break;
				} else {
					driver.scrollingToBottomofAPage();
					getAssgnPaginationNextButton().waitAndClick(3);
					bc.stepInfo("Expected assignment not found in the page " + i);
				}
			}
			bc.waitForElement(getAssignmentAction_ViewinDocView());
			getAssignmentAction_ViewinDocView().waitAndClick(3);
			bc.stepInfo("View on Doc view option is clicked");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting assignment to view in Doc view" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @param assignmentName : (assignmentName is string value that name of
	 *                       assignment ).
	 * @description This method for selecting assignment to view in doc view.
	 */
	public void selectAssignmentToViewinDocview(final String assignmentName, String ProjectName) {
		try {
			bc.selectproject(ProjectName);
			driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

			bc.waitForElement(getNumberOfAssignmentsToBeShown());

			getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssgnPaginationCount().Visible();
				}
			}), Input.wait30);
			int count = ((getAssgnPaginationCount().size()) - 2);
			for (int i = 0; i < count; i++) {
				// driver.waitForPageToBeReady();
				Boolean status = getSelectAssignment(assignmentName).isDisplayed();
				if (status == true) {
					getSelectAssignment(assignmentName).ScrollTo();
					Boolean status1 = getSelectAssignmentRow(assignmentName).isDisplayed();
					if (!status1) {
						getSelectAssignment(assignmentName).waitAndClick(3);
					}
					driver.scrollPageToTop();
					getAssignmentActionDropdown().waitAndClick(4);
					bc.stepInfo("Expected assignment found in the page " + i);
					break;
				} else {
					driver.scrollingToBottomofAPage();
					getAssgnPaginationNextButton().waitAndClick(3);
					bc.stepInfo("Expected assignment not found in the page " + i);
				}
			}
			bc.waitForElement(getAssignmentAction_ViewinDocView());
			getAssignmentAction_ViewinDocView().waitAndClick(3);
			bc.stepInfo("View on Doc view option is clicked");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting assignment to view in Doc view" + e.getMessage());

		}
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date:28/8/2021
	 * @Description: Assignment creation with save button for rmu
	 * @param assignmentName
	 * @param codingForm
	 */
	// Assignment creating and saving the assignment
	// After Saving From action Drop down selecting the assignment

	public void assignmentWithSaveButtonForRmu(String assignmentName, String codingForm) {
		bc.waitForElement(getAssgn_NewAssignmnet());
		bc.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
		getAssgn_NewAssignmnet().waitAndClick(5);
		bc.waitForElement(getbulkassgnpopup());
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());
		bc.waitForElement(getContinueBulkAssign());
		bc.waitTillElemetToBeClickable(getContinueBulkAssign());
		getContinueBulkAssign().waitAndClick(20);
		bc.waitForElement(getAssgn_TotalCount());
		driver.waitForPageToBeReady();
		bc.waitForElement(getFinalizeButton());
		bc.waitTillElemetToBeClickable(getFinalizeButton());
		getFinalizeButton().waitAndClick(10);
		bc.waitForElement(getAssignmentName());
		getAssignmentName().SendKeys(assignmentName);
		getParentAssignmentGroupName().isDisplayed();
		bc.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_SaveWithoutCompleteingToggle());
		bc.waitTillElemetToBeClickable(getAssgn_SaveWithoutCompleteingToggle());
		getAssgn_SaveWithoutCompleteingToggle().waitAndClick(5);
//		bc.waitForElement(getAssgn_AnalyticsPanelToggle());
//		bc.waitTillElemetToBeClickable(getAssgn_AnalyticsPanelToggle());
//		getAssgn_AnalyticsPanelToggle().waitAndClick(5);
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		try {
			if (getAssignmentErrorText().isDisplayed()) {
				driver.waitForPageToBeReady();
				bc.waitForElement(getAssignmentName());
				getAssignmentName().SendKeys(assignmentName);
				bc.waitForElement(getAssignmentSaveButton());
				getAssignmentSaveButton().waitAndClick(5);
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		assignmentPagination(assignmentName);
		driver.scrollPageToTop();
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date:25/11/2021
	 * @Description: select assignment to view in docview
	 */

	public void assgnViewInAllDocView() {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(10);
		bc.waitForElement(getAssignmentAction_ViewinDocView());
		getAssignmentAction_ViewinDocView().waitAndClick(5);
		bc.stepInfo("Assignment selected and viewAllDocs in docview");
	}

	/**
	 * @author Krishna date: 24/9/2021 Modified date:28/8/2021
	 * @Description: select assignment to view in docview
	 * @param assignmentName
	 * @param codingForm
	 */

	public void createNewquickBatchWithoutReviewer(final String assignmentName, String codingForm) {
		bc.waitTillElemetToBeClickable(getAssignmentName());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentName().Visible();
			}
		}), Input.wait60);
		getAssignmentName().SendKeys(assignmentName);

		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentCodingFormDropDown().Visible();
				}
			}), Input.wait30);
			getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
		} catch (Exception e) {
			getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertion.assertTrue(getQuickBatch_optimizedorder().Selected());
		getQuickBatch_chornologicorder().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQuickBatch_Doccount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		int doccount = Integer.parseInt(getQuickBatch_Doccount().getText());
		System.out.println("Doc Count is :" + doccount);

		getContinueBulkAssign().waitAndClick(30);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getFinalizeButton().waitAndClick(30);

		bc.VerifySuccessMessage(
				"Quick Batch Assign has been added to background process. You will get notification on completion.");
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		bc.BckTaskMessageverify("QuickBatch");

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
	}

	/**
	 * @author Krishna
	 * @Description: Go to doc view from assignments
	 * @param assignmentName
	 */
	public void ViewinDocviewFromAssignments(final String assignmentName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.assignmentsTableClassification().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignment(assignmentName).Visible();
			}
		}), Input.wait60);
		getSelectAssignment(assignmentName).waitAndClick(5);
		driver.scrollPageToTop();

		getAssignmentActionDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentAction_ViewinDocView().Visible();
			}
		}), Input.wait60);
		assertion.assertTrue(getAssgnAction_Export().isDisplayed());
		getAssignmentAction_ViewinDocView().waitAndClick(3);
		assertion.assertAll();

	}

	/**
	 * @Author : Mohan date: 28/8/2021 Modified date:N/A Modified by:Steffy
	 * @Description : After creating assignment distributing to Two reviewer account
	 * @param assignmentName
	 * @param codingForm
	 * @param purehits       Modified as per new build
	 */

	public void assignDocstoNewAssgnEnableAnalyticalPanel(final String assignmentName, String codingForm,
			int purehits) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssign_NewAssignment().Visible();
			}
		}), Input.wait60);
		getBulkAssign_NewAssignment().waitAndClick(20);
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());

		getPersistCB_NewAssgn().waitAndClick(15);

		bc.waitTillElemetToBeClickable(getContinueBulkAssign());
		getContinueBulkAssign().waitAndClick(25);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTotalSelectedDocuments().Visible();
			}
		}), Input.wait120);
		getTotalSelectedDocuments().waitAndClick(15);
		purehits = Integer.parseInt(getTotalSelectedDocuments().getText());

		getFinalizeButton().waitAndClick(30);

		try {
			driver.waitForPageToBeReady();
			bc.waitTillElemetToBeClickable(getAssignmentName());
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentName().Visible();
				}
			}), Input.wait60);
			getAssignmentName().SendKeys(assignmentName);
		} catch (Exception e) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentName().Visible();
				}
			}), Input.wait60);
			getAssignmentName().SendKeys(assignmentName);
		}
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);
		String enabled = getAssgnGrp_Create_DrawPooltoggle().Class();
		if (!enabled.equals("true"))
			getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_AnalyticsPanelToggle().Visible();
			}
		}), Input.wait60);

		String analyticalPanelFlag = getAssgn_AnalyticsPanelToggle().GetAttribute("class");

		if (!analyticalPanelFlag.contains("true")) {
			getAssgn_AnalyticsPanelToggle().Click();
		}

		driver.scrollPageToTop();

		getAssignmentSaveButton().waitAndClick(30);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				getSelectAssignment(assignmentName).waitAndClick(3);
				;
				driver.scrollPageToTop();
				getAssignmentActionDropdown().waitAndClick(3);
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}

		bc.waitForElement(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(17);
		add2ReviewerAndDistribute();
		assertion.assertAll();

	}

	/**
	 * @author Gopinath
	 * @Description :Method for navigate back.
	 * @param noOfTimes : (noOfTimes is integer value that number of times need to
	 *                  click on navigate back).
	 */
	public void navigateBack(int noOfTimes) {
		try {
			for (int i = 1; i <= noOfTimes; i++) {
				driver.Navigate().back();
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Failed to navigate back" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath,Modified by : Gopinath,Modified date -01/10/2021
	 * @Description :Method for verifying document count.
	 * @param noOfDocuments : (noOfTimes is integer value that number of documents
	 *                      need to perform assignment).
	 * @param test          : (test is string value that name of assignment).
	 * @param codingForm    : (codingForm is string value that name of coding form).
	 */
	public void verifyingTheDocCount(String test, int noOfDocuments, String codingForm) {
		try {

			driver.waitForPageToBeReady();
			bc.waitForElement(getAssgnName());
			getAssgnName().Click();
			getAssgnName().SendKeys(test);

			SelectCodingform(codingForm);

			bc.waitForElement(getSaveBtn());
			getSaveBtn().Click();
			driver.waitForPageToBeReady();

			driver.Navigate().refresh();
			/*
			 * driver.waitForPageToBeReady();
			 * bc.waitTillElemetToBeClickable(getAssgnPaginationNextButton());
			 * getAssgnPaginationNextButton().Click();
			 */
			assignmentNameValidation(test);
			driver.waitForPageToBeReady();

			bc.waitForElement(getNoOfDocuments(test));
			String DocCount = getNoOfDocuments(test).getText();
			for (int i = 0; i < 7; i++) {
				if (DocCount.trim().equals(String.valueOf(noOfDocuments).trim())) {
					break;
				} else {
					driver.Navigate().refresh();
					driver.waitForPageToBeReady();
					bc.waitTillElemetToBeClickable(getLastPage());
					getLastPage().Click();

					driver.waitForPageToBeReady();

					bc.waitForElement(getNoOfDocuments(test));
					DocCount = getNoOfDocuments(test).getText();
				}
			}
			if (DocCount.trim().equals(String.valueOf(noOfDocuments).trim())) {
				bc.stepInfo("presence of " + DocCount + " Assigned Document Is Verified Successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Failed to verify the documwnt count" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath,Modified by : Gopinath,Modified date -01/10/2021
	 * @Description :Method for counting documents after unassign.
	 * @param test : (test is string value that name of assignment).
	 */
	public void countOfDocAfterUnAssigning(String test) {
		try {
			this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			String expectedText = "0";
			driver.Navigate().refresh();
			/*
			 * driver.waitForPageToBeReady();
			 * bc.waitForElement(getAssgnPaginationNextButton());
			 * getAssgnPaginationNextButton().Click();
			 * System.out.println("Clicked last page");
			 * 
			 * driver.waitForPageToBeReady();
			 */
			assignmentNameValidation(test);
			String DocCount = getNoOfDocuments(test).getText();
			for (int i = 0; i < 7; i++) {
				if (expectedText.equals(DocCount.trim())) {
					break;
				} else {
					/*
					 * driver.Navigate().refresh(); driver.waitForPageToBeReady();
					 * bc.waitTillElemetToBeClickable(getLastPage()); getLastPage().Click();
					 * 
					 * driver.waitForPageToBeReady();
					 */
					bc.waitForElement(getNoOfDocuments(test));
					DocCount = getNoOfDocuments(test).getText();
				}
			}
			if (expectedText.equals(DocCount.trim())) {
				bc.stepInfo("presence of Document After UnAssigning is verified Successfully as:" + DocCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Failed to count documents after unassign" + e.getMessage());
		}
	}

	/**
	 * @Author : Mohan date: 01/10/2021 Modified date:25/10/2021 Modified by:Mohan
	 * @Description : After creating assignment with Coding stamp enable
	 *              distributing to Two reviewer account
	 * @param assignmentName
	 * @param codingForm
	 * @param purehits
	 */
	public void assignDocstoNewAssgnEnableCodingStamp(final String assignmentName, String codingForm, int purehits) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssign_NewAssignment().Visible();
			}
		}), Input.wait60);
		getBulkAssign_NewAssignment().waitAndClick(20);
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());

		getPersistCB_NewAssgn().waitAndClick(15);

		bc.waitTillElemetToBeClickable(getContinueBulkAssign());
		getContinueBulkAssign().waitAndClick(25);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNearDupeDocumentsIncludeToggleButton().Visible()
						&& getNearDupeDocumentsIncludeToggleButton().isDisplayed();
			}
		}), Input.wait60);

		getNearDupeDocumentsIncludeToggleButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTotalSelectedDocuments().Visible();
			}
		}), Input.wait120);

		purehits = Integer.parseInt(getTotalSelectedDocuments().getText());

		getFinalizeButton().waitAndClick(30);

		try {
			driver.waitForPageToBeReady();
			bc.waitTillElemetToBeClickable(getAssignmentName());
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentName().Visible();
				}
			}), Input.wait60);
			getAssignmentName().SendKeys(assignmentName);
		} catch (Exception e) {
			Actions actions = new Actions(driver.getWebDriver());
			actions.moveToElement(getAssignmentName().getWebElement());
			actions.click();
			actions.build().perform();
			getAssignmentName().Clear();
			getAssignmentName().SendKeys(assignmentName);
		}
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");

		SelectCodingform(codingForm);

		String enabled = getAssgnGrp_Create_DrawPooltoggle().Class();
		if (!enabled.equals("true"))
			getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_AnalyticsPanelToggle().Visible();
			}
		}), Input.wait60);

		String analyticalPanelFlag = getAssgn_AnalyticsPanelToggle().GetAttribute("class");

		if (!analyticalPanelFlag.contains("true")) {
			getAssgn_AnalyticsPanelToggle().waitAndClick(10);
		}

		bc.waitForElement(getAssgn_CodingStampAplliedToggle());
		getAssgn_CodingStampAplliedToggle().waitAndClick(10);

		driver.scrollPageToTop();

		getAssignmentSaveButton().waitAndClick(30);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				getSelectAssignment(assignmentName).waitAndClick(3);
				;
				driver.scrollPageToTop();
				getAssignmentActionDropdown().waitAndClick(5);
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_EditAssignment());
		bc.waitTime(2);
		getAssignmentAction_EditAssignment().isElementAvailable(10);
		getAssignmentAction_EditAssignment().waitAndClick(17);
		add2ReviewerAndDistribute();
		assertion.assertAll();

	}

	/**
	 * @author Indium-Baskar date: 10/4/2021 Modified date: NA
	 * @Description: Toggle Enabled for Analytical and Save without complete
	 */

//	     Toggle enbaled for SaveWithoutCompleteing
//		 Toggle enabled for AnalyticalToggle
	public void toggleEnabledForSaveAndAnalytical() {
		// permissions
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_SaveWithoutCompleteingToggle());
		getAssgn_SaveWithoutCompleteingToggle().waitAndClick(5);
		bc.waitForElement(getAssgn_AnalyticsPanelToggle());
		getAssgn_AnalyticsPanelToggle().waitAndClick(5);
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar date: 10/4/2021 Modified date: NA
	 * @Description: Assignment page pagination concept
	 * @param filedValue
	 */

	public void assignmentPagination(String filedValue) {
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_Pagination());
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(filedValue).isDisplayed();
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(filedValue));
				getSelectAssignment(filedValue).waitAndClick(5);
				driver.scrollPageToTop();
				bc.waitForElement(getAssignmentActionDropdown());
				getAssignmentActionDropdown().Click();
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				bc.waitForElement(getAssgnPaginationNextButton());
				getAssgnPaginationNextButton().Click();
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}

	}

	/**
	 * @author Iyappan.Kasinathan date: 10/7/2021 Modified date: NA
	 * @Description: Add three reviewers and distributed docs to them
	 */
	public void add3ReviewerAndDistribute() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssig().waitAndClick(5);
		driver.scrollingToElementofAPage(getSelectUserToAssigReviewerManager());
		getSelectUserToAssigReviewerManager().waitAndClick(5);
		driver.scrollingToElementofAPage(getSelectUserToAssignPA());
		getSelectUserToAssignPA().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
		getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
		getSelectUserInDistributeTabsPA().waitAndClick(5);
		getSelect2ndUserInDistributeTab().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo("Documents are distributed to three reviewers successfully");

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will verify whether the pop up is displaying or not
	 *              when we hover near the help icon near manage assignment in
	 *              assignments page.
	 * @throws InterruptedException
	 */
	public void verifyHelpTextPopUpWhenHovering() throws InterruptedException {
		bc.waitForElement(manageAssignmentHelpIcon());
		manageAssignmentHelpIcon().ScrollTo();
		bc.stepInfo("mouse Hovered on the help icon  near manage assignment ");
		if (manageAssignmentHelpIconPopOver().isElementAvailable(5) == false) {
			bc.passedStep("Help Text popup is not appeared while hovering on help icon");
		} else {
			bc.failedStep("Help Text popup is appeared while hovering on help icon");
		}
		assertion.assertAll();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method will verifies whether Help text PopUp is appeared or
	 *              not when we click on the help icon near manage assignment in
	 *              assignments page
	 * @throws InterruptedException
	 */
	public void verifyHelpTextPopUpWhenClicking() throws InterruptedException {
		bc.waitForElement(manageAssignmentHelpIcon());
		try {
			bc.waitTillElemetToBeClickable(manageAssignmentHelpIcon());
			manageAssignmentHelpIcon().waitAndClick(30);
		} catch (Exception e) {
			manageAssignmentHelpIcon().waitAndClick(30);
		}
		bc.stepInfo("Help icon near manage assignment  is clicked");
		bc.waitForElement(manageAssignmentHelpIconPopOver());
		if (manageAssignmentHelpIconPopOver().isDisplayed() == true) {
			bc.passedStep("Help Text popup appeared when we clicked on help icon");
		} else {
			bc.failedStep("Help Text popup is not appeared when we click on the help icon ");
			assertion.fail();
		}
		bc.waitForElement(ActionBtn());
		ActionBtn().waitAndClick(3);
		bc.stepInfo("Other than help icon is clicked");
		if (manageAssignmentHelpIconPopOver().isElementAvailable(5) == false) {
			bc.passedStep("Help Text popup is disappeared when we click elsewhere in the page");
		} else {
			bc.failedStep("Help Text popup not disappeared");
		}
		assertion.assertAll();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentName
	 * @throws InterruptedException
	 * @description this method will delete already selected assignment
	 */
	public void deleteSelectedAgnmt(final String assignmentName) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().Click();
		bc.waitForElement(getAssignmentAction_DeleteAssignment());
		getAssignmentAction_DeleteAssignment().waitAndClick(3);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment deleted successfully");

	}

	/**
	 * @Author : Steffy date: 09/10/2021 Modified date:N/A Modified by:Steffy
	 * @Description : After creating assignment distributing to Two reviewer account
	 * @param assignmentName
	 * @param codingForm
	 * @param purehits
	 */
	public void assignFamilyDocstoNewAssgnEnableAnalyticalPanel(final String assignmentName, String codingForm,
			int purehits) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssign_NewAssignment().Visible();
			}
		}), Input.wait60);
		getBulkAssign_NewAssignment().waitAndClick(20);
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());

		getPersistCB_NewAssgn().waitAndClick(15);

		bc.waitTillElemetToBeClickable(getContinueBulkAssign());
		getContinueBulkAssign().waitAndClick(25);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getFamilyDocumentsIncludeToggleButton().Visible()
//						&& getFamilyDocumentsIncludeToggleButton().isDisplayed();
//			}
//		}), Input.wait60);
		bc.waitForElement(getFamilyDocumentsIncludeToggleButton());

		getFamilyDocumentsIncludeToggleButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTotalSelectedDocuments().Visible();
			}
		}), Input.wait120);

		purehits = Integer.parseInt(getTotalSelectedDocuments().getText());

		getFinalizeButton().waitAndClick(30);

		try {
			driver.waitForPageToBeReady();
			bc.waitTillElemetToBeClickable(getAssignmentName());
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentName().Visible();
				}
			}), Input.wait60);
			getAssignmentName().SendKeys(assignmentName);
		} catch (Exception e) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentName().Visible();
				}
			}), Input.wait60);
			getAssignmentName().SendKeys(assignmentName);
		}
		getParentAssignmentGroupName().isDisplayed();
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);
		String enabled = getAssgnGrp_Create_DrawPooltoggle().Class();
		if (!enabled.equals("true"))
			getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_AnalyticsPanelToggle().Visible();
			}
		}), Input.wait60);

		String analyticalPanelFlag = getAssgn_AnalyticsPanelToggle().GetAttribute("class");

		if (!analyticalPanelFlag.contains("true")) {
			getAssgn_AnalyticsPanelToggle().Click();
		}

		driver.scrollPageToTop();

		getAssignmentSaveButton().waitAndClick(30);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				getSelectAssignment(assignmentName).waitAndClick(3);
				;
				driver.scrollPageToTop();
				getAssignmentActionDropdown().waitAndClick(3);
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(17);
		add2ReviewerAndDistribute();
		assertion.assertAll();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentName
	 * @param Status
	 * @throws InterruptedException
	 * @description this method will verify the color of completed and uncompleted
	 *              status button
	 */
	public void verifyColorBands(String assignmentName, String Status) throws InterruptedException {
		if (Status == "Completed") {
			bc.waitForElement(completeStatusBtn(assignmentName));
			String color = completeStatusBtn(assignmentName).GetCssValue("background-color");
			try {
				assertion.assertEquals("rgba(147, 188, 97, 1)", color);
				bc.passedStep("The green color is displayed when all the documents are completed");
			} catch (Exception e) {
				bc.failedStep("The green color is not displayed when all the documents are completed");
			}
		} else {
			bc.waitForElement(unCompleteStatusBtn(assignmentName));
			String color = unCompleteStatusBtn(assignmentName).GetCssValue("background-color");
			try {
				assertion.assertEquals("rgba(231, 71, 53, 1)", color);
				bc.passedStep("The red color is displayed successfully, when no documents are completed");
			} catch (Exception e) {
				bc.failedStep("The red color is not displayed successfully when no documents are completed");
			}
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description this method will validated the unassigned user not displayed in
	 *              distributed and manage reviewer tab
	 */
	public void ValidateUnassignUserInAgnmt() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().Click();
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssigReviewerManager());
		getSelectUserToAssigReviewerManager().Click();
		driver.scrollingToElementofAPage(getSelect2ndUserToAssign());
		getSelect2ndUserToAssign().Click();
		getAdduserBtn().Click();
		bc.VerifySuccessMessage("Action saved successfully");
		getDistributeTab().Click();
		bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
		Boolean firstReviewer = getSelectUserInDistributeTabsReviewerManager().isDisplayed();
		Boolean secondReviewer = getSelect2ndUserInDistributeTab().isDisplayed();
		if (firstReviewer == true && secondReviewer == true) {
			bc.passedStep("The reviewers are added in manage reviewer tab are displayed in distribute docs tab");
		} else {
			bc.failedStep("The reviewers are added in manage reviewer tab are not displayed in distribute docs tab");
			assertion.fail();
		}
		getAssignment_ManageReviewersTab().Click();
		bc.waitForElement(getAssgn_ManageRev_selectrev());
		getAssgn_ManageRev_selectrev().waitAndClick(10);
		getAssgn_ManageRev_Action().waitAndClick(10);
		bc.waitForElement(getAssgn_ManageRev_Action_Unassignuser());
		getAssgn_ManageRev_Action_Unassignuser().waitAndClick(10);
		bc.waitForElement(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(10);
		bc.waitForElement(getSelect2ndUserInDistributeTab());
		Boolean unAssignedReviewerInManageRevTab = getSelect2ndUserInDistributeTab().isElementAvailable(5);
		if (unAssignedReviewerInManageRevTab == false) {
			bc.passedStep("The reviewer unassigned in manage reviewer tab is not displayed in manage reviewer tab");
		} else {
			bc.failedStep("The reviewer unassigned in manage reviewer tab is displayed in manage reviewer docs tab");
		}
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(10);
		bc.waitForElement(getSelect2ndUserInDistributeTab());
		Boolean unAssignedReviewerInDistTab = getSelect2ndUserInDistributeTab().isElementAvailable(5);
		if (unAssignedReviewerInDistTab == false) {
			bc.passedStep("The reviewer unassigned in manage reviewer tab is not displayed in distribute docs tab");
		} else {
			bc.failedStep("The reviewer unassigned in manage reviewer tab is displayed in distribute docs tab");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 * @param LikeDocsCatogery_Tobeassigned
	 * @param LikeDocsCount
	 * @param alreadyAssignedDocsCount
	 * @description This method will assign documents from Assign Like documents
	 *              PopUp after clicking finalize Button from assign/un-assign popup
	 *              .
	 * @return assigned document counts
	 */
	public int assgn_LikeDocs(String assignmentName, Element LikeDocsCatogery_Tobeassigned, Element LikeDocsCount,
			int alreadyAssignedDocsCount) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getStartingCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		bc.waitForElement(getSelectAssignmentToBulkAssign(assignmentName));
		getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);

		getContinueBulkAssign().waitAndClick(15);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		bc.waitForElement(LikeDocsCatogery_Tobeassigned);
		LikeDocsCatogery_Tobeassigned.Click();

		int AssignedLikeDocsCount = Integer.parseInt(LikeDocsCount.getText());
		int FinalCount_AfterToggleClick = Integer.parseInt(getFinalCount().getText());
		int ExpectedDocCount = alreadyAssignedDocsCount + AssignedLikeDocsCount;
		if (FinalCount_AfterToggleClick == ExpectedDocCount) {
			bc.passedStep(
					"The total no of docs to be assigned to assignment is displayed as expected in action like documents popup "
							+ FinalCount_AfterToggleClick);
			getFinalizeButton().Click();
		} else {
			bc.failedStep("The no of docs to be assigned to a Assignment is mismatching in count");
		}
		bc.VerifySuccessMessage(
				"Bulk Assign has been added to background process. You will get notification on completion.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println(ExpectedDocCount);
		return ExpectedDocCount;

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 * @param metaData,metaData1
	 * @description this method verify FamilyID sorting in reviewer page.
	 * @throws InterruptedException
	 */
	public void verifyFamilyIdSorting(String assignmentName, String metaData, String metaData1)
			throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(assignmentName).waitAndClick(2);
		driver.getPageSource();
		driver.waitForPageToBeReady();
		bc.waitForElement(getGearIcon());
//		getGearIcon().ScrollTo();
		try {
			getGearIcon().waitAndClick(3);
		} catch (Exception e) {
			getGearIcon().Click();
		}
		bc.waitForElementCollection(metaDatasInSelectedFields_reviewerPg());
		driver.waitForPageToBeReady();
		int metadatasCount = metaDatasInSelectedFields_reviewerPg().size();
		for (int i = 0; i < metadatasCount; i++) {
			metaDatasRemoveBtn_reviewerPg().Click();
			System.out.println(i);
		}
		if (metaDatasRemoveBtn_reviewerPg().isElementAvailable(5) == false) {
			bc.stepInfo("All the existing metadatas are deselected");
			bc.dragAndDropSpecialElement(getMetaData_reviewerPg(metaData), selectedMetaDataFields_reviewerPg());
			bc.dragAndDropSpecialElement(getMetaData_reviewerPg(metaData1), selectedMetaDataFields_reviewerPg());
			bc.stepInfo(metaData + " is selected for sorting");
			saveBtn_reviewerPg().Click();
		} else {
			assertion.fail();
			bc.failedStep("MetaData is not inserted into mini doc list page");
		}
		List<String> expectedMetadata = new ArrayList<>();
		List<String> ActualMetadata = new ArrayList<>();
		List<WebElement> elementList = null;
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return metaDataslist_reviewerPg().Visible();
				}
			}), Input.wait30);

			elementList = metaDataslist_reviewerPg().FindWebElements();
			for (WebElement webElementNames : elementList) {
				String elementName = webElementNames.getText();
				if (elementName != "") {
					ActualMetadata.add(elementName);
				} else {
					bc.stepInfo("Blank space found in FamilyID List");
					break;
				}

			}
			bc.stepInfo("familyId list after elimination of Blank space" + ActualMetadata);
			System.out.println(ActualMetadata.size());
			String[] StringArray;
			StringArray = bc.ListTOStringArray(ActualMetadata);
			System.out.println(StringArray);
			List<String> OccuranceList = new ArrayList<String>();
			List<String> PartitionedList = new ArrayList<String>();
			OccuranceList.addAll(bc.StringOccurance(StringArray));
			System.out.println(OccuranceList);
			System.out.println(OccuranceList.size());
			if (OccuranceList.size() != 0) {
				if (OccuranceList.size() == 1) {
					bc.stepInfo("single standalone doc is found");
					String UniqueElement = OccuranceList.get(0);
					int Position = ActualMetadata.indexOf(UniqueElement);
					PartitionedList = ActualMetadata.subList(0, Position);
					bc.stepInfo("FamilyId list after removing elements from standalone docs found position "
							+ PartitionedList);
					System.out.println(PartitionedList.size());
				}
				if (OccuranceList.size() > 1) {
					List<Integer> UniqueIndexList = new ArrayList<Integer>();
					for (String unique : OccuranceList) {
						int Position = ActualMetadata.indexOf(unique);
						// UniqueIndexList.add(Integer.toString(Position));
						UniqueIndexList.add(Position);
					}
					bc.stepInfo("Standalone Docs Index  found at following indexes " + UniqueIndexList
							+ "No of unique Family Ids(standalone docs) found" + UniqueIndexList.size());
					Collections.sort(UniqueIndexList);
					int PartitionPosition = UniqueIndexList.get(0);
					PartitionedList = ActualMetadata.subList(0, PartitionPosition);
					bc.stepInfo("FamilyId list after removing elements from standalone docs found position "
							+ PartitionedList);
					System.out.println(PartitionedList);
					System.out.println(PartitionedList.size());

				}
			} else {
				PartitionedList.addAll(ActualMetadata);
			}
			expectedMetadata.addAll(PartitionedList);
			Collections.sort(expectedMetadata);
			assertion.assertEquals(expectedMetadata, PartitionedList);
			List<String> DocId = new ArrayList<>();
			List<WebElement> DocIdElements = null;
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return metaDataList_reviewerPg().Visible();
				}
			}), Input.wait30);

			DocIdElements = metaDataList_reviewerPg().FindWebElements();
			for (WebElement webElementNames : DocIdElements) {
				String elementName = webElementNames.getText();
				DocId.add(elementName);
			}
			System.out.println(DocId.size());
			if (PartitionedList.size() < DocId.size()) {
				int position = PartitionedList.size();
				System.out.println("DocID list partitioned at" + position);
				List<String> first = new ArrayList<String>(DocId.subList(0, position));
				List<String> ActualDocId = new ArrayList<String>(DocId.subList(position, DocId.size()));
				bc.stepInfo("DocId List taken for verification of sorting order is " + ActualDocId);
				System.out.println(ActualDocId);
				System.out.println(ActualDocId.size());
				List<String> ExpectedDocId = new ArrayList<String>();
				ExpectedDocId.addAll(ActualDocId);
				Collections.sort(ExpectedDocId);
				assertion.assertEquals(ExpectedDocId, ActualDocId);
			}

			bc.passedStep("MetaData-" + metaData + "is displayed in  docview page as expected in Ascending order ");
		} catch (Exception e) {
			bc.passedStep(
					"MetaData-" + metaData + "is not  displayed in  docview page as expected in Ascending order ");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description to verify the keyword popup.
	 */
	public void verifyKeywordPopUp() {
		// verify keywords PopUp
		driver.scrollingToElementofAPage(getAssgn_Keywordsbutton());
		bc.waitForElement(getAssgn_Keywordsbutton());
		getAssgn_Keywordsbutton().waitAndClick(10);

	}

	/**
	 * @author Raghuram A date: 10/12/21 NA Modified date:N/A Modified by:N/A A
	 * @description : to assign documents for the new assignment
	 * @param assignmentName
	 * @return count of docs
	 */
	public String assignDocstoNewAssgn(String assignmentName) {

		bc.waitForElement(getBulkAssign_NewAssignment());
		getBulkAssign_NewAssignment().waitAndClick(20);
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());
		getPersistCB_NewAssgn().waitAndClick(15);
		for (int i = 0; i < 30; i++) {
			try {
				bc.waitTillElemetToBeClickable(search.getContinueButton());
				search.getContinueButton().waitAndClick(15);
				break;
			} catch (Exception e) {
				bc.waitForElement(search.getContinueButton());
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		bc.waitForElement(getFinalizeButton());
		bc.waitTillElemetToBeClickable(getFinalizeButton());
		String finalCount = getFinalCount().getText();
		System.out.println(finalCount);
		getFinalizeButton().Click();
		System.out.println("Bulk Assignment is done, : " + assignmentName);
		UtilityLog.info("Bulk Assignment is done, : " + assignmentName);
		assertion.assertAll();

		return finalCount;

	}

	/**
	 * @author Raghuram A date: 10/12/21 NA Modified date:N/A Modified by:N/A A
	 * @description To create assignment in the context of new assignment tab
	 * @param assignmentName
	 * @param codingForm
	 */
	public void quickAssignCreation(String assignmentName, String codingForm) {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getAssignmentName());
			getAssignmentName().SendKeys(assignmentName);
			getParentAssignmentGroupName().isDisplayed();
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			SelectCodingform(codingForm);

			getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			getAssgnGrp_Create_DisplayAnalyticstoggle().Click();
			driver.scrollPageToTop();
			getAssignmentSaveButton().waitAndClick(30);
			System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
			System.out.println("Docs assigned to  " + assignmentName);
			bc.stepInfo("Assignment " + assignmentName + " created with CF " + codingForm);
			bc.stepInfo("Docs assigned to  " + assignmentName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram A date: 10/12/21 NA Modified date:19/1/2022 Modified
	 *         by:Raghuram A
	 * @description To select an assignment from manage assignment and navigate to
	 *              docview
	 * @param assignmentName
	 * @return document counts of an assignment
	 */
	public String selectAssignmentToViewinDocView(String assignmentName) {
		String compareCount = null;
		try {
			bc.waitForElement(getNumberOfAssignmentsToBeShown());
			getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
			driver.scrollingToBottomofAPage();
			bc.waitForElement(getAssgn_Pagination());
			int count = ((getAssgnPaginationCount().size()) - 2);
			for (int i = 0; i < count; i++) {
				driver.waitForPageToBeReady();
				Boolean status = getSelectAssignment(assignmentName).isDisplayed();
				// Assert.assertEquals(finalCount, compareCount);
				if (status == true) {
					driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
					if (!getSelectAssignmentHighlightCheck(assignmentName).isElementAvailable(5)) {
						getSelectAssignment(assignmentName).waitAndClick(5);
					}
					compareCount = getSelectAssignmentDocCount(assignmentName).getText();
					System.out.println(compareCount);
					driver.scrollPageToTop();
					bc.waitForElement(getAssignmentActionDropdown());
					getAssignmentActionDropdown().Click();
					bc.stepInfo("Expected assignment found in the page " + i);
					break;
				} else {
					driver.scrollingToBottomofAPage();
					bc.waitForElement(getAssgnPaginationNextButton());
					getAssgnPaginationNextButton().Click();
					bc.stepInfo("Expected assignment not found in the page " + i);
				}
			}
			bc.waitForElement(getAssignmentAction_ViewinDocView());
			getAssignmentAction_ViewinDocView().waitAndClick(3);
			bc.stepInfo("View on Doc view option is clicked");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting assignment to view in Doc view" + e.getMessage());

		}
		return compareCount;
	}

	/**
	 * @author Jayanthi.ganesan Modified date-18/10/21
	 * @description To finalize the assignment after bulk assigning the documents to
	 *              assignment
	 */
	public void FinalizeAssignmentAfterBulkAssign() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssign_NewAssignment().Visible();
			}
		}), Input.wait60);
		getBulkAssign_NewAssignment().waitAndClick(20);

		assertion.assertTrue(getbulkassgnpopup().isDisplayed());
		getPersistCB_NewAssgn().waitAndClick(15);

		getContinueBulkAssign().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		bc.waitForElement(getAssgn_TotalCount());
		bc.waitForElement(getFinalizeButton());
		getFinalizeButton().waitAndClick(30);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentName
	 * @description this method update details in existing assignment.
	 * @throws InterruptedException
	 * @return It return the status of draw pool toggle
	 */
	public String updateAssignmentDetails(String assignmentName) throws InterruptedException {
		String status = null;
		try {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().SendKeys("updated " + assignmentName);
			bc.passedStep("Assignment name updated as " + "updated " + assignmentName);
			driver.waitForPageToBeReady();
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			System.out.println(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class").toString());
			String drawPoolToggleStatus = (getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class")).toString();
			if (drawPoolToggleStatus.equalsIgnoreCase("true")) {
				getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
				status = "disabled";
			} else {
				getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
				bc.waitTillElemetToBeClickable(getAssgnGrp_Create_DrawPoolCount());
				getAssgnGrp_Create_DrawPoolCount().SendKeys("100");
				bc.passedStep("Draw pool toggle is enabled and the count is set to 100");
				status = "enabled";
			}
			driver.scrollingToBottomofAPage();
			bc.waitForElement(configureButton());
			configureButton().waitAndClick(3);
			bc.waitForElement(instructionTextBox());
			instructionTextBox().SendKeys(Input.searchString1);
			instructionOkButton().waitAndClick(2);
			bc.passedStep("Instruction is updated as " + Input.searchString1);
			driver.scrollingToElementofAPage(getAssgn_permissions(14));
			getAssgn_permissions(14).waitAndClick(10);
			driver.scrollPageToTop();
			Assgnwithdocumentsequence("CustodianName", "Ascending");
			bc.passedStep("Document sequence are updated");
		} catch (Exception e) {
			bc.failedStep("Failed to update the details of assignment");
			UtilityLog.info("Failed due to " + e.getMessage());
		}
		return status;
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentName
	 * @param codingfrom
	 * @param status
	 * @description this method verify the details in copied assignment.
	 * @throws InterruptedException
	 */
	public void verifyAgnmtDetailsInCopyAgnmt(String assignmentName, String codingfrom, String status)
			throws InterruptedException {
		try {
			assertion.assertTrue(getAssignmentName().GetAttribute("value").contains("Copy"));
			assertion.assertTrue(getAssignmentName().GetAttribute("value").contains("updated " + assignmentName));
			bc.passedStep("Assignment name is reflected as expected");
			assertion.assertTrue(getAssignmentCodingFormName().getText().equalsIgnoreCase(codingfrom));
			bc.passedStep("coding form is reflected as expected");
			assertion.assertTrue(getSelectedClassification().selectFromDropdown().getFirstSelectedOption().getText()
					.equalsIgnoreCase("1LR"));
			bc.passedStep("Classification is reflected as expected");
			if (status == "enabled") {
				assertion.assertEquals(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"), "true");
				bc.passedStep("Draw pool toggle is enabled as expected");
				assertion.assertEquals(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value").toString(), "100");
				bc.passedStep("Draw pool count is reflected as expected");
				assertion.assertEquals(getAssgn_keepFamiliesTogetherToggle().GetAttribute("class"), "true");
				bc.passedStep("Families together toggle is enabled as expected");
			} else {
				assertion.assertEquals(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"), "false");
				bc.passedStep("Draw pool toggle is enabled as expected");
				assertion.assertEquals(getAssgn_keepFamiliesTogetherToggle().GetAttribute("class"), "false");
				bc.passedStep("Families together toggle is enabled as expected");
			}
			assertion.assertEquals(getAssgn_permissions(14).GetAttribute("class"), "false");
			driver.scrollingToBottomofAPage();
			bc.waitTillElemetToBeClickable(configureButton());
			configureButton().waitAndClick(3);
			driver.scrollPageToTop();
			bc.waitForElement(instructionTextBox());
			driver.waitForPageToBeReady();
			assertion.assertEquals(instructionTextBox().getText(), Input.searchString1);
			bc.passedStep("Instruction text is reflected as expected");
			instructionOkButton().waitAndClick(2);
			validatedTheSortByAndSortType("CustodianName", "Ascending");
			bc.passedStep("Document sequences are reflected as expected");
		} catch (Exception e) {
			bc.failedStep("Failed to validate the details of copy assignment");
			UtilityLog.info("Failed due to " + e.getMessage());
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentName
	 * @description this method edit copy assignment using pagination concept.
	 * @throws InterruptedException
	 */
	public void editCopyAssignmentUsingPaginationConcept(final String assignmentName) throws InterruptedException {
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectCopyAssignments(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectCopyAssignments(assignmentName));
				getSelectCopyAssignments(assignmentName).waitAndClick(3);
				driver.scrollPageToTop();
				getAssignmentActionDropdown().waitAndClick(3);
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(3);
		bc.stepInfo("Assignment edit option is clicked");
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param cascadeSettings
	 * @description this method verify the changes made by rmu user of cascading
	 *              settings
	 */
	public void validateCascadeSettingsChangesInAgnmt(String cascadeSettings) {
		if (cascadeSettings == "Yes") {
			if (getsortBymetaData().isElementAvailable(5) == true && getCategory().isElementAvailable(5) == true) {
				bc.passedStep("Unable to make changes when parent group has cascade settings on");
			} else {
				bc.failedStep("Able to make changes when parent group has cascade settings on");
			}
		} else {
			if (getsortBymetaData().isElementAvailable(5) == false && getCategory().isElementAvailable(5) == false) {
				bc.passedStep("RMU user can able to make changes when parent group has cascade settings off");
			} else {
				bc.failedStep("RMU user unable to make changes when parent group has cascade settings off");
			}
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentName
	 * @param assignmentGrpName
	 * @param cascadingSetting
	 * @description this method validate the cascading and non-cascading settings in
	 *              copy assignments.
	 * @throws InterruptedException
	 */
	public void validateCopyAssgnCascadingNoncascadingSettings(String assignmentGrpName, String assignmentName,
			String cascadingSettings) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		createCascadeNonCascadeAssgnGroup(assignmentGrpName, cascadingSettings);
		selectAssignmentGroup(assignmentGrpName);
		createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);
		bc.waitForElement(getSelectAssignment(assignmentName));
		getSelectAssignment(assignmentName).waitAndClick(3);
		driver.scrollPageToTop();
		getAssignmentActionDropdown().waitAndClick(5);
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentAction_CopyAssignment().waitAndClick(5);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Record copied successfully");
		getSelectCopyAssignments(assignmentName).waitAndClick(3);
		driver.scrollPageToTop();
		getAssignmentActionDropdown().waitAndClick(3);
		bc.waitForElement(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(3);
		bc.stepInfo("Assignment edit option is clicked");
		validateCascadeSettingsChangesInAgnmt(cascadingSettings);
		bc.waitForElement(getAssignment_BackToManageButton());
		getAssignment_BackToManageButton().waitAndClick(10);
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().ScrollTo();
		getAssignmentActionDropdown().waitAndClick(10);
		bc.waitForElement(getAssignmentAction_DeleteAssignment());
		getAssignmentAction_DeleteAssignment().waitAndClick(3);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment deleted successfully");
		bc.waitForElement(getSelectAssignment(assignmentName));
		getSelectAssignment(assignmentName).waitAndClick(3);
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().ScrollTo();
		getAssignmentActionDropdown().Click();
		bc.waitForElement(getAssignmentAction_DeleteAssignment());
		getAssignmentAction_DeleteAssignment().waitAndClick(3);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment deleted successfully");
		bc.CloseSuccessMsgpopup();
		getAssgGrptionDropdown().waitAndClick(10);
		getAssgnGrp_Delete().WaitUntilPresent();
		bc.waitForElement(getAssgnGrp_Delete());
		getAssgnGrp_Delete().waitAndClick(20);
		bc.getYesBtn().waitAndClick(5);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentName
	 * @description this method navigated the assignment page using pagination.
	 */
	public void viewSelectedAssgnUsingPagination(String assignmentName) {
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		bc.waitForElementCollection(getAssgnPaginationCount());
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				driver.scrollPageToTop();
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(10);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify the displayed validations in success message popup.
	 */
	public void verifyDisplayedValidations() {
		try {
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			bc.waitForElement(getAddReviewersBtn());
			getAddReviewersBtn().waitAndClick(10);
			bc.waitForElement(getSelectUserToAssig());
			getSelectUserToAssig().waitAndClick(5);
			driver.scrollingToElementofAPage(getSelectUserToAssigReviewerManager());
			getSelectUserToAssigReviewerManager().waitAndClick(5);
			bc.waitForElement(getAdduserBtn());
			getAdduserBtn().waitAndClick(5);
			bc.VerifySuccessMessage("Action saved successfully");
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
			getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
			bc.CloseSuccessMsgpopup();
			getDistributeBtn().waitAndClick(3);
			bc.stepInfo("Documents are distributed to reviewer successfully");
			bc.CloseSuccessMsgpopup();
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rev1userName).waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
			getAssgn_ManageRev_Action().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_CompleteAllDocs());
			getAssgn_ManageRev_Action_CompleteAllDocs().waitAndClick(10);
			bc.VerifyWarningMessage("No documents available to do the selected action");
			bc.passedStep(
					"Getting expected message when complete the documents for the reviewer whose not assigned with no documents");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rev1userName).waitAndClick(10);
			getAssgn_ManageRev_Action().waitAndClick(10);
			getAssgn_ManageRev_Action_UnCompleteAllDocs().waitAndClick(10);
			bc.VerifyWarningMessage("No documents available to do the selected action");
			bc.passedStep(
					"Getting expected message when uncomplete the documents for the reviewer whose not assigned with no documents");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
			getAssgn_ManageRev_Action().waitAndClick(10);
			getAssgn_ManageRev_Action_UnCompleteAllDocs().waitAndClick(10);
			bc.waitTillElemetToBeClickable(bc.getYesBtn());
			bc.getYesBtn().waitAndClick(5);
			bc.VerifyWarningMessage("No documents available to do the selected action");
			bc.passedStep(
					"Getting expected message when incomplete the documents for the reviewer whose not having complted documents");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			driver.waitForPageToBeReady();
			driver.Navigate().refresh();
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
			getAssgn_ManageRev_Action().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_CompleteAllDocs());
			getAssgn_ManageRev_Action_CompleteAllDocs().waitAndClick(10);
			bc.waitTillElemetToBeClickable(bc.getYesBtn());
			bc.getYesBtn().waitAndClick(10);
			bc.VerifySuccessMessage("Documents successfully completed for user.");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
			getAssgn_ManageRev_Action().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_CompleteAllDocs());
			getAssgn_ManageRev_Action_CompleteAllDocs().waitAndClick(10);
			bc.waitTillElemetToBeClickable(bc.getYesBtn());
			bc.getYesBtn().waitAndClick(5);
			bc.VerifyWarningMessage("No documents available to do the selected action");
			bc.passedStep(
					"Getting expected message when complete the documents for the reviewer whose documents are already completed");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			driver.Navigate().refresh();
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
			getAssgn_ManageRev_Action().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_UnCompleteAllDocs());
			getAssgn_ManageRev_Action_UnCompleteAllDocs().waitAndClick(10);
			bc.VerifyWarningMessage("Please select an user");
			bc.passedStep("Getting expected message when no user selected before the uncompleted document action");
			bc.waitForElement(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.passedStep("All validations are verified");
		} catch (Exception e) {
			bc.failedStep("Failed to verify the validations");
			assertion.fail();
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify the displayed validations of removed documents.
	 * @param count
	 * @throws InterruptedException
	 */
	public void verifyDisplayedValidationsOfRemovedDocs(int count) throws InterruptedException {
		try {
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			bc.waitForElement(getAddReviewersBtn());
			getAddReviewersBtn().waitAndClick(10);
			bc.waitForElement(getSelectUserToAssig());
			getSelectUserToAssigReviewerManager().waitAndClick(5);
			bc.waitForElement(getAdduserBtn());
			getAdduserBtn().waitAndClick(5);
			bc.VerifySuccessMessage("Action saved successfully");
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
			getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
			bc.CloseSuccessMsgpopup();
			int Totalpages = (int) Math.ceil(count / 2);
			getAssgn_docsToDistribute().SendKeys(Integer.toString(Totalpages));
			getDistributeBtn().waitAndClick(3);
			bc.stepInfo("Documents are distributed to reviewer successfully");
			bc.CloseSuccessMsgpopup();
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
			getAssgn_ManageRev_Action().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_CompleteAllDocs());
			getAssgn_ManageRev_Action_CompleteAllDocs().waitAndClick(10);
			bc.waitTillElemetToBeClickable(bc.getYesBtn());
			bc.getYesBtn().waitAndClick(10);
			bc.VerifySuccessMessage("Documents successfully completed for user.");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
			getAssgn_ManageRev_Action().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_removedoc());
			getAssgn_ManageRev_Action_removedoc().waitAndClick(10);
			bc.waitTillElemetToBeClickable(bc.getYesBtn());
			bc.getYesBtn().waitAndClick(5);
			bc.VerifyWarningMessage("No documents available to do the selected action");
			bc.passedStep("Getting the expected validation when removing the completed documents");
			driver.Navigate().refresh();
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
			getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
			bc.waitForElement(getDistributeBtn());
			getDistributeBtn().waitAndClick(10);
			bc.stepInfo("Documents are distributed to reviewer successfully");
			bc.CloseSuccessMsgpopup();
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
			getAssgn_ManageRev_Action().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_removedoc());
			getAssgn_ManageRev_Action_removedoc().waitAndClick(10);
			bc.waitTillElemetToBeClickable(bc.getYesBtn());
			bc.getYesBtn().waitAndClick(5);
			driver.Navigate().refresh();
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			bc.waitForElement(getAssgn_TodoCountInManageRev(Input.rmu1userName));
			assertion.assertEquals(getAssgn_TodoCountInManageRev(Input.rmu1userName).getText(), "0");
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getEditAggn_Distribute_Unassgndoc());
			assertion.assertEquals(getEditAggn_Distribute_Unassgndoc().getText(), Integer.toString(Totalpages));
			bc.passedStep("removed documents are displayed as unassigned documents in distributed tab as expected");
		} catch (Exception e) {
			bc.failedStep("Failed to verify the displayed validation");
			e.printStackTrace();
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param AssignmentName
	 * @param metaData
	 * @throws InterruptedException
	 * @description- this method will verify the System Define metadata sorting in
	 *               DocList.
	 */
	public void verifyDescendingMetaDataSorting_DocList(String AssignmentName, String metaData)
			throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(AssignmentName).waitAndClick(2);
		driver.getPageSource();
		driver.waitForPageToBeReady();

		bc.waitForElement(getGearIcon());
//		getGearIcon().ScrollTo();
		try {
			getGearIcon().waitAndClick(3);
		} catch (Exception e) {
			getGearIcon().Click();
		}
		bc.waitForElementCollection(metaDatasInSelectedFields_reviewerPg());
		driver.waitForPageToBeReady();
		int metadatasCount = metaDatasInSelectedFields_reviewerPg().size();
		for (int i = 0; i < metadatasCount; i++) {
			metaDatasRemoveBtn_reviewerPg().Click();
			System.out.println(i);
		}
		if (metaDatasRemoveBtn_reviewerPg().isElementAvailable(5) == false) {
			bc.stepInfo("All the existing metadatas are deselected");
			bc.dragAndDropSpecialElement(getMetaData_reviewerPg(metaData), selectedMetaDataFields_reviewerPg());
			bc.stepInfo(metaData + " is selected for sorting");
			saveBtn_reviewerPg().Click();
		} else {
			assertion.fail();
			bc.failedStep("MetaData is not inserted into mini doc list page");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return metaDataslist_reviewerPg().Visible();
			}
		}), Input.wait30);

		List<String> originalOrderedList = bc.getAvailableListofElements(metaDataslist_reviewerPg());
		System.out.println(originalOrderedList);
		List<String> afterSortList = bc.getAvailableListofElements(metaDataslist_reviewerPg());
		Collections.sort(afterSortList);
		System.out.println(afterSortList);
		Collections.sort(afterSortList, Collections.reverseOrder());
		bc.stepInfo("The metadata list after sorted in descending order" + afterSortList);
		try {
			assertion.assertEquals(afterSortList, originalOrderedList);
			bc.passedStep("sucessfully verified that metadata is sorted in descending order");
		} catch (Exception e) {
			bc.failedStep("Assertion exception ocurred,Metadata is not sorted as expected.");

		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 * @param codingForm
	 * @description Create new assignment from assgn and unassgn popup
	 * @throws InterruptedException
	 */
	public void createAssignment_fromAssignUnassignPopup(String assignmentName, String codingForm)
			throws InterruptedException {
		try {
			bc.waitForElement(dashBoardPageTitle());
			bc.waitForElement(getAssignmentName());
			driver.waitForPageToBeReady();
			getAssignmentName().SendKeys(assignmentName);
			driver.waitForPageToBeReady();
			getParentAssignmentGroupName().isDisplayed();
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			SelectCodingform(codingForm);

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while creating new assignment for bulk assign operation" + e.getMessage());
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param count - pureHits
	 * @description this method distribute half the count of docs to reviewer.
	 * @throws InterruptedException
	 * @return it returns the unassigned document count
	 */
	public int distributeHalfTheDocsToReviewer(int count) throws InterruptedException {

		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssigReviewerManager().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
		getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
		int Total = (int) Math.ceil(count / 2);
		int unassignedDocs = count - Total;
		getAssgn_docsToDistribute().SendKeys(Integer.toString(Total));
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo("Documents are distributed to reviewer successfully");
		bc.CloseSuccessMsgpopup();
		bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
		bc.getCloseSucessmsg().waitAndClick(10);
		bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
		getAssgn_ManageRev_Action().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_CompleteAllDocs());
		getAssgn_ManageRev_Action_CompleteAllDocs().waitAndClick(10);
		bc.waitTillElemetToBeClickable(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(10);
		bc.VerifySuccessMessage("Documents successfully completed for user.");
		bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
		bc.getCloseSucessmsg().waitAndClick(10);
		bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
		return unassignedDocs;
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentGrpName
	 * @description this method update assignment group and change drawpool toggle.
	 * @throws InterruptedException
	 * @return status of draw pool toggle
	 */
	public String updateAssgnGrpDetails(String assignmentGrpName) throws InterruptedException {
		String status = null;
		bc.waitForElement(getAssignmentName());
		driver.waitForPageToBeReady();
		getAssignmentName().SendKeys(assignmentGrpName);
		String drawPoolToggleStatus = (getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class")).toString();
		if (drawPoolToggleStatus.equalsIgnoreCase("true")) {
			getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
			status = "disabled";
		} else {
			getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssgnGrp_Create_DrawPoolCount());
			getAssgnGrp_Create_DrawPoolCount().SendKeys("100");
			bc.passedStep("Draw pool toggle is enabled and the count is set to 100");
			status = "enabled";
		}
		return status;
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentGrpName
	 * @param status
	 * @description this method validate assignment group and and change drawpool
	 *              toggle.
	 */
	public void validateUpdatedDetailsInAssgnGrp(String assignmentGrpName, String status) {
		bc.waitForElement(getAssignmentName());
		assertion.assertEquals(getAssignmentName().GetAttribute("value"), assignmentGrpName);
		if (status == "enabled") {
			assertion.assertEquals(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"), "true");
			bc.passedStep("Draw pool toggle is enabled as expected");
		} else if (status == "disabled") {
			assertion.assertEquals(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"), "false");
			bc.passedStep("Draw pool toggle is disabled as expected");
		} else {
			bc.failedStep("Draw pool toggle status not displayed as expected");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentGrpName
	 * @param isSelected        ->No-select and edit the group;Yes->edit the grp
	 *                          directly
	 * @description this method edit the existing assignment group
	 */
	public void editAssgnGrp(String AssignmentGrp, String isSelected) {
		if (isSelected.equalsIgnoreCase("No")) {
			driver.waitForPageToBeReady();
			bc.waitForElement(getAssgnGrp_Select(AssignmentGrp));
			driver.scrollingToBottomofAPage();
			driver.scrollingToElementofAPage(getAssgnGrp_Select(AssignmentGrp));
			getAssgnGrp_Select(AssignmentGrp).waitAndClick(15);
		}
		driver.scrollPageToTop();
		bc.waitTillElemetToBeClickable(getAssgGrptionDropdown());
		getAssgGrptionDropdown().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssgnGrp_Edit());
		getAssgnGrp_Edit().waitAndClick(20);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignmentName
	 */
	public void ValidateRetainedMetaDataList(String assignmentName) {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		GetSelectMetaDataBtn().waitAndClick(10);
		List<String> ExpectedMetadataList = new ArrayList<String>();
		ExpectedMetadataList = bc.availableListofElements(getAssignedMetadataListInAssgnPage());
		System.out.println(ExpectedMetadataList);
		System.out.println(ExpectedMetadataList.size());
		lp = new LoginPage(driver);
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Logged in as reviewer to verify retained metadata list in doc view page.");
		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(assignmentName).waitAndClick(2);
		driver.getPageSource();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		List<String> ActualMetadataList = new ArrayList<String>();
		bc.waitForElementCollection(getRetainedMetadataListInReviewerPage());
		ActualMetadataList = bc.availableListofElements(getRetainedMetadataListInReviewerPage());
		if (ActualMetadataList.size() != 0) {
			bc.waitForElementCollection(getRetainedMetadataListInReviewerPage());
			ActualMetadataList = bc.availableListofElements(getRetainedMetadataListInReviewerPage());
		}
		System.out.println(ActualMetadataList);
		System.out.println(ActualMetadataList.size());
		try {
			assertion.assertEquals(ActualMetadataList, ExpectedMetadataList);
			bc.passedStep("Metadata list from assignments page is matched with metadata list in DocView  Page");

		} catch (Exception e) {
			bc.failedStep("Metadata List mismatched");

		}
		assertion.assertAll();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description to verify tag all option in assignments page
	 */

	public void VerifyTagAllOption() {
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.stepInfo("clicked Action dropdown");
		if (getAssignmentAction_TagAll().isDisplayed()) {
			bc.passedStep("Tag all documents option is displayed in actions dropdown in manage assignments page.");
			bc.passedStep(
					"Sucessfully verified that whether RMU is able to view option TagAll Documents in manage assignments Page");
		} else {
			bc.failedStep("Tag All documents option is not displayed in manage assignment page under action dropdown");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description navigate to new or edit assignment based on option parameter
	 * @param option
	 */
	public void NavigateToNewEditAssignmentPage(String option) {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getAssignmentActionDropdown());
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(10);
		if (option.equalsIgnoreCase("new")) {
			bc.waitTillElemetToBeClickable(getAssignmentAction_NewAssignment());
			bc.waitForElement(getAssignmentAction_NewAssignment());
			getAssignmentAction_NewAssignment().waitAndClick(20);
		}
		if (option.equalsIgnoreCase("edit")) {
			bc.waitForElement(getAssignmentAction_EditAssignment());
			bc.waitTillElemetToBeClickable(getAssignmentAction_EditAssignment());

			getAssignmentAction_EditAssignment().ScrollTo();
			getAssignmentAction_EditAssignment().waitAndClick(10);
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description to verify the success message validation
	 */
	public void verifySuccessMsgValidations() {
		try {
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			bc.waitForElement(getAddReviewersBtn());
			getAddReviewersBtn().waitAndClick(10);
			driver.scrollingToElementofAPage(getSelectUserToAssigReviewerManager());
			getSelectUserToAssigReviewerManager().waitAndClick(10);
			bc.waitForElement(getAdduserBtn());
			getAdduserBtn().waitAndClick(5);
			bc.VerifySuccessMessage("Action saved successfully");
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
			getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
//			bc.CloseSuccessMsgpopup();
			getDistributeBtn().waitAndClick(3);
			bc.stepInfo("Documents are distributed to reviewer successfully");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			bc.waitTime(5);
			bc.waitForElement(getAssgn_ManageRev_selectReviewer(Input.rmu1userName));
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			selectActionsInManageRev(getAssgn_ManageRev_Action_CompleteAllDocs());
			bc.VerifySuccessMessage("Documents successfully completed for user.");
			bc.passedStep("Documents are completed successfully for the reviewer");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			selectActionsInManageRev(getAssgn_ManageRev_Action_UnCompleteAllDocs());
			bc.VerifySuccessMessage("Documents successfully un-completed for user.");
			bc.passedStep("Documents are uncompleted successfully for the reviewer");
			bc.waitForElement(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			driver.Navigate().refresh();
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			selectActionsInManageRev(getAssgn_ManageRev_Action_removedoc());
			bc.VerifySuccessMessage("Action saved successfully");
			bc.passedStep("Documents are removed for the reviewer successfully");
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
			getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
			getDistributeBtn().waitAndClick(3);
			bc.stepInfo("Documents are distributed to reviewer successfully");
			bc.passedStep("Documents are reassigned successfully for the reviewer");
			bc.CloseSuccessMsgpopup();
		} catch (Exception e) {
			bc.failedStep("Failed to verify the displayed validations");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param assignmentName
	 * @description to verify the page navigated docview successfully
	 */
	public void validateNavigationOfDocViewPg(String assignmentName) {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
		selectActionsInManageRev(getAssgn_ManageRev_Action_ViewDocview());
		bc.waitForElement(assignmentNameInDocViewPg(assignmentName));
		if (assignmentNameInDocViewPg(assignmentName).isDisplayed() == true) {
			bc.passedStep("Doc-view page of the reviewer displayed successfully");
		} else {
			bc.failedStep("Doc-view page of the reviewer is not displayed");
			assertion.fail();
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description to verify the page navigated doclist successfully
	 */
	public void validateNavigationOfDocListPg() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
		selectActionsInManageRev(getAssgn_ManageRev_Action_ViewDoclist());
		bc.waitForElementCollection(getAllDocIds_DocListreviewerPg());
		if (docListPageTitle().isDisplayed() == true) {
			bc.passedStep("DocList page of the reviewer displayed successfully");
		} else {
			bc.failedStep("DocList page of the reviewer is not displayed");
			assertion.fail();
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To perform the action in manage reviewer tab based on parameter
	 * @param actions
	 */
	public void selectActionsInManageRev(Element actions) {
		// try {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
		getAssgn_ManageRev_Action().waitAndClick(10);
		bc.waitTillElemetToBeClickable(actions);
		actions.waitAndClick(10);
		if (bc.getYesBtn().isElementAvailable(2)) {
			bc.waitTillElemetToBeClickable(bc.getYesBtn());
			bc.getYesBtn().waitAndClick(5);
		}
//		} catch (Exception e) {
//			bc.failedStep("Failed to select action functions");
//		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify the keywords are checked
	 */
	public void verifyKeywordsPopup() {
		try {
			getAssgn_Keywordsbutton().waitAndClick(10);
			bc.waitForElement(getAssgn_Keywordspopup());
			for (int j = 1; j <= getAllKeywordsList().size(); j++) {
				try {
					assertion.assertEquals(getKeywordFromList(j).GetAttribute("checked"), "true");
					bc.passedStep("Keyword is checked");
					assertion.assertAll();
				} catch (Exception e) {
					bc.failedStep("Keyword is not checked");
				}
			}
		} catch (Exception e) {
			bc.failedStep("Failed to validate keywords in assignment page");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assgngrpName
	 * @param cascadeSettings
	 * @throws InterruptedException
	 */
	public void createCascadeNonCascadeAssgnGroup_withoutSave(String assgngrpName, String cascadeSettings)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitForElement(getAssgGrptionDropdown());
		bc.waitTillElemetToBeClickable(getAssgGrptionDropdown());
		getAssgGrptionDropdown().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssgnGrp_Create());
		bc.waitForElement(getAssgnGrp_Create());
		getAssgnGrp_Create().waitAndClick(10);
		bc.waitForElement(getAssignmentName());
		driver.waitForPageToBeReady();
		getAssignmentName().SendKeys(assgngrpName);
		driver.waitForPageToBeReady();
		if ("Yes".equalsIgnoreCase(cascadeSettings)) {
			bc.waitForElement(getAssgngrp_CascadeSetting());
			getAssgngrp_CascadeSetting().waitAndClick(5);
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description To validate the cascade settings is enabled
	 */

	public void validateCascadeSettings() {
		String expectedMsg = "Cascade Settings is ENABLED for this Assignment";
		driver.scrollingToElementofAPage(getCascadeSettingsText());
		bc.waitForElement(getCascadeSettingsText());
		if (getAssgngrp_CascadeSetting().isElementAvailable(5)) {
			assertion.assertFalse(false);
		} else {
			assertion.assertTrue(true);
		}
		if ((getCascadeSettingsText().getText()).equalsIgnoreCase(expectedMsg)) {
			bc.passedStep("Verified Cascading settings Button and Text");
		} else {
			bc.failedStep("Exception occurred");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description To validate the cascade settings is disabled
	 */
	public void validateNonCascadeSettings() {
		String expectedMsg = "Cascade Settings is DISABLED for this Assignment";
		driver.scrollingToElementofAPage(getCascadeSettingsText());
		bc.waitForElement(getCascadeSettingsText());
		if (getAssgngrp_CascadeSetting().isDisplayed()) {
			assertion.assertFalse(false);
		} else {
			assertion.assertTrue(true);
		}
		bc.ValidateElement_Presence(getAssgngrp_CascadeSetting(), "Cascade Button is present");
		if ((getCascadeSettingsText().getText()).equalsIgnoreCase(expectedMsg)) {
			bc.passedStep("Verified Cascading settings Button and Text");
		} else {
			bc.failedStep("Exception occurred");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description To validate the toggles are enabled
	 */
	public void verifyCascadeFunctionality_OFF() {
		try {
			bc.ValidateElement_Absence(getsortBymetaData(), "Enabled SortByMetadata Tab is present");
			bc.ValidateElement_Absence(getCategory(), "Enabled Cateogory Tab is present");
			bc.ValidateElement_Absence(SelectMetaDataPopUpDisabled(), "Enabled SelectMetaData PopUp is present");
			bc.ValidateElement_Absence(SelectKeywordPopUpDisabled(), "Enable Keyword popup is  present");
			bc.ValidateElement_Absence(SelectInstructionPopUpDisabled(), "Enabled instruction PopUp is present ");
			bc.ValidateElement_Absence(getEmailThreadsTogetherBtnDisabled(),
					"Enabled getEmailThreadsTogetherBtn is present");
			bc.ValidateElement_Absence(getDraawPoolToggleDisabled(), "Enabled Draw pool toggle is present");
			bc.ValidateElement_Absence(getKeepFamiliesBtnDisabled(), "Enabled Keep Families is present");
			bc.passedStep(
					"Sucessfully verified  the cascading functionality if it is OFF for parent Assignment Group.");
			driver.scrollingToBottomofAPage();
		} catch (Exception e) {
			bc.failedStep(
					"Exception Occured while validating the All the controls except Assignment Group are Enabled");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description To validate the toggles are disabled
	 */
	public void VerifyCascadefunctionality_ON() {
		try {
			bc.ValidateElement_Presence(getsortBymetaData(), "Disabled SortByMetadata Tab ");
			bc.ValidateElement_Presence(getCategory(), "Disabled Cateogory Tab");
			validateCascadeSettings();
			bc.ValidateElement_Presence(SelectMetaDataPopUpDisabled(), "disabled SelectMetaData PopUp");
			bc.ValidateElement_Presence(SelectKeywordPopUpDisabled(), "disabled SelectMetaData PopUp");
			bc.ValidateElement_Presence(SelectInstructionPopUpDisabled(), "disabled SelectMetaData PopUp");
			bc.ValidateElement_Presence(getEmailThreadsTogetherBtnDisabled(), "disabled SelectMetaData PopUp");
			bc.ValidateElement_Presence(getDraawPoolToggleDisabled(), "disabled SelectMetaData PopUp");
			bc.ValidateElement_Presence(getKeepFamiliesBtnDisabled(), "disabled SelectMetaData PopUp");
			driver.scrollingToBottomofAPage();
			bc.ValidateElementCollection_Presence(getPresentationControlTogglesDisabled(1),
					"All Toggles are disabled under CONTROL THE PRESENTATION OF DOCVIEW "
							+ "FOR REVIEWERS WHILE IN THIS ASSIGNMENT ");
			bc.ValidateElementCollection_Presence(getPresentationControlTogglesDisabled(2),
					"All Toggles are disabled under CONTROL THE PRESENTATION OF DOCVIEW "
							+ "FOR REVIEWERS WHILE IN THIS ASSIGNMENT ");
			bc.ValidateElementCollection_Presence(getPresentationControlTogglesDisabled(3),
					"All Toggles are disabled under CONTROL THE PRESENTATION OF DOCVIEW "
							+ "FOR REVIEWERS WHILE IN THIS ASSIGNMENT");
			bc.passedStep("Sucessfully verified  the cascading functionality if it is ON for parent Assignment Group.");
			driver.scrollingToBottomofAPage();

		} catch (Exception e) {
			bc.failedStep(
					"Exception Occured while validating the All the controls except Assignment Group are Disabled");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param parentAssgnGroupName
	 * @param childAssgnGroupName
	 * @param AssignmentName
	 * @description to delete the assignment from assignment group
	 */
	public void deleteAssignmentFromAssgnGrp(String parentAssgnGroupName, String childAssgnGroupName,
			String AssignmentName) {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getAssgGrptionDropdown());
		bc.waitForElement(getArrowBtn_AssgnGroup(parentAssgnGroupName));
		getArrowBtn_AssgnGroup(parentAssgnGroupName).waitAndClick(5);
		bc.waitForElement(getAssgnGrp_Select(childAssgnGroupName));
		getAssgnGrp_Select(childAssgnGroupName).waitAndClick(3);
		driver.waitForPageToBeReady();
		bc.waitForElement(getSelectAssignment(AssignmentName));
		driver.scrollingToElementofAPage(getSelectAssignment(AssignmentName));
		getSelectAssignment(AssignmentName).waitAndClick(5);
		driver.scrollPageToTop();
		getAssignmentActionDropdown().waitAndClick(10);
		bc.waitForElement(getAssignmentAction_DeleteAssignment());
		getAssignmentAction_DeleteAssignment().waitAndClick(30);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment deleted successfully");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param noOfElements
	 * @param index
	 * @description to verify the all toggle buttons under presentation control is
	 *              enabled
	 */
	public void ValidateEnabledToggleBtn_PresentationControl(int noOfElements, int index) {
		for (int i = 1; i <= noOfElements; i++) {
			int k = index;
			boolean status = GetPresentaionControlToggle_Column1(i, k).isElementAvailable(5);
			System.out.println(status);
			System.out.println(i);
			if (status == true) {
				bc.failedStep("Disabled Toggle Element found under Presentaion Control ");
				assertion.fail();
			} else {
				assertion.assertTrue(true);

			}
		}
		bc.passedStep("All Toggle button present under Presentation Control is Enabled");

	}

	/**
	 * @author Raghuram A date: 10/18/21 NA Modified date:N/A Modified by:N/A A
	 * @description to add reviewer and distribute documents to that reviewer
	 * @param assignmentName
	 * @throws InterruptedException
	 */
	public void addReviewerAndDistributeDocsT(String assignmentName) throws InterruptedException {

		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(30);
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssign());
		getSelectUserToAssign().waitAndClick(10);
		getAdduserBtn().Click();
		bc.VerifySuccessMessage("Action saved successfully");
		getDistributeTab().waitAndClick(20);
		bc.waitForElement(getSelectUserInDistributeTab());
		getSelectUserInDistributeTab().waitAndClick(20);
		getDistributeBtn().waitAndClick(15);
		getAssignment_BackToManageButton().waitAndClick(10);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Raghuram A date: 10/12/21 NA Modified date:N/A Modified by:N/A A
	 * @description To select an assignment from assignments
	 * @param assignmentName
	 * @return it returns the documents in assignments
	 */
	public String selectAssignmentToView(String assignmentName) {
		String compareCount = null;
		try {
			bc.waitForElement(getNumberOfAssignmentsToBeShown());
			getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
			driver.scrollingToBottomofAPage();
			bc.waitForElement(getAssgn_Pagination());
			int count = ((getAssgnPaginationCount().size()) - 2);
			for (int i = 0; i < count; i++) {
				driver.waitForPageToBeReady();
				Boolean status = getSelectAssignment(assignmentName).isDisplayed();
				// Assert.assertEquals(finalCount, compareCount);
				if (status == true) {
					getSelectAssignment(assignmentName).ScrollTo();
					getSelectAssignment(assignmentName).waitAndClick(5);
					compareCount = getSelectAssignmentDocCount(assignmentName).getText();
					System.out.println(compareCount);
					driver.scrollPageToTop();
					bc.waitForElement(getAssignmentActionDropdown());
					getAssignmentActionDropdown().Click();
					bc.stepInfo("Expected assignment found in the page " + i);
					break;
				} else {
					driver.scrollingToBottomofAPage();
					bc.waitForElement(getAssgnPaginationNextButton());
					getAssgnPaginationNextButton().Click();
					bc.stepInfo("Expected assignment not found in the page " + i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting assignment to view in Doc view" + e.getMessage());

		}
		return compareCount;
	}

	/**
	 * @author Raghuram A date: 10/18/21 NA Modified date:N/A Modified by:N/A A
	 * @description To create an assignment
	 * @param assignmentName
	 * @param codingForm
	 */
	public void quickAssignmentCreation(String assignmentName, String codingForm) {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getAssignmentName());
			getAssignmentName().SendKeys(assignmentName);
			getParentAssignmentGroupName().isDisplayed();
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			SelectCodingform(codingForm);

			driver.scrollingToBottomofAPage();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author : Raghuram A date: 10/18/21 Description : Modified date: 11/15/21
	 *         Modified by: Raghuram A getAssgn_OverrideOptimizedSortToggle( due to
	 *         update in the application
	 * @param docHistorytoogle
	 * @param withoutSaveCompToggle
	 * @param completeCoddingStamtoggle
	 * @param getAssgn_OverrideOptimizedSortToggle
	 * @param toggle2
	 * @param toggle3
	 * @param toggle4
	 * @param toggle5
	 * @param toggle6
	 * @description to perform the actions in quick assign toggled
	 */
	public void quickAssignToggles(Boolean docHistorytoogle, Boolean withoutSaveCompToggle,
			Boolean completeCoddingStamtoggle, Boolean getAssgn_OverrideOptimizedSortToggle, Boolean toggle2,
			Boolean toggle3, Boolean toggle4, Boolean toggle5, Boolean toggle6) {

		if (completeCoddingStamtoggle) {
			getAssignment_Edit_CodingStampApplied().waitAndClick(10);
		}
		if (withoutSaveCompToggle) {
			getAssgn_SaveWithoutCompleteingToggle().waitAndClick(5);
		}
		if (getAssgn_OverrideOptimizedSortToggle) {
			bc.waitForElement(getAssgn_OverrideOptimizedSortToggle());
			String tagButtonStatus = getAssgn_OverrideOptimizedSortToggle().GetCssValue("class");
			if (tagButtonStatus.equalsIgnoreCase("false")) {
				bc.stepInfo("OptimizedSortToggle OFF");
				getAssgn_OverrideOptimizedSortToggle().waitAndClick(3);
				bc.stepInfo("OptimizedSortToggle Enabled");
			} else {
				bc.stepInfo("OptimizedSortToggle ON");
			}
		}

	}

	/**
	 * @author Raghuram A date: 10/18/21 NA Modified date:N/A Modified by:N/A A
	 * @description To save an assignment
	 * @param assignmentName
	 * @param codingForm
	 */
	public void saveAssignment(String assignmentName, String codingForm) {
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(30);
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		System.out.println("Docs assigned to  " + assignmentName);
		bc.stepInfo("Assignment " + assignmentName + " created with CF " + codingForm);
		bc.stepInfo("Docs assigned to  " + assignmentName);
	}

	/**
	 * @author Raghuram A date: 10/18/21 NA Modified date:N/A Modified by:N/A A
	 * @description To perform an assignment actions
	 * @param type
	 */
	public void assignmentActions(String type) {
		if (type.equals("DocView")) {
			bc.waitForElement(getAssignmentAction_ViewinDocView());
			getAssignmentAction_ViewinDocView().waitAndClick(3);
		} else if (type.equals("Edit")) {
			bc.waitForElement(getAssignmentAction_EditAssignment());
			getAssignmentAction_EditAssignment().waitAndClick(3);
		}
		bc.stepInfo("View on " + type + " option is clicked");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will verify the display of DrawPool in Action column
	 *              in reviewer page
	 * @param assignmentName
	 * @param type
	 */
	public void verifyDrawPoolToggledisplay(String assignmentName, String type) {
		if (type.equalsIgnoreCase("enabled")) {
			if (getAssignmentsDrawPoolInreviewerPg(assignmentName).isDisplayed() == true) {
				bc.passedStep("Draw pool link is displayed");
			} else {
				bc.failedStep("Draw pool link is not displayed");
				assertion.fail();
			}
		} else {
			if (getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementAvailable(5) == true) {
				bc.failedMessage("Draw pool link is displayed");
				assertion.fail();
			} else {
				bc.passedStep("Draw pool link is not displayed");
				assertion.assertTrue(true);
			}

		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will verify the TODO docs count in reviewer page
	 * @param assignmentName
	 * @param TodoCount_Expected
	 */
	public void VerifyTodoCountInReviewerPg(String assignmentName, String TodoCount_Expected) {
		bc.waitForElement(getAssignmentsInreviewerPg());
		getAssignmentsInreviewerPg().waitAndClick(10);
		bc.waitForElement(getAssignmentsTodoCountInreviewerPg(assignmentName));
		try {
			bc.waitTillTextToPresent(getAssignmentsTodoCountInreviewerPg(assignmentName), TodoCount_Expected);
			String ActualTodoCount = getAssignmentsTodoCountInreviewerPg(assignmentName).getText();
			bc.stepInfo("The todo count displayed in reviewer page is " + ActualTodoCount);
			assertion.assertEquals(ActualTodoCount, TodoCount_Expected);
			bc.passedStep("The To do count are displayed as expected");
		} catch (Exception e) {
			bc.failedStep("The To do count are not displayed as expected");
		}
		assertion.assertAll();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will remove the assigned docs from the assigned
	 *              user.
	 * @param user
	 */

	public void removeDocs(String user) {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		getAssgn_ManageRev_selectReviewer(user).waitAndClick(20);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
		getAssgn_ManageRev_Action().waitAndClick(10);
		bc.waitForElement(getAssgn_ManageRev_Action_removedoc());
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_removedoc());
		getAssgn_ManageRev_Action_removedoc().waitAndClick(10);
		bc.waitTillElemetToBeClickable(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
		bc.stepInfo("Removed all the assigned document from the RMU reviewer.");
		bc.passedStep("Documents are removed successfully for the reviewer");
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Steffy
	 * @description this method will the reassign docs to the specific user.
	 * @param user Name of the user for whom the docs needs to be reassigned
	 */
	public void reassignDocs(String user) {
		bc.waitForElement(getDistributeTab());
		bc.waitTillElemetToBeClickable(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		bc.waitForElement(getSelectUserInDistributeTabToReassign(user));
		bc.waitTillElemetToBeClickable(getSelectUserInDistributeTabToReassign(user));
		getSelectUserInDistributeTabToReassign(user).waitAndClick(20);
		bc.waitForElement(getDistributeBtn());
		bc.waitTillElemetToBeClickable(getDistributeBtn());
		getDistributeBtn().waitAndClick(3);
		bc.VerifySuccessMessage("Action saved successfully");
		bc.stepInfo("Documents are distributed to reviewer successfully");
		bc.passedStep("Documents are reassigned successfully for the reviewer");
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description To delete an assignment from single assignment group
	 * @param parentAssgnGroupName
	 * @param AssignmentName
	 */
	public void deleteAssignmentFromSingleAssgnGrp(String parentAssgnGroupName, String AssignmentName) {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getAssgGrptionDropdown());
		driver.scrollingToElementofAPage(getAssgnGrp_Select(parentAssgnGroupName));
		bc.waitForElement(getAssgnGrp_Select(parentAssgnGroupName));
		getAssgnGrp_Select(parentAssgnGroupName).waitAndClick(3);
		driver.waitForPageToBeReady();
		bc.waitForElement(getSelectAssignment(AssignmentName));
		driver.scrollingToElementofAPage(getSelectAssignment(AssignmentName));
		getSelectAssignment(AssignmentName).waitAndClick(5);
		driver.scrollPageToTop();
		getAssignmentActionDropdown().waitAndClick(10);
		bc.waitForElement(getAssignmentAction_DeleteAssignment());
		getAssignmentAction_DeleteAssignment().waitAndClick(30);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment deleted successfully");
		bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
		bc.getCloseSucessmsg().waitAndClick(10);
		bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method un-assign the added reviewer
	 * @param pureHits
	 * @throws InterruptedException
	 */
	public void unassignTheAddedReviewer(int pureHits) throws InterruptedException {
		try {
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			bc.waitForElement(getAddReviewersBtn());
			getAddReviewersBtn().waitAndClick(10);
			bc.waitForElement(getSelectUserToAssig());
			getSelectUserToAssigReviewerManager().waitAndClick(5);
			bc.waitForElement(getAdduserBtn());
			getAdduserBtn().waitAndClick(5);
			bc.VerifySuccessMessage("Action saved successfully");
			bc.waitForElement(getEditAggn_Distribute_UnassgndocCount(pureHits));
			getDistributeTab().waitAndClick(5);
			bc.waitTillElemetToBeClickable(getSelectUserInDistributeTabsReviewerManager());
			getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
			getDistributeBtn().waitAndClick(3);
			bc.stepInfo("Documents are distributed to reviewer successfully");
			bc.CloseSuccessMsgpopup();
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			selectActionsInManageRev(getAssgn_ManageRev_Action_Unassignuser());
			bc.passedStep("Selected reviewer unassigned successfully");
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getEditAggn_Distribute_Unassgndoc());
			bc.waitTillTextToPresent(getEditAggn_Distribute_Unassgndoc(), Integer.toString(pureHits));
			String unassignedDocs = getEditAggn_Distribute_Unassgndoc().getText();
			assertion.assertEquals(unassignedDocs, Integer.toString(pureHits));
			bc.passedStep("Assigned documents are moved to unassigned to distribute again after the user is removed");
		} catch (Exception e) {
			bc.failedStep("Failed to verify the displayed validation");
			e.printStackTrace();
		}
		assertion.assertAll();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method verify the redistributed documents in another user
	 * @param count
	 */
	public void verifyRedistributedDocsToAnotherUser(int count) {
		try {
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			bc.waitForElement(getAddReviewersBtn());
			getAddReviewersBtn().waitAndClick(10);
			bc.waitTillElemetToBeClickable(getSelect2ndUserToAssign());
			getSelect2ndUserToAssign().waitAndClick(5);
			driver.scrollingToElementofAPage(getSelectUserToAssigReviewerManager());
			getSelectUserToAssigReviewerManager().waitAndClick(10);
			bc.waitForElement(getAdduserBtn());
			getAdduserBtn().waitAndClick(5);
			bc.VerifySuccessMessage("Action saved successfully");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
			getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
			getDistributeBtn().waitAndClick(3);
			bc.stepInfo("Documents are distributed to reviewer successfully");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			selectActionsInManageRev(getAssgn_ManageRev_Action_CompleteAllDocs());
			bc.VerifySuccessMessage("Documents successfully completed for user.");
			bc.passedStep("Documents are completed successfully for the reviewer");
			bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			selectActionsInManageRev(getAssgn_ManageRev_Action_UnCompleteAllDocs());
			bc.VerifySuccessMessage("Documents successfully un-completed for user.");
			bc.passedStep("Documents are uncompleted successfully for the reviewer");
			bc.waitForElement(bc.getCloseSucessmsg());
			bc.getCloseSucessmsg().waitAndClick(10);
			driver.Navigate().refresh();
			bc.waitForElement(getAssignment_ManageReviewersTab());
			getAssignment_ManageReviewersTab().waitAndClick(10);
			getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
			selectActionsInManageRev(getAssgn_ManageRev_Action_redistributedoc());
			bc.waitTillElemetToBeClickable(getSelectReviewerInRedistributedDocsTab(Input.rev1userName));
			getSelectReviewerInRedistributedDocsTab(Input.rev1userName).waitAndClick(5);
			bc.waitTillElemetToBeClickable(getAssgn_Redistributepopup_save());
			getAssgn_Redistributepopup_save().waitAndClick(5);
			bc.VerifySuccessMessage("Action saved successfully");
			bc.passedStep("Documents are redistributed to another reviewer successfully");
			driver.waitForPageToBeReady();
			bc.waitTillTextToPresent(getAssgn_TodoCountInManageRev(Input.rev1userName), Integer.toString(count));
			String toDoCount = getAssgn_TodoCountInManageRev(Input.rev1userName).getText();
			assertion.assertEquals(toDoCount, Integer.toString(count));
			bc.CloseSuccessMsgpopup();
		} catch (Exception e) {
			bc.failedStep("Failed to redistribute the documents");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method verifies the todo count in manage redistributed
	 *              documents
	 */
	public void VerifyTodocountinManageRevTab_RedistributeDocs() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 2; i++) {
			String temp = getAssgn_TodoCountInManageRevTab(i).getText();
			System.out.println(temp);
			System.out.println(i);
			if (Integer.parseInt(temp) == 0) {
				getAssgn_TodoCountInManageRevTab(i).waitAndClick(10);
				bc.passedStep("Select a Reviewer that has no assigned docs (0 in Distributed to User column).");
				break;
			}
		}
		getAssgn_ManageRev_Action().waitAndClick(20);
		bc.waitForElement(getAssgn_RedistributeDoc());
		getAssgn_RedistributeDoc().waitAndClick(20);
		try {
			bc.VerifyWarningMessage("No documents available to do the selected action");
			bc.passedStep(
					"Warning Message Displayed As Exepcted When we tried to Redistribute Docs to a Reviewer user.");
		} catch (Exception e) {
			bc.failedStep("Warning Message not displayed");

		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param Username
	 * @param AssignmentName
	 * @throws InterruptedException
	 */

	public void VerifyRedistributeDocsInToDoCount_ReviewerPg(String Username, String AssignmentName)
			throws InterruptedException {
		System.out.println(Username);
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_selectReviewer(Input.rmu1userName));
		getAssgn_ManageRev_selectReviewer(Input.rmu1userName).ScrollTo();
		getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(10);
		bc.stepInfo("Selected the Reviewer that is assigned with few docs");
		getAssgn_ManageRev_Action().waitAndClick(20);
		bc.waitForElement(getAssgn_RedistributeDoc());
		getAssgn_RedistributeDoc().waitAndClick(20);
		bc.waitForElement(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		bc.waitForElement(getCountofRedistributeDocs());
		String CountOfDocsRedistributed = getCountofRedistributeDocs().GetAttribute("value");
		System.out.println("Count of docs redistributed" + CountOfDocsRedistributed);
		bc.waitForElement(getUsersListInRedistrubutePopup());
		getSelectuserToRedistributeDocs(Input.rev1userName).waitAndClick(10);
		getAssgn_Redistributepopup_save().waitAndClick(10);
		driver.waitForPageToBeReady();
		getAssgn_TodoCountInManageRevTabWithUser(Input.rev1userName).waitAndClick(10);
		bc.waitTillTextToPresent(getAssgn_TodoCountInManageRevTabWithUser(Input.rev1userName),
				CountOfDocsRedistributed);
		String CountInTODOColumn = getAssgn_TodoCountInManageRevTabWithUser(Input.rev1userName).getText();
		System.out.println(CountInTODOColumn);
		assertion.assertEquals(CountInTODOColumn, CountOfDocsRedistributed);
		bc.stepInfo("Cont of Docs Redistributed to user Displayed in TODO column of Manage Reviewer Tab as Exepcted");
		lp = new LoginPage(driver);
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Logged in as reviewer user to whom the docs are redistributed.");
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(getTotalDocsCountInReviewerDashboard(AssignmentName));
		String ActualDocs_value = getTotalDocsCountInReviewerDashboard(AssignmentName).getText().trim();
		System.out.println(ActualDocs_value);
		String TotslDocs = ActualDocs_value.substring(7, 11).trim();
		try {
			assertion.assertEquals(TotslDocs, CountOfDocsRedistributed);
			bc.passedStep(
					" My Dashboard is displayed and the \"Your Batch Progress To Completion\" cell for the assignment shows "
							+ ActualDocs_value + " which is expected");
		} catch (Exception e) {
			bc.failedStep("Doc COunt is not displayed as expected--" + ActualDocs_value + "");

		}
		assertion.assertAll();
	}

	/**
	 * @Author Mohan Created on 15/11/2021
	 * @Description To enable the Complete Coding Stamp Toggle present in AssignPage
	 * @param assignmentName
	 */
	public void editAssignmentAndEnableToggleForCodingStamp(String assignmentName) {

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		driver.scrollPageToTop();

		getAssignmentActionDropdown().waitAndClick(10);

		bc.waitForElement(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(3);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_CodingStampAplliedToggle());

		String analyticalPanelFlag = getAssgn_CodingStampAplliedToggle().GetAttribute("class");

		if (!analyticalPanelFlag.contains("true")) {
			getAssgn_CodingStampAplliedToggle().waitAndClick(10);
		}

		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment updated successfully");
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date:28/8/2021 Modified
	 *         by:Baskar
	 * @Description: Assignment Distributing to reviewer
	 */

	public void assignmentDistributingToTwoReviewer() {
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignment_ManageReviewersTab());
		bc.waitTillElemetToBeClickable(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		bc.waitTillElemetToBeClickable(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssig().WaitUntilPresent().ScrollTo();
		bc.waitTillElemetToBeClickable(getSelectUserToAssig());
		getSelectUserToAssig().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssigTwo());
		getSelectUserToAssigTwo().waitAndClick(10);
		bc.waitForElement(getAdduserBtn());
		bc.waitTillElemetToBeClickable(getAdduserBtn());
		getAdduserBtn().waitAndClick(10);
		bc.waitForElement(getDistributeTab());
		bc.waitTillElemetToBeClickable(getDistributeTab());
		getDistributeTab().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getSelectUserInDistributeTabs());
		getSelectUserInDistributeTabs().waitAndClick(10);
		bc.waitForElement(getSelectUserInDistributeTabsTwo());
		getSelectUserInDistributeTabsTwo().waitAndClick(10);
		bc.waitForElement(getDistributeBtn());
		bc.waitTillElemetToBeClickable(getDistributeBtn());
		getDistributeBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date:28/8/2021 Modified
	 *         by:Baskar
	 * @Description: This method used to manage assignment to docview page asRMU
	 * @param assignmentName
	 */

	public void manageAssignmentToDocViewAsRmu(String assignmentName) {
		driver.waitForPageToBeReady();
		assignmentPagination(assignmentName);
		bc.waitForElement(getAssignmentAction_ViewinDocView());
		getAssignmentAction_ViewinDocView().waitAndClick(5);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignmentName1
	 * @description This method verifies the assignment is selected or not
	 */
	public void Checkclickedstatus(String assignmentName1) {
		driver.waitForPageToBeReady();
		if (!getSelectAssignment(assignmentName1).GetAttribute("class").contains("highlight")) {

			getSelectAssignment(assignmentName1).Click();
		} else {
			bc.stepInfo("Assignments is already in clicked state");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param ele
	 * @description This method will check wether the toggle btn under control the
	 *              presentaion of doc view for reviewers while in this assignment
	 *              is in enabled state
	 */

	public void checkForToggleEnable(Element ele) {
		driver.scrollingToBottomofAPage();
		if (ele.GetAttribute("class").contentEquals("true")) {
			bc.passedStep("Toggle is enabled by default.");
		} else {
			bc.failedStep("Toggle is not enabled by default.");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param KeywordName
	 * @param list1
	 * @param listToverify
	 * @description This method verifies the keywords are displayed before and after
	 *              deletion
	 */
	public void verifyKeywordsBeforeAndAfterDelete(String KeywordName, List<String> listToverify,
			boolean verifyBeforeDelete) {
		getAssgn_Keywordsbutton().waitAndClick(10);
		bc.waitForElement(getAssgn_Keywordspopup());
		List<String> result = new ArrayList<String>();
		result = bc.getAvailableListofElements(KeywordNamesList());
		if (verifyBeforeDelete == true) {
			if (result.contains(KeywordName) && result.containsAll(listToverify)) {
				bc.passedStep(KeywordName + " is  Found after clicking keyword button");
				bc.passedStep("Add Keyword window  pop-up contains only keywords associated to the security group");
			} else {
				bc.stepInfo(KeywordName + " Keyword is not Found");
				bc.failedStep("Add Keyword window  pop-up does not contains only keywords associated"
						+ " to the security group");
			}
		} else {
			if ((!result.contains(KeywordName)) && (result.containsAll(listToverify))) {
				bc.passedStep(KeywordName + " is  Not Found after Deleting keyword ");
			} else {
				bc.failedStep(KeywordName + " Deleted Keyword is  Found");
				bc.failedStep("Add Keyword window  pop-up does not contains only keywords associated"
						+ " to the security group");
			}
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param AssName
	 * @description This method delete all the assignments
	 */

	public void deleteAllAssignments(String AssName) {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		boolean status = true;
		while (status == true) {
			System.out.println("Count of assignments found " + GetAssignmentsList(AssName).size());
			bc.waitForElementCollection(GetAssignmentsList(AssName));
			if (GetAssignmentsList(AssName).Displayed()) {
				int size = GetAssignmentsList(AssName).size();
				System.out.println("Count in if loop" + GetAssignmentsList(AssName).size());
				// for (int i = 1; i <= size; i++) {
				// System.out.println("for loop" + i);
				driver.waitForPageToBeReady();
				bc.waitTillElemetToBeClickable(GetAssignments(AssName));
				GetAssignments(AssName).ScrollTo();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				GetAssignments(AssName).waitAndClick(30);
				driver.scrollPageToTop();
				bc.waitForElement(getAssignmentActionDropdown());
				getAssignmentActionDropdown().waitAndClick(15);
				bc.waitForElement(getAssignmentAction_DeleteAssignment());
				getAssignmentAction_DeleteAssignment().waitAndClick(3);
				bc.getYesBtn().waitAndClick(10);
				bc.CloseSuccessMsgpopup();
			}
			// }
			else {
				if (getAssgnPaginationNextButton().isDisplayed()) {
					System.out.println("next btn enabled.");
					driver.scrollingToBottomofAPage();
					getAssgnPaginationNextButton().waitAndClick(30);
					status = true;
				} else {
					status = false;
					System.out.println("next btn not enabled.");
				}
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method verifies the live sequence
	 */
	public void VerifyLiveSequence() {
		List<String> ElementNamesInOrder = Arrays.asList("Email Threads", "Family Members", "Near Duplicate Docs",
				"DocID");
		driver.waitForPageToBeReady();
		List<WebElement> LiveSequenceList = getLiveSequenceElements().FindWebElements();
		bc.stepInfo("The live sequence order is");
		if (LiveSequenceList.size() == 4) {
			for (int i = 0; i < LiveSequenceList.size(); i++) {
				String s = LiveSequenceList.get(i).getText().trim();
				assertion.assertTrue(ElementNamesInOrder.get(i).contentEquals(s));
				bc.stepInfo(ElementNamesInOrder.get(i));
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description Verify Draw Pool Toggle is ON and Draw pool count is "100" by
	 *              default.
	 */
	public void VerifyDrawPoolToggleEnabled() {
		driver.scrollingToElementofAPage(getAssgnGrp_Create_DrawPooltoggle());
		String s = getAssgnGrp_Create_DrawPooltoggle().GetAttribute("Class");
		assertion.assertTrue("true".contentEquals(s));
		assertion.assertEquals(getAssgnGrp_Create_DrawPoolCount().GetAttribute("Value"), "100");
		assertion.assertAll();
		bc.passedStep("Draw Pool Toggle is ON and Max Draw Count as 100 is displayed");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method verifies the draw pool toggle is disabled
	 */
	public void VerifyDrawPoolToggleDisabled() {
		String s = getAssgnGrp_Create_DrawPooltoggle().GetAttribute("Class");
		bc.stepInfo(s);
		assertion.assertTrue("true".contentEquals(s));
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method verify whether all toggles under Presentation
	 *              Control Block are Enabled and Disabled by default.
	 */
	public void verifyPresentationControlTogglesEnabledDisabled() {
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		String[] elementNamesEnabled = { "Display Mini DocList", "Allow reviewers to pop out panels",
				"Allow reviewers to folder documents", "Allow reviewers to edit Reviewer Remarks",
				"Show Default View Tab", "Enable Highlighting", "Allow reviewers to override Optimized Sort",
				"Allow reviewers to print docs to PDF", "Allow reviewers to download natives",
				"Allow access to full DocList", "Display Analytics Panel",
				"Display the Email Thread Map Tab of Analytics Panel", "Display the Near Dupes Tab of Analytics Panel",
				"Display Folders Tab", "Allow access to Coding Stamps", "Display Assignment Progress Bar",
				"Allow reviewers to see productions/images", "Allow reviewers to apply redactions",
				"Display the Family Members Tab of Analytics Panel",
				"Display the Conceptually Similar Tab of Analytics Panel", "Allow presentation of Metadata Panel" };

		String[] elementNamesDisabled = { "Display Document History Tab", "Allow users to save without completing",
				"Complete When Coding Stamp Applied" };
		// verify default toggles enabled
		for (int D = 0; D < elementNamesEnabled.length; D++) {
			driver.waitForPageToBeReady();
			String s = getPresentationControlToggles(elementNamesEnabled[D]).GetAttribute("Class");
			assertion.assertTrue("true".contentEquals(s));
		}
		// verify default toggles disabled
		for (int D = 0; D < elementNamesDisabled.length; D++) {
			String s = getPresentationControlToggles(elementNamesDisabled[D]).GetAttribute("Class");
			assertion.assertTrue("false".contentEquals(s));
		}
		assertion.assertAll();
		bc.passedStep(
				"All toggles from 'CONTROL THE PRESENTATION OF DOCVIEW FOR REVIEWERS WHILE IN THIS ASSIGNMENT' except below is ON by default:"
						+ "1. Display Document History Tab" + "2. Allow users to save without completing"
						+ "3. Complete When Coding Stamp Applied");
	}

	/**
	 * @author : Gopinath
	 * @Description : Method for navigating to assignments page.
	 */
	public void navigateToAssignmentsPage() {
		try {
			driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception while navigating to assignments page " + e.getMessage());
		}
	}

	/**
	 * @author Steffy
	 * @description this method will the complete docs to the specific user.
	 * @param user Name of the user for whom the docs needs to be completed
	 */
	public void completeDocs(String user) {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitTime(2);
		getAssgn_ManageRev_selectReviewer(user).waitAndClick(20);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
		getAssgn_ManageRev_Action().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_CompleteAllDocs());
		getAssgn_ManageRev_Action_CompleteAllDocs().waitAndClick(10);
		bc.waitTillElemetToBeClickable(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Documents successfully completed for user.");
		bc.CloseSuccessMsgpopup();
		bc.passedStep("Documents are completed successfully for the " + user);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @description This method is used to verify the allow reviewers to code
	 *              enabled within assignment
	 */
	public void allowReviewer() {
		SoftAssert softAssertion = new SoftAssert();
		boolean flag;
		bc.waitForElement(getAllowReviewerCode());
		flag = getAllowReviewerCode().GetAttribute("class").contains("true");
		driver.waitForPageToBeReady();
		if (flag) {
			softAssertion.assertTrue(true);
			bc.passedStep("Enabled 'Allow reviewers to code docs outside reviewer's batch (but within assignment) '");

		} else {
			getAllowReviewerCode().waitAndClick(5);
			flag = getAllowReviewerCode().GetAttribute("class").contains("true");
			driver.waitForPageToBeReady();
			if (flag) {
				softAssertion.assertTrue(true);
				bc.passedStep(
						"Enabled 'Allow reviewers to code docs outside reviewer's batch (but within assignment) '");

			}
		}
	}

	/**
	 * @author Indium-Mohan date: 20/12/2021 Modified date: NA
	 * @description : Toggle disable Code Outside Reviewer's Batch
	 */

	public void toggleDisableCodeOutsideReviewersBatch() {
		// permissions
		// Toggle disable for DisableCodeOutsideReviewersBatch
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		String analyticalPanelFlag = getAssgn_ToggleForAllowCodingOutsideReviwerBatch().GetAttribute("class");

		if (analyticalPanelFlag.contains("true")) {
			getAssgn_ToggleForAllowCodingOutsideReviwerBatch().Click();
		}

		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Indium-Mohan date: 20/12/2021 Modified date: NA
	 * @description Toggle disable Code Outside Reviewer's Batch
	 */

	public void toggleEnableSaveWithoutCompletion() {
		// permissions
		// Toggle disable for DisableCodeOutsideReviewersBatch
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		String analyticalPanelFlag = getAssgn_ToggleForAllowSaveWithoutCompletion().GetAttribute("class");

		if (analyticalPanelFlag.contains("false")) {
			getAssgn_ToggleForAllowSaveWithoutCompletion().Click();
		}

		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param comment
	 * @description This method edit the coding form
	 */
	public void editCodingForm(String comment) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		bc.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		bc.waitTillElemetToBeClickable(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().Clear();
		getDocument_CommentsTextBox().SendKeys(comment);
	}

	/**
	 * @author Indium-Baskar
	 * @description This method verifies the toggle color
	 */
	public void VerifyToggleColour() {
		SoftAssert assertion = new SoftAssert();
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(15);
		bc.waitForElement(getAssignmentAction_NewAssignment());
		getAssignmentAction_NewAssignment().waitAndClick(5);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		String flagFalse = getAssgn_CodingStampAplliedToggle().GetAttribute("class");
		if (flagFalse.contains("false")) {
			String colourRed = getAssgn_CodingStampAplliedToggle().GetCssValue("background-color");
			colourRed = bc.rgbTohexaConvertor(colourRed);
			System.out.println(colourRed);
			assertion.assertEquals(colourRed, "#E54036");
		}
		getAssgn_CodingStampAplliedToggle().waitAndClick(5);
		driver.waitForPageToBeReady();
		String colourGreen = getAssgn_CodingStampAplliedToggle().GetCssValue("background-color");
		colourGreen = bc.rgbTohexaConvertor(colourGreen);
		System.out.println(colourGreen);
		assertion.assertEquals(colourGreen, "#A9C981");
		assertion.assertAll();
	}

	/**
	 * @author gopinath.srinivasan
	 * @Description : Method for creating new new bulk assignment with enabling
	 *              persistant hit check box,
	 * @param assignmentName : assignmentName is String value that name of
	 *                       assignment name need to create.
	 * @param codingForm     : codingForm is String value that name of coding form.
	 */
	public void assignmentCreationWithPersistantHitDocList(String assignmentName, String codingForm) {
		try {
			driver.scrollPageToTop();
			DocListPage docList = new DocListPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return docList.getDocList_actionButton().Visible();
				}
			}), Input.wait60);
			docList.getDocList_actionButton().isElementAvailable(15);
			docList.getDocList_actionButton().waitAndClick(10);
			bc.waitTime(3);
			docList.getDocList_action_BulkAssignButton().isElementAvailable(10);
			docList.getDocList_action_BulkAssignButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			UtilityLog.info("performing bulk assign");
			driver.waitForPageToBeReady();
			bc.waitForElement(getAssgn_NewAssignmnet());
			getAssgn_NewAssignmnet().isElementAvailable(15);
			bc.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
			getAssgn_NewAssignmnet().waitAndClick(5);
			bc.waitForElement(getbulkassgnpopup());
			getbulkassgnpopup().isElementAvailable(10);
			assertion.assertTrue(getbulkassgnpopup().isDisplayed());
			try {
				bc.waitForElement(getContinueBulkAssign());
				getContinueBulkAssign().isElementAvailable(15);
				bc.waitTillElemetToBeClickable(getContinueBulkAssign());
				getContinueBulkAssign().waitAndClick(20);
			} catch (Exception e) {
				bc.waitForElement(getContinueBulkAssign());
				getContinueBulkAssign().isElementAvailable(15);
				bc.waitTillElemetToBeClickable(getContinueBulkAssign());
				getContinueBulkAssign().waitAndClick(20);
			}
			bc.waitForElement(getAssgn_TotalCount());
			getAssgn_TotalCount().isElementAvailable(10);
			bc.waitForElement(getFinalizeButton());
			bc.waitTillElemetToBeClickable(getFinalizeButton());
			getFinalizeButton().isElementAvailable(10);
			getFinalizeButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			try {
				bc.waitForElement(getAssignmentName());
				getAssignmentName().isElementAvailable(15);
				getAssignmentName().SendKeys(assignmentName);
			} catch (Exception e) {
				getAssignmentName().isElementAvailable(15);
				bc.waitForElement(getAssignmentName());
				getAssignmentName().Clear();
				getAssignmentName().SendKeys(assignmentName);
			}
			getParentAssignmentGroupName().isElementAvailable(10);
			getParentAssignmentGroupName().isDisplayed();
			bc.waitForElement(getSelectedClassification());
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			SelectCodingform(codingForm);
			bc.waitForElement(getAssignmentSaveButton());
			bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
			getAssignmentSaveButton().waitAndClick(5);
			try {
				if (getAssignmentErrorText().isElementAvailable(5)) {
					driver.waitForPageToBeReady();
					bc.waitForElement(getAssignmentName());
					getAssignmentName().SendKeys(assignmentName);
					bc.waitForElement(getAssignmentSaveButton());
					getAssignmentSaveButton().waitAndClick(5);
				}
			} catch (org.openqa.selenium.NoSuchElementException e) {
				e.printStackTrace();
			}
			System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
			UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
			bc.waitForElement(getNumberOfAssignmentsToBeShown());
			getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
			driver.scrollingToBottomofAPage();
			bc.waitForElement(getAssgn_Pagination());
			int count = ((getAssgnPaginationCount().size()) - 2);
			for (int i = 0; i < count; i++) {
				driver.waitForPageToBeReady();
				Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
				if (status == true) {
					driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
					getSelectAssignment(assignmentName).waitAndClick(5);
					driver.scrollPageToTop();
					getAssignmentActionDropdown().isElementAvailable(10);
					bc.waitTime(1);
					bc.waitForElement(getAssignmentActionDropdown());
					getAssignmentActionDropdown().Click();
					bc.stepInfo("Expected assignment found in the page " + i);
					break;
				} else {
					driver.scrollingToBottomofAPage();
					bc.waitForElement(getAssgnPaginationNextButton());
					getAssgnPaginationNextButton().Click();
					bc.stepInfo("Expected assignment not found in the page " + i);
				}
			}
			getAssignmentAction_EditAssignment().isElementAvailable(15);
			bc.waitForElement(getAssignmentAction_EditAssignment());
			bc.waitTillElemetToBeClickable(getAssignmentAction_EditAssignment());
			getAssignmentAction_EditAssignment().waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occured while creating new new bulk assignment with enabling persistant hit check box"
							+ e.getLocalizedMessage());
		}

	}

	/**
	 * @author Gopinath Description : this methoad create an assignment from bulk
	 *         assign and allow native download if not selected
	 * @param assignmentName : assignmentName is name of assignment need to create.
	 * @param codingForm     : codingForm is String value that name of coding form.
	 */
	public void createAssignmentWithNativeDownload(String assignmentName, String codingForm) {
		try {
			bc.waitForElement(getAssgn_NewAssignmnet());
			bc.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
			getAssgn_NewAssignmnet().waitAndClick(5);
			bc.waitForElement(getbulkassgnpopup());
			assertion.assertTrue(getbulkassgnpopup().Displayed());
			try {
				bc.waitForElement(getContinueBulkAssign());
				bc.waitTillElemetToBeClickable(getContinueBulkAssign());
				getContinueBulkAssign().waitAndClick(30);
			} catch (Exception e) {
				bc.waitForElement(getContinueBulkAssign());
				bc.waitTillElemetToBeClickable(getContinueBulkAssign());
				getContinueBulkAssign().waitAndClick(30);
			}
			bc.waitForElement(getAssgn_TotalCount());
			bc.waitForElement(getFinalizeButton());
			bc.waitTillElemetToBeClickable(getFinalizeButton());
			getFinalizeButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			try {
				bc.waitForElement(getAssignmentName());
				getAssignmentName().SendKeys(assignmentName);
			} catch (Exception e) {
				bc.waitForElement(getAssignmentName());
				getAssignmentName().Clear();
				getAssignmentName().SendKeys(assignmentName);
			}
			getParentAssignmentGroupName().Displayed();
			bc.waitForElement(getSelectedClassification());
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			SelectCodingform(codingForm);
			driver.scrollingToBottomofAPage();
			getAssgn_DownloadNativesToggle().ScrollTo();
			if (getAssign_DownloadNativesToggleON().isElementAvailable(5) == false) {
				getAssgn_DownloadNativesToggle().waitAndClick(5);
			}
			bc.waitForElement(getAssign_DownloadNativesToggleON());
			if (getAssign_DownloadNativesToggleON().isElementAvailable(5) == false) {
				bc.failedStep("unable to turn on native download button");
			}
			driver.scrollPageToTop();
			getAssignmentSaveButton().isElementAvailable(10);
			bc.waitForElement(getAssignmentSaveButton());
			bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
			getAssignmentSaveButton().waitAndClick(5);
			if (getAssignmentErrorText().isElementAvailable(1)) {
				try {
					if (getAssignmentErrorText().isDisplayed()) {
						driver.waitForPageToBeReady();
						bc.waitForElement(getAssignmentName());
						getAssignmentName().SendKeys(assignmentName);
						bc.waitForElement(getAssignmentSaveButton());
						getAssignmentSaveButton().waitAndClick(5);
					}
				} catch (org.openqa.selenium.NoSuchElementException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
			UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occured while creating an assignment from bulk assign and allow native download if not selected"
							+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath Description : thsi methoad will create an assignment by
	 *         disabling production/images toggle
	 * @param assignmentName
	 * @param codingForm
	 */
	public void createAssignmentWithImagesViewDisabled(String assignmentName, String codingForm) {
		try {
			bc.waitForElement(getAssgn_NewAssignmnet());
			bc.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
			getAssgn_NewAssignmnet().waitAndClick(5);
			bc.waitForElement(getbulkassgnpopup());
			assertion.assertTrue(getbulkassgnpopup().Displayed());
			try {
				bc.waitForElement(getContinueBulkAssign());
				bc.waitTillElemetToBeClickable(getContinueBulkAssign());
				getContinueBulkAssign().waitAndClick(30);
			} catch (Exception e) {
				bc.waitForElement(getContinueBulkAssign());
				bc.waitTillElemetToBeClickable(getContinueBulkAssign());
				getContinueBulkAssign().waitAndClick(30);
			}
			bc.waitForElement(getAssgn_TotalCount());
			bc.waitForElement(getFinalizeButton());
			bc.waitTillElemetToBeClickable(getFinalizeButton());
			getFinalizeButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			getAssign_ViewImagesToggle().ScrollTo();
			bc.waitTime(3);
			if (getAssign_ViewImagesToggleOn().isElementAvailable(5)) {
				getAssign_ViewImagesToggle().waitAndClick(5);
			}
			if (getAssign_ViewImagesToggleOn().isElementAvailable(5) == true) {
				bc.failedStep("unable to turn on Productions/images button");
			}
			driver.scrollPageToTop();
			try {
				bc.waitForElement(getAssignmentName());
				getAssignmentName().SendKeys(assignmentName);
			} catch (Exception e) {
				bc.waitForElement(getAssignmentName());
				getAssignmentName().Clear();
				getAssignmentName().SendKeys(assignmentName);
			}
			getParentAssignmentGroupName().Displayed();
			bc.waitForElement(getSelectedClassification());
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			bc.waitForElement(getAssignmentCodingFormDropDown());
			getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);

			bc.waitForElement(getAssignmentSaveButton());
			bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
			getAssignmentSaveButton().waitAndClick(5);
			if (getAssignmentErrorText().isElementAvailable(1)) {
				try {
					if (getAssignmentErrorText().isDisplayed()) {
						driver.waitForPageToBeReady();
						bc.waitForElement(getAssignmentName());
						getAssignmentName().SendKeys(assignmentName);
						bc.waitForElement(getAssignmentSaveButton());
						getAssignmentSaveButton().waitAndClick(5);
					}
				} catch (org.openqa.selenium.NoSuchElementException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
			UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while creating an assignment by disabling production/images toggle"
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param ele
	 * @param EnableorDisable
	 * @param name
	 * @description boolean value should be true if we want to enable the toggle and
	 *              false if we want to disable
	 */
	public void toggleEnable_Disable(Element ele, boolean EnableorDisable, String name) {
		boolean status = false;
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		bc.waitForElement(ele);
		if (ele.GetAttribute("class").contentEquals("true")) {
			status = true;
		} else {
			bc.stepInfo(name + " Toggle is not enabled.");
		}
		if (EnableorDisable) {
			if (status) {
				bc.stepInfo(name + " Toggle is already enabled .");
			} else {
				ele.waitAndClick(5);
				bc.stepInfo(name + " Toggle is  enabled now .");
			}

		} else {
			if (status) {
				ele.waitAndClick(5);
				bc.stepInfo(name + " Toggle is  enabled so disabled the toggle now .");
			} else {
				bc.stepInfo(name + " Toggle is  disabled already.");
			}
		}
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
	}

	/**
	 * @author Gopinath
	 * @description this mehtod will create new assignment with allow user to save
	 *              without complete
	 * @param assignmentName
	 * @param codingForm
	 */
	public void createAssignmentWithAllowUserToSave(String assignmentName, String codingForm) {
		bc.waitForElement(getAssgn_NewAssignmnet());
		bc.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
		getAssgn_NewAssignmnet().waitAndClick(5);
		bc.waitForElement(getbulkassgnpopup());
		assertion.assertTrue(getbulkassgnpopup().Displayed());
		try {
			bc.waitForElement(getContinueBulkAssign());
			bc.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(30);
		} catch (Exception e) {
			bc.waitForElement(getContinueBulkAssign());
			bc.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(30);
		}
		bc.waitForElement(getAssgn_TotalCount());
		bc.waitForElement(getFinalizeButton());
		bc.waitTillElemetToBeClickable(getFinalizeButton());
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		getAssign_AllowUserToSaveToggle().ScrollTo();
		bc.waitTime(3);
		bc.waitForElement(getAssign_AllowUserToSaveToggle());
		getAssign_AllowUserToSaveToggle().waitAndClick(5);

		if (!getAssign_AllowUserToSaveToggle().isElementAvailable(5)) {
			getAssign_ViewImagesToggle().waitAndClick(5);
		}
		if (!getAssign_ViewImagesToggleOn().isElementAvailable(5)) {
			bc.failedStep("unable to turn on Allow users to Save without completing");
		}
		driver.scrollPageToTop();
		try {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().SendKeys(assignmentName);
		} catch (Exception e) {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().Clear();
			getAssignmentName().SendKeys(assignmentName);
		}
		getParentAssignmentGroupName().Displayed();
		bc.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);

		bc.waitForElement(getAssignmentSaveButton());
		bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		if (getAssignmentErrorText().isElementAvailable(1)) {
			try {
				if (getAssignmentErrorText().isDisplayed()) {
					driver.waitForPageToBeReady();
					bc.waitForElement(getAssignmentName());
					getAssignmentName().SendKeys(assignmentName);
					bc.waitForElement(getAssignmentSaveButton());
					getAssignmentSaveButton().waitAndClick(5);
				}
			} catch (org.openqa.selenium.NoSuchElementException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
	}

	/**
	 * @author Indium-Mohan date: 20/12/2021 Modified date: NA Description: Toggle
	 *         Disable Show Default View Tab
	 */

	public void toggleDisableShowDefaultViewTab() {
		// permissions
		// Toggle disable for DisableCodeOutsideReviewersBatch
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		String analyticalPanelFlag = getAssgn_ShowDefaultViewTabToggleButton().GetAttribute("class");

		if (analyticalPanelFlag.contains("true")) {
			getAssgn_ShowDefaultViewTabToggleButton().Click();
		}

		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Gopinath
	 * @description : Method to verify added keyword is checked.
	 */
	public void verifyAddedKeywordsChecked() {
		try {
			driver.waitForPageToBeReady();
			getAssgn_Keywordsbutton().ScrollTo();
			getAssgn_Keywordsbutton().isElementAvailable(10);
			getAssgn_Keywordsbutton().waitAndClick(10);
			bc.waitForElement(getAssgn_Keywordspopup());
			for (int j = 1; j <= getAllKeywordsList().size(); j++) {
				try {
					assertion.assertEquals(getKeywordFromList(j).GetAttribute("checked"), "true");
					assertion.assertAll();
				} catch (Exception e) {
					bc.failedStep("Keyword is not checked");
				}
			}
			bc.passedStep("Keyword is checked");
			driver.waitForPageToBeReady();
			getAssgn_Keywordokbutton().ScrollTo();
			getAssgn_Keywordokbutton().isElementAvailable(10);
			getAssgn_Keywordokbutton().Click();
			bc.waitTime(1);
			if (getRedistributeYesPopup().isDisplayed()) {
				getRedistributeYesPopup().Click();
			}
			driver.scrollPageToTop();
		} catch (Exception e) {
			bc.failedStep("Failed to validate keywords in assignment page");
		}
	}

	/**
	 * Indium-Vijaya.Rani
	 */
	public void VerifyUnCompleteDoc(String assignmentName) {
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		final int Bgcount = bc.initialBgCount();
		driver.waitForPageToBeReady();
		docViewRedact.getManageTab().waitAndClick(10);
		bc.waitForElement(docViewRedact.getAssignDropDown());
		docViewRedact.getAssignDropDown().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		System.out.println("Docs assigned to  " + assignmentName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfAssignmentsToBeShown().Visible();
			}
		}), Input.wait60);

		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.scrollingToBottomofAPage();
		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgnPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				getSelectAssignment(assignmentName).waitAndClick(3);
				;
				driver.scrollPageToTop();
				getAssignmentActionDropdown().waitAndClick(3);
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}

		driver.waitForPageToBeReady();
		bc.waitForElement(getAssgn_ManageRev_Action_UnCompleteAllDocs());
		getAssgn_ManageRev_Action_UnCompleteAllDocs().waitAndClick(10);

		bc.waitForElement(getUnComplePopOutYes());
		getUnComplePopOutYes().waitAndClick(10);
		bc.VerifySuccessMessage("All Document successfully un-completed");
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Jayanthi
	 * @Description: Add 4 reviewers and distributed docs to
	 *               them(Reviewer/RMU/PA/DA).
	 */
	public void add4ReviewerAndDistribute() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssig().waitAndClick(5);
		driver.scrollingToElementofAPage(getSelectUserToAssigReviewerManager());
		getSelectUserToAssigReviewerManager().waitAndClick(5);
		driver.scrollingToElementofAPage(getSelectUserToAssignPA());
		getSelectUserToAssignPA().waitAndClick(5);
		getSelectDAUserToAssign().ScrollTo();
		getSelectDAUserToAssign().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(getSelectUserInDistributeTabsReviewerManager());
		getSelectUserInDistributeTabsReviewerManager().waitAndClick(5);
		getSelectUserInDistributeTabsPA().waitAndClick(5);
		getSelect2ndUserInDistributeTab().waitAndClick(5);
		getSelectUserInDistributeTabsDA().waitAndClick(5);
//		bc.CloseSuccessMsgpopup();
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo("Documents are distributed to four reviewers successfully");

	}

	/**
	 * @author Steffy
	 * @description this method will the unassigned docs to the specific user.
	 * @param user Name of the user for whom the docs needs to be unassigned
	 */
	public void UnassignedUser(String user) {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		getAssgn_ManageRev_selectReviewer(user).waitAndClick(20);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
		getAssgn_ManageRev_Action().waitAndClick(10);
		bc.waitForElement(getAssgn_ManageRev_Action_Unassignuser());
		getAssgn_ManageRev_Action_Unassignuser().waitAndClick(10);
		bc.waitForElement(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(10);
		bc.VerifySuccessMessage("User successfully unassigned from the Assignment");
		bc.stepInfo("Removed all the assigned document from the " + user);
		bc.passedStep("User successfully unassigned from the Assignment" + user);
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Indium-Baskar date: 10/7/2021 Modified date: NA
	 * @Description: Add three reviewers and distributed docs to them
	 */
	public void addReviewerAndDistributeDaPa() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssig().waitAndClick(5);
		driver.scrollingToElementofAPage(getSelectUserToAssignDA());
		getSelectUserToAssignDA().waitAndClick(5);
		driver.scrollingToElementofAPage(getSelectUserToAssignPA());
		getSelectUserToAssignPA().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(getSelectUserInDistributeDA());
		getSelectUserInDistributeDA().waitAndClick(5);
		getSelectUserInDistributeTabsPA().waitAndClick(5);
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo("Documents are distributed to three reviewers successfully");

	}

	/**
	 * @author Vijaya.Rani date: 19/01/2022 Modified date: NA Description: Toggle
	 *         Disable Analytical panel Batch
	 */

	public void toggleEnableAnalyticalPanelSaveWithoutCompletion() {
		// permissions
		// Toggle disable for DisableCodeOutsideReviewersBatch
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		String analyticalPanelFlag = getAssgn_ToggleForAllowSaveWithoutCompletion().GetAttribute("class");

		if (analyticalPanelFlag.contains("false")) {
			getAssgn_ToggleForAllowSaveWithoutCompletion().Click();
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_AnalyticsPanelToggle().Visible();
			}
		}), Input.wait60);

		String analyticalPanelFlag1 = getAssgn_AnalyticsPanelToggle().GetAttribute("class");

		if (!analyticalPanelFlag1.contains("False")) {
			getAssgn_AnalyticsPanelToggle().Click();
		}

		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assginmentName
	 * @throws InterruptedException
	 */
	public void selectAssignmentfromRMUDashborad(String assginmentName) throws InterruptedException {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitForElement(getRMUDashBoardBtn());
		getRMUDashBoardBtn().waitAndClick(10);
		bc.waitForElement(RMUDashBoardAssgn(assginmentName));
		RMUDashBoardAssgn(assginmentName).ScrollTo();
		RMUDashBoardAssgn(assginmentName).waitAndClick(10);
	}

	public String getDistibuteDocsCount(String userNAme) {
		getAssignment_ManageReviewersTab().ScrollTo();
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(15);
		bc.waitTime(1);
		String actualDocs = getDistributedDocs(userNAme).getText();
		System.out.println(actualDocs);
		return actualDocs;

	}

	/**
	 * @Authro Jeevitha ( Modified by Jayanthi)
	 * @Description : Can Enable or Disable Toggles
	 * @param enableToggle
	 * @param DisableToggle
	 * @param toggleEle
	 * @param ELementName
	 * @param save
	 */
	public void toggleEnableOrDisableOfAssignPage(boolean enableToggle, boolean DisableToggle, Element toggleEle,
			String ELementName, boolean save) {

		if (enableToggle) {
			bc.waitForElement(toggleEle);
			String keepFMToggleStatus = toggleEle.GetAttribute("class");
			if (keepFMToggleStatus.equals("true")) {
				System.out.println(ELementName + " : Toggle is Already Enabled ");
				bc.stepInfo(ELementName + " : Toggle is Already Enabled ");
				if (ELementName.equalsIgnoreCase("Draw From Pool")) {
					bc.waitForElement(getAssgnGrp_Create_DrawPoolCount());
					getAssgnGrp_Create_DrawPoolCount().SendKeys(Integer.toString(20));
				}

			} else if (keepFMToggleStatus.equals(Input.TextEmpty) || keepFMToggleStatus.equalsIgnoreCase("false")) {
				toggleEle.waitAndClick(5);
				if (ELementName.equalsIgnoreCase("Draw From Pool")) {
					getAssgnGrp_Create_DrawPoolCount().SendKeys("100");
				}
				System.out.println(ELementName + " : Toggle is Now Enabled ");
				bc.stepInfo(ELementName + " : Toggle is Now Enabled ");
			}
		}

		if (DisableToggle) {
			bc.waitForElement(toggleEle);
			String keepFMToggleStatus = toggleEle.GetAttribute("class");
			if (keepFMToggleStatus.equals("true")) {
				toggleEle.waitAndClick(5);
				System.out.println(ELementName + " : Toggle is Now Disabled ");
				bc.stepInfo(ELementName + " : Toggle is Now Disabled ");
			} else if (keepFMToggleStatus.equals(Input.TextEmpty) || keepFMToggleStatus.equalsIgnoreCase("false")) {
				System.out.println(ELementName + " : Toggle is Already Disabled ");
				bc.stepInfo(ELementName + " : Toggle is Already Disabled ");
			}
		}

		if (save) {
			bc.waitForElement(getAssignmentSaveButton());
			getAssignmentSaveButton().waitAndClick(5);
			if (bc.getSuccessMsgHeader().isElementAvailable(3)) {
				bc.VerifySuccessMessage("Assignment updated successfully");
				bc.CloseSuccessMsgpopup();
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param Username
	 * @return
	 * @throws InterruptedException
	 */
	public String manageRevTabToViewInDocView(String Username) throws InterruptedException {
		System.out.println(Username);
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		bc.waitTime(1);
		String actualDocs = getDistributedDocs(Username).getText();
		System.out.println(actualDocs);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_selectReviewer(Username));
		getAssgn_ManageRev_selectReviewer(Username).ScrollTo();
		getAssgn_ManageRev_selectReviewer(Username).waitAndClick(10);
		getAssgn_ManageRev_Action().waitAndClick(20);
		bc.waitForElement(getAssgn_ManageRev_Action_ViewDocview());
		getAssgn_ManageRev_Action_ViewDocview().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.getYesBtn().ScrollTo();
		bc.getYesBtn().waitAndClick(10);
		return actualDocs;

	}

	/**
	 * @author Arunkumar
	 * @throws InterruptedException
	 * @description this method will unmap/unassign the keywords which assigned to
	 *              the document.
	 * 
	 */
	public void unmappingKeywordsFromAssignment(String assignmentName) throws InterruptedException {
		bc = new BaseClass(driver);
		keywordPage = new KeywordPage(driver);
		editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		getAssgn_Keywordsbutton().ScrollTo();
		getAssgn_Keywordsbutton().isElementAvailable(10);
		getAssgn_Keywordsbutton().waitAndClick(10);
		bc.waitForElement(getAssgn_Keywordspopup());
		getAvailableKeywordCheckboxes();
		List<WebElement> allvalues = getAvailableKeywordCheckboxes().FindWebElements();
		List<String> expkey = new ArrayList<String>();

		for (int j = 0; j <= allvalues.size() - 1; j++) {

			allvalues.get(j).click();
		}
		driver.waitForPageToBeReady();
		getAssgn_Keywordokbutton().ScrollTo();
		getAssgn_Keywordokbutton().isElementAvailable(10);
		getAssgn_Keywordokbutton().Click();
		keywordPage.getYesButton().Click();
		driver.waitForPageToBeReady();
		bc.passedStep("Keywords unmapped from the assignment successfully");
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description this method complete all documents.
	 * 
	 */
	public void completeAllDocsByReviewer(String assignmentName) throws InterruptedException {
		bc.waitForElement(dashBoardPageTitle());
		assgnInDashBoardPg(assignmentName).waitAndClick(2);
		bc.waitForElement(completeBtn());
		getTotalRecords().waitAndClick(15);
		String recordsLabel = getTotalRecords().getText();
		System.out.println("recordslabel " + recordsLabel);
		String[] totalRecords = recordsLabel.split(" ");
		String count = totalRecords[1];
		System.out.println("count " + count);
		int records = Integer.parseInt(count);
		for (int i = 0; i < records; i++) {
			driver.waitForPageToBeReady();
			editCodingForm(Input.searchString1);
			bc.waitForElement(completeBtn());
			completeBtn().waitAndClick(5);
		}
		bc.stepInfo("All the docs are completed");
	}

	public void mappingKeywordToAssignment(String keywordName) throws InterruptedException {
		bc = new BaseClass(driver);
		driver.waitForPageToBeReady();
		getAssgn_Keywordsbutton().ScrollTo();
		getAssgn_Keywordsbutton().isElementAvailable(10);
		getAssgn_Keywordsbutton().waitAndClick(10);
		bc.waitForElement(getAssgn_Keywordspopup());

		List<WebElement> allvalues = getAvailableKeywordCheckboxes().FindWebElements();
		List<String> names = bc.availableListofElements(getKeywordNames());
		System.out.println(names);
		for (int i = 0; i <= names.size() - 1; i++) {
			if (names.get(i).contains(keywordName)) {
				allvalues.get(i).click();
				System.out.println("done");
				break;
			}

		}
		driver.waitForPageToBeReady();
		getAssgn_Keywordokbutton().ScrollTo();
		getAssgn_Keywordokbutton().isElementAvailable(10);
		getAssgn_Keywordokbutton().Click();
		keywordPage.getYesButton().Click();
		bc.passedStep("Required keyword mapped to the assignment successfully");

	}

	/**
	 * @author Gopinath
	 * @description thsi methoad will select the required keyword by its name and
	 *              ignore if it is already selcted from list
	 * @param keywordName
	 */
	public void addKeywordToAssignment(String keywordName) {
		driver.waitForPageToBeReady();
		if (getAssgn_Keywordsbutton().isElementAvailable(2)) {
			getAssgn_Keywordsbutton().ScrollTo();
			getAssgn_Keywordsbutton().isElementAvailable(10);
			getAssgn_Keywordsbutton().waitAndClick(10);
		}
		bc.waitForElement(getAssgn_Keywordspopup());
		bc.waitForElement(getKeywordCheckBox(keywordName));
		if (getKeywordCheckBox(keywordName).isElementAvailable(5)) {
			bc.passedStep("selected keyword group found");
		} else {
			bc.failedStep("selected keyword not found");
		}
		if (getKeywordCheckBox(keywordName).getWebElement().isSelected() == false) {
			getKeywordCheckBox(keywordName).waitAndClick(5);

		} else {
			bc.stepInfo("keyword group is already selected");

		}
		driver.waitForPageToBeReady();
		getAssgn_Keywordokbutton().ScrollTo();
		getAssgn_Keywordokbutton().isElementAvailable(10);
		getAssgn_Keywordokbutton().Click();
		bc.waitTime(1);
		if (getRedistributeYesPopup().isDisplayed()) {
			getRedistributeYesPopup().Click();
		}
		driver.scrollPageToTop();
		bc.waitForElement(getSaveBtn());

		getSaveBtn().waitAndClick(5);

	}

	/**
	 * @author Raghuram.A Date 01/02/22 Modified date: NA Modified by:NA
	 * @param userName
	 * @param iteration
	 * @param distributedCOunt
	 * @param distrCount
	 * @param completedCount
	 * @param leftToDoCOunt
	 */
	public void verifyCountMatches(String userName, int iteration, String distributedCOunt, Boolean distrCount,
			Boolean completedCount, Boolean leftToDoCOunt, Boolean additional1) {
		int totalDOcsCompleted = 0;
		int indexDisdocC = 0;
		if (distrCount) {
			// Total no.of distributed docs count verification
			bc.stepInfo("Verify DISTRIBUTED TO USER count matches with initial count");
			int indexDisdoc = bc.getIndex(getManageReviewereTabHeader(), "DISTRIBUTED TO USER") - 1;
			indexDisdocC = Integer.parseInt(getDistributedDocs(userName, Integer.toString(indexDisdoc)).getText());
			bc.digitCompareEquals(indexDisdocC, Integer.parseInt(distributedCOunt),
					"DISTRIBUTED TO USER count matches : " + indexDisdocC, "DISTRIBUTED TO USER count mis-matches");
		}

		if (completedCount) {
			// Total no.of completed docs count verification
			bc.stepInfo("Verify total completed document count matches with the completion iteration count");
			int indexTotalCompleted = bc.getIndex(getManageReviewereTabHeader(), "TOTAL COMPLETED") - 1;
			totalDOcsCompleted = Integer
					.parseInt(getDistributedDocs(userName, Integer.toString(indexTotalCompleted)).getText());
			bc.digitCompareEquals(totalDOcsCompleted, iteration,
					"Completed Document count matches : " + totalDOcsCompleted, "Wrong completed document count");
		}

		if (leftToDoCOunt) {
			// Left to DOcount verification
			bc.stepInfo(
					"Verify LEFT TO DO count is the subtraction of distributed count - total completed doument count");
			int indexLefttoDo = bc.getIndex(getManageReviewereTabHeader(), "LEFT TO DO") - 1;
			int totalLeftToDo = Integer
					.parseInt(getDistributedDocs(userName, Integer.toString(indexLefttoDo)).getText());
			bc.digitCompareEquals(totalLeftToDo, indexDisdocC - totalDOcsCompleted,
					"LEFT TO DO count matches : " + totalLeftToDo, "LEFT TO DO count is wrong");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @return
	 */
	public int docCountViewinDocView() {
		bc.waitForElement(getAssg_DocView_DocCOunt());
		String resultValue = getAssg_DocView_DocCOunt().getText();
		ArrayList<String> result = new ArrayList<String>(Arrays.asList(resultValue.split(" ")));
		int docCount = Integer.parseInt(result.get(1));
		bc.stepInfo("Doc Count reflected in doc view page");
		return docCount;
	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException
	 * @description this method will unmap/unassign the keywords which assigned to
	 *              the document.
	 * 
	 */
	public void unmappingKeywordsFromEditedAssignment(String assignmentName) throws InterruptedException {
		bc = new BaseClass(driver);
		keywordPage = new KeywordPage(driver);
		editAssignment(assignmentName);
		driver.waitForPageToBeReady();
		getAssgn_Keywordsbutton().ScrollTo();
		getAssgn_Keywordsbutton().isElementAvailable(10);
		getAssgn_Keywordsbutton().waitAndClick(10);
		bc.waitForElement(getAssgn_Keywordspopup());
		getAvailableKeywordCheckboxes();
		List<WebElement> allvalues = getAvailableKeywordCheckboxes().FindWebElements();
		List<String> expkey = new ArrayList<String>();

		for (int j = 0; j <= allvalues.size() - 1; j++) {

			allvalues.get(j).click();
		}
		driver.waitForPageToBeReady();
		getAssgn_Keywordokbutton().ScrollTo();
		getAssgn_Keywordokbutton().isElementAvailable(10);
		getAssgn_Keywordokbutton().Click();
		keywordPage.getYesButton().Click();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getAssignmentSaveButton().isElementAvailable(10);
		// getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
		getAssignmentSaveButton().waitAndClick(5);
		bc.passedStep("Keywords unmapped from the assignment successfully");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param user [user to which the completed docs need to be un completed]. Thia
	 *             method will uncomplete the completed documents for a particular
	 *             user via edit assignment page
	 */
	public void UnCompleteDocs(String user) {

		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		getAssgn_ManageRev_selectReviewer(user).waitAndClick(20);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action());
		getAssgn_ManageRev_Action().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_Action_UnCompleteAllDocs());
		getAssgn_ManageRev_Action_UnCompleteAllDocs().waitAndClick(10);
		bc.waitTillElemetToBeClickable(bc.getYesBtn());
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Documents successfully un-completed for user.");
		bc.passedStep("Documents are un-completed successfully for the " + user);

	}

	/**
	 * @author Jayanthi
	 * @param count - NO of Docs to assigned to user
	 * @description This method will distribute the given count of docs to reviewer.
	 */
	public String distributeTheGivenDocCountToReviewer(String count) throws InterruptedException {

		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		getSelect2ndUserToAssign().ScrollTo();
		bc.waitForElement(getSelect2ndUserToAssign());
		getSelect2ndUserToAssign().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(getSelect2ndUserInDistributeTab());
		getSelect2ndUserInDistributeTab().waitAndClick(5);
		getAssgn_docsToDistribute().SendKeys(count);
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo(count + " Documents are distributed to reviewer successfully");
		bc.CloseSuccessMsgpopup();
		bc.waitTillElemetToBeClickable(bc.getCloseSucessmsg());
		bc.getCloseSucessmsg().waitAndClick(10);
		bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);

		return count;
	}

	/**
	 * @author Indium-Baskar
	 * @param assignmentName passing to create assignmnet
	 * @param codingForm     Thia method will uncomplete the completed documents for
	 *                       a particular user via edit assignment page
	 */

	public int assignmentCreationFamilyCount(String assignmentName, String codingForm) {
		bc.waitForElement(getAssgn_NewAssignmnet());
		bc.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
		getAssgn_NewAssignmnet().waitAndClick(5);
		bc.waitForElement(getbulkassgnpopup());
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());
		try {
			bc.waitForElement(getContinueBulkAssign());
			bc.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(10);
		} catch (Exception e) {
			bc.waitForElement(getContinueBulkAssign());
			bc.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(10);
		}
		bc.waitForElement(getFamilyMembersCount());
		int familyCount = Integer.parseInt(getFamilyMembersCount().getText());
		bc.waitForElement(getAssgn_TotalCount());
		bc.waitForElement(getFinalizeButton());
		bc.waitTillElemetToBeClickable(getFinalizeButton());
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		try {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().SendKeys(assignmentName);
		} catch (Exception e) {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().Clear();
			getAssignmentName().SendKeys(assignmentName);
		}
		getParentAssignmentGroupName().isDisplayed();
		bc.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		bc.waitForElement(getAssignmentCodingFormDropDown());
		SelectCodingform(codingForm);
		bc.waitForElement(getAssignmentSaveButton());
		bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		try {
			if (getAssignmentErrorText().isElementAvailable(5)) {
				driver.waitForPageToBeReady();
				bc.waitForElement(getAssignmentName());
				getAssignmentName().SendKeys(assignmentName);
				bc.waitForElement(getAssignmentSaveButton());
				getAssignmentSaveButton().waitAndClick(5);
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();

		}
		return familyCount;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method return boolean value as 'true' if toggle is enabled
	 *              and 'false' if given toggle is disabled
	 * @param Element to which enabled or disabled state needs to checked
	 */
	public boolean verifyToggleEnableORDisabled(Element ele, String elementInfo) {
		String s = ele.GetAttribute("Class");
		boolean status;
		if (s.equals("true")) {
			status = true;
			bc.stepInfo(elementInfo + "Toggle is enabled*");
		} else {
			status = false;
			bc.stepInfo(elementInfo + "Toggle is disabled*");
		}
		return status;
	}

	/**
	 * @author Indium-Baskar date: 31/03/2021 Modified date: NA
	 * @Description: Add DA user and distributed docs to them
	 */
	public void assignmentDistributeToDa() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		driver.scrollingToElementofAPage(getSelectUserToAssignDA());
		getSelectUserToAssignDA().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(getSelectUserInDistributeDA());
		getSelectUserInDistributeDA().waitAndClick(5);
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo("Documents are distributed to DA user successfully");

	}

	/**
	 * @author Indium-Baskar date: 31/03/2021 Modified date: NA
	 * @Description: Add Pa user and distributed docs to them
	 */
	public void assignmentDistributeToPa() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		driver.scrollingToElementofAPage(getSelectUserToAssignPA());
		getSelectUserToAssignPA().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		getSelectUserInDistributeTabsPA().waitAndClick(5);
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo("Documents are distributed to PA user successfully");

	}

	/**
	 * @Author Jeevitha
	 * @Description : verifying error message for Assignmentname with special
	 *              character
	 * @param assignmnetName
	 */
	public void verifyErrorMSgForSpclChar(String assignmnetName) {
		String dataSet[][] = { { "<" }, { "(" }, { ")" }, { "[" }, { "]" }, { "{" }, { "}" }, { ":" }, { " ' " },
				{ "~" }, { "*" }, { "?" }, { "&" }, { "$" }, { "#" }, { "@" }, { "!" }, { "-" }, { "_" } };

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;

			String renameAssign = dataSet[i][j] + assignmnetName;

			bc.waitForElement(getAssignmentName());
			getAssignmentName().SendKeys(renameAssign);
			driver.scrollPageToTop();
			bc.waitForElement(getAssignmentSaveButton());
			getAssignmentSaveButton().waitAndClick(5);

			if (getAssignmentErrorText().isElementAvailable(5)) {
				String errorMsg = getAssignmentErrorText().getText();
				System.out.println("Error Msg " + errorMsg + "for specialchar : " + dataSet[i][j]);
				bc.passedStep("Error Msg " + errorMsg + "for specialchar : " + dataSet[i][j]);
			} else {
				bc.failedStep("Application is allowing assignment name with special character");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 24/06/2022 Modified date: NA
	 * @Description: validating assignment available or not in manage assignment
	 * @param filedValue
	 */

	public void assignmentNameValidation(String filedValue) {
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_Pagination());
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(filedValue).isDisplayed();
			if (status == true) {
				bc.passedStep("Assignment name available in manage assignmnet");
				bc.stepInfo("Assignment in the name of :" + filedValue + "");
				break;
			} else {
				driver.scrollingToBottomofAPage();
				bc.waitForElement(getAssgnPaginationNextButton());
				getAssgnPaginationNextButton().Click();
				bc.failedStep("Assignment name not found in manage assignmnet");
			}
		}
	}

	public void verifyProgressReportIsPresent() {
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		bc.stepInfo("Navigated to assignments page successfully.");
		bc.waitTillElemetToBeClickable(getAssgGrptionDropdown());
		getAssgGrptionDropdown().Click();
		bc.waitTillElemetToBeClickable(getProgressReport());
		if (getProgressReport().isDisplayed()) {
			bc.passedStep("Progress report option is present in manage assignment group actions.");
		} else {
			bc.failedStep("Progress report option is not displayed");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to select coding form in assignments page
	 * 
	 */
	public void SelectCodingform(String CFName) {
		try {
			getSelectSortCodingForm_Tab().ScrollTo();
		} catch (Exception e) {

		}
		getSelectSortCodingForm_Tab().waitAndClick(10);

		if (SelectCFPopUp_Step1().isElementAvailable(2)) {
			bc.stepInfo("Add / Remove Coding Forms in this Assignment Pop Up displayed.");
			bc.waitForElement(getSelectCF_CheckBox(CFName));
			getSelectCF_CheckBox(CFName).ScrollTo();
			getSelectCF_CheckBox(CFName).waitAndClick(5);
			bc.waitTime(1);
			getSelectCodeFormRadioBtn(CFName).waitAndClick(5);
			bc.waitTime(1);
			try {
				sortOrderNxtBtn().ScrollTo();
			} catch (Exception e) {

			}
			sortOrderNxtBtn().waitAndClick(5);
			if (getSelectedCodeForm_inSortingPopUp(CFName).isElementAvailable(2)) {
				sortCodeFormOrderSaveBtn().waitAndClick(5);
				bc.waitTime(2);
				bc.waitForElement(getSelectedCodeForminAssignPAge());
				if (getSelectedCodeForminAssignPAge().isDisplayed()) {
					String acualCfName = getSelectedCodeForminAssignPAge().getText();

					String passMSg = "Selected a coding form " + CFName
							+ " and its reflected in manage assignments page";
					String failMsg = "Selected  coding form " + CFName
							+ "  is not reflected in manage assignments page";
					bc.compareTextViaContains(acualCfName, CFName, passMSg, failMsg);

				} else {
					bc.failedStep("Step-2 Sort CodeForm Pop Up Not displayed.");
				}
			} else {
				bc.failedStep("Step-1 Select CodingForm Pop Up Not displayed.");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 30/6/2022 Modified date:30/6/2022 Modified
	 *         by:Baskar *
	 * @Description: Assignment creation
	 * @param assignmentName
	 * @param codingForm
	 */
	// Assignment creating and saving the assignment
	// After Saving From action Drop down selecting the assignment with edit
	// assignment
	// Again coming back to assignment page for editing the assignmnet

	public void assignmentCreationAsPerCf(String assignmentName, String codingForm) {
		bc.waitForElement(getAssgn_NewAssignmnet());
		bc.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
		getAssgn_NewAssignmnet().waitAndClick(5);
		bc.waitForElement(getbulkassgnpopup());
		assertion.assertTrue(getbulkassgnpopup().isDisplayed());
		try {
			bc.waitForElement(getContinueBulkAssign());
			bc.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(30);
		} catch (Exception e) {
			bc.waitForElement(getContinueBulkAssign());
			bc.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(30);
		}
		bc.waitForElement(getAssgn_TotalCount());
		bc.waitForElement(getFinalizeButton());
		bc.waitTillElemetToBeClickable(getFinalizeButton());
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		try {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().SendKeys(assignmentName);
		} catch (Exception e) {
			bc.waitForElement(getAssignmentName());
			getAssignmentName().Clear();
			getAssignmentName().SendKeys(assignmentName);
		}
		getParentAssignmentGroupName().isDisplayed();
		bc.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		SelectCodingform(codingForm);
		bc.waitForElement(getAssignmentSaveButton());
		bc.waitTillElemetToBeClickable(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		try {
			if (getAssignmentErrorText().isElementAvailable(5)) {
				driver.waitForPageToBeReady();
				bc.waitForElement(getAssignmentName());
				getAssignmentName().SendKeys(assignmentName);
				bc.waitForElement(getAssignmentSaveButton());
				getAssignmentSaveButton().waitAndClick(5);
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_Pagination());
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getSelectAssignment(assignmentName));
				getSelectAssignment(assignmentName).waitAndClick(5);
				driver.scrollPageToTop();
				bc.waitForElement(getAssignmentActionDropdown());
				getAssignmentActionDropdown().Click();
				bc.stepInfo("Expected assignment found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				bc.waitForElement(getAssgnPaginationNextButton());
				getAssgnPaginationNextButton().Click();
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}
		bc.waitForElement(getAssignmentAction_EditAssignment());
		bc.waitTillElemetToBeClickable(getAssignmentAction_EditAssignment());
		getAssignmentAction_EditAssignment().waitAndClick(5);

	}

	/**
	 * @Author Mohan Created on 04/07/2022
	 * @Description To enable the Analytics panel Toggle present in AssignPage
	 */
	public void toggleDisableForAnalyticsPanelMiniDoclistReviewerToApplyRedation() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		String analyticalPanelFlag = getAssgn_AnalyticsPanelToggle().GetAttribute("class");
		System.out.println(analyticalPanelFlag);
		String miniDocListFlag = getMiniDocListToggleButton().GetAttribute("class");
		System.out.println(miniDocListFlag);

		String applyRedactionFlag = getAssgn_ApplyRedactionToggle().GetAttribute("class");
		System.out.println(applyRedactionFlag);
		if (analyticalPanelFlag.contains("true") && miniDocListFlag.contains("true")
				&& applyRedactionFlag.contains("true")) {
			bc.waitForElement(getAssgn_AnalyticsPanelToggle());
			getAssgn_AnalyticsPanelToggle().Click();

			bc.waitForElement(getMiniDocListToggleButton());
			getMiniDocListToggleButton().Click();

			bc.waitForElement(getMiniDocListToggleButton());
			getMiniDocListToggleButton().Click();
		}
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify Assignmnets with toggle off condition is present in
	 *               Assignmnets page.
	 * @param assignmentName
	 */
	public void verifyAssignmentsPageWithOnlyAssignments(String assignmentName) {

		bc.waitForElement(getNumberOfAssignmentsToBeShown());
		getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getAssgn_Pagination());
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
			if (status == true) {
				bc.stepInfo(
						"The Assignment mentioned in Pre-requisite is display with the changes in properties in source template project and the Mini DocList,Analytics Panel and Allow reviewers to apply redactions toggles are OFF successfully");
				break;
			} else {
				driver.scrollingToBottomofAPage();
				bc.waitForElement(getAssgnPaginationNextButton());
				getAssgnPaginationNextButton().Click();
				bc.stepInfo("Expected assignment not found in the page " + i);
			}
		}

	}

	/**
	 * @author Raghuram.A
	 * @param username
	 * @param assignmentName
	 * @throws InterruptedException
	 */
	public String assignMentCreationDeletionBasedOnUser(Boolean createAssign, String username, String assignmentName,
			Boolean deleteAssignment, String expectedUserRole, Boolean additional, String additional1)
			throws InterruptedException {
		String ActualCount = null;
		if (createAssign) {
			if (username.equals(expectedUserRole)) {
				FinalizeAssignmentAfterBulkAssign();
				createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
				getAssignmentSaveButton().waitAndClick(10);
				bc.selectproject();
				bc.stepInfo("Created a assignment by assigning saved search documents from search term report page -"
						+ assignmentName);
				ActualCount = verifydocsCountInAssgnPage(assignmentName);
			}
		}

		if (deleteAssignment) {
			if (username.equals(expectedUserRole)) {
				deleteAssgnmntUsingPagination(assignmentName);
			}
		}
		return ActualCount;
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param AssignName
	 * @param codingForm
	 * @param expErrorMSg
	 */

	public void verifyErrorMSg_QuickBatchAssing(String AssignName, String codingForm, String expErrorMSg) {

		bc.waitForElement(getAssignmentName());
		getAssignmentName().SendKeys(AssignName);
		bc.stepInfo("Entered Assignment Name as -" + AssignName);
		bc.waitForElement(getAssignmentCodingFormDropDown());
		getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
		bc.stepInfo("Selected Coding form as -" + codingForm);
		getContinueBulkAssign().waitAndClick(30);
		if (getQB_AssignemntName_ErrorMSg().isElementAvailable(1)) {
			String actualMsg = getQB_AssignemntName_ErrorMSg().getText();
			bc.textCompareEquals(actualMsg, expErrorMSg,
					"ErrorAlert for assignment name with special chars Displayed as expected ",
					"ErrorAlert Displayed for assignment name with special chars is not as expected");
		} else {

			bc.failedStep(
					"After entering Assginment name in " + "quick batch assign creation,Error message not displayed ");
		}

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param reviewersListToAssign
	 */

	public void assignReviewers(String[] reviewersListToAssign) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignment_ManageReviewersTab());
		bc.waitTillElemetToBeClickable(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		bc.waitTillElemetToBeClickable(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		for (int i = 0; i < reviewersListToAssign.length; i++) {
			bc.waitForElement(getSelectUserToAssignAsReviewer(reviewersListToAssign[i]));
			getSelectUserToAssignAsReviewer(reviewersListToAssign[i]).WaitUntilPresent().ScrollTo();
			getSelectUserToAssignAsReviewer(reviewersListToAssign[i]).waitAndClick(10);
			bc.stepInfo("Selected user " + reviewersListToAssign[i] + " as reviewer .");
		}
		bc.waitForElement(getAdduserBtn());
		bc.waitTillElemetToBeClickable(getAdduserBtn());
		getAdduserBtn().waitAndClick(10);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method alters the order of live sequence
	 * @param metaData
	 * @param metaData2
	 * @throws InterruptedException
	 */
	public void dragAndDropLiveSequenceFromTopToBottom(String metaData, String metaData2) throws InterruptedException {
		System.out.println(metaData);
		driver.waitForPageToBeReady();
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 30);
		wait.until(ExpectedConditions.elementToBeClickable(liveSequenceSorts(metaData).getWebElement()));
		bc.waitForElement((liveSequenceSorts(metaData)));
		actions.clickAndHold((liveSequenceSorts(metaData)).getWebElement());
		System.out.println("Click and hold performed");
		actions.moveToElement((liveSequenceSorts(metaData2)).getWebElement(), -20, 10);
		actions.moveToElement((liveSequenceSorts(metaData2)).getWebElement(), -20, 30);
		actions.moveToElement((liveSequenceSorts(metaData2)).getWebElement(), -20, 20).build().perform();
		actions.release();
		actions.build().perform();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method verifies the error message
	 * @param element
	 * @param expectedMsg
	 */
	public void validateErrorMessage(Element element, String expectedMsg) {
		driver.waitForPageToBeReady();
		bc.waitForElement(element);
		String value = element.getText();
		if (value.equals(expectedMsg)) {
			bc.passedStep("Error message is displayed as " + value + " as expected");
		} else {
			bc.failedStep("Error message is not displayed as expected");
		}

	}

	/**
	 * @author sowndarya.velraj
	 * @description: To create Assignmnets with classification
	 * @param assignmentName,codingForm,classification
	 */
	public void createAssignmentWithClassification(String assignmentName, String classification, String codingForm)
			throws InterruptedException {
		try {
			bc.waitForElement(dashBoardPageTitle());
			bc.waitForElement(getAssignmentName());
			driver.waitForPageToBeReady();
			getAssignmentName().SendKeys(assignmentName);
			driver.waitForPageToBeReady();
			getParentAssignmentGroupName().isDisplayed();
			getSelectedClassification().selectFromDropdown().selectByVisibleText(classification);
			SelectCodingform(codingForm);

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while creating new assignment for bulk assign operation" + e.getMessage());
		}
	}

	/**
	 * @author sowndarya.velraj
	 * @Description: Add 2 reviewers and distributed docs to them(PA/DA).
	 */
	public void distributeToPAUandDAU() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		// assign to pa
		driver.scrollingToElementofAPage(getSelectUserToAssignPA());
		getSelectUserToAssignPA().waitAndClick(5);
		// assign to dau
		getSelectDAUserToAssign().ScrollTo();
		getSelectDAUserToAssign().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		// distribute docs
		getSelectUserInDistributeTabsPA().waitAndClick(5);
		getSelectUserInDistributeTabsDA().waitAndClick(5);
		bc.CloseSuccessMsgpopup();
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo("Documents are distributed to four reviewers successfully");

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify the user is present in reviewer
	 *              popup
	 * @param reviewer
	 */
	public void verifyUserInReviewerTab(String reviewer) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignment_ManageReviewersTab());
		bc.waitTillElemetToBeClickable(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		bc.waitTillElemetToBeClickable(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.stepInfo("Reviewer popup is opened");
		if (getSelectUserToAssign(reviewer).isElementAvailable(5)) {
			bc.passedStep("Expected user is displayed in reviewer popup");
		} else {
			bc.failedStep("Expected user is not displayed in reviewer popup");
		}
		bc.waitTillElemetToBeClickable(getReviewerPopupCloseBtn());
		getReviewerPopupCloseBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		if (getReviewerPopupCloseBtn().isDisplayed()) {
			bc.failedStep("Reviewer popup not closed successfully");
		} else {
			bc.passedStep("Reviewer popup is closed successfully");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param metaData
	 * @throws InterruptedException
	 */
	public void dragAndDropLiveSeqfrmAvailableCrit(String metaData) throws InterruptedException {
		System.out.println(metaData);
		driver.waitForPageToBeReady();
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 30);
		wait.until(ExpectedConditions.elementToBeClickable(getunderAvailableCriteria(metaData).getWebElement()));
		bc.waitForElement((getunderAvailableCriteria(metaData)));
		actions.clickAndHold((getunderAvailableCriteria(metaData)).getWebElement());
		System.out.println("Click and hold performed");
		actions.moveToElement((getDropElement()).getWebElement());
		actions.moveToElement((dragElement(metaData)).getWebElement(), 0, -45);
		actions.release(getDropElement().getWebElement());
		actions.release();
		actions.build().perform();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method enables all toggles
	 */
	public void enableAllToogleUnderPresentationControl() {
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		String[] elementNamesEnabled = { "Display Mini DocList", "Allow reviewers to pop out panels",
				"Allow reviewers to folder documents", "Allow reviewers to edit Reviewer Remarks",
				"Show Default View Tab", "Enable Highlighting", "Allow reviewers to override Optimized Sort",
				"Allow reviewers to print docs to PDF", "Allow reviewers to download natives",
				"Allow access to full DocList", "Display Analytics Panel",
				"Display the Email Thread Map Tab of Analytics Panel", "Display the Near Dupes Tab of Analytics Panel",
				"Display Folders Tab", "Allow access to Coding Stamps", "Display Assignment Progress Bar",
				"Allow reviewers to see productions/images", "Allow reviewers to apply redactions",
				"Display the Family Members Tab of Analytics Panel",
				"Display the Conceptually Similar Tab of Analytics Panel", "Allow presentation of Metadata Panel",
				"Display Document History Tab", "Allow users to save without completing",
				"Complete When Coding Stamp Applied",
				"Save the coding form automatically when switched to different coding form" };

		// To Enable All Toogle
		for (int D = 0; D < elementNamesEnabled.length; D++) {
			driver.waitForPageToBeReady();
			String value = getPresentationControlToggles(elementNamesEnabled[D]).GetAttribute("Class");
			if (value.equalsIgnoreCase("false")) {
				getPresentationControlToggles(elementNamesEnabled[D]).waitAndClick(5);

			}
		}

		// To Verify All Toogle is Enable or Not
		for (int i = 0; i < elementNamesEnabled.length; i++) {
			driver.waitForPageToBeReady();
			String status = getPresentationControlToggles(elementNamesEnabled[i]).GetAttribute("Class");
			if (status.equalsIgnoreCase("false")) {
				bc.failedStep(
						"All the toggles under 'CONTROL THE PRESENTATION OF DOCVIEW FOR REVIEWERS WHILE IN THIS ASSIGNMENT' are Not Enabled and Not in Green Color");
				System.out.println(
						"All the toggles under 'CONTROL THE PRESENTATION OF DOCVIEW FOR REVIEWERS WHILE IN THIS ASSIGNMENT' are Not Enabled and Not in Green Color");
			}
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param instruction
	 * @param selectAssignedMetaData
	 */
	public void configInstructionKeywordMDFieldinAssignPage(String instruction, String selectAssignedMetaData) {
		driver.scrollingToBottomofAPage();
		bc.waitForElement(configureButton());
		configureButton().waitAndClick(3);
		bc.waitForElement(instructionTextBox());
		instructionTextBox().SendKeys(instruction);
		instructionOkButton().waitAndClick(2);
		bc.stepInfo("Instruction Successfully Configured For The Assignment");
		verifyAddedKeywordsChecked();
		bc.stepInfo("KeyWords Successfully Configured For The Assignment");
		bc.waitForElement(GetSelectMetaDataBtn());
		GetSelectMetaDataBtn().waitAndClick(3);
		bc.waitForElement(getSelectAssignedMDinAssignPage(selectAssignedMetaData));
		getSelectAssignedMDinAssignPage(selectAssignedMetaData).waitAndClick(3);
		bc.waitForElement(getMetadataFielOkBtn());
		getMetadataFielOkBtn().waitAndClick(3);
		bc.stepInfo("MetaData Field Successfully Configured For The Assignment");
	}

	public void verifyErrorMsginAssignmentName() {
		String expErrorMSg = "Please enter an assignment name without using special characters";

		if (getQB_AssignemntName_ErrorMSg().isElementAvailable(1)) {
			String actualMsg = getQB_AssignemntName_ErrorMSg().getText();
			bc.textCompareEquals(actualMsg, expErrorMSg,
					"ErrorAlert for assignment name with special chars Displayed as expected ",
					"ErrorAlert Displayed for assignment name with special chars is not as expected");
		} else {

			bc.failedStep("After entering Assginment name with special chars Error message not displayed ");

		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @Description-This method will assign documents to assignments using sample
	 *                   methods and verify the Doc counts assigned in manage
	 *                   assignment page.
	 * @param assignmentName
	 * @param sampleMethod
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	/**
	 * @author Jayanthi.ganesan
	 * @Description-This method will assign documents to assignments using sample
	 *                   methods and verify the Doc counts assigned in manage
	 *                   assignment page.
	 * @param assignmentName
	 * @param sampleMethod
	 * @return
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	public String assignwithSamplemethod(String assignmentName, String samplemethod, String countToAssign)
			throws NumberFormatException, InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getStartingCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		VerifySampleMethodDDOptions();
		if (samplemethod.equalsIgnoreCase("Percentage")) {
			getsampleMethod().selectFromDropdown().selectByVisibleText("Percent of Selected Docs");
			getCountToAssign().SendKeys(countToAssign);
			bc.stepInfo("Bulk assigned using sample method/Precentage with precentage of " + " doc count  -"
					+ countToAssign + "% .");
			getPersistCB_ExistAssgn().waitAndClick(5);

		} else if (samplemethod.equalsIgnoreCase("Count of Selected Docs")) {
			getsampleMethod().selectFromDropdown().selectByVisibleText("Count of Selected Docs");
			getCountToAssign().SendKeys(countToAssign);
			bc.stepInfo(
					"Bulk assigned using sample method/Count of Selected Docs with" + " doc count  -" + countToAssign);
		} else if (samplemethod.equalsIgnoreCase("Parent Level Docs Only")) {
			getsampleMethod().selectFromDropdown().selectByVisibleText("Parent Level Docs Only");
			bc.stepInfo(
					"Bulk assigned using sample method/Count of Selected Docs with" + " doc count  -" + countToAssign);
		}else if (samplemethod.equalsIgnoreCase("Inclusive Email")) {
			getsampleMethod().selectFromDropdown().selectByVisibleText("Inclusive Email");
			bc.stepInfo(
					"Bulk assigned using sample method/Count of Selected Docs with" + " doc count  -" + countToAssign);
		}
		bc.waitForElement(getSelectAssignmentToBulkAssign(assignmentName));
		getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);
		getContinueBulkAssign().waitAndClick(15);
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		String FinalCount = getFinalCount().getText();

		getFinalizeButton().Click();
		bc.VerifySuccessMessage(
				"Bulk Assign has been added to background process. You will get notification on completion.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		int ExpectedDocCount = Integer.parseInt(FinalCount);
		int ActualDocCount = Integer.parseInt(verifydocsCountInAssgnPage(assignmentName));
		assertion.assertEquals(ActualDocCount, ExpectedDocCount);
		bc.passedStep("Sucesfuly verified doc counts assigned in Assignmnets[DocCount found in assignment page is "
				+ ActualDocCount + "] using " + samplemethod + " sample method.");

		assertion.assertAll();

		return FinalCount;
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param existing [Should be true if we need to check for existing assign tab]
	 */

	public void verifyPersistentHitIcon(boolean existing) {
		if (existing) {
			bc.ValidateElement_Presence(getPersistCB_ExistAssgn(),
					"Persistant search option present in existing assign tab.");

		} else {
			bc.waitTime(1);
			getBulkAssign_NewAssignment().waitAndClick(20);
			bc.ValidateElement_Presence(getPersistCB_NewAssgn(), "Persistant search option present in New assign tab.");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used verify the classification dd options
	 * @return
	 */
	public List<String> verifyClassificationDDOptions() {
		List<String> actOptions = new ArrayList<String>();
		driver.waitForPageToBeReady();
		bc.waitForElement(getSelectedClassification());
		Select select = new Select(getSelectedClassification().getWebElement());
		List<WebElement> options = select.getOptions();
		for (int i = 1; i <= options.size() - 1; i++) {
			System.out.println(options.get(i).getText());
			bc.stepInfo(options.get(i).getText());
			actOptions.add(options.get(i).getText());
		}
		return actOptions;
	}

	/**
	 * @author: Arun Created Date: 05/08/2022 Modified by: NA Modified Date: NA
	 * @description: this method will create coding form and assignment with tag or
	 *               folder
	 * @param sg
	 * @param codingFormName
	 * @param assignmentName
	 * @param searchstring
	 * @param type
	 * @param typename
	 */
	public void createTagOrFolderCodingFormAssignment(String sg, String codingFormName, String assignmentName,
			String searchstring, String type, String typeName) throws InterruptedException {
		driver.waitForPageToBeReady();
		bc.selectsecuritygroup(sg);
		CodingForm codingForm = new CodingForm(driver);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		bc.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(5);
		bc.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingFormName);
		codingForm.savingCodingForm();
		driver.waitForPageToBeReady();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		if (type.equalsIgnoreCase("tag")) {
			tagsAndFolderPage.CreateTag(typeName, sg);
		} else if (type.equalsIgnoreCase("folder")) {
			tagsAndFolderPage.CreateFolder(typeName, sg);
		}
		createAssignment(assignmentName, codingFormName);
		search = new SessionSearch(driver);
		search.basicContentSearch(searchstring);
		search.bulkAssignExisting(assignmentName);
		editAssignmentUsingPaginationConcept(assignmentName);
		addReviewerAndDistributeDocs();
	}

	/**
	 * @author: Arun Created Date: 05/08/2022 Modified by: NA Modified Date: NA
	 * @description: this method will select assignment and tag or folder popup to
	 *               verify availability
	 * @param sg
	 * @param assignmentName
	 * @param userName
	 * @param type
	 */
	public void verifyTagOrFolderAvailabilityInAssignment(String sg, String assignmentName, String userName,
			String type) throws InterruptedException {
		bc.selectsecuritygroup(sg);
		editAssignmentUsingPaginationConcept(assignmentName);
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getAssgn_ManageRev_selectReviewer(userName));
		getAssgn_ManageRev_selectReviewer(userName).waitAndClick(5);
		getAssgn_ManageRev_Action().waitAndClick(5);
		if (type.equalsIgnoreCase("tag")) {
			bc.waitForElement(getAssgn_ManageRev_Action_tagdoc());
			getAssgn_ManageRev_Action_tagdoc().waitAndClick(5);
		} else if (type.equalsIgnoreCase("folder")) {
			bc.waitForElement(getAssgn_ManageRev_Action_folderdoc());
			getAssgn_ManageRev_Action_folderdoc().waitAndClick(5);

		}
		if (bc.getYesBtn().isElementAvailable(10)) {
			bc.getYesBtn().waitAndClick(2);
			driver.waitForPageToBeReady();
		}
	}

	/**
	 * @author: Arun Created Date: 18/08/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check and verify field values in assignment
	 *               distribution tab.
	 */
	public void verifyValuesFromDistributionTab(String limit, Element element) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElement(element);
		String value = element.getText().trim();
		if (limit.equalsIgnoreCase(value)) {
			bc.passedStep("Pool value displayed correctly in assignment distribute tab");
		} else {
			bc.failedStep("pool value not displayed correctly");
		}

	}

	/**
	 * @author
	 * @param pairOfAssignmentsAndExpectedTotalDocCountInAssign
	 */
	public void validateTotalDocumentCountInManageAssignmentPageForMultipleAssignments(
			Map<String, Integer> pairOfAssignmentsAndExpectedTotalDocCountInAssign) {

		List<String> assignments = new ArrayList<String>(pairOfAssignmentsAndExpectedTotalDocCountInAssign.keySet());
		for (int i = 0; i < assignments.size(); i++) {
			String assignment = assignments.get(i);
			int ExpectedTotalDocCountInAssign = pairOfAssignmentsAndExpectedTotalDocCountInAssign.get(assignment);
			assignmentPagination(assignment);
			int assignment01DocCount = Integer.parseInt(getSelectAssignmentDocCount(assignment).getText());
			bc.digitCompareEquals(assignment01DocCount, ExpectedTotalDocCountInAssign,
					"Assignment Total Document Count : '" + assignment01DocCount
							+ "' in Manage Assignment page match with Expected Count : "
							+ ExpectedTotalDocCountInAssign,
					"Assignment Total Document Count Doesn't match with Expected Count.");
		}
	}

	/**
	 * @author
	 * @param pairOfAssignmentsAndexptedDocCountInDistrTab
	 * @throws InterruptedException
	 */
	public void verifyTotalCountOfDocsInMultipleAssignmentsInDistributeDocumentsTab(
			Map<String, Integer> pairOfAssignmentsAndexptedDocCountInDistrTab) throws InterruptedException {
		List<String> assignments = new ArrayList<String>(pairOfAssignmentsAndexptedDocCountInDistrTab.keySet());
		for (int i = 0; i < assignments.size(); i++) {
			String assignment = assignments.get(i);
			editAssignment(assignment);
			driver.waitForPageToBeReady();
			bc.waitForElement(getDistributeTab());
			getDistributeTab().waitAndClick(5);
			bc.waitForElement(getEditAggn_Distribute_Totaldoc());
			int value = Integer.parseInt(getEditAggn_Distribute_Totaldoc().getText().trim());
			if (pairOfAssignmentsAndexptedDocCountInDistrTab.get(assignment) == value) {
				bc.passedStep("verified that Total count of docs in Assignment: '" + value
						+ "' in Distribute Documents Tab is match with the Expected Count : '"
						+ pairOfAssignmentsAndexptedDocCountInDistrTab.get(assignment) + "'");
			} else {
				bc.failedStep(
						"Total count of docs in Assignment in Distribute Documents Tab doesn't match with the Expected Count ");
			}
			bc.waitForElement(getAssignment_BackToManageButton());
			getAssignment_BackToManageButton().waitAndClick(5);
		}
	}

	/**
	 * @author
	 * @description : delete multiple assignments with list
	 * @param listOfAssignments
	 */
	public void deleteMultipleAssgnmntUsingPagination(List<String> listOfAssignments) {

		for (int i = 0; i < listOfAssignments.size(); i++) {
			String assignmentName = listOfAssignments.get(i);
			driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			bc.waitForElement(getNumberOfAssignmentsToBeShown());
			getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssgnPaginationCount().Visible();
				}
			}), Input.wait30);
			int count = ((getAssgnPaginationCount().size()) - 2);
			for (int j = 0; j < count; j++) {
				driver.waitForPageToBeReady();
				Boolean status = getSelectAssignment(assignmentName).isElementAvailable(5);
				if (status == true) {
					getSelectAssignment(assignmentName).ScrollTo();
					if (!getSelectAssignmentHighlightCheck(assignmentName).isElementAvailable(5)) {
						getSelectAssignment(assignmentName).waitAndClick(3);
						// getSelectAssignment(assignmentName).waitAndClick(5);
					}
					driver.scrollPageToTop();
					bc.waitForElement(getAssignmentActionDropdown());
					getAssignmentActionDropdown().Click();
					bc.stepInfo("Expected assignment found in the page " + j);
					break;
				} else {
					driver.scrollingToBottomofAPage();
					bc.waitForElement(getAssgnPaginationNextButton());
					getAssgnPaginationNextButton().Click();
					bc.stepInfo("Expected assignment not found in the page " + j);
				}
			}
			bc.waitForElement(getAssignmentAction_DeleteAssignment());
			getAssignmentAction_DeleteAssignment().waitAndClick(3);
			bc.getYesBtn().waitAndClick(5);
			bc.VerifySuccessMessage("Assignment deleted successfully");
		}
	}

	/**
	 * @author
	 * @description : addReviewers ( handled dynamically )
	 * @param listOfReviewers
	 */
	public void addReviewers(List<String> listOfReviewers) {

		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		for (int i = 0; i < listOfReviewers.size(); i++) {
			String reviewer = listOfReviewers.get(i);
			if (reviewer.equals("REV")) {
				bc.waitForElement(getSelectUserToAssig());
				getSelectUserToAssig().waitAndClick(5);
			} else if (reviewer.equals("RMU")) {
				driver.scrollingToElementofAPage(getSelectUserToAssigReviewerManager());
				getSelectUserToAssigReviewerManager().waitAndClick(5);
			} else if (reviewer.equals("PA")) {
				driver.scrollingToElementofAPage(getSelectUserToAssignPA());
				getSelectUserToAssignPA().waitAndClick(5);
			} else if (reviewer.equals("DA")) {
				getSelectDAUserToAssign().ScrollTo();
				getSelectDAUserToAssign().waitAndClick(5);
			}
		}
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
	}

	/**
	 * @author
	 * @param countOfDocs
	 * @description : distributeGivenDocCountToReviewers
	 */
	public void distributeGivenDocCountToReviewers(String countOfDocs) {

		bc.waitForElement(getDistributeTab());
		getDistributeTab().waitAndClick(5);
		bc.waitForElementCollection(selectReviewersToDistributeDocs());
		List<WebElement> listOfReviewersToDistributeDocs = selectReviewersToDistributeDocs().FindWebElements();
		for (int i = 0; i < listOfReviewersToDistributeDocs.size(); i++) {
			listOfReviewersToDistributeDocs.get(i).click();
		}
		getAssgn_docsToDistribute().SendKeys(countOfDocs);
		getDistributeBtn().waitAndClick(3);
		bc.stepInfo(countOfDocs + " Documents are distributed to reviewers successfully");
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author
	 * @param totalDocs
	 * @param noOfReviewers
	 * @param modifyDocsCount
	 * @return
	 */
	public int[] evenlyDistributedDocCountToReviewers(int totalDocs, int noOfReviewers, int modifyDocsCount) {
		int modifiedTotalDocsCount = totalDocs - modifyDocsCount;
		int remainingUnAssignDocsCount = modifiedTotalDocsCount % noOfReviewers;
		int DocCountDistributeToRev = modifiedTotalDocsCount - remainingUnAssignDocsCount;
		int totalremainingUnAssignDocsCount = remainingUnAssignDocsCount + modifyDocsCount;
		int DistributedCountForEachRev = DocCountDistributeToRev / noOfReviewers;
		int[] DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev = { DocCountDistributeToRev,
				totalremainingUnAssignDocsCount, DistributedCountForEachRev };
		return DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev;
	}

	/**
	 * @author: Arun Created Date: 19/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check the availability of keyword in
	 *               assignment keywords popup
	 * 
	 */
	public void verifyKeywordsAvailabilityInAssignment(String[] keywords) {
		bc.waitForElement(getAssgn_Keywordsbutton());
		getAssgn_Keywordsbutton().waitAndClick(10);
		bc.waitForElement(getAssgn_Keywordspopup());

		for (int i = 0; i < keywords.length; i++) {
			if (getKeywordCheckBox(keywords[i]).isElementAvailable(5)) {
				bc.passedStep("Added keyword available");
			} else {
				bc.failedStep("Added keyword not available");
			}
		}
		bc.waitForElement(getKeywordPopUpCancelBtn());
		getKeywordPopUpCancelBtn().waitAndClick(10);

	}

	/**
	 * @author Jayanthi.Ganesan returns row values from assignmetn table [ex -ToDo
	 *         Count,completed do count]
	 */
	public String getRowValueFromAssignmentTable(String rowName, String assignmentName) {
		int index = bc.getIndex(getHeaders_AssignmentTable(), rowName);
		bc.stepInfo("Value of " + rowName + " for " + assignmentName + "is "
				+ getRowValuesinAssignmentTAble(assignmentName, index).getText());
		return getRowValuesinAssignmentTAble(assignmentName, index).getText();
	}

	/**
	 * @author Jayanthi.Ganesan This method will perform uncomplete all dcos from
	 *         manage assignments page.
	 */
	public void uncompleteAllDocs(String assignmentName) {

		getSelectAssignment(assignmentName).isElementAvailable(2);
		getSelectAssignment(assignmentName).ScrollTo();
		getSelectAssignment(assignmentName).Click();
		driver.scrollPageToTop();
		getAssignmentActionDropdown().waitAndClick(3);
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentAction_UnCompletedoc());
		getAssignmentAction_UnCompletedoc().waitAndClick(10);
		bc.waitForElement(getUnComplePopOutYes());
		getUnComplePopOutYes().waitAndClick(10);
		bc.VerifySuccessMessage("All Documents successfully un-completed.");
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @Description-This method will let the RMU View All Docs in DocList option in
	 *                   manage assignment page
	 */
	public void ViewAllDocsinDocListBtnINActionDD() {

		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().ScrollTo();
		getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.stepInfo("clicked Action dropdown");
		if (getAssignmentAction_ViewinDocList().isDisplayed()) {
			bc.passedStep("View All Docs in DocList option is displayed");
			getAssignmentAction_ViewinDocList().Click();
		} else {
			bc.failedStep("View All Docs in DocList is not displayed in manage assignment page under action dropdown");
		}

	}

	/**
	 * @author: Arun Created Date: 21/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will add the reviewer in manage reviewer tab
	 *               present in assignment
	 * 
	 */
	public void addReviewerInManageReviewerTab() {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		bc.waitForElement(getSelectUserToAssig());
		getSelectUserToAssig().waitAndClick(5);
		bc.waitForElement(getAdduserBtn());
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");

	}

	/**
	 * @author: Arun Created Date: 22/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check the bulkassign popup default selection
	 *               and assignments
	 * 
	 */
	public void verifyBulkAssignPopupWrtToProject(String assign1, String assign2) {

		bc.stepInfo("verify assign/unassign popup");
		driver.waitForPageToBeReady();
		if (getsampleMethod().isDisplayed() && getassignmentTabTreeView().isElementAvailable(10)) {
			bc.passedStep(
					"assign document option is selected by default and" + " displayed existing assignments in a tree");
		} else {
			bc.failedStep("unassign document option selected by default");
		}
		if (getSelectAssignmentToBulkAssign(assign1).isElementAvailable(10)
				&& !getSelectAssignmentToBulkAssign(assign2).isElementAvailable(5)) {
			bc.passedStep("assignment group and assignments in popup are available wrt to projects");
		} else {
			bc.failedStep("assignments in popup not available wrt to projects");
		}
		bc.waitForElement(bulkAssignCancelButton());
		bulkAssignCancelButton().waitAndClick(10);
	}

	/**
	 * @author
	 * @param assignmentGroup
	 * @param assignmentName
	 */
	public void editAssignmentInAssignGroup(String assignmentGroup, String assignmentName) {
		selectAssignmentGroup(assignmentGroup);
		driver.waitForPageToBeReady();
		bc.waitForElement(getSelectAssignment(assignmentName));
		if (!getSelectAssignmentHighlightCheck(assignmentName).isElementAvailable(5)) {
			getSelectAssignment(assignmentName).waitAndClick(5);
			driver.waitForPageToBeReady();
		}
		NavigateToNewEditAssignmentPage("Edit");
	}

	/**
	 * @author
	 * @param setDefaultCodingForm
	 * @return
	 */
	public List<String> SelectAllCodingFormAndChangeSortingSequence(String setDefaultCodingForm) {
		List<String> listOfCFAfterSorting = new ArrayList<String>();
		try {
			getSelectSortCodingForm_Tab().ScrollTo();
		} catch (Exception e) {

		}
		getSelectSortCodingForm_Tab().waitAndClick(10);

		if (SelectCFPopUp_Step1().isElementAvailable(2)) {
			bc.stepInfo("Add / Remove Coding Forms in this Assignment Pop Up displayed.");
			bc.waitForElement(getSelectAllCodingForm());
			getSelectAllCodingForm().ScrollTo();
			getSelectAllCodingForm().waitAndClick(5);
			bc.waitTime(1);
			getSelectCodeFormRadioBtn(setDefaultCodingForm).waitAndClick(5);
			bc.waitTime(1);
			try {
				sortOrderNxtBtn().ScrollTo();
			} catch (Exception e) {

			}
			sortOrderNxtBtn().waitAndClick(5);
			bc.waitForElement(getCodingFormOrderPopUpHeader());
			int noOfCFInCodingFormOrderPopUp = listOfCFInCodingFormOrderPopUp().size();
			if (noOfCFInCodingFormOrderPopUp > 2) {
				Actions action = new Actions(driver.getWebDriver());
				action.clickAndHold(getCodingFormOrderHamburgerIcons(3).getWebElement());
				action.moveToElement(getCodingFormOrderHamburgerIcons(2).getWebElement());
				action.release(getCodingFormOrderHamburgerIcons(2).getWebElement());
				action.build().perform();
			}
			listOfCFAfterSorting = bc.availableListofElements(listOfCFInCodingFormOrderPopUp());

			sortCodeFormOrderSaveBtn().waitAndClick(5);
			bc.waitTime(2);
			bc.waitForElement(getSelectedCodeForminAssignPAge());
			String acualCfName = getSelectedCodeForminAssignPAge().getText();
			List<String> listOfAcualCfName = Arrays.asList(acualCfName.split(", "));
			bc.listCompareEquals(listOfCFAfterSorting, listOfAcualCfName,
					"list of coding form in Coding Form Order PopUp Match with the coding forms displayed under 'Coding Form' section in New Assignment",
					"Expected result don't match with Actual Result");

		} else {
			bc.failedStep("Step-1 Select CodingForm Pop Up Not displayed.");
		}
		return listOfCFAfterSorting;
	}

	/**
	 * @author
	 * @param listOfCFName
	 * @param codingFormSetAsDefault
	 * @param sort
	 * @return
	 */
	public List<String> editExistingCodingForm(List<String> listOfCFName, String codingFormSetAsDefault, boolean sort) {
		List<String> listOfCFAfterSorting = new ArrayList<String>();
		try {
			getSelectSortCodingForm_Tab().ScrollTo();
		} catch (Exception e) {

		}
		getSelectSortCodingForm_Tab().waitAndClick(10);

		if (SelectCFPopUp_Step1().isElementAvailable(2)) {
			bc.stepInfo("Add / Remove Coding Forms in this Assignment Pop Up displayed.");
			getSelectAllCodingForm().waitAndClick(5);
			bc.waitTime(2);
			getSelectAllCodingForm().waitAndClick(5);
			for (String CFName : listOfCFName) {
				if (getSelectCF_CheckBox(CFName).isElementAvailable(5)) {
					getSelectCF_CheckBox(CFName).ScrollTo();
					getSelectCF_CheckBox(CFName).waitAndClick(5);
				} else {
					bc.failedStep("CodingForm : '" + CFName + "' not present");
				}
			}
			bc.waitTime(1);
			getSelectCodeFormRadioBtn(codingFormSetAsDefault).waitAndClick(5);
			bc.waitTime(1);
			try {
				sortOrderNxtBtn().ScrollTo();
			} catch (Exception e) {

			}
			sortOrderNxtBtn().waitAndClick(5);
			bc.waitForElement(getCodingFormOrderPopUpHeader());
			if (sort) {
				int noOfCFInCodingFormOrderPopUp = listOfCFInCodingFormOrderPopUp().size();
				if (noOfCFInCodingFormOrderPopUp > 2) {
					Actions action = new Actions(driver.getWebDriver());
					action.clickAndHold(getCodingFormOrderHamburgerIcons(3).getWebElement());
					action.moveToElement(getCodingFormOrderHamburgerIcons(2).getWebElement());
					action.release(getCodingFormOrderHamburgerIcons(2).getWebElement());
					action.build().perform();
				}
			}
			listOfCFAfterSorting = bc.availableListofElements(listOfCFInCodingFormOrderPopUp());

			sortCodeFormOrderSaveBtn().waitAndClick(5);
			bc.waitTime(2);
			bc.waitForElement(getSelectedCodeForminAssignPAge());
			String acualCfName = getSelectedCodeForminAssignPAge().getText();
			List<String> listOfAcualCfName = Arrays.asList(acualCfName.split(", "));
			bc.listCompareEquals(listOfCFAfterSorting, listOfAcualCfName,
					"list of coding form in Coding Form Order PopUp Match with the coding forms displayed under 'Coding Form' section in New Assignment",
					"Expected result don't match with Actual Result");

		} else {
			bc.failedStep("Step-1 Select CodingForm Pop Up Not displayed.");
		}
		return listOfCFAfterSorting;
	}

	/**
	 * @author
	 * @param assignmentName
	 * @param setDefaultCodingForm
	 * @return
	 * @throws InterruptedException
	 */
	public List<String> createAssignmentFromAssgnGroupByChangeSortingSequenceOfCF(String assignmentName,
			String setDefaultCodingForm) throws InterruptedException {
		List<String> listOfCFAfterSorting = new ArrayList<String>();
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getAssignmentActionDropdown());
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getAssignmentAction_NewAssignment());
		bc.waitForElement(getAssignmentAction_NewAssignment());
		getAssignmentAction_NewAssignment().waitAndClick(20);
		driver.waitForPageToBeReady();
		bc.waitForElement(getAssignmentName());
		getAssignmentName().SendKeys(assignmentName);
		driver.waitForPageToBeReady();
		getParentAssignmentGroupName().isDisplayed();
		bc.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		driver.waitForPageToBeReady();
		listOfCFAfterSorting = SelectAllCodingFormAndChangeSortingSequence(setDefaultCodingForm);
		bc.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(3);
		bc.stepInfo("Assignment " + assignmentName + " created.");
		return listOfCFAfterSorting;
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param listOfReviewers
	 * @param userType
	 */
	public void addReviewersUsingList(List<String> listOfReviewers, String userType) {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(10);
		bc.waitForElement(getAddReviewersBtn());
		getAddReviewersBtn().waitAndClick(10);
		for (int i = 0; i < listOfReviewers.size(); i++) {
			bc.waitForElement(getSelectUserToAssignAsReviewer(listOfReviewers.get(i)));
			if (getSelectUserToAssignAsReviewer(listOfReviewers.get(i)).isElementAvailable(1)) {
				bc.passedStep("Users list shows " + userType + " user" + listOfReviewers.get(i));
				getSelectUserToAssignAsReviewer(listOfReviewers.get(i)).ScrollTo();
				getSelectUserToAssignAsReviewer(listOfReviewers.get(i)).waitAndClick(5);
			} else {
				bc.failedStep("Users list not  showing  " + userType + " user" + listOfReviewers.get(i));
			}
		}
		getAdduserBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Action saved successfully");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method verifies warning message if we try to do
	 *              redistribute completed documents
	 */
	public void VerifyWarnignMSGinManageRevTab_RedistributeDocs(String user1, String user2) {
		bc.waitForElement(getAssignment_ManageReviewersTab());
		getAssignment_ManageReviewersTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		getAssgn_ManageRev_selectReviewer(user1).ScrollTo();
		getAssgn_ManageRev_selectReviewer(user1).waitAndClick(10);
		if (user2 != null) {
			getAssgn_ManageRev_selectReviewer(user2).ScrollTo();
			getAssgn_ManageRev_selectReviewer(user2).waitAndClick(10);
		}
		bc.stepInfo("Selected the Reviewer that is assigned with few docs");
		getAssgn_ManageRev_Action().waitAndClick(20);
		bc.waitForElement(getAssgn_RedistributeDoc());
		getAssgn_RedistributeDoc().waitAndClick(20);
		try {
			bc.VerifyWarningMessage("No documents available to do the selected action");
			bc.passedStep(
					"Warning Message Displayed As Exepcted When we tried to Redistribute Docs to a Reviewer user with completed docs.");
		} catch (Exception e) {
			bc.failedStep("Warning Message not displayed");

		}
	}

	/**
	 * @author
	 * @param ListOfAssignedMetadata
	 * @description : get All Assigned Or UnAssigned Metadata From ConfigureMetadata
	 * @return
	 */
	public List<String> getAllAssignedOrUnAssignedMetadataFromConfigureMetadata(boolean ListOfAssignedMetadata) {
		List<String> listOfResultMetadata = new ArrayList<String>();
		bc.waitForElement(GetSelectMetaDataBtn());
		GetSelectMetaDataBtn().waitAndClick(5);
		if (ListOfAssignedMetadata) {
			bc.waitForElementCollection(getAssignedMetadataListInAssgnPage());
			listOfResultMetadata = bc.availableListofElements(getAssignedMetadataListInAssgnPage());
		} else {
			bc.waitForElementCollection(getUnAssignedMetadataListInAssgnPage());
			listOfResultMetadata = bc.availableListofElements(getUnAssignedMetadataListInAssgnPage());
		}

		bc.waitForElement(getMetadataFielOkBtn());
		getMetadataFielOkBtn().waitAndClick(5);

		return listOfResultMetadata;
	}

	/**
	 * @author
	 * @description : get All Keywords From Add Keyword Popup
	 * @return
	 */
	public List<String> getAllKeywordsFromAddKeywordPopup() {
		List<String> listOfKeywords = new ArrayList<String>();
		bc.waitForElement(getAssgn_Keywordsbutton());
		getAssgn_Keywordsbutton().waitAndClick(5);
		bc.waitForElementCollection(getCheckedKeywordList());
		listOfKeywords = bc.availableListofElements(getCheckedKeywordList());
		getAssgn_Keywordokbutton().waitAndClick(5);
		if (bc.getYesBtn().isElementAvailable(10)) {
			bc.getYesBtn().waitAndClick(5);
		}
		return listOfKeywords;
	}
	

	/**
	 * @author Jayanthi.ganesan
	 * @description this method will Perform Tag all / Folder All option in manage
	 *              assignment page
	 */

	public void Bulk_TagAll_FolderAll(boolean tagAll) {
		driver.scrollPageToTop();
		bc.waitForElement(getAssignmentActionDropdown());
		getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.stepInfo("clicked Action dropdown");
		if (tagAll) {
			getAssignmentAction_TagAll().ScrollTo();
			getAssignmentAction_TagAll().Click();
			bc.stepInfo("clicked Tag All docs option ");
		} else {
			getAssignmentAction_FolderAll().ScrollTo();
			getAssignmentAction_FolderAll().Click();
			bc.stepInfo("clicked Folder All docs option ");
		}

	}
	

}

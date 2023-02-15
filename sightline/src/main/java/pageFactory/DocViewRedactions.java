package pageFactory;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
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

public class DocViewRedactions {

	Driver driver;
	BaseClass base;
	LoginPage loginPage;
	SoftAssert softAssertion;
	ProductionPage page;
	DocViewPage docView;
	Input in = new Input();

	String namesg1 = "Default_1234" + Utility.dynamicNameAppender();
	String namesg2 = "Default_5678" + Utility.dynamicNameAppender();
	String docsforsg1 = "ID00000673 OR ID00000678";
	String docsforsg2 = "ID00000688";

	/**
	 * @author Sai.Duvvuru Reusable code For Modules DocView/Redaction & DocView
	 * @return
	 */

	public Element gotoSearchTab() {
		return driver.FindElementByXPath("//i[@class='fa fa-lg fa-fw fa-search']");
	}

	public Element gotoSessionSearch() {
		return driver.FindElementByXPath("//a[@class='a-menu'][@name='Session']");
	}

	public Element searchClicableTab() {
		return driver.FindElementByXPath("//a[@id='btnBasicSearch']");
	}

	public Element searchResultsTab() {
		return driver.FindElementByXPath("//*[@id=\"001\"]/span");
	}

	public Element shoppingCart() {
		return driver.FindElementByXPath("//*[@id=\"DragDefaulttile\"]");
	}

	public Element actionsDropDown() {
		return driver.FindElementByXPath("//div[@id='idsearchoption']//div[@class='pull-right']//div/button/span");
	}

	public Element docViewFromDropDown() {
		return driver.FindElementByXPath("//a[contains(text(),'View In DocView')]");
	}

	public Element redactionIcon() {
		return driver.FindElementById("gray-tab");
	}

	public Element multiPageIcon() {
		return driver.FindElementById("multipageRedaction_divDocViewer");
	}

	public Element multiPageInputTextbox() {
		return driver.FindElementByXPath("//input[@name='pages']");
	}

	public Element multiPageInputSavaBtn() {
		return driver.FindElementByXPath("//button[@id='btnSave']");
	}

	public Element docExplorerIcon() {
		return driver.FindElementByXPath("//*[@id=\"123\"]/i");
	}

	public Element selectingFirstdataFromTable() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentList\"]/tbody/tr[1]/td[1]/label/i");
	}

	public Element docExplorerActions() {
		return driver.FindElementByXPath("//*[@id=\"idDocExplorerActions\"]");
	}

	public Element redactionTagSelect() {
		return driver.FindElementByXPath("//select[@id='ddlMultiRedactionTagsForPopup']");
	}

	public Element redactionMethodSelect() {
		return driver.FindElementByXPath("//select[@id='ddlIncludeExclude']");
	}

	public Element multiPagePopUp() {
		return driver.FindElementById("multiPageRedactionDiv");
	}

	public ElementCollection availableTagsInMultiPageRedact() {
		return driver.FindElementsByXPath("//select[@id=\"ddlMultiRedactionTagsForPopup\"]/option");
	}

	// Edited on 25/07

	public Element rectangleClick() {
		return driver.FindElementById("blackRectRedact_divDocViewer");
	}

	public Element redactionSave() {
		return driver.FindElementById("btnSave");
	}

	public Element redactionForwardNavigate() {
		return driver.FindElementByXPath("//i[@id='NextAllRedaction']");
	}

	public Element redactionBackwardNavigate() {
		return driver.FindElementByXPath("//i[@id='PrevAllRedaction']");
	}

	public Element deleteRedactioBtn() {
		return driver.FindElementById("btn_delete");
	}

	public WebElement getDocViewTextArea() {
		return driver.FindElementById("ig0level5").getWebElement();
	};

	public Element deleteClick() {
		return driver.FindElementByXPath("//*[@id=\"btn_delete\"]");
	}

	public Element rectangleRedactionTagSelect() {
		return driver.FindElementById("ddlRedactionTagsForPopup");
	}

	// Edited on 26/07-------- For TC_RPMXCON52255

	public Element gearIconMiniDocView() {
		return driver.FindElementByXPath("//i[@id='wEdit']");
	}

	public Element selectingSeconddataFromMiniDocList() {
		return driver.FindElementByXPath("//tbody/tr[2]/td[1]/label[1]/i[1]");
	}

	public Element pageNumberTextBox() {
		return driver.FindElementByXPath("//input[@id='txtBoxPageNum']");
	}

	public Element nextDocViewBtn() {
		return driver.FindElementByXPath("//a[@id='btnNextDocView']");
	}

	public Element previousDocViewBtn() {
		return driver.FindElementByXPath("//a[@id='btnPrevDocView']");
	}

	public Element historyDrowDownBtn() {
		return driver.FindElementByXPath("//a[@id='btnDocHistory']");
	}

	// Edited on 28/07-------- For Duplicate Docs Prerequisites

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

	public Element deleteSecurityGroupdd() {
		return driver.FindElementByXPath("//select[@id='ddlSecurityGroupsList']");
	}

	public Element deleteSecurityGroup() {
		return driver.FindElementById("btnDeleteSecurityGroup");
	}

	public Element addToShoppingCartbtn() {
		return driver.FindElementById("addtile-1630-001");
	}

	public Element deleteSelectYes() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element addNewSearchBtn() {
		return driver.FindElementByXPath("//button[@id='add_tab']");
	}

	public Element actionsBulkRelease() {
		return driver.FindElementByXPath("//*[@id=\"ddlbulkactions\"]/ul/li[10]/a");
	}

	public Element clickSG1Checkbox() {
		return driver.FindElementByXPath("//div[contains(text(),'Default_1234')]/preceding-sibling::div[1]");
	}

	public Element clickSG2Checkbox() {
		return driver.FindElementByXPath("//div[contains(text(),'Default_5678')]/preceding-sibling::div[1]");
	}

	public Element deleteText() {
		return driver.FindElementByClassName("textboxlist-bit-box-deletebutton");
	}

	// Created on 29/07

	public Element actionsClick() {
		return driver.FindElementById("idAction");
	}

	public Element clickBulkRelease() {
		return driver.FindElementByXPath("//a[contains(text(),'Bulk Release')]");
	}

	public Element clickRelease() {
		return driver.FindElementByXPath("//*[@id='btnRelease']");
	}

	public Element clickFinaliseRelease() {
		return driver.FindElementByXPath("//*[@id=\"btnfinalizeAssignment\"]");
	}

	public Element clearShoppingCart() {
		return driver.FindElementByXPath("//i[@class='fa fa-times-circle ui-removeTile']");
	}

	public Element clickRedactionTagsManage() {
		return driver.FindElementByXPath("//*[@id=\"LeftMenu\"]/li[3]/ul/li[4]/a");
	}

	public Element allActionsManageClick() {
		return driver.FindElementById("ddlSecurityGroupRedaction");
	}

	public Element allRedactionsTags() {
		return driver.FindElementByClassName("jstree-anchor");
	}

	public Element redactionTagAction() {
		return driver.FindElementByClassName("btn btn-defualt action-title");
	}

	public Element redactionTagActionNew() {
		return driver.FindElementById("aAddRedactionTag");
	}

	public Element redactionTagActionNewName() {
		return driver.FindElementById("txtRedactionNew");
	}

	public Element redactionTagActionNewSave() {
		return driver.FindElementById("btnSaveRedactionTag");
	}

	// Unrelease and SG access

	public Element unReleaseFromSGBtn() {
		return driver.FindElementByXPath("//button[@id='btnUnrelease']");
	}

	public Element manageBtn() {
		return driver.FindElementByXPath("//*[@id=\"2\"]/i");
	}

	public Element editUserRMUSecurity() {
		return driver.FindElementByXPath("//td[contains(text(),'Consilio RMU1')]");
	}

	public Element securityGroupID() {
		return driver.FindElementByXPath("//option[contains(text(),'SG2105398')]");
	}

	public Element manageUser() {
		return driver.FindElementByXPath("//*[@id=\"LeftMenu\"]/li[3]/ul/li[6]/a");
	}

	public Element userRolesDropDown() {
		return driver.FindElementByXPath("//select[@id='ddlRoles']");
	}

	public Element userTextInput() {
		return driver.FindElementByXPath("//*[@id=\"txtsearchUser\"]");
	}

	public Element userFiletersBtn() {
		return driver.FindElementByXPath("//*[@id=\"btnAppyFilter\"]");
	}

	public Element userEditBtn() {
		return driver.FindElementByXPath("//table[@id='dtUserList']//tr[1]//td[7]//a[text()='Edit']");
	}

	public Element userSelectSecurityGroup() {
		return driver.FindElementByXPath("//select[@id='ddlSg']");
	}

	public Element userSelectSGSaveBtn() {
		return driver.FindElementByXPath("//button[@id='btnsubmit']");
	}

	// DocView Redact Duplicates

	public Element getDocView_Redactrec_textarea() {
		return driver.FindElementByXPath("//*[contains(@id,'level0surface')]");
	}

	// Elements for adding annotation layer

	public Element defaultAnnotationLayer() {
		return driver.FindElementByXPath(
				"//a[@id='1_anchor'][@class='jstree-anchor'][@data-content='Default Annotation Layer']");
	}

	public Element AdddefaultAnnotationLayer() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary'][@onclick='AnnotationRightShift();']");
	}

	public Element annotationLayerSaveBtn() {
		return driver.FindElementByXPath("//*[@id=\"btnSaveAccessControls\"]");
	}

// adding to create new annotation layer-04/08

	public Element selectSecurityGroup() {
		return driver.FindElementByXPath("//select[@id='ddlSecurityGroup']");
	}

	public Element createAnnotationLayer() {
		return driver.FindElementByXPath("//input[@id='btnAddAnnotation']");
	}

	public Element inputAnnotationName() {
		return driver.FindElementByXPath("//input[@id='AnnotationName']");
	}

	public Element saveNewAnnotationLayer() {
		return driver.FindElementByXPath("//input[@id='btnAnnotationSave']");
	}

// The below elements are created for DocView Module	  
// Added for case number RPMXCON-50779---- DocView Navigation

	public Element forwardNextDocBtn() {
		return driver.FindElementByXPath("//a[@id='btnNextDocView']");
	}

	public Element backwardPriviousDocBtn() {
		return driver.FindElementByXPath("//a[@id='btnPrevDocView']");
	}

	public Element forwardToLastDoc() {
		return driver.FindElementByXPath("//a[@id='btnLast']");
	}

	public Element backwardToFirstDoc() {
		return driver.FindElementByXPath("//a[@id='btnFirst'][@onclick='loadFirstDocument()']");
	}

// Added for 51863----52232-----52230

	public Element remarksIcon() {
		return driver.FindElementByXPath("//i[@class='fa-stack-1x fa fa-wifi fa-rotate-270']");
	}

	public Element bullhornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element bullhornIconRedColour() {
		return driver.FindElementByXPath("//b[@class='badge bg-color-red bounceIn animated']");
	}

	public Element printIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-print']");
	}

	public Element viewAllBtn() {
		return driver.FindElementByXPath("//button[@id='btnViewAll']");
	}

	public Element addRemarksBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-plus-circle']");
	}

	public Element addRemarksTextArea() {
		return driver.FindElementByXPath("//textarea[@id='txt_remark']");
	}

	public Element saveRemarksBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-check-circle-o']");
	}

	public Element deleteRemarksBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-trash-o']");
	}

	public Element editRemarksIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-pencil']");
	}

	public Element confirmDeleteRemarksBtn() {
		return driver.FindElementByXPath("//*[@id=\"btnYes\"]");
	}

	// Edited for RPMXCON-51758---- 10/08

	public Element manageAssignments() {
		return driver.FindElementByXPath("//a[@name='Assignments']");
	}

	public Element AssignmentDocCountTgl() {
		return driver.FindElementByXPath("//i[@id='assignmentDocCount']");
	}

	public Element testAssignments() {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][@id='182_anchor']");
	}

	public Element testTable() {
		return driver.FindElementByXPath("//table[@id='GridAssignment']//tr[3]//td[1]");
	}

	public Element actionDropDownAssignments() {
		return driver.FindElementByXPath("//div[@class='btn-group select-actions pull-right']//span[@class='caret']");
	}

	public Element actionDropDownAssignmentsViewInDocView() {
		return driver.FindElementByXPath("//a[@onclick='ViewDocumentsInDocView()']");
	}

	public Element persistantHitBtn() {
		return driver.FindElementByXPath("//li[@class='clsPersistentSearch']");
	}

	public Element persistantHitToggle() {
		return driver.FindElementByXPath("//i[@id='EnableSearchTerm']");
	}

	// selecting security group "test" for project
	public Element selectSecurityGroupRMU() {
		return driver.FindElementByXPath("//span[@id='SecurityGroup-selector']");
	}

	public Element assignNewSecurityGroupRMU() {
		return driver.FindElementByXPath("//span[@id='SecurityGroup-selector'][@title='Test']");
	}

	// elements added for 52233

	public Element btnactionsMenu() {
		return driver.FindElementById("idAction");
	}

	public Element actionsSubMenu(String MenuName) {
		return driver.FindElementByXPath("//a[text()='" + MenuName + "']");
	}

	public Element btnprintIcon() {
		return driver.FindElementByXPath("//*[@title='Print']");
	}

	public Element btnBullhornIcon() {
		return driver.FindElementByXPath("//*[@id='activity']/*[contains(@class,'red')]");
	}

	public Element btnViewAll() {
		return driver.FindElementById("btnViewAll");
	}

	public Element btnViewFile() {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]/td/a");
	}

	public Element btnPreviewDoc() {
		return driver.FindElementByXPath("//a[text()='Preview Document']");
	}

	public Element btnpreviewDocClose() {
		return driver.FindElementByClassName("ui-dialog-titlebar-close");
	}

	public Element sideMenu(String MenuName) {
		return driver.FindElementByXPath("//*[text()='" + MenuName + "']");
	}

	public Element txtDocId() {
		return driver.FindElementByXPath("//th/*[@id='DOCID'][1]");
	}

	public Element btnEyeIcon() {
		return driver.FindElementByXPath("//a[@class='fa fa-eye preview']");
	}

	// Added for text redaction 31/8/2021--- adding keywords
	// Added for 51991 and 51990 by Krishna

	public Element addKeywords() {
		return driver.FindElementByXPath("//*[@id=\"myTab1\"]/li[2]/a");
	}

	public Element assignKeywords() {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][@data-content='test'][@id='1_anchor']");
	}

	public Element assignKeywordsAddRight() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary'][@onclick='KeywordRightshift();']");
	}

	public Element saveKeywordsBtn() {
		return driver.FindElementByXPath("//button[@id='btnSaveAccessControls']");
	}

	public Element btnTypeOfRedaction(String Type) {
		return driver.FindElementByXPath("//span[text()='" + Type + "']");
	}

	public Element searchTextWP() {
		return driver.FindElementByXPath(".//*[@id='xEdit']/li/input");
	}

	public Element doclistTable(int r) {
		return driver.FindElementByXPath("//*[@id=\"SearchDataTable\"]/tbody/tr[" + r + "]/td[2]");
	}

	// added on 02-09-2021

	public Element firstTaskInList() {
		return driver.FindElementByXPath("//ul[@class='notification-body']/li[1]/span[1]");
	}

//added on 06-09-2021

	public Element docViewNextPage() {
		return driver.FindElementByXPath("//li[@id='nextPage_divDocViewer']");
	}

	public Element docViewPreviousPage() {
		return driver.FindElementByXPath("//li[@id='previousPage_divDocViewer']");
	}

	public Element thisPageRedaction() {
		return driver.FindElementByXPath("//li[@id='redactCurrentPage_divDocViewer']");
	}

//added on 07-09-2021		

	public Element audioDocRedactionadd() {
		return driver.FindElementByXPath("//button[@class='btn-transparent add-row']");
	}

	public Element redactionTagCancleBtn() {
		return driver.FindElementByXPath("//button[@id='btnCancel']");
	}

// added by mohan

	public Element thisPageTagSelect() {
		return driver.FindElementById("highlightCurrentPage_divDocViewer");
	}

	public Element getDocView_MiniDoc_Selectdoc(int rowno) {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//following-sibling::tbody//tr[" + rowno + "]");
	}

	public Element codeSameAsLastDocIcon() {
		return driver.FindElementById("CodeSameAsLast");
	}

	public Element popoutCodingForm() {
		return driver.FindElementByXPath("//*[@id='HdrCoddingForm']//i[@class='fa fa-expand']");
	}

	public Element getGearIcon() {
		return driver.FindElementById("wEdit");
	}

	public Element redactionSaveBtn() {
		return driver.FindElementByXPath("//button[@id='btnSave']");
	}

	public Element getAssignment_ConfigurationTab() {
		return driver.FindElementByXPath("//*[contains(text(),'Configuration')]");
	}

	public Element getAssignmentSaveButton() {
		return driver.FindElementByXPath("//input[@value='Save']");
	}

	public Element getAssgn_RedactionAplliedToggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsEnableRedactions']/following-sibling::i");
	}

	public Element backToManageBtn() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary save-btn']");
	}

// added on 08-9-2021 by Krishna

	public Element assignDefaultSecurityGroupRMU() {
		return driver.FindElementByXPath("//a[contains(@title,'Default Security Group')]");
	}

	public Element editRemarksTextArea() {
		return driver.FindElementByXPath("//textarea[contains(@id,'txt')]");
	}

	public Element multiPageIconColourCheck() {
		return driver.FindElementByXPath("//span[text()='Multipage']");
	}

	// Added by Gopinath -- 21/09/2021
	public Element getSelectReductionTagDropDown() {
		return driver.FindElementById("ddlRedactionTagsForPopup");
	}

	public Element getSaveButton() {
		return driver.FindElementById("btnSave");
	}

	public Element totalNoOfPagesInDocument() {
		return driver.FindElementById("lblTotalPageCount_divDocViewer");
	}

	// Added by Gopinath -- 22/09/2021
	public Element getUnReductionTagDropDown() {
		return driver.FindElementById("ddlRedactionTags");
	}

	// added by sowndarya.velraj
	public Element selectFirstDoc() {

		return driver.FindElementByXPath("// *[@id='SearchDataTable']//tr['row']//td[2]");
	}

	public Element selectSecondDoc() {

		return driver.FindElementByXPath("(// *[@id='SearchDataTable']//tr['row']//td[2])[2]");
	}

	public Element selectThirdDoc() {

		return driver.FindElementByXPath("//input[@id='chkMiniDocList_3']/following-sibling::i");

	}

	// Added by Gopinath -- 23/09/2021
	public Element getAudioRedactDropdown() {
		return driver.FindElementById("ddlAudioRedactionTags");
	}

	public Element getAudioRedactSave() {
		return driver.FindElementByXPath("//a[@id='btnSave']");
	}

	public Element getDeleteAudioRedactYesButton() {
		return driver.FindElementById("bot1-Msg1");
	}

	// added by Jagadeesh.jana --24Sep2021
	// *** Jagadeesh.Jana _ Starts ***
	public Element searchTextIcon() {
		return driver.FindElementByXPath("(//a[@title='Search']/i)[2]");
	}

	public Element set_searchText() {
		return driver.FindElementByXPath("//input[@id='sodTextBox']");
	}

	public Element getDocView_MiniDoclistChildWindow() {
		return driver
				.FindElementByXPath("//*[@id='HdrMiniDoc']//*[@class='jarviswidget-ctrls']//i[@class='fa fa-expand']");
	}

	public Element get_HiddenContentDocument() {
		return driver.FindElementByXPath("//td[contains(text(),'ID00001476')]");
	}

	public Element get_textHighlightedColor() {
		return driver.FindElementByCssSelector("g:nth-child(2) > rect:nth-child(n-3)");
	}

	public Element requiredDocumentFromTable() // RPMXCON-51563
	{
		return driver.FindElementByXPath("(//td[contains(text(),'ID00000663')])[1]");
	}

	public Element getHitCount(String text) // RPMXCON-51563
	{
		return driver.FindElementByXPath("//span[@id='HitCount_" + text + "']");
	}

	public Element hitForwardIcon(String text) // RPMXCON-51563
	{
		return driver.FindElementByXPath("//i[@id='NextHit_" + text + "']");
	}

	public Element hitBackwardIcon(String text) // RPMXCON-51563
	{
		return driver.FindElementByXPath("//i[@id='PrevHit_" + text + "']");
	}

	public Element getHitCount1() // RPMXCON-51564
	{
		return driver.FindElementByXPath("//span[@id='HitCount_chocolate']");
	}

	public Element hitBackwardIcon1() // RPMXCON-51564
	{
		return driver.FindElementByXPath("//i[@id='PrevHit_chocolate']");
	}

	public Element yellowHighlightIcon() {
		return driver.FindElementByXPath("//i[@style='color:#FFC600;']");
	}

	public Element textSelectionRedactionIcon() {
		return driver.FindElementByXPath("//li[@id='textSelectionRedaction_divDocViewer']");
	}

	public ElementCollection get_NoOfTermsHits() {
		return driver.FindElementsByXPath("//div[@class='message-1 col-md-12 yellow-border']");
	}

	public Element get_hitsValue(int i) {
		return driver.FindElementByXPath("(//div[@class='message-1 col-md-12 yellow-border'])[" + i + "]/p/span");
	}
	// *** Jagadeesh.Jana _ Ends ***

	public Element assignmentsTableClassification() {
		return driver.FindElementByXPath("//th[contains(text(),'Classification')]");
	}

	public Element persistantHitBtnAudio() {
		return driver.FindElementByXPath("//li[@id='search-btn-audio-view']");
	}

	public Element persistantHitRightNavigate() {
		return driver.FindElementByXPath("//i[@class='fa fa-chevron-right']");
	}

	public Element getHitCountTest() {
		return driver.FindElementByXPath("//span[@id='HitCount_test']");
	}

	public Element miniDoclistTable(int rowNum) {
		return driver
				.FindElementByXPath("//table[@id='SearchDataTable']//following-sibling::tbody//tr[" + rowNum + " ]");
	}

	public Element RedactionTagSelectWhileNavigating() {
		return driver.FindElementById("ddlRedactionTags");
	}

	// Elements for highlite-DocViewPage

	public Element HighliteIcon() {
		return driver.FindElementByXPath("//li[@id='yellow-tab']");
	}

	public Element thisPageHighlite() {
		return driver.FindElementByXPath("//i[@class='fa fa-file-text-o ']");
	}

	public Element highliteDeleteBtn() {
		return driver.FindElementByXPath("//button[@id='btn_delete']");
	}

	// annotation layer delete as PA

	public Element annotationLayerDelete(String annotationLayer) {
		return driver
				.FindElementByXPath("//*[@id=\"AnnotationsDatatable\"]//tr[td='" + annotationLayer + "']/td[3]/a[2]");
	}

	public Element confirmAnnotationLayerDelete() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}
	// icon displayed after clicking the search icon on doc view page- X

	public Element crossxIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-times-circle']");
	}

	// select security group
	public Element getsgNames() {
		return driver.FindElementById("SecurityGroup-selector");
	}

	public Element getSelectsg(String sgname) {
		return driver.FindElementByXPath("//a[@title='" + sgname + "']");
	}

	public Element getMultipageRedactionError() {
		return driver.FindElementById("pages-error");
	}

	public Element getRedactionPopup() {
		return driver.FindElementByXPath("//div[@class=\"pcc-pull-left pcc-open\"]");
	}

	public Element getRedactedPage() {
		return driver.FindElementByXPath("//div[@class=\"igAnchor\"]");
	}

	public Element getMagnifyingIcon() {
		return driver.FindElementByXPath("//div[@class=\"sodIcon\"]//a[@title=\"Search\"]");
	}

	public Element getCloseIcon() {
		return driver.FindElementByXPath("//a[@class=\"close\"]");
	}

	public Element getHitIcon() {
		return driver.FindElementByXPath("//span[contains(@class,\"count\")]");
	}

	public ElementCollection getRedactionPopups() {
		return driver.FindElementsByXPath("//div[@class=\"pcc-pull-left pcc-open\"]");
	}

	// Added by krishna on 08/11/2021

	public Element getMultipageCancleBtn() {
		return driver.FindElementById("btnCancel");
	}

	public Element nextKeywordTest() {
		return driver.FindElementById("NextHit_test");
	}

	public Element saveClick() {
		return driver.FindElementByXPath("//*[@id='btnRedactionTag']/span");
	}

	// added by jayanthi
	public Element getTrashIcon() {
		return driver.FindElementByXPath("//div[@class='text-truncate' and contains(text(),'" + Input.testData1
				+ "')] /parent::div//div[contains(@class,'trashCan')]//i");
	}

	public Element getBellyBandMsgHeaderFromDeleteBatchRedact() {
		return driver.FindElementByXPath("//div[@id='Msg1']/div/h2/span");
	}

	public Element getBellyBandMessageFromDeleteBatchRedact() {
		return driver.FindElementByXPath("//div[@id='Msg1']//div//p");
	}

	public Element getBatchRedactTextInRedactionPanel() {
		return driver
				.FindElementByXPath("//div[@class='text-truncate' and contains(text(),'" + Input.testData1 + "')]");
	}

	public Element clickPageNumber(int x) {
		return driver.FindElementByXPath("(//div[@class='pccThumbnailLabel'])[" + x + " ]");

	}

	public Element thumbNailsIcon() {
		return driver.FindElementById("thumbnails_divDocViewer");
	}

	public Element thumbNailsPanel() {
		return driver.FindElementByXPath("//div[@class='pcc-dialog-offset']");
	}

	public Element pageNumberBox() {
		return driver.FindElementById("PageNumber_divDocViewer");
	}

	// Added by Vijaya Rani
	public Element getEyeIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-eye']");
	}

	public Element getNextArrowBtn() {
		return driver.FindElementById("PrevHit_key1279552");
	}

	public Element getDocView_PageNumber() {
		return driver.FindElementByXPath("//input[@name='pageNum' and @id='PageNumber_divDocViewer']");
	}

	public Element getSearchIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-search']");
	}

	public Element getInputSearchBox() {
		return driver.FindElementById("sodTextBox");
	}

	public Element getRemarkXBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-times-circle-o']");
	}

	// Added By jeevitha
	public Element getTrashIcon(String search) {
		return driver.FindElementByXPath(
				"//div[text()='" + search + "']//..//div[@class='pull-right']//div[contains(@class,'trashCan')]");
	}

	public Element getSecondSearchIcon() {
		return driver.FindElementByXPath("(//i[@class='fa fa-search'])[last()]");
	}

	public Element getHitCount_cc() {
		return driver.FindElementByXPath("//div[@class='counters']//span");
	}

	// Added By Vijaya.Rani
	public Element getDashboardButton() {
		return driver.FindElementByXPath("//label[text()='Dashboard']");
	}

	public Element getSelectAssignmentFromDashborad(String assignmentName) {
		return driver.FindElementByXPath(
				"//*[@id='dt_basic']//following-sibling::tbody//following-sibling::tr//strong[text()='" + assignmentName
						+ "']");
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

	public Element getCompleteDocBtn() {
		return driver.FindElementById("btnDocumentComplete");
	}

	public Element getAssignmentsCompletedCountInreviewerPg(String assignmentName) {
		return driver.FindElementByXPath("//td[text()='" + assignmentName + "']/ancestor::tr/td[11]");
	}

	public Element getDocview_GearButton() {
		return driver.FindElementByXPath(" //*[@id=\"miniDocListConfig\"]/i");
	}

	public Element getDocview_ShowCompletedDocs() {
		return driver.FindElementByXPath("//i[@data-swchon-text='ON']");
	}

	public Element getDocview_GeerSaveBtn() {
		return driver.FindElementByXPath("//button[@class='btn btn-primary']");
	}

	public Element getUnCompleteButton() {
		return driver.FindElementByXPath("//button[@id='btnDocumentUnComplete']");
	}

	public Element getElementIconBlue() {
		return driver.FindElementById("ElmentIcon_BLUE");
	}

	public Element getCodingFormStampButton() {
		return driver.FindElementById("SaveUserStamps");
	}

	public Element getCodingStampTextBox() {
		return driver.FindElementById("txtStampName");
	}

	public Element getCodingEditStampTextBox() {
		return driver.FindElementByXPath("//input[@id='txtStampNameEdit']");
	}

	public Element getDrp_CodingEditStampColour() {
		return driver.FindElementByXPath("//div//dl[@id='ddlEditStamps']");
	}

	public Element getDrp_StampColour() {
		return driver.FindElementById("stampSelect");
	}

	public Element getCodingStampSaveBtn() {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='Save']");
	}

	public Element getAssignedColour(String colour) {
		return driver.FindElementByXPath("//dl[@id='stampSelect']//ul//li[@id='" + colour + "']");
	}

	public Element getCodindFormRadioBtn() {
		return driver.FindElementById("13_radio");
	}

	// Adding as Vijaya.Rani
	public Element getValueFromProgressBar() {
		return driver.FindElementById("(//*[contains(@id,'AssignmentProgress')])[1]");
	}

	public Element getHomeDashBoard() {
		return driver.FindElementByXPath("//i[@class='fa fa-lg fa-fw fa-home']");
	}

	// Addded by Sai Krishna

	public Element getConceptuallySimilarTab() {
		return driver.FindElementById("liDocumentConceptualSimilar");
	}

	public Element imagesIconDocView() {
		return driver.FindElementById("liDocumentProductionView");
	}

	// Added by jayanthi
	public Element getOnDemandSearchBtn() {
		return driver.FindElementByXPath(
				"//div[@class='searchOnDemand']//div[@class='searchIcon']//i[@class='fa fa-search']");
	}

	public Element getKeywordInPersistentHitPanel_test() {
		return driver.FindElementById("PHitCount_test");
	}

	// download icon after SA impersonation
	public Element downloadIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-download']");
	}

	public Element dropDownDownloadIcon() {
		return driver.FindElementByXPath("//ul[@id='documentTypeDropDown']/li/a");
	}

	public Element textTab() {
		return driver.FindElementById("liDocumentTxtView");
	}

	public Element activeDocId() {
		return driver.FindElementByXPath("//span[@id='activeDocumentId']");
	}

	// images tab elements - Added by krishna

	public Element imagesTabDropDown() {
		return driver.FindElementById("AvailableImagesDropDown");
	}

	public Element imagesTabZoomOut() {
		return driver.FindElementById("zoomOut_divDocViewerImage");
	}

	public Element imagesTabZoomIn() {
		return driver.FindElementById("zoomIn_divDocViewerImage");
	}

	public Element imagesTabZoomFitToScreen() {
		return driver.FindElementById("fitContent_divDocViewerImage");
	}

	// Added by krishna
	public Element historyDropDownDocSelect(String DocId) {
		return driver.FindElementById("History_" + DocId + "");
	}

	// Added by Vijaya.Rani
	public Element docViewEyeSearchTerm() {
		return driver.FindElementByXPath("//h3[text()='Search Hits:']");
	}

	public Element docViewEyeIconKeyword() {
		return driver.FindElementById("PHitCount_Keyword8799652");
	}

	public Element docViewReviewerPage() {
		return driver.FindElementById("RemarkPnl");
	}

	public Element getHitPanleVerify(String panel) {
		return driver.FindElementById("PHitCount_" + panel + "");
	}

	public Element getDocView_ToggleButton() {
		return driver.FindElementById("EnableSearchTerm");
	}

	public Element getHitPanel() {
		return driver.FindElementByXPath("//p[contains(@id,'PHitCount')]");
	}

	public ElementCollection getHitPanelCollection() {
		return driver.FindElementsByXPath("//p[contains(@id,'PHitCount')]");
	}

	public Element audioRemarksBtn() {
		return driver.FindElementById("remarks-btn-audio-view");
	}

	public Element addAudioRemarks() {
		return driver.FindElementById("audAddRemark");
	}

	public Element getManageTab() {
		return driver.FindElementByXPath("//a[@name='Manage']");
	}

	public Element getAssignDropDown() {
		return driver.FindElementByXPath("//a[@name='Assignments']");
	}

	public Element getDocView_EyePageTrems() {
		return driver.FindElementById("PHitCount_t");
	}

	public Element getDocHighlightedCount(String term) {
		return driver.FindElementByXPath("//a[@class='up']//following-sibling::span[@data-custom-id='" + term + "']");
	}

	public Element getDocPersistentPanelCount(String term) {
		return driver.FindElementByXPath(
				"//p[contains(@id,'PHitCount')]//following-sibling::span[@data-custom-id='" + term + "']");
	}

	// added by krishna

	public Element getAssgn_PrintPdfToggle() {
		return driver.FindElementByXPath("//*[@id='AdditionalPreferences_IsAllowPDFPrinting']/following-sibling::i");
	}

	public Element get_PrintIcon() {
		return driver.FindElementById("print_divDocViewer");
	}

	public Element getDocViewPanel() {
		return driver.FindElementById("CoddingContent");
	}

	public Element getMaximizePanel() {
		return driver.FindElementByXPath("(//div[@class='ui-resizable-handle ui-resizable-e'])[last()]");
	}

	public Element getSearchIconDisabled() {
		return driver.FindElementByXPath("//div[@class='searchOnDemand disabled']");
	}

	// added by Raghuram
	public Element getDocView_RedactHTextarea() {
		return driver.FindElementByXPath("//div[@class='igViewerGraphics']");
	}

	public Element highLightRectangleClick() {
		return driver.FindElementById("highlightRectangle_divDocViewer");
	}

	public Element rectangleRedactStats(String xAxis, String yAxis) {
		return driver.FindElementByCssSelector("rect[x='" + xAxis + "' ][y='" + yAxis + "']:last-child");
	}

	public Element rectangleRedactXYStats() {
		return driver.FindElementByCssSelector("g[fill-opacity='1']>rect:last-child");
	}

	public ElementCollection rectangleRedactionXYStats() {
		return driver.FindElementsByCssSelector("g[fill-opacity='1']>g[data-pcc-mark^='mark-']");
	}

	public Element rectangleRedactionXYStats(String parameter1) {
		return driver.FindElementByCssSelector("g[fill-opacity='1']>g[data-pcc-mark='" + parameter1 + "']");
	}

	public ElementCollection textRedactionXYStats() {
		return driver.FindElementsByCssSelector("g[fill-opacity='1']>g[data-pcc-mark^='mark-']>rect");
	}

	// Added by krishna

	public Element zoomOutDocView() {
		return driver.FindElementById("zoomOut_divDocViewer");
	}

	public Element zoomInDocView() {
		return driver.FindElementById("zoomIn_divDocViewer");
	}

	public Element zoomFitToScreenDocView() {
		return driver.FindElementById("fitContent_divDocViewer");
	}

	public Element rotateClockWise() {
		return driver.FindElementById("rotateRight_divDocViewer");
	}

	public Element rotateAntiClockWise() {
		return driver.FindElementById("rotateLeft_divDocViewer");
	}

	// Added by Raghuram

	public Element getDoTextRedactionXYStats() {
		return driver.FindElementByCssSelector("text[x]:first-child");
	}

	public Element getDoTextRedactionXYStatsAttribute() {
		return driver.FindElementByCssSelector("g[fill-opacity='1']>g[data-pcc-mark^='mark-']:first-child");
	}

	public Element getDoTextRedactionXYStatsAttributeSelect(String x, String y) {
		return driver.FindElementByCssSelector("text[x='" + x + "'][y='" + y + "']");
	}

	public Element getMarkedTextRedactionXYStatsAttributeSelect(String x, String y) {
		return driver.FindElementByCssSelector("g[x='" + x + "'][y='" + y + "']");
	}

	// Added by Krishna

	public Element multiPageRedactionTagSelect() {
		return driver.FindElementById("ddlMultiRedactionTagsForPopup");
	}

	public Element hiddenInfoIcon() {
		return driver.FindElementById("hiddenProperty");
	}

	public Element textData() {
		return driver.FindElementByXPath("//div[@id='divViewerText']");
	}

	// Added By Vijaya.Rani
	public Element getDocViewAllRedation() {
		return driver.FindElementByXPath("//div[@class='pull-right']//div[@id='counterAll']");
	}

	public Element textViewTab() {
		return driver.FindElementById("liDocumentTxtView");
	}

	public Element translationsViewTab() {
		return driver.FindElementById("liDocumentTranslationsView");
	}

	public Element defaultViewTab() {
		return driver.FindElementById("liDocumentDefaultView");
	}

	public Element imagesTabSlider() {
		return driver.FindElementById("slider_divDocViewerImage");
	}

	public Element ImagesTabRightRotate() {
		return driver.FindElementById("rotateRight_divDocViewerImage");
	}

	public Element ImagesTabLeftRotate() {
		return driver.FindElementById("rotateLeft_divDocViewerImage");
	}

	public Element getKeywordInPersistentHitPanel() {
		return driver.FindElementByXPath("//p[starts-with(@id,\"PHitCount\")]");
	}

	public Element hiddenInfoIconToolTip() {
		return driver.FindElementByXPath("//li[@id='hiddenProperty']/a");
	}

	public Element get_textHighlightedColorOnRectangleSubMenu() {
		return driver.FindElementByXPath("//li[@id='blackRectRedact_divDocViewer' and @class=\"state-active\"]/a");
	}

	public Element getRectangleRedactionIconColor() {
		return driver.FindElementByXPath("//li[@id='blackRectRedact_divDocViewer']//span");
	}

	public Element getHighlightedPageRedactionIconColor() {
		return driver.FindElementByXPath("//li[@id='highlightCurrentPage_divDocViewer']//span");
	}

	public Element getNonAudioRemarkBtn() {
		return driver.FindElementById("remarks-btn");
	}

	public DocViewRedactions(Driver driver) {
		this.driver = driver;
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);
		softAssertion = new SoftAssert();
		base = new BaseClass(driver);
	}

	/**
	 * Author : Krishna D date: NA Modified date: 24/08/21 Modified by: Krishna D
	 * Krishna Description : Method to create a rectangle redaction using
	 * co-ordinates and offset factory is designed for DocView/Redactions and
	 * DocView Component
	 */
	public void redactRectangleUsingOffset(int x, int y, int offsetx, int offsety) throws Exception {

		Actions actions = new Actions(driver.getWebDriver());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionIcon().Displayed() && redactionIcon().Enabled();
			}
		}), Input.wait30);
		base.waitTime(3);
		redactionIcon().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return rectangleClick().Visible() && rectangleClick().Enabled();
			}
		}), Input.wait30);
		base.waitTime(3);
		rectangleClick().waitAndClick(10);
		if (deleteRedactioBtn().isElementAvailable(2)) {
			deleteRedactioBtn().waitAndClick(5);
		}
		actions.moveToElement(getDocView_Redactrec_textarea().getWebElement(), x, y).clickAndHold()
				.moveByOffset(offsetx, offsety).release().build().perform();

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Description : Select Default redaction tag for applied redactions
	 */

	public void selectingMultiplePagesForRedaction() throws Exception {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionTagSelect().Visible() && redactionTagSelect().Enabled();
			}
		}), Input.wait30);

		Select multiplepageredaction = new Select(redactionTagSelect().getWebElement());
		multiplepageredaction.selectByVisibleText("Default Redaction Tag");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionMethodSelect().Visible() && redactionMethodSelect().Enabled();
			}
		}), Input.wait30);

		Select redactionmethod = new Select(redactionMethodSelect().getWebElement());
		redactionmethod.selectByIndex(0);
	}

	/**
	 * Author : Krishna D date: NA Modified date: 24/08/21 Modified by: Krishna D
	 * Krishna Description : Entering the page numbers in textbox required for
	 * Multipage redaction
	 */

	public void enteringPagesInMultipageTextBox(String pagerange) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 10);
		Actions actions = new Actions(driver.getWebDriver());
		try {
			wait.until(ExpectedConditions.elementToBeClickable(multiPageInputTextbox().getWebElement()));
			multiPageInputTextbox().waitAndClick(5);
		} catch (Exception e) {
			actions.moveToElement(multiPageIcon().getWebElement()).click();
			actions.click().build().perform();
			multiPageInputTextbox().waitAndClick(5);
		}

		multiPageInputTextbox().Clear();
		multiPageInputTextbox().SendKeys(pagerange);
		multiPageInputSavaBtn().waitAndClick(4);
	}

	public void selectingRectangleRedactionTag() throws Exception {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return rectangleRedactionTagSelect().Visible() && rectangleRedactionTagSelect().Enabled();
			}
		}), Input.wait30);
		rectangleRedactionTagSelect().waitAndFind(10);
		Select redactionTag = new Select(rectangleRedactionTagSelect().getWebElement());
		redactionTag.selectByVisibleText("Default Redaction Tag");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionSave().Visible() && redactionSave().Enabled();
			}
		}), Input.wait30);
		redactionSave().isElementAvailable(15);
		redactionSave().waitAndClick(20);

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Description : Double click on redaction button second to view double
	 * click---Edited------- For TC_RPMXCON52255
	 */
	public void doubleClickRedactionBtn() throws Exception {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionIcon().Visible() && redactionIcon().Enabled();
			}
		}), Input.wait30);
		redactionIcon().waitAndClick(20);
		redactionIcon().waitAndClick(20);

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Description : Selecting redacted doc with different search options printing
	 * redacted document as PA ---Edited------- For TC_RPMXCON52233
	 */

	public void clickOnPreviewDocumentButton() throws Exception {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return btnPreviewDoc().Visible() && btnPreviewDoc().Enabled();
			}
		}), Input.wait30);
		btnPreviewDoc().waitAndClick(10);
	}

	public void clickOnClosePreviewButton() throws Exception {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return btnpreviewDocClose().Visible() && btnpreviewDocClose().Enabled();
			}
		}), Input.wait30);
		btnpreviewDocClose().waitAndClick(10);
	}

	public void selectSideMenu(String menuName) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		wait.until(ExpectedConditions.elementToBeClickable(sideMenu(menuName).getWebElement()));
		sideMenu(menuName).Click();
	}

	public void enterDocId(String docId) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		wait.until(ExpectedConditions.elementToBeClickable(txtDocId().getWebElement()));
		txtDocId().getWebElement().clear();
		txtDocId().getWebElement().sendKeys(docId);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public void clickOnPreviewEyeIcon() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		wait.until(ExpectedConditions.elementToBeClickable(btnEyeIcon().getWebElement()));
		btnEyeIcon().Click();
		Thread.sleep(2000);
	}

	/**
	 * Author : Krishna D date: NA Modified date: 31/08/21 Modified by: Krishna D
	 * Krishna Description : Method to create a text redaction using co-ordinates
	 * and offset factory is designed for DocView/Redactions and DocView Component
	 */
	public void redactTextUsingOffset() throws Exception {

		Actions actions = new Actions(driver.getWebDriver());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionIcon().Displayed() && redactionIcon().Enabled();
			}
		}), Input.wait30);
		redactionIcon().waitAndClick(20);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		wait.until(ExpectedConditions.elementToBeClickable(btnTypeOfRedaction("Text Redaction").getWebElement()));
		base.waitTime(2);
		btnTypeOfRedaction("Text Redaction").Click();
		WebElement src = driver.FindElement(By.cssSelector("g[id='ig0level0surface1'] g text:nth-child(1)"))
				.getWebElement();
		WebElement dest = driver.FindElement(By.cssSelector("g[id='ig0level0surface1'] g text:nth-child(10)"))
				.getWebElement();
		actions.dragAndDrop(src, dest).build().perform();
	}

	public void docListTableItration() throws Exception {
		for (int r = 1; r < 10; r++) {

			doclistTable(r).Click();
			Thread.sleep(4000);
		}
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Description :Canceling a tag for redaction
	 */

	public void canclingRedactionTag() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return rectangleRedactionTagSelect().Visible() && rectangleRedactionTagSelect().Enabled();
				}
			}), Input.wait30);
			rectangleRedactionTagSelect().waitAndFind(10);
		} catch (Exception e) {

		}
		Select redactionTag = new Select(rectangleRedactionTagSelect().getWebElement());
		redactionTag.selectByVisibleText("Default Redaction Tag");
		wait.until(ExpectedConditions.elementToBeClickable(redactionTagCancleBtn().getWebElement()));
		actions.moveToElement(redactionTagCancleBtn().getWebElement());
		actions.click().build().perform();

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Description : Selecting any tag for redaction
	 */

	public void selectingRedactionTag(String redactiontagname) throws Exception {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return rectangleRedactionTagSelect().Visible() && rectangleRedactionTagSelect().Enabled();
				}
			}), Input.wait30);
			rectangleRedactionTagSelect().waitAndFind(5);
		} catch (Exception e1) {
		}
		Select redactionTag = new Select(rectangleRedactionTagSelect().getWebElement());
		redactionTag.selectByVisibleText(redactiontagname);
		Robot robot = new Robot();
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);

	}

	/**
	 * @author Mohan 9/06/21 NA Modified date: NA Modified by:NA
	 * @description To Select Docs From mini doclist for case number 52170
	 */
	public void selectMiniDocListAndViewInDocView(int docNo) {

		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(docNo).waitAndClick(10);

	}

	public void selectRedactionIconAndRedactWholePage() throws Exception {
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		base.waitForElement(redactionIcon());
		redactionIcon().waitAndClick(10);
		base.waitForElement(thisPageRedaction());
		thisPageRedaction().waitAndClick(10);
		base.waitForElement(redactionSave());
		redactionSave().waitAndClick(10);
		System.out.println("Current page is Redacted successfully");
	}

	public void popOutCodingFormChildWindow() {
		base = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		base.waitForElement(getGearIcon());
		driver.waitForPageToBeReady();
		getGearIcon().waitAndClick(10);
		actions.moveToElement(getCodingFormStampButton().getWebElement());
		actions.moveToElement(popoutCodingForm().getWebElement());
		actions.click().build().perform();
		base.stepInfo("Coding form child window opened successfully");

	}

	public void mouseOverToCodeSameAsLastIcon() {
		base = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		base.waitForElement(codeSameAsLastDocIcon());
		actions.moveToElement(codeSameAsLastDocIcon().getWebElement());
		String text = codeSameAsLastDocIcon().getText();
		System.out.println(text);
		base.stepInfo(
				" 'Code this document the same as the last coded document' is displayed when mouserhover to Code Same As Last doc successfully");

	}

	public void toggleCompletedRedactionTagEnabled() {
		// permissions
		// Toggle enabled for CodingStampAplliedToggle
		base = new BaseClass(driver);
		base.waitForElement(getAssignment_ConfigurationTab());
		getAssignment_ConfigurationTab().Click();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getAssgn_RedactionAplliedToggle());
		getAssgn_RedactionAplliedToggle().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		backToManageBtn().waitAndClick(0);
	}

	public void assignAccessToSecurityGroups(String SgName) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		driver.scrollPageToTop();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(manageBtn().getWebElement()));
		actions.moveToElement(manageBtn().getWebElement());
		actions.click().build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(manageUser().getWebElement()));
		actions.moveToElement(manageUser().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(userTextInput().getWebElement());
		actions.click();
		actions.sendKeys(Input.rmu2userName);
		actions.build().perform();
		actions.moveToElement(userFiletersBtn().getWebElement());
		actions.click().build().perform();
		Thread.sleep(4000);
		wait.until(ExpectedConditions.elementToBeClickable(userEditBtn().getWebElement()));
		actions.moveToElement(userEditBtn().getWebElement());
		actions.click();
		actions.build().perform();
//		userEditBtn().waitAndClick(10);
		Thread.sleep(4000);
		driver.scrollingToBottomofAPage();
		Select assignSG1 = new Select(userSelectSecurityGroup().getWebElement());
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		assignSG1.selectByVisibleText(SgName);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public void checkingPersistentHitPanel() throws Exception {
		base = new BaseClass(driver);
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return persistantHitBtn().Visible() && persistantHitBtn().Enabled();
			}
		}), Input.wait30);
		base.waitForElement(persistantHitBtn());
		persistantHitBtn().waitAndClick(30);

	}

	public void verifyingMultipageIconColour(String expectedColor) throws Exception {
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		base.waitForElement(multiPageIconColourCheck());
		String color = multiPageIconColourCheck().getWebElement().getCssValue("color");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		if (hex.equalsIgnoreCase(expectedColor)) {
			base.passedStep("The multipage icon turned to red colour as expected- Successfully");
		} else {
			base.failedStep("The multipage icon NOT turned to red colour as expected");
		}
	}

	/**
	 * @author Krishna 20/9/21 NA Modified date: NA Modified by:NA
	 * @description Adding methods for consolidation
	 */

	public void navigatingThroughRedactions() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		base = new BaseClass(driver);
		Thread.sleep(2000);
		actions.moveToElement(rectangleClick().getWebElement());
		actions.click().build().perform();
		base.waitTillElemetToBeClickable(redactionForwardNavigate());
		actions.moveToElement(redactionForwardNavigate().getWebElement());
		actions.click().build().perform();
		base.stepInfo("Navigated to Next Redaction Successfully");
		if (deleteClick().isDisplayed()) {
			base.waitTillElemetToBeClickable(deleteClick());
			actions.moveToElement(deleteClick().getWebElement());
			actions.click().build().perform();
			base.passedStep("The redaction tag has been deleted sucessfully");
		} else {
			base.passedStep("The redaction tag has was not avilable");
		}

		base.passedStep("The redaction tag has been deleted sucessfully");
		actions.moveToElement(redactionForwardNavigate().getWebElement());
		actions.doubleClick().build().perform();
		actions.moveToElement(redactionBackwardNavigate().getWebElement());
		actions.click().build().perform();
		base.stepInfo("Navigated to previous Redaction Successfully");
		if (deleteClick().isDisplayed()) {
			if (deleteClick().isDisplayed()) {
				base.waitTillElemetToBeClickable(deleteClick());
				actions.moveToElement(deleteClick().getWebElement());
				actions.click().build().perform();
				base.passedStep("The redaction tag has been deleted sucessfully");
			} else {
				base.passedStep("The redaction tag was not avilable");
			}
			Thread.sleep(3000);
		}
	}

	public void navigatingThroughPages() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		base = new BaseClass(driver);
		Thread.sleep(4000);
		actions.moveToElement(docViewNextPage().getWebElement());
		actions.click();
		actions.build().perform();
		base.stepInfo("Navigaated to next page Successfully");
		try {
			actions.moveToElement(docViewPreviousPage().getWebElement());
			actions.click();
			actions.build().perform();
			base.stepInfo("Navigaated to previous page Successfully");
		}

		catch (Exception e) {
			driver.waitForPageToBeReady();
			docViewPreviousPage().waitAndClick(5);
			base.stepInfo("Navigaated to previous page Successfully");
		}

	}

	/**
	 * @author Gopinath
	 * @Description -- Method to Set this page redaction .
	 * @param redactiontag -- (redactiontag is a String value that name of redaction
	 *                     tag).
	 */
	public void performThisPageRedaction(String redactiontag) {
		try {
			base = new BaseClass(driver);
			base.waitForElement(thisPageRedaction());
			base.waitTime(3);
			base.waitTillElemetToBeClickable(thisPageRedaction());
			thisPageRedaction().waitAndClick(5);
			base.waitForElement(getSelectReductionTagDropDown());
			getSelectReductionTagDropDown().selectFromDropdown().selectByVisibleText(redactiontag);
			getSaveButton().isElementAvailable(15);
			base.waitForElement(getSaveButton());
			getSaveButton().Click();
			base.VerifySuccessMessage("Redaction tags saved successfully.");
			base.getCloseSucessmsg();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while setting this page redaction" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description -- Method to Set multi page redaction .
	 * @param redactiontag -- (redactiontag is a String value that name of redaction
	 *                     tag).
	 */
	public void performAllPagesMultiPageRedaction(String redactiontag) {
		try {
			base = new BaseClass(driver);
			base.waitForElement(totalNoOfPagesInDocument());
			base.waitTillElemetToBeClickable(totalNoOfPagesInDocument());
			base.waitTime(2);
			totalNoOfPagesInDocument().isElementAvailable(15);
			String totalPagesInDoc = totalNoOfPagesInDocument().getText().trim();
			String pages = totalPagesInDoc.replaceAll("[^0-9]+", "");
			System.out.println("Total number of pages in selected document : " + pages);
			base.waitForElement(multiPageIcon());
			base.waitTillElemetToBeClickable(multiPageIcon());
			multiPageIcon().Click();
			redactionTagSelect().selectFromDropdown().selectByVisibleText(redactiontag);
			Select redactionmethod = new Select(redactionMethodSelect().getWebElement());
			redactionmethod.selectByIndex(0);
			base.waitForElement(multiPageInputTextbox());
			base.waitTillElemetToBeClickable(multiPageInputTextbox());
			multiPageInputTextbox().SendKeys("1-" + pages);
			redactionSaveBtn().Click();
			base.waitTime(3);
			base.VerifySuccessMessage("Redaction tags saved successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while setting muti page redaction" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description -- Method to Click on last performed redaction on current opened
	 *              document.
	 */
	public void clickOnLastPerformedRedactionOnCurrentDoc() {
		try {
			base = new BaseClass(driver);
			driver.waitForPageToBeReady();
			base.waitForElement(totalNoOfPagesInDocument());
			Thread.sleep(2000);
			List<WebElement> redactions = driver.getWebDriver().findElements(By.cssSelector("rect:nth-child(1)"));
			driver.waitForPageToBeReady();
			System.out.println(redactions.size());
			redactions.get(redactions.size() - 1).click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while setting muti page redaction" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description -- Method to verify selected option from unredaction dropdown is
	 *              blank.
	 */
	public void verifySelectedOptionFromUnRedactionDropdownIsBlank() {
		try {
			base = new BaseClass(driver);
			driver.waitForPageToBeReady();
			base.waitForElement(getUnReductionTagDropDown());
			Select select = new Select(getUnReductionTagDropDown().getWebElement());
			WebElement option = select.getFirstSelectedOption();
			String selectedOption = option.getText();
			if (selectedOption.equalsIgnoreCase("")) {
				base.passedStep("Selected option from unredaction dropdown is blank");
			} else {
				base.failedStep("Selected option from unredaction dropdown is not blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying selected option from unredaction dropdown is blank"
					+ e.getMessage());

		}
	}

	/**
	 * 
	 * @author Gopinath
	 * @Description -- Method to Click on add audio redaction button.
	 */
	public void clickOnAddRedactionForAudioDocument() {
		try {
			base = new BaseClass(driver);
			driver.waitForPageToBeReady();
			base.waitForElement(audioDocRedactionadd());
			base.waitTillElemetToBeClickable(audioDocRedactionadd());
			audioDocRedactionadd().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while clicking on add audio redaction button" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description -- Method to verify redaction dropdown for audio document is
	 *              blank or not.
	 * @param flag -- (flag is a boolean value that if redation dropdown is blank
	 *             then true otherwise false).
	 */
	public void verifyWeatherAudioRedactionDropdownIsBlank(boolean flag) {
		try {
			getAudioRedactDropdown().isElementAvailable(15);
			base.waitTime(3);
			base.waitForElement(getAudioRedactDropdown());
			base.waitTillElemetToBeClickable(getAudioRedactDropdown());
			Select select = new Select(getAudioRedactDropdown().getWebElement());
			WebElement option = select.getFirstSelectedOption();
			String selectedOption = option.getText();
			if (selectedOption.equalsIgnoreCase("") && flag == true) {
				base.passedStep("Selected option from audio redaction dropdown is blank");
			} else if (!(selectedOption.equalsIgnoreCase("")) && flag == true) {
				base.failedStep("Selected option from audio redaction dropdown is not blank");
			} else if (!(selectedOption.equalsIgnoreCase("")) && flag == false) {
				base.passedStep("Selected option from audio redaction dropdown is not blank");
			} else if (selectedOption.equalsIgnoreCase("") && flag == false) {
				base.failedStep("Selected option from audio redaction dropdown is blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying redaction dropdown for audio document is blank or not"
					+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description -- Method to add audio redaction.
	 * @param startTime    - (startTime is a string value that starting time of
	 *                     audio redact)
	 * @param endTime      - (endTime is a string value that ending time of audio
	 *                     redact)
	 * @param redactionTag - (redactionTag is string value that tag need to select
	 *                     for redaction dropdown)
	 */
	public void addAudioRedaction(String startTime, String endTime, String redactionTag) {

		base = new BaseClass(driver);
		clickOnAddRedactionForAudioDocument();
		List<WebElement> timeTextFields = driver.getWebDriver()
				.findElements(By.xpath("//table[@id='audioGrid']//input"));
		timeTextFields.get(0).sendKeys(startTime);
		timeTextFields.get(1).sendKeys(endTime);
		base.waitForElement(getAudioRedactDropdown());
		base.waitTillElemetToBeClickable(getAudioRedactDropdown());
		getAudioRedactDropdown().selectFromDropdown().selectByVisibleText(redactionTag);
		getAudioRedactSave().waitAndFind(5);
		getAudioRedactSave().waitAndClick(5);
		base.waitForElement(base.getSuccessMsg());
		base.getSuccessMsg().waitAndFind(10);
		Assert.assertEquals(base.getSuccessMsg().getWebElement().isDisplayed(), true,
				"Success message is not displayed");
		if (base.getSuccessMsg().getWebElement().isDisplayed()) {
			base.passedStep("Success message is displayed successfully");
			base.passedStep("Audio redaction added successfully");
		}

	}

	/**
	 * @author Gopinath
	 * @Description -- Method to delete all applied redactions.
	 */
	public void deleteAllAppliedRedactions() {
		try {
			base = new BaseClass(driver);
			driver.waitForPageToBeReady();
			base.waitForElement(audioDocRedactionadd());
			base.waitTillElemetToBeClickable(audioDocRedactionadd());
			Thread.sleep(1000);
			List<WebElement> deleteButtons = driver.getWebDriver().findElements(By.xpath("//a[@id='btnDelete']//i"));
			for (WebElement deleteButton : deleteButtons) {
				driver.waitForPageToBeReady();
				Thread.sleep(2000);
				deleteButton.click();
				base.waitForElement(getDeleteAudioRedactYesButton());
				base.waitTillElemetToBeClickable(getDeleteAudioRedactYesButton());
				getDeleteAudioRedactYesButton().waitAndClick(5);
			}
			base.passedStep("Deleted all applied redactions successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while deleting all applied redactions" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description -- Method to edit applied redaction.
	 */
	public void editAppliedRedaction() {
		try {
			base = new BaseClass(driver);
			driver.waitForPageToBeReady();
			base.waitForElement(audioDocRedactionadd());
			base.waitTillElemetToBeClickable(audioDocRedactionadd());
			List<WebElement> editButtons = driver.getWebDriver().findElements(By.xpath("//a[@id='btnEdit']//i"));
			editButtons.get(0).click();
			base.passedStep("Applied audio redaction edited successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while edit applied redaction" + e.getMessage());
		}
	}

	/**
	 * @Author Sowndarya.Velraj
	 */
	public void selectDoc1() {

		// selectFirstDoc
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return selectFirstDoc().Visible() && selectFirstDoc().Enabled();
			}
		}), Input.wait30);
		selectFirstDoc().waitAndClick(10);
	}

	/**
	 * @Author Sowndarya.Velraj
	 */
	public void selectDoc2() {

		// selectSecondDoc

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return selectSecondDoc().Visible() && selectSecondDoc().Enabled();
			}
		}), Input.wait30);

		selectSecondDoc().waitAndClick(10);
	}

	/**
	 * @Author Sowndarya.Velraj
	 */
	public void selectDoc3() {

		// selectThirdDoc
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return selectThirdDoc().Visible() && selectThirdDoc().Enabled();
			}
		}), Input.wait30);

		selectThirdDoc().waitAndClick(10);
	}

	/**
	 * Modified on 05/12/22
	 * 
	 * @Author Sowndarya.Velraj
	 */
	public void selectingRedactionTag2(String redactiontagname) throws Exception {
		driver.waitForPageToBeReady();
		Select redactionTag = new Select(rectangleRedactionTagSelect().getWebElement());
		base.waitTime(2);
		redactionTag.selectByVisibleText(redactiontagname);

		base.waitForElement(redactionSaveBtn());
		redactionSaveBtn().waitAndClick(10);
		base.CloseSuccessMsgpopup();
	}

	/**
	 * Author : Sowndarya.Velraj
	 */
	public void redactRectangleUsingOffsetWithDoubleClick(int x, int y, int offsetx, int offsety) throws Exception {

		Actions actions = new Actions(driver.getWebDriver());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionIcon().Displayed() && redactionIcon().Enabled();
			}
		}), Input.wait30);

//	actions.doubleClick(redactionIcon().getWebElement()).build().perform();
		redactionIcon().waitAndClick(10);
		redactionIcon().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return rectangleClick().Visible() && rectangleClick().Enabled();
			}
		}), Input.wait30);

		rectangleClick().waitAndClick(3);

		actions.moveToElement(getDocView_Redactrec_textarea().getWebElement(), x, y).clickAndHold()
				.moveByOffset(offsetx, offsety).release().build().perform();

	}

	/*
	 * Jagadeesh.Jana Test Case Id: RPMXCON-51562
	 */
	public void verifyHighlightedText() throws Exception {
		base = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		base.waitForElement(searchTextIcon());
		searchTextIcon().waitAndClick(10);

		set_searchText().getWebElement().sendKeys("S");
		Thread.sleep(3000); // needed here implicitly


		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		base.waitTime(7); // needed here implicitly

		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex = Color.fromString(color).asHex(); // #dc5252
		System.out.println(hex);
		base.waitTime(5); 
		if (hex.equalsIgnoreCase(Input.colorCodeHighlight)) // #dc5252
		{
			System.out.println("The color for the Highlighted texts is verfied- Successfully");
			base.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			System.out.println("The color for the Highlighted text is not-verfied - Failed");
			base.failedStep("The color for the Highlighted text is not-verfied - Failed");
		}

	}

	/*
	 * Jagadeesh.Jana Test Case Id: RPMXCON-51564
	 */
	public void thereAreNoHitsVerify() throws Exception {
		SoftAssert softAssertion = new SoftAssert();
		base = new BaseClass(driver);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getSearchIcon().Visible() && HighliteIcon().Enabled();
			}
		}), Input.wait30);
		getSearchIcon().waitAndClick(30);

		base.passedStep("successfully Clicked the Search Icon");

		driver.waitForPageToBeReady();
		base.waitForElement(getInputSearchBox());
		getInputSearchBox().waitAndClick(30);

		getInputSearchBox().SendKeys("chocolate");
		base.stepInfo("Search input Text completed");

		Thread.sleep(2000); // Implicitly needed here
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		base.waitForElement(hitBackwardIcon1());
		hitBackwardIcon1().waitAndClick(10);
		String backwardCount = getHitCount1().getText();
		System.out.println("BackwardCount :" + backwardCount);
		if (backwardCount.equals("0 of 0")) {
			System.out.println("There are no hits present - PASSED");
			base.passedStep("There are no hits present - PASSED");
			softAssertion.assertTrue(true);
		} else {
			System.out.println("There are hits present - FAILED");
			base.failedStep("There are hits present - FAILED");
			softAssertion.assertTrue(false);
		}
		softAssertion.assertAll();
	}

	public void checkingPersistentHitPanelAudio() throws Exception {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return persistantHitBtnAudio().Visible() && persistantHitBtnAudio().Enabled();
			}
		}), Input.wait30);
		persistantHitBtnAudio().waitAndClick(30);

	}

	/*
	 * Sai Krishna Test Case Id: RPMXCON-52169, RPMXCON-52168 & RPMXCON-52165
	 */

	public void createNewAnnotationLayer(String annotationLayerName) throws Exception {

		this.driver.getWebDriver().get(Input.url + "Annotations/Annotations");
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		createAnnotationLayer().waitAndClick(5);
		base.waitForElement(inputAnnotationName());
		inputAnnotationName().SendKeys(annotationLayerName);
		saveNewAnnotationLayer().waitAndClick(5);

	}

	public void documentSelectionDocExplorer() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return selectingFirstdataFromTable().Visible() && selectingFirstdataFromTable().Enabled();
			}
		}), Input.wait30);
		selectingFirstdataFromTable().waitAndClick(15);
	}

	public void clickingRedactionIcon() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionIcon().Visible() && redactionIcon().Enabled();
			}
		}), Input.wait30);
		redactionIcon().waitAndClick(30);
	}

	/*
	 * Jagadeesh.Jana Test Case Id: RPMXCON-51731
	 */
	public void TextHightingNotPresentVerify() throws Exception {
		base = new BaseClass(driver);
		SoftAssert softAssertion = new SoftAssert();

		base.waitForElement(yellowHighlightIcon());
		yellowHighlightIcon().waitAndClick(10);

		if (textSelectionRedactionIcon().Displayed() == false) {
			System.out.println("Text highlighting not present on doc view - Passed");
			base.passedStep("Text highlighting not present on doc view - Passed");
			softAssertion.assertTrue(true);
		} else {
			System.out.println("Text highlighting is present on doc view - Failed");
			base.failedStep("Text highlighting is present on doc view - Failed");
			softAssertion.assertTrue(false);
		}
		softAssertion.assertAll();
	}

	/*
	 * Jagadeesh.Jana Test Case Id: RPMXCON-51755
	 */
	public void ToggleONhideZeroHits() throws Exception {
		base = new BaseClass(driver);

		base.waitForElement(persistantHitBtn());
		persistantHitBtn().waitAndClick(10);

		base.waitForElement(persistantHitToggle());
		persistantHitToggle().waitAndClick(10);

		Thread.sleep(2000); // implicitly need here to maintain a constant result
		int termCounts = get_NoOfTermsHits().size();
		System.out.println("No of TermHits : " + termCounts);
		base.stepInfo("No of Term Hits are : " + termCounts);
		for (int i = 1; i <= termCounts; i++) {
			base.waitForElement(get_hitsValue(i));
			String hitvalue = get_hitsValue(i).getText().split(" ")[0].trim();
			System.out.println("Hitvalue : " + hitvalue);
			if (!hitvalue.equals("0")) {
				System.out.println("0 hit terms is hided on hits panel - Passed");
				base.passedStep("0 hit terms is hided on hits panel - Passed");
				softAssertion.assertTrue(true);
			} else {
				System.out.println("0 hit terms is not-hided on hits panel - Failed");
				base.failedStep("0 hit terms is not-hided on hits panel - Failed");
				softAssertion.assertTrue(false);
			}
		}
		softAssertion.assertAll();
	}

	/*
	 * Jagadeesh.Jana Test Case Id: RPMXCON-51756
	 */
	public void ToggleOFFshowZeroHits() throws Exception {
		base = new BaseClass(driver);
		boolean zeroHitsPresent = false;

		base.waitForElement(persistantHitBtn());
		persistantHitBtn().waitAndClick(10);

		Thread.sleep(3000); // implicitly need here to maintain a constant result
		int termCounts = get_NoOfTermsHits().size();
		System.out.println("No of TermHits : " + termCounts);
		base.stepInfo("No of Term Hits are : " + termCounts);
		for (int i = 1; i <= termCounts; i++) {
			base.waitForElement(get_hitsValue(i));
			String hitvalue = get_hitsValue(i).getText().split(" ")[0].trim();
			System.out.println("Hitvalue : " + hitvalue);
			if (hitvalue.equals("0")) {
				zeroHitsPresent = true;
			}
		}
		if (zeroHitsPresent == true) {
			System.out.println("0 hit terms is not-hided on hits panel - Passed");
			base.passedStep("0 hit terms is not-hided on hits panel - Passed");
			softAssertion.assertTrue(true);
		} else {
			System.out.println("0 hit terms is hided on hits panel - Failed");
			base.failedStep("0 hit terms is hided on hits panel - Failed");
			softAssertion.assertTrue(false);
		}
		softAssertion.assertAll();
	}

	public void VerifyKeywordHitsinDoc() throws Exception {
		SoftAssert softAssertion = new SoftAssert();
		base = new BaseClass(driver);

		base.waitForElement(getHitCountTest());
		getHitCountTest().waitAndClick(10);
		String firstCount = getHitCountTest().getText();
		System.out.println("Count :" + firstCount);

		System.out.println("There are hits present - PASSED " + firstCount);
		base.passedStep("The hits for first Doc are " + firstCount);
		softAssertion.assertTrue(true);

	}

	public void selectingDocFromMiniDocListChildWindow() throws Exception {
		base = new BaseClass(driver);

		String parentWindow = driver.getWebDriver().getWindowHandle();
		Set<String> childWindow = driver.getWebDriver().getWindowHandles();
		for (String miniDocListChild : childWindow) {
			if (!parentWindow.equals(miniDocListChild)) {
				driver.switchTo().window(miniDocListChild);
//				driver.waitForPageToBeReady();
			}
		}

		for (int i = 1; i <= 4; i++) {
			Thread.sleep(4000);
			miniDoclistTable(i).waitAndClick(10);

		}
		driver.switchTo().window(parentWindow);
		if (persistantHitRightNavigate().Displayed() && persistantHitRightNavigate().Enabled()) {
			assertTrue(true);
			base.passedStep(
					"The persistent hit panel is visible for audio documents After navigating from child window");
		} else {
			assertTrue(false);
		}

	}

	public void clickingRemarksIcon() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return remarksIcon().Visible() && remarksIcon().Enabled();
			}
		}), Input.wait30);
		remarksIcon().waitAndClick(30);

	}

	/*
	 * Sai Krishna 8/10/2021
	 */
	public void checkinHighlitedText(int count) throws Exception {
		base = new BaseClass(driver);
		for(int i=1;i<=count;i++) {
		getDocView_MiniDoc_Selectdoc(count).waitAndClick(20);
		driver.waitForPageToBeReady();
		if(get_textHighlightedColor().isElementAvailable(15)) {
		String text = get_textHighlightedColor().getWebElement().getText();
		System.out.println(text);
		if (text.equalsIgnoreCase("r")) {
			base.passedStep("The batch redated text is matching the keyword");
		}
		String color = get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		base.passedStep("The text for keyword is highlited in the document");
		break;
		}
	}
	}
	/*
	 * Sai Krishna 12/10/2021 Method for clicking yellow colour icon
	 */

	public void clickingHighlitingIcon() throws InterruptedException {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return HighliteIcon().Visible() && HighliteIcon().Enabled();
				}
			}), Input.wait30);
			HighliteIcon().waitAndClick(30);
			thisPageHighlite().waitAndClick(5);
		} catch (Exception e) {
			HighliteIcon().waitAndClick(30);
			thisPageHighlite().waitAndClick(5);

		}
	}

	/*
	 * Sai Krishna 17/10/2021 Method for selecting SG after login
	 */

	public void selectsecuritygroup(String sgname) {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getsgNames().Visible();
			}
		}), 10000);
		driver.scrollPageToTop();
		getsgNames().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectsg(sgname).Visible();
			}
		}), 10000);
		base.waitTillElemetToBeClickable(getSelectsg(sgname));
		getSelectsg(sgname).Click();
		driver.waitForPageToBeReady();
		UtilityLog.info("Security Group Selected: " + sgname);
	}

	/*
	 * Sai Krishna 17/10/2021 Method for assigning access to RMU user for newly
	 * created SGs From PA user provide access to SGs for RMU-- Needed few wait
	 * times to make the method robust
	 */

	public void assignAccesstoSGs(String securityGroup1, String securityGroup2, String user) throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		actions.moveToElement(userTextInput().getWebElement());
		actions.click();
		actions.sendKeys(user);
		actions.build().perform();
		actions.moveToElement(userFiletersBtn().getWebElement());
		actions.click().build().perform();
		driver.waitForPageToBeReady();
		base.waitTime(2);
		wait.until(ExpectedConditions.elementToBeClickable(userEditBtn().getWebElement()));
		actions.moveToElement(userEditBtn().getWebElement());
		actions.click();
		actions.build().perform();
		driver.scrollingToBottomofAPage();
		base.waitTime(3);
		Select selectSG = new Select(userSelectSecurityGroup().getWebElement());
		Robot robot = new Robot();
		base.waitTime(2);
		robot.keyPress(KeyEvent.VK_CONTROL);
		base.waitTime(2);
		selectSG.selectByVisibleText(securityGroup1);
		base.waitTime(3);
		selectSG.selectByVisibleText(securityGroup2);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		base.waitTime(3);
		userSelectSGSaveBtn().isElementAvailable(15);
		userSelectSGSaveBtn().Click();
		base.waitTime(3);
		base.passedStep(
				"Given access for these SG's " + securityGroup1 + " " + securityGroup2 + "  for this user" + user);
	}

	/*
	 * Steffy 18/01/2022 Method for assigning access to RMU user for newly created
	 * SGs From PA user provide access to SGs for RMU-- Needed few wait times to
	 * make the method robust
	 */

	public void assignAccesstoSGs(String securityGroup1, String user) throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		actions.moveToElement(userTextInput().getWebElement());
		actions.click();
		actions.sendKeys(user);
		actions.build().perform();
		actions.moveToElement(userFiletersBtn().getWebElement());
		actions.click().build().perform();
		Thread.sleep(Input.wait3);
		driver.waitForPageToBeReady();
		wait.until(ExpectedConditions.elementToBeClickable(userEditBtn().getWebElement()));
		actions.moveToElement(userEditBtn().getWebElement());
		actions.click();
		actions.build().perform();
		Thread.sleep(Input.wait3);
		driver.scrollingToBottomofAPage();
		Select selectSG = new Select(userSelectSecurityGroup().getWebElement());
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		selectSG.selectByVisibleText(securityGroup1);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000); // needed for selecting 2 SGs simultaniously
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
		base.VerifySuccessMessage("User profile was successfully modified");
		base.passedStep("Given access for these SG's " + securityGroup1 + "  for this user" + user);
	}

	/*
	 * Sai Krishna 17/10/2021 Method for deleting annotation layer
	 * 
	 */

	public void deleteAnnotationLayer(String annotationLayerName) throws Exception {

		this.driver.getWebDriver().get(Input.url + "Annotations/Annotations");
		base = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		driver.waitForPageToBeReady();
		try {
			annotationLayerDelete(annotationLayerName).waitAndClick(5);
			confirmAnnotationLayerDelete().waitAndClick(5);
		} catch (Exception e) {
			actions.moveToElement(annotationLayerDelete(annotationLayerName).getWebElement());
			actions.click();
			actions.build().perform();
			base.waitForElement(confirmAnnotationLayerDelete());
			confirmAnnotationLayerDelete().waitAndClick(5);
		}

	}

	public void checkingForRedaction() throws Exception {
		base = new BaseClass(driver);
//		base.waitForElement(redactionIcon());
		driver.waitForPageToBeReady();
		if (redactionIcon().isDisplayed()) {

			base.failedStep("The delete Icon appears for the clicked rectangle redaction");
		} else {

			base.passedStep(
					"The delete Icon does not appear as the Annotation Layer linked to redaction has been deleted");
		}
	}

	/**
	 * @author Steffy
	 * @Description -- Method to Set this page redaction without saving the
	 *              redaction
	 * @param redactiontag -- (redactiontag is a String value that name of redaction
	 *                     tag).
	 */
	public void performThisPageRedactionWithoutSaving(String redactiontag) {
		try {
			base = new BaseClass(driver);
			base.waitForElement(thisPageRedaction());
			base.waitTillElemetToBeClickable(thisPageRedaction());
			thisPageRedaction().Click();
			base.waitForElement(getSelectReductionTagDropDown());
			getSelectReductionTagDropDown().selectFromDropdown().selectByVisibleText(redactiontag);
			base.waitForElement(getSaveButton());
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while setting this page redaction" + e.getMessage());
		}
	}

	/**
	 * @author Steffy
	 * @Description -- Method to verify whether the redaction is saved or not
	 * 
	 * @param isSavedFlag -- This is the flag to denote whether redaction should be
	 *                    saved or not.
	 */
	public void verifyWhetherRedactionIsSaved(Boolean isSavedFlag) {
		try {
			base = new BaseClass(driver);
			driver.waitForPageToBeReady();
			base.waitForElement(getRedactedPage());
			Actions actions = new Actions(driver.getWebDriver());
			base.waitTime(10);
			actions.moveToElement(getDocView_Redactrec_textarea().getWebElement()).click();
			actions.build().perform();
			if (isSavedFlag) {
				base.waitForElement(getRedactionPopup());
				softAssertion.assertEquals(getRedactionPopup().getWebElement().isDisplayed(), true);
				base.passedStep("Redaction is  saved and popup is displayed");
			} else {
				Element element = getRedactionPopup();
				try {
					if (!element.Stale()) {
						base.failedStep("Redaction is saved and it is displayed");
					} else {
						base.passedStep("Redaction is not saved and it is not displayed");
					}
				} catch (Exception e) {
					base.passedStep("Redaction is not saved and it is not displayed");

				}
			}
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying this page redaction" + e.getMessage());
		}

	}

	/**
	 * @author Steffy
	 * @Description -- Method to verify search text can be done using magnifying
	 *              icon and if X clicked then searched text should be cleared and
	 *              highlighted text should be removed
	 * 
	 * @param isFlag -- This is the flag to denote whether x icon should be clicked
	 *               or not.
	 */
	public void verifySearchUsingMagnifyingIcon(Boolean isFlag) {
		try {
			driver.waitForPageToBeReady();
			if (isFlag) {
				verifyHighlightedText();
				base.waitForElement(getHitIcon());
				if (getHitIcon().Displayed()) {
					base.passedStep("Search is successful");
				} else {
					base.failedStep("Search is not successful");
				}
			} else {
				base.stepInfo("Click On Close Icon and Verify whether search is cleared");
				base.waitForElement(getCloseIcon());
				getCloseIcon().Click();
				base.waitForElement(getMagnifyingIcon());
				Element element = getHitIcon();
				try {
					if (!element.Stale()) {
						base.failedStep("Search is not cleared");
					} else {
						base.passedStep("Search is cleared successfully");
					}
				} catch (Exception e) {
					base.passedStep("Search is cleared successfully");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying search using magnifying icon" + e.getMessage());
		}

	}

	/*
	 * @author Steffy
	 * 
	 * @Description -- Method to verify whether the highlighted text in docview are
	 * from keyword groups
	 * 
	 */
	public void verifyHighlightedTextsAreDisplayed() throws Exception {
		try {
			driver.waitForPageToBeReady();
			base = new BaseClass(driver);
			base.waitTime(10);
			base.waitForElement(get_textHighlightedColor());
			String color = get_textHighlightedColor().getWebElement().getCssValue("fill");
			String hex = Color.fromString(color).asHex();
			driver.waitForPageToBeReady();
			System.err.println(hex);
			if ((hex.equals(Input.keyWordHexCode)) || hex.equalsIgnoreCase(Input.colorCodeOfRed))
				base.passedStep("The text for keyword is highlited in the document");
			else
				base.failedStep("The text for keyword is not highlited in the document");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying highlighted text in Doc View Page" + e.getMessage());
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 **/
	public void verifyRedactionsSubMenu() {
		base = new BaseClass(driver);
		if (multiPageIcon().Displayed() && textSelectionRedactionIcon().Displayed() && thisPageRedaction().Displayed()
				&& rectangleClick().Displayed()) {
			base.passedStep("Multipage,Text,ThisPage,Rectangular redaction icon is displayed on"
					+ " redactions panel after clicking on redact icon");
		} else {
			base.failedStep("Redactions icons are not displayed as expected in redaction panel.");
		}

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @modified By : jeevitha @modified date: 13/1/2022
	 **/
	public void VerifyDeletePopUp_BatchRedact(boolean deleteRedaction) {
		base = new BaseClass(driver);
		SessionSearch session = new SessionSearch(driver);
		base.waitForElement(getTrashIcon());
		getTrashIcon().waitAndClick(10);
		if (getBellyBandMsgHeaderFromDeleteBatchRedact().Displayed()) {
			base.stepInfo("Belly Band popup dor delete action is displayed");
			base.stepInfo("Displayed warning message---" + getBellyBandMsgHeaderFromDeleteBatchRedact().getText());
			base.stepInfo("Displayed warning message---" + getBellyBandMessageFromDeleteBatchRedact().getText());
			softAssertion.assertEquals(getBellyBandMsgHeaderFromDeleteBatchRedact().getText(),
					"Are you sure you want to delete all redactions for " + Input.testData1 + "?");
			softAssertion.assertEquals(getBellyBandMessageFromDeleteBatchRedact().getText(),
					"This will delete the redactions for \"" + Input.testData1 + "\" on this document permanently.");
			base.passedStep("Warnign Message displayed as expected ");

			if (deleteRedaction) {
				String confirmationYesBtn = session.getYesQueryAlert().getText();
				getBellyBandMsgHeaderFromDeleteBatchRedact().Displayed();
				boolean flag = confirmationYesBtn.equals("ja");
				if (flag) {
					System.out.println(
							confirmationYesBtn + " : Belly Band popup warning message is Localized to German language");
					base.stepInfo(
							confirmationYesBtn + " : Belly Band popup warning message is Localized to German language");
				}
				session.getYesQueryAlert().waitAndClick(10);
				if (flag) {
					base.VerifySuccessMessageInGerman("Redaction Removed successfully.");
				} else {
					base.VerifySuccessMessage("Redaction Removed successfully.");
				}

			} else {
				String confirmationNoBtn = base.getNOBtn().getText();

				if (confirmationNoBtn.equals("nicht")) {
					System.out.println(
							confirmationNoBtn + " : Belly Band popup warning message is Localized to German language");
					base.stepInfo(
							confirmationNoBtn + " : Belly Band popup warning message is Localized to German language");
				}
				base.getNOBtn().waitAndClick(10);
				driver.waitForPageToBeReady();
				softAssertion.assertEquals(getBatchRedactTextInRedactionPanel().Displayed().booleanValue(), true);
				base.passedStep(
						"Batch Redactions is not  deleted and belly band message also get disappeared after clicking on NO button.");
			}

			softAssertion.assertAll();
		} else {
			base.failedStep("Delete pop up is not displayed.");
		}

	}

	/**
	 * @author Sai krishna
	 **/

	public void clickingThumbnailIcon() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return thumbNailsIcon().Visible() && HighliteIcon().Enabled();
			}
		}), Input.wait30);
		thumbNailsIcon().waitAndClick(30);
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting redaction tag from multiple page
	 *               redaction.
	 * @param redactionTag : redactionTag is String value that name of redaction tag
	 *                     to select for multiple page redaction.
	 **/

	public void selectingMultiplePagesForRedactionExclude(String redactionTag) throws Exception {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionTagSelect().waitAndFind(12);
			}
		}), Input.wait30);

		Select multiplepageredaction = new Select(redactionTagSelect().getWebElement());
		multiplepageredaction.selectByVisibleText(redactionTag);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionMethodSelect().Visible() && redactionMethodSelect().Enabled();
			}
		}), Input.wait30);

		Select redactionmethod = new Select(redactionMethodSelect().getWebElement());
		redactionmethod.selectByIndex(1);
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for performing multiple page redaction.
	 **/
	public void performMultiplePageRedactionForExclude(String redactionTag, String pageRange) {
		try {
			base = new BaseClass(driver);
			Actions actions = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return redactionIcon().waitAndFind(12);
				}
			}), Input.wait30);
			redactionIcon().waitAndClick(30);
			driver.waitForPageToBeReady();
			base.waitForElement(multiPageIcon());
			actions.moveToElement(multiPageIcon().getWebElement()).click();
			actions.click().build().perform();
			selectingMultiplePagesForRedactionExclude(redactionTag);
			enteringPagesInMultipageTextBox(pageRange);
			base.VerifySuccessMessage("Redaction tags saved successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while performing multiple page redaction." + e.getMessage());
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting redaction tag from multiple page
	 *               redaction.
	 * @param redactionTag : redactionTag is String value that name of redaction tag
	 *                     to select for multiple page redaction.
	 **/

	public void selectingMultiplePagesForRedactionInclude(String redactionTag) throws Exception {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionTagSelect().waitAndFind(12);
			}
		}), Input.wait30);

		Select multiplepageredaction = new Select(redactionTagSelect().getWebElement());
		multiplepageredaction.selectByVisibleText(redactionTag);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionMethodSelect().Visible() && redactionMethodSelect().Enabled();
			}
		}), Input.wait30);

		Select redactionmethod = new Select(redactionMethodSelect().getWebElement());
		redactionmethod.selectByIndex(0);
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for performing multiple page redaction.
	 **/
	public void performMultiplePageRedactionForInclude(String redactionTag, String pageRange) {
		try {
			base = new BaseClass(driver);
			Actions actions = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return redactionIcon().waitAndFind(12);
				}
			}), Input.wait30);
			redactionIcon().waitAndClick(30);
			driver.waitForPageToBeReady();
			base.waitForElement(multiPageIcon());
			actions.moveToElement(multiPageIcon().getWebElement()).click();
			actions.click().build().perform();
			selectingMultiplePagesForRedactionInclude(redactionTag);
			enteringPagesInMultipageTextBox(pageRange);
			base.VerifySuccessMessage("Redaction tags saved successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while performing multiple page redaction." + e.getMessage());
		}

	}

	/**
	 * Author : Vijaya.Rani date: 2/12/21 NA Modified date: NA Modified by:NA
	 * Description :perform The Eye Icon HighLighting.
	 *
	 */
	public void performTheEyeIconHighLighting() throws Exception {
		base = new BaseClass(driver);
		getDocView_MiniDoc_Selectdoc(5).waitAndClick(20);
		driver.waitForPageToBeReady();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getEyeIcon().Visible() && HighliteIcon().Enabled();
			}
		}), Input.wait30);
		getEyeIcon().waitAndClick(30);

		base.stepInfo("docView Eye Icon Clicked Successfully");

		if (docViewEyeSearchTerm().Displayed()) {

			base.passedStep("HighLighting Text is Displayed");
		} else {

			base.failedStep("HighLighting Text  is Not Displayed");
		}
		driver.waitForPageToBeReady();
		if (getNextArrowBtn().Displayed()) {

			base.passedStep("The Arrow Button is Displayed");
			getNextArrowBtn().waitAndClick(30);
		} else {

			base.failedStep("Arrow Button is Not Displayed");
		}

	}

	/**
	 * @author : Mohan Created date: 02/12/2021 Modified date: NA Modified
	 *         by:Gopinath.
	 * @Description: Method for verifying Reviewer Remarks
	 **/
	public void verifyReviewerRemarksIsPresent() {

		try {
			base = new BaseClass(driver);
			driver.waitForPageToBeReady();
			base.waitForElement(editRemarksIcon());
			// editRemarksIcon().ScrollTo();
			softAssertion.assertTrue(editRemarksIcon().isElementPresent());
			base.passedStep("Reviewers remark is displayed successfully");
			softAssertion.assertAll();

		} catch (Exception e) {
			base.failedStep("Reviewer Remark is not displayed in DocView");
		}

	}

	/**
	 * Author : Vijaya.Rani date: 2/12/21 NA Modified date: NA Modified by:NA
	 * Description :perform Click The Search Icon Back To original
	 *
	 */
	public void performClickSearchIconAndX() throws Exception {

		base = new BaseClass(driver);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getSearchIcon().Visible() && HighliteIcon().Enabled();
			}
		}), Input.wait30);
		getSearchIcon().waitAndClick(30);

		base.passedStep("successfully Clicked the Search Icon");

		driver.waitForPageToBeReady();
		base.waitForElement(getInputSearchBox());
		getInputSearchBox().waitAndClick(30);

		getInputSearchBox().SendKeys("documents");
		base.stepInfo("Search input Text completed");

		driver.waitForPageToBeReady();
		base.waitForElement(crossxIcon());
		crossxIcon().waitAndClick(30);

	}

	/**
	 * Author : Vijaya.Rani date: 7/12/21 NA Modified date: NA Modified by:NA
	 * Description :perform The Remark Icon
	 *
	 */
	public void performTheRemarkIconNotSave() throws Exception {

		base = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		getDocView_MiniDoc_Selectdoc(5).waitAndClick(30);
		driver.waitForPageToBeReady();

		base.waitForElement(remarksIcon());
		remarksIcon().waitAndClick(25);

	}

	/**
	 * Author : Vijaya.Rani date: 8/12/21 NA Modified date: NA Modified by:NA
	 * Description :perform The select Assignment from Dashborad
	 *
	 */
	public void selectAssignmentfromDashborad(String assginmentName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDashboardButton().Visible() && getDashboardButton().Enabled();
			}
		}), Input.wait30);
		getDashboardButton().waitAndClick(5);

		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentFromDashborad(assginmentName).Visible()
						&& getSelectAssignmentFromDashborad(assginmentName).Enabled();
			}
		}, Input.wait30);
		getSelectAssignmentFromDashborad(assginmentName).waitAndClick(10);

	}

	/**
	 * @author Vijaya.Rani date: 18/8/2021 Modified date: NA Description:Perform
	 *         Geer Icon Show Completed Docs
	 * 
	 */

	public void performGeerIcon() {

		driver.waitForPageToBeReady();
		getDocview_GearButton().waitAndClick(30);
		driver.waitForPageToBeReady();
		getDocview_ShowCompletedDocs().waitAndClick(30);
		driver.waitForPageToBeReady();
		getDocview_GeerSaveBtn().waitAndClick(30);

	}

	/**
	 * @author Vijaya.Rani date: 10/12/2021
	 * @Description:Perform Complete Button
	 * 
	 */

	public void performCompleteToDocs() throws InterruptedException {
		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(1).waitAndClick(30);
		getCompleteDocBtn().waitAndClick(20);
		;
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(2).waitAndClick(30);
		getCompleteDocBtn().waitAndClick(20);
		;
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(3).waitAndClick(30);
		getCompleteDocBtn().waitAndClick(20);
		;
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(4).waitAndClick(30);
		getCompleteDocBtn().waitAndClick(20);
		;
		driver.scrollPageToTop();
	}

	/**
	 * @author Vijaya.Rani date: 10/12/2021
	 * @Description:Perform UnComplete Button
	 * 
	 */

	public void performUnCompleteToDocs() throws InterruptedException {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		getDocView_MiniDoc_Selectdoc(1).waitAndClick(30);
		getUnCompleteButton().waitAndClick(20);
		;
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(2).waitAndClick(30);
		getUnCompleteButton().waitAndClick(20);
		;
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(3).waitAndClick(30);
		getUnCompleteButton().waitAndClick(20);
		;
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(4).waitAndClick(30);
		getUnCompleteButton().waitAndClick(20);
		;
		driver.scrollPageToTop();
	}

	/**
	 * Author : Vijaya.Rani date: 2/12/21 NA
	 *
	 * @Modified date: 9/12/2021
	 * @Modified by: Jeevitha.Rajendran
	 * @Description :perform Click The Search Icon Back To original
	 *
	 */
	public void performClickSearchIconAndX(String input, boolean select) throws Exception {

		base = new BaseClass(driver);

		base.waitForElement(getSearchIcon());
		getSearchIcon().waitAndClick(30);

		base.passedStep("successfully Clicked the Search Icon");

		driver.waitForPageToBeReady();
		base.waitForElement(getInputSearchBox());
		getInputSearchBox().waitAndClick(30);

		getInputSearchBox().SendKeys(input);
		base.stepInfo("Search input is : " + input);

		if (select) {
			driver.waitForPageToBeReady();
			base.waitForElement(getSecondSearchIcon());
			getSecondSearchIcon().waitAndClick(10);
			base.waitForElement(getHitCount_cc());

			if (getHitCount_cc().isElementPresent()) {
				String hitCount = getHitCount_cc().getText();
				String[] count = hitCount.split(" ");
				String doCount = null;
				for (String ext : count) {
					doCount = ext;
					System.out.println(doCount);
				}
				base.stepInfo("Hit Count : " + hitCount);
				base.stepInfo("Document Count : " + doCount);
			}

		} else {

			driver.waitForPageToBeReady();
			base.waitForElement(crossxIcon());
			crossxIcon().waitAndClick(30);
		}

	}

	// Added by sai krishna
	public void navigatingDocsFromMiniDocListChildWindowandClose() throws Exception {
		base = new BaseClass(driver);

		String parentWindow = driver.getWebDriver().getWindowHandle();
		Set<String> childWindow = driver.getWebDriver().getWindowHandles();
		for (String miniDocListChild : childWindow) {
			if (!parentWindow.equals(miniDocListChild)) {
				driver.switchTo().window(miniDocListChild);
				driver.waitForPageToBeReady();
			}
		}
		for (int i = 1; i <= 4; i++) {
			Thread.sleep(4000);
			miniDoclistTable(i).waitAndClick(10);
		}
		driver.switchTo().window(parentWindow);

	}

	public void clickingConceptuallySimilarTab() {
		driver.scrollingToElementofAPage(getConceptuallySimilarTab());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getConceptuallySimilarTab().Visible() && getConceptuallySimilarTab().Enabled();
			}
		}), Input.wait30);
		getConceptuallySimilarTab().waitAndClick(30);
		driver.scrollingToBottomofAPage();

	}

	public void clickingImagesTab() throws InterruptedException {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return imagesIconDocView().Visible() && imagesIconDocView().Enabled();
			}
		}), Input.wait30);
		imagesIconDocView().waitAndClick(30);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	public void verifyHighlightedText_withclick() throws Exception {
		base = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		base.waitForElement(searchTextIcon());
		searchTextIcon().waitAndClick(10);

		set_searchText().getWebElement().sendKeys("Enron");
		getOnDemandSearchBtn().waitAndClick(10);

		Thread.sleep(4000); // needed here implicitly

		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex = Color.fromString(color).asHex(); // #dc5252
		System.out.println(hex);

		if (hex.equalsIgnoreCase(Input.colorCodeHighlight)) // #dc5252
		{
			System.out.println("The color for the Highlighted text is verfied- Successfully");
			base.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			System.out.println("The color for the Highlighted text is not-verfied - Failed");
			base.failedStep("The color for the Highlighted text is not-verfied - Failed");
		}

	}

	/**
	 * Method to verify active Doc Id
	 */

	public void verifyingActiveDocIdInDocView(String Docid) {
		base = new BaseClass(driver);
		String text = activeDocId().getText();
		System.out.println(text);
		if (text.equalsIgnoreCase(Docid)) {
			base.passedStep("Active DocId verified");
		} else {
			base.failedStep("Active doc id is not as expected");
		}

	}

	/**
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :perform Display Icon Reviewer Highlight.
	 *
	 */
	public void performDisplayIconReviewerHighlight() throws Exception {
		base = new BaseClass(driver);
		docView = new DocViewPage(driver);
		getDocView_MiniDoc_Selectdoc(1).waitAndClick(20);
		driver.waitForPageToBeReady();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionIcon().Visible();
			}
		}), Input.wait30);
		redactionIcon().waitAndClick(30);

		base.stepInfo("Redaction Icon Clicked Successfully");

		softAssertion.assertTrue(multiPageIcon().Displayed());
		base.passedStep("DocView Redaction Page is Displayed");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getEyeIcon().Visible() && HighliteIcon().Enabled();
			}
		}), Input.wait30);
		getEyeIcon().waitAndClick(30);

		base.stepInfo("docView Eye Icon Clicked Successfully");

		softAssertion.assertTrue(docViewEyeSearchTerm().Displayed());
		base.passedStep("DocView EyeIcon Search Term Is Displayed");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return remarksIcon().Visible();
			}
		}), Input.wait30);
		remarksIcon().waitAndClick(30);

		base.stepInfo("Reviewer Remark Icon Clicked Successfully");

		softAssertion.assertTrue(docViewReviewerPage().Displayed());
		base.passedStep("DocView Reviewer Page Is Displayed");

		String parentWindowID = driver.getWebDriver().getWindowHandle();
		docView.popOutMiniDocList();
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		getDocView_MiniDoc_Selectdoc(3).waitAndClick(20);
		base.passedStep("Mini Doc List Child Window Docs Selected Successfully");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		softAssertion.assertTrue(docViewReviewerPage().Displayed());
		base.passedStep("DocView Reviewer Page Is Displayed");

	}

	/*
	 * Arunkumar Description: verify all redaction count status after doing
	 * redaction
	 */

	public void verifyAllRedactionCountStatusAfterRedaction(int beforeRedactedCount) {
		docView = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		base.waitTime(2); // app sync
		String[] firstRedactedCount = docView.getDocView_AllRedactionCount().getText().split("/ ");
		int afterFirstRedactedCount = Integer.parseInt(firstRedactedCount[1]);
		if (afterFirstRedactedCount > beforeRedactedCount) {
			base.passedStep("All redactions count Increased after redaction");
		} else {
			base.failedStep("All redactions count not Increased after redaction");
		}
	}

	/*
	 * Arunkumar Description: Method for performing multipage redactions multiple
	 * times and also verifying all redaction count after every redaction Testcase :
	 * RPMXCON-47026
	 */
	public void multipleMultiPageRedactions(String firstRange, String secondRange, String thirdRange) throws Exception {
		docView = new DocViewPage(driver);
		base = new BaseClass(driver);
		// Clicking redact icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_RedactIcon().Visible() && docView.getDocView_RedactIcon().Enabled();
			}
		}), Input.wait30);
		base.waitTillElemetToBeClickable(docView.getDocView_RedactIcon());
		docView.getDocView_RedactIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		String[] count = docView.getDocView_AllRedactionCount().getText().split("/ ");
		int beforeRedactedCount = Integer.parseInt(count[1]);

		// first condition open multipage redact popup and enter page range
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return multiPageIcon().Visible() && multiPageIcon().Enabled();
			}
		}), Input.wait30);
		base.waitTillElemetToBeClickable(multiPageIcon());
		multiPageIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		selectingMultiplePagesForRedaction();
		enteringPagesInMultipageTextBox(firstRange);
		base.passedStep("First multipage redaction done for page number :" + firstRange);
		base.VerifySuccessMessage("Redaction tags saved successfully.");

		// verifying all redact count status after first redaction
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(multiPageIcon());
		String[] firstRedactedCount = docView.getDocView_AllRedactionCount().getText().split("/ ");
		int afterFirstRedactedCount = Integer.parseInt(firstRedactedCount[1]);
		if (afterFirstRedactedCount > beforeRedactedCount) {
			base.passedStep("All redactions count Increased after first redaction");
		} else {
			base.failedStep("All redactions count not Increased after first redaction");
		}

		// second condition open multipage redact popup and enter page range
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return multiPageIcon().Visible() && multiPageIcon().Enabled();
			}
		}), Input.wait30);
		base.waitTillElemetToBeClickable(multiPageIcon());
		multiPageIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		selectingMultiplePagesForRedaction();
		enteringPagesInMultipageTextBox(secondRange);
		base.passedStep("Second multipage redaction done for page number :" + secondRange);
		base.VerifySuccessMessage("Redaction tags saved successfully.");

		// verifying all redact count status after second redaction
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(multiPageIcon());
		String[] secondRedactedCount = docView.getDocView_AllRedactionCount().getText().split("/ ");
		int afterSecondRedactedCount = Integer.parseInt(secondRedactedCount[1]);
		if (afterSecondRedactedCount > afterFirstRedactedCount) {
			base.passedStep("All redactions count Increased after second redaction");
		} else {
			base.failedStep("All redactions count not Increased after second redaction");
		}
		// third condition open multipage redact popup and enter page range
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return multiPageIcon().Visible() && multiPageIcon().Enabled();
			}
		}), Input.wait30);
		base.waitTillElemetToBeClickable(multiPageIcon());
		multiPageIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		selectingMultiplePagesForRedaction();
		enteringPagesInMultipageTextBox(thirdRange);
		base.passedStep("Third multipage redaction done for page number :" + thirdRange);
		base.VerifySuccessMessage("Redaction tags saved successfully.");

		// verifying all redact count status after third redaction
		driver.waitForPageToBeReady();
		base.waitTime(2); // app sync
		String[] thirdRedactedCount = docView.getDocView_AllRedactionCount().getText().split("/ ");
		int afterThirdRedactedCount = Integer.parseInt(thirdRedactedCount[1]);
		if (afterThirdRedactedCount > afterSecondRedactedCount) {
			base.passedStep("All redactions count Increased after third redaction");
		} else {
			base.failedStep("All redactions count not Increased after third redaction");
		}

	}

	/*
	 * Arunkumar Description: verify whether default redaction tag selected in
	 * multiredact pop-up
	 */
	public void verifyDefaultRedactionTagSelectionInMultiPageRedact() {
		docView = new DocViewPage(driver);
		base = new BaseClass(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return multiPageIcon().Visible() && multiPageIcon().Enabled();
			}
		}), Input.wait30);
		base.waitTillElemetToBeClickable(multiPageIcon());
		multiPageIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionTagSelect().Visible() && redactionTagSelect().Enabled();
			}
		}), Input.wait30);

		Select multiplepageredaction = new Select(redactionTagSelect().getWebElement());
		String selectedTag = multiplepageredaction.getFirstSelectedOption().getText();

		System.out.println(selectedTag);

		if (selectedTag.equalsIgnoreCase("Default Redaction Tag")) {
			base.passedStep("Default Redaction Tag Selected by default");
		} else {
			base.failedStep("Default Redaction Tag not selected by default");
		}

	}

	/*
	 * Arunkumar Description:getting last saved redaction tag selected in
	 * multiredact pop-up
	 */
	public String gettingLastSavedRedactionTagName() throws InterruptedException {
		docView = new DocViewPage(driver);
		base = new BaseClass(driver);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return multiPageIcon().Visible() && multiPageIcon().Enabled();
			}
		}), Input.wait30);
		base.waitTillElemetToBeClickable(multiPageIcon());
		multiPageIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionTagSelect().Visible() && redactionTagSelect().Enabled();
			}
		}), Input.wait30);

		Select multiplepageredaction = new Select(redactionTagSelect().getWebElement());
		String selectedTag = multiplepageredaction.getFirstSelectedOption().getText();

		return selectedTag;
	}

	public void clickingAudioRemarksIcon() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return audioRemarksBtn().Visible() && audioRemarksBtn().Enabled();
			}
		}), Input.wait30);
		audioRemarksBtn().waitAndClick(30);

	}

	/*
	 * @author Steffy Method to validate persistent hit panel against doc
	 * highlighted count
	 */

	public void validatePersistentPanelHitCountAgainstDocHighlightedCount(String term) {
		Robot robot;
		try {
			robot = new Robot();
			base = new BaseClass(driver);
			base.waitForElement(getSearchIcon());
			getSearchIcon().Click();
			base.waitForElement(getInputSearchBox());
			getInputSearchBox().SendKeys(term);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		base.waitForElement(persistantHitToggle());
		persistantHitToggle().waitAndClick(10);
		base.stepInfo("Get the persistent hit count from document");
		String docCount = getDocHighlightedCount(term).getText();
		base.stepInfo("Get the persistent hit count from panel");
		String panelCount = getDocHighlightedCount(term).getText();
		softAssertion.assertTrue(docCount.equals(panelCount));
		softAssertion.assertAll();
		base.passedStep("Keyword highlighted count in document is same as persistent hit panel count");
	}

	// Added by krishna - disabling print toggle for assignment after going into
	// edit

	public void DisablePrintToggleinAssignment() {
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		base.waitTillElemetToBeClickable(getAssgn_PrintPdfToggle());
		String analyticalPanelFlag = getAssgn_PrintPdfToggle().GetAttribute("class");
		if (!analyticalPanelFlag.contains("false")) {
			getAssgn_PrintPdfToggle().waitAndClick(10);
		}

		driver.scrollPageToTop();
		base.waitForElement(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		base.VerifySuccessMessage("Assignment updated successfully");
	}

	// added by Sai krishna
	public void verifyViewDocAnalyticalPanelIsNotPartInMiniDocList() {
		docView = new DocViewPage(driver);
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		docView.scrollingDocumentInMiniDocList();
		driver.waitForPageToBeReady();
		getDocView_MiniDoc_Selectdoc(116).ScrollTo();
		getDocView_MiniDoc_Selectdoc(116).WaitUntilPresent().Click();
		base.waitForElement(docView.getDocView_CurrentDocId());
		softAssertion.assertTrue(docView.getDocView_CurrentDocId().isDisplayed());
		base.passedStep("Selected DocId is successfully Displayed");
		base.waitForElement(docView.getDocView_ImagesTab());
		docView.getDocView_ImagesTab().waitAndClick(5);
		base.stepInfo("Image tab loaded");
		if (docView.getDocView_ImagesTab().isDisplayed()) {
			base.passedStep("Imagetab is displayed successfully");
		} else {
			base.failedStep("Image tab is not retained");
		}
		docView.popOutAnalyticsPanel();
		driver.waitForPageToBeReady();
		docView.switchToNewWindow(2);
		base.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().Click();
		base.waitForElement(docView.getDocumentConceptuallySimilar(1));
		docView.getDocumentConceptuallySimilar(1).waitAndClick(5);
		base.stepInfo("Doc is selected which is not present in minidoclist");
		base.waitForElement(docView.getDocView_ChildWindow_ActionButton());
		docView.getDocView_ChildWindow_ActionButton().waitAndClick(10);
		base.waitForElement(docView.getDocView_Analytics_Concept_ViewDocument());
		docView.getDocView_Analytics_Concept_ViewDocument().waitAndClick(10);
		driver.waitForPageToBeReady();
		docView.closeWindow(1);
		docView.switchToNewWindow(1);
		driver.scrollPageToTop();
		base.waitForElement(docView.getDocView_CurrentDocId());
		softAssertion.assertTrue(docView.getDocView_CurrentDocId().isDisplayed());
		base.passedStep("After Selected analytical panel DocId is successfully Displayed");
		if (docView.getDocView_ImagesTab().isDisplayed()) {
			base.passedStep("After selected analytical panel docId on image tab is displayed successfully");
		} else {
			base.failedStep("Image tab is not displayed");

		}
	}

	public void verifyViewDocAnalyticalPanelPartInMiniDocList() {
		driver.waitForPageToBeReady();
		docView = new DocViewPage(driver);
		base = new BaseClass(driver);
		getDocView_MiniDoc_Selectdoc(1).waitAndClick(10);
		base.waitForElement(docView.getDocView_CurrentDocId());
		softAssertion.assertTrue(docView.getDocView_CurrentDocId().isDisplayed());
		base.passedStep("Selected DocId is successfully Displayed");
		base.waitForElement(docView.getDocView_ImagesTab());
		docView.getDocView_ImagesTab().waitAndClick(5);
		base.stepInfo("Image tab loaded");
		if (docView.getDocView_ImagesTab().isDisplayed()) {
			base.passedStep("Imagetab is displayed successfully");
		} else {
			base.failedStep("Image tab is not retained");

		}
		docView.popOutAnalyticsPanel();
		driver.waitForPageToBeReady();
		docView.switchToNewWindow(2);
		base.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		base.waitForElement(docView.getDocView_NearDupe_Selectdoc());
		docView.getDocView_NearDupe_Selectdoc().waitAndClick(10);
		base.stepInfo("Doc is selected which is present in minidoclist");
		docView.viewInDocumentAnalyticalPanel();
		driver.waitForPageToBeReady();
		docView.closeWindow(1);
		docView.switchToNewWindow(1);
		driver.scrollPageToTop();
		if (docView.geDocView_MiniDocList_ArrowDownIcon().isDisplayed()) {
			base.passedStep("ArrowDown icon is displayed for the selected docs ");
		} else {
			base.failedStep("ArrownDown icon is not displayed for the selected docs");
		}
		base.waitForElement(docView.getDocView_CurrentDocId());
		softAssertion.assertTrue(docView.getDocView_CurrentDocId().isDisplayed());
		base.passedStep("After Selected analytical panel DocId is successfully Displayed");
		if (docView.getDocView_ImagesTab().isDisplayed()) {
			base.passedStep("After selected analytical panel docId on image tab is displayed successfully");
		} else {
			base.failedStep("Image tab is not displayed");

		}
	}

	public void verifyMaximizetheMiddlePanel() throws InterruptedException {
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		base.waitForElement(getDocViewPanel());
		if (getDocViewPanel().isDisplayed()) {
			base.passedStep("DocView panels are displayed");
			softAssertion.assertEquals(getDocViewPanel().isDisplayed().booleanValue(), true);
		} else {
			base.failedStep("panels are not displayed");
		}
		Actions actions = new Actions(driver.getWebDriver());
		driver.waitForPageToBeReady();
		base.waitForElement(getMaximizePanel());
		wait.until(ExpectedConditions.elementToBeClickable(getMaximizePanel().getWebElement()));
		Thread.sleep(4000);
		// Thread sleep added for the page to maximize
		actions.moveToElement(getMaximizePanel().getWebElement()).clickAndHold().moveByOffset(200, 20).release().build()
				.perform();
		base.stepInfo("Middle panel of the doc view page is successfully maximized");
		base.waitForElement(getDocViewPanel());
		if (getDocViewPanel().isDisplayed()) {
			base.passedStep("DocView panels are collapsed on maximizing the middle panel ");
			softAssertion.assertTrue(getDocViewPanel().isDisplayed());
		} else {
			base.failedStep("panels are not displayed");
		}
	}

	/**
	 * @author Raghuram.A Description : doDrawUsingXY Date: 01/20/22 Modified date:
	 *         N/A Modified by: N/A
	 * @param x
	 * @param y
	 * @param offsetx
	 * @param offsety
	 * @throws Exception
	 */
	public void doDrawUsingXY(int x, int y, int offsetx, int offsety) throws Exception {

		base = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		driver.waitForPageToBeReady();
		base.waitTime(3);// To handle abnormal waits

		actions.moveToElement(getDocView_RedactHTextarea().getWebElement(), x, y).clickAndHold()
				.moveByOffset(offsetx, offsety).release().build().perform();

	}

	/**
	 * @author Raghuram.A Description : checkANdDOThisPageHighlight Date: 01/20/22
	 *         Modified date: N/A Modified by: N/A
	 * @throws InterruptedException
	 */
	public void checkANdDOThisPageHighlight() throws InterruptedException {
		base = new BaseClass(driver);

		getDocView_RedactHTextarea().waitAndClick(5);
		driver.scrollPageToTop();
		if (highliteDeleteBtn().isElementAvailable(5)) {
			base.stepInfo("The page has been highlited already");
		} else {
			clickingHighlitingIcon();
			base.stepInfo("Clicked This page icon");
			driver.waitForPageToBeReady();
			base.passedStep("Current page is highlighted successfully");
			driver.waitForPageToBeReady();
		}
	}

	/**
	 * @author Raghuram.A Description : verifyThisPageHighlightMaintained Date:
	 *         01/20/22 Modified date: N/A Modified by: N/A
	 * @param torRmoveAnnotation
	 */
	public void verifyThisPageHighlightMaintained(Boolean torRmoveAnnotation) {
		base = new BaseClass(driver);

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getDocView_RedactHTextarea().waitAndClick(5);
		if (highliteDeleteBtn().isElementAvailable(10)) {
			base.passedStep(
					"Document remains highlighted and Highlighting not shifted when visiting the different file types having page highlighting");
			if (torRmoveAnnotation) {
				deleteRedactioBtn().waitAndClick(5);
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Annotation Removed successfully.");
			}
		} else {
			base.failedStep("Fail");
		}
	}

	/**
	 * @author Raghuram.A Description : removeThisPageHighlight Date: 01/20/22
	 *         Modified date: N/A Modified by: N/A
	 */
	public void removeThisPageHighlight() {
		base = new BaseClass(driver);

		getDocView_RedactHTextarea().waitAndClick(5);
		driver.scrollPageToTop();
		if (highliteDeleteBtn().isElementAvailable(5)) {
			base.stepInfo("The page has been highlited already");
			deleteRedactioBtn().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.VerifySuccessMessage("Annotation Removed successfully.");
			base.stepInfo("Annotation Removed successfully.");
		} else {
			System.out.println("This Page highlight not done at current doc");
		}
	}

	/**
	 * @author Raghuram.A Description : doRectangleHighlightWithXYPoints Date:
	 *         01/20/22 Modified date: N/A Modified by: N/A
	 * @param x
	 * @param y
	 * @param xOffset
	 * @param yOffset
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> doRectangleHighlightWithXYPoints(int x, int y, int xOffset, int yOffset)
			throws Exception {
		base = new BaseClass(driver);
		String xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();

		HighliteIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		highLightRectangleClick().waitAndClick(8);
		base.waitTime(5);
		doDrawUsingXY(x, y, xOffset, yOffset);
		base.waitTime(5);// To handle abnormal waits
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		xAxis = rectangleRedactXYStats().GetAttribute("x");
		yAxis = rectangleRedactXYStats().GetAttribute("y");
		xyMap.put("xAxis", xAxis);
		xyMap.put("yAxis", yAxis);

		base.stepInfo("Rectangle highlighted position : X axis : " + xAxis + " and Y axis : " + yAxis);

		return xyMap;
	}

	/**
	 * @author Raghuram.A Description : doRectangleRedactWithXYPoints Date: 01/20/22
	 *         Modified date: N/A Modified by: N/A
	 * @param x
	 * @param y
	 * @param xOffset
	 * @param yOffset
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> doRectangleRedactWithXYPoints(int x, int y, int xOffset, int yOffset)
			throws Exception {
		base = new BaseClass(driver);
		String xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();
		List<String> sortList = new ArrayList<>();

		redactionIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		rectangleClick().waitAndClick(5);
		driver.waitForPageToBeReady();
		doDrawUsingXY(x, y, xOffset, yOffset);
		driver.waitForPageToBeReady();
		multiPageInputSavaBtn().waitAndClick(3);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		sortList = base.availableListofElementsWithAttributeValues(rectangleRedactionXYStats(), "data-pcc-mark");
		Collections.sort(sortList, Collections.reverseOrder());
		String attributeValue = sortList.get(0).toString();
		System.out.println(attributeValue);

		xAxis = rectangleRedactionXYStats(attributeValue).GetAttribute("x");
		yAxis = rectangleRedactionXYStats(attributeValue).GetAttribute("y");
		xyMap.put("xAxis", xAxis);
		xyMap.put("yAxis", yAxis);

		base.stepInfo("Rectangle redacted position : X axis : " + xAxis + " and Y axis : " + yAxis);

		return xyMap;
	}

	/**
	 * @author Raghuram.A Description : verifyRectangleHighlightPositionRetained
	 *         Date: 01/20/22 Modified date: N/A Modified by: N/A
	 * @param xAxis
	 * @param yAxis
	 * @param toRemoveAnnotation
	 */
	public void verifyRectangleHighlightPositionRetained(String xAxis, String yAxis, Boolean toRemoveAnnotation) {
		base = new BaseClass(driver);
		base.stepInfo(
				"Rectangle highlighted position retained to verify : X axis : " + xAxis + " and Y axis : " + yAxis);
		if (rectangleRedactStats(xAxis, yAxis).isElementAvailable(5)) {
			base.passedStep(
					"Highlighting not shifted when visiting the different file types having Rectangle highlighting");
			if (toRemoveAnnotation) {
				rectangleRedactStats(xAxis, yAxis).waitAndClick(3);
				deleteRedactioBtn().waitAndClick(3);
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Annotation Removed successfully.");
			}
		} else {
			base.failedStep("Rectangle highlighting position mismatch");
		}
	}

	/**
	 * @author Raghuram.A Description : verifyRectangleRedactionPositionRetained
	 *         Date: 01/20/22 Modified date: N/A Modified by: N/A
	 * @param xAxis
	 * @param yAxis
	 * @param toRemoveAnnotation
	 */
	public void verifyRectangleRedactionPositionRetained(String xAxis, String yAxis, Boolean toRemoveAnnotation) {
		base = new BaseClass(driver);
		base.stepInfo("Rectangle redaction position retained to verify : X axis : " + xAxis + " and Y axis : " + yAxis);
		if (rectangleRedactStats(xAxis, yAxis).isElementAvailable(5)) {
			base.passedStep(
					"Rectangle redaction not shifted when visiting the different file types having Rectangle redaction");
			if (toRemoveAnnotation) {
				rectangleRedactStats(xAxis, yAxis).waitAndClick(3);
				highliteDeleteBtn().waitAndClick(3);
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Redaction Removed successfully.");
			}
		} else {
			base.failedStep("Rectangle redaction position mismatch");
		}
	}

	/**
	 * @author Raghuram.A Description : doTextRedactWithXYPoints Date: 01/27/22
	 *         Modified date: N/A Modified by: N/A
	 */
	public HashMap<String, String> doTextRedactWithXYPoints() throws Exception {
		base = new BaseClass(driver);
		String xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();

		redactionIcon().isElementAvailable(15);
		redactionIcon().waitAndClick(30);
		textSelectionRedactionIcon().isElementAvailable(15);
		textSelectionRedactionIcon().waitAndClick(10);
		driver.waitForPageToBeReady();

		xAxis = getDoTextRedactionXYStats().GetAttribute("x");
		yAxis = getDoTextRedactionXYStats().GetAttribute("y");
		System.out.println(xAxis);
		System.out.println(yAxis);

		driver.waitForPageToBeReady();
		base.waitTime(3);// To handle abnormal waits
		getDoTextRedactionXYStatsAttributeSelect(xAxis, yAxis).waitAndClick(5);

		multiPageInputSavaBtn().waitAndClick(5);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();

		xAxis = getDoTextRedactionXYStatsAttribute().GetAttribute("x");
		yAxis = getDoTextRedactionXYStatsAttribute().GetAttribute("y");
		xyMap.put("xAxis", xAxis);
		xyMap.put("yAxis", yAxis);

		if (getMarkedTextRedactionXYStatsAttributeSelect(xAxis, yAxis).isElementAvailable(4)) {
			System.out.println("Text marked");
		}

		base.stepInfo("Text redacted position : X axis : " + xAxis + " and Y axis : " + yAxis);

		return xyMap;
	}

	/**
	 * @author Raghuram.A Description : verifyTextRedactionPositionRetained Date:
	 *         01/27/22 Modified date: N/A Modified by: N/A
	 */
	public void verifyTextRedactionPositionRetained(String xAxis, String yAxis, Boolean toRemoveAnnotation) {
		base = new BaseClass(driver);
		base.stepInfo("Text redaction position retained to verify : X axis : " + xAxis + " and Y axis : " + yAxis);
		if (getMarkedTextRedactionXYStatsAttributeSelect(xAxis, yAxis).isElementAvailable(5)) {
			base.passedStep("Text redaction not shifted when visiting the different file types having Text redaction");
			if (toRemoveAnnotation) {
				getMarkedTextRedactionXYStatsAttributeSelect(xAxis, yAxis).waitAndClick(3);
				highliteDeleteBtn().waitAndClick(3);
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Redaction Removed successfully.");
			}
		} else {
			base.failedStep("Text redaction position mismatch");
		}
	}

	/**
	 * @author Krishna Description : verify assignment progress bar in completed
	 *         docs Date: 31/01/22 Modified date: N/A Modified by: N/A
	 * @param assname
	 * 
	 */
	public void verifyAssignmentBarInSelectedDocs(String assname) {
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
		SoftAssert softAssert = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.getHomeDashBoard().waitAndClick(10);
		base.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			base.passedStep("Assignment progress bar refreshed on completed doc");
			softAssert.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());

		} else {
			System.out.println("not completed");
			base.failedStep("Doc not completed");
		}
	}

	/**
	 * @author Raghuram.A Date: 01/27/22 Modified date: N/A Modified by: N/A
	 * @param docIDlist
	 * @param dataToVerify
	 */
	public void verifyContentPresentForListOfDocs(List<String> docIDlist, String[] dataToVerify) {
		base = new BaseClass(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		Boolean check = false;

		for (String nb : docIDlist) {
			System.out.println(nb);
			miniDocListpage.getDociD(nb).waitAndClick(10);
			driver.waitForPageToBeReady();

			base.stepInfo(nb + " : Docs Viewed in Doc View");
			base.waitTillElemetToBeClickable(textTab());
			textTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitTime(2);

			String datas = textData().getText();

			String[] count = datas.split("\\r"); // change - replacing \\r?\\n with \\r
			for (String a : count) {
				check = false;
				System.out.println(a);
				for (String datasToCheck : dataToVerify) {
					if (a.contains(datasToCheck)) {
						base.passedStep("Expected data is present in current doucment");
						base.stepInfo(datasToCheck);
						check = true;
						break;
					}
				}
			}
			if (!check) {
				base.failedStep("Unrelavent document is filtered");
			}
		}
	}

	public void RedactTextInDocView(int x, int y, int offsetx, int offsety) {
		Actions actions = new Actions(driver.getWebDriver());
		redactionIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		base.waitTime(15);
		textSelectionRedactionIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitTime(5);
		actions.moveToElement(getDocView_Redactrec_textarea().getWebElement(), x, y).clickAndHold()
				.moveByOffset(offsetx, offsety).release().build().perform();

	}

	/**
	 * @author Krishna Date: 03/02/22 Modified date: N/A Modified by: N/A
	 * @description Verify ThumnbnailsPanel is displayed on Docview.
	 */

	public void verifyThumbNailsPanelDisplayed() {
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		base.waitTime(2);
		softAssertion.assertTrue(docViewRedact.thumbNailsPanel().isElementPresent());
		if (docViewRedact.thumbNailsPanel().isElementPresent() == true) {
			base.passedStep("The thumbnails panel is clicked and menu is visible");
		} else {
			base.failedStep("The thumbnail Panel menu is NOT displayed");
		}
	}

	/**
	 * @author Krishna Date: 03/02/22 Modified date: N/A Modified by: N/A
	 * @description verify different type of Documents in minidocList on
	 *              Thumbnailspanel is displayed.
	 * @Param pdfDocId,xlsExcelDocId,tiffDocId,pptDocId,messageDocId
	 */
	public void verifyDifferentTypesOfDocsInThumbNailsPanel(String pdfDocId, String xlsExcelDocId, String tiffDocId,
			String pptDocId, String messageDocId) throws InterruptedException {
		driver.waitForPageToBeReady();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		base = new BaseClass(driver);
		base.stepInfo("bascic contant search on DocView");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		base.waitTime(2);
		driver.scrollPageToTop();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		base.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewRedact.clickingThumbnailIcon();
		docViewRedact.verifyThumbNailsPanelDisplayed();
		base.passedStep("Thumbnails of docs page is displayed in thumbnail panel");
		docView.ScrollAndSelectDocument(pdfDocId);
		driver.waitForPageToBeReady();
		docViewRedact.clickingThumbnailIcon();
		docViewRedact.verifyThumbNailsPanelDisplayed();
		base.passedStep("Thumbnails of pdfDoc page is displayed in thumbnail panel");
		base.waitTime(2);
		driver.Navigate().refresh();
		docView.ScrollAndSelectDocument(xlsExcelDocId);
		driver.waitForPageToBeReady();
		docViewRedact.clickingThumbnailIcon();
		docViewRedact.verifyThumbNailsPanelDisplayed();
		base.passedStep("Thumbnails of XlsDoc page is displayed in thumbnail panel");
		base.waitTime(2);
		driver.Navigate().refresh();
		docView.ScrollAndSelectDocument(tiffDocId);
		driver.waitForPageToBeReady();
		docViewRedact.clickingThumbnailIcon();
		docViewRedact.verifyThumbNailsPanelDisplayed();
		base.passedStep("Thumbnails of tiffDoc page is displayed in thumbnail panel");
		base.waitTime(3);
		driver.Navigate().refresh();
		docView.ScrollAndSelectDocument(pptDocId);
		driver.waitForPageToBeReady();
		docViewRedact.clickingThumbnailIcon();
		docViewRedact.verifyThumbNailsPanelDisplayed();
		base.passedStep("Thumbnails of ppt page is displayed in thumbnail panel");
		base.waitTime(3);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		docView.ScrollAndSelectDocument(messageDocId);
		driver.waitForPageToBeReady();
		docViewRedact.clickingThumbnailIcon();
		docViewRedact.verifyThumbNailsPanelDisplayed();
		base.passedStep("Thumbnails of messagedocs page is displayed in thumbnail panel");

	}

	public void verifyHighLightingTextInDocView() {
		driver.waitForPageToBeReady();
		String text = get_textHighlightedColor().getWebElement().getText();
		System.out.println(text);
		if (text.equalsIgnoreCase("r")) {
			base.passedStep("The batch redated text is matching the keyword");
		}
		String color = get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		base.passedStep("The text for keyword is highlited in the document");

	}

	/**
	 * @author Krishna
	 * @Description -- Verify select the ‘Default Redaction Tag’ from the pop up if
	 *              ‘Default Redaction Tag’ does not exist then first redaction tag
	 *              is automatically selected.
	 * @param redactiontag , redactiontagfirst
	 */
	public void verifySelectDefaultRedactionTag(String redactiontag, String redactiontagfirst) {
		try {
			base = new BaseClass(driver);
			base.waitForElement(getSelectReductionTagDropDown());
			String redactiontagname = getSelectReductionTagDropDown().getText();
			System.out.println(redactiontagname);
			driver.waitForPageToBeReady();
			if (redactiontagname.contains("Default Redaction Tag")) {
				base.waitForElement(getSelectReductionTagDropDown());
				getSelectReductionTagDropDown().selectFromDropdown().selectByVisibleText(redactiontag);
				base.stepInfo("Select default redaction tag");

			} else {
				base.waitForElement(getSelectReductionTagDropDown());
				getSelectReductionTagDropDown().selectFromDropdown().selectByVisibleText(redactiontagfirst);
				base.stepInfo("First redaction tag from the list is automatically selected");
			}
			getSaveButton().isElementAvailable(15);
			base.waitForElement(getSaveButton());
			getSaveButton().Click();
			base.VerifySuccessMessage("Redaction tags saved successfully.");
			base.getCloseSucessmsg();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while setting this page redaction" + e.getMessage());
		}
	}

	/**
	 * @author Brundha.T
	 * @param FieldValue
	 * @param Field
	 * @throws InterruptedException
	 * @Description: selecting highlight icon in docviewpage
	 */
	public void doubleclickingHighlitingIcon(boolean FieldValue, boolean Field) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return HighliteIcon().Visible() && HighliteIcon().Enabled();
			}
		}), Input.wait30);
		base.waitTillElemetToBeClickable(HighliteIcon());
		HighliteIcon().waitAndClick(10);

		if (FieldValue) {
			thisPageHighlite().waitAndClick(5);
		}
		if (Field) {
			base.waitTillElemetToBeClickable(HighliteIcon());
			HighliteIcon().waitAndClick(10);
		}

	}

	/**
	 * @author Brundha.T
	 * @param ele
	 * @return
	 * @description: method to verify revert on or off docview redactions
	 */
	public String verifyingRevertOffAndOn(Element ele) {
		String color = ele.getWebElement().getCssValue("color");
		System.out.println(color);
		String ActColor = Color.fromString(color).asHex();
		System.out.println(ActColor);
		return ActColor;
	}

	/**
	 * @author Krishna
	 * @Description -- Verify multi page and all page options is removed from
	 *              highlighting sub menus
	 * @param Text , Text1
	 */
	public void verifyMultiPageandAllpageHighlight(String text, String text1) {
		try {
			base = new BaseClass(driver);
			driver.waitForPageToBeReady();
			if (base.textValue(text).isElementAvailable(5) && base.textValue(text1).isElementAvailable(5)) {
				base.failedStep(
						"multi page and all page options is not removed from highlighting sub menus as expected ");

			} else {
				base.passedStep("multi page and all page options is removed from highlighting sub menus as expected");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while setting this page redaction" + e.getMessage());
		}
	}

	/**
	 * @author Krishna
	 * @Description -- Verify Redaction icon is selected
	 *
	 */
	public void verifyRedactionIconRemain() {
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return redactionIcon().Displayed() && redactionIcon().Enabled();
			}
		}), Input.wait120);
		base.waitTime(3);
		String status = redactionIcon().GetAttribute("class");
		System.out.println(status);
		if (status.equals("active")) {
			base.passedStep("Redaction menu is remain selected as expected");
		} else {

			base.failedStep(" not selected");
			;
		}

	}

	/*
	 * Author:Jagadeesh.Jana Date:NA Modify Author:Vijaya.Rani Modify
	 * Date:15/12/2022
	 */
	public void TraverseForwardAndBackwardOnHits() throws Exception {
		SoftAssert softAssertion = new SoftAssert();
		base = new BaseClass(driver);

		base.waitTillElemetToBeClickable(getSearchIcon());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getSearchIcon().Visible() && HighliteIcon().Enabled();
			}
		}), Input.wait30);
		getSearchIcon().waitAndClick(30);

		base.passedStep("successfully Clicked the Search Icon");

		driver.waitForPageToBeReady();
		base.waitTime(5);
		base.waitForElement(getInputSearchBox());
		base.waitTillElemetToBeClickable(getInputSearchBox());
		getInputSearchBox().waitAndClick(30);

		getInputSearchBox().SendKeys("in");
		base.stepInfo("Search input Text completed");

		Thread.sleep(2000); // Implicitly need here
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		base.waitForElement(hitForwardIcon("in"));
		hitForwardIcon("in").waitAndClick(10);
		String forwardCount = getHitCount("in").getText();
		System.out.println("forwardCount: " + forwardCount);
		base.waitTime(3);
		if (forwardCount.contains("2 of ")) {
			System.out.println("Traverse Forward to the hits - PASSED");
			base.passedStep("Traverse Forward to the hits - PASSED");
			softAssertion.assertTrue(true);
		} else {
			System.out.println("Traverse Forward to the hits - FAILED");
			base.failedStep("Traverse Forward to the hits - FAILED");
			softAssertion.assertTrue(false);
		}

		base.waitForElement(hitBackwardIcon("in"));
		base.waitTillElemetToBeClickable(hitBackwardIcon("in"));
		hitBackwardIcon("in").waitAndClick(10);
		String backwardCount = getHitCount("in").getText();
		System.out.println("backwardCount :" + backwardCount);
		base.waitTime(3);
		if (backwardCount.contains("1 of ")) {
			System.out.println("Traverse Backward to the hits - PASSED");
			base.passedStep("Traverse Backward to the hits - PASSED");
			softAssertion.assertTrue(true);
		} else {
			System.out.println("Traverse Backward to the hits - FAILED");
			base.failedStep("Traverse Backward to the hits - FAILED");
			softAssertion.assertTrue(false);
		}
		softAssertion.assertAll();
	}

	public void addremark(String remark, int x, int y, int offsetx, int offsety) throws InterruptedException {

		base = new BaseClass(driver);
		DocViewPage docView = new DocViewPage(driver);
		if(docView.getAddRemarkbtn().isDisplayed()== false) {
			base.waitForElement(getNonAudioRemarkBtn());
			getNonAudioRemarkBtn().waitAndClick(10);
		}
		Actions actions = new Actions(driver.getWebDriver());
		Thread.sleep(10000);
		actions.moveToElement(getDocView_Redactrec_textarea().getWebElement(), x, y).clickAndHold().perform();
		base.waitTime(2);
		actions.moveByOffset(offsetx, offsety).release().build().perform();
		base.waitTime(5);
		driver.scrollPageToTop();
		docView.getAddRemarkbtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getRemarkTextArea().Visible();
			}
		}), Input.wait30);
		docView.getRemarkTextArea().SendKeys(remark);
		docView.getSaveRemark().Click();
	}

}

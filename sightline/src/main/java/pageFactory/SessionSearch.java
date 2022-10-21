package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.Log;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class SessionSearch {

	Driver driver;
	public static int pureHit;
	BaseClass base;
	DocViewMetaDataPage docViewMetaDataPage;
	SoftAssert softAssert;
	DocViewRedactions docViewRedact;
	Categorization categorize;
	UserManagement userManage;
	AssignmentsPage assignPage;

	public static String selectedProductionName;
	Map<String, Integer> pureHitCountMapping = new HashMap<String, Integer>();

	public Element getEnterSearchString() {
		return driver.FindElementByXPath(".//*[@id='xEdit']/li/input");
	}

	public Element getSearchButton() {
		return driver.FindElementById("btnBasicSearch");
	}

	public Element getQuerySearchButton() {
		return driver.FindElementById("qSearch");
	}

	// updated 6/10/21
	public Element getQuerySearchButtonC() {
		return driver.FindElementByXPath("//div[contains(@id,'tabs-') and contains(@style,'block')]//a[@id='qSearch']");
	}

	public Element getSaveAsNewSearchRadioButton() {
		return driver.FindElementByXPath("//*[@id='saveAsNewSearchRadioButton']/following-sibling::i");
	}

	// Hits
	public Element getPureHitsCount() {
		return driver.FindElementByXPath(".//*[@id='001']/span/count");
	}

	public Element getPureHitsCountwithOptions() {
		return driver.FindElementByXPath(".//*[@id='001']/count");
	}

	// modified 9/30 - Generic
	public Element getPureHitsCount2ndSearch() {
		return driver.FindElementByXPath("(//*[@id='001']/span/count)[last()]");
	}

	public Element getTDHitsCount() {
		return driver.FindElementByXPath(".//*[@id='002']/span/count");
	}

	public Element getNDHitsCount() {
		return driver.FindElementByXPath(".//*[@id='003']/span/count");
	}

	public Element getFMHitsCount() {
		return driver.FindElementByXPath(".//*[@id='004']/span/count");
	}

	public Element getCSHitsCount() {
		return driver.FindElementByXPath(".//*[@id='005']/span/count");
	}

	public Element getNewSearchPureHitsCount(int num) {
		return driver.FindElementByXPath("(//*[@id='001']/span/count)[" + num + "]");
	}

	// added by jeevitha
	public Element getWorkProductTextBox() {
		return driver.FindElementByXPath("//div[@id='workproduct']//input[@class='textboxlist-bit-editable-input']");
	}

	public Element getSelectSampleMethod() {
		return driver.FindElementByXPath("//select[@id='sampleMethod']");
	}

	public Element getSelectSampleMethodOptions(String Option) {
		return driver.FindElementByXPath("//select[@id='sampleMethod']/option[text()='" + Option + "']");
	}

	public Element getTextBoxLableOfSampleMethod() {
		return driver.FindElementByXPath("//label[@id='CountToAssignLabel']");
	}

	public Element getTextBoxLableRemovedOfSampleMethod() {
		return driver.FindElementByXPath("//div[@id='divNumberToAssign' and @style='display: none;']");
	}

	public Element getShowDocumentCountToggle() {
		return driver.FindElementByXPath("//i[@id='showUnAssignDocCounts']");
	}

	public Element getAssignmentToUnAssign(String assignName) {
		return driver.FindElementByXPath("//*[@id='jstreeUnAssign']//a[contains(text(),'" + assignName + "')]");
	}

	public Element getToolTipTextInAssignUnassignPopUp() {
		return driver.FindElementByXPath("//div[@role='tooltip']/div[@class='popover-content']");
	}

	public Element getNoOfDocInProject() {
		return driver.FindElementByXPath("//span[@class='switchTip']//following-sibling::span//label");
	}

	public Element contentAndMetaDataResult() {
		return driver.FindElementByXPath("//td[text()='Content and Metadata']/parent::tr//span[@class='badge']");
	}

	public Element getFromBatesBtn() {
		return driver.FindElementByXPath("//input[@id='BatesFrom']");
	}

	public Element getToBatesBtn() {
		return driver.FindElementByXPath("//input[@id='BatesTo']");
	}

	public Element getSearchTerm() {
		return driver.FindElementByXPath("(//td[@class='value']//input)[last()]");
	}

	public Element getErrorMsg() {
		return driver.FindElementByXPath("//div[@id='validationErrMsg']");
	}

	public ElementCollection getSearchPanelCount() {
		return driver.FindElementsByXPath("//ul[@id='sessionSearchList']//li");
	}

	public Element getWarningMsg() {
		return driver.FindElementByXPath("//div[@class='MessageBoxMiddle']//div[contains(@style,'overflow')]");
	}

	public Element getAnalysisOfCommExplorer() {
		return driver.FindElementByXPath("//li[@id='CommunicationsExplorerOpt']");
	}

	public Element getAnalysisOfConceptExplorer() {
		return driver.FindElementByXPath("//li[@id='ConceptExplorerOpt']");
	}

	public Element getNoBtn() {
		return driver.FindElementByXPath("//button[@id='bot2-Msg1']");
	}

	public Element getConceptualCount_last() {
		return driver.FindElementByXPath("(.//*[@id='005']/span/count)[last()]");
	}

	public Element getConceptualCountPlayButton() {
		return driver.FindElementByXPath("(//i[@Class='fa fa-play-circle-o playButton'])[last()]");
	}

	public Element getConceptualCountDisplayed() {
		return driver.FindElementByXPath(
				"(.//*[@id='005']/span/count)[last()]/../preceding-sibling::i[@style='display: block;']");
	}

	public Element getModifyASearch_Current() {
		return driver.FindElementByXPath("//div[@style='display: block;']//a[@id='qModifySearch']");
	}

	public Element getViewBtn() {
		return driver.FindElementByXPath("//a[text()='View']");
	}

	public Element getPersistantHitCb_Existing() {
		return driver.FindElementByXPath("//div[@id='existingassignment']//div//label[@class='checkbox']//i");
	}

	public Element getPinBtn(String searchName) {
		return driver.FindElementByXPath(
				"//span[text()='SS:" + searchName + "']//following::span[@class='pin-search']//button");
	}

	public Element getUnpinToolTipMsg() {
		return driver.FindElementByXPath("//span[@class='pin-search pinned']");
	}

	public Element getThreadedCount() {
		return driver.FindElementByXPath(".//*[@id='002']/span/count");
	}

	public Element getNearDupeCount() {
		return driver.FindElementByXPath(".//*[@id='003']/span/count");
	}

	public Element getFamilyCount() {
		return driver.FindElementByXPath(".//*[@id='004']/span/count");
	}

	public Element getConceptualCount() {
		return driver.FindElementByXPath(".//*[@id='005']/span/count");
	}

	public Element getSaveSearchButton() {
		return driver.FindElementByXPath("SaveSearch_Button");
	}

	public Element getPureHitAddButton() {
		return driver.FindElementByXPath(".//*[@id='001']/i[2]");
	}

	// Bulk tag and folder
	// public Element getBulkActionButton(){ return
	// driver.FindElementById("idAction"); }
	public Element getBulkActionButton() {
		return driver.FindElementByXPath("//*[@id='idAction']");
	}

	public Element getBulkTagAction() {
		return driver.FindElementByXPath("//a[contains(text(),'Bulk Tag')]");
	}

	public Element getTagsAllRoot() {
		return driver.FindElementByXPath("//*[@id='tagsJSTree']//*[@id='-1_anchor']");
	}

	public Element getEnterTagName() {
		return driver.FindElementById("txtTagName");
	}

	public Element getBulkFolderAction() {
		return driver.FindElementByXPath("//a[contains(text(),'Bulk Folder')]");
	}

	public Element getBulkNewTab() {
		return driver.FindElementById("tabNew");
	}

	public Element getBulkTagConfirmationButton() {
		return driver.FindElementByXPath("//button[contains(text(),'Ok')]");
	}

	public Element getFolderAllRoot() {
		return driver.FindElementByXPath("//*[@id='folderJSTree']//*[@id='-1_anchor']");
	}

	public Element getEnterFolderName() {
		return driver.FindElementById("txtFolderName");
	}

	public Element getContinueCount() {
		return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']");
	}

	public Element getContinueButton() {
		return driver.FindElementByXPath(".//*[@id='divBulkAction']//button[contains(.,'Continue')]");
	}

	// public Element getContinueButton(){ return driver.FindElementById("btnAdd");
	// }
	// public Element getContinueButton(){ return
	// driver.FindElementByXPath("//*[@id=\"btnAdd\"]"); }
	public Element getFinalCount() {
		return driver.FindElementByXPath("//span[@id='spanTotal']");
	}

	public Element getFinalizeButton() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	public Element getFolderTab() {
		return driver.FindElementByXPath("//a[contains(text(),'Folders')]");
	}

	public Element getToggleDocCount() {
		return driver.FindElementByXPath("folderDocCount");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	// Actions
	public Element getas_Action_Exportdata() {
		return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[text()='Export Data']");
	}

	public Element getDocListAction() {
		return driver.FindElementByXPath("//a[contains(text(),'View In DocList')]");
	}

	public Element getDocViewAction() {
		return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'View In DocView')]");
	}

	public Element getDocViewActionDL() {
		return driver.FindElementByXPath("//a[contains(.,'View in DocView')]");
	}

	public Element getTallyResults() {
		return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Tally Results')]");
	}

	public Element getBulkAssignAction() {
		return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Bulk Assign')]");
	}

	public Element getBulkReleaseAction() {
		return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Bulk Release')]");
	}

	public Element getBulkReleaseActionDL() {
		return driver.FindElementByXPath("//a[contains(.,'Bulk Release')]");
	}

	// save search
	public Element getSaveSearch_Button() {
		return driver.FindElementById("btnSaveSearch");
	}

	// save search- stabilized change
	public Element getSaveSearch_Btn() {
		return driver.FindElementByXPath("(//*[@id='btnSaveSearch'])[last()]");
	}

	public Element getAdvanceS_SaveSearch_Button() {
		return driver.FindElementByXPath(
				"//div[@class='searchInput smart-accordion-default']//div[@class='row']//*[@id='qSave']");
	}

	public Element getSavedSearch_MySearchesTab() {
		return driver.FindElementById("-1_anchor");
	}

	public Element getSaveSearch_Name() {
		return driver.FindElementById("txtSaveSearchName");
	}

	public Element getSaveSearch_SaveButton() {
		return driver.FindElementById("btnEdit");
	}

	public Element getBulkRelDefaultSecurityGroup_CheckBox(String SG) {
		return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[text()='" + SG + "']/../div[1]/label/i");
	}

	public Element getBulkRelOther_CheckBox(String SGname) {
		return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[text()='" + SGname + "']/../div[1]/label/i");
	}

	public Element getBulkRelease_ButtonRelease() {
		return driver.FindElementById("btnRelease");
	}

	// Added by Arunkumar
	public Element getBulkRelease_ButtonUnRelease() {
		return driver.FindElementByXPath("//button[@id='btnUnrelease']");
	}

	public Element getSearchCriteriaValue() {
		return driver.FindElementByXPath("//td[@class='value']//span");
	}

	public Element getCountUniqueDocId() {
		return driver.FindElementByXPath("//h1[@class='page-title']//label");
	}

	public Element getMasterDate() {
		return driver.FindElementByXPath("//td[@class=' formatDate']");
	}

	public Element getMetaDataInDropdown(String field) {
		return driver.FindElementByXPath("//select[@id='metatagSelect']//option[@value='" + field + "']");
	}

	// Metadata
	public Element getBasicSearch_MetadataBtn() {
		return driver.FindElementById("metadataHelper");
	}

	public Element getAdvanceSearch_MetadataBtn() {
		return driver.FindElementByXPath("(//*[@id='metadataHelper'])[2]");
	}

	public Element getSelectMetaData() {
		return driver.FindElementById("metatagSelect");
	}

	public Element getMetaOption() {
		return driver.FindElementById("selectOp");
	}

	public Element getMetaDataSearchText1() {
		return driver.FindElementById("val1");
	}

	public Element getMetaDataSearchText2() {
		return driver.FindElementById("val2");
	}

	public Element getMetaDataInserQuery() {
		return driver.FindElementById("insertQueryBtn");
	}

	// Advance Saerch -modified 6/10/21
	public Element getAdvancedSearchLink() {
		return driver.FindElementByXPath("//*[@id='advancedswitch']");
	}

	public Element getWorkProductTags() {
		return driver
				.FindElementByXPath("//div[contains(@id,'tabs-') and contains(@style,'block')]//button[text()='Tags']");
	}

	public Element selectWorkProductTags(String tagName) {
		return driver.FindElementByXPath(
				"((//h4[text()='Tags']//following::div[@class='panel-collapse']//ul[@class='jstree-children'])[1]//a[text()='"
						+ tagName + "']//i)[1]");
	}

	public Element getInsertInToQueryBtn() {
		return driver.FindElementByXPath("//a[text()=' Insert into Query']");
	}

	// advanced Content search -modified
	public Element getAdvancedContentAndMetadataInputArea() {
		return driver.FindElementByXPath(
				"//div[contains(@id,'tabs-') and contains(@style,'block')]//ol[contains(@class,'Adv')]//div[@id='contentmetadata']//div//input");
	}

	public Element getSavedSearch_MySearchesClosedArrow() {
		return driver.FindElementByXPath("//li[contains(@class,'closed')]//a[@id='-1_anchor']//..//i");
	}

	public Element getSavedSearch_MySearchesOpenArrow() {
		return driver.FindElementByXPath("(//li[contains(@class,'open')]//a[@id='-1_anchor']//..//i)[1]");
	}

	public Element getSavedSearch_MySearchesNewNodeClosed() {
		return driver.FindElementByXPath(
				"//a[@data-content='My Saved Search']//following-sibling::ul//li[last()][contains(@class,'closed')]//i");
	}

	public Element getSavedSearch_MySearchesLastNode(String nodeName) {
		return driver.FindElementByXPath(
				"//a[@data-content='My Saved Search']//following-sibling::ul//li//a[text()='" + nodeName + "']");
	}

	public Element getSavedSearch_DefaultSecurityGroupTabClosed() {
		return driver.FindElementByXPath(
				"//li[contains(@class,'closed') and contains(@aria-labelledby,'anchor')]//a[contains(text(),'Default Security Group')]");
	}

	// Added by Raghuram A - 9/30
	public Element getSavedSearchExpandStatsC() {
		return driver.FindElementByXPath(
				"//li[@role='treeitem']//a[text()='All']//..//..//li[@class='jstree-node  jstree-closed jstree-last']//i[@class='jstree-icon jstree-ocl']");
	}

	public Element getSaveSearchPopupFolderName_Expand(String name) {
		return driver.FindElementByXPath(
				"(// a[contains(text(),'" + name + "')])[last()]//parent::li[contains(@class,'closed')]");
	}

	public Element getSavedSearchChecked(String name) {
		return driver.FindElementByXPath("//a[contains(@class,'clicked') and text()='" + name + "']");
	}

	public Element getAdvancedSearchLinkCurrent() {
		return driver.FindElementByXPath("(//*[@id='advancedswitch'])[last()]");
	}

	public ElementCollection gettTileSpinningList() {
		return driver.FindElementsByXPath("//span//count[contains(@id,'tilecount-')]");
	}

	public ElementCollection gettTileSpinningList(String searchName, int num) {
		return driver.FindElementsByXPath("//span[@style=\"margin-bottom:3px;\" and text()='" + searchName
				+ " ']//span[@id='divSearchCnt' and text()='" + num
				+ "']//..//..//..//..//..//span//count[contains(@id,'tilecount-')]");
	}

	public Element getContentAndMetaDatabtnCurrent() {
		return driver.FindElementByXPath("(//button[@id='contentmetadata'])[last()]");
	}

	public Element getAdvanceSearch_btn_Current() {
		return driver.FindElementByXPath("(//a[@id='qSearch'])[last()]");
	}

	public Element getContentAndMetaDatabtn() {
		return driver.FindElementByXPath("//button[@id='contentmetadata']");
	}

	public Element getWorkproductBtn() {
		return driver.FindElementByXPath("//button[@id='workproduct']");
	}

	public Element getSavedSearchBtn() {
		return driver.FindElementById("savedSearchesHelper");
	}

	public Element getSavedSearchBtn1() {
		return driver.FindElementById("savedSearchesHelper");
	}

	public Element getSavedSearchName(String savedSearchName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'" + savedSearchName + "')]");
	}

	public ElementCollection getTree() {
		return driver.FindElementsByXPath("//a[@class='jstree-anchor'][contains(text(),'')]");
	}

	public ElementCollection getSecurityNamesTree() {
		return driver.FindElementsByXPath("//*[@id='JSTree']/div/label");
	}

	// advanced Content search
	public Element getAdvancedContentSearchInput() {
		return driver.FindElementByXPath(
				"//*[@id='c-1']//*[@id='contentmetadata']//*[@id='xEdit']/li/input[@autocomplete='on']");
	}

	// advanced Content search - Generic 9/30
	public Element getAdvancedContentSearchInputCurrent() {
		return driver.FindElementByXPath(
				"(//*[@id='contentmetadata']//*[@id='xEdit']/li/input[@autocomplete='on'])[last()]");
	}

	// Audio Saerch
	public Element getAs_Audio() {
		return driver.FindElementById("audio");
	}

	public Element getAs_AudioLanguage() {
		return driver.FindElementById("audioLanguage");
	}

	public Element getAs_AudioText() {
		return driver.FindElementByXPath("(//*[@id='xEdit']/li/input)[3]");
	}

	// 10/6/21
	public Element getAs_AudioC() {
		return driver
				.FindElementByXPath("//div[contains(@id,'tabs-') and contains(@style,'block')]//button[@id='audio']");
	}

	public Element getAs_AudioLanguageC() {
		return driver.FindElementByXPath(
				"//div[contains(@id,'tabs-') and contains(@style,'block')]//select[@id='audioLanguage']");
	}

	public Element getAs_AudioTextC() {
		return driver.FindElementByXPath(
				"//div[contains(@id,'tabs-') and contains(@style,'block')]//span[text()='Audio']//..//..//following-sibling::div//div[@class='textboxlist']//input");
	}

	// Advance search combinations
	public Element getAs_AudioText_Combination() {
		return driver.FindElementByXPath("(//*[@id='xEdit']/li/input)[6]");
	}

	public Element getAs_SelectOperation(int condition) {
		return driver.FindElementByXPath("(//*[@id='op']/section/div[1]/label/select)[" + condition + "]");
	}

	public Element getSelectAssignmentExisting(String assignmentName) {
		return driver.FindElementByXPath("//*[@id='jstreeComplete']//a[contains(.,'" + assignmentName + "')]");
	}

	// --- added 10/6/21
	public Element getContentAndMetaDatabtnC() {
		return driver.FindElementByXPath(
				"//div[contains(@id,'tabs-') and contains(@style,'block')]//button[@id='contentmetadata']");
	}

	public Element getWorkproductBtnC() {
		return driver.FindElementByXPath(
				"//div[contains(@id,'tabs-') and contains(@style,'block')]//button[@id='workproduct']");
	}

	public Element getWorkProductTagsC() {
		return driver
				.FindElementByXPath("//div[contains(@id,'tabs-') and contains(@style,'block')]//button[text()='Tags']");
	}

	public Element selectWorkProductTagsC(String tagName) {
		return driver.FindElementByXPath(
				"((//h4[text()='Tags']//following::div[@class='panel-collapse']//ul[@class='jstree-children'])[1]//a[text()='"
						+ tagName + "']//i)[1]");
	}

	// Conceptual
	public Element getAs_Conceptual() {
		return driver.FindElementByXPath("//button[@id='conceptual']");
	}

	public Element getAs_ConceptualTextArea() {
		return driver.FindElementByXPath("//textarea");
	}

	public Element getSelectFolderExisting(String folderName) {
		return driver.FindElementByXPath("//*[@id='divBulkFolderJSTree']//a[contains(.,'" + folderName + "')]/i[1]");
	}

	public Element getBulkUntagbutton() {
		return driver.FindElementByXPath("//*[@id='toUnassign']/following-sibling::i");
	}

	public Element getSelectTagExisting(String tagName) {
		return driver.FindElementByXPath("//*[@id='divBulkTagJSTree']//a[contains(.,'" + tagName + "')]");
	}

	public Element getBulkUnFolderbutton() {
		return driver.FindElementByXPath("//*[@id='toUnfolder']/following-sibling::i");
	}

	public Element getWP_FolderBtn() {
		return driver.FindElementById("foldersHelper");
	}

	public Element getWP_TagBtn() {
		return driver.FindElementById("tagsHelper");
	}

	public Element getWP_assignmentsBtn() {
		return driver.FindElementById("assignmentsHelper");
	}

	public Element getWP_SelectFolderName(String FolderName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'" + FolderName + "')]");
	}

	public Element getWP_SelectTagName(String TagName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'" + TagName + "')]");
	}

	public Element getWP_SelectRedactionName(String redactName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'" + redactName + "')]");
	}

	public Element getWP_SelectSGName(String sgName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'" + sgName + "')]");
	}

	public Element getWP_SelectProductionName(String productionName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'" + productionName + "')]");
	}

	public Element getRedactionBtn() {
		return driver.FindElementById("redactionsHelper");
	}

	public Element getProductionBtn() {
		return driver.FindElementById("productionsHelper");
	}

	public Element getSecurityGrpBtn() {
		return driver.FindElementById("SecurityGroupsHelper");
	}

	public Element getOperatorDD() {
		return driver.FindElementByXPath("(//*[@id='Tabs']//button[contains(text(),'Operator')])[2]");
	}

	public Element getOperatorAND() {
		return driver.FindElementByXPath("(//*[@id='opAND'])[2]");
	}

	public Element getOperatorOR() {
		return driver.FindElementByXPath("(//*[@id='opOR'])[2]");
	}

	public Element getOperatorNOT() {
		return driver.FindElementByXPath("(//*[@id='opNOT'])[2]");
	}

	// Conceptual Search right, left and mid
	public Element getConceptualRight() {
		return driver.FindElementByXPath("//span[@class='irs-line-right']");
	}

	public Element getConceptualLeft() {
		return driver.FindElementByXPath("//span[@class='irs-line-left']");
	}

	public Element getConceptualMid() {
		return driver.FindElementByXPath("//span[@class='irs-line-mid']");
	}

	// Audio
	public Element getAudioTermOperator() {
		return driver.FindElementByXPath("//*[@id='operator']");
	}

	public Element getAudioLocation() {
		return driver.FindElementByXPath("//*[@id='location']");
	}

	public Element getAudioTimeDiff() {
		return driver.FindElementByXPath("//*[@id='timeDiff']");
	}

	// Doclist
	public Element getTallyContinue() {
		return driver.FindElementByXPath("//*[@id='bot1-Msg1']");
	}

	// search query alerts
	public Element getQueryAlertGetText() {
		return driver.FindElementByXPath("//*[@id='Msg1']/div/div[1]");
	}

	public Element getQueryAlertGetTextSingleLine() {
		return driver.FindElementByXPath("//*[@id='Msg1']/div/p");
	}

	// shilpi
	public Element getSaveSearch_AdvButton() {
		return driver.FindElementByXPath(".//*[@id='tabsResult-1']//a[@id='qSave']");
	}

	// conceptually playBtn
	public Element getConceptuallyPlayButton() {
		return driver.FindElementByXPath("//*[contains(@id,'playButton')]");
	}

	// modify advanced search
	public Element getModifyASearch() {
		return driver.FindElementById("qModifySearch");
	}

	// added by shilpi
	public Element getThreadedAddButton() {
		return driver.FindElementByXPath(".//*[@id='002']/i[2]");
	}

	public Element getNearDupesAddButton() {
		return driver.FindElementByXPath(".//*[@id='003']/i[2]");
	}

	public Element getFamilyAddButton() {
		return driver.FindElementByXPath(".//*[@id='004']/i[2]");
	}

	public Element getConceptAddButton() {
		return driver.FindElementByXPath(".//*[@id='005']/i[2]");
	}

	// added by shilpi on 17-07
	public Element getRemovedocsfromresult() {
		return driver.FindElementByXPath("//*[@class='fa fa-times-circle ui-removeTile']");
	}

	public Element getsavesearch_overwrite() {
		return driver.FindElementByXPath("//*[@id='overwriteRadioButton']/following-sibling::i");
	}

	// Info messages/ tool tips
	public Element getSingleToolTipIcon() {
		return driver.FindElementByXPath("//*[@id='single']/section/label/i");
	}

	public Element getRangeToolTipIcon1() {
		return driver.FindElementByXPath("//*[@id='rangeVal']/div[1]/section/label/i");
	}

	public Element getRangeToolTipIcon2() {
		return driver.FindElementByXPath("//*[@id='rangeVal']/div[2]/section/label/i");
	}

	// tool tip messages
	public Element getRangeToolTipIconFirst() {
		return driver.FindElementByXPath("//*[@id='rangeVal']/div[1]/section/label/b");
	}

	public Element getRangeToolTipIconSecond() {
		return driver.FindElementByXPath("//*[@id='rangeVal']/div[2]/section/label/b");
	}

	// displayed in black background
	public Element getSingleToolTipText() {
		return driver.FindElementByXPath("//*[@id='single']/section/label/b");
	}

	public Element getRangeToolTip1Text() {
		return driver.FindElementByXPath("//*[@id='rangeVal']/div[1]/section/label/b");
	}

	public Element getRangeToolTip2Text() {
		return driver.FindElementByXPath("//*[@id='rangeVal']/div[2]/section/label/b");
	}

	// second search
	public Element getCopyTo() {
		return driver.FindElementByXPath("//*[@id='Basic']/div[1]/div/div[2]/div[1]/button");
	}

	public Element getCopyToNewSearch() {
		return driver.FindElementByXPath("//*[@id='Basic']/div[1]/div/div[2]/div[1]/ul/li[2]/a");
	}

	public Element getSecondSearchBtn() {
		return driver.FindElementByXPath("(//*[@id='btnBasicSearch'])[last()]");
	}

	public Element getSecondPureHit() {
		return driver.FindElementByXPath("(//*[@id='001']/span/count)[2]");
	}

	// Quick Batch added by shilpi
	public Element getQuickBatchAction() {
		return driver.FindElementByXPath("//a[contains(text(),'Quick Batch')]");
	}

	// search options
	public Element getadvoption_family() {
		return driver.FindElementByXPath("//*[@id='chkIncludeFamilyMember']/following-sibling::i");
	}

	public Element getadvoption_near() {
		return driver.FindElementByXPath("//*[@id='chkIncludeNearDuplicate']/following-sibling::i");
	}

	public Element getadvoption_threaded() {
		return driver.FindElementByXPath("//*[@id='chkIncludeThreadedDocuments']/following-sibling::i");
	}

	// Assignment distribution list
	public ElementCollection getadwp_assgn_distributedto() {
		return driver.FindElementsById("dist");
	}

	public Element getadwp_assgn_status() {
		return driver.FindElementById("statusSel");
	}
	// public Element getadvoption_threaded(){ return
	// driver.FindElementByXPath("//*[@id='chkIncludeThreadedDocuments']/following-sibling::i");
	// }

	// Addtile
	public Element getDocsMetYourCriteriaLabel() {
		return driver.FindElementByXPath("//label[text()='Docs That Met Your Criteria']");
	}

	// Action dropdown button
	public Element getActionDropDownButton() {
		return driver.FindElementById("idAction");
	}

	// Action Pad
	public Element getActionPad() {
		return driver.FindElementById("resultsCart");
	}

	// get doc view from drop down
	public Element getDocViewFromDropDown() {
		return driver.FindElementByXPath("//a[text()='View In DocView']");
	}

	// Query alert for proximity and regex search
	public Element getYesQueryAlert() {
		return driver.FindElementByCssSelector("#bot1-Msg1");
	}

	// added by Jayanthi On 26/7/2021 Advanced Search
	public Element getModifysearchOKBtn() {
		return driver.FindElementByXPath("//i[@class='glyphicon glyphicon-ok']");
	}

	public Element getModifysearchNOBtn() {
		return driver.FindElementByXPath("//i[@class='glyphicon glyphicon-remove']");
	}

	// Locator for query which needs to be modified in modify search
	public Element getModifiableSavedSearchQueryAS() {
		return driver.FindElementByXPath("(//span[@class='editable editable-click'])[1]");
	}

	// searchQueryName should be redactionName or ProductionName or assignmentName
	// which is selected .
	public Element getModifiableSearchQuery(String searchQueryName) {
		return driver.FindElementByXPath("(//li//span[contains(text(),'" + searchQueryName + "')])[1]");
	}

	public Element getNewSearchButton() {
		return driver.FindElementByXPath("//button[contains(text(),'New Search')]");
	}

	public Element getContentAndMetaDataBtn() {
		return driver.FindElementByXPath("//div[contains(@style,'display: block')]//button[@id='contentmetadata']");
	}

	public Element getQuerySearchBtn() {
		return driver.FindElementByXPath("//div[contains(@style,'display: block')]//*[@id='qSearch']");
	}

	public Element getPureHitsCounts() {
		return driver.FindElementByXPath("//*[@id='001']//i[contains(@class,'addTile')]");
	}

	public Element getPureHitAddBtn() {
		return driver.FindElementByXPath("//*[@id='001']//i[contains(@class,'addTile')]");
	}

	public Element getAdvancedContentSearchInputAudio() {
		return driver.FindElementByXPath(
				"//div[@id='Adv']//*[@id='contentmetadata']//*[@id='xEdit']/li/input[@autocomplete='on']");
	}

	// Added by jayanthi
	public ElementCollection getAutoSuggestSearchresults() {
		return driver.FindElementsByXPath("//li//div[@class='ui-menu-item-wrapper']");
	}

	public Element getQueryFromTextBox() {
		return driver.FindElementByXPath("//span[@class='editable editable-pre-wrapped editable-click']");
	}

	public Element getModifiableSavedSearchQueryDeleteBtnAS() {
		return driver.FindElementByXPath("(//a[@class='textboxlist-bit-box-deletebutton'])[1]");
	}

	public Element getPureHitsLastCount() {
		return driver.FindElementByXPath("(.//*[@id='001']/span/count)[last()]");
	}

	// get bulk folder from dropdown
	public Element getBulkFolderOption() {
		return driver.FindElementByXPath("//a[text()='Bulk Folder']");
	}

	// get select all folder option from tree
	public Element getSelectAllFoldersOption() {
		return driver.FindElementByXPath("//div[@id='folderJSTree']//a[text()='All Folders']");
	}

	// added on 28/9/21
	public Element getAudioModifiableQuery() {
		return driver.FindElementByXPath(("//li//span[@class='editable editable-pre-wrapped editable-click']"));
	}

	public Element getAudioQueryTextBox() {
		return driver.FindElementByXPath("(//input[@class='textboxlist-bit-editable-input'])[last()]");
	}

	public Element getAudioDropDownselectedValue(String option) {
		return driver.FindElementByXPath("//label//select[@id='audioLanguage']//option[text()='" + option + "']");
	}

	public Element getSaveSearch_ASButton() {
		return driver.FindElementByXPath("(.//*[@id='tabsResult-1']//a[@id='qSave'])[last()]");
	}

	public Element getPureHitsCount_ModifySearch() {
		return driver.FindElementByXPath("(.//*[@id='001']/span/count)[last()]");
	}

	public Element GetConfidenceThresholdInSecondSearchResult() {
		return driver.FindElementByXPath("(//table[@class='table table-striped']//td[4])[last()]");

	}
// end of added by jayanthi 28/9/21

	// Added By jeevitha
	// SaveSearch for shared With default security group
	public Element getSavedSearch_DefaultSgTab() {
		return driver.FindElementByXPath("//*[@id='1_anchor']");
	}

	// Save search In Search group
	public Element getSaveSearch_SearchGroupTab(String search) {
		return driver.FindElementByXPath("//*[contains(@class,'jstree-anchor') and @data-content='" + search + "']");
	}

	public Element getSaveSearchDropDrown() {
		return driver.FindElementByXPath("//*[@class='jstree-node  jstree-closed']/i");
	}

	public String getNewNode() {
		return driver.FindElementByXPath("(//a[@data-content='My Saved Search']//following-sibling::ul//a)[last()]")
				.getText();
	}

	public Element getSavedSearch_MySearchesTabClosed() {
		return driver.FindElementByXPath("//li[contains(@class,'closed') and @aria-labelledby='-1_anchor']/i");
	}

	public Element getSavedSearch_MySearchesNewNode() {
		return driver.FindElementByXPath("//a[@data-content='My Saved Search']//following-sibling::ul//li[last()]");
	}

	public Element getRemoveLink() {
		return driver.FindElementByXPath("(//a[text()='Remove'])[2]");
	}

	public Element getBasicSearchLink() {
		return driver.FindElementByXPath("(//a[text()='Switch to Basic'])[2]");
	}

	public Element getAudioTextBox() {
		return driver.FindElementByXPath("(//*[@id='xEdit'])[5]//li/input");
	}

	// Modified by Raghuram 9-30-21
	public Element GetAdvSaveBtn_New() {
		return driver.FindElementByXPath("(//*[@id='qSave'])[last()]");
	}

	public Element getBsSecondSaveSearch_Button() {
		return driver.FindElementByXPath("(//a[@id='btnSaveSearch'])[last()]");
	}

	// added by jayanthi
	public ElementCollection getTree_productions() {
		return driver.FindElementsByXPath("//div[@id='JSTree']//span[@class='font-xs']");
	}

	// added by Jayanthi 23/8/21
	public Element selectReviewerInAssgnWP(String reviewer) {
		return driver.FindElementByXPath("//option[contains(text(),'" + reviewer + "')]");
	}

	public Element selectStatusInAssgnWp(String status) {
		return driver.FindElementByXPath("//select[@id='statusSel']/option[text()='" + status + "']");
	}

	public ElementCollection getAllSelectedAssgnReviewers() {
		return driver.FindElementsByXPath("//select[@id='dist']/option");
	}

	public Element getSelectedAssgnReviewers(int index) {
		return driver.FindElementByXPath("(//select[@id='dist']/option)['" + index + "']");
	}

	public Element getDocText(String fileName, int i) {
		return driver.FindElementByXPath("(//td[contains(text(),'" + fileName + "')])[" + i + "]");
	}

	public ElementCollection totalDocs(String fileName) {
		return driver.FindElementsByXPath("//td[contains(text(),'" + fileName + "')]");
	}

	public Element SelectFromDropDown(String dropdownName) {
		return driver.FindElementByXPath("//option[text()='" + dropdownName + "']");
	}

	public Element processingIcon() {
		return driver.FindElementByXPath("//div[@id='processingPopupDiv' and @style='display: none;']");
	}

	public Element selectExistingassgnGroup(String assignmentGroup) {
		return driver
				.FindElementByXPath("//div[@id='jstreeBulkAssignmentGroup']//ul[@class='jstree-children']//a[text()='"
						+ assignmentGroup + "']");
	}

	public Element getSelectFolder(int index) {
		return driver.FindElementByXPath(
				"(//a[@class='jstree-anchor' and @tabindex='-1']/i[@class='jstree-icon jstree-checkbox'])[" + index
						+ "]");
	}

	// added on 26/10/21 by jayanthi
	public Element getWarningAudioBlackListChar_Header() {
		return driver.FindElementByXPath("//div[@class='MessageBoxMiddle']//span");
	}

	public Element getWarningAudioBlackListChar_Message() {
		return driver.FindElementByXPath("//div[@class='MessageBoxMiddle']//p");
	}

	// added on 30/8/21
	public Element countOfDocsInBulkAssgnTree() {
		return driver.FindElementByCssSelector(
				"div[id='newassignmentdiv'] span[class='label bg-color-blue font-md loader text-align-center bulkActionsSpanLoder hide']");
	}

	// Added by Raghuram
	public Element getExistingFolderSelectionCheckBox(String folderName) {
		return driver.FindElementByXPath("//*[@id='divBulkFolderJSTree']//a[text()='" + folderName
				+ "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSaveSearchPopupTitle() {
		return driver.FindElementByXPath("//span[text()='Save Search']");
	}

	public Element getSaveSearchPopupRadioButton(String buttonName) {
		return driver.FindElementByXPath("//span[text()='" + buttonName + "']");
	}

	public Element getSaveSearchPopupFolderName(String name) {
		return driver.FindElementByXPath("//a[text()='" + name + "']");
	}

	public Element getEditSessionSearchTextField(int searchNum, String searchName) {
		return driver.FindElementByXPath(
				"//span[@class='editable editable-pre-wrapped editable-click' and text()='" + searchName + "']");
	}

	public Element getTextAreaEdit() {
		return driver.FindElementByXPath("//div[@class='editable-input']//textarea");
	}

	public Element getSavedSearch_MySearchesNewNode1(String Node) {
		return driver.FindElementByXPath(
				"//a[text()='My Saved Search']//following-sibling::ul//li//a[contains(text(),'" + Node + "')]");
	}

	// 9/29
	public Element getExpandAllTab() {
		return driver.FindElementByXPath("//li[@role='treeitem']//a[text()='All']//..//i");
	}

	public Element getExpandSecurityGroupOw() {
		return driver.FindElementByXPath(
				"(//li[@role='treeitem']//ul[@role='group']//a[text()='Shared with Default Security Group']//..//i)[1]");
	}

	public Element getNodeToOw(String nodeName) {
//		return driver.FindElementByXPath("(//a[text()='" + nodeName + "']//..//i)[1]");
		return driver.FindElementByXPath(
				"(//a[@class='jstree-anchor jstree-disabled'and text()='" + nodeName + "']//preceding-sibling::i)[1]");
	}

	public Element getSecurityGroupNewNode(String Node) {
		return driver.FindElementByXPath(
				"//a[text()='Shared with Default Security Group']//following-sibling::ul//li//a[contains(text(),'"
						+ Node + "')]");
	}

	public Element getChooseSearchToOverwrite(String searchName) {
		return driver
				.FindElementByXPath("//a[text()='" + searchName + "']//..//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getBaseInputDynamic() {
		return driver.FindElementByXPath("(//ul[@id='xEdit']//li//input[@autocomplete='on'])[last()]");
	}

	public Element getBasicSearchBtnDynamic() {
		return driver.FindElementByXPath("(//a[@id='btnBasicSearch'])[last()]");
	}

	public Element getCreatedNode(String nodeName) {
		return driver.FindElementByXPath("//a[text()='" + nodeName + "']");
	}

	public Element getExpandCurrentNode() {
		return driver.FindElementByXPath(
				"//a[@class='jstree-anchor jstree-clicked']//..//i[@class='jstree-icon jstree-ocl']");
	}

	public Element getBasicSaveBtnDynamic() {
		return driver.FindElementByXPath("(//*[@id='btnSaveSearch'])[last()]");
	}

	// end

	// Added by Mohan

	public Element getSavedSearchTreeNode() {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//li[4]//a");
	}

	public Element getSavedSearchGroupName(String name) {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//a[contains(text(),'" + name + "')]");
	}

	public ElementCollection getCounts() {
		return driver.FindElementsByXPath("//*[@id='SavedSearchGrid']/tbody/tr");
	}

	public ElementCollection getSavedSearchTermsListEmpty() {
		return driver.FindElementsByXPath("//td[text()='Your query returned no data']");
	}

	public Element getSavedSearchNodeCount() {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//ul//li");
	}

	public Element getSavedSearchTableCount() {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']//tbody/tr");
	}

	public Element getSavedSearchTableNoDatas() {
		return driver
				.FindElementByXPath("//*[@id='SavedSearchGrid']//td[contains(text(),'Your query returned no data')]");
	}

	public Element getPureHitsCountNumText() {
		return driver.FindElementByXPath("//*[@id='001']//span//count");
	}

	public Element getNearDupePureHitsCount() {
		return driver.FindElementByXPath("//*[@id='003']//i[contains(@class,'addTile')]");
	}

	public Element getMySavedSearch() {
		return driver.FindElementByXPath("//*[@id='jstree_demo']//a[contains(text(),'My Saved')]");
	}

	public Element getConceptPureHitsCount() {
		return driver.FindElementByXPath("//*[@id='005']//i[contains(@class,'addTile')]");
	}

	public Element getFamilyMemberPureHitsCount() {
		return driver.FindElementByXPath("//*[@id='004']//i[contains(@class,'addTile')]");
	}

	// added by Jagdeesh.jana
	public Element getDocviewImageTab() {

		return driver.FindElementByXPath("//a[@id='ui-id-2']");
	}

	// Added by jeevitha
	public Element getNodeOfSG(String nodeName) {
		return driver.FindElementByXPath("//a[text()='" + nodeName + "' and @class='jstree-anchor  jstree-disabled']");
	}

	public Element getAutoSuggest() {
		return driver.FindElementByXPath("//li[@class='ui-menu-item']//div");
	}

	public Element getMetaDataInsertedVAlue() {
		return driver.FindElementByXPath("(//div[@id='contentmetadata']//li//span)[2]");
	}

	public Element getSearchElementCount() {
		return driver.FindElementByXPath(
				"//div[@id='counterAllBatch']//..//..//following-sibling::div[contains(@class,'col-md-')]//div[contains(@id,'counterBatch_')]");
	}

	public Element getTrashCanIcon() {
		return driver.FindElementByXPath(
				"//div[@id='counterAllBatch']//..//..//following-sibling::div[contains(@class,'col-md-')]//div[contains(@id,'counterBatch_')]//following::div[contains(@class,'trashCan')]//i");
	}

	public Element getRedactBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-stop' and not(@style)]");
	}

	public Element getBatchRedactionCount() {
		return driver.FindElementById("counterAllBatch");
	}

	// added by jeevitha
	public Element getOperatorDDinBS() {
		return driver.FindElementByXPath("(//*[@id='Tabs']//button[contains(text(),'Operator')])");
	}

	public Element getOperatorANDinBS() {
		return driver.FindElementByXPath("(//*[@id='opAND'])");
	}

	public Element getOperatorORinBS() {
		return driver.FindElementByXPath("(//*[@id='opOR'])");
	}

	public Element getOperatorNOTinBS() {
		return driver.FindElementByXPath("(//*[@id='opNOT'])");
	}

	public Element getEnterStringInBSCurrent() {
		return driver.FindElementByXPath("(//*[@id='xEdit']/li/input[@autocomplete='on'])[last()]");
	}

	// end
	// Added By jayanthi
	public Element getCommentsFieldAndRemarks() {
		return driver.FindElementById("commentsHelper");
	}

	public Element getSelectField() {
		return driver.FindElementById("fieldSelect");
	}

	// added by jayanthi on 23/9/21
	public Element GetSliderConfidenceThreshold() {
		return driver.FindElementByXPath("//span[@class='irs-slider single']");

	}

	public Element GetConfidenceThresholdInSearchResult() {
		return driver.FindElementByXPath(("(//table[@class='table table-striped']//td//span)[5]"));

	}

	public Element getAllFoldersTabInwp() {
		return driver.FindElementById("-1g_anchor");

	}

	public Element getFirstFoldersTabInwp() {
		return driver
				.FindElementByXPath("(//a[@class='jstree-anchor']/ancestor::ul//ul[@class='jstree-children']/li/a)[1]");

	}

	public Element getSavedSearchQueryAS() {
		return driver.FindElementByXPath("//span[@class='editable editable-click']");
	}

	public Element getValueTextArea() {
		return driver.FindElementById("val1");

	}

	public Element getDoneIcon() {
		return driver.FindElementByXPath("//button[@class='btn btn-primary btn-sm editable-submit']");
	}

	public Element getSanitiztionFilter() {
		return driver.FindElementByXPath("//*[@style='max-width:320px !important']//i");
	}

	public Element getSelectSavedSearch() {
		return driver.FindElementById("144_anchor");

	}

	public Element getWarningPopupMsg() {
		return driver.FindElementById("divbigBoxes");

	}

	public Element getSelectProductionName(int rowno) {
		return driver.FindElementByXPath("(//div[@id='JSTree']//span[@class='font-xs'])[" + rowno + "]");

	}

	public Element getQueryTextArea() {
		return driver.FindElementByXPath("//span[@class='editable editable-pre-wrapped editable-click']");

	}

	public Element getDdnAlreadyProducedOption() {
		return driver.FindElementByXPath("//option[contains(text(),'Already Produced')]");

	}

	public Element getDdnSelectedForProductionOption() {
		return driver.FindElementByXPath("//option[contains(text(),'Selected for Productions')]");

	}

	// added by jayanthi
	public Element getAdvanceSearch_DraftQuerySave_Button() {
		return driver.FindElementByXPath("//div//a[@id='qSave']");
	}

	public Element getReleventMessage() {
		return driver.FindElementByXPath("//div[@id='tabsResult-1']//div[@id='divSearchInprogress']//h2");
	}

	public Element getspinningWheel() {
		return driver.FindElementByXPath("//div[@id= 'processingPopupDiv' and @style='']");
	}

	// added by Iyappan
	public Element getSaveSearch_AdvBtn() {
		return driver.FindElementByXPath("(//a[@id='qSave'])[last()]");
	}

	public Element getSaveSearchInASWithoutSearch() {
		return driver.FindElementByXPath("(//a[@id='qSave'])[1]");
	}

	public Element getSaveAsNewSearchBtn() {
		return driver.FindElementByXPath("//input[@id='saveAsNewSearchRadioButton']//parent::label/i");
	}

	public Element getAs_AudioTextBox_CombinationSearch() {
		return driver.FindElementByXPath("(//div[@id='audio']//div//ul[@id='xEdit']//li//input)");

	}

	public Element date(String Value) {
		return driver.FindElementByCssSelector("input[placeholder='" + Value + ":']");
	}

	public Element getSelectCopyAssignmentExisting(String assignmentName) {
		return driver
				.FindElementByXPath("(//*[@id='jstreeComplete']//a[contains(.,'" + assignmentName + "')])[last()]");
	}

	public Element processingIconInExportData() {
		return driver.FindElementByCssSelector("div[aria-describedby='DivExport'] img[id='imgLoadPM']");

	}

	public Element getExportPopupClose() {
		return driver
				.FindElementByXPath("//span[text()='Export']/parent::div/button[@class='ui-dialog-titlebar-close']");

	}

	public Element getSavedSearchCount(String serachName) {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[4]");
	}

	public Element getAudioQueryTextField() {
		return driver.FindElementByXPath("//div[@id='Adv']//ul[@id='xEdit']/li[last()]/input");
	}

	// Added on 10/6/21
	public Element getAs_ConceptualC() {
		return driver.FindElementByXPath(
				"//div[contains(@id,'tabs-') and contains(@style,'block')]//button[@id='conceptual']");
	}

	public Element getAs_ConceptualTextAreaC() {
		return driver.FindElementByXPath("//div[contains(@id,'tabs-') and contains(@style,'block')]//textarea");
	}

	// Added by Jeevitha
	public Element draftQueryTextBS() {
		return driver.FindElementByXPath("//div[@id='divSearchInprogress']//h2");
	}

	public Element draftQueryTextAS() {
		return driver.FindElementByXPath("(//div[@id='divSearchInprogress']//h2)[2]");
	}

	public Element getContinueCountAssign() {
		return driver.FindElementByXPath(
				"//div[@id='newassignmentdiv']//div//div/div[@class='col-md-3 bulkActionsSpanLoderTotal']");
	}

	public Element getSavedAppear() {
		return driver.FindElementByXPath("(//*[@id='tabs2']/ul/li/a/span[@class='tablbl']/span[@class='SrchText'])[1]");
	}

	// Added by gopinath - 19/10/2021
	public Element getProcutionCheckBox(String productionName) {
		return driver.FindElementByXPath("//input[@value='" + productionName + "']/following-sibling::i");
	}

	public Element getNewSearch() {
		return driver.FindElementById("add_tab");
	}

	public ElementCollection getWorkproductBttn() {
		return driver.FindElementsByXPath("//button[@id='workproduct']");
	}

	public ElementCollection getDocCount() {
		return driver
				.FindElementsByXPath("//ul[@id='gallery']//*[@id='001']/span/label[@id='countType']/preceding::count");
	}

	public ElementCollection getProductionBttn() {
		return driver.FindElementsById("productionsHelper");
	}

	public Element getOverWrittenAllTabDD() {
		return driver.FindElementByXPath("//div[@id='searchTree']//ul//li//i");
	}

	public Element getSaveSearch_SaveButton_cc() {
		return driver.FindElementByXPath("(//button[@id='btnEdit'])[last()]");
	}

	public Element getSaveSearchPopupFolderName_cc(String name) {
		return driver.FindElementByXPath("(//a[contains(text(),'" + name + "')])[last()]//parent::li/i");
	}

	// Added by
	public Element getSelectNewSearchbtn() {
		return driver.FindElementByXPath("//button[@id='add_tab']");
	}

	public Element getSavedSearchResult() {
		return driver.FindElementByXPath(
				"//div[contains(@id,'tabs-') and contains(@style,'block')]//button[text()='Saved Search Results']");
	}

	public Element getSelectWorkProductSSResults(String SavedSearch) {
		return driver.FindElementByXPath(
				"((//h4[text()='Saved Search Results']//following::div[@class='panel-collapse']//ul[@class='jstree-children'])[1]//a[contains(text(),'"
						+ SavedSearch + "')]//i)[1][last()]");
	}

	//
	public Element getExistingTagSelectionCheckBox(String tagNmae) {
		return driver.FindElementByXPath(
				"//*[@id='divBulkTagJSTree']//a[text()='" + tagNmae + "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getObjectsInPreviewBox(String objectName) {
		return driver.FindElementByXPath("//span[text()='" + objectName + "']/parent::span/parent::div/label/input");
	}

	public Element getSearchBtn() {
		return driver.FindElementByXPath("//a[@name='Search']");
	}

	// Added by gopinath - 03/12/2021
	public Element getSearchSecondTextField() {
		return driver.FindElementByXPath(".//*[@id='xEdit']//li/following-sibling::li//input");
	}

	public Element getSaveSearchTextField() {
		return driver.FindElementByXPath("//input[@id='txtSaveSearchName']");
	}

	// Added by Aathith
	public Element getAllProductionBatesRanges() {
		return driver.FindElementByXPath("//*[@value='AllProductionBatesRanges']");
	}

	// Added by Jeevitha
	public Element getWhenAllResultsAreReadyPopUp() {
		return driver.FindElementByXPath(
				"//div[@class='MessageBoxButtonSection']//button[text()=' When all results are ready']");
	}

	public Element getWhenAllResultsAreReadyID() {
		return driver.FindElementByXPath("//div[@class='modal-body ui-dialog-content ui-widget-content']//b");
	}

	// Method to avoid abnormal termination
	public Element getSavedSearchNameResult(String savedSearchName) {
		return driver.FindElementByXPath("//a[text()='" + savedSearchName + "']");
	}

	// Added by Gopinath - 23/12/2021
	public Element getSaveButton() {
		return driver.FindElementByXPath("//a[@id='qModifySearch']//..//a[@id='qSave']");
	}

	public Element getBasicSearch_MetadataBtnSec() {
		return driver.FindElementByXPath("(//button[@id='metadataHelper'])[last()]");
	}

	public Element getCommentsFieldAndRemarksSec() {
		return driver.FindElementByXPath("(//button[@id='commentsHelper'])[last()]");
	}

	public Element getSearchButtonSec() {
		return driver.FindElementByXPath("(//a[@id='btnBasicSearch'])[last()]");
	}

	public Element getSearchName() {
		return driver.FindElementByXPath("//div[@id='Basic']//span[@class='font-lg']");
	}

	public Element getExclamationTile(String tileName) {
		return driver.FindElementByXPath("//a[@data-original-title='" + tileName
				+ "']//i[@class='fa fa-exclamation-triangle'" + " and contains((@style), 'display: none')] ");
	}

	public Element getRemovePureHit() {
		return driver.FindElementByXPath(
				"//label[text()='Docs That Met Your Criteria']//..//..//..//i[@title='Remove from Selected Results']");
	}

	// Added by Gopinath - 12/01/2022

	public Element getAssgn_NewAssignmnet() {
		return driver.FindElementById("tabnewAssignment");
	}

	public Element getbulkassgnpopup() {
		return driver.FindElementByXPath("//*[text()='Assign/Unassign Documents']");
	}

	public Element getContinueBulkAssign() {
		return driver.FindElementByXPath("//*[@id='divBulkAction']//button[contains(.,'Continue')]");
	}

	public Element getNumberOfAssignmentsToBeShown() {
		return driver.FindElementByXPath("//*[@id='GridAssignment_length']/label/select");
	}

	public Element getSelectAssignment(String assignmentName) {
		return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[td='" + assignmentName + "']");
	}

	public Element getAssignmentActionDropdown() {
		return driver.FindElementByXPath("//*[@id='ulActions']/../button[@class='btn btn-defualt dropdown-toggle']");
	}

	public Element getAssignmentName() {
		return driver.FindElementById("AssignmentName");
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

	public Element getAssignmentSaveButton() {
		return driver.FindElementByXPath("//input[@value='Save']");
	}

	public Element getAssgn_TotalCount() {
		return driver.FindElementByXPath("//div[@id='divBulkAction']//div//span[@id='spanTotal']");
	}

	public Element getAssignmentErrorText() {
		return driver.FindElementByXPath("//span[@id='AssignmentName-error']");
	}

	public Element getPersistantHitCheckBox() {
		return driver.FindElementByXPath("sdz");
	}

	public Element getDocViewActionGerman() {
		return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Dokumentviewer')]");
	}

	public Element getPureHitDroppedTileBtn() {
		return driver.FindElementByXPath("//i[@title='Remove from Selected Results']/ancestor::li/a/span/count");
	}

	public Element getQueryAlertGetTextHeader() {
		return driver.FindElementByXPath("//span[@class='MsgTitle']");
	}

	public Element getAdvSearchCopyToNewSearch() {
		return driver.FindElementByXPath(
				"(//*[@id=\"Adv\"]/div/div/button[@class='btn btn-default dropdown-toggle'])[last()]");
	}

	public ElementCollection getDetailsTable() {
		return driver.FindElementsByXPath("//table[contains(@id,'taskbasicPureHits')]//th");
	}

	public Element getDetailsTableToMap(int i) {
		return driver.FindElementByXPath("//table[contains(@id,'taskbasicPureHits')]//th[" + i + "]");
	}

	public ElementCollection getTableDetails(int index) {
		return driver.FindElementsByXPath("//table[contains(@id,'taskbasicPureHits')]//td[" + index + "]");
	}

	public ElementCollection getTotalRows() {
		return driver.FindElementsByXPath("// table[contains(@id,'taskbasicPureHits')]//tbody//tr");
	}

	public Element getCellValue(int rowNum, int colNum) {
		return driver.FindElementByXPath(
				"//table[contains(@id,'taskbasicPureHits')]//tbody//tr[" + rowNum + "]//td[" + colNum + "]");
	}

	public Element getCommentsFieldAndRemarks_AdvacnedSearch() {
		return driver.FindElementByXPath(
				"//ol[@class='dd-list smart-accordion-default Adv']//div//button[@id='commentsHelper']");
	}

	public Element getPinnedSearchIcon() {
		return driver.FindElementByXPath("//span[@title='Un Pin this Search']");
	}

	public Element getModifyASearch_Last() {
		return driver.FindElementByXPath("(//a[@id='qModifySearch'])[last()]");
	}

	public Element gettingAudioSearchTableRowValues(int i) {
		return driver.FindElementByXPath(
				"(//table[@class='table table-striped']//td/table[@class='table table-striped']/tbody/tr/td[" + i
						+ "]/span)[last()]");
	}

	public ElementCollection getAudioSearchTableHeaders() {
		return driver.FindElementsByXPath(
				"//table[@class='table table-striped']//td/table[@class='table table-striped']/thead/tr/th/strong");
	}

	public ElementCollection audioSearchTermResult() {
		return driver.FindElementsByXPath("//td[text()='Audio']/parent::tr//span[@class='badge']");
	}

	public Element languagePackInSearchResult() {
		return driver.FindElementByXPath("//span[@id='spnAudLang']");
	}

	public Element ConenetInSaerchResult() {
		return driver.FindElementByXPath("//td[text()='Content & MetaData']/parent::tr//span[@class='badge']");
	}

	public Element gettingThresholdValue() {
		return driver.FindElementByXPath("//span[@class='irs-single']");
	}

	// Added by Aathith
	public Element getCurrentAudioButton() {
		return driver.FindElementByXPath("(//button[@id='audio'])[last()]");
	}

	public Element getCurrentLanguageSelectButton() {
		return driver.FindElementByXPath("(//select[@id='audioLanguage'])[last()]");
	}

	public Element get_Current_As_AudioText() {
		return driver.FindElementByXPath("(//*[@id='xEdit']/li/input)[last()]");
	}

	public Element GetCurrentSliderConfidenceThreshold() {
		return driver.FindElementByXPath("(//span[@class='irs-slider single'])[last()]");

	}

	public Element getCurrentPureHitsCount() {
		return driver.FindElementByXPath("(//*[@id='001']/span/count)[last()]");
	}

	public Element getAdvancedSearchTextArea() {
		return driver.FindElementByXPath("//textarea[@class='form-control input-large']");
	}

	public Element getRemoveAddBtn() {
		return driver.FindElementByXPath("//i[@title='Remove from Selected Results']");
	}

	public Element getCurrentPureHitAddBtn() {
		return driver.FindElementByXPath("(//*[@id='001']//i[contains(@class,'addTile')])[last()]");
	}

	public Element getSavedSearchNewGroupExpand() {
		return driver.FindElementByXPath("// *[contains(@class,'clicked')]//preceding-sibling::i");
	}

	public Element getSavedSearchExpandStats() {
		return driver.FindElementByXPath("//a[@class='jstree-anchor jstree-clicked']//parent::li");
	}

	public ElementCollection getRemovePureHits() {
		return driver.FindElementsByXPath(
				"//label[text()='Docs That Met Your Criteria']//..//..//..//i[@title='Remove from Selected Results']");
	}

	public Element getWhenAllResultsAreReadyPopUpDynamic() {
		return driver.FindElementByXPath(
				"(//div[@class='MessageBoxButtonSection']//button[text()=' When all results are ready'])[last()]");
	}

	public Element getWhenAllResultsAreReadyIDDynamic() {
		return driver.FindElementByXPath("(//div[@class='modal-body ui-dialog-content ui-widget-content']//b)[last()]");
	}

	public Element getBulkTagConfirmationBtnDynamic() {
		return driver.FindElementByXPath("(//button[contains(text(),'Ok')])[last()]");
	}

	public Element getspinningPurehit(int i) {
		return driver.FindElementByXPath(
				"(//i[@class='fa fa-spinner fa-spin']/ancestor::li[@data-tile='purehit'])[" + i + "]");
	}

	public Element getspinningfamilyHit(int i) {
		return driver.FindElementByXPath(
				"(//i[@class='fa fa-spinner fa-spin']/ancestor::li[@data-tile='family'])[" + i + "]");
	}

	public Element getspinningThreadedCount(int i) {
		return driver.FindElementByXPath(
				"(//i[@class='fa fa-spinner fa-spin']/ancestor::li[@data-tile='thread'])[" + i + "]");
	}

	public Element getspinningNearDupeCount(int i) {
		return driver.FindElementByXPath(
				"(//i[@class='fa fa-spinner fa-spin']/ancestor::li[@data-tile='neardupe'])[" + i + "]");
	}

	public Element getSessionSearchList(String searchId) {
		return driver.FindElementByXPath("//span[@id='SearchCnt' and contains(text(),'" + searchId + "')]");
	}

	public Element getEnteredSearchText(String searchText) {
		return driver.FindElementByXPath("//span[text()='" + searchText + "']");
	}

	public ElementCollection getAllTilesResult(String searchNO) {
		return driver.FindElementsByXPath("(//span[@id='divSearchCnt' and contains(text(),'" + searchNO
				+ "')])[last()]//..//..//..//..//span//count");
	}

	public Element getBulkNavigationPopup() {
		return driver.FindElementByXPath("//span[text()='BulkNavigation']");
	}

	public Element getBulkNavigationPopupNoBtn() {
		return driver.FindElementByXPath(
				"//span[text()='BulkNavigation']/parent::div/following-sibling::div[contains(@class,'buttonpane') ]/div/button[@id='btnNo']");
	}

	public Element getBackGroundTaskAlertPopUp() {
		return driver.FindElementByXPath("(//span[contains(text(),'Background Task Alert')])[last()]");
	}

	public Element getBullHornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getViewAllBtn() {
		return driver.FindElementByXPath("//button[@id='btnViewAll']");
	}

	public ElementCollection getTableHeader_BGPage() {
		return driver.FindElementsByXPath("//table[@id='dt_basic']//th");
	}

	public Element getRowElement_BgPage(String BG_ID, int index) {
		return driver
				.FindElementByXPath("(//td[normalize-space(text())='" + BG_ID + "']/parent::tr/td)[" + index + "]");
	}

	public Element getPureHitCount(int i) {
		return driver
				.FindElementByXPath("(.//*[@data-original-title='Docs That Met Your Criteria']/span/count)[" + i + "]");
	}

	public Element getView() {
		return driver.FindElementByXPath("//a[@class='submenu-a']");
	}

	// Added by Gopinath - 08/04/2022
	public Element getViewOn() {
		return driver.FindElementByXPath("//li[@class='dropdown-submenu']//a[text()='View']");
	}

	public Element getViewInDocViewLat() {
		return driver.FindElementByXPath("//a[text()='View In DocView']");
	}

	public Element getCurrentTabClosed() {
		return driver.FindElementByXPath("//li[@aria-selected='true' and contains(@class,'closed')]");
	}

	public Element getCurrentTabClosedExpand() {
		return driver.FindElementByXPath(
				"//li[@aria-selected='true' and contains(@class,'closed')]//i[@class='jstree-icon jstree-ocl']");
	}

	public Element getBackGroundTask() {
		return driver.FindElementByXPath("//div[@id='bgTask']//em/following-sibling::span");

	}

	public Element getBullIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']/following-sibling::b");
	}

	public Element getSavedSearch_SharedWithPA() {
		return driver.FindElementById("-2_anchor");
	}

	// added by Aathith
	public Element getThreadedLastCount() {
		return driver.FindElementByXPath("(//*[@id='002']//count)[last()]");

	}

	public Element getNewSearch_MetadataBtn() {
		return driver.FindElementByXPath("(//*[@id='metadataHelper'])[last()]");
	}

	public Element getNewSelectMetaData() {
		return driver.FindElementByXPath("(//*[@id='metatagSelect'])[last()]");
	}

	public Element getBatchPrintBG() {
		return driver.FindElementByXPath(
				"(//div[@id='bgTask']//child::span[contains(text(),' Your Batch Print')])[position()=1]");
	}

	public Element getMouseOver_ToTextBoxString(String inputString) {
		return driver.FindElementByXPath("//span[text()='" + inputString + "']");
	}

	public Element getDeletePreviousSearch() {
		return driver.FindElementByXPath("//a[@class='textboxlist-bit-box-deletebutton']");
	}

	public Element getTaskBackGround(String id) {
		return driver.FindElementByXPath(
				"//a[text()='Your Navigation execution with Notification Id. " + id + " is COMPLETED']");
	}

	public Element getEnterSearchString_UsingPosition(int i) {
		return driver.FindElementByXPath("(.//*[@id='xEdit']/li/input)[position()=" + i + "]");
	}

	public Element getPureHit_UsingLast() {
		return driver.FindElementByXPath("(//a[@id='001']/span/count)[last()]");
	}

	public Element getSearchString2(int i) {
		return driver.FindElementByXPath(".//*[@id='xEdit']/li/input[" + i + "]");
	}

//Added by Krishna

	public Element getAdvScrnSearchLabel(String i) {
		return driver.FindElementByXPath("//div[@class='col-md-6']//span[text()='Search ']//span[text()='" + i + "']");
	}

	public Element getConceptualTileHit(int i) {
		return driver.FindElementByXPath(".//*[@id='005']/span/count[text()=" + i + "]");
	}

	public Element getTotalSelectedDocs() {
		return driver.FindElementByXPath("//span[@id='spanTotal']");
	}

	public Element getSelectSgPopup() {
		return driver.FindElementById("Edit User Group");
	}

	public Element getBellyBandTitleLabel() {
		return driver.FindElementByXPath(
				"//div[@class='MessageBoxMiddle']//child::span[text()='This search is in progress']");
	}

	public Element getIWantToWaitPopup() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[text()=' I want to wait']");
	}

	public Element getWhenPureHitsAreReadyPopup() {
		return driver.FindElementByXPath(
				"//div[@class='MessageBoxButtonSection']//button[text()=' When Pure Hits are ready']");
	}

	public Element getExecutedStatusinLHS(int searchNum) {
		return driver.FindElementByXPath(
				"//li[@lang='tabs-" + searchNum + "']//a//span[@id='idLastExecuted' and contains(text(),'Executed')]");
	}

	public Element getBulkAssignSelectedExistingAssignment() {
		return driver.FindElementByXPath("//li[@class='active']/a[@id='tabBasicToExtAssigmnment']");
	}

	public Element getBulkAssignAssignDocumentsButton() {
		return driver.FindElementByXPath("//*[@id='toAssign']/following-sibling::i");
	}

	public Element getWorkProductSearchResult() {
		return driver.FindElementByXPath("//td[text()='Work Product']/parent::tr//span[@class='badge']");
	}

	public Element getThreadedDocumentsAddBtn() {
		return driver.FindElementByXPath(
				"(//*[@data-original-title='Threaded Documents']//i[contains(@class,'addTile')])[last()]");
	}

	public Element getNearDuplicatesAddBtn() {
		return driver.FindElementByXPath(
				"(//*[@data-original-title='Near Duplicates']//i[contains(@class,'addTile')])[last()]");
	}

	public Element getFamilyMembersAddBtn() {
		return driver.FindElementByXPath(
				"(//*[@data-original-title='Family Members']//i[contains(@class,'addTile')])[last()]");
	}

	public Element getConceptuallySimilarAddBtn() {
		return driver.FindElementByXPath(
				"(//*[@data-original-title='Conceptually Similar']//i[contains(@class,'addTile')])[last()]");
	}

	public ElementCollection getRemoveTilesBtn() {
		return driver.FindElementsByXPath("//i[@title='Remove from Selected Results']");
	}

	public ElementCollection getAllTilesName() {
		return driver.FindElementsByXPath("(//ul[@id='gallery'])[last()]//li/a");
	}

	public Element getConceptualSearchResult() {
		return driver.FindElementByXPath("//td[text()='Conceptual']/parent::tr//span[@class='badge']");
	}

	public Element getBulkAssign_NewAssignment() {
		return driver.FindElementByXPath("//li[@id='tabnewAssignment']//a[text()='New Assignment']");
	}

	public Element getUnassign_ExistingAssignButton() {
		return driver.FindElementByXPath("//li[@class='existingunassignment active']/a[@id='tabExitingUnAssign']");
	}

	public ElementCollection getUnassign_ExistingAssignments() {
		return driver.FindElementsByXPath("//*[@id='jstreeUnAssign']//a");
	}

	public Element getUnAssignRadioBtn() {
		return driver.FindElementByXPath("//input[@id='toUnassign']/parent::label/i");
	}

	public Element getSelectedExistingAssignments() {
		return driver.FindElementByXPath("//*[@id='jstreeComplete']//a[@class='jstree-anchor jstree-clicked']");
	}

	public ElementCollection getassign_ExistingAssignments() {
		return driver.FindElementsByXPath("//*[@id='jstreeComplete']//a//parent::li");
	}

	public Element getSelectSavedSearchResult(String savedSearchResult) {
		return driver.FindElementByXPath("//a[contains(text(),'" + savedSearchResult + "')]");
	}

	public Element getExistingAssignmentToUnAssign(String assignName) {
		return driver.FindElementByXPath("//*[@id='jstreeUnAssign']//a[text()='" + assignName + "']");
	}

	public Element getPropagateFoldersCheckBox(int i) {
		return driver.FindElementByXPath(
				"(//*[@id='ss2']//div[@id='modalFolderAdd']//div//label[@class='checkbox']//i)[" + i + "]");
	}

	public Element getBulkAssignPopUpClosed() {
		return driver.FindElementByXPath(
				"//div[contains(@class,'assignmentbulkactionpopup') and contains(@style,'display: none')]");
	}

	public Element selectOperatorBetweenAdvancedSearchBlocks() {
		return driver.FindElementByXPath("(//div[@id='op']//select)[last()-1]");
	}

	public Element getbulkAssignTotalCountLoad() {
		return driver.FindElementByXPath(
				"//div[@class='col-md-3 bulkActionsSpanLoderTotal' and @style='padding-bottom: 5px; display: none;']");
	}

	public Element getContinueBtnDisabled() {
		return driver.FindElementByXPath(
				".//*[@id='divBulkAction']//button[contains(.,'Continue') and @class='btn btn-primary btn-sm disabled']");
	}

	public Element performBulkAction(String action) {
		return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'" + action + "')]");
	}

	public Element getTotalDocCount(String action) {
		return driver.FindElementByXPath(
				"//div[@id='" + action + "']//..//div[@class='col-md-3 bulkActionsSpanLoderTotal']");
	}
	// added by sowndarya
	public ElementCollection getStartDatesInBG() {
		return driver.FindElementsByXPath("//div[@id='dt_basic_wrapper']//table//tbody//tr//td[6]");
	}
	public Element getStartDateButton() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//th[6]");
	}
	public Element getStatusText(String id,String status) {
		return driver.FindElementByXPath("//td[contains(text(),'"+id+"')]//following-sibling::td[text()='"+status+"']");
	}

	public Element getTxtDownloadFile(String id) {
		return driver.FindElementByXPath(
				"//table[@id='dt_basic']//td[contains(text(),'" + id + "')]//..//a[contains(text(),'Download File')]");
	}

	// added by jayanthi
	public Element getContinueCount_ExistingAssign() {
		return driver.FindElementByXPath(
				"//div[@id='existingassignment']//div//div/div[@class='col-md-3 bulkActionsSpanLoderTotal']");
	}

	public Element getActionPopupCloseBtn() {
		return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']");

	}

	public SessionSearch(Driver driver) {
		this.driver = driver;
		// this.driver.getWebDriver().get(Input.url + "Search/Searches");
		base = new BaseClass(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		softAssert = new SoftAssert();
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	public String getToolTipMsgBS(String isOrRange, String metaDataField) {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterSearchString().Visible();
			}
		}), Input.wait30);
		getBasicSearch_MetadataBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectMetaData().Visible();
			}
		}), Input.wait30);

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);

		// Create action class object
		Actions builder = new Actions(driver.getWebDriver());
		if (isOrRange.equalsIgnoreCase("IS")) {
			getSingleToolTipIcon().waitAndClick(10);
			// Mouse hover to that text message
			builder.moveToElement(getSingleToolTipText().getWebElement()).perform();
			return getSingleToolTipText().getText();
		} else if (isOrRange.equalsIgnoreCase("RANGE")) {
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getRangeToolTipIcon1().waitAndClick(10);
			builder.moveToElement(getRangeToolTip1Text().getWebElement()).perform();
			String first = getRangeToolTip1Text().getText();
			builder.moveToElement(getRangeToolTip2Text().getWebElement()).perform();
			String second = getRangeToolTip1Text().getText();
			Assert.assertEquals(first, second);
			return first;
		}

		return null;
	}

	public String getToolTipMsgAS(String isOrRange, String metaDataField) {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtn().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtn().Click();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvanceSearch_MetadataBtn().Visible();
			}
		}), Input.wait30);
		getAdvanceSearch_MetadataBtn().Click();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);

		// Create action class object
		Actions builder = new Actions(driver.getWebDriver());
		if (isOrRange.equalsIgnoreCase("IS")) {
			getSingleToolTipIcon().waitAndClick(10);
			// Mouse hover to that text message
			builder.moveToElement(getSingleToolTipText().getWebElement()).perform();
			return getSingleToolTipText().getText();
		} else if (isOrRange.equalsIgnoreCase("RANGE")) {
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getRangeToolTipIcon1().waitAndClick(10);
			builder.moveToElement(getRangeToolTip1Text().getWebElement()).perform();
			String first = getRangeToolTip1Text().getText();
			builder.moveToElement(getRangeToolTip2Text().getWebElement()).perform();
			String second = getRangeToolTip1Text().getText();
			Assert.assertEquals(first, second);
			return first;
		}

		return null;
	}

	/**
	 * @author Raghuram.A modifiedOn : 2/5/22 by modified by : Raghuram
	 * @param searchName
	 */
	public void saveSearch(String searchName) {
		// Save Search
		saveSearchAction();

		if (getYesQueryAlert().isElementAvailable(6)) {
			getYesQueryAlert().waitAndClick(5);
		}

		if (getSaveAsNewSearchRadioButton().isElementAvailable(7)) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveAsNewSearchRadioButton().Visible() && getSaveAsNewSearchRadioButton().Enabled();
				}
			}), Input.wait30);
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} else {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {
				return getSavedSearch_MySearchesTab().Visible() && getSavedSearch_MySearchesTab().Enabled();
			}
		}), Input.wait30);
		getSavedSearch_MySearchesTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveSearch_Name().Visible() && getSaveSearch_Name().Enabled();
			}
		}), Input.wait30);
		getSaveSearch_Name().SendKeys(searchName);
//		driver.Manage().window().fullscreen();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveSearch_SaveButton().Visible() && getSaveSearch_SaveButton().Enabled();
			}
		}), Input.wait30);
		getSaveSearch_SaveButton().Click();
//		driver.Manage().window().maximize();
		driver.waitForPageToBeReady();

		base.VerifySuccessMessage("Saved search saved successfully");

		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	public void wrongQueryAlertBasicSaerch(String SearchString, int MessageNumber, String fielded, String fieldName) {

		driver.getWebDriver().get(Input.url + "Search/Searches");
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterSearchString().Visible();
			}
		}), Input.wait30);

		//
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicSearch_MetadataBtn().Visible();
			}
		}), Input.wait30);
		if (fielded.equalsIgnoreCase("fielded")) {
			//
			getBasicSearch_MetadataBtn().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectMetaData().Visible();
				}
			}), Input.wait30);

			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			getSelectMetaData().selectFromDropdown().selectByVisibleText(fieldName);
			getMetaDataSearchText1().SendKeys(SearchString);
			getMetaDataInserQuery().Click();

			//
		} else
			getEnterSearchString().SendKeys(SearchString);

		// Click on Search button
		getSearchButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQueryAlertGetText().Visible();
			}
		}), 10);
		// System.out.println(getQueryAlertGetText().getText());
		if (MessageNumber == 1)
			Assert.assertEquals(
					"Wildcard characters in proximity searches and in phrase searches are not supported. Please contact your project manager to help structure your search needs.",
					getQueryAlertGetText().getText());
		if (MessageNumber == 2) {
			String msg = "Your query contains at least one lowercase \"and\", \"or\" or \"not\" word. In Sightline, lowercase \"and\", \"or\" or \"not\" are treated as terms, whereas capitalized \"AND\", \"OR\" or \"NOT\" are treated as operators.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 3) {
			String msg = "Your query contains an argument that may be intended to look for dates within any body content or metadata attribute. Please be advised that the search engine interprets dash - or slash / characters in non-fielded arguments as implied OR statement. For example, 1980/01/02 is treated by the search engine as 1980 OR 01 OR 02. The same is true of 1980-01-02. You can add quotation marks around a query to treat the dash or slash argument as a string. For example, \"1980/01/02\" is treated as is. The same is true of \"1980-01-02\".Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 4)
			Assert.assertEquals(
					"YOUR QUERY CONTAINS A HYPHEN CHARACTER \"-\" THAT IS INTERPRETED IN DIFFERENT WAYS BY THE SEARCH ENGINE DEPENDING ON ITS USE IN THE QUERY\n\nIf the hyphen character is part of a string that is enveloped within quotations, such as Ã¢â‚¬Å“bi-weekly reportÃ¢â‚¬Â, the hyphen character is treated literally, returning documents that hit on that exact phrase.\n If the hyphen character is not part of a string enveloped in quotations, and is preceded or succeeded by a space, then the hyphen character will be interpreted as a space, which is an implied OR operator (ex. bi - weekly will be interpreted as bi OR weekly).\n\nIf the hyphen character is not part of a string enveloped in quotations, and is the first character of an argument (ie. not separated by a space from an argument), then the search engine will interpret the hyphen as a NOT operator (ex. bi -weekly is interpreted as bi NOT weekly)\n\nBased on this information, is your query what you intended? If this is what you intended, please click YES to execute your search. If this is not what you intended, please click NO and correct your query.",
					getQueryAlertGetText().getText());
		if (MessageNumber == 5) {
			String msg = "Invalid parenthesis balance, please ensure all the braces have proper parenthesis balance.";

			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 6) {
			String msg = "One or more of your Proximity Queries has only a single Term. This could be as a result of extra Double Quotes around terms or the use of Parenthesis which group multiple values as a single term.";

			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 7) {
			String msg = "Your query may contain an invalid Proximity Query. Any Double Quoted phrases as part of a Proximity Query must also be wrapped in Parentheses, e.g. \"Term1 (\"Specific Phrase\")\"~4.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 8) {
			String msg = "Your query has multiple potential syntax issues.1. Your query contains a ~ (tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)2. Your query contains the ~ (tilde) character which does not immediately follow a double-quoted set of terms or is not immediately followed by a numeric value . If you are trying to run a proximity search, please use appropriate proximity query syntax e.g. \"Term1 Term2\"~4. Note there is no space before or after the tilde.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}

		if (MessageNumber == 9) {
			String msg = "There is a trailing operator not followed by an argument, please verify the search syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}

		if (MessageNumber == 10) {
			String msg = "Your query contains two or more arguments that do not have an operator between them. In Sightline, each term without an operator between them will be treated as A OR B, not \"A B\" as an exact phrase. If you want to perform a phrase search, wrap the terms in quotations (ex. \"A B\" returns all documents with the phrase A B).Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 11) {
			String msg = "Your query contains a ~(tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)Does your query reflect your intent?Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			System.out.println(getQueryAlertGetText().getText());
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}

		if (MessageNumber == 12) {
			if (getWarningAudioBlackListChar_Header().isDisplayed()) {
				softAssert.assertEquals(getWarningAudioBlackListChar_Header().getText(), "Possible Wrong Query Alert");
				base.stepInfo("Displayed Header is : " + getWarningAudioBlackListChar_Header().getText());
			}
			String msg = " Parentheses are missing in your search query. Please correct the query to include proper opening and closing parentheses.";
			String actualMsg = getQueryAlertGetTextSingleLine().getText();
			base.stepInfo(actualMsg);
			System.out.println(actualMsg);
			softAssert.assertEquals(msg.replaceAll(" ", ""), actualMsg.replaceAll(" ", "").replaceAll("\n", ""));

		}
		if (MessageNumber == 13) {
			if (getWarningAudioBlackListChar_Header().isDisplayed()) {
				softAssert.assertEquals(getWarningAudioBlackListChar_Header().getText(), "Possible Wrong Query Alert");
				base.stepInfo("Displayed Header is : " + getWarningAudioBlackListChar_Header().getText());
				System.out.println("Displayed Header is : " + getWarningAudioBlackListChar_Header().getText());
			}

			String msg = "Your query has multiple potential syntax issues.\r\n" + "\r\n"
					+ "1. Your query contains the ~ (tilde) character which does not immediately follow a double-quoted set of terms or is not immediately followed by a numeric value .\r\n"
					+ "If you are trying to run a proximity search, please use appropriate proximity query syntax e.g. \"Term1 Term2\"~4.\r\n"
					+ "Note there is no space before or after the tilde.\r\n" + "\r\n"
					+ "2. Your query contains two or more arguments that do not have an operator between them. In Sightline, each term without an operator between them will be treated as A OR B, not \"A B\" as an exact phrase. If you want to perform a phrase search, wrap the terms in quotations (ex. \"A B\" returns all documents with the phrase A B).\r\n"
					+ "\r\n" + "Does your query reflect your intent?\r\n"
					+ "Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";

			String actualMsg = getWarningMsg().getText();
			System.out.println(actualMsg);
			base.stepInfo(actualMsg);

			softAssert.assertEquals(msg.replaceAll(" ", ""), actualMsg.replaceAll(" ", "").replaceAll("\n", ""));

		}
		// click on ok
		/*
		 * if(MessageNumber == 1) getTallyContinue().Click(); else
		 */

	}

//Modified on 10/2/22 by jayanthi added one new message with number 13.
	public void wrongQueryAlertAdvanceSaerch(String SearchString, int MessageNumber, String fielded, String fieldName) {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtn().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtn().Click();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvanceSearch_MetadataBtn().Visible();
			}
		}), Input.wait30);
		if (fielded.equalsIgnoreCase("fielded")) {
			//
			getAdvanceSearch_MetadataBtn().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectMetaData().Visible();
				}
			}), Input.wait30);

			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			getSelectMetaData().selectFromDropdown().selectByVisibleText(fieldName);
			getMetaDataSearchText1().SendKeys(SearchString);
			getMetaDataInserQuery().Click();

			//
		} else
			getAdvancedContentSearchInput().SendKeys(SearchString);

		// Click on Search button
		getQuerySearchButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQueryAlertGetText().Visible();
			}
		}), 10);
		// System.out.println(getQueryAlertGetText().getText());
		if (MessageNumber == 1)
			Assert.assertEquals(
					"Wildcard characters in proximity searches and in phrase searches are not supported. Please contact your project manager to help structure your search needs.",
					getQueryAlertGetText().getText());
		if (MessageNumber == 2) {
			String msg = "Your query contains at least one lowercase \"and\", \"or\" or \"not\" word. In Sightline, lowercase \"and\", \"or\" or \"not\" are treated as terms, whereas capitalized \"AND\", \"OR\" or \"NOT\" are treated as operators.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 3) {
			String msg = "Your query contains an argument that may be intended to look for dates within any body content or metadata attribute. Please be advised that the search engine interprets dash - or slash / characters in non-fielded arguments as implied OR statement. For example, 1980/01/02 is treated by the search engine as 1980 OR 01 OR 02. The same is true of 1980-01-02. You can add quotation marks around a query to treat the dash or slash argument as a string. For example, \"1980/01/02\" is treated as is. The same is true of \"1980-01-02\".Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";

			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
			base.passedStep(msg);

		}
		if (MessageNumber == 4)
			Assert.assertEquals(
					"YOUR QUERY CONTAINS A HYPHEN CHARACTER \"-\" THAT IS INTERPRETED IN DIFFERENT WAYS BY THE SEARCH ENGINE DEPENDING ON ITS USE IN THE QUERY\n\nIf the hyphen character is part of a string that is enveloped within quotations, such as Ã¢â‚¬Å“bi-weekly reportÃ¢â‚¬Â, the hyphen character is treated literally, returning documents that hit on that exact phrase.\n\nIf the hyphen character is not part of a string enveloped in quotations, and is preceded or succeeded by a space, then the hyphen character will be interpreted as a space, which is an implied OR operator (ex. bi - weekly will be interpreted as bi OR weekly).\n\nIf the hyphen character is not part of a string enveloped in quotations, and is the first character of an argument (ie. not separated by a space from an argument), then the search engine will interpret the hyphen as a NOT operator (ex. bi -weekly is interpreted as bi NOT weekly)\n\nBased on this information, is your query what you intended? If this is what you intended, please click YES to execute your search. If this is not what you intended, please click NO and correct your query.",
					getQueryAlertGetText().getText());

		if (MessageNumber == 5) {
			String msg = "Invalid parenthesis balance, please ensure all the braces have proper parenthesis balance.";

			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 6) {
			String msg = "One or more of your Proximity Queries has only a single Term. This could be as a result of extra Double Quotes around terms or the use of Parenthesis which group multiple values as a single term.";

			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 7) {
			String msg = "Your query may contain an invalid Proximity Query. Any Double Quoted phrases as part of a Proximity Query must also be wrapped in Parentheses, e.g. \"Term1 (\"Specific Phrase\")\"~4.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 8) {
			String msg = "Your query has multiple potential syntax issues.1. Your query contains a ~ (tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)2. Your query contains the ~ (tilde) character which does not immediately follow a double-quoted set of terms or is not immediately followed by a numeric value . If you are trying to run a proximity search, please use appropriate proximity query syntax e.g. \"Term1 Term2\"~4. Note there is no space before or after the tilde.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}

		if (MessageNumber == 9) {
			String msg = "There is a trailing operator not followed by an argument, please verify the search syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}

		if (MessageNumber == 10) {
			String msg = "Your query contains two or more arguments that do not have an operator between them. In Sightline, each term without an operator between them will be treated as A OR B, not \"A B\" as an exact phrase. If you want to perform a phrase search, wrap the terms in quotations (ex. \"A B\" returns all documents with the phrase A B).Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
			base.passedStep(msg);
		}

		if (MessageNumber == 11) {
			String msg = "Your query has multiple potential syntax issues.1. Your query contains a ~ (tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)2. Your query contains the ~ (tilde) character which does not immediately follow a double-quoted set of terms or is not immediately followed by a numeric value . If you are trying to run a proximity search, please use appropriate proximity query syntax e.g. \"Term1 Term2\"~4. Note there is no space before or after the tilde.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
			base.passedStep(msg);
		}
		if (MessageNumber == 12) {
			String msg = "Search queries with wildcard characters within phrases are not supported. Alternatively, you could get the same results by performing a proximity search with a distance of zero. For example, a search \"trade contr*\" can be equivalently reconstructed as \"trade contr*\"~0! to get the same results. Please reach out to Support or your administrator for any additional help.";
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 13) {
			base.stepInfo(SearchString + " is entered as proximity phrase in advanced search content search text box.");
			String msg = "Your query contains a ~(tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)Does your query reflect your intent?Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			System.out.println(getQueryAlertGetText().getText());
			if (msg.replaceAll(" ", "")
					.equals(getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""))) {
				base.passedStep("Proximity query alert message displayed as expected.");
				base.passedStep(msg);
				getTallyContinue().waitAndClick(10);
				// verify counts for all the tiles
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
					}
				}), Input.wait90);

			} else {
				base.failedMessage(msg);
				base.failedStep("Proximity query alert message not displayed as expected.");
			}
		}
		// click on ok
		/*
		 * if(MessageNumber == 1) getTallyContinue().Click(); else
		 */
		driver.getWebDriver().navigate().refresh();

	}

	// Function to perform content search for a given search string
	// Function edited by krishna as part of stabilisation
	public int basicContentSearch(String SearchString) {
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterSearchString().Visible();
			}
		}), Input.wait60);
		getEnterSearchString().SendKeys(SearchString);
		// Click on Search button
		getSearchButton().waitAndClick(10);
		// handle pop confirmation for regex and proximity queries
		if (getYesQueryAlert().isElementAvailable(8))
			try {
				getYesQueryAlert().waitAndClick(8);
			} catch (Exception e) {
				// TODO: handle exception
			}
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		getPureHitsCount().waitAndClick(20);
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		// System.out.println("Search is done for "+SearchString+" and PureHit is :
		// "+pureHit);
		UtilityLog.info("Search is done for " + SearchString + " and PureHit is : " + pureHit);
		Reporter.log("Search is done for " + SearchString + " and PureHit is : " + pureHit, true);
		return pureHit;
	}

	// Function to perform content search for a given search string
	public int advancedContentSearch(String SearchString) {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtn().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtn().Click();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentSearchInput().Visible();
			}
		}), Input.wait30);
		getAdvancedContentSearchInput().SendKeys(SearchString);

		// Click on Search button
		getQuerySearchButton().Click();

		// look for warnings, in case of proximity search
		try {
			if (getTallyContinue().isElementAvailable(10)) {
				getTallyContinue().waitAndClick(10);
			}
			Thread.sleep(4000);
		} catch (Exception e) {

		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		Reporter.log("Serach is done for '" + SearchString + "' and PureHit is : " + pureHit, true);
		UtilityLog.info("Serach is done for " + SearchString + " and PureHit is : " + pureHit);

		return pureHit;
	}

	/**
	 * modified on : 11/3/21 modified : wait time for tally
	 * 
	 * @param metaDataField
	 * @param option
	 * @param val1
	 * @param val2
	 * @return
	 */
	public int basicMetaDataSearch(String metaDataField, String option, String val1, String val2) {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		getBasicSearch_MetadataBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectMetaData().Visible();
			}
		}), Input.wait30);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		if (option == null) {

			getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		} else if (option.equalsIgnoreCase("IS")) {
			getMetaOption().selectFromDropdown().selectByVisibleText("IS (:)");
			getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		} else if (option.equalsIgnoreCase("RANGE")) {
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
			getMetaDataSearchText2().SendKeys(val2 + Keys.TAB);

		}
		getMetaDataInserQuery().Click();
		// Click on Search button
		getSearchButton().Click();

		// two handle twosearch strings
		if (metaDataField.equalsIgnoreCase("CustodianName") || metaDataField.equalsIgnoreCase("EmailAuthorName")
				|| metaDataField.equalsIgnoreCase("EmailRecipientNames")
				|| metaDataField.equalsIgnoreCase(Input.docFileType)) {

			try {
				driver.waitForPageToBeReady();
				if (getTallyContinue().isElementAvailable(10)) {
					getTallyContinue().waitAndClick(10);
				}
			} catch (Exception e) {
			}
		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		base.stepInfo("Search is done for " + metaDataField + " with value " + val1 + " - " + val2 + " purehit is : "
				+ pureHit);
		UtilityLog.info("Search is done for " + metaDataField + " with value " + val1 + " - " + val2 + " purehit is : "
				+ pureHit);

		return pureHit;

	}

	public int advancedMetaDataSearch(String metaDataField, String option, String val1, String val2) {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtn().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtn().Click();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvanceSearch_MetadataBtn().Visible();
			}
		}), Input.wait30);
		getAdvanceSearch_MetadataBtn().Click();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		if (option == null) {

			getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		} else if (option.equalsIgnoreCase("IS")) {
			getMetaOption().selectFromDropdown().selectByVisibleText("IS (:)");
			getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		} else if (option.equalsIgnoreCase("RANGE")) {
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
			getMetaDataSearchText2().SendKeys(val2 + Keys.TAB);

		}
		getMetaDataInserQuery().Click();
		// Click on Search button
		getQuerySearchButton().Click();
		// two handle twosearch strings
		if (metaDataField.equalsIgnoreCase("CustodianName") || metaDataField.equalsIgnoreCase("EmailAuthorName")
				|| metaDataField.equalsIgnoreCase("EmailRecipientNames")) {

			if (getTallyContinue().isElementAvailable(2)) {
				getTallyContinue().waitAndClick(5);
			}
		}
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Search is done for " + metaDataField + " with value " + val1 + " - " + val2
				+ " purehit is : " + pureHit);
		UtilityLog.info("Search is done for " + metaDataField + " with value " + val1 + " - " + val2 + " purehit is : "
				+ pureHit);

		return pureHit;

	}

	public int audioSearch(String SearchString, String language) {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");

		getAdvancedSearchLink().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_Audio().Visible();
			}
		}), Input.wait30);
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioLanguage().Visible();
			}
		}), Input.wait30);
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioText().Visible();
			}
		}), Input.wait30);
		getAs_AudioText().SendKeys(SearchString);

		// Click on Search button
		getQuerySearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		getPureHitsCount().isElementAvailable(10);
		base.waitTime(5);
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Audio Search is done for " + SearchString + " and PureHit is : " + pureHit);
		UtilityLog.info("Audio Search is done for " + SearchString + " and PureHit is : " + pureHit);

		return pureHit;

	}

	// for two search strings with operator
	public int audioSearch(String SearchString1, String SearchString2, String language, String threshold)
			throws InterruptedException {

		getAdvancedSearchLink().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_Audio().Visible();
			}
		}), Input.wait30);
		getAs_Audio().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioLanguage().Visible();
			}
		}), Input.wait30);
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioText().Visible();
			}
		}), Input.wait30);
		getAs_AudioText().SendKeys(SearchString1 + Keys.ENTER + SearchString2 + Keys.ENTER);

		// Select term operator
		getAudioTermOperator().selectFromDropdown().selectByVisibleText("ALL");
		// Select location
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAudioLocation().Visible();
			}
		}), Input.wait30);
		Thread.sleep(6000);
		getAudioLocation().selectFromDropdown().selectByValue("range");
		// enter time
		getAudioTimeDiff().SendKeys("30");

		Thread.sleep(5000);
		// select threshold
		if (threshold.equalsIgnoreCase("right")) {
			getConceptualRight().waitAndClick(15);
		} else if (threshold.equalsIgnoreCase("mid")) {
			// Since by default it will be in mid,no need to click mid
			// getConceptualMid().waitAndClick(15);
		} else if (threshold.equalsIgnoreCase("left")) {
			getConceptualLeft().waitAndClick(15);
		}
		// Click on Search button
		getQuerySearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println(
				"Audio Serach is done for " + SearchString1 + " and " + SearchString2 + " PureHit is : " + pureHit);

		return pureHit;

	}

	public int conceptualSearch(String SearchString, String Percetage) {
		getAdvancedSearchLink().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_Conceptual().Visible();
			}
		}), Input.wait30);
		getAs_Conceptual().Click();

		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_ConceptualTextArea().Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		getAs_ConceptualTextArea().SendKeys(SearchString);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		/*
		 * if(Percetage.equalsIgnoreCase("right")){ getConceptualRight().Click(); } else
		 * if(Percetage.equalsIgnoreCase("left")){ getConceptualLeft().Click(); } else
		 * if(Percetage.equalsIgnoreCase("mid")){ getConceptualMid().Click(); }
		 */
		// Click on Search button
		getQuerySearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Conceptual search is done for " + SearchString + " and PureHit is : " + pureHit);
		UtilityLog.info("Conceptual search is done for " + SearchString + " and PureHit is : " + pureHit);

		return pureHit;

	}

//modified by jayanthi 7/9/21
	public void searchSavedSearch(final String SaveName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchBtn().Visible();
			}
		}), Input.wait60);
		getSavedSearchBtn().Click();
		driver.scrollingToBottomofAPage();
		for (WebElement iterable_element : getTree().FindWebElements()) {
			// System.out.println(iterable_element.getText());
			base.waitTime(2);
			if (iterable_element.getText().contains(SaveName)) {
				base.waitTime(2);
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				// System.out.println(iterable_element.getText());
				iterable_element.click();
			}
		}
		// added on 16-8-21
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(15);
		// Click on Search button
		driver.scrollPageToTop();

	}

	// Function to fetch pure hit count
	public String verifyPureHitsCount() {

		return getPureHitsCount().getText();

	}

	// Function to fetch Threaded docs hit count
	public String verifyThreadedCount() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getThreadedCount().Visible();
			}
		}), Input.wait60);

		return getThreadedCount().getText();

	}

	// Function to fetch neardupes hit count
	public String verifyNearDupeCount() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNearDupeCount().Visible();
			}
		}), Input.wait60);
		return getNearDupeCount().getText();

	}

	// Function to fetch Familymembers hit count
	public String verifyFamilyount() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFamilyCount().Visible();
			}
		}), Input.wait60);
		return getFamilyCount().getText();
	}

	// Function to perform new bulk folder with given tag name
	public void bulkFolder(String folderName) throws InterruptedException {

		// driver.getWebDriver().get(Input.url+"Search/Searches");
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait60);
		getBulkActionButton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderAction().Visible();
			}
		}), Input.wait60);

		getBulkFolderAction().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkNewTab().Visible();
			}
		}), Input.wait60);

		getBulkNewTab().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterFolderName().Visible();
			}
		}), Input.wait60);
		getEnterFolderName().SendKeys(folderName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderAllRoot().Visible();
			}
		}), Input.wait60);
		getFolderAllRoot().Click();
		driver.Manage().window().fullscreen();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();
		driver.Manage().window().maximize();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();

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

	/**
	 * Modified on 03/14/2022 Function to perform bulk folder with existing folder
	 */
	public void bulkFolderExisting(final String folderName) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Search/Searches");
		if (getRemovePureHit().isElementAvailable(3)) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		} else if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(10);
		}

		driver.scrollPageToTop();
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderAction().Visible();
			}
		}), Input.wait60);

		getBulkFolderAction().waitAndClick(10);
		driver.Manage().window().fullscreen();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolderExisting(folderName).Visible();
			}
		}), Input.wait60);

		getSelectFolderExisting(folderName).waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().waitAndClick(10);
		driver.Manage().window().maximize();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().waitAndClick(10);

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

	/**
	 * * Modified on 05/12/22
	 * 
	 * @modifiedOn : 3/8/2022 (added wait time)
	 * @modifiedOn : 1/5/2022 (getRemovePureHit().isElementAvailable(3))
	 * @modifiedOn : 1/7/2022 getPureHitAddButton().Click(); to
	 *             getPureHitAddButton().waitAndClick(10);
	 * @param tagName
	 * @throws InterruptedException
	 */
	// Function to perform bulk tag with existing tag
	public void bulkTagExisting(final String tagName) throws InterruptedException {

		if (getRemovePureHit().isElementAvailable(3)) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		} else if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(10);
		}

		driver.waitForPageToBeReady();
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);

		base.waitForElement(getBulkTagAction());
		getBulkTagAction().waitAndClick(10);

		base.waitForElement(getSelectTagExisting(tagName));
		driver.waitForPageToBeReady();
		getSelectTagExisting(tagName).waitAndClick(5);

		driver.Manage().window().fullscreen();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().waitAndClick(10);

		driver.Manage().window().maximize();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkTagConfirmationButton().Visible();
			}
		}), Input.wait30);
		getBulkTagConfirmationButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Tag is done, Tag is : " + tagName);
		UtilityLog.info("Bulk Tag is done, Tag is : " + tagName);

		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();
	}

	// Function to perform bulk tag with given tag name
	public void bulkTag(String TagName) throws InterruptedException {
		// driver.getWebDriver().get(Input.url+"Search/Searches");
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(10);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(30);

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getBulkTagAction().Visible() ;}}), Input.wait60);
		 */
		Thread.sleep(2000); // synch with app!
		getBulkTagAction().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkNewTab().Visible();
			}
		}), Input.wait60);

		getBulkNewTab().waitAndClick(60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterTagName().Visible();
			}
		}), Input.wait60);
		getEnterTagName().SendKeys(TagName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsAllRoot().Visible();
			}
		}), Input.wait60);
		getTagsAllRoot().Click();
		driver.Manage().window().fullscreen();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();
		driver.Manage().window().maximize();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkTagConfirmationButton().Visible();
			}
		}), Input.wait60);
		getBulkTagConfirmationButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		// System.out.println("Bulk Tag is done, Tag is : "+TagName);
		UtilityLog.info("Bulk Tag is done, Tag is : " + TagName);
		Reporter.log("Bulk Tag is done, Tag is : " + TagName, true);

		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();
	}

	/*
	 * public void exportData() { try{ getPureHitAddButton().Click(); }catch
	 * (Exception e) {
	 * System.out.println("Pure hit block already moved to action panel"); }
	 * 
	 * getBulkActionButton().Click(); getas_Action_Exportdata().Click();
	 * 
	 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
	 * getMetaDataFields().size()>1 ;}}), Input.wait60);
	 * 
	 * //getBulkTagAction().Click(); System.out.println(getMetaDataFields().size());
	 * 
	 * 
	 * }
	 */

	public void ViewInDocList() throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Search/Searches");
		if (getRemovePureHit().isElementAvailable(2)) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		} else {

			getPureHitAddButton().waitAndClick(5);
		}

		getBulkActionButton().waitAndClick(5);

		base.waitTime(2);
		if (getView().isDisplayed()) {
			driver.waitForPageToBeReady();
			Actions act = new Actions(driver.getWebDriver());
			act.moveToElement(getView().getWebElement()).build().perform();
		} else {
			System.out.println("View is not found");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocListAction().Visible();
			}
		}), Input.wait60);

		getDocListAction().waitAndClick(5);
		if (getTallyContinue().isElementAvailable(5)) {
			try {
				getTallyContinue().waitAndClick(5);
			} catch (Exception e) {
			}
		}
		base.stepInfo("Navigated to doclist, to view docslist");
		UtilityLog.info("Navigated to doclist, to view docslist");

	}

	/**
	 * Modified on 04/08/2022 stabilized navigating to docview as per new deployment
	 * in PT.
	 * 
	 * @Stabilization Done
	 * @throws InterruptedException
	 */

	public void ViewInDocView() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Search/Searches");

		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000); // App synch
		getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch

		if (getViewBtn().isElementAvailable(2)) {
			driver.waitForPageToBeReady();

			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			Actions actions = new Actions(driver.getWebDriver());
			wait.until(ExpectedConditions.elementToBeClickable(getViewBtn().getWebElement()));
			actions.moveToElement(getViewBtn().getWebElement()).build().perform();

			base.waitForElement(getDocViewFromDropDown());
			getDocViewFromDropDown().waitAndClick(10);
		} else {
			getDocViewAction().waitAndClick(10);
			base.waitTime(3); // added for stabilization
		}

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");

	}

	/**
	 * modified the same available method for german locale
	 * 
	 * @throws InterruptedException
	 */
	public void ViewInDocViewGerman() throws InterruptedException {

		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000); // App synch
		getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch

		if (getView().isDisplayed()) {
			driver.waitForPageToBeReady();
			Actions act = new Actions(driver.getWebDriver());
			act.moveToElement(getView().getWebElement()).build().perform();
		} else {
			System.out.println("View is not found");
		}
		base.waitForElement(getDocViewAction());
		getDocViewAction().waitAndClick(5);
		base.waitTime(3); // added for stabilization

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");

	}

	public void tallyResults() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocListAction().Visible();
			}
		}), Input.wait60);

		Thread.sleep(2000);
		getTallyResults().waitAndClick(5);
		base.waitTime(3); // added for stabilization

		System.out.println("Navigated to Tally  to view docs");
		UtilityLog.info("Navigated to Tally  to view docs");
	}

	public void bulkAssign() {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		if (getRemovePureHit().isElementAvailable(3)) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		} else if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(10);
		}

		getBulkActionButton().waitAndClick(3);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait60);
		getBulkAssignAction().waitAndClick(10);

		base.stepInfo("clicked bulk assign tab");
		UtilityLog.info("clicked  bulk assign tab");

	}

	// Bulk release to default security group
	public void bulkRelease(final String SecGroup) {

		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(10);
// added on 16-08-21		 
		driver.waitForPageToBeReady();

		try {
			getBulkReleaseAction().waitAndClick(10);
		} catch (Exception e) {

			getBulkReleaseActionDL().waitAndClick(10);
		}
// added on 16-08-21
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible();
			}
		}), Input.wait60);
		getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelease_ButtonRelease().Visible();
			}
		}), Input.wait60);
		getBulkRelease_ButtonRelease().waitAndClick(20);

		if (getTallyContinue().isElementAvailable(5)) {
			getTallyContinue().waitAndClick(10);
		}

		if (getFinalizeButton().isDisplayed()) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFinalizeButton().Visible();
				}
			}), Input.wait60);
			getFinalizeButton().waitAndClick(20);
		}

		base.VerifySuccessMessageB("Records saved successfully");

		System.out.println("performing bulk release");
		UtilityLog.info("performing bulk release");

	}

	public boolean bulkReleaseIngestions(final String SecGroup) {
		boolean release = false;
		try {
			if (getPureHitAddButton().isElementAvailable(2)) {
				getPureHitAddButton().waitAndClick(5);
			} else {
				System.out.println("Pure hit block already moved to action panel");
				UtilityLog.info("Pure hit block already moved to action panel");
			}

			getBulkActionButton().waitAndClick(10);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (getBulkReleaseAction().isElementAvailable(10)) {
				getBulkReleaseAction().waitAndClick(10);
			} else {
				getBulkReleaseActionDL().waitAndClick(10);
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible();
				}
			}), Input.wait60);
			getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getBulkRelease_ButtonRelease().Visible();
				}
			}), Input.wait60);
			getBulkRelease_ButtonRelease().waitAndClick(20);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFinalizeButton().Visible();
				}
			}), Input.wait60);
			getFinalizeButton().waitAndClick(20);

			if (!base.VerifySuccessMessageB("Records saved successfully")) {
				System.out.println("Execution aborted!");
				UtilityLog.info("Execution aborted!");
				System.out.println("Bulk relese did not go well! take a look and continue!!");
				UtilityLog.info("Bulk relese did not go well! take a look and continue!!");
				System.exit(1);
			}

			System.out.println("performed bulk release");
			UtilityLog.info("performed bulk release");
			release = true;

		} finally {
			System.out.println("in");
			return release;
		}
	}

	// Function to perform bulk untag
	public void bulkUnTag(final String TagName) throws InterruptedException {

		Thread.sleep(1000);
		getBulkActionButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkTagAction().Visible();
			}
		}), Input.wait60);
		getBulkTagAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkUntagbutton().Visible();
			}
		}), Input.wait30);
		getBulkUntagbutton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectTagExisting(TagName).Visible();
			}
		}), Input.wait60);
		getSelectTagExisting(TagName).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		bc.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Untag is done, Tag is : " + TagName);
		UtilityLog.info("Bulk Untag is done, Tag is : " + TagName);

		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();
	}

	// Function to perform new bulk folder with given folder name
	public void bulkUnFolder(final String folderName) throws InterruptedException {
		driver.scrollPageToTop();
		Thread.sleep(1000);
		getBulkActionButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderAction().Visible();
			}
		}), Input.wait60);
		getBulkFolderAction().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkUnFolderbutton().Visible();
			}
		}), Input.wait30);
		getBulkUnFolderbutton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolderExisting(folderName).Visible();
			}
		}), Input.wait60);
		getSelectFolderExisting(folderName).waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		bc.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Unfolder is done, folder is : " + folderName);
		UtilityLog.info("Bulk Unfolder is done, folder is : " + folderName);

		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();
	}

	public void selectTagInASwp(String tagName) {
		base.waitForElement(getWP_TagBtn());
		getWP_TagBtn().Click();
		driver.waitForPageToBeReady();
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(tagName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				iterable_element.click();
				break;
			}
		}
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		driver.scrollPageToTop();

	}

	public void selectFolderInASwp(String folderName) {
		base.waitForElement(getWP_FolderBtn());
		getWP_FolderBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(folderName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				iterable_element.click();
				break;
			}
		}
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(10);
		driver.scrollPageToTop();

	}

	// modified by jayanthi 12/4/22 reduced un-necessary waits
	public void selectSecurityGinWPS(String sgname) {
		base.waitForElement(getSecurityGrpBtn());
		getSecurityGrpBtn().Click();
		driver.waitForPageToBeReady();
		System.out.println(getSecurityNamesTree().FindWebElements().size());
		UtilityLog.info(getSecurityNamesTree().FindWebElements().size());
		for (WebElement iterable_element : getSecurityNamesTree().FindWebElements()) {
			// System.out.println(iterable_element.getText());
			if (iterable_element.getText().contains(sgname)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				iterable_element.click();
				break;
			}
		}
		driver.scrollingToBottomofAPage();
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		driver.scrollPageToTop();

	}

	// modified by Jayanthi 16/9/21
	public void selectOperator(String operator) {
		driver.scrollPageToTop();
		base.waitForElement(getOperatorDD());
		getOperatorDD().waitAndClick(10);
		if (operator.equalsIgnoreCase("and")) {
			base.waitForElement(getOperatorAND());
			getOperatorAND().waitAndClick(15);
		}
		if (operator.equalsIgnoreCase("OR")) {
			base.waitForElement(getOperatorOR());
			getOperatorOR().waitAndClick(15);
		}
		if (operator.equalsIgnoreCase("NOT")) {
			base.waitForElement(getOperatorNOT());
			getOperatorNOT().waitAndClick(15);

		}

	}

	// Function to perform redaction name search
	// modified by jayanthi 12/4/22
	public void selectRedactioninWPS(final String redactName) throws InterruptedException {

		base.waitForElement(getRedactionBtn());
		getRedactionBtn().Click();

		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			// System.out.println(iterable_element.getText());
			if (iterable_element.getText().contains(redactName)) {

				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				System.out.println(iterable_element.getText());
				iterable_element.click();
				break;
			}
		}
		//
		// driver.scrollingToBottomofAPage();
		// added on 24/8/21
		driver.scrollingToElementofAPage(getMetaDataInserQuery());
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();

		driver.scrollPageToTop();

	}

	// Function to perform assignment name search in work product
	public void selectAssignmentInWPS(final String assignMentName) throws InterruptedException {
		base.waitForElement(getWP_assignmentsBtn());
		getWP_assignmentsBtn().Click();
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			// System.out.println(iterable_element.getText());
			if (iterable_element.getText().contains(assignMentName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				System.out.println(iterable_element.getText());
				UtilityLog.info(iterable_element.getText());
				iterable_element.click();
				break;
			}
		}
		//
		// driver.scrollingToBottomofAPage();

		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(20);
		driver.scrollPageToTop();
		base.stepInfo("Assignment is selected in work product - advanced search");

	}

	public void switchToWorkproduct() {
		driver.getWebDriver().get(Input.url + "Search/Searches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkproductBtn().Visible();
			}
		}), Input.wait30);
		getWorkproductBtn().Click();
		base.stepInfo("Switched to Advanced search - Work product");

	}

	public int serarchWP() {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQuerySearchButton().Visible();
			}
		}), Input.wait30);
		getQuerySearchButton().waitAndClick(5);
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getPureHitsCount().waitAndClick(10);
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		base.stepInfo("Search is done and PureHit is : " + pureHit);
		UtilityLog.info("Search is done and PureHit is : " + pureHit);
		return pureHit;
	}

	// Function to perform bulk tag from any page
	public String BulkActions_Tag(String TagName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkNewTab().Visible();
			}
		}), Input.wait60);

		getBulkNewTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterTagName().Visible();
			}
		}), Input.wait60);
		getEnterTagName().SendKeys(TagName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsAllRoot().Visible();
			}
		}), Input.wait60);
		getTagsAllRoot().Click();
		driver.Manage().window().fullscreen();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();
		driver.Manage().window().maximize();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		String finalCnt = getFinalCount().getText();
		getFinalizeButton().Click();

		getTallyContinue().waitAndClick(10);

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Tag is done, Tag is : " + TagName);
		base.stepInfo("Bulk Tag is done for " + finalCnt + " docs with  Tag name  : " + TagName);
		return finalCnt;
	}

	public void saveSearchAdvanced(String searchName) {

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getSaveSearch_AdvButton().Visible() ;}}), Input.wait60);
		 */
		getSaveSearch_AdvButton().waitAndClick(30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait30);
		getSavedSearch_MySearchesTab().Click();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		System.out.println("Saved search with name - " + searchName);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	public void Removedocsfromresults() {

		try {
			getRemovedocsfromresult().waitAndClick(20);
			base.stepInfo("document removed from the cart");
		} catch (Exception e) {
			System.out.println("No docs present in cart");
			UtilityLog.info("No docs present in cart");
		}

	}

	public void quickbatch() {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait60);

		getQuickBatchAction().Click();

		System.out.println("performing quick batch");
		UtilityLog.info("performing quick batch");

	}

	// Function to perform content search with advanced search options
	public int advContentSearchwithoptions(String SearchString) {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtn().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtn().Click();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentSearchInput().Visible();
			}
		}), Input.wait30);
		getAdvancedContentSearchInput().SendKeys(SearchString);

		getadvoption_family().waitAndClick(10);

		getadvoption_near().waitAndClick(10);

		getadvoption_threaded().waitAndClick(10);

		// Click on Search button
		getQuerySearchButton().waitAndClick(10);

		// look for warnings, in case of proximity search
		if (getTallyContinue().isElementAvailable(5)) {
			getTallyContinue().waitAndClick(5);
		} else {
			System.out.println("NO pop up appears");
			UtilityLog.info("NO pop up appears");
		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCountwithOptions().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCountwithOptions().getText());
		System.out.println("Serach is done for " + SearchString + " and PureHit is : " + pureHit);
		UtilityLog.info("Serach is done for " + SearchString + " and PureHit is : " + pureHit);

		String backgroundColorthread = driver.FindElementByXPath(".//*[@id='002']").GetCssValue("background-color");
		System.out.println(backgroundColorthread);
		UtilityLog.info(backgroundColorthread);

		Assert.assertEquals(backgroundColorthread, "rgba(218, 218, 218, 1)");

		return pureHit;

	}

	public void selectAssignmentInASwp(String AssgnName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWP_assignmentsBtn().Visible();
			}
		}), Input.wait30);
		getWP_assignmentsBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTree().Visible();
			}
		}), Input.wait30);
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			// System.out.println(iterable_element.getText());
			if (iterable_element.getText().contains(AssgnName)) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				iterable_element.click();
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getadwp_assgn_distributedto().Visible();
			}
		}), Input.wait30);

		List<WebElement> alloptions = getadwp_assgn_distributedto().FindWebElements();

		System.out.println(alloptions.size());
		UtilityLog.info(alloptions.size());

		for (WebElement options : getadwp_assgn_distributedto().FindWebElements()) {
			System.out.println(options.getText());
			UtilityLog.info(options.getText());
			Assert.assertTrue(options.getText().contains(Input.rmu1userName));
			Assert.assertTrue(options.getText().contains(Input.rmu2userName));
			Assert.assertTrue(options.getText().contains(Input.pa1userName));
			Assert.assertTrue(options.getText().contains(Input.pa2userName));
			Assert.assertTrue(options.getText().contains(Input.rev1userName));
			Assert.assertTrue(options.getText().contains(Input.rev2userName));
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getadwp_assgn_status().Visible();
			}
		}), Input.wait30);

		getadwp_assgn_status().selectFromDropdown().selectByVisibleText("Assigned");
		getMetaDataInserQuery().Click();
		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQuerySearchButton().Visible();
			}
		}), Input.wait30);
		getQuerySearchButton().waitAndClick(5);
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Search is done and PureHit is : " + pureHit);
		UtilityLog.info("Search is done and PureHit is : " + pureHit);

	}

	// ************************************************* Indium COde
	// ***********************************************************

	/**
	 * @author Jayanthi.ganesan
	 * @Description-This method will validate modify search results in Advanced
	 *                   search Module(during modify search with out editing the
	 *                   searched query we are just clicking the tick or cross mark)
	 *                   Created Date-21/7/2021
	 * @param option               - It should be Yes(if we want to click on Green
	 *                             tick mark ) or No (for cross mark).
	 * @param expectedPureHitCount -PureHit Count after search(before modify search
	 *                             )
	 * @param modifiableQueryType  -Query type should be( "redactions" Or
	 *                             "productions" or "assignments" )
	 */
	public void validateModifiedAdvnSearch(String option, String expectedPureHitCount, String modifiableQueryType) {
		base.waitTime(5);
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getModifyASearch());
		getModifyASearch().ScrollTo();
		getModifyASearch().Click();
		driver.waitForPageToBeReady();
		if (modifiableQueryType == "savedsearch") {
			getModifiableSavedSearchQueryAS().waitAndClick(20);
		} else {
			getModifiableSearchQuery(modifiableQueryType).waitAndClick(20);
		}
		if (option == "YES") {
			base.waitTime(1);
			getModifysearchOKBtn().waitAndClick(20);
		} else {
			base.waitTime(1);
			getModifysearchNOBtn().waitAndClick(20);
		}
		driver.scrollPageToTop();
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().waitAndClick(5);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().Visible();
			}
		}), Input.wait30);
		base.waitTillTextToPresent(getPureHitsCount(), expectedPureHitCount);
		String actualPureHitCount = getPureHitsCount().getText();
		UtilityLog.info("Pure hit count after modified Search in WP " + modifiableQueryType + " when option is "
				+ option + "-" + actualPureHitCount);
		System.out.println("Pure hit count after modified Search in WP " + modifiableQueryType + " when option is "
				+ option + "-" + actualPureHitCount);
		SoftAssert softAssertion = new SoftAssert();
		softAssertion.assertEquals(actualPureHitCount, expectedPureHitCount);
		softAssertion.assertAll();
		base.passedStep("Sucessfully verified the PureHits count after modify Search when option is" + option);

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param saveSearchName
	 * @param searchString
	 * @description-This method creates a advanced content search and save it Under
	 *                   my saved search TAB
	 */
	public void createContentSearchAndSavedSearchAS(String saveSearchName, String searchString) {
		base = new BaseClass(driver);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		advancedContentSearch(searchString);
		saveSearchAdvanced(saveSearchName);

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param saveSearchName
	 * @description -This method select the saved search from advanced search work
	 *              Product Tab.
	 * 
	 */
	public void selectSavedsearchInASWp(String saveSearchName) {
		base = new BaseClass(driver);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.selectproject();
		switchToWorkproduct();
		searchSavedSearch(saveSearchName);
		UtilityLog.info("Selected a saved search " + saveSearchName + "and inserted into query text box ");
		base.stepInfo("Selected a saved search " + saveSearchName + "and inserted into query text box ");

	}

	/**
	 * @author Sowndarya.Velraj
	 * @param saveSearchName
	 * @description -This method select the saved search we created from advanced
	 *              search work Product Tab.
	 * 
	 */
	public void selectNewSavedsearchInASWp(String saveSearchName) {
		base = new BaseClass(driver);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.selectproject();
		switchToWorkproduct();
		driver.waitForPageToBeReady();
		base.waitForElement(getSanitiztionFilter());
		getSanitiztionFilter().waitAndClick(5);
		searchSavedSearch(saveSearchName);

		UtilityLog.info("Selected a saved search " + saveSearchName + "and inserted into query text box ");
		base.stepInfo("Selected a saved search " + saveSearchName + "and inserted into query text box ");

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description-This method will perform bulk assign for the existing assignment
	 * @param assignmentName Modified by Jayanthi 28/21/21
	 */
	public void bulkAssignExisting(String assignmentName) {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(1)) {
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);

		getBulkActionButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait30);
		getBulkAssignAction().Click();
		UtilityLog.info("performing bulk assign");
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentExisting(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.waitForPageToBeReady();
		getSelectAssignmentExisting(assignmentName).Click();
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getContinueButton());
		getContinueButton().waitAndClick(20);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk assign is done, assignment is : " + assignmentName);
		Reporter.log("Bulk assign is done, assignment is : " + assignmentName, true);
	}

	/**
	 * @author Jayanthi.ganesan Modified Date-23/8/21
	 * @description-This method will validate autosuggest search functionality for
	 *                   doc file name or custodian name having more than 50
	 *                   charachters.
	 * @param expected                                DocFileName or custodian name
	 * @param metaDatafield                           to be selected from dropdown
	 *                                                metadata.
	 * @param value                                   to be entered into the
	 *                                                metadata text box
	 * @param expFileDisplay                          (Expected DOC File Name
	 *                                                display in metadata text box)
	 * @param expFileDisplayInQuerySelection(Expected doc File Name display in query
	 *                                                text box)
	 */
	public void validateAutosuggestSearchResult(String expectedDocFileName, String metaDataField, String value,
			String expFileDisplay, String expFileDisplayInQuerySelection) throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		System.out.println("3");
		getAdvancedSearchLink().Click();
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvanceSearch_MetadataBtn().Visible();
			}
		}), Input.wait30);
		getAdvanceSearch_MetadataBtn().Click();
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return SelectFromDropDown(metaDataField).Visible() && SelectFromDropDown(metaDataField).isDisplayed();
			}
		}), Input.wait30);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(3);

		getMetaDataSearchText1().SendKeys(value);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAutoSuggestSearchresults().Visible();
			}
		}), Input.wait30);
		if (getAutoSuggestSearchresults().size() > 0) {
			List<WebElement> autosuggestResults = getAutoSuggestSearchresults().FindWebElements();

			for (WebElement Result : autosuggestResults) {

				if (Result.getText().contains(expectedDocFileName)) {
					base.passedStep(
							"sucessfully verified the autosuggest search funtionality for MetaData-" + metaDataField);
					Result.click();
				}
			}
			String textValue = getMetaDataSearchText1().GetAttribute("value");
			UtilityLog.info(metaDataField + " Displayed in Meta data query text Box" + textValue);
			base.stepInfo(metaDataField + " Displayed in Meta data query text Box" + textValue);
			softAssertion.assertTrue(textValue.contains(expFileDisplayInQuerySelection));
			base.passedStep("Sucessfully verified the " + metaDataField + " display in MetaData  Text Box ");

			getMetaDataInserQuery().Click();
			base.waitForElement(getQueryFromTextBox());
			UtilityLog.info(metaDataField + " in Search Query TextBox" + getQueryFromTextBox().getText());
			softAssertion.assertTrue(getQueryFromTextBox().getText().contains(expFileDisplay));
			base.passedStep("Sucessfully verified the " + metaDataField + " display in Search Query Text Box ");
			getQuerySearchButton().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return processingIcon().Visible() && SelectFromDropDown(metaDataField).isDisplayed();
				}
			}), Input.wait60);
			driver.scrollingToBottomofAPage();
			getPureHitsCount().ScrollTo();
			for (int i = 1; i <= totalDocs(value).size(); i++) {
				String docText = getDocText(value, i).getText();
				int countOfDocTitle = docText.length();
				if (countOfDocTitle >= 50) {
					base.passedStep("This name contains more than 50 characters : " + docText);
					System.out.println(countOfDocTitle);
				} else {
					base.failedStep("This doc name " + docText + " doesn't contains more than 50 characters");
				}
			}
		} else {
			base.failedStep("Auto suggestion list is not displayed");
		}
	}

	/**
	 * @author Jayanthi.ganesan Modidfied on-12/4/22
	 * @param productionName
	 * @throws InterruptedException
	 * @description-This method selects the production under Work product-
	 *                   productions search tree.
	 */
	public void selectProductionstInASwp(String productionName) throws InterruptedException {
		base.waitForElement(getProductionBtn());
		getProductionBtn().ScrollTo();
		getProductionBtn().Click();
		driver.scrollingToBottomofAPage();
		System.out.println(getTree_productions().FindWebElements().size());
		for (WebElement iterable_element : getTree_productions().FindWebElements()) {
			if (iterable_element.getText().contains(productionName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				System.out.println(iterable_element.getText());
				iterable_element.click();
				break;
			}
		}
		driver.scrollingToBottomofAPage();
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		driver.scrollPageToTop();

	}

// Unknown Comment

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

	/**
	 * @author Gopinath Method to add docs met criteria to action board.
	 */
	public void addDocsMetCriteriaToActionBoard() {
		try {
			if (getPureHitAddButton().isDisplayed()) {
				getPureHitAddButton().waitAndClick(5);
			} else {
				System.out.println("Pure hit block already moved to action panel");
				UtilityLog.info("Pure hit block already moved to action panel");
			}

			driver.scrollPageToTop();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getBulkActionButton().Visible();
				}
			}), Input.wait30);
			Thread.sleep(2000); // App synch
			getBulkActionButton().waitAndClick(5);
			Thread.sleep(2000); // App Synch

			if (getViewBtn().isElementAvailable(2)) {
				driver.waitForPageToBeReady();

				WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
				Actions actions = new Actions(driver.getWebDriver());
				wait.until(ExpectedConditions.elementToBeClickable(getViewBtn().getWebElement()));
				actions.moveToElement(getViewBtn().getWebElement()).build().perform();

				base.waitForElement(getDocViewFromDropDown());
				getDocViewFromDropDown().waitAndClick(10);
			} else {
				getDocViewAction().waitAndClick(10);
				base.waitTime(3); // added for stabilization
			}

			System.out.println("Navigated to docView to view docs");
			UtilityLog.info("Navigated to docView to view docs");

		} catch (Exception e) {
			UtilityLog.info("Failed to Perform advanced Search With Content And MetaData Option");
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Mohan date: 15/8/2021 Modified date:28/9/2021 Modified
	 *         By:Baskar Description:Darg the purehit and view in docview
	 */

	public void ViewInDocViews() throws InterruptedException {
		driver.waitForPageToBeReady();

		if (getPureHitAddBtn().isElementAvailable(2)) {
			getPureHitAddBtn().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000); // App synch
		getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch

		if (getViewBtn().isElementAvailable(2)) {
			driver.waitForPageToBeReady();

			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			Actions actions = new Actions(driver.getWebDriver());
			wait.until(ExpectedConditions.elementToBeClickable(getViewBtn().getWebElement()));
			actions.moveToElement(getViewBtn().getWebElement()).build().perform();

			base.waitForElement(getDocViewFromDropDown());
			getDocViewFromDropDown().waitAndClick(10);
		} else {
			getDocViewAction().waitAndClick(10);
			base.waitTime(3); // added for stabilization
		}

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");

	}

	/**
	 * @author Indium-Mohan date: 15/8/2021 Modified date: 8/24/21 Modified by :
	 *         Mohan V Description:Function to perform content search for a given
	 *         search string
	 */

	public int advancedNewContentSearch(String SearchString) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNewSearchButton().Visible() && getNewSearchButton().Enabled();
			}
		}), Input.wait30);
		driver.scrollPageToTop();
		getNewSearchButton().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDataBtn().Visible() && getContentAndMetaDataBtn().Enabled();
			}
		}), Input.wait30);
		getContentAndMetaDataBtn().waitAndClick(5);
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentSearchInputAudio().Visible();
			}
		}), Input.wait30);
		getAdvancedContentSearchInputAudio().SendKeys(SearchString);
		// Click on Search button
		getQuerySearchBtn().Click();
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCounts().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		Reporter.log("Serach is done for '" + SearchString + "' and PureHit is : " + pureHit, true);
		UtilityLog.info("Serach is done for " + SearchString + " and PureHit is : " + pureHit);
		return pureHit;
	}

	/**
	 * @author Gopinath
	 * @Description Method to creating bulk folder in search page.
	 * @param folderName -- (foldername is a string value that name of folder for
	 *                   bulk action).
	 */
	public void creatingBulkFolderInSearchPage(String folderName) {
		try {
			base.waitForElement(getDocsMetYourCriteriaLabel());
			base.dragAndDrop(getDocsMetYourCriteriaLabel(), getActionPad());
			driver.scrollPageToTop();
			Thread.sleep(2000);
			base.waitForElement(getActionDropDownButton());
			docViewMetaDataPage.selectValueFromDropDown(getActionDropDownButton(), getBulkFolderOption());
			base.waitForElement(docViewMetaDataPage.getNewFolderLink());
			docViewMetaDataPage.getNewFolderLink().isDisplayed();
			docViewMetaDataPage.getNewFolderLink().Click();
			base.waitForElement(getSelectAllFoldersOption());
			getSelectAllFoldersOption().isDisplayed();
			getSelectAllFoldersOption().Click();
			try {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_F11);
				robot.keyRelease(KeyEvent.VK_F11);
			} catch (Exception e) {
				UtilityLog.info("Failed to full size screen");
				e.printStackTrace();
			}
			base.waitForElement(docViewMetaDataPage.getNameTextField());
			docViewMetaDataPage.getNameTextField().SendKeys(folderName);
			base.waitForElement(getContinueButton());
			for (int i = 0; i < 10; i++) {
				try {
					docViewMetaDataPage.getContinueButton().Click();
					break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
			docViewMetaDataPage.enableActionRequestToogles();
			docViewMetaDataPage.getFinalizeAssignmentButton().Click();
			base.waitForElement(base.getSuccessMsg());
			base.getSuccessMsg().isElementAvailable(10);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
				base.passedStep("Bulk Documents added to folder successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while creating bulk folder in search page" + e.getMessage());
		}
	}

	/**
	 * @author : Jeevitha
	 * @param searchName description: Save Saved Search In Deafult TAb Modified on :
	 *                   11/16/21 - added catch e1
	 */
	public void saveSearchDefaultTab(String searchName) {
		if (getSaveSearch_Button().isElementAvailable(5)) {
			getSaveSearch_Button().waitAndClick(5);
		} else if (getAdvanceS_SaveSearch_Button().isElementAvailable(5)) {
			getAdvanceS_SaveSearch_Button().waitAndClick(5);
		} else {
			getBsSecondSaveSearch_Button().waitAndClick(5);
		}

		if (getSaveAsNewSearchRadioButton().isElementAvailable(2)) {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} else {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait60);
		getSavedSearch_DefaultSgTab().Click();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		base.stepInfo("Saved search with name - " + searchName);
	}

	/**
	 * @author : Jeevitha
	 * @param searchName description: Save Saved Search In Node TAb Modified on :
	 *                   12/9/21 modified by : Raghuram A
	 * @Stabilization : changes implemented
	 */
	public void saveSearchInNode(String searchName) throws InterruptedException {

		// Click Save Button
		saveSearchAction();

		if (getSaveAsNewSearchRadioButton().isElementAvailable(3)) {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} else {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}
		// My saved search tab
		if (getSavedSearch_MySearchesTabClosed().isElementAvailable(3)) {
			getSavedSearch_MySearchesTabClosed().waitAndClick(3);
		} else {
			System.out.println("MY SavedSearch TAb dropdown Already CLicked");
		}
		getSavedSearch_MySearchesNewNode().waitAndClick(5);

		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().waitAndClick(5);
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignMentName
	 * @param reviewer
	 * @param status
	 * @return
	 * @throws InterruptedException
	 * @modified by jayanthi //added if loop in status selection.
	 */
	public ArrayList<String> selectAssignmentInWPSWthFilters(final String assignMentName, String reviewer,
			String status) throws InterruptedException {

		base.waitForElement(getWP_assignmentsBtn());
		getWP_assignmentsBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTree().Visible();
			}
		}), Input.wait30);
		ArrayList<String> reviwersList = new ArrayList<String>();

		for (int i = 1; i <= getAllSelectedAssgnReviewers().size(); i++) {
			String reviewers = getSelectedAssgnReviewers(i).getText();
			reviwersList.add(reviewers);
		}
		Collections.sort(reviwersList);
		selectReviewerInAssgnWP(reviewer).waitAndClick(3);
		if (!(status == null)) {
			selectStatusInAssgnWp(status).waitAndClick(2);
		}
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(assignMentName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				System.out.println(iterable_element.getText());
				UtilityLog.info(iterable_element.getText());
				iterable_element.click();
				break;
			}
		}
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);

		driver.scrollPageToTop();
		return reviwersList;
	}

	/**
	 * @author Jayanthi Ganesan modified date-26/11/21
	 * @param assignmentGroup
	 * @description Create bulk assign with new assignment(under assignment group)
	 *              in advanced search.
	 */
	public void bulkAssignWithNewAssgn(String assignmentGroup) {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(1)) {
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		base.waitForElement(getBulkActionButton());

		getBulkActionButton().Click();
		base.waitForElement(getBulkAssignAction());
		getBulkAssignAction().Click();
		UtilityLog.info("performing bulk assign");
		driver.waitForPageToBeReady();
		getBulkNewTab().waitAndClick(3);
		base.waitForElement(selectExistingassgnGroup(assignmentGroup));
		driver.scrollingToBottomofAPage();
		driver.scrollingToElementofAPage(selectExistingassgnGroup(assignmentGroup));
		selectExistingassgnGroup(assignmentGroup).waitAndClick(30);
		base.stepInfo("Existing group is selected");
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		// base.waitForElement(countOfDocsInBulkAssgnTree());
		base.waitTillElemetToBeClickable(getContinueButton());

		base.waitForElement(getContinueButton());
		getContinueButton().waitAndClick(30);
		base.waitForElement(getFinalizeButton());
		getFinalizeButton().waitAndClick(30);
		UtilityLog.info("Bulk assign is done, for the group is : " + assignmentGroup);
		Reporter.log("Bulk assign is done, for the group is : " + assignmentGroup, true);
	}

	/**
	 * 
	 * @author Jayanthi.ganesan
	 * 
	 */
	public void viewInDocView_redactions() {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		driver.scrollPageToTop();
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);

		if (getView().isDisplayed()) {
			driver.waitForPageToBeReady();
			Actions act = new Actions(driver.getWebDriver());
			act.moveToElement(getView().getWebElement()).build().perform();
		} else {
			System.out.println("View is not found");
		}
		base.waitForElement(getDocViewActionDL());
		getDocViewActionDL().waitAndClick(5);

		UtilityLog.info("Navigated to docView to view docs");
		base.stepInfo("Navigated to docView to view docs");
	}

	/**
	 * @author Jeevitha Description: filters Audio and Non-Audio search
	 * @param SearchString
	 * @param language
	 * @return
	 */
	public int AudioAndNonAudioSearch(String SearchString, String language) {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtn().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtn().Click();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentSearchInput().Visible();
			}
		}), Input.wait30);
		getAdvancedContentSearchInput().SendKeys(SearchString);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_Audio().Visible();
			}
		}), Input.wait30);
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioLanguage().Visible();
			}
		}), Input.wait30);
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		// Enter search string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAudioTextBox().Visible();
			}
		}), Input.wait30);
		getAudioTextBox().SendKeys(SearchString);
		driver.scrollPageToTop();
		// Click on Search button
		getQuerySearchButton().Click();

		// look for warnings, in case of proximity search
		try {
			if (getTallyContinue().isElementAvailable(5)) {
				getTallyContinue().waitAndClick(5);
				Thread.sleep(4000);
			}
		} catch (Exception e) {

		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		Reporter.log("Serach is done for '" + SearchString + "' and PureHit is : " + pureHit, true);
		UtilityLog.info("Serach is done for " + SearchString + " and PureHit is : " + pureHit);

		return pureHit;
	}

	/**
	 * @author Indium-Mohan date: 24/8/2021 Modified date: NA Description: To View
	 *         the NearDupe Doc in the DocView
	 */
	public void ViewNearDupeDocumentsInDocView() throws InterruptedException {

		if (getNearDupePureHitsCount().isElementAvailable(3))
			getNearDupePureHitsCount().waitAndClick(10);
		else
			UtilityLog.info("Pure hit is already moved");

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		getBulkActionButton().waitAndClick(10);
		Thread.sleep(1000);

//		if (getDocViewAction().isElementAvailable(6)) {
//			getDocViewAction().waitAndClick(10);
//		} else {
//			getDocViewActionDL().Click();
//		}

		if (getViewBtn().isElementAvailable(2)) {
			driver.waitForPageToBeReady();

			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			Actions actions = new Actions(driver.getWebDriver());
			wait.until(ExpectedConditions.elementToBeClickable(getViewBtn().getWebElement()));
			actions.moveToElement(getViewBtn().getWebElement()).build().perform();

			base.waitForElement(getDocViewFromDropDown());
			getDocViewFromDropDown().waitAndClick(10);
		} else {
			getDocViewAction().waitAndClick(10);
			base.waitTime(3); // added for stabilization
		}

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");

	}

	/**
	 * @Author : Indium-Raghuram date: 8/31/21 NA Modified date: N/A Modified by:
	 * @Description : Selecting Audio document With more than 40 document
	 */
//			        Reusable method for selecting audio document with more number of document
	public void audioDocumentMetaData() {
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		base.waitForElement(getBasicSearch_MetadataBtn());
		getBasicSearch_MetadataBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().selectFromDropdown().selectByVisibleText("AudioPlayerReady");
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys("1");
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		// Click on Search button
		base.waitForElement(getSearchButton());
		getSearchButton().waitAndClick(5);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Indium-Mohan date: 24/8/2021 Modified date: NA Description: To
	 *         BulkAssign the NearDupe Doc in the to an Assignment
	 */
	public void bulkAssignNearDupeDocuments() {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		if (getNearDupesAddButton().isElementAvailable(5)) {
			getNearDupesAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait60);

		getBulkAssignAction().waitAndClick(10);

		System.out.println("performing bulk assign");
		UtilityLog.info("performing bulk assign");

	}

	/**
	 * @Author : Indium-Raghuram date: 9/01/21 NA Modified date: N/A Modified by:
	 * @Description :
	 */
	public void saveSearchPopupVerification() {
		try {
			softAssert.assertTrue(getSaveSearchPopupTitle().Visible());
			base.stepInfo("Search Popup landed ");
		} catch (Exception e) {
			System.out.println("Search Popup landing fails ");
		}
		try {
			softAssert.assertTrue(getSaveSearchPopupRadioButton("Overwrite existing").Visible());
			base.stepInfo("Radio button : Overwrite existing - Present");
		} catch (Exception e) {
			System.out.println("Overwrite existing not Present");
		}
		try {
			softAssert.assertTrue(getSaveSearchPopupRadioButton("Save as new search").Visible());
			base.stepInfo("Radio Button : Save as new search - Present");
		} catch (Exception e) {
			System.out.println("Save as new search not Present");
		}
		try {
			softAssert.assertTrue(getSaveSearchPopupFolderName("My Saved Search").Visible());
			base.stepInfo("My saved search is present");
		} catch (Exception e) {
			System.out.println("My Saved Search not present");
		}
		try {
			softAssert.assertTrue(getSaveSearchPopupFolderName("Shared with Default Security Group").Visible());
			base.stepInfo("Shared with Default Security Group is present");
		} catch (Exception e) {
			System.out.println("Shared with Default Security Group");
		}
	}

	/**
	 * @author Jeevitha Description:Function to fetch Conceptualy similar hit count
	 * @return
	 * @throws InterruptedException
	 */
	public String verifyConceptuallySimilarCount() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptualCount().Present();
			}
		}), Input.wait60);
		String conceptcount = getConceptualCount().getText();

		return conceptcount;

	}

	// ***Jagdeesh.jana
	public void VerifyDocViewImages() {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);

		base.waitForElement(getDocViewAction());
		getDocViewAction().waitAndClick(10);

		base.waitForElement(getDocviewImageTab());
		getDocviewImageTab().waitAndClick(10);

	}

	public String basicMetaDataAutosuggest(String metaDataField, String val1, int clickTimes) {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		getBasicSearch_MetadataBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectMetaData().Visible();
			}
		}), Input.wait30);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);

		getMetaDataSearchText1().SendKeys(val1);
		base.hitDownKey(clickTimes);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAutoSuggest().Visible();
			}
		}), Input.wait60);
		getAutoSuggest().waitAndClick(10);

		getMetaDataInserQuery().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMetaDataInsertedVAlue().Visible();
			}
		}), Input.wait60);
		String value = getMetaDataInsertedVAlue().getWebElement().getText();
		System.out.println(value);

		// Click on Search button
		getSearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Search is done for " + metaDataField + " with value " + value + " purehit is : " + pureHit);
		UtilityLog.info("Search is done for " + metaDataField + " with value " + value + " purehit is : " + pureHit);

		return value;
	}

	/**
	 * @Author Jeevitha #Stabilized
	 */
	public String checkBatchRedactionCount() {
		DocViewPage docviewpage = new DocViewPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRedactBtn().Visible();
			}
		}), Input.wait30);
		getRedactBtn().waitAndClick(10);

		docviewpage.selectBatchRedactedDoc();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchRedactionCount().Visible();
			}
		}), Input.wait30);
		System.out.println(getBatchRedactionCount().getText());
		System.out.println(getSearchElementCount().getText());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTrashCanIcon().Visible();
			}
		}), Input.wait30);
		return getBatchRedactionCount().getText();

	}

	/**
	 * @Author Mohan Created on 15/09/2021 Modified by : Raghuram on : 10-25-21
	 * @Description: To Save the Searched Query
	 */
	public void saveSearchQuery(String SaveName) {

		try {
			driver.waitForPageToBeReady();
			// base.waitForElement(getSavedSearchBtn());
			// getSavedSearchBtn().waitAndClick(5);

			try {
//				base.waitForElement(getSaveSearch_Button());
				getSaveSearch_Button().waitAndClick(5);
			} catch (Exception e) {
				try {
//					base.waitForElement(getAdvanceS_SaveSearch_Button());
					getAdvanceS_SaveSearch_Button().waitAndClick(5);
				} catch (Exception e1) {
					getBsSecondSaveSearch_Button().waitAndClick(5);
				}
			}

			base.waitForElement(getMySavedSearch());
			getMySavedSearch().waitAndClick(5);

			base.waitForElement(getSaveSearch_Name());
			getSaveSearch_Name().SendKeys(SaveName);

			base.waitForElement(getSaveSearch_SaveButton());
			getSaveSearch_SaveButton().waitAndClick(5);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Saved search is done successfully");
		}
	}

	/**
	 * @Author Jayanthi Created on 16/09/2021
	 * @Description: To Create Metadata advanced search
	 * @param metaDataField
	 * @param val1--metadata value
	 * @return
	 */
	public int MetaDataSearchInAdvancedSearch(String metaDataField, String val1) {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.waitForElement(getAdvancedSearchLink());
		getAdvancedSearchLink().waitAndClick(3);
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().waitAndClick(3);
		// Enter search string
		base.waitForElement(getAdvanceSearch_MetadataBtn());
		getAdvanceSearch_MetadataBtn().waitAndClick(3);
		base.waitForElement(getSelectMetaData());
		// getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(3);
		// Click on Search button
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().waitAndClick(3);
		if (getTallyContinue().isElementAvailable(2)) {
			getTallyContinue().Click();
		}

		base.waitForElement(getPureHitsCount());
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		base.stepInfo("Search is done for " + metaDataField + " with value " + val1 + " purehit is : " + pureHit);
		return pureHit;
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param metadata field, value
	 * @throws InterruptedException
	 * @description This method will assert the page navigated to advanced search,
	 *              then clear existing query and search new query
	 */
	public int clearExistingQueryAndSearchNewQuery(String metaDataField, String val1) throws InterruptedException {
		SoftAssert softAssertion = new SoftAssert();
		// base.waitTillElemetToBeClickable(getModifyASearch());
		String currentUrl = driver.getUrl();
		softAssertion.assertEquals(currentUrl, Input.url + "Search/Searches");
		getModifyASearch().isElementAvailable(5);
		base.waitTillElemetToBeClickable(getModifyASearch());
		getModifyASearch().waitAndClick(10);
		base.waitForElement(getModifiableSavedSearchQueryAS());
		getModifiableSavedSearchQueryAS().ScrollTo();
		base.waitForElement(getModifiableSavedSearchQueryDeleteBtnAS());
		getModifiableSavedSearchQueryDeleteBtnAS().ScrollTo();
		getModifiableSavedSearchQueryDeleteBtnAS().waitAndClick(5);
		base.stepInfo("Existing query  is cleared");
		// Enter search string
		base.waitForElement(getAdvanceSearch_MetadataBtn());
		getAdvanceSearch_MetadataBtn().Click();
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		// Click on Search button
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();
		base.waitForElement(getPureHitsCount());
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsLastCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsLastCount().getText());
		base.stepInfo("New advanced search query is performed");
		base.stepInfo("Search is done for " + metaDataField + " with value " + val1 + " purehit is : " + pureHit);
		return pureHit;
	}

	/**
	 * @param searchString2
	 * @param searchNum
	 * @Author : Indium-Raghuram date: 9/13/21 NA Modified date:9/14/21 N/A Modified
	 *         by: Raghuram A
	 * @Description :
	 * @Stabilization : changes done
	 */
	public void modifySearchTextArea(int searchNum, String searchString2, String editSearch, String actionType) {
		try {
			driver.waitForPageToBeReady();
			base.stepInfo("Current search string : " + searchString2);
			getEditSessionSearchTextField(searchNum, searchString2).waitAndClick(10);
//			getTextAreaEdit().Clear();
			getTextAreaEdit().SendKeys(editSearch);
			base.stepInfo("Modified search string : " + editSearch);
			if (actionType.equals("Save")) {
				getModifysearchOKBtn().waitAndClick(3);
			} else if (actionType.equals("Cancel")) {
				getModifysearchNOBtn().waitAndClick(3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author jeevitha Description : Basic content search with few changes
	 * @param SearchString
	 * @param select
	 * @param Query
	 */
	public int basicContentSearchWithSaveChanges(String SearchString, String select, String Query) {
		int pureHit = 0;
		// Enter seatch string
		if (Query.equals("First")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getEnterSearchString().Visible();
				}
			}), Input.wait30);
			getEnterSearchString().SendKeys(SearchString);
		} else if (Query.equals("Third")) {
			driver.WaitUntil((new Callable<Boolean>() {

				public Boolean call() {
					return getEnterStringInBSCurrent().Visible();
				}
			}), Input.wait30);
			getEnterStringInBSCurrent().SendKeys(SearchString);
		}
		// Click on Search button
		if (select.equals("Yes")) {
			try {
				if (getSecondSearchBtn().isElementAvailable(5)) {
					getSecondSearchBtn().waitAndClick(5);
				}
			} catch (Exception e1) {
			}
			// handle pop confirmation for regex and proximity queries
			try {
				if (getYesQueryAlert().isElementAvailable(8)) {
					getYesQueryAlert().waitAndClick(8);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			// verify counts for all the tiles
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
				}
			}), Input.wait120);
			pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
			System.out.println("Search is done  " + " and PureHit is :" + pureHit);
			UtilityLog.info("Search is done " + " and PureHit is : " + pureHit);
			Reporter.log("Search is done " + " and PureHit is : " + pureHit, true);
		} else if (select.equals("No")) {
			System.out.println("Search button Is Not Clicked");
		}
		return pureHit;
	}

	/**
	 * @author Jeevitha Description : Selects operator in Basic Search
	 * @param operator
	 */
	public void selectOperatorInBasicSearch(String operator) {

		base.waitForElement(getOperatorDDinBS());
		getOperatorDDinBS().waitAndClick(10);
		if (operator.equalsIgnoreCase("AND")) {
			base.waitForElement(getOperatorANDinBS());
			getOperatorANDinBS().waitAndClick(15);
		}
		if (operator.equalsIgnoreCase("OR")) {
			base.waitForElement(getOperatorORinBS());
			getOperatorORinBS().waitAndClick(15);
		}
		if (operator.equalsIgnoreCase("NOT")) {
			base.waitForElement(getOperatorNOTinBS());
			getOperatorNOTinBS().waitAndClick(15);

		}

	}

	/**
	 * @author Jayanthi
	 * @param remarks
	 * @param val1
	 * @return
	 */
	public int getCommentsOrRemarksCount(String remarks, String val1) {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.waitForElement(getCommentsFieldAndRemarks());
		getCommentsFieldAndRemarks().waitAndClick(5);
		base.waitForElement(SelectFromDropDown(remarks));
		SelectFromDropDown(remarks).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		// Click on Search button
		base.waitForElement(getSearchButton());
		getSearchButton().Click();
		// two handle twosearch strings
		base.waitForElement(getPureHitsCount());
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsLastCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		int pureHit = Integer.parseInt(getPureHitsLastCount().getText());
		System.out.println("Search is done for " + remarks + " with value " + val1 + " purehit is : " + pureHit);
		base.stepInfo("Search is done for " + remarks + " with value " + val1 + " purehit is : " + pureHit);
		return pureHit;

	}

	/**
	 * @author Gopinath modified date-NA
	 * @throws InterruptedException
	 * @description Create bulk assign with new assignment.
	 */
	public void bulkAssignWithNewAssignment() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitTime(2);
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.waitTime(3);
		getBulkActionButton().isElementAvailable(10);
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().Click();
		getBulkAssignAction().isElementAvailable(10);
		base.waitForElement(getBulkAssignAction());
		getBulkAssignAction().Click();
		UtilityLog.info("performing bulk assign");
		driver.waitForPageToBeReady();
		getBulkNewTab().waitAndClick(3);
		for (int i = 0; i < 15; i++) {
			try {
				base.waitTime(3);
				base.waitTillElemetToBeClickable(getContinueButton());
				getContinueButton().Click();
				break;
			} catch (Exception e) {
				base.waitTime(1);
			}
		}
		getFinalizeButton().isElementAvailable(10);
		base.waitForElement(getFinalizeButton());
		getFinalizeButton().Click();
		Reporter.log("Bulk assign is completed", true);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SearchString
	 * @param language
	 * @param ThresholdValue(it should be "max" or "min" or "default")
	 * @return This method will select the threshold value to
	 *         Default,Maximum,Minimum and perform a audio search
	 * @throws InterruptedException
	 */
	public String VerifyaudioSearchThreshold(String SearchString, String language, String ThresholdValue)
			throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		getAdvancedSearchLink().waitAndClick(20);
		base.waitForElement(getAs_Audio());
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);
		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		base.stepInfo("Selected Language as " + language);
		driver.waitForPageToBeReady();
		Actions action = new Actions(driver.getWebDriver());
		action.clickAndHold(GetSliderConfidenceThreshold().getWebElement());
		if (ThresholdValue.equalsIgnoreCase("max")) {
			action.moveToElement(GetSliderConfidenceThreshold().getWebElement(), 115, 0);
		}
		if (ThresholdValue.equalsIgnoreCase("min")) {
			action.moveToElement(GetSliderConfidenceThreshold().getWebElement(), -115, 0);
		}
		action.release();
		action.build().perform();
		GetSliderConfidenceThreshold().Click();
		// Enter search string
		base.waitForElement(getAs_AudioText());
		getAs_AudioText().SendKeys(SearchString);
		base.stepInfo("Entered a text in search text box" + SearchString);
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		String ActualThresholdValue = GetConfidenceThresholdInSearchResult().getText();
		System.out.println(
				"Audio Search is done for " + SearchString + " and Theshold value is : " + ActualThresholdValue);
		base.stepInfo("Audio Search is done for " + SearchString + " and Threshold value is : " + ActualThresholdValue);

		return ActualThresholdValue;
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param metadata field, value
	 * @throws InterruptedException
	 * @description This method will select alone the saved searches from the work
	 *              product tree.
	 */
	public void selectSavedsearchesInTree(String SaveName) throws InterruptedException {

		base.waitForElement(getSavedSearchName(SaveName));
		driver.scrollingToBottomofAPage();
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(SaveName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				base.waitTime(2);
				iterable_element.click();
				break;
			}
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @description This method will run and return conceptual hits.
	 */
	public int runAndVerifyConceptualSearch() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getConceptuallyPlayButton());
		getConceptuallyPlayButton().waitAndClick(20);
		base.waitForElement(getConceptualCount());
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptualCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		int conceptualCount = Integer.parseInt(getConceptualCount().getText());
		base.stepInfo("Search is done and the conceptualCount is : " + conceptualCount);
		return conceptualCount;
	}

	/**
	 * 
	 * @author Jeevitha --
	 * @Stabilization changes done - isElementAvailable
	 */
	public int advancedContentSearchWithSearchChanges(String SearchString, String select) {
		int pureHit = 0;
		// To make sure we are in basic search page

		try {
			if (getAdvancedSearchLinkCurrent().isDisplayed()) {
				getAdvancedSearchLinkCurrent().waitAndClick(10);
			} else {
				System.out.println("Already in Advance Search page");
			}
		} catch (Exception e) {
			System.out.println("Already in Advance Search page");
		}

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {
				return getContentAndMetaDatabtnCurrent().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtnCurrent().Click();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentSearchInputCurrent().Visible();
			}
		}), Input.wait30);
		getAdvancedContentSearchInputCurrent().SendKeys(SearchString);

		// Click on Search button
		if (select.equals("Yes")) {
			getAdvanceSearch_btn_Current().waitAndClick(5);

			// look for warnings, in case of proximity search
			try {
				if (getTallyContinue().isElementAvailable(7)) {
					getTallyContinue().waitAndClick(5);
					Thread.sleep(4000);
				}
			} catch (Exception e) {

			}

			// verify counts for all the tiles
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
				}
			}), Input.wait90);

			pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
			Reporter.log("Serach is done for '" + SearchString + "' and PureHit is : " + pureHit, true);
			UtilityLog.info("Serach is done for " + SearchString + " and PureHit is : " + pureHit);

			return pureHit;
		} else if (select.equals("No")) {
			System.out.println("Search button Is Not Clicked");

		}
		return pureHit;
	}

	/**
	 * modified on 11/16/21 -
	 * 
	 * @param searchName
	 * @param groupName
	 * @throws InterruptedException
	 * @Stablization - changes done
	 */
	public void saveSearchAtAnyRootGroup(String searchName, String groupName) throws InterruptedException {
		driver.waitForPageToBeReady();
		saveSearchAction();

		if (getSaveAsNewSearchRadioButton().isElementAvailable(5)) {
			// base.waitForElement(getSaveAsNewSearchRadioButton());
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} else {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}
		try {
			driver.waitForPageToBeReady();
			base.waitTime(4);
			if (getSaveSearchPopupFolderName(groupName).isElementAvailable(5)) {
				base.stepInfo(groupName + " : is present");
				base.waitTillElemetToBeClickable(getSaveSearchPopupFolderName(groupName));
				getSaveSearchPopupFolderName(groupName).waitAndClick(10);
			} else {
				System.out.println(groupName + " : SG not available");
				base.stepInfo(groupName + " : SG not available");
			}
		} catch (Exception e) {
			System.out.println(groupName + " : SG not available");
			base.stepInfo(groupName + " : SG not available");
		}

		base.waitForElement(getSaveSearch_Name());
		getSaveSearch_Name().Click();
		getSaveSearch_Name().SendKeys(searchName);

		base.waitForElement(getSaveSearch_SaveButton());
		getSaveSearch_SaveButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		base.VerifySuccessMessage("Saved search saved successfully");

		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	public int modifyExistingQueryByOkandCancelBtn(String option) throws InterruptedException {
		base.waitTillElemetToBeClickable(getModifyASearch());
		getModifyASearch().waitAndClick(10);
		base.waitTillElemetToBeClickable(getQueryFromTextBox());
		getQueryFromTextBox().waitAndClick(10);
		base.stepInfo("Existing query is edited");
		if (option == "Yes") {
			base.waitTillElemetToBeClickable(getModifysearchOKBtn());
			getModifysearchOKBtn().waitAndClick(10);
			base.passedStep("Ok button is clicked");
		} else {
			base.waitTillElemetToBeClickable(getModifysearchNOBtn());
			getModifysearchNOBtn().waitAndClick(10);
			base.passedStep("Cancel button is clicked");
		}
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsLastCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		int pureHit = Integer.parseInt(getPureHitsLastCount().getText());
		Reporter.log("Serach is done and PureHit is : " + pureHit, true);
		UtilityLog.info("Serach is done and PureHit is : " + pureHit);

		return pureHit;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param productionName
	 * @description-This method selects the production under Work product-
	 *                   productions search tree.
	 */
	public void selectMultipleProductionstWithAlreadyProducedFilter() throws InterruptedException {
		base.waitForElement(getProductionBtn());
		getProductionBtn().Click();
		driver.scrollingToBottomofAPage();
		System.out.println(getTree_productions().FindWebElements().size());
		UtilityLog.info(getTree_productions().FindWebElements().size());

		for (int i = 1; i < 3; i++) {
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectProductionName(i));
			getSelectProductionName(i).ScrollTo();
			getSelectProductionName(i).waitAndClick(10);
			selectedProductionName = getSelectProductionName(i).getText();
			System.out.println(selectedProductionName);

		}

		base.waitForElement(getadwp_assgn_status());
		getadwp_assgn_status().waitAndClick(5);
		base.waitForElement(getDdnAlreadyProducedOption());
		getDdnAlreadyProducedOption().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		driver.scrollPageToTop();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param productionName
	 * @description-This method selects the production under Work product-
	 *                   productions search tree.
	 */
	public void selectMultipleProductionstWithSelectedForProductionFilter() throws InterruptedException {
		base.waitForElement(getProductionBtn());
		getProductionBtn().Click();
		driver.scrollingToBottomofAPage();
		System.out.println(getTree_productions().FindWebElements().size());
		UtilityLog.info(getTree_productions().FindWebElements().size());
		for (int i = 1; i < 3; i++) {
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectProductionName(i));
			getSelectProductionName(i).ScrollTo();
			getSelectProductionName(i).waitAndClick(10);
			selectedProductionName = getSelectProductionName(i).getText();
			System.out.println(selectedProductionName);
		}
		base.waitForElement(getadwp_assgn_status());
		getadwp_assgn_status().waitAndClick(5);
		base.waitForElement(getDdnSelectedForProductionOption());
		getDdnSelectedForProductionOption().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		driver.scrollPageToTop();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param productionName
	 */
	public void verifySearchQueryForAlreadyProducedFilter() {

		String actual = "produced";
		base.waitForElement(getQueryTextArea());
		String queryArea = getQueryTextArea().getText();
		softAssert.assertTrue(queryArea.contains(selectedProductionName));
		base.stepInfo("Selected Production Name Matched");
		boolean optionalFilter = queryArea.contains("produced");
		softAssert.assertEquals(optionalFilter, actual);
		base.stepInfo("Selected optional Filter Matched");

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param productionName
	 */
	public void verifySearchQueryForSelectedForProductionFilter() {

		String actual = "selected";
		base.waitForElement(getQueryTextArea());
		String queryArea = getQueryTextArea().getText();
		softAssert.assertTrue(queryArea.contains(selectedProductionName));
		base.stepInfo("Selected Production Name Matched");
		boolean optionalFilter = queryArea.contains("selected");
		softAssert.assertEquals(optionalFilter, actual);
		base.stepInfo("Selected optional Filter Matched");

	}

	/**
	 * @author Mohan
	 * @param conceptually purehit
	 */
	public void getConceptDocument() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptuallyPlayButton().isDisplayed();
			}
		}), Input.wait60);

		getConceptuallyPlayButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptPureHitsCount().isDisplayed();
			}
		}), Input.wait120);

		if (getConceptPureHitsCount().isElementAvailable(2)) {
			getConceptPureHitsCount().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SearchString,language,ThresholdValue
	 */
	public String ModifyAudioSearch(String SearchString, String language, String ThresholdValue)
			throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		base.waitForElement(getModifyASearch());
		getModifyASearch().waitAndClick(5);
		if (SearchString != null) {
			// Enter search string
			base.waitForElement(getAudioModifiableQuery());
			getAudioModifiableQuery().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getModifiableSavedSearchQueryDeleteBtnAS());
			getModifiableSavedSearchQueryDeleteBtnAS().ScrollTo();
			getModifiableSavedSearchQueryDeleteBtnAS().waitAndClick(5);
			base.stepInfo("Existing query  is cleared");
			getAudioQueryTextField().waitAndClick(30);
			getAudioQueryTextField().SendKeys(SearchString);
			driver.waitForPageToBeReady();
			base.stepInfo("Entered a text in search text box" + SearchString);
		}
		if (ThresholdValue != null) {
			driver.waitForPageToBeReady();
			setThresholdAudioSearch(ThresholdValue);
		}
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount_ModifySearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		driver.getPageSource();
		base.waitForElement(GetConfidenceThresholdInSecondSearchResult());
		String ActualModifiedThresholdValue = GetConfidenceThresholdInSecondSearchResult().GetAttribute("textContent");
		base.stepInfo("Threshold value after modify search is" + ActualModifiedThresholdValue);
		return ActualModifiedThresholdValue;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param ThresholdValue
	 */
	public void setThresholdAudioSearch(String ThresholdValue) {
		driver.waitForPageToBeReady();
		Actions action = new Actions(driver.getWebDriver());
		action.clickAndHold(GetSliderConfidenceThreshold().getWebElement());
		if (ThresholdValue.equalsIgnoreCase("max")) {
			action.moveToElement(GetSliderConfidenceThreshold().getWebElement(), 115, 0);
		}
		if (ThresholdValue.equalsIgnoreCase("min")) {
			action.moveToElement(GetSliderConfidenceThreshold().getWebElement(), -115, 0);
		}
		action.release();
		action.build().perform();
		GetSliderConfidenceThreshold().Click();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param searchName
	 * @param savetab(My Saved Search or Shared with Default Security Group)
	 */
	public void saveSearchAdvanced_New(String searchName, String savetab) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getSaveSearch_ASButton());
//		getSaveSearch_ASButton().ScrollTo();
		getSaveSearch_ASButton().waitAndClick(30);
		base.waitForElement(getSaveSearchPopupFolderName(savetab));
		getSaveSearchPopupFolderName(savetab).Click();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().waitAndClick(10);
		try {
			base.VerifySuccessMessage("Saved search saved successfully");
			System.out.println("Saved search with name - " + searchName);
			base.passedStep("Saved search saved successfully under -" + savetab + " with name " + searchName);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to save the Search.");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param language
	 */
	public void VerifyaudioSearchDropdown(String language) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		getAdvancedSearchLink().waitAndClick(20);
		base.waitForElement(getAs_Audio());
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);
		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		driver.waitForPageToBeReady();
		if (getAudioDropDownselectedValue(language).isDisplayed()) {
			base.stepInfo("Selected Audio search Language as " + language);
			base.passedStep("Sucessfully verified whether the langauage drop down is selected as" + language);
		} else {
			base.failedStep("LanguageDrop down option is Not selected as expected");
		}
	}

	/**
	 * Modified by : Jeevitha modified on : 10/13/21
	 */
	public int conceptualSearch_new(String SearchString, String Percentage) {
		int pureHit = 0;
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		getAdvancedSearchLink().Click();
		base.waitForElement(getAs_Conceptual());
		getAs_Conceptual().Click();

		// Enter search string
		base.waitForElement(getAs_ConceptualTextArea());
		getAs_ConceptualTextArea().SendKeys(SearchString);
		driver.waitForPageToBeReady();

		if (Percentage.equalsIgnoreCase("right")) {
			getConceptualRight().Click();
		} else if (Percentage.equalsIgnoreCase("left")) {
			getConceptualLeft().Click();
		} else if (Percentage.equalsIgnoreCase("mid")) {
			getConceptualMid().Click();
		}

		// Click on Search button
		getQuerySearchButton().waitAndClick(5);
		try {

			// verify counts for all the tiles
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
				}
			}), Input.wait90);
			if (getPureHitsCount().isDisplayed()) {
				SoftAssert assertion = new SoftAssert();
				assertion.assertNotNull(pureHit);
				assertion.assertAll();
				pureHit = Integer.parseInt(getPureHitsCount().getText());
				System.out.println("Conceptual search is done for " + SearchString + " and PureHit is : " + pureHit);
				base.passedStep("Conceptual search is done for " + SearchString + " and PureHit is : " + pureHit);
			} else {
				base.failedStep("Conceptual search is not working and pure hit "
						+ " not displayed when we set precision at defualt.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Failed to verify Concept Search when precision is set as default & purehit not displayed ");

		}
		return pureHit;
	}

	public Element getCombinedSearchResults() {
		return driver.FindElementByXPath(".//*[@id='001']/count");
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param metadata field, value, advanced search check box option
	 * @throws InterruptedException
	 * @description This method will check the advanced search option, insert the
	 *              metadata query and search to get overall and conceptual results.
	 */
	public int conceptualSimilarSearchInAdvancedSearch(String metaDataField, String val1, Element element)
			throws InterruptedException {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		SoftAssert softAssertion = new SoftAssert();
		base.waitForElement(getAdvancedSearchLink());
		getAdvancedSearchLink().Click();
		base.stepInfo("Navigated to advanced search page successfuly");
		try {
			base.waitForElement(element);
			element.waitAndClick(10);
			base.passedStep("Advanced search option is selected");
		} catch (Exception e) {
			base.failedStep("Advanced search option is not selected");
			softAssertion.fail();
		}
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().Click();
		// Enter search string
		base.waitForElement(getAdvanceSearch_MetadataBtn());
		getAdvanceSearch_MetadataBtn().Click();
		base.waitForElement(getSelectMetaData());
		// getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		// Click on Search button
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();
		int conceptualHits = runAndVerifyConceptualSearch();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCombinedSearchResults().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		String combinedSearchResults = getCombinedSearchResults().getText();
		softAssertion.assertNotNull(combinedSearchResults, "Search is done and no combined search hits returned");
		base.stepInfo("The combined search results are " + combinedSearchResults);
		return conceptualHits;
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param Folder name
	 * @throws InterruptedException
	 * @description This method will select alone the folder from the work product
	 *              tree.
	 */
	public void selectFolderInTree(String folderName) {
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(folderName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				iterable_element.click();
			}
		}
	}

	/**
	 * @author : Jeevitha
	 * @param searchName description: Save Saved Search In Node TAb
	 * @Stabilization - changes implemented
	 */
	public void saveSearchInNewNode(String searchName, String Node) throws InterruptedException {

		// Stabilization changes
		saveSearchAction();

		// handle pop confirmation for regex and proximity queries
		if (getYesQueryAlert().isElementAvailable(8)) {
			getYesQueryAlert().waitAndClick(8);
		}

		// Radio button selection
		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			base.stepInfo("Radio button already selected");
		}

		// My saved search tab
		if (getSavedSearch_MySearchesTabClosed().isElementAvailable(3)) {
			getSavedSearch_MySearchesTabClosed().waitAndClick(3);
		} else {
			System.out.println("MY SavedSearch TAb dropdown Already CLicked");
		}

		// Node Selection
		if (getSavedSearch_MySearchesNewNode1(Node).isElementAvailable(10)) {
			getSavedSearch_MySearchesNewNode1(Node).waitAndClick(3);
		} else {
			getSavedSearch_MySearchesTab().waitAndClick(10);
		}

		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Saved search saved successfully");
		base.CloseSuccessMsgpopup();
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Raghuram Date : 9/29/21 Description: bulkActions_TagSS_returnCount
	 *         Modified on : N/A modified by : N/A
	 * @throws InterruptedException
	 */
	public String bulkActions_TagSS_returnCount(String TagName) throws InterruptedException {

		base.waitForElement(getBulkNewTab());
		getBulkNewTab().Click();

		base.waitForElement(getEnterTagName());
		getEnterTagName().SendKeys(TagName);

		base.waitForElement(getTagsAllRoot());
		getTagsAllRoot().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		String finalCount = getFinalCount().getText();
		System.out.println(finalCount);
		getFinalizeButton().Click();

		getTallyContinue().waitAndClick(10);

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Tag is done, Tag is : " + TagName);
		UtilityLog.info("Bulk Tag is done, Tag is : " + TagName);

		return finalCount;
	}

	/**
	 * @author Jayanthi.ganesan.Modified by jeevitha
	 * @description-This method will create remarks
	 * @param RemarksName Modified on : 10/26/21 - 5400
	 * 
	 */

	public void createRemarks(String RemarksName) {
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(docViewRedact.remarksIcon());
		docViewRedact.remarksIcon().waitAndClick(25);
		base.waitTillElemetToBeClickable(docViewRedact.getDocView_Redactrec_textarea());
		driver.waitForPageToBeReady();
		Actions actions = new Actions(driver.getWebDriver());
		docViewRedact.getDocView_Redactrec_textarea().ScrollTo();
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 10, 10).clickAndHold()
				.moveByOffset(40, 40).release().build().perform();
		base.stepInfo("text for remarks has been selected");
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys(RemarksName);
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		base.stepInfo("Remarks added sucessfully " + RemarksName);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description-This method will perform bulk assign for the existing assignment
	 * @param assignmentName
	 * 
	 */
	public void bulkAssign_withoutshoppingCartAdd(String assignmentName) {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentExisting(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.waitForPageToBeReady();
		getSelectAssignmentExisting(assignmentName).Click();
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		base.stepInfo("Bulk assign is done, assignment is : " + assignmentName);
		Reporter.log("Bulk assign is done, assignment is : " + assignmentName, true);
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */
	public void bulkAssignThreadedDocs() {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		try {
			getThreadedAddButton().Click();
		} catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait60);

		getBulkAssignAction().waitAndClick(10);

		System.out.println("performing bulk assign");
		base.stepInfo("performing bulk assign");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param DraftQueryName
	 * 
	 */
	public void saveDraftQuery(String DraftQueryName) throws InterruptedException {
		try {

			base.waitForElement(getAdvanceSearch_DraftQuerySave_Button());
			getAdvanceSearch_DraftQuerySave_Button().waitAndClick(5);
		} catch (Exception e) {
			base.waitForElement(getAdvanceSearch_DraftQuerySave_Button());
			getAdvanceSearch_DraftQuerySave_Button().waitAndClick(5);
		}

		try {
			base.waitForElement(getSaveAsNewSearchRadioButton());
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}
		base.waitForElement(getSavedSearch_MySearchesTab());
		getSavedSearch_MySearchesTab().waitAndClick(10);
		base.waitForElement(getSaveSearch_Name());
		getSaveSearch_Name().SendKeys(DraftQueryName);

		base.waitForElement(getSaveSearch_SaveButton());
		getSaveSearch_SaveButton().waitAndClick(10);
		base.VerifySuccessMessage("Saved search saved successfully");
		base.stepInfo("Saved draft query with name - " + DraftQueryName);
	}

	/**
	 * @author Sowndarya.Velraj
	 */
	public void sanitizationWithFolder() {
		switchToWorkproduct();
		base.waitForElement(getWP_FolderBtn());
		getWP_FolderBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getFirstFoldersTabInwp());
		getFirstFoldersTabInwp().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getSavedSearchQueryAS());
		getSavedSearchQueryAS().waitAndClick(5);

		base.waitForElement(getValueTextArea());
		getValueTextArea().Clear();
		getValueTextArea().SendKeysNoClear("[name:['01_Prod*']]");

		base.waitForElement(getDoneIcon());
		getDoneIcon().waitAndClick(10);
		driver.scrollPageToTop();
		base.waitForElement(getSanitiztionFilter());
		getSanitiztionFilter().waitAndClick(5);
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().waitAndClick(5);

	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */

	public void verifyReleventMessage_draftQuery() {
		String ExpectedMessage = "Since this search was never run - it's in Draft State - there are no results to be displayed. Please execute the search to see results.";
		System.out.println("expected" + ExpectedMessage);
		System.out.println("expected" + getReleventMessage().getText());
		if (ExpectedMessage.equalsIgnoreCase(getReleventMessage().getText())) {
			base.passedStep(
					"Relevant message appears when user Navigate - Advanced Search(WorkProduct) - DRAFT Query >> Edit from saved search page on Search Screen");
		} else {
			base.failedStep(
					"Relevant message does not appears when user Navigate - Advanced Search(WorkProduct) - DRAFT Query >> Edit from saved search page on Search Screen");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SearchString,language
	 * 
	 */
	public void CreateDraftaudioSearchQuery(String SearchString, String language) {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		getAdvancedSearchLink().waitAndClick(20);
		base.waitForElement(getAs_Audio());
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);

		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		// Enter search string
		base.waitForElement(getAs_AudioText());
		getAs_AudioText().SendKeys(SearchString);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SearchString
	 * 
	 */
	public void CreateDraftConceptualsearch(String SearchString) {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		getAdvancedSearchLink().waitAndClick(20);
		base.waitForElement(getAs_Conceptual());
		getAs_Conceptual().waitAndClick(5);

		// Enter search string
		base.waitForElement(getAs_ConceptualTextArea());
		driver.waitForPageToBeReady();
		getAs_ConceptualTextArea().SendKeys(SearchString);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Sowndarya.Velraj
	 */
	public void sanitizationWithSavedSearch() {

		String newSavedSearch;
		newSavedSearch = "search_" + Utility.dynamicNameAppender();
		createContentSearchAndSavedSearchAS(newSavedSearch, Input.testData1);
		base.stepInfo("Created a saved search -" + newSavedSearch);
		selectNewSavedsearchInASWp(newSavedSearch);
		driver.scrollPageToTop();
		base.waitForElement(getSanitiztionFilter());
		getSanitiztionFilter().waitAndClick(5);
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		getPureHitsCount().isDisplayed();

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println(pureHit);

	}

	/**
	 * @author Iyappan.Kasinathan
	 */
	public void saveASQueryWithoutSearch(String searchName) {
		base.waitForElement(getSaveSearchInASWithoutSearch());
		getSaveSearchInASWithoutSearch().waitAndClick(30);
		base.waitForElement(getSaveAsNewSearchBtn());
		getSaveAsNewSearchBtn().waitAndClick(3);
		base.waitForElement(getSavedSearch_MySearchesTab());
		getSavedSearch_MySearchesTab().Click();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		base.stepInfo("Saved search with name - " + searchName);
	}

	/**
	 * @author Iyappan.Kasinathan
	 */
	public void saveAdvancedSearchQuery(String searchName) {
		base.waitForElement(getSaveSearch_AdvBtn());
		getSaveSearch_AdvBtn().waitAndClick(10);
		base.waitTillElemetToBeClickable(getSavedSearch_MySearchesTab());
		getSavedSearch_MySearchesTab().Click();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		base.stepInfo("Saved search with name - " + searchName);
	}

	/**
	 * @author Iyappan.Kasinathan Modified on : 10/26/21
	 */
	public void clearExistingQueryAndSearchNewQueryWithoutSearch(String metaDataField, String val1)
			throws InterruptedException {
		SoftAssert softAssertion = new SoftAssert();
		base.waitTillElemetToBeClickable(getModifyASearch());
		String currentUrl = driver.getUrl();
		softAssertion.assertEquals(currentUrl, Input.url + "Search/Searches");
		base.waitTillElemetToBeClickable(getModifyASearch());
		getModifyASearch().waitAndClick(10);
		base.waitForElement(getModifiableSavedSearchQueryAS());
		getModifiableSavedSearchQueryAS().ScrollTo();
		base.waitForElement(getModifiableSavedSearchQueryDeleteBtnAS());
		base.waitTillElemetToBeClickable(getModifiableSavedSearchQueryDeleteBtnAS());
		getModifiableSavedSearchQueryDeleteBtnAS().ScrollTo();
		getModifiableSavedSearchQueryDeleteBtnAS().waitAndClick(5);
		base.stepInfo("Existing query  is cleared");
		base.waitForElement(getAdvanceSearch_MetadataBtn());
		getAdvanceSearch_MetadataBtn().Click();
		base.waitForElement(getSelectMetaData());
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
	}

	public int verifyCombinedSearch(String WPName, String WpSearch, String ContentSearch, String operator1,
			String operator2, String operator3) throws InterruptedException {

		// Content And Metadata search

		base.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.waitForElement(getAdvancedSearchLink());
		getAdvancedSearchLink().Click();
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().Click();

		if (ContentSearch == "yes") {
			// Enter search string for content search
			base.waitForElement(getAdvancedContentSearchInput());
			getAdvancedContentSearchInput().SendKeys(Input.searchString1);
		} else {
			// for metadata search
			base.waitForElement(getAdvanceSearch_MetadataBtn());
			getAdvanceSearch_MetadataBtn().waitAndClick(3);
			base.waitForElement(getSelectMetaData());
			base.waitForElement(getSelectMetaData());
			getSelectMetaData().waitAndClick(3);
			base.waitForElement(SelectFromDropDown(Input.metaDataName));
			SelectFromDropDown(Input.metaDataName).waitAndClick(10);
			base.waitForElement(getMetaDataSearchText1());
			getMetaDataSearchText1().SendKeys("Andrew" + Keys.TAB);
			base.waitForElement(getMetaDataInserQuery());
			getMetaDataInserQuery().waitAndClick(3);
		}

		// Work product search

		base.waitForElement(getWorkproductBtn());
		getWorkproductBtn().Click();
		// condition

		base.waitForElement(getAs_SelectOperation(1));
		getAs_SelectOperation(1).selectFromDropdown().selectByVisibleText(operator1);
		driver.scrollingToBottomofAPage();
		if (WpSearch.equalsIgnoreCase("tag")) {
			selectTagInASwp(WPName);
		}
		if (WpSearch.equalsIgnoreCase("folder")) {
			selectFolderInASwp(WPName);
		}
		if (WpSearch.equalsIgnoreCase("redactions")) {
			selectRedactioninWPS(WPName);
		}
		// Audio search
		base.waitForElement(getAs_Audio());
		getAs_Audio().waitAndClick(10);

		// condition
		base.waitForElement(getAs_SelectOperation(2));
		getAs_SelectOperation(2).selectFromDropdown().selectByVisibleText(operator2);

		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText("North American English");
		// Enter search string
		driver.scrollingToBottomofAPage();
		base.waitForElement(getAs_AudioTextBox_CombinationSearch());
		getAs_AudioTextBox_CombinationSearch().SendKeys(Input.audioSearchString1);
		// conceptual search
		driver.scrollPageToTop();
		base.waitForElement(getAs_Conceptual());
		getAs_Conceptual().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getAs_SelectOperation(3));
		getAs_SelectOperation(3).selectFromDropdown().selectByVisibleText(operator3);
		// Enter search string
		base.waitForElement(getAs_ConceptualTextArea());
		getAs_ConceptualTextArea().SendKeys("right form");
		driver.scrollPageToTop();
		int ActualPureHit = serarchWP();
		return ActualPureHit;
	}

	/**
	 * @author Indium-Mohan date: 06/10/2021 Modified date:NA Modified By:NA
	 *         Description:Darg the ThreadMap purehit and view in docview
	 *         Sttabilised as per new build - Krishna
	 */

	public void ViewThreadedDocsInDocViews() throws InterruptedException {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getThreadedAddButton().Visible();
			}
		}), Input.wait30);
		getThreadedAddButton().waitAndClick(5);
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000);// required
		getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000);// // required
		if (getViewBtn().isElementAvailable(2)) {
			driver.waitForPageToBeReady();

			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			Actions actions = new Actions(driver.getWebDriver());
			wait.until(ExpectedConditions.elementToBeClickable(getViewBtn().getWebElement()));
			actions.moveToElement(getViewBtn().getWebElement()).build().perform();

			base.waitForElement(getDocViewFromDropDown());
			getDocViewFromDropDown().waitAndClick(10);
		} else {
			getDocViewAction().waitAndClick(10);
			base.waitTime(3); // added for stabilization
		}

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");

	}

	/**
	 * @author Indium-Baskar date: 10/4/2021 Modified date: NA
	 * @Description: Assign near dupe document to assignment
	 */
	public void ViewNearDupeDocumentsToBulkAssign() throws InterruptedException {
		driver.waitForPageToBeReady();
		if (getNearDupePureHitsCount().isElementAvailable(8)) {
			getNearDupePureHitsCount().waitAndClick(10);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		driver.scrollPageToTop();
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);
		Thread.sleep(Input.wait30);
		try {
			getBulkAssignAction().waitAndClick(10);
		} catch (Exception e) {
			getBulkAssignAction().Click();
		}

	}

	/**
	 * @author Indium-Baskar date: 10/4/2021 Modified date: NA
	 * @Description: Assign near dupe document to bulk folder
	 */
	public void bulkFolderNearDupe(String folderName) throws InterruptedException {
		// driver.getWebDriver().get(Input.url+"Search/Searches");
		driver.waitForPageToBeReady();
		if (getNearDupePureHitsCount().isElementAvailable(5)) {
			getNearDupePureHitsCount().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(5);
		base.waitForElement(getBulkFolderAction());
		getBulkFolderAction().waitAndClick(5);
		base.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(20);
		base.waitForElement(getEnterFolderName());
		getEnterFolderName().SendKeys(folderName);
		base.waitForElement(getFolderAllRoot());
		getFolderAllRoot().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();
		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk folder is done, folder is : " + folderName);
		Reporter.log("Bulk folder is done, folder is : " + folderName, true);
		driver.getWebDriver().navigate().refresh();
	}

	/**
	 * @author Sowndarya.Velraj
	 */
	public int VerifyaudioSearchAndSetThreshold(String SearchString, String language, int ThresholdValue)
			throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		getAdvancedSearchLink().waitAndClick(20);
		base.waitForElement(getAs_Audio());
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);
		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		base.stepInfo("Selected Language as " + language);
		driver.waitForPageToBeReady();
		Actions action = new Actions(driver.getWebDriver());
		action.clickAndHold(GetSliderConfidenceThreshold().getWebElement());
		action.moveToElement(GetSliderConfidenceThreshold().getWebElement(), ThresholdValue, 0);
		action.release();
		action.build().perform();
		GetSliderConfidenceThreshold().Click();
		// Enter search string
		base.waitForElement(getAs_AudioText());
		getAs_AudioText().SendKeys(SearchString);
		base.stepInfo("Entered a text in search text box" + SearchString);
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Audio Serach is done for " + SearchString + " and  PureHit is : " + pureHit);

		return pureHit;

//		String ActualThresholdValue = GetConfidenceThresholdInSearchResult().getText();
//		System.out.println(
//				"Audio Search is done for " + SearchString + " and Theshold value is : " + ActualThresholdValue);
//		base.stepInfo("Audio Search is done for " + SearchString + " and Threshold value is : " + ActualThresholdValue);
//
//		return ActualThresholdValue;
	}

	/**
	 * @author Sowndarya.Velraj
	 */
	public int audioSearchWithOperator(String SearchString1, String SearchString2, String language, int threshold,
			String operator) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		getAdvancedSearchLink().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_Audio().Visible();
			}
		}), Input.wait30);
		getAs_Audio().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioLanguage().Visible();
			}
		}), Input.wait30);
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		// Enter search string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioText().Visible();
			}
		}), Input.wait30);
		getAs_AudioText().SendKeys(SearchString1 + Keys.ENTER + SearchString2 + Keys.ENTER);

		// Select term operator
		getAudioTermOperator().selectFromDropdown().selectByVisibleText(operator);
		// Select location
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAudioLocation().Visible();
			}
		}), Input.wait30);
		Thread.sleep(6000);

		// select threshold
		Actions action = new Actions(driver.getWebDriver());
		action.clickAndHold(GetSliderConfidenceThreshold().getWebElement());
		action.moveToElement(GetSliderConfidenceThreshold().getWebElement(), threshold, 0);
		action.release();
		action.build().perform();
		GetSliderConfidenceThreshold().Click();

		// Click on Search button
		getQuerySearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Audio Search is done for " + SearchString1 + " and " + SearchString2 + "with operator: "
				+ operator + "and it's PureHit is : " + pureHit);

		return pureHit;

	}

	/**
	 * @author Iyappan.Kasinathan date: 10/7/2021 Modified date: NA
	 * @Description: Select three reviewers in work product search page
	 */
	public ArrayList<String> selectAssignmentAndMultipleReviewersWPSWthFilters(final String assignMentName,
			String reviewer, String RMU, String PA, String status) throws InterruptedException, AWTException {

		base.waitForElement(getWP_assignmentsBtn());
		getWP_assignmentsBtn().Click();
		base.waitForElementCollection(getTree());
		ArrayList<String> reviwersList = new ArrayList<String>();
		for (int i = 1; i <= getAllSelectedAssgnReviewers().size(); i++) {
			String reviewers = getSelectedAssgnReviewers(i).getText();
			reviwersList.add(reviewers);
		}
		System.out.println(reviwersList);
		Collections.sort(reviwersList);
		System.out.println(reviwersList);
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			base.waitForElement(selectReviewerInAssgnWP(reviewer));
			selectReviewerInAssgnWP(reviewer).waitAndClick(10);
			base.waitForElement(selectReviewerInAssgnWP(RMU));
			selectReviewerInAssgnWP(RMU).waitAndClick(10);
			base.waitForElement(selectReviewerInAssgnWP(PA));
			selectReviewerInAssgnWP(PA).waitAndClick(10);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			base.passedStep("The distributed list of users are present in advanced search page users list");
		} catch (Exception e) {
			base.failedStep("The distributed list of users are present in advanced search page users list");
		}
		driver.scrollingToBottomofAPage();
		selectStatusInAssgnWp(status).waitAndClick(2);
		if (status != "Choose Status:") {
			selectDate("From Date");
			selectDate("To Date");
		}
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(assignMentName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				System.out.println(iterable_element.getText());
				UtilityLog.info(iterable_element.getText());
				iterable_element.click();
			}
		}
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		driver.scrollPageToTop();
		return reviwersList;
	}

	/**
	 * @author Raghuram Date : 9/30/21 Description: multipleBasicContentSearch
	 *         Modified on : N/A modified by : N/A
	 * @throws InterruptedException
	 */
	public int multipleBasicContentSearch(String SearchString) {

		// Enter seatch string
		base.waitForElement(getBaseInputDynamic());
		getBaseInputDynamic().SendKeys(SearchString);

		// Click on Search button
		base.waitForElement(getBasicSearchBtnDynamic());
		getBasicSearchBtnDynamic().Click();

		// handle pop confirmation for regex and proximity queries
		try {
			if (getYesQueryAlert().isElementAvailable(8)) {
				getYesQueryAlert().waitAndClick(8);
			}
		} catch (Exception e) {
			System.out.println("No Query Alert");
		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);

		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
		UtilityLog.info("Search is done for " + SearchString + " and PureHit is : " + pureHit);
		Reporter.log("Search is done for " + SearchString + " and PureHit is : " + pureHit, true);
		return pureHit;
	}

	/**
	 * @author Raghuram Date : 9/30/21 Description: multipleBasicContentSearch
	 *         Modified on : 11/17/21 modified by : Raghuram
	 * @throws InterruptedException
	 * @Stablization changes implemented - as adviced replaced try-catch with
	 *               if-statement
	 */
	public void saveSearchInNodewithChildNode(String searchName, String nodeName) throws InterruptedException {
		// SaveSearch
		saveSearchAction();

		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}

		if (getSavedSearch_MySearchesTabClosed().isElementAvailable(4)) {
			getSavedSearch_MySearchesTabClosed().waitAndClick(5);
		} else {
			System.out.println("Already Expanded");
		}

		if (getCreatedNode(nodeName).isElementAvailable(7)) {
			System.out.println("On");
			getCreatedNode(nodeName).waitAndClick(5);
		} else {
			System.out.println("To Expand");
			getExpandCurrentNode().waitAndClick(20);
			getCreatedNode(nodeName).waitAndClick(3);
		}

		driver.waitForPageToBeReady();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Raghuram Date : 9/30/21 Description: multipleBasicContentSearch
	 *         Modified on : N/A modified by : N/A
	 * @return
	 * @throws InterruptedException
	 */
	public HashMap<String, String> saveSearchInNodewithChildNode(List<String> newNodeList, Boolean inputValue)
			throws InterruptedException {
		int purehit;
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		for (String nodeName : newNodeList) {
			String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

			System.out.println(nodeName);
			if (inputValue) {
				purehit = multipleBasicContentSearch(Input.searchString5);
				inputValue = false;
			} else {
				purehit = multipleBasicContentSearch(Input.searchString6);
				inputValue = true;
			}

			driver.waitForPageToBeReady();
			saveSearchInNodewithChildNode(searchName1, nodeName);
			nodeSearchpair.put(nodeName, searchName1);
			getNewSearchButton().waitAndClick(5);
		}
		return nodeSearchpair;
	}

//	Content and Metadata search area
	public void advancedSearchContentMetadata(String contentMetadataString) {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtnC().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtnC().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentAndMetadataInputArea().Visible();
			}
		}), Input.wait30);
		getAdvancedContentAndMetadataInputArea().SendKeys(contentMetadataString);
	}

//	Conceptual search area
	public void advancedSearchConceptual(String conceptualString) {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_ConceptualC().Visible();
			}
		}), Input.wait30);
		getAs_ConceptualC().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_ConceptualTextAreaC().Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		getAs_ConceptualTextAreaC().SendKeys(conceptualString);
	}

//     Audio search area
	public void advancedSearchAudio(String audioString, String language) {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioC().Visible();
			}
		}), Input.wait30);
		getAs_AudioC().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioLanguageC().Visible();
			}
		}), Input.wait30);
		getAs_AudioLanguageC().selectFromDropdown().selectByVisibleText(language);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAs_AudioTextC().Visible();
			}
		}), Input.wait30);
		getAs_AudioTextC().SendKeys(audioString);
	}

//Work Product search area
	public void advancedSearchWorkProduct(String tagName) {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkproductBtnC().Visible();
			}
		}), Input.wait30);
		getWorkproductBtnC().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkProductTags().Visible();
			}
		}), Input.wait30);
		getWorkProductTags().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return selectWorkProductTags(tagName).Visible();
			}
		}), Input.wait30);
		selectWorkProductTags(tagName).waitAndClick(10);
		getInsertInToQueryBtn().waitAndClick(10);
	}

	public int saveAndReturnPureHitCount() {
		driver.scrollPageToTop();
		// Click on Search button
		getQuerySearchButtonC().Click();

		// handle pop confirmation for regex and proximity queries
		try {
			if (getYesQueryAlert().isElementAvailable(8)) {
				getYesQueryAlert().waitAndClick(8);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		driver.scrollingToBottomofAPage();
		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
		return pureHit;
	}

	public Map<String, Integer> advancedSearchWithCombinations(String contentMetadataString, String conceptualString,
			String audioText, String language, String tabName) throws InterruptedException {
		Map<String, Integer> pureHitCount = new HashMap<String, Integer>();
		driver.getWebDriver().get(Input.url + "Search/Searches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

//	Content and Metadata + Conceptual
		advancedSearchContentMetadata(contentMetadataString);// CM Search
		advancedSearchConceptual(conceptualString);// Conceptual Search
		String saveName1 = "search_" + Utility.dynamicNameAppender();
		pureHitCount.put(saveName1, saveAndReturnPureHitCount());// Get Purehit count
		saveSearchInNode_DefaultSecurityGroup(saveName1);// Save in Default Security Group
		getNewSearchButton().waitAndClick(10);// Click New Search Button

//	Content and Metadata + Audio
		advancedSearchContentMetadata(contentMetadataString);// CM Search
		advancedSearchAudio(audioText, language);// Audio Search
		String saveName2 = "search_" + Utility.dynamicNameAppender();
		pureHitCount.put(saveName2, saveAndReturnPureHitCount());// Get Purehit count
		saveSearchInNode_DefaultSecurityGroup(saveName2);// Save in Default Security Group
		getNewSearchButton().waitAndClick(10);// Click New Search Button

//	Content and Metadata + Work Product
		advancedSearchContentMetadata(contentMetadataString);// CM Search
		advancedSearchWorkProduct(tabName);// Work Product Search
		String saveName3 = "search_" + Utility.dynamicNameAppender();
		pureHitCount.put(saveName3, saveAndReturnPureHitCount());// Get Purehit count
		saveSearchInNode_DefaultSecurityGroup(saveName3);// Save in Default Security Group
		getNewSearchButton().waitAndClick(10);// Click New Search Button

//	Conceptual + Audio
		advancedSearchConceptual(conceptualString);// Conceptual Search
		advancedSearchAudio(audioText, language);// Audio Search
		String saveName4 = "search_" + Utility.dynamicNameAppender();
		pureHitCount.put(saveName4, saveAndReturnPureHitCount());// Get Purehit count
		saveSearchInNode_DefaultSecurityGroup(saveName4);// Save in Default Security Group
		getNewSearchButton().waitAndClick(10);// Click New Search Button

//	Conceptual + Work Product
		advancedSearchConceptual(conceptualString);// Conceptual Search
		advancedSearchWorkProduct(tabName);// Work Product Search
		String saveName5 = "search_" + Utility.dynamicNameAppender();
		pureHitCount.put(saveName5, saveAndReturnPureHitCount());// Get Purehit count
		saveSearchInNode_DefaultSecurityGroup(saveName5);// Save in Default Security Group
		getNewSearchButton().waitAndClick(10);// Click New Search Button

//	Audio + Work Product
		advancedSearchAudio(audioText, language);// Audio Search
		advancedSearchWorkProduct(tabName);// Work Product Search
		String saveName6 = "search_" + Utility.dynamicNameAppender();
		pureHitCount.put(saveName6, saveAndReturnPureHitCount());// Get Purehit count
		saveSearchInNode_DefaultSecurityGroup(saveName6);// Save in Default Security Group
		getNewSearchButton().waitAndClick(10);// Click New Search Button

//	Content and Metadata + Conceptual + Audio + Work Product
		advancedSearchContentMetadata(contentMetadataString);// CM Search
		advancedSearchConceptual(conceptualString);// Conceptual Search
		advancedSearchAudio(audioText, language);// Audio Search
		advancedSearchWorkProduct(tabName);// Work Product Search
		String saveName7 = "search_" + Utility.dynamicNameAppender();
		pureHitCount.put(saveName7, saveAndReturnPureHitCount());// Get Purehit count
		saveSearchInNode_DefaultSecurityGroup(saveName7);// Save in Default Security Group

		return pureHitCount;
	}

	public void saveSearchForNNumberOfNodes(int size, List<String> nodeList) {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		List<String> savedNameList = new ArrayList<>();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterSearchString().Visible();
			}
		}), Input.wait30);
		getEnterSearchString().SendKeys("command");
		for (int i = 0; i < size; i++) {
			getSearchButton().waitAndClick(10);

			// handle pop confirmation for regex and proximity queries
			try {
				getYesQueryAlert().waitAndClick(8);
			} catch (Exception e) {
			}

			// verify counts for all the tiles
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
				}
			}), Input.wait120);

			getSaveSearch_Button().waitAndClick(10);

			try {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSaveAsNewSearchRadioButton().Visible() && getSaveAsNewSearchRadioButton().Enabled();
					}
				}), Input.wait30);
				getSaveAsNewSearchRadioButton().waitAndClick(5);
			} catch (Exception e) {
				System.out.println("Radio button already selected");
				UtilityLog.info("Radio button already selected");
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSavedSearch_MySearchesTab().Visible() && getSavedSearch_MySearchesTab().Enabled();
				}
			}), Input.wait30);
			getSavedSearch_MySearchesTab().Click();

			try {
				getSavedSearch_MySearchesClosedArrow().waitAndClick(10);
			} catch (Exception e) {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSavedSearch_MySearchesOpenArrow().Visible() && getSaveSearch_Name().Enabled();
					}
				}), Input.wait30);
			}
			while (true)
				try {
					getSavedSearch_MySearchesLastNode(nodeList.get(i)).waitAndClick(10);
					getSavedSearch_MySearchesNewNodeClosed().waitAndClick(10);
				} catch (Exception e) {
					break;
				}
		}

		String searchName = "search_" + Utility.dynamicNameAppender();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveSearch_Name().Visible() && getSaveSearch_Name().Enabled();
			}
		}), Input.wait30);
		getSaveSearch_Name().SendKeys(searchName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveSearch_SaveButton().Visible() && getSaveSearch_SaveButton().Enabled();
			}
		}), Input.wait30);
		getSaveSearch_SaveButton().Click();

		base.VerifySuccessMessage("Saved search saved successfully");

		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Jeevitha
	 * @param searchName
	 * @throws InterruptedException
	 */
	public void saveSearchInNode_DefaultSecurityGroup(String searchName) throws InterruptedException {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		saveSearchAction();

		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}
		getSavedSearch_DefaultSecurityGroupTabClosed().waitAndClick(10);
		driver.waitForPageToBeReady();

		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Jeevitha Description : search the query of combination Audio and work
	 *         Product
	 * @param WPName
	 * @param WpSearch
	 * @param operator
	 * @param AudioString
	 * @return PureHit count
	 * @throws InterruptedException
	 */
	public int combinationOfAudioAndWP(String WPName, String WpSearch, String operator, String AudioString)
			throws InterruptedException {

		try {
			base.waitForElement(getAdvancedSearchLink());
			getAdvancedSearchLink().Click();
		} catch (Exception e) {
			System.out.println("ALready In Advance Search PAge");
		}

		// Work product search
		base.waitForElement(getWorkproductBtn());
		getWorkproductBtn().Click();

		// condition
		if (WpSearch.equalsIgnoreCase("tag")) {
			selectTagInASwp(WPName);
		}
		if (WpSearch.equalsIgnoreCase("folder")) {
			selectFolderInASwp(WPName);
		}
		if (WpSearch.equalsIgnoreCase("redactions")) {
			selectRedactioninWPS(WPName);
		}

		// Audio search
		base.waitForElement(getAs_Audio());
		getAs_Audio().waitAndClick(10);

		// condition
		base.waitForElement(getAs_SelectOperation(1));
		getAs_SelectOperation(1).selectFromDropdown().selectByVisibleText(operator);

		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText("North American English");
		// Enter search string
		driver.scrollingToBottomofAPage();
		base.waitForElement(getAs_AudioTextBox_CombinationSearch());
		getAs_AudioTextBox_CombinationSearch().SendKeys(AudioString);
		driver.scrollPageToTop();
		int ActualPureHit = serarchWP();
		return ActualPureHit;
	}

	/**
	 * @author Steffy date: 07/10/2021 Modified date: NA Description: To BulkAssign
	 *         the Family Members Doc in the to an Assignment
	 */
	public void bulkAssignFamilyMemberDocuments() {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		try {
			getFamilyAddButton().Click();
		} catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait60);

		getBulkAssignAction().waitAndClick(10);

		System.out.println("performing bulk assign");
		UtilityLog.info("performing bulk assign");

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param Value - From Date or To Date
	 * @throws InterruptedException
	 * @description this method will select date from date picker in assignment
	 *              workproduct
	 */
	public void selectDate(String Value) throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		base.waitForElement(date(Value));
		driver.scrollingToElementofAPage(date(Value));
		date(Value).SendKeys(dateFormat.format(date));
		base.stepInfo(dateFormat.format(date) + " is selected as " + Value);
	}

	// Raghuram
	public int getDocCountBtwnTwoSearches(Boolean searchPage, String searchOne, String searchTwo) {
		int hitCount;
		if (searchPage) {
			driver.getWebDriver().get(Input.url + "Search/Searches");
		}
		basicContentSearchWithSaveChanges(Input.searchString5, "No", "First");
		base.hitEnterKey(1);
		selectOperatorInBasicSearch("OR");
		hitCount = basicContentSearchWithSaveChanges(Input.searchString6, "Yes", "Third");
		base.stepInfo("Unique doc PureHit count : " + hitCount);

		return hitCount;
	}

	// Function to perform new bulk folder with given tag name
	/**
	 * @author Raghuram A Modified date:7/10/21 Modified by: Raghuram A -
	 */
	public String BulkActions_Folder_returnCount(String folderName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkNewTab().Visible();
			}
		}), Input.wait60);

		getBulkNewTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterFolderName().Visible();
			}
		}), Input.wait60);
		getEnterFolderName().SendKeys(folderName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderAllRoot().Visible();
			}
		}), Input.wait60);
		getFolderAllRoot().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		for (int i = 0; i < 30; i++) {
			try {
				getContinueButton().Click();
				break;
			} catch (Exception e) {
				base.waitForElement(getContinueButton());
			}
		}
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		base.waitForElement(getFinalizeButton());
		base.waitTillElemetToBeClickable(getFinalizeButton());
		String finalCount = getFinalCount().getText();
		System.out.println(finalCount);
		getFinalizeButton().Click();

		base.VerifySuccessMessage("Records saved successfully");
		base.CloseSuccessMsgpopup();

//					 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//							bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
		System.out.println("Bulk folder is done, folder is : " + folderName);
		UtilityLog.info("Bulk folder is done, folder is : " + folderName);

		return finalCount;
	}

	/**
	 * @author Mohan 11/10/21 NA Modified date: NA Modified by:NA
	 * @param metadataDocId
	 * @description: to search metadataquery
	 * 
	 */
	public void basicSearchWithMetaDataQuery(String metadataDocId) {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getBasicSearch_MetadataBtn());
			driver.waitForPageToBeReady();
			getBasicSearch_MetadataBtn().waitAndClick(10);

			driver.waitForPageToBeReady();
			base.waitForElement(getSelectMetaData());
			getSelectMetaData().selectFromDropdown().selectByValue("DocID");

			driver.waitForPageToBeReady();
			base.waitForElement(getMetaDataSearchText1());
			getMetaDataSearchText1().SendKeys(metadataDocId);

			base.waitForElement(getMetaDataInserQuery());
			getMetaDataInserQuery().waitAndClick(10);

			base.waitForElement(getSearchButton());
			getSearchButton().waitAndClick(10);

		} catch (Exception e) {
			base.failedStep("Query is not found");
		}

	}

	/**
	 * @author Raghuram A Date: 10/11/21 Modified date:N/A Modified by: Description
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public int verifyBulk_releaseCount(String SecGroup) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Search/Searches");
		try {
			getPureHitAddButton().waitAndClick(10);
		} catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getBulkReleaseAction());
		getBulkReleaseAction().waitAndClick(10);
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
	 * @author Jeevitha Date: 10/11/21 Modified date:N/A Modified by: Description
	 */
	public void verifySS() {
		driver.waitForPageToBeReady();
		try {
			softAssert.assertTrue(getSavedAppear().isDisplayed());
			base.passedStep("User Saved Query then SS appears left had side In Session search");
		} catch (Exception e) {
			base.failedStep("User Saved Query then SS not appeared in left had side In Session search");
		}
	}

	/**
	 * @author Raghuram A Date: 10/11/21 Modified date:N/A Modified by: Description
	 */
	public int verifyBulkTag(String TagName) throws InterruptedException {
		driver.waitForPageToBeReady();
		if (getRemovePureHit().isElementAvailable(3)) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		} else if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(10);
		}

		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(30);
		Thread.sleep(2000); // synch with app!
		base.waitForElement(getBulkTagAction());
		getBulkTagAction().waitAndClick(30);
		base.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(60);
		base.waitForElement(getEnterTagName());
		getEnterTagName().SendKeys(TagName);
		base.waitForElement(getTagsAllRoot());
		getTagsAllRoot().Click();
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		base.waitForElement(getContinueButton());
		getContinueButton().waitAndClick(5);
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		int newPurehit = Integer.parseInt(getFinalCount().getText());
		Thread.sleep(5000);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkTagConfirmationButton().Visible();
			}
		}), Input.wait60);
		getBulkTagConfirmationButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk Tag is done, Tag is : " + TagName);
		Reporter.log("Bulk Tag is done, Tag is : " + TagName, true);

		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();
		return newPurehit;
	}

	/**
	 * @author Raghuram Date: 10/11/21 Modified date:N/A Modified by: Description
	 */
	public int verifyBulkFolder(String folderName) throws InterruptedException {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait60);
		getBulkActionButton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderAction().Visible();
			}
		}), Input.wait60);

		getBulkFolderAction().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkNewTab().Visible();
			}
		}), Input.wait60);

		getBulkNewTab().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterFolderName().Visible();
			}
		}), Input.wait60);
		getEnterFolderName().SendKeys(folderName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderAllRoot().Visible();
			}
		}), Input.wait60);
		getFolderAllRoot().Click();

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		base.waitForElement(getContinueButton());
		getContinueButton().waitAndClick(5);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		Thread.sleep(5000);
		int newPurehit = Integer.parseInt(getFinalCount().getText());
		getFinalizeButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk folder is done, folder is : " + folderName);
		Reporter.log("Bulk folder is done, folder is : " + folderName, true);
		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();
		return newPurehit;
	}

	/**
	 * @author Jeevitha Date: 10/11/21 Modified date:N/A Modified by: Description
	 */
	public void save_node(String searchName1) {
		try {

			getAdvanceS_SaveSearch_Button().waitAndClick(5);
		} catch (Exception e) {
			GetAdvSaveBtn_New().waitAndClick(5);
		}

		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}
		getSavedSearch_MySearchesTabClosed().waitAndClick(5);
		base.waitForElement(getSavedSearch_MySearchesNewNode());
		getSavedSearch_MySearchesNewNode().waitAndClick(5);

		getSaveSearch_Name().SendKeys(searchName1);
		getSaveSearch_SaveButton().waitAndClick(5);
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName1 + "'", true);
		UtilityLog.info("Saved search with name - " + searchName1);

	}

	/**
	 * @author Raghuram Date: 10/13/21 Modified date:N/A Modified by: Description
	 */
	public int selectAssignmentInWPSWs(String assignMentName) throws InterruptedException {

		base.waitForElement(getWP_assignmentsBtn());
		getWP_assignmentsBtn().Click();
		base.waitForElementCollection(getTree());
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(assignMentName)) {

				base.waitTime(4);
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				System.out.println(iterable_element.getText());
				UtilityLog.info(iterable_element.getText());
				iterable_element.click();
				System.out.println("Assignment selected");

			}
		}
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);

		int pureHit = saveAndReturnPureHitCount();

		return pureHit;
	}

	/**
	 * @author Gopianth
	 * @param productionName
	 * @throws InterruptedException
	 * @description-This method selects the production under Work product-
	 *                   productions search tree.
	 */
	public void selectProduction(String productionName) throws InterruptedException {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getProductionBtn().Visible() && getProductionBtn().Enabled();
				}
			}), Input.wait30);
			getProductionBtn().Click();
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTree_productions().Visible();
				}
			}), Input.wait30);
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			base.waitForElement(getProcutionCheckBox(productionName));
			getProcutionCheckBox(productionName).Click();
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMetaDataInserQuery().Visible();
				}
			}), Input.wait30);
			getMetaDataInserQuery().Click();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getQuerySearchBtn());
			base.waitTillElemetToBeClickable(getQuerySearchBtn());
			getQuerySearchBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while selecting the production under Work product- productions search tree"
							+ e.getMessage());
		}

	}

	/**
	 * @author Gopianth
	 * @description-This method verify pure hit count with production count.
	 * @param productionCount : productionCount is integer value that production
	 *                        count got from production.
	 */
	public void verifyPureHitsCountWithProductionCount(int productionCount) {
		try {
			if (getYesQueryAlert().isDisplayed()) {
				getYesQueryAlert().waitAndClick(8);
			} else {
				driver.waitForPageToBeReady();
			}

			// verify counts for all the tiles
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
				}
			}), Input.wait120);

			int pureHit = Integer.parseInt(getPureHitsCount().getText());
			if (pureHit == productionCount) {
				base.passedStep("Pure hit count is equal production count");
			} else {
				base.failedStep("Pure hit count is not equal production count");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying pure hit count with production count" + e.getMessage());
		}

	}

	/**
	 * Modified on 03/09/2022
	 * 
	 * @author Gopinath
	 * @description-This method verify pure hit count with many production count.
	 * @param productionCount : productionCount is integer value that production
	 *                        count got from production.
	 */
	public void verifyPureHitsCountWithManyProductionCount(int productionCount) {
		if (getYesQueryAlert().isDisplayed()) {
			getYesQueryAlert().waitAndClick(8);
		} else {
			driver.waitForPageToBeReady();
		}

		List<WebElement> elemnts = getDocCount().FindWebElements();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return elemnts.get(elemnts.size() - 1).getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);

		int pureHit = Integer.parseInt(elemnts.get(elemnts.size() - 1).getText());
		if (pureHit == productionCount) {
			base.passedStep("Pure hit count is equal production count");
		} else {
			base.failedStep("Pure hit count is not equal production count");
		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to perform new search by new work product.
	 */
	public void performNewSearchByNewWorkProduct(String productionName) {
		try {
			driver.getWebDriver().get(Input.url + "Search/Searches");
			driver.waitForPageToBeReady();
			driver.Navigate().refresh();
			base.waitForElement(getNewSearch());
			base.waitTillElemetToBeClickable(getNewSearch());
			getNewSearch().Click();
			driver.waitForPageToBeReady();
			List<WebElement> elements = getWorkproductBttn().FindWebElements();
			driver.waitForPageToBeReady();
			elements.get(elements.size() - 1).click();
			List<WebElement> elemnts = getProductionBttn().FindWebElements();
			driver.waitForPageToBeReady();
			if (elemnts.size() >= 2) {
				driver.waitForPageToBeReady();
				elemnts.get(elemnts.size() - 1).click();
			} else {
				driver.waitForPageToBeReady();
				elemnts.get(0).click();
			}
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTree_productions().Visible();
				}
			}), Input.wait30);
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			base.waitForElement(getProcutionCheckBox(productionName));
			getProcutionCheckBox(productionName).Click();
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMetaDataInserQuery().Visible();
				}
			}), Input.wait30);
			getMetaDataInserQuery().Click();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getQuerySearchBtn());
			base.waitTillElemetToBeClickable(getQuerySearchBtn());
			getQuerySearchBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while perform new search by new work product" + e.getMessage());
		}
	}

	/**
	 * @author Jeevitha Description: search the query and return pureHit Count Of
	 *         Basic search
	 * @return pureHit count in integer type
	 * @Stabilization changes done
	 */
	public int searchAndReturnPureHit_BS() {

		// Perform Search
		SearchBtnAction();

		try {
			if (getQueryAlertGetText().isElementAvailable(8)) {
				getQueryAlertGetText().waitAndClick(5);
			}
		} catch (Exception e) {

		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit;
		try {
			pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
		} catch (NumberFormatException e) {
			pureHit = Integer.parseInt(getPureHitsCount().getText());
		}
		System.out.println("PureHit COunt Is : " + pureHit);
		base.stepInfo("PureHit COunt Is : " + pureHit);

		return pureHit;

	}

	/**
	 * @author Jeevitha Description : Save search with Overwritten Search
	 * @modifiedBy : Raghuram @modifiedDate : 03/04/21
	 * @param groupName
	 * @param searchName
	 * @param select
	 * @Stabilization CHanges immplemented Modifcations : additional parameters
	 *                String notificationMsg, String additional1, Boolean check
	 */
	public void saveAsOverwrittenSearch(String groupName, String searchName, String select, String notificationMsg,
			String additional1, Boolean check) {
		driver.waitForPageToBeReady();
		saveSearchAction();
		try {
			base.waitForElement(getsavesearch_overwrite());
			getsavesearch_overwrite().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}

//		if (select.equalsIgnoreCase("First") && getOverWrittenAllTabDD().isElementAvailable(3)) {
//			getOverWrittenAllTabDD().waitAndClick(5);
//		} else if (select.equalsIgnoreCase("Next")) {
//			System.out.println("ALl TAb dropdown Already CLicked");
//			base.stepInfo("ALl TAb dropdown Already CLicked");
//		} -  backup --------
		if (getSaveSearchPopupFolderName_Expand("All").isElementAvailable(3)) {
			getSaveSearchPopupFolderName_cc("All").waitAndClick(10);
			System.out.println("Expanded");
		} else {
			System.out.println("ALl TAb dropdown Already CLicked");
			base.stepInfo("ALl TAb dropdown Already CLicked");
		}

		if (getSaveSearchPopupFolderName_Expand(groupName).isElementAvailable(3)) {
			getSaveSearchPopupFolderName_cc(groupName).waitAndClick(10);
			System.out.println("Expanded");
		} else {
			System.out.println(groupName + " Already Expanded");
		}

		if (!getSavedSearchChecked(searchName).isElementAvailable(3)) {
			getChooseSearchToOverwrite(searchName).waitAndClick(10);
		} else {
//			getSaveSearchPopupFolderName_cc(groupName).waitAndClick(10);
//			base.waitForElement(getChooseSearchToOverwrite(searchName));
//			getChooseSearchToOverwrite(searchName).waitAndClick(10); - for backup - to be removed after impacts verified
			System.out.println(searchName + " already checked");
		}

		getSaveSearch_SaveButton_cc().waitAndClick(10);
		driver.waitForPageToBeReady();

		if (notificationMsg.equals("ExecutionErrorInProgress")) {
			base.VerifyErrorMessage(Input.errMsgSinceExecutionInProgress);
			base.stepInfo("User not able to save a session search onto an existing saved search that is progress.");

		} else if (notificationMsg.equals("Success")) {
			base.VerifySuccessMessage("Saved search saved successfully");
			System.out.println("Saved the OverWritten search with name '" + searchName + "");
			base.stepInfo("Saved  overwritten search with name - " + searchName);
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description-This method will perform bulk assign for the existing assignment
	 * @param assignmentName
	 * 
	 */
	public void bulkAssignExistingForCopyAssignment(String assignmentName) {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);

		getBulkActionButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait30);
		getBulkAssignAction().Click();
		UtilityLog.info("performing bulk assign");
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectCopyAssignmentExisting(assignmentName).Visible();
			}
		}), Input.wait60);
		driver.waitForPageToBeReady();
		getSelectCopyAssignmentExisting(assignmentName).Click();
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk assign is done, assignment is : " + assignmentName);
		Reporter.log("Bulk assign is done, assignment is : " + assignmentName, true);
	}

	/**
	 * @author Indium-Mohan date: 21/10/2021 Modified date:NA Modified By:NA
	 *         Description:Darg the Conceptual Similar purehit and view in docview
	 */

	public void ViewConceptualDocsInDocViews() throws InterruptedException {
		driver.waitForPageToBeReady();
		getConceptuallyPlayButton().ScrollTo();
		base.waitForElement(getConceptuallyPlayButton());
		getConceptuallyPlayButton().waitAndClick(10);

		base.VerifySuccessMessage("Conceptual Similar search started successfully.");
		Thread.sleep(2000);// required
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
//		getConceptPureHitsCount().ScrollTo();
		base.waitForElement(getConceptPureHitsCount());
		Thread.sleep(2000);// required
		getConceptPureHitsCount().waitAndClick(10);

		Thread.sleep(2000);// required
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000);// required
		getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000);// // required
		if (getView().isDisplayed()) {
			driver.waitForPageToBeReady();
			Actions act = new Actions(driver.getWebDriver());
			act.moveToElement(getView().getWebElement()).build().perform();
		} else {
			System.out.println("View is not found");
		}

		base.waitForElement(getDocViewAction());
		getDocViewAction().waitAndClick(5);

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");
	}

	/**
	 * @author Jeevitha Description : advanceWorkProduct
	 */
	public void advanceWorkProduct(String searchname, String search_name) throws InterruptedException {

		base.waitForElement(getAdvancedSearchLink());
		getAdvancedSearchLink().waitAndClick(5);
		getSelectNewSearchbtn().waitAndClick(5);
		getWorkproductBtnC().waitAndClick(5);
		getSavedSearchResult().waitAndClick(5);

		driver.scrollingToBottomofAPage();
		base.waitForElement(getSelectWorkProductSSResults(searchname));
		getSelectWorkProductSSResults(searchname).waitAndClick(5);
		getInsertInToQueryBtn().waitAndClick(10);

		driver.scrollPageToTop();
		try {
			base.waitForElement(getQuerySearchButtonC());
			getQuerySearchButtonC().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Not Clicked");
		}

		driver.waitForPageToBeReady();
		saveSearchAction();

		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}

		getMySavedSearch().waitAndClick(5);
		base.waitForElement(getSavedSearch_MySearchesNewNode());
		getSavedSearch_MySearchesNewNode().waitAndClick(5);

		getSaveSearch_Name().SendKeys(search_name);
		getSaveSearch_SaveButton().waitAndClick(5);
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + search_name + "'", true);
		UtilityLog.info("Saved search with name - " + search_name);

	}

	/*
	 * @author Jeevitha Description : validates Auto suggest of basic search Page
	 */
	public String validateAutosuggestSearchResult_BS(String expectedDocFileName, String metaDataField, String value)
			throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		getBasicSearch_MetadataBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectMetaData().Visible();
			}
		}), Input.wait30);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);

		getMetaDataSearchText1().SendKeys(value);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAutoSuggestSearchresults().Visible();
			}
		}), Input.wait30);
		if (getAutoSuggestSearchresults().size() > 0) {
			List<WebElement> autosuggestResults = getAutoSuggestSearchresults().FindWebElements();

			for (WebElement Result : autosuggestResults) {

				if (Result.getText().contains(expectedDocFileName)) {
					base.passedStep(
							"sucessfully verified the autosuggest search funtionality for MetaData-" + metaDataField);
					Result.click();
				}
			}
			String textValue = getMetaDataSearchText1().GetAttribute("value");
			UtilityLog.info(metaDataField + " Displayed in Meta data query text Box" + textValue);
			base.stepInfo(metaDataField + " Displayed in Meta data query text Box" + textValue);
			base.passedStep("Sucessfully verified the " + metaDataField + " display in MetaData  Text Box ");

			getMetaDataInserQuery().Click();

			// Click on Search button
			getSearchButton().waitAndClick(10);

			// verify counts for all the tiles
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
				}
			}), Input.wait90);

			int pureHit = Integer.parseInt(getPureHitsCount().getText());
			System.out.println(
					"Search is done for " + metaDataField + " with value " + textValue + " purehit is : " + pureHit);
			base.stepInfo(
					"Search is done for " + metaDataField + " with value " + textValue + " purehit is : " + pureHit);

		}
		return value;
	}

	/**
	 * @author jeevitha
	 * @param expectedDocFileName
	 * @param metaDataField
	 * @param value
	 * @param expFileDisplay
	 * @param expFileDisplayInQuerySelection
	 * @param search
	 * @throws InterruptedException
	 */
	public void validateAutosuggestSearchResult_AS(String expectedDocFileName, String metaDataField, String value,
			String expFileDisplay, String expFileDisplayInQuerySelection, boolean search) throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();

		try {
			getAdvancedSearchLink().waitAndClick(10);
		} catch (Exception e) {
			System.out.println("ALready In advance search page");
		}
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvanceSearch_MetadataBtn().Visible();
			}
		}), Input.wait30);
		getAdvanceSearch_MetadataBtn().Click();
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return SelectFromDropDown(metaDataField).Visible() && SelectFromDropDown(metaDataField).isDisplayed();
			}
		}), Input.wait30);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(3);

		getMetaDataSearchText1().SendKeys(value);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAutoSuggestSearchresults().Visible();
			}
		}), Input.wait30);
		if (getAutoSuggestSearchresults().size() > 0) {
			List<WebElement> autosuggestResults = getAutoSuggestSearchresults().FindWebElements();

			for (WebElement Result : autosuggestResults) {

				if (Result.getText().contains(expectedDocFileName)) {
					base.passedStep(
							"sucessfully verified the autosuggest search funtionality for MetaData-" + metaDataField);
					Result.click();
				}
			}
			String textValue = getMetaDataSearchText1().GetAttribute("value");
			System.out.println(metaDataField + " Displayed in Meta data query text Box" + textValue);
			base.stepInfo(metaDataField + " Displayed in Meta data query text Box" + textValue);
			softAssertion.assertTrue(textValue.contains(expFileDisplayInQuerySelection));
			base.passedStep("Sucessfully verified the " + textValue + " display in MetaData  Text Box ");

			getMetaDataInserQuery().Click();
			base.passedStep("Sucessfully verified the " + metaDataField + "With value" + textValue
					+ " display in Search Query Text Box ");
			if (search) {
				getQuerySearchButton().waitAndClick(20);
			}
		} else {
			base.failedStep("Auto suggestion list is not displayed");
		}
	}

	/**
	 * @Author Iyappan
	 * @Description: To select the export data
	 */
	public void exportData() {
		driver.waitForPageToBeReady();
		try {
			getPureHitAddButton().Click();
		} catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.waitTillElemetToBeClickable(getBulkActionButton());
		getBulkActionButton().Click();
		base.waitTillElemetToBeClickable(getas_Action_Exportdata());
		getas_Action_Exportdata().Click();
	}

	/**
	 * @Author Iyappan
	 * @Description: To close the export data popup
	 */
	public void closeExportDataPopup() {
		try {
			base.waitTillElemetToBeClickable(getExportPopupClose());
			getExportPopupClose().ScrollTo();
			getExportPopupClose().Click();
			base.passedStep("Export data close popup is enabled and closed");
		} catch (Exception e) {
			base.failedStep("Failed to close the popup" + e);
		}
	}

	/**
	 * @Author Iyappan
	 * @Description: To Create Metadata basic search
	 * @param metaDataField
	 * @param val1--metadata value
	 * @return
	 */
	public int MetaDataSearchInBasicSearch(String metaDataField, String val1) {
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		// Enter search string
		base.waitForElement(getBasicSearch_MetadataBtn());
		getBasicSearch_MetadataBtn().waitAndClick(3);
		base.waitForElement(getSelectMetaData());
		// getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(3);
		// Click on Search button
		base.waitForElement(getSearchButton());
		getSearchButton().waitAndClick(3);
		base.waitForElement(getPureHitsCount());
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		getPureHitsCount().waitAndClick(15);
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		base.stepInfo("Search is done for " + metaDataField + " with value " + val1 + " purehit is : " + pureHit);
		return pureHit;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	public void VerifyaudioSearch_DisallowedSpecialCharachters(String SearchString) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		getAdvancedSearchLink().waitAndClick(20);
		base.waitForElement(getAs_Audio());
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);
		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText("North American English");
		base.stepInfo("Selected Language as North American English");
		driver.waitForPageToBeReady();
		Actions action = new Actions(driver.getWebDriver());
		action.clickAndHold(GetSliderConfidenceThreshold().getWebElement());
		action.moveToElement(GetSliderConfidenceThreshold().getWebElement(), 115, 0);
		action.release();
		action.build().perform();
		GetSliderConfidenceThreshold().Click();
		// Enter search string
		base.waitForElement(getAs_AudioText());
		getAs_AudioText().SendKeys(SearchString);
		base.stepInfo(
				"Entered a text with Black Listed Special charachters in Audion search text box--" + SearchString);
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();

		// verify WarningMessage for Blacklisted special charchters Disallowed.
		if (getWarningAudioBlackListChar_Header().isDisplayed()) {
			base.stepInfo(
					" pre-defined list of special characters is disallowed when we entered in audio search And Alert is Present");
			softAssert.assertEquals(getWarningAudioBlackListChar_Header().getText(), "Possible Wrong Query Alert");
			softAssert.assertEquals(getWarningAudioBlackListChar_Message().getText(),
					"Only alphanumeric characters and hyphens (-) can be specified in an audio search term");
			base.passedStep(
					"Sucesfully verified the alert Message after entering Black Listed Special charachteres in Audio search and it is displayed as expected.");
			getTallyContinue().waitAndClick(20);
		} else {
			base.failedStep("Warning Message Alert is not present as Expected");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param SearchString,language,ThresholdValue
	 */
	public String modifyAudioSearch(String SearchString, String language, String ThresholdValue)
			throws InterruptedException {
		base.waitForElement(getModifyASearch());
		getModifyASearch().waitAndClick(5);
		if (SearchString != null) {
			// Enter search string
			base.waitForElement(getAudioModifiableQuery());
			getAudioModifiableQuery().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getModifiableSavedSearchQueryDeleteBtnAS());
			getModifiableSavedSearchQueryDeleteBtnAS().ScrollTo();
			getModifiableSavedSearchQueryDeleteBtnAS().waitAndClick(5);
			base.stepInfo("Existing query  is cleared");
			// getAudioQueryTextBoxNew().waitAndClick(30);
			base.waitTillElemetToBeClickable(getAudioQueryTextField());
			getAudioQueryTextField().SendKeys(SearchString);
			driver.waitForPageToBeReady();
			base.waitTillElemetToBeClickable(getAs_AudioLanguage());
			getAs_AudioLanguage().Click();
			base.waitTillElemetToBeClickable(getAudioDropDownselectedValue(language));
			getAudioDropDownselectedValue(language).Click();
			base.stepInfo("Selected Language as " + language);
			base.stepInfo("Entered a text in search text box" + SearchString);
		}
		if (ThresholdValue != null) {
			driver.waitForPageToBeReady();
			setThresholdAudioSearch(ThresholdValue);
		}
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount_ModifySearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		driver.getPageSource();
		base.waitForElement(GetConfidenceThresholdInSecondSearchResult());
		String ActualModifiedThresholdValue = GetConfidenceThresholdInSecondSearchResult().GetAttribute("textContent");
		base.stepInfo("Threshold value after modify search is" + ActualModifiedThresholdValue);
		return ActualModifiedThresholdValue;
	}

	/*
	 * @author Jeevitha
	 */
	public void bulkReleaseNearDupeAndDoc(String SecGroup) throws InterruptedException {
		// driver.getWebDriver().get(Input.url+"Search/Searches");
		driver.waitForPageToBeReady();
		try {
			getNearDupePureHitsCount().Click();
		} catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		try {
			getPureHitAddButton().waitAndClick(10);
		} catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(10);

		driver.waitForPageToBeReady();

		try {
			getBulkReleaseAction().waitAndClick(10);
		} catch (Exception e) {

			getBulkReleaseActionDL().waitAndClick(10);
		}

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible();
			}
		}), Input.wait60);
		getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelease_ButtonRelease().Visible();
			}
		}), Input.wait60);
		getBulkRelease_ButtonRelease().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalizeButton().Visible();
			}
		}), Input.wait60);
		getFinalizeButton().waitAndClick(20);

		base.VerifySuccessMessageB("Records saved successfully");

		System.out.println("performing bulk release");
		base.stepInfo("performing bulk release");

	}

	/**
	 * @author Jeevitha description : Save Search In One Sub Node
	 * @throws InterruptedException
	 * @modified By Jeevitha[17/12/2021]
	 */
	public void saveSearchInRootNode(String searchName, String nodeName, String childNode) throws InterruptedException {

		saveSearchAction();
		// handle pop confirmation for regex and proximity queries

		if (getYesQueryAlert().isElementAvailable(5)) {
			getYesQueryAlert().waitAndClick(8);
		} else {

		}

		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}
		if (getSavedSearch_MySearchesTabClosed().isElementAvailable(7)) {
			getSavedSearch_MySearchesTabClosed().waitAndClick(5);
		} else {
			System.out.println("Already Expanded");
		}

		getCreatedNode(nodeName).waitAndClick(10);
		base.hitDownKey(1);
		base.waitForElement(getExpandCurrentNode());
		getExpandCurrentNode().waitAndClick(10);
		base.waitForElement(getCreatedNode(childNode));
		getCreatedNode(childNode).waitAndClick(10);

		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: Get saved search id from workproduct
	 */
	public String getSavedSearchId(final String SaveName) {
		String id = null;
		base.waitForElement(getSavedSearchBtn());
		getSavedSearchBtn().Click();
		base.waitForElement(getSavedSearchName(SaveName));
		driver.scrollingToBottomofAPage();
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(SaveName)) {
				String saveSearchName = iterable_element.getText();
				System.out.println(saveSearchName);
				String[] searchName = saveSearchName.split(" ");
				String SearchId = searchName[1];
				String searchid = SearchId.replace("(", "");
				id = searchid.replace(")", "");
			}
		}
		return id;
	}

	// Added by krishna

	/**
	 * @author Jeevitha
	 */
	public void saveSearchAtAnyRootGroup1(String searchName, String groupName) throws InterruptedException {
		driver.waitForPageToBeReady();
		saveSearchAction();// Save Search

		try {
			base.waitForElement(getSaveAsNewSearchRadioButton());
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}
		try {
			softAssert.assertTrue(getSaveSearchPopupFolderName(groupName).isDisplayed());
			base.stepInfo(groupName + " : is present");
			getSaveSearchPopupFolderName(groupName).waitAndClick(10);
		} catch (Exception e) {
			System.out.println(groupName + " : SG not available");
			base.stepInfo(groupName + " : SG not available");
		}

		base.waitForElement(getSaveSearch_Name());
		getSaveSearch_Name().SendKeys(searchName);

		base.waitForElement(getSaveSearch_SaveButton());
		getSaveSearch_SaveButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Mohan date: 09/11/2021 Modified date: NA Description: To BulkAssign
	 *         the Conceptual Doc in the to an Assignment
	 */
	public void bulkAssignConceptualDocuments() {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptuallyPlayButton().isDisplayed();
			}
		}), Input.wait60);

		getConceptuallyPlayButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptPureHitsCount().isDisplayed();
			}
		}), Input.wait120);
		if (getConceptPureHitsCount().isElementAvailable(5)) {
			getConceptPureHitsCount().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait60);

		getBulkAssignAction().waitAndClick(10);

		System.out.println("performing bulk assign");
		UtilityLog.info("performing bulk assign");

	}

	/**
	 * @author Indium-Mohan date: 21/10/2021 Modified date:NA Modified By:NA
	 *         Description:Darg the Conceptual Similar purehit and view in docview
	 */

	public void ViewFamilyMemberDocsInDocViews() throws InterruptedException {

		driver.waitForPageToBeReady();
//		getFamilyMemberPureHitsCount().ScrollTo();
		base.waitForElement(getFamilyMemberPureHitsCount());
		getFamilyMemberPureHitsCount().waitAndClick(10);

		driver.scrollPageToTop();
		base.waitForElement(getBulkActionButton());
		Thread.sleep(2000);// required
		getBulkActionButton().waitAndClick(10);
		Thread.sleep(2000);// // required
		if (getViewBtn().isElementAvailable(2)) {
			driver.waitForPageToBeReady();

			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			Actions actions = new Actions(driver.getWebDriver());
			wait.until(ExpectedConditions.elementToBeClickable(getViewBtn().getWebElement()));
			actions.moveToElement(getViewBtn().getWebElement()).build().perform();

			base.waitForElement(getDocViewFromDropDown());
			getDocViewFromDropDown().waitAndClick(10);
		} else {
			getDocViewAction().waitAndClick(10);
			base.waitTime(3); // added for stabilization
		}

		/*
		 * base.waitForElement(getFMHitsCount()); // verify counts for all the tiles
		 * driver.WaitUntil((new Callable<Boolean>() { public Boolean call() { return
		 * getFMHitsCount().getText().matches("-?\\d+(\\.\\d+)?"); } }), Input.wait120);
		 * 
		 * int pureHit = Integer.parseInt(getFMHitsCount().getText());
		 * System.out.println("Search is done and Family Member PureHit is :" +
		 * pureHit); base.stepInfo("Search is done and Family Member PureHit is :" +
		 * pureHit);
		 */

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");
		base.stepInfo("Navigated to docView to view docs");
	}

	/**
	 * @author Indium-Baskar date: 11/11/2021 Modified date:N/A Modified By:Baskar
	 * @Description:Method used to get family member count in session search to
	 *                     docview page
	 */

	public void viewDocViewFamilyMemberDocuments() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		try {
			getFamilyAddButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait60);
		Thread.sleep(2000); // App synch
		getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch
		if (getViewBtn().isElementAvailable(2)) {
			driver.waitForPageToBeReady();

			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			Actions actions = new Actions(driver.getWebDriver());
			wait.until(ExpectedConditions.elementToBeClickable(getViewBtn().getWebElement()));
			actions.moveToElement(getViewBtn().getWebElement()).build().perform();

			base.waitForElement(getDocViewFromDropDown());
			getDocViewFromDropDown().waitAndClick(10);
		} else {
			getDocViewActionDL().Click();
			getDocViewAction().waitAndClick(10);
			base.waitTime(3); // added for stabilization
		}

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");
	}

	/**
	 * @author Raghuram.A
	 * @param tagName
	 * @return
	 * @throws InterruptedException
	 */
	// Function to perform bulk tag with existing tag
	public String bulkTagExistingWithReturn(String tagName) throws InterruptedException {

		base.waitForElement(getExistingTagSelectionCheckBox(tagName));
		getExistingTagSelectionCheckBox(tagName).waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		base.waitForElement(getContinueButton());
		getContinueButton().waitAndClick(5);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		String finalizeDocCount = getFinalCount().getText();
		base.waitForElement(getFinalizeButton());
		getFinalizeButton().waitAndClick(5);

		base.waitForElement(getBulkTagConfirmationButton());
		getBulkTagConfirmationButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Tag is done, Tag is : " + tagName);
		UtilityLog.info("Bulk Tag is done, Tag is : " + tagName);

		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();

		return finalizeDocCount;
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting doc view at action.
	 */
	public void selectDocviewAtAction() throws InterruptedException {
		try {
			// To make sure we are in basic search page

			base.waitForElement(getBulkActionButton());
			getBulkActionButton().waitAndClick(10);

			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getDocViewAction());
			getDocViewAction().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting Doc view At Action" + e.getMessage());
		}

	}

	/**
	 * @author Raghuram.A Date: 11/23/21 Modified date:N/A Modified by: N/A
	 * @throws InterruptedException
	 */
	public Map<String, Integer> saveAndCountMapping() throws InterruptedException {
		String saveName = "search_" + Utility.dynamicNameAppender();
		pureHitCountMapping.put(saveName, saveAndReturnPureHitCount());// Get Purehit count
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		saveSearchAdvanced_New(saveName, Input.mySavedSearch);
		getNewSearchButton().waitAndClick(10);// Click New Search Button
		return pureHitCountMapping;
	}

	/**
	 * @author Raghuram.A Date: 11/23/21 Modified date:2/5/22 Modified by: Raghuram
	 */
	public Map<String, Integer> advancedSearchWithCombinationsSaveUnderMySearches(String contentMetadataString,
			String conceptualString, String audioText, String language, String tabName, String[] combinations,
			String metaDataType, String metaDataInput, String audioThreshold, Boolean save)
			throws InterruptedException {
		Map<String, Integer> pureHitCount = new HashMap<String, Integer>();
		driver.getWebDriver().get(Input.url + "Search/Searches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		for (int i = 0; i < combinations.length; i++) {
			String flowType = combinations[i];

			base.stepInfo(flowType);
			if (flowType.equalsIgnoreCase("Content and Metadata + Conceptual")) {
				advancedSearchContentMetadata(contentMetadataString);// CM Search
				advancedSearchConceptual(conceptualString);// Conceptual Search
			} else if (flowType.equalsIgnoreCase("Content and Metadata + Audio")) {
				advancedSearchContentMetadata(contentMetadataString);// CM Search
				advancedSearchAudio(audioText, language);// Audio Search
			} else if (flowType.equalsIgnoreCase("Content and Metadata + Work Product")) {
				advancedSearchContentMetadata(contentMetadataString);// CM Search
				advancedSearchWorkProduct(tabName);// Work Product Search
			} else if (flowType.equalsIgnoreCase("Conceptual + Audio")) {
				advancedSearchConceptual(conceptualString);// Conceptual Search
				advancedSearchAudio(audioText, language);// Audio Search
			} else if (flowType.equalsIgnoreCase("Conceptual + Work Product")) {
				advancedSearchConceptual(conceptualString);// Conceptual Search
				advancedSearchWorkProduct(tabName);// Work Product Search
			} else if (flowType.equalsIgnoreCase("Audio + Work Product")) {
				advancedSearchAudio(audioText, language);// Audio Search
				advancedSearchWorkProduct(tabName);// Work Product Search
			} else if (flowType.equalsIgnoreCase("Content and Metadata + Conceptual + Audio + Work Product")) {
				advancedSearchContentMetadata(contentMetadataString);// CM Search
				advancedSearchConceptual(conceptualString);// Conceptual Search
				advancedSearchAudio(audioText, language);// Audio Search
				advancedSearchWorkProduct(tabName);// Work Product Search
			} else if (flowType.equalsIgnoreCase("Metadata + Audio")) {
				advMetaDataSearchQueryInsertTest(metaDataType, metaDataInput);
				advancedSearchAudio(audioText, language);// Audio Search
				driver.waitForPageToBeReady();
				setThresholdAudioSearch(audioThreshold);
			} else if (flowType.equalsIgnoreCase("Audio + Metadata")) {
				advancedSearchAudio(audioText, language);// Audio Search
				driver.waitForPageToBeReady();
				setThresholdAudioSearch(audioThreshold);
				advMetaDataSearchQueryInsertTest(metaDataType, metaDataInput);
			}
			if (save) {
				pureHitCount = saveAndCountMapping();
			}
		}

		return pureHitCount;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param metaDataField
	 * @param val1
	 */
	public void advMetaDataSearchQueryInsertTest(String metaDataField, String val1) {
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().Click();
		base.waitForElement(getAdvanceSearch_MetadataBtn());
		getAdvanceSearch_MetadataBtn().Click();
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating to session search page.
	 */
	public void navigateToSessionSearchPageURL() {
		try {
			driver.getWebDriver().get(Input.url + "Search/Searches");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to session search page is failed" + e.getMessage());
		}
	}

	/**
	 * Date: 12/1/21 Modified date:N/A Modified by: N/A
	 * 
	 * @param newNodeList
	 * @param indexcount
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> addSearchInNodewithChildNode(List<String> newNodeList, int indexcount)
			throws Exception {
		int purehit;
		HashMap<String, String> nodeNewSearchpair = new HashMap<String, String>();
		for (int i = 0; i <= indexcount; i++) {
			String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

			getNewSearch().Click();
			purehit = multipleBasicContentSearch(Input.searchString5);

			driver.waitForPageToBeReady();
//			saveSearchInNodewithChildNode(searchName1, newNodeList.get(i));
			saveSearchInNodewithChildNode(searchName1, newNodeList.get(i), newNodeList);
			System.out.println("Added new Search to " + newNodeList.get(i) + " : " + searchName1);
			base.stepInfo("Added new Search to " + newNodeList.get(i) + " : " + searchName1);
			nodeNewSearchpair.put(newNodeList.get(i), searchName1);
			getNewSearchButton().waitAndClick(5);
		}
		return nodeNewSearchpair;
	}

	/**
	 * @author Gopianth
	 * @Description : this method used for performing advanced search by folder.
	 * @param folderName : folderName is String value that name of folder to perform
	 *                   advanced search.
	 */
	public void advanceSearchByFolder(String folderName) {
		try {
			driver.getWebDriver().get(Input.url + "Search/Searches");
			switchToWorkproduct();
			base.stepInfo("Navigated to advance search and work product is clicked");
			driver.waitForPageToBeReady();
			base.waitForElement(getWP_FolderBtn());
			getWP_FolderBtn().Click();
			selectFolderInTree(folderName);
			driver.waitForPageToBeReady();
			base.waitForElement(getMetaDataInserQuery());
			getMetaDataInserQuery().ScrollTo();
			getMetaDataInserQuery().waitAndClick(15);
			base.passedStep("Folders are selected from tree and inserted into query");
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getQuerySearchButton());
			getQuerySearchButton().waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while performing advanced search by folder." + e.getMessage());
		}
	}

	/**
	 * @author Gopianth
	 * @Description : this method used for clicking on new search.
	 * 
	 */
	public void clickOnNewSearch() {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getNewSearchButton());
			getNewSearchButton().waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while performing advanced search by folder." + e.getMessage());
		}
	}

	public int basicContentSearchForTwoItems(String SearchString, String SearchString2) {
		int pureHit = 0;
		try {
			// To make sure we are in basic search page
			driver.getWebDriver().get(Input.url + "Search/Searches");

			// Enter seatch string
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getEnterSearchString().isDisplayed();
				}
			}), Input.wait60);
			getEnterSearchString().getWebElement().sendKeys(SearchString);
			driver.waitForPageToBeReady();
			getEnterSearchString().getWebElement().sendKeys(Keys.ENTER);
			driver.waitForPageToBeReady();
			getSearchSecondTextField().getWebElement().sendKeys(SearchString2);

			// Click on Search button
			getSearchButton().waitAndClick(10);

			// handle pop confirmation for regex and proximity queries
			try {
				if (getYesQueryAlert().isElementAvailable(8)) {
					getYesQueryAlert().waitAndClick(8);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			// verify counts for all the tiles
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
				}
			}), Input.wait120);

			pureHit = Integer.parseInt(getPureHitsCount().getText());

			// System.out.println("Search is done for "+SearchString+" and PureHit is :
			// "+pureHit);
			UtilityLog.info("Search is done for " + SearchString + " and PureHit is : " + pureHit);
			Reporter.log("Search is done for " + SearchString + " and PureHit is : " + pureHit, true);

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while performing advanced search by folder." + e.getMessage());

		}
		return pureHit;
	}

	/**
	 * @author Raghuram.A Date: 9/21/21 Modified date:N/A Modified by: N/A
	 */
	public void saveSearchAction() {
		driver.waitForPageToBeReady();
		if (getSaveSearch_Btn().isDisplayed()) {
			getSaveSearch_Btn().waitAndClick(10);
			System.out.println("Basic Save");
		} else if (getSaveSearch_Button().isDisplayed()) {
			getSaveSearch_Button().waitAndClick(10);
			System.out.println("Basic Save1");
		} else if (getAdvanceSearch_DraftQuerySave_Button().isDisplayed()) {
			getAdvanceSearch_DraftQuerySave_Button().waitAndClick(10);
			System.out.println("Advance Save");
		} else if (GetAdvSaveBtn_New().isDisplayed()) {
			base.waitForElement(GetAdvSaveBtn_New());
			GetAdvSaveBtn_New().waitAndClick(10);
			System.out.println("Advance Save1");
		}
	}

	public String BulkActions_Folder(String folderName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkNewTab().Visible();
			}
		}), Input.wait60);

		getBulkNewTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterFolderName().Visible();
			}
		}), Input.wait60);
		getEnterFolderName().waitAndClick(15);
		getEnterFolderName().SendKeys(folderName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderAllRoot().Visible();
			}
		}), Input.wait60);
		getFolderAllRoot().Click();
		driver.Manage().window().fullscreen();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		for (int i = 0; i < 30; i++) {
			try {
				getContinueButton().Click();
				break;
			} catch (Exception e) {
				base.waitForElement(getContinueButton());
			}
		}
		driver.Manage().window().maximize();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		String finalCnt = getFinalCount().getText();
		base.waitForElement(getFinalizeButton());
		base.waitTillElemetToBeClickable(getFinalizeButton());

		getFinalizeButton().Click();

		base.VerifySuccessMessage("Records saved successfully");
		base.CloseSuccessMsgpopup();

//			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//					bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
		System.out.println("Bulk folder is done for " + finalCnt + ", folder name is : " + folderName);
		base.stepInfo("Bulk folder is done for " + finalCnt + " docs with folder name  : " + folderName);
		return finalCnt;
	}

	public void advancedNewContentSearch1(String SearchString) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNewSearchButton().Visible() && getNewSearchButton().Enabled();
			}
		}), Input.wait30);
		driver.scrollPageToTop();
		getNewSearchButton().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDataBtn().Visible() && getContentAndMetaDataBtn().Enabled();
			}
		}), Input.wait30);
		getContentAndMetaDataBtn().waitAndClick(5);
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentSearchInputAudio().Visible();
			}
		}), Input.wait30);
		getAdvancedContentSearchInputAudio().SendKeys(SearchString);
		// Click on Search button
		getQuerySearchBtn().Click();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SearchString
	 * @param Operator
	 * @param Searchtext2
	 * @return
	 */
	public String basicContentSearchUsingOperator(String SearchString, String Operator, String Searchtext2) {
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		// Enter search string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterSearchString().Visible();
			}
		}), Input.wait60);
		getEnterSearchString().SendKeys(SearchString + Keys.ENTER + Operator + Keys.ENTER + Searchtext2 + Keys.ENTER);

		// Click on Search button
		getSearchButton().waitAndClick(10);
		// handle pop confirmation for regex and proximity queries
		if (getYesQueryAlert().isElementAvailable(8))
			try {
				getYesQueryAlert().waitAndClick(8);
			} catch (Exception e) {
				// TODO: handle exception
			}
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		getPureHitsCount().waitAndClick(20);
		String pureHit = getPureHitsCount().getText();

		UtilityLog.info("Search is done for " + SearchString + " " + Operator + " " + Searchtext2 + " and PureHit is : "
				+ pureHit);
		Reporter.log("Search is done for " + SearchString + "  " + Operator + "  " + Searchtext2 + " and PureHit is : "
				+ pureHit, true);
		return pureHit;
	}

	/**
	 * @Author jeevitha
	 */
	public void handleWhenAllResultsBtnInUncertainPopup() {
		if (getWhenAllResultsAreReadyPopUp().isElementAvailable(20)) {
			getWhenAllResultsAreReadyPopUp().waitAndClick(3);
			String ID = getWhenAllResultsAreReadyID().getText();
			base.stepInfo("When All Results Tab Clicked And Generated ID is: " + ID);
			getBulkTagConfirmationButton().waitAndClick(3);
		} else {
			base.stepInfo("Page Loaded and PopUp Didnot Appear");
		}
	}

	/**
	 * @Author jeevitha
	 * @return
	 */
	public int returnPurehitCount() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
		System.out.println("Search is done  " + " and PureHit is :" + pureHit);
		base.stepInfo("Search is done " + " and PureHit is : " + pureHit);
		return pureHit;
	}

	/**
	 * @author Raghuram.A date: 12/21/2021 Modified date:N/A
	 * @param tagName
	 * @return
	 * @throws InterruptedException
	 */
	// Function to perform bulk tag with existing tag
	public String bulkFolderExistingWithReturn(String folderName) throws InterruptedException {

		getExistingFolderSelectionCheckBox(folderName).waitAndClick(6);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		base.waitForElement(getContinueButton());
		getContinueButton().waitAndClick(5);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		String finalizeDocCount = getFinalCount().getText();
		base.waitForElement(getFinalizeButton());
		getFinalizeButton().waitAndClick(5);

		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Folder is done, Tag is : " + folderName);
		UtilityLog.info("Bulk Folder is done, Tag is : " + folderName);

		// Since page is freezing after bulk actiononly in automation, lets reload page
		// to avoid it..
		driver.getWebDriver().navigate().refresh();

		return finalizeDocCount;
	}

	/**
	 * @author Raghuram.A @Date : 12/23/21 @modifiedon : N/A @modifiedby : N/A
	 * @param SaveName
	 */
	public void searchSavedSearchResult(String SaveName) {

		base = new BaseClass(driver);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.selectproject();
		switchToWorkproduct();
		// searchSavedSearch(saveSearchName);

		driver.waitForPageToBeReady();
		getSavedSearchBtn().Click();

		if (getSavedSearchNameResult(SaveName).isDisplayed()) {
			System.out.println(getSavedSearchNameResult(SaveName).getText());
		}
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();

		getSavedSearchNameResult(SaveName).getWebElement().click();
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(15);
		// Click on Search button
		driver.scrollPageToTop();

		UtilityLog.info("Selected a saved search " + SaveName + "and inserted into query text box ");
		base.stepInfo("Selected a saved search " + SaveName + "and inserted into query text box ");

	}

	/**
	 * @Author :Brundha
	 * @Description : Selecting Audio document
	 */

	public void getMetaDataSearch() {
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		base.waitForElement(getBasicSearch_MetadataBtn());
		getBasicSearch_MetadataBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().selectFromDropdown().selectByVisibleText("DocFileExtension");
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(".mp3");
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		// Click on Search button
		base.waitForElement(getSearchButton());
		getSearchButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Audio Search is done for DocFileExtension and PureHit is : " + pureHit);
		UtilityLog.info("Audio Search is done for DocFileExtension and PureHit is : " + pureHit);

	}

	/**
	 * @author Gopinath Description : this method insert the Query for draft save.
	 */
	public void basicMetaDataDraftSearch(String metaDataField, String option, String val1, String val2) {

		try {
			driver.getWebDriver().get(Input.url + "Search/Searches");
			getBasicSearch_MetadataBtn().Click();
			base.waitTime(3);
			getSelectMetaData().isElementAvailable(10);
			getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
			if (option == null) {

				getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
			} else if (option.equalsIgnoreCase("IS")) {
				getMetaOption().selectFromDropdown().selectByVisibleText("IS (:)");
				getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
			} else if (option.equalsIgnoreCase("RANGE")) {
				getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
				getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
				getMetaDataSearchText2().SendKeys(val2 + Keys.TAB);

			}
			base.waitForElement(getMetaDataInserQuery());
			getMetaDataInserQuery().Click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Description : this function will enter the search string
	 *         into input text field and will not Seaech the input
	 * @param SearchString : SearchString is string value that search value need to
	 *                     enter in search field.
	 */
	public void basicContentDraftSearch(String SearchString) {
		try {
			// To make sure we are in basic search page
			driver.getWebDriver().get(Input.url + "Search/Searches");
			// Enter seatch string
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getEnterSearchString().Visible();
				}
			}), Input.wait60);
			getEnterSearchString().SendKeys(SearchString);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while enter the search string into input text field and will not Seaech the input"
							+ e.getLocalizedMessage());
		}

	}

	/**
	 * @author Gopinath Description : this function will navigate to Advanced search
	 *         page and enter the search string into input text field the search
	 *         string into input text filed and will not Search the input
	 * @param SearchString : SearchString is string value that search value need to
	 *                     enter in search field.
	 */
	public void advanedContentDraftSearch(String SearchString) {
		try {
			// To make sure we are in basic search page
			driver.getWebDriver().get(Input.url + "Search/Searches");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAdvancedSearchLink().Visible();
				}
			}), Input.wait30);
			getAdvancedSearchLink().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getContentAndMetaDatabtn().Visible();
				}
			}), Input.wait30);
			getContentAndMetaDatabtn().Click();
			// Enter seatch string
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAdvancedContentSearchInput().Visible();
				}
			}), Input.wait30);
			getAdvancedContentSearchInput().SendKeys(SearchString);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while navigate to Advanced search page and enter the search string into input text field "
							+ e.getLocalizedMessage());
		}

	}

	/**
	 * @author Gopinath Description : This function will save the advanced search
	 * @param searchName : searchName is string value that search value need to
	 *                   enter in search field.
	 */
	public void saveSearchadvanced(String searchName) {
		try {
			if (getSaveButton().isElementAvailable(2)) {
				driver.WaitUntil((new Callable<Boolean>() {

					public Boolean call() {
						return getSaveButton().Visible() && getSaveButton().Enabled();
					}
				}), Input.wait30);
				base.waitForElement(getSaveButton());
				getSaveButton().waitAndClick(5);
			} else {
				getAdvanceSearch_DraftQuerySave_Button().Click();
			}
			if (getYesQueryAlert().isElementAvailable(2)) {
				try {
					getYesQueryAlert().waitAndClick(8);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			if (getSaveAsNewSearchRadioButton().isElementAvailable(7)) {
				driver.WaitUntil((new Callable<Boolean>() {

					public Boolean call() {
						return getSaveAsNewSearchRadioButton().Visible() && getSaveAsNewSearchRadioButton().Enabled();
					}
				}), Input.wait30);
				getSaveAsNewSearchRadioButton().waitAndClick(5);
			} else {
				System.out.println("Radio button already selected");
				UtilityLog.info("Radio button already selected");
			}

			driver.WaitUntil((new Callable<Boolean>() {

				public Boolean call() {
					return getSavedSearch_MySearchesTab().Visible() && getSavedSearch_MySearchesTab().Enabled();
				}
			}), Input.wait30);
			getSavedSearch_MySearchesTab().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveSearch_Name().Visible() && getSaveSearch_Name().Enabled();
				}
			}), Input.wait30);
			getSaveSearch_Name().SendKeys(searchName);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveSearch_SaveButton().Visible() && getSaveSearch_SaveButton().Enabled();
				}
			}), Input.wait30);
			getSaveSearch_SaveButton().Click();
			base.VerifySuccessMessage("Saved search saved successfully");
			Reporter.log("Saved the search with name '" + searchName + "'", true);
			UtilityLog.info("Saved search with name - " + searchName);
		} catch (

		Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while will save the advanced search. " + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for advanced meta data for draft.
	 */
	public void advancedMetaDataForDraft(String metaDataField, String option, String val1, String val2) {
		try {
			// To make sure we are in basic search page
			driver.getWebDriver().get(Input.url + "Search/Searches");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAdvancedSearchLink().Visible();
				}
			}), Input.wait30);
			getAdvancedSearchLink().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getContentAndMetaDatabtn().Visible();
				}
			}), Input.wait30);
			getContentAndMetaDatabtn().Click();
			// Enter seatch string
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAdvanceSearch_MetadataBtn().Visible();
				}
			}), Input.wait30);
			getAdvanceSearch_MetadataBtn().Click();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
			if (option == null) {

				getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
			} else if (option.equalsIgnoreCase("IS")) {
				getMetaOption().selectFromDropdown().selectByVisibleText("IS (:)");
				getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
			} else if (option.equalsIgnoreCase("RANGE")) {
				getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
				getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
				getMetaDataSearchText2().SendKeys(val2 + Keys.TAB);

			}
			getMetaDataInserQuery().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while performing advanced meta data draft " + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath Description: Method for get count from tills.
	 * @return countofSearch : countofSearch is hash map that count from search.
	 */
	public Map<String, String> getCountFromTills() {
		Map<String, String> countofSearch = new HashMap<String, String>();
		try {
			String FamilyCount = "";
			String ThreadedCount = "";
			getFMHitsCount().isElementAvailable(20);
			for (int i = 0; i < 10; i++) {
				base.waitTime(2);
				if (FamilyCount.contentEquals("")) {
					FamilyCount = getFMHitsCount().getText();
				} else {
					break;
				}
			}
			FamilyCount = getFMHitsCount().getText();
			getPureHitsCount().isElementAvailable(10);
			String PureitCount = getPureHitsCount().getText();
			getNDHitsCount().isElementAvailable(10);
			String NearDupeCount = getNDHitsCount().getText();
			getTDHitsCount().isElementAvailable(10);
			for (int i = 0; i < 10; i++) {
				base.waitTime(2);
				if (ThreadedCount.contentEquals("")) {
					ThreadedCount = getTDHitsCount().getText();
				} else {
					break;
				}
			}
			ThreadedCount = getTDHitsCount().getText();
			countofSearch.put("NearDupe Count", NearDupeCount);
			countofSearch.put("Pureit Count", PureitCount);
			countofSearch.put("Threaded Count", ThreadedCount);
			countofSearch.put("Family member count", FamilyCount);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while  get count from tills." + e.getMessage());
		}
		return countofSearch;
	}

	/**
	 * @author Gopinath Description: Method for save search.
	 * @param searchName : searchName is String value that search value to check in
	 *                   saved search table.
	 */
	public void saveSearchHandle(String searchName) {
		try {

			if (getSaveSearch_Button().isElementAvailable(7)) {

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSaveSearch_Button().Visible() && getSaveSearch_Button().Enabled();
					}
				}), Input.wait30);
				getSaveSearch_Button().waitAndClick(5);
			} else {
				driver.WaitUntil((new Callable<Boolean>() {

					public Boolean call() {
						return getAdvanceS_SaveSearch_Button().Visible() && getAdvanceS_SaveSearch_Button().Enabled();
					}
				}), Input.wait30);
				getAdvanceS_SaveSearch_Button().waitAndClick(5);
			}
			if (getTallyContinue().isElementAvailable(1)) {
				getTallyContinue().waitAndClick(5);
			}
			if (getSaveAsNewSearchRadioButton().isElementAvailable(1)) {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSaveAsNewSearchRadioButton().Visible() && getSaveAsNewSearchRadioButton().Enabled();
					}
				}), Input.wait30);
				getSaveAsNewSearchRadioButton().waitAndClick(5);
			} else {
				System.out.println("Radio button already selected");
				UtilityLog.info("Radio button already selected");
			}

			driver.WaitUntil((new Callable<Boolean>() {

				public Boolean call() {
					return getSavedSearch_MySearchesTab().Visible() && getSavedSearch_MySearchesTab().Enabled();
				}
			}), Input.wait30);
			getSavedSearch_MySearchesTab().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveSearch_Name().Visible() && getSaveSearch_Name().Enabled();
				}
			}), Input.wait30);
			getSaveSearch_Name().SendKeys(searchName);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveSearch_SaveButton().Visible() && getSaveSearch_SaveButton().Enabled();
				}
			}), Input.wait30);
			getSaveSearch_SaveButton().Click();

			base.VerifySuccessMessage("Saved search saved successfully");

			Reporter.log("Saved the search with name '" + searchName + "'", true);
			UtilityLog.info("Saved search with name - " + searchName);
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public int metadataAndCommentSearch(String projectFieldINT, String metadataText, String addComment,
			String commentText) {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		base.waitForElement(getNewSearch());
		getNewSearch().waitAndClick(5);
		base.waitForElement(getBasicSearch_MetadataBtnSec());
		getBasicSearch_MetadataBtnSec().waitAndClick(10);
		base.waitForElement(getSelectMetaData());
		// getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(10);
		base.waitForElement(SelectFromDropDown(projectFieldINT));
		SelectFromDropDown(projectFieldINT).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(metadataText + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(10);
		base.waitForElement(getCommentsFieldAndRemarksSec());
		getCommentsFieldAndRemarksSec().waitAndClick(10);
		base.waitForElement(SelectFromDropDown(addComment));
		SelectFromDropDown(addComment).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(commentText + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		// Click on Search button
		base.waitForElement(getSearchButtonSec());
		getSearchButtonSec().Click();
		// two handle twosearch strings
		if (getTallyContinue().isElementAvailable(1)) {
			try {
				Thread.sleep(Input.wait30);
				base.waitForElement(getTallyContinue());
				getTallyContinue().waitAndClick(5);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		base.waitForElement(getPureHitsCount());
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsLastCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		int pureHit = Integer.parseInt(getPureHitsLastCount().getText());
		return pureHit;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SearchString
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	public String advancedSearch_CombinedResults(String SearchString, Element element) throws InterruptedException {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		SoftAssert softAssertion = new SoftAssert();
		base.waitForElement(getAdvancedSearchLink());
		getAdvancedSearchLink().Click();
		base.stepInfo("Navigated to advanced search page successfuly");
		try {
			base.waitForElement(element);
			element.waitAndClick(10);
			base.passedStep("Advanced search option is selected");
		} catch (Exception e) {
			base.failedStep("Advanced search option is not selected");
			softAssertion.fail();
		}
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().Click();
		base.waitForElement(getAdvancedContentSearchInput());
		getAdvancedContentSearchInput().SendKeys(SearchString);
		base.waitForElement(getQuerySearchButton());
		// Click on Search button
		getQuerySearchButton().Click();

		// look for warnings, in case of proximity search
		try {
			if (getTallyContinue().isElementAvailable(2)) {
				getTallyContinue().waitAndClick(10);
			}
		} catch (Exception e) {

		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCombinedSearchResults().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		String combinedSearchResults = getCombinedSearchResults().getText();
		softAssertion.assertNotNull(combinedSearchResults, "Search is done and no combined search hits returned");
		base.stepInfo("The combined search results are " + combinedSearchResults);
		return combinedSearchResults;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param folderName
	 * @throws InterruptedException
	 */

	public void bulkFolderWithOutHitADD(String folderName) throws InterruptedException {
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(5);
		base.waitForElement(getBulkFolderAction());
		getBulkFolderAction().waitAndClick(5);
		base.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(20);
		base.waitForElement(getEnterFolderName());
		getEnterFolderName().SendKeys(folderName);
		base.waitForElement(getFolderAllRoot());
		getFolderAllRoot().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk folder is done, folder is : " + folderName);
		Reporter.log("Bulk folder is done, folder is : " + folderName, true);
		driver.getWebDriver().navigate().refresh();
	}

	/**
	 * @Author Gopinath
	 * @Description: To Create Metadata advanced search.
	 */
	public void metaDataSearchInAdvancedSearch(String metaDataField, String val1) {

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.waitForElement(getAdvancedSearchLink());
		getAdvancedSearchLink().waitAndClick(3);
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().waitAndClick(3);
		// Enter search string
		base.waitForElement(getAdvanceSearch_MetadataBtn());
		getAdvanceSearch_MetadataBtn().waitAndClick(3);
		base.waitForElement(getSelectMetaData());
		// getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(3);
		// Click on Search button
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().waitAndClick(3);
		if (getTallyContinue().isElementAvailable(3)) {
			getTallyContinue().waitAndClick(5);
		}
		// two handle twosearch strings

		base.waitForElement(getPureHitsCount());
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		base.stepInfo("Search is done for " + metaDataField + " with value " + val1 + " purehit is : " + pureHit);
	}

	/**
	 * @author Gopinath Description: advanced search with any one of the operator
	 * @param SearchString(Input  search string 1)
	 * @param operator
	 * @param searchString2(Input search string 2)
	 * @return purehit of search
	 */
	public int advancedContentSearchWithOperator(String SearchString, String operator, String searchString2) {
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtn().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtn().Click();
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentSearchInput().Visible();
			}
		}), Input.wait30);
		getAdvancedContentSearchInput().SendKeys(SearchString);

		selectOperator(operator);
		base.waitForElement(getAdvancedContentSearchInputCurrent());
		try {
			getAdvancedContentSearchInputCurrent().SendKeys(searchString2);
		} catch (Exception e) {
			base.waitTime(5);
			getAdvancedContentSearchInputCurrent().SendKeys(searchString2);
		}

		// Click on Search button
		getQuerySearchButton().Click();

		// look for warnings, in case of proximity search
		try {
			if (getTallyContinue().isElementAvailable(2)) {
				getTallyContinue().waitAndClick(10);
			}
			base.waitTime(2);
		} catch (Exception e) {

		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		Reporter.log("Serach is done for '" + SearchString + "' and PureHit is : " + pureHit, true);
		UtilityLog.info("Serach is done for " + SearchString + " and PureHit is : " + pureHit);

		return pureHit;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SearchString
	 * @param language
	 */
	public void verifyaudioSearchWarning(String SearchString, String language) {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		getAdvancedSearchLink().waitAndClick(20);
		base.waitForElement(getAs_Audio());
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);
		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		base.stepInfo("Selected Language as " + language);
		driver.waitForPageToBeReady();
		// Enter search string
		base.waitForElement(getAs_AudioText());
		getAs_AudioText().SendKeys(SearchString);
		base.stepInfo("Entered a text in search text box" + SearchString);
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();

	}

	/**
	 * @author : Raghuram
	 * @param searchName description: Advance Save Saved Search In Node TAb Modified
	 *                   on : 12/9/21 modified by : Raghuram A
	 * @Stabilization : changes implemented
	 */
	public void saveAdvanceSearchInNode(String searchName, String node) throws InterruptedException {
		// Click Save Button
		saveSearchAction();
//		if (getSaveButton().isElementAvailable(2)) {
//			getSaveButton().waitAndClick(5);
//		} else {
//			getAdvanceSearch_DraftQuerySave_Button().Click();
//		}
		// handle pop confirmation for regex and proximity queries
		try {
			if (getYesQueryAlert().isElementAvailable(8)) {
				getYesQueryAlert().waitAndClick(8);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			base.stepInfo("Radio button already selected");
		}
		try {
			if (getSavedSearch_MySearchesTabClosed().isElementAvailable(3)) {
				getSavedSearch_MySearchesTabClosed().waitAndClick(3);
			}
		} catch (Exception e) {
			System.out.println("MY SavedSearch TAb dropdown Already CLicked");
			base.stepInfo("MY SavedSearch TAb dropdown Already CLicked");
		}
		try {
			if (getSavedSearch_MySearchesNewNode1(node).isElementAvailable(3)) {
				getSavedSearch_MySearchesNewNode1(node).waitAndClick(3);
			} else {
				getSavedSearch_MySearchesTab().waitAndClick(10);
			}
		} catch (Exception e) {
			getSavedSearch_MySearchesTab().waitAndClick(10);
		}
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Gopinath modified date-NA
	 * @throws InterruptedException
	 * @description Create bulk assign with new assignment with persistant hit.
	 */
	public void bulkAssignWithNewAssignmentWithPersistantHit(String assignmentName, String codingForm)
			throws InterruptedException {
		AssignmentsPage assignPage = new AssignmentsPage(driver);
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isDisplayed()) {
			base.waitForElement(getPureHitAddButton());
			base.waitTime(2);
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.waitTime(3);
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().Click();
		base.waitForElement(getBulkAssignAction());
		getBulkAssignAction().Click();
		UtilityLog.info("performing bulk assign");
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		base.waitForElement(getAssgn_NewAssignmnet());
		getAssgn_NewAssignmnet().isElementAvailable(15);
		base.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
		getAssgn_NewAssignmnet().waitAndClick(5);
		base.waitForElement(getbulkassgnpopup());
		getbulkassgnpopup().isElementAvailable(10);
		try {
			base.waitForElement(getContinueBulkAssign());
			getContinueBulkAssign().isElementAvailable(15);
			base.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(20);
		} catch (Exception e) {
			getPersistantHitCheckBox().isElementAvailable(15);
			getPersistantHitCheckBox().Click();
			base.waitForElement(getContinueBulkAssign());
			getContinueBulkAssign().isElementAvailable(15);
			base.waitTillElemetToBeClickable(getContinueBulkAssign());
			getContinueBulkAssign().waitAndClick(20);
		}
		base.waitForElement(getAssgn_TotalCount());
		getAssgn_TotalCount().isElementAvailable(10);
		base.waitForElement(getFinalizeButton());
		base.waitTillElemetToBeClickable(getFinalizeButton());
		getFinalizeButton().isElementAvailable(10);
		getFinalizeButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		try {
			base.waitForElement(getAssignmentName());
			getAssignmentName().isElementAvailable(15);
			getAssignmentName().SendKeys(assignmentName);
		} catch (Exception e) {
			getAssignmentName().isElementAvailable(15);
			base.waitForElement(getAssignmentName());
			getAssignmentName().Clear();
			getAssignmentName().SendKeys(assignmentName);
		}
		getParentAssignmentGroupName().isElementAvailable(10);
		getParentAssignmentGroupName().isDisplayed();
		base.waitForElement(getSelectedClassification());
		getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
		assignPage.SelectCodingform(codingForm);
		base.waitForElement(getAssignmentSaveButton());
		base.waitTillElemetToBeClickable(getAssignmentSaveButton());
		getAssignmentSaveButton().waitAndClick(5);
		try {
			if (getAssignmentErrorText().isElementAvailable(5)) {
				driver.waitForPageToBeReady();
				base.waitForElement(getAssignmentName());
				getAssignmentName().SendKeys(assignmentName);
				base.waitForElement(getAssignmentSaveButton());
				getAssignmentSaveButton().waitAndClick(5);
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
		System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
		UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void verifyProximitySearch() {
		String expectedHeaderMsg = "Possible Wrong Query Alert";
		String expectedAlertMSg = "Double quotes are missing in your search query. Please correct the query to include double quotes.";
		base.waitForElement(getQueryAlertGetTextHeader());
		String actualHeadaerMsg = getQueryAlertGetTextHeader().getText().trim();
		String ActualAlertMSg = getQueryAlertGetTextSingleLine().getText().trim();
		System.out.println(actualHeadaerMsg);
		System.out.println(ActualAlertMSg);
		getTallyContinue().Click();
		if (actualHeadaerMsg.contentEquals(expectedHeaderMsg) && ActualAlertMSg.contentEquals(expectedAlertMSg)) {
			base.passedStep("Application  displayed below  warning message on screen");
			base.passedStep(actualHeadaerMsg);
			base.passedStep(ActualAlertMSg);
		} else {
			base.failedStep("Application  not displayed the expected warning message on screen");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void AdvContentSearchWithoutPopHandling(String SearchString) {
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.waitForElement(getAdvancedSearchLink());
		getAdvancedSearchLink().Click();
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().Click();
		// Enter search string
		base.waitForElement(getAdvancedContentSearchInput());
		getAdvancedContentSearchInput().SendKeys(SearchString);
		// Click on Search button
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().waitAndClick(10);
	}

	/**
	 * @author Jayanthi.ganesan Modified by jayanthi-3/2/21
	 */
	public void resubmitSearch() {
		driver.scrollPageToTop();
		base.waitForElement(getModifyASearch_Last());
		getModifyASearch_Last().waitAndClick(10);
		base.waitForElement(getAdvanceSearch_btn_Current());
		getAdvanceSearch_btn_Current().waitAndClick(10);
	}

	public void navigateToAdvancedSearchPage() {
		try {
			driver.getWebDriver().get(Input.url + "Search/Searches");
			driver.waitForPageToBeReady();
			base.waitTime(2);
			base.waitForElement(getAdvancedSearchLink());
			getAdvancedSearchLink().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to advanced search page is failed" + e.getMessage());
		}
	}

	public void selctProductionsAlreadyProduced() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionBtn().Visible() && getProductionBtn().Enabled();
			}
		}), Input.wait30);
		getProductionBtn().Click();
		base.waitForElement(getadwp_assgn_status());
		getadwp_assgn_status().waitAndClick(5);
		base.waitForElement(getDdnAlreadyProducedOption());
		getDdnAlreadyProducedOption().waitAndClick(5);

		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		base.waitForElement(getMetaDataInserQuery());

		getMetaDataInserQuery().Click();
		driver.scrollPageToTop();

	}

	public void workProductSearch(String WpSearch, String WPName, boolean WPBtnClick) throws InterruptedException {
		if (WPBtnClick) {
			base.waitForElement(getWorkproductBtn());
			getWorkproductBtn().Click();
			base.stepInfo("Switched to Advanced search - Work product");
		}
		if (WpSearch.equalsIgnoreCase("tag")) {
			selectTagInASwp(WPName);
		}
		if (WpSearch.equalsIgnoreCase("folder")) {
			selectFolderInASwp(WPName);
		}
		if (WpSearch.equalsIgnoreCase("redactions")) {
			selectRedactioninWPS(WPName);
		}
		if (WpSearch.equalsIgnoreCase("security group")) {
			selectSecurityGinWPS(WPName);
		}
		if (WpSearch.equalsIgnoreCase("productions")) {
			selectProductionstInASwp(WPName);
		}
		if (WpSearch.equalsIgnoreCase("assignments")) {
			selectAssignmentInWPS(WPName);
		}
	}

	/**
	 * @author Mohan date: 27/01/2021 Modified date: NA
	 * @Description: Assign Conceptual document to bulk folder
	 */
	public void bulkFolderConceptualDocs(String folderName) throws InterruptedException {
		// driver.getWebDriver().get(Input.url+"Search/Searches");
		driver.waitForPageToBeReady();
		if (getConceptPureHitsCount().isElementAvailable(5)) {
			getConceptPureHitsCount().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(5);
		base.waitForElement(getBulkFolderAction());
		getBulkFolderAction().waitAndClick(5);
		base.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(20);
		base.waitForElement(getEnterFolderName());
		getEnterFolderName().SendKeys(folderName);
		base.waitForElement(getFolderAllRoot());
		getFolderAllRoot().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();
		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk folder is done, folder is : " + folderName);
		Reporter.log("Bulk folder is done, folder is : " + folderName, true);
		driver.getWebDriver().navigate().refresh();
	}

	/**
	 * @author Mohan date: 27/01/2021 Modified date: NA
	 * @Description: Bulk assign docs
	 */
	public void performBulkAssignDocsAction() {

		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(3);

		base.waitForElement(getBulkAssignAction());
		getBulkAssignAction().waitAndClick(10);

		base.stepInfo("performing bulk assign");
		UtilityLog.info("performing bulk assign");

	}

	/**
	 * @author Raghuram.A Date: 1/26/22 Modified date:1/26/21 Modified by: Raghuram
	 * @param cdName
	 * @param headerName
	 */
	public void verifySearchReult(String cdName, String headerName, String checkType, Boolean resultCheck) {
		HashMap<String, Integer> headerDataPair = new HashMap<>();
		Boolean conditionPass = false;
		String custodianName = "";

		base.stepInfo("Expected custodianName result : " + cdName);

		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		System.out.println(getDetailsTable().size());
		for (int i = 1; i <= getDetailsTable().size(); i++) {
			headerDataPair.put(getDetailsTableToMap(i).getText().toString(), i);
		}

		// Header List
		base.stepInfo("Available table headers");
		for (Entry<String, Integer> entry : headerDataPair.entrySet()) {
			System.out.println(entry.getKey() + " => " + entry.getValue());
			base.stepInfo(entry.getKey());
		}

		// Condition check
		if (checkType.equalsIgnoreCase("Equal")) {
			int totalRows = getTotalRows().size();
			for (int i = 1; i <= totalRows; i++) {
				custodianName = getCellValue(i, headerDataPair.get(headerName)).getText();
				if (custodianName.equalsIgnoreCase(cdName)) {
					conditionPass = true;
				} else {
					conditionPass = false;
					break;
				}
			}
		} else if (checkType.equalsIgnoreCase("contains")) {
			int totalRows = getTotalRows().size();
			for (int i = 1; i <= totalRows; i++) {
				custodianName = getCellValue(i, headerDataPair.get(headerName)).getText();
				if (custodianName.contains(cdName)) {
					base.passedStep(cdName + " - Relevant Custodian details displayed on screen. : " + custodianName);
				} else {
					base.failedStep(cdName + " - Unrelevant Custodian details displayed on screen. : " + custodianName);
				}
			}
		}

		// Result check
		if (resultCheck) {
			if (conditionPass) {
				base.passedStep("Relevant Custodian details displayed on screen. : " + custodianName);
			} else {
				base.failedStep("CustodianName mis-matches : " + custodianName);
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param status
	 * @throws InterruptedException
	 */
	public void selectCompletedAssignmentInWP(String status) throws InterruptedException {
		base.waitForElement(getWP_assignmentsBtn());
		getWP_assignmentsBtn().Click();
		base.waitTime(1);
		selectStatusInAssgnWp(status).waitAndClick(2);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		driver.scrollPageToTop();
	}

	/**
	 * @author Mohan 31/01/22 NA Modified date: NA Modified by:NA
	 * @param metadataDocId
	 * @description: to search metadataquery
	 * 
	 */
	public int basicSearchWithMetaDataQuery(String metadataDocId, String dropDownValue) {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getBasicSearch_MetadataBtn());
			driver.waitForPageToBeReady();
			getBasicSearch_MetadataBtn().waitAndClick(10);

			driver.waitForPageToBeReady();
			base.waitForElement(getSelectMetaData());
			getSelectMetaData().selectFromDropdown().selectByValue(dropDownValue);

			driver.waitForPageToBeReady();
			base.waitForElement(getMetaDataSearchText1());
			getMetaDataSearchText1().SendKeys(metadataDocId);

			base.waitForElement(getMetaDataInserQuery());
			getMetaDataInserQuery().waitAndClick(10);

			base.waitForElement(getSearchButton());
			getSearchButton().waitAndClick(10);

			if (getTallyContinue().isElementAvailable(2)) {
				getTallyContinue().waitAndClick(10);
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPureHitAddButton().Displayed();
				}
			}), Input.wait90);

			int pureHit = Integer.parseInt(getPureHitsCount().getText());

		} catch (Exception e) {
			base.failedStep("Query is not found");
		}
		return pureHit;

	}

	public void ViewInDocListWithOutPureHit() throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		System.out.println("Pure hit block already moved to action panel");
		UtilityLog.info("Pure hit block already moved to action panel");

		getBulkActionButton().waitAndClick(5);
		base.waitTime(2);
		if (getView().isDisplayed()) {
			driver.waitForPageToBeReady();
			Actions act = new Actions(driver.getWebDriver());
			act.moveToElement(getView().getWebElement()).build().perform();
		} else {
			System.out.println("View is not found");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocListAction().Visible();
			}
		}), Input.wait60);

		getDocListAction().waitAndClick(5);
		if (getTallyContinue().isElementAvailable(5)) {
			try {
				getTallyContinue().waitAndClick(5);
			} catch (Exception e) {
			}
		}
		base.stepInfo("Navigated to doclist, to view docslist");
		UtilityLog.info("Navigated to doclist, to view docslist");

	}

	/**
	 * @author Jayanthi
	 * @param remarks
	 * @param val1
	 * @return
	 */
	public int getCommentsOrRemarksCount_AdvancedSearch(String remarks, String val1) {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		base.waitForElement(getAdvancedSearchLink());
		getAdvancedSearchLink().Click();
		base.waitForElement(getContentAndMetaDatabtn());
		getContentAndMetaDatabtn().Click();
		base.waitForElement(getCommentsFieldAndRemarks_AdvacnedSearch());
		getCommentsFieldAndRemarks_AdvacnedSearch().waitAndClick(5);
		base.waitForElement(SelectFromDropDown(remarks));
		SelectFromDropDown(remarks).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		// Click on Search button
		getQuerySearchButton().Click();
		// look for warnings, in case of proximity search
		if (getTallyContinue().isElementAvailable(2)) {
			getTallyContinue().waitAndClick(10);
		}
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsLastCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsLastCount().getText());
		Reporter.log("Serach is done for comments term' " + remarks + "' and PureHit is : " + pureHit, true);
		UtilityLog.info("Serach is done for  comments term " + remarks + " and PureHit is : " + pureHit);

		return pureHit;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param operator1
	 * @param operatorPosition
	 */
	public void selectOperator(String operator1, int operatorPosition) {
		base.waitForElement(getAs_SelectOperation(operatorPosition));
		getAs_SelectOperation(operatorPosition).selectFromDropdown().selectByVisibleText(operator1);

	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void audioSearch_Combined() {
		// Audio search
		base.waitForElement(getAs_Audio());
		getAs_Audio().waitAndClick(10);
		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText("North American English");
		// Enter search string
		driver.scrollingToBottomofAPage();
		base.waitForElement(getAs_AudioTextBox_CombinationSearch());
		getAs_AudioTextBox_CombinationSearch().SendKeys(Input.audioSearchString1);
	}

	/**
	 * @Author :Aathith
	 * @Description :Selecting metadata in search
	 */
	public void SearchMetaData(String Text, String Value) {
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		base.waitForElement(getBasicSearch_MetadataBtn());
		getBasicSearch_MetadataBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().selectFromDropdown().selectByVisibleText(Text);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(Value);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		// Click on Search button
		base.waitForElement(getSearchButton());
		getSearchButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (getTallyContinue().isDisplayed()) {
			base.waitForElement(getTallyContinue());
			getTallyContinue().waitAndClick(5);
		} else {
			driver.waitForPageToBeReady();
		}

	}

	/**
	 * @Author Jeevitha
	 * @param persistant
	 */
	public void bulkAssign_Persistant(boolean persistant) {

		if (getRemovePureHit().isElementAvailable(3)) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		} else if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(10);
		}
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(3);

		base.waitForElement(getBulkAssignAction());
		getBulkAssignAction().waitAndClick(10);

		base.stepInfo("performing bulk assign");
		UtilityLog.info("performing bulk assign");

		if (persistant) {
			driver.waitForPageToBeReady();
			if (getPersistantHitCb_Existing().isElementAvailable(3))
				getPersistantHitCb_Existing().waitAndClick(5);
		} else if (getPersistantHitCheckBox().isElementAvailable(3)) {
			getPersistantHitCheckBox().waitAndClick(5);
		}
	}

	public void advancedContentSearchConfigure(String SearchString) {

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDataBtn().Visible() && getContentAndMetaDataBtn().Enabled();
			}
		}), Input.wait30);
		getContentAndMetaDataBtn().waitAndClick(5);
		// Enter search string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedContentSearchInputAudio().Visible();
			}
		}), Input.wait30);
		getAdvancedContentSearchInputAudio().SendKeys(SearchString);

	}

	public void validateAudioSearchResult(List<String> ExpectedResult, String ExpectedTermOperator,
			String ExpectedLanguagePack, String ExpectedLocationAudioFile, String ExpectedConfidenceThreshold) {

		driver.waitForPageToBeReady();
		List<String> ActualResult = base.availableListofElements(audioSearchTermResult());

		for (int i = 0; i < ExpectedResult.size(); i++) {

			if (ActualResult.contains(ExpectedResult.get(i))) {
				base.passedStep("The Expected Audio Search term '" + ExpectedResult.get(i)
						+ "' is present in the Search Result");
			} else {
				base.failedMessage("The Expected Audio Search term '" + ExpectedResult.get(i)
						+ "' is Not present in the Search Result");
			}
		}
		String actualTermOperator = gettingAudioSearchTableRowValues(
				base.getIndex(getAudioSearchTableHeaders(), "Term Operator".toUpperCase())).getText();
		if (actualTermOperator.equalsIgnoreCase(ExpectedTermOperator)) {
			base.passedStep(
					"Term Operator displayed  in Search Result is " + actualTermOperator + " which is expected .");
		} else {
			base.failedStep("Term Operator in Search Result doesn't matches with the applied Term Operator");
		}
		String actualLanguagePack = gettingAudioSearchTableRowValues(
				base.getIndex(getAudioSearchTableHeaders(), "Language Pack / Dialect".toUpperCase())).getText();

		softAssert.assertEquals(actualLanguagePack, ExpectedLanguagePack);

		String actualLocationAudioFile = gettingAudioSearchTableRowValues(
				base.getIndex(getAudioSearchTableHeaders(), "Location in Audio File".toUpperCase())).getText();

		softAssert.assertEquals(actualLocationAudioFile, ExpectedLocationAudioFile);

		String actualConfidenceThreshold = gettingAudioSearchTableRowValues(
				base.getIndex(getAudioSearchTableHeaders(), "Minimum Confidence Threshold".toUpperCase())).getText();

		softAssert.assertEquals(actualConfidenceThreshold, ExpectedConfidenceThreshold);

		softAssert.assertAll();
		base.passedStep("Language pack in Search Result validated Successfully");
		base.passedStep("Location in audio File in Search Result validated Successfully");
		base.passedStep("Minimum Confidence Threshold in Search Result validated Successfully");

	}

	public String configureAudioSearchBlock(String SearchString1, String SearchString2, String language, int threshold,
			String operator, String location, String seconds) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		getAdvancedSearchLink().Click();
		base.waitForElement(getAs_Audio());
		getAs_Audio().waitAndClick(10);
		base.waitForElement(getAs_AudioLanguage());
		getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
		// Enter search string
		base.waitForElement(getAs_AudioText());
		getAs_AudioText().SendKeys(SearchString1 + Keys.ENTER + SearchString2 + Keys.ENTER);

		// Select term operator
		getAudioTermOperator().selectFromDropdown().selectByVisibleText(operator);
		// Select location
		base.waitForElement(getAudioLocation());
		if (location.equalsIgnoreCase("Entire Audio File")) {
			getAudioLocation().selectFromDropdown().selectByVisibleText(location);
		} else if (location.equalsIgnoreCase("Within:")) {
			getAudioLocation().selectFromDropdown().selectByVisibleText(location);
			getAudioTimeDiff().SendKeys(seconds);
		}
		// select threshold
		Actions action = new Actions(driver.getWebDriver());
		action.clickAndHold(GetSliderConfidenceThreshold().getWebElement());
		action.moveToElement(GetSliderConfidenceThreshold().getWebElement(), threshold, 0);
		action.release();
		action.build().perform();
		GetSliderConfidenceThreshold().Click();
		base.waitTime(2);
		String[] thresholdValue = gettingThresholdValue().getText().split(" ");
		String thresholdValue1 = thresholdValue[0];
		return thresholdValue1;
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void addNewSearch() {
		driver.scrollPageToTop();
		base.waitTillElemetToBeClickable(getSelectNewSearchbtn());
		getSelectNewSearchbtn().waitAndClick(10);
		base.stepInfo("new search window is added");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param SearchString
	 * @param language
	 * @param ThresholdValue
	 * @return
	 * @throws InterruptedException
	 */
	public int newAudioSearchThreshold(String SearchString, String language, String ThresholdValue)
			throws InterruptedException {

		base.waitForElement(getCurrentAudioButton());
		getCurrentAudioButton().ScrollTo();
		getCurrentAudioButton().waitAndClick(10);
		base.waitForElement(getCurrentLanguageSelectButton());
		getCurrentLanguageSelectButton().selectFromDropdown().selectByVisibleText(language);
		base.stepInfo("Selected Language as " + language);
		driver.waitForPageToBeReady();
		Actions action = new Actions(driver.getWebDriver());
		action.clickAndHold(GetCurrentSliderConfidenceThreshold().getWebElement());
		if (ThresholdValue.equalsIgnoreCase("max")) {
			action.moveToElement(GetCurrentSliderConfidenceThreshold().getWebElement(), 145, 0);
		}
		if (ThresholdValue.equalsIgnoreCase("min")) {
			action.moveToElement(GetCurrentSliderConfidenceThreshold().getWebElement(), -145, 0);
		}
		action.release();
		action.build().perform();
		GetCurrentSliderConfidenceThreshold().Click();
		// Enter search string
		base.waitForElement(get_Current_As_AudioText());
		get_Current_As_AudioText().SendKeys(SearchString);
		base.stepInfo("Entered a text in search text box " + SearchString);
		base.waitForElement(getAdvanceSearch_btn_Current());
		getAdvanceSearch_btn_Current().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCurrentPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());

		String ActualThresholdValue = GetConfidenceThresholdInSearchResult().getText();
		System.out.println(
				"Audio Search is done for " + SearchString + " and Theshold value is : " + ActualThresholdValue);
		base.stepInfo("Audio Search is done for " + SearchString + " and Threshold value is : " + ActualThresholdValue);

		return pureHit;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param SearchString
	 * @param language
	 * @return
	 */
	public int newAudioSearch(String SearchString, String language) {

		base.waitForElement(getCurrentAudioButton());
		getCurrentAudioButton().waitAndClick(10);
		base.waitForElement(getCurrentLanguageSelectButton());
		getCurrentLanguageSelectButton().selectFromDropdown().selectByVisibleText(language);
		// Enter seatch string
		base.waitForElement(get_Current_As_AudioText());
		get_Current_As_AudioText().SendKeys(SearchString);
		// Click on Search button
		getAdvanceSearch_btn_Current().Click();
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
		System.out.println("Audio Search is done for " + SearchString + " and PureHit is : " + pureHit);
		UtilityLog.info("Audio Search is done for " + SearchString + " and PureHit is : " + pureHit);
		return pureHit;

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void ViewInDocViewWithoutPureHit() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Search/Searches");

		System.out.println("Pure hit block already moved to action panel");
		UtilityLog.info("Pure hit block already moved to action panel");

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000); // App synch
		getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch

		if (getView().isDisplayed()) {
			driver.waitForPageToBeReady();
			Actions act = new Actions(driver.getWebDriver());
			act.moveToElement(getView().getWebElement()).build().perform();
		} else {
			System.out.println("View is not found");
		}
		getDocViewAction().waitAndClick(10);
		base.waitTime(3); // added for stabilization

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");

	}

	/**
	 * 
	 * @Author :Brundha
	 * @Description :Method to modify audio search
	 */

	public int modifyAdvanceSearch(String option, String SearchString) throws InterruptedException {

		base.waitTillElemetToBeClickable(getModifyASearch());
		getModifyASearch().waitAndClick(10);
		base.waitTillElemetToBeClickable(getQueryFromTextBox());
		getQueryFromTextBox().waitAndClick(10);
		base.stepInfo("Existing query is edited");
		if (option == "Yes") {
			base.waitTillElemetToBeClickable(getAdvancedSearchTextArea());
			getAdvancedSearchTextArea().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getAdvancedSearchTextArea());
			getAdvancedSearchTextArea().Clear();
			base.waitForElement(getAdvancedSearchTextArea());
			getAdvancedSearchTextArea().SendKeys(SearchString);
			base.waitForElement(getModifysearchOKBtn());
			getModifysearchOKBtn().waitAndClick(20);
			base.waitTime(3);
			driver.waitForPageToBeReady();
			base.passedStep("Ok button is clicked");
		} else {
			base.waitTillElemetToBeClickable(getModifysearchNOBtn());
			getModifysearchNOBtn().waitAndClick(10);
			base.passedStep("Cancel button is clicked");
		}
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsLastCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		int pureHit = Integer.parseInt(getPureHitsLastCount().getText());
		Reporter.log("Serach is done and PureHit is : " + pureHit, true);
		UtilityLog.info("Serach is done and PureHit is : " + pureHit);

		return pureHit;
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void addPureHit() {
		base.waitForElement(getCurrentPureHitAddBtn());
		getCurrentPureHitAddBtn().waitAndClick(10);
		base.stepInfo("purehit added to shop cart");
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public int newAudioSearch(String SearchString1, String SearchString2, String SearchString3, String language) {

		base.waitForElement(getCurrentAudioButton());
		getCurrentAudioButton().waitAndClick(10);

		base.waitForElement(getCurrentLanguageSelectButton());
		getCurrentLanguageSelectButton().selectFromDropdown().selectByVisibleText(language);
		// Enter seatch string
		base.waitForElement(get_Current_As_AudioText());
		get_Current_As_AudioText().SendKeys(SearchString1 + Keys.ENTER + SearchString2 + Keys.ENTER + SearchString3);

		// Click on Search button
		getAdvanceSearch_btn_Current().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		getPureHitsCount().isElementAvailable(10);
		base.waitTime(5);
		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
		System.out.println("Audio Search is done for " + SearchString1 + SearchString2 + SearchString3
				+ " and PureHit is : " + pureHit);
		UtilityLog.info("Audio Search is done for " + SearchString1 + SearchString2 + SearchString3
				+ " and PureHit is : " + pureHit);

		return pureHit;

	}

	public int newAudioSearchThreshold(String SearchString1, String SearchString2, String SearchString3,
			String language, String ThresholdValue) throws InterruptedException {

		base.waitForElement(getCurrentAudioButton());
		getCurrentAudioButton().ScrollTo();
		getCurrentAudioButton().waitAndClick(10);
		base.waitForElement(getCurrentLanguageSelectButton());
		getCurrentLanguageSelectButton().selectFromDropdown().selectByVisibleText(language);
		base.stepInfo("Selected Language as " + language);
		driver.waitForPageToBeReady();
		Actions action = new Actions(driver.getWebDriver());
		action.clickAndHold(GetCurrentSliderConfidenceThreshold().getWebElement());
		if (ThresholdValue.equalsIgnoreCase("50")) {
			action.moveToElement(GetCurrentSliderConfidenceThreshold().getWebElement(), -80, 0);
		}
		if (ThresholdValue.equalsIgnoreCase("60")) {
			action.moveToElement(GetCurrentSliderConfidenceThreshold().getWebElement(), -40, 0);
		}
		if (ThresholdValue.equalsIgnoreCase("70")) {
			action.moveToElement(GetCurrentSliderConfidenceThreshold().getWebElement(), 0, 0);
		}
		if (ThresholdValue.equalsIgnoreCase("80")) {
			action.moveToElement(GetCurrentSliderConfidenceThreshold().getWebElement(), 40, 0);
		}
		if (ThresholdValue.equalsIgnoreCase("90")) {
			action.moveToElement(GetCurrentSliderConfidenceThreshold().getWebElement(), 80, 0);
		}
		action.release();
		action.build().perform();
		GetCurrentSliderConfidenceThreshold().Click();
		// Enter search string
		base.waitForElement(get_Current_As_AudioText());
		get_Current_As_AudioText()
				.SendKeys(SearchString1 + Keys.ENTER + SearchString2 + Keys.ENTER + SearchString3 + Keys.ENTER);
		base.stepInfo("Entered a text in search text box" + SearchString1 + SearchString2 + SearchString3);
		base.waitForElement(getAdvanceSearch_btn_Current());
		getAdvanceSearch_btn_Current().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());

		String ActualThresholdValue = GetConfidenceThresholdInSearchResult().getText();
		System.out.println("Audio Search is done for " + SearchString1 + SearchString2 + SearchString3
				+ " and Theshold value is : " + ActualThresholdValue);
		base.stepInfo("Audio Search is done for " + SearchString1 + SearchString2 + SearchString3
				+ " and Threshold value is : " + ActualThresholdValue);

		return pureHit;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SearchString
	 * @param language
	 * @return
	 */
	public int audioSearchTwoTimesandAddingTwoPureHits(String SearchString, String language) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCurrentAudioButton());
		getCurrentAudioButton().ScrollTo();
		getCurrentAudioButton().waitAndClick(10);
		base.waitForElement(getCurrentLanguageSelectButton());
		getCurrentLanguageSelectButton().selectFromDropdown().selectByVisibleText(language);
		base.stepInfo("Selected Language as " + language);
		driver.waitForPageToBeReady();

		// Enter search string
		base.waitForElement(get_Current_As_AudioText());
		get_Current_As_AudioText().SendKeys(SearchString);
		base.stepInfo("Entered a text in search text box " + SearchString);
		base.waitForElement(getAdvanceSearch_btn_Current());
		getAdvanceSearch_btn_Current().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCurrentPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		base.waitForElement(getCurrentPureHitsCount());
		base.waitTime(3);
		int pureHit = Integer.parseInt(getCurrentPureHitsCount().getText());
		System.out.println("Audio Search is done for " + SearchString + " and PureHit is : " + pureHit);
		UtilityLog.info("Audio Search is done for " + SearchString + " and PureHit is : " + pureHit);

		return pureHit;

	}

	public void removePureHitsFromSelectedResult() {
		List<WebElement> PureHitsFromSelectedResult = getRemovePureHits().FindWebElements();
		for (int i = 0; i < PureHitsFromSelectedResult.size(); i++) {
			PureHitsFromSelectedResult.get(i).click();
		}
	}

	/**
	 * @author Vijaya.Rani
	 */
	public void bulkAssignWithOutPureHit() {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		System.out.println("Pure hit block already moved to action panel");
		UtilityLog.info("Pure hit block already moved to action panel");
		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait60);

		getBulkAssignAction().waitAndClick(10);

		base.stepInfo("performing bulk assign");
		UtilityLog.info("performing bulk assign");

	}

	/**
	 * @author Jayanthi.ganesan This method will save the search under node created
	 *         under default security group.
	 * @param searchName
	 * @param nodeName
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void saveSearchInNodeUnderGroup(String searchName, String nodeName, String groupName)
			throws InterruptedException, AWTException {
		// SaveSearch
		saveSearchAction();

		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}

		if (getSaveSearchPopupFolderName(groupName).isElementAvailable(3)) {
			base.stepInfo(groupName + " : is present");
			getSaveSearchPopupFolderName(groupName).waitAndClick(10);
		} else {
			base.stepInfo(groupName + " : is not present");
		}

		if (getCreatedNode(nodeName).isElementAvailable(7)) {
			System.out.println("On");
			getCreatedNode(nodeName).waitAndClick(5);
		} else {
			System.out.println("To Expand");
			Robot robot = new Robot();
			robot.mouseMove(200, 200);
			base.waitForElement(getExpandCurrentNode());
			getExpandCurrentNode().waitAndClick(20);
			base.waitForElement(getCreatedNode(nodeName));
			getCreatedNode(nodeName).waitAndClick(3);
		}

		driver.waitForPageToBeReady();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().waitAndClick(5);
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @Author Jeevitha
	 * @Description : Performs Search In Session Search
	 */
	public void SearchBtnAction() {
		driver.waitForPageToBeReady();
		if (getSearchButton().isDisplayed()) {
			getSearchButton().waitAndClick(10);
			System.out.println("Basic Search");
		} else if (getSearchButtonSec().isDisplayed()) {
			getSearchButtonSec().waitAndClick(10);
			System.out.println("Basic Search2");
		} else if (getQuerySearchButton().isDisplayed()) {
			getQuerySearchButton().waitAndClick(10);
			System.out.println("Advance Search");
		} else if (GetAdvSaveBtn_New().isDisplayed()) {
			getAdvanceSearch_btn_Current().waitAndClick(10);
			System.out.println("Advance Search2");
		}

		if (getYesQueryAlert().isElementAvailable(2)) {
			getYesQueryAlert().waitAndClick(8);
		}

	}

	/**
	 * @author Raghuram.A
	 * @description - verify tile spinning in session search page
	 */
	public void verifyTileSpinning() {
		List<String> spinningTileList = new ArrayList<>();

		spinningTileList = base.getAvailableListofElements(gettTileSpinningList());
		for (String a : spinningTileList) {
			if (a.trim().equals("")) {
				base.stepInfo(" Yes - one or more related tiles are still spinning ");
				break;
			} else {
				System.out.println(" one or more related tiles are not still spinning ");
			}
		}
	}

	/**
	 * @author Jayanthi.Ganesan This method will handle when all results are ready
	 *         pop up when search goes back ground
	 * @return
	 */
	public String handleWhenAllResultsPopUpDynamic() {
		String ID = null;

		if (getWhenAllResultsAreReadyPopUpDynamic().isElementAvailable(20)) {
			getWhenAllResultsAreReadyPopUpDynamic().waitAndClick(3);
			base.waitTime(1);
			ID = getWhenAllResultsAreReadyIDDynamic().getText();
			base.stepInfo("When All Results Tab Clicked And Seach is in back ground with " + "Generated ID is: " + ID);
			getBulkTagConfirmationBtnDynamic().ScrollTo();
			getBulkTagConfirmationBtnDynamic().waitAndClick(10);
		} else {
			base.stepInfo("Page Loaded and PopUp Didnot Appear");
		}
		return ID;
	}

	/**
	 * @author Jayanthi.Ganesan This method will perform Advanced search when search
	 *         goes background
	 * @param SearchString
	 * @param select
	 * @param i[represents nth number of search performed]
	 * @return
	 */
	public String advancedContentBGSearch(String SearchString, int i, boolean newSearch) {
		base.waitForElement(getContentAndMetaDatabtnCurrent());
		getContentAndMetaDatabtnCurrent().Click();
		// Enter search string
		base.waitForElement(getAdvancedContentSearchInputCurrent());
		getAdvancedContentSearchInputCurrent().SendKeys(SearchString);
		getAdvanceSearch_btn_Current().waitAndClick(15);
		driver.waitForPageToBeReady();
		String id = handleWhenAllResultsPopUpDynamic();
		if (getspinningPurehit(i).isElementAvailable(2) && getspinningfamilyHit(i).isElementAvailable(2)
				&& getspinningThreadedCount(i).isElementAvailable(2)
				&& getspinningThreadedCount(i).isElementAvailable(2)) {
			base.passedStep("All tiles are spinning when search is in back ground.");
		} else {
			base.failedStep("All tiles are  not spinning when search is in back ground.");
		}
		if (newSearch) {
			getNewSearchButton().waitAndClick(5);
		}
		return id;
	}

	/**
	 * @author Jayanthi.Ganesan This method will verify display of search results
	 *         after a background search without refresh.
	 * @param searchText[search Term]
	 * @param searchNO[Number   of saerch term as per the session search list
	 *                          displayed in right side]
	 */

	public void verifySearchDisplayWithoutRefresh(String searchText, String searchNO) {
		getSessionSearchList(searchNO).Click();
		if (getEnteredSearchText(searchText).isElementAvailable(2)) {
			base.waitTime(2);
			List<String> tileResults = base.availableListofElements(getAllTilesResult(searchNO));
			System.out.println(tileResults);
			int size = tileResults.size();
			int limit = size - 2;
			SoftAssert assertion = new SoftAssert();
			for (int j = 0; j < limit; j++) {
				assertion.assertNotNull(tileResults.get(j));
			}
			assertion.assertAll();
			base.passedStep("Search Result should appeared " + "without Refresh  on Advanced Search Result Screen");
		}
	}

	/**
	 * @author Raghuram.A
	 * @description - verify tile spinning in session search page dynamic
	 * @param searchName - Search name to pick (or) current search
	 * @param Index      - search index
	 */
	public void verifyTileSpinning(String searchName, int Index) {
		List<String> spinningTileList = new ArrayList<>();

		spinningTileList = base.getAvailableListofElements(gettTileSpinningList(searchName, Index));
		for (String a : spinningTileList) {
			if (a.trim().equals("")) {
				base.stepInfo(" Yes - one or more related tiles are still spinning ");
				break;
			} else {
				System.out.println(" one or more related tiles are not still spinning ");
			}
		}
	}

	/**
	 * @author Jayanthi.Ganesan This method will perform a metadata search in
	 *         advanced search page in draft state[which means with out clicking
	 *         search button].
	 * @param metaDataField [Meta data field to be selected in DD]
	 * @param val1[Metadata Value to be entered in query text box]
	 */
	public void advMetaSearch_Draft(String metaDataField, String val1) {

		base.waitForElement(getAdvanceSearch_MetadataBtn());
		getAdvanceSearch_MetadataBtn().Click();
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		getMetaDataInserQuery().Click();
	}

	/**
	 * @author Jayanthi.Ganesan This method will create Metadata search in advanced
	 *         search page with operator
	 * @param metaData[Meta           data field to be selected in DD]
	 * @param metaDataValue[Metadata  Value to be entered in query text box]
	 * @param Operator
	 * @param metaData1[Meta          data field to be selected in DD]
	 * @param metaDataValue1[Metadata Value to be entered in query text box]
	 * @param clickContetnBtn         [if true it will navigate to advanced saerch
	 *                                button and click on content meta data button]
	 */
	public void metadataSearchesUsingOperators(String metaData, String metaDataValue, String Operator, String metaData1,
			String metaDataValue1, boolean clickContetnBtn) {
		if (clickContetnBtn) {
			navigateToAdvancedSearchPage();
			base.waitForElement(getContentAndMetaDatabtn());
			getContentAndMetaDatabtn().Click();
		}
		advMetaSearch_Draft(metaData, metaDataValue);
		selectOperator(Operator);
		advMetaSearch_Draft(metaData1, metaDataValue1);
	}

	/**
	 * @author Jayanthi.Ganesan This method will handle bulk navigation pop up if we
	 *         navigate from session search page to any page with very high doc
	 *         count.
	 * @return
	 */
	public String pushingBulkNavigationToBackGround() {
		// base.waitForElementToBeGone(getspinningWheel(), 8);
		for (int i = 0; i <= 6; i++) {
			base.waitTime(1);
			if (getspinningWheel().Displayed()) {
				continue;
			}
			if (getBulkNavigationPopup().Displayed()) {
				base.failedStep("Bulk navigation pop appeared before 8 seconds");
			}

		}
		String BGTaskId = null;
		if (getBulkNavigationPopup().isElementAvailable(3)) {
			base.stepInfo("Bulk Navigation pop displayed.");
			getBulkNavigationPopupNoBtn().waitAndClick(30);
			if (getBackGroundTaskAlertPopUp().isElementAvailable(3)) {
				BGTaskId = getWhenAllResultsAreReadyIDDynamic().getText();
				base.stepInfo("bulk navigation is  in back ground with " + "Generated ID is: " + BGTaskId);
				getBulkTagConfirmationBtnDynamic().ScrollTo();
				getBulkTagConfirmationBtnDynamic().waitAndClick(10);
			} else {
				base.stepInfo("Page Loaded and bulk navigation PopUp Didnot Appear");
			}
		}

		else {
			base.stepInfo("Bulk Navigation pop not displayed.");
		}
		return BGTaskId;
	}

	/**
	 * this method will verify whether the notification count in bull horn icon and
	 * navigate to my back ground task page
	 * 
	 * @author Jayanthi.Ganesan
	 * @param bgCount[Initial back ground count]
	 */
	public void verifyNotificationAndNavigateBackGroundTaskPg(int bgCount) {

//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return base.initialBgCount() == bgCount + 1;
//			}
//		}), Input.wait60);
		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(20);
		base.waitForElement(getViewAllBtn());
		getViewAllBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		base.stepInfo("Navigated to My backgroud task page.");
	}

	/**
	 * This method will take particular cell value from a My back ground task page
	 * web table row based on Back ground task id given.
	 * 
	 * @author Jayanthi.Ganesan
	 * @param columnName[column value from which data needs to be taken ]
	 * @param BG_ID[Back        ground task id ]
	 * @return
	 */
	public String getRowData_BGT_Page(String columnName, String BG_ID) {
		int Index;
		Index = base.getIndex(getTableHeader_BGPage(), columnName);
		String actualDocs = getRowElement_BgPage(BG_ID, Index).getText();
		return actualDocs;
	}

	/**
	 * @author Jayanthi Description: To BulkTag the Family Members Docs.
	 * @param TagName [Name of Tag]
	 */
	public void bulkTagFamilyMemberDocuments(String TagName) {
		driver.getWebDriver().get(Input.url + "Search/Searches");
		getFamilyAddButton().ScrollTo();
		getFamilyAddButton().waitAndClick(10);
		getBulkActionButton().ScrollTo();
		getBulkActionButton().waitAndClick(10);
		base.waitTime(1);
		base.waitForElement(getBulkTagAction());
		getBulkTagAction().waitAndClick(30);
		getBulkNewTab().waitAndClick(60);
		base.waitForElement(getEnterTagName());
		getEnterTagName().SendKeys(TagName);
		getTagsAllRoot().waitAndClick(30);
		driver.Manage().window().fullscreen();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();
		driver.Manage().window().maximize();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();

		getBulkTagConfirmationButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		UtilityLog.info("Bulk Tag is done, Tag is : " + TagName);
		Reporter.log("Bulk Tag is done, Tag is : " + TagName, true);

	}

	/**
	 * @author Jayanthi.Ganesan This method will try to do audio search without
	 *         selecting language.
	 * @param SearchString
	 */
	public void verifyWarningAudioSearch_NotSelctedLanguage(String SearchString) {
		base.waitForElement(getAs_Audio());
		getAs_Audio().ScrollTo();
		getAs_Audio().waitAndClick(10);
		driver.waitForPageToBeReady();
		// Enter search string
		base.waitForElement(getAs_AudioText());
		getAs_AudioText().SendKeys(SearchString);
		base.stepInfo("Entered a text in search text box" + SearchString);
		base.waitForElement(getQuerySearchButton());
		getQuerySearchButton().Click();
		base.VerifyWarningMessage("Please select the Language Pack/Dialect in Audio search");

	}

	/**
	 * @author Jayanthi.Ganesan This method will assign/unassign pop up when we try
	 *         to perform bulk assign from anysource for existing assignment.
	 * @param assignmentName
	 */
	public void bulkAssignExistingWithoutActionTab(String assignmentName) {
		UtilityLog.info("performing bulk assign");
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectAssignmentExisting(assignmentName));
		driver.waitForPageToBeReady();
		getSelectAssignmentExisting(assignmentName).Click();
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		getPersistantHitCb_Existing().isElementAvailable(3);
		getPersistantHitCb_Existing().waitAndClick(5);
		base.waitTillElemetToBeClickable(getContinueButton());
		getContinueButton().waitAndClick(20);
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk assign is done, assignment is : " + assignmentName);
		Reporter.log("Bulk assign is done, assignment is : " + assignmentName, true);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param Text
	 * @param Value
	 * @Description without passing url for keep previous used data and search in
	 *              metadata
	 */
	public void SearchMetaDataWithoutUrlPassing(String Text, String Value) {
		// To make sure we are in basic search page
		driver.waitForPageToBeReady();
		base.waitForElement(getBasicSearch_MetadataBtn());
		getBasicSearch_MetadataBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().selectFromDropdown().selectByVisibleText(Text);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(Value);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		// Click on Search button
		base.waitForElement(getSearchButton());
		getSearchButton().waitAndClick(5);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Mohan 11/10/21 NA Modified date: NA Modified by:NA
	 * @param metadataDocId
	 * @description: to search metadataquery
	 * 
	 */
	public void basicSearchWithMetaDataQueryUsingSourceDOCID(String metadataDocId) {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getBasicSearch_MetadataBtn());
			driver.waitForPageToBeReady();
			getBasicSearch_MetadataBtn().waitAndClick(10);

			driver.waitForPageToBeReady();
			base.waitForElement(getSelectMetaData());
			getSelectMetaData().selectFromDropdown().selectByValue("SourceDocID");

			driver.waitForPageToBeReady();
			base.waitForElement(getMetaDataSearchText1());
			getMetaDataSearchText1().SendKeys(metadataDocId);

			base.waitForElement(getMetaDataInserQuery());
			getMetaDataInserQuery().waitAndClick(10);

			base.waitForElement(getSearchButton());
			getSearchButton().waitAndClick(10);

		} catch (Exception e) {
			base.failedStep("Query is not found");
		}

	}

	/**
	 * @author Gopinath.Srinivasan
	 * @description : Method to seached documents view in doc view.
	 */

	public void viewInDocView() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Search/Searches");

		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		base.waitTime(2); // App synch
		getBulkActionButton().waitAndClick(5);
		base.waitTime(2); // App Synch

		if (getDocViewAction().isDisplayed()) {
			getDocViewAction().Click();
		} else {
			Actions ac = new Actions(driver.getWebDriver());
			ac.moveToElement(getViewOn().getWebElement()).build().perform();
			base.waitTime(2);
			getViewInDocViewLat().isElementAvailable(15);
			getViewInDocViewLat().Click();
		}
		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");

	}

	/**
	 * @author Raghuram.A
	 * @param searchName  - Search name to save
	 * @param nodeName    - node name to save search
	 * @param newNodeList - available nodeList
	 * @throws InterruptedException
	 */
	public void saveSearchInNodewithChildNode(String searchName, String nodeName, List<String> newNodeList)
			throws InterruptedException {
		String rootNode = newNodeList.get(0);
		// SaveSearch
		saveSearchAction();

		try {
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}

		if (getSavedSearch_MySearchesTabClosed().isElementAvailable(4)) {
			getSavedSearch_MySearchesTabClosed().waitAndClick(5);
		} else {
			System.out.println("Already Expanded");
		}

		base.waitForElement(getCreatedNode(rootNode));
		getCreatedNode(rootNode).waitAndClick(3);
		if (getCurrentTabClosed().isElementAvailable(2)) {
			getCurrentTabClosedExpand().waitAndClick(5);
		}

		for (int i = 0; i <= newNodeList.size(); i++) {
			getCreatedNode(newNodeList.get(i)).waitAndClick(3);
			if (newNodeList.get(i).equalsIgnoreCase(nodeName)) {
				break;
			}
			if (getCurrentTabClosed().isElementAvailable(2)) {
				getCurrentTabClosedExpand().waitAndClick(5);
			}
		}

		driver.waitForPageToBeReady();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param SearchString1
	 * @param SearchString2
	 * @param language
	 * @return
	 * @Description New audio search with two search string
	 */
	public int newAudioSearch(String SearchString1, String SearchString2, String language) {

		base.waitForElement(getCurrentAudioButton());
		getCurrentAudioButton().waitAndClick(10);

		base.waitForElement(getCurrentLanguageSelectButton());
		getCurrentLanguageSelectButton().selectFromDropdown().selectByVisibleText(language);
		// Enter seatch string
		base.waitForElement(get_Current_As_AudioText());
		get_Current_As_AudioText().SendKeys(SearchString1 + Keys.ENTER + SearchString2 + Keys.ENTER);

		// Click on Search button
		getAdvanceSearch_btn_Current().Click();

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		driver.waitForPageToBeReady();
		base.waitForElement(getPureHitsCount2ndSearch());

		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
		System.out
				.println("Audio Search is done for " + SearchString1 + SearchString2 + " and PureHit is : " + pureHit);
		UtilityLog.info("Audio Search is done for " + SearchString1 + SearchString2 + " and PureHit is : " + pureHit);

		return pureHit;

	}

	/**
	 * @author Brundha
	 * @description : Method to verify the document count in metadata
	 */
	public void verifyTheCountOfDocumentForMetaData() {
		for (int i = 0; i < 4; i++) {
			driver.waitForPageToBeReady();
			getPureHitsCount().isElementAvailable(2);
			String PurehitCount = getPureHitsCount().getText();
			if (Integer.valueOf(PurehitCount) != 0) {
				base.passedStep("Document is displayed as expected");
				break;
			} else {
				base.failedStep("Document count is not displayed as expected");
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify Precision Sensitivity Of Conceptual Search
	 * @param conceptualBtn
	 * @param ConceptSearch
	 * @return
	 */
	public String verifyingPrecisionValue(boolean conceptualBtn, String ConceptSearch, boolean DefaultValue,
			boolean MoveSlider) {

		if (conceptualBtn) {
			advancedSearchConceptual(ConceptSearch);
		}

		if (DefaultValue) {
			base.waitForElement(gettingThresholdValue());
			String defSliderValueInPer = gettingThresholdValue().getText();
			base.stepInfo("Default precision slider value : " + defSliderValueInPer);
			System.out.println("Default precision slider value : " + defSliderValueInPer);

			String defPrecisionTbValue = driver
					.findAttributeValueViaJS("return document.querySelector('.form-control').value");
			base.stepInfo("Default precision textbox value  : " + defPrecisionTbValue);
			System.out.println("Default precision textbox value  : " + defPrecisionTbValue);

			String[] defSliderValue = defSliderValueInPer.split(" ");
			String passMsg = "Default Slider and Textbox Value is Same";
			String failMsg = "Default Slider and Textbox Value is Not Same";
			base.textCompareEquals(defSliderValue[0], defPrecisionTbValue, passMsg, failMsg);

			softAssert.assertEquals(defSliderValue[0], "70");
		}

		if (MoveSlider) {
			driver.waitForPageToBeReady();
			getConceptualRight().waitAndClick(10);
			base.passedStep("Precision Slider is Flexible to Move");
		}

		driver.waitForPageToBeReady();
		String sliderValueAfterInPer = gettingThresholdValue().getText();
		base.stepInfo("precision slider value after moving : " + sliderValueAfterInPer);
		System.out.println("precision slider value after moving : " + sliderValueAfterInPer);

		String precisionTbValue = driver
				.findAttributeValueViaJS("return document.querySelector('.form-control').value");
		base.stepInfo("precision textbox value  : " + precisionTbValue);
		System.out.println("precision textbox value : " + precisionTbValue);

		String[] sliderChangeValue = sliderValueAfterInPer.split(" ");
		String passMsg2 = "Slider and Textbox Value is Same After Changing Moving Slider";
		String failMsg2 = "Slider and Textbox Value is Not Same";
		base.textCompareEquals(sliderChangeValue[0], precisionTbValue, passMsg2, failMsg2);

		softAssert.assertAll();
		return precisionTbValue;
	}

	/**
	 * @author Brundha
	 */
	public void verifyingBackGrounTaskInBullHornIcon() {

		getBullHornIcon().isElementAvailable(30);
		base.waitForElement(getBullHornIcon());
		String color = getBullIcon().getWebElement().getCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#e74735";
		if (ActualColor.equals(ExpectedColor)) {
			base.passedStep("BullHorn icon is highlighted red as expected");
		} else {
			base.failedStep("Bullhorn icon is not red as expected");
		}
		getBullHornIcon().waitAndClick(20);
		getBackGroundTask().isDisplayed();
		String BackGroundText = getBackGroundTask().getText();
		System.out.println(BackGroundText);
		String[] Tag = BackGroundText.split("-");
		String output = Tag[1];
		System.out.println(output);
		if (output.equals("Tag with Bulkaction")) {
			base.passedStep("Related task is displayed in the background task");
		} else {
			base.failedStep("Related task is not displayed in the backgroundtask");
		}

	}

	public void bulkFolderExistingWithoutPureHit(final String folderName) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		driver.scrollPageToTop();
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderAction().Visible();
			}
		}), Input.wait60);

		getBulkFolderAction().waitAndClick(10);
		driver.Manage().window().fullscreen();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolderExisting(folderName).Visible();
			}
		}), Input.wait60);

		getSelectFolderExisting(folderName).waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().waitAndClick(10);
		driver.Manage().window().maximize();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().waitAndClick(10);

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

	/**
	 * @author Vijaya.Rani modifiedOn : 27/4/22 by modified by :NA
	 * @param searchName Shared With Project Administrator
	 */
	public void saveSearchSharedWithPA(String searchName) {
		// Save Search
		saveSearchAction();

		if (getSaveAsNewSearchRadioButton().isElementAvailable(7)) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveAsNewSearchRadioButton().Visible() && getSaveAsNewSearchRadioButton().Enabled();
				}
			}), Input.wait30);
			getSaveAsNewSearchRadioButton().waitAndClick(5);
		} else {
			System.out.println("Radio button already selected");
			UtilityLog.info("Radio button already selected");
		}

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {
				return getSavedSearch_SharedWithPA().Visible() && getSavedSearch_SharedWithPA().Enabled();
			}
		}), Input.wait30);
		getSavedSearch_SharedWithPA().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveSearch_Name().Visible() && getSaveSearch_Name().Enabled();
			}
		}), Input.wait30);
		getSaveSearch_Name().SendKeys(searchName);
//		driver.Manage().window().fullscreen();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveSearch_SaveButton().Visible() && getSaveSearch_SaveButton().Enabled();
			}
		}), Input.wait30);
		getSaveSearch_SaveButton().Click();
//		driver.Manage().window().maximize();
		driver.waitForPageToBeReady();

		base.VerifySuccessMessage("Saved search saved successfully");

		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param metaDataField
	 * @param val1
	 * @return
	 * @Description new metadata basic search
	 */
	public int newMetaDataSearchInBasicSearch(String metaDataField, String val1) {
		// To make sure we are in basic search page
		driver.waitForPageToBeReady();
		// Enter search string
		base.waitForElement(getNewSearch_MetadataBtn());
		getNewSearch_MetadataBtn().waitAndClick(10);
		base.waitForElement(getNewSelectMetaData());
		// getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		base.waitForElement(getNewSelectMetaData());
		getNewSelectMetaData().waitAndClick(10);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(3);
		// Click on Search button
		base.waitForElement(getSecondSearchBtn());
		getSecondSearchBtn().waitAndClick(3);
		if (base.getYesBtn().isElementAvailable(2)) {
			base.getYesBtn().waitAndClick(10);
		}
		base.waitForElement(getPureHitsCount2ndSearch());
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount2ndSearch().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getPureHitsCount2ndSearch().waitAndClick(15);
		int pureHit = Integer.parseInt(getPureHitsCount2ndSearch().getText());
		base.stepInfo("Search is done for " + metaDataField + " with value " + val1 + " purehit is : " + pureHit);
		return pureHit;
	}

	/**
	 * @author Indium-Baskar
	 */
	public int verifyingBullIconAndGetingIDValue() {

		getBullHornIcon().isElementAvailable(30);
		base.waitForElement(getBullHornIcon());
		String color = getBullIcon().getWebElement().getCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#e74735";
		if (ActualColor.equals(ExpectedColor)) {
			base.passedStep("BullHorn icon is highlighted red as expected");
		} else {
			base.failedStep("Bullhorn icon is not red as expected");
		}
		base.waitTime(5);
		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(20);
		getBatchPrintBG().isDisplayed();
		String backGroundText = getBatchPrintBG().getText();
		System.out.println(backGroundText);
		String afterRemovingChar = backGroundText.replaceAll("[^\\d]", "");
		int idValue = Integer.parseInt(afterRemovingChar);
		System.out.println(idValue);
		if (backGroundText.contains("Batch Print")) {
			base.passedStep("Related task is displayed in the background task");
		} else {
			base.failedStep("Related task is not displayed in the backgroundtask");
		}
		return idValue;

	}

	/**
	 * @author Jeevitha
	 * @Description :verifies Session saerch page Url
	 */
	public void verifySessionSearchPage() {

		String currentUrl = driver.getWebDriver().getCurrentUrl();
		String BsUrl = Input.url + "Search/Searches";

		if (currentUrl.equals(BsUrl)) {
			base.passedStep("Navigated to Basic Search Page : " + currentUrl);
		} else if (currentUrl.contains("Search/Searches")) {
			base.passedStep("Navigated to Basic Search Page : " + currentUrl);
		} else {
			base.failedStep("Navigated to Page : " + currentUrl);
		}
	}

	/**
	 * @author Brundha
	 * @description : Method to verify audio document count
	 */
	public void verifyDocCountForAudioPlayerReady() {
		driver.waitForPageToBeReady();
		getPureHitsCount().isElementAvailable(2);
		String PurehitCount = getPureHitsCount().getText();
		if (Integer.valueOf(PurehitCount) != 0) {
			base.passedStep("Document is displayed as expected");
		} else {
			base.failedStep("Document count is not displayed as expected");
		}
	}

	/**
	 * @Author Jeevitha
	 * @param SearchNamelist
	 * @param conceptuallySimilarCountList
	 * @throws InterruptedException
	 */
	public void verifyingConceptuallySimilarCountInSessionSearcheWithCountInSavedSearch(List<String> SearchNamelist,
			List<String> conceptuallySimilarCountList) throws InterruptedException {
		for (int i = 0; i < SearchNamelist.size(); i++) {
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			String concept = conceptuallySimilarCountList.get(i);
			driver.waitForPageToBeReady();
			SavedSearch savedSearch = new SavedSearch(driver);
			savedSearch.selectSavedSearch(SearchNamelist.get(i));
			savedSearch.getSavedSearchEditButton().waitAndClick(5);
			System.out.println("Edit icon Clicked from SavedSearch");
			driver.waitForPageToBeReady();
			getModifyASearch_Current().waitAndClick(5);

			System.out.println("Modify search button Clicked");
			getQuerySearchBtn().waitAndClick(5);
			System.out.println("Search button Clicked");
			if (base.getYesBtn().isElementAvailable(3)) {
				base.getYesBtn().waitAndClick(5);
			}
			base.waitForElement(getConceptualCountPlayButton());
			getConceptualCountPlayButton().waitAndClick(5);

			base.waitForElement(getConceptualCountDisplayed());
			base.VerifySuccessMessage("Conceptual Similar search started successfully.");
			System.out.println("Clicked ConceptualCount button");
			driver.waitForPageToBeReady();

			String conceptualCount = getConceptualCount_last().getText();
			System.out.println("Conceptual Similar Count for SavedSearch '" + SearchNamelist.get(i)
					+ "' from SessionSearch : " + conceptualCount);
			base.stepInfo("Conceptual Similar Count for SavedSearch '" + SearchNamelist.get(i)
					+ "' from SessionSearch : " + conceptualCount);
			System.out.println("Conceptual Similar Count for SavedSearch '" + SearchNamelist.get(i)
					+ "' from SavedSearch :" + conceptuallySimilarCountList.get(i));
			base.stepInfo("Conceptual Similar Count for SavedSearch '" + SearchNamelist.get(i) + "' from SavedSearch :"
					+ conceptuallySimilarCountList.get(i));
			softAssert.assertEquals(conceptualCount, conceptuallySimilarCountList.get(i));
			softAssert.assertAll();
		}
	}

	/**
	 * @Author Baskar
	 * @Description: To Create Metadata basic search
	 * @param metaDataField
	 * @param val1--metadata value
	 * @return
	 */
	public int metaDataSearchInBasicSearch(String metaDataField, String val1) {
		// To make sure we are in basic search page
		// Enter search string
		base.waitForElement(getBasicSearch_MetadataBtn());
		getBasicSearch_MetadataBtn().waitAndClick(3);
		base.waitForElement(getSelectMetaData());
		// getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(3);
		// Click on Search button
		base.waitForElement(getSearchButton());
		getSearchButton().waitAndClick(3);
		base.waitForElement(getPureHitsCount());
		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		getPureHitsCount().waitAndClick(15);
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		base.stepInfo("Search is done for " + metaDataField + " with value " + val1 + " purehit is : " + pureHit);
		return pureHit;
	}

	/**
	 * this method will verify whether the notification count in bull horn icon and
	 * navigate to doclist page
	 * 
	 * @author Baskar
	 * @param bgCount[Initial back ground count]
	 */
	public void verifyNotificationAndNavigateToDocList(int bgCount, String bgId) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == bgCount + 1;
			}
		}), Input.wait60);
		String color = getBullIcon().getWebElement().getCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#e74735";
		if (ActualColor.equals(ExpectedColor)) {
			base.passedStep("BullHorn icon is highlighted red as expected");
		} else {
			base.failedStep("Bullhorn icon is not red as expected");
		}
		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(20);
		driver.waitForPageToBeReady();
		base.waitForElement(getTaskBackGround(bgId));
		getTaskBackGround(bgId).waitAndClick(20);
		String doclistUrl = Input.url + "Document/DocList";
		driver.waitForPageToBeReady();
		if (driver.getUrl().equalsIgnoreCase(doclistUrl)) {
			base.passedStep("User navigate to doclist when clicked from bull icon task");
		} else {
			base.failedMessage("user not navigated to doclist page");
		}
	}

	/**
	 * @author: Arun Created Date: 20/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform unrelease docs from security group
	 */
	public void unReleaseDocsFromSecuritygroup(final String SecurityGroup) {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		getBulkActionButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		try {
			getBulkReleaseAction().waitAndClick(10);
		} catch (Exception e) {

			getBulkReleaseActionDL().waitAndClick(10);
		}

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelDefaultSecurityGroup_CheckBox(SecurityGroup).Visible();
			}
		}), Input.wait60);
		getBulkRelDefaultSecurityGroup_CheckBox(SecurityGroup).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelease_ButtonUnRelease().Visible();
			}
		}), Input.wait60);
		getBulkRelease_ButtonUnRelease().waitAndClick(20);

		if (getTallyContinue().isElementAvailable(2)) {
			getTallyContinue().waitAndClick(10);
		} else {
			driver.waitForPageToBeReady();
		}

		if (getFinalizeButton().isDisplayed()) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFinalizeButton().Visible();
				}
			}), Input.wait60);
			getFinalizeButton().waitAndClick(20);
		}

		base.passedStep("Successfully unreleased documents from security group");
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify Session search page Url
	 */
	public void verifySessionSearchPageUrl() {
		String url = driver.getUrl();
		String expURL = Input.url + "Search/Searches";
		softAssert.assertEquals(expURL, url);
		base.stepInfo("Navigate to session search page");
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To Validate under My saved search more 3 saved search trems are
	 *               there
	 * 
	 */
	public void validateMySavedSearchTerms() {

		String BasicSearchName = "CloningProjectSaveSearch01" + Utility.dynamicNameAppender();
		String BasicSearchName1 = "CloningProjectSaveSearch02" + Utility.dynamicNameAppender();
		String BasicSearchName2 = "CloningProjectSaveSearch03" + Utility.dynamicNameAppender();

		WebElement savedSearchTableCount = getSavedSearchTableCount().getWebElement();
		List<WebElement> tablecount = savedSearchTableCount.findElements(By.tagName("tr"));
		int tableSize = tablecount.size();

		if (getSavedSearchTableNoDatas().isDisplayed() && tableSize < 3) {
			base.stepInfo("There is no search terms under My saved search");
			basicContentSearch("test");
			saveSearch(BasicSearchName);
			base.selectproject(Input.projectName);
			basicContentSearch("crammer");
			saveSearch(BasicSearchName1);
			base.selectproject(Input.projectName);
			basicContentSearch("comments");
			saveSearch(BasicSearchName2);

		} else {
			base.stepInfo("Save search terms are already present");
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To Validate under My saved search more 3 saved search nodes are
	 *               there
	 * 
	 */
	public void validateSavedSearchNode() {
		getSavedSearchNodeCount().isElementAvailable(5);
		WebElement savedSearchNodes = getSavedSearchNodeCount().getWebElement();
		List<WebElement> NodesCount = savedSearchNodes.findElements(By.xpath("//*[@id='jsTreeSavedSearch']//ul//li"));
		int nodeSize = NodesCount.size();
		System.out.println(nodeSize);
		if (nodeSize > 2) {
			base.passedStep("There are more than 2 level of search group hierarchy");

		} else {
			base.failedStep("There are no search nodes hierarchy present");
		}

	}

	/**
	 * @Author Jeevitha
	 * 
	 */
	public void addPurehitAndActionAsConceptOrComm(boolean communication, boolean concept) {

		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(3);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(3);

		if (communication) {
			base.waitForElement(getAnalysisOfCommExplorer());
			getAnalysisOfCommExplorer().waitAndClick(3);
		} else if (concept) {
			base.waitForElement(getAnalysisOfConceptExplorer());
			getAnalysisOfConceptExplorer().waitAndClick(3);
		}
		driver.waitForPageToBeReady();

		String url = driver.getUrl();
		if (url.contains("CommunicationExplorerReport")) {
			base.passedStep("Navigate to Communications Explorer Page");
		} else if (url.contains("ConceptExplorer")) {
			base.passedStep("Navigate to Concept Explorer Page");
		} else {
			base.failedStep("Navigates to wrong Page");
		}
	}

	/**
	 * @author: Arun Created Date: 29/06/2022 Modified by: NA Modified Date: NA
	 * @description: this method will navigate to perform metadata search in
	 *               advanced search
	 */
	public void navigateToAdvancedMetaDataSearch() {
		navigateToSessionSearchPageURL();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchLink().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchLink().waitAndClick(5);
		base.stepInfo("Navigated to advanced search");
		getContentAndMetaDatabtn().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvanceSearch_MetadataBtn().Visible();
			}
		}), Input.wait30);
		getAdvanceSearch_MetadataBtn().waitAndClick(5);

	}

	/**
	 * @author: Arun Created Date: 29/06/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify options available on DocDateDateOnly
	 *               field
	 */
	public void verifyOptionsDisplayForDocDateDateOnlyField() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectMetaData().Visible();
			}
		}), Input.wait30);
		getSelectMetaData().selectFromDropdown().selectByVisibleText(Input.DocDateDateOnly);
		base.stepInfo("Verifying the options available for 'DocDateDateOnly'");
		List<WebElement> availableOptions = getMetaOption().selectFromDropdown().getOptions();
		int size = availableOptions.size();
		for (int i = 0; i < size; i++) {
			String option = availableOptions.get(i).getAttribute("value");
			if (option.equalsIgnoreCase(Input.is)) {
				if (getMetaDataSearchText1().isElementAvailable(10)
						&& !getMetaDataSearchText2().isElementAvailable(10)) {
					base.passedStep("'IS' option available with single textbox");
				}
			}
			if (option.equalsIgnoreCase(Input.range)) {
				getMetaOption().selectFromDropdown().selectByVisibleText(Input.range);
				if (getMetaDataSearchText1().isElementAvailable(10)
						&& getMetaDataSearchText2().isElementAvailable(10)) {
					base.passedStep("'RANGE' option available with two textboxes");
				}
			}
		}
	}

	/**
	 * @author: Arun Created Date: 30/06/2022 Modified by: NA Modified Date: NA
	 * @description: this method will add the pure hit tile to shopping cart and
	 *               navigate to required page
	 */
	public void addPureHitAndNavigate(String navigate) {

		driver.waitForPageToBeReady();

		if (getPureHitAddBtn().isElementAvailable(2)) {
			getPureHitAddBtn().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.passedStep("Pure hit added to the shopping cart");
		if (navigate.equalsIgnoreCase("Categorize")) {
			categorize = new Categorization(driver);
			categorize.navigateToCategorizePage();
			base.passedStep("Navigated to categorize page");
		} else if (navigate.equalsIgnoreCase("Users")) {
			userManage = new UserManagement(driver);
			userManage.navigateToUsersPAge();
			base.passedStep("Navigated to users page");
		} else if (navigate.equalsIgnoreCase("Assignment")) {
			assignPage = new AssignmentsPage(driver);
			assignPage.navigateToAssignmentsPage();
			base.passedStep("Navigated to Assignment page");
		}
	}

	/**
	 * @author: Arun Created Date: 30/06/2022 Modified by: NA Modified Date: NA
	 * @description: this method will navigate to search page and check tile
	 *               retained status
	 */
	public void navigateToSearchPageAndVerifyTileStatus() {
		navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		if (getRemoveAddBtn().isElementAvailable(10)) {
			base.passedStep("Dropped tiles retained in shopping cart when navigating back to search page");
		} else {
			base.failedStep("Dropped tiles not retained in shopping cart when navigating back to search page");
		}

	}

	/**
	 * @author: Arun Created Date: 01/07/2022 Modified by: NA Modified Date: NA
	 * @throws InterruptedException
	 * @description: this method will create query with SG OR operator with
	 *               production status/date
	 */
	public void configureQueryWithSecurityGroupAndProductionStatus(String securityGroup, String operator,
			Boolean productionDate) throws InterruptedException {
		driver.waitForPageToBeReady();
		if (!(securityGroup == null)) {
			selectSecurityGinWPS(securityGroup);
		}
		if (!(operator == null)) {
			selectOperator(operator);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionBtn().Visible() && getProductionBtn().Enabled();
			}
		}), Input.wait30);
		getProductionBtn().Click();
		if (productionDate) {
			selectDate("From Date");
			selectDate("To Date");
		}
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMetaDataInserQuery().Visible();
			}
		}), Input.wait30);
		getMetaDataInserQuery().waitAndClick(5);
		base.passedStep("Inserted query");
		driver.scrollPageToTop();
	}

	/**
	 * @author: Arun Created Date: 01/07/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the doc count available under SG with
	 *               search result
	 */
	public void verifyDocsCountAvailableInSgWithSearchResult() {
		serarchWP();
		driver.waitForPageToBeReady();
		base.stepInfo("Get count available in selected security group");
		if (getCountUniqueDocId().isElementAvailable(10)) {
			String label = getCountUniqueDocId().getText();
			String countlabel = label.substring(label.indexOf(":"));
			int expectedCount = Integer.parseInt(countlabel.replace(",", "").replace(": ", ""));
			int actualCount = Integer.parseInt(verifyPureHitsCount());
			if (actualCount <= expectedCount) {
				base.passedStep(
						"Application returns all the documents which are available under selected security group in search result.");
			} else {
				base.failedStep("Application not returned all the documents which are available");
			}
		}
	}

	/**
	 * @Author Brundha
	 * @Description: To Create Metadata basic search
	 * @param metaDataField
	 * @param val1--metadata value
	 * @param val2
	 * @return
	 */
	public int metaDataSearchInBasicSearch(String metaDataField, String val1, String val2) {
		// Enter search string
		base.waitForElement(getBasicSearch_MetadataBtn());
		getBasicSearch_MetadataBtn().waitAndClick(3);
		base.waitForElement(getSelectMetaData());
		base.waitForElement(getSelectMetaData());
		getSelectMetaData().waitAndClick(3);
		base.waitForElement(SelectFromDropDown(metaDataField));
		SelectFromDropDown(metaDataField).waitAndClick(10);
		base.waitForElement(getMetaDataSearchText1());
		getMetaDataSearchText1().SendKeys(val1 + Keys.TAB);
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(3);
		driver.waitForPageToBeReady();
		getSearchString2(2).getWebElement().sendKeys(Keys.ENTER + val2);

		if (getTallyContinue().isElementAvailable(2)) {
			getTallyContinue().waitAndClick(10);
		} else {
			driver.waitForPageToBeReady();
		}
		base.waitForElement(getSearchButton());
		getSearchButton().waitAndClick(3);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		getPureHitsCount().waitAndClick(15);
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		return pureHit;
	}

	public void verifyQueryPresentinSearchbox(String SearchTabNo, String query) {

		if (getAdvScrnSearchLabel(SearchTabNo).Visible()) {
			base.waitForElement(getQueryFromTextBox());
			String actText = getQueryFromTextBox().getText();
			String expText = query;
			SoftAssert asserts = new SoftAssert();
			asserts.assertEquals(expText, actText);
			base.passedStep(query + " present in search " + SearchTabNo);
		} else {
			base.failedStep(SearchTabNo + "is Not Visible");
		}
	}

	/**
	 * @author: Arun Created Date: 07/07/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the search result for master date
	 *               metadata using range operator
	 * @param range1--from date (year)
	 * @param range2--     to date (year)
	 */
	public void verifyMasterDateSearchResults(String range1, String range2) {

		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		base.stepInfo("pure hit count for search result '" + pureHit + "'");
		String masterDate = getMasterDate().getText();
		base.stepInfo(" master date search result details'" + masterDate + "'");
		if (pureHit > 0 && getMasterDate().isElementAvailable(10) && masterDate.contains(range1)
				|| masterDate.contains(range2)) {
			base.passedStep("Search results and Master date details displayed on advanced search screen");
		} else {
			base.failedStep("Search results and Master date details not present, please check the range");
		}
	}

	/**
	 * @author: Arun Created Date: 07/07/2022 Modified by: NA Modified Date: NA
	 * @throws InterruptedException
	 * @description: this method will verify the conceptual tile run result status
	 */
	public void verifySearchResultAndConceptualTileReturnResult() throws InterruptedException {
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		base.stepInfo("pure hit count for search result '" + pureHit + "'");
		if (pureHit > 0) {
			base.passedStep("Search results returned for the configured query");
		} else {
			base.failedStep("Search results not returned for the configured query");
		}
		int conceptPureHit = runAndVerifyConceptualSearch();

		if (getConceptualTileHit(conceptPureHit).Visible()) {
			base.passedStep(
					"Verified that Conceptual tile return the result for work product search in Advanced  Search Screen");
		} else {
			base.failedStep("Still conceptual results Loading on the Screen");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param SG
	 * @param UserType
	 * @param expectedUserType
	 * @param countComparision
	 * @param expectedDocCount
	 * @return
	 */
	public String bulkReleaseCountReturn(String SG, String UserType, String expectedUserType, Boolean countComparision,
			String expectedDocCount) {
		String TotalDocs = null;
		if (UserType.equalsIgnoreCase(expectedUserType)) {
			getBulkRelDefaultSecurityGroup_CheckBox(SG).Click();
			base.waitForElement(getBulkRelease_ButtonRelease());
			getBulkRelease_ButtonRelease().waitAndClick(20);
			base.waitForElement(getTotalSelectedDocs());
			TotalDocs = getTotalSelectedDocs().getText();
			base.waitForElement(getFinalizeButton());
			getFinalizeButton().waitAndClick(20);
			driver.waitForPageToBeReady();
			base.VerifySuccessMessageB("Records saved successfully");
			base.stepInfo("performing bulk release for " + SG + " docs count is " + TotalDocs);

			if (countComparision) {
				base.textCompareEquals(expectedDocCount, TotalDocs, "SG releasae Document count matches as expected",
						"Mis-match in document count");
			}
			base.stepInfo("Bulk Release Action done successfully");
		}

		return TotalDocs;
	}

	/**
	 * @Author jeevitha
	 * @Description : save search in pre-built Models
	 * @param searchName
	 * @param childSearchGrp
	 * @param defaultSearchGroup
	 * @param groupName
	 * @param childGroup
	 * @param defaultGroup
	 * @throws InterruptedException
	 */

	public void saveSearchInPreBuiltModels(String searchName, String childSearchGrp, String defaultSearchGroup,
			String groupName, boolean childGroup, boolean defaultGroup) throws InterruptedException {
		// SaveSearch
		saveSearchAction();

		base.waitForElement(getSaveAsNewSearchRadioButton());
		getSaveAsNewSearchRadioButton().waitAndClick(5);

		base.waitForElement(getCreatedNode(groupName));
		getCreatedNode(groupName).waitAndClick(3);

		if (getCurrentTabClosed().isElementAvailable(2)) {
			getCurrentTabClosedExpand().waitAndClick(5);
		} else {
			base.waitForElement(getsavesearch_overwrite());
			getsavesearch_overwrite().waitAndClick(5);
			base.waitTime(5);
			getSaveAsNewSearchRadioButton().waitAndClick(10);
			if (getCurrentTabClosed().isElementAvailable(2)) {
				getCurrentTabClosedExpand().waitAndClick(5);
			}
		}

		if (getCreatedNode(Input.preBuilt).isElementAvailable(5)) {
			getCreatedNode(Input.preBuilt).waitAndClick(10);
			if (getCurrentTabClosed().isElementAvailable(2)) {
				getCurrentTabClosedExpand().waitAndClick(5);
				System.out.println(" Expanded");

			} else {
				System.out.println("Already Expanded");
			}

			if (defaultGroup)
				if (getCreatedNode(defaultSearchGroup).isElementAvailable(5)) {
					getCreatedNode(defaultSearchGroup).waitAndClick(10);
				}
			if (childGroup) {
				if (getCurrentTabClosed().isElementAvailable(2)) {
					getCurrentTabClosedExpand().waitAndClick(5);
				}
				getCreatedNode(childSearchGrp).waitAndClick(10);

			}
		} else {
			base.failedStep("Pre-Built Model Tab is Not available");
		}

		driver.waitForPageToBeReady();
		getSaveSearch_Name().SendKeys(searchName);
		getSaveSearch_SaveButton().Click();
		base.VerifySuccessMessage("Saved search saved successfully");
		Reporter.log("Saved the search with name '" + searchName + "'", true);
		UtilityLog.info("Saved search with name - " + searchName);

	}

	public void verifyAllTilesResultsinAdvSrcScrn() throws InterruptedException {

		SoftAssert softAssert = new SoftAssert();
		// Getting Result From PureHit Tile
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		base.waitTime(60);
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		softAssert.assertNotNull(pureHit, "Pure Hit Tile Successfully Returned the Results");
		System.out.println("PureHit Count :" + pureHit);

		// Getting Result From Thread Count Tile
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getThreadedCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);

		int threadCount = Integer.parseInt(getThreadedCount().getText());
		softAssert.assertNotNull(threadCount, "Thread Tile Successfully Returned the Results");
		System.out.println("ThreadCount :" + threadCount);

		// Getting Result From NearDuplicate Tile
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNearDupeCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);

		int nearDuplicate = Integer.parseInt(getNearDupeCount().getText());
		softAssert.assertNotNull(nearDuplicate, "Near Duplicate Tile Successfully Returned the Results");
		System.out.println("Near Duplocate Count :" + nearDuplicate);

		// Getting Result From Family Count Tile
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFamilyCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);

		int familyMember = Integer.parseInt(getFamilyCount().getText());
		softAssert.assertNotNull(familyMember, "Family Member Tile Successfully Returned the Results");
		System.out.println("Family Member Count :" + familyMember);

	}

	public void verifyBellyBandOptions() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBellyBandTitleLabel().isElementAvailable(10);
			}
		}), Input.wait120);
		base.stepInfo("Belly Band PopUpSuccessfully Opened");

		if (getWhenAllResultsAreReadyPopUp().isElementAvailable(5)
				&& getWhenPureHitsAreReadyPopup().isElementAvailable(06)
				&& getIWantToWaitPopup().isElementAvailable(5)) {
			base.passedStep("All Three Options are Present in BellyBand When Search Goes BackGround");
			System.out.println("All Three Options are Present in BellyBand When Search Goes BackGround");

		} else {
			base.failedStep("All Three Options Not Present in BellyBand When Search Goes BackGround");
			System.out.println("All Three Options Not Present in BellyBand When Search Goes BackGround");
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @throws InterruptedException
	 * @descripton: To Verify Cloning project saved search terms
	 */
	public void verifySavedSearchTermsForCloningProject(String searchGroup) throws InterruptedException {
		base.waitForElement(getSavedSearchGroupName(searchGroup));
		getSavedSearchGroupName(searchGroup).waitAndClick(5);
		base.waitForElementCollection(getCounts());
		ElementCollection counts = getCounts();
		int size = counts.size();
		System.out.println(size);
		if (size > 3) {
			base.passedStep("Saved Search list contains more than 3 save searches");
		} else if (getSavedSearchTermsListEmpty().isElementAvailable(5) || size < 3) {

			basicContentSearch(Input.searchString1);
			saveSearchAtAnyRootGroup("Donot Delete1" + Utility.dynamicNameAppender(), searchGroup);
			driver.waitForPageToBeReady();
			base.selectproject();
			basicContentSearch(Input.testData1);
			driver.waitForPageToBeReady();
			base.waitTime(3);
			saveSearchAtAnyRootGroup("Donot Delete2" + Utility.dynamicNameAppender(), searchGroup);
			driver.waitForPageToBeReady();
			base.selectproject();
			basicContentSearch(Input.searchString2);
			base.waitTime(3);
			saveSearchAtAnyRootGroup("Donot Delete3" + Utility.dynamicNameAppender(), searchGroup);

			base.stepInfo("Saved search list is creted with more than 3 save searches");
		}

	}

	/**
	 * @author
	 * @Date: 07/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @param addPureHit
	 * @param addThreadedDocuments
	 * @param addNearDuplicates
	 * @param addFamilyMembers
	 * @param playConceptual
	 * @param addconceptuallySimilar
	 * @throws InterruptedException
	 */
	public void addingAllTilesToShoppingCart(String addPureHit, String addThreadedDocuments, String addNearDuplicates,
			String addFamilyMembers, String playConceptual, String addconceptuallySimilar) throws InterruptedException {

		if (addPureHit.equalsIgnoreCase("Yes")) {
			base.waitForElement(getCurrentPureHitAddBtn());
			getCurrentPureHitAddBtn().waitAndClick(5);
			base.stepInfo("Added pureHit");
		}
		if (addThreadedDocuments.equalsIgnoreCase("Yes")) {
			base.waitForElement(getThreadedDocumentsAddBtn());
			getThreadedDocumentsAddBtn().waitAndClick(5);
			base.stepInfo("Added Threaded doc count");
		}
		if (addNearDuplicates.equalsIgnoreCase("Yes")) {
			base.waitForElement(getNearDuplicatesAddBtn());
			getNearDuplicatesAddBtn().waitAndClick(5);
			base.stepInfo("Added Near Dupes Count");
		}
		if (addFamilyMembers.equalsIgnoreCase("Yes")) {
			base.waitForElement(getFamilyMembersAddBtn());
			getFamilyMembersAddBtn().waitAndClick(5);
			base.stepInfo("Added Family Members Count");
		}
		if (playConceptual.equalsIgnoreCase("Yes")) {
			base.waitForElement(getConceptualCountPlayButton());
			getConceptualCountPlayButton().waitAndClick(5);
			verifyConceptuallySimilarCount();
			base.stepInfo("Added Conceptual Similar count");
			if (addconceptuallySimilar.equalsIgnoreCase("Yes")) {
				base.waitForElement(getConceptuallySimilarAddBtn());
				getConceptuallySimilarAddBtn().waitAndClick(5);
				base.stepInfo("Added Conceptual Similar count");
			}
		}
	}

	/**
	 * @author
	 * @Date: 07/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description : perform Adv Search and AddToCart 'n' times
	 * @param limit
	 * @throws InterruptedException
	 */
	public void performAdvSearchandAddToCart(int limit) throws InterruptedException {
		for (int i = 1; i <= limit; i++) {
			base.stepInfo("Adding tiles for search " + i);
			base.waitTime(3);
			getModifyASearch_Current().waitAndClick(5);
			getAdvanceSearch_btn_Current().waitAndClick(5);
			addingAllTilesToShoppingCart("yes", "yes", "yes", "yes", "yes", "no");
		}
	}

	/**
	 * @author Raghuram.A
	 * @Date: 07/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description : remove All Added Tiles
	 */
	public void removeAllAddedTiles() {
		List<WebElement> Removetiles = getRemoveTilesBtn().FindWebElements();

		System.out.println("list size : " + Removetiles.size());
		for (int i = 0; i < Removetiles.size(); i++) {
			base.waitTime(2);
			Removetiles.get(i).click();
		}
		base.failedMessage("All the tiles in the shopping cart is removed.");
	}

	/**
	 * @author
	 * @Date: 07/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @param actualSequenceOfTiless
	 * @param tilesNameInSequence
	 * @description : verify Tiles Present In Expected Sequence
	 */
	public void verifyTilesPresentInSequence(List<String> actualSequenceOfTiless, String[] tilesNameInSequence) {
		for (int i = 0; i < actualSequenceOfTiless.size(); i++) {
			if (tilesNameInSequence[i].equalsIgnoreCase(actualSequenceOfTiless.get(i))) {
				System.out.println(actualSequenceOfTiless.get(i));
				base.stepInfo(i + 1 + " : Expected - " + tilesNameInSequence[i]);
				base.stepInfo(i + 1 + " : Actual - " + actualSequenceOfTiless.get(i));
			} else {
				base.failedStep("Tiles are not present in Sequence.");
			}
		}
		base.passedStep("Tiles are Present in Sequence.");
	}

	/**
	 * @author: Jayanthi
	 * @description: this method will get the doc count available under SG .
	 */
	public int verifyDocsCountAvailableInSg() {
		int expectedCount = 0;
		driver.waitForPageToBeReady();
		if (getCountUniqueDocId().isElementAvailable(10)) {
			String label = getCountUniqueDocId().getText();
			String countlabel = label.substring(label.indexOf(":"));
			expectedCount = Integer.parseInt(countlabel.replace(",", "").replace(": ", ""));
			base.stepInfo("Count available in selected security group " + expectedCount);

		} else {
			base.failedMessage("Uniques docs count under Selected SG is not displayed.");
		}
		return expectedCount;
	}

	/**
	 * @Author Jeevitha
	 */
	public void verifyWarningMessage(boolean search, boolean verifyHeader, int MessageNumber) {
		if (search) {
			if (getAdvanceSearch_btn_Current().isDisplayed()) {
				getAdvanceSearch_btn_Current().waitAndClick(10);
			} else if (getSearchButtonSec().isDisplayed()) {
				getSearchButtonSec().waitAndClick(10);
			}
		}

		if (verifyHeader) {
			if (getWarningAudioBlackListChar_Header().isDisplayed()) {
				String header = getWarningAudioBlackListChar_Header().getText();
				softAssert.assertEquals(header, "Possible Wrong Query Alert");
				base.stepInfo("Displayed Header is : " + header);
				System.out.println("Displayed Header is : " + header);
			}
		}

		if (MessageNumber == 1) {

			String msg = "Only alphanumeric characters and hyphens (-) can be specified in an audio search term";
			base.waitTime(2);
			base.waitForElement(getWarningAudioBlackListChar_Message());
			String actualMsg = getWarningAudioBlackListChar_Message().getText();
			System.out.println(actualMsg);
			base.stepInfo(actualMsg);

			softAssert.assertEquals(msg.replaceAll(" ", ""), actualMsg.replaceAll(" ", "").replaceAll("\n", ""));
		}

		if (MessageNumber == 2) {

			String msg = " Your query may contain an invalid Proximity Query. Any Double Quoted phrases as part of a Proximity Query must also be wrapped in Parentheses, e.g. \"Term1 (\"Specific Phrase\")\"~4.";
			base.waitTime(2);
			base.waitForElement(getWarningAudioBlackListChar_Message());
			String actualMsg = getWarningAudioBlackListChar_Message().getText();
			System.out.println(actualMsg);

			softAssert.assertEquals(msg.replaceAll(" ", ""), actualMsg.replaceAll(" ", "").replaceAll("\n", ""));
			base.passedStep(actualMsg);
		}

		if (MessageNumber == 3) {

			String msg = " Double quotes are missing in your search query. Please correct the query to include double quotes.";
			base.waitTime(2);
			base.waitForElement(getWarningAudioBlackListChar_Message());
			String actualMsg = getWarningAudioBlackListChar_Message().getText();
			System.out.println(actualMsg);

			softAssert.assertEquals(msg.replaceAll(" ", ""), actualMsg.replaceAll(" ", "").replaceAll("\n", ""));
			base.passedStep(actualMsg);
		}
		if (MessageNumber == 4) {
			String msg = "Your query has multiple potential syntax issues.  1. Your query contains the ~ (tilde) character which does not immediately follow a double-quoted set of terms or is not immediately followed by a numeric value . If you are trying to run a proximity search, please use appropriate proximity query syntax e.g. \"Term1 Term2\"~4. Note there is no space before or after the tilde.  2. Your query contains two or more arguments that do not have an operator between them. In Sightline, each term without an operator between them will be treated as A OR B, not \"A B\" as an exact phrase. If you want to perform a phrase search, wrap the terms in quotations (ex. \"A B\" returns all documents with the phrase A B).  Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			base.waitTime(2);
			base.waitForElement(getWarningMsg());
			String actualMsg = getWarningMsg().getText();
			System.out.println(actualMsg);
			base.stepInfo(actualMsg);

			softAssert.assertEquals(msg.replaceAll(" ", ""), actualMsg.replaceAll(" ", "").replaceAll("\n", ""));
		}
		if (MessageNumber == 5) {
			String msg = "Your query contains a ~(tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)  Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			System.out.println(getQueryAlertGetText().getText().replaceAll("\n", " "));
			Assert.assertEquals(msg.replaceAll(" ", ""),
					getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
		}
		softAssert.assertAll();
	}

	/**
	 * @Author Jeevitha
	 */
	public void verifyCopyToNewSearch() {
		int initialSize = getSearchPanelCount().size();

		base.waitForElement(getAdvSearchCopyToNewSearch());
		getAdvSearchCopyToNewSearch().waitAndClick(10);
		base.stepInfo("Clicked : copy to new search Btn");
		driver.waitForPageToBeReady();
		int afterSize = getSearchPanelCount().size();
		if (initialSize < afterSize) {
			base.passedStep("New Search is Created");
		} else {
			base.failedStep("New Search is not Created");
		}
	}

	/**
	 * @author Jayanthi
	 * @Description: Selecting assignments with distributed To/Status/Data Range
	 *               filters.
	 */
	public void selectAssignmentAndReviewers_Status_dateRange(String assignMentName, String reviewer, String RMU,
			String PA, String status, boolean date) throws InterruptedException, AWTException {

		base.waitForElement(getWP_assignmentsBtn());
		getWP_assignmentsBtn().Click();

		base.waitForElement(selectReviewerInAssgnWP(reviewer));
		selectReviewerInAssgnWP(reviewer).waitAndClick(10);
		if (RMU != null) {
			base.waitForElement(selectReviewerInAssgnWP(RMU));
			selectReviewerInAssgnWP(RMU).waitAndClick(10);
		}
		if (PA != null) {
			base.waitForElement(selectReviewerInAssgnWP(PA));
			selectReviewerInAssgnWP(PA).waitAndClick(10);
		}

		base.passedStep("The distributed list of users are selected.");

		driver.scrollingToBottomofAPage();
		selectStatusInAssgnWp(status).waitAndClick(2);
		if (date) {
			selectDate("From Date");
			selectDate("To Date");
		}
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if (iterable_element.getText().contains(assignMentName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				System.out.println(iterable_element.getText());
				UtilityLog.info(iterable_element.getText());
				iterable_element.click();
			}
		}
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		driver.scrollPageToTop();
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify Error Messagein save search popup
	 * @param expectedMsg
	 */

	public void verifyErrorMsgInSavePopUp(String expectedMsg) {

		if (getErrorMsg().isElementAvailable(10)) {
			String actualMsg = getErrorMsg().getText();
			String passMsg = "Displayed Error Msg : " + actualMsg;
			String failMsg = "Error Message is Not As Expected";
			base.compareTextViaContains(actualMsg, expectedMsg, passMsg, failMsg);
		} else {
			base.failedStep("Error Message is Not Displayed");
		}
	}

	/**
	 * @author Jayanthi.Ganesan This method will verify if user selects un-assign
	 *         option then only existing tab displayed with assignments to select
	 *         for un-assign.
	 */
	public void verifyUnAssignOptions() {
		base.waitForElement(getUnAssignRadioBtn());
		base.stepInfo("Assign/UnAssign pop up displayed");
		getUnAssignRadioBtn().Click();
		String BulkAssign_Existing = getBulkAssignSelectedExistingAssignment().GetAttribute("style");
		System.out.println(BulkAssign_Existing);
		String BulkAssign_New = getBulkAssign_NewAssignment().GetAttribute("style");
		System.out.println(BulkAssign_New);
		String BulkUnAssign_Existing = getUnassign_ExistingAssignButton().GetAttribute("style");
		System.out.println(BulkUnAssign_Existing);
		if (BulkAssign_Existing.contains("display: none;") && BulkAssign_New.contains("display: none;")
				&& BulkUnAssign_Existing.equals("")) {
			base.passedStep("If user click unassign tab Only existing assign tab is displayed");
			List<WebElement> elementList = null;
			elementList = getUnassign_ExistingAssignments().FindWebElements();
			if (elementList.size() > 1) {
				base.passedStep(" Existing Assignments tab  loaded and " + " all the existing assignments  displayed");
			} else {
				base.failedStep(
						" Existing Assignments tab not loaded and " + " all the existing assignments not displayed");
			}
		} else {
			base.failedStep("If user click unassign tab Only existing assign tab is displayed");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : enters production Date/Bates Range in Work Product
	 * @param productionDate
	 * @param Bates
	 * @param FromDate
	 * @param FromBates
	 * @param ToBates
	 * @throws InterruptedException
	 */
	public void selectWPWithProdBatesAndDateRange(boolean productionDate, boolean Bates, String FromDate,
			String FromBates, String ToBates) throws InterruptedException {

		base.waitForElement(getProductionBtn());
		getProductionBtn().waitAndClick(5);
		if (productionDate) {
			driver.waitForPageToBeReady();
			date("From Date").SendKeys(FromDate);
			selectDate("To Date");
		}

		if (Bates) {
			driver.waitForPageToBeReady();
			base.waitForElement(getFromBatesBtn());
			getFromBatesBtn().SendKeys(FromBates);

			driver.waitForPageToBeReady();
			getToBatesBtn().SendKeys(ToBates);
		}
		driver.scrollingToBottomofAPage();
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(5);
		base.stepInfo("Inserted Query");

		driver.scrollPageToTop();
	}

	/**
	 * @AUthor Jeevitha
	 * @Description :returns configured query in dome value
	 * @modified date: 26/08/2022
	 * @return
	 */
	public String configuredQuery() {
		String searchTerm = "";
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		if (getSearchTerm().isElementAvailable(5)) {
			searchTerm = getSearchTerm().GetAttribute("value");
		} else if (getModifiableSavedSearchQueryAS().isElementAvailable(5)) {
			searchTerm = getModifiableSavedSearchQueryAS().getText();
		}
		base.passedStep("Configured query is : " + searchTerm);
		return searchTerm;

	}

	/**
	 * @author S
	 * @Date: 02/08/22
	 * @Description :handle I Want To Wait In BellyBandPopup
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @param waitTime
	 */
	public void handleIWantToWaitInBellyBandPopup(int waitTime) {
		if (getIWantToWaitPopup().isElementAvailable(waitTime)) {
			getIWantToWaitPopup().waitAndClick(3);
			System.out.println("*** Clicked 'I Want To Wait' Button ***");
		}
	}

	/**
	 * @author
	 * @Description :handleWhenPureHitsAreReadyInBellyBandPopup
	 * @Date: 02/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @param waitTime
	 */
	public String handleWhenPureHitsAreReadyInBellyBandPopup(int waitTime) {
		String ID = null;
		if (getWhenPureHitsAreReadyPopup().isElementAvailable(waitTime)) {
			getWhenPureHitsAreReadyPopup().waitAndClick(3);
			System.out.println("*** Clicked 'When PureHit Are Ready' Button ***");
			ID = getWhenAllResultsAreReadyID().getText();
			base.stepInfo(" When Pure Hits are ready Clicked And Generated ID is: " + ID);
			getBulkTagConfirmationButton().waitAndClick(3);
		} else {
			base.stepInfo("Page Loaded and PopUp Did not Appear");
		}
		return ID;
	}

	/**
	 * @author
	 * @Date: 02/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @param tagNameList
	 */
	public void selectMultipleTagsInASwp(List<String> tagNameList) {

		base.waitForElement(getWorkproductBtn());
		getWorkproductBtn().Click();
		base.stepInfo("Switched to Advanced search - Work product");
		base.waitForElement(getWP_TagBtn());
		getWP_TagBtn().Click();
		driver.waitForPageToBeReady();
		System.out.println(getTree().FindWebElements().size());
		UtilityLog.info(getTree().FindWebElements().size());
		for (String tagName : tagNameList) {
			for (WebElement iterable_element : getTree().FindWebElements()) {
				if (iterable_element.getText().contains(tagName)) {
					new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
					driver.scrollingToBottomofAPage();
					iterable_element.click();
					break;
				}
			}
		}
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().Click();
		driver.scrollPageToTop();
	}

	/**
	 * @author: Arun Created Date: 05/08/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform bulk release docs to two security
	 *               group
	 * @param secGroup1
	 * @param secGroup2
	 */
	public void bulkReleaseToMultipleSecurityGroups(String secGroup1, String secGroup2) {

		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(10);
		try {
			base.waitForElement(getBulkReleaseAction());
			getBulkReleaseAction().waitAndClick(5);
		} catch (Exception e) {

			getBulkReleaseActionDL().waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		base.waitForElement(getBulkRelDefaultSecurityGroup_CheckBox(secGroup1));
		getBulkRelDefaultSecurityGroup_CheckBox(secGroup1).waitAndClick(10);
		base.waitForElement(getBulkRelDefaultSecurityGroup_CheckBox(secGroup1));
		getBulkRelDefaultSecurityGroup_CheckBox(secGroup2).waitAndClick(10);

		base.waitForElement(getBulkRelease_ButtonRelease());
		getBulkRelease_ButtonRelease().waitAndClick(5);

		if (getTallyContinue().isElementAvailable(5)) {
			getTallyContinue().waitAndClick(5);
		}
		base.waitForElement(getFinalizeButton());
		if (getFinalizeButton().isElementAvailable(10)) {
			getFinalizeButton().waitAndClick(5);
		}
		base.VerifySuccessMessageB("Records saved successfully");
	}

	/**
	 * @author S
	 * @createdOn : 8/9/22
	 * @param savedSearchList
	 * @description : insert Multiple SavedSearch Results In Wp
	 */
	public void insertMultipleSavedSearchResultsInWp(List<String> savedSearchList) {

		base.waitForElement(getWorkproductBtn());
		getWorkproductBtn().Click();
		base.stepInfo("Switched to Advanced search - Work product");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchBtn().Visible();
			}
		}), Input.wait60);
		getSavedSearchBtn().Click();
		driver.scrollingToBottomofAPage();
		for (String SaveName : savedSearchList) {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getSelectSavedSearchResult(SaveName).waitAndClick(10);
		}
		// added on 16-8-21
		base.waitForElement(getMetaDataInserQuery());
		getMetaDataInserQuery().waitAndClick(15);
		// Click on Search button
		driver.scrollPageToTop();
	}

	/**
	 * @author Jayanthi.Ganesan This method Perform unassign docs from a assignment
	 * @param assignName
	 */
	public void UnAssignExistingAssignment(String assignName) {
		base.waitForElement(getUnAssignRadioBtn());
		base.stepInfo("Assign/UnAssign pop up displayed");
		getUnAssignRadioBtn().Click();

		getUnassign_ExistingAssignButton().Click();
		getExistingAssignmentToUnAssign(assignName).ScrollTo();
		getExistingAssignmentToUnAssign(assignName).Click();
		base.waitTillElemetToBeClickable(getExistingAssignmentToUnAssign(assignName));
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
		bc.stepInfo(" Docs Unassigned from  " + assignName);

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param sourceDocIds
	 * @Description Search a list of document with source id's
	 */
	public void basicSourceDocIdsSearch(String[] sourceDocIds) {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		try {
			driver.waitForPageToBeReady();

			for (String id : sourceDocIds) {
				base.waitForElement(getBasicSearch_MetadataBtn());
				driver.waitForPageToBeReady();
				getBasicSearch_MetadataBtn().waitAndClick(5);

				driver.waitForPageToBeReady();
				base.waitForElement(getSelectMetaData());
				getSelectMetaData().selectFromDropdown().selectByValue("SourceDocID");

				driver.waitForPageToBeReady();
				base.waitForElement(getMetaDataSearchText1());
				getMetaDataSearchText1().SendKeys(id);

				base.waitForElement(getMetaDataInserQuery());
				getMetaDataInserQuery().waitAndClick(5);
			}

			base.waitForElement(getSearchButton());
			getSearchButton().waitAndClick(10);
			if (base.getYesBtn().isElementAvailable(2)) {
				base.getYesBtn().waitAndClick(5);
			}
			driver.waitForPageToBeReady();
			base.stepInfo("list of source docid's searched");

		} catch (Exception e) {
			base.failedStep("Query is not found");
		}

	}

	/**
	 * @author Sakthivel date: 27/01/2021 Modified date: NA
	 * @Description: Assign EmailDuplicate document to bulk folder
	 */
	public void bulkFolderDuplicateEmailDocs(String folderName) throws InterruptedException {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().waitAndClick(5);
		base.waitForElement(getBulkFolderAction());
		getBulkFolderAction().waitAndClick(5);
		base.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(20);
		base.waitForElement(getEnterFolderName());
		getEnterFolderName().SendKeys(folderName);
		base.waitForElement(getFolderAllRoot());
		getFolderAllRoot().waitAndClick(5);
		base.waitForElement(getPropagateFoldersCheckBox(4));
		getPropagateFoldersCheckBox(4).waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();
		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		UtilityLog.info("Bulk folder is done, folder is : " + folderName);
		Reporter.log("Bulk folder is done, folder is : " + folderName, true);
		driver.getWebDriver().navigate().refresh();
	}

	/**
	 * @author
	 * @param waitTime
	 * @description : tallyContinue click
	 */
	public void tallyContinue(int waitTime) {
		if (getTallyContinue().isElementAvailable(waitTime)) {
			getTallyContinue().waitAndClick(5);
		}
	}

	/**
	 * @author
	 * @param pairOfAssignmentNameAndcodingForm
	 * @description : bulkAssignWithMultipleNewAssignmentWithPersistantHit
	 */
	public void bulkAssignWithMultipleNewAssignmentWithPersistantHit(String[][] pairOfAssignmentNameAndcodingForm) {

		for (int i = 0; i < pairOfAssignmentNameAndcodingForm.length; i++) {
			String assignmentName = pairOfAssignmentNameAndcodingForm[i][0];
			String codingForm = pairOfAssignmentNameAndcodingForm[i][1];

			navigateToSessionSearchPageURL();
			AssignmentsPage assignPage = new AssignmentsPage(driver);
			driver.waitForPageToBeReady();
			if (getPureHitAddButton().isDisplayed()) {
				base.waitForElement(getPureHitAddButton());
				base.waitTime(2);
				getPureHitAddButton().Click();
			} else {
				System.out.println("Pure hit block already moved to action panel");
				UtilityLog.info("Pure hit block already moved to action panel");
			}
			base.waitTime(3);
			base.waitForElement(getBulkActionButton());
			getBulkActionButton().Click();
			base.waitForElement(getBulkAssignAction());
			getBulkAssignAction().Click();
			UtilityLog.info("performing bulk assign");
			driver.waitForPageToBeReady();
			driver.waitForPageToBeReady();
			base.waitForElement(getAssgn_NewAssignmnet());
			getAssgn_NewAssignmnet().isElementAvailable(15);
			base.waitTillElemetToBeClickable(getAssgn_NewAssignmnet());
			getAssgn_NewAssignmnet().waitAndClick(5);
			base.waitForElement(getbulkassgnpopup());
			getbulkassgnpopup().isElementAvailable(10);
			try {
				base.waitForElement(getContinueBulkAssign());
				getContinueBulkAssign().isElementAvailable(15);
				base.waitTillElemetToBeClickable(getContinueBulkAssign());
				getContinueBulkAssign().waitAndClick(20);
			} catch (Exception e) {
				getPersistantHitCheckBox().isElementAvailable(15);
				getPersistantHitCheckBox().Click();
				base.waitForElement(getContinueBulkAssign());
				getContinueBulkAssign().isElementAvailable(15);
				base.waitTillElemetToBeClickable(getContinueBulkAssign());
				getContinueBulkAssign().waitAndClick(20);
			}
			base.waitForElement(getAssgn_TotalCount());
			getAssgn_TotalCount().isElementAvailable(10);
			base.waitForElement(getFinalizeButton());
			base.waitTillElemetToBeClickable(getFinalizeButton());
			getFinalizeButton().isElementAvailable(10);
			getFinalizeButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			try {
				base.waitForElement(getAssignmentName());
				getAssignmentName().isElementAvailable(15);
				getAssignmentName().SendKeys(assignmentName);
			} catch (Exception e) {
				getAssignmentName().isElementAvailable(15);
				base.waitForElement(getAssignmentName());
				getAssignmentName().Clear();
				getAssignmentName().SendKeys(assignmentName);
			}
			getParentAssignmentGroupName().isElementAvailable(10);
			getParentAssignmentGroupName().isDisplayed();
			base.waitForElement(getSelectedClassification());
			getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
			assignPage.SelectCodingform(codingForm);
			base.waitForElement(getAssignmentSaveButton());
			base.waitTillElemetToBeClickable(getAssignmentSaveButton());
			getAssignmentSaveButton().waitAndClick(5);
			try {
				if (getAssignmentErrorText().isElementAvailable(5)) {
					driver.waitForPageToBeReady();
					base.waitForElement(getAssignmentName());
					getAssignmentName().SendKeys(assignmentName);
					base.waitForElement(getAssignmentSaveButton());
					getAssignmentSaveButton().waitAndClick(5);
				}
			} catch (org.openqa.selenium.NoSuchElementException e) {
				e.printStackTrace();
			}
			System.out.println("Assignment " + assignmentName + " created with CF " + codingForm);
			base.stepInfo("Assignment " + assignmentName + " created with CF " + codingForm);
			UtilityLog.info("Assignment " + assignmentName + " created with CF " + codingForm);
		}
	}

	/**
	 * @author
	 * @description : UnAssignMultipleExistingAssignments
	 * @param listOfAssignments
	 */
	public void UnAssignMultipleExistingAssignments(List<String> listOfAssignments) {

		base.waitForElement(getUnAssignRadioBtn());
		base.stepInfo("Assign/UnAssign pop up displayed");
		getUnAssignRadioBtn().Click();

		getUnassign_ExistingAssignButton().Click();
		for (int i = 0; i < listOfAssignments.size(); i++) {
			base.waitForElement(getAssignmentToUnAssign(listOfAssignments.get(i)));
			getAssignmentToUnAssign(listOfAssignments.get(i)).Click();
		}
		base.waitForElement(getContinueBulkAssign());
		getContinueBulkAssign().isElementAvailable(15);
		base.waitTillElemetToBeClickable(getContinueBulkAssign());
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
		for (int i = 0; i < listOfAssignments.size(); i++) {
			bc.stepInfo(" Docs Unassigned from  " + listOfAssignments.get(i));
		}
	}

	/**
	 * @author: Arun Created Date: 06/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check search result count for configured query
	 */
	public void verifySearchResultReturnsForConfiguredQuery(int purehitCount) {
		if (purehitCount > 0) {
			base.passedStep("Docs returned for the configured query");
		} else {
			base.failedStep("Docs not returned for the configured query");
		}
	}

	/**
	 * @author
	 * @date: 9/13/21 NA
	 * @Modifieddate: N/A
	 * @Modifiedby:
	 * @Description :
	 * @param searchResult
	 */
	public void advanceWorkProductSearchResult(String searchResult) {
		getWorkproductBtnC().waitAndClick(5);
		getSavedSearchResult().waitAndClick(5);

		driver.scrollingToBottomofAPage();
		base.waitForElement(getSelectWorkProductSSResults(searchResult));
		getSelectWorkProductSSResults(searchResult).waitAndClick(5);
		getInsertInToQueryBtn().waitAndClick(10);
	}

	/**
	 * @author
	 * @date: 9/13/21 NA
	 * @Modifieddate: N/A
	 * @Modifiedby:
	 * @Description :
	 * @param SearchStrings
	 * @param operator
	 */
	public void advancedMultipleContentSearchWithOperator(List<String> SearchStrings, String operator) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDatabtn().Visible();
			}
		}), Input.wait30);
		getContentAndMetaDatabtn().Click();
		for (int i = 0; i < SearchStrings.size(); i++) {
			if (i != 0) {
				selectOperator(operator);
			}
			base.waitForElement(getAdvancedContentSearchInputCurrent());
			try {
				getAdvancedContentSearchInputCurrent().SendKeys(SearchStrings.get(i));
			} catch (Exception e) {
				base.waitTime(5);
				getAdvancedContentSearchInputCurrent().SendKeys(SearchStrings.get(i));
			}
		}
	}

	/**
	 * @author Jayanthi.Ganesan This method will verify if user selects assign
	 *         option then existing assignment and new assignment tab displayed.
	 */
	public void verifyBulkAssignOptions() {
		base.waitForElement(getUnAssignRadioBtn());
		base.stepInfo("Assign/UnAssign pop up displayed");
		String BulkAssign_Existing = getBulkAssignSelectedExistingAssignment().GetAttribute("style");
		System.out.println(BulkAssign_Existing);
		String BulkAssign_New = getBulkAssign_NewAssignment().GetAttribute("style");
		System.out.println(BulkAssign_New);
		String BulkUnAssign_Existing = getUnassign_ExistingAssignButton().GetAttribute("style");
		System.out.println(BulkUnAssign_Existing);
		if (BulkAssign_Existing.contains("") && BulkAssign_New.contains("")
				&& BulkUnAssign_Existing.equals("display: none;")) {
			base.passedStep("If user click assign tab Only existing assignment tab and new assignment is displayed");
		} else {
			base.failedStep(
					"If user click assign tab  existing assign tab and new assignment is not displayed as expected.");
		}
	}

	/**
	 * @author Raghuram.A
	 * @description : add pure Hit if available
	 */
	public void checkAddPureHit() {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(1)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param action
	 * @description : performBulkActionM
	 */
	public void performBulkActionM(String action) {
		// Perform Bulk Action
		base.waitForElement(getBulkActionButton());
		getBulkActionButton().Click();
		base.waitForElement(performBulkAction(action));
		performBulkAction(action).waitAndClick(5);
		base.stepInfo("performing " + action + " assign");
		UtilityLog.info("performing " + action + " assign");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Raghuram.A
	 * @description : unassign radio btn Click
	 */
	public void unassignClick() {
		try {
			base.waitForElement(getUnAssignRadioBtn());
			base.stepInfo("Assign/UnAssign pop up displayed");
			getUnAssignRadioBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram.A
	 * @param status
	 * @param assignmentType
	 * @description : verifyContinuButtonIsStatus
	 */
	public void verifyContinuButtonIsStatus(String status, String assignmentType) {

		if (status.equalsIgnoreCase("Disabled")) {
			// Verify doc count load
			if (getbulkAssignTotalCountLoad().isElementAvailable(3)) {
				base.stepInfo("Total doc count is loading...");
				base.printResutInReport(base.ValidateElement_PresenceReturn(getContinueBtnDisabled()),
						"Continue Button is in disabled state", "Continue Button is in enabled state", "Pass");
			} else {
				base.failedMessage("Total doc count loaded");
			}
		} else if (status.equalsIgnoreCase("Enabled")) {
			// Wait for Some time and check count loaded
			if (!getbulkAssignTotalCountLoad().isElementAvailable(5)) {
				base.stepInfo("Total doc count is displayed : " + getTotalDocCount(assignmentType).getText());
				base.printResutInReport(base.ValidateElement_PresenceReturn(getContinueBtnDisabled()),
						"Continue Button is in enabled state", "Continue Button is in disabled state", "Fail");
			} else {
				base.failedMessage("Total doc count not yet loaded");
			}
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param TagName
	 * @param docCount[doc count to be verified ] This method will verify the total
	 *                     document selected in bulk tag pop up
	 * @throws InterruptedException
	 */

	public void bulkTag_FluctuationVerify(String TagName, String docCount) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		String actualCount_continue = getContinueCount().getText();
		base.textCompareEquals(actualCount_continue, docCount, "Count didn't fluctuate",
				"Count is not same as expected.");

		base.stepInfo("Now clicking new Tag tab");
		base.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(10);

		String passMsg = " Count didn't fluctuate and count didn't " + "even change after selecting the New Tag.";
		String actualCount_AfterNewTabClick = getContinueCount().getText();
		base.textCompareEquals(actualCount_continue, actualCount_AfterNewTabClick, passMsg,
				"Count is not same as expected.");
		base.waitForElement(getEnterTagName());
		getEnterTagName().SendKeys(TagName);
		base.waitForElement(getTagsAllRoot());
		getTagsAllRoot().Click();

		getContinueButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		if (docCount != null) {
			String actualCount_final = getFinalCount().getText();
			base.textCompareEquals(actualCount_final, docCount,
					"The Count matching with count in " + "Like Document Popup.",
					"The Count not match with count in Like Document Popup.");
		}

	}

	/**
	 * this method verifies the Total docs selected count displayed in bulk folder
	 * pop up.
	 * 
	 * @param folderName
	 * @param docCount
	 * @throws InterruptedException
	 */
	public void bulkFolder_FluctuationVerify(String folderName, String docCount) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		String actualCount_continue = getContinueCount().getText();
		base.textCompareEquals(actualCount_continue, docCount, "Count didn't fluctuate",
				"Count is not same as expected.");

		base.stepInfo("Now clicking new Folder tab");
		base.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(10);

		String passMsg = " Count didn't fluctuate and count didn't " + "even change after selecting the New Folder.";
		String actualCount_AfterNewTabClick = getContinueCount().getText();
		base.textCompareEquals(actualCount_continue, actualCount_AfterNewTabClick, passMsg,
				"Count is not same as expected.");

		base.waitForElement(getEnterFolderName());
		getEnterFolderName().SendKeys(folderName);

		base.waitForElement(getFolderAllRoot());
		getFolderAllRoot().Click();
		getContinueButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		if (docCount != null) {
			String actualCount_final = getFinalCount().getText();
			base.textCompareEquals(actualCount_final, docCount,
					"The Count matching with count in " + "Like Document Popup.",
					"The Count not match with count in Like Document Popup.");
		}

	}

	/**
	 * @Author Jeevitha
	 * @Description : return total no od documents i Project
	 * @return
	 */
	public int verifyNoOfDocsInProject() {
		base.waitForElement(getNoOfDocInProject());
		String fullText = getNoOfDocInProject().getText();
		String[] SplitText = fullText.split(":");

		int docCount = Integer.parseInt(SplitText[1].trim().replace(",", ""));
		System.out.println(docCount);
		base.stepInfo("Total No Of Doc's In Project is : " + docCount);
		return docCount;
	}

	/**
	 * @Author Jeevitha
	 * @param assignmentName
	 * @param selectAction
	 * @return
	 */
	public String verifyToolTipTextInAssignUnassignPopUp(String assignmentName, String selectAction) {
		Element assignmentElement = null;
		base.waitForElement(getbulkassgnpopup());
		if (selectAction.equalsIgnoreCase("Assign")) {
			base.waitForElement(getBulkAssignAssignDocumentsButton());
			getBulkAssignAssignDocumentsButton().waitAndClick(5);
			assignmentElement = getSelectAssignmentExisting(assignmentName);
		} else if (selectAction.equalsIgnoreCase("Unassign")) {
			base.waitForElement(getBulkUntagbutton());
			getBulkUntagbutton().waitAndClick(5);
			assignmentElement = getExistingAssignmentToUnAssign(assignmentName);
		}

		Actions action = new Actions(driver.getWebDriver());
		base.waitForElement(assignmentElement);
		base.waitTillElemetToBeClickable(assignmentElement);
		action.moveToElement(assignmentElement.getWebElement()).clickAndHold().build().perform();
		base.waitTime(3);
		base.waitForElement(getToolTipTextInAssignUnassignPopUp());
		String assignmentNameInToolTip = getToolTipTextInAssignUnassignPopUp().getText();
		base.textCompareEquals(assignmentName, assignmentNameInToolTip,
				"Expected AssignmentName : '" + assignmentName + "' match with the  AssignmentName In Tool Tip : '"
						+ assignmentNameInToolTip + "'",
				"Expected AssignmentName : '" + assignmentName
						+ "' Doesn't match with the  AssignmentName In Tool Tip : '" + assignmentNameInToolTip + "'");
		return assignmentNameInToolTip;
	}

	public void bulkAssignForMultipleExistingAssignments(List<String> listOfAssignments) {
		driver.waitForPageToBeReady();
		if (getPureHitAddButton().isElementAvailable(1)) {
			getPureHitAddButton().Click();
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);

		getBulkActionButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction().Visible();
			}
		}), Input.wait30);
		getBulkAssignAction().Click();
		UtilityLog.info("performing bulk assign");
		driver.waitForPageToBeReady();
		for (String assignment : listOfAssignments) {
			base.waitForElement(getSelectAssignmentExisting(assignment));
			driver.waitForPageToBeReady();
			getSelectAssignmentExisting(assignment).Click();
		}
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getContinueButton());
		getContinueButton().waitAndClick(20);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		for (String assignment : listOfAssignments) {
			UtilityLog.info("Bulk assign is done, assignment is : " + assignment);
			Reporter.log("Bulk assign is done, assignment is : " + assignment, true);
		}
	}

	/**
	 * @author Jayanthi.ganesan Modified date-18/10/21
	 * @description To finalize the assignment after bulk assigning the documents to
	 *              assignment
	 */
	public void verifyDocsFluctuation_BulkAssign(String docCount) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount_ExistingAssign().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		String actualCount_continue = getContinueCount_ExistingAssign().getText();
		base.textCompareEquals(actualCount_continue, docCount, "Count didn't fluctuate",
				"Count is not same as expected.");

		base.stepInfo("Now clicking new assignment tab");

		getBulkAssign_NewAssignment().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCountAssign().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		String passMsg = " Count didn't fluctuate and count didn't " + "even change after selecting the New assign.";
		String actualCount_AfterNewTabClick = getContinueCountAssign().getText();
		base.textCompareEquals(actualCount_continue, actualCount_AfterNewTabClick, passMsg,
				"Count is not same as expected.");

		getContinueBulkAssign().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssgn_TotalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		base.waitForElement(getAssgn_TotalCount());
		String actualCount_final = getAssgn_TotalCount().getText();
		base.textCompareEquals(actualCount_final, docCount,
				"The Count matching with count in " + "Like Document Popup.",
				"The Count not match with count in Like Document Popup.");

	}

	/**
	 * @Author
	 * @param optionToSelect
	 * @param verifyTextBoxLableOfSampleMethod
	 * @param verifyAllOptionsInDropDown
	 */
	public void changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(String optionToSelect,
			boolean verifyTextBoxLableOfSampleMethod, boolean verifyAllOptionsInDropDown) {

		if (verifyAllOptionsInDropDown) {
			String listOfSampleMethodOptions[] = { "Count of Selected Docs", "Percent of Selected Docs",
					"Parent Level Docs Only", "Inclusive Email" };
			for (String SampleMethodOption : listOfSampleMethodOptions) {
				base.ValidateElement_Presence(getSelectSampleMethodOptions(SampleMethodOption),
						"sample Method Option '" + SampleMethodOption + "'");
			}
		}

		getSelectSampleMethod().selectFromDropdown().selectByVisibleText(optionToSelect);
		;

		if (verifyTextBoxLableOfSampleMethod) {

			if (optionToSelect.equals("Count of Selected Docs")) {
				String actualTextBoxLable = getTextBoxLableOfSampleMethod().getText();
				String expectedTextBoxLable = "Number to Assign:";
				base.textCompareEquals(expectedTextBoxLable, actualTextBoxLable,
						"Verified that label is changed to \"Number to Assign\"",
						"actual lable Text doesn't match with expected lable Text");
			} else if (optionToSelect.equals("Percent of Selected Docs")) {
				String actualTextBoxLable = getTextBoxLableOfSampleMethod().getText();
				String expectedTextBoxLable = "Percentage to Assign:";
				base.textCompareEquals(expectedTextBoxLable, actualTextBoxLable,
						"Verify that Label is changed to \"Percentage to Assign\"",
						"actual lable Text doesn't match with expected lable Text");
			} else if (optionToSelect.equals("Parent Level Docs Only") || optionToSelect.equals("Inclusive Email")) {
				softAssert.assertEquals(getTextBoxLableRemovedOfSampleMethod().isElementAvailable(5), true);
				softAssert.assertAll();
				base.passedStep("Verified that label is removed.");
			}
		}

	}
	
	/**
	 * @author sowndarya
	 */
	public void checkingStatusUsingRefresh(String backGroundID,String status) {
		driver.waitForPageToBeReady();

		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			if (getStatusText(backGroundID,status).isElementAvailable(15)) {
				
                base.stepInfo("waiting for status"+status);
                break;
		}
			else {
                driver.Navigate().refresh();
                System.out.println("Refresh");
                driver.waitForPageToBeReady();
            }
		}
		
	}
	
	/**
	 * @author sowndarya
	 */
	public void advancedNewContentSearchNotPureHit(String SearchString) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNewSearchButton().Visible() && getNewSearchButton().Enabled();
			}
		}), Input.wait30);
		driver.scrollPageToTop();
		getNewSearchButton().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContentAndMetaDataBtn().Visible() && getContentAndMetaDataBtn().Enabled();
			}
		}), Input.wait30);
		getContentAndMetaDataBtn().waitAndClick(5);
		// Enter seatch string
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterStringInBSCurrent().Visible();
			}
		}), Input.wait30);
		getEnterStringInBSCurrent().SendKeys(SearchString);
		// Click on Search button
		getQuerySearchBtn().Click();
	}
	
	

}
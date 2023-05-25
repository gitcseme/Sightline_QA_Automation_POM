package pageFactory;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.Log;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class SavedSearch {

	Driver driver;
	SessionSearch search;
	BaseClass base;
	AssignmentsPage assgnpage;
	SoftAssert softAssertion;
	SecurityGroupsPage sg;
	SearchTermReportPage searhTRpage;
	DocListPage dcPage;
	ReportsPage report;
	TagsAndFoldersPage tagsAndFolderPage;
	LoginPage login;

	// Variables
	String searchID;
	String searchIDtoCompare;
	String searchRowDetails;
	String childNodeRowDetails;

	public Element getSavedSearch_SearchName() {
		return driver.FindElementByXPath("//input[@id='txtSearchName']");
	}

	public Element getSavedSearch_ApplyFilterButton() {
		return driver.FindElementById("btnApplyFilter");
	}

	public Element getSavedSearch_name(String serachName) {
		return driver.FindElementById("//td[contains(text(),'" + serachName + "')]");
	}

	public Element getSelectWithNameC(String serachName) {
		return driver.FindElementByXPath("//td[contains(text(),'" + serachName + "')]/parent::tr/td[1]");
		// return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody//tr[td='"
		// + serachName + "']/td[1]//following::i");
	}

	// Xpath reverted back
	public Element getSelectWithName(String serachName) {
		return driver.FindElementByXPath(
				"//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[1]//following::i");
	}

	public Element getSSHeader() {
		return driver.FindElementByXPath("//header[@id='header']");
	}

	public Element getSavedSearch_ScheduleButton() {
		return driver.FindElementById("rbnSchedule");
	}

	public Element getSavedSearch_SchedulePopUp() {
		return driver.FindElementById("divscheduler");
	}

	public Element getSavedSearch_ScheduleTime() {
		return driver.FindElementById("txtOneTimeStartDate");
	}

	public Element getSaveSchedulerBtn() {
		return driver.FindElementByXPath("//*[@id='btnScheduleSubmit']");
	}

	public Element getSavedSearchNewGroupExpand() {
		return driver.FindElementByXPath("//*[contains(@class,'clicked')]//preceding-sibling::i");
	}
	
	public Element getSavedSearchChildNodesExists() {
		return driver.FindElementByXPath("//a[contains(@class,'clicked')]/parent::li[@aria-expanded='false']");
	}

	public Element getSavedSearchGroupName() {
		return driver.FindElementByXPath(".//*[@id='jsTreeSavedSearch']//a[contains(.,'New node')]");
	}

	public Element getShareSerachBtn() {
		return driver.FindElementById("rbnShare");
	}

	// public Element getSearchUserToShare(){ return
	// driver.FindElementByXPath("//input[@id= 'kwd_search']"); }
	// public ElementCollection getUserPopUptoShare(){ return
	// driver.FindElementsByXPath("(//div[@class='user-list-content
	// clear'])[1]"); }

	public ElementCollection getUserInShareList() {
		return driver.FindElementsByXPath("//*[@id='my-table']/tbody/tr/td[2]/label[1]");
	}

	public Element getSharecheckBoxofUser(int num) {
		return driver.FindElementByXPath("(//*[@id='my-table']/tbody/tr/td[1]/label/i)[" + num + "]");
	}

	public Element getShareSaveBtn() {
		return driver.FindElementByXPath("(//button[text()='Save'])[1]");
	}

	public Element getShareSaveBtnNew() {
		return driver.FindElementByCssSelector("#btnShareSave");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	// batch upload
	public Element getBatchUploadButton() {
		return driver.FindElementById("rbnBatchSearchUpload");
	}

	public Element getSelectFile() {
		return driver.FindElementByXPath("//input[@id='fileupload']");
	}
	
	public Element getChooseFile() {
		return driver.FindElementByXPath("//input[@id='txtSelectedFile']");
	}


	public Element getSubmitToUpload() {
		return driver.FindElementByXPath("//button[contains(text(),'Ok')]");
	}

	public Element getMySeraches() {
		return driver
				.FindElementByXPath("//*[contains(@class,'clicked')]//preceding-sibling::i[contains(@class,'ocl')]");
	}

	public Element getSelectUploadedFile(String fileName) {
		return driver.FindElementByXPath("//a[contains(text(),'" + fileName.replace(".xlsx", "") + "')]");
	}

	public Element getNumberOfSavedSearchToBeShown() {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid_length']/label/select");
	}

	public ElementCollection getSearchStatus() {
		return driver.FindElementsByXPath("//*[@id='SavedSearchGrid']/tbody/tr/td[6]");
	}

	public ElementCollection getCounts() {
		return driver.FindElementsByXPath("//*[@id='SavedSearchGrid']/tbody/tr");
	}

	public Element getToDocList() {
		return driver.FindElementById("btnDocumentList");
	}

	// Xpath change based on new implemetation - ("document-btn");
	public Element getToDocView() {
		return driver.FindElementById("view");
	}
	public Element getviewindocview() {
		return driver.FindElementById("document-btn");
	}

	public Element getToDocViewoption() {
		return driver.FindElementByXPath("//span[contains(text(),'Doc View')]");
	}

	public Element getLaunchDocView() {
		return driver.FindElementByXPath("//li[@id='rbngViewGroup']//a[@id='view']");
	}

	public Element getToDocViewAction(String action) {
		return driver.FindElementByXPath("//a[@title='Doc View']//..//li//a[text()='" + action + "']");
	}

	public Element getCountofDocs() {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody/tr/td[4]");
	}

	public Element getSavedSearchToBulkFolder() {
		return driver.FindElementById("rbnFolder");
	}

	public Element getSavedSearchToBulkAssign() {
		return driver.FindElementById("rbnAssign");
	}

	public Element getSavedSearchDeleteButton() {
		return driver.FindElementById("rbnDelete");
	}

	public Element getSavedSearchExecuteButton() {
		return driver.FindElementById("rbnSubmit");
	}

	public Element getSavedSearchEditButton() {
		return driver.FindElementById("rbnEdit");
	}

	public Element getSavedSearchExportButton() {
		return driver.FindElementById("rbnExport");
	}

	public Element getSavedSearchToTally() {
		return driver.FindElementById("rbnTally");
	}

	public Element getSavedSearchToConcept() {
		return driver.FindElementById("rbnCategorize");
	}

	public Element getSavedSearchToTermReport() {
		return driver.FindElementById("rbnReport");
	}

	public Element getSavedSearch_SearchButton() {
		return driver.FindElementById("rbnBasicSearch");
	}

	public Element getSavedSearchNewGroupButton() {
		return driver.FindElementById("rbnSearchGroup");
	}

	public Element getSavedSearchToBulkTag() {
		return driver.FindElementById("rbnTag");
	}

	public Element getSelectCustodianName() {
		return driver.FindElementByCssSelector("input[data-friendlbl='CustodianName'] + i");
	}

	public Element getSelectDOCID() {
		return driver.FindElementByCssSelector("input[data-friendlbl='DocID'] + i");
	}

	public Element getAddToSelected_Button() {
		return driver.FindElementById("addFormObjects-coreList");
	}

	public Element getRunReport_Button() {
		return driver.FindElementById("btnRunReport");
	}

	public Element getExportPopup() {
		return driver.FindElementById("DivExport");
	}

	public Element getBckTask_selectExport() {
		return driver.FindElementByXPath("//*[@id='bgTask']//li[1]//a");
	}

	// reports from saved search
	public Element getSelectedSourceConcept() {
		return driver.FindElementByXPath(".//*[@id='bitlist-sources']//li");
	}

	public Element getTermReportTitle() {
		return driver.FindElementByXPath("//h1[@class='page-title']");
	}

	public Element getTermReportHitsCount() {
		return driver.FindElementById("lblHitsCount");
	}

	// added by shilpi
	public Element getSavedSearchID() {
		return driver.FindElementByCssSelector("table#SavedSearchGrid>tbody>tr.odd>td:nth-child(2)");
	}

	public Element getShare_selectallcheckbox() {
		return driver.FindElementByXPath("//*[@id='chkSelectAll']/following-sibling::i");
	}

	public Element getSharedWithMe() {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//li[2]");
	}

	public Element getPublicShare() {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//li[3]");
	}

	// public Element getPublicShare() { return
	// driver.FindElementByXPath("//a[@data-content='Public Shared (With Entire
	// Security Group)']"); }
	public Element getSearchName(String serachName) {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[3]");
	}

	public Element getShare_PA() {
		return driver.FindElementByXPath("//*[@id='s1']//label[contains(.,'Project Admin')]/i");
	}

	// modified on 11/17/21 - Raghuram
	public Element getLastStatusSelectionFromGrid(String status) {
		return driver.FindElementByXPath(
				"(//*[@id='SavedSearchGrid']/tbody//tr[td='" + status + "']//following::i)[last()]");
	}

	public Element getSelectSearchWithID(String serachName) {
		return driver
				.FindElementByXPath("(//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[2])[last()]");
	}

	public Element getSelectSearchWithResultCount(String serachName) {
		return driver
				.FindElementByXPath("(//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[4])[last()]");
	}

	public Element getShare_SecurityGroup(String securitygroup) {
		return driver.FindElementByXPath("//div[@id='s1']//label[contains(.,'" + securitygroup + "')]/i");
	}

	public Element getShare_SecurityGroupC(String securitygroup) {
		return driver.FindElementByXPath("// *[@id='s1']//label[normalize-space()='" + securitygroup + "']/i");
	}

	// *[@id='s1']//label[normalize-space()='Shared with Default Security Group']/i
	// *[@id='s1']//label[contains(.,'" + securitygroup + "')]/i

	public Element getSavedSearchGroupName(String name) {
		return driver.FindElementByXPath("// *[@id='jsTreeSavedSearch']//a[contains(text(),'" + name + "')]");
	}
	
	public Element getChildOfCurrentClickedNode(int noOfChild) {
		return driver.FindElementByXPath("//div[@id='jsTreeSavedSearch']//a[@class='jstree-anchor jstree-clicked']/following-sibling::ul/li["+noOfChild+"]/a");
	}

	public Element getSavedSearchGroupNameC(String name) {
		return driver.FindElementByXPath("// *[@id='jsTreeSavedSearch']//a[normalize-space()='" + name + "']");
	}

	// *[@id='jsTreeSavedSearch']//a[contains(text(),'" + name + "')]
	// *[@id='jsTreeSavedSearch']//a[normalize-space()='Shared with Default Security
	// Group']

	// quick batch
	public Element getSavedSearchQuickBatchButton() {
		return driver.FindElementById("rbnQuickAssign");
	}

	// Added By Jeevitha

	public Element getUnreleaseBtn() {
		return driver.FindElementByXPath("// button[@id='btnUnrelease']");
	}

	public Element getNotificationStatus(int i) {
		return driver.FindElementByXPath("(// ul[@class='notification-body']//span//span)[" + i + "]");
	}

	public ElementCollection getListOfGroupsUnderTab() {
		return driver.FindElementsByXPath("//a[contains(@class,'clicked')]//following-sibling::ul//li");
	}

	public ElementCollection getSearchNames() {
		return driver.FindElementsByXPath("//*[@id='SavedSearchGrid']/tbody//tr/td[3]");
	}

	public Element getText_dropdown() {
		return driver.FindElementByXPath("//select[@id='textSelect']");
	}

	public Element getNewLine_dropdown() {
		return driver.FindElementByXPath("//select[@id='newLineSelect']");
	}

	public Element getDateStyle_dropdown() {
		return driver.FindElementByXPath("//select[@id='dateStyleSelect']");
	}

	public Element getSelectMatadata(String metaData) {
		return driver.FindElementByCssSelector("input[data-friendlbl='" + metaData + "'] + i");
	}

	public Element getRenameButtonDisabled() {
		return driver.FindElementByXPath("//a[@id='rbnRename' and @class='disabled']");
	}

	public Element getGridHeader() {
		return driver.FindElementByXPath("//div[@class='dataTables_scrollHeadInner']");
	}

	public Element getGridBody() {
		return driver.FindElementByClassName("dataTables_scrollBody");
	}

	public Element getExecutePopMSg() {
		return driver.FindElementByXPath("//p[@class='pText']");
	}

	public ElementCollection getPageNumCount() {
		return driver.FindElementsByXPath("//ul[@class='pagination pagination-sm']//li");
	}

	public Element getPageNextBtn() {
		return driver.FindElementByXPath("//li[@class='paginate_button next']//a");
	}

	public Element getLastNodeFromTab(String GroupName) {
		return driver.FindElementByXPath("(//a[text()='" + GroupName + "']//following-sibling::ul//a)[last()]");
	}

	public Element getNodeFromTab(String name, String GroupName) {
		return driver.FindElementByXPath("//a[@data-content='" + GroupName + "']//..//li//a[text()='" + name + "']");
	}

	public Element getSavedSearchNewNode() {
		return driver.FindElementByXPath("(//a[text()='My Saved Search']//following-sibling::ul//a)[last()]");
	}

	public Element getRenameButton() {
		return driver.FindElementById("rbnRename");
	}

	public Element getRenameTextdfield() {
		return driver.FindElementById("RenameSSTxt");
	}

	public Element getRenameSaveBtn() {
		return driver.FindElementByXPath("( //button[text()='Save'])[3]");
	}

	public String getNewNode() {
		return driver.FindElementByXPath("(//a[@data-content='My Saved Search']//following-sibling::ul//a)[last()]")
				.getText();
	}

	public Element getDeleteOkBtn() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[text()=' Ok']");
	}

	// Added by Raghuram
	public Element getPreBuiltHelpIcon() {
		return driver.FindElementByXPath("//a[text()='Pre-Built Models']//div");
	}

	public Element getPreBuiltHelpTextContent() {
		return driver.FindElementByXPath("//h3[@class='popover-title']//..//div[@class='popover-content']");
	}

	public Element getCreatedNodeName(String nodeName) {
		return driver.FindElementByXPath("//a[text()='My Saved Search']//..//li//a[text()='" + nodeName + "']");
	}

	public Element getCreatedNodeName(String nodeName, String rtFolder) {
		return driver.FindElementByXPath("//a[@data-content='" + rtFolder + "']//..//li//a[text()='" + nodeName + "']");
	}

	public Element getLastChildCreatedNode(String rtFolder) {
		return driver.FindElementByXPath("(//a[@data-content='" + rtFolder + "']//following-sibling::ul//a)[last()]");
	}

	public Element getArrowMark(String rtFolder) {
		return driver.FindElementByXPath("//a[@data-content='" + rtFolder + "']//..//i");
	}

	public Element getChooseSearchRadiobtn(String name) {
		return driver.FindElementByXPath(
				"//tr[@role='row']//td[text()='" + name + "']//..//input[@type='radio']//following-sibling::i");
	}

	public Element getReleaseIcon() {
		return driver.FindElementByXPath("//a[@id='rbnRelease']");
	}

	public Element getReleaseDocToSG(String groupName) {
		return driver.FindElementByXPath(
				"//div[text()='" + groupName + "']//..//input[@type='checkbox']//following-sibling::i");
	}

	public Element getReleaseBtn() {
		return driver.FindElementByXPath("//button[@id='btnRelease']");
	}

	public Element getFinalizeReleaseButtonfromPopup() {
		return driver.FindElementByXPath("//button[@id='btnfinalizeAssignment']");
	}

	// Added by jeevitha
	public Element getSearchNewNode(String securityGroup) {
		return driver.FindElementByXPath(
				"(//a[contains(@data-content,'" + securityGroup + "')]//following-sibling::ul//a)[last()]");
	}

	public Element getCollapsedSharedWithDefaultSecurityGroup() {
		return driver.FindElementByXPath("//li//a[text()='Shared with Default Security Group']//preceding-sibling::i");
	}

	public Element getSharedGroupName(String name) {
		return driver.FindElementByXPath(
				"//a[@data-content='Shared with Default Security Group']//following-sibling::ul//a[contains(.,'" + name
						+ "')]");
	}

	// Added By Jeevitha
	public Element getSelect_verifyName(String search_name) {
		return driver.FindElementByXPath(
				"//*[@id='SavedSearchGrid']/tbody//tr[td='" + search_name + "']/td[1]/descendant::i");
	}

	//modified By jeevitha 27/12/2022
	public Element getDocView_button() {
		return driver.FindElementByXPath("//span[contains(text(),'Doc View')]");
	}

	public Element getSelect_redact() {
		return driver.FindElementByXPath("//a[@title='Redact']/i");
	}

	public Element getSelect_batchNext() {
		return driver.FindElementByXPath("//i[@class='fa fa-chevron-right DocViewBatchNext']");
	}

	public Element getSelect_saveButton() {
		return driver.FindElementByXPath("//span[@class='fa fa-save']");
	}

	public Element get_germanHeaderMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor9')]//span");
	}

	public Element get_germanSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor9')]//p");
	}

	// Added by jayanthi 16/9/21
	public Element getSavedSearchRefresh() {
		return driver.FindElementByCssSelector("a[id='rbnRefresh']");
	}

	public Element getCountofThreshold() {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody/tr/td[11]");
	}

	public Element getSearchStatus(String searchName, String status) {
		return driver.FindElementByXPath("//td[text()='" + searchName + "']//..//td[6][text()='" + status + "']");
	}

	public ElementCollection getCompletedStatusColumn() {
		return driver.FindElementsByXPath("//table[@id='SavedSearchGrid']//tbody//td[6]");
	}

	public Element getStatusDropDown() {
		return driver.FindElementByXPath("//*[@id='ddlSavedSearchStatus']");
	}

	// Added by Raghuram
	public Element getMoveIcon() {
		return driver.FindElementByXPath("//a[@id='rbnMove']");
	}

	public Element getExpandIconfromPopup() {
		return driver.FindElementByXPath("// div[@id='divMoveTreeNode']//li[@role='treeitem']//i");
	}

	public Element getSelectNodeToMove(String nodeName) {
		return driver
				.FindElementByXPath("//div[@id='divMoveTreeNode']//li[@role='treeitem']//a[text()='" + nodeName + "']");
	}

	public Element getMoveActionCancel() {
		return driver.FindElementByXPath("//button[@id='btnMoveCancel']");
	}

	public Element getMoveActionSave() {
		return driver.FindElementByXPath("//button[@id='btnMoveSave']");
	}

	public Element getSelectAnode(String name) {
		return driver.FindElementByXPath("//a[@data-content='My Saved Search']//..//li//a[text()='" + name + "']");
	}

	public Element getshareASearchPopup() {
		return driver.FindElementByXPath("//h3[text()='Share a Search']");
	}

	// only when MySavedsearch is selected and not expanded

	public ElementCollection getAvailableShareListfromMenu() {
		return driver.FindElementsByXPath(
				"//div[@class='panel-body']//div[@id='jsTreeSavedSearch']//li[@role='treeitem' and @aria-selected='false']//a");
	}

	public ElementCollection getAvailableShareListfromShareASearchPopup() {
		return driver.FindElementsByXPath("//div[@class='smart-form client-form']//label");
	}

	public Element getShareSerachCloseBtn() {
		return driver
				.FindElementByXPath("//div[@id='divShareSearchList']//..//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getsearchResultEmptyMessage() {
		return driver.FindElementByXPath("//td[text()='Your query returned no data']");
	}

	public Element getRenameSearchGroupNode(String nodeName) {
		return driver.FindElementByXPath("//li[@role='treeitem']//input[@value='" + nodeName + "']");
	}

	public Element getbasicSearchIcon() {
		return driver.FindElementByXPath("//a[@id='rbnBasicSearch']");
	}

	// 9-28-21
	public Element getCurrentNodeLastChild() {
		return driver.FindElementByXPath("(// *[contains(@class,'clicked')]//following-sibling::ul//a)[last()]");
	}

	public Element getMainFolders(String name) {
		return driver.FindElementByXPath("//li[@role='treeitem']//a[text()='" + name + "']");
	}

	public ElementCollection getMainFoldersList() {
		return driver.FindElementsByXPath("//li[@role='treeitem']//a");
	}

	public Element getSuccessPopup() {
		return driver.FindElementByXPath("//span[text()='Success !']");
	}

	public Element getErrorPopup() {
		return driver.FindElementByXPath("//span[text()='Error !']");
	}

	public Element getDefaultSecurityGroupExpand() {
		return driver
				.FindElementByXPath("//div[@id='jsTreeSavedSearch']//a[contains(text(),'Default Security Group')]");
	}

	// Added by Jeevitha - modified 6/10/21
	public Element getCreatedNode(String newNode) {
		return driver.FindElementByXPath("//a[text()='" + newNode + "']");
	}

	// Added by Iyappan
	public Element getSearchText(String searchText) {
		return driver.FindElementByXPath(
				"(//div[@class='panel-collapse collapse in']//td[@class='value']//span[contains(text(),'" + searchText
						+ "')])[last()]");
	}

	public Element getCollapsedSharedWithProjectAdministrator() {
		return driver.FindElementByXPath("//li//a[contains(text(),'Project Administrator')]//preceding-sibling::i");
	}

	public Element getSharedGroupNameInPA(String name) {
		return driver.FindElementByXPath(
				"//a[contains(@data-content,'Project Administrator')]//following-sibling::ul//a[contains(.,'" + name
						+ "')]");
	}

	public Element getDocListIcon() {
		return driver.FindElementByXPath("//a[@id='btnDocumentList']");
	}

	public Element getTotalCountLoad() {
		return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal' and @style='display: none;']");
	}

	public Element getSelectSavedSearch(String searchName) {
		return driver.FindElementByXPath("//td[contains(text(),'" + searchName + "')]/parent::tr/td[1]//i");
	}

	public Element getSavedSearchCount(String searchName) {
		return driver.FindElementByXPath("//td[contains(text(),'" + searchName + "')]/parent::tr/td[4]");
	}

	public Element getSavedSearchStatus(String searchName) {
		return driver.FindElementByXPath("//td[contains(text(),'" + searchName + "')]/parent::tr/td[6]");
	}

	public Element getStatusBySavedSearchStatus(String searchName, String status) {
		return driver
				.FindElementByXPath("//td[contains(text(),'" + searchName + "')]//..//td[6][text()='" + status + "']");
	}

	// Added By Jeevitha
	public Element getSGTab(String SecurityGroup) {
		return driver.FindElementByXPath("//a[contains(text(),'" + SecurityGroup + "')]");
	}

	// Added by raghuram
	public Element getHideSHowBtn() {
		return driver.FindElementByXPath("//button[@class='ColVis_Button ColVis_MasterButton']");
	}

	public ElementCollection getGridDataList(int numIndex) {
		return driver.FindElementsByXPath("//table[@id='SavedSearchGrid']//tr//td[" + numIndex + "]");
	}

	public ElementCollection getBGgridDataList() {
		return driver.FindElementsByXPath("//table[@id='dt_basic']//th");
	}

	public Element getValueAgainstID(String id, int index) {
		return driver.FindElementByXPath(
				"//table[@id='SavedSearchGrid']//tr//td[text()='" + id + "']//..//td[" + index + "]");
	}

	public Element getValueAgainstIDbg(String id, int index) {
		return driver.FindElementByXPath("//table[@id='dt_basic']//td[text()='" + id + " ']//..//td[" + index + "]");
	}

	public Element getCurrentSelectedSearchName(int num) {
		return driver.FindElementByXPath("//tr[contains(@class,'active-row')]//td['" + num + "']");
	}

	public Element getFieldoptions(String fieldToChoose) {
		return driver.FindElementByXPath(
				"(//span[contains(text(),'" + fieldToChoose + "')]//..//input[@type='checkbox'])[last()]");
	}

	public Element getFieldHeader(String headerName) {
		return driver.FindElementByXPath("//tr[@role='row']//th[contains(text(),'" + headerName + "')]");
	}

	public Element getbackGroundFilm() {
		return driver.FindElementByXPath("//div[@class='ColVis_collectionBackground']");
	}

	// 10/13/21
	public Element getStyle_dropdown() {
		return driver
				.FindElementByXPath("//*[@class='col-sm-12 no-padding smart-form']/label/select[@id='styleSelect']");
	}

	public Element getField_dropdown() {
		return driver
				.FindElementByXPath("//*[@class='col-sm-12 no-padding smart-form']/label/select[@id='fieldSelect']");
	}

	public Element getbulkAssignTotalCountLoad() {
		return driver.FindElementByXPath(
				"//div[@class='col-md-3 bulkActionsSpanLoderTotal' and @style='padding-bottom: 5px; display: none;']");
	}

	public ElementCollection getGridHeaderListSS() {
		return driver.FindElementsByXPath("//tr[@role='row']//th[text()]");
	}

	public Element getCurrentSearchSelection(String searchName) {
		return driver.FindElementByXPath("//td[text()='" + searchName + "']");
	}

	// end
	// added by jeevitha
	public Element getExecuteContinueBtn() {
		return driver
				.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[contains(text(),'Continue')]");
	}

	public Element getContinueButton() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public Element getSearchStatus(String searchName) {
		return driver.FindElementByXPath("//td[text()='" + searchName + "']//..//td[6]");
	}

	public Element getNotficationStatusMessage() {
		return driver.FindElementByXPath("//div[@id='divbigBoxes']//p");
	}

	// added by Mohan
	public ElementCollection getTreeNodesCounts() {
		return driver.FindElementsByXPath("//*[@id='jsTreeSavedSearch']//li");
	}

	public Element getSavedSearchTableDraftName() {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']//td[contains(text(),'DRAFT')]");
	}

	public Element getSavedSearchTreeNode(String name) {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//a[contains(.,'" + name + "')]");
	}

	public Element getSavedSearchSharedWithExpandButton() {
		return driver.FindElementByXPath(
				"//*[@class='jstree-node gp_tc_sgshared jstree-closed']//i[contains(@class,'ocl')]");
	}

	public Element getSavedSearchSharedWithExpandLastButton() {
		return driver.FindElementByXPath(
				"//*[@class='jstree-node gp_tc_sgshared jstree-closed jstree-last']//i[contains(@class,'ocl')]");
	}

	public Element getSavedSearchSharedWithChildNode() {
		return driver.FindElementByXPath("(.//*[@class='jstree-children']//a[contains(.,'New node')])[1]");
	}

	public Element getSavedSearchRowDetailsText(int rowNo) {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']//tr[1]//td[" + rowNo + "]");
	}

	public Element getSavedSearchLastChildNodeExpandLastButton() {
		return driver.FindElementByXPath(
				"//*[@class='jstree-node gp_tc_pashared jstree-closed jstree-last']//i[contains(@class,'ocl')]");
	}

	public Element getSavedSearchSharedWithProjectLastChildNode() {
		return driver.FindElementByXPath("(.//*[@class='jstree-children']//a[contains(.,'New node')])[last()]");
	}

	public Element getSavedSearchDisplayedSearchRadioButton(int rowNo) {
		return driver.FindElementByXPath("//table[@id='SavedSearchGrid']//tr[" + rowNo + "]//label//i");
	}

	public Element getSavedSearchSharedWithProjectAdminExpandButton() {
		return driver.FindElementByXPath(
				"//*[@class='jstree-node gp_tc_pashared jstree-closed']//i[contains(@class,'ocl')]");
	}

	// Added By Jeevitha
	public Element getSavedSearchCB(String searchName) {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']//tbody//tr//td[contains(text(),'" + searchName
				+ "')]//parent::tr//td[1]//label//i");
	}

	public Element getNearDupeCount(String searchName) {
		return driver.FindElementByXPath("//td[contains(text(),'" + searchName + "')]/parent::tr/td[14]");
	}

	public Element getFamilyCount(String searchName) {
		return driver.FindElementByXPath("//td[contains(text(),'" + searchName + "')]/parent::tr/td[16]");
	}

	public Element getThreadedCount(String searchName) {
		return driver.FindElementByXPath("//td[contains(text(),'" + searchName + "')]/parent::tr/td[15]");
	}

	public Element getFieldoptions_CC(String fieldToChoose) {
		return driver.FindElementByXPath(
				"(//span[contains(text(),'" + fieldToChoose + "')]//..//input[@type='checkbox'])[last()]");
	}

	public Element getSavedSearchNodeWithRespectiveSG(String rootGroup, String nodeName) {
		return driver.FindElementByXPath("//a[text()='" + rootGroup + "']//..//li//a[text()='" + nodeName + "']");
	}

	public Element getspinningWheel() {
		return driver.FindElementByXPath("//div[@id= 'processingPopupDiv' and @style='']");
	}

	public Element getsearch() {
		return driver.FindElementByXPath("(//td[@class='sorting_1']/label/i)[1]");
	}

	public Element NavigateToSearch() {
		return driver.FindElementByXPath("//a[@name='Search']//i");
	}

	public Element NavigateToSavedSearch() {
		return driver.FindElementByXPath("//a[@name='Saved']//i");
	}

	// Added By Jeevitha
	public Element getLastCompletedTime(String searchName) {
		return driver.FindElementByXPath("//td[contains(text(),'" + searchName + "')]/parent::tr/td[9]");
	}

	// Added By sowndarya.velraj

	public Element getPersistantHitCb_Existing() {
		return driver.FindElementByXPath("//div[@id='existingassignment']//div//label[@class='checkbox']//i");
	}

	public Element getPersistantHitCheckBox() {
		return driver.FindElementByXPath("sdz");
	}

	public Element getLastStatusAs(String status) {
		return driver.FindElementByXPath("//select[@id='ddlSavedSearchStatus']//option[text()='" + status + "']");
	}

	public Element getSearchColumnValue(String searchName, int i) {
		return driver.FindElementByXPath("//td[contains(text(),'" + searchName + "')]/parent::tr/td[ " + i + "]");
	}

	public ElementCollection gettableHeaders() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHead']//thead//tr//th");
	}

	public Element getDocListPopup() {
		return driver.FindElementByXPath("//div[@class='MessageBoxMiddle']//span");
	}

	public Element getbtnDocListContinue() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public String getLastStatus() {
		return driver.FindElementByXPath("//table//td[6]").getText();
	}

	public Element getLastStatusAsPending() {
		return driver.FindElementByXPath("//select[@id='ddlSavedSearchStatus']//option[text()='PENDING']");
	}

	public Element getLastStatusAsInProgress() {
		return driver.FindElementByXPath("//select[@id='ddlSavedSearchStatus']//option[text()='INPROGRESS']");
	}

	public Element getLastStatusAsCompleted() {
		return driver.FindElementByXPath("//select[@id='ddlSavedSearchStatus']//option[text()='COMPLETED']");
	}

	public Element getLastStatusAsError() {
		return driver.FindElementByXPath("//select[@id='ddlSavedSearchStatus']//option[text()='ERROR']");
	}

	public Element getLastStatusAsFailed() {
		return driver.FindElementByXPath("//select[@id='ddlSavedSearchStatus']//option[text()='FAILED']");
	}

	public Element noQueryReturnedText() {
		return driver.FindElementByXPath("//table//td[text()='Your query returned no data']");
	}

	// Added by gopinath - 27/12/2021
	public Element getSelectOptionsFromShowHideDropDown(String Option) {
		return driver.FindElementByXPath("//ul[@class='ColVis_collection']/li/label/span[contains(text(),'" + Option
				+ "')]/ancestor::label/input");
	}

	public Element getShowHideDropDown() {
		return driver.FindElementByXPath("//button[@class='ColVis_Button ColVis_MasterButton']");
	}

	public Element getNearDuplicatesCountOfSavedSearch(String SaveSearchName) {
		return driver.FindElementByXPath(
				"//*[@id='SavedSearchGrid']/tbody/tr/td[text()='" + SaveSearchName + "']/following-sibling::td[12]");
	}

	public Element getShowHideOptionList() {
		return driver.FindElementByXPath("//ul[@class='ColVis_collection']");
	}

	public Element getPureHitCountOfSavedSearch(String SaveSearchName) {
		return driver.FindElementByXPath(
				"//*[@id='SavedSearchGrid']/tbody/tr/td[text()='" + SaveSearchName + "']/following-sibling::td[1]");
	}

	public Element getFamilyMembersCountOfSavedSearch(String SaveSearchName) {
		return driver.FindElementByXPath(
				"//*[@id='SavedSearchGrid']/tbody/tr/td[text()='" + SaveSearchName + "']/following-sibling::td[14]");
	}

	public Element getConcepuallySimilarCountOfSavedSearch(String SaveSearchName) {
		return driver.FindElementByXPath(
				"//*[@id='SavedSearchGrid']/tbody/tr/td[text()='" + SaveSearchName + "']/following-sibling::td[11]");
	}

	public Element getThreadedDocumentCountOfSavedSearch(String SaveSearchName) {
		return driver.FindElementByXPath(
				"//*[@id='SavedSearchGrid']/tbody/tr/td[text()='" + SaveSearchName + "']/following-sibling::td[13]");
	}

	public Element getCount(String SaveSearchName) {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody/tr/td[contains(text(),'" + SaveSearchName
				+ "')]/following-sibling::td[1]");
	}

	// Added by Gopinath - 26/12/2021
	public Element getPendingStatus() {
		return driver.FindElementByXPath("(//td[text()='PENDING'])[1]");
	}

	public Element getPendingStatusAdvancedQueary() {
		return driver.FindElementByXPath("//td[contains(text(),'Basic Work Product')]/following-sibling::td[3]");
	}

	// Added By Jeevitha
	public ElementCollection gettableHeaders(String headerName) {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHead']//thead//tr//th[normalize-space()='"
				+ headerName + "']              ");
	}

	public ElementCollection getColumnValues(int i) {
		return driver.FindElementsByXPath("//tbody//td[" + i + "]");
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

	public Element getContinueCount() {
		return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']");
	}

	public Element getContinueButton_Untag() {
		return driver.FindElementByXPath("//button[@id='btnAdd']");
	}

	public ElementCollection getList() {
		return driver.FindElementsByXPath(".//*[@id='jsTreeSavedSearch']//a[contains(.,'New node')]");
	}

	public Element getSavedSearchExpandStats() {
		return driver.FindElementByXPath("//a[@class='jstree-anchor jstree-clicked']//parent::li");
	}

	public Element currentClickedNode() {
		return driver.FindElementByXPath("//a[@class='jstree-anchor jstree-clicked']");
	}

	public Element getLastCreatedSearchGroup(String rtFolder) {
		return driver
				.FindElementByXPath("(//a[contains(text(),'" + rtFolder + "')]//following-sibling::ul//a)[last()]");
	}

	public Element currentcreateddNode() {
		return driver.FindElementByXPath("jstree-node gp_tc_mysearch jstree-leaf jstree-last");
	}

	public Element getExpansionArrow(String groupName) {
		return driver.FindElementByXPath("//a[text()='" + groupName + "']/parent::li/i");
	}

	public Element getSavesSreachSharedWithPA() {
		return driver.FindElementByXPath("//div[@id='jsTreeSavedSearch']/ul/li[2]/a");
	}

	public Element getUnBulkTagConfirmationButton() {
		return driver.FindElementByXPath("//button[contains(text(),'Ok')]");
	}

	public Element getSavedSearchSelectRadioButton() {
		return driver.FindElementByXPath(
				"(//*[@id='SavedSearchGrid']/tbody//td[2])[last()]/../td[@class='sorting_1']/label/i");
	}

	public List<String> listOfAvailableSharefromMenu = new ArrayList<>();
	List<String> listOfAvailableShareListfromShareASearchPopup = new ArrayList<>();
	List<String> sgList = new ArrayList<>();

	public ElementCollection getcurrentClickedNode() {
		return driver.FindElementsByXPath("//a[@class='jstree-anchor jstree-clicked']");
	}

	public SavedSearch(Driver driver) {

		this.driver = driver;
//		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		dcPage = new DocListPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		login = new LoginPage(driver);
		sg = new SecurityGroupsPage(driver);
		// search = new SessionSearch(driver);

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	@SuppressWarnings("static-access")
	public void uploadBatchFile(final String batchFile) {
		driver.waitForPageToBeReady();

		ArrayList<Integer> expectCounts = new ArrayList<Integer>();
		if (Input.numberOfDataSets == 3) {
			expectCounts.add(240);
			expectCounts.add(283);
			expectCounts.add(104);
			expectCounts.add(64);
			expectCounts.add(1198);
			expectCounts.add(1);
			expectCounts.add(27);
			expectCounts.add(13);
			expectCounts.add(2);
			expectCounts.add(1);
			expectCounts.add(0);
			expectCounts.add(0);
		} else if (Input.numberOfDataSets == 1) {

			expectCounts.add(28);
			expectCounts.add(28);
			expectCounts.add(11);
			expectCounts.add(8);
			expectCounts.add(0);
			expectCounts.add(1);
			expectCounts.add(0);
			expectCounts.add(3);
			expectCounts.add(2);
			expectCounts.add(0);
			expectCounts.add(0);
			expectCounts.add(0);
		}

		ArrayList<Integer> actualCounts = new ArrayList<Integer>();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchUploadButton().Visible();
			}
		}), Input.wait30);
		getBatchUploadButton().Click();
		System.out.println("Clicked on Upload button..");
		UtilityLog.info("Clicked on Upload button..");

		System.out.println("Clicked on Batch Upload Button.........");
		UtilityLog.info("Clicked on Batch Upload Button.........");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFile().Visible();
			}
		}), Input.wait30);

		// System.getProperty("user.dir")+"\\src\\com\\stc\\ExcelFiles\\"+ExcelSheetName+
		System.out.println(System.getProperty("user.dir") + Input.batchFilesPath + batchFile);
		UtilityLog.info(System.getProperty("user.dir") + Input.batchFilesPath + batchFile);
		getSelectFile().SendKeys(System.getProperty("user.dir") + Input.batchFilesPath + batchFile);
		getSubmitToUpload().Click();

		base.VerifySuccessMessage("File uploaded successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 12;
			}
		}), Input.wait60);
//		driver.Navigate().refresh();
//		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySeraches().Visible();
			}
		}), Input.wait30);
		getMySeraches().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUploadedFile(batchFile).Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSelectUploadedFile(batchFile).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfSavedSearchToBeShown().Visible();
			}
		}), Input.wait30);
		getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");

		/*
		 * for (final Element status : getSearchStatus()) { driver.WaitUntil((new
		 * Callable<Boolean>() {public Boolean call(){return
		 * status.getText().equals("COMPLETED") ;}}),Input.wait60); }
		 */
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCounts().Visible();
			}
		}), Input.wait30);
		// System.out.println(getCounts().size());

		driver.scrollingToBottomofAPage();
		String value = "0";
		for (int i = 1; i <= getCounts().size(); i++) {
			try {
				value = driver.FindElement(By.xpath("//*[@id='SavedSearchGrid']/tbody/tr[" + i + "]/td[4]")).getText();
				actualCounts.add(Integer.parseInt(value));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				value = "0";
				actualCounts.add(Integer.parseInt(value));
			}

		}

		System.out.println("ExpectCounts " + expectCounts);
		UtilityLog.info("ExpectCounts " + expectCounts);
		System.out.println("ActualCounts :" + actualCounts);
		UtilityLog.info("ActualCounts :" + actualCounts);

		// Delete uploaded set
		driver.scrollPageToTop();
		getSavedSearchDeleteButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDeleteOkBtn().Visible();
			}
		}), Input.wait60);
		getDeleteOkBtn().waitAndClick(10);

		base.VerifySuccessMessage("Save search tree node successfully deleted.");
//		softAssertion.assertTrue(expectCounts.equals(actualCounts));
	}

	public void savedSearchToDocList(final String searchName) {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSavedSearch_SearchName().SendKeys(searchName);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSavedSearch_ApplyFilterButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);
		getSelectWithName(searchName).waitAndClick(10);
		driver.scrollPageToTop();
		getToDocList().waitAndClick(10);
		try {
			if (base.getYesBtn().isElementAvailable(5)) {
				base.getYesBtn().waitAndClick(20);
			}
		} catch (Exception e) {
			System.out.println("Pop up message does not appear");
			Log.info("Pop up message does not appear");
		}
		base.stepInfo("Saved search is opened in doc list");
	}

	public void savedSearchToDocView(final String searchName) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSavedSearch_SearchName().SendKeys(searchName);
		Thread.sleep(2000);

		getSavedSearch_ApplyFilterButton().Click();
		Thread.sleep(1000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		getSelectWithName(searchName).waitAndClick(10);

//		getToDocView().waitAndClick(10);
		docViewFromSS("View in DocView");

		try {
			if (base.getYesBtn().isElementAvailable(3)) {
				base.getYesBtn().waitAndClick(10);
			}

		} catch (Exception e) {
			System.out.println("Pop up message does not appear");
			UtilityLog.info("Pop up message does not appear");
		}

	}

	public void scheduleSavedSearch(final String searchName) throws ParseException, InterruptedException {
//		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		getSavedSearch_SearchName().SendKeys(searchName);
		base.waitTime(2);
		getSavedSearch_ApplyFilterButton().waitAndClick(2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSelectWithName(searchName).Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ScheduleButton().Visible();
			}
		}), Input.wait30);
		getSavedSearch_ScheduleButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ScheduleTime().Visible();
			}
		}), Input.wait60);
		getSavedSearch_ScheduleTime().SendKeys(schdulerTimePlus15Secs());

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getSaveSchedulerBtn().isElementAvailable(5);
		getSaveSchedulerBtn().waitAndClick(1);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Saved search " + searchName + " is scheduled to run in 5 secs!");
		UtilityLog.info("Saved search " + searchName + " is scheduled to run in 5 secs!");
	}

	public void shareSavedSearchPA(final String searchName, String securitygroupname)
			throws ParseException, InterruptedException {

		// base.getSelectProject();

		/*
		 * Dimension dim = new Dimension(1600, 900);
		 * driver.getWebDriver().manage().window().setSize(dim);
		 */
		// driver.getWebDriver().manage().window().setSize(800);

		savedSearch_Searchandclick(searchName);
		getShareSerachBtn().waitAndClick(5);
		Thread.sleep(5000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShare_PA().Visible();
			}
		}), Input.wait30);
		getShare_PA().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShareSaveBtnNew().Visible();
			}
		}), Input.wait30);
		getShareSaveBtnNew().waitAndClick(10);
//        driver.getWebDriver().manage().window().maximize();

		// getShareSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		Thread.sleep(3000);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		Thread.sleep(3000);

		// get Search ID
		String SearchID = getSelectSearchWithID(searchName).getText();
		System.out.println(SearchID);
		UtilityLog.info(SearchID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		getSelectWithName(searchName).waitAndClick(10);

		// Again select same search and share with security group
		// savedSearch_Searchandclick(searchName);
		getShareSerachBtn().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShare_SecurityGroup(securitygroupname).Visible();
			}
		}), Input.wait30);
		getShare_SecurityGroup(securitygroupname).waitAndClick(10);

		getShareSaveBtnNew().waitAndClick(5);
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		Thread.sleep(2000);

		// click on share with project admin tab
		getSavedSearchGroupName("Project Admin").waitAndClick(10);
		Thread.sleep(3000);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		// verify id should get changed
		String newSearchID = getSelectSearchWithID(searchName).waitAndGet(10);
		System.out.println(newSearchID);
		UtilityLog.info(newSearchID);
		softAssertion.assertNotSame(SearchID, newSearchID);

		softAssertion.assertTrue(getSearchName(searchName).Displayed());

		// click on share with security group tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		Thread.sleep(3000);

		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		// verify id should get changed
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSearchWithID(searchName).Visible();
			}
		}), Input.wait30);
		String newSearchID1 = getSelectSearchWithID(searchName).getText();
		System.out.println(newSearchID1);
		UtilityLog.info(newSearchID1);
		softAssertion.assertNotSame(SearchID, newSearchID1);

		softAssertion.assertTrue(getSearchName(searchName).Displayed());

		// impersonate to RMU and check search
		base.impersonatePAtoRMU();

		// click on share with security group tab
		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		Thread.sleep(2000);

		// verify id should get changed
		String newSearchID2 = getSelectSearchWithID(searchName).getText();
		System.out.println(newSearchID2);
		UtilityLog.info(newSearchID2);
		softAssertion.assertEquals(newSearchID2, newSearchID1);
	}

	/**
	 * Modified on : 10/19/21 by : Raghuram A
	 * 
	 * @param searchName
	 * @param securitygroupname
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public void shareSavedSearchRMU(final String searchName, String securitygroupname)
			throws ParseException, InterruptedException {

//		savedSearch_Searchandclick(searchName);
		selectSavedSearchTAb(searchName, Input.mySavedSearch, "Yes");
		getShareSerachBtn().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShare_SecurityGroup(securitygroupname).Visible();
			}
		}), Input.wait30);
		if (getShare_SecurityGroup(securitygroupname).isElementAvailable(3)) {
			base.mouseHoverOnElement(getshareASearchPopup());
			getShare_SecurityGroup(securitygroupname).waitAndClick(10);
		} else {
			base.mouseHoverOnElement(getshareASearchPopup());
			getShare_SecurityGroupC(securitygroupname).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShareSaveBtnNew().Visible();
			}
		}), Input.wait30);
		getShareSaveBtnNew().waitAndClick(10);
		base.VerifySuccessMessage("Share saved search operation successfully done.");

		Thread.sleep(2000);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		Thread.sleep(2000);

		// get Search ID
		String SearchID = getSelectSearchWithID(searchName).getText();
		System.out.println(SearchID);
		base.stepInfo("Search ID : " + SearchID);
		UtilityLog.info(SearchID);

		// click on share with security group tab
		driver.waitForPageToBeReady();
		if (getSavedSearchGroupName(securitygroupname).isElementAvailable(3)) {
			getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		} else {
			getSavedSearchGroupNameC(securitygroupname).waitAndClick(10);
		}
		// Thread sleep added to Execute in cosolidation
		Thread.sleep(2000);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		getSelectWithName(searchName).waitAndClick(10);

		// verify id should get changed
		String newSearchID1 = getSelectSearchWithID(searchName).getText();
		System.out.println(newSearchID1);
		base.stepInfo("Search ID from shared folder : " + newSearchID1);
		UtilityLog.info(newSearchID1);

		softAssertion.assertNotSame(SearchID, newSearchID1);

		softAssertion.assertTrue(getSearchName(searchName).Displayed());

	}

	public void sharewithUsers(final String searchName, String Usertype) {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		if (Usertype.equalsIgnoreCase("Project Admin")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSharedWithMe().Visible();
				}
			}), Input.wait30);
			getSharedWithMe().Click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			softAssertion.assertTrue(getSearchName(searchName).Displayed());

		} else if (Usertype.equalsIgnoreCase("RMU")) {
			driver.WaitUntil((new Callable<Boolean>() {

				public Boolean call() {
					return getPublicShare().Visible();
				}
			}), Input.wait30);
			getPublicShare().Click();
			try {
				Thread.sleep(5000);
			} catch (

			InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			softAssertion.assertTrue(getSearchName(searchName).Displayed());
		}

	}

	public static String schdulerTimePlus15Secs() throws ParseException {
		// Time in GMT
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormatLocal.parse(dateFormatGmt.format(new Date()));

		String Time = dateFormatGmt.format(new Date()).toString();
		// System.out.println(Time);

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = df.parse(Time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.SECOND, 8);
		String newTime = df.format(cal.getTime());
		// System.out.println(newTime);
		return newTime.toString();

	}

	public void getSaveSearchID(final String searchName) {

		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().Click();

	}

	public String renameFile() {
		String FileName = null;
		// back up TCs zip file for later verification
		File destinationFolder = new File(System.getProperty("user.dir") + Input.batchFilesPath);
		File sourceFolder = new File(System.getProperty("user.dir") + Input.batchFilesPath);

		if (!destinationFolder.exists()) {
			destinationFolder.mkdirs();
		}

		// Check weather source exists and it is folder.
		if (sourceFolder.exists() && sourceFolder.isDirectory()) {

			// Get list of the files and iterate over them
			File[] listOfFiles = sourceFolder.listFiles();

			if (listOfFiles != null) {
				System.out.println(1);
				for (File child : listOfFiles) {
					if (child.isFile()) { // rename only files not folders
						// Move files to destination folder
						FileName = "BatchUpload" + Utility.dynamicNameAppender() + ".xlsx";
						child.renameTo(new File(destinationFolder + "\\" + FileName));
					}
				}

				// Add if you want to delete the source folder
				// sourceFolder.delete();
			}
		} else {
			System.out.println(sourceFolder + "  Folder does not exists");
			UtilityLog.info(sourceFolder + "  Folder does not exists");
		}

		// delete last TCs PDF from PDFs folder
		File file = new File("C:\\BatchPrintFiles\\PDFs");
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}
		return FileName;
	}

	/**
	 * Modified date:9/22/21 Modified by: Description : Added wait in-order to avoid
	 * abnormal wait
	 */
	public void savedSearch_Searchandclick(final String searchName) {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait120);
		getSavedSearch_SearchName().SendKeys(searchName);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSavedSearch_ApplyFilterButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getSelectWithName(searchName).waitAndClick(10);
	}

	public void SaveSearchToBulkTag(final String searchName, String TagName) throws InterruptedException {

		search = new SessionSearch(driver);

		savedSearch_Searchandclick(searchName);

		getSavedSearchToBulkTag().Click();

		search.BulkActions_Tag(TagName);

	}

	public void SaveSearchToBulkFolder(final String searchName, String FolderName) throws InterruptedException {

		search = new SessionSearch(driver);
		Thread.sleep(2000);
		savedSearch_Searchandclick(searchName);

		getSavedSearchToBulkFolder().Click();

		search.BulkActions_Folder(FolderName);

	}

	public void SaveSearchToBulkAssign(final String searchName, String assignmentName, String codingForm,
			int purehits) {

		assgnpage = new AssignmentsPage(driver);
		savedSearch_Searchandclick(searchName);

		getSavedSearchToBulkAssign().waitAndClick(10);

		// SessionSearch search = new SessionSearch(driver);
		// search.bulkAssign();
		assgnpage.assignDocstoNewAssgnEnableAnalyticalPanel(assignmentName, codingForm, SessionSearch.pureHit);
	}

	// Below function is to search saved search and delete it
	public void SaveSearchDelete(final String searchName) {

		savedSearch_Searchandclick(searchName);

		getSavedSearchDeleteButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSubmitToUpload().Visible();
			}
		}), Input.wait60);
		getSubmitToUpload().Click();

		base.VerifySuccessMessage("Save search tree node successfully deleted.");

	}

	public void SaveSearchExport(final String searchName) throws InterruptedException {

		savedSearch_Searchandclick(searchName);

		getSavedSearchExportButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getExportPopup().Visible();
			}
		}), Input.wait60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectCustodianName().Visible();
			}
		}), Input.wait30);
		getSelectCustodianName().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDOCID().Visible();
			}
		}), Input.wait30);
		getSelectDOCID().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddToSelected_Button().Visible();
			}
		}), Input.wait30);
		getAddToSelected_Button().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRunReport_Button().Visible();
			}
		}), Input.wait30);
		getRunReport_Button().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		base.VerifySuccessMessage(
				"Your Report has been added into the Background successfully. Once it is complete, the "
						+ "\"bullhorn\""
						+ " icon in the upper right-hand corner will turn red, and will increment forward.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.getBackgroundTask_Button().Visible();
			}
		}), Input.wait60);
		bc.getBackgroundTask_Button().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBckTask_selectExport().Visible();
			}
		}), Input.wait30);
		getBckTask_selectExport().waitAndClick(10);

	}

	public void savedSearchToConcepts(final String searchName) {

		savedSearch_Searchandclick(searchName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchToConcept().Visible();
			}
		}), Input.wait30);
		getSavedSearchToConcept().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectedSourceConcept().Visible();
			}
		}), Input.wait30);
		softAssertion.assertEquals(getSelectedSourceConcept().getText().toString(),
				"Search: Selected Documents from Save Search");

	}

	public void savedSearchToTally(final String searchName) {

		savedSearch_Searchandclick(searchName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchToTally().Visible();
			}
		}), Input.wait30);
		getSavedSearchToTally().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectedSourceConcept().Visible();
			}
		}), Input.wait30);
		softAssertion.assertEquals(getSelectedSourceConcept().getText().toString(),
				"Search: Selected Documents from SavedSearch: " + searchName + "");

	}

	public void savedSearchToTermReport(final String searchName) {

		savedSearch_Searchandclick(searchName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchToTermReport().Visible();
			}
		}), Input.wait30);
		getSavedSearchToTermReport().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTermReportTitle().Visible();
			}
		}), Input.wait60);
		System.out.println(getTermReportTitle().getText().toString());
		softAssertion.assertEquals(getTermReportTitle().getText().toString(), "Saved Search");

	}

	public void savedSearchExecute(final String searchName, int PureHits) {

		savedSearch_Searchandclick(searchName);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchExecuteButton().Visible();
			}
		}), Input.wait30);
		getSavedSearchExecuteButton().Click();

		base.VerifySuccessMessage("Successfully added to background search.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Got notification!");

		getSavedSearch_ApplyFilterButton().Click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCountofDocs().Visible();
			}
		}), Input.wait60);

		softAssertion.assertEquals(getCountofDocs().getText(), String.valueOf(PureHits));

	}

	public void savedSearchNewSearchGrp(final String searchName) {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchNewGroupButton().Visible();
			}
		}), Input.wait60);
		getSavedSearchNewGroupButton().Click();

		base.VerifySuccessMessage("Save search tree node successfully created.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchNewGroupExpand().Visible();
			}
		}), Input.wait120);
		// driver.Navigate().refresh();
		getSavedSearchNewGroupExpand().waitAndClick(30);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchGroupName().Visible();
			}
		}), Input.wait30);
		getSavedSearchGroupName().Displayed();
		getSavedSearchGroupName().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchButton().Visible();
			}
		}), Input.wait60);
		getSavedSearch_SearchButton().Click();

		base.selectproject();
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.saveSearch(searchName);
	}

	public void savedSearchEdit(final String searchName1, final String searchName2) {

		savedSearch_Searchandclick(searchName1);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchEditButton().Visible();
			}
		}), Input.wait60);
		getSavedSearchEditButton().waitAndClick(10);

		// verify same pure hits should display when edit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return search.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(search.getPureHitsCount().getText());
		System.out.println("PureHit is : " + pureHit);

		softAssertion.assertEquals(Integer.parseInt(search.getPureHitsCount().getText()), pureHit);

		/*
		 * search.getSecondSearchBtn().waitAndClick(10);
		 * 
		 * search.getSecondSavedSearchBtn().waitAndClick(10);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * search.getsavesearch_overwrite().Visible() ;}}),Input.wait60);
		 * search.getsavesearch_overwrite().Click();
		 * 
		 * search.getSaveSearch_SaveButton().Click();
		 * base.VerifySuccessMessage("Saved search saved successfully");
		 * 
		 * savedSearch_Searchandclick(searchName1);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * search.getSavedSearch_MySearchesTab().Visible() ;}}), Input.wait60);
		 * search.getSavedSearch_MySearchesTab().Click();
		 * search.getSaveSearch_Name().SendKeys(searchName1);
		 * search.getSaveSearch_SaveButton().Click();
		 * base.VerifySuccessMessage("Saved search saved successfully");
		 * System.out.println("Saved search with name - "+searchName1);
		 * 
		 * search.basicContentSearch(Input.searchString2);
		 * 
		 * softAssertion.assertEquals(search.getPureHitsCount().getText(), pureHit);
		 * 
		 * search.saveSearch(searchName2);
		 * 
		 * savedSearch_Searchandclick(searchName1);
		 * 
		 * savedSearch_Searchandclick(searchName2);
		 * getSavedSearch_ApplyFilterButton().waitAndClick(20);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getSelectWithName(searchName).Visible() ;}}),Input.wait30);
		 * 
		 * 
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getSavedSearchGroupName().Visible() ;}}),Input.wait30);
		 * getSavedSearchGroupName().Displayed(); getSavedSearchGroupName().Click();
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getSavedSearch_SearchButton().Visible() ;}}),Input.wait60);
		 * getSavedSearch_SearchButton().Click();
		 * 
		 * search.basicContentSearch(Input.searchString1);
		 * search.saveSearch(searchName);
		 */
	}

	public void savedsearchquickbatch(String searchName) {

		savedSearch_Searchandclick(searchName);

		getSavedSearchQuickBatchButton().waitAndClick(10);

		System.out.println("performing quick batch");

	}

	/**
	 * @param searchName
	 * @author Jeevitha Description: creates new search group
	 * @return
	 * @modifiedBy : Raghuram @modifiedOn : 03/02/22
	 */
	public String createNewSearchGrp(String groupName) {

		navigateToSavedSearchPage();
		getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		System.out.println("Clicked MY saved Search Tab");

		// Select specified Root group
		selectRootGroupTab(groupName);

		getSavedSearchNewGroupButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitTime(3);// to handle wait for observing the text
//		base.hitKey(KeyEvent.VK_ENTER);// base on new implementation
		getSavedSearchNewGroupButton().waitAndClick(5);
//		getSSHeader().waitAndClick(3);// In order to avoid abnormal showstoppers

//		driver.waitForPageToBeReady();
		base.waitForElement(base.getSuccessMsgHeader());
		base.VerifySuccessMessage("Save search tree node successfully created.");
		base.CloseSuccessMsgpopup();
		// driver.Navigate().refresh();

		// Get created node text
		String newNode = currentClickedNode().getText();
		System.out.println("Created new node : " + newNode);

		return newNode;
	}

	/**
	 * @author Jeevitha Description: Selects Node
	 */
	public void selectNode() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchGroupName().Visible();
			}
		}), Input.wait60);
		getSavedSearchGroupName().waitAndClick(10);

	}

	/**
	 * @author Jeevitha Description: Schedules Saved search In Minutes
	 */
	public void scheduleSavedSearchInMinute(String searchName, int number) throws ParseException, InterruptedException {
		driver.waitForPageToBeReady();
		savedSearch_SearchandSelect(searchName, "Yes");
		base.waitForElement(getSavedSearch_ScheduleButton());
		getSavedSearch_ScheduleButton().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ScheduleTime().Visible();
			}
		}), Input.wait60);
		getSavedSearch_ScheduleTime().SendKeys(scheduleTimePlusMinutes(number));

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getSaveSchedulerBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait30);
		System.out.println("Saved search " + searchName + " is scheduled to run in " + number + "minutes");
		base.stepInfo("Saved search " + searchName + " is scheduled to run in " + number + "minutes");
	}

	/**
	 * Description : Generate time in Minutes
	 * 
	 * @param number - Desired value (Minutes)
	 * @return String - Modified time in String format
	 * @throws ParseException
	 * @author Jeevitha
	 */
	public static String scheduleTimePlusMinutes(int number) throws ParseException {
		// Time in GMT
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormatLocal.parse(dateFormatGmt.format(new Date()));

		String Time = dateFormatGmt.format(new Date()).toString();
		// System.out.println(Time);

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = df.parse(Time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, number);
		String newTime = df.format(cal.getTime());
		// System.out.println(newTime);
		return newTime.toString();

	}

	/**
	 * 
	 * @author Jeevitha Description: Rename search in saved Search page
	 * @param searchName1
	 * @param searchName2
	 */
	public void renameSavedSearch(String searchName1, String searchName2) {

		driver.waitForPageToBeReady();
		savedSearch_Searchandclick(searchName1);

		getRenameButton().Click();
		getRenameTextdfield().SendKeys(searchName2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRenameSaveBtn().Visible();
			}
		}), Input.wait60);
		getRenameSaveBtn().Click();

		base.VerifySuccessMessage("Rename of Saved Search done successfully.");
	}

	/**
	 * Modified on : 10/19/21 by Raghuram A
	 * 
	 * @param searchName
	 * @param securitygroupname
	 * @throws ParseException
	 * @throws InterruptedException
	 * 
	 * @Stabilization : changes done
	 */
	public void shareSavedSearchAsPA(final String searchName, String securitygroupname)
			throws ParseException, InterruptedException {

		savedSearch_Searchandclick(searchName);
		getShareSerachBtn().waitAndClick(5);
		driver.waitForPageToBeReady();

		getShare_SecurityGroup(securitygroupname).waitAndClick(10);
		getShareSaveBtnNew().waitAndClick(10);
		driver.waitForPageToBeReady();

		base.VerifySuccessMessage("Share saved search operation successfully done.");
		base.CloseSuccessMsgpopup();
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		// get Search ID
		String SearchID = getSelectSearchWithID(searchName).getText();
		System.out.println(SearchID + " is Saved search ID");
		base.stepInfo(SearchID + " is Saved search ID");
		UtilityLog.info(SearchID + " is Saved search ID");

		driver.waitForPageToBeReady();
		getSelectWithName(searchName).waitAndClick(10);
		driver.waitForPageToBeReady();

		// Again select same search and share with security group
		getShareSerachBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		getShare_SecurityGroup(securitygroupname).waitAndClick(10);

		getShareSaveBtnNew().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		driver.waitForPageToBeReady();

		// click on share with project admin tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		driver.waitForPageToBeReady();
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		// verify id should get changed
		String newSearchID = getSelectSearchWithID(searchName).waitAndGet(10);
		System.out.println(newSearchID + " is Project admin ID");
		base.stepInfo(newSearchID + " is Project admin ID");
		UtilityLog.info(newSearchID + " is Project admin ID");
		softAssertion.assertNotSame(SearchID, newSearchID);

		getSearchName(searchName).isElementAvailable(2);
		softAssertion.assertTrue(getSearchName(searchName).Displayed());

		// click on share with security group tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		driver.waitForPageToBeReady();
		getSavedSearch_ApplyFilterButton().waitAndClick(10);

		// verify id should get changed
		getSelectSearchWithID(searchName).isElementAvailable(2);
		String newSearchID1 = getSelectSearchWithID(searchName).getText();
		System.out.println(newSearchID1 + " is Security group ID");
		base.stepInfo(newSearchID1 + " is Security group ID");
		UtilityLog.info(newSearchID1 + " is Security group ID");
		softAssertion.assertNotSame(SearchID, newSearchID1);

		getSearchName(searchName).isElementAvailable(2);
		softAssertion.assertTrue(getSearchName(searchName).isElementAvailable(2));

		softAssertion.assertAll();
	}

	/**
	 * @param searchName
	 * @author Raghuram Date : 9/03/21 Description: creates new search group --UNDER
	 *         CONSTRUCTION
	 * @throws InterruptedException
	 */
	public String createNewSearchGrp(String nodeName, int count) throws InterruptedException {

		String childNodeName = null;
		// driver.Navigate().refresh();
		base.waitForElement(getCreatedNodeName(nodeName));
		selectNode1(nodeName);
		getCreatedNodeName(nodeName).waitAndClick(10);
		System.out.println("Clicked " + nodeName);
		Thread.sleep(3000);
		for (int i = 1; i <= count; i++) {
			getSavedSearchNewGroupButton().Click();
			System.out.println("Created new group");
			Thread.sleep(3000);
		}
		driver.Navigate().refresh();
		driver.scrollingToBottomofAPage();
		getSavedSearchNewGroupExpand().waitAndClick(10);
		childNodeName = getLastChildCreatedNode(nodeName).getText();
		// base.VerifySuccessMessage("Save search tree node successfully created.");
		base.stepInfo("Save search tree node successfully created under :" + childNodeName);
		System.out.println("Save search tree node successfully created under :" + childNodeName);

		return childNodeName;
	}

	/**
	 * @param searchName
	 * @author Raghuram Date : 9/03/21 Description: creates new search group
	 */
	public String createSearchGroupAndReturn(String searchName, String role) {
		String newNode = createNewSearchGrp(searchName);
		System.out.println("Via : " + role + " Created new node : " + newNode);
		base.stepInfo("Via : " + role + " Created new node : " + newNode);
		return newNode;
	}

	/**
	 * @param searchName
	 * @author Raghuram Date : 9/21/21 Description: creates new search group
	 *         Modified on : 03/02/22 modified by : Raghuram - as per new
	 *         implementation - still need to verify the impacted methods
	 * @throws InterruptedException
	 */
	public String createSearchGroupAndReturn(String groupName, String role, String verifyNodeEmptyInitally)
			throws InterruptedException {

		// Create SG and Get created node text
		String newNode = createNewSearchGrp(groupName);
		base.stepInfo("Via : " + role + " Created new node : " + newNode);

		// To check Empty
		if (verifyNodeEmptyInitally.equals("Yes")) {
			if (getSelectAnode(newNode).isElementAvailable(1)) {
				getSelectAnode(newNode).waitAndClick(10);
			} else if (getNodeFromTab(newNode, groupName).isElementAvailable(1)) {
				getNodeFromTab(newNode, groupName).waitAndClick(10);
				System.out.println(newNode + " : is clicked");
			}
			driver.waitForPageToBeReady();
			verifySavedSearch_isEmpty();
		}
		return newNode;
	}

	/**
	 * @param searchName
	 * @author Raghuram Date : 9/03/21 Description: creates new search group
	 */
	public void preRequestCreation(String searchName, String securityGroup) {
		// Choose search
		base.waitForElement(getChooseSearchRadiobtn(searchName));
		getChooseSearchRadiobtn(searchName);

		// Release Icon
		getReleaseIcon().waitAndClick(3);

		// CHoose SG
		base.waitForElement(getReleaseDocToSG(securityGroup));
		getReleaseDocToSG(securityGroup).waitAndClick(5);

		// Click Release Button
		base.waitForElement(getReleaseBtn());
		getReleaseBtn().waitAndClick(5);

		// Finalize release button
		base.waitForElement(getFinalizeReleaseButtonfromPopup());
		getFinalizeReleaseButtonfromPopup().waitAndClick(6);

		System.out.println(searchName + "released to SG : " + securityGroup);

	}

	/**
	 * @author Jeevitha
	 * @param searchName
	 * @param securitygroupname
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public void shareSavedSearchFromNode(final String searchName, String securitygroupname)
			throws ParseException, InterruptedException {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchNewGroupExpand().Visible();
			}
		}), Input.wait30);
		getSavedSearchNewGroupExpand().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchNewNode().Visible();
			}
		}), Input.wait30);
		getSavedSearchNewNode().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait120);
		getSavedSearch_SearchName().SendKeys(searchName);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSavedSearch_ApplyFilterButton().waitAndClick(20);
		Thread.sleep(5000);

		// get Search ID
		String SearchID = getSelectSearchWithID(searchName).getText();
		System.out.println(SearchID + " is Saved search ID");
		base.stepInfo(SearchID + " is Saved search ID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		getSelectWithName(searchName).waitAndClick(10);

		getShareSerachBtn().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShare_SecurityGroup(securitygroupname).Visible();
			}
		}), Input.wait30);
		getShare_SecurityGroup(securitygroupname).waitAndClick(10);

		getShareSaveBtnNew().waitAndClick(5);
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		Thread.sleep(2000);

		// click on share with security group tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchNewGroupExpand().Visible();
			}
		}), Input.wait30);
		getSavedSearchNewGroupExpand().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSearchNewNode(securitygroupname).Visible();
			}
		}), Input.wait30);
		getSearchNewNode(securitygroupname).waitAndClick(20);
		Thread.sleep(3000);

		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		// verify id should get changed
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSearchWithID(searchName).Visible();
			}
		}), Input.wait30);
		String newSearchID1 = getSelectSearchWithID(searchName).getText();
		System.out.println(newSearchID1 + " is Security group ID");
		UtilityLog.info(newSearchID1 + " is Security group ID");

		softAssertion.assertNotSame(SearchID, newSearchID1);

	}

	/**
	 * @author Jeevitha
	 * @param nodeName
	 * @throws InterruptedException
	 */
	public void shareSavedNodePA(String nodeName) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchGroupName(nodeName).Visible();
			}
		}), Input.wait60);
		getSavedSearchGroupName(nodeName).waitAndClick(10);
		getShareSerachBtn().waitAndClick(10);
		getShare_SecurityGroup("Shared with Default Security Group").waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShareSaveBtnNew().Visible();
			}
		}), Input.wait60);
		getShareSaveBtnNew().waitAndClick(10);
	}

	/**
	 * @author Jeevitha
	 * @param nodeName
	 * @return Stabilization changes implemented
	 */
	public boolean verifySharedNode(String nodeName) {
//		getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		rootGroupExpansion();
		driver.waitForPageToBeReady();
		if (getSharedGroupName(nodeName).isElementAvailable(5)) {
			getSharedGroupName(nodeName).waitAndClick(10);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @author Jeevitha
	 * @param nodeName
	 */
	public void deleteSharedNode(String nodeName) {
		driver.getWebDriver().navigate().refresh();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCollapsedSharedWithDefaultSecurityGroup().Visible();
			}
		}), Input.wait60);
		getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSharedGroupName(nodeName).Visible();
			}
		}), Input.wait60);
		getSharedGroupName(nodeName).waitAndClick(10);
		getSavedSearchDeleteButton().waitAndClick(10);
		getDeleteOkBtn().waitAndClick(10);
	}

	/**
	 * @author Jayanthi Ganesan
	 * @param SearchName
	 */

	public void savesearch_Edit(final String searchName1) {
		savedSearch_Searchandclick(searchName1);
		base.waitForElement(getSavedSearchEditButton());
		getSavedSearchEditButton().waitAndClick(10);
	}

	/**
	 * @author Jayanthi Ganesan
	 * @param SearchName,pureHits
	 * 
	 */
	public void savedSearchExecuteAndrefresh(final String searchName, int PureHits) {

		savedSearch_Searchandclick(searchName);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		if (getSearchName(searchName).isElementPresent() == true) {
			bc.passedStep("Saved advanced search query is displayed in saved searches list");
		} else {
			bc.failedStep("Saved advanced search query is not displayed in saved searches list");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchExecuteButton().Visible();
			}
		}), Input.wait30);
		getSavedSearchExecuteButton().Click();

		base.VerifySuccessMessage("Successfully added to background search.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Got notification!");
		getSavedSearchRefresh().Click();
		base.waitForElement(getSavedSearch_SearchName());
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getSelectWithName(searchName));
		getSelectWithName(searchName).ScrollTo();
		getSelectWithName(searchName).waitAndClick(10);
		getSavedSearch_ApplyFilterButton().Click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCountofDocs().Visible();
			}
		}), Input.wait60);

		softAssertion.assertEquals(getCountofDocs().getText(), String.valueOf(PureHits));
	}

	/**
	 * @author A Date: 9/14/21 Modified date:N/A Modified by: A Description :
	 * @Stabilization : changes done
	 */
	public void savedSearchEdit(String searchName1) {

		savedSearch_Searchandclick(searchName1);
		// base.waitForElement(getSavedSearchEditButton());
		getSavedSearchEditButton().waitAndClick(5);

	}

	/**
	 * @author A Date: 9/15/21 Modified date:N/A Modified by: A Description : Move
	 *         search form one node to another - Sprint 03
	 */
	public void moveSearchToAnotherFolder(String nodeName) {

		try {
			base.waitForElement(getMoveIcon());
			getMoveIcon().waitAndClick(5);
			base.waitForElement(getExpandIconfromPopup());
			getExpandIconfromPopup().waitAndClick(5);
			base.waitForElement(getSelectNodeToMove(nodeName));
			getSelectNodeToMove(nodeName).waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author A Date: 9/15/21 Modified date:N/A Modified by: A Description : -
	 *         Sprint 03
	 */
	public void moveActionButton(String action) {
		try {
			if (action.equals("Save")) {
				getMoveActionSave().Click();
			} else if (action.equals("Cancel")) {
				getMoveActionCancel().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author A Date: 9/15/21 Modified date:03/08/22 Modified by: A Description :
	 *         Saved Search Selection- Sprint 03
	 * @throws InterruptedException
	 * @Stabilization : changes done
	 */
	public void savedSearch_SearchandSelect(String searchName, String select) throws InterruptedException {

		driver.waitForPageToBeReady();
		base.waitForElement(getSavedSearch_SearchName());
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();
//		base.waitForElement(getSelectWithName(searchName));// impacts to be verified
		try {
			if (getSelectWithName(searchName).isElementAvailable(15)) {
				base.stepInfo("Search Found :" + searchName);
			} else {
				base.stepInfo("Search Not Found :" + searchName);
				if (select.equals("Yes")) {
					base.failedStep("Search Not Found :" + searchName); // impacts to be verified
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (select.equals("Yes")) {
			base.waitTime(5); // in order to handle abnormal wait activities
			// base.waitForElement(getSelectWithName(searchName));
//			getSelectWithName(searchName).isElementAvailable(5);
			Actions act=new Actions(driver.getWebDriver());
			act.moveToElement(getSelectWithName(searchName).getWebElement()).click().build().perform();
//			getSelectWithName(searchName).Click();
//			getSelectWithName(searchName).waitAndClick(6);
			getSelectWithName(searchName).checkIn();
		}

	}

	/**
	 * @author A Date: 9/15/21 Modified date:10/12/21 Modified by: Raghuram A
	 *         Description : Share Popup verification
	 */
	public void sharePoupVerificationOfAvailableSharedSG(String shareBtnStatus, String actionType) {

		ElementCollection listCollectionOfAvailableSharefromMenu = getAvailableShareListfromMenu();
		listOfAvailableSharefromMenu = base.availableListofElements(listCollectionOfAvailableSharefromMenu);
		System.out.println(listOfAvailableSharefromMenu.size());
		if (listOfAvailableSharefromMenu.size() == 0) {
			base.failedMessage("No Share with SG is available");
		}
		base.stepInfo("list Of Available Share from Menu");
		for (String a : listOfAvailableSharefromMenu) {
			System.out.println(a);
			base.stepInfo(a);
		}

		if (shareBtnStatus.equals("NotClicked")) {
			base.waitForElement(getShareSerachBtn());
			getShareSerachBtn().Click();
		} else {

		}
		String shareASearchPopup = getshareASearchPopup().getText();
		softAssertion.assertEquals(shareASearchPopup, "Share a Search");
		base.passedStep("Landed on shared search pop up window");

		driver.waitForPageToBeReady();
		ElementCollection listCollectionOfAvailableShareListfromShareASearchPopup = getAvailableShareListfromShareASearchPopup();
		listOfAvailableShareListfromShareASearchPopup = base
				.availableListofElements(listCollectionOfAvailableShareListfromShareASearchPopup);
		System.out.println(listOfAvailableShareListfromShareASearchPopup.size());
		if (listOfAvailableShareListfromShareASearchPopup.size() == 0) {
			base.failedMessage("No Share with SG is available in Popup as well");
		}
		base.stepInfo("list Of Available Share from ShareASearchPopup");
		for (String a : listOfAvailableShareListfromShareASearchPopup) {
			System.out.println(a);
			base.stepInfo(a);
		}

		// List comparision
		softAssertion.assertEquals(listOfAvailableSharefromMenu, listOfAvailableSharefromMenu);
		if (listOfAvailableSharefromMenu.equals(listOfAvailableShareListfromShareASearchPopup)) {
			System.out.println("Available Shared SG from menu is displayed in Share a search popup");
			base.passedStep("Available Shared SG from menu is displayed in Share a search popup");
		} else {
			System.out.println("Available Shared SG from menu not displayed in Share a search popup");
			base.failedStep("Available Shared SG from menu not displayed in Share a search popup");
		}

		if (actionType.equals("Close")) {
			getShareSerachCloseBtn().Click();
		}
		softAssertion.assertAll();
	}

	/**
	 * @author Jeevitha Description: Selects created Node Modified on : 2/10/22 by :
	 *         Raghuram
	 */
	public void selectNode1(String Node) {

		if (getSavedSearchExpandStats().isElementAvailable(5)) {
			sgExpansion();
		} else {
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			sgExpansion();
		}

		base.waitForElement(getCreatedNode(Node));
		getCreatedNode(Node).waitAndClick(20);
	}

	/**
	 * @author Raghuram.A
	 */
	public void sgExpansion() {
		String status = getSavedSearchExpandStats().GetAttribute("class");
		if (status.contains("closed")) {
			System.out.println("To be Expanded");
			getSavedSearchNewGroupExpand().waitAndClick(10);
		} else {
			System.out.println("Already Expanded");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param searchName  ---save search name to be renamed
	 * @param searchName2 ----renamed save search name
	 */
	public void verifyRenamedsavsearch(String searchName, String searchName2) {
		savedSearch_Searchandclick(searchName);
		renameSavedSearch(searchName, searchName2);
		savedSearch_Searchandclick(searchName2);
		if (getSearchName(searchName2).isElementAvailable(4)) {
			base.passedStep("Sucesfully verified the Renamed saved search");
		} else {
			base.failedStep("Renamed saved search is not displayed as expected");

		}
	}

	/**
	 * @author A Date: 9/21/21 Modified date:12/9/21 Modified by: Raghuram A
	 *         Description : verifySavedSearch_isEmpty
	 * @throws InterruptedException
	 * @Stabilization : changes implemented
	 */
	public Boolean verifySavedSearch_isEmpty() throws InterruptedException {

		driver.waitForPageToBeReady();
		if (getsearchResultEmptyMessage().isElementAvailable(3)) {
			base.stepInfo("No Records found");
			System.out.println("No Records found");
			return false;
		} else {
			base.stepInfo("Data Available");
			System.out.println("Data Available");
			return true;
		}
	}

	/**
	 * @author Raghuram A Date: 9/21/21 Modified date:3/25/21 Modified by: Jeevitha
	 *         A Description : verifyMoveActionSSMethod Test case steps
	 * @throws InterruptedException
	 * @Stabilization : changes done
	 */
	public void verifyMoveActionSSMethod(String savedSearchName1, String newNode, String newNode2,
			Boolean seachToAnotherFolder, Boolean subFolerToFolder, Boolean subFolerToFolderMoveActionVerify)
			throws InterruptedException {

		// Navigate to Saved Search Page
		navigateToSSPage();

		if (seachToAnotherFolder) {
			// Move Search from one folder to another folder
			selectNode1(newNode2);
			base.stepInfo("Saved : " + savedSearchName1 + " under " + newNode2); // -- to modify
			savedSearch_SearchandSelect(savedSearchName1, "Yes");
			moveSearchToanotherGroup(newNode, savedSearchName1);
			driver.getWebDriver().navigate().refresh();
		}

		if (subFolerToFolder) {
			// Move sub-folder to the selected folder
			driver.waitForPageToBeReady();
			selectNode1(newNode);
			moveSearchToAnotherFolder(newNode2);
			moveActionButton("Save");
			base.stepInfo("Moved : " + newNode + " to " + newNode2);
			driver.getWebDriver().navigate().refresh();
		}

		if (subFolerToFolderMoveActionVerify) {
			// Verify moved sub-folder is present in the selected folder
			driver.waitForPageToBeReady();
			getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(10);
			rootGroupExpansion();
			driver.waitForPageToBeReady();
			System.out.println("Main folder");
			base.stepInfo("Main folder");
			verifyNodePresent(newNode);

			base.waitForElement(getSelectAnode(newNode2));
			getSelectAnode(newNode2).waitAndClick(6);
			rootGroupExpansion();
			System.out.println("Inside moved folder" + newNode2);
			base.stepInfo("Inside moved folder" + newNode2);
			verifyNodePresent(newNode);
		}

	}

	/**
	 * @author Raghuram A Date: 9/21/21 Modified date:N/A Modified by: N/A
	 *         Description : verifyNodePresent
	 * @return
	 * @throws InterruptedException
	 * @Stabilization - updates ON
	 * @TOcheck - verifyMoveActionAsRev() - verifyMoveActionAsRMU() -
	 *          verifyMoveActionSSMethod() -
	 */
	public boolean verifyNodePresent(String newNode) {

		if (getSelectAnode(newNode).isElementAvailable(5)) {
			System.out.println(newNode + " is Present");
			base.stepInfo(newNode + " is Present");
			return true;
		} else {
			System.out.println(newNode + " is not Present");
			base.stepInfo(newNode + " is not Present");
			return false;
		}

	}

	/**
	 * @author Raghuram A Date: 9/21/21 Modified date:12/9/21 Modified by: Raghuram
	 *         Description
	 * @param searchName  ---save search name to be renamed
	 * @param searchName2 ----renamed save search name
	 * @throws InterruptedException
	 * @Stabilization : changes implemented
	 */
	public void verifyRenamedsavedSearch(String searchName, String searchName2) throws InterruptedException {
		savedSearch_SearchandSelect(searchName, "Yes");
		renameSavedSearch(searchName2);
		savedSearch_SearchandSelect(searchName2, "Yes");
		if (getSearchName(searchName2).isElementAvailable(5)) {
			base.passedStep("Sucesfully verified the Renamed saved search");
		} else {
			base.failedStep("Renamed saved search is not displayed as expected");
		}
	}

	/**
	 * Method Overloaded : renameSavedSearch();
	 * 
	 * @author Raghuram A Date: 9/21/21 Modified date:N/A Modified by: N/A
	 *         Description Description: Rename search in saved Search page
	 * @param searchName1
	 * @param searchName2
	 */
	public void renameSavedSearch(String searchName2) {
		try {
			getRenameButton().Click();
			getRenameTextdfield().SendKeys(searchName2);
			base.waitForElement(getRenameSaveBtn());
			getRenameSaveBtn().Click();
			driver.waitForPageToBeReady();
			base.VerifySuccessMessage("Rename of Saved Search done successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram A Date: 9/22/21 Modified date:N/A Modified by: N/A
	 *         Description Description: moveSearchToanotherGroup
	 */
	public void moveSearchToanotherGroup(String newNode, String savedSearchName) throws InterruptedException {
		moveSearchToAnotherFolder(newNode);
		moveActionButton("Save");
		base.stepInfo("Moved : " + savedSearchName + " to " + newNode);
		// Make sure moved search is present in the Node
		driver.waitForPageToBeReady();
		selectNode1(newNode);
		savedSearch_SearchandSelect(savedSearchName, "No");
	}

	/**
	 * @author Raghuram A Date: 9/22/21 Modified date:12/9/21 Modified by: N/A
	 *         Description Description: verifySharedGroupSearch
	 * @Stabilization : changes done
	 */
	public void verifySharedGroupSearch(String securitygroupname, String searchName) throws InterruptedException {

		// click on share with security group tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		savedSearch_SearchandSelect(searchName, "No");
		softAssertion.assertTrue(getSearchName(searchName).isElementAvailable(2));
	}

	/**
	 * @author Raghuram A Date: 9/22/21 Modified date:10/06/21 Modified by: Raghuram
	 *         A Description Description: shareSavedNodePA
	 * @return
	 */
	public String shareSavedNodePA(String SGtoShare, String nodeName, Boolean verifyNode, Boolean verifySearch,
			String searchName) throws InterruptedException {
		// Share to SG
		getSavedSearchGroupName(nodeName).waitAndClick(10);
		getShareSerachBtn().waitAndClick(10);
		
		base.waitForElement(getShare_SecurityGroup(SGtoShare));
		
		// Updates
		if (getShare_SecurityGroup(SGtoShare).isElementAvailable(2)) {
			getShare_SecurityGroup(SGtoShare).waitAndClick(10);
		} else {
			getShare_SecurityGroupC(SGtoShare).waitAndClick(10);
		}

		// Perform Share Action
		getShareSaveBtnNew().waitAndClick(10);
		System.out.println("Shared to : " + SGtoShare);
		base.stepInfo("Shared to : " + SGtoShare);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		savedSearch_SearchandSelect(searchName, "No");
		driver.waitForPageToBeReady();

		// get Search ID
		searchID = GetSearchID(searchName);
		base.stepInfo("Search ID before Share : " + searchID);
		driver.getWebDriver().navigate().refresh();

		if (verifyNode) {
			verifySharedNode(SGtoShare, nodeName, searchID, verifySearch, searchName, true);
		}
		return searchID; // newly modified
	}

	/**
	 * @author Raghuram A Date: 9/22/21 Modified date:7/10/21 Modified by: Jeevitha
	 *         Description Description: verifySharedNode
	 * @Stabilization : changes done
	 */
	public void verifySharedNode(String SGtoShare, String nodeName, String searchID, Boolean verifySearch,
			String searchName, Boolean compareSearchID) throws InterruptedException {
		// Make sure shared Node reflected in the SG
		getSavedSearchGroupName(SGtoShare).waitAndClick(10);
//		getSavedSearchNewGroupExpand().waitAndClick(20);
		rootGroupExpansion(); // to handle dynamic expansion as per new updates
		driver.waitForPageToBeReady();

		// verify id should get changed
		try {
			if (getSavedSearchGroupName(nodeName).isElementAvailable(3)) {
				System.out.println(nodeName + " : Search group is Present in " + SGtoShare);
				base.stepInfo(nodeName + " : Search group is Present in " + SGtoShare);
			} else {
				System.out.println(nodeName + " : Search group is not Present in " + SGtoShare);
				base.stepInfo(nodeName + " : Search group is Present not in " + SGtoShare);
			}

		} catch (Exception e) {

		}

		if (verifySearch) {
			getSavedSearchGroupName(nodeName).waitAndClick(5);
			try {
				savedSearch_SearchandSelect(searchName, "No");
				if (getSearchName(searchName).isElementAvailable(3)) {
					System.out.println(searchName + " : is Present");
					base.stepInfo(searchName + " : is Present");

					searchIDtoCompare = getSelectSearchWithID(searchName).getText();
					System.out.println(searchIDtoCompare);
					base.stepInfo("Search ID after Shared : " + searchIDtoCompare);

					// Doc Count Availability
					docResultCOuntCHeck(searchName);

				} else {
					System.out.println(searchName + " : is not Present");
					base.stepInfo(searchName + " : is not Present");
				}

			} catch (Exception e) {

			}

		}

		if (compareSearchID) {
			// softAssertion.assertNotSame(searchID, searchIDtoCompare);
			if (!searchID.equalsIgnoreCase(searchIDtoCompare)) {
				base.stepInfo("Different Search ID");
			} else {
				base.stepInfo("Same Search ID");
			}

		}
		// softAssertion.assertAll();
	}

	/**
	 * @author Raghuram A Date: 9/22/21 Modified date:N/A Modified by: N/A
	 *         Description Description: renameSearchGroup
	 * @throws InterruptedException
	 */
	public String renameSearchGroup(String newNode) throws InterruptedException {
		String SearchGroupRenamed = "RenamedSearchGroup" + Utility.dynamicNameAppender();
		getSavedSearchGroupName(newNode).waitAndClick(10);
		try {
			// Rename SearchGroup
			if (getRenameButton().Enabled() == true) {
				base.passedStep("Rename Action is Enabled and has Access");
				System.out.println("Rename Action is Enabled and has Access");
			}
		} catch (Exception e) {
			System.out.println("Not Enabled - No Access");
		}

		getRenameButton().waitAndClick(10);
		getRenameSearchGroupNode(newNode).SendKeysNoClear(SearchGroupRenamed);
		getTermReportTitle().Click();
		base.VerifySuccessMessage("Save search tree node successfully updated.");
		base.CloseSuccessMsgpopup();
		base.stepInfo(newNode + "Renamed as : " + SearchGroupRenamed);

		return SearchGroupRenamed;
	}

	/**
	 * @author Raghuram A Date: 9/24/21 Modified date:N/A Modified by: N/A
	 *         Description Description: renameSearchGroup
	 */
	public void SavedSearchToTermReport(String SearchGroupRenamed) {
		try {
			driver.waitForPageToBeReady();
			if (getSavedSearchToTermReport().Enabled()) {
				base.passedStep("Report Action is Enabled and has Access");
				getSavedSearchToTermReport().waitAndClick(5);
				Thread.sleep(15000); // In order to handle wait time till report loads
				driver.waitForPageToBeReady();
				searhTRpage.ValidateSearchTermreportSaveandImpact(SearchGroupRenamed, true);
			}
		} catch (Exception e) {
			System.out.println("Not Enabled - No Access");
		}
	}

	/**
	 * @author Jeevitha
	 * @return
	 */
	public String savedSearchExecute_Draft(final String searchName, int PureHits) {

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchExecuteButton().Visible();
			}
		}), Input.wait30);
		getSavedSearchExecuteButton().waitAndClick(10);

		try {
			base.VerifySuccessMessage("Successfully added to background search.");
		} catch (Exception e) {
			base.VerifySuccessMessage("Successfully added to background search.\r\n"
					+ "In the search group being executed, searches that are referring to other searches in the same search group will be errored out. You can select the errored searches and run them individually.");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Got notification!");

		getSavedSearch_ApplyFilterButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCountofDocs().Visible();
			}
		}), Input.wait60);
		String docCount;
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}
		if (PureHits == 0) {
			docCount = getCountofDocs().getText();
			System.out.println("NO Of Docs Executed :" + docCount);
			bc.stepInfo("NO Of Docs Executed :" + docCount);

		} else {
			docCount = getCountofDocs().getText();
			System.out.println(
					"NO Of Docs Executed :" + docCount + ", Actual PureHit Count : " + String.valueOf(PureHits));
			bc.stepInfo("NO Of Docs Executed :" + docCount + ", Actual PureHit Count : " + String.valueOf(PureHits));
			softAssertion.assertEquals(docCount, String.valueOf(PureHits));
		}
		return docCount;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param searchName
	 * @return
	 */
	public String VerifyThresholdValueInSavedAudiosearch(String searchName) {
		String Threshold_savedSearch1 = null;
		if (getCountofThreshold().isElementPresent()) {
			Threshold_savedSearch1 = getCountofThreshold().getText();
			base.passedStep("Threshold Value is displayed In saved Search page" + Threshold_savedSearch1);
		} else {

			base.failedStep("Threshold Element not displayed");
		}
		return Threshold_savedSearch1;

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param searchName
	 * @return
	 */
	public String VerifyPureHitInSavedAudiosearch(String searchName) {
		String PureHit_savedSearch1 = null;
		if (getCountofDocs().isElementPresent()) {
			PureHit_savedSearch1 = getCountofDocs().getText();
			base.passedStep("PureHit Value is displayed In saved Search page" + PureHit_savedSearch1);
		} else {

			base.failedStep("PureHitCount Element not displayed");
		}
		return PureHit_savedSearch1;

	}

	/**
	 * @author Raghuram A Date: 9/27/21 Modified date:N/A Modified by: N/A
	 *         Description Description: verifySecurityGroupList
	 * @param sgListToCompare
	 * @throws InterruptedException
	 */
	public void verifySecurityGroupList(List<String> sgListToCompare, Boolean onlySG, Boolean toPrefix,
			Boolean comparisionOfSG, Boolean mainFoldersCheckRequired, String role) throws InterruptedException {
		String mainDefaultFolders[] = { "My Saved Search", Input.shareSearchDefaultSG };
		List<String> mainDefaultFoldersList = new ArrayList<>();
		List<String> expectedList = new ArrayList<>();

		ElementCollection listCollectionOfAvailableSharefromMenu = getAvailableShareListfromMenu();
		listOfAvailableSharefromMenu = base.availableListofElements(listCollectionOfAvailableSharefromMenu);
		base.stepInfo("list Of Available Share from SavedSearch Menu");
		if (onlySG) {
			listOfAvailableSharefromMenu.remove(Input.shareSearchPA);
		}
		Collections.sort(listOfAvailableSharefromMenu);
		System.out.println("List of Security groups from SavedSearch Page");
		base.stepInfo("List of Security groups from SavedSearch Page");
		for (String a : listOfAvailableSharefromMenu) {
			System.out.println(a);
			base.stepInfo(a);
		}
		if (toPrefix) {
			System.out.println("List of Available Security groups available for the account");
			base.stepInfo("List of Available Security groups available for the account");
			for (int i = 0; i < sgListToCompare.size(); i++) {
				sgListToCompare.set(i, "Shared with " + sgListToCompare.get(i));
				System.out.println(sgListToCompare.get(i).toString());
				base.stepInfo(sgListToCompare.get(i).toString());
			}
		}
		if (comparisionOfSG) {
			softAssertion.assertEquals(listOfAvailableSharefromMenu, sgListToCompare);
			if (listOfAvailableSharefromMenu.equals(sgListToCompare)) {
				System.out.println("Saved Search screen is loaded with all the available Security groups");
				base.passedStep("Saved Search screen is loaded with all the available Security groups");
			} else {
				System.out.println("Saved Search screen is loaded with missing Security groups");
				base.failedStep("Saved Search screen is loaded with missing Security groups");
			}
		}
		if (mainFoldersCheckRequired) {
			try {
				base.waitForElement(getMainFolders("My Saved Search"));
				System.out.println(getMainFolders("My Saved Search").Displayed());
				getMainFolders("My Saved Search").Displayed();
				System.out.println("My Saved Search folder available");
				base.passedStep("My Saved Search folder available");
			} catch (NoSuchElementException e) {
				System.out.println("My Saved Search folder not available");
				base.stepInfo("My Saved Search folder not available");
			}
			try {
				base.waitForElement(getMainFolders(Input.shareSearchPA));
				getMainFolders(Input.shareSearchPA).Displayed();
				System.out.println("PA folder available");
				base.passedStep("PA folder available");
			} catch (NoSuchElementException e) {
				System.out.println("PA not available");
				base.stepInfo("PA not available");
			}
		}
		if (role.equals("RMU") || role.equals("Rev")) {
			expectedList = Arrays.asList(mainDefaultFolders);
			base.stepInfo("-------Expected Result------------");
			System.out.println("--------Expected Result--------------");
			for (String b : expectedList) {
				base.stepInfo(b);
				System.out.println(b);
			}
			ElementCollection listOfMainFolders = getMainFoldersList();
			mainDefaultFoldersList = base.availableListofElements(listOfMainFolders);
			System.out.println("Actual Result ---- List of Folders available : " + role);
			base.stepInfo("Actual Result ----List of Folders available : " + role);
			for (String a : mainDefaultFoldersList) {
				System.out.println(a);
				base.stepInfo(a);
			}
			softAssertion.assertEquals(mainDefaultFoldersList, expectedList);
			if (mainDefaultFoldersList.equals(expectedList)) {
				System.out.println(role + ": Matches ");
				base.passedStep(role + " : As Expected");
			} else {
				System.out.println(role + ": mis-Matches ");
				base.failedStep(role + " : Not As Expected");
			}
		}
	}

	/**
	 * @author Raghuram A Date: 9/27/21 Modified date:12/4/21 Modified by: Raghuram
	 *         A Description Description: UIchangesVerificationFlow
	 * @param sgListToCompare
	 * @throws InterruptedException
	 */
	public void UIchangesVerificationFlow(String userRole) throws InterruptedException {
		if (userRole.equals(Input.pa1userName)) {
			// Get data from Security Group page
			sg = new SecurityGroupsPage(driver);
			driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
			driver.waitForPageToBeReady();
			sgList = sg.GetSecurityGrouplist();
			Collections.sort(sgList);

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			verifySecurityGroupList(sgList, true, true, true, true, "PA");
		} else if (userRole.equals(Input.rmu1userName)) {
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			verifySecurityGroupList(sgList, false, false, false, false, "RMU");
		} else if (userRole.equals(Input.rev1userName)) {
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			verifySecurityGroupList(sgList, false, false, false, false, "Rev");
		}
	}

	/**
	 * @param searchName
	 * @author Raghuram A Date : 9/28/21 Description: creates multiple new search
	 *         group - In-progress Modified on : 2/22/22 modified by : Raghuram
	 * @throws InterruptedException
	 */
	public List<String> createSGAndReturn(String role, String verifyNodeEmptyInitally, int size)
			throws InterruptedException {
		String newNode=null;
		List<String> newNodeList = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			// Create SearchGroup

			base.waitForElement(getSavedSearchNewGroupButton());
			getSavedSearchNewGroupButton().waitAndClick(2);
			base.waitTime(2);// to handle wait for observing the text
			base.hitKey(KeyEvent.VK_ENTER);// base on new implementation
//			getSavedSearchNewGroupButton().waitAndClick(2);

			/*
			 * 
			 * try { if (getSuccessPopup().isElementAvailable(2)) {
			 * base.VerifySuccessMessage("Save search tree node successfully created.");
			 * base.CloseSuccessMsgpopup(); } else if
			 * (getErrorPopup().isElementAvailable(3)) {
			 * base.VerifyErrorMessage("Only 5 levels deep profiling allows");
			 * base.stepInfo("-------------Only 5 levels deep profiling allows----------");
			 * break; } } catch (Exception e) { break; }
			 */
			
			
			// getSavedSearchExpandStats
			
			driver.Navigate().refresh();
			base.waitTime(2);
			
			try {
				if(base.ValidateElement_PresenceReturn(getSavedSearchChildNodesExists())) {
					getSavedSearchNewGroupExpand().waitAndClick(20);
					base.waitForElement(currentClickedNode());
					newNode = getChildOfCurrentClickedNode(i-1).getText();	
				}else {
					newNode = currentClickedNode().getText();
				}
				
				newNodeList.add(newNode);
				getSavedSearchGroupName(newNodeList.get(0)).waitAndClick(10);
//				base.waitTime(2);// to handle wait for observing the text
				
				
			} catch (Exception e) {
				System.out.println("No node");
			}
			
			
			
			
			
			
			System.out.println("Via : " + role + " Created new node : " + newNode);

			base.stepInfo("Via : " + role + " Created new node : " + newNode);

			if (verifyNodeEmptyInitally.equals("Yes")) {
				driver.waitForPageToBeReady();
				verifySavedSearch_isEmpty();
			}

		}
		return newNodeList;
	}

	/**
	 * @param searchName
	 * @author Raghuram Date : 9/28/21 Description: Only Execute SS and Verify
	 *         Modified on : N/A modified by : N/A
	 * @throws InterruptedException
	 */
	public void savedSearchExecute2(final String searchName, int PureHits) {

		// final BaseClass bc = new BaseClass(driver);
		final int Bgcount = base.initialBgCount();

		base.waitForElement(getSavedSearchExecuteButton());
		getSavedSearchExecuteButton().Click();
		driver.waitForPageToBeReady();

		base.VerifySuccessMessage("Successfully added to background search.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Got notification!");

		getSavedSearch_ApplyFilterButton().Click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		base.waitForElement(getCountofDocs());

		softAssertion.assertEquals(getCountofDocs().getText(), String.valueOf(PureHits));

	}

	/**
	 * @author Raghuram Date : 9/29/21 Description: verifySearchContents Modified on
	 *         : N/A modified by : N/A
	 * @throws InterruptedException
	 */
	public void verifySearchContents(String searchName, Boolean searchViaFilter, Boolean compareSearchID)
			throws InterruptedException {
		if (searchViaFilter) {
			savedSearch_SearchandSelect(searchName, "No");
			base.waitForElement(getSelectSearchWithID(searchName));
			searchIDtoCompare = getSelectSearchWithID(searchName).getText();
			System.out.println(searchIDtoCompare);
			base.stepInfo(searchName + " - Search ID : " + searchIDtoCompare);
		}
		if (compareSearchID) {
			try {
				softAssertion.assertNotSame(searchID, searchIDtoCompare);
				base.stepInfo("Different Search ID");
			} catch (Exception e) {
				base.stepInfo("Same ID");
			}
		}
	}

	/**
	 * @author Raghuram Date : 9/29/21 Description: GetSearchID Modified on : N/A
	 *         modified by : N/A
	 * @throws InterruptedException
	 */
	public String GetSearchID(String searchName) {
		// get Search ID
		String searchiD = null;
		try {
			// savedSearch_SearchandSelect(searchName, "No");
			base.waitForElement(getSelectSearchWithID(searchName));
			base.wait(1);
			searchiD = getSelectSearchWithID(searchName).getText();
			System.out.println(searchiD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchiD;
	}

	/**
	 * @author Raghuram Date : 9/29/21 Description: GetSearchID Modified on :
	 *         10-25-21 modified by : Raghuram
	 * @throws InterruptedException
	 */
	public void validatingSaves(String SGtoShare, String newNode, String searchName1) throws InterruptedException {
		search = new SessionSearch(driver);
		driver.waitForPageToBeReady();

		search.saveSearchAction();
		driver.waitForPageToBeReady();

		if (search.getSavedSearchExpandStatsC().isElementAvailable(6)) {
//	            base.waitForElement(search.getExpandAllTab());
//	            search.getExpandAllTab().waitAndClick(5);
			search.getSavedSearchExpandStatsC().waitAndClick(5);
		}

		base.waitForElement(search.getExpandSecurityGroupOw());
		search.getExpandSecurityGroupOw().waitAndClick(5);

		if (search.getNodeOfSG(newNode).isElementAvailable(10)) {
			search.getNodeOfSG(newNode).waitAndClick(5);

		} else {
			base.waitForElement(search.getSecurityGroupNewNode(newNode));
			search.getSecurityGroupNewNode(newNode).waitAndClick(5);
		}

		Actions ac = new Actions(driver.getWebDriver());
//		ac.moveToElement(search.getNodeToOw(newNode).getWebElement()).build().perform();
		base.waitTime(3);
		ac.click().build().perform();

		base.waitForElement(search.getNodeToOw(newNode));
		search.getNodeToOw(newNode).waitAndClick(5);

		base.waitForElement(search.getChooseSearchToOverwrite(searchName1));
		search.getChooseSearchToOverwrite(searchName1).waitAndClick(5);

		base.waitForElement(search.getSaveSearch_SaveButton());
		search.getSaveSearch_SaveButton().Click();

		driver.waitForPageToBeReady();

		base.stepInfo("Executing search saved under My Searches with different ID");
		search.saveSearch(searchName1);

		base.stepInfo("Executed search saved under Shared with groups with different ID");
		search.saveSearchAtAnyRootGroup(searchName1, SGtoShare);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		verifySearchContents(searchName1, true, true);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		getSavedSearchNewGroupExpand().waitAndClick(20);
		getSavedSearchGroupName(SGtoShare).waitAndClick(10);
	}

	/**
	 * @author Jeevitha Date : 9/29/21 Description: uploadBatchFile_New Modified on
	 *         : N/A modified by : N/A
	 */
	public void uploadBatchFile_New(final String batchFile) {
		driver.waitForPageToBeReady();

		ArrayList<Integer> actualCounts = new ArrayList<Integer>();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchUploadButton().Visible();
			}
		}), Input.wait30);
		getBatchUploadButton().waitAndClick(5);
		System.out.println("Clicked on Upload button..");
		UtilityLog.info("Clicked on Upload button..");

		System.out.println("Clicked on Batch Upload Button.........");
		UtilityLog.info("Clicked on Batch Upload Button.........");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFile().Visible();
			}
		}), Input.wait30);

		System.out.println(System.getProperty("user.dir") + Input.batchFileNewLocation + batchFile);
		UtilityLog.info(System.getProperty("user.dir") + Input.batchFileNewLocation + batchFile);
		getSelectFile().SendKeys(System.getProperty("user.dir") + Input.batchFileNewLocation + batchFile);
		getSubmitToUpload().Click();
		driver.waitForPageToBeReady();

		base.VerifySuccessMessage("File uploaded successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 12;
			}
		}), Input.wait60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySeraches().Visible();
			}
		}), Input.wait30);
		getMySeraches().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUploadedFile(batchFile).Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSelectUploadedFile(batchFile).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfSavedSearchToBeShown().Visible();
			}
		}), Input.wait30);
		getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCounts().Visible();
			}
		}), Input.wait30);

		driver.scrollingToBottomofAPage();
		String value = "0";
		for (int i = 1; i <= getCounts().size(); i++) {
			try {
				value = driver.FindElement(By.xpath("//*[@id='SavedSearchGrid']/tbody/tr[" + i + "]/td[4]")).getText();
				actualCounts.add(Integer.parseInt(value));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				value = "0";
				actualCounts.add(Integer.parseInt(value));
			}

		}

		System.out.println("ActualCounts :" + actualCounts);
		UtilityLog.info("ActualCounts :" + actualCounts);

		// Delete uploaded set
		driver.scrollPageToTop();
		getSavedSearchDeleteButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDeleteOkBtn().Visible();
			}
		}), Input.wait60);
		getDeleteOkBtn().waitAndClick(10);

		base.VerifySuccessMessage("Save search tree node successfully deleted.");
	}

	/**
	 * @author Jeevitha Date : 9/30/21 Description: renameFile with filePath as
	 *         parameter Modified on : N/A modified by : N/A
	 */
	public String renameFile(String filePath) {
		String FileName = null;
		// back up TCs zip file for later verification
		File destinationFolder = new File(System.getProperty("user.dir") + filePath);
		File sourceFolder = new File(System.getProperty("user.dir") + filePath);

		if (!destinationFolder.exists()) {
			destinationFolder.mkdirs();
		}

		// Check weather source exists and it is folder.
		if (sourceFolder.exists() && sourceFolder.isDirectory()) {

			// Get list of the files and iterate over them
			File[] listOfFiles = sourceFolder.listFiles();

			if (listOfFiles != null) {
				System.out.println(1);
				for (File child : listOfFiles) {
					if (child.isFile()) { // rename only files not folders
						// Move files to destination folder
						FileName = "BatchUpload" + Utility.dynamicNameAppender() + ".xlsx";
						child.renameTo(new File(destinationFolder + "\\" + FileName));
					}
				}

				// Add if you want to delete the source folder
				// sourceFolder.delete();
			}
		} else {
			System.out.println(sourceFolder + "  Folder does not exists");
			UtilityLog.info(sourceFolder + "  Folder does not exists");
		}

		// delete last TCs PDF from PDFs folder
		File file = new File("C:\\BatchPrintFiles\\PDFs");
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}
		return FileName;
	}

	/**
	 * 
	 * @author Raghuram A Date: 10/5/21 Modified date:3/24/22 Modified by:
	 *         Description :collectionOfSearchIdsFromNodeCollections updated
	 *         newNodeList.size() -> nodeSearchpair.size() - S - updated dynamically
	 *         based on new implementation - yet to verify the impacts
	 */
	public HashMap<String, String> collectionOfSearchIdsFromNodeCollections(List<String> newNodeList,
			HashMap<String, String> nodeSearchpair, HashMap<String, String> searchGroupSearchpID)
			throws InterruptedException {
		String node = null;
		for (int i = 0; i <= nodeSearchpair.size() - 1; i++) {
			
			node = newNodeList.get(i);
			
			base.waitForElement(getSavedSearchGroupName(node));
			Actions act=new Actions(driver.getWebDriver());
			act.moveToElement(getSavedSearchGroupName(node).getWebElement()).click().build().perform();
//			getSavedSearchGroupName(node).waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitTime(3);// To handle abnormal waits
			getSavedSearchRefresh().Click();
			if (verifySavedSearch_isEmpty()) {
				// get Search ID
				System.out.println("Node :-"+ node);
				System.out.println("Node :-"+ nodeSearchpair.get(node));
				String searchiD = GetSearchID(nodeSearchpair.get(node));
				
				searchGroupSearchpID.put(nodeSearchpair.get(node), searchiD);
				try {
					if (i != nodeSearchpair.size() - 1) {
						rootGroupExpansion(); // not required at direct situation added in order to prevent future
												// impact
					}
					getSavedSearchGroupName(node).waitAndClick(5);
				} catch (Exception e) {
					System.out.println("End of Node");
				}
			} else {
				searchGroupSearchpID.put(nodeSearchpair.get(node), "No Data found");
			}
		}

		return searchGroupSearchpID;
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:3/24/22 Modified by:
	 *         Description :childNodeSelectionToShare - updated dynamically based on
	 *         new implementation - yet to verify the impacts
	 */
	public String childNodeSelectionToShare(int selectIndex, List<String> newNodeList) throws InterruptedException {
		String nodeSelected = null;
		rootGroupExpansion();
		for (int i = 0; i <= selectIndex; i++) {
			nodeSelected = newNodeList.get(i);
			getSavedSearchGroupName(nodeSelected).waitAndClick(5);
			base.waitTime(3);
			try {
				if (i != selectIndex) {
					rootGroupExpansion(); // not required at direct situation added in order to prevent future impact
				}
				getSavedSearchGroupName(nodeSelected).waitAndClick(5);
				System.out.println("Final : " + nodeSelected);
			} catch (Exception e) {
				System.out.println("End of Node");
			}
		}

		return nodeSelected;
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:11/30/21 Modified by: Raghuram
	 *         Description :verifyImpactinSharedchildNodes - included Result count
	 *         updated for(); newNodeList.size() -> nodeSearchpair.size() - S
	 * @throws InterruptedException
	 */
	public void verifyImpactinSharedchildNodes(String SGtoShare, List<String> newNodeList, int selectIndex,
			HashMap<String, String> nodeSearchpair, HashMap<String, String> searchGroupSearchpIDpair)
			throws InterruptedException {
		getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		rootGroupExpansion();
		String node = null, searchiD;
		for (int i = 0; i <= nodeSearchpair.size() - 1; i++) {
			node = newNodeList.get(i);
			
			// verify id should get changed
			
			if (getSavedSearchGroupName(node).isElementAvailable(5)) {
				System.out.println(node + " : Search group is Present in " + SGtoShare);
				base.passedStep(node + " : Search group is Present in " + SGtoShare);
				getSavedSearchGroupName(node).Click();
				if (i >= selectIndex) {
					savedSearch_SearchandSelect(nodeSearchpair.get(node), "No");
					driver.waitForPageToBeReady();
					// get Search ID
					base.waitTime(3);
					searchiD = GetSearchID(nodeSearchpair.get(node));
					base.stepInfo("Search ID after Share : " + searchiD);
					base.stepInfo(searchGroupSearchpIDpair.get(nodeSearchpair.get(node)));
					base.textCompareNotEquals(searchiD, searchGroupSearchpIDpair.get(nodeSearchpair.get(node)),
							"Different Search ID", "Same Search ID");

					// Result count
					String resultCount = getSelectSearchWithResultCount(nodeSearchpair.get(node)).getText();
					if (resultCount.length() > 0) {
						base.stepInfo("Available Result Count : " + resultCount);
					} else {
						base.stepInfo("Result Count is empty : " + resultCount);
					}

				} else {
					verifySavedSearch_isEmpty();
					base.passedStep("Not the selected search group");
				}
//				getSavedSearchNewGroupExpand().waitAndClick(20);
				rootGroupExpansion();
			} else {
				System.out.println(node + " : Search group is not Present in " + SGtoShare);
				base.failedStep(node + " : Search group is not Present in " + SGtoShare);
			}
		}
	}
	
	public void verifyImpactinSharedchildNodes(String SGtoShare, String node,List<String> newNodeList, int selectIndex,
			HashMap<String, String> nodeSearchpair, HashMap<String, String> searchGroupSearchpIDpair)
			throws InterruptedException {
		getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		rootGroupExpansion();
		String searchiD=null;
		
		String Parentnode = newNodeList.get(0);
		getSavedSearchGroupName(Parentnode).Click();
		getSavedSearchNewGroupExpand().waitAndClick(20);
			
			// verify id should get changed
			
			if (getSavedSearchGroupName(node).isElementAvailable(5)) {
				System.out.println(node + " : Search group is Present in " + SGtoShare);
				base.passedStep(node + " : Search group is Present in " + SGtoShare);
				getSavedSearchGroupName(node).Click();
				
					savedSearch_SearchandSelect(nodeSearchpair.get(node), "No");
					driver.waitForPageToBeReady();
					// get Search ID
					base.waitTime(3);
					searchiD = GetSearchID(nodeSearchpair.get(node));
					base.stepInfo("Search ID after Share : " + searchiD);
					base.stepInfo(searchGroupSearchpIDpair.get(nodeSearchpair.get(node)));
					base.textCompareNotEquals(searchiD, searchGroupSearchpIDpair.get(nodeSearchpair.get(node)),
							"Different Search ID", "Same Search ID");

					// Result count
					String resultCount = getSelectSearchWithResultCount(nodeSearchpair.get(node)).getText();
					if (resultCount.length() > 0) {
						base.stepInfo("Available Result Count : " + resultCount);
					} else {
						base.stepInfo("Result Count is empty : " + resultCount);
					}

//				getSavedSearchNewGroupExpand().waitAndClick(20);
				rootGroupExpansion();
			} else {
				System.out.println(node + " : Search group is not Present in " + SGtoShare);
				base.failedStep(node + " : Search group is not Present in " + SGtoShare);
			}
		}
	
	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description
	 *         :sortedMapList
	 */
	public void sortedMapList(HashMap<String, String> nodeSearchpair) {
		for (Entry<String, String> entry : nodeSearchpair.entrySet()) {
			System.out.println(entry.getKey() + " => " + entry.getValue());
		}
		// Creating an ArrayList with the HashMap keys
		ArrayList<String> sortedList = new ArrayList<>(nodeSearchpair.keySet());
		Collections.sort(sortedList);// Sorting the ArrayList

		base.stepInfo("Node List with Searches");
		for (String s : sortedList) {
			base.stepInfo(s + "-->" + nodeSearchpair.get(s));
		}
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 */
	public void moveSavedSearchToNode(final String searchName, String moveToNode) throws InterruptedException {
		savedSearch_SearchandSelect(searchName, "Yes");
		moveSearchToAnotherFolder(moveToNode);
		moveActionButton("Save");
		base.stepInfo("Moved : " + searchName + " to " + moveToNode);
		// Make sure moved search is present in the Node
		driver.waitForPageToBeReady();
		selectNode1(moveToNode);
		savedSearch_SearchandSelect(searchName, "No");
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:10/28/21 Modified by:
	 *         Description : - updated based on new implementations
	 */
	public void moveNodeToNode(String nodeToBeMoved, String moveToNode) {
		selectNode1(nodeToBeMoved);
		moveSearchToAnotherFolder(moveToNode);
		moveActionButton("Save");
		base.stepInfo("Moved : " + nodeToBeMoved + " to " + moveToNode);
		driver.waitForPageToBeReady();
		getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		selectNode1(moveToNode);
		rootGroupExpansion();
		driver.waitForPageToBeReady();
		base.textCompareEquals(getLastNodeFromTab(moveToNode).getText(), nodeToBeMoved,
				"Moved Node is Available at right destination", "Move Action Failed");
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 */
	public void validateSAuserisAllowedtoMove() throws InterruptedException {
		search = new SessionSearch(driver);
		for (int a = 0; a < 3; a++) {
			switch (a) {
			case 1: {// impersonate to RMU
				base.impersonatePAtoRMU();
				base.stepInfo("Impersonated As RMU");
				break;
			}
			case 2: {// impersonate to Reviewer
				base.impersonateRMUtoReviewer();
				base.stepInfo("Impersonated As Reviewer");
				break;
			}
			}
			List<String> newNodeList = new ArrayList<String>();// to store newly created search groups
			List<String> savedInRoot = new ArrayList<String>();// to store name of saved file in root node
			List<String> savedInNode = new ArrayList<String>();// to store name of saved file in the newly created node

			for (int i = 0; i < 2; i++) {

				String newNode = createSearchGroupAndReturn(Input.mySavedSearch, "", "No");
				newNodeList.add(newNode);
				// To make sure we are in basic search page
				driver.getWebDriver().get(Input.url + "Search/Searches");
				driver.waitForPageToBeReady();
				if (i == 0) {
					// Create saved search
					search.basicContentSearch("transport");
				}
				String name1 = "SearchName" + Utility.dynamicNameAppender();
				String name2 = "SearchName" + Utility.dynamicNameAppender();
				savedInRoot.add(name1);
				savedInNode.add(name2);
				search.saveSearchAtAnyRootGroup(name1, Input.mySavedSearch);
				search.saveSearchInNewNode(name2, newNode);
			}
			navigateToSavedSearchPage();
			getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
			moveSavedSearchToNode(savedInRoot.get(0), newNodeList.get(1));
			navigateToSavedSearchPage();
			getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
			moveNodeToNode(newNodeList.get(0), newNodeList.get(1));
		}
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 */
	public void validatePAUisabletoMoveSharedSGS() throws InterruptedException {
		search = new SessionSearch(driver);
		for (int a = 0; a < 3; a++) {
			switch (a) {
			case 1: {// impersonate to RMU
				base.impersonatePAtoRMU();
				base.stepInfo("Impersonated As RMU");
				break;
			}
			case 2: {// impersonate to Reviewer
				base.impersonateRMUtoReviewer();
				base.stepInfo("Impersonated As Reviewer");
				break;
			}
			}
			List<String> newNodeList = new ArrayList<String>();// to store newly created search groups
			List<String> savedInRoot = new ArrayList<String>();// to store name of saved file in root node
			List<String> savedInNode = new ArrayList<String>();// to store name of saved file in the newly created node

			for (int i = 0; i < 2; i++) {
				String newNode = createSearchGroupAndReturn(Input.mySavedSearch, "", "No");
				newNodeList.add(newNode);
				// To make sure we are in basic search page
				driver.getWebDriver().get(Input.url + "Search/Searches");
				if (i == 0) {
					// Create saved search
					search.basicContentSearch("transport");
				}
				String name1 = "Oct0" + Utility.dynamicNameAppender();
				String name2 = "Oct0" + Utility.dynamicNameAppender();
				savedInRoot.add(name1);
				savedInNode.add(name2);
				search.saveSearchAtAnyRootGroup(name1, Input.mySavedSearch);
				search.saveSearchInNewNode(name2, newNode);
			}
			if (a == 0) {
				navigateToSavedSearchPage();
				getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
				selectNode1(newNodeList.get(0));
				shareSavedNodeWithDesiredGroup(newNodeList.get(0), "Default Security Group");
				driver.Navigate().refresh();
				driver.waitForPageToBeReady();
				softAssertion.assertEquals(verifySharedNode(newNodeList.get(0)), true);

				navigateToSavedSearchPage();
				getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
				selectNode1(newNodeList.get(0));
				shareSavedNodeWithDesiredGroup(newNodeList.get(0), "Project Administrator");
				driver.Navigate().refresh();
				driver.waitForPageToBeReady();
				softAssertion.assertEquals(verifySharedNodeInProjectAdminGroup(newNodeList.get(0)), true);
			}

			navigateToSavedSearchPage();
			getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
			moveSavedSearchToNode(savedInRoot.get(0), newNodeList.get(1));
			navigateToSavedSearchPage();
			getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
			moveNodeToNode(newNodeList.get(0), newNodeList.get(1));
		}
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 */
	public void shareSavedNodeWithDesiredGroup(String nodeName, String desiredGroup) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchGroupName(nodeName).Visible();
			}
		}), Input.wait60);
		getSavedSearchGroupName(nodeName).waitAndClick(10);
		getShareSerachBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		getShare_SecurityGroup(desiredGroup).waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShareSaveBtnNew().Visible();
			}
		}), Input.wait60);
		getShareSaveBtnNew().waitAndClick(10);
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:12/14/21 Modified by: Raghuram
	 *         Description :
	 */
	public boolean verifySharedNodeInProjectAdminGroup(String nodeName) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCollapsedSharedWithProjectAdministrator().Visible();
				}
			}), Input.wait60);
			getCollapsedSharedWithProjectAdministrator().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSharedGroupNameInPA(nodeName).Visible();
				}
			}), Input.wait60);
			getSharedGroupNameInPA(nodeName).waitAndClick(10);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 */
	public void loadingCountVerify() throws InterruptedException {
		base.stepInfo("Verifying Application not hang or shows latency longer than 5 seconds.");
		// base.waitForElementToBeGone(saveSearch.getTotalCountLoad(), 5);
		Thread.sleep(5000); // In order to verify latency
		if (getTotalCountLoad().isElementAvailable(1) == false) {
			base.passedStep("Application not hang or shows latency more than 5 seconds.");
		} else {
			base.failedMessage("Continues Loading more than 5 seconds.");
		}
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:10/21/21 Modified by:
	 *         Sowndarya.velraj Description :Handled Grey popup updated
	 * @return
	 */
	public int launchDocListViaSSandReturnDocCount() {
		String footerData, aggregatedDocCount;
		String[] arrOfStr;
		int finalCountresult;

		getDocListIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (getDocListPopup().isElementAvailable(2)) {
			getbtnDocListContinue().waitAndClick(5);
		}
		base.waitTime(5);

		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "en-us/Document/DocList", currentUrl);
		base.stepInfo("Landed on DocList Page : " + currentUrl);
		driver.scrollingToBottomofAPage();
		footerData = dcPage.getTableFooterDocListCount().getText();
		arrOfStr = footerData.split(" ");
		aggregatedDocCount = arrOfStr[arrOfStr.length - 2];
		base.stepInfo("Aggregated count" + aggregatedDocCount);
		if ((String.valueOf(aggregatedDocCount)).contains(",")) {
			aggregatedDocCount = aggregatedDocCount.replace(",", "");
			finalCountresult = Integer.parseInt(aggregatedDocCount);
		} else {
			finalCountresult = Integer.parseInt(aggregatedDocCount);
		}
		return finalCountresult;
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 * @return
	 */
	public void verifyDocCountWithResult(Map<String, Integer> purehit) throws InterruptedException {
		for (String key : purehit.keySet()) {
			savedSearch_SearchandSelect(String.valueOf(key), "Yes");
			getSearchName(key).WaitUntilPresent();
			try {
				base.stepInfo("Docs count : " + getCountofDocs().getText());
				base.stepInfo("Result count : " + String.valueOf(purehit.get(key)));
				softAssertion.assertEquals(getCountofDocs().getText(), String.valueOf(purehit.get(key)));
				base.passedStep("Document count matches with the result");
			} catch (Exception e) {
				base.failedStep("Document count doesn't matches with the result");
			}

		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 *
	 */
	public void verifyTheNavigationOfAS(String searchText) {
		base.waitForElement(getSearchText(searchText));
		if (getSearchText(searchText).isElementPresent() == true) {
			base.passedStep("Saved search query is displayed as expected");
		} else {
			base.failedStep("Saved search query is not displayed as expected");

		}
	}

	/**
	 * @author Jeevitha Description: Delete Functionality
	 */
	public void deleteFunctionality() {

		driver.scrollPageToTop();
		getSavedSearchDeleteButton().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDeleteOkBtn().Visible();
			}
		}), Input.wait60);
		getDeleteOkBtn().waitAndClick(10);

		String Expected = "Save search tree node successfully deleted.";
		base.VerifySuccessMessage(Expected);
		base.CloseSuccessMsgpopup();

	}

	/**
	 * @author Raghuram A Date: 10/11/21 Modified date:N/A Modified by: Description
	 *         :
	 * @return isElementPresent() == false ->
	 */
	public void loadingCountVerify(Element element, int secondsToWait, String passMsg, String failMessage)
			throws InterruptedException {
		base.stepInfo("Verifying Application not hang or shows latency longer than 5 seconds.");
		base.waitTime(secondsToWait); // In order to verify latency
		if (!element.isElementAvailable(1)) {
			base.passedStep(passMsg);
		} else {
			base.failedMessage(failMessage);
		}
	}

	/**
	 * @author Raghuram A Date: 10/8/21 Modified date:12/29/21 Modified by:Raghuram
	 *         Description :
	 * @return
	 */
	public void methodTocheckHideandShowFunction(String specificHeaderName) throws InterruptedException {

		List<String> headerListAfterHide = new ArrayList<>();
		verifyHeaderIsPresent(specificHeaderName);
		getHideSHowBtn().waitAndClick(5);
		try {
			base.waitForElement(getFieldoptions(specificHeaderName));
			getFieldoptions(specificHeaderName).waitAndClick(5);
			base.stepInfo("Unchecked Search Name Field");
		} catch (Exception e) {
			e.printStackTrace();
		}
		base.waitForElement(getbackGroundFilm());
		getbackGroundFilm().waitAndClick(5);
		verifyHeaderIsPresent(specificHeaderName);

		// updated list after hide
		ElementCollection headerListElementsBefore = getGridHeaderListSS();
		headerListAfterHide = base.availableListofElements(headerListElementsBefore);
		Collections.sort(headerListAfterHide);
		base.stepInfo(headerListAfterHide.toString());

		getSavedSearchRefresh().Click();
		driver.waitForPageToBeReady();
		base.stepInfo("Refreshed  Search Grid");
		verifyHeaderIsPresent(specificHeaderName);
	}

	/**
	 * @author Raghuram A Date: 10/8/21 Modified date:N/A Modified by: Description :
	 * @return
	 */
	public void verifyHeaderIsPresent(String headerName) {
		try {
			driver.Manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			if (getFieldHeader(headerName).isElementAvailable(5) == true) {
				base.stepInfo("Header " + headerName + " is displayed");
			} else {
				base.stepInfo("Header " + headerName + " not displayed");
			}
		} catch (Exception e) {
			base.failedMessage("Error");
		}
	}

	/**
	 * @author Raghuram A Date: 10/6/21 Modified date:N/A Modified by: Description :
	 */
	public void verifyExportpopup(String StyletoChoose, String fieldTypeToChoose) throws InterruptedException {
		try {
			getSavedSearchExportButton().Click();
			base.waitForElement(getExportPopup());
			base.waitForElement(getExportPopup());
			base.waitForElement(getStyle_dropdown());
			getStyle_dropdown().selectFromDropdown().selectByVisibleText(StyletoChoose);
			getField_dropdown().selectFromDropdown().selectByVisibleText(fieldTypeToChoose);
			base.waitForElement(getSelectCustodianName());
			getSelectCustodianName().waitAndClick(5);
			base.waitForElement(getAddToSelected_Button());
			getAddToSelected_Button().Click();
			base.waitForElement(getRunReport_Button());
			getRunReport_Button().waitAndClick(3);
			driver.waitForPageToBeReady();
			final BaseClass bc = new BaseClass(driver);
			final int Bgcount = bc.initialBgCount();
			try {
				bc.VerifySuccessMessage(
						"Your Report has been added into the Background successfully. Once it is complete, the "
								+ "\"bullhorn\""
								+ " icon in the upper right-hand corner will turn red, and will increment forward.");
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return bc.initialBgCount() == Bgcount + 1;
					}
				}), Input.wait60);
				base.passedStep("Able to export the saved search from search group in Tab-Delimited format");
			} catch (Exception e) {
				base.failedMessage("Error in Export/Success Notification");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram A Date: 10/6/21 Modified date:N/A Modified by: Description :
	 */
	public void verifyBasicSearchPage() {
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		String BsUrl = Input.url + "Search/Searches";
		String BsUrlViaSS = Input.url + "Search/Searches?SearchId=null&RedirectToSearch=Basic";

		try {
			if (currentUrl.equals(BsUrl)) {
				base.passedStep("Navigated to Basic Search Page : " + currentUrl);
			} else if (currentUrl.equals(BsUrlViaSS)) {
				base.passedStep("Navigated to Basic Search Page via SS : " + currentUrl);
			} else {
				base.failedStep("Navigated to Page : " + currentUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram A Date: 10/19/21 Modified date:N/A Modified by: Description
	 *         :
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public void shareSearchFlow(String searchName, String sharedGroup, String role)
			throws ParseException, InterruptedException {
		if (role.equals("RMU")) {
			shareSavedSearchRMU(searchName, sharedGroup);
		} else if (role.equals("PA")) {
			shareSavedSearchAsPA(searchName, sharedGroup);
		}
	}

	/**
	 * @author Raghuram A Date: 10/19/21 Modified date:12/29/21 Modified by:
	 *         Raghuram Description :-updated base.stepinfo
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public void headerVerification(int type, String SGtoShare, String role) throws InterruptedException {
		List<String> newNodeList = new ArrayList<>();
		List<String> headerList1 = new ArrayList<>();
		List<String> headerList2 = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int noOfNode = 2;
		String specificHeaderName = "Search Name";
		String node;
		int selectIndex = 0;

		// SG selection based on TC
		if (role.equals("PA") && type == 1) {
			SGtoShare = Input.shareSearchPA;
		} else if (role.equals("PA") && type == 2) {
			SGtoShare = Input.shareSearchDefaultSG;
		} else if (role.equals("RMU") || role.equals("REV")) {
			SGtoShare = Input.shareSearchDefaultSG;
		}

		navigateToSavedSearchPage();
		newNodeList = createSGAndReturn("PA", "No", noOfNode);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");
		search = new SessionSearch(driver);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = search.saveSearchInNodewithChildNode(newNodeList, inputValue);

		navigateToSavedSearchPage();
		node = childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);

		shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-requesties completed--------");

		// Launch DocVia via Saved Search
		navigateToSavedSearchPage();
		base.stepInfo("Root node selected : " + newNodeList.get(0));
		getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		selectNode1(newNodeList.get(0));

		ElementCollection headerListElementsBefore = getGridHeaderListSS();
		headerList1 = base.availableListofElements(headerListElementsBefore);
		Collections.sort(headerList1);
		base.stepInfo(headerList1.toString());

		methodTocheckHideandShowFunction(specificHeaderName);

		driver.waitForPageToBeReady();
		ElementCollection headerListElementsAfter = getGridHeaderListSS();
		headerList2 = base.availableListofElements(headerListElementsAfter);
		Collections.sort(headerList2);
		base.stepInfo(headerList2.toString());

		if (headerList1.equals(headerList2)) {
			base.passedStep(" All column appear on screen");
		} else {
			base.failedStep("FAIL");
		}
	}

	/*
	 * @author Jeevitha
	 */
	public void saveSearchToDoclist() {
		String footerData, aggregatedDocCount;
		String[] arrOfStr;
		int finalCountresult;

		getDocListIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		System.out.println("Landed on DocList Page : " + currentUrl);
		base.stepInfo("Landed on DocList Page : " + currentUrl);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		driver.scrollingToBottomofAPage();
		base.waitForElement(dcPage.getTableFooterDocListCount());
		footerData = dcPage.getTableFooterDocListCount().getText();
		System.out.println("Doc_Counts : " + footerData);
		base.stepInfo("Doc_Counts : " + footerData);

	}

	/**
	 * @author Jeevitha Description: Selects Tab and search Name and selects
	 * @param search
	 * @param GroupName
	 * @param selectSearch
	 * @throws InterruptedException
	 */
	public void selectSavedSearchTAb(String search, String GroupName, String selectSearch) throws InterruptedException {
		// Perform Edit function
		navigateToSavedSearchPage();
		getSavedSearchGroupName(GroupName).waitAndClick(10);
		savedSearch_SearchandSelect(search, selectSearch);
	}

	/**
	 * @author Jeevitha
	 * @param searchName
	 * @param PureHits
	 * @return
	 * @throws InterruptedException Modified on : 7/12/21
	 * @modified By jeevitha
	 */
	public String savedSearchExecute_SearchGRoup(final String searchName, int PureHits) throws InterruptedException {

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchExecuteButton().Visible();
			}
		}), Input.wait30);
		getSavedSearchExecuteButton().Click();

		try {
			getExecuteContinueBtn().waitAndClick(10);
		} catch (Exception e) {

		}

		base.waitForElement(getSuccessMsgHeader());
		if (getSuccessMsgHeader().getText() == "Warning !") {
			System.out.println(getSuccessMsgHeader().getText());
			System.out.println(getSuccessMsg().getText());
			base.failedMessage("Verification Failed. Warning message appeared");
		} else {
			base.stepInfo("Verification Passed. Warning message doesn't appeared");
		}

		base.VerifySuccessMessage(
				"Successfully added to background search. In the search group being executed, searches that are referring to other searches in the same search group will be errored out. You can select the errored searches and run them individually.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Got notification!");

		savedSearch_SearchandSelect(searchName, "No");
		// getSavedSearch_ApplyFilterButton().Click(); - updated

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCountofDocs().Visible();
			}
		}), Input.wait60);
		String docCount;
		if (PureHits == 0) {
			docCount = getCountofDocs().getText();
			System.out.println("NO Of Docs Executed :" + docCount);
			bc.stepInfo("NO Of Docs Executed :" + docCount);

		} else {
			docCount = getCountofDocs().getText();
			System.out.println(
					"NO Of Docs Executed :" + docCount + ", Actual PureHit Count : " + String.valueOf(PureHits));
			bc.stepInfo("NO Of Docs Executed :" + docCount + ", Actual PureHit Count : " + String.valueOf(PureHits));
			softAssertion.assertEquals(docCount, String.valueOf(PureHits));
		}
		return docCount;

	}

	/**
	 * @author Steffy A Date: 20/10/21 Modified date:N/A Modified by: N/A
	 *         Description Description: verifySharedGroupSearch
	 */
	public void verifySharedGroupSearch1(String securitygroupname, String searchName, Boolean flag)
			throws InterruptedException {

		// click on share with security group tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		savedSearch_SearchandSelect(searchName, "No");
		if (flag) {
			base.waitForElement(getSelectWithName(searchName));
			base.waitTillElemetToBeClickable(getSelectWithName(searchName));
			softAssertion.assertTrue(getSearchName(searchName).Displayed());
		} else {
			try {
				if (getSearchName(searchName).isElementAvailable(3)) {
					base.failedStep(searchName + " is displayed under this group " + securitygroupname);
				} else {
					base.passedStep(searchName + " is not displayed under this group " + securitygroupname);
				}
			} catch (Exception e) {
				base.passedStep(searchName + " is not displayed under this group " + securitygroupname);
				UtilityLog.info(searchName + " is not displayed under this group " + securitygroupname);
			}
		}

	}

	/**
	 * @author Mohan Date: 10/20/21 Modified date:7/10/21 Modified by: N/A
	 *         Description Description: verifySharedNode
	 * @Stabilization - done
	 */
	public void verifySearchAndSearchId(String SGtoShare, String searchID, Boolean verifySearch, String searchName,
			Boolean compareSearchID) throws InterruptedException {
		// Make sure shared Node reflected in the SG
		getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		getSavedSearchNewGroupExpand().waitAndClick(20);

		if (verifySearch) {
			try {
				savedSearch_SearchandSelect(searchName, "No");
				if (getSelectSearchWithID(searchName).isElementAvailable(3)) {
					searchIDtoCompare = getSelectSearchWithID(searchName).getText();
					System.out.println(searchIDtoCompare);
					base.stepInfo("Search ID after Shared : " + searchIDtoCompare);

					softAssertion.assertTrue(getSearchName(searchName).Displayed());
					System.out.println(searchName + " : is Present");
					base.stepInfo(searchName + " : is Present");
				} else {
					System.out.println(searchName + " : is not Present");
					base.stepInfo(searchName + " : is not Present");
				}

			} catch (Exception e) {
				System.out.println(searchName + " : is not Present");
				base.stepInfo(searchName + " : is not Present");
			}

		}

		if (compareSearchID) {
			softAssertion.assertNotSame(searchID, searchIDtoCompare);
			base.stepInfo("Different Search ID");
		}
	}

	/**
	 * @author Date : 10/18/21 Description: uploadBatchFile Notification message on
	 *         : N/A modified by : N/A
	 */
	public void verifyexecuteErrorMessage(String searchName) throws InterruptedException {

		driver.scrollPageToTop();
		getSavedSearchExecuteButton().waitAndClick(5);
		getContinueButton().waitAndClick(5);

		// verify Popup Message
		String Expected = "Successfully added to background search. In the search group being executed, searches that are referring to other searches in the same search group will be errored out."
				+ " You can select the errored searches and run them individually.";
		base.VerifySuccessMessage(Expected);
		driver.waitForPageToBeReady();

		do {
			getSavedSearchRefresh().waitAndClick(3);
		} while (getSearchStatus(searchName).getText().equals("ERROR"));

		savedSearch_SearchandSelect(searchName, "No");
		driver.waitForPageToBeReady();

		String Text = getSearchStatus(searchName).getText();
		System.out.println(Text);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(getSearchStatus(searchName).getText().toString(), "ERROR");
	}

	/**
	 * @author Date : 10/18/21 Description: uploadBatchFile Notification message on
	 *         : N/A modified by : N/A
	 */
	public void verifyWarning_messageForDocView(int pureHit) throws InterruptedException {

		driver.waitForPageToBeReady();
		docViewFromSS("View in DocView");
		if (pureHit == 0) {
			try {
				base.VerifyWarningMessage("There are no documents in the search results to proceed.");
				base.passedStep("Warning message successful");
			} catch (Exception e) {
				System.out.println("not displayed");
				base.failedStep("FAIL");
			}
		} else {
			System.out.println("");
		}
	}

	/**
	 * @author Date : 10/18/21 Description: uploadBatchFile Notification message on
	 *         : modified on : 11/08/21 modified by : Jeevitha R
	 */
	public void verify_sharedDSGroup(String searchName, String securitygroupname) throws InterruptedException {

		savedSearch_Searchandclick(searchName);
		getShareSerachBtn().Click();
		driver.waitForPageToBeReady();

		base.waitForElement(getShare_SecurityGroup(securitygroupname));
		getShare_SecurityGroup(securitygroupname).waitAndClick(10);

		getShareSaveBtnNew().waitAndClick(5);
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		driver.waitForPageToBeReady();

		// default SG tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
//		getSavedSearchNewGroupExpand().waitAndClick(20);
		rootGroupExpansion();
		savedSearch_SearchandSelect(searchName, "Yes");

		deleteFunctionality();
		driver.waitForPageToBeReady();

		savedSearch_SearchandSelect(searchName, "No");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param Search
	 */

	public void VerifySavedSearchExecuteStatus(String Search) {
		savedSearch_Searchandclick(Search);
		driver.waitForPageToBeReady();
		getSavedSearchExecuteButton().waitAndClick(5);
		base.VerifySuccessMessage("Successfully added to background search.");
		base.getCloseSucessmsg();
		savedSearch_Searchandclick(Search);
		base.waitForElement(getSearchStatus(Search, "COMPLETED"));
		driver.scrollingToElementofAPage(getSearchStatus(Search, "COMPLETED"));
		if (getSearchStatus(Search, "COMPLETED").isElementPresent()) {
			base.passedStep("search execution is completed status for the saved search changed to 'Completed'");
			softAssertion.assertTrue(true);
		} else {
			base.failedStep("Search execution status is not displayed as expected.");
		}
	}

	/**
	 * @author Raghuram A Date : 10/20/21 Description: uploadBatchFile_New Modified
	 *         on : N/A modified by : N/A
	 */
	public void uploadBatchFile_D(String batchFilePath, String fileName, Boolean upload) {
		driver.waitForPageToBeReady();
		base.waitForElement(getBatchUploadButton());
		getBatchUploadButton().Click();
		System.out.println("Clicked on Upload button..");
		base.stepInfo("Clicked on Upload button..");

		System.out.println("Clicked on Batch Upload Button.........");
		UtilityLog.info("Clicked on Batch Upload Button.........");
		// base.waitForElement(getSelectFile());
		driver.waitForPageToBeReady();
		base.stepInfo(System.getProperty("user.dir") + batchFilePath + fileName);
		base.waitForElement(getSelectFile());
		getSelectFile().javascriptclick(getSelectFile());
		System.out.println(System.getProperty("user.dir") + batchFilePath + fileName);
		getSelectFile().SendKeys(System.getProperty("user.dir") + batchFilePath + fileName);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Raghuram A Date : 10/20/21 Description: uploadBatchFile Notification
	 *         message on : N/A modified by : N/A
	 */
	public void verifyBatchUploadMessage(String status, Boolean upload) {
		if (status.equals("Success")) {
			try {
				base.VerifySuccessMessage("File uploaded successfully");
				base.stepInfo("Verified : File uploaded successfully");
				if (upload) {
					getSubmitToUpload().waitAndClick(5);
				}
			} catch (Exception e) {
				base.failedStep(getNotficationStatusMessage().getText());
			}
		} else if (status.equals("Failure")) {
			try {
				base.VerifyErrorMessage(Input.invalidBatchFileFormatErrMsg);
				base.stepInfo("Verified : " + Input.invalidBatchFileFormatErrMsg);
			} catch (Exception e) {
				base.stepInfo("Error in verification, Try with some other file");
				base.failedStep(getNotficationStatusMessage().getText());
			}
		} else if (status.equals("DataFailure")) {

			base.VerifyErrorMessage(Input.fileDataErrMsg);
			base.stepInfo("Verified : Error in file saving ");

		}
	}

	/**
	 * @author Mohan Created on 28/10/2021 Modified date:N/A Modified by:N/A
	 * @description: To Verify Saved Search options Under My Saved Search are
	 *               visible in PARMUREV
	 * 
	 */
	public void verifySavedSearchProperOptionsMySavedSearchAsPA() {

		try {
			base.stepInfo("Verify whether the Search Button is getting displayed in Saved Search Page");
			if (getSavedSearch_SearchButton().Displayed()) {
				softAssertion.assertEquals(getSavedSearch_SearchButton().Displayed().booleanValue(), true);
				base.passedStep("Search Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Search Group Button is getting displayed in Saved Search Page");
			if (getSavedSearchNewGroupButton().Displayed()) {
				softAssertion.assertEquals(getSavedSearchNewGroupButton().Displayed().booleanValue(), true);
				base.passedStep("Search Group Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Batch Search Upload Button is getting displayed in Saved Search Page");
			if (getBatchUploadButton().Displayed()) {
				softAssertion.assertEquals(getBatchUploadButton().Displayed().booleanValue(), true);
				base.passedStep("Batch Search Upload Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the DocView Button is getting displayed in Saved Search Page");
			if (getToDocView().Displayed()) {
				softAssertion.assertEquals(getToDocView().Displayed().booleanValue(), true);
				base.passedStep("DocView Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the DocList Button is getting displayed in Saved Search Page");
			if (getToDocList().Displayed()) {
				softAssertion.assertEquals(getToDocList().Displayed().booleanValue(), true);
				base.passedStep("DocList Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Tag Button is getting displayed in Saved Search Page");
			if (getSavedSearchToBulkTag().Displayed()) {
				softAssertion.assertEquals(getSavedSearchToBulkTag().Displayed().booleanValue(), true);
				base.passedStep("Tag Button is displayed in Saved Search Page");
			}
			base.stepInfo("Verify whether the Folder Button is getting displayed in Saved Search Page");
			if (getSavedSearchToBulkFolder().Displayed()) {
				softAssertion.assertEquals(getSavedSearchToBulkFolder().Displayed().booleanValue(), true);
				base.passedStep("Folder Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Report Button is getting displayed in Saved Search Page");
			if (getSavedSearchToTermReport().Displayed()) {
				softAssertion.assertEquals(getSavedSearchToTermReport().Displayed().booleanValue(), true);
				base.passedStep("Report Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Execute Button is getting displayed in Saved Search Page");
			if (getSavedSearchExecuteButton().Displayed()) {
				softAssertion.assertEquals(getSavedSearchExecuteButton().Displayed().booleanValue(), true);
				base.passedStep("Execute Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Export Button is getting displayed in Saved Search Page");
			if (getSavedSearchExportButton().Displayed()) {
				softAssertion.assertEquals(getSavedSearchExportButton().Displayed().booleanValue(), true);
				base.passedStep("Export Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Release Button is getting displayed in Saved Search Page");
			if (getReleaseIcon().Displayed()) {
				softAssertion.assertEquals(getReleaseIcon().Displayed().booleanValue(), true);
				base.passedStep("Release Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Refresh Button is getting displayed in Saved Search Page");
			if (getSavedSearchRefresh().Displayed()) {
				softAssertion.assertEquals(getSavedSearchRefresh().Displayed().booleanValue(), true);
				base.passedStep("Refresh Button is displayed in Saved Search Page");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Saved Search buttons verification failed due to this exception " + e.getMessage());
		}

		softAssertion.assertAll();
	}

	/**
	 * @author Mohan Created on 28/10/2021 Modified date:N/A Modified by:N/A
	 * @description: To Verify Saved Search options Under Shared With are visible in
	 *               PARMUREV
	 * 
	 */
	public void verifySavedSearchProperOptionsSharedWithAsPARMUREV(String user) {

		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.stepInfo("Verify whether the DocView Button is getting displayed in Saved Search Page");
			if (getToDocView().Displayed()) {
				softAssertion.assertEquals(getToDocView().Displayed().booleanValue(), true);
				base.passedStep("DocView Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the DocList Button is getting displayed in Saved Search Page");
			if (getToDocList().Displayed()) {
				softAssertion.assertEquals(getToDocList().Displayed().booleanValue(), true);
				base.passedStep("DocList Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Tag Button is getting displayed in Saved Search Page");
			if (getSavedSearchToBulkTag().Displayed()) {
				softAssertion.assertEquals(getSavedSearchToBulkTag().Displayed().booleanValue(), true);
				base.passedStep("Tag Button is displayed in Saved Search Page");
			}
			base.stepInfo("Verify whether the Folder Button is getting displayed in Saved Search Page");
			if (getSavedSearchToBulkFolder().Displayed()) {
				softAssertion.assertEquals(getSavedSearchToBulkFolder().Displayed().booleanValue(), true);
				base.passedStep("Folder Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Report Button is getting displayed in Saved Search Page");
			if (user.equals("PA") || user.equals("RMU")) {
				softAssertion.assertEquals(getSavedSearchToTermReport().Displayed().booleanValue(), true);
				base.passedStep("Report Button is displayed in Saved Search Page");
			} else if (user.equals("REV")) {
				base.passedStep("Report Button is not displayed in Saved Search Page for reviewer");
			}

			base.stepInfo("Verify whether the Execute Button is getting displayed in Saved Search Page");
			if (getSavedSearchExecuteButton().Displayed())
				;
			{
				softAssertion.assertEquals(getSavedSearchExecuteButton().Displayed().booleanValue(), true);
				base.passedStep("Execute Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Export Button is getting displayed in Saved Search Page");
			if (getSavedSearchExportButton().Displayed()) {
				softAssertion.assertEquals(getSavedSearchExportButton().Displayed().booleanValue(), true);
				base.passedStep("Export Button is displayed in Saved Search Page");
			}

			if (user.equals("PA")) {
				base.stepInfo("Verify whether the Release Button is getting displayed in Saved Search Page");
				softAssertion.assertEquals(getReleaseIcon().Displayed().booleanValue(), true);
				base.passedStep("Release Button is displayed in Saved Search Page");
			} else if (user.equals("RMU")) {
				base.stepInfo("Verify whether the Assign Button is getting displayed in Saved Search Page");
				softAssertion.assertTrue(getSavedSearchToBulkAssign().Displayed());
				base.passedStep("Assign Button is displayed in Saved Search Page");

			}

			base.stepInfo("Verify whether the Refresh Button is getting displayed in Saved Search Page");
			if (getSavedSearchRefresh().Displayed()) {
				softAssertion.assertEquals(getSavedSearchRefresh().Displayed().booleanValue(), true);
				base.passedStep("Refresh Button is displayed in Saved Search Page");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Saved Search buttons verification failed due to this exception " + e.getMessage());
		}

		softAssertion.assertAll();
	}

	/**
	 * @author Mohan Created on 28/10/2021 Modified date:N/A Modified by:N/A
	 * @description: To Verify Saved Search options Under Shared With are visible in
	 *               PA
	 * 
	 */
	public void verifySavedSearchProperOptionsChildNodeAsPARMUREV() {

		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.stepInfo("Verify whether the DocView Button is getting displayed in Saved Search Page");
			if (getToDocView().Displayed()) {
				softAssertion.assertEquals(getToDocView().Displayed().booleanValue(), true);
				base.passedStep("DocView Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the DocList Button is getting displayed in Saved Search Page");
			if (getToDocList().Displayed()) {
				softAssertion.assertEquals(getToDocList().Displayed().booleanValue(), true);
				base.passedStep("DocList Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Tag Button is getting displayed in Saved Search Page");
			if (getSavedSearchToBulkTag().Displayed()) {
				softAssertion.assertEquals(getSavedSearchToBulkTag().Displayed().booleanValue(), true);
				base.passedStep("Tag Button is displayed in Saved Search Page");
			}
			base.stepInfo("Verify whether the Folder Button is getting displayed in Saved Search Page");
			if (getSavedSearchToBulkFolder().Displayed()) {
				softAssertion.assertEquals(getSavedSearchToBulkFolder().Displayed().booleanValue(), true);
				base.passedStep("Folder Button is displayed in Saved Search Page");
			}

			try {
				base.stepInfo("Verify whether the Report Button is getting displayed in Saved Search Page");
				softAssertion.assertEquals(getSavedSearchToTermReport().Displayed().booleanValue(), true);
				base.passedStep("Report Button is displayed in Saved Search Page");

			} catch (Exception e) {
				try {
					base.waitForElement(getSavedSearchExecuteButton());
					getSavedSearchExecuteButton().Displayed();
					base.passedStep("User is on Reviewer Mode");
				} finally {
					base.stepInfo("Performed On Reviewer");
				}
			}

			base.stepInfo("Verify whether the Rename Button is getting displayed in Saved Search Page");
			if (getRenameButton().Displayed()) {
				softAssertion.assertEquals(getRenameButton().Displayed().booleanValue(), true);
				base.passedStep("Rename Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Rename Button is getting displayed in Saved Search Page");
			if (getSavedSearchDeleteButton().Displayed()) {
				softAssertion.assertEquals(getSavedSearchDeleteButton().Displayed().booleanValue(), true);
				base.passedStep("Rename Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Execute Button is getting displayed in Saved Search Page");
			if (getSavedSearchExecuteButton().Displayed()) {
				softAssertion.assertEquals(getSavedSearchExecuteButton().Displayed().booleanValue(), true);
				base.passedStep("Execute Button is displayed in Saved Search Page");
			}

			base.stepInfo("Verify whether the Export Button is getting displayed in Saved Search Page");
			if (getSavedSearchExportButton().Displayed()) {
				softAssertion.assertEquals(getSavedSearchExportButton().Displayed().booleanValue(), true);
				base.passedStep("Export Button is displayed in Saved Search Page");
			}

			try {
				base.stepInfo("Verify whether the Release Button is getting displayed in Saved Search Page");
				softAssertion.assertTrue(getReleaseIcon().Displayed());
				base.passedStep("Release Button is displayed in Saved Search Page");

			} catch (Exception e) {
				e.printStackTrace();
				try {
					base.stepInfo("Verify whether the Assign Button is getting displayed in Saved Search Page");
					softAssertion.assertTrue(getSavedSearchToBulkAssign().Displayed());
					base.passedStep("Assign Button is displayed in Saved Search Page");

				} catch (Exception e2) {
					if (getSavedSearchRefresh().Displayed()) {
						base.passedStep("User is on Reviewer Mode");
					}
				}
			}
			base.stepInfo("Verify whether the Refresh Button is getting displayed in Saved Search Page");
			if (getSavedSearchRefresh().Displayed()) {
				softAssertion.assertEquals(getSavedSearchRefresh().Displayed().booleanValue(), true);
				base.passedStep("Refresh Button is displayed in Saved Search Page");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Saved Search buttons verification failed due to this exception " + e.getMessage());
		}

		softAssertion.assertAll();
	}

	/**
	 * @author Mohan Created on 28/10/2021 Modified date:N/A Modified by:N/A
	 * @description: Navigate to Doclist and Return back to Saved Search
	 * 
	 */
	public void navigateToDoclistAndReturnBackToSavedSearch() {

		driver.waitForPageToBeReady();
		getToDocList().ScrollTo();
		base.waitForElement(getToDocList());
		getToDocList().waitAndClick(10);

		driver.waitForPageToBeReady();
		if (dcPage.getBackToSourceBtn().Displayed()) {
			base.passedStep("Navigated to DocList Page successfully");
		} else {
			base.failedStep("User failed to navigated to DocList Page");
		}

		base.waitForElement(dcPage.getBackToSourceBtn());
		dcPage.getBackToSourceBtn().waitAndClick(10);

		driver.waitForPageToBeReady();
		if (getToDocList().Displayed()) {
			base.passedStep("Navigated back to Saved Search Page Successfully");
		} else {
			base.failedStep("User failed to navigated to Saved Search Page");
		}

	}

	/**
	 * @author Mohan Created on 28/10/2021 Modified date:N/A Modified by:N/A
	 * @description: To select Child Node under SharedWith
	 * 
	 */
	public void selectChildNodeOfSharedWith() throws InterruptedException {

		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		try {
			base.waitTime(3);// to handle wait for observing the text
			if (getSavedSearchSharedWithExpandButton().isElementAvailable(5)) {
				getSavedSearchSharedWithExpandButton().waitAndClick(10);
			} else {
				getSavedSearchSharedWithExpandLastButton().waitAndClick(10);
			}

			base.waitForElement(getSavedSearchSharedWithChildNode());
			getSavedSearchSharedWithChildNode().waitAndClick(10);

			base.passedStep("Child node is selected sucessfully");

		} catch (Exception e) {

			base.failedStep("Child node is not selected");

		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: Upload work product contents batch file
	 * @param batch filename
	 */
	public void uploadWPBatchFile(final String batchFile) {
		driver.waitForPageToBeReady();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchUploadButton().Visible();
			}
		}), Input.wait30);
		getBatchUploadButton().Click();
		System.out.println("Clicked on Upload button..");
		UtilityLog.info("Clicked on Upload button..");

		System.out.println("Clicked on Batch Upload Button.........");
		UtilityLog.info("Clicked on Batch Upload Button.........");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFile().Visible();
			}
		}), Input.wait30);

		System.out.println(System.getProperty("user.dir") + Input.WPbatchFile + batchFile);
		UtilityLog.info(System.getProperty("user.dir") + Input.WPbatchFile + batchFile);
		getSelectFile().SendKeys(System.getProperty("user.dir") + Input.WPbatchFile + batchFile);
		getSubmitToUpload().Click();

		base.VerifySuccessMessage("File uploaded successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 12;
			}
		}), Input.wait60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySeraches().Visible();
			}
		}), Input.wait30);
		getMySeraches().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUploadedFile(batchFile).Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSelectUploadedFile(batchFile).Click();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: verify document count and status of the saved search results
	 */
	public void verifyDocCountsAndStatus(String searchName, int pureHits) {
		try {
			base.waitForElement(getSavedSearch_SearchName());
			getSavedSearch_SearchName().SendKeys(searchName);
			base.waitTillElemetToBeClickable(getSavedSearch_ApplyFilterButton());
			getSavedSearch_ApplyFilterButton().waitAndClick(10);
			Thread.sleep(3000);
			base.waitForElement(getSavedSearchCount(searchName));
			String docCounts = getSavedSearchCount(searchName).getText();
			softAssertion.assertEquals(docCounts, Integer.toString(pureHits));
			base.passedStep("Purehits from session search page is same as saved search page");
			softAssertion.assertEquals(getSavedSearchStatus(searchName).getText(), "COMPLETED");
			base.passedStep("Status is displayed as completed as expected ");
		} catch (Exception e) {
			base.failedStep("Failed to verify documents count and status");
		}
	}

	/**
	 * @author Raghuram A Date: 11/01/21 Modified date:N/A Modified by: Description
	 *         : SG creation and return nodeName
	 */
	public String createASearchGroupandReturnName(String searchName) {
		String new_node = createNewSearchGrp(searchName);

		base.stepInfo("Search and saveSearch in the created node");
		return new_node;
	}

	/*
	 * @author Jeevitha
	 */
	public void getDocCountAndStatusOfBatch(String searchName, String specificHeaderName, boolean select) {
		driver.waitForPageToBeReady();
		base.waitForElement(getSavedSearch_SearchName());
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		base.waitForElement(getSavedSearchCB(searchName));
		base.waitTillElemetToBeClickable(getSavedSearchCB(searchName));
		if (getSavedSearchCB(searchName).isElementAvailable(3)) {
			softAssertion.assertTrue(true);
			base.stepInfo("Search Found :" + searchName);
		} else {
			softAssertion.assertTrue(false);
			base.stepInfo("Search Not Found :" + searchName);
		}
		getSavedSearchCB(searchName).waitAndClick(20);

		driver.waitForPageToBeReady();
		String Status = getSavedSearchStatus(searchName).getText();
		System.out.println(Status + "  is the Status Displayed");
		softAssertion.assertEquals(getSavedSearchStatus(searchName).getText(), "COMPLETED");

		String docCounts = getSavedSearchCount(searchName).getText();
		System.out.println(docCounts + "  is the Count of Docs");

		if (select) {
			verifyHeaderIsPresent(specificHeaderName);
			getHideSHowBtn().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getFieldoptions(specificHeaderName));
			getFieldoptions_CC(specificHeaderName).waitAndClick(20);
			base.waitTillElemetToBeClickable(getHideSHowBtn());
			base.waitForElement(getbackGroundFilm());
			getbackGroundFilm().waitAndClick(5);
			driver.scrollPageToTop();
			verifyHeaderIsPresent(specificHeaderName);
		}
		base.waitForElement(getThreadedCount(searchName));
		String threadedCount = getThreadedCount(searchName).getText();

		base.waitForElement(getFamilyCount(searchName));
		String FamilyMember = getFamilyCount(searchName).getText();

		base.waitForElement(getNearDupeCount(searchName));
		String nearDup = getNearDupeCount(searchName).getText();

		base.stepInfo("[Status : " + Status + " ] [ " + "PureHit : " + docCounts + " ] [ " + "Threaded Count : "
				+ threadedCount + " ] [ " + "Family Members : " + FamilyMember + " ] [ " + "Near Duplicates : "
				+ nearDup + "]");

	}

	/**
	 * @author Raghuram A Date: 11/01/21 Modified date:N/A Modified by: Description
	 *         :
	 */
	public void validateMoveActionsVIaDiffRoles() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		String search1 = "Search" + Utility.dynamicNameAppender();
		String roleName = "";
		driver.waitForPageToBeReady();
		String rolesToImp[][] = { { "DA", "PA" }, { "PA", "RMU" }, { "RMU", "REV" } };
		for (int i = 0; i < rolesToImp.length; i++) {
			base.rolesToImp(rolesToImp[i][0], rolesToImp[i][1]);

			base.stepInfo("Node (or) New Search Group creation");
			String newNode = createSearchGroupAndReturn(searchName, roleName, "Yes");
			String newNode2 = createSearchGroupAndReturn(searchName, roleName, "Yes");

			base.stepInfo("Search and saveSearch in the created node");
			search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString2);
			search.saveSearchInNewNode(searchName, newNode);
			search.saveSearchInNewNode(search1, null);
			driver.waitForPageToBeReady();

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			base.stepInfo("Landed on SavedSearchPage");

			// Move node to another node
			moveNodeToNode(newNode, newNode2);

			// Move Search from My Search to another
			selectRootGroupTab(Input.mySavedSearch);
			savedSearch_Searchandclick(search1);
			moveSearchToanotherGroup(newNode2, search1);
			driver.getWebDriver().navigate().refresh();
		}
	}

	/**
	 * @author Raghuram A Date: 11/01/21 Modified date:N/A Modified by: Description
	 *         :
	 */
	public int fileVerificationSpecificMethod() {
		report = new ReportsPage(driver);
		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "//";
		File a = report.getLatestFilefromDir(testPath);
		System.out.println(a.getName());
		String fileName = a.getName();
		int countToCompare = report.fileVerification(testPath, fileName);
		return countToCompare;
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: Select saved search result
	 */
	public void selectSavedSearch(String searchName) {
		base.waitForElement(getSavedSearch_SearchName());
		getSavedSearch_SearchName().SendKeys(searchName);
		base.waitTillElemetToBeClickable(getSavedSearch_ApplyFilterButton());
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		base.waitTillElemetToBeClickable(getSelectSavedSearch(searchName));
		base.waitTime(4);
		getSelectSavedSearch(searchName).waitAndClick(10);
		base.passedStep("Saved search is selected");
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: Update saved search id into excel
	 */
	public void writeDataIntoExcel(String batchFile, String data) throws Exception {
		File file = new File(System.getProperty("user.dir") + Input.WPbatchFile + batchFile);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook xsf = new XSSFWorkbook(fis);
		XSSFSheet sheet = xsf.getSheetAt(0);
		sheet.getRow(15).createCell(2).setCellValue(data);
		FileOutputStream fos = new FileOutputStream(file);
		xsf.write(fos);
		base.passedStep("Data updated to excel sheet");
	}

	/**
	 * @author Mohan Created on 01/11/2021 Modified date:N/A Modified by:N/A
	 * @description: To select Child Node under SharedWith Project Admin
	 * 
	 */
	public void selectChildNodeOfSharedWithProjectAdmin() throws InterruptedException {

		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		try {

			Thread.sleep(3000);// to handle wait for observing the text
			getSavedSearchSharedWithProjectAdminExpandButton().waitAndClick(10);
			Thread.sleep(3000);// to handle wait for observing the text
			getSavedSearchLastChildNodeExpandLastButton().waitAndClick(10);
			base.waitForElement(getSavedSearchSharedWithProjectLastChildNode());
			getSavedSearchSharedWithProjectLastChildNode().waitAndClick(10);

			base.passedStep("Last Child node is selected sucessfully");

		} catch (Exception e) {

			base.failedStep("Last Child node is not selected");

		}

	}

	/**
	 * @author Mohan Created on 01/11/2021 Modified date:N/A Modified by:N/A
	 * @description: To execute sub node of Shared with Project Admin
	 * 
	 */
	public void executeChildNodeOfProjectAdmin(int rowNo, int countNo) throws InterruptedException {

		driver.waitForPageToBeReady();
		Thread.sleep(3000);// to handle wait for observing the text
		try {
			for (int i = rowNo; i <= rowNo; i++) {
				for (int k = countNo; k <= countNo; k++) {
					getSavedSearchRowDetailsText(rowNo).ScrollTo();
					softAssertion.assertTrue(getSavedSearchRowDetailsText(rowNo).Displayed());

					String rowDetails = getSavedSearchRowDetailsText(rowNo).getText();

					driver.scrollPageToTop();
					getSavedSearchExecuteButton().waitAndClick(10);

					try {
						getExecuteContinueBtn().waitAndClick(10);
					} catch (Exception e) {

					}
					childNodeRowDetails = getSavedSearchRowDetailsText(countNo).getText();
					base.VerifySuccessMessage(
							"Successfully added to background search. In the search group being executed, searches that are referring to other searches in the same search group will be errored out. You can select the errored searches and run them individually.");
					Thread.sleep(3000);// to handle wait for observing the text
					for (int j = rowNo; j <= rowNo; j++) {
						getSavedSearchRowDetailsText(rowNo).ScrollTo();
						String rowDetails1 = getSavedSearchRowDetailsText(rowNo).getText();
						softAssertion.assertNotEquals(rowDetails, rowDetails1);
					}

				}
			}
			base.passedStep("Child Node Is executed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Child Node is not executed");
		}
	}

	/**
	 * @author Mohan Created on 01/11/2021 Modified date:N/A Modified by:N/A
	 * @description: To execute any specific search
	 * 
	 */
	public void executeAnySpecficSearch(int rowNo, int countNo) throws InterruptedException {

		driver.waitForPageToBeReady();
		Thread.sleep(3000);// to handle wait for observing the text
		try {
			for (int i = rowNo; i <= rowNo; i++) {
				for (int k = countNo; k <= countNo; k++) {
					getSavedSearchRowDetailsText(rowNo).ScrollTo();
					softAssertion.assertTrue(getSavedSearchRowDetailsText(rowNo).Displayed());

					String rowDetails = getSavedSearchRowDetailsText(rowNo).getText();

					driver.scrollPageToTop();
					driver.waitForPageToBeReady();
					base.waitForElement(getSavedSearchDisplayedSearchRadioButton(1));
					getSavedSearchDisplayedSearchRadioButton(1).waitAndClick(10);
					getSavedSearchExecuteButton().waitAndClick(10);
					searchRowDetails = getSavedSearchRowDetailsText(countNo).getText();
					base.VerifySuccessMessage("Successfully added to background search.");

					Thread.sleep(3000);// to handle wait for observing the text
					for (int j = rowNo; j <= rowNo; j++) {
						getSavedSearchRowDetailsText(rowNo).ScrollTo();
						String rowDetails1 = getSavedSearchRowDetailsText(rowNo).getText();
						softAssertion.assertNotEquals(rowDetails, rowDetails1);
					}

				}
			}
			base.passedStep("Specific Search Is executed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Specific Search is not executed");
		}

	}

	/**
	 * @author Mohan Created on 01/11/2021 Modified date:N/A Modified by:N/A
	 * @description: To verify count of sub node and specific search
	 * 
	 */
	public void verifyChildNodeCount(int countNo) throws InterruptedException {

		driver.waitForPageToBeReady();
		Thread.sleep(3000);// to handle wait for observing the text
		try {
			for (int i = countNo; i <= countNo; i++) {

				getSavedSearchRowDetailsText(countNo).ScrollTo();
				softAssertion.assertTrue(getSavedSearchRowDetailsText(countNo).Displayed());

				String rowDetails = getSavedSearchRowDetailsText(countNo).getText();

				softAssertion.assertEquals(childNodeRowDetails, rowDetails);
				base.passedStep("Child node count is same in other PAU users also");
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (int i = countNo; i <= countNo; i++) {

				getSavedSearchRowDetailsText(countNo).ScrollTo();
				softAssertion.assertTrue(getSavedSearchRowDetailsText(countNo).Displayed());

				String rowDetails = getSavedSearchRowDetailsText(countNo).getText();

				softAssertion.assertEquals(searchRowDetails, rowDetails);
				base.passedStep("Specific search count is same in other PAU users");
			}

		}

	}

	/**
	 * @author Mohan Created on 01/11/2021 Modified date:N/A Modified by:N/A
	 * @description: To select Child Node under SharedWith Default
	 * 
	 */
	public void selectChildNodeOfSharedWithDefault() throws InterruptedException {

		driver.waitForPageToBeReady();
//		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		try {

			Thread.sleep(3000);// to handle wait for observing the text
			getSavedSearchSharedWithExpandLastButton().waitAndClick(10);
			Thread.sleep(3000);// to handle wait for observing the text
//			getSavedSearchSharedWithExpandLastButton().waitAndClick(10);

			base.waitForElement(getSavedSearchSharedWithProjectLastChildNode());
			getSavedSearchSharedWithProjectLastChildNode().waitAndClick(10);

			base.passedStep("Child node is selected sucessfully");

		} catch (Exception e) {

			base.failedStep("Child node is not selected");

		}

	}

	/**
	 * @author Jayanthi A Date: 11/02/21 Modified date:N/A Modified by: N/A A
	 *         Description Description: shareSavedNode To Security Group
	 */
	public void shareSavedNodeToSG(String SGtoShare, String nodeName, String searchName) throws InterruptedException {
		// Share to SG
		base.waitForElement(getSavedSearchGroupName(nodeName));
		getSavedSearchGroupName(nodeName).waitAndClick(10);
		getShareSerachBtn().waitAndClick(10);
		base.waitForElement(getShare_SecurityGroup(SGtoShare));
		getShare_SecurityGroup(SGtoShare).waitAndClick(10);
		base.waitForElement(getShareSaveBtnNew());
		getShareSaveBtnNew().waitAndClick(10);
		System.out.println("Shared to : " + SGtoShare);
		try {
			base.VerifySuccessMessage("Share saved search operation successfully done.");
			base.CloseSuccessMsgpopup();
			base.stepInfo("SavedSearch " + searchName + " is Shared to : " + SGtoShare);
		} catch (Exception e) {
			base.stepInfo("Search group is not shared to SG.");

		}

	}

	/**
	 * @author Raghuram.A Created on 11/08/2021 Modified date:N/A Modified by:N/A
	 * @throws InterruptedException
	 * @throws ParseException       description : RPMXCON-57380
	 */

	public int saveFlowCheckMethodWithShare(String saveFlow, String SearchName, String username)
			throws InterruptedException, ParseException {
		search = new SessionSearch(driver);
		int pureHits = 0;
		String custodianName = Input.metaDataCustodianNameInput;
		if (saveFlow.equalsIgnoreCase("Folder")) {
			base.stepInfo("--- Any Folder within My Saved Search ---");

			// create new searchGroup and Save Search
			String newNode = createSearchGroupAndReturn(SearchName, username, "Yes");
			pureHits = search.basicMetaDataSearch(Input.metaDataName, null, custodianName, null); // check handle E
			search.saveSearchInNewNode(SearchName, newNode);
			base.stepInfo("--- Share Saved Search with Security group ---");
			shareSavedSearchFromNode(SearchName, Input.securityGroup);

		} else if (saveFlow.equalsIgnoreCase("MySaveSearch")) {
			base.stepInfo("--- Within My Saved Search root node ---");

			// Save Search within MySearch
			pureHits = search.basicMetaDataSearch(Input.metaDataName, null, custodianName, null); // check handle E
			search.saveSearch(SearchName);
			base.stepInfo("--- Share Saved Search with Security group ---");
			shareSavedSearchRMU(SearchName, Input.securityGroup);
		}
		return pureHits;
	}

	/**
	 * @author Raghuram.A Created on 11/08/2021 Modified date:11/12/21 Modified
	 *         by:Raghuram A
	 * @throws InterruptedException
	 * @throws ParseException       description : RPMXCON-57379
	 */

	public int saveFlowCheckMethod(String saveFlow, String SearchName, String username)
			throws InterruptedException, ParseException {
		search = new SessionSearch(driver);
		int pureHits = 0;
		String custodianName = Input.metaDataCustodianNameInput;
		if (saveFlow.equalsIgnoreCase("Folder")) {
			base.stepInfo("--- Any Folder within My Saved Search ---");

			// create new searchGroup and Save Search
			String newNode = createSearchGroupAndReturn(SearchName, username, "Yes");
			pureHits = search.basicMetaDataSearch(Input.metaDataName, null, custodianName, null);
			search.saveSearchInNewNode(SearchName, newNode);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			selectNode1(newNode);

		} else if (saveFlow.equalsIgnoreCase("MySaveSearch")) {
			base.stepInfo("--- Within My Saved Search root node ---");

			// Save Search within MySearch
			pureHits = search.basicMetaDataSearch(Input.metaDataName, null, custodianName, null);
			search.saveSearch(SearchName);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
		} else if (saveFlow.equalsIgnoreCase("SubFolder")) {
			base.stepInfo("--- Within My Saved Search - Node - sub node ---");

			String newNode = createSearchGroupAndReturn(SearchName, username, "Yes");

//			selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
			base.selectproject();
			navigateToSavedSearchPage();
			rootGroupExpansion();

			String subNodeName = createNewSearchGrp(newNode, 1);

			// Save Search within MySearch
			pureHits = search.basicMetaDataSearch(Input.metaDataName, null, custodianName, null); // check handle E
//			search.saveSearchInRootNode(SearchName, newNode, subNodeName);
			search.saveSearchInNodewithChildNode(SearchName, subNodeName);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			selectNode1(newNode);
			selectNode1(subNodeName);
//			base.stepInfo("--- Share Saved Search with Security group ---");
//			shareSavedSearchRMU(SearchName, Input.securityGroup);
		}
		return pureHits;
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: Update production name into excel
	 */
	public void writeProductionDataIntoExcel(String batchFile, String data) throws Exception {
		File file = new File(System.getProperty("user.dir") + Input.WPbatchFile + batchFile);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook xsf = new XSSFWorkbook(fis);
		XSSFSheet sheet = xsf.getSheetAt(0);
		sheet.getRow(18).createCell(2).setCellValue(data);
		FileOutputStream fos = new FileOutputStream(file);
		xsf.write(fos);
		base.passedStep("Data updated to excel sheet");

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param status
	 * @description This method will validate the status of saved search.
	 */
	public void CheckStatus(String status) {
		getStatusDropDown().selectFromDropdown().selectByVisibleText(status);
		getSavedSearch_ApplyFilterButton().waitAndClick(20);
		driver.waitForPageToBeReady();
		base.ValidateElementCollection_Presence(getCompletedStatusColumn(),
				" Last Status Column Searches are displayed");
		List<WebElement> elementList = null;
		elementList = getCompletedStatusColumn().FindWebElements();
		for (WebElement wenElementNames : elementList) {
			String elementName = wenElementNames.getText();
			System.out.println(elementName);
			if (elementName.contains(status)) {
				softAssertion.assertEquals("COMPLETED", elementName);
				continue;
			} else {
				base.failedStep("Status in saved saerch page is not displayed as exepcted");
				break;
			}
		}
		base.passedStep("Last status column in saved search page is displayed as Expected when we applied the filter.");

	}

	/**
	 * @author Raghuram.A Created on 11/08/2021 Modified date:N/A Modified by:N/A
	 * @throws InterruptedException
	 * @throws ParseException       description : RPMXCON-
	 */

	public int saveFlowCheckAudioNonaudioWithShare(String saveFlow, String SearchName, String username, String shareTo)
			throws InterruptedException, ParseException {
		search = new SessionSearch(driver);
		int pureHits = 0;
		if (saveFlow.equalsIgnoreCase("Folder")) {
			base.stepInfo("--- Any Folder within My Saved Search ---");

			// create new searchGroup and Save Search
			String newNode = createSearchGroupAndReturn(SearchName, username, "Yes");
			pureHits = search.AudioAndNonAudioSearch(Input.audioSearch, Input.language);
			search.saveAdvanceSearchInNode(SearchName, newNode);
			base.stepInfo("--- Share Saved Search with Security group ---");
			shareSavedSearchFromNode(SearchName, shareTo);

		} else if (saveFlow.equalsIgnoreCase("MySaveSearch")) {
			base.stepInfo("--- Within My Saved Search root node ---");

			// Save Search within MySearch
			pureHits = search.AudioAndNonAudioSearch(Input.audioSearch, Input.language);
			search.saveAdvancedSearchQuery(SearchName);
			base.stepInfo("--- Share Saved Search with Security group ---");
			shareSavedSearchRMU(SearchName, shareTo);
		}
		return pureHits;
	}

	
	/**
	 * @author Raghuram.A Created on 11/08/2021 Modified date:N/A Modified by:N/A
	 * @throws InterruptedException
	 * @throws ParseException       description : RPMXCON-
	 */

	public int saveFlowCheckAudioNonaudioWithoutShare(String saveFlow, String SearchName, String username)
			throws InterruptedException, ParseException {
		search = new SessionSearch(driver);
		int pureHits = 0;
		if (saveFlow.equalsIgnoreCase("Folder")) {
			base.stepInfo("--- Any Folder within My Saved Search ---");

			// create new searchGroup and Save Search
			String newNode = createSearchGroupAndReturn(SearchName, username, "Yes");
			pureHits = search.AudioAndNonAudioSearch(Input.audioSearch, Input.language);
			search.saveAdvanceSearchInNode(SearchName, newNode);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			selectNode1(newNode);

		} else if (saveFlow.equalsIgnoreCase("MySaveSearch")) {
			base.stepInfo("--- Within My Saved Search root node ---");

			// Save Search within MySearch
			pureHits = search.AudioAndNonAudioSearch(Input.audioSearch, Input.language);
			search.saveAdvancedSearchQuery(SearchName);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
		}
		return pureHits;
	}

	/**
	 * @author Raghuram.A Created on 11/08/2021 Modified date:N/A Modified by:N/A
	 */
	public void navigateBackToSavedSearchViaBacktoSourceDOcList(Boolean baclToSource, Boolean selectNode,
			String nodeName) {
		try {
			if (baclToSource) {
				base.waitForElement(dcPage.getBackToSourceBtn());
				dcPage.getBackToSourceBtn().waitAndClick(10);
			}
			driver.waitForPageToBeReady();
			/*
			 * if (selectNode) {
			 * base.waitForElement(getSavedSearchNodeWithRespectiveSG(Input.
			 * shareSearchDefaultSG, nodeName));
			 * getSavedSearchNodeWithRespectiveSG(Input.shareSearchDefaultSG,
			 * nodeName).waitAndClick(10); System.out.println("Node clicked again"); }
			 */
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			Element batchUploadButton = getBatchUploadButton();
			base.waitTime(2);
			checkButtonDisabled(batchUploadButton, "Should be disabled", "Batch Upload Button");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Raghuram.A Created on 11/08/2021 Modified date:N/A Modified by:N/A
	 *         description : checkButtonDisabled - method has to be updated
	 */
	public void checkButtonDisabled(Element element, String resultStatus, String buttonName) {
		String elementStatus = element.GetAttribute("class").toString();
		try {
			if (elementStatus.equals("disabled") && resultStatus.equalsIgnoreCase("Should be disabled")) {
				base.passedStep(buttonName + " is disabled ");
			} else if (elementStatus.equals("disabled") && resultStatus.equalsIgnoreCase("Should not be disabled")) {
				base.failedStep(buttonName + " is disabled ");
			} else if (elementStatus.equals("") && resultStatus.equalsIgnoreCase("Should be enabled")) {
				base.passedStep(buttonName + " is Enabled ");
			} else {
				base.failedStep("Condition failed");
			}
		} catch (Exception e) {
			base.failedStep("Condition failed");
		}

	}

	/**
	 * @author Raghuram.A Date: 11/9/21 Modified date:10/21/21 Modified by: N/A
	 * @param searchName
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public void scheduleSavedSearches(String searchName) throws ParseException, InterruptedException {

		String scheduleSuccessMessage = "Record scheduled successfully";
		driver.waitForPageToBeReady();
		savedSearch_SearchandSelect(searchName, "Yes");

		getSavedSearch_ScheduleButton().waitAndClick(5);
		base.waitForElement(getSavedSearch_ScheduleTime());
		getSavedSearch_ScheduleTime().SendKeys(schdulerTimePlus15Secs());

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getSaveSchedulerBtn().waitAndClick(5);
		Element getNotficationStatusMessage = getNotficationStatusMessage();
		base.textFromElementToVerify(getNotficationStatusMessage, scheduleSuccessMessage);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Saved search " + searchName + " is scheduled to run in 5 secs!");
		UtilityLog.info("Saved search " + searchName + " is scheduled to run in 5 secs!");
	}

	/**
	 * @author Raghuram A Date: 11/9/21 Modified date:10/21/21 Modified by: N/A
	 *         Description :
	 * @return
	 * @throws InterruptedException
	 */
	public void launchDocListViaSS(int latencyCheckTime, String passMessage, String failureMsg)
			throws InterruptedException {

		try {
			base.waitForElement(getDocListIcon());
			getDocListIcon().waitAndClick(5);
			Element loadingElement = getspinningWheel();
			loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
			driver.waitForPageToBeReady();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram A Date: 11/9/21 Modified date:N/AModified by: N/A
	 *         Description :
	 * @return
	 * @throws InterruptedException
	 */
	public int docListPageFooterCountCheck() {
		String footerData, aggregatedDocCount;
		String[] arrOfStr;
		int finalCountresult;

		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "en-us/Document/DocList", currentUrl);
		base.stepInfo("Landed on DocList Page : " + currentUrl);
		driver.scrollingToBottomofAPage();
		base.waitTime(3);
		footerData = dcPage.getTableFooterDocListCount().getText();
		arrOfStr = footerData.split(" ");
		aggregatedDocCount = arrOfStr[arrOfStr.length - 2];
		base.stepInfo("Aggregated count" + aggregatedDocCount);
		if ((String.valueOf(aggregatedDocCount)).contains(",")) {
			aggregatedDocCount = aggregatedDocCount.replace(",", "");
			finalCountresult = Integer.parseInt(aggregatedDocCount);
		} else {
			finalCountresult = Integer.parseInt(aggregatedDocCount);
		}
		return finalCountresult;
	}

	/**
	 * @author Jeevitha
	 * @param search
	 * @param GroupName
	 * @param selectSearch
	 * @throws InterruptedException Description: Delete Searches
	 */
	public void deleteSearch(String search, String GroupName, String selectSearch) throws InterruptedException {
		selectSavedSearchTAb(search, GroupName, selectSearch);
		base.waitForElement(getSavedSearchDeleteButton());
		deleteFunctionality();
	}

	public void uploadWPBatchFile_New(final String batchFile, String filePath) {
		driver.waitForPageToBeReady();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchUploadButton().Visible();
			}
		}), Input.wait30);
		base.waitTime(2);
		getBatchUploadButton().Click();
		System.out.println("Clicked on Upload button..");
		UtilityLog.info("Clicked on Upload button..");

		System.out.println("Clicked on Batch Upload Button.........");
		UtilityLog.info("Clicked on Batch Upload Button.........");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFile().Visible();
			}
		}), Input.wait30);

		System.out.println(System.getProperty("user.dir") + filePath + batchFile);
		UtilityLog.info(System.getProperty("user.dir") + filePath + batchFile);
		getSelectFile().SendKeys(System.getProperty("user.dir") + filePath + batchFile);
		getSubmitToUpload().Click();

		base.VerifySuccessMessage("File uploaded successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 12;
			}
		}), Input.wait60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySeraches().Visible();
			}
		}), Input.wait30);
		getMySeraches().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUploadedFile(batchFile).Visible();
			}
		}), Input.wait30);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSelectUploadedFile(batchFile).Click();
	}

	/**
	 * @author Raghuram.A Created on 11/12/2021 Modified date:11/18/21 Modified
	 *         by:N/A
	 * @throws InterruptedException
	 * @throws ParseException       description : RPMXCON-57381
	 */

	public int saveFlowCheckMethodWithShareSpecificToImp(String saveFlow, String SearchName, String username,
			Boolean shareRequired, String shareTo, Boolean impRequired, String impFrom, String impToRole,
			Boolean additionalVariable1, Boolean additionalVariable2, Boolean additionalVariablel3)
			throws InterruptedException, ParseException {
		search = new SessionSearch(driver);
		int pureHits = 0;
		String custodianName = Input.metaDataCustodianNameInput;
		String newNode = null;
		if (saveFlow.equalsIgnoreCase("Folder")) {
			base.stepInfo("--- Any Folder within My Saved Search ---");

			// create new searchGroup and Save Search
			newNode = createSearchGroupAndReturn(SearchName, username, "Yes");
			pureHits = search.basicMetaDataSearch(Input.metaDataName, null, custodianName, null); // check handle E
			search.saveSearchInNewNode(SearchName, newNode);
			if (shareRequired) {
				base.stepInfo("--- Share Saved Search with Security group ---");
				shareSavedSearchFromNode(SearchName, shareTo);
			}

		} else if (saveFlow.equalsIgnoreCase("MySaveSearch")) {
			base.stepInfo("--- Within My Saved Search root node ---");

			// Save Search within MySearch
			pureHits = search.basicMetaDataSearch(Input.metaDataName, null, custodianName, null); // check handle E
			search.saveSearch(SearchName);
			if (shareRequired) {
				base.stepInfo("--- Share Saved Search with Security group ---");
				shareSavedSearchRMU(SearchName, shareTo);
			}
		} else if (saveFlow.equalsIgnoreCase(Input.shareSearchDefaultSG)
				|| saveFlow.equalsIgnoreCase(Input.shareSearchPA)) {
			base.stepInfo("--- Within SG root node ---");

			// Save Search within SG / PA
			pureHits = search.basicMetaDataSearch(Input.metaDataName, null, custodianName, null); // check handle E
			search.saveSearchAtAnyRootGroup(SearchName, saveFlow);
		}

		// Impersonate
		if (impRequired) {
			base.rolesToImp(impFrom, impToRole);
		}

		if (saveFlow.equalsIgnoreCase("Folder")) {
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			if (shareTo.equals(Input.shareSearchDefaultSG)) {
				getDefaultSecurityGroupExpand().waitAndClick(5);
			}
			selectNode1(newNode);
		} else if (saveFlow.equalsIgnoreCase("MySaveSearch")) {
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			if (shareTo.equals(Input.shareSearchDefaultSG)) {
				getDefaultSecurityGroupExpand().waitAndClick(5);
			}
		} else if (saveFlow.equalsIgnoreCase(Input.shareSearchDefaultSG)
				|| saveFlow.equalsIgnoreCase(Input.shareSearchPA)) {
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			getSavedSearchGroupName(saveFlow).waitAndClick(5);
			getSavedSearchNewGroupExpand().waitAndClick(5);
		}

		return pureHits;
	}

	/**
	 * @author Jeevitha Created on 11/12/2021 Modified date:N/A Modified by:N/A
	 */
	public void performBulkActionFromNode(String searchName, String nodeName, String FolderName)
			throws InterruptedException {
		search = new SessionSearch(driver);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		selectNode1(nodeName);
		savedSearch_SearchandSelect(searchName, "Yes");
		driver.scrollPageToTop();
		base.waitForElement(getSavedSearchToBulkFolder());
		getSavedSearchToBulkFolder().waitAndClick(10);
		search.BulkActions_Folder_returnCount(FolderName);

	}

	/**
	 * @author Jeevitha Created on 11/12/2021 Modified date:N/A Modified by:N/A
	 */
	public void deleteNode(String GroupName, String Node) {

		navigateToSSPage();
		getSavedSearchGroupName(GroupName).waitAndClick(10);
		selectNode1(Node);
		base.waitForElement(getSavedSearchDeleteButton());
		deleteFunctionality();
	}

	/**
	 * @author Raghuram Created on 12/09/2021 Modified date:N/A Modified by:N/A
	 */
	public void deleteNode(String GroupName, String Node, Boolean navigateToSS, Boolean sgToSelect) {

		if (navigateToSS) {
			navigateToSavedSearchPage();
		}
		if (sgToSelect) {
			getSavedSearchGroupName(GroupName).waitAndClick(10);
		}
		selectNode1(Node);
		base.waitForElement(getSavedSearchDeleteButton());
		deleteFunctionality();
	}

	/**
	 * @author Raghuram Created on 11/16/2021 Modified date:N/A Modified by:N/A
	 */
	public void verifySavedSearchPage() {

		String currentUrl = driver.getWebDriver().getCurrentUrl();
		String BsUrl = Input.url + "SavedSearch/SavedSearches";
		String BsUrlViaSS = Input.url + "SavedSearch/SavedSearches?SearchId=null&RedirectToSearch=Basic";
		// Navigated to Page :
		// https://sightlinept.consilio.com/SavedSearch/SavedSearches
		try {
			if (currentUrl.equals(BsUrl)) {
				base.passedStep("Navigated to Basic Search Page : " + currentUrl);
			} else if (currentUrl.equals(BsUrlViaSS)) {
				base.passedStep("Navigated to Basic Search Page via SS : " + currentUrl);
			} else if (currentUrl.contains("SavedSearch/SavedSearches")) {
				base.passedStep("Navigated to Basic Search Page : " + currentUrl);
			} else {
				base.failedStep("Navigated to Page : " + currentUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram.A Date: 11/23/21 Modified date:N/A Modified by: N/A
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	public void verifyTheDocumentCountForRMU_withmultipleCombinations(String[] combinations)
			throws InterruptedException, ParseException {
		search = new SessionSearch(driver);
		List<String> searchList = new ArrayList<>();
		Set<String> purehitKeySet = new HashSet<String>();
		String tagName = "TAG" + Utility.dynamicNameAppender();
		String shareTo = Input.shareSearchDefaultSG;

		// Creating new Tag under Default Security Group
		tagsAndFolderPage.CreateTag(tagName, Input.securityGroup);

		// Executing and Saving the Different Combinations of Advanced SavedSearch under
		base.stepInfo(" Different Combinations of Advanced SavedSearch under My Saved Search");
		Map<String, Integer> purehit = search.advancedSearchWithCombinationsSaveUnderMySearches(Input.searchString1,
				Input.searchString1, "morning", Input.language, tagName, combinations, "", "", "", true);
		System.out.println(purehit);

		// Sharing the Saved Search with Default Security Group
		driver.Navigate().to(Input.url + "SavedSearch/SavedSearches");
		purehitKeySet = purehit.keySet();
		Iterator<String> iterator = purehitKeySet.iterator();
		while (iterator.hasNext()) {
			String serachName = iterator.next();
			searchList.add(serachName);
			shareSavedSearchRMU(serachName, shareTo);
		}
		login.logout();

		// Verify Doc Count as RMU
		base.stepInfo("Verify Doc Count as RMU");
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.Navigate().to(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		if (getSavedSearchGroupName(shareTo).isElementAvailable(3)) {
			getSavedSearchGroupName(shareTo).waitAndClick(10);
		} else {
			getSavedSearchGroupNameC(shareTo).waitAndClick(5);
		}

		verifyDocCountWithResult(purehit);

		login.logout();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Back As : " + Input.pa1FullName);

		while (iterator.hasNext()) {
			String serachName = iterator.next();
			int count = purehit.get(serachName);
			driver.Navigate().to(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();

			// Executing the Saved Search under My saved search
			getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(10);
			getSavedSearch_SearchName().Clear();
			savedSearch_SearchandSelect(serachName, "Yes");
			savedSearchExecute2(serachName, count);
			getSavedSearchGroupName(shareTo).waitAndClick(10);
			getSavedSearch_SearchName().Clear();
			savedSearch_SearchandSelect(serachName, "Yes");
			savedSearchExecute2(serachName, count);
		}

		login.logout();
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Back As : " + Input.rmu1FullName);
		driver.Navigate().to(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		if (getSavedSearchGroupName(shareTo).isElementAvailable(2)) {
			getSavedSearchGroupName(shareTo).waitAndClick(10);
		} else {
			getSavedSearchGroupNameC(shareTo).waitAndClick(10);
		}

		verifyDocCountWithResult(purehit);

		login.logout();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Back As : " + Input.pa1FullName);

		// Delete Searches
		base.stepInfo("Initiated Delete Searches");
		deleteSearch(true, true, Input.mySavedSearch, searchList, "Yes");
		// Delete Shared SG Searches
		base.stepInfo("Initiated Delete Shared SG Searches");
		deleteSearch(true, true, Input.shareSearchDefaultSG, searchList, "Yes");

		// Delete Created Tag
		base.stepInfo("Initiated Delete Tags");
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteTag(tagName, Input.securityGroup);

	}

	/**
	 * @author Raghuram.A Date: 11/23/21 Modified date:N/A Modified by: N/A
	 * @param navigateToSearcPage
	 * @param selectGroup
	 * @param groupName
	 * @param SearchListToDelete
	 * @param option
	 * @throws InterruptedException Description : Delete SearchList
	 */
	public void deleteSearch(Boolean navigateToSearcPage, Boolean selectGroup, String groupName,
			List<String> SearchListToDelete, String option) throws InterruptedException {

		if (navigateToSearcPage) {
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		}
		if (selectGroup) {
			if (getSavedSearchGroupName(groupName).isElementAvailable(2)) {
				getSavedSearchGroupName(groupName).waitAndClick(10);
			} else {
				getSavedSearchGroupNameC(groupName).waitAndClick(10);
			}

		}
		for (String searchName : SearchListToDelete) {
			savedSearch_SearchandSelect(searchName, option);
			base.waitForElement(getSavedSearchDeleteButton());
			deleteFunctionality();
		}
	}

	/**
	 * @author Raghuram.A Date: 11/24/21 Modified date:N/A Modified by: N/A
	 * @throws InterruptedException description :
	 *                              verifyShareSearchwithMultiplesRoles - with
	 *                              additional parameters for future updates
	 */
	public void verifyShareSearchwithMultiplesRoles(String[] rolesToVerify, String SearchName, String SGtoVerify,
			String toVerify, String searchID, Boolean additionalValue1, Boolean additionalValue2, String additional1,
			String additional2) throws InterruptedException {
		String searchIDtoCompare;

		for (int i = 0; i < rolesToVerify.length; i++) {
			System.out.println(rolesToVerify[i]);
			if (rolesToVerify[i].equals("RMU-2")) {
				login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			} else if (rolesToVerify[i].equals("PA-2")) {
				login.loginToSightLine(Input.pa2userName, Input.pa2password);
			}

			base.stepInfo("Logged in as : " + rolesToVerify[i]);
			navigateToSavedSearchPage(); // Navigate to SavedSeachPage
			if (toVerify.equals("RootSearch")) {
				verifySharedGroupSearch(SGtoVerify, SearchName);

				driver.waitForPageToBeReady();
				searchIDtoCompare = getSelectSearchWithID(SearchName).getText();
				System.out.println(searchIDtoCompare);
				base.stepInfo("Search ID before Shared : " + searchID);
				base.stepInfo("Search ID after Shared : " + searchIDtoCompare);

				softAssertion.assertNotSame(searchID, searchIDtoCompare, "Different Search ID");
				base.textCompareNotEquals(searchID, searchIDtoCompare, "Different Search ID", "Same Search ID");

				// Result count
				String resultCount = docResultCOuntCHeck(SearchName);
			}

			login.logout();
		}
	}

	/**
	 * @author Raghuram.A Date: 11/24/21 Modified date:N/A Modified by: N/A
	 * 
	 * @param searchName
	 * @param securitygroupname
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public String shareSavedSearchRMUreturnsOriginalSearchID(final String searchName, String securitygroupname)
			throws ParseException, InterruptedException {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		savedSearch_SearchandSelect(searchName, "Yes");
		getShareSerachBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getShare_SecurityGroup(securitygroupname));
		getShare_SecurityGroup(securitygroupname).waitAndClick(10);

		base.waitForElement(getShareSaveBtnNew());
		getShareSaveBtnNew().waitAndClick(10);
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		driver.waitForPageToBeReady();
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		// get Search ID
		String SearchID = getSelectSearchWithID(searchName).getText();
		base.stepInfo("Search ID : " + SearchID);

		// click on share with security group tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		// Thread sleep added to Execute in cosolidation
		driver.waitForPageToBeReady();
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		base.waitForElement(getSelectWithName(searchName));
		getSelectWithName(searchName).waitAndClick(10);

		// verify id should get changed
		String newSearchID1 = getSelectSearchWithID(searchName).getText();
		base.stepInfo("Search ID from shared folder : " + newSearchID1);

		softAssertion.assertNotSame(SearchID, newSearchID1);
		softAssertion.assertTrue(getSearchName(searchName).Displayed());

		return SearchID;

	}

	/**
	 * @author Raghuram.A Date: 11/25/21 Modified date:N/A Modified by: N/A
	 * @throws InterruptedException description :
	 *                              verifySharedNodehwithMultiplesRoles - with
	 *                              additional parameters for future updates
	 */
	public void verifySharedNodehwithMultiplesRoles(String[] rolesToVerify, String SearchName, String SGtoVerify,
			String toVerify, String searchID, Boolean additionalValue1, Boolean additionalValue2, String nodeName,
			String nodeFlow, String additional2, HashMap<String, String> nodeSearchDocCOuntpair,
			List<String> additionalList) throws InterruptedException {

		for (int i = 0; i < rolesToVerify.length; i++) {
			System.out.println(rolesToVerify[i]);
			if (rolesToVerify[i].equals("RMU-2")) {
				login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			} else if (rolesToVerify[i].equals("PA-2")) {
				login.loginToSightLine(Input.pa2userName, Input.pa2password);
			}

			base.stepInfo("Logged in as : " + rolesToVerify[i]);
			navigateToSavedSearchPage(); // Navigate to SavedSeachPage

			if (nodeFlow.equals("Single")) {
				// Verify Shared Node
				verifySharedNode(SGtoVerify, nodeName, searchID, true, SearchName, true);

				// Result count
				String resultCount = docResultCOuntCHeck(SearchName);

				softAssertion.assertNotSame(resultCount, nodeSearchDocCOuntpair.get(SearchName), "Count Failed");
				base.textCompareEquals(resultCount, nodeSearchDocCOuntpair.get(SearchName), "Document Count Matches",
						"Document Count mismatches");
			}

			login.logout();
		}
	}

	/**
	 * @author Raghuram.A Date: 11/25/21 Modified date:N/A Modified by: N/A
	 */
	public String docResultCOuntCHeck(String SearchName) {
		// Result count
		String resultCount = getSelectSearchWithResultCount(SearchName).getText();
		if (resultCount.length() > 0) {
			base.stepInfo("Available Result Count : " + resultCount);
		} else {
			base.stepInfo("Result Count is empty : " + resultCount);
		}
		return resultCount;
	}

	/**
	 * @author Raghuram.A Date: 11/25/21 Modified date:N/A Modified by: N/A
	 */
	public void navigateToSavedSearchPage() {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Navigated to SavedSearch Page");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Raghuram.A Date: 11/25/21 Modified date:N/A Modified by: N/A
	 */
	public void navigateToSSPage() {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		base.waitForElement(NavigateToSearch());
		NavigateToSearch().waitAndClick(10);
		base.waitForElement(NavigateToSavedSearch());
		NavigateToSavedSearch().waitAndClick(10);
		verifySavedSearchPage();
	}

	/**
	 * @author Raghuram.A Date: 11/25/21 Modified date:N/A Modified by: N/A
	 * @throws InterruptedException
	 */
	public void verifyListofsharedNodesandSearchesAcrossUsers(String SGtoShare, List<String> newNodeList,
			int selectIndex, HashMap<String, String> nodeSearchpair, HashMap<String, String> searchGroupSearchpIDpair,
			String[] rolesToVerify, String passMessage) throws InterruptedException {

		for (int i = 0; i < rolesToVerify.length; i++) {
			System.out.println(rolesToVerify[i]);
			if (rolesToVerify[i].equals("RMU-2")) {
				login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			} else if (rolesToVerify[i].equals("PA-2")) {
				login.loginToSightLine(Input.pa2userName, Input.pa2password);
			}
			// Navigate to Saved Search
			navigateToSavedSearchPage();
			// verify Impact in Shared child Nodes
			verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
					searchGroupSearchpIDpair);
			base.passedStep(passMessage);

			login.logout();
		}

	}
	
	public void verifyListofsharedNodesandSearchesAcrossUsers(String SGtoShare, String node, List<String> newNodeList,
			int selectIndex, HashMap<String, String> nodeSearchpair, HashMap<String, String> searchGroupSearchpIDpair,
			String[] rolesToVerify, String passMessage) throws InterruptedException {

		for (int i = 0; i < rolesToVerify.length; i++) {
			System.out.println(rolesToVerify[i]);
			if (rolesToVerify[i].equals("RMU-2")) {
				login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			} else if (rolesToVerify[i].equals("PA-2")) {
				login.loginToSightLine(Input.pa2userName, Input.pa2password);
			}
			// Navigate to Saved Search
			navigateToSavedSearchPage();
			// verify Impact in Shared child Nodes
			verifyImpactinSharedchildNodes(SGtoShare,node, newNodeList, selectIndex, nodeSearchpair,
					searchGroupSearchpIDpair);
			base.passedStep(passMessage);

			login.logout();
		}

	}

	/**
	 * @author A Date: 9/15/21 Modified date:11/25/21 Modified by: Raghuram A
	 *         Description : Saved Search Selection- Sprint 03 - removed
	 *         waitForElement
	 * @throws InterruptedException
	 */
	public void savedSearch_SearchandSelect_New(String searchName, String select) throws InterruptedException {

		driver.waitForPageToBeReady();
		base.waitForElement(getSavedSearch_SearchName());
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		try {
			if (getSelectWithName(searchName).isElementAvailable(3)) {
//				softAssertion.assertTrue(true);
				base.passedStep("Search Found :" + searchName);
			} else {
//				softAssertion.assertTrue(false);
				base.failedMessage("Search Not Found :" + searchName);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		if (select.equals("Yes")) {
			Thread.sleep(5000); // in order to handle abnormal wait activities
			base.waitForElement(getSelectWithName(searchName));
			getSelectWithName(searchName).waitAndClick(10);
			getSelectWithName(searchName).checkIn();
		}

	}

	/**
	 * Date: 12/1/21 Modified date:N/A Modified by: N/A
	 * 
	 * @param newNodeList
	 * @param nodeSearchpair
	 * @param indexCount
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> modifyExistingSearchName(List<String> newNodeList,
			Map<String, String> nodeSearchpair, int indexCount) throws Exception {
		HashMap<String, String> nodeRenameSearchpair = new HashMap<String, String>();
		String node = null;
		for (int i = 0; i < indexCount; i++) {
			node = newNodeList.get(i);
			getSavedSearchGroupName(node).waitAndClick(5);
			Thread.sleep(3000);// To handle abnormal waits
			String searchName2 = "SearchName" + UtilityLog.dynamicNameAppender();

			driver.waitForPageToBeReady();
			savedSearch_SearchandSelect(nodeSearchpair.get(node), "Yes");
			renameSavedSearch(searchName2);
			System.out.println(nodeSearchpair.get(node) + " is renamed as " + searchName2);
			base.stepInfo(node + " : " + nodeSearchpair.get(node) + " is renamed as " + searchName2);
			nodeRenameSearchpair.put(node, searchName2);
			try {
				if (getSavedSearchNewGroupExpand().Displayed()) {
					getSavedSearchNewGroupExpand().waitAndClick(20);
				} else {
					getSavedSearchGroupName(node).waitAndClick(5);
				}
			} catch (Exception e) {
				System.out.println("End of Node");
			}
		}
		return nodeRenameSearchpair;
	}

	/**
	 * @author Raghuram.A Date: 12/1/21 Modified date:N/A Modified by: N/A
	 * @throws InterruptedException
	 * @throws ParseException
	 * 
	 */
	public void PAUrunsRMUAudioSearchValidationFlow(String securitygroupname, String audioSearch, String audioLanguage,
			String audioSearchString) throws InterruptedException, ParseException {

		search = new SessionSearch(driver);

		// Default SG count
		base.selectproject();
		base.stepInfo("Input String : " + audioSearchString);
		base.stepInfo("Input Language : " + audioLanguage);
		int countToCompare = search.audioSearch(Input.audioSearch, Input.audioLanguage);
		base.stepInfo("pureHit Count : " + countToCompare);

		// Impersonate PA to RMU
		base.rolesToImp("PA", "RMU");
		base.selectsecuritygroup(securitygroupname);
		base.stepInfo("Current SG : " + sg.get_CurrentSG().getText());

		base.stepInfo(
				"2. RMU (with security group SG)Saved Audio Search and Shared with specific Security group in Saved Search Screen.");

		base.stepInfo("Input String : " + Input.audioSearch);
		base.stepInfo("Input Language : " + Input.audioLanguage);
		int audiopureHit = search.audioSearch(Input.audioSearch, Input.audioLanguage);
		search.saveAdvanceSearchInNode(audioSearch, null);
		String currentSG2 = base.getsgNames().getText();
		base.stepInfo("SG Selected : " + currentSG2);
		base.stepInfo("pureHit Count : " + audiopureHit);

		// Share Searches - Navigate to Saved Search
		navigateToSavedSearchPage();
		shareSearchFlow(audioSearch, securitygroupname, "RMU");

		login.logout();
		base.stepInfo("Pre-requesties completed");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate to SavedSearch Page
		navigateToSSPage();

		// Content Search
		verifySharedGroupSearch(securitygroupname, audioSearch);
		String docCountWithSG = savedSearchExecute_SearchGRoup(audioSearch, audiopureHit);

		base.stepInfo("Search Result under Default Security group : " + countToCompare);
		base.stepInfo("Search Result under " + securitygroupname + " : " + docCountWithSG);
		base.textCompareNotEquals(docCountWithSG, Integer.toString(countToCompare),
				"Search results is in the Context of respective Security Group SG",
				"Search results is NOT in the Context of respective Security Group SG");

	}

	/**
	 * @author Raghuram.A Created on 11/08/2021 Modified date:N/A Modified by:N/A
	 *         description : checkButtonEnabled - method has to be updated
	 */
	public void checkButtonEnabled(Element element, String resultStatus, String buttonName) {
		String elementStatus = element.GetAttribute("class").toString();
		try {
			if (!elementStatus.equals("disabled") && resultStatus.equalsIgnoreCase("Should be Enabled")) {
				base.passedStep(buttonName + " is Enabled ");
			} else if (!elementStatus.equals("disabled") && resultStatus.equalsIgnoreCase("Should not be Enabled")) {
				base.failedStep(buttonName + " is Enabled ");
			} else if (elementStatus.equals("") && resultStatus.equalsIgnoreCase("Should be enabled")) {
				base.passedStep(buttonName + " is Enabled ");
			} else {
				base.failedStep("Condition failed");
			}
		} catch (Exception e) {
			base.failedStep("Condition failed");
		}

	}

	/**
	 * @Author Jeevitha
	 * @description : verify whether Node is present or Not
	 */
	public void verifyNodePresentInSG(String sg, String Node) {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		getSavedSearchGroupName(sg).waitAndClick(10);
		rootGroupExpansion();

		boolean flag = getSavedSearchGroupName(Node).isElementAvailable(5);
		if (flag == true) {
			getSavedSearchGroupName(Node).waitAndClick(10);
			System.out.println(Node + " is present in  " + "'" + sg + "'");
			base.stepInfo(Node + " is present in  " + "'" + sg + "'");
		} else if (flag == false) {
			System.out.println(Node + " is Not present in  " + "'" + sg + "'");
			base.stepInfo(Node + " is Not present in " + "'" + sg + "'");

		}
	}

	/**
	 * @author Jeevitha
	 * @param search
	 * @param node
	 * @param sg
	 * @param selectNode
	 * @return
	 * @throws InterruptedException
	 */
	public String verifyCompletedTime(String search, String node, String sg, boolean selectNode)
			throws InterruptedException {
		navigateToSavedSearchPage();
		getSavedSearchGroupName(sg).waitAndClick(10);
		rootGroupExpansion();
		if (selectNode) {
			getSavedSearchGroupName(node).waitAndClick(10);
			base.stepInfo(node + " is present in " + "'" + sg + "'");
		}
		savedSearch_SearchandSelect(search, "Yes");
		String dateAndTime = getLastCompletedTime(search).getText();
		System.out.println("Last Completed Date And Time : " + dateAndTime);
		String[] time = dateAndTime.split(" ");
		String Time = time[1].trim();
		base.stepInfo("Last Completed Time of search In SavedSearch Page : " + Time);
		return Time;
	}

	/**
	 * @author :Brundha method for executing the saved search document
	 */
	public void verifyExecutionOfFolder(String searchName) throws InterruptedException {
		try {
			this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			base.waitForElement(getSavedSearchNewGroupExpand());
			getSavedSearchNewGroupExpand().Click();

			base.waitForElement(getSavedSearchSharedWithProjectLastChildNode());
			getSavedSearchSharedWithProjectLastChildNode().Click();

			base.waitForElement(getSavedSearch_SearchName());
			getSavedSearch_SearchName().SendKeys(searchName);

			getSavedSearch_ApplyFilterButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectWithName(searchName));
			getSelectWithName(searchName).Click();
			driver.scrollPageToTop();
			getSavedSearchExecuteButton().waitAndClick(10);

			try {
				base.VerifySuccessMessage("Successfully added to background search.");
			} catch (Exception e1) {
				base.VerifySuccessMessage("Successfully added to background search.\r\n"
						+ "In the search group being executed, searches that are referring to other searches in the same search group will be errored out. You can select the errored searches and run them individually.");
			}

		} catch (Exception e) {
			base.failedStep("exception occured while handling exeution in saved search:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author :Brundha method for handling bulk folder action
	 */
	public void bulkFolder(String FolderName, int purehit) throws InterruptedException {
		String finalCount;
		search = new SessionSearch(driver);
		driver.waitForPageToBeReady();
		base.waitForElement(getSavedSearchToBulkFolder());
		getSavedSearchToBulkFolder().waitAndClick(10);
		finalCount = search.BulkActions_Folder_returnCount(FolderName);
		if (Integer.valueOf(finalCount).equals(purehit)) {
			base.passedStep("The document count verified successfully");
		} else {
			base.failedStep("the document count is not as excepted count");
		}
	}

	/**
	 * @author :Brundha method for executing default security group node
	 */
	public void shareDefaultSecurityGroup(String securitygroupname, String searchName) {

		try {
			this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			selectRootGroupTab(Input.mySavedSearch);
			rootGroupExpansion();
//			base.waitForElement(getSavedSearchNewGroupExpand());
//			getSavedSearchNewGroupExpand().Click();

			base.waitForElement(getSavedSearchSharedWithProjectLastChildNode());
			getSavedSearchSharedWithProjectLastChildNode().Click();
			getShareSerachBtn().Click();

			getShare_SecurityGroup(securitygroupname).waitAndClick(10);
			getShareSaveBtnNew().waitAndClick(5);
			base.VerifySuccessMessage("Share saved search operation successfully done.");
			base.CloseSuccessMsgpopup();

			base.waitForElement(getSavedSearch_SearchName());
			getSavedSearch_SearchName().SendKeys(searchName);

			getSavedSearch_ApplyFilterButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectWithName(searchName));
			getSelectWithName(searchName).waitAndClick(10);
			//
			driver.scrollPageToTop();
		} catch (Exception e) {
			base.failedStep("exception occured while handling share in default security group:" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @author Raghuram.A @Created on 12/17/2021 @ModifiedDate:N/A @ModifiedBy:N/A
	 * @param verificationDatas
	 * @param hitCount
	 * @param newNodeList
	 * @throws InterruptedException
	 */
	public void docViewAggregateCountCustomize(String[][] verificationDatas, int hitCount, List<String> newNodeList)
			throws InterruptedException {
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		search = new SessionSearch(driver);
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";

		for (int i = 0; i < verificationDatas.length; i++) {
			System.out.println(verificationDatas.length);
			String selectRootGroup = verificationDatas[i][3];
			login.loginToSightLine(verificationDatas[i][0].toString(), verificationDatas[i][1].toString());
			base.stepInfo(verificationDatas[i][0].toString());
			base.stepInfo("Via : " + selectRootGroup);

			// Launch DocVia via Saved Search
			navigateToSSPage();
			getSavedSearchGroupName(selectRootGroup).waitAndClick(3);
			base.stepInfo("Root node : " + newNodeList.get(0));
			selectNode1(newNodeList.get(0));
			docViewFromSS("View in DocView");

			// Load latency Verification
			Element loadingElement = search.getspinningWheel();
			loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
			driver.waitForPageToBeReady();
			String currentUrl = driver.getWebDriver().getCurrentUrl();
			softAssertion.assertEquals(Input.url + "DocumentViewer/DocView", currentUrl);
			base.stepInfo("Navigated to DocView Page : " + currentUrl);

			// Main method
			base.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
			int sizeofList = miniDocListpage.getListofDocIDinCW().size();
			System.out.println("Size : " + sizeofList);
			base.stepInfo("Available documents in DocView page : " + sizeofList);

			base.digitCompareEquals(hitCount, sizeofList,
					"displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group \" and when User Navigate to DocView",
					"Count Mismatches with the Documents");

			driver.Navigate().back();
			driver.waitForPageToBeReady();

			login.logout();
		}
	}

	/**
	 * @author Raghuram.A @Created on 12/17/2021 @ModifiedDate:N/A @ModifiedBy:N/A
	 * @param verificationDatas
	 * @param hitCount
	 * @param newNodeList
	 * @throws InterruptedException
	 */
	public void docListAggregateCountCustomize(String[][] verificationDatas, int hitCount, List<String> newNodeList)
			throws InterruptedException {

		for (int i = 0; i < verificationDatas.length; i++) {
			System.out.println(verificationDatas.length);
			String selectRootGroup = verificationDatas[i][3];
			login.loginToSightLine(verificationDatas[i][0].toString(), verificationDatas[i][1].toString());
			base.stepInfo(verificationDatas[i][0].toString());
			base.stepInfo("Via : " + selectRootGroup);

			// Launch DocVia via Saved Search
			navigateToSSPage();
			getSavedSearchGroupName(selectRootGroup).waitAndClick(3);
			base.stepInfo("Root node : " + newNodeList.get(0));
			selectNode1(newNodeList.get(0));
			int docCount = launchDocListViaSSandReturnDocCount();

			base.digitCompareEquals(hitCount, docCount,
					"displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group\" and User Navigate  to DocList",
					"Count Mismatches with the Documents");

			driver.Navigate().back();
			driver.waitForPageToBeReady();

			login.logout();
		}
	}

	/**
	 * @author Brundha Date : 12/18/21 Description: SavedSearchSearchID Modified on
	 *         : N/A modified by : N/A
	 */
	public String SavedSearchSearchID(String securitygroupname, String searchName) {
		getShareSerachBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getShare_SecurityGroup(securitygroupname));
		getShare_SecurityGroup(securitygroupname).waitAndClick(10);

		getShareSaveBtnNew().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		base.CloseSuccessMsgpopup();
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		// get Search ID
		String searchID = GetSearchID(searchName);
		return searchID;
	}

	/**
	 * @Author Jeevitha
	 * @param file
	 * @param groupName
	 * @param Node
	 * @param newNode
	 */
	public void deleteUploadedBatchFile(String file, String groupName, boolean Node, String newNode) {
		navigateToSSPage();
		getSavedSearchGroupName(groupName).waitAndClick(10);
		if (Node) {
			selectNode1(newNode);
		}
		rootGroupExpansion();
		base.waitForElement(getSelectUploadedFile(file));
		getSelectUploadedFile(file).waitAndClick(20);
		deleteFunctionality();
	}

	/**
	 * @Author Jeevitha
	 * @param GroupName
	 * @param selectNode
	 * @param newNode
	 * @param search
	 * @param select
	 * @throws InterruptedException
	 */
	public void SavedSearchToTermReport_New(String GroupName, boolean selectNode, String newNode, String search,
			String select) throws InterruptedException {
		navigateToSavedSearchPage();
		getSavedSearchGroupName(GroupName).waitAndClick(10);

		if (selectNode) {
			selectNode1(newNode);
		}
		savedSearch_SearchandSelect(search, select);
		driver.waitForPageToBeReady();
//		Element strBtnStatus = getSavedSearchToTermReport();
//		if (checkButtonEnabled(strBtnStatus, "Should be Enabled", "Report Action"))
		if (getSavedSearchToTermReport().Enabled()) {
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();// temporary fix because of application abnormal behaviour
			base.passedStep("Report Action is Enabled and has Access");
			getSavedSearchToTermReport().waitAndClick(5);
			base.waitTime(5);
			driver.waitForPageToBeReady();
		} else {
			System.out.println("Not Enabled - No Access");
		}

	}

	/**
	 * @author Brundha Date : 12/18/21 Description: SavedSearchSearchID Modified on
	 *         : N/A modified by : N/A
	 */
	public String getMySaveSearchID(String searchName, String select) {
		driver.waitForPageToBeReady();
		base.waitForElement(getSavedSearch_SearchName());
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		base.waitForElement(getSelectWithName(searchName));
		try {
			if (getSelectWithName(searchName).isElementAvailable(10)) {
				base.stepInfo("Search Found :" + searchName);
			} else {
				base.stepInfo("Search Not Found :" + searchName);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (select.equalsIgnoreCase("Yes")) {
			base.waitTime(5); // in order to handle abnormal wait activities
			// base.waitForElement(getSelectWithName(searchName));
			getSelectWithName(searchName).isElementAvailable(5);
			getSelectWithName(searchName).waitAndClick(6);
			getSelectWithName(searchName).checkIn();

		}
		String mySvedSearchID = GetSearchID(searchName);
		System.out.println("My saved search ID" + mySvedSearchID);
		return mySvedSearchID;
	}

	/**
	 * @author Brundha Date : 12/18/21 Description: verifySearchContents Modified on
	 *         : N/A modified by : N/A
	 * @throws InterruptedException Method Overloaded
	 */
	public void verifySearchContents(String searchName, String searchID, Boolean searchViaFilter,
			Boolean compareSearchID, Boolean additional1, Boolean additional2, String add1)
			throws InterruptedException {
		if (searchViaFilter) {
			savedSearch_SearchandSelect(searchName, "No");
			base.waitForElement(getSelectSearchWithID(searchName));
			searchIDtoCompare = getSelectSearchWithID(searchName).getText();
			System.out.println(searchIDtoCompare);
			base.stepInfo(searchName + " - Search ID : " + searchIDtoCompare);
		}
		if (compareSearchID) {
			base.textCompareNotEquals(searchID, searchIDtoCompare, "Different Search ID", "Same ID");
			base.stepInfo("Different Search ID");
		}
	}

	/**
	 * @author Jeevitha
	 * @param SGtoShare
	 * @param newNodeList
	 * @param selectIndex
	 * @param nodeSearchpair
	 * @param searchGroupSearchpIDpair
	 */
	public void verifyImpactsInSharedChildNodes(String SGtoShare, List<String> newNodeList, int selectIndex,
			HashMap<String, String> nodeSearchpair, HashMap<String, String> searchGroupSearchpIDpair) {

		String node = null, searchiD;
		for (int i = 0; i <= nodeSearchpair.size() - 1; i++) {
			node = newNodeList.get(i);
			// verify id should get changed
			try {
				driver.waitForPageToBeReady();
				Assert.assertTrue(getSavedSearchGroupName(node).isElementAvailable(3));
				System.out.println(node + " : Search group is Present in " + SGtoShare);
				base.passedStep(node + " : Search group is Present in " + SGtoShare);
				getSavedSearchGroupName(node).Click();
				if (i >= selectIndex) {
					// savedSearch_SearchandSelect(nodeSearchpair.get(node), "No");
					driver.waitForPageToBeReady();
					// get Search ID
					try {
						searchiD = GetSearchID(nodeSearchpair.get(node));
					} catch (NoSuchElementException e) {
						base.waitForElement(getSavedSearchGroupName(node));
						getSavedSearchGroupName(node).Click();
						driver.waitForPageToBeReady();
						searchiD = GetSearchID(nodeSearchpair.get(node));
					}
					base.stepInfo("Search ID after Share : " + searchiD);
					base.stepInfo(searchGroupSearchpIDpair.get(nodeSearchpair.get(node)));
					base.textCompareNotEquals(searchiD, searchGroupSearchpIDpair.get(nodeSearchpair.get(node)),
							"Different Search ID", "Same Search ID");

					// Result count
					String resultCount = getSelectSearchWithResultCount(nodeSearchpair.get(node)).getText();
					if (resultCount.length() > 0) {
						base.stepInfo("Available Result Count : " + resultCount);
					} else {
						base.stepInfo("Result Count is empty : " + resultCount);
					}

				} else {
					verifySavedSearch_isEmpty();
					base.passedStep("Not the selected search group");
				}
				getSavedSearchNewGroupExpand().waitAndClick(20);
			} catch (Exception e) {
				System.out.println(node + " : Search group is not Present in " + SGtoShare);
				base.failedStep(node + " : Search group is not Present in " + SGtoShare);
			}
		}

	}

	/**
	 * @author :Sowndarya.velraj
	 * @throws InterruptedException
	 * @Description: Method for checking the Execution status for searches from My
	 *               saved search tab in saved search page
	 */
	public void verifyExecutionStatusInSavedSearchPage(String statusMsg) throws InterruptedException {
		for (int i = 0; i < 1; i++) {
			driver.waitForPageToBeReady();
			if (noQueryReturnedText().isDisplayed()) {
				base.passedStep(statusMsg + " status not displayed");
				break;
			} else if (getLastStatus().contains(statusMsg)) {
				base.passedStep(statusMsg + "status displayed");
			}
		}
	}

	/**
	 * @Author Jeevitha @Modified By jeevitha @Modified On 4/03/2022
	 * @param SGtoShare
	 * @param newNodeList
	 * @param selectIndex
	 * @param nodeSearchpair
	 */
	public void verifyStatusAndCountInAllChildNode(String SGtoShare, List<String> newNodeList, int selectIndex,
			HashMap<String, String> nodeSearchpair) {
		List<String> list = new ArrayList<>();

		if (getSavedSearchGroupName(SGtoShare).isElementAvailable(3)) {
			getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		} else {
			getSavedSearchGroupNameC(SGtoShare).waitAndClick(10);
		}

		rootGroupExpansion();
		String node = null, searchiD;
		for (int i = 0; i <= nodeSearchpair.size() - 1; i++) {
			node = newNodeList.get(i);

			// verify id should get changed
			try {
				base.waitForElement(getSavedSearchGroupName(node));
				softAssertion.assertTrue(getSavedSearchGroupName(node).isDisplayed());
				System.out.println(node + " : Search group is Present in " + SGtoShare);
				base.passedStep(node + " : Search group is Present in " + SGtoShare);
				getSavedSearchGroupName(node).waitAndClick(5);
				if (i >= selectIndex) {

					list = getListFromSavedSearchTable("Search Name");
					for (String searchNames : list) {
						savedSearch_SearchandSelect(searchNames, "No");
						driver.waitForPageToBeReady();

						// GetStatus
//						String searchStatus = getLastStatus();
//						base.stepInfo(nodeSearchpair.get(node) + " Status : " + searchStatus);
//						softAssertion.assertEquals(searchStatus, "COMPLETED");
						verifyStatusByReSearch(searchNames, "COMPLETED", 10);

						// Result count
						String resultCount = getSelectSearchWithResultCount(searchNames).getText();

						if (resultCount.length() > 0) {
							base.stepInfo("Count Of Doc is : " + resultCount);
						} else {
							base.stepInfo("Count Of Doc is Empty : " + resultCount);
						}
					}
				} else {
					verifySavedSearch_isEmpty();
					base.passedStep("Not the selected search group");
				}
//				sgExpansion();
				rootGroupExpansion();
			} catch (Exception e) {
				System.out.println(node + " : Search group is not Present in " + SGtoShare);
				base.failedStep(node + " : Search group is not Present in " + SGtoShare);
			}
		}
		softAssertion.assertAll();
	}

	/**
	 * @Author Jeevitha
	 * @param specificHeaderName
	 * @param searchName
	 */
	public String ApplyShowAndHideFilter(String specificHeaderName, String searchName) {
		verifyHeaderIsPresent(specificHeaderName);
		getHideSHowBtn().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getFieldoptions(specificHeaderName));
		getFieldoptions_CC(specificHeaderName).waitAndClick(20);
		base.waitTillElemetToBeClickable(getHideSHowBtn());
		base.waitForElement(getbackGroundFilm());
		getbackGroundFilm().waitAndClick(5);
		driver.scrollPageToTop();
		verifyHeaderIsPresent(specificHeaderName);

		base.waitForElement(getNearDupeCount(searchName));
		String Count = getNearDupeCount(searchName).getText();
		base.stepInfo(specificHeaderName + "  : " + Count);
		System.out.println(specificHeaderName + " : " + Count);
		return Count;

	}

	/**
	 * @author Raghuram.A @Date : 12/23/21 @modifiedon : N/A @modifiedby : N/A
	 * @throws InterruptedException
	 */
	public void searchesToVerify(Boolean verifySearches, String[] searches, Boolean verifyNodes, String[] nodes,
			Boolean additional1, List<String> additionalList1) throws InterruptedException {

		if (verifySearches) {
			for (String searchData : searches) {
				savedSearch_SearchandSelect(searchData, "No");
			}
		}

		if (verifyNodes) {
//			getSavedSearchNewGroupExpand().waitAndClick(3);
			rootGroupExpansion();
			for (String nodeDatas : nodes) {
				verifyNodePresent(nodeDatas);
			}
		}
	}

	/**
	 * @author Raghuram.A @Date : 12/23/21 @modifiedon : 12/27/21 @modifiedby :
	 *         Raghuram
	 * @return
	 * @throws InterruptedException
	 */
	public HashMap<String, String> bulkTagorFolderViaSS(Boolean ssPge, Boolean rootNode, String shareTo,
			Boolean selectNode, Boolean selectSearch, String node, String searchName, String bulkAction, int loadTime,
			String TagName, Boolean newTag, Boolean tagVerify, String folderName, Boolean newFolder,
			Boolean folderVerify) throws InterruptedException {

		search = new SessionSearch(driver);
		HashMap<String, String> counts = new HashMap<String, String>();
		String finalCount = null;
		int pureHit = 0;
		int finalCountresult;

		// Landed on Saved Search
		if (ssPge) {
			navigateToSSPage();
		}

		if (rootNode) {
			getSavedSearchGroupName(shareTo).waitAndClick(5);
		}

		if (selectNode) {
			selectNode1(node);
			base.stepInfo("Root node selected : " + node);
		}

		if (bulkAction.equalsIgnoreCase("Tag")) {
			getSavedSearchToBulkTag().Click();
		} else if (bulkAction.equalsIgnoreCase("Folder")) {

			// verify Assign btn
			driver.waitForPageToBeReady();
			Element folderBtnStatus = getSavedSearchToBulkFolder();
			checkButtonEnabled(folderBtnStatus, "Should be Enabled", "Folder");

			getSavedSearchToBulkFolder().Click();
		}

		// Count load time check
		loadTimeCheck(loadTime);

		if (bulkAction.equalsIgnoreCase("Tag")) {
			if (newTag) {
				finalCount = search.bulkActions_TagSS_returnCount(TagName);
				base.stepInfo("Completed Bulk Tag");
			}
			if (tagVerify) {
				base.stepInfo(
						"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Tags (Select Same Tag which we have created in prerequesties.");
				search.switchToWorkproduct();
				search.selectTagInASwp(TagName);
				pureHit = search.serarchWP();
				finalCountresult = Integer.parseInt(finalCount);
				base.stepInfo("Finalized Tag count : " + finalCountresult);
				base.stepInfo("Aggreagate Tag search count : " + pureHit);

			}

		} else if (bulkAction.equalsIgnoreCase("Folder")) {
			if (newFolder) {
				finalCount = search.BulkActions_Folder_returnCount(folderName);
				base.stepInfo("Completed Bulk Folder mapping");
			}
			if (folderVerify) {
				base.stepInfo(
						"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Folder (Selected Same Folder which we have created in prerequesties.");
				search.switchToWorkproduct();
				search.selectFolderInASwp(folderName);
				pureHit = search.serarchWP();
				finalCountresult = Integer.parseInt(finalCount);

			}

		}
		counts.put("Finalize Count", finalCount);
		counts.put("PureHit Count", Integer.toString(pureHit));

		return counts;
	}

	/**
	 * @author Raghuram.A @Date : 12/23/21 @modifiedon : N/A @modifiedby : N/A
	 * @param time
	 * @throws InterruptedException
	 */
	public void loadTimeCheck(int time) throws InterruptedException {

		int latencyCheckTime = time;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";

		// Load latency Verification
		Element loadingElement = getTotalCountLoad();
		loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
	}

	/**
	 * @author Gopinath
	 * @Description : Method to open uploded batch file.
	 * @param batchFile : batchFile is String value that name of batch upload file.
	 */
	public void openUplodedBatchFile(String batchFile) {
		try {
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			getMySeraches().isElementAvailable(15);
			getMySeraches().waitAndClick(2);
			getSelectUploadedFile(batchFile).isElementAvailable(15);
			getSelectUploadedFile(batchFile).waitAndClick(2);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while open uploded batch file." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method to delete uploded batch file.
	 * @param batchFile : batchFile is String value that name of batch upload file.
	 */
	public void deleteUplodedBatchFile(String batchFile) {
		try {
			driver.Navigate().refresh();
			getMySeraches().isElementAvailable(15);
			getMySeraches().Click();
			getSelectUploadedFile(batchFile).isElementAvailable(15);
			getSelectUploadedFile(batchFile).Click();
			driver.scrollPageToTop();
			getSavedSearchDeleteButton().isElementAvailable(10);
			getSavedSearchDeleteButton().waitAndClick(10);
			getDeleteOkBtn().isElementAvailable(10);
			getDeleteOkBtn().waitAndClick(10);
			base.VerifySuccessMessage("Save search tree node successfully deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while deleting uploded batch file." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Description: Method for select the saved search and select
	 *         the Near dupe count Conceptually Similar Count
	 * @param searchname : searchname is String value that search name.
	 */
	public void selectSavedsearchwithNearDupeCountAndCSCount(String searchname) {
		try {

			savedSearch_Searchandclick(searchname);
			driver.waitForPageToBeReady();
			getChooseSearchRadiobtn(searchname).isElementAvailable(10);
			getChooseSearchRadiobtn(searchname).Click();
			getShowHideDropDown().isElementAvailable(10);
			getShowHideDropDown().waitAndClick(10);
			getSelectOptionsFromShowHideDropDown("Near Duplicate Count").isElementAvailable(10);
			getSelectOptionsFromShowHideDropDown("Near Duplicate Count").waitAndClick(5);
			getSelectOptionsFromShowHideDropDown("Conceptually Similar Count").isElementAvailable(10);
			getSelectOptionsFromShowHideDropDown("Conceptually Similar Count").Click();
			driver.scrollPageToTop();
			getbackGroundFilm().isElementAvailable(10);
			getbackGroundFilm().waitAndClick(7);
			getChooseSearchRadiobtn(searchname).isElementAvailable(10);
			scrollToElementOfPage(getChooseSearchRadiobtn(searchname));
			base.waitForElement(getSearchStatus(searchname, "DRAFT"));
			if (getSearchStatus(searchname, "DRAFT").isElementAvailable(10)) {
				base.passedStep("search is completed status for the saved search is 'DRAFT'");
			} else {
				base.failedStep("Search  status is not displayed as expected.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while selecting the saved search select the Near dupe count Conceptually Similar Count "
							+ e.getMessage());
		}

	}

	/**
	 * @author Gopinath Description: Method for count verifaction of draft basic
	 *         MetaData search.
	 * @param savedSearchName : savedSearchName is String value that saved search
	 *                        name.
	 */
	public void countVerifactionOfDraftBasicMetaDataSearch(String savedSearchName) {
		try {
			getFamilyCount(savedSearchName).isElementAvailable(10);
			String FamilyCount = getFamilyCount(savedSearchName).getText();
			getPureHitCountOfSavedSearch(savedSearchName).isElementAvailable(10);
			String PureitCount = getPureHitCountOfSavedSearch(savedSearchName).getText();
			getNearDupeCount(savedSearchName).isElementAvailable(10);
			String NearDupeCount = getNearDupeCount(savedSearchName).getText();
			getThreadedCount(savedSearchName).isElementAvailable(10);
			String ThreadedCount = getThreadedCount(savedSearchName).getText();
			Map<String, String> CountofSearch = new HashMap<String, String>();
			CountofSearch.put("NearDupe Count", NearDupeCount);
			CountofSearch.put("Pureit Count", PureitCount);
			CountofSearch.put("Threaded Count", ThreadedCount);
			CountofSearch.put("Family member count", FamilyCount);
			Set<String> CountKeySet = CountofSearch.keySet();

			for (String name : CountKeySet) {
				String count = CountofSearch.get(name);
				if (count.equals("")) {
					base.passedStep("The SavedSearch " + savedSearchName + " " + name + "is Blank");
					System.out.println("The SavedSearch " + savedSearchName + " " + name + "is Blank");
				} else {
					base.failedStep(savedSearchName + " is having " + count + name);
					System.out.println(savedSearchName + " is having " + count + name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while  count verifaction of draft basic MetaData search." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Description : Scrolling to Element by getting it's location
	 * @param element : element is scrolling to element.
	 */
	public void scrollToElementOfPage(Element element) {
		try {
			Point Location = element.getWebElement().getLocation();
			((JavascriptExecutor) driver).executeScript("window.scrollBy" + Location);

		} catch (Exception e) {
		}

	}

	/**
	 * @author Gopinath Description: Method to verify count field in saved search
	 *         table is blank.
	 * @param savedSearchName : savedSearchName is String value that saved search
	 *                        name.
	 */
	public void verifyCountFiledIsBlank(String savedSearchName) {
		try {
			getSavedSearch_SearchName().isElementAvailable(10);
			getSavedSearch_SearchName().SendKeys(savedSearchName);
			getSavedSearch_ApplyFilterButton().waitAndClick(10);
			base.waitTime(3);
			getCount(savedSearchName).isElementAvailable(10);
			String pureitCount = getCount(savedSearchName).getText();
			if (pureitCount.contentEquals("")) {
				base.passedStep("Count field in saved search table is blank");
			} else {
				base.failedStep("Count field in saved search table is not blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while  count verifaction of draft basic MetaData search." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Description: Method for count verifaction of draft basic
	 *         MetaData search.
	 * @param savedSearchName : savedSearchName is String value that saved search
	 *                        name.
	 */
	public void countVerifactionOfDraftBasicMetaDataSearch(Map<String, String> CountofSearch, String savedSearchName) {
		try {
			getFamilyCount(savedSearchName).isElementAvailable(10);
			String FamilyCount = getFamilyCount(savedSearchName).getText();
			getPureHitCountOfSavedSearch(savedSearchName).isElementAvailable(10);
			String PureitCount = getPureHitCountOfSavedSearch(savedSearchName).getText();
			getNearDupeCount(savedSearchName).isElementAvailable(10);
			String NearDupeCount = getNearDupeCount(savedSearchName).getText();
			getThreadedCount(savedSearchName).isElementAvailable(10);
			String ThreadedCount = getThreadedCount(savedSearchName).getText();
			if (CountofSearch.get("NearDupe Count").trim().contentEquals(NearDupeCount)) {
				base.passedStep("NearDupe Count is as exptected to acutal : " + NearDupeCount);
			} else {
				base.failedStep("NearDupe Count is not as exptected to acutal : " + NearDupeCount);
			}
			if (CountofSearch.get("Pureit Count").trim().contentEquals(PureitCount)) {
				base.passedStep("Pureit Count is as exptected to acutal : " + PureitCount);
			} else {
				base.failedStep("Pureit Count is not as exptected to acutal : " + PureitCount);
			}
			if (CountofSearch.get("Threaded Count").trim().contentEquals(ThreadedCount)) {
				base.passedStep("Threaded Count is as exptected to acutal : " + ThreadedCount);
			} else {
				base.failedStep("Threaded Count is not as exptected to acutal : " + ThreadedCount);
			}
			if (CountofSearch.get("Family member count").trim().contentEquals(FamilyCount)) {
				base.passedStep("Family member countt is as exptected to acutal : " + FamilyCount);
			} else {
				base.failedStep("Family member count is not as exptected to acutal : " + FamilyCount);
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while  count verifaction of draft basic MetaData search." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath Description: Method for select the saved search and select
	 *         the Near dupe count Conceptually Similar Count
	 * @param searchname : searchname is String value that search name.
	 */
	public void selectSavedsearchCount(String searchname) {
		try {

			savedSearch_Searchandclick(searchname);
			driver.waitForPageToBeReady();
			getChooseSearchRadiobtn(searchname).isElementAvailable(10);
			getChooseSearchRadiobtn(searchname).Click();
			getShowHideDropDown().isElementAvailable(10);
			getShowHideDropDown().waitAndClick(10);
			getSelectOptionsFromShowHideDropDown("Near Duplicate Count").isElementAvailable(10);
			getSelectOptionsFromShowHideDropDown("Near Duplicate Count").waitAndClick(5);
			driver.scrollPageToTop();
			getbackGroundFilm().isElementAvailable(10);
			getbackGroundFilm().waitAndClick(7);
			getChooseSearchRadiobtn(searchname).isElementAvailable(10);
			scrollToElementOfPage(getChooseSearchRadiobtn(searchname));

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while selecting the saved search select the Near dupe count Conceptually Similar Count "
							+ e.getMessage());
		}

	}

	/**
	 * @author Gopinath Description: Method to verify pending status appeared on
	 *         saved search table.
	 */
	public void verifyPendingStatusAppearedSavedSearchTable() {
		try {
			driver.scrollPageToTop();
			getNumberOfSavedSearchToBeShown().isElementAvailable(15);
			getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");
			getStatusDropDown().isElementAvailable(15);
			getStatusDropDown().selectFromDropdown().selectByVisibleText("PENDING");
			getSavedSearch_ApplyFilterButton().waitAndClick(20);
			boolean status = getPendingStatus().isDisplayed();
			if (status) {
				base.passedStep("Pending stats is appeared on table successfully by batch upload");
			} else {
				base.failedStep("Failed to get pending status on saved search table after batch upload");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verifying pending status appeared on saved search table."
					+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath Description: Method to verify pending status appeared for
	 *         advanced search on saved search table.
	 */
	public void verifyPendingStatusForAdvanceSearchAppeared() {
		try {
			driver.scrollPageToTop();
			getNumberOfSavedSearchToBeShown().isElementAvailable(15);
			getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");
			getStatusDropDown().isElementAvailable(15);
			getStatusDropDown().selectFromDropdown().selectByVisibleText("PENDING");
			getSavedSearch_ApplyFilterButton().waitAndClick(20);
			boolean status = getPendingStatusAdvancedQueary().isDisplayed();
			if (status) {
				base.passedStep("Pending status is appeared for advanced search on table successfully by batch upload");
			} else {
				base.failedStep(
						"Failed to get pending status for advanced search on saved search table after batch upload");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verifying pending status appeared for advanced search on saved search table."
							+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath Description: Method to verify completed status appeared for
	 *         advanced search on saved search table.
	 */
	public void verifyCompletedStatusForAdvanceSearchAppeared() {
		try {
			driver.scrollPageToTop();
			getNumberOfSavedSearchToBeShown().isElementAvailable(15);
			getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");
			getStatusDropDown().isElementAvailable(15);
			getStatusDropDown().selectFromDropdown().selectByVisibleText("COMPLETED");
			getSavedSearch_ApplyFilterButton().waitAndClick(20);
			boolean status = getPendingStatusAdvancedQueary().isDisplayed();
			if (status) {
				base.passedStep(
						"Completed status is appeared for advanced search on table successfully by batch upload");
			} else {
				base.failedStep(
						"Failed to get completed status for advanced search on saved search table after batch upload");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verify completed status appeared for advanced search on saved search table.."
							+ e.getMessage());
		}
	}

	/**
	 * @Author Jeevitha
	 */
	public void performExecute() {
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		base.waitForElement(getSavedSearchExecuteButton());
		getSavedSearchExecuteButton().Click();

		if (getExecuteContinueBtn().isElementAvailable(10)) {

			String executeActualMSg = getExecutePopMSg().getText();
			String executeExpectedMsg = "All child searches in all child groups will be submitted to the search engine for execution";
			base.textCompareEquals(executeActualMSg, executeExpectedMsg, executeActualMSg,
					"Popup Msg is Not As Expected");

			getExecuteContinueBtn().waitAndClick(10);
		} else {
			System.out.println("Saved Search Execute Popup is Not Dispalyed ");
		}

		base.waitForElement(getSuccessMsgHeader());
		if (getSuccessMsgHeader().getText() == "Warning !") {
			System.out.println(getSuccessMsgHeader().getText());
			System.out.println(getSuccessMsg().getText());
			base.failedMessage("Verification Failed. Warning message appeared");
		} else {
			base.stepInfo("Verification Passed. Warning message doesn't appeared");
			base.CloseSuccessMsgpopup();
		}

		base.VerifySuccessMessage(
				"Successfully added to background search. In the search group being executed, searches that are referring to other searches in the same search group will be errored out. You can select the errored searches and run them individually.");

		String expected = "Successfully added to background search. In the search group being executed, searches that are referring to other searches in the same search group will be errored out. You can select the errored searches and run them individually.";
		String actualMsg = getNotficationStatusMessage().getText();
		softAssertion.assertEquals(actualMsg, expected);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		int afterBg = base.initialBgCount();

		if (Bgcount < afterBg) {
			System.out.println("Got notification!");
			base.stepInfo("Recieved Notification ");
		}
		softAssertion.assertAll();
	}

	/**
	 * @Author Jeevitha
	 * @param headerName
	 * @return
	 */
	public List<String> getListFromSavedSearchTable(String headerName) {
		int i;
		i = base.getIndex(gettableHeaders(), headerName);
		System.out.println(headerName + "--" + i);

		base.waitForElement(getNumberOfSavedSearchToBeShown());
		getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.waitForPageToBeReady();
		List<String> elementNames = new ArrayList<>();
		List<WebElement> elementList = null;
		elementList = getColumnValues(i).FindWebElements();
		for (WebElement webElementNames : elementList) {
			String elementName = webElementNames.getText();
			elementNames.add(elementName);
		}
		return elementNames;
	}

	/**
	 * @Author Jeevitha
	 * @param headerName
	 * @param columnName
	 * @throws InterruptedException
	 */
	public void StatusAndCountForListOfSearches(String headerName, String columnName) throws InterruptedException {
		List<String> list = getListFromSavedSearchTable(headerName);
		System.out.println(list);

		for (String search : list) {
			getDocCountAndStatusOfBatch(search, columnName, true);
			softAssertion.assertAll();
		}
	}

	/**
	 * @author Raghuram Created on 12/30/2021 Modified date:N/A Modified by:N/A
	 */
	public void deleteListofNode(String GroupName, List<String> nodeList, Boolean navigateToSS, Boolean sgToSelect) {

		if (navigateToSS) {
			navigateToSSPage();
		}
		if (sgToSelect) {
			getSavedSearchGroupName(GroupName).waitAndClick(10);
		}
		getSavedSearchNewGroupExpand().waitAndClick(5);
		for (String node : nodeList) {
			getCreatedNode(node).waitAndClick(5);
			base.waitForElement(getSavedSearchDeleteButton());
			deleteFunctionality();
		}

	}

	/**
	 * @author Raghuram.A Created on 12/30/2021 Modified date:N/A Modified by:N/A
	 */
	public List<String> verifyListOfNodes(List<String> listOfNodesToVerify, List<String> listOfNodes, Boolean custom,
			int number_of_sheets, String fileName, String additional1, Boolean additionalB1, Boolean selectGroupExpand,
			String groupName) {

		if (selectGroupExpand) {
			getSavedSearchGroupName(groupName).waitAndClick(2);
			rootGroupExpansion();
		}

		if (custom) {
			for (int i = 1; i <= number_of_sheets; i++) {
				String batchNodeToCheck = fileName + "_" + i + "_Sheet" + i;
				softAssertion.assertTrue(verifyNodePresent(batchNodeToCheck),
						"Searches not uploaded in Saved search screen.");
				listOfNodesToVerify.add(batchNodeToCheck);
			}
		}
		return listOfNodesToVerify;

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param searchName
	 * @throws InterruptedException
	 */
	public void savedSearchToTally_sharedToSG(String searchName) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);

		savedSearch_SearchandSelect(searchName, "Yes");
		base.waitForElement(getSavedSearchToTally());

		getSavedSearchToTally().Click();

		base.waitForElement(getSelectedSourceConcept());

		softAssertion.assertEquals(getSelectedSourceConcept().getText().toString(),
				"Search: Selected Documents from SavedSearch: " + searchName + "");

		softAssertion.assertAll();
	}

	/**
	 * @author Raghuram.A Created on 12/30/2021 Modified date:N/A Modified by:N/A
	 * @Description : Delete multiple searches
	 * @param groupName
	 * @param listSize
	 * @param limitValue
	 * @throws InterruptedException
	 */
	public void deleteSearches(String groupName, int listSize, int limitValue) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		getSavedSearchGroupName(groupName).waitAndClick(10);

		for (int i = listSize; i >= limitValue; i--) {
			if (getsearch().isElementAvailable(2)) {
				getsearch().waitAndClick(20);
				deleteFunctionality();
			} else {
				System.out.println("Searches are deleted completly");
				break;
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param TagName
	 * @throws InterruptedException
	 */
	public void bulkUnTag(final String TagName) throws InterruptedException {

		base.waitForElement(getBulkUntagbutton());

		getBulkUntagbutton().Click();
		base.waitForElement(getSelectTagExisting(TagName));

		getSelectTagExisting(TagName).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		driver.Manage().window().fullscreen();

		getContinueButton_Untag().Click();
		driver.Manage().window().maximize();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		getUnBulkTagConfirmationButton().Click();

		bc.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 2;
			}
		}), Input.wait60);
		System.out.println("Bulk Untag is done, Tag is : " + TagName);
		UtilityLog.info("Bulk Untag is done, Tag is : " + TagName);
	}

	public void bulkUnFolder(final String folderName) throws InterruptedException {
		base.waitForElement(getBulkUnFolderbutton());
		getBulkUnFolderbutton().Click();
		base.waitForElement(getSelectFolderExisting(folderName));

		getSelectFolderExisting(folderName).waitAndClick(5);
		driver.Manage().window().fullscreen();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton_Untag().Click();

		driver.Manage().window().maximize();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		bc.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Unfolder is done, folder is : " + folderName);
		UtilityLog.info("Bulk Unfolder is done, folder is : " + folderName);
	}

	/**
	 * @author Raghuram.A
	 * @param search
	 */
	public void checkStatus(String search) {
		if (getSearchStatus(search, "COMPLETED").isElementAvailable(5)) {
			base.passedStep("search execution is completed status for the saved search changed to 'Completed'");
		} else {
			base.failedStep("Search execution status is not displayed as expected.");
		}
	}

	/**
	 * @Author Jeevitha.Modified on 02/05/2022
	 * @param headerName
	 * @return
	 */
	public String getListFromSavedSearchTable1(String headerName, String search) {
		int i = base.getIndex(gettableHeaders(), headerName);
		System.out.println(i);

		base.waitForElement(getSearchColumnValue(search, i));
		String value = getSearchColumnValue(search, i).getText();
		System.out.println(value);
		base.stepInfo(headerName + " : " + value);
		return value;

	}

	/**
	 * @author Sowndarya
	 * @param statusToCHeck
	 * @throws InterruptedException
	 */
	public void verifyStatusFilter(String statusToCHeck) throws InterruptedException {
		base.stepInfo("To verify Completed Status By applying filter");
		getStatusDropDown().waitAndClick(2);
		getLastStatusAs(statusToCHeck).waitAndClick(2);
		getSavedSearch_ApplyFilterButton().waitAndClick(2);
		verifyExecutionStatusInSavedSearchPage(statusToCHeck);
	}

	/**
	 * @author Raghuram.A @Modified By Jeevitha @Modified On 2/03/2022
	 * @param statusToCHeck
	 * @param column
	 * @param multiPage
	 * @throws InterruptedException
	 */
	public void verifyStatusFilterT(String statusToCHeck, String column, Boolean multiPage)
			throws InterruptedException {
		List<String> list = new ArrayList<>();
		String passMsg = statusToCHeck + " : is the Status Displayed";
		String failMsg = "Able to See other status";

		System.out.println(statusToCHeck);
		base.stepInfo("To verify " + column + " Status By applying filter");

		int count = 0;
		if (multiPage) {
			base.waitForElement(getNumberOfSavedSearchToBeShown());

			getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");

			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPageNumCount().Visible();
				}
			}), Input.wait30);
			count = ((getPageNumCount().size()) - 2);
			System.out.println("Number of Pages Available : " + count);
			base.stepInfo("Number of Pages Available : " + count);

			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			for (int i = 1; i <= count; i++) {
				list = getListFromSavedSearchTable(column);
				driver.scrollingToBottomofAPage();

				if (getPageNextBtn().isElementAvailable(3)) {
					getPageNextBtn().waitAndClick(3);
				}
				System.out.println("Page Number : " + i);
				base.stepInfo("Page Number : " + i);
				driver.scrollPageToTop();

			}
		} else {
			getStatusDropDown().waitAndClick(2);
			getLastStatusAs(statusToCHeck).waitAndClick(2);
			Thread.sleep(1000);
			getSavedSearch_ApplyFilterButton().waitAndClick(2);
			list = getListFromSavedSearchTable(column);
		}
		if (list.size() > 0) {
			base.compareListWithString(list, statusToCHeck, passMsg, failMsg);
		} else {
			base.failedMessage("No data Available");
		}

	}

	/**
	 * @author Raghuram.A @Date : 12/23/21 @modifiedon : N/A @modifiedby : N/A
	 * @param time
	 * @throws InterruptedException
	 */
	public void loadTimeCheckWithPageSpinningWheel(int time) throws InterruptedException {

		int latencyCheckTime = time;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";

		// Load latency Verification
		Element loadingElement = getspinningWheel();
		loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
	}

	/**
	 * @author Mohan.Venugopal Modified by: NA Modified date: NA
	 * @description used to select default security grp tab and select savesearch
	 *              and clickon folder icon
	 * @param searchName
	 */
	public void selectWithDefaultSecurityGroupAndFolder(String searchName, String folderNam)
			throws InterruptedException {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		base.waitForElement(getSavedSearchGroupName(Input.shareSearchDefaultSG));
		getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(5);
		base.waitForElement(getSavedSearch_SearchName());
		getSavedSearch_SearchName().SendKeys(searchName);

		base.waitTime(3);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		base.waitTime(3);
		getSelectWithName(searchName).waitAndClick(10);

		base.waitForElement(getSavedSearchToBulkFolder());
		getSavedSearchToBulkFolder().waitAndClick(5);
		search = new SessionSearch(driver);
		search.BulkActions_Folder(folderNam);

	}

	/**
	 * @Author Jeevitha
	 * @Description : Select Security Group From Saved Search Table
	 */
	public void selectRootGroupTab(String groupName) {

		if (groupName.equals(Input.shareSearchDefaultSG) || groupName.equals(Input.shareSearchPA)
				|| groupName.contains("Shared with ") || (groupName.equals(Input.mySavedSearch))) {

			if (getSavedSearchGroupName(groupName).isElementAvailable(2)) {
				getSavedSearchGroupName(groupName).waitAndClick(15);
			} else {
				getSavedSearchGroupNameC(groupName).waitAndClick(15);
			}

			System.out.println("Clicked :" + groupName);
		}
	}

	/**
	 * @author Jayanthi.ganesan This method will select the node under specified
	 *         search group.
	 * @param searchGroup[Search group under which node needs to be selected]
	 * @param NodeName[Name      of node needs to be selected]
	 * 
	 * @Modified By Jeevitha @Modified On 2/03/2022
	 */
	public void selectNodeUnderSpecificSearchGroup(String GroupName, String NodeName) {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		getSavedSearchGroupName(GroupName).waitAndClick(10);
		selectNode1(NodeName);

	}

	/**
	 * @author Raghuram.A
	 * @param savedSearchName - Saved Search to select
	 * @param status          - SavedSearch status to check
	 * @param attempts        - no.of attempts to try
	 * @throws InterruptedException
	 */
	public void verifyStatusByReSearch(String savedSearchName, String status, int attempts)
			throws InterruptedException {

		for (int i = 0; i <= attempts; i++) {
			if (!getSearchStatus(savedSearchName, status).isElementAvailable(3)) {
				System.out.println(i + " - " + status);
				if (i == attempts) {
					base.failedStep("Expected status not present");
				}
				savedSearch_SearchandSelect(savedSearchName, "Yes");
			} else {
				base.stepInfo(" Actual Status - " + status);
				break;
			}
		}
	}

	/**
	 * @author Raghuram.A
	 * @description - Expanding the selected rootGroup
	 */
	public void rootGroupExpansion() {

		if (getSavedSearchExpandStats().isElementAvailable(5)) {
			sgExpansion();
		} else {
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			sgExpansion();
		}
	}

	/**
	 * @author Raghuram.A
	 * @param savedSearchName - Respective SavedSearh name
	 * @param additional      - additional parameter for future use
	 * @param additionalInt   - additional parameter for future use
	 * @throws InterruptedException
	 */
	public void verifyStatusBasedOnCount(String savedSearchName, String additional, int additionalInt)
			throws InterruptedException {

		String docCount = docResultCOuntCHeck(savedSearchName);
		if (docCount.trim().equals("")) {
			if (getSearchStatus(savedSearchName, "INPROGRESS").isElementAvailable(1)) {
				base.passedStep("DocCount is : " + docCount + " and Status is in Inprogress");
			} else {
				base.failedStep("Status failed");
			}
		} else {
			base.stepInfo("Doc Count : " + docCount + " is present");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Decsription : verifies Saved Search Page Grid Position After Resize
	 */
	public void verifyGridPosition() {
		// value before maximize
		driver.waitForPageToBeReady();
		base.waitForElement(getGridHeader());
		String headerWidthBefore = getGridHeader().GetAttribute("style");
		String bodyWidthBefore = getGridBody().GetAttribute("style");

		// Maximize
		driver.Manage().window().maximize();

		// value After Maximize
		driver.waitForPageToBeReady();
		base.waitForElement(getGridHeader());
		String headerWidthAfter = getGridHeader().GetAttribute("style");
		String bodyWidthAfter = getGridBody().GetAttribute("style");

		String passMsg = "The Header of Grid position is As expected : " + headerWidthAfter;
		String failMsg = "The Grid Position is Not As Expected";
		base.textCompareEquals(headerWidthBefore, headerWidthAfter, passMsg, failMsg);

		String passMsgGridBody = "The Body of Grid position is As expected : " + bodyWidthAfter;
		base.textCompareEquals(bodyWidthBefore, bodyWidthAfter, passMsgGridBody, failMsg);
	}

	/**
	 * @author Raghuram.A
	 * @param searchIDlist - List of search id's
	 * @param countIDindex - header/colum to pick
	 * @return
	 */
	public HashMap<String, String> collectionOfSearchIdAndItsCount(List<String> searchIDlist, int IDindex) {
		HashMap<String, String> mapPair = new HashMap<String, String>();
		for (String searchIDName : searchIDlist) {
			System.out.println(searchIDName);
			String countIDindex = getValueAgainstID(searchIDName, IDindex).getText();
			mapPair.put(searchIDName, countIDindex);
		}
		return mapPair;
	}

	/**
	 * @author Raghuram.A
	 * @param searchIDlist - List of search id's
	 * @param mapPair      - hasSet data to compare
	 * @param index        - header/column to pick
	 */
	public void SearchIdAndDataToCompare(List<String> searchIDlist, HashMap<String, String> mapPair, int index) {
		for (String value : searchIDlist) {
			String countValue = mapPair.get(value);
			base.stepInfo("Doc Count for " + value + " in Saved Search Page : " + countValue);
			String countValuefromBG = getValueAgainstIDbg(value, index).getText();
			base.stepInfo("Doc Count for " + value + " in Background Page : " + countValuefromBG);
			base.textCompareEquals(countValuefromBG, countValue,
					"Count of results from saved search after batch upload matchs with the actual docs from background tasks page with large data  ",
					"FAIL");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param action - action selection
	 */
	public void docViewFromSS(String action) {
		if (getLaunchDocView().isElementAvailable(2)) {
			getLaunchDocView().waitAndClick(3);
		} else if (getToDocView().isElementAvailable(2)) {
			getToDocView().waitAndClick(2);
			// to handle old and new changes
			// - can be removed once PT build is moved to UAT
		}
		if (getToDocViewAction(action).isElementAvailable(2)) {
			System.out.println(action);
			getToDocViewAction(action).waitAndClick(3);
		}
	}

	/**
	 * @Author Jeevitha
	 * @param specificHeaderName
	 * @throws InterruptedException
	 */
	public void checkHideandShowFunction(String specificHeaderName) throws InterruptedException {

		verifyHeaderIsPresent(specificHeaderName);
		getHideSHowBtn().waitAndClick(5);
		try {
			base.waitForElement(getFieldoptions(specificHeaderName));
			getFieldoptions(specificHeaderName).waitAndClick(5);
			base.stepInfo("Unchecked Search Name Field");
		} catch (Exception e) {
			e.printStackTrace();
		}
		base.waitForElement(getbackGroundFilm());
		getbackGroundFilm().waitAndClick(5);

		base.waitTime(4);
		verifyHeaderIsPresent(specificHeaderName);
		softAssertion.assertTrue(getFieldHeader(specificHeaderName).isElementPresent());
		softAssertion.assertAll();
	}

	/**
	 * @author Mohan.Venugopal
	 * @descripton: To Verify Cloning project saved search terms
	 */
	public void verifySavedSearchDetailsForCloningProject(String searchGroup) {
		base.waitForElement(getSavedSearchGroupName(searchGroup));
		getSavedSearchGroupName(searchGroup).waitAndClick(5);
		base.waitForElementCollection(getCounts());
		ElementCollection counts = getCounts();
		System.out.println(counts);
		if (counts != null) {
			base.passedStep("Saved Saerch list contains more than 3 save searches");
		}
		base.waitForElement(getSavedSearchTableDraftName());
		String draftName = getSavedSearchTableDraftName().getText();
		System.out.println(draftName);
		base.passedStep("Draft Name is present in the Saved Search List");
		base.waitForElement(getCountofDocs());
		String docsCount = getCountofDocs().getText();
		System.out.println(docsCount);
		if (draftName.equals("DRAFT") && docsCount.isEmpty()) {
			base.passedStep("Copied searches is in 'DRAFT' state and doesn't have any counts associated with them. ");
		} else {
			base.failedStep("The req fields are not present in the saved search list");
		}
	}

	/**
	 * @Author
	 * @param batchFilePath
	 * @param batchFile
	 * @return
	 */
	public List<String> batchFileUpload(String batchFilePath, String batchFile) {
		driver.waitForPageToBeReady();
		ArrayList<Integer> actualCounts = new ArrayList<Integer>();
		uploadWPBatchFile_New(batchFile, batchFilePath);

		base.waitForElement(getNumberOfSavedSearchToBeShown());
		getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");

		base.waitForElementCollection(getCounts());
		driver.scrollingToBottomofAPage();
		String value = "0";
		for (int i = 1; i <= getCounts().size(); i++) {
			try {
				value = driver.FindElement(By.xpath("//*[@id='SavedSearchGrid']/tbody/tr[" + i + "]/td[4]")).getText();
				actualCounts.add(Integer.parseInt(value));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				value = "0";
				actualCounts.add(Integer.parseInt(value));
			}

		}

		System.out.println("ActualCounts :" + actualCounts);
		UtilityLog.info("ActualCounts :" + actualCounts);

		List<String> searchNames = base.availableListofElements(getSearchNames());

		return searchNames;
	}

	/**
	 * @Author
	 * @param metadata
	 * @param StyletoChoose
	 * @param fieldTypeToChoose
	 * @param TextTypetoChoose
	 * @param NewLineTypetoChoose
	 * @param DateStyleTypetoChoose
	 * @return
	 * @throws InterruptedException
	 */
	public int configureExportPopup(List<String> metadata, String StyletoChoose, String fieldTypeToChoose,
			String TextTypetoChoose, String NewLineTypetoChoose, String DateStyleTypetoChoose)
			throws InterruptedException {

		base.waitForElement(getExportPopup());

		if (StyletoChoose != null) {
			base.waitForElement(getStyle_dropdown());
			getStyle_dropdown().selectFromDropdown().selectByVisibleText(StyletoChoose);
		}
		if (fieldTypeToChoose != null) {
			base.waitForElement(getField_dropdown());
			getField_dropdown().selectFromDropdown().selectByVisibleText(fieldTypeToChoose);
		}
		if (TextTypetoChoose != null) {
			base.waitForElement(getText_dropdown());
			getText_dropdown().selectFromDropdown().selectByVisibleText(TextTypetoChoose);
		}
		if (NewLineTypetoChoose != null) {
			base.waitForElement(getNewLine_dropdown());
			getNewLine_dropdown().selectFromDropdown().selectByVisibleText(NewLineTypetoChoose);
		}
		if (DateStyleTypetoChoose != null) {
			base.waitForElement(getNewLine_dropdown());
			getDateStyle_dropdown().selectFromDropdown().selectByVisibleText(DateStyleTypetoChoose);
		}
		for (int i = 0; i < metadata.size(); i++) {
			base.waitForElement(getSelectMatadata(metadata.get(i)));
			getSelectMatadata(metadata.get(i)).waitAndClick(5);
		}
		base.waitForElement(getAddToSelected_Button());
		getAddToSelected_Button().waitAndClick(3);
		base.waitForElement(getRunReport_Button());
		getRunReport_Button().waitAndClick(3);
		driver.waitForPageToBeReady();
		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		base.VerifySuccessMessage(
				"Your Report has been added into the Background successfully. Once it is complete, the "
						+ "\"bullhorn\""
						+ " icon in the upper right-hand corner will turn red, and will increment forward.");

		return Bgcount;
	}

	/**
	 * @Author Jeevitha
	 * @param initialBgcount
	 * @param expectedFormat
	 */
	public void downloadExportFile(int initialBgcount, String expectedFormat) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == initialBgcount + 1;
			}
		}), Input.wait60);
		base.waitForElement(base.getBackgroundTask_Button());
		base.getBackgroundTask_Button().waitAndClick(10);
		base.waitTime(3);
		base.waitForElement(getBckTask_selectExport());
		getBckTask_selectExport().waitAndClick(10);

		base.waitTime(2);
		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "\\";

		// wait until file download is complete
		base.waitUntilFileDownload();

		// base.csvReader();
		ReportsPage report = new ReportsPage(driver);
		File a = report.getLatestFilefromDir(testPath);
		System.out.println(a.getName());
		base.stepInfo(a.getName());

		String fileName = a.getName();

		String filePath = testPath + fileName;
		System.out.println(fileName);
		base.stepInfo("Downloade File  : " + filePath);

		// Verify File Extension
		String fileFormat = FilenameUtils.getExtension(fileName);

		String passMsg = "Downloaded File : " + fileName + "    And Verified Format IS  : " + fileFormat;
		String failMsg = "Downloaded File Format is Not As Expected";
		base.textCompareEquals(fileFormat, expectedFormat, passMsg, failMsg);
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/05/22
	 * @ModifiedOn :N/A
	 * @ModifiedBy : N/A
	 * @param groupName - Select root group name
	 */
	public void selectSearchGroupTab(String groupName, String rootFolder) {
		if (groupName.equalsIgnoreCase(Input.preBuilt)) {
			getSavedSearchGroupName(rootFolder).waitAndClick(10);
			System.out.println("Clicked :" + rootFolder);
			rootGroupExpansion();
			base.passedStep(rootFolder + " Selected And Expanded");
			getSavedSearchGroupName(Input.preBuilt).waitAndClick(10);
			driver.waitForPageToBeReady();
			System.out.println("Clicked : Pre-Built Models");
			base.stepInfo("Clicked : Pre-Built Models");
		} else {
			getSavedSearchGroupName(groupName).waitAndClick(10);
			System.out.println("Clicked :" + groupName);
			base.stepInfo("Clicked :" + groupName);

		}
	}

	/**
	 * @Author Jeevitha
	 * @Decsription : verify Notification of saved search screen
	 * @param initialCount
	 * @param size
	 * @return
	 */
	public List<String> verifyExecuteAndReleaseNotify(int initialCount, int size) {
		List<String> id = new ArrayList<>();
		driver.waitForPageToBeReady();
		int afterCount = base.initialBgCount();
		if (afterCount == initialCount + size) {
			for (int i = 1; i <= size; i++) {
				base.getBullHornIcon().waitAndClick(10);
				String completeStatus = getNotificationStatus(i).getText();
				base.stepInfo("Recieved Notification : " + completeStatus);
				String[] notify = completeStatus.split(" ");
				int count = notify.length;
				System.out.println(count);
				String lastID = notify[count - 3];
				String status = notify[count - 1];
				System.out.println("Id : " + lastID);
				base.stepInfo("Id : " + lastID);
				System.out.println("Status : " + status);
				base.stepInfo("Status : " + status);

				id.add(lastID);
			}
		} else {
			base.failedStep("Didnot recieve Notification");
		}
		return id;
	}

	/**
	 * @Author : Jeevitha
	 * @Description : Perform Release action On saved search Screen
	 * @param securityGroup
	 * @param release
	 */
	public void performReleaseAction(String securityGroup, boolean release) {
		String expected = "Records saved successfully";
		int Bgcount = base.initialBgCount();
		base.waitForElement(getReleaseIcon());
		getReleaseIcon().waitAndClick(5);

		// CHoose SG
		base.waitForElement(getReleaseDocToSG(securityGroup));
		getReleaseDocToSG(securityGroup).waitAndClick(5);

		// Click Release OR Unrelease Button
		if (release) {
			base.waitForElement(getReleaseBtn());
			getReleaseBtn().waitAndClick(5);

			if (getContinueButton().isElementAvailable(5)) {
				getContinueButton().waitAndClick(10);
			}

			// Finalize release button
			base.waitForElement(getFinalizeReleaseButtonfromPopup());
			getFinalizeReleaseButtonfromPopup().waitAndClick(6);
			base.stepInfo("Released to SG : " + securityGroup);

		} else {
			base.waitForElement(getUnreleaseBtn());
			getUnreleaseBtn().waitAndClick(10);
			base.stepInfo("Unreleased From SG : " + securityGroup);
			if (getContinueButton().isElementAvailable(5)) {
				getContinueButton().waitAndClick(10);
			}
		}

		base.VerifySuccessMessage(expected);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		int afterBgcount = base.initialBgCount();
		if (afterBgcount > Bgcount) {
			base.stepInfo("Recieved Notification");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: to verify the count of Tree node in Saved Search
	 * @return
	 */
	public int verifyTreeNodeCount() {

		driver.waitForPageToBeReady();

		ElementCollection treeNodesCounts = getTreeNodesCounts();
		int size = treeNodesCounts.size();
		System.out.println(size);

		return size;
	}

	/**
	 * @param parentGroupAndChildGroupPair
	 * @author
	 * @Date : 30/08/2022 Description: verify Presence Of Expansion Arrow And its
	 *       Clickable Of Child Saved Search Groups Under Pre-Built Models SG.
	 */

	public void verifyPresenceOfExpansionArrowAndClickableOfChildSavedSGsUnderPreBuiltModelsSG(
			Map<String, String> parentGroupAndChildGroupPair) {
		List<String> parentGroupList = new ArrayList<String>(parentGroupAndChildGroupPair.keySet());
		for (int i = 0; i < parentGroupList.size(); i++) {
			String parentGroupName = parentGroupList.get(i);
			base.ValidateElement_Presence(getExpansionArrow(parentGroupName),
					"'" + parentGroupName + "' saved search group Arrow ");
			getExpansionArrow(parentGroupName).waitAndClick(5);
			base.ValidateElement_Presence(getMainFolders(parentGroupAndChildGroupPair.get(parentGroupName)),
					"'" + parentGroupAndChildGroupPair.get(parentGroupName) + "' saved search group");
			base.stepInfo("Verify that little arrow present at '" + parentGroupName
					+ "' saved search group ls present under the 'Pre-Built Models' Search Group and its clickable");
		}
	}

	/**
	 * @author
	 * @param ListOfSavedSearchGroup
	 * @param parentGroup
	 */
	public void verifyPresenceOfListOfSavedSearchGroups(List<String> ListOfSavedSearchGroup, String parentGroup) {
		for (String savedSearchGroupName : ListOfSavedSearchGroup) {
			if (getMainFolders(savedSearchGroupName).isElementAvailable(5)) {
				getMainFolders(savedSearchGroupName).waitAndClick(5);
				if (!parentGroup.equals(null)) {
					base.passedStep("Verify that '" + savedSearchGroupName
							+ "' Saved Search Group is Present Under the '" + parentGroup + "' Search Group.");
				} else {
					base.passedStep("Verify that '" + savedSearchGroupName + "' Saved Search Group is Present.");
				}
			} else {
				base.failedStep("'" + savedSearchGroupName + "' Saved Search Group is Not Present.");
			}
		}
	}

	/**
	 * @author Vijaya.Rani
	 * @description:saveSearch Shared With PA To DocList
	 * 
	 */
	public void saveSearchSharedWithPAToDocList(final String searchName) {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavesSreachSharedWithPA().Visible();
			}
		}), Input.wait30);
		getSavesSreachSharedWithPA().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSavedSearch_SearchName().SendKeys(searchName);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSavedSearch_ApplyFilterButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);
		getSelectWithName(searchName).waitAndClick(10);
		driver.scrollPageToTop();
		getToDocList().waitAndClick(10);
		try {
			if (base.getYesBtn().isElementAvailable(5)) {
				base.getYesBtn().waitAndClick(20);
			}
		} catch (Exception e) {
			System.out.println("Pop up message does not appear");
			Log.info("Pop up message does not appear");
		}
		base.stepInfo("Saved search is opened in doc list");
	}

	/**
	 * @author Sowndarya.Velraj
	 * @param searchName
	 * @param persistant
	 */
	public void bulkAssignPersistencSS(String searchName, boolean persistant) {
		navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		savedSearch_Searchandclick(searchName);

		base.waitForElement(getSavedSearchToBulkAssign());
		getSavedSearchToBulkAssign().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("performing bulk assign");
		UtilityLog.info("performing bulk assign");

		if (persistant) {
			driver.waitForPageToBeReady();
			if (getPersistantHitCb_Existing().isElementAvailable(3))
				getPersistantHitCb_Existing().waitAndClick(5);
		} else if (getPersistantHitCheckBox().isElementAvailable(3)) {
			getPersistantHitCheckBox().waitAndClick(5);
		}
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Sowndarya.Velraj
	 * @param searchGroup
	 * @param persistant
	 */
	public void bulkAssignPersisSrcGrpSS(String searchGroup, boolean persistant) {
		navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		selectRootGroupTab(searchGroup);

		base.waitForElement(getSavedSearchToBulkAssign());
		getSavedSearchToBulkAssign().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("performing bulk assign");
		UtilityLog.info("performing bulk assign");

		if (persistant) {
			driver.waitForPageToBeReady();
			if (getPersistantHitCb_Existing().isElementAvailable(3))
				getPersistantHitCb_Existing().waitAndClick(5);
		} else if (getPersistantHitCheckBox().isElementAvailable(3)) {
			getPersistantHitCheckBox().waitAndClick(5);
		}
		driver.waitForPageToBeReady();

	}

}
package pageFactory;

import java.awt.AWTException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class DocExplorerPage {

	Driver driver;
	Element element;
	BaseClass bc;
	SoftAssert assertion;
	SessionSearch search;
	DocListPage doclist;

	// Filters
	public Element getDocExp_IngestionNameFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'IngestionName')]");
	}

	public Element getDocExp_CustodianFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'CustodianName')]");
	}

	public Element getDocExp_AllCustodianFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'AllCustodians')]");
	}

	public Element getDocExp_GetDocFIleTypeFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'DocFileType')]");
	}

	public Element getDocExp_MasterDateFiler() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'MasterDate')]");
	}

	public Element getDocExp_EmailAuthNameFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorName')]");
	}

	public Element getDocExp_EmailRecNameFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailRecipientNames')]");
	}

	public Element getDocExp_EmailAuthDomainFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorDomain')]");
	}

	public Element getDocExp_EmailReciepientDomainFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailRecipientDomains')]");
	}

	public Element getDocExp_TagFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Tags')]");
	}

	public Element getDocExp_FolderFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Folders')]");
	}

	public Element getDocExp_AssignmentFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Assignments')]");
	}

	public Element getDocExp_CommentsFilter() {
		return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Comments')]");
	}

	public Element getDocExp_DocumentList_info() {
		return driver.FindElementById("dtDocumentList_info");
	}

	public Element getDocExp_DocID() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[2]/div");
	}

	public Element getDocExp_Docfiletype() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[5]/div");
	}

	public Element getDocExp_DocIDSearchName() {
		return driver.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr[2]/th[2]/input");
	}

	public Element getDocExp_DocFiletypeSearchName() {
		return driver.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr[2]/th[5]/input");
	}

	public Element getDocExp_CusName() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[4]/div");
	}

	public Element getDocExp_CustodianSearchName() {
		return driver.FindElementByXPath(".//*[@class='dataTables_scrollHead']//tr[2]/th[4]/input");
	}

	public Element getDocExp_MasterDate() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[7]/div");
	}

	public Element getDocExp_MasterDateSearchName() {
		return driver.FindElementByXPath(".//*[@class='dataTables_scrollHead']//tr[2]/th[7]/input");
	}

	public Element getDocExp_ActiveFilter() {
		return driver.FindElementByXPath(".//*[@id='activeFilters']/li");
	}

	public Element getDocExp_UpdateFilter() {
		return driver.FindElementByXPath("(.//*[contains(text(),'Update Filter')])[2]");
	}

	public Element getDocExp_SetFromMasterDate() {
		return driver.FindElementByXPath("(//*[contains(@id,'MasterDate-1-DOCEXPLORER')])[1]");
	}

	public Element getDocExpt_SetToMasterDate() {
		return driver.FindElementByXPath("(//*[@id='MasterDate-2-DOCEXPLORER'])[1]");
	}

	public Element getDocExp_SelectAllDocs() {
		return driver.FindElementByXPath("(//*[@id='selectAllDocuments']/following-sibling::i)[1]");
	}

	public Element getDocExp_actionButton() {
		return driver.FindElementById("idDocExplorerActions");
	}

	public Element getDocExp_action_quickbatch() {
		return driver.FindElementById("idQuickAssign");
	}

	public Element getDocViewAction() {
		return driver.FindElementById("idViewInDocView");
	}

	public Element getDocListAction() {
		return driver.FindElementById("idViewInDocList");
	}

	// Added By Gopinath - 09-01-2021
	public Element getAllDocSelectedCheckBox() {
		return driver.FindElementByXPath("//thead/tr[1]/th[1]/label[1]/i[1]");
	}

	public Element getOkButton() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getBulkTagButton() {
		return driver.FindElementById("idBulkTag");
	}

	public Element getTag(String tagName) {
		return driver
				.FindElementByXPath("//a[@data-content = '" + tagName + "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getContinueButton() {
		return driver.FindElementById("btnAdd");
	}

	public Element getFinalizeButton() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	public Element getDocBoard() {
		return driver.FindElementById("divBulkTagJSTree");
	}

	// Added by jayanthi
	public Element getDocExp_BulkAssign() {
		return driver.FindElementByXPath("//li//a[text()='Bulk Assign ']");
	}

	// added by sowndariya
	public Element getExportDataSaveReport() {
		return driver.FindElementByXPath("//i[@id='saveReport']");
	}

	public Element getExportDataCustomRepName() {
		return driver.FindElementById("txtReportname");
	}

	public Element getExportSaveBtn() {
		return driver.FindElementById("saveXML");
	}

	public Element getBulkFolder() {
		return driver.FindElementByXPath(" //a[text()='Bulk Folder']");
	}

	public Element getBulkFolderCheckBox() {
		return driver.FindElementByXPath(
				"//div[@id='divBulkFolderJSTree']//a[text()='analyticsFolderName7838786']/i[contains(@class,'checkbox')]");
	}

	public Element getselectDoc(int rowno) {
		return driver
				.FindElementByXPath("//*[@id='dtDocumentList']//following-sibling::tbody//tr[" + rowno + "]//label");
	}

	public Element getBulkFolderCheckBox(String folderName) {
		return driver.FindElementByXPath(
				"//div[@id='divBulkFolderJSTree']//a[text()='" + folderName + "']/i[contains(@class,'checkbox')]");
	}

	// *** Added by Jagadeesh.Jana _ Starts ***
	public Element get_filterByFolder() {
		return driver.FindElementById("option-NVARCHAR--32720");
	}

	public Element get_filterByFolderInclude() {
		return driver.FindElementByXPath("(//div[@id='rbIncExclude']/label/i)[1]");
	}

	public Element get_ClickToMakeYourSelection() {
		return driver.FindElementByXPath("//input[@placeholder='Click to make your selection']");
	}

	public Element get_firstRecordFromList() {
		return driver.FindElementByXPath("//ul[@id='select2-Folders-results']/li[1]");
	}

	public Element addToFilterButton() {
		return driver.FindElementByXPath("(//a[@id='action-NVARCHAR--32720'])[1]");
	}

	public Element doclistTableMultiSelect(int row) {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']/tbody/tr[" + row + "]/td[1]");
	}

	public Element actionDropdown() {
		return driver.FindElementByXPath("//button[@id='idDocExplorerActions']");
	}

	public Element exportDataFromActionDropdown() {
		return driver.FindElementByXPath("//a[@id='idExportData']");
	}

	public Element exportWindow_AllCustodiansCheckbox() {
		return driver.FindElementByXPath("(//*[text()='AllCustodians'])[2]");
	}

	public Element exportWindow_AddToSelectedButton() {
		return driver.FindElementByXPath("//a[@id='addFormObjects-coreList']");
	}

	public Element exportWindow_RunExport() {
		return driver.FindElementByXPath("//a[@id='btnRunReport']");
	}

	public Element exportWindow_closeButton() {
		return driver.FindElementByXPath("(//button[@class='ui-dialog-titlebar-close'])[2]");
	}

	public Element exportWindow_PreviewDocumentCloseButton() {
		return driver.FindElementByXPath("(//button[@class='ui-dialog-titlebar-close'])[1]");
	}

	public Element doclistTableMulti_EyeIconSelect(int row1) {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']/tbody/tr[" + row1 + "]/td[10]");
	}
	// *** Added by Jagadeesh.Jana _ Ends ***

	// Added by Gopinath - 08/10/2021
	public Element getDocumentsCheckBoxbyRowNum(int rowNumber) {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//tr[" + rowNumber + "]//td[1]//i");
	}

	public Element getExcludeRadioBtn() {
		return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[2])[1]");
	}

	public Element getSearchTextArea() {
		return driver.FindElementByXPath("//*[@type='search']");
	}

	public Element getAddToFilter() {
		return driver.FindElementByXPath("(//*[contains(text(),' Add to Filter')])[1]");
	}

	public Element getDocumentsIdbyRowNum(int rowNumber) {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//tr[" + rowNumber + "]//td[2]//div");
	}

	public ElementCollection getTotalRowsInTable() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentList']//tbody//tr");
	}

	public Element getApplyFilter() {
		return driver.FindElementById("btnAppyFilter");
	}

	public Element getAllDocumentsCount() {
		return driver.FindElementByXPath("//div[@id='divNodeTree']/ul[1]/li[1]/a[1]");
	}

	public Element getPresentDocCount() {
		return driver.FindElementById("dtDocumentList_info");
	}

	public Element getIncludeRadioBtn() {
		return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[1])[1]");
	}

	public Element getActiveFilter() {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li");
	}

	public Element getUpdateFilter() {
		return driver.FindElementByXPath("(//a[text()='Update Filter'])[1]");
	}

	public ElementCollection getDocumentsName() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentList']//tbody//tr//td[2]/div");
	}

	public Element getDocumentId(int rowNo) {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']/tbody/tr[" + rowNo + "]//td[2]//div");
	}

	// Added by gopinath-12/05/2021
	public Element getEmailSubjectTextField() {
		return driver.FindElementByXPath(
				"//div[@class='dataTables_scrollHeadInner']//thead//tr[2]/th[@data-searchname='DocFileName']//input");
	}

	public Element getMasterDatetextField() {
		return driver.FindElementByXPath(
				"//div[@class='dataTables_scrollHeadInner']//thead//tr//th[@data-searchname='MasterDate']//input");
	}

	public Element getDocListHeader() {
		return driver.FindElementByXPath("//h1[@class='page-title']");
	}

	// Added by Gopinath - 10/02/2022
	public Element getfolderFromTreeByNumber(String folderNumber) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a[@id='-1_anchor']/following-sibling::ul[@class='jstree-children']/li["
						+ folderNumber + "]/a");
	}

	// Added by Brundha
	public Element getView() {
		return driver.FindElementByXPath("//a[@class='submenu-a']");
	}

	public Element getDocListCustodianName() {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']/tbody/tr[1]/td[4]/div");
	}

	public ElementCollection getDocListInPage() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentList']/tbody/tr");
	}

	public ElementCollection getDocLictPaginationCount() {
		return driver.FindElementsByXPath("//ul[@class='pagination pagination-sm']/li/a");
	}

	public Element getDocLictPaginationNextButton() {
		return driver.FindElementByXPath("//ul[@class='pagination pagination-sm']/li/a[text()='Next']");
	}

	// Added by Gopinath - 11/02/2022
	public Element getEmailRecipientColumnTextField() {
		return driver.FindElementByXPath(
				"//div[@class='dataTables_scrollHeadInner']/descendant::input[@id='EMAILRECIPIENTS']");
	}

	public Element getDocListInfo() {
		return driver.FindElementByXPath("//div[@id='dtDocumentList_info' and text()='Showing 0 to 0 of 0 entries']");
	}

	public Element getQuaryReturnedNoDataMessage() {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentList']/descendant::td[text()='Your query returned no data']");
	}

	// Added by Gopinath - 14/02/2022
	public ElementCollection getDocExplorerSubFolders(String folderNumber) {
		return driver.FindElementsByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a[@id='-1_anchor']/following-sibling::ul/li["
						+ folderNumber + "]/ul/li/a");
	}

	public Element getFolderExpandButton(String folderNumber) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a[@id='-1_anchor']/following-sibling::ul[@class='jstree-children']/li["
						+ folderNumber + "]/i");
	}

	public Element getDocIdColumnTextField() {
		return driver.FindElementByXPath("//div[@class='dataTables_scrollHeadInner']/descendant::input[@id='DOCID']");
	}

	// Added by Gopinath - 17/02/2022
	public Element getDocExplorerPageHeader() {
		return driver.FindElementByXPath("//div[@id='leftPane']/descendant::h1[contains(text(),'Doc Explorer')]");
	}

	// Added by Gopinath - 21/02/2022
	public Element getFolderToolTip() {
		return driver.FindElementByXPath("//div[@class='popover fade top in']/descendant::h3/following-sibling::div");
	}

	public Element getfolderFromTreeByName(String folderName) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-children']/li/i/following-sibling::a[text()='" + folderName + "']");
	}

	public ElementCollection getFolderOfMoreCharacters() {
		return driver.FindElementsByXPath("//div[@id='divNodeTree']//ul//ul//li//a");
	}

	// Added by Gopinath - 02/03/2022
	public Element getDocExplorerSubFolder() {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a[@id='-1_anchor']/following-sibling::ul/li/ul/li");
	}

	public Element getDocExpFolderExpandbutton(String folderName) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a/following-sibling::ul/li/a[text()='"
						+ folderName + "']/../i");
	}

	public Element getDocExpSubFolderName(String folderName, int subFolderNumber) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a/following-sibling::ul/li/a[text()='"
						+ folderName + "']/../ul/descendant::li[" + subFolderNumber + "]/i/following-sibling::a");
	}

	public Element getDoxExpSubFoldarExpandbutton(String folderName, int subFolderNumber) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a/following-sibling::ul/li/a[text()='"
						+ folderName + "']/../ul/descendant::li[" + subFolderNumber + "]/i");
	}

	// Added By Vijaya.Rani
	public Element getClickDocExplorerID(int row) {
		return driver.FindElementByXPath("//*[@id='dtDocumentList']/tbody/tr[" + row + "]/td[1]/label/i");
	}

	// Added by Gopinath - 16/03/2022
	public Element getDocExplorerTabAfterDashBoard() {
		return driver.FindElementByXPath(
				"//nav[@id='LeftMenu']/ul/li/a[@title='Dashboard']/../following-sibling::li/a[@title='Doc Explorer']");
	}

	public Element getDocExplorerAboveDatasets() {
		return driver.FindElementByXPath(
				"//nav[@id='LeftMenu']/ul/li/a[@title='Datasets']/../preceding-sibling::li/a[@title='Doc Explorer']");
	}

	public Element getDocExplorerTab() {
		return driver.FindElementByXPath("//nav[@id='LeftMenu']/ul/li/a[@title='Doc Explorer']");
	}

	// Added by Gopinath - 21/03/2022
	public ElementCollection getDocExplorerFolders() {
		return driver.FindElementsByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a[@id='-1_anchor']/following-sibling::ul[@class='jstree-children']/li/a");

	}

	// Added by Gopinath - 08/04/2022
	public Element getViewOn() {
		return driver.FindElementByXPath("//li[@class='dropdown-submenu']//a[text()='View']");
	}

	public Element getViewInDocViewLat() {
		return driver.FindElementByXPath("//a[text()='View in DocView']");
	}

	public Element getViewInDocListLat() {
		return driver.FindElementByXPath("//a[text()='View in DocList']");
	}

	public Element getPageNextButtonDisabled() {
		return driver.FindElementByXPath("//li[@class='paginate_button next disabled']");
	}

	public Element getAllFoldersExpandButton() {
		return driver.FindElementByXPath("//a[@id='-1_anchor']/preceding-sibling::i");
	}

	public Element getDocExpSubfolderExpandButtonLast(String folderName) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']/li/a/following-sibling::ul/li/a[text()='"
						+ folderName + "']/../ul/descendant::li[@class='jstree-node  jstree-leaf jstree-last']");
	}

	public Element getExportSchedulerButton() {
		return driver.FindElementById("btnScheduler");
	}

	public Element getExportShareEmail() {
		return driver.FindElementByXPath("//textarea[@id='txtEmail']");
	}

	public Element getExportEmailErrorMsg() {
		return driver.FindElementById("ErrMsgdiv");
	}

	public Element getExportSumbitBtn() {
		return driver.FindElementById("btnScheduleSubmit");
	}

	public Element getBulkReleaseBtn() {
		return driver.FindElementById("idBulkRelease");
	}

	public Element getBulkRelDefaultSecurityGroup_CheckBox(String SG) {
		return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[text()='" + SG + "']/../div[1]/label/i");
	}

	public Element getBulkRelease_ButtonRelease() {
		return driver.FindElementById("btnRelease");
	}

	public Element getBulkRelease_ButtonUnRelease() {
		return driver.FindElementByXPath("//button[@id='btnUnrelease']");
	}

	public Element getDocExplorer_NewFolder() {
		return driver.FindElementById("tabNew");
	}

	public Element getBulkFolder_SelectAllFolder() {
		return driver.FindElementByXPath("//div[@id='FolderGroupList']//a[text()='All Folders']");
	}

	public Element getDocExplorer_NewFolderName() {
		return driver.FindElementById("txtFolderName");
	}

	// added by arun
	public Element getDocExpField(String field) {
		return driver.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr//th//input[@id='" + field + "']");
	}

	public Element getDocExpFieldValues(String value) {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//td//div[text()='" + value + "']");

	}

	public Element getDocExplorer_UnTagDocuments() {
		return driver.FindElementByXPath("//*[@id=\"divBulkAction\"]/div[1]/div/fieldset/div[1]/div/label[2]/i");
	}

	public Element getEmailRecipient(String emailRecipient) {
		return driver
				.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr//th//input[@id='" + emailRecipient + "']");
	}

	public Element getEmailRecipientValues() {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//td[9]//div");

	}

	public Element getDocExp_EmailSubFileSearchName(String emailSubFile) {
		return driver
				.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr//th//input[@id='" + emailSubFile + "']");
	}

	public Element getEmailSubFileValues() {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//td[6]//div");

	}

	public Element getLastPage() {
		return driver.FindElementByXPath("//a[text()='Next']/..//preceding-sibling::li[1]//a");

	}

	public Element getFirstPage() {
		return driver.FindElementByXPath("//a[text()='Previous']/..//following-sibling::li//a");

	}

	public Element getNavigationPageNumber(String PageNo) {
		return driver.FindElementByXPath("//li[contains(@class,'paginate_button')]//a[text()='" + PageNo + "']");

	}

	public ElementCollection getTableHeader() {
		return driver.FindElementsByXPath("//table//thead//th");

	}

	public ElementCollection getRowData(int FieldValue) {
		return driver.FindElementsByXPath("//table[@id='dtDocumentList']//tbody//tr//td[" + FieldValue + "]");

	}

	public Element getNavigationBtn(String Btn) {
		return driver.FindElementByXPath("//a[text()='" + Btn + "']/..");

	}

	public Element getSelectAllDocuments() {
		return driver.FindElementByXPath("//*[@id='divNodeTree']//a[contains(@data-content,'All Documents')]");

	}

	public Element getYesRadioBtn() {
		return driver.FindElementByXPath("//input[@id='Yes']");
	}

	public Element getDocExplorerUnFolder() {
		return driver.FindElementByXPath("//*[@id='toUnfolder']/following-sibling::i");

	}

	public ElementCollection getNumberOfDocsCount() {
		return driver.FindElementsByXPath("//table[@id='SearchDataTable']//tbody//tr");
	}

	public Element getListViewHeader(int i) {
		return driver.FindElementByXPath("//table//thead//tr[1]//th[" + i + "]");
	}

	public Element getDocExp_CustodianNameSearchName(String custodianName) {
		return driver
				.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr//th//input[@id='" + custodianName + "']");
	}

	public Element getCustodianNameValues() {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//td[4]//div");
	}

	public Element getDocExp_EmailAuthorSearchName(String emailAuthor) {
		return driver
				.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr//th//input[@id='" + emailAuthor + "']");
	}
	
	public Element getEmailAuthorValues() {
		return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//td[8]//div");

	}
	
	public ElementCollection getMasterDateAsc() {
		return driver.FindElementsByXPath("//th[text()='MASTERDATE']/following::tr//td[7]/div");
	}
	
	public Element getDocExMasterDate() {
		return driver.FindElementByXPath("//div[@class='dataTables_scrollHeadInner']/table/thead/tr[1]/th[7]");
	}
	
	public DocExplorerPage(Driver driver) {

		this.driver = driver;
		bc = new BaseClass(driver);
//		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.waitForPageToBeReady();
		assertion = new SoftAssert();
		doclist = new DocListPage(driver);
		search = new SessionSearch(driver);

	}

	public void HeaderFilter(String Filtertype) throws InterruptedException, ParseException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		switch (Filtertype) {

		case "DocID":
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocExp_DocID().Visible();
				}
			}), Input.wait30);
			String DocId = getDocExp_DocID().getText();
			System.out.println(DocId);
			UtilityLog.info(DocId);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocExp_DocIDSearchName().Visible();
				}
			}), Input.wait30);
			getDocExp_DocIDSearchName().SendKeys(DocId);
			doclist.getApplyFilter().waitAndClick(10);
			Thread.sleep(2000);
			validateCount("Showing 1 to 1 of 1 entries");
			getDocExp_DocIDSearchName().Clear();
			break;

		case "Custodian":
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocExp_CusName().Visible();
				}
			}), Input.wait30);
			String Cusname = getDocExp_CusName().getText();
			System.out.println(Cusname);
			UtilityLog.info(Cusname);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocExp_CustodianSearchName().Visible();
				}
			}), Input.wait30);
			getDocExp_CustodianSearchName().SendKeys(Cusname);
			doclist.getApplyFilter().waitAndClick(10);
			Thread.sleep(2000);
			validateCount("Showing 1 to 50 of 1,134 entries");
			getDocExp_CustodianSearchName().Clear();
			break;

		case "MasterDate":
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocExp_MasterDate().Visible();
				}
			}), Input.wait30);
			String date = getDocExp_MasterDate().getText();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date1 = dateformat.parse(date);
			String expdate = dateformat.format(date1);
			System.out.println(expdate);
			UtilityLog.info(expdate);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocExp_MasterDateSearchName().Visible();
				}
			}), Input.wait30);
			getDocExp_MasterDateSearchName().SendKeys(expdate);
			doclist.getApplyFilter().waitAndClick(10);
			Thread.sleep(2000);
			validateCount("Showing 1 to 50 of 95 entries");
			break;

		}

	}

	public void TagWithMasterDateFilter(String dataforfilter) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_TagFilter().Visible();
			}
		}), Input.wait60);
		getDocExp_TagFilter().waitAndClick(10);

		doclist.exclude(dataforfilter);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_MasterDateFiler().Visible();
			}
		}), Input.wait30);
		getDocExp_MasterDateFiler().waitAndClick(10);

		doclist.dateFilter("before", "2010/01/01", null);
		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(5000);

		validateCount("Showing 1 to 50 of 1,145 entries");
	}

	public void MasterDateFilter() throws InterruptedException {

		dateFilterexplorer("between", "2001/01/01", "2020/01/01");
		Thread.sleep(2000);

		doclist.getApplyFilter().waitAndClick(10);

		validateCount("Showing 1 to 50 of 302 entries");
	}

	public void TagFilter(String data1, String data2) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_TagFilter().Visible();
			}
		}), Input.wait60);
		getDocExp_TagFilter().waitAndClick(10);

		doclist.include(data1);
		Thread.sleep(2000);

		doclist.getApplyFilter().waitAndClick(10);

		validateCount("Showing 1 to 48 of 48 entries");

		UpdateFilter(data2);

		doclist.getApplyFilter().waitAndClick(10);

		validateCount("Showing 1 to 50 of 1,202 entries");
	}

	public void CustodianFilter(String data1, String data2, String functionality) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_CustodianFilter().Visible();
			}
		}), Input.wait60);
		getDocExp_CustodianFilter().waitAndClick(10);

		if (functionality.equalsIgnoreCase("include")) {
			doclist.include(data1);
			Thread.sleep(2000);

			doclist.getApplyFilter().waitAndClick(10);
			Thread.sleep(5000);

			validateCount("Showing 1 to 50 of 1,134 entries");

			UpdateFilter(data2);
			Thread.sleep(2000);

			doclist.getApplyFilter().waitAndClick(10);
			Thread.sleep(5000);

			validateCount("Showing 1 to 50 of 1,136 entries");
		}
		if (functionality.equalsIgnoreCase("exclude")) {
			doclist.exclude(data1);
			Thread.sleep(2000);

			doclist.getApplyFilter().waitAndClick(10);
			Thread.sleep(2000);

			validateCount("Showing 1 to 50 of 68 entries");

			UpdateFilter(data2);
			Thread.sleep(2000);

			doclist.getApplyFilter().waitAndClick(10);
			Thread.sleep(2000);

			validateCount("Showing 1 to 50 of 66 entries");
		}

	}

	public void DocFileTypeFilter(String data1, String data2) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_GetDocFIleTypeFilter().Visible();
			}
		}), Input.wait60);
		getDocExp_GetDocFIleTypeFilter().waitAndClick(10);

		doclist.include(data1);
		Thread.sleep(2000);

		doclist.getApplyFilter().waitAndClick(10);

		validateCount("Showing 1 to 50 of 813 entries");

		UpdateFilter(data2);

		doclist.getApplyFilter().waitAndClick(10);

		validateCount("Showing 1 to 50 of 817 entries");
	}

	public void EmailRecipientNameFilter(String data1, String data2) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_EmailRecNameFilter().Visible();
			}
		}), Input.wait60);
		getDocExp_EmailRecNameFilter().waitAndClick(10);

		doclist.include(data1);
		Thread.sleep(2000);

		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(5000);

		validateCount("Showing 1 to 3 of 3 entries");

		UpdateFilter(data2);

		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(5000);

		validateCount("Showing 1 to 13 of 13 entries");
	}

	public void EmailAuthorNameFilter(String data1, String data2) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_EmailAuthNameFilter().Visible();
			}
		}), Input.wait60);
		getDocExp_EmailAuthNameFilter().waitAndClick(10);

		doclist.exclude(data1);
		Thread.sleep(2000);

		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(5000);

		validateCount("Showing 1 to 50 of 2,537 entries");

		UpdateFilter(data2);

		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(5000);

		validateCount("Showing 1 to 50 of 2,535 entries");
	}

	public void EmailAuthorDomainFilter(String data1, String data2) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_EmailAuthDomainFilter().Visible();
			}
		}), Input.wait60);
		getDocExp_EmailAuthDomainFilter().waitAndClick(10);

		doclist.include(data1);
		Thread.sleep(2000);

		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(5000);

		validateCount("Showing 1 to 6 of 6 entries");

		UpdateFilter(data2);

		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(5000);

		validateCount("Showing 1 to 10 of 10 entries");
	}

	public void AssignmentFilter(String assgnm1, String assgnm2, String functionality) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_AssignmentFilter().Visible();
			}
		}), Input.wait30);
		getDocExp_AssignmentFilter().waitAndClick(10);

		if (functionality.equalsIgnoreCase("include")) {
			doclist.include(assgnm1);
			Thread.sleep(2000);

			doclist.getApplyFilter().waitAndClick(10);

			validateCount("Showing 1 to 10 of 10 entries");

			UpdateFilter(assgnm2);

			doclist.getApplyFilter().waitAndClick(10);

			validateCount("Showing 1 to 48 of 48 entries");
		}
		if (functionality.equalsIgnoreCase("exclude")) {
			doclist.exclude(assgnm1);
			Thread.sleep(2000);

			doclist.getApplyFilter().waitAndClick(10);

			validateCount("Showing 1 to 50 of 1,192 entries");

			UpdateFilter(assgnm2);

			doclist.getApplyFilter().waitAndClick(10);

			validateCount("Showing 1 to 50 of 1,154 entries");
		}

	}

	public void validateCount(String counts) {
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return !getDocExp_DocumentList_info().getText().isEmpty();
			}
		}), Input.wait60);
		// to make sure that not reading previous results and wait for page mask to
		// complete,
		// simply try to click on result text!
		driver.scrollingToBottomofAPage();
		getDocExp_DocumentList_info().waitAndClick(30); // works only when results updates!!

		// Now validate results
		Assert.assertTrue(getDocExp_DocumentList_info().getText().toString().equalsIgnoreCase(counts));

	}

	public void UpdateFilter(String data) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_ActiveFilter().Visible();
			}
		}), Input.wait30);
		getDocExp_ActiveFilter().Click();
		doclist.getSearchTextArea().SendKeys(data);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doclist.getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
		getDocExp_UpdateFilter().waitAndClick(10);
	}

	public void dateFilterexplorer(String option, String fromDate, String toDate) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getMasterDateFilter().Visible();
			}
		}), Input.wait30);

		doclist.getMasterDateFilter().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getMasterDateRange().Visible();
			}
		}), Input.wait30);
		doclist.getMasterDateRange().selectFromDropdown().selectByValue(option);

		getDocExp_SetFromMasterDate().SendKeys(fromDate + Keys.TAB);

		if (option.equalsIgnoreCase("between"))
			getDocExpt_SetToMasterDate().SendKeys(toDate + Keys.TAB);

		doclist.getAddToFilter().waitAndClick(10);
		doclist.getApplyFilter().waitAndClick(10);
	}

	public void DocExplorertoquickBatch() {
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		doclist.getPopUpOkBtn().Click();

		getDocExp_actionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_action_quickbatch().Visible();
			}
		}), Input.wait30);
		getDocExp_action_quickbatch().Click();
	}

	public void DocIDandDocFileTypeFilters() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_DocID().Visible();
			}
		}), Input.wait30);
		String DocId = getDocExp_DocID().getText();
		System.out.println(DocId);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_DocIDSearchName().Visible();
			}
		}), Input.wait30);
		getDocExp_DocIDSearchName().SendKeys(DocId);

		String Docfiletype = getDocExp_Docfiletype().getText();
		System.out.println(Docfiletype);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_DocFiletypeSearchName().Visible();
			}
		}), Input.wait30);
		getDocExp_DocFiletypeSearchName().SendKeys(Docfiletype);

		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(2000);
		validateCount("Showing 1 to 1 of 1 entries");
	}

	public void CusNameandDocFileTypeFilters() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_CusName().Visible();
			}
		}), Input.wait30);
		String Cusname = getDocExp_CusName().getText();
		System.out.println(Cusname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_CustodianSearchName().Visible();
			}
		}), Input.wait30);
		getDocExp_CustodianSearchName().SendKeys(Cusname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_DocFiletypeSearchName().Visible();
			}
		}), Input.wait30);
		getDocExp_DocFiletypeSearchName().SendKeys("Outlook");

		doclist.getApplyFilter().waitAndClick(10);
		Thread.sleep(2000);
		validateCount("Showing 1 to 50 of 822 entries");
	}

	public void CommentFilter() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_CommentsFilter().Visible();
			}
		}), Input.wait60);
		getDocExp_CommentsFilter().waitAndClick(10);

		doclist.include("Document_Comments");
		Thread.sleep(2000);

		doclist.getApplyFilter().waitAndClick(10);

		validateCount("Showing 1 to 2 of 2 entries");
	}

	public void DocExplorertodoclist() {
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		doclist.getPopUpOkBtn().Click();

		getDocExp_actionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocListAction().Visible();
			}
		}), Input.wait60);

		getDocListAction().waitAndClick(20);
		try {
			bc.getYesBtn().waitAndClick(10);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Navigated to doclist, to view docslist");
		UtilityLog.info("Navigated to doclist, to view docslist");

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting documents and bulk tag them..
	 * @param tagName : tagName is a string value that name of tag need to select.
	 */
	public void selectDocAndBulkTag(String tagName) {
		try {
			driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
			for (int i = 0; i < 20; i++) {
				try {
					getAllDocSelectedCheckBox().Click();
					break;
				} catch (Exception e) {
					bc.waitForElement(getAllDocSelectedCheckBox());
					bc.waitTillElemetToBeClickable(getAllDocSelectedCheckBox());
				}
			}
			if (getOkButton().isDisplayed()) {
				bc.waitTillElemetToBeClickable(getOkButton());
				getOkButton().Click();
			} else {
				bc.stepInfo("Ok button is not appered");
			}
			for (int i = 0; i < 10; i++) {
				try {
					bc.waitTime(2);
					bc.waitForElement(getDocExp_actionButton());
					bc.waitTillElemetToBeClickable(getDocExp_actionButton());
					getDocExp_actionButton().Click();
					break;
				} catch (Exception e) {
					bc.waitTime(2);
				}
			}
			bc.waitTillElemetToBeClickable(getBulkTagButton());
			getBulkTagButton().Click();
			driver.scrollingToBottomofAPage();
			bc.waitForElement(getDocBoard());
			getDocBoard().Click();
			bc.waitTime(2);
			getTag(tagName).Click();
			for (int i = 0; i < 20; i++) {
				try {
					bc.waitTime(4);
					bc.waitTillElemetToBeClickable(getContinueButton());
					getContinueButton().Click();
					break;
				} catch (Exception e) {
					bc.waitTillElemetToBeClickable(getContinueButton());
				}
			}
			bc.waitForElement(getFinalizeButton());
			bc.waitTillElemetToBeClickable(getFinalizeButton());
			getFinalizeButton().Click();
			try {
				bc.waitForElement(getOkButton());
				bc.waitTillElemetToBeClickable(getOkButton());
				getOkButton().Click();
			} catch (Exception e) {
				bc.stepInfo("Ok button is not appered after bulk tag operation");
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
			bc.failedStep("Exception occcured while selecting documents or bulk tag" + e.getMessage());

		}
	}

	/**
	 * @Author:Indium-sowndarya.Velraj
	 * 
	 */
	public void documentSelectionIteration() throws InterruptedException {
		for (int D = 1; D < 4; D++) {
			driver.waitForPageToBeReady();
			bc.waitForElement(getselectDoc(D));
			getselectDoc(D).waitAndClick(10);
		}
	}

	public void bulkFolderExisting(String folderName) throws InterruptedException {
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getDocExp_actionButton().Visible();
			}

		}), Input.wait60);

		getDocExp_actionButton().Click();

		Thread.sleep(2000);
		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getBulkFolder().Visible();
			}

		}), Input.wait60);

		getBulkFolder().Click();

		driver.Manage().window().fullscreen();
		System.out.println("Popup is displayed");

		bc.hitTabKey(3);
		bc.hitEnterKey(2);
		try {

			Thread.sleep(5000);

			driver.WaitUntil((new Callable<Boolean>() {

				public Boolean call() {

					return getBulkFolderCheckBox(folderName).Visible();
				}

			}), Input.wait60);

			getBulkFolderCheckBox(folderName).Click();
			getBulkFolderCheckBox(folderName).ScrollTo();

			System.out.println("Folder is selected");

		} catch (Exception e) {
			System.out.println("Folder is Searching");
		}
		Thread.sleep(12000);

		bc.waitForElement(getContinueButton());
		getContinueButton().Click();
		System.out.println("Clicked continue");

		driver.waitForPageToBeReady();

		bc.waitForElement(getFinalizeButton());

		getFinalizeButton().Click();
		driver.Manage().window().maximize();
		bc.VerifySuccessMessage("Records saved successfully");
	}

	/*
	 * Jagadeesh.Jana Test Case Id: RPMXCON-51992
	 */
	public void exportData() throws Exception {

		bc = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		bc.waitForElement(get_filterByFolder());
		get_filterByFolder().waitAndClick(10);

		bc.waitForElement(get_filterByFolderInclude());
		get_filterByFolderInclude().waitAndClick(10);

		bc.waitForElement(get_ClickToMakeYourSelection());
		get_ClickToMakeYourSelection().waitAndClick(10);

		bc.waitForElement(get_firstRecordFromList());
		get_firstRecordFromList().waitAndClick(10);

		bc.waitForElement(addToFilterButton());
		addToFilterButton().waitAndClick(10);
		for (int row = 1; row <= 10; row++) {
			doclistTableMultiSelect(row).Click();
			Thread.sleep(5000); // required here as per testCase requirements
		}
		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(10);

		bc.waitForElement(exportDataFromActionDropdown());
		exportDataFromActionDropdown().waitAndClick(10);

		bc.waitForElement(exportWindow_AllCustodiansCheckbox());
		exportWindow_AllCustodiansCheckbox().waitAndClick(10);

		driver.scrollingToBottomofAPage();
		bc.waitForElement(exportWindow_AddToSelectedButton());
		exportWindow_AddToSelectedButton().waitAndClick(10);

		driver.scrollPageToTop();
		bc.waitForElement(exportWindow_RunExport());
		exportWindow_RunExport().waitAndClick(10);

		bc.waitForElement(exportWindow_closeButton());
		exportWindow_closeButton().waitAndClick(10);

		for (int row1 = 1; row1 <= 10; row1++) {
			doclistTableMulti_EyeIconSelect(row1).Click();
			Thread.sleep(5000); // required here as per testCase requirements
			bc.waitForElement(exportWindow_PreviewDocumentCloseButton());
			exportWindow_PreviewDocumentCloseButton().waitAndClick(10);
			Thread.sleep(5000); // required here as per testCase requirements
		}

		System.out.println("DocView and Doc Explorer_Performance_Navigate through documents one by one- Successfully");
		bc.passedStep("DocView and Doc Explorer_Performance_Navigate through documents one by one- Successfully");
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting some documents and bulk tag them..
	 * @param tagName           : tagName is a string value that name of tag need to
	 *                          select.
	 * @param numberOfDocuments : numberOfDocuments is integer value that number of
	 *                          documents need to select.
	 * @return docids : Selected documents ID's for bulk tag.
	 */
	public ArrayList<String> selectDocumentsAndBulkTag(int numberOfDocuments, String tagName) {
		ArrayList<String> docids = new ArrayList<String>();
		try {
			driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
			driver.getWebDriver().navigate().refresh();
			driver.waitForPageToBeReady();
			for (int i = 0; i < 5; i++) {
				try {
					bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
					bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
					break;
				} catch (Exception e) {
					bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
					bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
				}
			}
			for (int row = 0; row < numberOfDocuments; row++) {
				bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
				bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
				getDocumentsCheckBoxbyRowNum(row + 1).Click();
				docids.add(getDocumentsIdbyRowNum(row + 1).GetAttribute("data-content").trim());
			}
			for (int i = 0; i < 7; i++) {
				try {
					bc.waitForElement(getDocExp_actionButton());
					bc.waitTillElemetToBeClickable(getDocExp_actionButton());
					getDocExp_actionButton().Click();
					break;
				} catch (Exception e) {
					Thread.sleep(Input.wait30 / 10);
				}
			}
			bc.waitTillElemetToBeClickable(getBulkTagButton());
			getBulkTagButton().Click();
			driver.scrollingToBottomofAPage();
			bc.waitForElement(getDocBoard());
			getDocBoard().Click();
			bc.waitForElement(getTag(tagName));
			bc.waitTillElemetToBeClickable(getTag(tagName));
			getTag(tagName).Click();
			for (int i = 0; i < 5; i++) {
				try {
					Thread.sleep(Input.wait30 / 10);
					getContinueButton().Click();
					break;
				} catch (Exception e) {
					bc.waitTillElemetToBeClickable(getContinueButton());
				}
			}
			bc.waitForElement(getFinalizeButton());
			bc.waitTillElemetToBeClickable(getFinalizeButton());
			getFinalizeButton().Click();
			try {
				bc.waitForElement(getOkButton());
				bc.waitTillElemetToBeClickable(getOkButton());
				getOkButton().Click();
				bc.waitForElement(bc.getSuccessMsg());
				bc.getSuccessMsg().waitAndFind(10);
				Assert.assertEquals("Success message is not displayed", true,
						bc.getSuccessMsg().getWebElement().isDisplayed());
				if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
					bc.passedStep("Success message is displayed successfully");
				}
			} catch (Exception e) {
				bc.stepInfo("Ok button is not appered after bulk tag operation");
			}

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting documents or bulk tag" + e.getMessage());

		}
		return docids;
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude filter for tag..
	 * @param tagName : tagName is a string value that name of tag need to select.
	 */
	public void performExculdeTagFilter(String tagName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_TagFilter());
			bc.waitTillElemetToBeClickable(getDocExp_TagFilter());
			getDocExp_TagFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(tagName);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for tag" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for verifying include tag filter works properly or not..
	 * @param docIds : docIds is array list of strings that documents of expected.
	 */
	public void verifyIncludeTagFilterWorksProperly(ArrayList<String> docIds) {
		try {
			driver.waitForPageToBeReady();
			int totalRows = getTotalRowsInTable().FindWebElements().size();
			for (int row = 0; row < totalRows; row++) {
				driver.waitForPageToBeReady();
				bc.waitForElement(getDocumentsIdbyRowNum(row + 1));
				String docId = getDocumentsIdbyRowNum(row + 1).GetAttribute("data-content").trim();
				if (!docIds.contains(docId)) {
					bc.failedStep("expected document with ID :: " + docId + " is not appered properly");
				} else if (docIds.contains(docId)) {
					bc.passedStep("Document of Id :: " + docId
							+ " is displayed on doc explorer table by applying tag filter");
				}
			}
			bc.passedStep("Include functionality is worked properly as expected");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while verifying include tag filter works properly or not" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for getting total number of documents..
	 * @return docCount : docCount is a integer value that number of documents.
	 */
	public int totalDocumentsCount() {
		String docCount = null;
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getAllDocumentsCount());
			bc.waitTillElemetToBeClickable(getAllDocumentsCount());
			docCount = getAllDocumentsCount().GetAttribute("data-content").trim().replaceAll("[^0-9]", "");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while getting total number of documents" + e.getMessage());
		}
		return Integer.parseInt(docCount);
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to verify documents after applying exclude functionality
	 *               by tag..
	 * @param totalDocCount    : docCount is a integer value that total number of
	 *                         documents.
	 * @param selectedDocCount : selectedDocCount is a integer that count of
	 *                         selected documents count for bulk tag
	 */
	public void verifyExcludeFunctionlityForTag(int totalDocCount, int selectedDocCount) {

		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getPresentDocCount());
			bc.waitTillElemetToBeClickable(getPresentDocCount());
			String[] presentDocCount = getPresentDocCount().getText().trim().split("of", 2);
			String docCount = presentDocCount[1].trim().replaceAll("[^0-9]", "");
			int expectedCount = totalDocCount - selectedDocCount;
			if (expectedCount == Integer.parseInt(docCount)) {
				bc.passedStep("Exclude filter functionality by tag is working as expected");
			} else {
				bc.failedStep("Exclude filter functionality by tag is not working as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while verifying documents after applying exclude functionality by tag"
					+ e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing include filter for tag..
	 * @param tagName : tagName is a string value that name of tag need to select.
	 */
	public void performInculdeTagFilter(String tagName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_TagFilter());
			bc.waitTillElemetToBeClickable(getDocExp_TagFilter());
			getDocExp_TagFilter().Click();
			bc.waitForElement(getIncludeRadioBtn());
			bc.waitTillElemetToBeClickable(getIncludeRadioBtn());
			getIncludeRadioBtn().Click();
			getSearchTextArea().SendKeys(tagName);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing include filter for tag" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting some documents and bulk folder them..
	 * @param folderName        : folderName is a string value that name of folder
	 *                          need to select.
	 * @param numberOfDocuments : numberOfDocuments is integer value that number of
	 *                          documents need to select.
	 * @return docids : Selected documents ID's for bulk folder.
	 */
	public ArrayList<String> selectDocumentsAndBulkFolder(int numberOfDocuments, String folderName) {
		ArrayList<String> docids = new ArrayList<String>();
		try {
			driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
			driver.getWebDriver().navigate().refresh();
			driver.waitForPageToBeReady();
			for (int i = 0; i < 5; i++) {
				try {
					bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
					bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
					break;
				} catch (Exception e) {
					bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
					bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
				}
			}
			for (int row = 0; row < numberOfDocuments; row++) {
				bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
				bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
				getDocumentsCheckBoxbyRowNum(row + 1).Click();
				docids.add(getDocumentsIdbyRowNum(row + 1).GetAttribute("data-content").trim());
			}
			bulkFolderExisting(folderName);
			bc.waitForElement(bc.getSuccessMsg());
			bc.getSuccessMsg().waitAndFind(10);
			Assert.assertEquals("Success message is not displayed", true,
					bc.getSuccessMsg().getWebElement().isDisplayed());
			if (bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting documents or bulk folder" + e.getMessage());

		}
		return docids;
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude filter for folder..
	 * @param folderName : folderName is a string value that name of folder need to
	 *                   select.
	 */
	public void performExculdeFolderFilter(String folderName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_FolderFilter());
			bc.waitTillElemetToBeClickable(getDocExp_FolderFilter());
			getDocExp_FolderFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(folderName);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for folder" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to verify documents after applying exclude functionality
	 *               by folder..
	 * @param totalDocCount    : docCount is a integer value that total number of
	 *                         documents.
	 * @param selectedDocCount : selectedDocCount is a integer that count of
	 *                         selected documents count for bulk folder
	 */
	public void verifyExcludeFunctionlityForFolder(int totalDocCount, int selectedDocCount) {

		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getPresentDocCount());
			bc.waitTillElemetToBeClickable(getPresentDocCount());
			String[] presentDocCount = getPresentDocCount().getText().trim().split("of", 2);
			String docCount = presentDocCount[1].trim().replaceAll("[^0-9]", "");
			int expectedCount = totalDocCount - selectedDocCount;
			if (expectedCount == Integer.parseInt(docCount)) {
				bc.passedStep("Exclude filter functionality by folder is working as expected");
			} else {
				bc.failedStep("Exclude filter functionality by folder is not working as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while verifying documents after applying exclude functionality by folder"
					+ e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to refreh pager..
	 */
	public void refreshPage() {

		try {
			driver.Navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while refresh page" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude another filter for folder..
	 * @param folderName : folderName is a string value that name of folder need to
	 *                   select.
	 */
	public void performUpdateExculdeFolderFilter(String folderName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getActiveFilter());
			bc.waitTillElemetToBeClickable(getActiveFilter());
			getActiveFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(folderName);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getUpdateFilter());
			bc.waitTillElemetToBeClickable(getUpdateFilter());
			getUpdateFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for folder" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for selecting Documents.
	 * @param noofDocuments : noofDocuments is a integer value that number of
	 *                      documents need to select from doc explorer table.
	 * @return documentId : documentId is array list value that contains document
	 *         ids of selected document.
	 */
	public ArrayList<String> documentsSelection(int noofDocuments) throws InterruptedException {
		ArrayList<String> documentId = new ArrayList<String>();
		try {
			this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

			for (int D = 0; D < noofDocuments; D++) {
				driver.waitForPageToBeReady();
				bc.waitForElement(getDocumentsCheckBoxbyRowNum(D + 1));
				getDocumentsCheckBoxbyRowNum(D + 1).waitAndClick(10);
				documentId.add(getDocumentId(D + 1).getText().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting the document ." + e.getMessage());
		}
		return documentId;
	}

	/**
	 * @author Gopinath
	 * @Description :verifying the presence of documents after filling
	 *              document_comments section .
	 */
	public void verifyingTheSelectedDocumentInDocExplorerPage(ArrayList<String> documentId, String data)
			throws InterruptedException {
		try {
			this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
			String Counts = null;
			driver.waitForPageToBeReady();

			bc.waitForElement(getDocExp_CommentsFilter());
			getDocExp_CommentsFilter().Click();
			doclist.include(data);
			bc.waitForElement(doclist.getApplyFilter());
			doclist.getApplyFilter().waitAndClick(10);

			driver.waitForPageToBeReady();
			List<WebElement> allvalues = getDocumentsName().FindWebElements();
			System.out.println(allvalues.size());
			int j;

			List<String> values = new ArrayList<String>();
			for (j = 0; j < 10; j++) {
				driver.waitForPageToBeReady();
				Counts = allvalues.get(j).getText();
				values.add(allvalues.get(j).getText());
			}
			System.out.println(values);
			if (values.retainAll(documentId)) {
				bc.passedStep("The Document is displayed successfullly");
			} else {
				bc.failedStep("The Document is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occured while verifying the presence of documents after filling document_comments section ."
							+ e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating to doc Explorer page.
	 */
	public void navigateToDocExplorerURL() {
		try {
			driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while navigating to doc explorer url is failed" + e.getMessage());
		}
	}

	/**
	 * @Author:jayanthi
	 * 
	 */
	public void docExp_BulkAssign() {
		driver.waitForPageToBeReady();
		bc.waitForElement(getDocExp_actionButton());
		// getDocExp_actionButton().ScrollTo();
		getDocExp_actionButton().waitAndClick(15);
		bc.waitForElement(getDocExp_BulkAssign());
		getDocExp_BulkAssign().waitAndClick(15);
	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating To DocView From Doc Explorer.
	 */
	public void navigateToDoclistFromDocExplorer() {
		try {
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_actionButton());
			getDocExp_actionButton().waitAndClick(10);

			if (getView().isElementAvailable(10)) {
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

			bc.waitForElement(doclist.getDocListPageHeader());
			if (doclist.getDocListPageHeader().isElementAvailable(5)) {
				bc.passedStep("Navigated to doclist, to view docslist");
			} else {
				bc.failedStep("unable to Navigated to doclist, to view docslist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while navigating To DocView From Doc Explorer is failed" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating To DocView From Doc Explorer.
	 * @param emailSubject : emailSubject is string value that email subject value.
	 * @param masterDate   : masterDate is string value that master date value.
	 */
	public void masterDateWithEmailSubject(String emailSubject, String masterDate) {
		try {
			getEmailSubjectTextField().isElementAvailable(15);
			getEmailSubjectTextField().SendKeys(emailSubject);
			getEmailSubjectTextField().getWebElement().sendKeys(Keys.ENTER);
			getMasterDatetextField().isElementAvailable(15);
			getMasterDatetextField().SendKeys(masterDate);
			getMasterDatetextField().getWebElement().sendKeys(Keys.ENTER);
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while navigating To DocView From Doc Explorer is failed" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to navigate doc explorer page.
	 */
	public void navigateToDocExplorerPage() {
		try {
			driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while navigating doc explorer page." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for selecting Documents.
	 */
	public void selectDocument(int noofDocuments) {
		try {
			for (int D = 0; D < noofDocuments; D++) {
				driver.waitForPageToBeReady();
				bc.waitForElement(getDocumentsCheckBoxbyRowNum(D + 1));
				getDocumentsCheckBoxbyRowNum(D + 1).waitAndClick(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while selecting the document ." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to verify doc list header.
	 */
	public void verifyDocListHeader() {
		try {
			getDocListHeader().isElementAvailable(10);
			if (getDocListHeader().isDisplayed()) {
				bc.passedStep("Navigated to doclist successfully");
			} else {
				bc.failedStep("Navigating to doclist is failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while verify doc list header." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for entering file name in file name filter.
	 */
	public void enterFileNameInFileNameFilter(String fileName) {
		try {
			getEmailSubjectTextField().isElementAvailable(15);
			getEmailSubjectTextField().SendKeys(fileName);
			getEmailSubjectTextField().getWebElement().sendKeys(Keys.ENTER);
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while entering file name in file name filter." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating from doc explorer to doc list.
	 */
	public void docExplorerToDocList() {
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		bc.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		if (doclist.getPopUpOkBtn().isElementAvailable(5)) {
			bc.waitTillElemetToBeClickable(doclist.getPopUpOkBtn());
			doclist.getPopUpOkBtn().waitAndClick(5);
		} else {
			driver.Navigate().refresh();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocExp_SelectAllDocs().Visible();
				}
			}), Input.wait30);
			getDocExp_SelectAllDocs().waitAndClick(10);

			bc.waitTime(2);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return doclist.getPopUpOkBtn().Visible();
				}
			}), Input.wait30);
			bc.waitTillElemetToBeClickable(doclist.getPopUpOkBtn());
			doclist.getPopUpOkBtn().waitAndClick(5);
		}
		bc.waitTime(3);
		getDocExp_actionButton().isElementAvailable(10);
		getDocExp_actionButton().waitAndClick(10);
		bc.waitTime(3);
		if (getDocListAction().isDisplayed()) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocListAction().Visible();
				}
			}), Input.wait60);
			bc.waitTime(2);
			getDocListAction().isElementAvailable(10);
			getDocListAction().waitAndClick(20);
			try {
				if (bc.getYesBtn().isDisplayed()) {
					bc.getYesBtn().waitAndClick(10);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("Navigated to doclist, to view docslist");
			UtilityLog.info("Navigated to doclist, to view docslist");
		} else {
			Actions ac = new Actions(driver.getWebDriver());
			ac.moveToElement(getViewOn().getWebElement()).build().perform();
			bc.waitTime(2);
			getViewInDocListLat().isElementAvailable(15);
			getViewInDocListLat().Click();
		}

	}

	/**
	 * @author Gopinath
	 * @Description:methoad to select folder from tree to view docs in doc list
	 * @param folderNumber
	 */
	public void selectFolder(String folderNumber) {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getfolderFromTreeByNumber(folderNumber));
			getfolderFromTreeByNumber(folderNumber).waitAndClick(3);
			if (getfolderFromTreeByNumber(folderNumber).GetAttribute("class").contains("clicked")) {
				bc.passedStep("folder selected successfully");
			} else {
				bc.failedStep("Unable to select the folder ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while selecting the folder to view docs");
		}

	}

	/**
	 * @author Gopinath
	 * @Description: method to verify docList displayed selected from folder in a
	 *               tree
	 * @param folderNumber
	 */
	public void verifyDocList(String folderNumber) {
		try {
			selectFolder(folderNumber);

			driver.waitForPageToBeReady();
			getDocListCustodianName().isElementAvailable(10);
			String custodianNameInTable = getDocListCustodianName().getText();
			getfolderFromTreeByNumber(folderNumber).isElementAvailable(10);
			bc.waitForElement(getfolderFromTreeByNumber(folderNumber));
			String CustodianNameInTree = getfolderFromTreeByNumber(folderNumber).getText();
			String numberOfDocumentInFolder = CustodianNameInTree.substring(CustodianNameInTree.indexOf("(") + 1,
					CustodianNameInTree.indexOf(")"));

			bc.waitForElementCollection(getDocListInPage());
			int numberOfDocumentsInPage = getDocListInPage().FindWebElements().size();
			int PaginationCount = getDocLictPaginationCount().size();

			if (PaginationCount > 3) {
				for (int i = 1; i <= PaginationCount - 3; i++) {
					getDocLictPaginationNextButton().isElementAvailable(10);
					bc.waitForElement(getDocLictPaginationNextButton());
					getDocLictPaginationNextButton().waitAndClick(3);
					driver.waitForPageToBeReady();
					int numberOfDocumentsInNextPage = getDocListInPage().FindWebElements().size();
					numberOfDocumentsInPage = numberOfDocumentsInPage + numberOfDocumentsInNextPage;
				}

			}
			if (CustodianNameInTree.contains(custodianNameInTable)) {
				if (Integer.parseInt(numberOfDocumentInFolder) == numberOfDocumentsInPage) {
					bc.passedStep(
							"List View presents the documents corresponding to the folder(s) selected in the tree view");
				} else {
					bc.failedStep("All documents corresponding to the folder(s) are not displated in doc list");
				}
			} else {
				bc.failedStep("corresponding  folder document are not displayed in doc list");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verifying docExplorer docList due to " + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description: method to verify EmailRecipient domain Exclude filter
	 * @param EmailReceipientDmain
	 */
	public void verifyDocumentsExcludeOfEmailReceipientDomain(String EmailReceipientDmain) {
		bc.waitForElement(getEmailRecipientColumnTextField());
		getEmailRecipientColumnTextField().SendKeys(EmailReceipientDmain);
		getEmailRecipientColumnTextField().Enter();
		bc.waitForElement(getQuaryReturnedNoDataMessage());
		if (getQuaryReturnedNoDataMessage().isElementAvailable(5) && getDocListInfo().isElementAvailable(5)) {
			bc.passedStep(
					EmailReceipientDmain + " EmailRecipient domain documents are not displayed  after Exclude filter");
		} else {
			bc.failedStep(
					EmailReceipientDmain + " EmailRecipient domain documents are displayed even  after Exclude filter");
		}

	}

	/**
	 * @author Gopinath
	 * @Description:methoad to filter for email recipient domain
	 * @param functionality(Type of filter)
	 * @param data(Domain        name )
	 */
	public void emailRecipientDomainFiletr(String functionality, String data) {
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		bc.waitForElement(getDocExp_EmailReciepientDomainFilter());
		getDocExp_EmailReciepientDomainFilter().waitAndClick(5);
		if (functionality.equalsIgnoreCase("include")) {
			doclist.include(data);
			bc.waitTime(2);
			doclist.getApplyFilter().waitAndClick(10);
			bc.waitTime(2);

		}
		if (functionality.equalsIgnoreCase("exclude")) {
			doclist.exclude(data);
			bc.waitTime(2);
			doclist.getApplyFilter().waitAndClick(10);
			bc.waitTime(2);

		}
	}

	/**
	 * @author Gopinath
	 * @Description:method to verify count of documents in folder
	 * @param folderNumber
	 */
	public void verifyDOcExplorerFolderDocCount(String folderNumber) {
		try {
			driver.waitForPageToBeReady();
			getfolderFromTreeByNumber(folderNumber).isElementAvailable(10);
			String CustodianNameInTree = getfolderFromTreeByNumber(folderNumber).getText();
			String numberOfDocumentInFolder = CustodianNameInTree.substring(CustodianNameInTree.indexOf("(") + 1,
					CustodianNameInTree.indexOf(")"));
			bc.stepInfo("numberOfDocumentInFolder" + numberOfDocumentInFolder);
			bc.waitForElement(getFolderExpandButton(folderNumber));
			getFolderExpandButton(folderNumber).isElementAvailable(10);
			getFolderExpandButton(folderNumber).ScrollTo();
			getFolderExpandButton(folderNumber).waitAndClick(5);
			driver.waitForPageToBeReady();
			getDocExplorerSubFolders(folderNumber).isElementAvailable(10);
			bc.waitForElementCollection(getDocExplorerSubFolders(folderNumber));
			List<WebElement> totalSubfolders = getDocExplorerSubFolders(folderNumber).FindWebElements();
			int totalCountForSubFolderDocs = 0;
			for (WebElement eachFolder : totalSubfolders) {
				String subFolderName = eachFolder.getText();
				String countOfEachFolderDocs = subFolderName.substring(subFolderName.indexOf("(") + 1,
						subFolderName.indexOf(")"));
				totalCountForSubFolderDocs = totalCountForSubFolderDocs + Integer.parseInt(countOfEachFolderDocs);
			}
			bc.stepInfo("totalCountForSubFolderDocs" + totalCountForSubFolderDocs);
			if (Integer.parseInt(numberOfDocumentInFolder) == totalCountForSubFolderDocs) {
				bc.passedStep(
						"Count of documents should be displayed for each folder  with the corresponding number of docs in the folder");
			} else {
				bc.failedStep("Count of documents for  folder is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verifying the document count due to " + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description:method to select all documents from current page.
	 */
	public void selectAllDocumentsFromCurrentPage() {
		try {
			driver.waitForPageToBeReady();
			getDocExp_SelectAllDocs().isElementAvailable(10);
			getDocExp_SelectAllDocs().Click();
			driver.waitForPageToBeReady();
			if (doclist.getPopUpOkBtn().isDisplayed()) {
				doclist.getPopUpOkBtn().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while selecting all documents from current page." + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description:Method to apply ingestion name filter.
	 * @param ingestionName : ingestionName is String value that name of ingestion.
	 * @param mode          : mode is String value that the functionality need to
	 *                      perform like include or exclude.
	 */
	public void applyIngestionNameFilter(String mode, String ingestionName) {
		try {
			bc.waitForElement(getDocExp_IngestionNameFilter());
			getDocExp_IngestionNameFilter().waitAndClick(5);
			if (mode.equalsIgnoreCase("include")) {
				doclist.include(ingestionName);
				bc.waitTime(2);
				doclist.getApplyFilter().waitAndClick(10);
				bc.waitTime(2);
			}
			if (mode.equalsIgnoreCase("exclude")) {
				doclist.exclude(ingestionName);
				bc.waitTime(2);
				doclist.getApplyFilter().waitAndClick(10);
				bc.waitTime(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while applying ingestion name filter." + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description: method to verify documents of tag with exclude filter
	 * @param DocId : DocId is String value taht document id.
	 */
	public void verifyExcludeDocumentsOfTag(String DocId) {
		bc.waitForElement(getDocIdColumnTextField());
		getDocIdColumnTextField().SendKeys(DocId);
		getDocIdColumnTextField().Enter();
		bc.waitForElement(getQuaryReturnedNoDataMessage());
		if (getQuaryReturnedNoDataMessage().isElementAvailable(5) && getDocListInfo().isElementAvailable(5)) {
			bc.passedStep(DocId + " is not displaayed after Exclude filter of tag");
		} else {
			bc.failedStep(DocId + " is  displaayed even after Exclude filter of tag");
		}
	}

	/**
	 * @author Gopinath
	 * @Description: methoad to select multiple folders
	 * @param numberOfFolders
	 * @throws AWTException
	 */
	public void selectMultipleFoldersOfTree(int numberOfFolders) {
		Actions ac = new Actions(driver.getWebDriver());
		bc.waitForElement(getfolderFromTreeByNumber("2"));
		getfolderFromTreeByNumber("2").waitAndClick(5);
		for (int i = 2; i <= numberOfFolders + 1; i++) {
			ac.keyDown(Keys.CONTROL).click(getfolderFromTreeByNumber(Integer.toString(i)).getWebElement())
					.keyUp(Keys.CONTROL).build().perform();
			bc.waitTime(1);
		}

	}

	/**
	 * @author Gopinath
	 * @Description:method to select all the docs of current page to view in doc
	 *                     list
	 */
	public void viewIndocListOfCurrentPageDocs() {
		try {
			bc.waitForElement(getDocExp_SelectAllDocs());
			getDocExp_SelectAllDocs().waitAndClick(5);

			try {
				// bc.waitForElement(doclist.getPopUpOkBtn());
				if (doclist.getPopUpOkBtn().isElementAvailable(5)) {
					bc.passedStep("popup is appeared with ok and cancel button after select all docs");
					doclist.getPopUpOkBtn().waitAndClick(10);
				} else {
					bc.stepInfo("popup is not displayed after click on check box to select all docs");
				}
			} catch (Exception e) {
				System.out.println("No pop up displayed");
			}

			bc.waitForElement(getDocExp_actionButton());
			getDocExp_actionButton().waitAndClick(10);
			bc.waitTime(2);
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

			bc.waitForElement(doclist.getDocListPageHeader());
			if (doclist.getDocListPageHeader().isElementAvailable(5)) {
				bc.passedStep("Navigated to doclist, to view docslist");
			} else {
				bc.failedStep("unable to Navigated to doclist, to view docslist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while View in doc list due to " + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description:method to verify doc explorer page header is displayed.
	 */
	public void verifyDocExplorerPageHeader() {
		try {
			bc.waitForElement(getDocExplorerPageHeader());
			if (getDocExplorerPageHeader().isDisplayed()) {
				bc.passedStep("DocExplorer page is displayed as default menu for Project Administrator");
			} else {
				bc.stepInfo("DocExplorer page is not displayed as default menu for Project Administrator");
			}

		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verify doc explorer page header is displayed." + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description:methoad to verify long folder name in tooltip.
	 * @param folderName(name of the folder to verify in tooltip)
	 */
	public void verifyFolderName() {
		try {
			driver.waitForPageToBeReady();
			bc.waitTime(2);
			List<WebElement> folders = getFolderOfMoreCharacters().FindWebElements();
			for (int i = 0; i < folders.size(); i++) {
				driver.waitForPageToBeReady();
				String foldername = folders.get(i).getText();
				if (foldername.length() > 18) {
					Actions ac = new Actions(driver.getWebDriver());
					bc.waitTime(3);
					folders.get(i).click();
					ac.moveByOffset(50, 50).perform();
					bc.passedStep(
							"after mouse hover to the folder full name " + foldername + " is displayed in tool tip");
					ac.moveToElement(folders.get(i));
					ac.perform();
					getFolderToolTip().isElementAvailable(10);
					if (getFolderToolTip().isDisplayed()) {
						String folderNameInToolTip = getFolderToolTip().getText();
						System.out.println(folderNameInToolTip);
						if (foldername.equals(folderNameInToolTip)) {
							bc.passedStep("after mouse hover to the folder full name " + folderNameInToolTip
									+ " is displayed in tool tip");
						} else {
							bc.failedStep("folder name is " + folderNameInToolTip + " not displayed in tool tip");
						}
					} else {
						bc.failedStep("folder tool tip is not displayed");
					}

					break;
				} else if (i == folders.size() - 1) {
					bc.failedStep("folder name is not displayed in tool tip for full name with long name");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verify long folder name in tooltip." + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description:method to verify doc Explorer folder level
	 * @param folderName
	 * @param folderLevel
	 */
	public void verifyDocExpFolderLevel(String folderName, int folderLevel) {
		try {
			driver.waitForPageToBeReady();
			;
			getfolderFromTreeByName(folderName).ScrollTo();
			bc.waitForElement(getDocExpFolderExpandbutton(folderName));
			getDocExpFolderExpandbutton(folderName).waitAndClick(5);
			Actions ac = new Actions(driver.getWebDriver());

			for (int i = 1; i <= folderLevel - 1; i++) {
				bc.waitForElement(getDoxExpSubFoldarExpandbutton(folderName, i));
				ac.moveToElement(getDoxExpSubFoldarExpandbutton(folderName, i).getWebElement()).click().build()
						.perform();
				bc.waitTime(1);
			}
			bc.waitForElement(getDocExpSubFolderName(folderName, folderLevel));
			if (getDocExpSubFolderName(folderName, folderLevel).isElementAvailable(5)
					&& getDocExpSubFolderName(folderName, folderLevel).isDisplayed()) {
				bc.passedStep("folders are displayed upto " + (folderLevel) + " level without horizontal scroll");
			} else {
				bc.failedStep("folder is not displayed at " + (folderLevel) + " level");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verifying doc explorer folders due to " + e.getMessage());
		}
	}

	/**
	 * @author Gopianth
	 * @Description:methoad to verify doc explorer default tree structure
	 * @param folderName
	 */
	public void verrifyDocExpDefaultTreeStructure(String folderName) {
		try {
			bc.waitForElement(getfolderFromTreeByNumber("1"));
			if (getfolderFromTreeByNumber("1").isDisplayed()) {
				if (getDocExplorerSubFolder().isElementAvailable(3)) {
					bc.failedStep("by default sub folders are expanded from tree structure");
				} else {
					bc.passedStep("By default tree structure is expanded presents by default  level 1 expanded");

				}
			} else {
				bc.failedStep("folders from tree structure are not displayed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verifying tree strecture");
		}

	}

	/**
	 * @author Gopinath
	 * @Description method to verify doc explore doc folder count separated by
	 *              comma(,)
	 * @param folderNumber
	 */
	public void verifyDocExpFolderCountFormat(String folderNumber) {
		try {
			bc.waitForElement(getfolderFromTreeByNumber(folderNumber));
			String folderName = getfolderFromTreeByNumber(folderNumber).getText();
			String folderCount = folderName.substring(folderName.indexOf("(") + 1, folderName.indexOf(")"));
			if (folderCount.length() < 4) {
				if (folderCount.contains(",")) {
					bc.failedStep("folder count is seperated by ',' for below thousand documents");
				} else {
					bc.failedStep("folder count should be more then 1000");
				}
			}
			if (folderCount.contains(",")) {
				bc.passedStep("folder count having " + folderCount + " documents are separated by comma(,) ");
			} else {
				bc.failedStep("comma(,) is not used to separate the count having " + folderCount + " documents");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * @Author:Indium-sowndarya.Velraj Modify By:Vijaya.Rani Modify Date:10/03/2022
	 * 
	 */
	public void docExpViewInDocView() {

		bc.waitForElement(getDocExp_actionButton());
		getDocExp_actionButton().waitAndClick(10);
		if (getView().isDisplayed()) {
			driver.waitForPageToBeReady();
			Actions act = new Actions(driver.getWebDriver());
			act.moveToElement(getView().getWebElement()).build().perform();
		} else {
			System.out.println("View is not found");
		}

		bc.waitForElement(getDocViewAction());
		getDocViewAction().waitAndClick(5);
	}

	/**
	 * @author Gopinath
	 * @Description method to verify DocExplorer view presented below the dash board
	 *              in left menu panal
	 */
	public void verifyDocExplorerBelowDashBoard() {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getDocExplorerTabAfterDashBoard());
			if (getDocExplorerTabAfterDashBoard().isDisplayed()) {
				bc.passedStep("Doc explorer view is displayed below dash board from left menu");
			} else {
				bc.failedStep("Doc explorer view is not displayed after dash board fro left menu");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verifying docExplorer view in left menu panal");

		}

	}

	/**
	 * @author Gopinath
	 * @Description method to verify docExplorer view presented above the datasets
	 *              in left menu panal
	 */
	public void verifyDocExplorerAboveDatasets() {
		try {
			bc.waitForElement(getDocExplorerAboveDatasets());
			if (getDocExplorerAboveDatasets().isDisplayed()) {
				bc.passedStep("Docexplorer view is displayed above the datasets in left menu");
			} else {
				bc.failedStep("Docexplorer view is not displayed above the datasets in left menu");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verifying Doc explorer view in left menu panal");
		}
	}

	/**
	 * @author Gopinath
	 * @Description: method to verify DocExplorer in left menu panal
	 */
	public void verifyDocExplorerInLeftMenu() {
		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getDocExplorerTab());
			if (getDocExplorerTab().getWebElement().isDisplayed()) {
				bc.passedStep("Doc Explorer is presented in left menu panal");

			} else {
				bc.failedStep("Doc explorer is not presented in left menu panal");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.stepInfo("Exception occured while verifying docExplorer tab");
		}
	}

	/**
	 * @author Gopinath
	 * @Description method to verify folders having zero doc are not displayed
	 */
	public void verifyDOcExplorerNoZeroDocFolder() {
		driver.waitForPageToBeReady();
		bc.waitForElementCollection(getDocExplorerFolders());
		for (int i = 0; i < getDocExplorerFolders().size(); i++) {
			String folderName = getDocExplorerFolders().FindWebElements().get(i).getText();
			String noOfDdocs = folderName.substring(folderName.indexOf("(") + 1, folderName.indexOf(")"));
			if (noOfDdocs.equals("0")) {
				bc.failedStep(folderName + " folder having zero documents is displayed in doc explorer tree structure");
				break;
			}
		}
		bc.passedStep("folders with zero documents are not displayed");

	}

	/**
	 * @author : Gopinath
	 * @Description : Method to verify custodian and corresponding data sets are
	 *              displayed
	 * @param folderName
	 */
	public void verrifyDocExplorerFolder(String folderName) {
		try {
			getfolderFromTreeByName(folderName).ScrollTo();
			bc.waitForElement(getfolderFromTreeByName(folderName));
			if (getfolderFromTreeByName(folderName).isDisplayed()) {
				String custName = getfolderFromTreeByName(folderName).getText();
				if (custName.length() > 0) {
					bc.passedStep("folder with custodian name is displayed in doc explorer tree structure ");
				} else {
					bc.failedStep("custodian name is not displayed");
				}
			} else {
				bc.failedStep("folder is not displayed in doc explorer tree view");
			}
			bc.waitForElement(getDocExpFolderExpandbutton(folderName));
			getDocExpFolderExpandbutton(folderName).waitAndClick(5);
			bc.waitForElement(getDocExpSubFolderName(folderName, 1));
			if (getDocExpSubFolderName(folderName, 1).isElementAvailable(5)
					&& getDocExpSubFolderName(folderName, 1).isDisplayed()) {
				bc.passedStep("After click on coustodian name corresponding datasets are expanded and displayed");
			} else {
				bc.failedStep("After click on coustodian name corresponding datasets are not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while verifying custodian and datasets");
		}

	}

	/**
	 * @author Jayanthi.Ganesan This method will navigate from doc explorer page to
	 *         Export page.
	 */
	public void docExpToExport() {
		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(10);
		bc.waitForElement(exportDataFromActionDropdown());
		exportDataFromActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author
	 * @description: method to verify subfolders are displayed in doc explorer tree
	 */
	public void verifySubFoldersDisplayed() {
		bc.waitForElement(getAllFoldersExpandButton());
		if (getAllFoldersExpandButton().isElementAvailable(5)) {
			bc.passedStep("All folders littele Arrow button is displayed to indicating they have su folders");
		} else {
			bc.failedStep("All folders little Arrow button is not displayed");
		}
		bc.waitForElement(getFolderExpandButton("1"));
		getFolderExpandButton("1").waitAndClick(5);
		bc.waitForElement(getDocExplorerSubFolder());
		if (getDocExplorerSubFolder().isDisplayed()) {
			bc.passedStep("After click on arrow button subfolders are displayed");
		} else {
			bc.failedStep("Afetr click on arrow button subfolders are not displayed");
		}

	}

	/**
	 * @author
	 * @Description:method to verify count of selected multiple folders in table
	 * @param numberOfFolders
	 */
	public void verifyMultiFoldersCount(int numberOfFolders) {
		bc.waitForElement(getfolderFromTreeByNumber("2"));
		int numberOfDocumentInFolders = 0;
		for (int i = 3; i <= numberOfFolders + 1; i++) {
			bc.waitForElement(getfolderFromTreeByNumber(String.valueOf(i)));
			String CustodianNameInTree = getfolderFromTreeByNumber(String.valueOf(i)).getText();
			String numberOfDocumentInFolder = CustodianNameInTree.substring(CustodianNameInTree.indexOf("(") + 1,
					CustodianNameInTree.indexOf(")"));
			if (numberOfDocumentInFolder.contains(",")) {
				numberOfDocumentInFolder = numberOfDocumentInFolder.replace(",", "");
			}
			numberOfDocumentInFolders = numberOfDocumentInFolders + Integer.parseInt(numberOfDocumentInFolder);
		}

		int docCountIntable = docCountInTable();
		if (docCountIntable == numberOfDocumentInFolders) {
			bc.stepInfo(
					"Document count from the right side is same as the count displayed for the selected multiple folders in tree view");
		} else {
			bc.failedStep(
					"Document count from the right is not equal to the count displayed for the selected multiple folders in tree view");
		}
	}

	/**
	 * @author
	 * @Description:method to get the count of documents in a table
	 * @return (number of documents in table)
	 */
	public int docCountInTable() {

		bc.waitForElementCollection(getDocListInPage());
		int numberOfDocumentsInTable = getDocListInPage().FindWebElements().size();

		while (true) {
			if (getPageNextButtonDisabled().isElementAvailable(3)) {
				break;
			} else {
				bc.waitForElement(getDocLictPaginationNextButton());
				getDocLictPaginationNextButton().waitAndClick(3);
				driver.waitForPageToBeReady();
				int numberOfDocumentsInNextPage = getDocListInPage().FindWebElements().size();
				numberOfDocumentsInTable = numberOfDocumentsInTable + numberOfDocumentsInNextPage;
			}

		}
		return numberOfDocumentsInTable;
	}

	/**
	 * @author
	 * @Description:Method to verify arrow button is not displayed for the folder
	 *                     which are not having sub folders
	 * @param folderName
	 */
	public void verifyArrowButtonNotDisplayed(String folderName) {
		bc.waitForElement(getDocExpFolderExpandbutton(folderName));
		getDocExpFolderExpandbutton(folderName).waitAndClick(5);
		int folderNumer = 0;
		String folder = "";
		;
		boolean flag = true;
		while (true) {
			folderNumer = folderNumer + 1;
			bc.waitForElement(getDoxExpSubFoldarExpandbutton(folderName, folderNumer));
			getDoxExpSubFoldarExpandbutton(folderName, folderNumer).waitAndClick(5);
			bc.waitTime(2);
			if (!getDocExpSubfolderExpandButtonLast(folderName).isElementAvailable(3)) {
				if (!getDocExpSubFolderName(folderName, folderNumer + 1).isElementAvailable(5)) {
					folder = folder + getDocExpSubFolderName(folderName, folderNumer).getText();
					flag = false;
					break;
				}
			} else {
				break;
			}
		}
		if (flag == true) {
			bc.passedStep("Arror button is not displayed for the  folder which is not having sub folders");
		} else {
			bc.failedStep(
					"Arror button is   displayed for the folder '" + folder + "' which is not having sub folders");
		}
	}

	public void docExloerExportData(String fullName, String emailname) throws Exception {

		bc = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		doclist.getPopUpOkBtn().Click();

		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(5);

		bc.waitForElement(exportDataFromActionDropdown());
		exportDataFromActionDropdown().waitAndClick(5);
		bc.stepInfo("Export pop up is open Successfully");

		bc.waitForElement(exportWindow_AllCustodiansCheckbox());
		exportWindow_AllCustodiansCheckbox().waitAndClick(10);

		driver.scrollingToBottomofAPage();
		bc.waitForElement(exportWindow_AddToSelectedButton());
		exportWindow_AddToSelectedButton().waitAndClick(10);

		bc.waitForElement(getExportSchedulerButton());
		getExportSchedulerButton().waitAndClick(5);
		bc.stepInfo("Schedule Report pop up is open Successfully");

		bc.waitForElement(getExportShareEmail());
		getExportShareEmail().SendKeys(emailname);

	}

	public void docExloerRelease(String SecGroup) throws Exception {

		bc = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		doclist.getPopUpOkBtn().Click();

		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(5);

		bc.waitForElement(getBulkReleaseBtn());
		getBulkReleaseBtn().waitAndClick(5);

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

		if (getFinalizeButton().isDisplayed()) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFinalizeButton().Visible();
				}
			}), Input.wait60);
			getFinalizeButton().waitAndClick(20);
		}

		bc.VerifySuccessMessageB("Records saved successfully");

		System.out.println("performing bulk release");
		UtilityLog.info("performing bulk release");

	}

	public void docExloerUnRelease(String SecGroup) throws Exception {

		bc = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		doclist.getPopUpOkBtn().Click();

		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(5);

		bc.waitForElement(getBulkReleaseBtn());
		getBulkReleaseBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible();
			}
		}), Input.wait60);
		getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelease_ButtonUnRelease().Visible();
			}
		}), Input.wait60);
		getBulkRelease_ButtonUnRelease().waitAndClick(20);

		bc.VerifySuccessMessageB("Records saved successfully");

		System.out.println("performing bulk release");
		UtilityLog.info("performing bulk release");

	}

	public void newBulkFolderExisting(String folderName) throws InterruptedException {
		bc = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		doclist.getPopUpOkBtn().Click();

		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(5);

		bc.waitForElement(getBulkFolder());
		getBulkFolder().waitAndClick(5);
		System.out.println("Popup is displayed");

		bc.waitForElement(getDocExplorer_NewFolder());
		getDocExplorer_NewFolder().waitAndClick(5);

		bc.waitForElement(getBulkFolder_SelectAllFolder());
		getBulkFolder_SelectAllFolder().waitAndClick(5);

		bc.waitForElement(getDocExplorer_NewFolderName());
		getDocExplorer_NewFolderName().SendKeys(folderName);

		driver.waitForPageToBeReady();
		bc.waitForElement(getContinueButton());
		getContinueButton().Click();
		System.out.println("Clicked continue");

		driver.waitForPageToBeReady();

		bc.waitForElement(getFinalizeButton());

		getFinalizeButton().Click();
		driver.Manage().window().maximize();
		bc.VerifySuccessMessage("Records saved successfully");
	}

	public void bulkFolderExistingInDocExplorer(String folderName) throws InterruptedException {
		bc = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		doclist.getPopUpOkBtn().Click();

		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(5);

		bc.waitForElement(getBulkFolder());
		getBulkFolder().waitAndClick(5);
		System.out.println("Popup is displayed");

		if (getBulkFolderCheckBox(folderName).isDisplayed()) {
			bc.passedStep("Created Folder Is Displayed");
		} else {
			bc.failedStep("Created Folder Is not Displayed");
		}

	}

	/**
	 * @author: Arun Created Date: 26/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will return the docsCount from selected folder tree
	 *               view
	 */
	public int getFolderCountFromTreeView(String folderNumber) {
		bc.waitForElement(getfolderFromTreeByNumber(folderNumber));
		getfolderFromTreeByNumber(folderNumber).waitAndClick(10);
		String folderName = getfolderFromTreeByNumber(folderNumber).getText();
		String folderCount = folderName.substring(folderName.indexOf("(") + 1, folderName.indexOf(")"));
		int treeviewCount = Integer.parseInt(folderCount.replace(",", ""));
		return treeviewCount;
	}

	/**
	 * @author: Arun Created Date: 26/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will return the docsCount for selected folder in
	 *               right side list view
	 */
	public int getDocumentCountFromListView() {
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		bc.waitForElement(getDocExp_DocumentList_info());
		String count = getDocExp_DocumentList_info().getText().toString();
		String docsCount = count.substring(count.indexOf("of") + 3, count.indexOf(" entries"));
		int listViewCount = Integer.parseInt(docsCount.replace(",", ""));
		driver.scrollPageToTop();
		return listViewCount;
	}

	/**
	 * @author: Arun Created Date: 26/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the count in folder tree view and right
	 *               side list view
	 */
	public void verifyDocsCountInFolderAndListView(int treeCount, int listCount) {
		if (treeCount == listCount) {
			bc.passedStep("Document count on the list view is same as in folder tree view");
		} else {
			bc.failedStep("document count in tree view and list view not matched");
		}
	}

	/**
	 * @author: Arun Created Date: 27/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the fields in doc explorer table list
	 *               view
	 */
	public void verifyDocExpFieldAvailabilityInListView() {

		String[] fields = { "DOCID", "FAMILY", "CUSTODIANNAME", "DOCFILETYPE", "EMAILSUBJECT/FILENAME", "MASTERDATE",
				"EMAILAUTHOR", "EMAILRECIPIENTS" };
		driver.waitForPageToBeReady();
		for (int i = 0; i < fields.length; i++) {
			bc.waitForElement(getDocExpField(fields[i]));
			if (getDocExpField(fields[i]).isElementAvailable(10)) {
				bc.passedStep(fields[i] + " field available in doc explorer list view");
			} else {
				bc.failedStep(fields[i] + " field not available in doc explorer list view");
			}
		}

	}

	/**
	 * @author: Arun Created Date: 27/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the family field values in doc exp list
	 *               view
	 */
	public void verifyFamilyFieldValuesInDocExp(String[] familyValues, String[] values) {

		for (int j = 0; j < familyValues.length; j++) {
			bc.waitForElement(getDocExpField("FAMILY"));
			getDocExpField("FAMILY").SendKeys(familyValues[j]);
			getDocExpField("FAMILY").SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			int docsCount = getDocumentCountFromListView();
			String fieldValue = getDocExpFieldValues(familyValues[j]).GetAttribute("data-content");
			System.out.println("field Value for" + familyValues[j] + ":" + fieldValue);
			bc.stepInfo("field Value for" + familyValues[j] + ":" + fieldValue);
			if (docsCount > 0 && fieldValue.equalsIgnoreCase(values[j])) {
				bc.passedStep(values[j] + " family field values displayed correctly");
			} else {
				bc.failedStep(values[j] + " family field values not displayed");
			}
		}
	}

	public void docExplorerRelease(String SecGroup) {
		driver.waitForPageToBeReady();
		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(5);

		bc.waitForElement(getBulkReleaseBtn());
		getBulkReleaseBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible();
			}
		}), Input.wait60);
		getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkRelease_ButtonUnRelease().Visible();
			}
		}), Input.wait60);
		getBulkRelease_ButtonUnRelease().waitAndClick(20);

		bc.VerifySuccessMessageB("Records saved successfully");

		System.out.println("performing bulk release");
		UtilityLog.info("performing bulk release");

	}

	public void docExplorerexportData() throws InterruptedException {
		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(10);

		bc.waitForElement(exportDataFromActionDropdown());
		exportDataFromActionDropdown().waitAndClick(10);

		bc.waitForElement(exportWindow_AllCustodiansCheckbox());
		exportWindow_AllCustodiansCheckbox().waitAndClick(10);

		driver.scrollingToBottomofAPage();
		bc.waitForElement(exportWindow_AddToSelectedButton());
		exportWindow_AddToSelectedButton().waitAndClick(10);

		driver.scrollPageToTop();
		bc.waitForElement(exportWindow_RunExport());
		exportWindow_RunExport().waitAndClick(10);

		bc.waitForElement(exportWindow_closeButton());
		exportWindow_closeButton().waitAndClick(10);

		System.out.println("DocView and Doc Explorer_Performance_Navigate through documents one by one- Successfully");
		bc.passedStep("DocView and Doc Explorer_Performance_Navigate through documents one by one- Successfully");
	}

	/**
	 * @author :Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude filter for DocFileType.
	 */
	public void performExculdeDocFileTypeFilter(String fileType) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_GetDocFIleTypeFilter());
			bc.waitTillElemetToBeClickable(getDocExp_GetDocFIleTypeFilter());
			getDocExp_GetDocFIleTypeFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(fileType);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for folder" + e.getMessage());
		}
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to verify documents after applying exclude functionality
	 *               by Docfiletype.
	 * 
	 */
	public void verifyExcludeFunctionlityForDocFileType() {

		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getPresentDocCount());
			bc.waitTillElemetToBeClickable(getPresentDocCount());
			driver.waitForPageToBeReady();
			if (bc.text("ID000").isDisplayed()) {
				bc.passedStep("Exclude filter functionality by folder is working as expected");
			} else {
				bc.failedStep("Exclude filter functionality by folder is not working as expected.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while verifying documents after applying exclude functionality by Docfiletype"
							+ e.getMessage());
		}
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude another filter for DocFileType.
	 * @param folderName : folderName is a string value that name of folder need to
	 *                   select.
	 */
	public void performUpdateExculdeDocFileTypeFilter(String fileType) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getActiveFilter());
			bc.waitTillElemetToBeClickable(getActiveFilter());
			getActiveFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(fileType);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getUpdateFilter());
			bc.waitTillElemetToBeClickable(getUpdateFilter());
			getUpdateFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for folder" + e.getMessage());
		}
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA
	 * @Description: Method for selecting documents and Untag them..
	 * @param tagName : tagName is a string value that name of tag need to select.
	 */
	public void selectDocAndBulkUnTagDocs(String tagName) {
		try {
			driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
			for (int i = 0; i < 20; i++) {
				try {
					getAllDocSelectedCheckBox().Click();
					break;
				} catch (Exception e) {
					bc.waitForElement(getAllDocSelectedCheckBox());
					bc.waitTillElemetToBeClickable(getAllDocSelectedCheckBox());
				}
			}
			if (getOkButton().isDisplayed()) {
				bc.waitTillElemetToBeClickable(getOkButton());
				getOkButton().Click();
			} else {
				bc.stepInfo("Ok button is not appered");
			}
			for (int i = 0; i < 10; i++) {
				try {
					bc.waitTime(2);
					bc.waitForElement(getDocExp_actionButton());
					bc.waitTillElemetToBeClickable(getDocExp_actionButton());
					getDocExp_actionButton().Click();
					break;
				} catch (Exception e) {
					bc.waitTime(2);
				}
			}
			bc.waitTillElemetToBeClickable(getBulkTagButton());
			getBulkTagButton().Click();
			bc.waitForElement(getDocExplorer_UnTagDocuments());
			getDocExplorer_UnTagDocuments().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			bc.waitForElement(getDocBoard());
			getDocBoard().Click();
			bc.waitTime(2);
			getTag(tagName).Click();
			for (int i = 0; i < 20; i++) {
				try {
					bc.waitTime(4);
					bc.waitTillElemetToBeClickable(getContinueButton());
					getContinueButton().Click();
					break;
				} catch (Exception e) {
					bc.waitTillElemetToBeClickable(getContinueButton());
				}
			}
			bc.waitForElement(getFinalizeButton());
			bc.waitTillElemetToBeClickable(getFinalizeButton());
			getFinalizeButton().Click();
			try {
				bc.waitForElement(getOkButton());
				bc.waitTillElemetToBeClickable(getOkButton());
				getOkButton().Click();
			} catch (Exception e) {
				bc.stepInfo("Ok button is not appered after bulk tag operation");
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
			bc.failedStep("Exception occcured while selecting documents or bulk tag" + e.getMessage());

		}
	}

	public void DocumentInDocFileTypeFilters(String document) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_DocFiletypeSearchName().Visible();
			}
		}), Input.wait30);
		getDocExp_DocFiletypeSearchName().SendKeys(document);

		doclist.getApplyFilter().waitAndClick(10);
		bc.stepInfo(" Value entered applied only to the \"DocFileType\" corresponding field.");
		if (bc.text("MS Word").isDisplayed()) {
			bc.passedStep("It return documents filter functionality by docs is working as expected");
		} else {
			bc.failedStep("It return documents filter functionality by docs is not working as expected");
		}
	}

	/**
	 * @author :Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude filter for IngestionName
	 */
	public void performExculdeIngestionNameFilter(String ingestionName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_IngestionNameFilter());
			bc.waitTillElemetToBeClickable(getDocExp_IngestionNameFilter());
			getDocExp_IngestionNameFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(ingestionName);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for IngestionName" + e.getMessage());
		}
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude another filter for IngestionName.
	 *
	 */
	public void performUpdateExculdeIngestionNameFilter(String ingestionName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getActiveFilter());
			bc.waitTillElemetToBeClickable(getActiveFilter());
			getActiveFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(ingestionName);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getUpdateFilter());
			bc.waitTillElemetToBeClickable(getUpdateFilter());
			getUpdateFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for IngestionName" + e.getMessage());
		}
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to verify documents after applying exclude functionality
	 *               by IngestionName.
	 * 
	 */
	public void verifyExcludeFunctionlityForIngestionName() {

		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getPresentDocCount());
			bc.waitTillElemetToBeClickable(getPresentDocCount());
			driver.waitForPageToBeReady();
			if (bc.text("ID000").isDisplayed()) {
				bc.passedStep("Exclude filter functionality by IngestionName is working as expected");
			} else {
				bc.failedStep("Exclude filter functionality by IngestionName is not working as expected.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while verifying documents after applying exclude functionality by IngestionName"
							+ e.getMessage());
		}
	}

	/**
	 * @author : Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude filter for folder..
	 * @param folderName : folderName is a string value that name of folder need to
	 *                   select.
	 */
	public void performIncludeFolderFilter(String folderName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_FolderFilter());
			bc.waitTillElemetToBeClickable(getDocExp_FolderFilter());
			getDocExp_FolderFilter().Click();
			bc.waitForElement(getIncludeRadioBtn());
			bc.waitTillElemetToBeClickable(getIncludeRadioBtn());
			getIncludeRadioBtn().Click();
			getSearchTextArea().SendKeys(folderName);
			bc.waitTime(10);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTime(10);
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for folder" + e.getMessage());
		}
	}

	/**
	 * @author : Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to verify documents after applying Include functionality
	 *               by Docfiletype.
	 * 
	 */
	public void verifyIncludeFunctionlityForDocFileType(String DocId) {

		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getPresentDocCount());
			bc.waitTillElemetToBeClickable(getPresentDocCount());
			driver.waitForPageToBeReady();
			if (bc.text(DocId).isDisplayed()) {
				bc.passedStep("Include filter functionality by folder is working as expected");
			} else {
				bc.failedStep("Include filter functionality by folder is not working as expected.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while verifying documents after applying exclude functionality by Docfiletype"
							+ e.getMessage());
		}
	}

	/**
	 * @author : Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude another filter for folder..
	 * @param folderName : folderName is a string value that name of folder need to
	 *                   select.
	 */
	public void performUpdateIncludeFolderFilter(String folderName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getActiveFilter());
			bc.waitTillElemetToBeClickable(getActiveFilter());
			getActiveFilter().Click();
			bc.waitForElement(getIncludeRadioBtn());
			bc.waitTillElemetToBeClickable(getIncludeRadioBtn());
			getIncludeRadioBtn().Click();
			getSearchTextArea().SendKeys(folderName);
			bc.waitTime(10);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getUpdateFilter());
			bc.waitTillElemetToBeClickable(getUpdateFilter());
			getUpdateFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occcured while performing exclude filter for folder" + e.getMessage());
		}
	}

	/**
	 * @author : sowndariya
	 * @Description: Method for selecting some documents and action bulk tag them..
	 * @param tagName           : tagName is a string value that name of tag need to
	 *                          select.
	 * @param numberOfDocuments : numberOfDocuments is integer value that number of
	 *                          documents need to select.
	 * @return docids : Selected documents ID's for bulk tag.
	 */
	public ArrayList<String> selectDocumentsAndActionBulkTag(int numberOfDocuments, String tagName) {
		ArrayList<String> docids = new ArrayList<String>();

		driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		for (int i = 0; i < 5; i++) {
			try {
				bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
				bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
				break;
			} catch (Exception e) {
				bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
				bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
			}
		}

		for (int row = 0; row < numberOfDocuments; row++) {
			bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
			bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
			getDocumentsCheckBoxbyRowNum(row + 1).Click();
			docids.add(getDocumentsIdbyRowNum(row + 1).GetAttribute("data-content").trim());
		}
		for (int i = 0; i < 7; i++) {
			try {
				bc.waitForElement(getDocExp_actionButton());
				bc.waitTillElemetToBeClickable(getDocExp_actionButton());
				getDocExp_actionButton().Click();
				break;
			} catch (Exception e) {
//					Thread.sleep(Input.wait30 / 10);
			}
		}
		bc.waitTillElemetToBeClickable(getBulkTagButton());
		getBulkTagButton().Click();
		driver.scrollingToBottomofAPage();
		return docids;

	}

	/**
	 * @author: Vijaya.Rani Created Date: 17/10/2022 Modified by: NA Modified Date:
	 *          NA
	 * @description: this method will verify the Email Recipient field values in doc
	 *               exp list view
	 */
	public void verifyEmailRecipientValuesInDocExp(String[] emailRecipientValues) {

		for (int j = 0; j < emailRecipientValues.length; j++) {

			bc.waitForElement(getEmailRecipient("EMAILRECIPIENTS"));
			getEmailRecipient("EMAILRECIPIENTS").SendKeys(emailRecipientValues[j]);
			getEmailRecipient("EMAILRECIPIENTS").SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.validatingGetTextElement(getEmailRecipientValues(), emailRecipientValues[j]);

			driver.Navigate().refresh();
		}
	}

	/**
	 * @author: Vijaya.Rani Created Date: 17/10/2022 Modified by: NA Modified Date:
	 *          NA
	 * @description: this method will verify the EmailSubject/Filename field values
	 *               in doc exp list view
	 */
	public void verifyEmailSubjectFilenameValuesInDocExp(String[] emailFileValues) {

		for (int j = 0; j < emailFileValues.length; j++) {

			bc.waitForElement(getDocExp_EmailSubFileSearchName("EMAILSUBJECT/FILENAME"));
			getDocExp_EmailSubFileSearchName("EMAILSUBJECT/FILENAME").SendKeys(emailFileValues[j]);
			getDocExp_EmailSubFileSearchName("EMAILSUBJECT/FILENAME").SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.validatingGetTextElement(getEmailSubFileValues(), emailFileValues[j]);
			bc.stepInfo("Selected email value is " + emailFileValues[j]);
			driver.Navigate().refresh();
		}
	}

	public void docExpExportDataSaveReport(String customReportName) throws Exception {

		bc = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocExp_SelectAllDocs().Visible();
			}
		}), Input.wait30);
		getDocExp_SelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doclist.getPopUpOkBtn().Visible();
			}
		}), Input.wait30);
		doclist.getPopUpOkBtn().Click();

		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(5);

		bc.waitForElement(exportDataFromActionDropdown());
		exportDataFromActionDropdown().waitAndClick(5);
		bc.stepInfo("Export pop up is open Successfully");

		bc.waitForElement(exportWindow_AllCustodiansCheckbox());
		exportWindow_AllCustodiansCheckbox().waitAndClick(10);

		driver.scrollingToBottomofAPage();
		bc.waitForElement(exportWindow_AddToSelectedButton());
		exportWindow_AddToSelectedButton().waitAndClick(10);

		bc.waitForElement(getExportDataSaveReport());
		getExportDataSaveReport().waitAndClick(5);
		bc.stepInfo("Save Report pop up is open Successfully");

		bc.waitForElement(getExportDataCustomRepName());
		getExportDataCustomRepName().SendKeys(customReportName);

		bc.waitForElement(getExportSaveBtn());
		getExportSaveBtn().waitAndClick(5);

		bc.VerifySuccessMessage("Report save successfully");
		bc.stepInfo("Report Saved Successfully With the name " + customReportName);
	}

	/**
	 * @author Brundha.T
	 * @param RowNo
	 * @param ele
	 * @param Value Description: method to verify the navigation option in
	 *              docexplorer page
	 */
	public void verifyingNavigationOption(int RowNo, Element ele, String Value) {
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		List<String> DefaultPage = bc.availableListofElements(getRowData(RowNo));
		System.out.println(DefaultPage);
		bc.waitTime(2);
		ele.waitAndClick(5);
		driver.waitForPageToBeReady();
		List<String> NavigatedPage = bc.availableListofElements(getRowData(RowNo));
		System.out.println(NavigatedPage);
		if (Value.equals("NotCompareEqual")) {
			bc.listCompareNotEquals(DefaultPage, NavigatedPage, "Navigated as per the page number", "Not navigated");
		} else if (Value.equals("Compare")) {
			bc.listCompareEquals(DefaultPage, NavigatedPage, "Navigated as per the page number", "Not navigated");
		} else if (Value.equals("yes")) {
			bc.ValidateElementCollection_Presence(getRowData(RowNo), "Documents in DocExplorer");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author: Vijaya.Rani
	 * @description: Get Count for docexplorer folder count
	 */
	public int getcount(String folderNumber) {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		bc.waitTime(5);
		String folderName = getfolderFromTreeByNumber(folderNumber).getText();
		String folderCount = folderName.substring(folderName.indexOf("(") + 1, folderName.indexOf(")"));
		int treeviewCount = Integer.parseInt(folderCount.replace(",", ""));
		return treeviewCount;
	}

	/**
	 * @author: Vijaya.Rani
	 * @description: this method will return the docsCount from selected All
	 *               Document folder tree view
	 */
	public int getAllDocumentFolderCountFromTreeView() {
		bc.waitForElement(getAllDocumentsCount());
		getAllDocumentsCount().waitAndClick(10);
		String folderName = getAllDocumentsCount().getText();
		String folderCount = folderName.substring(folderName.indexOf("(") + 1, folderName.indexOf(")"));
		int treeviewCount = Integer.parseInt(folderCount.replace(",", ""));
		return treeviewCount;

	}

	/**
	 * @author :Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude filter for Comments
	 */
	public void performExculdeCommentsFilter(String Comments) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_CommentsFilter());
			bc.waitTillElemetToBeClickable(getDocExp_CommentsFilter());
			getDocExp_CommentsFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(Comments);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while performing exclude filter for EmailRecipientNames" + e.getMessage());
		}
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to verify documents after applying exclude functionality
	 *               by Comments.
	 * 
	 */
	public void verifyExcludeFunctionlityForComments() {

		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getPresentDocCount());
			bc.waitTillElemetToBeClickable(getPresentDocCount());
			driver.waitForPageToBeReady();
			if (bc.text("ID000").isDisplayed()) {
				bc.passedStep("Exclude filter functionality by Comments is working as expected");
			} else {
				bc.failedStep("Exclude filter functionality by Comments is not working as expected.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while verifying documents after applying exclude functionality by Comments"
							+ e.getMessage());
		}
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude another filter for
	 *               EmailRecipientNames.
	 *
	 */
	public void performUpdateExculdeEmailFilter(String EmailRecipient) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getActiveFilter());
			bc.waitTillElemetToBeClickable(getActiveFilter());
			getActiveFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(EmailRecipient);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getUpdateFilter());
			bc.waitTillElemetToBeClickable(getUpdateFilter());
			getUpdateFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while performing exclude filter for EmailRecipientNames" + e.getMessage());
		}
	}

	/**
	 * @author Brundha.T
	 * @param fieldVal Description:Selecting documents in docExplorerpage
	 */
	public void SelectingAllDocuments(String fieldVal) {
		bc.waitForElement(getDocExp_SelectAllDocs());
		getDocExp_SelectAllDocs().waitAndClick(10);
		if (fieldVal.equalsIgnoreCase("yes")) {
			getYesRadioBtn().waitAndClick(10);
			bc.stepInfo("Documents from all pages are selected");
		}
		if (doclist.getPopUpOkBtn().isElementAvailable(10)) {
			doclist.getPopUpOkBtn().waitAndClick(10);
		}
		bc.stepInfo("Documents from first page is selected");
	}

	/**
	 * @author Vijaya.Rani
	 * @param folderName Description:bulkFolder Existing In UnFolder Docs
	 */
	public void bulkFolderExistingInUnFolderDocs(String folderName) throws InterruptedException {
		bc = new BaseClass(driver);
		bc.waitForElement(actionDropdown());
		actionDropdown().waitAndClick(5);

		bc.waitForElement(getBulkFolder());
		getBulkFolder().waitAndClick(5);
		System.out.println("Popup is displayed");

		bc.waitForElement(getDocExplorerUnFolder());
		getDocExplorerUnFolder().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderCheckBox(folderName).Visible();
			}
		}), Input.wait60);
		getBulkFolderCheckBox(folderName).waitAndClick(5);

		for (int i = 0; i < 10; i++) {
			try {
				bc.waitTime(4);
				bc.waitTillElemetToBeClickable(getContinueButton());
				getContinueButton().Click();
				break;
			} catch (Exception e) {
				bc.waitTillElemetToBeClickable(getContinueButton());
			}
		}
		System.out.println("Clicked continue");

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

		driver.getWebDriver().navigate().refresh();
	}

	/**
	 * @author: Arun Created Date: 27/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will select all return documents and perform action
	 *               as docview
	 */

	public void docExpToDocViewWithIngestion(String ingestionName, String value) {

		applyIngestionNameFilter("include", ingestionName);
		driver.waitForPageToBeReady();
		int count = getDocumentCountFromListView();
		bc.stepInfo("Docs available in doc explorer: " + count);
		// select all docs and view in docview
		SelectingAllDocuments(value);
		docExpViewInDocView();
		driver.waitForPageToBeReady();
		bc.waitForElementCollection(getNumberOfDocsCount());
		bc.verifyUrlLanding(Input.url + "en-us/DocumentViewer/DocView", " on doc View page", "Not on doc view page");
		int docsCount = getNumberOfDocsCount().size();
		bc.stepInfo("Docs available in docview: " + docsCount);
		if (count == docsCount) {
			bc.passedStep("All selected docs loaded in docview");
		} else {
			bc.failedStep("docs not loaded in docview");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify the user is on DocExp Page
	 */
	public void verifyUserIsOnDocExplorerPage() {

		driver.waitForPageToBeReady();
		bc.waitForElement(getDocExplorerPageHeader());
		String pageTitle = getDocExplorerPageHeader().getText();
		if (getDocExplorerPageHeader().isElementAvailable(5)) {
			bc.passedStep("The user is on " + pageTitle + " page");
		} else {
			bc.failedStep("The user is not on" + pageTitle + " page ");
		}
	}

	/**
	 * @author Brundha.T
	 * @param folderName
	 * @return
	 * @throws InterruptedException
	 * @Decription: method for new bulk folder
	 */
	public int newBulkFolder(String folderName) throws InterruptedException {
		bc.waitForElement(actionDropdown());
		actionDropdown().Click();

		getBulkFolder().waitAndClick(10);
		System.out.println("Clicked Bulk Folder");

		bc.waitForElement(getDocExplorer_NewFolder());
		getDocExplorer_NewFolder().waitAndClick(5);

		bc.waitForElement(getBulkFolder_SelectAllFolder());
		getBulkFolder_SelectAllFolder().waitAndClick(5);

		bc.waitForElement(getDocExplorer_NewFolderName());
		getDocExplorer_NewFolderName().SendKeys(folderName);

		for (int i = 0; i < 10; i++) {
			try {
				bc.waitTime(4);
				bc.waitTillElemetToBeClickable(getContinueButton());
				getContinueButton().Click();
				break;
			} catch (Exception e) {
				bc.waitTillElemetToBeClickable(getContinueButton());
			}
		}
		System.out.println("Clicked continue");

		driver.waitForPageToBeReady();
		SessionSearch session = new SessionSearch(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return session.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		int TotalCount = Integer.parseInt(session.getFinalCount().getText());
		getFinalizeButton().Click();
		driver.Manage().window().maximize();
		bc.VerifySuccessMessage("Records saved successfully");
		return TotalCount;
	}

	/**
	 * @author Brundha.T
	 * @param Index
	 * @return Description: getting the document count in doc explorer page
	 */
	public int DocviewDocCountByIndex(int Index) {
		String count1 = getDocExp_DocumentList_info().getText();
		String[] Doc1 = count1.split(" ");
		String DocCount = Doc1[Index];
		System.out.println(DocCount);
		return Integer.valueOf(DocCount);
	}

	/**
	 * @author :Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude filter for EmailRecipientNames
	 */
	public void performExculdeEmailRecipientNamesFilter(String EmailRecipient, String EmailRecipient1) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_EmailRecNameFilter());
			bc.waitTillElemetToBeClickable(getDocExp_EmailRecNameFilter());
			getDocExp_EmailRecNameFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(EmailRecipient);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			if (EmailRecipient1 != null) {
				bc.waitForElement(getExcludeRadioBtn());
				bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
				getExcludeRadioBtn().Click();
				getSearchTextArea().SendKeys(EmailRecipient1);
				Thread.sleep(Input.wait30 / 30);
				getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
				driver.waitForPageToBeReady();
				bc.waitForElement(getAddToFilter());
				bc.waitTillElemetToBeClickable(getAddToFilter());
				getAddToFilter().Click();
			}
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while performing exclude filter for EmailRecipientNames" + e.getMessage());
		}
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to verify documents after applying exclude functionality
	 *               by EmailRecipientNames.
	 * 
	 */
	public void verifyExcludeFunctionlityForEmailRecipientNames(String DocId) {

		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getPresentDocCount());
			bc.waitTillElemetToBeClickable(getPresentDocCount());
			driver.waitForPageToBeReady();
			if (bc.text(DocId).isDisplayed()) {
				bc.passedStep("Exclude filter functionality by EmailRecipientNames is working as expected");
			} else {
				bc.failedStep("Exclude filter functionality by EmailRecipientNames is not working as expected.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while verifying documents after applying exclude functionality by EmailRecipientNames"
							+ e.getMessage());
		}
	}

	/**
	 * @author: Vijaya.Rani Created Date: 14/11/2022 Modified by: NA Modified Date:
	 *          NA
	 * @description: this method will verify the CustodianName field values in doc
	 *               exp list view
	 */
	public void verifyCustodianNameValuesInDocExp(String[] custodname) {

		for (int j = 0; j < custodname.length; j++) {

			bc.waitForElement(getDocExp_CustodianNameSearchName("CUSTODIANNAME"));
			getDocExp_CustodianNameSearchName("CUSTODIANNAME").SendKeys(custodname[j]);
			getDocExp_CustodianNameSearchName("CUSTODIANNAME").SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.validatingGetTextElement(getCustodianNameValues(), custodname[j]);
			driver.Navigate().refresh();
		}
	}
	/**
	 * @author :Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method for performing exclude filter for EmailRecipientNames
	 */
	public void performExculdeEmailRecipientNamesFilter(String EmailRecipient) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			bc.waitForElement(getDocExp_EmailRecNameFilter());
			bc.waitTillElemetToBeClickable(getDocExp_EmailRecNameFilter());
			getDocExp_EmailRecNameFilter().Click();
			bc.waitForElement(getExcludeRadioBtn());
			bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
			getSearchTextArea().SendKeys(EmailRecipient);
			Thread.sleep(Input.wait30 / 30);
			getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.waitForElement(getAddToFilter());
			bc.waitTillElemetToBeClickable(getAddToFilter());
			getAddToFilter().Click();
			bc.waitForElement(getApplyFilter());
			bc.waitTillElemetToBeClickable(getApplyFilter());
			getApplyFilter().Click();
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while performing exclude filter for EmailRecipientNames" + e.getMessage());
		}
	}
	
	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA Modified by:NA.
	 * @Description: Method to verify documents after applying exclude functionality
	 *               by EmailRecipientNames.
	 * 
	 */
	public void verifyExcludeFunctionlityForEmailRecipientNames() {

		try {
			driver.waitForPageToBeReady();
			bc.waitForElement(getPresentDocCount());
			bc.waitTillElemetToBeClickable(getPresentDocCount());
			driver.waitForPageToBeReady();
			if (bc.text("ID000").isDisplayed()) {
				bc.passedStep("Exclude filter functionality by EmailRecipientNames is working as expected");
			} else {
				bc.failedStep("Exclude filter functionality by EmailRecipientNames is not working as expected.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occcured while verifying documents after applying exclude functionality by EmailRecipientNames"
							+ e.getMessage());
		}
	}
	
	/**
	 * @author: Vijaya.Rani Created Date: 14/11/2022 Modified by: NA Modified Date:
	 *          NA
	 * @description: this method will verify the EmailAuthor field values
	 *               in doc exp list view
	 */
	public void verifyEmailAuthorValuesInDocExp(String[] emailAuthor) {

		for (int j = 0; j < emailAuthor.length; j++) {

			bc.waitForElement(getDocExp_EmailAuthorSearchName("EMAILAUTHOR"));
			getDocExp_EmailAuthorSearchName("EMAILAUTHOR").SendKeys(emailAuthor[j]);
			getDocExp_EmailAuthorSearchName("EMAILAUTHOR").SendKeysNoClear("" + Keys.ENTER);
			driver.waitForPageToBeReady();
			bc.validatingGetTextElement(getEmailAuthorValues(), emailAuthor[j]);
			driver.Navigate().refresh();
		}
	}
	
	/**
	 * @author Vijaya.rani
	 * @Description :suffling and verifying it in descending order
	 */
	public void verifyingDescendingOrderInColumn() {
		try {
			
			driver.waitForPageToBeReady();
			getDocExMasterDate().waitAndClick(5);
			List<WebElement> RowCount = getMasterDateAsc().FindWebElements();
			bc.waitTime(5);
			List<String> Rowcounts = new ArrayList<String>();
			List<String> sortValue = new ArrayList<String>();
			bc.waitTime(3);
			for (int j = 1; j <RowCount.size(); j++) {
				driver.waitForPageToBeReady();
				String row = RowCount.get(j).getText();
				String nocounts[] = row.split("/");
				bc.waitTime(2);
				String output = nocounts[0];
				Rowcounts.add(output);
			}
			bc.waitTime(5);
			System.out.println("master date values" + Rowcounts);
			Collections.sort(Rowcounts);
			sortValue.addAll(Rowcounts);
			System.out.println(sortValue);
			
			bc.waitTime(3);
			if (Rowcounts.equals(sortValue)) {
				bc.passedStep("" + Rowcounts + " and " + sortValue + "is in descending order as expected");
			} else {
				bc.failedStep("" + Rowcounts + "and " + sortValue + " is not in descending order as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

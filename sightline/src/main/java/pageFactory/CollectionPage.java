package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class CollectionPage {
	Driver driver;
	BaseClass base;

	// Added BY Jeevitha

	public Element getDatasetTableValue(String value) {
		return driver.FindElementByXPath("//table[@id='dtDatasetList']//td//div[text()='" + value + "']");
	}

	public Element getPopupMsg() {
		return driver.FindElementByXPath("//div[@class='MessageBoxMiddle']");
	}

	public Element getNewCollectionBtn() {
		return driver.FindElementByXPath("//input[@id='btnNewCollection']");
	}

	public Element getNewCollPageHeader() {
		return driver.FindElementByXPath("//div[@class='row prod']//h2");
	}

	public Element getAddNewSource() {
		return driver.FindElementByXPath("//a[@id='btnAddNewSourceLocation']");
	}

	public Element getAddNewSourcePopUp() {
		return driver.FindElementByXPath("//div[@class='ui-widget-overlay ui-front']");
	}

	public ElementCollection getAddNewSourcePopUp_Attributes() {
		return driver.FindElementsByXPath(
				"//div[@class='ui-widget-overlay ui-front']//preceding::label[contains(@class,'labelAlign')]");
	}

	public Element getDataSourceName() {
		return driver.FindElementByXPath(
				"//div[@class='ui-widget-overlay ui-front']//preceding::input[@id='txtSourceName']");
	}

	public Element getTenantID() {
		return driver
				.FindElementByXPath("//div[@class='ui-widget-overlay ui-front']//preceding::input[@id='txtTenantID']");
	}

	public Element getApplicationID() {
		return driver.FindElementByXPath(
				"//div[@class='ui-widget-overlay ui-front']//preceding::input[@id='txtApplicationID']");
	}

	public Element getAppSecretKey() {
		return driver.FindElementByXPath(
				"//div[@class='ui-widget-overlay ui-front']//preceding::input[@id='txtClientSecret']");
	}

	public Element getAddNewPopup_SaveBtn() {
		return driver.FindElementByXPath(
				"//div[@class='ui-widget-overlay ui-front']//preceding::input[@id='btnSaveSourceLocation']");
	}

	public Element getErrroMsg(int i) {
		return driver.FindElementByXPath("(//label[@class='input']//font)[" + i + "]");
	}

	public Element getDataSrcType() {
		return driver.FindElementByXPath(
				"//div[@class='ui-widget-overlay ui-front']//preceding::select[@id='ddlSourceType']//option");
	}

	public Element getSourceLocation(String srceLoc) {
		return driver.FindElementByXPath(
				"//div[@id='divSourceLocations']//div[@class='popout text-wrap' and (contains(text(),'" + srceLoc
						+ "'))]");
	}

	public Element getCollectioName() {
		return driver.FindElementByXPath("//input[@id='txtCollectionName']");
	}

	public Element getNextBtn() {
		return driver.FindElementByXPath("//a[@id='btnCollectionInfoNext']");
	}

	public Element getNextBtnDS() {
		return driver.FindElementByXPath("//a[@id='btnDatasetSelectionNext']");
	}

	public Element getCollectErrorMsg() {
		return driver.FindElementByXPath("//span[@id='txtCollectionName-error']");
	}

	public ElementCollection getListOfSrcLoc() {
		return driver.FindElementsByXPath("//div[@id='divSourceLocations']//div[@class='popout text-wrap']");
	}

	public Element getAutomaticInitiate_Btn() {
		return driver.FindElementByXPath("//label[@id='BoxAuto']");
	}

	public Element getDonotAutomaticInitiate_Btn() {
		return driver.FindElementByXPath("//label[@id='BoxManual']");
	}

	public Element getCollectionAction(String collectionName) {
		return driver.FindElementByXPath(
				"//div[text()='" + collectionName + "']//..//following::td//div//a[text()='Actions ']");
	}

	public Element getCollectionActionList(String collectionName, String actionType) {
		return driver.FindElementByXPath(
				"//div[text()='" + collectionName + "']//..//following::td//div//a[text()='" + actionType + "']");
	}

	// Added by Mohan
	public Element getLoadingFoldersIcon() {
		return driver.FindElementByXPath("//span[contains(text(),'Loading folders')]");
	}

	public Element getCollectionListFieldValueRunByAndSourceLocationText(int rowNo) {
		return driver.FindElementByXPath("//*[@id='dtCollectionList']//tbody//tr[1]//td[" + rowNo + "]");
	}

	public Element getCollectionListDatasetValueCollectionName() {
		return driver.FindElementByXPath("//*[@id='dtCollectionList']//tbody//tr[1]//td[2]//div");
	}

	public Element getCollectionListDatasetValue(int rowNo) {
		return driver.FindElementByXPath("//*[@id='dtCollectionList']//tbody//tr[1]//td[" + rowNo + "]");
	}

	public Element getCollectionListDataset(int rowNo) {
		return driver.FindElementByXPath("//*[@id='dtCollectionList']//tbody//tr[1]//td[" + rowNo + "]//a");
	}

	public Element getCollectionHeaderList(int rowNo) {
		return driver.FindElementByXPath("//div[@class='dataTables_scrollHeadInner']//th[" + rowNo + "]");
	}

	public ElementCollection getCollectionListHeaderFields() {
		return driver.FindElementsByXPath("//*[@class='table dataTable no-footer']//thead//tr//th");
	}

	public Element getDatasetPopUpSaveButton() {
		return driver.FindElementByXPath("//input[@id='btnSaveDatasetSelection' and @disabled]");
	}

	public Element getDatasetPopUpSaveAndAddNewDatasetButton() {
		return driver.FindElementByXPath("//input[@id='btnSaveAndAddNewDataset' and @disabled]");
	}

	public Element getDatasetPopUpCancelDatasetButton() {
		return driver.FindElementByXPath("//input[@id='btnCancelDatasetSelection']");
	}

	public Element getLoadingFolderInDataset() {
		return driver.FindElementByXPath("//span[text()='Loading folders']");
	}

	public Element getCustodianNameErrorMsg() {
		return driver.FindElementByXPath("//span[@id='spanCustodianName']");
	}

	public Element getDatasetNameErrorMsg() {
		return driver.FindElementByXPath("//span[@id='spanDatasetName']");
	}

	public Element getFolderErrorMsg() {
		return driver.FindElementByXPath("//span[@id='spanSelectFolders']");
	}

	public Element getFilterButton() {
		return driver.FindElementByXPath("//a[@id='ancFilterStart']");
	}

	public Element getFilteEnablerButton() {
		return driver.FindElementByXPath("//i[@id='IsEnableFilter']");
	}

	public Element getDateRangeFilterErrorMsg() {
		return driver.FindElementByXPath("//span[text()='Please enter a valid date range.']");
	}

	public Element getKeyboardFilterButton() {
		return driver.FindElementByXPath("//label[@for='rbtnKeywords']");
	}

	public Element getKeyboardFilterNoteErrorMsg() {
		return driver.FindElementByXPath("//span[@id='spanWarningKeywordFilters']");
	}

	public Element getKeyboardFilterErrorMsg() {
		return driver.FindElementByXPath("//span[@id='spanSelectFilters']");
	}

	public Element getDatasetsListEditButton() {
		return driver.FindElementByXPath("//a[text()='Edit']");
	}

	public Element getFolderCompletedButton() {
		return driver.FindElementByXPath("//a[@id='ancFolderCompleted']");
	}

	public Element getRefreshButtonInSelectFolderField() {
		return driver.FindElementByXPath("//a[@id='refreshFolderList']");
	}

	public Element getDatasetHomePageNoCutodainAvailableText() {
		return driver.FindElementByXPath("//td[text()='No dataset has been added yet']");
	}

	public Element getDatasetPageAddDatasetLinkButton() {
		return driver.FindElementByXPath("//a[text()='Add datasets']");
	}

	public ElementCollection getDatasetHomePageCustodianList() {
		return driver.FindElementsByXPath("//*[@id='dtDatasetList']//tbody//tr");
	}

	public Element getDatasetPageCustodianDeleteButton() {
		return driver.FindElementByXPath("//*[@id='dtDatasetList']//tbody//tr[1]//a[text()='Delete']");
	}

	public Element getDataSourceType() {
		return driver.FindElementByXPath("//select[@id='ddlSourceType']");
	}

	public Element getStartALinkCollection() {
		return driver.FindElementByXPath("//a[text()='Start a collection']");
	}

	public ElementCollection getCollectionPageFirstRowValue() {
		return driver.FindElementsByXPath(
				"//div[@class='dataTables_scrollHeadInner']//tr//th[@aria-controls=\"dtCollectionList\"]");
	}

	// Added by Raghuram
	public Element getDestinationPathLocation(String nameAtttribute) {
		return driver.FindElementByXPath("//div[text()='" + nameAtttribute + "']//..//div[@class='popout text-wrap']");
	}

	public Element getPopUpStatusShowDetailsLink(String columnName, String status) {
		return driver.FindElementByXPath("//td[contains(text(),'" + columnName
				+ "')]//..//span[text()[normalize-space()='" + status + "']]//a[text()='Show Details']");
	}

	public Element getPopUpStatus(String emailID, String status, int index) {
		return driver.FindElementByXPath(
				"//div[text()='" + emailID + "']//..//..//td[" + index + "]//p[text()='" + status + "']");
	}

	public ElementCollection getErrHeaderTable() {
		return driver.FindElementsByXPath("//table[@id='dtFinalError']//th");
	}

	public Element getCollectionInfoPopUpStatus() {
		return driver.FindElementByXPath("//div[@role='dialog' and contains(@style,'display')]");
	}

	public Element getDestinationPathLocation() {
		return driver.FindElementByXPath("//label[text()='Destination Path:']//..//..//div[@class='col-md-9']");
	}

	public Element getCollectionIdFromInfoPopup() {
		return driver.FindElementByXPath("//p[text()='CollectionId:']//..//..//div[@class='col-sm-9']");
	}

	public Element getSourceLocationFromInfoPopup() {
		return driver.FindElementByXPath("//p[text()='Source Location']//..//..//div[@class='col-sm-9']//p");
	}

	public Element getCollectionNameFromInfoPopup() {
		return driver.FindElementByXPath("//p[text()='Collection Name:']//..//..//div[@class='col-sm-9']");
	}

	public Element getDestinationLocationInfoPopup() {
		return driver.FindElementByXPath("//p[text()='Destination Path:']//..//..//div[@id='divDestinationPath']//p");
	}

	public Element getInitiateStatInfoPopup() {
		return driver.FindElementByXPath("//p[text()='Initiate Processing:']//..//..//div[@class='col-sm-7']//p");
	}

	public ElementCollection getCollectionDataSetDetailsHeader() {
		return driver.FindElementsByXPath(
				"//div[@aria-describedby=\"divCollectionDetails\"]//div[@id='divCollectionDetails']//div[@id='divCollectionDatasetTitle']//..//div[@class='dataTables_scrollHead']//table[@class='table dataTable no-footer']//tr//th");
	}

	public Element getCollectionStatsDiv(String collectionName, int index) {
		return driver.FindElementByXPath("//div[text()='" + collectionName + "']//..//..//td[" + index + "]//div");
	}

	public Element getCollectionProgressBar(String collectionName, int index) {
		return driver.FindElementByXPath("//div[text()='" + collectionName + "']//..//..//td[" + index
				+ "]//div[@class='progressbar-grey progressbar-round']");
	}

	public Element getCollectionPauseStats(String collectionName, int index) {
		return driver.FindElementByXPath("//div[text()='" + collectionName + "']//..//..//td[" + index
				+ "]//div[@class='col-md-5 progressbar-grey-text']");
	}

	public Element getStartALinkCollection(String nameSrc) {
		return driver.FindElementByXPath(
				"//div[@id='divSourceLocations']//div[@class='popout text-wrap']//..//div[@data-content='(" + nameSrc
						+ ")']");
	}

	public Element getAddDataSetBtn() {
		return driver.FindElementByXPath("//a[@id='btnNewDataset']");
	}

	public Element getCustodianIDInputTextField() {
		return driver.FindElementByXPath("//input[@id='txtCustodianName']");
	}

	public Element selectId() {
		return driver.FindElementByXPath(
				"//dataList[@id='datalistCustodianName']//option[@data-useremail='Raghuram.A@consiliotest.com']");
	}

	public ElementCollection getlistOfAutoSugg() {
		return driver.FindElementsByXPath("(//datalist[@id='datalistCustodianName']//option)");
	}

	public Element getSpecificAutoSugg(int i) {
		return driver.FindElementByXPath("(//datalist[@id='datalistCustodianName']//option)[" + i + "]");
	}

	public Element getDataSetNameTextFIeld() {
		return driver.FindElementByXPath("//input[@id='txtDatasetName']");
	}

	public Element getCollectionID() {
		return driver.FindElementByXPath("//label[text()='Collection ID: ']//..//..//div[@class='col-md-9']");
	}

	public Element getFolderabLabel() {
		return driver.FindElementByXPath("//a[@id='ancFolderLabel']");
	}

	public Element getFolderNameToSelect(String type) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor' and text()='" + type + "']");
	}
	
	public Element getSubFolderNameToSelect(String Parentfolder,String Childfolder) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor' and text()='" + Parentfolder +"']/following-sibling::ul/li/a[contains(.,'" + Childfolder + "')]");
	}
	
	public Element getFolderTree(String type) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor' and text()='" + type + "']/preceding-sibling::i");
	}

	public Element getActionBtn(String type) {
		return driver.FindElementByXPath("//input[@value='" + type + "']");
	}

	public Element getFolderSelectionConfirmation() {
		return driver.FindElementByXPath("//span[text()='Folders Selected for Collection']");
	}

	public Element getConfirmationBtnAction(String actionType) {
		return driver.FindElementByXPath("//button[text()=' " + actionType + "']");
	}

	public Element getDataSetDetails(String name, int index) {
		return driver.FindElementByXPath("//div[text()='" + name + "']//..//..//td[" + index + "]");
	}

//	public Element getDataSetDetails(String name, int index) {
//		return driver.FindElementByXPath(
//				"//table[@id='dtDatasetList']//div[text()='" + name + "']//..//..//td[" + index + "]");
//	}

	public ElementCollection getDataSetDetailsHeader() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHeadInner']//th");
	}

	public Element getActionDiv(String action) {
		return driver.FindElementByXPath("//a[text()='" + action + "']");
	}

	public Element getCollectionsPageAction(String collectionId) {
		return driver.FindElementByXPath("//a[text()='" + collectionId + "']//..//..//..//td//a[text()='Actions ']");
	}

	public Element getCollectionsPageActionList(String collectionId, String actionType) {
		return driver.FindElementByXPath(
				"//a[text()='" + collectionId + "']//..//..//..//td//a[text()='" + actionType + "']");
	}

	public Element getDisabledBackBtn() {
		return driver.FindElementByXPath("//a[text()='Back' and @disabled='disabled']");
	}

	public Element getCollectionNameElement(String collectionName) {
		return driver.FindElementByXPath("//table[@id='dtCollectionList']//td//div[text()='" + collectionName + "']");
	}

	public ElementCollection getCollectionNameElements(int index) {
		return driver.FindElementsByXPath("//table[@id='dtCollectionList']//td[" + index + "]");
	}

	public ElementCollection getCollectionNameStatusElements(String status) {
		return driver.FindElementsByXPath("//table[@id='dtCollectionList']//td//b[text()='" + status + "']");
	}

	public Element getCollectionNameBasedOnStatusElements(String status, int index) {
		return driver.FindElementByXPath("(//table[@id='dtCollectionList']//td//b[text()='" + status
				+ "']//..//..//..//td[" + index + "]//div)[last()]");
	}

	public Element getCollectionPageFirstCollectionSelect() {
		return driver.FindElementByXPath("(//div[@id='divSourceLocations']//div[@class='popout text-wrap'])[last()]");
	}

	// added by jeevitha

	public Element getPopupRanByDetail() {
		return driver.FindElementByXPath("//p[text()='Collection Ran By:']//parent::div//following-sibling::div//p");
	}

	public Element getNameBasedOnCollectionName(String collectionName, String value) {
		return driver.FindElementByXPath(
				"//div[text()='" + collectionName + "']//..//parent::tr//td[text()='" + value + "']");
	}

	public ElementCollection getAttributesOfSummaryTab() {
		return driver.FindElementsByXPath("//div[@class='row rowPadding']//div[@class='col-md-3']");
	}

	public Element getHeaderBtn(String headerName) {
		return driver.FindElementByXPath("//th[text()='" + headerName + "']");
	}

	public Element getStartBtn() {
		return driver.FindElementByXPath("//a[@id='btnStartCollection']");
	}

	public Element getViewDatsetBtn(String collectionName) {
		return driver.FindElementByXPath("//div[text()='" + collectionName
				+ "']//parent::td//following-sibling::td//div//a[text()='View Datasets']");
	}

	public Element getCollectionNameBasedOnStatus(String status, int index) {
		return driver.FindElementByXPath("(//table[@id='dtCollectionList']//td//b[contains(text(),'" + status
				+ "')]//..//..//..//td[" + index + "]//div)[last()]");
	}

	public ElementCollection getCollectionNameStatus(String status) {
		return driver.FindElementsByXPath("//table[@id='dtCollectionList']//td//b[contains(text(),'" + status + "')]");
	}

	public Element getDataSetTableValue(String name, int index) {
		return driver.FindElementByXPath("//div[text()='" + name + "']//..//..//td[" + index + "]//div");
	}

	public Element getMouseHoverDetails(int index) {
		return driver.FindElementByXPath("//td[" + index + "]//div[contains(@aria-describedby,'popover')]");
	}

	public Element getCickedFolder(String folderType) {
		return driver.FindElementByXPath("//a[contains(@class,'clicked') and text()='" + folderType + "']");
	}

	public Element getCustodianLabel() {
		return driver.FindElementByXPath("//a[@id='ancCustodianLabel']");
	}

	public ElementCollection getCompletedTagOfPopup() {
		return driver.FindElementsByXPath("//a[text()='Completed']//preceding-sibling::a[@class='label-bold']");
	}

	public Element getCollectioNameOnPopup(String collectionName) {
		return driver.FindElementByXPath("//label[contains(text(),'" + collectionName + "')]");
	}

	public Element getDataSetPopup() {
		return driver.FindElementByXPath("//div[@class='ui-widget-overlay ui-front']");
	}

	public Element getEditBtnDataSelection(String custodianMailId) {
		return driver.FindElementByXPath(
				"//div[text()='" + custodianMailId + "']//parent::td//following-sibling::td//a[text()='Edit']");
	}

	public Element getApplyFilterLink() {
		return driver.FindElementByXPath("//a[@id='ancFilterLabel']");
	}

	public Element getEnableFilterStatus() {
		return driver.FindElementByXPath("//div[@id='divSelectFilters']");
	}

	public Element getEnableFilterToggle() {
		return driver.FindElementByXPath("//i[@id='IsEnableFilter']");
	}

	public Element getKeywordToggle() {
		return driver.FindElementByXPath("//input[@id='rbtnKeywords']");
	}

	public Element getKeywordTextBox() {
		return driver.FindElementByXPath("//input[@id='txtKeywords']");
	}

	public Element getDataSetSelectionPopDisplay() {
		return driver.FindElementByXPath("//div[@id='divDatasetSelectionPopup']");
	}

	public Element getExpCollectionStatus(String collectionName, String expStatus) {
		return driver.FindElementByXPath(
				"//div[text()='" + collectionName + "']//..//..//td//b[text()='" + expStatus + "']");
	}

	public Element getClickDownloadReport(String collectionName, int index) {
		return driver.FindElementByXPath("//div[text()='" + collectionName + "']//..//..//td[" + index + "]//a");
	}

	public Element getDataSelectionAction(String custodianMailId, String action) {
		return driver.FindElementByXPath("//div[text()='" + custodianMailId
				+ "']//parent::td//following-sibling::td//a[text()='" + action + "']");
	}

	public Element getDataSetDeletionRibbon() {
		return driver.FindElementByXPath("//span[text()='Delete Dataset']");
	}

	public ElementCollection getCollectionListInCollectionHomePage() {
		return driver.FindElementsByXPath("//table[@id='dtCollectionList']//tbody//tr");
	}

	public Element getCollectionFieldValuesInCollectionHomePage(int rowNo, int tableRow) {
		return driver.FindElementByXPath(
				"//table[@id='dtCollectionList']//tbody//tr[" + tableRow + "]/td[" + rowNo + "]//b");
	}

	public Element getCollectionFieldValuesRowNoInCollectionHomePage(int rowNo, int tableRow) {
		return driver.FindElementByXPath(
				"//table[@id='dtCollectionList']//tbody//tr[" + tableRow + "]//td[" + rowNo + "]//a");
	}

	public Element getCollectionRetrievedCountTextFromCollectionIdsList() {
		return driver
				.FindElementByXPath("//table[@class='table dataTable no-footer']//th[text()='Total Retrieved Count']");
	}

	public Element getCollectionRetrievedCountFromCollectionIdsList() {
		return driver.FindElementByXPath(
				"(//table[@class='table dataTable no-footer']//td//div[@class='text-wrap popout'])[last()]");
	}

	public Element getErrorReportPageShowDetailsButton() {
		return driver.FindElementByXPath("//a[text()='Show Details']");
	}

	public Element getErrorReportPageRetrievalTable() {
		return driver.FindElementByXPath("//table[@id='dtRetrieval']");
	}

	public Element getErrorReportPageRetrievalTableErrorCountNum() {
		return driver.FindElementByXPath("//table[@id='dtRetrieval']//td[4]//p");
	}

	public Element getErrorReportPageRetrievalTableErrorCount() {
		return driver.FindElementByXPath("//table[@id='dtRetrieval']//th[text()='Error Count']");
	}

	public Element getStartCollectionButton() {
		return driver.FindElementByXPath("//a[@id='btnStartCollection']");
	}

	public Element getRibbonHeader(String text) {
		return driver.FindElementByXPath("//span[text()='" + text + "']");
	}

	public Element getProgressBarStats(String collectionName, int index) {
		return driver.FindElementByXPath("//div[text()='" + collectionName + "']//..//..//td[" + index
				+ "]//div[@class='col-md-5 progressbar-blue-text']");
	}

	public ElementCollection getCollectionDatas() {
		return driver.FindElementsByXPath("//table[@id='dtCollectionList']//tr");
	}

	public Element getBackBtn() {
		return driver.FindElementByXPath("//a[text()='Back']");
	}

	public CollectionPage(Driver driver) {
		this.driver = driver;
		base = new BaseClass(driver);
	}

	/**
	 * @Author Jeevitha
	 * @Description : click Create ne collection Btn and verify whetehr it is landed
	 *              on new collection page
	 */
	public void performCreateNewCollection() {

		base.waitForElement(getNewCollectionBtn());
		getNewCollectionBtn().waitAndClick(10);
		base.stepInfo("Clicked create new collection button");

		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		String expectedUrl = Input.url + "en-us/Collection/NewCollection";
		base.textCompareEquals(currentUrl, expectedUrl, "Landed In New Collection Page", "Landed Page : " + currentUrl);

		verifyCurrentTab("Source Selection");

	}

	/**
	 * @Author Jeevitha
	 * @Description : verify Attributes of Add new soucre Location Popup
	 *
	 */
	public void verifyAddNewSourcePopupAttributes() {
		String attributeSet[] = { "Data Source Type: *", "Data Source Name: *", "Tenant ID: *", "Application ID: *",
				"Application Secret Key: *" };
		List<String> actualList = new ArrayList<String>();

		base.waitForElement(getAddNewSource());
		getAddNewSource().waitAndClick(10);
		base.stepInfo("Clicked Add new source location button");

		if (getAddNewSourcePopUp().isElementAvailable(10)) {
			base.passedStep("Add New Source location Popup is opened");
			base.waitForElementCollection(getAddNewSourcePopUp_Attributes());
			actualList = base.availableListofElements(getAddNewSourcePopUp_Attributes());

			String passMsg = "Available Attributes In Add New Source Location Popup : " + actualList;
			String failMsg = "Expected Attributes Are not Present";
			base.compareArraywithDataList(attributeSet, actualList, true, passMsg, failMsg);

		} else {
			base.failedStep("Add New Source Location Popup is Not Present");
		}

	}

	/**
	 * @Author Jeevitha
	 * @Description : enter Attribute values and verify errro message
	 * @param srceTyp  : source type value
	 * @param srceName : source name value
	 * @param tentID   : tentent id value
	 * @param appID    : application id value
	 * @param secrtKey : secret key value
	 */
	public void performAddNewSource(String srceTyp, String srceName, String tentID, String appID, String secrtKey) {
		SourceLocationPage source = new SourceLocationPage(driver);
		if (getAddNewSource().isElementAvailable(10)) {
			getAddNewSource().waitAndClick(10);
			base.stepInfo("Clicked Add new source location button on collection page");
		} else if (source.getAddNewSrcLoction().isElementAvailable(10)) {
			source.getAddNewSrcLoction().waitAndClick(10);
			base.stepInfo("Clicked Add new source location button on Source location Page");
		}

		if (getAddNewSourcePopUp().isElementAvailable(10)) {
			base.passedStep("Add New Source location Popup is opened");

			// data source type
			base.waitForElement(getDataSrcType());
			String actualType = getDataSrcType().getText();
			String expectedType = "Microsoft 365";
			base.textCompareEquals(actualType, expectedType, "Data Source type : " + actualType,
					"Data Source type is not as expected");

			// data source Name
			getDataSourceName().waitAndClick(5);
			getDataSourceName().SendKeys(srceName);
			base.stepInfo("Entered Data source Name : " + srceName);
			// tenant ID
			getTenantID().waitAndClick(5);
			getTenantID().Clear();
			getTenantID().SendKeys(tentID);
			base.stepInfo("Entered Tentant ID : " + tentID);
			// Application ID
			getApplicationID().waitAndClick(5);
			getApplicationID().SendKeys(appID);
			base.stepInfo("Entered Application ID : " + appID);
			// Application secret key
			getAppSecretKey().waitAndClick(5);
			getAppSecretKey().Clear();
			getAppSecretKey().SendKeys(secrtKey);
			base.stepInfo("Entered Secret Key : " + secrtKey);

		} else {
			base.failedStep("Add New Source Location Popup is Not Present");
		}

		base.waitTillElemetToBeClickable(getAddNewPopup_SaveBtn());
		getAddNewPopup_SaveBtn().waitAndClick(10);
		base.stepInfo("Clicked Save Button");

		if (srceName.equals("") && tentID.equals("") && appID.equals("") && secrtKey.equals("")) {

			for (int i = 1; i < 5; i++) {
				base.waitForElement(getErrroMsg(i));
				String errorMsg = getErrroMsg(i).getText();
				if (errorMsg.equals("")) {
					base.failedStep("Expected Error Message is Not Displayed");
				} else {
					System.out.println("Error : " + errorMsg);
					base.passedStep("Dispalyed Error Msg : " + errorMsg);
				}
			}

		} else {
			base.VerifySuccessMessage("Source Location added successfully");
			base.CloseSuccessMsgpopup();
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify added source location is displayed on source location
	 *              tab and verify available source locations in project
	 * @param srceLocation
	 * @throws InterruptedException
	 */
	public void verifyAddedSourceLocation(String srceLocation, List<String> listOfSrcLoc) throws InterruptedException {
		List<String> availSrcLoc = new ArrayList<String>();

		verifyCurrentTab("Source Selection");

		if (getSourceLocation(srceLocation).isElementAvailable(10)) {
			base.passedStep(srceLocation + " : is displayed on Source Location Tab");
		} else if (!listOfSrcLoc.equals(null)) {
			availSrcLoc = base.availableListofElements(getListOfSrcLoc());
			String passMsg = "Displayed Source Location is : " + availSrcLoc;
			String failMsg = "Source location list is not as expected";
			base.listCompareEquals(listOfSrcLoc, availSrcLoc, passMsg, failMsg);

		} else {
			base.failedStep(srceLocation + ": is not Displayed ");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify Collection information Page after clicking specified
	 *              source location
	 * @param expectedSrc
	 * @param collectionName
	 * @param Next
	 * @modifiedIn : 11/18/22
	 * @Modifiedby : Raghuram
	 */
	public HashMap<String, String> verifyCollectionInfoPage(String srceLocation, String collectionName, boolean Next) {
		HashMap<String, String> colllectionData = new HashMap<>();

		base.waitForElement(getSourceLocation(srceLocation));
		getSourceLocation(srceLocation).waitAndClick(10);
		base.stepInfo("Clicked on :" + srceLocation + " source location");

		base.waitForElement(getCollectioName());
		verifyCurrentTab("Collection Information");

		if (getCollectioName().isElementAvailable(3)) {
			getCollectioName().waitAndClick(5);
			getCollectioName().SendKeys(collectionName);
			base.passedStep("Collection Name Field Is displayed");

			// Verifying Entered collection name is displayed as expected
			String displayedCname = driver
					.findAttributeValueViaJS("return document.querySelector('#txtCollectionName').value");
			base.textCompareEquals(displayedCname, collectionName, "Entered collection name is displayed",
					"Collection name mis-match");

		} else {
			base.failedStep("Collection Name Field is Not displayed");
		}

		// Get Collection ID
		String collectionID = getCollectionID().getText();
		colllectionData.put(collectionName, collectionID);

		// Get Destination Path - latest
		String destinationPath = getDestinationPathLocation().getText();
		colllectionData.put("DestinationPath", destinationPath);

		if (Next) {
			getNextBtn().waitAndClick(10);
			base.stepInfo("Clicked Next Button");

			if (collectionName.equals("")) {
				if (getCollectErrorMsg().isElementAvailable(4)) {
					String actualMsg = getCollectErrorMsg().getText();
					String expectedMsg = "Collection Name is required.";
					String passMsg = "Error Message : " + actualMsg;
					base.textCompareEquals(actualMsg, expectedMsg, passMsg, "Error Message is not as expected");
				} else {
					base.failedStep("Error message is not displayed");
				}
			}
		}
		return colllectionData;
	}

	/**
	 * @author Jeevitha
	 * @Description : verify the current tab header
	 * @param expectedTab
	 */
	public void verifyCurrentTab(String expectedTab) {
		driver.waitForPageToBeReady();
		String actualTab = getNewCollPageHeader().getText();
		base.compareTextViaContains(actualTab, expectedTab, "Navigated Tab is : " + actualTab,
				"Navigated Tab is : " + actualTab);
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: Verify Collection Page
	 */
	public void verifyCollectionPage() {

		driver.waitForPageToBeReady();
		getCollectionPageFirstRowValue().isElementAvailable(5);
		ElementCollection collection = getCollectionPageFirstRowValue();
		int collectionPage = collection.size();
		System.out.println(collectionPage);

		if (collectionPage > 3) {
			base.passedStep("Manage Collections screen is opened successfully");

		} else {
			base.failedStep("Manage Collections screen is not opened");
		}

	}

	/**
	 * @author Mohan
	 * @createdDate : 7/27/22
	 */
	public String selectSourceFromTheListAvailable() {

		driver.waitForPageToBeReady();
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();

		if (getCollectionPageFirstCollectionSelect().isElementAvailable(5)) {
			base.waitForElement(getCollectionPageFirstCollectionSelect());
			dataSourceName = getCollectionPageFirstCollectionSelect().GetAttribute("data-content");

		} else {
			driver.waitForPageToBeReady();
			performAddNewSource(null, dataSourceName, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);
		}

		return dataSourceName;
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @param expectedMailId
	 * @return
	 */
	public int getIndexOfAutoSuggestion(String expectedMailId) {
		System.out.println(expectedMailId);
		base.waitForElementCollection(getlistOfAutoSugg());
		int size = getlistOfAutoSugg().size();
		System.out.println(size);
		for (int i = 1; i <= size; i++) {
			String actualmailId = getSpecificAutoSugg(i).GetAttribute("data-useremail");
			if (!actualmailId.equalsIgnoreCase("")) {
				System.out.println("Actual :" + actualmailId);
				if (actualmailId.equalsIgnoreCase(expectedMailId)) {
					size = i;
					break;
				}
			}
		}
		return size;
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @param type
	 */
	public void addNewDataSetCLick(String type) {
		try {
			if (type.equalsIgnoreCase("Button")) {
				getAddDataSetBtn().waitAndClick(5);
			} else if (type.equalsIgnoreCase("Link")) {

			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Error in initiating dataSets");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @param inputString
	 * @param inputEmailId
	 * @param verifyRetrivedData
	 * @param additionl1
	 * @param additional2
	 * @return
	 */
	public String custodianNameSelectionInNewDataSet(String inputString, String inputEmailId,
			Boolean verifyRetrivedData, Boolean additionl1, String additional2) {

		// Custodian Nmae input
		driver.waitForPageToBeReady();
		getCustodianIDInputTextField().waitAndClick(5);
		base.waitTime(2);
		getCustodianIDInputTextField().SendKeys(inputString);
		base.waitTime(2); // To handle abnormal waits
System.out.println("inputEmailId :-"+inputEmailId);
		// Get Auto suggesstions list size
		int size = getIndexOfAutoSuggestion(inputEmailId);
		System.out.println(size);

		// ID selection Iteration
		for (int i = 1; i <= size; i++) {
			// Hit DOWN arrow key for selection
			base.hitKey(KeyEvent.VK_DOWN);
			System.out.println(i);
		}

		// Hit ENTER key for selection
		base.waitTime(3);
		base.hitKey(KeyEvent.VK_ENTER);
		driver.waitForPageToBeReady();

		// Custodian Retived data
		base.waitTime(2);
		String retrivedData = getDataSetNameTextFIeld().GetAttribute("value");
		base.stepInfo("Actual populated dataset name : " + retrivedData);
		System.out.println("Actual populated dataset name : " + retrivedData);
		return retrivedData;
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @param firstName
	 * @param lastName
	 * @param collecctionName
	 * @param defaultText
	 * @param actualValue
	 * @param additional1
	 * @param additional2
	 * @return
	 */
	public boolean verifyRetrivedDataMatches(String firstName, String lastName, String collecctionName,
			String defaultText, String actualValue, Boolean comparision, Boolean additional1, String additional2) {

		String expectedValue = collecctionName + "_" + defaultText + "_" + firstName + " " + lastName;
		Boolean status = false;
		System.out.println(actualValue);
		System.out.println(expectedValue);

		if (comparision) {
			base.textCompareEquals(expectedValue, actualValue, "DataSet Name retrived as expected",
					"DataSet Name not retrived as expected");
		} else {
			if (expectedValue.equalsIgnoreCase(actualValue)) {
				status = true;
			} else {
				status = false;
			}
		}
		return status;

	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @param type
	 * @param saveAction
	 * @param verifySuccessMsg
	 */
	public void applyAction(String type, String saveAction, String verifySuccessMsg) {

		// Action apply
		try {
			getActionBtn(type).waitAndClick(5);
			driver.waitForPageToBeReady();
			base.stepInfo("Clicked : " + type);
			confirmationAction(type, saveAction, verifySuccessMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @param type
	 * @param action
	 * @param verifyMsg
	 */
	public void confirmationAction(String type, String action, String verifyMsg) {
		if (type.equalsIgnoreCase("Save") || type.equalsIgnoreCase("Save & Add New Dataset")) {
			if (getFolderSelectionConfirmation().isElementAvailable(5)) {

				String expectedTxt = "You have selected to retrieve data from the following folders for this custodian:";
				String actualTxt = getPopupMsg().getText();
				String passMsg = "Displayed Popup Msg : " + actualTxt;
				String failMsg = "Belly band msg is not as expected";
				base.compareTextViaContains(actualTxt, expectedTxt, passMsg, failMsg);

				getConfirmationBtnAction(action).waitAndClick(5);
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage(verifyMsg);
				base.CloseSuccessMsgpopup();
			}
		} else if (type.equalsIgnoreCase("CancelTo")) {

		} else if (type.equalsIgnoreCase("Delete")) {
			getConfirmationBtnAction(action).waitAndClick(5);
			base.stepInfo("Clicked : " + action);
			driver.waitForPageToBeReady();
			base.VerifySuccessMessage(verifyMsg);
			base.CloseSuccessMsgpopup();
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @param folderName
	 * @param toExpand
	 * @param additional1
	 */
	public void folderToSelect(String folderName, Boolean toExpand, Boolean additional1) {
		if (toExpand) {
			getFolderabLabel().waitAndClick(5);
		}
		// Respective folder to select
		try {
			driver.waitForPageToBeReady();
			base.waitTime(3);
			getFolderNameToSelect(folderName).waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author Hema MJ
	 * @createdDate : 11/25/22
	 * @param folderName with additional parameters
	 * @param subFolder
	 * @param subFolderName
	 */
	public void folderToSelect(String folderName, Boolean toExpand, Boolean SubFolder,String SubFolderName) {
		if (toExpand) {
			getFolderabLabel().waitAndClick(5);
		}
		// Respective folder to select
		if(SubFolder) {
			
			driver.waitForPageToBeReady();
			base.waitTime(3);
			getFolderTree(folderName).waitAndClick(10);
			getSubFolderNameToSelect(folderName,SubFolderName).waitAndClick(10);
			
		}else {
		try {
			driver.waitForPageToBeReady();
			base.waitTime(3);
			getFolderNameToSelect(folderName).waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @param addNewDataSetType
	 * @param firstName
	 * @param lastName
	 * @param collectionEmailId
	 * @param selectedApp
	 * @param colllectionData
	 * @param dataName
	 * @param retry
	 * @return
	 */
	public String addDataSetWithHandles(String addNewDataSetType, String firstName, String lastName,
			String collectionEmailId, String selectedApp, HashMap<String, String> colllectionData, String dataName,
			int retry) {

		
		String actualValue = null;
		Boolean status = false;
		for (int i = 1; i <= retry; i++) {
			System.out.println("Attempt : " + i);
			if (!status) {
				// Add Dataset
				addNewDataSetCLick(addNewDataSetType);
				actualValue = custodianNameSelectionInNewDataSet(firstName, collectionEmailId, true, false, "");
				status = verifyRetrivedDataMatches(firstName, lastName, colllectionData.get(dataName), selectedApp,
						actualValue, false, false, "");
				if (!status && i != retry) {
					applyAction("Cancel", "", "");
				} else if (!status && i == retry) {
					base.failedStep("Expected Input data not available");
				} else {
					status = verifyRetrivedDataMatches(firstName, lastName, colllectionData.get(dataName), selectedApp,
							actualValue, true, false, "");
					break;
				}
			} else {
				break;
			}
		}

		return actualValue;
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @Description : verify datasets content
	 * @param headerList
	 * @param firstName
	 * @param lastName
	 * @param selectedApp
	 * @param collectionEmailId
	 * @param dataSetNameGenerated
	 * @param selectedFolder
	 * @param additional1
	 * @param additional2
	 * @param additional3
	 * @param additional4
	 */
	public void verifyDataSetContents(String[] headerList, String firstName, String lastName, String selectedApp,
			String collectionEmailId, String dataSetNameGenerated, String selectedFolder, String DateOrKeyword,
			String subFolderName, Boolean subFolderFlag, int additional4) {

		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		for (int i = 0; i <= headerList.length - 1; i++) {
			int index = base.getIndex(getDataSetDetailsHeader(), headerList[i]);
			colllectionDataHeadersIndex.put(headerList[i], index);
		}

		base.printHashMapDetails(colllectionDataHeadersIndex);

		for (int j = 0; j <= headerList.length - 1; j++) {
			String expValue = " - ";

			if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader1)) {
				expValue = firstName + " " + lastName;
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader2)) {
				expValue = selectedApp;
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader3)) {
				expValue = collectionEmailId;
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader4)) {
				expValue = dataSetNameGenerated;
			} else if (subFolderFlag==true && headerList[j].equalsIgnoreCase(Input.collectionDataHeader5)) {
				expValue = subFolderName;
			} else if (subFolderFlag==false && headerList[j].equalsIgnoreCase(Input.collectionDataHeader5)) {
				expValue = selectedFolder;
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader6)) {
				expValue = DateOrKeyword;
			}
			// DataSet details comparision
			base.stepInfo(headerList[j]);
			
			base.textCompareEquals(expValue,
					getDataSetDetails(dataSetNameGenerated, colllectionDataHeadersIndex.get(headerList[j])).getText(),
					"Displayed as expected", "Not Displayed as expected");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @Description : Collection Deletion - Single
	 * @param collectionName
	 */
	public void collectionDeletion(String collectionName) {
		try {
			getCollectionsPageAction(collectionName).waitAndClick(5);
			getCollectionsPageActionList(collectionName, "Delete").waitAndClick(5);
			confirmationAction("Delete", "Yes", "The collection has been deleted successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/27/22
	 * @Description : Collection Save as draft
	 */
	public void collectionSaveAsDraft() {
		try {
			getActionDiv("Save as Draft").waitAndClick(5);
			driver.waitForPageToBeReady();
			base.VerifySuccessMessage("Collection saved as draft successfully");
			base.CloseSuccessMsgpopup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/28/22
	 * @param headerList
	 * @return
	 */
	public HashMap<String, Integer> getDataSetsHeaderIndex(String[] headerList) {

		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();
		for (int i = 0; i <= headerList.length - 1; i++) {
			int index = base.getIndex(getDataSetDetailsHeader(), headerList[i]);
			colllectionDataHeadersIndex.put(headerList[i], index);
		}
		return colllectionDataHeadersIndex;
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/28/22
	 * @Description : verify expected data present in nthe grid
	 * @param headerListDataSets
	 * @param collectionName
	 * @param expStatus
	 * @param verifyCollectionStatus
	 * @param additional1
	 * @param additional2
	 */
	public void verifyExpectedCollectionIsPresentInTheGrid(String[] headerListDataSets, String collectionName,
			String expStatus, Boolean verifyCollectionStatus, Boolean additional1, String additional2) {

		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();
		base.waitForElementCollection(getDataSetDetailsHeader());
//		List<String> headerListDatas = base.availableListofElements(getDataSetDetailsHeader());

		// Collection Header details
		colllectionDataHeadersIndex = getDataSetsHeaderIndex(headerListDataSets);

		// Check collection presence
		base.printResutInReport(base.ValidateElement_PresenceReturn(getCollectionNameElement(collectionName)),
				collectionName + " : is displayed in the grid", "Expected collection not available in the grid",
				"Pass");

		// Get collection Id
		String collId = getDataSetDetails(collectionName, colllectionDataHeadersIndex.get(Input.collectionIdHeader))
				.getText();
		base.stepInfo("Collection Id : " + collId);

		// Get Collection Status
		String collStatus = getDataSetDetails(collectionName,
				colllectionDataHeadersIndex.get(Input.collectionStatusHeader)).getText();
		base.stepInfo("Collection Status : " + collStatus);

		// Expected Status Validation
		if (verifyCollectionStatus) {
			base.textCompareEquals(
					getDataSetDetails(collectionName, colllectionDataHeadersIndex.get(Input.collectionStatusHeader))
							.getText(),
					expStatus, "Collection is in " + expStatus + " state as Expected",
					"Collection is not in " + expStatus + " state");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdDate : 7/28/22
	 * @Description : dataSets Creation Based On the Grid Availability
	 * @param firstName
	 * @param lastName
	 * @param collectionEmailId
	 * @param selectedApp
	 * @param colllectionData
	 * @param selectedFolder
	 * @param headerList
	 * @param expectedStatus
	 * @param creationType
	 * @param additional1
	 * @param additional2
	 * @return
	 */
	public HashMap<String, String> dataSetsCreationBasedOntheGridAvailability(String firstName, String lastName,
			String collectionEmailId, String selectedApp, HashMap<String, String> colllectionData,
			String selectedFolder, String[] headerList, String expectedStatus, String creationType, int retryAttempt,
			Boolean AutoInitiate, String collectionSpecificName) {

		String dataName;
		String creationStatus = "0";

		// Collection Specific Name
		if (!collectionSpecificName.equalsIgnoreCase("")) {
			dataName = collectionSpecificName;
		} else {
			dataName = "Automation" + Utility.dynamicNameAppender();
		}

		driver.waitForPageToBeReady();
		if (getCollectionNameStatusElements(expectedStatus).size() > 0) {
			String collectionId = dataName = getCollectionNameBasedOnStatusElements(expectedStatus, 1).getText();
			dataName = getCollectionNameBasedOnStatusElements(expectedStatus, 2).getText();
			colllectionData.put(dataName, collectionId);
		} else {
			creationStatus = "1";
			// Click create New Collection
			performCreateNewCollection();

			// Select source and Click create New Collection
			String dataSourceName = selectSourceFromTheListAvailable();

			// click created source location and verify navigated page
			colllectionData = verifyCollectionInfoPage(dataSourceName, dataName, false);

			// initiate collection process
			selectInitiateCollectionOrClickNext(AutoInitiate, true, true);

			// Add DataSets
			String dataSetNameGenerated = addDataSetWithHandles(creationType, firstName, lastName, collectionEmailId,
					selectedApp, colllectionData, dataName, retryAttempt);

			// Folder Selection
			folderToSelect(selectedFolder, true, false);
			applyAction("Save", "Confirm", "Dataset added successfully.");
			driver.waitForPageToBeReady();

			// verify DataSet Contents
			verifyDataSetContents(headerList, firstName, lastName, selectedApp, collectionEmailId, dataSetNameGenerated,
					selectedFolder, "-", "", false, 0);

			// Next Button
			driver.waitForPageToBeReady();
			getNextBtnDS().waitAndClick(10);
			base.stepInfo("Clicked Next Button");
			driver.waitForPageToBeReady();

			// Save As Draft
			collectionSaveAsDraft();
		}

		System.out.println(creationStatus);
		return colllectionData;

	}

	/**
	 * @Author Jeevitha
	 * @Description : selects Initiate process And Clicks next button
	 * @param Automatic
	 * @param Next
	 * @param verifyTab
	 */
	public void selectInitiateCollectionOrClickNext(boolean Automatic, boolean Next, boolean verifyTab) {
		if (Automatic) {
			getAutomaticInitiate_Btn().waitAndClick(10);
			base.stepInfo("Selected : Automatially Initiate process");
		} else {
			getDonotAutomaticInitiate_Btn().waitAndClick(10);
			base.stepInfo("Selected : Do Not Automatially Initiate process");
		}

		if (Next) {
			getNextBtn().waitAndClick(10);
			base.stepInfo("Clicked Next Button");
			driver.waitForPageToBeReady();
			if (verifyTab)
				verifyCurrentTab("Dataset Selection");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : delete collection using collection name
	 * @param collectionName
	 */
	public void deleteUsingCollectionName(String collectionName, boolean delete) {
		if (getCollectionAction(collectionName).isElementAvailable(5)) {
			getCollectionAction(collectionName).waitAndClick(5);
			getCollectionActionList(collectionName, "Delete").waitAndClick(10);

			if (getPopupMsg().isElementAvailable(8)) {
				String expectedMsg = "Are you sure you want to delete the collection?";
				driver.waitForPageToBeReady();
				String actualMsg = getPopupMsg().getText();
				base.compareTextViaContains(actualMsg, expectedMsg, actualMsg, "Popup msg is not as expected");

				if (delete) {
					confirmationAction("Delete", "Yes", "The collection has been deleted successfully.");
				} else {
					getConfirmationBtnAction("No").waitAndClick(10);
					base.stepInfo("Clicked No button");
				}
			}
		} else {
			base.failedStep(collectionName + " : is not avialable");
		}
	}

	public void startCollection(String collectionName, String actionType) {
		if (getCollectionAction(collectionName).isElementAvailable(5)) {
			getCollectionAction(collectionName).waitAndClick(5);
			getCollectionActionList(collectionName, actionType).waitAndClick(10);

			if (actionType.equalsIgnoreCase("Start Collection")) {
				base.VerifySuccessMessage("Collection extraction process started successfully.");
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @description : select already present collection from home page or create new
	 *              collection
	 * @return
	 */
	public HashMap<String, String> createNewCollection(HashMap<String, String> colllectionData, String dataName,
			Boolean AutoInitiate, String expectedStatus, boolean selectCollFromList) {

		String creationStatus = "0";

		driver.waitForPageToBeReady();
		if (selectCollFromList && getCollectionNameStatus(expectedStatus).size() > 0) {
			String collectionId = dataName = getCollectionNameBasedOnStatus(expectedStatus, 1).getText();
			dataName = getCollectionNameBasedOnStatus(expectedStatus, 2).getText();
			colllectionData.put(dataName, collectionId);
		} else {
			creationStatus = "1";
			// Click create New Collection
			performCreateNewCollection();

			// Select source and Click create New Collection
			String dataSourceName = selectSourceFromTheListAvailable();

			// click created source location and verify navigated page
			colllectionData = verifyCollectionInfoPage(dataSourceName, dataName, false);

			// initiate collection process
			selectInitiateCollectionOrClickNext(AutoInitiate, true, true);

		}
		return colllectionData;

	}

	/**
	 * @Author Jeevitha
	 * @Description : apply filter in dataset popup
	 */
	public void applyFilterInDataSet(boolean ApplyFilter, boolean Enable, boolean keyword, String keywords) {
		if (ApplyFilter) {
			getApplyFilterLink().waitAndClick(10);
			driver.waitForPageToBeReady();
			String status = getEnableFilterStatus().GetAttribute("style");
			System.out.println(status);
			if (status.contains("none")) {
				base.stepInfo("Filter is Disabled");

				if (Enable) {
					getEnableFilterToggle().waitAndClick(10);
					base.stepInfo("Filter Toggle Is Enabled");

					if (keyword) {
						driver.waitForPageToBeReady();
						getKeywordToggle().waitAndClick(10);
						getKeywordTextBox().SendKeys(keywords);
						base.stepInfo("Entered Keyword is : " + keywords);
					}
				}
			} else {
				base.stepInfo("Filter Toggle Is Enabled");
			}
		}

	}

	/**
	 * @return
	 * @Author Jeevitha
	 * @modifiedBy : Raghuram
	 * @modifiedOn : 8/16/22 - ( Save & new )
	 * @Description : add dataset by clicking btn/link and verify new row added in
	 *              dataset selection table
	 */
	public List<String> fillingDatasetSelection(String creationType, String firstName, String lastName,
			String collectionEmailId, String selectedApp, HashMap<String, String> colllectionData, String dataName,
			int retryAttempt, String selectedFolder, boolean ApplyFilter, boolean Enable, boolean keyword,
			String keywords, boolean Save, boolean Confirm, String saveType, String additional1) {

		List<String> custodianDetails = new ArrayList<>();
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };

		// Add DataSets
		String dataSetNameGenerated = addDataSetWithHandles(creationType, firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, dataName, retryAttempt);

		// custodian Name
		base.waitForElement(getCustodianIDInputTextField());
		String selectedcustodianName = getCustodianIDInputTextField().GetAttribute("value");

		// Folder Selection
		folderToSelect(selectedFolder, true, false);

		// apply filter
		applyFilterInDataSet(ApplyFilter, Enable, keyword, keywords);

		// click save/cancel and verify row added
		String expectedTxt = "You have selected to retrieve data from the following folders for this custodian:";
		if (Save) {
			if (saveType.equalsIgnoreCase("Save")) {
				SaveActionInDataSetPopup(Confirm, firstName, lastName, selectedApp, collectionEmailId,
						dataSetNameGenerated, selectedFolder, keywords, true, "Dataset added successfully.");
			} else if (saveType.equalsIgnoreCase("Save & Add New Dataset")) {
				applyAction(saveType, "Confirm", "Dataset added successfully.");
				driver.waitForPageToBeReady();
				System.out.println(getDataSetSelectionPopDisplay().isDisplayed());
				base.printResutInReport(getDataSetSelectionPopDisplay().isDisplayed(),
						"'Add Dataset' page (popup) opened, to allow the user to add another custodian dataset to the collection  after Save&New action",
						"New Add dataset pop-up is not displayed after Save&New action", "Pass");
			}

		}

		driver.waitForPageToBeReady();
		custodianDetails.add(selectedcustodianName);
		custodianDetails.add(dataSetNameGenerated);
		return custodianDetails;
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify Added Dataset From Dataset selection Popup and check if
	 *              it is Retained
	 */
	public void verifyAddedDataSetFrmPopup(String custodianMailId, String collectionName,
			List<String> expectedCustodianDetaisl, String expectedFolderType, boolean ApplyFilter,
			String expectedFilterStatus) {
		List<String> custodianDetails = new ArrayList<>();

		String headerList[] = { "Select Custodian", "Select Folders to Collect", "Apply Filter" };

		driver.waitForPageToBeReady();
		getEditBtnDataSelection(custodianMailId).waitAndClick(10);

		if (getDataSetPopup().isElementAvailable(5)) {
			base.stepInfo("Dataset Selection Popup is Displayed");

			base.ValidateElement_Presence(getCollectioNameOnPopup(collectionName),
					"Collection Name is displayed on Popup");

			// verify Completed Tag
			base.ValidateElementCollection_Presence(getCompletedTagOfPopup(), "Complete Tag ");

			List<String> attributesContainComplete = base.availableListofElements(getCompletedTagOfPopup());
			Collections.sort(attributesContainComplete);
			System.out.println(attributesContainComplete);
			List<String> expectedAttributes = new ArrayList<String>();
			for (int i = 0; i < headerList.length; i++) {
				expectedAttributes.add(headerList[i]);
			}
			System.out.println(expectedAttributes);
			boolean status = base.compareListViaContains(attributesContainComplete, expectedAttributes);

			String passMsg = "Complete Tag is Present On : " + attributesContainComplete;
			String failMsg = "Completed Tag is Not present as Expected";
			base.printResutInReport(status, passMsg, failMsg, "Pass");

			// verify Custodian Details
			getCustodianLabel().waitAndClick(10);

			base.waitForElement(getCustodianIDInputTextField());
			String actualName = getCustodianIDInputTextField().GetAttribute("value");
			base.waitForElement(getDataSetNameTextFIeld());
			String actualDatasetName = getDataSetNameTextFIeld().GetAttribute("value");
			custodianDetails.add(actualName);
			custodianDetails.add(actualDatasetName);

			String passMsg2 = "Custodian Name & Dataset Name is Retained As expected : " + custodianDetails;
			String failMsg2 = "Custodian Name & Dataset Name is not Retained";
			base.listCompareEquals(custodianDetails, expectedCustodianDetaisl, passMsg2, failMsg2);

			// verify Selected folder
			getFolderabLabel().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.ValidateElement_Presence(getCickedFolder(expectedFolderType),
					expectedFolderType + " : folder is selected and retained as Expected");

			// Verify Filter status
			if (ApplyFilter) {
				verifyApplyFilterStatus(true, expectedFilterStatus);
				driver.waitForPageToBeReady();
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : click edit and verify added Dataset and can modify any field
	 */
	public void editDatasetAndVerify(boolean clickEdit, String collectionEmailId, boolean editCustodianName,
			String firstName, String collection2ndEmailId, boolean editFolder, boolean validateFolder,
			String resetFolderType, String SelectFolderType, String expectedFilterStatus, boolean ApplyFilter) {
		if (clickEdit) {
			driver.waitForPageToBeReady();
			getEditBtnDataSelection(collectionEmailId).waitAndClick(10);
		}
		if (getDataSetPopup().isElementAvailable(8)) {
			base.stepInfo("DatasetPopup is displayed");
			if (editCustodianName) {
				driver.waitForPageToBeReady();
				getCustodianLabel().waitAndClick(10);

				base.waitForElement(getCustodianIDInputTextField());
				getCustodianIDInputTextField().Clear();

				String actualValue = custodianNameSelectionInNewDataSet(firstName, collection2ndEmailId, true, false,
						"");
			}

			if (editFolder) {
				// verify Selected folder
				getFolderabLabel().waitAndClick(10);
				driver.waitForPageToBeReady();
				if (validateFolder) {
					base.ValidateElement_Absence(getCickedFolder(resetFolderType),
							"The Selected folder is unselected and Reset");
				}
				if (getCickedFolder(resetFolderType).isElementAvailable(3)) {
					getCickedFolder(resetFolderType).waitAndClick(10);
				}
				folderToSelect(SelectFolderType, false, null);
			}

			if (ApplyFilter) {
				// Apply filter
				verifyApplyFilterStatus(true, expectedFilterStatus);
				base.passedStep("Apply Filter Tab is Reset");
			}
		} else {
			base.failedStep("Dataset Popup is not displayed");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify Apply Filter Status
	 * @param applyFilter
	 * @param expectedFilterStatus
	 */
	public void verifyApplyFilterStatus(boolean applyFilter, String expectedFilterStatus) {
		if (applyFilter) {
			getApplyFilterLink().waitAndClick(10);
			driver.waitForPageToBeReady();
		}

		String toggleStatus;
		String applyFilterstatus = getEnableFilterStatus().GetAttribute("style");
		System.out.println(applyFilterstatus);
		if (applyFilterstatus.contains("none")) {
			base.stepInfo("Filter is Disabled");
			toggleStatus = "Disabled";
		} else {
			base.stepInfo("Filter is Enabled");
			toggleStatus = "Enabled";
		}
		String passMsg = "Apply Fiter is " + toggleStatus + " as Expected";
		String failMsg = "Apply filter status is not as expected";
		base.textCompareEquals(toggleStatus, expectedFilterStatus, passMsg, failMsg);
	}

	/**
	 * @Author Jeevitha
	 * @Description : CLick Save And Verify Dataset table
	 */
	public void SaveActionInDataSetPopup(boolean Confirm, String firstName, String lastName, String selectedApp,
			String collectionEmailId, String dataSetNameGenerated, String selectedFolder, String keywords,
			boolean verifyTable, String successMsg) {
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };

		// click save/cancel and verify row added
		String expectedTxt = "You have selected to retrieve data from the following folders for this custodian:";

		getActionBtn("Save").waitAndClick(5);
		if (getFolderSelectionConfirmation().isElementAvailable(5)) {
			String actualTxt = getPopupMsg().getText();
			String passMsg = "Displayed Popup Msg : " + actualTxt;
			String failMsg = "Belly band msg is not as expected";
			base.compareTextViaContains(actualTxt, expectedTxt, passMsg, failMsg);

			if (Confirm) {
				getConfirmationBtnAction("Confirm").waitAndClick(10);
				driver.waitForPageToBeReady();
				base.VerifySuccessMessage(successMsg);
				base.CloseSuccessMsgpopup();

				if (verifyTable) {
					// verify DataSet Contents in table
					verifyDataSetContents(headerList, firstName, lastName, selectedApp, collectionEmailId,
							dataSetNameGenerated, selectedFolder, keywords, "", false, 0);
				}
			} else {
				getConfirmationBtnAction("Cancel").waitAndClick(10);
				base.waitForElement(getActionBtn("Cancel"));
				getActionBtn("Cancel").waitAndClick(10);
				if (verifyTable) {

					// verify row is Not added
					base.ValidateElement_Absence(getDatasetTableValue(collectionEmailId),
							"Dataset is not added as expected");
				}
			}
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify User is able to change the source location type from
	 *               one to another
	 * @param rowNo
	 * @param srceName
	 * @param tentID
	 * @param appID
	 * @param secrtKey
	 */
	public void verifyAnotherSourceLocationCanBeSelected(String dropDownValue, String srceName, String tentID,
			String appID, String secrtKey) {

		SourceLocationPage source = new SourceLocationPage(driver);
		if (getAddNewSource().isElementAvailable(10)) {
			getAddNewSource().waitAndClick(10);
			base.stepInfo("Clicked Add new source location button on collection page");
		} else if (source.getAddNewSrcLoction().isElementAvailable(10)) {
			source.getAddNewSrcLoction().waitAndClick(10);
			base.stepInfo("Clicked Add new source location button on Source location Page");
		}

		if (getAddNewSourcePopUp().isElementAvailable(10)) {
			base.passedStep("Add New Source location Popup is opened");

			// data source type

			base.waitForElement(getDataSourceType());
			getDataSourceType().selectFromDropdown().selectByVisibleText(dropDownValue);
			base.passedStep(
					"User is able to change one source location type to another from 'Data Source Type' dropdown on 'Add New Source Location' screen. and it is enabled (Currently we are only supporting  O365)");

			// data source Name
			getDataSourceName().waitAndClick(5);
			getDataSourceName().SendKeys(srceName);
			base.stepInfo("Entered Data source Name : " + srceName);
			// tenant ID
			getTenantID().waitAndClick(5);
			getTenantID().Clear();
			getTenantID().SendKeys(tentID);
			base.stepInfo("Entered Tentant ID : " + tentID);
			// Application ID
			getApplicationID().waitAndClick(5);
			getApplicationID().SendKeys(appID);
			base.stepInfo("Entered Application ID : " + appID);
			// Application secret key
			getAppSecretKey().waitAndClick(5);
			getAppSecretKey().Clear();
			getAppSecretKey().SendKeys(secrtKey);
			base.stepInfo("Entered Secret Key : " + secrtKey);

		} else {
			base.failedStep("Add New Source Location Popup is Not Present");
		}

		base.waitTillElemetToBeClickable(getAddNewPopup_SaveBtn());
		getAddNewPopup_SaveBtn().waitAndClick(10);
		base.stepInfo("Clicked Save Button");

		base.VerifySuccessMessage("Source Location added successfully");

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To Verify Error Msg in Custodian and Dataset Name place.
	 * @param addNewDataSetType
	 * @param firstName
	 * @param lastName
	 * @param collectionEmailId
	 * @param selectedApp
	 * @param collectionInfoPage
	 * @param dataName
	 * @param retryNo
	 */
	public void verifyErrorMessageInCutodianSelection(String addNewDataSetType, String firstName, String lastName,
			String collectionEmailId, String selectedApp, HashMap<String, String> collectionInfoPage, String dataName,
			int retryNo) {

		driver.waitForPageToBeReady();
		addNewDataSetCLick(addNewDataSetType);

		base.waitForElement(getActionBtn("Save"));
		getActionBtn("Save").waitAndClick(5);

		if (getCustodianNameErrorMsg().isElementAvailable(5) && getDatasetNameErrorMsg().isElementAvailable(5)) {

			String actualErrorMsg = getCustodianNameErrorMsg().getText();
			String expectedErrorMsg = "Please select a custodian name/email.";

			base.textCompareEquals(actualErrorMsg, expectedErrorMsg, "Custodian Error Msg is : " + actualErrorMsg,
					" as expected");

			String actualDatasetsErrorMsg = getDatasetNameErrorMsg().getText();
			String expectedDatasetsErrorMsg = "Please enter dataset name.";

			base.textCompareEquals(actualDatasetsErrorMsg, expectedDatasetsErrorMsg,
					"Datasets Error Msg is : " + actualDatasetsErrorMsg, " as expected");

			base.passedStep("Error message is displayed to enter custodian name and dataset name successfully");

		} else {
			base.failedStep("Error Message is not displayed in the Datasets popup window");
		}

		base.waitForElement(getActionBtn("Cancel"));
		getActionBtn("Cancel").waitAndClick(5);

		// Pre-Requsite Add DataSets
		String dataSetNameGenerated = addDataSetWithHandles(addNewDataSetType, firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataName, retryNo);
		System.out.println(dataSetNameGenerated);
		base.passedStep("Custodian name/email, dataset name are entered successfully");

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To Verify Error in Folder selection fields
	 * @param selectedFolder
	 */
	public void verifyErrorMessageInFolderSelectionFields(String selectedFolder) {

		driver.waitForPageToBeReady();

		base.waitForElement(getFolderabLabel());
		getFolderabLabel().waitAndClick(5);

		base.waitForElement(getActionBtn("Save"));
		getActionBtn("Save").waitAndClick(5);

		if (getFolderErrorMsg().isElementAvailable(5)) {

			String actualFolderErrorMessage = getFolderErrorMsg().getText();
			String expectedFolderErrorMessage = "Please select at least one folder from which to collect data.";

			base.textCompareEquals(actualFolderErrorMessage, expectedFolderErrorMessage,
					"Datasets Error Msg is : " + actualFolderErrorMessage, " as expected");

			base.passedStep("Error message is displayed in folder selection successfully");

		} else {
			base.failedStep("Error Message is not displayed in the Folder Selection popup window");
		}

		folderToSelect(selectedFolder, false, false);
		base.passedStep("Folder is Selected successfully");

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify Error msg in Apply Filter Field
	 * 
	 */
	public void verifyErrorMessageInApplyFilterField() {

		driver.waitForPageToBeReady();

		base.waitForElement(getFilterButton());
		getFilterButton().waitAndClick(5);

		driver.waitForPageToBeReady();
		base.waitForElement(getFilteEnablerButton());
		getFilteEnablerButton().waitAndClick(5);

		base.waitTime(3);
		base.waitForElement(getActionBtn("Save"));
		getActionBtn("Save").waitAndClick(5);

		base.waitTime(3);

		if (getDateRangeFilterErrorMsg().isElementAvailable(5)) {

			String actualDateRangeErrorMessage = getDateRangeFilterErrorMsg().getText();
			System.out.println(actualDateRangeErrorMessage);
			String expectedDateRangeErrorMessage = "Please enter a valid date range.";

			base.textCompareEquals(actualDateRangeErrorMessage, expectedDateRangeErrorMessage,
					"Datasets Error Msg is : " + actualDateRangeErrorMessage, " as expected");

			base.passedStep("Error message is displayed in Date Range Filter selection successfully");

		} else {
			base.failedStep("Error Message is not displayed in the Date Range Filter Selection popup window");
		}

		base.waitTime(3);
		base.waitForElement(getKeyboardFilterButton());
		getKeyboardFilterButton().waitAndClick(5);

		base.waitForElement(getActionBtn("Save"));
		getActionBtn("Save").waitAndClick(5);

		if (getKeyboardFilterErrorMsg().isElementAvailable(5)
				&& getKeyboardFilterNoteErrorMsg().isElementAvailable(5)) {

			String actualKeyboardFilterErrorMessage = getKeyboardFilterNoteErrorMsg().getText();
			String expectedKeyboardFilterErrorMessage = "Note: Keyword filters are not applied to Calendar items.";

			base.textCompareEquals(actualKeyboardFilterErrorMessage, expectedKeyboardFilterErrorMessage,
					"Datasets Error Msg is : " + actualKeyboardFilterErrorMessage, " as expected");

			base.passedStep("Error message is displayed in Keyboard Filter selection successfully");

		} else {
			base.failedStep("Error Message is not displayed in Keyboard Filter Selection popup window");

		}

		base.waitForElement(getFilteEnablerButton());
		getFilteEnablerButton().waitAndClick(5);

		applyAction("Save", "Confirm", "Dataset added successfully.");

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify Completed datatset error msg
	 * @param selectedFolder
	 */
	public void editDatasetAndVerifyErrorMessage(String selectedFolder) {

		driver.waitForPageToBeReady();

		base.waitForElement(getDatasetsListEditButton());
		getDatasetsListEditButton().waitAndClick(5);

		base.waitForElement(getFolderCompletedButton());
		getFolderCompletedButton().waitAndClick(5);

		if (getRefreshButtonInSelectFolderField().isElementAvailable(5)) {

			base.waitForElement(getRefreshButtonInSelectFolderField());
			getRefreshButtonInSelectFolderField().waitAndClick(5);

		} else {
			base.failedStep("Error msg can't be validated");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify When no custodian datasets added/configured as part
	 *               of this collection, the grid should present zero rows, and
	 *               should present a message saying 'No custodian dataset has been
	 *               added to this collection yet.' with a link to allow the user to
	 *               'Add Datasets'
	 */
	public void verifyDatasetsPage() {

		driver.waitForPageToBeReady();

		if (getDatasetHomePageNoCutodainAvailableText().isElementAvailable(5)
				&& getDatasetPageAddDatasetLinkButton().isElementAvailable(5)) {

			String actualValue = getDatasetHomePageNoCutodainAvailableText().getText();
			System.out.println(actualValue);
			String expectedValue = "No dataset has been added yet";
			String passedMsg = "The expected value is:" + actualValue;
			String failedMsg = "The expected value is not present in the Datasets Page";
			base.compareTextViaContains(actualValue, expectedValue, passedMsg, failedMsg);

			base.waitForElement(getDatasetPageAddDatasetLinkButton());
			getDatasetPageAddDatasetLinkButton().waitAndClick(5);
			base.passedStep("Dataset selection pop up is opened successfully");

			base.passedStep(
					"In Datasets Home Page When no custodian are found the expected message is:" + actualValue + "");

		} else if (getDatasetHomePageCustodianList().isElementAvailable(5)) {

			int tableRowSize = getDatasetHomePageCustodianList().size();
			System.out.println(tableRowSize);

			for (int i = 1; i < tableRowSize; i++) {

				base.waitForElement(getDatasetPageCustodianDeleteButton());
				getDatasetPageCustodianDeleteButton().waitAndClick(5);

				String actualValue = getDatasetHomePageNoCutodainAvailableText().getText();
				System.out.println(actualValue);
				String expectedValue = "No dataset has been added yet";
				String passedMsg = "The expected value is:" + actualValue;
				String failedMsg = "The expected value is not present in the Datasets Page";
				base.compareTextViaContains(actualValue, expectedValue, passedMsg, failedMsg);

				base.waitForElement(getDatasetPageAddDatasetLinkButton());
				getDatasetPageAddDatasetLinkButton().waitAndClick(5);
				base.passedStep("Dataset selection pop up is opened successfully");
				base.passedStep("In Datasets Home Page the expected message is:" + actualValue + "");

			}
		} else {
			base.failedStep("No Element found in the Dataste home page");
		}
	}

	/**
	 * @Author Jeevitha
	 */
	public void clickNextBtnOnDatasetTab() {
		driver.scrollPageToTop();
		getNextBtnDS().waitAndClick(10);
		verifyCurrentTab("Summary and Start Collection");
	}

	/**
	 * @author Raghuram.A
	 * @param collectionName
	 */
	public void enterCollectionName(String collectionName) {
		HashMap<String, String> colllectionData = new HashMap<>();

		base.waitForElement(getCollectioName());
		verifyCurrentTab("Collection Information");

		if (getCollectioName().isElementAvailable(3)) {
			getCollectioName().waitAndClick(5);
			getCollectioName().SendKeys(collectionName);
			base.stepInfo("Entered collection Name : " + collectionName);
		} else {
			base.failedStep("Collection Name Field is Not displayed");
		}

		String collectionID = getCollectionID().getText();
		colllectionData.put(collectionName, collectionID);
	}

	/**
	 * @author Raghuram.A
	 * @param nextType
	 */
	public void nextAction(String nextType) {
		// Next Button
		driver.waitForPageToBeReady();
		if (nextType.equalsIgnoreCase("DataSet")) {
			getNextBtnDS().waitAndClick(10);
		} else if (nextType.equalsIgnoreCase("CollectionTab")) {
			getNextBtn().waitAndClick(10);
		}
		base.stepInfo("Clicked Next Button");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Raghuram.A
	 * @param headerWait
	 * @param headerListDataSets
	 * @param collectionName
	 * @param expStatus
	 * @param reTryAttempt
	 * @param verifyCollectionStatus
	 * @param additional1
	 * @param additional2
	 * @param additional3
	 * @description : verify Expected Collection Status - (Completed status yet to
	 *              be scripted based on future requirements)
	 */
	public void verifyExpectedCollectionStatus(Boolean headerWait, String[] headerListDataSets, String collectionName,
			String[] expStatus, int reTryAttempt, Boolean verifyCollectionStatus, Boolean progressBar,
			String additional2, String additional3) {

		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();
		String collStatus = null;

		if (headerWait) {
			base.waitForElementCollection(getDataSetDetailsHeader());
		}

		// Collection Header details
		colllectionDataHeadersIndex = getDataSetsHeaderIndex(headerListDataSets);

		// Get collection Id
		String collId = getDataSetDetails(collectionName, colllectionDataHeadersIndex.get(Input.collectionIdHeader))
				.getText();
		base.stepInfo("Collection Id : " + collId);

		// Status check
		statusCheck(expStatus, reTryAttempt, collectionName, colllectionDataHeadersIndex, true, progressBar, "", "", 0);

	}

	/**
	 * @author Raghuram.A
	 * @param expStatus
	 * @param reTryAttempt
	 * @param collectionName
	 * @param colllectionDataHeadersIndex
	 * @param verifyCollectionStatus
	 * @param additional1
	 * @param addditional2
	 * @param additional3
	 * @param additional4
	 * @modifiedon : 8/25/22
	 * @modifiedBy : Raghuram
	 */
	public void statusCheck(String[] expStatus, int reTryAttempt, String collectionName,
			HashMap<String, Integer> colllectionDataHeadersIndex, Boolean verifyCollectionStatus, Boolean progressBar,
			String addditional2, String additional3, int additional4) {
		// Status check
		String collStatus = null;
		String collProgress = "0";

		for (int i = 0; i < expStatus.length; i++) {
			String expectedStatus = expStatus[i];
			for (int j = 0; j <= reTryAttempt; j++) {
				System.out.println("Attempt : " + j);
				if (base.ValidateElement_StatusReturn(getExpCollectionStatus(collectionName, expectedStatus), 10)) {
					break;
				} else {
					// Get Collection Status
					collStatus = getDataSetDetails(collectionName,
							colllectionDataHeadersIndex.get(Input.collectionStatusHeader)).getText();
					base.stepInfo("Collection Status is in : " + collStatus);
				}
			}

			// Expected Status Validation
			if (verifyCollectionStatus) {
				// Get Collection Status
				collStatus = getDataSetDetails(collectionName,
						colllectionDataHeadersIndex.get(Input.collectionStatusHeader)).getText();

				base.stepInfo("Collection Status : " + collStatus);
				base.textCompareEquals(collStatus, expectedStatus,
						"Collection is in " + expectedStatus + " state as Expected",
						"Collection is not in " + expectedStatus + " state");
			}

			// Progress Bar
			if (progressBar) {
				String collProgressStats = getProgressBarStats(collectionName,
						colllectionDataHeadersIndex.get(Input.progressBarHeader)).getText();
				base.textCompareNotEquals(collProgress, collProgressStats, "Progress Bar value got updated",
						"Progress Bar value remains the same");
				collProgress = collProgressStats;
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @Decsription : get Displayed Message on performing mouse hover Action in
	 *              Dataset Selection Page
	 * @param headerName
	 * @param custodianEmail
	 * @return
	 */
	public String verifyMouseOverAction(String headerName, String custodianEmail) {
		String actualTxt = null;
		driver.waitForPageToBeReady();
		int index = base.getIndex(getDataSetDetailsHeader(), headerName);
		driver.waitForPageToBeReady();
		base.mouseHoverOnElement(getDataSetTableValue(custodianEmail, index));
		if (getMouseHoverDetails(index).isElementAvailable(3)) {
			actualTxt = getMouseHoverDetails(index).GetAttribute("data-content");
			base.passedStep("On Mouse Hover the displayed Message is : " + actualTxt);
		} else {
			base.failedStep("On mouse hover the popup is not Displayed");
		}
		return actualTxt;
	}

	/**
	 * @Author Jeevitha
	 * @Description : click View datasets from collection page
	 * @param collectionName
	 */
	public void clickViewDataset(String collectionName) {
		if (getCollectionAction(collectionName).isElementAvailable(3)) {
			getCollectionAction(collectionName).waitAndClick(5);
			getViewDatsetBtn(collectionName).waitAndClick(5);
			base.stepInfo("Clicked View Dataset Btn");
		} else if (getViewDatsetBtn(collectionName).isElementAvailable(2)) {
			getViewDatsetBtn(collectionName).waitAndClick(5);
			base.stepInfo("Clicked View Dataset Btn");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify sorting order of column values in collection page .
	 * @param ClickTotalRetrived
	 * @param headerName
	 * @param sortType
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void verifySortingOrderOfCollectionPage(boolean ClickBtn, String headerName, String sortType)
			throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		if (ClickBtn)
			getHeaderBtn(headerName).waitAndClick(10);
		base.stepInfo("Clicked : " + headerName);
		driver.waitForPageToBeReady();

		int index = base.getIndex(getDataSetDetailsHeader(), headerName);

		driver.waitForPageToBeReady();
		List<String> originalList = base.availableListofElements(getCollectionNameElements(index));
		List<String> afterSortList = base.availableListofElements(getCollectionNameElements(index));
//		base.stepInfo("Original Order :" + originalList);

		base.verifyOriginalSortOrder(originalList, afterSortList, sortType, true);
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: Verify Loading Folder Message for Select Folder is done
	 */
	public void verifyLoadingFolderMessage() {

		driver.waitForPageToBeReady();

		base.waitForElement(getFolderabLabel());
		getFolderabLabel().waitAndClick(5);

		if (getLoadingFolderInDataset().isElementPresent()) {
			base.passedStep("Loading folders icon appears on screen successfully");
			base.passedStep(
					"Only Cancel buttons are enabled. Save & Add New Dataset button is disabled. Save button is also disabled successfully.");

		} else {
			base.failedStep("Loading folders icon doesn't appears on screen successfully");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: Verify Loading Folders Icon Cancel button Save & Add New
	 *               Datatsets Save buttons
	 */
	public void verifyCancelSaveAddNewDatasetSave() {

		driver.waitForPageToBeReady();

		base.waitForElement(getFolderabLabel());
		getFolderabLabel().waitAndClick(5);

		base.waitForElement(getRefreshButtonInSelectFolderField());
		getRefreshButtonInSelectFolderField().waitAndClick(5);

		getLoadingFolderInDataset().isElementPresent();
		base.passedStep("Loading folders icon appears on screen successfully");

		base.waitForElement(getRefreshButtonInSelectFolderField());
		getRefreshButtonInSelectFolderField().waitAndClick(5);
		getDatasetPopUpSaveAndAddNewDatasetButton().isElementAvailable(5);

		base.waitForElement(getRefreshButtonInSelectFolderField());
		getRefreshButtonInSelectFolderField().waitAndClick(5);
		getDatasetPopUpCancelDatasetButton().Enabled();

		base.waitForElement(getRefreshButtonInSelectFolderField());
		getRefreshButtonInSelectFolderField().waitAndClick(5);
		getDatasetPopUpSaveButton().isElementAvailable(5);

		base.passedStep(
				"Only Cancel buttons are enabled. Save & Add New Dataset button is disabled. Save button is also disabled successfully.");

	}

	/**
	 * @author Raghuram.A
	 * @param headerListDataSets
	 * @param collectionName
	 * @param expStatus
	 * @param retryAttempt
	 * @description : verify Status Using Contains TypeII
	 */
	public void verifyStatusUsingContainsTypeII(String[] headerListDataSets, String collectionName, String[] expStatus,
			int retryAttempt) {

		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		// Collection Header details
		colllectionDataHeadersIndex = getDataSetsHeaderIndex(headerListDataSets);

		for (int i = 0; i < expStatus.length; i++) {
			String expStatusToCheck = expStatus[i];
			for (int j = 0; j <= retryAttempt; j++) {
				Boolean status = false;
				// Get Collection Status
				base.waitTime(15); // To handle abnormal wait times in case of data processing
				String collStatus = getDataSetDetails(collectionName,
						colllectionDataHeadersIndex.get(Input.collectionStatusHeader)).getText();

				// Contains comparision
				if (collStatus.contains(expStatusToCheck)) {
					base.passedStep("Collection is in " + collStatus + " state as Expected");
					status = true;
					break;
				} else {
					base.stepInfo("Collection is in " + collStatus + " state");
				}

				// Final Check
				if (status == false && j == retryAttempt) {
					base.failedStep("Collection is in " + collStatus + " state not as expected");
				}
			}
		}
	}

	/**
	 * @author Raghuram.A
	 * @param singleFlow
	 * @param custodianEmailId
	 * @param type
	 * @param action
	 * @param verifyMsg
	 * @param additional1
	 */
	public void confirmationDataSetDelAction(Boolean singleFlow, String custodianEmailId, String type, String action,
			String verifyMsg, String additional1) {

		driver.waitForPageToBeReady();
		getDataSelectionAction(custodianEmailId, "Delete").waitAndClick(5);

		// Deletion Confirmation
		if (getDataSetDeletionRibbon().isElementAvailable(5)) {
			base.stepInfo("Dataset deletion confirmation ribbon is displayed");
			getConfirmationBtnAction(action).waitAndClick(5);
			base.stepInfo("Clicked " + action + " as confirmation Action");
			driver.waitForPageToBeReady();

			// Action notification message
			if (action.equalsIgnoreCase("yes")) {
				base.VerifySuccessMessage(verifyMsg);
				base.CloseSuccessMsgpopup();
				driver.waitForPageToBeReady();
			}
		} else {
			base.failedMessage("Dataset deletion confirmation ribbon is not displayed");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param collectionName
	 * @param headerListDataSets
	 * @param headerName
	 * @param additional1
	 * @param additional2
	 */
	public void clickDownloadReportLink(String collectionName, String[] headerListDataSets, String headerName,
			Boolean additional1, String additional2) {

		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		// Collection Header details
		colllectionDataHeadersIndex = getDataSetsHeaderIndex(headerListDataSets);

		// Download Report
		driver.waitForPageToBeReady();
		try {
			getClickDownloadReport(collectionName, colllectionDataHeadersIndex.get(headerName)).waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To get total count of collection present in Collection Wizard
	 * @param rowNo
	 * @return
	 */
	public int getTotalCountOfCollectionPresentInCollectionsPage(int rowNo) {

		driver.waitForPageToBeReady();

		int collectionList = getCollectionListInCollectionHomePage().size();
		System.out.println(collectionList);

		int a = 1;
		int collectionRowNo;

		for (int i = 1; i < collectionList; i++) {

			base.waitForElement(getCollectionFieldValuesInCollectionHomePage(rowNo, i));
			String collectionFieldValue = getCollectionFieldValuesInCollectionHomePage(rowNo, i).getText();
			System.out.println("Collection Status Value: " + collectionFieldValue);

			if (collectionFieldValue.contains("Completed")) {

				base.passedStep("The Collection is completed successfully amd collection is present in rownNo" + i);
				collectionRowNo = i;
				i = a;
				break;

			} else {
				base.stepInfo("Respective Collection is not present in row" + i);
			}

		}

		return a;

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: Select collection id from the list present in Collection Wizard
	 * @param rowNo
	 */
	public void selectCollectionIdFromTheCollectionListPresent(int rowNo) {

		driver.waitForPageToBeReady();

		base.waitForElement(getCollectionFieldValuesRowNoInCollectionHomePage(rowNo, 1));
		getCollectionFieldValuesRowNoInCollectionHomePage(rowNo, 1).waitAndClick(5);

		if (getCollectionRetrievedCountTextFromCollectionIdsList().isElementAvailable(10)) {

			base.waitForElement(getCollectionRetrievedCountFromCollectionIdsList());
			String retrievedCount = getCollectionRetrievedCountFromCollectionIdsList().getText();
			base.passedStep(
					"1. Column name 'Retrieved Count' is available on 'Collections Details Pop up' successfully 2. Retrieved file count in dataset is:"
							+ retrievedCount);

		} else {
			base.failedStep("'Collections Details Pop up' is not opened");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To click next button and Start A Collection
	 */
	public void clickOnNextAndStartAnCollection() {

		driver.waitForPageToBeReady();
		if (getNextBtnDS().isElementAvailable(5)) {
			base.waitForElement(getNextBtnDS());
			getNextBtnDS().waitAndClick(5);
		} else {
			base.stepInfo("Next Button is already clicked");
			System.out.println("Next Button is already clicked");
		}

		base.waitForElement(getStartCollectionButton());
		getStartCollectionButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.stepInfo("Clicked Start and Initiated collection");
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify View Error and Errored Datsets Page
	 */
	public void verifyViewErrorDatasetsLink() {

		driver.waitForPageToBeReady();

		for (int i = 1; i < 5; i++) {
			base.stepInfo("View Error Report link is still loading in the Collection Wizard");
			base.waitTime(10);
			driver.Navigate().refresh();
		}
		String viewErrorReport = getCollectionFieldValuesRowNoInCollectionHomePage(6, 1).getText();
		System.out.println(viewErrorReport);
		if (viewErrorReport.contains("View Error Report")) {
			getCollectionFieldValuesRowNoInCollectionHomePage(6, 1).waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getErrorReportPageShowDetailsButton());
			getErrorReportPageShowDetailsButton().waitAndClick(5);
			driver.waitForPageToBeReady();
			if (getErrorReportPageRetrievalTable().isElementAvailable(5)
					&& getErrorReportPageRetrievalTableErrorCount().isDisplayed()
					&& getErrorReportPageRetrievalTableErrorCountNum().isDisplayed()) {

				String errorCount = getErrorReportPageRetrievalTableErrorCountNum().getText();
				System.out.println(errorCount);
				base.passedStep(
						"For any errored dataset - Column “Error Count“ is display in Separate column Data tables. (Dataset Creation) in Error section pop up"
								+ "2.The displayed Error count per dataset is:" + errorCount);

			} else {
				base.failedStep("Errored Datasettable is not visible");
			}
		} else {

			base.failedStep("View Error Report link is not present in the Collection Wizard");

		}

	}

	/**
	 * @Author Jeevitha
	 * @Description : verify summary and start Tab attributes
	 */
	public void verifySummaryAndStartAttributes() {

		String[] expectedAttri = { "Collection ID:", "Destination Path:", "Source Location:", "Initiate Processing:",
				"Collection Name:" };
		List<String> attributes = base.getAvailableListofElements(getAttributesOfSummaryTab());

		String passMsg = "Attributes from Collection Information is : " + attributes;
		String failMsg = "Attributes are not as expected";
		base.compareArraywithDataList(expectedAttri, attributes, true, passMsg, failMsg);
	}

	/**
	 * @Author jeevitha
	 * @Description :verify bull horn notification
	 * @param initialBg
	 * @param expctedNotify
	 */
	public void verifyNotification(int initialBg, int expectedBgCount, String expctedNotify) {
		base.checkNotificationCount(initialBg, expectedBgCount);
		int aftrBg = base.initialBgCount();
		if (initialBg < aftrBg) {
			base.passedStep("Recieved Notification");
			base.getBullHornIcon().waitAndClick(10);
			driver.waitForPageToBeReady();
			String notificationMsg = base.getFirstBackRoundTask().getText();
			if (notificationMsg.contains(expctedNotify)) {
				base.passedStep("Recieved Notification is : " + notificationMsg);
			}
		} else {
			base.failedStep("Did Not Recieve Notification");
		}
		base.getBullHornIcon().waitAndClick(10);
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify Collection Header and List of colelction present
	 */
	public void getCollectionPageHeaderList() {

		driver.waitForPageToBeReady();
		base.waitTime(2);
		int collectionHeaderList = getDataSetDetailsHeader().size();
		System.out.println(collectionHeaderList);

		for (int i = 1; i < collectionHeaderList; i++) {
			Element elementByIndex = getCollectionHeaderList(i);
			base.waitTime(2);
			String collectionHeader = elementByIndex.getText();

			System.out.println("Collection HeaderList No:" + i + ":" + collectionHeader);
			base.stepInfo("Collection HeaderList No:" + i + ":" + collectionHeader);

		}
		base.waitForElement(getCollectionListDataset(1));
		Element collectionListDataset = getCollectionListDataset(1);
		String collectionList = collectionListDataset.getText();
		System.out.println("Collection ID:" + collectionList);
		base.stepInfo("Collection ID:" + collectionList);
		base.waitForElement(getCollectionListDatasetValueCollectionName());
		String collectionName = getCollectionListDatasetValueCollectionName().getText();
		System.out.println("Collection Name:" + collectionName);
		base.stepInfo("Collection Name:" + collectionList);
		base.waitForElement(getCollectionListFieldValueRunByAndSourceLocationText(3));
		String collectionRunBy = getCollectionListFieldValueRunByAndSourceLocationText(3).getText();
		System.out.println("Collection RunBy:" + collectionRunBy);
		base.stepInfo("Collection RunBy:" + collectionRunBy);
		base.waitForElement(getCollectionListFieldValueRunByAndSourceLocationText(4));
		String collectionSourceLoaction = getCollectionListFieldValueRunByAndSourceLocationText(4).getText();
		System.out.println("Collection SourceLoaction:" + collectionSourceLoaction);
		base.stepInfo("Collection SourceLoaction:" + collectionSourceLoaction);
		base.waitForElement(getCollectionFieldValuesInCollectionHomePage(5, 1));
		String collectionStatus = getCollectionFieldValuesInCollectionHomePage(5, 1).getText();
		System.out.println("Collection Status:" + collectionStatus);
		base.stepInfo("Collection Status:" + collectionStatus);
		base.waitForElement(getCollectionListDataset(6));
		Element collectionListDataset1 = getCollectionListDataset(6);
		String collectionErrorStatus = collectionListDataset1.getText();
		System.out.println("Collection Error Status:" + collectionErrorStatus);
		base.stepInfo("Collection Error Status:" + collectionErrorStatus);
		base.waitForElement(getCollectionListFieldValueRunByAndSourceLocationText(7));
		String collectionTotalRetrievedCount = getCollectionListFieldValueRunByAndSourceLocationText(7).getText();
		System.out.println("Collection TotalRetrievedCount:" + collectionTotalRetrievedCount);
		base.stepInfo("Collection TotalRetrievedCount:" + collectionTotalRetrievedCount);
		base.waitForElement(getCollectionListFieldValueRunByAndSourceLocationText(8));
		String collectionProgress = getCollectionListFieldValueRunByAndSourceLocationText(8).getText();
		System.out.println("Collection Progress:" + collectionProgress);
		base.stepInfo("Collection Progress:" + collectionProgress);
		base.waitForElement(getCollectionListDataset(9));
		Element collectionListDataset2 = getCollectionListDataset(9);
		String collectionAction = collectionListDataset2.getText();
		System.out.println("Collection Action:" + collectionAction);
		base.stepInfo("Collection Action:" + collectionAction);

		System.out.println(
				"All configured Collections and associated properties are available on 'Manage Collections screen (Grid).");
		base.stepInfo(
				"All configured Collections and associated properties are available on 'Manage Collections screen (Grid).");

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify Notification Icon changing
	 */
	public void verifyNotificationIcon(int bgCountBefore) {

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == bgCountBefore + 1;
			}
		}), Input.wait120);
		final int bgCountAfter = base.initialBgCount();

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To enter on Keyword filter and save
	 */
	public void applyFilterToKeyword(String keyword) {

		driver.waitForPageToBeReady();

		if (getFilterButton().isElementAvailable(5)) {

			base.waitForElement(getFilterButton());
			getFilterButton().waitAndClick(15);

			base.waitForElement(getFilteEnablerButton());
			getFilteEnablerButton().waitAndClick(15);
			base.passedStep("Filter button is Enabled successfully");

		}

		else {
			base.failedStep("FIlter button is disabled");
		}

		if (getKeyboardFilterButton().isElementAvailable(15)) {
			base.waitForElement(getKeyboardFilterButton());
			getKeyboardFilterButton().waitAndClick(15);

			base.waitForElement(getKeywordTextBox());
			getKeywordTextBox().SendKeys(keyword);

			base.passedStep("Keywords are entered successfully");
		} else {
			base.failedStep("Keywords are not entered");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param action
	 * @param verifyMsg
	 */
	public void confirmationAction(String action, String verifyMsg, boolean addition) {
		try {
			base.waitForElement(getConfirmationBtnAction(action));
			getConfirmationBtnAction(action).waitAndClick(5);
			driver.waitForPageToBeReady();
			base.VerifySuccessMessage(verifyMsg);
			base.CloseSuccessMsgpopup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram.A
	 * @param dataName
	 * @param colllectionData
	 * @param AutoInitiate
	 * @return
	 */
	public HashMap<String, String> dataSetsCreationBasedOntheGridAvailabilityT(String dataName,
			HashMap<String, String> colllectionData, Boolean AutoInitiate) {

		base.waitForElement(getCollectioName());
		verifyCurrentTab("Collection Information");

		// Enter Collection Name an initiate collection process
		enterCollectionName(dataName);
		String collectionID = getCollectionID().getText();
		colllectionData.put(dataName, collectionID);
		selectInitiateCollectionOrClickNext(AutoInitiate, true, true);

		return colllectionData;

	}

	/**
	 * @author Raghuram.A
	 * @param dataName
	 * @param firstName
	 * @param lastName
	 * @param collectionEmailId
	 * @param selectedApp
	 * @param colllectionData
	 * @param selectedFolder
	 * @param headerList
	 * @param creationType
	 * @param retryAttempt
	 * @param AutoInitiate
	 * @param saveAction
	 * @param additional1
	 * @param additional2
	 * @return
	 */
	public HashMap<String, String> fillinDS(String dataName, String firstName, String lastName,
			String collectionEmailId, String selectedApp, HashMap<String, String> colllectionData,
			String selectedFolder, String[] headerList, String creationType, int retryAttempt, Boolean AutoInitiate,
			String saveAction, Boolean subFolderFlag, String subFolderName) {

		// Add DataSets
		String dataSetNameGenerated = addDataSetWithHandles(creationType, firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, dataName, retryAttempt);
		
		System.out.println("dataSetNameGenerated"+dataSetNameGenerated);

		// Folder Selection
		if(subFolderFlag) {
			folderToSelect(selectedFolder, true, true,subFolderName);
			
		}
		else {
			folderToSelect(selectedFolder, true, false);
		}
		applyAction("Save", "Confirm", "Dataset added successfully.");
		driver.waitForPageToBeReady();

		// verify DataSet Contents
		verifyDataSetContents(headerList, firstName, lastName, selectedApp, collectionEmailId, dataSetNameGenerated,
				selectedFolder, "-", subFolderName, subFolderFlag, 0);

		return colllectionData;

	}

	public void verifyErrorMessageOfDatasetTab(boolean clickSaveBtn, boolean custodianErrorMsg,
			boolean datasetErrorMsg) {
		if (clickSaveBtn) {
			base.waitForElement(getActionBtn("Save"));
			getActionBtn("Save").waitAndClick(5);
		}

		driver.waitForPageToBeReady();
		if (custodianErrorMsg) {
			base.waitForElement(getCustodianNameErrorMsg());
			String actualErrorMsg = getCustodianNameErrorMsg().getText();
			String expectedErrorMsg = "Please select a custodian name/email.";
			base.textCompareEquals(actualErrorMsg, expectedErrorMsg, "Custodian Error Msg is : " + actualErrorMsg,
					" as expected");
		}
		if (datasetErrorMsg) {
			base.waitForElement(getDatasetNameErrorMsg());
			String actualDatasetsErrorMsg = getDatasetNameErrorMsg().getText();
			String expectedDatasetsErrorMsg = "Please enter dataset name.";
			base.textCompareEquals(actualDatasetsErrorMsg, expectedDatasetsErrorMsg,
					"Datasets Error Msg is : " + actualDatasetsErrorMsg, " as expected");
		}
	}

	/**
	 * @author Raghuram.A
	 */
	public void verifyDataSetContentsCustomize(ElementCollection elementHeader, String[] headerList, String firstName,
			String lastName, String selectedApp, String collectionEmailId, String dataSetNameGenerated,
			String selectedFolder, String retrivalCount, String DateOrKeyword, String additional2, Boolean additional3,
			int additional4) {

		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		for (int i = 0; i <= headerList.length - 1; i++) {
			int index = base.getIndex(elementHeader, headerList[i]);
			colllectionDataHeadersIndex.put(headerList[i], index);
		}

		base.printHashMapDetails(colllectionDataHeadersIndex);

		for (int j = 0; j <= colllectionDataHeadersIndex.size() - 1; j++) {
			String expValue = " - ";

			if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader1)) {
				expValue = firstName + " " + lastName;
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader2)) {
				expValue = selectedApp;
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader3)) {
				expValue = collectionEmailId;
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader4)) {
				expValue = dataSetNameGenerated;
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader5)) {
				expValue = selectedFolder;
			} else if (headerList[j].equalsIgnoreCase(Input.dateKeywordHeaderC)) {
				expValue = DateOrKeyword;
			} else if (headerList[j].equalsIgnoreCase(Input.retreivingDSCountH)) {
				expValue = retrivalCount;
			}

			// DataSet details comparision
			base.stepInfo(headerList[j]);
			base.textCompareEquals(expValue,
					getDataSetDetails(dataSetNameGenerated, colllectionDataHeadersIndex.get(headerList[j])).getText(),
					"Displayed as expected", "Not Displayed as expected");
		}
	}

	public void verifyCollectionPausedStatus(String collectionName,
			HashMap<String, Integer> colllectionDataHeadersIndex, String additional, Boolean additional1) {

		// Verify Collection progress bar presence
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(getCollectionProgressBar(collectionName,
						colllectionDataHeadersIndex.get(Input.collectionProgressH))),
				"Collection is Paused", "Collection is not yet paused", "Pass");
		base.stepInfo(
				getCollectionPauseStats(collectionName, colllectionDataHeadersIndex.get(Input.collectionProgressH))
						.getText());

		// Error Color code verification
		String statsColor = base.getCSSValue(
				getCollectionStatsDiv(collectionName, colllectionDataHeadersIndex.get(Input.collectionStatusH)),
				"color");
		String bgColorHexa = base.rgbTohexaConvertorCustomized(statsColor, 4);
		base.textCompareEquals(Input.collectionErrColorCodeOrange, bgColorHexa,
				"When collection gets paused due to some errors then it's displayed in Orange/Yellow colour coded on \"Manage Collections\" screen.\r\n"
						+ "",
				"Error color code failed");
	}

	public void collectionAction(String collectionName, String actionType, Boolean confirmation,
			String confirmationAction, Boolean bellyBandText, String expectedTxt) {
		if (getCollectionAction(collectionName).isElementAvailable(5)) {
			getCollectionAction(collectionName).waitAndClick(5);
			getCollectionActionList(collectionName, actionType).waitAndClick(10);
			base.stepInfo("Clicked : " + actionType);

			if (bellyBandText) {
				String actualTxt = getPopupMsg().getText();
				String passMsg = "Displayed Popup Msg : " + actualTxt;
				String failMsg = "Belly band msg is not as expected";
				base.compareTextViaContains(actualTxt, expectedTxt, passMsg, failMsg);
			}

			if (confirmation) {
				getConfirmationBtnAction(confirmationAction).waitAndClick(10);
				driver.waitForPageToBeReady();
			}

			if (actionType.equalsIgnoreCase("Start Collection")) {
				base.VerifySuccessMessage("Collection extraction process started successfully.");
			}
			if (actionType.equalsIgnoreCase("Ignore Errors and Continue")
					&& confirmationAction.equalsIgnoreCase("Yes")) {
				base.VerifySuccessMessage(
						"The application has initiated the action to ignore the errors and continue the collection from where it was paused.");
			}
		}
	}

	/**
	 * @author Raghuram.A
	 * @param username
	 * @param password
	 * @param role
	 * @param actionRole
	 * @param actionUserName
	 * @param actionPassword
	 * @return
	 * @throws Exception
	 * @description : Pre-requesties for Collection draft creation
	 */
	public HashMap<String, String> verifyUserAbleToSaveCollectionAsDraft(String username, String password, String role,
			String actionRole, String actionUserName, String actionPassword, String selectedFolder,
			String additional1Status, Boolean additional2) throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		DataSets dataSets = new DataSets(driver);
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String dataName;
		HashMap<String, String> colllectionDataToReturn = new HashMap<>();

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, additional1Status, "Button", 3, true, "");

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);
		colllectionDataToReturn.put(collectionID, dataName);

		// Verify Collection presence
		verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus, true, false,
				"");
		base.passedStep("Pre-requestied created colleciton Name :" + dataName);

		// return dataNmae created / used
		return colllectionDataToReturn;
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify loading Icon on Dataset folder
	 */
	public void loadingIconOnDataSetPage() {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getFolderabLabel());
			getFolderabLabel().waitAndClick(5);

			// validation for Loading Icon
			if (getLoadingFoldersIcon().isElementAvailable(3)) {
				base.passedStep("Processing icon is displayed for Node select/unselect on folder tree successfully");
			} else if (getRefreshButtonInSelectFolderField().isElementAvailable(2)) {
				base.waitForElement(getRefreshButtonInSelectFolderField());
				getRefreshButtonInSelectFolderField().waitAndClick(5);
				getLoadingFoldersIcon().isElementAvailable(3);
				base.passedStep("Processing icon is displayed for Node select/unselect on folder tree successfully");
			} else {
				base.failedStep("Failed to check processing Icon is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram.A
	 * @param filename
	 * @param rowNumToStart
	 * @param headerName
	 * @return
	 */
	public HashMap<String, String> testreadExcelData(String filename, int sheetNumber, int rowNumToStart,
			String headerName, String additional1, Boolean additional2) {
		try {
			HashMap<String, String> colllectionData = new HashMap<>();
			Boolean toBreak = false;
			Boolean status = false;
			Row xlRows = null;
			int temp = 0;

			// Get Sheet Object
			Sheet xlSheet = base.sheetDataObject(filename, sheetNumber);

			// Get total number of rows in the respective sheet
			int numRows = xlSheet.getLastRowNum() + 1;
			System.out.println("No of Rows : " + numRows);

			// HeaderName - "Collection Summary"
			for (int i = rowNumToStart; i < numRows; i++) {
				// Dataset Summary
				xlRows = xlSheet.getRow(i);
				if (xlRows != null) {
					String ab = xlRows.getCell(0).toString();
					System.out.println(ab);
					if (ab.equals(headerName)) {
						status = true;
						temp = i;
						break;
					}
				}
			}

			// Fetch Column datas
			int numCols = xlSheet.getRow(temp + 1).getLastCellNum();
			System.out.println("NO of columns : " + numCols);

			// Extract and Map Datas
			if (status) {
				String xlcell = "";
				for (int k = temp + 1; k < numRows; k++) {
					if (toBreak) {
						break;
					}
					xlRows = xlSheet.getRow(k);
					System.out.println(xlSheet.getRow(k).getLastCellNum());
					for (int j = 0; j < xlSheet.getRow(k).getLastCellNum(); j++) {
						if (xlRows.getCell(j) != null) {
							int checkL = xlRows.getCell(j).toString().length();
							if (checkL > 1) {
								xlcell = xlRows.getCell(j).toString();
								System.out.println(xlcell);
								if (j == 0) {
									colllectionData.put(xlcell, xlRows.getCell(j + 1).toString());
								} else if (xlcell.contains("Phase")) {
									if (xlRows.getCell(j + 1).toString().equals("Success")) {
										colllectionData.put(xlcell, xlRows.getCell(j + 1).toString());
									} else if (xlRows.getCell(j + 1).toString().equals("Failure")) {
										colllectionData.put(xlcell, xlRows.getCell(j + 2).toString());
									}
								}
							} else if (checkL < 1 && j == 0) {
								toBreak = true;
								break;
							}
						}
					}
				}
			}

			UtilityLog.info("Data from excel sheet retrieved successfully");
			return colllectionData;
		} catch (Exception E) {
			E.printStackTrace();
			UtilityLog.info(E.toString());
			return null;
		}
	}
}

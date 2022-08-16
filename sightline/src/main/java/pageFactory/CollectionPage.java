package pageFactory;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
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
	 * @Description : verify Collection information Page after clciking speciifed
	 *              source location
	 * @param expectedSrc
	 * @param collectionName
	 * @param Next
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
			base.stepInfo("Entered collection Name : " + collectionName);
		} else {
			base.failedStep("Collection Name Field is Not displayed");
		}

		String collectionID = getCollectionID().getText();
		colllectionData.put(collectionName, collectionID);

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
		String retrivedData = getDataSetNameTextFIeld().GetAttribute("value");
		base.stepInfo("Actual populated dataset name : " + retrivedData);

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
		} else if (type.equalsIgnoreCase("Cancel")) {

		} else if (type.equalsIgnoreCase("Delete")) {
			getConfirmationBtnAction(action).waitAndClick(5);
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
		getFolderNameToSelect(folderName).waitAndClick(5);
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
			String additional2, Boolean additional3, int additional4) {

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
			} else if (headerList[j].equalsIgnoreCase(Input.collectionDataHeader5)) {
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

			if (getPopupMsg().isElementAvailable(3)) {
				String expectedMsg = "Are you sure you want to delete the collection?";
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
		if (selectCollFromList && getCollectionNameStatusElements(expectedStatus).size() > 0) {
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
}

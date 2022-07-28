package pageFactory;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

	// Added by Mohan
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
		if (type.equalsIgnoreCase("Save")) {
			if (getFolderSelectionConfirmation().isElementAvailable(5)) {
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
			String collectionEmailId, String dataSetNameGenerated, String selectedFolder, String additional1,
			String additional2, Boolean additional3, int additional4) {

		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		for (int i = 0; i <= headerList.length - 1; i++) {
			int index = base.getIndex(getDataSetDetailsHeader(), headerList[i]);
			colllectionDataHeadersIndex.put(headerList[i], index);
		}

		base.printHashMapDetails(colllectionDataHeadersIndex);

		for (int j = 0; j <= headerList.length - 1; j++) {
			String expValue = "-";

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
			String selectedFolder, String[] headerList, String expectedStatus, String creationType, int retryAttempt,Boolean additional1,
			String additional2) {

		String dataName = "Automation" + Utility.dynamicNameAppender();
		String creationStatus = "0";

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
			colllectionData = verifyCollectionInfoPage(dataSourceName, dataName, true);

			// Add DataSets
			String dataSetNameGenerated = addDataSetWithHandles(creationType, firstName, lastName, collectionEmailId,
					selectedApp, colllectionData, dataName, retryAttempt);

			// Folder Selection
			folderToSelect(selectedFolder, true, false);
			applyAction("Save", "Confirm", "Dataset added successfully.");
			driver.waitForPageToBeReady();

			// verify DataSet Contents
			verifyDataSetContents(headerList, firstName, lastName, selectedApp, collectionEmailId, dataSetNameGenerated,
					selectedFolder, "", "", false, 0);

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

}

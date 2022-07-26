package pageFactory;

import java.util.ArrayList;
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

	public Element getCollectErrorMsg() {
		return driver.FindElementByXPath("//span[@id='txtCollectionName-error']");
	}

	public ElementCollection getListOfSrcLoc() {
		return driver.FindElementsByXPath("//div[@id='divSourceLocations']//div[@class='popout text-wrap']");
	}
	
	//Added by Mohan
	public Element getStartALinkCollection() {
		return driver.FindElementByXPath("//a[text()='Start a collection']");
	}
	
	public ElementCollection getCollectionPageFirstRowValue() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHeadInner']//tr//th[@aria-controls=\"dtCollectionList\"]");
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
		} else if(!listOfSrcLoc.equals(null)){
			availSrcLoc = base.availableListofElements(getListOfSrcLoc());
			String passMsg="Displayed Source Location is : "+availSrcLoc;
			String failMsg="Source location list is not as expected";
			base.listCompareEquals(listOfSrcLoc, availSrcLoc, passMsg, failMsg);
			
		}else {
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
	 */
	public void verifyCollectionInfoPage(String srceLocation, String collectionName, boolean Next) {
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
		
		if (collectionPage>3) {
			base.passedStep("Manage Collections screen is opened successfully");
			
		}else {
			base.failedStep("Manage Collections screen is not opened");
		}
		
	}
}

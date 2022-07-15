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

		String actualTab = getNewCollPageHeader().getText();
		String expectedTab = "Source Selection";
		base.compareTextViaContains(actualTab, expectedTab, "Current Tab : " + actualTab, "Current Tab : " + actualTab);
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

		base.waitForElement(getAddNewSource());
		getAddNewSource().waitAndClick(10);
		base.stepInfo("Clicked Add new source location button");

		if (getAddNewSourcePopUp().isElementAvailable(10)) {
			base.passedStep("Add New Source location Popup is opened");

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

		}
	}
}

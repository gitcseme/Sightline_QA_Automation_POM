package pageFactory;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class SourceLocationPage {
	Driver driver;
	BaseClass base;
	SoftAssert softassert;

	public Element getSrcDeleteBtn(String srceLoc) {
		return driver.FindElementByXPath("//td[text()='" + srceLoc + "']//parent::tr//a[text()='Delete']");
	}

	public Element getDeletePopUpHeader() {
		return driver.FindElementByXPath("//span[@class='MsgTitle']");
	}

	public Element getDeleteMsg() {
		return driver.FindElementByXPath("//p[@class='pText']");
	}

	public Element getDeleteYesBtn() {
		return driver.FindElementByXPath("//button[normalize-space()='Yes']");
	}

	public Element getAddNewSrcLoction() {
		return driver.FindElementByXPath("//input[@id='btnAddSourceLocaiton']");
	}

	public ElementCollection getTableHeader() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHeadInner']//tr//th[@aria-controls=\"dtSourceList\"]");
	}

	public ElementCollection getColumnValues(int i) {
		return driver.FindElementsByXPath("//tr[@role='row']//td[" + i + "]");
	}

	public SourceLocationPage(Driver driver) {
		this.driver = driver;
		base = new BaseClass(driver);
		softassert = new SoftAssert();
	}

	/**
	 * @Author Jeevitha
	 * @Dsecription : delete created source location
	 * @param srcLoction
	 * @param delete
	 */
	public void deleteSourceLocation(String srcLoction, boolean delete) {
		String expectedUrl = Input.url + Input.sourceLocationPageUrl;
		base.verifyPageNavigation(expectedUrl);

		base.waitForElement(getSrcDeleteBtn(srcLoction));
		getSrcDeleteBtn(srcLoction).waitAndClick(10);

		String expectedMsg = "If you delete this source location, you will no longer be able to use any collections configured with this source location. Do you want to really delete this source location?";
		base.waitForElement(getDeletePopUpHeader());
		softassert.assertEquals("Delete Source Location", getDeletePopUpHeader().getText().toString());
		base.passedStep("Displayed Header is : Delete Source Location");

		base.waitForElement(getDeleteMsg());
		softassert.assertEquals(expectedMsg, getDeleteMsg().getText().toString());
		base.passedStep("Displayed message - " + expectedMsg);

		getDeleteYesBtn().waitAndClick(10);
		base.VerifySuccessMessage("Source Location deleted successfully");
		softassert.assertAll();
	}

	/**
	 * @Author Jeevitha
	 * @Description : get the Table Value from source location page
	 * @param headerName
	 * @return
	 */
	public List<String> getListFromTable(String headerName, boolean prefixSuffix) {
		int i;
		i = base.getIndex(getTableHeader(), headerName);
		System.out.println(headerName + "--" + i);

		driver.waitForPageToBeReady();
		List<String> elementNames = new ArrayList<>();
		List<WebElement> elementList = null;
		elementList = getColumnValues(i).FindWebElements();
		for (WebElement webElementNames : elementList) {
			String elementName = webElementNames.getText();
			base.stepInfo("Available Source Location : "+elementName);
			if (prefixSuffix) {
				elementNames.add("(" + elementName + ")");
			} else {
				elementNames.add(elementName);
			}
		}
		return elementNames;
	}

}

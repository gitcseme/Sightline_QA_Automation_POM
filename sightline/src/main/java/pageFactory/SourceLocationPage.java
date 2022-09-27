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
	public Element getSetUpASourceLocation() {
		return driver.FindElementById("divAddNewSourceLocation");
	}
	
	public Element getSetUpASourceLocationLink() {
		return driver.FindElementByXPath("//a[text()='Set up a source location']");
	}
	
	
	public ElementCollection getSourceLocationListTable() {
		return driver.FindElementsByXPath("//*[@id='dtSourceList']//tbody//tr");
	}
	
	
	public Element getSetUpASourceLocationDeleteButton() {
		return driver.FindElementByXPath("//*[@id='dtSourceList']//tr[1]//a[text()='Delete']");
	}
	
	public Element getSetUpASourceLocationDeleteYesButton() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}
	
	public Element getDataSourceName(String sourceName) {
		return driver.FindElementByXPath("//table[@id='dtSourceList']//tr//td[text()='"+sourceName+"']");
	}
	
	public Element columnHeader(String headerName) {
		return driver.FindElementByXPath("//th[text()='" + headerName + "']");
	}

	public Element getSrcActionBtn(String srceLoc, String actionType) {
		return driver.FindElementByXPath("//td[text()='" + srceLoc + "']//parent::tr//a[text()='" + actionType + "']");
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
	
	
	
	/**
	 * @author Mohan.Venugopal
	 * @description To Verify Source Location link is present in Collections Page.
	 * 
	 */
	public void verifySourceLocationIsNotPresent() {

		
		driver.waitForPageToBeReady();
		
		if (getSetUpASourceLocation().isElementAvailable(5)) {
			base.stepInfo("The Set Up for Source link is present in New Collection Page");
			
		}else {
			driver.Navigate().to("https://sightlinept.consilio.com/Collection/SourceLocation");
			
			ElementCollection locationListTable = getSourceLocationListTable();
			int locationListSize = locationListTable.size();
			System.out.println(locationListSize);
			
			for (int i = 0; i < locationListSize; i++) {
				driver.waitForPageToBeReady();
				base.waitTime(2);
				if (getSetUpASourceLocationDeleteButton().isElementAvailable(5)) {
					
					base.waitForElement(getSetUpASourceLocationDeleteButton());
					getSetUpASourceLocationDeleteButton().waitAndClick(5);
					
					base.waitForElement(getSetUpASourceLocationDeleteYesButton());
					getSetUpASourceLocationDeleteYesButton().waitAndClick(5);
					
					base.VerifySuccessMessage("Source Location deleted successfully");
					
				}else {
					base.stepInfo("All Source Locations are deleted successfully");
					base.passedStep("Source Location Link is Available now");
				}
				
				
			}
			
		}
		
		
	}
	
	
	/**
	 * @author Mohan.Venugopal
	 * @description: To verify source location name in source location page.
	 * @param sourceName
	 */
	public void verifySourceLocationName(String sourceName) {

		base.waitForElement(getDataSourceName(sourceName));
		String dataSourceName = getDataSourceName(sourceName).getText();
		System.out.println(dataSourceName);
		softassert.assertEquals(sourceName, dataSourceName);
		softassert.assertAll();
		base.passedStep("Newly added  source location entry is appeared on “Source Locations “screen(grid).");

	}
	


}

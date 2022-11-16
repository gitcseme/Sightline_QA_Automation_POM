package pageFactory;

import java.util.concurrent.Callable;

import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class ManageIPRestrictions {
	Driver driver;
	BaseClass base;
	SoftAssert softAssertion;
	Input in = new Input();

	public Element getIPRestrictionCheck() {
		return driver.FindElementByXPath("//*[@id='frmiprestriction']/div/div/div/label/i");
	}

	public Element getIPRestrictionAddError() {
		return driver.FindElementByXPath("//*[@id='IPRangeFrom-error']");
	}

	public Element getEnterFrom() {
		return driver.FindElementByXPath("//*[@id='IPRangeFrom']");
	}

	public Element getIPRestrictionAdd() {
		return driver.FindElementByXPath("//*[@id='btnadd']");
	}

	public Element getIPRangeTo() {
		return driver.FindElementByXPath("//*[@id='IPRangeTo']");
	}

	public Element getDeleteIPRange() {
		return driver.FindElementByXPath("//*[@id='vbg']");
	}

	public Element getIPValue(String IPvalue) {
		return driver.FindElementByXPath("//table[@id='tblip']/tbody/tr/td[contains(text(),'" + IPvalue + "')]");
	}

	public Element getIPDeleteMessage() {
		return driver.FindElementByXPath("//*[@id='Msg1']");
	}

	public Element getYesButton() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getCancelBtn() {
		return driver.FindElementById("btnCancel");
	}

	public Element getIPDuplicateError() {
		return driver.FindElementByXPath("//*[@id='lblerrormsg']");
	}
	public Element getIPRangeErrorMsg() {
		return driver.FindElementById("lblerrormsg");
	}
	public ManageIPRestrictions(Driver driver) {

		this.driver = driver;
//		this.driver.getWebDriver().get(Input.url + "Project/CreateIPRestriction");
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Sakthivel
	 * @Description : Method for navigating to IPReduction page.
	 */
	public void navigateToManageIPPageURL() {
		try {
			driver.getWebDriver().get(Input.url + "Project/CreateIPRestriction");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to redaction page is failed" + e.getMessage());
		}
	}

	/**
	 * @author Vijaya.Rani
	 * @Description : Create Manage IP range
	 */
	public void createIPrange(String IPfrom, String IPTo) {
		driver.waitForPageToBeReady();
		navigateToManageIPPageURL();
		driver.waitForPageToBeReady();
		base.waitForElement(getIPRestrictionCheck());
		getIPRestrictionCheck().waitAndClick(5);
		driver.waitForPageToBeReady();
		getEnterFrom().SendKeys(IPfrom);
		base.waitForElement(getIPRangeTo());
		getIPRangeTo().SendKeys(IPTo);
		base.waitForElement(getIPRestrictionAdd());
		getIPRestrictionAdd().waitAndClick(5);
		base.stepInfo("Manage IP Range Added Successfully");
	}

	/**
	 * @author Vijaya.Rani
	 * @Description : delete Manage IP range
	 */
	public void deleteIPrange(String IPFrom) {
		driver.waitForPageToBeReady();
		base.waitForElement(getIPValue(IPFrom));
		getIPValue(IPFrom).waitAndClick(2);
		base.waitForElement(getDeleteIPRange());
		getDeleteIPRange().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIPDeleteMessage().Visible();
			}
		}), Input.wait30);
		if (getIPDeleteMessage().isElementPresent() == true) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getYesButton().Visible();
				}
			}), Input.wait30);
			getYesButton().Click();
		} else {
			Assert.fail("Failed to delete the IP Range");
		}
		base.stepInfo("IP Range deleted successfully");

	}

	/**
	 * @author Vijaya.Rani
	 * @Description : Create Manage IP range add Same Range
	 */
	public void createIPrangeErrorMsgduplicate(String IPfrom, String IPTo) {
		SoftAssert softAssertion = new SoftAssert();
		driver.waitForPageToBeReady();
		getEnterFrom().SendKeys(IPfrom);
		base.waitForElement(getIPRangeTo());
		getIPRangeTo().SendKeys(IPTo);
		base.waitForElement(getIPRestrictionAdd());
		getIPRestrictionAdd().waitAndClick(5);
		base.waitForElement(getIPDuplicateError());
		String expectedErrorMsg=getIPDuplicateError().getText();
		String actualErrorMsg="IP Range already exists in the IP restrictions.";
		softAssertion.assertEquals(actualErrorMsg, expectedErrorMsg);
		base.passedStep("The Error Msg is Displayed");
		softAssertion.assertAll();
		}
}

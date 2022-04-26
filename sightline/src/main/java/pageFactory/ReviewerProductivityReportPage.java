package pageFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ReviewerProductivityReportPage {
	Driver driver;
	BaseClass bc;
	SoftAssert softAssertion;

	public Element getReviewerProductivityReportButton() {
		return driver.FindElementByXPath("//tr//a[text()='Reviewer Productivity Report']");
	}

	public Element getReviewerExpandButton() {
		return driver.FindElementByXPath(
				"//span[text()='Select Reviewers ']/parent::strong/following-sibling::a[@data-toggle='collapse']");
	}

	public Element getReviewerChkBox() {
		return driver.FindElementByXPath("//h4[contains(.,'Reviewers')]/parent::label/i");
	}

	public Element getDataRangeExpandBtn() {
		return driver.FindElementByXPath(
				"//span[text()='Completed Date Range']/parent::strong/following-sibling::a[@data-toggle='collapse']");
	}

	public Element getFromDateRangeTxtBox() {
		return driver
				.FindElementByXPath("//label[text()='FROM']/parent::div//input[@class='form-control hasDatepicker']");
	}

	public Element getToDateRangeTxtBox() {
		return driver
				.FindElementByXPath("//label[text()='TO']/parent::div//input[@class='form-control hasDatepicker']");
	}

	public Element getDatePicker() {
		return driver.FindElementByXPath("//td/a[@class='ui-state-default ui-state-highlight']");
	}

	public Element getApplyChanges() {
		return driver.FindElementById("btn_applychanges");
	}

	public Element getTableHeader(String value) {
		return driver.FindElementByXPath("//thead/tr/th[text()='" + value + "']");
	}

	public ElementCollection getTableHeaders() {
		return driver.FindElementsByXPath("//table[@id='ReviewerProductivityReportID']/thead/tr/th");
	}

	public Element getTableColumnValue(int i) {
		return driver.FindElementByXPath("//table[@id='ReviewerProductivityReportID']/tbody/tr/td[" + i + "]");
	}

	public Element getAssignmentExpandButton() {
		return driver.FindElementByXPath(
				"//span[text()='Assignment Group']/parent::strong/following-sibling::a[@data-toggle='collapse']");
	}

	public Element getAssignmentChkBox(String assgnName) {
		return driver.FindElementByXPath("//a[text()='" + assgnName + "']/i[@class='jstree-icon jstree-checkbox']");
	}

	public ReviewerProductivityReportPage(Driver driver) {
		this.driver = driver;
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
	}

	public void navigateTOReviewerPodReportPage() {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String expectedURL = Input.url + "Review/ReviewerProductivityReport";
		bc.waitForElement(getReviewerProductivityReportButton());
		if (getReviewerProductivityReportButton().isDisplayed()) {
			bc.passedStep("Reviewer Productivity Report link is displayed in reports landing page");
			getReviewerProductivityReportButton().Click();
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(expectedURL, driver.getUrl());
			softAssertion.assertAll();
			bc.passedStep("Sucessfully navigated to Reviewer Productivity Report Page");
		} else {
			bc.failedStep("Reviewer Productivity Report link is not  displayed in reports landing page");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	public void generateReport(String assgnName) throws InterruptedException {
		bc.waitForElement(getReviewerExpandButton());
		getReviewerExpandButton().Click();
		bc.waitForElement(getReviewerChkBox());
		getReviewerChkBox().waitAndClick(2);
		bc.stepInfo("Selected all Reviewers.");
		getReviewerExpandButton().waitAndClick(2);
		if (assgnName != null) {
			bc.waitTime(5);
			bc.waitTillElemetToBeClickable(getAssignmentExpandButton());
			getAssignmentExpandButton().waitAndClick(10);
			bc.waitTime(5);
			bc.waitTillElemetToBeClickable(getAssignmentChkBox(assgnName));
			getAssignmentChkBox(assgnName).waitAndClick(10);
			bc.waitTillElemetToBeClickable(getAssignmentExpandButton());
			getAssignmentExpandButton().waitAndClick(10);
		}
		bc.waitForElement(getDataRangeExpandBtn());
		getDataRangeExpandBtn().waitAndClick(10);
		selectDate(getFromDateRangeTxtBox(), "From date range");
		driver.waitForPageToBeReady();
		selectDate(getToDateRangeTxtBox(), "To date range");
		driver.scrollPageToTop();
		getApplyChanges().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param ele
	 * @param text
	 * @throws InterruptedException
	 */
	public void selectDate(Element ele, String text) throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		bc.waitForElement(ele);
		driver.scrollingToElementofAPage(ele);
		ele.SendKeys(dateFormat.format(date));
		bc.stepInfo(dateFormat.format(date) + " is selected in " + text);
	}

	public void verifyColumnDisplay(Element ele, String colName) {
		bc.waitForElement(ele);
		if (ele.isDisplayed()) {
			bc.passedStep(colName + " is displayed in report table.");
		} else {
			bc.failedStep(colName + " is not displayed in report table.");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param ele
	 * @param colName
	 * @return
	 */
	public String verifyColumnValueDisplay(ElementCollection ele, String colName) {
		bc.waitForElementCollection(ele);
		int i = bc.getIndex(ele, colName);
		String value = null;
		if (getTableColumnValue(i).isDisplayed()) {
			bc.passedStep(colName + " is displayed in report table.");
			value = getTableColumnValue(i).getText();
		} else {
			bc.failedStep(colName + " is not displayed in report table.");
		}
		System.out.println(value);
		return value;
	}

}

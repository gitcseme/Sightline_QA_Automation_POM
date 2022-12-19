package pageFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ReviewerCountsReportPage {
	Driver driver;
	BaseClass bc;
	SoftAssert softAssertion;

	public Element getReviewerCountsReportButton() {
		return driver.FindElementByXPath("//tr//a[text()='Reviewer Counts by Day/Hour Report']");
	}

	public Element getReviewerExpandButton() {
		return driver.FindElementByXPath(
				"//span[text()='Select Reviewers ']/parent::strong/following-sibling::a[@data-toggle='collapse']");
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

	public Element getApplyChanges() {
		return driver.FindElementById("btn_applychanges");
	}


	public ElementCollection getTableHeaders() {
		return driver.FindElementsByXPath("//div[@id='datatable_fixed_column_wrapper']//table/thead/tr/th");
	}

	public Element getTableColumnValue(int i) {
		return driver.FindElementByXPath("//div[@id='datatable_fixed_column_wrapper']//table/tbody/tr/td[" + i + "]");
	}

	public Element getAssignmentExpandButton() {
		return driver.FindElementByXPath(
				"//span[text()='Assignment Group']/parent::strong/following-sibling::a[@data-toggle='collapse']");
	}

	public Element getAssignmentChkBox(String assgnName) {
		return driver.FindElementByXPath("//a[text()='" + assgnName + "']/i[@class='jstree-icon jstree-checkbox']");
	}
	
	public Element getDailyRadiobtn() {
		return driver.FindElementByXPath("//div[@class='form-group']//label[@style='display:inline-block;']//i");
	}
	public Element getSelectReviewer(String revFullName) {
		return driver.FindElementByXPath("//div[@id='rvlist']//following::span[text()='" + revFullName + "']//parent::label//child::i");
	}

	//added by sowndarya
	public ElementCollection getReviewersList() {
		return driver.FindElementsByXPath("//div[@id='rvlist']//following::span");
	}

	public Element getSelectFromDateTime() {
		return driver.FindElementByXPath("//input[@id='FromDateTime']");
	}
	public Element getSelectToDateTime() {
		return driver.FindElementByXPath("//input[@id='ToDateTime']");
	}
	public Element getHourlyRadio() {
		return driver.FindElementByXPath("//input[@id='id_radio2']//following-sibling::i");
	}
	public Element getExpandDateRange() {
		return driver.FindElementByXPath("//a[@id='aDateRange']//following-sibling::a");
	}
	
	public Element getSaveReportBtn() {
		return driver.FindElementById("saveReport");
	}

	public Element getSaveBtn() {
		return driver.FindElementById("saveXML");
	}

	public Element getSaveReportName() {
		return driver.FindElementById("txtReportname");
	}
	
	public Element getCustomReport(String report) {
		return driver.FindElementByXPath("//tbody//tr//td//a[text()='" + report +"']");
	}
	
	public ReviewerCountsReportPage(Driver driver) {
		this.driver = driver;
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
	}

	public void navigateTOReviewerCountsReportPage() {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String expectedURL = Input.url + "Review/ReviewerCountsReport";
		bc.waitForElement(getReviewerCountsReportButton());
		if (getReviewerCountsReportButton().isDisplayed()) {
			bc.passedStep("Reviewer Count Report link is displayed in reports landing page");
			getReviewerCountsReportButton().Click();
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(expectedURL, driver.getUrl());
			softAssertion.assertAll();
			bc.passedStep("Sucessfully navigated to Reviewer Count Report Page");
		} else {
			bc.failedStep("Reviewer Count Report link is not  displayed in reports landing page");
		}
	}

	public void generateReport(String assgnName) throws InterruptedException {
		bc.waitForElement(getDailyRadiobtn());
		getDailyRadiobtn().Click();
		bc.waitForElement(getReviewerExpandButton());
		getReviewerExpandButton().Click();
		
		bc.waitForElement(getSelectReviewer(Input.rev1FullName));
		getSelectReviewer(Input.rev1FullName).waitAndClick(2);
		bc.stepInfo("Reviewer user selected : "+Input.rev1FullName);
		
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
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getDataRangeExpandBtn());
		getDataRangeExpandBtn().waitAndClick(10);
		selectFromDate(getFromDateRangeTxtBox(), "From date range");
		driver.waitForPageToBeReady();
		selectToDate(getToDateRangeTxtBox(), "To date range");
		driver.scrollPageToTop();
		getApplyChanges().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	public void selectFromDate(Element ele, String text) throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		bc.waitForElement(ele);
		driver.scrollingToElementofAPage(ele);	
		cal.add(Calendar.DATE, -1);
		ele.SendKeys(dateFormat.format(cal.getTime()));
		bc.stepInfo(dateFormat.format(cal.getTime()) + " is selected in " + text);
	}
	
	public void selectToDate(Element ele, String text) throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		bc.waitForElement(ele);
		driver.scrollingToElementofAPage(ele);
		ele.SendKeys(dateFormat.format(date));
		bc.stepInfo(dateFormat.format(date) + " is selected in " + text);
	}


	public String verifyColumnValueDisplay(ElementCollection ele, String colName) {
		bc.waitForElementCollection(ele);
		int i = bc.getIndex(ele, colName);
		String value = null;
		if (getTableColumnValue(i).isDisplayed()) {
			bc.passedStep(colName + " is displayed in report table.");
			value = getTableColumnValue(i).getText();
			bc.stepInfo("Doc count is displayed in "+colName+ "is "+value);
		} else {
			bc.failedStep(colName + " is not displayed in report table.");
		}
		System.out.println(value);
		return value;
	}

	
	public void selectReviewers(String[] reviwers) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getReviewerExpandButton());
		getReviewerExpandButton().Click();
		for(String rev : reviwers) {
			bc.waitTime(2);
			bc.waitForElement(getSelectReviewer(rev));
			getSelectReviewer(rev).ScrollTo();
			getSelectReviewer(rev).waitAndClick(10);
			bc.waitTime(2);
		}
		
	}
	
	/**
	 * @author: sowndarya
	 * @description: Generate Report multi reviewers
	 */
	public void generateReportMultiRev(String assgnName, String[] reviewers) throws InterruptedException {
	    driver.waitForPageToBeReady();
		selectReviewers(reviewers);	
		getReviewerExpandButton().waitAndClick(5);
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
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getDataRangeExpandBtn());
		getDataRangeExpandBtn().waitAndClick(10);
		selectFromDate(getFromDateRangeTxtBox(), "From date range");
		driver.waitForPageToBeReady();
		selectToDate(getToDateRangeTxtBox(), "To date range");
		driver.scrollPageToTop();
		getApplyChanges().waitAndClick(10);
		driver.waitForPageToBeReady();
	}
}

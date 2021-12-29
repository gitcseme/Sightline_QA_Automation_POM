package pageFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;


public class ReviewerProductivityReportPage {
	Driver driver;
	BaseClass bc;
	SoftAssert	softAssertion;
    public Element getReviewerProductivityReportButton(){ return driver.FindElementByXPath("//tr//a[text()='Reviewer Productivity Report']"); }
    public Element getReviewerExpandButton(){ return driver.FindElementByXPath("//span[text()='Select Reviewers ']/parent::strong/following-sibling::a[@data-toggle='collapse']"); }
    public Element getReviewerChkBox(){ return driver.FindElementByXPath("//h4[contains(.,'Reviewers')]/parent::label/i"); }
    public Element getDataRangeExpandBtn(){ return driver.FindElementByXPath("//span[text()='Completed Date Range']/parent::strong/following-sibling::a[@data-toggle='collapse']"); }
    public Element getFromDateRangeTxtBox(){ return driver.FindElementByXPath("//label[text()='FROM']/parent::div//input[@class='form-control hasDatepicker']"); }
    public Element getToDateRangeTxtBox(){ return driver.FindElementByXPath("//label[text()='TO']/parent::div//input[@class='form-control hasDatepicker']"); }
    public Element getDatePicker(){ return driver.FindElementByXPath("//td/a[@class='ui-state-default ui-state-highlight']"); }
    public Element getApplyChanges() { return driver.FindElementById("btn_applychanges");}
    public Element getTableHeader(String value) {return driver.FindElementByXPath("//thead/tr/th[text()='"+value+"']");}
    
	public ReviewerProductivityReportPage(Driver driver) {
		this.driver = driver;
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
	}
	
	public void navigateTOReviewerPodReportPage() {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String expectedURL=Input.url+"Review/ReviewerProductivityReport";
		bc.waitForElement(getReviewerProductivityReportButton());
		if(getReviewerProductivityReportButton().isDisplayed()){
			bc.passedStep("Reviewer Productivity Report link is displayed in reports landing page");
		getReviewerProductivityReportButton().Click();
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(expectedURL,driver.getUrl());
		softAssertion.assertAll();
		bc.passedStep("Sucessfully navigated to Reviewer Productivity Report Page");
		}
		else {
			bc.failedStep("Reviewer Productivity Report link is not  displayed in reports landing page");
		}
	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	public void generateReport() throws InterruptedException {
		bc.waitForElement(getReviewerExpandButton());
		getReviewerExpandButton().Click();
		bc.waitForElement(getReviewerChkBox());
		getReviewerChkBox().waitAndClick(2);
		bc.stepInfo("Selected all Reviewers.");
		getReviewerExpandButton().waitAndClick(2);
		bc.waitForElement(getDataRangeExpandBtn());
		getDataRangeExpandBtn().waitAndClick(2);
		selectDate(getFromDateRangeTxtBox(),"From date range");
		selectDate(getToDateRangeTxtBox(),"To date range");
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
	public void selectDate(Element ele,String text) throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		bc.waitForElement(ele);
		driver.scrollingToElementofAPage(ele);
		ele.SendKeys(dateFormat.format(date));
		bc.stepInfo(dateFormat.format(date) + " is selected in "+text);
	}
	
	public void verifyColumnDisplay(Element ele,String colName) {
		bc.waitForElement(ele);
		if(ele.isDisplayed()) {
			bc.passedStep(colName+" is displayed in report table.");
		}
		else {
			bc.failedStep(colName+" is not displayed in report table.");	
		}
	}
	
}

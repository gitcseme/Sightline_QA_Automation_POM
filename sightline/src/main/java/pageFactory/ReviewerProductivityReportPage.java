package pageFactory;

import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;


public class ReviewerProductivityReportPage {
	Driver driver;
	BaseClass bc;
	SoftAssert	softAssertion;
    public Element getReviewerProductivityReportButton(){ return driver.FindElementByXPath("//tr//a[text()='Reviewer Productivity Report']"); }

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
	
	
}

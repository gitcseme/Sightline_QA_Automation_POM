package pageFactory;


import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;

public class ReviewerTimeOnlineReport {

	Driver driver;
	BaseClass base;
	SoftAssert softAssertion; 

	public Element getReports_ReviewerTimeOnlineReport() {
		return driver.FindElementByXPath("//span[text()='REVIEW']/parent::div/parent::div//a[@href='/Review/ReviewerTimeOnline']");
	}
	
	public Element getAssignmentGroupExpandIcon() {
		return driver.FindElementByXPath("//a[@id='assignmentGroupID']");
	}
	public Element getReviewerExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseDivReviewer']");
	}
	public Element selectReviewerCheckBox(String reviewer) {
		return driver.FindElementByXPath("//span[text()='"+reviewer+"']");
	}
	public Element selectAssignmentGroupCheckBox(String assignmentGrpName) {
		return driver.FindElementByXPath("//a[text()='"+assignmentGrpName+"']/i");
	}
	public Element applyChangesBtn() {
		return driver.FindElementByXPath("//button[@id='btn_applychanges']");
	}
	public Element titleHeader() {
		return driver.FindElementByXPath("//h2[@class='page-title header']");
	}
	public ElementCollection reviewerColumnNameHeader() {
		return driver.FindElementsByXPath("//th[@aria-controls='ReviewerTimeOnlineID']");
	}
	public Element reviewerColumnNameValue(String reviewer,int index) {
		return driver.FindElementByXPath("(//td[text()='"+reviewer+"']/parent::tr/td)["+index+"]");
	}
	public Element getDateRangeExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#div1']");
	}
	public Element selectFromDate() {
		return driver.FindElementByXPath("//input[@id='FromDateTime']");
	}
	public Element selectToDate() {
		return driver.FindElementByXPath("//input[@id='ToDateTime']");
	}


	public ReviewerTimeOnlineReport(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method navigate to reviewer review progress report from reports page
	 */
	public void navigateToReviewerTimeOnlineReport() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReports_ReviewerTimeOnlineReport());
		getReports_ReviewerTimeOnlineReport().Click();
		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select reviewer in the popup
	 */
	
	public void selectReviewer(String reviewer) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReviewerExpandIcon());
		getReviewerExpandIcon().Click();
		base.stepInfo("Reviewer popup is expanded");
		base.waitTillElemetToBeClickable(selectReviewerCheckBox(reviewer));
		selectReviewerCheckBox(reviewer).ScrollTo();
		selectReviewerCheckBox(reviewer).waitAndClick(10);
		base.stepInfo("Reviewer: "+reviewer+" is selected");
		base.waitTillElemetToBeClickable(getReviewerExpandIcon());
		getReviewerExpandIcon().waitAndClick(10);
		base.stepInfo("Reviewer popup is collapsed");		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select assignment group in the popup
	 */
	public void selectAssignment(String agnmtGrp) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getAssignmentGroupExpandIcon());
		getAssignmentGroupExpandIcon().Click();
		base.stepInfo("Assignment group popup is expanded");
		base.waitTillElemetToBeClickable(selectAssignmentGroupCheckBox(agnmtGrp));
		selectAssignmentGroupCheckBox(agnmtGrp).waitAndClick(10);
		base.stepInfo("Assignment group of "+agnmtGrp+" is selected");
		base.waitTillElemetToBeClickable(getAssignmentGroupExpandIcon());
		getAssignmentGroupExpandIcon().Click();
		base.stepInfo("Assignment group popup is collapsed");		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to select date range.
	 */
	public void selectDateRange(String fromDate, String toDate) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getDateRangeExpandIcon());
		getDateRangeExpandIcon().waitAndClick(10);
		base.stepInfo("Daterange popup is expanded");
		base.waitTillElemetToBeClickable(selectFromDate());
		selectFromDate().SendKeys(fromDate);
		base.stepInfo(fromDate + " is passed as from date");
		base.waitTillElemetToBeClickable(selectToDate());
		selectToDate().SendKeys(toDate);
		base.stepInfo(toDate + " is passed as from date");
		base.waitTillElemetToBeClickable(getDateRangeExpandIcon());
		getDateRangeExpandIcon().waitAndClick(10);
		base.stepInfo("Daterange popup is collapsed");		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method applied the changes and generate the report
	 */
	public void applyChanges() {
		base.waitTillElemetToBeClickable(applyChangesBtn());
		applyChangesBtn().Click();
		base.stepInfo("Changes applied successfully");
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method generates the assignment review progress report
	 */
	public void generateRTOreport(String agnmtGrp,String fromDate, String toDate, String reviewer) {
		selectReviewer(reviewer);
		selectDateRange(fromDate,toDate);
		selectAssignment(agnmtGrp);
		applyChanges();	
		base.waitForElement(titleHeader());
		String title = titleHeader().getText();
		if(title.contains("Reviewer Time Online")) {
			base.passedStep("Report generated for the reviewer "+reviewer+" and the assignment group "+agnmtGrp+" is successfully");
		}else {
			base.failedStep("Report not generated as expected");
		}
		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method returns the value of the column.
	 */
	public String getColoumnValue(ElementCollection coloumnHeader, String textValue, String agnmtName ) {
		int indexValue =base.getIndex(coloumnHeader, textValue);
		String value = reviewerColumnNameValue(agnmtName,indexValue).getText();
		return value;	
		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @param totalTime
	 * @param format
	 * @param actualValue
	 * @description This method is used to validate the time
	 */
	public void validateTime(String totalTime, String format, String actualValue) {
		String[] time = totalTime.split(":");
		if(format=="hour") {
			softAssertion.assertEquals(time[0], actualValue,"Total time reviewer stayed online not reflected in report as expected");
		}if(format=="min") {
			softAssertion.assertEquals(time[1], actualValue,"Total time reviewer stayed online not reflected in report as expected");
		}else {
			softAssertion.assertEquals(time[2], actualValue,"Total time reviewer stayed online not reflected in report as expected");
		}
		softAssertion.assertAll();
		base.passedStep("Total time reviewer stayed online exactly reflected in report as expected");
		
	}
}
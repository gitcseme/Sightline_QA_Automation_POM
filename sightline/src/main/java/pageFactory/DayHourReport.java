package pageFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
//import com.sun.org.apache.bcel.internal.util.BCELComparator;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class DayHourReport {

	Driver driver;
	BaseClass base;
	SoftAssert softAssertion; 

	public Element getReports_ReviewerCountsReport() {
		return driver.FindElementByXPath("//span[text()='REVIEW']/parent::div/parent::div//a[@href='/Review/ReviewerCountsReport']");
	}
	
	public Element getAssignmentGroupExpandIcon() {
		return driver.FindElementByXPath("//a[@id='assignmentGroupID']");
	}
	public Element getReviewerExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseDivReviewer']");
	}
	public Element selectReviewerCheckBox(String reviewer) {
		return driver.FindElementByXPath("(//span[text()='"+reviewer+"'])[last()]");
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
		return driver.FindElementsByXPath("//th[@aria-controls='DataTables_Table_0']");
	}
	public Element reviewerColumnNameValue(String reviewer,int index) {
		return driver.FindElementByXPath("(//td[text()='"+reviewer+"']/parent::tr/td)["+index+"]");
	}
	public Element getDateRangeExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseDateOnly']");
	}
	public Element selectFromDate() {
		return driver.FindElementByXPath("//input[@id='from']");
	}
	public Element selectToDate() {
		return driver.FindElementByXPath("//input[@id='to']");
	}


	public DayHourReport(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method navigate to reviewer review progress report from reports page
	 */
	public void navigateToDayHourReport() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReports_ReviewerCountsReport());
		getReports_ReviewerCountsReport().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(base.getPageTitle());
		String reportName = base.getPageTitle().getText();
		if(reportName.contains("Report: Daily/Hourly Review Metrics")) {
			base.passedStep("Navigated to reviewer review progress report page successfully");
		}else {
			base.failedStep("Page is not navigated to reviewer review progress report page ");
		}
		
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
		selectReviewerCheckBox(reviewer).waitAndClick(10);
		base.stepInfo("Reviewer: "+reviewer+" is selected");
		base.waitTillElemetToBeClickable(getReviewerExpandIcon());
		getReviewerExpandIcon().Click();
		base.stepInfo("Reviewer popup is collapsed");		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select reviewer in the popup
	 */
	
	public void selectMultipleReviewer(String reviewer1,String reviewer2) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReviewerExpandIcon());
		getReviewerExpandIcon().Click();
		base.stepInfo("Reviewer popup is expanded");
		base.waitTillElemetToBeClickable(selectReviewerCheckBox(reviewer1));
		selectReviewerCheckBox(reviewer1).waitAndClick(10);
		base.waitTillElemetToBeClickable(selectReviewerCheckBox(reviewer2));
		selectReviewerCheckBox(reviewer2).waitAndClick(10);
		base.stepInfo("Reviewer: "+reviewer1+" is selected");
		base.waitTillElemetToBeClickable(getReviewerExpandIcon());
		getReviewerExpandIcon().Click();
		base.stepInfo("Reviewer popup is collapsed");		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select assignment group in the popup
	 */
	public void selectAssignmentGroup(String agnmtGrp) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getAssignmentGroupExpandIcon());
		getAssignmentGroupExpandIcon().Click();
		base.stepInfo("Assignment group popup is expanded");
		base.waitTillElemetToBeClickable(selectAssignmentGroupCheckBox(agnmtGrp));
		selectAssignmentGroupCheckBox(agnmtGrp).ScrollTo();
		selectAssignmentGroupCheckBox(agnmtGrp).waitAndClick(10);
		base.stepInfo("Assignment group of "+agnmtGrp+" is selected");
		base.waitTillElemetToBeClickable(getAssignmentGroupExpandIcon());
		getAssignmentGroupExpandIcon().Click();
		base.stepInfo("Assignment group popup is collapsed");		
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
	public void generateRRPreport(String agnmtGrp,String reviewer,String fromDate, String toDate) {
		selectReviewer(reviewer);
		selectAssignmentGroup(agnmtGrp);
		selectDateRange(fromDate,toDate);
		applyChanges();	
		
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
	 * @description This method used to select date range.
	 */
	public void selectDateRange(String fromDate, String toDate) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getDateRangeExpandIcon());
		getDateRangeExpandIcon().Click();
		base.stepInfo("Daterange popup is expanded");
		base.waitTillElemetToBeClickable(selectFromDate());
		selectFromDate().SendKeys(fromDate);
		base.stepInfo(fromDate + " is passed as from date");
		base.waitTillElemetToBeClickable(selectToDate());
		selectToDate().SendKeys(toDate);
		base.stepInfo(toDate + " is passed as from date");
		base.waitTillElemetToBeClickable(getDateRangeExpandIcon());
		getDateRangeExpandIcon().Click();
		base.stepInfo("Daterange popup is collapsed");		
	}
	/**
     * @author Iyappan.Kasinathan
     * @description This method used to get current utc time
     * @return
     * @throws ParseException
     */
	public String getCurrentDate() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("yyyy/MM/dd");
		Date d1 = ldf.parse(sdf.format(new Date()));
		String d2 = ldf.format(d1);
		System.out.println(d2);
		return d2;
	}
}
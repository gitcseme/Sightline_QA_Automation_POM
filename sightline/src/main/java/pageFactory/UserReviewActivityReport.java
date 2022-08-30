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

import org.apache.commons.lang.time.DateUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.sun.org.apache.bcel.internal.util.BCELComparator;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class UserReviewActivityReport {

	Driver driver;
	BaseClass base;

	public Element getReports_UserReviewActivity() {
		return driver.FindElementByXPath("//span[text()='OTHER']/parent::div/parent::div//a[@href='/Review/UserActivity']");
	}
	public Element getReports_DocumentAudit() {
		return driver.FindElementByXPath("//span[text()='OTHER']/parent::div/parent::div//a[@href='/Review/DocumentAudit']");
	}
	public Element getUserExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseUserList']");
	}
	public Element getDateRangeExpandIcon() {
		return driver.FindElementByXPath("//div[@id='divdate']//a[@href='#div1']");
	}
	public Element applyChangesBtn() {
		return driver.FindElementByXPath("//button[@id='btn_applychanges']");
	}
	public Element selectUserCheckbox(String reviewer) {
		return driver.FindElementByXPath("//span[text()='"+reviewer+"']");
	}
	public Element selectFromDate() {
		return driver.FindElementByXPath("//input[@id='FromDateTime']");
	}
	public Element selectToDate() {
		return driver.FindElementByXPath("//input[@id='ToDateTime']");
	}
	public Element statusCheck(String docId, String status) {
		return driver.FindElementByXPath("//td[text()='"+docId+"']/parent::tr/td[text()='"+status+"']");
	}
	public Element getDateFromReport(String docId, String status) {
		return driver.FindElementByXPath("//td[text()='"+docId+"']/parent::tr/td[text()='"+status+"']/parent::tr/td[@class='sorting_1']");
	}
	public Element yesBtn() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}
	public Element waitForTaskCompletePopup() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}
	public ElementCollection actionsList() {
		return driver.FindElementsByXPath("//div[@id='divActionTypeList']//span");
	}
	public Element getActionTypeExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseActionType']");
	}


	public UserReviewActivityReport(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method navigates from reports landing page to user review activity report page. 
	 */
	public void navigateToUserReviewActivityReport() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReports_UserReviewActivity());
		getReports_UserReviewActivity().Click();
		base.stepInfo("User review activity report is clicked");
		driver.waitForPageToBeReady();
		base.waitForElement(base.getPageTitle());
		String reportName = base.getPageTitle().getText();
		if(reportName.contains("Report: User Activity")) {
			base.passedStep("Navigated to user activity report page successfully");
		}else {
			base.failedStep("Page is not navigated to user activity report page ");
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select user in the popup
	 */
	
	public void selectUser(String reviewer) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getUserExpandIcon());
		getUserExpandIcon().Click();
		base.stepInfo("Reviewer popup is expanded");
		base.waitTillElemetToBeClickable(selectUserCheckbox(reviewer));
		selectUserCheckbox(reviewer).ScrollTo();
		selectUserCheckbox(reviewer).waitAndClick(10);
		base.stepInfo("Reviewer: "+reviewer+" is selected");
		base.waitTillElemetToBeClickable(getUserExpandIcon());
		getUserExpandIcon().Click();
		base.stepInfo("Reviewer popup is collapsed");	
		base.passedStep(reviewer+" is selected");
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
	 * @description This method applied the changes and generate the report
	 */
	public void applyChanges() {
		base.waitTillElemetToBeClickable(applyChangesBtn());
		applyChangesBtn().Click();
		base.stepInfo("Changes applied successfully");
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to generate report
	 * @param user
	 * @param fromdate
	 * @param todate
	 */
	public void generateUserReviewActivityReport(String user, String fromdate,String todate) {
		selectUser(user);
		selectDateRange(fromdate,todate);
		applyChanges();
		if(yesBtn().isElementAvailable(20)) {
			yesBtn().waitAndClick(10);
		}
		
	}
    /**
     * @author Iyappan.Kasinathan
     * @description This method used to get current utc time
     * @return
     * @throws ParseException
     */
	public String getCurrentUtcTime() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d1 = ldf.parse(sdf.format(new Date()));
		String d2 = ldf.format(d1);
		System.out.println(d2);
		return d2;
	} 
	public String getPriorOrPostDate(int days) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d1 = ldf.parse(sdf.format(new Date()));
		Date d2 = DateUtils.addDays(d1, days);
		String d3 = ldf.format(d2);
		System.out.println(d3);
		return d3;
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @param docId
	 * @param status
	 * @description This method is used to validate status of docid 
	 */
	public void validateStatusOfUser(String docId, String status) {
			driver.waitForPageToBeReady();
			base.waitForElement(statusCheck(docId, status));
			if (statusCheck(docId, status).isElementAvailable(5)) {
				base.passedStep("Expected " + status + " status is present for expected " + docId + " docId");
			} else {
				base.failedStep("Expected " + status + " status is not present for expected " + docId + " docId");
			}
		}
	/**
	 * @author Iyappan.Kasinathan
	 * @param actualDate
	 * @param expectedDate
	 * @description This method is used to format and validate two dates 
	 */
	 public void validateDate(String actualDate, String expectedDate) throws ParseException {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		 Date d1 = sdf.parse(actualDate);
		 Date d2 = sdf.parse(expectedDate);
		 System.out.println(d1);
		 System.out.println(d2);
		 SoftAssert sa = new SoftAssert();
		 sa.assertEquals(d1, d2);
		 sa.assertAll();
		 base.passedStep("The time and date of action performed is reflected exactly in the reports page as expected ");
	 }
	 /**
	  * @author Iyappan.Kasinathan
	  * @param docId
	  * @param status
	  * @return
	  */
	 public String getDateFromReportsPage(String docId, String status) {
		 driver.waitForPageToBeReady();
		 base.waitForElement(getDateFromReport(docId, status));
		 String date = getDateFromReport(docId, status).getText();
		 return date;
	 }
	 /**
		 * @Author : Iyappan.Kasinathan
		 * @Description : This method navigates from reports landing page to document audit report page. 
		 */
		public void navigateToDocumentAuditReport() {
			driver.waitForPageToBeReady();
			base.waitTillElemetToBeClickable(getReports_DocumentAudit());
			getReports_DocumentAudit().Click();
			base.stepInfo("User review activity report is clicked");
			driver.waitForPageToBeReady();
			base.waitForElement(base.getPageTitle());
			String reportName = base.getPageTitle().getText();
			if(reportName.contains("Report: Document Audit")) {
				base.passedStep("Navigated to document audit report page successfully");
			}else {
				base.failedStep("Page is not navigated to document audit report page ");
			}
		}
		
}
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

public class UserLoginActivityReport {

	Driver driver;
	BaseClass base;

	public Element getReports_UserLoginActivity() {
		return driver.FindElementByXPath("//span[text()='OTHER']/parent::div/parent::div//a[@href='/Review/UserLoginActivityReport']");
	}
	public Element getUserExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseUserList']");
	}
	public Element getDateRangeExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseDateOnly']");
	}
	public Element applyChangesBtn() {
		return driver.FindElementByXPath("//button[@id='btn_applychanges']");
	}
	public Element onlyShow() {
		return driver.FindElementByXPath("//select[@id='ddlActivity']");
	}
	public Element selectUserCheckbox(String reviewer) {
		return driver.FindElementByXPath("//span[text()='"+reviewer+"']");
	}
	public Element selectFromDate() {
		return driver.FindElementByXPath("//input[@id='from']");
	}
	public Element selectToDate() {
		return driver.FindElementByXPath("//input[@id='to']");
	}
	public Element yesBtn() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}
	public Element waitForTaskCompletePopup() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}
	public ElementCollection columnHeader() {
		return driver.FindElementsByXPath("//tr[@role='row']/th");
	}
	public Element getLoginactivitiesReportValue(String user, int index) {
		return driver.FindElementByXPath("(//td[text()='"+user+"']/parent::tr/td["+index+"])");
	}
	public Element loginTimeIcon() {
		return driver.FindElementByXPath("//th[text()='Login Date/Time (UTC)']");
	}
	public Element allUsers() {
		return driver.FindElementByXPath("//h4[text()=' Users ']/parent::label/i");
	}


	public UserLoginActivityReport(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);


	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method navigates from reports landing page to user login activity report page. 
	 */
	public void navigateToUserLoginActivityReport() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReports_UserLoginActivity());
		getReports_UserLoginActivity().Click();
		base.stepInfo("User review activity report is clicked");
		driver.waitForPageToBeReady();
		base.waitForElement(base.getPageTitle());
		String reportName = base.getPageTitle().getText();
		if(reportName.contains("User Login Activity")) {
			base.passedStep("Navigated to user login activity report page successfully");
		}else {
			base.failedStep("Page is not navigated to user login activity report page ");
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
		driver.scrollingToElementofAPage(selectUserCheckbox(reviewer));
		selectUserCheckbox(reviewer).waitAndClick(10);
		base.stepInfo("Reviewer: "+reviewer+" is selected");
		base.waitTillElemetToBeClickable(getUserExpandIcon());
		getUserExpandIcon().Click();
		base.stepInfo("Reviewer popup is collapsed");	
		base.passedStep(reviewer+" is selected");
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to verify PA user is not in list
	 */
	public void  verifyPAuserNotPresent(String paUser) {
		driver.waitForPageToBeReady();
		if(selectUserCheckbox(paUser).isElementAvailable(5)==false){
			base.passedStep("While login as Review manager, Project admin user not in the list of user login activity report page");
		}else {
			base.failedStep("While login as Review manager, Project admin is in the list of user login activity report page");
		}	
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to select all users
	 */
	public void selectAllUsers() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getUserExpandIcon());
		getUserExpandIcon().waitAndClick(10);
		base.stepInfo("Reviewer popup is expanded");
		base.waitTillElemetToBeClickable(allUsers());
		allUsers().waitAndClick(10);
		base.stepInfo("All users are selected");
		base.waitTillElemetToBeClickable(getUserExpandIcon());
		getUserExpandIcon().Click();
		base.stepInfo("Reviewer popup is collapsed");	
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
	 * @description This method select login activities
	 */
	public void selectLoginActivities(String value) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(onlyShow());
		onlyShow().selectFromDropdown().selectByVisibleText(value);
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to generate report
	 * @param user
	 * @param fromdate
	 * @param todate
	 */
	public void generateUserLoginActivityReport(String user, String fromdate,String todate) {
		selectUser(user);
		selectDateRange(fromdate,todate);
		applyChanges();
		if(yesBtn().isElementAvailable(10)) {
			yesBtn().Click();
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
	/**
	 * @author Iyappan.Kasinathan
	 * @param actualDate
	 * @param expectedDate
	 * @description This method is used to format and validate two dates 
	 */
	 public void validateDate(String actualDate, String expectedDate,String role, String action) throws ParseException {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		 Date d1 = sdf.parse(actualDate);
		 Date d2 = sdf.parse(expectedDate);
		 Date d3 = DateUtils.addMinutes(d2, 1);
		 String actDate = sdf.format(d1);
		 String expDate = sdf.format(d2);
		 String offsetDate = sdf.format(d3);
		 if(actDate.equalsIgnoreCase(expDate)){
			 base.passedStep("The time and date of "+role+" is "+action+" is reflected exactly in the reports page as expected");
		 }else if(actDate.equalsIgnoreCase(offsetDate)) {
			 base.passedStep("The time and date of "+role+" is "+action+" is reflected exactly in the reports page as expected");
		 }else {
			 base.failedStep("The time and date of "+role+" is "+action+" is reflected exactly in the reports page as expected");
		 }
	 }
	 /**
	  * @author Iyappan.Kasinathan
    	 * @description This method returns the value of the column.
	 */
	 public String getColoumnValue(ElementCollection coloumnHeader, String textValue, String user) {
			int indexValue = base.getIndex(coloumnHeader, textValue);
			String value = getLoginactivitiesReportValue(user, indexValue).getText();
			return value;

		}
	 /**
	  * @author Iyappan.Kasinathan
	  * @description This method sorts the login time to descending order
	  */
	 public void sortLoginTimeColumn() {
		 driver.waitForPageToBeReady();
		 base.waitTillElemetToBeClickable(loginTimeIcon());
		 loginTimeIcon().ScrollTo();
		 loginTimeIcon().waitAndClick(30);
		 base.waitTillElemetToBeClickable(loginTimeIcon());
		 loginTimeIcon().ScrollTo();
		 loginTimeIcon().waitAndClick(30);
		 base.waitForElement(loginTimeIcon());
		 String value = loginTimeIcon().GetAttribute("aria-sort");
		 if(value.equalsIgnoreCase("descending")) {
			 base.stepInfo("Datas are sorted descending order successfully");
		 }
		 
	 }
		
}
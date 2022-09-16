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
	public Element getCurrentLoggedInUser(String user) {
		return driver.FindElementByXPath("//td[text()='"+user+"']");
	}
	public Element saveReport() {
		return driver.FindElementByXPath("//i[@id='saveReport']");
	}
	public Element reportNameTextBox() {
		return driver.FindElementByXPath("//input[@id='txtReportname']");
	}
	public Element saveButton() {
		return driver.FindElementByXPath("//button[@id='saveXML']");
	}
	public Element customReports(String rname) {
		return driver.FindElementByXPath("//a[text()='"+rname+"']");
	}
	public Element getOnlyShowActivity(String value) {
		return driver.FindElementByXPath("//select[@id='ddlActivity']/option[@value='1'][text()='"+value+"']");
	}
	public Element getExportReport() {
		return driver.FindElementByXPath("//i[@id='exportExcel']");
	}
	public Element getSettingsIcon() {
		return driver.FindElementByXPath("//button[@class='ColVis_Button ColVis_MasterButton']");
	}
	public Element getColumn(String value) {
		return driver.FindElementByXPath("//span[text()='"+value+"']");
	}
	public Element getColoumnHeaderValue(String value) {
		return driver.FindElementByXPath("//th[text()='"+value+"']");
	}
	public Element getRerunReportText() {
		return driver.FindElementByXPath("//h3[text()='Please click \"Apply Changes\" button to re-run report']");
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
		applyChangesBtn().waitAndClick(10);
		if(yesBtn().isElementAvailable(10)) {
			yesBtn().Click();
		}
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
		 base.waitTime(3);
		 driver.waitForPageToBeReady();
		 base.waitTillElemetToBeClickable(loginTimeIcon());
		 loginTimeIcon().ScrollTo();
		 base.waitTime(3);
		 loginTimeIcon().waitAndClick(30);
		 base.waitTillElemetToBeClickable(loginTimeIcon());
		 loginTimeIcon().ScrollTo();
		 base.waitTime(3);
		 loginTimeIcon().waitAndClick(30);
		 base.waitTime(3);
		 base.waitForElement(loginTimeIcon());
		 String value = loginTimeIcon().GetAttribute("aria-sort");
		 if(value.equalsIgnoreCase("descending")) {
			 base.stepInfo("Datas are sorted descending order successfully");
		 }
		 
	 }
	 /**
	  * @author Iyappan.Kasinathan
	  * @param user
	  * @description This method is used to verify the current logged in user is present
	  */
	 public void verifyCurrentLoggedInUserPresent(String user) {
		 driver.waitForPageToBeReady();
		 base.waitForElement(getCurrentLoggedInUser(user));
		 if(getCurrentLoggedInUser(user).isElementAvailable(5)) {
			 base.passedStep("The user currently logged in are present in user login activity report under current logged in activity");
		 }else {
			 base.failedStep("The user currently logged in are not present in user login activity report under current logged in activity");
		 }		 
	 }
	 /**
	  * @author Iyappan.Kasinathan
	  * @param rname - report name
	  * @description This method is used to save the report
	  */
	 public void saveUserloginActivityReport(String rname) {
		 base.waitTillElemetToBeClickable(saveReport());
		 saveReport().waitAndClick(10);
		 base.waitTillElemetToBeClickable(reportNameTextBox());
		 reportNameTextBox().SendKeys(rname);
		 base.waitTillElemetToBeClickable(saveButton());
		 saveButton().waitAndClick(10);
		 base.VerifySuccessMessage("Report save successfully");
	 }
	 /**
	  * @author Iyappan.Kasinathan
	  * @param report name
	  * @description This method is used to verify the report is saved successfully or not
	  */
	 public void verifyReportSavedSuccessfully(String reportName) {
		 driver.waitForPageToBeReady();
		 if(customReports(reportName).isElementAvailable(5)) {
			 base.passedStep("Saved report shown in custom report coloumn in main reports page successfully");
		 }else {
			 base.failedStep("Saved report not displayed not in custom report section");
		 }
	 }
	 /**
	  * @author Iyappan.Kasinathan
	  * @param selection criteria
	  * @description This method is used to verify the report is saved successfully or notselection criteria
	  */
	 public void verifySelectionCriteria(String value) {
		 driver.waitForPageToBeReady();
		 base.waitForElement(getOnlyShowActivity(value));
		 if(getOnlyShowActivity(value).isElementAvailable(5)) {
			 base.passedStep("The selection criterias are displayed as expected");
		 }else {
			 base.failedStep("The selection criterias are not displayed as expected");
		 }		 
	 }
	 /**
	  * @author Iyappan.Kasinathan
	  * @description This method is used to export the report
	  */
	 public void exportReport() {
		 base.waitTillElemetToBeClickable(getExportReport());
		 getExportReport().waitAndClick(10);
		 base.VerifySuccessMessage("Your report has been pushed into the background, and you will get a notification (in the upper right-hand corner \"bullhorn\" icon when your report is completed and ready for viewing.");
	 }
	
	 /**
	  * @author Iyappan.Kasinathan
	  * @param user
	  * @description This method is used to verify the current logged in user is presentlogged out user is not present
	  */
	 public void verifyLoggedOutUserNotPresent(String user) {
		 driver.waitForPageToBeReady();
		 if(getCurrentLoggedInUser(user).isElementAvailable(5)==false) {
			 base.passedStep("The logged out user not present in currently logged in user report page");
		 }else {
			 base.failedStep("The logged out user is present in currently logged in user report page");
		 }		 
	 }
	 public void deselectColumn(String value) {	     
	     driver.waitForPageToBeReady();
		 base.waitTillElemetToBeClickable(getSettingsIcon());
		 getSettingsIcon().waitAndClick(5);
		 base.waitTillElemetToBeClickable(getColumn(value));
		 getColumn(value).ScrollTo();		 
		 getColumn(value).waitAndClick(10);
		 Boolean status = getColoumnHeaderValue(value).isElementAvailable(10);
		 if(status==false) {
			 base.passedStep("Deselected coloumn is not displayed in report");
			
			}else {
				base.failedStep("Deselected coloumn is displayed in report");
			}
		 
	 }
		
}
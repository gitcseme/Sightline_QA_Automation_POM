package pageFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

public class AssignmentReviewProgressReport {

	Driver driver;
	BaseClass base;
	SoftAssert softAssertion;

	public Element getReports_AssignmentReviewProgressReport() {
		return driver.FindElementByXPath(
				"//span[text()='REVIEW']/parent::div/parent::div//a[@href='/Review/AssignmentProgressReport']");
	}

	public Element getAssignmentGroupExpandIcon() {
		return driver.FindElementByXPath("//a[@id='assignmentGroupID']");
	}

	public Element getAssignmentLevelExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseAssignment1']");
	}

	public Element selectAgnmtLevel(String assignmentLevel) {
		return driver.FindElementByXPath("//span[text()='" + assignmentLevel + "']");
	}

	public Element selectAssignmentGroupCheckBox(String assignmentGrpName) {
		return driver.FindElementByXPath("//a[text()='" + assignmentGrpName + "']/i");
	}

	public Element applyChangesBtn() {
		return driver.FindElementByXPath("//button[@id='btn_applychanges']");
	}

	public Element titleHeader() {
		return driver.FindElementByXPath("//h2[@class='page-title header']");
	}

	public ElementCollection agnmtColumnNameHeader() {
		return driver.FindElementsByXPath("//th[@aria-controls='AssignmentProgressAssignmentDetailsGrid_0']");
	}

	public Element agnmtColumnNameValue(String assignmentGrpName, int index) {
		return driver.FindElementByXPath("(//td[text()='" + assignmentGrpName + "']/parent::tr/td)[" + index + "]");
	}

	public Element getYesPopUp() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
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

	public AssignmentReviewProgressReport(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method navigate to assignment review progress report from
	 *              reports page
	 */
	public void navigateToAssgnmntReviewProgressReport() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReports_AssignmentReviewProgressReport());
		getReports_AssignmentReviewProgressReport().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(base.getPageTitle());
		String reportName = base.getPageTitle().getText();
		if (reportName.contains("Assignment Review Progress")) {
			base.passedStep("Navigated to assignment review progress report page successfully");
		} else {
			base.failedStep("Page is not navigated to assignment review progress report page ");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method return sum of two time durations
	 */
	public String addTwoTimeDuration(String time1, String time2) throws ParseException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date t1 = timeFormat.parse(time1);
		Date t2 = timeFormat.parse(time2);
		long sum = t1.getTime() + t2.getTime();
		String totalTime1 = null;
		totalTime1 = timeFormat.format(new Date(sum));
		System.out.println("The sum is " + totalTime1);
		return totalTime1;
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select assignment level
	 */
	public void selectAssignmentLevel(String agnmtLevel) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getAssignmentLevelExpandIcon());
		getAssignmentLevelExpandIcon().Click();
		base.stepInfo("Assignment level popup is expanded");
		base.waitTillElemetToBeClickable(selectAgnmtLevel(agnmtLevel));
		selectAgnmtLevel(agnmtLevel).waitAndClick(10);
		base.stepInfo("Assignment level of " + agnmtLevel + " is selected");
		base.waitTillElemetToBeClickable(getAssignmentLevelExpandIcon());
		getAssignmentLevelExpandIcon().Click();
		base.stepInfo("Assignment level popup is collapsed");
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
		base.stepInfo("Assignment group of " + agnmtGrp + " is selected");
		base.waitTillElemetToBeClickable(getAssignmentGroupExpandIcon());
		getAssignmentGroupExpandIcon().waitAndClick(10);
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
	public void generateARPreport(String agnmtGrp,String agnmtLev) {
		selectAssignmentLevel(agnmtLev);
		selectAssignmentGroup(agnmtGrp);
		applyChanges();	
		if(getYesPopUp().isElementAvailable(30)) {
			getYesPopUp().waitAndClick(5);
		}
		String title = titleHeader().getText();
		if(title.contains(agnmtGrp)) {
			base.passedStep("Report generated for the "+agnmtGrp+" is successfully");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method returns the value of the column.
	 */
	public String getColoumnValue(ElementCollection coloumnHeader, String textValue, String agnmtName) {
		int indexValue = base.getIndex(coloumnHeader, textValue);
		String value = agnmtColumnNameValue(agnmtName, indexValue).getText();
		return value;

	}
	
	public void SaveReport(String reportName) {
		if (getSaveReportBtn().isElementAvailable(10)) {
			getSaveReportBtn().ScrollTo();
			getSaveReportBtn().Click();
		}
		getSaveReportName().isElementAvailable(3);
		getSaveReportName().Click();
		getSaveReportName().Clear();
		getSaveReportName().SendKeys(reportName);
		getSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Report save successfully");
		base.CloseSuccessMsgpopup();
		base.waitForElementToBeGone(base.getCloseSucessmsg(), 30);
	}

}
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

public class TimelineReportPage {

	Driver driver;
	BaseClass base;

	public Element getReports_TimelineGaps() {
		return driver.FindElementByXPath("//span[text()=' DATA EXPLORATION']/parent::div/parent::div//a[@href='/Report/Timeline']");
	}
	public Element getSelectSource() {
		return driver.FindElementByXPath("//a[@id='select-source']");
	}
	public Element getSelectSourceSecurityGroup() {
		return driver.FindElementByXPath("//strong[text()='Security Groups']/parent::a");
	}
	public Element selectSecurityGrp() {
		return driver.FindElementByXPath("//label[text()='Default Security Group']/preceding-sibling::input");
	}
	public Element saveSelectedSecurityGrp() {
		return driver.FindElementByXPath("//button[@id='secgroup']");
	}
	public Element selectTimeLineDropdown() {
		return driver.FindElementByXPath("//select[@id='ddlTimelineBy']");
	}
	public Element selectFromDate() {
		return driver.FindElementByXPath("//input[@id='from']");
	}
	public Element selectToDate() {
		return driver.FindElementByXPath("//input[@id='to']");
	}
	public Element applyChangesBtn() {
		return driver.FindElementByXPath("//button[@id='btnApplyChanges']");
	}
	public Element actionBtn(String value) {
		return driver.FindElementByXPath("//div[@id='"+value+"']//button/span");
	}
	public Element selectAction(String action,String value) {
		return driver.FindElementByXPath("//div[@id='"+value+"']//ul/li/a[text()='"+action+"']");
	}
	public Element selectChart() {
		return driver.FindElementByCssSelector("div[id='level1timeline'] rect");
	}
	public Element totalDocsInTag() {
		return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']");
	}


	public TimelineReportPage(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method navigates from reports landing page to user review activity report page. 
	 */
	public void navigateToTimelineAndGapsReport() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReports_TimelineGaps());
		getReports_TimelineGaps().Click();
		base.stepInfo("Timeline and gaps report is clicked");
		driver.waitForPageToBeReady();
		base.waitForElement(base.getPageTitle());
		String reportName = base.getPageTitle().getText();
		System.out.println(reportName);
		if(reportName.contains("Report: Timeline and Gaps")) {
			base.passedStep("Navigated to Timeline and gaps report page successfully");
		}else {
			base.failedStep("Page is not navigated to Timeline and gaps report page ");
		}
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method selects the source
	 */
	public void selectSource() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getSelectSource());
		getSelectSource().waitAndClick(5);
		base.waitTillElemetToBeClickable(getSelectSourceSecurityGroup());
		getSelectSourceSecurityGroup().waitAndClick(5);
		base.waitTillElemetToBeClickable(selectSecurityGrp());
		selectSecurityGrp().waitAndClick(5);
		base.waitTillElemetToBeClickable(saveSelectedSecurityGrp());
		saveSelectedSecurityGrp().waitAndClick(5);
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method selects timeline in report
	 */
	public void selectTimeline(String value) {
		base.waitTillElemetToBeClickable(selectTimeLineDropdown());
		selectTimeLineDropdown().selectFromDropdown().selectByVisibleText(value);
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to select date range.
	 */
	public void selectDateRange(String fromDate, String toDate) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(selectFromDate());
		selectFromDate().SendKeys(fromDate);
		base.stepInfo(fromDate + " is passed as from date");
		base.waitTillElemetToBeClickable(selectToDate());
		selectToDate().SendKeys(toDate);
		base.stepInfo(toDate + " is passed as from date");	
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method applied the changes and generate the report
	 */
	public void applyChanges() {
		base.waitTillElemetToBeClickable(applyChangesBtn());
		applyChangesBtn().waitAndClick(10);
		base.stepInfo("Changes applied successfully");
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
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method selects actions in the list
	 */
	public void selectActions(String value,String timeline) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(actionBtn(timeline));
		actionBtn(timeline).waitAndClick(5);
		base.waitTillElemetToBeClickable(selectAction(value,timeline));
		selectAction(value,timeline).waitAndClick(5);
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method selects the bar chart in the report
	 */
	public void selectBarChart() {
		base.waitTillElemetToBeClickable(selectChart());
		selectChart().ScrollTo();
		selectChart().waitAndClick(10);
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method used to view in docview or doclist
	 */
	public void viewInDocviewOrDoclist(String value,String timeline) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(actionBtn(timeline));
		actionBtn(timeline).waitAndClick(5);
		base.waitTillElemetToBeClickable(selectAction("View",timeline));
		selectAction("View",timeline).ScrollTo();
		base.waitTillElemetToBeClickable(selectAction(value,timeline));
		selectAction(value,timeline).waitAndClick(5);
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method verifies the column names in excel sheet
	 */
	public void verifyTheReportColoumnValuesInExcel(int rowNo,String passMsg,String failMsg,String headerList[]) {		
        List<String> value = new ArrayList<String>();
		String fileName = base.GetLastModifiedFileName();
	    String[][] record = base.readExcelData(fileName, rowNo);
	    System.out.println("Rown :" + record[rowNo].length);
		for (int i = 0; i < record[rowNo].length; i++) {
			if (!record[0][i].equals("")) {
				System.out.println(record[rowNo][i]);
				value.add(record[rowNo][i]);
			}
		}
	    base.compareArraywithDataList(headerList, value, false, passMsg, failMsg);	
	}
	
		
}
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
		return driver.FindElementByXPath(
				"//span[text()=' DATA EXPLORATION']/parent::div/parent::div//a[@href='/Report/Timeline']");
	}

	public Element getSelectSource() {
		return driver.FindElementByXPath("//a[@id='select-source']");
	}

	public Element getSelectSourceSecurityGroup() {
		return driver.FindElementByXPath("//strong[text()='Security Groups']/parent::a");
	}

	public Element selectSecurityGrp() {
		return driver.FindElementByXPath("//label[contains(text(),'Default  Security Group')]");
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
		return driver.FindElementByXPath("//div[@id='" + value + "']//button/span");
	}

	public Element selectAction(String action, String value) {
		return driver.FindElementByXPath("//div[@id='" + value + "']//ul/li/a[text()='" + action + "']");
	}

	public Element selectChart() {
		return driver.FindElementByCssSelector("div[id='level1timeline'] rect");
	}

	public Element totalDocsInTag() {
		return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']");
	}

	public Element dynamicDocCountPOPUP() {
		return driver.FindElementByXPath("//div[@class='tipsy-inner']");
	}

	public Element selectChart(int i) {
		return driver.FindElementByCssSelector("div[id='level" + i + "timeline'] rect[height]");
	}

	public Element getSelectSourceTags() {
		return driver.FindElementByXPath("//strong[text()='Tags']/parent::a");
	}

	public Element saveSelectedTag() {
		return driver.FindElementByXPath("//button[@id='tag']");
	}

	public Element selectTags(String tag) {
		return driver.FindElementByXPath("//div[@id='divTagGroupTree']//li//a[text()='" + tag + "']");
	}

	public Element selectedOptions(String options) {
		return driver.FindElementByXPath("//ul[@id='bitlist-sources']//li[contains(text() , '" + options + "')]");
	}

	public Element selectedOptionsClose(String options) {
		return driver.FindElementByXPath("//ul[@id='bitlist-sources']//li[contains(text() , '" + options + "')]//i");
	}

	public Element getSaveReportBtn() {
		return driver.FindElementById("saveReport");
	}

	public Element getSaveBtn() {
		return driver.FindElementById("saveXML");
	}
	public Element getSelectSourceSelection(String value) {
		return driver.FindElementByXPath("//strong[text()='"+value+"']/parent::a");
	}
	public Element selectSourceSelectionValues(String value) {
		return driver.FindElementByXPath("//a[text()='"+value+"']");
	}
	public Element saveSelectedSearches() {
		return driver.FindElementByXPath("//button[@id='search']");
	}
	public Element saveSelectedProjects() {
		return driver.FindElementByXPath("//button[@id='project']");
	}
	public Element selectMonthChart() {
		return driver.FindElementByCssSelector("div[id='level2timeline'] rect[y='0']");
	}
	public Element selectDayChart() {
		return driver.FindElementByCssSelector("div[id='level3timeline'] rect[y='0']");
	}
	public Element getCustodiandropdown() {
		return driver.FindElementByXPath("//span[text()='Custodians']/parent::strong/following-sibling::span");
	}
	public Element getSelectCustodianNames() {
		return driver.FindElementByXPath("//span[text()='Custodians']/parent::strong/parent::div//ul[@class='select2-choices']//input");
	}
	public Element getProjectName(String projectName) {
		return driver.FindElementByXPath("//label[text()='"+projectName+"']");
	}

	public Element getSaveReportName() {
		return driver.FindElementById("txtReportname");
	}

	public void RemoveAndVerifyOptionFrmSrcCriteria(String[] optionsToRemove) {
		for (String option : optionsToRemove) {
			base.waitForElement(selectedOptionsClose(option));
			selectedOptionsClose(option).waitAndClick(5);
			if (!selectedOptions(option).isElementAvailable(20)) {
				base.passedStep(
						"After Click Close Icon " + option + " Option Successfully Removed From Source Criteria ");
			} else {
				base.failedStep("After Click Close Icon " + option + " Option Not Removed From Source Criteria ");
			}
		}
	}

	public TimelineReportPage(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method navigates from reports landing page to user review
	 *              activity report page.
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
		if (reportName.contains("Report: Timeline and Gaps")) {
			base.passedStep("Navigated to Timeline and gaps report page successfully");
		} else {
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
	public void selectActions(String value, String timeline) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(actionBtn(timeline));
		actionBtn(timeline).waitAndClick(5);
		base.waitTillElemetToBeClickable(selectAction(value, timeline));
		selectAction(value, timeline).waitAndClick(5);
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
	public void viewInDocviewOrDoclist(String value, String timeline) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(actionBtn(timeline));
		actionBtn(timeline).waitAndClick(5);
		base.waitTillElemetToBeClickable(selectAction("View", timeline));
		selectAction("View", timeline).ScrollTo();
		base.waitTillElemetToBeClickable(selectAction(value, timeline));
		selectAction(value, timeline).waitAndClick(5);
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method verifies the column names in excel sheet
	 */
	public void verifyTheReportColoumnValuesInExcel(int rowNo, String passMsg, String failMsg, String headerList[]) {
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

	/**
	 * @Author : sowndarya
	 * @Description : This method is to filling details in timegaps
	 */
	public void fillingDetailsinTimeGaps(String timeLine, String fromDate, String toDate) {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		navigateToTimelineAndGapsReport();
		selectSource();
		selectTimeline(timeLine);
		selectDateRange(fromDate, toDate);
		applyChanges();
	}

	/**
	 * @Author : sowndarya
	 * @Description : This method is to select bar chart
	 */
	public String selectBarChartandRtnDocCount(String timeline) throws InterruptedException {

		if (timeline.equalsIgnoreCase("Monthly")) {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			base.waitForElement(selectChart(2));
			base.waitTillElemetToBeClickable(selectChart(2));
			selectChart(2).ScrollTo();
			selectChart(2).waitAndClick(10);
		} else if (timeline.equalsIgnoreCase("Daily")) {
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			base.waitForElement(selectChart(3));
			base.waitTillElemetToBeClickable(selectChart(3));
			selectChart(3).ScrollTo();
			selectChart(3).waitAndClick(10);
		} else {
			base.waitForElement(selectChart());
			base.waitTillElemetToBeClickable(selectChart());
			selectChart().ScrollTo();
			selectChart().waitAndClick(10);
		}

		base.waitForElement(dynamicDocCountPOPUP());
		String popText = dynamicDocCountPOPUP().getText();
		String docCount = popText.substring(0, 2).trim();
		System.out.println(popText);
		base.stepInfo("Doc Count in Selected Bar is : " + docCount);
		return docCount;
	}

	/**
	 * @Author : sowndarya
	 * @Description : This method is to select tag in source
	 */
	public void selectTagsinSource(String[] tags) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getSelectSource());
		getSelectSource().waitAndClick(5);
		base.waitTillElemetToBeClickable(getSelectSourceTags());
		getSelectSourceTags().waitAndClick(5);
		for (int i = 0; i < tags.length; i++) {
			base.waitTillElemetToBeClickable(selectTags(tags[i]));
			selectTags(tags[i]).ScrollTo();
			selectTags(tags[i]).waitAndClick(5);
		}
		base.waitTillElemetToBeClickable(saveSelectedTag());
		saveSelectedTag().waitAndClick(5);
	}

	/**
	 * @Author : sowndarya
	 * @Description : This method is to verify selected options in source criteria
	 */
	public void verifySelctedOptnsInSourceCriteria(String[] options) {

		driver.waitForPageToBeReady();
		for (int i = 0; i < options.length; i++) {
			base.waitForElement(selectedOptions(options[i]));
			if (selectedOptions(options[i]).isElementAvailable(5)) {
				base.passedStep(options[i] + "Displaying Successfully in Source Selection Criteria");
			} else {
				base.failedStep(options[i] + "Not displaying in Source Selection Criteria");
			}
		}
	}

	/**
	 * @Author : sowndarya
	 * @Description : To save the report
	 */
	public void SaveReport(String reportName) {

		if (getSaveReportBtn().isElementAvailable(2)) {
			getSaveReportBtn().ScrollTo();
			getSaveReportBtn().waitAndClick(2);
		}
		getSaveReportName().isElementAvailable(3);
		getSaveReportName().Click();
		getSaveReportName().Clear();
		getSaveReportName().SendKeys(reportName);
		getSaveBtn().Click();
		base.VerifySuccessMessage("Report save successfully");
		base.CloseSuccessMsgpopup();
		base.waitForElementToBeGone(base.getCloseSucessmsg(), 30);
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method selects the source
	 */
	public void selectSource(String value,String actions) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getSelectSource());
		getSelectSource().waitAndClick(5);
		base.waitTillElemetToBeClickable(getSelectSourceSelection(value));
		getSelectSourceSelection(value).waitAndClick(5);
		base.waitTillElemetToBeClickable(selectSourceSelectionValues(actions));
		selectSourceSelectionValues(actions).waitAndClick(5);
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method selects the bar chart in the report
	 */
	public void selectDayBarChart() {
		base.waitTillElemetToBeClickable(selectDayChart());
		selectDayChart().ScrollTo();
		selectDayChart().waitAndClick(10);
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method selects the bar chart in the report
	 */
	public void selectMonthBarChart() {
		base.waitTillElemetToBeClickable(selectMonthChart());
		selectMonthChart().ScrollTo();
		selectMonthChart().waitAndClick(10);
	}
	public void selectCustodianName(String value) {
		base.waitTillElemetToBeClickable(getCustodiandropdown());
		getCustodiandropdown().waitAndClick(10);
		base.waitTillElemetToBeClickable(getSelectCustodianNames());
		getSelectCustodianNames().SendKeys(value);
		base.hitEnterKey(1);
		
	}
}
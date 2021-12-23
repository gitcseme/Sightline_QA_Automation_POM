package pageFactory;

import java.awt.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class ReviewerResultReportPage {
	Driver driver;
	BaseClass bc;
	SoftAssert	softAssertion ;
	public Element getReviewerResultReportButton(){ return driver.FindElementByXPath("//tr//a[text()='Review Results Report']"); }
	public Element getExpandButton(String elementValue ){ return driver.FindElementByXPath("//span[contains(.,'"+elementValue+"')]/parent::strong/following-sibling::a[@data-toggle='collapse']");}
	public Element getSelectSecGrpPlusBtn() { return driver.FindElementByXPath("//div[@id='securityGroupsPopup']/div/div/div/a/strong");  }
	public Element getSelectSG() { return driver.FindElementByXPath("//ul[@id='secgroup']//li/label/span[text()='Default Security Group']");  }
	public Element getSelectSource() { return driver.FindElementById("select-new-source");  }
	public Element getResponsiveTagCheckBox() { return driver.FindElementByXPath("//span[text()='Responsive']/parent::label/i");}
	public Element getApplyBtn() { return driver.FindElementByXPath("//button[@id='btn_applychanges']");}
	public Element getTally_SaveSelections() {
		return driver.FindElementByXPath("//button[@id='secgroup']");
	}
	public Element getReportTable() {
		return driver.FindElementByXPath("//table[@id='dtReviewResultsData']");
	}
	public Element getshareBtn() {
		return driver.FindElementById("ReportReviewer");
	}
	public Element getUserToShare() {
		return driver.FindElementByXPath("//ul[@class='multiselect-container dropdown-menu']//li/a/label/input");
	}
	public Element getUserToShareDropDown() {
		return driver.FindElementByXPath("//button[@class='multiselect dropdown-toggle btn']");
	}
	public Element sharePopUp_ShareBtn() {
		return driver.FindElementById("btnSaveShareReport");
	}
	public Element getCheckBox(String eleValue) {
		return driver.FindElementByXPath("//span[text()='"+eleValue+"']/parent::label/i");
	}
	public Element getScheduleBtn() {
		return driver.FindElementByXPath("//a[@id='btnAScheduler']");
	}
	public Element getSavedSearch_ScheduleTime() {
		return driver.FindElementById("txtOneTimeStartDate");
	}

	public Element getSaveSchedulerBtn() {
		return driver.FindElementByXPath("//button[@id='btnScheduleSubmit']");
	}
	public Element getSavedSearch_SchedulePopUp() {
		return driver.FindElementById("divscheduler");
	}
	
	public ReviewerResultReportPage(Driver driver) {

		this.driver = driver;
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
	}
	/**
	 * @author Jayanthi.ganesan
	 */
	public void navigateToReviewerResultReportPage() {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String expectedURL=Input.url+"Review/ReviewResults";
		bc.waitForElement(getReviewerResultReportButton());
		if(getReviewerResultReportButton().isDisplayed()){
			bc.passedStep("Reviewer Result Report link is displayed in reports landing page");
			getReviewerResultReportButton().Click();
		SoftAssert	softAssertion = new SoftAssert();
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(expectedURL,driver.getUrl());
		softAssertion.assertAll();
		bc.passedStep("Sucessfully navigated to Reviewer Result Report Page");
		}
		else {
			bc.failedStep("Reviewer Result Report link is not  displayed in reports landing page");
		}
	}
	
	
	/**
	 * @author Jayanthi.ganesan
	 */
	public void generateReport() {
		bc.waitForElement(getSelectSource());
		getSelectSource().Click();
		bc.waitForElement(getSelectSecGrpPlusBtn());
		getSelectSecGrpPlusBtn().Click();
		bc.waitForElement(getSelectSG());
		getSelectSG().Click();
		getTally_SaveSelections().Click();
		getExpandButton("Document Types").Click();
		getCheckBox("Adobe Acrobat").Click();
		getExpandButton("Document Types").Click();	
		driver.scrollingToBottomofAPage();
		getExpandButton("Tags").Click();
		getResponsiveTagCheckBox().Click();
		getExpandButton("Tags").Click();
		driver.scrollPageToTop();
		getApplyBtn().waitAndClick(5);
		getReportTable().isElementAvailable(10);
		if (getReportTable().isDisplayed()) {
			bc.passedStep("Review result report Generated");
		} else {
			bc.failedStep("Review result report not Generated");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void shareReport() {
		if (getReportTable().isDisplayed()) {
			getshareBtn().Click();
			getUserToShareDropDown().waitAndClick(5);
			getUserToShare().waitAndClick(5);
			sharePopUp_ShareBtn().waitAndClick(5);
		} else {
			bc.failedStep("Report not generated so we cant share the report to any user.");
		}
	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public void scheduleReport() throws ParseException, InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ScheduleTime().Visible();
			}
		}), Input.wait60);
		System.out.println(scheduleTimePlusMinutes(1));
		getSavedSearch_ScheduleTime().SendKeys(scheduleTimePlusMinutes(1));
		getSaveSchedulerBtn().isElementAvailable(5);
		getSaveSchedulerBtn().waitAndClick(10);
		System.out.println("Review report result is scheduled to run in 1 minute!");
		bc.stepInfo(" Review report result is scheduled to run in 1  minute!");
	}
	/**
	 * @author Jayanthi.ganesan
	 * @return
	 * @throws ParseException
	 */
	 
		public static String scheduleTimePlusMinutes(int number) throws ParseException {
			// Time in GMT
			SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

			// Local time zone
			SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			dateFormatLocal.parse(dateFormatGmt.format(new Date()));

			String Time = dateFormatGmt.format(new Date()).toString();
			// System.out.println(Time);

			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date d = df.parse(Time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.MINUTE, number);
			String newTime = df.format(cal.getTime());
			// System.out.println(newTime);
			return newTime.toString();

		}

	
	
	}



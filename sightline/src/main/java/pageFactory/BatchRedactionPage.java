package pageFactory;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sqlite.SQLiteConfig.DateClass;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import ch.qos.logback.classic.pattern.DateConverter;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class BatchRedactionPage {

	Driver driver;
	BaseClass base;
	LoginPage login;
	ReportsPage report;
	String searchname;
	SoftAssert softassert;
	DocViewPage docview;
	AssignmentsPage assign;
	MiniDocListPage minidocList;
	ReusableDocViewPage reUseDocPage;

	/**
	 * @Author Jeevitha
	 * @return
	 */
	public Element manageBtn() {
		return driver.FindElementByXPath("//label[text()='Manage']//parent::a");
	}

	public Element batchRedactionBtn() {
		return driver.FindElementByXPath("//a[@name='BatchRedaction']");
	}

	public Element getMySavedSearchDropDown() {
		return driver.FindElementByXPath("(//*[@id='searchTree']//i)[1]");
	}

	public Element getMySavedSearchTextbox(String searchname) {
		return driver.FindElementByXPath(
				"//a[contains(@class,'jstree')]//following-sibling::ul//li[contains(@class,'-leaf')]//div[text()=\""
						+ searchname + "\"]");
	}

	public Element getAnalyzeSearchForSavedSearchResult(String searchname) {
		return driver.FindElementByXPath(
				"//a[contains(@class,'jstree')]//following-sibling::ul//li[contains(@class,'-leaf')]//div[text()=\""
						+ searchname
						+ "\"]//following-sibling::div/div/following-sibling::button[contains(@class,' analyze')]");
	}

	public Element getViewReportForSavedSearch(String searchname) {
		return driver.FindElementByXPath(
				"//a[contains(@class,'jstree')]//following-sibling::ul//li[contains(@class,'-leaf')]//div[text()=\""
						+ searchname + "\"]//following-sibling::div/button[contains(text(),'View Report')]");
	}

	public Element getPreRedactReportMessage1() {
		return driver.FindElementByXPath("(//*[@id='tblResults']/tbody/tr/td)[2]");

	}

	public Element getPreRedactReportMessage2() {
		return driver.FindElementByXPath("(//*[@id='tblResults']/tbody/tr/td)[3]");

	}

	public Element getPreRedactReportMessage3() {
		return driver.FindElementByXPath("(//*[@id='tblResults']/tbody/tr/td)[4]");

	}

	public Element getPopUpRedactButton() {
		return driver.FindElementByXPath("//*[text()='Redact']");
	}

	public Element getPopupYesBtn() {
		return driver.FindElementByXPath("(//*[@class='MessageBoxButtonSection']/button)[1]");
	}

	public Element getPopupNoBtn() {
		return driver.FindElementByXPath("(//*[@class='MessageBoxButtonSection']/button)[2]");
	}

	public Element getPopupMessage() {
		return driver.FindElementByXPath("//div[@id='Msg1']//div[@class='MessageBoxMiddle']/h2/span/span");
	}

//	Please make sure that redactions are not being applied manually to the same documents while running this Batch Redaction as it can possibly create unexpected or lost redactions. Do you want to continue with the Batch Redaction?
	public Element verifyAnalyzePopupmsg() {
		return driver.FindElementByXPath("//*[@class='botClose fa fa-times']");
	}

	public Element getRollbackbtn(String searchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + searchName
				+ "']//following::td[5]//a");
	}

	public Element getRollbackMsg(String searchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + searchName
				+ "']//following::td[3]//div[contains(text(),'Successfully')]");
	}

	public Element getClickHereReportbtn(String searchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + searchName
				+ "']//following::td[4]");
	}

	public Element getProgressBar() {
		return driver.FindElementByXPath("//div[@class='br-progress']//following::div[text()='100%']");
	}

	public Element getSelectRedactionHelpIcon() {
		return driver.FindElementByXPath("//div[contains(@class,'branalyzetaglede')]//a");
	}

	public Element getVeiwAnalysisReportPopup() {
		return driver.FindElementById("tblResults");
	}

	public Element getRedactHelpText() {
		return driver.FindElementByXPath("//p//parent::div[@class='popover-content']");
	}

	public Element getBAtchRedactionHelpIcon() {
		return driver.FindElementByXPath("(//div[@class='h1 br-page-title']//a)[1]");
	}

	public Element getBAtchRedactionHistoryHelpIcon() {
		return driver.FindElementByXPath("(//div[@class='h1 br-page-title']//a)[2]");
	}

	public Element getSelectSearchHelpIcon() {
		return driver.FindElementByXPath("//label[@class='labelAlign control-label']//a");
	}

	public Element getHelpText() {
		return driver.FindElementByXPath("//div[@class='popover fade right in']//h3");
	}

	public Element getMySavedSearchAnalyzeBtn() {
		return driver.FindElementByXPath("(//button[text()='Analyze Group for Redactions'])[1]");
	}

	public Element getMySavedSearchViewReportBtn() {
		return driver.FindElementByXPath("(//button[text()='View Report & Apply Redactions'])[1]");
	}

	public Element viewReportAndBatchReport() {
		return driver.FindElementById("tblResults");
	}

	// added by jeevitha
	public Element getHistoryEnteries(int index) {
		return driver.FindElementByXPath("(//table[@id='BatchRedactionsDataTable']//tr//td)[" + index + "]");
	}

	public ElementCollection HeaderTabsPresentInBR() {
		return driver.FindElementsByXPath("//div[@class='h1 br-page-title']");
	}

	public Element getSelectSearchHeader() {
		return driver.FindElementByXPath("//label[@class='labelAlign control-label']");
	}

	public Element getDDStatus(String type) {
		return driver.FindElementByXPath(
				"//div[text()='" + type + "']//..//..//i[@class='jstree-icon jstree-ocl']//parent::li");
	}

	public Element getAnalyzeGroupForTab(String tabName) {
		return driver.FindElementByXPath(
				"//div[text()='" + tabName + "']//parent::a//button[text()='Analyze Group for Redactions']");
	}

	public ElementCollection batchRedactionHistoryHeader() {
		return driver.FindElementsByXPath("//table[@id='BatchRedactionsDataTable']//th");
	}

	// Added by Raghuram
	public Element getInProgressStatus(String ssName) {
		return driver.FindElementByXPath(
				"(//tr[@role='row']//td[text()='" + ssName + "']//..//td[contains(text(),'In Progress.')])[1]");
	}

	public Element getCompletedWithErrorsStatus(String ssName) {
		return driver.FindElementByXPath(
				"(//tr[@role='row']//td[text()='" + ssName + "']//..//p[@class='CompWithError'])[1]");
	}

	public Element getConfirmationBtn(String option) {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[text()=' " + option + "']");
	}

	// Added By Jeevitha
	public Element getFileDownload() {
		return driver.FindElementByXPath(
				"(//a[contains(text(),'Your Batch Redaction analysis is complete. Please click here to download the report')])[1]");
	}

	public Element getBullHornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getBullHornIcon_CC() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']//following::b[1]");
	}

	// Added
	public Element getRightBtm() {
		return driver.FindElementByXPath("//div[@class='pccMarkHandleBottomRight']");
	}

	public Element getexpn() {
		return driver.FindElementByXPath("//div[contains(@data-pcc-mark,\"outline-\")]");
	}

	public Element getRductionBtb() {
		return driver.FindElementByXPath("(//i[@class='fa fa-stop'])[1]");
	}

	public Element getNextbtn() {
		return driver.FindElementByXPath("//i[@id='NextAllBatchRedaction']");
	}

	public Element getDocViewSaveBtn() {
		return driver.FindElementByXPath("//div[@class='pcc-pull-right']//span");
	}

	public Element getRedactionTagDropDown() {
		return driver.FindElementById("ddlRedactionmTaglist");
	}

	public Element getRedactionTagDropDownValue(String value) {
		return driver.FindElementByXPath("//select[@id='ddlRedactionmTaglist']//option[text()='" + value + "']");
	}

	// Added by Jeevitha
	public Element getColorStatusMsg(String SearchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + SearchName
				+ "']//following::td[3]//p");
	}

	public Element getStatusMsg(String SearchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + SearchName
				+ "']//following::td[3]//div");
	}

	// Added By Jeevitha
	public Element getBatchRedactionStatus(String searchname) {
		return driver.FindElementByXPath(
				"//a[contains(@class,'jstree')]//following-sibling::ul//li[contains(@class,'-leaf')]//div[text()='"
						+ searchname + "']//following-sibling::div[contains(text(),'Batch')]");
	}

	public Element getRollBackMsg() {
		return driver.FindElementByClassName("MessageBoxMiddle");
	}

	public Element getRollbackInProgressStatus(String searchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + searchName
				+ "']//following::td[3]//div");
	}

	public Element getRollBackNotification() {
		return driver.FindElementByXPath("(//ul[@class='notification-body']//a)[1]");
	}

	public Element getRedactProgressing(String searchname) {
		return driver.FindElementByXPath(
				"//a[contains(@class,'jstree')]//following-sibling::ul//li[contains(@class,'-leaf')]//div[text()='"
						+ searchname + "']//following-sibling::div");
	}

	public Element getFailedStatus(String ssName) {
		return driver.FindElementByXPath(
				"(//tr[@role='row'][1]//td[text()='" + ssName + "']//..//p[contains(text(),'Failed')])[1]");
	}

	public Element getDocCountFromTable(String ssName) {
		return driver.FindElementByXPath("(//tr[@role='row'][1]//td[text()='" + ssName
				+ "']//..//td[count(//th[text()='# DOCS']//preceding-sibling::th)+1])");
	}

	public Element getBatchId(String ssName) {
		return driver.FindElementByXPath("//tbody//tr[1]//td[text()='" + ssName + "']//parent::tr//td[1]");
	}

	public Element getRedactionTag(String ssName) {
		return driver.FindElementByXPath("//tbody//tr[1]//td[text()='" + ssName + "']//parent::tr//td[3]");
	}

	public Element getSuccessStatus(String ssName) {
		return driver.FindElementByXPath(
				"//tr[@role='row']//td[text()='" + ssName + "']//..//div[contains(text(),'Successfully')]");
	}

	public Element getSearchHitCount(String ssName) {
		return driver.FindElementByXPath(
				"//div[contains(text(),'" + ssName + "')]//following::div[contains(text(),'Search Hit Count')]");
	}

	public Element getSearchToolTip(String ssName) {
		return driver.FindElementByXPath(
				"//div[contains(text(),'" + ssName + "')]//following::div[contains(text(),'Search Hit Count')]//div");
	}

	// added by jayanthi
	// modifed by jeevitha
	public Element getAnalysisReportPopup() {
		return driver.FindElementByXPath("//div//span[contains(text(),'View Analysis Report and Batch Redact')]");
	}

	// Added By Jeevitha
	public Element getRollbackQueText(String ssName) {
		return driver.FindElementByXPath("//td[text()='" + ssName
				+ "']//parent::tr//td//div[contains(text(),'Rollback Batch Redaction queued')]");
	}

	public Element getNodeAnalyseBtn(String newNode) {
		return driver.FindElementByXPath(
				"//div[text()='" + newNode + "']//parent::a//child::div//button[contains(text(),'Analyze Group')]");
	}

	public Element getNodeViewReportBtn(String newNode) {
		return driver.FindElementByXPath(
				"//div[text()='" + newNode + "']//parent::a//child::div//button[contains(text(),'View Report')]");
	}

	public Element getSavedSearch(String ssName) {
		return driver.FindElementByXPath("//tbody//tr[1]//td[contains(text(),'" + ssName + "')]//parent::tr//td[2]");
	}

	public Element getSavedSearchNewGroupExpand() {
		return driver.FindElementByXPath("//*[contains(@class,'clicked')]//preceding-sibling::i");
	}

	public Element getNewNode(String newNode) {
		return driver.FindElementByXPath("//div[text()='" + newNode + "']");
	}

	// added by jeevitha
	public Element getLoginUserEdit() {
		return driver.FindElementByXPath("//table[@id='dtUserList']//tr//td//a[text()='Edit']");
	}

	public Element preRedactionReport() {
		return driver.FindElementByXPath("//a[text()='Download Pre-Redaction Report']");
	}

	public ElementCollection getBatchIDs() {
		return driver.FindElementsByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[1]");
	}

	public Element getProgress() {
		return driver.FindElementByXPath("(//div[@class='br-progress'])[2]//div");
	}

	public Element getProgressPercent() {
		return driver.FindElementByXPath(
				"(//div[@class='br-progress'])[2]//following::div[@class='br-progress-percent'][1]");
	}

	// Added By jeevitha
	public Element getManageFunctionalityTab() {
		return driver.FindElementByXPath("//a[contains(text(),'Functionality')]");
	}

	public Element getManageRedactionClickedCB() {
		return driver.FindElementByXPath(
				"//label//input[@id='UserRights_CanRedactions' and @checked='checked']//parent::label//i");
	}

	public Element getManageRedactionCB() {
		return driver.FindElementByXPath("//label//input[@id='UserRights_CanRedactions']//parent::label//i");
	}

	public Element getSaveBtn() {
		return driver.FindElementByXPath("//button[contains(text(),'Save')]");
	}

	public Element getClickReportBtn_InBRHistory(String ssName) {
		return driver.FindElementByXPath("//tbody//tr[1]//td[text()='" + ssName + "']//parent::tr//td[6]");
	}

	public Element getFileDownloadwithID() {
		return driver.FindElementByXPath("//a[contains(text(),'Your Batch Redaction report (ID ')]");
	}

	public Element getViewAllBtn() {
		return driver.FindElementByXPath("//button[@id='btnViewAll']");
	}

	public Element getStatusDD() {
		return driver.FindElementByXPath("//select[@id='ddlStatusList']/parent::label[@class='select']");
	}

	public Element getStatusDD_options() {
		return driver.FindElementByXPath("//select[@id='ddlStatusList']//option[text()='Batch Redactions']");
	}

	public Element getApplyBtnBgPage() {
		return driver.FindElementByXPath("//a[@id='btnAppyFilter']");
	}

	public ElementCollection getTaskColumn() {
		return driver.FindElementsByXPath("(//table[@id='dt_basic']//tbody/tr/td[text()='BATCHREDACTION'])");
	}

	public Element getTaskColumnName(int i) {
		return driver.FindElementByXPath("(//table[@id='dt_basic']//tbody/tr/td[text()='BATCHREDACTION'])[" + i + "]");
	}

	public Element getMessageCoulmn(int i) {
		return driver.FindElementByXPath("(//table[@id='dt_basic']//tbody/tr/td[9])[" + i + "]");
	}

	// Added BY jeevitha
	public Element getDefaultTab() {
		return driver.FindElementByXPath("//div[text()='Shared with Default Security Group']");
	}

	public Element getCancelAndRollBack(String searchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + searchName
				+ "']//following::td//a[text()='Cancel and Rollback']");
	}

	public Element getRollBackStatus(String searchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + searchName
				+ "']//..//child::td[5]");
	}

	public Element getCancelledRollBackStatus(String searchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + searchName
				+ "']//..//child::td[contains(text(),'Redaction Cancelled.')]");
	}

	// Added By Jeevitha
	public ElementCollection getBGTaskHeader() {
		return driver.FindElementsByXPath("(//thead//tr)[1]//th");
	}

	public Element getIDFromBGTaskPage(String ID) {
		return driver.FindElementByXPath("//tbody//tr//td[normalize-space()='" + ID + "']");
	}

	public ElementCollection getListFromBgTaskPage(String ID) {
		return driver.FindElementsByXPath("//tbody//tr//td[normalize-space()='" + ID + "']//parent::tr//td");
	}

	public ElementCollection getActualDocCount_BG(String ID, int columnNum) {
		return driver.FindElementsByXPath("//tbody//tr//td[normalize-space()='" + ID + "']//..//td[" + columnNum + "]");
	}

	public Element getSearchTab(String searchName) {
		return driver.FindElementByXPath(
				"//a[contains(@class,'jstree')]//following-sibling::ul//li[contains(@class,'-leaf')]//div[@title='"
						+ searchName + "']");
	}

	// Added by Jeevitha
	public Element getPageNumBar() {
		return driver.FindElementByXPath("//ul[@class='pagination pagination-sm']");
	}

	public Element getRecordCount() {
		return driver.FindElementByXPath("//div[@id='BatchRedactionsDataTable_info']");
	}

	public Element checkPreviousBtnDisabled() {
		return driver.FindElementByXPath("//li[@class='paginate_button previous disabled']");
	}

	public Element checkNextBtnDisabled() {
		return driver.FindElementByXPath("//li[@class='paginate_button next disabled']");
	}

	public Element getPageNum(int num) {
		return driver.FindElementByXPath("//li[@class='paginate_button ']//a[text()='" + num + "']");
	}

	public Element getLastPageNum() {
		return driver.FindElementByXPath("(//li[@class='paginate_button ']//a)[last()]");
	}

	public ElementCollection getPageNumb() {
		return driver.FindElementsByXPath("(//ul[@class='pagination pagination-sm']//a)");
	}

	public Element getActivePage() {
		return driver.FindElementByXPath("//li[@class='paginate_button active']");
	}

	// Added by jeevitha
	public Element getCloseBtn() {
		return driver.FindElementByXPath("//button[text()='Close']");
	}

	public Element getColorStatusToolTip(String SearchName) {
		return driver.FindElementByXPath("//table[@id='BatchRedactionsDataTable']//tbody//tr//td[text()='" + SearchName
				+ "']//following::td[3]//p//a");
	}

	public Element getCompletedToolTipMSg(String SearchName) {
		return driver.FindElementByXPath("(//tr[@role='row']//td[text()='" + SearchName
				+ "']//..//p[@class='CompWithError'])//a//following::div[@class='popover-content']");
	}

	public Element getCompletedToolTipIcon(String SearchName) {
		return driver.FindElementByXPath(
				"(//tr[@role='row']//td[text()='" + SearchName + "']//..//p[@class='CompWithError'])//a");
	}

	// Added by Raghuram
	public Element getOptionDropdown(String type) {
		return driver.FindElementByXPath("//div[text()='" + type + "']//..//..//i[@class='jstree-icon jstree-ocl']");
	}

	public ElementCollection getListofDatas(String name) {
		return driver.FindElementsByXPath("//div[text()='" + name + "']//..//..//li//a[@class='jstree-anchor']");
	}

	public Element getFileDownloadwithSpecificID(String id) {
		return driver.FindElementByXPath("//a[contains(text(),'Your Batch Redaction report (ID " + id + "')]");
	}

	public Element getInformativeErrorMessageText() {
		return driver.FindElementByXPath("//div[@class='form-group']//strong//label");
	}

	public Element getRedactDisabledBtn() {
		return driver.FindElementByXPath("//button[text()='Redact' and @disabled='disabled']");
	}

	public BatchRedactionPage(Driver driver) {

		this.driver = driver;

		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softassert = new SoftAssert();
		docview = new DocViewPage(driver);
		assign = new AssignmentsPage(driver);
		minidocList = new MiniDocListPage(driver);
		reUseDocPage = new ReusableDocViewPage(driver);
	}

	/**
	 * @throws InterruptedException
	 * @Created By jeevitha.R
	 * 
	 *          Description : Verifies Analyze Report And View Report And Verifies
	 *          Popup.
	 */

	public void savedSearchBatchRedaction(String searchname) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySavedSearchDropDown().Visible();
			}
		}), Input.wait60);
		getMySavedSearchDropDown().waitAndClick(20);
		getMySavedSearchTextbox(searchname).Selected();

		// Verify Analyze Report
		final int Bgcount = base.initialBgCount();

		boolean flag = verifyAnalyzeBtn(searchname, null);
		if (flag == false) {
			driver.Navigate().refresh();
			loadBatchRedactionPage(Input.mySavedSearch);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAnalyzeSearchForSavedSearchResult(searchname).Visible()
						&& getAnalyzeSearchForSavedSearchResult(searchname).Enabled();
			}
		}), Input.wait90);
		getAnalyzeSearchForSavedSearchResult(searchname).Click();
		System.out.println("Clicked on Analyze report");
		base.stepInfo("Clicked on Analyze report");

		verifyAnalyzePopupmsg().waitAndClick(10);

		// Verifies Progress Bar

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		final int bgCountAfter = base.initialBgCount();
		System.out.println("Progress Completed");
		base.stepInfo("Progress Completed");

		// verify View report
		base.waitForElement(getViewReportForSavedSearch(searchname));
		if (getViewReportForSavedSearch(searchname).isDisplayed()) {
			base.waitForElement(getViewReportForSavedSearch(searchname));
			getViewReportForSavedSearch(searchname).waitAndClick(10);
			System.out.println("Clicked on View Report");
			base.stepInfo("Clicked on View Report");
		} else {
			driver.Navigate().refresh();
			base.waitForElement(getMySavedSearchDropDown());
			getMySavedSearchDropDown().waitAndClick(20);
			getMySavedSearchTextbox(searchname).Selected();
			if (getViewReportForSavedSearch(searchname).Displayed()) {
				getViewReportForSavedSearch(searchname).waitAndClick(10);
				softassert.assertTrue(true);
				base.passedStep(
						"Analyze completed and Verify that \"View Report & Apply Redactions\" button appeared.");
			} else {
				softassert.assertFalse(false);
				base.failedStep("\"View Report & Apply Redactions\" button not appeared");
			}
		}

		// verify Pre-Redaction report Popup
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreRedactReportMessage1().Visible() && getPreRedactReportMessage1().Enabled();
			}
		}), Input.wait120);
		String firstLine = getPreRedactReportMessage1().getWebElement().getText();
		String secondLine = getPreRedactReportMessage2().getWebElement().getText();
		String thirdLine = getPreRedactReportMessage3().getWebElement().getText();
		System.out.println(firstLine);
		base.stepInfo(firstLine);
		System.out.println(secondLine);
		base.stepInfo(secondLine);
		System.out.println(thirdLine);
		base.stepInfo(thirdLine);

		// Click on redact button
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopUpRedactButton().Visible() && getPreRedactReportMessage1().Enabled();
			}
		}), Input.wait60);
		getPopUpRedactButton().Click();

		driver.waitForPageToBeReady();

		// verify Popup Message
		String Expected = "Please make sure that redactions are not being applied manually to the same documents while running this Batch Redaction as it can possibly create unexpected or lost redactions.";
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopupMessage().Visible();
			}
		}), Input.wait60);
		String text = getPopupMessage().getText();
		System.out.println(text);

		if (text.contains(Expected)) {
			base.passedStep(text);
		} else {
			base.failedMessage("Expected Msg doesnt match");
		}
	}

	/**
	 * @author jeevitha Description: Simple Batch Redcation process
	 * @param searchname
	 * @throws InterruptedException
	 */
	public void batchRedaction(String searchname) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySavedSearchDropDown().Visible();
			}
		}), Input.wait60);
		getMySavedSearchDropDown().Click();
		getMySavedSearchTextbox(searchname).Selected();

		// Verify Analyze Report
		final int Bgcount = base.initialBgCount();
		boolean flag = verifyAnalyzeBtn(searchname, null);
		if (flag == false) {
			driver.Navigate().refresh();
			loadBatchRedactionPage(Input.mySavedSearch);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAnalyzeSearchForSavedSearchResult(searchname).Visible()
						&& getAnalyzeSearchForSavedSearchResult(searchname).Enabled();
			}
		}), Input.wait90);
		getAnalyzeSearchForSavedSearchResult(searchname).Click();
		System.out.println("Clicked on Analyze report");
		base.stepInfo("Clicked on Analyze report");

		verifyAnalyzePopupmsg().waitAndClick(10);

		// Verifies Progress Bar

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		final int bgCountAfter = base.initialBgCount();
		System.out.println("Progress Completed");
		base.stepInfo("Progress Completed");

		// verify View report
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getViewReportForSavedSearch(searchname).Visible()
						&& getViewReportForSavedSearch(searchname).Enabled();
			}
		}), Input.wait60);

		getViewReportForSavedSearch(searchname).waitAndClick(10);
		System.out.println("Clicked on View Report");
		base.stepInfo("Clicked on View Report");

		// Verify Select Redcation Help Text
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRedactionHelpIcon().Visible();
			}
		}), Input.wait60);
		getSelectRedactionHelpIcon().waitAndClick(10);
		Thread.sleep(3000);
		String helpText = getRedactHelpText().getWebElement().getText();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRedactionHelpIcon().Visible();
			}
		}), Input.wait30);
		System.out.println("Retrieved help text as :" + helpText);
		base.stepInfo("Retrieved help text as :" + helpText);
		Thread.sleep(3000);
		String ExpectedText = "Upon reviewing the potential expected redactions from the selected search or search group, the user can proceed with the batch redaction run. To do so, the user must first 'select a redaction tag'. This redaction tag will be associated with each redaction that will be applied in the following batch redaction run, when the user invokes 'View and Redact'.";
		// Verification if tooltip text is matching expected value
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRedactHelpText().Visible();
			}
		}), Input.wait60);
		if (helpText.equalsIgnoreCase(ExpectedText)) {
			System.out.println("Pass : Tooltip matching expected value");
			base.stepInfo("Pass : Tooltip matching expected value");

		} else {
			System.out.println("Fail : help Text NOT matching expected value");
			base.stepInfo("Fail : Help Text NOT matching expected value");

		}
		base.hitEnterKey(1);

		// Click on redact button
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopUpRedactButton().Visible() && getPreRedactReportMessage1().Enabled();
			}
		}), Input.wait60);
		getPopUpRedactButton().waitAndClick(10);
	}

	/**
	 * @author jeevitha Description:verifies batch redcation header help icon
	 */
	public void BatchRedactionHelpIcon() {

		// Verify BAtch Redaction HElp Icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBAtchRedactionHelpIcon().Visible();
			}
		}), Input.wait120);
		String acutalValue = "helptip fa fa-question-circle";
		boolean expextedValue = getBAtchRedactionHelpIcon().GetAttribute("class")
				.contains("helptip fa fa-question-circle");
		if (expextedValue) {
			softassert.assertTrue(expextedValue);
			System.out.println("Verified Icon Of batch redaction  ");
			base.passedStep("Verified Icon Of batch redaction  ");
		} else {
			base.failedStep("Failed To verify BAtch Redaction icon");
		}

		// Bactch Redaction HElp Text
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBAtchRedactionHelpIcon().Visible();
			}
		}), Input.wait60);
		getBAtchRedactionHelpIcon().waitAndClick(10);
		String helpText = getHelpText().getText();

		System.out.println("Retrieved help text as :" + helpText);
		base.stepInfo("Retrieved help text as :" + helpText);

		base.hitEnterKey(1);
	}

	/**
	 * @author jeevitha Description: verifies BAtch redaction history help icon
	 */
	public void BatchRedactionHistoryHelpIcon() {
		// Verify BAtch Redaction History HElp Icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBAtchRedactionHistoryHelpIcon().Visible();
			}
		}), Input.wait120);
		String acutalValue1 = "helptip fa fa-question-circle";
		boolean expextedValue1 = getBAtchRedactionHistoryHelpIcon().GetAttribute("class")
				.contains("helptip fa fa-question-circle");
		if (expextedValue1) {
			softassert.assertTrue(expextedValue1);
			System.out.println("");
			System.out.println("Verified Icon Of batch redaction History ");
			base.passedStep("Verified Icon Of batch redaction History ");
		} else {
			base.failedStep("Failed To verify BAtch Redaction History icon");
		}

		// Bactch Redaction History HElp Text
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBAtchRedactionHistoryHelpIcon().Visible();
			}
		}), Input.wait60);
		getBAtchRedactionHistoryHelpIcon().waitAndClick(10);
		String helpText1 = getHelpText().getText();

		System.out.println("Retrieved help text as :" + helpText1);
		base.stepInfo("Retrieved help text as :" + helpText1);

		base.hitEnterKey(1);

	}

	/**
	 * @author Jeevitha Description: verifies Search group Help icons of batch
	 *         redaction page
	 */
	public void SearchGroupHelpIcon() {

		// Verify Select Search Group HElp Icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSearchHelpIcon().Visible();
			}
		}), Input.wait120);
		String acutalValue2 = "helptip fa fa-question-circle";
		boolean expextedValue2 = getSelectSearchHelpIcon().GetAttribute("class")
				.contains("helptip fa fa-question-circle");
		if (expextedValue2) {
			softassert.assertTrue(expextedValue2);
			System.out.println("");
			System.out.println("Verified Icon Of search group ");
			base.passedStep("Verified Icon Of search group  ");
		} else {
			base.failedStep("Failed To verify search group icon");
		}

		// Select Search Group HElp Text
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSearchHelpIcon().Visible();
			}
		}), Input.wait60);
		getSelectSearchHelpIcon().waitAndClick(10);
		String helpText2 = getHelpText().getText();

		System.out.println("Retrieved help text as :" + helpText2);
		base.stepInfo("Retrieved help text as :" + helpText2);

		base.hitEnterKey(1);
	}

	/**
	 * @author jeevitha
	 * @throws InterruptedException
	 */
	public void mySavedSearchRedaction() throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");

		// Verify Analyze Report
		final int Bgcount = base.initialBgCount();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySavedSearchAnalyzeBtn().Visible() && getMySavedSearchAnalyzeBtn().Enabled();
			}
		}), Input.wait90);
		getMySavedSearchAnalyzeBtn().Click();
		System.out.println("Clicked on Analyze report");
		base.stepInfo("Clicked on Analyze report");

		verifyAnalyzePopupmsg().waitAndClick(10);

		// Verifies Progress Bar

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		final int bgCountAfter = base.initialBgCount();
		System.out.println("Progress Completed");
		base.stepInfo("Progress Completed");

		// verify View report
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySavedSearchViewReportBtn().Visible() && getMySavedSearchViewReportBtn().Enabled();
			}
		}), Input.wait120);

		getMySavedSearchViewReportBtn().waitAndClick(10);
		System.out.println("Clicked on View Report");
		base.stepInfo("Clicked on View Report");

		// verify Pre-Redaction report Popup
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return verifyAnalyzePopupmsg().Visible() && verifyAnalyzePopupmsg().Enabled();
			}
		}), Input.wait30);
		String msg = viewReportAndBatchReport().getWebElement().getText();
		System.out.println(msg);
		base.stepInfo(msg);
	}

	/**
	 * @author Raghuram.A date: 9/09/21 Modified date:N/A Modified by: N/A
	 *         Description : History Status
	 */
	public void verifyHistoryStatus(String ssName) {
		driver.waitForPageToBeReady();
		base.waitForElement(getInProgressStatus(ssName));
		System.out.println("New row added in the batch redaction history : Status In-Progress");
		base.stepInfo("New row added in the batch redaction history : Status In-Progress");
		waitUntilStatusUpdate(ssName);
		verifyHIstoryStatuswithColorCode(ssName);
	}

	/**
	 * @author Raghuram.A date: 9/09/21 Modified date:21/9/2021 Modified by:
	 *         Jeevitha Description :verify HIstory Status with Color Code
	 *         Stabilized
	 */
	public void verifyHIstoryStatuswithColorCode(String ssName) {
		try {

			String color = null;
//			base.waitForElement(getColorStatusMsg(ssName));
			if (getColorStatusMsg(ssName).isDisplayed()) {
				color = getColorStatusMsg(ssName).GetCssValue("color");
				color = base.rgbTohexaConvertor(color);
			}
			if (color.equals(Input.completedWithErrColorCodeBR)) {
				System.out.println(getColorStatusMsg(ssName).getText() + " [Yellow color] " + color);
				base.stepInfo(getColorStatusMsg(ssName).getText() + "  [Yellow color] " + color);
			} else if (color.equals(Input.colorCodeOfRed)) {
				System.out.println(getColorStatusMsg(ssName).getText() + " [RED color] " + color);
				base.stepInfo(getColorStatusMsg(ssName).getText() + " [RED color] " + color);
			} else if (getSuccessStatus(ssName).isDisplayed()) {
				System.out.println(getSuccessStatus(ssName).getText());
				base.passedStep(getSuccessStatus(ssName).getText());
			} else {
				System.out.println("Color Code Doesn't Match");
				base.stepInfo("Color Code Doesn't Match");
			}
		} catch (Exception e) {
			System.out.println(getStatusMsg(ssName).getText());
			base.stepInfo(getStatusMsg(ssName).getText());
		}
	}

	/**
	 * @author jeevitha Decsription: verifies BAtch redaction File download
	 * @return
	 * @throws InterruptedException
	 */
	public String verifyBatchRedactionFileDownload() throws InterruptedException {

		// Download report
		driver.waitForPageToBeReady();
		downLoadBAtchRedactionReport();
		base.stepInfo("File Downloaded");

		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "\\";

		// base.csvReader();
		report = new ReportsPage(driver);
		File a = report.getLatestFilefromDir(testPath);
		System.out.println(a.getName());
		base.stepInfo(a.getName());

		String fileName = a.getName();

		fileName = testPath + fileName;
		System.out.println(fileName);
		base.stepInfo(fileName);
		return fileName;
	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException
	 */
	public void downLoadBAtchRedactionReport() throws InterruptedException {

		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);
		base.waitForElement(getFileDownload());
		String popUpMsg = getFileDownload().getText();
		String expectedMsg = "Your Batch Redaction analysis is complete. Please click here to download the report";
		softassert.assertEquals(popUpMsg, expectedMsg);
		getFileDownload().Click();
		getBullHornIcon().waitAndClick(2);
	}

	/**
	 * @author Jeevitha Description : Read BAtch redaction Excel Data
	 * @param fileName
	 * @throws IOException
	 */
	public void readExcel(String fileName) throws IOException {
		String data;
		File file = new File(fileName);
		try {
			FileInputStream xlFile = new FileInputStream(file);
			Workbook xlwb = new XSSFWorkbook(xlFile);
			Sheet xlSheet = xlwb.getSheetAt(0);

			int numRows = xlSheet.getLastRowNum();
			System.out.println("no of Rows : " + numRows);
			int numCols = xlSheet.getRow(numRows).getLastCellNum() - 1;
			System.out.println("NO of columns : " + numCols);

			Row row = xlSheet.getRow(numRows);
			Row xlRows = xlSheet.getRow(numRows);
			// HSSFCell xlCell = xlRows.getCell(numCols);

			data = xlRows.getCell(numCols).toString();
			System.out.println(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @Author Jeevitha
	 * @throws InterruptedException
	 */
	public void testDD() throws InterruptedException {
		// Click Reduction Button
		base.waitForElement(getRductionBtb());
		getRductionBtb().waitAndClick(5);

		docview.selectBatchRedactedDoc();

		// Choose Reduct
		getNextbtn().waitAndClick(3);

		Actions actions = new Actions(driver.getWebDriver());
		// Drag and Drop to x,y
		Thread.sleep(3000);// waiting for batch redacted text to resize
		actions.clickAndHold(getexpn().getWebElement());
		actions.dragAndDropBy(getexpn().getWebElement(), 50, 100).build().perform();
		System.out.println("Drag and Dropped");
		Thread.sleep(5000);// waiting for batch redacted text to resize
		// Expand reduction
		actions.dragAndDropBy(getRightBtm().getWebElement(), 50, 50).build().perform();
		System.out.println("Exnded");
		getDocViewSaveBtn().waitAndClick(10);
		System.out.println("REdaction Tag Saved Successfully");
	}

	/**
	 * @author jeevitha
	 * @param value
	 * @param searchname
	 * @throws InterruptedException
	 */
	public void savedSearchBatchRedaction1(String value, String searchname) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySavedSearchDropDown().Visible();
			}
		}), Input.wait60);
		getMySavedSearchDropDown().Click();
		getMySavedSearchTextbox(searchname).Selected();

		// Verify Analyze Report
		final int Bgcount = base.initialBgCount();

		boolean flag = verifyAnalyzeBtn(searchname, null);
		if (flag == false) {
			driver.Navigate().refresh();
			loadBatchRedactionPage(Input.mySavedSearch);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAnalyzeSearchForSavedSearchResult(searchname).Visible()
						&& getAnalyzeSearchForSavedSearchResult(searchname).Enabled();
			}
		}), Input.wait90);
		getAnalyzeSearchForSavedSearchResult(searchname).Click();
		System.out.println("Clicked on Analyze report");
		base.stepInfo("Clicked on Analyze report");

		verifyAnalyzePopupmsg().waitAndClick(10);

		// Verifies Progress Bar

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		final int bgCountAfter = base.initialBgCount();
		System.out.println("Progress Completed");
		base.stepInfo("Progress Completed");

		// verify View report
		if (getViewReportForSavedSearch(searchname).isElementAvailable(20)) {
			getViewReportForSavedSearch(searchname).waitAndClick(10);
			System.out.println("Clicked on View Report");
			base.stepInfo("Clicked on View Report");
		} else {
			driver.Navigate().refresh();
			base.waitForElement(getMySavedSearchDropDown());
			getMySavedSearchDropDown().waitAndClick(20);
			getMySavedSearchTextbox(searchname).Selected();
			getViewReportForSavedSearch(searchname).waitAndClick(20);
		}

		// verify Pre-Redaction report Popup
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreRedactReportMessage1().Visible() && getPreRedactReportMessage1().Enabled();
			}
		}), Input.wait120);
		String firstLine = getPreRedactReportMessage1().getWebElement().getText();
		String secondLine = getPreRedactReportMessage2().getWebElement().getText();
		String thirdLine = getPreRedactReportMessage3().getWebElement().getText();
		System.out.println(firstLine);
		base.stepInfo(firstLine);
		System.out.println(secondLine);
		base.stepInfo(secondLine);
		System.out.println(thirdLine);
		base.stepInfo(thirdLine);

		driver.waitForPageToBeReady();
		getRedactionTagDropDown().waitAndClick(10);
		getRedactionTagDropDownValue(value).waitAndClick(10);

		// Click on redact button
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopUpRedactButton().Visible() && getPreRedactReportMessage1().Enabled();
			}
		}), Input.wait60);
		getPopUpRedactButton().waitAndClick(10);

		driver.waitForPageToBeReady();

		// verify Popup Message
		String Expected = "Please make sure that redactions are not being applied manually to the same documents while running this Batch Redaction as it can possibly create unexpected or lost redactions.";
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopupMessage().Visible();
			}
		}), Input.wait60);
		String text = getPopupMessage().getWebElement().getText();

		System.out.println(text);
		if (text.contains(Expected)) {
			base.passedStep(text);
		} else {
			base.failedMessage("Expected Msg doesnt match");
		}
	}

	/**
	 * @author Jeevitha @Modified By jeevitha @Modified date : 14/2/2022
	 * @Description : perform Rollback and verify Rollback Success
	 * @param searchName
	 * @param select
	 */
	public void verifyRollback(String searchName, String select) {

		// Rollback Saved Search

		base.waitForElement(getRollbackbtn(searchName));
		getRollbackbtn(searchName).waitAndClick(10);

		base.waitTime(7);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopupYesBtn().Visible();
			}
		}), Input.wait60);
		String actual = getRollBackMsg().getText();
		String ExpectedPopUp = "Are you sure you wish to roll back this completed Batch Redaction?";
		if (actual.contains(ExpectedPopUp)) {
			base.passedStep(actual);
		}

		if (select.equalsIgnoreCase("Yes")) {
			getPopupYesBtn().waitAndClick(20);

			String ExpectedMsg = "Your request to Roll Back this Batch Redaction has been added to the background.  Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.";
			base.VerifySuccessMessageB(ExpectedMsg);
			// Rollback In progress Status
			base.waitForElement(getRollbackInProgressStatus(searchName));
			base.stepInfo(getRollbackInProgressStatus(searchName).getText());

			// Verify Rollback Success message
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getRollbackMsg(searchName).Visible();
				}
			}), Input.wait120);
			System.out.println(getRollbackMsg(searchName).getText());
			base.stepInfo(getRollbackMsg(searchName).getText());
		} else if (select.equalsIgnoreCase("No")) {
			getPopupNoBtn().waitAndClick(20);
			System.out.println("ROllback is not Perfomed");
		}

	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException
	 */
	public void rollbackBatchRedactionReport() throws InterruptedException {

		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);
		base.waitForElement(getRollBackNotification());
		System.out.println(getRollBackNotification().getText());
		base.stepInfo(getRollBackNotification().getText());
	}

	/**
	 * @author Jeevitha
	 * @param ssName
	 * @param hitCount
	 */
	public void verifyInprogressStatusChanges(String ssName, int hitCount) {
		driver.waitForPageToBeReady();
		boolean flag = true;
		while (flag) {
			base.waitForElement(getDocCountFromTable(ssName));
			String docCount = getDocCountFromTable(ssName).getText();
			if (String.valueOf(hitCount).equals(docCount))
				flag = false;
			if (flag == false) {
				System.out.println("Still IN Progress");
				if (getFailedStatus(ssName).isElementAvailable(5)) {
					getFailedStatus(ssName).waitAndClick(5);
					flag = false;
				} else if (getCompletedWithErrorsStatus(ssName).isElementAvailable(5)) {
					getCompletedWithErrorsStatus(ssName).waitAndClick(5);
					flag = false;
				} else {
					System.out.println("Still IN Progress");
				}
			}

		}
	}

	/**
	 * @author Jeevitha
	 * @param ssName
	 * @param purehit
	 */
	public void verifyHistoryStatus2(String ssName, int purehit) {
		driver.waitForPageToBeReady();
		base.waitForElement(getInProgressStatus(ssName));
		System.out.println("New row added in the batch redaction history : Status In-Progress");
		base.stepInfo("New row added in the batch redaction history : Status In-Progress");
		verifyInprogressStatusChanges(ssName, purehit);
		verifyHIstoryStatuswithColorCode(ssName);
	}

	/**
	 * @author Raghuram.A date: 10/21/21 Modified date:N/A Modified by:N/A
	 *         Description :batchPanelVerification
	 */
	public void verifyBatchReductionMenuFlow(String assignName) throws InterruptedException, AWTException {

		String passMessage = "Redaction Window is present";
		String failureMsg = "Redaction Window not present";
		Element getDocView_RedactionPanel = docview.getDocView_RedactionPanel();
		Element getDocView_AllRedationColumn = docview.getDocView_AllRedationColumn();
		Element getDocView_BatchRedactionColumn = docview.getDocView_BatchRedactionColumn();

		// View In docView
		docview.selectAssignmentfromDashborad(assignName);
		base.stepInfo("Doc is viewed in the docView Successfully");
		driver.waitForPageToBeReady();
		base.waitForElement(docview.getDocView_RedactIcon());
		docview.getDocView_RedactIcon().waitAndClick(5);
		base.stepInfo("Clicked Redaction Icon");
		// Batch panel and Window Verification
		batchPanelVerification(getDocView_RedactionPanel, getDocView_AllRedationColumn, getDocView_BatchRedactionColumn,
				passMessage, failureMsg);

		// Completed Document
		String currentDocName = docview.getDocView_SelectedDocID().getText();
		base.waitForElement(minidocList.getDocumentCompleteButton());
		minidocList.getDocumentCompleteButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		System.out.println("Completed Document : " + currentDocName);
		base.stepInfo("Completed Document : " + currentDocName);
		// Doc ID verification
		currentDocName = currentDOCIverification(currentDocName);
		// Batch panel and Window Verification
		batchPanelVerification(getDocView_RedactionPanel, getDocView_AllRedationColumn, getDocView_BatchRedactionColumn,
				passMessage, failureMsg);

		// Complete same as last
		docview.getLastCodeIconWhitePencil().waitAndClick(5);
		driver.waitForPageToBeReady();
		System.out.println("Complete same as last : " + currentDocName);
		base.stepInfo("Complete same as last : " + currentDocName);
		// Doc ID verification
		currentDocName = currentDOCIverification(currentDocName);
		// Batch panel and Window Verification
		batchPanelVerification(getDocView_RedactionPanel, getDocView_AllRedationColumn, getDocView_BatchRedactionColumn,
				passMessage, failureMsg);

		// Coding Stamp
		docview.docViewCodingFormPanelStampSelectionWithoutSave(Input.stampColour);
		driver.waitForPageToBeReady();
		System.out.println("Coding Stamp : " + currentDocName);
		base.stepInfo("Coding Stamp : " + currentDocName);
		// Batch panel and Window Verification
		batchPanelVerification(getDocView_RedactionPanel, getDocView_AllRedationColumn, getDocView_BatchRedactionColumn,
				passMessage, failureMsg);

		// Child window Actions Complete Button
		String parentWindow = driver.getWebDriver().getWindowHandle();

		reUseDocPage.clickGearIconOpenCodingFormChildWindow();
		String parentID = reUseDocPage.switchTochildWindow();
		reUseDocPage.getCompleteDocBtn().waitAndClick(5);
		driver.switchTo().window(parentWindow);
		base.stepInfo("Complete action done via Child Window");
		driver.waitForPageToBeReady();
		System.out.println("Complete Document Via childWindow : " + currentDocName);
		base.stepInfo("Complete Document Via childWindow : " + currentDocName);
		driver.waitForPageToBeReady();
		// Doc ID verification
		currentDocName = currentDOCIverification(currentDocName);
		// Batch panel and Window Verification
		batchPanelVerification(getDocView_RedactionPanel, getDocView_AllRedationColumn, getDocView_BatchRedactionColumn,
				passMessage, failureMsg);
		for (String CodingFormChild : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(CodingFormChild);
			System.out.println(CodingFormChild);
		}

		// Child window Actions Complete same as last
		docview.getLastCodeIconWhitePencil().waitAndClick(5);
		driver.waitForPageToBeReady();
		System.out.println("Complete same as last : " + currentDocName);
		base.stepInfo("Complete same as last : " + currentDocName);
		driver.waitForPageToBeReady();
		reUseDocPage.childWindowToParentWindowSwitching(parentWindow);
		driver.waitForPageToBeReady();
		// Doc ID verification
		currentDocName = currentDOCIverification(currentDocName);
		driver.waitForPageToBeReady();
		// Batch panel and Window Verification
		batchPanelVerification(getDocView_RedactionPanel, getDocView_AllRedationColumn, getDocView_BatchRedactionColumn,
				passMessage, failureMsg);
	}

	/**
	 * @author Raghuram.A date: 10/21/21 Modified date:N/A Modified by:N/A
	 *         Description :batchPanelVerification
	 */
	public void batchPanelVerification(Element getDocView_RedactionPanel, Element getDocView_AllRedationColumn,
			Element getDocView_BatchRedactionColumn, String passMessage, String failureMsg) {
		docview.elementAvailability(getDocView_RedactionPanel, passMessage, failureMsg);
		docview.elementAvailability(getDocView_AllRedationColumn, passMessage + "All Redaction panel", failureMsg);
		docview.elementAvailability(getDocView_BatchRedactionColumn, passMessage + "Batch Redaction panel", failureMsg);
	}

	/**
	 * @author Raghuram.A date: 10/21/21 Modified date:N/AModified by:N/A
	 *         Description :currentDOCIverification
	 */
	public String currentDOCIverification(String currentDocName) {
		String afterCurrentDocName = docview.getDocView_SelectedDocID().getText();
		base.stepInfo("Current Document ID" + afterCurrentDocName);
		System.out.println(currentDocName);
		System.out.println(afterCurrentDocName);
		softassert.assertNotSame(currentDocName, afterCurrentDocName);
		base.passedStep("Navigated to the next document");
		currentDocName = afterCurrentDocName;
		return currentDocName;
	}

	/**
	 * @author Jeevitha
	 * @modified By Jeevitha
	 * @modified Date : 1/12/2021
	 * @param ssName
	 */
	public void verifyBatchHistoryStatus(String ssName) {
		driver.waitForPageToBeReady();
		base.waitForElement(getInProgressStatus(ssName));

		if (getInProgressStatus(ssName).isElementAvailable(10)) {
			System.out.println("New row added in the batch redaction history : Status In-Progress");
			base.stepInfo("New row added in the batch redaction history : Status In-Progress");
			String completeTextOfSearchGp = getSearchHitCount(ssName).getText();
			String searchStatus = completeTextOfSearchGp.substring(completeTextOfSearchGp.indexOf(")") + 1,
					completeTextOfSearchGp.indexOf("."));
			if (searchStatus.contains("Batch Redacting")) {
				System.out.println("search Status Format : " + searchStatus);
				base.passedStep("search Status Format : " + searchStatus);
			}
		}

		waitUntilStatusUpdate(ssName);
		verifyHIstoryStatuswithColorCode(ssName);
	}

	/**
	 * @author jeevitha Description : Waits Until status is updated
	 * @param ssName
	 */
	public void waitUntilStatusUpdate(String ssName) {
		driver.waitForPageToBeReady();
		boolean flag = true;
		while (flag) {
			String currentUrl = driver.getWebDriver().getCurrentUrl();
			if ((Input.url + "BatchRedaction/BatchRedaction").equals(currentUrl)) {
				if (getFailedStatus(ssName).isDisplayed()) {
					System.out.println("Status : Failed");
					flag = false;
				} else if (getCompletedWithErrorsStatus(ssName).isDisplayed()) {
					System.out.println("Status : Completed with Error");
					flag = false;
				} else if (getSuccessStatus(ssName).isDisplayed()) {
					System.out.println("Status : Successfully Completed");
					flag = false;
				}
			} else {
				base.failedStep("Session Logged Out");
				break;
			}
		}
	}

	/*
	 * @author Jeevitha
	 */
	public void loadBatchRedactionPage(String groupName) {
		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		driver.waitForPageToBeReady();
		if (groupName.equals(Input.shareSearchDefaultSG)) {
			base.waitForElement(getOptionDropdown(Input.shareSearchDefaultSG));
			getOptionDropdown(Input.shareSearchDefaultSG).waitAndClick(5);
		} else {
			base.waitForElement(getOptionDropdown(Input.mySavedSearch));
			getOptionDropdown(Input.mySavedSearch).waitAndClick(5);
		}
	}

	/*
	 * @author Jeevitha Description : Search Toop tip Message
	 */
	public void searchToolTipMsg(String searchname) {
		driver.waitForPageToBeReady();

		if (getSearchHitCount(searchname).isElementPresent()) {
			base.waitForElement(getSearchHitCount(searchname));
			String searchMsg = getSearchHitCount(searchname).getText();
			base.passedStep("Search Details : " + searchMsg);
		} else {
			base.failedMessage("Search Hit Count Not Found ");
		}

		if (getSearchToolTip(searchname).isElementPresent()) {
			base.waitForElement(getSearchToolTip(searchname));
			String msg = getSearchToolTip(searchname).GetAttribute("title");
			String expectedMsg = "This hit count represents the total docs returned by the search query. The date/time presented represents date and time when the saved search was last executed.";
			base.stepInfo("ToolTip Text : " + msg);
			softassert.assertEquals(expectedMsg, msg);
		} else {
			base.failedMessage("Search Hit Count Not Found ");
		}
	}

	/**
	 * @author Jayanthi.ganesan modified date:17/11/2021 modified by Jeevitha
	 */
	/**
	 * @author Jayanthi.ganesan
	 */
	public void VerifyBatchRedaction_ElementsDisplay(String searchname, boolean status) {
		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		driver.waitForPageToBeReady();
		base.waitForElement(getMySavedSearchDropDown());
		getMySavedSearchDropDown().waitAndClick(20);
		getMySavedSearchTextbox(searchname).Selected();
		final int Bgcount = base.initialBgCount();

		boolean flag = verifyAnalyzeBtn(searchname, null);

		System.out.println(flag);
		if (flag == false) {
			driver.Navigate().refresh();
			loadBatchRedactionPage(Input.mySavedSearch);
		}

		base.waitForElement(getAnalyzeSearchForSavedSearchResult(searchname));
		if (getAnalyzeSearchForSavedSearchResult(searchname).isElementAvailable(20)) {
			getAnalyzeSearchForSavedSearchResult(searchname).waitAndClick(10);
			softassert.assertTrue(true);
			base.passedStep("Analyze Search for Redaction button is visible on Batch Redaction Screen.");
			base.stepInfo("Clicked on Analyze report");
		} else {
			softassert.assertFalse(false);
			base.failedStep("Analyze Search for Redaction button on Batch Redaction Screen is not visible.");
		}
		verifyAnalyzePopupmsg().waitAndClick(10);
		// verify View report Button.
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		final int bgCountAfter = base.initialBgCount();
		base.waitForElement(getViewReportForSavedSearch(searchname));
		if (getViewReportForSavedSearch(searchname).isElementAvailable(20)) {
			getViewReportForSavedSearch(searchname).waitAndClick(10);
			System.out.println("Clicked on View Report");
			base.stepInfo("Clicked on View Report");
		} else {
			driver.Navigate().refresh();
			base.waitForElement(getMySavedSearchDropDown());
			getMySavedSearchDropDown().waitAndClick(20);
			getMySavedSearchTextbox(searchname).Selected();
			if (getViewReportForSavedSearch(searchname).Displayed()) {
				getViewReportForSavedSearch(searchname).waitAndClick(10);
				softassert.assertTrue(true);
				base.passedStep(
						"Analyze completed and Verify that \"View Report & Apply Redactions\" button appeared.");
			} else {
				softassert.assertFalse(false);
				base.failedStep("\"View Report & Apply Redactions\" button not appeared");
			}
		}
		try {
			base.waitForElement(getAnalysisReportPopup());
			if (getAnalysisReportPopup().Displayed()) {
				softassert.assertTrue(true);
				base.passedStep("View Report & Apply Redactions pop up appeared on screen.");
			}
		} catch (Exception e) {
			softassert.assertFalse(false);
			e.printStackTrace();
		}
	}

	/*
	 * @author Jeevitha Modified date: 16/11/2021 Description : Perform Batch
	 * redcation From Node
	 */
	public void verifyNodeAnalyseAndViewBtn(String node, String nodesearch, String subNode, String subNodeSearch) {
		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		base.waitForElement(getMySavedSearchDropDown());
		getMySavedSearchDropDown().waitAndClick(5);
		final int Bgcount = base.initialBgCount();

		// Verify Analyze Btn
		base.waitForElement(getNodeAnalyseBtn(node));

		if (getNodeAnalyseBtn(node).isElementPresent()) {
			getNewNode(node).waitAndClick(10);
			base.waitForElement(getSavedSearchNewGroupExpand());
			getSavedSearchNewGroupExpand().waitAndClick(5);
			base.stepInfo(node + " : is Present");
			driver.waitForPageToBeReady();
			if (getMySavedSearchTextbox(nodesearch).isDisplayed()) {
				base.passedStep(nodesearch + " Is Present In " + node);
			} else if (getSearchTab(nodesearch).isDisplayed()) {
				base.passedStep(nodesearch + " Is Present In " + node);
			} else {
				base.failedStep("Expected search is not found in : " + node);
			}

			if (getNewNode(subNode).isDisplayed()) {
				getNewNode(subNode).waitAndClick(10);
				base.waitForElement(getSavedSearchNewGroupExpand());
				getSavedSearchNewGroupExpand().waitAndClick(5);
				base.stepInfo(subNode + " : is Present");

				if (getMySavedSearchTextbox(subNodeSearch).isDisplayed()) {
					base.passedStep(subNodeSearch + " Is Present In " + subNode);
				} else {
					base.failedStep(subNodeSearch + " Expected search is not found in : " + subNode);

				}
			} else {
				base.stepInfo(subNode + "  : Sub-Node is not Present");
			}
			base.waitForElement(getNodeAnalyseBtn(node));
			getNodeAnalyseBtn(node).waitAndClick(10);
			base.stepInfo("Performed Analysis for " + node);
		}

		// verify View report Button.
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		final int bgCountAfter = base.initialBgCount();
		// verify View Report Btn
		base.waitForElement(getNodeViewReportBtn(node));
		if (getNodeViewReportBtn(node).isElementAvailable(10)) {
			getNodeViewReportBtn(node).waitAndClick(10);
			base.stepInfo("Performed View Report for " + node);

		} else {
			driver.Navigate().refresh();
			base.waitForElement(getMySavedSearchDropDown());
			getMySavedSearchDropDown().waitAndClick(20);
			base.waitForElement(getNodeViewReportBtn(node));
			if (getNodeViewReportBtn(node).isElementPresent()) {
				getNodeViewReportBtn(node).waitAndClick(10);
				base.stepInfo("Performed View Report for " + node);
			} else {
				base.failedStep("View Report & Apply Redaction button Not Visible");
			}

		}

	}

	/*
	 * @author Jeevitha
	 */
	public void viewAnalysisAndBatchReport(String Redactiontag, String select) {

		// verify Pre-Redaction report Popup
		driver.waitForPageToBeReady();
		base.waitForElement(getPreRedactReportMessage1());

		if (getVeiwAnalysisReportPopup().isElementPresent()) {
			String msg = getVeiwAnalysisReportPopup().getWebElement().getText();
			System.out.println(msg);
			base.stepInfo(msg);
		}

		driver.waitForPageToBeReady();
		getRedactionTagDropDown().waitAndClick(10);
		getRedactionTagDropDownValue(Redactiontag).waitAndClick(10);
		// getRedactionTagDropDown().selectFromDropdown().selectByVisibleText(Redactiontag);

		// Click on redact button
		base.waitForElement(getPopUpRedactButton());
		getPopUpRedactButton().waitAndClick(15);

		// verify Popup Message
		String Expected = "Please make sure that redactions are not being applied manually to the same documents while running this Batch Redaction as it can possibly create unexpected or lost redactions.";

		base.waitForElement(getPopupMessage());
		if (getPopupMessage().isElementPresent()) {
			String text = getPopupMessage().getText();
			System.out.println(text);
			if (text.contains(Expected)) {
				base.passedStep(text);
			} else {
				base.failedMessage("Expected Msg doesnt match");
			}
		}

		// verify Font-Size of PopUp
		String expectedSize = "font-size: 19px;";
		String fontSize = getPopupMessage().GetAttribute("style");
		System.out.println(fontSize);
		base.stepInfo(fontSize);
		softassert.assertEquals(expectedSize, fontSize);

		if (select.equalsIgnoreCase("Yes")) {
			base.waitForElement(getPopupYesBtn());
			String value = getPopupYesBtn().getText();
			getPopupYesBtn().waitAndClick(5);
			base.stepInfo("Clicked " + value + " Button");
			base.CloseSuccessMsgpopup();
		} else if (select.equalsIgnoreCase("No")) {
			base.waitForElement(getPopupNoBtn());
			String value = getPopupNoBtn().getText();
			getPopupNoBtn().waitAndClick(5);
			base.stepInfo("Clicked " + value + " Button");
		}
	}

	/**
	 * @author jeevitha Description : Verify Pre Redaction Report Link and download
	 *         File
	 * @return
	 * @throws InterruptedException
	 */
	public String verifyPreRedactionReport() throws InterruptedException {

		if (preRedactionReport().isElementPresent()) {
			base.waitForElement(getPreRedactReportMessage1());
			preRedactionReport().waitAndClick(10);

			base.VerifySuccessMessage("Report generation started");
		}

		// download File
		String fileName = verifyBatchRedactionFileDownload();

		String actualfileName = FilenameUtils.getBaseName(fileName);
		String fileFormat = FilenameUtils.getExtension(fileName);
		String expectedFormat = "xlsx";

		softassert.assertEquals(fileFormat, expectedFormat);
		return fileName;
	}

	/*
	 * @author Jeevitha
	 */
	public void performAnalysisGroupForRedcation(String searchname, String newNode, int purehit, boolean download,
			boolean checkAnalyze) throws InterruptedException, IOException {

		String bullHornValue = getBullHornIcon_CC().getText();
		int valueBeforeAnalysis = Integer.parseInt(bullHornValue);

		if (checkAnalyze) {
			boolean flag = verifyAnalyzeBtn(searchname, newNode);
			if (flag == false) {
				driver.Navigate().refresh();
				loadBatchRedactionPage(Input.mySavedSearch);
			}
		}

		// CLick Analysis Btn
		if (getAnalyzeSearchForSavedSearchResult(searchname).isElementAvailable(5)) {
			base.waitForElement(getAnalyzeSearchForSavedSearchResult(searchname));
			getAnalyzeSearchForSavedSearchResult(searchname).Click();
			softassert.assertTrue(true);
			base.passedStep(
					"Analyze Search for Redaction button is VISIBLE for :" + searchname + " under MY SAVED SEARCH TAB");
			base.stepInfo("Clicked on Analyze report");
		} else if (getNodeAnalyseBtn(newNode).isDisplayed()) {
			getNewNode(newNode).waitAndClick(10);
			base.waitForElement(getSavedSearchNewGroupExpand());
			getSavedSearchNewGroupExpand().waitAndClick(5);
			base.stepInfo(newNode + " : is Present");
			if (getMySavedSearchTextbox(searchname).isDisplayed()) {
				base.passedStep(searchname + " Is Present In " + newNode);

				// verify analyse button for search under Node
				if (getAnalyzeSearchForSavedSearchResult(searchname).isDisplayed()) {
					softassert.assertTrue(true);
					base.passedStep("Analyze Search for Redaction button is visible on Batch Redaction Screen for :"
							+ searchname + "under MY SAVED SEARCH TAB");
				} else {
					base.failedStep("Analyze Search for Redaction button is NOT VISIBLE  for :" + searchname);
				}

			} else {
				base.failedStep("Expected search is not found in : " + newNode);
			}
			base.waitForElement(getNodeAnalyseBtn(newNode));
			getNodeAnalyseBtn(newNode).waitAndClick(10);
			base.passedStep("Analyze Search for Redaction button is visible for:" + newNode);
			base.stepInfo("Clicked on Analyze report");
		} else {
			softassert.assertFalse(false);
			base.stepInfo("Analyze Search for Redaction button on Batch Redaction Screen is not visible.");
		}

		// verify Progress Bar
		driver.waitForPageToBeReady();
//		base.waitForElement(getProgressPercent());
		if (getProgressPercent().isDisplayed()) {
			getProgressPercent().isElementAvailable(15);
			System.out.println("Percentage is : " + getProgressPercent().getText());
			base.stepInfo("Percentage is : " + getProgressPercent().getText());

			// verify Color Of Percentage Bar
			String color = getProgress().GetCssValue("background-color");
			color = base.rgbTohexaConvertor(color);
			if (color.equalsIgnoreCase(Input.progresBarColor)) {
				System.out.println("Percentage Bar is [Blue color] :" + color);
				base.stepInfo("Percentage Bar is [Blue color] :" + color);
			} else {
				base.failedMessage("Color Doesnot Match");
			}
		}
//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getProgressBar().Visible() && getProgressBar().Displayed();
//			}
//		}), Input.wait30);
		String bullHornValue2 = getBullHornIcon_CC().getText();
		int valueAfterAnalysis = Integer.parseInt(bullHornValue2);

		// Download file Only If BullHorn notification Appears
		if (valueAfterAnalysis > valueBeforeAnalysis) {

			// verify Bull Horn Icon
			softassert.assertEquals(valueAfterAnalysis, valueBeforeAnalysis);
			base.passedStep("Bull horn icon has New Notification");

			// verify color of bullhorn
			String color2 = getBullHornIcon().GetCssValue("color");
			color2 = base.rgbTohexaConvertor(color2);
			System.out.println(color2);
			if (color2.equalsIgnoreCase(Input.bullHornIconColor)) {
				System.out.println("Notification is [Red color] : " + color2);
				base.stepInfo("Notification is [Red color] : " + color2);
			} else {
				base.failedMessage("Color Doesnot Match");
			}

			// Analysis REport Download
			if (download) {
				String fileName = verifyBatchRedactionFileDownload();
				String[][] record = base.readExcelData(fileName, 4);
				for (String[] data : record) {

					if (data[0] != null) {
						String value = data[2];
						System.out.println(data[1] + "   " + data[2]);
						base.stepInfo(data[1] + "   " + data[2]);
						double purehitd = purehit;
						double valued = Double.parseDouble(value);

						if (purehitd > valued) {
							softassert.assertTrue(true);
							System.out.println(purehitd + " is greater Than " + valued);
						} else {
							base.failedStep("Failure : Expected TermCount is Greater Than PureHit Hit Count");
						}
					}
				}
			}
		}

	}

	/**
	 * @author Jeevitha
	 * @Description : Assigning Redaction Rights to Users
	 * @param username
	 * @param selectEnable
	 * @Modified : 29/08/2022
	 * @throws InterruptedException
	 */
	public void assignRedactionRights(String username, boolean selectEnable) throws InterruptedException {
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		actions.moveToElement(docViewRedact.userTextInput().getWebElement());
		actions.click();
		actions.sendKeys(username);
		actions.build().perform();
		actions.moveToElement(docViewRedact.userFiletersBtn().getWebElement());
		actions.click().build().perform();
		Thread.sleep(Input.wait3);
		driver.waitForPageToBeReady();
		base.waitForElement(getLoginUserEdit());
		getLoginUserEdit().waitAndClick(5);

		base.waitForElement(getManageFunctionalityTab());
		getManageFunctionalityTab().waitAndClick(10);

		driver.waitForPageToBeReady();

		if (getManageRedactionClickedCB().isElementAvailable(15)) {
			getManageRedactionClickedCB().waitAndClick(10);
			base.stepInfo("Disabled the Redaction Rights to : " + username);
		} else if (getManageRedactionCB().isElementPresent()) {
			base.stepInfo("Redaction Rights is already Disabled to : " + username);
		}
		if (selectEnable) {
			getManageRedactionCB().waitAndClick(10);
			base.stepInfo("Enabled Redaction Right to user : " + username);
		}
		base.waitForElement(getSaveBtn());
		getSaveBtn().waitAndClick(10);

		base.VerifySuccessMessage("User profile was successfully modified");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param ssName
	 * @throws InterruptedException
	 */
	public void VerifyBGMessageForReportDownload(String ssName) throws InterruptedException {
		String bullHornValue = getBullHornIcon_CC().getText();
		System.out.println(bullHornValue);
		int valueBforeAnalysis = Integer.parseInt(bullHornValue);
		String batchId = getBatchId(ssName).getText();
		System.out.println(batchId.length());
		int index = 4;
		String batchID = batchId.substring(index);
		base.ValidateElement_Presence(getClickReportBtn_InBRHistory(ssName), "Click here for report link");
		getClickReportBtn_InBRHistory(ssName).waitAndClick(30);
		base.VerifySuccessMessage("Report generation started");
		checkAndValidateBgTask(valueBforeAnalysis, true);
		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(30);
		base.waitForElement(getFileDownloadwithID());
		String popUpMsg = getFileDownloadwithID().getText();
		String expectedMsg = "Your Batch Redaction report (ID " + batchID
				+ ") has been completed. Click here to download the report";
		System.out.println(popUpMsg);
		System.out.println(batchId);
		softassert.assertEquals(popUpMsg, expectedMsg);
		getFileDownload().waitAndClick(20);
		getBullHornIcon().waitAndClick(20);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param valueBeforeAnalysis
	 * @param BgCheck
	 * @throws InterruptedException
	 */

	public void checkAndValidateBgTask(int valueBeforeAnalysis, boolean BgCheck) throws InterruptedException {
		Thread.sleep(10000);
		String bullHornValue2 = getBullHornIcon_CC().getText();
		int valueAfterAnalysis = Integer.parseInt(bullHornValue2);

		// Download file Only If BullHorn notification Appears
		if (valueAfterAnalysis > valueBeforeAnalysis) {
			// verify Bull Horn Icon
			softassert.assertEquals(valueAfterAnalysis, valueBeforeAnalysis);
			base.passedStep("Bull horn icon has New Notification");
			// verify color of bullhorn
			String color2 = getBullHornIcon().GetCssValue("color");
			color2 = base.rgbTohexaConvertor(color2);
			System.out.println(color2);
			if (color2.equalsIgnoreCase(Input.bullHornIconColor)) {
				System.out.println("Notification is [Red color] : " + color2);
				base.stepInfo("Notification is [Red color] : " + color2);
			} else {
				base.failedMessage("Color Doesnot Match");
			}

		}
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void verifyStatusInBackGroundTaskPg(int bgCount) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == bgCount + 1;
			}
		}), Input.wait60);
		final int bgCountAfter = base.initialBgCount();

		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(20);
		base.waitForElement(getViewAllBtn());
		getViewAllBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		String url = driver.getUrl();
		String expURL = Input.url + "Background/BackgroundTask";
		softassert.assertEquals(expURL, url);
		base.stepInfo("Navigated to My backgroud task page.");
		base.waitForElement(getStatusDD());
		getStatusDD().waitAndClick(40);
		base.waitForElement(getStatusDD_options());
		getStatusDD_options().waitAndClick(30);
		base.waitForElement(getApplyBtnBgPage());
		getApplyBtnBgPage().waitAndClick(30);
		base.stepInfo("Applied the filter as batch redaction");
		base.waitForElementCollection(getTaskColumn());
		String AssertTaskName = null;
		String AssertMessageName = null;
		ArrayList<String> MessageNameList = new ArrayList<String>(Arrays.asList("Batch Redaction",
				"Batch Redaction Analysis", "Batch Redaction Rolled Back", "Batch Redaction Cancelled"));
		System.out.println(getTaskColumn().size());
		for (int D = 1; D <= getTaskColumn().size(); D++) {
			driver.waitForPageToBeReady();
			base.waitForElement(getTaskColumnName(D));
			AssertTaskName = getTaskColumnName(D).getText();
			AssertMessageName = getMessageCoulmn(D).getText().trim();
			System.out.println(AssertMessageName);
			if (AssertTaskName.contentEquals("BATCHREDACTION") && MessageNameList.contains(AssertMessageName)) {
				base.stepInfo("TaskName displayed is " + AssertTaskName);
				base.stepInfo("Task Message displayed is " + AssertMessageName);
				softassert.assertTrue(true);
				continue;
			} else {
				softassert.assertTrue(false);
				break;
			}

		}
		base.passedStep(
				"Sucessfully verified the result  displayed on ''My BackgroundTask Page' for the selected Filter type 'Batch redaction'");
		softassert.assertAll();
	}

	/**
	 * @author Jeevitha Description : perform View report and apply redaction
	 * @param node
	 */
	public void performViewReportAndApplyRedactions(String node) {
		// verify View Report Btn For Node

		if (getNodeViewReportBtn(node).isElementAvailable(20)) {
			getNodeViewReportBtn(node).waitAndClick(10);
			base.stepInfo("Performed View Report for " + node);

		} else {
			driver.Navigate().refresh();
			base.waitForElement(getMySavedSearchDropDown());
			getMySavedSearchDropDown().waitAndClick(20);

			if (getNodeViewReportBtn(node).isElementAvailable(20)) {
				getNodeViewReportBtn(node).waitAndClick(10);
				base.stepInfo("Performed View Report for " + node);
				base.passedStep(
						"Analyze completed and Verify that \"View Report & Apply Redactions\" button appeared. for NODE under MY SAVED SEARCH TAB");
			} else if (getViewReportForSavedSearch(searchname).isElementPresent()) {
				getViewReportForSavedSearch(searchname).waitAndClick(10);
				softassert.assertTrue(true);
				base.passedStep(
						"Analyze completed and Verify that \"View Report & Apply Redactions\" button appeared. for Search under MY SAVED SEARCH TAB");
			} else {
				base.failedStep("View Report & Apply Redaction button Not Visible");
			}

		}
	}

	/**
	 * @author jeevitha Description : perform batch redaction for saerch/search
	 *         group under Default TAb
	 * @param searchname
	 * @param newNode
	 */
	public void performAnalysisFromDefaultTab(String searchname, String newNode) {
		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		base.waitForElement(getDefaultTab());
		getDefaultTab().waitAndClick(10);
		getSavedSearchNewGroupExpand().waitAndClick(10);

		boolean flag = verifyAnalyzeBtn(searchname, null);
		if (flag == false) {
			driver.Navigate().refresh();
			loadBatchRedactionPage(Input.shareSearchDefaultSG);
		}

		// CLick Analysis Btn For Search Under Default TAB
		if (getAnalyzeSearchForSavedSearchResult(searchname).isElementAvailable(5)) {
			base.waitForElement(getAnalyzeSearchForSavedSearchResult(searchname));
			getAnalyzeSearchForSavedSearchResult(searchname).Click();
			softassert.assertTrue(true);
			base.passedStep(
					"Analyze Search for Redaction button  IN Deafult Tab is visible on Batch Redaction Screen.");
			base.stepInfo("Clicked on Analyze report");
		} else if (getNodeAnalyseBtn(newNode).isElementAvailable(5)) {
			getNewNode(newNode).waitAndClick(10);
			base.waitForElement(getSavedSearchNewGroupExpand());
			getSavedSearchNewGroupExpand().waitAndClick(5);
			base.stepInfo(newNode + " : is Present");
			if (getMySavedSearchTextbox(searchname).isElementPresent()) {
				base.passedStep(searchname + " Is Present In " + newNode);

				// verify analyse button for search under Node
				if (getAnalyzeSearchForSavedSearchResult(searchname).isElementPresent()) {
					softassert.assertTrue(true);
					base.passedStep(
							"Analyze Search for Redaction button IN Deafult Tab is visible on Batch Redaction Screen for :"
									+ searchname);
				} else {
					base.failedStep(
							"Analyze Search for Redaction button IN Deafult Tab is NOT VISIBLE  for :" + searchname);
				}

			} else {
				base.failedStep("Expected search is not found in : " + newNode);
			}
			base.waitForElement(getNodeAnalyseBtn(newNode));
			getNodeAnalyseBtn(newNode).waitAndClick(10);
			base.passedStep("Analyze Search for Redaction button IN Deafult Tab is visible for:" + newNode);
			base.stepInfo("Clicked on Analyze report");
		} else {
			softassert.assertFalse(false);
			base.stepInfo("Analyze Search for Redaction button IN Deafult Tab is NOT VISIBLE In Default Tab");
		}

		// verify View report Button. Under Default TAb

		if (getViewReportForSavedSearch(searchname).isElementAvailable(10)) {
			getViewReportForSavedSearch(searchname).waitAndClick(10);
			softassert.assertTrue(true);
			base.passedStep("Analyze completed and \"View Report & Apply Redactions\" button appeared. for Search");
		} else {
			driver.Navigate().refresh();
			base.waitForElement(getDefaultTab());
			getDefaultTab().waitAndClick(10);
			getSavedSearchNewGroupExpand().waitAndClick(10);
			if (getViewReportForSavedSearch(searchname).isElementPresent()) {
				getViewReportForSavedSearch(searchname).waitAndClick(10);
				softassert.assertTrue(true);
				base.passedStep(
						"Analyze completed and Verify that \"View Report & Apply Redactions\" button appeared. for Search");
			} else if (getNodeViewReportBtn(newNode).isElementPresent()) {
				getNodeViewReportBtn(newNode).waitAndClick(10);
				base.stepInfo("Performed View Report for " + newNode);
				base.passedStep(
						"Analyze completed and Verify that \"View Report & Apply Redactions\" button appeared. for NODE");
			} else {
				softassert.assertFalse(false);
				base.stepInfo("View Report & Apply Redaction button Not Visible");
			}
		}
	}

	/**
	 * @author Jeevitha
	 * @description : Verify The date Time format of Search group and batch
	 *              redcation History
	 * @param searchname
	 * @throws InterruptedException
	 */
	public void verifyDateTimeFormat(String searchname) throws InterruptedException {
		String completeTextOfSearchGp = getSearchHitCount(searchname).getText();

		driver.scrollingToBottomofAPage();
		String dataTimeOfSearchGP = completeTextOfSearchGp.substring(completeTextOfSearchGp.indexOf("(") + 1,
				completeTextOfSearchGp.indexOf(")"));
		System.out.println(dataTimeOfSearchGP);
		base.stepInfo(dataTimeOfSearchGP);

		base.waitForElement(getColorStatusMsg(searchname));
		String completeStatus = getColorStatusMsg(searchname).getText();
		String dateTimeOfBR = completeStatus.substring(completeStatus.indexOf("on") + 2, completeStatus.indexOf("by"));
		System.out.println(dateTimeOfBR);
		base.stepInfo(dateTimeOfBR);

		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss");
		dateFormat.setLenient(false);

		String record[][] = { { dataTimeOfSearchGP }, { dateTimeOfBR } };
		for (String[] data : record) {

			String dateTimeFormat = data[0];

			try {
				dateFormat.parse(dateTimeFormat.trim());
				base.passedStep(dateTimeFormat + " : Match The Expected Format");
			} catch (ParseException pe) {
				base.failedMessage(dateTimeFormat + " : DoesNot Match The Expected Format");
			}
		}
	}

	/**
	 * @author Jeevitha Description : click the cancel and rollback btn and verify
	 *         status
	 * @param ssName
	 * @param select
	 */
	public void verifyCancelAndRollBack(String ssName, boolean select) {
		driver.waitForPageToBeReady();
		base.waitForElement(getInProgressStatus(ssName));
		if (getInProgressStatus(ssName).isElementPresent()) {
			base.stepInfo("New Row Added And Status is : " + getInProgressStatus(ssName).getText());
		}
		String completeTextOfSearchGp = getSearchHitCount(ssName).getText();
		String searchStatus = completeTextOfSearchGp.substring(completeTextOfSearchGp.indexOf(")") + 1,
				completeTextOfSearchGp.indexOf("."));
		if (searchStatus.contains("Batch Redacting")) {
			System.out.println("search Status Format : " + searchStatus);
			base.passedStep("search Status Format : " + searchStatus);
		}

		base.waitForElement(getCancelAndRollBack(ssName));
		if (getCancelAndRollBack(ssName).isElementPresent()) {
			getCancelAndRollBack(ssName).waitAndClick(10);
		}
		String popUpMsg = getRollBackMsg().getText();
		String expectedMsg = "This will cancel the current Batch Redaction Job and undo any applied redactions.  do you wish to continue?";

		if (popUpMsg.contains(expectedMsg)) {
			base.passedStep(popUpMsg);
		}

		if (select) {
			getPopupNoBtn().waitAndClick(10);
			base.stepInfo("Clicked on Yes Button [RollBack PopUp]");
			base.VerifySuccessMessage("Cancellation request issued");

			if (getCancelAndRollBack(ssName).isDisplayed()) {
				base.failedMessage("User should is able to do further action on the same ");
			} else {
				base.passedStep(" User IS Not be able to do further action on the same");
			}

			driver.waitForPageToBeReady();
			String status1 = getRollBackStatus(ssName).getText();
			String expectedStatus1 = "Cancellation In Progress";
			if (status1.equalsIgnoreCase(expectedStatus1)) {
				softassert.assertEquals(status1, expectedStatus1);
				base.passedStep(status1);
			}

			base.waitForElement(getCancelledRollBackStatus(ssName));
			String status2 = getCancelledRollBackStatus(ssName).getText();
			String expectedStatus2 = "Redaction Cancelled.";
			if (status2.contains(expectedStatus2)) {
				base.passedStep(status2);
			}

		} else {
			getPopupYesBtn().waitAndClick(10);
			base.stepInfo("Clicked No Button [RollBackPopUp]");
		}
	}

	/**
	 * @author Jeevitha
	 * @param select
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String verifyBatchRedactionFileDownload2(boolean select) throws InterruptedException, IOException {
		// Download report
		driver.waitForPageToBeReady();
		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);
		base.waitForElement(getFileDownload());
		String popUpMsg = getFileDownload().getText();
		String expectedMsg = "DE: Your Batch Redaction analysis is complete. Please click here to download the report";
		softassert.assertEquals(popUpMsg, expectedMsg);
		getFileDownload().Click();
		getBullHornIcon().waitAndClick(2);
		base.stepInfo("File Downloaded");

		// file Downloaded Path
		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "\\";

		report = new ReportsPage(driver);
		File a = report.getLatestFilefromDir(testPath);
		System.out.println(a.getName());
		base.stepInfo(a.getName());

		String fileName = a.getName();

		fileName = testPath + fileName;
		System.out.println(fileName);
		base.stepInfo(fileName);

		if (select) {
			String[][] header = getHeaderText(fileName, 3);
			for (String[] actualValue : header) {
				if (actualValue[0] != null) {
					softassert.assertEquals(actualValue[0], "DocumentID");
					softassert.assertEquals(actualValue[1], "Text to Redact");
					softassert.assertEquals(actualValue[2], "Expected Redactions");
					softassert.assertEquals(actualValue[3], "Note");
					base.stepInfo(
							actualValue[0] + "  " + actualValue[1] + "  " + actualValue[2] + "  " + actualValue[3]);
					base.passedStep("Report Contains Expected Header");
				}
			}

		} else {
			String[][] record = base.readExcelData(fileName, 4);
			for (String[] data : record) {
				if (data[0] != null) {
					System.out.println(data[1] + "   " + data[2]);
					base.stepInfo(data[1] + "   " + data[2]);
				}
			}
		}
		return fileName;
	}

	/**
	 * @author Jeevitha
	 * @param filename
	 * @param rowNumToStart
	 * @return
	 * @throws IOException
	 */
	public static String[][] getHeaderText(String filename, int rowNumToStart) throws IOException {

		String[][] dataTable = null;
		File file = new File(filename);

		FileInputStream xlFile = new FileInputStream(file);
		Workbook xlwb = new XSSFWorkbook(xlFile);
		Sheet xlSheet = xlwb.getSheetAt(0);

		int numRows = xlSheet.getLastRowNum() + 1;
		System.out.println("no of Rows : " + numRows);
		int numCols = xlSheet.getRow(0).getLastCellNum();
		System.out.println("NO of columns : " + numCols);

		dataTable = new String[numRows][numCols];

		for (int i = rowNumToStart; i <= rowNumToStart; i++) {
			Row row = xlSheet.getRow(i);
			Row xlRows = xlSheet.getRow(i);
			int colss = row.getLastCellNum();
			for (int j = 0; j < colss; j++) {

				String xlcell = "";
				if (xlRows.getCell(j) != null) {
					xlcell = xlRows.getCell(j).toString();
				}
				dataTable[i][j] = xlcell.toString();
			}
		}
		return dataTable;
	}

	/**
	 * @author Jeevitha
	 * @param ssName
	 * @param docCount
	 * @param valueBeforeAnalysis
	 * @throws InterruptedException
	 */
	public void verifyBgTaskheaderAndCount(String ssName, String docCount, int valueBeforeAnalysis)
			throws InterruptedException {

		base.waitForElement(getBatchId(ssName));
		String fullId = getBatchId(ssName).getText();
		String[] ID = fullId.split("0");
		String actualId = null;
		for (String ext : ID) {
			actualId = ext;
		}
		System.out.println(actualId);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == valueBeforeAnalysis + 1;
			}
		}), Input.wait60);
		final int valueAfterAnalysis = base.initialBgCount();

		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);

		base.waitForElement(getViewAllBtn());
		getViewAllBtn().waitAndClick(20);

		// verify Background Task page
		driver.waitForPageToBeReady();
		String url = driver.getUrl();
		String expURL = Input.url + "Background/BackgroundTask";
		softassert.assertEquals(expURL, url);
		base.stepInfo("Navigated to My backgroud task page.");

		// Apply filter as Batch Redaction
		base.waitForElement(getStatusDD());
		getStatusDD().waitAndClick(30);
		base.waitForElement(getStatusDD_options());
		getStatusDD_options().waitAndClick(30);
		base.waitForElement(getApplyBtnBgPage());
		getApplyBtnBgPage().waitAndClick(30);
		base.stepInfo("Applied the filter as batch redaction");

		List<String> header = base.getAvailableListofElements(getBGTaskHeader());
		String expectedvalues[] = { "ID/NAME", "TASK TYPE", "SECURITY GROUP", "TARGET", "ACTUAL DOCS", "START DATE",
				"END DATE", "STATUS", "MESSAGE" };
		List<String> expectedList = new ArrayList<>();
		expectedList = Arrays.asList(expectedvalues);

		base.stepInfo(header.toString());

		if (header.equals(expectedList)) {
			System.out.println("Result : Default Values displayed in the Optimized Sort Order");
			base.passedStep("Result : Default Values displayed in the Optimized Sort Order");
		} else {
			System.out.println("Result : Default Values not displayed in the Optimized Sort Order");
			base.failedStep("Result : Default Values displayed in the Optimized Sort Order");
		}

		base.waitForElementCollection(getListFromBgTaskPage(actualId));
		if (getIDFromBGTaskPage(actualId).isElementPresent()) {
			List<String> listFromBgTable = base.getAvailableListofElements(getListFromBgTaskPage(actualId));
			int columnNum = base.getIndex(getBGTaskHeader(), "ACTUAL DOCS");
			base.stepInfo(listFromBgTable.toString());

			List<String> actualDocCount = base.getAvailableListofElements(getActualDocCount_BG(actualId, columnNum));

			int actualCount = actualDocCount.size();
			if (docCount.equals(actualDocCount)) {
				base.passedStep("Actual Count : " + actualDocCount + " is Same As "
						+ " Batch Redacted history Count i.e.., : " + docCount);
			} else if (actualCount > 1) {
				base.stepInfo(actualDocCount.toString() + " : Document Count IS Split ");
			} else {
				base.failedMessage("Document Count Mismatch");
			}

		} else {
			base.failedMessage(actualId + " is Not prsent In the Table");
		}
		softassert.assertAll();
	}

	/**
	 * @author Jeevitha
	 * @param searchName
	 * @param newNode
	 * @return
	 */
	public String verifyCompletedTimeOfSearchInBR(String searchName, String newNode) {
		driver.waitForPageToBeReady();
		if (getNodeAnalyseBtn(newNode).isDisplayed()) {
			getNewNode(newNode).waitAndClick(10);
			base.waitForElement(getSavedSearchNewGroupExpand());
			getSavedSearchNewGroupExpand().waitAndClick(5);
			base.stepInfo(newNode + " : is Present in BatchRedacion page");
		}
		if (getMySavedSearchTextbox(searchName).isDisplayed()) {
			base.stepInfo(searchName + " : is Present in BatchRedacion page");
		}
		String completeTextOfSearchGp = getSearchHitCount(searchName).getText();
		System.out.println("Time of search in BR tree : " + completeTextOfSearchGp);
		driver.scrollPageToTop();
		String[] time = completeTextOfSearchGp.replace(")", " ").split(" ");
		String Timestamp = time[7];
		base.stepInfo(" Time of search in BatchRedaction Tree : " + Timestamp);
		return Timestamp;
	}

	/**
	 * @AUthor Jeevitha
	 * @param searchname
	 * @param node
	 */
	public void verifyViewReportBtn(String searchname, String node) {
		if (getViewReportForSavedSearch(searchname).isElementAvailable(3)) {
			System.out.println("View Report & Apply Redactions button is Displayed for :" + searchname);
			base.stepInfo("View Report & Apply Redactions button is Displayed for :" + searchname);
		} else if (getNodeViewReportBtn(node).isElementAvailable(3)) {
			System.out.println("View Report & Apply Redactions button is Displayed for :" + node);
			base.stepInfo("View Report & Apply Redactions button is Displayed for :" + node);
		} else {
			base.failedMessage("View Report & Apply Redactions button is Not Displayed");
		}
	}

	/**
	 * @Author Jeevitha
	 */
	public void verifyPagination() {
		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		base.waitForElement(getMySavedSearchDropDown());
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();

		base.waitForElement(getRecordCount());
		String recordCount = getRecordCount().getText();
		String expectedText = "Showing 1 to 10 of";

		if (recordCount.contains(expectedText)) {
			base.stepInfo(recordCount + " : Is Displayed");
		}

		String[] count = recordCount.split(" ");
		int Count = Integer.parseInt(count[5]);
		System.out.println(Count);

		if (Count > 10) {
			base.waitForElement(getPageNumBar());
			if (getPageNumBar().isElementAvailable(Count)) {
				System.out.println(
						"Pagination is displayed for Batch Redaction History when records count is : " + Count);
				base.stepInfo("Pagination is displayed for Batch Redaction History when records count is : " + Count);
			} else {
				base.failedMessage(
						"Pagination is Not displayed for Batch Redaction History when records count is : " + Count);

			}
		}
	}

	/**
	 * @Author Jeevitha
	 */
	public void verifyPreviousAndNextBtn() {
		driver.scrollingToBottomofAPage();
		String lastNum;
		if (getLastPageNum().isElementAvailable(4)) {
			lastNum = getLastPageNum().getText();
		} else {
			int totalNum = getPageNumb().size();
			lastNum = String.valueOf(totalNum - 2);
		}
		System.out.println("LAst PAge No : " + lastNum);
		String activePage = getActivePage().getText();
		System.out.println("First Page No : " + activePage);
		if (checkPreviousBtnDisabled().isElementAvailable(2)) {
			base.stepInfo("Page No : " + activePage + " and Previous Button is Disabled");
		} else {
			base.stepInfo("Page No : " + activePage + " and Previous Button is Enabled");
		}

		if (checkNextBtnDisabled().isElementAvailable(2)) {
			base.stepInfo("Page No : " + activePage + " and Next Button is Disabled");
		} else {
			base.stepInfo("Page No : " + activePage + " and Next Button is Enabled");
		}
		int totalPage = Integer.parseInt(lastNum);
		for (int i = 2; i <= totalPage; i++) {
			getPageNum(i).waitAndClick(3);
			driver.waitForPageToBeReady();
			if (checkPreviousBtnDisabled().isElementAvailable(2)) {
				base.stepInfo("Page No : " + i + " and Previous Button is Disabled");
			} else {
				base.stepInfo("Page No : " + i + " and Previous Button is Enabled");
			}

			if (checkNextBtnDisabled().isElementAvailable(2)) {
				base.stepInfo("Page No : " + i + " and Next Button is Disabled");
			} else {
				base.stepInfo("Page No : " + i + " and Next Button is Enabled");
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @param searchname
	 * @param node
	 */
	public boolean verifyAnalyzeBtn(String searchname, String node) {
		boolean flag = false;
		if (getAnalyzeSearchForSavedSearchResult(searchname).isElementAvailable(3)) {
			System.out.println("Analyze Search for Redactions button is Displayed for :" + searchname);
			base.passedStep("Analyze Search for Redactions button is Displayed for :" + searchname);
			flag = true;
		} else if (getNodeAnalyseBtn(node).isElementAvailable(3)) {
			System.out.println("Analyze Group for Redactions button is Displayed for :" + node);
			base.passedStep("Analyze Group for Redactions button is Displayed for :" + node);
			flag = true;
		} else {
			base.stepInfo("Analyze Search/Group for Redactions button is Not Displayed");
			flag = false;
		}
		return flag;
	}

	/**
	 * @author Raghuram.A
	 * @param limit
	 * @throws InterruptedException
	 */
	public void verifyScrollBar(int limit) throws InterruptedException {
		Boolean checkedLargerLimit = false;
		int listSize = 0;
		loadBatchRedactionPage(Input.mySavedSearch);
		driver.waitForPageToBeReady();

		// getOptionDropdown(Input.mySavedSearch).waitAndClick(3);
		listSize = getListofDatas(Input.mySavedSearch).size();

		if (listSize >= limit) {
			System.out.println("Flow 1");
			int updatedlistSize = 0;
			boolean flag = batchRedactopnSrollCheck();
			softassert.assertTrue(flag);

			if (flag) {
				base.stepInfo("Initial List Size : " + listSize);
				base.passedStep("Scrolling displayed when list size is : " + listSize);
			} else {
				base.stepInfo("Initial List Size : " + listSize);
				base.failedMessage("No Scroll Bar available");
			}

			SavedSearch savesearch = new SavedSearch(driver);
			savesearch.deleteSearches(Input.mySavedSearch, listSize, limit);

			this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
			driver.waitForPageToBeReady();

			if (getOptionDropdown(Input.mySavedSearch).isElementAvailable(4)) {

				getOptionDropdown(Input.mySavedSearch).waitAndClick(3);
				updatedlistSize = getListofDatas(Input.mySavedSearch).size();
				System.out.println(updatedlistSize + "after deletion");
			}

			if (updatedlistSize < limit) {

				flag = batchRedactopnSrollCheck();
				softassert.assertFalse(flag);

				if (flag) {
					base.stepInfo(updatedlistSize + ": Updated List Size");
					base.failedMessage("Scrolling displayed when " + updatedlistSize + " are available");
				} else {
					base.stepInfo(updatedlistSize + ": updatedlistSize List Size");
					base.passedStep("No Scroll Bar available when list size is : " + updatedlistSize);
				}

			}

		}

		if (listSize <= limit) {
			System.out.println("Flow 2");
			int updatedlistSize = 0;
			boolean flag = batchRedactopnSrollCheck();
			softassert.assertFalse(flag);

			if (flag) {
				base.stepInfo("Initial List Size : " + listSize);
				base.failedMessage("Scrolling displayed when " + listSize + " are available");
			} else {
				base.stepInfo("Initial List Size : " + listSize);
				base.passedStep("No Scroll Bar available when list size is : " + listSize);
			}

			SessionSearch ss = new SessionSearch(driver);
			ss.basicContentSearch(Input.testData1);
			for (int i = listSize; i <= limit; i++) {
				String searchName = "SearchName_" + Utility.dynamicNameAppender();
				ss.saveSearch(searchName);
			}

			this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
			driver.waitForPageToBeReady();

			if (getOptionDropdown(Input.mySavedSearch).isElementAvailable(4)) {
				getOptionDropdown(Input.mySavedSearch).waitAndClick(3);
				updatedlistSize = getListofDatas(Input.mySavedSearch).size();
			}

			if (updatedlistSize > limit) {

				flag = batchRedactopnSrollCheck();
				softassert.assertTrue(flag);

				if (flag) {
					base.stepInfo(updatedlistSize + ": List Size");
					base.passedStep("Scrolling displayed when " + updatedlistSize + " are available");
				} else {
					base.stepInfo(updatedlistSize + ": List Size");
					base.failedMessage("No Scroll Bar available when list size is : " + updatedlistSize);
				}

			}

		}

		softassert.assertAll();
	}

	/**
	 * @author Raghuram.A
	 * @return
	 */
	public boolean batchRedactopnSrollCheck() {
		driver.waitForPageToBeReady();
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		boolean flag = (boolean) jse.executeScript("return document.querySelector('.tree-wrapper').scrollHeight>"
				+ "document.querySelector('.tree-wrapper').clientHeight;");
		System.out.println(flag);

		return flag;
	}

	/**
	 * @author Raghuram.A
	 * @return
	 */
	public void verifyRedactionConfirmationMessage(String action) {

		driver.waitForPageToBeReady();
		softassert.assertTrue(getPopupMessage().isDisplayed());
		if (action.equalsIgnoreCase("Yes")) {
			boolean flag = getPopupYesBtn().getText().equals("Ja");
			if (flag) {
				System.out.println("Redaction Confirmation message is Localized to German Language");
				base.stepInfo("Redaction Confirmation message is Localized to German Language");
				getPopupYesBtn().waitAndClick(5);
				base.stepInfo("Clicked Yes button");
			} else {
				getPopupYesBtn().waitAndClick(5);
				base.stepInfo("Clicked Yes button");
			}

			if (flag) {
				System.out.println("Success Message is Localized to' German Language'");
				base.stepInfo("Success Message is Localized to' German Language'");
				base.VerifySuccessMessageInGerman(
						"DE: Your request to batch redact has been added to the background. Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.");
			} else {
				base.VerifySuccessMessage(
						"Your request to batch redact has been added to the background. Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.");
			}
		} else if (action.equalsIgnoreCase("No")) {

			if (getPopupNoBtn().getText().equals("Nein")) {
				System.out.println("Redaction Confirmation message is Localized to German Language");
				base.stepInfo("Redaction Confirmation message is Localized to German Language");
				getPopupNoBtn().waitAndClick(5);
				base.stepInfo("Clicked No button");
			} else {
				getPopupNoBtn().waitAndClick(5);
				base.stepInfo("Clicked No button");
			}
		}

	}

	/**
	 * @author Raghuram.A
	 * @return
	 */
	public void VerifyBGMessageForBatchRedactionReportDownload(String ssName, int bgCount) throws InterruptedException {

		String batchId = getBatchId(ssName).getText();
		System.out.println(batchId.length());
		int index = 4;
		String batchID = batchId.substring(index);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == bgCount + 1;
			}
		}), Input.wait60);
		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);

		// checkAndValidateBgTask(valueBforeAnalysis, true);
		base.waitForElement(getFileDownloadwithSpecificID(batchID));
		String popUpMsg = getFileDownloadwithSpecificID(batchID).getText();
		String expectedMsg;
		if (popUpMsg.contains("DE:")) {
			System.out.println("Batch Readction Report Message in Background Tasks is Localized to German Language");
			base.stepInfo("Batch Readction Report Message in Background Tasks is Localized to German Language");
			expectedMsg = "DE: Your Batch Redaction report (ID " + batchID
					+ ") has been completed. Click here to download the report";
		} else {
			expectedMsg = "Your Batch Redaction report (ID " + batchID
					+ ") has been completed. Click here to download the report";
		}
		base.stepInfo(popUpMsg);
		softassert.assertEquals(popUpMsg, expectedMsg);
		getFileDownloadwithSpecificID(batchID).waitAndClick(20);
		getBullHornIcon().waitAndClick(20);
		softassert.assertAll();
	}

	/**
	 * @author Raghuram.A
	 * @return
	 */
	public String verifyBatchRedactionFileDownload_Dynamic() throws InterruptedException {

		// Download report
		driver.waitForPageToBeReady();
		base.stepInfo("File Downloaded");

		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "\\";

		// base.csvReader();
		report = new ReportsPage(driver);
		File a = report.getLatestFilefromDir(testPath);
		System.out.println(a.getName());
		base.stepInfo(a.getName());

		String fileName = a.getName();

		fileName = testPath + fileName;
		System.out.println(fileName);
		base.stepInfo(fileName);
		return fileName;
	}

	/**
	 * @author Jeevitha
	 */
	public void verifyInformativeErrorMessage() {
		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		driver.waitForPageToBeReady();
		base.waitForElement(getInformativeErrorMessageText());

		String sourceString = getInformativeErrorMessageText().getText();

		if (sourceString.contains("Für die")) {
			String compreString = "Für die Stapelredaktion sind mindestens ein Redaktions-Tag und eine Anmerkungsebene verfügbar";
			System.out.println("Informative/Error Message is Localized to German Language");
			base.stepInfo(sourceString + " : Localized to German Language ");
			base.textCompareEquals(sourceString, compreString, "Informative/Error Message is Verified successfully",
					"Informative/Error Message is not Verified");
		} else {
			String compreString = "Batch Redaction requires at least one Redaction Tag and one Annotation Layer available";
			base.stepInfo(sourceString + " : Localized to English Language ");
			base.textCompareEquals(sourceString, compreString, "Informative/Error Message is Verified successfully",
					"Informative/Error Message is not Verified");
		}

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verifies completed with error icon and tooltip message
	 * @param searchName : search query name
	 * @param expected   : expected tooltip Msg
	 * @param passMsg    : pass messsage
	 * @param failMsg    : fail message
	 */
	public void verifyCompletedErrorToolTip(String searchName, String expected, String passMsg, String failMsg) {
		String color = null;
		if (getCompletedToolTipIcon(searchName).isElementAvailable(10)) {
			color = "return window.getComputedStyle(document.querySelector('a.fa-question-circle-orange'),':before').getPropertyValue('color');";
			String colorCode = driver.findAttributeValueViaJS(color);
			System.out.println(colorCode);

			String splitTerm = colorCode.substring(colorCode.indexOf("(") + 1, colorCode.indexOf(")"));
			color = base.rgbTohexaConvertor_Optional(null, false, splitTerm);
			System.out.println(color);
		}

		if (color.equals(Input.completedWithErrColorCodeBR)) {

			getCompletedToolTipIcon(searchName).waitAndClick(5);
			System.out.println(" Icon is Yellow color " + color);
			base.passedStep(" Icon is Yellow color" + color);

			String toolTipMsg = getCompletedToolTipMSg(searchName).getText();
			base.compareTextViaContains(toolTipMsg, expected, passMsg, failMsg);
		} else {
			base.failedStep(" Colour Mismatch ");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param searchName - Searchname to perform Rollback
	 * @description : method to rollback
	 */
	public void rollBack(String searchName) {
		// Rollback Saved Search
		base.waitForElement(getRollbackbtn(searchName));
		getRollbackbtn(searchName).waitAndClick(10);

	}

	/**
	 * @author Raghuram.A
	 * @param condition - Yes / No
	 * @Description : method to Confirm rollback
	 */
	public void rollBackActionConfirmation(String condition) {
		if (condition.equalsIgnoreCase("Yes")) {
			base.waitForElement(getPopupYesBtn());
			String actual = getRollBackMsg().getText();
			String ExpectedPopUp = "Are you sure you wish to roll back this completed Batch Redaction?";
			if (actual.contains(ExpectedPopUp)) {
				base.passedStep(actual);
			}
			getPopupYesBtn().waitAndClick(3);
		}
	}

	/**
	 * @Author Jeevitha
	 * @param filePath
	 */
	public void verifyExpectedRedactionCount(String filePath) {
		String[][] record = base.readExcelData(filePath, 4);
		for (String[] data : record) {
			String Name = data[2];
			if (Name != null) {
				int count = (int) Float.parseFloat(Name);
				if (count > 0) {
					System.out.println("Expected Redaction : " + count);
					base.stepInfo("Expected Redaction : " + count);
				}
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : click manage & batch redact btn and navigate.
	 */
	public void navigateToBRPage() {
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(manageBtn(), "Manage Button");
		manageBtn().waitAndClick(10);
		base.stepInfo("CLicked Manage Btn");
		driver.waitForPageToBeReady();
		batchRedactionBtn().waitAndClick(10);
		base.stepInfo("CLicked batch redaction Btn & batchRedactio btn is Available");

		driver.waitForPageToBeReady();
		base.waitForElement(getSelectSearchHelpIcon());
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		if ((Input.url + "BatchRedaction/BatchRedaction").equals(currentUrl)) {
			base.passedStep("Navigated to Batch redaction Page");
		} else {
			base.failedStep("Navigation is not as expected");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : verifcation method for tabs & analyze btn present in select
	 *              serach group section
	 * @param tabName
	 */
	public void verifySelectSearchSection(String tabName) {
		if (getOptionDropdown(tabName).isElementAvailable(5)) {
			base.passedStep(tabName + " Tab is Displayed");
			String ddStatus = getDDStatus(tabName).GetAttribute("class");
			base.compareTextViaContains(ddStatus, "closed", tabName + " is in Collaped Mode",
					"Dropdown status is not as expected");

			if (getAnalyzeGroupForTab(tabName).isElementAvailable(5)) {
				base.passedStep("Analyze Group for Redactions button is Displayed for " + tabName);
			} else {
				base.failedStep("Analyze group button is displayed for " + tabName);
			}
		} else {
			base.failedStep(tabName + " is not displayed");
		}
	}

	/**
	 * @Author Jeevitha
	 * @param searchname
	 * @throws ParseException
	 */
	public void verifySearchTree(String searchname) throws ParseException {
		base.waitForElement(getViewReportForSavedSearch(searchname));
		String displayedText = getSearchHitCount(searchname).getText();
		String dataTimeOfSearchGP = displayedText.substring(displayedText.indexOf("(") + 1, displayedText.indexOf(")"));

		if (displayedText.contains("Hit Count") && displayedText.contains("Redactable Documents")
				&& !dataTimeOfSearchGP.equals("")) {
			base.stepInfo(displayedText);
			base.passedStep("Search Tree shows Hit Count {Date of Search} # Redactable Documents ");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Dsecription : verify redact btn is disabled
	 * @param searchname
	 * @param clickViewBtn
	 */
	public void verifyRedactBtnDisabled(String searchname, boolean clickViewBtn) {
		if (clickViewBtn) {
			if (getViewReportForSavedSearch(searchname).isElementAvailable(20)) {
				getViewReportForSavedSearch(searchname).waitAndClick(10);
			} else {
				loadBatchRedactionPage(Input.mySavedSearch);
				getViewReportForSavedSearch(searchname).waitAndClick(10);
			}
		}

		driver.waitForPageToBeReady();
		base.waitForElement(getPreRedactReportMessage2());
		String actualRedactDoc = getPreRedactReportMessage2().getText();
		String expectedRedactCount = "Redactable Docs: 0";
		if (actualRedactDoc.contains(expectedRedactCount)) {
			base.passedStep(actualRedactDoc);

			String btnStatus = getPopUpRedactButton().GetAttribute("disabled");
			base.textCompareEquals(btnStatus, "true", "Redact Button is Disabled", "Redact btn is not as expected");
		} else {
			base.failedStep("Redactable Docs: 0 is not Displayed");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify Allignment of enteries
	 */
	public void verifyAllignmentOfHistoryEnteries() {
		List<String> headerList = base.availableListofElements(batchRedactionHistoryHeader());
		for (int i = 1; i <= headerList.size(); i++) {
			String positionStatus = getHistoryEnteries(i).GetCssValue("vertical-align");
			String passMsg = " The Position of the values in  " + headerList.get(i - 1)
					+ " column is in center as expected";
			String failMsg = " History Enteries position is not as expected";

			base.textCompareEquals(positionStatus, "middle", passMsg, failMsg);
		}
	}
}

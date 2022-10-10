package pageFactory;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.Seconds;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.google.common.io.Files;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import de.redsix.pdfcompare.PdfComparator;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class BaseClass {

	Driver driver;
	public static String tooltipmsg;
	SoftAssert softAssertion;
	ProductionPage page;
	StringWriter sw;
	PrintWriter pw;

	public Element getBGnotificationCount1() {
		return driver.FindElementByXPath("//b[@class='badge']");
	}

	public Element getBGnotificationCount2() {
		return driver.FindElementByXPath("//b[@class='badge bg-color-red bounceIn animated']");
	}

	public Element getSignoutMenu() {
		return driver.FindElementByXPath("//*[@id='user-selector']");
	}

	public Element getChangeRole() {
		return driver.FindElementByXPath("//*[@id='utility-group']//a[text()='Change Role']");
	}

	public Element getSelectRole() {
		return driver.FindElementByXPath("//select[@name='Role']");
	}

	public Element getSelectDomain() {
		return driver.FindElementById("ddlAvailableDomains");
	}

	public Element getSelectProjectTo() {
		return driver.FindElementById("ddlAvailableProjects");
	}

	public Element getSelectSecurityGroup() {
		return driver.FindElementByXPath("//select[@name='SecurityGroupID']");
	}

	// ChangeRole locator Modified by Baskar
	public Element getSaveChangeRole() {
		return driver.FindElementByXPath("//*[@id='ChangeRolePopup']//input[@type='submit']");
	}

	public Element getContinueBtn() {
		return driver.FindElementByXPath("//button[contains(.,'Continue')]");
	}

	// select project
	public Element getProjectNames() {
		return driver.FindElementById("project-selector");
	}

	public Element getSelectProject() {
		return driver.FindElementByXPath("//a[@title='" + Input.projectName + "']");
	}

	public Element getPopupYesBtn() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}

	// background task
	public Element getBackgroundTask_Button() {
		return driver.FindElementByXPath("//*[@id='activity']/i");
	}

	public Element getBckTask_SelectAll() {
		return driver.FindElementById("btnViewAll");
	}

	public Element getBackgroundTask_Message(String texttosearch) {
		return driver.FindElementByXPath("(//span[contains(text(),'" + texttosearch + "')])[1]");
	}

	// success message
	public Element getCloseSucessmsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//i");
	}

	public ElementCollection getElements() {
		return driver.FindElementsByXPath("//*[@class='a-menu']");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	public Element getYesBtn() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	// select domain
	public Element getSelectdomaindropdown(String domainname) {
		return driver.FindElementByXPath("//a[@title='" + domainname + "']");
	}

	// select security group
	public Element getsgNames() {
		return driver.FindElementById("SecurityGroup-selector");
	}

	public Element getSelectsg(String sgname) {
		return driver.FindElementByXPath("//a[@title='" + sgname + "']");
	}

	public Element getCancelbutton() {
		return driver.FindElementByXPath("//button[contains(text(),'Cancel')]");
	}

	public Element getNOBtn() {
		return driver.FindElementByXPath("//button[@id='bot2-Msg1']");
	}

	public Element getAvlDomain() {
		return driver.FindElementById("ddlAvailableDomains");
	}

	public Element getAvlProject() {
		return driver.FindElementById("ddlAvailableProjects");
	}

	// Added by Raghuram A
	public Element getSelectProject(String projectName) {
		return driver.FindElementByXPath("//a[@title='" + projectName + "']");
	}

	public ElementCollection getAvailableProjectList() {
		return driver.FindElementsByXPath("//ul[@id='ddlProject11']//li//a");
	}

	public Element SelectSearchOption() {
		return driver.FindElementById("txtsearchUser");
	}

	public Element getEditButton(String ProjectName) {
		return driver.FindElementByXPath("//td[text()='" + ProjectName + "']//following-sibling::td//a[text()='Edit']");
	}

	public Element getFunctionalityButton() {
		return driver.FindElementByXPath("//a[contains(text(),'Functionality')] ");
	}

	public Element UnSelectProductionCheckBox() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanProductions']//following-sibling::i");
	}

	public Element getSaveBtn() {
		return driver.FindElementById("btnsubmit");
	}

	public Element selectSecurityGroup() {
		return driver.FindElementByXPath("//select[@id='ddlSg']");
	}

	public Element selectDefaultSecurityGroup() {
		return driver.FindElementByXPath("//select[@id='ddlSg']");
	}

	// Added by Jeevitha
	public Element getWarningMsgHeader() {
		return driver.FindElementByXPath("//span[text()='Warning !']");
	}

	public Element getSecondLineOfWarningMsg() {
		return driver.FindElementByXPath("//span[text()='Warning !']/parent::div//li");
	}

	// Added by Krishna
	public Element getSecondLineSuccessMsg(int i) {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//li[" + i + "]");

	}

	// added by Aathith
	public Element text(String text) {
		return driver.FindElementByXPath("//*[contains(text(),'" + text + "')]");
	}

	public Element getGlobalMessagePopUpClose() {
		return driver.FindElementById("btnDialogClose");
	}

	// added by iyappan
	public Element getWarningsMsgHeader() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//span[text()='Warning !']");
	}

	public Element getErrorMsgHeader() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//span[text()='Error !']");
	}

	public Element getWarningMsg() {
		return driver.FindElementByXPath("//span[text()='Warning !']/parent::div/p");
	}

	public Element getSelectSecurityGroupBulk() {
		return driver.FindElementByXPath("(//select[@name='SecurityGroupID'])[last()]");
	}

	// add by Aathith
	public Element textValue(String text) {
		return driver.FindElementByXPath("//*[text()=" + text + "]");
	}

	public Element getBackGroudTaskSuccesMark() {
		return driver.FindElementByXPath("//i[@class='fa fa-check fa-fw fa-2x']");
	}

	public Element getBackGroudTaskIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getFirstBackRoundTask() {
		return driver.FindElementByXPath("(//span[@class='padding-10']/a)[1]");
	}

	public Element getBullHornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getRedBullHornIcon() {
		return driver.FindElementByXPath("//span[@class='activity-dropdown newRed']");
	}

	public Element getBullIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']/following-sibling::b");
	}

	public ElementCollection getAvailableDomainList() {
		return driver.FindElementsByXPath("//ul[@id='ddlDomains']//li//a");
	}

	public Element getPageTitle() {
		return driver.FindElementByXPath("//h1[@class='page-title']");
	}

	public Element getSelectRole(String role) {
		return driver.FindElementByXPath("//select[@name='Role']/option[text()='" + role + "']");
	}

	public Element getSelectProjectTo(String project) {
		return driver.FindElementByXPath("//select[@id='ddlAvailableProjects']/option[text()='" + project + "']");
	}

	// add by Aathith
	public Element getSelectDomain(String Domain) {
		return driver.FindElementByXPath("//select[@id='ddlAvailableDomains']/option[text()='" + Domain + "']");
	}

	public Element getLoginedUserRole() {
		return driver.FindElementByXPath("//span[@class='badge bg-color-greenLight']");
	}

	public BaseClass(Driver driver) {

		this.driver = driver;
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);
		softAssertion = new SoftAssert();
		sw = new StringWriter();
		pw = new PrintWriter(sw);
	}

	// Indium-stabilization
	public int initialBgCount() {
		int bgCount = 0;
		if (getBGnotificationCount1().isElementAvailable(5)) {
			bgCount = Integer.parseInt(getBGnotificationCount1().getText());
		} else {
			bgCount = Integer.parseInt(getBGnotificationCount2().getText());
		}

		// System.out.println(bgCount+" - BGcount");

		return bgCount;
	}

	public void impersonatePAtoRMU() throws InterruptedException {
		waitForElement(getSignoutMenu());
		waitTillElemetToBeClickable(getSignoutMenu());
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait60);
		getChangeRole().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		Thread.sleep(3000);
		getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		Thread.sleep(3000);
		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
		getSaveChangeRole().waitAndClick(10);
		this.stepInfo("Impersnated from PA to RMU");
		UtilityLog.info("Impersnated from PA to RMU");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

//  Modified by baskar
	public void impersonateRMUtoReviewer() throws InterruptedException {
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait90);
		getChangeRole().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		Thread.sleep(3000);
		getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		Thread.sleep(3000);
		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from RMU to Reviewer");
		UtilityLog.info("Impersnated from RMU to Reviewer");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

//  Modified by baskar
	public void impersonateRMUtoReviewers() throws InterruptedException {
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait90);
		getChangeRole().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText("MasterAutomationData");
		Thread.sleep(3000);
		getAvlProject().selectFromDropdown().selectByVisibleText("Regression_AllDataset_Consilio");
		Thread.sleep(3000);
		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from RMU to Reviewer");
		UtilityLog.info("Impersnated from RMU to Reviewer");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void impersonateSAtoDA(String domain) {
		getSignoutMenu().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait30);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait30);
		getSelectRole().selectFromDropdown().selectByVisibleText("Domain Administrator");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDomain().Visible();
			}
		}), Input.wait30);
		if (domain.equalsIgnoreCase("Any"))
			getSelectDomain().selectFromDropdown().selectByIndex(1);
		else
			getSelectDomain().selectFromDropdown().selectByVisibleText(domain);
		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from RMU to Reviewer");
		UtilityLog.info("Impersnated from RMU to Reviewer");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void impersonateSAtoPA() throws InterruptedException {
		getSignoutMenu().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait30);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait30);
		getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

		Thread.sleep(3000);

		getSelectProjectTo().selectFromDropdown().selectByVisibleText(Input.projectName);

		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from SA to PA");
		UtilityLog.info("Impersnated from SA to PA");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void selectproject() {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProjectNames().Visible();
			}
		}), 10000);
		driver.scrollPageToTop();
		// Select project if required one is not seletced
		getProjectNames().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectProject().Visible();
			}
		}), 10000);
		getSelectProject().waitAndClick(10);
		driver.waitForPageToBeReady();
		UtilityLog.info("Project is successfully selected");

	}

	/**
	 * Method is to select the project after logging in to application
	 * 
	 * @author iyyapan
	 * @param projectName Name of the project which needs to be selected
	 */
	public void selectproject(String projectName) {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProjectNames().Visible();
			}
		}), Input.wait3);
		driver.scrollPageToTop();
		// Select project if required one is not seletced
		getProjectNames().waitAndClick(3);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectProject(projectName).Visible();
			}
		}), Input.wait3);
		getSelectProject(projectName).waitAndClick(3);
		driver.waitForPageToBeReady();

	}

	public void BckTaskMessageverify(String texttosearch) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBackgroundTask_Button().Visible();
			}
		}), Input.wait30);
		getBackgroundTask_Button().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBackgroundTask_Message(texttosearch).Visible();
			}
		}), Input.wait60);
		String exptext = getBackgroundTask_Message(texttosearch).getText().toString();
		System.out.println(exptext);
		UtilityLog.info(exptext);
		Assert.assertTrue(exptext.contains(texttosearch));
		UtilityLog.info("Message verified");
	}

	public void CloseSuccessMsgpopup() {
		waitForElement(getCloseSucessmsg());
		try {
			getCloseSucessmsg().waitAndClick(5);
			UtilityLog.info("Closed Success message popup");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void yesPopUp() {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPopupYesBtn().Visible();
				}
			}), Input.wait30);
			getPopupYesBtn().Click();
			UtilityLog.info("Accepted the popup");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void VerifySuccessMessage(String ExpectedMsg) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSuccessMsgHeader().Visible();
			}
		}), Input.wait60);
		Assert.assertEquals("Success !", getSuccessMsgHeader().getText().toString());
		Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
		UtilityLog.info("Expected message - " + ExpectedMsg);
		Reporter.log("Expected message - " + ExpectedMsg, true);
	}

	public boolean VerifySuccessMessageB(String ExpectedMsg) {
		boolean release = false;
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSuccessMsgHeader().Visible();
				}
			}), Input.wait30);
			Assert.assertEquals("Success !", getSuccessMsgHeader().getText().toString());
			Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
			UtilityLog.info("Expected message - " + ExpectedMsg);
			Reporter.log("Expected message - " + ExpectedMsg, true);

			release = true;

		} finally {
			return release;
		}

	}

	public void VerifyWarningMessage(String ExpectedMsg) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWarningsMsgHeader().Visible();
			}
		}), Input.wait30);
		Assert.assertEquals("Warning !", getWarningsMsgHeader().getText().toString());
		Assert.assertEquals(ExpectedMsg, getWarningMsg().getText().toString());
		UtilityLog.info("Expected message - " + ExpectedMsg);
		Reporter.log("Expected message - " + ExpectedMsg, true);
	}

	public void VerifyErrorMessage(String ExpectedMsg) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSuccessMsgHeader().Visible();
			}
		}), Input.wait30);
		Assert.assertEquals("Error !", getSuccessMsgHeader().getText().toString());
		Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
		UtilityLog.info("Expected message - " + ExpectedMsg);
		Reporter.log("Expected message - " + ExpectedMsg, true);
	}

	public void SelectCurrentdatfromDatePicker(Element DateFrom, Element dateWidget) {

		// Get Today's number
		String today = getCurrentDay();
		System.out.println("Today's number: " + today + "\n");

		// Click and open the datepickers
		DateFrom.waitAndClick(10);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// This is from date picker table
		WebElement dateWidgetFrom = dateWidget.getWebElement();

		// This are the rows of the from date picker table
		// List<WebElement> rows =
		// dateWidgetFrom.findElements(By.tagName("tr"));

		// This are the columns of the from date picker table
		List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));

		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		for (WebElement cell : columns) {
			/*
			 * //If you want to click 18th Date if (cell.getText().equals("18")) {
			 */
			// Select Today's Date
			if (cell.getText().equals(today)) {
				cell.click();
				break;
			}
		}
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Get The Current Day
	private String getCurrentDay() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("Today Int: " + todayInt + "\n");
		UtilityLog.info("Today Int: " + todayInt + "\n");

		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		System.out.println("Today Str: " + todayStr + "\n");
		UtilityLog.info("Today Str: " + todayStr + "\n");

		return todayStr;
	}

	public void impersonateDAtoPA() {
		getSignoutMenu().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait30);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait30);
		getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectProjectTo().Visible();
			}
		}), Input.wait30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

		getSelectProjectTo().selectFromDropdown().selectByVisibleText(Input.projectName);
		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from DA to PA");
		UtilityLog.info("Impersnated from DA to PA");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void selectdomain(final String domain) {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProjectNames().Visible();
			}
		}), 10000);
		driver.scrollPageToTop();
		getProjectNames().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectdomaindropdown(domain).Visible();
			}
		}), 10000);
		getSelectdomaindropdown(domain).waitAndClick(5);
		driver.waitForPageToBeReady();
		UtilityLog.info("Domain selected: " + domain);
	}

	public void GetandVerifyTooltip(Element element, String TextToValidate) {

		Actions builder = new Actions(driver.getWebDriver());
		builder.clickAndHold(element.getWebElement()).perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tooltipmsg = element.GetAttribute("title");
		System.out.println("Tooltip/ Help message is " + tooltipmsg);
		UtilityLog.info("Expected Tooltip/ Help message is " + TextToValidate);
		UtilityLog.info("Actual Tooltip/ Help message is " + tooltipmsg);
		softAssertion.assertEquals(TextToValidate, tooltipmsg);
		softAssertion.assertAll();
	}

	public void CompareListStrings(List<WebElement> list1, List<WebElement> list2) {

		List<String> a1 = new ArrayList<String>();
		List<String> a2 = new ArrayList<String>();

		for (int i = 1; i <= a1.size(); i++) {
			a1.add(list1.get(i).getText());
			a2.add(list2.get(i).getText());
			a1.addAll(a1);
			a2.addAll(a2);
			if (a1.equals(a2)) {
				System.out.println("Pass");
				UtilityLog.info("Strings compared and matched");
			} else {
				System.out.println("FAIL");
				UtilityLog.info("Strings compared but found not matched");
			}
		}
	}

	public void selectsecuritygroup(final String sgname) {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getsgNames().Visible();
			}
		}), 10000);
		driver.scrollPageToTop();
		getsgNames().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectsg(sgname).Visible();
			}
		}), 10000);
		getSelectsg(sgname).waitAndClick(5);
		driver.waitForPageToBeReady();
		UtilityLog.info("Security Group Selected: " + sgname);
	}

	public String getcurrentdateinGMT() throws ParseException {
		// Time in GMT
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		System.out.println(dateFormatLocal.parse(dateFormatGmt.format(new Date())));
		UtilityLog.info(dateFormatLocal.parse(dateFormatGmt.format(new Date())));

		String Time = dateFormatGmt.format(new Date()).toString();
		System.out.println(Time);
		UtilityLog.info(Time);
		return Time;

	}

	public void impersonatePAtoRMU_SelectedSG(String sgname) {
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait60);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);

		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(sgname);
		getSaveChangeRole().waitAndClick(10);
		System.out.println("Impersnated from PA to RMU");
		UtilityLog.info("Impersnated from PA to RMU");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public void impersonateSAtoRMU() throws InterruptedException {
		waitForElement(getSignoutMenu());
		getSignoutMenu().waitAndClick(10);
		waitForElement(getChangeRole());
		getChangeRole().waitAndClick(5);
		waitForElement(getSelectRole());
		getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
		waitTime(1);
		waitForElement(getAvlDomain());
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		waitForElement(getAvlProject());
		getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		waitForElement(getSelectSecurityGroup());
		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
		waitForElement(getSaveChangeRole());
		getSaveChangeRole().waitAndClick(3);
		System.out.println("Impersonated from SA to RMU");
		UtilityLog.info("Impersonated from SA to RMU");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void comparearraywithlist(String[] listarray, ElementCollection elelist) {

		List<WebElement> values = elelist.FindWebElements();
		List<String> value = new ArrayList<String>();
		for (int j = 1; j <= values.size(); j++) {
			System.out.println(value.add(values.get(j).getText()));
			UtilityLog.info(value.add(values.get(j).getText()));
		}
		UtilityLog.info(listarray);
		UtilityLog.info(values);
		Assert.assertSame(listarray, values);
	}

	public void getallselectele(Select ele) {
		List<WebElement> dd = ele.getOptions();

		System.out.println(dd.size());

		for (int j = 0; j < dd.size(); j++) {
			System.out.println(dd.get(j).getText());
			UtilityLog.info(dd.get(j).getText());

		}
	}

	public boolean isFileDownloaded(String downloadPath, int count) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();
		System.out.println(dir_contents.length);
		UtilityLog.info(dir_contents.length);

		if (dir_contents.length == count)
			return flag = true;

		else
			return flag;
	}

	public void copyfiles() throws Exception {
		// Path src2 =
		// Paths.get("\\\\MTPVTSSLMQ01\\Productions\\Automation\\P657944\\VOL0001\\PDF\\0001\\A_7786590000000100_P128632.pdf");
		page = new ProductionPage(driver);
		Path srcpath1 = Paths.get(Input.MasterPDF1location);
		Path srcpath2 = Paths.get(Input.MasterPDF2location);
		Path actsrcall = Paths.get(page.ProdPathallredact);
		Path actsrcsome = Paths.get(page.ProdPathsomeredact);
		Path dest1 = Paths.get(System.getProperty("user.dir") + "//Misc//Production Files//MasterPDFAllRedact.pdf");
		Path dest2 = Paths.get(System.getProperty("user.dir") + "//Misc//Production Files//MasterPDF2Redact.pdf");
		Path actdestall = Paths.get(System.getProperty("user.dir") + "//Misc//Production Files//actsrcall.pdf");
		Path actdestsome = Paths.get(System.getProperty("user.dir") + "//Misc//Production Files//actsrcsome.pdf");

		// delete last TCs PDF from PDFs folder
		File file = new File("C:\\Users\\smangal\\Documents\\Production Files");
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}

		Files.copy(srcpath1.toFile(), dest1.toFile());
		Files.copy(srcpath2.toFile(), dest2.toFile());
		Files.copy(actsrcall.toFile(), actdestall.toFile());
		Files.copy(actsrcsome.toFile(), actdestsome.toFile());
		System.out.println("Copied file into another location successfully");
		UtilityLog.info("Copied file into another location successfully");

	}

	public void TestPDFCompare() throws Exception {
		boolean isEquals = false;
		copyfiles();
		String result1 = System.getProperty("user.dir") + "//Misc//Production Files//resultallredact.pdf";
		String result2 = System.getProperty("user.dir") + "//Misc//Production Files//result2redact.pdf";
		String file1 = System.getProperty("user.dir") + "//Misc//Production Files//MasterPDFAllRedact.pdf";
		String file2 = System.getProperty("user.dir") + "//Misc//Production Files//MasterPDF2Redact.pdf";
		String actualfileall = System.getProperty("user.dir") + "//Misc//Production Files//actsrcall.pdf";
		String actualfilesome = System.getProperty("user.dir") + "//Misc//Production Files//actsrcsome.pdf";

		new PdfComparator(file1, actualfileall).compare().writeTo(result1);
		new PdfComparator(file2, actualfilesome).compare().writeTo(result2);

		System.out.println("process completed");
		isEquals = new PdfComparator(file1, file2).compare().writeTo(result1);
		System.out.println("Are PDF files similar..." + isEquals);
		isEquals = new PdfComparator(file1, file2).compare().writeTo(result2);
		System.out.println("Are PDF files similar..." + isEquals);

	}

	// Code added by Narendra
	public void impersonateSAtoReviewertoSA() throws InterruptedException {
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait60);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

		Thread.sleep(3000);

		getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		Thread.sleep(2000);

		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from SA to Reviewer");
		UtilityLog.info("Impersnated from SA to Reviewer");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait60);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("System Administrator");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveChangeRole().Visible();
			}
		}), Input.wait60);

		getSaveChangeRole().waitAndClick(10);

		System.out.println("Impersnated back Reviewer to SA");
		UtilityLog.info("Impersnated back Reviewer to SA");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void impersonateSAtoPAtoSA() throws InterruptedException {
		getSignoutMenu().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait30);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait30);
		getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

		Thread.sleep(3000);

		getSelectProjectTo().selectFromDropdown().selectByVisibleText(Input.projectName);

		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from SA to PA");
		UtilityLog.info("Impersnated from SA to PA");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait60);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("System Administrator");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveChangeRole().Visible();
			}
		}), Input.wait60);

		getSaveChangeRole().waitAndClick(10);

		System.out.println("Impersnated back PA to SA");
		UtilityLog.info("Impersnated back PA to SA");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * Method is to print the test step information in report in blue color
	 * 
	 * @author Sowndariya
	 * @param message Message which needs to be printed in report
	 */
	public void stepInfo(String message) {
		Reporter.log("<font color='blue'>" + message + "</font>");
		UtilityLog.info("Step info: " + message);
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void stepInfo_DataBase(String message) {
		Reporter.log("<font color='orange'>" + message + "</font>");
		UtilityLog.info("Step info: " + message);
	}

	/**
	 * Method is to print the test step pass information in report in green color
	 * 
	 * @author Sowndariya
	 * @param message Message which needs to be printed in report
	 */
	public void passedStep(String message) {
		Reporter.log("<font color='green'>" + message + "</font>");
		UtilityLog.info("Passed step: " + message);

	}

	/**
	 * Method is to print the test step fail information in report in blue color
	 * 
	 * @author Sowndariya
	 * @param message Message which needs to be printed in report
	 */
	public void failedStep(String message) {
		Reporter.log("<font color='red'>" + message + "</font>");
		UtilityLog.info("Failed step: " + message);
		softAssertion.assertTrue(false);
		softAssertion.assertAll();
	}

	/**
	 * Method is to wait for the element until it is displayed,enabled and it is
	 * present in DOM
	 * 
	 * @author Sowndariya
	 * @param element Element locator for which needs to be waited
	 */
	public void waitForElement(Element element) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return element.Displayed() && element.Enabled() && element.Present();
				}
			}), Input.wait90);
		} catch (Exception e) {
			UtilityLog.info("Got exception while waiting for element");
			e.printStackTrace();
		}
	}

	/**
	 * Method is to wait for the element until it is clickable
	 * 
	 * @author Sowndariya
	 * @param element Element locator for which needs to be waited
	 */
	public boolean waitTillElemetToBeClickable(Element element) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 30);
			wait.until(ExpectedConditions.elementToBeClickable(element.getWebElement()));
			status = true;
		} catch (Exception e) {
			UtilityLog.info("Exception occured whilr waiting for element is clickable");
		}
		return status;
	}

	/**
	 * @Author Indium-Sowndariya
	 */
	public void hitTabKey(int nooftimes) {
		try {
			Actions actions = new Actions(driver.getWebDriver());
			for (int i = 1; i <= nooftimes; i++) {
				actions.sendKeys(Keys.TAB).build().perform();
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @Author Indium-Sowndariya
	 */
	public void hitEnterKey(int nooftimes) {
		try {
			Actions actions = new Actions(driver.getWebDriver());
			for (int i = 1; i <= nooftimes; i++) {
				actions.sendKeys(Keys.ENTER).build().perform();
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @description : This method is used for drag and drop an element.
	 */
	public void dragAndDrop(Element source, Element destination) {
		try {
			Actions action = new Actions(driver.getWebDriver());
			action.dragAndDrop(source.getWebElement(), destination.getWebElement()).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			failedStep("Exception occcured while clicking on project field link" + e.getMessage());
		}

	}

	/**
	 * @author Indium-Baskar
	 */

	public void waitForElementCollection(ElementCollection elementCollection) {
		try {
			Thread.sleep(1000);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return elementCollection.Displayed() && elementCollection.Visible();
				}
			}), Input.wait30);
		} catch (Exception e) {
			UtilityLog.info("Got exception while waiting for element");
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Jayanthi
	 */

	public List<String> getAvailableListofElements(ElementCollection element) {
		try {
			List<String> elementNames = new ArrayList<>();
			List<WebElement> elementList = null;
			elementList = element.FindWebElements();
			for (WebElement webElementNames : elementList) {
				String elementName = webElementNames.getText();
				elementNames.add(elementName);
			}
			return elementNames;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}

	}

	/**
	 * @author Indium-Baskar
	 */
//	Method used for checking the clickable condition and click the button
	public void clickButton(Element element) {

		try {
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			wait.until(ExpectedConditions.elementToBeClickable(element.getWebElement())).click();
		} catch (Exception e) {
			e.printStackTrace();
			UtilityLog.info("Exception occured while waiting for element is not clickable");
		}

	}

	/**
	 * @author Indium-Jayanthi
	 */

	public void dragAndDropSpecialElement(Element elementToBeDragged, Element elementToBeDropped) {
		try {
			waitForElement(elementToBeDragged);
			elementToBeDragged.waitAndClick(5);
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 30);
			wait.until(ExpectedConditions.elementToBeClickable(elementToBeDragged.getWebElement()));
			Actions actions = new Actions(driver.getWebDriver());
			waitForElement(elementToBeDragged);
			actions.clickAndHold((elementToBeDragged).getWebElement());
			actions.moveToElement((elementToBeDropped).getWebElement());
			actions.release(elementToBeDropped.getWebElement());
			actions.build().perform();
			UtilityLog.info("Drag and drop action performed");
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Raghuram A Created on : 9/07/21 Modified on : N/A Modified by : N/A
	 *         Description : Picking file from the local system
	 * 
	 * @return : file
	 */
	public FileReader fileLocate(String filePath, String fileName) {
		FileReader file = null;
		String fileToPick = filePath + fileName;
		try {
			file = new FileReader(fileToPick);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * @author Jeevitha Description : Impersonate Reviewer to RMU
	 */
	public void impersonateReviewertoRMU() {
		try {
			getSignoutMenu().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getChangeRole().Visible();
				}
			}), Input.wait60);
			getChangeRole().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectRole().Visible();
				}
			}), Input.wait60);
			getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");

			Thread.sleep(1000);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAvlDomain().Visible();
				}
			}), Input.wait30);
			getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

			Thread.sleep(1000);

			getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
			Thread.sleep(1000);

			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			getSaveChangeRole().waitAndClick(5);
			System.out.println("Impersnated from Reviewer to RMU");

			UtilityLog.info("Impersnated from Reviewer to RMU");

			if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
				try {
					getGlobalMessagePopUpClose().waitAndClick(5);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Indium-Brundha
	 */
	public void impersonationReviewertoRMU() {
		try {
			Thread.sleep(3000);
			// getChangeRole().waitAndFind(10);
			getSignoutMenu().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getChangeRole().Visible();
				}
			}), Input.wait60);
			getChangeRole().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectRole().Visible();
				}
			}), Input.wait60);
			getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");

			Thread.sleep(1000);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAvlDomain().Visible();
				}
			}), Input.wait30);
			getAvlDomain().selectFromDropdown().selectByVisibleText("MasterAutomationData");

			Thread.sleep(1000);

			getAvlProject().selectFromDropdown().selectByVisibleText("Regression_AllDataset_Consilio");
			Thread.sleep(1000);

			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			getSaveChangeRole().waitAndClick(5);
			System.out.println("Impersnated from Reviewer to RMU");

			UtilityLog.info("Impersnated from Reviewer to RMU");
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Indium Raghuram Description : rgb to hexa code convertor Date:9/09/21
	 *         Modified date: N/A Modified by: N/A
	 */
	public String rgbTohexaConvertor(String bgColor) {
		try {
			String s1 = bgColor.substring(4);
			bgColor = s1.replace(')', ' ');
			bgColor = s1.replace('(', ' ');
			StringTokenizer st = new StringTokenizer(bgColor);
			int r = Integer.parseInt(st.nextToken(",").trim());
			int g = Integer.parseInt(st.nextToken(",").trim());
			int b = Integer.parseInt(st.nextToken(",").trim());
			Color c = new Color(r, g, b);
			String hex = "#" + Integer.toHexString(c.getRGB()).substring(2).toUpperCase();
			return hex;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}
	}

	/**
	 * @author Jeevitha
	 * @param ExpectedMsg
	 */
	public void VerifySuccessMessageInGerman(String ExpectedMsg) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSuccessMsgHeader().Visible();
				}
			}), Input.wait60);
			Assert.assertEquals("Erfolg !", getSuccessMsgHeader().getText().toString());
			Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
			UtilityLog.info("Expected message - " + ExpectedMsg);
			Reporter.log("Expected message - " + ExpectedMsg, true);
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Jeevitha
	 * @param nooftimes
	 */
	public void hitDownKey(int nooftimes) {
		try {
			Actions actions = new Actions(driver.getWebDriver());
			for (int i = 1; i <= nooftimes; i++) {
				actions.sendKeys(Keys.DOWN).build().perform();
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Indium Raghuram ] Description : Method to collect the list of
	 *         elements and returns as list with text (GenericMethod) Date:8/15/21
	 *         Modified date: N/A Modified by: N/A
	 */
	public List<String> availableListofElements(ElementCollection element) {
		try {
			List<String> elementNames = new ArrayList<>();
			List<WebElement> elementList = null;
			elementList = element.FindWebElements();
			for (WebElement wenElementNames : elementList) {
				String elementName = wenElementNames.getText();
				elementNames.add(elementName);
			}
			return elementNames;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */
	public void impersonatePAtoReviewer() {
		try {
			driver.waitForPageToBeReady();
			waitForElement(getSignoutMenu());
			getSignoutMenu().waitAndClick(10);
			waitForElement(getChangeRole());
			getChangeRole().waitAndClick(5);
			waitForElement(getSelectRole());
			getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
			waitForElement(getAvlDomain());
			Thread.sleep(3000);
			getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
			waitForElement(getAvlProject());
			Thread.sleep(3000);
			getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
			waitForElement(getSelectSecurityGroup());
			Thread.sleep(3000);
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			waitForElement(getSaveChangeRole());
			getSaveChangeRole().waitAndClick(10);
			System.out.println("Impersnated from PA to RMU");
			UtilityLog.info("Impersnated from PA to RMU");

			if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
				try {
					getGlobalMessagePopUpClose().waitAndClick(5);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */
	public boolean waitForElementToBeGone(Element element, int timeout) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), timeout);
			status = wait.until(ExpectedConditions.invisibilityOf(element.getWebElement()));
		} catch (Exception e) {
			UtilityLog.info("Exception occured while waiting for element invisibilty");
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @Author Raghuram A
	 */

	public void failedMessage(String message) {
		Reporter.log("<font color='red'>" + message + "</font>");
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */
	public void ValidateElement_Presence(Element element, String ElementName) {
		if (element.isElementAvailable(5)) {
			Assert.assertTrue(true);
			passedStep("Element is present -" + ElementName);
			UtilityLog.info("Element is displayed as expected");

		} else {
			Assert.assertFalse(false);
			failedStep("Element is not present ");
		}
	}

	/**
	 * Converts List to string array
	 * 
	 * @author Jayanthi.ganesan
	 * @param list
	 * @return
	 */
	public String[] ListTOStringArray(List<String> list) {
		try {
			String[] StringArray = new String[list.size()];
			StringArray = list.toArray(StringArray);

			for (String s : StringArray)
				System.out.println(s);
			return StringArray;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param S String array
	 * @return List
	 * @description This method will find the single occurance of elements from
	 *              string array which has both duplicate and unique elements
	 */
	public List<String> StringOccurance(String[] S) {
		try {
			List<String> Singleoccurance = new ArrayList<String>();
			HashMap<String, Integer> hm = new HashMap<String, Integer>();
			for (String tempString : S) {
				if (hm.get(tempString) != null) {
					hm.put(tempString, hm.get(tempString) + 1);
				} else {
					hm.put(tempString, 1);
				}
			}
			Iterator<String> tempString = hm.keySet().iterator();
			while (tempString.hasNext()) {
				String temp = tempString.next();
				if (hm.get(temp) == 1) {
					Singleoccurance.add(temp);
				}
			}
			return Singleoccurance;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}
	}

	/**
	 * @author Indium-Baskar
	 */
	public void impersonateSAtoReviewer() {
		try {
			driver.waitForPageToBeReady();
			waitForElement(getSignoutMenu());
			getSignoutMenu().waitAndClick(10);
			waitForElement(getChangeRole());
			getChangeRole().waitAndClick(5);
			waitForElement(getSelectRole());
			getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
			waitForElement(getAvlDomain());
			Thread.sleep(3000);
			getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
			Thread.sleep(3000);
			waitForElement(getAvlProject());
			getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
			Thread.sleep(3000);
			waitForElement(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			waitForElement(getSaveChangeRole());
			getSaveChangeRole().waitAndClick(5);
			System.out.println("Impersnated from SA to Reviewer");
			UtilityLog.info("Impersnated from SA to Reviewer");

			if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
				try {
					getGlobalMessagePopUpClose().waitAndClick(5);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */
	public void ValidateElementCollection_Presence(ElementCollection element, String ElementName) {

		if (element.isElementAvailable(10)) {
			Assert.assertTrue(true);
			passedStep(ElementName + "-Element is present ");
			UtilityLog.info("Expected elements are displayed as expected");
		} else {
			Assert.assertFalse(false);
			failedStep("Element is not present ");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */
	public void ValidateElement_Absence(Element element, String ElementName) {
		driver.scrollingToElementofAPage(element);
		if (element.isElementAvailable(5)) {
			Assert.assertFalse(false);
			UtilityLog.info("Expected elements are not displayed as expected");
		} else {
			Assert.assertTrue(true);
			passedStep(ElementName);

		}
	}

	/**
	 * @author Raghuram A Modified on : 23/2/22 by Jeevitha Description : TO handle
	 *         all impersonate dynamically in one method with "FROM" and "TO"
	 *         parameter - can be extended based on the requirement
	 * 
	 */
	public void rolesToImp(String fromRole, String toRole) {
		try {
			if (fromRole.equalsIgnoreCase("DA") && toRole.equalsIgnoreCase("PA")) {// impersonate to PA
				impersonateDAtoPA();
			} else if (fromRole.equalsIgnoreCase("PA") && toRole.equalsIgnoreCase("RMU")) {// impersonate to RMU
				impersonatePAtoRMU();
			} else if (fromRole.equalsIgnoreCase("RMU") && toRole.equalsIgnoreCase("REV")) {// impersonate to REV
				impersonateRMUtoReviewer();
			} else if (fromRole.equalsIgnoreCase("SA") && toRole.equalsIgnoreCase("PA")) {// impersonate to PA
				impersonateSAtoPA();
			} else if (fromRole.equalsIgnoreCase("PA") && toRole.equalsIgnoreCase("PA")) {// impersonate to PA
			} else if (fromRole.equalsIgnoreCase("REV") && toRole.equalsIgnoreCase("RMU")) {// impersonate to RMU
				impersonateReviewertoRMU();
			} else if (fromRole.equalsIgnoreCase("SA") && toRole.equalsIgnoreCase("RMU")) {// impersonate to RMU
				impersonateSAtoRMU();
			} else if (fromRole.equalsIgnoreCase("SA") && toRole.equalsIgnoreCase("REV")) {// impersonate to REV
				impersonateSAtoReviewer();
			} else if (fromRole.equalsIgnoreCase("DA") && toRole.equalsIgnoreCase("RMU")) {// impersonate to RMU
				impersonateDAtoRMU();
			} else if (fromRole.equalsIgnoreCase("DA") && toRole.equalsIgnoreCase("REV")) {// impersonate to REV
				impersonateDAtoReviewer();
			} else if (fromRole.equalsIgnoreCase("RMU") && toRole.equalsIgnoreCase("PA")) {// impersonate to PA
				impersonateSAtoPA();
			} else if (fromRole.equalsIgnoreCase("REV") && toRole.equalsIgnoreCase("PA")) {// impersonate to PA
				impersonateSAtoPA();
			} else if (fromRole.equalsIgnoreCase("PA") && toRole.equalsIgnoreCase("REV")) {// impersonate to PA
				impersonatePAtoReviewer();
			}
			stepInfo("Impersonated to " + toRole + " from " + fromRole + "");
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @Author Jeevitha Modified date: 18/11/2021 Description : Read Excel Data and
	 *         return the value
	 */
	public static String[][] readExcelData(String filename, int rowNumToStart) {
		try {
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

			for (int i = rowNumToStart; i < numRows; i++) {
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
			UtilityLog.info("Data from excel sheet retrieved successfully");
			return dataTable;
		} catch (Exception E) {
			E.printStackTrace();
			UtilityLog.info(E.toString());
			return null;
		}
	}

	/**
	 * @author Indium-Baskar
	 * 
	 */

	public void impersonateDAtoRMU() {
		try {
			waitForElement(getSignoutMenu());
			getSignoutMenu().waitAndClick(5);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getChangeRole().Visible();
				}
			}), Input.wait60);
			getChangeRole().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectRole().Visible();
				}
			}), Input.wait60);
			getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
			Thread.sleep(3000);
			waitForElement(getAvlDomain());
			getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
			waitForElement(getAvlProject());
			getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
			waitForElement(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			waitForElement(getSaveChangeRole());
			getSaveChangeRole().waitAndClick(10);
			System.out.println("Impersonated from DA to RMU");
			UtilityLog.info("Impersonated from DA to RMU");

			if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
				try {
					getGlobalMessagePopUpClose().waitAndClick(5);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Indium-Baskar
	 * 
	 */

	public void impersonateDAtoReviewer() {
		try {
			waitForElement(getSignoutMenu());
			getSignoutMenu().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getChangeRole().Visible();
				}
			}), Input.wait90);
			getChangeRole().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectRole().Visible();
				}
			}), Input.wait60);
			getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
			Thread.sleep(3000);
			waitForElement(getAvlDomain());
			getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
			waitForElement(getAvlProject());
			getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
			waitForElement(getSelectSecurityGroup());
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			waitForElement(getSaveChangeRole());
			getSaveChangeRole().waitAndClick(5);
			System.out.println("Impersonated from DA to Reviewer");
			UtilityLog.info("Impersonated from DA to Reviewer");

			if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
				try {
					getGlobalMessagePopUpClose().waitAndClick(5);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Indium-Baskar
	 * 
	 */
//	This method used to login As SA,DA,PA
//	Impersonate to SA-->rmu,rev and DA-->rmu,rev & PA-->rmu,rev
	public void credentialsToImpersonateAsRMUREV(String roll, String impersonate) {
		try {
			switch (impersonate) {
			case "rmu":
				if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonateSAtoRMU();
				}
				if (roll.equalsIgnoreCase("da") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonateDAtoRMU();
				}
				if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonatePAtoRMU();
				}

			case "rev":
				if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
					driver.waitForPageToBeReady();
					impersonateSAtoReviewer();
				}
				if (roll.equalsIgnoreCase("da") && impersonate.equalsIgnoreCase("rev")) {
					driver.waitForPageToBeReady();
					impersonateDAtoReviewer();
				}
				if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
					driver.waitForPageToBeReady();
					impersonatePAtoReviewer();
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}

	}

	/**
	 * @author Raghuram A Date: 11/9/21 Modified date:11/11/21 Modified by: Raghuram
	 *         A Description :
	 * @return
	 */
	public void textFromElementToVerify(Element element, String messageToVerify) {
		try {
			waitForElement(element);
			String status = element.getText().toString();
			softAssertion.assertEquals(status, messageToVerify);
			textCompareEquals(status, messageToVerify, status, status);
		} catch (NoSuchElementException e) {
			failedStep("Element not found");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram A Date: 11/11/21 Modified date:7/13/22 Modified by:
	 *         Description : textCompareEquals with Pass and Fail Message
	 */
	public void textCompareEquals(String sourceString, String compreString, String passMsg, String failMessage) {
		try {
			System.out.println("Source String  : " + sourceString);
			System.out.println("Compare String  : " + compreString);

			stepInfo("Expected : " + sourceString);
			stepInfo("Actual : " + compreString);

			if (sourceString.equals(compreString)) {
				passedStep(passMsg);
			} else if (!sourceString.equals(compreString)) {
				failedStep(failMessage);
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			failedStep(failMessage);
		}
	}

	/**
	 * @author Raghuram A Date: 11/11/21 Modified date:N/A Modified by: Description
	 *         : textCompareEquals with Pass and Fail Message
	 */
	public void textCompareNotEquals(String sourceString, String compreString, String passMsg, String failMessage) {
		try {
			System.out.println("Source String  : " + sourceString);
			System.out.println("Compare String  : " + compreString);
			stepInfo("Source String  : " + sourceString);
			stepInfo("Compare String  : " + compreString);

			softAssertion.assertEquals(sourceString, compreString);
			if (!sourceString.equals(compreString)) {
				passedStep(passMsg);
			} else if (sourceString.equals(compreString)) {
				failedStep(failMessage);
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Raghuram A Date: 11/11/21 Modified date:N/A Modified by: Description
	 *         : textCompareEquals with Pass and Fail Message
	 */
	public void digitCompareEquals(int value1, int value2, String passMsg, String failMessage) {
		try {
			System.out.println("Value 1  : " + value1);
			System.out.println("Value 2  : " + value2);

			softAssertion.assertEquals(value1, value2);
			if (value1 == value2) {
				passedStep(passMsg);
			} else if (value1 != value2) {
				failedStep(failMessage);
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @return
	 */
	public String GetLastModifiedFileName() {
		try {
			File ab = new File(Input.fileDownloadLocation);
			String testPath = ab.toString() + "\\";
			File a = getLatestFilefromDir(testPath);
			if (a != null) {
				stepInfo("last modified file found id " + a.getName());
				String fileName = a.getName();
				fileName = testPath + fileName;
				System.out.println(fileName);
				return fileName;
			} else {
				stepInfo("No files found in the given directory");
				return null;
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @return
	 */
	public int getDirFilesCount() {
		try {
			File dir = new File(Input.fileDownloadLocation);
			File[] dir_contents = dir.listFiles();
			System.out.println(dir_contents.length);
			UtilityLog.info(dir_contents.length + " are present in directory");
			return dir_contents.length;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return 0;
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param dirPath
	 * @return
	 */
	public File getLatestFilefromDir(String dirPath) {
		try {
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				return null;
			}
			File lastModifiedFile = files[0];
			for (int i = 1; i < files.length; i++) {
				if (lastModifiedFile.lastModified() < files[i].lastModified()) {
					lastModifiedFile = files[i];
				}
			}
			UtilityLog.info("Last modified file retreived successfully");
			return lastModifiedFile;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}

	}

	/**
	 * @author Raghuram.A Date: 11/26/21 Modified date:N/A Modified by: N/A
	 * @param projectName
	 */
	public void switchProject(String currentProject) {
		String switchedProject = null;
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			waitForElement(getProjectNames());
			getProjectNames().waitAndClick(10);

			ElementCollection projectList = getAvailableProjectList();
			List<String> availableProjectList = availableListofElements(projectList);
			System.out.println("List size : " + availableProjectList.size());
			if (availableProjectList.size() > 1) {

				for (String project : availableProjectList) {
					if (!project.equalsIgnoreCase(currentProject)) {
						switchedProject = project;
						waitForElement(getSelectProject(switchedProject));
						getSelectProject(switchedProject).waitAndClick(10);
						break;
					}
				}

			} else {
				UtilityLog.info(switchedProject + " : Not more than one project configured");
				failedStep(switchedProject + " : Not more than one project configured");
			}

			driver.waitForPageToBeReady();
			stepInfo("Project Switched : " + getProjectNames().getText());
			UtilityLog.info("Project Switched : " + getProjectNames().getText());
		} catch (Exception e) {
			e.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			failedStep(switchedProject + " : Not more than one project configured");
		}
	}

	/**
	 * @author Indium-Baskar
	 * 
	 */
//	This method used to login As PA
//	Impersonate to PA-->rmu,rev
	public void paImpersonateAsRMUREV(String roll, String impersonate) {
		try {
			switch (impersonate) {
			case "rmu":
				if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonatePAtoRMU();
				}

			case "rev":
				if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
					driver.waitForPageToBeReady();
					impersonatePAtoReviewer();
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Indium-Baskar
	 * 
	 */
//	This method used to login As DA
//	Impersonate to DA-->rmu,rev
	public void daImpersonateAsRMUREV(String roll, String impersonate) {
		try {
			switch (impersonate) {
			case "rmu":
				if (roll.equalsIgnoreCase("da") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonateDAtoRMU();
				}

			case "rev":
				if (roll.equalsIgnoreCase("da") && impersonate.equalsIgnoreCase("rev")) {
					driver.waitForPageToBeReady();
					impersonateDAtoReviewer();
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Indium-Baskar
	 * 
	 */
//	This method used to login As SA
//	Impersonate to SA-->rmu,rev
	public void SaImpersonateAsRMUREV(String roll, String impersonate) throws InterruptedException {
		try {
			switch (impersonate) {
			case "rmu":
				if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonateSAtoRMU();
				}

			case "rev":
				if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
					driver.waitForPageToBeReady();
					impersonateSAtoReviewer();
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}

	}

	/**
	 * @author Jayanthi.ganesan Modified by jayanthi-3/2/22
	 * @param element
	 * @param text
	 * @return
	 */
	public int getIndex(ElementCollection element, String text) {
		int k = 1;
		List<WebElement> elementList = null;
		try {
			elementList = element.FindWebElements();
			for (WebElement wenElementNames : elementList) {

				String elementName = wenElementNames.getText().trim();
				System.out.println(elementName);
				System.out.println(text);
				if (text.equalsIgnoreCase(elementName)) {
					break;
				} else {
					k++;
				}
			}
			UtilityLog.info("Index of the element is " + k);
			return k;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return 0;
		}
	}

	public void clearAllCookies() {
		driver.Manage().deleteAllCookies();
		UtilityLog.info("All cookies are cleared");
	}

	public void implicitWait(int timeOut) {
		driver.Manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	/*
	 * Method is to give static wait time value
	 * 
	 * @author Steffy
	 * 
	 * @param sec This is to denote the number of seconds needs to be waited
	 */
	public void waitTime(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			UtilityLog.info("");
		}
	}

	public void dragAndDrop1(Element elementToBeDragged, Element elementToBeDropped) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 30);
		wait.until(ExpectedConditions.elementToBeClickable(elementToBeDragged.getWebElement()));
		Actions actions = new Actions(driver.getWebDriver());
		waitForElement(elementToBeDragged);
		actions.clickAndHold((elementToBeDragged).getWebElement());
		actions.moveToElement((elementToBeDropped).getWebElement());
		actions.release(elementToBeDropped.getWebElement());
		actions.build().perform();
	}

	public void waitTillTextToPresent(Element element, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 90);
			wait.until(ExpectedConditions.textToBePresentInElement(element.getWebElement(), text));
		} catch (Exception e) {
			e.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Indium-Baskar
	 *
	 */
	// Reusable method for choosing random number
	public int randNumber(int size) {
		int rand_Number = 0;
		try {
			Random rand = new Random();
			rand_Number = rand.nextInt(size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rand_Number;
	}

	/**
	 * @author Raghuram.A Date: 12/21/21 Modified date:N/A Modified by: N/A
	 * @description : list Of Random Index data To Add
	 */
	public List<String> listOfRandomIndexToAdd(List<String> sourceList, int[] indexToSelect) {

		List<String> addedList = new ArrayList<String>();
		System.out.println("length : " + indexToSelect.length);
		for (int i = 0; i < indexToSelect.length; i++) {
			addedList.add(sourceList.get(indexToSelect[i]));
		}

		return addedList;

	}

	/**
	 * @author Raghuram.A Date: 12/21/21 Modified date:N/A Modified by: N/A
	 * @description : list Of Random Index data To Add in HashMap
	 */
	public HashMap<String, String> hashMapOfRandomIndexToAdd(List<String> sourceList, int[] indexToSelect,
			HashMap<String, String> mapListPair) {

		HashMap<String, String> mapPair = new HashMap<String, String>();
		System.out.println("length : " + indexToSelect.length);
		for (int i = 0; i < indexToSelect.length; i++) {
			mapPair.put(sourceList.get(indexToSelect[i]), mapListPair.get(sourceList.get(indexToSelect[i])));
		}

		return mapPair;

	}

	/**
	 * @author Raghuram A Date: 12/29/21 Modified date:N/A Modified by: Description
	 *         : listCompareEquals with Pass and Fail Message
	 */
	public void listCompareEquals(List<String> sourceList, List<String> compreList, String passMsg,
			String failMessage) {
		System.out.println("Source String  : " + sourceList);
		System.out.println("Compare String  : " + compreList);

		stepInfo("Source String  : " + sourceList);
		stepInfo("Compare String  : " + compreList);

		softAssertion.assertEquals(sourceList, compreList);
		if (sourceList.equals(compreList)) {
			passedStep(passMsg);
		} else if (!sourceList.equals(compreList)) {
			failedStep(failMessage);
		}
		softAssertion.assertAll();
	}

	/**
	 * @author Raghuram A Date: 12/29/21 Modified date:N/A Modified by:N/A
	 *         Description : returns TotalSheetCount from workbook
	 * @param fileName
	 * @throws IOException
	 */
	public int getTotalSheetCount(String location, String fileName) throws IOException {
		int number_of_sheets = 0;
		String fileTOGetCOunt = location + fileName;
		File file = new File(fileTOGetCOunt);
		FileInputStream xlFile = new FileInputStream(file);
		String fileFormat = FilenameUtils.getExtension(fileName);

		if (fileFormat.equalsIgnoreCase("xlsx")) {
			Workbook xlwb = new XSSFWorkbook(xlFile);
			number_of_sheets = xlwb.getNumberOfSheets();
		}
		return number_of_sheets;
	}

	/**
	 * @author Raghuram.A Date: 12/30/21 Modified date:N/A Modified by:N/A
	 *         Description : returns rename a Particular file
	 */
	public String renameFile(Boolean renameDynamic, String fileLocation, String fileName, String fileFormat,
			Boolean backtoDefault, String defaultName) {
		String renameString = fileName + "_R_" + Utility.dynamicNameAppender();
		File oldName = new File(fileLocation + fileName + fileFormat);
		File newName = new File(fileLocation + renameString + fileFormat);

		if (backtoDefault) {
			File oldNameD = new File(fileLocation + fileName + fileFormat);
			File newNameD = new File(fileLocation + defaultName + fileFormat);

			if (oldNameD.renameTo(newNameD)) {
				System.out.println("File renamed to Default Successfully");
			} else {
				System.out.println("Error in renaming File");
			}
		}

		if (renameDynamic) {
			if (oldName.renameTo(newName)) {
				System.out.println("File renamed Successfully");
			} else {
				System.out.println("Error in renaming File");
			}
		}

		return renameString;
	}

	/**
	 * @Author Jeevitha
	 * @param ddElement
	 * @return
	 */
	public String getCurrentDropdownValue(Element ddElement) {
		WebElement dropdown = ddElement.getWebElement();
		Select select = new Select(dropdown);
		WebElement selectedValue = select.getFirstSelectedOption();
		String selectedoption = selectedValue.getText();
		System.out.println("Selected element from dropdown: " + selectedoption);
		stepInfo("Selected element from dropdown: " + selectedoption);
		return selectedoption;
	}

	/**
	 * @Author Brundha
	 * @param username
	 * @description Method to unselect the checkbox
	 */
	public void UnSelectTheProductionChecKboxInUser(String username) {
		waitForElement(SelectSearchOption());
		SelectSearchOption().SendKeys(username);

		SelectSearchOption().Enter();
		driver.waitForPageToBeReady();

		waitTillElemetToBeClickable(getEditButton(Input.projectName));
		driver.waitForPageToBeReady();
		getEditButton(Input.projectName).waitAndClick(10);
		getFunctionalityButton().waitAndClick(20);

		waitTillElemetToBeClickable(UnSelectProductionCheckBox());
		driver.waitForPageToBeReady();
		UnSelectProductionCheckBox().waitAndClick(10);
		getSaveBtn().waitAndClick(20);
		VerifySuccessMessage("User profile was successfully modified");
		CloseSuccessMsgpopup();

	}

	/**
	 * @Author Jeevitha
	 * @param sourceString
	 * @param compreString
	 * @param passMsg
	 * @param failMessage
	 */
	public void compareTextViaContains(String sourceString, String compreString, String passMsg, String failMessage) {
		try {
			System.out.println("Source String  : " + sourceString);
			System.out.println("Compare String  : " + compreString);

			if (sourceString.contains(compreString)) {
				passedStep(passMsg);
			} else {
				failedStep(sourceString + " : " + failMessage);
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/*
	 * Method is to handle the alert which is getting displayed
	 * 
	 * @author steffy.d
	 */

	public void handleAlert() {
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {
			UtilityLog.info("Alert is not present");
		}
	}

	/**
	 * @author Raghuram A Date: 12/29/21 Modified date:N/A Modified by: Description
	 *         : listCompareEquals with Pass and Fail Message
	 */
	public void listCompareNotEquals(List<String> sourceList, List<String> compreList, String passMsg,
			String failMessage) {
		System.out.println("Source String  : " + sourceList);
		System.out.println("Compare String  : " + compreList);

		softAssertion.assertNotEquals(sourceList, compreList);
		if (sourceList.equals(compreList)) {
			failedStep(failMessage);
		} else if (!sourceList.equals(compreList)) {
			passedStep(passMsg);
		}
		softAssertion.assertAll();
	}

	public void SelectSecurityGrp(String username, String SecurityGrp) {
		waitForElement(SelectSearchOption());
		SelectSearchOption().SendKeys(username);
		SelectSearchOption().Enter();
		driver.waitForPageToBeReady();
		waitTillElemetToBeClickable(getEditButton(Input.projectName));
		getEditButton(Input.projectName).waitAndClick(10);
		driver.scrollingToBottomofAPage();
		selectSecurityGroup().isDisplayed();
		waitTillElemetToBeClickable(selectSecurityGroup());
		selectSecurityGroup().selectFromDropdown().selectByVisibleText(SecurityGrp);
		getSaveBtn().waitAndClick(5);
		VerifySuccessMessage("User profile was successfully modified");
		CloseSuccessMsgpopup();

	}

	public void SelectDefaultSecurityGrp(String username) {
		waitForElement(SelectSearchOption());
		SelectSearchOption().SendKeys(username);
		SelectSearchOption().Enter();
		waitTillElemetToBeClickable(getEditButton(Input.projectName));
		getEditButton(Input.projectName).waitAndClick(10);
		driver.scrollingToBottomofAPage();
		selectDefaultSecurityGroup().isDisplayed();
		selectDefaultSecurityGroup().isElementAvailable(1);
		selectDefaultSecurityGroup().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		getSaveBtn().waitAndClick(10);
		VerifySuccessMessage("User profile was successfully modified");
		CloseSuccessMsgpopup();

	}

	/**
	 * @author Indium Raghuram ] Description : Method to collect the list of
	 *         elements and returns as list with attributeValues (GenericMethod)
	 *         Date:8/15/21 Modified date: N/A Modified by: N/A
	 */
	public List<String> availableListofElementsWithAttributeValues(ElementCollection element, String attributeName) {
		try {
			List<String> elementNames = new ArrayList<>();
			List<WebElement> elementList = null;
			elementList = element.FindWebElements();
			for (WebElement wenElementNames : elementList) {
				String elementName = wenElementNames.getAttribute(attributeName);
				elementNames.add(elementName);
			}
			return elementNames;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}
	}

	/**
	 * @author @Brundha
	 * @Description:Method to verify data in excel file
	 * 
	 */
	public void csvFileVerification() throws IOException, InterruptedException {
		driver.waitForPageToBeReady();
		String fileName = GetFileName();
		List<String> lines = new ArrayList<>();
		String line = null;
		String[] headerToVerify = { "DocID", "Beg Bates", "End Bates" };
		FileReader file = null;
		file = new FileReader(fileName);
		BufferedReader br = new BufferedReader(file);
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		String value = lines.get(0);
		System.out.println(value);
		String[] arrOfStr = value.split(",");
		for (int i = 0; i < headerToVerify.length; i++) {
			textCompareEquals(arrOfStr[i], headerToVerify[i], "Data is Present as Expected: " + arrOfStr[i],
					"Data is not Present as Expected : " + arrOfStr[i]);
		}
	}

	/**
	 * @author @Brundha
	 * @Description:Method to get the file name
	 * 
	 */
	public String GetFileName() {
		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "\\";
		File a = getLatestFilefromDir(testPath);
		if (a != null) {
			stepInfo("last modified file found id " + a.getName());
			String fileName = a.getName();
			fileName = testPath + fileName;
			System.out.println(fileName);
			return fileName;
		} else {
			stepInfo("No files found in the given directory");
			return null;
		}

	}

	/**
	 * @author Indium Raghuram Description : verify Original SortOrder Date:01/31/21
	 *         Modified date: N/A Modified by: N/A
	 */
	public void verifyOriginalSortOrder(List<String> originalOrderedList, List<String> afterSortList, String sortType,
			Boolean sortOrder) throws InterruptedException, AWTException {

		System.out.println("Original Order");
		for (String a : originalOrderedList) {
			System.out.println(a);
		}

		if (sortType.equals("Ascending")) {
			Collections.sort(afterSortList);
		} else if (sortType.equals("Descending")) {
			Collections.sort(afterSortList, Collections.reverseOrder());
		}

		System.out.println("Sorted Order");
		for (String b : afterSortList) {
			System.out.println(b);
		}

		if (sortOrder) {
			listCompareEquals(originalOrderedList, afterSortList, "Sorting order maintained", "Sorting order failed");
		}

	}

	/**
	 * @author Indium Raghuram Description : Date:05/17/21 Modified date: N/A
	 *         Modified by: N/A
	 */
	public Boolean verifyElementCollectionIsNotEmpty(ElementCollection elementC, String passMsg, String failMsg) {
		if (elementC.size() > 0) {
			passedStep(passMsg);
			return true;
		} else {
			failedMessage(failMsg);
			return false;
		}
	}

	/*
	 * This method is to verify the background color
	 * 
	 * @author Steffy
	 * 
	 * @param element The element for which BG color needs to be verified
	 * 
	 * @param expectedColor This the expected BG color value
	 */
	public void verifyBackGroundColor(Element element, String expectedColor) {
		waitForElement(element);
		try {
			String actualColor = element.GetCssValue("background-color");
			softAssertion.assertEquals(actualColor, expectedColor);
			softAssertion.assertAll();
			passedStep("Background color is changed as expected");
		} catch (Exception e) {
			e.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	public void VerifyWarningMessageAdditionalLine(String ExpectedMsg, String ExpectedMsg2, String ExpectedMsg3) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSuccessMsgHeader().Visible();
			}
		}), Input.wait30);
		Assert.assertEquals("Warning !", getSuccessMsgHeader().getText().toString());
		Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
		UtilityLog.info("Expected message - " + ExpectedMsg);
		Reporter.log("Expected message - " + ExpectedMsg, true);
		Assert.assertEquals(ExpectedMsg2, getSecondLineSuccessMsg(1).getText().toString());
		String string3 = getSecondLineSuccessMsg(1).getText().toString();
		System.out.println(string3);
		Assert.assertEquals(ExpectedMsg3, getSecondLineSuccessMsg(2).getText().toString());
	}

	/**
	 * @author Jeevitha
	 * @param source
	 * @param compareString
	 * @param passMsg
	 * @param failMsg
	 * @throws InterruptedException
	 */
	public void compareListWithString(List<String> source, String compareString, String passMsg, String failMsg)
			throws InterruptedException {
		boolean compare = false;
		for (String actualValue : source) {
			if (actualValue.equalsIgnoreCase(compareString)) {
				compare = true;
			} else {
				compare = false;
				break;
			}
		}
		if (compare) {
			passedStep(passMsg);
		} else {
			failedStep(failMsg);
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param element
	 */
	public void elementDisplayCheck(Element element) {
		if (element.isDisplayed()) {
			passedStep("Element is displayed :" + element);
			System.out.println("element is displayed");
		} else {
			failedStep("Element verification failed :" + element);
			System.out.println("element not is displayed");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void visibleCheck(String text) {
		if (text(text).isElementAvailable(5)) {
			passedStep(text + " is visibled");
			System.out.println(text + " is visible");
		} else {
			failedStep(text + " is not visible");
			System.out.println(text + " is not visible");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param result
	 * @param passMsg   Message which is to be printed in report when condition is
	 *                  pass
	 * @param failMsg   Message which is to be printed in report when condition is
	 *                  fail
	 * @param condition This is flag which says whether it is pass or fail or
	 *                  warning Method to print result in report based on the
	 *                  bollean value - can be extended
	 * @createdon : 2/8/22 @modifiedon NA @modifiedby NA
	 */
	public void printResutInReport(Boolean result, String passMsg, String failMsg, String condition) {
		if (condition.equalsIgnoreCase("Pass")) {
			if (result) {
				passedStep(passMsg);
			} else {
				failedStep(failMsg);
			}
		} else if (condition.equalsIgnoreCase("Fail")) {
			if (result) {
				failedStep(failMsg);
			} else {
				passedStep(passMsg);
			}
		} else if (condition.equalsIgnoreCase("Warning")) {
			if (result) {
				passedStep(passMsg);
			} else {
				failedMessage(failMsg);
			}
		}
	}

	/*
	 * This method is to open a new empty tab
	 * 
	 * @author Steffy D
	 */
	public void openNewTab() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			passedStep("New tab is opened successfully");
		} catch (Exception e) {
			UtilityLog.info("Failed to open new tab due to following exception " + e);
		}
	}

	/*
	 * This method is to open a unzip file
	 * 
	 * @author sowndarya.velraj
	 * 
	 * @param zipFilePath [The path where the zip file is located]
	 * 
	 * @param destDir [The destination path where the zip file needs to extracted]
	 */
	public static void unzip(String zipFilePath, String destDir) {
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				System.out.println("Unzipping to " + newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @Author Jeevitha
	 * @Description : Converts rgb to hexa
	 * @param bgColor   : string contains rgba
	 * @param select    : if true then rgba else rgb
	 * @param splitTern : string contains rgb
	 * @return hexa value
	 */
	public String rgbTohexaConvertor_Optional(String bgColor, boolean select, String splitTern) {
		try {
			StringTokenizer st;
			if (select) {
				String s1 = bgColor.substring(4);
				bgColor = s1.replace(')', ' ');
				bgColor = s1.replace('(', ' ');
				st = new StringTokenizer(bgColor);
			} else {
				splitTern = splitTern + ",";
				st = new StringTokenizer(splitTern);

			}
			int r = Integer.parseInt(st.nextToken(",").trim());
			int g = Integer.parseInt(st.nextToken(",").trim());
			int b = Integer.parseInt(st.nextToken(",").trim());
			Color c = new Color(r, g, b);
			String hex = "#" + Integer.toHexString(c.getRGB()).substring(2).toUpperCase();
			return hex;
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
			return null;
		}
	}

	/**
	 * @author Raghuram.A
	 * @description : duplicate Tab using Robot Class
	 */
	public void openDuplicateTab() {
		try {
			Robot robot = new Robot();

			// Key Press
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_D);
			robot.delay(2000);
			robot.keyPress(KeyEvent.VK_ENTER);

			// Key Release
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_D);
			robot.keyRelease(KeyEvent.VK_ENTER);

			passedStep("Duplicate TAB is Opened Successfully");
		} catch (Exception e) {
			failedStep("Failed to duplicate due to following exception " + e);
		}
	}

	/**
	 * @Author Jeevitha
	 * @param ExpectedMsg : expected Error Msg
	 * @Description : Verify Error message in German
	 */
	public void VerifyErrorMessageInGerman(String ExpectedMsg) {
		waitForElement(getSuccessMsgHeader());
		Assert.assertEquals("Fehler !", getSuccessMsgHeader().getText().toString());
		Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
		UtilityLog.info("Expected message - " + ExpectedMsg);
		Reporter.log("Expected message - " + ExpectedMsg, true);
	}

	/**
	 * @author Indium Raghuram Description: verifyListSizeWithCondition
	 *         Date:02/16/21 Modified date:N/A* Modified by:N/A
	 * @description : method to verify list size based on condition value
	 * @param listToCheck    - List to compare
	 * @param condition      - Condition to check
	 * @param passMsg        - Pass message to display in result
	 * @param failMsg        - Fail message to display in result
	 * @param conditionValue - Condition Value to check
	 */

	public void verifyListSizeWithCondition(ElementCollection listToCheck, String condition, String passMsg,
			String failMsg, int conditionValue) {
		if (condition.equalsIgnoreCase("equalsP")) {
			if (listToCheck.size() == conditionValue) {
				passedStep(passMsg);
			} else {
				failedStep(failMsg);
			}
		}
	}

	/**
	 * @author Raghuram.A
	 * @description : method to add a list into set
	 * @param datas - List of datas to be added into a Set
	 * @return - a new Set
	 */
	public HashSet<String> addListIntoSet(List<String> datas) {
		HashSet<String> hash_Set = new HashSet<String>();
		for (String fieldNames : datas) {
			hash_Set.add(fieldNames);
		}
		return hash_Set;
	}

	/**
	 * @author @Brundha
	 * @throws AWTException
	 * @Description:Method to verify downloaded csv file and its sorting order
	 * 
	 */
	public void VerifyingCSVFileDownloadedAndSorted() throws IOException, InterruptedException, AWTException {
		// Method to verify the downloaded file:
		String fileName = GetFileName();
		List<String> lines = new ArrayList<>();
		String line = null;
		List<String> Values = new ArrayList<>();
		FileReader file = null;
		file = new FileReader(fileName);

		BufferedReader br = new BufferedReader(file);
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		System.out.println(lines.size());
		for (int i = 1; i < lines.size() - 1; i++) {
			String value = lines.get(i);
			String[] arrOfStr = value.split(",");

			System.out.println(arrOfStr[1]);
			Values.add(arrOfStr[1]);
		}
		// Method to verify the sorting order
		verifyOriginalSortOrder(Values, Values, "Ascending", true);
	}

	/**
	 * @author Raghuram.A
	 * @param key - Key to Press
	 * @description : method to hit keyboard key and release
	 */
	public void hitKey(int key) {
		try {
			Robot robot = new Robot();

			// Key Press
			robot.keyPress(key);
			// Key Release
			robot.keyRelease(key);

			System.out.println(key + ": Pressed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param BaseList
	 * @param CompareList[List to be compared] This method will remove the
	 *                         duplicated elements in the given list by comparing
	 *                         with another base list.
	 * @return
	 */
	public List<String> getUniqueValues_FromTwoList(List<String> BaseList, List<String> CompareList) {
		List<String> UniqueValues = new ArrayList<String>();

		for (int i = 0; i < CompareList.size(); i++) {
			if (!BaseList.contains(CompareList.get(i))) {
				UniqueValues.add(CompareList.get(i));
			}
		}
		return UniqueValues;
	}

	/**
	 * @author Raghuram.A
	 * @param Bgcount - Initial Notification count
	 * @param toCount - Expected Notification Count
	 */
	public void checkNotificationCount(int Bgcount, int toCount) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return initialBgCount() == Bgcount + toCount;
			}
		}), Input.wait60);
	}

	/**
	 * @author Jayanthi.Ganesan This method will find the number of times a String
	 *         appeared in a given list.
	 * @param s                      [List from which element occurrence needs to be
	 *                               counted]
	 * @param elementToCountOCurence [string for which we need to find no of
	 *                               occurrences in list]
	 * @return
	 */
	public int findNoOfOccurences(List<String> s, String elementToCountOCurence) {
		int occurence = 0;

		for (int i = 0; i < s.size(); i++) {
			if (elementToCountOCurence.equalsIgnoreCase(s.get(i))) {
				occurence++;
			}
		}
		return occurence;

	}

	/**
	 * @author Raghuram.A
	 * @param fileName    - Excel File to select along with location
	 * @param sheet       - Sheet to select
	 * @param additional1 - additional string parameter for future reference
	 * @param additional2 - additional int parameter for future reference
	 * @return - return total no.of rows in excel
	 * @throws IOException
	 */
	public int getTotalNumOfRowsInExcel(String fileName, int sheet, String additional1, int additional2)
			throws IOException {
		int numRows = 0;
		File file = new File(fileName);
		try {
			FileInputStream xlFile = new FileInputStream(file);
			Workbook xlwb = new XSSFWorkbook(xlFile);
			Sheet xlSheet = xlwb.getSheetAt(sheet);

			// Get total no.of rows in excel
			numRows = xlSheet.getLastRowNum();
			System.out.println("no of Rows : " + numRows);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numRows;
	}

	/**
	 * @author Raghuram.A
	 * @param expURL  - Expected Landing page URL
	 * @param passMsg - Pass message to display
	 * @param failMsg - Fail message to display
	 */
	public void verifyUrlLanding(String expURL, String passMsg, String failMsg) {
		driver.waitForPageToBeReady();
		String url = driver.getUrl();
		textCompareEquals(expURL, url, passMsg, failMsg);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description impersonate Da to pa if Da user is multi domain assigned
	 */
	public void impersonateDAtoPAforMultiDominUser() {
		getSignoutMenu().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait30);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait30);
		getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectProjectTo().Visible();
			}
		}), Input.wait30);

		waitForElement(getAvlDomain());
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

		getSelectProjectTo().selectFromDropdown().selectByVisibleText(Input.projectName);
		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from DA to PA");
		UtilityLog.info("Impersnated from DA to PA");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param projectVerify
	 * @Description verify that current project is selected project
	 */
	public void verifyCurrentProject(String projectVerify) {
		String project = getProjectNames().getText().trim();
		softAssertion.assertEquals(projectVerify, project);
		passedStep(projectVerify + " is the Current Project");
	}

	/**
	 * @author Indium-Baskar
	 * @Description This Method used for impersonate to pa to rmu user after bulk
	 *              user access done
	 */
	public void impersonatePAtoRMUAfterbulk() throws InterruptedException {
		waitForElement(getSignoutMenu());
		waitTillElemetToBeClickable(getSignoutMenu());
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait60);
		getChangeRole().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		Thread.sleep(3000);
		getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		Thread.sleep(3000);
		getSelectSecurityGroupBulk().selectFromDropdown().selectByVisibleText("Default Security Group");
		getSaveChangeRole().waitAndClick(10);
		this.stepInfo("Impersnated from PA to RMU");
		UtilityLog.info("Impersnated from PA to RMU");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * @author Indium-Baskar
	 * 
	 */

	public void impersonateBasedOnCondition(String role, String domainName, String nonDomainProject) {
		try {
			waitForElement(getSignoutMenu());
			getSignoutMenu().waitAndClick(5);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getChangeRole().Visible();
				}
			}), Input.wait60);
			getChangeRole().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectRole().Visible();
				}
			}), Input.wait60);
			getSelectRole().selectFromDropdown().selectByVisibleText(role);
			Thread.sleep(3000);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAvlDomain().Visible();
				}
			}), Input.wait30);
			getAvlDomain().selectFromDropdown().selectByVisibleText(domainName);
			Thread.sleep(3000);
			getAvlProject().selectFromDropdown().selectByVisibleText(nonDomainProject);
			Thread.sleep(3000);
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
			getSaveChangeRole().waitAndClick(10);
			System.out.println("Impersnated  to : " + role + "");
			UtilityLog.info("Impersnated  to : " + role + "");

			if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
				try {
					getGlobalMessagePopUpClose().waitAndClick(5);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : Wait until file download is complete
	 */
	public void waitUntilFileDownload() {
		File dir = new File(Input.fileDownloadLocation);
		long size, resize;
		for (int i = 0; i < 1000; i++) {
			size = org.apache.commons.io.FileUtils.sizeOfDirectory(dir);
			System.out.println("Initial Size : " + size);
			waitTime(6);
			resize = org.apache.commons.io.FileUtils.sizeOfDirectory(dir);
			System.out.println("Resized size: " + resize);

			if (size == resize) {
				System.out.println("File Downloaded Successfully");
				stepInfo("File Downloaded Successfully");
				break;
			}

		}
	}

	/**
	 * @throws IOException
	 * @Author Mohan Modified date: 18/11/2021 Description : Read Excel Data and
	 *         return the value
	 */
	public String readExcelDataSheetNameOnly(String filename, int sheetNameNum) throws IOException {

		String sheetName = null;
		try {
			File file = new File(filename);
			FileInputStream xlFile = new FileInputStream(file);
			Workbook xlwb = new XSSFWorkbook(xlFile);
			sheetName = xlwb.getSheetName(sheetNameNum);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sheetName;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description verify that background task is completed
	 */
	public void isBackGroudTaskCompleted() {
		driver.waitForPageToBeReady();
		waitForElement(getBackGroudTaskIcon());
		getBackGroudTaskIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		if (getBackGroudTaskSuccesMark().isDisplayed()) {
			passedStep("backgroud task is completed successfully");
		} else {
			failedStep("verification failed");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description click the last completed backgroud task
	 */
	public void clickFirstBackRoundTask() {
		waitForElement(getFirstBackRoundTask());
		getFirstBackRoundTask().waitAndClick(10);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description verify that notification bull Icon changed to red.
	 */
	public void notifyBullIconVerification() {
		driver.waitForPageToBeReady();
		getBullHornIcon().isElementAvailable(30);
		waitForElement(getBullHornIcon());
		getRedBullHornIcon().isElementAvailable(30);
		driver.waitForPageToBeReady();
		String color = getBullIcon().getWebElement().getCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = org.openqa.selenium.support.Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		if (ExpectedColor.equals("#e74735")) {
			passedStep("BullHorn icon is highlighted red as expected");
		} else {
			failedStep("Bullhorn icon is not red as expected");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @return
	 * @Description switch to another domain.
	 */
	public String switchDomain() {

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		String currentProject = getProjectNames().getText().trim();
		waitForElement(getProjectNames());
		getProjectNames().waitAndClick(10);

		List<WebElement> availableProjectList = getAvailableDomainList().FindWebElements();
		int n = availableProjectList.size();
		System.out.println("List size : " + n);
		if (n > 1) {

			for (WebElement element : availableProjectList) {
				String project = element.getText().trim();
				if (!project.equalsIgnoreCase(currentProject)) {
					waitForElement(getSelectProject(project));
					getSelectProject(project).waitAndClick(10);
					break;
				}
			}

		} else {
			UtilityLog.info(" : Not more than one project configured");
			failedStep(" : Not more than one project configured");
		}

		driver.waitForPageToBeReady();
		stepInfo("Project Switched : " + getProjectNames().getText());
		UtilityLog.info("Project Switched : " + getProjectNames().getText());
		return currentProject;

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param text
	 * @escription verify that expected text is not displayed
	 */
	public void notDisplayCheck(String text) {
		if (!textValue(text).isElementAvailable(1)) {
			passedStep(text + " is not displayed");
		} else {
			failedStep(text + " is displayed");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param expURL
	 * @description : This method verifies page navigation using url
	 */
	public void verifyPageNavigation(String expURL) {
		driver.waitForPageToBeReady();
		// String expectedURL=Input.url+expURL;
		String actualURL = driver.getUrl();
		if (actualURL.contains(expURL)) {
			passedStep("Navigated to  " + expURL + " Page sucessfully");
		} else {
			failedStep("Navigated to  " + actualURL + " Page but expected Page to navigate  is " + expURL + ".");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param expURL
	 * @description : This method verifies the new tab is opened or not
	 */
	public boolean verifyNewTabOpened() {
		// get window handlers as list
		List<String> browserTabs = new ArrayList<String>(driver.WindowHandles());
		boolean isTabOpen = true;
		System.out.println(browserTabs);
		// switch to new tab
		if (browserTabs.size() == 1) {
			stepInfo("Window Id of opened tab" + browserTabs);
			stepInfo("As per the window ID list retrieved No new Tabs opened and page loaded in existing tab itself");
		}
		if (browserTabs.size() > 1) {
			stepInfo("Window Id of all opened tabs" + browserTabs);
			stepInfo("New Tabs are  opened .");
			isTabOpen = false;
		}
		return isTabOpen;
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @param expURL
	 * @description : This method is used to switch the tab
	 */
	public void switchTab(int SwitchtoWindow) {
		// get window handlers as list
		List<String> browserTabs = new ArrayList<String>(driver.WindowHandles());
		System.out.println("Total windows: " + browserTabs.size());
		// switch to new tab
		if (browserTabs.size() > 0) {
			driver.switchTo().window(browserTabs.get(SwitchtoWindow));
		} else {
			stepInfo("No new Tabs opened.");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param textList
	 * @Description verify list of text is displayed in webPage
	 */
	public void isTextAreAvailableInWebPage(String[] textList) {
		driver.waitForPageToBeReady();
		for (String text : textList) {
			if (text(text).isElementAvailable(5)) {
				passedStep(text + "  is displayed");
			} else {
				failedStep(text + " not displayed");
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description Clear bullhorn notifications
	 */
	public void clearBullHornNotification() {
		driver.waitForPageToBeReady();
		waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);
	}

	/**
	 * @author
	 * @Description reads excel column data
	 */
	public List<String> readExcelColumnData(String filename, String columnHeading) throws Exception {

		List<String> excelData = new ArrayList<String>();
		File file = new File(filename);

		FileInputStream xlFile = new FileInputStream(file);
		Workbook xlwb = new XSSFWorkbook(xlFile);
		Sheet xlSheet = xlwb.getSheetAt(0);

		int numRows = xlSheet.getLastRowNum() + 1;
		System.out.println("no of Rows : " + numRows);
		int numCols = xlSheet.getRow(0).getLastCellNum();
		System.out.println("NO of columns : " + numCols);

		for (int i = 0; i < numCols; i++) {
			String columnValue = xlSheet.getRow(0).getCell(i).getStringCellValue();
			System.out.println(columnValue);

			if (columnValue.equals(columnHeading)) {
				for (int j = 1; j < numRows; j++) {
					String currentValue = xlSheet.getRow(j).getCell(i).getStringCellValue();
					System.out.println(currentValue);
					excelData.add(currentValue);
				}

				break;
			}
		}
		return excelData;

	}

	/**
	 * @author
	 * @return
	 * @Description compare list with list using contains
	 * @Modified By :Jeevitha
	 */

	public boolean compareListViaContains(List<String> baseList, List<String> compareList) {
		boolean flag = false;

		for (int i = 0; i < baseList.size(); i++) {
			if (baseList.get(i).contains(compareList.get(i))) {
				Assert.assertEquals(true, true);
				flag = true;
			} else {
				failedStep("Base String doesn't contains compare String");
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param textList
	 * @Description verify that list of text are not available in webPage
	 */
	public void isTextAreNotAvailableInWebPage(String[] textList) {
		driver.waitForPageToBeReady();
		for (String text : textList) {
			if (!textValue(text).isElementAvailable(1)) {
				passedStep(text + "  is displayed");
			} else {
				failedStep(text + " not displayed");
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param domain
	 * @param projectName
	 * @param securityGroup
	 * @throws InterruptedException
	 * @Descripiton imporsonate rmu to reviewer with out taken input as domain
	 *              projectname and security group
	 */
	public void impersonateRMUtoReviewer(String domain, String projectName, String securityGroup)
			throws InterruptedException {
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait90);
		getChangeRole().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(domain);
		Thread.sleep(3000);
		getAvlProject().selectFromDropdown().selectByVisibleText(projectName);
		Thread.sleep(3000);
		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from RMU to Reviewer");
		UtilityLog.info("Impersnated from RMU to Reviewer");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description oprn imporsonate tab
	 */
	public void openImpersonateTab() {
		driver.waitForPageToBeReady();
		waitForElement(getSignoutMenu());
		waitTillElemetToBeClickable(getSignoutMenu());
		getSignoutMenu().waitAndClick(10);
		waitForElement(getChangeRole());
		waitTillElemetToBeClickable(getChangeRole());
		getChangeRole().waitAndClick(10);
		driver.waitForPageToBeReady();
		stepInfo("open impersonate tab");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param role
	 * @Description select role in imporsonate tab
	 */
	public void selectImpersonateRole(String role) {
		driver.waitForPageToBeReady();
		waitForElement(getSelectRole());
		getSelectRole().selectFromDropdown().selectByVisibleText(role);
		driver.waitForPageToBeReady();
		stepInfo("select imporsonate role");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param domain
	 * @Description select domain in imporsonate tab
	 */
	public void selectImpersonateDomain(String domain) {
		driver.waitForPageToBeReady();
		waitForElement(getSelectDomain());
		getSelectDomain().selectFromDropdown().selectByVisibleText(domain);
		stepInfo(domain + " domain was selected");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Desction wait for task should be completed and get notified
	 */
	public void waitForNotification() {
		if (getRedBullHornIcon().isElementAvailable(350)) {
			passedStep("notification got received");
		} else {
			failedStep("notification still not received,it's get too much time");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/04/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @param dataList
	 */
	public void printListString(List<String> dataList) {
		// Using For Each loop to print String list
		stepInfo("--------------------------------");
		for (String value : dataList) {
			stepInfo(value);
		}
		stepInfo("--------------------------------");
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/04/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @param listarray - source array
	 * @param dataList  - list to compare
	 * @param sort      - sort condition if required
	 * @param passMsg
	 * @param failMsg
	 */
	public void compareArraywithDataList(String[] listarray, List<String> dataList, Boolean sort, String passMsg,
			String failMsg) {

		List<String> value = new ArrayList<String>();
		for (int i = 0; i < listarray.length; i++) {
			value.add(listarray[i]);
		}

		if (sort) {
			Collections.sort(value);
			Collections.sort(dataList);
		}
		listCompareEquals(dataList, value, passMsg, failMsg);
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/04/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @param element
	 */
	public void mouseHoverOnElement(Element element) {
		try {
			Actions ac = new Actions(driver.getWebDriver());
			ac.moveToElement(element.getWebElement()).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			failedStep("Failed to mouse hover");
		}
	}

	/**
	 * @author Indium Raghuram Description : Date:07/04/21 Modified date: N/A
	 *         Modified by: N/A
	 */
	public Boolean stringNotEmpty(String content) {
		if (content != "") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Indium Raghuram Description : Date:07/05/21 Modified date: N/A
	 *         Modified by: N/A
	 */
	public Boolean ValidateElement_PresenceReturn(Element element) {
		if (element.isElementAvailable(5)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param project
	 * @Description selecting project name while impersonate the role
	 */
	public void selectImpersonateProject(String project) {
		driver.waitForPageToBeReady();
		waitForElement(getSelectProjectTo());
		getSelectProjectTo().selectFromDropdown().selectByVisibleText(project);
		stepInfo("impersonate tab project was selected");
	}

	/**
	 * @author Raghuram.A
	 * @param bgColor
	 * @param subStringPosition
	 * @return
	 * @Description : Modified rgbTohexaConvertor method based on customization
	 */
	public String rgbTohexaConvertorCustomized(String bgColor, int subStringPosition) {
		String s1 = bgColor.substring(subStringPosition);
		bgColor = s1.replace('(', ' ');
		bgColor = bgColor.replace(')', ' ');
		StringTokenizer st = new StringTokenizer(bgColor);
		System.out.println(bgColor);
		int r = Integer.parseInt(st.nextToken(",").trim());
		int g = Integer.parseInt(st.nextToken(",").trim());
		int b = Integer.parseInt(st.nextToken(",").trim());

		Color c = new Color(r, g, b);
		String hex = "#" + Integer.toHexString(c.getRGB()).substring(2).toUpperCase();

		stepInfo("RGB color " + bgColor + " is converted to hex value -" + hex);
		return hex;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param securityGroup
	 * @Description select security group on impersonate tab
	 */
	public void selectImpersonateSecurityGroup(String securityGroup) {
		driver.waitForPageToBeReady();
		waitForElement(getSelectSecurityGroup());
		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroup);
		driver.waitForPageToBeReady();
		stepInfo("impersonate tab security group was selected");
	}

	/**
	 * @author Vijaya.Rani
	 */
	public void impersonateSAtoDA() {
		try {
			driver.waitForPageToBeReady();
			waitForElement(getSignoutMenu());
			getSignoutMenu().waitAndClick(10);
			waitForElement(getChangeRole());
			getChangeRole().waitAndClick(5);
			waitForElement(getSelectRole());
			getSelectRole().selectFromDropdown().selectByVisibleText("Domain Administrator");
			waitForElement(getAvlDomain());
			Thread.sleep(3000);
			getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
			Thread.sleep(3000);
			waitForElement(getSaveChangeRole());
			getSaveChangeRole().waitAndClick(5);
			System.out.println("Impersnated from SA to DA");
			UtilityLog.info("Impersnated from SA to DA");

			if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
				try {
					getGlobalMessagePopUpClose().waitAndClick(5);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @return current logined user role
	 * @Description get current logined user role
	 */
	public String getCurrentLoginedUserRole() {
		return getLoginedUserRole().getText().trim();
	}

	public void impersonateSAtoRMU(String projectName) throws InterruptedException {
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait60);
		getChangeRole().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait60);
		getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		Thread.sleep(3000);
		getAvlProject().selectFromDropdown().selectByVisibleText(projectName);
		Thread.sleep(3000);
		getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
		getSaveChangeRole().Click();
		System.out.println("Impersnated from SA to RMU");
		UtilityLog.info("Impersnated from SA to RMU");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * @author Krishna Description : Impersonate Reviewer to PA
	 */
	public void impersonateReviewertoPA(String projectName) {
		try {
			getSignoutMenu().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getChangeRole().Visible();
				}
			}), Input.wait60);
			getChangeRole().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectRole().Visible();
				}
			}), Input.wait60);
			getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");

			Thread.sleep(1000);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAvlDomain().Visible();
				}
			}), Input.wait30);
			getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

			Thread.sleep(1000);

			getAvlProject().selectFromDropdown().selectByVisibleText(projectName);
			Thread.sleep(1000);

			getSaveChangeRole().waitAndClick(5);
			System.out.println("Impersnated from Reviewer to PA");

			UtilityLog.info("Impersnated from Reviewer to PA");

			if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
				try {
					getGlobalMessagePopUpClose().waitAndClick(5);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}
	}

	public void impersonateSAtoPA(String projectName) throws InterruptedException {
		getSignoutMenu().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getChangeRole().Visible();
			}
		}), Input.wait30);
		getChangeRole().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRole().Visible();
			}
		}), Input.wait30);
		getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAvlDomain().Visible();
			}
		}), Input.wait30);
		getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);

		Thread.sleep(3000);

		getSelectProjectTo().selectFromDropdown().selectByVisibleText(projectName);

		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from SA to PA");
		UtilityLog.info("Impersnated from SA to PA");

		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public void copyandPasteString(String textToCopy, Element element) {
		Actions action = new Actions(driver.getWebDriver());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection str = new StringSelection(textToCopy);
		clipboard.setContents(str, null);

		waitForElement(element);
		element.waitAndClick(5);
		element.Clear();
		action.keyDown(Keys.CONTROL);
		action.sendKeys("v");
		action.keyUp(Keys.CONTROL);
		action.build().perform();

	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/28/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @param dataPair - hash map data to print
	 */
	public void printHashMapDetails(HashMap<String, Integer> dataPair) {
		// Using For Each loop to print HashMap
		stepInfo("--------------------------------");
		for (String key : dataPair.keySet()) {
			System.out.println(key + " = " + dataPair.get(key));
			stepInfo(key + " = " + dataPair.get(key));
		}
		stepInfo("--------------------------------");
	}

	/**
	 * @author Raghuram.A
	 * @Description : only for single data ( modification to be done based on future
	 *              req )
	 * @param colllectionData
	 * @param expectedKey
	 * @param additional
	 * @return
	 */
	public String returnKey(HashMap<String, String> colllectionData, String expectedKey, Boolean additional) {
		String dataName = null;
		// Invoke keySet() on the HashMap object to get the keys as a set
		Set<String> keys = colllectionData.keySet();
		if (expectedKey.equalsIgnoreCase("")) {
			for (String key : keys) {
				System.out.println(key);
				dataName = key;
			}
		}
		return dataName;
	}

	/**
	 * @author Indium-Baskar date: 25/07/2022 Modified date: 25/07/2022
	 * @Description:This method used to pass length character
	 * @param size passing count
	 */
	public String passingCharacterBasedOnSize(int size) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(4000);
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	/**
	 * @author Indium-Baskar date: 25/07/2022 Modified date: 25/07/2022
	 * @Description:This method used to pass length character with hyphen,underscore
	 *                   and space.
	 * @param size passing count
	 */
	public String passingCharacterUsingCombination(int size) {
		char[] chars = "abcde fghi-jklmnopq_rstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(4000);
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	/**
	 * @author Indium-Baskar date: 28/07/2022 Modified date: 2/07/2022
	 * @Description:This method used check to check dropdown value exists based on
	 *                   passing string in parameter
	 * @param dropDownElement
	 * @param dropDownValue
	 */
	public boolean dropDownValueCheck(Element dropDownElement, String dropDownValue) {
		String valueBeingChecked = dropDownValue;
		boolean flag = false;
		WebElement dropdown = dropDownElement.getWebElement();
		Select DrpDwnSel = new Select(dropdown);
		List<WebElement> DrpDwnList = DrpDwnSel.getOptions();
		for (WebElement webElement : DrpDwnList) {
			if (webElement.getText().contains(valueBeingChecked)) {
				passedStep(dropDownValue + "value displayed in dropdown");
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 8/8/22
	 * @param comparisionType
	 * @param expectedDate
	 * @param actualDate
	 * @description : date Comparision
	 */
	public void dateComparision(String comparisionType, Date expectedDate, Date actualDate, Date toDate) {

		// BEFORE comparison
		if (comparisionType.equalsIgnoreCase("Before")) {
			printResutInReport(actualDate.before(expectedDate),
					"actualDate : " + actualDate + " comes before expected date : " + expectedDate,
					"actualDate : " + actualDate + " comes after expected date : " + expectedDate, "Pass");
		}

		// AFTER comparison
		else if (comparisionType.equalsIgnoreCase("After")) {
			printResutInReport(actualDate.after(expectedDate),
					"actualDate : " + actualDate + " comes after expected date : " + expectedDate,
					"actualDate : " + actualDate + " comes before expected date : " + expectedDate, "Pass");
		}

		// ON comparison
		else if (comparisionType.equalsIgnoreCase("On")) {
			printResutInReport(actualDate.equals(expectedDate),
					"actualDate : " + actualDate + " matches with the expectedDate : " + expectedDate,
					"actualDate : " + actualDate + " doesn't matches with the expectedDate : " + expectedDate, "Pass");
		}

		// BETWEEN comparison
		else if (comparisionType.equalsIgnoreCase("Between")) {
			if (actualDate.equals(expectedDate) || actualDate.equals(toDate)) {
				passedStep("Both dates are equal");
			} else {
				printResutInReport(actualDate.before(toDate) && actualDate.after(expectedDate),
						"Actual date lies between the From " + expectedDate + " and to " + toDate + " range",
						"Actual date doesn't lies between the From" + expectedDate + " and to " + toDate + " range",
						"Pass");
			}
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 8/8/22
	 * @param comparisionType
	 * @param expectedDate
	 * @param actualDate
	 * @description : Date Comparision return
	 */
	public Boolean dateComparisionReturn(String comparisionType, Date expectedDate, Date actualDate, Date toDate) {

		Boolean result = false;

		// BEFORE comparison
		if (comparisionType.equalsIgnoreCase("Before")) {
			if (actualDate.before(expectedDate)) {
				result = true;
			}
		}

		// AFTER comparison
		else if (comparisionType.equalsIgnoreCase("After")) {
			if (actualDate.after(expectedDate)) {
				result = true;
			}
		}

		// ON comparison
		else if (comparisionType.equalsIgnoreCase("On")) {
			if (actualDate.equals(expectedDate)) {
				result = true;
			}
		}

		// BETWEEN comparison
		else if (comparisionType.equalsIgnoreCase("Between")) {

			if (actualDate.equals(expectedDate) || actualDate.equals(toDate)) {
				result = true;
			} else if (actualDate.before(toDate) && actualDate.after(expectedDate)) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * @author Raghuram.A
	 * @param comparisionType
	 * @param actualDate
	 * @param expectedDate
	 * @param toDate
	 * @param status
	 * @description : date Comparision Message
	 */
	public void dateComparisonMsg(String comparisionType, String actualDate, String expectedDate, String toDate,
			Boolean status) {
		// BEFORE comparison
		if (comparisionType.equalsIgnoreCase("Before")) {
			printResutInReport(status, "actualDate : " + actualDate + " comes before expected date : " + expectedDate,
					"actualDate : " + actualDate + " comes after expected date : " + expectedDate, "Pass");
		}

		// AFTER comparison
		else if (comparisionType.equalsIgnoreCase("After")) {
			printResutInReport(status, "actualDate : " + actualDate + " comes after expected date : " + expectedDate,
					"actualDate : " + actualDate + " comes before expected date : " + expectedDate, "Pass");
		}

		// ON comparison
		else if (comparisionType.equalsIgnoreCase("On")) {
			printResutInReport(status,
					"actualDate : " + actualDate + " matches with the expectedDate : " + expectedDate,
					"actualDate : " + actualDate + " doesn't matches with the expectedDate : " + expectedDate, "Pass");
		}

		// BETWEEN comparison
		else if (comparisionType.equalsIgnoreCase("Between")) {
			printResutInReport(status,
					"Actual date lies between the From " + expectedDate + " and to " + toDate + " range",
					"Actual date doesn't lies between the From" + expectedDate + " and to " + toDate + " range",
					"Pass");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param element
	 * @param cTime
	 * @return
	 * @description : ValidateElement_StatusReturn
	 */
	public Boolean ValidateElement_StatusReturn(Element element, int cTime) {
		if (element.isElementAvailable(cTime)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Raghuram.A
	 * @param textMsg
	 * @param customMsg
	 * @throws InterruptedException
	 */
	public void notificationSelection(String textMsg, Boolean customMsg) throws InterruptedException {
		waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);
		if (customMsg) {
			// To be added based on future requirement
		} else {
			clickFirstBackRoundTask();
		}
		getBullHornIcon().waitAndClick(2);
	}

	/**
	 * @author Raghuram.A
	 * @param fileName
	 * @param expectedFormat
	 * @description : validate file format
	 */
	public void validateFileFormat(String fileName, String expectedFormat) {

		String actualfileName = FilenameUtils.getBaseName(fileName);
		String fileFormat = FilenameUtils.getExtension(fileName);

		// Comparision
		stepInfo("File Name : " + fileName);
		textCompareEquals(fileFormat, expectedFormat, "File format is as Expected : " + fileFormat,
				"File format is not as Expected : " + fileFormat);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param element
	 * @param n
	 * @Description this method is used for move to element and perform action
	 *              click, this method used action not performed in not normal click
	 *              method.
	 */
	public void moveWaitAndClick(Element element, int n) {

		Actions action = new Actions(driver.getWebDriver());
		for (int i = 0; i < n; i++) {
			try {
				action.moveToElement(element.getWebElement()).click().perform();
				break;
			} catch (Exception e) {
				waitTime(1);

			}
		}
	}

	/*
	 * @author Indium-Baskar
	 * 
	 */
//	This method used to login As SA,PA,RMU
//	Impersonate to SA-->rmu,rev,pa & PA-->rmu,rev &RMU-->rev
	public void credentialsToImpersonateAsPARMUREV(String roll, String impersonate) {
		try {
			switch (impersonate) {
			case "pa":
				if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("pa")) {
					driver.waitForPageToBeReady();
					impersonateSAtoPA();
				}
				if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonateDAtoRMU();
				}

			case "rmu":
				if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonateSAtoRMU();
				}
				if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
					driver.waitForPageToBeReady();
					impersonatePAtoRMU();
				}

			case "rev":
				if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
					driver.waitForPageToBeReady();
					impersonateSAtoReviewer();
				}
				if (roll.equalsIgnoreCase("rmu") && impersonate.equalsIgnoreCase("rev")) {
					driver.waitForPageToBeReady();
					impersonateRMUtoReviewer();
				}
			}
		} catch (Exception E) {
			E.printStackTrace(pw);
			UtilityLog.info(sw.toString());
		}

	}

	/**
	 * @author Brundha.T
	 * @param ele
	 * @param CompareString
	 * @Description gettext String comparing method
	 */
	public void validatingGetTextElement(Element ele, String CompareString) {
		String ActualString = ele.getText();
		stepInfo("The actual string is"+ActualString);
		System.out.println("The actual string is"+ActualString);
		System.out.println("The CompareString is"+CompareString);
		stepInfo("The CompareString is"+CompareString);
		driver.waitForPageToBeReady();
		compareTextViaContains(ActualString, CompareString, "" + ActualString + " is displayed",
				"" + ActualString + " not displayed");

	}

	/**
	 * @Author jeevitha
	 * @Description : compare list via contains & trim Space
	 * @param baseList
	 * @param compareList
	 * @return
	 */
	public boolean compareListViaContainsTrimSpace(List<String> baseList, List<String> compareList) {
		boolean flag = false;

		for (int i = 0; i < baseList.size(); i++) {

			String source = baseList.get(i).trim().replace(" ", "");
			String compare = compareList.get(i).trim().replace(" ", "");

			System.out.println("Source : " + source);
			System.out.println("Compare : " + compare);

			if (source.contains(compare)) {
				Assert.assertEquals(true, true);
				flag = true;
			} else {
				failedStep("Base String doesn't contains compare String");
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param web element
	 * @return tool tip message of the element
	 * @Description hover on the element and get the tool tip message of the element
	 */
	public String getToolTipMsg(Element ele) {
		driver.waitForPageToBeReady();
		waitForElement(ele);
		Actions action = new Actions(driver.getWebDriver());
		action.moveToElement(ele.getWebElement()).build().perform();
		stepInfo("moves hover to the elemet and get tool tip message");
		return ele.GetAttribute("title");
	}

	/**
	 * @author Indium Raghuram Description : Date:09/26/21 Modified date: N/A
	 *         Modified by: N/A
	 */
	public Boolean ValidateElement_AbsenceReturn(Element element) {
		if (element.isElementAvailable(1)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @author sowndarya.velraj
	 * @Description hover on the element and get the tool tip message of the element
	 */
	public void verifyMegaPhoneIconAndBackgroundTasks(boolean bullHorn, boolean viewAll) {
		if (bullHorn) {
			waitForElement(getBullHornIcon());
			getBullHornIcon().waitAndClick(10);
		}

		if (viewAll) {
			waitForElement(getBckTask_SelectAll());
			getBckTask_SelectAll().waitAndClick(20);
		}

		// verify Background Task page
		String url = driver.getUrl();
		String expURL = Input.url + "Background/BackgroundTask";
		softAssertion.assertEquals(expURL, url);
		stepInfo("Navigated to My backgroud task page.");
	}

	/**
	 * @author Jeevitha
	 */
	public void compareListWithOnlyOneString(List<String> source, String compareString, String passMsg, String failMsg)
			throws InterruptedException {
		boolean compare = false;
		for (String actualValue : source) {
			if (actualValue.equalsIgnoreCase(compareString)) {
				compare = true;
				break;
			} else {
				compare = false;

			}
		}
		if (compare) {
			passedStep(passMsg);
		} else {
			failedStep(failMsg);
		}

	}
	
	/**
	 * @author Raghuram.A
	 * @param element
	 * @param splitUsing
	 * @param reqIndex
	 * @return
	 */
	public String getSpecifiedTextViaSplit(Element element, String splitUsing, int reqIndex) {
		String textValue, valueToReturn;
		String[] arrOfStr;

		textValue = element.getText();
		arrOfStr = textValue.split(splitUsing);
		for (String a : arrOfStr) {
			System.out.println(a);
		}
		valueToReturn = arrOfStr[reqIndex].toString();
		System.out.println(valueToReturn);

		return valueToReturn;
	}
	
	public void clearATextBoxValue(Element element) {
		element.waitAndClick(10);
		waitForElement(element);
		element.Clear();

	}
	
	/**
	 * @Author 
	 * @param element
	 * @param attribute
	 * @return
	 */
	public String getCSSValue(Element element,String attribute) {
        String statsColor = null;
        try {
            statsColor = element.GetCssValue(attribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statsColor;
    }
}

package pageFactory;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.Seconds;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
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
	public Element  SelectSearchOption() {
		return driver.FindElementById("txtsearchUser");
	}
	
	public Element  getEditButton() {
		return driver.FindElementByXPath("//a[text()='Edit']");
	}
	public Element  getFunctionalityButton() {
		return driver.FindElementByXPath("//a[contains(text(),'Functionality')] ");
	}
	public Element  UnSelectProductionCheckBox() {
		return driver.FindElementByXPath("//input[@id='UserRights_CanProductions']//following-sibling::i");
	}
	public Element  getSaveBtn() {
		return driver.FindElementById("btnsubmit");
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

//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getCloseSucessmsg().Exists();
//			}
//		}), Input.wait30);
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
		waitTillTextToPresent(getSuccessMsg(), ExpectedMsg);
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
				return getSuccessMsgHeader().Visible();
			}
		}), Input.wait30);
		Assert.assertEquals("Warning !", getSuccessMsgHeader().getText().toString());
		Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
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

		getSelectProjectTo().selectFromDropdown().selectByVisibleText(Input.projectName);
		getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersnated from DA to PA");
		UtilityLog.info("Impersnated from DA to PA");
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

	}

	public void impersonateSAtoRMU() throws InterruptedException {
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
		getSaveChangeRole().Click();
		System.out.println("Impersnated from SA to RMU");
		UtilityLog.info("Impersnated from SA to RMU");
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
	}

	/**
	 * @Author Indium-Sowndarya.Velraj
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
	 * @Author Indium-Sowndarya.Velraj
	 */
	public void passedStep(String message) {
		Reporter.log("<font color='green'>" + message + "</font>");
		UtilityLog.info("Passed step: " + message);

	}

	/**
	 * @Author Indium-Sowndarya.Velraj
	 */

	public void failedStep(String message) {
		Reporter.log("<font color='red'>" + message + "</font>");
		UtilityLog.info("Failed step: " + message);
		softAssertion.assertTrue(false);
		softAssertion.assertAll();
	}

	/**
	 * @Author Indium-Sowndarya.Velraj
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
	 * @Author Indium-Sowndarya.Velraj
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
	 * @author Raghuram A Modified on : 11/12/21 by Raghuram A Description : TO
	 *         handle all impersonate dynamically in one method with "FROM" and "TO"
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
				impersonateDAtoPA();
			} else if (fromRole.equalsIgnoreCase("REV") && toRole.equalsIgnoreCase("PA")) {// impersonate to PA
				impersonateDAtoPA();
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
			System.out.println("Impersnated from DA to RMU");
			UtilityLog.info("Impersnated from DA to RMU");
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
			System.out.println("Impersnated from DA to Reviewer");
			UtilityLog.info("Impersnated from DA to Reviewer");
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
	 * @author Raghuram A Date: 11/11/21 Modified date:N/A Modified by: Description
	 *         : textCompareEquals with Pass and Fail Message
	 */
	public void textCompareEquals(String sourceString, String compreString, String passMsg, String failMessage) {
		try {
			System.out.println("Source String  : " + sourceString);
			System.out.println("Compare String  : " + compreString);

			softAssertion.assertEquals(sourceString, compreString);
			if (sourceString.equals(compreString)) {
				passedStep(passMsg);
			} else if (!sourceString.equals(compreString)) {
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
	public void textCompareNotEquals(String sourceString, String compreString, String passMsg, String failMessage) {
		try {
			System.out.println("Source String  : " + sourceString);
			System.out.println("Compare String  : " + compreString);

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
	 * @author Jayanthi.ganesan
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
				if (text.equals(elementName)) {
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

	public void waitTime(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {

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
		WebElement dropdown=ddElement.getWebElement();
		Select select= new Select(dropdown);
	    WebElement selectedValue =select.getFirstSelectedOption();
	    String selectedoption = selectedValue.getText();
	    System.out.println("Selected element from dropdown: "+ selectedoption);
	    stepInfo("Selected element from dropdown: "+ selectedoption);
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
		
		waitTillElemetToBeClickable(getEditButton());
		driver.waitForPageToBeReady();
		getEditButton().waitAndClick(10);
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
	
	
	
}

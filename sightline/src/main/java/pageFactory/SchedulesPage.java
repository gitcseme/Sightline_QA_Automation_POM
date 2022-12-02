package pageFactory;

import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class SchedulesPage {

	Driver driver;
	SoftAssert softAssertion;
	BaseClass bc ;
	public Element getRunTab() {
		return driver.FindElementByXPath("//*[@id='ui-id-2']");
	}

	public ElementCollection getfindSearchRow() {
		return driver.FindElementsByXPath("//table[@id='GridSchedulerExecutionList']//td[2]");
	}

	public Element getStatusCheck(int row) {
		return driver.FindElementByXPath("//table[@id='GridSchedulerExecutionList']/tbody/tr[" + row + "]/td[5]");
	}

	/*
	 * public Element getDescription(){ return
	 * driver.FindElementById("AnnotationDescription"); } public Element
	 * getSaveBtn(){ return driver.FindElementById("btnAnnotationSave"); }
	 */ public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	// @added by jeevitha
	// Schedule tab
	public Element getEditBtn() {
		return driver.FindElementByXPath("//a[text()='Edit']");
	}

	public Element getSavedSearch_ScheduleTime() {
		return driver.FindElementByXPath("//input[@id='txtOneTimeStartDate']");
	}

	public Element getNameFromScheduled() {
		return driver.FindElementByXPath("(//table[@id='GridSchedulerList']//tbody//tr)[1]//td[2]");
	}

	public Element getScheduleTime(String searchName) {
		return driver.FindElementByXPath("//table[@id='GridSchedulerList']//tbody//tr//td[text()='" + searchName
				+ "']//following-sibling::td[2]");

	}

	public Element getoldScheduleTime(String searchName) {
		return driver.FindElementByXPath(
				"//table[@id='GridScheduler']//tbody//tr//td[text()='" + searchName + "']//following-sibling::td[2]");

	}

	public Element getExecutionHeader() {
		return driver.FindElementByXPath("//th[text()='Execution Date/Time(UTC)']");

	}

	public Element getSearchNames(int row) {
		return driver.FindElementByXPath("//table[@id='GridSchedulerExecutionList']/tbody/tr[" + row + "]/td[2]");

	}

	public int getCount() {
		return driver.FindElementsByXPath("//table[@id='GridSchedulerExecutionList']/tbody/tr/td[2]").size();
	}

	public ElementCollection getSearchNamesfromList(String searchName) {
		return driver.FindElementsByXPath(
				"//table[@id='GridSchedulerExecutionList']/tbody//td[text()='" + searchName + "']");

	}
	public Element getScheduleTime(int i) {
		return driver.FindElementByXPath("(//table[@id='GridSchedulerList']//tbody//tr//td[text()='Review Results Report']//following-sibling::td[2])["+i+"]");

	}
	public Element getRow_Reports_Runtab(int i) {
		return driver.FindElementByXPath("(//table[@id='GridSchedulerExecutionList']//td[text()='Review Results Report'])[\"+i+\"]");

	}

	// Annotation Layer added successfully
	public SchedulesPage(Driver driver) {

		this.driver = driver;
//		this.driver.getWebDriver().get(Input.url + "Scheduler/ManageScheduler");
		driver.waitForPageToBeReady();
		softAssertion = new SoftAssert();
		 bc = new BaseClass(driver);
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	/**
	 * modified by : Raghram A modified on : 11/23/21 added : sortStatus
	 * 
	 * @param taskName
	 */
	public void checkStatusComplete(final String taskName) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRunTab().Visible();
			}
		}), Input.wait30);
		getRunTab().Click();
		boolean nextPage = true;
		boolean found = false;
		String sortStatus;

		do {
			getExecutionHeader().waitAndClick(5);
			System.out.println("Clicked");
			driver.waitForPageToBeReady();
			sortStatus = getExecutionHeader().GetAttribute("class");
			System.out.println(sortStatus);
		} while (!sortStatus.equals("sorting_desc"));

		while (nextPage) {
			int row = 1;
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getfindSearchRow().Visible();
				}
			}), Input.wait30);
			for (WebElement ele : getfindSearchRow().FindWebElements()) {

				if (ele.getText().trim().equals(taskName)) {
					nextPage = false;
					found = true;
					softAssertion.assertTrue(getStatusCheck(row).getText().trim().equalsIgnoreCase("COMPLETE"));
					System.out.println(taskName + " Scheduled run is completed with the status 'COMPLETE'!");
					UtilityLog.info(taskName + " Scheduled run is completed with the status 'COMPLETE'!");
					return;
				}

				row++;

			}
			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
				System.out.println("Not found!!!!!!");
				UtilityLog.info("Not found!!!!!!");
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}

		softAssertion.assertAll();
	}

	/**
	 * @Author Jeevitha.R Description: Edit the Scheduled Saved search
	 * @param searchName
	 * @param number
	 * @throws ParseException
	 * 
	 */
	public void editScheduledSaveSearch(String searchName, int number) throws ParseException {
		SavedSearch saveSearch = new SavedSearch(driver);
		driver.getWebDriver().get(Input.url + "Scheduler/ManageScheduler");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEditBtn().Visible();
			}
		}), Input.wait30);
		getEditBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ScheduleTime().Visible();
			}
		}), Input.wait60);
		getSavedSearch_ScheduleTime().SendKeys(saveSearch.scheduleTimePlusMinutes(number));

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		saveSearch.getSaveSchedulerBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait30);
		System.out.println("Saved search " + searchName + " is scheduled to run in " + number + "minutes");
		bc.stepInfo("Saved search " + searchName + " is scheduled to run in " + number + "minutes");
	}

	/**
	 * @author Jeevitha Description: Waits until the Scheduled Time Is Completed
	 * @param searchName
	 * @throws InterruptedException
	 */
	public void verifyScheduledTime(String searchName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Scheduler/ManageScheduler");

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getoldScheduleTime(searchName).isDisplayed();
			}
		}), Input.wait30);
		String str = getScheduleTime(searchName).getText();
		while (str != "") {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getScheduleTime(searchName).Selected();
				}
			}), Input.wait60);

			driver.Navigate().refresh();
			driver.Manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.waitForPageToBeReady();
			str = getScheduleTime(searchName).getText();
		}

	}
	
/**
 * @author Jayanthi.ganesan
 * @param i
 * @throws InterruptedException
 */
	public void verifyScheduledTime_Reports(int i) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Scheduler/ManageScheduler");

		driver.waitForPageToBeReady();
		
		String str = getScheduleTime(i).getText();
		while (str != "") {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getScheduleTime(i).Selected();
				}
			}), Input.wait60);

			driver.Navigate().refresh();
			driver.Manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.waitForPageToBeReady();
			str = getScheduleTime(i).getText();
		}

	}
	/**
	 * @author Jayanthi.ganesan
	 */
	
	public void checkStatusComplete_reports() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRunTab().Visible();
			}
		}), Input.wait30);
		getRunTab().Click();
		String sortStatus;

		do {
			getExecutionHeader().waitAndClick(5);
			System.out.println("Clicked");
			driver.waitForPageToBeReady();
			sortStatus = getExecutionHeader().GetAttribute("class");
			System.out.println(sortStatus);
		} while (!sortStatus.equals("sorting_desc"));
				if (getRow_Reports_Runtab(1).isDisplayed()) {
					softAssertion.assertTrue(getStatusCheck(1).getText().trim().equalsIgnoreCase("COMPLETE"));
					System.out.println( "Scheduled run is completed with the status 'COMPLETE'!");
				}

		softAssertion.assertAll();
		bc.passedStep("Scheduled run is completed with the status 'COMPLETE'!");
	}

	
}
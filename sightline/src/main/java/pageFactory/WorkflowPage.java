package pageFactory;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class WorkflowPage {

	Driver driver;
	BaseClass baseClass;
	SoftAssert softAssertion;
	MiniDocListPage miniDocList;
	List<String> originalOrderedList;
	List<String> afterSortList;

	public Element getWorkFlow_CreateNewWorkFlowBtn() {
		return driver.FindElementById("btnCreate");
	}

	public Element getWorkFlow_WorkFlowName() {
		return driver.FindElementByCssSelector("input#workflowName");
	}

	public Element getWorkFlow_Desc() {
		return driver.FindElementByCssSelector("textarea#workflowDesc");
	}

	public Element getWorkFlow_DescPage_Next() {
		return driver.FindElementByCssSelector("a#btnDescNext");
	}

	public Element getWorkFlow_Source_Next() {
		return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)");
	}

	public Element getWorkFlow_SourceID() {
		return driver.FindElementByCssSelector("input#SavedSearchID");
	}

	public Element getWorkFlow_Filters_Next() {
		return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)");
	}

	public Element getWorkFlow_Family_Next() {
		return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)");
	}

	public Element getWorkFlow_FolderSelector1() {
		return driver.FindElementByCssSelector("input#id_radio1 + i");
	}

	public Element getWorkFlow_AssignmentsSelector() {
		return driver.FindElementByCssSelector("input#id_radio2 + i");
	}

	public Element getWorkFlow_SelectAssignment(String AssName) {
		return driver.FindElementByXPath(".//*[@id='jsTreeAssignment']//a[contains(.,'" + AssName + "')]");
	}

	public Element getWorkFlow_SelectAssignment() {
		return driver.FindElementByXPath(".//*[@id='jsTreeAssignment']//a");
	}

	public Element getWorkFlow_Actions_Next() {
		return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)");
	}

	public Element getWorkFlow_Time1() {
		return driver.FindElementByCssSelector("input#txtTime1");
	}

	public Element getWorkFlow_day(String day) {
		return driver.FindElementByCssSelector("input[name*='" + day + "'][type='hidden'] + i");
	}

	public Element getWorkFlow_StopDateOfCurrentWorkFlow() {
		return driver.FindElementByCssSelector("input#StopDateOfCurrentWorkFlow");
	}

	public Element getWorkFlow_Schedules_Next() {
		return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)");
	}

	public Element getWorkFlow_Notification_AddAllUsers() {
		return driver.FindElementByCssSelector("a#btnAllRightMapping");
	}

	public Element getWorkFlow_Notifications_Next() {
		return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)");
	}

	public Element geWorkFlow_Summary_Save() {
		return driver.FindElementByCssSelector("div#divCentralPanel a[onclick='SaveWorkFlow()']");
	}

	public Element getWorkFlow_SelectWorkflow(String WFName) {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody//td[contains(.,'" + WFName + "')]");
	}
	public Element getWorkFlow_SelectWorkflow1() {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]");
	}

	public Element getWorkFlow_SelectWorkStatus(String WFName, int i) {
		return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody//td[contains(.,'" + WFName
				+ "')]//following-sibling::td//..//td[" + i + "]");
	}

	public Element getWorkFlow_WorkFlowID(String WFName) {
		return driver.FindElementByXPath(
				".//*[@id='dt_basic']/tbody//td[contains(.,'" + WFName + "')]//preceding-sibling::td");
	}

	public Element getWorkFlow_WorkFlowNotificationStatus() {
		return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[1]/td[8]");
	}

	public Element getWorkFlow_ActionDropdown() {
		return driver.FindElementByCssSelector("div.clearfix button[type='button'][data-toggle='dropdown']");
	}

	public Element getWorkFlow_RunWorkflowNow() {
		return driver.FindElementByCssSelector("a#lnkRunWorkflow");
	}

	public Element getWorkFlow_RunWorkflowNow_YesButton() {
		return driver.FindElementByCssSelector("button#bot1-Msg1");
	}

	public Element getWorkFlow_ActionDeleteWorkFlow() {
		return driver.FindElementById("lnkDeleteWorkflow");
		//return driver.FindElementByCssSelector("a#lnkDeleteWorkflow");
	}

	public Element getWorkFlow_ActionDeleteWorkFlow_YesButton() {
		return driver.FindElementByCssSelector("button#bot1-Msg1");
	}

	public Element getWorkFlow_NextFilters() {
		return driver.FindElementByXPath(".//*[@id='divCentralPanel']//a[contains(.,'Next')]");
	}

	
	// public Element getWorkFlow_Desc(){ return
	// driver.FindElementByCssSelector("textarea#workflowDesc"); }
	public ElementCollection getElements() {
		return driver.FindElementsByXPath("//*[@class='a-menu']");
	}

	// Check status
	public Element getStatusTable() {
		return driver.FindElementByXPath("//*[@id='dt_basic']");
	}

	public ElementCollection getWorkflowNames() {
		return driver.FindElementsByXPath("//*[@id='dt_basic']/tbody/tr/td[2]");
	}

	public Element getStatus(int row) {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[" + row + "]/td[3]");
	}

	public Element getWorkFlowPaginationNextButton() {
		return driver.FindElementByCssSelector("li[class='paginate_button next'] a");
	}

	public Element getWorkFlowIdPassing() {
		return driver.FindElementByCssSelector("#txtWorkflowid");
	}

	public Element getApplyFilter() {
		return driver.FindElementByCssSelector("#btnworkflowfilter");
	}

	public Element getStatusDropDown() {
		return driver.FindElementByCssSelector("#WorkFlowStatus");
	}

	public Element getResetButton() {
		return driver.FindElementByCssSelector("#btnworkflowfilterreset");
	}

	public ElementCollection getTableHeader() {
		return driver.FindElementsByXPath("//table[@id='dt_basic']/thead/tr/th");
	}

	public Element getTableRowData(String WFName, int i) {
		return driver.FindElementByXPath(
				"//td[contains(.,'" + WFName + "')]/ancestor::table[@id='dt_basic']/tbody//td[" + i + "]");

	}

	public Element getFirstFamilyOptions() {
		return driver.FindElementByXPath("//input[@id='FirstFamily']//parent::label");
	}

	public Element getHistoryButton() {
		return driver.FindElementByCssSelector("#lnkviewhistory");
	}

	public Element getFolderSelector(String folderName) {
		return driver.FindElementByXPath(".//*[@id='jsTreeFolder']//a[contains(.,'" + folderName + "')]");
	}

	public Element getNextLinkButton() {
		return driver.FindElementByXPath("//a[text()='Next']");
	}

	public ElementCollection getAssgnPaginationCount() {
		return driver.FindElementsByCssSelector("li[class*='paginate_button '] a");
	}

	public Element getDocCount(String wfName) {
		return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody//tr[contains(.,'" + wfName + "')]//td[6]");
	}

	public Element getActionDocCount(String wfName) {
		return driver
				.FindElementByXPath(".//*[@id='dt_workflowhistory']/tbody//tr[contains(.,'" + wfName + "')]//td[7]");
	}

	public Element getHistoryPopUpClose() {
		return driver.FindElementByCssSelector(".ui-dialog-titlebar-close");
	}

	public Element getSaveLinkButton() {
		return driver.FindElementByXPath("//a[text()='Save']");
	}

	public Element getDeletePopUpMessage() {
		return driver.FindElementByXPath("//p[@class='pText']");
	}

	public ElementCollection getWorkFlowIds() {
		return driver.FindElementsByXPath("// *[@id='dt_basic']//tr['row']/td[1]");
	}

	public Element getWorkFlowIdTab() {
		return driver.FindElementByXPath("//*[@id='dt_basic']/thead/tr/th[1]");
	}

	public Element getBackTOManageBtn() {
		return driver.FindElementByXPath("//a[text()='Go Back to Manage Workflows']");
	}

	public Element getSaveBtn() {
		return driver.FindElementByXPath("//a[text()='Save']");
	}

	public Element getCreatedByUserDropDown() {
		return driver.FindElementByXPath("//select[@id='WorkFlowCreatedBy']");

	}

	public Element getWorkFlow_AssignedUsers() {
		return driver.FindElementById("AssignedUser");

	}

	public ElementCollection getTableColumnData(int i) {
		return driver.FindElementsByXPath("//table[@id='dt_basic']/tbody//td[" + i + "]");

	}

	public Element getFiltertextbox_WFid() {
		return driver.FindElementByXPath("//input[@id='txtWorkflowid']");

	}

	public Element getApplyFilterBtn() {
		return driver.FindElementByXPath("//a[@id='btnworkflowfilter']");

	}

	public Element getEnabledHistoryBtn() {
		return driver.FindElementByXPath("//a[@id='lnkviewhistory' and @class='']");

	}

	public ElementCollection getActionHeader() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHead']//tr/th[@class='sorting']");

	}

	public Element getSelectedEnabledStateRadioBtn(int j, int headerIndex, int enabledIndex) {
		return driver.FindElementByXPath(
				"(//tr[" + j + "]//td[" + headerIndex + "]//label[" + enabledIndex + "]/input[@checked=''])");
	}

	public Element getTableHeader(int i) {
		return driver.FindElementByXPath("(//table[@id='dt_basic']/thead/tr/th)[" + i + "]");
	}

	public Element getEmptyTableMEssage() {
		return driver.FindElementByXPath("//tbody/tr/td[@class='dataTables_empty']");
	}

	public Element getStateDropDown() {
		return driver.FindElementByXPath("//select[@id='WorkFlowState']");
	}

	public Element getStatusCompleteOption() {
		return driver.FindElementByXPath("//option[@value='COMPLETED']");

	}

	public Element getSummeryTabDescription() {
		return driver.FindElementByXPath("//*[@id='accordion1']/div/div[1]/h4/a");

	}

	public Element getSummeryTabSource() {
		return driver.FindElementByXPath("//*[@id='accordion2']/div/div[1]/h4/a");

	}

	public Element getSummeryTabFamilyOption() {
		return driver.FindElementByXPath("//*[@id='accordion4']/div/div[1]/h4/a");

	}

	public Element getSummeryTabActionTab() {
		return driver.FindElementByXPath("//*[@id='accordion5']/div/div[1]/h4/a");

	}

	public Element getSummeryTabScheduleOption() {
		return driver.FindElementByXPath("//*[@id='accordion6']/div/div[1]/h4/a");

	}

	public Element getSummeryTabNotificationOption() {
		return driver.FindElementByXPath("//*[@id='accordion7']/div/div[1]/h4/a");

	}

	// added by jayanthi
	public Element getSummaryTab_WFName() {
		return driver.FindElementByXPath("//label[contains(text(),'Workflow Name:')]/following-sibling::div");
	}

	public Element getSummaryTab_WFDesc() {
		return driver.FindElementByXPath("//label[contains(text(),'Workflow Description:')]/following-sibling::div");
	}

	public Element getAction_EditWF() {
		return driver.FindElementById("lnkEditWorkflow");
	}

	// Added by Vijaya.Rani
	public Element getSummaryTab_SourceAssignment() {
		return driver.FindElementByXPath("//label[contains(text(),'Assignments:')]/following-sibling::div");
	}

	public Element getSummaryTab_SourceSavedSearch() {
		return driver.FindElementByXPath("//label[contains(text(),'Saved Search:')]/following-sibling::div");
	}
	
	//Added by Baskar
	public Element getActionTab_QueryMessage() {
		return driver.FindElementByXPath("//h2[contains(normalize-space(.),'Define What Actions')]");
	}
	public Element getActionTab_PageMessage() {
		return driver.FindElementByXPath("//p[contains(normalize-space(.),'On this page you')]");
	}

	//Added by Vijaya.Rani 
	public Element getSummaryTab_FamilyOptionAssignment() {
		return driver.FindElementByXPath("//label[contains(text(),'Member 1:')]/following-sibling::div");
	}
	
	public Element getSecondFamilyOptions() {
		return driver.FindElementByXPath("//input[@id='SecondFamily']//parent::label");
	}
	public Element getFamilyOption_Second() {
		return driver.FindElementByXPath("//span[normalize-space(.)='Only act upon a candidate document if all of its family members are either (1) in the specified Source Universe OR (2) are already present in the target Assignment/Folder. This option will act upon all of the family members in the Source Universe (not those already in the target Assignment/Folder) in order to unite the whole family unit in the target Assignment/Folder.']");
	}
	public Element getFamilyOption_First() {
		return driver.FindElementByXPath("//span[normalize-space(.)='Only act upon a candidate document if all of its family members are also present in the selected Source Universe. If this condition is met, then action the entire family unit along with the candidate document. If the condition is not met, do not action the family unit and do not action the candidate document.']");
	}
	public Element getNotifyUserName(String userName) {
		return driver.FindElementByXPath("//select[@id='UnNotifiedUser']//option[text()='"+userName+"']");
	}
	
	public Element getNotify_ButtonRight() {
		return driver.FindElementById("btnRightMapping");
	}
	
	public Element getSummaryTab(String tabName) {
		return driver.FindElementByXPath("//i[@class='fa fa-lg fa-angle-up pull-right']//parent::a[text()='"+tabName+"']");
	}
	public Element getNotified_EmailUserId() {
		return driver.FindElementByXPath("//label[@for='WF_Notifications_AssignedUsers']");
	}
	
	
	public WorkflowPage(Driver driver) {

		this.driver = driver;
		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		baseClass = new BaseClass(driver);
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);
	}

	public boolean checkStatusComplete(final String workflowName) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getStatusTable().Visible();
			}
		}), Input.wait30);

		boolean nextPage = true;
		boolean found = false;

		while (nextPage) {
			int row = 1;

			for (WebElement ele : getWorkflowNames().FindWebElements()) {

				if (ele.getText().trim().equals(workflowName)) {
					nextPage = false;
					found = true;
					System.out.println(row);
					if (getStatus(row).getText().trim().equalsIgnoreCase("COMPLETE"))
						;
					{
						System.out.println(workflowName + " Scheduled run is completed with the status 'COMPLETE'!");
						return true;
					}
				}

				row++;

			}
			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
				// System.out.println("Not found!!!!!!");
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}
		return false;

	}

	public void CreateWFwithAssignments(int SavedSearchID, final String WFName, String WFDesc,
			final String AssignmentName, int purehit) throws ParseException, InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_CreateNewWorkFlowBtn().Visible();
			}
		}), Input.wait60);

		getWorkFlow_CreateNewWorkFlowBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_WorkFlowName().Visible();
			}
		}), Input.wait30);
		getWorkFlow_WorkFlowName().SendKeys(WFName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Desc().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Desc().SendKeys(WFDesc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_DescPage_Next().Visible();
			}
		}), Input.wait30);
		getWorkFlow_DescPage_Next().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_SourceID().Visible();
			}
		}), Input.wait30);
		getWorkFlow_SourceID().SendKeys(Integer.toString(SavedSearchID));

		Thread.sleep(10000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Source_Next().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Source_Next().Click();

		Thread.sleep(10000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Filters_Next().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Filters_Next().Click();

		Thread.sleep(10000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Family_Next().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Family_Next().Click();

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_AssignmentsSelector().Visible();
			}
		}), Input.wait30);
		getWorkFlow_AssignmentsSelector().Click();

		driver.scrollingToBottomofAPage();

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_SelectAssignment().Visible();
			}
		}), Input.wait30);
		getWorkFlow_SelectAssignment(AssignmentName).Click();

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Actions_Next().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Actions_Next().Click();

		// ******* Schedules tab *************************************
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

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date d = df.parse(Time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, 1);

		DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
		String weekdays[] = dfs.getWeekdays();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String nameOfDay = weekdays[day];
		System.out.println(nameOfDay);
		UtilityLog.info(nameOfDay);

		// get next day date
		cal.add(Calendar.DATE, 1);
		String newTime = df.format(cal.getTime());
		System.out.println(newTime);
		UtilityLog.info(newTime);
		String s[] = newTime.split(" ");
		System.out.println(s[0]); // next day date
		UtilityLog.info(s[0]);
		System.out.println(s[1]); // current time + 1 min
		UtilityLog.info(s[01]);
		System.out.println();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Time1().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Time1().SendKeys(s[1]);

		// get day of week

		// Select days for schedules -
		getWorkFlow_day(nameOfDay).Click();

		getWorkFlow_StopDateOfCurrentWorkFlow().SendKeys(s[0]);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Schedules_Next().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Schedules_Next().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Notification_AddAllUsers().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Notification_AddAllUsers().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_Notifications_Next().Visible();
			}
		}), Input.wait30);
		getWorkFlow_Notifications_Next().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geWorkFlow_Summary_Save().Visible();
			}
		}), Input.wait30);
		geWorkFlow_Summary_Save().Click();

		/*
		 * BaseClass bc = new BaseClass(driver); int Bgcount = bc.initialBgCount();
		 * System.out.println(Bgcount);
		 */

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getWorkFlow_SelectWorkflow(WFName).Visible();
			}
		}), Input.wait30);

		for (int i = 0; i < 30; i++) {
			try {
				if (checkStatusComplete(WFName))
					break;
				// getWorkFlow_SelectWorkStatus(WFName).Displayed();
			} catch (Exception e) {
				Thread.sleep(5000);
				driver.Navigate().refresh();
			}
		}

		/*
		 * String WrkflowID = getWorkFlow_WorkFlowID(WFName).getText();
		 * System.out.println(WrkflowID);
		 */

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * bc.initialBgCount() == Bgcount+1 ;}}), Input.wait120+Input.wait60);
		 * System.out.println(bc.initialBgCount());
		 */

		/*
		 * BaseClass base = new BaseClass(driver); base.BckTaskClick();
		 * 
		 * Assert.assertEquals(getWorkFlow_WorkFlowNotificationStatus().getText().
		 * toString(), "Completed");
		 */

		/*
		 * //verify documents in assignment Thread.sleep(25000); AssignmentsPage asspage
		 * = new AssignmentsPage(driver);
		 * 
		 * 
		 * driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * asspage.getNumberOfAssignmentsToBeShown().Visible() ;}}), Input.wait60);
		 * 
		 * asspage.getNumberOfAssignmentsToBeShown().selectFromDropdown().
		 * selectByVisibleText("100");
		 * 
		 * driver.scrollingToBottomofAPage();
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * asspage.getSelectAssignment(AssignmentName).Visible() ;}}), Input.wait60);
		 * 
		 * int DocsinAssgn =
		 * Integer.parseInt(asspage.getSelectAssignmentDocCount(AssignmentName).getText(
		 * )); System.out.println(DocsinAssgn);
		 * 
		 * Assert.assertEquals(DocsinAssgn,purehit );
		 */

	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for create new workflow button link tab
	 */
	// Reusable method
	public void createNewWorkFlow() {
		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getWorkFlow_CreateNewWorkFlowBtn());
		getWorkFlow_CreateNewWorkFlowBtn().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for Next button link tab for all pages tabs
	 */
	// Reusable method
	public void nextButton() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getNextLinkButton());
		getNextLinkButton().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for save button link tab for all pages tabs
	 */
	// Reusable method
	public void saveButton() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getSaveLinkButton());
		getSaveLinkButton().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for description tab
	 * @param wfName : wfName for passing workflow name
	 * @param wfDesc : wfDesc for passing description name
	 */
	// Reusable method
	public void descriptionTab(String wfName, String wfDesc) {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getWorkFlow_WorkFlowName());
		getWorkFlow_WorkFlowName().SendKeys(wfName);
		baseClass.waitForElement(getWorkFlow_Desc());
		getWorkFlow_Desc().SendKeys(wfDesc);

	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for sources tab passing saved search id
	 * @param savedSearchID : savedSearchID from session search to saved search id
	 */
	// Reusable method
	public void sourcesTab(int savedSearchID) {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getWorkFlow_SourceID());
		getWorkFlow_SourceID().SendKeys(Integer.toString(savedSearchID));

	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @throws AWTException
	 * @Description : Method for family option tab for clicking first family options
	 */
	// Reusable method for clicking first family options
	public void familyOptions(boolean flag) {
		driver.waitForPageToBeReady();
		if (flag == true) {
			baseClass.waitForElement(getFirstFamilyOptions());
			baseClass.waitTillElemetToBeClickable(getFirstFamilyOptions());
			getFirstFamilyOptions().ScrollTo();
			boolean flg2 = getFirstFamilyOptions().isDisplayed();
			System.out.println(flg2);
			getFirstFamilyOptions().waitAndClick(40);
		} else {
			System.out.println("No need this action");
		}
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for action tab to select folder which created
	 * @param folderName : folderName is passed to select checkbox
	 */
	// Reusable method for select folder
	public void actionTabToSelectFolder(String folderName, boolean flag) {
		driver.waitForPageToBeReady();
		if (flag == true) {
			baseClass.waitForElement(getWorkFlow_FolderSelector1());
			getWorkFlow_FolderSelector1().waitAndClick(5);
			driver.scrollingToElementofAPage(getFolderSelector(folderName));
			baseClass.waitForElement(getFolderSelector(folderName));
			getFolderSelector(folderName).waitAndClick(5);
		} else {
			System.out.println("No need this action to select folder checkbox");
		}
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for action tab to select assignment which created
	 * @param assgnName : assgnName is passed to select checkbox
	 */
	// Reusable method for select assignment
	public void actionTabToSelectAssignment(String assgnName, boolean flag) {
		driver.waitForPageToBeReady();
		if (flag == true) {
			baseClass.waitForElement(getWorkFlow_AssignmentsSelector());
			getWorkFlow_AssignmentsSelector().waitAndClick(5);
			driver.scrollingToElementofAPage(getWorkFlow_SelectAssignment(assgnName));
			baseClass.waitForElement(getWorkFlow_SelectAssignment(assgnName));
			getWorkFlow_SelectAssignment(assgnName).waitAndClick(5);
		} else {
			System.out.println("No need this action to select assignment checbox");
		}
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for schedules tab to fix timings
	 * @throws ParseException
	 */
	// Reusable method for schedule time to run workflow
	public void schedulesTab(int number) throws ParseException {
		driver.waitForPageToBeReady();
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

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date d = df.parse(Time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, number);

		DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
		String weekdays[] = dfs.getWeekdays();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String nameOfDay = weekdays[day];
		System.out.println(nameOfDay);
		UtilityLog.info(nameOfDay);

		// get next day date
		cal.add(Calendar.DATE, 1);
		String newTime = df.format(cal.getTime());
		System.out.println(newTime);
		UtilityLog.info(newTime);
		String s[] = newTime.split(" ");
		System.out.println(s[0]); // next day date
		UtilityLog.info(s[0]);
		System.out.println(s[1]); // current time + 1 min
		UtilityLog.info(s[01]);
		System.out.println();

		// passing work flow time
		baseClass.waitForElement(getWorkFlow_Time1());
		getWorkFlow_Time1().SendKeys(s[1]);

		// Select days for schedules -
		getWorkFlow_day(nameOfDay).Click();

		// stop work flow time
		getWorkFlow_StopDateOfCurrentWorkFlow().SendKeys(s[0]);

	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for notification tab passing all user
	 */
	// Reusable method for notification tab
	public void notificationTab() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getWorkFlow_Notification_AddAllUsers());
		getWorkFlow_Notification_AddAllUsers().waitAndClick(5);

	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for summary tab to save
	 */
	// Reusable method for summary tab
	public void summaryTab() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(geWorkFlow_Summary_Save());
		geWorkFlow_Summary_Save().waitAndClick(5);

	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @throws ParseException
	 * @Description : Method for creating new workflow
	 */
	// Reusable method for summary tab
	public void newWorkFlowCreation(String wfName, String wfDesc, int savedSearch, boolean familyFlag, String folder,
			boolean folderFlag, String assgn, boolean assgnFlag, int number) throws ParseException {
		createNewWorkFlow();
		descriptionTab(wfName, wfDesc);
		nextButton();
		sourcesTab(savedSearch);
		nextButton();
		nextButton();
		familyOptions(familyFlag);
		nextButton();
		actionTabToSelectFolder(folder, folderFlag);
		actionTabToSelectAssignment(assgn, assgnFlag);
		nextButton();
		schedulesTab(number);
		nextButton();
		notificationTab();
		nextButton();
		summaryTab();
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for creating workflow using pagination
	 * @param wfName
	 */
	// Reusable method for pagination in worlflow page
	public void selectWorkFlowUsingPagination(String wfName) {
		driver.scrollingToBottomofAPage();
//		baseClass.waitForElementCollection(getAssgnPaginationCount());
		int count = ((getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			Boolean status = getWorkFlow_SelectWorkflow(wfName).isElementAvailable(5);
			if (status == true) {
				driver.scrollingToElementofAPage(getWorkFlow_SelectWorkflow(wfName));
				getWorkFlow_SelectWorkflow(wfName).waitAndClick(5);
				driver.scrollPageToTop();
				baseClass.stepInfo("Expected workflow found in the page " + i);
				break;
			} else {
				driver.scrollingToBottomofAPage();
				baseClass.waitForElement(getWorkFlowPaginationNextButton());
				getWorkFlowPaginationNextButton().waitAndClick(5);
				baseClass.stepInfo("Expected workflow not found in the page " + i);
			}
		}
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Action to run workflow
	 */
	// Reusable method to run workflow
	public void actionToRunWorkFlow() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getWorkFlow_ActionDropdown());
		getWorkFlow_ActionDropdown().waitAndClick(10);
		baseClass.waitForElement(getWorkFlow_RunWorkflowNow());
		getWorkFlow_RunWorkflowNow().waitAndClick(10);
		baseClass.waitForElement(getWorkFlow_RunWorkflowNow_YesButton());
		getWorkFlow_RunWorkflowNow_YesButton().waitAndClick(10);
		baseClass.waitTime(10);
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : getting workflow id using workflow name
	 * @param wfName
	 */
	// Reusable method to get work flow id
	public int gettingWorkFlowId(String wfName) {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getWorkFlow_WorkFlowID(wfName));
		int workFlowId = Integer.parseInt(getWorkFlow_WorkFlowID(wfName).getText());
		return workFlowId;
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : passing workflow Id
	 * @param workFlowId
	 */
	// Reusable method for passing work flow id
	public void workFlowIdPassing(int workFlowId) {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getWorkFlowIdPassing());
		getWorkFlowIdPassing().SendKeys(Integer.toString(workFlowId));
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : apply filter method
	 */
//	Reusable method for apply filter
	public void applyFilter() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getApplyFilter());
		getApplyFilter().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : docs count
	 * @param wfName
	 */
	// Reusable method for getting doc count
	public int gettingWorkFlowListDocCount(String wfName) {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDocCount(wfName));
		int docCount = Integer.parseInt(getDocCount(wfName).getText());
		return docCount;
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : action to view history popup window
	 * @param wfName
	 */
	// Reusable method for action to view history
	public void actionToHistory(String wfName) {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getWorkFlow_SelectWorkflow(wfName));
		getWorkFlow_SelectWorkflow(wfName).waitAndClick(5);
		baseClass.waitForElement(getWorkFlow_ActionDropdown());
		getWorkFlow_ActionDropdown().waitAndClick(10);
		baseClass.waitForElement(getHistoryButton());
		getHistoryButton().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : action to view history popup window to doc count
	 * @param wfName
	 */
	// Reusable method for getting action doc count
	public int gettingActionDocCount(String wfName) {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getActionDocCount(wfName));
		int actiondocCount = Integer.parseInt(getActionDocCount(wfName).getText());
		return actiondocCount;
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : close history window
	 */
	// Reusable method for close history window
	public void closeHistoryPopUpWindow() {
		baseClass.waitForElement(getHistoryPopUpClose());
		getHistoryPopUpClose().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : page Refresh
	 */
	public void refreshingThePage() {
		driver.Navigate().refresh();
	}

	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Action to delete workflow
	 * @param wfName
	 * @param workFlowId
	 */
	// Reusable method for delete workflow
	public String actionToDelete(String wfName, int workFlowId) {
		driver.waitForPageToBeReady();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		baseClass.waitForElement(getWorkFlow_SelectWorkflow1());
		getWorkFlow_SelectWorkflow1().waitAndClick(20);
		baseClass.waitForElement(getWorkFlow_ActionDropdown());
		getWorkFlow_ActionDropdown().waitAndClick(10);
		baseClass.waitForElement(getWorkFlow_ActionDeleteWorkFlow());
		getWorkFlow_ActionDeleteWorkFlow().waitAndClick(10);
		baseClass.waitForElement(getDeletePopUpMessage());
		String popUpDelete = getDeletePopUpMessage().getText();
		System.out.println(popUpDelete);
		baseClass.waitForElement(getWorkFlow_RunWorkflowNow_YesButton());
		getWorkFlow_RunWorkflowNow_YesButton().waitAndClick(10);
		return popUpDelete;
	}

	/**
	 * @author Indium-Jayanthi Modified Date:24/2/2022
	 * @Description : method for webtable handling
	 * @param eleName
	 * @param WGName
	 */
	public String getTableValue(String eleName, String WGName) {
		int index = baseClass.getIndex(getTableHeader(), eleName);
		String tableValue = getWorkFlow_SelectWorkStatus(WGName, index).getText();
		return tableValue;
	}

	/**
	 * @author Vijaya.Rani Modified Date:24/2/2022
	 * @throws ParseException
	 * @Description :apply Ascanding/descending order
	 */
	public void applyAscandingorder() throws InterruptedException {
		driver.waitForPageToBeReady();
		miniDocList = new MiniDocListPage(driver);
		softAssertion = new SoftAssert();
		baseClass.waitForElementCollection(getWorkFlowIds());
		originalOrderedList = miniDocList.availableListofElements(getWorkFlowIds());
		afterSortList = miniDocList.availableListofElements(getWorkFlowIds());

		System.out.println("Original Order");
		for (String a : originalOrderedList) {
			System.out.println(a);
		}

		if (Input.sortType.equals("Ascending")) {
			Collections.sort(afterSortList);
		} else if (Input.sortType.equals("Descending")) {
			Collections.sort(afterSortList, Collections.reverseOrder());
		}

		System.out.println("Sorted Order");
		for (String b : afterSortList) {
			System.out.println(b);
		}
		softAssertion.assertEquals(afterSortList, originalOrderedList);

		if (afterSortList.equals(originalOrderedList)) {
			System.out.println("WorkFlow Id Colunm is Sorted");
			baseClass.passedStep("Sorted order is successfully");
		} else {
			System.out.println("Sorting order failed");
			baseClass.failedStep("Sorting order failed");
		}

	}

	/**
	 * @author Jayanthi.ganesan This method will perform filter based on work flow
	 *         id
	 * @param ID
	 */
	public void filterByWF_ID(String ID) {
		driver.scrollPageToTop();
		baseClass.waitForElement(getFiltertextbox_WFid());
		getFiltertextbox_WFid().SendKeys(ID);
		getApplyFilterBtn().waitAndClick(2);
	}

	/**
	 * @author Jayanthi.ganesan This method will get Values from work flow table
	 *         particular column based on index passed.
	 * @param eleName[Name of coulmn from which value needs to be extracted.]
	 * @return
	 */
	public List<String> getTableCoumnValue(String eleName) {
		int index = baseClass.getIndex(getTableHeader(), eleName);
		List<String> tableValue = baseClass.availableListofElements(getTableColumnData(index));
		return tableValue;
	}

	/**
	 * @author Jayanthi.ganesan This method will filter the work flow table using
	 *         'created by user'
	 * @param user
	 */
	public void filterByCreatedByUSer(String user) {
		driver.scrollPageToTop();
		getCreatedByUserDropDown().selectFromDropdown().selectByVisibleText(user);
		getApplyFilterBtn().waitAndClick(2);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param wfName[work flow name]
	 * @param wfDesc[work flow description]
	 */
	public void workFlow_Draft(String wfName, String wfDesc) {
		createNewWorkFlow();
		descriptionTab(wfName, wfDesc);
		getSaveBtn().Click();
		getBackTOManageBtn().Click();
		baseClass.getYesBtn().Click();
	}

	/**
	 * @author Vijaya.Rani Modified Date:28/2/2022
	 * @throws ParseException
	 * @Description : Method for creating new workflow in Assign User
	 */
	// Reusable method for notification tab
	public void newWorkFlowCreationAssignUser(String wfName, String wfDesc, int savedSearch, boolean familyFlag,
			String folder, boolean folderFlag, String assgn, boolean assgnFlag, int number) throws ParseException {
		createNewWorkFlow();
		descriptionTab(wfName, wfDesc);
		nextButton();
		sourcesTab(savedSearch);
		nextButton();
		nextButton();
		familyOptions(familyFlag);
		nextButton();
		actionTabToSelectFolder(folder, folderFlag);
		actionTabToSelectAssignment(assgn, assgnFlag);
		nextButton();
		schedulesTab(number);
		nextButton();
		notificationTab();
		saveButton();
		baseClass.VerifySuccessMessage("Record saved successfully");
	}

	/**
	 * @author Jayanthi.Ganesan
	 */
	public void getWorkFlowPage() {
		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Jayanthi.ganesan This method will filter the work flow table using
	 *         'status'
	 * @param statusTOFilter
	 */
	public void filterByStatus(String statusTOFilter) {
		driver.scrollPageToTop();
		getStatusDropDown().selectFromDropdown().selectByVisibleText(statusTOFilter);
		getApplyFilterBtn().waitAndClick(2);
	}

	/**
	 * @author Jayanthi.Ganesan This Method will return all values present under
	 *         respective column across all pages.
	 * @param eleName         [Name of Column ]
	 * @param EnabledState[it should be true if we want to take value from enabled
	 *                        state column else it should be false.]
	 * @return List of values under particular column.
	 */
	public List<String> getTableHeaderValuesPagination(String eleName, boolean EnabledState) {
		driver.scrollingToBottomofAPage();
		int count = ((getAssgnPaginationCount().size()) - 2);
		int index = baseClass.getIndex(getTableHeader(), eleName);
		List<String> tableValue = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
			int k = 1;
			List<WebElement> elementList = null;
			elementList = getTableColumnData(index).FindWebElements();
			if (EnabledState) {
				for (WebElement wenElementNames : elementList) {
					String data = wenElementNames.getText();
					if (data.equals(" ")) {
						tableValue.add(data);
					} else {
						if (getSelectedEnabledStateRadioBtn(k, index, 1).isElementAvailable(2)) {

							tableValue.add("Enabled");
						}
						if (getSelectedEnabledStateRadioBtn(k, index, 2).isElementAvailable(2)) {

							tableValue.add("Disabled");
						}
					}
					k++;
				}
			} else {
				for (WebElement wenElementNames : elementList) {
					String data = wenElementNames.getText();
					tableValue.add(data);
				}
			}
			System.out.println(tableValue);
			System.out.println(tableValue.size());
			driver.scrollingToBottomofAPage();
			if (i != count - 1) {
				baseClass.waitForElement(getWorkFlowPaginationNextButton());
				getWorkFlowPaginationNextButton().waitAndClick(5);
			}
		}
		return tableValue;

	}

	/**
	 * @author Jayanthi.Ganesan This method will perform column ascending or
	 *         descending order sorting.
	 * @param ascending[True  if we want to do ascending order sort]
	 * @param descending[True if we want to do descending order sort]
	 * @param URL
	 * @param eleName[Name    of column in which ascending sort to be performed]
	 */
	public void applySorting(boolean ascending, boolean descending, boolean URL, String eleName) {
		if (URL) {
			this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
			driver.waitForPageToBeReady();
		}
		String sortOrder;
		int index = baseClass.getIndex(getTableHeader(), eleName);
		try {
		if (descending) {
			baseClass.waitForElement(getTableHeader(index));
			getTableHeader(index).ScrollTo();
			getTableHeader(index).Click();
			baseClass.waitTime(1);
			if (!eleName.equals("Workflow ID")) {
				getTableHeader(index).Click();
			}
			baseClass.waitTime(1);
			sortOrder = getTableHeader(index).GetAttribute("aria-sort");
			if (sortOrder.equalsIgnoreCase("descending")) {
				baseClass.stepInfo(getTableHeader(index).getText() + " column is sorted in descending order");
			}

			else {
				baseClass.stepInfo(getTableHeader(index).getText() + " column is not  sorted in descending order");
			}
		}
		if (ascending) {
			getTableHeader(index).ScrollTo();
			getTableHeader(index).Click();
			driver.waitForPageToBeReady();
			sortOrder = getTableHeader(index).GetAttribute("aria-sort");
			if (sortOrder.equalsIgnoreCase("ascending")) {
				baseClass.stepInfo(getTableHeader(index).getText() + " column is sorted in ascending order");
			} else {
				baseClass.stepInfo(getTableHeader(index).getText() + " column is not  sorted in ascending order");
			}
		}
		}
		catch(Exception e)
		{
			baseClass.stepInfo("Eelemnt data is not available");
		}
	}

	/***
	 * @author Jayanthi.Ganesan This method verify whether a column sorted correctly
	 *         based on the order applied
	 * @param eleName[Name      of column]
	 * @param EnabledState[it   should be true if we want to take value from enabled
	 *                          state column else it should be false.]
	 * @param expectedList[List of values under the column before applying sorting]
	 * @param sortType[Type     of sort applied on the column]
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyHeaderSort(String eleName, boolean enabled, List<String> expectedList, String sortType)
			throws InterruptedException, AWTException {
		List<String> listAfterSort = getTableHeaderValuesPagination(eleName, enabled);
		baseClass.verifyOriginalSortOrder(listAfterSort, expectedList, sortType, true);
	}

	/**
	 * @author Jayanthi.ganesan This method will filter the work flow table using
	 *         'Enabled state'
	 * @param stateTOFilter
	 */
	public void filterByState(String stateTOFilter) {
		driver.scrollPageToTop();
		getStateDropDown().selectFromDropdown().selectByVisibleText(stateTOFilter);
		getApplyFilterBtn().waitAndClick(2);
	}

	/**
	 * @author Vijaya.Rani Modified Date:24/3/2022
	 * @throws ParseException
	 * @Description : verify Status List Complete Display
	 */
	public void verifyStatusListDisplay() {

		softAssertion = new SoftAssert();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getStatusDropDown());
		getStatusDropDown().waitAndClick(5);

		baseClass.waitForElement(getStatusCompleteOption());
		softAssertion.assertTrue(getStatusCompleteOption().Displayed());
		baseClass.passedStep("'Completed' status is displayed in the list successfully");
		softAssertion.assertAll();
	}

	/**
	 * @author Vijaya.Rani Modified Date:24/3/2022
	 * @throws ParseException
	 * @Description : Method for creating new workflow Summery Display
	 */
	// Reusable method for summary tab
	public void newWorkFlowCreationSummaryDisplay(String wfName, String wfDesc, int savedSearch, boolean familyFlag,
			String folder, boolean folderFlag, String assgn, boolean assgnFlag, int number) throws ParseException {
		createNewWorkFlow();
		descriptionTab(wfName, wfDesc);
		nextButton();
		sourcesTab(savedSearch);
		nextButton();
		nextButton();
		familyOptions(familyFlag);
		nextButton();
		actionTabToSelectFolder(folder, folderFlag);
		actionTabToSelectAssignment(assgn, assgnFlag);
		nextButton();
		schedulesTab(number);
		nextButton();
		notificationTab();
		nextButton();
		summaryTabDetails();
		summaryTab();
	}

	/**
	 * @author Vijaya.Rani Modified Date:24/3/2022
	 * @throws ParseException
	 * @Description : Summary All Data Display
	 */
	public void summaryTabDetails() {
		softAssertion = new SoftAssert();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getSummeryTabDescription());
		softAssertion.assertTrue(getSummeryTabDescription().Displayed());
		baseClass.passedStep("created workflow name is Display in SummaryTab");
		softAssertion.assertAll();
		baseClass.waitForElement(getSummeryTabSource());
		getSummeryTabSource().waitAndClick(5);
		softAssertion.assertTrue(getSummeryTabSource().Displayed());
		baseClass.passedStep("Saved  Search Source is Display in SummaryTab");
		softAssertion.assertAll();
		baseClass.waitForElement(getSummeryTabFamilyOption());
		getSummeryTabFamilyOption().waitAndClick(5);
		softAssertion.assertTrue(getSummeryTabFamilyOption().Displayed());
		baseClass.passedStep("Selected Family Option is Display in SummaryTab");
		softAssertion.assertAll();
		baseClass.waitForElement(getSummeryTabActionTab());
		getSummeryTabActionTab().waitAndClick(5);
		softAssertion.assertTrue(getSummeryTabActionTab().Displayed());
		baseClass.passedStep("Created FloderName is Display in SummaryTab");
		softAssertion.assertAll();
		baseClass.waitForElement(getSummeryTabScheduleOption());
		getSummeryTabScheduleOption().waitAndClick(5);
		softAssertion.assertTrue(getSummeryTabScheduleOption().Displayed());
		baseClass.passedStep("selected Schedule time is Display in SummaryTab");
		softAssertion.assertAll();
		baseClass.waitForElement(getSummeryTabNotificationOption());
		getSummeryTabNotificationOption().waitAndClick(5);
		softAssertion.assertTrue(getSummeryTabNotificationOption().Displayed());
		baseClass.passedStep("Notification is Display in SummaryTab");
		softAssertion.assertAll();
	}

	/**
	 * @author Jayanthi This method will select and open the work flow in edit mode
	 * @param wfName[Name of work flow]
	 */
	public void editWorkFlow(String wfName) {
		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		selectWorkFlowUsingPagination(wfName);
		// action to edit
		baseClass.waitForElement(getWorkFlow_ActionDropdown());
		getWorkFlow_ActionDropdown().waitAndClick(10);
		baseClass.waitForElement(getAction_EditWF());
		getAction_EditWF().waitAndClick(10);
		driver.waitForPageToBeReady();
	}
	
	/**
	 * @author Vijaya.Rani Modified Date:22/4/2022
	 * @throws AWTException
	 * @Description : Method for family option tab for clicking second family options
	 */
	// Reusable method for clicking second family options
	public void secondFamilyOptions(boolean flag) {
		driver.waitForPageToBeReady();
		if (flag == true) {
			baseClass.waitTillElemetToBeClickable(getSecondFamilyOptions());
			getSecondFamilyOptions().ScrollTo();
			boolean flg2 = getSecondFamilyOptions().isDisplayed();
			System.out.println(flg2);
			baseClass.stepInfo("SecondFamilyOption is " +flg2);
			getSecondFamilyOptions().waitAndClick(40);
		} else {
			System.out.println("No need this action");
		}
	}
	
	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for notification tab passing all user
	 */
	// Reusable method for notification tab
	public void notificationTabWithEmail() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getNotifyUserName(Input.rmu1FullName));
		getNotifyUserName(Input.rmu1FullName).waitAndClick(5);
		baseClass.waitForElement(getNotify_ButtonRight());
		getNotify_ButtonRight().waitAndClick(5);

	}
	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @Description : Method for summary tab to validate user email present or not
	 */
	// Reusable method for summary tab
	public void summaryTabEmailValidation() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(getSummaryTab("Notifications"));
		getSummaryTab("Notifications").waitAndClick(5);
		baseClass.waitForElement(getNotified_EmailUserId());
		String userId=getNotified_EmailUserId().getText();
		if (userId.contains(Input.rmu1userName)) {
			baseClass.passedStep("user email id are displaying in summary tab");
		}
		else {
			baseClass.failedStep("Not as per expected");
		}
		baseClass.waitForElement(geWorkFlow_Summary_Save());
		geWorkFlow_Summary_Save().waitAndClick(5);

	}
	
	/**
	 * @author Indium-Baskar Modified Date:24/2/2022
	 * @throws ParseException
	 * @Description : Method for creating new workflow
	 */
	// Reusable method for summary tab email id checking
	public void newWorkFlowCreationWithOneEmail(String wfName, String wfDesc, int savedSearch, boolean familyFlag, String folder,
			boolean folderFlag, String assgn, boolean assgnFlag, int number) throws ParseException {
		createNewWorkFlow();
		descriptionTab(wfName, wfDesc);
		nextButton();
		sourcesTab(savedSearch);
		nextButton();
		nextButton();
		familyOptions(familyFlag);
		nextButton();
		actionTabToSelectFolder(folder, folderFlag);
		actionTabToSelectAssignment(assgn, assgnFlag);
		nextButton();
		schedulesTab(number);
		nextButton();
		notificationTabWithEmail();
		nextButton();
		summaryTabEmailValidation();
	}

}
package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ABMReportPage {

	Driver driver;
	BaseClass bc;

	public Element getReport_ABM() {
		return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Advanced Batch Management Report')]");
	}

	public Element getABM_SelectSource() {
		return driver.FindElementById("select-new-source");
	}

	public Element getABM_SearchButton() {
		return driver.FindElementByXPath("//*[@id='searchesPopup']//strong");
	}

	public Element getABM_AssignmentButton() {
		return driver.FindElementByXPath("//*[@id='assignmentsPopup']//strong");
	}

	public Element getABM_FolderButton() {
		return driver.FindElementByXPath("//*[@id='foldersPopup']//strong");
	}

	public Element getABM_SelectSearch(String search) {
		return driver.FindElementByXPath("//*[@id='searchesJSTree']//a[contains(text(),'" + search + "')]/i[1]");
	}

	public Element getApplyBtn() {
		return driver.FindElementById("btn_applychanges");
	}

	public Element getABM_ReviewerExpandbutton() {
		return driver.FindElementByXPath("//*[@id='divreviewer']/div/a[2]");
	}

	public Element getABM_SelectAssgn(String assgnname) {
		return driver.FindElementByXPath("//*[@id='jstreeAssignmentGroup']//a[contains(text(),'" + assgnname + "')]");
	}

	public Element getABM_Reviewer_SelectAll() {
		return driver.FindElementByXPath("//*[@id='checkAll4']/following-sibling::i");
	}

	public Element getABM_SelectAssignment() {
		return driver.FindElementById("assignmentGroupID");
	}

	public Element getABM_Searchsavebutton() {
		return driver.FindElementById("search");
	}

	public Element getABM_SummaryPage() {
		return driver.FindElementById("ABMSummary");
	}

	public Element getManageBatchAt() {
		return driver.FindElementById("ddlManageBatch");
	}

	public Element seelctAllRows_DocLevel() {
		return driver.FindElementById("selectAllRows");
	}

	public Element getYesBtn_PopUP() {
		return driver.FindElementByXPath("//input[@id='Yes']");
	}

	public Element getLeftToDoRadioBtn_DocLevel() {
		return driver.FindElementByXPath("//input[@value='NOTCOMPLETED']");
	}

	// Updated on 12/09
	public Element getABM_RevList(String name) {
		return driver.FindElementByXPath(".//*[@id='rvlist']//span[contains(text(),'" + name + "')]");
	}

	public Element actionBtn() {
		return driver.FindElementById("tallyactionbtn");
	}

	public Element getAction_ViewBtn() {
		return driver.FindElementByXPath("//li//a[text()='View']");
	}

	public Element getAction_DocViewBtn() {
		return driver.FindElementByXPath("//li//a[text()='View in DocView']");
	}

	public Element getToDo_chkBox(String assgnNme) {
		return driver.FindElementByXPath("//table[@id='dtAdvanceBatchMangeReport']/tbody/tr/td[text()='" + assgnNme
				+ "']/following-sibling::td[@class=' Inset_ToDo']//td/input");
	}

	public Element getToDo_Text(String assgnNme) {
		return driver.FindElementByXPath("//table[@id='dtAdvanceBatchMangeReport']/tbody/tr/td[text()='" + assgnNme
				+ "']/following-sibling::td[@class=' Inset_ToDo']//td/label");
	}

	public Element getCompletedDocs_Text(String assgnNme) {
		return driver.FindElementByXPath("//table[@id='dtAdvanceBatchMangeReport']/tbody/tr/td[text()='" + assgnNme
				+ "']/following-sibling::td[@class=' Inset_Completed']//td/label");
	}

	public ElementCollection getListToDo_Text(String assgnNme) {
		return driver.FindElementsByXPath("//table[@id='dtAdvanceBatchMangeReport']/tbody/tr/td[text()='" + assgnNme
				+ "']/following-sibling::td[@class=' Inset_ToDo']//td/label");
	}

	public ElementCollection getListCompletedDocs_Text(String assgnNme) {
		return driver.FindElementsByXPath("//table[@id='dtAdvanceBatchMangeReport']/tbody/tr/td[text()='" + assgnNme
				+ "']/following-sibling::td[@class=' Inset_Completed']//td/label");
	}

	public ElementCollection getElements() {
		return driver.FindElementsByXPath("//*[@class='a-menu']");
	}

	public Element getBG_NotificationPopUp() {
		return driver.FindElementByXPath("//p[contains(text(),'Your report is taking a')]");
	}

	public Element getNoButton() {
		return driver.FindElementById("btnNo");
	}

	public Element getBullHornIcon_CC() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']//following::b[contains(@class,'badge')]");
	}

	public Element getRedBullHornIcon_CC() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']//following::b[contains(@class,'bg-color-red')]");
	}

	public Element getBullHornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getBullHornIconNotification(int i) {
		return driver
				.FindElementByXPath("(//a[contains(text(),'Your Background Report with Notification ')])[" + i + "]");
	}

	public ElementCollection gettableHeaders() {
		return driver.FindElementsByXPath("//table[@id='dtAdvanceBatchMangeReport']/thead/tr/th");
	}

	public Element getActionButton() {
		return driver.FindElementByXPath("//button[@id='tallyactionbtn']//span[@class='fa fa-chevron-down']");
	}

	public Element getCheckBox_table(String assignName, int i) {
		return driver.FindElementByXPath("//table[@id='dtAdvanceBatchMangeReport']/tbody/tr/td[text()='" + assignName
				+ "']/parent::tr/td[" + i + "]/table//td/input");
	}

	public Element getDocCount_table(String assignName, int i) {
		return driver.FindElementByXPath("//table[@id='dtAdvanceBatchMangeReport']/tbody/tr/td[text()='" + assignName
				+ "']/parent::tr/td[" + i + "]/table//td/label");
	}

	public Element getDocSlectCheckBox() {
		return driver.FindElementByXPath("//td[@class=' tableCell']/input");
	}

	public Element getActionBulkTag() {
		return driver.FindElementByXPath("//a[text()='Bulk Tag']");
	}

	public Element getActionBulkAssign() {
		return driver.FindElementByXPath("//a[text()='Bulk Assign']");
	}

	public Element getActionBulkFolder() {
		return driver.FindElementByXPath("//a[text()='Bulk Folder']");
	}

	// added by jeevitha

	public Element getClickedAssignment_CB(String assignment) {
		return driver.FindElementByXPath(
				"//div[@id='jstreeAssignmentGroup']//a[@class='jstree-anchor jstree-clicked' and text()='" + assignment
						+ "']");
	}

	public Element getTotalDocsSelected() {
		return driver.FindElementByXPath("//label[text()='Total Net Selected Docs ']//span");
	}

	public Element getABM_ReportPage() {
		return driver.FindElementById("dvAdvancedBatchreport");
	}

	public Element getABM_Assignsavebutton() {
		return driver.FindElementById("assignment");
	}

	public Element getSourceAssignment(String assignment) {
		return driver.FindElementByXPath("//div[@id='assignmentsJSTree']//a[text()='" + assignment + "']");
	}

	public ABMReportPage(Driver driver) {

		this.driver = driver;
		bc = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

	}

	public void ValidateABMreport(final String savedsearch, final String assgnname) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReport_ABM().Visible();
			}
		}), Input.wait30);
		getReport_ABM().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApplyBtn().Visible();
			}
		}), Input.wait30);
		getApplyBtn().Click();

		bc.VerifyWarningMessage("Please select source details");
		bc.CloseSuccessMsgpopup();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getABM_SelectSource().Visible();
			}
		}), Input.wait30);
		getABM_SelectSource().Click();

		getABM_SearchButton().waitAndClick(10);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getABM_SelectSearch(savedsearch).Visible();
			}
		}), Input.wait30);
		getABM_SelectSearch(savedsearch).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getABM_Searchsavebutton().Enabled();
			}
		}), Input.wait30);
		getABM_Searchsavebutton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApplyBtn().Visible();
			}
		}), Input.wait30);
		getApplyBtn().Click();
		Thread.sleep(2000);

		bc.VerifyWarningMessage("Please select reviewers");
		bc.CloseSuccessMsgpopup();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getABM_ReviewerExpandbutton().Visible();
			}
		}), Input.wait30);
		getABM_ReviewerExpandbutton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getABM_RevList("0QARMU11 RMU11").Visible();
			}
		}), Input.wait30);
		getABM_RevList("0QARMU11 RMU11").Displayed();
		getABM_RevList("0QADA1 DA11").Displayed();
		getABM_RevList("0QAPA1 PA1").Displayed();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getABM_Reviewer_SelectAll().Enabled();
			}
		}), Input.wait30);
		getABM_Reviewer_SelectAll().Click();

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApplyBtn().Visible();
			}
		}), Input.wait30);
		getApplyBtn().Click();
		Thread.sleep(2000);

		bc.VerifyWarningMessage("Please select assignments");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getABM_SelectAssignment().Visible();
			}
		}), Input.wait30);
		getABM_SelectAssignment().Click();
		Thread.sleep(2000);

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getABM_SelectAssgn(assgnname).Visible();
			}
		}), Input.wait30);
		getABM_SelectAssgn(assgnname).Click();

		driver.scrollPageToTop();
		getApplyBtn().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getABM_SummaryPage().Visible();
			}
		}), Input.wait60);

		Assert.assertTrue(getABM_SummaryPage().Displayed());
		System.out.println("test passed");
	}

	/**
	 * @author Jayanthi.ganesan This method will redirect from ABM Page to Doc view
	 *         page.
	 */
	public void ViewInDocView() {
		driver.scrollPageToTop();
		bc.waitForElement(actionBtn());
		actionBtn().waitAndClick(20);
		bc.waitForElement(getAction_ViewBtn());
		getAction_ViewBtn().ScrollTo();
		getAction_DocViewBtn().ScrollTo();
		getAction_DocViewBtn().waitAndClick(30);
		driver.waitForPageToBeReady();
		bc.stepInfo("Navigating from Search term report page to view in doc view page.");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param savedsearch            (Name of saved search to be selected as source)
	 * @param assgnname              (Name of Assignment to be selected to generate
	 *                               report)
	 * @param manageBatch('true'--if ABM Report needs to generated at doc
	 *                               Level(Manage Batch option selected as doc
	 *                               level) ('false'--if ABM Report needs to be
	 *                               generated at assignment Level(Manage Batch
	 *                               option selected as Assignment level)
	 * @return To Do Value for a particular assignment from ABM Report Generated .
	 * @throws InterruptedException
	 */
	public String generateABM_Report(final String savedsearch, final String assgnname, boolean manageBatch)
			throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

		bc.waitForElement(getReport_ABM());
		getReport_ABM().Click();
		driver.waitForPageToBeReady();
		if (manageBatch) {
			getManageBatchAt().selectFromDropdown().selectByVisibleText("Document");
		}
		bc.waitForElement(getABM_SelectSource());
		getABM_SelectSource().Click();
		getABM_SearchButton().waitAndClick(10);
		Thread.sleep(2000);

		bc.waitForElement(getABM_SelectSearch(savedsearch));
		getABM_SelectSearch(savedsearch).Click();
		bc.waitForElement(getABM_Searchsavebutton());
		getABM_Searchsavebutton().Click();
		bc.waitForElement(getABM_ReviewerExpandbutton());
		getABM_ReviewerExpandbutton().Click();
		bc.waitForElement(getABM_Reviewer_SelectAll());
		getABM_Reviewer_SelectAll().Click();
		getABM_ReviewerExpandbutton().ScrollTo();
		getABM_ReviewerExpandbutton().Click();

		bc.waitForElement(getABM_SelectAssignment());
		Thread.sleep(1000);
		getABM_SelectAssignment().Click();
		Thread.sleep(1000);

		getABM_SelectAssgn(assgnname).ScrollTo();
		getABM_SelectAssgn(assgnname).Click();

		driver.scrollPageToTop();
		getApplyBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.waitForElement(getABM_SummaryPage());
		String value = null;
		if (getABM_SummaryPage().Displayed()) {
			bc.passedStep("Advanced Batch Manage Report generated with saved search " + savedsearch
					+ " as Source and assignment seelcted is " + assgnname);
			if (manageBatch) {
				// getLeftToDoRadioBtn_DocLevel().Click();
				driver.waitForPageToBeReady();
				bc.waitForElement(seelctAllRows_DocLevel());
				seelctAllRows_DocLevel().waitAndClick(30);

				if (getYesBtn_PopUP().isElementAvailable(3)) {
					getYesBtn_PopUP().Click();
				}
				if (bc.getYesBtn().isElementAvailable(3)) {
					bc.getYesBtn().Click();
				}
			} else {
				getToDo_chkBox(assgnname).ScrollTo();
				getToDo_chkBox(assgnname).Click();
				value = getToDo_Text(assgnname).getText();
			}
		} else {
			bc.failedMessage("Advanced Batch Management report not generated.");
		}
		return value;
	}

	/**
	 * @author Jayanthi.ganesan This method will generate ABM Report and validate
	 *         the Reviewer list in Report Page
	 * @param savedsearch(Name of saved search to be selected as source)
	 * @param assgnname        (Name of Assignment to be selected to generate
	 *                         report)
	 * @param manageBatch      (should be true if ABM needs to be generated at doc
	 *                         level)
	 * @throws InterruptedException
	 */
	public void validateRevListAndgenerateABM_Report(final String savedsearch, final String assgnname,
			boolean manageBatch, boolean TocheckreviewerList) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

		bc.waitForElement(getReport_ABM());
		getReport_ABM().Click();
		driver.waitForPageToBeReady();
		if (manageBatch) {
			getManageBatchAt().selectFromDropdown().selectByVisibleText("Document");
		}
		bc.waitForElement(getABM_SelectSource());
		getABM_SelectSource().Click();
		getABM_SearchButton().waitAndClick(10);
		bc.waitForElement(getABM_SelectSearch(savedsearch));
		getABM_SelectSearch(savedsearch).Click();
		bc.waitForElement(getABM_Searchsavebutton());
		getABM_Searchsavebutton().Click();
		bc.waitForElement(getABM_ReviewerExpandbutton());
		getABM_ReviewerExpandbutton().Click();
		bc.waitForElement(getABM_Reviewer_SelectAll());
		getABM_Reviewer_SelectAll().Click();
		if (TocheckreviewerList) {
			getABM_RevList(Input.pa1FullName).isElementAvailable(2);
			// getABM_RevList(Input.da1FullName).isElementAvailable(2);
			getABM_RevList(Input.rev1FullName).isElementAvailable(2);
			getABM_RevList(Input.rmu1FullName).isElementAvailable(2);
			bc.stepInfo("PA/DA/RMU/REviewer Associated to the project is avialable under ABM.");
		}
		getABM_ReviewerExpandbutton().ScrollTo();
		getABM_ReviewerExpandbutton().Click();
		bc.waitTime(3);
		getABM_SelectAssignment().ScrollTo();
		bc.waitTillElemetToBeClickable(getABM_SelectAssignment());
		getABM_SelectAssignment().waitAndClick(20);
		bc.waitTime(2);
		getABM_SelectAssgn(assgnname).Click();
		driver.scrollPageToTop();
		getApplyBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.waitForElement(getABM_SummaryPage());
		if (getABM_SummaryPage().isDisplayed()) {
			bc.passedStep("Advanced Batch Manage Report generated with saved search " + savedsearch
					+ " as Source and assignment seelcted is " + assgnname);
		} else {
			bc.failedMessage("Advanced Batch Management report not generated.");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param elecollection
	 * @Description This method will get the doc counts of one column (Eg-To Do
	 *              Count ,Completed Doc Count ,etc)from report generated
	 * @return Sum of all values in that particular column.
	 */
	public int validateReport(ElementCollection elecollection) {
		List<Integer> elementNames = new ArrayList<>();
		List<WebElement> elementList = null;
		elementList = elecollection.FindWebElements();
		for (WebElement webElementNames : elementList) {
			String elementName = webElementNames.getText();
			elementNames.add(Integer.parseInt(elementName));
		}
		int sum = 0;
		if (elementNames == null || elementNames.size() < 1) {
			return 0;
		} else {
			for (int i : elementNames) {
				sum = sum + i;
			}
		}
		return sum;
	}

	/**
	 * @author Jayanthi.Ganesan This method will generate ABM report [Report
	 *         generation goes background] and verify whether report displayed from
	 *         back ground notification
	 * @param savedsearch
	 * @param savedsearch_docLevel[this saved search should be having docs upto
	 *                                  10000 only because we have to use this
	 *                                  search to generate report at doc level ]
	 * @param assgnname
	 * @param assignmentName1
	 * @param manageBatch[if            manage batch option is document level this
	 *                                  should be 'true']
	 * @throws InterruptedException
	 */
	public void generateABM_BackGroundReportGeneration(String savedsearch, String savedsearch_docLevel,
			String assgnname, String assignmentName1, boolean manageBatch) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		bc.waitForElement(getReport_ABM());
		getReport_ABM().Click();
		driver.waitForPageToBeReady();
		if (manageBatch) {
			getManageBatchAt().selectFromDropdown().selectByVisibleText("Document");
		}
		getBullHornIcon().Click();
		getBullHornIcon().Click();
		String bullHornValue = getBullHornIcon_CC().getText();
		int valueBeforeAnalysis = Integer.parseInt(bullHornValue);
		bc.waitForElement(getABM_SelectSource());
		getABM_SelectSource().Click();
		getABM_SearchButton().waitAndClick(10);
		if (manageBatch) {
			bc.waitForElement(getABM_SelectSearch(savedsearch_docLevel));
			getABM_SelectSearch(savedsearch_docLevel).Click();
		} else {
			bc.waitForElement(getABM_SelectSearch(savedsearch_docLevel));
			getABM_SelectSearch(savedsearch_docLevel).Click();
			bc.waitForElement(getABM_SelectSearch(savedsearch));
			getABM_SelectSearch(savedsearch).Click();
		}
		bc.waitForElement(getABM_Searchsavebutton());
		getABM_Searchsavebutton().Click();
		bc.waitForElement(getABM_ReviewerExpandbutton());
		getABM_ReviewerExpandbutton().Click();
		bc.waitForElement(getABM_Reviewer_SelectAll());
		getABM_Reviewer_SelectAll().Click();
		getABM_ReviewerExpandbutton().ScrollTo();
		getABM_ReviewerExpandbutton().Click();
		bc.waitTime(5);
		bc.waitForElement(getABM_SelectAssignment());
		getABM_SelectAssignment().waitAndClick(10);
		bc.waitTime(5);
		getABM_SelectAssgn(assgnname).ScrollTo();
		getABM_SelectAssgn(assgnname).waitAndClick(10);
		bc.waitTime(5);
		getABM_SelectAssgn(assignmentName1).ScrollTo();
		getABM_SelectAssgn(assignmentName1).waitAndClick(10);
		driver.scrollPageToTop();
		getApplyBtn().waitAndClick(10);
		bc.waitForElement(getBG_NotificationPopUp());
		if (getBG_NotificationPopUp().isElementAvailable(1)) {
			getNoButton().Click();
			bc.passedStep("Back ground report generation notification displayed.");
		} else {
			bc.failedMessage("Advanced Batch Management  Back ground report generation notification not  displayed.");
		}
		// checking for notification for BG search completed status.
		bc.waitForElement(getRedBullHornIcon_CC());
		bc.waitForElement(getRedBullHornIcon_CC());
		String bullHornValue2 = getRedBullHornIcon_CC().getText();
		int valueAfterAnalysis = Integer.parseInt(bullHornValue2);
		if (valueAfterAnalysis > valueBeforeAnalysis) {
			bc.passedStep("Bull horn icon has New Notification");
			getBullHornIcon().Click();
			driver.waitForPageToBeReady();
			bc.waitForElement(getBullHornIconNotification(1));
			getBullHornIconNotification(1).waitAndClick(10);
			bc.waitForElement(getABM_SummaryPage());
			if (getABM_SummaryPage().isDisplayed()) {
				bc.passedStep("Advanced Batch Manage Report generated with saved search " + savedsearch
						+ " as Source and assignment seelcted is " + assgnname);
			} else {
				bc.failedMessage("Advanced Batch Management report not generated.");
			}
		} else {
			bc.failedMessage("No new notification added in bull horn icon");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param IndexElement
	 * @return
	 */
	public int getIndex(String IndexElement) {
		int i;
		i = bc.getIndex(gettableHeaders(), IndexElement);
		return i;
	}

	/**
	 * Select docs from Any column[In set,To do,Completed] as per our wish if we
	 * select Assignment ABM report.
	 * 
	 * @param assignName
	 * @param columnName
	 * @param clickCheckBox
	 * @return
	 */

	public String selectDocsinTable(String assignName, String columnName, boolean clickCheckBox) {
		driver.waitForPageToBeReady();
		bc.waitForElementCollection(gettableHeaders());
		int i = getIndex(columnName);
		if (clickCheckBox) {
			getCheckBox_table(assignName, i).ScrollTo();
			getCheckBox_table(assignName, i).Click();
		}
		System.out.println(getDocCount_table(assignName, i).getText());
		return getDocCount_table(assignName, i).getText();

	}

	/**
	 * doc selection if we select ABM report at document level
	 */
	public void docSelection() {
		bc.waitForElement(getDocSlectCheckBox());
		getDocSlectCheckBox().Click();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param folder[if we want to do bulk folder it should be true else bulk tag
	 *                  will be done]
	 * 
	 */
	public void BulkTag_Folder(boolean folder) {

		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(20);
		if (folder) {
			bc.waitForElement(getActionBulkFolder());
			getActionBulkFolder().Click();
		} else {
			bc.waitForElement(getActionBulkTag());
			getActionBulkTag().Click();
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */
	public void bulkAssign() {
		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(20);
		bc.waitForElement(getActionBulkAssign());
		getActionBulkAssign().Click();
	}

	/**
	 * @Author Jeevitha
	 * @Dsecription : Select Sources from source selection tab
	 * @param SelectionLink
	 * @param Name
	 */
	public void selectSources(String SelectionLink, String Name) {
		bc.waitForElement(getABM_SelectSource());
		getABM_SelectSource().waitAndClick(5);

		bc.waitForElement(getABM_AssignmentButton());
		if (SelectionLink.equalsIgnoreCase("Assignments")) {
			getABM_AssignmentButton().waitAndClick(5);
			bc.waitForElement(getABM_AssignmentButton());
			getSourceAssignment(Name).waitAndClick(5);
			bc.waitForElement(getABM_AssignmentButton());
			getABM_Assignsavebutton().waitAndClick(5);

			bc.stepInfo("Selected Assignments in source slection tab is: " + Name);
		}
	}

	/**
	 * @Author Jeevitha
	 * @Dsecription : Select Reviewers from Reviewer selection tab
	 */
	public void selectReviewers(boolean SelectAll, boolean closeReviewerExpand, String additional) {
		bc.waitForElement(getABM_ReviewerExpandbutton());
		getABM_ReviewerExpandbutton().waitAndClick(5);

		if (SelectAll) {
			bc.waitForElement(getABM_Reviewer_SelectAll());
			getABM_Reviewer_SelectAll().waitAndClick(10);
			bc.stepInfo("Selected All reviewers from Reviewers Tab");
		}
		if (closeReviewerExpand) {
			getABM_ReviewerExpandbutton().ScrollTo();
			bc.waitForElement(getABM_ReviewerExpandbutton());
			getABM_ReviewerExpandbutton().waitAndClick(5);
		}
	}

	/**
	 * @Author Jeevitha
	 * @Dsecription : Select Assignment from Assignment selection tab
	 */
	public void selectAssignment(boolean oneAssignment, String assgnname, String addition) {
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getABM_SelectAssignment());
		getABM_SelectAssignment().waitAndClick(10);
		bc.waitTime(3);
		if (oneAssignment) {
			bc.waitForElement(getABM_SelectAssgn(assgnname));
			getABM_SelectAssgn(assgnname).ScrollTo();
			getABM_SelectAssgn(assgnname).waitAndClick(10);
			bc.stepInfo("Selected Assignment From Assignment selection TAb is: " + assgnname);
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify ABM generated Report
	 * @param verifyDoc
	 * @param selectedDocCount
	 */
	public void verifyReportGenerated(boolean clickApplyBtn, boolean verifyDoc, int selectedDocCount,
			boolean verifyImpactOfSelectCriteria, String assignName) {
		if (clickApplyBtn) {
			driver.scrollPageToTop();
			bc.waitForElement(getApplyBtn());
			getApplyBtn().waitAndClick(10);
			bc.stepInfo("Clicked Apply Button");
		}

		if (getABM_ReportPage().isElementAvailable(60)) {
			bc.passedStep("Advanced Batch Management Report Generated Successfully");
			bc.waitTime(3);
			if (verifyDoc) {
				bc.waitForElement(getTotalDocsSelected());
				String actualCount = getTotalDocsSelected().getText();
				bc.stepInfo("Total Net Selected Docs Displayed in Report is : " + actualCount);
				bc.digitCompareEquals(Integer.parseInt(actualCount), selectedDocCount,
						"Generated report is as per the selected criteria.",
						"Generated report is Not as per the selected criteria.");
			}

			if (verifyImpactOfSelectCriteria) {
				driver.waitForPageToBeReady();
				bc.ValidateElement_Presence(getClickedAssignment_CB(assignName), "Selected Assignment");
				driver.waitForPageToBeReady();
				bc.printResutInReport(
						driver.verifyElementPresence("return document.querySelector(\"#checkAll4\").checked"),
						"Selected Reviewers is Retained", "Selected Reviewers is not retained", "Pass");
				bc.stepInfo("Selected Reviewer and Assignment selection is not impacted after performing action.");
			}
		} else {
			bc.failedStep("Advanced Batch Management Report is not Displayed");
		}
	}
}
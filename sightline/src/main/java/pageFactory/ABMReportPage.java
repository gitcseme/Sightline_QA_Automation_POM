package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

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
	 * @author Jayanthi.ganesan
	 *         This method will redirect from ABM Page to Doc view page.
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
	 * @param savedsearch (Name of saved search to be selected as source)
	 * @param assgnname (Name of Assignment to be selected to generate report)
	 * @param manageBatch('true'--if ABM Report needs to generated at doc
	 *                   Level(Manage Batch option selected as doc level) 
	 *                   ('false'--if ABM Report needs to be generated at assignment 
	 *                   Level(Manage Batch option selected as Assignment level)
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
	 * @author Jayanthi.ganesan
	 *         This method will generate ABM Report and validate the Reviewer
	 *         list in Report Page
	 * @param savedsearch(Name of saved search to be selected as source)
	 * @param assgnname (Name of Assignment to be selected to generate report)
	 * @param manageBatch (should be true if ABM needs to be generated at doc level)
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
		bc.waitForElement(getABM_SelectAssignment());
		getABM_SelectAssignment().Click();
		getABM_SelectAssgn(assgnname).ScrollTo();
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
	 * @Description This method will get the doc counts of one column (Eg-To
	 *              Do Count ,Completed Doc Count ,etc)from report generated
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

}

package pageFactory;

import java.awt.AWTException;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class SearchTermReportPage {

	Driver driver;
	BaseClass bc;
	ReportsPage reportPage;
	SoftAssert softAssertion;
	DocListPage dl;

	public Element getReport_SearchTerm() {
		return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Search Term Report')]");
	}

	public Element getST_Selectmysearches() {
		return driver.FindElementByXPath("//a[contains(.,'My Saved Search')]");
	}

	public Element getApplyBtn() {
		return driver.FindElementById("btn_applychanges");
	}

	public Element getST_SummaryPage() {
		return driver.FindElementById("tblCustomSummary");
	}

	public Element getST_SelectHits() {
		return driver.FindElementById("chkHits");
	}

	public Element getST_Actionsbutton() {
		return driver.FindElementById("tallyactionbtn");
	}

	public Element getST_Actions_QB() {
		return driver.FindElementByXPath("//a[contains(.,'Quick Batch')]");
	}

	// public Element getST_SelectHits(){ return driver.FindElementById("chkHits");
	// }
	public Element getST_Selectsearch(String searchname) {
		return driver.FindElementByXPath("//a[contains(.,'" + searchname + "']/i[1]");
	}

	// Added by Raghuram
	public Element getSTR_SaveBtn() {
		return driver.FindElementByXPath("//i[@id='saveReport']");
	}

	public Element getSTR_SaveInputField() {
		return driver.FindElementByXPath("//input[@id='txtReportname']");
	}

	public Element getSTR_SaveReportBtn() {
		return driver.FindElementByXPath("//button[@id='saveXML']");
	}

	public Element getSaveSearch() {
		return driver.FindElementById("btnBasicSearch");
	}

	public Element getSearchTermReport() {
		return driver.FindElementByXPath("//a[text()='Search Term Report']");
	}

	public Element getSaveMySavedSearch() {
		return driver.FindElementByXPath("//a[text()='My Saved Search']");
	}

	public Element getMySavedSearchText() {
		return driver.FindElementById("txtSaveSearchName");
	}

	public Element getActionButton() {
		return driver.FindElementByXPath("//button[@id='tallyactionbtn']//span[@class='fa fa-chevron-down']");
	}

	public Element getActionBulkTag() {
		return driver.FindElementByXPath("//a[text()='Bulk Tag']");
	}

	public Element getBulkNewTab() {
		return driver.FindElementById("tabNew");
	}

	public Element getAllTagDocument() {
		return driver.FindElementByXPath("//*[@id='tagsJSTree']//*[@id='-1_anchor' or @data-content='All Tags']");
	}

	public Element getNameTagDocument() {
		return driver.FindElementByXPath("//input[@id='txtTagName']");
	}

	public Element getContinueDocument() {
		return driver.FindElementByXPath(".//*[@id='divBulkAction']//button[contains(.,'Continue')]");
	}

	public Element getFinalizeTag() {
		return driver.FindElementByXPath("//button[@id='btnfinalizeAssignment']");
	}

	public Element getFinalizeOkButton() {
		return driver.FindElementByXPath(" //button[@id='bot1-Msg1']");
	}

	public Element getNodeCheckBox(String nodeName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor' and text()='My Saved Search']//..//ul//a[text()='"
				+ nodeName + "']//i[@class='jstree-icon jstree-checkbox']");
	}
	public ElementCollection gettableHeaders() {
		return driver.FindElementsByXPath("(//table[@id='searchtermTable']//thead/tr/th)");
	}

	public Element getRowCheckBox(String savaesearchName, int i) {
		return driver.FindElementByXPath(" //table[@id='searchtermTable']//tbody/tr/td[text()='" + savaesearchName
				+ "']/parent::tr/td[" + i + "]//table/tbody/tr/td/input");
	}

	public Element getRowValue(String savaesearchName, int i) {
		return driver.FindElementByXPath(" //table[@id='searchtermTable']//tbody/tr/td[text()='" + savaesearchName
				+ "']/parent::tr/td[" + i + "]//table/tbody/tr/td/label");
	}

	public Element getActionBulkFolder() {
		return driver.FindElementByXPath("//a[text()='Bulk Folder']");
	}

	public Element getSTReport() {
		return driver.FindElementById("searchtermTable");
	}

	public Element getAllFoldersTab() {
		return driver
				.FindElementByXPath("//div[@id='folderJSTree']//a[@id='-1_anchor' or @data-content='All Folders']");
	}

	public Element getFolderNameTxtBox() {
		return driver.FindElementByXPath("//input[@id='txtFolderName']");
	}

	public Element getViewBtn() {
		return driver.FindElementByXPath("//ul[@class='dropdown-menu action-dd']//li//a[text()='View']");
	}

	public Element getViewinDocViewBtn() {
		return driver.FindElementByXPath("//ul[@Class='dropdown-menu']//li//a[text()='View in DocView']");
	}

	public Element getBulkAssignBtn() {
		return driver.FindElementByXPath("//a[text()='Bulk Assign']");
	}

	public Element getExportBtn() {
		return driver.FindElementByXPath("//a[text()=' Export Data']");
	}

	public Element getTotalSelectedCount() {
		return driver.FindElementByXPath("//div//label[@id='lblTotal']");
	}
	
	// added by jayanthi 16/12/21
	public Element mySavedSearchCheckbox() {
		return driver.FindElementByXPath(
				"//a[@class='jstree-anchor' and text()='My Saved Search']//i[@class='jstree-icon jstree-checkbox']");
	}

	public ElementCollection getColumnValues(int i) {
		return driver.FindElementsByXPath("//*[@id='searchtermTable']/tbody/tr/td[" + i + "]");
	}

	public Element getUniqueHitsFromSummary() {
		return driver.FindElementByXPath("//label[@id='lblUniquePureHitsCount']/font/b");
	}

	public Element getUniqueFamilyHitsFromSummary() {
		return driver.FindElementByXPath("//label[@id='lblUniqueFamilyHitsCount']/font/b");
	}

	public Element getUniqueHits() {
		return driver.FindElementByXPath("(//table[@id='searchtermTable']//thead/tr/th[text()='UNIQUE HITS'])");
	}

	public Element getUniqueFamilyHits() {
		return driver.FindElementByXPath("(//table[@id='searchtermTable']//thead/tr/th[text()='UNIQUE FAMILY HITS'])");
	}
	
	public Element getActionBulkRelease() {
		return driver.FindElementByXPath("//a[text()='Bulk Release']");
	}
	
	public Element getBulkRelDefaultSecurityGroup_CheckBox(String SG) {
		return driver.FindElementByXPath("//form[@id='Edit User Group']//div[text()='" + SG + "']/../div[1]/label/i");
	}

	public Element getTotalSelectedDocs() {
		return driver.FindElementByXPath("//span[@id='spanTotal']");
	}
	public Element getBulkRelease_ButtonRelease() {
		return driver.FindElementById("btnRelease");
	}
	public Element getFinalizeButton() {
		return driver.FindElementById("btnfinalizeAssignment");
	}
	
	public ElementCollection getSummaryTableHeader() {
		return driver.FindElementsByXPath("//table[@id='tblSummary']//table//td[@colspan]/label/font");
		}

	//Added By Jeevitha
	public Element getHitsCount() {
        return driver.FindElementByXPath("//label[@id='lblHitsCount']/font/b");
    }
	public Element getActionExportData() {
		return driver.FindElementByXPath("//a[text()=' Export Data']");
	}
	public Element getTotalSelectedCount_fontSize() {
		return driver.FindElementByXPath("//div//label[@id='lblTotal' and @style[contains(.,'font-size: 16px')]]");
	}
	//Added by Iyappan
	public Element getViewinDocListBtn() {
		return driver.FindElementByXPath("//ul[@Class='dropdown-menu']//li//a[text()='View in DocList']");
	}
	public SearchTermReportPage(Driver driver) {
		this.driver = driver;
		bc = new BaseClass(driver);
		reportPage = new ReportsPage(driver);
		dl = new DocListPage(driver);
		// this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		softAssertion = new SoftAssert();
	}

	public void ValidateSearchTermreport(String Searchname) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReport_SearchTerm().Visible();
			}
		}), Input.wait30);
		getReport_SearchTerm().Click();

		getApplyBtn().waitAndClick(20);

		bc.VerifyErrorMessage("Please select at least one search.");
		bc.CloseSuccessMsgpopup();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getST_Selectmysearches().Visible();
			}
		}), Input.wait30);
		getST_Selectmysearches().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApplyBtn().Visible();
			}
		}), Input.wait30);
		getApplyBtn().Click();
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getST_SummaryPage().Visible();
			}
		}), Input.wait60);

		Assert.assertTrue(getST_SummaryPage().Displayed());
		System.out.println("test passed");
	}

	public void TermtoQuickbatch() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getST_SelectHits().Visible();
			}
		}), Input.wait30);
		getST_SelectHits().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getST_Actionsbutton().Visible();
			}
		}), Input.wait30);
		getST_Actionsbutton().waitAndClick(20);
		Thread.sleep(2000);

		getST_Actions_QB().waitAndClick(20);

	}

	/**
	 * @author Raghuram A Date: 9/25/21 Modified date:N/A Modified by: N/A A
	 *         Description :
	 */
	public void ValidateSearchTermreportSaveandImpact(String Searchname, Boolean verifySave)
			throws InterruptedException {

		String reportName = "Report" + Utility.dynamicNameAppender();
		driver.waitForPageToBeReady();
		bc.waitForElement(getSTR_SaveBtn());
		getSTR_SaveBtn().waitAndClick(5);

		bc.waitForElement(getSTR_SaveInputField());
		getSTR_SaveInputField().SendKeys(reportName);

		getSTR_SaveReportBtn().Click();
		if (verifySave) {
			driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
			driver.waitForPageToBeReady();
			reportPage.verifyCustomReportPresent(reportName);
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param searchName
	 * @return
	 */
	public String GenerateReport(String searchName) {
		bc.waitForElement(getSearchTermReport());
		getSearchTermReport().Click();
		driver.waitForPageToBeReady();
		bc.waitForElement(getNodeCheckBox(searchName));
		bc.waitTime(2);
		getNodeCheckBox(searchName).waitAndClick(5);
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitTillElemetToBeClickable(getApplyBtn());
		getApplyBtn().Click();
		driver.waitForPageToBeReady();
		bc.waitTime(8);
		bc.waitForElement(getSTReport());
		int i;
		try {
			i = bc.getIndex(gettableHeaders(), "HITS");
		} catch (Exception e) {
			i = bc.getIndex(gettableHeaders(), "HITS");
		}
		System.out.println(i);
		getRowCheckBox(searchName, i).waitAndClick(20);
		String pureHits = getRowValue(searchName, i).getText();
		return pureHits;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param TagName
	 */

	public String BulkTag(String TagName) throws InterruptedException, AWTException {

		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(20);
		bc.waitForElement(getActionBulkTag());
		getActionBulkTag().Click();
		bc.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(10);
		getAllTagDocument().waitAndClick(10);
		bc.waitTillElemetToBeClickable(getNameTagDocument());
		getNameTagDocument().SendKeys(TagName);
		driver.Manage().window().fullscreen();
		bc.waitForElement(getContinueDocument());
		bc.waitTillElemetToBeClickable(getContinueDocument());
		getContinueDocument().waitAndClick(10);
		driver.Manage().window().maximize();
		driver.waitForPageToBeReady();
		bc.waitForElement(getTotalSelectedDocs());
		String TotalDocs = getTotalSelectedDocs().getText();
		bc.waitTillElemetToBeClickable(getFinalizeTag());
		getFinalizeTag().Click();
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(getFinalizeOkButton());
		getFinalizeOkButton().Click();
		bc.VerifySuccessMessage("Records saved successfully");
		driver.waitForPageToBeReady();
		return TotalDocs;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param folderName
	 */
	public String BulkFolder(String folderName) throws InterruptedException, AWTException {

		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(20);
		bc.waitForElement(getActionBulkFolder());
		getActionBulkFolder().Click();
		bc.waitForElement(getBulkNewTab());
		getBulkNewTab().waitAndClick(10);
		try {
			getAllFoldersTab().waitAndClick(10);
		} catch (Exception e) {
			getAllFoldersTab().waitAndClick(10);
		}
		bc.waitForElement(getFolderNameTxtBox());
		getFolderNameTxtBox().SendKeys(folderName);
		driver.Manage().window().fullscreen();
		bc.waitForElement(getContinueDocument());
		bc.waitTillElemetToBeClickable(getContinueDocument());
		getContinueDocument().waitAndClick(10);
		driver.Manage().window().maximize();
		driver.waitForPageToBeReady();
		bc.waitForElement(getTotalSelectedDocs());
		String TotalDocs = getTotalSelectedDocs().getText();
		bc.waitTillElemetToBeClickable(getFinalizeTag());
		getFinalizeTag().Click();
		bc.VerifySuccessMessage("Records saved successfully");
		driver.waitForPageToBeReady();
		return TotalDocs;
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void ViewInDocView() {
		driver.scrollPageToTop();
		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(20);
		bc.waitForElement(getViewBtn());
		getViewBtn().ScrollTo();
		getViewinDocViewBtn().ScrollTo();
		getViewinDocViewBtn().waitAndClick(30);
		bc.stepInfo("Navigating from Search term report page to view in doc view page.");
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void BulkAssign() {
		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(20);
		getBulkAssignBtn().waitAndClick(30);
		bc.stepInfo("Navigating from Search term report page to assignments creation page.");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param searchName
	 */
	public void GenerateReportWithAllSearches(String[] savedSearchNames) {
		bc.waitForElement(getSearchTermReport());
		getSearchTermReport().Click();
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(mySavedSearchCheckbox());
		for (int i = 0; i < savedSearchNames.length; i++) {
			driver.scrollingToElementofAPage(getNodeCheckBox(savedSearchNames[i]));
			bc.waitTillElemetToBeClickable(getNodeCheckBox(savedSearchNames[i]));
			getNodeCheckBox(savedSearchNames[i]).waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitTillElemetToBeClickable(getApplyBtn());
		getApplyBtn().Click();
		driver.waitForPageToBeReady();
		bc.waitTime(3);		
		bc.waitForElement(getSTReport());
		if (getSTReport().isDisplayed()) {
			bc.stepInfo("Report generated sucessfull");
		} else {
			bc.failedStep("Report not generated sucessfull");
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
	 * @author Jayanthi.ganesan
	 * @param ele
	 * @return
	 */
	public List<Integer> getColumn(ElementCollection ele) {
		List<Integer> elementNames = new ArrayList<>();
		List<WebElement> elementList = null;
		elementList = ele.FindWebElements();
		for (WebElement webElementNames : elementList) {
			String elementName = webElementNames.getText();
			elementNames.add(Integer.parseInt(elementName));
		}
		return elementNames;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param list
	 * @return
	 */
	public int sumUsingList(List<Integer> list) {
		int sum = 0;
		if (list == null || list.size() < 1) {
			return 0;
		} else {
			for (int i : list) {
				sum = sum + i;
			}
		}
		return sum;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param eleValue
	 * @param ele
	 * @throws InterruptedException
	 */
	public void verifyColumnSorting(String eleValue, Element ele) throws InterruptedException {
		int i = getIndex(eleValue);
		List<Integer> HitsBeforeSorting = new ArrayList<>();
		HitsBeforeSorting = getColumn(getColumnValues(i));
		List<Integer> HitsAfterSorting = new ArrayList<>();
		if (HitsBeforeSorting.size() > 0) {
			for (int k = 1; k <= 2; k++) {
				bc.waitForElement(ele);
				ele.Click();
				Thread.sleep(2000);
				String attribute = ele.GetAttribute("aria-sort");
				if (attribute.equalsIgnoreCase("descending")) {
					HitsAfterSorting = getColumn(getColumnValues(i));
					Collections.sort(HitsBeforeSorting, Collections.reverseOrder());
					bc.stepInfo("Verifying sorting fueature of " + eleValue + " when order is " + attribute);
					softAssertion.assertEquals(HitsBeforeSorting, HitsAfterSorting);
				}
				if (ele.GetAttribute("aria-sort").equalsIgnoreCase("ascending")) {
					HitsAfterSorting = getColumn(getColumnValues(i));
					Collections.sort(HitsBeforeSorting);
					bc.stepInfo("Verifying sorting fueature of " + eleValue + " when order is " + attribute);
					softAssertion.assertEquals(HitsBeforeSorting, HitsAfterSorting);
				}
				softAssertion.assertAll();
				bc.passedStep("Sucessfully verified sorting functionality" + " of " + eleValue
						+ "for both ascending and descending order");
			}
		} else {
			bc.stepInfo("List size is zero.");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param saerchlist
	 * @return
	 */
	public List<String> verifySearchInReportsTable(String[] saerchlist) {
		int i = getIndex("SEARCH NAME");
		List<String> searchList = new ArrayList<>();
		List<String> searchListActual = Arrays.asList(saerchlist);
		searchList = bc.getAvailableListofElements(getColumnValues(i));
		if (searchList.size() > 0) {
			bc.passedStep("Report generated for the documents in the selected searches");
			System.out.println(searchList.size());
			System.out.println(searchListActual.size());
			softAssertion.assertTrue(searchListActual.containsAll(searchList));
			softAssertion.assertAll();
			bc.passedStep("Generated report contains all searches selected.");
		} else {
			bc.failedStep("Report not generated for all searches.");
		}
		return searchList;
	}

	/**
	 * @Author Jeevitha
	 * @param Node
	 * @param search
	 */
	public void verifySTRForSearchFromSSPage(String Node, String search) {
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "DataAnalysisReport/SearchTermReport", currentUrl);
		bc.stepInfo("Landed on Search Term Report Page : " + currentUrl);

		bc.waitForElement(getNodeCheckBox(Node));
		if (getNodeCheckBox(Node).isElementAvailable(4)) {
			System.out.println(Node + " : is present And Selected");
			bc.stepInfo(Node + " : is present And Selected");
		} else {
			bc.stepInfo(Node + " : is Not present And Selected");
		}

		bc.waitForElement(getNodeCheckBox(search));
		if (getNodeCheckBox(search).isElementAvailable(4)) {
			System.out.println(search + " : is present And Selected");
			bc.stepInfo(search + " : is present And Selected");
		} else {
			bc.stepInfo(search + " : is Not present And Selected");
		}

		if (getSTReport().isDisplayed()) {
			bc.stepInfo("Report generated sucessfull");
		} else {
			bc.failedStep("Report not generated sucessfull");
		}

	}


	/**
	 * @Author Jeevitha
	 */
	public int verifyaggregateCount(String hits) {
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "DataAnalysisReport/SearchTermReport", currentUrl);
		bc.stepInfo("Landed on Search Term Report Page : " + currentUrl);
		int sumofCount = 0;
		if (getSTReport().isDisplayed()) {
			int i = getIndex(hits);
			List<Integer> Hits = new ArrayList<>();
			Hits = getColumn(getColumnValues(i));
			System.out.println(Hits);
			sumofCount = sumUsingList(Hits);
			bc.stepInfo("Search Term Report generated sucessfully");
			bc.stepInfo("Sum Of searches " + hits + " Count is : " + sumofCount);

		} else {
			bc.failedStep("Search Term Report is not generated sucessfully");
		}
		return sumofCount;

	}

	/** @author Jayanthi.ganesan
	 * @param eleValue
	 * @param searchName
	 * @return
	 */
	
	public String getHitsValueFromRow(String eleValue,String searchName) {
		int i = bc.getIndex(gettableHeaders(),eleValue);
		System.out.println(i);
		String pureHits = getRowValue(searchName, i).getText();
		return pureHits;
	
	}
	
	
	/**
	 * @author Jayanthi.ganesan
	 */
	public String bulkRelease(String SG) {
		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(20);
		getActionBulkRelease().waitAndClick(30);
		bc.stepInfo("Navigating from Search term report page to Security Groups doc release popup.");
		getBulkRelDefaultSecurityGroup_CheckBox(SG).Click();
		bc.waitForElement(getBulkRelease_ButtonRelease());
		getBulkRelease_ButtonRelease().waitAndClick(20);
		bc.waitForElement(getTotalSelectedDocs());
		String TotalDocs = getTotalSelectedDocs().getText();
		bc.waitForElement(getFinalizeButton());
		getFinalizeButton().waitAndClick(20);
		bc.VerifySuccessMessageB("Records saved successfully");
		bc.stepInfo("performing bulk release for "+SG+" docs count is " + TotalDocs);
		return TotalDocs;
}

    /**
	 * @author Jayanthi.ganesan
	 */
	public void STR_ToExportData() {
		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(30);
		bc.waitTime(2);
		bc.waitForElement(getActionExportData());
		getActionExportData().waitAndClick(10);
		bc.stepInfo("Navigating from Search term report page to Export page.");
	}
	public Element getSGSaveSearchNodeCheckBox(String nodeName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor' and text()='Shared with Default Security Group']//..//ul//a[text()='"
				+ nodeName + "']//i[@class='jstree-icon jstree-checkbox']");
	}
	/**
	 * @author Jayanthi.ganesan
	 * @param searchName
	 */
	public void GenerateReportWithSharedWithSGSearches(String[] savedSearchNames) {
		bc.waitForElement(getSearchTermReport());
		getSearchTermReport().Click();
		driver.waitForPageToBeReady();
		bc.waitTillElemetToBeClickable(mySavedSearchCheckbox());
		for (int i = 0; i < savedSearchNames.length; i++) {
			driver.scrollingToElementofAPage(getSGSaveSearchNodeCheckBox(savedSearchNames[i]));
			bc.waitTillElemetToBeClickable(getSGSaveSearchNodeCheckBox(savedSearchNames[i]));
			getSGSaveSearchNodeCheckBox(savedSearchNames[i]).waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitTillElemetToBeClickable(getApplyBtn());
		getApplyBtn().Click();
		driver.waitForPageToBeReady();
		bc.waitTime(3);		
		bc.waitForElement(getSTReport());
		if (getSTReport().isDisplayed()) {
			bc.stepInfo("Report generated sucessfully");
		} else {
			bc.failedStep("Report not generated sucessfully");
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify the documents count in doclist page
	 */
	public String verifyingDocListCount() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();		
		bc.waitForElement(dl.getTableFooterDocListCount());
		bc.waitTime(5);
		String DocListCount = dl.getTableFooterDocListCount().getText();
		driver.waitForPageToBeReady();
		String[] doccount = DocListCount.split(" ");
		String DocumentCount = doccount[5];
		System.out.println("doclist page document count is" + DocumentCount);
		driver.waitForPageToBeReady();
		return DocumentCount;
	}
	
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to view in doclist from searcterm report page
	 */
	public void ViewInDocList() {
		driver.scrollPageToTop();
		bc.waitForElement(getActionButton());
		getActionButton().waitAndClick(20);
		bc.waitForElement(getViewBtn());
		getViewBtn().ScrollTo();
		getViewinDocListBtn().ScrollTo();
		getViewinDocListBtn().waitAndClick(30);
		bc.stepInfo("Navigating from Search term report page to view in doc list page.");
	}

}
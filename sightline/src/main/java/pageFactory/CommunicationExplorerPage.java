package pageFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.sun.org.apache.bcel.internal.util.BCELComparator;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class CommunicationExplorerPage {

	Driver driver;
	BaseClass base;

	public Element getReports_CommunicationsExplorer() {
		return driver.FindElementByCssSelector("a[href*='CommunicationExplorerReport']");
	}

	public Element getTally_SelectSource() {
		return driver.FindElementById("select-source");
	}

	public Element getTally_SecurityGroupsButton() {
		return driver.FindElementByXPath("//strong[contains(.,'Security Groups')]");
	}

	public Element getTally_SelectSecurityGroup() {
		return driver.FindElementByXPath(".//*[@id='secgroup']/li[contains(.,'Default Security Group')]/label");
	}

	public Element getTally_SaveSelections() {
		return driver.FindElementByXPath("//button[@id='secgroup']");
	}

	public Element getTally_SaveSelections_Search() {
		return driver.FindElementByXPath("//button[@id='search']");
	}

	public Element getCommunicationExplorer_ApplyBtn() {
		return driver.FindElementById("btn_applychanges");
	}

	public ElementCollection getfindAllNodes() {
		return driver.FindElementsByCssSelector("div[id='wrapper-graph']  g  text[class='nodetext']");
	}

	public Element getCommunicationExplorer_TotalDocCount_OnHover() {
		return driver.FindElementByCssSelector(".count-total");
	}

	public Element getCommunicationExplorer_Graph_Action_DropDownBtn() {
		return driver
				.FindElementByCssSelector("div[class*='graph-title']  button[class*='btn-primary dropdown-toggle']");
	}

	public Element getCommunicationExplorer_Graph_Action_DropDown() {
		return driver.FindElementById("div[class*='graph-title']  ul[class='dropdown-menu']");
	}

	public Element getCommunicationExplorer_Graph_Action_DropDown_SubTagOption() {
		return driver.FindElementById("a[onclick*='SubTag']");
	}

	public Element getCommunicationExplorer_Graph_Action_DropDown_BulkTag_TotDocCount() {
		return driver.FindElementById("spanTotal");
	}

	public Element getCommunicationExplorer_Graph_Action_DropDown_AnalyzeBtn() {
		return driver.FindElementById("CommtoTally");
	}

	public Element getSelectaTallyFieldtoruntallyon() {
		return driver.FindElementById("select-source1");
	}

	public Element getTally_btnTallyApply() {
		return driver.FindElementById("btnTallyApply");
	}

	public Element getTally_metadataselect() {
		return driver.FindElementById("metadataselect");
	}

	public Element getTally_SubTallyActionButton() {
		return driver.FindElementById("subtallyactionbtn");
	}

	public Element getBG_NotificationPopUp() {
		return driver.FindElementByXPath("//p[contains(text(),'This report is taking a')]");
	}

	public Element getYesButton() {
		return driver.FindElementById("btnYes");
	}

	public Element getAction_ViewInDoclistButton() {
		return driver.FindElementByXPath("//ul[@class='dropdown-menu']//a[contains(.,'in DocList')]");
	}

	public Element getTally_SubTally_Action_ViewButton() {
		return driver.FindElementByXPath(".//*[@id='freezediv']//a[contains(.,'View')]");
	}

	public Element getTally_subMetadata() {
		return driver.FindElementByXPath(".//*[@id='accordion2']//strong[contains(.,'METADATA')]");
	}

	public Element getTally_SubTally_Action_ViewDocList() {
		return driver.FindElementById("idSubTallyViewDoclist");
	}

	public Element getTally_LoadingScreen() {
		return driver.FindElementByXPath("//span[@class='LoadImagePosition']/img[@src='/img/loading.gif']");
	}

	public ElementCollection getElements() {
		return driver.FindElementsByXPath("//*[@class='a-menu']");
	}

	// added by jayanthi
	public Element getReportSaveBtn() {
		return driver.FindElementByXPath("//i[@id='saveReport']");
	}

	public Element getSaveReport_TextField() {
		return driver.FindElementByXPath("//input[@id='txtReportname']");
	}

	public Element getsaveBtn_SavePopUp() {
		return driver.FindElementByXPath("//button[@id='saveXML']");
	}

	public ElementCollection getFilterByTagsList() {
		return driver.FindElementsByXPath("//ul[@id='select2-Tags-results']/li");
	}

	public Element getFilterDocumentsBy_options(String optionName) {
		return driver.FindElementByXPath("//ul[@id='optionFilters']//li[text()='" + optionName + "']");
	}

	public Element searchCriteriaTextBox() {
		return driver.FindElementByXPath("//ul[@class='select2-selection__rendered']/li/input");
	}

	public Element getSelectResourcesOption(String option) {
		return driver.FindElementByXPath("//a[@class='accordion-toggle']//strong[text()='" + option + "']");
	}

	public Element getNodeMySavedSearchCheckBox() {
		return driver.FindElementByXPath(
				"//a[@class='jstree-anchor' and text()='My Saved Search']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getAutosuggestElement(String eleName) {
		return driver.FindElementByXPath("//ul[@class='select2-results__options']/li[text()='" + eleName + "']");
	}

	public Element getUpdateFiltersElement() {
		return driver.FindElementByXPath("(//a[text()='Update Filter'])[1]");
	}

	public Element getActiveFiltersElement() {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li[1]");
	}

	public Element getViewInDocList() {
		return driver.FindElementByXPath("//a[contains(text(),'View All in DocList')]");
	}

	public Element getActionBtn() {
		return driver.FindElementByXPath("//span[@class='fa fa-chevron-down']");
	}

	public Element getVisualizedReportDisplay() {
		return driver.FindElementByCssSelector("g[class='graph']>g[Class*='node normal-node']");
	}

	public Element getVisualizedSelectedReportDisplay() {
		return driver.FindElementByCssSelector("g[class='graph']>g[Class*='node-active']");
	}

	public Element getIncludeRadioBtn() {
		return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[1])");
	}

	public Element getExcludeRadioBtn() {
		return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[2])");
	}

	public Element getAddToFilter() {
		return driver.FindElementByXPath("(//*[contains(text(),' Add to Filter')])");
	}

	public Element getUpdateFilter() {
		return driver.FindElementByXPath("(//*[contains(text(),'Update Filter')])");
	}

	public Element getCommunicationExplorer_Result() {
		return driver.FindElementByXPath("(//div[@class='font-lg col-md-8']//strong)[last()]");
	}

	public Element getViewInDocView() {
		return driver.FindElementByXPath("//ul[@Class='dropdown-menu']//li//a[contains(text(),'in DocView')]");
	}

	public Element getMailCountOFSelectedReport() {
		return driver.FindElementByCssSelector("g>[Class*='node-active']>text>tspan[class='mail-count']");
	}

	public ElementCollection metaDataslist_reviewerPg() {
		return driver.FindElementsByXPath("//div[@id='SearchDataTable_wrapper']//tr[@role='row']/td[position()=2]");
	}

	public Element getCommunicationExplorer_ApplyResult() {
		return driver.FindElementByXPath("//div[@class='font-lg col-md-8']//strong");
	}

	public Element getViewBtn() {
		return driver.FindElementByXPath("//a[@class='submenu-a' and text()='View']");
	}

	public Element getLoadedImage() {
		return driver.FindElementByXPath("//div[@id='processingPopupDiv' and @style='display: none;']");
	}

	public Element getViewInDocViewInNewTab() {
		return driver.FindElementByXPath(
				"//ul[@Class='dropdown-menu']//li//a[contains(text(),'View in DocView in New Tab')]");
	}

	public Element getBackToSourceButton() {
		return driver.FindElementByXPath("//a[contains(text(),'Back to Source')]");
	}

	public Element getSelectedFilterName(String sType, String option, String sgName) {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li[@class='active' and text()='" + sType + " - "
				+ option + ": \"" + sgName + "\"']");
	}

	public Element getActionBulkTag() {
		return driver.FindElementByXPath("//a[text()='Bulk Tag']");
	}

	public Element getActionBulkFolder() {
		return driver.FindElementByXPath("//a[text()='Bulk Folder']");
	}

	public Element getBulkAssignBtn() {
		return driver.FindElementByXPath("//a[text()='Bulk Assign']");
	}

	public Element getSelectedSourcesName(String sType, String sgName) {
		return driver.FindElementByXPath("//ul[@id='bitlist-sources']//li[text()='" + sType + ": " + sgName + "']");
	}

	public Element getShowByCountTop_8_20() {
		return driver.FindElementById("ShowByCount");
	}

	public Element getShowByDomainOrEmail() {
		return driver.FindElementById("ShowBy");
	}

	public Element getShowByVolume() {
		return driver.FindElementById("ShowByVolume");
	}

	public ElementCollection getSmallerNodesList() {
		return driver.FindElementsByCssSelector("g[class='graph']>g[Class='node smaller-node']");
	}

	public ElementCollection getNormalNodesList() {
		return driver.FindElementsByCssSelector("g[class='graph']>g[Class='node normal-node']");
	}

	public Element getSmallerNode() {
		return driver.FindElementByCssSelector("g[class='graph']>g[Class='node smaller-node']");
	}

	public Element getNormalNode() {
		return driver.FindElementByCssSelector("g[class='graph']>g[Class='node normal-node']");
	}

	public Element getMasterDatePopup() {
		return driver.FindElementByXPath("//div[@class='popover bottom in']");
	}

	public Element getMasterDateInput() {
		return driver
				.FindElementByXPath("//div[@class='popover bottom in']//input[@id='MasterDate-1-comminicationmap']");
	}

	public Element getMasterToDateInput() {
		return driver
				.FindElementByXPath("//div[@class='popover bottom in']//input[@id='MasterDate-2-comminicationmap']");
	}

	public Element getSetDateRange() {
		return driver.FindElementByXPath(
				"//div[text()=' Filter by MasterDate:']//..//..//form[@class='smart-form comminicationmap']//select");
	}

	public Element getCalenderIcon() {
		return driver.FindElementByXPath(
				"//div[text()=' Filter by MasterDate:']//..//..//form[@class='smart-form comminicationmap']//i[@class='fa fa-calendar']");
	}

	public CommunicationExplorerPage(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	public void ValidateCommExplorerreport() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReports_CommunicationsExplorer().Visible();
			}
		}), Input.wait30);
		getReports_CommunicationsExplorer().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSource().Visible();
			}
		}), Input.wait30);
		getTally_SelectSource().Click();
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SecurityGroupsButton().Visible();
			}
		}), Input.wait30);
		getTally_SecurityGroupsButton().waitAndClick(10);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSecurityGroup().Visible();
			}
		}), Input.wait30);
		getTally_SelectSecurityGroup().waitAndClick(10);
		// Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SaveSelections().Visible();
			}
		}), Input.wait30);
		getTally_SaveSelections().waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCommunicationExplorer_ApplyBtn().Visible();
			}
		}), Input.wait30);
		getCommunicationExplorer_ApplyBtn().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_LoadingScreen().Visible();
			}
		}), Input.wait30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getTally_LoadingScreen().Stale();
			}
		}), Input.wait30);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getfindAllNodes().Exists();
			}
		}), Input.wait30);
		for (WebElement ele : getfindAllNodes().FindWebElements()) {
			// System.out.println(Configuration.getData("ShareTo")+" -
			// "+ele.getText().trim());
			if (ele.getText().trim().equalsIgnoreCase("symphonyteleca...")) {
				ele.click();
				break;
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCommunicationExplorer_ApplyBtn().Visible();
			}
		}), Input.wait30);
		String actHoverDocCount = getCommunicationExplorer_TotalDocCount_OnHover().getText();
		System.out.println(actHoverDocCount);
		UtilityLog.info(actHoverDocCount);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCommunicationExplorer_Graph_Action_DropDownBtn().Visible();
			}
		}), Input.wait30);
		getCommunicationExplorer_Graph_Action_DropDownBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getAction_ViewInDoclistButton().Enabled();
			}
		}), Input.wait30);
		getAction_ViewInDoclistButton().Click();

		System.out.println("Navigated to Doclist page");
		UtilityLog.info("Navigated to Doclist page");

		final DocListPage dp = new DocListPage(driver);
		dp.getDocList_info().WaitUntilPresent();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return !dp.getDocList_info().getText().isEmpty();
			}
		}), Input.wait60);
		System.out.println(dp.getDocList_info().getText().toString().substring(19, 21));
		UtilityLog.info(dp.getDocList_info().getText().toString().substring(19, 21));
		Assert.assertEquals(dp.getDocList_info().getText().toString().substring(19, 21), actHoverDocCount);
		System.out.println("Expected docs are shown in doclist");
		UtilityLog.info("Expected docs are shown in doclist");
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @description Thsi method will generate report by selecting default security
	 *              group as selected source.
	 */

	public void generateReportusingDefaultSG() throws InterruptedException {
		base.waitForElement(getReports_CommunicationsExplorer());
		getReports_CommunicationsExplorer().waitAndClick(15);
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().waitAndClick(25);
		driver.waitForPageToBeReady();
		try {
			base.waitForElement(getTally_SecurityGroupsButton());
			Actions action = new Actions(driver.getWebDriver());
			action.moveToElement(getTally_SecurityGroupsButton().getWebElement()).click().perform();
		} catch (Exception e) {
			base.waitForElement(getTally_SecurityGroupsButton());
			base.waitTillElemetToBeClickable(getTally_SecurityGroupsButton());
			Actions action = new Actions(driver.getWebDriver());
			action.moveToElement(getTally_SecurityGroupsButton().getWebElement()).click().perform();
			getTally_SecurityGroupsButton().waitAndClick(30);
		}
		base.waitForElement(getTally_SelectSecurityGroup());
		getTally_SelectSecurityGroup().waitAndClick(10);
		base.waitForElement(getTally_SaveSelections());
		getTally_SaveSelections().waitAndClick(15);
		base.waitForElement(getCommunicationExplorer_ApplyBtn());
		getCommunicationExplorer_ApplyBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		for (int i = 0; i < 10; i++) {
			if (getLoadedImage().isElementAvailable(1)) {
				base.waitTime(1);
				continue;
			} else {
				break;
			}
		}
		if (getBG_NotificationPopUp().isElementAvailable(2)) {
			getYesButton().Click();
		} else {
			base.stepInfo("Bulk Navigation pop up not appeared");
		}

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @description This method will save the report as custom report after report
	 *              generated.
	 */
	public void saveReport(String ReportNameToBeSaved) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getReportSaveBtn());
			base.waitTillElemetToBeClickable(getReportSaveBtn());
			// getReportSaveBtn().ScrollTo();
			getReportSaveBtn().waitAndClick(5);
		} catch (Exception e) {
			Actions action = new Actions(driver.getWebDriver());
			action.moveToElement(getReportSaveBtn().getWebElement()).click().perform();
		}
		base.waitForElement(getSaveReport_TextField());
		getSaveReport_TextField().SendKeys(ReportNameToBeSaved);
		getsaveBtn_SavePopUp().waitAndClick(10);
		base.VerifySuccessMessageB("Report save successfully");
		base.stepInfo("Generated Report saved sucessfully with name --" + ReportNameToBeSaved);
		base.CloseSuccessMsgpopup();
	}

	/**
	 * @author Jayanthi.Ganesan
	 */
	public void verifyFilters(String options, boolean status, String TagName) throws InterruptedException {
		getFilterDocumentsBy_options(options).waitAndClick(10);
		searchCriteriaTextBox().waitAndClick(10);
		driver.waitForPageToBeReady();
		Thread.sleep(1000);
		List<String> elementNames = new ArrayList<>();
		elementNames = base.getAvailableListofElements(getFilterByTagsList());
		System.out.println(elementNames.size());
		if (status == true) {
			if (elementNames.contains(TagName)) {
				base.stepInfo("Created Tag Reflected in reports Page.");
				System.out.println(elementNames);
			} else {
				base.stepInfo("Created Tag Not Reflected in reports Page.");
				System.out.println(elementNames);
			}
		} else if (status == false) {
			if (elementNames.contains(TagName)) {
				System.out.println(elementNames);
				base.failedStep("Deleted Tag Reflected in reports Page.");
			} else {
				System.out.println(elementNames);
				base.passedStep("Deleted Tag not reflected in reports Page.");
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param data
	 * @param options
	 * @param data1
	 * @param include
	 */

	public void include(String data, String options, String data1, boolean include) {
		getFilterDocumentsBy_options(options).waitAndClick(10);
		driver.waitForPageToBeReady();
		if (include == true) {
			base.waitForElement(getIncludeRadioBtn());
			getIncludeRadioBtn().Click();
		} else {
			base.waitForElement(getExcludeRadioBtn());
			getExcludeRadioBtn().Click();
		}
		driver.waitForPageToBeReady();
		searchCriteriaTextBox().SendKeys(data);
		base.waitForElement(getAutosuggestElement(data));
		getAutosuggestElement(data).waitAndClick(20);
		if (data1 != null) {
			driver.waitForPageToBeReady();
			searchCriteriaTextBox().SendKeys(data1);
			base.waitForElement(getAutosuggestElement(data1));
			getAutosuggestElement(data1).waitAndClick(20);
		}
		base.waitForElement(getAddToFilter());
		getAddToFilter().waitAndClick(20);
		base.stepInfo("Filters Applied.");
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void excludeAfterInclude() {
		base.waitForElement(getActiveFiltersElement());
		getActiveFiltersElement().waitAndClick(10);
		getExcludeRadioBtn().waitAndClick(10);
		base.waitTillElemetToBeClickable(getUpdateFilter());
		base.waitForElement(getUpdateFilter());
		getUpdateFilter().waitAndClick(20);

	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void clickReport() {
		driver.waitForPageToBeReady();
		base.waitForElement(getVisualizedReportDisplay());
		getVisualizedReportDisplay().ScrollTo();
		getVisualizedReportDisplay().waitAndClick(10);
		for (int i = 0; i < 5; i++) {
			if (getVisualizedSelectedReportDisplay().isElementAvailable(1)) {
				break;
			}
			base.stepInfo("Not clicked on visualized Communications exp report");
			getVisualizedReportDisplay().waitAndClick(30);
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void viewinDoclist() {
		driver.waitForPageToBeReady();
		base.waitForElement(getActionBtn());
		getActionBtn().ScrollTo();
		getActionBtn().waitAndClick(10);
		if (getViewBtn().isElementAvailable(5)) {
			getViewBtn().ScrollTo();
			base.waitTime(1);
			getViewBtn().ScrollTo();
		}
		base.waitForElement(getAction_ViewInDoclistButton());
		getAction_ViewInDoclistButton().waitAndClick(10);
		base.stepInfo("Navigating  to docListPage.");
		driver.waitForPageToBeReady();
	}

	public String VerifyTaggedDocsCountDisplay() {
		String resultValue = getCommunicationExplorer_Result().getText();
		ArrayList<String> result = new ArrayList<String>(Arrays.asList(resultValue.split(" ")));
		System.out.println(result.get(0));
		return result.get(0).trim();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void viewinDocView() {
		driver.scrollPageToTop();
		base.waitForElement(getActionBtn());
		getActionBtn().ScrollTo();
		getActionBtn().waitAndClick(10);
		base.waitForElement(getViewBtn());
		if (getViewBtn().isElementAvailable(5)) {
			getViewBtn().ScrollTo();
		}
		if (getViewInDocView().isElementPresent()) {
			base.passedStep("View in Doc View option is displayed under action "
					+ "dropdown in Communication exp reports page");
			getViewInDocView().ScrollTo();
			getViewInDocView().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.stepInfo("Navigated to docViewPage.");
		} else {
			base.failedStep("View in doc view option is not displayed");

		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	public void generateReportusingSearch() throws InterruptedException {
		base.waitForElement(getReports_CommunicationsExplorer());
		getReports_CommunicationsExplorer().waitAndClick(15);
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().waitAndClick(25);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectResourcesOption("Searches"));
		getSelectResourcesOption("Searches").waitAndClick(5);
		base.stepInfo("Selected searches as source.");
		base.waitForElement(getNodeMySavedSearchCheckBox());
		getNodeMySavedSearchCheckBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTally_SaveSelections_Search());
		getTally_SaveSelections_Search().ScrollTo();
		Actions action = new Actions(driver.getWebDriver());
		action.moveToElement(getTally_SaveSelections_Search().getWebElement()).click().perform();
		driver.scrollPageToTop();
		base.waitForElement(getCommunicationExplorer_ApplyBtn());
		getCommunicationExplorer_ApplyBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getTally_LoadingScreen().Stale();
			}
		}), Input.wait30);
	}

	/**
	 * @author : Iyappan.Kasinathan
	 * @description : This method navigates to docview in new tab from reports page
	 */
	public void viewinDocViewInNewTab() {
		driver.scrollPageToTop();
		base.waitForElement(getActionBtn());
		getActionBtn().ScrollTo();
		getActionBtn().waitAndClick(10);
		base.waitForElement(getViewBtn());
		if (getViewBtn().isElementAvailable(5)) {
			getViewBtn().ScrollTo();
		}
		if (getViewInDocViewInNewTab().isElementPresent()) {
			base.passedStep("View in Doc View in new taboption is displayed under action "
					+ "dropdown in Communication exp reports page");
			getViewInDocViewInNewTab().ScrollTo();
			getViewInDocViewInNewTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.stepInfo("Navigated to docViewPage in new tab successfully.");
		} else {
			base.failedStep("View in doc view in new tab option is not displayed");

		}
	}

	/**
	 * @author : Iyappan.Kasinathan
	 * @description : This method returns doc count for selected item
	 */
	public String selectedDocsCount() {
		base.waitForElement(getMailCountOFSelectedReport());
		String DocCountInreportsPage = getMailCountOFSelectedReport().getText();
		base.stepInfo(DocCountInreportsPage + "  Doc Count in report page that is selected to view in doc view.");
		return DocCountInreportsPage;
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param showCount[only     number as 8 or 20 ]
	 * @param domain_EmailAdress
	 * @param getShowByVolume    this method used select option under show tab in
	 *                           communications explorer page
	 */
	public void selectShowOptions(String showCount, String domain_EmailAdress, String getShowByVolume) {
		String showbyCountTop_Option = "Top " + showCount;
		getShowByCountTop_8_20().selectFromDropdown().selectByVisibleText(showbyCountTop_Option);
		base.waitTime(1);
		base.stepInfo("Selected show by count as  " + showbyCountTop_Option);
		getShowByDomainOrEmail().selectFromDropdown().selectByVisibleText(domain_EmailAdress);
		base.waitTime(1);
		base.stepInfo("Selected show by as  " + domain_EmailAdress);
		getShowByVolume().selectFromDropdown().selectByVisibleText(getShowByVolume);
		base.stepInfo("Selected show by volume as  " + getShowByVolume);
		base.waitTime(1);
	}

	/**
	 * This method performs click on action button and click on bulk action btn.
	 * 
	 * @param tag
	 * @param folder
	 * @param assign
	 */
	public void clickActionBtn(boolean tag, boolean folder, boolean assign) {
		driver.scrollPageToTop();
		base.waitForElement(getActionBtn());
		getActionBtn().ScrollTo();
		getActionBtn().waitAndClick(10);
		if (tag) {
			base.waitForElement(getActionBulkTag());
			getActionBulkTag().Click();
			base.stepInfo("Performing bulk tag.");
		}
		if (folder) {
			base.waitForElement(getActionBulkFolder());
			getActionBulkFolder().Click();
			base.stepInfo("Performing bulk folder.");
		}
		if (assign) {
			base.waitForElement(getBulkAssignBtn());
			getBulkAssignBtn().Click();
			base.stepInfo("Navigating from com explorer page to assignments creation page.");
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 */
	public void navigateToCommunicationExpPage() {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(getReports_CommunicationsExplorer());
		getReports_CommunicationsExplorer().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.verifyPageNavigation("DataAnalysisReport/CommunicationExplorerReport");
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @description This method will generate report by selecting default security
	 *              group as selected source.
	 */

	public void generateReport_DefaultSG() throws InterruptedException {
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().waitAndClick(25);
		driver.waitForPageToBeReady();
		base.waitForElement(getTally_SecurityGroupsButton());
		Actions action = new Actions(driver.getWebDriver());
		action.moveToElement(getTally_SecurityGroupsButton().getWebElement()).click().perform();
		base.waitForElement(getTally_SelectSecurityGroup());
		getTally_SelectSecurityGroup().waitAndClick(10);
		base.waitForElement(getTally_SaveSelections());
		getTally_SaveSelections().waitAndClick(15);
		// Validate retained datas
		base.ValidateElement_Presence(getSelectedSourcesName("Security Group", "Default Security Group"),
				"'Selected source : Default Security Group'.");

	}

	/**
	 * @author Jayanthi.Ganesan This method will perform click operation on apply
	 *         button and handles bulk navigation pop up if appeared.
	 */
	public void clickApplyBtn() {
		base.waitForElement(getCommunicationExplorer_ApplyBtn());
		getCommunicationExplorer_ApplyBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		for (int i = 0; i < 10; i++) {
			if (getLoadedImage().isElementAvailable(1)) {
				base.waitTime(1);
				continue;
			} else {
				break;
			}
		}
		if (getBG_NotificationPopUp().isElementAvailable(2)) {
			base.stepInfo("Bulk Navigation pop up appeared");
			getYesButton().Click();
		} else {
			base.stepInfo("Bulk Navigation pop up not appeared");
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param metaDataType
	 * @param metadataText
	 * @param exclude      this method verify whether the selected filter is
	 *                     reflected in communication exp page.
	 */
	public void filterCriteriaVerify(String metaDataType, String metadataText, boolean exclude) {
		String filterType = null;
		if (exclude) {
			filterType = "Exclude";
		} else {
			filterType = "Include";
		}
		base.ValidateElement_Presence(getSelectedFilterName(metaDataType, filterType, metadataText),
				"Selected filter data : " + getSelectedFilterName(metaDataType, filterType, metadataText).getText()
						+ " retained.");
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 8/10/22
	 * @param selectSourceList
	 * @param sourceToSelect
	 * @param sgToSelect
	 * @param savedSearchName
	 * @param expectedDateInput
	 * @param expectedToDateInput
	 * @param conditionToCheck
	 * @throws ParseException
	 * @description : master Date Verifications
	 */
	public void masterDateVerifications(String[] selectSourceList, String sourceToSelect, String sgToSelect,
			String savedSearchName, String expectedDateInput, String expectedToDateInput, String[] conditionToCheck)
			throws ParseException {

		ReportsPage reports = new ReportsPage(driver);
		DocListPage docList = new DocListPage(driver);

		for (int i = 0; i <= conditionToCheck.length - 1; i++) {
			List<String> masterDateValues = new ArrayList<>();
			String condition = conditionToCheck[i];
			String toDateIp = " ";

			if (condition.equalsIgnoreCase("Between")) {
				toDateIp = expectedToDateInput;
			}

			// Navigate to Communication Explorer page
			base.stepInfo("**Apply masterdate on Page filter  MasterDate: " + condition);
			reports.navigateToReportsPage("Communications Explorer");

			// Select Sources and Apply MasterDate filter - BEFORE - AFTER - ON - BETWEEN
			customizedActions(selectSourceList, sourceToSelect, sgToSelect, savedSearchName, "", expectedDateInput,
					expectedToDateInput, "MasterDate", true, "View in DocList", condition, "", "", false);

			// Master date collection and comparision
			masterDateValues = docList.getColumnValue("MasterDate", false);
			docList.checkMaseterDateAsExpected(masterDateValues, expectedDateInput, toDateIp, condition, "yyyy/MM/dd");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 8/10/22
	 * @param selectSourceList
	 * @param sourceToSelect
	 * @param sgToSelect
	 * @param nameToSelect
	 * @param type
	 * @param expectedDateInput
	 * @param expectedToDateInput
	 * @param filterType
	 * @param datasToSelect
	 * @param viewType
	 * @param masterDateOption
	 * @param additional1
	 * @param addtional2
	 * @param additional3
	 * @description : customizedActions for master date filter
	 */
	public void customizedActions(String[] selectSourceList, String sourceToSelect, String sgToSelect,
			String nameToSelect, String type, String expectedDateInput, String expectedToDateInput, String filterType,
			Boolean datasToSelect, String viewType, String masterDateOption, String additional1, String addtional2,
			Boolean additional3) {

		ConceptExplorerPage conceptExplorer = new ConceptExplorerPage(driver);

		// Select Sources
		conceptExplorer.selectSources(selectSourceList, sourceToSelect, sgToSelect, nameToSelect, "Search", false, "",
				"", false);

		// Master Date filter apply
		if (filterType.equalsIgnoreCase("MasterDate")) {
			// MasterDate as filter.
			masterDateAsInputString(expectedDateInput, expectedToDateInput, masterDateOption);

			// Apply filter
			clickApplyBtn();
			driver.waitForPageToBeReady();
		}

		// Select data to view
		if (datasToSelect) {
			// Select data to view
			selectAllListedDatas(getSmallerNode(), getSmallerNodesList(),null);
			selectAllListedDatas(getNormalNode(), getNormalNodesList(),null);
		}

		// View in DocList
		if (viewType.equalsIgnoreCase("View in DocList")) {
			viewinDoclist();
			base.waitTime(6);
			base.verifyPageNavigation("en-us/Document/DocList");
			driver.waitForPageToBeReady();
		}

	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 8/10/22
	 * @param expectedDateInput
	 * @param actionType
	 * @description : masterDate As InputString
	 */
	public void masterDateAsInputString(String expectedDateInput, String expectedToDateInput, String actionType) {
		// MasterDate as filter.
		getFilterDocumentsBy_options("MasterDate").waitAndClick(10);
		driver.waitForPageToBeReady();
		getSetDateRange().selectFromDropdown().selectByVisibleText(actionType);
		driver.waitForPageToBeReady();
		getMasterDateInput().SendKeys(expectedDateInput);
		driver.waitForPageToBeReady();

		if (actionType.equalsIgnoreCase("Between")) {
			getMasterToDateInput().SendKeys(expectedToDateInput);
			driver.waitForPageToBeReady();
		}
		getCalenderIcon().Click();
		driver.waitForPageToBeReady();

		// Add to filer Action
		getAddToFilter().waitAndClick(20);
		base.stepInfo("Filters Applied with " + actionType);
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 8/10/22
	 * @param element
	 * @param elementCollections
	 * @description : select All Listed Datas from the communication explorer UI
	 */
	public void selectAllListedDatas(Element element, ElementCollection elementCollections,
			Integer nodeCountToSelected) {
		driver.waitForPageToBeReady();
		if (element.isElementAvailable(10)) {
//			base.waitForElementCollection(elementCollections); // to  handle abnormal waits
			base.waitTime(5);
			if (nodeCountToSelected == null) {
				int ecSize = elementCollections.size();
				System.out.println(ecSize);
				for (int i = 1; i <= ecSize; i++) {
					System.out.println(i);
					element.waitAndClick(5);
					driver.waitForPageToBeReady();
				}
			}
			if (nodeCountToSelected != null) {

				for (int i = 1; i <= nodeCountToSelected; i++) {
					System.out.println(i);
					if (element.isElementAvailable(1)) {
						base.waitTime(2);
						// element.ScrollTo();
						element.waitAndClick(5);
						driver.waitForPageToBeReady();
					}
				}

			}

		}
	}
	
}
package pageFactory;

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
	//added by jayanthi
	public Element getReportSaveBtn() {
		return driver.FindElementByXPath("//i[@id='saveReport']");
	}
	public Element getSaveReport_TextField() {
		return driver.FindElementByXPath("//input[@id='txtReportname']");}
	
	public Element getsaveBtn_SavePopUp() {
		return driver.FindElementByXPath("//button[@id='saveXML']");}	
	
	public ElementCollection getFilterByTagsList() {
		return driver.FindElementsByXPath("//ul[@id='select2-Tags-results']/li");}
	public  Element  getFilterDocumentsBy_options(String optionName) {
		return  driver.FindElementByXPath("//ul[@id='optionFilters']//li[text()='"+optionName+"']");
	}
	public Element searchCriteriaTextBox() {
		return  driver.FindElementByXPath("//ul[@class='select2-selection__rendered']/li/input");
	}
	public Element getSelectResourcesOption(String option) {
		return driver.FindElementByXPath("//a[@class='accordion-toggle']//strong[text()='" + option + "']");
	}

	public Element getNodeMySavedSearchCheckBox() {
		return driver.FindElementByXPath(
				"//a[@class='jstree-anchor' and text()='My Saved Search']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getAutosuggestElement(String eleName) {
		return driver.FindElementByXPath("//ul[@id='select2-Tags-results']/li[text()='" + eleName + "']");
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
		return driver.FindElementByCssSelector("g[class='graph']>g[Class='node normal-node']");
	}
	public Element getVisualizedSelectedReportDisplay() {
		return driver.FindElementByCssSelector("g[class='graph']>g[Class='node normal-node node-active']");
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
		return driver.FindElementByCssSelector("g>[class='node normal-node node-active']>text>tspan[class='mail-count']");
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
	 * @description Thsi method will generate report by selecting default security group as selected source.
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
		for(int i=0;i<10;i++) {
			if(getLoadedImage().isElementAvailable(1)) {
				base.waitTime(1);
				continue;
			}
			else {
			break;
		}}
		if(getBG_NotificationPopUp().isElementAvailable(2)) {
		getYesButton().Click();
		}else {
			base.stepInfo("Bulk Navigation pop up not appeared");
		}
		
	}
	/** 
	 * @author Jayanthi.Ganesan
	 * @description  This method will save the report as custom report after report generated.
	 */
	public void saveReport(String ReportNameToBeSaved) {
		try {
		driver.waitForPageToBeReady();
		base.waitForElement(getReportSaveBtn());
		base.waitTillElemetToBeClickable(getReportSaveBtn());
		//getReportSaveBtn().ScrollTo();
		getReportSaveBtn().waitAndClick(5);}
		catch(Exception e) {
			Actions action = new Actions(driver.getWebDriver());
			action.moveToElement(getReportSaveBtn().getWebElement()).click().perform();
		}
		base.waitForElement(getSaveReport_TextField());
		getSaveReport_TextField().SendKeys(ReportNameToBeSaved);
		getsaveBtn_SavePopUp().waitAndClick(10);
		base.VerifySuccessMessageB("Report save successfully");
		base.stepInfo("Generated Report saved sucessfully with name --"+ReportNameToBeSaved);
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
			}
			else {
				base.stepInfo("Created Tag Not Reflected in reports Page.");
				System.out.println(elementNames);
			}
		}
		else if (status == false) {
			if (elementNames.contains(TagName)) {
				System.out.println(elementNames);
				base.failedStep("Deleted Tag Reflected in reports Page.");
			} else {
				System.out.println(elementNames);
				base.passedStep("Deleted Tag not reflected in reports Page.");
			}
		}		}


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
		if(getVisualizedSelectedReportDisplay().isElementAvailable(1)) {
			base.stepInfo("Clicked on visualized Communications exp report");
		}else {
			base.stepInfo("Not clicked on visualized Communications exp report");
			getVisualizedReportDisplay().waitAndClick(30);
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void viewinDoclist() {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitForElement(getActionBtn());
		getActionBtn().waitAndClick(10);
		if(getViewBtn().isElementAvailable(2)) {
			getViewBtn().ScrollTo();
		}
		base.waitForElement(getAction_ViewInDoclistButton());
		getAction_ViewInDoclistButton().waitAndClick(10);
		base.stepInfo("Navigating  to docListPage.");
		driver.waitForPageToBeReady();
	}
	
	public String VerifyTaggedDocsCountDisplay() {
		String resultValue=getCommunicationExplorer_Result().getText();
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
			base.waitForElement(getViewInDocView());
			if(getViewBtn().isElementAvailable(2)) {
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
}
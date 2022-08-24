package pageFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ConceptExplorerPage {

	Driver driver;
	BaseClass base;

	public Element getReports_ConceptExplorer() {
		return driver.FindElementByXPath("//*[@id='collapseOne']//a[contains(.,'Concept Explorer Report')]");
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

	public Element getApplyBtn() {
		return driver.FindElementById("btnAppyFilter");
	}

	// public ElementCollection getfindAllNodes(){ return
	// driver.FindElementsByCssSelector("div[id='wrapper-graph'] g
	// text[class='nodetext']"); }

	public Element gettextpanel() {
		return driver.FindElementByXPath(".//*[@id='textpannel']//span[starts-with(.,'1st Level')]");
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

	public Element getAction_ViewInDoclistButton() {
		return driver.FindElementByXPath(".//*[@class='dropdown-menu']//a[contains(.,'View All in DocList')]");
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
	public Element getfilterDocumentsBy() {
		return driver.FindElementByXPath("//a[@id='lnk_collapsID']/parent::h4");
	}

	public Element getfilterOptions() {
		return driver.FindElementByXPath("//ul[@id='optionFilters']");
	}

	// Added by Raghuram
	public Element getSelectSourcedBtn() {
		return driver.FindElementByXPath("//*[@class = 'btn btn-primary col-md-12' and text()='Select Sources']");
	}
	// a[text()='Select Sources'] - reference

	public Element getSelectSourcedOptions() {
		return driver.FindElementByXPath("//div[@class='custom-popup' and @style=\"display: block;\"]");
	}

	public Element getSelectSourcedOption(String option) {
		return driver.FindElementByXPath("// strong[text()='" + option + "']//..//span[@class='fa fa-plus']");
	}

	public Element getSelectSGOption(String option) {
		return driver.FindElementByXPath("//li//label[text()='" + option + "']//..//input[@type='checkbox']");
	}

	public Element getSaveSelectionsBtn() {
		return driver.FindElementByXPath("//button[@id='secgroup']");
	}

	public ElementCollection getSelectSourcedOptionsList() {
		return driver.FindElementsByXPath("//div[@class='custom-popup' and @style=\"display: block;\"]//strong");
	}

	public Element getApplyFilterBtn() {
		return driver.FindElementByXPath("//a[@id='btnAppyFilter']");
	}

	public ElementCollection getDataToAddInCart() {
		return driver.FindElementsByXPath("//div[@class='node cedefault']//i");
	}

	public Element getDataToAddInCart(int i) {
		return driver.FindElementByXPath("(//div[@class='node cedefault']//i)[" + i + "]");
	}

	public Element getCreateBtn() {
		return driver.FindElementByXPath("//a[text()='Create']");
	}

	public Element getKeywordTextBox() {
		return driver.FindElementByXPath("//input[@placeholder='Enter keyword for highlighting']");
	}

	public Element getKeyWordPopUpOkBtn() {
		return driver.FindElementByXPath("//button[@id='submitSearchTearm']");
	}

	public Element getAppliedKeyword(String keyWord) {
		return driver.FindElementByXPath("//strong[text()='Applied Keyword Heat Map:" + keyWord + "']");
	}

	public Element getAnalyseActionBtn(String text) {
		return driver.FindElementByXPath("//a[@class='btn btn-primary' and text()='" + text + "']");
	}

	public Element getSquareToSelect(int i) {
		return driver.FindElementByXPath("(//div[@class='node cedefault'])[" + i + "]");
	}

	public Element getSpanText() {
		return driver.FindElementByXPath("//span[@class='font-sm ']");
	}

	public Element geClusterID() {
		return driver.FindElementByXPath("//div[@class='popover fade top in']//h3[@class='popover-title']");
	}

	public Element getTileToSelect(int clusterID) {
		return driver.FindElementByXPath("//div[@id='" + clusterID + "' and @class='node cedefault']");
	}

	public Element getTileSelected(int clusterID) {
		return driver.FindElementByXPath("//div[@id='" + clusterID + "' and @class='node cedefault selected']");
	}

	public Element getHighlightedNodeNames(int i) {
		return driver.FindElementByXPath("(//div[contains(@class,'purple')]//span)[" + i + "]");
	}

	public ElementCollection getCartAddedTilesName() {
		return driver.FindElementsByXPath("//ul[@id='ulResultsCart']//label");
	}

	public Element getKeywordHighlightedNodesPlusBtn(int i) {
		return driver.FindElementByXPath("(//div[contains(@class,'purple')]//i)[" + i + "]");
	}

	public ElementCollection getKeywordHighlightedNodesPlusBtns() {
		return driver.FindElementsByXPath("(//div[contains(@class,'purple')]//i)");
	}

	public Element getActionBtn() {
		return driver.FindElementByXPath("//button[@id='idAction']");
	}

	public Element getActionType(String actionTpye) {
		return driver.FindElementByXPath("//a[text()='" + actionTpye + "']");
	}

	public Element getPageLevel(String level) {
		return driver.FindElementByXPath(
				"//div[@id='textpannel']//span[contains(text(),'" + level + " Level Conceptual Map')]");
	}

	public Element getTileAddedSelected(int clusterID) {
		return driver.FindElementByXPath("//div[@id='" + clusterID + "' and @class='node cedefault added selected']");
	}

	public Element getAttributefromSquare(int i) {
		return driver.FindElementByXPath("//div[contains(@class,'cedefault')][" + i + "]");
	}

	public Element getTotalSelectedDocCount() {
		return driver.FindElementByXPath("//span[@class='text-muted']");
	}

	public Element getBackToSourceButton() {
		return driver.FindElementByXPath("//a[contains(text(),'Back to Source : ')]");
	}

	public Element getBackToSourceBtn() {
		return driver.FindElementByXPath("//a[contains(text(),'Back to Source')]");
	}

	public Element getFilterDocumentsBy_options(String option) {
		return driver.FindElementByXPath("//ul[@id='optionFilters']//li[text()='" + option + "']");
	}

	public Element getIncludeExcludeRadioBtn(String option) {
		return driver
				.FindElementByXPath("//div[text()=' Filter by CustodianName:']//..//..//input[@id='" + option + "']");
	}

	public Element searchCriteriaTextBox() {
		return driver.FindElementByXPath("//ul[@class='select2-selection__rendered']/li/input");
	}

	public Element getAutosuggestElement(String eleName) {
		return driver.FindElementByXPath("//ul[@class='select2-results__options']/li[text()='" + eleName + "']");
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

	public Element getNoResultData() {
		return driver.FindElementByXPath("//li[text()='No results found']");
	}

	public Element getDocCOuntFromHeader() {
		return driver.FindElementByXPath("//span[@class='font-lg']");
	}

	public Element getUpdateFilter() {
		return driver.FindElementByXPath(
				"//div[@class='popover-content']//a[@class='btn btn-primary active' and text()='Update Filter']");
	}

	public Element getActiveFiltersElement() {
		return driver.FindElementByXPath("//div[@id='activeFilters']//li");
	}

	public Element getWaitForBgTaskToComplete() {
		return driver.FindElementByXPath("//div[@role='dialog']//span[text()='Wait for Task to Complete?']");
	}

	public Element getWaitForBgTaskToCompleteAction(String action) {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='" + action + "']");
	}

	public Element getNodeFromTab(String groupName, String nodeName) {
		return driver.FindElementByXPath("//a[text()='" + groupName + "']//..//li//a[text()='" + nodeName + "']");
	}

	public Element getSaveSearchSelectionsBtn() {
		return driver.FindElementByXPath("//button[@id='search']");
	}

	public Element getspinningWheel() {
		return driver.FindElementByXPath("//div[@id= 'processingPopupDiv' and @style='']");
	}

	public Element getSelectedSourcesName(String sType, String sgName) {
		return driver.FindElementByXPath("//ul[@id='bitlist-sources']//li[text()='" + sType + ": " + sgName + "']");
	}

	public Element getMasterDatePopup() {
		return driver.FindElementByXPath("//div[@class='popover bottom in']");
	}

	public Element getMasterDateInput() {
		return driver
				.FindElementByXPath("//div[@class='popover bottom in']//input[@id='MasterDate-1-CONCEPTEXPLORER']");
	}

	public Element getMasterToDateInput() {
		return driver
				.FindElementByXPath("// div[@class='popover bottom in']//input[@id='MasterDate-2-CONCEPTEXPLORER']");
	}

	public Element getSetDateRange() {
		return driver.FindElementByXPath(
				"//div[text()=' Filter by MasterDate:']//..//..//form[@class='smart-form CONCEPTEXPLORER']//select");
	}

	public Element getCalenderIcon() {
		return driver.FindElementByXPath(
				"//div[text()=' Filter by MasterDate:']//..//..//form[@class='smart-form CONCEPTEXPLORER']//i[@class='fa fa-calendar']");
	}

	public ElementCollection getActiveFilters() {
		return driver.FindElementsByXPath("//div[@id='activeFilters']//li");
	}

	public ConceptExplorerPage(Driver driver) {

		this.driver = driver;
		// this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		base = new BaseClass(driver);
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	public void ValidateConceptExplorerreport() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReports_ConceptExplorer().Visible();
			}
		}), Input.wait30);
		getReports_ConceptExplorer().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSource().Visible();
			}
		}), Input.wait30);
		getTally_SelectSource().Click();
		// Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SecurityGroupsButton().Enabled();
			}
		}), Input.wait30);
		getTally_SecurityGroupsButton().waitAndClick(5);
		// Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSecurityGroup().Enabled();
			}
		}), Input.wait30);
		getTally_SelectSecurityGroup().waitAndClick(5);
		// Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SaveSelections().Visible();
			}
		}), Input.wait30);
		getTally_SaveSelections().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApplyBtn().Visible();
			}
		}), Input.wait30);
		getApplyBtn().Click();

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getTally_LoadingScreen().Visible() ;}}), Input.wait30);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws
		 * Exception{return getTally_LoadingScreen().Stale() ;}}), Input.wait30);
		 */
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettextpanel().Visible();
			}
		}), Input.wait30);
		gettextpanel().Displayed();

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getCommunicationExplorer_Graph_Action_DropDownBtn().Visible() ;}}),
		 * Input.wait30); getCommunicationExplorer_Graph_Action_DropDownBtn().Click();
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws
		 * Exception{return getAction_ViewInDoclistButton().Enabled() ;}}),
		 * Input.wait30); getAction_ViewInDoclistButton().Click();
		 * 
		 * System.out.println("Navigated to Doclist page");
		 * 
		 * final DocListPage dp = new DocListPage(driver);
		 * dp.getDocList_info().WaitUntilPresent(); driver.WaitUntil((new
		 * Callable<Boolean>() {public Boolean call(){return
		 * !dp.getDocList_info().getText().isEmpty() ;}}), 60000, null, 1000);
		 * System.out.println(dp.getDocList_info().getText().toString(). substring(19,
		 * 21)); Assert.assertEquals(dp.getDocList_info().getText().toString().
		 * substring(19, 21),actHoverDocCount);
		 * System.out.println("Expected docs are shown in doclist");
		 */
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void navigateToConceptExplorerPage() {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String expectedURL = Input.url + "DataAnalysisReport/ConceptExplorer";
		base.waitForElement(getReports_ConceptExplorer());
		if (getReports_ConceptExplorer().isDisplayed()) {
			base.passedStep("Concept Explorer Report link is displayed in reports landing page");
			getReports_ConceptExplorer().Click();
			SoftAssert softAssertion = new SoftAssert();
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(expectedURL, driver.getUrl());
			softAssertion.assertAll();
			base.passedStep("Sucessfully navigated to Concept Explorer Report Page");
		} else {
			base.failedStep("Concept Explorer Report link is not  displayed in reports landing page");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param selectSourceList
	 */
	public void verifySourceList(String[] selectSourceList) {
		try {
			driver.waitForPageToBeReady();
			List<String> listDatas = base.getAvailableListofElements(getSelectSourcedOptionsList());
			base.printListString(listDatas);
			base.compareArraywithDataList(selectSourceList, listDatas, true,
					"Expected sources displayed in select source popup",
					"Expected sources not displayed in select source popup");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Source List display Error");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/04/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @Description : Select Security group
	 * @param sourceToSelect
	 * @param sgToSelect
	 */
	public void selectSGsource(String sourceToSelect, String sgToSelect) {
        driver.waitForPageToBeReady();
        getSelectSourcedOption(sourceToSelect).waitAndClick(15);
        getSelectSGOption(sgToSelect).waitAndClick(5);
        getSaveSelectionsBtn().waitAndClick(10);
    }

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/04/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @Description : Perform Add to cart
	 * @param index
	 */
	public void addToCartIndex(int index) {
		try {
			getDataToAddInCart(index).waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Not able to perform Add to Cart");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/05/22
	 * @param keyWord
	 * @param apply
	 */
	public void addKeyWordHighlighting(String keyWord, boolean apply) {
		if (apply) {
			base.waitForElement(getCreateBtn());
			getCreateBtn().waitAndClick(10);
			base.waitForElement(getKeywordTextBox());
			getKeywordTextBox().SendKeys(keyWord);
			base.waitForElement(getKeyWordPopUpOkBtn());
			getKeyWordPopUpOkBtn().waitAndClick(10);
		}
		base.ValidateElement_Presence(getAppliedKeyword(keyWord), "Applied HeatMap Keyword '" + keyWord + "'. ");
	}

	/**
	 * @author Raghuram.A
	 * @param i
	 * @return
	 */
	public String hoverOnSpecificConcepTualMapReturnText(int i) {
		base.mouseHoverOnElement(getSquareToSelect(i));
		driver.waitForPageToBeReady();
		System.out.println("-------------------");
		String textContent = getSpanText().getText();
		return textContent;
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 05/19/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @param mouseHover
	 * @param position
	 * @param hoverId
	 * @return
	 */
	public String getClusterID(Boolean mouseHover, int position, int hoverId) {
		if (mouseHover) {
			base.mouseHoverOnElement(getSquareToSelect(hoverId));
			driver.waitForPageToBeReady();
		}
		String clusterIDValue = geClusterID().getText();
		clusterIDValue = clusterIDValue.substring(clusterIDValue.lastIndexOf(" ") + position);
		return clusterIDValue;
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 05/13/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @param textContent   - text content to retrive required datas
	 * @param containsCheck - list of datas to check
	 * @param position
	 * @param clusterID
	 * @return - returns hashMap datas retrived from the input textcontent
	 */
	public HashMap<String, String> getDataFromParticularArrayString(String textContent, String[] containsCheck,
			int position, Boolean clusterID, Boolean splitData) {
		HashMap<String, String> dataPair = new HashMap<>();
		String expArratyToString, lastWord = null;
		String[] textContentArray = textContent.split("\n");
		for (int i = 0; i < textContentArray.length; i++) {
			System.out.println(i);
			System.out.println(textContentArray[i]);
			for (int k = 0; k < containsCheck.length; k++) {
				if (textContentArray[i].contains(containsCheck[k])) {
					expArratyToString = textContentArray[i];
					if (splitData) {
						lastWord = expArratyToString.substring(expArratyToString.lastIndexOf(" ") + position);
						dataPair.put(containsCheck[k], lastWord);
					} else {
						dataPair.put(containsCheck[k], expArratyToString);
					}
				}
			}
		}
		return dataPair;
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/05/22
	 * @param actionType
	 */
	public void performActions(String actionType) {

		// Perform View in DocView/DocList Action
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getActionBtn().waitAndClick(10);
			driver.waitForPageToBeReady();
			getActionType(actionType).waitAndClick(10);
			base.stepInfo("Clicked : " + actionType);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("View Action failed");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/05/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @param analyze
	 */
	public void analyzeAction(String analyze) {
		if (getAnalyseActionBtn(analyze).isElementAvailable(3)) {
			base.stepInfo(analyze + " button is enabled to click");
			getAnalyseActionBtn(analyze).ScrollTo();
			getAnalyseActionBtn(analyze).waitAndClick(3);
			base.stepInfo("Clicked : " + analyze);
			driver.waitForPageToBeReady();
		} else {
			base.stepInfo(analyze + " button is disabled");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/05/22
	 * @param limitCheck
	 */
	public String tileSelctionBasedOnChildCountReturnClusterID(int limitCheck, int docCount) {
		// Verify child count
		String clusterID = null;
		for (int k = 1; k <= limitCheck; k++) {
			int clusterCount = Integer.parseInt(getAttributefromSquare(k).GetAttribute("childcount"));
			if (clusterCount > docCount) {
				String textContent = hoverOnSpecificConcepTualMapReturnText(k);

				// Cluster ID
				clusterID = getClusterID(false, 1, limitCheck);
				base.stepInfo("Cluster Id : " + clusterID);

				int tCid = Integer.parseInt(clusterID);
				getTileToSelect(tCid).waitAndClick(5);
				driver.waitForPageToBeReady();
			}
		}
		return clusterID;
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/05/22
	 * @param limitCheck
	 * @param docCount
	 */
	public void tileSelctionBasedOnChildCount(int limitCheck, int docCount) {
		for (int k = 1; k <= limitCheck; k++) {
			int clusterCount = Integer.parseInt(getAttributefromSquare(k).GetAttribute("childcount"));
			if (clusterCount > docCount) {
				getSquareToSelect(k).waitAndClick(3);
				break;
			}
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 04/21/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @Description : Perform Doc View/List Actions
	 * @param action     - View / ...
	 * @param actionType - Action type existing tab / new tab
	 */
	public void performDocActions(String action, String actionType) {

		// Perform View in DocView/DocList Action
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getActionBtn().waitAndClick(10);
			driver.waitForPageToBeReady();
			Actions ac = new Actions(driver.getWebDriver());
			ac.moveToElement(getActionType(action).getWebElement()).build().perform();
			getActionType(actionType).waitAndClick(10);
			base.stepInfo("Clicked : " + actionType);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("View Action failed");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param actionType
	 * @param actualUserName
	 * @param expectedUserType
	 */
	public void performSpecificActions(String actionType, String actualUserName, String expectedUserType) {
		if (actualUserName.equalsIgnoreCase(expectedUserType)) {
			try {
				driver.scrollPageToTop();
				driver.waitForPageToBeReady();
				getActionBtn().waitAndClick(10);
				driver.waitForPageToBeReady();
				getActionType(actionType).waitAndClick(10);
				base.stepInfo("Clicked : " + actionType);
			} catch (Exception e) {
				e.printStackTrace();
				base.failedStep("View Action failed");
			}
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 04/21/22
	 * @ModifiedOn : N/A
	 * @ModifiedBy : N/A
	 * @Description : Filter Action
	 * @param data      - input value
	 * @param options   - action options
	 * @param data1     - autosuggest data
	 * @param selection - include or exclude
	 */
	public void filterAction(String data, String options, String data1, Boolean include) {
		try {
			// Fiter option
			filterSelection(options, include);

			// Input text for applying filter
			searchCriteriaTextBox().SendKeys(data);
			base.waitForElement(getAutosuggestElement(data));
			getAutosuggestElement(data).waitAndClick(20);
			if (data1 != null) {
				driver.waitForPageToBeReady();
				searchCriteriaTextBox().SendKeys(data1);
				base.waitForElement(getAutosuggestElement(data1));
				getAutosuggestElement(data1).waitAndClick(20);
			}

			// Add to filer Action
			getAddToFilter().waitAndClick(20);
			driver.waitForPageToBeReady();
			List<String> activeFilters = base.getAvailableListofElements(getActiveFilters());
			base.stepInfo("Applied Filters are " + activeFilters.toString());

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Error in Applying filter");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/08/22
	 * @param data
	 * @param options
	 * @param include
	 * @return
	 */
	public Boolean filterActionResultStatus(String data, String options, Boolean include) {

		// Fiter option
		filterSelection(options, include);

		// Input text for applying filter
		searchCriteriaTextBox().SendKeys(data);
		if (getNoResultData().isElementAvailable(15)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/08/22
	 * @Description : Filter Selection against options
	 * @param options
	 * @param include
	 */
	public void filterSelection(String options, Boolean include) {
		// Fiter option
		getFilterDocumentsBy_options(options).waitAndClick(10);
		driver.waitForPageToBeReady();

		// Include or Exclude option
		if (include) {
			base.waitForElement(getIncludeRadioBtn());
			getIncludeRadioBtn().Click();
		} else {
			base.waitForElement(getExcludeRadioBtn());
			getExcludeRadioBtn().waitAndClick(5);
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/08/22
	 * @Description : Extract String from the required position
	 * @param textContent
	 * @param position
	 * @return
	 */
	public String extractStringFromPosition(String textContent, int position) {
		String[] arrOfStr = null;
		// Get Doc count consolidated
		base.stepInfo(textContent);
		arrOfStr = textContent.split(" ");
		String aggregatedDocCount = arrOfStr[arrOfStr.length - position];
		return aggregatedDocCount;
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/08/22
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
	 * @author Jayanthi
	 * @Description : Click Select Sources
	 */
	public void clickSelectSources() {
		try {
			getSelectSourcedBtn().waitAndClick(3);
			base.ValidateElement_Presence(getSelectSourcedOptions(), "Select sources popup");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Select Sources button not available / not enabled");
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * 
	 * @param sourceToSelect
	 * @param sgToSelect
	 * @param searchName
	 * @param nodeName
	 * @param additional1
	 * @param additional2
	 */

	public void selectSearchessource(String sourceToSelect, String sgToSelect, String searchName, String nodeName,
			Boolean additional1, String additional2) {
		driver.waitForPageToBeReady();
		if (getSelectSourcedOption(sourceToSelect).isElementAvailable(10)) {
			getSelectSourcedOption(sourceToSelect).waitAndClick(10);
		}
		getNodeFromTab(sgToSelect, searchName).waitAndClick(10);
		base.waitTime(5);
		getSaveSearchSelectionsBtn().ScrollTo();
		base.waitTime(5);
		getSaveSearchSelectionsBtn().waitAndClick(10);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Jayanthi
	 * @param action
	 */
	public void backgroundWait(String action, int checkTiming) {

		for (int i = 0; i < checkTiming; i++) {
			if (getspinningWheel().isElementAvailable(1)) {
				base.waitTime(1);
				continue;
			} else {
				break;
			}
		}

		// background Action
		if (getWaitForBgTaskToComplete().isElementAvailable(3)) {
			getWaitForBgTaskToCompleteAction(action).waitAndClick(3);
			System.out.println("Bg popup handled");
		} else {
			System.out.println("No BG popup within the expected time : " + checkTiming);
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Jayanthi
	 * @param action
	 * @param bgTask
	 * @param waitTiming
	 */
	public void applyFilter(String action, int waitTiming) {
		driver.waitForPageToBeReady();
		getApplyFilterBtn().waitAndClick(3);
		base.stepInfo("Apply Filter action done");

		// Background action Wait
		backgroundWait(action, waitTiming);
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 8/8/22
	 * @param expectedDateInput
	 * @param actionType
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
	 */
	public void addAllTilesToCart() {
		// Select data to 'Add to cart'
		base.waitForElementCollection(getDataToAddInCart());
		int resultToAddInCart = getDataToAddInCart().size();
		System.out.println(resultToAddInCart);

		for (int i = 1; i <= resultToAddInCart; i++) {
			System.out.println(i);
			if (getDataToAddInCart(1).isElementAvailable(5)) {
				hoverOnSpecificConcepTualMapReturnText(1);
				getDataToAddInCart(1).waitAndClick(5);
				driver.waitForPageToBeReady();
			}
		}
	}

	/**
	 * @author Raghuram.A
	 * @param selectSourceList
	 * @param sourceToSelect
	 * @param sgToSelect
	 * @param nameToSelect
	 * @param type
	 * @param verifySourceList
	 * @param additional1
	 * @param addtional2
	 * @param additional3
	 */
	public void selectSources(String[] selectSourceList, String sourceToSelect, String sgToSelect, String nameToSelect,
			String type, Boolean verifySourceList, String additional1, String addtional2, Boolean additional3) {
		// Select Sources
		clickSelectSources();
		if (verifySourceList) {
			base.stepInfo("**To verify to click the source option available in concept explorer page");
			verifySourceList(selectSourceList);
		}
		base.stepInfo("*Select any one of the source and save selection");

		if (type.equalsIgnoreCase("Search")) {
			selectSearchessource(sourceToSelect, sgToSelect, nameToSelect, "", false, "");
			base.ValidateElement_Presence(getSelectedSourcesName("Search", nameToSelect),
					"Selected source : " + nameToSelect + " and Saved selection");
		}
	}

	/**
	 * @author Raghuram.A
	 * @param selectSourceList
	 * @param sourceToSelect
	 * @param sgToSelect
	 * @param nameToSelect
	 * @param type
	 * @param expectedDateInput
	 * @param expectedToDateInput
	 * @param filterType
	 * @param addToCart
	 * @param viewType
	 * @param masterDateOption
	 * @param additional1
	 * @param addtional2
	 * @param additional3
	 */
	public void customizedActions(String[] selectSourceList, String sourceToSelect, String sgToSelect,
			String nameToSelect, String type, String expectedDateInput, String expectedToDateInput, String filterType,
			Boolean addToCart, String viewType, String masterDateOption, String additional1, String addtional2,
			Boolean additional3) {

		// Select Sources
		selectSources(selectSourceList, sourceToSelect, sgToSelect, nameToSelect, "Search", false, "", "", false);

		if (filterType.equalsIgnoreCase("MasterDate")) {
			// MasterDate as filter.
			masterDateAsInputString(expectedDateInput, expectedToDateInput, masterDateOption);

			// Apply filter
			applyFilter("Yes", 10);
			driver.waitForPageToBeReady();
		}

		// Select data to 'Add to cart'
		if (addToCart) {
			addAllTilesToCart();
		}

		// View in DocList
		if (viewType.equalsIgnoreCase("View in DocList")) {
			performDocActions("View", "View in DocList");
			base.waitTime(6);
			base.verifyPageNavigation("en-us/Document/DocList");
			driver.waitForPageToBeReady();
		}

	}

	/**
	 * @author Raghuram.A
	 * @param selectSourceList
	 * @param sourceToSelect
	 * @param sgToSelect
	 * @param savedSearchName
	 * @param expectedDateInput
	 * @param expectedToDateInput
	 * @param conditionToCheck
	 * @throws ParseException
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

			// Navigate to Concept Explorer page
			base.stepInfo("**Apply masterdate on Page filter  MasterDate: " + condition);
			reports.navigateToReportsPage("Concept Explorer Report");

			// Select Sources and Apply MasterDate filter - BEFORE - AFTER - ON - BETWEEN
			customizedActions(selectSourceList, sourceToSelect, sgToSelect, savedSearchName, "", expectedDateInput,
					expectedToDateInput, "MasterDate", true, "View in DocList", condition, "", "", false);

			// Master date collection and comparision
			masterDateValues = docList.getColumnValue("MasterDate", false);
			docList.checkMaseterDateAsExpected(masterDateValues, expectedDateInput, toDateIp, condition, "yyyy/MM/dd");
		}
	}

	/**
	 * @author jayanthi
	 * @Description : Perform Add to cart
	 * @param CountOfTileToBeAdded
	 */
	public String addMultipleTilesToCart(int CountOfTileToBeAdded) {
		String[] arrOfStr = null;
		String aggregatedDocCount = null;
		driver.waitForPageToBeReady();
		if (getNoResultData().isElementAvailable(5)) {
			base.failedStep("No results displayed[no tiles]");
		} else {
			for (int i = 1; i <= CountOfTileToBeAdded; i++) {

				getDataToAddInCart(CountOfTileToBeAdded).waitAndClick(10);

			}
			// Segregating total docs count from the display
			String totalSelectedDocCount = getTotalSelectedDocCount().getText();
			arrOfStr = totalSelectedDocCount.split(" ");
			aggregatedDocCount = arrOfStr[arrOfStr.length - 3];
			base.stepInfo("Total Doc count added to cart : " + aggregatedDocCount);
		}
		return aggregatedDocCount;
	}

	/**
	 * @author Jayanthi.Ganesan This method will verify the Include filter
	 *         functionality working properly.
	 * @param metaDataWithTwoData[meta data for which two datas are applied in
	 *                                 filter]
	 * @param metaDataWithOneData[meta data for which one datas are applied in
	 *                                 filter]
	 * @param metaData1[               data for which two datas are applied in one
	 *                                 meta data filter]
	 * @param metaData_1[              data for which two datas are applied in one
	 *                                 meta data filter]
	 * @param metaData[                data for which one data is applied in one
	 *                                 meta data filter]
	 */
	public void verifyIcludeFiltersLikeOR_Operator(List<String> metaDataWithTwoData, List<String> metaDataWithOneData,
			String metaData1, String metaData_1, String metaData) {
		boolean status = false;
		String metaData_twoOption;
		for (int i = 0; i < metaDataWithTwoData.size(); i++) {
			 metaData_twoOption=metaDataWithTwoData.get(i).toLowerCase();
			if ((metaData_twoOption.contains(metaData_1.toLowerCase())) || (metaData_twoOption.contains(metaData1.toLowerCase()))) {
				status = true;

				if ((metaDataWithOneData.get(i).toLowerCase().contains(metaData.toLowerCase())) && status) {

					continue;
				} else {
					base.failedStep("Meta Data are not filtered as expected.");
				}

			} else {
				base.failedStep("Meta Data are not filtered as expected.");

			}
		}

		base.passedStep(
				"**After validating  the Include apllied filters from Concpet explorer Tiles via Doc List We observed follwing things**");
		base.passedStep("1. Multiple values for a filter is considered as OR operator.");

		base.passedStep("2. Multiple filters should be considered as AND operator.");
	}

	/**
	 * @author Jayanthi.Ganesan This method will verify the Exclude filter
	 *         functionality working properly.
	 * @param metaDataWithTwoData[meta data for which two datas are applied in
	 *                                 filter]
	 * @param metaDataWithOneData[meta data for which one datas are applied in
	 *                                 filter]
	 * @param metaData1[               data for which two datas are applied in one
	 *                                 meta data filter]
	 * @param metaData_1[              data for which two datas are applied in one
	 *                                 meta data filter]
	 * @param metaData[                data for which one data is applied in one
	 *                                 meta data filter]
	 */
	public void verifyExcludeFiltersLikeOR_Operator(List<String> metaDataWithTwoData, List<String> metaDataWithOneData,
			String metaData1, String metaData_1, String metaData) {
		boolean status = false;
		String metaData_twoOption;
		for (int i = 0; i < metaDataWithTwoData.size(); i++) {
			 metaData_twoOption=metaDataWithTwoData.get(i).toLowerCase();
			if (!(metaData_twoOption.contains(metaData_1.toLowerCase()))
					|| (metaData_twoOption.contains(metaData1.toLowerCase()))) {
				status = true;
				if (!(metaDataWithOneData.get(i).toLowerCase().contains(metaData.toLowerCase())) && status) {
					continue;
				} else {
					base.failedStep("Meta Data are not filtered as expected.");
				}

			} else {
				base.failedStep("Meta Data are not filtered as expected.");
			}
		}
		base.passedStep(
				"**After validating the apllied Exclude filters from Concpet explorer Tiles via Doc List We observed follwing things**");
		base.passedStep("1. Multiple values for a filter is considered as OR operator.");

		base.passedStep("2. Multiple filters should be considered as AND operator.");
	}

	/**
	 * @author Jayanthi.Ganesan This method will return the apllied active filters
	 *         and compare the active filters with expected filters list
	 * @param expFilters
	 * @return
	 */
	public List<String> verifyActiveFilters(List<String> expFilters) {

		List<String> activeFilters = base.getAvailableListofElements(getActiveFilters());
		if (expFilters == null) {
			base.stepInfo("Applied Filters are " + activeFilters.toString());
		}
		if (expFilters != null)
			base.listCompareEquals(expFilters, activeFilters,
					"Applied Active Filters Retained " + activeFilters.toString(),
					"Applied Active Filters not Retained");
		return activeFilters;
	}
/**
 * @author Jayanthi.Ganesan
 * @param limitCheck  [iteration to be checked in for loop]
 * @param docCount[total direct sub clusters count]
 * @param tileCountToSelect[no of tile to be selected]
 */
	public void tileSelctionAnalyze_BasedChildCount(int limitCheck, int docCount, int tileCountToSelect) {
		// Verify child count
		String clusterID = null;
		int tileSelected = 0;
		for (int k = 1; k <= limitCheck; k++) {
			int clusterCount = Integer.parseInt(getAttributefromSquare(k).GetAttribute("childcount"));
			if (clusterCount > docCount) {
				tileSelected = tileSelected+1;
				 hoverOnSpecificConcepTualMapReturnText(k);
				// Cluster ID
				clusterID = getClusterID(false, 1, limitCheck);
				base.stepInfo("Cluster Id : " + clusterID);

				int tCid = Integer.parseInt(clusterID);
				getTileToSelect(tCid).waitAndClick(5);
				driver.waitForPageToBeReady();
				if (tileCountToSelect == tileSelected) {
					break;
				}
			}
		}
	
	}

}

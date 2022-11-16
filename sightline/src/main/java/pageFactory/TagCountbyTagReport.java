package pageFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class TagCountbyTagReport {

	Driver driver;
	BaseClass bc;
	SoftAssert softAssertion;

	public Element getReport_TagCount() {
		return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Tag Counts')]");
	}

	public Element getApplyBtn() {
		return driver.FindElementById("btn_applychanges");
	}

	public Element getTags_Expandbutton() {
		return driver.FindElementByXPath("//*[@id='divTagList']/div[1]/a[2]");
	}

	public Element getTag_Selecttag(String tagname) {
		return driver.FindElementByXPath("//*[@id='collapsetags2']//a[contains(text(),'" + tagname + "')]");
	}

	public Element getDate_Expandbutton() {
		return driver.FindElementByXPath("//*[@id='divdateonly']/div/a[2]");
	}

	public Element getDateRangeFrom() {
		return driver.FindElementById("from");
	}

	public Element getDateRangeTo() {
		return driver.FindElementById("to");
	}

	public Element getDatetabledata() {
		return driver.FindElementByXPath(".//*[@id='ui-datepicker-div']/table/tbody");
	}

	public Element getTagReporttable(int colno) {
		return driver.FindElementByXPath("//*[@id='TagcountReportTable']//td[" + colno + "]");
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

	public Element getDataAsOfToday() {
		return driver.FindElementById("DataAsOf");
	}

	// Added by Jayanthi
	public Element getExportBtn() {
		return driver.FindElementByXPath("//i[@id='exportExcel']");
	}

	public Element getBullHornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getBullHornNotification() {
		return driver.FindElementByXPath("//b[@class='badge bg-color-red bounceIn animated']");
	}

	public Element getFileDownloaded() {
		return driver.FindElementByXPath("(//a[text()='Your export is ready please click here to download'])[1]");
	}

	public Element getTagTypes() {
		return driver.FindElementByXPath("//strong//span[text()='Tag Types']");
	}

	public Element getTagGroups() {
		return driver.FindElementByXPath("//strong//span[text()='Tags']");
	}

	public Element TagREportsTable() {
		return driver.FindElementByXPath("//table[@id='TagcountReportTable']");
	}

	public Element getReportBtn() {
		return driver.FindElementByXPath(".//a[@class='a-menu']//i[@class='fa fa-lg fa-fw fa-bar-chart-o']");
	}

	public Element getAllTagReport() {
		return driver.FindElementByXPath("//*[@id='collapsetags2']//a[contains(.,'All Tags')]");
	}

	public Element getResetBtn() {
		return driver.FindElementByXPath("//a[@id='ResetReportID']");
	}

	public Element getAllTagReportClicked() {
		return driver.FindElementByXPath(
				"//*[@id='collapsetags2']//a[@Class='jstree-anchor jstree-clicked' and contains(.,'All Tags')]");
	}

	public Element getTableText() {
		return driver.FindElementByXPath("//*[text()='Please select criteria and apply changes to run report.']");
	}

	public Element getTagCheckBox(String tagName) {
		return driver.FindElementByXPath("//ul[@class='jstree-children']//li//a[@data-content='" + tagName + "']");
	}

	public ElementCollection getTableHeaders() {
		return driver.FindElementsByXPath("//table[@id='TagcountReportTable']/thead/tr/th");
	}

	public ElementCollection getTableRow(int i) {
		return driver.FindElementsByXPath("//table[@id='TagcountReportTable']/tbody/tr/td[" + i + "]");
	}

	public Element getTableRowUsingTagname(String tagname, int i) {
		return driver.FindElementByXPath("(//table[@id='TagcountReportTable']/tbody/tr/td[text()='" + tagname
				+ "']/following-sibling::td)[" + i + "]");
	}

	public TagCountbyTagReport(Driver driver) {
		this.driver = driver;
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
//		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

	}

	public void ValidateTagCountreport(final String tagname) throws InterruptedException, ParseException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReport_TagCount().Visible();
			}
		}), Input.wait30);
		getReport_TagCount().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTags_Expandbutton().Visible();
			}
		}), Input.wait60);
		getTags_Expandbutton().Click();

		Thread.sleep(5000);

		getTag_Selecttag(tagname).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return getDate_Expandbutton().Enabled();
			}
		}), Input.wait30);
		getDate_Expandbutton().Click();

		bc.SelectCurrentdatfromDatePicker(getDateRangeFrom(), getDatetabledata());
		Thread.sleep(2000);

		bc.SelectCurrentdatfromDatePicker(getDateRangeTo(), getDatetabledata());
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApplyBtn().Visible();
			}
		}), Input.wait30);
		getApplyBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagReporttable(1).Visible();
			}
		}), Input.wait60);
		String time = bc.getcurrentdateinGMT();
		System.out.println(time);

		/*
		 * //validate tag name driver.WaitUntil((new Callable<Boolean>() {public Boolean
		 * call(){return getTagReporttable(1).Visible() ;}}), Input.wait60); String
		 * tagnameonreport = getTagReporttable(1).getText();
		 * System.out.println(tagnameonreport);
		 * 
		 * softAssertion.assertEquals(tagnameonreport, tagname);
		 * 
		 * String tagcountonreport = getTagReporttable(2).getText();
		 * System.out.println(Integer.parseInt(tagcountonreport));
		 * 
		 * softAssertion.assertEquals(tagcountonreport, "50");
		 * 
		 * softAssertion.assertAll();
		 */

	}

	/**
	 * @author Jayanthi.ganesan
	 *         This method will generate Tag Count by Tag report
	 * @param tagname
	 * @param flag[if given 'true'it will redirect to export page and verify download]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public void GenerateTagCountreport(final String tagname, boolean flag) throws InterruptedException, ParseException {

		bc.waitForElement(getReport_TagCount());
		getReport_TagCount().Click();
		bc.waitForElement(getTags_Expandbutton());
		getTags_Expandbutton().Click();
		bc.waitForElement(getTag_Selecttag(tagname));
		getTag_Selecttag(tagname).waitAndClick(10);
		bc.waitForElement(getApplyBtn());
		getApplyBtn().Click();
		if (flag = true) {
			driver.waitForPageToBeReady();
			bc.waitForElement(getExportBtn());
			getExportBtn().waitAndClick(10);
			driver.waitForPageToBeReady();
			// verify color of bullhorn
			String color2 = getBullHornIcon().GetCssValue("color");
			color2 = bc.rgbTohexaConvertor(color2);
			System.out.println(color2);
			if (color2.equalsIgnoreCase("#E74735")) {
				System.out.println("Notification is [Red color] : " + color2);
				bc.stepInfo("Notification is [Red color] : " + color2);
			} else {
				Thread.sleep(10000);// Thread.sleep added to wait until notification displayed after downlaod
			}
			bc.waitForElement(getBullHornIcon());
			getBullHornIcon().waitAndClick(30);
			bc.waitForElement(getFileDownloaded());
			getFileDownloaded().waitAndClick(30);
			getBullHornIcon().waitAndClick(30);
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 *         This method will perform Reset Functionality is working.
	 * @throws InterruptedException
	 */

	public void ValidateResetFunctionality() throws InterruptedException {
		bc.waitForElement(getReport_TagCount());
		getReport_TagCount().Click();
		bc.stepInfo("Verify that Tags count by Tags page is visible and clicked");
		bc.waitForElement(getTags_Expandbutton());
		getTags_Expandbutton().Click();
		bc.stepInfo("Verify that once Tags_Expand button is visible and clicked is displayed all docments");
		bc.waitForElement(getAllTagReport());
		getAllTagReport().Click();
		getApplyBtn().waitAndClick(10);
		bc.waitForElement(TagREportsTable());
		if (TagREportsTable().Displayed()) {
			bc.passedStep("All tags checkbox is clicked and Report is displayed as per the criteria selected.");
		} else {
			bc.failedMessage("Report is not displayed as per the criteria selected.");
		}
		driver.waitForPageToBeReady();
		bc.waitForElement(getResetBtn());
		getResetBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		if (TagREportsTable().isElementAvailable(5)) {
			bc.failedMessage("Generated report is not disapppeared after clicking reset button");
		} else {
			bc.waitForElement(getTags_Expandbutton());
			getTags_Expandbutton().Click();
			if (getAllTagReportClicked().isElementAvailable(5)) {
				bc.failedMessage("Current selected criteria is not changed to default selection criteria.");
				softAssertion.assertTrue(false);
			} else {
				bc.passedStep("Current selected criteria is changed to default selection criteria.");
				softAssertion.assertTrue(true);
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 *         This method will generate report when input gioven is  Two tags.
	 * @param tagname
	 * @param tagname1
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public void GenerateTagCountreport_Multipletags(String tagname, String tagname1)
			throws InterruptedException, ParseException {
		bc.waitForElement(getReport_TagCount());
		getReport_TagCount().Click();
		bc.waitForElement(getTags_Expandbutton());
		getTags_Expandbutton().Click();
		driver.scrollingToElementofAPage(getTag_Selecttag(tagname1));
		bc.waitForElement(getTag_Selecttag(tagname));
		getTag_Selecttag(tagname).waitAndClick(10);
		driver.scrollingToElementofAPage(getTag_Selecttag(tagname));
		bc.waitForElement(getTag_Selecttag(tagname1));
		getTag_Selecttag(tagname1).ScrollTo();
		getTag_Selecttag(tagname1).waitAndClick(20);
		driver.scrollingToElementofAPage(getApplyBtn());
		bc.stepInfo("selcted  tags from tag selection group.");
		bc.waitForElement(getApplyBtn());
		getApplyBtn().Click();
		bc.waitForElement(TagREportsTable());
		if (TagREportsTable().Displayed()) {
			bc.passedStep(" Tag counts by tag Report generated.");
		} else {
			bc.failedStep("Report not generated.");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * This method will verify whether the tags are reflected in report or not.
	 * @return
	 */

	public List<String> getTagNamesFromReport() {
		List<String> elementNames = new ArrayList<>();
		bc.waitForElement(TagREportsTable());
		if (TagREportsTable().Displayed()) {
			int i = bc.getIndex(getTableHeaders(), "TAG NAME (TAG TYPE)");
			elementNames = bc.availableListofElements(getTableRow(i));
			System.out.println(elementNames);
			System.out.println(elementNames.size());
			System.out.println(i);
			bc.stepInfo("Total Tags reflected in reports after clicking on apply button -" + elementNames.size());
		} else {
			bc.failedStep("Report not generated.");
		}
		return elementNames;
	}
/**
 * @author Jayanthi.ganesan
 * This method will verify Doc Count for given Tags displayed in report table
 * @param tagName[Name of Tag whose doc count to be validated from report]
 * @param Count[Doc count to be verified in Report table]
 * @param i[Index Value of Table Header Element]
 * @return
 */
	
	public boolean verifyTagDocCount(String tagName, String Count, int i) {
		String docCount = null;
		boolean status = false;
		docCount = getTableRowUsingTagname(tagName, i).getText();
		System.out.println(docCount);
		if (docCount.equals(Count)) {
			status = true;
			bc.stepInfo("doc count displayed in reports page is expected.");
		} else {
			status = false;
			bc.stepInfo("doc count displayed in reports page is not as  expected.");
		}

		return status;
	}
	/**
	 * @author jayanthi
	 */
	public void navigateToReportPage() {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		if (driver.getUrl().contains("Report/ReportsLanding")) {
			bc.stepInfo("Navigated to reports page");
		} else {
			bc.failedStep("Report page not loaded.");
		}
		
	}
}
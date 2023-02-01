package pageFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class CustomDocumentDataReport {
	Driver driver;
	BaseClass bc;
	SoftAssert softAssertion;

	public Element getCustomDocumentDataReport() {
		return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Custom Document Data Report')]");
	}

	public Element getTally_SelectSource() {
		return driver.FindElementById("select-source");
	}

	public Element getTally_SecurityGroupsButton() {
		return driver.FindElementByXPath("//strong[contains(.,'Security Groups')]");
	}

	public Element getTally_SelectSecurityGroup(String sourceValue) {
		return driver
				.FindElementByXPath("//label[@class='checkbox']//span[contains(text(),'" + sourceValue + "')]//..//i");
	}

	public Element getTally_SaveSelections() {
		return driver.FindElementByXPath("//button[@id='secgroup']");
	}

	public Element getMetdataField(String metaDataField) {
		return driver
				.FindElementByXPath("//*[@id='tab1-export']/div/ul/li/label/strong[text()='" + metaDataField + "']");
	}

	public Element getAddToSelectedBtn() {
		return driver.FindElementById("addFormObjects-coreList");
	}

	public Element getSwapMetaDataFiledSource(int poistion) {
		return driver.FindElementByXPath("//div[@id='nestable']//*[@id='" + poistion + "']/div[1]");
	}

	public Element getSwapMetaDataFiledDest(int poistion) {
		return driver.FindElementByXPath("//div[@id='nestable']//*[@id='" + poistion + "']/div[1]");
	}

	public Element getRunReport() {
		return driver.FindElementById("btnRunReport");
	}

	public Element getBckTask_selectExport() {
		return driver
				.FindElementByXPath("(//a[contains(text(),'Your export is ready please click here to download')])[1]");
	}

	public Element getMetadataTab() {
		return driver.FindElementByXPath("//*[@data-toggle='tab']/*[text()='METADATA']");
	}

	public Element getWorkProductTab() {
		return driver.FindElementByXPath("//*[@data-toggle='tab']/*[text()='WORKPRODUCT']");
	}

	public Element getWorkProductField(String workProductField) {
		return driver.FindElementByXPath(
				"//*[@class='tab-pane left-panel active']/div//a[contains(text(),'" + workProductField + "')]");
	}

	public Element getExportFieldBtn() {
		return driver.FindElementById("fieldSelect");
	}

	public Element getExportFieldValue(String value) {
		return driver.FindElementByXPath("//*[@id='fieldSelect']/*[contains(text(),'" + value + "')]");
	}

	public Element getExportTextBtn() {
		return driver.FindElementById("textSelect");
	}
	
	public Element getExportCloseBtn() {
		return driver.FindElementByXPath("/html/body/div[11]/div[1]/button");
	}
	public Element getExportTextValue(String value) {
		return driver.FindElementByXPath("//*[@id='textSelect']/*[contains(text(),'" + value + "')]");
	}

	public Element getSelectedExports(String value) {
		return driver.FindElementByXPath("//*[@id='coreList']//*[text()='" + value + "']");
	}

	public Element getSaveReportBtn() {
		return driver.FindElementById("saveReport");
	}

	public Element getSaveBtn() {
		return driver.FindElementById("saveXML");
	}

	public Element getSaveReportName() {
		return driver.FindElementById("txtReportname");
	}

	public Element getReportName(String reportName) {
		return driver.FindElementByXPath("//*[text()='" + reportName + "']");
	}

	// added by jayanthi
	public Element getTally_SearchesButton() {
		return driver.FindElementByXPath("//strong[contains(.,'Searches')]");
	}

	public Element getSavedSearchCheckBox(String nodeName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor' and text()='My Saved Search']//..//ul//a[text()='"
				+ nodeName + "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getTally_SaveSelections_Search() {
		return driver.FindElementByXPath("//button[@id='search']");
	}

	public ElementCollection getSelectedFieldsList() {
		return driver.FindElementsByXPath("//span[@class='itemFriendlyName']");
	}

	public Element getDefaultWorkproductCheckBox(String eleValue) {
		return driver.FindElementByXPath("//*[@id='tab2-export']//div/ul/li/a[text()='" + eleValue + "']");
	}

	public Element getToggle_ObjectName() {
		return driver.FindElementByXPath("//label//i[@id='exportObject']");
	}

	public Element getToggle_ScrubSpecChar() {
		return driver.FindElementByXPath("//div[@id='divCcrubSpecialChar']//i");
	}

	public Element getMetaDataFields_withoutLabel(int i) {
		return driver.FindElementByXPath("(//div[@id='tab1-export']/div/ul/li/label)[" + i + "]");
	}

	public Element getWrkProductHeaders(String eleValue) {
		return driver.FindElementByXPath("//a[text()='" + eleValue + "']/parent::li");
	}

	public Element getSelectedSourceName() {
		return driver.FindElementByXPath("//ul[@id='bitlist-sources']/li");
	}

	// added by sowndarya

	public Element getSpecialCharDD(int ddNo) {
		return driver.FindElementById("scrubSpecialCharFieldSet" + ddNo);
	}

	public Element getReplaceCharDD(int ddNo) {
		return driver.FindElementById("scrubReplaceCharFieldSet" + ddNo);
	}

	public Element getSpecialCharDDLast() {
		return driver.FindElementByXPath("(//select[contains(@id, 'scrubSpecialCharFieldSet')])[last()]");
	}

	public Element getReplaceCharLast() {
		return driver.FindElementByXPath("(//select[contains(@id, 'scrubReplaceCharFieldSet')])[last()]");
	}

	public Element getRedXIcon(int numDD) {
		return driver.FindElementByXPath("//tr[@id='tablerow" + numDD + "']//i");
	}

	public Element getShareIcon() {
		return driver.FindElementById("ReportReviewer");
	}

	public Element getEmailtextarea() {
		return driver.FindElementByXPath("//textarea[@id='txtEmail']");
	}

	public Element getLastUser() {
		return driver.FindElementByXPath("(//ul[@class='multiselect-container dropdown-menu']//label)[last()]");
	}

	public Element getShareBtn() {
		return driver.FindElementById("btnSaveShareReport");
	}

	public Element getUserDD(String username) {
		return driver.FindElementByXPath("//label[normalize-space()='" + username + "']");
	}

	public Element getShareUsersDD() {
		return driver.FindElementByXPath("//button[@class='multiselect dropdown-toggle btn']");
	}

	public Element getSharedID(int index) {
		return driver.FindElementByXPath("(//div[@id='bgShared']//a)[" + index + "]");
	}

	public Element getSharedAlert() {
		return driver.FindElementByXPath("//label[@id='idShared']//span");
	}

	public Element getSharedNotification(String ID) {
		return driver.FindElementByXPath("//div[@id='bgShared']//a[contains(text(),'" + ID + "')]");
	}

	public Element getCloseBtn_ScrubPopup() {
		return driver.FindElementByXPath("//div[@aria-describedby='SelectScrubPopup']//button");
	}

	public Element getScrubSpecialCharcterField() {
		return driver.FindElementByXPath("//table[@id='table-scrub']//select[@id='scrubSpecialCharFieldSet2']");
	}

	public Element getAddNewBtn_ScrubLink() {
		return driver.FindElementById("addNew");
	}

	public Element getScrubLink() {
		return driver.FindElementById("btnScrubExportSpecialChar");
	}

	public Element getRedXIcon() {
		return driver.FindElementByXPath("//tr[@id='tablerow1']//i");
	}

	public Element getExportNewLineBtn() {
		return driver.FindElementById("newLineSelect");
	}

	public Element getExportNewlineValue(String value) {
		return driver.FindElementByXPath("//*[@id='newLineSelect']/*[contains(text(),'" + value + "')]");
	}

	public Element getExportDateStyleDD() {
		return driver.FindElementById("dateStyleSelect");
	}

	public Element getExportStyleDD() {
		return driver.FindElementById("styleSelect");
	}

	public CustomDocumentDataReport(Driver driver) {

		this.driver = driver;
		// this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	public void selectSource(String sourceName, final String sourceValue) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCustomDocumentDataReport().Visible();
			}
		}), Input.wait30);

		getCustomDocumentDataReport().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSource().Visible();
			}
		}), Input.wait30);
		getTally_SelectSource().Click();
		Thread.sleep(2000);
		if (sourceName.equalsIgnoreCase("Security Groups")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTally_SecurityGroupsButton().Visible();
				}
			}), Input.wait30);
			getTally_SecurityGroupsButton().waitAndClick(10);
			Thread.sleep(2000);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTally_SelectSecurityGroup(sourceValue).Visible();
				}
			}), Input.wait30);
			getTally_SelectSecurityGroup(sourceValue).waitAndClick(10);
			// Thread.sleep(2000);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SaveSelections().Visible();
			}
		}), Input.wait30);
		getTally_SaveSelections().waitAndClick(5);

	}

	public void selectMeataDatFields(String[] Mfields) {
		for (int i = 0; i < Mfields.length; i++) {
			driver.scrollingToBottomofAPage();
			getMetdataField(Mfields[i]).waitAndClick(5);
		}
		getAddToSelectedBtn().Click();
	}

	public void swapMetaDataFields(int source, int dest) {

		new Actions(driver.getWebDriver()).dragAndDrop(getSwapMetaDataFiledSource(source).getWebElement(),
				getSwapMetaDataFiledDest(dest).getWebElement()).perform();

	}

	public void downloadExport() throws InterruptedException {
		// Take Back up and clear all files

		final BaseClass base = new BaseClass(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.getBackgroundTask_Button().Visible();
			}
		}), Input.wait60);
		base.getBackgroundTask_Button().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBckTask_selectExport().Visible();
			}
		}), Input.wait30);
		getBckTask_selectExport().Click();

		// download time
		Thread.sleep(5000);

	}

	public void validateColumnsInExport(ArrayList expected) throws IOException {
		ArrayList<String> actual = new ArrayList();
		File root = new File(System.getProperty("user.dir") + Input.batchFilesPath + "BatchPrintFiles/downloads/");
		File[] list = root.listFiles();
		System.out.println(list.length);
		UtilityLog.info(list.length);
		// Create an object of filereader
		// class with CSV file as a parameter.
		FileReader filereader = new FileReader(list[0]);

		// file reader as a parameter
		CSVReader csvReader = new CSVReader(filereader);
		String[] nextRecord;

		// we are going to read data line by line
		try {
			while ((nextRecord = csvReader.readNext()) != null) {
				for (String cell : nextRecord) {

					actual.add(cell.toString().trim().replaceAll("ï»¿\"", ""));
					// System.out.println(cell.toString());
					// System.out.println(cell.toString().replaceAll("ï»¿\"",""));

				}

				break;
			}
		} catch (CsvValidationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Selected columns order : " + expected);
		UtilityLog.info("Selected columns order : " + expected);
		System.out.println("Columns order in export  : " + actual);
		UtilityLog.info("Columns order in export  : " + actual);
		for (int i = 0; i < expected.size(); i++) {
			Assert.assertTrue(expected.get(i).equals(actual.get(i)));
		}

	}

	public void reportRunSuccessMsg() {
		BaseClass bc = new BaseClass(driver);
		bc.VerifySuccessMessage(
				"Your Report has been added into the Background successfully. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");

	}

	public void selectExportFieldFormat(String fieldvalue) {
		getExportFieldBtn().waitAndClick(2);
		getExportFieldValue(fieldvalue).waitAndClick(2);

	}

	public void selectExportTextFormat(String textvalue) {
		getExportTextBtn().Click();
		getExportTextValue(textvalue).waitAndClick(5);

	}

	public void validateSelectedExports(String[] value) {
		for (int i = 0; i < value.length; i++) {
			driver.scrollPageToTop();
			if (getSelectedExports(value[i]).isDisplayed()) {
				driver.waitForPageToBeReady();
				softAssertion.assertTrue(true);
				bc.passedStep("Selected metadata fields and work products " + value[i]
						+ "  added under 'Selected for Export' section");
			} else {
				bc.failedStep("Selected metadata fields and work products " + value[i]
						+ " not added under 'Selected for Export' section");
			}
		}

	}

	public ArrayList<String> expectedValuesInReport(String[] value, ArrayList<String> expected) {

		for (int i = 0; i < value.length; i++) {
			driver.scrollPageToTop();
			expected.add(value[i]);

		}
		return expected;
	}

	/**
	 * @author Jayanthi.Ganesan This method will save the custom document data
	 *         report
	 * @param reportName
	 */
	public void SaveReport(String reportName) {

		if (getSaveReportBtn().isElementAvailable(2)) {
			getSaveReportBtn().ScrollTo();
			getSaveReportBtn().waitAndClick(2);
		}
		getSaveReportName().isElementAvailable(3);
		getSaveReportName().Click();
		getSaveReportName().Clear();
		getSaveReportName().SendKeys(reportName);
		getSaveBtn().Click();
		bc.CloseSuccessMsgpopup();
		bc.waitForElementToBeGone(bc.getCloseSucessmsg(), 30);
	}

	public void selectMetaDataFields(String[] Mfields) {
		driver.scrollPageToTop();
		bc.waitTillElemetToBeClickable(getMetadataTab());
		getMetadataTab().Click();
		for (int i = 0; i < Mfields.length; i++) {
			getMetdataField(Mfields[i]).ScrollTo();
			bc.waitForElement(getMetdataField(Mfields[i]));
			getMetdataField(Mfields[i]).waitAndClick(5);
		}
		bc.waitForElement(getAddToSelectedBtn());
		getAddToSelectedBtn().waitAndClick(2);
		driver.scrollPageToTop();
	}

	public void selectWorkProductFields(String[] Wfields) {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		bc.waitTime(3);
		getWorkProductTab().ScrollTo();
		bc.waitTillElemetToBeClickable(getWorkProductTab());
		getWorkProductTab().waitAndClick(10);
		bc.waitTime(2);
		for (int i = 0; i < Wfields.length; i++) {
			bc.waitForElement(getWorkProductField(Wfields[i]));
			bc.waitTime(2);
			driver.waitForPageToBeReady();
			getWorkProductField(Wfields[i]).ScrollTo();
//			bc.waitTillElemetToBeClickable(getWorkProductField(Wfields[i]));
			getWorkProductField(Wfields[i]).waitAndClick(10);
		}
		bc.waitForElement(getAddToSelectedBtn());
		getAddToSelectedBtn().waitAndClick(5);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param fileLocation
	 * @param fileName
	 * @return
	 */
	public String csvfileVerification(String fileLocation, String fileName) {
		FileReader file = bc.fileLocate(fileLocation, fileName);
		String value = null;
		try {
			BufferedReader br = new BufferedReader(file);
			List<String> lines = new ArrayList<>();
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line.trim().replaceAll("ï»¿\"", ""));
			}
			System.out.println(lines);
			System.out.println(lines.get(0));
			value = lines.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param sourceValue
	 * @throws InterruptedException
	 */
	public void selectSourceAsSearch(String sourceValue) throws InterruptedException {
		getCustomDocumentDataReport().Click();
		getTally_SelectSource().Click();
		getTally_SearchesButton().waitAndClick(10);
		getSavedSearchCheckBox(sourceValue).waitAndClick(10);
		getTally_SaveSelections_Search().waitAndClick(5);

	}

	/**
	 * @author Jayanthi.ganesan
	 * @return
	 * @throws InterruptedException
	 */
	public List<String> verifyMetaDatswap() throws InterruptedException {
		List<String> slectdFieldListbforeSwap = new ArrayList<>();
		slectdFieldListbforeSwap = bc.getAvailableListofElements(getSelectedFieldsList());
		dragAndDrop();
		List<String> slectdFieldList = new ArrayList<>();
		slectdFieldList = bc.getAvailableListofElements(getSelectedFieldsList());
		if (!slectdFieldListbforeSwap.equals(slectdFieldList)) {
			bc.passedStep("Selected field are swapped");
			Reporter.log("Selected Fields list before swapping" + slectdFieldListbforeSwap);
			Reporter.log("Selected Fields list After swapping" + slectdFieldList);
		} else {
			bc.failedStep("Selected field are not swapped as expected ");
		}
		return slectdFieldList;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @return
	 * @throws InterruptedException
	 */

	public String runReportandVerifyFileDownloaded() throws InterruptedException {
		final int Bgcount = bc.initialBgCount();

		String Filename = bc.GetLastModifiedFileName();
		if (Filename == null) {
			Filename = "Directory Empty";
		}
		bc.stepInfo(Filename + "Last Modified File name before Downloading the report");
		getRunReport().waitAndClick(10);
		driver.waitForPageToBeReady();
		reportRunSuccessMsg();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		downloadExport();
		bc.waitUntilFileDownload();
		String Filename2 = bc.GetLastModifiedFileName();
		bc.stepInfo(Filename2 + "Last Modified File name after Downloading the report");
		if (Filename.equals(Filename2)) {
			bc.failedStep("File not downloaded after the export.");
		} else {
			bc.passedStep("File downloaded after the export-" + Filename2);
		}
		return Filename2;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	public void dragAndDrop() throws InterruptedException {
		Actions actions = new Actions(driver.getWebDriver());
		actions.clickAndHold((getSwapMetaDataFiledSource(1)).getWebElement());
		System.out.println("Click and hold performed");
		actions.moveToElement(getSwapMetaDataFiledSource(1).getWebElement(), 0, 23);
		actions.moveToElement(getSwapMetaDataFiledSource(1).getWebElement(), 0, 24);
		actions.release();
		actions.build().perform();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param Wfields
	 */
	public void selectDefaultWorkProductFields(String[] Wfields) {
		getWorkProductTab().Click();
		for (int i = 0; i < Wfields.length; i++) {
			driver.scrollingToBottomofAPage();
			getDefaultWorkproductCheckBox(Wfields[i]).waitAndClick(5);
		}
		getAddToSelectedBtn().waitAndClick(2);
		driver.scrollPageToTop();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */

	public void verifyMetaFieldDisplay() {
		getMetadataTab().Click();
		bc.stepInfo("Clicked on metadata Field tab");
		for (int i = 1; i <= 14; i++) {
			if (getMetaDataFields_withoutLabel(i).isDisplayed()) {
				softAssertion.assertTrue(true);
				continue;

			} else {
				softAssertion.assertTrue(false);
				break;
			}
		}
		softAssertion.assertAll();
		bc.passedStep("Available objects rows under metadata tab displayed upto 14 rows by default. ");

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param Wfields
	 */
	public void validateWrkprductHeaders(String[] Wfields) {
		getWorkProductTab().Click();
		bc.stepInfo("Clciked on workproduct Tab");
		for (int i = 0; i < Wfields.length; i++) {
			String status = getWrkProductHeaders(Wfields[i]).GetAttribute("aria-expanded");
			System.out.println(status);
			softAssertion.assertEquals("true", status);
		}
		softAssertion.assertAll();
		bc.passedStep("Available objects rows under workproduct tab displayed and expanded by default. ");
	}

	/**
	 * @author Jayanthi.Ganesan This method will select source from export page
	 * @param sourceName[Source name like security group,Folder,tag]
	 * @param sourceValue[Tag   Name ,Folder name to be selected under Folder/Tag
	 *                          Tree ]
	 * @throws InterruptedException
	 */
	public void selectSources(String sourceName, final String sourceValue) throws InterruptedException {
		bc.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		if (sourceName.equalsIgnoreCase("Security Groups")) {
			bc.waitForElement(getTally_SecurityGroupsButton());
			getTally_SecurityGroupsButton().waitAndClick(10);
			bc.waitForElement(getTally_SelectSecurityGroup(sourceValue));
			getTally_SelectSecurityGroup(sourceValue).waitAndClick(10);
		}
		bc.waitForElement(getTally_SaveSelections());
		driver.scrollingToElementofAPage(getTally_SaveSelections());
		getTally_SaveSelections().waitAndClick(5);

	}

	/**
	 * @author Jayanthi.Ganesan This method will verify whether the selected source
	 *         details are reflected in export page.
	 * @param sourceType[Source type-Ex: Advanced Search,Saved Search,Doc List,etc]
	 */
	public void validateSourceSelction(String sourceType) {
		driver.waitForPageToBeReady();
		String sourceName = getSelectedSourceName().getText();
		switch (sourceType) {

		case "save search":
			if (sourceName.equalsIgnoreCase("Selected Documents from Save Search")) {
				bc.passedStep(sourceName + " selected source name reflected in export page as expected.");
			} else {
				bc.failedStep(sourceName + " selected source name reflected in export page which is not expected.");
			}
			break;
		case "advanced search":
			if (sourceName.equalsIgnoreCase("Documents: Selected Documents from Search")) {
				bc.passedStep(sourceName + "  selected source name reflected in export page as expected.");
			} else {

				bc.failedStep(sourceName + " selected source name reflected in export page which is not expected.");
			}
			break;
		case "doc list":
			if (sourceName.equalsIgnoreCase("Documents: Selected Documents from Document List")) {
				bc.passedStep(sourceName + " selected source name reflected in export page as expected.");
			} else {

				bc.failedStep(sourceName + " selected source name reflected in export page which is not expected.");
			}
			break;
		case "doc exp":
			if (sourceName.equalsIgnoreCase("Documents: Selected Documents from Document Explorer")) {
				bc.passedStep(sourceName + " selected source name reflected in export page as expected.");
			} else {

				bc.failedStep(sourceName + " selected source name reflected in export page which is not expected.");
			}
			break;

		case "tally":
			if (sourceName.equalsIgnoreCase("Documents: Selected Documents from Tally")) {
				bc.passedStep(sourceName + " selected source name reflected in export page as expected.");
			} else {

				bc.failedStep(sourceName + " selected source name reflected in export page which is not expected.");
			}
			break;
		}

	}

	/**
	 * This method will select the saved custom document data report from reports
	 * page.
	 * 
	 * @param report1
	 */
	public void SavedCDDRToExportPage(String report1) {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		getReportName(report1).Click();
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Jayanthi.Ganesan This method will retrieve the value from csv file
	 *         downloaded
	 * @param fileLocation
	 * @param fileName[Name of file]
	 * @return return a list of string [Values from csv file column]
	 */
	public List<String> csvfileSize(String fileLocation, String fileName) {
		FileReader file = bc.fileLocate(fileLocation, fileName);
		List<String> lines = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(file);
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line.trim().replaceAll("ï»¿\"", ""));
			}
			System.out.println(lines);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * @author sowndarya This method will navigate To CDDReportPage
	 */
	public void navigateToCDDReportPage() {
		ReportsPage report = new ReportsPage(driver);
		driver.scrollPageToTop();
		bc.waitForElement(report.navigateToReports());
		report.navigateToReports().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.verifyPageNavigation("Report/ReportsLanding");
		bc.waitForElement(report.getCustomDocumentDataReport());
		report.getCustomDocumentDataReport().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.verifyPageNavigation("Report/ExportData");
	}

	/**
	 * @author sowndarya
	 */
	public void selectExportStyle(String style) {
		bc.waitForElement(getExportStyleDD());
		getExportStyleDD().selectFromDropdown().selectByVisibleText(style);
	}

	/**
	 * @author sowndarya
	 */
	public void selectExportNewLineFormat(String newLine) {
		bc.waitForElement(getExportNewLineBtn());
		getExportNewLineBtn().Click();
		bc.waitForElement(getExportNewlineValue(newLine));
		getExportNewlineValue(newLine).waitAndClick(5);

	}

	/**
	 * @author sowndarya
	 */
	public void selectExportDateStyleFormat(String dateStyle) {
		bc.waitForElement(getExportDateStyleDD());
		getExportDateStyleDD().selectFromDropdown().selectByVisibleText(dateStyle);
	}

	/**
	 * @author sowndarya
	 */
	public void verifyScrubToggleAddDeleteAndModify() {

		bc.waitForElement(getScrubLink());
		getScrubLink().waitAndClick(10);

		// add
//	driver.scrollingToBottomofAPage();
		bc.waitForElement(getAddNewBtn_ScrubLink());
		getAddNewBtn_ScrubLink().ScrollTo();
		getAddNewBtn_ScrubLink().waitAndClick(10);

		// modify
		bc.waitForElement(getScrubSpecialCharcterField());
		getScrubSpecialCharcterField().selectFromDropdown().selectByIndex(1);

		// delete
		bc.waitForElement(getRedXIcon());
		getRedXIcon().waitAndClick(10);

		// close popup
		bc.waitForElement(getCloseBtn_ScrubPopup());
		getCloseBtn_ScrubPopup().waitAndClick(10);
	}

	/**
	 * @author sowndarya
	 */
	public String selectSpecialORReplaceCharByIndex(String type, int ddNo, int valueIndex) {
		String value = null;
		bc.waitForElement(getScrubLink());
		getScrubLink().waitAndClick(10);
		// add
		driver.scrollingToBottomofAPage();
		if (type.equalsIgnoreCase("special character")) {
			driver.waitForPageToBeReady();
			bc.waitForElement(getSpecialCharDD(ddNo));
			getSpecialCharDD(ddNo).selectFromDropdown().selectByIndex(valueIndex);
			driver.waitForPageToBeReady();
			value = getSpecialCharDD(ddNo).selectFromDropdown().getFirstSelectedOption().getText();
		} else {
			bc.waitForElement(getReplaceCharDD(ddNo));
			getReplaceCharDD(ddNo).selectFromDropdown().selectByIndex(valueIndex);
			value = getReplaceCharDD(ddNo).selectFromDropdown().getFirstSelectedOption().getText();
		}
		bc.waitForElement(getCloseBtn_ScrubPopup());
		getCloseBtn_ScrubPopup().waitAndClick(10);
		return value;
	}

	/**
	 * @author sowndarya
	 */
	public void performSharingAction(String username, String emailId) {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		bc.waitForElement(getShareIcon());
		getShareIcon().waitAndClick(10);
		bc.waitForElement(getShareUsersDD());
		getShareUsersDD().waitAndClick(10);
		if (getUserDD(username).isElementAvailable(10)) {
			getUserDD(username).waitAndClick(10);
		} else {
			bc.waitForElement(getLastUser());
			getLastUser().waitAndClick(10);
		}
		getShareUsersDD().waitAndClick(10);
		bc.waitForElement(getEmailtextarea());
		getEmailtextarea().waitAndClick(10);
		driver.waitForPageToBeReady();
		getEmailtextarea().SendKeys(emailId);

		bc.waitForElement(getShareBtn());
		getShareBtn().waitAndClick(10);
		bc.VerifySuccessMessage("Your Report has been successfully shared with others.");
		bc.waitTime(10);
	}

	/**
	 * @author sowndarya
	 */
	public String SharedIDFromNotification(int index) {
		bc.waitTime(4);
		bc.waitForElement(getSharedAlert());
		getSharedAlert().waitAndClick(5);
		bc.waitTime(4);
		driver.waitForPageToBeReady();
		bc.waitForElement(getSharedID(index));
		String text = getSharedID(index).getText().trim();
		String[] splits = text.split("\\s");
		String ID = splits[splits.length - 1];
		System.out.println(splits);
		System.out.println(ID);
		return ID;
	}

	/**
	 * @author sowndarya
	 */
	public void openSharedNotification(String ID) {
		bc.waitTime(5);
		bc.waitForElement(getSharedAlert());
		getSharedAlert().waitAndClick(5);
		driver.waitForPageToBeReady();
		bc.waitForElement(getSharedNotification(ID));
		getSharedNotification(ID).waitAndClick(5);
		driver.waitForPageToBeReady();
	}

}
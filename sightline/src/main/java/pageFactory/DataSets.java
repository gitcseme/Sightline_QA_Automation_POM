package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import automationLibrary.Driver;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;
import automationLibrary.Element;

public class DataSets {

	Driver driver;
	SessionSearch search;
	BaseClass base;

	public Element getLoadMore() {
		return driver.FindElementByXPath("//button[@id='btnLoadDataset']");
	}

	public Element getDatasetBtn() {
		return driver.FindElementByXPath("//a[@title='Datasets']");
	}

	public Element getSearchTheFile() {
		return driver.FindElementByXPath("//input[@id='searchBox']");
	}

	public Element getClkSearch() {
		return driver.FindElementByXPath("//i[@id='btnSearchBox']");
	}

	public Element getSelectAction() {
		return driver.FindElementByXPath("//button[@id='idAction']");
	}

	public Element getSelectDocList() {
		return driver.FindElementByXPath("//a[@id='idBulkTag'][text()='DocList']");

	}

	// Added by Gopinath - 22/02/2022
	public Element getExportIconButton() {
		return driver.FindElementByXPath("//a[@id='ExportDatasetExceptions']//i");

	}

	public Element getDownloadedCompletedState() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[1]//td[text()='COMPLETED']");

	}

	public Element getDownloadLink() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[1]//td//a[text()='Download File']");

	}

	// added by brundha
	public Element getDataSetTypeList() {
		return driver.FindElementById("DatasetTypeList");
	}

	// Added by Aathith
	public Element getDataSetActionBtn(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'" + DataSet + "')]/../..//button");
	}

	public Element getDataSetViewInDocList(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'" + DataSet + "')]/../..//a[text()='DocList']");
	}

	// Added by Gopinath - 28/02/2022
	public Element getDatasetPanel() {
		return driver.FindElementByXPath(
				"//div[@id='DataSetListContainer']//div[@id='tileContainer']//div[@id='cardCanvas']");

	}

	public Element getPublishedCount(int i) {
		return driver.FindElementByXPath(
				"(//input[contains(@value,'View Set' )]/../../../..//span[contains(text(),'Auto')]/..//div[@class='ingestCt col-md-4 txt-color-green']//span)["
						+ i + "]");
	}

	public Element getSelectAction(int i) {
		return driver.FindElementByXPath(
				"(//input[contains(@value,'View Set' )]/../../../..//span[contains(text(),'Auto')]/..//button[@id='idAction'])["
						+ i + "]");
	}

	public Element getSelectDocList(int i) {
		return driver.FindElementByXPath(
				"(//input[contains(@value,'View Set' )]/../../../..//span[contains(text(),'Auto')]/..//a[@id='idBulkTag'][text()='DocList'])["
						+ i + "]");

	}

	public ElementCollection getPublishedCount() {
		return driver.FindElementsByXPath(
				"//input[contains(@value,'View Set' )]/../../../..//span[contains(text(),'Auto')]/..//div[@class='ingestCt col-md-4 txt-color-green']//span");
	}

	public Element getSelectDocView() {
		return driver.FindElementByXPath("//a[@id='idBulkFolder']");
	}

	public Element getSelectDataSetMenu(String name, String menu) {
		return driver.FindElementByXPath("//*[@title='" + name + "']//..//..//ul//li//a[text()='" + menu + "']");
	}

	public Element getSelectDatasetsAndActionButton(String name) {
		return driver.FindElementByXPath("//*[@title='" + name + "']//..//..//button");
	}

	// added by Aatith
	public Element getDataSetName(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'" + DataSet + "')]");
	}

	public Element getDataSetViewInDocView(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'" + DataSet + "')]/../..//a[text()='DocView']");
	}

	// Jeevitha
	public Element getBullHornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getNotificationMsg() {
		return driver.FindElementByXPath("(//ul[@class='notification-body']//a)[1]");
	}

	// Added by Arunkumar
	public Element loadMoreOption() {
		return driver.FindElementByXPath("//button[@id='btnLoadDataset']");
	}

	// added by sowndariya

	public Element allSourcesDataset() {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//a//strong[contains(text(),'Automation_All')]");
	}

	public Element allSourcesDatasetDropdown() {
		return driver.FindElementByXPath(
				"//div[@id='cardCanvas']//a//strong[contains(text(),'Automation_All')]//..//..//..//button[@id='idAction']");
	}

	public Element allSourcesDataset_viewInDoclist() {
		return driver.FindElementByXPath(
				"//div[@id='cardCanvas']//a//strong[contains(text(),'Automation_All')]//..//..//..//button[@id='idAction']//..//li//a[contains(text(),'DocList')]");
	}

	// Added by Raghuram
	public Element NavigateToDataSets() {
		return driver.FindElementByXPath("//a[@name='DataSets']//i");
	}

	public Element NavigateToDataSets(String componentName) {
		return driver.FindElementByXPath("//a[@name='" + componentName + "']//i");
	}

	public DataSets(Driver driver) {

		this.driver = driver;
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
	}

	public void fillingDataSetPage() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDatasetBtn().Enabled();
			}
		}), Input.wait30);
		getDatasetBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSearchTheFile().Enabled();
			}
		}), Input.wait30);
		getSearchTheFile().Click();
		getSearchTheFile().SendKeys("42770");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getClkSearch().Enabled();
			}
		}), Input.wait30);
		getClkSearch().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAction().Enabled();
			}
		}), Input.wait30);
		getSelectAction().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDocList().Enabled();
			}
		}), Input.wait120);
		getSelectDocList().waitAndClick(10);
		base.stepInfo("Dataset Page is Filled");

	}

	/**
	 * @author Gopinath
	 * @description : Method to navigate data sets page.
	 */
	public void navigateToDataSetsPage() {
		try {
			driver.getWebDriver().get(Input.url + "ICE/Datasets");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating data sets page." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to verify Field Displayed Of Downlodeded Excel File.
	 * @param fileLocation : fileLocation is downloaded file location.
	 * @param sheetName    : sheetName is name of work sheet that need to verify
	 *                     fields
	 * @param fieldName    : fieldName is name of field need to verify in excel file
	 *                     weather field is present or not.
	 */
	public void verifyFieldDisplayedOfDownlodedExcelFile(String fileLocation, String sheetName, String fieldName) {
		String lastModifiedName = null;
		try {
			String value = null;
			File dir = new File(fileLocation);
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				System.out.println("nulll");
				lastModifiedName = base.GetFileName();
			} else {
				File lastModifiedFile = files[0];
				for (int i = 1; i < files.length; i++) {
					if (lastModifiedFile.lastModified() < files[i].lastModified()) {
						lastModifiedFile = files[i];
					}
				}
				System.out.println("====" + lastModifiedFile.getName());
				lastModifiedName = lastModifiedFile.getName();
			}
			String path = fileLocation + "\\\\" + lastModifiedName;
			FileInputStream fis = new FileInputStream(path);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetName);
			int lastRow = sheet.getLastRowNum();
			for (int i = 0; i <= lastRow; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					int lastCell = row.getLastCellNum();
					for (int j = 0; j < lastCell; j++) {
						Cell cell = row.getCell(j);
						try {
							cell.getCellType();
							value = cell.getStringCellValue();
							System.out.println(value);
							if (value == null) {
							} else {

								if (fieldName.trim().equalsIgnoreCase(value.trim())) {
									base.passedStep(
											fieldName + " : is displayed in downloaded file - " + lastModifiedName);
									break;
								} else if ((i == lastRow) && (j == lastCell - 1)) {
									base.failedStep(
											fieldName + " : is not displayed in downloaded file - " + lastModifiedName);
								}
							}
						} catch (Exception e) {
						}
					}
					if (value != null) {
						if (fieldName.trim().equalsIgnoreCase(value.trim())) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying Field Displayed Of Downlodeded Excel File." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to perform export data sets.
	 */
	public void exportDataSets() {
		try {
			driver.getWebDriver().get(Input.url + "ICE/Datasets");
			driver.waitForPageToBeReady();
			getExportIconButton().isElementAvailable(15);
			getExportIconButton().Click();
			base.getSuccessMsg().isElementAvailable(15);
			if (base.getSuccessMsg().isDisplayed()) {
				base.passedStep("Success message is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while exporting datasets" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to download exported file.
	 */
	public void downloadExportedFile() {
		try {
			driver.getWebDriver().get(Input.url + "Background/BackgroundTask");
			driver.waitForPageToBeReady();
			for (int i = 0; i < 15; i++) {
				driver.waitForPageToBeReady();
				if (getDownloadedCompletedState().isDisplayed()) {
					driver.waitForPageToBeReady();
					getDownloadLink().isElementAvailable(10);
					getDownloadLink().Click();
					break;
				} else {
					driver.getWebDriver().navigate().refresh();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while download exported file." + e.getMessage());
		}
	}

	/**
	 * @author Brundha
	 * @description : Method to select dataset.
	 */
	public void SelectingUploadedDataSet() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDataSetTypeList());
		getDataSetTypeList().selectFromDropdown().selectByVisibleText("Only Uploaded Sets");
		driver.waitForPageToBeReady();
		for (int i = 1; i < getPublishedCount().size(); i++) {
			String count = getPublishedCount(i).getText();
			int count1 = Integer.parseInt(count);
			if (count1 == 0) {
				continue;
			} else if (count1 != 0)
				base.waitForElement(getSelectAction(i));
			getSelectAction(i).waitAndClick(10);
			base.waitForElement(getSelectDocList(i));
			getSelectDocList(i).waitAndClick(10);
			break;
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param DataSet
	 */
	public void selectDataSetWithName(String DataSet) {
		navigateToDataSetsPage();
		driver.waitForPageToBeReady();
		int i = 1;
		try {
			while (!getDataSetActionBtn(DataSet).isElementAvailable(10)) {
				driver.scrollingToBottomofAPage();
				driver.waitForPageToBeReady();
				if (loadMoreOption().isElementAvailable(10)) {
					loadMoreOption().waitAndClick(5);
				}
				if (i == 10) {
					System.out.println("DataSet not in the project");
					base.failedStep("DataSet is not in project");
					break;
				}
				i++;
			}
			getDataSetActionBtn(DataSet).ScrollTo();
			driver.waitForPageToBeReady();
			getDataSetActionBtn(DataSet).waitAndClick(10);
			base.waitForElement(getDataSetViewInDocList(DataSet));
			getDataSetViewInDocList(DataSet).waitAndClick(10);
			base.stepInfo("DataSet is selected and viewed in DocList.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("failed" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to verify data sets page is loaded.
	 */
	public void verifyDatasetsPageIsLoaded() {
		try {
			driver.waitForPageToBeReady();
			getDatasetPanel().isElementAvailable(15);
			if (getDatasetPanel().isDisplayed()) {
				base.passedStep("Data sets page is loaded successfully");
			} else {
				base.failedStep("Loading Data sets page is failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verify data sets page is loaded." + e.getMessage());

		}
	}

	/*
	 * @author Sakthivel
	 * 
	 * @description : Method to select a uploaded dataset in dropDown.
	 */
	public void SelectingUploadedDataSets() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDataSetTypeList());
		getDataSetTypeList().selectFromDropdown().selectByVisibleText("Only Uploaded Sets");
		base.stepInfo("uploaded dataset is selected on a dropdown successfully");
	}

	/**
	 * @author Sakthivel
	 * @description : Method to select a datasets in dropDown.
	 */
	public void SelectingDataSets(String Set) {
		driver.waitForPageToBeReady();
		base.waitForElement(getDataSetTypeList());
		getDataSetTypeList().selectFromDropdown().selectByVisibleText(Set);
		base.stepInfo(Set + " is selected on a dropdown successfully");

	}

	/**
	 * @author Sakthivel
	 * @description : Method to selected a search dataset and go to doclist
	 */
	public void SearchDataSetsInDocList(String dataset) {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSearchTheFile().Enabled();
			}
		}), Input.wait30);
		getSearchTheFile().waitAndClick(5);
		getSearchTheFile().SendKeys(dataset);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getClkSearch().Enabled();
			}
		}), Input.wait30);
		getClkSearch().waitAndClick(3);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectAction());
		getSelectAction().waitAndClick(10);
		base.waitForElement(getSelectDocList());
		getSelectDocList().waitAndClick(10);
	}

	/**
	 * @author Sakthivel
	 * @description : Method to selected a search dataset and go to docview.
	 */
	public void SearchDataSetsInDocView(String dataset) {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSearchTheFile().Enabled();
			}
		}), Input.wait30);
		getSearchTheFile().waitAndClick(5);
		getSearchTheFile().SendKeys(dataset);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getClkSearch().Enabled();
			}
		}), Input.wait30);
		getClkSearch().waitAndClick(3);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectAction());
		getSelectAction().waitAndClick(10);
		base.waitForElement(getSelectDocView());
		getSelectDocView().waitAndClick(10);
	}

	/**
	 * @author Sakthivel
	 * @description : Method to selected a automationallSourcesData and go to
	 *              docview.
	 */
	public void getAutomationAllSourcesData(String AllSourceData, String name) {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		base.waitTime(3);
		driver.scrollingToBottomofAPage();
		base.waitTime(2);
		driver.scrollingToElementofAPage(getSelectDatasetsAndActionButton(AllSourceData));
		getSelectDatasetsAndActionButton(AllSourceData).waitAndClick(2);
		driver.waitForPageToBeReady();
		getSelectDataSetMenu(AllSourceData, name).waitAndClick(5);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param DataSet
	 * @return
	 * @Description check weather data is there or not
	 */
	public String isDataSetisAvailable(String DataSet) {
		navigateToDataSetsPage();
		String datasetName = null;
		driver.waitForPageToBeReady();
		for (int i = 0; i < 10; i++)
			if (getDataSetName(DataSet).isElementAvailable(2)) {
				base.passedStep(DataSet + " is available in this project");
				datasetName = getDataSetName(DataSet).GetAttribute("title");
				break;
			} else if (loadMoreOption().isElementAvailable(10)) {
				loadMoreOption().waitAndClick(5);
			} else {
				driver.scrollingToBottomofAPage();
			}
		return datasetName;
	}

	/**
	 * @author Sakthivel
	 * @param DataSet
	 */
	public void selectDataSetWithNameInDocView(String DataSet) {
		driver.waitForPageToBeReady();
		int i = 1;
		try {
			while (!getDataSetActionBtn(DataSet).isElementAvailable(1)) {
				driver.scrollingToBottomofAPage();
				driver.waitForPageToBeReady();
				if (loadMoreOption().isElementAvailable(10)) {
					loadMoreOption().waitAndClick(5);
				}
				if (i == 10) {
					System.out.println("DataSet not in the project");
					base.failedStep("DataSet is not in project");
					break;
				}
				i++;
			}
			getDataSetActionBtn(DataSet).ScrollTo();
			driver.waitForPageToBeReady();
			getDataSetActionBtn(DataSet).waitAndClick(10);
			base.waitForElement(getDataSetViewInDocView(DataSet));
			getDataSetViewInDocView(DataSet).waitAndClick(10);
			base.stepInfo("DataSet is selected and viewed in docView.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("failed" + e.getMessage());
		}
	}

	/**
	 * @Author Jeevitha
	 * @param bgCountBefore
	 * @return
	 */
	public String downloadExportFile(int bgCountBefore) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == bgCountBefore + 1;
			}
		}), Input.wait60);
		final int bgCountAfter = base.initialBgCount();

		String filePath = null;
		if (bgCountAfter > bgCountBefore) {
			getBullHornIcon().waitAndClick(10);

			String downloadMsg = getNotificationMsg().getText();
			String expected = "Your dataset summary report is ready. Please click here to download.";
			String failMsg = "Download Notification is not As Expected";
			base.textCompareEquals(downloadMsg, expected, downloadMsg, failMsg);

			base.waitForElement(getNotificationMsg());
			getNotificationMsg().waitAndClick(10);

			File ab = new File(Input.fileDownloadLocation);
			String testPath = ab.toString() + "\\";

			// wait until file download is complete
			base.waitUntilFileDownload();

			// base.csvReader();
			ReportsPage report = new ReportsPage(driver);
			File a = report.getLatestFilefromDir(testPath);
			System.out.println(a.getName());
			base.stepInfo(a.getName());
			String fileName = a.getName();
			filePath = testPath + fileName;
			System.out.println("File path : " + filePath);
			base.stepInfo("Downloaded File : " + filePath);

		}
		return filePath;
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: Get notification msg whether the proj is cloned successfully.
	 * @param bgCountBefore
	 */
	public int getNotificationMessage(int bgCountBefore, String projectName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == bgCountBefore + 1;
			}
		}), Input.wait120);
		final int bgCountAfter = base.initialBgCount();

		if (bgCountAfter > bgCountBefore) {
			getBullHornIcon().waitAndClick(10);

			String downloadMsg = getNotificationMsg().getText();
			String expected = "Project " + projectName + " creation successful.";
			String failMsg = "Download Notification is not As Expected";
			base.textCompareEquals(downloadMsg, expected, downloadMsg, failMsg);
		} else {
			driver.Navigate().refresh();
		}
		return bgCountAfter;
	}

	/**
	 * @author Raghuram.A
	 * @Date: 07/12/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @param componentName
	 * @param expectedURL
	 * @Description : navigateToDataSets
	 */
	public void navigateToDataSets(String componentName, String expectedURL) {
		driver.waitForPageToBeReady();
		base.waitForElement(NavigateToDataSets());
		NavigateToDataSets().waitAndClick(10);
		base.waitForElement(NavigateToDataSets(componentName));
		NavigateToDataSets(componentName).waitAndClick(10);

		// Verify Page Navigation
		base.verifyPageNavigation(expectedURL);
	}

	/**
	 * @author Raghuram.A
	 * @Date: 07/12/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : CollectionAndSourcecomponentAbsenceCheck
	 */
	public void CollectionAndSourcecomponentAbsenceCheck() {
		try {
			driver.waitForPageToBeReady();
			NavigateToDataSets().waitAndClick(20);
		} catch (Exception e) {
			base.failedStep("Datasets not available");
		}
		base.printResutInReport(base.ValidateElement_PresenceReturn(NavigateToDataSets("Collections")),
				"'Collections' options not available when User do not have Collection’s access control permission.",
				"'Collections' options are available when User do not have Collection’s access control permission.",
				"Fail");

		base.printResutInReport(base.ValidateElement_PresenceReturn(NavigateToDataSets("Source")),
				"'Source Locations' options are not available when User do not have Collection’s access control permission.",
				"'Source Locations' options are available when User do not have Collection’s access control permission.",
				"Fail");
	}

}

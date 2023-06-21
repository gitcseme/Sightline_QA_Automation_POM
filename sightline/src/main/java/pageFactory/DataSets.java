package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import automationLibrary.Driver;
import automationLibrary.ElementCollection;
import automationLibrary.GenFunc;
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
	public Element getUploadFilesBtn() {return driver.FindElementByCssSelector("#initiate");}

	// Added by Gopinath - 22/02/2022
	public Element getExportIconButton() {
		return driver.FindElementByXPath("//a[@id='ExportDatasetExceptions']//i");

	}
	public Element getDropZoneLink() {return driver.FindElementByCssSelector("#mydropzone");}
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

	public Element getInitatePopupYesBtn() {return driver.FindElementByCssSelector("#bot1-Msg1");}
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
	public Element getLastStatus() {
		return driver.FindElementByXPath("//strong[contains(text(),'Last Status')]");
	}

	public Element getDatasetTile(int index) {
		return driver.FindElementByXPath("(//ul[@id='dataset_tilesContainer']//li)[" + index + "]");
	}

	public ElementCollection getTotalDataset() {
		return driver
				.FindElementsByXPath("//ul[@id='dataset_tilesContainer']//li//span[normalize-space()='Upload Set']");
	}

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

	public Element getTotalDatasetCount() {
		return driver.FindElementByXPath("//text[@id='totaldatasetCount']");
	}

	// added by sowndariya

	public Element getDataset(String dataset) {
		return driver.FindElementByXPath("//a[contains(@title,'" + dataset
				+ "')]//..//..//input[@value='View Set in...']//..//button[@id='idAction']");
	}

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

	public Element getDataSetNameViaViewDS() {
		return driver.FindElementByXPath("//a[@class='tileTooltipText']//strong");
	}

	public Element getdatasetleftmenuBtn() {
		return driver.FindElementByName("DataSets");
	}

	public Element getdatasetleftsubmenuBtn() {
		return driver.FindElementByName("Datasets1");
	}

	public Element getCreateNewUploadSetLink() {
		return driver.FindElementByCssSelector("#createSet");
	}

	public Element getDatasetNameTxtBox() {
		return driver.FindElementById("txtDatasetName");
	}

	public Element getCustodianNameTxtBox() {
		return driver.FindElementById("txtCustodianName");
	}

	public Element getDescriptionTxtBox() {
		return driver.FindElementById("txtDatasetDescription");
	}

	public Element getCreateBtn() {
		return driver.FindElementById("CreateUploadSet");
	}

	public Element getNOBtn() {
		return driver.FindElementById("bot2-Msg1");
	}

	public Element getdraganddroppage() {
		return driver.FindElementById("mydropzone");
	}

	public Element getdataSetErrorMsg() {
		return driver.FindElementByXPath("//span[@id='txtDatasetName-error']");
	}

	public Element getCreateUploadDatasetPopUpClose() {
		return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']");
	}

	public Element geteditDatasetDetails() {
		return driver.FindElementByXPath("(//a[@id='editUploadSet'])[1]");
	}

	public Element getSaveDatasetDetails() {
		return driver.FindElementByXPath("//button[@id='EditUploadSet']");
	}

	public Element geteditFirstDataSetElement() {
		return driver.FindElementByXPath("(//ul[@id='dataset_tilesContainer']/li/span[2]/a/strong)[1]");
	}

	public Element getNoOpenInCurrentTab() {
		return driver.FindElementByXPath("//button[@id='bot2-Msg1']");
	}

	public Element geteditProjectPwds() {
		return driver.FindElementByXPath("//a[@id='editProjectPwd']");
	}

	public Element getProjectPwdsText() {
		return driver.FindElementByXPath("//textArea[@id='txtProjectPwds']");
	}

	public Element getSaveProjectPwdsText() {
		return driver.FindElementByXPath("//button[@id='SaveProjectPwd']");
	}

	public ElementCollection getIngestionCount() {
		return driver.FindElementsByXPath(
				"//a//strong[contains(text(),'RPMXCON40140')]/../../..//div[@class='ingestCt col-md-4 txt-color-green']//span");
	}

	public Element getIngestionCount(int i) {
		return driver.FindElementByXPath(
				"(//a//strong[contains(text(),'RPMXCON40140')]/../../..//div[@class='ingestCt col-md-4 txt-color-green']//span)["
						+ i + "]");

	}

	public Element getAction(int i) {
		return driver.FindElementByXPath(
				"(//input[contains(@value,'View Set' )]/../../../..//Strong[contains(text(),'RPMXCON40140')]/../../..//button[@id='idAction'])["
						+ i + "]");

	}

	public Element getDocList(int i) {
		return driver.FindElementByXPath(
				"(//input[contains(@value,'View Set' )]/../../../..//Strong[contains(text(),'RPMXCON40140')]/../../..//a[@id='idBulkTag'][text()='DocList'])["
						+ i + "]");

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
			while (!getDataset(DataSet).isElementAvailable(10)) {
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
			getDataset(DataSet).ScrollTo();
			driver.waitForPageToBeReady();
			try {
				getDataset(DataSet).waitAndClick(10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			base.waitForElement(getDataSetViewInDocList(DataSet));
			getDataSetViewInDocList(DataSet).waitAndClick(10);

			/*try {

			if(getInitatePopupYesBtn().isDisplayed()) {
			try {
				getInitatePopupYesBtn().waitAndClick(10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			base.stepInfo("DataSet is selected and viewed in DocList.");

			}
		catch (Exception e) {
			e.printStackTrace();
			base.failedStep("failed" + e.getMessage());
		}
			base.stepInfo("DataSet is selected and viewed in DocList.");}
	


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

	/**
	 * @Author Jeevitha
	 * @Description : verify search box value after navigating from collection page
	 * @param expectedSearchValue
	 * @param expectedTileView
	 */
	public void verifysearchBoxValue(String expectedSearchValue, String expectedTileView) {
		driver.waitForPageToBeReady();
		String actualSearchValue = getSearchTheFile().GetAttribute("value");

		String passMsg = "After Navigating to Dataset page from Collection Page Filter is Applied & search filter box value is : "
				+ actualSearchValue;
		String failMsg = "Filter is Not Applied";
		base.textCompareEquals(actualSearchValue, expectedSearchValue, passMsg, failMsg);

		if (!expectedTileView.equals("")) {
			String actualType = getTileViewType();
			base.textCompareEquals(actualType, expectedTileView,
					"the same tile for the datasets in the collection is Displayed", "The Tile Is different");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @Author Jeevitha
	 * @Description : returns tile view type
	 * @modifiedOn : 9/12/22 - handling abnormal load time
	 * @return
	 */
	public String getTileViewType() {
		String viewType = "";
		driver.waitForPageToBeReady();
		base.waitForElement(getTotalDatasetCount());
		String datasetCount = getTotalDatasetCount().getText();
		int totalDoc = Integer.parseInt(datasetCount);
		base.stepInfo("Total Doc Count : " + totalDoc);

		if (totalDoc > 1) {
			int expCount = totalDoc - totalDoc + 1;// to avoid abnormal wait handles
			driver.waitForPageToBeReady();
			base.waitForElement(getDatasetTile(expCount));
			viewType = getDatasetTile(expCount).GetCssValue("display");
			base.stepInfo("Display Type : " + viewType);
		} else if (totalDoc == 1) {
			base.waitForElement(getDatasetTile(totalDoc));
			viewType = getDatasetTile(totalDoc).GetCssValue("display");
		}
		return viewType;
	}

	/**
	 * @author: Arun Created Date: 06/09/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check dataset menu availability
	 */
	public void verifyMenuAndDatasetsAvailability() {
		navigateToDataSetsPage();
		driver.waitForPageToBeReady();
		base.waitForElement(getTotalDatasetCount());
		int count = Integer.parseInt(getTotalDatasetCount().getText());
		if (NavigateToDataSets().isElementAvailable(10) && count > 0) {
			base.passedStep("User have access to dataset menu and datasets available");
		} else {
			base.failedStep("user dont have access to dataset menu and datasets not available");
		}

	}

	/**
	 * @Author Hema MJ
	 * @Description : create & set dataset details
	 * @param dname
	 * @param ddcustodianit
	 * @param ddisc
	 */
	public void setdatasetdetails(String dname, String dcustodian, String ddisc) {
		System.out.println("********Started to create new uploaded set.********");
		getCreateNewUploadSetLink().WaitUntilPresent().Click();
		base.waitForElement(getDatasetNameTxtBox());
		getDatasetNameTxtBox().SendKeys(dname);
		getCustodianNameTxtBox().SendKeys(dcustodian);
		getDescriptionTxtBox().SendKeys(ddisc);
		getCreateBtn().WaitUntilPresent().Click();

		// checking for special characters
		Pattern p = Pattern.compile("[<'>&]", Pattern.CASE_INSENSITIVE);
		// Creating matcher for above pattern on our string
		Matcher m1 = p.matcher(dname);
		Matcher m2 = p.matcher(dcustodian);
		Matcher m3 = p.matcher(ddisc);
		// Now finding the matches for which let us set a boolean flag and imposing
		// find() method
		boolean dnameflag = m1.find();
		boolean dcustodianflag = m2.find();
		boolean ddiscflag = m3.find();
		if (dnameflag || ddiscflag || dcustodianflag) {

			if (dnameflag) {
				base.waitForElement(getdataSetErrorMsg());
				String ErrorMsg = getdataSetErrorMsg().getText();
				if (ErrorMsg.equals("")) {
					base.failedStep("Expected Error Message is Not Displayed");
				} else {
					System.out.println("Error : " + ErrorMsg);
					base.passedStep("Dispalyed Error Msg : " + ErrorMsg);
				}
			}
			if (ddiscflag || dcustodianflag) {
				String ErrorMsg = "Failed to create a dataset due to invalid details. Custodian Name and Dataset description should not include characters < or > in them.";
				base.VerifyErrorMessage(ErrorMsg);
				base.passedStep("dataset failed to create");
			}
			base.waitForElement(getCreateUploadDatasetPopUpClose());
			getCreateUploadDatasetPopUpClose().waitAndClick(5);

		} else {
			try {
				base.waitForElement(getNoOpenInCurrentTab());
				getNoOpenInCurrentTab().waitAndClick(10);
			} catch (Exception e) {
				base.failedStep("dataset failed to create");
			}
			base.passedStep("Creation of new uploaded set is completed.");
			System.out.println("********Creation of new uploaded set is completed.********");
		}
	}

	public void updateDatasetDetails(String dname, String dcustodian, String ddisc) {
		base.waitForElement(geteditDatasetDetails());

		geteditDatasetDetails().Click();

		base.waitForElement(getDatasetNameTxtBox());

		getDatasetNameTxtBox().Clear();
		getDatasetNameTxtBox().SendKeys(dname);
		getCustodianNameTxtBox().Clear();
		getCustodianNameTxtBox().SendKeys(dcustodian);
		getDescriptionTxtBox().Clear();
		getDescriptionTxtBox().SendKeys(ddisc);
		getSaveDatasetDetails().WaitUntilPresent().Click();

		// checking for special characters
		Pattern p = Pattern.compile("[<'>&]", Pattern.CASE_INSENSITIVE);
		// Creating matcher for above pattern on our string
		Matcher m1 = p.matcher(dname);
		Matcher m2 = p.matcher(dcustodian);
		Matcher m3 = p.matcher(ddisc);
		// Now finding the matches for which let us set a boolean flag and imposing
		// find() method
		boolean dnameflag = m1.find();
		boolean dcustodianflag = m2.find();
		boolean ddiscflag = m3.find();
		if (dnameflag || ddiscflag || dcustodianflag) {

			if (dnameflag) {
				base.waitForElement(getdataSetErrorMsg());
				String ErrorMsg = getdataSetErrorMsg().getText();
				if (ErrorMsg.equals("")) {
					base.failedStep("Expected Error Message is Not Displayed");
				} else {
					System.out.println("Error : " + ErrorMsg);
					base.passedStep("Dispalyed Error Msg : " + ErrorMsg);
				}
			}
			if (ddiscflag || dcustodianflag) {
				String ErrorMsg = "Failed to create a dataset due to invalid details. Custodian Name and Dataset description should not include characters < or > in them.";
				base.VerifyErrorMessage(ErrorMsg);
				base.CloseSuccessMsgpopup();
				base.passedStep("dataset failed to create");
			}
			base.waitForElement(getCreateUploadDatasetPopUpClose());
			getCreateUploadDatasetPopUpClose().waitAndClick(5);

		} else {
			base.VerifySuccessMessage("Upload dataset updated successfully");
		}
		base.passedStep("Updating of existing uploaded set is completed.");
		System.out.println("********Updating of existing uploaded set is completed.********");

	}

	public void updateProjectPwds(String projectPwd) throws InterruptedException {
		base.waitForElement(geteditProjectPwds());
		geteditProjectPwds().Click();
		base.waitForElement(getProjectPwdsText());
		getProjectPwdsText().Clear();
		getProjectPwdsText().SendKeys(projectPwd);
		base.waitForElement(getSaveProjectPwdsText());
		getSaveProjectPwdsText().Click();
		Thread.sleep(2000);
		base.VerifySuccessMessage("Passwords saved successfully.");

	}

	/**
	 * @author Brundha
	 * @description : Method to select dataset.
	 */
	public void SelectingUploadedDataSetViewInDoclist(String Dataset) {
		driver.waitForPageToBeReady();
		base.waitForElement(getDataSetTypeList());
		getDataSetTypeList().selectFromDropdown().selectByVisibleText("Only Uploaded Sets");
		driver.waitForPageToBeReady();
		int j = 1;
		while (!getDataset(Dataset).isElementAvailable(10)) {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			if (loadMoreOption().isElementAvailable(10)) {
				loadMoreOption().waitAndClick(5);
			}
			if (j == 10) {
				System.out.println("DataSet not in the project");
				base.failedStep("DataSet is not in project");
				break;
			}
			j++;
		}
		for (int i = 1; i <= getIngestionCount().size(); i++) {
			String count = getIngestionCount(i).getText();
			int count1 = Integer.parseInt(count);
			if (count1 == 0) {
				continue;
			} else if (count1 != 0)
				base.waitForElement(getAction(i));
			getAction(i).waitAndClick(10);
			base.waitForElement(getDocList(i));
			getDocList(i).waitAndClick(10);
			break;
		}
	}

	/**
	 * @Author Jeevitha
	 * @param expectedStatus
	 * @param count
	 * @param collectionName
	 * @return
	 */
	public String VerifyLastStatusOfCollection(String expectedStatus, int count, String collectionName) {
		base.waitForElement(getLastStatus());
		String actualStatus = getLastStatus().getText();
		String[] status = actualStatus.split("\n");
		if (actualStatus.equalsIgnoreCase(expectedStatus)) {
			base.textCompareEquals(status[1], expectedStatus, "Last Status Displayed is : " + status[1],
					"Displayed Last Status is not as Expected");
		} else {
			for (int i = 0; i < count; i++) {
				base.waitTime(25);
				base.waitForElement(getClkSearch());
				getClkSearch().waitAndClick(5);
				driver.waitForPageToBeReady();
				base.waitForElement(getLastStatus());
				actualStatus = getLastStatus().getText();
				status = actualStatus.split("\n");
				if(status[1].equals(expectedStatus)) {
					break;
				}
			}
			base.textCompareEquals(status[1], expectedStatus, "Last Status Displayed is : " + status[1],
					"Displayed Last Status is not as Expected");
		}
		return actualStatus;
	}
	
	
	public int uploadFilesByFolder(String folderPath)
    {
    	System.out.println("*********Uplaoding Files started***********");
    	
    	int FileUploadCountExist = GenFunc.StringToInt(driver.FindElementByCssSelector("#fileCount").GetAttribute("data-filecount"));
    	String exeFile = System.getProperty("user.dir")+"\\src\\main\\java\\aIs\\";
		String fileListString = "";
		System.out.println(exeFile);
		base.waitForElement(getDropZoneLink());
    	getDropZoneLink().waitAndClick(10);
    	exeFile = System.getProperty("user.dir")+"\\src\\main\\java\\aIs\\";
    	//String testDataFolder = System.getProperty("user.dir")+"\\sightline\\ICETestData";
    	File directory = new File(folderPath);
		File[] flist =  directory.listFiles();
		System.out.println("Printing files");
		for (File file:flist)
		{
			if(file.isFile())
			{
				fileListString = fileListString+"\""+file.getName()+"\""+" ";
				//fileListString = fileListString+"\""+file.getName().replaceAll("\\s+","")+"\""+" ";
			}
		}
		try {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Runtime.getRuntime().exec(exeFile+"DragAndDrop_x64.exe"+" "+folderPath+"\\ "+fileListString);


		} catch (Exception e) {
			e.printStackTrace();	
		}

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getUploadFilesBtn().getText().contains("Uploading...") ;}}),Input.wait90);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			GenFunc.StringToInt(driver.FindElementByCssSelector("#fileCount").GetAttribute("data-filecount"))== FileUploadCountExist+flist.length ;}}),500000);
    	
    	System.out.println("*********Uplaoding Files Completed***********");
    	return flist.length;
    }
}

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
	
	//Added by Gopinath - 22/02/2022
	public Element getExportIconButton() {
		return driver.FindElementByXPath("//a[@id='ExportDatasetExceptions']//i");
	
	}
	public Element getDownloadedCompletedState() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[1]//td[text()='COMPLETED']");
	
	}
	public Element getDownloadLink() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[1]//td//a[text()='Download File']");
	
	}
	//added by brundha
	public Element getDataSetTypeList() {
		return driver.FindElementById("DatasetTypeList");
	}
	
	//Added by Aathith
	public Element getDataSetActionBtn(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'"+DataSet+"')]/../..//button");
	}
	public Element getDataSetViewInDocList(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'"+DataSet+"')]/../..//a[text()='DocList']");
	}
	//Added by Gopinath - 28/02/2022
	public Element getDatasetPanel() {
		return driver.FindElementByXPath("//div[@id='DataSetListContainer']//div[@id='tileContainer']//div[@id='cardCanvas']");
	
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
		   }}), Input.wait30);getDatasetBtn().Click();	
		
		
	driver.WaitUntil((new Callable<Boolean>() {
		    public Boolean call() {
			return getSearchTheFile().Enabled();
			}}), Input.wait30);getSearchTheFile().Click();	
			getSearchTheFile().SendKeys("42770");
	
	driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
			return getClkSearch().Enabled();
			}}), Input.wait30);getClkSearch().Click();	
				
   driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
			return getSelectAction().Enabled();
			}}), Input.wait30);getSelectAction().Click();	
					
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
				return getSelectDocList().Enabled();
				}}), Input.wait120);getSelectDocList().waitAndClick(10);	
				base.stepInfo("Dataset Page is Filled");					
				
	}	
	
	
	
	/**
	*@author Gopinath
	*@description : Method to navigate data sets page.
	*/
	public void navigateToDataSetsPage() {
		try {
			driver.getWebDriver().get(Input.url+ "ICE/Datasets");
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating data sets page."+e.getMessage());
			
		}
	}
	
	
	/**
	*@author Gopinath
	*@description : Method to verify Field Displayed Of Downlodeded Excel File.
	*@param fileLocation : fileLocation is downloaded file location.
	*@param sheetName : sheetName is name of work sheet that need to verify fields
	*@param fieldName : fieldName is name of field need to verify in excel file weather field is present or not.
	*/
	public void verifyFieldDisplayedOfDownlodedExcelFile(String fileLocation,String sheetName,String fieldName) {
		String lastModifiedName = null;
		try {
			String value = null;
			 File dir = new File(fileLocation);
			    File[] files = dir.listFiles();
			    if (files == null || files.length == 0) {
			        System.out.println("nulll");
			        lastModifiedName = base.GetFileName();
			    }else {
				    File lastModifiedFile = files[0];
				    for (int i = 1; i < files.length; i++) {
				       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				           lastModifiedFile = files[i];
				       }
				    }
				    System.out.println("===="+lastModifiedFile.getName());
				    lastModifiedName = lastModifiedFile.getName();
			    }
			    String path = fileLocation+"\\\\"+lastModifiedName;
			    FileInputStream fis = new FileInputStream(path);
			    Workbook workbook = new XSSFWorkbook(fis);
			    Sheet sheet = workbook.getSheet(sheetName);
			    int lastRow = sheet.getLastRowNum();
			    for(int i=0; i<=lastRow; i++){
			    	Row row = sheet.getRow(i);
			    	if(row!=null) {
				    	int lastCell = row.getLastCellNum();
				    	for(int j=0; j<lastCell; j++){
					    	Cell cell = row.getCell(j);
					    	try {
					    		cell.getCellType();
					    		value = cell.getStringCellValue();
					    		System.out.println(value);
						    	if(value==null) {
						    	}else {
						    	
							    	if(fieldName.trim().equalsIgnoreCase(value.trim())) {
							    		base.passedStep(fieldName+" : is displayed in downloaded file - "+lastModifiedName);
							    		break;
							    	}else if((i==lastRow)&&(j==lastCell-1)) {
							    		base.failedStep(fieldName+" : is not displayed in downloaded file - "+lastModifiedName);
							    	}
						    	}
					    	}catch(Exception e) {
					    	}
				    	}
				    	if(value!=null) {
					    	if(fieldName.trim().equalsIgnoreCase(value.trim())) {
					    		break;
					    	}
				    	}
				    }
			    }
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying Field Displayed Of Downlodeded Excel File."+e.getMessage());
			
		}
	}
	/**
	*@author Gopinath
	*@description : Method to perform export data sets.
	*/
	public void exportDataSets() {
		try {
			driver.getWebDriver().get(Input.url+ "ICE/Datasets");
			driver.waitForPageToBeReady();
			getExportIconButton().isElementAvailable(15);
			getExportIconButton().Click();
			base.getSuccessMsg().isElementAvailable(15);
			if(base.getSuccessMsg().isDisplayed()) {
				base.passedStep("Success message is displayed");
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while exporting datasets"+e.getMessage());
		}
	}  
	
	/**
	*@author Gopinath
	*@description : Method to download exported file.
	*/
	public void downloadExportedFile() {
		try {
			driver.getWebDriver().get(Input.url+ "Background/BackgroundTask");
			driver.waitForPageToBeReady();
			for(int i =0;i<15;i++) {
				driver.waitForPageToBeReady();
				if(getDownloadedCompletedState().isDisplayed()) {
					driver.waitForPageToBeReady();
					getDownloadLink().isElementAvailable(10);
					getDownloadLink().Click();
					break;
				}else {
					driver.getWebDriver().navigate().refresh();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while download exported file."+e.getMessage());
		}
	}
	
	/**
	*@author Brundha
	*@description : Method to select dataset.
	*/
	public void SelectingUploadedDataSet() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getDataSetTypeList());
		getDataSetTypeList().selectFromDropdown().selectByVisibleText("Only Uploaded Sets");
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectAction());
		getSelectAction().waitAndClick(10);
		base.waitForElement(getSelectDocList());
		getSelectDocList().waitAndClick(10);
		
	}
	/**
	 * @author Aathith.Senthilkumar
	 * @param DataSet
	 */
	public void selectDataSetWithName(String DataSet) {
		driver.waitForPageToBeReady();
		int i = 1;
		try {
		while(!getDataSetActionBtn(DataSet).isElementAvailable(1)){
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			if(i==10) {
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
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("failed"+e.getMessage());
		}
	}
	
	/**
	*@author Gopinath
	*@description : Method to verify data sets page is loaded.
	*/
	public void verifyDatasetsPageIsLoaded() {
		try {
			driver.waitForPageToBeReady();
			getDatasetPanel().isElementAvailable(15);
			if(getDatasetPanel().isDisplayed()) {
				base.passedStep("Data sets page is loaded successfully");
			}else {
				base.failedStep("Loading Data sets page is failed");
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verify data sets page is loaded."+e.getMessage());
			
		}
	}
}




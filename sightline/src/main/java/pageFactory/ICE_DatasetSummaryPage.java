package pageFactory;

import automationLibrary.Driver;
import automationLibrary.Element;


public class ICE_DatasetSummaryPage {

	Driver driver;
	String datasetName;
	
	public Element getPageTitle() {return driver.FindElementByCssSelector("h1.page-title");}
	public Element getExcludeFilesLink() {return driver.FindElementByXPath("//*[@id='divDatasetProcess']/div[2]/div/div[1]/div/div/div/div[3]/div/div/a");}
	public Element getIncludeErrorsLink() {return driver.FindElementByXPath("//*[@id='divDatasetProcess']/div[2]/div/div[1]/div/div/div/div[4]/div/div/a");}
	public Element getDatasetHistorygraph() {return driver.FindElementById("canvas");}
	public Element getPublishedFilesGraph() {return driver.FindElementById("queuecanvas");}
	public Element getProcessedFileTable() {return driver.FindElementByClassName("table");}
	public Element getViewSetinBtn() {return driver.FindElementById("idAction");}
	public Element getDocViewLink() {return driver.FindElementById("idBulkFolder");}
	public Element getDocListLink() {return driver.FindElementById("idBulkTag");}
	public Element getTallyLink() {return driver.FindElementById("idBulkTally");}
	public Element getUploadedFileList() {return driver.FindElementById("downloadUploadedDatasetFiles");}
	
	public ICE_DatasetSummaryPage(Driver driver,String datasetname)
	{
		this.driver = driver;
		this.datasetName = datasetname;
	}
	
	public boolean isDatasetSummaryPageLoaded()
	{
		String pagename = driver.FindElementByClassName("page-title").getText().toString().trim();
		if(pagename.equalsIgnoreCase("Dataset Summary: "+datasetName))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public int viewSetInDocView()
	{
		if(isDatasetSummaryPageLoaded())
		{
			int docsCountInDocView;
			//int DocsCount = GenFunc.StringToInt(driver.FindElementByCssSelector("#divDatasetProcess > div.col-md-6\\> > div > div:nth-child(1) > div > div > div > div:nth-child(1) > div > div").getText().toString().trim());
			getViewSetinBtn().Click();
			getDocViewLink().WaitUntilPresent().Click();
			driver.waitForPageToBeReady();
			docsCountInDocView = driver.FindElementByCssSelector("#SearchDataTable > tbody ").getTrs().size();
			return docsCountInDocView;
			
		}
		else
		{
			new Exception("The Dataset Summary page for provided dataset is not loaded, please verify the error manually.");
			return 0;
		}
	}
	

	public int viewSetInDocList()
	{
		if(isDatasetSummaryPageLoaded())
		{
			int docsCountInDocView;
			//int DocsCount = GenFunc.StringToInt(driver.FindElementByCssSelector("#divDatasetProcess > div.col-md-6\\> > div > div:nth-child(1) > div > div > div > div:nth-child(1) > div > div").getText().toString().trim());
			getViewSetinBtn().Click();
			getDocListLink().WaitUntilPresent().Click();
			driver.waitForPageToBeReady();
			docsCountInDocView = driver.FindElementByCssSelector("#dtDocList > tbody").getTrs().size();
			return docsCountInDocView;
			
		}
		else
		{
			new Exception("The Dataset Summary page for provided dataset is not loaded, please verify the error manually.");
			return 0;
		}
	}
	
	public boolean viewSetInTally()
	{
		if(isDatasetSummaryPageLoaded())
		{
			
			getViewSetinBtn().Click();
			getTallyLink().WaitUntilPresent().Click();
			driver.waitForPageToBeReady();
			if(driver.FindElementByClassName("page-title").getText().toString().trim().equalsIgnoreCase("Tally"))
			{
				return true; 
			}
			return false;
		}
		else
		{
			new Exception("The Dataset Summary page for provided dataset is not loaded, please verify the error manually.");
			return false;
		}
	}
	
	public int viewExlcudedFilesAndDocuments()
	{
		if(isDatasetSummaryPageLoaded())
		{
		
			int excludedFilesCount=0;
			getExcludeFilesLink().Click();
			driver.waitForPageToBeReady();
			if(driver.FindElementByClassName("dataTables_empty").Present())
			{
				return 0;
			}else
			{
				excludedFilesCount = driver.FindElementByCssSelector("#dtExcludedDocs > tbody").getTrs().size();
				return excludedFilesCount; 
			}
		}else
		{
			new Exception("The Dataset Summary page for provided dataset is not loaded, please verify the error manually.");
			return 0;
		}
	}
	
	public int viewIncludedDocsWithErrors()
	{

		if(isDatasetSummaryPageLoaded())
		{
		
			int includedFilesCount=0;
			getIncludeErrorsLink().Click();
			driver.waitForPageToBeReady();
			if(driver.FindElementByClassName("dataTables_empty").Present())
			{
				return 0;
			}else
			{
				includedFilesCount = driver.FindElementByCssSelector("#dtExcludedDocs > tbody").getTrs().size();
				return includedFilesCount; 
			}
		}else
		{
			new Exception("The Dataset Summary page for provided dataset is not loaded, please verify the error manually.");
			return 0;
		}
	}
}

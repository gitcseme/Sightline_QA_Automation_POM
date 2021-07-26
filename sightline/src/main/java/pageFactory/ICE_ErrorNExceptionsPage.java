package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import automationLibrary.ExcelUtils;
import automationLibrary.GenFunc;

public class ICE_ErrorNExceptionsPage {
	
	Driver driver;
	String datasetName;
	BaseClass bc;
	
	public Element getErrorNExceptionPage() {return driver.FindElementByCssSelector("ol.breadcrumb > li:nth-child(2)");}
	public Element getExcludeFileDocumentsTab() {return driver.FindElementById("liExcludedFilesDocuments");}
	public Element getExcludeFileDocumentsTabLink() {return driver.FindElementByCssSelector("#liExcludedFilesDocuments > a");}
	public Element getIncludedDocsWithErrorsTab() {return driver.FindElementById("liIncludedDocswithErrors");}
	public Element getIncludedDocsWithErrorsLink() {return driver.FindElementByCssSelector("#liIncludedDocswithErrors > a");}
	public ElementCollection getExcludedFilesList() {return driver.FindElementsByCssSelector("#dtExcludedDocs > tbody > tr");}
	public ElementCollection getIncludedFilesList() {return driver.FindElementsByCssSelector("#dtIncludedDocs > tbody > tr");}
	public Element getExportListBtn() {return driver.FindElementByCssSelector("#btnExportExFile");}
	public Element getMessage() {return driver.FindElementByCssSelector("#divbigBoxes > div > div > i > span");}
	public boolean isMessageSuccess() {return driver.FindElementByCssSelector("#divbigBoxes > div > div > i > span").getText().equalsIgnoreCase("success !");}
	public Element getIncludedDocsWithErrors() {return driver.FindElementByXPath("//*[@id='dtIncludedDocs']/tbody/tr/td[3]");}
	public ElementCollection getExcludedDocsWithErrors() {return driver.FindElementsByXPath("//*[@id='dtExcludedDocs']/tbody/tr/td/input");}
	public Element getExcludedDocsWithErrors1() {return driver.FindElementByXPath("//*[@id='dtExcludedDocs']/tbody/tr/td/input");}

	
	public ICE_ErrorNExceptionsPage(Driver driver, String datasetname)
	{
		this.driver = driver;
		this.datasetName = datasetname;
	}
	
	public HashMap<String,String> getIncludedDocsListMap()
	{
		driver.scrollPageToTop();
		HashMap<String,String> inFileList = new HashMap<String,String>();
		this.getIncludedDocsWithErrorsLink().WaitUntilPresent().Click();
		driver.waitForPageToBeReady();
		ElementCollection files = this.getIncludedFilesList();
		for(Element file:files)
			{
			driver.scrollingToElementofAPage(file);
				inFileList.put(file.FindElementBycssSelector("td:nth-child(2)").getText(), file.FindElementBycssSelector("td:nth-child(3)").getText());
			}
		return inFileList;
	}
	
	public int getIncludeDocsListCount()
	{
		int inCount = 0;
		driver.scrollPageToTop();
		this.getIncludedDocsWithErrorsLink().WaitUntilPresent().Click();
		driver.waitForPageToBeReady();
		ElementCollection files = this.getIncludedFilesList();
		for(Element file:files)
		{
			file.ScrollTo();
			inCount = inCount+GenFunc.StringToInt(file.FindElementBycssSelector("td:nth-child(3)").getText());
		}
		return inCount;
	}
	
	public int getExcludedDocsListCount()
	{
		
		int inCount = 0;
		//getExcludeFileDocumentsTabLink().WaitUntilPresent().Click();
		//driver.waitForPageToBeReady();
		ElementCollection files = this.getExcludedFilesList();
		inCount = inCount+files.size();
		try{
			driver.scrollingToBottomofAPage();
			ElementCollection pages = driver.FindElementsByCssSelector("li.paginate_button");
			for(int i =1;i<pages.size()-2;i++)
			{
				driver.scrollingToBottomofAPage();
				driver.FindElementByCssSelector("li.paginate_button.next > a").WaitUntilPresent().Click();
				driver.waitForPageToBeReady();
				driver.scrollPageToTop();
				ElementCollection pFiles = this.getExcludedFilesList();
				inCount = inCount+pFiles.size();
			}
				
		}catch(Exception e)
		{
			System.out.println("lets see the value");
			//to do list
		}
		return inCount;
	}
	

	public String[][] SelectExcludedFilesForExportByIndex( int[] indexArray)
	{
		String[][] ElementsSelected = new String[indexArray.length][2];
		int[] indexarray = indexArray;
		for(int index=0;index<indexarray.length;index++)
		{
			int indexValue = 0;
		try{
			int indexv = indexarray[index];
			driver.scrollingToBottomofAPage();
			ElementCollection pages = driver.FindElementsByCssSelector("li.paginate_button");
			driver.scrollPageToTop();
			
			for(int i =1;i<=pages.size()-2;i++)
			{
				ElementCollection pFiles = this.getExcludedFilesList();
				indexValue = indexValue+pFiles.size();
				if(indexv<indexValue)
				{
					int indexto = indexv%10;
					ElementsSelected[index][0]=pFiles.getElementByIndex(indexto).FindElementBycssSelector("td:nth-child(2)").getText();
					ElementsSelected[index][1]=pFiles.getElementByIndex(indexto).FindElementBycssSelector("td:nth-child(3)").getText();
					pFiles.getElementByIndex(indexto).FindElementBycssSelector("input.checkAll").Click();
					break;
				}
				driver.scrollingToBottomofAPage();
				driver.FindElementByCssSelector("li.paginate_button.next > a").WaitUntilPresent().Click();
				driver.waitForPageToBeReady();
				driver.scrollPageToTop();
			}
			for(int i =1;i<=pages.size()-3;i++)
			{
				driver.scrollingToBottomofAPage();
				driver.FindElementByCssSelector("li.paginate_button.previous > a").WaitUntilPresent().Click();
				driver.waitForPageToBeReady();
				driver.scrollPageToTop();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//to do list
		}
		}
		return ElementsSelected;
	}

	public String[][] SelectAllExcludedFilesForExport()
	{
		int index = this.getExcludedDocsListCount();
		String[][] ElementsSelected = new String[index][2];
		driver.FindElementByCssSelector("#dtExcludedDocs > thead > tr > th > label > i").Click();
		driver.waitForPageToBeReady();
		driver.FindElementByCssSelector("#Yes").WaitUntilPresent().Click();
		driver.FindElementByCssSelector("#bot1-Msg1").Click();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		ElementCollection pages = driver.FindElementsByCssSelector("li.paginate_button");
		driver.scrollPageToTop();
		for(int i =1;i<=pages.size()-2;i++)
		{
			ElementCollection pFiles = this.getExcludedFilesList();
			int indexv = pFiles.size();
			for(int j =0;j<indexv;j++)
			{
				ElementsSelected[j][0]=pFiles.getElementByIndex(j).FindElementBycssSelector("td:nth-child(2)").getText();
				ElementsSelected[j][1]=pFiles.getElementByIndex(j).FindElementBycssSelector("td:nth-child(3)").getText();
			}
			driver.scrollingToBottomofAPage();
			driver.FindElementByCssSelector("li.paginate_button.next > a").WaitUntilPresent().Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
		}
			
			return ElementsSelected;
	}
	
	public String[][] getExcludeFileDownload(String path) throws Exception
	{
		String fileName= null;
		ArrayList<String> fileList = new ArrayList<String>();
		fileList = GenFunc.getFilesNamesByFolder(path, fileList);
		for(String file: fileList)
		{
			if(file.split("_")[0].equalsIgnoreCase("excludedfile"))
			{
				fileName = file;
			}
		}
		if(fileName!=null)
		{
		ExcelUtils eu = new ExcelUtils();
		String[][] values = eu.getArray(path+"\\"+fileName, "ExportDocHistory");
		return values;
		}
		return null;
	}
	
	public boolean deleteExportedFile(String path)
	{
		File filedel=null;
		String fileName=null;
		ArrayList<String> fileList = new ArrayList<String>();
		fileList = GenFunc.getFilesNamesByFolder(path, fileList);
		for(String file: fileList)
		{
			if(file.split("_")[0].equalsIgnoreCase("excludedfile"))
			{
				fileName = file;
				filedel = new File(path+"\\"+file);
			}
		}
		if(fileName!=null)
		{
			if(filedel.delete())
			{
				return true;
			}
		}
		return false;
		
	}
	
	public boolean isExportAccurate(String[][] selected, String[][] result)
	{
		Boolean match = false;
		String[][] sel = selected;
		String[][] res = result;
		String[][] resFinal = new String[res.length-1][2];
		for(int i = 0;i<res.length-1;i++)
		{
			resFinal[i][0] = res[i+1][1];
			resFinal[i][1] = res[i+1][2];
		}
		System.out.println(sel.length+" matched "+resFinal.length);
		
		if(sel.length == resFinal.length)
		{
			for(int n=0;n<resFinal.length;n++)
			{
			
			for (int i =0;i<sel.length;i++)
			{
				if(resFinal[n][0].equals(sel[i][0]))
				{
					System.out.println(resFinal[n][0]+" matched "+sel[i][0]);
					if(resFinal[n][1].equals(sel[i][1]))
					{
						System.out.println(resFinal[n][1]+" matched "+sel[i][1]);
						match = true;
					}else
					{
						match=false;
					}
				}
			}
			}
		}
		return match;
	}

	public void selectfewfilesforexport()
	{
		getExcludedDocsWithErrors().WaitUntilPresent();
	//	ElementCollection files = getExcludedDocsWithErrors();
		
		Random r = new Random();
	//	int randomValue = r.nextInt(getExcludedDocsWithErrors().size()); //Getting a random value that is between 0 and (list's size)-1
	//	getExcludedDocsWithErrors().getElementByIndex(randomValue).waitAndClick(10);; //Clicking on the random item in the list.
		
		 
		 List<WebElement> optimized = getExcludedDocsWithErrors().FindWebElements();
		 int[] indexArray = new int[] {1,3,11,12};
	         for(int k=0;k<indexArray.length;k++)
         {
        		optimized.get(k).click();
    			
    		
       	   }
		
	}
	
	public void testExportAllRecords(String downloadPath,int noOfEntries) throws Exception {
		
	
        File file = getLatestFilefromDir(downloadPath);
		String csvFileName = file.getName();
		System.out.println("CSV File Downloaded is :- "+csvFileName);

		System.out.println("Verifying number of entries with number of entries in csv");
		getRowCount(downloadPath);
		Assert.assertEquals(noOfEntries, getRecordsCountInCSV(downloadPath,csvFileName));
	}
	


public int getRecordsCountInCSV(String downloadPath, String csvFileName) {
	int lineNumberCount = 0;
	try {
		if (!csvFileName.isEmpty() || csvFileName != null) {
			String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
			System.out.println(filePath);
			File file = new File(filePath);
			if (file.exists()) {
				System.out.println("File found :" +csvFileName);
				FileReader fr = new FileReader(file);
				LineNumberReader linenumberreader = new LineNumberReader(fr);
				while (linenumberreader.readLine() != null) {
					lineNumberCount++;
				}
				//To remove the header
				lineNumberCount=lineNumberCount-1;
				System.out.println("Total number of lines found in csv : " + (lineNumberCount));
				linenumberreader.close();
				
			} else {
				System.out.println("File does not exists");
			}
		}
	}
	catch (IOException e) {
		e.printStackTrace();
	}
	
	return lineNumberCount;
}

 public int getRowCount(String downloadPath) throws Exception {

		// Making of excel file object 
	    File myFile = new File(downloadPath);
		FileInputStream fis = new FileInputStream(myFile); 
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis); 
		XSSFSheet mySheet = myWorkBook.getSheet("Logout"); 
		XSSFRow row = null; 
 
		// Making the object of excel row 
		row = mySheet.getRow(0); 
 
		int colCount = row.getLastCellNum(); 
		System.out.println("Column Count :- " + colCount); 
		 
		int rowCount = mySheet.getLastRowNum() + 1; 
		System.out.println("Row Count :- " + rowCount);
		return rowCount; 
	} 
	

//Below method is used to get the latest file from the directory. 
//It takes the folder path as the parameter and returns the file which is recently added to the folder.

private File getLatestFilefromDir(String dirPath){
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
        return null;
    }

    File lastModifiedFile = files[0];
    for (int i = 1; i < files.length; i++) {
       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
           lastModifiedFile = files[i];
       }
    }
    return lastModifiedFile;
}


	
	
		
}

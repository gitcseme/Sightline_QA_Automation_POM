package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.interactions.Actions;

import com.opencsv.CSVReader;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class CustomDocumentDataReport {
	  Driver driver;
	    public Element getCustomDocumentDataReport(){ return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Custom Document Data Report')]"); }
	    
	    public Element getTally_SelectSource(){ return driver.FindElementById("select-source"); }
	    public Element getTally_SecurityGroupsButton(){ return driver.FindElementByXPath("//strong[contains(.,'Security Groups')]"); }
	       
	    public Element getTally_SelectSecurityGroup(String value){ return driver.FindElementByXPath(".//*[@id='secgroup']/li[contains(.,'"+value+"')]/label"); }
	    public Element getTally_SaveSelections(){ return driver.FindElementByXPath("//button[@id='secgroup']"); } 
	    public Element getMetdataField(String metaDataField){ return driver.FindElementByXPath("//*[@id='tab1-export']/div/ul/li/label/strong[contains(text(),'"+metaDataField+"')]"); } 
	  
	    
	    public Element getAddToSelectedBtn(){ return driver.FindElementById("addFormObjects-coreList"); } 
	    public Element getSwapMetaDataFiledSource(int poistion){ return driver.FindElementByXPath("//div[@id='nestable']//*[@id='"+poistion+"']/div[1]"); } 
	    public Element getSwapMetaDataFiledDest(int poistion){ return driver.FindElementByXPath("//div[@id='nestable']//*[@id='"+poistion+"']/div[1]"); } 
		  
	    public Element getRunReport(){ return driver.FindElementById("btnRunReport"); } 
	    public Element getBckTask_selectExport(){ return driver.FindElementByXPath("(//a[contains(text(),'Your export is ready please click here to download')])[1]"); }
	    
	    public CustomDocumentDataReport(Driver driver){

	    	this.driver = driver;
	        this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
	        
	        //This initElements method will create all WebElements
	        //PageFactory.initElements(driver.getWebDriver(), this);

	    }
	    
	    public void selectSource(String sourceName, final String sourceValue) throws InterruptedException {
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getCustomDocumentDataReport().Visible()  ;}}), Input.wait30); 
	    	getCustomDocumentDataReport().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getTally_SelectSource().Visible()  ;}}), Input.wait30); 
			getTally_SelectSource().Click();
			Thread.sleep(2000);
            if(sourceName.equalsIgnoreCase("Security Groups")){
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getTally_SecurityGroupsButton().Visible()  ;}}), Input.wait30); 
			getTally_SecurityGroupsButton().waitAndClick(10);
			Thread.sleep(2000);
            
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getTally_SelectSecurityGroup(sourceValue).Visible()  ;}}), Input.wait30); 
		    getTally_SelectSecurityGroup(sourceValue).waitAndClick(10);
		   // Thread.sleep(2000);
            }
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getTally_SaveSelections().Visible()  ;}}), Input.wait30);		
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
	    	
	    	new Actions(driver.getWebDriver()).dragAndDrop(getSwapMetaDataFiledSource(source).getWebElement(), getSwapMetaDataFiledDest(dest).getWebElement()).perform();

		}
	    public void downloadExport() throws InterruptedException {
	    	//Take Back up and clear all files
	    	
	    	final BaseClass base = new BaseClass(driver);
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			base.getBackgroundTask_Button().Visible()  ;}}),Input.wait60);
	    	base.getBackgroundTask_Button().Click();
			
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getBckTask_selectExport().Visible()  ;}}), Input.wait30);		
	    	getBckTask_selectExport().Click();
	    	
	    	//download time
			Thread.sleep(5000);
			
			
		}
	    
  public void validateColumnsInExport(ArrayList expected) throws IOException {
	          ArrayList<String> actual = new ArrayList();
	    	  File root = new File(System.getProperty("user.dir")+Input.batchFilesPath+"BatchPrintFiles/downloads/");
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
	          while ((nextRecord = csvReader.readNext()) != null) { 
	              for (String cell : nextRecord) { 
	                
	            	  actual.add(cell.toString().trim().replaceAll("ï»¿\"",""));
	                  //System.out.println(cell.toString());
	                  //System.out.println(cell.toString().replaceAll("ï»¿\"",""));
	  	    		
	              } 
	            
	              break;
	          } 
      	    
	  	
	    	  System.out.println("Selected columns order : "+expected );
	    	  UtilityLog.info("Selected columns order : "+expected );
	    	  System.out.println("Columns order in export  : "+actual );
	    	  UtilityLog.info("Columns order in export  : "+actual );
	    	  for (int i = 0; i < expected.size(); i++) {
	    		  Assert.assertTrue(expected.get(i).equals(actual.get(i)));	
	    	}
	    	 


		}
	    public void reportRunSuccessMsg() {
	    	BaseClass bc =new BaseClass(driver);
	    	bc.VerifySuccessMessage("Your Report has been added into the Background successfully. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");
	    	
		}
}

package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class DocListPage {

    Driver driver;
    SessionSearch search;
    BaseClass base;
  
    public Element getDocList_info(){ return driver.FindElementById("dtDocList_info"); }
    public ElementCollection getDocListRows(){ return driver.FindElementsById("//*[@id='dtDocList']/tbody/tr"); }
    public Element getColumnText(int row, int col){ return driver.FindElementByXPath("//*[@id='dtDocList']/tbody/tr["+row+"]/td["+col+"]/a"); }
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
     //Filters
    public Element getCustodianFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'CustodianName')]"); }
    public Element getMasterDateFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'MasterDate')]"); }
    public Element getDocFileSizeFiler(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'DocFileSize')]"); }
    public Element getGetDocFIleTypeFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'DocFileType')]"); }
    public Element getEmailAuthNameFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorName')]"); }
    public Element getEmailRecNameFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailRecipientNames')]"); }
    public Element getEmailAuthDomainFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorDomain')]"); }
    public Element getEmailAllDomainFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAllDomains')]"); }
    //options
    public Element getMasterDateRange(){ return driver.FindElementByXPath("(//select[@id='MasterDate-dtop'])[1]"); }
    public Element getSetFromMasterDate(){ return driver.FindElementByXPath("(//*[contains(@id,'MasterDate-1-DOC')])[1]"); }
    public Element getSetToMasterDate(){ return driver.FindElementByXPath("(//*[@id='MasterDate-2-DOCLIST'])[1]"); }
    public Element getDocFileSizeOption(){ return driver.FindElementByXPath("(//select[@id='DocFileSize-op'])[1]"); }
    public Element getDocFileFromSize(){ return driver.FindElementByXPath("(//*[@id='DocFileSize-1-DOCLIST'])[1]"); }
    public Element getDocFileToSize(){ return driver.FindElementByXPath("(//*[@id='DocFileSize-2-DOCLIST'])[1]"); }
    //include exclude radio buttons
    public Element getIncludeRadioBtn(){ return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[1])[1]"); }
    public Element getExcludeRadioBtn(){ return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[2])[1]"); }
    //Serach text
    public Element getSearchTextArea(){ return driver.FindElementByXPath("//*[@type='search']"); }
    public Element getSelectFromList(){ return driver.FindElementByXPath("//*[@type='search']"); }
    public Element getAddToFilter(){ return driver.FindElementByXPath("(//*[contains(text(),' Add to Filter')])[1]"); }
    public Element getApplyFilter(){ return driver.FindElementByXPath("//*[@id='btnAppyFilter']"); }
    //Cancel filter
    public Element getCancelFilter1(){ return driver.FindElementByXPath("//*[@id='Include']/i"); }
    public Element getCancelFilter2(){ return driver.FindElementByXPath("//*[@id='undefined']/i"); }
    //Actions
   // public Element getSelectAll(){ return driver.FindElementByXPath("//*[@id='dtDocList']/thead/tr/th[2]/label"); }
    public Element getSelectAll(){ return driver.FindElementByXPath("//*[@id='selectAllRows']/following-sibling::i"); }
    public Element getYesAllPageDocs(){ return driver.FindElementByXPath("(//*[@id='Yes'])[1]"); }
    public Element getPopUpOkBtn(){ return driver.FindElementByXPath("//button[@id='bot1-Msg1']"); }
    public Element getBackToSourceBtn(){ return driver.FindElementByXPath("//a[contains(text(),'Back to Source')]"); }
  //addition by shilpi on 04/26
    public Element getDocList_Previewpage(){ return driver.FindElementById("divPreviewDocument"); }
    public Element getDocList_Preview_Pagenotextbox(){ return driver.FindElementById("PageNumber_divPreviewDocViewer"); }
    public Element getDocList_Preview_AudioPlay(){ return driver.FindElementById("btnPlayPause"); }
    public Element getDocList_Preview_AudioStop(){ return driver.FindElementByXPath("//*[@class='jp-stop fa fa-stop']"); }
    public Element getDocList_Preview_AudioStartTime(){ return driver.FindElementByXPath("//*[@class='jp-current-time start gmt']"); }
    public Element getDocList_Preview_AudioDuration(){ return driver.FindElementByXPath(".//*[@class='jp-current-time']"); }
    public Element getDocList_actionButton(){ return driver.FindElementById("idAction"); }
    public Element getDocList_action_BulkAssignButton(){ return driver.FindElementById("idBulkAssign"); }
    public Element getDocList_action_BulkReleaseButton(){ return driver.FindElementById("idBulkRelease"); }
    public Element getDocList_SelectLenthtobeshown(){ return driver.FindElementById("idPageLength"); }
    
    public Element getDocList_QuickBatch(){ return driver.FindElementByXPath("//a[contains(text(),'Quick Batch')]"); }
    public Element getDocList_Preview_CloseButton(){ return driver.FindElementByXPath("//span[@id='ui-id-1']/following-sibling::button"); }
    public Element getrowColumnText(int row, int col){ return driver.FindElementByXPath("//*[@id='dtDocList']/tbody/tr["+row+"]/td["+col+"]"); }
    
  
    
    public DocListPage(Driver driver){

        this.driver = driver;
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);
        search = new SessionSearch(driver);
    	base = new BaseClass(driver);
    	this.driver.getWebDriver().get(Input.url+"Document/DocList");
       }
    
    public void include(String data) {
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getIncludeRadioBtn().Visible()  ;}}), Input.wait30);
    	getIncludeRadioBtn().Click();
    	getSearchTextArea().SendKeys(data);
    	try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	getSearchTextArea().SendKeysNoClear(""+Keys.ENTER);
    	getAddToFilter().waitAndClick(10);
    
	}
    public void exclude(String data) {
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getExcludeRadioBtn().Visible()  ;}}), Input.wait30);
    	 getExcludeRadioBtn().Click();
    		getSearchTextArea().SendKeys(data);
        	try {
    			Thread.sleep(4000);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	getSearchTextArea().SendKeysNoClear(""+Keys.ENTER);
        	getAddToFilter().Click();
    	
	}
    
    public void dateFilter(String option, String fromDate, String toDate) {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMasterDateFilter().Visible()  ;}}), Input.wait30);
		
    	getMasterDateFilter().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMasterDateRange().Visible()  ;}}), Input.wait30);
    	getMasterDateRange().selectFromDropdown().selectByValue(option);
    	
    	getSetFromMasterDate().SendKeys(fromDate+Keys.TAB);
    	
    	if(option.equalsIgnoreCase("between")) 
    	getSetToMasterDate().SendKeys(toDate+Keys.TAB);
    	
    	getAddToFilter().waitAndClick(10);
    	getApplyFilter().waitAndClick(10);
		
    	
	}
    
    public void removeRpeviousFilters() {
    	driver.scrollPageToTop();
    	try{
    		getCancelFilter2().waitAndClick(10);
    	}catch (Exception e) {
    		
    		getCancelFilter1().waitAndClick(10);
		}
	}
    
   public void validateCount(String counts) {
	   driver.scrollingToBottomofAPage();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		   !getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	   //to make sure that not reading previous results and wait for page mask to complete,
	   //simply try to click on result text!
	   driver.scrollingToBottomofAPage();
	   getDocList_info().waitAndClick(30); //works only when results updates!!
       
	   //Now validate results
	   Assert.assertTrue(getDocList_info().getText().toString().equalsIgnoreCase(counts));
	   
}
   
   public void DoclistPreviewNonAudio() throws InterruptedException {
		
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getColumnText(1,8).Visible()  ;}}),Input.wait60);
	   getColumnText(1,8).Click();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getDocList_Previewpage().Visible()  ;}}),Input.wait30);
	   Assert.assertTrue(getDocList_Previewpage().Enabled());
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getDocList_Preview_Pagenotextbox().Visible()  ;}}),Input.wait30);
	   getDocList_Preview_Pagenotextbox().SendKeys("5");
	   getDocList_Preview_Pagenotextbox().Enter();
	   
	
	   driver.scrollingToBottomofAPage();
	   Thread.sleep(2000);
	   
	   driver.scrollPageToTop();
	  }

     public void DoclistPreviewAudio() throws InterruptedException {
		
      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			   getColumnText(1,8).Visible()  ;}}),Input.wait60);
       getColumnText(1,8).waitAndClick(10);
  	 Thread.sleep(5000);
  	   
      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			   getDocList_Preview_AudioPlay().Visible()  ;}}),Input.wait60);
 	   Assert.assertTrue(getDocList_Preview_AudioPlay().Displayed());
 	   
 	  getDocList_Preview_AudioPlay().waitAndClick(10);
 	  Thread.sleep(7000);
 	 
 	  getDocList_Preview_AudioPlay().waitAndClick(10);
 	  
 	  String playtime = getDocList_Preview_AudioDuration().getText(); 
 	  System.out.println(playtime);	
 	  
 	 getDocList_Preview_AudioStop().waitAndClick(10);
	  
 	 
 	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			   getDocList_Previewpage().Visible()  ;}}),Input.wait30);
 	   Assert.assertTrue(getDocList_Previewpage().Enabled());
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocList_Preview_Pagenotextbox().Visible() ;}}),Input.wait30);
		 * getDocList_Preview_Pagenotextbox().SendKeys("5");
		 * getDocList_Preview_Pagenotextbox().Enter();
		 */
 	   
 	  getDocList_Preview_CloseButton().waitAndClick(10);
 	  }

     public void DoclisttobulkAssign(String assignmentName,String length ) throws InterruptedException {
    	 
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         			getDocList_SelectLenthtobeshown().Visible()  ;}}), Input.wait60);
         	
     getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(length);
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getSelectAll().Visible()  ;}}), Input.wait60); 
   	 getSelectAll().waitAndClick(20);
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getPopUpOkBtn().Visible()  ;}}), Input.wait60); 
   	 getPopUpOkBtn().Click();
   	 
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	   getDocList_actionButton().Visible()  ;}}), Input.wait60); 
     getDocList_actionButton().waitAndClick(10);
     Thread.sleep(3000);
    	     
     getDocList_action_BulkAssignButton().waitAndClick(10);
     
     System.out.println("performing bulk assign");
  }
     
     public void DoclisttobulkRelease(String SecGroup) throws InterruptedException {
     	
       	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getSelectAll().Visible()  ;}}), Input.wait60); 
       	 getSelectAll().Click();
       	 
       	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getPopUpOkBtn().Visible()  ;}}), Input.wait60); 
       	 getPopUpOkBtn().Click();
       	 
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        	   getDocList_actionButton().Visible()  ;}}), Input.wait60); 
         getDocList_actionButton().waitAndClick(10);
         Thread.sleep(3000);
        	     
         getDocList_action_BulkReleaseButton().waitAndClick(10);
         
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 search.getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible()  ;}}), Input.wait60); 
         search.getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Click();
    	 
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 search.getBulkRelease_ButtonRelease().Visible()  ;}}),Input.wait60); 
    	 search.getBulkRelease_ButtonRelease().Click();
    	 
    	 search.getFinalizeButton().waitAndClick(30);
       
    	 base.VerifySuccessMessage("Records saved successfully");
    	 
    	 System.out.println("performing bulk release");
    	
      }
     
      public void Selectpagelength(String length) {
     	//driver.getWebDriver().get(Input.url+ "Document/DocList");
     	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getDocList_SelectLenthtobeshown().Visible()  ;}}), Input.wait60);
     	
     	getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(length);
   
}
      
      public void DoclisttoQuickbatch(String length ) throws InterruptedException {
     	 
    	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	         			getDocList_SelectLenthtobeshown().Visible()  ;}}), Input.wait30);
    	         	
    	     getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(length);
    	   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				 getSelectAll().Visible()  ;}}), Input.wait60); 
    	   	 getSelectAll().waitAndClick(20);
    	   	 
    	   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	   			getPopUpOkBtn().Visible()  ;}}), Input.wait60); 
    	   	 getPopUpOkBtn().Click();
    	   	 
    	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	    	   getDocList_actionButton().Visible()  ;}}), Input.wait60); 
    	     getDocList_actionButton().waitAndClick(10);
    	     Thread.sleep(3000);
    	    	     
    	     getDocList_QuickBatch().waitAndClick(10);
    	     
    	     System.out.println("performing quick batch");
    	  }
      
}
package pageFactory;

import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ABMReportPage {

    Driver driver;
    BaseClass bc;
  
    public Element getReport_ABM(){ return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Advanced Batch Management Report')]"); }
    public Element getABM_SelectSource(){ return driver.FindElementById("select-new-source"); }
    public Element getABM_SearchButton(){ return driver.FindElementByXPath("//*[@id='searchesPopup']//strong"); }
    public Element getABM_AssignmentButton(){ return driver.FindElementByXPath("//*[@id='assignmentsPopup']//strong"); }
    public Element getABM_FolderButton(){ return driver.FindElementByXPath("//*[@id='foldersPopup']//strong"); }
    public Element getABM_SelectSearch(String search){ return driver.FindElementByXPath("//*[@id='searchesJSTree']//a[contains(text(),"+search+")]/i[1]"); }
    public Element getApplyBtn(){ return driver.FindElementById("btn_applychanges"); } 
    
    public Element getABM_ReviewerExpandbutton(){ return driver.FindElementByXPath("//*[@id='divreviewer']/div/a[2]"); } 
    public Element getABM_SelectAssgn(String assgnname){ return driver.FindElementByXPath("//*[@id='jstreeAssignmentGroup']//a[contains(text(),"+assgnname+")]"); }
    public Element getCommunicationExplorer_Graph_Action_DropDown_SubTagOption(){ return driver.FindElementById("a[onclick*='SubTag']"); }
    public Element getCommunicationExplorer_Graph_Action_DropDown_BulkTag_TotDocCount(){ return driver.FindElementById("spanTotal"); }
    public Element getABM_Reviewer_SelectAll(){ return driver.FindElementByXPath("//*[@id='checkAll4']/following-sibling::i"); }
    public Element getSelectaTallyFieldtoruntallyon(){ return driver.FindElementById("select-source1"); }
    public Element getABM_SelectAssignment(){ return driver.FindElementById("assignmentGroupID"); }
    public Element getABM_Searchsavebutton(){ return driver.FindElementById("search"); }
    public Element getABM_SummaryPage(){ return driver.FindElementById("ABMSummary"); } 
    public Element getAction_ViewInDoclistButton(){ return driver.FindElementByXPath(".//*[@class='dropdown-menu']//a[contains(.,'View All in DocList')]"); } 
    
    public Element getTally_SubTally_Action_ViewButton(){ return driver.FindElementByXPath(".//*[@id='freezediv']//a[contains(.,'View')]"); } 
    public Element getTally_subMetadata(){ return driver.FindElementByXPath(".//*[@id='accordion2']//strong[contains(.,'METADATA')]"); } 
    public Element getTally_SubTally_Action_ViewDocList(){ return driver.FindElementById("idSubTallyViewDoclist"); } 
    
    public Element getTally_LoadingScreen(){ return driver.FindElementByXPath("//span[@class='LoadImagePosition']/img[@src='/img/loading.gif']"); } 
    
    
    
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
   
    public ABMReportPage(Driver driver){

    	this.driver = driver;
    	bc = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
        
     }
    
    public void ValidateABMreport(final String savedsearch, final String assgnname) throws InterruptedException {
    	
    	 driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
         
      	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getReport_ABM().Visible()  ;}}), Input.wait30); 
         getReport_ABM().Click();
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			  getApplyBtn().Visible()  ;}}), Input.wait30);	
    	  getApplyBtn().Click();
    	  
    	  bc.VerifyWarningMessage("Please select source details");
    	  bc.CloseSuccessMsgpopup();
		  
		  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getABM_SelectSource().Visible()  ;}}), Input.wait30);		
		   getABM_SelectSource().Click();
		   
		   getABM_SearchButton().waitAndClick(10);
		   Thread.sleep(2000);
		   
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getABM_SelectSearch(savedsearch).Visible()  ;}}), Input.wait30);
		 getABM_SelectSearch(savedsearch).Click();
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getABM_Searchsavebutton().Enabled() ;}}), Input.wait30);
		 getABM_Searchsavebutton().Click();
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getApplyBtn().Visible()  ;}}), Input.wait30);	
	      getApplyBtn().Click();
	      Thread.sleep(2000);
	      
	     bc.VerifyWarningMessage("Please select reviewers");
	     bc.CloseSuccessMsgpopup();
	     
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getABM_ReviewerExpandbutton().Visible()  ;}}), Input.wait30);
	     getABM_ReviewerExpandbutton().Click();
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getABM_Reviewer_SelectAll().Enabled() ;}}), Input.wait30);
		 getABM_Reviewer_SelectAll().Click();
		 
		 driver.scrollPageToTop();
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getApplyBtn().Visible()  ;}}), Input.wait30);	
	      getApplyBtn().Click();
	      Thread.sleep(2000);
		      
	     bc.VerifyWarningMessage("Please select assignments");
	     
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getABM_SelectAssignment().Visible()  ;}}), Input.wait30);		
	     getABM_SelectAssignment().Click();
	     Thread.sleep(2000);
		   
		 driver.scrollingToBottomofAPage();   
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getABM_SelectAssgn(assgnname).Visible()  ;}}), Input.wait30);
		 getABM_SelectAssgn(assgnname).Click();
		 
		 driver.scrollPageToTop();
	     getApplyBtn().waitAndClick(10);
	     
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		 getABM_SummaryPage().Visible()  ;}}), Input.wait60);
	   
		 Assert.assertTrue(getABM_SummaryPage().Displayed());
		  System.out.println("test passed");
	  }

     
}
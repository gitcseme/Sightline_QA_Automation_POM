package pageFactory;

import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class CommunicationExplorerPage {

    Driver driver;
  
    public Element getReports_CommunicationsExplorer(){ return driver.FindElementByCssSelector("a[href*='CommunicationExplorerReport']"); }
    public Element getTally_SelectSource(){ return driver.FindElementById("select-source"); }
    public Element getTally_SecurityGroupsButton(){ return driver.FindElementByXPath("//strong[contains(.,'Security Groups')]"); }
       
    public Element getTally_SelectSecurityGroup(){ return driver.FindElementByXPath(".//*[@id='secgroup']/li[contains(.,'Default Security Group')]/label"); }
    public Element getTally_SaveSelections(){ return driver.FindElementByXPath("//button[@id='secgroup']"); } 
    public Element getCommunicationExplorer_ApplyBtn(){ return driver.FindElementById("btn_applychanges"); } 
    
    public ElementCollection getfindAllNodes(){ return driver.FindElementsByCssSelector("div[id='wrapper-graph']  g  text[class='nodetext']"); }

    public Element getCommunicationExplorer_TotalDocCount_OnHover(){ return driver.FindElementByCssSelector(".count-total"); } 
    public Element getCommunicationExplorer_Graph_Action_DropDownBtn(){ return driver.FindElementByCssSelector("div[class*='graph-title']  button[class*='btn-primary dropdown-toggle']"); } 
    public Element getCommunicationExplorer_Graph_Action_DropDown(){ return driver.FindElementById("div[class*='graph-title']  ul[class='dropdown-menu']"); }
    public Element getCommunicationExplorer_Graph_Action_DropDown_SubTagOption(){ return driver.FindElementById("a[onclick*='SubTag']"); }
    public Element getCommunicationExplorer_Graph_Action_DropDown_BulkTag_TotDocCount(){ return driver.FindElementById("spanTotal"); }
    public Element getCommunicationExplorer_Graph_Action_DropDown_AnalyzeBtn(){ return driver.FindElementById("CommtoTally"); }
    public Element getSelectaTallyFieldtoruntallyon(){ return driver.FindElementById("select-source1"); }
    public Element getTally_btnTallyApply(){ return driver.FindElementById("btnTallyApply"); }
    public Element getTally_metadataselect(){ return driver.FindElementById("metadataselect"); }
    public Element getTally_SubTallyActionButton(){ return driver.FindElementById("subtallyactionbtn"); } 
    public Element getAction_ViewInDoclistButton(){ return driver.FindElementByXPath(".//*[@class='dropdown-menu']//a[contains(.,'View All in DocList')]"); } 
    
    public Element getTally_SubTally_Action_ViewButton(){ return driver.FindElementByXPath(".//*[@id='freezediv']//a[contains(.,'View')]"); } 
    public Element getTally_subMetadata(){ return driver.FindElementByXPath(".//*[@id='accordion2']//strong[contains(.,'METADATA')]"); } 
    public Element getTally_SubTally_Action_ViewDocList(){ return driver.FindElementById("idSubTallyViewDoclist"); } 
    
    public Element getTally_LoadingScreen(){ return driver.FindElementByXPath("//span[@class='LoadImagePosition']/img[@src='/img/loading.gif']"); } 
    
    
    
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
   
    public CommunicationExplorerPage(Driver driver){

    	this.driver = driver;
        this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
        
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }
    
    public void ValidateCommExplorerreport() throws InterruptedException {
    	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getReports_CommunicationsExplorer().Visible()  ;}}), Input.wait30); 
    	getReports_CommunicationsExplorer().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SelectSource().Visible()  ;}}), Input.wait30); 
		getTally_SelectSource().Click();
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SecurityGroupsButton().Visible()  ;}}), Input.wait30); 
		getTally_SecurityGroupsButton().waitAndClick(10);
		Thread.sleep(2000);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SelectSecurityGroup().Visible()  ;}}), Input.wait30); 
	    getTally_SelectSecurityGroup().waitAndClick(10);
	   // Thread.sleep(2000);
	    
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getTally_SaveSelections().Visible()  ;}}), Input.wait30);		
	   getTally_SaveSelections().waitAndClick(15);
		
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			  getCommunicationExplorer_ApplyBtn().Visible()  ;}}), Input.wait30);	
	  getCommunicationExplorer_ApplyBtn().Click();
	  Thread.sleep(3000);
	  
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_LoadingScreen().Visible()  ;}}), Input.wait30);
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getTally_LoadingScreen().Stale() ;}}), Input.wait30);
		  
	  
	  driver.scrollingToBottomofAPage();
		
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getfindAllNodes().Exists()  ;}}), Input.wait30);	
		for (WebElement ele : getfindAllNodes().FindWebElements()) {
			   //System.out.println(Configuration.getData("ShareTo")+" - "+ele.getText().trim());
				if(ele.getText().trim().equalsIgnoreCase("symphonyteleca...")){
				ele.click();
					break;
				}
		}
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getCommunicationExplorer_ApplyBtn().Visible()  ;}}), Input.wait30);	
		  String actHoverDocCount =  getCommunicationExplorer_TotalDocCount_OnHover().getText();
		  System.out.println(actHoverDocCount);
		  
		  driver.scrollPageToTop();
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getCommunicationExplorer_Graph_Action_DropDownBtn().Visible()  ;}}), Input.wait30);
		 getCommunicationExplorer_Graph_Action_DropDownBtn().Click();
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getAction_ViewInDoclistButton().Enabled() ;}}), Input.wait30);
		 getAction_ViewInDoclistButton().Click();
		
		 System.out.println("Navigated to Doclist page");
			
		  final DocListPage dp = new DocListPage(driver);
	       dp.getDocList_info().WaitUntilPresent();
	       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   !dp.getDocList_info().getText().isEmpty()  ;}}), Input.wait60);
	       System.out.println(dp.getDocList_info().getText().toString().substring(19, 21));
	       Assert.assertEquals(dp.getDocList_info().getText().toString().substring(19, 21),actHoverDocCount);
	       System.out.println("Expected docs are shown in doclist");

	  }

     
}
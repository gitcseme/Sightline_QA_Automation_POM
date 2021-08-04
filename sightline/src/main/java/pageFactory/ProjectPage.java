package pageFactory;

import java.util.concurrent.Callable;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class ProjectPage {

    Driver driver;
    BaseClass bc;
    
    public Element getAddProjectBtn(){ return driver.FindElementById("btnAdd"); }
    public Element getProjectName(){ return driver.FindElementById("txtproject"); }
    public Element getSelectEntity(){ return driver.FindElementById("ddlEntityTypeList"); }
    public Element getSelectEntityType(){ return driver.FindElementById("ddlDomainType"); }
    public Element getHCode(){ return driver.FindElementById("txtHcode"); }
    public Element getProjectDBServerDropdown(){ return driver.FindElementByXPath("//label[@class='SourceLabel']/select[@id='ddlProjDBServer']"); }
    public Element getProjectServerPath(){ return driver.FindElementByXPath("//*[@id='ddlProjectWS']/option[1]"); }
    public Element getIngestionserverpath(){ return driver.FindElementByXPath(".//*[@id='ddlLoadWS']"); }
    public Element getProductionserverpath(){ return driver.FindElementByXPath(".//*[@id='ddlProductionWS']/option[1]"); }
    public Element getIsActiveButton(){ return driver.FindElementByXPath(".//*[@id='iss1']//label[@class='checkbox']/i"); }
    public Element getProjectFolder(){ return driver.FindElementById("txtProjectFold"); }
    public Element getIngestionFolder(){ return driver.FindElementById("txtIngestionFold"); }
    public Element getProductionFolder(){ return driver.FindElementById("txtProductionFold"); }
    
    public Element getAddProject_SettingsTab(){ return driver.FindElementByXPath(".//*[@id='hr2']//a[contains(.,'Settings')]"); }
    public Element getNoOfDocuments(){ return driver.FindElementById("txtMaxNoOfDocs"); }
    public Element getButtonSaveProject(){ return driver.FindElementById("btnSaveProject"); }
    public Element getSearchProjectName(){ return driver.FindElementById("txtProjectLabel"); }
    public Element getProjectFilterButton(){ return driver.FindElementById("btnFilter"); }
    public Element getProjectName(String projectname){ return driver.FindElementByXPath(".//*[@id='ProjectDataTable']/tbody/tr[td='"+projectname+"']"); }
    
  
 
   
    //Annotation Layer added successfully
    public ProjectPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Project/Project");
        driver.waitForPageToBeReady();
        bc = new BaseClass(driver);
    }

    public void AddDomainProject(String projectname,String clientname) throws InterruptedException {
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddProjectBtn().Visible()  ;}}), Input.wait30); 
    	getAddProjectBtn().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getProjectName().Visible()  ;}}), Input.wait30); 
    	getProjectName().SendKeys(projectname);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectEntityType().Visible()  ;}}), Input.wait30); 
    	getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectEntity().Visible()  ;}}), Input.wait30); 
    	getSelectEntity().selectFromDropdown().selectByVisibleText(clientname);
    	
    	driver.scrollingToBottomofAPage();
    	
    	getProjectFolder().Clear();
    	getProjectFolder().SendKeys("Automation");
    	
    	getIngestionFolder().Clear();
    	getIngestionFolder().SendKeys("Automation");
    	
    	getProductionFolder().Clear();
    	getProductionFolder().SendKeys("Automation");
    	
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddProject_SettingsTab().Visible()  ;}}), Input.wait30); 
    	getAddProject_SettingsTab().waitAndClick(10);;
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNoOfDocuments().Visible()  ;}}), Input.wait30); 
    	getNoOfDocuments().SendKeys("20000");
    	
    	final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        System.out.println(Bgcount);
        UtilityLog.info(Bgcount);
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getButtonSaveProject().Visible()  ;}}), Input.wait30); 
        driver.scrollingToBottomofAPage();
    	getButtonSaveProject().Click();
    	
    	bc.VerifySuccessMessage("Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");
    	  
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait120+Input.wait60);  
 	   System.out.println(bc.initialBgCount());
 	   UtilityLog.info(bc.initialBgCount());
 	   
 	       
	}
    
       public void AddNonDomainProject(String projectname,String hcode) throws InterruptedException{
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddProjectBtn().Visible()  ;}}), Input.wait30); 
    	getAddProjectBtn().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getProjectName().Visible()  ;}}), Input.wait30); 
    	getProjectName().SendKeys(projectname);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectEntity().Visible()  ;}}), Input.wait30); 
    	getSelectEntity().selectFromDropdown().selectByIndex(1);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getHCode().Visible()  ;}}), Input.wait30); 
    	getHCode().SendKeys(hcode);
    	
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getProjectDBServerDropdown().Visible()  ;}}), Input.wait30); 
    	getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getProjectServerPath().Visible()  ;}}), Input.wait30); 
    	getProjectServerPath().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getIngestionserverpath().Visible()  ;}}), Input.wait30); 
    	getIngestionserverpath().selectFromDropdown().selectByIndex(0);
    	getIngestionserverpath().selectFromDropdown().selectByIndex(1);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getProductionserverpath().Visible()  ;}}), Input.wait30); 
    	getProductionserverpath().Click();
    	/*
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getIsActiveButton().Visible()  ;}}), Input.wait30); 
    	getIsActiveButton().Click();
    	*/
    	getProjectFolder().SendKeys("Automation");
    	
    	getIngestionFolder().SendKeys("Automation");
    	
    	getProductionFolder().SendKeys("Automation");
    	
    	driver.scrollPageToTop();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddProject_SettingsTab().Visible()  ;}}), Input.wait30); 
    	getAddProject_SettingsTab().waitAndClick(10);;
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNoOfDocuments().Visible()  ;}}), Input.wait30); 
    	getNoOfDocuments().SendKeys("20000");
    	
    	final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        System.out.println(Bgcount);
        UtilityLog.info(Bgcount);
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getButtonSaveProject().Visible()  ;}}), Input.wait30); 
        driver.scrollingToBottomofAPage();
    	getButtonSaveProject().Click();
    	
    	bc.VerifySuccessMessage("Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");
  	     	  
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait120+Input.wait60);  
 	   System.out.println(bc.initialBgCount());
 	   UtilityLog.info(bc.initialBgCount());
 	   
 	  /* this.driver.getWebDriver().get(Input.url+"Project/Project");
 	  
 	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSearchProjectName().Visible()  ;}}), Input.wait30); 
 	 getSearchProjectName().SendKeys(projectname);
 	 
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getProjectFilterButton().Visible()  ;}}), Input.wait30); 
 	getProjectFilterButton().Click(); 	 
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getProjectName().Visible()  ;}}), Input.wait30); 
 	 getProjectName(projectname).Displayed();
 	  */
 	   
 	   
       
	}
    
    
 }
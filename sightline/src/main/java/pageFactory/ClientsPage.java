package pageFactory;

import java.util.concurrent.Callable;
import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;


public class ClientsPage {

    Driver driver;
    BaseClass bc;
    ProjectPage project;
  
    public Element getAddEntity(){ return driver.FindElementById("btnAddEntity"); }
    public Element getEntityName(){ return driver.FindElementByXPath("//*[@placeholder='Name']"); }
    public Element getEntityNameFilter(){ return driver.FindElementByXPath("//*[@placeholder='Filter by Name']"); }
    public Element getEntityshortname(){ return driver.FindElementById("EntityCode"); }
    public Element getSaveBtn(){ return driver.FindElementById("btnsave"); }
    public Element getFilterButton(){ return driver.FindElementById("btnSearch"); }
    public Element getEntitytype(){ return driver.FindElementById("ddlEntityTypeList"); }
    public Element getDomainID(){ return driver.FindElementById("entity_domainid"); }
    public Element getFiler_Clientname(){ return driver.FindElementByXPath("//*[@id='EntityDataTable']//td[1]"); }
    public Element getEntityServerPath(){ return driver.FindElementByXPath("//*[@id='ddlEntityWS']/option[1]"); }
 
    //added by jayanthi 30/8/21
    public Element getSelectManageBtn(){ return driver.FindElementByCssSelector("a[name='Manage'] i"); }
    public Element getClient_clientPg(){ return driver.FindElementByCssSelector("a[name='Entity'] i"); }
    public Element databaseHelpIcon_clientPg(){ return driver.FindElementByXPath("//label[text()='Initial Size of Project Database: ']//i"); }
    public Element databaseHelpIconPopOver_clientPg(){ return driver.FindElementByXPath("//label[text()='Initial Size of Project Database: ']//a[contains(@aria-describedby,'popover')]"); }
    public Element createClientHelpIcon_clientPg(){ return driver.FindElementByXPath("//span[text()='Create Client']//i"); }
    public Element createClientHelpIconPopOver_clientPg(){ return driver.FindElementByXPath("//span[text()='Create Client']//a[contains(@aria-describedby,'popover')]"); }
    public Element dataBaseTitle(){ return driver.FindElementByXPath("//strong[text()='Database ']"); }

  
    //Annotation Layer added successfully
    public ClientsPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Entity/Entity");
        driver.waitForPageToBeReady();
      
        bc = new BaseClass(driver);
        project = new ProjectPage(driver);
       }

    public void AddNonDomainClient(String Clientnamenondomain) {
    	   this.driver.getWebDriver().get(Input.url+"Entity/Entity");
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddEntity().Visible()  ;}}),Input.wait30); 
    	getAddEntity().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEntityName().Visible()  ;}}),Input.wait30); 
    	getEntityName().SendKeys(Clientnamenondomain);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEntityshortname().Visible()  ;}}),Input.wait30); 
    	getEntityshortname().SendKeys(Clientnamenondomain);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSaveBtn().Visible()  ;}}),Input.wait30); 
    	getSaveBtn().Click();
    	
    	bc.VerifySuccessMessage("The new client was added successfully");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEntityName().Visible()  ;}}),Input.wait30); 
    	getEntityName().SendKeys(Clientnamenondomain);
    	
    	getFilterButton().waitAndClick(10);
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(getFiler_Clientname().getText());
    	
    	Assert.assertEquals(getFiler_Clientname().getText(), Clientnamenondomain);
     	    	
    }
    
     public void AddDomainClient(String Clientnamedomain,String domainid) {
		
    	   this.driver.getWebDriver().get(Input.url+"Entity/Entity");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddEntity().Visible()  ;}}),Input.wait30); 
    	getAddEntity().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEntityName().Visible()  ;}}),Input.wait30); 
    	getEntityName().SendKeys(Clientnamedomain);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEntityshortname().Visible()  ;}}),Input.wait30); 
    	getEntityshortname().SendKeys(Clientnamedomain);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			project.getSelectEntity().Visible()  ;}}),Input.wait30);     	
    	project.getSelectEntity().selectFromDropdown().selectByVisibleText("Domain");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDomainID().Visible()  ;}}), Input.wait30); 
    	getDomainID().SendKeys(domainid);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			project.getProjectDBServerDropdown().Visible()  ;}}), Input.wait30); 
    	project.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);
    	
    	getEntityServerPath().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			project.getIngestionserverpath().Visible()  ;}}), Input.wait30); 
    	project.getIngestionserverpath().selectFromDropdown().selectByIndex(0);
    	project.getIngestionserverpath().selectFromDropdown().selectByIndex(1);
    	
    	project.getProductionserverpath().waitAndClick(10);
    	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSaveBtn().Visible()  ;}}),Input.wait30); 
    	getSaveBtn().Click();
    	
    	bc.VerifySuccessMessage("The new client was added successfully");
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEntityNameFilter().Visible()  ;}}),Input.wait30); 
    	getEntityNameFilter().SendKeys(Clientnamedomain);
    	
    	getFilterButton().waitAndClick(10);
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(getFiler_Clientname().getText());
    	UtilityLog.info(getFiler_Clientname().getText());
    	
    	Assert.assertEquals(getFiler_Clientname().getText(), Clientnamedomain);
     	    	
    }
     
     /**
     * @author Jayanthi.ganesan
     * @throws InterruptedException
     * @description This method will create new client with entity type as Domain.
     */
    public void addNewClientWithDomainType() throws InterruptedException{
		 this.driver.getWebDriver().get(Input.url+ "Entity/Entity");
		 bc.waitForElement(getAddEntity());
		 getAddEntity().waitAndClick(3);
		 bc.waitForElement(getEntitytype());
		 getEntitytype().selectFromDropdown().selectByVisibleText("Domain");
	 }
    /**
     *@author Jayanthi.ganesan
     *@description This method will verifies whether Help text doesn't appear when we hover near the Initial size of Project
     *             DataBase Help icon in client page
     */
	 public void verifyHelpTextPopUpWhenHovering() throws InterruptedException{
		 
		 addNewClientWithDomainType();
		 bc.waitForElement(databaseHelpIcon_clientPg());
		 databaseHelpIcon_clientPg().ScrollTo();
		 if(databaseHelpIconPopOver_clientPg().isElementPresent()==false) {
			 bc.passedStep("Help popup is not appeared while hovering on help icon");
		 }else {
			 bc.failedStep("Help popup is appeared while hovering on help icon");
			 Assert.fail();
		 }
		 
	 }
	 /**
	     *@author Jayanthi.ganesan
	     *@description This method will verifies whether Help text is appeared or not when we click on the Initial size of Project
	     *             DataBase Help icon in client page.
	     */
	 public void verifyHelpTextPopUpWhenClicking() throws InterruptedException{
		 addNewClientWithDomainType();
		 bc.waitForElement(databaseHelpIcon_clientPg());
		 databaseHelpIcon_clientPg().Click();
		 bc.stepInfo("Help icon is clicked");
		 if(databaseHelpIconPopOver_clientPg().isElementPresent()==true) {
			 bc.passedStep("Help popup appeared when we click on help icon");
		 }else {
			 bc.failedStep("Help popup is not appeared");
			 Assert.fail();
		 }
		 dataBaseTitle().Click();
		 bc.stepInfo("Other than help icon is clicked");
		 if(databaseHelpIconPopOver_clientPg().isElementPresent()==false) {
			 bc.passedStep("Help popover is disappeared");
		 }else {
			 bc.failedStep("Help popup not disappeared");
			 Assert.fail();
		 }
		 
	 }
	     
 
 }
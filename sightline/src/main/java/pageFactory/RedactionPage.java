package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class RedactionPage {

    Driver driver;
    BaseClass bc;
  
    public Element getAllRedactionRootNode(){ return driver.FindElementById("-1_anchor"); }
    public Element getAddRedactionTag(){ return driver.FindElementById("aAddRedactionTag"); }
    public Element getRedactionTagName(){ return driver.FindElementById("txtRedactionNew"); }
    public Element getRedactionEdit(){ return driver.FindElementById("aEditRedactionTag"); }
    public Element getRedactionDelete(){ return driver.FindElementById("aDeleteRedactionTag"); }
    public Element getRedactionEditTagName(){ return driver.FindElementById("txtRedactionTagName"); }
    public Element getSaveBtn(){ return driver.FindElementById("btnSaveRedactionTag"); }
    public Element getEditSaveBtn(){ return driver.FindElementById("btnModifySecurityGroup"); }
    public Element getactionDropDown(){ return driver.FindElementByXPath("//button[@class='btn btn-defualt dropdown-toggle']"); }
    public Element getSelectredaction(String redactname){ return driver.FindElementByXPath("//a[contains(text(),'"+redactname+"')]"); }
    public Element getSecurityGrp(){ return driver.FindElementById("ddlSecurityGroupRedaction"); }
    public ElementCollection getredactiontags(){ return driver.FindElementsByXPath("//*[@id='tagsJSTree']//a"); }
    
    

    public RedactionPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
        driver.waitForPageToBeReady();
        bc = new BaseClass(driver);
       }

    public void AddRedaction(String RedactName,String usertype) 
    {
    	this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
    	if(usertype.equalsIgnoreCase("PA"))
    	{
    	SecurityGroupsPage sp = new SecurityGroupsPage(driver);
    	List<String> expvalue = sp.GetSecurityGrouplist();
    	
    	 this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
    	 
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getSecurityGrp().Visible()  ;}}),Input.wait30); 
    	List<WebElement> sgnames = getSecurityGrp().selectFromDropdown().getOptions();
    	List<String> allsg = new ArrayList<String>();
    	
    	for(int i=1;i<sgnames.size();i++)
    	{
  		  System.out.println(allsg.add(sgnames.get(i).getText()));
  		  UtilityLog.info(allsg.add(sgnames.get(i).getText()));
  		  System.out.println(allsg);
      	}
     	
    	Assert.assertEquals(allsg, expvalue);
    	}
    	else if(usertype.equalsIgnoreCase("RMU")){
    		System.out.println("User is not PA");
    		UtilityLog.info("User is not PA");
    	}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAllRedactionRootNode().Visible()  ;}}),Input.wait30); 
    	getAllRedactionRootNode().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getactionDropDown().Visible()  ;}}),Input.wait30); 
    	getactionDropDown().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddRedactionTag().Visible()  ;}}),Input.wait30); 
    	getAddRedactionTag().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getRedactionTagName().Visible()  ;}}),Input.wait30); 
    	getRedactionTagName().SendKeys(RedactName);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSaveBtn().Visible()  ;}}),Input.wait30); 
    	getSaveBtn().Click();
    	
    	bc.VerifySuccessMessage("Redaction label added successfully");
    	bc.CloseSuccessMsgpopup();
     }
  
     public void EditRedaction(String redactName) {
		
    	 this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectredaction(redactName).Visible()  ;}}),Input.wait30); 
    	getSelectredaction(redactName).waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getactionDropDown().Visible()  ;}}),Input.wait30); 
    	getactionDropDown().Click();
    	
    	getRedactionEdit().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getRedactionEditTagName().Visible()  ;}}),Input.wait30); 
    	getRedactionEditTagName().SendKeys(redactName+Utility.dynamicNameAppender());
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEditSaveBtn().Visible()  ;}}),Input.wait30); 
    	getEditSaveBtn().Click();
    	
    	bc.VerifySuccessMessage("Redaction label updated successfully");
    	bc.CloseSuccessMsgpopup();
    	
     } 
     
     public void DeleteRedaction(String redactName) {
 		
    	 this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getSelectredaction(redactName).Visible()  ;}}),Input.wait30); 
     	getSelectredaction(redactName).waitAndClick(10);
     	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getactionDropDown().Visible()  ;}}),Input.wait30); 
     	getactionDropDown().Click();
     	
     	getRedactionDelete().waitAndClick(10);
     	
     	bc.getNOBtn().waitAndClick(10);
     	
     	getSelectredaction(redactName).WaitUntilPresent();
     	Assert.assertTrue(getSelectredaction(redactName).Displayed());
     	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getactionDropDown().Visible()  ;}}),Input.wait30); 
     	getactionDropDown().waitAndClick(10);
     	
     	getRedactionDelete().waitAndClick(10);
     	     	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			bc.getYesBtn().Visible()  ;}}),Input.wait30); 
     	bc.getYesBtn().waitAndClick(10);
     	
     	bc.VerifySuccessMessage("Redaction label deleted successfully");
     	bc.CloseSuccessMsgpopup();
     	
     	
     	 try{
     		getSelectredaction(redactName).Displayed();
		           Assert.assertFalse(1==0);
		     }catch (org.openqa.selenium.NoSuchElementException e) {
		               System.out.println(" 'Redaction' is not displayed");
		 }
     }
 
     
     public void findredaction()
     {
    	 String expvalues[] = {"Default Redaction Tag","Redacted Privacy","Redacted Privilege","All Redaction Tags"};
    	 List<WebElement> allvalues = getredactiontags().FindWebElements();
    	 System.out.println(allvalues.size());
    	 List<String> value = new ArrayList<String>();
         for(int j=0;j<allvalues.size();j++)
         {
        	 System.out.println(allvalues.get(j).getText());
       	 System.out.println(value.add(allvalues.get(j).getText()));
        }
    	 
         Assert.assertSame(value, expvalues);
 		
    	 
    	 
    	 
    	 
     }
 }
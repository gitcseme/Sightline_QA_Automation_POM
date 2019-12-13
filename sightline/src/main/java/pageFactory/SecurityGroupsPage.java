package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class SecurityGroupsPage {

    Driver driver;
    BaseClass bc;
    
    public Element getSecurityGroupCreateButton(){ return driver.FindElementById("btnNewSecurityGroup"); }
    public Element getSecurityGroupName(){ return driver.FindElementById("txtSecurityGroupName"); }
    public Element getSecurityGroupList(){ return driver.FindElementById("ddlSecurityGroupsList"); }
    public Element getSecurityGroupSaveButton(){ return driver.FindElementById("btnSaveSecurityGroup"); }
       
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
  
  
    //Annotation Layer added successfully
    public SecurityGroupsPage(Driver driver){

        this.driver = driver;
        bc = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+"SecurityGroups/SecurityGroups");
        driver.waitForPageToBeReady();
      
       }

    public void AddSecurityGroup(String securitygroupname) {
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSecurityGroupCreateButton().Visible()  ;}}), Input.wait60); 
    	getSecurityGroupCreateButton().waitAndClick(5);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSecurityGroupName().Visible()  ;}}), Input.wait30); 
    	getSecurityGroupName().SendKeys(securitygroupname);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSecurityGroupSaveButton().Visible()  ;}}), Input.wait30); 
    	getSecurityGroupSaveButton().Click();
    	
    	bc.VerifySuccessMessage("Security group added successfully");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSecurityGroupList().Visible()  ;}}), Input.wait30); 
    	getSecurityGroupList().selectFromDropdown().selectByVisibleText(securitygroupname);
     }
    
       public List<String> GetSecurityGrouplist() {
    	
    	List<WebElement> allvalues = getSecurityGroupList().selectFromDropdown().getOptions();
			
    	List<String> all = new ArrayList<String>();
		for(int j=0;j<allvalues.size();j++)
		   {
			  System.out.println(all.add(allvalues.get(j).getText()));
			  System.out.println(all);
		   }
		return all;
		
       }
		
		
 }
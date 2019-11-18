package pageFactory;

import java.util.concurrent.Callable;


import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class SecurityGroupsPage {

    Driver driver;
    
    public Element getSecurityGroupCreateButton(){ return driver.FindElementById("btnNewSecurityGroup"); }
    public Element getSecurityGroupName(){ return driver.FindElementById("txtSecurityGroupName"); }
    public Element getSecurityGroupList(){ return driver.FindElementById("ddlSecurityGroupsList"); }
    public Element getSecurityGroupSaveButton(){ return driver.FindElementById("btnSaveSecurityGroup"); }
       
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
 
  
    //Annotation Layer added successfully
    public SecurityGroupsPage(Driver driver){

        this.driver = driver;
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
    	
    	successMsgConfirmation();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSecurityGroupList().Visible()  ;}}), Input.wait30); 
    	getSecurityGroupList().selectFromDropdown().selectByVisibleText(securitygroupname);
    	
    	
    	
    }
    
    public void successMsgConfirmation() {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSuccessMsgHeader().Visible()  ;}}), Input.wait60); 
    	Assert.assertEquals("Success !", getSuccessMsgHeader().getText().toString());
    	Assert.assertEquals("Security group added successfully", getSuccessMsg().getText().toString());
	}
   
 
 }
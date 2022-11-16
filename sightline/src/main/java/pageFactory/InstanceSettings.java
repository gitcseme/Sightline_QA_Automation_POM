package pageFactory;

import java.util.concurrent.Callable;

import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class InstanceSettings {

    Driver driver;
   
    public Element getEnable2FA(){ return driver.FindElementById("enable2faDropDown"); }
    public Element getLevel(){ return driver.FindElementById("Level2faDropDown"); }
    public Element getPasscodeExpiry(){ return driver.FindElementById("txtPasscodeExpiry"); }
    public Element getAuthPassExpiry(){ return driver.FindElementById("txtAuthTokenExpiry"); }
    public Element getSaveBtn(){ return driver.FindElementById("btn_Save"); }
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
 
    //Annotation Layer added successfully
    public InstanceSettings(Driver driver){

        this.driver = driver;
//        this.driver.getWebDriver().get(Input.url+ "Instance/InstanceSettings#");
    }

    public void enable2FA() {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEnable2FA().Exists() ;}}), Input.wait30);
    	
    	getEnable2FA().selectFromDropdown().selectByIndex(1);
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	getLevel().selectFromDropdown().selectByVisibleText("Passcode Verification for Each Login");
    	getPasscodeExpiry().SendKeys("20");
    	getAuthPassExpiry().SendKeys("30");
    	getSaveBtn().Click();
    	successMsgConfirmation();
    	 
	}
    
    public void disable2FA() {
    	this.driver.getWebDriver().get(Input.url+ "Instance/InstanceSettings#");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEnable2FA().Exists() ;}}), Input.wait30);
    	
    	getEnable2FA().selectFromDropdown().selectByIndex(0);;
    	//getPasscodeExpiry().SendKeys("20");
    	//getAuthPassExpiry().SendKeys("30");
    	getSaveBtn().Click();
    	successMsgConfirmation();
    	 
	}
    
    public void successMsgConfirmation() {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSuccessMsgHeader().Visible()  ;}}), Input.wait30); 
    	Assert.assertEquals("Success !", getSuccessMsgHeader().getText().toString());
    	Assert.assertEquals("Instance Settings Saved Successfully", getSuccessMsg().getText().toString());
	}
   
 
 }
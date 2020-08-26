package pageFactory;

import java.util.concurrent.Callable;

import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class KeywordPage {

    Driver driver;
    BaseClass base;
   
    public Element getNewKeywordButton(){ return driver.FindElementById("btnAddKeyword"); }
    public Element getKeywordName(){ return driver.FindElementById("KeywordName"); }
    public Element getDescription(){ return driver.FindElementById("Description"); }
    public Element getSaveBtn(){ return driver.FindElementById("btnSaveKeywords"); }
    public Element getKeywords(){ return driver.FindElementById("Keywords");  }
    public Element getYesButton(){ return driver.FindElementById("bot1-Msg1");  }
    
    
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
 
  
    //Annotation Layer added successfully
    public KeywordPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Keywords/Keywords");
        driver.waitForPageToBeReady();
        base= new BaseClass(driver);
       }

    public void AddKeyword(String keywordname,String keywords) {
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNewKeywordButton().Visible()  ;}}), Input.wait60); 
    	getNewKeywordButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getKeywordName().Visible()  ;}}), Input.wait30); 
    	getKeywordName().SendKeys(keywordname);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDescription().Visible()  ;}}), Input.wait30); 
    	getDescription().SendKeys(keywordname);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getKeywords().Visible()  ;}}), Input.wait30); 
    	getKeywords().SendKeys(keywords);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSaveBtn().Visible()  ;}}), Input.wait30); 
    	getSaveBtn().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getYesButton().Visible()  ;}}), Input.wait30); 
    	getYesButton().Click();
    	
    	base.VerifySuccessMessage("Keyword Highlighting Group added successfully");
    	
        base.CloseSuccessMsgpopup();
      }
    
 
 }
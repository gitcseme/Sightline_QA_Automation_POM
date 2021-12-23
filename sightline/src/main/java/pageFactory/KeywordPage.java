package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.Callable;

import org.testng.Reporter;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
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
    public Element getSelectColor(){ return driver.FindElementById("ddlKeywordColors");  }
    public ElementCollection getKeywordsList(){ return driver.FindElementsByXPath("//table[@id=KeywordsDatatable]/tbody/tr/td[4]");}
    
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
    
    public Element getDeleteButton(String keywordName){ return driver.FindElementByXPath("//td[text()='"+keywordName+"']/parent::tr//a[text()='Delete']"); }
 
  
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
    	Reporter.log("Keyword '"+keywordname+" with keywords "+keywords+"' added successfully",true);
        base.CloseSuccessMsgpopup();
      }
    public void getIntoFullScreen() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_F11);
		r.keyRelease(KeyEvent.VK_F11);
	}

	public void getExitFullScreen() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_F11);
		r.keyRelease(KeyEvent.VK_F11);
	}
    public void addKeywordWithColor(String keywordname, String color) throws AWTException {
    	base.waitForElement(getNewKeywordButton());
    	getNewKeywordButton().waitAndClick(5);
    	base.waitForElement(getKeywordName());
    	getKeywordName().SendKeys(keywordname);
    	base.waitForElement(getDescription());
    	getDescription().SendKeys(keywordname);
    	base.waitForElement(getKeywords());
    	getKeywords().SendKeys(keywordname);
    	getSelectColor().selectFromDropdown().selectByVisibleText(color);
    	getIntoFullScreen();
    	base.waitForElement(getSaveBtn());
    	getSaveBtn().waitAndClick(5);
    	base.waitForElement(getYesButton());
    	getYesButton().waitAndClick(5);
    	getExitFullScreen();
    	base.VerifySuccessMessage("Keyword Highlighting Group added successfully");
        base.CloseSuccessMsgpopup();    	
    }
    
    public void deleteKeyword(String keyword) {
    	base.waitForElement(getDeleteButton(keyword));
    	getDeleteButton(keyword).waitAndClick(5);
    	base.waitForElement(getYesButton());
    	getYesButton().waitAndClick(5);
    	base.VerifySuccessMessage("Keyword Highlighting Group successfully deleted");
        base.CloseSuccessMsgpopup();    	
    	
    }
   
 
 }
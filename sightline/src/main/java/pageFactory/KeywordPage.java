package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
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
 
    //Added by Gopinath - 03/01/2022
    public Element getKeyword(int rowNum){ return driver.FindElementByXPath("//table[@id='KeywordsDatatable']//tbody//tr["+rowNum+"]//td[4]"); }
    public ElementCollection totalRows(){ return driver.FindElementsByXPath("//table[@id='KeywordsDatatable']//tbody//tr"); }
    public Element getNextButton(){ return driver.FindElementByXPath("//a[text()='Next']/parent::li"); }
    public Element getNextButtonEle(){ return driver.FindElementByXPath("//a[text()='Next']"); }
    
  
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
    /**
	 * @author Gopinath
	 * Description: Method for navigating to keyword page.
	 */
    public void navigateToKeywordPage(){
    	try {
    		driver.getWebDriver().get(Input.url+"Keywords/Keywords");
    	}catch(Exception e) {
    		e.printStackTrace();
    		base.failedStep("Navigaing to keyword page is failed"+e.getLocalizedMessage());
    	}
    }
    
    /**
   	 * @author Gopinath
   	 * @Description: Method for getting all keywords
   	 * @return keywords : keywords list of String values that total keywords in keywords table.
   	 */
       public List<String> getAllKeywords(){
    	   List<String> keywords = new ArrayList<String>();
       	try {
       		driver.getWebDriver().get(Input.url+"Keywords/Keywords");
       		base.waitTime(2);
       		getKeyword(1).isElementAvailable(15);
       		int rowCount = totalRows().FindWebElements().size();
       		for(int i=0;i<rowCount;i++) {
       			keywords.add(getKeyword(i+1).getText().trim());
       			String getNextButtonAtt = getNextButton().GetAttribute("class");
       			if((i==rowCount-1)&&!(getNextButtonAtt.contains("disabled"))) {
       				driver.scrollingToBottomofAPage();
       				driver.waitForPageToBeReady();
       				getNextButtonEle().isElementAvailable(8);
       				getNextButtonEle().Click();
       				driver.waitForPageToBeReady();
       				rowCount = totalRows().FindWebElements().size();
       				i=-1;
       			}
       		}
       		System.out.println("Keywords are "+keywords);
       		base.passedStep("Keywords are "+keywords);
       	}catch(Exception e) {
       		e.printStackTrace();
       		base.failedStep("Execption occured while verifying all keywords"+e.getLocalizedMessage());
       	}
       	return keywords;
       }
 
       /**
      	 * @author Gopinath
      	 * @Description: Method for getting all keywords
      	 * @param keyword : keyword is String value that name of keyword.
      	 */
       public void deleteKeywordByName(String keyword) {
    	  	try {
           		driver.getWebDriver().get(Input.url+"Keywords/Keywords");
           		base.waitTime(2);
           		getKeyword(1).isElementAvailable(15);
           		int rowCount = totalRows().FindWebElements().size();
           		for(int i=0;i<rowCount;i++) {
           			String val =getKeyword(i+1).getText();
           			if(getKeyword(i+1).getText().trim().equalsIgnoreCase(keyword)) {
           				base.waitForElement(getDeleteButton(keyword));
           		    	getDeleteButton(keyword).waitAndClick(5);
           		    	base.waitForElement(getYesButton());
           		    	getYesButton().waitAndClick(5);
           		    	base.VerifySuccessMessage("Keyword Highlighting Group successfully deleted");
           		        base.CloseSuccessMsgpopup(); 
           		        break;
           			}
           			String getNextButtonAtt = getNextButton().GetAttribute("class");
           			if((i==rowCount-1)&&!(getNextButtonAtt.contains("disabled"))) {
           				driver.scrollingToBottomofAPage();
           				driver.waitForPageToBeReady();
           				getNextButtonEle().isElementAvailable(8);
           				getNextButtonEle().Click();
           				driver.waitForPageToBeReady();
           				rowCount = totalRows().FindWebElements().size();
           				i=-1;
           			}
           		}
    		}catch(Exception e) {
           		e.printStackTrace();
           		base.failedStep("Execption occured while deleting keyword"+e.getLocalizedMessage());
           	}
       	
       }
 }
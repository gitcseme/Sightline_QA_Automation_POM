package pageFactory;

import java.util.concurrent.Callable;

import org.testng.Reporter;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class AnnotationLayer {

    Driver driver;
    BaseClass base;
   
    public Element getAddAnnotationLayerBtn(){ return driver.FindElementById("btnAddAnnotation"); }
    public Element getAnnotationName(){ return driver.FindElementById("AnnotationName"); }
    public Element getDescription(){ return driver.FindElementById("AnnotationDescription"); }
    public Element getSaveBtn(){ return driver.FindElementById("btnAnnotationSave"); }
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getDeleteAnnotationLayer(){ return driver.FindElementByXPath("//a[contains(text(),'Delete')]"); }
    public Element getYes(){ return driver.FindElementByXPath("//button[@id='bot1-Msg1']"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
  
  
    //Annotation Layer added successfully
    public AnnotationLayer(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+ "Annotations/Annotations");
        base = new BaseClass(driver);
    }

    public void AddAnnotation(String name) {
		
 
    	try{
    		this.driver.getWebDriver().get(Input.url+ "Annotations/Annotations");
    	getAddAnnotationLayerBtn().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAnnotationName().Visible()  ;}}), Input.wait30); 
    	getAnnotationName().SendKeys(name);
    	getDescription().SendKeys(name);
    	getSaveBtn().Click();
    	base.VerifySuccessMessage("Annotation Layer added successfully");
    	}
    	catch (Exception e)
    	{
    	Reporter.log("Annotation layer "+name+" added!",true);
    	UtilityLog.info("Annotation layer already exist");
    	}
    	 
         base.CloseSuccessMsgpopup();
 
	}
    
   }
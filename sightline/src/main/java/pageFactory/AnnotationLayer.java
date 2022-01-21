package pageFactory;

import java.awt.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
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
    public Element getDeleteAnnotation(String annotation){ return driver.FindElementByXPath("//td[text()='"+annotation+"']/following-sibling::td//a[text()='Delete']"); }
    public Element getNextButton(){ return driver.FindElementById("AnnotationsDatatable_next"); }
    public ElementCollection getPages(){ return driver.FindElementsByXPath("//a[text()='Next']//..//preceding-sibling::li"); }
    
  
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
    /**
   	 * @author Gopinath
   	 * @Description : Method for deleting annotation layer.
   	 * @param name : name is String value that name of annotation layer.
   	 */
       public void deleteAnnotation(String name) {
       	try{
       		driver.getWebDriver().get(Input.url+ "Annotations/Annotations");
       		driver.waitForPageToBeReady();
       		getDeleteAnnotation(name).isElementAvailable(15);
       		getDeleteAnnotation(name).Click();
       		getYes().isElementAvailable(15);
       		getYes().waitAndClick(5);
       		base.VerifySuccessMessage("Annotation Layer deleted successfully");
       		base.CloseSuccessMsgpopup();
       	}catch (Exception e){
   	    	e.printStackTrace();
   	    	base.failedStep("Exception occured while deleting annotation layer "+e.getLocalizedMessage());
       	}
   	}
       
       /**
      	 * @author Gopinath
      	 * @Description : Method for deleting annotation layer by pagination.
      	 * @param name : name is String value that name of annotation layer.
      	 */
          public void deleteAnnotationByPagination(String name) {
          	try{
          		
          		driver.getWebDriver().get(Input.url+ "Annotations/Annotations");
          		driver.waitForPageToBeReady();
          		java.util.List<WebElement> pages = getPages().FindWebElements();
          		for(int i=0;i<pages.size()-1;i++) {
	          		if(getDeleteAnnotation(name).isDisplayed()) {
	          			getDeleteAnnotation(name).Click();
	          		}
	          		driver.scrollingToBottomofAPage();
	          		String status = getNextButton().GetAttribute("class");
	          		if(!status.contains("disabled")) {
	          			getNextButton().Click();
	          		}else {
	          			break;
	          		}
          		}
          		getYes().isElementAvailable(15);
          		getYes().waitAndClick(5);
          		base.VerifySuccessMessage("Annotation Layer deleted successfully");
          		base.CloseSuccessMsgpopup();
          	}catch (Exception e){
      	    	e.printStackTrace();
      	    	base.failedStep("Exception occured while deleting annotation layer "+e.getLocalizedMessage());
          	}
      	}
    
   }
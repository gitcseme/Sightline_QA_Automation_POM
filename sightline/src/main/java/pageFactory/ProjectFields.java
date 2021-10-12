package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;

public class ProjectFields {
	
	 Driver driver;
	    Element element;
	    BaseClass bc;
	    String fieldname;
	    
	    public Element addcommentfield(){ return driver.FindElementByXPath("//a[@id='btnAddProjectField']"); }
	    public Element PresentationFieldLabel(){ return driver.FindElementById("FieldLabel"); }  
	    public Element fieldClassification(){ return driver.FindElementById("ddlFieldClassification"); }
		public Element enterfieldname() {return driver.FindElementById("FieldName");}
		public Element FieldType() {return driver.FindElementById("ddlProjectFieldGroupCode");}
		public Element ProjectFieldSave() {return driver.FindElementById("btnProjectFieldAddSave");}
		public Element selectIsSearchable(){ return driver.FindElementByXPath("//div[@class='widget-body']//div[1]/div[7]/div[2]/label[1]/i[1]"); }
		public Element filterfields() { return driver.FindElementByXPath("//input[@id='txtsearchField']"); }
		public Element Applyfilter() { return driver.FindElementByXPath("//a[@id='btnAppyFilter']"); }
		 public Element getfieldName(String fieldname){ return driver.FindElementByXPath("//td[contains(text(),'"+fieldname+"')]"); }
		
		
		
	      
	    
	    public ProjectFields(Driver driver){

	        this.driver = driver;
	        bc = new BaseClass(driver);
	        
	        this.driver.getWebDriver().get(Input.url+ "ProjectFields/ProjectFieldsList");
	        driver.waitForPageToBeReady();
	        //This initElements method will create all WebElements
	        //assertion = new SoftAssert();
	        //search = new SessionSearch(driver);
	       // docview = new DocViewPage(driver);
	        //fieldname =Input.Projectfieldname;
	        

	    }
	    
	    public void CreateProjectField(String fieldname) {
	    	
	    	
	    	driver.getWebDriver().get(Input.url+ "ProjectFields/ProjectFieldsList");
		    driver.waitForPageToBeReady();
		    addcommentfield().Click();
		    //Actions ac = new Actions(driver.getWebDriver());
		    //ac.moveToElement(fieldname().getWebElement()).perform();
		    //fieldname().VisibilityOfElementExplicitWait(fieldname(), 60);
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		enterfieldname().Visible()  ;}}), Input.wait30); 
		    enterfieldname().SendKeys(fieldname);
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		PresentationFieldLabel().Visible()  ;}}), Input.wait30); 
		    PresentationFieldLabel().SendKeys(fieldname);
		    fieldClassification().selectFromDropdown().selectByVisibleText("Custom");
		    FieldType().selectFromDropdown().selectByVisibleText("DATETIME");
		    selectIsSearchable().Click();
		    ProjectFieldSave().Click();
	    }
	    
	   public String VerifyCustomfield(String fieldname) throws InterruptedException {
		   driver.getWebDriver().get(Input.url+ "ProjectFields/ProjectFieldsList");
		   driver.waitForPageToBeReady();
		   filterfields().SendKeys(fieldname);
		   Applyfilter().waitAndClick(10);
		   
		   try {
		   String Fieldtext = getfieldName(fieldname).getText();
		  
		   System.out.println(Fieldtext+" Custom field is present");
		   return Fieldtext;
		   }
		   
		  
		   
		   catch(Exception e) {
			   
			  
			   return null;
			  
		   }
		   
	   
	    
	   }
}


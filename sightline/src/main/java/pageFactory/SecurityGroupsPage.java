package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class SecurityGroupsPage {

    Driver driver;
    BaseClass bc;
    
    public Element getSecurityGroupCreateButton(){ return driver.FindElementByXPath("//button[text()='Create']");}
    public Element getSecurityGroupName(){ return driver.FindElementById("txtSecurityGroupName"); }
    public Element getSecurityGroupList(){ return driver.FindElementById("ddlSecurityGroupsList"); }
    public Element getSecurityGroupSaveButton(){ return driver.FindElementById("btnSaveSecurityGroup"); }
    public Element getSG_AnnLayerbutton(){ return driver.FindElementByXPath("//*[@id='myTab1']//a[contains(text(),'Annotation Layers')]"); }
    public Element getSG_AddAnnLayer(){ return driver.FindElementByXPath("//*[@id=\"annotationJSTree\"]/ul/li/ul/li[1]"); }
    public Element getSG_AddAnnLayer_Right(){ return driver.FindElementByXPath("//*[@onclick='AnnotationRightShift();']"); }
    public Element getSG_AnnSaveButton(){ return driver.FindElementById("btnSaveAccessControls"); }
        
  
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
    	
    	Actions action = new Actions((WebDriver) driver);
    	action.moveToElement((WebElement) getSecurityGroupCreateButton()).click().perform();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSecurityGroupName().Visible()  ;}}), Input.wait30); 
    	getSecurityGroupName().SendKeys(securitygroupname);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSecurityGroupSaveButton().Visible()  ;}}), Input.wait30); 
    	getSecurityGroupSaveButton().Click();
    	
    	bc.VerifySuccessMessage("Security group added successfully");
    	System.out.println("Security Group created :-"+ securitygroupname);
    	
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getSecurityGroupList().Visible() ;}}), Input.wait30);
		 * getSecurityGroupList().selectFromDropdown().selectByVisibleText(
		 * securitygroupname);
		 */
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
       
       public void addlayertosg()
       {
    	 
    	   this.driver.getWebDriver().get(Input.url+"SecurityGroups/SecurityGroups");
    	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			   getSG_AnnLayerbutton().Visible()  ;}}), Input.wait30); 
    	   getSG_AnnLayerbutton().waitAndClick(5);
    	   
    	   getSG_AddAnnLayer().waitAndClick(15);
    	   
    	   getSG_AddAnnLayer_Right().waitAndClick(15);
    	   try {
    		   bc.VerifyWarningMessage("Cannot add more than one security group. "
    		   		+ "A security group can have only one annotation layer at a time.");
    		 
    	     getSG_AnnSaveButton().waitAndClick(15);
    	     bc.VerifySuccessMessage("Your selections were saved successfully");
    	     bc.CloseSuccessMsgpopup();
    	   }
    	   catch(Exception e)
    	   {
    		   System.out.println("Annotation layer already added");
    	   }
         	
       }
		
		
 }
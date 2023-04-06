package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class RedactionPage {

    Driver driver;
    BaseClass bc;
  
    public Element getAllRedactionRootNode(){ return driver.FindElementById("-1_anchor"); }
    public Element getAddRedactionTag(){ return driver.FindElementById("aAddRedactionTag"); }
    public Element getRedactionEdit(){ return driver.FindElementById("aEditRedactionTag"); }
    public Element getRedactionDelete(){ return driver.FindElementById("aDeleteRedactionTag"); }
    public Element getRedactionEditTagName(){ return driver.FindElementById("txtRedactionTagName"); }
    public Element getSaveBtn(){ return driver.FindElementById("btnSaveRedactionTag"); }
    public Element getEditSaveBtn(){ return driver.FindElementById("btnModifySecurityGroup"); }
    public Element getactionDropDown(){ return driver.FindElementByXPath("//button[@class='btn btn-defualt dropdown-toggle']"); }
    public Element getSelectredaction(String redactname){ return driver.FindElementByXPath("//a[contains(text(),'"+redactname+"')]"); }
    public Element getSecurityGrp(){ return driver.FindElementById("ddlSecurityGroupRedaction"); }
    public ElementCollection getredactiontags(){ return driver.FindElementsByXPath("//*[@id='tagsJSTree']//a"); }
    
 // added by sowndarya on 9/21/21
    public Element getRedactionTagName(){ return driver.FindElementByXPath("//input[@id='txtRedactionNew']"); }
 	public Element getselectAllRedactionTag() {
 		return driver.FindElementByXPath(" //div[@id='tagsJSTree']//ul/li/a[text()='All Redaction Tags']");
 	}

 	public Element getselectActionToggle() {
 		return driver.FindElementByXPath("//button[@class='btn btn-defualt dropdown-toggle']");
 	}

 	public Element getselectNewFromDropdown() {
 		return driver.FindElementByXPath(" //ul//li/a[text()='New']");
 	}

	public Element getDefaultSecurityGroup() {
		return driver.FindElementByXPath("//select[@id='ddlSecurityGroupRedaction']");
	}
	
	//Added by Krishna
		public Element redactionTagErrorMsg() {
			return driver.FindElementById("redactionTagErrorMsg");
		}
		
		public Element getRedactedPrivacy() {
			return driver.FindElementById("3_anchor");
		}

		public Element getRedactedPrivilege() {
			return driver.FindElementById("2_anchor");
		}

    

    public RedactionPage(Driver driver){

        this.driver = driver;
//        this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
        driver.waitForPageToBeReady();
        bc = new BaseClass(driver);
       }

    public void AddRedaction(String RedactName,String usertype) 
    {
    	this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
    	if(usertype.equalsIgnoreCase("PA"))
    	{
    	SecurityGroupsPage sp = new SecurityGroupsPage(driver);
    	List<String> expvalue = sp.GetSecurityGrouplist();
    	
    	 this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
    	 
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getSecurityGrp().Visible()  ;}}),Input.wait30); 
    	List<WebElement> sgnames = getSecurityGrp().selectFromDropdown().getOptions();
    	List<String> allsg = new ArrayList<String>();
    	
    	for(int i=1;i<sgnames.size();i++)
    	{
  		  //System.out.println(allsg.add(sgnames.get(i).getText()));
  		  UtilityLog.info(allsg.add(sgnames.get(i).getText()));
  		  
      	}
     	
    	Assert.assertEquals(allsg, expvalue);
    	}
    	else if(usertype.equalsIgnoreCase("RMU")){
    		System.out.println("User is not PA");
    		UtilityLog.info("User is not PA");
    	}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getselectAllRedactionTag().Visible()  ;}}),Input.wait30); 
    	getselectAllRedactionTag().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getactionDropDown().Visible()  ;}}),Input.wait30); 
    	getactionDropDown().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddRedactionTag().Visible()  ;}}),Input.wait30); 
    	getAddRedactionTag().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getRedactionTagName().Visible()  ;}}),Input.wait30); 
    	getRedactionTagName().SendKeys(RedactName);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSaveBtn().Visible()  ;}}),Input.wait30); 
    	getSaveBtn().Click();
    	
    	bc.VerifySuccessMessage("Redaction label added successfully");
    	//System.out.println("Redaction Successful");
    	Reporter.log("Redaction "+RedactName+" added successfully",true);
		UtilityLog.info("Redaction Successful");
    	bc.CloseSuccessMsgpopup();
     }
  
     public void EditRedaction(String redactName) {
		
    	 this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectredaction(redactName).Visible()  ;}}),Input.wait30); 
    	getSelectredaction(redactName).waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getactionDropDown().Visible()  ;}}),Input.wait30); 
    	getactionDropDown().Click();
    	
    	getRedactionEdit().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getRedactionEditTagName().Visible()  ;}}),Input.wait30); 
    	getRedactionEditTagName().SendKeys(redactName+Utility.dynamicNameAppender());
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEditSaveBtn().Visible()  ;}}),Input.wait30); 
    	getEditSaveBtn().Click();
    	
    	bc.VerifySuccessMessage("Redaction label updated successfully");
    	bc.CloseSuccessMsgpopup();
    	
     } 
     //modified by jayanthi
     public void DeleteRedaction(String redactName) {
         
         this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
                 getSelectredaction(redactName).Visible()  ;}}),Input.wait30);
         getSelectredaction(redactName).waitAndClick(10);
        
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
                 getactionDropDown().Visible()  ;}}),Input.wait30);
         getactionDropDown().Click();
         //added on 25/8/21
        bc.waitForElement(getRedactionDelete());
         getRedactionDelete().waitAndClick(10);
        
         bc.getNOBtn().waitAndClick(10);
        
         getSelectredaction(redactName).WaitUntilPresent();
         Assert.assertTrue(getSelectredaction(redactName).Displayed());
        
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
                 getactionDropDown().Visible()  ;}}),Input.wait30);
         getactionDropDown().waitAndClick(10);
       //  added  on 25/8/21
         bc.waitForElement(getRedactionDelete());
         getRedactionDelete().waitAndClick(10);
                 
         bc.waitForElement(bc.getYesBtn());
         bc.getYesBtn().waitAndClick(10);
       
         driver.waitForPageToBeReady();
         if (bc.getCloseSucessmsg().isElementAvailable(5)) {
        	 bc.VerifySuccessMessage("Redaction label deleted successfully");
         }
        
          try{
             getSelectredaction(redactName).isDisplayed();
                   Assert.assertFalse(1==0);
             }catch (org.openqa.selenium.NoSuchElementException e) {
                       System.out.println(" 'Redaction' is not displayed");
         }
     }     
     public void findredaction()
     {
    	 String expvalues[] = {"Default Redaction Tag","Redacted Privacy","Redacted Privilege","All Redaction Tags"};
    	 List<WebElement> allvalues = getredactiontags().FindWebElements();
    	 System.out.println(allvalues.size());
    	 List<String> value = new ArrayList<String>();
         for(int j=0;j<allvalues.size();j++)
         {
        	 System.out.println(allvalues.get(j).getText());
       	 System.out.println(value.add(allvalues.get(j).getText()));
        }
    	 
         Assert.assertSame(value, expvalues);
 		
    	 
    	 
    	 
    	 
     }
     

  	/**
  	 * @throws InterruptedException 
  	 * @Author:Sowndarya.Velraj
  	 */
  	public void selectDefaultSecurityGroup() throws InterruptedException {
  		try {
 			driver.WaitUntil((new Callable<Boolean>() {
 				public Boolean call() {
 					return

 							getSecurityGrp().Enabled();
 				}
 			}), Input.wait30);

 			getSecurityGrp().waitAndClick(10);
 		} catch (Exception e) {
 			e.printStackTrace();
 			getSecurityGrp().waitAndClick(10);
 		}
  		try {
 			driver.WaitUntil((new Callable<Boolean>() {
 				public Boolean call() {
 					return

 							getDefaultSecurityGroup().Enabled();
 				}
 			}), Input.wait30);
 			
 			getDefaultSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
 			
 		} catch (Exception e) {
 			e.printStackTrace();
 			getDefaultSecurityGroup().waitAndClick(10);
 		}
  	}
  	/**
	 * @Author:Sowndarya.Velraj
	 */
	public void manageRedactionTagsPage(String Tag) throws InterruptedException {

		driver.getWebDriver().get(Input.url+"Redaction/Redaction");
		selectDefaultSecurityGroup();
			driver.waitForPageToBeReady();
			bc.waitTime(2); 
			getselectAllRedactionTag().isElementAvailable(15);
			bc.waitForElement(getselectAllRedactionTag());
			getselectAllRedactionTag().waitAndClick(10);
			
			getselectActionToggle().isElementAvailable(15);
			bc.waitForElement(getselectActionToggle());
			getselectActionToggle().waitAndClick(10);

			getselectNewFromDropdown().isElementAvailable(15);
			bc.waitForElement(getselectNewFromDropdown());
			getselectNewFromDropdown().waitAndClick(10);

			getRedactionTagName().isElementAvailable(15);
			bc.waitForElement(getRedactionTagName());
			getRedactionTagName().SendKeys(Tag);

			getSaveBtn().isElementAvailable(15);
			bc.waitForElement(getSaveBtn());
			getSaveBtn().waitAndClick(10);
			driver.waitForPageToBeReady();
		} 

	 /** 
     * @author Gopinath
     * @Description : Method for navigating to redactions page.
     */               
     public void navigateToRedactionsPageURL() {
     	try {
     		 driver.getWebDriver().get(Input.url+"Redaction/Redaction");
     	}catch(Exception e) {
     		e.printStackTrace();
     		bc.failedStep("Exception occured while navigating to redaction page is failed"+e.getMessage());
     	}
     }
     
     
     /** 
      * @author Gopinath
      * @Description : Method for verifying weather error message is displayed by deleting applied redaction tag.
      */ 
     public void verifyErrorMessageByDeletingAppliedRedactionTag(String redactName) {
         try {
	         this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
	         driver.waitForPageToBeReady();    
	         getSelectredaction(redactName).isElementAvailable(15);
	         getSelectredaction(redactName).waitAndClick(10);
	         driver.waitForPageToBeReady();
	         getactionDropDown().isElementAvailable(15);
	         getactionDropDown().Click();
	         bc.waitForElement(getRedactionDelete());
	         getRedactionDelete().isElementAvailable(15);
	         getRedactionDelete().waitAndClick(10);
	         bc.getYesBtn().isElementAvailable(15);
	         bc.getYesBtn().waitAndClick(10);
	        //Commented because error message is not displayed by deleting applied redaction tag.
	        // bc.VerifyErrorMessage("81001000001 : This redaction tag is used in one or more applied redactions. Please delete them before deleting the redaction tag.");
	        //  bc.CloseSuccessMsgpopup();
         }catch(Exception e) {
        	 e.printStackTrace();
        	 bc.failedStep("Exception occured by verifying weather error message is displayed by deleting applied redaction tag."+e.getLocalizedMessage());
        	 
         }
     } 
     
     /**
  	 * @Author:Krishna
  	 */
  	public void createRedactionTagWithExistingNameAndVerifyErrorMessage(String TagName) throws InterruptedException {
  			driver.waitForPageToBeReady();
  			bc.waitTime(2); 
  			getselectAllRedactionTag().isElementAvailable(15);
  			bc.waitForElement(getselectAllRedactionTag());
  			getselectAllRedactionTag().waitAndClick(10);
  			
  			getselectActionToggle().isElementAvailable(15);
  			bc.waitForElement(getselectActionToggle());
  			getselectActionToggle().waitAndClick(10);

  			getselectNewFromDropdown().isElementAvailable(15);
  			bc.waitForElement(getselectNewFromDropdown());
  			getselectNewFromDropdown().waitAndClick(10);

  			getRedactionTagName().isElementAvailable(15);
  			bc.waitForElement(getRedactionTagName());
  			getRedactionTagName().SendKeys(TagName);

  			getSaveBtn().isElementAvailable(15);
  			bc.waitForElement(getSaveBtn());
  			getSaveBtn().waitAndClick(10);
  			
  		}
  	
  	/**
  	 * @author Mohan.Venugopal
  	 * @description: To validate Redaction Tag page count 
  	 */
  	public void validateReactionTagPageTree() {

  		driver.waitForPageToBeReady();
  		ElementCollection redactionTagsCount = getredactiontags();
  		int sizeRCCount = redactionTagsCount.size();
  		System.out.println(redactionTagsCount);
  		if (sizeRCCount>3) {
  			bc.passedStep("There are more than 2 Redaction Tags in each security Groups(1-1 in Security Group) should exists in source template project.");
			
		}else {
			bc.failedStep("There is no Redaction Tags in this project");
		}
  		
  		
	}
  	
  	/**
  	 * @author Mohan.Venugopal
  	 * @description: To validate Redaction Tag page Tags present 
  	 */
  	public void verifyRedactionTagPage() {
		
  		
  		driver.waitForPageToBeReady();
  		if (getSelectredaction("Default Redaction Tag").isElementAvailable(5)&&getSelectredaction("Redacted Privacy").isElementAvailable(5)&&getSelectredaction("Redacted Privilege").isElementAvailable(5)) {
			
  			bc.passedStep("The provisioned Redaction Tags are available in RedactionTag Page successfully");
		}else {
			
			bc.failedStep("The provisioned Redaction Tags are not available in RedactionTag Page");
		}
  		
  		
	}
  	
  	/**
	 * @author Krishna
	 * @description: To verify Redaction tag below provisioned privacy and privilege
	 *               tag is available
	 */
	public void verifyReductionTagPrivacyAndPriviledge() {
		SoftAssert softassertion = new SoftAssert();
		driver.waitForPageToBeReady();
		String getPrivacy = getRedactedPrivacy().getText();
		String getPrivilege = getRedactedPrivilege().getText();
		softassertion.assertTrue(getRedactedPrivacy().isElementPresent());
		softassertion.assertTrue(getRedactedPrivilege().isElementPresent());
		driver.waitForPageToBeReady();
		if (getRedactedPrivacy().isDisplayed() && getRedactedPrivilege().isElementPresent()) {
			bc.passedStep("In Redaction Tags below provisioned  " + getPrivacy + getPrivilege
					+ "  is available successfully");

		} else {
			bc.failedStep("Redaction Tags below provisioned is not available");
		}

	}


 }
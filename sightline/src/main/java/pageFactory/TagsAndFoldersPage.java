package pageFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;


public class TagsAndFoldersPage {

    Driver driver;
    BaseClass base;
    LoginPage lp;
	int j,k;
     
    public Element getTag_ToggleDocCount(){ return driver.FindElementById("tagDocCount"); }
    public Element getTagandCount(String TagName, int count){ return driver.FindElementByXPath("//*[@id='-1']/ul/li//a[contains(text(),'"+TagName+" ("+count+")')]"); }
    public Element getFolder_ToggleDocCount(){ return driver.FindElementById("folderDocCount"); }
    public Element getFolderandCount(String FolderName, int count) {return driver.FindElementByXPath("//ul[@class='jstree-children']//a[contains(text(),'"+FolderName+" ("+count+")')]");}
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
    public Element getAllTagRoot(){ return driver.FindElementById("-1_anchor"); }
    public Element getAddTag(){ return driver.FindElementById("aAddTag"); }
    public Element getTagName(){ return driver.FindElementById("txtTagName"); }
    public Element getSaveTag(){ return driver.FindElementById("btnAddTag"); }
    public Element getTagActionDropDownArrow(){ return driver.FindElementByXPath(".//*[@id='tabs-a']/div[1]/div/button[2]"); }
    public Element getFoldersTab(){ return driver.FindElementByXPath("//*[@class='tags-folders']//a[contains(text(),'Folders')]"); }
    public Element getTagsTab(){ return driver.FindElementById("ui-id-1"); }
    public Element getAllFolderRoot(){ return driver.FindElementByXPath("//a[contains(text(),'All Folders')]"); }
    public Element getDecutiryGroup(){ return driver.FindElementById("ddlSecurityGroupFolder"); }
    public Element getAddFolder(){ return driver.FindElementById("aAddFolder"); }
    public Element getFolderName(){ return driver.FindElementById("txtFolderName"); }
    public Element getSaveFolder(){ return driver.FindElementById("btnAddFolder"); }
    public Element getFolderActionDropDownArrow(){ return driver.FindElementByXPath("//*[@id='tabs-b']/div[1]/div/button[2]"); }
 
    //added on 4th feb
    public Element getTagName(String TagName){ return driver.FindElementByXPath("//a[contains(text(),'"+TagName+"')]"); }
    public Element getFolderName(String FolderName){ return driver.FindElementByXPath("//a[contains(text(),'"+FolderName+"')]"); }
    public Element getDeleteTag(){ return driver.FindElementById("aDeleteTagTagGroup"); }
    public Element getDeleteFolder(){ return driver.FindElementById("aDeleteFolderFolderGroup"); }
        
    //Security group select while creating tag and folder
    public Element getTagSecutiryGroup(){ return driver.FindElementById("ddlSecurityGroupTag"); }
    public Element getFolderSecutiryGroup(){ return driver.FindElementById("ddlSecurityGroupFolder"); }
    //added on 05/06 
    public Element getSecutiryGroupTag(){ return driver.FindElementById("ddlSecurityGroupTag"); }
    public Element getTagClassification(){ return driver.FindElementById("ddlTagClassificationName"); }
    public Element getTagViewDoclist(){ return driver.FindElementById("aViewTagInDocList"); }
    public Element getTagViewDocView(){ return driver.FindElementById("aViewTagInDocView"); }
    public Element getFolderViewDoclist(){ return driver.FindElementById("aViewFolderInDocList"); }
    public Element getFolderViewDocView(){ return driver.FindElementById("aViewFolderInDocView"); }
    
    //added- System Level Template - Narendra
    public Element getDefaultTag(){ return driver.FindElementByXPath("//a[contains(text(),'Default Tags')]"); }
    public Element getDefaultTagsArrow(){ return driver.FindElementByXPath("//li[@id='1']//i[@class='jstree-icon jstree-ocl']"); }
    public ElementCollection getDefaultTagsCount(){ return driver.FindElementsByXPath("//a[@data-content='Default Tags']/following-sibling::ul/li"); }
    public Element getDefaultSecgrp(){ return driver.FindElementByXPath("//select[@id='ddlSecurityGroupTag']//option[contains(text(),'Default Security Group')]"); }
    public Element action(){ return driver.FindElementByXPath("//button[@data-toggle='dropdown']/following-sibling::ul/li[3]"); }
    public Element actionarrow(){ return driver.FindElementByXPath("//button[@data-toggle='dropdown']"); }
    public Element getSelectFolders(){ return driver.FindElementByXPath("//div[starts-with(@id,'tabs')]/ul/li/a[contains(text(),'Folders')]"); }
    public Element getAllFolders(){ return driver.FindElementByXPath("//a[@id='-1_anchor']"); }
    public ElementCollection getFolderList(){ return driver.FindElementsByXPath("//div[starts-with(@id,'folderJSTree')]//ul//li"); }
    public Element getDefaultSecgrpClick(){ return driver.FindElementByXPath("//select[@id='ddlSecurityGroupFolder']"); }
    public Element getFoldersDefaultSecgrp(){ return driver.FindElementByXPath("//select[@id='ddlSecurityGroupFolder']//option[contains(text(),'Default Security Group')]"); }
    public Element getreductionclick(){ return driver.FindElementByXPath("//div[starts-with(@id,'tagsJSTree')]/ul/li/a[contains(text(),'All Redaction Tags')]"); }
    public ElementCollection getReductionList(){ return driver.FindElementsByXPath("//div[starts-with(@id,'tagsJSTree')]//ul//li"); }
    public Element getRedAllgrp(){ return driver.FindElementByXPath("//select[@id='ddlSecurityGroupRedaction']"); }
    public Element getRedDefaultSecgrp(){ return driver.FindElementByXPath("//select[@id='ddlSecurityGroupRedaction']//option[contains(text(),'Default Security Group')]"); }
    //public Element getAllFolderRoot(){ return driver.FindElementByXPath("//a[contains(text(),'All Folders')]"); }
    public Element rightarrow(){ return driver.FindElementByXPath("(//span[@class='caret'])[1]"); }
    public Element getEditClick(){ return driver.FindElementByXPath("//a[@id='aEditTagTagGroup']"); }
    public Element getLayerClicked(){ return driver.FindElementById("AnnotationsDatatable"); }
    public ElementCollection getLayerList(){ return driver.FindElementsByXPath("//table[@id='AnnotationsDatatable']//tbody//tr//td[@class='sorting_1']"); }
    public Element getAnnoDefaultSecgrp(){ return driver.FindElementByXPath("//select[@id='ddlSecurityGroup']//option[contains(text(),'Default Security Group')]"); }
    public Element getDefaulttagallele(String ele){ return driver.FindElementByXPath("//a[@data-content='"+ele+"']"); }
    public Element getTagclick(){ return driver.FindElementByXPath("//select[@id='ddlTagClassificationName']"); }
    public ElementCollection getAllTagClassification(){ return driver.FindElementsByXPath("//select[@id='ddlTagClassificationName']//option"); }
    public Element getCancel(){ return driver.FindElementByXPath("//button[@id='btnUpdateTagCancel']"); }
    public Element getAllFolderClick(){ return driver.FindElementByXPath("//a[@id='-1_anchor']"); }
    public Element getCancelTag(){ return driver.FindElementById("btnAddTagCancel"); }
    public Element getErrorMsg(){ return driver.FindElementById("tagErrorMsg"); }
    public ElementCollection getTagGroup(){ return driver.FindElementsByXPath("//a[contains(@class,'jstree-anchor tag-groups')]"); }
    public Element getEditTag(){ return driver.FindElementById("aEditTagTagGroup"); }
    
    
    public Element getTagGroupName(){ return driver.FindElementById("txtTagGroupName"); }

    
    
       public TagsAndFoldersPage(Driver driver){

        this.driver = driver;
        base = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
        driver.waitForPageToBeReady();
        }
    
    //securityGroup parameter value will be secGroup name else 'All Groups'   
    public void CreateTag(String strtag, String securityGroup) throws InterruptedException 
    {
    	   this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
    	     
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagsTab().Visible()  ;}}), Input.wait30); 
         try{
    	 getTagsTab().waitAndClick(10);	
         }catch (Exception e) {
			//may be in tag already
		}
         try{
    	//Select secGroup
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTagSecutiryGroup().Visible()  ;}}), Input.wait30); 
    	getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
         }catch (Exception e) {
			// TODO: handle exception
		}
    	//Select root all
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getAllTagRoot().Visible()  ;}}), Input.wait30); 
    	getAllTagRoot().waitAndClick(10);
    	
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
        getTagActionDropDownArrow().waitAndClick(10);
        
        Thread.sleep(1000);
        
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getAddTag().Visible()  ;}}), Input.wait30); 
        getAddTag().waitAndClick(10);
   
     
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getTagName().Visible()  ;}}), Input.wait30); 
     getTagName().SendKeys(strtag);
     
     Thread.sleep(1000);
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getSaveTag().Visible()  ;}}), Input.wait30); 
     getSaveTag().waitAndClick(10);
     base.VerifySuccessMessage("Tag added successfully");
 	 Reporter.log("Tag '"+strtag+"' is added successfully to security group "+securityGroup ,true);
	 UtilityLog.info("Tag Successful");
 
     base.CloseSuccessMsgpopup();
   	
    }
    
    public void CreateFolder(String strFolder, String securityGroup) throws InterruptedException 
    {
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getFoldersTab().Visible()  ;}}), Input.wait60); 
    	 
     
    	 try{
    		 getFoldersTab().waitAndClick(10);		
             }catch (Exception e) {
    			//may be in folder already
    		}
    	 try{
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getDecutiryGroup().Visible()  ;}}), Input.wait30);
         getDecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
    	 }catch (Exception e) {
 			// TODO: handle exception
 		}

         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getAllFolderRoot().Visible()  ;}}), Input.wait30); 
         Thread.sleep(3000);
         getAllFolderRoot().waitAndClick(10);
         Thread.sleep(3000);
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getFolderActionDropDownArrow().Visible()  ;}}), Input.wait30); 
         getFolderActionDropDownArrow().waitAndClick(30);
     
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getAddFolder().Visible()  ;}}), Input.wait30); 
         getAddFolder().waitAndClick(10);
     
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getFolderName().Visible()  ;}}), Input.wait30); 
      new Actions(driver.getWebDriver()).sendKeys(strFolder).perform();
     //getFolderName().SendKeys(strFolder);
     
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getSaveFolder().Visible()  ;}}), Input.wait30); 
     getSaveFolder().waitAndClick(10);
     
     base.VerifySuccessMessage("Folder added successfully");
     base.CloseSuccessMsgpopup();
     Reporter.log("Folder "+strFolder+" added to security group -"+securityGroup,true);
 	 UtilityLog.info("Folder Successful");
    }
    
    public void DeleteTag(final String strtag,String securityGroup) 
    {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getTagsTab().Visible()  ;}}), Input.wait30); 
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	getTagsTab().waitAndClick(10);
        
    	
    	//Select secGroup
        try{
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTagSecutiryGroup().Visible()  ;}}), Input.wait30); 
    	getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
        }catch (Exception e) {
        	e.printStackTrace();
		}
       //driver.scrollingToBottomofAPage(); 
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagName(strtag).Visible()  ;}}), Input.wait30); 
         
    	 getTagName(strtag).waitAndClick(10);
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
    	 
    	 driver.scrollPageToTop();
         getTagActionDropDownArrow().waitAndClick(10);
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getDeleteTag().Visible()  ;}}), Input.wait30); 
        getDeleteTag().waitAndClick(10);
   
       base.getYesBtn().waitAndClick(10);
     
        base.VerifySuccessMessage("Tag deleted successfully");
        base.CloseSuccessMsgpopup();
        
        Reporter.log(strtag +"tag delete Successful",true);
    	UtilityLog.info("Tag delete Successful");
     
      }
    
    public void DeleteFolder(final String strFolder,String securityGroup) 
    {
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getFoldersTab().Visible()  ;}}), Input.wait30); 
    	 driver.scrollPageToTop();
         getFoldersTab().waitAndClick(15);	
         
       //Select secGroup
         try{
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getFolderSecutiryGroup().Visible()  ;}}), Input.wait30); 
     	Thread.sleep(3000);
     	getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
         }catch (Exception e) {
        	 e.printStackTrace();
 		}

         //driver.scrollingToBottomofAPage();
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getFolderName(strFolder).Visible()  ;}}), Input.wait30); 
         getFolderName(strFolder).waitAndClick(10);	
     
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getFolderActionDropDownArrow().Visible()  ;}}), Input.wait30); 
        
         driver.scrollPageToTop();
         getFolderActionDropDownArrow().waitAndClick(10);
     
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getDeleteFolder().Visible()  ;}}), Input.wait30); 
         getDeleteFolder().waitAndClick(10);
     
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 base.getYesBtn().Visible()  ;}}), Input.wait30); 
    	    base.getYesBtn().waitAndClick(10);
         
            base.VerifySuccessMessage("Folder deleted successfully");
            base.CloseSuccessMsgpopup();
            Reporter.log(strFolder +" Folder delete Successful", true);
        	UtilityLog.info("Folder delete Successful");
     
    }
    
    public void CreateTagwithClassification(String strtag,String classificationname) 
    {
        this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
        
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getSecutiryGroupTag().Visible()  ;}}), Input.wait30); 
    	getSecutiryGroupTag().selectFromDropdown().selectByVisibleText("Default Security Group");
   	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getAllTagRoot().Visible()  ;}}), Input.wait30); 
    	getAllTagRoot().waitAndClick(10);
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
    	
    	 driver.scrollPageToTop();
        getTagActionDropDownArrow().waitAndClick(10);
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getAddTag().Visible()  ;}}), Input.wait30); 
        getAddTag().waitAndClick(10);
   
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getTagName().Visible()  ;}}), Input.wait60); 
       getTagName().SendKeys(strtag);
       
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getTagClassification().Visible()  ;}}), Input.wait30); 
       getTagClassification().selectFromDropdown().selectByVisibleText(classificationname);
     
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getSaveTag().Visible()  ;}}), Input.wait30); 
       getSaveTag().waitAndClick(10);
     
      base.VerifySuccessMessage("Tag added successfully");
      Reporter.log("Tag "+strtag+" is as/under "+classificationname,true);
      base.CloseSuccessMsgpopup();
     
    }
    
     public void ViewinDocViewthrTag(final String strtag) 
    {
    	 driver.getWebDriver().navigate().to(Input.url+"TagsAndFolders/TagsAndFolders");
    	 
    	
    	 driver.scrollPageToTop();
         getTagActionDropDownArrow().waitAndClick(10);
         
        /* Assert.assertFalse(getTagViewDocView().Enabled());
         Assert.assertFalse(getTagViewDoclist().Enabled());*/
         
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagName(strtag).Visible()  ;}}), Input.wait30); 
    	 getTagName(strtag).waitAndClick(10);
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
    	 
    	 driver.scrollPageToTop();
    	 getTagActionDropDownArrow().waitAndClick(10);
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getTagViewDocView().Visible()  ;}}), Input.wait30); 
        getTagViewDocView().waitAndClick(10);
   
             }
     
     public void ViewinDocListthrTag(final String strtag) 
     {
     	 driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
     	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getTagName(strtag).Visible()  ;}}), Input.wait30); 
     	 getTagName(strtag).waitAndClick(10);
     	
     	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30);
     	 
     	 driver.scrollPageToTop();
          getTagActionDropDownArrow().waitAndClick(10);
      
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getTagViewDoclist().Visible()  ;}}), Input.wait30); 
         getTagViewDoclist().waitAndClick(10);
    
       }
  
     public void ViewinDocListthrFolder(final String strFolder) 
     {
    	 driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getFoldersTab().Visible()  ;}}), Input.wait30); 
          getFoldersTab().waitAndClick(10);	
          
        //Select secGroup
          try{
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getFolderSecutiryGroup().Visible()  ;}}), Input.wait30); 
      	getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
          }catch (Exception e) {
  			// TODO: handle exception
  		}
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getFolderName(strFolder).Visible()  ;}}), Input.wait30); 
         getFolderName(strFolder).waitAndClick(10);	
      
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     		 getFolderActionDropDownArrow().Visible()  ;}}), Input.wait30); 
         
         driver.scrollPageToTop();
          getFolderActionDropDownArrow().waitAndClick(10);
      
          driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		  getFolderViewDoclist().Visible()  ;}}), Input.wait30); 
         getFolderViewDoclist().waitAndClick(10);
  }  	
     
     public void ViewinDocViewthrFolder(final String strFolder) 
     {
    	 driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getFoldersTab().Visible()  ;}}), Input.wait30); 
          getFoldersTab().waitAndClick(10);	
          
        //Select secGroup
          try{
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getFolderSecutiryGroup().Visible()  ;}}), Input.wait30); 
      	getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
          }catch (Exception e) {
  			// TODO: handle exception
  		}
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getFolderName(strFolder).Visible()  ;}}), Input.wait30); 
         getFolderName(strFolder).waitAndClick(10);	
      
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     		 getFolderActionDropDownArrow().Visible()  ;}}), Input.wait30); 
         
         driver.scrollPageToTop();
          getFolderActionDropDownArrow().waitAndClick(10);
      
          driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		  getFolderViewDoclist().Visible()  ;}}), Input.wait30); 
         getFolderViewDocView().waitAndClick(10);
  }  	
    
    //Code added by Narendra
 	
 	public void provisionedTags() throws InterruptedException {
 				
 		         this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
 		   	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getDefaultTag().Visible()  ;}}), Input.wait30); 
      	     	 getDefaultTagsArrow().waitAndClick(10);
      	     	 Thread.sleep(3000);
      	     	 List<String> test1 = new ArrayList<String>();
      	     	 List<WebElement> deflttags1 = getDefaultTagsCount().FindWebElements();
      	     	 for (j=0; j<deflttags1.size(); j++){
      	     	 test1.add(deflttags1.get(j).getText());}
      	     	 
      	     	 List<String> test2 = Arrays.asList("Attorney_Client", "Attorney_WorkProduct", "Confidential", "Foreign_Language","Hidden_Content", "Highly_Confidential", "Hot_Doc", "Not_Privileged", "Not_Responsive", "Privileged","Processing_Issue", "Responsive", "Technical_Issue");
      	     	
      	         //Comparison
      	     	 Assert.assertEquals(test1, test2);
      	     	 System.out.println("Verified provisioned Tags are available under Default Tags " ); 
      	     	
      	         //Now Default Security Group 
     	     	 getDefaultSecgrp().waitAndClick(10);
     	     	 Thread.sleep(3000);
      	         getDefaultTagsArrow().waitAndClick(10);
      	         Thread.sleep(3000);
      	         List<String> test3 = new ArrayList<String>();
 	     	     List<WebElement> deflttags2 = getDefaultTagsCount().FindWebElements();
 	     	     for (k=0; k<deflttags2.size(); k++){
 	     	     test3.add(deflttags2.get(k).getText());}     	  
 	     	     Assert.assertEquals(test2, test3);
 	     		 System.out.println("Verified provisioned Tags are available under Default Security Group " );
	     		 
 	         	 
 	}
 	
 	public void provisionedTagsAsRMU() throws InterruptedException {

				 this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
				 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getDefaultTag().Visible()  ;}}), Input.wait30); 
				 getDefaultTagsArrow().waitAndClick(10);
				 Thread.sleep(3000);
				 List<String> protaglist3 = new ArrayList<String>();
				 List<WebElement> deflttags3 = getDefaultTagsCount().FindWebElements();
				 for (j=0; j<deflttags3.size(); j++){
				 protaglist3.add(deflttags3.get(j).getText());}
				 List<String> prolist2 = Arrays.asList("Attorney_Client", "Attorney_WorkProduct", "Confidential", "Foreign_Language","Hidden_Content", "Highly_Confidential", "Hot_Doc", "Not_Privileged", "Not_Responsive", "Privileged","Processing_Issue", "Responsive", "Technical_Issue");
				 Assert.assertEquals(prolist2, protaglist3);
				 System.out.println("Verified provisioned Tags are available under Default Tags as RMU User" );   
 	}
 		
 	public void tagsClassifications() throws InterruptedException {
 		
 		this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
   	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 		getDefaultTag().Visible()  ;}}), Input.wait30); 
 		//getDefaultSecgrp().waitAndClick(10);
 	    getDefaultTagsArrow().waitAndClick(10);
        	List<WebElement> allvalues1 = getDefaultTagsCount().FindWebElements();
        	List<String> test12 = Arrays.asList("Select Tag Classification","Technical Issue","Private Data","Privileged","Responsive");
        	for(WebElement element : allvalues1) {
		        	 element.click();
		        	 Thread.sleep(1000);
		        	 driver.scrollPageToTop();
			          new Actions(driver.getWebDriver()).moveToElement(rightarrow().getWebElement()).click();
			         //rightarrow().waitAndClick(10);
			         rightarrow().waitAndClick(10);
			         Thread.sleep(1000);
			         getEditClick().waitAndClick(10);
			         Thread.sleep(1000);
			         getTagclick().waitAndClick(10);
			         Thread.sleep(1000);
			         List<String> tags1 = new ArrayList<String>();
			  	     List<WebElement> tagsclss = getAllTagClassification().FindWebElements();
			  	     for (k=0; k<tagsclss.size(); k++){
			  	    	tags1.add(tagsclss.get(k).getText());}
			  	     Assert.assertEquals(tags1, test12);
			  	     getCancel().waitAndClick(10);
		        } 
        	System.out.println("Verified Tag Classification under All Tags" ); 
        	
        //Now Default Security Group
        getDefaultSecgrp().waitAndClick(10);
 	    getDefaultTagsArrow().waitAndClick(10);
 	    List<WebElement> allvalues2 = getDefaultTagsCount().FindWebElements();
       	for(WebElement element : allvalues2) {
			    	 element.click();
			    	 Thread.sleep(1000);
			    	 driver.scrollPageToTop();
			         new Actions(driver.getWebDriver()).moveToElement(rightarrow().getWebElement()).click();
				     //rightarrow().waitAndClick(10);
				     rightarrow().waitAndClick(10);
				     Thread.sleep(1000);
				     getEditClick().waitAndClick(10);
				     Thread.sleep(1000);
				     getTagclick().waitAndClick(10);
				     Thread.sleep(1000);
				     List<String> tags2 = new ArrayList<String>();
				     List<WebElement> tagsclss = getAllTagClassification().FindWebElements();
				     for (k=0; k<tagsclss.size(); k++){
				     tags2.add(tagsclss.get(k).getText());}
				     Assert.assertEquals(tags2, test12);
				     getCancel().waitAndClick(10);} 
				     System.out.println("Verified Tag Classification under Default Security Group" ); 

 	}
 	
 	public void tagsClassificationsAsRMU() throws InterruptedException {
 		this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDefaultTag().Visible()  ;}}), Input.wait30); 
					//getDefaultSecgrp().waitAndClick(10);
				    getDefaultTagsArrow().waitAndClick(10);
				    List<String> test13 = Arrays.asList("Select Tag Classification","Technical Issue","Private Data","Privileged","Responsive");
			       	List<WebElement> allvalues3 = getDefaultTagsCount().FindWebElements();
			       	//List<String> test12 = Arrays.asList("Select Tag Classification","Technical Issue","Private Data","Privileged","Responsive");
			       	for(WebElement element : allvalues3) {
			       	element.click();
			       	Thread.sleep(1000);
			       	driver.scrollPageToTop();
			        new Actions(driver.getWebDriver()).moveToElement(rightarrow().getWebElement()).click();
			        //rightarrow().waitAndClick(10);
			        rightarrow().waitAndClick(10);
			        Thread.sleep(1000);
			        getEditClick().waitAndClick(10);
			        Thread.sleep(1000);
			        getTagclick().waitAndClick(10);
			        Thread.sleep(1000);
			        List<String> tags3 = new ArrayList<String>();
			 	    List<WebElement> tagsclss = getAllTagClassification().FindWebElements();
			 	    for (k=0; k<tagsclss.size(); k++){
			 	    	tags3.add(tagsclss.get(k).getText());}
			 	    Assert.assertEquals(tags3, test13);
			 	    getCancel().waitAndClick(10);
			       } 
			       	System.out.println("Verified Tag Classification under All Tags for RMU User" ); 
 	}

 	public void layerAnnotations() throws InterruptedException {
 
 		this.driver.getWebDriver().get(Input.url+"/Annotations/Annotations");
 		//getLayerClicked().waitAndClick(10);
 		Thread.sleep(3000);
 		List<String> layerann1 = new ArrayList<String>();
  	    List<WebElement> layertags1 = getLayerList().FindWebElements();
  	    for (k=0; k<layertags1.size(); k++){
  	    	layerann1.add(layertags1.get(k).getText());}
  	    Assert.assertTrue(layerann1.contains("Default Annotation Layer")); 
  		System.out.println("Verified provisioned Annotation Layer is available in the Project" );
  		
  		//Now Default Security Group
  		getAnnoDefaultSecgrp().waitAndClick(10);
  		Thread.sleep(3000);
 		List<String> layerann3 = new ArrayList<String>();
  	    List<WebElement> layertags3 = getLayerList().FindWebElements();
  	    for (k=0; k<layertags3.size(); k++){
  	    	layerann3.add(layertags3.get(k).getText());}
  	    Assert.assertTrue(layerann3.contains("Default Annotation Layer")); 
  		System.out.println("Verified provisioned Annotation Layer is available in Default Security Group" );

 	}
 	
	public void layerAnnotationsAsRMU() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url+"/Annotations/Annotations");
 		Thread.sleep(3000);
 		List<String> layerann2 = new ArrayList<String>();
  	    List<WebElement> layertags2 = getLayerList().FindWebElements();
  	    for (k=0; k<layertags2.size(); k++){
  	    layerann2.add(layertags2.get(k).getText());}
  	    Assert.assertTrue(layerann2.contains("Default Annotation Layer")); 
  		System.out.println("Verified provisioned Annotation Layer is available in the Project as RMU User" );
	}

 	public void provisionedFolder() throws InterruptedException {
 
 		this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
 		getSelectFolders().waitAndClick(10);
 		Thread.sleep(3000);
 		getAllFolders().waitAndClick(10);
 		List<String> test4 = new ArrayList<String>();
      	List<WebElement> defltfolders1 = getFolderList().FindWebElements();

      	for (j=0; j<defltfolders1.size(); j++){
      	test4.add(defltfolders1.get(j).getText());}
      	Assert.assertTrue(test4.contains("Productions"));    
      	System.out.println("Verified Productions is available under All Folders " );

      	
 	}
 	
 	public void provisionedFolderAsRMU() throws InterruptedException {
 		this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
 		getSelectFolders().waitAndClick(10);
 		Thread.sleep(3000);		
 		List<String> test5 = new ArrayList<String>();
      	List<WebElement> defltfolders2 = getFolderList().FindWebElements();

      	for (k=0; k<defltfolders2.size(); k++){
      		test5.add(defltfolders2.get(k).getText());}
      	    
      	Assert.assertTrue(test5.contains("Productions"));
      	System.out.println("Verified Productions is available under under All Folders as RMU User " );
 	}

 	public void redactionTags() throws InterruptedException {
 		    
 		    this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
 		    getreductionclick().waitAndClick(10);
 		    Thread.sleep(3000);
 			List<String> test6 = new ArrayList<String>();
 	     	List<WebElement> red1 = getReductionList().FindWebElements();
 	     	for (j=0; j<red1.size(); j++){
 	     	test6.add(red1.get(j).getText());}
    	     	Assert.assertTrue(test6.contains("Default Redaction Tag") && test6.contains("Redacted Privacy") && test6.contains ("Redacted Privilege"));
 	     	System.out.println("Verified provisioned Redaction Tags are available in the Project");
 	     	
 	     	//Now Default Security Group 
     	     
 	     	//getRedAllgrp().waitAndClick(10);
 	     	getRedDefaultSecgrp().waitAndClick(10);
 	     	Thread.sleep(3000);
 	     	getDefaultTagsArrow().waitAndClick(10);
 	     	Thread.sleep(3000);
 	     	List<String> test7 = new ArrayList<String>();
 	     	List<WebElement> red2 = getReductionList().FindWebElements();
 	     	for (j=0; j<red2.size(); j++){
 	     	test7.add(red2.get(j).getText());}     	    
     	  	Assert.assertTrue(test7.contains("Default Redaction Tag") && test7.contains("Redacted Privacy") && test7.contains ("Redacted Privilege"));
     	  	System.out.println("Verified provisioned Redaction Tags are available under Default Security Group " );

 	}
 	
 	public void redactionTagsAsRMU() throws InterruptedException {
 		   this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
		    getreductionclick().waitAndClick(10);
		    Thread.sleep(3000);
			List<String> redlist3 = new ArrayList<String>();
	     	List<WebElement> red3 = getReductionList().FindWebElements();
	     	for (j=0; j<red3.size(); j++){
	     		redlist3.add(red3.get(j).getText());}
 	     	Assert.assertTrue(redlist3.contains("Default Redaction Tag") && redlist3.contains("Redacted Privacy") && redlist3.contains ("Redacted Privilege"));
	     	System.out.println("Verified provisioned Redaction Tags are available in the Project as RMU User");
 	}
 
 	public void provisionedFolderGroup() throws InterruptedException{
 		
 		this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
 		getSelectFolders().waitAndClick(10);
 		Thread.sleep(3000);
 		List<String> test8 = new ArrayList<String>();
      	List<WebElement> dgrp1 = getFolderList().FindWebElements();
      	for (j=0; j<dgrp1.size(); j++){
      		test8.add(dgrp1.get(j).getText());}   	    
      	Assert.assertTrue(test8.contains("Productions"));
      	System.out.println("Productions is available under All Folders");
      	Thread.sleep(3000);
      	    	
      	
      	//Now select Default Security Group
      	getFoldersDefaultSecgrp().waitAndClick(10);
      	Thread.sleep(3000);
      	getAllFolderClick().waitAndClick(10);//Default Security Group is getting disabled
      	List<String> test9 = new ArrayList<String>();
      	List<WebElement> defltfldrgroup2 = getFolderList().FindWebElements();

      	for (k=0; k<defltfldrgroup2.size(); k++){
      		test9.add(defltfldrgroup2.get(k).getText());}
      	    
      	Assert.assertTrue(test9.contains("Productions"));
      	System.out.println("Productions is available under Default Security Group");
      		
 	}
	
 	public void Tags(String strtag, String securityGroup) 
    {
    	 this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
    	     
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagsTab().Visible()  ;}}), Input.wait30); 
         try{
    	 getTagsTab().waitAndClick(10);	
         }catch (Exception e) {
			//may be in tag already
		}
         try{
    	//Select secGroup
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTagSecutiryGroup().Visible()  ;}}), Input.wait30); 
    	getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
         }catch (Exception e) {
			// TODO: handle exception
		}
    	//Select root all
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getAllTagRoot().Visible()  ;}}), Input.wait30); 
    	getAllTagRoot().waitAndClick(10);
    	
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
        getTagActionDropDownArrow().waitAndClick(10);
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getAddTag().Visible()  ;}}), Input.wait30); 
        getAddTag().waitAndClick(10);
   
     
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTagName().Visible()  ;}}), Input.wait30); 
		 new Actions(driver.getWebDriver()).sendKeys(strtag).perform();
	    // getTagName().SendKeys(strtag);
	     
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		 getCancelTag().Visible()  ;}}), Input.wait30); 
	     getCancelTag().waitAndClick(10);
	     System.out.println("Tag addition cancel successfully");
	     
        //Select root all
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getAllTagRoot().Visible()  ;}}), Input.wait30); 
    	getAllTagRoot().waitAndClick(10);
    	
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
        getTagActionDropDownArrow().waitAndClick(10);
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getAddTag().Visible()  ;}}), Input.wait30); 
        getAddTag().waitAndClick(10);
   
     
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTagName().Visible()  ;}}), Input.wait30);
		 //driver.scrollingToElementofAPage(getTagName());
	     getTagName().SendKeys("");
	     
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		 getCancelTag().Visible()  ;}}), Input.wait30); 
	     getSaveTag().waitAndClick(10);
	     
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		 getErrorMsg().Visible()  ;}}), Input.wait30);
	     String ExpectedErrorMsg = "Please enter tag name";
	     String ErrorMsg = getErrorMsg().getText();
	     Assert.assertEquals(ErrorMsg,ExpectedErrorMsg);
	     System.out.println("Verified Error message successfully");
	     
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		 getCancelTag().Visible()  ;}}), Input.wait30); 
	     getCancelTag().waitAndClick(10);
	     
	     base.CloseSuccessMsgpopup();
   	
    }
  
 	public void TagGroup(String securityGroup) 
    {
    	 this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
    	     
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagsTab().Visible()  ;}}), Input.wait30); 
         try{
    	 getTagsTab().waitAndClick(10);	
         }catch (Exception e) {
			//may be in tag already
		}
         try{
    	//Select secGroup
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTagSecutiryGroup().Visible()  ;}}), Input.wait30); 
    	getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
         }catch (Exception e) {
			// TODO: handle exception
		}
    	//Select root all
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAllTagRoot().Visible()  ;}}), Input.wait30); 
    	getAllTagRoot().waitAndClick(10);
    	
       	List<WebElement> groupele = getTagGroup().FindWebElements();
       	for(WebElement element : groupele) {
        	
      		if(groupele.get(j).getText() == "Default Tags")
      		{
      			System.out.println("Do Nothing");
      		}
      		else
      		{
      			element.click();
      			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      	    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
      			
      			driver.scrollPageToTop();
      	        getTagActionDropDownArrow().waitAndClick(10);
      	     
      	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      	        		getEditTag().Visible()  ;}}), Input.wait30); 
      	      getEditTag().waitAndClick(10);
      	      String tggrp=getTagGroupName().getText();
      	      System.out.println(tggrp);
      		}
    }
  }
 	
}  	
  
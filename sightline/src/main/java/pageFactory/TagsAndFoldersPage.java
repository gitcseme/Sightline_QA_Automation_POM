package pageFactory;

import java.util.concurrent.Callable;
import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import testScriptsSmoke.Input;


public class TagsAndFoldersPage {

    Driver driver;
    BaseClass base;
     
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

    
       public TagsAndFoldersPage(Driver driver){

        this.driver = driver;
        base = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
        driver.waitForPageToBeReady();
        }
    
    //securityGroup parameter value will be secGroup name else 'All Groups'   
    public void CreateTag(String strtag, String securityGroup) 
    {
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagsTab().Visible()  ;}}), Input.wait30); 
         try{
    	 getTagsTab().Click();	
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
        getTagActionDropDownArrow().Click();
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getAddTag().Visible()  ;}}), Input.wait30); 
        getAddTag().waitAndClick(10);
   
     
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getTagName().Visible()  ;}}), Input.wait30); 
     getTagName().SendKeys(strtag);
     
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getSaveTag().Visible()  ;}}), Input.wait30); 
     getSaveTag().Click();
     
     base.VerifySuccessMessage("Tag added successfully");
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
         Thread.sleep(2000);
         getAllFolderRoot().waitAndClick(10);
         Thread.sleep(2000);
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getFolderActionDropDownArrow().Visible()  ;}}), Input.wait30); 
         getFolderActionDropDownArrow().waitAndClick(10);
     
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getAddFolder().Visible()  ;}}), Input.wait30); 
         getAddFolder().Click();
     
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getFolderName().Visible()  ;}}), Input.wait30); 
     getFolderName().SendKeys(strFolder);
     
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getSaveFolder().Visible()  ;}}), Input.wait30); 
     getSaveFolder().Click();
     
     base.VerifySuccessMessage("Folder added successfully");
     base.CloseSuccessMsgpopup();
     
    }
    
    public void DeleteTag(final String strtag,String securityGroup) 
    {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getTagsTab().Visible()  ;}}), Input.wait30); 
        getTagsTab().waitAndClick(10);
    	
    	//Select secGroup
        try{
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTagSecutiryGroup().Visible()  ;}}), Input.wait30); 
    	getTagSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
        }catch (Exception e) {
			// TODO: handle exception
		}

    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagName(strtag).Visible()  ;}}), Input.wait30); 
    	 getTagName(strtag).waitAndClick(10);
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
         getTagActionDropDownArrow().Click();
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getDeleteTag().Visible()  ;}}), Input.wait30); 
        getDeleteTag().Click();
   
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 base.getYesBtn().Visible()  ;}}), Input.wait30); 
	    base.getYesBtn().Click();
     
        base.VerifySuccessMessage("Tag deleted successfully");
        base.CloseSuccessMsgpopup();
     
      }
    
    public void DeleteFolder(final String strFolder,String securityGroup) 
    {
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getFoldersTab().Visible()  ;}}), Input.wait30); 
         getFoldersTab().waitAndClick(10);	
         
       //Select secGroup
         try{
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getFolderSecutiryGroup().Visible()  ;}}), Input.wait30); 
     	getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText(securityGroup);
         }catch (Exception e) {
 			// TODO: handle exception
 		}

         
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getFolderName(strFolder).Visible()  ;}}), Input.wait30); 
        getFolderName(strFolder).waitAndClick(10);	
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getFolderActionDropDownArrow().Visible()  ;}}), Input.wait30); 
         getFolderActionDropDownArrow().waitAndClick(10);
     
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getDeleteFolder().Visible()  ;}}), Input.wait30); 
         getDeleteFolder().Click();
     
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 base.getYesBtn().Visible()  ;}}), Input.wait30); 
    	    base.getYesBtn().Click();
         
            base.VerifySuccessMessage("Folder deleted successfully");
            base.CloseSuccessMsgpopup();
     
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
        getTagActionDropDownArrow().Click();
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getAddTag().Visible()  ;}}), Input.wait30); 
        getAddTag().Click();
   
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getTagName().Visible()  ;}}), Input.wait30); 
       getTagName().SendKeys(strtag);
       
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getTagClassification().Visible()  ;}}), Input.wait30); 
       getTagClassification().selectFromDropdown().selectByVisibleText(classificationname);
     
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getSaveTag().Visible()  ;}}), Input.wait30); 
       getSaveTag().Click();
     
      base.VerifySuccessMessage("Tag added successfully");
      base.CloseSuccessMsgpopup();
     
    }
    
     public void ViewinDocViewthrTag(final String strtag) 
    {
    	 driver.getWebDriver().navigate().to(Input.url+"TagsAndFolders/TagsAndFolders");
    	 
    	
         getTagActionDropDownArrow().waitAndClick(10);
         
        /* Assert.assertFalse(getTagViewDocView().Enabled());
         Assert.assertFalse(getTagViewDoclist().Enabled());*/
         
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagName(strtag).Visible()  ;}}), Input.wait30); 
    	 getTagName(strtag).waitAndClick(10);
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
         getTagActionDropDownArrow().Click();
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getTagViewDocView().Visible()  ;}}), Input.wait30); 
        getTagViewDocView().Click();
   
             }
     
     public void ViewinDocListthrTag(final String strtag) 
     {
     	 driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
     	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getTagName(strtag).Visible()  ;}}), Input.wait30); 
     	 getTagName(strtag).waitAndClick(10);
     	
     	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getTagActionDropDownArrow().Visible()  ;}}), Input.wait30); 
          getTagActionDropDownArrow().Click();
      
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getTagViewDoclist().Visible()  ;}}), Input.wait30); 
         getTagViewDoclist().Click();
    
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
          getFolderActionDropDownArrow().waitAndClick(10);
      
          driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		  getFolderViewDoclist().Visible()  ;}}), Input.wait30); 
         getFolderViewDoclist().Click();
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
          getFolderActionDropDownArrow().waitAndClick(10);
      
          driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		  getFolderViewDoclist().Visible()  ;}}), Input.wait30); 
         getFolderViewDocView().Click();
  }  	
    

     
  
  }  	
  
package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class ManageUsersPage {

    Driver driver;
  
    public Element getAssignUserButton(){ return driver.FindElementById("btnAssign"); }
    public Element getBulkUserAccessButton(){ return driver.FindElementById("btnBulkUserAccessControl"); }
    public Element getProjectsTab(){ return driver.FindElementById("tabNew"); }
    public Element getSelectProjectName(){ return driver.FindElementById("lstProjects"); }
    public Element getAssignUser_ProjectAdmin1(){ return driver.FindElementByXPath("//*[@id='UnAssignedUser']//option[text()='"+Input.pa1FullName+"']"); }
    public Element getAssignUser_ProjectAdmin2(){ return driver.FindElementByXPath(".//*[@id='UnAssignedUser']//option[text()='"+Input.pa2FullName+"']"); }
    public Element getAssignUser_RMU1(){ return driver.FindElementByXPath(".//*[@id='UnAssignedUser']//option[text()='"+Input.rmu1FullName+"']"); }
    public Element getAssignUser_RMU2(){ return driver.FindElementByXPath(".//*[@id='UnAssignedUser']//option[text()='"+Input.rmu2FullName+"']"); }
    public Element getAssignUser_Reviewer1(){ return driver.FindElementByXPath(".//*[@id='UnAssignedUser']//option[text()='"+Input.rev1FullName+"']"); }
    public Element getAssignUser_Reviewer2(){ return driver.FindElementByXPath(".//*[@id='UnAssignedUser']//option[text()='"+Input.rev2FullName+"']"); }
    
    public Element getSelectSecurityGroup(){ return driver.FindElementByXPath("//*[@id='lstSecurityGroup']//option[contains(text(), 'Default Security Group')]"); }
    
    public Element getAssignUser_Role(){ return driver.FindElementById("lstRoles"); }
    public Element getAssignUser_AddInList(){ return driver.FindElementById("btnRightUserMaapping"); }
    public Element getAssignUser_SecurityGroup(){ return driver.FindElementById("lstSecurityGroup"); }
    public Element getCloseButton(){ return driver.FindElementByXPath(".//i[starts-with(@id,'botClose')]"); }
    public Element getAssignUser_Save(){ return driver.FindElementById("btnSave"); }
    public Element getAssignedUser(){ return driver.FindElementById("AssignedUser"); }
    
    public Element getBulkUserRole(){ return driver.FindElementById("ddlBulkUserRoles"); }
    public Element getBulkUserProject(){ return driver.FindElementById("ddlBulkUserProjects"); }
    public Element getBulkEnableButton(){ return driver.FindElementByXPath("//*[@id='rbEnable']/following-sibling::i"); }
    public Element getBulkIngestion(){ return driver.FindElementById("lblCanIngestion"); }
    public Element getBulkProduction(){ return driver.FindElementById("lblCanProductions"); }
    public Element getBulkDataset() {return driver.FindElementByXPath("//*[@id='lblCanProview']/i");}
    public Element getBulkUserList(){ return driver.FindElementByXPath("//*[@id='divBulkUserList']//label[contains(text(),'"+Input.pa1FullName+"')]"); }
    public Element getBulkUserSave(){ return driver.FindElementById("btnSaveBulkAccessControls"); }
    public Element getEditBtn() {return driver.FindElementByXPath("//*[@id='dtUserList']/tbody//tr[td='" + Input.ICEProjectName + "']/td[9]/a[1]"); }
   // public Element getEditBtn(Element project) {return project.FindElementBycssSelector("td:nth-child(9) >  a"); }
    public Element getBulkCancelBtn(){return driver.FindElementByCssSelector("btnCancelBulkAccessControls");}
    
    public Element getEditUserFunctionality() {return driver.FindElementByCssSelector("#myTab1 > li:nth-child(2) > a");}
    public Element getIngestionsCheckbox() {return driver.FindElementByCssSelector("div.form-group-edit > div > div:nth-child(3) > label");}
    public Element getDatasetCheckbox() {return driver.FindElementByCssSelector("div.form-group-edit > div > div:nth-child(11) > label");}
    public Element getDatasetCheckStatus() {return driver.FindElementByCssSelector("div.form-group-edit > div > div:nth-child(11) > label > input");}
    public Element getIngestionCheckStatus() {return driver.FindElementByCssSelector("div.form-group-edit > div > div:nth-child(3) > label > input");}
    public Element getSaveBtnEditUser() {return driver.FindElementByCssSelector("#btnsubmit");}
  
    
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
    public Element getICEProjectStatuslabel(){return driver.FindElementByCssSelector("#hdrICEStatus");}
    public Element getICEProjectStatus() {return driver.FindElementByCssSelector("#ProjectDataTable > tbody > tr > td:nth-child(5)");}
      public Element searchUserBox() {return driver.FindElementByCssSelector("#txtsearchUser");}
      public Element searchUserApplyBtn() {return driver.FindElementByCssSelector("#btnAppyFilter");}
      public Element editSAUserBtn() {return driver.FindElementByCssSelector("#dtUserList > tbody > tr > td:nth-child(9) > a:nth-child(1)");}
      public Element getDatasetOptionInFunctionalityTab() {return driver.FindElementByXPath("//*[@id='s2']/div/div[1]/div/div/div[11]/label");}
      public Element getDatasetOptionInFunctionalityTabCheck() {return driver.FindElementByXPath("//*[@id='s2']/div/div[1]/div/div/div[11]/label/i");}
      public Element getEditUserCancelBtn() {return driver.FindElementByCssSelector("#submit");}
     
    
       //Annotation Layer added successfully
    public ManageUsersPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"User/UserListView");
        driver.waitForPageToBeReady();

    }

    public void AddUserstoProject(String projectname) throws InterruptedException {
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUserButton().Visible()  ;}}), Input.wait30); 
    	getAssignUserButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getProjectsTab().Visible()  ;}}), Input.wait30); 
    	getProjectsTab().Click();
    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectProjectName().Visible()  ;}}), Input.wait30); 
    	getSelectProjectName().selectFromDropdown().selectByVisibleText(projectname);
    	
    	Thread.sleep(2000); //app behaviour 
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_ProjectAdmin1().Visible()  ;}}), Input.wait30); 
    	
    	getAssignUser_ProjectAdmin1().waitAndClick(5);//.selectFromDropdown().selectByVisibleText(Input.pa1FullName);
    	getAssignUser_ProjectAdmin2().waitAndClick(5);;//.selectFromDropdown().selectByVisibleText(Input.pa2FullName);
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_Role().Visible()  ;}}), Input.wait30); 
    	getAssignUser_Role().selectFromDropdown().selectByVisibleText("Project Administrator");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_AddInList().Visible()  ;}}), Input.wait30); 
    	getAssignUser_AddInList().Click();  
    	Thread.sleep(2000);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_RMU1().Visible()  ;}}), Input.wait30); 
    	getAssignUser_RMU1().Click();
    	getAssignUser_RMU2().Click();
    	Thread.sleep(2000); //app behaviour
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_Role().Visible()  ;}}), Input.wait30); 
    	getAssignUser_Role().selectFromDropdown().selectByVisibleText("Review Manager");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_SecurityGroup().Visible()  ;}}), Input.wait30); 
    	getAssignUser_SecurityGroup().selectFromDropdown().deselectAll();
      	getSelectSecurityGroup().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_AddInList().Visible()  ;}}), Input.wait30); 
    	getAssignUser_AddInList().Click();
    	getAssignUser_AddInList().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_Reviewer1().Visible()  ;}}), Input.wait30); 
    	getAssignUser_Reviewer1().Click();
    	getAssignUser_Reviewer2().Click();
        
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_Role().Visible()  ;}}), Input.wait30); 
    	getAssignUser_Role().selectFromDropdown().selectByVisibleText("Reviewer");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_SecurityGroup().Visible()  ;}}), Input.wait30); 
    	getAssignUser_SecurityGroup().selectFromDropdown().deselectAll();
      	getSelectSecurityGroup().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_AddInList().Visible()  ;}}), Input.wait30); 
    	getAssignUser_AddInList().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignUser_Save().Visible()  ;}}), Input.wait30); 
    	getAssignUser_Save().Click();
    	
    	
    	successMsgConfirmation("User Mapping Successful");
           
	}
    
  public void BulkUserAccess(String projectname) throws InterruptedException {
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBulkUserAccessButton().Visible()  ;}}), Input.wait30); 
    	getBulkUserAccessButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBulkUserRole().Visible()  ;}}), Input.wait30); 
    	getBulkUserRole().selectFromDropdown().selectByVisibleText("Project Administrator");
    	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getBulkUserProject().Visible()  ;}}), Input.wait30); 
    	getBulkUserProject().selectFromDropdown().selectByVisibleText(projectname);
    	Thread.sleep(3000);
    	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getBulkIngestion().Visible()  ;}}), Input.wait30); 
    	getBulkIngestion().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBulkProduction().Visible()  ;}}), Input.wait30); 
    	getBulkProduction().Click();
   
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBulkEnableButton().Visible()  ;}}), Input.wait30); 
     	getBulkEnableButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBulkUserList().Visible()  ;}}), Input.wait30); 
    	getBulkUserList().Click();
   
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBulkUserSave().Visible()  ;}}), Input.wait30); 
    	getBulkUserSave().Click();
    	
    	successMsgConfirmation("Access rights applied successfully");
           
	}
    
    public void successMsgConfirmation(String msg) {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSuccessMsgHeader().Visible()  ;}}), Input.wait60); 
    	Assert.assertEquals("Success !", getSuccessMsgHeader().getText().toString());
    	Assert.assertEquals(msg, getSuccessMsg().getText().toString());
    	System.out.println("Success msg passed");
    	UtilityLog.info("Success msg passed");
	}
   
    public void getuserandsearch(String username)
    {
    	driver.FindElementByCssSelector("#LeftMenu > li > a").Click();
    	driver.FindElementByCssSelector("#LeftMenu > li > ul > li > a").WaitUntilPresent().Click();
    	driver.waitForPageToBeReady();
    	driver.FindElementByCssSelector("#txtsearchUser").WaitUntilPresent().SendKeys(username);
    	driver.FindElementByCssSelector("#btnAppyFilter").Click();
    	driver.waitForPageToBeReady();
       	}
    	
    public Element getUserListbyUserName(String username, String projectname)
    {
    	driver.FindElementByCssSelector("#LeftMenu > li > a").Click();
    	driver.FindElementByCssSelector("#LeftMenu > li > ul > li > a").WaitUntilPresent().Click();
    	driver.waitForPageToBeReady();
    	driver.FindElementByCssSelector("#txtsearchUser").WaitUntilPresent().SendKeys(username);
    	driver.FindElementByCssSelector("#btnAppyFilter").Click();
    	driver.waitForPageToBeReady();
    	ElementCollection pages = driver.FindElementsByCssSelector("#dtUserList_paginate > ul > li");
    	for(int i = 0;i<pages.size()-2;i++)
    	{
    		
    		ElementCollection projects = driver.FindElementsByCssSelector("#dtUserList > tbody > tr");
    		for(Element project: projects)
    		{
    			project.ScrollTo();
    			if(project.FindElementBycssSelector("td:nth-child(6)").getText().equalsIgnoreCase(Input.ICEProjectName))
    			{
    				
    				
    				return project;
    			}
    		}
    		driver.FindElementByCssSelector("#dtUserList_paginate > ul > li:nth-child(4) > a").Click();
    		driver.waitForPageToBeReady();
    		driver.scrollPageToTop();
    	}
    	return null;
    	
    }
    	   	
   
    

    public Element getUserList(String username)
    {
    	driver.FindElementByCssSelector("#LeftMenu > li > a").Click();
    	driver.FindElementByCssSelector("#LeftMenu > li > ul > li > a").WaitUntilPresent().Click();
    	driver.waitForPageToBeReady();
    	driver.FindElementByCssSelector("#txtsearchUser").WaitUntilPresent().SendKeys(username);
    	driver.FindElementByCssSelector("#btnAppyFilter").Click();
    	driver.waitForPageToBeReady();
    	Element project = driver.FindElementByCssSelector("#dtUserList > tbody > tr");
    	return project;
      }
 
 
    public Element getProjectListByProjectName(String projectname) throws InterruptedException
    {
    	driver.FindElementByCssSelector("#LeftMenu > li > a").WaitUntilPresent().Click();
		driver.FindElementByCssSelector("#LeftMenu > li.active > ul > li:nth-child(2) > a").WaitUntilPresent().Click();
		driver.waitForPageToBeReady();
		driver.FindElementByCssSelector("#txtProjectLabel").WaitUntilPresent().setText(Input.ICEProjectName);
		driver.FindElementByCssSelector("#btnFilter").WaitUntilPresent().Click();
		Thread.sleep(2000);
		
    	return null;
    }
    

    public void BulkUserAccessDataset(String projectname) throws InterruptedException {
  		
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getBulkUserAccessButton().Visible()  ;}}), Input.wait30); 
      	getBulkUserAccessButton().Click();
      	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getBulkUserRole().Visible()  ;}}), Input.wait30); 
      	getBulkUserRole().selectFromDropdown().selectByVisibleText("Project Administrator");
      	
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getBulkUserProject().Visible()  ;}}), Input.wait30); 
      	getBulkUserProject().selectFromDropdown().selectByVisibleText(projectname);
      	Thread.sleep(3000);
      	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			 getBulkDataset().Visible()  ;}}), Input.wait30); 
      	getBulkDataset().Click();
      	
      	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getBulkUserList().Visible()  ;}}), Input.wait30); 
      	getBulkUserList().Click();
     
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getBulkUserSave().Visible()  ;}}), Input.wait30); 
      	getBulkUserSave().Click();
      	
      	successMsgConfirmation("Access rights applied successfully");
             
  	}
    
 public boolean isBulkUserAccessDatasetGreyedForReview(String projectname) throws InterruptedException {
  		
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getBulkUserAccessButton().Visible()  ;}}), Input.wait30); 
      	getBulkUserAccessButton().Click();
      	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getBulkUserRole().Visible()  ;}}), Input.wait30); 
      	getBulkUserRole().selectFromDropdown().selectByVisibleText("Reviewer");
      	
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getBulkUserProject().Visible()  ;}}), Input.wait30); 
      	getBulkUserProject().selectFromDropdown().selectByVisibleText(projectname);
      	Thread.sleep(3000);
      	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			 getBulkDataset().Visible()  ;}}), Input.wait30); 
      	if(getBulkDataset().GetAttribute("style").contains("grey"))
      	{
      		getBulkCancelBtn().Click();
      		return true;
      	}else
      	{
      		getBulkCancelBtn().Click();
      		return false;
      	}
      	
 }
 
 }
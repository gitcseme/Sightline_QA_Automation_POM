package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;

import automationLibrary.Driver;
import automationLibrary.Element;
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
    public Element getBulkUserList(){ return driver.FindElementByXPath("//*[@id='divBulkUserList']//label[contains(text(),'"+Input.pa1FullName+"')]"); }
    public Element getBulkUserSave(){ return driver.FindElementById("btnSaveBulkAccessControls"); }
    
    
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
  
    
       //Annotation Layer added successfully
    public ManageUsersPage(Driver driver){

        this.driver = driver;
//        this.driver.getWebDriver().get(Input.url+"User/UserListView");
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
   
 
 }
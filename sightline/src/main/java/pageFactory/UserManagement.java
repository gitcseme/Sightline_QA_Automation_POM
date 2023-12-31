package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class UserManagement {
	   Driver driver;
	   BaseClass bc;
	   SoftAssert softAssertion;
	   
	   public Element getAddUserBtn(){ return driver.FindElementById("addNewUser"); }
	    public Element getFirstName(){ return driver.FindElementByXPath("//*[@tabindex='1']"); }
	    public Element getLastName(){ return driver.FindElementByXPath("//*[@tabindex='2']"); }
	    public Element getSelectRole(){ return driver.FindElementByXPath("//*[@tabindex='3']"); }
	    public Element getEmail(){ return driver.FindElementByXPath("//*[@tabindex='4']"); }
	    public Element getSelectLanguage(){ return driver.FindElementByXPath("//*[@tabindex='6']"); }
	    public Element getSelectDomain(){ return driver.FindElementByXPath("//*[@tabindex='8']"); }
	    public Element getSelectProject(){ return driver.FindElementByXPath("//*[@tabindex='7']"); }
	    public Element getSecurityGroup(){ return driver.FindElementByXPath("(//*[@tabindex='8'])"); }
	    public Element getSave(){ return driver.FindElementById("SaveUser"); }
	    
	    //set password
	    public Element getSetPassword(){ return driver.FindElementById("NewPassword"); }
	    public Element getConfirmPassword(){ return driver.FindElementById("ConfirmPassword"); }
	    public Element getSavePassword(){ return driver.FindElementById("btnSubmit"); }
	    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
	    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
	   
	    //delete user
	    public Element getManageFilterByDate(){ return driver.FindElementByXPath("//input[@name='startdate']"); }
	    public Element getFilerApplyBtn(){ return driver.FindElementById("btnAppyFilter"); }
	    public ElementCollection getRowsInTable(){ return driver.FindElementsByXPath("//*[@id='dtUserList']/tbody/tr"); }
	    public Element getConfirmDelete(){ return driver.FindElementByXPath("//button[@id='bot2-Msg1']"); }
	   
	    //edit user
	    public Element getFunctionalityTab(){ return driver.FindElementByLinkText("Functionality"); }
	    public Element getManageproject(){ return driver.FindElementByXPath("//*[@id='s2']//label[contains(.,'Manage Project')]/i"); }
	    public Element getSubmitChanges(){ return driver.FindElementById("/btnsubmit"); }
	
	    //Search user 
	    public Element getUserNameFilter(){ return driver.FindElementById("txtsearchUser"); }
	    public ElementCollection getNumberofUsers(){ return driver.FindElementsByXPath("//*[@id='dtUserList']/tbody/tr/td[1]"); }
	    public Element getUserFirstName(int row){ return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+row+"]/td[1]"); }
	    public Element getUserLastName(int row){ return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+row+"]/td[2]"); }
	    public Element getUserState(int row){ return driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+row+"]/td[4]"); }
	    public Element getActiveInactiveBtn(){ return driver.FindElementById("activeUserObject"); }
	    public Element getSelectRoleToFilter(){ return driver.FindElementById("ddlRoles"); }
	    //Functionality tab
	    public Element getManageRole_Functionality_Manage(){ return driver.FindElementByXPath("//*[@id='s2']//input[@id='UserRights_CanManage']/following-sibling::i"); }
	    public Element getManageRole_Functionality_ManageDomain(){ return driver.FindElementByXPath("//*[@id='s2']//label[contains(.,'Manage Domain Projects')]/i"); }
	    public Element getManageRole_Functionality_Searching(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Searching')]/i"); }
	    public Element getManageRole_Functionality_ConceptExplr(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Concept Explorer')]/i"); }
	    public Element getManageRole_Functionality_CommExplr(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Communications Explorer')]/i"); }
	    public Element getManageRole_Functionality_Categorize(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Categoriz')]/i"); }
	    public Element getManageRole_Functionality_AnalyticPanel(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Analytics Panels')]/i"); }
	    public Element getManageRole_Functionality_Native(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Download Native')]/i"); }
	    public Element getManageRole_Functionality_AllReport(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'All Reports')]/i"); }
	    public Element getManageRole_Functionality_Production(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Productions')]/i"); }
	    public Element getManageRole_Functionality_Ingestion(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Ingestions')]/i"); }
	    public Element getManageRole_Functionality_Dataset(){ return driver.FindElementByXPath("//*[@id='s2']//label[contains(.,'Datasets')]/i"); }
	    public Element getManageRole_Functionality_Redactions(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Redactions')]/i"); }
	    public Element getManageRole_Functionality_Highlighting(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Highlighting')]/i"); }
	    public Element getManageRole_Functionality_Remarks(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Reviewer Remarks')]/i"); }
	    public Element getManageRole_Functionality_Datasetcheckbox(){ return driver.FindElementByXPath(".//*[@id='s2']//label[contains(.,'Concept Explorer')]/input[1]"); }
	    
	    //attorney profile for rmu
	    public Element getAttorneyprofilecheckbox(){ return driver.FindElementByXPath("//*[@id='IsAttorneyProfile']/ancestor::label"); }
	    //Assign User Objects
	    public Element getAssignUserButton(){ return driver.FindElementById("btnAssign"); }
	    public Element getSelectDomainname(){ return driver.FindElementById("lstDomains"); }
	    public Element getSelectusertoassignindomain(){ return driver.FindElementById("UnAssignedUsersForDomain"); }
	    public Element getrightBtndomainuser(){ return driver.FindElementByXPath("//a[@id='btnRightUserMaappingForDomain']"); }
	    public Element getsavedomainuser(){ return driver.FindElementById("btnSave"); }
	    
	    //added by Narendra - Lock Account
	    public Element getEdit(int i){ return driver.FindElementByXPath("//table[@id='dtUserList']//tr["+i+"]/td[7]/a[contains(text(),'Edit')]"); }
	    public Element getIsLock(){ return driver.FindElementById("Ischklocked"); }
	    public Element getSubmit(){ return driver.FindElementById("btnsubmit"); }
	    public Element getCancel(){ return driver.FindElementById("submit"); }
	    public Element getLock(){ return driver.FindElementByXPath("//div[@class='tab-pane fade in active']//div[10]//div[1]//label[1]//i[1]"); }
	    public Element getRMUasPASecurityGroup(){ return driver.FindElementByXPath("(//*[@id='SysAdminSecGroup'][@tabindex='7'])"); }
	    public Element getRMUasRMUSecurityGroup(){ return driver.FindElementByXPath("(//*[@id='SysAdminSecGroup'][@tabindex='6'])"); }
	    
	    
	    
	   
	    public UserManagement(Driver driver){

	        this.driver = driver;
	        bc = new BaseClass(driver);
	        softAssertion= new SoftAssert(); 
	        this.driver.getWebDriver().get(Input.url+ "User/UserListView");
	    }
		 public void findUsers(String name,String role, String userSate) {
			 
			 if (userSate.equalsIgnoreCase("inactive")) {
				 driver.scrollPageToTop();
				 getActiveInactiveBtn().waitAndClick(10);
			}
		 getUserNameFilter().SendKeys(name);
		 getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
			getFilerApplyBtn().Click();
			   
			boolean nextPage= true;
			  
			   while(nextPage){
				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   getNumberofUsers().Visible()  ;}}),Input.wait30);
				   System.out.println("Number of records in a current page : "+getNumberofUsers().size());
				   System.out.println("Validation started..please wait");
					
				   for (int i = 1; i<getNumberofUsers().size();i++) {
					  if (userSate.endsWith("active")) {
						  Assert.assertTrue((getUserFirstName(i).getText().contains(name)||getUserLastName(i).getText().contains(name))&&getUserState(i).getText().equals("Active"));
							//System.out.println(getUserFirstName(i).getText()+" "+getUserLastName(i).getText() + "matched");	
						
					}else if (userSate.equalsIgnoreCase("inactive")) {
						Assert.assertTrue((getUserFirstName(i).getText().contains(name)||getUserLastName(i).getText().contains(name))&&(getUserState(i).getText().equals("Active")||getUserState(i).getText().equals("In Active")));
						//System.out.println(getUserFirstName(i).getText()+" "+getUserLastName(i).getText() + "matched");	
					
					}
						
					}
				   try{
					   driver.scrollingToBottomofAPage();
					   driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a")).isDisplayed();
					   nextPage = false;
				   }
				   catch (Exception e) {
					   driver.getWebDriver().findElement(By.linkText("Next")).click(); 
				   } 
					   
			   }
		
	}
	 
public void setPassword(String pwd) {
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getSetPassword().Visible() ;}}), Input.wait30);
		 getSetPassword().SendKeys(pwd);
		 getConfirmPassword().SendKeys(pwd);
		 getSavePassword().Click();
		 try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //successPasswordSet();
		 
		 
	}
	
 public void createUser(String firstName, String lastName, String role, String emailId, String domain, String project) {
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAddUserBtn().Visible() ;}}), Input.wait30);
		 getAddUserBtn().Click();
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getFirstName().Visible() ;}}), Input.wait30);
		 getFirstName().SendKeys(firstName);
		 getLastName().SendKeys(lastName);
		 getSelectRole().selectFromDropdown().selectByVisibleText(role);
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getSelectDomain().Visible() ;}}), Input.wait30);
		 if(role.equalsIgnoreCase("Domain Administrator")){
			 getSelectDomain().selectFromDropdown().selectByIndex(1);
		 }
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getEmail().Exists() ;}}), Input.wait30);
		 getEmail().SendKeys(emailId);
		 getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		 if(role.equalsIgnoreCase("Project Administrator")||role.equalsIgnoreCase("Review Manager")
					||role.equalsIgnoreCase("Reviewer")){
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getSelectProject().Visible() ;}}), Input.wait30);
		 //getSelectDomain().SendKeys(domain);
		 getSelectProject().selectFromDropdown().selectByVisibleText(project);
		 }
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getSecurityGroup().Visible() ;}}), Input.wait30);
		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(role.equalsIgnoreCase("Review Manager")
					||role.equalsIgnoreCase("Reviewer")){
		 getSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");;
		 
		 }
		 getSave().Click();
		 bc.VerifySuccessMessage("User profile was successfully created");
		 
	}
	//00AutoRev510105322
		 public void selectUserToEdit(String firstName) {
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getManageFilterByDate().Visible() ;}}), Input.wait30);
			 getManageFilterByDate().SendKeys(""+Keys.ENTER);
			 
			 getFilerApplyBtn().Click();
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/* driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getRowsInTable().Visible() ;}}), Input.wait30);*/
			 for (int i = 1; i <= getRowsInTable().size(); i++) {
				// System.out.println(driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+i+"]/td[1]").getText());
				 if(driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+i+"]/td[1]").getText().equals(firstName)){
					 driver.FindElementByXPath("//table[@id='dtUserList']//tr["+i+"]/td[8]/a[contains(text(),'Edit')]").Click();
					 break;
				 }
			}
			
		}
		 
		 public void modifyUserRights(String rightName, String value) {
			// TODO Auto-generated method stub
			 
			 if(rightName.equalsIgnoreCase("Manage Project") && value.equalsIgnoreCase("checkIn")){
				getManageproject().checkIn();
			 }
			 BaseClass bc= new BaseClass(driver);
			 getSubmitChanges().Click();
			 bc.VerifySuccessMessage("User profile was successfully modified");
			
		}
		 
	 //00AutoRev510105322
	 public void deleteUser(String firstName) {
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getManageFilterByDate().Visible() ;}}), Input.wait30);
		 getManageFilterByDate().SendKeys(""+Keys.ENTER);
		 
		 getFilerApplyBtn().Click();
		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getRowsInTable().Visible() ;}}), Input.wait30);*/
		 for (int i = 1; i <= getRowsInTable().size(); i++) {
			// System.out.println(driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+i+"]/td[1]").getText());
			 if(driver.FindElementByXPath("//*[@id='dtUserList']/tbody/tr["+i+"]/td[1]").getText().equals(firstName)){
				 driver.FindElementByXPath("//table[@id='dtUserList']//tr["+i+"]/td[8]/a[contains(text(),'Delete')]").Click();
				 break;
			 }
		}
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getConfirmDelete().Visible() ;}}), Input.wait30);
		 getConfirmDelete().waitAndClick(5);
		 bc.VerifySuccessMessage("User has been deactivated");

	}

     public void RMUUserwithAttorneyProfile(String firstName, String lastName, String role, String emailId, String domain, String project) {
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAddUserBtn().Visible() ;}}), Input.wait30);
		 getAddUserBtn().Click();
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getFirstName().Visible() ;}}), Input.wait30);
		 getFirstName().SendKeys(firstName);
		 getLastName().SendKeys(lastName);
		 getSelectRole().selectFromDropdown().selectByVisibleText(role);
		 if(role.equalsIgnoreCase("Review Manager"))
				 {
			 getAttorneyprofilecheckbox().WaitUntilPresent();
			 Assert.assertTrue(getAttorneyprofilecheckbox().Displayed());
			 getAttorneyprofilecheckbox().waitAndClick(10);
				 }
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getSelectDomain().Visible() ;}}), Input.wait30);
		 if(role.equalsIgnoreCase("Domain Administrator")){
			 getSelectDomain().selectFromDropdown().selectByIndex(1);
		 }
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getEmail().Exists() ;}}), Input.wait30);
		 getEmail().SendKeys(emailId);
		 getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		 if(role.equalsIgnoreCase("Project Administrator")||role.equalsIgnoreCase("Review Manager")
					||role.equalsIgnoreCase("Reviewer")){
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getSelectProject().Visible() ;}}), Input.wait30);
		 //getSelectDomain().SendKeys(domain);
		 getSelectProject().selectFromDropdown().selectByVisibleText(project);
		 }
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getSecurityGroup().Visible() ;}}), Input.wait30);
		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		 if(role.equalsIgnoreCase("Review Manager")
					||role.equalsIgnoreCase("Reviewer")){
		 getSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");;
		 
		 }
		 getSave().Click();
		 bc.VerifySuccessMessage("User profile was successfully created");
				 
	}
	 
	    public void checkUserRights(String role) {
			 
			 if(role.equalsIgnoreCase("Review Manager")){
				String a = getManageRole_Functionality_Dataset().GetAttribute("value");
				System.out.println(a);
				
				 if(getManageRole_Functionality_Dataset().GetAttribute("value").contains("diabled"))
						 System.out.println("Test passed");
				 else
					 System.out.println("Test failed");
			// Assert.assertFalse(getManageRole_Functionality_Dataset().GetAttribute("value"));
			 Assert.assertFalse(getManageRole_Functionality_ConceptExplr().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_CommExplr().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_Categorize().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_AnalyticPanel().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_AllReport().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_Native().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_Production().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_Ingestion().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_Dataset().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_Redactions().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_Highlighting().Enabled());
			 Assert.assertFalse(getManageRole_Functionality_Remarks().Enabled());
			 }
	}
	    
	    public void Assignusertodomain(String clientname) {
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getAssignUserButton().Visible() ;}}), Input.wait30);
			 getAssignUserButton().Click();
			 
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getSelectDomainname().Visible() ;}}), Input.wait30);
			 getSelectDomainname().selectFromDropdown().selectByVisibleText(clientname);
			 
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getSelectusertoassignindomain().Visible() ;}}), Input.wait30);
			 getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText("0QADA1 DA1");
			 
			getrightBtndomainuser().waitAndClick(10);
			 
			getsavedomainuser().waitAndClick(10);
			 
	    }		 

	    //added by Narendra
	    
	    public void lockAccount(String name,String role, String userSate) throws InterruptedException {
	    	 
	    	 if (userSate.equalsIgnoreCase("inactive")) {
				 driver.scrollPageToTop();
				 getActiveInactiveBtn().waitAndClick(10);
			}
		    	 getUserNameFilter().SendKeys(name);
		    	 getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		    	 getFilerApplyBtn().Click();
			   
		    	 boolean nextPage= true;
		    	 
		    	 while(nextPage){
				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   getNumberofUsers().Visible()  ;}}),Input.wait30);
				   System.out.println("Number of records in a current page : "+getNumberofUsers().size());
				   System.out.println("Validation started..please wait");
					
				   for (int i = 1; i<getNumberofUsers().size();i++) {
					  if (userSate.endsWith("active")) {
						  getEdit(i).waitAndClick(10);
						  Thread.sleep(3000);
						  if(getIsLock().GetAttribute("checked") != null)
						  {
							  System.out.println("Already Cheacked"); 
							  getCancel().waitAndClick(10);
						  }
						  else
						  {
							  getIsLock().Click(); 
							  getSubmit().waitAndClick(10);
						  }
						  
					}else if (userSate.equalsIgnoreCase("inactive")) {
						
							System.out.println("Users are Inactive");	
					
					}
						
					}
				   
				   try{
					   driver.scrollingToBottomofAPage();
					   driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a")).isDisplayed();
					   nextPage = false;
				   }
				   catch (Exception e) {
					   driver.getWebDriver().findElement(By.linkText("Next")).click(); 
				   } 
					   
			   }
	    }
	    
	    public void UnlockAccount(String name,String role, String userSate) throws InterruptedException {
	    	 
	    	 if (userSate.equalsIgnoreCase("inactive")) {
				 driver.scrollPageToTop();
				 getActiveInactiveBtn().waitAndClick(10);
			}
		    	 getUserNameFilter().SendKeys(name);
		    	 getSelectRoleToFilter().selectFromDropdown().selectByVisibleText(role);
		    	 getFilerApplyBtn().Click();
			   
		    	 boolean nextPage= true;
		    	 
		    	 while(nextPage){
				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   getNumberofUsers().Visible()  ;}}),Input.wait30);
				   System.out.println("Number of records in a current page : "+getNumberofUsers().size());
				   System.out.println("Validation started..please wait");
					
				   for (int i = 1; i<getNumberofUsers().size();i++) {
					  if (userSate.endsWith("active")) {
						  getEdit(i).waitAndClick(10);
						  Thread.sleep(3000);
						  if(getIsLock().GetAttribute("checked") != null)
						  {
							  getLock().Click(); 
							  getSubmit().waitAndClick(10);
							  System.out.println("Account is unlocked now");
						  }
						  else
						  {
							  System.out.println("Already unlocked"); 
							  getCancel().waitAndClick(10);
						  }
						  
					}else if (userSate.equalsIgnoreCase("inactive")) {
						
							System.out.println("Users are Inactive");	
					
					}
						
					}
				   
				   try{
					   driver.scrollingToBottomofAPage();
					   driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a")).isDisplayed();
					   nextPage = false;
				   }
				   catch (Exception e) {
					   driver.getWebDriver().findElement(By.linkText("Next")).click(); 
				   } 
					   
			   }
	    }
		
	    public void RMUUserAttorneyasPA(String firstName, String lastName, String role, String emailId, String domain) {
			 
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getAddUserBtn().Visible() ;}}), Input.wait30);
			 getAddUserBtn().Click();
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getFirstName().Visible() ;}}), Input.wait30);
			 getFirstName().SendKeys(firstName);
			 getLastName().SendKeys(lastName);
			 getSelectRole().selectFromDropdown().selectByVisibleText(role);
			 if(role.equalsIgnoreCase("Review Manager"))
					 {
				 getAttorneyprofilecheckbox().WaitUntilPresent();
				 Assert.assertTrue(getAttorneyprofilecheckbox().Displayed());
				 getAttorneyprofilecheckbox().waitAndClick(10);
					 }
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getSelectDomain().Visible() ;}}), Input.wait30);
			 if(role.equalsIgnoreCase("Domain Administrator")){
				 getSelectDomain().selectFromDropdown().selectByIndex(1);
			 }
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getEmail().Exists() ;}}), Input.wait30);
			 getEmail().SendKeys(emailId);
			 getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
			 if(role.equalsIgnoreCase("Project Administrator")||role.equalsIgnoreCase("Review Manager")
						||role.equalsIgnoreCase("Reviewer")){
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getSelectProject().Visible() ;}}), Input.wait30);
			 //getSelectDomain().SendKeys(domain);
			 //getSelectProject().selectFromDropdown().selectByVisibleText(project);
			 }
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getRMUasPASecurityGroup().Visible() ;}}), Input.wait30);
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			 if(role.equalsIgnoreCase("Review Manager")
						||role.equalsIgnoreCase("Reviewer")){
				 getRMUasPASecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");;
			 
			 }
			 getSave().Click();
			 bc.VerifySuccessMessage("User profile was successfully created");
					 
		}
	    
	    public void RMUUserAttorneyasRMU(String firstName, String lastName, String role, String emailId, String domain) {
			 
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getAddUserBtn().Visible() ;}}), Input.wait30);
			 getAddUserBtn().Click();
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getFirstName().Visible() ;}}), Input.wait30);
			 getFirstName().SendKeys(firstName);
			 getLastName().SendKeys(lastName);
			 getSelectRole().selectFromDropdown().selectByVisibleText(role);
			 if(role.equalsIgnoreCase("Review Manager"))
					 {
				 getAttorneyprofilecheckbox().WaitUntilPresent();
				 Assert.assertTrue(getAttorneyprofilecheckbox().Displayed());
				 getAttorneyprofilecheckbox().waitAndClick(10);
					 }
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getSelectDomain().Visible() ;}}), Input.wait30);
			 if(role.equalsIgnoreCase("Domain Administrator")){
				 getSelectDomain().selectFromDropdown().selectByIndex(1);
			 }
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getEmail().Exists() ;}}), Input.wait30);
			 getEmail().SendKeys(emailId);
			 getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
			 if(role.equalsIgnoreCase("Project Administrator")||role.equalsIgnoreCase("Review Manager")
						||role.equalsIgnoreCase("Reviewer")){
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getSelectProject().Visible() ;}}), Input.wait30);
			 //getSelectDomain().SendKeys(domain);
			 //getSelectProject().selectFromDropdown().selectByVisibleText(project);
			 }
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getRMUasRMUSecurityGroup().Visible() ;}}), Input.wait30);
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			 if(role.equalsIgnoreCase("Review Manager")
						||role.equalsIgnoreCase("Reviewer")){
				 getRMUasRMUSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");;
			 
			 }
			 getSave().Click();
			 bc.VerifySuccessMessage("User profile was successfully created");
					 
		}
	    
	   
}

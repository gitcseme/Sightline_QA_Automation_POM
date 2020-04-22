package pageFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;


public class BaseClass {

    Driver driver;
    public static String tooltipmsg;
    SoftAssert softAssertion;
  
    public Element getBGnotificationCount1(){ return driver.FindElementByXPath("//b[@class='badge']"); }
    public Element getBGnotificationCount2(){ return driver.FindElementByXPath("//b[@class='badge bg-color-red bounceIn animated']"); }
    public Element getSignoutMenu(){ return driver.FindElementByXPath("//*[@id='user-selector']"); }
    public Element getChangeRole(){ return driver.FindElementByXPath("//*[@id='utility-group']//a[text()='Change Role']"); }
    public Element getSelectRole(){ return driver.FindElementByXPath("//select[@name='Role']"); }
    public Element getSelectDomain(){ return driver.FindElementById("ddlSysAdminChangeRoleDomain"); }
    public Element getSelectProjectTo(){ return driver.FindElementById("ddlSysAdminChangeRoleProj"); }
    
    public Element getSelectSecurityGroup(){ return driver.FindElementByXPath("//select[@name='SecurityGroupID']"); }
    public Element getSaveChangeRole(){ return driver.FindElementByXPath("//input[@type='submit']"); }
     
    
    public Element getContinueBtn(){ return driver.FindElementByXPath("//button[contains(.,'Continue')]"); }
  
    //select project
    public Element getProjectNames(){ return driver.FindElementById("project-selector"); }
    public Element getSelectProject(){ return driver.FindElementByXPath("//a[@title='"+Input.projectName+"']"); }
   	public Element getPopupYesBtn(){ return driver.FindElementByXPath("//button[@id='btnYes']"); }
   
    //background task
    public Element getBackgroundTask_Button(){ return driver.FindElementByXPath("//*[@id='activity']/i"); }
    public Element getBckTask_SelectAll(){ return driver.FindElementById("btnViewAll"); }
    public Element getBackgroundTask_Message(String texttosearch){ return driver.FindElementByXPath("(//span[contains(text(),'"+texttosearch+"')])[1]"); }
    
    //success message
    public Element getCloseSucessmsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//i"); }

   
    
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
    public Element getYesBtn(){ return driver.FindElementByXPath("//button[@id='bot1-Msg1']"); } 
    //select domain
    public Element getSelectdomaindropdown(String domainname){ return driver.FindElementByXPath("//a[@title='"+domainname+"']"); }
    //select security group
    public Element getsgNames(){ return driver.FindElementById("SecurityGroup-selector"); }
    public Element getSelectsg(String sgname){ return driver.FindElementByXPath("//a[@title='"+sgname+"']"); }
    public Element getCancelbutton(){ return driver.FindElementByXPath("//button[contains(text(),'Cancel')]"); }
    public Element getNOBtn(){ return driver.FindElementByXPath("//button[@id='bot2-Msg1']"); } 
   
    public Element getAvlDomain(){ return driver.FindElementById("ddlAvailableDomains"); }
    public Element getAvlProject(){ return driver.FindElementById("ddlAvailableProjects"); }
  
    
    public BaseClass(Driver driver){

        this.driver = driver;
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);
        softAssertion= new SoftAssert(); 
    }
    
    public int initialBgCount() {
    	int bgCount = 0;	
    	try{
		bgCount = Integer.parseInt(getBGnotificationCount1().getText());
    	}catch (Exception e) {
    		bgCount = Integer.parseInt(getBGnotificationCount2().getText());
		}
    	
    	//System.out.println(bgCount+" - BGcount");
    	
    	return bgCount;
	}

    public void impersonatePAtoRMU() throws InterruptedException {
    	getSignoutMenu().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	   	getChangeRole().Visible()  ;}}),Input.wait60);
    	getChangeRole().Click();
  	    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getSelectRole().Visible()  ;}}),Input.wait60);
    	getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAvlDomain().Visible()  ;}}),Input.wait30);
    	getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
    
    	Thread.sleep(3000);
	
    	getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
    	Thread.sleep(2000);
        	
    	getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
    	getSaveChangeRole().waitAndClick(10);
    	System.out.println("Impersnated from PA to RMU");

	}
    
    public void impersonateRMUtoReviewer() throws InterruptedException {
    	getSignoutMenu().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	   	getChangeRole().Visible()  ;}}),Input.wait60);
    	getChangeRole().Click();
  	    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getSelectRole().Visible()  ;}}),Input.wait60);
    	getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAvlDomain().Visible()  ;}}),Input.wait30);
    	getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
    
    	Thread.sleep(3000);
	
    	getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
    	Thread.sleep(2000);
    	
    	getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
    	getSaveChangeRole().waitAndClick(5);
    	System.out.println("Impersnated from RMU to Reviewer");
	}
    
    
    public void impersonateSAtoDA(String domain) {
    	getSignoutMenu().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	   	getChangeRole().Visible()  ;}}),Input.wait30);
    	getChangeRole().Click();
  	    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getSelectRole().Visible()  ;}}),Input.wait30);
    	getSelectRole().selectFromDropdown().selectByVisibleText("Domain Administrator");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectDomain().Visible()  ;}}),Input.wait30);
    	if(domain.equalsIgnoreCase("Any"))
    		getSelectDomain().selectFromDropdown().selectByIndex(1);
    	else
    		getSelectDomain().selectFromDropdown().selectByVisibleText(domain);
    	getSaveChangeRole().waitAndClick(5);
    	System.out.println("Impersnated from RMU to Reviewer");
	}
    
    public void impersonateSAtoPA() throws InterruptedException {
    	getSignoutMenu().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	   	getChangeRole().Visible()  ;}}),Input.wait30);
    	getChangeRole().Click();
  	    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getSelectRole().Visible()  ;}}),Input.wait30);
    	getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");
    	    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAvlDomain().Visible()  ;}}),Input.wait30);
    	getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
    
    	Thread.sleep(3000);
	
    	getSelectProjectTo().selectFromDropdown().selectByVisibleText(Input.projectName);
    	
    	getSaveChangeRole().waitAndClick(5);
    	System.out.println("Impersnated from SA to PA");
	}
    public void selectproject() {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProjectNames().Visible()  ;}}), 10000);
		driver.scrollPageToTop();
		//Select project if required one is not seletced
		getProjectNames().waitAndClick(5);
		  
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectProject().Visible()  ;}}), 10000);
		getSelectProject().waitAndClick(5);
		driver.waitForPageToBeReady();
	
	}
    
    public void BckTaskMessageverify(String texttosearch) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBackgroundTask_Button().Visible()  ;}}),Input.wait30);
    	getBackgroundTask_Button().Click();
  	    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBackgroundTask_Message(texttosearch).Visible()  ;}}),Input.wait60);
    	String exptext = getBackgroundTask_Message(texttosearch).getText().toString();
    	System.out.println(exptext);
    	
     	Assert.assertTrue(exptext.contains(texttosearch));
   	}
    
    public void CloseSuccessMsgpopup() {
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getCloseSucessmsg().Exists() ;}}), Input.wait30);
		try{
		getCloseSucessmsg().Click();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
    
     public void yesPopUp() {
    	try{
	   		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   				getPopupYesBtn().Visible() ;}}),Input.wait30);
	   		getPopupYesBtn().Click();  
	   	
	    	}catch (Exception e) {
				// TODO: handle exception
		}

	}
     public void VerifySuccessMessage(String ExpectedMsg) {
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getSuccessMsgHeader().Visible()  ;}}), Input.wait30); 
      	Assert.assertEquals("Success !", getSuccessMsgHeader().getText().toString());
      	Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
  	}
     
     public void VerifyWarningMessage(String ExpectedMsg) {
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSuccessMsgHeader().Visible()  ;}}), Input.wait30); 
        	Assert.assertEquals("Warning !", getSuccessMsgHeader().getText().toString());
        	Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
    	}
     
     public void VerifyErrorMessage(String ExpectedMsg) {
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getSuccessMsgHeader().Visible()  ;}}), Input.wait30); 
     	Assert.assertEquals("Error !", getSuccessMsgHeader().getText().toString());
     	Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
 	}
  
     
     public void SelectCurrentdatfromDatePicker(Element DateFrom, Element dateWidget) {
    	 
  	   //Get Today's number
		   String  today = getCurrentDay();
	       System.out.println("Today's number: " + today + "\n");
	 
	        //Click and open the datepickers
	        DateFrom.waitAndClick(10);
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	      
	        //This is from date picker table
	        WebElement dateWidgetFrom = dateWidget.getWebElement();
	 
	        //This are the rows of the from date picker table
	        //List<WebElement> rows = dateWidgetFrom.findElements(By.tagName("tr"));
	 
	        //This are the columns of the from date picker table
	        List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));
	 
	        //DatePicker is a table. Thus we can navigate to each cell
	        //and if a cell matches with the current date then we will click it.
	        for (WebElement cell: columns) {
	            /*
	            //If you want to click 18th Date
	            if (cell.getText().equals("18")) {
	            */
	            //Select Today's Date
	            if (cell.getText().equals(today)) {
	                cell.click();
	                break;
	            }
	        }
	          try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }	  

        //Get The Current Day
          private String getCurrentDay () {
         //Create a Calendar Object
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

         //Get Current Day as a number
         int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
         System.out.println("Today Int: " + todayInt +"\n");

         //Integer to String Conversion
          String todayStr = Integer.toString(todayInt);
           System.out.println("Today Str: " + todayStr + "\n");

          return todayStr;
}
          
          public void impersonateDAtoPA() {
          	getSignoutMenu().Click();
          	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
          	   	getChangeRole().Visible()  ;}}),Input.wait30);
          	getChangeRole().Click();
        	    
          	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSelectRole().Visible()  ;}}),Input.wait30);
          	getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");
          	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
          			getSelectProjectTo().Visible()  ;}}),Input.wait30);
          	
          		getSelectProjectTo().selectFromDropdown().selectByVisibleText(Input.projectName);
          	getSaveChangeRole().waitAndClick(5);
          	System.out.println("Impersnated from DA to PA");
      	}
  	 
          public void selectdomain(final String domain) {
      		driver.scrollPageToTop();
      		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      				getProjectNames().Visible()  ;}}), 10000);
      		driver.scrollPageToTop();
      		getProjectNames().waitAndClick(5);
      		  
          	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
          			getSelectdomaindropdown(domain).Visible()  ;}}), 10000);
          	getSelectdomaindropdown(domain).waitAndClick(5);
      		driver.waitForPageToBeReady();
      	}
          
          public void GetandVerifyTooltip(Element element,String TextToValidate) {
          
          	Actions builder = new Actions(driver.getWebDriver());
  			builder.clickAndHold(element.getWebElement()).perform();
  			try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  			String tooltipmsg = element.GetAttribute("title");
  			System.out.println("Tooltip/ Help message is "+tooltipmsg);
  			softAssertion.assertEquals(TextToValidate, tooltipmsg);
  			softAssertion.assertAll();
      	}
          public void CompareListStrings(List<WebElement> list1,List<WebElement> list2)
          {
          
            List<String> a1 = new ArrayList<String>();
            List<String> a2 = new ArrayList<String>();
      		  
 		   for(int i=1;i<=a1.size();i++)
 		   {
 			 a1.add(list1.get(i).getText());
 			 a2.add(list2.get(i).getText());
 			 a1.addAll(a1);
 			 a2.addAll(a2);
 			 if(a1.equals(a2)) {
 				   System.out.println("Pass");
 			   }
 			   else {
 				   System.out.println("FAIL");
 			   }
 		   }
          } 	   
 
          public void selectsecuritygroup(final String sgname) {
      		driver.scrollPageToTop();
      		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      				getsgNames().Visible()  ;}}), 10000);
      		driver.scrollPageToTop();
      		getsgNames().waitAndClick(5);
      		  
          	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
          			getSelectsg(sgname).Visible()  ;}}), 10000);
          	getSelectsg(sgname).waitAndClick(5);
      		driver.waitForPageToBeReady();
      	}
          
        public String getcurrentdateinGMT() throws ParseException
        {
        	//Time in GMT
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
           
            //Local time zone   
            SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            System.out.println(dateFormatLocal.parse( dateFormatGmt.format(new Date())));
            
            String Time = dateFormatGmt.format(new Date()).toString();
            System.out.println(Time);
        	return Time;
        	
        	
        }
        
        
        public void impersonatePAtoRMU_SelectedSG(String sgname) {
        	getSignoutMenu().waitAndClick(10);
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        	   	getChangeRole().Visible()  ;}}),Input.wait60);
        	getChangeRole().Click();
      	    
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getSelectRole().Visible()  ;}}),Input.wait60);
        	getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAvlDomain().Visible()  ;}}),Input.wait30);
        	getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
        	
        	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
        	
        	getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(sgname);
        	getSaveChangeRole().waitAndClick(10);
        	System.out.println("Impersnated from PA to RMU");

    	}
        
        public void impersonateSAtoRMU() throws InterruptedException {
        	getSignoutMenu().Click();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        	   	getChangeRole().Visible()  ;}}),Input.wait60);
        	getChangeRole().Click();
      	    
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getSelectRole().Visible()  ;}}),Input.wait60);
        	getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
        	Thread.sleep(3000);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAvlDomain().Visible()  ;}}),Input.wait30);
        	getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
        	
        	Thread.sleep(3000);
			
        	getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
        	Thread.sleep(3000);
        	
         	getSelectSecurityGroup().selectFromDropdown().selectByVisibleText("Default Security Group");
        	getSaveChangeRole().Click();
        	System.out.println("Impersnated from SA to RMU");

    	}

   
}

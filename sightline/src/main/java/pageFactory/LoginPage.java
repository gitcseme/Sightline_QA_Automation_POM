package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Callable;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;

import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import testScriptsSmoke.Input;

public class LoginPage {

    Driver driver;
    
    public Element getEuserName(){ return driver.FindElementById("txtBxUserID"); }
    public Element getEpassword(){ return driver.FindElementById("txtBxUserPass"); }
    public Element getEloginButton(){ return driver.FindElementByName("submit"); }
    public Element getActiveSessionYesButton(){ return driver.FindElementByXPath("//button[@id='bot1-Msg1']"); }
    public Element getSignoutMenu(){ return driver.FindElementByXPath("//*[@id='user-selector']"); }
    public Element getLogoutOption(){ return driver.FindElementByXPath("//*[@id='utility-group']//a[contains(.,'Sign Out')]"); }
    
    public Element getEmailMeButton(){ return driver.FindElementById("emailme"); }
    public Element getInputOTP(){ return driver.FindElementById("txtPasscode"); }
    //public Element getLogoutOption(){ return driver.FindElementByXPath("//*[@id='utility-group']//a[contains(.,'Sign Out')]"); }
    //Edit profile
    public Element getEditProfile(){ return driver.FindElementById("EditProfile"); }
    public Element getSelectLanguage(){ return driver.FindElementById("select-1"); }
    public Element getEditprofilesave(){ return driver.FindElementById("btnSaveEditProfile"); }

    public Element getSelectProjectDD() { return driver.FindElementById("project-selector"); }
    public Element getSelectProject(String project) {return driver.FindElementByXPath(String.format("//a[contains(text(),'%s')]",project)); }

	static ExtentReports report;
	static ExtentTest test;
   
    public LoginPage(Driver driver){

        this.driver = driver;
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }

   // @SuppressWarnings("static-access")
	public void loginToSightLine(String strUserName,String strPasword){
		loginToSightLine( strUserName, strPasword, true, null);
	}
	public void loginToSightLine(String strUserName,String strPasword, boolean verifyLogin, HashMap dataMap){
		
    	test = (dataMap != null) ? (ExtentTest) dataMap.get("ExtentTest") : null;
		
    	driver.waitForPageToBeReady();
    	
    	try {
	        //Fill user name
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getEuserName().Visible()  ;}}), Input.wait30); 
	    	getEuserName().SendKeys(strUserName);
	
	        //Fill password
	    	getEpassword().SendKeys(strPasword);
	
	        //Click Login button
	    	getEloginButton().Click();
    	} catch (Exception e) {
    		if (test!=null)
				test.log(LogStatus.FAIL, test.addScreenCapture(driver.capture())+"Unable to enter credentials");
    	}
    	
    	//check if user session is active
    	boolean activeSessionButton = false;
    	try{
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getEuserName().Enabled()  ;}}), 3000); 
    		getActiveSessionYesButton().Click();
    		activeSessionButton = true;
    		
		}catch (Exception e) {
			// no activeSessionButton found
			
		}
    	
    	if (activeSessionButton) {
	       	try{
	    		//driver.Navigate().refresh();
	    		driver.waitForPageToBeReady();
	    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				getEuserName().Enabled()  ;}}), 30000);
	    		getEuserName().SendKeys(strUserName) ;
	
	            //Fill password
	        	getEpassword().SendKeys(strPasword);
	            //Click Login button
	        	getEloginButton().Click();
				driver.waitForPageToBeReady();
			}catch (Exception e) {
				if (test!=null) test.log(LogStatus.FAIL, "Unable to enter credentials for active session");
				
			}
    	}
    	
    	verifyLogin = false;
    	if (verifyLogin) {
	    	//below code is to handles 2FA
	    	try{
				/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    				getEuserName().Enabled()  ;}}), Input.wait30);
				*/
		    	getEmailMeButton().Click();
				getInputOTP().SendKeys(LoginPage.readGmailMail("Your one-time passcode to log into Sightline","","OTP",strUserName,strPasword));
				getEloginButton().Click();
			}catch (Exception e1) {
				if (test!=null) test.log(LogStatus.INFO, "2FA is failed/disabled");
				//System.out.println("2FA is failed/disabled");
			}
	    	
	    	//Make sure sign out menu is visible post login
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			 getSignoutMenu().Visible()  ;}}), Input.wait30); 
	    	BaseClass bc = new BaseClass(driver);
	    	try{
	    		//if(!strUserName.equals(Input.sa1userName))
	    			bc.selectproject();
	    	}catch (Exception e) {
	    		if (test!=null) test.log(LogStatus.INFO, String.format("%s not %s as expected", strUserName, Input.sa1userName));
				// TODO: handle exception
			}
	    	
    	}
	    
    	if (getSignoutMenu().Visible()) {
	    	if (test != null) test.log(LogStatus.PASS, "Login success!");
    	} else {
	    	if (test != null) test.log(LogStatus.FAIL, "NOT Logged in!");
    	}
    	Assert.assertTrue(getSignoutMenu().Visible());
    	

    }
    public void logout(){
    	logout(null);
    
    }
    
    public void logout(HashMap databaseMap){
 
		test = null;
		if (databaseMap != null)
			test = (ExtentTest) databaseMap.get("ExtentText");
		
  	
    	driver.Navigate().refresh();
    	 try {
    		 try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	        Alert alert = driver.getWebDriver().switchTo().alert();
    	        String alertText = alert.getText();
    	        System.out.println("Alert data: " + alertText);
    	        alert.accept();
    	    } catch (NoAlertPresentException e) {
    	        //e.printStackTrace();
    	    }
    	
    	try {
	    	driver.scrollPageToTop();
	    	/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getSignoutMenu().Visible()  ;}}), 30000); */
	    	getSignoutMenu().waitAndClick(5);
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getLogoutOption().Visible()  ;}}), 30000); 
	    	getLogoutOption().waitAndClick(5);;
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getEuserName().Visible()  ;}}), 30000); 
	    	if (test != null && getEuserName().Visible()) test.log(LogStatus.PASS, "Logged out sucessfully");
	    	Assert.assertTrue(getEuserName().Visible());
    	} catch (Exception e) {
	    	if (test != null) test.log(LogStatus.FAIL, "Unable to logout");
    	}
    }

 public void logOutWithConfirmation(){
    	
    	driver.Navigate().refresh();
    	
    	
    	driver.scrollPageToTop();
    
    	getSignoutMenu().waitAndClick(5);
    	try{
    		BaseClass bc = new BaseClass(driver);
    		bc.getPopupYesBtn().waitAndClick(10);
			System.out.println("For DocView - Pop up confirmation dialog is shown and clicked on yes button");
		}catch(Exception e1){
			System.out.println("For Doc View - Pop up confirmation dialog is not appeared");
		}

    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getLogoutOption().Visible()  ;}}), 30000); 
    	getLogoutOption().waitAndClick(5);;
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEuserName().Visible()  ;}}), 30000); 
    	Assert.assertTrue(getEuserName().Visible());
    }
    
    public void closeBrowser() {
		driver.close();

	}
    public void quitBrowser() {
		driver.Quit();

	}
    
    public String dynamicNameAppender() {
    	StringBuilder sb = new StringBuilder();
    	Calendar now = Calendar.getInstance(); // Gives you the current time
    	sb.append(now.get(Calendar.DAY_OF_MONTH));
    	sb.append(now.get(Calendar.MONTH));
    	sb.append(now.get(Calendar.HOUR));
    	sb.append(now.get(Calendar.MINUTE));
    	sb.append(now.get(Calendar.SECOND));
    	return sb.toString();

	}
    
    //This function is to read user activation link or 2FA OTP from gmail
    //to read activation link call :LoginPage.readGmailMail("Welcome ","DERev!","ActivationLink","a.utomationsightlineconsilio@gmail.com","consilio@123");
    //for project allocation call:LoginPage.readGmailMail("Dear ","DERev!","ActivationLink","a.utomationsightlineconsilio@gmail.com","consilio@123");
    //to get otp call:LoginPage.readGmailMail("Your one-time passcode to log into Sightline","","OTP",Input.pa1userName,Input.pa1password);

    @SuppressWarnings("null")
	public static String readGmailMail(String SujjectLine, String userName,String OTPorURL,String userID, String userPwd) {
	  System.out.println("passed : "+SujjectLine+userName);
      Properties props = new Properties();
      String url = null;
      String otp = null;

      try {
	            int waitForMail = 35;
	            for(int wait = 0; wait<waitForMail; wait++){
	            	props.load(new FileInputStream(new File(System.getProperty("user.dir")+Input.batchFilesPath+"gmailProperties/"+"smtp.properties")));
	 	            Session session = Session.getDefaultInstance(props, null);
	 	 
	 	            Store store = session.getStore("imaps");
	 	            store.connect("smtp.gmail.com", userID,"consilio@123" );
	 	 
	 	            Folder inbox = store.getFolder("inbox");
	 	            inbox.open(Folder.READ_WRITE);
	 	            //int messageCount = inbox.getMessageCount();
	 	            //System.out.println("Total Messages:- " + messageCount);
	 	            
	 	            // search for all "unseen" messages
	            	Flags seen = new Flags(Flags.Flag.SEEN);
		            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		            Message messages[] = inbox.search(unseenFlagTerm);
		            boolean mailReceived= false;
	            	for (int i = messages.length-1; i > messages.length-5; i--) {


	            	if(messages[i].getSubject().trim().startsWith(SujjectLine+userName)){
	            		mailReceived=true;
		                System.out.println("e-mail Subject:- " + messages[i].getSubject());
		                Object msgContent = messages[i].getContent();
		
		                String content = ""; 
		                String link = null;
		                if (msgContent instanceof Multipart) {
		
		                     Multipart multipart = (Multipart) msgContent;
		
		                     for (int j = 0; j < multipart.getCount(); j++) {
		
		                      BodyPart bodyPart = multipart.getBodyPart(j);
		                      link=bodyPart.getContent().toString();
		                      if(OTPorURL.equalsIgnoreCase("OTP")){  //if looking for otp
		                    		  otp= link.substring(link.indexOf("code is")+8, link.indexOf(", which")); 
			                          //break; 
		                      }
		                      else{ // is looking for activation link
		                    	  if(link.substring(link.indexOf("<")+1, link.indexOf(">")).startsWith("http")){
		                    	  url= link.substring(link.indexOf("Click Here")+11, link.indexOf(">"));
		                    	  //break;
		                    	  }
		                      }
		                     // String disposition = bodyPart.getDisposition();
		                      //break;
		                    }
		                }
		                else{
		                	if (msgContent instanceof String){
		                	//System.out.println("not mp");
		                	link=msgContent.toString();
		                	content= (String)msgContent;
		                	 if(OTPorURL.equalsIgnoreCase("OTP")){  //if looking for otp
		                		 //System.out.println(link);
	                    		  otp= link.substring(link.indexOf("code is")+8, link.indexOf(", which")); 
		                          //break; 
		                	 	}
	                      else{ // is looking for activation link
		                    	  if(link.substring(link.indexOf("<")+1, link.indexOf(">")).startsWith("http")){
		                    	  url= link.substring(link.indexOf("Click Here")+11, link.indexOf(">"));
		                    	  //break;
		                    	  
		                    }
	                      }
	                      }
	                     
		                }
	              
	            	}
	            }

          	
          	if(mailReceived)
          		break;
          	else{
          		if(wait >= waitForMail){
          			System.out.println("no mail, waited for given time..");
          		}
          		Thread.sleep(5000);
          		System.out.println("Waiting for mail!");
          	}
          	 inbox.close(true);
           	store.close();
	           }
	           
	           

      	} catch (Exception e) {
      		e.printStackTrace();
      	}
	      if(OTPorURL.equalsIgnoreCase("OTP"))
			return otp;
	      else
	      return url;
      
    }
    public static void clearBrowserCache() {
   	 /*try {
   	        String[] command = {"cmd.exe", "/C", "Start", System.getProperty("user.dir")+ "//BrowserDrivers//chromeBrowser.bat"};
   	        Runtime.getRuntime().exec(command); 
   	        try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   	        //p.destroyForcibly();
   	    } catch (IOException ex1) {
   	    }*/
    	//Thread.sleep(5000); //wait for clean up activity
	}

}
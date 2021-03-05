package pageFactory;

import static org.testng.Assert.assertTrue;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class WorkflowPage {

	Driver driver;
    
	public Element getWorkFlow_CreateNewWorkFlowBtn(){ return driver.FindElementById("btnCreate"); }
    public Element getWorkFlow_WorkFlowName(){ return driver.FindElementByCssSelector("input#workflowName"); }
    public Element getWorkFlow_Desc(){ return driver.FindElementByCssSelector("textarea#workflowDesc"); }
    public Element getWorkFlow_DescPage_Next(){ return driver.FindElementByCssSelector("a#btnDescNext"); }
    public Element getWorkFlow_Source_Next(){ return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)"); }
    public Element getWorkFlow_SourceID(){ return driver.FindElementByCssSelector("input#SavedSearchID"); }
    public Element getWorkFlow_Filters_Next(){ return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)"); }
    public Element getWorkFlow_Family_Next(){ return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)"); }
    public Element getWorkFlow_FolderSelector1(){ return driver.FindElementByCssSelector("input#id_radio1 + i"); }
    public Element getWorkFlow_AssignmentsSelector(){ return driver.FindElementByCssSelector("input#id_radio2 + i"); }
    public Element getWorkFlow_SelectAssignment(String AssName){ return driver.FindElementByXPath(".//*[@id='jsTreeAssignment']//a[contains(.,'"+AssName+"')]"); }
    public Element getWorkFlow_SelectAssignment(){ return driver.FindElementByXPath(".//*[@id='jsTreeAssignment']//a"); }
    public Element getWorkFlow_Actions_Next(){ return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)"); }
    public Element getWorkFlow_Time1(){ return driver.FindElementByCssSelector("input#txtTime1"); }
    public Element getWorkFlow_day(String day){ return driver.FindElementByCssSelector("input[name*='"+day+"'][type='hidden'] + i"); }
    public Element getWorkFlow_StopDateOfCurrentWorkFlow(){ return driver.FindElementByCssSelector("input#StopDateOfCurrentWorkFlow"); }
    public Element getWorkFlow_Schedules_Next(){ return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)"); }
    public Element getWorkFlow_Notification_AddAllUsers(){ return driver.FindElementByCssSelector("a#btnAllRightMapping"); }
    public Element getWorkFlow_Notifications_Next(){ return driver.FindElementByCssSelector("div#divCentralPanel>div>div>a:nth-child(2)"); }
    public Element geWorkFlow_Summary_Save(){ return driver.FindElementByCssSelector("div#divCentralPanel a[onclick='SaveWorkFlow()']"); }
    public Element getWorkFlow_SelectWorkflow(String WFName){ return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody//td[contains(.,'"+WFName+"')]"); }
    public Element getWorkFlow_SelectWorkStatus(String WFName){ return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody//td[contains(.,'"+WFName+"'')]//td[3]"); }
    public Element getWorkFlow_WorkFlowID(String WFName){ return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody//td[contains(.,'"+WFName+"'')]//td[1]"); }
    public Element getWorkFlow_WorkFlowNotificationStatus(){ return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[1]/td[8]"); }
    
    public Element getWorkFlow_ActionDropdown(){ return driver.FindElementByCssSelector("div.clearfix button[type='button'][data-toggle='dropdown']"); }
    public Element getWorkFlow_RunWorkflowNow(){ return driver.FindElementByCssSelector("a#lnkRunWorkflow"); }
    public Element getWorkFlow_RunWorkflowNow_YesButton(){ return driver.FindElementByCssSelector("button#bot1-Msg1"); }
    public Element getWorkFlow_ActionDeleteWorkFlow(){ return driver.FindElementByCssSelector("a#lnkDeleteWorkflow"); }
    public Element getWorkFlow_ActionDeleteWorkFlow_YesButton(){ return driver.FindElementByCssSelector("button#bot1-Msg1"); }
    public Element getWorkFlow_NextFilters(){ return driver.FindElementByXPath(".//*[@id='divCentralPanel']//a[contains(.,'Next')]"); }
  //  public Element getWorkFlow_Desc(){ return driver.FindElementByCssSelector("textarea#workflowDesc"); }
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
   
    
    //Check status
    public Element getStatusTable(){ return driver.FindElementByXPath("//*[@id='dt_basic']"); }
    public ElementCollection getWorkflowNames(){ return driver.FindElementsByXPath("//*[@id='dt_basic']/tbody/tr/td[2]"); }
    
    public Element getStatus(int row){ return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr["+row+"]/td[3]"); }
    
    
    
    public WorkflowPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);
    }
    
    
    public boolean checkStatusComplete(final String workflowName) {
 	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			  getStatusTable().Visible()  ;}}),Input.wait30);
 	   
 	   boolean nextPage= true;
 	   boolean found= false;
 	  
 	   while(nextPage){
 		   int row = 1;
 		   
 		   for (WebElement ele : getWorkflowNames().FindWebElements()) {
 			  
 				if(ele.getText().trim().equals(workflowName)){
 					nextPage = false;
 					found=true;
 					System.out.println(row);
 					if(getStatus(row).getText().trim().equalsIgnoreCase("COMPLETE") );{
 					System.out.println(workflowName +" Scheduled run is completed with the status 'COMPLETE'!");
 					return true;
 					}
 				}
 				
 				row++;
 				
 			}
 		   try{
 			   driver.scrollingToBottomofAPage();
 			   driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a")).isDisplayed();
 			   nextPage = false;
 			   //System.out.println("Not found!!!!!!");
 		   }
 		   catch (Exception e) {
 			   driver.getWebDriver().findElement(By.linkText("Next")).click(); 
 		   } 
 			   
 	   }
 	   return false;
 			
    	}
  
    public void CreateWFwithAssignments(int SavedSearchID,final String WFName,String WFDesc,final String AssignmentName, int purehit) throws ParseException, InterruptedException {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_CreateNewWorkFlowBtn().Visible() ;}}),Input.wait60);
    	
    	getWorkFlow_CreateNewWorkFlowBtn().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_WorkFlowName().Visible() ;}}),Input.wait30);
    	getWorkFlow_WorkFlowName().SendKeys(WFName);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_Desc().Visible() ;}}),Input.wait30);
    	getWorkFlow_Desc().SendKeys(WFDesc);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_DescPage_Next().Visible() ;}}),Input.wait30);
    	getWorkFlow_DescPage_Next().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_SourceID().Visible() ;}}),Input.wait30);
    	getWorkFlow_SourceID().SendKeys(Integer.toString(SavedSearchID));
    	
    	Thread.sleep(10000);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_Source_Next().Visible() ;}}),Input.wait30);
    	getWorkFlow_Source_Next().Click();
    	
    	Thread.sleep(10000);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_Filters_Next().Visible() ;}}),Input.wait30);
    	getWorkFlow_Filters_Next().Click();
    	
    	Thread.sleep(10000);
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getWorkFlow_Family_Next().Visible() ;}}),Input.wait30);
    	getWorkFlow_Family_Next().Click();
    	
    	Thread.sleep(5000);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_AssignmentsSelector().Visible() ;}}),Input.wait30);
    	getWorkFlow_AssignmentsSelector().Click();
    	
    	driver.scrollingToBottomofAPage();
    	
    	Thread.sleep(5000);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_SelectAssignment().Visible() ;}}),Input.wait30);
    	getWorkFlow_SelectAssignment(AssignmentName).Click();
    	
    	Thread.sleep(5000);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_Actions_Next().Visible() ;}}),Input.wait30);
    	getWorkFlow_Actions_Next().Click();
    	
    	// ******* Schedules tab *************************************
    	//Time in GMT
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
       
        //Local time zone   
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        System.out.println(dateFormatLocal.parse( dateFormatGmt.format(new Date())));
        UtilityLog.info(dateFormatLocal.parse( dateFormatGmt.format(new Date())));
        
        String Time = dateFormatGmt.format(new Date()).toString();
        System.out.println(Time);
        UtilityLog.info(Time);
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d = df.parse(Time); 
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 1);
        
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
   	 	String weekdays[] = dfs.getWeekdays();
   	 	int day = cal.get(Calendar.DAY_OF_WEEK);
   	 	String nameOfDay = weekdays[day];
   	 	System.out.println(nameOfDay);
   	 	UtilityLog.info(nameOfDay);
   	 
   	 	//get next day date
        cal.add(Calendar.DATE, 1);
        String newTime = df.format(cal.getTime());
        System.out.println(newTime);
        UtilityLog.info(newTime);
        String s[] = newTime.split(" ");
        System.out.println(s[0]); //next day date
        UtilityLog.info(s[0]);
        System.out.println(s[1]); //current time + 1 min
        UtilityLog.info(s[01]);
        System.out.println();
    	
       	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getWorkFlow_Time1().Visible()  ;}}),Input.wait30);
    	 getWorkFlow_Time1().SendKeys(s[1]);
    	 
    	 //get day of week 
    	 
    	
    	// Select days for schedules -
    	getWorkFlow_day(nameOfDay).Click();
    				
    	
    	getWorkFlow_StopDateOfCurrentWorkFlow().SendKeys(s[0]);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_Schedules_Next().Visible() ;}}),Input.wait30);
    	getWorkFlow_Schedules_Next().Click();
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_Notification_AddAllUsers().Visible() ;}}),Input.wait30);
    	getWorkFlow_Notification_AddAllUsers().Click();
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_Notifications_Next().Visible() ;}}),Input.wait30);
    	getWorkFlow_Notifications_Next().Click();
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			geWorkFlow_Summary_Save().Visible() ;}}),Input.wait30);
    	geWorkFlow_Summary_Save().Click();
    	
    	
    	/*BaseClass bc = new BaseClass(driver);
        int Bgcount = bc.initialBgCount();
        System.out.println(Bgcount);*/
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getWorkFlow_SelectWorkflow(WFName).Visible() ;}}),Input.wait30);
    	
    	
    	for (int i = 0; i < 30; i++) {
			try {
				if(checkStatusComplete(WFName))
				break;
			//	getWorkFlow_SelectWorkStatus(WFName).Displayed();
			} catch (Exception e) {
				Thread.sleep(5000);
				driver.Navigate().refresh();
			}
		}
    	
    	/*String WrkflowID = getWorkFlow_WorkFlowID(WFName).getText();
		System.out.println(WrkflowID);*/
		
		/*  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait120+Input.wait60);  
	 	   System.out.println(bc.initialBgCount());*/
	 	   
	 	 /* BaseClass base = new BaseClass(driver);
	 	  base.BckTaskClick();
    	
    	Assert.assertEquals(getWorkFlow_WorkFlowNotificationStatus().getText().toString(), "Completed");*/
    	
    	/*//verify documents in assignment
    	Thread.sleep(25000);
    	 AssignmentsPage asspage = new AssignmentsPage(driver);
    	
    	
        driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			asspage.getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
    	
    	asspage.getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
    	
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			asspage.getSelectAssignment(AssignmentName).Visible()  ;}}), Input.wait60);
    	
    	int DocsinAssgn = Integer.parseInt(asspage.getSelectAssignmentDocCount(AssignmentName).getText());
		System.out.println(DocsinAssgn);
    	
		Assert.assertEquals(DocsinAssgn,purehit );*/
    	
    	
    	
    	
		
    				
    }

     
}
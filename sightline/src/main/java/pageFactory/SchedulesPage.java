package pageFactory;

import static org.testng.Assert.assertTrue;
import java.util.concurrent.Callable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;



public class SchedulesPage {

    Driver driver;
    
    public Element getRunTab(){ return driver.FindElementByXPath("//*[@id='ui-id-2']"); }
    public ElementCollection getfindSearchRow(){ return driver.FindElementsByXPath("//table[@id='GridSchedulerExecutionList']//td[2]"); }
    public Element getStatusCheck(int row){ return driver.FindElementByXPath("//table[@id='GridSchedulerExecutionList']/tbody/tr["+row+"]/td[5]"); }
  /*  
    public Element getDescription(){ return driver.FindElementById("AnnotationDescription"); }
    public Element getSaveBtn(){ return driver.FindElementById("btnAnnotationSave"); }
*/    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
 
  
    //Annotation Layer added successfully
    public SchedulesPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+ "Scheduler/ManageScheduler");
        driver.waitForPageToBeReady();
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }

   
   public void checkStatusComplete(final String taskName) {
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getRunTab().Visible()  ;}}),Input.wait30);
	   getRunTab().Click();
	   boolean nextPage= true;
	   boolean found= false;
	  
	   while(nextPage){
		   int row = 1;
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getfindSearchRow().Visible()  ;}}),Input.wait30);
		   for (WebElement ele : getfindSearchRow().FindWebElements()) {
			  
				if(ele.getText().trim().equals(taskName)){
					nextPage = false;
					found=true;
					assertTrue(getStatusCheck(row).getText().trim().equalsIgnoreCase("COMPLETE") );
					System.out.println(taskName +" Scheduled run is completed with the status 'COMPLETE'!");
					UtilityLog.info(taskName +" Scheduled run is completed with the status 'COMPLETE'!");
					return;
				}
				
				row++;
				
			}
		   try{
			   driver.scrollingToBottomofAPage();
			   driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a")).isDisplayed();
			   nextPage = false;
			   System.out.println("Not found!!!!!!");
			   UtilityLog.info("Not found!!!!!!");
		   }
		   catch (Exception e) {
			   driver.getWebDriver().findElement(By.linkText("Next")).click(); 
		   } 
			   
	   }
			
   	}
 
   
 }
package pageFactory;

import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;

public class TagCountbyTagReport {

    Driver driver;
    BaseClass bc;
    SoftAssert softAssertion;
  
    public Element getReport_TagCount(){ return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Tag Counts')]"); }
    public Element getApplyBtn(){ return driver.FindElementById("btn_applychanges"); } 
    public Element getTags_Expandbutton(){ return driver.FindElementByXPath("//*[@id='divTagList']/div[1]/a[2]"); } 
    public Element getTag_Selecttag(String tagname){ return driver.FindElementByXPath("//*[@id='collapsetags2']//a[contains(text(),"+tagname+")]"); }
    public Element getDate_Expandbutton(){ return driver.FindElementByXPath("//*[@id='divdateonly']/div/a[2]"); } 
    public Element getDateRangeFrom(){ return driver.FindElementById("from"); }
    public Element getDateRangeTo(){ return driver.FindElementById("to"); }
    public Element getDatetabledata(){ return driver.FindElementByXPath(".//*[@id='ui-datepicker-div']/table/tbody"); }
    public Element getTagReporttable(int colno){ return driver.FindElementByXPath("//*[@id='TagcountReportTable']//td["+colno+"]"); } 
    public Element getTally_SubTally_Action_ViewButton(){ return driver.FindElementByXPath(".//*[@id='freezediv']//a[contains(.,'View')]"); } 
    public Element getTally_subMetadata(){ return driver.FindElementByXPath(".//*[@id='accordion2']//strong[contains(.,'METADATA')]"); } 
    public Element getTally_SubTally_Action_ViewDocList(){ return driver.FindElementById("idSubTallyViewDoclist"); } 
    public Element getTally_LoadingScreen(){ return driver.FindElementByXPath("//span[@class='LoadImagePosition']/img[@src='/img/loading.gif']"); } 
    public Element getDataAsOfToday(){ return driver.FindElementById("DataAsOf"); }

   
    
    
    public TagCountbyTagReport(Driver driver){

    	this.driver = driver;
    	bc = new BaseClass(driver);
    	softAssertion= new SoftAssert(); 
        this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
        
     }
    
    public void ValidateTagCountreport(final String tagname) throws InterruptedException, ParseException {
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getReport_TagCount().Visible()  ;}}), Input.wait30); 
         getReport_TagCount().Click();
         
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getTags_Expandbutton().Visible()  ;}}), Input.wait60);		
		  getTags_Expandbutton().Click();
		  
		  Thread.sleep(5000);
		  
		 getTag_Selecttag(tagname).waitAndClick(10);
			 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
					 getDate_Expandbutton().Enabled() ;}}), Input.wait30);
		 getDate_Expandbutton().Click();
		 
		 bc.SelectCurrentdatfromDatePicker(getDateRangeFrom(), getDatetabledata());
	     Thread.sleep(2000);
	       
	     bc.SelectCurrentdatfromDatePicker(getDateRangeTo(), getDatetabledata());     
		 Thread.sleep(2000);
		      
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			  getApplyBtn().Visible()  ;}}), Input.wait30);	
    	  getApplyBtn().Click();
    	  
    	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			  getTagReporttable(1).Visible()  ;}}), Input.wait60);	
    	  String time = bc.getcurrentdateinGMT();
    	  System.out.println(time);
       
    	/*  
    	//validate tag name
    	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			  getTagReporttable(1).Visible()  ;}}), Input.wait60);	
    	  String tagnameonreport = getTagReporttable(1).getText();
    	  System.out.println(tagnameonreport);

    	  softAssertion.assertEquals(tagnameonreport, tagname);
    	  
    	  String tagcountonreport = getTagReporttable(2).getText();
    	  System.out.println(Integer.parseInt(tagcountonreport));

    	  softAssertion.assertEquals(tagcountonreport, "50");
    	 
    	  softAssertion.assertAll();*/
    	     	  
    }	  

      
    	 
		  
		   
		  

     
}
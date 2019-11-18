package pageFactory;

import java.util.concurrent.Callable;
import org.openqa.selenium.By;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;

public class HomePage {

    Driver driver;
   
    //Menu and sub menus 
    public Element getManageMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Manage')]"); }
    public Element getTagsPage(){ return driver.FindElementByName("Tags"); }
    public Element getAssignmentsPage(){ return driver.FindElementByName("Assignments"); }
    public Element getKeywordsPage(){ return driver.FindElementByName("Keywords"); }
    public Element getCommentsPage(){ return driver.FindElementByName("Comments"); }
    public Element getRedactionPage(){ return driver.FindElementByName("Redaction"); }
    public Element getUsersPage(){ return driver.FindElementByName("Users"); }
    public Element getClassificationPage(){ return driver.FindElementByName("Classification"); }
    public Element getCodingPage(){ return driver.FindElementByName("Coding"); }
    public Element getAnnotationPage(){ return driver.FindElementByName("Annotation"); }
    public Element getSchedulesPage(){ return driver.FindElementByName("Schedules"); }
    public Element getWorkFlowPage(){ return driver.FindElementByName("WorkFlow"); }
    
    public Element getSearchMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Search')]"); }
    public Element getIngestionMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Ingestion')]"); }
    public Element getReportsMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Reports')]"); }
    public Element getProductionsMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Productions')]"); }
    public Element getBatchPrintMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Batch Print')]"); }
    public Element getCategorizationMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Categorize')]"); }
    
    //-----------------
    //Reviewer
    public ElementCollection getAssignmentsList(){ return driver.FindElementsByXPath("//strong[contains(text(),'')]"); }
    
    
 
     
    
    public HomePage(Driver driver){

        this.driver = driver;
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }

    }
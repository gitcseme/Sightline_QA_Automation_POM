package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;


public class CommentsPage {

    Driver driver;
    BaseClass base;
   
    public Element getAddCommentsBtn(){ return driver.FindElementById("btnAddComment"); }
    public Element getCommentName(){ return driver.FindElementById("CommentLabel"); }
    public Element getEditCommentButton(){ return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[1]//a[contains(text(),'Edit')]"); }
    public Element getDeleteCommentButton(String commentname){ return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[contains(.,'"+commentname+"')]//a[contains(text(),'Delete')]"); }
    public Element getPopupYesBtn(){ return driver.FindElementByXPath("//button[@id='bot1-Msg1']"); }
    
    public Element getSaveBtn(){ return driver.FindElementById("btnSave"); }
    
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
 
    //select coding's radio button from table
    public Element getCommentlistTable(){ return driver.FindElementByXPath("//*[@id='CommentsTable']"); }
    public ElementCollection getCommentnames(){ return driver.FindElementsByXPath("//*[@id='CommentsTable']/tbody/tr/td[1]"); }
    public Element getCommentname(String commentname){ return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[contains(.,'"+commentname+"')]"); }
    
  
     public CommentsPage(Driver driver){

        this.driver = driver;
        base = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "Comments/CommentsList");
      }

    public void AddComments(String ComentName) {
    	
    	 this.driver.getWebDriver().get(Input.url+ "Comments/CommentsList");
    	getAddCommentsBtn().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getCommentName().Visible()  ;}}), Input.wait30); 
    	getCommentName().SendKeys(ComentName);
    	getSaveBtn().Click();
    	
      base.VerifySuccessMessage("Comment Field added successfully");
      base.CloseSuccessMsgpopup();
	}
    
   public void DeleteComments(String ComentName) {
	   
	    FindComment(ComentName); 
	    getDeleteCommentButton(ComentName).Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getPopupYesBtn().Visible()  ;}}), Input.wait30); 
    	getPopupYesBtn().Click();
       base.VerifySuccessMessage("Comment Field deleted successfully.");
       base.CloseSuccessMsgpopup();
	}
   
     public boolean FindComment(final String Commentname) {
   	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			  getCommentlistTable().Visible()  ;}}),Input.wait30);
   	   
   	   boolean nextPage= true;
   	   boolean found= false;
   	  System.out.println(getCommentnames().size());
   	   while(nextPage){
   		   int row = 1;
   		   
   		   for (WebElement ele : getCommentnames().FindWebElements()) {
   			  System.out.println(ele.getText().trim());
   				if(ele.getText().trim().equals(Commentname)){
   					nextPage = false;
   					found=true;
   					//System.out.println(row);
   					getCommentname(Commentname).waitAndClick(10);
   					System.out.println(Commentname +" is selected");
   					return true;
   					
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
   }
     
  
   
 
 
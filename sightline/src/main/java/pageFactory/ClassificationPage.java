package pageFactory;

import java.util.*;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;


public class ClassificationPage {

    Driver driver;
    BaseClass base;
   
    public Element getMaxQualified(){ return driver.FindElementById("ddlMaxQualified"); }
    public Element getCommentName(){ return driver.FindElementById("CommentLabel"); }
    public Element getEditCommentButton(){ return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[1]//a[contains(text(),'Edit')]"); }
    public Element getDeleteCommentButton(String commentname){ return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[contains(.,'"+commentname+"')]//a[contains(text(),'Delete')]"); }
    public Element getPopupYesBtn(){ return driver.FindElementByXPath("//button[@id='bot1-Msg1']"); }
    
    public Element getSaveBtn(){ return driver.FindElementById("btnSave"); }
    public Element getCommentlistTable(){ return driver.FindElementByXPath("//*[@id='CommentsTable']"); }
    public Element getLabelQC(){ return driver.FindElementByXPath("//label[text()='QC']"); }
    public Element getLevellabel(int no){ return driver.FindElementByXPath("//label[text()='Level "+no+"']"); }
    
  
     public ClassificationPage(Driver driver){

        this.driver = driver;
        base = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "Assignment/Classification");
      }

    public void VerifyClassification() throws InterruptedException {
    	
    	List<String> explevelname = new ArrayList<>(Arrays.asList("1","2","3","4","5"));
    	List<String> levelname = new ArrayList<String>();
		Select select = new Select(getMaxQualified().getWebElement());
		List<WebElement> levels = select.getOptions();
		
		for(int i=1;i<=5;i++)
		{
		    System.out.println(levels.get(i).getText());
		    levelname.add(levels.get(i).getText());
		}
		System.out.println(levelname);
		System.out.println(explevelname);
		
		if(explevelname.containsAll(levelname))
		{
			System.out.println("Pass");
		}
		else {
			System.out.println("Fail");
		}
		
	
		//display all level names
		for(int k=1;k<=5;k++)
		{
			getMaxQualified().selectFromDropdown().selectByValue(Integer.toString(k));
			Thread.sleep(2000);
			Assert.assertTrue(getLevellabel(k).Displayed());
			Assert.assertTrue(getLabelQC().Displayed());
	}
		
		
		
		
    }
  
   }
     
  
   
 
 
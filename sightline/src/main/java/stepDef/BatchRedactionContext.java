package stepDef;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import testScriptsSmoke.Input;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import junit.framework.Assert;



@SuppressWarnings({"deprecation", "rawtypes" })
public class BatchRedactionContext extends CommonContext {
	
	public Element getPageTitle(){ return driver.FindElementByXPath("//body/div[@id='renderBody']/div[@id='main']/div[@id='content']/div[1]/div[1]/div[1]"); }
	public Element getBatchRedactionHistory(){ return driver.FindElementByXPath("//body/div[@id='renderBody']/div[@id='main']/div[@id='content']/div[5]/div[1]/div[1]"); }
	public Element getBatchRedactionSection(){ return driver.FindElementByXPath("//body/div[@id='renderBody']/div[@id='main']/div[@id='content']/div[4]/div[1]/div[1]/label[1]"); }
	public Element getButtonAnalyze(){ return driver.FindElementByXPath("//button[@id='1g']"); }
	public Element getRedactionmTaglist(){ return driver.FindElementByXPath("//select[@id='ddlRedactionmTaglist']/option[1]"); }
	public Element getClose(){ return driver.FindElementByXPath("//body/div[8]/div[2]/div[2]/div[2]/button[1]"); }
	
	
	@When("^.*(\\[Not\\] )? goto_redaction_page$")
	public void verify_title_returned(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			//Verify title "Batch Redactions Page"
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getPageTitle().Visible()  ;}}), Input.wait30); 

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getBatchRedactionHistory().Visible()  ;}}), Input.wait30); 
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getBatchRedactionSection().Visible()  ;}}), Input.wait30);

			pass(dataMap,"Verified Batch Redactions Home page.");

		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				// scriptState = false: not pass or fail. Just move on
			}
		}

	}
	
		
	@And("^.*(\\[Not\\] )? verify_analyze_group$")
	public void verify_analyze_group(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
		try {
			//Verify title "Analyze Group for Redactions"
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getButtonAnalyze().Visible()  ;}}), Input.wait30); 
			getButtonAnalyze().Click();
			base.VerifySuccessMessage("Your request to batch redact has been added to the background. Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.");
			pass(dataMap,"Analyze Group for Redactions.");

		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				// scriptState = false: not pass or fail. Just move on
			}
		}
		}
	
	}

	@And("^.*(\\[Not\\] )? click_View_and_Redact$")
	public void click_View_and_Redact(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
		try {
			//Verify "click_View_and_Redact"
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getButtonAnalyze().Visible()  ;}}), Input.wait30); 
			getButtonAnalyze().waitAndClick(10);
			//String txt = getRedactionmTaglist().getText();
			Assert.assertEquals("17Dec20_RTag1" , getRedactionmTaglist().getText());
			pass(dataMap,"Analyze Group for Redactions.");

		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				// scriptState = false: not pass or fail. Just move on
			}
		}
		}
	}
	
}

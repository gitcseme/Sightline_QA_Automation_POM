package stepDef;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

import automationLibrary.Driver;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import pageFactory.BatchPrintPage;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.BaseClass;
import pageFactory.SessionSearch;
import testScriptsSmoke.Input;

@SuppressWarnings({"rawtypes", "unchecked" })
public class CommonContext {
	Driver driver;
	WebDriver webDriver;
	LoginPage lp;

	ProductionPage prod;
	IngestionPage ingest;
	BatchPrintPage batchPrint;
	BaseClass base;
	SavedSearch savedSearch;

    @Given("^(\\[Not\\] )?sightline_is_launched$")
	public void sightline_is_launched(boolean scriptState, HashMap dataMap) {

		driver = new Driver();
		webDriver = driver.getWebDriver();

		if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC){
			driver.Manage().window().maximize();}
		else driver.Manage().window().fullscreen();

		dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
        
		String url;
		if (scriptState) {
			url = (String) dataMap.get("URL");
			webDriver.get(url);
			driver.waitForPageToBeReady();
			pass(dataMap,String.format("Opened page %s",url));
		} else {
			url = "http://www.sqasquared.com";
			webDriver.get(url);
			pass(dataMap,String.format("Opened random page %s",url));
		}
	}

	@And("^.*(\\[Not\\] )? login$")
	public void login(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		lp = new LoginPage(driver);
		String uid = (String) dataMap.get("uid");
		String pwd = (String) dataMap.get("pwd");
		if (scriptState) {
			lp.loginToSightLine(uid, pwd, true, dataMap);
			//lp.loginToSightLine((String) dataMap.get("uid"), (String) dataMap.get("pwd"), true, dataMap);
		} else {
			if (uid != null && uid.length() > 0) {
				lp.loginToSightLine(uid, pwd, false, dataMap);
			}
		}
	}

    
    @And("^(.*\\[Not\\] )?login_as_pau$")
	public void login_as_pau(boolean scriptState, HashMap dataMap) {
		lp = new LoginPage(driver);
		if (scriptState) {
			lp.loginToSightLine((String) dataMap.get("uid"), (String) dataMap.get("pwd"), true, dataMap);
		} else {
			String uid = (String) dataMap.get("uid");
			String pwd = (String) dataMap.get("pwd");
			
			if (uid != null && uid.length() > 0) {
				lp.loginToSightLine(uid, pwd, false, dataMap);
			}
		}
	}
    
    @And("^(.*\\[Not\\] )?login_as_sau$")
	public void login_as_sau(boolean scriptState, HashMap dataMap) {
		lp = new LoginPage(driver);
		
		ingest = new IngestionPage(driver);
		
		if (scriptState) {
			lp.loginToSightLine("juan.guzman@consilio.com","Q@test_10", true, dataMap);
			//lp.loginToSightLine((String) dataMap.get("uid"), (String) dataMap.get("pwd"), true, dataMap);
		} else {
			String uid = (String) dataMap.get("uid");
			String pwd = (String) dataMap.get("pwd");
			
			if (uid != null && uid.length() > 0) {
				lp.loginToSightLine(uid, pwd, false, dataMap);
			}
		}
	}

	@And("^.*(\\[Not\\] )? login_as_rmu$")
	public void login_as_rmu(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
    	lp = new LoginPage(driver);
    	base = new BaseClass(driver);

    	login_as_pau(scriptState, dataMap);

		if (scriptState) {
			//
			//* Enter Username and password for Review Manager user
			//* User is logged in
			//* Sightline Home page is displayed
			//
			try {
				String project = dataMap.get("project").toString();
				String role = dataMap.get("impersonate").toString();
				String securityGroup = dataMap.get("security_group").toString();
				String domain = dataMap.get("domain").toString();

				base.getSignoutMenu().click();
				base.getChangeRole().click();

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return
								base.getSelectRole().Visible();
					}
				}), Input.wait30);
				base.getSelectRole().selectFromDropdown().selectByVisibleText(role);
				Thread.sleep(3000);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return
								base.getAvlDomain().Visible();
					}
				}), Input.wait30);
				base.getAvlDomain().selectFromDropdown().selectByVisibleText(domain);
				Thread.sleep(3000);
				base.getAvlProject().selectFromDropdown().selectByVisibleText(project);
				Thread.sleep(3000);
				base.getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(securityGroup);
				base.getSaveChangeRole().click();
			}catch (Exception e){
				System.out.println(e);
			}
			pass(dataMap,"Login as rmu");
		} else {
			fail(dataMap,"Not able to login as rmu");
		}

	}
 
	@And("^.*(\\[Not\\] )? select_project$")
	public void select_project(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				lp.getSelectProjectDD().Enabled()  ;}}), Input.wait30); 
		
		String project = (String) dataMap.get("project");
		lp.getSelectProjectDD().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				lp.getSelectProject(project).Visible()  ;}}), Input.wait30); 
		lp.getSelectProject(project).Click();
    	driver.waitForPageToBeReady();
	}
	
    @When("^.*(\\[Not\\] )? on_production_home_page$")
	public void on_production_home_page(boolean scriptState, HashMap dataMap)  throws ImplementationException, Exception {
    	prod = new ProductionPage(driver);

		dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
		//Used to create string to append to any folder/tag/etc names
		dataMap.put("dateTime",new Long((new Date()).getTime()).toString());
	    

		if (scriptState) {
			
	        String url = (String) dataMap.get("URL");
			webDriver.get(url+"/Production/Home");
			driver.waitForPageToBeReady();
			
			if (!prod.changeProjectSelector().getText().equals("021320_EG")) {
				prod.changeProjectSelector().Click();
			    prod.productionProjectSelector().Click();
			}

		    driver.waitForPageToBeReady();
		    
		 // switch to AutomationProductionSet
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 prod.getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
			prod.getProdExport_ProductionSets().SendKeys("DefaultProductionSet");
			driver.waitForPageToBeReady();
			
		} else {
			webDriver.get("http://www.google.com");
		}

		driver.waitForPageToBeReady();

	}
    
    @And("^.*(\\[Not\\] )? on_ingestion_home_page$")
    public void on_ingestion_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	    ingest = new IngestionPage(driver);
	    if (!ingest.changeProjectSelector().getText().equals("Auto_Smoke2901")) {
	    	ingest.changeProjectSelector().Click();
	    	ingest.ingestionProjectSelector().Click();
		}

    	dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
	    
	    if (scriptState) {
	    	String url = (String) dataMap.get("URL");
	    		webDriver.get(url+"Ingestion/Home");
	    		
	    } else {
	    		webDriver.get("http://www.google.com");
	    }

	    driver.waitForPageToBeReady();
	    
	    // save Ingestion count
	    String totalIngestCountText = ingest.getTotalIngestCount().getText();
	    dataMap.put("ingestion_count", totalIngestCountText);

	} 
    
	@And("^.*(\\[Not\\] )? on_saved_search_page$")
	public void on_saved_search_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* User navigates to Saved Search page (/SavedSearch/SavedSearches)
			//* Saved Search page is displayed
			//
			savedSearch = new SavedSearch(driver);
		} else {
			fail(dataMap, "Not on the saved search page");
		}

	}
    
	@And("^.*(\\[Not\\] )? on_batch_print_page$")
	public void on_batch_print_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* User navigates to Batch Print page (/BatchPrint)
			//* Batch Print page is displayed
			//
			batchPrint = new BatchPrintPage(driver);
			dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
		    
		    if (scriptState) {
		    	String url = (String) dataMap.get("URL");
		    		webDriver.get(url+"/BatchPrint/");
		    		
		    } else {
		    		webDriver.get("http://www.google.com");
		    }

		    driver.waitForPageToBeReady();
		} else {
			throw new ImplementationException("NOT on_batch_print_page");
		}

	}
    
    @And("^.*(\\[Not\\] )? on_admin_home_page$")
	public void on_admin_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
		
		if (scriptState) {
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"Project/Project");
		} else {
			webDriver.get("http://www.google.com");
		}
			driver.waitForPageToBeReady();
								
	}
    
    @And("^.*(\\[Not\\] )? on_ingestion_home_page$")
	public void on_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
		
		ingest = new IngestionPage(driver);
		ingest.changeProjectSelector().Click();
		ingest.changeProjectSelectorField().Click();

		if (scriptState) {
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"Ingestion/Home");
		} else {
			webDriver.get("http://www.google.com");
		}
		driver.waitForPageToBeReady();
								
	}
   
    public HashMap close_browser(boolean scriptState, HashMap dataMap) {
		try{ 
			if (lp !=null) lp.logout();
		     //lp.quitBrowser();	
			}finally {
				if (lp !=null) lp.quitBrowser();
			}
		LoginPage.clearBrowserCache();
		
		try {
			//final attempt to close any open browsers
			driver.close();
		} catch (Exception e) {
		}
		return dataMap;
	}
    
    public void pass(HashMap dataMap, String message) {
    	log( dataMap,  true,  message);
    }
    public void fail(HashMap dataMap, String message) {
    	log( dataMap,  false,  message);
    }
    public void log(HashMap dataMap, boolean result, String message) {
    	ExtentTest test = (dataMap != null) ? (ExtentTest) dataMap.get("ExtentTest") : null;
    	if (test != null) {
    		test.log((result ? LogStatus.PASS : LogStatus.FAIL), message);
    	}
    	assert result;
    }
    
    public void logTestResult(HashMap dataMap, String tid, String result, String description) {
		ArrayList testCaseResultList = (ArrayList) dataMap.get("TestCaseResults");
		HashMap testCaseResult = new HashMap();
		testCaseResult.put("tid", tid);
		testCaseResult.put("result", result);
		testCaseResult.put("description", description);
		testCaseResultList.add(testCaseResult);
    }
}
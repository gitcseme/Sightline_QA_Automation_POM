package stepDef;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

import automationLibrary.Driver;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import testScriptsSmoke.Input;

@SuppressWarnings({"rawtypes", "unchecked" })
public class CommonContext {
	Driver driver;
	WebDriver webDriver;
	LoginPage lp;

	ProductionPage prod;
	IngestionPage ingest;

    @Given("^(\\[Not\\] )?sightline_is_launched$")
	public void sightline_is_launched(boolean scriptState, HashMap dataMap) {

		driver = new Driver();
		webDriver = driver.getWebDriver();

		dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
        
		String url;
		if (scriptState) {
			url = (String) dataMap.get("URL");
			webDriver.get(url);
			driver.waitForPageToBeReady();
			pass(dataMap,String.format("Opened page %s",url));
		} else {
			url = "http://www.google.com";
			webDriver.get("http://www.google.com");
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
			lp.loginToSightLine(Input.pa1userName, Input.pa1password, true, dataMap);
			//lp.loginToSightLine((String) dataMap.get("uid"), (String) dataMap.get("pwd"), true, dataMap);
		} else {
			String uid = (String) dataMap.get("uid");
			String pwd = (String) dataMap.get("pwd");
			
			if (uid != null && uid.length() > 0) {
				lp.loginToSightLine(uid, pwd, false, dataMap);
			}
		}
	}

    @When("^.*(\\[Not\\] )? on_production_home_page$")
	public void on_production_home_page(boolean scriptState, HashMap dataMap)  throws ImplementationException, Exception {
		dataMap = new HashMap();
		dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
		//Used to create string to append to any folder/tag/etc names
		dataMap.put("dateTime",new Long((new Date()).getTime()).toString());

		if (scriptState) {
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"/Production/Home");
		} else {
			webDriver.get("http://www.google.com");
		}
		driver.waitForPageToBeReady();

		prod = new ProductionPage(driver);
	}
    
    @And("^.*(\\[Not\\] )? on_ingestion_home_page$")
	public void on_ingestion_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		dataMap = new HashMap();
		dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
		dataMap.put("dateTime",new Long((new Date()).getTime()).toString());
		
		if (scriptState) {
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"Ingestion/Home");
		} else {
			webDriver.get("http://www.google.com");
		}
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			ingest.getTotalIngestCount().Visible()  ;}}), Input.wait30); 
		String totalIngestCountText = ingest.getTotalIngestCount().getText();
		dataMap.put(totalIngestCountText, "actualCount");
		
		ingest = new IngestionPage(driver);
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
}
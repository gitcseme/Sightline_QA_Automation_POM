package stepDef;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

import automationLibrary.Driver;

import org.apache.commons.lang3.SystemUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

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

		//if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC){
		//	driver.Manage().window().maximize();
		//} else driver.Manage().window().fullscreen();

		String url;
		if (scriptState) {
			url = Input.url;
			dataMap.put("URL",url);

			webDriver.get(url);
			driver.waitForPageToBeReady();
			pass(dataMap,String.format("Opened page %s",url));
		} else {
			url = "http://www.sqasquared.com";
			dataMap.put("URL",url);

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
			// this approach allows the negative state to provide a specific uid 
			// to test for specific negative tests
			if (uid != null && uid.length() > 0) {
				lp.loginToSightLine(uid, pwd, false, dataMap);
			}
		}
	}

	@And("^(.*\\[Not\\] )?login_as$")
	public void login_as(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String loginAs = (String) dataMap.get("loginAs");
		switch (loginAs.toLowerCase()) {
		case "pau":
			login_as_pau(scriptState, dataMap);
			break;
		case "sau":
			login_as_sau(scriptState, dataMap);
			break;
		case "rmu":
			login_as_rmu(scriptState, dataMap);
			break;
		case "rev":
			login_as_rev(scriptState, dataMap);
			break;
		}
	}


	@And("^(.*\\[Not\\] )?login_as_pau$")
	public void login_as_pau(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String uid = Input.pa1userName;
		String pwd = Input.pa1password;
		dataMap.put("uid",uid);
		dataMap.put("pwd",pwd);
		login(scriptState, dataMap);
	}

	@And("^(.*\\[Not\\] )?login_as_sau$")
	public void login_as_sau(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String uid = Input.sa1userName;
		String pwd = Input.sa1password;
		dataMap.put("uid",uid);
		dataMap.put("pwd",pwd);
		login(scriptState, dataMap);
	}

	@And("^(.*\\[Not\\] )?login_as_rev$")
	public void login_as_rev(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String uid = Input.rev1userName;
		String pwd = Input.rev1password;
		dataMap.put("uid",uid);
		dataMap.put("pwd",pwd);
		login(scriptState, dataMap);
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

		if (!lp.getSelectProjectDD().getText().equals(project)) {
			lp.getSelectProjectDD().Click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					lp.getSelectProject(project).Visible()  ;}}), Input.wait30); 
			lp.getSelectProject(project).Click();
			driver.waitForPageToBeReady();
		}
	}

	@When("^.*(\\[Not\\] )? on_production_home_page$")
	public void on_production_home_page(boolean scriptState, HashMap dataMap)  throws ImplementationException, Exception {
		prod = new ProductionPage(driver);

		//Used to create string to append to any folder/tag/etc names
		dataMap.put("dateTime",new Long((new Date()).getTime()).toString());

		if (scriptState) {
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"/Production/Home");
			driver.waitForPageToBeReady();

			// switch to AutomationProductionSet
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
			prod.getProdExport_ProductionSets().SendKeys("DefaultProductionSet");
		} else {
			webDriver.get("http://www.google.com");
		}

		driver.waitForPageToBeReady();

	}

	@And("^.*(\\[Not\\] )? on_ingestion_home_page$")
	public void on_ingestion_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		ingest = new IngestionPage(driver);

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
		savedSearch = new SavedSearch(driver);

		if (scriptState) {
			//
			//* User navigates to Saved Search page (/SavedSearch/SavedSearches)
			//* Saved Search page is displayed
			//
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"/SavedSearch/SavedSearches");
		} else {
			fail(dataMap, "Not on the saved search page");
		}

	}

	@And("^.*(\\[Not\\] )? on_batch_print_page$")
	public void on_batch_print_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		batchPrint = new BatchPrintPage(driver);

		if (scriptState) {
			//
			//* User navigates to Batch Print page (/BatchPrint)
			//* Batch Print page is displayed
			//
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"/BatchPrint/");

		} else {
			webDriver.get("http://www.google.com");
		}

	}

	@And("^.*(\\[Not\\] )? on_admin_home_page$")
	public void on_admin_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"/Project/Project");
		} else {
			webDriver.get("http://www.google.com");
		}

		driver.waitForPageToBeReady();

	}

	@And("^.*(\\[Not\\] )? on_ingestion_home_page$")
	public void on_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		ingest = new IngestionPage(driver);

		if (scriptState) {
			String url = (String) dataMap.get("URL");
			webDriver.get(url+"/Ingestion/Home");
		} else {
			webDriver.get("http://www.google.com");
		}
		driver.waitForPageToBeReady();

	}

	public String get_productions_drive_path() {
		String mountPath = "";
		if(SystemUtils.IS_OS_LINUX){
			mountPath = "";
		} else if(SystemUtils.IS_OS_WINDOWS){
			mountPath = "Z:" + File.separator + "H021301" + File.separator;
		} else if(SystemUtils.IS_OS_MAC){
			mountPath = File.separator + "Volumes" + File.separator + "Productions" + File.separator + "H021301" + File.separator;
		}

		return mountPath;
	}

	public String get_ingestions_drive_path() {
		String mountPath = "";
		return mountPath;
	}

	public void logoff(boolean scriptState, HashMap dataMap) {
		if (lp !=null) lp.logout();

		LoginPage.clearBrowserCache();
	}

	public void close_browser(boolean scriptState, HashMap dataMap) {
		try {
			logoff(scriptState, dataMap);
		}finally {
			if (lp !=null) lp.quitBrowser();
		}
		try {
			//final attempt to close any open browsers
			driver.close();
		} catch (Exception e) {
		}
	}

	public void pass(HashMap dataMap, String message) {
		log( dataMap,  LogStatus.PASS,  message);
	}
	public void fail(HashMap dataMap, String message) {
		log( dataMap,  LogStatus.FAIL,  message);
	}
	public void error(HashMap dataMap, String message) {
		log( dataMap,  LogStatus.ERROR,  message);
	}
	public void log(HashMap dataMap, LogStatus result, String message) {
		ExtentTest test = (dataMap != null) ? (ExtentTest) dataMap.get("ExtentTest") : null;
		if (test != null) {
			test.log(result, message);
		}
		SoftAssert sa= new SoftAssert();
		sa.assertTrue(result == LogStatus.PASS);

		if (result == LogStatus.FAIL) assert false;
	}

	public void log(HashMap dataMap, boolean result, String message) {
		log(dataMap,(result ? LogStatus.PASS : LogStatus.FAIL), message);
	}

	public void logTestResult(HashMap dataMap, String tid, String result, String description) {
		ArrayList testCaseResultList = (ArrayList) dataMap.get("TestCaseResults");
		HashMap testCaseResult = new HashMap();
		testCaseResult.put("tid", tid);
		testCaseResult.put("result", result);
		testCaseResult.put("description", description);
		testCaseResultList.add(testCaseResult);
	}

	public boolean validateMessage(HashMap dataMap, String expectedErrorTag, String foundMessage) {
		boolean errorMatched = false;
		JSONArray errorList = (JSONArray) dataMap.get("ui_messages");
		Iterator<JSONObject> errorsIterator = errorList.iterator();
		while (errorsIterator.hasNext()) {
			JSONObject error = errorsIterator.next();
			if (((String)error.get("tag")).equalsIgnoreCase(expectedErrorTag)) {
				errorMatched = (foundMessage.startsWith((String)error.get("message")));
				break;
			}
		}

		return errorMatched;
	}

}
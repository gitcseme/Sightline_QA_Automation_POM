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
public class SearchContext extends CommonContext {
	SessionSearch sessionSearch;

	/* 
	 * moved to CommonContext
	 * 
	public void sightline_is_launched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void login_as_pau(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void login(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void on_ingestion_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	 */

	/* 
	 * Ignore
	 * 
	public void create_search[nTimes](boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void create_search[ForEach_MetaData](boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void create_search[is](boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void create_search[full_text_search](boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void create_search[range](boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void create_search[long_search](boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void (boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	 */

	@When("^.*(\\[Not\\] )? goto_search_session_page$")
	public void goto_search_session_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//if (sessionSearch == null) {
				sessionSearch = new SessionSearch((String)dataMap.get("URL"),driver);
			//} else {
			//	webDriver.get((String)dataMap.get("URL")+sessionSearch.uri);
			//}
			dataMap.put("sessionSearch",sessionSearch);
		} else {
			webDriver.get("http://www.google.com");
		}
		driver.waitForPageToBeReady();

	}


	@Then("^.*(\\[Not\\] )? on_production_Search_Session_page$")
	public void on_production_Search_Session_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			//Verify title "Search"
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getPageTitle().Visible()  ;}}), Input.wait30); 

			//Verify Help Tip [?]
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getHelpTip().Visible()  ;}}), Input.wait30); 

			//Verify button [+ New Search]
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getNewSearch().Visible()  ;}}), Input.wait30); 

			//Verify "Count of unique DocIDs index in the project: ????"
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getUniqueCount().Visible()  ;}}), Input.wait30); 

			pass(dataMap,"On Session Search page.");

		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				// scriptState = false: not pass or fail. Just move on
			}
		}

	}

	@And("^.*(\\[Not\\] )? create_search$")
	public void create_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String searchType = (String) dataMap.get("searchType");
			if (searchType==null) searchType = "metaData";

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
			sessionSearch.getNewSearch().Click();

			if (searchType.equalsIgnoreCase("long")) {
				dataMap.put("searchString", "");
				createSearch(dataMap);
			} else {
				String searchString = (String)dataMap.get("searchValue");
				String metaDataOption = (String)dataMap.get("metaDataOption");
				String metaDataString = (String)dataMap.get("metaDataValue");
				String metaDataString2 = (String)dataMap.get("metaDataVal2");

				searchString = (metaDataString != null) ? metaDataString : searchString;
				dataMap.put("searchString", searchString);

				if (metaDataOption != null || searchString != null || metaDataString2 != null) {
					createSearch(searchType, metaDataOption, searchString, metaDataString2);
				}
			}
				

		} else {
			String searchType = (String) dataMap.get("searchType");
			if (searchType==null) searchType = "metaData";

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
			sessionSearch.getNewSearch().Click();
		}

	}

	@And("^.*(\\[Not\\] )? create_workproduct_search$")
	public void create_workproduct_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getActiveElementByXPath(sessionSearch.getWorkproductBtnXPath).Visible()  ;}}), Input.wait30); 
			if (sessionSearch.getActiveElementByXPath(sessionSearch.getWorkproductBtnXPath).Enabled()) {
				sessionSearch.getActiveElementByXPath(sessionSearch.getWorkproductBtnXPath).Click();
			}
			
			String searchType = (String) dataMap.get("workProductType");

			switch (searchType.toUpperCase()) {
				case "REDACTION":
					create_workproduct_redaction_search(scriptState,dataMap);
					break;					
				case "TAGS":
					break;					
				case "SECURITY":
					break;					
				case "FOLDERS":
					create_workproduct_folder_search(scriptState,dataMap);
					break;					
				case "SAVED":
					break;					
				case "PRODUCTION":
					break;					
			}

		} else {
			throw new ImplementationException("NOT create_workproduct_search");	
		}

	}
		
	@And("^.*(\\[Not\\] )? create_workproduct_redaction_search$")
	public void create_workproduct_redaction_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			JSONArray redactionLabels = (JSONArray) dataMap.get("labels");
			Iterator<JSONObject> labelsList = redactionLabels.iterator();
			String redactLabels = "";
			while (labelsList.hasNext()) {
				JSONObject data = labelsList.next();
				String redactionLabel = (String) data.get("label");
				if (redactLabels.length()>0) redactLabels = String.format("%s, ", redactLabels);
				if (sessionSearch.canSetRedactioninWPS(redactionLabel)) {
					redactLabels = String.format("%s\"%s\"", redactLabels, redactionLabel);
				} else {
					redactLabels = String.format("%s\"<unlisted>%s\"", redactLabels, redactionLabel);
				}
			}
			String searchString = (dataMap.get("searchString")==null) ? "" : (String) dataMap.get("searchString");
			
			searchString = String.format("%s%s",searchString,String.format("redactions: [ name: [%s] ]",redactLabels));
			dataMap.put("searchString", searchString);
		} else {
			throw new ImplementationException("NOT create_workproduct_redaction_search");	
		}

	}

	@And("^.*(\\[Not\\] )? create_workproduct_folder_search$")
	public void create_workproduct_folder_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			JSONArray folderLabels = (JSONArray) dataMap.get("labels");
			Iterator<JSONObject> labelsList = folderLabels.iterator();
			String labels = "";
			while (labelsList.hasNext()) {
				JSONObject data = labelsList.next();
				String label = (String) data.get("label");
				if (labels.length()>0) labels = String.format("%s, ", labels);
				if (sessionSearch.canSetFolderinWPS(label)) {
					labels = String.format("%s\"%s\"", labels, label);
				} else {
					labels = String.format("%s\"<unlisted>%s\"", labels, label);
				}
			}
			String searchString = (dataMap.get("searchString")==null) ? "" : (String) dataMap.get("searchString");
			
			searchString = String.format("%s%s",searchString,String.format("folder: [ name: [%s] ]",labels));
			dataMap.put("searchString", searchString);
		} else {
			throw new ImplementationException("NOT create_workproduct_folder_search");	
		}

	}

	@And("^.*(\\[Not\\] )? select_advanced_search_options$")
	public void select_advanced_search_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			JSONArray advancedOptions = (JSONArray) dataMap.get("advancedOptionList");
     	    Iterator<JSONObject> optionsList = advancedOptions.iterator();
	        while (optionsList.hasNext()) {
	        	JSONObject option = optionsList.next();
	        	if (((String)option.get("value")).equalsIgnoreCase("threaded")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					    	sessionSearch.getActiveElementByXPath(sessionSearch.AdvOption_threadedXPath).Enabled() ;}}), 10);
					sessionSearch.getActiveElementByXPath(sessionSearch.AdvOption_threadedXPath).Click();
	        	} else if (((String)option.get("value")).equalsIgnoreCase("family")) {
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						    	sessionSearch.getActiveElementByXPath(sessionSearch.AdvOption_familyXPath).Enabled() ;}}), 10);
						sessionSearch.getActiveElementByXPath(sessionSearch.AdvOption_familyXPath).Click();
	        	} else if (((String)option.get("value")).equalsIgnoreCase("near")) {
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						    	sessionSearch.getActiveElementByXPath(sessionSearch.AdvOption_nearXPath).Enabled() ;}}), 10);
						sessionSearch.getActiveElementByXPath(sessionSearch.AdvOption_nearXPath).Click();
		        }
	        }
			
		} else {
			throw new ImplementationException("NOT select_advanced_search_options");	
		}
	}
	
	@And("^.*(\\[Not\\] )? verify_advanced_search_options_notice$")
	public void verify_advanced_search_options_notice(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (dataMap.get("TestCase")!=null) ? (String) dataMap.get("TestCase") : "TBD";

		if (scriptState) {
			wait_for_loading(scriptState, dataMap);
	    	
			JSONArray advancedOptions = (JSONArray) dataMap.get("advancedOptionList");
     	    Iterator<JSONObject> optionsList = advancedOptions.iterator();
	        while (optionsList.hasNext()) {
	        	JSONObject option = optionsList.next();
	        	if (((String)option.get("value")).equalsIgnoreCase("threaded")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					    	sessionSearch.getActiveElementByXPath(sessionSearch.ThreadedLabelXPath).Enabled() ;}}), 10);
					String tileLabel = sessionSearch.getActiveElementByXPath(sessionSearch.ThreadedLabelXPath).getText();
					if (validateMessage(dataMap,"ThreadedTileLabel",tileLabel)) {
						String logMessage = "Found Threaded Tile label as expected.";
						pass(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"pass",logMessage);
						System.out.println(logMessage);
					} else {
						String logMessage = "Did not find Threaded Tile label as expected.";
						error(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"fail",logMessage);
						System.out.println(logMessage);
					}
	        	} else if (((String)option.get("value")).equalsIgnoreCase("family")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					    	sessionSearch.getActiveElementByXPath(sessionSearch.FamilyLabelXPath).Enabled() ;}}), 10);
					String tileLabel = sessionSearch.getActiveElementByXPath(sessionSearch.FamilyLabelXPath).getText();
					if (validateMessage(dataMap,"FamilyTileLabel",tileLabel)) {
						String logMessage = "Found Family Tile label as expected.";
						pass(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"pass",logMessage);
						System.out.println(logMessage);
					} else {
						String logMessage = "Did not find Family Tile label as expected.";
						error(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"fail",logMessage);
						System.out.println(logMessage);
					}
	        	} else if (((String)option.get("value")).equalsIgnoreCase("near")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					    	sessionSearch.getActiveElementByXPath(sessionSearch.NearDupeLabelXPath).Enabled() ;}}), 10);
					String tileLabel = sessionSearch.getActiveElementByXPath(sessionSearch.NearDupeLabelXPath).getText();
					if (validateMessage(dataMap,"NearTileLabel",tileLabel)) {
						String logMessage = "Found Near Tile label as expected.";
						pass(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"pass",logMessage);
						System.out.println(logMessage);
					} else {
						String logMessage = "Did not find Near Tile label as expected.";
						error(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"fail",logMessage);
						System.out.println(logMessage);
					}
		        }
	        }
			
		} else {
			throw new ImplementationException("NOT verify_advanced_search_options_notice");	
		}
	}
	
	@And("^.*(\\[Not\\] )? verify_wrong_query_message$")
	public void verify_wrong_query_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (String) dataMap.get("TestCase");

		if (scriptState) {
			String errorMsgType = (String) dataMap.get("errorMsgType");
			if (errorMsgType == null) errorMsgType = "band";
			
			try {
			} catch (Exception e) {
			}
			
			String foundErrorMessage = null;
			String expectedErrorTag = (String) dataMap.get("expectedErrorTag");
			if (errorMsgType.equalsIgnoreCase("band")) {
				// band type error message
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				    	sessionSearch.getQueryAlertGetTextSingleLine().Visible() ;}}), 10);

				try {
					foundErrorMessage = sessionSearch.getQueryAlertGetTextSingleLine().getText();
					if (foundErrorMessage == null || foundErrorMessage.trim().length() == 0) {
						foundErrorMessage = sessionSearch.getQueryAlertGetTextMultiLine().getText();
					}
					if (validateMessage(dataMap,expectedErrorTag,foundErrorMessage)) {
						String logMessage = String.format("Expected '%s' error message found",expectedErrorTag);
						pass(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"pass",logMessage);
						System.out.println(logMessage);
					} else {
						String logMessage = String.format("Found '%s', but expected '%s' error message!",foundErrorMessage,expectedErrorTag);
						error(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"fail",logMessage);
						System.out.println(logMessage);
					}
					
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					    	sessionSearch.getQueryPossibleWrongAlertContinueButton().Visible() ;}}), 10);
					boolean hitContinue = true;
					try {
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						    	sessionSearch.getQueryPossibleWrongAlertNoContinueButton().Visible() ;}}), 10);
			
						boolean continueSearch = (dataMap.get("continueSearch") == null) ? false : !((String)dataMap.get("continue")).equalsIgnoreCase("Yes") ? false : true; 
						if (!continueSearch && sessionSearch.getQueryPossibleWrongAlertNoContinueButton().Visible()) {
							sessionSearch.getQueryPossibleWrongAlertNoContinueButton().Click();
							
							// Verify warning message disappears
							driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							    	sessionSearch.getQueryAlertGetTextSingleLine().Visible() ;}}), 10);
							
							try {
								// should not be able to still see alert
								if (sessionSearch.getQueryAlertGetTextSingleLine().Visible()) {
									String logMessage = String.format("Found '%s', but expected to have disappeared!",foundErrorMessage,expectedErrorTag);
									error(dataMap,logMessage);
									logTestResult(dataMap,testCaseNo,"fail",logMessage);
									System.out.println(logMessage);
								} else {
									String logMessage = String.format("'%s' disappeared after click 'No'",expectedErrorTag);
									pass(dataMap,logMessage);
									logTestResult(dataMap,testCaseNo,"pass",logMessage);
									System.out.println(logMessage);
								}
							} catch (Exception e) {
								// alert is no longer visible
								String logMessage = String.format("'%s' disappeared after click 'No'",expectedErrorTag);
								pass(dataMap,logMessage);
								logTestResult(dataMap,testCaseNo,"pass",logMessage);
								System.out.println(logMessage);
							}
							
							
							hitContinue = false;
						}			
					} catch (Exception e) {
						
					}
					if (hitContinue) sessionSearch.getQueryPossibleWrongAlertContinueButton().Click();
				} catch (Exception e) {
					String logMessage = String.format("Expected '%s' error message not found!",expectedErrorTag);
					error(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"fail",logMessage);
					System.out.println(logMessage);
				}
			} else if (errorMsgType.equalsIgnoreCase("bigBoxes")) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				    	sessionSearch.getMessagePopupCloseBtn().Enabled() ;}}), 10);
				try {
					foundErrorMessage = sessionSearch.getMessagePopup().getText();

					if (validateMessage(dataMap,expectedErrorTag,foundErrorMessage)) {
						String logMessage = String.format("Expected '%s' 'popup' error message found",expectedErrorTag);
						pass(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"pass",logMessage);
						System.out.println(logMessage);
					} else {
						String logMessage = String.format("Found '%s', but expected '%s' 'popup' error message!",foundErrorMessage,expectedErrorTag);
						error(dataMap,logMessage);
						logTestResult(dataMap,testCaseNo,"fail",logMessage);
						System.out.println(logMessage);
					}
				} catch (Exception e) {
					String logMessage = String.format("Expected '%s' 'popup' error message not found!",expectedErrorTag);
					error(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"fail",logMessage);
					System.out.println(logMessage);
				}
				
			}
			
		 
		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			    	sessionSearch.getQueryAlertGetTextSingleLine().Visible() ;}}), 10);
			try {
				String foundErrorMessage = null;
				if (sessionSearch.getQueryAlertGetTextSingleLine().Visible()) {
					foundErrorMessage = sessionSearch.getQueryAlertGetTextSingleLine().getText();
					if (foundErrorMessage == null || foundErrorMessage.trim().length() == 0) {
						foundErrorMessage = sessionSearch.getQueryAlertGetTextMultiLine().getText();
					}
					String logMessage = String.format("Unexpected '%s' error message found",foundErrorMessage);
					fail(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"fail",logMessage);
					System.out.println(logMessage);		
				} else {
					String logMessage = String.format("No Unexpected error message found");
					pass(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"pass",logMessage);
					System.out.println(logMessage);		
				}
			} catch (Exception e) {
				String logMessage = String.format("No Unexpected error message found");
				pass(dataMap,logMessage);
				logTestResult(dataMap,testCaseNo,"pass",logMessage);
				System.out.println(logMessage);		
			}
		}
	}
	
	@And("^.*(\\[Not\\] )? verify_error_message$")
	public void verify_error_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getActiveElementByXPath(sessionSearch.DateValidationMsgXPath).Visible()  ;}}), Input.wait30); 
			String testCaseNo = (String) dataMap.get("TestCase");
			String foundErrorMessage = null;
			String expectedErrorTag = (String) dataMap.get("expectedErrorTag");
			try {
				foundErrorMessage = sessionSearch.getActiveElementByXPath(sessionSearch.DateValidationMsgXPath).getText();
				if (validateMessage(dataMap,expectedErrorTag,foundErrorMessage)) {
					String logMessage = String.format("Expected '%s' error message found",expectedErrorTag);
					pass(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"pass",logMessage);
					System.out.println(logMessage);
				} else {
					String logMessage = String.format("Found '%s', but expected '%s' error message!",foundErrorMessage,expectedErrorTag);
					error(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"fail",logMessage);
					System.out.println(logMessage);
				}
			} catch (Exception e) {
				String logMessage = String.format("Expected '%s' error message not found!",expectedErrorTag);
				error(dataMap,logMessage);
				logTestResult(dataMap,testCaseNo,"fail",logMessage);
				System.out.println(logMessage);
			}
		} else {
			throw new ImplementationException("NOT verify_error_message");	
		}
	}

	public void createSearch(HashMap dataMap) {
		JSONArray metaDataOptions = (JSONArray) dataMap.get("longSearchList");
 	    Iterator<JSONArray> optionsList = metaDataOptions.iterator();
        while (optionsList.hasNext()) {
        	JSONArray options = optionsList.next();
     	    Iterator<JSONObject> iterator = options.iterator();
     	    HashMap optionMap = new HashMap();
	        while (iterator.hasNext()) {
	        	JSONObject data = iterator.next();
	        	optionMap.put(data.get("name"), data.get("value"));
	        }
			String entrySearchType = (String) optionMap.get("entrySearchType");
			String option = (optionMap.get("metaDataOption") != null) ? (String) optionMap.get("metaDataOption") : (String) optionMap.get("condition");
			String metaDataValue = (String) optionMap.get("metaDataValue");
			String metaDataValue2 = (String) optionMap.get("metaDataValue2");
			
			String searchString = (String) dataMap.get("searchString");
			if (searchString == null) {
				searchString = "";
			}
			String updatedSearchString = createSearch(entrySearchType, option, metaDataValue, metaDataValue2);
			searchString = String.format("%s %s", searchString, updatedSearchString);
			dataMap.put("searchString", searchString);
		}
	}
	
	public String createSearch(String searchType, String option, String searchString, String searchString2) {
		if (searchType.equalsIgnoreCase("content")) {
			sessionSearch.enterBasicContentSearchString(searchString);
		} else if (searchType.equalsIgnoreCase("condition")) {
			sessionSearch.selectOperator(option);
			searchString = option;
		} else if (searchType.equalsIgnoreCase("metaData")) {
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( null,searchString,null);
			searchString = String.format("%s: (%s)", option, searchString);
		} else if (searchType.equalsIgnoreCase("is")) {
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( "IS",searchString,null);
			searchString = String.format("%s: (%s TO %s)", option, searchString, searchString);
		} else if (searchType.equalsIgnoreCase("range")) {
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( "RANGE",searchString,searchString2);
			searchString = String.format("%s: (%s TO %s)", option, searchString, searchString2);
		} else if (searchType.equalsIgnoreCase("fulltext")) {
			sessionSearch.enterBasicContentSearchString(searchString);
		}
		return searchString;
	}

	public void createAdvancedSearch(String searchType, String option, String searchString, String searchString2) throws ImplementationException {
		if (searchType.equalsIgnoreCase("content")) {
			sessionSearch.getActiveElementByXPath(sessionSearch.AdvancedContentSearchInputXPath).SendKeys(searchString) ;
		} else if (searchType.equalsIgnoreCase("metaData")) {
			//todo: this need to be reviewed for multiple uses - find active
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( null,searchString,null);
		} else if (searchType.equalsIgnoreCase("is")) {
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( "IS",searchString,null);
		} else if (searchType.equalsIgnoreCase("range")) {
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( "RANGE",searchString,searchString2);
		} else if (searchType.equalsIgnoreCase("range")) {
		} else if (searchType.equalsIgnoreCase("long")) {
			throw new ImplementationException("create search - long");
		} else if (searchType.equalsIgnoreCase("fulltext")) {
			throw new ImplementationException("create search - fulltext");
		}
		
	}

	@And("^.*(\\[Not\\] )? save_search$")
	public void save_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String searchName = String.format("search_%s",(new Date()).getTime());
			dataMap.put("searchName", searchName);
			sessionSearch.saveSearch(searchName);
		} else {
			throw new ImplementationException("NOT save_search");
		}

	}

	@When("^.*(\\[Not\\] )? verify_searched_save$")
	public void verify_searched_save(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String searchName = (String) dataMap.get("searchName");
			if (searchName == null) {
				fail(dataMap,"Search Name not provided");
			} else {
				if (sessionSearch.getSavedSearchName(searchName) != null) {
					pass(dataMap,String.format("Verified that '%s' was saved as Search",searchName));
				} else {
					fail(dataMap,String.format("Search Name '%s' not found as a 'Saved Search'",searchName));
				}
			}
		} else {
			throw new ImplementationException("NOT verify_searched_save");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_current_login_session_previous_search_query_selection$")
	public void verify_current_login_session_previous_search_query_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Test case 84 - Verify the current login session save search, previous search query selection
			ArrayList searches = (ArrayList) dataMap.get("searches");
			String searchName = (String) ((HashMap)searches.get(0)).get("name");
			String searchString = (String) ((HashMap)searches.get(0)).get("search");
			sessionSearch.getSavedSearchName(searchName).Click();
			String searchElementText = sessionSearch.getSearchString(1).getText();
			if (searchElementText.contains(searchString)) {
				String msg = String.format("Verified able to pull previous search query.",searchString);
				logTestResult(dataMap,"84","pass",msg);
				pass(dataMap,msg);
			} else {
				String msg = String.format("Previous search query not as expected. Expected '%s' to be part of '%s'.",searchString, searchElementText);
				logTestResult(dataMap,"84","fail",msg);
				pass(dataMap,msg);
			}
			
		} else {
			throw new ImplementationException("NOT verify_current_login_session_previous_search_query_selection");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_current_login_session_saved_search_SEARCH5$")
	public void verify_current_login_session_saved_search_SEARCH5(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//[Test Case 80 - verify_current_login_session_saved_search_SEARCH5]
			String nTimes = (String) dataMap.get("nTimes");

			ArrayList<HashMap> searches = (ArrayList<HashMap>) dataMap.get("searches");
			int foundCnt = 0;
			for (HashMap search : searches) {
				String searchName = (String) ((HashMap)searches.get(0)).get("name");
				String searchString = (String) ((HashMap)searches.get(0)).get("search");
				
				try {
					sessionSearch.getSavedSearchName(searchName).Click();
					String searchElementText = sessionSearch.getSearchString(1).getText();
					if (searchElementText.contains(searchString)) {
						foundCnt++;
					}
				} catch (Exception e) {
					// ignore - will handle with count 
				}
			}
			if (foundCnt == Integer.parseInt(nTimes)) {
				String msg = String.format("Verified able to create %s saved searches, was able to select each one and matched search string.",nTimes);
				logTestResult(dataMap,"80","pass",msg);
				pass(dataMap,msg);
			} else {
				String msg = String.format("Expected '%s' saved searches. Only found %s.",nTimes, foundCnt);
				logTestResult(dataMap,"80","fail",msg);
				pass(dataMap,msg);
			}
		} else {
			throw new ImplementationException("NOT verify_current_login_session_saved_search_SEARCH5");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_current_login_session_edit_previous_search_query$")
	public void verify_current_login_session_edit_previous_search_query(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//[Test Case 85 - verify the current login session save search edit on selected previous search query\
			//click on first created search
			ArrayList searches = (ArrayList) dataMap.get("searches");
			String searchName = (String) ((HashMap)searches.get(0)).get("name");
			String searchString = (String) ((HashMap)searches.get(0)).get("search");
			sessionSearch.getSavedSearchName(searchName).Click();
			//edit search
			Element searchElementEdit = sessionSearch.getEditSearchString(1);
			searchElementEdit.Click();
			String newSearchString = String.format(" OR (\"newSearchString %s\")",(new Date()).getTime());
			sessionSearch.getMetaDataSearchText1().SendKeys(newSearchString);
			sessionSearch.getMetaDataEditSearchBtn().Click();
			sessionSearch.getActiveSaveSearch_Button().Click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getsavesearch_overwrite().Visible()  ;}}), Input.wait30); 
			sessionSearch.getsavesearch_overwrite().Click();

			//move saveSearchpopup dialog up
			WebElement destElement = sessionSearch.getNewSearch().getWebElement().findElement(By.xpath("//span[@id='project-selector']")); 
		    WebElement dialogElement = sessionSearch.getSaveSearchPopup().getWebElement().findElement(By.xpath("//span[@id='ui-id-1']")); 
			Actions actions = new Actions(driver.driver);
			actions.moveToElement(dialogElement); 
			actions.clickAndHold(); 
			actions.moveToElement(destElement); 
			actions.release().perform();

			sessionSearch.getSaveSearch_SaveButton().Click();
			//sessionSearch.VerifySuccessMessage("Saved search overwritten successfully");
			
			//click on last created search
			int lastSearch = Integer.parseInt((String)dataMap.get("nTimes")) - 1;
			searchName = (String) ((HashMap)searches.get(lastSearch)).get("name");
			sessionSearch.getSavedSearchName(searchName).Click();

			//click back to first and check if changes kept
			searchName = (String) ((HashMap)searches.get(0)).get("name");
			sessionSearch.getSavedSearchName(searchName).Click();

			String searchElementText = sessionSearch.getSearchString(1).getText();
			if (searchElementText.contains(newSearchString)) {
				String msg = String.format("Verified able to edit previous search query.",searchString);
				logTestResult(dataMap,"85","pass",msg);
				pass(dataMap,msg);
			} else {
				String msg = String.format("Previous search query not edited as expected. Expected '%s' to be part of '%s'.",newSearchString, searchElementText);
				logTestResult(dataMap,"85","fail",msg);
				pass(dataMap,msg);
			}
			
		} else {
			throw new ImplementationException("NOT verify_current_login_session_edit_previous_search_query");
		}

	}


	@And("^.*(\\[Not\\] )? modify_search_criteria$")
	public void modify_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("modify_search_criteria");
		} else {
			throw new ImplementationException("NOT modify_search_criteria");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_modified_session_query_not_changed_saved_query$")
	public void verify_user_modified_session_query_not_changed_saved_query(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//[Test Case 149. - Verify that if user modified an In-Session search query then existing query should not get changed.
			throw new ImplementationException("verify_user_modified_session_query_not_changed_saved_query");
		} else {
			throw new ImplementationException("NOT verify_user_modified_session_query_not_changed_saved_query");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_search_criteria$")
	public void verify_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (dataMap.get("TestCase")!=null) ? (String) dataMap.get("TestCase") : "TBD";

		if (scriptState) {
			//
			String searchType = (String) dataMap.get("searchType");
			String metadataOption = (String) dataMap.get("metaDataOption");
			String metadataValue = (String) dataMap.get("metaDataValue");
			String metadataVal2 = (String) dataMap.get("metaDataVal2");

			String searchQuery = sessionSearch.getActiveElementByXPath(sessionSearch.SearchQueryTextXpath).getText();

			if (searchType.equalsIgnoreCase("MetaData") && searchQuery.equals(String.format("%s: (%s)", metadataOption, metadataValue))) {
				String msg = String.format("Search criterial matches for %s with value %s", metadataOption, metadataValue);
				logTestResult(dataMap,testCaseNo,"pass",msg);
				pass(dataMap,msg);
				System.out.println(msg);
			} else if (searchType.equalsIgnoreCase("Range") && searchQuery.equals(String.format("%s: [%s TO %s]", metadataOption, metadataValue, metadataVal2))) {
				String msg = String.format("Search criterial matches for %s with range %s to %s", metadataOption, metadataValue, metadataVal2);
				logTestResult(dataMap,testCaseNo,"pass",msg);
				pass(dataMap,msg);
				System.out.println(msg);
			} else {
				String msg = String.format("Search criterial DOES NOT match for %s with value %s", metadataOption, metadataValue);
				logTestResult(dataMap,testCaseNo,"fail",msg);
				error(dataMap,msg);
				fail(dataMap,msg);
				System.out.println(msg);
			}


		} else {
			throw new ImplementationException("NOT verify_search_criteria");
		}

	}

	@Then("^.*(\\[Not\\] )? remove_search_criteria$")
	public void remove_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String advancedSearch = (String) dataMap.get("advancedSearch");
			
			if (advancedSearch != null && advancedSearch.equalsIgnoreCase("yes")) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						sessionSearch.getActiveElementByXPath(sessionSearch.ModifySearchLocator).Visible()  ;}}), Input.wait30); 
				sessionSearch.getActiveElementByXPath(sessionSearch.ModifySearchLocator).Click();
			}
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchQueryText(1).Exists()  ;}}), Input.wait30); 
			sessionSearch.getSearchQueryText(1).Visible();
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						sessionSearch.getActiveElementByXPath(sessionSearch.SearchQueryTextXpath).Visible()  ;}}), Input.wait30);
				Element e = sessionSearch.removeSearchQueryRemove();
				try {
					e.Visible();
	        		try{
	        			Thread.sleep(1000);
	        		}catch (Exception e1) {
	
	        		}
					e.Click();
				} catch (Exception ex) {
					// it may NOT exist, so okay to not be visible or clickable
				}
				
				//sessionSearch.getSearchQueryText(1).Visible();
			} catch (Exception e) {
				e.printStackTrace();
				// should be removed
			}

		} else {
			throw new ImplementationException("NOT remove_search_criteria");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_is_search_criteria$")
	public void verify_is_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_is_search_criteria");
		} else {
			throw new ImplementationException("NOT verify_is_search_criteria");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_fulltext_search_criteria$")
	public void verify_fulltext_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_fulltext_search_criteria");
		} else {
			throw new ImplementationException("NOT verify_fulltext_search_criteria");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_range_search_criteria$")
	public void verify_range_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_range_search_criteria");
		} else {
			throw new ImplementationException("NOT verify_range_search_criteria");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_long_search_criteria$")
	public void verify_long_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_long_search_criteria");
		} else {
			throw new ImplementationException("NOT verify_long_search_criteria");
		}

	}

	@When("^.*(\\[Not\\] )? click_search$")
	public void click_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
	        //Click on Search button
			try { // try basic search
				sessionSearch.getActiveButtonById("btnBasicSearch").Click();

			} catch (Exception e) {
				sessionSearch.getActiveElementByXPath(sessionSearch.QuerySearchButton).Click();
			}
		} else {
			throw new ImplementationException("NOT click_search");
		}

	}


	@Then("^.*(\\[Not\\] )? wait_for_loading$")
	public void wait_for_loading(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
    	// while loading look for warnings, in case of proximity search
		String searchString = (String) dataMap.get("searchString");
		try {

        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			sessionSearch.getActiveElementByXPath(sessionSearch.SearchInProgressXPath).Visible() ;}}), 10);
        	
        	while (true) {
        		// continue to check for in progress
	        	while (sessionSearch.getActiveElementByXPath(sessionSearch.SearchInProgressXPath).Visible()) {  
	        		try{
	        			Thread.sleep(1000);
	        		}catch (Exception e) {

	        		}
	        	}
	        	
	        	//check for alert
	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        			sessionSearch.getQueryAlertGetText().Visible() ;}}), 10);
	        	try {
	        		String alertText = sessionSearch.getQueryAlertGetText().getText();
	        		if (alertText.contains("If you want to perform a stemming search")) {
	        			if (searchString.contains("*")) {
	        				logTestResult(dataMap,"10240","pass",String.format("Received '*' warning message as expected because search string '%s' contains '*'.",searchString));
	        			} else {
	        				logTestResult(dataMap,"9601","fail",String.format("Received '*' warning message though not expected. Search string '%s' does not contain an '*'.",searchString));
	        			}
		        		try{
		        			Thread.sleep(1000);
		        		}catch (Exception e) {
	
		        		}
	        		}
	    			sessionSearch.getTallyContinue().waitAndClick(5);
	        	} catch (Exception e) {
	        		// no alert - finished searching
	        		break;
	        	}
        	}
		} catch (Exception e) {
			
		}
	
	}
	
	@Then("^.*(\\[Not\\] )? verify_search_returned$")
	public void verify_search_returned(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (dataMap.get("TestCase")!=null) ? (String) dataMap.get("TestCase") : "TBD";
		String searchType = (String) dataMap.get("searchType");
		if (searchType == null) {
			searchType = "Search ";
		} else if (searchType.equalsIgnoreCase("MetaData")) {
			searchType = String.format("%s '%s' search ",searchType,dataMap.get("metaDataOption"));	
		} else if (searchType.equalsIgnoreCase("Comment")) {
			searchType = String.format("%s '%s' search ",searchType,dataMap.get("commentField"));	
		} else {
			searchType = String.format("%s search ",searchType);
		}
		String searchString = (String) dataMap.get("searchString");
		if (scriptState) {
			// first check for errors - no need to continue if create search error
			boolean searchError = false;
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				    	sessionSearch.getQueryAlertGetTextSingleLine().Visible() ;}}), 5);
				searchError = sessionSearch.getQueryAlertGetTextSingleLine().Visible();
			} catch (Exception e) {
				// apparently no errors
			}
				
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				    	sessionSearch.getMessagePopupCloseBtn().Enabled() ;}}), 5);
				searchError = sessionSearch.getMessagePopupCloseBtn().Enabled();
			} catch (Exception e1) {// apparently no errors
				
			}
				
			
			if (searchError) {
	    		String msg = String.format("%s is failed for '%s'.", searchType, searchString);
				pass(dataMap,msg);
				logTestResult(dataMap,testCaseNo,"pass",msg);
	    		System.out.println(msg);
				
			} else {
				wait_for_loading(scriptState, dataMap);
		    	
		    	//verify counts for specified tile
				String verifyTile = (String) dataMap.get("verifyTile");
				if (verifyTile == null) verifyTile = "PureHit";
				
				Element countElement = null;
				String countLocator = null;
				if (verifyTile.equalsIgnoreCase("PureHit")) {
					countLocator = sessionSearch.PureHitsCountLocator;
				} else if (verifyTile.equalsIgnoreCase("Threaded")) {
					countLocator = sessionSearch.TDCountLocator;
				} else if (verifyTile.equalsIgnoreCase("Near")) {
					countLocator = sessionSearch.NDCountLocator;
				} else if (verifyTile.equalsIgnoreCase("Family")) {
					countLocator = sessionSearch.FMCountLocator;
				} else if (verifyTile.equalsIgnoreCase("Conceptual")) {
					countLocator = sessionSearch.CSCountLocator;
			    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			    			sessionSearch.getActiveElementByXPath(sessionSearch.ConceptuallyPlayButton).Visible()  ;}}), Input.wait30);
			    	sessionSearch.getActiveElementByXPath(sessionSearch.ConceptuallyPlayButton).Click();
		        		try {
				        	while (sessionSearch.getActiveElementByXPath(sessionSearch.ConceptuallySearchInProgressXPath).Visible()) {  
				        		try{
				        			Thread.sleep(1000);
				        		}catch (Exception e) {
				        			//conceptual tile search done
				        		}
				        	
				        	}
		        		} catch (Exception e1) {
		        			//conceptual tile search done
		        		}
				}
				final String l = countLocator;
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			sessionSearch.getActiveElementByXPath(l).Visible()  ;}}), Input.wait30);
				countElement = sessionSearch.getActiveElementByXPath(countLocator);		
	
				final Element ce = countElement;
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ce.getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait30);
				
		    	int countHit = 0;
		    	try {
		    		countHit = Integer.parseInt(countElement.getText());
		    	} catch (Exception e) {
		    		System.out.println("failed to parse: "+countElement.getText());
		    		
		    	}
		    	Integer expectedCount = null;
		    	Boolean expectedAnyCount = false;
		    	try {
		    		if (verifyTile.equalsIgnoreCase("PureHit")) {
		    			expectedCount = new Integer((String)dataMap.get("expectedPureHit"));
		    		} else if (verifyTile.equalsIgnoreCase("Threaded")) {
		    			expectedCount = new Integer((String)dataMap.get("expectedThreadedCount"));
		    		} else if (verifyTile.equalsIgnoreCase("Near")) {
		    			expectedCount = new Integer((String)dataMap.get("expectedNearCount"));
		    		} else if (verifyTile.equalsIgnoreCase("Family")) {
		    			expectedCount = new Integer((String)dataMap.get("expectedFamilyCount"));
		    		}
	 	    	} catch (Exception e) {
		    		if (dataMap.get("continueSearch")!= null) {
		    			expectedAnyCount = ((String)dataMap.get("continueSearch")).equalsIgnoreCase("Yes");
		    		}
		    	}
		    		 
		    	if (expectedCount != null) {
			    	if (expectedCount == countHit) {
			    		String msg = String.format("%s is done for '%s' and %s is %s as expected.", searchType, searchString, verifyTile, countHit);
						pass(dataMap,msg);
						logTestResult(dataMap,testCaseNo,"pass",msg);
			    		System.out.println(msg);
			    	} else {
			    		String msg = String.format("ERROR: %s is done for '%s' and %s is %s but expected %s.", searchType, searchString, verifyTile, countHit, expectedCount);
						logTestResult(dataMap,testCaseNo,"fail",msg);
						error(dataMap,msg);
			    		System.out.println(msg);
			    	}
		    	} else {
		    		if (expectedAnyCount) {
				    	if (countHit > 0) {
				    		String msg = String.format("%s is done for '%s' and %s found '%s' matches as expected.", searchType, searchString, verifyTile, countHit);
							pass(dataMap,msg);
							logTestResult(dataMap,testCaseNo,"pass",msg);
				    		System.out.println(msg);
				    	} else {
				    		String msg = String.format("ERROR: %s is done for '%s' and no verifyTile were found.", searchType, searchString, verifyTile);
							logTestResult(dataMap,testCaseNo,"fail",msg);
							error(dataMap,msg);
				    		System.out.println(msg);
				    	}
		    		}
		    	}
	
		    	// verify display below tile if test case provided
		    	String verifyTileDisplayTestCase = (String) dataMap.get("verifyTileDisplayTestCase");
		    	if (verifyTileDisplayTestCase != null) {
	    			sessionSearch.hoverOverTileCount(countLocator);
		    		String msg = String.format("Hover over %s shows top 10 documents.", verifyTile);
					pass(dataMap,msg);
					logTestResult(dataMap,verifyTileDisplayTestCase,"pass",msg);
		    		System.out.println(msg);
		    	}
			}
		} else {
			throw new ImplementationException("NOT verify_search_returned");
		}

	}

	@Then("^.*(\\[Not\\] )? pin_search$")
	public void pin_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (dataMap.get("TestCase")!=null) ? (String) dataMap.get("TestCase") : "TBD";
		int pinSearchNo = 1;
		try {
			pinSearchNo = new Integer((String)dataMap.get("pinSearchNo"));
		} catch (Exception e) {
		}
		
		if (scriptState) {
			final int p = pinSearchNo;
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getUnsavedSearchItem(p).Visible()  ;}}), 10);	    	
	    	sessionSearch.getUnsavedSearchItemPin(pinSearchNo).Click();
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getUnsavedSearchItemPinned(p).Visible()  ;}}), 10);	    	
	    	try {
	    		if (sessionSearch.getUnsavedSearchItemPinned(pinSearchNo).Visible()) {
		    		String msg = String.format("Search #%s was pinned.", pinSearchNo);
					pass(dataMap,msg);
					logTestResult(dataMap,testCaseNo,"pass",msg);
		    		System.out.println(msg);
	    		} else {
		    		String msg = String.format("Search #%s was not pinned as expected.", pinSearchNo);
					error(dataMap,msg);
					logTestResult(dataMap,testCaseNo,"fail",msg);
		    		System.out.println(msg);
	    		}
	    	} catch (Exception e) {
	    		String msg = String.format("Search #%s was not pinned as expected.", pinSearchNo);
				error(dataMap,msg);
				logTestResult(dataMap,testCaseNo,"fail",msg);
	    		System.out.println(msg);
	    	}
	    	
	    	
		} else {
			throw new ImplementationException("NOT context_pin_search");
		}
	}

	@Then("^.*(\\[Not\\] )? verify_first_search_pin$")
	public void verify_first_search_pin(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (dataMap.get("TestCase2")!=null) ? (String) dataMap.get("TestCase2") : "TBD";
		if (scriptState) {
			int pinSearchNo = 1;
			final int p = pinSearchNo;

	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getUnsavedSearchItemPinned(p).Visible()  ;}}), 10);	    	
	    	try {
	    		if (sessionSearch.getUnsavedSearchItemPinned(pinSearchNo).Visible()) {
		    		String msg = String.format("Search #%s pinned remained as pinned.", pinSearchNo);
					pass(dataMap,msg);
					logTestResult(dataMap,testCaseNo,"pass",msg);
		    		System.out.println(msg);
	    		} else {
		    		String msg = String.format("Search #%s did not remained pinned as expected.", pinSearchNo);
					error(dataMap,msg);
					logTestResult(dataMap,testCaseNo,"fail",msg);
		    		System.out.println(msg);
	    		}
	    	} catch (Exception e) {
	    		String msg = String.format("Search #%s did not remained pinned as expected.", pinSearchNo);
				error(dataMap,msg);
				logTestResult(dataMap,testCaseNo,"fail",msg);
	    		System.out.println(msg);
	    	}
		} else {
			throw new ImplementationException("NOT verify_first_search_pin");
		}
	}

	@And("^.*(\\[Not\\] )? start_advanced_search$")
	public void start_advanced_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
			sessionSearch.getNewSearch().Click();

	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getAdvancedSearchLink().Visible()  ;}}), 10); 
	    	try {
	    		sessionSearch.getAdvancedSearchLink().Click();
	    	} catch (Exception e) {
	    		// probably already clicked...
	    	}

		} else {
			throw new ImplementationException("NOT start_advanced_search");
		}

	}

	@And("^.*(\\[Not\\] )? select_advanced_search$")
	public void select_advanced_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			start_advanced_search(scriptState, dataMap);

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getActiveElementByXPath(sessionSearch.ContentAndMetaDatabtnXPath).Visible()  ;}}), Input.wait30); 
	    	sessionSearch.getActiveElementByXPath(sessionSearch.ContentAndMetaDatabtnXPath).Click();

	    	String metaDataField = (String) dataMap.get("metaDataOption");
			String val1 = (String) dataMap.get("metaDataValue");
			if (scriptState) {
				sessionSearch.selectMetaDataOption(metaDataField);
				sessionSearch.getMetaDataSearchText1().SendKeys(val1);
			} else {
				if (metaDataField != null) {
					sessionSearch.selectMetaDataOption(metaDataField);
					if (val1 != null) {
						sessionSearch.getMetaDataSearchText1().SendKeys(val1);
					}
				}
			}
		} else {
			throw new ImplementationException("NOT select_advanced_search");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_autosuggest$")
	public void verify_autosuggest(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (dataMap.get("TestCase")!=null) ? (String) dataMap.get("TestCase") : "TBD";
		
		String err;
		boolean autoSuggested = false;
		String searchText = (String) dataMap.get("metaDataValue");

		//
		//* check for autosuggest within 3 seconds timer|TC#5768
		try{
			Thread.sleep(3000);
		}catch (Exception e) {

		}

		String subTestCaseNo = (dataMap.get("advancedSearch")!=null && ((String)dataMap.get("advancedSearch")).equalsIgnoreCase("yes")) ? "TBD" : "5708";

		Element autoSuggest = null;
		boolean autoSuggestVisible = false;
		String suggestText = "";
		try {
			autoSuggest = sessionSearch.getActiveElementByXPath(sessionSearch.AutoSuggestXPath);
			autoSuggestVisible = autoSuggest.Visible();
		} catch (Exception e) {
			// either could not find or autosuggest element was not visible
		}
		if (scriptState && !autoSuggestVisible) {
			err = "No auto suggest caused as expected.";
			logTestResult(dataMap,subTestCaseNo,"fail",err);
			error(dataMap,err);
		} else if (!scriptState && !autoSuggestVisible) {
			logTestResult(dataMap,"TBD","pass", "Auto suggest not found as expected.");
		} else {  
			// at this point autuSuggest is visible
			try {
				autoSuggest = sessionSearch.getActiveElementByXPath(sessionSearch.AutoSuggestXPath);
				suggestText = autoSuggest.getWebElement().getText();
			} catch (Exception e) {
				// either could not find or autosuggest element was not visible
			}

			if (suggestText.length()>0) {
				logTestResult(dataMap,subTestCaseNo,"pass",String.format("Creating search entry with matching MetaData text (using '%s') causes autosuggest to be provided",searchText));
			} else if (!scriptState) {
				logTestResult(dataMap,"TBD","pass","Auto suggest had no text as expected.");
			}

			autoSuggested = suggestText.toUpperCase().startsWith(searchText.toUpperCase());
			if (!autoSuggested) {
				err = String.format("Auto suggest with first %s chars does not match search text.",searchText.length());
				logTestResult(dataMap,"5768","fail",err);
				//fail(dataMap,err);
			} else {
				if (scriptState) logTestResult(dataMap,"5768","pass",String.format("Auto suggest with first %s chars matched",searchText.length()));

				String keys = (String) dataMap.get("additionalKeys");
				searchText = searchText + keys;
				sessionSearch.getMetaDataSearchText1().SendKeys(keys);
				try{
					Thread.sleep(3000);
				}catch (Exception e) {

				}

				try {
					autoSuggest = sessionSearch.getActiveElementByXPath(sessionSearch.AutoSuggestXPath);
				} catch (Exception e) {
					// may not be available
				}
				if (!scriptState && (autoSuggest == null || !autoSuggest.Visible())) {
					logTestResult(dataMap,testCaseNo,"pass",String.format("Auto suggest not found with search text '%s' as expected.",searchText));
				} else {
					suggestText = autoSuggest.getText();
					autoSuggested = suggestText.toUpperCase().startsWith(searchText.toUpperCase());
					if (!autoSuggested) {
						err = String.format("Auto suggest with %s chars does not match search text.",searchText.length());
						logTestResult(dataMap,"5768","fail",err);
						//fail(dataMap,err);
					} else {
						logTestResult(dataMap,"5768","pass",String.format("Auto suggest with %s chars matched",searchText.length()));

						//* Enter {additionalKeys}
						keys = (String) dataMap.get("additionalKeys1");
						searchText = searchText + keys;
						sessionSearch.getMetaDataSearchText1().SendKeys(keys);
						try{
							Thread.sleep(3000);
						}catch (Exception e) {

						}
						try {
							autoSuggest = sessionSearch.getActiveElementByXPath(sessionSearch.AutoSuggestXPath);
							suggestText = autoSuggest.getText();
							autoSuggested = suggestText.toUpperCase().startsWith(searchText.toUpperCase());
						} catch (Exception e) {
							autoSuggested = false;
						}
						if (!autoSuggested) {
							err = String.format("Auto suggest with %s chars does not match search text.",searchText.length());
							logTestResult(dataMap,"5768","fail",err);
							error(dataMap,err);
						} else {
							logTestResult(dataMap,"5768","pass",String.format("Auto suggest with %s chars matched",searchText.length()));

							if (scriptState) {
								pass(dataMap,String.format("Auto suggest is provided when entering search matched (using '%s') text and pausing.",searchText));
							} else {
								err = String.format("Auto suggest should not have succeeded with '%s' text entered.",searchText);
								logTestResult(dataMap,testCaseNo,"fail",err);
								error(dataMap,err);
								
							}
						}
					}
				}
			}
		}

	}

	@And("^.*(\\[Not\\] )? cancel_metadata_insert$")
	public void cancel_metadata_insert(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				sessionSearch.getActiveElementByXPath(sessionSearch.MetaDataCancelButtonXPath).Visible()  ;}}), Input.wait30); 
		sessionSearch.getActiveElementByXPath(sessionSearch.MetaDataCancelButtonXPath).Click();	
	}
	
	@And("^.*(\\[Not\\] )? select_search$")
	public void select_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		select_metadata(scriptState, dataMap);
		
		String val1 = (String) dataMap.get("metaDataValue");
		if (scriptState) {
			sessionSearch.getMetaDataSearchText1().SendKeys(val1);
		} else {
			if (val1 != null) {
				sessionSearch.getMetaDataSearchText1().SendKeys(val1);
			}
		}

	}

	@And("^.*(\\[Not\\] )? select_metadata$")
	public void select_metadata(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		String metaDataField = (String) dataMap.get("metaDataOption");
		if (scriptState) {
			sessionSearch.selectMetaDataOption(metaDataField);
		} else {
			if (metaDataField != null) {
				sessionSearch.selectMetaDataOption(metaDataField);
			}
		}

	}

	@And("^.*(\\[Not\\] )? verify_tooltip$")
	public void verify_tooltip(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (String) dataMap.get("TestCase");
			
		if (scriptState) {
			String entryType = (String) dataMap.get("entryType");
			if (entryType.equalsIgnoreCase("value")) {
				String toolTipTag = (String) dataMap.get("toolTipTag");
				String ToolTipText = sessionSearch.getToolTipText(sessionSearch.getSingleToolTipIcon(),sessionSearch.getSingleToolTipText());
				
				if (validateMessage(dataMap, toolTipTag, ToolTipText)) {
					String logMessage = "Found 'Value' ToolTip text as expected.";
					pass(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"pass",logMessage);
					System.out.println(logMessage);
				} else {
					String logMessage = "Did not find 'Value' ToolTip text as expected.";
					error(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"fail",logMessage);
					System.out.println(logMessage);
				}
			} else if (entryType.equalsIgnoreCase("is")) {
				sessionSearch.getMetaOption().selectFromDropdown().selectByVisibleText("IS (:)");
				String fromToolTipTag = (String) dataMap.get("fromToolTipTag");
				String fromToolTipText = sessionSearch.getToolTipText(sessionSearch.getSingleToolTipIcon(),sessionSearch.getSingleToolTipText());
				
				if (validateMessage(dataMap, fromToolTipTag, fromToolTipText)) {
					String logMessage = "Found 'is' ToolTip text as expected.";
					pass(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"pass",logMessage);
					System.out.println(logMessage);
				} else {
					String logMessage = "Did not find 'is' ToolTip text as expected.";
					error(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"fail",logMessage);
					System.out.println(logMessage);
				}
			} else if (entryType.equalsIgnoreCase("range")) {
				sessionSearch.getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
				String fromToolTipTag = (String) dataMap.get("fromToolTipTag");
				String fromToolTipText = sessionSearch.getToolTipText(sessionSearch.getRangeToolTipIcon1(),sessionSearch.getRangeToolTip1Text());
				if (validateMessage(dataMap, fromToolTipTag, fromToolTipText)) {
					String logMessage = "Found 'From Range' ToolTip text as expected.";
					pass(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"pass",logMessage);
					System.out.println(logMessage);
				} else {
					String logMessage = "Did not find 'To Range' ToolTip text as expected.";
					error(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"fail",logMessage);
					System.out.println(logMessage);
				}

				String toToolTipTag = (String) dataMap.get("toToolTipTag");
				String toToolTipText = sessionSearch.getToolTipText(sessionSearch.getRangeToolTipIcon2(),sessionSearch.getRangeToolTip2Text());
				if (validateMessage(dataMap, toToolTipTag, toToolTipText)) {
					String logMessage = "Found 'is' ToolTip text as expected.";
					pass(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"pass",logMessage);
					System.out.println(logMessage);
				} else {
					String logMessage = "Did not find 'is' ToolTip text as expected.";
					error(dataMap,logMessage);
					logTestResult(dataMap,testCaseNo,"fail",logMessage);
					System.out.println(logMessage);
				}
				
			}
		} else {
			throw new ImplementationException("NOT verify_tooltip");
		}

	}

	@When("^.*(\\[Not\\] )? cancel_save_search$")
	public void cancel_save_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String searchName = String.format("search_%s",(new Date()).getTime());
			dataMap.put("searchName", searchName);
			sessionSearch.cancelSaveSearch(searchName);
		} else {
			throw new ImplementationException("NOT cancel_save_search");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_search_not_saved$")
	public void verify_search_not_saved(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (dataMap.get("TestCase")!=null) ? (String) dataMap.get("TestCase") : "TBD";

		if (scriptState) {
			String searchName = (String) dataMap.get("searchName");
			if (searchName == null) {
				error(dataMap,"Search Name not provided");
			} else {
				Element savedSearch = sessionSearch.getSavedSearchName(searchName);
				try {
					String msg = String.format("Search saved as '%s' but not expected to saved as Search",searchName);
					savedSearch.Exists();
					logTestResult(dataMap,testCaseNo,"fail",msg);
					error(dataMap,msg);
				} catch (Exception e) {
					String msg = String.format("Search Name '%s' not found as a 'Saved Search' as expected",searchName);
					logTestResult(dataMap,testCaseNo,"pass",msg);
					pass(dataMap,msg);					
				}
			}
		} else {
			throw new ImplementationException("NOT verify_searched_not_save");
		}


	}

	@And("^.*(\\[Not\\] )? create_advanced_search$")
	public void create_advanced_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String searchType = (String) dataMap.get("searchType");
		if (searchType==null) searchType = "metaData";
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
		sessionSearch.getNewSearch().Click();

		if (scriptState) {
			boolean alreadyAtAdvanced = false;
			try {
				
				alreadyAtAdvanced = sessionSearch.getActiveElementByXPath(sessionSearch.AdvanceLabelXPath).Visible();
			} catch (Exception e) {
				// not yet on AdvancedSearchUI
			}

			if (!alreadyAtAdvanced) {
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			sessionSearch.getActiveElementByXPath(sessionSearch.AdvancedSearchLinkXPath).Visible()  ;}}), Input.wait30); 
		    	sessionSearch.getActiveElementByXPath(sessionSearch.AdvancedSearchLinkXPath).Click();
			}
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getActiveElementByXPath(sessionSearch.ContentAndMetaDatabtnXPath).Visible()  ;}}), Input.wait30); 
	    	sessionSearch.getActiveElementByXPath(sessionSearch.ContentAndMetaDatabtnXPath).Click();

			String searchString = (String)dataMap.get("searchValue");
			String metaDataOption = (String)dataMap.get("metaDataOption");
			String metaDataString = (String)dataMap.get("metaDataValue");
			String metaDataString2 = (String)dataMap.get("metaDataVal2");

			searchString = (metaDataString != null) ? metaDataString : searchString;
			dataMap.put("searchString", searchString);
			
			createAdvancedSearch(searchType, metaDataOption, searchString, metaDataString2);

		} else {
			throw new ImplementationException("NOT create_advanced_search");
		}

	}

	@And("^.*(\\[Not\\] )? create_comment_search$")
	public void create_comment_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
		sessionSearch.getNewSearch().Click();

		if (scriptState) {
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getActiveElementByXPath(sessionSearch.CommentsbtnXPath).Visible()  ;}}), Input.wait30); 
	    	sessionSearch.getActiveElementByXPath(sessionSearch.CommentsbtnXPath).Click();

			String commentField = (String)dataMap.get("commentField");
			String commentValue = (String)dataMap.get("commentValue");

			dataMap.put("searchString", commentValue);
			
			sessionSearch.selectCommentFieldOption(commentField);
			sessionSearch.setCommentValue(commentValue);

		} else {
			throw new ImplementationException("NOT create_comment_search");
		}
	}

	@And("^.*(\\[Not\\] )? create_advanced_comment_search$")
	public void create_advanced_comment_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
		sessionSearch.getNewSearch().Click();

		if (scriptState) {
			boolean alreadyAtAdvanced = false;
			try {
				
				alreadyAtAdvanced = sessionSearch.getActiveElementByXPath(sessionSearch.AdvanceLabelXPath).Visible();
			} catch (Exception e) {
				// not yet on AdvancedSearchUI
			}

			if (!alreadyAtAdvanced) {
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			sessionSearch.getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
		    	sessionSearch.getAdvancedSearchLink().Click();
			}
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getActiveElementByXPath(sessionSearch.ContentAndMetaDatabtnXPath).Visible()  ;}}), Input.wait30); 
	    	sessionSearch.getActiveElementByXPath(sessionSearch.ContentAndMetaDatabtnXPath).Click();			
			
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getActiveElementByXPath(sessionSearch.CommentsbtnXPath).Visible()  ;}}), Input.wait30); 
	    	sessionSearch.getActiveElementByXPath(sessionSearch.CommentsbtnXPath).Click();

			boolean exclude = (dataMap.get("exclude") != null && ((String)dataMap.get("exclude")).equalsIgnoreCase("yes"));
			String commentField = (String)dataMap.get("commentField");
			String commentValue = (String)dataMap.get("commentValue");

			dataMap.put("searchString", commentValue);
			
			//TODO: figure out how to exclude a comment
			sessionSearch.selectCommentFieldOption(commentField);
			sessionSearch.setCommentValue(commentValue);

		} else {
			throw new ImplementationException("NOT create_advanced_comment_search");
		}

	}

	@And("^.*(\\[Not\\] )? plus_icon_add_to_shopping_cart$")
	public void plus_icon_add_to_shopping_cart(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			Iterator<JSONObject> tiles = getIterator(dataMap, "tiles");
			while (tiles.hasNext()) {
				JSONObject tileData = tiles.next();
				String tileLabel = (String) tileData.get("tileLabel");
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			sessionSearch.getActiveElementByXPath(sessionSearch.getTileThatMetCriteriaAddBtn(tileLabel)).Visible()  ;}}), Input.wait30); 
		    	Element tileElement = sessionSearch.getActiveElementByXPath(sessionSearch.getCountLocator(tileLabel));
		    	String elementId = tileElement.GetAttribute("id");
		    	String[] tileKeys = elementId.split("-");
		    	String searchId = tileKeys[1];
		    	String tileTypeId = tileKeys[2];
		    	String count = tileElement.getText();
		    	JSONArray tilesAddedToShoppingCart = (JSONArray) dataMap.get("tilesAddedToShoppingCart");
		    	JSONObject tileDescriptor = new JSONObject();
		    	tileDescriptor.put("searchId", searchId);
		    	tileDescriptor.put("tileTypeId", tileTypeId);
		    	tileDescriptor.put("count", count);
		    	tilesAddedToShoppingCart.add(tileDescriptor);	
		    	
		    	dataMap.put("lastSearchPinned",searchId);
		    	sessionSearch.getActiveElementByXPath(sessionSearch.getTileThatMetCriteriaAddBtn(tileLabel)).Click();
			}

		} else {
			throw new ImplementationException("NOT plus_icon_add_to_shopping_cart");
		}

	}
	
	@And("^.*(\\[Not\\] )? plus_icon_add_to_shopping_cart$")
	public void verify_plus_icon_not_possible_while_search_loading(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = getTestCaseNo(dataMap);
		if (scriptState) {
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			sessionSearch.getActiveElementByXPath(sessionSearch.SearchInProgressXPath).Visible() ;}}), 10);

        	String msg = "";
        	Iterator<JSONObject> tiles = getIterator(dataMap, "tiles");
			while (tiles.hasNext()) {
				JSONObject tileData = tiles.next();
				String tileLabel = (String) tileData.get("tileLabel");


				try {
			    	sessionSearch.getActiveElementByXPath(sessionSearch.getTileThatMetCriteriaAddBtn(tileLabel)).Click();
			    	//if get here, was able to click which it should
			    	// check if search completed too soon
			    	try {
			    		if (sessionSearch.getActiveElementByXPath(sessionSearch.SearchInProgressXPath).Visible()) {
			    			// bad news
							msg = String.format("Able to add Tile '%s' to Shopping cart before search completed.",tileLabel);
							logTestResult(dataMap,testCaseNo,"fail",msg);
							error(dataMap,msg);					
			    		} else {
			    			// unfortunately search completed too soon
							msg = String.format("Able to add Tile '%s' to Shopping cart but search completed. Need longer search cycle.",tileLabel);
							logTestResult(dataMap,testCaseNo,"fail",msg);
							error(dataMap,msg);					
			    		}
			    	} catch (Exception e1) {
		    			// unfortunately search completed too soon
						msg = String.format("Able to add Tile '%s' to Shopping cart but search completed. Need longer search cycle.",tileLabel);
						logTestResult(dataMap,testCaseNo,"fail",msg);
						error(dataMap,msg);								    		
			    	}
			    	
				} catch (Exception e) {
					// expect it to fail here - should not be available
					msg = String.format("Unable to add Tile '%s' to Shopping cart as expected.",tileLabel);
					logTestResult(dataMap,testCaseNo,"pass",msg);
					pass(dataMap,msg);								    		
				}	
				System.out.println(msg);
			}

		} else {
			throw new ImplementationException("NOT plus_icon_add_to_shopping_cart");
		}

	}
	
	@And("^.*(\\[Not\\] )? verify_added_tiles_in_shopping_cart$")
	public void verify_added_tiles_in_shopping_cart(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = getTestCaseNo(dataMap);
		if (scriptState) {
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getResultCart().Visible()  ;}}), Input.wait30);

	    	Element shoppingCart = sessionSearch.getResultCart();

	    	ElementCollection shoppingCartTiles = sessionSearch.getResultTiles();
	    	JSONObject tilesInShoppingCart = new JSONObject();
	    	for (int c=1; c<shoppingCartTiles.size()+1;c++) {
	    		WebElement tile = shoppingCart.getWebElement().findElement(By.xpath(String.format("(//span/count)[%s]",c)));

	    		JSONObject tileData = new JSONObject();
	    		tileData.put("count", tile.getText());
	    		String[] tileIds = tile.getAttribute("id").split("-");
	    		tilesInShoppingCart.put(String.format("%s-%s", tileIds[1], tileIds[2]),tileData);  //(1st) countCount-1344-001 (following) tilecount-1360-002
	    	}

	    	Iterator<JSONObject> iterator = getIterator(dataMap, "tilesAddedToShoppingCart");
			int foundInShoppingCart = 0;
			int shoppingCartCount = 0;
			boolean countMatch = true;
			while (iterator.hasNext()) {
				JSONObject data = iterator.next();
		    	String searchId = (String) data.get("searchId");
		    	String tileTypeId = (String) data.get("tileTypeId");
		    	String count = (String) data.get("count");
		    	JSONObject shoppinCartData = (JSONObject) tilesInShoppingCart.get(String.format("%s-%s", searchId, tileTypeId));
		    	if (shoppinCartData != null) {
		    		foundInShoppingCart++;
		    		if (!count.equalsIgnoreCase((String)shoppinCartData.get("count"))) {
		    			countMatch = false;
		    		}
		    	}
		    	shoppingCartCount++;
			}
	    	
			String msg = "";
			if (foundInShoppingCart == shoppingCartCount) {
				msg = String.format("Found all %s added search results in shopping cart.",foundInShoppingCart);
				logTestResult(dataMap,testCaseNo,"pass",msg);
				pass(dataMap,msg);					
			} else {
				msg = String.format("ERROR: Did not find all '%s' added search results in shopping cart",foundInShoppingCart);
				logTestResult(dataMap,testCaseNo,"fail",msg);
				error(dataMap,msg);
			}
			System.out.println(msg);
			if (countMatch) {
				msg = String.format("Count for all %s added search results in shopping cart match.",foundInShoppingCart);
				logTestResult(dataMap,testCaseNo,"pass",msg);
				pass(dataMap,msg);					
			} else {
				msg = String.format("ERROR: Count for all %s added search results in shopping cart DO NOT match",foundInShoppingCart);
				logTestResult(dataMap,testCaseNo,"fail",msg);
				error(dataMap,msg);
			}
			System.out.println(msg);
		} else {
			throw new ImplementationException("NOT verify_added_tiles_in_shopping_cart");
		}

	}

	@And("^.*(\\[Not\\] )? unpin_tiles_in_shopping_cart$")
	public void unpin_tiles_in_shopping_cart(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = getTestCaseNo(dataMap);
		if (scriptState) {
			String lastSearchPinned = (String) dataMap.get("lastSearchPinned");
			Iterator<JSONObject> tiles = getIterator(dataMap, "unpinTiles");
			while (tiles.hasNext()) {
				JSONObject tileData = tiles.next();
				String tileLabel = (String) tileData.get("tileLabel");
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			sessionSearch.getActiveElementByXPath(sessionSearch.ResultTileXPath(lastSearchPinned,tileLabel)).Visible()  ;}}), Input.wait30); 

		    	Element tileElement = sessionSearch.getActiveElementByXPath(sessionSearch.ResultTileXPath(lastSearchPinned,tileLabel));
		    	WebElement removeButton = tileElement.getWebElement().findElement(By.xpath("//i[@title='Remove from Selected Results']"));
		    	removeButton.click();

		    	//Attempted various approaches to not use sleep, like testing with element no longer visible or enabled
		    	// but nothing work so finally resorted to sleep. Even tried the original element.
//		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//		    			removeButton.isEnabled()  ;}}), 5); 
//		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//		    			removeButton.isDisplayed()  ;}}), 5); 
//		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//		    			sessionSearch.getActiveElementByXPath(sessionSearch.ResultTileXPath(lastSearchPinned,tileLabel)).Visible()  ;}}), Input.wait30); 
        		try{
        			Thread.sleep(1000);
        		}catch (Exception e) {

        		}

			}

		} else {
			throw new ImplementationException("NOT unpin_tiles_in_shopping_cart");
		}

	}

	@And("^.*(\\[Not\\] )? verify_tiles_sequence_in_results$")
	public void verify_tiles_sequence_in_results(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = getTestCaseNo(dataMap);
		if (scriptState) {
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getResultsTab().Visible()  ;}}), Input.wait30); 

	    	Element resultsTab = sessionSearch.getResultsTab();
	    	List<WebElement> resultsTabChildren = resultsTab.getWebElement().findElements(By.xpath("//i"));
	    	
	    	Iterator<JSONObject> tiles = getIterator(dataMap, "availableTiles");
			int tilePosInSequence = 0;
			boolean areExpectedTilesVisible = true;
			boolean areTilesSeqCorrectly = true;
			while (tiles.hasNext()) {
				JSONObject tileData = tiles.next();
				String tileLabel = (String) tileData.get("tileLabel");
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			sessionSearch.getActiveElementByXPath(sessionSearch.getTileThatMetCriteriaAddBtnAfterUnpinned(tileLabel)).Visible()  ;}}), Input.wait30); 

		    	if (sessionSearch.getActiveElementByXPath(sessionSearch.getTileThatMetCriteriaAddBtnAfterUnpinned(tileLabel)).Enabled())  {
		    		Element tileElement = sessionSearch.getActiveElementByXPath(sessionSearch.getTileThatMetCriteriaAddBtnAfterUnpinned(tileLabel));
		    		int tilePosition = resultsTabChildren.indexOf(tileElement);
		    		if (tilePosition < tilePosInSequence) {
		    			areTilesSeqCorrectly = false;
		    		} else {
		    			tilePosition = tilePosInSequence;
		    		}
		    	} else {
					String msg = String.format("ERROR: tile '%s' not available as expected.",tileLabel);
					logTestResult(dataMap,testCaseNo,"fail",msg);
					error(dataMap,msg);
					System.out.println(msg);
					areExpectedTilesVisible = false;
		    	}
			}

			if (areExpectedTilesVisible) {
				String msg = "";
				if (areTilesSeqCorrectly) {
					msg = String.format("Tiles are sequenced in results tab as expected.");
					logTestResult(dataMap,testCaseNo,"pass",msg);
					pass(dataMap,msg);					
				} else {
					msg = String.format("ERROR: Tiles are not sequenced correctly in results tab as expected.");
					logTestResult(dataMap,testCaseNo,"fail",msg);
					error(dataMap,msg);					
				}
				System.out.println(msg);
			}
		} else {
			throw new ImplementationException("NOT verify_tiles_sequence_in_results");
		}
	}

	public Iterator getIterator(HashMap dataMap, String arrayName) {
		JSONArray array = (JSONArray) dataMap.get(arrayName);
		return array.iterator();		
	}
	
	public void loadData(HashMap dataMap, JSONArray array) {
		Iterator<JSONObject> iterator = array.iterator();
		while (iterator.hasNext()) {
			JSONObject data = iterator.next();
			dataMap.put(data.get("name"), data.get("value"));
		}
	}

	public String getTestCaseNo(HashMap dataMap) {
		return (dataMap.get("TestCase")!=null) ? (String) dataMap.get("TestCase") : "TBD";
	}

	/*
	JSONArray option = optionsList.next();
	Iterator<JSONObject> iterator = option.iterator();
	while (iterator.hasNext()) {
		JSONObject data = iterator.next();
		dataMap.put(data.get("name"), data.get("value"));
	}

	JSONArray logins = (JSONArray) dataMap.get("logins");
	Iterator<JSONArray> loginsList = logins.iterator();
	try {
		while (loginsList.hasNext()) {
			JSONArray loginDataList = loginsList.next();
			Iterator<JSONObject> loginData = loginDataList.iterator();
			while (loginData.hasNext()) {
				JSONObject data = loginData.next();
				dataMap.put(data.get("name"), data.get("value"));
			}
	*/
	
} //EOF


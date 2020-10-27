package stepDef;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import automationLibrary.Driver;
import automationLibrary.Element;
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
			if (sessionSearch == null) {
				sessionSearch = new SessionSearch((String)dataMap.get("URL"),driver);
			} else {
				webDriver.get((String)dataMap.get("URL"));
			}
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

				createSearch(searchType, metaDataOption, searchString, metaDataString2);
			}

		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
			sessionSearch.getNewSearch().Click();
		}

	}

	public void createSearch(HashMap dataMap) {
		ArrayList<HashMap> longSearchList = (ArrayList<HashMap>) dataMap.get("longSearchList");
		for (HashMap searchEntryMap: longSearchList) {
			String entrySearchType = (String) searchEntryMap.get("entrySearchType");
			String option = (searchEntryMap.get("metaDataOption") != null) ? (String) searchEntryMap.get("metaDataOption") : (String) searchEntryMap.get("condition");
			String metaDataValue = (String) searchEntryMap.get("metaDataValue");
			String metaDataValue2 = (String) searchEntryMap.get("metaDataValue2");
			
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
			sessionSearch.getAdvancedContentSearchInput().SendKeys(searchString) ;
		} else if (searchType.equalsIgnoreCase("metaData")) {
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( null,searchString,null);
		} else if (searchType.equalsIgnoreCase("is")) {
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( "IS",searchString,null);
		} else if (searchType.equalsIgnoreCase("range")) {
			sessionSearch.selectMetaDataOption(option);
			sessionSearch.setMetaDataValue( "RANGE",searchString,searchString2);
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
			sessionSearch.getSaveSearch_SaveButton().Click();
			sessionSearch.VerifySuccessMessage("Saved search overwritten successfully");
			
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

		if (scriptState) {
			//
			String metadataOption = (String) dataMap.get("metaDataOption");
			String metadataValue = (String) dataMap.get("metaDataValue");

			String searchQuery = sessionSearch.getSearchQueryText(1).getText();

			if (searchQuery.equals(String.format("%s: ( %s)", metadataOption, metadataValue))) {
				pass(dataMap,String.format("Search criterial matches for %s with value %s", metadataOption, metadataValue));
			} else {
				fail(dataMap,String.format("Search criterial DOES NOT matche for %s with value %s", metadataOption, metadataValue));
			}


		} else {
			throw new ImplementationException("NOT verify_search_criteria");
		}

	}

	@Then("^.*(\\[Not\\] )? remove_search_criteria$")
	public void remove_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchQueryText(1).Exists()  ;}}), Input.wait30); 
			sessionSearch.getSearchQueryText(1).Visible();
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						sessionSearch.getActiveElementByXPath(sessionSearch.SearchQueryTextXpath).Visible()  ;}}), Input.wait30);
				sessionSearch.removeSearchQueryRemove().Click();
				
				//sessionSearch.getSearchQueryText(1).Visible();
			} catch (Exception e) {
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
				sessionSearch.getQuerySearchButton().Click();
			}
		} else {
			throw new ImplementationException("NOT click_search");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_search_returned$")
	public void verify_search_returned(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String testCaseNo = (dataMap.get("TestCase")!=null) ? (String) dataMap.get("TestCase") : "TBD";
		String searchType = (String) dataMap.get("searchType");
		searchType = (searchType == null) ? "Search " : String.format("%s Search ",searchType);
		String searchString = (String) dataMap.get("searchString");
		if (scriptState) {
	    	// while loading look for warnings, in case of proximity search
			try {

	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        			sessionSearch.isSearchInProgress().Visible() ;}}), 10);
	        	
	        	while (true) {
	        		// continue to check for in progress
		        	while (sessionSearch.isSearchInProgress().Visible()) {  
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
	    	
	    	//verify counts for all the tiles
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getActiveElementByXPath(sessionSearch.PureHitsCountLocator).Visible()  ;}}), Input.wait90);
			Element pureHitsCountElement = sessionSearch.getActiveElementByXPath(sessionSearch.PureHitsCountLocator);
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			pureHitsCountElement.getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
	    	
	    	int pureHit = 0;
	    	try {
		    	pureHit = Integer.parseInt(pureHitsCountElement.getText());
	    	} catch (Exception e) {
	    		System.out.println("failed to parse: "+pureHitsCountElement.getText());
	    		
	    	}
	    	int expectedPureHit = Integer.parseInt((String) dataMap.get("expectedPureHit"));
	    	
	    	if (expectedPureHit == pureHit) {
	    		String msg = String.format("%s is done for '%s' and PureHit is %s as expected.", searchType, searchString, pureHit);
				pass(dataMap,msg);
				logTestResult(dataMap,testCaseNo,"pass",msg);
	    		System.out.println(msg);
	    	} else {
	    		String msg = String.format("ERROR: Search is done for '%s' and PureHit is %s but expected %s.", searchString, pureHit, expectedPureHit);
				logTestResult(dataMap,testCaseNo,"fail",msg);
				//fail(dataMap,msg);
	    		System.out.println(msg);
	    	}

		} else {
			throw new ImplementationException("NOT verify_search_returned");
		}

	}

	@And("^.*(\\[Not\\] )? select_advanced_search$")
	public void select_advanced_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

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

		boolean autoSuggestVisible = false;
		try {
			autoSuggestVisible = sessionSearch.getAutoSuggest().Visible();
			autoSuggestVisible = true;
		} catch (Exception e) {
			// either could not find or autosuggest element was not visible
		}
		if (scriptState && !autoSuggestVisible) {
			err = "No auto suggest caused as expected.";
			logTestResult(dataMap,subTestCaseNo,"fail",err);
			fail(dataMap,err);
		} else if (!scriptState && !autoSuggestVisible) {
			logTestResult(dataMap,"TBD","pass", "Auto suggest not found as expected.");
		} else {  
			// at this point autuSuggest is visible
			Element autoSuggest = sessionSearch.getAutoSuggest();
			String suggestText = autoSuggest.getWebElement().getText();

			if (suggestText.length()>0) {
				logTestResult(dataMap,subTestCaseNo,"pass",String.format("Creating search entry with matching MetaData text (using '%s') causes autosuggest to be provided",searchText));
			} else if (!scriptState) {
				logTestResult(dataMap,"TBD","pass","Auto suggest had no text as expected.");
			}

			autoSuggested = suggestText.toUpperCase().startsWith(searchText.toUpperCase());
			if (!autoSuggested) {
				err = String.format("Auto suggest with first %s chars does not match search text.",searchText.length());
				logTestResult(dataMap,"5768","fail",err);
				fail(dataMap,err);
			} else {
				if (scriptState) logTestResult(dataMap,"5768","pass",String.format("Auto suggest with first %s chars matched",searchText.length()));

				String keys = (String) dataMap.get("additionalKeys");
				searchText = searchText + keys;
				sessionSearch.getMetaDataSearchText1().SendKeys(keys);
				try{
					Thread.sleep(3000);
				}catch (Exception e) {

				}

				if (!scriptState && !sessionSearch.getAutoSuggest().Visible()) {
					logTestResult(dataMap,testCaseNo,"pass",String.format("Auto suggest not found with search text '%s' as expected.",searchText));
				} else {
					autoSuggest = sessionSearch.getAutoSuggest();
					suggestText = autoSuggest.getText();
					autoSuggested = suggestText.toUpperCase().startsWith(searchText.toUpperCase());
					if (!autoSuggested) {
						err = String.format("Auto suggest with %s chars does not match search text.",searchText.length());
						logTestResult(dataMap,"5768","fail",err);
						fail(dataMap,err);
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
						autoSuggest = sessionSearch.getAutoSuggest();
						suggestText = autoSuggest.getText();
						autoSuggested = suggestText.toUpperCase().startsWith(searchText.toUpperCase());
						if (!autoSuggested) {
							err = String.format("Auto suggest with %s chars does not match search text.",searchText.length());
							logTestResult(dataMap,"5768","fail",err);
							fail(dataMap,err);
						} else {
							logTestResult(dataMap,"5768","pass",String.format("Auto suggest with %s chars matched",searchText.length()));

							if (scriptState) {
								pass(dataMap,String.format("Auto suggest is provided when entering search matched (using '%s') text and pausing.",searchText));
							} else {
								err = String.format("Auto suggest should not have succeeded with '%s' text entered.",searchText);
								logTestResult(dataMap,testCaseNo,"fail",err);
								fail(dataMap,err);
								
							}
						}
					}
				}
			}
		}

	}


	@And("^.*(\\[Not\\] )? select_search$")
	public void select_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

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
				fail(dataMap,"Search Name not provided");
			} else {
				Element savedSearch = sessionSearch.getSavedSearchName(searchName);
				try {
					String msg = String.format("Search saved as '%s' but not expected to saved as Search",searchName);
					savedSearch.Exists();
					logTestResult(dataMap,testCaseNo,"fail",msg);
					fail(dataMap,msg);
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

		if (scriptState) {
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			sessionSearch.getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
	    	sessionSearch.getAdvancedSearchLink().Click();

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
} //EOF


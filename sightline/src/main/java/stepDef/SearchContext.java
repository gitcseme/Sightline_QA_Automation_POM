package stepDef;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
			sessionSearch = new SessionSearch((String)dataMap.get("URL"),driver);
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
			if(!dataMap.containsKey("queryText")) {
				ArrayList<String> queryText = new ArrayList<String>();
				dataMap.put("queryText", queryText);
			}
			if (searchType==null) searchType = "metaData";
			
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
			if(sessionSearch.getNewSearch().Enabled())
				sessionSearch.getNewSearch().Click();
				driver.waitForPageToBeReady();

			String metaDataOption = (String)dataMap.get("metaDataOption");
			String metaDataValue = (String)dataMap.get("metaDataValue");
			if(metaDataOption == null) metaDataOption = "CustodianName";
			if(metaDataValue == null) metaDataValue = "Testing_Purposes";

			if (searchType.equalsIgnoreCase("metaData")) {
				((ArrayList<String>)dataMap.get("queryText")).add(metaDataOption + ": ( " + metaDataValue + ')');
				sessionSearch.selectMetaDataOption(metaDataOption);
				Thread.sleep(2000);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getMetaDataSearchText1().Enabled() && sessionSearch.getMetaDataSearchText1().Displayed()  ;}}), Input.wait30); 
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getMetaDataInserQuery().Enabled() && sessionSearch.getMetaDataInserQuery().Displayed()  ;}}), Input.wait30); 
				sessionSearch.setMetaDataValue( null,metaDataValue,null);
				Thread.sleep(2000);
				driver.waitForPageToBeReady();
			} 
			else if (searchType.equalsIgnoreCase("is")) {
				sessionSearch.selectMetaDataOption(metaDataOption);
				sessionSearch.setMetaDataValue( "IS",metaDataValue,null);
			} 
			else if (searchType.equalsIgnoreCase("range")) {
				sessionSearch.selectMetaDataOption(metaDataOption);
				String metaDataVal2 = (String)dataMap.get("metaDataVal2");
				sessionSearch.setMetaDataValue( "RANGE",metaDataValue,metaDataVal2);
			} 
			else if (searchType.equalsIgnoreCase("long")) {
				throw new ImplementationException("create search - long");
			} 
			else if (searchType.equalsIgnoreCase("fulltext")) {
				throw new ImplementationException("create search - fulltext");
			} 

			
						

			
		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
			sessionSearch.getNewSearch().Click();
		}

	}


	@And("^.*(\\[Not\\] )? save_search$")
	public void save_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		
		Random rand = new Random();
		if (scriptState) {
			String metaDataOption = (String) dataMap.get("metaDataOption");
			String tempString = Integer.toString(rand.nextInt(20000) +1);
			dataMap.put("CurrentSaveValue","Test Search" + tempString);
			//Get #of Search Buttons on Page
			int searchSize = sessionSearch.getSaveSearchButtons().FindWebElements().size();
			try {

				//Get Current Search Button (Last Index of List)
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSaveSearchButtons().FindWebElements().get(searchSize-1).isEnabled()  ;}}), Input.wait30); 
				sessionSearch.getSaveSearchButtons().FindWebElements().get(searchSize-1).click();

				//Choose Correct Search Tree
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSavedSearch_MySearchesTab().Enabled()  ;}}), Input.wait30); 
				sessionSearch.getSavedSearch_MySearchesTab().Click();

				//Put in Random Test Name
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSaveSearch_Name().Enabled()  ;}}), Input.wait30); 
				sessionSearch.getSaveSearch_Name().SendKeys("Test Search"+ tempString);

				//Submit Save Search
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSaveSearch_SaveButton().Enabled()  ;}}), Input.wait30); 
				sessionSearch.getSaveSearch_SaveButton().Click();
				driver.waitForPageToBeReady();
				pass(dataMap, "Saved a search successfully");
			}
			catch(Exception e) { fail(dataMap, "Failed To Click Save Search Button");}
		}
		else {fail(dataMap, "Failed To Click Save Search Button");}

	}

	@When("^.*(\\[Not\\] )? verify_searched_save$")
	public void verify_searched_save(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if(scriptState){
			//
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchTabName().FindWebElements().get(0).isDisplayed()  ;}}), Input.wait30); 
				String nameToCompare = sessionSearch.getSearchTabName().FindWebElements().get(0).getText();
				Assert.assertEquals(((String)dataMap.get("CurrentSaveValue")).toLowerCase(), (nameToCompare.split(":")[1]).toLowerCase());
			}
			catch(Exception e) {
				fail(dataMap, "Could not find the required search term");
			}
		}
		else {fail(dataMap,"Could not find the required search term");}

	}

	@Then("^.*(\\[Not\\] )? verify_current_login_session_previous_search_query_selection$")
	public void verify_current_login_session_previous_search_query_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSavedQueryButtons().FindWebElements().get(0).isDisplayed()  ;}}), Input.wait30); 

				//Get Size of Saved Searches That we need to click through.
				int buttonSize2 = sessionSearch.getSavedQueryButtons().FindWebElements().size();

				//Click through Rest of Saved Searches Starting From the Bottom
				for(int i=buttonSize2-1; i>=0; i--) {
					sessionSearch.getSavedQueryButtons().FindWebElements().get(i).click();
					Thread.sleep(3000);
					//For Each SavedSearch -> Find its corresponding Query Text. 
					for(int j =0; j<buttonSize2; j++) {
						if(!sessionSearch.getQueryText2(j).getText().equals("")) {
							//Verify Query Text is Equal to what user Inputed Previously before Save
							Assert.assertEquals(sessionSearch.getQueryText2(j).getText(),((ArrayList<String>)dataMap.get("queryText")).get(buttonSize2-i-1));
						}
					}
				}
				pass(dataMap, "Saved Query Text Was Verified");
			
			}
			catch(Exception e) { fail(dataMap, "Saved Query Text Was Not Verified");}
		}
		else fail(dataMap, "Saved Query Text Was Not Verified");

	}

	@Then("^.*(\\[Not\\] )? verify_current_login_session_saved_search_SEARCH5$")
	public void verify_current_login_session_saved_search_SEARCH5(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//[Test Case 80 - verify_current_login_session_saved_search_SEARCH5]
			throw new ImplementationException("verify_current_login_session_saved_search_SEARCH5");
		} else {
			throw new ImplementationException("NOT verify_current_login_session_saved_search_SEARCH5");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_current_login_session_edit_previous_search_query$")
	public void verify_current_login_session_edit_previous_search_query(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//[Test Case 85 - verify the current login session save search edit on selected previous search query\
			throw new ImplementationException("verify_current_login_session_edit_previous_search_query");
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
						sessionSearch.removeSearchQueryRemove(1).Visible()  ;}}), Input.wait30);
				sessionSearch.removeSearchQueryRemove(1).Click();
				sessionSearch.getSearchQueryText(1).Visible();
				fail(dataMap,"Unable to remove search criteria");
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
			//
			throw new ImplementationException("click_search");
		} else {
			throw new ImplementationException("NOT click_search");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_search_returned$")
	public void verify_search_returned(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_search_returned");
		} else {
			throw new ImplementationException("NOT verify_search_returned");
		}

	}
}

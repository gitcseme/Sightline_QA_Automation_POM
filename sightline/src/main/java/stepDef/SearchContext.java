package stepDef;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	public void startUP(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if(scriptState) {
			sightline_is_launched(scriptState, dataMap);
			login_as_pau(scriptState, dataMap);
			goto_search_session_page(scriptState, dataMap);
			on_production_Search_Session_page(scriptState, dataMap);
		}
	}
    
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
			if(sessionSearch.getNewSearch().Enabled()) {
				sessionSearch.getNewSearch().Click();
				driver.waitForPageToBeReady();
			}

			String metaDataOption = (String)dataMap.get("metaDataOption");
			String metaDataValue = (String)dataMap.get("metaDataValue");
			if(metaDataOption == null){
				metaDataOption = "CustodianName";
				dataMap.put("metaDataOption", metaDataOption);
			}
			if(metaDataValue == null) {
				metaDataValue = "Testing_Purposes";
				dataMap.put("metaDataValue", metaDataValue);
			}

			if (searchType.equalsIgnoreCase("metaData")) {
				((ArrayList<String>)dataMap.get("queryText")).add(metaDataOption + ": ( " + metaDataValue + ')');
				sessionSearch.selectMetaDataOption(metaDataOption);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getMetaDataSearchText1().Enabled() && sessionSearch.getMetaDataSearchText1().Displayed()  ;}}), Input.wait30); 
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getMetaDataInserQuery().Enabled() && sessionSearch.getMetaDataInserQuery().Displayed()  ;}}), Input.wait30); 
				sessionSearch.setMetaDataValue( null,metaDataValue,null);
				driver.waitForPageToBeReady();
			} 
			else if (searchType.equalsIgnoreCase("is")) {
				sessionSearch.selectMetaDataOption(metaDataOption);
				sessionSearch.setMetaDataValue( "IS",metaDataValue,null);
			} 
			else if (searchType.equalsIgnoreCase("range")) {
				sessionSearch.selectMetaDataOption(metaDataOption);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getMetaDataSearchText1().Enabled() && sessionSearch.getMetaDataSearchText1().Displayed()  ;}}), Input.wait30); 
				String metaDataVal2 = (String)dataMap.get("metaDataVal2");
				sessionSearch.setMetaDataValue( "RANGE",metaDataValue,metaDataVal2);
			} 
			else if (searchType.equalsIgnoreCase("long")) {
				sessionSearch.insertLongText((String)dataMap.get("metaDataOption"), (String)dataMap.get("metaDataOption2"),
					(String)dataMap.get("condition"), (String)dataMap.get("metaDataValue"), (String)dataMap.get("metaDataValue2"));
			} 
			else if (searchType.equalsIgnoreCase("fulltext")) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.setQueryText().FindWebElements().get(0).isDisplayed()  ;}}), Input.wait30); 
				sessionSearch.insertFullText((String)dataMap.get("FullText"));
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
			catch(Exception e){ 
				e.printStackTrace();
				fail(dataMap, "Failed To Click Save Search Button")
			;}
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
					int curr = i;
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						sessionSearch.getSavedQueryButtons().FindWebElements().get(curr).isDisplayed()  ;}}), Input.wait30); 
					sessionSearch.getSavedQueryButtons().FindWebElements().get(i).click();
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
			try {
				int numOfSearches = sessionSearch.getSavedQueryButtons().FindWebElements().size();

				//After 5th Search Query is Saved -> Click on Search Button 
				//6th Query Will be created Automatically?
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchButtons().FindWebElements().get(numOfSearches-1).isDisplayed()  ;}}), Input.wait30); 
				sessionSearch.getSearchButtons().FindWebElements().get(numOfSearches-1).click();
				
				//Need to think of a better way to deal with this wait after search
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchTableResults().Displayed()  ;}}), Input.wait30); 

				//Click on 5th Saved Search
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSavedQueryButtons().FindWebElements().get(1).isEnabled() &&  sessionSearch.getSavedQueryButtons().FindWebElements().get(1).isDisplayed() ;}}), Input.wait30); 
				sessionSearch.getSavedQueryButtons().FindWebElements().get(1).click();
				
				
				for(int i =0; i<numOfSearches; i++) {
					if(!sessionSearch.getQueryText2(i).getText().equals("")) {
						Assert.assertEquals((sessionSearch.getQueryText2(i).getText()), ((ArrayList<String>)dataMap.get("queryText")).get(4));
					}
				}
				pass(dataMap, "Passed Search 5 Verification");

			}
			catch(Exception e) {fail(dataMap, "Failed Search 5 Verification");
			}

		}
		else fail(dataMap, "Failed Search 5 Verification");

	}


	@Then("^.*(\\[Not\\] )? verify_current_login_session_edit_previous_search_query$")
	public void verify_current_login_session_edit_previous_search_query(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//[Test Case 85 - verify the current login session save search edit on selected previous search query
			driver.waitForPageToBeReady();
			ArrayList<String> query = new ArrayList<>();
			StringBuilder temp = new StringBuilder();

			try {
				int searchSessionSize = sessionSearch.getSavedQueryButtons().FindWebElements().size();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						sessionSearch.getSavedQueryButtons().FindWebElements().get(searchSessionSize-1).isDisplayed()  ;}}), Input.wait30); 

				//Loop to Click through our Previous Queries
				for(int i = searchSessionSize-1; i>=0; i--) {
					int curr = i;
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						sessionSearch.getSavedQueryButtons().FindWebElements().get(curr).isEnabled()  ;}}), Input.wait30); 
					sessionSearch.getSavedQueryButtons().FindWebElements().get(i).click(); // Click on each search query,starting with last search
					//Loop To Modify Query Text, for each of our Previous Queries
					for(WebElement j: sessionSearch.setQueryText().FindWebElements()) {
						if(j.isDisplayed() &&j.isEnabled()) {
							j.click();
							j.sendKeys("Test");
							j.sendKeys(Keys.ENTER);
							//Loop To find and save the final new modified Query  
							for(WebElement k: sessionSearch.getQueryTextBoxes().FindWebElements()) {
								if(!k.getText().equals("") && k.isDisplayed()) {
									temp.append(k.getText());
									temp.append(" ");
								}
							}
							query.add(temp.toString());
							temp = new StringBuilder();
						}
					}
					
				}
				//Final loop To iterate back through our Queries, and make sure the modified Query Text has Persisted
				for(int i = searchSessionSize-1; i>=0; i--){
					sessionSearch.getSavedQueryButtons().FindWebElements().get(i).click();
					for(WebElement k: sessionSearch.getQueryTextBoxes().FindWebElements()) {
						if(!k.getText().equals("") && k.isDisplayed()) {
							temp.append(k.getText());
							temp.append(" ");
						}
					}
					Assert.assertEquals(temp.toString(), query.get(searchSessionSize-i-1));
					temp = new StringBuilder();
				}
				pass(dataMap,"Was Able to Edit Previous Searches");
			}
			catch(Exception e) { e.printStackTrace();
				fail(dataMap, "Could not Edit Previous Search Queries");}

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
			StringBuilder temp = new StringBuilder();
			try {

				//First Search and Wait For Search Results to Load
				sessionSearch.getSearchButton().Click();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchTableResults().Displayed()  ;}}), Input.wait30); 

				
				//Insert Operator
				 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						 sessionSearch.getOperatorDropdown().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 
				 for(WebElement x: sessionSearch.getOperatorDropdown().FindWebElements()) {
					 if(x.isDisplayed() && x.isEnabled()) {
						 x.click();
						 for(WebElement y: sessionSearch.getOperatorDropDownOP("AND").FindWebElements()) {
							 if(y.isDisplayed() && y.isEnabled())
								 y.click();
						 }
					 }
				 }				

					
				//Insert new AND MetaData 
				String metaDataOption = (String)dataMap.get("metaDataOption");
				String metaDataValue = (String)dataMap.get("metaDataValue");
				if(metaDataOption == null) metaDataOption = "CustodianName";
				if(metaDataValue == null) metaDataValue = "Other_Testing_Purposes";
				sessionSearch.selectMetaDataOption(metaDataOption);
				sessionSearch.setMetaDataValue( null,metaDataValue,null);
				
				//Find The Correct Search Button in DOM Tree
				for(WebElement x: sessionSearch.getSearchButtons().FindWebElements()) {
					if(x.isDisplayed() && x.isEnabled()) {
						x.click();
					}
				}

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchTableResults().Displayed()  ;}}), Input.wait30); 

				//Get our Modified Text through each Query Text Box
				for(WebElement x: sessionSearch.getQueryTextBoxes().FindWebElements()) {
					if(x.isDisplayed() && !x.getText().equals("")) {
						temp.append(x.getText());
						temp.append(" ");
					}
				}
								
				//Click The Original Query -> First Query we Entered 
				int searchSessionSize = sessionSearch.getSavedQueryButtons().FindWebElements().size();  //Total number of search queries (It is 3)
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						sessionSearch.getSavedQueryButtons().FindWebElements().get(searchSessionSize-1).isEnabled()  ;}}), Input.wait30); 
				sessionSearch.getSavedQueryButtons().FindWebElements().get(searchSessionSize-1).click();	//Should click bottom most
				
				//Get Original Query Text from Query Text Boxes
				String originalQuery = "";
				for(WebElement x: sessionSearch.getQueryTextBoxes().FindWebElements()) {
					if(x.isDisplayed() && !x.getText().equals("")) {
						originalQuery = x.getText();
					}
				}
				//Verify That Original Query Text has not Changed, by Adding the difference of the Final Query and Comparing their equality
				Assert.assertEquals(temp.toString(), originalQuery + " AND " + metaDataOption + ": ( " + metaDataValue + ") ");
				pass(dataMap, "Original Query was not Modified");
					
			}
			catch(Exception e) {fail(dataMap, "Could not Verify Orginal Query Not Modified");}
			

		}
		else fail(dataMap, "Could not Verify Orginal Query Not Modified");

	}

	@Then("^.*(\\[Not\\] )? verify_search_criteria$")
	public void verify_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String metadataOption = (String) dataMap.get("metaDataOption");
			String metadataValue = (String) dataMap.get("metaDataValue");
			String searchQuery = "";

			//Simply go through Query's and get the current displayed text
			for(WebElement x: sessionSearch.getQueryTextBoxes().FindWebElements()) {
				if(x.isDisplayed() && x.getText()!= "") searchQuery = x.getText();
			}

			//Verify Text here
			if (searchQuery.equals(String.format("%s: ( %s)", metadataOption, metadataValue))) {
				pass(dataMap,String.format("Search criterial matches for %s with value %s", metadataOption, metadataValue));
			} else {
				fail(dataMap,String.format("Search criterial DOES NOT matche for %s with value %s", metadataOption, metadataValue));
			}

			
		} else {
			fail(dataMap,"NOT verify_search_criteria");
		}

	}
	
	@Then("^.*(\\[Not\\] )? remove_search_criteria$")
	public void remove_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				//Wait for Query Textbox to appear, then call function to Hover over that Element
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getQueryTextBoxes().FindWebElements().get(0).isDisplayed()  ;}}), Input.wait30); 
				sessionSearch.removeSearchQueryRemove().Click();
				pass(dataMap, "Search Criteria Removed Successfully");

			} catch (Exception e) {fail(dataMap, "Could not remove Search Criteria");}
			
		} else { fail(dataMap, "Could not remove Search Criteria");}	

	}

	@Then("^.*(\\[Not\\] )? verify_is_search_criteria$")
	public void verify_is_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				String metaDataOption = (String)dataMap.get("metaDataOption");
				String metaDataValue= (String)dataMap.get("metaDataValue");
				String searchQuery = "";
				//Find our Search Query 
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getQueryTextBoxes().FindWebElements().get(0).isDisplayed()  ;}}), Input.wait30); 
				for(WebElement x: sessionSearch.getQueryTextBoxes().FindWebElements()) {
					if(x.isDisplayed() && !x.getText().equals("")) {
						searchQuery = x.getText();
					}
				}
				//Verify it
				Assert.assertEquals(String.format("%s: [%s TO %s]", metaDataOption, metaDataValue, metaDataValue), searchQuery);
				
			}
			catch(Exception e) {fail(dataMap, "Could not verify IS search criteria");}
		}
		else fail(dataMap, "Could not verify IS search criteria");

	}

	@Then("^.*(\\[Not\\] )? verify_fulltext_search_criteria$")
	public void verify_fulltext_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				String searchQuery = "";
				//Find our Search Query
				for(WebElement x: sessionSearch.getQueryTextBoxes().FindWebElements()) {
					if(x.isDisplayed() && !x.getText().equals("")) {
						searchQuery = x.getText();
					}
				}
				//Verify it
				Assert.assertEquals(searchQuery,(String)dataMap.get("FullText"));
				
			}
			catch(Exception e) {fail(dataMap, "Could not Verify FullText Search Criteria");}
		}	
		else fail(dataMap,"Could not Verify FullText Search Criteria");

	}

	@Then("^.*(\\[Not\\] )? verify_range_search_criteria$")
	public void verify_range_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			try {
				String searchQuery = "";
				String metaDataOption = (String)dataMap.get("metaDataOption"); 
				String metaDataValue = (String)dataMap.get("metaDataValue");
				String metaDataValue2 = (String)dataMap.get("metaDataVal2"); 
				//Find our Query
				for(WebElement x: sessionSearch.getQueryTextBoxes().FindWebElements()) {
					if(x.isDisplayed() && !x.getText().equals("")) {
						searchQuery = x.getText();
					}
				}
				//Verify it
				Assert.assertEquals(searchQuery, String.format("%s: [%s TO %s]",metaDataOption,metaDataValue,metaDataValue2));
				
			}
			catch(Exception e) {fail(dataMap, "Could not Verify Range Search Criteria");}
			
		}
		else fail(dataMap, "Could not Verify Range Search Criteria");

	}

	@Then("^.*(\\[Not\\] )? verify_long_search_criteria$")
	public void verify_long_search_criteria(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try{
				StringBuilder searchQuery = new StringBuilder();
				String metaDataOption = (String)dataMap.get("metaDataOption"); 
				String metaDataValue = (String)dataMap.get("metaDataValue");
				String metaDataValue2 = (String)dataMap.get("metaDataValue2"); 
				String metaDataOption2 = (String)dataMap.get("metaDataOption2"); 
				String condition = (String)dataMap.get("condition");
				//Grab Each String from All Query Boxes, and Combine them into one final SearchQuery to compare
				for(WebElement x: sessionSearch.getQueryTextBoxes().FindWebElements()) {
					if(x.isDisplayed() && !x.getText().equals("")) {
						searchQuery.append(x.getText());
						searchQuery.append(" ");
					}
				}
				if(metaDataOption2 == null) {
					Assert.assertEquals(searchQuery.toString(), String.format("%s %s %s ", metaDataValue,condition,metaDataValue2));
				}
				else {
					Assert.assertEquals((searchQuery.toString()), String.format("%s: ( %s) %s %s: ( %s) ", 
						metaDataOption,metaDataValue,condition,metaDataOption2,metaDataValue2));
				}
				pass(dataMap, "Verified the Long Text Search Criteria");

				
			}
			catch(Exception e) {fail(dataMap, "Could Not Verify Long Text Search Criteria");}

		}	
		else fail(dataMap, "Could Not Verify Long Text Search Criteria");

	}


	@When("^.*(\\[Not\\] )? click_search$")
	public void click_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		String searchType = (String)dataMap.get("searchType");
		if (scriptState) {
			try {
				//Find the valid search Button through the DOM list
				for(WebElement x: sessionSearch.getSearchButtons().FindWebElements()) {
					//If its displayed and enabled that is a valid search button to use
					if(x.isDisplayed() && x.isEnabled()) {
						x.click(); 
						driver.waitForPageToBeReady();
						//A warning message arises when using the long search type, this check clicks continue on that popup
						if(searchType!=null && searchType.equalsIgnoreCase("long")){
							if(sessionSearch.getQueryPossibleWrongAlertContinueButton().Displayed() && sessionSearch.getQueryPossibleWrongAlertContinueButton().Enabled()) {
								sessionSearch.getQueryPossibleWrongAlertContinueButton().Click();
							}
						}
					}
				}
			pass(dataMap, "Successfully Clicked the search button");
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "Could not click the search button");
				}
		}
		else fail(dataMap, "Could not click the search button");

	}


	@Then("^.*(\\[Not\\] )? verify_search_returned$")
	public void verify_search_returned(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				//Verify that The Search Results table has spawned. Is this a good enough verification?
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchTableResults().Displayed()  ;}}), Input.wait30); 
				Assert.assertTrue(sessionSearch.getResultsTab().Displayed());
				pass(dataMap, "Able to verify our search went through");
			}
			catch(Exception e) {fail(dataMap, "Could not verify Search Returned");}

		}
	    else fail(dataMap, "Could not verify Search Returned");

	}

	@And("^.*(\\[Not\\] )? select_advanced_search$")
	public void select_advanced_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try
			{

				String metaDataOption = (String)dataMap.get("MetaDataOption");
				String metaDataValue = (String)dataMap.get("MetaDataValue");
				String metaDataValue2 = (String)dataMap.get("MetaDataValue2");

				//* Verify button [+ New Search]
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getNewSearch().Enabled()  ;}}), Input.wait30); 
				Assert.assertTrue(sessionSearch.getNewSearch().Displayed() && sessionSearch.getNewSearch().Enabled());
				
				//* Verify Advanced Search Link
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getAdvancedSearchLink().Enabled()  ;}}), Input.wait30); 
				Assert.assertTrue(sessionSearch.getAdvancedSearchLink().Displayed() && sessionSearch.getAdvancedSearchLink().Enabled());

				//* Click Advanced Search link
				sessionSearch.getAdvancedSearchLink().Click();
				
				//Click Content and MetaData
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getContentAndMetaDatabtn().Displayed()  ;}}), Input.wait30); 
				sessionSearch.getContentAndMetaDatabtn().Click();

				
				//* Verify [MetaData] button is enabled
				//* Click [MetaData] button
				//* Verify "Insert MetaData" popup
				//* Verify "Select a Field" dropdown is enabled
				//* Click "Select a Field" dropdown
				//* Select "MetaDataOption"
				sessionSearch.selectMetaDataOption(metaDataOption);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getMetaDataSearchText1().Enabled() && sessionSearch.getMetaDataSearchText1().Displayed()  ;}}), Input.wait30); 

				//* Verify "Enter Value" Text box is enabled
				//* Enter {ing} in Text Box
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getMetaDataInserQuery().Enabled() && sessionSearch.getMetaDataInserQuery().Displayed()  ;}}), Input.wait30); 
				sessionSearch.setMetaDataValueNoEnter( null,metaDataValue,null);

				//* Verify Autosuggest appears
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getSearchAutoCompletePopup().Displayed()  ;}}), Input.wait30); 
				Assert.assertTrue(sessionSearch.getSearchAutoCompletePopup().Displayed());

			}
			catch(Exception e) {e.printStackTrace();}

			

		}
		else fail(dataMap, "Could not use Advanced Search Options"); 
		

	}


	@Then("^.*(\\[Not\\] )? verify_autosuggest$")
	public void verify_autosuggest(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			//
			//* check for autosuggest within 3 seconds timer|TC#5768
			//* Enter {additionalKeys}
			//* check for autosuggest
			//
		}
		else {
			try {
				//Enter Last Part of Invalid String, to remove Auto Suggest Box
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getMetaDataInserQuery().Enabled() && sessionSearch.getMetaDataInserQuery().Displayed()  ;}}), Input.wait30); 
				sessionSearch.setMetaDataValueNoEnter(null, (String)dataMap.get("MetaDataValue2"), null);

				//Verify Auto Complete Pop Up is gone
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					!sessionSearch.getSearchAutoCompletePopup().Displayed()  ;}}), Input.wait30); 
				Assert.assertTrue(!sessionSearch.getSearchAutoCompletePopup().Displayed());
				pass(dataMap, "AutoSuggest Was Removed with invalid input");
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "AutoSuggest Was Not Removed with invalid input");
			}
			
		}

	}


	@And("^.*(\\[Not\\] )? select_search$")
	public void select_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Verify button [+ New Search]
			//* Click [+ New Search]
			//* Verify [MetaData] button is enabled
			//* Click [MetaData] button
			//* Verify "Insert MetaData" popup
			//* Verify "Select a Field" dropdown is enabled
			//* Click "Select a Field" dropdown
			//* Select "{DocFileExtension}"
			

			
			throw new ImplementationException("select_search");
		} else {
			throw new ImplementationException("NOT select_search");
		}

	}

	@And("^.*(\\[Not\\] )? create_advanced_search$")
	public void create_advanced_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Verify button [+ New Search]
			//* Verify Advanced Search Link
			//* Click Advanced Search link
			//* Verify [MetaData] button is enabled
			//* Click [MetaData] button
			//* Verify "Insert MetaData" popup
			//* Verify "Select a Field" dropdown is enabled
			//* Click "Select a Field" dropdown
			//* Select "MetaDataOption"
			//* Verify "Enter Value" Text box is enabled
			//* Enter {MetaDataValue} in Text Box
			//
			//Click "Select a Field" dropdownSelect "All Custodian"Verify "Enter Value" Text box is enabledEnter "Test" in Text BoxVerify [Insert into Query] button is enabledClick [Insert into Query]Verify "AllCustodians: (test)" query entry is addedClick [Save]Verify "Save Search" popupClick "Save as new search" optionClick "My Saved Search"Enter Name of search as Search[timestamp]Click "Save"
			//Confirm searched saved on Search List with provided name
			throw new ImplementationException("create_advanced_search");
		} else {
			throw new ImplementationException("NOT create_advanced_search");
		}

	}
}//end

	

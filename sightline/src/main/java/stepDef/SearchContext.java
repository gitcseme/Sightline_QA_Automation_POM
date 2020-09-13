package stepDef;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import automationLibrary.Driver;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import testScriptsSmoke.Input;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import junit.framework.Assert;

@SuppressWarnings({"deprecation", "rawtypes" })
public class SearchContext extends CommonContext {
	Driver driver;
	WebDriver webDriver;
	LoginPage lp;

	SessionSearch searchSession;

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
	public void (boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	 */
    
	@When("^.*(\\[Not\\] )? goto_search_session_page$")
	public void goto_search_session_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			dataMap.put("searchSession",searchSession);
			//
			throw new ImplementationException("goto_search_session_page");
		} else {
			throw new ImplementationException("NOT goto_search_session_page");
		}

	}


	@Then("^.*(\\[Not\\] )? on_production_Search_Session_page$")
	public void on_production_Search_Session_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Goto URI /Search/SearchesVerify title "Search"Verify Help Tip [?]Verify button [+ New Search]Verify "Count of unique DocIDs index in the project: ????"
			throw new ImplementationException("on_production_Search_Session_page");
		} else {
			throw new ImplementationException("NOT on_production_Search_Session_page");
		}

	}


	@And("^.*(\\[Not\\] )? create_search$")
	public void create_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("create_search");
		} else {
			throw new ImplementationException("NOT create_search");
		}

	}


	@And("^.*(\\[Not\\] )? save_search$")
	public void save_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String metaDataOption = (String) dataMap.get("metaDataOption");
			//
			throw new ImplementationException("save_search");
		} else {
			throw new ImplementationException("NOT save_search");
		}

	}

	@When("^.*(\\[Not\\] )? verify_searched_save$")
	public void verify_searched_save(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_searched_save");
		} else {
			throw new ImplementationException("NOT verify_searched_save");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_current_login_session_previous_search_query_selection$")
	public void verify_current_login_session_previous_search_query_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_current_login_session_previous_search_query_selection");
		} else {
			throw new ImplementationException("NOT verify_current_login_session_previous_search_query_selection");
		}

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
			throw new ImplementationException("verify_search_criteria");
		} else {
			throw new ImplementationException("NOT verify_search_criteria");
		}

	}
}

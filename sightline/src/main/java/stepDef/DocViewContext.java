package stepDef;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Random;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.apache.commons.lang3.SystemUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

import pageFactory.DocViewPage;
import pageFactory.SavedSearch;
import testScriptsSmoke.Input;

@SuppressWarnings({"rawtypes", "unchecked" })
public class DocViewContext extends CommonContext {

	/* 
	 * moved to CommonContext
	 * 
	public void sightline_is_launched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void login_as_pau(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void on_production_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

	 */



	@And("^.*(\\[Not\\] )? open_saved_audio_doc_view$")
	public void open_saved_audio_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click 'Saved with SG1' search group
			//* Click radio button for first saved search
			//* Click 'Doc View' button at the top of the page
			//
			
		} else {
			throw new ImplementationException("NOT open_saved_audio_doc_view");
		}

	}


	@And("^.*(\\[Not\\] )? click_grey_redact_tool$")
	public void click_grey_redact_tool(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click grey Redact tool button
			//
			
		} else {
			throw new ImplementationException("NOT click_grey_redact_tool");
		}

	}


	@When("^.*(\\[Not\\] )? apply_audio_redaction$")
	public void apply_audio_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("apply_audio_redaction");
		} else {
			throw new ImplementationException("NOT apply_audio_redaction");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_audio_redaction$")
	public void verify_audio_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3485 Verify user after impersonating as RMU/Reviewer can see the redaction and can redact in an audio file
			
		} else {
			throw new ImplementationException("NOT verify_audio_redaction");
		}

	}


	@And("^.*(\\[Not\\] )? open_saved_search_doc_view$")
	public void open_saved_search_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click 'Saved with SG1' search group
			SavedSearch savedSearch = new SavedSearch(driver);
			savedSearch.getSavedSearchByGroupName("Saved with SG1");

			//* Click radio button for first saved search
			savedSearch.getSavedSearchTableRadioButtons().getElementByIndex(0).click();
			//* Click 'Doc View' button at the top of the page
			savedSearch.getToDocView().click();

			pass(dataMap, "Open saved search doc view");
		} else {
			fail(dataMap,"Cannot open save search doc view");
		}

	}


	@And("^.*(\\[Not\\] )? click_rectangle_redaction_button$")
	public void click_rectangle_redaction_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click Rectangle Redact button
			//
			throw new ImplementationException("click_rectangle_redaction_button");
		} else {
			throw new ImplementationException("NOT click_rectangle_redaction_button");
		}

	}


	@And("^.*(\\[Not\\] )? open_dev_tools_f12$")
	public void open_dev_tools_f12(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Open developer tools by pressing F12
			//
			throw new ImplementationException("open_dev_tools_f12");
		} else {
			throw new ImplementationException("NOT open_dev_tools_f12");
		}

	}


	@And("^.*(\\[Not\\] )? add_redaction_to_page_without_saving$")
	public void add_redaction_to_page_without_saving(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Place rectangle redaction on the document
			//* Select 'SGSame1' redaction tag on Redaction Tag Save Confirmation popup
			//
			throw new ImplementationException("add_redaction_to_page_without_saving");
		} else {
			throw new ImplementationException("NOT add_redaction_to_page_without_saving");
		}

	}


	@And("^.*(\\[Not\\] )? redaction_setup_for_user2$")
	public void redaction_setup_for_user2(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Enter Username and password for Review Manager user
			//* User is logged in
			//* Sightline Home page is displayed
			//* on_saved_search_page
			//* open_saved_search_doc_view
			//* click_grey_redact_tool
			//* click_rectangle_redaction_button
			//* open_dev_tools_f12
			//
			throw new ImplementationException("redaction_setup_for_user2");
		} else {
			throw new ImplementationException("NOT redaction_setup_for_user2");
		}

	}


	@And("^.*(\\[Not\\] )? switch_to_user1$")
	public void switch_to_user1(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Switch to user 1
			//
			throw new ImplementationException("switch_to_user1");
		} else {
			throw new ImplementationException("NOT switch_to_user1");
		}

	}


	@And("^.*(\\[Not\\] )? disconnect_from_internet$")
	public void disconnect_from_internet(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Disconnect from your internet network
			//
			throw new ImplementationException("disconnect_from_internet");
		} else {
			throw new ImplementationException("NOT disconnect_from_internet");
		}

	}


	@And("^.*(\\[Not\\] )? switch_to_user2$")
	public void switch_to_user2(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Switch to user 2
			//
			throw new ImplementationException("switch_to_user2");
		} else {
			throw new ImplementationException("NOT switch_to_user2");
		}

	}


	@And("^.*(\\[Not\\] )? save_applied_redaction$")
	public void save_applied_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select 'SGSame1' redaction tag on Redaction Tag Save Confirmation popup
			//* Click 'Save' button on Redaction Tag Save Confirmation popup
			//
			throw new ImplementationException("save_applied_redaction");
		} else {
			throw new ImplementationException("NOT save_applied_redaction");
		}

	}


	@And("^.*(\\[Not\\] )? connect_to_internet$")
	public void connect_to_internet(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Reconnect to your internet network
			//
			throw new ImplementationException("connect_to_internet");
		} else {
			throw new ImplementationException("NOT connect_to_internet");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redactiontagged_in_documentredactions_db$")
	public void verify_redactiontagged_in_documentredactions_db(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC12212 Verify orphan nodes are not created by disconnecting from the network with multiple users
			throw new ImplementationException("verify_redactiontagged_in_documentredactions_db");
		} else {
			throw new ImplementationException("NOT verify_redactiontagged_in_documentredactions_db");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_displayed$")
	public void verify_redaction_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC12212 Verify orphan nodes are not created by disconnecting from the network with multiple users
			throw new ImplementationException("verify_redaction_displayed");
		} else {
			throw new ImplementationException("NOT verify_redaction_displayed");
		}

	}


	@And("^.*(\\[Not\\] )? wait_minutes$")
	public void wait_minutes(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Sleep for 20 minutes
			//
			throw new ImplementationException("wait_minutes");
		} else {
			throw new ImplementationException("NOT wait_minutes");
		}

	}


	@When("^.*(\\[Not\\] )? add_redaction_to_page$")
	public void add_redaction_to_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Place rectangle redaction on the document
			//* Select 'SGSame1' redaction tag on Redaction Tag Save Confirmation popup
			//* Click 'Save' button on Redaction Tag Save Confirmation popup
			//
			throw new ImplementationException("add_redaction_to_page");
		} else {
			throw new ImplementationException("NOT add_redaction_to_page");
		}

	}


	@And("^.*(\\[Not\\] )? reconnect_to_internet$")
	public void reconnect_to_internet(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Disconnect from your internet network
			//* Reconnect to your internet network
			//
			throw new ImplementationException("reconnect_to_internet");
		} else {
			throw new ImplementationException("NOT reconnect_to_internet");
		}

	}


	@When("^.*(\\[Not\\] )? add_redaction_to_page_repeatedly_for_30_mins$")
	public void add_redaction_to_page_repeatedly_for_30_mins(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Do the following on loop for 30 mins
			//
			//* Place rectangle redaction on the document
			//* Select 'SGSame1' redaction tag on Redaction Tag Save Confirmation popup
			//* Click 'Save' button on Redaction Tag Save Confirmation popup
			//
			throw new ImplementationException("add_redaction_to_page_repeatedly_for_30_mins");
		} else {
			throw new ImplementationException("NOT add_redaction_to_page_repeatedly_for_30_mins");
		}

	}


	@And("^.*(\\[Not\\] )? nav_to_page2_of_doc$")
	public void nav_to_page2_of_doc(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to page 2 of document
			//
			throw new ImplementationException("nav_to_page2_of_doc");
		} else {
			throw new ImplementationException("NOT nav_to_page2_of_doc");
		}

	}


	@And("^.*(\\[Not\\] )? delete_redactions_from_both_pages$")
	public void delete_redactions_from_both_pages(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Delete the 2 redactions added in the previous steps
			//
			throw new ImplementationException("delete_redactions_from_both_pages");
		} else {
			throw new ImplementationException("NOT delete_redactions_from_both_pages");
		}

	}


	@When("^.*(\\[Not\\] )? browse_all_history_on_docview$")
	public void browse_all_history_on_docview(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click 'History' tab on Doc View
			//* Click 'Browse All History' button
			//* Sort by most recent 'Time Stamp'
			//
			throw new ImplementationException("browse_all_history_on_docview");
		} else {
			throw new ImplementationException("NOT browse_all_history_on_docview");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redactionuntagged_in_doc_history$")
	public void verify_redactionuntagged_in_doc_history(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC12188 Verify that on deleting multi redactions from multi page document should not create orphan tags
			throw new ImplementationException("verify_redactionuntagged_in_doc_history");
		} else {
			throw new ImplementationException("NOT verify_redactionuntagged_in_doc_history");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redactionuntagged_in_documentredactions_db$")
	public void verify_redactionuntagged_in_documentredactions_db(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC12188 Verify that on deleting multi redactions from multi page document should not create orphan tags
			throw new ImplementationException("verify_redactionuntagged_in_documentredactions_db");
		} else {
			throw new ImplementationException("NOT verify_redactionuntagged_in_documentredactions_db");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redactiontagged_in_doc_history$")
	public void verify_redactiontagged_in_doc_history(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC12188 Verify that on deleting multi redactions from multi page document should not create orphan tags
			throw new ImplementationException("verify_redactiontagged_in_doc_history");
		} else {
			throw new ImplementationException("NOT verify_redactiontagged_in_doc_history");
		}

	}
	

	@When("^.*(\\[Not\\] )? rectangle_redaction_applied$")
	public void rectangle_redaction_applied(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Place rectangle redaction on the document
			//* Select 'SGSame1' redaction tag on Redaction Tag Save Confirmation popup
			//* Click 'Save' button on Redaction Tag Save Confirmation popup
			//
			throw new ImplementationException("rectangle_redaction_applied");
		} else {
			throw new ImplementationException("NOT rectangle_redaction_applied");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_transparent$")
	public void verify_redaction_transparent(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11665 Verify that rectangle redaction should be transparent all the time and content should be visible
			throw new ImplementationException("verify_redaction_transparent");
		} else {
			throw new ImplementationException("NOT verify_redaction_transparent");
		}

	}


	@And("^.*(\\[Not\\] )? click_all_page_redaction_button$")
	public void click_all_page_redaction_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Apply 'All Page' redaction
			//
			throw new ImplementationException("click_all_page_redaction_button");
		} else {
			throw new ImplementationException("NOT click_all_page_redaction_button");
		}

	}


	@When("^.*(\\[Not\\] )? place_redaction$")
	public void place_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("place_redaction");
		} else {
			throw new ImplementationException("NOT place_redaction");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_default_redaction_tag_selected$")
	public void verify_default_redaction_tag_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11377 Verify that when applies 'Rectangle' redaction for the first time then application should automatically select the 'Default Redaction Tag'TC11378 Verify that when applies 'This Page' redaction for the first time then application should automatically select the 'Default Redaction Tag'TC11379 Verify that when applies 'All Page' redaction for the first time then application should automatically select the 'Default Redaction Tag'TC11380 Verify that when applies 'Page Range' redaction for the first time then application should automatically select the 'Default Redaction Tag'
			//
			//* Verify 'Default Redaction Tag' is selected
			//
			throw new ImplementationException("verify_default_redaction_tag_selected");
		} else {
			throw new ImplementationException("NOT verify_default_redaction_tag_selected");
		}

	}


	@And("^.*(\\[Not\\] )? click_page_range_redaction_button$")
	public void click_page_range_redaction_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("click_page_range_redaction_button");
		} else {
			throw new ImplementationException("NOT click_page_range_redaction_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_control_in_off_state$")
	public void verify_redaction_control_in_off_state(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4828 Verify that when redaction control in red "on" state, if the icon is clicked again by the user, it must revert to an "off" state
			throw new ImplementationException("verify_redaction_control_in_off_state");
		} else {
			throw new ImplementationException("NOT verify_redaction_control_in_off_state");
		}

	}


	@And("^.*(\\[Not\\] )? nav_to_other_doc$")
	public void nav_to_other_doc(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click another document in the mini doc list window
			//
			throw new ImplementationException("nav_to_other_doc");
		} else {
			throw new ImplementationException("NOT nav_to_other_doc");
		}

	}


	@When("^.*(\\[Not\\] )? delete_redaction_with_keyboard_delete_key$")
	public void delete_redaction_with_keyboard_delete_key(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("delete_redaction_with_keyboard_delete_key");
		} else {
			throw new ImplementationException("NOT delete_redaction_with_keyboard_delete_key");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_not_deleted_with_keyboard$")
	public void verify_redaction_not_deleted_with_keyboard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9552 Verify that when 'Rectangle' redaction selected to delete with 'Delete' key from keyboard should be disabled keyboard actionTC9553 Verify that when 'This Page' redaction selected to delete with 'Delete' key from keyboard should be disabled keyboard action
			throw new ImplementationException("verify_redaction_not_deleted_with_keyboard");
		} else {
			throw new ImplementationException("NOT verify_redaction_not_deleted_with_keyboard");
		}

	}


	@And("^.*(\\[Not\\] )? click_this_page_redaction_button$")
	public void click_this_page_redaction_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click This Page redaction button
			//
			throw new ImplementationException("click_this_page_redaction_button");
		} else {
			throw new ImplementationException("NOT click_this_page_redaction_button");
		}

	}


	@And("^.*(\\[Not\\] )? this_page_redaction_applied$")
	public void this_page_redaction_applied(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Scroll to the page you want to redact
			//* Click 'This Page' redaction button
			//* Select 'SGSame1' redaction tag on Redaction Tag Save Confirmation popup
			//* Click 'Save' button on Redaction Tag Save Confirmation popup
			//
			throw new ImplementationException("this_page_redaction_applied");
		} else {
			throw new ImplementationException("NOT this_page_redaction_applied");
		}

	}


	@And("^.*(\\[Not\\] )? nav_back_to_first_doc$")
	public void nav_back_to_first_doc(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click the first document in the mini doc list window
			//
			throw new ImplementationException("nav_back_to_first_doc");
		} else {
			throw new ImplementationException("NOT nav_back_to_first_doc");
		}

	}


	@When("^.*(\\[Not\\] )? rectangle_redaction_deleted$")
	public void rectangle_redaction_deleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click redacted rectangle
			//* Click 'Delete Selected' trashcan button
			//
			throw new ImplementationException("rectangle_redaction_deleted");
		} else {
			throw new ImplementationException("NOT rectangle_redaction_deleted");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_rectangle_redaction_deleted$")
	public void verify_rectangle_redaction_deleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3495 Verify user can delete the redaction in a document
			throw new ImplementationException("verify_rectangle_redaction_deleted");
		} else {
			throw new ImplementationException("NOT verify_rectangle_redaction_deleted");
		}

	}


	@Given("^.*(\\[Not\\] )? default_redaction_tag_does_not_exist$")
	public void default_redaction_tag_does_not_exist(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* 'Default Redaction Tag' does not exist
			//
			throw new ImplementationException("default_redaction_tag_does_not_exist");
		} else {
			throw new ImplementationException("NOT default_redaction_tag_does_not_exist");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_alternate_redaction_tag_selected$")
	public void verify_alternate_redaction_tag_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11381 Verify that when 'Default Redaction Tag' doesn't exist then on applying 'Rectangle' redaction, the application must automatically select the first in the list of redaction tagsTC11382 Verify that when 'Default Redaction Tag' doesn't exist then on applying 'This Page' redaction, the application must automatically select the first in the list of redaction tagsTC11383 Verify that when 'Default Redaction Tag' doesn't exist then on applying 'All Page' redaction, the application must automatically select the first in the list of redaction tagsTC11384 Verify that when 'Default Redaction Tag' doesn't exist then on applying 'Page Range' redaction, the application must automatically select the first in the list of redaction tags
			//
			//* Verify another tag aside from 'Default Redaction Tag' is selected
			//
			throw new ImplementationException("verify_alternate_redaction_tag_selected");
		} else {
			throw new ImplementationException("NOT verify_alternate_redaction_tag_selected");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_rectangle_redaction$")
	public void verify_rectangle_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3226 Verify RMU/Reviewer can redact by selecting rectangle location in document in context of an assignment
			throw new ImplementationException("verify_rectangle_redaction");
		} else {
			throw new ImplementationException("NOT verify_rectangle_redaction");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redactions_menu_remains_open$")
	public void verify_redactions_menu_remains_open(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4983 Verify when Redactions menu is selected from doc view and navigates to another document from mini doc list child window then previously selected panels/menus should remain
			throw new ImplementationException("verify_redactions_menu_remains_open");
		} else {
			throw new ImplementationException("NOT verify_redactions_menu_remains_open");
		}

	}


	@And("^.*(\\[Not\\] )? edit_redaction_$")
	public void edit_redaction_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Prerequisite: Rectangle redaction exists on document
			//* Click existing rectangle redaction
			//* Change redaction dimensions
			//* Change redaction tag
			//
			throw new ImplementationException("edit_redaction_");
		} else {
			throw new ImplementationException("NOT edit_redaction_");
		}

	}


	@When("^.*(\\[Not\\] )? save_redaction_edit$")
	public void save_redaction_edit(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click 'Save' redaction button
			//
			throw new ImplementationException("save_redaction_edit");
		} else {
			throw new ImplementationException("NOT save_redaction_edit");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_edited$")
	public void verify_redaction_edited(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11427 Verify that user should be able to edit an applied redaction and change the redaction tag that was applied automatically
			throw new ImplementationException("verify_redaction_edited");
		} else {
			throw new ImplementationException("NOT verify_redaction_edited");
		}

	}


	@Given("^.*(\\[Not\\] )? login_to_saved_search_rmu$")
	public void login_to_saved_search_rmu(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:sightline_is_launchedlogin_as_rmuon_saved_search_page
			throw new ImplementationException("login_to_saved_search_rmu");
		} else {
			throw new ImplementationException("NOT login_to_saved_search_rmu");
		}

	}


	@And("^.*(\\[Not\\] )? open_saved_doc_view$")
	public void open_saved_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click 'Saved with SG1' search group
			//* Click radio button for first saved search
			//* Click 'Doc View' button at the top of the page
			//
			throw new ImplementationException("open_saved_doc_view");
		} else {
			throw new ImplementationException("NOT open_saved_doc_view");
		}

	}


	@And("^.*(\\[Not\\] )? select_docview_doc_$")
	public void select_docview_doc_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click doc with matcahing docid on Doc View page
			throw new ImplementationException("select_docview_doc_");
		} else {
			throw new ImplementationException("NOT select_docview_doc_");
		}

	}


	@And("^.*(\\[Not\\] )? apply_rectangle_redaction$")
	public void apply_rectangle_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonrectangle_redaction_appliedclick_grey_redact_tool
			throw new ImplementationException("apply_rectangle_redaction");
		} else {
			throw new ImplementationException("NOT apply_rectangle_redaction");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_propagation_in_exact_dupe$")
	public void verify_redaction_propagation_in_exact_dupe(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6030 Exact dupes in the same security group: Verify redaction propagation in a document when exact dupes in the same security groupVerify redaction propagation applied to exact duplicate doc
			//
			//* Redaction coordinates
			//* Redaction applied
			//* History of redaction has the last redaction applied
			//
			//Delete the redaction from both documents after verification
			throw new ImplementationException("verify_redaction_propagation_in_exact_dupe");
		} else {
			throw new ImplementationException("NOT verify_redaction_propagation_in_exact_dupe");
		}

	}


	@And("^.*(\\[Not\\] )? login_as_$")
	public void login_as_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Enter Username and password for impersonated user
			//* User is logged in
			//* Sightline Home page is displayed
			//
			throw new ImplementationException("login_as_");
		} else {
			throw new ImplementationException("NOT login_as_");
		}

	}


	@And("^.*(\\[Not\\] )? change_role_to_$")
	public void change_role_to_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click user info dropdownClick 'Change Role' buttonEnter matching impersonation informationSightline Home page is displayed
			throw new ImplementationException("change_role_to_");
		} else {
			throw new ImplementationException("NOT change_role_to_");
		}

	}


	@And("^.*(\\[Not\\] )? on_saved_search_page$")
	public void on_saved_search_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* User navigates to Saved Search page (/SavedSearch/SavedSearches)
			//* Saved Search page is displayed
			//
			throw new ImplementationException("on_saved_search_page");
		} else {
			throw new ImplementationException("NOT on_saved_search_page");
		}

	}


	@When("^.*(\\[Not\\] )? apply_this_page_redaction$")
	public void apply_this_page_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_this_page_redaction_buttonthis_page_redaction_appliedclick_grey_redact_tool
			throw new ImplementationException("apply_this_page_redaction");
		} else {
			throw new ImplementationException("NOT apply_this_page_redaction");
		}

	}
}//eof
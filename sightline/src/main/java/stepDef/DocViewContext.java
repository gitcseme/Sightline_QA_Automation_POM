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
	
	DocViewPage docView;

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
			//* Click 'Saved with SG1' search group
			String securityGroup = (String)dataMap.get("security_group");
			SavedSearch savedSearch = new SavedSearch(driver,0);
			savedSearch.getSavedSearchGroupName(securityGroup).click();	
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				savedSearch.getSavedSearchRadioButtonRows().FindWebElements().size()!=0  ;}}), Input.wait30);
			//* Click radio button for first saved search
			savedSearch.getSavedSearchRadioButtonRows().FindWebElements().get(0).click();
			
			//* Click 'Doc View' button at the top of the page
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(savedSearch.getToDocView().getWebElement()).perform();
			savedSearch.getToDocView2().click();
			driver.waitForPageToBeReady();

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
			String url = (String) dataMap.get("URL");
    		webDriver.get(url+"/SavedSearch/SavedSearches");
    		driver.waitForPageToBeReady();
			pass(dataMap, "On Saved search Page");
		} else {
			fail(dataMap,"Did not navigate to Saved search page");
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

	@And("^.*(\\[Not\\] )? on_doc_with_no_redactions$")
	public void on_doc_with_no_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select document with DOCID ID00000001
			throw new ImplementationException("on_doc_with_no_redactions");
		} else {
			throw new ImplementationException("NOT on_doc_with_no_redactions");
		}

	}


	@When("^.*(\\[Not\\] )? click_highlight_tool$")
	public void click_highlight_tool(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Highlight tool button
			throw new ImplementationException("click_highlight_tool");
		} else {
			throw new ImplementationException("NOT click_highlight_tool");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_sub_redaction_highlight_options_not_displayed$")
	public void verify_sub_redaction_highlight_options_not_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5059 Verify after impersonation user can not see the multi page and all page options from redactions, highlightingTC5057 Verify multi page and all page options are removed from redaction menus
			//
			//* 'Multi Page' and 'All Page' options are not available types of Redactions or Highlights
			//
			throw new ImplementationException("verify_sub_redaction_highlight_options_not_displayed");
		} else {
			throw new ImplementationException("NOT verify_sub_redaction_highlight_options_not_displayed");
		}

	}


	@And("^.*(\\[Not\\] )? nav_to_other_doc_view_doc_via_$")
	public void nav_to_other_doc_view_doc_via_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//If 'doc_nav' is 'doc list'Click the doc below the currently selected doc in the mini doc listIf 'doc_nav' is 'child'Click the 'Parent' doc in the mini doc listMake sure Redactions menu is activeClick 'Child' doc in the mini doc listIf 'doc_nav' is '<'Click the '<' button in the Doc View page document navigationIf 'doc_nav' is '>'Click the '>' button in the Doc View page document navigationIf 'doc_nav' is '<<'Click the '<<' button in the Doc View page document navigationIf 'doc_nav' is '>>'Click the '>>' button in the Doc View page document navigationIf 'doc_nav' is 'history'Select a document without the current document icon from the history drop down (Clock icon drop down at the top left of Doc View page)
			throw new ImplementationException("nav_to_other_doc_view_doc_via_");
		} else {
			throw new ImplementationException("NOT nav_to_other_doc_view_doc_via_");
		}

	}


	@When("^.*(\\[Not\\] )? doc_loaded$")
	public void doc_loaded(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("doc_loaded");
		} else {
			throw new ImplementationException("NOT doc_loaded");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redactions_menu_displayed$")
	public void verify_redactions_menu_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4975 Verify Redactions menu is selected from doc view and then navigates to another document then selected panels/menus previously selected should remain
			//
			//* Redacted menu buttons stay displayed after navigating to new document
			//
			throw new ImplementationException("verify_redactions_menu_displayed");
		} else {
			throw new ImplementationException("NOT verify_redactions_menu_displayed");
		}

	}


	@And("^.*(\\[Not\\] )? open_highlight_tool$")
	public void open_highlight_tool(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Highlight buttonClick 'Rectangle' highlight button
			throw new ImplementationException("open_highlight_tool");
		} else {
			throw new ImplementationException("NOT open_highlight_tool");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_highlight_reverts_off$")
	public void verify_highlight_reverts_off(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4831 When the user closes the redaction or highlight control in the ribbon, all states revert to "off"
			//
			//* Highlight ' ' are not selected
			//
			throw new ImplementationException("verify_highlight_reverts_off");
		} else {
			throw new ImplementationException("NOT verify_highlight_reverts_off");
		}

	}


	@And("^.*(\\[Not\\] )? open_redaction_tool$")
	public void open_redaction_tool(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Redaction buttonClick 'Rectangle' redaction button
			throw new ImplementationException("open_redaction_tool");
		} else {
			throw new ImplementationException("NOT open_redaction_tool");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_reverts_off$")
	public void verify_redaction_reverts_off(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4831 When the user closes the redaction or highlight control in the ribbon, all states revert to "off"
			//
			//* Redaction 'Rectangle' and 'This Page' are not selected
			//
			throw new ImplementationException("verify_redaction_reverts_off");
		} else {
			throw new ImplementationException("NOT verify_redaction_reverts_off");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_text_redaction_sub_redactions_option_not_displayed$")
	public void verify_text_redaction_sub_redactions_option_not_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6405 Verify that 'Text Redaction' option should not be displayed under Redactions sub menus on doc view
			//
			//* 'Text Redaction' option not displayed in Redactions menu
			//
			throw new ImplementationException("verify_text_redaction_sub_redactions_option_not_displayed");
		} else {
			throw new ImplementationException("NOT verify_text_redaction_sub_redactions_option_not_displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_submenu_off$")
	public void verify_redaction_submenu_off(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4687 Veify that by default redaction submenu icons is set to OFFTC4829 Verify that when redaction control in red "ON" state, if the icon is clicked again by the user, it must revert to an "OFF" state
			//
			//* Redactions submenu buttons are not selected
			//
			throw new ImplementationException("verify_redaction_submenu_off");
		} else {
			throw new ImplementationException("NOT verify_redaction_submenu_off");
		}

	}


	@And("^.*(\\[Not\\] )? on_doc_with_redactions$")
	public void on_doc_with_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select document with DOCID ID00000009If 'redaction_type' is rectangleClick redaction toolClick 'Rectangle' redaction buttonPlace redaction on documentIf 'redaction_type' is multipageClick redaction toolClick 'Rectangle' redaction buttonPlace redactions on pages 1 and 2 of the documentIf 'redaction_type' is currentClick redaction toolClick 'This Page' redaction buttonPlace redaction on current page documentIf 'redaction_type' is allClick redaction toolClick 'This Page' redaction buttonPlace redaction on all pages of the document
			throw new ImplementationException("on_doc_with_redactions");
		} else {
			throw new ImplementationException("NOT on_doc_with_redactions");
		}

	}


	@When("^.*(\\[Not\\] )? click_doc_view_print_button$")
	public void click_doc_view_print_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("click_doc_view_print_button");
		} else {
			throw new ImplementationException("NOT click_doc_view_print_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_pdf_document_with_redactions_displayed$")
	public void verify_pdf_document_with_redactions_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3689 Verify user after impersonation can download the file with redaction on click of the print icon from default view
			//
			//* Verify document printed with redactions
			//* Remove redactions after verification
			//
			throw new ImplementationException("verify_pdf_document_with_redactions_displayed");
		} else {
			throw new ImplementationException("NOT verify_pdf_document_with_redactions_displayed");
		}

	}


	@When("^.*(\\[Not\\] )? doc_view_download_native$")
	public void doc_view_download_native(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Download button above the documentClick 'Native' button
			throw new ImplementationException("doc_view_download_native");
		} else {
			throw new ImplementationException("NOT doc_view_download_native");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_document_downloaded$")
	public void verify_document_downloaded(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3701 Verify user should be able to download the associated file from default view when document is having redaction
			//
			//* Document downloaded
			//* Downloaded document is not redacted
			//* Remove redactions after verification
			//
			throw new ImplementationException("verify_document_downloaded");
		} else {
			throw new ImplementationException("NOT verify_document_downloaded");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_docid_in_pdf_file_name$")
	public void verify_docid_in_pdf_file_name(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3647 Verify user can download the document in PDF format without any redaction using print icon
			//
			//* Printed PDF file has the DOCID as the file name
			//
			throw new ImplementationException("verify_docid_in_pdf_file_name");
		} else {
			throw new ImplementationException("NOT verify_docid_in_pdf_file_name");
		}

	}


	@When("^.*(\\[Not\\] )? doc_view_download_txt$")
	public void doc_view_download_txt(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Download button above the documentClick 'Txt' button
			throw new ImplementationException("doc_view_download_txt");
		} else {
			throw new ImplementationException("NOT doc_view_download_txt");
		}

	}


	@When("^.*(\\[Not\\] )? doc_view_download_pdf$")
	public void doc_view_download_pdf(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Download button above the documentClick 'PDF' button
			throw new ImplementationException("doc_view_download_pdf");
		} else {
			throw new ImplementationException("NOT doc_view_download_pdf");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_pdf_document_without_redactions_displayed$")
	public void verify_pdf_document_without_redactions_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3688 Verify user after impersonation can download the file without redaction on click of the print icon from default view
			//
			//* Verify document printed without redactions
			//
			throw new ImplementationException("verify_pdf_document_without_redactions_displayed");
		} else {
			throw new ImplementationException("NOT verify_pdf_document_without_redactions_displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_icon_not_displayed_to_project_admin$")
	public void verify_redaction_icon_not_displayed_to_project_admin(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC2496 Verify that Project Admin cannot view 'Redaction' icon on doc view
			//
			//* Grey Redacte tool not displayed for Project Admin user
			//
//			Assert.assertFalse(docView.getGreyRedactButton().Visible());
//			driver.FindElementsByXPath("//*[@id='SearchDataTable']/tbody/tr")
			Assert.assertEquals(0, driver.FindElementsByXPath("gray-tab").size());
			pass(dataMap, "Redaction icon is not displayed");
		} else {
			fail(dataMap, "Redaction icon is displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_applied$")
	public void verify_redaction_applied(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6407 Verify that "Block Redaction" (Rectangle) and "This Page", must continue to be available and work they way they do today
			//
			//* Verify redaction applied
			//* Delete redaction after verification
			//
			throw new ImplementationException("verify_redaction_applied");
		} else {
			throw new ImplementationException("NOT verify_redaction_applied");
		}

	}


	@When("^.*(\\[Not\\] )? click_applied_redaction$")
	public void click_applied_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click applied redaction
			throw new ImplementationException("click_applied_redaction");
		} else {
			throw new ImplementationException("NOT click_applied_redaction");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_1_redaction_tag_per_redaction$")
	public void verify_1_redaction_tag_per_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4684 Verify one redaction tag for should be selected per redaction when redaction is done with all the available options for redactions
			//
			//* Only 1 redaction tag selected per redaction
			//
			throw new ImplementationException("verify_1_redaction_tag_per_redaction");
		} else {
			throw new ImplementationException("NOT verify_1_redaction_tag_per_redaction");
		}

	}


	@And("^.*(\\[Not\\] )? apply_rectangle_highlight$")
	public void apply_rectangle_highlight(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_highlight_toolclick_rectangle_highlight_buttonrectangle_highlight_appliedclick_highlight_tool
			throw new ImplementationException("apply_rectangle_highlight");
		} else {
			throw new ImplementationException("NOT apply_rectangle_highlight");
		}

	}


	@And("^.*(\\[Not\\] )? reload_the_page$")
	public void reload_the_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the browser reload page
			throw new ImplementationException("reload_the_page");
		} else {
			throw new ImplementationException("NOT reload_the_page");
		}

	}


	@And("^.*(\\[Not\\] )? rectangle_highlight_deleted$")
	public void rectangle_highlight_deleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click highlighted rectangle
			//* Click 'Delete Selected' trashcan button
			//
			throw new ImplementationException("rectangle_highlight_deleted");
		} else {
			throw new ImplementationException("NOT rectangle_highlight_deleted");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_highlight_deleted_in_doc_view$")
	public void verify_redaction_highlight_deleted_in_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC7848 Verify the Redaction Tag & Highlighting is deleted successfully in DocView
			//
			//* Deleted Redaction & Highlight are not displayed on document
			//
			throw new ImplementationException("verify_redaction_highlight_deleted_in_doc_view");
		} else {
			throw new ImplementationException("NOT verify_redaction_highlight_deleted_in_doc_view");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_deleted_in_doc_view$")
	public void verify_redaction_deleted_in_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC7846 Verify the Redaction Tag is deleted successfully in DocView
			//
			//* Deleted redaction does not display on document
			//
			throw new ImplementationException("verify_redaction_deleted_in_doc_view");
		} else {
			throw new ImplementationException("NOT verify_redaction_deleted_in_doc_view");
		}

	}


	@And("^.*(\\[Not\\] )? apply_rectangle_redaction_$")
	public void apply_rectangle_redaction_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonrectangle_redaction_applied_{SGSame2}click_grey_redact_tool
			throw new ImplementationException("apply_rectangle_redaction_");
		} else {
			throw new ImplementationException("NOT apply_rectangle_redaction_");
		}

	}


	@And("^.*(\\[Not\\] )? log_out$")
	public void log_out(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click user account dropdown
			//* Click 'Sign Out' button
			//
			throw new ImplementationException("log_out");
		} else {
			throw new ImplementationException("NOT log_out");
		}

	}


	@When("^.*(\\[Not\\] )? apply_rectangle_redaction_without_changing_tag$")
	public void apply_rectangle_redaction_without_changing_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonrectangle_redaction_applied_default_tagclick_grey_redact_tool
			throw new ImplementationException("apply_rectangle_redaction_without_changing_tag");
		} else {
			throw new ImplementationException("NOT apply_rectangle_redaction_without_changing_tag");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_saved_with_last_applied_tag$")
	public void verify_redaction_saved_with_last_applied_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11385 Verify that when applies rectangle redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)TC11386 Verify that when applies 'This Page' redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)
			//
			//* Redaction saved with the last used tag on the document after logging in with a different user
			//
			throw new ImplementationException("verify_redaction_saved_with_last_applied_tag");
		} else {
			throw new ImplementationException("NOT verify_redaction_saved_with_last_applied_tag");
		}

	}


	@And("^.*(\\[Not\\] )? apply_this_page_redaction_$")
	public void apply_this_page_redaction_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonthis_page_redaction_applied_{SGSame2}click_grey_redact_tool
			throw new ImplementationException("apply_this_page_redaction_");
		} else {
			throw new ImplementationException("NOT apply_this_page_redaction_");
		}

	}


	@When("^.*(\\[Not\\] )? apply_this_page_redaction_without_changing_tag$")
	public void apply_this_page_redaction_without_changing_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonthis_page_redaction_applied_with_default_tagclick_grey_redact_tool
			throw new ImplementationException("apply_this_page_redaction_without_changing_tag");
		} else {
			throw new ImplementationException("NOT apply_this_page_redaction_without_changing_tag");
		}

	}


	@When("^.*(\\[Not\\] )? apply_rectangle_redaction_with_default_tag$")
	public void apply_rectangle_redaction_with_default_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonrectangle_redaction_applied_with_default_tagclick_grey_redact_tool
			throw new ImplementationException("apply_rectangle_redaction_with_default_tag");
		} else {
			throw new ImplementationException("NOT apply_rectangle_redaction_with_default_tag");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_edited_redaction_tag_is_latest_redaction_tag$")
	public void verify_edited_redaction_tag_is_latest_redaction_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11428 Verify that after editing a redaction and applies a redaction tag to a redaction, then this applied redaction tag should be considered as the latest redaction tag
			//
			//* Edited redaction tag is considered the latest redaction tag when adding a new redaction
			//
			throw new ImplementationException("verify_edited_redaction_tag_is_latest_redaction_tag");
		} else {
			throw new ImplementationException("NOT verify_edited_redaction_tag_is_latest_redaction_tag");
		}

	}


	@And("^.*(\\[Not\\] )? open_saved_search_doc_view_new_tab$")
	public void open_saved_search_doc_view_new_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Open new tab
			//* User navigates to http://mtpvtsslwb01.consilio.com/SavedSearch/SavedSearches
			//* Click 'Saved with SG1' search group
			//* Click radio button for first saved search
			//* Click 'Doc View' button at the top of the page
			//
			throw new ImplementationException("open_saved_search_doc_view_new_tab");
		} else {
			throw new ImplementationException("NOT open_saved_search_doc_view_new_tab");
		}

	}


	@And("^.*(\\[Not\\] )? switch_to_tab_1$")
	public void switch_to_tab_1(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Switch to tab 1
			throw new ImplementationException("switch_to_tab_1");
		} else {
			throw new ImplementationException("NOT switch_to_tab_1");
		}

	}


	@And("^.*(\\[Not\\] )? switch_to_tab_2$")
	public void switch_to_tab_2(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Switch to tab 2
			throw new ImplementationException("switch_to_tab_2");
		} else {
			throw new ImplementationException("NOT switch_to_tab_2");
		}

	}


	@When("^.*(\\[Not\\] )? click_rectangle_redaction$")
	public void click_rectangle_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click existing rectangle redaction
			throw new ImplementationException("click_rectangle_redaction");
		} else {
			throw new ImplementationException("NOT click_rectangle_redaction");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_cannot_delete_redaction_on_2nd_tab_before_reload$")
	public void verify_user_cannot_delete_redaction_on_2nd_tab_before_reload(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			// TC6447 Verify that same user with two different tabs in the same browser, and confirm that able to delete redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* 2nd tab user cannot delete redactions
			//
			throw new ImplementationException("verify_user_cannot_delete_redaction_on_2nd_tab_before_reload");
		} else {
			throw new ImplementationException("NOT verify_user_cannot_delete_redaction_on_2nd_tab_before_reload");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_can_apply_redaction_on_2nd_tab_after_reload$")
	public void verify_user_can_apply_redaction_on_2nd_tab_after_reload(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6445 Verify that same user with two different tabs in the same browser, and confirm that able to add redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* After document reloaded, user can apply redaction on tab 2
			//
			throw new ImplementationException("verify_user_can_apply_redaction_on_2nd_tab_after_reload");
		} else {
			throw new ImplementationException("NOT verify_user_can_apply_redaction_on_2nd_tab_after_reload");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_another_user_applied_redaction_message$")
	public void verify_another_user_applied_redaction_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6445 Verify that same user with two different tabs in the same browser, and confirm that able to add redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XMLTC6446 Verify that same user with two different tabs in the same browser, and confirm that able to edit redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XMLTC6447 Verify that same user with two different tabs in the same browser, and confirm that able to delete redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* Error message in Redaction sub-menu displayed "Another user has applied redactions, annotations or Reviewer Remarks to this document since you presented it in DocView. You may not apply markup - because that would overwrite the work done by the other user. Please reload the document."
			//
			throw new ImplementationException("verify_another_user_applied_redaction_message");
		} else {
			throw new ImplementationException("NOT verify_another_user_applied_redaction_message");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_cannot_add_redaction_on_2nd_tab_before_reload$")
	public void verify_user_cannot_add_redaction_on_2nd_tab_before_reload(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			// TC6445 Verify that same user with two different tabs in the same browser, and confirm that able to add redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* 2nd tab user cannot apply redactions
			//
			throw new ImplementationException("verify_user_cannot_add_redaction_on_2nd_tab_before_reload");
		} else {
			throw new ImplementationException("NOT verify_user_cannot_add_redaction_on_2nd_tab_before_reload");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_can_edit_redaction_on_2nd_tab_after_reload$")
	public void verify_user_can_edit_redaction_on_2nd_tab_after_reload(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6446 Verify that same user with two different tabs in the same browser, and confirm that able to edit redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* After document reloaded, user can edit redaction on tab 2
			//
			throw new ImplementationException("verify_user_can_edit_redaction_on_2nd_tab_after_reload");
		} else {
			throw new ImplementationException("NOT verify_user_can_edit_redaction_on_2nd_tab_after_reload");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_on_2nd_tab_deleted_after_reload$")
	public void verify_redaction_on_2nd_tab_deleted_after_reload(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6447 Verify that same user with two different tabs in the same browser, and confirm that able to delete redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* After document reloaded, redaction is deleted from tab 2
			//
			throw new ImplementationException("verify_redaction_on_2nd_tab_deleted_after_reload");
		} else {
			throw new ImplementationException("NOT verify_redaction_on_2nd_tab_deleted_after_reload");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_cannot_edit_redaction_on_2nd_tab_before_reload$")
	public void verify_user_cannot_edit_redaction_on_2nd_tab_before_reload(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			// TC6446 Verify that same user with two different tabs in the same browser, and confirm that able to edit redactions to the same records successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* 2nd tab user cannot edit redactions
			//
			throw new ImplementationException("verify_user_cannot_edit_redaction_on_2nd_tab_before_reload");
		} else {
			throw new ImplementationException("NOT verify_user_cannot_edit_redaction_on_2nd_tab_before_reload");
		}

	}
}//eof
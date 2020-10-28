package stepDef;

import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import org.apache.poi.hssf.record.formula.functions.Count;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import com.beust.jcommander.JCommander.Builder;

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
			docView = new DocViewPage(driver,0);
			Actions builder = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getGreyRedactButton().Displayed()  ;}}), Input.wait30); 
			
			//Move to grey button and click
			builder.moveToElement(docView.getGreyRedactButton().getWebElement()).perform();;
			Thread.sleep(2000);
			docView.getGreyRedactButton().click();

			driver.waitForPageToBeReady();
			int originalRedactionCount = docView.getExistingRectangleRedactions().FindWebElements().size();
			dataMap.put("originalRedactionCount", originalRedactionCount);

			pass(dataMap, "Clicked grey redact button");
			
		}
		else fail(dataMap, "Clicked the grey redact tool");

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
			String securityGroup = (String)dataMap.get("security_group");
			SavedSearch savedSearch = new SavedSearch(driver,0);
			savedSearch.getSavedSearchGroupName(securityGroup).click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				savedSearch.getSavedSearchRadioButtonRows().FindWebElements().size()!=0  ;}}), Input.wait30); 
			//* Click radio button for first saved search
			savedSearch.getSavedSearchRadioButtonRows().FindWebElements().get(0).click();
			//* Click 'Doc View' button at the top of the page
			savedSearch.getToDocView().click();
			driver.waitForPageToBeReady();
			pass(dataMap, "Open saved search doc view");
		} else {
			fail(dataMap,"Cannot open save search doc view");
		}

	}


	@And("^.*(\\[Not\\] )? click_rectangle_redaction_button$")
	public void click_rectangle_redaction_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Find rectangle button and click it
			for(WebElement x: docView.getRectangleButton().FindWebElements()) {
				if(x.isDisplayed() && x.isEnabled()) x.click();
			}
			pass(dataMap, "Clicked Rectangle Button");
			
		}
		else fail(dataMap, "failed to click button");

	}


	@And("^.*(\\[Not\\] )? open_dev_tools_f12$")
	public void open_dev_tools_f12(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Open developer tools by pressing F12
			//
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F12);
			robot.keyRelease(KeyEvent.VK_F12);
		}
		else fail(dataMap, "Failed to open developer tools");

	}


	@And("^.*(\\[Not\\] )? add_redaction_to_page_without_saving$")
	public void add_redaction_to_page_without_saving(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Place rectangle redaction on the document
			//* Select 'SGSame1' redaction tag on Redaction Tag Save Confirmation popup
			//
			System.out.println("waiting");
			WebElement el = docView.getRectangleButton().FindWebElements().get(0);
			int x = el.getLocation().getX();
			int y = el.getLocation().getY();
			System.out.println(x);
			System.out.println(y);
			Actions builder = new Actions(driver.getWebDriver());

			docView.redactbyrectangle(0,0,0,"SGSame1");
		}
		else fail(dataMap, "Failed to add redaction to page without saving");

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
			//* Place rectangle redaction on the document
			//* Select 'SGSame1' redaction tag on Redaction Tag Save Confirmation popup

			//Using consilio's method, these parameters seem to work well
			docView.redactbyrectangle(100, 10, 0, "SGSame1");

			//* Click 'Save' button on Redaction Tag Save Confirmation popup
			docView.getDocViewSaveRedactionButton().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getConfirmPopUp().Displayed()  ;}}), Input.wait30); 
			 
			pass(dataMap, "Redaction rectangle was applied");
		}
		else fail(dataMap, "Redaction rectangle could not be applied");

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_transparent$")
	public void verify_redaction_transparent(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//TC11665 Verify that rectangle redaction should be transparent all the time and content should be visible
		if (scriptState) {
			double opacity = 0.0;
			//Verify all Redactions have an opacity (transparency) of less than 1
			for(WebElement x: docView.getExistingRectangleRedactions().FindWebElements()) {
				opacity = Double.parseDouble(x.getCssValue("opacity"));
				Assert.assertTrue(opacity<1.0);
			}
			pass(dataMap, "Redactions have remainded transparent");
		}
		else fail(dataMap, "redactions were not transparent");

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
			 Actions actions = new Actions(driver.getWebDriver());  
             WebElement text = docView.getDocView_Redactrec_textarea();
             actions.moveToElement(text, 100,10).clickAndHold().moveByOffset(100, 10).release().perform();
			pass(dataMap, "placed redaction");
		}
		else fail(dataMap, "couldn't place redaction");

	}


	@Then("^.*(\\[Not\\] )? verify_default_redaction_tag_selected$")
	public void verify_default_redaction_tag_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//TC11377 Verify that when applies 'Rectangle' redaction for the first time then application should automatically select the 'Default Redaction Tag'TC11378 Verify that when applies 'This Page' redaction for the first time then application should automatically select the 'Default Redaction Tag'TC11379 Verify that when applies 'All Page' redaction for the first time then application should automatically select the 'Default Redaction Tag'TC11380 Verify that when applies 'Page Range' redaction for the first time then application should automatically select the 'Default Redaction Tag'
		if (scriptState) {
			//
			//Grab our default tag and compare with expected value. 
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_SelectReductionLabel().Displayed()  ;}}), Input.wait30); 
			String defaultTag = docView.getDocView_SelectReductionLabel().selectFromDropdown().getFirstSelectedOption().getText();
			Assert.assertEquals(defaultTag, "Default Redaction Tag");
			pass(dataMap, "The default tag was selected");
		}
		else fail(dataMap, "Verify default redaction tag not selected");

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
			String color = "";
			for(WebElement x: docView.getRectangleButton().FindWebElements()) {
				if(x.isDisplayed() && x.isEnabled()) {
					color = x.getCssValue("color");
				}
			}
			Assert.assertEquals(color, "rgba(118, 115, 115, 1)");
		}
		else fail(dataMap, "Color was not in off state");

	}


	@And("^.*(\\[Not\\] )? nav_to_other_doc$")
	public void nav_to_other_doc(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click another document in the mini doc list window
			//
			docView.getDocViewTableRows().FindWebElements().get(1).click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewNumOfPages().FindWebElements().size()!=0  ;}}), Input.wait30); 
		}
		else fail(dataMap, "Couldnt select another document");

	}


	@When("^.*(\\[Not\\] )? delete_redaction_with_keyboard_delete_key$")
	public void delete_redaction_with_keyboard_delete_key(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 

			//Save amount of redactions before we attempt to delete
			int existingRedactions = docView.getExistingRectangleRedactions().FindWebElements().size();
			if(existingRedactions==0) {
				fail(dataMap, "No redactions to test");
				return;
			}
			dataMap.put("firstRedactionCount", existingRedactions);
			Actions builder = new Actions(driver.getWebDriver());
			//Get the last redaction added(last index in our list of redactions)
			builder.moveToElement(docView.getExistingRectangleRedactions().FindWebElements().get(existingRedactions-1)).click().build().perform();
			builder.sendKeys(Keys.DELETE);
			pass(dataMap, "Attempted to delete redaction with keyboard");
		}
		else fail(dataMap, "failed to attempt to delete redaction with keyboard");

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_not_deleted_with_keyboard$")
	public void verify_redaction_not_deleted_with_keyboard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9552 Verify that when 'Rectangle' redaction selected to delete with 'Delete' key from keyboard should be disabled keyboard actionTC9553 Verify that when 'This Page' redaction selected to delete with 'Delete' key from keyboard should be disabled keyboard action

			int firstRedactionCount = (int)dataMap.get("firstRedactionCount");
			int currentRedactions = docView.getExistingRectangleRedactions().FindWebElements().size();
			//Make sure the number of redactions we recorded before attempting to delete is equal to the number of redactions now (nothing got deleted)
			Assert.assertEquals(firstRedactionCount, currentRedactions);

			pass(dataMap, "Redaction was not deleted with keyboard");
		}
		else fail(dataMap, "Redaction was deleted with keyboard");

	}


	@And("^.*(\\[Not\\] )? click_this_page_redaction_button$")
	public void click_this_page_redaction_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click This Page redaction button
			//
			for(WebElement x: docView.getThisPageButton().FindWebElements()) {
				if(x.isDisplayed() && x.isEnabled()) x.click();
			}
			pass(dataMap, "Clicked the this page redaction button");
		}
		else fail(dataMap, "Failed to click this page redactin button");

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
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 
			docView.getDocViewTableRows().FindWebElements().get(0).click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewNumOfPages().FindWebElements().size()!=0  ;}}), Input.wait30); 
			pass(dataMap, "navigated back to original document");

		}
		else fail(dataMap, "couldnt navigate back to original document");

	}


	@When("^.*(\\[Not\\] )? rectangle_redaction_deleted$")
	public void rectangle_redaction_deleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			if(size == 0) {
				fail(dataMap, "no redactions to delete");
				return;
			}
			Actions builder = new Actions(driver.getWebDriver());
			//Get the last redaction added(last index in our list of redactions)
			builder.moveToElement(docView.getExistingRectangleRedactions().FindWebElements().get(size-1)).click().build().perform();
			//get rid of prior save popup
			if(docView.getCloseButton().FindWebElements().size()!=0) {
				docView.getCloseButton().FindWebElements().get(0).click();
			}
			//delete redaction
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_Annotate_DeleteIcon().Displayed()  ;}}), Input.wait30); 
			docView.getDocView_Annotate_DeleteIcon().click();
			driver.waitForPageToBeReady();
			pass(dataMap, "deleted redaction");
		}
		else fail(dataMap, "Could not delete redaction");

	}


	@Then("^.*(\\[Not\\] )? verify_rectangle_redaction_deleted$")
	public void verify_rectangle_redaction_deleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			//TC3495 Verify user can delete the redaction in a document
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getConfirmPopUp().Displayed()  ;}}), Input.wait30); 

			Assert.assertEquals(docView.getConfirmPopUp().getText(), "Redaction Removed successfully.");
			pass(dataMap, "Deleted Redaction successfully");

		}
		else fail(dataMap, "failed to delete redaction");

	}


	@Given("^.*(\\[Not\\] )? default_redaction_tag_does_not_exist$")
	public void default_redaction_tag_does_not_exist(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* 'Default Redaction Tag' does not exist
			//
			pass(dataMap, "This is a script where a default redaction does not exist");
		}
		else fail(dataMap, "default redaction must exist");

	}


	@Then("^.*(\\[Not\\] )? verify_alternate_redaction_tag_selected$")
	public void verify_alternate_redaction_tag_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11381 Verify that when 'Default Redaction Tag' doesn't exist then on applying 'Rectangle' redaction, the application must automatically select the first in the list of redaction tagsTC11382 Verify that when 'Default Redaction Tag' doesn't exist then on applying 'This Page' redaction, the application must automatically select the first in the list of redaction tagsTC11383 Verify that when 'Default Redaction Tag' doesn't exist then on applying 'All Page' redaction, the application must automatically select the first in the list of redaction tagsTC11384 Verify that when 'Default Redaction Tag' doesn't exist then on applying 'Page Range' redaction, the application must automatically select the first in the list of redaction tags
			//
			//* Verify another tag aside from 'Default Redaction Tag' is selected
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_SelectReductionLabel().Displayed()  ;}}), Input.wait30); 
			String defaultTag = docView.getDocView_SelectReductionLabel().selectFromDropdown().getFirstSelectedOption().getText();
			Assert.assertFalse(defaultTag.equals("Default Redaction Tag"));
			pass(dataMap, "verified alternate redaction tag");
		}
		else fail(dataMap, "could not verify alternate redaction tag");

	}


	@Then("^.*(\\[Not\\] )? verify_rectangle_redaction$")
	public void verify_rectangle_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3226 Verify RMU/Reviewer can redact by selecting rectangle location in document in context of an assignment
			
			driver.getWebDriver().navigate().refresh();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().size()!=0 ;}}), Input.wait30); 

			//Just make sure that the original Redcation count we recorded before we added the redaction, is 1 less than the new count after we added a redaction
			int originalRedactionCount = (int)dataMap.get("originalRedactionCount");
			int currentRedactionCount = docView.getExistingRectangleRedactions().FindWebElements().size();
			Assert.assertEquals(currentRedactionCount,originalRedactionCount+1);
			pass(dataMap, "verified rectangle redaction");
		}
		else fail(dataMap, "could not verify rectangle_redaction");


	}


	@Then("^.*(\\[Not\\] )? verify_redactions_menu_remains_open$")
	public void verify_redactions_menu_remains_open(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4983 Verify when Redactions menu is selected from doc view and navigates to another document from mini doc list child window then previously selected panels/menus should remain
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 
			Boolean rectangleBtn = false;
			Boolean thisPageBtn = false;
			for(WebElement x: docView.getRectangleButton().FindWebElements()) {
				if(x.isDisplayed() && x.isEnabled()) rectangleBtn = true;
			}
			for(WebElement x: docView.getThisPageButton().FindWebElements()) {
				if(x.isDisplayed() && x.isEnabled()) thisPageBtn = true;
			}
			Assert.assertTrue(thisPageBtn);
			Assert.assertTrue(rectangleBtn);
			pass(dataMap, "redactions menu remained open");

		}
		else fail(dataMap, "redactions menu did not remain open");

	}


	@And("^.*(\\[Not\\] )? edit_redaction_$")
	public void edit_redaction_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Prerequisite: Rectangle redaction exists on document
			docView = new DocViewPage(driver, 0);
			driver.waitForPageToBeReady();
			Actions builder = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();

			//get original dimension
			String originalDimension = docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getCssValue("height");


			//* Click existing rectangle redaction
			builder.moveToElement(docView.getExistingRectangleRedactions().FindWebElements().get(size-1)).click().build().perform();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getBottomEditSideOfRedactionRectangle().Enabled() && docView.getBottomEditSideOfRedactionRectangle().Displayed()  ;}}), Input.wait30); 
			//* Change redaction dimensions
             builder.moveToElement(docView.getBottomEditSideOfRedactionRectangle().getWebElement()).clickAndHold().moveByOffset(-5, -5).release().perform();

             //get new dimension
             String afterEditDimension = docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getCssValue("height");
             dataMap.put("originalDimension", originalDimension);
             dataMap.put("afterEditDimension", afterEditDimension);

			//* Change redaction tag
             String beforeTag = docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getText();
             docView.getDocView_Redactedit_selectlabel().click();
             for(WebElement x: docView.getRedactionTagOptions().FindWebElements()) {
            	 	if(x.getText().equals(beforeTag)) continue;
            	 	x.click();
            	 	break;
             }
             String afterTag = docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getText();

             dataMap.put("beforeTag", beforeTag);
             dataMap.put("afterTag", afterTag);
             pass(dataMap, "edited the redaction");
		}
		else fail(dataMap, "could not edit the redaction");

	}


	@When("^.*(\\[Not\\] )? save_redaction_edit$")
	public void save_redaction_edit(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click 'Save' redaction button
			//
			docView.getRedactionEditSaveBtn().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getConfirmPopUp().Displayed()  ;}}), Input.wait30); 

			pass(dataMap, "saved the redaction");
		}
		else fail(dataMap, "could not save the redaction");

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_edited$")
	public void verify_redaction_edited(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11427 Verify that user should be able to edit an applied redaction and change the redaction tag that was applied automatically
			String firstDimension = (String)dataMap.get("originalDimension");
			String secondDimension = (String)dataMap.get("afterEditDimension");
			String firstTag = (String)dataMap.get("beforeTag");
			String secondTag = (String)dataMap.get("afterTag");
			//Make sure dimensions have changed
			Assert.assertFalse(firstDimension.equals(secondDimension));
			//Make sure tags have changed
			Assert.assertFalse(firstTag.equals(secondTag));
			pass(dataMap, "verified that the redaction was edited");
		}
		else fail(dataMap, "could not verify that the redaction was edited");

	}
}//eof
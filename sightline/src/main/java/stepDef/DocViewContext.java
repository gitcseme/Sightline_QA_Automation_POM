package stepDef;

import static org.testng.Assert.assertEquals;

import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.DriverAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;
import javax.swing.text.ChangedCharSetException;

import java.util.List;
import java.util.Random;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.aop.ThrowsAdvice;
import org.apache.commons.lang3.SystemUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.record.formula.functions.Count;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.beust.jcommander.JCommander.Builder;
import com.sun.jna.platform.unix.X11;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.conditional.ElseAction;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.es.E;
import cucumber.api.java.en.And;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.RedactionPage;
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
			driver = (Driver)dataMap.get("originalDriver");
			driver.switchTo().window((String)dataMap.get("originalWindow"));
		}
		else fail(dataMap, "failed to switch to user1");

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
			driver = (Driver)dataMap.get("secondDriver");
			driver.switchTo().window((String)dataMap.get("secondWindow"));
			driver.waitForPageToBeReady();
		}
		else fail(dataMap, "Failed to switch to user2");

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

			//Store default value of dropdown for later context's verifications
			String tag = (String)dataMap.get("tag");
			if(tag == null) tag = "Default Automation Redaction";

			//Using consilio's method, these parameters seem to work well
			docView.redactbyrectangle(100, 10, 0, tag);
			dataMap.put("defaultValue", docView.PageViewTagString());

			//* Click 'Save' button on Redaction Tag Save Confirmation popup
			docView.getDocViewSaveRedactionButton().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getConfirmPopUp().Displayed()  ;}}), Input.wait30); 
			
			
			dataMap.put("originalRedactionCount", docView.getExistingRectangleRedactions().FindWebElements().size());
			
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			double originalx = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("x"));
			double originaly = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("y"));
			double width = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("width"));
			double height = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("height"));
			dataMap.put("originalx", originalx);
			dataMap.put("originaly", originaly);
			dataMap.put("width", width);
			dataMap.put("height", height);
			 
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
			if(dataMap.get("defaultValue")!=null) {
				Assert.assertEquals((String)dataMap.get("defaultValue"), "SGSame1");
				pass(dataMap, "the default tag was selected");
				return;
			}
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
			//Save data about the applied redaction
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			double originalx = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("x"));
			double originaly = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("y"));
			double width = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("width"));
			double height = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("height"));
			dataMap.put("originalx", originalx);
			dataMap.put("originaly", originaly);
			dataMap.put("width", width);
			dataMap.put("height", height);

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
			docView.getDocView_SelectReductionLabel().click();
			String defaultValue = docView.getDocView_SelectReductionLabel().selectFromDropdown().getFirstSelectedOption().getText();

			String tag = (String)dataMap.get("tag");
			dataMap.put("defaultValue", defaultValue);
			docView.getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(tag);
			
			docView.getDocViewSaveRedactionButton().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getConfirmPopUp().Displayed()  ;}}), Input.wait30); 
			pass(dataMap, "applied this page redaction");
			
		}
		else fail(dataMap, "failed to apply this page redaction");

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
			double originalHeight = (double)dataMap.get("height");
			double originalWidth = (double)dataMap.get("width");

			//Find our rectangle to delete based on dimensions of last placed highlight
			for(WebElement x: docView.getExistingRectangleRedactions().FindWebElements()) {
				if(Double.parseDouble(x.getAttribute("width")) != originalWidth && Double.parseDouble(x.getAttribute("height")) != originalHeight) continue;
				builder.moveToElement(x).click().build().perform();
				break;
			}

			//Get the last redaction added(last index in our list of redactions)
			//builder.moveToElement(docView.getExistingRectangleRedactions().FindWebElements().get(size-1)).click().build().perform();

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
			Random rnd = new Random();
			Actions builder = new Actions(driver.getWebDriver());

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				!docView.getDocViewTotalPages().getText().equals("")  ;}}), Input.wait30); 
			String pageNumString = docView.getDocViewTotalPages().getText();
			int totalPages = Integer.parseInt((pageNumString.split("of "))[1].split(" pages")[0]);

			//Process of zooming out and scrolling through pages until we get into the view of a redaction to edit
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getMagnifyGlassZoomOutButton().Displayed()	;}}), Input.wait30); 
			for(int i =0; i<5;i++) docView.getMagnifyGlassZoomOutButton().click();
			
			for(int j=0; j<totalPages; j++) {
				if(docView.getExistingRectangleRedactions().FindWebElements().size()>0) break;
				docView.getNextRedactionPage().click();
				Thread.sleep(3000);
			}
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			dataMap.put("existingRedactions", size);
			
			//get original dimension
			double originalDimension = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("width"))
					* Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("height"));

			
			//* Click existing rectangle redaction
			builder.moveToElement(docView.getExistingRectangleRedactions().FindWebElements().get(size-1)).click().build().perform();

			//* Change redaction dimensions
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getBottomEditSideOfRedactionRectangle().Enabled() && docView.getBottomEditSideOfRedactionRectangle().Displayed()  ;}}), Input.wait30); 
			String temp= docView.getBottomEditSideOfRedactionRectangle().GetAttribute("data-pcc-mark");
	
			for(WebElement x: docView.getAllEditSidesOfRedactionRectangle(temp).FindWebElements()) {
            	 	int xCord = rnd.nextInt(6)-3;
            	 	int yCord = rnd.nextInt(6)-3;
            	 	if(x.isDisplayed() && x.isEnabled()) {
            	 		builder.clickAndHold(x).moveByOffset(xCord,yCord).release().perform();
				}
			}
			

             //get new dimension
			double afterEditDimension = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("width"))
					* Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("height"));

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
             String afterTag = docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getAttribute("title");
             //Wait for edited tag to update
             driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getAttribute("title").equals(afterTag) ;}}), Input.wait30); 
             //Save our edit
              driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getRedactionEditSaveBtn().Displayed() ;}}), Input.wait30); 
              docView.getRedactionEditSaveBtn().click();

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
			double firstDimension = (double)dataMap.get("originalDimension");
			double secondDimension = (double)dataMap.get("afterEditDimension");
			String firstTag = (String)dataMap.get("beforeTag");
			String secondTag = (String)dataMap.get("afterTag");
			//Make sure dimensions have changed
			if( ((String)dataMap.get("dimensions")).equals("true")) {
				Assert.assertFalse(firstDimension==secondDimension);
			}
			//Make sure tags have changed
			Assert.assertFalse(firstTag.equals(secondTag));
			pass(dataMap, "verified that the redaction was edited");
		}
		else fail(dataMap, "could not verify that the redaction was edited");

	}


	@Given("^.*(\\[Not\\] )? login_to_saved_search_rmu$")
	public void login_to_saved_search_rmu(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:sightline_is_launchedlogin_as_rmuon_saved_search_page
			sightline_is_launched(scriptState, dataMap);
			super.login_as_rmu(scriptState, dataMap);
			on_saved_search_page(scriptState, dataMap);
			
			pass(dataMap, "successfully logged into saved search as RMU");
		}
		else fail(dataMap, "failed to login to saved search as RMU");

	}


	@And("^.*(\\[Not\\] )? open_saved_doc_view$")
	public void open_saved_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//* Click 'Saved with SG1' search group
			String securityGroup = (String)dataMap.get("security_group");
			SavedSearch savedSearch = new SavedSearch(driver,0);
			if(securityGroup.equals("SG3")) savedSearch.clickSG3Nested();
			else savedSearch.getSavedSearchGroupName(securityGroup).click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				savedSearch.getSavedSearchRadioButtonRows().FindWebElements().size()!=0  ;}}), Input.wait30); 
//			* Click radio button for first saved search
			savedSearch.getSavedSearchRadioButtonRows().FindWebElements().get(0).click();

			//* Click 'Doc View' button at the top of the page
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(savedSearch.getToDocView().getWebElement()).perform();
			//savedSearch.getToDocView().click();
			savedSearch.getToDocView2().click();

			driver.waitForPageToBeReady();
			pass(dataMap, "Open saved search doc view");

		}
		else fail(dataMap, "failed to open saved doc view");

	}


	@And("^.*(\\[Not\\] )? select_docview_doc_$")
	public void select_docview_doc_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click doc with matcahing docid on Doc View page
			docView = new DocViewPage(driver, 0);
			String target = (String)dataMap.get("docid");
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewTableRows().FindWebElements().size()!=0  ;}}), Input.wait30); 
			for(WebElement x: docView.getDocViewTableRows().FindWebElements()) {
				if ( (x.findElements(By.cssSelector(" td")).get(1).getText().equals(target)) ) {
					x.click();
				}
			}
			pass(dataMap, "successfully opened the docview doc");
		}
		else fail(dataMap, "failed to select the docview doc");

	}


	@And("^.*(\\[Not\\] )? apply_rectangle_redaction$")
	public void apply_rectangle_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonrectangle_redaction_appliedclick_grey_redact_tool
			click_grey_redact_tool(scriptState, dataMap);
			click_rectangle_redaction_button(scriptState, dataMap);
			rectangle_redaction_applied(scriptState, dataMap);
			click_grey_redact_tool(scriptState, dataMap);
			pass(dataMap, "successfully applied rectangle redaction");
		}
		else fail(dataMap, "failed to apply rectangle redaction");

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

			double originalx, originaly, originalHeight, originalWidth;
			originalx = (double)dataMap.get("originalx");
			originaly = (double)dataMap.get("originaly");
			originalHeight = (double)dataMap.get("height");
			originalWidth = (double)dataMap.get("width");


			//Get our near dupe and save it's docID (Work around that we can possibly ignore once we can actually get an exact dupe)
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_Analytics_NearDupeTab().Displayed()  ;}}), Input.wait30); 
			docView.getDocView_Analytics_NearDupeTab().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getNearDupeDocID().Displayed()  ;}}), Input.wait30); 
			String docID = docView.getNearDupeDocID().getText();
			dataMap.put("docid", docID);

			select_docview_doc_(scriptState, dataMap);

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 

						
			//Coordinates and Dimensions of Duped Redaction
			double dupex = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(0).getAttribute("x"));
			double dupey = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(0).getAttribute("y"));
			double dupeWidth = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(0).getAttribute("width"));
			double dupeHeight = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(0).getAttribute("height"));
			
			//Click into All History Tab
			docView.getDocumentHistoryTab().click();
			docView.getViewAllHistoryButton().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getViewAllHistoryColumnHeaders().FindWebElements().size()!=0  ;}}), Input.wait30); 


			//Click TimeStamp column once, wait for it to register and click again to sort by most recent (descending)
			docView.getViewAllHistoryColumnHeaders().FindWebElements().get(3).click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getViewAllHistoryColumnHeaders().FindWebElements().get(3).getAttribute("aria-sort").equalsIgnoreCase("ascending")  ;}}), Input.wait30); 
			docView.getViewAllHistoryColumnHeaders().FindWebElements().get(3).click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getViewAllHistoryColumnHeaders().FindWebElements().get(3).getAttribute("aria-sort").equalsIgnoreCase("descending")  ;}}), Input.wait30); 
			

			String redactionHistory = docView.getViewAllHistoryRows().FindWebElements().get(1).findElements(By.cssSelector(" td")).get(0).getText();
			
			//Make Sure Dimensions and Coordinates of Redactions are Equal across the dupes, and also make sure the Dupe's history is recorded as redactionTagged
			Assert.assertEquals(originalx, dupex);
			Assert.assertEquals(originaly, dupey);
			Assert.assertEquals(originalHeight, dupeWidth);
			Assert.assertEquals(originalWidth, dupeHeight);
			Assert.assertEquals(redactionHistory, "RedactionTagged");
			
			//Delete redaction finally
			rectangle_redaction_deleted(scriptState, dataMap);
			pass(dataMap, "verified redaction propagation in exact dupe");
		}
		else fail(dataMap, "failed to verify redaction propagation in exact dupe");

	}


	@And("^.*(\\[Not\\] )? login_as_$")
	public void login_as_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Enter Username and password for impersonated user
			//* User is logged in
			//* Sightline Home page is displayed
			//
			base = new BaseClass(driver);
			String desiredRole = (String)dataMap.get("impersonate");
			String desiredDomain = (String)dataMap.get("domain");
			String desiredProject = (String)dataMap.get("project");
			String desiredSG =  (String)dataMap.get("security_group");
 
			dataMap.put("uid", "automate.sqa2@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			login_as_pau(scriptState, dataMap);

			base.getSignoutMenu().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getCurrentRole().Displayed()  ;}}), Input.wait30); 

			//if we are not currently logged in to our current role, impersonate to it
			if(!desiredRole.equalsIgnoreCase(base.getCurrentRole().getText())) {
				
				base.getChangeRole().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					base.getSelectRole().Displayed()  ;}}), Input.wait30); 
				base.getSelectRole().click();
				base.getSelectRole().selectFromDropdown().selectByVisibleText(desiredRole);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					base.getSelectRole().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredRole)  ;}}), Input.wait30); 

				
				if(desiredRole.equalsIgnoreCase("Project Administrator")){
					base.getAvlDomain().click();
					base.getAvlDomain().selectFromDropdown().selectByVisibleText(desiredDomain);
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						base.getAvlDomain().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredDomain)  ;}}), Input.wait30); 

					base.getAvlProject().click();
					base.getAvlProject().selectFromDropdown().selectByVisibleText(desiredProject);
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						base.getAvlProject().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredProject)  ;}}), Input.wait30); 
				}
				else if(desiredRole.equalsIgnoreCase("Domain Administrator")){
					base.getAvlDomain().click();
					base.getAvlDomain().selectFromDropdown().selectByVisibleText(desiredDomain);
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						base.getAvlDomain().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredDomain)  ;}}), Input.wait30); 

				}
				else if(desiredRole.equalsIgnoreCase("Review Manager")) {
					base.getAvlDomain().click();
					base.getAvlDomain().selectFromDropdown().selectByVisibleText(desiredDomain);
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						base.getAvlDomain().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredDomain)  ;}}), Input.wait30); 

					base.getAvlProject().click();
					base.getAvlProject().selectFromDropdown().selectByVisibleText(desiredProject);
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						base.getAvlProject().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredProject)  ;}}), Input.wait30); 
					
					base.getAvlSecurity().click();
					base.getAvlSecurity().selectFromDropdown().selectByVisibleText(desiredSG);
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						base.getAvlSecurity().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredSG)  ;}}), Input.wait30); 
				}
				else fail(dataMap, "Invalid role selection");
				
				base.getSaveChangeRole().click();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					base.getSignoutMenu().Displayed() && base.getSignoutMenu().Enabled()  ;}}), Input.wait30); 

			}
			else base.getSignoutMenu().click();
			
			pass(dataMap, "successfully login/switch to desired role");

		}
		else fail(dataMap, "failed to login/switch to desired role");

	}


	@And("^.*(\\[Not\\] )? change_role_to_$")
	public void change_role_to_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click user info dropdownClick 'Change Role' buttonEnter matching impersonation informationSightline Home page is displayed
			base = new BaseClass(driver);
			String desiredRole = (String)dataMap.get("impersonate");
			String desiredDomain = (String)dataMap.get("domain");
			String desiredProject = (String)dataMap.get("project");
			String desiredSG =  (String)dataMap.get("security_group");
			
			base.getSignoutMenu().click();
			base.getChangeRole().click();
			
			
			//Change to desired role
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getSelectRole().Displayed()  ;}}), Input.wait30); 
			base.getSelectRole().click();
			base.getSelectRole().selectFromDropdown().selectByVisibleText(desiredRole);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getSelectRole().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredRole)  ;}}), Input.wait30); 

			//Change domain
			base.getAvlDomain().click();
			Thread.sleep(800);
			base.getAvlDomain().selectFromDropdown().selectByVisibleText(desiredDomain);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getAvlDomain().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredDomain)  ;}}), Input.wait30); 

			//Change Project
			base.getAvlProject().click();
			Thread.sleep(800);
			base.getAvlProject().selectFromDropdown().selectByVisibleText(desiredProject);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getAvlProject().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredProject)  ;}}), Input.wait30); 
					
			//Change Security
			base.getAvlSecurity().click();
			Thread.sleep(800);
			base.getAvlSecurity().selectFromDropdown().selectByVisibleText(desiredSG);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getAvlSecurity().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(desiredSG)  ;}}), Input.wait30); 
		
			base.getSaveChangeRole().click();
			driver.waitForPageToBeReady();

			pass(dataMap, "changed roll successfully");
		}	
		else fail(dataMap, "failed to change role");

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
			click_grey_redact_tool(scriptState, dataMap);
			click_this_page_redaction_button(scriptState, dataMap);
			this_page_redaction_applied(scriptState, dataMap);
			click_grey_redact_tool(scriptState, dataMap);
		}
		else fail(dataMap, "failed to apply this page redaction");

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
			docView = new DocViewPage(driver,0);
			Actions builder = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getYellowAnnotateButton().Displayed()  ;}}), Input.wait30); 
			docView.getYellowAnnotateButton().click();
			pass(dataMap, "successfully clicked highlight tool button");
		}
		else fail(dataMap, "failed to click highlight tool button");

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
			//Click doc with matcahing docid on Doc View page
			docView = new DocViewPage(driver, 0);
			String target = (String)dataMap.get("docid");
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewTableRows().FindWebElements().size()!=0  ;}}), Input.wait30); 
			for(WebElement x: docView.getDocViewTableRows().FindWebElements()) {
				if ( (x.findElements(By.cssSelector(" td")).get(1).getText().equals(target)) ) {
					x.click();
				}
			}

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
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 

			double originalx = (double)dataMap.get("originalx");
			double originaly = (double)dataMap.get("originaly");
			double originalHeight = (double)dataMap.get("height");
			double originalWidth = (double)dataMap.get("width");
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			
			double currentX = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("x"));
			double currentY = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("y"));
			double currentWidth = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("width"));
			double currentHeight = Double.parseDouble(docView.getExistingRectangleRedactions().FindWebElements().get(size-1).getAttribute("height"));
			
			//Make sure orignal values after redaction, have persisted. i.e the redaction was applied
			Assert.assertEquals(originalx, currentX);
			Assert.assertEquals(originaly, currentY);
			Assert.assertEquals(originalWidth, currentWidth);
			Assert.assertEquals(originalHeight, currentHeight);
			
			rectangle_redaction_deleted(scriptState, dataMap);

			pass(dataMap, "Successfully verified redaction applied");

		}
		else fail(dataMap, "failed to verify redaction applied");

	}


	@When("^.*(\\[Not\\] )? click_applied_redaction$")
	public void click_applied_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click applied redaction
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			if(size == 0) {
				fail(dataMap, "no redactions to click");
				return;
			}
			Actions builder = new Actions(driver.getWebDriver());
			double originalHeight = (double)dataMap.get("height");
			double originalWidth = (double)dataMap.get("width");

			//Find our rectangle to click based on dimensions of last placed redaction
			for(WebElement x: docView.getExistingRectangleRedactions().FindWebElements()) {
				if(Double.parseDouble(x.getAttribute("width")) != originalWidth && Double.parseDouble(x.getAttribute("height")) != originalHeight) continue;
				builder.moveToElement(x).click().build().perform();
				break;
			}
		}
		else fail(dataMap, "failed to click the applied redaction");
	}

	


	@Then("^.*(\\[Not\\] )? verify_1_redaction_tag_per_redaction$")
	public void verify_1_redaction_tag_per_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4684 Verify one redaction tag for should be selected per redaction when redaction is done with all the available options for redactions
			//

			//* Only 1 redaction tag selected per redaction
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_Redactedit_selectlabel().Displayed()  ;}}), Input.wait30); 
			docView.getDocView_Redactedit_selectlabel().click();
			String ourTag = (String)dataMap.get("redactionTag");
			//Must be only 1 redaction tag that is our tag
			Assert.assertEquals(docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getAttribute("title"),ourTag);
			pass(dataMap, "verified that there is only one redaction tag");
		}
		else fail(dataMap, "failed to verify 1 redaction tag");

	}


	@And("^.*(\\[Not\\] )? apply_rectangle_highlight$")
	public void apply_rectangle_highlight(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_highlight_toolclick_rectangle_highlight_buttonrectangle_highlight_appliedclick_highlight_tool
			
			click_highlight_tool(scriptState, dataMap);
			click_rectangle_highlight_button(scriptState, dataMap);
			rectangle_highlight_applied(scriptState, dataMap);
			click_highlight_tool(scriptState, dataMap);
			pass(dataMap, "was able to apply rectangle highlight");
		}
		else fail(dataMap, "failed to apply rectangle highlight");

	}
	
	@And("^.*(\\[Not\\] )? applied rectangel highlight button") 
	public void rectangle_highlight_applied(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if(scriptState){
			Random rand = new Random();
			int x = rand.nextInt(99) + 1;
			int y = rand.nextInt(9) + 1;
			docView.highlightByRectangle(x, y, 0);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingHighlightRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 

			int size = docView.getExistingHighlightRedactions().FindWebElements().size();
			dataMap.put("originalHighlightCount", size);

			double originalx = Double.parseDouble(docView.getExistingHighlightRedactions().FindWebElements().get(size-1).getAttribute("x"));
			double originaly = Double.parseDouble(docView.getExistingHighlightRedactions().FindWebElements().get(size-1).getAttribute("y"));
			double width = Double.parseDouble(docView.getExistingHighlightRedactions().FindWebElements().get(size-1).getAttribute("width"));
			double height = Double.parseDouble(docView.getExistingHighlightRedactions().FindWebElements().get(size-1).getAttribute("height"));
			dataMap.put("highlightx", originalx);
			dataMap.put("highlighty", originaly);
			dataMap.put("highlightWidth", width);
			dataMap.put("highlightHeight", height);

			pass(dataMap, "rectangle highlight applied");
		}
		else fail(dataMap, "failed to apply rectange_highlight");
	}


	@And("^.*(\\[Not\\] )? click_rectangle_highlight_button")
	public void click_rectangle_highlight_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception{
		if(scriptState){
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getRectangleButton().FindWebElements().size()!=0  ;}}), Input.wait30); 
			for(WebElement x: docView.getRectangleButton().FindWebElements()) {
				if(x.isDisplayed() && x.isEnabled()) x.click();
			}
			
		}
		else fail(dataMap, "failed to click rectangle highlight button");
		
	}


	@And("^.*(\\[Not\\] )? reload_the_page$")
	public void reload_the_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the browser reload page
			driver.getWebDriver().navigate().refresh();
		}
		else fail(dataMap, "failed to reload the page");

	}


	@And("^.*(\\[Not\\] )? rectangle_highlight_deleted$")
	public void rectangle_highlight_deleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click highlighted rectangle
			//* Click 'Delete Selected' trashcan button
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingHighlightRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 
			int size = docView.getExistingHighlightRedactions().FindWebElements().size();
			if(size == 0) {
				fail(dataMap, "no highlights to delete");
				return;
			}
			Actions builder = new Actions(driver.getWebDriver());
			double originalHeight = (double)dataMap.get("highlightHeight");
			double originalWidth = (double)dataMap.get("highlightWidth");

			//Find our rectangle to delete based on dimensions of last placed highlight
			for(WebElement x: docView.getExistingHighlightRedactions().FindWebElements()) {
				if(Double.parseDouble(x.getAttribute("width")) != originalWidth && Double.parseDouble(x.getAttribute("height")) != originalHeight) continue;
				builder.moveToElement(x).click().build().perform();
				break;
			}
			//builder.moveToElement(docView.getExistingHighlightRedactions().FindWebElements().get(size-1)).click().build().perform();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_Annotate_DeleteIcon().Displayed()  ;}}), Input.wait30); 

			docView.getDocView_Annotate_DeleteIcon().click();
			pass(dataMap, "succesfully deleted highlight redaction");
		}
		else fail(dataMap, "was unable to delete highlight redaction");

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_highlight_deleted_in_doc_view$")
	public void verify_redaction_highlight_deleted_in_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			//TC7848 Verify the Redaction Tag & Highlighting is deleted successfully in DocView
			//
			//* Deleted Redaction & Highlight are not displayed on document
			//

			//First verify redaction rectangle deleted
			verify_redaction_deleted_in_doc_view(scriptState, dataMap);


			int beforeDeleteSize = (int)dataMap.get("originalHighlightCount");
			if(beforeDeleteSize!=1) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					docView.getExistingHighlightRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 

			}
			//In the case we are going to 1 highlight to 0 -> Just make sure no highlights are left and return pass
			else {
				Assert.assertTrue((docView.getExistingHighlightRedactions().FindWebElements().size())==0);
				pass(dataMap, "verified redaction deleted in docView");
				return;
			}
			//Otherwise go through existing redactions
			int afterDeleteSize = docView.getExistingHighlightRedactions().FindWebElements().size();
			double originalHeight = (double)dataMap.get("highlightHeight");
			double originalWidth = (double)dataMap.get("highlightWidth");
			
			//First make sure a redaction was deleted
			Assert.assertTrue(beforeDeleteSize == afterDeleteSize+1);
			
			//Go through redactions and make sure none of them were the original redaction
			for(WebElement x : docView.getExistingHighlightRedactions().FindWebElements()) {
				double tempHeight = Double.parseDouble(x.getAttribute("height"));
				double tempWidth = Double.parseDouble(x.getAttribute("width"));

				Assert.assertTrue(originalHeight!=tempHeight);
				Assert.assertTrue(originalWidth!=tempWidth);
			}
			pass(dataMap, "redaction was verfiied to be delted in doc view");

		}
		else fail(dataMap, "unable to verify highlight redaction deleted");

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_deleted_in_doc_view$")
	public void verify_redaction_deleted_in_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC7846 Verify the Redaction Tag is deleted successfully in DocView
			//
			//* Deleted redaction does not display on document
			//
			int beforeDeleteSize = (int)dataMap.get("originalRedactionCount");
			if(beforeDeleteSize!=1) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 

			}
			//In the case we are going to 1 redaction to 0 -> Just make sure no redactions are left and return pass
			else {
				Assert.assertTrue((docView.getExistingRectangleRedactions().FindWebElements().size())==0);
				pass(dataMap, "verified redaction deleted in docView");
				return;
			}
			//Otherwise go through existing redactions
			int afterDeleteSize = docView.getExistingRectangleRedactions().FindWebElements().size();
			double originalHeight = (double)dataMap.get("height");
			double originalWidth = (double)dataMap.get("width");
			
			//First make sure a redaction was deleted
			Assert.assertTrue(beforeDeleteSize == afterDeleteSize+1);
			
			//Go through redactions and make sure none of them were the original redaction
			for(WebElement x : docView.getExistingRectangleRedactions().FindWebElements()) {
				double tempHeight = Double.parseDouble(x.getAttribute("height"));
				double tempWidth = Double.parseDouble(x.getAttribute("width"));
				Assert.assertTrue(originalHeight!=tempHeight || originalWidth!=tempWidth);
			}
			pass(dataMap, "redaction was verfiied to be delted in doc view");
		}
		else fail(dataMap, "failed to verify redaction deleted in doc_view");

	}


	@And("^.*(\\[Not\\] )? apply_rectangle_redaction_$")
	public void apply_rectangle_redaction_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonrectangle_redaction_applied_{SGSame2}click_grey_redact_tool
			click_grey_redact_tool(scriptState, dataMap);
			click_rectangle_redaction_button(scriptState, dataMap);
			rectangle_redaction_applied(scriptState, dataMap);
			click_grey_redact_tool(scriptState, dataMap);
			pass(dataMap, "applied rectangle redaction");
		}
		else fail(dataMap, "failed to apply rectangle redaction");

	}


	@And("^.*(\\[Not\\] )? log_out$")
	public void log_out(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click user account dropdown
			//* Click 'Sign Out' button
			//
			base = new BaseClass(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getSignoutMenu().Displayed()  ;}}), Input.wait30); 
			base.getSignoutMenu().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getSignoutButton().Displayed()  ;}}), Input.wait30); 
			base.getSignoutButton().click();
			close_browser(scriptState, dataMap);
			pass(dataMap, "successfully logged out ");
		}
		else fail(dataMap, "failed to log out");

	}


	@When("^.*(\\[Not\\] )? apply_rectangle_redaction_without_changing_tag$")
	public void apply_rectangle_redaction_without_changing_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonrectangle_redaction_applied_default_tagclick_grey_redact_tool
			
			click_grey_redact_tool(scriptState, dataMap);
			click_rectangle_redaction_button(scriptState, dataMap);
			rectangle_redaction_applied(scriptState, dataMap);
			pass(dataMap, "was able to apply rectangle without changing the tag");
		}
		else fail(dataMap, "failed to apply rectangle without changing the tag");

	}
	
	
	@And("^.*(\\[Not\\] )? rectangle_redaction_applied_default_tag$")
	public void rectangle_redaction_applied_default_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception{
		
		if(scriptState) {
			try {
				Actions actions = new Actions(driver.getWebDriver());  
				WebElement text = docView.getCorrectSurfaceLevel();
				Random rand = new Random();
				int x = rand.nextInt(99) + 1;
				int y = rand.nextInt(9) + 1;
				int off1 = rand.nextInt(99) + 1;
				int off2 = rand.nextInt(9) + 1;
				actions.moveToElement(text, off1,off2)
				.clickAndHold()
				.moveByOffset(x, y)
				.release()
				.perform();
			}
			catch (Exception e) { e.printStackTrace();}
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				docView.getDocView_SelectReductionLabel().Displayed() ;}}), Input.wait30);
		    String defaultRedactionTagString = docView.getDocView_SelectReductionLabel().selectFromDropdown().getFirstSelectedOption().getText();

			dataMap.put("defaultValue", defaultRedactionTagString);

			//* Click 'Save' button on Redaction Tag Save Confirmation popup
			docView.getDocViewSaveRedactionButton().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getConfirmPopUp().Displayed()  ;}}), Input.wait30); 
			pass(dataMap, "successfully applied rectangle redaction with default tag");
		}
		else fail(dataMap, "failed rectangle_redaction_applied_default_tag");
	}


	@Then("^.*(\\[Not\\] )? verify_redaction_saved_with_last_applied_tag$")
	public void verify_redaction_saved_with_last_applied_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11385 Verify that when applies rectangle redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)TC11386 Verify that when applies 'This Page' redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)
			//
			//* Redaction saved with the last used tag on the document after logging in with a different user
			//
			Assert.assertEquals(((String)dataMap.get("defaultValue")), ((String)dataMap.get("tag")));
			pass(dataMap, "redaction saved with last tag");
		}
		else fail(dataMap, "failed to verify redaction saved");

	}


	@And("^.*(\\[Not\\] )? apply_this_page_redaction_$")
	public void apply_this_page_redaction_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonthis_page_redaction_applied_{SGSame2}click_grey_redact_tool
			click_grey_redact_tool(true, dataMap);
			click_rectangle_redaction_button(scriptState, dataMap);
			click_this_page_redaction_button(scriptState, dataMap);
			this_page_redaction_applied(scriptState, dataMap);
			click_grey_redact_tool(scriptState, dataMap);
		}
		else fail(dataMap, "failed to apply this page redaction");

	}


	@When("^.*(\\[Not\\] )? apply_this_page_redaction_without_changing_tag$")
	public void apply_this_page_redaction_without_changing_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonthis_page_redaction_applied_with_default_tagclick_grey_redact_tool
			click_grey_redact_tool(scriptState, dataMap);
			click_this_page_redaction_button(scriptState, dataMap);
			this_page_redaction_applied(scriptState, dataMap);
			click_grey_redact_tool(scriptState, dataMap);
			
			pass(dataMap, "passed this page redaction");
			
			
		}
		else fail(dataMap, "Failed to apply this page redaction");

	}

	public void this_page_redaction_applied_with_default_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception{
		
		if(scriptState) {
			String defaultValue = docView.getDocView_SelectReductionLabel().selectFromDropdown().getFirstSelectedOption().getText();
			dataMap.put("defaultValue", defaultValue);

			docView.getDocViewSaveRedactionButton().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getConfirmPopUp().Displayed()  ;}}), Input.wait30); 
			pass(dataMap, "applied this page redaction");

		}
		else fail(dataMap,"failed to apply this page redaction with default tag");
		
	}
	 

	@When("^.*(\\[Not\\] )? apply_rectangle_redaction_with_default_tag$")
	public void apply_rectangle_redaction_with_default_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonrectangle_redaction_applied_with_default_tagclick_grey_redact_tool
			click_grey_redact_tool(scriptState, dataMap);
			click_rectangle_redaction_button(scriptState, dataMap);
			rectangle_redaction_applied_default_tag(scriptState, dataMap);
			click_grey_redact_tool(scriptState, dataMap);
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
			String editedTag = (String)dataMap.get("afterTag"); 
			String thisTag = (String)dataMap.get("defaultValue");
			Assert.assertEquals(editedTag, thisTag);
			pass(dataMap, "edited redaction tag is latest redaction tag");
		}
		else fail(dataMap, "failed to verify edited redaction tag is latest redaction_tag");

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


	@And("^.*(\\[Not\\] )? login_as_rmu$")
	public void login_as_rmu(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:sightline_is_launchedlogin_as_rmuon_saved_search_page
			throw new ImplementationException("login_as_rmu");
		} else {
			throw new ImplementationException("NOT login_as_rmu");
		}

	}


	@When("^.*(\\[Not\\] )? place_rectangle_redaction_without_saving$")
	public void place_rectangle_redaction_without_saving(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:click_grey_redact_toolclick_rectangle_redaction_buttonPlace rectangle redaction without clicking Save on Redaction Tag Save Confirmation popup
			Random rand = new Random();
			int off1 = rand.nextInt(99) + 1;
			int off2 = rand.nextInt(9) + 1;
			click_grey_redact_tool(scriptState, dataMap);
			click_rectangle_redaction_button(scriptState, dataMap);
			try {
				Actions actions = new Actions(driver.getWebDriver());  
				WebElement text = docView.getCorrectSurfaceLevel();
				int x = rand.nextInt(99) + 1;
				int y = rand.nextInt(9) + 1;
				actions.moveToElement(text, off1,off2)
				.clickAndHold()
				.moveByOffset(x, y)
				.release()
				.perform();
			}
			catch (Exception e) {}
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				docView.getDocView_SelectReductionLabel().Displayed() ;}}), Input.wait30);

//			docView.redactbyrectangle(x, y, 0, (String)dataMap.get("tag"));
			pass(dataMap, "was able to add redation without saving ");
		}
		else fail(dataMap, "failed to add redaction without saving");

	}


	@Then("^.*(\\[Not\\] )? verify_last_saved_tag_used_for_new_redaction_after_redaction_tag_deletion$")
	public void verify_last_saved_tag_used_for_new_redaction_after_redaction_tag_deletion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11438 Verify that last applied redaction tag should be displayed automatically for the propagated document
			//
			//* Default redaction tag applied by default redaction applied on the propagated document
			//* Selected Redaction Tag is 'SGSame1' on Redaction Tag Save Confirmation popup
			//* Delete applied redaction
			//
			Assert.assertEquals((String)dataMap.get("firstTag"), docView.getDocView_SelectReductionLabel().selectFromDropdown().getFirstSelectedOption().getText());
			
			pass(dataMap, "verified last saved tag");
		}
		else fail(dataMap, "failed to verify last saved tag");

	}


	@Then("^.*(\\[Not\\] )? verify_default_redaction_tag_selected_for_2nd_user$")
	public void verify_default_redaction_tag_selected_for_2nd_user(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11436 Verify that when two different users applies redaction to same document then default selection of redaction tag should be as per users session
			//
			//* When user 1 places redaction with tag B then the default redaction tag will be placed when user 2 places a redaction tag on the same document
			//* Select Redaction Tag is 'SGSame1' when 2nd user places a redaction
			//* Delete applied redactions
			//
			throw new ImplementationException("verify_default_redaction_tag_selected_for_2nd_user");
		} else {
			throw new ImplementationException("NOT verify_default_redaction_tag_selected_for_2nd_user");
		}

	}


	@And("^.*(\\[Not\\] )? add_temp_redaction_tag$")
	public void add_temp_redaction_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click MANAGE > Redaction Tags navigation menu buttonClick 'All Redaction Tags' tag folderClick 'New' from Action dropdownEnter 'TempRedaction' tagClick Save
			
			BaseClass base = new BaseClass(driver);
			RedactionPage redact = new RedactionPage(driver, 0);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getManageMenuButton().Displayed()  ;}}), Input.wait30); 
			base.getManageMenuButton().click();
	
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getManageRedactionButton().Displayed()  ;}}), Input.wait30); 
			base.getManageRedactionButton().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getAllRedactionRootNode().Displayed()  ;}}), Input.wait30); 
			redact.getAllRedactionRootNode().click();
			
			String defaultRedactionTag = redact.getredactiontags().FindWebElements().get(1).getAttribute("data-content");
			dataMap.put("defaultRedactionTag", defaultRedactionTag);


			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getactionDropDown().Displayed()  ;}}), Input.wait30); 
			redact.getactionDropDown().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getAddRedactionTag().Displayed()  ;}}), Input.wait30); 
			redact.getAddRedactionTag().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getRedactionEnterNewName().Displayed()  ;}}), Input.wait30); 
			redact.getRedactionEnterNewName().click();
			redact.getRedactionEnterNewName().SendKeys((String)dataMap.get("tag"));

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getSaveBtn().Displayed()  ;}}), Input.wait30); 
			redact.getSaveBtn().click();


			pass(dataMap, "succesfully added a temp redaction tag");
		}
		else fail(dataMap, "failed to add temp redaction tag");

	}


	@And("^.*(\\[Not\\] )? delete_temp_redaction_tag$")
	public void delete_temp_redaction_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click MANAGE > Redaction Tags navigation menu buttonClick 'TempRedaction' tagClick 'Delete' from Action dropdown
			BaseClass base = new BaseClass(driver);
			RedactionPage redact = new RedactionPage(driver, 0);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getManageMenuButton().Displayed()  ;}}), Input.wait30); 
			base.getManageMenuButton().click();
	
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				base.getManageRedactionButton().Displayed()  ;}}), Input.wait30); 
			base.getManageRedactionButton().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getAllRedactionRootNode().Displayed()  ;}}), Input.wait30); 

			redact.getAllRedactionRootNode().click();

			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(redact.getRedactionTagByName((String)dataMap.get("tag")).getWebElement()).click().build().perform();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getactionDropDown().Displayed()  ;}}), Input.wait30); 
			redact.getactionDropDown().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getRedactionDelete().Displayed()  ;}}), Input.wait30); 
			redact.getRedactionDelete().click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				redact.getConfirmDeleteRedactionYesButton().Displayed()  ;}}), Input.wait30); 
			redact.getConfirmDeleteRedactionYesButton().click();
			
			driver.waitForPageToBeReady();

			
			pass(dataMap, "temp redaction was deleted");

		}
		else fail(dataMap, "failed to delete temp redaction");
			

	}


	@When("^.*(\\[Not\\] )? click_previously_placed_redaction$")
	public void click_previously_placed_redaction(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			
			Actions builder = new Actions(driver.driver);
			builder.moveToElement(docView.getExistingRectangleRedactions().FindWebElements().get(size-1)).click().build().perform();
			pass(dataMap, "clicked previous redaction");
		}
		else fail(dataMap, "Failed to click previous redaction");

	}


	@Then("^.*(\\[Not\\] )? verify_default_tag_selected_for_existing_redaction_after_redaction_tag_deletion$")
	public void verify_default_tag_selected_for_existing_redaction_after_redaction_tag_deletion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11435 Verify that after deletion of the last saved redaction tag, 'Default Redaction Tag' should be selected automatically from redaction pop up
			//
			//* After redactions with Tag B and Tag C are applied, when Tag C is deleted from the Security Group then Tag A is automatically selected from the redaction popup for the redaction that formerly used Tag C
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_Redactedit_selectlabel().Displayed()  ;}}), Input.wait30); 

			Assert.assertEquals((String)dataMap.get("defaultRedactionTag"), docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getAttribute("title"));
			pass(dataMap, "verified default tag exists");
		}
		else fail(dataMap, "Failed to verify default tag selected");

	}


	@Then("^.*(\\[Not\\] )? verify_edited_redaction_displayed_for_diff_sg_with_same_annotation_layer$")
	public void verify_edited_redaction_displayed_for_diff_sg_with_same_annotation_layer(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5753 Verify that after moving the redaction should be saved for the document when same annotation layer is mapped to different security groups
			//
			//* Edited redaction displayed for another user in a different Security Group with the same Annotation Layer
			//
			System.out.println((String)dataMap.get("tag"));
			System.out.println((String)dataMap.get("beforeTag"));
			Assert.assertEquals((String)dataMap.get("tag"), (String)dataMap.get("beforeTag"));
			pass(dataMap, "verified edited redaction for diff sg with same annotation layer");
		}
		else fail(dataMap, "could not verify edited redation for diff sg with same annotation layer");

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_displayed_for_diff_sg_with_same_annotation_layer$")
	public void verify_redaction_displayed_for_diff_sg_with_same_annotation_layer(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5746 Verify that redaction should be saved for the document when same annotation layer is mapped to different security groupsTC3203 Verify RMU/Reviewer can see the redaction, redaction tags of document on doc view page in different security group if the same annotation layer, redaction tag is mapped to different security groups
			//
			//* Redaction displayed for another user in a different Security Group with the same Annotation Layer
			//
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0 ;}}), Input.wait30); 

			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			if(size == 0) {
				fail(dataMap, "no redactions to delete");
				return;
			}
			Actions builder = new Actions(driver.getWebDriver());
			double originalHeight = (double)dataMap.get("height");
			double originalWidth = (double)dataMap.get("width");

			//Find our rectangle to delete based on dimensions of last placed highlight
			for(WebElement x: docView.getExistingRectangleRedactions().FindWebElements()) {
				if(Double.parseDouble(x.getAttribute("width")) != originalWidth && Double.parseDouble(x.getAttribute("height")) != originalHeight) continue;
				builder.moveToElement(x).click().build().perform();
				break;
			}
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocView_Redactedit_selectlabel().Displayed() ;}}), Input.wait30); 
			
			//Make sure our OG tag has persisted after we've changed SG's
			Assert.assertEquals((String)dataMap.get("tag"), docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getAttribute("title"));
			
			pass(dataMap, "was able to verify redaction for diff sg with same annotation layer");

		}
		else fail(dataMap, "Failed to verify_redaction displayed for diff sg with same annotation layer");

	}


	@Then("^.*(\\[Not\\] )? verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags$")
	public void verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6594 Verify that when two different users under different security group with different annotation layer adds/edit/delete redactions to the same record successfully
			//
			//* Both users can Add/Edit/Delete a redaction on the same document
			//* This message is NOT displayed for the 2nd user "Another user has applied redactions, annotations or Reviewer Remarks to this document since you presented it in Doc View. You may not apply markup because that would overwrite the mark upwork done by the other user. Please reload the document."
			//
			throw new ImplementationException("verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags");
		} else {
			throw new ImplementationException("NOT verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags");
		}

	}


	@And("^.*(\\[Not\\] )? login_to_saved_search_rmu_private_browser$")
	public void login_to_saved_search_rmu_private_browser(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Use a different userThis is a collection of the following steps:sightline_is_launchedlogin_as_rmuon_saved_search_page
			dataMap.put("originalWindow", driver.CurrentWindowHandle());
			dataMap.put("originalDriver", driver);

			sightline_is_launched(scriptState, dataMap);
			login(scriptState, dataMap);
			change_role_to_(scriptState, dataMap);
			on_saved_search_page(scriptState, dataMap);

			dataMap.put("secondWindow", driver.CurrentWindowHandle());
			dataMap.put("secondDriver", driver);
		}

	}


	@Then("^.*(\\[Not\\] )? verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags$")
	public void verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6591 Verify that when two different users under different security group sharing annotation layer adds/edit/delete redactions to the same record successfully, and confirm that the XML nodes are all properly created/reflected in the XML
			//
			//* 1st user can Add/Edit/Delete a redaction
			//* 2nd user Highlight submenu buttons are disabled
			//* 2nd user receives the following error message "Another user has applied redactions, annotations or Reviewer Remarks to this document since you presented it in Doc View. You may not apply markup because that would overwrite the mark upwork done by the other user. Please reload the document."
			//

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDuplicateHighlightWarning().Displayed() ;}}), Input.wait30); 
			Assert.assertEquals("Another user has applied redactions, annotations or Reviewer Remarks to this document since you presented it in DocView.  You may not apply markup – because that would overwrite the work done by the other user.  Please reload the document.",
					docView.getDuplicateHighlightWarning().getText());
					
			docView.getDocView_Annotate_Rectangle().click();
			driver.waitForPageToBeReady();
			Assert.assertFalse(docView.getDocView_Annotate_Rectangle().GetCssValue("color").equals("rgb(230, 70, 52)"));
			
			docView.getDocView_Annotate_ThisPage().click();
			driver.waitForPageToBeReady();
			Assert.assertFalse(docView.getDocView_Annotate_ThisPage().GetCssValue("color").equals("rgb(230, 70, 52)"));
			
			pass(dataMap, "Was able to verify highlight error with diff sg and same annotation layer");
		}
		else fail(dataMap, "failed to verify highlight error with diff sg");

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags$")
	public void verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6591 Verify that when two different users under different security group sharing annotation layer adds/edit/delete redactions to the same record successfully, and confirm that the XML nodes are all properly created/reflected in the XML
			//
			//* 1st user can Add/Edit/Delete a redaction
			//* 2nd user Redaction submenu 'Rectangle', 'This Page', and 'Text Redaction' buttons are disabled
			//* 2nd user receives the following error message "Another user has applied redactions, annotations or Reviewer Remarks to this document since you presented it in Doc View. You may not apply markup because that would overwrite the mark upwork done by the other user. Please reload the document."
			//
			Assert.assertEquals("Another user has applied redactions, annotations or Reviewer Remarks to this document since you presented it in DocView.  You may not apply markup – because that would overwrite the work done by the other user.  Please reload the document.",
					docView.getDuplicateRedactionWarning().getText());

			docView.getRectangleRedactionButton().click();
			driver.waitForPageToBeReady();
			Assert.assertFalse(docView.getRectangleRedactionButton().GetCssValue("color").equals("rgb(230, 70, 52)"));
			
			docView.getThisPageRedactionButton().click();
			driver.waitForPageToBeReady();
			Assert.assertFalse(docView.getThisPageRedactionButton().GetCssValue("color").equals("rgb(230, 70, 52)"));
			
			docView.getTextRedactionButton().click();
			driver.waitForPageToBeReady();
			Assert.assertFalse(docView.getTextRedactionButton().GetCssValue("color").equals("rgb(230, 70, 52)"));

			pass(dataMap, "Verified error with diff sg same annotation layer");
		}
		else fail(dataMap, "failed to verify redaction error with diff sg same annotation layer");

	}
	
	public void verify_remarks_error_with_diff_sg_same_annotation_layer_same_redaction_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception{
		if(scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getRemarksWarning().Displayed()	;}}), Input.wait30); 
			Assert.assertFalse(docView.getAddRemarkbtn().GetCssValue("color").equals("rgb(230, 70, 52)"));
			Assert.assertTrue(docView.getRemarksWarning().getText().contains("Another user has applied redactions, annotations or Reviewer Remarks to this document since you presented it in DocView."));
			pass(dataMap, "verfied remarks error with diff sg same annotation layer");
		}
		else fail(dataMap, "was unable to verify remarks error with diff sg same annotation layer");
	}


	@Then("^.*(\\[Not\\] )? verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags$")
	public void verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6591 Verify that when two different users under different security group sharing annotation layer adds/edit/delete redactions to the same record successfully, and confirm that the XML nodes are all properly created/reflected in the XML
			//
			//* Redaction from 1st user is displayed for the 2nd user after reloading the document
			//
			driver.getWebDriver().navigate().refresh();
			driver.waitForPageToBeReady();
			Actions builder = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				!docView.getDocViewTotalPages().getText().equals("")  ;}}), Input.wait30); 
			String pageNumString = docView.getDocViewTotalPages().getText();
			int totalPages = Integer.parseInt((pageNumString.split("of "))[1].split(" pages")[0]);

			//Process of zooming out and scrolling through pages until we get into the view of a redaction to edit
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getMagnifyGlassZoomOutButton().Displayed()	;}}), Input.wait30); 
			for(int i =0; i<5;i++) docView.getMagnifyGlassZoomOutButton().click();
			
			for(int j=0; j<totalPages; j++) {
				if(docView.getExistingRectangleRedactions().FindWebElements().size()>0) break;
				docView.getNextRedactionPage().click();
				Thread.sleep(3000);
			}

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 

			double originalx = (double)dataMap.get("originalx");
			double originaly = (double)dataMap.get("originaly");
			double originalHeight = (double)dataMap.get("height");
			double originalWidth = (double)dataMap.get("width");
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			
			boolean passed;
			if(dataMap.get("delete")!=null && (boolean)dataMap.get("delete") == true){
				passed = true;
				for(WebElement x: docView.getExistingRectangleRedactions().FindWebElements()) {
					//Out of all redactions, we shouldnt find a perfect match if we've deletd
					if(Double.parseDouble(x.getAttribute("width")) != originalWidth || Double.parseDouble(x.getAttribute("height")) != originalHeight
							|| Double.parseDouble(x.getAttribute("x"))!= originalx || Double.parseDouble(x.getAttribute("y")) !=originaly) continue;
					else passed = false;
				}
			}
			else {
				passed = false;
				for(WebElement x: docView.getExistingRectangleRedactions().FindWebElements()) {

					if(Double.parseDouble(x.getAttribute("width")) != originalWidth && Double.parseDouble(x.getAttribute("height")) != originalHeight
							&& Double.parseDouble(x.getAttribute("x"))!=originalx && Double.parseDouble(x.getAttribute("y"))!=originaly ) continue;
					else {
						builder.moveToElement(x).click().build().perform();
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								docView.getDocView_Redactedit_selectlabel().Displayed()	;}}), Input.wait30); 
						//Assert Tags here
						String currTag = docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getAttribute("title");
						String tag2Compare = "";
						if((String)dataMap.get("afterTag") == null) tag2Compare = (String)dataMap.get("tag");
						else tag2Compare = (String)dataMap.get("afterTag");
						Assert.assertEquals(currTag, tag2Compare);
						passed = true;
					}
				}
			}
			if(passed) pass(dataMap, "was able to find/not find a redaction");
			else fail(dataMap, "failed to find/not find a redaction");

		}
		else fail(dataMap, "failed to verify the last redaction");

	}


	@Then("^.*(\\[Not\\] )? verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs$")
	public void verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11440 Verify the automatically selected redaction tag when shared annotation layer with un-shared redaction tags in security groups and all documents are released to security groups
			//
			//* Automatically set Select Redaction Tag for 2nd user on propagated document is 'Default Redaction Tag' for different Security Group with same Annotation Layer different Redaction Tags and Released documents
			//* Delete all applied redactions for this test
			//
			throw new ImplementationException("verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs");
		} else {
			throw new ImplementationException("NOT verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs");
		}

	}


	@When("^.*(\\[Not\\] )? place_this_page_redaction_without_saving$")
	public void place_this_page_redaction_without_saving(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			click_grey_redact_tool(true, dataMap);
			click_this_page_redaction_button(scriptState, dataMap);
			pass(dataMap, "was able to place this page redaction without saving");
		}
		else fail(dataMap, "failed to place this page redaction without saving");

	}


	@Then("^.*(\\[Not\\] )? verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs$")
	public void verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11440 Verify the automatically selected redaction tag when shared annotation layer with un-shared redaction tags in security groups and all documents are released to security groups
			//
			//* Automatically set Select Redaction Tag for 2nd user is 'Default Redaction Tag' for different Security Group with same Annotation Layer different Redaction Tags and Released documents.
			//* Delete all applied redactions for this test
			//
			throw new ImplementationException("verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs");
		} else {
			throw new ImplementationException("NOT verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs$")
	public void verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11439 Verify the automatically selected redaction tag when shared annotation layer with shared redaction tags in security groups and all documents are released to security groups
			//
			//* Automatically set Select Redaction Tag for 2nd user on propagated document matches the tag used by the 1st user in different Security Group with same Annotation Layer same Redaction Tags and Released documents.
			//* 2nd Select Redaction Tag is 'SGSame1'
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				docView.getDocView_SelectReductionLabel().Displayed() ;}}), Input.wait30);
			Assert.assertEquals(docView.getDocView_SelectReductionLabel().selectFromDropdown().getFirstSelectedOption().getText(), (String)dataMap.get("tag"));

		}

	}


	@Then("^.*(\\[Not\\] )? verify_auto_selected_tag_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs$")
	public void verify_auto_selected_tag_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11439 Verify the automatically selected redaction tag when shared annotation layer with shared redaction tags in security groups and all documents are released to security groups
			//
			//* Automatically set Select Redaction Tag for 2nd user matches the tag used by the 1st user in different Security Group with same Annotation Layer same Redaction Tags and Released documents.
			//* 2nd Select Redaction Tag is 'SGSame1'
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				docView.getDocView_SelectReductionLabel().Displayed() ;}}), Input.wait30);
			Assert.assertEquals(docView.getDocView_SelectReductionLabel().selectFromDropdown().getFirstSelectedOption().getText(), (String)dataMap.get("tag"));
		}

	}


	@When("^.*(\\[Not\\] )? on_audio_doc_with_redactions$")
	public void on_audio_doc_with_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select Audio doc ID00000002
			throw new ImplementationException("on_audio_doc_with_redactions");
		} else {
			throw new ImplementationException("NOT on_audio_doc_with_redactions");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_audio_redaction_not_displayed_from_different_security_group$")
	public void verify_audio_redaction_not_displayed_from_different_security_group(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC3484 Verify user can not see the redactions of audio file on doc view page in different security group when different annotation layer is mapped to different security groups
			//
			//* SG2 user cannot see SG1 redactions on the same document
			//
			throw new ImplementationException("verify_audio_redaction_not_displayed_from_different_security_group");
		} else {
			throw new ImplementationException("NOT verify_audio_redaction_not_displayed_from_different_security_group");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_message_to_reload_same_document$")
	public void verify_message_to_reload_same_document(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6687 Verify when two different users in the same project & Security Group, and are applying redactions to the same DocID at the same team
			//
			//* Message is displayed: "Another user has applied redactions, annotations, or Reviewer Remarks to this document since you presented it in DocView. You may not apply markup - because that would overwrite the work done by the other user. Please reload the document."
			//* Redaction cannot be placed until the document is reloaded
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getGeneralDuplicateWarning().FindWebElements().size()!=0 ;}}), Input.wait30); 
			for(WebElement x: docView.getGeneralDuplicateWarning().FindWebElements()) {
				if(x.isDisplayed()) {
					Assert.assertEquals("Another user has applied redactions, annotations or Reviewer Remarks to this document since you presented it in DocView.  You may not apply markup – because that would overwrite the work done by the other user.  Please reload the document.",
							x.getText());
				}
			}
			pass(dataMap, "was able to verify errorMessage"); 

		}
		else fail(dataMap, "failed to verify error message");

	}


	@Then("^.*(\\[Not\\] )? verify_doc_view_analytics_panel_unchanged$")
	public void verify_doc_view_analytics_panel_unchanged(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5377 Verify that on Saving document or Redactions or Annotations or Reviewer Remarks should not affect the displayed tab of the Analytics Panel
			//
			//* Analytics Panel remains unchanged after a Redaction, Annotation, or Reviewer Remark is added
			//* Delete Redaction, Annotation, or Reviewer Remark after verification
			//
			ArrayList<String> threadedColumns = (ArrayList<String>)dataMap.get("threadedColumns");
			ArrayList<String> familyRows = (ArrayList<String>)dataMap.get("familyRows");
			ArrayList<String> nearDupeRows = (ArrayList<String>)dataMap.get("nearDupeRows");
			ArrayList<String> conceptuallySimilarRows = (ArrayList<String>)dataMap.get("conceptuallySimilarRows");
			int i =0;

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			docView.getThreadDocumentsTab().Displayed()  ;}}), Input.wait30); 
			docView.getThreadDocumentsTab().click();
			driver.waitForPageToBeReady();
			Assert.assertEquals(threadedColumns.size(), docView.getThreadedDocumentsColumnHeaders().FindWebElements().size());
			for(WebElement x: docView.getThreadedDocumentsColumnHeaders().FindWebElements()) Assert.assertEquals(x.getText(), threadedColumns.get(i++));
		
			i =0;
			docView.getDocView_Analytics_FamilyTab().click();
			driver.waitForPageToBeReady();
			Assert.assertEquals(familyRows.size(), docView.getFamilyTabTableRows().FindWebElements().size());
			for(WebElement x: docView.getFamilyTabTableRows().FindWebElements()) {
				if(x.findElements(By.tagName("td")).size()<2) {
					Assert.assertEquals(familyRows.get(i), x.findElements(By.tagName("td")).get(0).getText());
					break;
				}
				Assert.assertEquals(x.findElements(By.tagName("td")).get(1).getText(), familyRows.get(i++));
			}
		
			i = 0;
			docView.getDocView_Analytics_NearDupeTab().click();
			driver.waitForPageToBeReady();
			Assert.assertEquals(nearDupeRows.size(), docView.getNearDupeRows().FindWebElements().size());
			for(WebElement x: docView.getNearDupeRows().FindWebElements()) {
				if(x.findElements(By.tagName("td")).size()<2) {
					Assert.assertEquals(nearDupeRows.get(i), x.findElements(By.tagName("td")).get(0).getText());
					break;
				}
				Assert.assertEquals(x.findElements(By.tagName("td")).get(1).getText(), nearDupeRows.get(i++));
			}

			i = 0;
			docView.getDocView_Analytics_liDocumentConceptualSimilarab().click();
			driver.waitForPageToBeReady();
			Assert.assertEquals(conceptuallySimilarRows.size(), docView.getConceptuallySimilarRows().FindWebElements().size());
			for(WebElement x: docView.getConceptuallySimilarRows().FindWebElements()) {
				if(x.findElements(By.tagName("td")).size()<2) {
					Assert.assertEquals(conceptuallySimilarRows.get(i), x.findElements(By.tagName("td")).get(0).getText());
					break;
				}
				Assert.assertEquals(x.findElements(By.tagName("td")).get(1).getText(), conceptuallySimilarRows.get(i++));
			}

			pass(dataMap, "was able to verify doc view analytics panel unchanged");
		}
		else fail(dataMap, "was unable to verify doc view anlytics panel unchanged");

	}


	@When("^.*(\\[Not\\] )? apply_reviewer_remark$")
	public void apply_reviewer_remark(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			Actions builder = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getNonAudioRemarkBtn().Displayed()  ;}}), Input.wait30); 
			docView.getNonAudioRemarkBtn().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			builder.moveToElement(docView.getExistingRectangleRedactions().FindWebElements().get(size-1)).click().build().perform();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getAddRemarkbtn().Enabled()  ;}}), Input.wait30); 
			docView.getAddRemarkbtn().click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getRemarkTextArea().Enabled()  ;}}), Input.wait30); 
			docView.getRemarkTextArea().click();
			docView.getRemarkTextArea().SendKeys("TestRemark!");
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getSaveRemark().Enabled()  ;}}), Input.wait30); 
			docView.getSaveRemark().click();
			driver.waitForPageToBeReady();
			pass(dataMap, "applied reviewer remark");
		}
		else fail(dataMap, "Failed to apply reviewer remark");

	}


	@And("^.*(\\[Not\\] )? on_audio_doc_without_redactions$")
	public void on_audio_doc_without_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select Audio doc ID00000001
			throw new ImplementationException("on_audio_doc_without_redactions");
		} else {
			throw new ImplementationException("NOT on_audio_doc_without_redactions");
		}

	}


	@And("^.*(\\[Not\\] )? apply_audio_redaction_$")
	public void apply_audio_redaction_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Audio Redaction '+' buttonSet STARTTIME based on 'starttime' variableSet ENDTIME based on 'endtime' variableSet REDACTIONTAGS based on 'redactiontag' variableClick Save ACTIONS buttonIf variables not set, default to the following:starttime=0:00:00endtime=0:00:05redactiontag=Redaction Tag 1If starttime or endtime = 'empty' then delete the text in the STARTTIME and/or ENDTIME boxes
			throw new ImplementationException("apply_audio_redaction_");
		} else {
			throw new ImplementationException("NOT apply_audio_redaction_");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_no_audio_redaction_time_selected_error$")
	public void verify_no_audio_redaction_time_selected_error(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4816 Verify warning message should be displayed when redaction tag is not selected for redaction for audio document
			//
			//* Redaction not saved
			//* Error message displayed
			//
			throw new ImplementationException("verify_no_audio_redaction_time_selected_error");
		} else {
			throw new ImplementationException("NOT verify_no_audio_redaction_time_selected_error");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_audio_redaction_not_saved$")
	public void verify_audio_redaction_not_saved(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6465 Verify that redaction should not be saved/updated when Start Time is greater than End Time
			//TC6466 Verify that redaction should not be saved/updated when Start Time/End Time is greater than the media length
			//
			//* Redaction not saved
			//* Error message displayed
			//* Delete redactions if applied
			//
			//
			throw new ImplementationException("verify_audio_redaction_not_saved");
		} else {
			throw new ImplementationException("NOT verify_audio_redaction_not_saved");
		}

	}


	@And("^.*(\\[Not\\] )? edit_audio_redaction_$")
	public void edit_audio_redaction_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("edit_audio_redaction_");
		} else {
			throw new ImplementationException("NOT edit_audio_redaction_");
		}

	}


	@When("^.*(\\[Not\\] )? audio_redaction_attempted_save$")
	public void audio_redaction_attempted_save(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("audio_redaction_attempted_save");
		} else {
			throw new ImplementationException("NOT audio_redaction_attempted_save");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_audio_redaction_time_format$")
	public void verify_audio_redaction_time_format(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6464 Verify that redaction Start Time and End Time saved in HH:MM:SS format
			//
			//* Redaction STARTTIME and ENDTIME saved in HH:MM:SS format
			//* Delete redaction after verification
			//
			throw new ImplementationException("verify_audio_redaction_time_format");
		} else {
			throw new ImplementationException("NOT verify_audio_redaction_time_format");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user1_redaction_change_displayed_after_user2_reload$")
	public void verify_user1_redaction_change_displayed_after_user2_reload(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6442 Verify that when two different users add redactions to the same record successfully, and confirm that the UserNames are all properly appears in the XMLTC6443 Verify that when two different users edits redactions to the same record successfully, and confirm that the UserNames are all properly appears in the XMLTC6444 Verify that when two different users deletes redactions to the same record successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* Redaction apply/edit/deletion displayed after user 2 reloads the document
			//
			driver.getWebDriver().navigate().refresh();
			driver.waitForPageToBeReady();
			Actions builder = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				!docView.getDocViewTotalPages().getText().equals("")  ;}}), Input.wait30); 
			String pageNumString = docView.getDocViewTotalPages().getText();
			int totalPages = Integer.parseInt((pageNumString.split("of "))[1].split(" pages")[0]);

			//Process of zooming out and scrolling through pages until we get into the view of a redaction to edit
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getMagnifyGlassZoomOutButton().Displayed()	;}}), Input.wait30); 
			for(int i =0; i<5;i++) docView.getMagnifyGlassZoomOutButton().click();
			
			for(int j=0; j<totalPages; j++) {
				if(docView.getExistingRectangleRedactions().FindWebElements().size()>0) break;
				docView.getNextRedactionPage().click();
				driver.waitForPageToBeReady();
			}

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getExistingRectangleRedactions().FindWebElements().size()!=0  ;}}), Input.wait30); 

			double originalx = (double)dataMap.get("originalx");
			double originaly = (double)dataMap.get("originaly");
			double originalHeight = (double)dataMap.get("height");
			double originalWidth = (double)dataMap.get("width");
			int size = docView.getExistingRectangleRedactions().FindWebElements().size();
			
			boolean passed;
			if(dataMap.get("delete")!=null && (boolean)dataMap.get("delete") == true){
				passed = true;
				for(WebElement x: docView.getExistingRectangleRedactions().FindWebElements()) {
					//Out of all redactions, we shouldnt find a perfect match if we've deletd
					if(Double.parseDouble(x.getAttribute("width")) != originalWidth || Double.parseDouble(x.getAttribute("height")) != originalHeight
							|| Double.parseDouble(x.getAttribute("x"))!= originalx || Double.parseDouble(x.getAttribute("y")) !=originaly) continue;
					else passed = false;
				}
			}
			else {
				passed = false;
				for(WebElement x: docView.getExistingRectangleRedactions().FindWebElements()) {
					if(x.isDisplayed()) {
					double currDimension = Double.parseDouble(x.getAttribute("width"))
								* Double.parseDouble(x.getAttribute("height"));
					
					if((Double)dataMap.get("afterEditDimension")!=null && currDimension== (Double)dataMap.get("afterEditDimension")) {
						builder.moveToElement(x).click().build().perform();
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								docView.getDocView_Redactedit_selectlabel().Displayed()	;}}), Input.wait30); 
						//Assert Tags here
						String currTag = docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getAttribute("title");
						String tag2Compare = "";
						if((String)dataMap.get("afterTag") == null) tag2Compare = (String)dataMap.get("tag");
						else tag2Compare = (String)dataMap.get("afterTag");
						Assert.assertEquals(currTag, tag2Compare);
						passed = true;
						break;

					}
					else if(Double.parseDouble(x.getAttribute("width")) != originalWidth || Double.parseDouble(x.getAttribute("height")) != originalHeight
							|| Double.parseDouble(x.getAttribute("x"))!=originalx || Double.parseDouble(x.getAttribute("y"))!=originaly ) continue;
					else {
						builder.moveToElement(x).click().build().perform();
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								docView.getDocView_Redactedit_selectlabel().Displayed()	;}}), Input.wait30); 
						//Assert Tags here
						String currTag = docView.getDocView_Redactedit_selectlabel().selectFromDropdown().getFirstSelectedOption().getAttribute("title");
						String tag2Compare = "";
						if((String)dataMap.get("afterTag") == null) tag2Compare = (String)dataMap.get("tag");
						else tag2Compare = (String)dataMap.get("afterTag");
						Assert.assertEquals(currTag, tag2Compare);
						passed = true;
						break;
					}
				}
				}
			}
			if(passed) pass(dataMap, "was able to find/not find a redaction");
			else fail(dataMap, "failed to find/not find a redaction");

			pass(dataMap, "was able to verify user1 redaciton change displayed after user2 reload");
		}
		else fail(dataMap, "failed to verify user1 redaction change displayed after user2");

	}


	@When("^.*(\\[Not\\] )? query_annotation_layer_in_db$")
	public void query_annotation_layer_in_db(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("query_annotation_layer_in_db");
		} else {
			throw new ImplementationException("NOT query_annotation_layer_in_db");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_username_in_db$")
	public void verify_redaction_username_in_db(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6442 Verify that when two different users add redactions to the same record successfully, and confirm that the UserNames are all properly appears in the XMLTC6443 Verify that when two different users edits redactions to the same record successfully, and confirm that the UserNames are all properly appears in the XMLTC6444 Verify that when two different users deletes redactions to the same record successfully, and confirm that the UserNames are all properly appears in the XML
			//
			//* 'userName' value in the most recent Annotation Layer column value matches the user that applied the redaction/annotation/reviewer remark
			//
			throw new ImplementationException("verify_redaction_username_in_db");
		} else {
			throw new ImplementationException("NOT verify_redaction_username_in_db");
		}
	}

	public void click_remark_button(boolean scriptState, HashMap dataMap) {
		
		if(scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getNonAudioRemarkBtn().Displayed()  ;}}), Input.wait30); 
			docView.getNonAudioRemarkBtn().click();
		}
	}


	public void get_analytics_data_prestate(boolean b, HashMap dataMap) {

		ArrayList<String> threadedColumns = new ArrayList<>();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			docView.getThreadDocumentsTab().Displayed()  ;}}), Input.wait30); 
		docView.getThreadDocumentsTab().click();
		driver.waitForPageToBeReady();
		for(WebElement x: docView.getThreadedDocumentsColumnHeaders().FindWebElements()) threadedColumns.add(x.getText());
		
		ArrayList<String> familyRows = new ArrayList<>();
		docView.getDocView_Analytics_FamilyTab().click();
		driver.waitForPageToBeReady();
		for(WebElement x: docView.getFamilyTabTableRows().FindWebElements()) {
			if(x.findElements(By.tagName("td")).size()<2) {
				familyRows.add(x.findElements(By.tagName("td")).get(0).getText());
				break;
			}
			familyRows.add(x.findElements(By.tagName("td")).get(1).getText());
		}
		
		ArrayList<String> nearDupeRows = new ArrayList<>();
		docView.getDocView_Analytics_NearDupeTab().click();
		driver.waitForPageToBeReady();
		for(WebElement x: docView.getNearDupeRows().FindWebElements()) {
			if(x.findElements(By.tagName("td")).size()<2) {
				nearDupeRows.add(x.findElements(By.tagName("td")).get(0).getText());
				break;
			}
			nearDupeRows.add(x.findElements(By.tagName("td")).get(1).getText());
		}

		ArrayList<String> conceptuallySimilarRows = new ArrayList<>();
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().click();
		driver.waitForPageToBeReady();
		for(WebElement x: docView.getConceptuallySimilarRows().FindWebElements()) {
			if(x.findElements(By.tagName("td")).size()<2) {
				conceptuallySimilarRows.add(x.findElements(By.tagName("td")).get(0).getText());
				break;
			}
			conceptuallySimilarRows.add(x.findElements(By.tagName("td")).get(1).getText());
		}
		
		dataMap.put("threadedColumns", threadedColumns);
		dataMap.put("familyRows", familyRows);
		dataMap.put("nearDupeRows", nearDupeRows);
		dataMap.put("conceptuallySimilarRows", conceptuallySimilarRows);

		
	}
}//eof
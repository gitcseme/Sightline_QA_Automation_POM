package stepDef;

import static org.testng.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;  

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import automationLibrary.Driver;
import automationLibrary.Element;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import testScriptsSmoke.Input;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import junit.framework.Assert;

@SuppressWarnings({"deprecation", "rawtypes" })
public class IngestionContext extends CommonContext {
	
	/* 
	 * moved to CommonContext
	 * 
	public void sightline_is_launched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void login_as_pau(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void on_ingestion_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

	 */
	 JavascriptExecutor js = (JavascriptExecutor)driver; 
    
    
	@And("^.*(\\[Not\\] )? add_a_new_ingestion_btn_is_clicked$")
	public void add_a_new_ingestion_btn_is_clicked(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			ingest.getAddanewIngestionButton().Click();
			driver.waitForPageToBeReady();
		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getAddanewIngestionButton().Visible()  ;}}), Input.wait30); 
		}
	}

	@And("^.*(\\[Not\\] )? new_ingestion_created$")
	public void new_ingestion_created(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getAddanewIngestionButton().Visible()  ;}}), Input.wait30);
			ingest.getAddanewIngestionButton().Click();
			driver.waitForPageToBeReady();
			ingest.requiredFieldsAreEntered(scriptState, dataMap);
			click_next_button(scriptState, dataMap);
		} else {
			ingest.requiredFieldsAreEntered(scriptState, dataMap);
		}
	}


	@When("^.*(\\[Not\\] )? click_run_ingest_button$")
	public void click_run_ingest_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getbtnRunIngestion().Visible()  ;}}), Input.wait30);
			ingest.getbtnRunIngestion().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionTile().Displayed()  ;}}), Input.wait30); 
			
			pass(dataMap,"Clicking Ingest Button was successful");
		} else {
			ingest.getPreviewRun().Click();
			fail(dataMap,"Clicking Ingest Button was unsuccessful");

		}

	}

	@And("^.*(\\[Not\\] )? required_fields_are_entered$")
	public void required_fields_are_entered(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getSpecifySourceSystem().Visible()  ;}}), Input.wait30);
			ingest.requiredFieldsAreEntered(scriptState, dataMap);

		} else {
			ingest.requiredFieldsAreEntered(scriptState, dataMap);
		}

	}


	@When("^.*(\\[Not\\] )? click_next_button$")
	public void click_next_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.FindElementByTagName("body").SendKeys(Keys.HOME.toString());
			Thread.sleep(2000);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getNextButton().Displayed()  ;}}), Input.wait30); 
	    	ingest.getNextButton().Click();
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    	ingest.getApproveMessageOKButton().Visible() ;}}), Input.wait30); 
			Thread.sleep(2000);
	    	ingest.getApproveMessageOKButton().Click(); 
		} else {
			ingest.getRunIndexing().Click();
		}

	}


	@When("^.*(\\[Not\\] )? click_source_system_dropdown$")
	public void click_source_system_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getSpecifySourceSystem().Visible()  ;}}), Input.wait30); 
	    	ingest.getSpecifySourceSystem().Click();
		} else {
			ingest.getNextButton().Click();
		}

	}


	@And("^.*(\\[Not\\] )? on_saved_draft_ingestion$")
	public void on_saved_draft_ingestion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			on_ingestion_home_page(scriptState, dataMap);
			new_ingestion_created(scriptState, dataMap);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getSaveDraftButton().Enabled()  ;}}), Input.wait30); 
	    	ingest.getSaveDraftButton().Click();
	    	Thread.sleep(2000);
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getApproveMessageOKButton().Enabled()  ;}}), Input.wait30); 
	    	ingest.getApproveMessageOKButton().Click();
	    	on_ingestion_home_page(scriptState, dataMap);
	    	ingest.openFirstIngestionSettings(scriptState);
		} else {
			on_ingestion_home_page(scriptState, dataMap);
		}

	}


	@When("^.*(\\[Not\\] )? click_open_wizard_option$")
	public void click_open_wizard_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionAction_Wizard().Displayed()  ;}}), Input.wait30); 
	    	ingest.getIngestionAction_Wizard().Click();
		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionAction_Delete().Displayed()  ;}}), Input.wait30); 
	    	ingest.getIngestionAction_Delete().Click();
		}

	}


	@And("^.*(\\[Not\\] )? on_ingest_execution_details_page$")
	public void on_ingest_execution_details_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			on_ingestion_home_page(scriptState, dataMap);
			new_ingestion_created(scriptState, dataMap);
			click_preview_run_button(scriptState, dataMap);
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getFirstIngestionTileName().Visible()  ;}}), Input.wait30); 
	    	ingest.getFirstIngestionTileName().Click();
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionExecutionHeader().Visible()  ;}}), Input.wait30); 
			String execHeaderText = ingest.getIngestionExecutionHeader().getText();
			if (execHeaderText.equals("Ingestion Execution Details")) {
				pass(dataMap,"Ingestion Execution Details Popup is Displayed");
			} else {
				fail(dataMap,"Ingestion Execution Details Popup did NOT Display");
				}
		} else {
			on_ingestion_home_page(scriptState, dataMap);
		}

	}


	@When("^.*(\\[Not\\] )? click_close_button$")
	public void click_close_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionName_ExecCloseButton().Visible()  ;}}), Input.wait30); 
	    	ingest.getIngestionName_ExecCloseButton().Click();
		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionAction_Wizard().Visible()  ;}}), Input.wait30); 
	    	ingest.getIngestionAction_Wizard().Click();
		}

	}


	@When("^.*(\\[Not\\] )? click_delete_option$")
	public void click_delete_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionAction_Delete().Visible()  ;}}), Input.wait30); 
	    	ingest.getIngestionAction_Delete().Click();
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getApproveMessageOKButton().Visible()  ;}}), Input.wait30); 
	    	ingest.getApproveMessageOKButton().Click();
		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionAction_Wizard().Visible()  ;}}), Input.wait30); 
	    	ingest.getIngestionAction_Wizard().Click();
		}

	}
	
	@Then("^.*(\\[Not\\] )? verify_expected_date_time_format_is_displayed$")
	public void verify_expected_date_time_format_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
    	ExtentTest test = null;
		if (dataMap != null)
			test = (ExtentTest) dataMap.get("ExtentTest");
		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getDateTimeOption().Exists()  ;}}), Input.wait30); 

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getTimeZone().Visible()  ;}}), Input.wait30); 
			
    		if (test != null) test.log(LogStatus.PASS, "Valid expected dateTime and TimeZone formats");
    		Assert.assertTrue(true);
		} else {
    		if (test != null) test.log(LogStatus.FAIL, "Invalid dateTime and TimeZone formats or not found");
    		Assert.assertTrue(false);
		}
	}

	@Then("^.*(\\[Not\\] )? verify_new_ingestion_tile_is_displayed$")
	public void verify_new_ingestion_tile_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getTotalIngestCount().Visible()  ;}}), Input.wait30); 

			String totalIngestCountText = ingest.getTotalIngestCount().getText();
			if (totalIngestCountText.equals(dataMap.get("actualCount"))) {
				pass(dataMap,"Ingestion Tile and Count Have Increased");
			} else {
				fail(dataMap,"Ingestion was NOT Processed Successfully");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Ingestion was NOT Processed Successfully");
			}
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_executed_successfully$")
	public void verify_ingestion_executed_successfully(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getTotalIngestCount().Visible()  ;}}), Input.wait30); 
			String totalIngestCountText = ingest.getTotalIngestCount().getText();
			
			if (totalIngestCountText.equals(dataMap.get("actualCount"))) {
				pass(dataMap,"Ingestion Processed Successfully.");
			} else {
				fail(dataMap,"Ingestion was NOT Processed Successfully");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Ingestion was NOT Processed Successfully");
			}
		}

	}


	@Then("^.*(\\[Not\\] )? verify_source_doc_id_is_auto_mapped$")
	public void verify_source_doc_id_is_auto_mapped(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getDESTINATIONFIELD1OPTION().Visible()  ;}}), Input.wait30); 
			String sourceOptionText = ingest.getDESTINATIONFIELD1OPTION().getText();
			if (sourceOptionText.equals("SourceDocID")) {
				pass(dataMap,"Destination Field displays the expected 'SourceDocID'");
			} else {
				fail(dataMap,"Destination Field Does Not display the expected 'SourceDocID'");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Destination Field Does Not display the expected 'SourceDocID'");
			}
		}

	}


	@Then("^.*(\\[Not\\] )? verify_expected_fields_are_mandatory$")
	public void verify_expected_fields_are_mandatory(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getMappingRequiredSource1().Displayed()  ;}}), Input.wait30); 
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getMappingRequiredSource2().Displayed()  ;}}), Input.wait30); 
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getMappingRequiredSource3().Displayed()  ;}}), Input.wait30); 
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getMappingRequiredSource4().Displayed()  ;}}), Input.wait30); 
			pass(dataMap,"Ingestion required fields are displayed with an asterisk");
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Ingestion required fields are not displayed with an asterisk");
			}
		}
	}


	@Then("^.*(\\[Not\\] )? verify_configure_mapping_page_is_displayed$")
	public void verify_configure_mapping_page_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getConfigureMappingText().Displayed()  ;}}), Input.wait30); 
			String configMappingText = ingest.getConfigureMappingText().getText();
			
			if (configMappingText.equals("Configure Field Mapping")) {
				pass(dataMap,"Ingestion configure mapping section is displayed");
			} else {
				fail(dataMap,"Ingestion configure mapping section is not displayed");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Ingestion configure mapping section is NOT displayed");
			}
		}
	}


	@Then("^.*(\\[Not\\] )? verify_source_system_displays_expected_options$")
	public void verify_source_system_displays_expected_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getSpecifySourceSystem().Displayed()  ;}}), Input.wait30); 

			String specifySourceSystemText = ingest.getSpecifySourceSystem().getText();
			if (specifySourceSystemText.contains("TRUE")
							) {
						pass(dataMap,"TRUE was found in the dropdown for Source System");
					} else {
						fail(dataMap,"TRUE was NOT found in the dropdown for Source System");
					}
		}catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"TRUE was NOT found in the dropdown for Source System");
			}
		}
	}


	@Then("^.*(\\[Not\\] )? verify_expected_source_fields_are_displayed$")
	public void verify_expected_source_fields_are_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		try {
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getSourceSystemTitle().Displayed()  ;}}), Input.wait30); 
			String sourceSystemTitleText = ingest.getSourceSystemTitle().getText();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getSourceLocationTitle().Displayed()  ;}}), Input.wait30); 
			String sourceLocationTitleText = ingest.getSourceLocationTitle().getText();
			
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getSourceFolderTitle().Displayed()  ;}}), Input.wait30); 
			String sourceFolderTitleText = ingest.getSourceFolderTitle().getText();
			
			if ((sourceSystemTitleText.split(":")[0]).equals("Source System") &&
				(sourceLocationTitleText.split(":")[0]).equals("Source Location") &&
				(sourceFolderTitleText.split(":")[0]).equals("Source Folder")
						) {
					pass(dataMap,"Source System, Location and Folder Fields are Displayed");
				} else {
					fail(dataMap,"Source System, Location and Folder Fields are NOT Displayed");
				}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Source System, Location and Folder Fields are NOT Displayed");
			}
		}

	}
	
	@Then("^.*(\\[Not\\] )? verify_close_button_redirects_to_ingestion_home_page$")
	public void verify_close_button_redirects_to_ingestion_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			String url = driver.getUrl();
			
			if(url.contains("Ingestion/Home")){
				pass(dataMap,"Close Button redirects to Ingestion Home Page");
			} else {
				fail(dataMap,"Close Button does not redirect to Ingestion Home Page");
			}
		}
		else {
			 throw new ImplementationException("Close Button does not redirect to Ingestion Home Page");
			}
		}
	

	@Then("^.*(\\[Not\\] )? verify_delete_button_is_available_on_tile$")
	public void verify_delete_button_is_available_on_tile(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						ingest.getIngestionAction_Delete().Visible()  ;}}), Input.wait30); 
				ingest.getIngestionAction_Delete().Exists();
				pass(dataMap, "Ingestion Action Delete Button is Avaliable");
				}
			catch (Exception e) {
				if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Ingestion Action Delete Button Is not Avaliable");
				}
			}
			
			
	}

	@Then("^.*(\\[Not\\] )? verify_mandatory_toast_message_is_displayed$")
	public void verify_mandatory_toast_message_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			
			driver.FindElementByTagName("body").SendKeys(Keys.HOME.toString());

			//Deselect Required Field
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getMappingSOURCEFIELD4().Displayed()  ;}}), Input.wait30); 
			ingest.getMappingSOURCEFIELD4().SendKeys("Select");

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getPreviewRun().Displayed()  ;}}), Input.wait30); 
			ingest.getPreviewRun().Click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getToastMessage().Displayed()  ;}}), Input.wait30); 
			
			pass(dataMap,"Toast Message is Displayed for Required Fields");
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Toast Message is NOT Displayed for Required Fields");
			}
		}
	}


	@Then("^.*(\\[Not\\] )? verify_saved_draft_retains_files_selected$")
	public void verify_saved_draft_retains_files_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getActualSourceSys().Displayed()  ;}}), Input.wait30); 
			String actualSourceSysText = ingest.getActualSourceSys().getText();
			
;			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getActualSrcLoc().Displayed()  ;}}), Input.wait30); 
			String actualSrcLocText = ingest.getActualSrcLoc().getText();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getActualSrcFolder().Displayed()  ;}}), Input.wait30); 
			String actualSrcFolderText = ingest.getActualSrcFolder().getText();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getActualDocKey().Displayed()  ;}}), Input.wait30); 
			String actualDocKeyText= ingest.getActualDocKey().getText();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getActualNativeFile().Displayed()  ;}}), Input.wait30); 
			String actualNativeFileText = ingest.getActualNativeFile().getText();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getActualMp3File().Displayed()  ;}}), Input.wait30); 
			String actualMp3FileText = ingest.getActualMp3File().getText();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getActualAudioFile().Displayed()  ;}}), Input.wait30); 
			String actualAudioFileText = ingest.getActualAudioFile().getText();
			
			if (actualSourceSysText.equals(dataMap.get("source_system")) &&
					actualSrcLocText.equals(dataMap.get("source_location")) &&
					actualSrcFolderText.equals(dataMap.get("source_folder")) &&
					actualDocKeyText.equals(dataMap.get("doc_key")) &&
					actualNativeFileText.equals(dataMap.get("native_file")) &&
					actualMp3FileText.equals(dataMap.get("mp3_file")) &&
					actualAudioFileText.equals(dataMap.get("audio_file"))
					) {
				pass(dataMap,"Ingestion Saved Draft Files are Retained");
			} else {
				fail(dataMap,"Ingestion Saved Draft Files are NOT Retained");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Ingestion Saved Draft Files are NOT Retained");
			}
		}

	}

	@And("^.*(\\[Not\\] )? click_preview_run_button$")
	public void click_preview_run_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getPreviewRun().Visible()  ;}}), Input.wait30); 
				ingest.getPreviewRun().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			        ingest.getApproveMessageOKButton().Visible() ;}}), Input.wait30); 
	    	    ingest.getApproveMessageOKButton().Click(); 

				pass(dataMap, "Get Preview Run Button is Clickable");
			}
			catch (Exception e) {
				fail(dataMap, "Get Preview Run Button could not be Clicked");
			}

		} else {
			ingest.getToastMessage();
		}
	}
	
	
	@Then("^.*(\\[Not\\] )? click_source_DAT_field$")
	public void click_source_DAT_field(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getSourceDATField().Visible()  ;}}), Input.wait30); 
			
			ingest.SecondRow().Click();
			ingest.SecondRowOptions().Click();
			ingest.ThrirdRow().Click();
			ingest.ThrirdRowOptions().Click();
			ingest.FourthRow().Click();
			ingest.FourthRowOptions().Click();
			Thread.sleep(2000);
						
			throw new ImplementationException("click_source_DAT_field");
		} else {
			throw new ImplementationException("NOT click_source_DAT_field");

		}

	}

	@Then("^.*(\\[Not\\] )? verify_first_50_records_are_displayed$")
	public void verify_first_50_records_are_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getRecordTable().Visible()  ;}}), Input.wait30); 
				int recordSize =  ingest.getRecordTable().getWebElement().findElements(By.tagName("tr")).size();
				if(recordSize <=50) pass(dataMap, "There are less than 50 records");
				else fail(dataMap, "There are more than 50 records");
			}
			catch(Exception e) {
				ingest.getToastMessage();
			}
		} 
	}

	@Then("^.*(\\[Not\\] )? verify_source_selection_types_are_displayed$")
	public void verify_source_selection_types_are_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
				try {
			// Checkbox
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getNativeCheckBox().Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getTextCheckBox().Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getPDFCheckBoxstionButton().Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getTIFFCheckBox().Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getDATcheckbox().Displayed()  ;}}), Input.wait30);	
				System.out.println("Made it past preliminary checks");
				
				//DAT files
				ingest.getDATcheckbox().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getSourceSelectionDATLoadFile().Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getDocumentKey().Displayed()  ;}}), Input.wait30);
				
				//Make sure Key Field is displayed once in DAT Section
				Assert.assertEquals(1, driver.FindElements(By.id("ddlKeyDatFile")).size());
				
				
				//Open Native Field
				ingest.getNativeCheckBox().Click();
				
				//Verify Both DAT Checkbox and Load File Field are displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getIsNativeInPathInDAT().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getNativeLST().Displayed()  ;}}), Input.wait30);

				//Click Native DAT Checkbox 
				ingest.getIsNativeInPathInDAT().Click();

				//Check to see after DAT Checkbox is selected if File Path Field is displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getNativeFilePathFieldinDAT().Displayed()  ;}}), Input.wait30);

				//Make Sure Load File Field is disabled
				Assert.assertFalse(ingest.getNativeLST().Enabled());

				
				
				//Open Text Field
				ingest.getTextCheckBox().Click();

				//Verify Both DAT Checkbox and Load File Field are displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getIsTextInPathInDAT().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getTextLST().Displayed()  ;}}), Input.wait30);
				
				//Click Text DAT Checkbox
				ingest.getIsTextInPathInDAT().Click();

				//Check to see after DAT Checkbox is selected if File Path Field is displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getTextFilePathFieldinDAT().Displayed()  ;}}), Input.wait30);
				
				//Make Sure Load File Field is disabled
				Assert.assertFalse(ingest.getTextLST().Enabled());


				
				//Open PDF Field
				ingest.getPDFCheckBoxstionButton().Click();


				//Verify Both DAT Checkbox and Load File Field are displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getIsPDFInPathInDAT().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getPDFLST().Displayed()  ;}}), Input.wait30);
				
				//Click PDF DAT Checkbox
				ingest.getIsPDFInPathInDAT().Click();

				//Check to see after DAT Checkbox is selected if File Path Field is displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getPDFFilePathFieldinDAT().Displayed()  ;}}), Input.wait30);
				
				//Make Sure Load File Field is disabled
				Assert.assertFalse(ingest.getPDFLST().Enabled());


				
				//Open TIFF DAT Checkbox
				ingest.getTIFFCheckBox().Click();

				//Verify Both DAT Checkbox and Load File Field are displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getIsTIFFInPathInDAT().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getTIFFLST().Displayed()  ;}}), Input.wait30);
				
				//Click TIFF DAT Checkbox
				ingest.getIsTIFFInPathInDAT().Click();
				
				//Check to see after DAT Checkbox is selected if File Path Field is displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getTIFFFilePathFieldinDAT().Displayed()  ;}}), Input.wait30);
				
				//Make Sure Load File Field is disabled
				Assert.assertFalse(ingest.getTIFFLST().Enabled());

				
				//Other Option Checks
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getOtherCheckBox().Displayed()  ;}}), Input.wait30);

				//Click "Other" field
				ingest.getOtherCheckBox().Click();

				//Wait for Link Dropdown, Load File Dropdown and DAT Checkbox to be displayed
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getOtherLinkType().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getOtherLoadFile().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getOtherCheckBox().Displayed()  ;}}), Input.wait30);

				//Get Link Type Drop down contents, and make sure it contains "Translation and Related"
				String LinkTypeContents = ingest.getOtherLinkType().getText();
				Assert.assertTrue(LinkTypeContents.contains("Translation") && LinkTypeContents.contains("Related"));

				pass(dataMap,"Selection types are displayed");
				
				
			}
			catch(Exception e) {
				fail(dataMap,"Selection types are not displayed");
			}
		} else {
			fail(dataMap,"Selection types are not displayed");
		}
	}


	@Then("^.*(\\[Not\\] )? verify_all_components_are_displayed_on_the_wizard$")
	public void verify_all_components_are_displayed_on_the_wizard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
				try {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							ingest.getSourceSystemTitle().Displayed()  ;}}), Input.wait30);
			
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							ingest.getSourceLocationTitle().Displayed()  ;}}), Input.wait30);
			
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							ingest.getDATTitle().Displayed()  ;}}), Input.wait30);
			
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							ingest.getNativeTitle().Displayed()  ;}}), Input.wait30);
			
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
							ingest.getConfigureMappingText().Displayed()  ;}}), Input.wait30);
			
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
							ingest.getPreviewRun().Displayed()  ;}}), Input.wait30);
			
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
							ingest.getIngestionWizardTitle().Displayed()  ;}}), Input.wait30);
	    			
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
							ingest.getSaveDraftButton().Displayed()  ;}}), Input.wait30);
	    	
					pass(dataMap,"All components are displayed on the wizard");

			}
			catch(Exception e) {
				fail(dataMap,"All components are NOT displayed on the wizard");}}
		else {
			fail(dataMap,"All components are NOT displayed on the wizard");
				}
		}
  
	@Then("^.*(\\[Not\\] )? verify_multi_value_ascii_is_set_by_default$")
	public void verify_multi_value_ascii_is_set_by_default(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					ingest.getDATDelimitersNewLine().Displayed()  ;}}), Input.wait30);
				//Get First Default Text of Dropdown
				String defaultAsciiText = ingest.getDATDelimitersNewLine().selectFromDropdown().getFirstSelectedOption().getText();

				//Make sure value is correct Ascii(59)
				if(defaultAsciiText.equals("ASCII(59)")) pass(dataMap, "Default Value is Ascii 59");
				else fail(dataMap, "Default Value is not Ascii 59");
			}
			catch(Exception e) {
				fail(dataMap, "Was not able to parse default Ascii Value");
				
			}
		}
			

	}


	@When("^.*(\\[Not\\] )? click_copy_button$")
	public void click_copy_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* On the Ingestion Execution page
			//* Click on the Action dropdown
			//* Click on Copy option
			//* Ingestion Wizard page is displayed
			//
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getFirstIngestionTileName().Visible()  ;}}), Input.wait30); 
				ingest.getFirstIngestionTileName().Click();

				//Wait and Click Action Dropdown

			}
			catch(Exception e) {}
		}

	}

	@Then("^.*(\\[Not\\] )? verify_source_field_is_auto_populated$")
	public void verify_source_field_is_auto_populated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC2559 To verify that row population in the Configure Mapping will be as per the fields avialable in the DAT file.
			//
			//* When choosing a DAT file to upload, the fields specified in the file are auto populated in the Source Field section
			//* Number of headers specified should match the number of fields that were auto populated
			//
			throw new ImplementationException("verify_source_field_is_auto_populated");
		} else {
			throw new ImplementationException("NOT verify_source_field_is_auto_populated");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_destination_field_is_auto_populated$")
	public void verify_destination_field_is_auto_populated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC2558 To Verify -> No of Headers in DAT < No. of headers in Destination field in Configure mapping page.
			//
			//* After a file has been selected or uploaded for DAT file and Source Field is auto populated, the Destination Field is auto mapped
			//* Number of headers populated on the source field section should match the number of fields mapped on Destination field section
			//
			throw new ImplementationException("verify_destination_field_is_auto_populated");
		} else {
			throw new ImplementationException("NOT verify_destination_field_is_auto_populated");
		}

	}


	@And("^.*(\\[Not\\] )? publish_ingested_files$")
	public void publish_ingested_files(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//
		//* Navigate to Ingestion/Analytics
		//* ??? Unable to select Incrmental Analysis and unable to click Publish Button
		//* Select "Incremental Analysis"
		//* Click Publish button
		//
		if (scriptState) {
		    String url = (String) dataMap.get("URL");
			driver.Navigate().to(url + "Ingestion/Analytics");
			driver.waitForPageToBeReady();
						
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					ingest.getIncrementalAnalysisBtn().Displayed()  ;}}), Input.wait30);
				ingest.getIncrementalAnalysisBtn().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
						ingest.getPublishAnalyticsBtn().Displayed() && ingest.getPublishAnalyticsBtn().Enabled() ;}}), Input.wait30);
					ingest.getPublishAnalyticsBtn().Click();
					
			pass(dataMap,"You succesfully published a file");
			} else {
				webDriver.get("http://www.google.com");
				fail(dataMap,"You unsuccesfully published a file");

			}
		} 

	@And("^.*(\\[Not\\] )? create_saved_search$")
	public void create_saved_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if	(scriptState) {
			try {
			SessionSearch session = new SessionSearch(driver);
			SearchContext sessionContext = new SearchContext();
			
			String url = (String) dataMap.get("URL");
			driver.Navigate().to(url + "Search/Searches");
			driver.waitForPageToBeReady();
			
			//Enter Search into text box
			session.insertFullText("AudioPlayerReady=1");
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					ingest.getSeachBtn().Displayed() && ingest.getSeachBtn().Enabled() ;}}), Input.wait30);
				ingest.getSeachBtn().Click();
				
			//Saves, Clicks on "My saved Search,Enter valid name and save
			sessionContext.save_search(true,dataMap);
			pass(dataMap,"You have successfully Saved a search");
			}catch (Exception e) {
				e.printStackTrace();
				fail(dataMap,"You were not able to save a search");
			}
		} else {
			fail(dataMap,"You were not able to save a search");

		}
	}
	
	//skip
	@When("^.*(\\[Not\\] )? unpublish_ingestion_files$")
	public void unpublish_ingestion_files(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to /Ingestion/UnPublish
			//* Select saved filter created
			//* Click Unpublish button
			//
			
			 String url = (String) dataMap.get("URL");
			 driver.Navigate().to(url + "Ingestion/Analytics");
			 driver.waitForPageToBeReady();
			throw new ImplementationException("unpublish_ingestion_files");
		} else {
			throw new ImplementationException("NOT unpublish_ingestion_files");
		}

	}

	//skip
	@Then("^.*(\\[Not\\] )? verify_unpublish_for_audio_documents_is_successful$")
	public void verify_unpublish_for_audio_documents_is_successful(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6067 To Verify Unpublish for Ingested audio documents
			//
			//* Verify successful toast message appears
			//
			throw new ImplementationException("verify_unpublish_for_audio_documents_is_successful");
		} else {
			throw new ImplementationException("NOT verify_unpublish_for_audio_documents_is_successful");
		}

	}


	@When("^.*(\\[Not\\] )? select_audio_indexing$")
	public void select_audio_indexing(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Return to Ingestion/Home
			//* Look for Ingestion Tile created
			//* Click on Ingestion Title
			//* Run the Catalog step
			//* Run the Copy step
			//* Click on Audio checkbox
			//* Select 3 language packs (Norh American English/United Kingdom English/German)
			//* Run Indexing
			//
			throw new ImplementationException("select_audio_indexing");
		} else {
			throw new ImplementationException("NOT select_audio_indexing");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_no_error_message_is_displayed$")
	public void verify_no_error_message_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6001 To Verify In Ingestions, for audio indexing, there should not be any error message.
			//* Validate no errors are displayed when indexing
			//
			throw new ImplementationException("verify_no_error_message_is_displayed");
		} else {
			throw new ImplementationException("NOT verify_no_error_message_is_displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_error_messaged_displays_mp3_variant$")
	public void verify_error_messaged_displays_mp3_variant(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5961 To Verify In Ingestion, the error message for audio ingestions should display "MP3 Variant"
			//* Return to Ingestion/Home
			//* Look for Ingestion Tile created
			//* Click on Ingestion Title
			//* Run the Catalog step
			//* Remove files ingested from source location/folder
			//* Run the Copy step in the ingestion
			//* Verify "MP3 Variant:File Not Found" error is displayed
			//
			throw new ImplementationException("verify_error_messaged_displays_mp3_variant");
		} else {
			throw new ImplementationException("NOT verify_error_messaged_displays_mp3_variant");
		}

	}


	@When("^.*(\\[Not\\] )? run_ingestion_indexing$")
	public void run_ingestion_indexing(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* After ingestion has been created
			//* Go to Ingestion/Home page
			//* Open the Ingestion tile created
			//* Take note of the Audio Document Count in the Copy Step
			//* Run Indexing step
			//* Take note of the Audio Doucment Count in the Indeing Step
			//
			throw new ImplementationException("run_ingestion_indexing");
		} else {
			throw new ImplementationException("NOT run_ingestion_indexing");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_document_and_audio_docs_count_are_the_same$")
	public void verify_document_and_audio_docs_count_are_the_same(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6022 To verify Document Count for Audio Docs in Indexing section
			//
			//* Open Ingestion Tile from Ingestion/Home page
			//* Validate Audio Document count and Indexing have the same count
			//
			throw new ImplementationException("verify_document_and_audio_docs_count_are_the_same");
		} else {
			throw new ImplementationException("NOT verify_document_and_audio_docs_count_are_the_same");
		}

	}

	@And("^.*(\\[Not\\] )? click_catalog_play_button$")
	public void click_catalog_play_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Find Ingested tile created
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionTile().Visible()  ;}}), Input.wait30); 
			ingest.getIngestionTile().Click();
			System.out.println("Clicked");
			
			Thread.sleep(2000);

			//* Modal is displayed
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngressionModal().Displayed()  ;}}), Input.wait30); 

			System.out.println(ingest.getIngestionTileText().getText()); // get the name of the automation project 
			
			Thread.sleep(4000);
			
			//now have to wait until it pass or fail
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getCatelogingStatus().getText().equals("pass") ;}}), Input.wait30);
			
			throw new ImplementationException("click_catalog_play_button");
		} else {
			throw new ImplementationException("NOT click_catalog_play_button");

		}
	}
	
	@And("^.*(\\[Not\\] )? click_copy_play_button$")
	public void click_copy_play_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Find Ingested tile created
			//* Click on the Ingestion Name
			//* Modal is displayed
			//* After Cataloging, click Copy play button
			//
			
//			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//	    			ingest.getIngestionTile().Displayed()  ;}}), Input.wait30); 
//			ingest.getIngestionTile().Click();
//			

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngressionModal().Displayed()  ;}}), Input.wait30); 

			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getCopyPlayButton().Displayed()  ;}}), Input.wait30); 
			ingest.getCopyPlayButton().Click();
			
			
			throw new ImplementationException("click_copy_play_button");
		} else {
			throw new ImplementationException("NOT click_copy_play_button");
		}

	}


	@And("^.*(\\[Not\\] )? rename_MP3_doc_file$")
	public void rename_MP3_doc_file(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to Destination Location where file is located
			//* Find MP3 file used in the ingestion
			//* Rename the file and save
			//
			throw new ImplementationException("rename_MP3_doc_file");
		} else {
			throw new ImplementationException("NOT rename_MP3_doc_file");
		}

	}


	@When("^.*(\\[Not\\] )? click_run_indexing_play_button$")
	public void click_run_indexing_play_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to /Ingestion/Home page
			//* Find Ingestion Created by filtering by Copied
			//* Click on the Indexing play button
			//
			throw new ImplementationException("click_run_indexing_play_button");
		} else {
			throw new ImplementationException("NOT click_run_indexing_play_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_audio_indexing_fails$")
	public void verify_audio_indexing_fails(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6069 To Verify For Audio Indexing only the files at Destination location should be sent for Indexing.
			//
			//* Assert Indexing for Ingestion fails due to MP3 Doc name changing
			//
			throw new ImplementationException("verify_audio_indexing_fails");
		} else {
			throw new ImplementationException("NOT verify_audio_indexing_fails");
		}

	}
	
	
	@And("^.*(\\[Not\\] )? click_add_project_button$")
	public void click_add_project_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		driver.waitForPageToBeReady();
		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getAddNewProjectBtn().Enabled() && ingest.getAddNewProjectBtn().Displayed()  ;}}), Input.wait30);
				ingest.getAddNewProjectBtn().Click();

			} catch(Exception e) {
				e.printStackTrace();
				fail(dataMap,"You didn't click Add project succesfully");
			}
			
			pass(dataMap,"You clicked Add project succesfully");

		} else {
			fail(dataMap,"You didn't click Add project succesfully");

		}
	}
	
	@And("^.*(\\[Not\\] )? click_kick_off_help_icon$")
	public void click_kick_off_help_icon(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		
		if (scriptState) {
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getKickOffHelpIcon().Visible() ;}}), Input.wait30); 
					ingest.getKickOffHelpIcon().Click();
			pass(dataMap,"You clicked Kick off Help Icon succesfully");
		} else {
			fail(dataMap,"You didn't clicked Kick off Help Icon succesfully");

		}
	}
	
	@And("^.*(\\[Not\\] )? click_run_analytics_help_icon$")
	public void click_run_analytics_help_icon(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getRunIncAnalytics().Visible() ;}}), Input.wait30); 
					ingest.getRunIncAnalytics().Click();
			pass(dataMap,"You clicked Run Analytics Help Icon succesfully");
		} else {
			fail(dataMap,"You didn't clicked Run Analytics off Help Icon succesfully");

		}	}
	
	@Then("^.*(\\[Not\\] )? verify_project_screen_displays_expected_options$")
	public void verify_project_screen_displays_expected_options (boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getKickOffText().Visible() ;}}), Input.wait30); 
			
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getRunIncAnalyticsText().Visible() ;}}), Input.wait30);
						
			assertEquals("Kick Off Analytics Automatically: ", ingest.getKickOffText().getText());
			assertEquals("Run Incremental Analytics for New Small Data(<20%): ",ingest.getRunIncAnalyticsText().getText());
			
			pass(dataMap,"Kick off Text and Run Analytics Text are displayed");
		} else {
			fail(dataMap,"Kick off Text and Run Analytics Text are not displayed");
		}
	
	}
	@Then("^.*(\\[Not\\] )? verify_run_incremental_analytics_option_displays_correct_message$")
	public void verify_run_incremental_analytics_option_displays_correct_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			String AnalyticsMsg = "If this option is disabled, full analytics is always executed automatically by Sightline. If this option is enabled, Sightline runs full analytics when the new data being ingested is >20% of the existing data and runs incremental analytics when the new data being ingests is <=20% of the existing data.";
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getRunIncAnalyticsPopUpText().Visible() ;}}), Input.wait30); 
			
			String RunIncAnalyticsText = ingest.getRunIncAnalyticsPopUpText().getText();
			
			Assert.assertEquals(AnalyticsMsg, RunIncAnalyticsText);
			pass(dataMap,"Run Analytics Message is displayed correctly");
			
		} 
	
	}
	
	@Then("^.*(\\[Not\\] )?  verify_kick_off_analytics_help_option_displays_correct_message$")
	public void  verify_kick_off_analytics_help_option_displays_correct_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			String KickOffMsg = "If this option is disabled, the ingestion will not kick off analytics after the ingestion is complete. The user needs to manually run analytics and publish the documents. If this option is enabled, the analytics is automatically kicked off after the data is ingested after all the datasets being ingested are complete, and automatically publishes the documents into the project. Sightline will wait until all the datasets are complete to kick off analytics.";
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getKickOffPopUpText().Visible() ;}}), Input.wait30); 
			
			String KickOffAnalytics = ingest.getKickOffPopUpText().getText();
			
			Assert.assertEquals(KickOffMsg, KickOffAnalytics);
			pass(dataMap,"Kick Off Analytics Message is displayed correctly");
			
		} else {
			fail(dataMap,"Kick Off Analytics Message is not displayed correctly");
		}

	
	}
	
	
	
	
}

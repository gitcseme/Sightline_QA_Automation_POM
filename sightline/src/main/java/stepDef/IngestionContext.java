package stepDef;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import automationLibrary.Driver;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
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
			ingest.requiredFieldsAreEntered(scriptState);
			click_next_button(scriptState, dataMap);
		} else {
			ingest.requiredFieldsAreEntered(scriptState);
		}

	}


	@When("^.*(\\[Not\\] )? click_run_ingest_button$")
	public void click_run_ingest_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getbtnRunIngestion().Visible()  ;}}), Input.wait30);
			ingest.getbtnRunIngestion().Click();
		} else {
			ingest.getPreviewRun().Click();
		}

	}

	@And("^.*(\\[Not\\] )? required_fields_are_entered$")
	public void required_fields_are_entered(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getSpecifySourceSystem().Visible()  ;}}), Input.wait30);
			ingest.requiredFieldsAreEntered(scriptState);

		} else {
			ingest.requiredFieldsAreEntered(scriptState);
		}

	}


	@When("^.*(\\[Not\\] )? click_next_button$")
	public void click_next_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.FindElementByTagName("body").SendKeys(Keys.HOME.toString());
			Thread.sleep(5000);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getNextButton().Displayed()  ;}}), Input.wait30); 
	    	ingest.getNextButton().Click();
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    	ingest.getApproveMessageOKButton().Visible() ;}}), Input.wait30); 
			Thread.sleep(5000);
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
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getPreviewRun().Visible()  ;}}), Input.wait30); 
	    	ingest.getPreviewRun().Click();
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getApproveMessageOKButton().Visible()  ;}}), Input.wait30); 
	    	ingest.getApproveMessageOKButton().Click();
	    	
	    	click_run_ingest_button(scriptState, dataMap);
	    	
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
	}


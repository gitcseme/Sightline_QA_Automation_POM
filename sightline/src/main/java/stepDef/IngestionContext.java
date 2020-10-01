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
	/* 
	 * ignore
	 * 
	public void (boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

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
		System.out.print("i am noe here");
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

		if (scriptState) {
			//
			//* Navigate to Ingestion/Analytics
			//* ??? Unable to select Incrmental Analysis and unable to click Publish Button
			//* Select "Incremental Analysis"
			//* Click Publish button
			//
			throw new ImplementationException("publish_ingested_files");
		} else {
			throw new ImplementationException("NOT publish_ingested_files");
		}

	}


	@And("^.*(\\[Not\\] )? create_saved_search$")
	public void create_saved_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to /Search/Searches
			//* Enter "AudioPlayerReady=1" into the text box
			//* Click Search Button
			//* Assert Audio file is displayed after search is completed
			//* Click Save Button
			//* Save Search modal is displayed
			//* Click on "My Saved Search" 
			//* Enter a valid name into the text box
			//* Click Save
			//
			throw new ImplementationException("create_saved_search");
		} else {
			throw new ImplementationException("NOT create_saved_search");
		}

	}


	@When("^.*(\\[Not\\] )? unpublish_ingestion_files$")
	public void unpublish_ingestion_files(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to /Ingestion/UnPublish
			//* Select saved filter created
			//* Click Unpublish button
			//
			throw new ImplementationException("unpublish_ingestion_files");
		} else {
			throw new ImplementationException("NOT unpublish_ingestion_files");
		}

	}


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


	@And("^.*(\\[Not\\] )? click_copy_play_button$")
	public void click_copy_play_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Find Ingested tile created
			//* Click on the Ingestion Name
			//* Modal is displayed
			//* After Cataloging, click Copy play button
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionTile().Displayed()  ;}}), Input.wait30); 
			ingest.getIngestionTile().Click();
			

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
		
	@And("^.*(\\[Not\\] )? map_configuration_fields$")
	public void map_configuration_fields(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("map_configuration_fields");
		} else {
			throw new ImplementationException("NOT map_configuration_fields");
		}

	}


	@And("^.*(\\[Not\\] )? click_copy_play_icon$")
	public void click_copy_play_icon(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Open ingested file
			//* If other steps have not been started, start Catalogue and copy steps before Indexing
			//* On the ingestion deatils page
			//* After the ingestion has been catalogued
			//* Click on the Play button for the Copy step of the ingestion
			//
			throw new ImplementationException("click_copy_play_icon");
		} else {
			throw new ImplementationException("NOT click_copy_play_icon");
		}

	}


	@And("^.*(\\[Not\\] )? click_indexing_play_icon$")
	public void click_indexing_play_icon(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Open ingested file
			//* If other steps have not been started, start Catalogue and copy steps before Indexing
			//* Click the play button for Indexing step
			//
			throw new ImplementationException("click_indexing_play_icon");
		} else {
			throw new ImplementationException("NOT click_indexing_play_icon");
		}

	}


	@When("^.*(\\[Not\\] )? start_analysing_step$")
	public void start_analysing_step(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Open ingested file
			//* If other steps have not been started, start Catalogue and copy steps before Indexing
			//* Click the play button for Indexing step
			//* Select Start option to begin Analysing
			//
			throw new ImplementationException("start_analysing_step");
		} else {
			throw new ImplementationException("NOT start_analysing_step");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_being_analysed_can_not_be_searched$")
	public void verify_ingestion_being_analysed_can_not_be_searched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC873:To verify that user is not able to search the data if Ingestions Analysing is running
			//* Once the ingestion has begun the analysing process
			//* Navigate to Search page
			//* Search for ingestion that is being analysed
			//* validate that no search results for the specified ingestion is displayed
			//
			throw new ImplementationException("verify_ingestion_being_analysed_can_not_be_searched");
		} else {
			throw new ImplementationException("NOT verify_ingestion_being_analysed_can_not_be_searched");
		}

	}


	@And("^.*(\\[Not\\] )? remove_mp3_attached_files$")
	public void remove_mp3_attached_files(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to source location
			//* Locate mp3 files specified in the dat file
			//* remove the mp3 files from the directory
			//
			throw new ImplementationException("remove_mp3_attached_files");
		} else {
			throw new ImplementationException("NOT remove_mp3_attached_files");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_copy_step_fails_for_mp3_ingestion$")
	public void verify_copy_step_fails_for_mp3_ingestion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6110:To Verify Copy Error Count for MP3 Doc in Ingestion UI.
			//* Open the ingestion, to view the error in the copy step
			//* Validate the copy step fails and displays how many documents are missing
			//
			throw new ImplementationException("verify_copy_step_fails_for_mp3_ingestion");
		} else {
			throw new ImplementationException("NOT verify_copy_step_fails_for_mp3_ingestion");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_in_progress_can_not_be_searched$")
	public void verify_ingestion_in_progress_can_not_be_searched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC871:To verify that user is not able to search the data if Ingestion is Inprogress.
			//* Once the ingestion has started (in progress status)
			//* Navigate to Search page
			//* Search for ingestion that is in progress
			//* validate that no search results for the specified ingestion is displayed
			//
			throw new ImplementationException("verify_ingestion_in_progress_can_not_be_searched");
		} else {
			throw new ImplementationException("NOT verify_ingestion_in_progress_can_not_be_searched");
		}

	}


	@And("^.*(\\[Not\\] )? rename_mp3_doc_file$")
	public void rename_mp3_doc_file(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* navigate to document location
			//* Rename the mp3 file that was ingested
			//
			throw new ImplementationException("rename_mp3_doc_file");
		} else {
			throw new ImplementationException("NOT rename_mp3_doc_file");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_renaming_doc_fails_indexing_step$")
	public void verify_renaming_doc_fails_indexing_step(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6069:To Verify For Audio Indexing only the files at Destination location should be sent for Indexing.Validate that the indexing step fails when running the index step after renaming the document ingested
			throw new ImplementationException("verify_renaming_doc_fails_indexing_step");
		} else {
			throw new ImplementationException("NOT verify_renaming_doc_fails_indexing_step");
		}

	}


	@When("^.*(\\[Not\\] )? start_indexing_step$")
	public void start_indexing_step(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Open ingested file
			//* If other steps have not been started, start Catalogue and copy steps before Indexing
			//* Click the play button for Indexing step
			//* Select Start Indexing option to begin Indexing
			//
			throw new ImplementationException("start_indexing_step");
		} else {
			throw new ImplementationException("NOT start_indexing_step");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_being_indexed_can_not_be_searched$")
	public void verify_ingestion_being_indexed_can_not_be_searched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC872:To verify that user is not able to search the data if Ingestions Indexing is running
			//* Once the ingestion has begun the indexing process
			//* Navigate to Search page
			//* Search for ingestion that is being indexed
			//* validate that no search results for the specified ingestion is displayed
			//
			throw new ImplementationException("verify_ingestion_being_indexed_can_not_be_searched");
		} else {
			throw new ImplementationException("NOT verify_ingestion_being_indexed_can_not_be_searched");
		}

	}

	@And("^.*(\\[Not\\] )? on_search_home_page$")
	public void on_search_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to /Search/Searches
			//
			throw new ImplementationException("on_search_home_page");
		} else {
			throw new ImplementationException("NOT on_search_home_page");
		}

	}


	@When("^.*(\\[Not\\] )? search_for_ingestion$")
	public void search_for_ingestion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* On the search page
			//* Enter the following into the search query:
			//IngestionName: (Ingestion Name Goes here)
			//* Click Search button
			//* Add the documents that were generated to the right hand side by clicking the Plus symbol
			//* Click the dropdown action button
			//* Click on View DocView
			//
			throw new ImplementationException("search_for_ingestion");
		} else {
			throw new ImplementationException("NOT search_for_ingestion");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_file_description_is_tally_searchable$")
	public void verify_file_description_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5548:To Verify FileDescription in Tally and Search.
			//* validate FileDescription is displayed on the metadata on the right side
			//
			throw new ImplementationException("verify_file_description_is_tally_searchable");
		} else {
			throw new ImplementationException("NOT verify_file_description_is_tally_searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_email_metadata_is_populated_correctly$")
	public void verify_email_metadata_is_populated_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5267:To Verify email metadata field is populated correctly for ingested data
			//
			//* Select the following columns:
			//
			//
			//* 'EmailAllDomainCount ','EmailAllDomain',EmailAuthorDomain ,EmailRecipientNames , EmailToAddresse,EmailToName and RecipientDomainCoun
			//
			//
			//* Validate the value displayed from the search
			//* Values will match those of the Ingested Email Document
			//
			throw new ImplementationException("verify_email_metadata_is_populated_correctly");
		} else {
			throw new ImplementationException("NOT verify_email_metadata_is_populated_correctly");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_expected_fields_are_in_data_set$")
	public void verify_expected_fields_are_in_data_set(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5527:To Verify Field ParentDocID ,HeadOfHouseholdDocID and FamilyID in Ingested Data Set.Verify ingestion is successfully published and no errors are displayed
			//* Validate the following fields are displayed for the ingestion published
			//* You can selet the fields as columns if that makes things easier to validate
			//
			//
			//* ParentDocId
			//* HeadOfHouseholdDocID
			//* FamilyID
			//
			throw new ImplementationException("verify_expected_fields_are_in_data_set");
		} else {
			throw new ImplementationException("NOT verify_expected_fields_are_in_data_set");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_processing_ocr_completed_is_tally_searchable$")
	public void verify_processing_ocr_completed_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5554:To Verify ProcessingOCRComplete in Tally and Search
			//
			//* validate ProcessingOCRComplete is displayed on the metadata on the right side
			//
			throw new ImplementationException("verify_processing_ocr_completed_is_tally_searchable");
		} else {
			throw new ImplementationException("NOT verify_processing_ocr_completed_is_tally_searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_family_relationship_is_tally_searchable$")
	public void verify_family_relationship_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5555:To Verify FamilyRelationship in Tally and Search.
			//* validate FamilyRelationship is displayed on the metadata on the right side
			//
			throw new ImplementationException("verify_family_relationship_is_tally_searchable");
		} else {
			throw new ImplementationException("NOT verify_family_relationship_is_tally_searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_exception_resolution_is_tally_searchable$")
	public void verify_exception_resolution_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5556:To Verify ExceptionResolution in Tally
			//* validate ExceptionResolution is displayed on the metadata on the right side
			//
			throw new ImplementationException("verify_exception_resolution_is_tally_searchable");
		} else {
			throw new ImplementationException("NOT verify_exception_resolution_is_tally_searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_email_duplicated_doc_id_is_populated_correctly$")
	public void verify_email_duplicated_doc_id_is_populated_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5265:To Verify EmailDuplicateDocID field is populated correctly for ingested data
			//
			//* Click on Select Column button and select the following columns:
			//
			//
			//* Email DuplicateDocID
			//* EmailsDuplicates
			//
			//
			//* Validate that values are displayed for the ingested email docuements.
			//
			throw new ImplementationException("verify_email_duplicated_doc_id_is_populated_correctly");
		} else {
			throw new ImplementationException("NOT verify_email_duplicated_doc_id_is_populated_correctly");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_excel_protected_workbook_is_tally_searchable$")
	public void verify_excel_protected_workbook_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5558:To Verify ExcelProtectedWorkbook in Tally and Search.
			//* validate ExcelProtectedWorkbook is displayed on the metadata on the right side
			//
			throw new ImplementationException("verify_excel_protected_workbook_is_tally_searchable");
		} else {
			throw new ImplementationException("NOT verify_excel_protected_workbook_is_tally_searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_all_custodians_is_tally_searchable$")
	public void verify_all_custodians_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5560:To Verify AllCustodians in Tally and Search
			//* validate the following field when searching the ingested document
			//
			//
			//* AllCustodians
			//
			throw new ImplementationException("verify_all_custodians_is_tally_searchable");
		} else {
			throw new ImplementationException("NOT verify_all_custodians_is_tally_searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_review_export_id_is_tally_searchable$")
	public void verify_review_export_id_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5561:To Verify ReviewExportID in Tally and Search.
			//* validate ReviewExportID is displayed on the metadata on the right side
			//
			throw new ImplementationException("verify_review_export_id_is_tally_searchable");
		} else {
			throw new ImplementationException("NOT verify_review_export_id_is_tally_searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_attach_doc_ids_are_searchable$")
	public void verify_attach_doc_ids_are_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5552:To Verify SourceAttachDocIDs and AttachDocIDs in Search
			//
			//* validate SourceAttachDocIDs is displayed on the metadata on the right side
			//* validate AttachDocIDs is displayed on the metadata on the right side
			//
			throw new ImplementationException("verify_attach_doc_ids_are_searchable");
		} else {
			throw new ImplementationException("NOT verify_attach_doc_ids_are_searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_hidden_properties_are_tally_searchable$")
	public void verify_hidden_properties_are_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5557:To Verify HiddenProperties in Tally and Search.
			//* validate HiddenProperties is displayed on the metadata on the right side
			//
			throw new ImplementationException("verify_hidden_properties_are_tally_searchable");
		} else {
			throw new ImplementationException("NOT verify_hidden_properties_are_tally_searchable");
		}

	}


	@And("^.*(\\[Not\\] )? click_save_button$")
	public void click_save_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* After clicking Next button
			//* Click the save button 
			//* Click Yes
			//* Ingestion is saved as a draft
			//
			throw new ImplementationException("click_save_button");
		} else {
			throw new ImplementationException("NOT click_save_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_draft_ingestion_files_are_not_found$")
	public void verify_draft_ingestion_files_are_not_found(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC869: To verify that user is not able to search the Ingested data if Ingestion is in Draft Mode
			//
			//* Validate the ingested files and ingestion saved can't be found in the search
			//
			throw new ImplementationException("verify_draft_ingestion_files_are_not_found");
		} else {
			throw new ImplementationException("NOT verify_draft_ingestion_files_are_not_found");
		}

	}
	
	@Then("^.*(\\[Not\\] )? verify_components_are_displayed_correctly$")
	public void verify_components_are_displayed_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC275: To verify that progress bar is displayed on tiles and Counts of Ingested and Errors keeps on updated once Ingestion process is started.Covered:TC235: New Ingestion with Overwrite option as 'Add Only'
			//
			//* On the Ingestion Home page
			//* Validate the Ingestion displays a progress bar when the ingestion is processing each step (catalog, copy, index)
			//* Validate Source, publish and error counts are updated
			//* validate progress status is updated (In Progress, Catalogued, Copied, Indexed)
			//
			throw new ImplementationException("verify_components_are_displayed_correctly");
		} else {
			throw new ImplementationException("NOT verify_components_are_displayed_correctly");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_can_ingest_a_saved_draft$")
	public void verify_user_can_ingest_a_saved_draft(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC315:To verify that user can Ingest the files which are in Draft Mode
			//* Once the Saved Draft is opened in the wizard
			//* Validate that the user can still ingest the saved draft opened
			//
			throw new ImplementationException("verify_user_can_ingest_a_saved_draft");
		} else {
			throw new ImplementationException("NOT verify_user_can_ingest_a_saved_draft");
		}

	}


	@When("^.*(\\[Not\\] )? click_filter_by_dropdown$")
	public void click_filter_by_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("click_filter_by_dropdown");
		} else {
			throw new ImplementationException("NOT click_filter_by_dropdown");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_filter_by_dropdown_displays_expected_options$")
	public void verify_filter_by_dropdown_displays_expected_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC842 To verify that Filter by dorpdown has following options: Draft,In progress,Failed,Cataloged,Copied,Indexed,Approved & PublishedValidate the following options are displayed when opening the filter:
			//* Draft
			//* In Progress
			//* Failed
			//* Cataloged
			//* Copied
			//* Indexed
			//* Approved
			//* Published
			//
			throw new ImplementationException("verify_filter_by_dropdown_displays_expected_options");
		} else {
			throw new ImplementationException("NOT verify_filter_by_dropdown_displays_expected_options");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_view_options_are_displayed$")
	public void verify_view_options_are_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC852:To verify that user is able to view two buttons of Grid and Tile view.Validate the following options are displayed:
			//
			//* Grid view
			//* Tile View
			//
			throw new ImplementationException("verify_view_options_are_displayed");
		} else {
			throw new ImplementationException("NOT verify_view_options_are_displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_filter_by_dropdown_has_default_option_selected$")
	public void verify_filter_by_dropdown_has_default_option_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC843:To verify that in filter, follwing options are selected by default: In progress
			//* Validate the following option is selected by default
			//
			//
			//* In Progress
			//
			throw new ImplementationException("verify_filter_by_dropdown_has_default_option_selected");
		} else {
			throw new ImplementationException("NOT verify_filter_by_dropdown_has_default_option_selected");
		}

	}


	@And("^.*(\\[Not\\] )? click_on_rollback_option$")
	public void click_on_rollback_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* After item has been ingested
			//* Click on the setting for grid tile on the ingested item
			//* Click Rollback option
			//* Click Yes to confirm rollback
			//
			throw new ImplementationException("click_on_rollback_option");
		} else {
			throw new ImplementationException("NOT click_on_rollback_option");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_rolled_back_ingestion_can_be_deleted$")
	public void verify_rolled_back_ingestion_can_be_deleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC830:To verify that ingestion which is Rolled back can be deleted once it is in Draft modeCovered:TC888 To verify that ingestion which is Rolled back can be deleted.
			//* Once an Ingested item has been rolledback
			//* Check the DB to make sure the value has changed to Draft Mode (Pending DB access) ???
			//* Click on the Settings gear for the ingested grid item
			//* Click on Delete
			//* Validate the item is deleted
			//
			throw new ImplementationException("verify_rolled_back_ingestion_can_be_deleted");
		} else {
			throw new ImplementationException("NOT verify_rolled_back_ingestion_can_be_deleted");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_grid_view_displays_expected_fields$")
	public void verify_ingestion_grid_view_displays_expected_fields(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC853:To verify that in Grid view, information of tiles are displayed in tabular with the following columns: Project Name,Ingestion Name,Percentage Completed Total document count, Ingested document count, Error document count, Last Modified By, Last Modified atCovered:TC276:To verify that Counts displayed on Tiles on Ingestion home page are correct.TC277:To verify that Ingestion Count on Ingested Home page is displayed correctly
			//
			//
			//* Once an Ingestion is created and redirected to the Ingestion/Home page
			//* Validate the following fields are displayed on the Grid Tile for the ingestion
			//
			//
			//* Project 
			//* Ingestion Name
			//* Ingestion Type
			//* % Completed
			//* Start Date
			//* End Date
			//* Source Docs
			//* Ingested Docs
			//* Error
			//* Last Modified By
			//* Last Modified at
			//* Ingestion Status
			//
			throw new ImplementationException("verify_ingestion_grid_view_displays_expected_fields");
		} else {
			throw new ImplementationException("NOT verify_ingestion_grid_view_displays_expected_fields");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_home_page_displays_default_tile_count$")
	public void verify_ingestion_home_page_displays_default_tile_count(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//1129:To verify that on Ingesion Home page displays default 10 tiles.
			//
			//* Validate default 10 tiles are displayed
			//
			throw new ImplementationException("verify_ingestion_home_page_displays_default_tile_count");
		} else {
			throw new ImplementationException("NOT verify_ingestion_home_page_displays_default_tile_count");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_home_page_is_refreshed$")
	public void verify_ingestion_home_page_is_refreshed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC257:To Verify the reload of the ingestion with the 'Refresh' option.
			//
			//* validate the ingestion tile is refreshed
			//* validate the count on the page is also updated correctly
			//
			throw new ImplementationException("verify_ingestion_home_page_is_refreshed");
		} else {
			throw new ImplementationException("NOT verify_ingestion_home_page_is_refreshed");
		}

	}


	@And("^.*(\\[Not\\] )? select_all_filter_by_options$")
	public void select_all_filter_by_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click filter by dropdown
			//* Select all options displayed
			//
			throw new ImplementationException("select_all_filter_by_options");
		} else {
			throw new ImplementationException("NOT select_all_filter_by_options");
		}

	}


	@And("^.*(\\[Not\\] )? select_sort_by_option$")
	public void select_sort_by_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("select_sort_by_option");
		} else {
			throw new ImplementationException("NOT select_sort_by_option");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_sort_by_works_as_expected$")
	public void verify_sort_by_works_as_expected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC845:To verify that Filter options work with the sort by functionality.Covers:TC846:To verify that Sort by in Grid View.
			//* Validate the different sort by options work as expected
			//
			//
			//* Last Modified Date
			//* Status
			//* Last modified user
			//* Project Name
			//
			throw new ImplementationException("verify_sort_by_works_as_expected");
		} else {
			throw new ImplementationException("NOT verify_sort_by_works_as_expected");
		}

	}

	@When("^.*(\\[Not\\] )? click_grid_view$")
	public void click_grid_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("click_grid_view");
		} else {
			throw new ImplementationException("NOT click_grid_view");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_pagination_exists_on_grid_view$")
	public void verify_pagination_exists_on_grid_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC848:To verify that on Ingesion Home page is having pagination for Grid View only
			//* Validate the grid view displays pagination 
			//
			throw new ImplementationException("verify_pagination_exists_on_grid_view");
		} else {
			throw new ImplementationException("NOT verify_pagination_exists_on_grid_view");
		}

	}


	@When("^.*(\\[Not\\] )? scroll_click_load_more_button$")
	public void scroll_click_load_more_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("scroll_click_load_more_button");
		} else {
			throw new ImplementationException("NOT scroll_click_load_more_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_load_more_button_is_displayed$")
	public void verify_load_more_button_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC1130:To verify that on Ingesion Home page, on scrolling down next 10 tiles are loaded and displayed and with sort options
			//
			//* Validate more tiles are loaded
			//
			throw new ImplementationException("verify_load_more_button_is_displayed");
		} else {
			throw new ImplementationException("NOT verify_load_more_button_is_displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_load_more_button_disappears$")
	public void verify_load_more_button_disappears(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC1133:To verify that "Load More" button enable or disable as per the availabilty of Tiles.validate load more button is no longer displayed when no more tiles are available
			throw new ImplementationException("verify_load_more_button_disappears");
		} else {
			throw new ImplementationException("NOT verify_load_more_button_disappears");
		}

	}

	@When("^.*(\\[Not\\] )? click_copy_option$")
	public void click_copy_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click settings (gear icon) on ingested tile
			//* Click on Copy
			//
			throw new ImplementationException("click_copy_option");
		} else {
			throw new ImplementationException("NOT click_copy_option");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_copy_ingestion_does_not_display_warning_message$")
	public void verify_copy_ingestion_does_not_display_warning_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC2564:To verify that on "Copy Ingestion" once the Configure mapping is matched between Current and Copied Ingestion then Warning message should not displayed.
			//
			//* Once and ingestion has been copied, select the same files previously selected when it was ingested
			//* Clicking on the next button, will not throw a warning for fields not matching
			//
			throw new ImplementationException("verify_copy_ingestion_does_not_display_warning_message");
		} else {
			throw new ImplementationException("NOT verify_copy_ingestion_does_not_display_warning_message");
		}

	}


	@When("^.*(\\[Not\\] )? click_back_button$")
	public void click_back_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* validate source selection and ingestion type section is disabled
			//* Click on the Back button, next to the preview and run button
			//
			throw new ImplementationException("click_back_button");
		} else {
			throw new ImplementationException("NOT click_back_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_back_button_works_as_expected$")
	public void verify_back_button_works_as_expected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC1420:To verify Back button functionality for ingestion wizard
			//
			//* Once the back button has been clicked
			//* Validate Configure Field Mapping section is disabled
			//
			throw new ImplementationException("verify_back_button_works_as_expected");
		} else {
			throw new ImplementationException("NOT verify_back_button_works_as_expected");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_copy_ingestion_displays_warning_message$")
	public void verify_copy_ingestion_displays_warning_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC2565:To verify that on "Copy Ingestion" once the Configure mapping is not matched between Current and Copied Ingestion then Warning message should displayed.
			//* Once you copy the ingestion and are on the Ingestion Wizard page.
			//* Select different files than the one selected prior to copying
			//* Click on the Next button
			//* Validate an error warning with the following message is displayed:
			//* Fields in the selected DAT file do not match with the source fields specified in the existing mappings. Existing mappings will be reset. Do you want to continue?"
			//
			throw new ImplementationException("verify_copy_ingestion_displays_warning_message");
		} else {
			throw new ImplementationException("NOT verify_copy_ingestion_displays_warning_message");
		}

	}

	@And("^.*(\\[Not\\] )? select_valid_email_metadata$")
	public void select_valid_email_metadata(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* After Configure Mapping is enabled
			//* Select options for all Required Source DAT Fields
			//* Select valid Source DAT Field for one additional field
			//* Select Email option in the Field Category option
			//* Select valid option for Destination Field
			//
			throw new ImplementationException("select_valid_email_metadata");
		} else {
			throw new ImplementationException("NOT select_valid_email_metadata");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_with_email_metadata_is_published$")
	public void verify_ingestion_with_email_metadata_is_published(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC10203 Verify Ingestion should published successfully with new Email metadata
			//
			//* Verify ingestion is successfully published and no errors are displayed
			//
			throw new ImplementationException("verify_ingestion_with_email_metadata_is_published");
		} else {
			throw new ImplementationException("NOT verify_ingestion_with_email_metadata_is_published");
		}

	}


	@And("^.*(\\[Not\\] )? on_datasets_home_page$")
	public void on_datasets_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to "/ICE/Datasets" url
			//* Document tiles are displayed
			//
			throw new ImplementationException("on_datasets_home_page");
		} else {
			throw new ImplementationException("NOT on_datasets_home_page");
		}

	}


	@And("^.*(\\[Not\\] )? search_for_dataset$")
	public void search_for_dataset(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Once on the Datasets home page
			//* Search for value set in dataMap.dataset_name
			//* Click Enter key or Click search magnify glass icon
			//
			throw new ImplementationException("search_for_dataset");
		} else {
			throw new ImplementationException("NOT search_for_dataset");
		}

	}


	@When("^.*(\\[Not\\] )? on_doc_view$")
	public void on_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Once a dataset has been searched for
			//* Click on the "View Set in..." action button
			//* Click on the "DocView" option
			//* A Review Mode and list of Emails will be displayed
			//
			throw new ImplementationException("on_doc_view");
		} else {
			throw new ImplementationException("NOT on_doc_view");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_concatenated_values_are_displayed_correctly_in_the_email$")
	public void verify_concatenated_values_are_displayed_correctly_in_the_email(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC10579 Verify concatenated email value should be displayed correctly for CCName and CCAddress fields in Doc ListTC10580 Verify concatenated email value should be displayed correctly for BCCName and BCCAddress fields in Doc ListTC10581 Verify concatenated email value should be displayed correctly for ToName and ToAddress fields in Doc ListTC10582 Verify concatenated email value should be displayed correctly for AuthorName and AuthorAddress fields in Doc List
			//
			//* For the test cases above we are validating conactenation of names and addresses in the emails
			//* We need to look into the Metadata tab that is displayed to the right in the DocView
			//* Scroll down until you see the field needed, eg. "EmailAuthorNameAndAddress" 
			//* In this field we can see, name and email address all together
			//* To validate the test cases, we need to make sure that the Name fields are displayed correctly without the email address
			//* eg. EmailAuthorNameAndAddress displays "sreekanth medarametla (/o=exchangelabs/ou=exchange administrative group (fydibohf23spdlt)/cn=recipients/cn=1255874db783428c8d670e68de56349f-sreekanth m)"
			//* but EmailAuthorName only displays sreekanth medarametla
			//* This example, the test would pass
			//
			throw new ImplementationException("verify_concatenated_values_are_displayed_correctly_in_the_email");
		} else {
			throw new ImplementationException("NOT verify_concatenated_values_are_displayed_correctly_in_the_email");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_valid_email_metadata_option_is_available$")
	public void verify_valid_email_metadata_option_is_available(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC10199 Verify that Ingestion Email Metadata 'EmailToNamesAndAddresses' is availableTC10198 Verify that Ingestion Email Metadata 'EmailToNamesAndAddresses' is availableTC10200 Verify that Ingestion Email Metadata 'EmailAuthorNameAndAddresses' is availableTC10201 Verify that Ingestion Email Metadata 'EmailCCNamesAndAddresses' is availableTC10202 Verify that Ingestion Email Metadata 'EmailBCCNamesAndAddresses' is available
			//* Once Field Cateogry is set to 'Email'
			//* Click the destination field dropdown corresponding to Field Category with Email selected
			//* Validate 'EmailToNamesAndAddresses' option is displayed
			//* Validate 'EmailAuthorNameAndAddresses' option is displayed
			//* Validate 'EmailCCNamesAndAddresses' option is displayed
			//* Validate 'EmailBCCNamesAndAddresses' option is displayed
			//
			throw new ImplementationException("verify_valid_email_metadata_option_is_available");
		} else {
			throw new ImplementationException("NOT verify_valid_email_metadata_option_is_available");
		}

	}
	
} //end


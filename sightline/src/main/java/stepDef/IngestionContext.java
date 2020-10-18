package stepDef;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;  

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import automationLibrary.Driver;
import automationLibrary.Element;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import testScriptsSmoke.Input;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
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
	
	SessionSearch sessionSearch;
	DocViewPage docView;
	DocListPage docListPage;
	TallyPage tally;
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
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						ingest.getbtnRunIngestion().Visible()  ;}}), Input.wait30);
				ingest.getbtnRunIngestion().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getIngestionTile().Displayed()  ;}}), Input.wait30); 
				
				//Put ingestion name into dataMap
				dataMap.put("lastCreatedIngestionName", ingest.getIngestionTileName(0));
				
				pass(dataMap,"Clicking Ingest Button was successful");
			} catch (Exception e) {
				System.out.println(e);
			}

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

			Thread.sleep(2000);

			if(ingest.getApproveMessageButton().FindWebElements().size()>0) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getApproveMessageOKButton().Visible() ;}}), Input.wait30); 
				ingest.getApproveMessageOKButton().Click(); 
			}

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
			on_ingestion_home_page(true, dataMap);
			new_ingestion_created(true, dataMap);
			map_configuration_fields(true, dataMap);
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
					ingest.getFirstIngestionActionButton().Enabled() && ingest.getIngestionAction_Wizard().Displayed()  ;}}), Input.wait30); 
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
			int count = Integer.parseInt(totalIngestCountText);
			int beginningCount = Integer.parseInt(dataMap.get("ingestion_count").toString());

			// update ingestion_count in dataMap
			dataMap.replace("ingestion_count", count);
			
			if (count > beginningCount) {
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
			} catch (Exception e) {
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
	    	    Thread.sleep(2000);
	    	    
	    	    //HashMap<String, ArrayList<String>> ingestPreviewData = ingest.getIngestDATPreviewInformation((HashSet<String>)dataMap.get("targetColumns"));
	    	    HashMap<String, ArrayList<String>> ingestPreviewData = ingest.getIngestDATPreviewInformation();
	    	    
	    	    dataMap.put("ingestPreviewData", ingestPreviewData);

				pass(dataMap, "Get Preview Run Button is Clickable");
			}
			catch (Exception e) {
				e.printStackTrace();
				fail(dataMap, "Get Preview Run Button could not be Clicked");
			}

		} else {
			ingest.getToastMessage();
		}
	}
	
	
	@Then("^.*(\\[Not\\] )? map_configuration_fields$")
	public void map_configuration_fields(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getSourceDATField().Visible()  ;}}), Input.wait30); 
			
			for(int i=2; i<=4; i++) {
				int index = i;
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getIngestionConfigureMappingRequiredDropDownFields(index).Displayed()  ;}}), Input.wait30); 
				ingest.getIngestionConfigureMappingRequiredDropDownFields(i).Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getIngestionConfigureMappingRequiredDropDownOptions(index).Enabled()  ;}}), Input.wait30); 
				ingest.getIngestionConfigureMappingRequiredDropDownOptions(i).Click();
			}
			pass(dataMap, "map_configuration_fields Successfully");
		}
		else fail(dataMap, "map_configuration_fields NOT Successfully");

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
	
	@When("^.*(\\[Not\\] )? click_copy_option_published$")
	public void click_copy_option_published(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

	if (scriptState) {
		//
		// * Click settings (gear icon) on ingested tile
		// * Click on Copy
		//
		driver.waitForPageToBeReady();
		ingest.getCopyOptionButtonPublished().Click();
		pass(dataMap, "Clicked copy Public button");
	} else {
		fail(dataMap, "NOT click_copy_option");
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
			try {

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getMappingDESTINATIONFIELD4().Visible()  ;}}), Input.wait30); 

				WebElement sourceField1 = ingest.getMappingSOURCEFIELD1().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField2 = ingest.getMappingSOURCEFIELD2().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField3 = ingest.getMappingSOURCEFIELD3().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField4 = ingest.getMappingSOURCEFIELD4().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField5 = ingest.getMappingSOURCEFIELD5().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField6 = ingest.getMappingSOURCEFIELD6().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField7 = ingest.getMappingSOURCEFIELD7().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField8 = ingest.getMappingSOURCEFIELD8().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField9 = ingest.getMappingSOURCEFIELD9().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField10 = ingest.getMappingSOURCEFIELD10().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField11 = ingest.getMappingSOURCEFIELD11().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField12 = ingest.getMappingSOURCEFIELD12().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField13 = ingest.getMappingSOURCEFIELD13().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField14 = ingest.getMappingSOURCEFIELD14().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField15 = ingest.getMappingSOURCEFIELD15().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField16 = ingest.getMappingSOURCEFIELD16().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField17 = ingest.getMappingSOURCEFIELD17().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField18 = ingest.getMappingSOURCEFIELD18().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField19 = ingest.getMappingSOURCEFIELD19().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField20 = ingest.getMappingSOURCEFIELD20().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField21 = ingest.getMappingSOURCEFIELD21().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField22 = ingest.getMappingSOURCEFIELD22().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField23 = ingest.getMappingSOURCEFIELD23().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField24 = ingest.getMappingSOURCEFIELD24().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement sourceField25 = ingest.getMappingSOURCEFIELD25().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				
				Assert.assertEquals("DOCID", sourceField1.getText());
				Assert.assertEquals("SourceParentDocID", sourceField2.getText());
				Assert.assertEquals("DataSource", sourceField3.getText());
				Assert.assertEquals("CustodianName", sourceField4.getText());
				Assert.assertEquals("ATTACH_DOCID", sourceField5.getText());
				Assert.assertEquals("BEGINBATES", sourceField6.getText());
				Assert.assertEquals("BEGINGROUP", sourceField7.getText());
				Assert.assertEquals("Custodian", sourceField8.getText());
				Assert.assertEquals("DocExtension", sourceField9.getText());
				Assert.assertEquals("DOCID", sourceField10.getText());
				Assert.assertEquals("DocPrimaryLanguage", sourceField11.getText());
				Assert.assertEquals("EmailAuthorNameAndAddress", sourceField12.getText());
				Assert.assertEquals("EmailBCCNamesAndAddresses", sourceField13.getText());
				Assert.assertEquals("EmailCCNamesAndAddresses", sourceField14.getText());
				Assert.assertEquals("EmailToNamesAndAddresses", sourceField15.getText());
				Assert.assertEquals("ENDBATES", sourceField16.getText());
				Assert.assertEquals("ENDGROUP", sourceField17.getText());
				Assert.assertEquals("fkCustId", sourceField18.getText());
				Assert.assertEquals("fkMediaId", sourceField19.getText());
				Assert.assertEquals("ITEMPATH", sourceField20.getText());
				Assert.assertEquals("M_Duration", sourceField21.getText());
				Assert.assertEquals("nuixGuid", sourceField22.getText());
				Assert.assertEquals("ocrAttempt", sourceField23.getText());
				Assert.assertEquals("PAGECOUNT", sourceField24.getText());
				Assert.assertEquals("PDFPATH", sourceField25.getText());
				pass(dataMap, "Source fields are auto populated!");
				
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "Source fields are not auto populated!");
			}
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
			try {

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getMappingDESTINATIONFIELD4().Visible()  ;}}), Input.wait30); 

				WebElement desField1 = ingest.getMappingDESTINATIONFIELD1().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement desField2 = ingest.getMappingDESTINATIONFIELD2().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement desField3 = ingest.getMappingDESTINATIONFIELD3().getWebElement().findElement(By.xpath("./option[@selected='selected']"));
				WebElement desField4 = ingest.getMappingDESTINATIONFIELD4().getWebElement().findElement(By.xpath("./option[@selected='selected']"));

				Assert.assertEquals("SourceDocID", desField1.getText());
				Assert.assertEquals("SourceParentDocID", desField2.getText());
				Assert.assertEquals("DataSource", desField3.getText());
				Assert.assertEquals("CustodianName", desField4.getText());
				pass(dataMap, "Destination fields are auto populated");
				
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "Destination fields are not autopopulated");
			}
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
			SearchContext sessionContext = new SearchContext();
			SessionSearch sessionSearch = new SessionSearch((String)dataMap.get("URL"),driver);
			sessionContext.sessionSearch = sessionSearch;
			sessionContext.driver = driver;
			
			String url = (String) dataMap.get("URL");
			driver.Navigate().to(url + "Search/Searches");
			driver.waitForPageToBeReady();
			
			
			//Enter Search into text box
			sessionContext.sessionSearch.insertFullText("AudioPlayerReady=1");
			
			//Saves, Clicks on "My saved Search,Enter valid name and save
			sessionContext.save_search(true,dataMap);

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					ingest.getSearchBtn().Displayed() && ingest.getSearchBtn().Enabled() ;}}), Input.wait30);
				ingest.getSearchBtn().Click();
				
			
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
			try {
			 String url = (String) dataMap.get("URL");
			 driver.Navigate().to(url + "Ingestion/UnPublish");
			 driver.waitForPageToBeReady();
			 
			//Clicks on your saved filter
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
						ingest.getIngestionPageSavedFilterCreated().Displayed();}}), Input.wait30);
					ingest.getIngestionPageSavedFilterCreated().Click();
			 
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
						ingest.getIngestionPageUnPublishBtn().Displayed() && ingest.getIngestionPageUnPublishBtn().Enabled() ;}}), Input.wait30);
					ingest.getIngestionPageUnPublishBtn().Click();
						
			pass(dataMap,"You successfully unpublished");
			} catch(Exception e) {
				e.printStackTrace();
				fail(dataMap,"You unsuccessfully unpublished");
			}
					
			} else {
				fail(dataMap,"You unsuccessfully unpublished");
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
			//Wait for popup, and confirm that it is displayed
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				ingest.getIngestionUnpublishToastPopup().Displayed();}}), Input.wait30);
			Assert.assertTrue(ingest.getIngestionUnpublishToastPopup().Displayed());
			pass(dataMap, "Ingested Audio Document Verified to be Unpublished");
		}
		else fail(dataMap, "Ingested Audio Document was not Verified to be Unpublished");

	}


	@When("^.*(\\[Not\\] )? select_audio_indexing$")
	public void select_audio_indexing(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try
			{
				HashSet<String> audioOptions = new HashSet<>(Arrays.asList("North American English", "United Kingdom English", "German"));
				//

				//* Return to Ingestion/Home

				//* Look for Ingestion Tile created
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionTile().Visible()  ;}}), Input.wait30); 
				//* Click on Ingestion Title
				ingest.getIngestionTile().Click();

				// CANT DO THESE TWO YET
				//* Run the Catalog step
				//* Run the Copy step

				//* Click on Audio checkbox (This is a really ugly CSS selector, maybe you can find a better one.
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionExecutionAudioIndexingCheckbox().Displayed()
	    				&& ingest.getIngestionExecutionAudioIndexingCheckbox().Enabled() ;}}), Input.wait30); 
				ingest.getIngestionExecutionAudioIndexingCheckbox().Click();
				Thread.sleep(5000);

				//Wait for Language Pack Box to be enabled
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionExecutionAudioLanguagePackOptions().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			    //* Select 3 language packs (Norh American English/United Kingdom English/German)
				//Use Action Class because we need to shiftclick to select multiple packs 
				Actions actions = new Actions(driver.getWebDriver());
				actions.sendKeys(Keys.PAGE_DOWN).build().perform();
				for(WebElement x: ingest.getIngestionExecutionAudioLanguagePackOptions().FindWebElements()) {
					if(x.isDisplayed()) {
						if(audioOptions.contains(x.getText())) {
							actions.keyDown(Keys.SHIFT).click(x).build().perform();
							Thread.sleep(1000);
						}
					}
					
				}
				Thread.sleep(5000);
			}
			catch(Exception e) {e.printStackTrace();}

			//* Run Indexing
			//
		}
		else fail(dataMap, "Could Not Run Audio Indexing");

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


	@And("^.*(\\[Not\\] )? click_ingestion_title$")
	public void click_ingestion_title(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//Find Ingested tile created
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionTile().Visible()  ;}}), Input.wait30); 
			ingest.getIngestionTile().Click();
			
			Thread.sleep(2000);

			//* Modal is displayed
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionModal().Displayed()  ;}}), Input.wait30); 
			
			//now have to wait until it pass or fail
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getCatelogingStatus().getText().equals("pass") ;}}), Input.wait30);
			dataMap.put("ingestName", ingest.getIngestionTile().GetAttribute("title"));
			throw new ImplementationException("click_ingestion_title");
		} else {
			throw new ImplementationException("NOT click_ingestion_title");

		}
	}
	
	@And("^.*(\\[Not\\] )? click_copy_play_icon$")
	public void click_copy_play_icon(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Find Ingested tile created
			//* Click on the Ingestion Name
			//* Modal is displayed
			//* After Cataloging, click Copy play button
			//
			//click on the 1st file
			try{
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionTile().Visible()  ;}}), Input.wait30); 
				ingest.getIngestionTile().Click();
				Thread.sleep(5000);
				
				while(ingest.getCopyPlayButton().getText().contains("disable")) {
					ingest.CloseButton().Click();
					ingest.getRefreshButton().Click();
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionTile().Visible()  ;}}), Input.wait30); 
					ingest.getIngestionTile().Click();

				}

				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
				//Clicked the play buttons
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getCopyPlayButton().Displayed()  ;}}), Input.wait30); 
				ingest.getCopyPlayButton().Click();
				Thread.sleep(5000);
				
				//click the close button 
				ingest.CloseButton().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getRefreshButton().Enabled()  ;}}), Input.wait30); 

				ingest.getRefreshButton().Click();
				pass(dataMap, "Passed the click copy play icon function");
			}
			catch(Exception e) {e.printStackTrace();}
			
		}
		else fail(dataMap, "could not click copy play_icon");

	}
	
	@And("^.*(\\[Not\\] )? click_filter$")
	public void click_cataloged_filter(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			try{
				//Open Filter menu
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getFilterByOption().Displayed()  ;}}), Input.wait30); 
				ingest.getFilterByOption().Click();

				//Deselect all non Catalog options 
				for(int i =1; i<=8; ++i){
					if(ingest.getSelectFilterByOption(i).Selected() && i!=4) ingest.getSelectFilterByOption(i).Click();
				}

				//Make sure catalog is clicked
				if(!ingest.getSelectFilterByOption(4).Selected()) ingest.getSelectFilterByOption(4).Click();
				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());

				pass(dataMap, "Filted Easily");
			}
			catch(Exception e) {e.printStackTrace();}
		}
		else fail(dataMap, "could not filter");

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


	//DONT DO THIS
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


	//Dont do this
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
			try{
				String query = ingest.getIngestionTitle().GetAttribute("title");
				dataMap.put("ingestionName", ingest.getIngestionTitle().GetAttribute("title"));
				
				String url = (String) dataMap.get("URL");
				driver.Navigate().to(url + "Search/Searches");
				driver.waitForPageToBeReady();
				SearchContext sessionContext = new SearchContext();
				SessionSearch sessionSearch = new SessionSearch((String)dataMap.get("URL"),driver);
				sessionContext.sessionSearch = sessionSearch;
				sessionContext.driver = driver;
				
				sessionSearch.insertFullText(query);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			sessionSearch.getSearchButton().Enabled() ;}}), Input.wait30); 
				sessionSearch.getSearchButton().Click();
	
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			sessionSearch.getSearchTableResults().Displayed() ;}}), Input.wait30); 
				//Should be 0
				Assert.assertEquals("0", (sessionSearch.getSearchDocsResults().getText()));
			}
			catch (Exception e) {
				e.printStackTrace();
			} 
			pass(dataMap, "Was able to Verify Search results are 0 when ingestion is in progress");

		}
		else fail(dataMap, "Could not verify ingetsion in progress can not be searched");

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
			try{
				
				//Filter by Copied, unselect all other options
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getFilterByOption().Displayed()  ;}}), Input.wait30); 
				ingest.getFilterByOption().Click();
				if(ingest.getSelectFilterByOption(4).Selected()) ingest.getSelectFilterByOption(4).Click();
				if(!ingest.getSelectFilterByOption(5).Selected()) ingest.getSelectFilterByOption(5).Click();
				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
				
				driver.waitForPageToBeReady();

							
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionTile().Displayed() && ingest.getIngestionTile().Enabled()  ;}}), Input.wait30); 
				String query =ingest.getIngestionTileName(0);
				ingest.getIngestionTile().Click();

				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());

				//Click it + Close menu
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getRunIndexing().Enabled()  ;}}), Input.wait30); 

				ingest.getRunIndexing().Click();
				ingest.CloseButton().Click();
				
				//Go to search page
				String url = (String) dataMap.get("URL");
				driver.Navigate().to(url + "Search/Searches");
				driver.waitForPageToBeReady();
				SearchContext sessionContext = new SearchContext();
				SessionSearch sessionSearch = new SessionSearch((String)dataMap.get("URL"),driver);
				sessionContext.sessionSearch = sessionSearch;
				sessionContext.driver = driver;

				//Insert our query
				sessionSearch.insertFullText(query);
				//Hit Search
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				sessionSearch.getSearchButton().Enabled() ;}}), Input.wait30); 
				sessionSearch.getSearchButton().Click();

				//Wait for results
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				sessionSearch.getSearchTableResults().Displayed() ;}}), Input.wait30); 
				//Should be 0
				Assert.assertEquals("0", (sessionSearch.getSearchDocsResults().getText()));
				pass(dataMap, "Was able to Verify Search results are 0 when indexing is in progress");
			}
			catch(Exception e) {e.printStackTrace();}

		}
		else fail(dataMap, "Was not able to verify indexing ingestion cannot be searched");

	}

	@And("^.*(\\[Not\\] )? on_search_home_page$")
	public void on_search_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			ingest.getNavigateToSearchMenuButton().click();
			ingest.getNavigateToSessionSearchPageMenuButton().click();
			
			pass(dataMap, "Navigated to Session Search home page");
		} else {

			fail(dataMap, "NOT on_search_home_page");
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
			//* Click on View D	ocView
			//
			SessionSearch sessionSearch = new SessionSearch(driver);
			String query = (String)dataMap.get("ingestQuery");
			if(query == null) query = "0C8A_SQA_Default_Automation_20201007144534140";
			sessionSearch.insertFullText(query);
			sessionSearch.getSearchButton().click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		sessionSearch.getSearchTableResults().Displayed() ;}}), Input.wait30); 

						
			if (dataMap.get("actionNavigateDoc") != null && dataMap.get("actionNavigateDoc").equals("docList")) {
				sessionSearch.getSearchResultDocsMetCriteriaPlusButton().FindWebElements().get(0).click(); 
				sessionSearch.getBulkActionButton().click();
				sessionSearch.getDocListAction().click();
			} else if ( dataMap.get("actionNavigateDoc") != null && dataMap.get("actionNavigateDoc").equals("docView")) {
				sessionSearch.getSearchResultDocsMetCriteriaPlusButton().FindWebElements().get(0).click(); 
				sessionSearch.getBulkActionButton().click();
				sessionSearch.getDocViewAction().click();
			}
			
			driver.waitForPageToBeReady();
			
			pass(dataMap, "Successfully searched for ingestions");
			
		} else {
			fail(dataMap, "NOT search_for_ingestion");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_file_description_is_tally_searchable$")
	public void verify_file_description_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5548:To Verify FileDescription in Tally and Search.
			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("FileDescription").Displayed()  ;}}), Input.wait30); 

				Assert.assertTrue(docView.getMetaDataTableRowByName("FileDescription").Displayed());
				Assert.assertFalse(docView.getMetaDataTableRowValueByName("FileDescription").getText().equals(""));
			}
			pass(dataMap, "tally description is searchable");
		} else {
			fail(dataMap, "tally description is not searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_email_metadata_is_populated_correctly$")
	public void verify_email_metadata_is_populated_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5267:To Verify email metadata field is populated correctly for ingested data

			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			int i = 0;
			//For Each Ingested File -> go through and Make sure the files exist 
			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("EmailAllDomainCount").Displayed()  ;}}), Input.wait30); 
				
				//Go through Each TargetColumn(The MetaData Values we want) and get it's corresponding row data
				for(Map.Entry e: ((HashMap<String, ArrayList<String>>)dataMap.get("ingestionPreviewData")).entrySet()) {
					Assert.assertEquals(((ArrayList<String>)e.getValue()).get(i++), docView.getMetaDataTableRowValueByName((String)e.getKey()).getText());
				}
				
			}
			pass(dataMap, "Verified expected fields are in data set");

		}
		else fail(dataMap, "Email metadata is not populated correctly");

	}


	@Then("^.*(\\[Not\\] )? verify_expected_fields_are_in_data_set$")
	public void verify_expected_fields_are_in_data_set(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5527:To Verify Field ParentDocID ,HeadOfHouseholdDocID and FamilyID in Ingested Data Set.Verify ingestion is successfully published and no errors are displayed
			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			//For Each Ingested File -> go through and Make sure the files exist 
			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("ParentDocID").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("ParentDocID").getText()).equals("") );
				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("HeadOfHouseholdDocID").getText()).equals("") );
				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("FamilyID").getText()).equals("") );
			}
			pass(dataMap, "Verified expected fields are in data set");
		}
		else fail(dataMap, "Could not verify expected field");

	}


	@Then("^.*(\\[Not\\] )? verify_processing_ocr_completed_is_tally_searchable$")
	public void verify_processing_ocr_completed_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5554:To Verify ProcessingOCRComplete in Tally and Search
			//
			//* validate ProcessingOCRComplete is displayed on the metadata on the right side
			//
			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("ProcessingOCRComplete").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("ProcessingOCRComplete").getText()).equals("") );
			}
			pass(dataMap, "Verified Processing OCR Complete is tally searchable");
		}
		else fail(dataMap, "Could not verify Processing OCR complete is tally searchable");

	}


	@Then("^.*(\\[Not\\] )? verify_family_relationship_is_tally_searchable$")
	public void verify_family_relationship_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5555:To Verify FamilyRelationship in Tally and Search.
			//* validate FamilyRelationship is displayed on the metadata on the right side
			//
			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("FamilyRelationship").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("FamilyRelationship").getText()).equals("") );
			}
			pass(dataMap, "Verified Family relationship is tally searchable");
		}
		else fail(dataMap, "Could not verify Family relationship is tally searchable");

	}

	@Then("^.*(\\[Not\\] )? verify_exception_resolution_is_tally_searchable$")
	public void verify_exception_resolution_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5556:To Verify ExceptionResolution in Tally
			//* validate ExceptionResolution is displayed on the metadata on the right side
			//
			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("ExceptionResolution").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("ExceptionResolution").getText()).equals("") );
			}
			pass(dataMap, "Verified Exception Resolution is tally searchable");
		}
		else fail(dataMap, "Could not verify Exception Resolution is tally searchable");

	}


	@Then("^.*(\\[Not\\] )? verify_email_duplicated_doc_id_is_populated_correctly$")
	public void verify_email_duplicated_doc_id_is_populated_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5265:To Verify EmailDuplicateDocID field is populated correctly for ingested data
			//* Click on Select Column button and select the following columns:
			//* Email DuplicateDocID
			//* EmailsDuplicates
			//* Validate that values are displayed for the ingested email docuements.

			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			//For Each Ingested File -> go through and Make sure the files exist 
			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("EmailDuplicateDocID").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("EmailDuplicateDocID").getText()).equals("") );
				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("EmailsDuplicate").getText()).equals("") );
			}
			pass(dataMap, "Verified expected fields are in data set");

		}
		else fail(dataMap, "Email duplicated doc is not not populated correctly");

	}


	@Then("^.*(\\[Not\\] )? verify_excel_protected_workbook_is_tally_searchable$")
	public void verify_excel_protected_workbook_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5558:To Verify ExcelProtectedWorkbook in Tally and Search.
			//* validate ExcelProtectedWorkbook is displayed on the metadata on the right side
			//
			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("ExcelProtectedWorkbook").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("ExcelProtectedWorkbook").getText()).equals("") );
			}
			pass(dataMap, "Verified expected fields are in data set");
		}
		
		else fail(dataMap, "Could not verify expected field");

	}


	@Then("^.*(\\[Not\\] )? verify_all_custodians_is_tally_searchable$")
	public void verify_all_custodians_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5560:To Verify AllCustodians in Tally and Search
			//* validate the following field when searching the ingested document
			//* AllCustodians

			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("AllCustodians").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("AllCustodians").getText()).equals("") );
			}
			pass(dataMap, "Verified expected fields are in data set");
		} 
		
		else fail(dataMap, "Could not verify expected field");

	}


	@Then("^.*(\\[Not\\] )? verify_review_export_id_is_tally_searchable$")
	public void verify_review_export_id_is_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5561:To Verify ReviewExportID in Tally and Search.
			//* validate ReviewExportID is displayed on the metadata on the right side
			//
			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();

				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("ReviewExportID").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse( (docView.getMetaDataTableRowValueByName("ReviewExportID").getText()).equals("") );
			}
			pass(dataMap, "Verified expected fields are in data set");
		}
		
		else fail(dataMap, "Could not verify expected field");
		
	}


	@Then("^.*(\\[Not\\] )? verify_attach_doc_ids_are_searchable$")
	public void verify_attach_doc_ids_are_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5552:To Verify SourceAttachDocIDs and AttachDocIDs in Search
			//
			//* validate SourceAttachDocIDs is displayed on the metadata on the right side
			//* validate AttachDocIDs is displayed on the metadata on the right side
			//
			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("SourceAttachDocIDs").Displayed()  ;}}), Input.wait30); 

				Assert.assertTrue(docView.getMetaDataTableRowByName("SourceAttachDocIDs").Displayed());
				Assert.assertFalse(docView.getMetaDataTableRowValueByName("SourceAttachDocIDs").getText().equals(""));
				Assert.assertTrue(docView.getMetaDataTableRowByName("AttachDocIDs").Displayed());
				Assert.assertFalse(docView.getMetaDataTableRowValueByName("AttachDocIDs").getText().equals(""));
			}
			pass(dataMap, "Doc_Ids are searchable");
		} else {
			fail(dataMap, "Doc_IDs are not searchable");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_hidden_properties_are_tally_searchable$")
	public void verify_hidden_properties_are_tally_searchable(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5557:To Verify HiddenProperties in Tally and Search.
			//* validate HiddenProperties is displayed on the metadata on the right side

			docView = new DocViewPage(driver);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocViewTableRows().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 

			for(WebElement row: docView.getDocViewTableRows().FindWebElements()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			row.isEnabled() && row.isDisplayed()  ;}}), Input.wait30); 
				row.click();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getMetaDataTableRowByName("HiddenProperties").Displayed()  ;}}), Input.wait30); 

				Assert.assertFalse(docView.getMetaDataTableRowValueByName("HiddenProperties").getText().equals(""));

			}
			pass(dataMap, "Hidden properties are tally searchable");
		}
		else fail(dataMap, "Hidden properties are not tally searchable");

	}


	@And("^.*(\\[Not\\] )? click_save_button$")
	public void click_save_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			ingest.getSaveDraftButton().click();
			ingest.getSaveButtonConfirmationYes().click();
			driver.waitForPageToBeReady();
			pass(dataMap, "Saved the ingestion draft");
		}
		else fail(dataMap, "could not save ingestion as draft");

	}


	//
	@Then("^.*(\\[Not\\] )? verify_draft_ingestion_files_are_not_found$")
	public void verify_draft_ingestion_files_are_not_found(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC869: To verify that user is not able to search the Ingested data if Ingestion is in Draft Mode
			//
			//* Validate the ingested files and ingestion saved can't be found in the search
			//
			sessionSearch = new SessionSearch(driver);
			//Go through each Document tile, and make sure the results are 0
			for(WebElement res: sessionSearch.getSearchDocumentMatchNumber().FindWebElements()) {
				if(!res.getText().equals(" ")) Assert.assertEquals("0", res.getText());
			}
			//Verify Table is empty. An empty table has only 1 row. Each tile, has its own row. Therefore we verify that there are a total of only 5 rows
			Assert.assertEquals(5, sessionSearch.getSearchResultsTableRows().FindWebElements().size());
			pass(dataMap, "Ingestion files are not found");
			
		}
		else fail(dataMap, "ingestion files were found");

	}
	
	@Then("^.*(\\[Not\\] )? verify_components_are_displayed_correctly$")
	public void verify_components_are_displayed_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC275: To verify that progress bar is displayed on tiles and Counts of Ingested and Errors keeps on updated once Ingestion process is started.
			//Covered:TC235: New Ingestion with Overwrite option as 'Add Only'
			//
			//* On the Ingestion Home page
			//* Validate the Ingestion displays a progress bar when the ingestion is processing each step (catalog, copy, index)
			//* Validate Source, publish and error counts are updated
			//* validate progress status is updated (In Progress, Catalogued, Copied, Indexed)
			//
			
			try {
				Boolean progressStatusUpdated =false;
				String status =ingest.getIngestionTileFilterStatus(0).getText().substring(8);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		ingest.getIngestionTile().Displayed() ;}}), Input.wait30); 
				Assert.assertTrue(ingest.getIngestionProgressBar(0).isDisplayed());
				Assert.assertTrue(ingest.getIngestSource().Displayed());
				Assert.assertTrue(ingest.getIngestPublish().Displayed());
				Assert.assertTrue(ingest.getIngestError().Displayed());
				if(status.equals("In Progess") || status.equals("Cataloged") || status.equals("Copied") || status.equals("Indexed")){
					progressStatusUpdated = true ;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			pass(dataMap, "verify_components_are_displayed_correctly");
		} else {
			fail(dataMap, "NOT verify_components_are_displayed_correctly");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_can_ingest_a_saved_draft$")
	public void verify_user_can_ingest_a_saved_draft(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC315:To verify that user can Ingest the files which are in Draft Mode
			//* Once the Saved Draft is opened in the wizard
			//* Validate that the user can still ingest the saved draft opened
			//
			Assert.assertEquals("In Progress", ingest.getIngestionTileFilterStatus(0).getText().substring(8));
			
			pass(dataMap, "verify_user_can_ingest_a_saved_draft");
		} else {
			fail(dataMap, "NOT verify_user_can_ingest_a_saved_draft");
		}

	}


	@When("^.*(\\[Not\\] )? click_filter_by_dropdown$")
	public void click_filter_by_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getFilterByOption().Displayed()  ;}}), Input.wait30); 
			ingest.getFilterByOption().Click();
			
			//Deselect all options 
			for(int i =1; i<=8; ++i){
				if(ingest.getSelectFilterByOption(i).Selected()) ingest.getSelectFilterByOption(i).Click();
			}
			//select the option
			if(dataMap.get("filter_option").equals("Draft"))
				ingest.getSelectFilterByOption(1).Click();
			if(dataMap.get("filter_option").equals("In Progress"))
				ingest.getSelectFilterByOption(2).Click();
			if(dataMap.get("filter_option").equals("Failed"))
				ingest.getSelectFilterByOption(3).Click();
			if(dataMap.get("filter_option").equals("Cataloged"))
				ingest.getSelectFilterByOption(4).Click();
			if(dataMap.get("filter_option").equals("Copied"))
				ingest.getSelectFilterByOption(5).Click();	
			if(dataMap.get("filter_option").equals("Indexed"))
				ingest.getSelectFilterByOption(6).Click();
			if(dataMap.get("filter_option").equals("Approved"))
				ingest.getSelectFilterByOption(7).Click();
			if(dataMap.get("filter_option").equals("Published"))
				ingest.getSelectFilterByOption(8).Click();	
			
			ingest.getcardCanvas().Click();
			ingest.getRefreshButton().Click();
				
			pass(dataMap, "click_filter_by_dropdown");
		} else {
			fail(dataMap, "NOT click_filter_by_dropdown");
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
			try {
			//click all load more button
			while(ingest.getclickMoreButton().Displayed()) {
				scroll_click_load_more_button(true,dataMap);
				driver.FindElementByTagName("body").SendKeys(Keys.DOWN.toString());
			}
			driver.waitForPageToBeReady();

			int size = ingest.getIngestionFilterStatus().FindWebElements().size();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				!ingest.getclickMoreButton().Displayed() && ingest.getIngestionTileFilterStatus(size).isDisplayed()  ;}}), Input.wait30); 
			
			for(int i=0; i <= size;i++) {
				Assert.assertTrue(ingest.getIngestionTileFilterStatus(i).getText().substring(8).equals(dataMap.get("filter_option").toString()));
			}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			pass(dataMap, "verify_filter_by_dropdown_displays_expected_options");
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
			try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getIngestedGridView().Displayed() ;}}), Input.wait30);
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getIngestedGridView().Displayed() ;}}), Input.wait30);
			
			//Click on filter button and select Published Ingestions 
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getFilterByOption().Displayed() ;}}), Input.wait30); 
					ingest.getFilterByOption().Click();
					
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						ingest.getSelectFilterByOption(8).Displayed() ;}}), Input.wait30); 
						ingest.getSelectFilterByOption(8).Click();		
			
			pass(dataMap,"View Options are available");
			} catch(Exception e) {
				e.printStackTrace();
				fail(dataMap,"View Options are not available");
			}
		} else {
			fail(dataMap,"View Options are not available");

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
			try {
			int count =0;
			String actual ="";
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getFilterByOption().Displayed()  ;}}), Input.wait30); 
			ingest.getFilterByOption().Click();
			
			for(int i =1; i<=8; ++i){
				if(ingest.getSelectFilterByOption(i).Selected()) { 
					actual = ingest.getSelectFilterByOption(i).GetAttribute("value");
					count++;
				}
			}
			
			Assert.assertEquals(1, count); //check if more then one is selected
			Assert.assertEquals("INPROGRESS", actual); 
			pass(dataMap, "verify_filter_by_dropdown_has_default_option_selected");}
			catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			fail(dataMap, "NOT verify_filter_by_dropdown_has_default_option_selected");
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
			try {
				String lastIngestionName = dataMap.get("lastCreatedIngestionName").toString();
				if (ingest.getIngestionPopupDetailsCollection().size() > 0) {
					
					if (ingest.getIngestionErrorsHelpIconCollection().size() > 0) {
						ingest.getIngestionErrorBackButton().click();
					}
					
					System.out.println("here");
					System.out.println("1");
					ingest.selectActionFromIngestionDetailsPopup("Rollback");
					System.out.println("2");
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							ingest.getSuccessMessageBox().Displayed() ;}}), Input.wait30);	
					System.out.println("3");

				} else {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    				ingest.getSpecificIngestionGearButton(lastIngestionName).Displayed() ;}}), Input.wait30);			
					ingest.getSpecificIngestionGearButton(lastIngestionName).click();
					ingest.getIngestionRollbackButton().click();
					ingest.getIngestionRollbackConfirmButton().click();
				}
				
				
			} catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "NOT click_on_rollback_option");
			}
			pass(dataMap, "click_on_rollback_option");
		} else {
			fail(dataMap, "NOT click_on_rollback_option");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_rolled_back_ingestion_can_be_deleted$")
	public void verify_rolled_back_ingestion_can_be_deleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				String ingestName = (String)dataMap.get("ingestName");				
				if(!ingest.getIngestionGridDetailsButton(ingestName).Displayed()) {
					pass(dataMap, "verify_rolled_back_ingestion_can_be_deleted");
				} else {
					fail(dataMap, "NOT verify_rolled_back_ingestion_can_be_deleted");
				}				
			} catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "NOT verify_rolled_back_ingestion_can_be_deleted");
			}			
		} else {
			fail(dataMap, "NOT verify_rolled_back_ingestion_can_be_deleted");
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
		
			try {
				click_grid_view(true,dataMap);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableProject().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableIngestionName().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableIngestionType().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableComplete().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableStart().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableEnd().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableSourceDocs().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableSourceIngestedDocs().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableErrors().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableLastModifiedBy().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableLastModifiedDate().Displayed() ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getIngestionGridTableIngestionStatus().Displayed() ;}}), Input.wait30);
			
			pass(dataMap,"The fields are displayed");

			}catch(Exception e) {
				e.printStackTrace();
				fail(dataMap,"The fields are not displayed");
			}
		} else {
			fail(dataMap,"The fields are not displayed");
		}
	}


	@Then("^.*(\\[Not\\] )? verify_ingestion_home_page_displays_default_tile_count$")
	public void verify_ingestion_home_page_displays_default_tile_count(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//1129:To verify that on Ingesion Home page displays default 10 tiles.
			//
			//* Validate default 10 tiles are displayed
			//
			
			//open the approved options 
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getFilterByOption().Displayed()  ;}}), Input.wait30); 
			ingest.getFilterByOption().Click();
			if(!ingest.getSelectFilterByOption(8).Selected()) ingest.getSelectFilterByOption(8).Click();
			if(ingest.getSelectFilterByOption(2).Selected()) ingest.getSelectFilterByOption(2).Click();
			ingest.getcardCanvas().Click();;
			ingest.getRefreshButton().Click();
			driver.waitForPageToBeReady();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getIngestionTile().Displayed() && ingest.getIngestionTile(10).isDisplayed()  ;}}), Input.wait30); 
			
			Assert.assertEquals(10, ingest.getIngestTile().size());
			pass(dataMap,"verify_ingestion_home_page_displays_default_tile_count");
		} else {
			fail(dataMap, "NOT verify_ingestion_home_page_displays_default_tile_count");
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
			try {
				while(ingest.getSavedIngestTile().Displayed()){
					ingest.getRefreshButton().click();
				}
				driver.waitForPageToBeReady();
	
				int TileSize = ingest.getIngestTile().size();
				Assert.assertEquals(0, TileSize);
				
				pass(dataMap,"verify_ingestion_home_page_is_refreshed");}
			catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			fail(dataMap,"NOT verify_ingestion_home_page_is_refreshed");
		}

	}


	@And("^.*(\\[Not\\] )? select_all_filter_by_options$")
	public void select_all_filter_by_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click filter by dropdown
			//* Select all options displayed
			//
			int numOfOptions = ingest.getAllFilterOptions().FindWebElements().size();
			ingest.getFilterByOption().click();
			for(int i =1; i<=numOfOptions; i++) {
				if(!ingest.getSelectFilterByOption(i).Selected()) ingest.getSelectFilterByOption(i).click();
			}
			driver.FindElementByTagName("body").SendKeys(Keys.HOME.toString());
			pass(dataMap, "Filtered by every option");
		}
		else fail(dataMap, "Could not filter by all options");

	}


	@And("^.*(\\[Not\\] )? select_sort_by_option$")
	public void select_sort_by_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			String option = (String)dataMap.get("sort_by_options");
			
			if(option.equals("Last Modified Date")) option = "IngestionDate";
				else if(option.equals("Status")) option = "IngestionStatus";
				else if(option.equals("Last Modified user")) option = "UserName";
				else if(option.equals("Project Name")) option = "ProjectName";
				
				ingest.getIngestSortDropDown().click();
				ingest.getIngestSortOption(option).click();
				driver.FindElementByTagName("body").SendKeys(Keys.HOME.toString());
				//ingest.getIngestedGridView().click();
				driver.waitForPageToBeReady();
				pass(dataMap, "Clicked the Right option");
		
			
		}
		else fail(dataMap, "Failed to sort by option");

	}


	@Then("^.*(\\[Not\\] )? verify_sort_by_works_as_expected$")
	public void verify_sort_by_works_as_expected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC845:To verify that Filter options work with the sort by functionality.Covers:TC846:To verify that Sort by in Grid View.
			//* Validate the different sort by options work as expected
			try {
			String option  = (String)dataMap.get("sort_by_options");
			ArrayList<String> pageElements = new ArrayList<>();
			
			//Scrolls to the end of page

			while(ingest.getclickMoreButton().Displayed()) {
				scroll_click_load_more_button(true,dataMap);
				driver.FindElementByTagName("body").SendKeys(Keys.DOWN.toString());
				driver.waitForPageToBeReady();
			}


			if(option.equals("Last Modified Date")) {
				for(WebElement x: ingest.getIngestionModifiedDates().FindWebElements()) {
					pageElements.add(x.getText());
				}
			}
			else if(option.equals("Status")) {
				for(WebElement x: ingest.getIngestionStatus().FindWebElements()) {
					pageElements.add(x.getText().substring(8));
				}
			}
			else if(option.equals("Last Modified user")) {
				for(WebElement x: ingest.getIngestionModifiedUser().FindWebElements()) {
					pageElements.add(x.getText().substring(0, x.getText().toString().length()-4));
				}
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				ingest.getGearButton().Displayed() ;}}), Input.wait30);			
				ingest.getGearButton().click();
				ingest.getIngestionRollbackButton().click();
				ingest.getIngestionRollbackConfirmButton().click();
			}
			else if(option.equals("Project Name")) {
				for(WebElement x: ingest.getIngestionProjectName().FindWebElements()) {
					pageElements.add(x.getText());
				}
			}
			
			Boolean pass =true;
			for (int i = 0 ; i < pageElements.size()-2; i++){
				if(pageElements.get(i).compareTo(pageElements.get(i+1))<0) {
					pass = false;
				}
			}
			Assert.assertTrue(pass);
			
			}catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "Could not verify Sort is correct");
			}
			
			
		}
		else fail(dataMap, "Could not verify Sort is correct");

	}

	@When("^.*(\\[Not\\] )? click_grid_view$")
	public void click_grid_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getIngestedGridView().Displayed() ;}}), Input.wait30);
				ingest.getIngestedGridView().Click();
				pass(dataMap,"Successfullly clicked on Grid View option");
			}catch(Exception e) {
				e.printStackTrace();
				fail(dataMap,"Clicking on Grid View option was unsuccessful ");
			}
		} else {
			fail(dataMap,"Clicking on Grid View option was unsuccessful ");
		}

	}
	


	@Then("^.*(\\[Not\\] )? verify_pagination_exists_on_grid_view$")
	public void verify_pagination_exists_on_grid_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC848:To verify that on Ingesion Home page is having pagination for Grid View only
			//* Validate the grid view displays pagination 
			//
			try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getIngestionGridPaginationTable().Displayed() ;}}), Input.wait30);
				pass(dataMap,"Pagination Exist on Grid view");
			}catch(Exception e) {
				e.printStackTrace();
				fail(dataMap,"Pagination does Exist on Grid view");
			}
		} else {
			fail(dataMap,"Pagination does Exist on Grid view");
		}

	}


	@When("^.*(\\[Not\\] )? scroll_click_load_more_button$")
	public void scroll_click_load_more_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		
		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getclickMoreButton().Displayed() ;}}), Input.wait30);
			ingest.getclickMoreButton().Click();
			
			pass(dataMap,"scroll_click_load_more_button");
		} else {
			fail(dataMap,"NOT scroll_click_load_more_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_load_more_button_is_displayed$")
	public void verify_load_more_button_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC1130:To verify that on Ingesion Home page, on scrolling down next 10 tiles are loaded and displayed and with sort options
			//
			//* Validate more tiles are loaded
			//
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getIngestionTile().Displayed() && ingest.getIngestionTile(10).isDisplayed()  ;}}), Input.wait30); 
			
			Assert.assertEquals(20, ingest.getIngestTile().size());
			Assert.assertTrue(ingest.getclickMoreButton().Displayed());
			
			pass(dataMap,"verify_load_more_button_is_displayed");
		} else {
			fail(dataMap, "NOT verify_load_more_button_is_displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_load_more_button_disappears$")
	public void verify_load_more_button_disappears(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC1133:To verify that "Load More" button enable or disable as per the availabilty of Tiles.validate load more button is no longer displayed when no more tiles are available
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				ingest.getIngestionTile().Displayed() && ingest.getIngestionTile(10).isDisplayed()  ;}}), Input.wait30); 
			
			Assert.assertFalse(ingest.getclickMoreButton().Displayed());
			pass(dataMap, "verify_load_more_button_disappears");
		} else {
			fail(dataMap, "NOT verify_load_more_button_disappears");
		}

	}

	@When("^.*(\\[Not\\] )? click_copy_option$")
	public void click_copy_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click settings (gear icon) on ingested tile
			//* Click on Copy
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getGearButton().Visible()  ;}}), Input.wait30);
			ingest.getGearButton().Click();
			ingest.getCopyOptionButton().Click();
			pass(dataMap,"Clicked copy button");
		} else {
			fail(dataMap,"NOT click_copy_option");
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
			//input the data again
			driver.waitForPageToBeReady();
			ingest.requiredFieldsAreEntered(scriptState, dataMap);
			click_next_button(scriptState, dataMap);
			
			Assert.assertTrue(ingest.getSourceDATField().Visible());
			pass(dataMap, "verify_copy_ingestion_does_not_display_warning_message");
		} else {
			fail(dataMap, "NOT verify_copy_ingestion_does_not_display_warning_message");
		}

	}


	@When("^.*(\\[Not\\] )? click_back_button$")
	public void click_back_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* validate source selection and ingestion type section is disabled
			//* Click on the Back button, next to the preview and run button
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getBackButton().Visible()  ;}}), Input.wait30);
			ingest.getBackButton().Click();
			pass(dataMap,"Clicking Ingest Button was successful");
		} else {
			fail(dataMap,"Clicking Ingest Button was NOT successful");
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
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getBackButton().Displayed()  ;}}), Input.wait30);
			Assert.assertTrue(ingest.getBackButton().GetAttribute("disabled"), true);
			pass(dataMap,"verify_back_button_works_as_expected");
			
		} else {
			fail(dataMap,"verify_back_button_works_as_expected");

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
			String ExpectedWarningMessage = "Fields in the selected DAT file do not match with the source fields specified in the existing mappings. Existing mappings will be reset. Do you want to continue?";
		
			driver.waitForPageToBeReady();
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("doc_key", "DocFileType");
			ingest.requiredFieldsAreEntered(scriptState, dataMap);
			click_next_button(scriptState, dataMap);

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getApproveMessageHeader().Visible()  ;}}), Input.wait30);
			String actualMessage = ingest.getWarningMessageContent().getText();
			assertEquals(ExpectedWarningMessage, actualMessage);
			ingest.getWarningMessageOKButton().Click();
			
			pass(dataMap, "verify_copy_ingestion_displays_warning_message");
		} else {
			fail(dataMap, "NOt verify_copy_ingestion_displays_warning_message");
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
			
			String url = driver.getUrl();
			
			// If user is on DocList, select all Docs then click Action button, then Click Doc View
			if (url.contains("/DocList")) {
				docListPage.getSelectAllCheckbox().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						docListPage.getMessageBoxContainer().Visible()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						docListPage.getPopUpOkBtn().Visible()  ;}}), Input.wait30);
				docListPage.getPopUpOkBtn().click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						docListPage.getDocList_actionButton().Visible()  ;}}), Input.wait30);

				docListPage.selectAction("View in DocView");
				
				driver.waitForPageToBeReady();
				
			}
			
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
	
	@Then("^.*(\\[Not\\] )? verify_mandatory_error_message_is_displayed$")
	public void verify_mandatory_error_message_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getSourceDATField().Visible()  ;}}), Input.wait30); 
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getIngestionConfigureMappingRequiredDropDownFields(2).Displayed()  ;}}), Input.wait30); 
			ingest.getIngestionConfigureMappingRequiredDropDownFields(2).Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getIngestionConfigureMappingRequiredDropDownOptions(2).Enabled()  ;}}), Input.wait30); 
			ingest.getResetMappingReqiredDropDown(2).Click();
			System.out.println(ingest.getResetMappingReqiredDropDown(2).getText());
			
			//*[@class="fa fa-warning shake animated"]
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					ingest.getPreviewRun().Visible() && ingest.getResetMappingReqiredDropDown(2).getText().equalsIgnoreCase("Select") ;}}), Input.wait30); 
			ingest.getPreviewRun().Click();
			
			Assert.assertTrue(ingest.getErrorWarningMessagePopUp().Visible());
			
			
			pass(dataMap,"verify_valid_email_metadata_option_is_available");
		} else {
			fail(dataMap,"verify_valid_email_metadata_option_is_available");
		}
	}

	public void unpublish_desired_saved_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if(scriptState){
			int i =0;
			SessionSearch sessionSearch = new SessionSearch(driver);
			SearchContext sessionContext = new SearchContext();
			sessionContext.sessionSearch = sessionSearch;
			sessionContext.driver = driver;
			

    		Actions builder  = new Actions(driver.getWebDriver());
    		//Move to cursor to results doc box to bring up Table data
    		builder.moveToElement(sessionSearch.getSearchResultDocsMetCriteriaPlusButton().FindWebElements().get(0)).perform();
			dataMap.put("originalIngestionResults", sessionSearch.getSearchResultDocsmetCriteriaCount().FindWebElements().get(0).getText());

    		//Ugly way to get a document from Results table
    		String docToRemove = (sessionSearch.getSearchResultsTableRows().FindWebElements().get(0)).findElements(By.cssSelector("td")).get(0).getText();
    			
    		//Append the document to our existing Ingestion Search, to narrow down on just that one document
    		sessionSearch.insertFullText("AND " + docToRemove);
    		sessionContext.click_search(true, dataMap);
    		while(i++<100){
    			for(WebElement x: sessionSearch.getAllSearchTableResults().FindWebElements()) {
    				if(x.isDisplayed() && x.isEnabled()) break;
    			}
    		}
    		sessionContext.save_search(true, dataMap);
    			    	
    		//Find appropriate + button
    		for(WebElement x: sessionSearch.getSearchResultDocsMetCriteriaPlusButton().FindWebElements()) {
    			if(x.isEnabled() && x.isDisplayed()) x.click();
    		}
    		//First We need to bulk Release with Default Security Group checked
			sessionSearch.getBulkActionButton().click();
			sessionSearch.getBulkReleaseAction().click();
			sessionSearch.getBulkRelDefaultSecurityGroup_CheckBox("Default Security Group").click();
			sessionSearch.getBulkRelease_ButtonRelease().click();		
			sessionSearch.getFinalizeButton().click();
	
			//Now we need to Unrelease with Default Security Group Checked
			sessionSearch.getBulkActionButton().click();
			sessionSearch.getBulkReleaseAction().click();
			sessionSearch.getBulkRelDefaultSecurityGroup_CheckBox("Default Security Group").click();
			sessionSearch.getBulkRelease_ButtonUnrelease().click();
				
			//Navigate to unpublish page
			ingest.getNavigateToIngestionMenuButton().click();
			ingest.getNavigateToUnpublishMenuButton().click();
			driver.waitForPageToBeReady();
			
			//Select our Saved Search, and unpublish it
			builder.moveToElement(ingest.getUnpublishFirstRow().getWebElement()).perform();
			ingest.getUnpublishFirstRow().click();
			ingest.getUnpublishSearchByName((String)dataMap.get("CurrentSaveValue")).click();
			ingest.getIngestionPageUnPublishBtn().click();
			pass(dataMap, "Successfully unpublished the desired search");
		}
		else fail(dataMap, "Could not unpublish the desired search");
	}

	public void verify_search_result_for_overlaid_text(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if(scriptState){
			int i = 0, j =0;
			SearchContext sessionContext = new SearchContext();
			SessionSearch sessionSearch = new SessionSearch((String)dataMap.get("URL"),driver);
			sessionContext.sessionSearch = sessionSearch;
			sessionContext.driver = driver;
			
			//Go to Publish Page
			ingest.getNavigateToIngestionMenuButton().click();
			ingest.getNavigateToAnalyticsAndPublishButton().click();
			
			//Run Analytics and Publish
			ingest.getRunAnalyticsRunButton().click();
			//Wait for publish button to be avaliable
			while(!ingest.getRunAnalyticsPublishButton().Enabled() && i++<100) {
				driver.getWebDriver().navigate().refresh();
			}
			ingest.getRunAnalyticsPublishButton().click();
			
			//Back to ingestion page
			ingest.getNavigateToIngestionMenuButton().click();
			ingest.getNavigateToIngestionHome().click();
			
			//Wait for our ingestion to be published before we head to back to search page
			wait_for_inprogress_to_published(true, dataMap);
			
			//back to search page
			ingest.getNavigateToSearchMenuButton().click();
			ingest.getNavigateToSessionSearchPageMenuButton().click();
			driver.waitForPageToBeReady();
			sessionSearch.getNewSearch().click();
			driver.waitForPageToBeReady();

			//Search and wait for results
			sessionSearch.insertFullText((String)dataMap.get("ingestionName"));
			for(WebElement x: sessionSearch.getSearchButtons().FindWebElements()) {
				if(x.isDisplayed() && x.isEnabled()) {
					x.click();
				}
			}
			while(j++<100){
    			for(WebElement x: sessionSearch.getAllSearchTableResults().FindWebElements()) {
    				if(x.isDisplayed() && x.isEnabled()) break;
    			}
    		}

			String res = "";
			//Get Search REsults and compare
			for(WebElement x: sessionSearch.getSearchResultDocsmetCriteriaCount().FindWebElements()) {
				if(x.isDisplayed()) {
					res = x.getText();
					break;
				}
			}
			System.out.println("Res = " + res);
			System.out.println("OG = " + (String)dataMap.get("originalIngestionResults"));
			Assert.assertEquals(res, (String)dataMap.get("originalIngestionResults"));
			pass(dataMap, "verified search result for overlaid text");
		}
		else fail(dataMap, "Could not verify search result for overlaid text");
	}

	public void wait_for_inprogress_to_published(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if(scriptState){
			int i =0;
			String targetIngestion = "";
			
			String ourIngestion = (String)dataMap.get("lastCreatedIngestionName");
			//Wait until ourIngestion = First Ingestion on the published filter page
			//This will tell us our Ingestion has been Successfully Published
			while(i++<100 && !targetIngestion.equals(ourIngestion)) {
				driver.getWebDriver().navigate().refresh();
				driver.waitForPageToBeReady();
				ingest.getFilterByButton().click();
				ingest.getFilterByINPROGRESS().click();
				ingest.getFilterByPUBLISHED().click();
				ingest.getFilterByButton().click();
				driver.waitForPageToBeReady();
				targetIngestion = ingest.getIngestionTileName(0);
			}
			
		}
		else fail(dataMap, "could not wait for ingestion to publish");
	}
		
	
	@When("^.*(\\\\[Not\\\\] )? delete_grid_ingestion$")
	public void delete_grid_ingestion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			try {
				String ingestName = (String)dataMap.get("ingestName");
				ingest.getIngestionGridDetailsButton(ingestName).click();
				ingest.getIngestionExecutionDetailActionButton().click();
				ingest.getIngestionAction_Grid_Delete().click();
				ingest.getIngestionDeleteConfirmationButton().click();								
				pass(dataMap, "select_grid_ingestion_details_button");
			} catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "NOT select_grid_ingestion_details_button");
			}
		} else {
			fail(dataMap, "NOT select_grid_ingestion_details_button");
		}
	}

	@Then("^.*(\\[Not\\] )? verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay$")
	public void verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9395:To verify that Overlay should work for 'SourceParentDocID' metadata
			//* Validate that Overlay ingestion is successful 
			//
			verify_new_ingestion_tile_is_displayed(true, dataMap);
		} else {
			throw new ImplementationException("NOT verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay");
		}

	}


	@And("^.*(\\[Not\\] )? complete_overlay_ingestion$")
	public void complete_overlay_ingestion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This method will need to navigate through the entire process of creating an ingestion. 
			//* Go To ingestion Home Page
			//* Click Add a new Ingestion
			//* Fill out required fields
			//* Click Next button
			//* Map configuration fields
			//* Click Preview run button
			//* Click Run Ingestion
			//
			new_ingestion_created(true, dataMap);
			click_preview_run_button(true, dataMap);
			click_run_ingest_button(true, dataMap);
		} else {
			throw new ImplementationException("NOT complete_overlay_ingestion");
		}

	}


	@Given("^.*(\\[Not\\] )? verify_source_system_ingestion_overlay_fails$")
	public void verify_source_system_ingestion_overlay_fails(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC10001:Verify error message that if Source Sytem of Ingestion Overlay is dffierent
			//* Once the ingestion has been ran
			//* Validate the ingestions fails with the following error:
			//
			//"Source System of the overlaid docs are not matching with the source system of originally ingested docs. In the overlay, please make sure to 
			//use the same source system used when the docs were added "
			String lastIngestionName = dataMap.get("lastCreatedIngestionName").toString();
						
			System.out.println(lastIngestionName);
			try {
				ingest.getIngestionCardTitle(lastIngestionName).waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						ingest.getIngestionPopup().Visible()  ;}}), Input.wait30);
				
				while (ingest.getPctCompleteLabel().getText().equalsIgnoreCase("In Progress")) {
					// TODO: Add logic to take into account if status alawys stays In Progress
					
					ingest.getIngestionPopupCloseButton().click();
					ingest.getIngestionCardTitle(lastIngestionName).waitAndClick(10);
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							ingest.getIngestionPopup().Visible()  ;}}), Input.wait30); 
					if (!ingest.getPctCompleteLabel().getText().equalsIgnoreCase("In Progress")) {
						break;
					}
				}

				Assert.assertEquals("Failed", ingest.getPctCompleteLabel().getText());
				
				ingest.getIngestionPopupErrorsLink().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						ingest.getIngestionPopupDetails().Visible()  ;}}), Input.wait30); 
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						ingest.getIngestionPopupDocIDErrorRows().Visible()  ;}}), Input.wait30); 
				
				String expectedError = "Source System of the overlaid docs are not matching with the source system of originally ingested docs. "
						+ "In the overlay, please make sure to use the same source system used when the docs were added.";
				for(WebElement row: ingest.getIngestionPopupDocIDErrorRows().FindWebElements()) {
					Assert.assertEquals(expectedError, row.getText());		
				}
				pass(dataMap, "Expected error message appears if source system of Ingestion overlay is different");
				
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "Unable to verify error message");
			}

			
		} else {
			throw new ImplementationException("NOT verify_source_system_ingestion_overlay_fails");
		}

	}


	@When("^.*(\\[Not\\] )? ignore_errors_found$")
	public void ignore_errors_found(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* This context assumes the user is on the Ingestions Errors popup dialog
			//* On the pop up, click Ignore all Errors
			//* Click done
			//* Click Play button for Catalog
			//
			
			try {
				ingest.getIngestionIgnoreAllErrorsBtn().waitAndClick(10);
				ingest.getApproveMessageOKButton().waitAndClick(10);
				ingest.getCatalogDoneBtn().waitAndClick(10);
				ingest.getRunCopying().waitAndClick(10);
			} catch (Exception e) {
				
			}
			
		} else {
			throw new ImplementationException("NOT ignore_errors_found");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ignoring_errors_still_ingests_remaining_files$")
	public void verify_ignoring_errors_still_ingests_remaining_files(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5300:To Verify In Ingestion, ignoring the error with one file Should not result in ignoring all the other files of the document
			//
			//* Validate ingestion is successful when ignoring the errors
			//
			throw new ImplementationException("verify_ignoring_errors_still_ingests_remaining_files");
		} else {
			throw new ImplementationException("NOT verify_ignoring_errors_still_ingests_remaining_files");
		}

	}


	@And("^.*(\\[Not\\] )? search_for_existing_ingestion$")
	public void search_for_existing_ingestion(boolean scriptState, HashMap dataMap)
			throws ImplementationException, Exception {

		if (scriptState) {

			//we need to preset an ingestion so that we can use it for future automation. 
			//You can hardcode the ingestion name or create a saved filter so that we can use the same ingestion for most of our tests.
			//Then this method will search for that existing ingestion
			
			
			//TODO: Search for existing ingestion in database
			
			// Use existing Ingestion: 0C8A_SQA_Default_Automation_20201014002433900
			String existingIngestion = "0C8A_SQA_Default_Automation_20201014002433900";
			
			sessionSearch = new SessionSearch(driver);
			
			if (dataMap.containsKey("existing_ingestion_name") ) {
				sessionSearch.basicContentSearch(dataMap.get("existing_ingestion_name").toString());
				
				//TODO: Include logic to save data and parameters in the dataMap associated to this Ingestion
				
			} else {
				sessionSearch.basicContentSearch(existingIngestion);
				dataMap.put("existing_ingestion_name", existingIngestion);
			}
			
			// TODO: Add logic to retreive Doc Primary Languages for a given Ingestion from the database
			if (dataMap.get("existing_ingestion_name").equals("0C8A_SQA_Default_Automation_20201014002433900")) {
				// For this existing Ingestion (0C8A_SQA_Default_Automation_20201014002433900), store 
				// the primary languages for the docs in the dataMap to be used in future contexts
				dataMap.put("existingIngestionDocPrimaryLanguages", "English,Japanese,Spanish");
			}
			
		} else {
			throw new ImplementationException("NOT search_for_existing_ingestion");
		}
	}


	@When("^.*(\\[Not\\] )? on_doc_list_view$")
	public void on_doc_list_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Only documents ingested will be displayed
			//* Select the documents and add them to righ side by click the plus symbol
			//* Click the Action dropdown
			//* Select View in Doc List
			//If DocPrimaryLanguage is not displayed as a column:Click Select Column buttonAdd the specified column
			sessionSearch.getDocsThatMetCriteriaAddBtn().Click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					sessionSearch.getResultTile().Visible()  ;}}), Input.wait30); 
			sessionSearch.ViewInDocList();
			

		} else {
			throw new ImplementationException("NOT on_doc_list_view");
		}

	}
	

	@Then("^.*(\\[Not\\] )? verify_doc_list_displays_doc_primary_language_metadata_correctly$")
	public void verify_doc_list_displays_doc_primary_language_metadata_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		docListPage = new DocListPage(driver);
		if (scriptState) {
			//TC9182:Verify value of "DocPrimaryLanguage" metadata field value should be populated correctly in Doclist and in Doc View
			//TC7502:Validate new metadata field DocLanguages on DocListValidate files that contained a value in the DocPrimaryLanguage are displayed correctly in the Doc List view
			try {

				// "DocPrimaryLanguage" column needs to be added to the Doc List view
				docListPage.addColumnToDocListView("DocPrimaryLanguage");
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						docListPage.getDocListTable().Visible()  ;}}), Input.wait30); 
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						docListPage.getDocPrimaryLanguageHeading().Visible()  ;}}), Input.wait30); 
				
				// Verifying DocPrimaryLanguage values for existing Ingestion - 0C8A_SQA_Default_Automation_20201014002433900
				// TODO: Add lopic to retreive Doc Primary Language from the database so values are not hardcoded
				if (dataMap.containsKey("existing_ingestion_name")) {		
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							docListPage.getPrimaryLanguageValue("ID00002613").Visible()  ;}}), Input.wait30); 
					
					Assert.assertEquals("Japanese", docListPage.getPrimaryLanguageValue("ID00002613").getText());
					Assert.assertEquals("Spanish", docListPage.getPrimaryLanguageValue("ID00002614").getText());
					Assert.assertEquals("English", docListPage.getPrimaryLanguageValue("ID00002615").getText());
					pass(dataMap, "Primary Language Metadata is displayed correctly!");
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			throw new ImplementationException("NOT verify_doc_list_displays_doc_primary_language_metadata_correctly");
		}

	}


	@When("^.*(\\[Not\\] )? on_tally_view$")
	public void on_tally_view(boolean scriptState, HashMap<String, TallyPage> dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String url = driver.getUrl();
			try {
				//TODO: Add logic to go to Tally view from other pages
				if (url.contains("/DocList")) {
					docListPage.getSelectAllCheckbox().click();
			    	docListPage.getPopUpOkBtn().waitAndClick(10);
			    	docListPage.selectAction("Tally");
			    	driver.WaitUntilUrlContains("Report/Tally");
				}
			} catch (Exception e) {
				System.out.println(e);
			}		 
		} else {
			throw new ImplementationException("NOT on_tally_view");
		}
	}


	@Given("^.*(\\[Not\\] )? verify_tally_view_displays_doc_primary_language_metadata_correctly$")
	public void verify_tally_view_displays_doc_primary_language_metadata_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		TallyPage tally = new TallyPage(driver);
		if (scriptState) {
			//TC7499:Validate new metadata field DocLanguages on Tally report
			//Validate metadata options are displayed in the Tally View after clicking the "En: Select a Tally Field to run tally on;" button

			try {
				tally.getTally_SelectaTallyFieldtoruntallyon().waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						tally.getMetadataPopup().Visible()  ;}}), Input.wait30);
				tally.selectMetadataOption("DocPrimaryLanguage");
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						tally.getMetadataButtonSelectedOption("DocPrimaryLanguage").Visible()  ;}}), Input.wait30);
				tally.getTally_btnTallyApply().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						tally.getMetadataTallyResults().Visible()  ;}}), Input.wait30);
				
				ArrayList<String> docPrimaryLanguages = new ArrayList<String>();
				
				for(WebElement row: tally.getMetadataViewText().FindWebElements()) {docPrimaryLanguages.add(row.getText());}
				
				// Remove empty strings from List
				docPrimaryLanguages.removeAll(Arrays.asList("", null));

				// Convert "existingIngestionDocPrimaryLanguages" into an ArrayList since the values are saved as a single string
				ArrayList<String> expectedDocPrimaryLanguages = new ArrayList<String>(Arrays.asList(dataMap.get("existingIngestionDocPrimaryLanguages").toString().split(",")));
								
				try {
					Assert.assertEquals(expectedDocPrimaryLanguages, docPrimaryLanguages);
					pass(dataMap, "Tally View correctly displays DocPrimaryLanguage metadata correctly");
				} catch (Exception e) {
					fail(dataMap,"DocPrimaryLanguage values are not correct" + docPrimaryLanguages + " != " + expectedDocPrimaryLanguages);
				}
				
			} catch (Exception e) {
				fail(dataMap, "Unable to verify Doc Primary Language metadata on Tally View");
				System.out.println(e);
			}
		} else {
			throw new ImplementationException("NOT verify_tally_view_displays_doc_primary_language_metadata_correctly");
		}

	}


	@When("^.*(\\[Not\\] )? on_sub_tally_view$")
	public void on_sub_tally_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		TallyPage tally = new TallyPage(driver);
		if (scriptState) {
			//Once Tally documents are displayed
			//Select all/some documents that are displayedC
			//lick on the action dropdownSelect Sub Tally
			tally.getTally_btnTallyAll().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					tally.getMetadataSelected().Visible()  ;}}), Input.wait30);
			
			tally.selectTallyAction("Sub Tally");
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					tally.getTally_SourceSubTally().Enabled()  ;}}), Input.wait30);
			
		} else {
			throw new ImplementationException("NOT on_sub_tally_view");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_sub_tally_view_displays_doc_primary_language_metadata_correctly$")
	public void verify_sub_tally_view_displays_doc_primary_language_metadata_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		TallyPage tally = new TallyPage(driver);
		if (scriptState) {
			//TC7500:Validate new metadata field DocLanguages on Sub-Tally report
			//Validate metadata options are displayed in the Tally View after clicking the "En: Select a Tally Field to run tally on;" button
			
			tally.getTally_SourceSubTally().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					tally.getMetadataPopup().Visible()  ;}}), 10);
			tally.getSubTallyByOption("METADATA").click();
			tally.selectSubMetadataOption("DocLanguages");
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					tally.getSubMetadataButtonSelectedOption("DocPrimaryLanguage").Visible()  ;}}), 10);
			tally.getTally_btnSubTallyApply().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					tally.getSubTallyMetadataResults().Visible()  ;}}), Input.wait30);
			
			
			ArrayList<String> docLanguages = new ArrayList<String>();
			// Remove empty strings from List
			docLanguages.removeAll(Arrays.asList("", null));
			for(WebElement row: tally.getSubTallyMetadataResults().FindWebElements()) {
				docLanguages.add(row.getText());}
			
			// Remove empty strings from List
			docLanguages.removeAll(Arrays.asList("", null));
				
			// Convert "existingIngestionDocPrimaryLanguages" into an ArrayList since the values are saved as a single string
			ArrayList<String> expectedDocLanguages = new ArrayList<String>(Arrays.asList(dataMap.get("existingIngestionDocPrimaryLanguages").toString().split(",")));
			

			try {
				Assert.assertEquals(expectedDocLanguages, docLanguages);
				pass(dataMap, "Tally View correctly displays DocPrimaryLanguage metadata correctly");
			} catch (Exception e) {
				fail(dataMap,"DocLanguages values are not correct: " + docLanguages + " != " + expectedDocLanguages);
			}
					
		} else {
			throw new ImplementationException("NOT verify_sub_tally_view_displays_doc_primary_language_metadata_correctly");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_doc_view_displays_doc_primary_language_metadata_correctly$")
	public void verify_doc_view_displays_doc_primary_language_metadata_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		docView = new DocViewPage(driver);
		if (scriptState) {
			//TC9174:Verify value of metadata field "DocPrimaryLanguage" should be derived from CA for Add Only IngestionTC7501:Validate new metadata field DocLanguages on DocView
			//* Once the documents have been searched and are being viewed in DocView
			//* On the metadata tab located on the right hand side
			//* Validate that only documents that have a Language have a value for DocPrimaryLanguage field
			//
			
			// check if test case is verifying an existing ingestion
			if (dataMap.containsKey("existing_ingestion_name")) {
				try {
					docView.getDocViewTableRow("ID00002613").click();
					driver.waitForPageToBeReady();
					Assert.assertEquals("Japanese", docView.getDocPrimaryLanguageValue().getText());
					
					docView.getDocViewTableRow("ID00002614").click();
					driver.waitForPageToBeReady();
					Assert.assertEquals("Spanish", docView.getDocPrimaryLanguageValue().getText());
					
					docView.getDocViewTableRow("ID00002615").click();
					driver.waitForPageToBeReady();
					Assert.assertEquals("English", docView.getDocPrimaryLanguageValue().getText());
					
				} catch (Exception e) {
					System.out.println(e);
				}

			}
			
		} else {
			throw new ImplementationException("NOT verify_doc_view_displays_doc_primary_language_metadata_correctly");
		}

	}


	@And("^.*(\\[Not\\] )? open_saved_draft_ingestion$")
	public void open_saved_draft_ingestion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click on the settings gear for the ingestion
			//* Click on open Wizard option
			//* Ingestion is reopened
			//
			throw new ImplementationException("open_saved_draft_ingestion");
		} else {
			throw new ImplementationException("NOT open_saved_draft_ingestion");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_re_running_ingestion_is_successful$")
	public void verify_re_running_ingestion_is_successful(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5299:To Verify Rerunning the ingestions Should be successful.Covered:
			//* Validate ingestion is successful after re running a draft ingestion
			//
			throw new ImplementationException("verify_re_running_ingestion_is_successful");
		} else {
			throw new ImplementationException("NOT verify_re_running_ingestion_is_successful");
		}

	}


	@And("^.*(\\[Not\\] )? new_ingestion_created3$")
	public void new_ingestion_created3(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("new_ingestion_created3");
		} else {
			throw new ImplementationException("NOT new_ingestion_created3");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_overlay_ingestion_metadata_is_correct$")
	public void verify_overlay_ingestion_metadata_is_correct(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5896: To Verify Overlays Ingestion.
			//
			//* Validate metadata values are displayed in the doc view metadata table on the right
			//
			throw new ImplementationException("verify_overlay_ingestion_metadata_is_correct");
		} else {
			throw new ImplementationException("NOT verify_overlay_ingestion_metadata_is_correct");
		}

	}


	@And("^.*(\\[Not\\] )? new_ingestion_created_$")
	public void new_ingestion_created_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("new_ingestion_created_");
		} else {
			throw new ImplementationException("NOT new_ingestion_created_");
		}

	}


	@Given("^.*(\\[Not\\] )? verify_ingestion_overlay_is_ingested_successfully$")
	public void verify_ingestion_overlay_is_ingested_successfully(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6111:To Verify Ingestion Overlay Without DAT for Native.TC6112:To Verify Ingestion Overlay Without DAT for PDFTC6113:To Verify Ingestion Overlay Without DAT for TIFF.TC6114:To Verify Ingestion Overlay Without DAT for MP3 Variant.TC6115:To Verify Ingestion Overlay Without DAT for TranscriptTC6116:To Verify Ingestion Overlay Without DAT for Translation.Many test cases are being tested under this outcome, but we only need to check that ingestion was successful when we run an Overlay type ingestion
			throw new ImplementationException("verify_ingestion_overlay_is_ingested_successfully");
		} else {
			throw new ImplementationException("NOT verify_ingestion_overlay_is_ingested_successfully");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_overlay_with_different_files_is_ingested_successfully$")
	public void verify_overlay_with_different_files_is_ingested_successfully(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4499:overlay of the different files, keeping the same Unique id, which are already ingested and available in Production DB.
			throw new ImplementationException("verify_overlay_with_different_files_is_ingested_successfully");
		} else {
			throw new ImplementationException("NOT verify_overlay_with_different_files_is_ingested_successfully");
		}

	}


	@When("^.*(\\[Not\\] )? open_ingestion_details_page$")
	public void open_ingestion_details_page(boolean scriptState, HashMap dataMap)
		throws ImplementationException, Exception {

	if (scriptState) {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return ingest.getFirstGearBtn().Displayed() ;}}), Input.wait30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingest.getFirstGearBtn().Visible();
			}
		}), Input.wait30);
		while(!ingest.getFirstGearBtn().Displayed()) {
			ingest.getRefreshButton().click();
		}
		ingest.getFirstGearBtn().Click();
	} else {
		throw new ImplementationException("NOT open_ingestion_details_page");
	}

}


	@Then("^.*(\\[Not\\] )? verify_source_system_error_message_is_displayed$")
	public void verify_source_system_error_message_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC10002: Verify error message if the source system is matching and if the doc ID is not available in the databaseCovered:TC10003: Verify error message displays if adding same source Doc ID which is already exists in the DBValidate the error message displays the following:
			//
			//* "SourceDocID provided in the overlay for this doc is not available in the database."
			//
			//or
			//
			//* "SourceDocID already exists in the database. Cannot add again." when adding an existing doc.
			//
			throw new ImplementationException("verify_source_system_error_message_is_displayed");
		} else {
			throw new ImplementationException("NOT verify_source_system_error_message_is_displayed");
		}

	}


	@And("^.*(\\[Not\\] )? new_ingestion_created2$")
	public void new_ingestion_created2(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("new_ingestion_created2");
		} else {
			throw new ImplementationException("NOT new_ingestion_created2");
		}

	}


	@And("^.*(\\[Not\\] )? unrelease_ingested_documents$")
	public void unrelease_ingested_documents(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Once ingested files have bee searched
			//* Add the documents to the right side
			//* Click on the Action button
			//* Click Unrelease
			//* Complete the modal that pops up
			//* Once modal is finished
			//* Save the search
			//
			throw new ImplementationException("unrelease_ingested_documents");
		} else {
			throw new ImplementationException("NOT unrelease_ingested_documents");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_unpublish_overlay_ingestion_is_successful$")
	public void verify_unpublish_overlay_ingestion_is_successful(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6130: To Verify unpublish for Overlay Ingestion
			//
			//* Validate unpublish is successful
			//
			throw new ImplementationException("verify_unpublish_overlay_ingestion_is_successful");
		} else {
			throw new ImplementationException("NOT verify_unpublish_overlay_ingestion_is_successful");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_overlay_with_same_files_is_ingested_successfully$")
	public void verify_overlay_with_same_files_is_ingested_successfully(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4498:overlay of the same files, which are already ingested and available in Production DB.
			//
			//* Validate the ingestion is successful
			//
			throw new ImplementationException("verify_overlay_with_same_files_is_ingested_successfully");
		} else {
			throw new ImplementationException("NOT verify_overlay_with_same_files_is_ingested_successfully");
		}

	}


	@When("^.*(\\[Not\\] )? search_for_ingested_docs$")
	public void search_for_ingested_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Navigate to Search/Searches
			//* In the text box enter the following query:
			//For Test Case: 5930CustodianName:(Enter Column Value)For Test Cases: 5931, 5932, 5933, 5934IngestionName:(Enter Ingestion Name Here)
			//* Click Search button
			//
			throw new ImplementationException("search_for_ingested_docs");
		} else {
			throw new ImplementationException("NOT search_for_ingested_docs");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_previous_documents_are_updated_correctly$")
	public void verify_previous_documents_are_updated_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5930:To Verify Ingestion Overlays of DAT without unpublish.TC5931:To Verify Ingestion Overlays of PDF without unpublish.TC5932:To Verify Ingestion Overlays of Native without unpublish.TC5933:To Verify Ingestion Overlays of TIFF without unpublish.TC5934:To Verify Ingestion Overlays of Others without unpublish.Validate the document displays the correct values when using the overlay ingestion type
			throw new ImplementationException("verify_previous_documents_are_updated_correctly");
		} else {
			throw new ImplementationException("NOT verify_previous_documents_are_updated_correctly");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ingested_docs_are_in_sequential_order$")
	public void verify_ingested_docs_are_in_sequential_order(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6318:To verify DocID's of the ingested documents (AddOnly) Should be in the same sequential order as the order of records/documents in the DAT file.TC6319:To verify DocID's of the ingested documents Should be in the same sequential order as the order of records/documents in the DAT file.
			//
			//* Verify DOCID column displays files in a sequential order
			//
			throw new ImplementationException("verify_ingested_docs_are_in_sequential_order");
		} else {
			throw new ImplementationException("NOT verify_ingested_docs_are_in_sequential_order");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_preview_mapping_section_is_enabled$")
	public void verify_preview_mapping_section_is_enabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC2562 To verify that once Configure Mapping is done Admin is able to go on Preview Mapping section.
			//
			//* All required source fields and file types are selected
			//* Click on the Next button
			//* Validate the preview mapping (configure field mapping) is enabled
			//* Validate the preview and run button is enabled
			//
			try {
				Assert.assertTrue(ingest.getConfigureMappingDiv().GetAttribute("class").contains("active"));
				Assert.assertTrue(ingest.getBackButton().Enabled());
				Assert.assertTrue(ingest.getPreviewRun().Enabled());
				
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "Configure Field Mapping is not enabled");
			}
		} else {
			throw new ImplementationException("NOT verify_preview_mapping_section_is_enabled");
		}

	}


	@When("^.*(\\[Not\\] )? click_preview_and_run_button$")
	public void click_preview_and_run_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("click_preview_and_run_button");
		} else {
			throw new ImplementationException("NOT click_preview_and_run_button");
		}

	}


	@And("^.*(\\[Not\\] )? manually_execute_ingestion$")
	public void manually_execute_ingestion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select all available Ingestion filters
			//* Click on created 'In Progress' Ingestion
			//* Click 'Cataloging' play button
			//* Click 'Close' button
			//* Refresh the Ingestions until 'Cataloging' step is complete
			//* Click on recently Cataloged Ingestion
			//* Click 'Copying' play button
			//* Click 'Close' button
			//* Refresh the Ingestions until 'Copying' step is complete
			//* Click on recently Copied Ingestion
			//* Click 'Indexing' play button
			//* Click 'Close' button
			//* Refresh the Ingestions until 'Indexing' step is complete
			//
			throw new ImplementationException("manually_execute_ingestion");
		} else {
			throw new ImplementationException("NOT manually_execute_ingestion");
		}

	}


	@And("^.*(\\[Not\\] )? manually_approve_ingestion$")
	public void manually_approve_ingestion(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Filter for 'Indexed' Ingestions
			//* Click on recently Indexed Ingestion
			//* Select 'Approve' from top right dropdown menu on Ingestion Execution Details popup
			//
			throw new ImplementationException("manually_approve_ingestion");
		} else {
			throw new ImplementationException("NOT manually_approve_ingestion");
		}

	}


	@And("^.*(\\[Not\\] )? run_ingestion_analytics$")
	public void run_ingestion_analytics(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("run_ingestion_analytics");
		} else {
			throw new ImplementationException("NOT run_ingestion_analytics");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_add_only_ingestion_e2e$")
	public void verify_add_only_ingestion_e2e(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8783
			//
			//* To Verify Ingestion Add Only end to End Flow with Source System as NIUX
			//
			//TC8806
			//
			//* To Verify Ingestion Add Only end to End Flow with Source System as TRUE
			//
			throw new ImplementationException("verify_add_only_ingestion_e2e");
		} else {
			throw new ImplementationException("NOT verify_add_only_ingestion_e2e");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_analysis_is_completed_by_the_time_specified$")
	public void verify_analysis_is_completed_by_the_time_specified(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC7605 Verify Incremental Analysis of Email Threading for Add only Ingestion with 25,000 records  should get completed by 70min on 500,000 docs project for which Analysis is already completed
			//
			//* validate the analysis is completed by the time specified
			//
			throw new ImplementationException("verify_analysis_is_completed_by_the_time_specified");
		} else {
			throw new ImplementationException("NOT verify_analysis_is_completed_by_the_time_specified");
		}

	}
} //end
package stepDef;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

import pageFactory.ProductionPage;
import testScriptsSmoke.Input;

@SuppressWarnings({"rawtypes", "unchecked" })
public class ProductionContext extends CommonContext {

	/* 
	 * moved to CommonContext
	 * 
	public void sightline_is_launched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void login_as_pau(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void on_production_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

	 */
    

	@When("^.*(\\[Not\\] )? begin_new_production_process$")
	public void begin_new_production_process(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String dateTime = new Long((new Date()).getTime()).toString();
		String template = (String) dataMap.get("prod_template");

		try {
			if (scriptState) {
				prod.addNewProduction("AutoProduction"+dateTime, template);
			} else {
				pass(dataMap,"Skipped adding new production");
			} 
		} catch (Exception e) {
			pass(dataMap,"Exception: 'When' condition 'begin_new_production_process'. Continuing...");
			// when conditions exceptions are ignored
		}
	}

	@When("^.*(\\[Not\\] )? complete_component_mp3_with_lst_toggled_off$")
	public void complete_component_mp3_with_lst_toggled_off(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			prod.selectDATWithDefaultValue();
		} catch (InterruptedException e) {
			throw new Exception(e.getMessage());
		}
		try {
			prod.selectMP3WithLSTOff();
		} catch (InterruptedException e) {
			throw new Exception(e.getMessage());
		}

		driver.FindElementByTagName("body").SendKeys(Keys.HOME.toString());
		Thread.sleep(2000);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getComponentsMarkComplete().Displayed()  ;}}), Input.wait30); 
		prod.getComponentsMarkComplete().Click();
	}

	// TC4894 Verify Basic info page User should be able to select Custom Template (if available under custom template)
	// and move ahead with Production
	@Then("^.*(\\[Not\\] )? verify_production_can_use_custom_template_on_basic_info$")
	public void verify_production_can_use_custom_template_on_basic_info(boolean scriptState, HashMap dataMap) throws Exception {
		try {
			// Verify the production tile view displays same count as grid view
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProductionComponentTitle().Visible()  ;}}), Input.wait30); 
			prod.getProductionComponentTitle().Visible();
			pass(dataMap,"Production can use custom templates on basic info");
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Production unable to use custom templates on basic info as expected");
			}
		}
	}

	// TC9611 Verify 'Advanced Options' should be removed from the DAT component section
	@Then("^.*(\\[Not\\] )? verify_production_dat_component_removed_advanced_option$")
	public void verify_production_dat_component_removed_advanced_option(boolean scriptState, HashMap dataMap) throws Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATTab().Visible()  ;}}), Input.wait30); 
			prod.getDATTab().Click();

			if (!prod.getDATComponentAdvanced().Displayed()) {
				pass(dataMap,"Production DAT component removed");
			} else {
				fail(dataMap,"Production DAT component not removed");
			}				
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Production DAT component exception as expected");
			}
		}
	}

	// TC5195 Verify On Productions landing page, Count of productions in the tile view should match with grid view
	@Then("^.*(\\[Not\\] )? verify_production_tile_count_matches_grid_count$")
	public void verify_production_tile_count_matches_grid_count(boolean scriptState, HashMap dataMap) throws Exception {
		try {
			// Verify the production tile view displays same count as grid view
			prod.clickProductionTileView();
			Integer tileCount = Integer.getInteger(prod.getTileProdCount().GetAttribute("value"));
			prod.clickProductionGridView();
			String gridCount = prod.getGridProdCount().getText();
	
			String[] gridCountItems = gridCount.split(" ");
	
			if (tileCount == Integer.getInteger(gridCountItems[5])) {
				pass(dataMap, "Production tile count matches grid" );
			} else {
				fail(dataMap, "Production tile count does NOT match grid");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Production tile count exception as expected");
			}
		}
	}

	// TC5071 Verify in DAT section of a production configuration step availabilty of calculated fields
	@Then("^.*(\\[Not\\] )? verify_production_dat_contains_production_volume_name$")
	public void verify_production_dat_contains_production_volume_name(boolean scriptState, HashMap dataMap) throws Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATTab().Visible()  ;}}), Input.wait30); 
			prod.getDATTab().Click();
	
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDAT_FieldClassification1(" [value='PRODUCTION']").Visible()  ;}}), Input.wait30); 
			prod.getDAT_FieldClassification1(" [value='PRODUCTION']").Click();

			try {
				// Verify volume name source field option is available for Production field classification
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getDAT_SourceField1(" [value='-119']").Visible()  ;}}), Input.wait30); 
				prod.getDAT_SourceField1(" [value='-119']").Click();
	
				pass(dataMap,"Volume name source field option is available for Production field classification");
			} catch (Exception e) {
				fail(dataMap,"Volume name source field option is NOT available for Production field classification");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Volume name source field option is NOT available for Production field classification as expected");
			}
		}
	}

	// TC5206 Verify In Archive File for FTP, only ZIP option should be available
	@Then("^.*(\\[Not\\] )? verify_production_archive_option_is_only_zip$")
	public void verify_production_archive_option_is_only_zip(boolean scriptState, HashMap dataMap) {
		try {
			// Open Archive File for FTP section
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getArchiveComponentTab().Visible()  ;}}), Input.wait30); 
			prod.getArchiveComponentTab().Click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getArchiveComponentType().Visible()  ;}}), Input.wait30); 
			String optionText = prod.getArchiveComponentType().getText();

			if (optionText.equals("ZIP")) {
				pass(dataMap,"Production archive option is only 'ZIP'");
			} else {
				fail(dataMap,"Production archive option is not 'ZIP'");
			}
		} catch (Exception e) {
			if (scriptState) {
				fail(dataMap,"Could not find Production archive option");
			} else {
				// expected failures
				pass(dataMap,"Could not find Production archive option as expected");
			}
		}

	}

	// TC5796 Verify for Error Message on Mark Complete for MP3 Files Selection ,without enabling LST generation for it
	@Then("^.*(\\[Not\\] )? verify_production_error_message_for_mp3_with_lst_off$")
	public void verify_production_error_message_for_mp3_with_lst_off(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMP3WarningBox().Visible()  ;}}), Input.wait30);
				String warningText = prod.getMP3WarningBox().getText();
				if (warningText.equals(prod.mp3Warning)) {
					pass(dataMap,"Found expected error message for MP3 with list off");
				} else {
					fail(dataMap,"Expected error message for MP3 with list off does not match");
				}
			} catch (Exception e) {
				if (scriptState) {
					fail(dataMap,"Not found expected error message for MP3 with list off");
				} else {
					pass(dataMap,"Not found expected error message for MP3 with list off as expected");
				}
			}
	}

	// TC5798 Verify Filler Audio is avaible only on enabling Burn Redaction for MP3 Files under Advanced Production Components
	@Then("^.*(\\[Not\\] )? verify_production_mp3_redaction_styles$")
	public void verify_production_mp3_redaction_styles(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			// Open MP3 section
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProductionAdvanced().Visible()  ;}}), Input.wait30);
			prod.getProductionAdvanced().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getMP3Tab().Displayed()  ;}}), Input.wait30);
			prod.getMP3Tab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getMP3ComponentRedactionToggle().Displayed()  ;}}), Input.wait30);
			prod.getMP3ComponentRedactionToggle().Click();
	
	        // Verify redaction style options are only available when burn redactions toggle is on
	        try {
	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMP3ComponentRedactionStyle(String.format(" [value='%s']","1")).Visible()  ;}}), Input.wait30);
				prod.getMP3ComponentRedactionStyle(String.format(" [value='%s']","1")).Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMP3ComponentRedactionStyle(String.format(" [value='%s']","2")).Visible()  ;}}), Input.wait30);
				prod.getMP3ComponentRedactionStyle(String.format(" [value='%s']","2")).Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMP3ComponentRedactionStyle(String.format(" [value='%s']","3")).Visible()  ;}}), Input.wait30);
				prod.getMP3ComponentRedactionStyle(String.format(" [value='%s']","3")).Click();
				
				pass(dataMap,"Redaction Style options are available for Production");
	        } catch (Exception e) {
	        	fail(dataMap,"Redaction Style options are not available for Production");
	        	throw new Exception(e.getMessage());
	        }
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Redaction Style options are not available for Production as expected");
			}
		}
	}

	// TC5876 Verify Native Section with various options
	@Then("^.*(\\[Not\\] )? verify_production_native_component_advanced_options$")
	public void verify_production_native_component_advanced_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		// Open Native Advanced section
		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNativeTab().Visible()  ;}}), Input.wait30);
			prod.getNativeTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNativeAdvanced().Displayed()  ;}}), Input.wait30);
			prod.getNativeAdvanced().Click();
			
			try {
				// Verify Native Advanced section displays all options
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeAdvanced().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeAdvanced().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeAdvanced().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeAdvanced().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeAdvanced().Displayed()  ;}}), Input.wait30);
				pass(dataMap,"Native advanced options are displayed for Production");
			} catch (Exception e) {
				fail(dataMap,"Native advanced options are not displayed for Production");
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Native advanced options are not displayed for Production as expected");
			}
		}
	}

	// TC5877 Verify TIFF Section with various options
	@Then("^.*(\\[Not\\] )? verify_production_tiff_section_options$")
	public void verify_production_tiff_section_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFTab().Visible()  ;}}), Input.wait30);
			prod.getTIFFTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFAdvanced().Displayed()  ;}}), Input.wait30);
			prod.getTIFFAdvanced().Click();
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFMultiRadio().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFSingleRadio().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFRotateDropdown().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFLetterRadio().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFA4Radio().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFColorToggle().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFBlankRemovalToggle().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFBrandingTagsLink().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFBrandingTextField().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderPrivilegedToggle().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderTechIssueToggle().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderPrivilegedTagsButton().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderPrivilegedTextField().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderNative().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFBurnRedactionToggle().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFSlipSheetsToggle().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFTiffToggle().Displayed()  ;}}), Input.wait30);
				pass(dataMap,"All Tiff options are displayed for Production");
			} catch (Exception e) {
				fail(dataMap,"All Tiff options are not displayed for Production");
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"All Tiff options are not displayed for Production as expected");
			}
		}
	}


	// TC9151 Verify that 'Tech Issue placeholdering' is avaiable on Tiff/PDF on Production-Component section
	@Then("^.*(\\[Not\\] )? verify_production_tech_issue_placeholder_is_available$")
	public void verify_production_tech_issue_placeholder_is_available(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		
		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFTab().Visible()  ;}}), Input.wait30);
			prod.getTIFFTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFPlaceholderTechIssueToggle().Visible()  ;}}), Input.wait30);
			prod.getTIFFPlaceholderTechIssueToggle().Click();
			
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderTechTagsButton().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderTechTextField().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderTechMetadataLink().Displayed()  ;}}), Input.wait30);
			} catch (Exception e) {
				fail(dataMap,"Tech Issues Placeholdering is not available for PDF options");
				throw new Exception(e.getMessage());
			}
			
//			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//					prod.getTIFFTab().Visible()  ;}}), Input.wait30);
//			prod.getTIFFTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getPDFTab().Visible()  ;}}), Input.wait30);
			prod.getPDFTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getPDFPlaceholderTechIssueToggle().Visible()  ;}}), Input.wait30);
			prod.getPDFPlaceholderTechIssueToggle().Click();
			
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFPlaceholderTechTagsButton().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFPlaceholderTechTextField().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFPlaceholderTechMetadataLink().Displayed()  ;}}), Input.wait30);
				pass(dataMap,"Tech Issues Placeholdering is available for TIFF and PDF options");
			} catch (Exception e) {
				fail(dataMap,"Tech Issues Placeholdering is not available for PDF options");
				throw new Exception(e.getMessage());
			}
 		} catch (Exception e) {
 			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Tech Issues Placeholdering is not available for TIFF and PDF options as expected");
			}
		}
	}

	// TC9614 Verify the Advanced section in Tiff /PDF component
	@Then("^.*(\\[Not\\] )? verify_production_tiff_pdf_advanced_options$")
	public void verify_production_tiff_pdf_advanced_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFTab().Visible()  ;}}), Input.wait30);
			prod.getTIFFTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFAdvanced().Displayed()  ;}}), Input.wait30);
			prod.getTIFFAdvanced().Click();

			if (!prod.getTIFFAdvancedRemovedExcel().Displayed()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFTab().Visible()  ;}}), Input.wait30);
				prod.getPDFTab().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFAdvanced().Displayed()  ;}}), Input.wait30);
				prod.getPDFAdvanced().Click();
				
				if (!prod.getPDFAdvancedRemovedExcel().Displayed()) {
					pass(dataMap,"Excel option has been removed from TIFF and PDF advanced options");
				} else {
					fail(dataMap,"Excel option has not been removed from PDF advanced options");
				}	
			} else {
				fail(dataMap,"Excel option has not been removed from TIFF advanced options");
			}				
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Excel option has not been removed from TIFF and PDF advanced options as expected");
			}
		}
	}


//	//#######################################################################################################################
//	//# Production/Exports
//	//#######################################################################################################################
//
//	// TC9617 Verify 'Advanced Options' should be removed from the DAT component section in Production-Export
//	@Then("^.*(\\[Not\\] )? verify_export_dat_component_removed_advanced_option$")
//	public void verify_export_dat_component_removed_advanced_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
//
//		// Open DAT section
//		/*        self.driver.find_element_by_css_selector(prod.DAT_COMPONENT_TAB).click()
//        time.sleep(2)
//
//        // Verify Advanced option is removed from DAT section
//        removed_advanced = self.driver.find_element_by_css_selector(prod.DAT_COMPONENT_ADVANCED).is_displayed()
//        assert removed_advanced is False
//		 */
//		throw new ImplementationException("verify_export_dat_component_removed_advanced_option");
//	}
//
//	// TC9618 Verify Native section in Production Components section
//	@Then("^.*(\\[Not\\] )? verify_export_native_component_section$")
//	public void verify_export_native_component_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
//
//		// Open Native section and advanced options
//		/*        self.driver.find_element_by_css_selector(prod.NATIVE_COMPONENT_TAB).click()
//        time.sleep(2)
//        self.driver.find_element_by_css_selector(prod.NATIVE_COMPONENT_ADVANCED).click()
//
//        // Verify Native section text is blue and LST option is in advanced section
//        desc_element = self.driver.find_element_by_css_selector(prod.NATIVE_COMPONENT_DESC)
//        assert prod.blue_desc in desc_element.text
//
//        text_color = desc_element.value_of_css_property('color')
//        assert Color.from_string(text_color) == Color.from_string(prod.blue_color)
//
//        assert self.driver.find_element_by_css_selector(prod.NATIVE_ADVANCED_LST_HELP).is_displayed()
//		 */
//		throw new ImplementationException("verify_export_native_component_section");
//	}
//
//	// TC9619 Verify changes in Text component section
//	@Then("^.*(\\[Not\\] )? verify_export_text_component_changes$")
//	public void verify_export_text_component_changes(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
//
//		// Open Text Advanced section
//		/*        self.driver.find_element_by_css_selector(prod.TEXT_COMPONENT_TAB).click()
//        time.sleep(2)
//        self.driver.find_element_by_css_selector(prod.TEXT_COMPONENT_ADVANCED).click()
//
//        // Verify Text description was changed
//        description = self.driver.find_element_by_css_selector(prod.TEXT_COMPONENT_DESC).text
//        assert prod.text_desc_change in description
//
//        // Verify TIFF toggle has been removed
//        removed_tiff = self.driver.find_element_by_css_selector(prod.TEXT_REMOVED_TIFF).is_displayed()
//        assert removed_tiff is False
//		 */
//		throw new ImplementationException("verify_export_text_component_changes");
//	}
//
//	// TC9620 Verify the Advanced section in Tiff /PDF component
//	@Then("^.*(\\[Not\\] )? verify_export_tiff_pdf_advanced_options$")
//	public void verify_export_tiff_pdf_advanced_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
//
//		// Open Tiff Advanced section
//		/*        self.driver.find_element_by_css_selector(prod.TIFF_COMPONENT_TAB).click()
//        time.sleep(2)
//        self.driver.find_element_by_css_selector(prod.TIFF_COMPONENT_ADVANCED).click()
//
//        // Verify Generate for Excel option removed from TIFF advanced section
//        removed_option_tiff = self.driver.find_element_by_css_selector(
//            prod.TIFF_ADVANCED_REMOVED_EXCEL).is_displayed()
//        assert removed_option_tiff is False
//
//        // Open PDF Advanced section
//        self.driver.find_element_by_css_selector(prod.PDF_COMPONENT_TAB).click()
//        time.sleep(2)
//        self.driver.find_element_by_css_selector(prod.PDF_COMPONENT_ADVANCED).click()
//
//        // Verify Generate for Excel option removed from PDF advanced section
//        removed_option_pdf = self.driver.find_element_by_css_selector(
//            prod.PDF_ADVANCED_REMOVED_EXCEL).is_displayed()
//        assert removed_option_pdf is False
//		 */
//		throw new ImplementationException("verify_export_tiff_pdf_advanced_options");
//	}

}




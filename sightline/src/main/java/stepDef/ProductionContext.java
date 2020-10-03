package stepDef;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import automationLibrary.Element;
import automationLibrary.ElementCollection;
import cucumber.api.java.en.Given;
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

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					!prod.getDATComponentAdvanced().Visible()  ;}}), Input.wait30); 

			if(prod.getDATComponentAdvanced().Visible()) fail(dataMap, "Production DAT component not removed");
		    else pass(dataMap,"Production DAT component removed");


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
					prod.getProductionAdvanced().Displayed()  ;}}), Input.wait30);
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
			
			// Verify Native Advanced section displays all options
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNativeAdvancedParentsRadio().Displayed()  ;}}), Input.wait30);
			prod.getNativeAdvancedParentsRadio().Click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNativeAdvancedFamilyRadio().Displayed()  ;}}), Input.wait30);
			prod.getNativeAdvancedFamilyRadio().Click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNativeAdvancedLST().Displayed()  ;}}), Input.wait30);
			prod.getNativeAdvancedLST().Click();

			pass(dataMap,"Native advanced options are displayed for Production");
			
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

	@When("^.*(\\[Not\\] )? expanding_the_dat_production_component$")
	public void expanding_the_dat_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				//The user should be on the "Production Components" section of Productions.The user should click on "DAT" to expand the Native section.
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATTab().Displayed()  ;}}), Input.wait30);
				prod.getDATTab().Click();
				pass(dataMap, "DAT tab was opened");
			}
			catch(Exception e) {
				fail(dataMap, "Could not open DAT Tab");
			}
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_dat_product_component_displays_the_correct_default_options$")
	public void verify_the_dat_product_component_displays_the_correct_default_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			try {
				//Find Ansci Radio Button and make sure it is not checked by default
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATAnsiRadioButton().Displayed()  ;}}), Input.wait30);
				Assert.assertFalse(prod.getDATAnsiRadioButton().Selected());


				//Verify Unicode Button is checked
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATAnsiUnicode().Displayed()  ;}}), Input.wait30);
				Assert.assertTrue(prod.getDATAnsiUnicode().Selected());
				
				//Verify Ansci is "deprecated by default"
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATAnsiType().Displayed()  ;}}), Input.wait30);
				String defaultAnsciType =  prod.getDATAnsiType().selectFromDropdown().getFirstSelectedOption().getText(); 
				Assert.assertEquals("(deprecated)", defaultAnsciType);
				
				//Verify all field delimeters below
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATFieldSeperator().Displayed()  ;}}), Input.wait30);
				String defaultFieldSeperatorText =  prod.getDATFieldSeperator().selectFromDropdown().getFirstSelectedOption().getText(); 
				Assert.assertEquals("ASCII(20)", defaultFieldSeperatorText);

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATTextQualifier() .Displayed()  ;}}), Input.wait30);
				String defaultTextQualifierText =  prod.getDATTextQualifier().selectFromDropdown().getFirstSelectedOption().getText(); 
				Assert.assertEquals("ASCII(254)", defaultTextQualifierText);

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATMultiValue().Displayed()  ;}}), Input.wait30);
				String defaultMultiValueText =  prod.getDATMultiValue().selectFromDropdown().getFirstSelectedOption().getText(); 
				Assert.assertEquals("ASCII(174)", defaultMultiValueText);

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATNewLine().Displayed()  ;}}), Input.wait30);
				String defaultNewLineText =  prod.getDATNewLine().selectFromDropdown().getFirstSelectedOption().getText(); 
				Assert.assertEquals("ASCII(10)", defaultNewLineText);

				
				//Verify Date Format is /YY/MM/DD HH:MI:SS
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATDateFormat().Displayed()  ;}}), Input.wait30);
				String defaultDateText =  prod.getDATDateFormat().selectFromDropdown().getFirstSelectedOption().getText(); 
				Assert.assertEquals("YYYY/MM/DD HH:MI:SS", defaultDateText);
				
				
				//Verify DAT Field Classification is set to "Selected"
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDAT_FieldClassification1().Displayed()  ;}}), Input.wait30);
				String defaultFieldClass =  prod.getDAT_FieldClassification1().selectFromDropdown().getFirstSelectedOption().getText(); 
				Assert.assertEquals("Select", defaultFieldClass);

				//Verify Rest of DAT field Mapping buttons are empty
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDAT_SourceField1().Displayed()  ;}}), Input.wait30);
				int fieldSize = prod.getDAT_SourceField1().selectFromDropdown().getAllSelectedOptions().size();
				Assert.assertEquals(0, fieldSize);

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDAT_DATField1().Displayed()  ;}}), Input.wait30);
				Assert.assertEquals("",prod.getDAT_DATField1().getText());
				
				//Verify DAT Field Mapping Buttons are Unchecked
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATRedactionsButton().Displayed()  ;}}), Input.wait30);
				Assert.assertFalse(prod.getDATRedactionsButton().Selected());

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATPrivilegedButton().Displayed()  ;}}), Input.wait30);
				Assert.assertFalse(prod.getDATPrivilegedButton().Selected());
				
				pass(dataMap, "All fields passed");

			}
			catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "Could not open DAT Tab");
			}
		}

	}


	@When("^.*(\\[Not\\] )? expanding_the_tiff_production_component$")
	public void expanding_the_tiff_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFTab().Enabled()  ;}}), Input.wait30);
					prod.getTIFFTab().Click();
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFAdvanced().Enabled()  ;}}), Input.wait30);
					prod.getTIFFAdvanced().Click();
					pass(dataMap, "Succesfully got through tiff advanced components");
			}
			catch(Exception e){fail(dataMap, "Did not enter TIFF advanced options");}
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_tiff_product_component_displays_the_correct_default_options$")
	public void verify_the_tiff_product_component_displays_the_correct_default_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
				try {

				   //Check first section is Page Options
				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFFirstPageElement().Enabled()  ;}}), Input.wait30);
				   Assert.assertEquals("Page Options:", prod.getTIFFFirstPageElement().getText());
				   
				   //Check if Multi Radio button is unchecked by defauly
				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFMultiRadio().Enabled()  ;}}), Input.wait30);
				   Assert.assertNull(prod.getTIFFMultiRadio().GetAttribute("checked"));

				   //Check if Single Radio button is check by default -> doesnt work registers as false?
				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFSingleRadio().Enabled()  ;}}), Input.wait30);
				   Assert.assertEquals("true",prod.getTIFFSingleRadio().GetAttribute("checked"));

				   //Check if A4 Radio button is unchecked by default
				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFA4Radio().Enabled()  ;}}), Input.wait30);
				   Assert.assertNull(prod.getTIFFA4Radio().GetAttribute("checked"));

				   //Check if Letter Radio button is checked by default
				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFLetterRadio().Enabled()  ;}}), Input.wait30);
				   Assert.assertEquals("true",prod.getTIFFLetterRadio().GetAttribute("checked"));

				    
				   //Make Sure Preserve Color, Blank Page Removal and Do not produce full content are Disabled by default
				    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFColorToggle().Displayed()  ;}}), Input.wait30);
				    Assert.assertFalse(prod.getTIFFColorToggle().Selected());

				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFBlankRemovalToggle().Displayed()  ;}}), Input.wait30);
				   Assert.assertFalse(prod.getTIFFBlankRemovalToggle().Selected());

				   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   prod.getTIFFTiffToggle().Displayed()  ;}}), Input.wait30);
				   Assert.assertFalse(prod.getTIFFTiffToggle().Selected());
				   
				  //Verify Rotate Landscape is set to "No Rotation" by default
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFRotateDropdown().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("No Rotation", prod.getTIFFRotateDropdown().selectFromDropdown().getFirstSelectedOption().getText());
				  
				  //Verify All Buttons of Rectangle, (Top Left, Top Center, Top Right, Bottom Left, Bottom Center, Bottom Right)
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFF_CenterHeaderBranding().Displayed()  ;}}), Input.wait30);
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFF_CenterFooterBranding().Displayed()  ;}}), Input.wait30);

				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFF_LeftHeaderBranding().Displayed()  ;}}), Input.wait30);
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFF_LeftFooterBranding().Displayed()  ;}}), Input.wait30);

				  //Verify Top Left is Default Selected
				  Assert.assertFalse(prod.getTIFF_LeftHeaderBranding().Selected());

				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFF_RightHeaderBranding().Displayed()  ;}}), Input.wait30);
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFF_RightFooterBranding().Displayed()  ;}}), Input.wait30);
				  
				  //Verify Middle Text says "Page Body"
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFRectangleMiddleText().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("--Page Body--", prod.getTIFFRectangleMiddleText().getText());
				  

				  //Verify Location in Branding
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFBrandingLocation().Displayed()  ;}}), Input.wait30);

				  
				  //Verify Branding Text in Branding
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFBrandingText().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Branding Text:", prod.getTIFFBrandingText().getWebElement().getText());

				  //Verify Specify Default Branding in Branding
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFSpecifyDefaultBranding().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Specify Default Branding", prod.getTIFFSpecifyDefaultBranding().getText());

				  //Verify Specify Tags in Branding
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFBrandingTagsLink().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Specify Branding by Selecting Tags:", prod.getTIFFBrandingTagsLink().getText());


				  //Verify Insert Metafield in Branding
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFMetadataField().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Insert Metadata Field", prod.getTIFFMetadataField().getText());
				  
				  //Verify Default Branding Inner Rectangle text
				  //Needs Assert - > find text with placeholder
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFDefaultBrandingRectangleText().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Enter default branding for the selected location on the page.", prod.getTIFFDefaultBrandingRectangleText().GetAttribute("placeholder").toString());
				  
				   
				   //Verify Privileged Docs is Default Green
				  //Is Default Selected, but still comes up false when I call Selected() function ?
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFF_EnableforPrivilegedDocs().Displayed()  ;}}), Input.wait30);
				  if(prod.getTiFFPrivToggleButton().GetAttribute("checked") != null) Assert.assertEquals("true", prod.getTiFFPrivToggleButton().GetAttribute("checked").toString());

				  //Verify Blue Tag Button next to Priviledged Docs
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getPriveldge_SelectTagButton().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Select Tags", prod.getPriveldge_SelectTagButton().getText());

				  //Verify Tech Tissue Docs is Selected Red by Default
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTechissue_toggle().Displayed()  ;}}), Input.wait30);
				  Assert.assertFalse(prod.getTechissue_toggle().Selected());

				  //Verify Native Produced Document Link
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFPlaceholderNative().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Enable for Natively Produced Documents:", prod.getTIFFPlaceholderNative().getText());

				  
				  //Verify PlaceHolder Rectangle Text
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFPlaceholderTechTextField().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Enter placeholder text for the privileged docs", prod.getTIFFPlaceholderTechTextField().GetAttribute("Placeholder"));

				  //Verify Insert MetaData Field
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFPlaceholderTechMetadataLink().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Insert Metadata Field", prod.getTIFFPlaceholderTechMetadataLink().getText());
				  
				  //THE FOLLOWING ALL NEED ASSERTS / CONFRIM CORRECTNESS
				  //Verify Burn Redactions is Red by Default
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFBurnRedactionToggle().Displayed()  ;}}), Input.wait30);
				  Assert.assertFalse(prod.getTIFFBurnRedactionToggle().Selected());
				  
				  //Verify Generate Load File is Green By Default
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFLSTLoadFileToggle().Displayed()  ;}}), Input.wait30);
				  Assert.assertTrue(prod.getTIFFLSTLoadFileToggle().Selected());

				  //Verify Tiff Slip Sheets are red by Default
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFSlipSheetsToggle().Displayed()  ;}}), Input.wait30);
				  Assert.assertFalse(prod.getTIFFSlipSheetsToggle().Selected());
				  
				  //Verify Load File Type Default is "Log"
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  prod.getTIFFLSTLoadFileType().Displayed()  ;}}), Input.wait30);
				  Assert.assertEquals("Log", prod.getTIFFLSTLoadFileType().selectFromDropdown().getFirstSelectedOption().getText());
				 
				   pass(dataMap, "passed");

				
			}
			catch(Exception e) { fail(dataMap, "not pass");}
		}

	}


	@And("^.*(\\[Not\\] )? complete_default_production_component$")
	public void complete_default_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			try {
				
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATChkBox().Displayed()  ;}}), Input.wait30);
				prod.getDATChkBox().Click();
				
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getDATTab().Displayed()  ;}}), Input.wait30);
				prod.getDATTab().Click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getFieldClassification().Displayed()  ;}}), Input.wait30);
				prod.getFieldClassification().Click();
				prod.getFieldClassification().SendKeys("Bates");
				
				
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getSourceField().Displayed()  ;}}), Input.wait30);
				prod.getSourceField().Click();
				prod.getSourceField().SendKeys("BatesNumber");
				
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDatField().Displayed()  ;}}), Input.wait30);
				prod.getDatField().Click();
				prod.getDatField().SendKeys("Bates Number");
				
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getComponentsMarkComplete().Displayed()  ;}}), Input.wait30);
				prod.getComponentsMarkComplete().Click();
					
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getComponentsMarkNext().Enabled() ;}}), Input.wait30);
				prod.getComponentsMarkNext().Click();
				
			pass(dataMap,"Default Production Component are completed");	
				
			} catch(Exception e) {
				fail(dataMap,"Default Production Component is not completed");
			}
		}else {
			fail(dataMap,"Default Production Component is not completed");
			}

}

	@And("^.*(\\[Not\\] )? complete_default_numbering_and_sorting$")
	public void complete_default_numbering_and_sorting(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			try {
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNumAndSortMarkCompleteBtn().Displayed() ;}}), Input.wait30);
			prod.getNumAndSortMarkCompleteBtn().Click();
				
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNumAndSortNextBtn().Enabled()  ;}}), Input.wait30);

				prod.getNumAndSortNextBtn().Click();
							
				pass(dataMap,"Default numbering and sorting is complete");
			}catch(Exception e) {
				fail(dataMap,"Default numbering and sorting is not complete");
			}
		} else {
			fail(dataMap,"Default numbering and sorting is not complete");

		}

	}


	@And("^.*(\\[Not\\] )? complete_default_document_selection$")
	public void complete_default_document_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getFolderRadioButton().Displayed()  ;}}), Input.wait30);
				prod.getFolderRadioButton().Click();
				
			//Collapse folders 
//			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//					prod.getDocumentCollapseBtn().Displayed()  ;}}), Input.wait30);
//				prod.getDocumentCollapseBtn().Click();
			
			//Select Default Automation Folder	
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDefaultAutomationChkBox().Displayed()  ;}}), Input.wait30);
				prod.getDefaultAutomationChkBox().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocumentMarkCompleteBtn().Enabled()  ;}}), Input.wait30);
				prod.getDocumentMarkCompleteBtn().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocumentNextBtn().Enabled()  ;}}), Input.wait30);
				prod.getDocumentNextBtn().Click();
				
				pass(dataMap,"Default document sections has been completed");
			}catch(Exception e) {
				fail(dataMap,"Default document sections has not been completed");
			}	
		} else {
			fail(dataMap,"Default document sections has not been completed");
		}

	}


	@And("^.*(\\[Not\\] )? complete_default_priv_guard_documents_are_matched$")
	public void complete_default_priv_guard_documents_are_matched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			try {
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivAddRuleBtn().Enabled() && prod.getPrivAddRuleBtn().Displayed()  ;}}), Input.wait30);
					prod.getPrivAddRuleBtn().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivRedactionsBtn().Enabled() && prod.getPrivRedactionsBtn().Displayed() ;}}), Input.wait30);
					prod.getPrivRedactionsBtn().Click();
					
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivDefaultAutomation().Enabled() && prod.getPrivDefaultAutomation().Displayed()  ;}}), Input.wait30);
					prod.getPrivDefaultAutomation().Click();
					
									
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivInsertQuery().Enabled() && prod.getPrivInsertQuery().Displayed()  ;}}), Input.wait30);
					prod.getPrivInsertQuery().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivChkForMatching().Enabled() && prod.getPrivChkForMatching().Displayed()  ;}}), Input.wait30);
					prod.getPrivChkForMatching().Click();
					

					pass(dataMap,"Priv guard documents are completed");
					
			}
			catch(Exception e){
				fail(dataMap,"Priv guard documents are not completed");
			}
		
		} 
		else{
			fail(dataMap,"Priv guard documents are not completed");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_on_the_docview_button$")
	public void clicking_on_the_docview_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				
			driver.waitForPageToBeReady();
			dataMap.put("totalDocuments", prod.getTotalMatchedDocuments().getText());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getPrivDocViewBtn().Displayed() && prod.getPrivDocViewBtn().Enabled() && prod.getPrivDocViewBtn().Visible()  ;}}), Input.wait30);
				prod.getPrivDocViewBtn().Click();
				
	
				pass(dataMap,"Clicking the doc view is successful");

		
			}catch (Exception e) {
				fail(dataMap,"Clicking the doc view is not successful");
			}
		} else {
			fail(dataMap,"Clicking the doc view is not successful");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_viewing_docview_for_priv_guard$")
	public void verify_viewing_docview_for_priv_guard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			driver.waitForPageToBeReady();
			try {
				String url = driver.getUrl();
				
				if(url.contains("DocumentViewer/DocView")){

					//Verify "Review Text is displayed
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getReviewModeText().Displayed()  ;}}), Input.wait30);
					String reviewMode = prod.getReviewModeText().getText();
					
					
					//Counting the Documents from table 
					ElementCollection totalDocuments = driver.FindElementsByXPath("//*[@id='SearchDataTable']/tbody/tr");
					int numOfDocuments = (int)totalDocuments.size();
					
										
					String docs = (String) dataMap.get("totalDocuments");
					int numDocs = Integer.parseInt(docs);
										
					Assert.assertEquals(numDocs, numOfDocuments);
					Assert.assertEquals(reviewMode, "REVIEW MODE");
										
					pass(dataMap,"You are in the Doc View");
				
				}
				}catch(Exception e) {
					fail(dataMap,"Not in the correct View");
				}
				} else {
					System.out.println("You never left");
					fail(dataMap,"Not in the correct View");

		} 
		

	}


	@When("^.*(\\[Not\\] )? clicking_on_the_doclist_button$")
	public void clicking_on_the_doclist_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocListButton().Displayed() && prod.getDocListButton().Enabled() ;}}), Input.wait30);
				prod.getDocListButton().Click();
				pass(dataMap, "Clicked Doc List Button Succesfully");
			}
			catch(Exception e) {fail(dataMap,"Could not click docList Button");}
		}

	}


	@Then("^.*(\\[Not\\] )? verify_viewing_doclist_for_priv_guard$")
	public void verify_viewing_doclist_for_priv_guard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			driver.waitForPageToBeReady();
			String url = driver.getUrl();
			if(url.contains("Document/DocList")) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocListSourceCriteria().Enabled()  ;}}), Input.wait30);
				Assert.assertEquals("SOURCE CRITERIA", prod.getDocListSourceCriteria().getText());

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocListProductionText().Enabled()  ;}}), Input.wait30);
				Assert.assertEquals("Production",prod.getDocListProductionText().getText());

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocListBackToSourceButton().Enabled() && prod.getDocListBackToSourceButton().Displayed()  ;}}), Input.wait30);

			    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocListEntryAmountText().Enabled()  ;}}), Input.wait30);
				Assert.assertEquals("Showing 1 to 5 of 5 entries",prod.getDocListEntryAmountText().getText());
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocListTableEntry().Enabled()  ;}}), Input.wait30);
				Assert.assertEquals(5, prod.getDocListTableEntry().getWebElement().findElements(By.tagName("tr")).size());

				pass(dataMap,"You are in the Doc List View");
			} else {
				fail(dataMap,"You are not in the Doc List view");
			}
		}

	}



	@When("^.*(\\[Not\\] )? clicking_document_as_the_numbering_default_option$")
	public void clicking_document_as_the_numbering_default_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking the Documents radio button
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNumDocumentLevelRadioButton().Enabled()  ;}}), Input.wait30);
				prod.getNumDocumentLevelRadioButton().Click();
				pass(dataMap, "Radio Button Succesfully Clicked");
			}
			catch(Exception e) {fail(dataMap, "Did not Click Documents Radio Button");}

		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_numbering_and_sorting_component_displays_the_correct_default_options$")
	public void verify_the_numbering_and_sorting_component_displays_the_correct_default_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				
				//Find which buttons are default
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNumDocumentLevelRadioButton().Displayed()  ;}}), Input.wait30);

				//If Document is Default Checked
				if(prod.getNumDocumentLevelRadioButtonCheck().GetAttribute("checked")!=null && prod.getNumDocumentLevelRadioButtonCheck().GetAttribute("checked").equals("true")) {
						//Verify Bates Number starts at 1: 
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getNumSubBatesNum().Displayed()  ;}}), Input.wait30);
						Assert.assertEquals("1",prod.getNumSubBatesNum().GetAttribute("value").toString());
				
						//Verify Min Number starts at 5: 
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getNumSubBatesMin().Displayed()  ;}}), Input.wait30);
						Assert.assertEquals("5",prod.getNumSubBatesMin().GetAttribute("value").toString());
				}
				else {
					//Verify Page Radio Button is selected by default
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumPageLevelRadioButton().Displayed()  ;}}), Input.wait30);
					Assert.assertTrue(prod.getNumPageLevelRadioButton().Selected());
				
				}

				if(prod.getNumUseMetaFieldButtonCheck().GetAttribute("checked")!=null && (prod.getNumUseMetaFieldButtonCheck().GetAttribute("checked")).equals("true")) {
					System.out.println("dont go in here");
					//Verify All Custodians is default from dropdown.
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumMetaDataCustodiansTab().Enabled()  ;}}), Input.wait30);
					Assert.assertEquals("AllCustodians",prod.getNumMetaDataCustodiansTab().selectFromDropdown().getFirstSelectedOption().getText());
					
					//Verify Both Prefix and Suffix are blank
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumMetaDataPrefix().Enabled()  ;}}), Input.wait30);
					Assert.assertEquals("", prod.getNumMetaDataPrefix().selectFromDropdown().getFirstSelectedOption().getText());
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumMetaDataSuffix().Enabled()  ;}}), Input.wait30);
					Assert.assertEquals("", prod.getNumMetaDataSuffix().selectFromDropdown().getFirstSelectedOption().getText());
				}
				else {
					//Verify Format Section as "Specify Bates Numbering" as default value
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumBatesRadioButton().Displayed()  ;}}), Input.wait30);
					Assert.assertEquals("true",prod.getNumBatesRadioButtonCheck().GetAttribute("checked"));

					//Verify Link under Specify Bates Numbering Button
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumNextBatesLink().Displayed()  ;}}), Input.wait30);

				}
				//Verify in Sorting Section, "Sort by Metadata" is default checked
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNumSortMetaRadioButtonCheck().Displayed()  ;}}), Input.wait30);
				Assert.assertEquals("true",prod.getNumSortMetaRadioButtonCheck().GetAttribute("checked"));
				pass(dataMap,"Passed Verification of Sorting and Nums Page");
								
				
			}
			catch(Exception e) { 
				e.printStackTrace();
				fail(dataMap,"Did not Pass Verification of Sorting and Nums Page");
			}
		}

	}


	@When("^.*(\\[Not\\] )? clicking_use_metadata_field_as_the_format_default_option$")
	public void clicking_use_metadata_field_as_the_format_default_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNumUseMetaFieldButton().Enabled()  ;}}), Input.wait30);
				prod.getNumUseMetaFieldButton().Click();
				pass(dataMap, "Succesfully clicked useMeta Field Button");
			}
			catch(Exception e) {fail(dataMap, "Could not click UseMeta Field Button");}
		}

	}


	//Duplicate Function -> Already Done
	@Then("^.*(\\[Not\\] )? verify_the_numbering_also_sorting_component_displays_the_correct_default_options$")
	public void verify_the_numbering_also_sorting_component_displays_the_correct_default_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4922
			//* In the Numbering, Level section, "Page" is selected by default.
			//* In the Numbering, Format section, "Specify Bates Numbering" should be selected by default with the option to "Click here to view and select the next bates number(s)".
			//* In the Sorting section, "Sort by Metadata" is chosen by default. 
			//If Document is selected to be default instead of page:
			//* Ignore bullet 1 above.
			//* Verify under Document, "Beginning Sub-bates Number:" starts at 1 and "Min Number Length:" starts at 5.
			//If Use Metadata field is selected to be default instead of Specify Bates Numbering:
			//* Ignore bullet 2 at the top. 
			//* Verify the field "Metadata" is populated with "AllCustodians" by default under "Use Metadata Field" with the Prefix and Suffix section left blank.
			//
			throw new ImplementationException("verify_the_numbering_also_sorting_component_displays_the_correct_default_options");
		} else {
			throw new ImplementationException("NOT verify_the_numbering_also_sorting_component_displays_the_correct_default_options");
		}

	}
	


	@When("^.*(\\[Not\\] )? clicking_the_document_selection_select_searches_option$")
	public void clicking_the_document_selection_select_searches_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the radio button Select Searches:
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocSelectSearchRadioButton().Displayed()  ;}}), Input.wait30);
				prod.getDocSelectSearchRadioButton().Click();
				
				dataMap.put("DocumentRadioBtn","selectSearchBtn");
				pass(dataMap,"You succesfully clicked the Search Options");
		}else {
			pass(dataMap,"You NOT succesfully clicked the Search Options");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_document_selection_component_displays_the_correct_default_options$")
	public void verify_the_document_selection_component_displays_the_correct_default_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4923In all cases, verify the option "Include Families" is checked green at the bottom by default.
			//If Select Folders is selected:1. Verify a grid of folders appears with the first option being "Default Automation Folder".
			//2. Verify no folders are selected by default.
			//3. Verify clicking All Folders and clicking Mark complete returns "Total Docs Selected Incl. Families: 20". 
			//If Select Tags is selected:1. Verify a list of tags should appear in a grid. 
			//2. Verify no tags are selected by default.
			//3.  Verify default automation tags is selected. can be checked off and clicking "Mark Complete" returns "Total Docs Selected Incl. Families: 6".
			//If Searches is selected:1. Verify a grid of Searches appear.
			//2. Verify no Searches are selected by default.
			//3. Verify the search name "shared with default security group" is checked and clicking "Mark Complete" returns "Total Docs Selected Incl. Families: 1223"
			
			
			
			//Include Families is selected
	 	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   prod.getIncludeFamilies().Displayed()  ;}}), Input.wait30);
			 Assert.assertFalse(prod.getIncludeFamilies().Selected());
			 
					
					if((dataMap.get("DocumentRadioBtn").equals("selectedFolderBtn"))){ 

						//For Each CheckBox, Assert That Each Value is Not Selected by Default
						for(WebElement x: prod.getAllDefaultFolderCheckboxes().FindWebElements()) {
							if(x.isDisplayed()) Assert.assertFalse(x.isSelected());
						}

						//Select Default Automation Folder
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								prod.getDefaultAutomationChkBox().Displayed() ;}}), Input.wait30);
						prod.getDefaultAutomationChkBox().Click();

						//Mark Complete
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								prod.getDocumentMarkCompleteBtn().Enabled() ;}}), Input.wait30);
						prod.getDocumentMarkCompleteBtn().Click();

						//Total documents 
						//Wait for Docuemnts number to change from 0 -> to another value before retrieving the value
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!(prod.getTotalDocProduction().getText()).equals("0") ;}}), Input.wait30); 
						Assert.assertEquals(prod.getTotalDocProduction().getText(), "20"); 
					}
					
					if(dataMap.get("DocumentRadioBtn").equals("selectTagsBtn")) { 

						//For Each CheckBox, Assert That Each Value is Not Selected by Default
						for(WebElement x: prod.getAllDefaultFolderCheckboxes().FindWebElements()) {
							if(x.isDisplayed()) Assert.assertFalse(x.isSelected());
						}

						//Select Default Tag Folder
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								prod.getDefaultTagsChkBox().Displayed() ;}}), Input.wait30);
						prod.getDefaultTagsChkBox().Click();
						
						//Mark Complete
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								prod.getDocumentMarkCompleteBtn().Enabled() ;}}), Input.wait30);
						prod.getDocumentMarkCompleteBtn().Click();

						//Total documents 
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!(prod.getTotalDocProduction().getText()).equals("0") ;}}), Input.wait30); 
						Assert.assertEquals(prod.getTotalDocProduction().getText(), "6"); 
						
					}
					
					if(dataMap.get("DocumentRadioBtn").equals("selectSearchBtn")) { 

						//For Each CheckBox, Assert That Each Value is Not Selected by Default
						for(WebElement x: prod.getAllDefaultFolderCheckboxes().FindWebElements()) {
							if(x.isDisplayed()) Assert.assertFalse(x.isSelected());
						}

						//Select Default Search Folder
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								prod.getDefaultSecurityGroupChkBox().Displayed() ;}}), Input.wait30);
						prod.getDefaultSecurityGroupChkBox().Click();
						
						//Mark Complete
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
								prod.getDocumentMarkCompleteBtn().Enabled() ;}}), Input.wait30);
						prod.getDocumentMarkCompleteBtn().Click();

						//Total documents 
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							!(prod.getTotalDocProduction().getText()).equals("0") ;}}), Input.wait30); 
						Assert.assertEquals(prod.getTotalDocProduction().getText(), "1223"); 
					}
			
	
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDocumentNextBtn().Enabled()  ;}}), Input.wait30);
				prod.getDocumentNextBtn().Click();
			
				pass(dataMap,"Succesfully verify_the document_selection_component_displays_the_correct_default_options");
		} else {
				fail(dataMap,"NOT Succesfully verify_the document_selection_component_displays_the_correct_default_options");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_document_selection_select_folders_option$")
	public void clicking_the_document_selection_select_folders_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the radio button Select Folders:
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getFolderRadioButton().Enabled()  ;}}), Input.wait30);
				prod.getFolderRadioButton().Click();
				dataMap.put("DocumentRadioBtn", "selectedFolderBtn");
				pass(dataMap,"You succesfully clicked the Folder Options");
			
		} else {
			fail(dataMap,"You unsuccesfully clicked the Folder Options");

		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_document_selection_select_tags_option$")
	public void clicking_the_document_selection_select_tags_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the radio button Select Tags:
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTagsRadioButton().Displayed()  ;}}), Input.wait30);
				prod.getTagsRadioButton().Click();
				dataMap.put("DocumentRadioBtn", "selectTagsBtn");
				pass(dataMap,"You succesfully clicked the Tags Options");
		} else {
			fail(dataMap,"You unsuccesfully clicked the Tags Options");
		}

	}

	@When("^.*(\\[Not\\] )? selecting_the_production$")
	public void selecting_the_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState){
			
			try {
				String viewMode = (String)dataMap.get("mode");
				String status = (String)dataMap.get("status");
				int index = 1;
				if(status.equalsIgnoreCase("INPROGRESS")) index = 2;
				else if(status.equalsIgnoreCase("COMPLETED")) index = 4;
				//Just Need to Select, if we are in Grid mode, Tile Mode has no Select
				if(viewMode != null && viewMode.equals("grid")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionListGridViewTable().Displayed()  ;}}), Input.wait30);

					//Get Filter dropdown
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getFilterByButton().Enabled()  ;}}), Input.wait30);
					prod.getFilterByButton().Click();
					//Deselect Filters we dont want
					for(int i =1; i<=4; i++) {
						if(prod.getFilter(i).Selected() && i!= index) prod.getFilter(i).Click();
					}

					//Select our filter, if it isn't already
					if(!prod.getFilter(index).Selected()) prod.getFilter(index).Click();
					
					driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
					//Click out of dropdown, then wait for table to update, and click first element 
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionListGridViewTableRows(0).findElements(By.tagName("td")).get(1).getText().equalsIgnoreCase(status)  ;}}), Input.wait30);
					prod.getProductionListGridViewTableRows(0).click();
				}
				pass(dataMap, "Selected the production based on grid view");
				
			}
			catch(Exception e) {e.printStackTrace();}
			
		}
		else fail(dataMap,"Could Not Select Production");

	}


	@When("^.*(\\[Not\\] )? locking_the_production$")
	public void locking_the_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the clog/settings icon to the right of the production's nameClick LockClick OK on the warning messageThe home page will refresh and the filter will reset.Filter the dropdown to show only Completed productions again.Verify the icon changed to the locked icon.
			throw new ImplementationException("locking_the_production");
		} else {
			throw new ImplementationException("NOT locking_the_production");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_locked_production_is_set_to_read_only$")
	public void verify_the_locked_production_is_set_to_read_only(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 3412 / 3509
			//* Click the locked Production's name.
			//* Try and click Mark Incomplete on Quality Control and Confirmation. It should not let you.
			//* Click Back to go to the Generate section.
			//* Try and click Mark Incomplete on Quality Control and Confirmation. It should not let you.
			//* Do the same until you get to "Production Components".
			//* Afterwards go to the production's homepage and unlock the production to "reset the test".
			//
			throw new ImplementationException("verify_the_locked_production_is_set_to_read_only");
		} else {
			throw new ImplementationException("NOT verify_the_locked_production_is_set_to_read_only");
		}

	}


	@And("^.*(\\[Not\\] )? on_the_manage_templates_tab$")
	public void on_the_manage_templates_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Manage Templates
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getManageTemplatesTab().Displayed() && prod.getManageTemplatesTab().Enabled() ;}}), Input.wait30);
				prod.getManageTemplatesTab().Click();
			}
			catch(Exception e) {fail(dataMap,"Could not click Manage Templates Tab");}
			//throw new ImplementationException("on_the_manage_templates_tab");
		} else {
			throw new ImplementationException("NOT on_the_manage_templates_tab");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_view_on_the_production_template$")
	public void clicking_view_on_the_production_template(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click "View" on the Template named "DefaultAutomationTemplate".
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDefaultAutomationTemplateView().Displayed() && prod.getDefaultAutomationTemplateView().Enabled() ;}}), Input.wait30);
				prod.getDefaultAutomationTemplateView().Click();		
			}
			catch(Exception e) {fail(dataMap, "Could not click View button for template named DefaultAutomationTemplate");}
			//throw new ImplementationException("clicking_view_on_the_production_template");
		} else {
			throw new ImplementationException("NOT clicking_view_on_the_production_template");
		}

	}


	@Then("^.*(\\[Not\\] )? the_default_production_template_loaded_successfully$")
	public void the_default_production_template_loaded_successfully(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4913Verify the user is able to open a saved template from the Manage Template tab.
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getSavedTemplate().Displayed()  ;}}), Input.wait30);
				prod.getSavedTemplate().Visible();
				pass(dataMap,"User is able to open a saved template from the Manage Template tab.");
			}
			catch(Exception e) {fail(dataMap, "Could not open saved template from Manage Template tab."); }
			//throw new ImplementationException("the_default_production_template_loaded_successfully");
		} else {
			throw new ImplementationException("NOT the_default_production_template_loaded_successfully");
		}

	}


	@And("^.*(\\[Not\\] )? on_the_summary_preview_section$")
	public void on_the_summary_preview_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Verify you are on the Summary and Preview section. If you are not, click Next until you can.
			throw new ImplementationException("on_the_summary_preview_section");
		} else {
			throw new ImplementationException("NOT on_the_summary_preview_section");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_preview_button$")
	public void clicking_the_productions_preview_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("clicking_the_productions_preview_button");
		} else {
			throw new ImplementationException("NOT clicking_the_productions_preview_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_preview_of_the_pdf_should_display_the_branding$")
	public void verify_the_preview_of_the_pdf_should_display_the_branding(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5096 
			//* Verify the branding on the PDF should display from the Tiff section information provided
			//
			throw new ImplementationException("verify_the_preview_of_the_pdf_should_display_the_branding");
		} else {
			throw new ImplementationException("NOT verify_the_preview_of_the_pdf_should_display_the_branding");
		}

	}


	@And("^.*(\\[Not\\] )? navigated_back_onto_the_production_components_section$")
	public void navigated_back_onto_the_production_components_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click on the production you want to openClick the "<Back" link as many times as it takes to get back to the "Production Components" section of Productions
			throw new ImplementationException("navigated_back_onto_the_production_components_section");
		} else {
			throw new ImplementationException("NOT navigated_back_onto_the_production_components_section");
		}

	}


	@And("^.*(\\[Not\\] )? editing_the_completed_production_component$")
	public void editing_the_completed_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click "Mark Incomplete"Change the DAT configuration to have the DAT field to be "CNG"Click "Mark Complete"
			throw new ImplementationException("editing_the_completed_production_component");
		} else {
			throw new ImplementationException("NOT editing_the_completed_production_component");
		}

	}


	@When("^.*(\\[Not\\] )? navigating_back_to_the_generate_section$")
	public void navigating_back_to_the_generate_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click "Next" until you get to the Generate tab
			throw new ImplementationException("navigating_back_to_the_generate_section");
		} else {
			throw new ImplementationException("NOT navigating_back_to_the_generate_section");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_production_can_be_regenerated$")
	public void verify_the_production_can_be_regenerated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5075
			//* Verify the Generate button is enabled
			//* Verify clicking the Generate button will change the status to "Pre generation check in progress" with the button changed to "in progress".
			//
			throw new ImplementationException("verify_the_production_can_be_regenerated");
		} else {
			throw new ImplementationException("NOT verify_the_production_can_be_regenerated");
		}

	}


	@When("^.*(\\[Not\\] )? c$")
	public void navigating_to_the_production_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			prod.getProductionGridViewActionDropDown().click();
			prod.getProductionGridViewActionOpenWizard().click();
			pass(dataMap, "getProductionGridViewActionOpenWizard");
		} else {
			throw new ImplementationException("NOT navigating_to_the_production_selected");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_document_level_numbers_displays_bate_number_on_production_home_page$")
	public void verify_document_level_numbers_displays_bate_number_on_production_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5081
			//* Verify the bates number on the document level numbering production that is completed is displaying correctly on the home page.
			//* It will be under the fields "Start" and "End". Those fields should have a number/value each on them representing the start number and end number.
			//
			throw new ImplementationException("verify_document_level_numbers_displays_bate_number_on_production_home_page");
		} else {
			throw new ImplementationException("NOT verify_document_level_numbers_displays_bate_number_on_production_home_page");
		}

	}


	@And("^.*(\\[Not\\] )? select_the_production_export_set$")
	public void select_the_production_export_set(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Store the first two production names in the list of productions.This will be used for validation in the outcome.
			//* Clicks the "Select a Production/Export Set" dropdown
			//* Select the option in the dropdown by Index. 0 = the first option, 1 = the second option, etc. 
			//
			throw new ImplementationException("select_the_production_export_set");
		} else {
			throw new ImplementationException("NOT select_the_production_export_set");
		}

	}


	@When("^.*(\\[Not\\] )? storing_the_productions_in_the_home_page$")
	public void storing_the_productions_in_the_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This method will just store the name of the first two productions displaying on the list of productions. If none appear, store them at "None". 
			throw new ImplementationException("storing_the_productions_in_the_home_page");
		} else {
			throw new ImplementationException("NOT storing_the_productions_in_the_home_page");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_productions_from_one_production_set_does_not_exist_in_another$")
	public void verify_the_productions_from_one_production_set_does_not_exist_in_another(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5869Verify that the first two production's in the previous Production/Export set do not appear when switching to the other production set.Productions do not carry over from 1 production set to another, so they should never have 1 production in both sets.
			throw new ImplementationException("verify_the_productions_from_one_production_set_does_not_exist_in_another");
		} else {
			throw new ImplementationException("NOT verify_the_productions_from_one_production_set_does_not_exist_in_another");
		}

	}


	@And("^.*(\\[Not\\] )? the_production_grid_is_set_to_view$")
	public void the_production_grid_is_set_to_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//In the middle of the production's home page, there are two buttons. It is by default set to Tile view.Depending on the parameter, you will either click on the Grid View button or the Tile View button.
			try {

				//Get the view mode we should be in
				String viewMode = (String)dataMap.get("mode");
				
				//If grid, click grid
				if(viewMode != null && viewMode.equals("grid")){
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getGridView().Displayed() && prod.getGridView().Enabled()  ;}}), Input.wait30);
				     	prod.getGridView().Click();
				}
				//If tile, click tile
				else if(viewMode!= null && viewMode.equals("tile")){
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTileViewIcon().Displayed() && prod.getTileViewIcon().Enabled()  ;}}), Input.wait30);
				     	prod.getTileViewIcon().Click();
				}
				else fail(dataMap, "Valid view mode was not selected");
				
				pass(dataMap, "View Mode was Selected Successfully");
				
				
			}
			catch(Exception e) {e.printStackTrace();}
		}
		else fail(dataMap, "Could not set production grid view");

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_settings_button$")
	public void clicking_the_productions_settings_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//If the mode is set to Grid View:You will need to click on the "Action" dropdownIf the mode is set to Tile View:You will need to click on the Settings button on the tile of the production you are testing with.
			try {
				String viewMode = (String)dataMap.get("mode");
				if(viewMode != null && viewMode.equals("grid")){
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionGridViewActionDropDown().Enabled()  ;}}), Input.wait30);
					prod.getProductionGridViewActionDropDown().Click();
				}
				else if(viewMode!= null && viewMode.equals("tile")) {
					//Implement this Later
				}
				else fail(dataMap, "A Valid View mode was not selected");
				pass(dataMap, "Settings button for a production was Successful");
				
			}
			catch(Exception e) {e.printStackTrace();}
		}
		else fail(dataMap, "Could not click productions settings buttons");

	}


	@Then("^.*(\\[Not\\] )? verify_the_productions_allowed_actions_in_settings_based_on_status$")
	public void verify_the_productions_allowed_actions_in_settings_based_on_status(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4911 / 3509If Status is DRAFT:
			//* Verify the settings button will display the following options are active/available to be selected:

			//* Open in Wizard
			//* Delete
			//* Save as Template
			//* Lock is disabled

			//* If in Grid View also check for:
			//* "Add Docs"
			//* "Remove Docs"
			//* Lock is disabled
			//If Status is INPROGRESS:
			//* Verify the settings button will display the following options are active/available to be selected:

			//* Open in Wizard
			//* Save as Template
			//* Lock is disabled

			//* If in Grid View also check for:
			//* "Add Docs"
			//* "Remove Docs"
			//* Lock is disabled
			//If Status is COMPLETED:
			//* Verify the settings button will display the following options are active/available to be selected:

			//* Open in Wizard
			//* Lock
			//* Save as Template

			//* If in Grid View also check for:
			//* "Add Docs"
			//* "Remove Docs"
			//* If in Grid View, also check that each Completed production display a Start Date and End Date. None should be empty in Completed Status.
			try {
				String status = (String)dataMap.get("status");
				String viewMode = (String)dataMap.get("mode");
				if(status != null && viewMode.equalsIgnoreCase("grid")){
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionGridViewActionOpenWizard().Displayed()  ;}}), Input.wait30);

					//Open in Wizard is Displayed and Enabled For all Status
					Assert.assertTrue(prod.getProductionGridViewActionOpenWizard().Displayed() &&
							!prod.getProductionGridViewActionOpenWizard().GetAttribute("class").contains("disable"));

					//Save Template is Displayed and Enabled For all Status
					Assert.assertTrue(prod.getProductionGridViewActionSaveTemplate().Displayed() &&
						 prod.getProductionGridViewActionSaveTemplate().Enabled());


					//Delete is Displayed and Enabled for Drafts only
					if(status.equalsIgnoreCase("DRAFT")) {
						Assert.assertTrue(prod.getProductionGridViewActionDelete().Displayed() &&
							!prod.getProductionGridViewActionDelete().GetAttribute("class").contains("disable"));
					}
					//Delete is Displayed and NOT Enabled for InProgress
					else if(status.equalsIgnoreCase("INPROGRESS")){
						Assert.assertTrue(prod.getProductionGridViewActionDelete().Displayed() &&
							prod.getProductionGridViewActionDelete().GetAttribute("class").contains("disable"));
					}
					
					//Checks that Draft and Inprogess have in Common
					if(status.equalsIgnoreCase("DRAFT") || status.equalsIgnoreCase("INPROGRESS")) {
						
						//Lock is Displayed and NOT Enabled
						Assert.assertTrue(prod.getProductionGridViewActionLock().Displayed() &&
							prod.getProductionGridViewActionLock().GetAttribute("class").contains("disable"));
						
						//Add Doc is Displayed and NOT Enabled
						Assert.assertTrue(prod.getProductionGridViewActionAddDoc().Displayed() &&
							prod.getProductionGridViewActionAddDoc().GetAttribute("class").contains("disable"));

						//Remove Doc is Displayed and NOT Enabled
						Assert.assertTrue(prod.getProductionGridViewActionRemoveDoc().Displayed() &&
							prod.getProductionGridViewActionRemoveDoc().GetAttribute("class").contains("disable"));

					}
					//Check Completed 
					else if(status.equalsIgnoreCase("COMPLETED")){
						//Lock is Displayed and Enabled
						Assert.assertTrue(prod.getProductionGridViewActionLock().Displayed() &&
							!prod.getProductionGridViewActionLock().GetAttribute("class").contains("disable"));

						//Add Doc is Displayed
						Assert.assertTrue(prod.getProductionGridViewActionAddDoc().Displayed());

						//Remove Doc is Displayed
						Assert.assertTrue(prod.getProductionGridViewActionRemoveDoc().Displayed());
						
						//Make Sure Start Time is Not Empty
						Assert.assertFalse(prod.getProductionListGridViewTableRows(0).findElements(By.tagName("td")).get(5).getText().equals(""));

						//Make Sure End Time is Not Empty
						Assert.assertFalse(prod.getProductionListGridViewTableRows(0).findElements(By.tagName("td")).get(6).getText().equals(""));
					}
				}
				
			}
			catch(Exception e) {e.printStackTrace();}
		}
		else fail(dataMap, "Could not verify Production Settings Options");

	}
	
	@Given("^.*(\\[Not\\] )? verify_the_next_bates_number_generation_is_stored_in_the_correct_fields$")
	public void verify_the_next_bates_number_generation_is_stored_in_the_correct_fields(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_the_next_bates_number_generation_is_stored_in_the_correct_fields");
		} else {
			throw new ImplementationException("NOT verify_the_next_bates_number_generation_is_stored_in_the_correct_fields");
		}

	}


	@And("^.*(\\[Not\\] )? selecting_a_different_generated_bates_number$")
	public void selecting_a_different_generated_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Mark IncompleteClick on the Click Here linkSelect the first option in the bates number grid
			throw new ImplementationException("selecting_a_different_generated_bates_number");
		} else {
			throw new ImplementationException("NOT selecting_a_different_generated_bates_number");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_mark_complete_button$")
	public void clicking_the_productions_mark_complete_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("clicking_the_productions_mark_complete_button");
		} else {
			throw new ImplementationException("NOT clicking_the_productions_mark_complete_button");
		}

	}


	@And("^.*(\\[Not\\] )? complete_specifying_the_next_bates_number$")
	public void complete_specifying_the_next_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click the link named "Click here" hunter the format section
			//* Store the second bates number listed as two values.
			//* The first should be the letter the number starts with. That should be stored as the "Prefix".
			//* The rest of the bates number should be stored as "beginning_bates"
			//* Example: A500.
			//* A is the Prefix
			//* 500 is the Beginning Bates
			//* Click Select on the second bates number listed.
			//
			throw new ImplementationException("complete_specifying_the_next_bates_number");
		} else {
			throw new ImplementationException("NOT complete_specifying_the_next_bates_number");
		}

	}


	@And("^.*(\\[Not\\] )? complete_default_production_location_component$")
	public void complete_default_production_location_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//1. Set the Production Root Path: to the second option in the dropdown.     1 a. The first option in the dropdown is "Select", so that is why we want it to default to the second.2. Type in a Production Directory:      2 a. The directory should be "Automation" + random 7 digit number + _ + dir     2 b. Ex: Automation5264345_dirMake sure to store the value for the Root Path and directory as it will be used later3. Click Mark Complete4. Click Next
			throw new ImplementationException("complete_default_production_location_component");
		} else {
			throw new ImplementationException("NOT complete_default_production_location_component");
		}

	}


	@And("^.*(\\[Not\\] )? completed_summary_preview_component$")
	public void completed_summary_preview_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Verify you are on the Summary and Preview page.Click Mark CompleteClick Next
			throw new ImplementationException("completed_summary_preview_component");
		} else {
			throw new ImplementationException("NOT completed_summary_preview_component");
		}

	}


	@And("^.*(\\[Not\\] )? starting_the_production_generation$")
	public void starting_the_production_generation(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the Generate button
			throw new ImplementationException("starting_the_production_generation");
		} else {
			throw new ImplementationException("NOT starting_the_production_generation");
		}

	}


	@When("^.*(\\[Not\\] )? waiting_for_production_to_be_in_progress$")
	public void waiting_for_production_to_be_in_progress(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("waiting_for_production_to_be_in_progress");
		} else {
			throw new ImplementationException("NOT waiting_for_production_to_be_in_progress");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_using_the_next_bates_generations_a_production_successfully$")
	public void verify_using_the_next_bates_generations_a_production_successfully(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4775If the bates range does generate, below is an explanation of what the number should look like based on the custom numbering we provided in "custom_number_and_sorting_is_added". We need to verify all of the data in the parameter we used is in the bates number itself in the correct order.For example, if you specify 1001 as the Beginning Bates #, "B" for Prefix, "T" for Suffix, and "8" for Minimum Number Length (used for number padding), then a sample bates number generated would look like "B00001001T".
			throw new ImplementationException("verify_using_the_next_bates_generations_a_production_successfully");
		} else {
			throw new ImplementationException("NOT verify_using_the_next_bates_generations_a_production_successfully");
		}

	}


	@And("^.*(\\[Not\\] )? editing_the_generated_bates_number$")
	public void editing_the_generated_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//1. Get the value stored for the beginning bates and add a random 6 digit number at the end of it. This will be your new beginning bates number.2. Get the Prefix and add the letter E to it. 3. Add the Suffix Q and store this value for later. We will verify the bates number is displayed with these options later.4. Click Mark Complete
			throw new ImplementationException("editing_the_generated_bates_number");
		} else {
			throw new ImplementationException("NOT editing_the_generated_bates_number");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_editing_the_bates_number_after_generation_is_displaying_correctly$")
	public void verify_editing_the_bates_number_after_generation_is_displaying_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8353If the bates range does generate, below is an explanation of what the number should look like based on the custom numbering we provided in "custom_number_and_sorting_is_added". We need to verify all of the data in the parameter we used is in the bates number itself in the correct order.For example, if you specify 1001 as the Beginning Bates #, "B" for Prefix, "T" for Suffix, and "8" for Minimum Number Length (used for number padding), then a sample bates number generated would look like "B00001001T".
			throw new ImplementationException("verify_editing_the_bates_number_after_generation_is_displaying_correctly");
		} else {
			throw new ImplementationException("NOT verify_editing_the_bates_number_after_generation_is_displaying_correctly");
		}

	}


	@And("^.*(\\[Not\\] )? the_numbering_is_set_to_document_with_no_sub_bates$")
	public void the_numbering_is_set_to_document_with_no_sub_bates(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Under NUMBERING, click Document Erase the value in Beginning Sub-bates NumberErase the value in Min Number Length
			throw new ImplementationException("the_numbering_is_set_to_document_with_no_sub_bates");
		} else {
			throw new ImplementationException("NOT the_numbering_is_set_to_document_with_no_sub_bates");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_document_level_numbering_can_be_empty_for_sub_bates$")
	public void verify_document_level_numbering_can_be_empty_for_sub_bates(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5188Verify when marking the numbering and sorting complete, it successfully marks it completed with no error.
			throw new ImplementationException("verify_document_level_numbering_can_be_empty_for_sub_bates");
		} else {
			throw new ImplementationException("NOT verify_document_level_numbering_can_be_empty_for_sub_bates");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_save_button$")
	public void clicking_the_productions_save_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//The user should click Save from the Production page.
			throw new ImplementationException("clicking_the_productions_save_button");
		} else {
			throw new ImplementationException("NOT clicking_the_productions_save_button");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_numbering_format_click_here_link$")
	public void clicking_the_numbering_format_click_here_link(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the link named "Click here" hunter the format section
			throw new ImplementationException("clicking_the_numbering_format_click_here_link");
		} else {
			throw new ImplementationException("NOT clicking_the_numbering_format_click_here_link");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_next_bates_number_dialog_displays_correctly$")
	public void verify_the_next_bates_number_dialog_displays_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8347 
			//* Verify the dialog is named "Next Bates Numbers"
			//* Verify the production name is displayed correctly.
			//* Verify a grid should display with the title "NEXT BATES NUMBER" and "ACTION".
			//* Verify at least one bates number is listed with the button "Select" next to it.
			//
			throw new ImplementationException("verify_the_next_bates_number_dialog_displays_correctly");
		} else {
			throw new ImplementationException("NOT verify_the_next_bates_number_dialog_displays_correctly");
		}

	}
}//end


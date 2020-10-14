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
		String productionName = "AutoProduction" + dateTime;
		dataMap.put("production_name", productionName);
		try {
			if (scriptState) {
				prod.addNewProduction(productionName, template);
				dataMap.put("productionName", productionName);
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
					
			    //Added code to add another rule
				prod.getPrivAddRuleBtn().click();
				prod.getPrivTagsBtn().FindWebElements().get(1).click();
				prod.getPrivTagDefaultAutomation().click();
				prod.getPrivInsertQuery().click();

					
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivChkForMatching().Enabled() && prod.getPrivChkForMatching().Displayed()  ;}}), Input.wait30);
				prod.getPrivChkForMatching().Click();
										
				driver.waitForPageToBeReady();
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
				dataMap.put("numbering_option", "Document");
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
	
	@Then("^.*(\\[Not\\] )? verify_the_numbering_also_sorting_component_displays_the_correct_default_options$")
	public void verify_the_numbering_also_sorting_component_displays_the_correct_default_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

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
					Assert.assertTrue(prod.getNumPageLevelRadioButtonCheck().Selected());
//					Assert.assertTrue(prod.getNumPageLevelRadioButton().Selected());
				
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
					prod.getNumUseMetaFieldButtonCheck().Enabled()  ;}}), Input.wait30);
				prod.getNumUseMetaFieldButtonCheck().Click();
				pass(dataMap, "Succesfully clicked useMeta Field Button");
			}
			catch(Exception e) {fail(dataMap, "Could not click UseMeta Field Button");}
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
				String prodName = "";
				if(dataMap.get("production_name") != null) prodName = (String)dataMap.get("production_name");
				String prodCount = prod.getProductionItemsTileItems().getText();

				//Use These Index's to Select the correct Status Option in the  Dropdown
				int index = 1;
				if(status.equalsIgnoreCase("INPROGRESS")) index = 2;
				else if(status.equalsIgnoreCase("FAILED")) index = 3;
				else if(status.equalsIgnoreCase("COMPLETED")) index = 4;

				

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


				//For Name Selection, this saves the target Production Tile Element into our dataMap for use in other contexts
				if(!prodName.equals("")){
					String temp  = prodName;
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionTileByName(temp).isEnabled()  ;}}), Input.wait30);
					dataMap.put("targetProduction", prod.getProductionTileByName(temp));
				}

				//Just Need to Select Row, if we are in Grid mode, Tile Mode has no Select
				else if(viewMode != null && viewMode.equals("grid")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionListGridViewTable().Displayed()  ;}}), Input.wait30);

					//Wait for table to update, and click first element 
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionListGridViewTableRows(0).findElements(By.tagName("td")).get(1).getText().equalsIgnoreCase(status)  ;}}), Input.wait30);
					prod.getProductionListGridViewTableRows(0).click();
				}
				//If we are in tile, all we need to do is wait for the filter to update the page
				else if(viewMode != null && viewMode.equalsIgnoreCase("tile")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getProductionItemsTileItems().getText().equals(prodCount) ;}}), Input.wait30);
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionTileStatusTypeText().getText().equalsIgnoreCase(status) ;}}), Input.wait30);

				}
				pass(dataMap, "Selected the production based on grid view");
				
			}
			catch(Exception e) {
				fail(dataMap, "Could not Select Prodution");
				e.printStackTrace();}
			
		}
		else fail(dataMap,"Could Not Select Production");

	}


	@When("^.*(\\[Not\\] )? locking_the_production$")
	public void locking_the_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the clog/settings icon to the right of the production's nameClick LockClick OK on the warning messageThe home page will refresh and the filter will reset.Filter the dropdown to show only Completed productions again.Verify the icon changed to the locked icon.
			try {
				//Select our target production's settings option
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					((WebElement)dataMap.get("targetProduction")).isEnabled()  ;}}), Input.wait30);
				prod.getProductionTileSettingsByName(((WebElement)dataMap.get("targetProduction"))).click();

				//Click Lock 
				prod.getLock().click();

				//Click okayMessage
				prod.getOK().click();
				driver.waitForPageToBeReady();

				//Refilter menu
				selecting_the_production(true,dataMap);

				//Make sure Element is Locked based on lock Attribute 
				Assert.assertTrue(((WebElement)dataMap.get("targetProduction")).findElement(By.cssSelector("i.fa-lock")).isDisplayed());
				
				pass(dataMap, "Clicked lock settings successfully");
			}
			catch(Exception e) {
				fail(dataMap, "Could not click lock settings button");
				e.printStackTrace();}
		}
		else fail(dataMap, "Could not click lock settings button");

	}


	@Then("^.*(\\[Not\\] )? verify_the_locked_production_is_set_to_read_only$")
	public void verify_the_locked_production_is_set_to_read_only(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//TC 3412 / 3509
		if (scriptState) {
			try {
				//Pages we use for our dynamic selectors
				String[] pages = {"Confirmation", "Generate", "ProductionSummary", "ProductionLocation",
						"ProductionGuard", "DocumentsSelection", "NumAndSort", "Components"}; 
				
				//Click into our desired production (saved in datamap from previous context)
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					((WebElement)dataMap.get("targetProduction")).isEnabled()  ;}}), Input.wait30);
				((WebElement)dataMap.get("targetProduction")).click();

				for(int i =0; i <pages.length; i++) {
					String temp = pages[i];
					if(pages[i].equals("NumAndSort") || pages[i].equals("Components")) {
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getProductionMarkIncompleteLastBtn(temp).Displayed() ;}}), Input.wait30);
						Assert.assertEquals("true",prod.getProductionMarkIncompleteLastBtn(temp).GetAttribute("disabled"));
					}
					else {
						driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getProductionMarkIncompleteBtnByPage(temp).Displayed() ;}}), Input.wait30);
						Assert.assertEquals("true",prod.getProductionMarkIncompleteBtnByPage(temp).GetAttribute("disabled"));
					}
					prod.getBackLink().click();
					driver.waitForPageToBeReady();
				}
				
				//Go back to Production Home and Filter again
				prod.goToProductionHomePage().click();
				driver.waitForPageToBeReady();
				selecting_the_production(true,dataMap);

				//Get Target Prodution's settings 
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					((WebElement)dataMap.get("targetProduction")).isEnabled()  ;}}), Input.wait30);
				prod.getProductionTileSettingsByName(((WebElement)dataMap.get("targetProduction"))).click();
				
				//Unlock production
				prod.getUnlock().click();

				//Click okayMessage
				prod.getOK().click();
				driver.waitForPageToBeReady();
				selecting_the_production(true,dataMap);

				//Make sure production is unlocked based on Element's Attribute
				Assert.assertTrue(((WebElement)dataMap.get("targetProduction")).findElement(By.cssSelector("i.fa-unlock")).isDisplayed());

				pass(dataMap, "Verified the locked productcion is read only");
			}
			catch(Exception e) {

				e.printStackTrace();
				fail(dataMap, "Could not verify the locked production set is read only");
			}
		}
		else fail(dataMap,"Could not verify the locked production set is read only");

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

		//TC4913
		if (scriptState) {
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
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getProductionSectionPageTitle().Displayed() ;}}), Input.wait30);
				//Move back till we are at our desired page
				while(!prod.getProductionSectionPageTitle().getText().equals("Summary and Preview")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getBackLink().Displayed() && prod.getBackLink().Enabled() ;}}), Input.wait30);
					prod.getBackLink().Click();
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getProductionSectionPageTitle().Displayed() ;}}), Input.wait30);
				}
				pass(dataMap, "Got to Summary and preview");
			}
			catch(Exception e) {
				fail(dataMap, "Could not get to the summar preview Section");
				e.printStackTrace();}
		}
		else fail(dataMap, "Could not get to the summar preview Section");

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_preview_button$")
	public void clicking_the_productions_preview_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getPreviewprod().Displayed() ;}}), Input.wait30);
				//Move page back to incomplete, then click Preview Button
				prod.getbtnProductionSummaryMarkInComplete().click();
				prod.getPreviewprod().click();
				driver.waitForPageToBeReady();
				pass(dataMap, "Clicked preview button");
				
			}
			catch(Exception e) {
				fail(dataMap, "Preview button was not clicked");
				e.printStackTrace();}
		}
		else fail(dataMap, "Failed to click the production preview button");

	}

	@Then("^.*(\\[Not\\] )? verify_the_preview_of_the_pdf_should_display_the_branding$")
	public void verify_the_preview_of_the_pdf_should_display_the_branding(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//TC5096 
		if (scriptState) {
				PDDocument document = null;
				String home = System.getProperty("user.home");
				if(SystemUtils.IS_OS_LINUX){
					while(document == null) document = PDDocument.load(new File(home + "/Downloads/S00012332Q.pdf"));}
				else if(SystemUtils.IS_OS_WINDOWS){
					while(document == null) document = PDDocument.load(new File(home + "\\Download\\S00012332Q.pdf"));
				}

				PDFTextStripper pdfTextStripper = new PDFTextStripper();
			
				String text = pdfTextStripper.getText(document);
			
				Pattern p = Pattern.compile("Default Tiff Branding");
				Matcher matcher = p.matcher(text);
			
				if (matcher.find()) {
				pass(dataMap, "Branding is displayed in the preview of the pdf");
				} else {
					fail(dataMap, "Branding is not displayed in the preview of the pdf");
				}
				document.close();
			
		} else {
			fail(dataMap, "Branding is not displayed in the preview of the pdf");
		}

	}


	@And("^.*(\\[Not\\] )? navigated_back_onto_the_production_components_section$")
	public void navigated_back_onto_the_production_components_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				WebElement temp  = (WebElement)dataMap.get("targetProduction");
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					temp.isDisplayed()  ;}}), Input.wait30);
				temp.click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProductionSectionPageTitle().Displayed()  ;}}), Input.wait30);
				
				while(!prod.getProductionSectionPageTitle().getText().equals("Production Components")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getBackLink().Displayed() && prod.getBackLink().Enabled() ;}}), Input.wait30);
					prod.getBackLink().Click();
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getProductionSectionPageTitle().Displayed() ;}}), Input.wait30);
					
				}
				driver.waitForPageToBeReady();
				pass(dataMap,"Navigated to Production Components section");
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "Could not click <Back link."); }
		} else {
			fail(dataMap, "Could not click <Back link."); 
		}

	}


	@And("^.*(\\[Not\\] )? editing_the_completed_production_component$")
	public void editing_the_completed_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				prod.getMarkIncompleteLink().click();	
				driver.waitForPageToBeReady();
				prod.getDATTab().click();
				prod.getDAT_DATField1().click();
				prod.getDAT_DATField1().SendKeys(Keys.chord(Keys.CONTROL, "a"));
				prod.getDAT_DATField1().SendKeys("CNG");
				prod.getMarkCompleteLink().click();
				pass(dataMap, "Edited the completed production component");
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "Could not click Mark Incomplete Button");}
			
		} else {
			fail(dataMap, "Could not click Mark Incomplete Button");
		}

	}


	@When("^.*(\\[Not\\] )? navigating_back_to_the_generate_section$")
	public void navigating_back_to_the_generate_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click "Next" until you get to the Generate tab
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNextButton().Displayed() && prod.getNextButton().Enabled() ;}}), Input.wait30);
				prod.getNextButton().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionSectionPageTitle().Displayed() ;}}), Input.wait30);
				while(!prod.getProductionSectionPageTitle().getText().equals("Generate")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getMarkCompleteLink().Displayed() && prod.getMarkCompleteLink().Enabled() ;}}), Input.wait30);
					prod.getMarkCompleteLink().Click();
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getNextButton().Displayed() && prod.getNextButton().Enabled() ;}}), Input.wait30);
					prod.getNextButton().Click();
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getProductionSectionPageTitle().Displayed() ;}}), Input.wait30);
				}
				pass(dataMap, "Navigated back to the generate section");
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "Could not click Next button");}
		} else {
			fail(dataMap, "Could not click Next button");

		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_production_can_be_regenerated$")
	public void verify_the_production_can_be_regenerated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5075
			try {
				//* Verify the Generate button is enabled
				prod.getGenerateButton().click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getGeneratePostGenStatus().isDisplayed() ;}}), Input.wait30);
				
				int i =0;
				while(!prod.getGeneratePostGenStatus().getText().equals("Post generation check complete") && i<50){
					i++;
					driver.getWebDriver().navigate().refresh();
					driver.waitForPageToBeReady();
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getGeneratePostGenStatus().isDisplayed() ;}}), Input.wait30);
				}
				Assert.assertEquals(prod.getGeneratePostGenStatus().getText(), "Post generation check complete");
				pass(dataMap, "Production was regenerated");
			}
			catch(Exception e) { 
				e.printStackTrace();
				fail(dataMap,"Did not regenerate");
			}
		} else {
			fail(dataMap, "Could not regenerate");
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
			try {
				driver.waitForPageToBeReady();
				//Grab The numbers under the Start and End tags on the given production Tile
				String startBates = (prod.getProductionTileViewBates(0).getText().split("START"))[1].split("END")[0];
				String endBates  = (prod.getProductionTileViewBates(0).getText().split("END"))[1].split("END")[0];

				//Make Sure both Start and End have a Value to them
				Assert.assertFalse(startBates.equals(""));
				Assert.assertFalse(endBates.equals(""));
				pass(dataMap, "Verified Bates Number");
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "Could not verify Bates Number");
			}
		}
		else fail(dataMap, "Could not verify Bates number");

	}


	@And("^.*(\\[Not\\] )? select_the_production_export_set$")
	public void select_the_production_export_set(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			int index = Integer.parseInt((String)dataMap.get("index"));
			String[] firstSetNames = new String[2]; 
			firstSetNames[0] = prod.getProductionTileNameByIndex(0);
			firstSetNames[1] = prod.getProductionTileNameByIndex(1);
			dataMap.put("firstSet", firstSetNames);

			//Click and wait for dropdown
			prod.getProdExport_ProductionSets().click();
			prod.clickProductionSetByIndex(index);
			driver.waitForPageToBeReady();
			pass(dataMap, "Stored prod names, and clicked the correct export set");
		}

		else fail(dataMap, "Could not select the production export set");

	}


	@When("^.*(\\[Not\\] )? storing_the_productions_in_the_home_page$")
	public void storing_the_productions_in_the_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String[] secondSetNames = new String[2];
			
			//Grab the names of the first two production tiles on this Set page, if there isn't one, assign name as None
			if(prod.getProductionTileNameByIndex(0) == null)secondSetNames[0] = "None1";
			else secondSetNames[0] = prod.getProductionTileNameByIndex(0);

			if(prod.getProductionTileNameByIndex(1) == null)secondSetNames[1] = "None2";
			else secondSetNames[1] = prod.getProductionTileNameByIndex(1);

			dataMap.put("secondSet", secondSetNames);
			pass(dataMap, "Saved the productions from the second set successfully");
		}
		else fail(dataMap, "Could not store the productions from the second export set");

	}


	@Then("^.*(\\[Not\\] )? verify_the_productions_from_one_production_set_does_not_exist_in_another$")
	public void verify_the_productions_from_one_production_set_does_not_exist_in_another(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Put all four of our names into a Set
			HashSet<String> productionSet = new HashSet<>();
			productionSet.add(((String[])dataMap.get("firstSet"))[0]);
			productionSet.add(((String[])dataMap.get("firstSet"))[1]);
			productionSet.add(((String[])dataMap.get("secondSet"))[0]);
			productionSet.add(((String[])dataMap.get("secondSet"))[1]);

			//If the productions are unique, the set will be of size 4
			Assert.assertEquals(4, productionSet.size());
			pass(dataMap, "Sets were unique");
		}
		else fail(dataMap, "Sets were not unique");

	}


	@And("^.*(\\[Not\\] )? the_production_grid_is_set_to_view$")
	public void the_production_grid_is_set_to_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
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
			try {
				String viewMode = (String)dataMap.get("mode");
				if(viewMode != null && viewMode.equals("grid")){
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionGridViewActionDropDown().Enabled()  ;}}), Input.wait30);
					prod.getProductionGridViewActionDropDown().Click();
				}
				else if(viewMode!= null && viewMode.equalsIgnoreCase("tile")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getprod_ActionButton().Enabled()  ;}}), Input.wait30);
					prod.getprod_ActionButton().Click();
				}
				else fail(dataMap, "A Valid View mode was not selected");
				pass(dataMap, "Settings button for a production was Successful");
				
			}
			catch(Exception e) {
				fail(dataMap, "Could not click productions settings buttons");
				e.printStackTrace();}
		}
		else fail(dataMap, "Could not click productions settings buttons");

	}


	@Then("^.*(\\[Not\\] )? verify_the_productions_allowed_actions_in_settings_based_on_status$")
	public void verify_the_productions_allowed_actions_in_settings_based_on_status(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			try {
				String status = (String)dataMap.get("status");
				String viewMode = (String)dataMap.get("mode");

				//Grid view verifications
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
					//Check Completed Status  
					else if(status.equalsIgnoreCase("COMPLETED")){
						//Lock is Displayed and Enabled
						if(prod.getProductionGridViewActionUnLock().Displayed()) {
							Assert.assertFalse(prod.getProductionGridViewActionUnLock().GetAttribute("class").contains("disable"));
						}
						else {
							Assert.assertTrue(prod.getProductionGridViewActionLock().Displayed() &&
								!prod.getProductionGridViewActionLock().GetAttribute("class").contains("disable"));
						}

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
				
				//Tile uses seperate CSS selectors, same logic follows, minus Add/Remove Docs, and Start-End time
				else if(status != null && viewMode.equalsIgnoreCase("tile")) {
					//Open in Wizard is Displayed and Enabled For all Status
					Assert.assertTrue(prod.getOpenWizard().Displayed() && prod.getOpenWizard().Enabled() && !prod.getOpenWizard().GetAttribute("class").contains("disable"));

					//Save Template Is Displayed and Enabled for all Status
					Assert.assertTrue(prod.getSaveTemplate().Displayed() &&
						 !prod.getSaveTemplate().GetAttribute("class").contains("disable"));

					//Delete is Displayed and Enabled for Drafts only
					if(status.equalsIgnoreCase("DRAFT")) {
						Assert.assertTrue(prod.getDelete().Displayed() &&
							!prod.getDelete().GetAttribute("class").contains("disable"));
					}
					//Delete is Displayed and NOT Enabled for InProgress
					else if(status.equalsIgnoreCase("INPROGRESS")){
						Assert.assertTrue(prod.getDelete().Displayed() &&
							prod.getDelete().GetAttribute("class").contains("disable"));
					}
					//Check Lock for Draft and Inprogess
					if(status.equalsIgnoreCase("DRAFT") || status.equalsIgnoreCase("INPROGRESS")){
						//Lock is Displayed and NOT Enabled
						Assert.assertTrue(prod.getLock().Displayed() &&
							prod.getLock().GetAttribute("class").contains("disable"));
					}	
					else if(status.equalsIgnoreCase("COMPLETED")){
						//Lock is Displayed and Enabled
						if(prod.getUnlock().Displayed()) Assert.assertTrue(!prod.getLock().GetAttribute("class").contains("disable"));
						else {
						Assert.assertTrue(prod.getLock().Displayed() &&
							!prod.getLock().GetAttribute("class").contains("disable"));
						}
					}
				}
				pass(dataMap, "Verified Production Settings Options");
				
			}
			catch(Exception e) {
				fail(dataMap, "Settings could not be verified");
				e.printStackTrace();}
		}
		else fail(dataMap, "Could not verify Production Settings Options");

	}
	
	@Given("^.*(\\[Not\\] )? verify_the_next_bates_number_generation_is_stored_in_the_correct_fields$")
	public void verify_the_next_bates_number_generation_is_stored_in_the_correct_fields(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		
		if (scriptState) {
			//Verify the field "Beginning Bates #" is populated with "beginning_bates"
			//Verify the field "Prefix" is populated with "prefix'
			//Verify the "Click here" link is not displayed when the component was marked Completed

			try {		
				String beginningBatesNumberInput = prod.getBeginningBates().GetAttribute("value");
				String prefixInput = prod.gettxtBeginningBatesIDPrefix().GetAttribute("value");
				String beginningBatesNumber = dataMap.get("beginning_bates_number").toString();

				
				if (dataMap.get("prefix") == null) {
					Assert.assertEquals(prefixInput, "");
				} else {
					String prefix = dataMap.get("prefix").toString();
					Assert.assertEquals(prefix, prefixInput);
				};
				
				Assert.assertEquals(beginningBatesNumber, beginningBatesNumberInput);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getClickHereToViewNextBatesNumbers().Displayed()  ;}}), Input.wait30); 
				Assert.assertFalse(prod.getClickHereToViewNextBatesNumbers().Displayed());
				pass(dataMap, "Prefix value is stored correctly!");
			} catch (Exception e) {
				fail(dataMap, "Prefix value is not stored correctly!");
			}
			
		} else {
			fail(dataMap, "Could not verify fields");
		}

	}


	@And("^.*(\\[Not\\] )? selecting_a_different_generated_bates_number$")
	public void selecting_a_different_generated_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Mark IncompleteClick on the Click Here link
			//Select the first option in the bates number grid
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getMarkIncompleteButton().Enabled()  ;}}), Input.wait30); 
			prod.getMarkIncompleteButton().Click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getMarkCompleteButton().Enabled()  ;}}), Input.wait30); 
			
			prod.openNextBatesNumbersDialog();

			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getFirstBatesNumberValue().Displayed()  ;}}), Input.wait30); 
			String firstBatesNumber = prod.getFirstBatesNumberValue().getText();
			prod.getFirstBatesNumberSelectButton().click();
			

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getBeginningBates().Enabled()  ;}}), Input.wait30); 

			String beginningBatesNumber = "";
			String prefix;
			
			// Check if batesNumber has prefix 
			if (Character.isLetter(firstBatesNumber.charAt(0))) {

				beginningBatesNumber = firstBatesNumber.substring(1);
				prefix = firstBatesNumber.substring(0, 1);
			} else {
				beginningBatesNumber = firstBatesNumber;
				prefix = null;
			}

			dataMap.put("beginning_bates_number", beginningBatesNumber);
			dataMap.put("prefix", prefix);
			
		} else {
			throw new ImplementationException("NOT selecting_a_different_generated_bates_number");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_mark_complete_button$")
	public void clicking_the_productions_mark_complete_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			try {
				driver.FindElementByTagName("body").SendKeys(Keys.HOME.toString());
				Actions builder = new Actions(driver.getWebDriver());
				builder.moveToElement(prod.getMarkCompleteButton().getWebElement()).perform();
				prod.getMarkCompleteButton().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkCompleteSuccessfulText().Displayed()  ;}}), Input.wait30); 
			} catch (Exception e) {
				e.printStackTrace();
				fail(dataMap, "Unable to click the Mark Complete button");
			}

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

			try {

				prod.openNextBatesNumbersDialog();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSecondBatesNumberValue().Displayed()  ;}}), Input.wait30); 
				String batesNumber = prod.getSecondBatesNumberValue().getText();
				dataMap.put("bates_number", batesNumber);
				
				String secondBatesNumber = prod.getSecondBatesNumberValue().getText();
				String beginningBatesNumber = secondBatesNumber.substring(1);
				String prefix = secondBatesNumber.substring(0, 1);

				prod.getSecondBatesNumberSelectButton().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getBeginningBates().Enabled()  ;}}), Input.wait30); 

				dataMap.put("beginning_bates_number", beginningBatesNumber);
				dataMap.put("prefix", prefix);
				
				
			} catch (Exception e) {
				fail(dataMap,"Unable to complete specifying the next bates number");
			}
		} else {
			throw new ImplementationException("NOT complete_specifying_the_next_bates_number");
		}

	}

	@And("^.*(\\[Not\\] )? marking_complete_the_next_available_bates_number$")
	public void marking_complete_the_next_available_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumAndSortMarkComplete().Enabled()  ;}}), Input.wait30); 
				prod.getNumAndSortMarkComplete().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkCompleteSuccessfulText().Displayed()  ;}}), Input.wait30); 

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumAndSortNextButton().Enabled()  ;}}), Input.wait30); 
				prod.getNumAndSortNextButton().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getCurrentCrumbDocumentSelection().Displayed()  ;}}), Input.wait30); 
			} catch (Exception e) {
				
			}
		} else {
			throw new ImplementationException("NOT complete_default_production_location_component");
		}

	}
	
	@And("^.*(\\[Not\\] )? mark_complete_default_priv_guard$")
	public void mark_complete_default_priv_guard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getbtnProductionGuardMarkComplete().Enabled() && prod.getPrivDefaultAutomation().Displayed()  ;}}), Input.wait30);
					prod.getbtnProductionGuardMarkComplete().Click();
					
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkCompleteSuccessfulText().Displayed()  ;}}), Input.wait30); 	
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getbtnProductionGuardNext().Enabled()  ;}}), Input.wait30); 
				prod.getbtnProductionGuardNext().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getCurrentCrumbProductionLocation().Displayed()  ;}}), Input.wait30); 
				
			} catch (Exception e) {
				
			}
		} else {
			throw new ImplementationException("NOT complete_default_production_location_component");
		}

	}

	@And("^.*(\\[Not\\] )? complete_default_production_location_component$")
	public void complete_default_production_location_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//1. Set the Production Root Path: to the second option in the dropdown.     1 a. The first option in the dropdown is "Select", so that is why we want it to default to the second.
			//2. Type in a Production Directory:      
			//2 a. The directory should be "Automation" + random 7 digit number + _ + dir     
			//2 b. Ex: Automation5264345_dir Make sure to store the value for the Root Path and directory as it will be used later
			//3. Click Mark Complete
			//4. Click Next
			
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getlstProductionRootPaths().Displayed()  ;}}), Input.wait30); 

				prod.getlstProductionRootPaths().Click();

				prod.getSecondRootPathOption().Click();
				Random rnd = new Random();
				int number = rnd.nextInt(9999999);
				String selectedRootPath = prod.getSecondRootPathOption().getText();
				String productionDirectory = "Automation" + number + "_dir";
				dataMap.put("root_path", selectedRootPath);
				dataMap.put("production_directory", productionDirectory);
				prod.getProductionOutputLocation_ProductionDirectory().SendKeys(productionDirectory);
		
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getbtnProductionLocationMarkComplete().Enabled() ;}}), Input.wait30);

				prod.getbtnProductionLocationMarkComplete().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkCompleteSuccessfulText().Displayed()  ;}}), Input.wait30); 	

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionLocationMarkIncompleteButton().Displayed()  ;}}), Input.wait30); 	

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 

				prod.getbtnProductionLocationNext().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getCurrentCrumbSummaryAndPreview().Displayed()  ;}}), Input.wait30); 
			} catch (Exception e) {
				fail(dataMap, "Unable to complete Production Location section");
			}


			
		} else {
			throw new ImplementationException("NOT complete_default_production_location_component");
		}

	}


	@And("^.*(\\[Not\\] )? completed_summary_preview_component$")
	public void completed_summary_preview_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Verify you are on the Summary and Preview page.Click Mark CompleteClick Next
			try {
				Assert.assertTrue(prod.getCurrentCrumbSummaryAndPreview().Displayed());
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getbtnProductionSummaryMarkComplete().Enabled() ;}}), Input.wait30);
				prod.getbtnProductionSummaryMarkComplete().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkCompleteSuccessfulText().Displayed() && prod.getbtnProductionSummaryNext().Enabled() ;}}), Input.wait30); 
				prod.getbtnProductionSummaryNext().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getCurrentCrumbGenerate().Displayed()  ;}}), Input.wait30); 
			} catch (Exception e) {
				fail(dataMap, "User is not on the Summery & Preview page");
			}
		} else {
			throw new ImplementationException("NOT completed_summary_preview_component");
		}

	}


	@And("^.*(\\[Not\\] )? starting_the_production_generation$")
	public void starting_the_production_generation(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		
		
		if (scriptState) {
			try {
				
				// Reset notification number back to 0
				if (!prod.getBulHornNotificationNumber().getText().equals("0")) {
					prod.getBulHornNotificationNumber().click();
				}
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getbtnProductionGenerate().Displayed()  ;}}), Input.wait30); 
				prod.getbtnProductionGenerate().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getGenerationStartedSuccessfullyText().Displayed()  ;}}), Input.wait30); 

			} catch (Exception e) {
				fail(dataMap, "Unable to start production generation");
			}
			
		} else {
			throw new ImplementationException("NOT starting_the_production_generation");
		}

	}


	@When("^.*(\\[Not\\] )? waiting_for_production_to_be_in_progress$")
	public void waiting_for_production_to_be_in_progress(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getInProgressStatus().Displayed()  ;}}), Input.wait30); 
			} catch (Exception e) {
				fail(dataMap, "Production was not set to In Progress");
			}
		} else {
			throw new ImplementationException("NOT waiting_for_production_to_be_in_progress");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_using_the_next_bates_generations_a_production_successfully$")
	public void verify_using_the_next_bates_generations_a_production_successfully(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4775If the bates range does generate, below is an explanation of what the number should look like based on the custom numbering we provided in 
			//"custom_number_and_sorting_is_added". We need to verify all of the data in the parameter we used is in the bates number itself in the correct order.
			//For example, if you specify 1001 as the Beginning Bates #, "B" for Prefix, "T" for Suffix, and "8" for Minimum Number Length (used for number padding), 
			//then a sample bates number generated would look like "B00001001T".

			// when using refresh() function after starting a production generation, the page reloads back to 
			// the Basic Info screen. As a workaround, instead of refreshing the page, the test will keep clicking
			// the Productions navigation link until the bullhorn notification icon gets updated indicating the
			// Production has been completed
			int i =0;
			while(!prod.getBulHornNotificationNumber().getText().equals("1") && i<50){
				prod.getProductionsNavLink().click();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getRedBullHorn().Displayed() ;}}), Input.wait30);
			}

			
			driver.FindElementByXPath("//a[@title='"+ dataMap.get("production_name") +"']").click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getExportBatesButton().Enabled() ;}}), Input.wait30); 
			
			String batesRange = prod.getProd_BatesRange().getText();

			String[] parts = batesRange.split("-");
			String firstNumber = parts[0].trim();

			String batesNumber = dataMap.get("bates_number").toString();

			try {
				Assert.assertEquals(firstNumber, batesNumber);
				pass(dataMap, "Bates number equals starting Bates number");
			} catch (Exception e) {
				fail(dataMap, "Bates number does not equal starting Bates number");
			}
			

		} else {
			throw new ImplementationException("NOT verify_using_the_next_bates_generations_a_production_successfully");
		}

	}


	@And("^.*(\\[Not\\] )? editing_the_generated_bates_number$")
	public void editing_the_generated_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//1. Get the value stored for the beginning bates and add a random 6 digit number at the end of it. This will be your new beginning bates number.
			//2. Get the Prefix and add the letter E to it. 
			//3. Add the Suffix Q and store this value for later. We will verify the bates number is displayed with these options later.
			//4. Click Mark Complete
			Random rnd = new Random();
			int number = rnd.nextInt(999999);
			String beginningBatesNumber = dataMap.get("beginning_bates_number").toString();
			String prefix = dataMap.get("prefix").toString();
			
			String newBeginningBatesNumber = beginningBatesNumber + number;
			String newPrefix = prefix + "E";
			System.out.println("new number: " + newBeginningBatesNumber);
			System.out.println("newPrefix: " + newPrefix);
			String newBatesNumber = newPrefix + newBeginningBatesNumber + "Q";
			dataMap.put("bates_number", newBatesNumber);
			prod.getBeginningBates().Clear();
			prod.gettxtBeginningBatesIDPrefix().Clear();
			
			
			prod.getBeginningBates().SendKeys(newBeginningBatesNumber);
			prod.gettxtBeginningBatesIDPrefix().SendKeys(newPrefix);
			prod.gettxtBeginningBatesIDSuffix().SendKeys("Q");
			prod.getMarkCompleteButton().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNextButton().Enabled() ;}}), Input.wait30);
			prod.getNextButton().click();
			
			
		} else {
			throw new ImplementationException("NOT editing_the_generated_bates_number");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_editing_the_bates_number_after_generation_is_displaying_correctly$")
	public void verify_editing_the_bates_number_after_generation_is_displaying_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			int i =0;
			while(!prod.getBulHornNotificationNumber().getText().equals("1") && i<50){
				prod.getProductionsNavLink().click();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getRedBullHorn().Displayed() ;}}), Input.wait30);
			}
			
			driver.FindElementByXPath("//a[@title='"+ dataMap.get("production_name") +"']").click();

			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getExportBatesButton().Enabled() ;}}), Input.wait30); 
			
			String batesRange = prod.getProd_BatesRange().getText();

			String[] parts = batesRange.split("-");
			String firstNumber = parts[0].trim();

			String batesNumber = dataMap.get("bates_number").toString();

			try {
				Assert.assertEquals(firstNumber, batesNumber);
				pass(dataMap, "Bates number equals starting Bates number");
			} catch (Exception e) {
				fail(dataMap, "Bates number does not equal starting Bates number");
			}
		} else {
			throw new ImplementationException("NOT verify_editing_the_bates_number_after_generation_is_displaying_correctly");
		}

	}


	@And("^.*(\\[Not\\] )? the_numbering_is_set_to_document_with_no_sub_bates$")
	public void the_numbering_is_set_to_document_with_no_sub_bates(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Under NUMBERING, click Document Erase the value in Beginning Sub-bates NumberErase the value in Min Number Length
			try {
				clicking_document_as_the_numbering_default_option(scriptState, dataMap);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumSubBatesNum().Displayed()  ;}}), Input.wait30);
				prod.getNumSubBatesNum().Clear();
				prod.getNumSubBatesMin().Clear();
			} catch (Exception e) {
				fail(dataMap, "Unable to clear input fields");
			}
		} else {
			throw new ImplementationException("NOT the_numbering_is_set_to_document_with_no_sub_bates");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_document_level_numbering_can_be_empty_for_sub_bates$")
	public void verify_document_level_numbering_can_be_empty_for_sub_bates(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5188Verify when marking the numbering and sorting complete, it successfully marks it completed with no error.
			
			try {
				Assert.assertEquals(prod.getNumSubBatesNum().GetAttribute("value"), "");
				Assert.assertEquals(prod.getNumSubBatesMin().GetAttribute("value"), "");
			} catch (Exception e) {
				fail(dataMap, "Fields are not empty!");
			}

		} else {
			try {
				Assert.assertEquals(prod.getNumSubBatesNum().GetAttribute("value"), "1");
				Assert.assertEquals(prod.getNumSubBatesMin().GetAttribute("value"), "5");
			} catch (Exception e) {
				fail(dataMap, "Fields are not empty!");
			}
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_save_button$")
	public void clicking_the_productions_save_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//The user should click Save from the Production page.
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getSaveButton().Enabled()  ;}}), Input.wait30);
			prod.getSaveButton().Click();
		} else {
			throw new ImplementationException("NOT clicking_the_productions_save_button");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_numbering_format_click_here_link$")
	public void clicking_the_numbering_format_click_here_link(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the link named "Click here" hunter the format section
			prod.openNextBatesNumbersDialog();
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
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getBatesDialogTitle().Displayed()  ;}}), Input.wait30);
				Assert.assertEquals(prod.getBatesDialogTitle().getText(), "Next Bates Numbers");
				Assert.assertEquals(prod.getBatesDialogProductionName().getText(), dataMap.get("production_name"));
				Assert.assertEquals(prod.getNextBatesNumberColumHeader().getText(), "NEXT BATES NUMBER");
				Assert.assertEquals(prod.getActionColumHeader().getText(), "ACTION");
				Assert.assertTrue(prod.getFirstBatesNumberSelectButton().Displayed());
			} catch (Exception e) {
				fail(dataMap, "Bates Number Dialog does not display the expected elements");
			}
		} else {
			throw new ImplementationException("NOT verify_the_next_bates_number_dialog_displays_correctly");
		}

	}
	
	@Then("^.*(\\[Not\\] )? delete_created_productions$")
	public void  delete_created_productions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		try {
			prod.goToProductionHomePage().click();
			driver.waitForPageToBeReady();
			
			prod.getProductionTileSettingsByName(prod.getProductionTileByName(dataMap.get("productionName").toString())).click();
			
			prod.getDelete().click();
			prod.getProductionDeleteOkButton().click();
			pass(dataMap, "Successfully deleted the target production");
		} catch (Exception e) {
			fail(dataMap, "Target production could not be deleted");
		}
	}
	
	//Not working
	@Then("^.*(\\[Not\\] )? deleteAll$")
	public void deleteAll(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		while(true) {
			selecting_the_production(true,dataMap);
			prod.getprod_ActionButton().click();
			prod.getDelete().click();
			prod.getProductionDeleteOkButton().click();


		}
	}

	
	@And("^.*(\\[Not\\] )? complete_complex_production_component$")
	public void complete_complex_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			String dat =  (String)dataMap.get("dat");
			String pdf =  (String)dataMap.get("pdf");
			String nat =  (String)dataMap.get("native");
			String tiff = (String)dataMap.get("tiff");
			String mp3 =  (String)dataMap.get("mp3");
			String text = (String)dataMap.get("text");
			Actions builder = new Actions(driver.getWebDriver());
			//Production Components page is already displayed
			// This method is a large method that will handle customization for the production components screen.
			// Basically, if a field is being passed in, we want the production component to be filled in with default data.
			// EX: if dat = true, pdf = true. The DAT and PDF section should be filled in with the data we have determined should be added based on the information below.
			//These parameters will be coming from using this method as an outcome to outcome or as a standalone. Basically, if any of the fields are true, do the steps needed to fulfill that component, else if blank or doesn't exist, skip it.
			//

			//IF DAT IS TRUE:
			//Click the DAT checkbox
			//Click the DAT tab to open the DAT container
			//Add field classification: Bates
			//Add source field: BatesNumber
			//Enter DAT field: Bates Number
			//

			if(dat!=null && dat.equalsIgnoreCase("true")){
				prod.getDATChkBox().click();
				prod.getDATTab().click();
				prod.getFieldClassification().click();
				prod.getFieldClassification().SendKeys("Bates");
				prod.getSourceField().click();
				prod.getSourceField().SendKeys("BatesNumber");
				prod.getDatField().click();
				prod.getDatField().SendKeys("Bates Number");
			}


			if(pdf!= null && pdf.equalsIgnoreCase("true")) {
				builder.moveToElement(prod.getPDFChkBox().getWebElement()).perform();
				prod.getPDFChkBox().click();
				prod.getPDFTab().click();
				prod.getPriveldge_SelectPDFTagButton().click();
				prod.getPriveldge_PDFTagTree("Privileged").click();
				prod.getPriveldge_PDFTagTree_SelectButton().click();
				prod.getPDFPlaceholderPrivDocsField().click();
				prod.getPDFPlaceholderPrivDocsField().SendKeys("Automated Placeholder");
				prod.getPDF_BurnRedtoggle().click();
				prod.getPDF_SelectRed_Radiobutton().click();
				prod.getPDFSelectRedactionsTagTree("Default Automation Redaction").click();;

			}
			
			if(tiff!= null && tiff.equalsIgnoreCase("true")) {
				builder.moveToElement(prod.getTIFFChkBox().getWebElement()).perform();
				prod.getTIFFChkBox().click();
				prod.getTIFFTab().click();
				prod.getTIFFPlaceholderPrivilegedTagsButton().click();
				prod.getPriveldge_TagTree("Privileged").click();
				prod.getPriveldge_TagTree_SelectButton().click();
				prod.getTIFFPlaceholderPrivilegedTextField().click();
				prod.getTIFFPlaceholderPrivilegedTextField().SendKeys("Automated Placeholder");
				prod.getTIFFBurnRedactionToggle().click();
				prod.getTIFF_SelectRed_Radiobutton().click();
				prod.getTIFFSelectRedactionsTagTree("Default Automation Redaction").click();
			}
			
			
			//IF NATIVE IS TRUE:
			//Check off Native
			//Click Native to expand it
			//Click SELECT ALL
			//Expand the "Advanced" option and enable "Generate Load File (LST)
			//
			//IF TIFF IS TRUE
			//Check off TIFF
			//Click TIFF to expand it
			//Click Select Tags in the "Placeholders" section.
			//Click the "Privileged" folder
			//Click Select
			//Type in "Automated Placeholder" in "Enter placeholder text for the privileged docs".
			//Toggle on "Burn Redactions"
			//Select the option "Select Redactions"
			//Check off Default Automation Redaction
			//
			//
			//IF PDF IS TRUE
			//Check off PDF
			//Click PDFto expand it
			//Click Select Tags in the "Placeholders" section.
			//Click the "Privileged" folder
			//Click Select
			//Type in "Automated Placeholder" in "Enter placeholder text for the privileged docs".
			//Toggle on "Burn Redactions"
			//Select the option "Select Redactions"
			//Check off Default Automation Redaction
			//
			//IF MP3 IS TRUE
			//Expand Advanced Production Components
			//Click the MP3 Files Checkbox
			//Enable Burn Redactions
			//Click "Select Redactions"
			//Click "Default Automation Redaction"
			//Set the "Redaction Style" to "Beep"
			//
			//IF TEXT IS TRUE
			//Checkoff the "Text" component checkbox
			//
			//The other parameters can be worked on as we use them.
			//
			//
			
			builder.moveToElement(prod.getComponentsMarkComplete().getWebElement()).perform();
			prod.getComponentsMarkComplete().Click();
					
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getConfirmCompletePopup().Displayed() ;}}), Input.wait30);
			Assert.assertTrue(prod.getConfirmCompletePopup().Displayed());
			prod.getComponentsMarkNext().Click();

			
			//At the end of the block above, the last two steps should do the following:
			//
			//Click the Mark complete button and verify the following message appears: "Mark Complete successful"
			//Click the next button
			
			
		}
		else fail(dataMap, "Failed Complex Production Component");

	}


	@And("^.*(\\[Not\\] )? remove_placeholders_on_tiff_pdf$")
	public void remove_placeholders_on_tiff_pdf(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click Back
			//* Click Mark Incomplete
			//* Expand TIFF
			//* Uncheck "Enable for Priviledged Docs:"
			//* Exapand PDF
			//* Uncheck "Enable for Priviledged Docs:".
			//* Click Mark Complete
			//* Click Next
			//
			throw new ImplementationException("remove_placeholders_on_tiff_pdf");
		} else {
			throw new ImplementationException("NOT remove_placeholders_on_tiff_pdf");
		}

	}


	@And("^.*(\\[Not\\] )? custom_number_sorting_is_added$")
	public void custom_number_sorting_is_added(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Based on the parameters:1. The parameter for this will be the amount of digits we randomize for this field. If 4 is here, that means we randomize a 4 digit number and Type the number in "Beginning Bates #"2. Type the "Prefix:" letter3. Type the suffix letter.4. Type the Min Number LengthClick Mark CompletedClick Next
			Random rnd = new Random();
			String prefix = (String) dataMap.get("prefix");
			int minLength = Integer.parseInt((String)dataMap.get("min_length"));
			String minimumNumber = Integer.toString(minLength);
			int beginningBates = Integer.parseInt((String)dataMap.get("beginning_bates"));
			String suffix = (String) dataMap.get("suffix");
			
			if(beginningBates == 1) {
				int low = 0;
				int high = 10;
				int randSingleDigit = rnd.nextInt(high-low) + low;
				String randDigit = Integer.toString(randSingleDigit);
				prod.getBeginningBates().click();
				prod.getBeginningBates().SendKeys(Keys.chord(Keys.CONTROL, "a"));
				prod.getBeginningBates().SendKeys(randDigit);
			}
			
			else if(beginningBates == 2) {
				int low = 10;
				int high = 100;
				int randDoubleDigit = rnd.nextInt(high-low) + low;
				String randDigit = Integer.toString(randDoubleDigit);
				prod.getBeginningBates().click();
				prod.getBeginningBates().SendKeys(Keys.chord(Keys.CONTROL, "a"));
				prod.getBeginningBates().SendKeys(randDigit);
			}
			
			else if(beginningBates == 3) {
				int low = 100;
				int high = 1000;
				int randTripleDigit = rnd.nextInt(high-low) + low;
				String randDigit = Integer.toString(randTripleDigit);
				prod.getBeginningBates().click();
				prod.getBeginningBates().SendKeys(Keys.chord(Keys.CONTROL, "a"));
				prod.getBeginningBates().SendKeys(randDigit);
			}
			
			else {
				int low = 1000;
				int high = 10000;
				int randQuadDigit = rnd.nextInt(high-low) + low;
				String randDigit = Integer.toString(randQuadDigit);
				prod.getBeginningBates().click();
				prod.getBeginningBates().SendKeys(Keys.chord(Keys.CONTROL, "a"));
				prod.getBeginningBates().SendKeys(randDigit);
			}
			
			prod.gettxtBeginningBatesIDPrefix().click();
			prod.gettxtBeginningBatesIDPrefix().SendKeys(prefix);
			
			prod.gettxtBeginningBatesIDSuffix().click();
			prod.gettxtBeginningBatesIDSuffix().SendKeys(suffix);
			
			prod.gettxtBeginningBatesIDMinNumLength().click();
			prod.gettxtBeginningBatesIDMinNumLength().SendKeys(minimumNumber);

			prod.getMarkCompleteLink().click();
			prod.getNextButton().click();
			
		} else {
			 fail(dataMap, "Custom number sorting is not added");
		}

	}


	@And("^.*(\\[Not\\] )? complete_document_section_with_priviledged_folder$")
	public void complete_document_section_with_priviledged_folder(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Make sure "Select Folders:" radio button is selectedClick "Default Automation Priviledged" checkboxClick Mark CompletedClick Next
			throw new ImplementationException("complete_document_section_with_priviledged_folder");
		} else {
			throw new ImplementationException("NOT complete_document_section_with_priviledged_folder");
		}

	}


	@And("^.*(\\[Not\\] )? completing_priv_guard_by_enabling_placeholders$")
	public void completing_priv_guard_by_enabling_placeholders(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//You should be on the Priv Guard section.
			//* Click Mark Complete
			//* Enable the button "Enable for Priviledged Docs"
			//* Click Select Tags
			//* Click Privileged
			//* Click Save
			//* Type in "Placeholder created on Priv Guard" (any randomized text is fine here too)
			//* Click Mark Complete
			//You should be on the Priv guard section.8. Click Back until you get back to the "Production Components" section again.
			throw new ImplementationException("completing_priv_guard_by_enabling_placeholders");
		} else {
			throw new ImplementationException("NOT completing_priv_guard_by_enabling_placeholders");
		}

	}


	@When("^.*(\\[Not\\] )? expanding_the_tiff_pdf_section_of_production_components$")
	public void expanding_the_tiff_pdf_section_of_production_components(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("expanding_the_tiff_pdf_section_of_production_components");
		} else {
			throw new ImplementationException("NOT expanding_the_tiff_pdf_section_of_production_components");
		}

	}


	@Given("^.*(\\[Not\\] )? verify_enabling_placeholders_on_priv_guard_saves_the_placeholders$")
	public void verify_enabling_placeholders_on_priv_guard_saves_the_placeholders(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("verify_enabling_placeholders_on_priv_guard_saves_the_placeholders");
		} else {
			throw new ImplementationException("NOT verify_enabling_placeholders_on_priv_guard_saves_the_placeholders");
		}

	}


	@And("^.*(\\[Not\\] )? the_production_is_started$")
	public void the_production_is_started(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Generate
			throw new ImplementationException("the_production_is_started");
		} else {
			throw new ImplementationException("NOT the_production_is_started");
		}

	}


	@When("^.*(\\[Not\\] )? refreshing_for_production_to_be_in_progress$")
	public void refreshing_for_production_to_be_in_progress(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//A loop should be created to verify if a Bates Range number is populated. This loop should check to see if there is a bates range number, if not wait 10 seconds and refresh the page. If a bates number is returned, exit the loop and the "AUTOMATED CHECK" grid should be populated with the information we need.Do this loop 10 times max, and it will fail if nothing is returned in that time
			throw new ImplementationException("refreshing_for_production_to_be_in_progress");
		} else {
			throw new ImplementationException("NOT refreshing_for_production_to_be_in_progress");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly$")
	public void verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6149Verify Priv Docs with No Placeholders should have the status "0 Docs"
			throw new ImplementationException("verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly");
		} else {
			throw new ImplementationException("NOT verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly");
		}

	}


	@And("^.*(\\[Not\\] )? complete_default_categorization_for_priv_guard$")
	public void complete_default_categorization_for_priv_guard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Run CategorizationIdentify by Production Guard Source should be selected by default.
			//Click Go to Step 2: Select Corpus To Be Analyzed.
			//Click Analyze Select Production Sets
			//Click "+Production Set"Check of any default production set listed
			//Click the "Select" buttonClick
			//"Go to Step 3: Run Categorization"A pop up might appear saying "Wait for this task to complete".Click Yes
			throw new ImplementationException("complete_default_categorization_for_priv_guard");
		} else {
			throw new ImplementationException("NOT complete_default_categorization_for_priv_guard");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_priv_guard_categorization_displays_the_correct_matched_documents_number$")
	public void verify_the_priv_guard_categorization_displays_the_correct_matched_documents_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4946 / 5005Verify the Priv Guard matched document count returns the number of Redacted Documents our "Default Automation Redaction" tag has and the number of tagged documents with "Default Automation Tag".
			throw new ImplementationException("verify_the_priv_guard_categorization_displays_the_correct_matched_documents_number");
		} else {
			throw new ImplementationException("NOT verify_the_priv_guard_categorization_displays_the_correct_matched_documents_number");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_view_results_in_doclist$")
	public void clicking_view_results_in_doclist(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.waitForPageToBeReady();
			dataMap.put("totalMatchedDocuments", prod.getTotalMatchedDocuments().getText());
			prod.getDocListButton().click();
			pass(dataMap,"Clicking view results in doc list is successful");
		} else {
			fail(dataMap,"Clicking view results in doc list is not successful");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_result_set_documents_are_displayed_in_DocList$")
	public void verify_the_result_set_documents_are_displayed_in_DocList(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4924 part 2
			// After View Results in DocList is clicked, the user should be taken to the DocList view.
			// 1. Verify the correct number of documents listed match the prior screen's count of how many documents should be displayed.
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getDocListTableEntry().Enabled() && prod.getDocListTableEntry().Displayed()  ;}}), Input.wait30);
			int expectedDocuments = Integer.parseInt(dataMap.get("totalMatchedDocuments").toString());
			int numberOfDocumentsInTable = prod.getDocListTableEntry().getWebElement().findElements(By.tagName("tr")).size();
			Assert.assertEquals(numberOfDocumentsInTable, expectedDocuments);
			pass(dataMap, "The number of documents listed matches the prior screen's count");
		} else {
			fail(dataMap, "The number of documents listed does not match the prior screen's count");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_view_results_in_docview$")
	public void clicking_view_results_in_docview(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.waitForPageToBeReady();
			dataMap.put("totalMatchedDocuments", prod.getTotalMatchedDocuments().getText());
			prod.getPrivDocViewBtn().click();
			pass(dataMap,"Clicking view results in doc view is successful");
		} else {
			fail(dataMap,"Clicking view results in doc view is not successful");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_result_set_documents_are_displayed_in_Docview$")
	public void verify_the_result_set_documents_are_displayed_in_Docview(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4924 part 3
			// After View Results in DocView is clicked, the user should be taken to the DocView.
			// 1. Verify the correct number of documents listed match the prior screen's count of how many documents should be displayed.
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getDocViewTableEntry().Enabled() && prod.getDocViewTableEntry().Displayed()  ;}}), Input.wait30);
			int expectedDocuments = Integer.parseInt(dataMap.get("totalMatchedDocuments").toString());
			int numberOfDocumentsInTable = prod.getDocViewTableEntry().getWebElement().findElements(By.tagName("tr")).size();
			Assert.assertEquals(numberOfDocumentsInTable, expectedDocuments);
			pass(dataMap, "The number of documents listed matches the prior screen's count");
		} else {
			fail(dataMap, "The number of documents listed does not match the prior screen's count");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_priv_guard_component_displays_the_correct_matched_documents_number$")
	public void verify_the_priv_guard_component_displays_the_correct_matched_documents_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4924/ 4925/ 3767Verify the Priv Guard matched document count returns the number of Redacted Documents our "Default Automation Redaction" tag has and the number of tagged documents with "Default Automation Tag".The matched documents should return 11. There are 5 redacted tags and 6 tagged equaling 11 matched documents.
			Assert.assertEquals("11", prod.getTotalMatchedDocuments().getText());
			
		}
		else fail(dataMap, "Could not Verify the Priv Guard Matched Documents");

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_name$")
	public void clicking_the_productions_name(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Using the production selected, click on the Name of the production to open it.
			throw new ImplementationException("clicking_the_productions_name");
		} else {
			throw new ImplementationException("NOT clicking_the_productions_name");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_productions_quality_control_section_display_correctly$")
	public void verify_the_productions_quality_control_section_display_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4928:Verify the name of the production displays on the page.Verify a number is generated on "Documents Generated". The number should not be 0.Verify the button "Review Production" exist on the screen.Verify the link "Confirm Production & Commit" exist on the screen.Verify on the "AUTOMATED QC CHECK" grid, the grid displays "Prod Files / Compenents", "Document and Page Counts", "Bates Number Generation", and "Blank Page Removal".
			throw new ImplementationException("verify_the_productions_quality_control_section_display_correctly");
		} else {
			throw new ImplementationException("NOT verify_the_productions_quality_control_section_display_correctly");
		}

	}


	@And("^.*(\\[Not\\] )? complete_tiff_production_component_with_branding$")
	public void complete_tiff_production_component_with_branding(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check off TIFFClick TIFF to expand itClick Select Tags in the "Placeholders" section.Click the "Privileged" folderClick SelectType in "Automated Placeholder" in "Enter placeholder text for the privileged docs".Toggle on "Burn Redactions"Select the option "Select Redactions"Check off Default Automation RedactionClick "+Specify Branding by Selecting Tags"Click Select TagsCheck off "Default Automation Tag"Click SelectEnter "Branding 1" in the first placeholder text for brandingEnter "Branding 2" in the second placeholder text for branding
			throw new ImplementationException("complete_tiff_production_component_with_branding");
		} else {
			throw new ImplementationException("NOT complete_tiff_production_component_with_branding");
		}

	}


	@And("^.*(\\[Not\\] )? complete_default_numbering_sorting$")
	public void complete_default_numbering_sorting(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Mark CompletedClick Next
			throw new ImplementationException("complete_default_numbering_sorting");
		} else {
			throw new ImplementationException("NOT complete_default_numbering_sorting");
		}

	}


	@And("^.*(\\[Not\\] )? complete_blank_priv_guard_check$")
	public void complete_blank_priv_guard_check(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//You should be on the section "Priv Guard"Verify the button "+ Add Rule" existsClick Mark CompleteClick Next
			throw new ImplementationException("complete_blank_priv_guard_check");
		} else {
			throw new ImplementationException("NOT complete_blank_priv_guard_check");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_number_of_mp3_files_are_shown_regardless_of_amount$")
	public void verify_the_number_of_mp3_files_are_shown_regardless_of_amount(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6126Verify the Count of Number of MP3 Files is shown when the number is 0
			throw new ImplementationException("verify_the_number_of_mp3_files_are_shown_regardless_of_amount");
		} else {
			throw new ImplementationException("NOT verify_the_number_of_mp3_files_are_shown_regardless_of_amount");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_summary_preview_button$")
	public void clicking_the_summary_preview_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking the Preview button
			throw new ImplementationException("clicking_the_summary_preview_button");
		} else {
			throw new ImplementationException("NOT clicking_the_summary_preview_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_preview_file_should_display_the_branding_entered$")
	public void verify_the_preview_file_should_display_the_branding_entered(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5096 / 3760A PDF file should be downloaded and opening the file should show the branding entered
			throw new ImplementationException("verify_the_preview_file_should_display_the_branding_entered");
		} else {
			throw new ImplementationException("NOT verify_the_preview_file_should_display_the_branding_entered");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_redaction_count_displays_correctly_on_the_summary_page$")
	public void verify_the_redaction_count_displays_correctly_on_the_summary_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5008The summary page should be displayed.1. Verify "Redacted Documents:" is displaying the correct count of refacted documents based on the redacted tags used in complete_tiff_production_component.If using Default Automation Redaction, 5 redactions should be returned.
			throw new ImplementationException("verify_the_redaction_count_displays_correctly_on_the_summary_page");
		} else {
			throw new ImplementationException("NOT verify_the_redaction_count_displays_correctly_on_the_summary_page");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_summary_page_displays_the_correct_information$")
	public void verify_the_summary_page_displays_the_correct_information(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5883
			//* Verify the top section of the Summary page is titled"Production Document Summary:"
			//Below the title, verify the following information is below it:
			//
			//* Total Documents: 
			//* Total Pages (Uses DocPages metadata field): 
			//* Number Of Custodians:
			//* Number of Natives: 
			//* Number of MP3 Files:
			//* Exception Documents: 
			//* Documents with Missing Text Files: 
			//* First and Last Doc IDs:
			//
			//2. Verify the second section of the Summary page is titled"Privileged Document Summary:"Below the title, verify the following information is below it:
			//
			//* Privileged Documents:
			//* Documents Identified by Production Guard:
			//* Redacted Documents:
			//
			//3.Verify the second section of the Summary page is titled"OCR/TIFF Documents:"Below the title, verify the following information is below it:
			//
			//* Documents to OCR:
			//* Docs and 0 Pages Documents to TIFF: 
			//* Docs and 0 Pages Documents Printed in Color:
			//
			throw new ImplementationException("verify_the_summary_page_displays_the_correct_information");
		} else {
			throw new ImplementationException("NOT verify_the_summary_page_displays_the_correct_information");
		}

	}
	

	@And("^.*(\\[Not\\] )? complete_the_default_dat_section$")
	public void complete_the_default_dat_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Production Components page is already displayedClick the DAT checkboxClick the DAT tab to open the DAT containerAdd field classification: BatesAdd source field: BatesNumberEnter DAT field: Bates Number
			throw new ImplementationException("complete_the_default_dat_section");
		} else {
			throw new ImplementationException("NOT complete_the_default_dat_section");
		}

	}


	@And("^.*(\\[Not\\] )? complete_the_native_section_with_tags_and_no_file_types$")
	public void complete_the_native_section_with_tags_and_no_file_types(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check NativeDo not select any file typesClick Select TagsCheck Attorney_Client, Confidential, and Default Automation TagClick Select
			throw new ImplementationException("complete_the_native_section_with_tags_and_no_file_types");
		} else {
			throw new ImplementationException("NOT complete_the_native_section_with_tags_and_no_file_types");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_native_section_with_tags_is_saving_correctly_without_file_types$")
	public void verify_native_section_with_tags_is_saving_correctly_without_file_types(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5333
			//* Expand the Native section
			//* Verify the selected tags are displaying in alphabetical order.
			//* Verify no file types are checked
			//
			throw new ImplementationException("verify_native_section_with_tags_is_saving_correctly_without_file_types");
		} else {
			throw new ImplementationException("NOT verify_native_section_with_tags_is_saving_correctly_without_file_types");
		}

	}


	@And("^.*(\\[Not\\] )? complete_tiff_pdf_with_rotation$")
	public void complete_tiff_pdf_with_rotation(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Check the "TIFF" checkbox
			//* Expand the "TIFF" section
			//* Verify the dropdown "Rotate Landscape pages to portrait layout:" is set to "No Rotation" by default.
			//* On the "Rotate Landscape pages to portrait layout:" dropdown, select Rotate 90 degrees counter clock-wise.
			//* Deselect "Enable for Privileged docs
			//* Check the "PDF" checkbox
			//* Expand the "PDF" section
			//* Verify the dropdown "Rotate Landscape pages to portrait layout:" is set to "No Rotation" by default.
			//* On the "Rotate Landscape pages to portrait layout:" dropdown, select Rotate 90 degrees counter clock-wise.
			//* Deselect "Enable for Privileged docs
			//
			throw new ImplementationException("complete_tiff_pdf_with_rotation");
		} else {
			throw new ImplementationException("NOT complete_tiff_pdf_with_rotation");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_marking_a_pdf_with_rotation_completed_returns_no_error$")
	public void verify_marking_a_pdf_with_rotation_completed_returns_no_error(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6183/5350There should not be any error Message As such "Rotation sections in PDF and TIFF compo nents must have the same configuration". User should be abl e to Mark complete and move on to next section of Production.Best way to verify this is by verifying you are on the numbering section of productions.
			throw new ImplementationException("verify_marking_a_pdf_with_rotation_completed_returns_no_error");
		} else {
			throw new ImplementationException("NOT verify_marking_a_pdf_with_rotation_completed_returns_no_error");
		}

	}


	@And("^.*(\\[Not\\] )? complete_tiff_with_empty_natively_produced_documents$")
	public void complete_tiff_with_empty_natively_produced_documents(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Check "TIFF"
			//* Expand "TIFF"
			//* Disable "Enable for Privileged Docs"
			//* Click "+Enable for Natively Producted Documents"
			//* Click "Other as the file type
			//* Type in a random placeholder text 
			//* Click "+Enable for Natively Producted Documents" a second time
			//* Leave the fields blank
			//
			throw new ImplementationException("complete_tiff_with_empty_natively_produced_documents");
		} else {
			throw new ImplementationException("NOT complete_tiff_with_empty_natively_produced_documents");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank$")
	public void verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5941 Verify a warning message is returned:The TIFF placeholder configuration information is incorrect.
			throw new ImplementationException("verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank");
		} else {
			throw new ImplementationException("NOT verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank");
		}

	}


	@And("^.*(\\[Not\\] )? add_a_second_dat_for_classification_production$")
	public void add_a_second_dat_for_classification_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Add FieldSet Field Classification to the corresponding parameter valueSet Source Field to the corresponding parameter valueSet Dat Field to the corresponding  parameter value
			throw new ImplementationException("add_a_second_dat_for_classification_production");
		} else {
			throw new ImplementationException("NOT add_a_second_dat_for_classification_production");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_adding_a_second_dat_data_mapping_is_retained$")
	public void verify_adding_a_second_dat_data_mapping_is_retained(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8358Expand the DAT sectionVerify both row of DAT field mapping are retained with the data entered.
			throw new ImplementationException("verify_adding_a_second_dat_data_mapping_is_retained");
		} else {
			throw new ImplementationException("NOT verify_adding_a_second_dat_data_mapping_is_retained");
		}

	}


	@And("^.*(\\[Not\\] )? complete_tiff_and_pdf_with_different_tags$")
	public void complete_tiff_and_pdf_with_different_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Check the "TIFF" checkbox
			//* Open the "TIFF" section
			//* Click on "+Enable for Natively Produced Documents:"
			//* Click Select Tags
			//* Click the first two tags under Default Tags
			//* Click Select
			//* Check the "PDF" checkbox
			//* Open the "PDF" section
			//* Click on "+Enable for Natively Produced Documents:"
			//* Click Select Tags
			//* Click the 3rd and 4th tags under Default Tags
			//* Click Select
			//
			throw new ImplementationException("complete_tiff_and_pdf_with_different_tags");
		} else {
			throw new ImplementationException("NOT complete_tiff_and_pdf_with_different_tags");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching$")
	public void verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5525 / 5519Verify the error message displays:PDF and TIFF PlaceHolder sections should have the same configuration.
			throw new ImplementationException("verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching");
		} else {
			throw new ImplementationException("NOT verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching");
		}

	}


	@When("^.*(\\[Not\\] )? expanding_the_mp3_advanced_section$")
	public void expanding_the_mp3_advanced_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking "Advanced" in the MP3 Section
			throw new ImplementationException("expanding_the_mp3_advanced_section");
		} else {
			throw new ImplementationException("NOT expanding_the_mp3_advanced_section");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_mp3_advanced_option_is_enabled_by_default$")
	public void verify_the_mp3_advanced_option_is_enabled_by_default(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5793Verify in the MP3 Advanced section, Generate Load File (LST) is enabled by default.
			throw new ImplementationException("verify_the_mp3_advanced_option_is_enabled_by_default");
		} else {
			throw new ImplementationException("NOT verify_the_mp3_advanced_option_is_enabled_by_default");
		}

	}


	@And("^.*(\\[Not\\] )? refresh_back_to_production_home_page$")
	public void refresh_back_to_production_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Navigate back to the Production Home Page URL
			throw new ImplementationException("refresh_back_to_production_home_page");
		} else {
			throw new ImplementationException("NOT refresh_back_to_production_home_page");
		}

	}


	@And("^.*(\\[Not\\] )? edit_the_production_component_with_a_tiff$")
	public void edit_the_production_component_with_a_tiff(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Mark IncompleteCheck the TIFF CheckboxClick TIFF to expand the TIFF sectionClick Multi-PageClick Select TagsClick the Privileged TagClick SelectType a Placeholder text in "Enter placeholder text for the privileged docs". Click Mark Complete.
			throw new ImplementationException("edit_the_production_component_with_a_tiff");
		} else {
			throw new ImplementationException("NOT edit_the_production_component_with_a_tiff");
		}

	}


	@When("^.*(\\[Not\\] )? refreshing_the_current_page$")
	public void refreshing_the_current_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("refreshing_the_current_page");
		} else {
			throw new ImplementationException("NOT refreshing_the_current_page");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_editing_an_existing_production_saves_the_new_changes$")
	public void verify_editing_an_existing_production_saves_the_new_changes(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5198Verify all of the new changes in "edit_the_production_component_with_a_tiff" are saved.Verify the Multi-page radio button is selected, the tag is saved, and the placeholder text is saved.
			throw new ImplementationException("verify_editing_an_existing_production_saves_the_new_changes");
		} else {
			throw new ImplementationException("NOT verify_editing_an_existing_production_saves_the_new_changes");
		}

	}


	@And("^.*(\\[Not\\] )? on_the_tiff_section_select_place_holder_tag_dialog$")
	public void on_the_tiff_section_select_place_holder_tag_dialog(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check the TIFF CheckboxClick TIFF to expand the TIFF sectionClick "Select Tags"
			throw new ImplementationException("on_the_tiff_section_select_place_holder_tag_dialog");
		} else {
			throw new ImplementationException("NOT on_the_tiff_section_select_place_holder_tag_dialog");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_non_privledge_tags$")
	public void clicking_non_privledge_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Try clicking the checkbox for "Default Automation Tag"Try clicking the checkbox for "All Tags"
			throw new ImplementationException("clicking_non_privledge_tags");
		} else {
			throw new ImplementationException("NOT clicking_non_privledge_tags");
		}

	}


	@And("^.*(\\[Not\\] )? verify_production_numbering_and_sorting_options_are_displayed$")
	public void verify_production_numbering_and_sorting_options_are_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5087Verify the All Tags checkbox does not get checkedVerify the All Tags checkbox does not get checked
			throw new ImplementationException("verify_production_numbering_and_sorting_options_are_displayed");
		} else {
			throw new ImplementationException("NOT verify_production_numbering_and_sorting_options_are_displayed");
		}

	}


	@And("^.*(\\[Not\\] )? complete_the_native_section_with_tags$")
	public void complete_the_native_section_with_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check NativeClick Select All on the file typesClick Select TagsCheck Attorney_Client, Confidential, and Default Automation TagClick Select
			throw new ImplementationException("complete_the_native_section_with_tags");
		} else {
			throw new ImplementationException("NOT complete_the_native_section_with_tags");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_native_section_with_tags_is_saving_correctly$")
	public void verify_native_section_with_tags_is_saving_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5332 /5334
			//* Expand the Native section
			//* Verify the selected tags are displaying in alphabetical order.
			//
			throw new ImplementationException("verify_native_section_with_tags_is_saving_correctly");
		} else {
			throw new ImplementationException("NOT verify_native_section_with_tags_is_saving_correctly");
		}

	}
}//EOF

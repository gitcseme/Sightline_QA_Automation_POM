package stepDef;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.FailedLoginException;

import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.lang.Math;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.PageOrder;
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
import pageFactory.LoginPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
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
			
			
			// Collapse DAT 
			prod.getTemplateProductionComponentToggle("DAT").click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					!prod.getTemplateFieldClassificationValue().Displayed()  ;}}), Input.wait30);
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getComponentsMarkComplete().Displayed()  ;}}), Input.wait30);
			prod.getComponentsMarkComplete().ScrollTo();
			prod.getComponentsMarkComplete().Click();
					
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getComponentsMarkNext().Enabled() ;}}), Input.wait30);
				prod.getComponentsMarkNext().Click();
			
			
			pass(dataMap,"Default Production Component are completed");	
				
			} catch(Exception e) {
				e.printStackTrace();
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
				
				// save document count for verification step
				dataMap.put("total_documents_number", prod.getTotalDocProduction().getText());
					
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
				prod.getFilterByButton().Click();
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
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return prod.getProductionListGridViewTable().Displayed()  ;}}), Input.wait30);

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
				e.printStackTrace();
				fail(dataMap, "Could not Select Prodution");
			}
			
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
				} else if(SystemUtils.IS_OS_MAC) {
					System.out.println("its a mac");
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
				String productionName  = dataMap.get("productionName").toString();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProductionTitleLink(productionName).Displayed()  ;}}), Input.wait30);
				prod.getProductionTitleLink(productionName).click();
				driver.waitForPageToBeReady();
				
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
				else {
					fail(dataMap, "Valid view mode was not selected");
				}
				
				pass(dataMap, "View Mode was Selected Successfully");
				
				
			} catch(Exception e) {e.printStackTrace();}
		}
		else {
			fail(dataMap, "Could not set production grid view");
		}

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
			String prefixBatesNumber = prefix + beginningBatesNumber;
			dataMap.put("bates_number", prefixBatesNumber);
			
		} else {
			throw new ImplementationException("NOT selecting_a_different_generated_bates_number");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_mark_complete_button$")
	public void clicking_the_productions_mark_complete_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			try {
				driver.scrollPageToTop();
				Actions builder = new Actions(driver.getWebDriver());
				builder.moveToElement(prod.getMarkCompleteButton().getWebElement()).perform();
				prod.getMarkCompleteButton().Click();
			} catch (Exception e) {
				e.printStackTrace();
				fail(dataMap, "Unable to click the Mark Complete button");
			}

		} else {
			try {
				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_UP.toString());
				Actions builder = new Actions(driver.getWebDriver());
				builder.moveToElement(prod.getMarkCompleteButton().getWebElement()).perform();
				prod.getMarkCompleteButton().Click();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	@When("^.*(\\[Not\\] )? clicking_the_components_mark_complete_button$")
	public void clicking_the_components_mark_complete_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//
			try {
				prod.getComponentsMarkComplete().click();
				pass(dataMap, "Able to  click the Mark Complete button");
			} catch (Exception e) {
				e.printStackTrace();
				fail(dataMap, "Unable to click the Mark Complete button");
			}
		} else {
			fail(dataMap, "Unable to click the Mark Complete button");
		}
	}
	
	@When("^.*(\\[Not\\] )? clicking_the_productions_component_mark_complete_button$")
	public void clicking_the_productions_component_mark_complete_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//
			try {
				driver.waitForPageToBeReady();
				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_UP.toString());
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
				prod.getComponentsMarkComplete().Click();
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
				/*
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
						prod.getbtnProductionGuardMarkComplete().Enabled() && prod.getPrivDefaultAutomation().Displayed()  ;}}), Input.wait30);
					prod.getbtnProductionGuardMarkComplete().Click();
				*/
				prod.getMarkCompleteButton().click();
				if(prod.getOkButton().Displayed()) prod.getOkButton().click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkCompleteSuccessfulText().Displayed()  ;}}), Input.wait30); 	
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getbtnProductionGuardNext().Enabled()  ;}}), Input.wait30); 
				prod.getbtnProductionGuardNext().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getCurrentCrumbProductionLocation().Displayed()  ;}}), Input.wait30); 
				
				pass(dataMap, "marked priv guard default complete");
			} catch (Exception e) {
				
			}
		} else {
			fail(dataMap, "failed to mark priv guard default complete");
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
				String docID = (prod.getProdPrevPageDocSummary().FindWebElements().get(16).getText()).split(" and")[0];
				dataMap.put("docID", docID);
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
			try {
				//The user should click Save from the Production page.
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSaveButton().Enabled()  ;}}), 10);
				prod.getSaveButton().Click();
			}
			catch (Exception e){
				
			}
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
			prod.goToProductionHomePage().ScrollTo();
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
		int i =0;
		while(i==0) {
			selecting_the_production(true,dataMap);
			try {
			Thread.sleep(300);
			}
			catch(Exception e) {}
			prod.getprod_ActionButton().click();
			prod.getDelete().click();
			prod.getProductionDeleteOkButton().click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getFilterByButton().Enabled()  ;}}), Input.wait30);
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

			//IF DAT IS TRUE:
			//Click the DAT checkbox
			//Click the DAT tab to open the DAT container
			//Add field classification: Bates
			//Add source field: BatesNumber
			//Enter DAT field: Bates Number
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
			if(nat!= null && nat.equalsIgnoreCase("true")){
				builder.moveToElement(prod.getNativeChkBox().getWebElement()).perform();
				prod.getNativeChkBox().click();
				prod.getNativeTab().click();
				prod.getNative_SelectAllCheck().click();
				prod.getNativeAdvanced().click();
				prod.getNative_GenerateLoadFileLST().click();
			}


			//WILL IMPLEMENT THESE LATER WHEN WE USE THEM IN FUTURE SCRIPTS

			//IF MP3 IS TRUE
			//Expand Advanced Production Components
			//Click the MP3 Files Checkbox
			//Enable Burn Redactions
			//Click "Select Redactions"
			//Click "Default Automation Redaction"
			//Set the "Redaction Style" to "Beep"

			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
			if(mp3!= null && mp3.equalsIgnoreCase("true")){
				builder.moveToElement(prod.getMP3_ToggElement().getWebElement()).perform();
				prod.getMP3_ToggElement().click();
				builder.moveToElement(prod.getMP3ChkBox().getWebElement()).perform();
				prod.getMP3ChkBox().click();
				prod.getMP3Tab().click();
				prod.getMP3ComponentRedactionToggle().click();
				prod.getMP3_SelectRed_Radiobutton().click();
				prod.getMP3SelectRedactionsTagTree("Default Redaction Tag").click();
			}

			//IF TEXT IS TRUE
			//Checkoff the "Text" component checkbox
			//The other parameters can be worked on as we use them.
			


			//Click the Mark complete button and verify the following message appears: "Mark Complete successful"
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_UP.toString());
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_UP.toString());
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_UP.toString());
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_UP.toString());
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_UP.toString());
			builder.moveToElement(prod.getComponentsMarkComplete().getWebElement()).perform();
			prod.getComponentsMarkComplete().Click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getConfirmCompletePopup().Displayed() ;}}), Input.wait30);
			Assert.assertTrue(prod.getConfirmCompletePopup().Displayed());

			//Click the next button
			prod.getNextButton().click();
			pass(dataMap, "Complex Components were enabled");
		}
		else fail(dataMap, "Failed Complex Production Component");

	}


	@And("^.*(\\[Not\\] )? remove_placeholders_on_tiff_pdf$")
	public void remove_placeholders_on_tiff_pdf(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			Actions builder = new Actions(driver.getWebDriver());
			prod.getBackLink().click();
			prod.getMarkIncompleteButton().click();

			//Open Tiff Tab and toggle off priv docs
			prod.getTIFFTab().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getTIFFPlaceholderPriviledgedToggleActive().Displayed() ;}}), Input.wait30);
			prod.getTIFFPlaceholderPriviledgedToggleActive().click();

			//Move to PDF tab and toggle off priv docs
			builder.moveToElement(prod.getPDFTab().getWebElement()).perform();
			prod.getPDFTab().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getPDFPlaceholderPriviledgedToggleActive().Displayed() ;}}), Input.wait30);
			prod.getPDFPlaceholderPriviledgedToggleActive().click();

			//Mark complete and go next
			builder.moveToElement(prod.getMarkCompleteButton().getWebElement()).perform();
			prod.getMarkCompleteButton().click();
			prod.getNextButton().click();
			pass(dataMap, "Removed placeholders on TIFF and PDF");
		} else {
			fail(dataMap, "NOT remove_placeholders_on_tiff_pdf");
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
			//Make sure "Select Folders:" radio button is selected
			//Click "Default Automation Priviledged" checkbox
			//Click Mark Completed
			//Click Next
			prod.getFolderRadioButton().click();
			prod.getSelectFolderCheckbox("Default Automation Priviledged").click();
			prod.getMarkCompleteButton().click();
			prod.getNextButton().click();
			driver.waitForPageToBeReady();
			pass(dataMap, "Completed document section with priviledged folder");
		} else {
			fail(dataMap, "NOT complete_document_section_with_priviledged_folder");
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
			driver.waitForPageToBeReady();
			prod.getMarkCompleteButton().click();
			prod.getPrivilegedPlaceholderDocsToggle().click();
			prod.getGuardSelectPrevTagsButton().click();
			prod.getGuardTreeTagCheckbox("Privileged").click();
			prod.getTagGuardTreeSaveButton().click();
			prod.getGuardTagTextArea().click();
			prod.getGuardTagTextArea().SendKeys("Placeholder created on Priv Guard");
			prod.getPrivMarkCompleteButton().click();
			for(int i =0; i<3; ++i) {
				driver.waitForPageToBeReady();
				prod.getBackLink().click();
			}
			pass(dataMap, "Enabled priv guard placeholders");

		}
		else fail(dataMap, "Failed to enable priv guard placeholders");


	}


	@When("^.*(\\[Not\\] )? expanding_the_tiff_pdf_section_of_production_components$")
	public void expanding_the_tiff_pdf_section_of_production_components(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			Actions builder = new Actions(driver.getWebDriver());

			//Move to and Open Tiff Tab
			//builder.moveToElement(prod.getTIFFTab().getWebElement()).perform();
			prod.getTIFFTab().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getTIFFPlaceholderPrivilegedTextField().Displayed() ;}}), Input.wait30);
			dataMap.put("tiffPrivText", prod.getTIFFPlaceholderPrivilegedTextField().getText());

			//Move to and Open PDF Tab
			//builder.moveToElement(prod.getPDFTab().getWebElement()).perform();
			prod.getPDFTab().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getPDFPlaceholderPrivDocsField().Displayed() ;}}), Input.wait30);
			dataMap.put("pdfPrivText", prod.getPDFPlaceholderPrivDocsField().getText());

			pass(dataMap, "Opened the PDF and TIFF section");
		}
		else fail(dataMap, "Failed to expand tiff PDF section of production");

	}


	@Given("^.*(\\[Not\\] )? verify_enabling_placeholders_on_priv_guard_saves_the_placeholders$")
	public void verify_enabling_placeholders_on_priv_guard_saves_the_placeholders(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			//Grab PDF Priv Holder Text
			String pdfHolderText = (String)dataMap.get("pdfPrivText");
			//Grab Tiff Priv Holder Text
			String tiffHolderText = (String)dataMap.get("tiffPrivText");

			Assert.assertEquals(pdfHolderText, "Placeholder created on Priv Guard");
			Assert.assertEquals(tiffHolderText, "Placeholder created on Priv Guard");

			String continueScript = (String)dataMap.get("continue");
			if(continueScript != null && continueScript.equalsIgnoreCase("true")) {
				for(int i =0; i<4; i++) {
					prod.getNextButton().click();
					driver.waitForPageToBeReady();
				}
			}
			pass(dataMap, "verified privguard saves placeholders");
		}
		else fail(dataMap, "Could not verify privguard saves placeholders");

	}


	@And("^.*(\\[Not\\] )? the_production_is_started$")
	public void the_production_is_started(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Generate
			prod.getGenerateButton().click();
			driver.waitForPageToBeReady();

		} else {
			fail(dataMap, "Generate button is not clicked");
		}

	}


	@When("^.*(\\[Not\\] )? refreshing_for_production_to_be_in_progress$")
	public void refreshing_for_production_to_be_in_progress(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			int i =0;
			driver.waitForPageToBeReady();
			//Loop to cycle back and forth between pages, until the export bates button is enabled
			//Once that button is enabled we know a bates range number is populated
			String bateString  = prod.getProd_BatesRange().getText();
			while(bateString.equals("") && i<10){
				i++;
				prod.getBackLink().click();
				driver.waitForPageToBeReady();
				Thread.sleep(10000);
				prod.getNextButton().click();
				driver.waitForPageToBeReady();
				bateString = prod.getProd_BatesRange().getText();
			}
			//One more back and forth to get the table status to update
			prod.getBackButton().click();
			driver.waitForPageToBeReady();
			prod.getNextButton().click();
			pass(dataMap, "Production was refreshed");
		} else {
			fail(dataMap, "Bates number not returned on time");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly$")
	public void verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.waitForPageToBeReady();
			//Verify 0 priv doc's
			Assert.assertEquals(prod.getPrivDocsStatus().getText(), "0 Docs");
			pass(dataMap, "Privileged docs with no placeholders displays correct status");
		} else {
			fail(dataMap, "Incorrect status displayed");
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
			prod.getDocListDropDownCount().click();
			prod.getDocListDropDownCountMax().click();
			driver.waitForPageToBeReady();
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
			//Simply make sure, we have 11 total documents
			Assert.assertEquals("11", prod.getTotalMatchedDocuments().getText());
		}
		else fail(dataMap, "Could not Verify the Priv Guard Matched Documents");

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_name$")
	public void clicking_the_productions_name(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Using the production selected, click on the Name of the production to open it.
			try{
				driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProductionTitleName().Displayed()  ;}}), Input.wait30);
			prod.getProductionTitleName().click();
			}catch (Exception e){
				fail(dataMap, "Unable to click the Production Title");
			}
			
		} else {
			throw new ImplementationException("NOT clicking_the_productions_name");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_productions_quality_control_section_display_correctly$")
	public void verify_the_productions_quality_control_section_display_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {		
			driver.waitForPageToBeReady();
			if(prod.getGeneratePageTitle().FindWebElements().size()>0){
				prod.navigateToNextSection();
			//TC4928:Verify the name of the production displays on the page.
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProdductionNameOnQCPage().Displayed()  ;}}), Input.wait30);
				Assert.assertTrue(prod.getProdductionNameOnQCPage().Displayed());
		    
		  //Verify a number is generated on "Documents Generated". The number should not be 0.
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getGeneratedDocCountOnQCPage().Displayed()  ;}}), Input.wait30);
				Assert.assertNotEquals("0", prod.getGeneratedDocCountOnQCPage().getText());
		    
		  //Verify the button "Review Production" exist on the screen.
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getReviewProductionButton().Displayed()  ;}}), Input.wait30);
				Assert.assertTrue(prod.getReviewProductionButton().Displayed());
		    
		  //Verify the link "Confirm Production & Commit" exist on the screen.
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getConfirmAndCommitProdLink().Displayed()  ;}}), Input.wait30);
				Assert.assertTrue(prod.getConfirmAndCommitProdLink().Displayed());
		    
		  //Verify on the "AUTOMATED QC CHECK" grid, the grid displays "Prod Files / Compenents", "Document and Page Counts", "Bates Number Generation", and "Blank Page Removal".
				String expectedProdFiles = prod.getAutomatedQCChecklistText(1).getText();
				String expectedDocCount = prod.getAutomatedQCChecklistText(2).getText();
				String expectedBatesNumber = prod.getAutomatedQCChecklistText(3).getText();
				String expectedBlankPageRemoval = prod.getAutomatedQCChecklistText(4).getText();
		    
				Assert.assertEquals("Prod Files / Components", expectedProdFiles);
				Assert.assertEquals("Document and Page Counts", expectedDocCount);
				Assert.assertEquals("Bates Number Generation", expectedBatesNumber);
				Assert.assertEquals("Blank Page Removal", expectedBlankPageRemoval);   
		    
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getQC_backbutton().Enabled()  ;}}), Input.wait30); 
				prod.getQC_backbutton().waitAndClick(15);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkIncompleteButton().Enabled()  ;}}), Input.wait30); 
				prod.getMarkIncompleteButton().waitAndClick(15);
			}
			
			else{
				//TC4928:Verify the name of the production displays on the page.
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProdductionNameOnQCPage().Displayed()  ;}}), Input.wait30);
			    Assert.assertTrue(prod.getProdductionNameOnQCPage().Displayed());
			    
			  //Verify a number is generated on "Documents Generated". The number should not be 0.
			    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getGeneratedDocCountOnQCPage().Displayed()  ;}}), Input.wait30);
			    Assert.assertNotEquals("0", prod.getGeneratedDocCountOnQCPage().getText());
			    
			  //Verify the button "Review Production" exist on the screen.
			    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getReviewProductionButton().Displayed()  ;}}), Input.wait30);
			    Assert.assertTrue(prod.getReviewProductionButton().Displayed());
			    
			  //Verify the link "Confirm Production & Commit" exist on the screen.
			    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getConfirmAndCommitProdLink().Displayed()  ;}}), Input.wait30);
			    Assert.assertTrue(prod.getConfirmAndCommitProdLink().Displayed());
			    
			  //Verify on the "AUTOMATED QC CHECK" grid, the grid displays "Prod Files / Compenents", "Document and Page Counts", "Bates Number Generation", and "Blank Page Removal".
			    String expectedProdFiles = prod.getAutomatedQCChecklistText(1).getText();
			    String expectedDocCount = prod.getAutomatedQCChecklistText(2).getText();
			    String expectedBatesNumber = prod.getAutomatedQCChecklistText(3).getText();
			    String expectedBlankPageRemoval = prod.getAutomatedQCChecklistText(4).getText();
			    
			    Assert.assertEquals("Prod Files / Components", expectedProdFiles);
			    Assert.assertEquals("Document and Page Counts", expectedDocCount);
			    Assert.assertEquals("Bates Number Generation", expectedBatesNumber);
			    Assert.assertEquals("Blank Page Removal", expectedBlankPageRemoval);  
			}
	    } 
		else {
			throw new ImplementationException("NOT verify_the_productions_quality_control_section_display_correctly");
		}

	}


	@And("^.*(\\[Not\\] )? complete_tiff_production_component_with_branding$")
	public void complete_tiff_production_component_with_branding(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check off TIFF
			//Click TIFF to expand it
			//Click Select Tags in the "Placeholders" section.
			//Click the "Privileged" folder
			//Click Select
			//Type in "Automated Placeholder" in "Enter placeholder text for the privileged docs".
			//Toggle on "Burn Redactions"
			//Select the option "Select Redactions"
			//Check off Default Automation Redaction
			//Click "+Specify Branding by Selecting Tags
			//"Click Select Tags
			//Check off "Default Automation Tag"
			//Click Select
			//Enter "Branding 1" in the first placeholder text for branding
			//Enter "Branding 2" in the second placeholder text for branding

			try {

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFChkBox().Displayed()  ;}}), Input.wait30);
					prod.getTIFFChkBox().Click();
					
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getTIFFTab().Displayed()  ;}}), Input.wait30);
				prod.getTIFFTab().Click();
					
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPriveldge_SelectTagButton().Displayed()  ;}}), Input.wait30);
				prod.getPriveldge_SelectTagButton().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSelectTagsModal().Displayed()  ;}}), Input.wait30);

				prod.getSelectTagsModalBody().Click();
				prod.getTIFFPrivilegedTagCheckbox().ScrollTo();
				prod.getTIFFPrivilegedTagCheckbox().Click();
				

				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFSubmitSelectionButton().Displayed()  ;}}), Input.wait30);
				
				prod.getTIFFSubmitSelectionButton().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getSelectTagsModal().Displayed()  ;}}), Input.wait30);
								
				prod.getPrivilegedDocsPlaceholderTextField().ScrollTo();
				prod.getPrivilegedDocsPlaceholderTextField().SendKeys("Automated Placeholder");
				
				prod.getTIFF_BurnRedtoggle().Click();
				prod.getTIFF_SelectRed_Radiobutton().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFRedactionTreeFolderDiv().Displayed()  ;}}), Input.wait30);
				

				driver.scrollingToBottomofAPage();
				prod.getTIFFRedactionTreeFolderDiv().Click();
				prod.getTIFFRedactionTreeFolderDiv().Click();
				prod.getRedactionDefaultAutomationRedactionCheckbox().Click();
				
				driver.scrollPageToTop();
				prod.getSpecifyBrandingBySelectingTagLink().ScrollTo();
				prod.getSpecifyBrandingBySelectingTagLink().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getDefaultBrandingTextField2().Displayed()  ;}}), Input.wait30);
				
				
				prod.getBrandingSelectTagsButton().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSelectTagsModal().Displayed()  ;}}), Input.wait30);
				prod.getSelectTagsModalBody().Click();
				prod.getSelectTagsModalBody().Click();
				prod.getBrandingDefaultAutomationTagCheckbox().ScrollTo();
				prod.getBrandingDefaultAutomationTagCheckbox().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFSubmitSelectionButton().Displayed()  ;}}), Input.wait30);
				prod.getTIFFSubmitSelectionButton().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getSelectTagsModalBody().Displayed()  ;}}), Input.wait30);
				
				String defaultBrandingText = "Branding 1";
				dataMap.put("default_branding", defaultBrandingText);
				
				prod.getDefaultBrandingTextField1().ScrollTo();
				prod.getDefaultBrandingTextField1().SendKeys(defaultBrandingText);
				prod.getDefaultBrandingTextField2().ScrollTo();
				prod.getDefaultBrandingTextField2().SendKeys("Branding 2");
				
				driver.scrollPageToTop();
					
			} catch (Exception e) {
				System.out.println("Exception: " + e);
			}
			
			
		} else {
			throw new ImplementationException("NOT complete_tiff_production_component_with_branding");
		}

	}


	@And("^.*(\\[Not\\] )? complete_default_numbering_sorting$")
	public void complete_default_numbering_sorting(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Mark CompletedClick Next
			try {
				prod.getMarkCompleteButton().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNextButton().Enabled()  ;}}), Input.wait30);
				dataMap.put("bates_number", "0");
				prod.getNextButton().click();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			throw new ImplementationException("NOT complete_default_numbering_sorting");
		}

	}


	@And("^.*(\\[Not\\] )? complete_blank_priv_guard_check$")
	public void complete_blank_priv_guard_check(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//You should be on the section "Priv Guard"
			//Verify the button "+ Add Rule" exists
			//Click Mark Complete
			//Click Next
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivAddRuleBtn().Displayed()  ;}}), Input.wait30);
				prod.getMarkCompleteButton().click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getOkButton().Displayed()  ;}}), Input.wait30);
				prod.getOkButton().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNextButton().Enabled()  ;}}), Input.wait30);
				prod.getNextButton().click();

				
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			throw new ImplementationException("NOT complete_blank_priv_guard_check");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_number_of_mp3_files_are_shown_regardless_of_amount$")
	public void verify_the_number_of_mp3_files_are_shown_regardless_of_amount(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6126Verify the Count of Number of MP3 Files is shown when the number is 0
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSummaryAndPreviewHeading().Displayed()  ;}}), Input.wait30);
				prod.getMP3FilesCountNumber().ScrollTo();
				Assert.assertEquals(prod.getMP3FilesCountNumber().getText(), "0");
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "MP3 File count is not correct");
			}
		} else {
			throw new ImplementationException("NOT verify_the_number_of_mp3_files_are_shown_regardless_of_amount");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_summary_preview_button$")
	public void clicking_the_summary_preview_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getPreviewprod().Displayed() ;}}), Input.wait30);
				//Move page back to incomplete, then click Preview Button
				prod.getPreviewprod().click();
				driver.waitForPageToBeReady();
				pass(dataMap, "Clicked preview button");
		} else {
			throw new ImplementationException("NOT clicking_the_summary_preview_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_preview_file_should_display_the_branding_entered$")
	public void verify_the_preview_file_should_display_the_branding_entered(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5096 / 3760A PDF file should be downloaded and opening the file should show the branding entered
			try {
				// TODO: add logic to get bates number if using existing production
				String batesNumber = dataMap.get("bates_number").toString();
				PDDocument document = null;
				String home = System.getProperty("user.home");
				String fileNamePath = "";
				if(SystemUtils.IS_OS_LINUX){
					fileNamePath = String.format("/Downloads/%s.pdf", batesNumber);
					while(document == null) document = PDDocument.load(new File(home + fileNamePath));}
				else if(SystemUtils.IS_OS_WINDOWS){
					fileNamePath = String.format("\\Download\\%s.pdf", batesNumber);
					while(document == null) document = PDDocument.load(new File(home + fileNamePath));
				} else if(SystemUtils.IS_OS_MAC) {
					fileNamePath = String.format("/Downloads/%s.pdf", batesNumber);
					while(document == null) document = PDDocument.load(new File(home + fileNamePath));
				}

				PDFTextStripper pdfTextStripper = new PDFTextStripper();

				String text = pdfTextStripper.getText(document);

				String defaultBrandingText = dataMap.get("default_branding").toString();
				
				Pattern p = Pattern.compile(defaultBrandingText);
				Matcher matcher = p.matcher(text);
			
				if (matcher.find()) {
					pass(dataMap, "Branding is displayed in the preview of the pdf");
				} else {
					fail(dataMap, "Branding is not displayed in the preview of the pdf");
				}
				document.close();
				
				// delete file after verification
				File file = new File(home + fileNamePath);
				file.delete();
			
			} catch (Exception e) {
				
			}
		} else {
			throw new ImplementationException("NOT verify_the_preview_file_should_display_the_branding_entered");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_redaction_count_displays_correctly_on_the_summary_page$")
	public void verify_the_redaction_count_displays_correctly_on_the_summary_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5008The summary page should be displayed.
			//1. Verify "Redacted Documents:" is displaying the correct count of refacted documents based on the redacted tags used in complete_tiff_production_component.If using Default Automation Redaction, 5 redactions should be returned.
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSummaryAndPreviewHeading().Displayed()  ;}}), Input.wait30);
				prod.getRedactedDocumentsNumber().ScrollTo();
				Assert.assertEquals(prod.getRedactedDocumentsNumber().getText(), "5");
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "Redaction count is not correct");
			}
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
			
			
			try {
				// TODO: Verify other values 
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSummaryAndPreviewHeading().Displayed()  ;}}), Input.wait30);
				String totalDocumentsNumberActual = prod.getTotalDocumentsNumber().getText();
				String totalDocumnetsNumberExpected = dataMap.get("total_documents_number").toString();
				Assert.assertEquals(totalDocumentsNumberActual, totalDocumnetsNumberExpected);
				// Production Doc Labels 
				String totalPagesExpected = prod.getSummaryPageLabels(3).getText();
				String numberOfCustodians = prod.getSummaryPageLabels(4).getText();
				String numberOfNatives = prod.getSummaryPageLabels(5).getText();
				String numberOfMP3Files = prod.getSummaryPageLabels(6).getText();
				String exceptionDocument = prod.getSummaryPageLabels(7).getText();
				String docWithMissingTextFiles = prod.getSummaryPageLabels(8).getText();
				String firstAndLastDoc = prod.getSummaryPageLabels(9).getText();
				
				// Privileged Document Summary Labels				
				String privilegedDoc = prod.getSummaryPageLabels(11).getText();
				String docIdentifiedByProdGuard = prod.getSummaryPageLabels(12).getText();
				String redactedDocuments = prod.getSummaryPageLabels(13).getText();
				
				// OCR/TIFF Documents Labels			
				String documentsToOCR = prod.getSummaryPageLabels(15).getText();
				String documentsToTIFF = prod.getSummaryPageLabels(16).getText();
				String documentsPrintedInColor = prod.getSummaryPageLabels(17).getText();
				
				Assert.assertEquals("Total Pages (Uses DocPages metadata field):", totalPagesExpected);
				Assert.assertEquals("Number Of Custodians:", numberOfCustodians);
				Assert.assertEquals("Number of Natives:", numberOfNatives);
				Assert.assertEquals("Number of MP3 Files:", numberOfMP3Files);
				Assert.assertEquals("Exception Documents:", exceptionDocument);
				Assert.assertEquals("Documents with Missing Text Files:", docWithMissingTextFiles);
				Assert.assertEquals("First and Last Doc IDs:", firstAndLastDoc);
				
				Assert.assertEquals("Privileged Documents:", privilegedDoc);
				Assert.assertEquals("Documents Identified by Production Guard:", docIdentifiedByProdGuard);
				Assert.assertEquals("Redacted Documents:", redactedDocuments);
				
				Assert.assertEquals("Documents to OCR:", documentsToOCR);
				Assert.assertEquals("Documents to TIFF:", documentsToTIFF);
				Assert.assertEquals("Documents Printed in Color:", documentsPrintedInColor);
				
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "Expected values not displatyed correctly on Summary page");
			}
		} else {
			throw new ImplementationException("NOT verify_the_summary_page_displays_the_correct_information");
		}

	}
	

	@And("^.*(\\[Not\\] )? complete_the_default_dat_section$")
	public void complete_the_default_dat_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			/*
			 * 
			 * /Production Components page is already displayedClick the DAT checkboxClick the DAT tab to open the DAT container
			 * Add field classification: BatesAdd source field: BatesNumberEnter DAT field: Bates Number */
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
				
			// Collapse DAT 
			prod.getTemplateProductionComponentToggle("DAT").click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					!prod.getTemplateFieldClassificationValue().Displayed()  ;}}), Input.wait30);
				
			pass(dataMap,"default DAT section is complete");
		} catch(Exception e) {
			e.printStackTrace();
			fail(dataMap,"default DAT section is not complete");
			}
					
		} else {
			fail(dataMap,"default DAT section is not complete");
		}
	}


	@And("^.*(\\[Not\\] )? complete_the_native_section_with_tags_and_no_file_types$")
	public void complete_the_native_section_with_tags_and_no_file_types(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			try{
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeChkBox().Enabled()  ;}}), Input.wait30); 
				prod.getNativeChkBox().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeTab().Visible()  ;}}), Input.wait30); 
				prod.getNativeTab().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSelectNativeTagsButton().Visible()  ;}}), Input.wait30); 
				prod.getSelectNativeTagsButton().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNative_DefaultAutomationTag().Visible()  ;}}), Input.wait30); 
				prod.getNative_DefaultAutomationTag().Click();
				prod.getNative_AttorneyCLientTag().Click();
				prod.getNative_ConfedentialTag().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSelectTagsButton().Visible()  ;}}), Input.wait30); 
				prod.getSelectTagsButton().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeContainer().Displayed()  ;}}), Input.wait30); 
				
				// Collapse Native tab
				prod.getTemplateProductionComponentToggle("Native").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getNativeContainer().Displayed()  ;}}), Input.wait30);
				
				pass(dataMap,"default Native section is complete");
			}
			catch(Exception e){
				e.printStackTrace();
				fail(dataMap,"default Native section is not complete");
			}
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
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkIncompleteButton().Displayed()  ;}}), Input.wait30); 
				Assert.assertTrue(prod.getMarkIncompleteButton().Displayed());
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeTab().Visible()  ;}}), Input.wait30); 
				prod.getNativeTab().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSelectNativeTagsButton().Visible()  ;}}), Input.wait30); 
				String nativeSelectedTags = prod.getNativeSelectedTagList().GetAttribute("textContent");
				List<String> tags = Arrays.asList(nativeSelectedTags.split(","));
				List<String> newlist = tags;
				Collections.sort(newlist);
				Assert.assertEquals(newlist, tags);
				if(!prod.getNative_SelectAllCheck().Selected()){
					pass(dataMap,"No file types are checked");
				}
				else{
					fail(dataMap,"File types are checked");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

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
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFChkBox().Visible() ;}}), Input.wait30); 
				prod.getTIFFChkBox().Click();		
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionComponentTIFFCheckbox().Selected()  ;}}), Input.wait30); 
				
				driver.scrollingToBottomofAPage();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFTab().Visible()  ;}}), Input.wait30); 
				prod.getTIFFTab().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFRotateDropdown().Visible()  ;}}), Input.wait30); 
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFRotateDropdown().selectFromDropdown().getFirstSelectedOption().getText().equals("No Rotation")  ;}}), Input.wait30); 
				
				prod.getTIFFRotateDropdown().selectFromDropdown().selectByVisibleText("Rotate 90 degrees counter clock-wise");
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFRotateDropdown().selectFromDropdown().getFirstSelectedOption().getText().equals("Rotate 90 degrees counter clock-wise")  ;}}), Input.wait30); 
				
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFF_EnableforPrivilegedDocs().Enabled()  ;}}), Input.wait30); 
				// scroll to toggle. Webdriver sometimes does not actually click the toggle if the
				// element is not in view
				prod.getTIFF_EnableforPrivilegedDocs().ScrollTo();
				prod.getTIFF_EnableforPrivilegedDocs().Click();
				
				// Sometimes toggle is not clicked, so adding logic to check if it was clicked. If it wasn't clicked 
				// the first time, then click again
				while (prod.getTIFFPrivilegeDocsDisabledToggle().FindWebElements().size() == 0) {
					System.out.println("TIFF Enabled Priv Docs toggle was not clicked. Clicking again...");
					prod.getTIFF_EnableforPrivilegedDocs().Click();
					driver.waitForPageToBeReady();
				}

				// Collapse TIFF tab
				driver.scrollPageToTop();
				prod.getTemplateProductionComponentToggle("TIFF").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getTemplateTIFFPlaceholderText().Displayed()  ;}}), Input.wait30);
				
				// Select PDF
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFChkBox().Visible() ;}}), Input.wait30); 
				prod.getPDFChkBox().Click();		
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionComponentPDFCheckbox().Selected()  ;}}), Input.wait30); 
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFTab().Visible()  ;}}), Input.wait30); 
				prod.getPDFTab().Click();
				
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFRotateDropdown().Visible()  ;}}), Input.wait30); 
				//
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFRotateDropdown().selectFromDropdown().getFirstSelectedOption().getText().equals("No Rotation")  ;}}), Input.wait30); 
				
				prod.getPDFRotateDropdown().selectFromDropdown().selectByVisibleText("Rotate 90 degrees counter clock-wise");
		
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFRotateDropdown().selectFromDropdown().getFirstSelectedOption().getText().equals("Rotate 90 degrees counter clock-wise")  ;}}), Input.wait30); 
				
				

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDF_EnableforPrivilegedDocs().Enabled()  ;}}), Input.wait30); 
				prod.getPDF_EnableforPrivilegedDocs().ScrollTo();
				prod.getPDF_EnableforPrivilegedDocs().Click();				
				
				// Sometimes toggle is not clicked, so adding logic to check if it was clicked. If it wasn't clicked 
				// the first time, then click again
				while (prod.getPDFPrivilegeDocsDisabledToggle().FindWebElements().size() == 0) {
					System.out.println("PDF Enabled Priv Docs toggle was not clicked. Clicking again...");
					prod.getTIFF_EnableforPrivilegedDocs().Click();
					driver.waitForPageToBeReady();
				}
				
				//Collapse PDF toggle
				driver.scrollPageToTop();
				prod.getTemplateProductionComponentToggle("PDF").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getTemplatePDFPageRotatePreferenceSelectedValue().Displayed()  ;}}), Input.wait30);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			throw new ImplementationException("NOT complete_tiff_pdf_with_rotation");
		}
	}
	
	@Then("^.*(\\[Not\\] )? verify_marking_a_pdf_with_rotation_completed_returns_no_error$")
	public void verify_marking_a_pdf_with_rotation_completed_returns_no_error(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//TC 6183/5350There should not be any error Message As such "Rotation sections in PDF and TIFF compo nents must have the same configuration". User should be abl e to Mark complete and move on to next section of Production.Best way to verify this is by verifying you are on the numbering section of productions.
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNumberingAndSortingTitle().Displayed()  ;}}), Input.wait30);
			Assert.assertTrue(prod.getNumberingAndSortingTitle().Displayed());
			pass(dataMap, "PASS! User is on Numbering and Sorting page");
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
			try {
				driver.waitForPageToBeReady();
				prod.getTIFFChkBox().click();
				prod.getTIFFTab().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceholderPrivilegedToggle().Displayed();}}), Input.wait30);
				prod.getTIFFPlaceholderPrivilegedToggle().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFComponentEnableNativelyProducedDocuments().Displayed();}}), Input.wait30);
				prod.getTIFFComponentEnableNativelyProducedDocuments().click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFNativelyProductedDocumentSelect().Displayed();}}), Input.wait30);
				prod.getTIFFNativelyProductedDocumentSelect().selectFromDropdown().selectByVisibleText("Other (i.e. Uncategorized, unknown, etc.)");
	
				prod.getTIFFComponenetNativelyProducedDocumentPlaceHolder().setText("Place holder text.");
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFComponentEnableNativelyProducedDocuments().Displayed();}}), Input.wait30);
				prod.getTIFFComponentEnableNativelyProducedDocuments().click();				
				pass(dataMap, "complete_tiff_with_empty_natively_produced_documents");
			} catch(Exception e) {
				e.printStackTrace();
				fail(dataMap, "NOT complete_tiff_with_empty_natively_produced_documents");				
			}
		} else {
			fail(dataMap, "NOT complete_tiff_with_empty_natively_produced_documents");
		}
	}
	@Then("^.*(\\[Not\\] )? verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank$")
	public void verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFPlaceHolderBlankNativelyProducedDocumentsWarning().Displayed();}}), Input.wait30);
				assert prod.getTIFFPlaceHolderBlankNativelyProducedDocumentsWarning().getText().equals("The TIFF placeholder configuration information is incorrect.");
				pass(dataMap, "PASS! The expected error message is returned");
				
			} catch (Exception e) {
				e.printStackTrace();
				fail(dataMap, "NOT verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank");
			}
			pass(dataMap, "verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank");
		} else {
			fail(dataMap, "NOT verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank");		}
	}
	@And("^.*(\\[Not\\] )? add_a_second_dat_for_classification_production$")
	public void add_a_second_dat_for_classification_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//Click Add Field Set Field Classification to the corresponding parameter value
			//Set Source Field to the corresponding parameter valueSet Dat Field to the corresponding  parameter value
			try {
				prod.getTemplateProductionComponentToggle("DAT").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplateFieldClassificationValue().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							prod.getDAT_AddField().Displayed()  ;}}), Input.wait30);
						prod.getDAT_AddField().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getFieldClassification().Displayed()  ;}}), Input.wait30);
					prod.getDAT_FieldClassification2().Click();
					prod.getDAT_FieldClassification2().SendKeys("Production");
					
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSourceField().Displayed()  ;}}), Input.wait30);
					prod.getDAT_SourceField2().Click();
					prod.getDAT_SourceField2().SendKeys("TIFFPageCount");
					
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getDatField().Displayed()  ;}}), Input.wait30);
					prod.getDAT_DATField2().Click();
					prod.getDAT_DATField2().SendKeys("TPageCount");
					
					pass(dataMap,"Second Dat wassuccessfull");
			}catch(Exception e) {
				e.printStackTrace();
				fail(dataMap,"Second Dat was unsuccessfull");
			}} else {
				fail(dataMap,"Second Dat was unsuccessfull");
		}
	}
	@Then("^.*(\\[Not\\] )? verify_adding_a_second_dat_data_mapping_is_retained$")
	public void verify_adding_a_second_dat_data_mapping_is_retained(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//TC8358Expand the DAT sectionVerify both row of DAT field mapping are retained with the data entered.
			try {
				driver.waitForPageToBeReady();
				prod.getDATTab().click();
				//prod.getComponentsMarkInComplete().click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getFieldClassificationDropDown(3).Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSourceFieldDropDown(2).Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getDataFieldText(0).Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSecondFieldClassificationDropDown(10).Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSecondSourceFieldDropDown(2).Displayed()  ;}}), Input.wait30);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getDataFieldText(1).Displayed()  ;}}), Input.wait30);
				
				String fieldClassification = prod.getFieldClassificationDropDown(3).getText().toString();
				String sourceField = prod.getSourceFieldDropDown(2).getText().toString();
				String dataField =  prod.getDataFieldText(0).GetAttribute("value").toString();
				String fieldClassification2 = prod.getSecondFieldClassificationDropDown(10).getText().toString();
				String sourceField2 = prod.getSecondSourceFieldDropDown(2).getText().toString();
				String dataField2 =  prod.getDataFieldText(1).GetAttribute("value").toString();
				
				Assert.assertEquals("Bates",fieldClassification);
				Assert.assertEquals("BatesNumber",sourceField);
				Assert.assertEquals("Bates Number",dataField);
				Assert.assertEquals("Production",fieldClassification2);
				Assert.assertEquals("TIFFPageCount",sourceField2);
				Assert.assertEquals("TPageCount",dataField2);
				
			
			pass(dataMap,"Second dat is retained");
			} catch(Exception e) {
				e.printStackTrace();
				fail(dataMap,"Second dat is not retained");
			}
		} else {
			fail(dataMap,"Second dat is not retained");
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
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFChkBox().Visible()  ;}}), Input.wait30);
				prod.getTIFFChkBox().click();
				prod.getTIFFTab().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFComponentEnableNativelyProducedDocuments().Visible()  ;}}), Input.wait30);
				prod.getTIFFComponentEnableNativelyProducedDocuments().ScrollTo();
				prod.getTIFFComponentEnableNativelyProducedDocuments().click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFF_SelectTagsButton().Visible()  ;}}), Input.wait30);
				prod.getTIFF_SelectTagsButton().click();
				
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFNativeDocumentTagsDialog().Visible()  ;}}), Input.wait30);

				prod.getTIFF_AttorneyClientTag().click();
				prod.getTIFF_AttorneyWorkProductClientTag().click();
				prod.getTIFF_TagSelectButton().click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getTIFFNativeDocumentTagsDialog().Visible()  ;}}), Input.wait30);
				
				// Collapse TIFF section
				driver.scrollPageToTop();
				prod.getTemplateProductionComponentToggle("TIFF").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getTemplateTIFFPlaceholderText().Displayed()  ;}}), Input.wait30);
				
				// PDF
				prod.getPDFChkBox().click();
				prod.getTemplateProductionComponentToggle("PDF").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFComponentNativelyProducedDocuments().Visible()  ;}}), Input.wait30);
				
				prod.getPDFComponentNativelyProducedDocuments().ScrollTo();
				prod.getPDFComponentNativelyProducedDocuments().click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDF_SelectTagsButton().Visible()  ;}}), Input.wait30);
				prod.getPDF_SelectTagsButton().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFNativeDocumentTagsDialog().Visible()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDF_ConfidentialTag().Visible()  ;}}), Input.wait30);
				
				prod.getPDF_ConfidentialTag().click();
				prod.getPDF_ForeignLanguageTag().click();
				prod.getPDF_TagSelectButton().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getPDFNativeDocumentTagsDialog().Visible()  ;}}), Input.wait30);
				pass(dataMap, "complete_tiff_and_pdf_with_different_tags");
			} catch (Exception e) {
				e.printStackTrace();
				fail(dataMap, "NOT complete_tiff_and_pdf_with_different_tags");
			}			
		} else {
			fail(dataMap, "NOT complete_tiff_and_pdf_with_different_tags");
		}
	}
	@Then("^.*(\\[Not\\] )? verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching$")
	public void verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			// The TIFF placeholder configuration information is incorrect.
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDF_NoMatchingTagsWarning().Displayed();}}), Input.wait30);
				assert prod.getPDF_NoMatchingTagsWarning().getText().equals("The TIFF placeholder configuration information is incorrect.");
				pass(dataMap, "verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching");
			} catch (Exception e) {
				e.printStackTrace();
				fail(dataMap, "NOT verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching");
			}
			//TC 5525 / 5519Verify the error message displays:PDF and TIFF PlaceHolder sections should have the same configuration.
			
		} else {
			fail(dataMap, "NOT verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching");
		}
	}
	
	@When("^.*(\\[Not\\] )? expanding_the_mp3_advanced_section$")
	public void expanding_the_mp3_advanced_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//Clicking "Advanced" in the MP3 Section
			Actions builder = new Actions(driver.getWebDriver());
			prod.getBackBtn().click();
			driver.waitForPageToBeReady();
			builder.moveToElement(prod.getMP3_ToggElement().getWebElement()).perform();
			prod.getMP3_ToggElement().click();
			prod.getMP3Tab().click();
			prod.getMP3_SelectAdvToggle().click();
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
			pass(dataMap, "expanding_the_mp3_advanced_section");
		} else {
			fail(dataMap, "NOT expanding_the_mp3_advanced_section");
		}
	}
	@Then("^.*(\\[Not\\] )? verify_the_mp3_advanced_option_is_enabled_by_default$")
	public void verify_the_mp3_advanced_option_is_enabled_by_default(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//TC 5793Verify in the MP3 Advanced section, Generate Load File (LST) is enabled by default.
			Assert.assertTrue(prod.getMP3AdvancedList().Enabled());
			pass(dataMap, "verify_the_mp3_advanced_option_is_enabled_by_default");
		} else {
			fail(dataMap, "NOT verify_the_mp3_advanced_option_is_enabled_by_default");
		}
	}
	@And("^.*(\\[Not\\] )? refresh_back_to_production_home_page$")
	public void refresh_back_to_production_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//Navigate back to the Production Home Page URL
			on_production_home_page(true,dataMap);
			
			// switch to DefaultProductionSet
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 prod.getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
			prod.getProdExport_ProductionSets().SendKeys("DefaultProductionSet");
			driver.waitForPageToBeReady();
			
			pass(dataMap,"refresh_back_to_production_home_page");
		} else {
			fail(dataMap,"NOT refresh_back_to_production_home_page");
		}
	}
	@And("^.*(\\[Not\\] )? edit_the_production_component_with_a_tiff$")
	public void edit_the_production_component_with_a_tiff(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//Click Mark Incomplete
			//Check the TIFF Checkbox
			//Click TIFF to expand the TIFF section
			//Click Multi-Page
			//Click Select Tags
			//Click the Privileged Tag
			//Click SelectType a Placeholder text in "Enter placeholder text for the privileged docs". 
			//Click Mark Complete.
			try {
				prod.getComponentsMarkInComplete().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getComponentsMarkComplete().Displayed()  ;}}), Input.wait30);

				prod.getTIFFChkBox().click();
				prod.getTIFFTab_Page().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFF_EnableforPrivilegedDocs().Displayed()  ;}}), Input.wait30);
				Actions a = new Actions(driver.getWebDriver());
				for(WebElement x : prod.getTIFF_PageOptions().FindWebElements()) {
					if (x.isDisplayed()) {
						a.moveToElement(x).perform();
						x.click();
						Thread.sleep(2000);
					}
				}
				driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFF_EnableforPrivilegedDocs().Displayed()  ;}}), Input.wait30);
				if(!prod.getTIFF_EnableforPrivilegedDocs().Enabled()) prod.getTIFF_EnableforPrivilegedDocs().click();
				
				prod.getTIFF_SelectTagSButton().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFFSelectTagsModal().Displayed()  ;}}), Input.wait30);
				
				prod.getTIFF_Privileged().click();
				

				
				prod.getSelectButton().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFF_SelectTagText().Displayed()  ;}}), Input.wait30);
				
				
				prod.getTIFF_SelectTagText().SendKeys("placeholder");
				driver.waitForPageToBeReady();
				driver.scrollPageToTop();
				prod.getComponentsMarkComplete().click();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			pass(dataMap,"edit_the_production_component_with_a_tiff");
		} else {
			fail(dataMap,"NOT edit_the_production_component_with_a_tiff");
		}
	}
	@When("^.*(\\[Not\\] )? refreshing_the_current_page$")
	public void refreshing_the_current_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if(scriptState) {
			driver.Navigate().refresh(); 
			driver.waitForPageToBeReady();
			Thread.sleep(1000);
			pass(dataMap,"refreshing_the_current_page");
		} else {
			fail(dataMap, "NOT refreshing_the_current_page");
		}
		
	}
	@Then("^.*(\\[Not\\] )? verify_editing_an_existing_production_saves_the_new_changes$")
	public void verify_editing_an_existing_production_saves_the_new_changes(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//TC 5198Verify all of the new changes in "edit_the_production_component_with_a_tiff" are saved.
			//Verify the Multi-page radio button is selected, the tag is saved, and the placeholder text is saved.
			prod.getBackBtn().click();
			driver.waitForPageToBeReady();
			prod.getTIFFTab_Page().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFF_EnableforPrivilegedDocs().Displayed()  ;}}), Input.wait30);
			Actions a = new Actions(driver.getWebDriver());
			for(WebElement x : prod.getTIFF_PageOptions().FindWebElements()) {
				if (x.isDisplayed()) {
					a.moveToElement(x).perform();
					Assert.assertTrue(x.isEnabled());
					Thread.sleep(2500);
				}
			}
			Assert.assertEquals(prod.getTIFF_PrivileTagSelected().getText(),"Privileged");
			Assert.assertEquals(prod.getTIFFPlaceholderPrivilegedTextField().getText(), "placeholder");
			pass(dataMap,"verify_editing_an_existing_production_saves_the_new_changes");
		} else {
			fail(dataMap, "NOT verify_editing_an_existing_production_saves_the_new_changes");
		}
	}
	@And("^.*(\\[Not\\] )? on_the_tiff_section_select_place_holder_tag_dialog$")
	public void on_the_tiff_section_select_place_holder_tag_dialog(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//Check the TIFF CheckboxClick TIFF to expand the TIFF sectionClick "Select Tags"
			prod.getTIFFChkBox().click();
			prod.getTemplateProductionComponentToggle("TIFF").waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFF_EnableforPrivilegedDocs().Displayed()  ;}}), Input.wait30);
			
			
			prod.getTIFF_EnableforPrivilegedDocs().ScrollTo();
			if(!prod.getTIFF_EnableforPrivilegedDocs().Enabled()) {
				prod.getTIFF_EnableforPrivilegedDocs().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTIFF_SelectTagSButton().Displayed()  ;}}), Input.wait30);	
			}
			prod.getTIFF_SelectTagSButton().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFNativeDocumentTagsDialog().Displayed()  ;}}), Input.wait30);
			pass(dataMap, "on_the_tiff_section_select_place_holder_tag_dialog");
		} else {
			fail(dataMap,"NOT on_the_tiff_section_select_place_holder_tag_dialog");
		}
	}
	@When("^.*(\\[Not\\] )? clicking_non_privledge_tags$")
	public void clicking_non_privledge_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//Try clicking the checkbox for "Default Automation Tag"
			prod.getTIFF_DefaultAutomationTag().click();
			
			pass(dataMap,"clicking_non_privledge_tags");
		} else {
			fail(dataMap,"NOT clicking_non_privledge_tags");
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
			//Check NativeClick Select All on the file typesClick Select TagsCheck Attorney_Client, Confidential, and Default Automation TagClick Select
			try{
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeChkBox().Visible()  ;}}), Input.wait30); 
				prod.getNativeChkBox().Click();
				
				prod.getNativeTab().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNative_SelectAllCheck().Visible()  ;}}), Input.wait30); 

				prod.getNative_SelectAllCheck().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSelectNativeTagsButton().Enabled()  ;}}), Input.wait30); 
				prod.getSelectNativeTagsButton().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNative_DefaultAutomationTag().Visible()  ;}}), Input.wait30); 
				prod.getNative_DefaultAutomationTag().Click();
				prod.getNativeAttorneyClientTag().Click();
				prod.getNativeConfidentialClientTag().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getSelectTagsButton().Enabled()  ;}}), Input.wait30); 
				prod.getSelectTagsButton().Click();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeContainer().Displayed()  ;}}), Input.wait30); 

				
				// Collapse Native tab
				prod.getTemplateProductionComponentToggle("Native").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getNativeContainer().Displayed()  ;}}), Input.wait30);
				
				pass(dataMap,"default Native section is complete");
			}
			catch(Exception e){
				e.printStackTrace();
				fail(dataMap,"default Native section is not complete");
			}
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
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMarkIncompleteButton().Displayed()  ;}}), Input.wait30); 
				Assert.assertTrue(prod.getMarkIncompleteButton().Displayed());
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeTab().Enabled()  ;}}), Input.wait30); 
				prod.getNativeTab().Click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNativeSelectedTagList().Visible()  ;}}), Input.wait30);
				String nativeSelectedTags = prod.getNativeSelectedTagList().GetAttribute("textContent");
				
				List<String> tags = Arrays.asList(nativeSelectedTags.split(","));
				List<String> newlist = tags;
				Collections.sort(newlist);
				Assert.assertEquals(newlist, tags);
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		} else {
			throw new ImplementationException("NOT verify_native_section_with_tags_is_saving_correctly");
		}
	}
	
	@Then("^.*(\\[Not\\] )? verify_privledged_tags_can_only_be_selected$")
	public void verify_privledged_tags_can_only_be_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			//verify_privledged_tags_can_only_be_selected 

			try {
				// If attribute "iscascadeenabled" is false, then tag cannot be selected. If true, then tag can be selected
				String nonPrivTag = prod.getTIFF_DefaultAutomationTag().GetAttribute("iscascadeenabled");
				String PrivTag = prod.getTIFF_Privileged().GetAttribute("iscascadeenabled");

				if (nonPrivTag.equalsIgnoreCase("false")) {
					pass(dataMap, "PASS! Non Priv tags cannot be selected");
				} else fail(dataMap, "FAIL! Non Priv tags can be selected");
				
				if (PrivTag.equalsIgnoreCase("true")) {
					pass(dataMap, "PASS! Priv tags can be selected");
				} else fail(dataMap, "FAIL! Non Priv tags cannot be selected");
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		} else {
			fail(dataMap, "verify_privledged_tags_can_only_be_selected ");
		}
	}
	
	@Then("^.*(\\[Not\\] )? navigated_back_to_production_sections$")
	public void navigated_back_to_production_sections(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		if (scriptState) {
			String productionName = dataMap.get("production_name").toString();
			System.out.println(productionName);
			
			prod.getProductionTileByName(productionName).click();;
			pass(dataMap, "navigated_back_to_production_sections");
		} else {
			fail(dataMap, "NOT navigated_back_to_production_sections");
		}
	}


	@And("^.*(\\[Not\\] )? custom_number_and_sorting_is_added$")
	public void custom_number_and_sorting_is_added(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		//Based on the parameters:1. The parameter for this will be the amount of digits we randomize for this field. If 4 is here, that means we randomize a 4 digit number and Type the number in "Beginning Bates #".  If the parameter starts with a digit and the plus sign, that means that bates should start with digit and randomize the number after it.Ex: 0+6, this means randomize 6 digits, but the start of the number should be 0. So 01234562.
		if (scriptState) {
			Random rnd = new Random();
			String prefix = (String) dataMap.get("prefix");
			int minLength = Integer.parseInt((String)dataMap.get("min_length"));
			String minimumNumber = Integer.toString(minLength);
			String suffix = (String) dataMap.get("suffix");
			String beforeParseBates = (String) dataMap.get("beginning_bates");

			if(!beforeParseBates.contains("+")) {
				int beginningBates = Integer.parseInt((String)dataMap.get("beginning_bates"));
				int low = (int)Math.pow(10, beginningBates-1);
				int high = (int)Math.pow(10, beginningBates);
				int randMultiDigit = rnd.nextInt(high-low) + low;
				String randDigit = Integer.toString(randMultiDigit);
				dataMap.put("beginning_bates", randDigit);
				prod.getBeginningBates().click();
				prod.getBeginningBates().SendKeys(Keys.chord(Keys.CONTROL, "a"));
				prod.getBeginningBates().SendKeys(randDigit);
			}

			else if(beforeParseBates.contains("+")) {
				String[] parts = beforeParseBates.split("\\+");
				String beforePlus = parts[0];
				String afterPlus = parts[1];
				int parsedAfterPlus = Integer.parseInt((afterPlus));
				int low = (int)Math.pow(10, parsedAfterPlus-1);
				int high = (int)Math.pow(10, parsedAfterPlus);
				int randMultiDigit = rnd.nextInt(high-low) + low;
				String randDigit = Integer.toString(randMultiDigit);
				prod.getBeginningBates().click();
				prod.getBeginningBates().SendKeys(Keys.chord(Keys.CONTROL, "a"));
				prod.getBeginningBates().SendKeys(beforePlus);
				prod.getBeginningBates().SendKeys(randDigit);
				String finalBatesNumber = prod.getBeginningBates().getText();
				dataMap.put("beginning_bates", finalBatesNumber);

			}

			prod.gettxtBeginningBatesIDPrefix().click();
			prod.gettxtBeginningBatesIDPrefix().SendKeys(prefix);

			prod.gettxtBeginningBatesIDSuffix().click();
			prod.gettxtBeginningBatesIDSuffix().SendKeys(suffix);

			prod.gettxtBeginningBatesIDMinNumLength().click();
			prod.gettxtBeginningBatesIDMinNumLength().SendKeys(minimumNumber);

			prod.getMarkCompleteLink().click();
			Assert.assertTrue(prod.getMarkCompleteSuccessfulText().Displayed());
			prod.getNextButton().click();

		}
		else {
			 fail(dataMap, "Custom number and sorting is not added");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_production_generate_button$")
	public void clicking_the_production_generate_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking the Generate Button.
			prod.getGenerateButton().click();
			driver.waitForPageToBeReady();

		}
		else fail(dataMap, "Couldnt click generate button");

	}


	@Given("^.*(\\[Not\\] )? verify_a_complex_production_is_able_to_be_generated$")
	public void verify_a_complex_production_is_able_to_be_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4157/3733
			//When used as a context, the parameters here are used to supply the values for "complete_complex_production"

			//Make sure Production Name is displaying the correct name
			String prodName = prod.getGenerateProductionName().getText();
			Assert.assertEquals(prodName, (String)dataMap.get("production_name"));

			//Wait a few seconds for Status text to change to "in progress"
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				!(prod.getGeneratePostGenStatus().getText()).equals("DRAFT")  ;}}), Input.wait30);

			//Make sure Status is In Progress, and InProgress Button is now Displayed
			Assert.assertTrue(prod.getGeneratePostGenStatus().getText().contains("IN PROGRESS") || prod.getGeneratePostGenStatus().getText().contains("in progress") );
			Assert.assertTrue(prod.getGenerateInProgressButton().Displayed());
			pass(dataMap, "Complex Production was able to be generated");
		}
		else fail(dataMap, "Could not verify a complex production can be generated");

	}


	@And("^.*(\\[Not\\] )? waiting_for_production_to_be_complete$")
	public void waiting_for_production_to_be_complete(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//We will need to create a loop here. This loop should check to see if the status changes to Post generation check complete, if not wait 10 seconds and refresh the page. If the status changes to the correct one, exit the loop.Afterwards, click Mark CompleteClick NextDo this loop 20 times max, and it will fail if nothing is returned in that time
			String status = prod.getGeneratePostGenStatus().getText();
			//Loop to wait for Post Generation check complete
			int i =0, j =0;
			while(!status.equalsIgnoreCase("post generation check complete") && i++<30) {
				prod.getBackLink().click();
				driver.waitForPageToBeReady();
				Thread.sleep(10000);
				prod.getNextButton().click();
				driver.waitForPageToBeReady();
				status = prod.getGeneratePostGenStatus().getText();
			}
			Assert.assertEquals("Post generation check complete", status);

			//Make Complete and Next
			prod.getMarkCompleteButton().click();
			prod.getNextButton().click();
			driver.waitForPageToBeReady();
			pass(dataMap, "Passed, post generation check for production");
		}
		else fail(dataMap, "Production Failed, or Timed out");

	}


	@When("^.*(\\[Not\\] )? clicking_review_production$")
	public void clicking_review_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			prod.getReviewproductionButton().click();
			pass(dataMap, "Clicked review production btn");
		}
		else fail(dataMap, "Error clicking review production btn");

	}


	@Then("^.*(\\[Not\\] )? verify_the_review_production_path_is_correct$")
	public void verify_the_review_production_path_is_correct(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState){
			//TC4994Verify the directory in the UI matches the directory we set in "complete_default_production_location_component".Example text from the UI:The documents are produced at the following path : \\MTPVTSSLMQ01\Productions\H021301\Test01
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getDestinationPathText().Displayed() ;}}), Input.wait30);

			//Get Path we inputed in Previous Location Page
			String originalPath = (String)dataMap.get("root_path") + "\\" + (String)dataMap.get("production_directory");

			//Get Path that is current Displayed
			String finalPath = prod.getDestinationPathText().getText();

			//Verify they are equal
			Assert.assertEquals(originalPath,finalPath);
			pass(dataMap, "Production Path was verified");
		}
		else fail(dataMap, "Production Path could not be verified");

	}


	@And("^.*(\\[Not\\] )? navigated_back_onto_the_document_components_section$")
	public void navigated_back_onto_the_document_components_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//Make sure the Bates number is stored. We will verify a new number is generated afterwards.Click the Back link until you get back to "Document Selection"
		if (scriptState) {
			//After Generation -> Head back to get bates Number
			prod.getBackLink().click();;
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getProd_BatesRange().Displayed()  ;}}), Input.wait30);

			//Save our first bates range for later use
			String firstBatesRange = prod.getProd_BatesRange().getText();
			dataMap.put("firstBatesRange", firstBatesRange);
			//Navigate back to document selection
			for(int i =0; i<4; i++) {
				prod.getBackLink().click();
				driver.waitForPageToBeReady();
			}
			pass(dataMap, "stored the original bates number and navigated back to Document Selection");
		}
		else fail(dataMap, "Could not store original bates number and could not navigate back to Document Selection");

	}


	@And("^.*(\\[Not\\] )? change_the_folder_selection_to_by_tags_complete_the_production$")
	public void change_the_folder_selection_to_by_tags_complete_the_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//Click Mark IncompleteClick "Select Tags:"Click Default Automation TagClick Mark CompleteClick NextClick Mark CompleteClick NextClick Mark CompleteClick NextClick Mark CompleteClick NextClick the Generate Button
		if (scriptState) {
			driver.waitForPageToBeReady();

			//Mark Incomplete and Select Default Automation Tag
			Actions builder = new Actions(driver.getWebDriver());
			prod.getMarkIncompleteButton().click();
			prod.getTagsRadioButton().click();
			builder.moveToElement(driver.FindElementById("tagTree").getWebElement()).perform();
			prod.getProductionDocumentSelectTagByName("Default Automation Tag").click();
			driver.waitForPageToBeReady();

			//Go back to generate page
			//prod.getMarkCompleteButton().click();
			for(int i =0; i<4; i++) {
				prod.getMarkCompleteButton().click();
				if(i ==1 && prod.getOkButton().Displayed()){
					prod.getOkButton().click();
				}
				prod.getNextButton().click();
				driver.waitForPageToBeReady();
			}
			driver.waitForPageToBeReady();

			//When on Generation page, click generate and wait for Inprogress to begin
			prod.getGenerateButton().click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				!(prod.getGeneratePostGenStatus().getText()).equals("DRAFT")  ;}}), Input.wait30);
			pass(dataMap, "Succesfully selected a new tag, and navigated back to Generate page");

		}
		else fail(dataMap, "failed to change the folder selection to by tags");

	}


	@Then("^.*(\\[Not\\] )? verify_overwriting_the_document_selection_generates_a_new_bate_number$")
	public void verify_overwriting_the_document_selection_generates_a_new_bate_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//TC 4956Verify the new bate number displayed does not match the prior bates number
		if (scriptState) {

			int i =0;
			String batesRangeExists = prod.getProd_BatesRange().getText();

			//Loop until bates range is displayed
			while(batesRangeExists.equals("") && i++<20) {
				prod.getBackLink().click();
				driver.waitForPageToBeReady();
				Thread.sleep(10000);
				prod.getNextButton().click();
				driver.waitForPageToBeReady();
				batesRangeExists = prod.getProd_BatesRange().getText();
			}
			String secondBatesRange = prod.getProd_BatesRange().getText();
			//Make sure we get a different bates range after, overwriting our doc selection
			Assert.assertFalse( secondBatesRange.equals((String)dataMap.get("firstBatesRange")) );
			pass(dataMap, "verified that overwriting the document generates a new bate numbers");
		}
		else fail(dataMap, "failed to verify that overwriting the document generates a new bate numbers");

	}


	@Then("^.*(\\[Not\\] )? verify_native_productions_can_be_generated$")
	public void verify_native_productions_can_be_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5067/4897Verify the production is set to "INPROGRESS".
			Assert.assertEquals("in progress", prod.getGenerateInProgressButton().getText());
			pass(dataMap, "passed the Native Production");
		}
		else fail(dataMap, "Failed the Native Production");

	}


	@When("^.*(\\[Not\\] )? navigating_to_the_vm_production_location$")
	public void navigating_to_the_vm_production_location(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click Review Production
			//* Copy the path of the VM
			//* Connect to the Virtual Machine
			//* Open file explorer
			//* Type the following into the directory search bar: "\\MTPVTSSLMQ01" Computer
			//* Navigate to the path copied
			//* Double click the file to open the production
			//
			throw new ImplementationException("navigating_to_the_vm_production_location");
		} else {
			throw new ImplementationException("NOT navigating_to_the_vm_production_location");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_generated_files_display_id_as_the_bates_number$")
	public void verify_the_generated_files_display_id_as_the_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4993Verify on the document generated, the key ID should be the bates number for the production.
			throw new ImplementationException("verify_the_generated_files_display_id_as_the_bates_number");
		} else {
			throw new ImplementationException("NOT verify_the_generated_files_display_id_as_the_bates_number");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_bates_generated_number_follows_the_custom_numbering$")
	public void verify_the_bates_generated_number_follows_the_custom_numbering(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		//TC4960 / 8349If the bates range does generate, below is an explanation of what the number should look like based on the custom numbering we provided in "custom_number_and_sorting_is_added". We need to verify all of the data in the parameter we used is in the bates number itself in the correct order.For example, if you specify 1001 as the Beginning Bates #, "B" for Prefix, "T" for Suffix, and "8" for Minimum Number Length (used for number padding), then a sample bates number generated would look like "B00001001T".
		if (scriptState) {
			String status = prod.getGeneratePostGenStatus().getText();
			String prefix = (String)dataMap.get("prefix");
			String suffix = (String)dataMap.get("suffix");
			String batesNum = (String)dataMap.get("randBates");
			int randLength = Integer.parseInt((String)dataMap.get("beginning_bates"));
			int minLength = Integer.parseInt((String)dataMap.get("min_length"));

			//This loop was added to wait for the Bates range to appear
			int i =0, j =0;
			while(!status.equalsIgnoreCase("Pre generation check complete") && i++<20) {
				prod.getBackLink().click();
				driver.waitForPageToBeReady();
				Thread.sleep(10000);
				prod.getNextButton().click();
				driver.waitForPageToBeReady();
				status = prod.getGeneratePostGenStatus().getText();
			}

			//Split Bates Range to get one of the Bates Numbers to verify its format format
			String[] finalBatesRange = (prod.getProd_BatesRange().getText()).split(" - ");
			char[] firstBatesNum = (finalBatesRange[0]).toCharArray();

			//Verify Prefix and Suffix, and Correct BatesLength
			Assert.assertEquals(firstBatesNum[0], prefix.charAt(0));
			Assert.assertEquals(firstBatesNum[firstBatesNum.length-1], suffix.charAt(0));
			Assert.assertEquals(firstBatesNum.length, minLength+2);
			pass(dataMap ,"Bates Number Range follows correct format");
		}
		else fail(dataMap, "Bates Number Range does not follow correct format");

	}


	@And("^.*(\\[Not\\] )? a_file_size_ingestion_was_completed$")
	public void a_file_size_ingestion_was_completed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Ingest a 10 mb file. Login as a project admin in the project 021320_EG
			//first.Call the ingestion method: new_ingestion_create. This takes in a parameter or pdf file.
			//We will have to create a text or pdf that is 10 mb and provide that for the ingestion. 
			throw new ImplementationException("a_file_size_ingestion_was_completed");
		} else {
			throw new ImplementationException("NOT a_file_size_ingestion_was_completed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_a_production_document_with_appropriate_file_size_returns_no_errors$")
	public void verify_a_production_document_with_appropriate_file_size_returns_no_errors(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6185 1. Verify the error message does not appear: There Should not be any error message such as WidthAndHeightCannotBeNegative2. Verify the "Review Production" button is displayed after a successful generation.
			throw new ImplementationException("verify_a_production_document_with_appropriate_file_size_returns_no_errors");
		} else {
			throw new ImplementationException("NOT verify_a_production_document_with_appropriate_file_size_returns_no_errors");
		}

	}


	@And("^.*(\\[Not\\] )? completed_summary_and_preview_component$")
	public void completed_summary_and_preview_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Verify you are on the Summary and Preview page.Click Mark CompleteClick Next
			driver.waitForPageToBeReady();
			Assert.assertEquals("Summary and Preview", prod.getProductionSectionPageTitle().getText());
			prod.getbtnProductionSummaryMarkComplete().click();
			prod.getbtnProductionSummaryNext().click();
			pass(dataMap, "Summary and Preview page displayed");
		} else {
			 fail(dataMap, "Not on Summary and Preview page");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_productions_are_set_to_draft_by_default$")
	public void verify_productions_are_set_to_draft_by_default(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4909/5884For this test case, set only DAT to true in the context "complete_completex_production_component"
			//* Verify on the Generate component, the field "Status:" should display "DRAFT".
			//* Verify bates range should be blank
			//* Verify the generate button is enabled.
			//
			driver.waitForPageToBeReady();
			Assert.assertEquals("DRAFT", prod.getGeneratePostGenStatus().getText());
			Assert.assertEquals("", prod.getProd_BatesRange().getText());
			Assert.assertTrue(prod.getGenerateButton().Enabled());
			pass(dataMap, "Productions are set to DRAFT");

		} else {
			fail(dataMap, "Productions are not set to draft");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_user_is_able_to_click_on_confirm_production$")
	public void verify_the_user_is_able_to_click_on_confirm_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		//TC 4554Verify the link 'Confirm Production & Commit' exists.Click on 'Confirm Production & Commit'Verify no errors are returned and it is successful.
		if (scriptState) {
			int j =0;
			//This loop finds any lingering pop up messages from "Mark Complete" buttons on previous pages, and closes them
			//We need these pop ups closed, because we will quickly press "confirm prod and commit" and we need to get that unique pop up message
			for(int i =1; i<100; i++) {
				if(j ==1) break;
				if(prod.getProductionConfirmPopupCloseBtn(i).FindWebElements().size()!=0) {
					prod.getProductionConfirmPopupCloseBtn(i).FindWebElements().get(0).click();
					j=1;
				}
			}
			//Wait for lingering messages to fade
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				!prod.getConfirmCompletePopup().Displayed() ;}}), Input.wait30);
			Assert.assertTrue(prod.getConfirmAndCommitProdLink().Enabled() && prod.getConfirmAndCommitProdLink().Displayed());

			//Click confirm link
			prod.getConfirmAndCommitProdLink().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				prod.getConfirmCompletePopup().Displayed() ;}}), Input.wait30);
			//Get successful popup message
			Assert.assertEquals("Commit action has been started as a background task. You will be notified upon completion. Please refresh this page to see the latest status.",
					prod.getConfirmCompletePopup().getText());
			pass(dataMap, "Was able to verify the functionality of Confirm production and commit button");
		}
		else fail(dataMap, "Failed to properly click Confirm production button");

	}


	@And("^.*(\\[Not\\] )? connect_to_the_database$")
	public void connect_to_the_database(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Connect to the database:Open sql server management studio. Connect to MTPVTDSLDB02 Enter Valid Credentials Click Connect Open a new query
			throw new ImplementationException("connect_to_the_database");
		} else {
			throw new ImplementationException("NOT connect_to_the_database");
		}

	}


	@When("^.*(\\[Not\\] )? executing_the_query$")
	public void executing_the_query(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Execute the following query based on the params
			throw new ImplementationException("executing_the_query");
		} else {
			throw new ImplementationException("NOT executing_the_query");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_in_the_database_the_background_process_for_pregen_is_active$")
	public void verify_in_the_database_the_background_process_for_pregen_is_active(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4560 Verify the status of the results of the query say "Pre-generation in progress"
			throw new ImplementationException("verify_in_the_database_the_background_process_for_pregen_is_active");
		} else {
			throw new ImplementationException("NOT verify_in_the_database_the_background_process_for_pregen_is_active");
		}

	}


	@And("^.*(\\[Not\\] )? open_the_first_production$")
	public void open_the_first_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click on the first production's name from the filtered results.
			try{
				driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProductionTitleName().Displayed()  ;}}), Input.wait30);
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_UP.toString());
			Thread.sleep(1000);
			prod.getProductionTitleName().click();
			}catch (Exception e){
				fail(dataMap, "Unable to click the Production Title");
			}
			
		} else {
			throw new ImplementationException("NOT open_the_first_production");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_failed_generation_link$")
	public void clicking_the_failed_generation_link(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking the link for the failed generation
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getPreGenerationCheckFailedLink().Displayed()  ;}}), Input.wait30);
			prod.getPreGenerationCheckFailedLink().click();
		} else {
			throw new ImplementationException("NOT clicking_the_failed_generation_link");
		}
	}


	@Then("^.*(\\[Not\\] )? verify_failed_productions_display_the_approriate_error_message$")
	public void verify_failed_productions_display_the_approriate_error_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5001Verify clicking the link for the error message displays a dialog with text displaying why the production failed.Close the dialog afterwards.
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getErrorDataTable().Displayed()  ;}}), Input.wait30);
			System.out.println(prod.getErrorDataTable().getText());
			Assert.assertTrue(prod.getErrorDataTable().Displayed());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getCloseModalButton().Displayed()  ;}}), Input.wait30);
			prod.getCloseModalButton().click();
			
		} else {
			throw new ImplementationException("NOT verify_failed_productions_display_the_approriate_error_message");
		}

	}
	

	@When("^.*(\\[Not\\] )? expanding_the_pdf_production_component$")
	public void expanding_the_pdf_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			prod.getPDFTab().click();
			prod.getPDFAdvanced().click();
			pass(dataMap, "Expanded the pdf production component");
		} else {
			fail(dataMap, "Can not expand the pdf production component");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_pdf_product_component_displays_the_correct_default_options$")
	public void verify_the_pdf_product_component_displays_the_correct_default_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 3764 / 6430 / 7247The verifcation here is the same for the TIFF version of this outcome which is already automated. Check to see if this can be reused since both TIFF and PDF have the same buttons and fields.

			//* Verify the first section is Page Options
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFFirstPageElement().Enabled();
				}
			}), Input.wait30);
			Assert.assertEquals("Page Options:", prod.getPDFFirstPageElement().getText());

			//* Verify in Page Options, the section "Single / Multiple:" has the options "Multi-page" and "Single Page" with Single Page selected as the default option with a radio button.
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFMultiRadio().Enabled();
				}
			}), Input.wait30);
			Assert.assertNull(prod.getPDFMultiRadio().GetAttribute("checked"));

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFSingleRadio().Enabled();
				}
			}), Input.wait30);
			Assert.assertEquals("true", prod.getPDFSingleRadio().GetAttribute("checked"));

			//* Verify in Page Options, the section "Format" has the options "Letter" and "A4" with Letter selected as the default option with a radio button.
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFA4Radio().Enabled();
				}
			}), Input.wait30);
			Assert.assertNull(prod.getPDFA4Radio().GetAttribute("checked"));

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFLetterRadio().Enabled();
				}
			}), Input.wait30);
			Assert.assertEquals("true", prod.getPDFLetterRadio().GetAttribute("checked"));

			//* Verify in Page Options, Blank Page Removal, Preserve Color, and Do not produce full content TIFFs or placeholder TIFFs for Natively Produced Docs: all have the option set to the red "x" by default. 
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFColorToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertFalse(prod.getPDFColorToggle().Selected());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFBlankRemovalToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertFalse(prod.getPDFBlankRemovalToggle().Selected());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFSkipPDFGenerationToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertFalse(prod.getPDFSkipPDFGenerationToggle().Selected());

			//* Verify in Page Options, the option "Rotate Landscape pages to portrait layout:" has the option "No Rotation" set to default.
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFRotateDropdown().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("No Rotation", prod.getPDFRotateDropdown().selectFromDropdown().getFirstSelectedOption().getText());

			//* Verify the "Branding" section contains the fields "Location", "Branding Text", "Speicify Default Branding", "Insert Metadata Field" link, and "+ Specify Branding by Selecting Tags:" link.
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFBrandingLocation().Displayed();
				}
			}), Input.wait30);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFBrandingText().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Branding Text:", prod.getPDFBrandingText().getWebElement().getText());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFSpecifyDefaultBranding().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Specify Default Branding", prod.getPDFSpecifyDefaultBranding().getText());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFMetadataField().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Insert Metadata Field", prod.getPDFMetadataField().getText());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.gePDFSpecificBrandingBySelectionTagsLink().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Specify Branding by Selecting Tags:", prod.gePDFSpecificBrandingBySelectionTagsLink().getText());

			//* Verify in the Branding section for Location, there is a rectangle with the options, "LEFT", "CENTER", "RIGHT" at the top and bottom of the rectable with the words "--Page Body--" in the middle
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDF_CenterHeaderBranding().Displayed();
				}
			}), Input.wait30);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFCenterFooterBranding().Displayed();
				}
			}), Input.wait30);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFLeftHeaderBranding().Displayed();
				}
			}), Input.wait30);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFLeftFooterBranding().Displayed();
				}
			}), Input.wait30);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFRightHeaderBranding().Displayed();
				}
			}), Input.wait30);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFRightFooterBranding().Displayed();
				}
			}), Input.wait30);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFRectangleMiddleText().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("--Page Body--", prod.getPDFRectangleMiddleText().getText());

			//* Verify top left "LEFT" option selected by default
			Assert.assertTrue(prod.getPDFLeftHeaderBranding().getWebElement().getAttribute("class").contains("btn-primary"));

			//* Verify in the Branding section, Specify Default Branding contains a section with the text "Enter default branding for the selection location on the page."
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFDefaultBrandingRectangleText().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Enter default branding for the selected location on the page.", prod.getPDFDefaultBrandingRectangleText().GetAttribute("placeholder").toString());

			//* Verify in the "Placeholders" section, "Enable for Privileged Docs:" is checked green by default, there is a "Select tags" blue button to the right of Enable for Privileged Docs:, "Enable for Tech Tissue Docs:" is checked red by default, "+ Enable for Natively Produced Documents:" link with a question mark button next to it, and a rectangle with the watermark "Enter placeholder text for the privileged docs" with a link "Insert Metadata Field" under it. 
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFEnableForPrivilegedDocs().Displayed();
				}
			}), Input.wait30);
			if (prod.getPDFEnableForPrivDocsToggle().GetAttribute("checked") != null)
				Assert.assertEquals("true", prod.getPDFEnableForPrivDocsToggle().GetAttribute("checked").toString());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFPriveldgeSelectTagButton().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Select Tags", prod.getPDFPriveldgeSelectTagButton().getText());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFEnableTechIssueDocsToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertFalse(prod.getPDFEnableTechIssueDocsToggle().Selected());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFEnableNativelyProducedDocs().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Enable for Natively Produced Documents:", prod.getPDFEnableNativelyProducedDocs().getText());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFEnableNativelyProducedDocsHelpTip().Displayed();
				}
			}), Input.wait30);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFPlaceholderTextField().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Enter placeholder text for the privileged docs", prod.getPDFPlaceholderTextField().GetAttribute("Placeholder"));

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFPlaceholderMetadataLink().Displayed();
				}
			}), Input.wait30);
			Assert.assertEquals("Insert Metadata Field", prod.getPDFPlaceholderMetadataLink().getText());

			//* In the Redactions section, the option "Burn Redactions:" is checked red by default.
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFBurnRedactionToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertFalse(prod.getPDFBurnRedactionToggle().Selected());

			//* In the "Advanced" section, "Generate Load File (LST):" is checked green by default, "Load File Type:" is set to "Log" by default, and "Slip Sheets" is checked red by default.
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFLSTLoadFileToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertTrue(prod.getPDFLSTLoadFileToggle().Selected());

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getPDFSlipSheetsToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertFalse(prod.getPDFSlipSheetsToggle().Selected());

			pass(dataMap,"The pdf product component displays the correct default options");
		} else {
			fail(dataMap,"The pdf product component displays the correct default options");
		}

	}


	@When("^.*(\\[Not\\] )? expanding_the_native_production_component$")
	public void expanding_the_native_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//The user should be on the "Production Components" section of Productions.The user should click on "Native" to expand the Native section.The user should click on "Advanced" to expand the Native Advanced section.
			prod.getNativeTab().click();
			prod.getNativeAdvanced().click();
			pass(dataMap, "Expanded the native production component");
		} else {
			fail(dataMap, "Can not expand the native production component");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_native_product_component_displays_the_correct_default_options$")
	public void verify_the_native_product_component_displays_the_correct_default_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4165 / 4167
			//* Verify Generate Load File (LST) button is set to No by default. 
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getNativeAdvancedLST().Enabled()  ;}}), Input.wait30);
			Assert.assertFalse(prod.getNativeAdvancedLSTToggle().Selected());

			//* Verify the radio button in the section "Advanced Families of Redacted and Privileged Documents" is set to "Do not produce natives of the parents of privileged and redacted docs" by default.
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getNativeAdvancedExcludeNativeOfParentRadio().Enabled()  ;}}), Input.wait30);
			Assert.assertTrue(prod.getNativeAdvancedExcludeNativeOfParentRadio().Selected());

			//* Verify the section "Select File Types and/or Tags:", each of the file type options below are NOT checked by default. 
			for (WebElement checkbox : prod.getNativeSelectFileTypeOrTagsTableCheckboxes().FindWebElements()) {
				Assert.assertFalse(checkbox.isSelected());
			}
			//* Verify the availability of the option for Force Protection on Excel files to not allow any modifi cations BUT allow to vi ew formulas and filter t he data. 
			//Need to double check step 4, this seems like an option that is not available.
			pass(dataMap,"The native product component displays the correct default options");
		} else {
			fail(dataMap,"The native product component does not display the correct default options");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_numbering_sorting_fields_are_displayed$")
	public void verify_production_numbering_sorting_fields_are_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4902Numbering and Sorting page is already displayed

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							//Verify level - page/document radio buttons are displayed
							prod.getNumPageLevelRadioButton().Displayed() &&
							prod.getNumDocumentLevelRadioButton().Displayed() &&

							//Verify format - metadata radio button is displayed
							prod.getNumUseMetaFieldButton().Displayed() &&

							//Verify format - Beginning Bates/Prefix/Suffix/Min Length input fields are displayed
							prod.getBeginningBates().Displayed() &&
							prod.gettxtBeginningBatesIDPrefix().Displayed() &&
							prod.gettxtBeginningBatesIDSuffix().Displayed() &&
							prod.gettxtBeginningBatesIDMinNumLength().Displayed() &&

							//Verify sorting - sort by metadata radio button is displayed
							prod.getNumSortMetaRadioButton().Displayed() &&

							//Verify sorting - sub-sort dropdown field is displayed
							prod.getlstSubSortingMetaData().Displayed();
				}
			}), Input.wait30);
			pass(dataMap,"Production numbering sorting fields are displayed");
		} else {
			fail(dataMap,"Production numbering sorting fields are not displayed");
		}

	}

	@And("^.*(\\[Not\\] )? store_the_default_template_values$")
	public void store_the_default_template_values(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click Manage Templates
			//* On"DefaultAutomationTemplate" click View
			//* In Priv Guard, store the rule for validation later
			//* Click Next
			//* Store the DAT values under "Specify DAT Field Mapping" for validation later.
			//* Under TIFF, store the placeholder text
			//* Under TIFF, store the Rotate Landscape pages to portrait layout:(Rotate 90 degrees etc..)
			//* Under PDF, store the placeholder text
			//* Under PDF, store the Rotate Landscape pages to portrait layout:
			//* Click Next
			//* In Number and sorting, store all of the values for beginning bates #, prefix, suffix, and min number length for validation later.
			//* Click Close
			//
			
			try {
				String templateName = dataMap.get("prod_template").toString();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getManageTemplatesTab().Displayed()  ;}}), Input.wait30);
				prod.getManageTemplatesTab().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplateTableGridDiv().Displayed()  ;}}), Input.wait30);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getCustomTemplateViewButton(templateName).Displayed()  ;}}), Input.wait30);
				prod.getCustomTemplateViewButton(templateName).click();
				


				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivledgedRules().Displayed()  ;}}), Input.wait30);
				
				// Save Privilege rules as a single string, with each rule separated by comma.
				String privRule = "";
				for(WebElement rule: prod.getPrivledgedRules().FindWebElements()) {
					privRule = privRule.concat(rule.getText()+",");
				}
				// Remove extra comma at the end
				String privRules = privRule.substring(0, privRule.length() - 1);
				
				prod.getTemplatePrivGuardNextButton().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplateProductionComponentsPanel().Displayed()  ;}}), Input.wait30);
				
				// Expand DAT toggle
				prod.getTemplateProductionComponentToggle("DAT").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplateFieldClassificationValue().Displayed()  ;}}), Input.wait30);
				
				String templateFieldClassification = prod.getTemplateFieldClassificationValue().selectFromDropdown().getFirstSelectedOption().getText();
				String templateSourceField = prod.getTemplateSourceFieldValue().selectFromDropdown().getFirstSelectedOption().getText();
				String templateDatField = prod.getTemplateDatFieldValue().GetAttribute("value");
				
				// Collapse DAT 
				prod.getTemplateProductionComponentToggle("DAT").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getTemplateFieldClassificationValue().Displayed()  ;}}), Input.wait30);

				// Expand TIFF toggle
				prod.getTemplateProductionComponentToggle("TIFF").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplateTIFFPlaceholderText().Displayed()  ;}}), Input.wait30);
				
				String templateTIFFPageRotatePreference = prod.getTemplateTIFFPageRotatePreferenceSelectedValue().getText();
				String templateTIFFPlacerholderText = prod.getTemplateTIFFPlaceholderText().getText();			
	
				// Collapse TIFF 
				prod.getTemplateProductionComponentToggle("TIFF").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getTemplateTIFFPlaceholderText().Displayed()  ;}}), Input.wait30);
				
				//Expand PDF toggle
				prod.getTemplateProductionComponentToggle("PDF").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplatePDFPageRotatePreferenceSelectedValue().Displayed()  ;}}), Input.wait30);
				
				String templatePDFPageRotatePreference = prod.getTemplatePDFPageRotatePreferenceSelectedValue().getText();

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplatePDFPlaceholderText().Displayed()  ;}}), Input.wait30);
				String templatePDFPlaceholderText = prod.getTemplatePDFPlaceholderText().getText();

				// Goto Numbering and Sorting
				prod.getComponentsMarkNext().waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplatePrefixValue().Displayed()  ;}}), Input.wait30);
				
				String beginningBatesValue = prod.getTemplateBeginningBatesValue().GetAttribute("value");
				String getTemplatePrefixValue = prod.getTemplatePrefixValue().GetAttribute("value");	
				String getTemplateSuffixValue = prod.getTemplateSuffixValue().GetAttribute("value");
				String getTemplateMinNumValue = prod.getTemplateMinNumValue().GetAttribute("value");

				// Store values to dataMap
				dataMap.put("templatePrivRules", privRules);
				dataMap.put("templateFieldClassification", templateFieldClassification);
				dataMap.put("templateSourceField", templateSourceField);
				dataMap.put("templateDatField", templateDatField);
				dataMap.put("templateTIFFPlacerholderText", templateTIFFPlacerholderText);
				dataMap.put("templatePageRotatePreference", templateTIFFPageRotatePreference);
				dataMap.put("templatePDFPlaceholderText", templatePDFPlaceholderText);
				dataMap.put("templatePDFPageRotatePreference", templatePDFPageRotatePreference);
				dataMap.put("beginningBatesValue", beginningBatesValue);
				dataMap.put("getTemplatePrefixValue", getTemplatePrefixValue);
				dataMap.put("getTemplateSuffixValue", getTemplateSuffixValue);
				dataMap.put("getTemplateMinNumValue", getTemplateMinNumValue);
				
				// Close dialog
				prod.getTemplateCloseButton().click();
				
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "unable to store default template values");
			}
			
		} else {
			throw new ImplementationException("NOT store_the_default_template_values");
		}

	}


	@And("^.*(\\[Not\\] )? adding_branding_to_pdf$")
	public void adding_branding_to_pdf(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Expand PDF
			//* Under the branding placeholder text field, type in "Automation branding on PDF"
			//
			String brandingText = "Automation branding on PDF";
			dataMap.put("pdfBrandingText", brandingText);
			
			try {
				prod.getTemplateProductionComponentToggle("PDF").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPriveldge_SelectPDFTagButton().Visible()  ;}}), Input.wait30);

				prod.getPDFBrandingPlaceholderTextField().Clear();
				prod.getPDFBrandingPlaceholderTextField().SendKeys(brandingText);
				
				// Collapse PDF section
				prod.getTemplateProductionComponentToggle("PDF").ScrollTo();
				prod.getTemplateProductionComponentToggle("PDF").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getPriveldge_SelectPDFTagButton().Visible()  ;}}), Input.wait30);
			} catch (Exception e) {
				System.out.println(e);
			}

			
		
			
			
		} else {
			throw new ImplementationException("NOT adding_branding_to_pdf");
		}

	}


	@And("^.*(\\[Not\\] )? the_default_template_for_production_components_is_displayed$")
	public void the_default_template_for_production_components_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Verify DAT, TIFF, PDF, and MP3 checkboxes are checked
			//* Verify in the DAT section, the mapping fields are "Bates", "BatesNumber", and the "DAT FIELD" has the value "def".
			//* Verify in TIFF, the tag is "Priviledged" and the placeholder text displays "Default Template Tiff".
			//* Verify in TIFF, the rotation should be "Rotate 90 degrees counter clock-wise"
			//* Verify in Tiff, the burn redaction button is enabled green and the "Default Automation Redaction" tag is checked.
			//* Verify in PDF, the tag is "Priviledged" and the placeholder text displays "Default Template PDF".
			//* Verify in PDF, the rotation should be "Rotate 90 degrees counter clock-wise"
			//* Verify in PDF, the burn redaction button is enabled green and the "Default Automation Redaction" tag is checked.
			//* Verify in MP3, Burn redactions is enabled, Default Automation Redaction is checked, Redaction Style is set to Beep, and expanding the Advanced section has Generate Load File (LST) enabled.
			//These values should be compared to what was stored in "store_the_default_template_values"
			//Click Mark CompletedClick Next
			try {				
				// Get values from dataMap
				String templateFieldClassification = dataMap.get("templateFieldClassification").toString();
				String templateSourceField = dataMap.get("templateSourceField").toString();
				String templateDatField = dataMap.get("templateDatField").toString();
				String templateTIFFPlacerholderText = dataMap.get("templateTIFFPlacerholderText").toString();
				String templateTIFFPageRotatePreference = dataMap.get("templatePageRotatePreference").toString();
				String templatePDFPlaceholderText = dataMap.get("templatePDFPlaceholderText").toString();
				String templatePDFPageRotatePreference = dataMap.get("templatePDFPageRotatePreference").toString();

				// Expand Advanced Production Components
				prod.getProductionComponentAdvanceToggle().waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionComponentMP3FilesCheckbox().Displayed()  ;}}), Input.wait30);
				
				
				Boolean isDATSelected = prod.getProductionComponentDATCheckbox().Selected();
				Boolean isTIFFSelected = prod.getProductionComponentTIFFCheckbox().Selected();
				Boolean isPDFSelected = prod.getProductionComponentPDFCheckbox().Selected();
				Boolean isMP3Selected = prod.getProductionComponentMP3FilesCheckbox().Selected();
				
				
				// Verify expected checkboxes are selected
				try {		
					Assert.assertTrue(isDATSelected);
					Assert.assertTrue(isTIFFSelected);
					Assert.assertTrue(isPDFSelected);
					Assert.assertTrue(isMP3Selected);
				} catch (Exception e) {
					fail(dataMap, "Expected (DAT, TIFF, PDF, MP3) checkboxes are not selected");
				}

				// Expand MP3 toggle
				prod.getTemplateProductionComponentToggle("MP3").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getMP3RedactionStyleValue().Displayed()  ;}}), Input.wait30);
				
				String redactionStyleValue = prod.getMP3RedactionStyleValue().selectFromDropdown().getFirstSelectedOption().getText();
				String mp3BurnRedactions = prod.getMP3BurnRedactions().GetAttribute("class");
				String mp3DefaultAutomationRedaction = prod.getDefaultAutomationRedactionTag().GetAttribute("class");
				
				
				// Verify MP3
				try {
					Assert.assertEquals(redactionStyleValue, "Beep");
					Assert.assertTrue(mp3BurnRedactions.contains("active"));
					Assert.assertTrue(mp3DefaultAutomationRedaction.contains("clicked"));
				} catch (Exception e) {
					fail(dataMap, "Expected values in MP3 section are not correct");
				}

				// Collapse MP3
				prod.getTemplateProductionComponentToggle("MP3").click();

				
				// Expand DAT toggle
				prod.getTemplateProductionComponentToggle("DAT").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplateFieldClassificationValue().Displayed()  ;}}), Input.wait30);
				String fieldClassification = prod.getTemplateFieldClassificationValue().selectFromDropdown().getFirstSelectedOption().getText();
				String sourceField = prod.getTemplateSourceFieldValue().selectFromDropdown().getFirstSelectedOption().getText();
				String datField = prod.getTemplateDatFieldValue().GetAttribute("value");
				
				// Verify DAT
				try {
					Assert.assertEquals(fieldClassification, templateFieldClassification);
					Assert.assertEquals(sourceField, templateSourceField);
					Assert.assertEquals(templateDatField, datField);
					pass(dataMap, "DAT values are correct");
				} catch (Exception e) {
					fail(dataMap, "DAT values are not correct");
				}
				
				// Collapse DAT 
				prod.getTemplateProductionComponentToggle("DAT").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getTemplateFieldClassificationValue().Displayed()  ;}}), Input.wait30);

				// Expand TIFF toggle
				prod.getTemplateProductionComponentToggle("TIFF").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplateTIFFPlaceholderText().Displayed()  ;}}), Input.wait30);
				String TIFFPageRotatePreference = prod.getTemplateTIFFPageRotatePreferenceSelectedValue().getText();			
				String TIFFPlacerholderText = prod.getTemplateTIFFPlaceholderText().getText();						
				String burnRedactAttr = prod.getTIFFBurnRedactionInput().GetAttribute("class");

				// Verify TIFF
				try {
					Assert.assertEquals(prod.getTIFFPlaceholderTag().getText(), "Privileged");
					Assert.assertEquals(TIFFPageRotatePreference, templateTIFFPageRotatePreference);
					Assert.assertEquals(templateTIFFPlacerholderText, TIFFPlacerholderText);
					Assert.assertTrue(burnRedactAttr.contains("activeC"));
				} catch (Exception e) {
					fail(dataMap, "TIFF values are not correct");
				}
				
				// Collapse TIFF 
				prod.getTemplateProductionComponentToggle("TIFF").click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						!prod.getTemplateTIFFPlaceholderText().Displayed()  ;}}), Input.wait30);
				
				//Expand PDF toggle
				prod.getTemplateProductionComponentToggle("PDF").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFPlaceholderTag().Displayed()  ;}}), Input.wait30);
				String PDFPageRotatePreference = prod.getTemplatePDFPageRotatePreferenceSelectedValue().getText();
				String PDFPlaceholderText = prod.getTemplatePDFPlaceholderText().getText();
				String defaultAutomationRedactionInput = prod.getDefaultAutomationRedactionTag().GetAttribute("class");

				// Verify PDF
				try {
					Assert.assertEquals(prod.getPDFPlaceholderTag().getText(), "Privileged");
					Assert.assertEquals(templatePDFPageRotatePreference, PDFPageRotatePreference);
					
					// Skip verification of placeholder text if new text was entered in previous step
					if (!dataMap.containsKey("pdfBrandingText")) {
						Assert.assertEquals(PDFPlaceholderText, templatePDFPlaceholderText);
					}
					Assert.assertTrue(defaultAutomationRedactionInput.contains("clicked"));
				} catch (Exception e) {
					fail(dataMap, "PDF values are not correct");
				}

				// Collapse PDF 
				prod.getTemplateProductionComponentToggle("PDF").ScrollTo();
				prod.getTemplateProductionComponentToggle("PDF").waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFPlaceholderTag().Displayed()  ;}}), Input.wait30);
				
				prod.getComponentsMarkComplete().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getComponentsMarkNext().Enabled()  ;}}), Input.wait30);
				prod.getComponentsMarkNext().click();
				driver.waitForPageToBeReady();
				
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "Unable to verify production components");
			}
		} else {
			throw new ImplementationException("NOT the_default_template_for_production_components_is_displayed");
		}

	}


	@And("^.*(\\[Not\\] )? the_default_template_for_numbering_is_displayed$")
	public void the_default_template_for_numbering_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Verify under Format, "Beginning Bates #" is 323 (or whatever is stored from the template)
			//* Verify under Prefix, it is "S"
			//* Verify under Suffix, it is "Q"
			//* Verify under Min Number Length it is "8".
			//These values should be compared to what was stored in "store_the_default_template_values"
			// after validation, change beginning bates value to three random digits
			//Click Mark Complete
			//Click Next
			
			try {
				// Retrieve values from dataMap in store_the_default_template_values
				String getTemplatebeginningBatesValue = dataMap.get("beginningBatesValue").toString();
				String getTemplatePrefixValue = dataMap.get("getTemplatePrefixValue").toString();
				String getTemplateSuffixValue = dataMap.get("getTemplateSuffixValue").toString();
				String getTemplateMinNumValue = dataMap.get("getTemplateMinNumValue").toString();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getTemplatePrefixValue().Displayed()  ;}}), Input.wait30);
				
				String beginningBatesValue = prod.getTemplateBeginningBatesValue().GetAttribute("value");
				String TemplatePrefixValue = prod.getTemplatePrefixValue().GetAttribute("value");
				String TemplateSuffixValue = prod.getTemplateSuffixValue().GetAttribute("value");
				String TemplateMinNumValue = prod.getTemplateMinNumValue().GetAttribute("value");

				try {
					Assert.assertEquals(beginningBatesValue, getTemplatebeginningBatesValue);
					Assert.assertEquals(TemplatePrefixValue, getTemplatePrefixValue);
					Assert.assertEquals(TemplateSuffixValue, getTemplateSuffixValue);
					Assert.assertEquals(TemplateMinNumValue, getTemplateMinNumValue);
					pass(dataMap, "Numbering and Sorting values are correct");
				} catch (Exception e) {
					fail(dataMap, "Numbering and Sorting values are not correct");
				}
				
				// Change beginning bates value after verification
				Random random = new Random();
				int i = random.nextInt(999);
				String newBeginningBatesNum = String.valueOf(i);
				prod.getBeginningBates().Clear();
				prod.getBeginningBates().SendKeys(newBeginningBatesNum);
				
				
				// update and store full bates number with prefix and sufix
				int updatedBegBatesNum = Integer.parseInt(newBeginningBatesNum);
				String batesWithTrailingZeros = String.format("%0"+getTemplateMinNumValue+"d", updatedBegBatesNum);
				String fullBatesNumber = getTemplatePrefixValue + batesWithTrailingZeros + getTemplateSuffixValue;
				
				dataMap.put("updatedBeginningBates", newBeginningBatesNum);
				dataMap.put("fullBatesNumber", fullBatesNumber);
				
				prod.getNumAndSortMarkComplete().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getNumAndSortNext().Enabled()  ;}}), Input.wait30);
				prod.getNumAndSortNext().click();
				driver.waitForPageToBeReady();

			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			throw new ImplementationException("NOT the_default_template_for_numbering_is_displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_branding_is_displayed_on_the_generated_production$")
	public void verify_the_branding_is_displayed_on_the_generated_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5089Verify the branding text with the black and white color style is displayed on the generated production
			throw new ImplementationException("verify_the_branding_is_displayed_on_the_generated_production");
		} else {
			throw new ImplementationException("NOT verify_the_branding_is_displayed_on_the_generated_production");
		}

	}


	@And("^.*(\\[Not\\] )? updating_redaction_style_adding_redaction_text_$")
	public void updating_redaction_style_adding_redaction_text_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand the Tiff sectionChange the redaction style based on the parameterClick Specify Redaction Text by Selecting Redaction Tags:Change the text to "Automated Redaction"
			throw new ImplementationException("updating_redaction_style_adding_redaction_text_");
		} else {
			throw new ImplementationException("NOT updating_redaction_style_adding_redaction_text_");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_redaction_documents_are_redacted_with_the_proper_style$")
	public void verify_the_redaction_documents_are_redacted_with_the_proper_style(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5344 / 5345 / 5346
			//* Verify the production document has the redaction style applied based on the parameter passed into "Updating_refaction_style_add_redaction_text". It will have either white redaction with black redaction text or black redaction with white text.
			//* Also, make sure a thin black border is provided around the white redaction
			//* Verify the redacted text entered in "Updating_refaction_style_add_redaction_text" is added to the documentation
			//
			throw new ImplementationException("verify_the_redaction_documents_are_redacted_with_the_proper_style");
		} else {
			throw new ImplementationException("NOT verify_the_redaction_documents_are_redacted_with_the_proper_style");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_on_the_productions_preview_button$")
	public void clicking_on_the_productions_preview_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click on the Preview Button and wait about 1-2 minutes.

			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPreviewprod().Displayed() ;}}), Input.wait30);
				prod.getPreviewprod().click();
				driver.waitForPageToBeReady();
			} catch (Exception e) {
				
			}
		
		} else {
			throw new ImplementationException("NOT clicking_on_the_productions_preview_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_preview_pdf_displays_the_pdf_branding$")
	public void verify_the_preview_pdf_displays_the_pdf_branding(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5193
			//* Verify the branding on the PDF preview does not overlap or appear over any writing on the actual content.
			//
			try {
				// get fullBatesNumber from dataMap as this will be the pdf file name
				String fullBatesNum = dataMap.get("fullBatesNumber").toString();
				String fileName = fullBatesNum + ".pdf";
				
				PDDocument document = null;
				String home = System.getProperty("user.home");
				
				if(SystemUtils.IS_OS_LINUX){
					while(document == null) document = PDDocument.load(new File(home + "/Downloads/"+fileName));}
				else if(SystemUtils.IS_OS_WINDOWS){
					while(document == null) document = PDDocument.load(new File(home + "\\Download\\"+fileName));
				} else if(SystemUtils.IS_OS_MAC){
					while(document == null) document = PDDocument.load(new File(home + "/Downloads/"+fileName));}
				
				
				// get branding text from dataMap that was entered in the PDF section of Production Components
				String brandingText = dataMap.get("pdfBrandingText").toString();
				
				PDFTextStripper pdfTextStripper = new PDFTextStripper();
				String PDFtext = pdfTextStripper.getText(document);

				// Verify branding text exists in PDF
				try {
					Assert.assertTrue(PDFtext.contains(brandingText));
					pass(dataMap, "Branding text on PDF is correct!");
				} catch (Exception e) {
					fail(dataMap,"Branding text on PDF is not correct!");
				}			
				document.close();
				
			} catch (Exception e) {
				System.out.println(e + " Unable to verify pdf branding on pdf");
				fail(dataMap,"Unable to verify pdf branding on pdf");
			}
		} else {
			throw new ImplementationException("NOT verify_the_preview_pdf_displays_the_pdf_branding");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_creating_a_production_with_a_custom_template_store_the_correct_values$")
	public void verify_creating_a_production_with_a_custom_template_store_the_correct_values(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5202/5800/5801/6182 / 9445 / 4639The final verification is to Verify the rules displayed match the rules stored in "store_the_default_template_values"
			try {
				
				String templatePrivRules = dataMap.get("templatePrivRules").toString();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPrivledgedRules().Displayed()  ;}}), Input.wait30);
				
				String privRule = "";
				for(WebElement rule: prod.getPrivledgedRules().FindWebElements()) {
					privRule = privRule.concat(rule.getText()+",");
				}
				// Remove extra comma at the end
				String privRules = privRule.substring(0, privRule.length() - 1);

				try {
					Assert.assertEquals(templatePrivRules, privRules);
				} catch (Exception e) {
					fail(dataMap, "Priv rules are not correct");
				}
				
			} catch (Exception e) {
				System.out.println(e);
				fail(dataMap, "Unable to verify priv rules");
			}
		} else {
			throw new ImplementationException("NOT verify_creating_a_production_with_a_custom_template_store_the_correct_values");
		}

	}


	@And("^.*(\\[Not\\] )? remove_burn_redaction_on_mp3$")
	public void remove_burn_redaction_on_mp3(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			//Expand Advanced Production Components
			prod.getProductionAdvanced().click();

			//Expand MP3 Files
			prod.getMP3Tab().click();

			//Disable Burn Redactions
			if(prod.getMP3BurnRedactionsCheckboxToggle().Selected()){
				prod.getMP3ComponentRedactionToggle().click();
			}


			pass(dataMap, "Burn redactions removed on MP3");
		} else {
			fail(dataMap, "Burn redactions not removed on MP3");
		}

	}


	@And("^.*(\\[Not\\] )? mark_the_component_section_complete$")
	public void mark_the_component_section_complete(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Mark Complete
			prod.getComponentsMarkComplete().click();
			//Click Next
			prod.getComponentsMarkNext().click();
			pass(dataMap, "Component section marked complete");
		} else {
			fail(dataMap, "Component section not marked complete");
		}

	}


	@And("^.*(\\[Not\\] )? the_production_is_generated_with_the_given_production_component$")
	public void the_production_is_generated_with_the_given_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This will generate the production with the given production component.
			//This is collection of the following steps:
			// marking_complete_the_next_available_bates_number
			marking_complete_the_next_available_bates_number(true, dataMap);
			// complete_default_document_selection
			complete_default_document_selection(true, dataMap);
			// mark_complete_default_priv_guard
			mark_complete_default_priv_guard(true, dataMap);
			// complete_default_production_location_component
			complete_default_production_location_component(true, dataMap);
			// completed_summary_preview_component
			completed_summary_and_preview_component(true, dataMap);
			// starting_the_production_generation
			starting_the_production_generation(true, dataMap);
			// waiting_for_production_to_be_complete
			waiting_for_production_to_be_complete(true, dataMap);
			pass(dataMap, "Production generated with the given production components");
		} else {
			fail(dataMap, "Production not generated with the given production components");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_on_disabling_burn_redactions_on_mp3s_no_redaction_content_is_removed$")
	public void verify_on_disabling_burn_redactions_on_mp3s_no_redaction_content_is_removed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5964Verify MP3 without redaction should be produced successfully without any redaction audioVerify MP3 with redactions should be produced without any redaction audios.
			throw new ImplementationException("verify_on_disabling_burn_redactions_on_mp3s_no_redaction_content_is_removed");
		} else {
			throw new ImplementationException("NOT verify_on_disabling_burn_redactions_on_mp3s_no_redaction_content_is_removed");
		}

	}


	@And("^.*(\\[Not\\] )? enabling_slip_sheets_on_tiff$")
	public void enabling_slip_sheets_on_tiff(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand the TIFF section
			prod.getTIFFTab().click();

			//Enable Slip Sheet
			prod.getTIFFAdvanced().click();
			prod.getTIFFSlipSheetsToggle().click();

			//Click on the Tab based on the parameter
			String tabName = dataMap.get("sheet_tab").toString();
			prod.getTIFFSlipSheetsFieldTabs(tabName);

			//Check off the checkbox based on the parameter.
			//If the parameter contains a period, that means you should split that value by the period and you would have multiple checkboxes to check.Ex: DocID.DocPages. This means we would check off both checkboxes
			List<String> parametersToCheck = Arrays.asList(dataMap.get("sheet_value").toString().split("\\."));
			for(WebElement parameter: prod.getTIFFSlipSheetsContainerLabels().FindWebElements()){
				if(parametersToCheck.contains(parameter.getText())){
					parameter.click();
				}
			}
			//Collapse the Tiff Section
			prod.getTIFFTab().ScrollTo();
			prod.getTIFFTab().click();

			pass(dataMap, "Enabled slip sheets on TIFF");
		} else {
			fail(dataMap, "Can not enable slip sheets on TIFF");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_date_format_on_dateonly_fields_should_not_include_time$")
	public void verify_date_format_on_dateonly_fields_should_not_include_time(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5940Verify DateOnly fields in the producti on date only includes the date portion and it should not cont ain the time portion, irrespecti ve of the format selected.
			throw new ImplementationException("verify_date_format_on_dateonly_fields_should_not_include_time");
		} else {
			throw new ImplementationException("NOT verify_date_format_on_dateonly_fields_should_not_include_time");
		}

	}


	@And("^.*(\\[Not\\] )? adding_multiple_dat_field_mappings$")
	public void adding_multiple_dat_field_mappings(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand DAT
			prod.getDATTab().click();

			//Click Add Field
			prod.getDAT_AddField().click();

			//The parameter data will appear in the following format:Field Classification-Source Field-DAT Field.
			//Each field on the table is going to be separated by hyphens and end with a period. This means you should first split these values by periods first to see how many sets of data you will have then split it by hyphens to see which individual values you need to fill out in the table.
			String[] fieldsToAdd = dataMap.get("dat_mapping_values").toString().split("\\.");
			for (int i = 0; i < fieldsToAdd.length; i++) {
				String[] values = fieldsToAdd[i].split("-");
				prod.getDAT_FieldClassificationOption(i, values[0].toUpperCase().replaceAll("\\s", "")).click();
				prod.getDAT_SourceFieldOption(i, values[1]).click();
				prod.getDAT_DATField(i).getWebElement().clear();
				prod.getDAT_DATField(i).getWebElement().sendKeys(values[2]);
				if (prod.getDAT_FieldMappingRows().size() < fieldsToAdd.length) {
					prod.getDAT_AddField().click();
				}
			}

			pass(dataMap, "Multiple DAT field mappings added");
		} else {
			fail(dataMap, "Multiple DAT field mappings not added");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_ending_bates_generated_in_a_production_should_have_correct_values$")
	public void verify_the_ending_bates_generated_in_a_production_should_have_correct_values(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6117Verify Ending Bates should displayed correct value in DAT
			throw new ImplementationException("verify_the_ending_bates_generated_in_a_production_should_have_correct_values");
		} else {
			throw new ImplementationException("NOT verify_the_ending_bates_generated_in_a_production_should_have_correct_values");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redaction_tags_configured_in_mp3_productions_are_retained_in_templates$")
	public void verify_redaction_tags_configured_in_mp3_productions_are_retained_in_templates(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5927Verify in the MP3 section, Burn redactions is enabled
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getMP3BurnRedactionsCheckboxToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertTrue(prod.getMP3BurnRedactionsCheckboxToggle().Selected());

			// Verify that the redaction "Default Automation Redaction" is checked off by default
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getMP3DefaultAutomationRedactionCheckbox().Displayed();
				}
			}), Input.wait30);
			Assert.assertTrue(prod.getMP3DefaultAutomationRedactionCheckbox().getWebElement().getAttribute("class").contains("jstree-clicked"), "The template should have Default Automation Redaction checked, but the template is not checking the checkbox.");

			// Verify Generate Load File (LST) is enabled by default.
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return
							prod.getMP3GenerateLoadFileCheckboxToggle().Displayed();
				}
			}), Input.wait30);
			Assert.assertTrue(prod.getMP3GenerateLoadFileCheckboxToggle().Selected());

			pass(dataMap,"Redaction tags configured in mp3 productions are retained in templates");
		} else {
			fail(dataMap,"Redaction tags configured in mp3 productions are not retained in templates");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_mp3_files_in_the_generation_has_redacted_audio$")
	public void verify_the_mp3_files_in_the_generation_has_redacted_audio(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5963Background info: the documents in the folder selected should have mp3 files with redactionsVerify MP3 with redaction should be generated successfully. All the redacted segments of the audi o SHOULD be replaced with th e redaction audio
			throw new ImplementationException("verify_the_mp3_files_in_the_generation_has_redacted_audio");
		} else {
			throw new ImplementationException("NOT verify_the_mp3_files_in_the_generation_has_redacted_audio");
		}

	}

	@Given("^.*(\\[Not\\] )? login_to_new_production$")
	public void login_to_new_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps

			dataMap.put("uid", "automate.sqa3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			//sightline_is_launched
			sightline_is_launched(true, dataMap);
			
			login_as_pau(true, dataMap);

			//login_as_pauon_production_home_page
			on_production_home_page(true, dataMap);

			//begin_new_production_process
			begin_new_production_process(true, dataMap);
			pass(dataMap, "Succesfully fnished the login - > to new production process");
		}
		else fail(dataMap, "Can not finish the login to production process");

	}


	@And("^.*(\\[Not\\] )? the_production_generation_is_started_with_the_given_production_component$")
	public void the_production_generation_is_started_with_the_given_production_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This will generate the production with the given production component. This is collection of the following steps: 

			
			//data for our custom 
			dataMap.put("prefix", "G");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Z");
			
			//Need Custom Bates Number or generartion will fail due to duplicate
			custom_number_and_sorting_is_added(true, dataMap);
			//complete_default_numbering_and_sorting(scriptState, dataMap);

			//complete_default_document_selection 
			complete_default_document_selection(true, dataMap);

			//mark_complete_default_priv_guard
			mark_complete_default_priv_guard(true, dataMap);

			//complete_default_production_location_component
			complete_default_production_location_component(true, dataMap);

			//completed_summary_preview_component
			completed_summary_preview_component(true, dataMap);

			//starting_the_production_generation
			starting_the_production_generation(true, dataMap);

			//waiting_for_production_to_be_complete
			waiting_for_production_to_be_complete(true, dataMap);
			
			//Commit the production 
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getConfirmAndCommitProdLink().Displayed()  ;}}), Input.wait30); 
			prod.getConfirmAndCommitProdLink().click();
			
			//Get bates for other contexts
			prod.getBackLink().click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getProd_BatesRange().Displayed()  ;}}), Input.wait30); 
			dataMap.put("prodBatesRange", prod.getProd_BatesRange().getText());
			
			//Loop to confirm production commit
			String status = prod.getGeneratePostGenStatus().getText();
			int i =0;
			while(!status.equalsIgnoreCase("COMPLETED") && i++<10) {
				prod.getBackLink().click();
				driver.waitForPageToBeReady();
				Thread.sleep(10000);
				prod.getNextButton().click();
				driver.waitForPageToBeReady();
				status = prod.getGeneratePostGenStatus().getText();
			}

			pass(dataMap, "Successfully finished the production generation process");
		}
		else fail(dataMap , "Could not finish the production generation process");

	}


	@Then("^.*(\\[Not\\] )? verify_committed_production_found$")
	public void verify_committed_production_found(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5207 Verify the Volume specified in the Production output step should be created where it is specified
			//
			//* The Volume specified in the Production output step should be created where it is specified but not in every folder
			//* Volumn set in Production Location step
			//
			throw new ImplementationException("verify_committed_production_found");
		} else {
			throw new ImplementationException("NOT verify_committed_production_found");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_location_component$")
	public void verify_production_location_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5882 Verify Production Location for Production Root path/Production Directory/Load File Path/Volume Included/Production Component folders
			//Verify the Specify Production Location components are present
			//
			//* Production Root Path
			//* Production Directory
			//* Load File Path
			//* Volume Included
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getlstProductionRootPaths().Displayed()  ;}}), Input.wait30); 
			
			String productionRootPath = prod.getProductionLocComponent(1).getText();
			String productionDirectory = prod.getProductionLocComponent(2).getText();
			String loadFilePath = prod.getProductionLocComponent(3).getText();
			String volumeIncluded = prod.getProductionLocComponent(4).getText();
			
			Assert.assertEquals("Production Root Path:", productionRootPath);
			Assert.assertEquals("Production Directory:", productionDirectory);
			Assert.assertEquals("Load File Path:",loadFilePath);
			Assert.assertEquals("Volume Included:", volumeIncluded);
		} else {
			throw new ImplementationException("NOT verify_production_location_component");
		}

	}


	@And("^.*(\\[Not\\] )? select_mp3_redactions$")
	public void select_mp3_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check MP3 Files checkbox
			//Expand MP3 Files section
			//Enable Burn Redactions
			//Specify Redactions: All redactions in annotation layer: Default Annotation Layer
			//Redaction Style: Beep
			//Expand MP3 Advanced section Enable Generate Load File (LST)
			Actions builder = new Actions(driver.getWebDriver());
			String mp3 =  (String)dataMap.get("mp3");
			
			//Check MP3 Files checkbox and Expand MP3 Files section
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
			builder.moveToElement(prod.getMP3_ToggElement().getWebElement()).perform();
			prod.getMP3_ToggElement().click();
			builder.moveToElement(prod.getMP3ChkBox().getWebElement()).perform();
			prod.getMP3ChkBox().click();
			prod.getMP3Tab().click();
			
			//Enable Burn Redactions && Specify Redactions: All redactions in annotation layer: Default Annotation Layer
			prod.getMP3ComponentRedactionToggle().click();
			prod.getMP3_SelectRed_RedactionByAnnotation().click();
			
			//Redaction Style: Beep
			prod.getMP3_RedactionStyle().click();
			prod.getMP3_RedactionStyle_Beet().click();
			
			//Expand MP3 Advanced section Enable Generate Load File (LST)
			prod.getMP3_SelectAdvToggle().click();
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
			if(!prod.getMP3AdvancedList().Enabled()) prod.getMP3AdvancedList().click();
			
			pass(dataMap,"select_mp3_redactions");
		} else {
			fail(dataMap,"NOT select_mp3_redactions");
		}

	}


	@And("^.*(\\[Not\\] )? click_the_save_as_template_button_for_created_production$")
	public void click_the_save_as_template_button_for_created_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Go to the productions home page.
			//Click on the gear of the created production
			//Select 'Save as Template' 
			//Give the template a name and save the template.
			on_production_home_page(true,dataMap);
			//Select compelete
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getFilterByButton().Enabled()  ;}}), Input.wait30);
			prod.getFilterByButton().Click();
				//Deselect Filters we dont want
			for(int i =1; i<=4; i++) {
				if(prod.getFilter(i).Selected() && i!= 4) prod.getFilter(i).Click();		
				Thread.sleep(1000);
			}
			//Select Complete filter, if it isn't already
			if(!prod.getFilter(2).Selected()) prod.getFilter(4).Click();
			prod.getFilterByButton().Click();
			Thread.sleep(1000);
			driver.FindElementByTagName("body").SendKeys(Keys.PAGE_DOWN.toString());
			
			prod.getprod_ActionButton().click();
			prod.getSaveTemplate().click();			
			String dateTime = new Long((new Date()).getTime()).toString();
			String TempName = "AutoProduction" + dateTime + "Temp";
			prod.getTemplateName().sendKeys(TempName);
			dataMap.put("prod_template", TempName);
			
			prod.getTemplateName().sendKeys("AutomationTemp");
			prod.getTemplateNameSaveButton().click();
			
			pass(dataMap,"click_the_save_as_template_button_for_created_production");
		} else {
			fail(dataMap, "NOT click_the_save_as_template_button_for_created_production");
		}

	}


	@And("^.*(\\[Not\\] )? begin_new_production_process_with_new_template$")
	public void begin_new_production_process_with_new_template(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Create new production
			//Select new template
			//Click Complete button
			//Click Next button
			
			String tempnameString = dataMap.get("Temp_production_name").toString();
			prod.getprod_LoadTemplate().click();
			prod.getprod_LoadTemplateOptions().FindWebElements().contains(tempnameString); 
			
			pass(dataMap,"begin_new_production_process_with_new_template");
		} else {
			fail(dataMap,"NOT begin_new_production_process_with_new_template");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_template_mp3_component_details$")
	public void verify_template_mp3_component_details(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5927 Verify in Productions, production template should retain the redaction tags configured in the production
			//
			//* Verify MP3 Files component section matches the template
			//
			Assert.assertTrue(prod.getMP3ComponentRedactionToggle().Enabled());
			Assert.assertTrue(prod.getMP3_SelectRed_RedactionByAnnotation().Selected());
			Assert.assertTrue(prod.getMP3_SelectAdvToggle().Enabled());
			Assert.assertTrue(prod.getMP3AdvancedList().Enabled());
			pass(dataMap, "verify_template_mp3_component_details");
		} else {
			fail(dataMap,"NOT verify_template_mp3_component_details");
		}

	}

	@And("^.*(\\[Not\\] )? navigate_to_session_search_page$")
	public void navigate_to_session_search_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Search > Session Search in navigation bar
			prod.getProdSearchMenuButton();
			prod.getProdSessionSearchButton();
			driver.waitForPageToBeReady();
			pass(dataMap, "Navigated to session Search, and searched for our production");

		}
		else fail(dataMap, "Could not get to the Session search page");

	}


	@When("^.*(\\[Not\\] )? open_production_in_docview$")
	public void open_production_in_docview(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//Search for DocIDIn Doc List add column 'AllProductionBatesRanges'
			SessionSearch sessionSearch = new SessionSearch(driver);
			String docId = (String)dataMap.get("docID");
			
			//Search for DocID
			sessionSearch.insertFullText(docId);
			sessionSearch.getSearchButton().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				sessionSearch.getSearchTableResults().Displayed()  ;}}), Input.wait30); 

			//Find appropriate + button
    			for(WebElement x: sessionSearch.getSearchResultDocsMetCriteriaPlusButton().FindWebElements()) {
    				if(x.isEnabled() && x.isDisplayed()) x.click();
    			}
    			//Go to DocView
    			sessionSearch.getBulkActionButton().click();
    			sessionSearch.getDocViewAction().click();
    			DocViewPage docView = new DocViewPage(driver);
			driver.waitForPageToBeReady();
			pass(dataMap, "Docview was opened");
		}
		else fail(dataMap, "docView was not opened");

	}


	@Then("^.*(\\[Not\\] )? verify_produced_pdf_in_docview$")
	public void verify_produced_pdf_in_docview(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5276 Verify Produced PDFs are being presented in the DocView for the documentTC6122 Verify Produced PDFs should be available for being presented in DocView for the document

			DocViewPage docView = new DocViewPage(driver,0);
			driver.waitForPageToBeReady();
			boolean foundPDF = false;
			//image tab is where our PDF will be
			docView.getDocView_ImagesTab().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewImagesDropDown().Displayed()  ;}}), Input.wait30); 

			//Loop through until we find our Production
			for(WebElement x: docView.getDocViewTotalImages().FindWebElements()) {
				if(x.getText().contains( (String)dataMap.get("production_name") )){
					x.click();
					foundPDF = true;
				}
			}
			//Make sure we really found it
			Assert.assertTrue(foundPDF);
			pass(dataMap, "PDF only has been verified");
		}
		else fail(dataMap, "Failed PDF only verification");

	}


	@Then("^.*(\\[Not\\] )? verify_produced_pdf_in_docview_with_pdf_only_production$")
	public void verify_produced_pdf_in_docview_with_pdf_only_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5276 Verify Produced PDFs are being presented in the DocView for the document
			DocViewPage docView = new DocViewPage(driver, 0);
			driver.waitForPageToBeReady();
			boolean foundPDF = false;
			//image tab is where our PDF will be
			docView.getDocView_ImagesTab().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewImagesDropDown().Displayed()  ;}}), Input.wait30); 

			//Loop through until we find our Production
			for(WebElement x: docView.getDocViewTotalImages().FindWebElements()) {
				if(x.getText().contains( (String)dataMap.get("production_name") )){
					x.click();
					foundPDF = true;
				}
			}
			//Make sure we really found it
			Assert.assertTrue(foundPDF);
			pass(dataMap, "PDF only has been verified");
		}
		else fail(dataMap, "Could not verify PDF only");


	}


	@And("^.*(\\[Not\\] )? advanced_search_for_production$")
	public void advanced_search_for_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Switch to Advanced'Search by 'Work Product' > 'Productions' > 'Already Produced'
			SessionSearch sessionSearch = new SessionSearch(driver);
			String prodName = (String)dataMap.get("production_name");
			
			//Click Advanced Search
			sessionSearch.getAdvancedSearchLink().click();
			
			//Click Work Product Button
			sessionSearch.getWorkproductBtn().click();
			
			//Click Productions Button
			sessionSearch.getProductionBtn().click();
		}
		else fail(dataMap, "Failed the advanced search");

	}


	@And("^.*(\\[Not\\] )? uncommit_last_production$")
	public void uncommit_last_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			//Use a past context: Selecting the production to help filter into our desired Production
			dataMap.put("status", "COMPLETED");
			dataMap.put("viewMode", "tile");
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
			prod.getProdExport_ProductionSets().SendKeys("DefaultProductionSet");

			selecting_the_production(true, dataMap);

			//Get our Target Production Tile and Click into it
			WebElement targetProd =  prod.getProductionTileByName((String)dataMap.get("production_name"));
			targetProd.click();
			
			//Uncommit and wait for popup
			prod.getProd_Uncommitbutton().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getMP3WarningBox().Displayed()  ;}}), Input.wait30); 

			//Refresh page, and verify commit button is now present rather than uncommit
			driver.getWebDriver().navigate().refresh();
			driver.waitForPageToBeReady();
			Assert.assertTrue(prod.getConfirmProductionCommit().Displayed());
			pass(dataMap, "Production was uncommited successfully");

		}
		else fail(dataMap, "Production was not uncommited");

	}


	@Then("^.*(\\[Not\\] )? verify_uncommitted_production_not_found$")
	public void verify_uncommitted_production_not_found(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8212 Verify that in Advanced Search, search against uncommit Production should not display any results
			//
			//* Uncommitted production not displayed in search results
			//
			throw new ImplementationException("verify_uncommitted_production_not_found");
		} else {
			throw new ImplementationException("NOT verify_uncommitted_production_not_found");
		}

	}


	@Given("^.*(\\[Not\\] )? doc_with_priv_tag_exists$")
	public void doc_with_priv_tag_exists(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Document has Priv Tag classification
			throw new ImplementationException("doc_with_priv_tag_exists");
		} else {
			throw new ImplementationException("NOT doc_with_priv_tag_exists");
		}

	}


	@And("^.*(\\[Not\\] )? login_as_rmu$")
	public void login_as_rmu(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("login_as_rmu");
		} else {
			throw new ImplementationException("NOT login_as_rmu");
		}

	}


	@And("^.*(\\[Not\\] )? remove_priv_tag_from_doc$")
	public void remove_priv_tag_from_doc(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("remove_priv_tag_from_doc");
		} else {
			throw new ImplementationException("NOT remove_priv_tag_from_doc");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_removal_of_priv_tag_produced_for_native$")
	public void verify_removal_of_priv_tag_produced_for_native(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5383 Verify Removal of Priv Tag from a Document should get Produced in Production for Native
			//
			//* Verify docs with removed Priv Tag are generated successfully in the Native folder
			//
			throw new ImplementationException("verify_removal_of_priv_tag_produced_for_native");
		} else {
			throw new ImplementationException("NOT verify_removal_of_priv_tag_produced_for_native");
		}

	}


	@Given("^.*(\\[Not\\] )? multimedia_doc_with_priv_tag_exists$")
	public void multimedia_doc_with_priv_tag_exists(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Multimedia document with Priv Tag exists
			//
			//* Refer to Data Set: \\10.55.79.37\data\AK_Native_PDF_MP3_Transcript_ForProduction
			//* Source Doc ID -> ICE000054-12-00000851 STC4_00001008 STC4_00001009 STC4_00001016
			//
			throw new ImplementationException("multimedia_doc_with_priv_tag_exists");
		} else {
			throw new ImplementationException("NOT multimedia_doc_with_priv_tag_exists");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_multimedia_file_group_production_generation_with_priv_tag$")
	public void verify_multimedia_file_group_production_generation_with_priv_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5843 Verify for Multimedia file group Production Generation with some doc having Priv Tag Classification
			//
			//* NATIVE/TIFF/DAT should generate succssfully
			//* Natives should not be copied for Priv files
			//
			throw new ImplementationException("verify_multimedia_file_group_production_generation_with_priv_tag");
		} else {
			throw new ImplementationException("NOT verify_multimedia_file_group_production_generation_with_priv_tag");
		}

	}


	@Given("^.*(\\[Not\\] )? doc_with_redaction_tag_exists$")
	public void doc_with_redaction_tag_exists(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Document has a Redaction Tag and no Priv Tag
			throw new ImplementationException("doc_with_redaction_tag_exists");
		} else {
			throw new ImplementationException("NOT doc_with_redaction_tag_exists");
		}

	}


	@And("^.*(\\[Not\\] )? remove_redaction_from_doc$")
	public void remove_redaction_from_doc(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Open redacted doc in Doc ViewRemove redaction
			throw new ImplementationException("remove_redaction_from_doc");
		} else {
			throw new ImplementationException("NOT remove_redaction_from_doc");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_removal_of_redaction_tag_produced_for_native$")
	public void verify_removal_of_redaction_tag_produced_for_native(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5382 Verify Removal of Redaction Tag from a documents Should get produced in Production for Native
			//
			//* Verify docs with removed redactions are generated successfully in the Native folder
			//
			//
			throw new ImplementationException("verify_removal_of_redaction_tag_produced_for_native");
		} else {
			throw new ImplementationException("NOT verify_removal_of_redaction_tag_produced_for_native");
		}

	}

	@When("^.*(\\[Not\\] )? open_doc_in_doc_view$")
	public void open_doc_in_doc_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			SessionSearch sessionSearch = new SessionSearch(driver);
			String docId = (String)dataMap.get("docID");
			
			//Search for DocID
			sessionSearch.insertFullText(docId);
			sessionSearch.getSearchButton().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				sessionSearch.getSearchTableResults().Displayed()  ;}}), Input.wait30); 

			//Find appropriate + button
    			for(WebElement x: sessionSearch.getSearchResultDocsMetCriteriaPlusButton().FindWebElements()) {
    				if(x.isEnabled() && x.isDisplayed()) x.click();
    			}
    			//Go to DocView
    			sessionSearch.getBulkActionButton().click();
			sessionSearch.getDocViewAction().click();
			driver.waitForPageToBeReady();
			pass(dataMap, "opened in doc_view");
		}
		else fail(dataMap, "Could not open in doc_view");

	}


	@Then("^.*(\\[Not\\] )? verify_doc_view_images_tab_displayed$")
	public void verify_doc_view_images_tab_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8211 Verify that DocView Images tab should  show the produced TIFF/PDF for this uncommitted production
			DocViewPage docView = new DocViewPage(driver,0);
			driver.waitForPageToBeReady();
			boolean foundPDF = false;
			//image tab is where our PDF will be
			docView.getDocView_ImagesTab().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewImagesDropDown().Displayed()  ;}}), Input.wait30); 

			//Loop through until we find our Production
			for(WebElement x: docView.getDocViewTotalImages().FindWebElements()) {
				if(x.getText().contains( (String)dataMap.get("production_name") )){
					x.click();
					foundPDF = true;
				}
			}
			//Make sure we really found it
			Assert.assertTrue(foundPDF);		
			pass(dataMap, "Found our TIFF/PDF from image tab");
		}
		else fail(dataMap, "Could not verify doc view images tab");

	}


	@And("^.*(\\[Not\\] )? click_uncommit_production_button$")
	public void click_uncommit_production_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Uncommit Production' button on Quality Control & Confirmation tab
			throw new ImplementationException("click_uncommit_production_button");
		} else {
			throw new ImplementationException("NOT click_uncommit_production_button");
		}

	}


	@And("^.*(\\[Not\\] )? nav_back_to_generate_tab$")
	public void nav_back_to_generate_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("nav_back_to_generate_tab");
		} else {
			throw new ImplementationException("NOT nav_back_to_generate_tab");
		}

	}


	@And("^.*(\\[Not\\] )? regenerate_production$")
	public void regenerate_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Regenerate' buttonwaiting_for_production_to_be_complete
			throw new ImplementationException("regenerate_production");
		} else {
			throw new ImplementationException("NOT regenerate_production");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_uncommitted_production_regenerated$")
	public void verify_uncommitted_production_regenerated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8220 Verify that after uncommit if user regenerate the production, it should generate successfully
			throw new ImplementationException("verify_uncommitted_production_regenerated");
		} else {
			throw new ImplementationException("NOT verify_uncommitted_production_regenerated");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_doc_view_images_tab_not_displayed$")
	public void verify_doc_view_images_tab_not_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8211 Verify that DocView Images tab should not show the produced TIFF/PDF for this uncommitted production
			DocViewPage docView = new DocViewPage(driver,0);
			driver.waitForPageToBeReady();
			boolean foundPDF = false;
			//image tab is where our PDF will be
			docView.getDocView_ImagesTab().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				docView.getDocViewImagesDropDown().Displayed()  ;}}), Input.wait30); 

			//Loop through until we find our Production
			for(WebElement x: docView.getDocViewTotalImages().FindWebElements()) {
				if(x.getText().contains( (String)dataMap.get("production_name") )){
					x.click();
					foundPDF = true;
				}
			}
			//Make sure we didnt find it
			Assert.assertFalse(foundPDF);		
			pass(dataMap, "TIFF/PDF from image tab not found");
		}
		else fail(dataMap, "could not verify that images tab was not displayed");

	}


	@And("^.*(\\[Not\\] )? add_allproductionbatesranges_column_to_doc$")
	public void add_allproductionbatesranges_column_to_doc(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Search for DocIDIn Doc List add column 'AllProductionBatesRanges'
			SessionSearch sessionSearch = new SessionSearch(driver);
			String docId = (String)dataMap.get("docID");
			
			//Search for DocID
			sessionSearch.insertFullText(docId);
			sessionSearch.getSearchButton().click();
			
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				sessionSearch.getSearchTableResults().Displayed()  ;}}), Input.wait30); 

			//Find appropriate + button
    			for(WebElement x: sessionSearch.getSearchResultDocsMetCriteriaPlusButton().FindWebElements()) {
    				if(x.isEnabled() && x.isDisplayed()) x.click();
    			}
    			//Go to DocList
    			sessionSearch.getBulkActionButton().click();
    			sessionSearch.getDocListAction().click();
			DocListPage docList = new DocListPage(driver);
			driver.waitForPageToBeReady();

			//Add Column -> AllProductionBatesRange
			docList.getDocListSelectColumnButton().click();
			docList.getDocListMetaDataColumnCheckBoxByName("AllProductionBatesRanges").click();
			docList.getDocListAddToSelectedButton().click();
			docList.getDocListSelectColumnOkButton().click();
			driver.waitForPageToBeReady();
			int tarIndex = 0;
			//Find Index of our row 
			int i = 0;
			for(WebElement x: docList.getDocListColumnHeaders().FindWebElements()){
				if(x.getText().equals("ALLPRODUCTIONBATESRANGES")) tarIndex = i;
				i++;
			}
			String batesRange = docList.getDocListColumnDataByIndex(docList.getDocListRows().FindWebElements().get(0), tarIndex);
			dataMap.put("bates1", batesRange);
			System.out.println(batesRange);

			pass(dataMap,  "Successfully added column AllProductionBatesRange");
		}
		else fail(dataMap,  "Could not add column AllProductionBatesRange");

	}


	@When("^.*(\\[Not\\] )? open_production_in_doc_list$")
	public void open_production_in_doc_list(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			SessionSearch search = new SessionSearch(driver);
			DocListPage docList = new DocListPage(driver);
			driver.waitForPageToBeReady();
			int tarIndex = 0;
			
			//Find Index of our row 
			int i = 0;
			for(WebElement x: docList.getDocListColumnHeaders().FindWebElements()){
				if(x.getText().equals("ALLPRODUCTIONBATESRANGES")) tarIndex = i;
				i++;
			}
			//Grab our document's allBatesrange and save in map for verification later
			String batesRange = docList.getDocListColumnDataByIndex(docList.getDocListRows().FindWebElements().get(0), tarIndex);
			dataMap.put("batesRange", batesRange);
			pass(dataMap, "Opened successfully in docList");
			
		}
		else pass(dataMap, "Couldnt open in docList");

	}


	@Then("^.*(\\[Not\\] )? verify_allproductionbatesranges_not_displayed_on_uncommitted_production$")
	public void verify_allproductionbatesranges_not_displayed_on_uncommitted_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {

			//TC8210 Verify that 'AllProductionBatesRanges' should not show for uncommitted production
			//Make sure batesRange is empty string
			String batesRange = (String)dataMap.get("batesRange");
			String prodBatesRange =  (String)dataMap.get("prodBatesRange");
			//Make sure AllBatesRange does not have the batesRange we uncommited
			Assert.assertFalse(batesRange.contains(prodBatesRange));
			pass(dataMap, "AllProductionBatesRange is not showing uncommited bates");

		}
		else fail(dataMap, "AllProductionBatesRange is showing");

	}

	@When("^.*(\\[Not\\] )? click_generate_button$")
	public void click_generate_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("click_generate_button");
		} else {
			throw new ImplementationException("NOT click_generate_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_state_of_generate_tab_after_generate_button_clicked$")
	public void verify_state_of_generate_tab_after_generate_button_clicked(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC7517 Verify that after clicking on Generate button from Production-Generate tab, the Generate button is disabledTC7521 Verify that after clicking on Generate button from Production-Generate tab, status should be changed to In ProgressTC7524 Verify that after clicking on Generate button from Production-Generate tab, user cannot click on the Generate button again
			//
			//* Generate button is disabled
			//* Status is 'In Progress'
			//* Generate button is not clickable
			//
			throw new ImplementationException("verify_state_of_generate_tab_after_generate_button_clicked");
		} else {
			throw new ImplementationException("NOT verify_state_of_generate_tab_after_generate_button_clicked");
		}

	}


	@Given("^.*(\\[Not\\] )? redacted_doc_folder_released_to_different_security_group$")
	public void redacted_doc_folder_released_to_different_security_group(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Folder with some redacted documents in Default Security GroupRelease folder to another security group (ex. SG1)Do not map Annotation Layer/Redaction Tag to SG1
			throw new ImplementationException("redacted_doc_folder_released_to_different_security_group");
		} else {
			throw new ImplementationException("NOT redacted_doc_folder_released_to_different_security_group");
		}

	}


	@And("^.*(\\[Not\\] )? select_redaction_tag_from_doc_folder$")
	public void select_redaction_tag_from_doc_folder(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand TIFF sectionSelect the redaction tag from the released folder in the current security group
			throw new ImplementationException("select_redaction_tag_from_doc_folder");
		} else {
			throw new ImplementationException("NOT select_redaction_tag_from_doc_folder");
		}

	}


	@And("^.*(\\[Not\\] )? complete_document_selection_for_folder_released_to_security_group$")
	public void complete_document_selection_for_folder_released_to_security_group(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select folder released to the current security groupMark CompleteClick Next button
			throw new ImplementationException("complete_document_selection_for_folder_released_to_security_group");
		} else {
			throw new ImplementationException("NOT complete_document_selection_for_folder_released_to_security_group");
		}

	}


	@And("^.*(\\[Not\\] )? the_production_is_generated_from_priv_guard$")
	public void the_production_is_generated_from_priv_guard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This will generate the production with the given production component. This is collection of the following steps:mark_complete_default_priv_guardcomplete_default_production_location_componentcompleted_summary_preview_componentstarting_the_production_generationwaiting_for_production_to_be_complete
			throw new ImplementationException("the_production_is_generated_from_priv_guard");
		} else {
			throw new ImplementationException("NOT the_production_is_generated_from_priv_guard");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_generated_in_different_security_group$")
	public void verify_production_generated_in_different_security_group(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5066 Verify Production Generation in Different Security Group
			//
			//* Verify production successfully generated
			//
			throw new ImplementationException("verify_production_generated_in_different_security_group");
		} else {
			throw new ImplementationException("NOT verify_production_generated_in_different_security_group");
		}

	}


	@And("^.*(\\[Not\\] )? select_tiff_slipsheets_workproduct$")
	public void select_tiff_slipsheets_workproduct(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand TIFF sectionExpand Advanced sectionEnable Slip SheetsClick 'WORKPRODUCT' field tabSelect 'Default Automation Folder' workproduct folderClick 'Add to Selected' button
			throw new ImplementationException("select_tiff_slipsheets_workproduct");
		} else {
			throw new ImplementationException("NOT select_tiff_slipsheets_workproduct");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_generated_with_slipsheets_workproduct$")
	public void verify_production_generated_with_slipsheets_workproduct(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5257 Verify in Productions, the workproduct fields for slip sheets
			throw new ImplementationException("verify_production_generated_with_slipsheets_workproduct");
		} else {
			throw new ImplementationException("NOT verify_production_generated_with_slipsheets_workproduct");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_opt_log_file_generated$")
	public void verify_opt_log_file_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5164 Verify the OPT/LOG file generated in a production have all required information
			//
			//* Format should be like: OR_00000001,OR0001,Z:\Images\OR0001\0001\OR_00000001.tif,Y,,, OR_00000002,OR0001,Z:\Images\OR0001\0001\OR_00000002.tif,Y,,, OR_00000003,OR0001,Z:\Images\OR0001\0001\OR_00000003.tif,,,,
			//
			throw new ImplementationException("verify_opt_log_file_generated");
		} else {
			throw new ImplementationException("NOT verify_opt_log_file_generated");
		}

	}


	@Given("^.*(\\[Not\\] )? text_files_ingested_with_redactions$")
	public void text_files_ingested_with_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Text files ingested with redactions
			throw new ImplementationException("text_files_ingested_with_redactions");
		} else {
			throw new ImplementationException("NOT text_files_ingested_with_redactions");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_ocring_of_text_component_for_redacted_docs$")
	public void verify_ocring_of_text_component_for_redacted_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5186 Verify OCRing of Text component of the production, only for Redacted Documents
			throw new ImplementationException("verify_ocring_of_text_component_for_redacted_docs");
		} else {
			throw new ImplementationException("NOT verify_ocring_of_text_component_for_redacted_docs");
		}

	}


	@And("^.*(\\[Not\\] )? select_different_dat_date_format$")
	public void select_different_dat_date_format(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand DAT sectionSelect Date Format: MMDDYYYY
			throw new ImplementationException("select_different_dat_date_format");
		} else {
			throw new ImplementationException("NOT select_different_dat_date_format");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_generated_production_date_format$")
	public void verify_generated_production_date_format(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5200 Verify the format of the date produced in the Production DAT should honor the date format configured in DAT section
			//
			//* Selected date format should be displayed in DAT file
			//
			throw new ImplementationException("verify_generated_production_date_format");
		} else {
			throw new ImplementationException("NOT verify_generated_production_date_format");
		}

	}


	@Given("^.*(\\[Not\\] )? native_docs_associated_to_tags$")
	public void native_docs_associated_to_tags(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//prerequisite step
			throw new ImplementationException("native_docs_associated_to_tags");
		} else {
			throw new ImplementationException("NOT native_docs_associated_to_tags");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_native_docs_associated_to_tags_are_copied$")
	public void verify_native_docs_associated_to_tags_are_copied(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5335 Verify Natives of the docs of the selected file types or selected tags are produced unless they are to excluded to Redaction or Priv Tags
			throw new ImplementationException("verify_native_docs_associated_to_tags_are_copied");
		} else {
			throw new ImplementationException("NOT verify_native_docs_associated_to_tags_are_copied");
		}

	}


	@Given("^.*(\\[Not\\] )? docs_associated_with_priv_tag$")
	public void docs_associated_with_priv_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//A few documents are associated to Privilege Tag
			throw new ImplementationException("docs_associated_with_priv_tag");
		} else {
			throw new ImplementationException("NOT docs_associated_with_priv_tag");
		}

	}


	@And("^.*(\\[Not\\] )? select_dat_priv_tag$")
	public void select_dat_priv_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand DATClick 'PRIVILEGED' checkbox for BatesNumber field
			throw new ImplementationException("select_dat_priv_tag");
		} else {
			throw new ImplementationException("NOT select_dat_priv_tag");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_dat_priv_flag_for_generated_production$")
	public void verify_dat_priv_flag_for_generated_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5068 Verify PRIV flag configured in the DAT section of Production is to be honored for all docs in the generated production
			//
			//* Production generated
			//* In DAT section, value should be blank if field is set as Priv
			//
			throw new ImplementationException("verify_dat_priv_flag_for_generated_production");
		} else {
			throw new ImplementationException("NOT verify_dat_priv_flag_for_generated_production");
		}

	}


	@And("^.*(\\[Not\\] )? add_tiffpagecount_field$")
	public void add_tiffpagecount_field(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand DAT sectionClick 'Add Field' buttonSelect field mapping: Production > TIFFPageCount
			throw new ImplementationException("add_tiffpagecount_field");
		} else {
			throw new ImplementationException("NOT add_tiffpagecount_field");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_tiffpagecount_on_generated_production$")
	public void verify_tiffpagecount_on_generated_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5938 Verify in Productions DAT, provide the TIFFPageCount for each document produced
			//
			//* DAT should contain the TIFFPageCount column with correct values
			//
			throw new ImplementationException("verify_tiffpagecount_on_generated_production");
		} else {
			throw new ImplementationException("NOT verify_tiffpagecount_on_generated_production");
		}

	}


	@And("^.*(\\[Not\\] )? add_dat_filename_field$")
	public void add_dat_filename_field(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand DAT sectionClick 'Add Field' buttonSelect field: Doc Basis > DocFileName
			throw new ImplementationException("add_dat_filename_field");
		} else {
			throw new ImplementationException("NOT add_dat_filename_field");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_generated_production_dat_row_for_each_doc$")
	public void verify_generated_production_dat_row_for_each_doc(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5204 Verify in Generated Production DAT will always have one row for each document
			//
			//* DAT will always have one row for each document
			//
			throw new ImplementationException("verify_generated_production_dat_row_for_each_doc");
		} else {
			throw new ImplementationException("NOT verify_generated_production_dat_row_for_each_doc");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_count_of_docs_produced_in_different_security_group$")
	public void verify_count_of_docs_produced_in_different_security_group(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5167 Verify correct count of Native documents produce in (Production in Different Security Group)
			//
			//* Production generation should not consider the "Is Privilege" count as Privilege Tag is not released to security group
			//
			//
			throw new ImplementationException("verify_count_of_docs_produced_in_different_security_group");
		} else {
			throw new ImplementationException("NOT verify_count_of_docs_produced_in_different_security_group");
		}

	}


	@And("^.*(\\[Not\\] )? ingest_landscape_and_portrait_layout_docs$")
	public void ingest_landscape_and_portrait_layout_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Prerequisite for TC5354
			//
			//* Ingest -> \\172.22.155.16\Storage\IngestionTestData\Automation\Zipp_DocView_20Family_20Threaded\DAT4_STC 1_newdtformatBlankNameAddress_Correct.dat
			//* Source Doc It -> 51ID00000169
			//
			throw new ImplementationException("ingest_landscape_and_portrait_layout_docs");
		} else {
			throw new ImplementationException("NOT ingest_landscape_and_portrait_layout_docs");
		}

	}


	@And("^.*(\\[Not\\] )? select_tiff_rotate_90_clockwise$")
	public void select_tiff_rotate_90_clockwise(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand TIFF sectionSelect Rotate Landscapepages to portrait layout: 'Rotate 90 degrees clock-wise'
			throw new ImplementationException("select_tiff_rotate_90_clockwise");
		} else {
			throw new ImplementationException("NOT select_tiff_rotate_90_clockwise");
		}

	}


	@And("^.*(\\[Not\\] )? complete_landscape_and_portrait_documents_doc_selection$")
	public void complete_landscape_and_portrait_documents_doc_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Ingest -> \\172.22.155.16\Storage\IngestionTestData\Automation\Zipp_DocView_20Family_20Threaded\DAT4_STC 1_newdtformatBlankNameAddress_Correct.dat
			//* Source Doc It -> 51ID00000169
			//
			//Mark CompleteClick Next
			throw new ImplementationException("complete_landscape_and_portrait_documents_doc_selection");
		} else {
			throw new ImplementationException("NOT complete_landscape_and_portrait_documents_doc_selection");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_generated_production_docs_rotation_applied$")
	public void verify_generated_production_docs_rotation_applied(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5354 Verify if a document has both landscape and portrait pages, the rotation selected is applied only to the pages that are in landscape layout
			//
			//* Rotation should be applied only to pages that are in landscape
			//
			throw new ImplementationException("verify_generated_production_docs_rotation_applied");
		} else {
			throw new ImplementationException("NOT verify_generated_production_docs_rotation_applied");
		}

	}


	@And("^.*(\\[Not\\] )? select_tiff_branding_bates_number$")
	public void select_tiff_branding_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand TIFF sectionClick branding 'Insert Metadata Field' buttonSelect 'BatesNumber' metadata field
			throw new ImplementationException("select_tiff_branding_bates_number");
		} else {
			throw new ImplementationException("NOT select_tiff_branding_bates_number");
		}

	}


	@And("^.*(\\[Not\\] )? marking_complete_the_next_available_bates_number_with_document_level_numbering$")
	public void marking_complete_the_next_available_bates_number_with_document_level_numbering(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Click the document radio button on the Numbering section
			//* Click the link named "Click here" under the format section
			//* Store the third bates number listed as three values.
			//* The first should be the letter the number starts with. That should be stored as the "Prefix".
			//* The last letter is the suffix.
			//* The last digits that are not 0 are the beginning bates number.
			//* Example: A00523Q.
			//* A is the Prefix
			//* 523 is the Beginning Bates
			//* Q is the suffix.
			//* Click Select on the third bates number listed.
			//* Click Mark Complete
			//* Click Next
			//
			throw new ImplementationException("marking_complete_the_next_available_bates_number_with_document_level_numbering");
		} else {
			throw new ImplementationException("NOT marking_complete_the_next_available_bates_number_with_document_level_numbering");
		}

	}


	@And("^.*(\\[Not\\] )? the_production_is_generated_from_document_selection$")
	public void the_production_is_generated_from_document_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("the_production_is_generated_from_document_selection");
		} else {
			throw new ImplementationException("NOT the_production_is_generated_from_document_selection");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_bates_number_in_branding_displayed$")
	public void verify_bates_number_in_branding_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5262 Verify Branding Bates number
			throw new ImplementationException("verify_bates_number_in_branding_displayed");
		} else {
			throw new ImplementationException("NOT verify_bates_number_in_branding_displayed");
		}

	}


	@Given("^.*(\\[Not\\] )? pdf_ppt_files_with_redactions_exist$")
	public void pdf_ppt_files_with_redactions_exist(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//On PDF apply 'Page Redaction' on 1st page with 'Redaction Tag 1' and apply another 'Page Redaction' on the 2nd page with 'Redaction Tag 2'On PPT apply Rectangle Redaction with 'Redaction Tag 1' and another with 'Redaction Tag 2'
			throw new ImplementationException("pdf_ppt_files_with_redactions_exist");
		} else {
			throw new ImplementationException("NOT pdf_ppt_files_with_redactions_exist");
		}

	}


	@And("^.*(\\[Not\\] )? select_redaction_tags_with_placeholders$")
	public void select_redaction_tags_with_placeholders(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand PDF sectionClick Placeholders 'Select Tags' buttonSelect 'Redaction Tag 1' and 'Redaction Tag 2'Enter placeholder text
			throw new ImplementationException("select_redaction_tags_with_placeholders");
		} else {
			throw new ImplementationException("NOT select_redaction_tags_with_placeholders");
		}

	}


	@And("^.*(\\[Not\\] )? complete_redacted_pdf_ppt_document_selection$")
	public void complete_redacted_pdf_ppt_document_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select redacted PDF and PPT filesClick Mark Complete buttonClick Next button
			throw new ImplementationException("complete_redacted_pdf_ppt_document_selection");
		} else {
			throw new ImplementationException("NOT complete_redacted_pdf_ppt_document_selection");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_text_generated_is_in_sync_with_pdf_generated$")
	public void verify_text_generated_is_in_sync_with_pdf_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5277 Verify the Content in Text Generated in Production is in Sync with PDF Generated
			throw new ImplementationException("verify_text_generated_is_in_sync_with_pdf_generated");
		} else {
			throw new ImplementationException("NOT verify_text_generated_is_in_sync_with_pdf_generated");
		}

	}


	@Given("^.*(\\[Not\\] )? upload_ice_dataset_without_file_extension$")
	public void upload_ice_dataset_without_file_extension(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Upload the ICE dataset from the following path:
			//
			//* \\172.22.155.16\Storage\IngestionTestData\Ops Testing01\Native\00
			//* Doc File Extension is blank
			//
			throw new ImplementationException("upload_ice_dataset_without_file_extension");
		} else {
			throw new ImplementationException("NOT upload_ice_dataset_without_file_extension");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_native_file_name_is_corrected_file_extension$")
	public void verify_native_file_name_is_corrected_file_extension(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9578 Verify that in Production, file extension corrected should be used in the file name as Native, when file extension as non-blank value and also sends file extension corrected as non-blank value
			throw new ImplementationException("verify_native_file_name_is_corrected_file_extension");
		} else {
			throw new ImplementationException("NOT verify_native_file_name_is_corrected_file_extension");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_dat_file_generated_with_dat_components$")
	public void verify_dat_file_generated_with_dat_components(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6120 Verify in Productions, the produced DAT should have DAT field name configured in DAT component
			throw new ImplementationException("verify_dat_file_generated_with_dat_components");
		} else {
			throw new ImplementationException("NOT verify_dat_file_generated_with_dat_components");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_keep_docs_with_no_master_date_sorting$")
	public void verify_keep_docs_with_no_master_date_sorting(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5179 Verify Keep Docs w/ No Master Date on Numbering and Sorting Page
			//
			//* Verify generated production matches 'Keep Docs w/ No Master Date' on Numbering & Sorting tab
			//
			throw new ImplementationException("verify_keep_docs_with_no_master_date_sorting");
		} else {
			throw new ImplementationException("NOT verify_keep_docs_with_no_master_date_sorting");
		}

	}


	@Given("^.*(\\[Not\\] )? in_project_with_ice_data$")
	public void in_project_with_ice_data(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Project contains ICE data
			throw new ImplementationException("in_project_with_ice_data");
		} else {
			throw new ImplementationException("NOT in_project_with_ice_data");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_generated_with_ice_data$")
	public void verify_production_generated_with_ice_data(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9130 Verify that production should generate successfully with ICE data
			throw new ImplementationException("verify_production_generated_with_ice_data");
		} else {
			throw new ImplementationException("NOT verify_production_generated_with_ice_data");
		}

	}


	@And("^.*(\\[Not\\] )? select_custodian_masterdate_sorting$")
	public void select_custodian_masterdate_sorting(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select 'Sort by Metadata' radio button'AllCustodians' > 'Descending'Sub-sort By: 'MasterDate' > 'Descending'
			throw new ImplementationException("select_custodian_masterdate_sorting");
		} else {
			throw new ImplementationException("NOT select_custodian_masterdate_sorting");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_sorting_by_metadata_first_second_sorting$")
	public void verify_sorting_by_metadata_first_second_sorting(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5201 Verify Bates Number in generated production DAT and Load Files should be in Ascending Order
			//
			//* Verify generated production sorted by 2 options in Sort by Metadata field
			//
			throw new ImplementationException("verify_sorting_by_metadata_first_second_sorting");
		} else {
			throw new ImplementationException("NOT verify_sorting_by_metadata_first_second_sorting");
		}

	}


	@Given("^.*(\\[Not\\] )? redacted_image_doc_exists$")
	public void redacted_image_doc_exists(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Image based document should be redacted
			//
			//* \\INBCTASLSTR01\Storage\IngestionTestData\Automation\39718
			//
			throw new ImplementationException("redacted_image_doc_exists");
		} else {
			throw new ImplementationException("NOT redacted_image_doc_exists");
		}

	}


	@When("^.*(\\[Not\\] )? copy_paste_redacted_image$")
	public void copy_paste_redacted_image(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("copy_paste_redacted_image");
		} else {
			throw new ImplementationException("NOT copy_paste_redacted_image");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redacted_image_copied_with_redactions$")
	public void verify_redacted_image_copied_with_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC10586 Verify that if produced document is copied to any other file then redacted image should not be displayed
			throw new ImplementationException("verify_redacted_image_copied_with_redactions");
		} else {
			throw new ImplementationException("NOT verify_redacted_image_copied_with_redactions");
		}

	}


	@Given("^.*(\\[Not\\] )? login_to_failed_production$")
	public void login_to_failed_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:sightline_is_launchedlogin_as_pauon_production_home_pageClick on a Failed production
			throw new ImplementationException("login_to_failed_production");
		} else {
			throw new ImplementationException("NOT login_to_failed_production");
		}

	}


	@When("^.*(\\[Not\\] )? click_on_failed_production_error$")
	public void click_on_failed_production_error(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("click_on_failed_production_error");
		} else {
			throw new ImplementationException("NOT click_on_failed_production_error");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_generation_failed_information$")
	public void verify_production_generation_failed_information(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5001 Verify the failed status (Production Generation Failed) should be clickable and should provide the detailed information on why the generate failed
			//
			//* Error should be displayed
			//
			throw new ImplementationException("verify_production_generation_failed_information");
		} else {
			throw new ImplementationException("NOT verify_production_generation_failed_information");
		}

	}


	@Given("^.*(\\[Not\\] )? mp3_files_with_redactions_exist$")
	public void mp3_files_with_redactions_exist(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("mp3_files_with_redactions_exist");
		} else {
			throw new ImplementationException("NOT mp3_files_with_redactions_exist");
		}

	}


	@And("^.*(\\[Not\\] )? complete_mp3_document_selection$")
	public void complete_mp3_document_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select folder containing MP3 filesClick Mark Complete buttonClick Next button
			throw new ImplementationException("complete_mp3_document_selection");
		} else {
			throw new ImplementationException("NOT complete_mp3_document_selection");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_generation_with_redacted_mp3_files$")
	public void verify_production_generation_with_redacted_mp3_files(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6184 Verify Production Generation for MP3 files Audio (redaction Applied)
			throw new ImplementationException("verify_production_generation_with_redacted_mp3_files");
		} else {
			throw new ImplementationException("NOT verify_production_generation_with_redacted_mp3_files");
		}

	}


	@Given("^.*(\\[Not\\] )? no_productions_exist_in_project$")
	public void no_productions_exist_in_project(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("no_productions_exist_in_project");
		} else {
			throw new ImplementationException("NOT no_productions_exist_in_project");
		}

	}


	@When("^.*(\\[Not\\] )? click_here_to_view_and_select_the_next_bates_number$")
	public void click_here_to_view_and_select_the_next_bates_number(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("click_here_to_view_and_select_the_next_bates_number");
		} else {
			throw new ImplementationException("NOT click_here_to_view_and_select_the_next_bates_number");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_empty_next_bates_number_popup_message$")
	public void verify_empty_next_bates_number_popup_message(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8389 Verify that if there is not any committed production then message should be displayed on Next Bates Number pop up
			//
			//* Message should display "There are no previously committed productions to present the next available bates number"
			//
			throw new ImplementationException("verify_empty_next_bates_number_popup_message");
		} else {
			throw new ImplementationException("NOT verify_empty_next_bates_number_popup_message");
		}

	}


	@Given("^.*(\\[Not\\] )? docs_with_redactions_highlights_reviews$")
	public void docs_with_redactions_highlights_reviews(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//A few docs have a few redactions, highlights, and reviwer remarks
			throw new ImplementationException("docs_with_redactions_highlights_reviews");
		} else {
			throw new ImplementationException("NOT docs_with_redactions_highlights_reviews");
		}

	}


	@And("^.*(\\[Not\\] )? select_annotation_layer_tiff_pdf$")
	public void select_annotation_layer_tiff_pdf(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand TIFF sectionSelect Specify Redactions: All redactions in annotation layer: Default Annotation LayerExpand PDF sectionSelect Specify Redactions: All redactions in annotation layer: Default Annotation Layer
			throw new ImplementationException("select_annotation_layer_tiff_pdf");
		} else {
			throw new ImplementationException("NOT select_annotation_layer_tiff_pdf");
		}

	}


	@And("^.*(\\[Not\\] )? complete_complex_production_component_without_changing_redactions$")
	public void complete_complex_production_component_without_changing_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Production Components page is already displayedThese parameters will be coming from using this method as an outcome to outcome or as a standalone. Basically, if any of the fields are true, do the steps needed to fulfill that component, else, skip it.IF DATE IS TRUE:Click the DAT checkboxClick the DAT tab to open the DAT containerAdd field classification: BatesAdd source field: BatesNumberEnter DAT field: Bates NumberIF NATIVE IS TRUE:Check off NativeClick Native to expand itClick SELECT ALLExpand the "Advanced" option and enable "Generate Load File (LST)IF TIFF IS TRUECheck off TIFFClick TIFF to expand itClick Select Tags in the "Placeholders" section.Click the "Privileged" folderClick SelectType in "Automated Placeholder" in "Enter placeholder text for the privileged docs".IF PDF IS TRUECheck off PDFClick PDFto expand itClick Select Tags in the "Placeholders" section.Click the "Privileged" folderClick SelectType in "Automated Placeholder" in "Enter placeholder text for the privileged docs".IF MP3 IS TRUEExpand Advanced Production ComponentsClick the MP3 Files CheckboxEnable Burn RedactionsClick "Select Redactions"Click "Default Automation Redaction"Set the "Redaction Style" to "Beep"IF text IS TRUECheckoff the "Text" component checkboxThe other parameters can be worked on as we use them.At the end of the block above, the last two steps should do the following:Click the Mark complete button and verify the following message appears: "Mark Complete successful"Click the next button
			throw new ImplementationException("complete_complex_production_component_without_changing_redactions");
		} else {
			throw new ImplementationException("NOT complete_complex_production_component_without_changing_redactions");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_only_redactions_in_annotation_layer$")
	public void verify_only_redactions_in_annotation_layer(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6392 Verify that when user select annotation layer, it should burn all redactions only in the annotation layer
			//
			//* Generated document should be displayed only burned redactions and not Highlighting or Reviewer remarks.
			//* Destination location should display only 'SeedFiles' folder
			//* SELECT * FROM EQADoP4E65_P39.[dbo].ProductionDocumentFilePaths
			//
			throw new ImplementationException("verify_only_redactions_in_annotation_layer");
		} else {
			throw new ImplementationException("NOT verify_only_redactions_in_annotation_layer");
		}

	}


	@And("^.*(\\[Not\\] )? select_annotation_layer_tiff$")
	public void select_annotation_layer_tiff(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand TIFF sectionSelect Specify Redactions: All redactions in annotation layer: Default Annotation Layer
			throw new ImplementationException("select_annotation_layer_tiff");
		} else {
			throw new ImplementationException("NOT select_annotation_layer_tiff");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_generated_with_redaction_text_annotation_layer$")
	public void verify_production_generated_with_redaction_text_annotation_layer(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11373 Verify that Production should generated with redaction text if user selects the annotation layer
			throw new ImplementationException("verify_production_generated_with_redaction_text_annotation_layer");
		} else {
			throw new ImplementationException("NOT verify_production_generated_with_redaction_text_annotation_layer");
		}

	}

	@When("^.*(\\[Not\\] )? select_docs_without_family_docs$")
	public void select_docs_without_family_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			Actions builder = new Actions(driver.getWebDriver());
			
			//Go back to Doc Selection Page
			driver.waitForPageToBeReady();
			prod.getBackLink().click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getTotalDocumentsCount().Displayed()  ;}}), Input.wait30); 
			String originalDocs = prod.getTotalDocumentsCount().getText();

		
			//Make Incomplete
			prod.getMarkIncompleteButton().click();
			
			//Select Tag that gives us family docs -> Default Child Tag
			prod.getTagsRadioButton().click();
			WebElement x = driver.FindElementsByCssSelector("#tagTree a").FindWebElements().get(2);
			builder.moveToElement(x).perform();
			prod.getProductionDocumentSelectTagByName("Default Child Tag").click();
			
			//Toggle off Include Family Docs and Mark complete
			prod.getIncludeFamilyToggle().click();
			prod.getMarkCompleteButton().click();

			//wait for document number to update
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				!prod.getTotalDocumentsCount().getText().equals(originalDocs)  ;}}), Input.wait30); 
			//Get the number of docs we will need to verify in doclist
			int numOfDocsToVerify = Integer.parseInt(prod.getTotalDocumentsCount().getText());
			dataMap.put("numOfDocs", numOfDocsToVerify);
			
			//go to doclist
			prod.getTotalDocumentsCount().click();
			driver.waitForPageToBeReady();
			
		} else {
			throw new ImplementationException("NOT select_docs_without_family_docs");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_doclist_without_family_docs$")
	public void verify_doclist_without_family_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC7858 Production Document Selection to DocListTC7860 Production Document Selection to DocList without Child Documents
			//
			//* User should redirect to DocList with selected documents
			//* User should be able to see all documents without family documents
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getDocListTableEntry().Enabled() && prod.getDocListTableEntry().Displayed()  ;}}), Input.wait30);
			int expectedDocuments = (int)dataMap.get("numOfDocs");

			//Maximize amount of documents shown in table
			prod.getDocListDropDownCount().click();
			prod.getDocListDropDownCountMax().click();
			driver.waitForPageToBeReady();

			//Get number of rows and make sure its equal to the previous recorded value
			int numberOfDocumentsInTable = prod.getDocListTableEntry().getWebElement().findElements(By.tagName("tr")).size();
			Assert.assertEquals(numberOfDocumentsInTable, expectedDocuments);
			pass(dataMap, "TC7858");
			
		} else {
			throw new ImplementationException("NOT verify_doclist_without_family_docs");
		}

	}


	@And("^.*(\\[Not\\] )? on_the_doclist_from_the_document_section$")
	public void on_the_doclist_from_the_document_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click back to get back to the document sectionClick on the document count (Should be 20)Verify once you are on the doclist, that the bottom displays: Showing 1 to 10 of 20 entries
			driver.waitForPageToBeReady();
			prod.getBackLink().click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getTotalDocumentsCount().Displayed()  ;}}), Input.wait30); 
			prod.getTotalDocumentsCount().click();
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getDocListEntryAmountText().Displayed()  ;}}), Input.wait30); 
			Assert.assertTrue(prod.getDocListEntryAmountText().getText().equals("Showing 1 to 10 of 20 entries"));
			pass(dataMap, "got to document selection");
		}
		else fail(dataMap, "failed to navigate to doclist from document selection");

	}


	@When("^.*(\\[Not\\] )? clicking_the_back_to_source_button$")
	public void clicking_the_back_to_source_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//On the top left of DocList, click the Back to Source button
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getDocListBackToSourceButton().Displayed()  ;}}), Input.wait30); 
			prod.getDocListBackToSourceButton().click();
			pass(dataMap, "Succesfully clicked back to source");
		}
		else fail(dataMap, "failed to click back to source");

	}


	@Then("^.*(\\[Not\\] )? verify_the_doclist_navigation_returns_the_user_to_the_production$")
	public void verify_the_doclist_navigation_returns_the_user_to_the_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 8343Verify the user is navigated back to the Production Location section of the production. 
			driver.waitForPageToBeReady();
			Assert.assertTrue(driver.getUrl().contains("Production"));
			pass(dataMap, "verified back to production location page");
		}
		else fail(dataMap, "could not verify back to production location page");

	}


	@And("^.*(\\[Not\\] )? complete_document_tag_selection_with_family$")
	public void complete_document_tag_selection_with_family(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			Actions builder = new Actions(driver.getWebDriver());
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				prod.getTotalDocumentsCount().Displayed()  ;}}), Input.wait30); 
			String originalDocs = prod.getTotalDocumentsCount().getText();

		
			
			//Select Tag that gives us family docs -> Default Child Tag
			prod.getTagsRadioButton().click();
			WebElement x = driver.FindElementsByCssSelector("#tagTree a").FindWebElements().get(2);
			builder.moveToElement(x).perform();
			prod.getProductionDocumentSelectTagByName("Default Child Tag").click();
			
			prod.getMarkCompleteButton().click();

			//wait for document number to update
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				!prod.getTotalDocumentsCount().getText().equals(originalDocs)  ;}}), Input.wait30); 
			//Get the number of docs we will need to verify in doclist
			int numOfDocsToVerify = Integer.parseInt(prod.getTotalDocumentsCount().getText());
			dataMap.put("numOfDocs", numOfDocsToVerify);
			
			//go to doclist
			prod.getTotalDocumentsCount().click();
			driver.waitForPageToBeReady();
		} else {
			throw new ImplementationException("NOT complete_document_tag_selection_with_family");
		}

	}


	@When("^.*(\\[Not\\] )? select_docs_with_family_docs$")
	public void select_docs_with_family_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select doc source with family
			driver.waitForPageToBeReady();
			//* DocID: ID00000861
			//First parent row with family docs
			if(prod.getDocListParentChildDetailsRowButton().FindWebElements().size()>0) {
				prod.getDocListParentChildDetailsRowButton().FindWebElements().get(0).click();
			}
			//Get all child rows of that parent and store size for verification later
			int childDocs = prod.getChildDocuments().FindWebElements().size();
			dataMap.put("childDocs", childDocs);
			

			
		} else {
			throw new ImplementationException("NOT select_docs_with_family_docs");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_doclist_with_family_docs$")
	public void verify_doclist_with_family_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC7859 Production Document Selection to DocList with Child Documents
			
			driver.waitForPageToBeReady();
			int childDocs = (int)dataMap.get("childDocs");
			int totalDocs = (int)dataMap.get("numOfDocs");
			
			
			//make sure child docs equals total docs minus the parent doc
			Assert.assertEquals(childDocs,totalDocs-1);
			
			pass(dataMap, "TC7859");
		} else {
			throw new ImplementationException("NOT verify_doclist_with_family_docs");
		}

	}


	@And("^.*(\\[Not\\] )? completing_the_priv_guard_section_and_navigating_back$")
	public void completing_the_priv_guard_section_and_navigating_back(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Mark CompletedClick NextClick Back
			driver.waitForPageToBeReady();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getProdGuardCompleteBtn().Displayed()  ;}}), Input.wait30);
			
			prod.getProdGuardCompleteBtn().click();
			prod.getbtnProductionGuardNext().click();
			prod.getSpecifyProdLocBackBtn().click();
		} else {
			throw new ImplementationException("NOT completing_the_priv_guard_section_and_navigating_back");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_mark_incomplete_button$")
	public void clicking_the_productions_mark_incomplete_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking Mark Incomplete
			driver.waitForPageToBeReady();
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getPrivMarkIncompleteBtn().Displayed()  ;}}), Input.wait30);
			
			prod.getPrivMarkIncompleteBtn().click();
			throw new ImplementationException("clicking_the_productions_mark_incomplete_button");
		} else {
			throw new ImplementationException("NOT clicking_the_productions_mark_incomplete_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_remove_option_on_the_rule_is_displayed$")
	public void verify_the_remove_option_on_the_rule_is_displayed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4466Verify the button "Remove" is displayed once the Mark incomplete button is clicked on.
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getPrivRemoveBtn().Displayed()  ;}}), Input.wait30);
			
			prod.getPrivRemoveBtn().FindWebElements().get(2).click();
			
			pass(dataMap, "TC4466");
		} else {
			throw new ImplementationException("NOT verify_the_remove_option_on_the_rule_is_displayed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_sorting_options_in_the_numbering_compoent_display_correctly$")
	public void verify_the_sorting_options_in_the_numbering_compoent_display_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4930Verify the three radio button options are "Sort by Metadata", "Sort by Selected Tags", "Custom Sort - Upload Excel".Verify when clicking Sort by Selected Tag, a grid displays with the header "AVAILABLE TAGS" and "SELECTED TAGS" with a list of tags under the "AVAILABLE TAGS".Verify when clicking "Custom Sort - Upload Excel", the button "Select Excel is displayed.
			throw new ImplementationException("verify_the_sorting_options_in_the_numbering_compoent_display_correctly");
		} else {
			throw new ImplementationException("NOT verify_the_sorting_options_in_the_numbering_compoent_display_correctly");
		}

	}


	@And("^.*(\\[Not\\] )? on_the_next_bates_number_dialog$")
	public void on_the_next_bates_number_dialog(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click on the Click here link
			throw new ImplementationException("on_the_next_bates_number_dialog");
		} else {
			throw new ImplementationException("NOT on_the_next_bates_number_dialog");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_x_button_on_the_next_bates_dialog$")
	public void clicking_the_x_button_on_the_next_bates_dialog(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the X to close the dialog
			throw new ImplementationException("clicking_the_x_button_on_the_next_bates_dialog");
		} else {
			throw new ImplementationException("NOT clicking_the_x_button_on_the_next_bates_dialog");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_clicking_x_on_the_next_bates_number_dialog_does_not_populate_any_fields$")
	public void verify_clicking_x_on_the_next_bates_number_dialog_does_not_populate_any_fields(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 8364Verify clicking X closes the dialog.Verify no values are entered into Beginning Bates #, Prefix, Suffix, or Min Number Length. Min Number Length is 0 by default
			throw new ImplementationException("verify_clicking_x_on_the_next_bates_number_dialog_does_not_populate_any_fields");
		} else {
			throw new ImplementationException("NOT verify_clicking_x_on_the_next_bates_number_dialog_does_not_populate_any_fields");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_sorting_metadata_dropdowns_are_sorted_alphabetically$")
	public void verify_the_sorting_metadata_dropdowns_are_sorted_alphabetically(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 8156Verify the dropdown in SORTING is sorted AlphabeticallyVerify the dropdown under Sub-sort By is sorted Alphabetically
			throw new ImplementationException("verify_the_sorting_metadata_dropdowns_are_sorted_alphabetically");
		} else {
			throw new ImplementationException("NOT verify_the_sorting_metadata_dropdowns_are_sorted_alphabetically");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_use_metadata_field_radio_button$")
	public void clicking_the_use_metadata_field_radio_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("clicking_the_use_metadata_field_radio_button");
		} else {
			throw new ImplementationException("NOT clicking_the_use_metadata_field_radio_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_click_here_link_is_not_available_when_the_option_use_metadata_field_is_selected$")
	public void verify_the_click_here_link_is_not_available_when_the_option_use_metadata_field_is_selected(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 8346Verify the "Click here" link when Speciy Bates Numbering option is selected is not there because the option was changed to Use Metadata Field
			throw new ImplementationException("verify_the_click_here_link_is_not_available_when_the_option_use_metadata_field_is_selected");
		} else {
			throw new ImplementationException("NOT verify_the_click_here_link_is_not_available_when_the_option_use_metadata_field_is_selected");
		}

	}


	@And("^.*(\\[Not\\] )? on_the_production_location_component$")
	public void on_the_production_location_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Mark CompleteClick Next //
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
			
			pass(dataMap,"on_the_production_location_component");
		} else {
			fail(dataMap,"NOT on_the_production_location_component");
		} 
	}


	@And("^.*(\\[Not\\] )? completed_the_second_default_dat_section$")
	public void completed_the_second_default_dat_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Production Components page is already displayedClick Add FieldAdd 2nd field classification: BatesAdd 2nd source field: BatesNumberEnter 2nd DAT field: Bates Number 2
			throw new ImplementationException("completed_the_second_default_dat_section");
		} else {
			throw new ImplementationException("NOT completed_the_second_default_dat_section");
		}

	}


	@And("^.*(\\[Not\\] )? completed_the_third_default_dat_section$")
	public void completed_the_third_default_dat_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Production Components page is already displayedClick Add FieldAdd 3rd field classification: BatesAdd 3rd source field: BatesNumberEnter 3rd DAT field: Bates Number 3
			throw new ImplementationException("completed_the_third_default_dat_section");
		} else {
			throw new ImplementationException("NOT completed_the_third_default_dat_section");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_production_can_be_completed_with_multiple_dats_with_the_same_field_class$")
	public void verify_the_production_can_be_completed_with_multiple_dats_with_the_same_field_class(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 7357Verify the following message appears:The same DAT source field has been found to occur more than once. DAT configuration includes mapping of a project field with multiple DAT fields. Do you want to continue with this configuration?Click Continue
			throw new ImplementationException("verify_the_production_can_be_completed_with_multiple_dats_with_the_same_field_class");
		} else {
			throw new ImplementationException("NOT verify_the_production_can_be_completed_with_multiple_dats_with_the_same_field_class");
		}

	}


	@And("^.*(\\[Not\\] )? the_pdf_section_is_expanded$")
	public void the_pdf_section_is_expanded(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//The PDF section is expanded
			throw new ImplementationException("the_pdf_section_is_expanded");
		} else {
			throw new ImplementationException("NOT the_pdf_section_is_expanded");
		}

	}


	@When("^.*(\\[Not\\] )? enabling_blank_page_removal_for_pdf$")
	public void enabling_blank_page_removal_for_pdf(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("enabling_blank_page_removal_for_pdf");
		} else {
			throw new ImplementationException("NOT enabling_blank_page_removal_for_pdf");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_message_displayed_when_pdf_blank_page_removal_is_enabled$")
	public void verify_the_message_displayed_when_pdf_blank_page_removal_is_enabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6972 part 2Verify the following message appears "Enabling Blank Page Removal doubles the overall production time. Are you sure you want to continue?"Click Continue
			throw new ImplementationException("verify_the_message_displayed_when_pdf_blank_page_removal_is_enabled");
		} else {
			throw new ImplementationException("NOT verify_the_message_displayed_when_pdf_blank_page_removal_is_enabled");
		}

	}


	@When("^.*(\\[Not\\] )? expanding_the_translations_components$")
	public void expanding_the_translations_components(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expands the Translations componentExpands the Advanced section
			throw new ImplementationException("expanding_the_translations_components");
		} else {
			throw new ImplementationException("NOT expanding_the_translations_components");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_generate_load_file_is_enabled_by_default_for_translation_components$")
	public void verify_the_generate_load_file_is_enabled_by_default_for_translation_components(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6432
			//* Verify the button "Generate Load File (LST) is enabled by default
			//
			throw new ImplementationException("verify_the_generate_load_file_is_enabled_by_default_for_translation_components");
		} else {
			throw new ImplementationException("NOT verify_the_generate_load_file_is_enabled_by_default_for_translation_components");
		}

	}


	@When("^.*(\\[Not\\] )? expanding_the_mp3_components$")
	public void expanding_the_mp3_components(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expands the MP3 componentExpands the Advanced section
			throw new ImplementationException("expanding_the_mp3_components");
		} else {
			throw new ImplementationException("NOT expanding_the_mp3_components");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_generate_load_file_is_enabled_by_default_for_mp3_components$")
	public void verify_the_generate_load_file_is_enabled_by_default_for_mp3_components(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6431
			//* Verify the button "Generate Load File (LST) is enabled by default
			//
			throw new ImplementationException("verify_the_generate_load_file_is_enabled_by_default_for_mp3_components");
		} else {
			throw new ImplementationException("NOT verify_the_generate_load_file_is_enabled_by_default_for_mp3_components");
		}

	}


	@And("^.*(\\[Not\\] )? completed_component_with_slip_sheet$")
	public void completed_component_with_slip_sheet(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Use the parameter to dictate whether you will be doing this on the PDF or TIFF section. Both TIFF and PDF have the same fields in the ir respective section so the steps are the same between both. Below is an example of the parameter being TIFF. Check the TIFF checkboxExpand the TIFF sectionDisable Enable for Priviledged DocsExpand the Advanced SectionEnable Slip SheetsCheck off the first three checkboxes under METADATAClick Add to SelectedVerify the three options are added to the right side into Selected Fields
			throw new ImplementationException("completed_component_with_slip_sheet");
		} else {
			throw new ImplementationException("NOT completed_component_with_slip_sheet");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added$")
	public void verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5174 / 5082Verify the production is successfully completed with the message Mark Complete Successful.Verify clicking next and clicking back, that the slip sheet is still saved and enabled.
			throw new ImplementationException("verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added");
		} else {
			throw new ImplementationException("NOT verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added");
		}

	}


	@And("^.*(\\[Not\\] )? tiff_pdf_components_are_completed_without_a_dat_component$")
	public void tiff_pdf_components_are_completed_without_a_dat_component(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check off TIFFClick TIFF to expand itClick Select Tags in the "Placeholders" section.Click the "Privileged" folderClick SelectType in "Automated Placeholder" in "Enter placeholder text for the privileged docs".Toggle on "Burn Redactions"Select the option "Select Redactions"Check off Default Automation RedactionCheck off PDFClick PDFto expand itClick Select Tags in the "Placeholders" section.Click the "Privileged" folderClick SelectType in "Automated Placeholder" in "Enter placeholder text for the privileged docs".Toggle on "Burn Redactions"Select the option "Select Redactions"Check off Default Automation RedactionClick the complete buttonVerify an error appears "For a production, the DAT check box selection is mandatory."
			throw new ImplementationException("tiff_pdf_components_are_completed_without_a_dat_component");
		} else {
			throw new ImplementationException("NOT tiff_pdf_components_are_completed_without_a_dat_component");
		}

	}


	@And("^.*(\\[Not\\] )? complete_the_dat_section_$")
	public void complete_the_dat_section_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Production Components page is already displayedClick the DAT checkboxClick the DAT tab to open the DAT containerAdd field classification: BatesAdd source field: BatesNumberEnter DAT field: The value here should come from the parameterMake sure to cover the negative case with the leading space test
			throw new ImplementationException("complete_the_dat_section_");
		} else {
			throw new ImplementationException("NOT complete_the_dat_section_");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_production_is_marked_complete_after_a_dat_is_added$")
	public void verify_the_production_is_marked_complete_after_a_dat_is_added(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4759 /4760 / 7321 / 7322 / 7323 /7343Verify the production is marked completed successfully.
			throw new ImplementationException("verify_the_production_is_marked_complete_after_a_dat_is_added");
		} else {
			throw new ImplementationException("NOT verify_the_production_is_marked_complete_after_a_dat_is_added");
		}

	}


	@When("^.*(\\[Not\\] )? expanding_clicking_the_field_classification_dropdown$")
	public void expanding_clicking_the_field_classification_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand the DAT sectionClick on the dropdown for Field Classification
			throw new ImplementationException("expanding_clicking_the_field_classification_dropdown");
		} else {
			throw new ImplementationException("NOT expanding_clicking_the_field_classification_dropdown");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_bates_field_classification_is_listed_in_alphabetical_order$")
	public void verify_the_bates_field_classification_is_listed_in_alphabetical_order(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 5173Verify the list of field classifications are listed in this exact order:
			//* Select
			//* Appointment
			//* Bates
			//* Custom
			//* Doc Basic
			//* Email
			//* Family
			//* File Path
			//* Near Dupe
			//* Production
			//* TAGS
			//
			throw new ImplementationException("verify_the_bates_field_classification_is_listed_in_alphabetical_order");
		} else {
			throw new ImplementationException("NOT verify_the_bates_field_classification_is_listed_in_alphabetical_order");
		}

	}


	@And("^.*(\\[Not\\] )? complete_native_section_with_$")
	public void complete_native_section_with_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Checkoff NativeExpand NativeIf Native Data is file_types:Check Select All for File TypesIf Native Data is tags:Click Select TagsClick Default Automation TagClick SelectIf Native Data is files_and_tags:Check Select All for File TypesClick Select TagsClick Default Automation TagClick Select
			throw new ImplementationException("complete_native_section_with_");
		} else {
			throw new ImplementationException("NOT complete_native_section_with_");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_productions_mark_complete_and_incomplete_button$")
	public void clicking_the_productions_mark_complete_and_incomplete_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the Mark Complete ButtonClick the Mark Incomplete Button
			throw new ImplementationException("clicking_the_productions_mark_complete_and_incomplete_button");
		} else {
			throw new ImplementationException("NOT clicking_the_productions_mark_complete_and_incomplete_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted$")
	public void verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6820 / 6821 / 6822Expand the Native sectionVerify depending on the native data provided, the correct file types, tags, or both are displayed correctly in the native section.If file types, verify the select all checkbox is enabled as well as the second checkbox to make sure.If tags, make sure the tag is displayed next to the "Select Tags" button.If both, verify both items above.
			throw new ImplementationException("verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted");
		} else {
			throw new ImplementationException("NOT verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted");
		}

	}


	@And("^.*(\\[Not\\] )? the_native_checkbox_is_enabled$")
	public void the_native_checkbox_is_enabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking the checkbox for the Native section
			throw new ImplementationException("the_native_checkbox_is_enabled");
		} else {
			throw new ImplementationException("NOT the_native_checkbox_is_enabled");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_an_error_message_is_returned_on_empty_native_components$")
	public void verify_an_error_message_is_returned_on_empty_native_components(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6415Verify the following error is returned: You must select at least a file group type or a tag in the Native components section
			throw new ImplementationException("verify_an_error_message_is_returned_on_empty_native_components");
		} else {
			throw new ImplementationException("NOT verify_an_error_message_is_returned_on_empty_native_components");
		}

	}


	@And("^.*(\\[Not\\] )? on_the_section_insert_metadata_field_dialog$")
	public void on_the_section_insert_metadata_field_dialog(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand the TIFF or PDF section depending on the component variable.If area = brandingIn the branding section, click Insert Metadata Field. If area = placeholderIn the placeholder section, click Insert Metadata Field. If area = filetypesIn the placeholder section, Click Enable for Natively Producted Documentsclick Insert Metadata Field. If area = redactionsIn the Redactions section, enable Burn RedactionsClick Specify Redaction Text by Selecting Redaction TagsClick Insert Metadata Field.If area = techissueIn the placeholder section, click Enable for Tech Issue DocsClick Insert Metadata Field.If area = slipsheetsExpand the Advanced sectionEnable Slip SheetsIf area = calculatedExpand the Advanced sectionEnable Slip SheetsClick on the CALCULATED Tab
			throw new ImplementationException("on_the_section_insert_metadata_field_dialog");
		} else {
			throw new ImplementationException("NOT on_the_section_insert_metadata_field_dialog");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_insert_metadata_field_dropdown$")
	public void clicking_the_insert_metadata_field_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//if area = slipsheetdo not click anythingif area = calculateddo not click anythingelseClick the Insert Metadata field dropdown
			throw new ImplementationException("clicking_the_insert_metadata_field_dropdown");
		} else {
			throw new ImplementationException("NOT clicking_the_insert_metadata_field_dropdown");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_metadata_field_dropdown_is_sorted_alphabetically$")
	public void verify_the_metadata_field_dropdown_is_sorted_alphabetically(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 8022, 8034, 8037, 8039, 8041, 8043, 8046Verify the options in the dropdown for "Insert Metadata Field" is sorted alphabetically.Click Cancel.
			throw new ImplementationException("verify_the_metadata_field_dropdown_is_sorted_alphabetically");
		} else {
			throw new ImplementationException("NOT verify_the_metadata_field_dropdown_is_sorted_alphabetically");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_new_line_dropdown$")
	public void clicking_the_new_line_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking the New Line dropdown
			throw new ImplementationException("clicking_the_new_line_dropdown");
		} else {
			throw new ImplementationException("NOT clicking_the_new_line_dropdown");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_dat_new_line_delimiters_are_displaying_from_the_dropdown$")
	public void verify_the_dat_new_line_delimiters_are_displaying_from_the_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC6327 Verify the dropdown has an option with the value -1039 in their http selector dropdown.Verify the dropdown has an option with the text "ASCII(255)".Verify the dropdown has an option with the value -875 in their http selector dropdown.Verify the dropdown has an option with the text "ASCII(90)".Verify the dropdown has an option with the text "ASCII(20)".Verify the dropdown has a total of 255 options to select. Should be ASCII 1-255. Don't need to verify each option is in the dropdown, but that the count of the dropdown is 255.
			throw new ImplementationException("verify_the_dat_new_line_delimiters_are_displaying_from_the_dropdown");
		} else {
			throw new ImplementationException("NOT verify_the_dat_new_line_delimiters_are_displaying_from_the_dropdown");
		}

	}


	@And("^.*(\\[Not\\] )? the_tiff_section_is_expanded$")
	public void the_tiff_section_is_expanded(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Expand the Tiff Section
			throw new ImplementationException("the_tiff_section_is_expanded");
		} else {
			throw new ImplementationException("NOT the_tiff_section_is_expanded");
		}

	}


	@When("^.*(\\[Not\\] )? enabling_blank_page_removal_for_tiff$")
	public void enabling_blank_page_removal_for_tiff(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Blank Page Removal to enable it on the TIff section
			throw new ImplementationException("enabling_blank_page_removal_for_tiff");
		} else {
			throw new ImplementationException("NOT enabling_blank_page_removal_for_tiff");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_message_displayed_when_tiff_blank_page_removal_is_enabled$")
	public void verify_the_message_displayed_when_tiff_blank_page_removal_is_enabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 6972Verify the following message appears "Enabling Blank Page Removal doubles the overall production time. Are you sure you want to continue?"Click Continue
			throw new ImplementationException("verify_the_message_displayed_when_tiff_blank_page_removal_is_enabled");
		} else {
			throw new ImplementationException("NOT verify_the_message_displayed_when_tiff_blank_page_removal_is_enabled");
		}

	}


	@And("^.*(\\[Not\\] )? the_tiff_section_is_enabled_with_burn_redactions_enabled$")
	public void the_tiff_section_is_enabled_with_burn_redactions_enabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check the TIFF checkboxExpand the Tiff SectionDisable Enable for Privileged DocsEnable the button Burn RedactionsClick Specify Redaction Text by Selecting Redaction TagsVerify the field "Abbreviated Text:" displays "RED" by default and the placeholder text displays "REDACTED". Click Select Refaction Tag(s)Check Default Automation RedactionClick Select
			throw new ImplementationException("the_tiff_section_is_enabled_with_burn_redactions_enabled");
		} else {
			throw new ImplementationException("NOT the_tiff_section_is_enabled_with_burn_redactions_enabled");
		}

	}


	@When("^.*(\\[Not\\] )? erase_the_placeholder_mark_complete$")
	public void erase_the_placeholder_mark_complete(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Delete the text for placeholder in redactions. The text shold be "REDACTED" by default, erase this value.Click Mark Complete.
			throw new ImplementationException("erase_the_placeholder_mark_complete");
		} else {
			throw new ImplementationException("NOT erase_the_placeholder_mark_complete");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_on_the_back_button$")
	public void clicking_on_the_back_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the <Back button //
			prod.getBackToPrivbutton().click();
			
			pass(dataMap,"clicking_on_the_back_button");
		} else {
			fail(dataMap, "NOT clicking_on_the_back_button");}
	}

	@Then("^.*(\\[Not\\] )? verify_an_error_is_returned_when_a_blank_redaction_placeholder_is_marked_completed$")
	public void verify_an_error_is_returned_when_a_blank_redaction_placeholder_is_marked_completed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 11369, 11366Verify an error message is displayed: Specified Redaction Text in TIFF Burned Redactions cannot be blank.
			throw new ImplementationException("verify_an_error_is_returned_when_a_blank_redaction_placeholder_is_marked_completed");
		} else {
			throw new ImplementationException("NOT verify_an_error_is_returned_when_a_blank_redaction_placeholder_is_marked_completed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_user_is_navigated_back_to_the_priv_guard$")
	public void verify_the_user_is_navigated_back_to_the_priv_guard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4467Verify the user is navigated back to the Priv Guard Component
			//
			Thread.sleep(1000);
			Assert.assertEquals(prod.getPrivTitle().getText().toString(), "Privileged Doc Check"); 
			pass(dataMap,"verify_the_user_is_navigated_back_to_the_priv_guard");
		} else {
			fail(dataMap, "NOT verify_the_user_is_navigated_back_to_the_priv_guard");
		}
	}

	@And("^.*(\\[Not\\] )? email_classification_is_added_for_dats$")
	public void email_classification_is_added_for_dats(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Check off DAT sectionExpand DAT sectionIn FIELDCLASSIFICATION set it to Email
			throw new ImplementationException("email_classification_is_added_for_dats");
		} else {
			throw new ImplementationException("NOT email_classification_is_added_for_dats");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_dats_source_field_dropdown$")
	public void clicking_the_dats_source_field_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking the dropdown for DAT's SOURCE FIELD
			throw new ImplementationException("clicking_the_dats_source_field_dropdown");
		} else {
			throw new ImplementationException("NOT clicking_the_dats_source_field_dropdown");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_email_field_classification_has_the_correct_options$")
	public void verify_the_email_field_classification_has_the_correct_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 7975 Verify that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses should be available under the "Email" category.
			throw new ImplementationException("verify_the_email_field_classification_has_the_correct_options");
		} else {
			throw new ImplementationException("NOT verify_the_email_field_classification_has_the_correct_options");
		}

	}


	@And("^.*(\\[Not\\] )? complete_the_second_dat_section_with_duplicate_information$")
	public void complete_the_second_dat_section_with_duplicate_information(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Add FieldThe Field Classification should be the same used in the first dat fieldThe Source Field should be the same used in the first dat fieldThe DAT field should be the same used in the first dat field
			throw new ImplementationException("complete_the_second_dat_section_with_duplicate_information");
		} else {
			throw new ImplementationException("NOT complete_the_second_dat_section_with_duplicate_information");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_an_error_is_returned_from_using_duplicate_dat_fields_values$")
	public void verify_an_error_is_returned_from_using_duplicate_dat_fields_values(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 7361Click Continue on the warning message.Verify the following warning appears after clicking Continue: "Multiple source fields cannot be mapped to the same field in the DAT file."
			throw new ImplementationException("verify_an_error_is_returned_from_using_duplicate_dat_fields_values");
		} else {
			throw new ImplementationException("NOT verify_an_error_is_returned_from_using_duplicate_dat_fields_values");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_open_wizard_on_the_first_production$")
	public void clicking_open_wizard_on_the_first_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the settings button on the productionClick Open in Wizard
			throw new ImplementationException("clicking_open_wizard_on_the_first_production");
		} else {
			throw new ImplementationException("NOT clicking_open_wizard_on_the_first_production");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_user_is_able_to_open_a_production_via_the_wizard$")
	public void verify_the_user_is_able_to_open_a_production_via_the_wizard(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 3411Verify the user is not on the production's home page anymoreVerify the user is able to see the Production's sections: Basic Info, Production Components, Numbering & Sorting, Document Selection, Priv Guard, Production Location, Summary & Preview, Generate, and Quality Control & Confirmation
			throw new ImplementationException("verify_the_user_is_able_to_open_a_production_via_the_wizard");
		} else {
			throw new ImplementationException("NOT verify_the_user_is_able_to_open_a_production_via_the_wizard");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_add_new_production$")
	public void clicking_add_new_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking Add New Production
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getAddNewProductionbutton().Displayed()  ;}}), Input.wait30); 
			prod.getAddNewProductionbutton().Click();
			driver.waitForPageToBeReady();

			pass(dataMap, "clicking_add_new_production");
		} else {
			fail(dataMap, "NOT clicking_add_new_production");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_basic_info_section_does_not_show_a_disclaimer$")
	public void verify_the_basic_info_section_does_not_show_a_disclaimer(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 11023
			//* Verify on the Basic Info section, there are only the fields "Name", "Description" and "Load Template".
			//* Verify there is no text saying "Disclaimer for Sightline goes here"
			//
			Assert.assertTrue(prod.getProductionNameLabel().getText().contains("Name"));
			Assert.assertTrue(prod.getProductionDescLabel().getText().contains("Description"));
			Assert.assertTrue(prod.getProductionLoadTempLabel().getText().contains("Load Template"));
			
			
			pass(dataMap,"verify_the_basic_info_section_does_not_show_a_disclaimer");
		} else {
			fail(dataMap,"NOT verify_the_basic_info_section_does_not_show_a_disclaimer");
		}

	}


	@And("^.*(\\[Not\\] )? store_the_first_productions_name$")
	public void store_the_first_productions_name(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Store the name of the first production that is displayed.This is going to be used later.
			dataMap.put("first prod name", prod.getProductionTileNameByIndex(0));
			pass(dataMap, "Stored the first production name");
		} else {
			fail(dataMap, "Cannot store the first production name");
		}

	}


	@And("^.*(\\[Not\\] )? on_the_basic_info_component_on_a_new_production$")
	public void on_the_basic_info_component_on_a_new_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking Add New Production
			clicking_add_new_production(true,dataMap);
			pass(dataMap, "on_the_basic_info_component_on_a_new_production");
		} else {
			fail(dataMap, "NOT on_the_basic_info_component_on_a_new_production");
		}

	}


	@And("^.*(\\[Not\\] )? enter_the_name_of_the_existing_production$")
	public void enter_the_name_of_the_existing_production(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Use the first production's name that was stored to type it into the new production's name field.
			prod.getProductionName().click();
			prod.getProductionName().sendKeys(dataMap.get("first prod name").toString());
			pass(dataMap, "Entered the name of the existing production");
		} else {
			fail(dataMap, "Did not enter the name of the existing production");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_user_is_not_able_to_enter_a_dupe_name_for_productions$")
	public void verify_the_user_is_not_able_to_enter_a_dupe_name_for_productions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4470
			//Click Mark Complete Button
			driver.FindElementByTagName("body").SendKeys(Keys.HOME.toString());
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(prod.getMarkCompleteButton().getWebElement()).perform();
			prod.getMarkCompleteButton().click();

			// Verify the following error appears: 60001000011 : You cannot create this production since a production with the same name already exists in the project.
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getPopUpBoxText().Visible()  ;}}), Input.wait30);
			String actualText = prod.getPopUpBoxText().getText();
			String expectedText = "60001000011 : You cannot create this production since a production with the same name already exists in the project.";
			Assert.assertEquals(actualText, expectedText);

			pass(dataMap, "The user is not able to enter a dupe name for productions");
		} else {
			fail(dataMap, "NOT verify_the_user_is_not_able_to_enter_a_dupe_name_for_productions");
		}

	}


	@When("^.*(\\[Not\\] )? setting_the_sort_dropdown_by_production_name$")
	public void setting_the_sort_dropdown_by_production_name(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the Sort By dropdown and select Production Name
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getSortByButton().Visible()  ;}}), Input.wait30);
			prod.getSortByButton().selectFromDropdown().selectByVisibleText("Production Name");

			pass(dataMap, "Setting the sort dropdown by production name");
		} else {
			fail(dataMap, "Not setting the sort dropdown by production name");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_sorting_of_the_productions_is_by_name$")
	public void verify_the_sorting_of_the_productions_is_by_name(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 3708
			//Verify the first 15 productions on the list of productions are sorted in alphabetical order.
			driver.scrollingToBottomofAPage();
			List<WebElement> productionTiles = prod.getProductionTileNames().FindWebElements();
			int iterations = productionTiles.size() > 15 ? 15 : productionTiles.size();
			String previousProductionName = "";
			for(int i = 0 ; i < iterations ; i++) {
				String currentProductionName = productionTiles.get(i).getText();
				if (currentProductionName.compareTo(previousProductionName) < 0)
					fail(dataMap,"The sorting of the productions is not by name");
				previousProductionName = currentProductionName;
			}
			pass(dataMap, "The sorting of the productions is by name");
		} else {
			fail(dataMap,"The sorting of the productions is not by name");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_action_dropdown$")
	public void clicking_the_action_dropdown(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the "Action" dropdown
			try {
				prod.getGridActionDropDown().click();
				Thread.sleep(2000);
				pass(dataMap,"clicking_the_action_dropdown");}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			fail(dataMap,"NOT clicking_the_action_dropdown");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_add_doc_button_is_disabled_on_completed_productions$")
	public void verify_the_add_doc_button_is_disabled_on_completed_productions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 7777
			//* Verify from the action dropdown, the option "Add Docs" is disabled
			//
			System.out.print(prod.getAddDocFromActionsDropDown().GetAttribute("class").toString());
			Assert.assertEquals(prod.getAddDocFromActionsDropDown().GetAttribute("class").toString(), "disable");
			
			pass(dataMap, "verify_the_add_doc_button_is_disabled_on_completed_productions");
		} else {
			fail(dataMap, "NOT verify_the_add_doc_button_is_disabled_on_completed_productions");
		}

	}


	@And("^.*(\\[Not\\] )? a_valid_production_name_is_entered$")
	public void a_valid_production_name_is_entered(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Type in the Name of the Production. 
			//Use the same stand name we are using now which is AutoProduction + randomized numbers.
			//Make sure this will type in the invalid parameters also.
			String dateTime = new Long((new Date()).getTime()).toString();
			String template = (String) dataMap.get("prod_template");
			
			String productionName =  (String)dataMap.get("name");

			if(productionName!=null && productionName!=""){
				productionName = productionName + dateTime;
				dataMap.put("production_name", productionName);
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionName().Displayed()  ;}}), Input.wait30); 
				prod.getProductionName().SendKeys(productionName);
				prod.getProductionDesc().click();
				
				pass(dataMap, "a_valid_production_name_is_entered");
			}
			else {
				productionName = "AutoProduction" + dateTime;
				dataMap.put("production_name", productionName);
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getProductionName().Displayed()  ;}}), Input.wait30); 
				prod.getProductionName().SendKeys(productionName);
				prod.getProductionDesc().click();
				pass(dataMap, "a_valid_production_name_is_entered");
			}				
		} else {
			fail(dataMap,"NOT a_valid_production_name_is_entered");
		}

	}


	@And("^.*(\\[Not\\] )? a_valid_production_description_is_entered$")
	public void a_valid_production_description_is_entered(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Type in any description
			prod.getProductionDesc().sendKeys(dataMap.get("description").toString());
			pass(dataMap,"a_valid_production_description_is_entered");
		} else {
			fail(dataMap,"NOT a_valid_production_description_is_entered");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_a_production_can_be_marked_completed_with_a_valid_name_description$")
	public void verify_a_production_can_be_marked_completed_with_a_valid_name_description(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 2909 part 2
			//Verify the message "Mark Complete successful" is displayed.
			//Make sure to check the negative section/False States.
			try {
				driver.waitForPageToBeReady();
				String productionName =  (String)dataMap.get("name");
				
				if (productionName.contains("!")){
					if(prod.getProductionNameWarning().Displayed() == false) {
						fail(dataMap,"The warning did not appear for having a special character in the production name.");
					}
				}else {
					if(prod.getMarkCompleteSuccessfulText().Displayed() == false) {
						fail(dataMap,"The mark complete successfull message did not appear.");
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			pass(dataMap, "verify_a_production_can_be_marked_completed_with_a_valid_name_description");
		} else {
			fail(dataMap, "NOT verify_a_production_can_be_marked_completed_with_a_valid_name_description");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_remove_doc_button_is_disabled_on_completed_productions$")
	public void verify_the_remove_doc_button_is_disabled_on_completed_productions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 7779
			//* Verify from the action dropdown, the option "Remove Docs" is disabled
			//
			System.out.print(prod.getRemoveDocFromActionsDropDown().GetAttribute("class").toString());
			Assert.assertEquals(prod.getRemoveDocFromActionsDropDown().GetAttribute("class").toString(), "disable");
			
			pass(dataMap,"verify_the_remove_doc_button_is_disabled_on_completed_productions");
		} else {
			fail(dataMap,"NOT verify_the_remove_doc_button_is_disabled_on_completed_productions");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_production_home_page_is_displayed_correctly$")
	public void verify_the_production_home_page_is_displayed_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 4431 
			//* Verify the title of the page displays as "Productions & Exports"
			Assert.assertEquals(prod.getProductionHomePageTitle().getText(), "Productions & Exports");

			//* Verify there is a link for "Add a New Production"
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getAddNewProductionbutton().Displayed()  ;}}), Input.wait30);

			//* Verify there is a dropdown for Production/Export Sets
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getProdExport_ProductionSets().Visible()  ;}}), Input.wait30);

			//* Verify the Currently selected production set's name is displayed under the label for "PRODUCTIONS SET".
			String selectedProductionSetName = prod.getProdExport_ProductionSets().selectFromDropdown().getFirstSelectedOption().getText().replace(" (Production Set)", "");
			String productionSetLabelText = prod.getProductionHomePageCurrentlySelectedProductionSet().getText();
			Assert.assertEquals(productionSetLabelText,selectedProductionSetName);

			//* Click the dropdown for Productions Set, and change it to another production set
			prod.clickProductionSetByIndex(prod.getProductionSetsOptions().size()-1);
			driver.waitForPageToBeReady();

			//* Verify the Production set name under "PRODUCTIONS SET" is updated to the new production set's name.
			selectedProductionSetName = prod.getProdExport_ProductionSets().selectFromDropdown().getFirstSelectedOption().getText().replace(" (Production Set)", "");
			productionSetLabelText = prod.getProductionHomePageCurrentlySelectedProductionSet().getText();
			Assert.assertEquals(productionSetLabelText,selectedProductionSetName);

			//* Set the production set back to the original. 
			prod.clickProductionSetByIndex(0);

			pass(dataMap, "The production home page is displayed correctly");
		} else {
			fail(dataMap, "The production home page is not displayed correctly");
		}

	}


	@When("^.*(\\[Not\\] )? clicking_the_production_name_column$")
	public void clicking_the_production_name_column(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Since it is on grid view, click on the column PRODUCTION NAME to sort it
			prod.getProductionGridViewProductionNameColumnHeader().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getProductionGridViewProductionNameColumnHeader().getWebElement().getAttribute("class").equals("sorting_asc");}}), Input.wait30);
			pass(dataMap, "clicking the production name column");
		} else {
			fail(dataMap, "Not clicking the production name column");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_sorting_of_the_productions_is_by_name_in_grid_view$")
	public void verify_the_sorting_of_the_productions_is_by_name_in_grid_view(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 3709Verify the first 15 productions  on the list of productions are sorted in alphabetical order.
			List<WebElement> productionItems = prod.getProductionItemsGrid().FindWebElements();
			//remove first item (column headers)
			productionItems.remove(0);
			String previousProductionName = "";
			for(WebElement currentProduction : productionItems) {
				if (currentProduction.getText().compareTo(previousProductionName) < 0)
					fail(dataMap, "The sorting of the productions is not by name in grid view");
				previousProductionName = currentProduction.getText();
			}
			pass(dataMap, "The sorting of the productions is by name in grid view");
		} else {
			fail(dataMap, "The sorting of the productions is not by name in grid view");
		}

	}


	@When("^.*(\\[Not\\] )? deleting_the_first_production_listed$")
	public void deleting_the_first_production_listed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Clicking the settings button on the first productionClick DeleteClick Ok
			throw new ImplementationException("deleting_the_first_production_listed");
		} else {
			throw new ImplementationException("NOT deleting_the_first_production_listed");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_production_is_deleted_successfully$")
	public void verify_the_production_is_deleted_successfully(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//tc 3402 / 4128Verify the production is deleted succesfully with the message: Production deleted successfully
			throw new ImplementationException("verify_the_production_is_deleted_successfully");
		} else {
			throw new ImplementationException("NOT verify_the_production_is_deleted_successfully");
		}

	}


	@And("^.*(\\[Not\\] )? navigate_back_to_the_production_home_page$")
	public void navigate_back_to_the_production_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click on the Productions button on the left side of the screen
			prod.goToProductionHomePage().click();
			driver.waitForPageToBeReady();
			pass(dataMap, "Navigated back to the production home page");
		} else {
			fail(dataMap, "Did not navigate back to the production home page");
		}

	}


	@And("^.*(\\[Not\\] )? store_the_first_productions_info$")
	public void store_the_first_productions_info(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getProdExport_ProductionSets().Visible()  ;}}), Input.wait30);
			prod.getProdExport_ProductionSets().SendKeys("DefaultProductionSet");

			driver.waitForPageToBeReady();

			//Store the name of the first production that is displayed.
			dataMap.put("firstProductionName", prod.getProductionTileNameByIndex(0));

			//Store the Last Modified user name of the first production that is displayed.
			String lastModifiedUserNameOfFirstProduction = prod.getProductionsLastModifiedUser().FindWebElements().get(0).getText();
			dataMap.put("firstProductionLastModifiedUsername", prod.getProductionsLastModifiedUser().FindWebElements().get(0).getText());

			//Store the time stamp of the last modified date of the first production that is displayed. This is going to be used later.
			String lastModifiedDataOfFirstProduction = prod.getProductionLastModifiedData().FindWebElements().get(0).getText();
			String[] split = lastModifiedDataOfFirstProduction.split("\n");
			dataMap.put("firstProductionLastModifiedDate", split[split.length-1]);

			pass(dataMap, "Stored the first productions info");
		} else {
			fail(dataMap, "Did not store the first productions info");
		}

	}


	@And("^.*(\\[Not\\] )? open_the_production_created_edit_the_name_then_save_using_$")
	public void open_the_production_created_edit_the_name_then_save_using_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click the name of the production that was created
			prod.getProductionTileByName(dataMap.get("firstProductionName").toString()).click();

			//Click Back to go back to Basic Info
			prod.getBackLink().click();

			//Click Mark Incomplete
			prod.getMarkIncompleteLink().click();
			driver.waitForPageToBeReady();

			//Change the name of the production to add 3 more random numbers at the end.
			Random random = new Random();
			int randomNumbers = random.nextInt(900) + 100;
			prod.getProductionName().sendKeys(String.valueOf(randomNumbers));

			//Click Save or Mark Completed depending on the parameter.Capture the timestamp in a new variable.  The server time is ahead 7 hours so convert the time.
			long sevenHours = 3600*1000*7; //in milli-seconds.  The server time is ahead 7 hours.
			int offset = 0; //for some reason the timestamp of when you click "save" or "mark complete" is behind by 5 sec. I use this value to account for that.

			if(dataMap.get("save_option").toString().equals("save")){
				prod.getSaveButton().click();
				String newTimeStamp = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date(new Date().getTime() + sevenHours - offset));
				dataMap.put("newTimeStamp", newTimeStamp);
				pass(dataMap, "Opened the production created and edited the name then saved");
			} else if(dataMap.get("save_option").toString().equals("markcomplete")){
				prod.getMarkCompleteLink().click();
				String newTimeStamp = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date(new Date().getTime() + sevenHours - offset));
				dataMap.put("newTimeStamp", newTimeStamp);
				pass(dataMap, "Opened the production created and edited the name then marked complete");
			}
		} else {
			fail(dataMap, "Can't open the production created and edit the name then save");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_date_created_field_is_reflecting_correctly$")
	public void verify_the_date_created_field_is_reflecting_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 7809Verify the start date reflects the timestamp of when the product was saved/marked completed the first time. This is reflected by the last modified date we captured in "store_the_first_productions_info". By doing this, we are checking that after an edit was made, the created date did not change.
			throw new ImplementationException("verify_the_date_created_field_is_reflecting_correctly");
		} else {
			throw new ImplementationException("NOT verify_the_date_created_field_is_reflecting_correctly");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_the_last_modified_date_on_productions_gets_updated$")
	public void verify_the_last_modified_date_on_productions_gets_updated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC 7739 / 7740
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					prod.getProdExport_ProductionSets().Visible()  ;}}), Input.wait30);
			prod.getProdExport_ProductionSets().SendKeys("DefaultProductionSet");
			
			
			driver.waitForPageToBeReady();

			//Verify the time stamp of when you stored the production's info vs the timestamp of when you made your change do not match.
			Assert.assertNotEquals(dataMap.get("firstProductionLastModifiedDate"), dataMap.get("newTimeStamp"));

			//Verify the new timestamp on the production matches the timestap we captured after clicking Save
			String lastModifiedData = prod.getProductionLastModifiedDataByName(dataMap.get("firstProductionName").toString()).getText();
			String[] splitData = lastModifiedData.split("\n");
			String lastModifiedDate = splitData[splitData.length-1];

			Assert.assertEquals(lastModifiedDate, dataMap.get("newTimeStamp"));

			pass(dataMap, "The last modified date on productions gets updated");
		} else {
			fail(dataMap, "The last modified date on productions does not get updated");
		}

	}
}//EOF
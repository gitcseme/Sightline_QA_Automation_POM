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

import pageFactory.BatchPrintPage;
import testScriptsSmoke.Input;

@SuppressWarnings({"rawtypes", "unchecked" })
public class BatchPrintContext extends CommonContext {

	/* 
	 * moved to CommonContext
	 * 
	public void sightline_is_launched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void login_as_pau(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void on_production_home_page(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

	 */





	@And("^.*(\\[Not\\] )? select_source_selection$")
	public void select_source_selection(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select a source for Select Search
			//* Click Next button
			//
			throw new ImplementationException("select_source_selection");
		} else {
			throw new ImplementationException("NOT select_source_selection");
		}

	}


	@When("^.*(\\[Not\\] )? select_basis_for_printing$")
	public void select_basis_for_printing(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			try {
				
				if (dataMap.containsKey("basis_for_printing")) {
					if (dataMap.get("basis_for_printing").equals("Native")) {
						if (batchPrint.getNativeRadioButton().Selected()) {
							batchPrint.getBasisForPrintingNextButton().click();
						} else {
							batchPrint.getNativeRadioButton().click();
							batchPrint.getBasisForPrintingNextButton().click();
						}
					}
				}
				
			} catch (Exception e) {
				
			}
		} else {
			throw new ImplementationException("NOT select_basis_for_printing");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_analysis_tab_details$")
	public void verify_analysis_tab_details(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4383 Verify that user can view the details of Basis for PrintingVerify radio options
			//
			//* "Print the natives only (ignore translated version of each document.)"
			//* "Print translated text for all of them.  Pick which language." With a language dropdown
			//* "Skip these documents from printing, but folder them for later."
			//* "Ignore these documents from print request."
			//
			//Verify message for translated documents count
			//
			//* "There are # documents that have one or more translated text of the natives.  You must select what you want to do with these # documents."
			//
			//Verify folders are displayed when "Skip these documents from printing, but folder them for later." option is selected
			throw new ImplementationException("verify_analysis_tab_details");
		} else {
			throw new ImplementationException("NOT verify_analysis_tab_details");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_saved_searches_on_source_selection_tab$")
	public void verify_saved_searches_on_source_selection_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4396 Verify user can see the saved searches on Source Selection tab of Batch Print
			throw new ImplementationException("verify_saved_searches_on_source_selection_tab");
		} else {
			throw new ImplementationException("NOT verify_saved_searches_on_source_selection_tab");
		}

	}


	@And("^.*(\\[Not\\] )? select_source_selection_same_name_less_than_250$")
	public void select_source_selection_same_name_less_than_250(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Precondition: Multiple files (less than 250) with same name but different file extensions exist.  (Ex. docname.msg, docnam.txt, docname.ppt)  
			//* Select a source for Select Search to find the precondition files
			//* Click Next button
			//
			try {
				
				if (dataMap.containsKey("select")) {
					String parentOption = "Shared with SG1";
					String option = "CustodianName Erika Grajeda";

					// wait until parent groups become visible
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getSharedWithSG1SearchParentGroup().Visible()  ;}}), Input.wait30);
					
					batchPrint.getSharedWithSG1SearchParentGroup().click();
					
					// wait until options become visible
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getCustodianNameCheckbox().Visible()  ;}}), Input.wait30);
					
					// select option
					batchPrint.getCustodianNameCheckbox().click();
					
					// click Next button
					batchPrint.getSourceSelectionNextButton().click();
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			throw new ImplementationException("NOT select_source_selection_same_name_less_than_250");
		}

	}


	@And("^.*(\\[Not\\] )? select_analysis$")
	public void select_analysis(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Selet analysis
			//* Click Next button
			//
			try {
				if (dataMap.get("basis_for_printing").equals("Native")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getAnalysisnextbutton().Visible()  ;}}), Input.wait30);
					batchPrint.getAnalysisnextbutton().click();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			throw new ImplementationException("NOT select_analysis");
		}

	}


	@And("^.*(\\[Not\\] )? select_exception_file_types$")
	public void select_exception_file_types(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select Exception File Types
			//* Click Next button
			//
			try {
				if (dataMap.get("excel_files").toString().equalsIgnoreCase("print")) {
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getPrintExcelFilesRadioButton().Visible()  ;}}), Input.wait30);
					if (batchPrint.getPrintExcelFilesRadioButton().Selected()) {
						batchPrint.getExceptionFileTypesNextButton().click();
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			throw new ImplementationException("NOT select_exception_file_types");
		}

	}


	@And("^.*(\\[Not\\] )? select_slip_sheets$")
	public void select_slip_sheets(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select Slip Sheets
			//* Click Next button
			//
			try {
				if (dataMap.get("enable_slip_sheets").toString().equalsIgnoreCase("false")) {
					
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getEnableSlipSheetsToggle().Visible()  ;}}), Input.wait30);
					
					// Disable slip sheets
					batchPrint.getEnableSlipSheetsToggle().click();
					
					// wait until slip sheet pabnel is disabled
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getSlipSheetsDisabledPanel().Visible()  ;}}), Input.wait30);
					
				}
				
				batchPrint.getSlipSheetsNextButton().click();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			throw new ImplementationException("NOT select_slip_sheets");
		}

	}


	@And("^.*(\\[Not\\] )? select_branding_redactions$")
	public void select_branding_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select Branding and Redactions
			//* Click Next button
			//
			try {
				String brandLocationText = "Test automation brand location";
				
				if (dataMap.containsKey("branding_location")) {
					String brandingLocation = dataMap.get("branding_location").toString().toUpperCase();
					batchPrint.getBrandingHeaderLocation(brandingLocation).click();
					
					// wait for branding location popup
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getBandingLocationPopup().Visible()  ;}}), Input.wait30);
					// wait for branding location text field
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getBrandingLocationTextField().Visible()  ;}}), Input.wait30);
					// enter branding text
					batchPrint.getBrandingLocationTextField().sendKeys(brandLocationText);
					// click OK button
					batchPrint.getInsertMetadataFieldOKButton().click();
				}
				
				if (dataMap.get("include_applied_redactions").toString().equalsIgnoreCase("true")) {
					
				}
				
				if (dataMap.get("opaque_transparent").toString().equalsIgnoreCase("true")) {
					
				}
				
				batchPrint.getBrandingAndRedactionNextButton().click();
				
			} catch (Exception e) {
				
			}

		} else {
			throw new ImplementationException("NOT select_branding_redactions");
		}

	}


	@And("^.*(\\[Not\\] )? select_export_format$")
	public void select_export_format(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select Export Format
			//* Click Generate button
			//
//			dataMap.put("pdf_creation", "1 PDF for all docs");
//			dataMap.put("sort_by", "MasterDate");
//			dataMap.put("export_by", "DocFileName");

			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getSelectExportFileSortBy().Visible()  ;}}), Input.wait30);
				batchPrint.getSelectExportFileSortBy().sendKeys(dataMap.get("sort_by").toString());
				batchPrint.getGenerateButton().click();
			} catch (Exception e) {
				
			}
			
		} else {
			throw new ImplementationException("NOT select_export_format");
		}

	}


	@When("^.*(\\[Not\\] )? click_download_file_link$")
	public void click_download_file_link(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

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
			try {
				// Reset notification number back to 0. We will use the bull horn to check
				// if file is ready to download
				if (!prod.getBulHornNotificationNumber().getText().equals("0")) {
					prod.getBulHornNotificationNumber().click();
				}
				
				
			} catch (Exception e) {
				
			}
		} else {
			throw new ImplementationException("NOT click_download_file_link");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_single_pdf_generated$")
	public void verify_single_pdf_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11816 Validate Batch Print - Generating single PDF file for corpus containing multiple files with same name but have different file extension
			throw new ImplementationException("verify_single_pdf_generated");
		} else {
			throw new ImplementationException("NOT verify_single_pdf_generated");
		}

	}


	@And("^.*(\\[Not\\] )? select_source_selection_same_name_greater_than_250$")
	public void select_source_selection_same_name_greater_than_250(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Precondition: Multiple files (greater than 250) with same name but different file extensions exist.  (Ex. docname.msg, docnam.txt, docname.ppt)  
			//* Select a source for Select Search to find the precondition files
			//* Click Next button
			//
			throw new ImplementationException("select_source_selection_same_name_greater_than_250");
		} else {
			throw new ImplementationException("NOT select_source_selection_same_name_greater_than_250");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_second_pdf_generated$")
	public void verify_second_pdf_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11816 Validate Batch Print - Generating single PDF file for corpus containing multiple files with same name but have different file extensionTC11817 Validate Batch Print - Generating individual PDF file for corpus containing multiple files (document with more than 250 page) with same name but have different file extensions
			throw new ImplementationException("verify_second_pdf_generated");
		} else {
			throw new ImplementationException("NOT verify_second_pdf_generated");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_separate_pdf_generated_for_every_document$")
	public void verify_separate_pdf_generated_for_every_document(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11817 Validate Batch Print - Generating individual PDF file for corpus containing multiple files (document with more than 250 page) with same name but have different file extensions
			throw new ImplementationException("verify_separate_pdf_generated_for_every_document");
		} else {
			throw new ImplementationException("NOT verify_separate_pdf_generated_for_every_document");
		}

	}


	@And("^.*(\\[Not\\] )? select_source_selection_redacted_docs_with_images$")
	public void select_source_selection_redacted_docs_with_images(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Precondition: Documents exist with multiple redactions, including redactions applied over images
			//* Select a source for Select Search to find the precondition files
			//* Click Next button
			//
			throw new ImplementationException("select_source_selection_redacted_docs_with_images");
		} else {
			throw new ImplementationException("NOT select_source_selection_redacted_docs_with_images");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_redactions_in_pdf$")
	public void verify_redactions_in_pdf(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11801 Validate Batch Printing "Native variant" redacted documents containing images
			//
			//* Applied redactions should be on the same coordinates
			//* Redactions are opaque
			//* When user copies content of redacted page, redacted area/content should still be opaque
			//* Redactions stay in the same place when the page is rotated
			//
			throw new ImplementationException("verify_redactions_in_pdf");
		} else {
			throw new ImplementationException("NOT verify_redactions_in_pdf");
		}

	}


	@And("^.*(\\[Not\\] )? select_slip_sheets_$")
	public void select_slip_sheets_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select slip sheets
			//* Click Next button
			//
			throw new ImplementationException("select_slip_sheets_");
		} else {
			throw new ImplementationException("NOT select_slip_sheets_");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_batch_print_successful$")
	public void verify_batch_print_successful(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC5911 Verify that Batch Print functionality is working correctly if user selects 'Prior Productions (TIFFs/PDFs)'
			throw new ImplementationException("verify_batch_print_successful");
		} else {
			throw new ImplementationException("NOT verify_batch_print_successful");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_batch_print_with_prior_production_slips$")
	public void verify_batch_print_with_prior_production_slips(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4538 Verify user can use the prior production slip sheets for batch print
			throw new ImplementationException("verify_batch_print_with_prior_production_slips");
		} else {
			throw new ImplementationException("NOT verify_batch_print_with_prior_production_slips");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_document_from_analysis_tab$")
	public void verify_production_document_from_analysis_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4645 Verify user can see the production document from analysis tabVerify the grid from the analysis tab
			//
			//* Grid with all the documents from saved search should be displayed
			//* Selected productions should be displayed as columns on analysis tab.  Radio buttons whould be displayed for the documents in the grid
			//
			//Verify production column from the grid
			//
			//* Selected productions should be displayed as a column on analysis tab
			//* Make sure documents from the production should be displayed
			//* Document can be selected from the production column
			//
			throw new ImplementationException("verify_production_document_from_analysis_tab");
		} else {
			throw new ImplementationException("NOT verify_production_document_from_analysis_tab");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_allproductionbatesranges_displayed_on_pdfs$")
	public void verify_allproductionbatesranges_displayed_on_pdfs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9725 Verify 'AllProductionBatesRanges' should display correct value on Batch Print slip sheetsTC9726 Verify in Batch Print, PDF file should be generated with the selected slip sheets field 'AllProductionBatesRanges' if Basis for Printing is selected as Production
			//
			//* Verify AllProductionBatesRanges displayed on generated PDF slip sheet
			//
			throw new ImplementationException("verify_allproductionbatesranges_displayed_on_pdfs");
		} else {
			throw new ImplementationException("NOT verify_allproductionbatesranges_displayed_on_pdfs");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_branding_redaction_tab_skipped$")
	public void verify_branding_redaction_tab_skipped(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4783 Verify that 'Branding and Redaction' tab is skipped if Prior Production is selected from Basis for Printing
			throw new ImplementationException("verify_branding_redaction_tab_skipped");
		} else {
			throw new ImplementationException("NOT verify_branding_redaction_tab_skipped");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_batch_print_when_exported_file_is_the_file_name_metadata_field$")
	public void verify_batch_print_when_exported_file_is_the_file_name_metadata_field(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9394 Verify that batch print should be working fine when the name of the exported file is the file name metadata field
			throw new ImplementationException("verify_batch_print_when_exported_file_is_the_file_name_metadata_field");
		} else {
			throw new ImplementationException("NOT verify_batch_print_when_exported_file_is_the_file_name_metadata_field");
		}

	}


	@And("^.*(\\[Not\\] )? select_folder$")
	public void select_folder(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("select_folder");
		} else {
			throw new ImplementationException("NOT select_folder");
		}

	}


	@When("^.*(\\[Not\\] )? select_another_folder$")
	public void select_another_folder(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("select_another_folder");
		} else {
			throw new ImplementationException("NOT select_another_folder");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_user_can_select_only_one_source$")
	public void verify_user_can_select_only_one_source(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9704 Verify user can select only one tag or folder or search at a time to perform batch print
			throw new ImplementationException("verify_user_can_select_only_one_source");
		} else {
			throw new ImplementationException("NOT verify_user_can_select_only_one_source");
		}

	}


	@And("^.*(\\[Not\\] )? select_tag$")
	public void select_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("select_tag");
		} else {
			throw new ImplementationException("NOT select_tag");
		}

	}


	@When("^.*(\\[Not\\] )? select_another_tag$")
	public void select_another_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("select_another_tag");
		} else {
			throw new ImplementationException("NOT select_another_tag");
		}

	}


	@And("^.*(\\[Not\\] )? select_search$")
	public void select_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			throw new ImplementationException("select_search");
		} else {
			throw new ImplementationException("NOT select_search");
		}

	}


	@When("^.*(\\[Not\\] )? select_another_search$")
	public void select_another_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

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
			throw new ImplementationException("select_another_search");
		} else {
			throw new ImplementationException("NOT select_another_search");
		}

	}


	@And("^.*(\\[Not\\] )? select_branding_redactions_$")
	public void select_branding_redactions_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select Branding and Redactions
			//* Click Next button
			//
			throw new ImplementationException("select_branding_redactions_");
		} else {
			throw new ImplementationException("NOT select_branding_redactions_");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_batch_print_with_source_folder$")
	public void verify_batch_print_with_source_folder(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9706 Verify that Batch Print should generate successfully if user selected source as 'Folder'
			throw new ImplementationException("verify_batch_print_with_source_folder");
		} else {
			throw new ImplementationException("NOT verify_batch_print_with_source_folder");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_batch_print_with_source_tag$")
	public void verify_batch_print_with_source_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9705 Verify that Batch Print should generate successfully, if user selected Source as 'Tag'
			throw new ImplementationException("verify_batch_print_with_source_tag");
		} else {
			throw new ImplementationException("NOT verify_batch_print_with_source_tag");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_batch_print_with_source_search$")
	public void verify_batch_print_with_source_search(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11206 Verify and gernerate Batch Print with Search as 'Source'
			throw new ImplementationException("verify_batch_print_with_source_search");
		} else {
			throw new ImplementationException("NOT verify_batch_print_with_source_search");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_source_selection_help_question_mark$")
	public void verify_source_selection_help_question_mark(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4386 Verify that help is displayed on clicking on '?'
			throw new ImplementationException("verify_source_selection_help_question_mark");
		} else {
			throw new ImplementationException("NOT verify_source_selection_help_question_mark");
		}

	}
}//eof
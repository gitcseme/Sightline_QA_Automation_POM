package stepDef;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
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

			try {
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT select_source_selection");
		}

	}


	@When("^.*(\\[Not\\] )? select_basis_for_printing$")
	public void select_basis_for_printing(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getBasisForPrintingHeader().Visible()  ;}}), Input.wait30);
				if (dataMap.containsKey("basis_for_printing")) {
					if (dataMap.get("basis_for_printing").equals("Native")) {
						
						// Click next button since native is selected by default
						batchPrint.getBasisForPrintingNextButton().click();
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
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
			batchPrint.getMySavedSearchArrow().click();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					batchPrint.getSelectSavedSearchRows().Displayed()  ;}}), Input.wait30);

			pass(dataMap, "Saved search are displayed on the Source Selection tab of Batch Print");
		} else {
			fail(dataMap, "Saved search are not displayed on the Source Selection tab of Batch Print");
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
				e.printStackTrace();
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
					
					// waits until next page is shown
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getCurrentBreadcrumb("Exception File Types").Visible()  ;}}), Input.wait30);
				}
			} catch (Exception e) {
				e.printStackTrace();
					batchPrint.getAnalysisnextbutton().click();
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
							   batchPrint.getExcelFileOptions().Visible()  ;}}), Input.wait30);
					
					// Check if "Other Exception File Types" field is shown
					if (batchPrint.getIncludeOtherExceptionFileTypesCheckBox().FindWebElements().size() > 0) {
						// if shown, enter placeholder text field
						batchPrint.getPrintExcelPlaceholderTextInputField().click(); // clicking to "enable" the textfield in order to use SendKeys
						batchPrint.getPrintExcelPlaceholderTextInputField().sendKeys("Placeholder Automation text");
					}
					batchPrint.getExceptionFileTypesNextButton().click();
				}
			} catch (Exception e) {
				e.printStackTrace();

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
				e.printStackTrace();
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
				dataMap.put("brangLocationText", brandLocationText);

				
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
				e.printStackTrace();
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




			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getSelectExportFileSortBy().Visible()  ;}}), Input.wait30);


				if (dataMap.get("pdf_creation").toString().equalsIgnoreCase("One PDF for all documents")) {

					batchPrint.getOnePDFForAllDocsRadioButton().Click();
				}
				

				if (dataMap.containsKey("sort_by")) {
					//TODO: Remove sendKeys and use a select function
					batchPrint.getSelectExportFileSortBy().sendKeys(dataMap.get("sort_by").toString());
				}
				

				batchPrint.getGenerateButton().click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getGenerateSuccessMessage().Visible()  ;}}), Input.wait30);

				
			} catch (Exception e) {
				e.printStackTrace();
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

				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getBackgroundTaskFirstRowStatus().Visible()  ;}}), Input.wait30);
				
				int i = 0;
				while(!batchPrint.getBackgroundTaskFirstRowStatus().getText().equalsIgnoreCase("COMPLETED") && i<1000) {
					i++;
					driver.getWebDriver().navigate().refresh();
					driver.waitForPageToBeReady();
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							batchPrint.getBackgroundTaskFirstRowStatus().Displayed() ;}}), Input.wait30);
				}
				if (i != 1000) {
					System.out.println("Completed!");
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							batchPrint.getBackgroundTaskFirstRowDownloadLink().Displayed() ;}}), Input.wait30);
					batchPrint.getBackgroundTaskFirstRowDownloadLink().click();
					driver.waitForPageToBeReady();
				} else {
					fail(dataMap, "Refreshed page 1000 times and is still in progress!");
				}
			} catch (Exception e) {
				e.printStackTrace();

			} 

		} else {
			throw new ImplementationException("NOT click_download_file_link");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_single_pdf_generated$")
	public void verify_single_pdf_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11816 Validate Batch Print - Generating single PDF file for corpus containing multiple files with same name but have different file extension
			
			try {
				String home = System.getProperty("user.home");
				String downloadPath;
			
				if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC){
					downloadPath = home + "/Downloads/";}
				else downloadPath = home + "\\Download\\";
				
				// Adding to sleep to wait for file to finish downloading
				Thread.sleep(30000);

				File dir = new File(downloadPath);
				File[] dirContents = dir.listFiles();
				
				for (int i = 0; i < dirContents.length; i++) {
					
					if (dirContents[i].getName().contains("BatchPrint_")) {
						System.out.println("Found file " + dirContents[i].getName() + "...");
						@SuppressWarnings("resource")
						ZipFile zipFile = new ZipFile(dirContents[i]);
						
						int numOfEntries = zipFile.size();
						
						// Verify there is only one entry in the zip file
						Assert.assertEquals(numOfEntries, 1);
						
						for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
							ZipEntry entry = (ZipEntry) e.nextElement();
							
							// Verify the file is a pdf
							Assert.assertTrue(entry.getName().contains("pdf"));
						}
						
						pass(dataMap, "Found file!");
						
						// delete file after verification
						System.out.println("Deleting file...");
						dirContents[i].delete();
						break;
					}
				}
			} catch (Exception e) {
				fail(dataMap, "Single pdf not generated!");
				e.printStackTrace();
			}
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
			try {
				// wait for radio button to be visible
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getSelectFolderRadioButtonIcon().Visible()  ;}}), Input.wait30);
				batchPrint.getSelectFolderRadioButtonIcon().click();
				
				// wait for Parent Folder to be visible
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getSelectFolderDisplaySet().Visible()  ;}}), Input.wait30);
				
				// click to expand parent folder
				batchPrint.getExpandFolderIcon().click();
				
				// wait for option to be visible
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.get250DocsFolderOption().Visible()  ;}}), Input.wait30);
				
				// click option
				batchPrint.get250DocsFolderOption().click();
				
				// click Next button
				batchPrint.getSourceSelectionNextButton().click();
				
				if (dataMap.containsKey("select")) {
					
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
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT select_source_selection_same_name_greater_than_250");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_second_pdf_generated$")
	public void verify_second_pdf_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11816 Validate Batch Print - Generating single PDF file for corpus containing multiple files with same name but have different file extensionTC11817 Validate Batch Print - Generating individual PDF file for corpus containing multiple files (document with more than 250 page) with same name but have different file extensions
			try {
				String home = System.getProperty("user.home");
				String downloadPath;
			
				if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC){
					downloadPath = home + "/Downloads/";}
				else downloadPath = home + "\\Download\\";
				
				// Adding to sleep to wait for file to download
				Thread.sleep(30000);

				File dir = new File(downloadPath);
				File[] dirContents = dir.listFiles();
				
				for (int i = 0; i < dirContents.length; i++) {
	
					if (dirContents[i].getName().contains("BatchPrint_")) {
						System.out.println("Found file " + dirContents[i].getName() + "...");
						@SuppressWarnings("resource")
						ZipFile zipFile = new ZipFile(dirContents[i]);

						int numOfEntries = zipFile.size();
						
						// Verify there are multiple entries in the zip file
						Assert.assertTrue(numOfEntries > 1);
						
						for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
							ZipEntry entry = (ZipEntry) e.nextElement();
							
							// Verify the files are pdf
							Assert.assertTrue(entry.getName().contains("pdf"));
						}
						
						// delete file after verification
						System.out.println("Deleting file...");
						dirContents[i].delete();
						break;
					}
				}
			} catch (Exception e) {
				fail(dataMap, "Multiple pdfs not generated!");
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT verify_second_pdf_generated");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_separate_pdf_generated_for_every_document$")
	public void verify_separate_pdf_generated_for_every_document(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC11817 Validate Batch Print - Generating individual PDF file for corpus containing multiple files (document with more than 250 page) with same name but have different file extensions
			try {
				String home = System.getProperty("user.home");
				String downloadPath;
			
				if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC){
					downloadPath = home + "/Downloads/";}
				else downloadPath = home + "\\Download\\";
				
				// Adding to sleep to wait for file to download
				Thread.sleep(45000);
				File dir = new File(downloadPath);
				File[] dirContents = dir.listFiles();
				
				for (int i = 0; i < dirContents.length; i++) {
	
					if (dirContents[i].getName().contains("BatchPrint_")) {
						System.out.println("Found file " + dirContents[i].getName() + "...");
						@SuppressWarnings("resource")
						ZipFile zipFile = new ZipFile(dirContents[i]);

						int numOfEntries = zipFile.size();
						
						// Verify there are multiple entries in the zip file
						Assert.assertTrue(numOfEntries > 1);
						
						List<String> listOfFiles = new ArrayList<String>();
						for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
							ZipEntry entry = (ZipEntry) e.nextElement();
							listOfFiles.add(entry.getName());
						}

						// Verify 1.docx and 1.pdf exist in list of files
						Pattern p = Pattern.compile("1.docx|1.pdf");
						boolean found = false;
						for (String f : listOfFiles) {
							if (p.matcher(f).find()) {
								found = true;
								break;
							}
						}
	
						Assert.assertTrue(found);
						
						// delete file after verification
						System.out.println("Deleting file...");
						dirContents[i].delete();
						break;
					}
				}
			} catch (Exception e) {
				fail(dataMap, "Multiple pdfs not generated!");
				e.printStackTrace();
			}
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

	@And("^.*(\\[Not\\] )? select_tag_with_0_docs$")
	public void select_tag_with_0_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Select Tag radio buttonSelect a tag with 0 Documents associated to it
			//
			//* All Tags > 0Docs
			//
			//Click Next button
			throw new ImplementationException("select_tag_with_0_docs");
		} else {
			throw new ImplementationException("NOT select_tag_with_0_docs");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_analysis_0_docs$")
	public void verify_analysis_0_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9707 Verify when user selects a tag/folder with zero count for batch print
			//
			//* Verify message "Analysis of your request   Your request to print: 0 documents"
			//
			throw new ImplementationException("verify_analysis_0_docs");
		} else {
			throw new ImplementationException("NOT verify_analysis_0_docs");
		}

	}


	@And("^.*(\\[Not\\] )? select_folder_with_0_docs$")
	public void select_folder_with_0_docs(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click Select Folder radio buttonSelect a tag with 0 Documents associated to it
			//
			//* All Folders > 0Docs
			//
			//Click Next button
			throw new ImplementationException("select_folder_with_0_docs");
		} else {
			throw new ImplementationException("NOT select_folder_with_0_docs");
		}

	}


	@And("^.*(\\[Not\\] )? select_basis_for_printing_$")
	public void select_basis_for_printing_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select Basis for Printing
			//* Click on the Next button
			//
			throw new ImplementationException("select_basis_for_printing_");
		} else {
			throw new ImplementationException("NOT select_basis_for_printing_");
		}

	}


	@And("^.*(\\[Not\\] )? select_export_format_$")
	public void select_export_format_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select Export Format
			//* Click Generate button
			//Ignore the index parameter, it isnt going to be used.
			throw new ImplementationException("select_export_format_");
		} else {
			throw new ImplementationException("NOT select_export_format_");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_pdf_file_sorted_correctly$")
	public void verify_pdf_file_sorted_correctly(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC8103 Validate Batch Print Sorting docs by MasterDateTime [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order
			//TC8105 Validate Batch Print Sorting docs by CreatedDateTime [Prior Productions (TIFFs/PDFs)]with one PDF for each doc in descending order
			//TC8107 Validate Batch Print Sorting docs by SendDateTime [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order
			//TC8166 Validate Batch Print Sorting docs by LastSaveDate (Native) with one PDF for each doc in descending order
			//TC8167 Validate Batch Print Sorting docs by LastSaveDate [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order
			//TC8169 Validate Batch Print Sorting docs by LastModifiedDate [Prior Productions (TIFFs/PDFs)]with one PDF for each doc in descending order
			//TC8170 Validate Batch Print Sorting docs by LastEditDate (Native) with one PDF for each doc in descending order
			//TC8171 Validate Batch Print Sorting docs by LastEditDate [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order
			//TC8174 Validate Batch Print Sorting docs by DocDate (Native) with one PDF for each doc in descending order
			//TC8175 Validate Batch Print Sorting docs by DocDate [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order
			//TC8177 Validate Batch Print Sorting docs by CustodianName [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order
			//TC8178 Validate Batch Print Sorting docs by DocFileName (Native) with one PDF for all docs in ascending order
			//TC8179 Validate Batch Print Sorting docs by DocFileName [Prior Productions (TIFFs/PDFs)]with one PDF for each doc in descending order
			//TC8181 Validate Batch Print Sorting docs by DocID [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order
			//
			//* Verify PDF exported according to Basis for Printing, and Export Format options
			//
			//
			throw new ImplementationException("verify_pdf_file_sorted_correctly");
		} else {
			throw new ImplementationException("NOT verify_pdf_file_sorted_correctly");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_production_pdf_generated$")
	public void verify_production_pdf_generated(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4563 Verify PDF file should be generated for the selected production set
			throw new ImplementationException("verify_production_pdf_generated");
		} else {
			throw new ImplementationException("NOT verify_production_pdf_generated");
		}

	}

	@And("^.*(\\[Not\\] )? select_source_selection_with_redactions$")
	public void select_source_selection_with_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Select a source for Select SearchDefault search: Shared with SG1 > CustodianName Erika GrajedaClick Next button
			throw new ImplementationException("select_source_selection_with_redactions");
		} else {
			throw new ImplementationException("NOT select_source_selection_with_redactions");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_pdf_generated_with_redactions$")
	public void verify_pdf_generated_with_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4651 Verify PDF file should be generated with redaction on for the documents from selected produciton set with DocID as export file nameTC4658 Verify PDF file should be generated with branding for the documents from selected production set with Begin Bates as export file name
			//TC4659 Verify PDF file should be generated with redaction on for the documents from selected produciton set with File Name as export file name
			//
			//* PDFs with redactions are generated based on Export Format
			//
			//
			throw new ImplementationException("verify_pdf_generated_with_redactions");
		} else {
			throw new ImplementationException("NOT verify_pdf_generated_with_redactions");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_pdf_from_selected_production_slipsheet_field$")
	public void verify_pdf_from_selected_production_slipsheet_field(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4648 Verify PDF file should be generated with the production slipsheet fields for the produciton set with DocID as export file name
			//TC4650 Verify PDF file should be generated with the new slipsheet fields for the produciton set with DocID as export file name
			//TC4653 Verify PDF file should be generated with the production slipsheet fields for the produciton set with Doc File Name as export file name
			//TC4654 Verify PDF file should be generated with the production slipsheet fields for the produciton set with Bates Number as export file name
			//TC4655 Verify PDF file should be generated with the new slipsheet fields for the produciton set with File Name as export file name
			//TC4656 Verify PDF file should be generated with the new slipsheet fields for the produciton set with Begin Bates as export file name
			//
			//* PDF generated based on Slip Sheets and Export Format
			//
			//
			throw new ImplementationException("verify_pdf_from_selected_production_slipsheet_field");
		} else {
			throw new ImplementationException("NOT verify_pdf_from_selected_production_slipsheet_field");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_selected_slipsheet_fields_for_selected_tag$")
	public void verify_selected_slipsheet_fields_for_selected_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9710 Verify PDF file should be generated with the selected slipsheet fields for the selected tag and with DocID as export file name
			//TC9711 Verify PDF file should be generated with the selected slipsheet fields for the selected tag and with DocFileName as export file name
			//
			//* Verify Slip Sheet fields and Export Format for selected tag
			//
			//
			throw new ImplementationException("verify_selected_slipsheet_fields_for_selected_tag");
		} else {
			throw new ImplementationException("NOT verify_selected_slipsheet_fields_for_selected_tag");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_selected_branding_redactions_for_selected_folder$")
	public void verify_selected_branding_redactions_for_selected_folder(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9716 Verify PDF file should be generated with the selected branding & redactions for the selected folder and with DocID as export file nameTC9717 Verify PDF file should be generated with the selected branding & redactions for the selected folder and with DocFileName as export file name
			//
			//* Verify Branding and Redactions and Export Format for selected folder
			//
			throw new ImplementationException("verify_selected_branding_redactions_for_selected_folder");
		} else {
			throw new ImplementationException("NOT verify_selected_branding_redactions_for_selected_folder");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_selected_branding_redactions_for_selected_tag$")
	public void verify_selected_branding_redactions_for_selected_tag(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9714 Verify PDF file should be generated with the selected branding & redactions for the selected tag and with DocID as export file nameTC9715 Verify PDF file should be generated with the selected branding & redactions for the selected tag and with DocFileName as export file name
			//
			//* Verify Branding and Redactions and Export Format for selected tag
			//
			throw new ImplementationException("verify_selected_branding_redactions_for_selected_tag");
		} else {
			throw new ImplementationException("NOT verify_selected_branding_redactions_for_selected_tag");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_selected_slipsheet_fields_for_selected_folder$")
	public void verify_selected_slipsheet_fields_for_selected_folder(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC9712 Verify PDF file should be generated with the selected slipsheet fields for the selected folder and with DocID as export file name
			//TC9713 Verify PDF file should be generated with the selected slipsheet fields for the selected folder and with DocFileName as export file name
			//
			//* Verify Slip Sheet fields and Export Format for selected folder
			//
			//
			throw new ImplementationException("verify_selected_slipsheet_fields_for_selected_folder");
		} else {
			throw new ImplementationException("NOT verify_selected_slipsheet_fields_for_selected_folder");
		}

	}


	@Given("^.*(\\[Not\\] )? login_to_new_batch_print$")
	public void login_to_new_batch_print(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//This is a collection of the following steps:
			//sightline_is_launched
			//login_as_pau
			//on_batch_print_page
			sightline_is_launched(true, dataMap);
			login_as_pau(true, dataMap);
			on_batch_print_page(true, dataMap);
		} else {
			throw new ImplementationException("NOT login_to_new_batch_print");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_notification_displayed_when_background_process_initialized$")
	public void verify_notification_displayed_when_background_process_initialized(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4561 Verify background process should be initialized by selecting Production setsTC4562 Verify notification should be displayed when background process for production set is initialized
			//
			//* Verify green Batch Print successfully created message displayed before directing user to My Background Tasks
			//* New BATCHPRINT row with your INPROGRESS task is created
			//* Notification displayed after Batch Print is COMPLETED.  "Your Batch Print with Batch Print Id ## is COMPLETED"
			//
			throw new ImplementationException("verify_notification_displayed_when_background_process_initialized");
		} else {
			throw new ImplementationException("NOT verify_notification_displayed_when_background_process_initialized");
		}

	}


	@When("^.*(\\[Not\\] )? click_source_selection_back_button$")
	public void click_source_selection_back_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Back' button
			throw new ImplementationException("click_source_selection_back_button");
		} else {
			throw new ImplementationException("NOT click_source_selection_back_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_directed_to_source_selection_tab$")
	public void verify_directed_to_source_selection_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4387 Verify that on clickon Back button, it will redirect to Source Selection
			//
			//* Directed to Source Selection tab
			//
			throw new ImplementationException("verify_directed_to_source_selection_tab");
		} else {
			throw new ImplementationException("NOT verify_directed_to_source_selection_tab");
		}

	}

	@Then("^.*(\\[Not\\] )? verify_prior_productions_radio_button$")
	public void verify_prior_productions_radio_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4450 Verify Analysis tab when 'Prior Productions (TIFFs/PDFs)' radio button is selected from 'Basis for Printing'Basis for Printing tab matches the following format:"You requested to print: 25 documentsOf those,There isno issues with 0 documents.However,There are 25 documents that need your decision below. Of those 25 documents:
			//
			//* 25 are not in any of your specified productions
			//* 0 are in more than one production
			//
			//"Documents grid should have the following columns:
			//
			//* Doc ID
			//* Skip Printing
			//* Production columns as per selected production
			//* Not in any production
			//
			//Make sure that checkbox should be displayed for the document as per the production.Make sure the pagination should be displayed for the grid.Folder tree structure should be displayed with toggling ON.If toggling is OFF then tree folder structure shouldnot be displayed.
			throw new ImplementationException("verify_prior_productions_radio_button");
		} else {
			throw new ImplementationException("NOT verify_prior_productions_radio_button");
		}

	}


	@When("^.*(\\[Not\\] )? select_skip_excel_files_radio_button$")
	public void select_skip_excel_files_radio_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Skip Excel Files' radio button
			try {
				batchPrint.getSkipExcelFilesRadioButton().click();
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getBP_Exception_Excel().GetAttribute("style").contains("display: block;")  ;}}), Input.wait30);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT select_skip_excel_files_radio_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_skip_excel_files_on_exception_file_types_tab$")
	public void verify_skip_excel_files_on_exception_file_types_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4457 Verify the details if user select option as 'Skip Excel Files' on Exception File Types
			//
			//* 'Include Placeholders' tab displayed
			//* Inert Metadata field is displayed
			//
			try {
				if (batchPrint.getInsertMetadataFieldLinkText().Displayed()) {
					pass(dataMap, "PASS! INsert Metadata Field is displayed!");
				} else fail(dataMap, "FAIL! Insert Metadata Field is not displayed!");
				
				if (batchPrint.getIncludePlaceholdersToggle().Displayed()) {
					pass(dataMap, "PASS! Include Placeholder toggle is displayed");
				} else fail(dataMap, "FAIL! Include Placeholder toggle is not displayed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT verify_skip_excel_files_on_exception_file_types_tab");
		}

	}


	@When("^.*(\\[Not\\] )? click_other_exception_file_types_help_button$")
	public void click_other_exception_file_types_help_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Other Exception File Types' help ? button
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getExcelFilesHelpIcon().Visible()  ;}}), Input.wait30);
				batchPrint.getOtherFileTypesHelpIcon().click();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT click_other_exception_file_types_help_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_help_displayed_on_exception_file_types_tab$")
	public void verify_help_displayed_on_exception_file_types_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4400 Verify that Help is displayed on exception file types
			//
			//* Help displayed
			//
			try {
				if (batchPrint.getFileTypeHelpPopup().Displayed()) {
					pass(dataMap, "PASS! Help popup is displayed");
				} else fail(dataMap, "FAIL! Help popup was not displayed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT verify_help_displayed_on_exception_file_types_tab");
		}

	}


	@When("^.*(\\[Not\\] )? click_excel_files_help_button$")
	public void click_excel_files_help_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Excel Files' help ? button
			try {
//				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//						   batchPrint.getExcelFilesHelpIcon().Visible()  ;}}), Input.wait30);
				batchPrint.getExcelFilesHelpIcon().click();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT click_excel_files_help_button");
		}

	}


	@When("^.*(\\[Not\\] )? click_media_files_help_button$")
	public void click_media_files_help_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Media Files' help ? button
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getMediaFilesHelpIcon().Visible()  ;}}), Input.wait30);
				batchPrint.getMediaFilesHelpIcon().click();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT click_media_files_help_button");
		}

	}


	@And("^.*(\\[Not\\] )? login_as_rmu$")
	public void login_as_rmu(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Enter Username and password for Review Manager user
			//* User is logged in
			//* Sightline Home page is displayed
			//
			throw new ImplementationException("login_as_rmu");
		} else {
			throw new ImplementationException("NOT login_as_rmu");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_rmu_fields_in_slip_sheets$")
	public void verify_rmu_fields_in_slip_sheets(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4545 Verify that RMU can view the fields in 'Slip Sheets' if it is associated to the security groupThese Slip Sheets Metadata fields are not listed for RMU:
			//
			//* AnalyticsExceptions
			//* Audio_AgentID
			//* Audio_AgentName
			//* Audio_Appearance
			//
			//The only Slip Sheets Workproduct Tags fields listed for RMU:
			//
			//* All Tag > TagFor SG1
			//
			//The only Slip Sheets Workproduct Redaction Tags fields listed for RMU:
			//
			//* All Redaction Tags > SGSame1
			//* All Redaction Tags > SGSame2
			//
			throw new ImplementationException("verify_rmu_fields_in_slip_sheets");
		} else {
			throw new ImplementationException("NOT verify_rmu_fields_in_slip_sheets");
		}

	}


	@When("^.*(\\[Not\\] )? slip_sheets_disabled$")
	public void slip_sheets_disabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//'Enable Slip Sheets' toggle is disabled
			throw new ImplementationException("slip_sheets_disabled");
		} else {
			throw new ImplementationException("NOT slip_sheets_disabled");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_slip_sheets_disabled$")
	public void verify_slip_sheets_disabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4530 Verify that 'Enabled Slip Sheets' is OFF/ON then panel should be disabled
			//
			//* 'Do you want to use slip sheets of prior productions or create new slip sheets?' disabled
			//* 'Select Fields for Slip Sheets' disabled
			//
			throw new ImplementationException("verify_slip_sheets_disabled");
		} else {
			throw new ImplementationException("NOT verify_slip_sheets_disabled");
		}

	}


	@When("^.*(\\[Not\\] )? slip_sheets_enabled$")
	public void slip_sheets_enabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//'Enabled Slip Sheets' toggle is ON
			throw new ImplementationException("slip_sheets_enabled");
		} else {
			throw new ImplementationException("NOT slip_sheets_enabled");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_slip_sheets_enabled$")
	public void verify_slip_sheets_enabled(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4530 Verify that 'Enable Slip Sheets' is OFF/ON then panel should be disabled
			//
			//* 'Do you want to use slip sheets of prior productions or create new slip sheets?' enabled
			//* 'Select Fields for Slip Sheets' enabled
			//
			throw new ImplementationException("verify_slip_sheets_enabled");
		} else {
			throw new ImplementationException("NOT verify_slip_sheets_enabled");
		}

	}


	@When("^.*(\\[Not\\] )? click_select_search_radio_button$")
	public void click_select_search_radio_button(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Click 'Select Search' radio button on Source Selection tab
			throw new ImplementationException("click_select_search_radio_button");
		} else {
			throw new ImplementationException("NOT click_select_search_radio_button");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_my_shared_removed$")
	public void verify_my_shared_removed(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4398 Verify that 'My Shared' it should be removed from Batch Print
			//
			//* 'My Shared' is not a 'Select Search' option
			//
			throw new ImplementationException("verify_my_shared_removed");
		} else {
			throw new ImplementationException("NOT verify_my_shared_removed");
		}

	}


	@And("^.*(\\[Not\\] )? toggle_branding_redactions_$")
	public void toggle_branding_redactions_(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			//* Select Branding and Redactions
			//If branding_location is 'All' then add branding to Top Left, Top Center, Top Right, Bottom Left, Bottom Center, and Bottom Right options
			
			try {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getIncludeAppliedRedactionsToggle().Visible()  ;}}), Input.wait30);
				
				for (WebElement el : batchPrint.getAllBrandingToggleButtons().FindWebElements()) {
					el.click();
					// wait for branding location popup
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getBandingLocationPopup().Visible()  ;}}), Input.wait30);
					// wait for branding location text field
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   batchPrint.getBrandingLocationTextField().Visible()  ;}}), Input.wait30);
					// enter branding text
					batchPrint.getBrandingLocationTextField().Clear();
					batchPrint.getBrandingLocationTextField().sendKeys("test");
					
					// click OK button
					batchPrint.getInsertMetadataFieldOKButton().click();
					
					// wait until popup not visible
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							   !batchPrint.getBandingLocationPopup().Visible()  ;}}), Input.wait30);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			throw new ImplementationException("NOT toggle_branding_redactions_");
		}

	}


	@When("^.*(\\[Not\\] )? diff_branding_redaction_configs_set$")
	public void diff_branding_redaction_configs_set(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//Do nothing

		} else {
			throw new ImplementationException("NOT diff_branding_redaction_configs_set");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_include_applied_redactions_on_branding_redactions_tab$")
	public void verify_include_applied_redactions_on_branding_redactions_tab(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4514 Verify user can on/off the 'Include Applied Redactions' from Branding and Redactions tab
			//
			//* Verify user can toggle 'Include Applied Redactions' ON/OFF
			//* Regardless of 'Include Applied Redactions' status user can place Branding on the Top Left, Top Center, Top Right, Bottom Left, Bottom Center, and Bottom Right
			//
			
			try {
				// get class attribute value of 'Include Applied Redactions' button
				String attr = batchPrint.getOpaqueTransparentDiv().GetAttribute("style");
				
				// click button
				batchPrint.getIncludeAppliedRedactionsToggle().click();
				driver.waitForPageToBeReady();
				
				String attrAfterClick = batchPrint.getOpaqueTransparentDiv().GetAttribute("style");
				
				if (!attr.equalsIgnoreCase(attrAfterClick)) {
					pass(dataMap, "PASS! 'Include Applied Redactions' can be toggled on/off");
				} else fail(dataMap, "FAIL! Include Applied Redactions' cannot be toggled on/off");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT verify_include_applied_redactions_on_branding_redactions_tab");
		}

	}


	@And("^.*(\\[Not\\] )? click_branding_location$")
	public void click_branding_location(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			
			try {
				//Click branding position determined by 'branding_location'
				batchPrint.getTopCenterBrandingLocationButton().click();
				
				// wait for branding location popup
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						   batchPrint.getBandingLocationPopup().Visible()  ;}}), Input.wait30);

			} catch (Exception e) {
				e.printStackTrace();
			}

			
		} else {
			throw new ImplementationException("NOT click_branding_location");
		}

	}


	@When("^.*(\\[Not\\] )? click_insert_metadata_field_button_on_branding_redactions$")
	public void click_insert_metadata_field_button_on_branding_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   batchPrint.getInsertMetadataFieldLink().Visible()  ;}}), Input.wait30);
			batchPrint.getInsertMetadataFieldLink().click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   batchPrint.getInsertMetadataFieldPopup().Visible()  ;}}), Input.wait30);
		} else {
			throw new ImplementationException("NOT click_insert_metadata_field_button_on_branding_redactions");
		}

	}


	@Then("^.*(\\[Not\\] )? verify_metadata_displayed_on_branding_redactions$")
	public void verify_metadata_displayed_on_branding_redactions(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		if (scriptState) {
			//TC4403 Verify that Meta Data should be displayed if clicks on Insert Meta Data Link
			//
			//* Insert Metadata Field pops up
			//* Dropdown of metadata fields is displayed
			//
			try {
				if (batchPrint.getMetadataDropdown().Displayed()) {
					pass(dataMap, "PASS! Metadata Dropdown is displayed!");
				} else fail(dataMap, "FAIL! Metadata dropdown is not displayed!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ImplementationException("NOT verify_metadata_displayed_on_branding_redactions");
		}

	}
}//eof
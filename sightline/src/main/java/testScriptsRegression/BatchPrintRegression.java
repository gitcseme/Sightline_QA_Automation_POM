package testScriptsRegression;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.BatchPrintContext;

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class BatchPrintRegression extends RegressionBase {

	BatchPrintContext context = new BatchPrintContext();

	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_When_select_basis_for_printing_Then_verify_analysis_tab_details() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection When select_basis_for_printing Then verify_analysis_tab_details");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			context.select_basis_for_printing(true, dataMap);
			context.verify_analysis_tab_details(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_batch_print_page_Then_verify_saved_searches_on_source_selection_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau When on_batch_print_page Then verify_saved_searches_on_source_selection_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.verify_saved_searches_on_source_selection_tab(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"BatchPrint, Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_same_name_less_than_250_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_When_click_download_file_link_Then_verify_single_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_same_name_less_than_250 and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format When click_download_file_link Then verify_single_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			dataMap.put("select", "search");
			context.select_source_selection_same_name_less_than_250(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_single_pdf_generated(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_same_name_greater_than_250_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_and_select_export_format_When_click_download_file_link_Then_verify_second_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_same_name_greater_than_250 and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format and select_export_format When click_download_file_link Then verify_second_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("URL","http://mtpvtsslwb01.consilio.com/");
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			dataMap.put("select", "search");
			context.select_source_selection_same_name_greater_than_250(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "One PDF for each document");
			dataMap.put("A", "");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_second_pdf_generated(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_same_name_greater_than_250_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_and_select_export_format_When_click_download_file_link_Then_verify_separate_pdf_generated_for_every_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_same_name_greater_than_250 and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format and select_export_format When click_download_file_link Then verify_separate_pdf_generated_for_every_document");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			dataMap.put("select", "folder");
			context.select_source_selection_same_name_greater_than_250(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "1 PDF for each doc");
			dataMap.put("A", "");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format(true, dataMap);
			dataMap.put("pdf_creation", "One PDF for each document");
			dataMap.put("A", "");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_separate_pdf_generated_for_every_document(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_same_name_greater_than_250_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_When_click_download_file_link_Then_verify_second_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_same_name_greater_than_250 and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format When click_download_file_link Then verify_second_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			dataMap.put("select", "search");
			context.select_source_selection_same_name_greater_than_250(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "1 PDF for all docs");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_second_pdf_generated(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_redacted_docs_with_images_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_When_click_download_file_link_Then_verify_redactions_in_pdf() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_redacted_docs_with_images and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format When click_download_file_link Then verify_redactions_in_pdf");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			dataMap.put("select", "search");
			context.select_source_selection_redacted_docs_with_images(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "1 PDF for all docs");
			dataMap.put("sort_by", "MasterDate");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_redactions_in_pdf(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truetrue_When_select_export_format_Then_verify_batch_print_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{true} When select_export_format Then verify_batch_print_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Productions");
			dataMap.put("prior_production1", "Production Test 3");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("folder_all_skipped_documents", "false");
			dataMap.put("production", "Production Test 3");
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "true");
			context.select_slip_sheets_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.verify_batch_print_successful(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truefalse_When_select_export_format_Then_verify_batch_print_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{false} When select_export_format Then verify_batch_print_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Productions");
			dataMap.put("prior_production1", "Production Test 3");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("folder_all_skipped_documents", "false");
			dataMap.put("production", "Production Test 3");
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.verify_batch_print_successful(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truetrue_When_select_export_format_Then_verify_batch_print_with_prior_production_slips() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{true} When select_export_format Then verify_batch_print_with_prior_production_slips");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Productions");
			dataMap.put("prior_production1", "Production Test 3");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("folder_all_skipped_documents", "false");
			dataMap.put("production", "Production Test 3");
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "true");
			context.select_slip_sheets_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.verify_batch_print_with_prior_production_slips(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_When_select_basis_for_printing_Then_verify_production_document_from_analysis_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection When select_basis_for_printing Then verify_production_document_from_analysis_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Productions");
			dataMap.put("A", "");
			dataMap.put("prior_production1", "Production Test 3");
			dataMap.put("basis_for_production", "");
			dataMap.put("A", "");
			dataMap.put("prior_production1", "");
			context.select_basis_for_printing(true, dataMap);
			context.verify_production_document_from_analysis_tab(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truefalse_When_select_export_format_Then_verify_allproductionbatesranges_displayed_on_pdfs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{false} When select_export_format Then verify_allproductionbatesranges_displayed_on_pdfs");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Productions");
			dataMap.put("prior_production1", "Production Test 3");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("folder_all_skipped_documents", "false");
			dataMap.put("production", "Production Test 3");
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.verify_allproductionbatesranges_displayed_on_pdfs(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_When_select_slip_sheets_Then_verify_branding_redaction_tab_skipped() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis When select_slip_sheets Then verify_branding_redaction_tab_skipped");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Productions");
			dataMap.put("prior_production1", "Production Test 3");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("folder_all_skipped_documents", "false");
			dataMap.put("production", "Production Test 3");
			context.select_analysis(true, dataMap);
			context.select_slip_sheets(true, dataMap);
			context.verify_branding_redaction_tab_skipped(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_When_select_export_format_Then_verify_batch_print_when_exported_file_is_the_file_name_metadata_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets When select_export_format Then verify_batch_print_when_exported_file_is_the_file_name_metadata_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Productions");
			dataMap.put("prior_production1", "Production Test 3");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("folder_all_skipped_documents", "false");
			dataMap.put("production", "Production Test 3");
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets1", "CreateDate");
			dataMap.put("field_for_slip_sheets3", "CustomDocId");
			dataMap.put("field_for_slip_sheets2", "CustodianName");
			context.select_slip_sheets(true, dataMap);
			context.select_export_format(true, dataMap);
			context.verify_batch_print_when_exported_file_is_the_file_name_metadata_field(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_folder_When_select_another_folder_Then_verify_user_can_select_only_one_source() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_folder When select_another_folder Then verify_user_can_select_only_one_source");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_folder(true, dataMap);
			context.select_another_folder(true, dataMap);
			context.verify_user_can_select_only_one_source(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_tag_When_select_another_tag_Then_verify_user_can_select_only_one_source() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_tag When select_another_tag Then verify_user_can_select_only_one_source");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_tag(true, dataMap);
			context.select_another_tag(true, dataMap);
			context.verify_user_can_select_only_one_source(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_search_When_select_another_search_Then_verify_user_can_select_only_one_source() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_search When select_another_search Then verify_user_can_select_only_one_source");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			dataMap.put("search_folder", "a");
			context.select_search(true, dataMap);
			context.select_another_search(true, dataMap);
			context.verify_user_can_select_only_one_source(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_folder_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_trueopaquecenter_and_select_export_format_When_click_download_file_link_Then_verify_batch_print_with_source_folder() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_folder and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions_{true}{opaque}{center} and select_export_format When click_download_file_link Then verify_batch_print_with_source_folder");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_folder(true, dataMap);
			dataMap.put("basis_for_printing", "Natives");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets1", "Metadata");
			dataMap.put("field_for_slip_sheets2", "Workproduct");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_batch_print_with_source_folder(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_folder_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_truetransparentcenter_and_select_export_format_When_click_download_file_link_Then_verify_batch_print_with_source_folder() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_folder and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions_{true}{transparent}{center} and select_export_format When click_download_file_link Then verify_batch_print_with_source_folder");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_folder(true, dataMap);
			dataMap.put("basis_for_printing", "Natives");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets1", "Metadata");
			dataMap.put("field_for_slip_sheets2", "Workproduct");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "transparent");
			context.select_branding_redactions_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_batch_print_with_source_folder(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_tag_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_trueopaquecenter_and_select_export_format_When_click_download_file_link_Then_verify_batch_print_with_source_tag() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_tag and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions_{true}{opaque}{center} and select_export_format When click_download_file_link Then verify_batch_print_with_source_tag");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_tag(true, dataMap);
			dataMap.put("basis_for_printing", "Natives");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets1", "Metadata");
			dataMap.put("field_for_slip_sheets2", "Workproduct");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_batch_print_with_source_tag(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_tag_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_truetransparentcenter_and_select_export_format_When_click_download_file_link_Then_verify_batch_print_with_source_tag() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_tag and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions_{true}{transparent}{center} and select_export_format When click_download_file_link Then verify_batch_print_with_source_tag");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_tag(true, dataMap);
			dataMap.put("basis_for_printing", "Natives");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets1", "Metadata");
			dataMap.put("field_for_slip_sheets2", "Workproduct");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "transparent");
			context.select_branding_redactions_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_batch_print_with_source_tag(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_search_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_trueopaquecenter_and_select_export_format_When_click_download_file_link_Then_verify_batch_print_with_source_search() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_search and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions_{true}{opaque}{center} and select_export_format When click_download_file_link Then verify_batch_print_with_source_search");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			dataMap.put("search_folder", "a");
			context.select_search(true, dataMap);
			dataMap.put("basis_for_printing", "Natives");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets1", "Metadata");
			dataMap.put("field_for_slip_sheets2", "Workproduct");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_batch_print_with_source_search(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_search_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_truetransparentcenter_and_select_export_format_When_click_download_file_link_Then_verify_batch_print_with_source_search() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_search and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions_{true}{transparent}{center} and select_export_format When click_download_file_link Then verify_batch_print_with_source_search");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			dataMap.put("search_folder", "a");
			context.select_search(true, dataMap);
			dataMap.put("basis_for_printing", "Natives");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets1", "Metadata");
			dataMap.put("field_for_slip_sheets2", "Workproduct");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "transparent");
			context.select_branding_redactions_(true, dataMap);
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			context.verify_batch_print_with_source_search(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_batch_print_page_Then_verify_source_selection_help_question_mark() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau When on_batch_print_page Then verify_source_selection_help_question_mark");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.verify_source_selection_help_question_mark(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
}//eof
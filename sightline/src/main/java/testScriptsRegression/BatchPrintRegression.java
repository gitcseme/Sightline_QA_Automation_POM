package testScriptsRegression;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.google.protobuf.Empty;
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


	@Test(groups = {"BatchPrint, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_batch_print_page_Then_verify_saved_searches_on_source_selection_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau When on_batch_print_page Then verify_saved_searches_on_source_selection_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "4396");
			dataMap.put("uid", "qapau2@consilio.com");
			dataMap.put("pwd", "Q@test_10");
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

	@Test(groups = {"BatchPrint, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_same_name_less_than_250_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_When_click_download_file_link_Then_verify_single_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_same_name_less_than_250 and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format When click_download_file_link Then verify_single_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "11816");
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


	@Test(groups = {"BatchPrint, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_same_name_greater_than_250_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_and_select_export_format_When_click_download_file_link_Then_verify_second_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_same_name_greater_than_250 and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format and select_export_format When click_download_file_link Then verify_second_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "11816|11817");
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


	@Test(groups = {"BatchPrint, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_same_name_greater_than_250_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_and_select_export_format_When_click_download_file_link_Then_verify_separate_pdf_generated_for_every_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_same_name_greater_than_250 and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format and select_export_format When click_download_file_link Then verify_separate_pdf_generated_for_every_document");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "11817");
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


	@Test(groups = {"BatchPrint, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_same_name_greater_than_250_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_When_click_download_file_link_Then_verify_second_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_same_name_greater_than_250 and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format When click_download_file_link Then verify_second_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "11816|11817");
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_tag_with_0_docs_When_select_basis_for_printing_Then_verify_analysis_0_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_tag_with_0_docs When select_basis_for_printing Then verify_analysis_0_docs");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_tag_with_0_docs(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			dataMap.put("A", "");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9707");
			context.verify_analysis_0_docs(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_folder_with_0_docs_When_select_basis_for_printing_Then_verify_analysis_0_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_folder_with_0_docs When select_basis_for_printing Then verify_analysis_0_docs");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_folder_with_0_docs(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9707");
			context.verify_analysis_0_docs(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "smoke7"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_Native_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_2_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{Native} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{2} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "2");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "LastEditDate");
			dataMap.put("sort_by_order", "desc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint", "Positive", "smoke7"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_Native_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_3_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{Native} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{3} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "3");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "DocDate");
			dataMap.put("sort_by_order", "desc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "smoke7"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_Native_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_4_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{Native} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{4} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "4");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "DocFileName");
			dataMap.put("sort_by_order", "asc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_5_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{5} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "5");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "MasterDateTime");
			dataMap.put("sort_by_order", "asc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_6_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{6} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "6");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "CreatedDateTime");
			dataMap.put("sort_by_order", "desc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_7_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{7} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "7");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "SendDateTime");
			dataMap.put("sort_by_order", "asc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_8_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{8} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "8");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "LastSaveDate");
			dataMap.put("sort_by_order", "asc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_9_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{9} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "9");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "LastModifiedDate");
			dataMap.put("sort_by_order", "desc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_10_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{10} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "10");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "LastEditDate");
			dataMap.put("sort_by_order", "asc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive","Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_11_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{11} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "11");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "DocDate");
			dataMap.put("sort_by_order", "asc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_12_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{12} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "12");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "CustodianName");
			dataMap.put("sort_by_order", "asc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_13_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{13} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "13");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "DocFileName");
			dataMap.put("sort_by_order", "desc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_PriorProduction_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_14_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{PriorProduction} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{14} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "14");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "DocID");
			dataMap.put("sort_by_order", "asc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "smoke7"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_Native_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_1_When_click_download_file_link_Then_verify_pdf_file_sorted_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing_{Native} and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format_{1} When click_download_file_link Then verify_pdf_file_sorted_correctly");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "1");
			dataMap.put("pdf_creation", "One PDF for all documents");
			dataMap.put("sort_by", "LastSaveDate");
			dataMap.put("sort_by_order", "desc");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "8103|8105|8107|8166|8167|8169|8170|8171|8174|8175|8177|8178|8179|8181");
			context.verify_pdf_file_sorted_correctly(true, dataMap);
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


	@Test(groups = {"BatchPrint, Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_When_click_download_file_link_Then_verify_production_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions and select_export_format When click_download_file_link Then verify_production_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "true");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4563");
			context.verify_production_pdf_generated(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_with_redactions_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_2_When_click_download_file_link_Then_verify_pdf_generated_with_redactions() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_with_redactions and select_basis_for_printing and select_analysis and select_slip_sheets and select_branding_redactions and select_export_format_{2} When click_download_file_link Then verify_pdf_generated_with_redactions");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection_with_redactions(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "2");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4651|4658|4659");
			context.verify_pdf_generated_with_redactions(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_with_redactions_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_3_When_click_download_file_link_Then_verify_pdf_generated_with_redactions() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_with_redactions and select_basis_for_printing and select_analysis and select_slip_sheets and select_branding_redactions and select_export_format_{3} When click_download_file_link Then verify_pdf_generated_with_redactions");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection_with_redactions(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "3");
			dataMap.put("export_file_name", "Begin Bates");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4651|4658|4659");
			context.verify_pdf_generated_with_redactions(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_with_redactions_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_1_When_click_download_file_link_Then_verify_pdf_generated_with_redactions() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_with_redactions and select_basis_for_printing and select_analysis and select_slip_sheets and select_branding_redactions and select_export_format_{1} When click_download_file_link Then verify_pdf_generated_with_redactions");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection_with_redactions(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "1");
			dataMap.put("export_file_name", "DocID");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4651|4658|4659");
			context.verify_pdf_generated_with_redactions(true, dataMap);
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

	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truetrue_and_select_branding_redactions_and_select_export_format_1_When_click_download_file_link_Then_verify_pdf_from_selected_production_slipsheet_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{true} and select_branding_redactions and select_export_format_{1} When click_download_file_link Then verify_pdf_from_selected_production_slipsheet_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "");
			dataMap.put("use_prior_production", "true");
			dataMap.put("field_for_slip_sheets", "");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "1");
			dataMap.put("export_file_name", "DocID");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4648|4650|4653|4654|4655|4656");
			context.verify_pdf_from_selected_production_slipsheet_field(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truetrue_and_select_branding_redactions_and_select_export_format_2_When_click_download_file_link_Then_verify_pdf_from_selected_production_slipsheet_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{true} and select_branding_redactions and select_export_format_{2} When click_download_file_link Then verify_pdf_from_selected_production_slipsheet_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "");
			dataMap.put("use_prior_production", "true");
			dataMap.put("field_for_slip_sheets", "");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "2");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4648|4650|4653|4654|4655|4656");
			context.verify_pdf_from_selected_production_slipsheet_field(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truetrue_and_select_branding_redactions_and_select_export_format_3_When_click_download_file_link_Then_verify_pdf_from_selected_production_slipsheet_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{true} and select_branding_redactions and select_export_format_{3} When click_download_file_link Then verify_pdf_from_selected_production_slipsheet_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "");
			dataMap.put("use_prior_production", "true");
			dataMap.put("field_for_slip_sheets", "");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "3");
			dataMap.put("export_file_name", "BatesNumber");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4648|4650|4653|4654|4655|4656");
			context.verify_pdf_from_selected_production_slipsheet_field(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truetrue_and_select_branding_redactions_and_select_export_format_4_When_click_download_file_link_Then_verify_pdf_from_selected_production_slipsheet_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{true} and select_branding_redactions and select_export_format_{4} When click_download_file_link Then verify_pdf_from_selected_production_slipsheet_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "");
			dataMap.put("use_prior_production", "true");
			dataMap.put("field_for_slip_sheets", "");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "4");
			dataMap.put("export_file_name", "Begin Bates");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4648|4650|4653|4654|4655|4656");
			context.verify_pdf_from_selected_production_slipsheet_field(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_1_When_click_download_file_link_Then_verify_pdf_from_selected_production_slipsheet_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{1} When click_download_file_link Then verify_pdf_from_selected_production_slipsheet_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "1");
			dataMap.put("export_file_name", "DocID");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4648|4650|4653|4654|4655|4656");
			context.verify_pdf_from_selected_production_slipsheet_field(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_2_When_click_download_file_link_Then_verify_pdf_from_selected_production_slipsheet_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{2} When click_download_file_link Then verify_pdf_from_selected_production_slipsheet_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "2");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4648|4650|4653|4654|4655|4656");
			context.verify_pdf_from_selected_production_slipsheet_field(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_3_When_click_download_file_link_Then_verify_pdf_from_selected_production_slipsheet_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{3} When click_download_file_link Then verify_pdf_from_selected_production_slipsheet_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "3");
			dataMap.put("export_file_name", "BatesNumber");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4648|4650|4653|4654|4655|4656");
			context.verify_pdf_from_selected_production_slipsheet_field(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "WIP"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_4_When_click_download_file_link_Then_verify_pdf_from_selected_production_slipsheet_field() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{4} When click_download_file_link Then verify_pdf_from_selected_production_slipsheet_field");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio10@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Prior Production");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("index", "4");
			dataMap.put("export_file_name", "Begin Bates");
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4648|4650|4653|4654|4655|4656");
			context.verify_pdf_from_selected_production_slipsheet_field(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_tag_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_DocID_When_click_download_file_link_Then_verify_selected_slipsheet_fields_for_selected_tag() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_tag and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{DocID} When click_download_file_link Then verify_selected_slipsheet_fields_for_selected_tag");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
		    dataMap.put("uid", "sqa.consilio10@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_tag(true, dataMap);
			//dataMap.put("basis_for_production", "Native");
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("C", "1");
			dataMap.put("export_file_name", "DocID");
			context.select_export_format(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9710|9711");
			context.verify_selected_slipsheet_fields_for_selected_tag(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive" ,"WIP"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_tag_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_DocFileName_When_click_download_file_link_Then_verify_selected_slipsheet_fields_for_selected_tag() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_tag and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{DocFileName} When click_download_file_link Then verify_selected_slipsheet_fields_for_selected_tag");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_tag(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("C", "2");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9710|9711");
			context.verify_selected_slipsheet_fields_for_selected_tag(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_folder_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_DocID_When_click_download_file_link_Then_verify_selected_branding_redactions_for_selected_folder() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_folder and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{DocID} When click_download_file_link Then verify_selected_branding_redactions_for_selected_folder");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_folder(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("C", "1");
			dataMap.put("export_file_name", "DocID");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9716|9717");
			context.verify_selected_branding_redactions_for_selected_folder(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_folder_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_DocFileName_When_click_download_file_link_Then_verify_selected_branding_redactions_for_selected_folder() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_folder and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{DocFileName} When click_download_file_link Then verify_selected_branding_redactions_for_selected_folder");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_folder(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("C", "2");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9716|9717");
			context.verify_selected_branding_redactions_for_selected_folder(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_tag_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_DocID_When_click_download_file_link_Then_verify_selected_branding_redactions_for_selected_tag() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_tag and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{DocID} When click_download_file_link Then verify_selected_branding_redactions_for_selected_tag");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_tag(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("C", "1");
			dataMap.put("export_file_name", "DocID");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9714|9715");
			context.verify_selected_branding_redactions_for_selected_tag(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_tag_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_DocFileName_When_click_download_file_link_Then_verify_selected_branding_redactions_for_selected_tag() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_tag and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{DocFileName} When click_download_file_link Then verify_selected_branding_redactions_for_selected_tag");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_tag(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("C", "2");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9714|9715");
			context.verify_selected_branding_redactions_for_selected_tag(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_folder_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_DocID_When_click_download_file_link_Then_verify_selected_slipsheet_fields_for_selected_folder() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_folder and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{DocID} When click_download_file_link Then verify_selected_slipsheet_fields_for_selected_folder");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_folder(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("C", "1");
			dataMap.put("export_file_name", "DocID");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9712|9713");
			context.verify_selected_slipsheet_fields_for_selected_folder(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_folder_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_truefalse_and_select_branding_redactions_and_select_export_format_DocFileName_When_click_download_file_link_Then_verify_selected_slipsheet_fields_for_selected_folder() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_folder and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets_{true}{false} and select_branding_redactions and select_export_format_{DocFileName} When click_download_file_link Then verify_selected_slipsheet_fields_for_selected_folder");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_folder(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("C", "2");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9712|9713");
			context.verify_selected_slipsheet_fields_for_selected_folder(true, dataMap);
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

	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_select_branding_redactions_When_select_export_format_Then_verify_notification_displayed_when_background_process_initialized() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and select_branding_redactions When select_export_format Then verify_notification_displayed_when_background_process_initialized");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
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
			dataMap.put("pdf_creation", "1 PDF for each document");
			dataMap.put("sort_by", "DocID");
			dataMap.put("A", "");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4561|4562");
			context.verify_notification_displayed_when_background_process_initialized(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_When_click_source_selection_back_button_Then_verify_directed_to_source_selection_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection When click_source_selection_back_button Then verify_directed_to_source_selection_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			context.click_source_selection_back_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4387");
			context.verify_directed_to_source_selection_tab(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint", "Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_When_select_basis_for_printing_Then_verify_prior_productions_radio_button() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection When select_basis_for_printing Then verify_prior_productions_radio_button");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Prior Production");
			dataMap.put("A", "");
			dataMap.put("prior_production1", "SQA Production Template");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4450");
			context.verify_prior_productions_radio_button(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_When_select_skip_excel_files_radio_button_Then_verify_skip_excel_files_on_exception_file_types_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis When select_skip_excel_files_radio_button Then verify_skip_excel_files_on_exception_file_types_tab");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "sqa.consilio8@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			context.select_skip_excel_files_radio_button(true, dataMap);
			context.verify_skip_excel_files_on_exception_file_types_tab(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_When_click_other_exception_file_types_help_button_Then_verify_help_displayed_on_exception_file_types_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis When click_other_exception_file_types_help_button Then verify_help_displayed_on_exception_file_types_tab");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "sqa.consilio8@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			context.click_other_exception_file_types_help_button(true, dataMap);
			context.verify_help_displayed_on_exception_file_types_tab(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_When_click_excel_files_help_button_Then_verify_help_displayed_on_exception_file_types_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis When click_excel_files_help_button Then verify_help_displayed_on_exception_file_types_tab");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "sqa.consilio8@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			context.click_excel_files_help_button(true, dataMap);
			context.verify_help_displayed_on_exception_file_types_tab(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_When_click_media_files_help_button_Then_verify_help_displayed_on_exception_file_types_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis When click_media_files_help_button Then verify_help_displayed_on_exception_file_types_tab");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "sqa.consilio8@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			context.click_media_files_help_button(true, dataMap);
			context.verify_help_displayed_on_exception_file_types_tab(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_When_select_exception_file_types_Then_verify_rmu_fields_in_slip_sheets() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis When select_exception_file_types Then verify_rmu_fields_in_slip_sheets");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio9@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4545");
			context.verify_rmu_fields_in_slip_sheets(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_When_slip_sheets_disabled_Then_verify_slip_sheets_disabled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis and select_exception_file_types When slip_sheets_disabled Then verify_slip_sheets_disabled");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "sqa.consilio8@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			context.slip_sheets_disabled(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4530");
			context.verify_slip_sheets_disabled(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_When_slip_sheets_enabled_Then_verify_slip_sheets_enabled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis and select_exception_file_types When slip_sheets_enabled Then verify_slip_sheets_enabled");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "sqa.consilio8@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			context.slip_sheets_enabled(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4530");
			context.verify_slip_sheets_enabled(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "smoke"})
	public void test_Given_login_to_new_batch_print_When_click_select_search_radio_button_Then_verify_my_shared_removed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print When click_select_search_radio_button Then verify_my_shared_removed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print(true, dataMap);
			context.click_select_search_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4398");
			context.verify_my_shared_removed(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_toggle_branding_redactions_trueopaqueAll_When_diff_branding_redaction_configs_set_Then_verify_include_applied_redactions_on_branding_redactions_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and toggle_branding_redactions_{true}{opaque}{All} When diff_branding_redaction_configs_set Then verify_include_applied_redactions_on_branding_redactions_tab");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "sqa.consilio7@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "All");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.toggle_branding_redactions_(true, dataMap);
			context.diff_branding_redaction_configs_set(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4514");
			context.verify_include_applied_redactions_on_branding_redactions_tab(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_toggle_branding_redactions_falseopaqueAll_When_diff_branding_redaction_configs_set_Then_verify_include_applied_redactions_on_branding_redactions_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and toggle_branding_redactions_{false}{opaque}{All} When diff_branding_redaction_configs_set Then verify_include_applied_redactions_on_branding_redactions_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "All");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.toggle_branding_redactions_(true, dataMap);
			context.diff_branding_redaction_configs_set(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4514");
			context.verify_include_applied_redactions_on_branding_redactions_tab(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Regression"})
	public void test_Given_login_to_new_batch_print_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_exception_file_types_and_select_slip_sheets_and_click_TopCenter_branding_location_When_click_insert_metadata_field_button_on_branding_redactions_Then_verify_metadata_displayed_on_branding_redactions() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print and select_source_selection and select_basis_for_printing and select_analysis and select_exception_file_types and select_slip_sheets and click_{TopCenter}_branding_location When click_insert_metadata_field_button_on_branding_redactions Then verify_metadata_displayed_on_branding_redactions");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "sqa.consilio7@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_new_batch_print(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			dataMap.put("B", "Natives");
			context.select_analysis(true, dataMap);
			dataMap.put("excel_files", "print");
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "Top Center");
			context.click_branding_location(true, dataMap);
			context.click_insert_metadata_field_button_on_branding_redactions(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4403");
			context.verify_metadata_displayed_on_branding_redactions(true, dataMap);
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


	@Test(groups = {"BatchPrint, Positive", "Regression"}) //Fails with known issue with sorting
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_DocFileNameDocIDAscOnePDFforeachdoc_When_click_download_file_link_Then_verify_native_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets and select_branding_redactions and select_export_format_{DocFileName}{DocID}{Asc}{OnePDFforeachdoc} When click_download_file_link Then verify_native_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_printing", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "One PDF for each doc");
			dataMap.put("sort_by", "DocID");
			dataMap.put("sort_by_order", "Asc");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4539|4540|4541|4543|4544|4506");
			context.verify_native_pdf_generated(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"BatchPrint, Positive", "Regression"}) //Fails with known issue with sorting
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_DocFileNameDocIDAscOnePDFforalldocs_When_click_download_file_link_Then_verify_native_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets and select_branding_redactions and select_export_format_{DocFileName}{DocID}{Asc}{OnePDFforalldocs} When click_download_file_link Then verify_native_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "One PDF for all docs");
			dataMap.put("sort_by", "DocID");
			dataMap.put("sort_by_order", "Asc");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4539|4540|4541|4543|4544|4506");
			context.verify_native_pdf_generated(true, dataMap);
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


	@Test(groups = {"BatchPrint, Positive", "Regression"}) //Fails with known issue with sorting
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_DocFileNameDocIDDescOnePDFforeachdoc_When_click_download_file_link_Then_verify_native_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets and select_branding_redactions and select_export_format_{DocFileName}{DocID}{Desc}{OnePDFforeachdoc} When click_download_file_link Then verify_native_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "One PDF for each doc");
			dataMap.put("sort_by", "DocID");
			dataMap.put("sort_by_order", "Desc");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4539|4540|4541|4543|4544|4506");
			context.verify_native_pdf_generated(true, dataMap);
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


	@Test(groups = {"BatchPrint, Positive", "Regression"}) //Fails with known issue with sorting 
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_DocFileNameDocFileNameAscOnePDFforeachdoc_When_click_download_file_link_Then_verify_native_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets and select_branding_redactions and select_export_format_{DocFileName}{DocFileName}{Asc}{OnePDFforeachdoc} When click_download_file_link Then verify_native_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");

			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "One PDF for each doc");
			dataMap.put("sort_by", "DocFileName");
			dataMap.put("sort_by_order", "Asc");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4539|4540|4541|4543|4544|4506");
			context.verify_native_pdf_generated(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive", "Regression"}) //Fails with known issue with sorting
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_and_select_basis_for_printing_and_select_analysis_and_select_slip_sheets_and_select_branding_redactions_and_select_export_format_DocFileNameDocFileNameDescOnePDFforeachdoc_When_click_download_file_link_Then_verify_native_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection and select_basis_for_printing and select_analysis and select_slip_sheets and select_branding_redactions and select_export_format_{DocFileName}{DocFileName}{Desc}{OnePDFforeachdoc} When click_download_file_link Then verify_native_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");

			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection(true, dataMap);
			dataMap.put("basis_for_production", "Native");
			context.select_basis_for_printing(true, dataMap);
			context.select_analysis(true, dataMap);
			context.select_exception_file_types(true, dataMap);
			dataMap.put("enable_slip_sheets", "true");
			dataMap.put("workproduct_for_slipsheets", "Default Automation Folder");
			dataMap.put("use_prior_production", "false");
			dataMap.put("field_for_slip_sheets", "AllProductionBatesRanges");
			context.select_slip_sheets(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "true");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions(true, dataMap);
			dataMap.put("pdf_creation", "One PDF for each doc");
			dataMap.put("sort_by", "DocFileName");
			dataMap.put("sort_by_order", "Desc");
			dataMap.put("export_file_name", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4539|4540|4541|4543|4544|4506");
			context.verify_native_pdf_generated(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_pau_When_click_select_search_radio_button_Then_verify_deleted_search_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_pau When click_select_search_radio_button Then verify_deleted_search_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_pau(true, dataMap);
			context.click_select_search_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4443");
			context.verify_deleted_search_not_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_rmu_When_click_select_folder_radio_button_Then_verify_folder_only_in_security_group_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_rmu When click_select_folder_radio_button Then verify_folder_only_in_security_group_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_rmu(true, dataMap);
			context.click_select_folder_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9718");
			context.verify_folder_only_in_security_group_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_pau_When_click_select_folder_radio_button_Then_verify_deleted_folder_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_pau When click_select_folder_radio_button Then verify_deleted_folder_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_pau(true, dataMap);
			context.click_select_folder_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9722");
			context.verify_deleted_folder_not_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_rmu_When_click_select_folder_radio_button_Then_verify_folder_in_multiple_security_groups_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_rmu When click_select_folder_radio_button Then verify_folder_in_multiple_security_groups_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_rmu(true, dataMap);
			context.click_select_folder_radio_button(true, dataMap);
			dataMap.put("TestCase", "9719|9720");
			context.verify_folder_in_multiple_security_groups_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_pau_When_click_select_folder_radio_button_Then_verify_folder_in_multiple_security_groups_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_pau When click_select_folder_radio_button Then verify_folder_in_multiple_security_groups_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_pau(true, dataMap);
			context.click_select_folder_radio_button(true, dataMap);
			dataMap.put("TestCase", "9719|9720");
			context.verify_folder_in_multiple_security_groups_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_rmu_When_click_select_folder_radio_button_Then_verify_unmapped_folder_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_rmu When click_select_folder_radio_button Then verify_unmapped_folder_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_rmu(true, dataMap);
			context.click_select_folder_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9721");
			context.verify_unmapped_folder_not_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_rmu_When_click_select_folder_radio_button_Then_verify_deleted_folder_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_rmu When click_select_folder_radio_button Then verify_deleted_folder_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_rmu(true, dataMap);
			context.click_select_folder_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9722");
			context.verify_deleted_folder_not_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_pau_When_click_select_tag_radio_button_Then_verify_tag_in_multiple_security_groups_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_pau When click_select_tag_radio_button Then verify_tag_in_multiple_security_groups_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_pau(true, dataMap);
			context.click_select_tag_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9719|9720");
			context.verify_tag_in_multiple_security_groups_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_pau_When_click_select_tag_radio_button_Then_verify_deleted_tag_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_pau When click_select_tag_radio_button Then verify_deleted_tag_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_pau(true, dataMap);
			context.click_select_tag_radio_button(true, dataMap);
			dataMap.put("TestCase", "9722");
			context.verify_deleted_tag_not_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_rmu_When_click_select_tag_radio_button_Then_verify_unmapped_tag_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_rmu When click_select_tag_radio_button Then verify_unmapped_tag_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_rmu(true, dataMap);
			context.click_select_tag_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9721");
			context.verify_unmapped_tag_not_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_rmu_When_click_select_tag_radio_button_Then_verify_tag_only_in_security_group_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_rmu When click_select_tag_radio_button Then verify_tag_only_in_security_group_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_rmu(true, dataMap);
			context.click_select_tag_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9718");
			context.verify_tag_only_in_security_group_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_rmu_When_click_select_tag_radio_button_Then_verify_deleted_tag_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_rmu When click_select_tag_radio_button Then verify_deleted_tag_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_rmu(true, dataMap);
			context.click_select_tag_radio_button(true, dataMap);
			dataMap.put("TestCase", "9722");
			context.verify_deleted_tag_not_displayed(true, dataMap);
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


	@Test(groups = {"BatchPrint", "Positive"})
	public void test_Given_login_to_new_batch_print_rmu_When_click_select_tag_radio_button_Then_verify_tag_in_multiple_security_groups_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_batch_print_rmu When click_select_tag_radio_button Then verify_tag_in_multiple_security_groups_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_batch_print_rmu(true, dataMap);
			context.click_select_tag_radio_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "9719|9720");
			context.verify_tag_in_multiple_security_groups_displayed(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_batch_print_page_and_select_source_selection_500_plus_documents_and_select_basis_for_printing_Natives_and_select_analysis_and_select_exception_file_types_falseprintfalse_and_select_slip_sheets_enabled_slip_sheets_and_select_branding_redactions_false_and_select_export_format_DocFileNameDocID1PDFforalldocs_When_click_download_file_link_Then_verify_generating_single_pdf_for_500_plus_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_batch_print_page and select_source_selection_500_plus_documents and select_basis_for_printing_{Natives} and select_analysis and select_exception_file_types_{false}{print}{false} and select_slip_sheets_{enabled_slip_sheets} and select_branding_redactions_{false} and select_export_format_{DocFileName}{DocID}{1PDFforalldocs} When click_download_file_link Then verify_generating_single_pdf_for_500_plus_docs");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection_500_plus_documents(true, dataMap);
			dataMap.put("basis_for_printing", "Natives");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("other_exception_file_types", "false");
			dataMap.put("excel_files", "print");
			dataMap.put("media_files", "false");
			context.select_exception_file_types_(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions_(true, dataMap);
			dataMap.put("pdf_creation", "1 PDF for all docs");
			dataMap.put("sort_by", "DocID");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "7924|7925|7926");
			context.verify_generating_single_pdf_for_500_plus_docs(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_batch_print_page_and_select_source_selection_500_plus_documents_and_select_basis_for_printing_Natives_and_select_analysis_and_select_exception_file_types_falseprintfalse_and_select_slip_sheets_enabled_slip_sheets_and_select_branding_redactions_false_and_select_export_format_DocFileNameDocID1PDFforalldocs_When_click_download_file_link_Then_verify_generating_single_pdf_for_500_plus_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_batch_print_page and select_source_selection_500_plus_documents and select_basis_for_printing_{Natives} and select_analysis and select_exception_file_types_{false}{print}{false} and select_slip_sheets_{enabled_slip_sheets} and select_branding_redactions_{false} and select_export_format_{DocFileName}{DocID}{1PDFforalldocs} When click_download_file_link Then verify_generating_single_pdf_for_500_plus_docs");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Project Administrator");
			dataMap.put("domain", "Not a Domain");
			context.login_as_pau(true, dataMap);
			context.on_batch_print_page(true, dataMap);
			context.select_source_selection_500_plus_documents(true, dataMap);
			dataMap.put("basis_for_printing", "Natives");
			context.select_basis_for_printing_(true, dataMap);
			context.select_analysis(true, dataMap);
			dataMap.put("other_exception_file_types", "false");
			dataMap.put("excel_files", "print");
			dataMap.put("media_files", "false");
			context.select_exception_file_types_(true, dataMap);
			dataMap.put("enable_slip_sheets", "false");
			context.select_slip_sheets_(true, dataMap);
			dataMap.put("branding_location", "center");
			dataMap.put("include_applied_redactions", "false");
			dataMap.put("opaque_transparent", "opaque");
			context.select_branding_redactions_(true, dataMap);
			dataMap.put("pdf_creation", "1 PDF for all docs");
			dataMap.put("sort_by", "DocID");
			dataMap.put("export_by", "DocFileName");
			context.select_export_format_(true, dataMap);
			context.click_download_file_link(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "7924|7925|7926");
			context.verify_generating_single_pdf_for_500_plus_docs(true, dataMap);
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
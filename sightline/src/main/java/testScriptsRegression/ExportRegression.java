package testScriptsRegression;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ExportContext;
import stepDef.ImplementationException;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked" })
public class ExportRegression extends RegressionBase {
	
	ExportContext context = new ExportContext();

	@Test(groups = {"Export", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_export_process_Then_verify_export_dat_component_removed_advanced_option() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_export_process Then verify_export_dat_component_removed_advanced_option");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_dat_component_removed_advanced_option(true, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_export_process_Then_Not_verify_export_dat_component_removed_advanced_option() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_export_process Then [Not] verify_export_dat_component_removed_advanced_option");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_dat_component_removed_advanced_option(false, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_export_process_Then_Not_verify_export_dat_component_removed_advanced_option() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_export_process Then [Not] verify_export_dat_component_removed_advanced_option");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_dat_component_removed_advanced_option(false, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_export_process_Then_Not_verify_export_dat_component_removed_advanced_option() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_export_process Then [Not] verify_export_dat_component_removed_advanced_option");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_dat_component_removed_advanced_option(false, dataMap);
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


	@Test(groups = {"Export", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_export_process_Then_verify_export_native_component_section() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_export_process Then verify_export_native_component_section");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_native_component_section(true, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_export_process_Then_Not_verify_export_native_component_section() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_export_process Then [Not] verify_export_native_component_section");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_native_component_section(false, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_export_process_Then_Not_verify_export_native_component_section() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_export_process Then [Not] verify_export_native_component_section");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_native_component_section(false, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_export_process_Then_Not_verify_export_native_component_section() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_export_process Then [Not] verify_export_native_component_section");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_native_component_section(false, dataMap);
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


	@Test(groups = {"Export", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_export_process_Then_verify_export_text_component_changes() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_export_process Then verify_export_text_component_changes");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_text_component_changes(true, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_export_process_Then_Not_verify_export_text_component_changes() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_export_process Then [Not] verify_export_text_component_changes");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_text_component_changes(false, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_export_process_Then_Not_verify_export_text_component_changes() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_export_process Then [Not] verify_export_text_component_changes");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_text_component_changes(false, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_export_process_Then_Not_verify_export_text_component_changes() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_export_process Then [Not] verify_export_text_component_changes");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_text_component_changes(false, dataMap);
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


	@Test(groups = {"Export", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_export_process_Then_verify_export_tiff_pdf_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_export_process Then verify_export_tiff_pdf_advanced_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_tiff_pdf_advanced_options(true, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_export_process_Then_Not_verify_export_tiff_pdf_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_export_process Then [Not] verify_export_tiff_pdf_advanced_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_tiff_pdf_advanced_options(false, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_export_process_Then_Not_verify_export_tiff_pdf_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_export_process Then [Not] verify_export_tiff_pdf_advanced_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_tiff_pdf_advanced_options(false, dataMap);
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


	@Test(groups = {"Export", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_export_process_Then_Not_verify_export_tiff_pdf_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_export_process Then [Not] verify_export_tiff_pdf_advanced_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("export_template", "false");
			dataMap.put("A", "");
			dataMap.put("prior_prod", "false");
			context.begin_new_export_process(true, dataMap);
			context.verify_export_tiff_pdf_advanced_options(false, dataMap);
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
}

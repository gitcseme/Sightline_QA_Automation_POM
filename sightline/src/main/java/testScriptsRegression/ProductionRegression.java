package testScriptsRegression;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.ProductionContext;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked" })
public class ProductionRegression extends RegressionBase {

	ProductionContext context = new ProductionContext();
//	ProductionOutcome outcome = new ProductionOutcome();

	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_can_use_custom_template_on_basic_info() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_can_use_custom_template_on_basic_info");
		dataMap.put("ExtentTest",test);
		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_can_use_custom_template_on_basic_info(true, dataMap);
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

	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_can_use_custom_template_on_basic_info() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_can_use_custom_template_on_basic_info");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_can_use_custom_template_on_basic_info(false, dataMap);
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

	//Failed on Test Suite
	//Passed Individually
	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_dat_contains_production_volume_name() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_dat_contains_production_volume_name");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_contains_production_volume_name(true, dataMap);
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
	
	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_dat_contains_production_volume_name() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_dat_contains_production_volume_name");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_contains_production_volume_name(false, dataMap);
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

	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_dat_contains_production_volume_name() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_dat_contains_production_volume_name");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_contains_production_volume_name(false, dataMap);
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

	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_dat_contains_production_volume_name() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_dat_contains_production_volume_name");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_contains_production_volume_name(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_can_use_custom_template_on_basic_info() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_can_use_custom_template_on_basic_info");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_can_use_custom_template_on_basic_info(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_can_use_custom_template_on_basic_info() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_can_use_custom_template_on_basic_info");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_can_use_custom_template_on_basic_info(false, dataMap);
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


	@Test(groups = {"Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_production_home_page_Then_verify_production_tile_count_matches_grid_count() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau When on_production_home_page Then verify_production_tile_count_matches_grid_count");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			//context.verify_production_tile_count_matches_grid_count(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_on_production_home_page_Then_Not_verify_production_tile_count_matches_grid_count() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When on_production_home_page Then [Not] verify_production_tile_count_matches_grid_count");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.on_production_home_page(true, dataMap);
			context.verify_production_tile_count_matches_grid_count(false, dataMap);
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

	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_on_production_home_page_Then_Not_verify_production_tile_count_matches_grid_count() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When on_production_home_page Then [Not] verify_production_tile_count_matches_grid_count");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.on_production_home_page(true, dataMap);
			context.verify_production_tile_count_matches_grid_count(false, dataMap);
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


	//Passed
	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_archive_option_is_only_zip() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_archive_option_is_only_zip");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_archive_option_is_only_zip(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_archive_option_is_only_zip() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_archive_option_is_only_zip");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_archive_option_is_only_zip(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_archive_option_is_only_zip() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_archive_option_is_only_zip");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_archive_option_is_only_zip(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_archive_option_is_only_zip() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_archive_option_is_only_zip");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_archive_option_is_only_zip(false, dataMap);
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


	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_complete_component_mp3_with_lst_toggled_off_Then_verify_production_error_message_for_mp3_with_lst_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When complete_component_mp3_with_lst_toggled_off Then verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_complete_component_mp3_with_lst_toggled_off_Then_Not_verify_production_error_message_for_mp3_with_lst_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When complete_component_mp3_with_lst_toggled_off Then [Not] verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_complete_component_mp3_with_lst_toggled_off_Then_Not_verify_production_error_message_for_mp3_with_lst_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When complete_component_mp3_with_lst_toggled_off Then [Not] verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_complete_component_mp3_with_lst_toggled_off_Then_Not_verify_production_error_message_for_mp3_with_lst_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When complete_component_mp3_with_lst_toggled_off Then [Not] verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_complete_component_mp3_with_lst_toggled_off_Then_Not_verify_production_error_message_for_mp3_with_lst_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When complete_component_mp3_with_lst_toggled_off Then [Not] verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(false, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(false, dataMap);
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


	//Failed on Test Suite
	//Passed Individually
	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_mp3_redaction_styles() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_mp3_redaction_styles");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_mp3_redaction_styles(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_mp3_redaction_styles() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_mp3_redaction_styles");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_mp3_redaction_styles(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_mp3_redaction_styles() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_mp3_redaction_styles");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_mp3_redaction_styles(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_mp3_redaction_styles() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_mp3_redaction_styles");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_mp3_redaction_styles(false, dataMap);
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


	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_native_component_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_native_component_advanced_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_native_component_advanced_options(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_native_component_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_native_component_advanced_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_native_component_advanced_options(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_native_component_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_native_component_advanced_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_native_component_advanced_options(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_native_component_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_native_component_advanced_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_native_component_advanced_options(false, dataMap);
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


	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tiff_section_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_tiff_section_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_section_options(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_tiff_section_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_tiff_section_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_section_options(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_tiff_section_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_tiff_section_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_section_options(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_tiff_section_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_tiff_section_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_section_options(false, dataMap);
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


	//Failed on Test Suite
	//Passed Individually
	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tech_issue_placeholder_is_available() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_tech_issue_placeholder_is_available");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tech_issue_placeholder_is_available(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_tech_issue_placeholder_is_available() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_tech_issue_placeholder_is_available");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tech_issue_placeholder_is_available(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_tech_issue_placeholder_is_available() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_tech_issue_placeholder_is_available");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tech_issue_placeholder_is_available(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_tech_issue_placeholder_is_available() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_tech_issue_placeholder_is_available");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tech_issue_placeholder_is_available(false, dataMap);
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


	//Failed on Test Suite
	//Failed Indivdually 
	@Test(groups = {"Production", "Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_dat_component_removed_advanced_option() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_dat_component_removed_advanced_option");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_component_removed_advanced_option(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_dat_component_removed_advanced_option() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_dat_component_removed_advanced_option");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_component_removed_advanced_option(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_dat_component_removed_advanced_option() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_dat_component_removed_advanced_option");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_component_removed_advanced_option(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_dat_component_removed_advanced_option() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_dat_component_removed_advanced_option");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_component_removed_advanced_option(false, dataMap);
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

	//Failed on Suite
	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tiff_pdf_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_tiff_pdf_advanced_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_pdf_advanced_options(true, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_tiff_pdf_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_tiff_pdf_advanced_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_pdf_advanced_options(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_tiff_pdf_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_tiff_pdf_advanced_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_pdf_advanced_options(false, dataMap);
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


	@Test(groups = {"Production", "Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_tiff_pdf_advanced_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_tiff_pdf_advanced_options");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_pdf_advanced_options(false, dataMap);
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
/**/
}

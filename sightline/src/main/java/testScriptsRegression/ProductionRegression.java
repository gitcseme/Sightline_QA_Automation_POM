package testScriptsRegression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.google.protobuf.Empty;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.ProductionContext;

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class ProductionRegression extends RegressionBase {

	ProductionContext context = new ProductionContext();
//	ProductionOutcome outcome = new ProductionOutcome();

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_can_use_custom_template_on_basic_info()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_can_use_custom_template_on_basic_info");
		dataMap.put("ExtentTest", test);
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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_can_use_custom_template_on_basic_info()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_can_use_custom_template_on_basic_info");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_can_use_custom_template_on_basic_info(false, dataMap);
		} catch (ImplementationException e) {
			System.out.println("A");
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			System.out.println("B");
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_dat_contains_production_volume_name()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_dat_contains_production_volume_name");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_dat_contains_production_volume_name()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_dat_contains_production_volume_name");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_contains_production_volume_name(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_dat_contains_production_volume_name()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_dat_contains_production_volume_name");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_contains_production_volume_name(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_dat_contains_production_volume_name()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_dat_contains_production_volume_name");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_can_use_custom_template_on_basic_info()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_can_use_custom_template_on_basic_info");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_can_use_custom_template_on_basic_info(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_can_use_custom_template_on_basic_info()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_can_use_custom_template_on_basic_info");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_production_home_page_Then_verify_production_tile_count_matches_grid_count()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau When on_production_home_page Then verify_production_tile_count_matches_grid_count");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.verify_production_tile_count_matches_grid_count(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_on_production_home_page_Then_Not_verify_production_tile_count_matches_grid_count()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When on_production_home_page Then [Not] verify_production_tile_count_matches_grid_count");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.on_production_home_page(true, dataMap);
			context.verify_production_tile_count_matches_grid_count(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_on_production_home_page_Then_Not_verify_production_tile_count_matches_grid_count()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When on_production_home_page Then [Not] verify_production_tile_count_matches_grid_count");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.on_production_home_page(true, dataMap);
			context.verify_production_tile_count_matches_grid_count(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_archive_option_is_only_zip()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_archive_option_is_only_zip");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_archive_option_is_only_zip()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_archive_option_is_only_zip");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_archive_option_is_only_zip(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_archive_option_is_only_zip()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_archive_option_is_only_zip");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_archive_option_is_only_zip(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_archive_option_is_only_zip()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_archive_option_is_only_zip");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_complete_component_mp3_with_lst_toggled_off_Then_verify_production_error_message_for_mp3_with_lst_off()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When complete_component_mp3_with_lst_toggled_off Then verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_complete_component_mp3_with_lst_toggled_off_Then_Not_verify_production_error_message_for_mp3_with_lst_off()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When complete_component_mp3_with_lst_toggled_off Then [Not] verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_complete_component_mp3_with_lst_toggled_off_Then_Not_verify_production_error_message_for_mp3_with_lst_off()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When complete_component_mp3_with_lst_toggled_off Then [Not] verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_complete_component_mp3_with_lst_toggled_off_Then_Not_verify_production_error_message_for_mp3_with_lst_off()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When complete_component_mp3_with_lst_toggled_off Then [Not] verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(false, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_complete_component_mp3_with_lst_toggled_off_Then_Not_verify_production_error_message_for_mp3_with_lst_off()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When complete_component_mp3_with_lst_toggled_off Then [Not] verify_production_error_message_for_mp3_with_lst_off");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(false, dataMap);
			context.complete_component_mp3_with_lst_toggled_off(true, dataMap);
			context.verify_production_error_message_for_mp3_with_lst_off(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_mp3_redaction_styles()
			throws Throwable {

		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_mp3_redaction_styles");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_mp3_redaction_styles()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_mp3_redaction_styles");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_mp3_redaction_styles(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_mp3_redaction_styles()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_mp3_redaction_styles");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_mp3_redaction_styles(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_mp3_redaction_styles()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_mp3_redaction_styles");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_native_component_advanced_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_native_component_advanced_options");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_native_component_advanced_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_native_component_advanced_options");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_native_component_advanced_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_native_component_advanced_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_native_component_advanced_options");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_native_component_advanced_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_native_component_advanced_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_native_component_advanced_options");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tiff_section_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_tiff_section_options");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_tiff_section_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_tiff_section_options");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_section_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_tiff_section_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_tiff_section_options");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_section_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_tiff_section_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_tiff_section_options");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tech_issue_placeholder_is_available()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_tech_issue_placeholder_is_available");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_tech_issue_placeholder_is_available()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_tech_issue_placeholder_is_available");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tech_issue_placeholder_is_available(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_tech_issue_placeholder_is_available()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_tech_issue_placeholder_is_available");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tech_issue_placeholder_is_available(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_tech_issue_placeholder_is_available()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_tech_issue_placeholder_is_available");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_dat_component_removed_advanced_option()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_dat_component_removed_advanced_option");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_dat_component_removed_advanced_option()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_dat_component_removed_advanced_option");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_component_removed_advanced_option(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_dat_component_removed_advanced_option()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_dat_component_removed_advanced_option");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_dat_component_removed_advanced_option(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_dat_component_removed_advanced_option()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_dat_component_removed_advanced_option");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tiff_pdf_advanced_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_production_tiff_pdf_advanced_options");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_begin_new_production_process_Then_Not_verify_production_tiff_pdf_advanced_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When begin_new_production_process Then [Not] verify_production_tiff_pdf_advanced_options");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_pdf_advanced_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_begin_new_production_process_Then_Not_verify_production_tiff_pdf_advanced_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When begin_new_production_process Then [Not] verify_production_tiff_pdf_advanced_options");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("A", "");
			context.begin_new_production_process(true, dataMap);
			context.verify_production_tiff_pdf_advanced_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_begin_new_production_process_Then_Not_verify_production_tiff_pdf_advanced_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When begin_new_production_process Then [Not] verify_production_tiff_pdf_advanced_options");
		dataMap.put("ExtentTest", test);

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
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	/**/

	@Test(groups = { "Production, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expanding_the_dat_production_component_Then_verify_the_dat_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expanding_the_dat_production_component Then verify_the_dat_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.expanding_the_dat_production_component(true, dataMap);
			context.verify_the_dat_product_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expanding_the_tiff_production_component_Then_verify_the_tiff_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expanding_the_tiff_production_component Then verify_the_tiff_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.expanding_the_tiff_production_component(true, dataMap);
			context.verify_the_tiff_product_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_on_the_docview_button_Then_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting and complete_default_document_selection and complete_default_priv_guard_documents_are_matched When clicking_on_the_docview_button Then verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_on_the_doclist_button_Then_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting and complete_default_document_selection and complete_default_priv_guard_documents_are_matched When clicking_on_the_doclist_button Then verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative"})
	public void test_Given_Not_sightline_is_launched_When_expanding_the_dat_production_component_Then_Not_verify_the_dat_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When expanding_the_dat_production_component Then [Not] verify_the_dat_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.expanding_the_dat_production_component(true, dataMap);
			context.verify_the_dat_product_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_expanding_the_dat_production_component_Then_Not_verify_the_dat_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When expanding_the_dat_production_component Then [Not] verify_the_dat_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.expanding_the_dat_production_component(true, dataMap);
			context.verify_the_dat_product_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_expanding_the_dat_production_component_Then_Not_verify_the_dat_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When expanding_the_dat_production_component Then [Not] verify_the_dat_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.expanding_the_dat_production_component(true, dataMap);
			context.verify_the_dat_product_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_expanding_the_dat_production_component_Then_Not_verify_the_dat_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When expanding_the_dat_production_component Then [Not] verify_the_dat_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.expanding_the_dat_production_component(true, dataMap);
			context.verify_the_dat_product_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_expanding_the_tiff_production_component_Then_Not_verify_the_tiff_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When expanding_the_tiff_production_component Then [Not] verify_the_tiff_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.expanding_the_tiff_production_component(true, dataMap);
			context.verify_the_tiff_product_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_expanding_the_tiff_production_component_Then_Not_verify_the_tiff_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When expanding_the_tiff_production_component Then [Not] verify_the_tiff_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.expanding_the_tiff_production_component(true, dataMap);
			context.verify_the_tiff_product_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_expanding_the_tiff_production_component_Then_Not_verify_the_tiff_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When expanding_the_tiff_production_component Then [Not] verify_the_tiff_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.expanding_the_tiff_production_component(true, dataMap);
			context.verify_the_tiff_product_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_expanding_the_tiff_production_component_Then_Not_verify_the_tiff_product_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When expanding_the_tiff_production_component Then [Not] verify_the_tiff_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.expanding_the_tiff_production_component(true, dataMap);
			context.verify_the_tiff_product_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_on_the_docview_button_Then_Not_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_on_the_docview_button Then [Not] verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_on_the_docview_button_Then_Not_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_on_the_docview_button Then [Not] verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_on_the_docview_button_Then_Not_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_on_the_docview_button Then [Not] verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_on_the_docview_button_Then_Not_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_on_the_docview_button Then [Not] verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_on_the_docview_button_Then_Not_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_on_the_docview_button Then [Not] verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_Not_complete_default_numbering_and_sorting_When_clicking_on_the_docview_button_Then_Not_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and [Not] complete_default_numbering_and_sorting When clicking_on_the_docview_button Then [Not] verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(false, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_Not_complete_default_document_selection_When_clicking_on_the_docview_button_Then_Not_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting and [Not] complete_default_document_selection When clicking_on_the_docview_button Then [Not] verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(false, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_default_document_selection_and_Not_complete_default_priv_guard_documents_are_matched_When_clicking_on_the_docview_button_Then_Not_verify_viewing_docview_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting and complete_default_document_selection and [Not] complete_default_priv_guard_documents_are_matched When clicking_on_the_docview_button Then [Not] verify_viewing_docview_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(false, dataMap);
			context.clicking_on_the_docview_button(true, dataMap);
			context.verify_viewing_docview_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_on_the_doclist_button_Then_Not_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_on_the_doclist_button Then [Not] verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_on_the_doclist_button_Then_Not_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_on_the_doclist_button Then [Not] verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_on_the_doclist_button_Then_Not_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_on_the_doclist_button Then [Not] verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_on_the_doclist_button_Then_Not_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_on_the_doclist_button Then [Not] verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_on_the_doclist_button_Then_Not_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_on_the_doclist_button Then [Not] verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_Not_complete_default_numbering_and_sorting_When_clicking_on_the_doclist_button_Then_Not_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and [Not] complete_default_numbering_and_sorting When clicking_on_the_doclist_button Then [Not] verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(false, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_Not_complete_default_document_selection_When_clicking_on_the_doclist_button_Then_Not_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting and [Not] complete_default_document_selection When clicking_on_the_doclist_button Then [Not] verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(false, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_default_document_selection_and_Not_complete_default_priv_guard_documents_are_matched_When_clicking_on_the_doclist_button_Then_Not_verify_viewing_doclist_for_priv_guard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting and complete_default_document_selection and [Not] complete_default_priv_guard_documents_are_matched When clicking_on_the_doclist_button Then [Not] verify_viewing_doclist_for_priv_guard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(false, dataMap);
			context.clicking_on_the_doclist_button(true, dataMap);
			context.verify_viewing_doclist_for_priv_guard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_document_as_the_numbering_default_option_Then_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_document_as_the_numbering_default_option Then verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_use_metadata_field_as_the_format_default_option_Then_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_use_metadata_field_as_the_format_default_option Then verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_and_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_and_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_and_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_document_as_the_numbering_default_option_Then_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_document_as_the_numbering_default_option Then verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Productio", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_use_metadata_field_as_the_format_default_option_Then_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_use_metadata_field_as_the_format_default_option Then verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_document_as_the_numbering_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_document_as_the_numbering_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_document_as_the_numbering_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_use_metadata_field_as_the_format_default_option_Then_Not_verify_the_numbering_also_sorting_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_use_metadata_field_as_the_format_default_option Then [Not] verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_use_metadata_field_as_the_format_default_option(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_When_clicking_the_document_selection_select_searches_option_Then_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting When clicking_the_document_selection_select_searches_option Then verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.clicking_the_document_selection_select_searches_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_When_clicking_the_document_selection_select_folders_option_Then_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting When clicking_the_document_selection_select_folders_option Then verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.clicking_the_document_selection_select_folders_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_the_document_selection_select_searches_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_the_document_selection_select_searches_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_the_document_selection_select_searches_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_the_document_selection_select_searches_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_the_document_selection_select_searches_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_the_document_selection_select_searches_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_the_document_selection_select_searches_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_the_document_selection_select_searches_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_the_document_selection_select_searches_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_the_document_selection_select_searches_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_the_document_selection_select_searches_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_the_document_selection_select_searches_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_the_document_selection_select_searches_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_the_document_selection_select_searches_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_the_document_selection_select_searches_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_Not_complete_default_numbering_and_sorting_When_clicking_the_document_selection_select_searches_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and [Not] complete_default_numbering_and_sorting When clicking_the_document_selection_select_searches_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(false, dataMap);
			context.clicking_the_document_selection_select_searches_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_the_document_selection_select_folders_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_the_document_selection_select_folders_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_the_document_selection_select_folders_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_the_document_selection_select_folders_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_the_document_selection_select_folders_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_the_document_selection_select_folders_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_the_document_selection_select_folders_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_the_document_selection_select_folders_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_the_document_selection_select_folders_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_the_document_selection_select_folders_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_the_document_selection_select_folders_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_the_document_selection_select_folders_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_the_document_selection_select_folders_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_the_document_selection_select_folders_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_the_document_selection_select_folders_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_Not_complete_default_numbering_and_sorting_When_clicking_the_document_selection_select_folders_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and [Not] complete_default_numbering_and_sorting When clicking_the_document_selection_select_folders_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(false, dataMap);
			context.clicking_the_document_selection_select_folders_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Positive", })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_When_clicking_the_document_selection_select_tags_option_Then_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting When clicking_the_document_selection_select_tags_option Then verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.clicking_the_document_selection_select_tags_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_Not_sightline_is_launched_When_clicking_the_document_selection_select_tags_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When clicking_the_document_selection_select_tags_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.clicking_the_document_selection_select_tags_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_clicking_the_document_selection_select_tags_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When clicking_the_document_selection_select_tags_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "dummyuser@consilio.com");
			dataMap.put("pwd", "123456");
			context.login_as_pau(false, dataMap);
			context.clicking_the_document_selection_select_tags_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_production_home_page_When_clicking_the_document_selection_select_tags_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_production_home_page When clicking_the_document_selection_select_tags_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(false, dataMap);
			context.clicking_the_document_selection_select_tags_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_Not_begin_new_production_process_When_clicking_the_document_selection_select_tags_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and [Not] begin_new_production_process When clicking_the_document_selection_select_tags_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(false, dataMap);
			context.clicking_the_document_selection_select_tags_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_Not_complete_default_production_component_When_clicking_the_document_selection_select_tags_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and [Not] complete_default_production_component When clicking_the_document_selection_select_tags_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(false, dataMap);
			context.clicking_the_document_selection_select_tags_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Production, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_Not_complete_default_numbering_and_sorting_When_clicking_the_document_selection_select_tags_option_Then_Not_verify_the_document_selection_component_displays_the_correct_default_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and [Not] complete_default_numbering_and_sorting When clicking_the_document_selection_select_tags_option Then [Not] verify_the_document_selection_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(false, dataMap);
			context.clicking_the_document_selection_select_tags_option(true, dataMap);
			context.verify_the_document_selection_component_displays_the_correct_default_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_COMPLETED_production_When_locking_the_production_Then_verify_the_locked_production_is_set_to_read_only() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{COMPLETED}_production When locking_the_production Then verify_the_locked_production_is_set_to_read_only");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "COMPLETED");
			dataMap.put("production_name", "DefaultCompletedProduction");
			context.selecting_the_production(true, dataMap);
			context.locking_the_production(true, dataMap);
			context.verify_the_locked_production_is_set_to_read_only(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_on_the_manage_templates_tab_When_clicking_view_on_the_production_template_Then_the_default_production_template_loaded_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and on_the_manage_templates_tab When clicking_view_on_the_production_template Then the_default_production_template_loaded_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.on_the_manage_templates_tab(true, dataMap);
			context.clicking_view_on_the_production_template(true, dataMap);
			context.the_default_production_template_loaded_successfully(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_INPROGRESS_production_and_on_the_summary_preview_section_When_clicking_the_productions_preview_button_Then_verify_the_preview_of_the_pdf_should_display_the_branding() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{INPROGRESS}_production and on_the_summary_preview_section When clicking_the_productions_preview_button Then verify_the_preview_of_the_pdf_should_display_the_branding");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "INPROGRESS");
			dataMap.put("production_name", "AutomationTiffBranding");
			context.selecting_the_production(true, dataMap);
			context.on_the_summary_preview_section(true, dataMap);
			context.clicking_the_productions_preview_button(true, dataMap);
			context.verify_the_preview_of_the_pdf_should_display_the_branding(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_INPROGRESS_GREEN_production_and_navigated_back_onto_the_production_components_section_and_editing_the_completed_production_component_When_navigating_back_to_the_generate_section_Then_verify_the_production_can_be_regenerated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{INPROGRESS_GREEN}_production and navigated_back_onto_the_production_components_section and editing_the_completed_production_component When navigating_back_to_the_generate_section Then verify_the_production_can_be_regenerated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "INPROGRESS_GREEN");
			context.selecting_the_production(true, dataMap);
			context.navigated_back_onto_the_production_components_section(true, dataMap);
			context.editing_the_completed_production_component(true, dataMap);
			context.navigating_back_to_the_generate_section(true, dataMap);
			context.verify_the_production_can_be_regenerated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_COMPLETED_production_When_navigating_to_the_production_selected_Then_verify_document_level_numbers_displays_bate_number_on_production_home_page() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{COMPLETED}_production When navigating_to_the_production_selected Then verify_document_level_numbers_displays_bate_number_on_production_home_page");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "COMPLETED");
			dataMap.put("production_name", "DocLvlNumbering");
			context.selecting_the_production(true, dataMap);
			context.navigating_to_the_production_selected(true, dataMap);
			context.verify_document_level_numbers_displays_bate_number_on_production_home_page(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_select_the_1_production_export_set_When_storing_the_productions_in_the_home_page_Then_verify_the_productions_from_one_production_set_does_not_exist_in_another() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and select_the_{1}_production_export_set When storing_the_productions_in_the_home_page Then verify_the_productions_from_one_production_set_does_not_exist_in_another");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("index", "1");
			context.select_the_production_export_set(true, dataMap);
			context.storing_the_productions_in_the_home_page(true, dataMap);
			context.verify_the_productions_from_one_production_set_does_not_exist_in_another(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_grid_view_and_selecting_the_DRAFT_production_When_clicking_the_productions_settings_button_Then_verify_the_productions_allowed_actions_in_settings_based_on_status() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{grid}_view and selecting_the_{DRAFT}_production When clicking_the_productions_settings_button Then verify_the_productions_allowed_actions_in_settings_based_on_status");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "grid");
			context.the_production_grid_is_set_to_view(true, dataMap);
			dataMap.put("status", "DRAFT");
			dataMap.put("production_name", "");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_productions_settings_button(true, dataMap);
			context.verify_the_productions_allowed_actions_in_settings_based_on_status(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_grid_view_and_selecting_the_INPROGRESS_production_When_clicking_the_productions_settings_button_Then_verify_the_productions_allowed_actions_in_settings_based_on_status() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{grid}_view and selecting_the_{INPROGRESS}_production When clicking_the_productions_settings_button Then verify_the_productions_allowed_actions_in_settings_based_on_status");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "grid");
			context.the_production_grid_is_set_to_view(true, dataMap);
			dataMap.put("status", "INPROGRESS");
			dataMap.put("production_name", "");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_productions_settings_button(true, dataMap);
			context.verify_the_productions_allowed_actions_in_settings_based_on_status(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_grid_view_and_selecting_the_COMPLETED_production_When_clicking_the_productions_settings_button_Then_verify_the_productions_allowed_actions_in_settings_based_on_status() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{grid}_view and selecting_the_{COMPLETED}_production When clicking_the_productions_settings_button Then verify_the_productions_allowed_actions_in_settings_based_on_status");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "grid");
			context.the_production_grid_is_set_to_view(true, dataMap);
			dataMap.put("status", "COMPLETED");
			dataMap.put("production_name", "");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_productions_settings_button(true, dataMap);
			context.verify_the_productions_allowed_actions_in_settings_based_on_status(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_tile_view_and_selecting_the_DRAFT_production_When_clicking_the_productions_settings_button_Then_verify_the_productions_allowed_actions_in_settings_based_on_status() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{tile}_view and selecting_the_{DRAFT}_production When clicking_the_productions_settings_button Then verify_the_productions_allowed_actions_in_settings_based_on_status");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "tile");
			context.the_production_grid_is_set_to_view(true, dataMap);
			dataMap.put("status", "DRAFT");
			dataMap.put("production_name", "");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_productions_settings_button(true, dataMap);
			context.verify_the_productions_allowed_actions_in_settings_based_on_status(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_tile_view_and_selecting_the_INPROGRESS_production_When_clicking_the_productions_settings_button_Then_verify_the_productions_allowed_actions_in_settings_based_on_status() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{tile}_view and selecting_the_{INPROGRESS}_production When clicking_the_productions_settings_button Then verify_the_productions_allowed_actions_in_settings_based_on_status");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "tile");
			context.the_production_grid_is_set_to_view(true, dataMap);
			dataMap.put("status", "INPROGRESS");
			dataMap.put("production_name", "");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_productions_settings_button(true, dataMap);
			context.verify_the_productions_allowed_actions_in_settings_based_on_status(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_tile_view_and_selecting_the_COMPLETED_production_When_clicking_the_productions_settings_button_Then_verify_the_productions_allowed_actions_in_settings_based_on_status() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{tile}_view and selecting_the_{COMPLETED}_production When clicking_the_productions_settings_button Then verify_the_productions_allowed_actions_in_settings_based_on_status");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "tile");
			context.the_production_grid_is_set_to_view(true, dataMap);
			dataMap.put("status", "COMPLETED");
			dataMap.put("production_name", "");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_productions_settings_button(true, dataMap);
			context.verify_the_productions_allowed_actions_in_settings_based_on_status(true, dataMap);
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
	public void test_Given_verify_the_next_bates_number_generation_is_stored_in_the_correct_fields_and_selecting_a_different_generated_bates_number_When_clicking_the_productions_mark_complete_button_Then_verify_the_next_bates_number_generation_is_updated_in_the_correct_fields() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_the_next_bates_number_generation_is_stored_in_the_correct_fields and selecting_a_different_generated_bates_number When clicking_the_productions_mark_complete_button Then verify_the_next_bates_number_generation_is_updated_in_the_correct_fields");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_specifying_the_next_bates_number(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_next_bates_number_generation_is_stored_in_the_correct_fields(true, dataMap);
			context.selecting_a_different_generated_bates_number(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_next_bates_number_generation_is_stored_in_the_correct_fields(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_specifying_the_next_bates_number_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_and_complete_default_production_location_component_and_completed_summary_preview_component_and_starting_the_production_generation_When_waiting_for_production_to_be_in_progress_Then_verify_using_the_next_bates_generations_a_production_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_specifying_the_next_bates_number and complete_default_document_selection and complete_default_priv_guard_documents_are_matched and complete_default_production_location_component and completed_summary_preview_component and starting_the_production_generation When waiting_for_production_to_be_in_progress Then verify_using_the_next_bates_generations_a_production_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_specifying_the_next_bates_number(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_in_progress(true, dataMap);
			context.verify_using_the_next_bates_generations_a_production_successfully(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_specifying_the_next_bates_number_and_editing_the_generated_bates_number_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_and_complete_default_production_location_component_and_completed_summary_preview_component_and_starting_the_production_generation_When_waiting_for_production_to_be_in_progress_Then_verify_editing_the_bates_number_after_generation_is_displaying_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_specifying_the_next_bates_number and editing_the_generated_bates_number and complete_default_document_selection and complete_default_priv_guard_documents_are_matched and complete_default_production_location_component and completed_summary_preview_component and starting_the_production_generation When waiting_for_production_to_be_in_progress Then verify_editing_the_bates_number_after_generation_is_displaying_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_specifying_the_next_bates_number(true, dataMap);
			context.editing_the_generated_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_in_progress(true, dataMap);
			context.verify_editing_the_bates_number_after_generation_is_displaying_correctly(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_specifying_the_next_bates_number_When_clicking_the_productions_mark_complete_button_Then_verify_the_next_bates_number_generation_is_stored_in_the_correct_fields() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_specifying_the_next_bates_number When clicking_the_productions_mark_complete_button Then verify_the_next_bates_number_generation_is_stored_in_the_correct_fields");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_specifying_the_next_bates_number(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_next_bates_number_generation_is_stored_in_the_correct_fields(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_the_numbering_is_set_to_document_with_no_sub_bates_When_clicking_the_productions_mark_complete_button_Then_verify_document_level_numbering_can_be_empty_for_sub_bates() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and the_numbering_is_set_to_document_with_no_sub_bates When clicking_the_productions_mark_complete_button Then verify_document_level_numbering_can_be_empty_for_sub_bates");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.the_numbering_is_set_to_document_with_no_sub_bates(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_document_level_numbering_can_be_empty_for_sub_bates(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_the_productions_save_button_Then_verify_the_numbering__sorting_component_displays_the_correct_default_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_the_productions_save_button Then verify_the_numbering_also_sorting_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_the_productions_save_button(true, dataMap);
			context.verify_the_numbering_also_sorting_component_displays_the_correct_default_options(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_the_numbering_format_click_here_link_Then_verify_the_next_bates_number_dialog_displays_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_the_numbering_format_click_here_link Then verify_the_next_bates_number_dialog_displays_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_the_numbering_format_click_here_link(true, dataMap);
			context.verify_the_next_bates_number_dialog_displays_correctly(true, dataMap);
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
	

	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_verify_enabling_placeholders_on_priv_guard_saves_the_placeholders_and_complete_default_production_location_component_and_completed_summary_preview_component_and_the_production_is_started_When_refreshing_for_production_to_be_in_progress_Then_verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_enabling_placeholders_on_priv_guard_saves_the_placeholders and complete_default_production_location_component and completed_summary_preview_component and the_production_is_started When refreshing_for_production_to_be_in_progress Then verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.remove_placeholders_on_tiff_pdf(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "6");
			dataMap.put("beginning_bates", "3");
			dataMap.put("suffix", "Q");
			dataMap.put("continue", "true");
			context.custom_number_sorting_is_added(true, dataMap);
			context.complete_document_section_with_priviledged_folder(true, dataMap);
			context.completing_priv_guard_by_enabling_placeholders(true, dataMap);
			context.expanding_the_tiff_pdf_section_of_production_components(true, dataMap);
			context.verify_enabling_placeholders_on_priv_guard_saves_the_placeholders(true, dataMap);

			
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.the_production_is_started(true, dataMap);
			context.refreshing_for_production_to_be_in_progress(true, dataMap);
			context.verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_sorting_is_added_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_and_complete_default_categorization_for_priv_guard_When_clicking_the_productions_mark_complete_button_Then_verify_the_priv_guard_categorization_displays_the_correct_matched_documents_number() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_sorting_is_added and complete_default_document_selection and complete_default_priv_guard_documents_are_matched and complete_default_categorization_for_priv_guard When clicking_the_productions_mark_complete_button Then verify_the_priv_guard_categorization_displays_the_correct_matched_documents_number");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "6");
			dataMap.put("beginning_bates", "3");
			dataMap.put("suffix", "Q");
			context.custom_number_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.complete_default_categorization_for_priv_guard(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_priv_guard_categorization_displays_the_correct_matched_documents_number(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_remove_placeholders_on_tiff_pdf_and_custom_number_sorting_is_added_and_complete_document_section_with_priviledged_folder_and_completing_priv_guard_by_enabling_placeholders_When_expanding_the_tiff_pdf_section_of_production_components_Then_verify_enabling_placeholders_on_priv_guard_saves_the_placeholders() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and remove_placeholders_on_tiff_pdf and custom_number_sorting_is_added and complete_document_section_with_priviledged_folder and completing_priv_guard_by_enabling_placeholders When expanding_the_tiff_pdf_section_of_production_components Then verify_enabling_placeholders_on_priv_guard_saves_the_placeholders");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.remove_placeholders_on_tiff_pdf(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "6");
			dataMap.put("beginning_bates", "3");
			dataMap.put("suffix", "Q");
			context.custom_number_sorting_is_added(true, dataMap);
			context.complete_document_section_with_priviledged_folder(true, dataMap);
			context.completing_priv_guard_by_enabling_placeholders(true, dataMap);
			context.expanding_the_tiff_pdf_section_of_production_components(true, dataMap);
			context.verify_enabling_placeholders_on_priv_guard_saves_the_placeholders(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_sorting_is_added_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_view_results_in_doclist_Then_verify_the_result_set_documents_are_displayed_in_DocList() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_sorting_is_added and complete_default_document_selection and complete_default_priv_guard_documents_are_matched When clicking_view_results_in_doclist Then verify_the_result_set_documents_are_displayed_in_DocList");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "6");
			dataMap.put("beginning_bates", "3");
			dataMap.put("suffix", "Q");
			context.custom_number_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.clicking_view_results_in_doclist(true, dataMap);
			context.verify_the_result_set_documents_are_displayed_in_DocList(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_sorting_is_added_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_view_results_in_docview_Then_verify_the_result_set_documents_are_displayed_in_Docview() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_sorting_is_added and complete_default_document_selection and complete_default_priv_guard_documents_are_matched When clicking_view_results_in_docview Then verify_the_result_set_documents_are_displayed_in_Docview");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "6");
			dataMap.put("beginning_bates", "3");
			dataMap.put("suffix", "Q");
			context.custom_number_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.clicking_view_results_in_docview(true, dataMap);
			context.verify_the_result_set_documents_are_displayed_in_Docview(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_sorting_is_added_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_the_productions_mark_complete_button_Then_verify_the_priv_guard_component_displays_the_correct_matched_documents_number() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_sorting_is_added and complete_default_document_selection and complete_default_priv_guard_documents_are_matched When clicking_the_productions_mark_complete_button Then verify_the_priv_guard_component_displays_the_correct_matched_documents_number");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "6");
			dataMap.put("beginning_bates", "3");
			dataMap.put("suffix", "Q");
			context.custom_number_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_priv_guard_component_displays_the_correct_matched_documents_number(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_INPROGRESS_production_When_clicking_the_productions_name_Then_verify_the_productions_quality_control_section_display_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{INPROGRESS}_production When clicking_the_productions_name Then verify_the_productions_quality_control_section_display_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "INPROGRESS");
			dataMap.put("viewMode", "tile");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_productions_name(true, dataMap);
			context.verify_the_productions_quality_control_section_display_correctly(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_tiff_production_component_with_branding_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_When_clicking_the_productions_save_button_Then_verify_the_number_of_mp3_files_are_shown_regardless_of_amount() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_tiff_production_component_with_branding and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component When clicking_the_productions_save_button Then verify_the_number_of_mp3_files_are_shown_regardless_of_amount");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_tiff_production_component_with_branding(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.clicking_the_productions_save_button(true, dataMap);
			context.verify_the_number_of_mp3_files_are_shown_regardless_of_amount(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_tiff_production_component_with_branding_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_When_clicking_the_summary_preview_button_Then_verify_the_preview_file_should_display_the_branding_entered() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_tiff_production_component_with_branding and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component When clicking_the_summary_preview_button Then verify_the_preview_file_should_display_the_branding_entered");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_tiff_production_component_with_branding(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.clicking_the_summary_preview_button(true, dataMap);
			context.verify_the_preview_file_should_display_the_branding_entered(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_tiff_production_component_with_branding_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_When_clicking_the_productions_save_button_Then_verify_the_redaction_count_displays_correctly_on_the_summary_page() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_tiff_production_component_with_branding and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component When clicking_the_productions_save_button Then verify_the_redaction_count_displays_correctly_on_the_summary_page");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_tiff_production_component_with_branding(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.clicking_the_productions_save_button(true, dataMap);
			context.verify_the_redaction_count_displays_correctly_on_the_summary_page(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_tiff_production_component_with_branding_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_When_clicking_the_productions_save_button_Then_verify_the_summary_page_displays_the_correct_information() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_tiff_production_component_with_branding and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component When clicking_the_productions_save_button Then verify_the_summary_page_displays_the_correct_information");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_tiff_production_component_with_branding(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.clicking_the_productions_save_button(true, dataMap);
			context.verify_the_summary_page_displays_the_correct_information(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	

	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_the_native_section_with_tags_and_no_file_types_When_clicking_the_productions_mark_complete_button_Then_verify_native_section_with_tags_is_saving_correctly_without_file_types() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_the_native_section_with_tags_and_no_file_types When clicking_the_productions_mark_complete_button Then verify_native_section_with_tags_is_saving_correctly_without_file_types");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			context.complete_the_native_section_with_tags_and_no_file_types(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_native_section_with_tags_is_saving_correctly_without_file_types(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_tiff_pdf_with_rotation_When_complete_default_production_component_Then_verify_marking_a_pdf_with_rotation_completed_returns_no_error() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_tiff_pdf_with_rotation When complete_default_production_component Then verify_marking_a_pdf_with_rotation_completed_returns_no_error");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_tiff_pdf_with_rotation(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.verify_marking_a_pdf_with_rotation_completed_returns_no_error(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_tiff_with_empty_natively_produced_documents_When_clicking_the_productions_mark_complete_button_Then_verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_tiff_with_empty_natively_produced_documents When clicking_the_productions_mark_complete_button Then verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			context.complete_tiff_with_empty_natively_produced_documents(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_add_a_second_dat_for_classification_production_When_clicking_the_productions_mark_complete_button_Then_verify_adding_a_second_dat_data_mapping_is_retained() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and add_a_second_dat_for_classification_production When clicking_the_productions_mark_complete_button Then verify_adding_a_second_dat_data_mapping_is_retained");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("field_classification", "Production");
			dataMap.put("source_field", "TIFFPageCount");
			dataMap.put("dat_field", "TPageCount");
			context.add_a_second_dat_for_classification_production(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_adding_a_second_dat_data_mapping_is_retained(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_tiff_and_pdf_with_different_tags_When_clicking_the_productions_mark_complete_button_Then_verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_tiff_and_pdf_with_different_tags When clicking_the_productions_mark_complete_button Then verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			context.complete_tiff_and_pdf_with_different_tags(true, dataMap);
			context.clicking_the_productions_mark_complete_button(false, dataMap);
			context.verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_When_expanding_the_mp3_advanced_section_Then_verify_the_mp3_advanced_option_is_enabled_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component When expanding_the_mp3_advanced_section Then verify_the_mp3_advanced_option_is_enabled_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("mp3", "true");
			context.complete_complex_production_component(true, dataMap);
			context.expanding_the_mp3_advanced_section(true, dataMap);
			context.verify_the_mp3_advanced_option_is_enabled_by_default(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_refresh_back_to_production_home_page_and_navigated_back_onto_the_production_components_section_and_edit_the_production_component_with_a_tiff_When_refreshing_the_current_page_Then_verify_editing_an_existing_production_saves_the_new_changes() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and refresh_back_to_production_home_page and navigated_back_onto_the_production_components_section and edit_the_production_component_with_a_tiff When refreshing_the_current_page Then verify_editing_an_existing_production_saves_the_new_changes");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.navigated_back_onto_the_production_components_section(true, dataMap);
			context.edit_the_production_component_with_a_tiff(true, dataMap);
			context.refreshing_the_current_page(true, dataMap);
			context.verify_editing_an_existing_production_saves_the_new_changes(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_select_place_holder_tag_dialog_When_clicking_non_privledge_tags_Then_verify_privledged_tags_can_only_be_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_tiff_section_select_place_holder_tag_dialog When clicking_non_privledge_tags Then verify_privledged_tags_can_only_be_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.on_the_tiff_section_select_place_holder_tag_dialog(true, dataMap);
			context.clicking_non_privledge_tags(true, dataMap);
			context.verify_privledged_tags_can_only_be_selected(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_the_native_section_with_tags_When_clicking_the_productions_mark_complete_button_Then_verify_native_section_with_tags_is_saving_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_the_native_section_with_tags When clicking_the_productions_mark_complete_button Then verify_native_section_with_tags_is_saving_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			context.complete_the_native_section_with_tags(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_native_section_with_tags_is_saving_correctly(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_and_waiting_for_production_to_be_complete_When_clicking_review_production_Then_verify_the_review_production_path_is_correct() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated and waiting_for_production_to_be_complete When clicking_review_production Then verify_the_review_production_path_is_correct");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "false");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("translations", "false");
			dataMap.put("mp3", "false");
			dataMap.put("archive_file", "false");
			dataMap.put("tiff", "true");
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.clicking_review_production(true, dataMap);
			context.verify_the_review_production_path_is_correct(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_and_navigated_back_onto_the_document_components_section_and_change_the_folder_selection_to_by_tags_complete_the_production_When_waiting_for_production_to_be_in_progress_Then_verify_overwriting_the_document_selection_generates_a_new_bate_number() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated and navigated_back_onto_the_document_components_section and change_the_folder_selection_to_by_tags_complete_the_production When waiting_for_production_to_be_in_progress Then verify_overwriting_the_document_selection_generates_a_new_bate_number");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.navigated_back_onto_the_document_components_section(true, dataMap);
			context.change_the_folder_selection_to_by_tags_complete_the_production(true, dataMap);
			context.waiting_for_production_to_be_in_progress(true, dataMap);
			context.verify_overwriting_the_document_selection_generates_a_new_bate_number(true, dataMap);
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


	
	@Test(groups = {"Production, Positive", "Regression" })
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_When_waiting_for_production_to_be_in_progress_Then_verify_native_productions_can_be_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated When waiting_for_production_to_be_in_progress Then verify_native_productions_can_be_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			context.waiting_for_production_to_be_in_progress(true, dataMap);
			context.verify_native_productions_can_be_generated(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_and_waiting_for_production_to_be_complete_When_navigating_to_the_vm_production_location_Then_verify_the_generated_files_display_id_as_the_bates_number() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated and waiting_for_production_to_be_complete When navigating_to_the_vm_production_location Then verify_the_generated_files_display_id_as_the_bates_number");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio9@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "false");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("translations", "false");
			dataMap.put("mp3", "false");
			dataMap.put("archive_file", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.verify_the_generated_files_display_id_as_the_bates_number(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_When_waiting_for_production_to_be_in_progress_Then_verify_the_bates_generated_number_follows_the_custom_numbering() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated When waiting_for_production_to_be_in_progress Then verify_the_bates_generated_number_follows_the_custom_numbering");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "false");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("translations", "false");
			dataMap.put("mp3", "false");
			dataMap.put("archive_file", "false");
			dataMap.put("tiff", "true");
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			context.waiting_for_production_to_be_in_progress(true, dataMap);
			context.verify_the_bates_generated_number_follows_the_custom_numbering(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_and_a_file_size_ingestion_was_completed_When_waiting_for_production_to_be_complete_Then_verify_a_production_document_with_appropriate_file_size_returns_no_errors() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated and a_file_size_ingestion_was_completed When waiting_for_production_to_be_complete Then verify_a_production_document_with_appropriate_file_size_returns_no_errors");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			dataMap.put("ingestion_size", "10MB");
			context.a_file_size_ingestion_was_completed(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.verify_a_production_document_with_appropriate_file_size_returns_no_errors(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_and_sorting_is_added_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_When_completed_summary_and_preview_component_Then_verify_productions_are_set_to_draft_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_and_sorting_is_added and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component When completed_summary_and_preview_component Then verify_productions_are_set_to_draft_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.verify_productions_are_set_to_draft_by_default(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_and_sorting_is_added_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_and_completed_summary_preview_component_When_clicking_the_production_generate_button_Then_verify_a_complex_production_is_able_to_be_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_and_sorting_is_added and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component and completed_summary_preview_component When clicking_the_production_generate_button Then verify_a_complex_production_is_able_to_be_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//Run This at your own risk, It delete's all drafts 1 by 1
	//Try to use this only when there has been a build up of production drafts
	@Test(groups = {"Production, Positive", "smoke"})
	public void deleteALLDrafts() throws Throwable{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Delete draft productions");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			dataMap.put("status", "DRAFT");
			//dataMap.put("viewMode", "tile");
			context.deleteAll(true, dataMap);
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

	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_When_waiting_for_production_to_be_complete_Then_verify_the_user_is_able_to_click_on_confirm_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated When waiting_for_production_to_be_complete Then verify_the_user_is_able_to_click_on_confirm_production");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "false");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("translations", "false");
			dataMap.put("mp3", "false");
			dataMap.put("archive_file", "false");
			dataMap.put("tiff", "true");
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.verify_the_user_is_able_to_click_on_confirm_production(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_and_connect_to_the_database_When_executing_the_query_Then_verify_in_the_database_the_background_process_for_pregen_is_active() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated and connect_to_the_database When executing_the_query Then verify_in_the_database_the_background_process_for_pregen_is_active");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("tiff", "true");
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			context.connect_to_the_database(true, dataMap);
			dataMap.put("query", "SELECT * FROM [MTPVTDSLDB02].[dbo].[ProductionTaskStatus] where ProductionID=<ID>order by 1 desc");
			context.executing_the_query(true, dataMap);
			context.verify_in_the_database_the_background_process_for_pregen_is_active(true, dataMap);
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


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_FAILED_production_and_open_the_first_production_When_clicking_the_failed_generation_link_Then_verify_failed_productions_display_the_approriate_error_message() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{FAILED}_production and open_the_first_production When clicking_the_failed_generation_link Then verify_failed_productions_display_the_approriate_error_message");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "FAILED");
			context.selecting_the_production(true, dataMap);
			context.open_the_first_production(true, dataMap);
			context.clicking_the_failed_generation_link(true, dataMap);
			context.verify_failed_productions_display_the_approriate_error_message(true, dataMap);
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


	@Test(groups = {"Production, Positive","Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expanding_the_pdf_production_component_Then_verify_the_pdf_product_component_displays_the_correct_default_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expanding_the_pdf_production_component Then verify_the_pdf_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.expanding_the_pdf_production_component(true, dataMap);
			context.verify_the_pdf_product_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production, Positive","Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expanding_the_native_production_component_Then_verify_the_native_product_component_displays_the_correct_default_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expanding_the_native_production_component Then verify_the_native_product_component_displays_the_correct_default_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.expanding_the_native_production_component(true, dataMap);
			context.verify_the_native_product_component_displays_the_correct_default_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_complete_default_production_component_Then_verify_production_numbering_sorting_fields_are_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When complete_default_production_component Then verify_production_numbering_sorting_fields_are_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.verify_production_numbering_sorting_fields_are_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	
	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_store_the_default_template_values_and_refresh_back_to_production_home_page_and_begin_new_production_process_and_adding_branding_to_pdf_and_the_default_template_for_production_components_is_displayed_and_the_default_template_for_numbering_is_displayed_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_and_complete_default_production_location_component_and_completed_summary_preview_component_and_starting_the_production_generation_and_waiting_for_production_to_be_complete_When_navigating_to_the_vm_production_location_Then_verify_the_branding_is_displayed_on_the_generated_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and store_the_default_template_values and refresh_back_to_production_home_page and begin_new_production_process and adding_branding_to_pdf and the_default_template_for_production_components_is_displayed and the_default_template_for_numbering_is_displayed and complete_default_document_selection and complete_default_priv_guard_documents_are_matched and complete_default_production_location_component and completed_summary_preview_component and starting_the_production_generation and waiting_for_production_to_be_complete When navigating_to_the_vm_production_location Then verify_the_branding_is_displayed_on_the_generated_production");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.on_production_home_page(true, dataMap);
			context.store_the_default_template_values(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.adding_branding_to_pdf(true, dataMap);
			context.the_default_template_for_production_components_is_displayed(true, dataMap);
			context.the_default_template_for_numbering_is_displayed(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_priv_guard_section(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.verify_the_branding_is_displayed_on_the_generated_production(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_store_the_default_template_values_and_refresh_back_to_production_home_page_and_begin_new_production_process_and_updating_redaction_style_adding_redaction_text_Whitewithblackfont_and_the_default_template_for_production_components_is_displayed_and_the_default_template_for_numbering_is_displayed_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_and_complete_default_production_location_component_and_completed_summary_preview_component_and_starting_the_production_generation_and_waiting_for_production_to_be_complete_When_navigating_to_the_vm_production_location_Then_verify_the_redaction_documents_are_redacted_with_the_proper_style() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and store_the_default_template_values and refresh_back_to_production_home_page and begin_new_production_process and updating_redaction_style_adding_redaction_text_{Whitewithblackfont} and the_default_template_for_production_components_is_displayed and the_default_template_for_numbering_is_displayed and complete_default_document_selection and complete_default_priv_guard_documents_are_matched and complete_default_production_location_component and completed_summary_preview_component and starting_the_production_generation and waiting_for_production_to_be_complete When navigating_to_the_vm_production_location Then verify_the_redaction_documents_are_redacted_with_the_proper_style");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.store_the_default_template_values(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			dataMap.put("redaction_style", "White with black font");
			context.updating_redaction_style_adding_redaction_text_(true, dataMap);
			context.the_default_template_for_production_components_is_displayed(true, dataMap);
			context.the_default_template_for_numbering_is_displayed(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_priv_guard_section(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_redaction_documents_are_redacted_with_the_proper_style(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_store_the_default_template_values_and_refresh_back_to_production_home_page_and_begin_new_production_process_and_updating_redaction_style_adding_redaction_text_Blackwithwhitefont_and_the_default_template_for_production_components_is_displayed_and_the_default_template_for_numbering_is_displayed_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_and_complete_default_production_location_component_and_completed_summary_preview_component_and_starting_the_production_generation_and_waiting_for_production_to_be_complete_When_navigating_to_the_vm_production_location_Then_verify_the_redaction_documents_are_redacted_with_the_proper_style() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and store_the_default_template_values and refresh_back_to_production_home_page and begin_new_production_process and updating_redaction_style_adding_redaction_text_{Blackwithwhitefont} and the_default_template_for_production_components_is_displayed and the_default_template_for_numbering_is_displayed and complete_default_document_selection and complete_default_priv_guard_documents_are_matched and complete_default_production_location_component and completed_summary_preview_component and starting_the_production_generation and waiting_for_production_to_be_complete When navigating_to_the_vm_production_location Then verify_the_redaction_documents_are_redacted_with_the_proper_style");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.on_production_home_page(true, dataMap);
			context.store_the_default_template_values(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			dataMap.put("redaction_style", "Black with white font");
			context.updating_redaction_style_adding_redaction_text_(true, dataMap);
			context.the_default_template_for_production_components_is_displayed(true, dataMap);
			context.the_default_template_for_numbering_is_displayed(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_priv_guard_section(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.verify_the_redaction_documents_are_redacted_with_the_proper_style(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_store_the_default_template_values_and_refresh_back_to_production_home_page_and_begin_new_production_process_and_adding_branding_to_pdf_and_the_default_template_for_production_components_is_displayed_and_the_default_template_for_numbering_is_displayed_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_and_complete_default_production_location_component_When_clicking_on_the_productions_preview_button_Then_verify_the_preview_pdf_displays_the_pdf_branding() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and store_the_default_template_values and refresh_back_to_production_home_page and begin_new_production_process and adding_branding_to_pdf and the_default_template_for_production_components_is_displayed and the_default_template_for_numbering_is_displayed and complete_default_document_selection and complete_default_priv_guard_documents_are_matched and complete_default_production_location_component When clicking_on_the_productions_preview_button Then verify_the_preview_pdf_displays_the_pdf_branding");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.store_the_default_template_values(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.adding_branding_to_pdf(true, dataMap);
			context.the_default_template_for_production_components_is_displayed(true, dataMap);
			context.the_default_template_for_numbering_is_displayed(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.clicking_on_the_productions_preview_button(true, dataMap);
			context.verify_the_preview_pdf_displays_the_pdf_branding(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_store_the_default_template_values_and_refresh_back_to_production_home_page_and_begin_new_production_process_and_the_default_template_for_production_components_is_displayed_and_the_default_template_for_numbering_is_displayed_and_complete_default_document_selection_When_clicking_the_productions_mark_complete_button_Then_verify_creating_a_production_with_a_custom_template_store_the_correct_values() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and store_the_default_template_values and refresh_back_to_production_home_page and begin_new_production_process and the_default_template_for_production_components_is_displayed and the_default_template_for_numbering_is_displayed and complete_default_document_selection When clicking_the_productions_mark_complete_button Then verify_creating_a_production_with_a_custom_template_store_the_correct_values");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.on_production_home_page(true, dataMap);
			context.store_the_default_template_values(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.the_default_template_for_production_components_is_displayed(true, dataMap);
			context.the_default_template_for_numbering_is_displayed(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.verify_creating_a_production_with_a_custom_template_store_the_correct_values(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_remove_burn_redaction_on_mp3_and_mark_the_component_section_complete_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_on_disabling_burn_redactions_on_mp3s_no_redaction_content_is_removed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and remove_burn_redaction_on_mp3 and mark_the_component_section_complete and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_on_disabling_burn_redactions_on_mp3s_no_redaction_content_is_removed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "3");
			context.begin_new_production_process(true, dataMap);
			context.remove_burn_redaction_on_mp3(true, dataMap);
			context.mark_the_component_section_complete(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_on_disabling_burn_redactions_on_mp3s_no_redaction_content_is_removed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_enabling_slip_sheets_on_tiff_and_mark_the_component_section_complete_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_date_format_on_dateonly_fields_should_not_include_time() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and enabling_slip_sheets_on_tiff and mark_the_component_section_complete and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_date_format_on_dateonly_fields_should_not_include_time");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "3");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("sheet_value", "DocID.MasterDateDateOnly");
			dataMap.put("sheet_tab", "METADATA");
			context.enabling_slip_sheets_on_tiff(true, dataMap);
			context.mark_the_component_section_complete(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_date_format_on_dateonly_fields_should_not_include_time(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_adding_multiple_dat_field_mappings_and_mark_the_component_section_complete_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_ending_bates_generated_in_a_production_should_have_correct_values() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and adding_multiple_dat_field_mappings and mark_the_component_section_complete and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_ending_bates_generated_in_a_production_should_have_correct_values");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "3");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat_mapping_values", "Bates-BatesNumber-BatesNum.Bates-BeginingAttachBate-BeginningAttach.Bates-EndingBates-EndingBatesNum.Doc Basic-MasterDate-MasterDateField");
			context.adding_multiple_dat_field_mappings(true, dataMap);
			context.mark_the_component_section_complete(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_ending_bates_generated_in_a_production_should_have_correct_values(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expanding_the_mp3_advanced_section_Then_verify_redaction_tags_configured_in_mp3_productions_are_retained_in_templates() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expanding_the_mp3_advanced_section Then verify_redaction_tags_configured_in_mp3_productions_are_retained_in_templates");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "5927");
			dataMap.put("uid", "automate.sqa3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			context.expanding_the_mp3_advanced_section(true, dataMap);
			context.verify_redaction_tags_configured_in_mp3_productions_are_retained_in_templates(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_mark_the_component_section_complete_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_mp3_files_in_the_generation_has_redacted_audio() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and mark_the_component_section_complete and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_mp3_files_in_the_generation_has_redacted_audio");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			context.mark_the_component_section_complete(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_mp3_files_in_the_generation_has_redacted_audio(true, dataMap);
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


	@Test(groups = {"Production", "Positive" ,"WIP"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_committed_production_found() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_committed_production_found");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_committed_production_found(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_default_document_selection_When_mark_complete_default_priv_guard_Then_verify_production_location_component() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_default_document_selection When mark_complete_default_priv_guard Then verify_production_location_component");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "5882");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "automate.sqa3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			//context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.verify_production_location_component(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Pending"})
	public void test_Given_login_to_new_production_and_select_mp3_redactions_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_and_click_the_save_as_template_button_for_created_production_and_begin_new_production_process_with_new_template_When_begin_new_production_process_Then_verify_template_mp3_component_details() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_mp3_redactions and complete_complex_production_component and the_production_is_generated_with_the_given_production_component and click_the_save_as_template_button_for_created_production and begin_new_production_process_with_new_template When begin_new_production_process Then verify_template_mp3_component_details");

		dataMap.put("ExtentTest",test);

		try { 
			context.login_to_new_production(true, dataMap);
			context.select_mp3_redactions(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("mp3", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.click_the_save_as_template_button_for_created_production(true, dataMap);
			context.begin_new_production_process_with_new_template(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.verify_template_mp3_component_details(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	

	@Test(groups = {"Production", "Positive", "Pending"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_When_open_production_in_docview_Then_verify_produced_pdf_in_docview() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page When open_production_in_docview Then verify_produced_pdf_in_docview");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("Test Case", "5276|6122");
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.open_production_in_docview(true, dataMap);
			context.verify_produced_pdf_in_docview(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Pending"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_When_open_production_in_docview_Then_verify_produced_pdf_in_docview_with_pdf_only_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page When open_production_in_docview Then verify_produced_pdf_in_docview_with_pdf_only_production");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("Test Case", "5276");
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.open_production_in_docview(true, dataMap);
			context.verify_produced_pdf_in_docview_with_pdf_only_production(true, dataMap);
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
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_and_advanced_search_for_production_When_advanced_search_for_production_Then_verify_committed_production_found() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page and advanced_search_for_production When advanced_search_for_production Then verify_committed_production_found");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.advanced_search_for_production(true, dataMap);
			context.advanced_search_for_production(true, dataMap);
			context.verify_committed_production_found(true, dataMap);
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
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_and_advanced_search_for_production_and_on_production_home_page_and_uncommit_last_production_and_navigate_to_session_search_page_When_advanced_search_for_production_Then_verify_uncommitted_production_not_found() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page and advanced_search_for_production and on_production_home_page and uncommit_last_production and navigate_to_session_search_page When advanced_search_for_production Then verify_uncommitted_production_not_found");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.advanced_search_for_production(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.uncommit_last_production(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.advanced_search_for_production(true, dataMap);
			context.verify_uncommitted_production_not_found(true, dataMap);
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
	public void test_Given_doc_with_priv_tag_exists_and_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_login_as_rmu_and_remove_priv_tag_from_doc_and_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_removal_of_priv_tag_produced_for_native() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given doc_with_priv_tag_exists and login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and login_as_rmu and remove_priv_tag_from_doc and login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_removal_of_priv_tag_produced_for_native");

		dataMap.put("ExtentTest",test);

		try {
			context.doc_with_priv_tag_exists(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.login_as_rmu(true, dataMap);
			context.remove_priv_tag_from_doc(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_removal_of_priv_tag_produced_for_native(true, dataMap);
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
	public void test_Given_multimedia_doc_with_priv_tag_exists_and_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_multimedia_file_group_production_generation_with_priv_tag() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given multimedia_doc_with_priv_tag_exists and login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_multimedia_file_group_production_generation_with_priv_tag");

		dataMap.put("ExtentTest",test);

		try {
			context.multimedia_doc_with_priv_tag_exists(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_multimedia_file_group_production_generation_with_priv_tag(true, dataMap);
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
	public void test_Given_doc_with_redaction_tag_exists_and_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_login_as_rmu_and_remove_redaction_from_doc_and_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_removal_of_redaction_tag_produced_for_native() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given doc_with_redaction_tag_exists and login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and login_as_rmu and remove_redaction_from_doc and login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_removal_of_redaction_tag_produced_for_native");

		dataMap.put("ExtentTest",test);

		try {
			context.doc_with_redaction_tag_exists(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.login_as_rmu(true, dataMap);
			context.remove_redaction_from_doc(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_removal_of_redaction_tag_produced_for_native(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_When_open_doc_in_doc_view_Then_verify_doc_view_images_tab_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page When open_doc_in_doc_view Then verify_doc_view_images_tab_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "8211");
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.open_doc_in_doc_view(true, dataMap);
			context.verify_doc_view_images_tab_displayed(true, dataMap);
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


	@Test(groups = {"Production", "Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_click_uncommit_production_button_and_nav_back_to_generate_tab_and_regenerate_production_When_navigating_to_the_vm_production_location_Then_verify_uncommitted_production_regenerated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and click_uncommit_production_button and nav_back_to_generate_tab and regenerate_production When navigating_to_the_vm_production_location Then verify_uncommitted_production_regenerated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.click_uncommit_production_button(true, dataMap);
			context.nav_back_to_generate_tab(true, dataMap);
			context.regenerate_production(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_uncommitted_production_regenerated(true, dataMap);
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


	@Test(groups = {"Production", "Positive" ,"Regression"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_on_production_home_page_and_uncommit_last_production_and_navigate_to_session_search_page_When_open_doc_in_doc_view_Then_verify_doc_view_images_tab_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and on_production_home_page and uncommit_last_production and navigate_to_session_search_page When open_doc_in_doc_view Then verify_doc_view_images_tab_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "8211");
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.uncommit_last_production(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.open_doc_in_doc_view(true, dataMap);
			context.verify_doc_view_images_tab_not_displayed(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_and_add_allproductionbatesranges_column_to_doc_and_on_production_home_page_and_uncommit_last_production_and_navigate_to_session_search_page_When_open_production_in_doc_list_Then_verify_allproductionbatesranges_not_displayed_on_uncommitted_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page and add_allproductionbatesranges_column_to_doc and on_production_home_page and uncommit_last_production and navigate_to_session_search_page When open_production_in_doc_list Then verify_allproductionbatesranges_not_displayed_on_uncommitted_production");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "8210");
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.add_allproductionbatesranges_column_to_doc(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.uncommit_last_production(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.open_production_in_doc_list(true, dataMap);
			context.verify_allproductionbatesranges_not_displayed_on_uncommitted_production(true, dataMap);
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

	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_and_completed_summary_preview_component_When_click_generate_button_Then_verify_state_of_generate_tab_after_generate_button_clicked() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component and completed_summary_preview_component When click_generate_button Then verify_state_of_generate_tab_after_generate_button_clicked");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.click_generate_button(true, dataMap);
			context.verify_state_of_generate_tab_after_generate_button_clicked(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_redacted_doc_folder_released_to_different_security_group_and_login_to_new_production_and_select_redaction_tag_from_doc_folder_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_document_selection_for_folder_released_to_security_group_and_the_production_is_generated_from_priv_guard_When_navigating_to_the_vm_production_location_Then_verify_production_generated_in_different_security_group() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given redacted_doc_folder_released_to_different_security_group and login_to_new_production and select_redaction_tag_from_doc_folder and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_document_selection_for_folder_released_to_security_group and the_production_is_generated_from_priv_guard When navigating_to_the_vm_production_location Then verify_production_generated_in_different_security_group");

		dataMap.put("ExtentTest",test);

		try {
			context.redacted_doc_folder_released_to_different_security_group(true, dataMap);
			context.login_to_new_production(true, dataMap);
			context.select_redaction_tag_from_doc_folder(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_document_selection_for_folder_released_to_security_group(true, dataMap);
			context.the_production_is_generated_from_priv_guard(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_production_generated_in_different_security_group(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_select_tiff_slipsheets_workproduct_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_production_generated_with_slipsheets_workproduct() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_tiff_slipsheets_workproduct and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_production_generated_with_slipsheets_workproduct");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_tiff_slipsheets_workproduct(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_production_generated_with_slipsheets_workproduct(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_opt_log_file_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_opt_log_file_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_opt_log_file_generated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_text_files_ingested_with_redactions_and_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_ocring_of_text_component_for_redacted_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given text_files_ingested_with_redactions and login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_ocring_of_text_component_for_redacted_docs");

		dataMap.put("ExtentTest",test);

		try {
			context.text_files_ingested_with_redactions(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_ocring_of_text_component_for_redacted_docs(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_select_different_dat_date_format_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_generated_production_date_format() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_different_dat_date_format and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_generated_production_date_format");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_different_dat_date_format(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_generated_production_date_format(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_native_docs_associated_to_tags_and_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_native_docs_associated_to_tags_are_copied() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given native_docs_associated_to_tags and login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_native_docs_associated_to_tags_are_copied");

		dataMap.put("ExtentTest",test);

		try {
			context.native_docs_associated_to_tags(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_native_docs_associated_to_tags_are_copied(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_docs_associated_with_priv_tag_and_login_to_new_production_and_select_dat_priv_tag_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_dat_priv_flag_for_generated_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given docs_associated_with_priv_tag and login_to_new_production and select_dat_priv_tag and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_dat_priv_flag_for_generated_production");

		dataMap.put("ExtentTest",test);

		try {
			context.docs_associated_with_priv_tag(true, dataMap);
			context.login_to_new_production(true, dataMap);
			context.select_dat_priv_tag(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_dat_priv_flag_for_generated_production(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_add_tiffpagecount_field_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_tiffpagecount_on_generated_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and add_tiffpagecount_field and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_tiffpagecount_on_generated_production");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.add_tiffpagecount_field(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_tiffpagecount_on_generated_production(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_add_dat_filename_field_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_generated_production_dat_row_for_each_doc() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and add_dat_filename_field and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_generated_production_dat_row_for_each_doc");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.add_dat_filename_field(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_generated_production_dat_row_for_each_doc(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_redacted_doc_folder_released_to_different_security_group_and_login_to_new_production_and_select_redaction_tag_from_doc_folder_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_document_selection_for_folder_released_to_security_group_and_the_production_is_generated_from_priv_guard_When_navigating_to_the_vm_production_location_Then_verify_count_of_docs_produced_in_different_security_group() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given redacted_doc_folder_released_to_different_security_group and login_to_new_production and select_redaction_tag_from_doc_folder and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_document_selection_for_folder_released_to_security_group and the_production_is_generated_from_priv_guard When navigating_to_the_vm_production_location Then verify_count_of_docs_produced_in_different_security_group");

		dataMap.put("ExtentTest",test);

		try {
			context.redacted_doc_folder_released_to_different_security_group(true, dataMap);
			context.login_to_new_production(true, dataMap);
			context.select_redaction_tag_from_doc_folder(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_document_selection_for_folder_released_to_security_group(true, dataMap);
			context.the_production_is_generated_from_priv_guard(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_count_of_docs_produced_in_different_security_group(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_ingest_landscape_and_portrait_layout_docs_and_login_to_new_production_and_select_tiff_rotate_90_clockwise_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_landscape_and_portrait_documents_doc_selection_and_the_production_is_generated_from_priv_guard_When_navigating_to_the_vm_production_location_Then_verify_generated_production_docs_rotation_applied() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given ingest_landscape_and_portrait_layout_docs and login_to_new_production and select_tiff_rotate_90_clockwise and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_landscape_and_portrait_documents_doc_selection and the_production_is_generated_from_priv_guard When navigating_to_the_vm_production_location Then verify_generated_production_docs_rotation_applied");

		dataMap.put("ExtentTest",test);

		try {
			context.ingest_landscape_and_portrait_layout_docs(true, dataMap);
			context.login_to_new_production(true, dataMap);
			context.select_tiff_rotate_90_clockwise(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_landscape_and_portrait_documents_doc_selection(true, dataMap);
			context.the_production_is_generated_from_priv_guard(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_generated_production_docs_rotation_applied(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_select_tiff_branding_bates_number_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_with_document_level_numbering_and_the_production_is_generated_from_document_selection_When_navigating_to_the_vm_production_location_Then_verify_bates_number_in_branding_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_tiff_branding_bates_number and complete_complex_production_component and marking_complete_the_next_available_bates_number_with_document_level_numbering and the_production_is_generated_from_document_selection When navigating_to_the_vm_production_location Then verify_bates_number_in_branding_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_tiff_branding_bates_number(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number_with_document_level_numbering(true, dataMap);
			context.the_production_is_generated_from_document_selection(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_bates_number_in_branding_displayed(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_pdf_ppt_files_with_redactions_exist_and_login_to_new_production_and_select_redaction_tags_with_placeholders_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_redacted_pdf_ppt_document_selection_and_the_production_is_generated_from_priv_guard_When_navigating_to_the_vm_production_location_Then_verify_text_generated_is_in_sync_with_pdf_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given pdf_ppt_files_with_redactions_exist and login_to_new_production and select_redaction_tags_with_placeholders and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_redacted_pdf_ppt_document_selection and the_production_is_generated_from_priv_guard When navigating_to_the_vm_production_location Then verify_text_generated_is_in_sync_with_pdf_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.pdf_ppt_files_with_redactions_exist(true, dataMap);
			context.login_to_new_production(true, dataMap);
			context.select_redaction_tags_with_placeholders(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_redacted_pdf_ppt_document_selection(true, dataMap);
			context.the_production_is_generated_from_priv_guard(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_text_generated_is_in_sync_with_pdf_generated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_upload_ice_dataset_without_file_extension_and_login_to_new_production_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_native_file_name_is_corrected_file_extension() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given upload_ice_dataset_without_file_extension and login_to_new_production and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_native_file_name_is_corrected_file_extension");

		dataMap.put("ExtentTest",test);

		try {
			context.upload_ice_dataset_without_file_extension(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_native_file_name_is_corrected_file_extension(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_dat_file_generated_with_dat_components() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_dat_file_generated_with_dat_components");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("prod_template", "false");
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_dat_file_generated_with_dat_components(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_keep_docs_with_no_master_date_sorting() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_keep_docs_with_no_master_date_sorting");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_keep_docs_with_no_master_date_sorting(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_in_project_with_ice_data_and_login_to_new_production_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_production_generated_with_ice_data() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given in_project_with_ice_data and login_to_new_production and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_production_generated_with_ice_data");

		dataMap.put("ExtentTest",test);

		try {
			context.in_project_with_ice_data(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_production_generated_with_ice_data(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_select_custodian_masterdate_sorting_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_sorting_by_metadata_first_second_sorting() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and select_custodian_masterdate_sorting and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_sorting_by_metadata_first_second_sorting");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.select_custodian_masterdate_sorting(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_sorting_by_metadata_first_second_sorting(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_redacted_image_doc_exists_and_login_to_new_production_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_and_navigating_to_the_vm_production_location_When_copy_paste_redacted_image_Then_verify_redacted_image_copied_with_redactions() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given redacted_image_doc_exists and login_to_new_production and complete_complex_production_component and the_production_is_generated_with_the_given_production_component and navigating_to_the_vm_production_location When copy_paste_redacted_image Then verify_redacted_image_copied_with_redactions");

		dataMap.put("ExtentTest",test);

		try {
			context.redacted_image_doc_exists(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.copy_paste_redacted_image(true, dataMap);
			context.verify_redacted_image_copied_with_redactions(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_failed_production_When_click_on_failed_production_error_Then_verify_production_generation_failed_information() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_failed_production When click_on_failed_production_error Then verify_production_generation_failed_information");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_failed_production(true, dataMap);
			context.click_on_failed_production_error(true, dataMap);
			context.verify_production_generation_failed_information(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_mp3_files_with_redactions_exist_and_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_mp3_document_selection_and_the_production_is_generated_from_priv_guard_When_navigating_to_the_vm_production_location_Then_verify_production_generation_with_redacted_mp3_files() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given mp3_files_with_redactions_exist and login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_mp3_document_selection and the_production_is_generated_from_priv_guard When navigating_to_the_vm_production_location Then verify_production_generation_with_redacted_mp3_files");

		dataMap.put("ExtentTest",test);

		try {
			context.mp3_files_with_redactions_exist(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("mp3", "true");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_mp3_document_selection(true, dataMap);
			context.the_production_is_generated_from_priv_guard(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_production_generation_with_redacted_mp3_files(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_no_productions_exist_in_project_and_login_to_new_production_and_complete_complex_production_component_When_click_here_to_view_and_select_the_next_bates_number_Then_verify_empty_next_bates_number_popup_message() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given no_productions_exist_in_project and login_to_new_production and complete_complex_production_component When click_here_to_view_and_select_the_next_bates_number Then verify_empty_next_bates_number_popup_message");

		dataMap.put("ExtentTest",test);

		try {
			context.no_productions_exist_in_project(true, dataMap);
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.click_here_to_view_and_select_the_next_bates_number(true, dataMap);
			context.verify_empty_next_bates_number_popup_message(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_docs_with_redactions_highlights_reviews_and_login_to_new_production_and_select_annotation_layer_tiff_pdf_and_complete_complex_production_component_without_changing_redactions_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_only_redactions_in_annotation_layer() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given docs_with_redactions_highlights_reviews and login_to_new_production and select_annotation_layer_tiff_pdf and complete_complex_production_component_without_changing_redactions and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_only_redactions_in_annotation_layer");

		dataMap.put("ExtentTest",test);

		try {
			context.docs_with_redactions_highlights_reviews(true, dataMap);
			context.login_to_new_production(true, dataMap);
			context.select_annotation_layer_tiff_pdf(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component_without_changing_redactions(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_only_redactions_in_annotation_layer(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_select_annotation_layer_tiff_and_complete_complex_production_component_without_changing_redactions_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_production_generated_with_redaction_text_annotation_layer() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_annotation_layer_tiff and complete_complex_production_component_without_changing_redactions and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_production_generated_with_redaction_text_annotation_layer");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_annotation_layer_tiff(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component_without_changing_redactions(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_production_generated_with_redaction_text_annotation_layer(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_login_to_new_production_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_default_document_selection_When_select_docs_without_family_docs_Then_verify_doclist_without_family_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_default_production_component and complete_default_numbering_and_sorting and complete_default_document_selection When select_docs_without_family_docs Then verify_doclist_without_family_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case","7858");
			context.login_to_new_production(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.select_docs_without_family_docs(true, dataMap);
			context.verify_doclist_without_family_docs(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_default_document_selection_and_on_the_doclist_from_the_document_section_When_clicking_the_back_to_source_button_Then_verify_the_doclist_navigation_returns_the_user_to_the_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting and complete_default_document_selection and on_the_doclist_from_the_document_section When clicking_the_back_to_source_button Then verify_the_doclist_navigation_returns_the_user_to_the_production");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio6@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.on_the_doclist_from_the_document_section(true, dataMap);
			context.clicking_the_back_to_source_button(true, dataMap);
			context.verify_the_doclist_navigation_returns_the_user_to_the_production(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_login_to_new_production_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_document_tag_selection_with_family_When_select_docs_with_family_docs_Then_verify_doclist_with_family_docs() throws Throwable
	{

		ExtentTest test = report.startTest("Given login_to_new_production and complete_default_production_component and complete_default_numbering_and_sorting and complete_document_tag_selection_with_family When select_docs_with_family_docs Then verify_doclist_with_family_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "7859");
			dataMap.put("uid", "sqa.consilio1@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("TestCaseId", "TC7859");
			dataMap.put("totalEntries", "5");
			context.login_to_new_production(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_document_tag_selection_with_family(true, dataMap);
			context.select_docs_with_family_docs(true, dataMap);
			context.verify_doclist_with_family_docs(true, dataMap);
			
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_and_completing_the_priv_guard_section_and_navigating_back_When_clicking_the_productions_mark_incomplete_button_Then_verify_the_remove_option_on_the_rule_is_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_and_sorting and complete_default_document_selection and complete_default_priv_guard_documents_are_matched and completing_the_priv_guard_section_and_navigating_back When clicking_the_productions_mark_incomplete_button Then verify_the_remove_option_on_the_rule_is_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "4466");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_and_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.completing_the_priv_guard_section_and_navigating_back(true, dataMap);
			context.clicking_the_productions_mark_incomplete_button(true, dataMap);
			context.verify_the_remove_option_on_the_rule_is_displayed(true, dataMap);
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

	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_the_productions_save_button_Then_verify_the_sorting_options_in_the_numbering_compoent_display_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_the_productions_save_button Then verify_the_sorting_options_in_the_numbering_compoent_display_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "4930");
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_the_productions_save_button(true, dataMap);
			context.verify_the_sorting_options_in_the_numbering_compoent_display_correctly(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_on_the_next_bates_number_dialog_When_clicking_the_x_button_on_the_next_bates_dialog_Then_verify_clicking_x_on_the_next_bates_number_dialog_does_not_populate_any_fields() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and on_the_next_bates_number_dialog When clicking_the_x_button_on_the_next_bates_dialog Then verify_clicking_x_on_the_next_bates_number_dialog_does_not_populate_any_fields");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "8364");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.on_the_next_bates_number_dialog(true, dataMap);
			context.clicking_the_x_button_on_the_next_bates_dialog(true, dataMap);
			context.verify_clicking_x_on_the_next_bates_number_dialog_does_not_populate_any_fields(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_the_productions_save_button_Then_verify_the_sorting_metadata_dropdowns_are_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_the_productions_save_button Then verify_the_sorting_metadata_dropdowns_are_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "8156");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_the_productions_save_button(true, dataMap);
			context.verify_the_sorting_metadata_dropdowns_are_sorted_alphabetically(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_the_use_metadata_field_radio_button_Then_verify_the_click_here_link_is_not_available_when_the_option_use_metadata_field_is_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When clicking_the_use_metadata_field_radio_button Then verify_the_click_here_link_is_not_available_when_the_option_use_metadata_field_is_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "8346");
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.clicking_the_use_metadata_field_radio_button(true, dataMap);
			context.verify_the_click_here_link_is_not_available_when_the_option_use_metadata_field_is_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally {
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	

	@Test(groups = {"Production, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_and_sorting_is_added_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_on_the_production_location_component_When_clicking_on_the_back_button_Then_verify_the_user_is_navigated_back_to_the_priv_guard() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_and_sorting_is_added and complete_default_document_selection and mark_complete_default_priv_guard and on_the_production_location_component When clicking_on_the_back_button Then verify_the_user_is_navigated_back_to_the_priv_guard");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "4467");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "6");
			dataMap.put("beginning_bates", "3");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.on_the_production_location_component(true, dataMap);
			context.clicking_on_the_back_button(true, dataMap);
			context.verify_the_user_is_navigated_back_to_the_priv_guard(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_completed_the_second_default_dat_section_and_completed_the_third_default_dat_section_When_clicking_the_productions_mark_complete_button_Then_verify_the_production_can_be_completed_with_multiple_dats_with_the_same_field_class() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and completed_the_second_default_dat_section and completed_the_third_default_dat_section When clicking_the_productions_mark_complete_button Then verify_the_production_can_be_completed_with_multiple_dats_with_the_same_field_class");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			context.completed_the_second_default_dat_section(true, dataMap);
			context.completed_the_third_default_dat_section(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_production_can_be_completed_with_multiple_dats_with_the_same_field_class(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_the_pdf_section_is_expanded_When_enabling_blank_page_removal_for_pdf_Then_verify_the_message_displayed_when_pdf_blank_page_removal_is_enabled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and the_pdf_section_is_expanded When enabling_blank_page_removal_for_pdf Then verify_the_message_displayed_when_pdf_blank_page_removal_is_enabled");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "6972");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.the_pdf_section_is_expanded(true, dataMap);
			context.enabling_blank_page_removal_for_pdf(true, dataMap);
			context.verify_the_message_displayed_when_pdf_blank_page_removal_is_enabled(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expanding_the_translations_components_Then_verify_the_generate_load_file_is_enabled_by_default_for_translation_components() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expanding_the_translations_components Then verify_the_generate_load_file_is_enabled_by_default_for_translation_components");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.expanding_the_translations_components(true, dataMap);
			context.verify_the_generate_load_file_is_enabled_by_default_for_translation_components(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expanding_the_mp3_components_Then_verify_the_generate_load_file_is_enabled_by_default_for_mp3_components() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expanding_the_mp3_components Then verify_the_generate_load_file_is_enabled_by_default_for_mp3_components");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.expanding_the_mp3_components(true, dataMap);
			context.verify_the_generate_load_file_is_enabled_by_default_for_mp3_components(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_completed_tiff_component_with_slip_sheet_When_clicking_the_productions_mark_complete_button_Then_verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and completed_{tiff}_component_with_slip_sheet When clicking_the_productions_mark_complete_button Then verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("component", "tiff");
			context.completed_component_with_slip_sheet(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_completed_pdf_component_with_slip_sheet_When_clicking_the_productions_mark_complete_button_Then_verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and completed_{pdf}_component_with_slip_sheet When clicking_the_productions_mark_complete_button Then verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("component", "pdf");
			context.completed_component_with_slip_sheet(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_production_is_marked_complete_after_a_tiff_with_a_slip_sheet_is_added(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_tiff_pdf_components_are_completed_without_a_dat_component_and_complete_the_dat_section_Bates_Number_When_clicking_the_productions_mark_complete_button_Then_verify_the_production_is_marked_complete_after_a_dat_is_added() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and tiff_pdf_components_are_completed_without_a_dat_component and complete_the_dat_section_{Bates_Number} When clicking_the_productions_mark_complete_button Then verify_the_production_is_marked_complete_after_a_dat_is_added");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.tiff_pdf_components_are_completed_without_a_dat_component(true, dataMap);
			dataMap.put("dat_value", "Bates_Number");
			context.complete_the_dat_section_(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_production_is_marked_complete_after_a_dat_is_added(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_tiff_pdf_components_are_completed_without_a_dat_component_and_complete_the_dat_section_Bates_space_Number_When_clicking_the_productions_mark_complete_button_Then_verify_the_production_is_marked_complete_after_a_dat_is_added() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and tiff_pdf_components_are_completed_without_a_dat_component and complete_the_dat_section_{Bates Number} When clicking_the_productions_mark_complete_button Then verify_the_production_is_marked_complete_after_a_dat_is_added");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.tiff_pdf_components_are_completed_without_a_dat_component(true, dataMap);
			dataMap.put("dat_value", "Bates Number");
			context.complete_the_dat_section_(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_production_is_marked_complete_after_a_dat_is_added(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_tiff_pdf_components_are_completed_without_a_dat_component_and_complete_the_dat_section_BatesNumber_When_clicking_the_productions_mark_complete_button_Then_verify_the_production_is_marked_complete_after_a_dat_is_added() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and tiff_pdf_components_are_completed_without_a_dat_component and complete_the_dat_section_{Bates-Number} When clicking_the_productions_mark_complete_button Then verify_the_production_is_marked_complete_after_a_dat_is_added");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.tiff_pdf_components_are_completed_without_a_dat_component(true, dataMap);
			dataMap.put("dat_value", "Bates-Number");
			context.complete_the_dat_section_(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_the_production_is_marked_complete_after_a_dat_is_added(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_expanding_clicking_the_field_classification_dropdown_Then_verify_the_bates_field_classification_is_listed_in_alphabetical_order() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When expanding_clicking_the_field_classification_dropdown Then verify_the_bates_field_classification_is_listed_in_alphabetical_order");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			context.expanding_clicking_the_field_classification_dropdown(true, dataMap);
			context.verify_the_bates_field_classification_is_listed_in_alphabetical_order(true, dataMap);
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



	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_native_section_with_file_types_When_clicking_the_productions_mark_complete_and_incomplete_button_Then_verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_native_section_with_{file_types} When clicking_the_productions_mark_complete_and_incomplete_button Then verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "6820|6821|6822");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("native_data", "file_types");
			context.complete_native_section_with_(true, dataMap);
			context.clicking_the_productions_mark_complete_and_incomplete_button(true, dataMap);
			context.verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_native_section_with_tags_When_clicking_the_productions_mark_complete_and_incomplete_button_Then_verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_native_section_with_{tags} When clicking_the_productions_mark_complete_and_incomplete_button Then verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("native_data", "tags");
			context.complete_native_section_with_(true, dataMap);
			context.clicking_the_productions_mark_complete_and_incomplete_button(true, dataMap);
			context.verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_native_section_with_files_and_tags_When_clicking_the_productions_mark_complete_and_incomplete_button_Then_verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_native_section_with_{files_and_tags} When clicking_the_productions_mark_complete_and_incomplete_button Then verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("native_data", "files_and_tags");
			context.complete_native_section_with_(true, dataMap);
			context.clicking_the_productions_mark_complete_and_incomplete_button(true, dataMap);
			context.verify_the_native_component_displays_the_saved_data_correctly_after_being_incompleted(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_the_native_checkbox_is_enabled_When_clicking_the_productions_mark_complete_button_Then_verify_an_error_message_is_returned_on_empty_native_components() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and the_native_checkbox_is_enabled When clicking_the_productions_mark_complete_button Then verify_an_error_message_is_returned_on_empty_native_components");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "6415");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			context.the_native_checkbox_is_enabled(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_an_error_message_is_returned_on_empty_native_components(true, dataMap);
		} catch (ImplementationException e) {
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_branding_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{tiff}_section_{branding}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio9@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "branding");
			dataMap.put("component", "tiff");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_placeholder_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{tiff}_section_{placeholder}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "placeholder");
			dataMap.put("component", "tiff");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_redactions_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{tiff}_section_{redactions}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "redactions");
			dataMap.put("component", "tiff");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_techissue_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{tiff}_section_{techissue}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "techissue");
			dataMap.put("component", "tiff");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_slipsheets_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{tiff}_section_{slipsheets}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "slipsheets");
			dataMap.put("component", "tiff");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_calculated_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{tiff}_section_{calculated}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "calculated");
			dataMap.put("component", "tiff");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_filetypes_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{tiff}_section_{filetypes}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "filetypes");
			dataMap.put("component", "tiff");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_pdf_section_branding_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{pdf}_section_{branding}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "branding");
			dataMap.put("component", "pdf");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_pdf_section_placeholder_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{pdf}_section_{placeholder}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "placeholder");
			dataMap.put("component", "pdf");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_pdf_section_redactions_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{pdf}_section_{redactions}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "redactions");
			dataMap.put("component", "pdf");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_pdf_section_techissue_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{pdf}_section_{techissue}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "techissue");
			dataMap.put("component", "pdf");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_pdf_section_slipsheets_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{pdf}_section_{slipsheets}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "slipsheets");
			dataMap.put("component", "pdf");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_pdf_section_calculated_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{pdf}_section_{calculated}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "calculated");
			dataMap.put("component", "pdf");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_pdf_section_filetypes_insert_metadata_field_dialog_When_clicking_the_insert_metadata_field_dropdown_Then_verify_the_metadata_field_dropdown_is_sorted_alphabetically() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_{pdf}_section_{filetypes}_insert_metadata_field_dialog When clicking_the_insert_metadata_field_dropdown Then verify_the_metadata_field_dropdown_is_sorted_alphabetically");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("area", "filetypes");
			dataMap.put("component", "pdf");
			context.on_the_section_insert_metadata_field_dialog(true, dataMap);
			context.clicking_the_insert_metadata_field_dropdown(true, dataMap);
			context.verify_the_metadata_field_dropdown_is_sorted_alphabetically(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_clicking_the_new_line_dropdown_Then_verify_the_dat_new_line_delimiters_are_displaying_from_the_dropdown() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When clicking_the_new_line_dropdown Then verify_the_dat_new_line_delimiters_are_displaying_from_the_dropdown");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "6327");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.expanding_the_dat_production_component(true, dataMap);
			context.clicking_the_new_line_dropdown(true, dataMap);
			context.verify_the_dat_new_line_delimiters_are_displaying_from_the_dropdown(true, dataMap);
		} catch (ImplementationException e) {
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_the_tiff_section_is_expanded_When_enabling_blank_page_removal_for_tiff_Then_verify_the_message_displayed_when_tiff_blank_page_removal_is_enabled() throws Throwable
	{

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and the_tiff_section_is_expanded When enabling_blank_page_removal_for_tiff Then verify_the_message_displayed_when_tiff_blank_page_removal_is_enabled");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("Test Case", "6972");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.the_tiff_section_is_expanded(true, dataMap);
			context.enabling_blank_page_removal_for_tiff(true, dataMap);
			context.verify_the_message_displayed_when_tiff_blank_page_removal_is_enabled(true, dataMap);
		} catch (ImplementationException e) {
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			context.delete_created_productions(true, dataMap);
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.delete_created_productions(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_the_tiff_section_is_enabled_with_burn_redactions_enabled_When_erase_the_placeholder_mark_complete_Then_verify_an_error_is_returned_when_a_blank_redaction_placeholder_is_marked_completed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and the_tiff_section_is_enabled_with_burn_redactions_enabled When erase_the_placeholder_mark_complete Then verify_an_error_is_returned_when_a_blank_redaction_placeholder_is_marked_completed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "automate.sqa2@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			context.the_tiff_section_is_enabled_with_burn_redactions_enabled(true, dataMap);
			context.erase_the_placeholder_mark_complete(true, dataMap);
			context.verify_an_error_is_returned_when_a_blank_redaction_placeholder_is_marked_completed(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_email_classification_is_added_for_dats_When_clicking_the_dats_source_field_dropdown_Then_verify_the_email_field_classification_has_the_correct_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and email_classification_is_added_for_dats When clicking_the_dats_source_field_dropdown Then verify_the_email_field_classification_has_the_correct_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.email_classification_is_added_for_dats(true, dataMap);
			context.clicking_the_dats_source_field_dropdown(true, dataMap);
			context.verify_the_email_field_classification_has_the_correct_options(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_the_second_dat_section_with_duplicate_information_When_clicking_the_productions_mark_complete_button_Then_verify_an_error_is_returned_from_using_duplicate_dat_fields_values() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_the_second_dat_section_with_duplicate_information When clicking_the_productions_mark_complete_button Then verify_an_error_is_returned_from_using_duplicate_dat_fields_values");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau1@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/Production/Home");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			context.complete_the_second_dat_section_with_duplicate_information(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
			context.verify_an_error_is_returned_from_using_duplicate_dat_fields_values(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_DRAFT_production_When_clicking_open_wizard_on_the_first_production_Then_verify_the_user_is_able_to_open_a_production_via_the_wizard() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{DRAFT}_production When clicking_open_wizard_on_the_first_production Then verify_the_user_is_able_to_open_a_production_via_the_wizard");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "3411");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "DRAFT");
			context.selecting_the_production(true, dataMap);
			context.clicking_open_wizard_on_the_first_production(true, dataMap);
			context.verify_the_user_is_able_to_open_a_production_via_the_wizard(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_clicking_add_new_production_Then_verify_the_basic_info_section_does_not_show_a_disclaimer() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When clicking_add_new_production Then verify_the_basic_info_section_does_not_show_a_disclaimer");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "11023");
			dataMap.put("uid", "sqa.consilio9@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.clicking_add_new_production(true, dataMap);
			context.verify_the_basic_info_section_does_not_show_a_disclaimer(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_store_the_first_productions_name_and_on_the_basic_info_component_on_a_new_production_and_enter_the_name_of_the_existing_production_When_clicking_the_productions_mark_complete_button_Then_verify_the_user_is_not_able_to_enter_a_dupe_name_for_productions() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and store_the_first_productions_name and on_the_basic_info_component_on_a_new_production and enter_the_name_of_the_existing_production When clicking_the_productions_mark_complete_button Then verify_the_user_is_not_able_to_enter_a_dupe_name_for_productions");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "4470");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.store_the_first_productions_name(true, dataMap);
			context.on_the_basic_info_component_on_a_new_production(true, dataMap);
			context.enter_the_name_of_the_existing_production(true, dataMap);
			context.verify_the_user_is_not_able_to_enter_a_dupe_name_for_productions(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_setting_the_sort_dropdown_by_production_name_Then_verify_the_sorting_of_the_productions_is_by_name() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When setting_the_sort_dropdown_by_production_name Then verify_the_sorting_of_the_productions_is_by_name");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "3708");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.setting_the_sort_dropdown_by_production_name(true, dataMap);
			context.verify_the_sorting_of_the_productions_is_by_name(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_grid_view_When_clicking_the_action_dropdown_Then_verify_the_add_doc_button_is_disabled_on_completed_productions() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{grid}_view When clicking_the_action_dropdown Then verify_the_add_doc_button_is_disabled_on_completed_productions");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "7777");
			dataMap.put("uid", "sqa.consilio9@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "grid");
			context.the_production_grid_is_set_to_view(true, dataMap);
			dataMap.put("status", "INPROGRESS");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_action_dropdown(true, dataMap);
			context.verify_the_add_doc_button_is_disabled_on_completed_productions(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_on_the_basic_info_component_on_a_new_production_and_a_valid_production_name_is_entered_and_a_valid_production_description_is_entered_When_clicking_the_productions_save_button_Then_verify_a_production_can_be_marked_completed_with_a_valid_name_description() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and on_the_basic_info_component_on_a_new_production and a_valid_production_name_is_entered and a_valid_production_description_is_entered When clicking_the_productions_save_button Then verify_a_production_can_be_marked_completed_with_a_valid_name_description");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "2909");
			dataMap.put("uid", "automate.sqa3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.on_the_basic_info_component_on_a_new_production(true, dataMap);
			dataMap.put("name", "AutoProduction");
			context.a_valid_production_name_is_entered(true, dataMap);
			dataMap.put("description", "!@123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891");
			context.a_valid_production_description_is_entered(true, dataMap);
			context.clicking_the_productions_save_button(true, dataMap);
			context.verify_a_production_can_be_marked_completed_with_a_valid_name_description(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_grid_view_and_selecting_the_COMPLETED_production_When_clicking_the_action_dropdown_Then_verify_the_remove_doc_button_is_disabled_on_completed_productions() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{grid}_view and selecting_the_{COMPLETED}_production When clicking_the_action_dropdown Then verify_the_remove_doc_button_is_disabled_on_completed_productions");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "7779");
			dataMap.put("uid", "sqa.consilio9@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "grid");
			context.the_production_grid_is_set_to_view(true, dataMap);
			//dataMap.put("status", "COMPLETED");
			dataMap.put("status", "INPROGRESS");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_action_dropdown(true, dataMap);
			context.verify_the_remove_doc_button_is_disabled_on_completed_productions(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_production_home_page_Then_verify_the_production_home_page_is_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau When on_production_home_page Then verify_the_production_home_page_is_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "4431");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.verify_the_production_home_page_is_displayed_correctly(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_the_production_grid_is_set_to_grid_view_When_clicking_the_production_name_column_Then_verify_the_sorting_of_the_productions_is_by_name_in_grid_view() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and the_production_grid_is_set_to_{grid}_view When clicking_the_production_name_column Then verify_the_sorting_of_the_productions_is_by_name_in_grid_view");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "3709");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("mode", "grid");
			context.the_production_grid_is_set_to_view(true, dataMap);
			context.clicking_the_production_name_column(true, dataMap);
			context.verify_the_sorting_of_the_productions_is_by_name_in_grid_view(true, dataMap);
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

	
	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_on_the_basic_info_component_on_a_new_production_and_a_valid_production_name_is_entered_and_a_valid_production_description_is_entered_When_clicking_the_productions_mark_complete_button_Then_verify_a_production_can_be_marked_completed_with_a_valid_name_description() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and on_the_basic_info_component_on_a_new_production and a_valid_production_name_is_entered and a_valid_production_description_is_entered When clicking_the_productions_mark_complete_button Then verify_a_production_can_be_marked_completed_with_a_valid_name_description");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "2909");
			dataMap.put("uid", "sqa.consilio9@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.on_the_basic_info_component_on_a_new_production(true, dataMap);
			dataMap.put("name", "AutoProduction!");
			context.a_valid_production_name_is_entered(true, dataMap);
			dataMap.put("description", "!@123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891123456789112345678911234567891");
			context.a_valid_production_description_is_entered(true, dataMap);
			context.clicking_the_productions_save_button(false, dataMap);
			context.verify_a_production_can_be_marked_completed_with_a_valid_name_description(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_DRAFT_production_When_deleting_the_first_production_listed_Then_verify_the_production_is_deleted_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{DRAFT}_production When deleting_the_first_production_listed Then verify_the_production_is_deleted_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "3402");
			dataMap.put("Test Case", "4128");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "DRAFT");
			context.selecting_the_production(true, dataMap);
			context.deleting_the_first_production_listed(true, dataMap);
			context.verify_the_production_is_deleted_successfully(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_navigate_back_to_the_production_home_page_and_store_the_first_productions_info_and_open_the_production_created_edit_the_name_then_save_using_save_and_navigate_back_to_the_production_home_page_When_the_production_grid_is_set_to_grid_view_Then_verify_the_date_created_field_is_reflecting_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and navigate_back_to_the_production_home_page and store_the_first_productions_info and open_the_production_created_edit_the_name_then_save_using_{save} and navigate_back_to_the_production_home_page When the_production_grid_is_set_to_{grid}_view Then verify_the_date_created_field_is_reflecting_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "7809");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			context.store_the_first_productions_info(true, dataMap);
			dataMap.put("save_option", "save");
			context.open_the_production_created_edit_the_name_then_save_using_(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			dataMap.put("mode", "grid");
			context.the_production_grid_is_set_to_view(true, dataMap);
			context.verify_the_date_created_field_is_reflecting_correctly(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_navigate_back_to_the_production_home_page_and_store_the_first_productions_info_and_open_the_production_created_edit_the_name_then_save_using_markcomplete_and_navigate_back_to_the_production_home_page_When_the_production_grid_is_set_to_grid_view_Then_verify_the_date_created_field_is_reflecting_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and navigate_back_to_the_production_home_page and store_the_first_productions_info and open_the_production_created_edit_the_name_then_save_using_{markcomplete} and navigate_back_to_the_production_home_page When the_production_grid_is_set_to_{grid}_view Then verify_the_date_created_field_is_reflecting_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "7809");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			context.store_the_first_productions_info(true, dataMap);
			dataMap.put("save_option", "markcomplete");
			context.open_the_production_created_edit_the_name_then_save_using_(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			dataMap.put("mode", "grid");
			context.the_production_grid_is_set_to_view(true, dataMap);
			context.verify_the_date_created_field_is_reflecting_correctly(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_navigate_back_to_the_production_home_page_and_store_the_first_productions_info_and_open_the_production_created_edit_the_name_then_save_using_save_When_navigate_back_to_the_production_home_page_Then_verify_the_last_modified_date_on_productions_gets_updated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and navigate_back_to_the_production_home_page and store_the_first_productions_info and open_the_production_created_edit_the_name_then_save_using_{save} When navigate_back_to_the_production_home_page Then verify_the_last_modified_date_on_productions_gets_updated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "7739|7740");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			context.store_the_first_productions_info(true, dataMap);
			dataMap.put("save_option", "save");
			context.open_the_production_created_edit_the_name_then_save_using_(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			context.verify_the_last_modified_date_on_productions_gets_updated(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_navigate_back_to_the_production_home_page_and_store_the_first_productions_info_and_open_the_production_created_edit_the_name_then_save_using_markcomplete_When_navigate_back_to_the_production_home_page_Then_verify_the_last_modified_date_on_productions_gets_updated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and navigate_back_to_the_production_home_page and store_the_first_productions_info and open_the_production_created_edit_the_name_then_save_using_{markcomplete} When navigate_back_to_the_production_home_page Then verify_the_last_modified_date_on_productions_gets_updated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("Test Case", "7739|7740");
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			context.store_the_first_productions_info(true, dataMap);
			dataMap.put("save_option", "markcomplete");
			context.open_the_production_created_edit_the_name_then_save_using_(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			context.verify_the_last_modified_date_on_productions_gets_updated(true, dataMap);
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

	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_When_starting_the_production_generation_Then_verify_bates_range_blank_on_generate_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component When starting_the_production_generation Then verify_bates_range_blank_on_generate_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_pregen_in_progress(true, dataMap);
			context.verify_bates_range_blank_on_generate_tab(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_and_completed_summary_preview_component_When_waiting_for_production_to_be_complete_Then_verify_bates_range_on_generate_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component and completed_summary_preview_component When waiting_for_production_to_be_complete Then verify_bates_range_on_generate_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_pregen_complete(true, dataMap);
			context.verify_bates_range_on_generate_tab(true, dataMap);
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

	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_and_completed_summary_preview_component_When_waiting_for_production_to_be_complete_Then_verify_export_bates_button_enabled_on_generate_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component and completed_summary_preview_component When waiting_for_production_to_be_complete Then verify_export_bates_button_enabled_on_generate_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_pregen_complete(true, dataMap);
			context.verify_export_bates_button_enabled_on_generate_tab(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_When_starting_the_production_generation_Then_verify_export_bates_button_displayed_on_generate_tab() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component When starting_the_production_generation Then verify_export_bates_button_displayed_on_generate_tab");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_pregen_in_progress(true, dataMap);
			context.verify_export_bates_button_displayed_on_generate_tab(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_failed_production_and_replace_seed_pdf_When_click_replace_seed_pdf_cancel_button_Then_verify_replace_seed_pdf_canceled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_failed_production and replace_seed_pdf When click_replace_seed_pdf_cancel_button Then verify_replace_seed_pdf_canceled");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_failed_production(true, dataMap);
			context.replace_seed_pdf(true, dataMap);
			context.click_replace_seed_pdf_cancel_button(true, dataMap);
			context.verify_replace_seed_pdf_canceled(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_without_updating_the_bates_number_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_and_completed_summary_preview_component_When_starting_the_production_generation_Then_verify_failed_generation_bates_range() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_without_updating_the_bates_number and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component and completed_summary_preview_component When starting_the_production_generation Then verify_failed_generation_bates_range");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_without_updating_the_bates_number(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.verify_failed_generation_bates_range(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_on_the_native_pdf_mp3_project_and_begin_new_production_process_and_enabling_natively_producted_documents_in_the_tiff_section_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_multimedia_file_group_with_native_files_generate_succesfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and on_the_native_pdf_mp3_project and begin_new_production_process and enabling_natively_producted_documents_in_the_tiff_section and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_multimedia_file_group_with_native_files_generate_succesfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.on_the_native_pdf_mp3_project(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			dataMap.put("tiff_file_type", "Multimedia");
			context.enabling_natively_producted_documents_in_the_tiff_section(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_multimedia_file_group_with_native_files_generate_succesfully(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_bates_number_generated_should_be_in_sync_with_actual_documents_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_bates_number_generated_should_be_in_sync_with_actual_documents_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "true");
			dataMap.put("pdf", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_bates_number_generated_should_be_in_sync_with_actual_documents_generated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_on_the_native_pdf_mp3_project_and_begin_new_production_process_and_enabling_natively_producted_documents_in_the_tiff_section_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_file_based_placeholdering_for_multimedia_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and on_the_native_pdf_mp3_project and begin_new_production_process and enabling_natively_producted_documents_in_the_tiff_section and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_file_based_placeholdering_for_multimedia_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.on_the_native_pdf_mp3_project(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			dataMap.put("tiff_file_type", "Multimedia");
			context.enabling_natively_producted_documents_in_the_tiff_section(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_file_based_placeholdering_for_multimedia_is_successful(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_the_default_dat_section_and_a_second_dat_field_is_added_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_native_path_are_populated_in_the_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_the_default_dat_section and a_second_dat_field_is_added and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_native_path_are_populated_in_the_production");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("field_classification", "File Path");
			dataMap.put("source_field", "NativePath");
			dataMap.put("dat_field", "AutomatedNativePath");
			context.a_second_dat_field_is_added(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_native_path_are_populated_in_the_production(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_adding_tags_to_native_component_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_production_was_generated_successfully_with_native_excluded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and adding_tags_to_native_component and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_production_was_generated_successfully_with_native_excluded");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.adding_tags_to_native_component(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "true");
			dataMap.put("pdf", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_production_was_generated_successfully_with_native_excluded(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_the_default_dat_section_and_a_second_dat_field_is_added_and_the_production_is_generated_with_the_given_production_component_and_navigate_back_to_the_production_home_page_and_select_the_production_created_and_regenerate_the_in_progress_production_When_storing_the_new_bates_number_Then_verify_regenerating_an_existing_production_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_the_default_dat_section and a_second_dat_field_is_added and the_production_is_generated_with_the_given_production_component and navigate_back_to_the_production_home_page and select_the_production_created and regenerate_the_in_progress_production When storing_the_new_bates_number Then verify_regenerating_an_existing_production_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("field_classification", "File Path");
			dataMap.put("source_field", "NativePath");
			dataMap.put("dat_field", "AutomatedNativePath");
			context.a_second_dat_field_is_added(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigate_back_to_the_production_home_page(true, dataMap);
			context.select_the_production_created(true, dataMap);
			context.regenerate_the_in_progress_production(true, dataMap);
			context.storing_the_new_bates_number(true, dataMap);
			context.verify_regenerating_an_existing_production_is_successful(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_the_pdf_tiff_is_enabled_for_multi_page_and_complete_complex_production_component_and_the_numbering_is_set_to_page_level_and_the_production_is_generated_with_the_given_production_component_numbering_When_navigating_to_the_vm_production_location_Then_verify_the_generated_production_has_bates_no_in_sync_with_the_document_page_number() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and the_pdf_tiff_is_enabled_for_multi_page and complete_complex_production_component and the_numbering_is_set_to_page_level and the_production_is_generated_with_the_given_production_component_numbering When navigating_to_the_vm_production_location Then verify_the_generated_production_has_bates_no_in_sync_with_the_document_page_number");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.the_pdf_tiff_is_enabled_for_multi_page(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_numbering_is_set_to_page_level(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component_numbering(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_generated_production_has_bates_no_in_sync_with_the_document_page_number(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_is_progressed_through_priv_guard_and_the_production_location_is_completed_with_split_sub_folders_and_the_production_is_generated_with_the_given_production_through_production_location_When_navigating_to_the_vm_production_location_Then_verify_the_split_count_should_split_the_productions_folders_by_the_number_entered() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_is_progressed_through_priv_guard and the_production_location_is_completed_with_split_sub_folders and the_production_is_generated_with_the_given_production_through_production_location When navigating_to_the_vm_production_location Then verify_the_split_count_should_split_the_productions_folders_by_the_number_entered");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_progressed_through_priv_guard(true, dataMap);
			context.the_production_location_is_completed_with_split_sub_folders(true, dataMap);
			context.the_production_is_generated_with_the_given_production_through_production_location(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_split_count_should_split_the_productions_folders_by_the_number_entered(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_enabling_tech_issue_docs_on_tiff_pdf_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_placeholder_text_is_available_in_the_generated_placeholder_pdfs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and enabling_tech_issue_docs_on_tiff_pdf and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_placeholder_text_is_available_in_the_generated_placeholder_pdfs");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.enabling_tech_issue_docs_on_tiff_pdf(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_placeholder_text_is_available_in_the_generated_placeholder_pdfs(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_enabling_natively_produced_documents_in_the_tiff_section_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_placeholdering_is_enabled_for_non_priv_documents() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and enabling_natively_produced_documents_in_the_tiff_section and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_placeholdering_is_enabled_for_non_priv_documents");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("file_type", "Word / Text Files (.doc, .txt, .rtf, etc.)");
			context.enabling_natively_produced_documents_in_the_tiff_section(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_placeholdering_is_enabled_for_non_priv_documents(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_the_default_dat_section_and_a_second_dat_field_is_added_and_a_third_dat_field_is_added_and_enabling_tech_issue_docs_on_tiff_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_the_document_selection_is_completed_and_the_production_is_generated_with_the_given_production_component_through_document_When_navigating_to_the_vm_production_location_Then_verify_tech_issue_docs_display_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_the_default_dat_section and a_second_dat_field_is_added and a_third_dat_field_is_added and enabling_tech_issue_docs_on_tiff and complete_complex_production_component and marking_complete_the_next_available_bates_number and the_document_selection_is_completed and the_production_is_generated_with_the_given_production_component_through_document When navigating_to_the_vm_production_location Then verify_tech_issue_docs_display_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.complete_the_default_dat_section(true, dataMap);
			dataMap.put("field_classification", "Production");
			dataMap.put("source_field", "TIFFPageCount");
			dataMap.put("dat_field", "count");
			context.a_second_dat_field_is_added(true, dataMap);
			dataMap.put("field_classification", "Doc Basic");
			dataMap.put("source_field", "DocID");
			dataMap.put("dat_field", "doc");
			context.a_third_dat_field_is_added(true, dataMap);
			context.enabling_tech_issue_docs_on_tiff(true, dataMap);
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			dataMap.put("doc_selection", "Techincal_Issue");
			dataMap.put("doc_options", "Select Tags");
			context.the_document_selection_is_completed(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component_through_document(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_tech_issue_docs_display_correctly(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_document_tag_selection_with_family_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_and_completed_summary_preview_component_and_starting_the_production_generation_and_waiting_for_production_to_be_complete_When_navigating_to_the_vm_production_location_Then_verify_the_production_was_generated_successfully_with_family_documents() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_document_tag_selection_with_family and mark_complete_default_priv_guard and complete_default_production_location_component and completed_summary_preview_component and starting_the_production_generation and waiting_for_production_to_be_complete When navigating_to_the_vm_production_location Then verify_the_production_was_generated_successfully_with_family_documents");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_document_tag_selection_with_family(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_production_was_generated_successfully_with_family_documents(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_clicking_review_production_Then_verify_productions_generated_with_password_protexted_documents_are_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When clicking_review_production Then verify_productions_generated_with_password_protexted_documents_are_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.clicking_review_production(true, dataMap);
			context.verify_productions_generated_with_password_protexted_documents_are_successful(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_multi_page_documents_are_generated_in_single_pages() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_multi_page_documents_are_generated_in_single_pages");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_multi_page_documents_are_generated_in_single_pages(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_enabling_Emailmsg_Notes_eml_etc_natively_produced_documents_in_the_tiff_section_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and enabling_{Email(.msg,Notes,.eml,etc.)}_natively_produced_documents_in_the_tiff_section and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("file_type", "Email (.msg, Notes, .eml, etc.)");
			context.enabling_natively_produced_documents_in_the_tiff_section(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_enabling_ArchiveFileszip_rar_etc_natively_produced_documents_in_the_tiff_section_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and enabling_{ArchiveFiles(.zip,.rar,etc.)}_natively_produced_documents_in_the_tiff_section and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("file_type", "Archive Files (.zip, .rar, etc.)");
			context.enabling_natively_produced_documents_in_the_tiff_section(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_enabling_AdobePortableDocumentFormatpdf_natively_produced_documents_in_the_tiff_section_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and enabling_{AdobePortableDocumentFormat(.pdf)}_natively_produced_documents_in_the_tiff_section and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("file_type", "Adobe Portable Document Format (.pdf)");
			context.enabling_natively_produced_documents_in_the_tiff_section(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_placeholder_and_redaction_text_is_generated_for_selected_file_types_on_the_generated_production(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_with_document_level_numbering_and_complete_default_document_selection_and_the_production_is_generated_with_the_given_production_component_through_document_When_clicking_review_production_Then_verify_the_production_is_generated_successfully_without_any_errors() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number_with_document_level_numbering and complete_default_document_selection and the_production_is_generated_with_the_given_production_component_through_document When clicking_review_production Then verify_the_production_is_generated_successfully_without_any_errors");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number_with_document_level_numbering(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component_through_document(true, dataMap);
			context.clicking_review_production(true, dataMap);
			context.verify_the_production_is_generated_successfully_without_any_errors(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_production_generated_keeps_the_redaction_style_on_redacted_documents() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_production_generated_keeps_the_redaction_style_on_redacted_documents");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_production_generated_keeps_the_redaction_style_on_redacted_documents(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_entering_tiff_pdf_branding_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_the_production_generated_should_have_branding_wrap_across_the_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and entering_tiff_pdf_branding and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_the_production_generated_should_have_branding_wrap_across_the_document");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("branding_value", "A very long branding should be displayed here for test case purposes. We want this branding to wrap across the page and not go off the page. The branding should also be centered on the page instead of left aligned. This is the expected result and should be wrapping around the page when the production is generated successfully.");
			context.entering_tiff_pdf_branding(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_production_generated_should_have_branding_wrap_across_the_document(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_enabling_slip_sheets_on_tiff_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_document_tag_selection_with_family_and_the_production_is_generated_with_the_given_production_component_through_document_When_navigating_to_the_vm_production_location_Then_verify_the_production_bates_range_displays_on_the_production_slip_sheet() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and enabling_slip_sheets_on_tiff and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_document_tag_selection_with_family and the_production_is_generated_with_the_given_production_component_through_document When navigating_to_the_vm_production_location Then verify_the_production_bates_range_displays_on_the_production_slip_sheet");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("sheet_value", "ProductionBatesRange");
			dataMap.put("sheet_tab", "Calculated");
			context.enabling_slip_sheets_on_tiff(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_document_tag_selection_with_family(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component_through_document(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_the_production_bates_range_displays_on_the_production_slip_sheet(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_numbering_is_set_to_document_and_the_production_is_generated_with_the_given_production_component_through_document_When_clicking_review_production_Then_verify_productions_are_generated_succesfully_for_mp3_files_using_document_numbering() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_numbering_is_set_to_document and the_production_is_generated_with_the_given_production_component_through_document When clicking_review_production Then verify_productions_are_generated_succesfully_for_mp3_files_using_document_numbering");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("mp3", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_numbering_is_set_to_document(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component_through_document(true, dataMap);
			context.clicking_review_production(true, dataMap);
			context.verify_productions_are_generated_succesfully_for_mp3_files_using_document_numbering(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_is_generated_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_if_no_rotation_specified_generated_productions_retain_thieir_layout() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_is_generated_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_if_no_rotation_specified_generated_productions_retain_thieir_layout");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_is_generated_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_if_no_rotation_specified_generated_productions_retain_thieir_layout(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_tiff_branding_for_image_docs_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_branding_applied_for_image_docs_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_tiff_branding_for_image_docs and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_branding_applied_for_image_docs_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_tiff_branding_for_image_docs(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_branding_applied_for_image_docs_generated(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_blank_page_removal_tiff_pdf_component_false_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_blank_page_removal_generated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_blank_page_removal_tiff_pdf_component_{false} and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_blank_page_removal_generated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("blank_page_removal", "false");
			context.select_blank_page_removal_tiff_pdf_component_(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_blank_page_removal_generated_correctly(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_blank_page_removal_tiff_pdf_component_true_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_blank_page_removal_generated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_blank_page_removal_tiff_pdf_component_{true} and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_blank_page_removal_generated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("blank_page_removal", "true");
			context.select_blank_page_removal_tiff_pdf_component_(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_blank_page_removal_generated_correctly(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_tiff_clockwise_rotation_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_rotate_90_degrees_clockwise_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_tiff_clockwise_rotation and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_rotate_90_degrees_clockwise_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_tiff_clockwise_rotation(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_rotate_90_degrees_clockwise_generated(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_tiff_natively_produced_docs_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_production_generated_with_database_file_types() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_tiff_natively_produced_docs and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_production_generated_with_database_file_types");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("file_type", "Database Files (.mdb, .mdf, etc)");
			context.select_tiff_natively_produced_docs(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_production_generated_with_database_file_types(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_branding_for_all_pages_of_redacted_image_docs_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_branding_applied_on_all_pages_for_redacted_image_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_branding_for_all_pages_of_redacted_image_docs and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_branding_applied_on_all_pages_for_redacted_image_docs");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_branding_for_all_pages_of_redacted_image_docs(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_branding_applied_on_all_pages_for_redacted_image_docs(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_native_file_types_component_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_production_generated_with_database_file_types() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_native_file_types_component and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_production_generated_with_database_file_types");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("file_type", "Database Files (.mdb, .mdf, etc)");
			context.select_native_file_types_component(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_production_generated_with_database_file_types(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_pdf_branding_for_image_docs_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_branding_applied_for_image_docs_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_pdf_branding_for_image_docs and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_branding_applied_for_image_docs_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_pdf_branding_for_image_docs(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_branding_applied_for_image_docs_generated(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_endingbates_dat_component_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_endingbates_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_endingbates_dat_component and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_endingbates_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			context.select_endingbates_dat_component(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_endingbates_generated(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_dat_component_checkboxes_truetrue_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_redactions_and_privileged_checkboxes_in_generation() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_dat_component_checkboxes_{true}{true} and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_redactions_and_privileged_checkboxes_in_generation");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat_redactions", "true");
			dataMap.put("dat_privileged", "true");
			context.select_dat_component_checkboxes_(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_redactions_and_privileged_checkboxes_in_generation(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_tiff_placeholdering_WordTextFilesEmailDefaultAutomationTagAttorney_Client_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_placeholder_for_file_types_and_tags_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_tiff_placeholdering_{Word/TextFiles}{Email}{DefaultAutomationTag}{Attorney_Client} and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_placeholder_for_file_types_and_tags_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("placeholder_tag2", "Attorney_Client");
			dataMap.put("placeholder_file_type2", "Email");
			dataMap.put("placeholder_file_type1", "Word/Text Files");
			dataMap.put("placeholder_tag1", "Default Automation Tag");
			context.select_tiff_placeholdering_(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_placeholder_for_file_types_and_tags_generated(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_tiff_placeholdering_DefaultAutomationTagAttorney_Client_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_placeholder_for_file_types_and_tags_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_tiff_placeholdering_{}{}{DefaultAutomationTag}{Attorney_Client} and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_placeholder_for_file_types_and_tags_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("placeholder_tag2", "Attorney_Client");
			dataMap.put("placeholder_file_type2", "");
			dataMap.put("placeholder_file_type1", "");
			dataMap.put("placeholder_tag1", "Default Automation Tag");
			context.select_tiff_placeholdering_(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_placeholder_for_file_types_and_tags_generated(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_tiff_placeholdering_WordTextFilesEmail_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_placeholder_for_file_types_and_tags_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_tiff_placeholdering_{Word/TextFiles}{Email}{}{} and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_placeholder_for_file_types_and_tags_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("placeholder_tag2", "");
			dataMap.put("placeholder_file_type2", "Email");
			dataMap.put("placeholder_file_type1", "Word/Text Files");
			dataMap.put("placeholder_tag1", "");
			context.select_tiff_placeholdering_(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_placeholder_for_file_types_and_tags_generated(true, dataMap);
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
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_pdf_with_comments_signature_document_selection_and_the_production_is_generated_from_document_selection_When_navigating_to_the_vm_production_location_Then_verify_production_generated_with_comments_signature() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_pdf_with_comments_signature_document_selection and the_production_is_generated_from_document_selection When navigating_to_the_vm_production_location Then verify_production_generated_with_comments_signature");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "false");
			context.complete_complex_production_component(true, dataMap);
			context.marking_complete_the_next_available_bates_number(true, dataMap);
			context.complete_pdf_with_comments_signature_document_selection(true, dataMap);
			context.the_production_is_generated_from_document_selection(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_production_generated_with_comments_signature(true, dataMap);
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
	public void test_Given_login_to_new_production_and_select_dat_component_checkboxes_truefalse_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_When_navigating_to_the_vm_production_location_Then_verify_redactions_and_privileged_checkboxes_in_generation() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and select_dat_component_checkboxes_{true}{false} and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component When navigating_to_the_vm_production_location Then verify_redactions_and_privileged_checkboxes_in_generation");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat_redactions", "true");
			dataMap.put("dat_privileged", "false");
			context.select_dat_component_checkboxes_(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "false");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
			context.verify_redactions_and_privileged_checkboxes_in_generation(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_pdf_production_component_and_custom_number_sorting_is_added_1_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_the_production_can_be_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_pdf_production_component and custom_number_sorting_is_added_{1} and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_the_production_can_be_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_pdf_production_component(true, dataMap);
			dataMap.put("index", "1");
			dataMap.put("prefix", "P");
			dataMap.put("min_length", "9");
			dataMap.put("beginning_bates", "1");
			dataMap.put("suffix", "S");
			context.custom_number_sorting_is_added_(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_the_production_can_be_downloaded(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_pdf_production_component_and_custom_number_sorting_is_added_2_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_the_production_can_be_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_pdf_production_component and custom_number_sorting_is_added_{2} and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_the_production_can_be_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_pdf_production_component(true, dataMap);
			dataMap.put("index", "2");
			dataMap.put("prefix", "BVaoKSuX6r1HpAtlV2FOy3SgMt5tSZj2kHD66BxEKXFjTwd5nA");
			dataMap.put("min_length", "5");
			dataMap.put("beginning_bates", "1");
			dataMap.put("suffix", "BVaoKSuX6r1HpAtlV2FOy3SgMt5tSZj2kHD66BxEKXFjTwd5nA");
			context.custom_number_sorting_is_added_(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_the_production_can_be_downloaded(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_refresh_back_to_production_home_page_Then_verify_production_creation_date_is_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When refresh_back_to_production_home_page Then verify_production_creation_date_is_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.verify_production_creation_date_is_displayed(true, dataMap);
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


	@Test(groups = {"Production, Positive", "smoke7"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expand_dat_section_Then_verify_email_source_field_options_available() throws Throwable
	{
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expand_dat_section Then verify_email_source_field_options_available");

		dataMap.put("ExtentTest",test);

		try {
			HashSet<String> sourceFieldSet = new HashSet<>();
			JSONArray sourceFields = (JSONArray) dataMap.get("datSourceFieldsToVerify");
     	    Iterator<JSONArray> optionsList = sourceFields.iterator();
	        while (optionsList.hasNext()) {
	        	JSONArray option = optionsList.next();
	     	    Iterator<JSONObject> iterator = option.iterator();
		        while (iterator.hasNext()) {
		        	JSONObject data = iterator.next();
		        	sourceFieldSet.add((String)data.get("value"));
		        }
	        }
	        dataMap.put("sourceFieldsToVerify", sourceFieldSet);

			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.expand_dat_section(true, dataMap);
			context.verify_email_source_field_options_available(true, dataMap);
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


	@Test(groups = {"Production, Positive", "smoke7"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expand_tiff_pdf_section_Then_verify_tiff_pdf_metadata_fields_sorted() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expand_tiff_pdf_section Then verify_tiff_pdf_metadata_fields_sorted");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.expand_tiff_pdf_section(true, dataMap);
			context.verify_tiff_pdf_metadata_fields_sorted(true, dataMap);
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


	@Test(groups = {"Production, Positive", "smoke7"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_click_add_new_production_link_Then_verify_validation_on_production_components() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When click_add_new_production_link Then verify_validation_on_production_components");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.click_add_new_production_link(true, dataMap);
			context.verify_validation_on_production_components(true, dataMap);
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



	@Test(groups = {"Production, Positive","Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_click_add_a_new_production_Then_verify_basic_info_section() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When click_add_a_new_production Then verify_basic_info_section");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.click_add_a_new_production(true, dataMap);
			context.verify_basic_info_section(true, dataMap);
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



	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_create_new_production_export_set_When_select_newly_created_production_set_Then_verify_the_production_set_can_be_edited() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and create_new_production_export_set When select_newly_created_production_set Then verify_the_production_set_can_be_edited");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.create_new_production_export_set(true, dataMap);
			context.select_newly_created_production_set(true, dataMap);
			context.verify_the_production_set_can_be_edited(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_and_sorting_is_added_and_complete_default_document_selection_and_mark_complete_default_priv_guard_and_complete_default_production_location_component_and_completed_summary_preview_component_and_clicking_the_production_generate_button_and_verify_a_complex_production_is_able_to_be_generated_and_waiting_for_production_to_be_complete_and_refresh_back_to_production_home_page_and_save_production_as_custom_template_When_on_the_manage_templates_tab_Then_verify_production_can_be_saved_as_template() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_and_sorting_is_added and complete_default_document_selection and mark_complete_default_priv_guard and complete_default_production_location_component and completed_summary_preview_component and clicking_the_production_generate_button and verify_a_complex_production_is_able_to_be_generated and waiting_for_production_to_be_complete and refresh_back_to_production_home_page and save_production_as_custom_template When on_the_manage_templates_tab Then verify_production_can_be_saved_as_template");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			context.complete_complex_production_component(true, dataMap);
			dataMap.put("prefix", "S");
			dataMap.put("min_length", "8");
			dataMap.put("beginning_bates", "6");
			dataMap.put("suffix", "Q");
			context.custom_number_and_sorting_is_added(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_default_priv_guard(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("tiff", "true");
			context.verify_a_complex_production_is_able_to_be_generated(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.save_production_as_custom_template(true, dataMap);
			context.on_the_manage_templates_tab(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4637|4638");
			context.verify_production_can_be_saved_as_template(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_pdf_production_component_and_complete_default_production_location_component_and_custom_number_sorting_is_added_2_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_the_production_can_be_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_pdf_production_component and complete_default_production_location_component and custom_number_sorting_is_added_{2} and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_the_production_can_be_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_pdf_production_component(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			dataMap.put("index", "2");
			dataMap.put("prefix", "BVaoKSuX6r1HpAtlV2FOy3SgMt5tSZj2kHD66BxEKXFjTwd5nA");
			dataMap.put("min_length", "5");
			dataMap.put("beginning_bates", "1");
			dataMap.put("suffix", "BVaoKSuX6r1HpAtlV2FOy3SgMt5tSZj2kHD66BxEKXFjTwd5nA");
			context.custom_number_sorting_is_added_(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_the_production_can_be_downloaded(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_pdf_production_component_and_complete_default_production_location_component_and_custom_number_sorting_is_added_1_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_the_production_can_be_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_pdf_production_component and complete_default_production_location_component and custom_number_sorting_is_added_{1} and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_the_production_can_be_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_pdf_production_component(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			dataMap.put("index", "1");
			dataMap.put("prefix", "P");
			dataMap.put("min_length", "9");
			dataMap.put("beginning_bates", "1");
			dataMap.put("suffix", "S");
			context.custom_number_sorting_is_added_(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_the_production_can_be_downloaded(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_dat_and_text_file_are_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_dat_and_text_file_are_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("text", "true");
			context.complete_complex_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_dat_and_text_file_are_downloaded(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_no_option_to_remove_documents() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_no_option_to_remove_documents");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_no_option_to_remove_documents(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_add_tiff_component_with_file_type_only_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_placeholder_text_for_file_types_in_tiff() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and add_tiff_component_with_file_type_only and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_placeholder_text_for_file_types_in_tiff");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("file_type", "Database Files (.mdb, .mdf, etc.)");
			context.add_tiff_component_with_file_type_only(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_placeholder_text_for_file_types_in_tiff(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_pdf_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_mark_complete_priv_guard_section_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_pdf_is_generated_on_completed_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_pdf_production_component and complete_default_numbering_sorting and complete_default_document_selection and mark_complete_priv_guard_section and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_pdf_is_generated_on_completed_production");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			context.complete_pdf_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_priv_guard_section(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_pdf_is_generated_on_completed_production(true, dataMap);
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



	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_manage_project_fields_page_Then_verify_is_searchable_false_for_all_production_bates_ranges() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau When on_manage_project_fields_page Then verify_is_searchable_false_for_all_production_bates_ranges");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio.testproject1@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_manage_project_fields_page(true, dataMap);
			context.verify_is_searchable_false_for_all_production_bates_ranges(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_manage_project_fields_page_When_click_edit_button_on_allproductionbatesranges_Then_verify_help_text_for_allproductionbatesranges() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_manage_project_fields_page When click_edit_button_on_allproductionbatesranges Then verify_help_text_for_allproductionbatesranges");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio.testproject1@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_manage_project_fields_page(true, dataMap);
			context.click_edit_button_on_allproductionbatesranges(true, dataMap);
			context.verify_help_text_for_allproductionbatesranges(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_manage_project_fields_page_When_click_edit_button_on_allproductionbatesranges_Then_verify_is_searchable_is_disabled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_manage_project_fields_page When click_edit_button_on_allproductionbatesranges Then verify_is_searchable_is_disabled");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio.testproject1@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			context.on_manage_project_fields_page(true, dataMap);
			dataMap.put("A", "");
			context.click_edit_button_on_allproductionbatesranges(true, dataMap);
			context.verify_is_searchable_is_disabled(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_pdf_production_component_and_complete_default_production_location_component_and_custom_number_sorting_is_added_1_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_0_docs_with_multiple_branding_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_pdf_production_component and complete_default_production_location_component and custom_number_sorting_is_added_{1} and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_0_docs_with_multiple_branding_tags");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_pdf_production_component(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			dataMap.put("index", "1");
			dataMap.put("prefix", "P");
			dataMap.put("min_length", "9");
			dataMap.put("beginning_bates", "1");
			dataMap.put("suffix", "S");
			context.custom_number_sorting_is_added_(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_0_docs_with_multiple_branding_tags(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_pdf_production_component_and_complete_default_production_location_component_and_custom_number_sorting_is_added_2_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_0_docs_with_multiple_branding_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_pdf_production_component and complete_default_production_location_component and custom_number_sorting_is_added_{2} and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_0_docs_with_multiple_branding_tags");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_pdf_production_component(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			dataMap.put("index", "2");
			dataMap.put("prefix", "BVaoKSuX6r1HpAtlV2FOy3SgMt5tSZj2kHD66BxEKXFjTwd5nA");
			dataMap.put("min_length", "5");
			dataMap.put("beginning_bates", "1");
			dataMap.put("suffix", "BVaoKSuX6r1HpAtlV2FOy3SgMt5tSZj2kHD66BxEKXFjTwd5nA");
			context.custom_number_sorting_is_added_(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_0_docs_with_multiple_branding_tags(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_refresh_back_to_production_home_page_Then_verify_production_end_date_is_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When refresh_back_to_production_home_page Then verify_production_end_date_is_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.verify_production_end_date_is_displayed(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_commit_and_wait_for_uncommit_button_to_display_Then_verify_uncommit_production_notification() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When commit_and_wait_for_uncommit_button_to_display Then verify_uncommit_production_notification");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.commit_and_wait_for_uncommit_button_to_display(true, dataMap);
			context.verify_uncommit_production_notification(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_completed_production_notification() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_completed_production_notification");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_completed_production_notification(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_additional_documents_cannot_be_added_after_production_is_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_additional_documents_cannot_be_added_after_production_is_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_additional_documents_cannot_be_added_after_production_is_generated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_add_tiff_component_with_log_file_type_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_production_log_file_is_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and add_tiff_component_with_log_file_type and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_production_log_file_is_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.add_tiff_component_with_log_file_type(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_production_log_file_is_generated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_When_complete_blank_priv_guard_check_Then_verify_production_location_toggle_buttons() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection When complete_blank_priv_guard_check Then verify_production_location_toggle_buttons");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.verify_production_location_toggle_buttons(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_commit_production_notification() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_commit_production_notification");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_commit_production_notification(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_add_text_component_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_text_file_is_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and add_text_component and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_text_file_is_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("format", "Unicode UTF-16");
			context.add_text_component(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_text_file_is_generated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_pdf_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_mark_complete_priv_guard_section_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_dat_file_Then_verify_the_bates_in_production_dat_file() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_pdf_production_component and complete_default_numbering_sorting and complete_default_document_selection and mark_complete_priv_guard_section and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production_dat_file Then verify_the_bates_in_production_dat_file");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			context.complete_pdf_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_priv_guard_section(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production_dat_file(true, dataMap);
			context.verify_the_bates_in_production_dat_file(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_add_native_component_with_specific_type_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_document_selection_with_specific_tags_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_native_generation_with_jpg_and_ppt_file() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and add_native_component_with_specific_type and complete_default_production_component and complete_default_numbering_sorting and complete_document_selection_with_specific_tags and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_native_generation_with_jpg_and_ppt_file");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("tags", "Automation Image Tag|Automation Presentation Tag");
			dataMap.put("images", "true");
			dataMap.put("presentation_files", "true");
			context.add_native_component_with_specific_type(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			dataMap.put("tags", "Automation Image Tag|Automation Presentation Tag");
			context.complete_document_selection_with_specific_tags(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_native_generation_with_jpg_and_ppt_file(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_dat_file_is_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_dat_file_is_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_dat_file_is_generated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_tiff_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_mark_complete_priv_guard_section_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_tiff_is_generated_on_completed_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_tiff_production_component and complete_default_numbering_sorting and complete_default_document_selection and mark_complete_priv_guard_section and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_tiff_is_generated_on_completed_production");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			context.complete_tiff_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.mark_complete_priv_guard_section(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_tiff_is_generated_on_completed_production(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_IN_PROGRESS_production_When_clicking_the_action_dropdown_Then_verify_production_delete_option_disabled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{IN_PROGRESS}_production When clicking_the_action_dropdown Then verify_production_delete_option_disabled");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "IN_PROGRESS");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_action_dropdown(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4129");
			context.verify_production_delete_option_disabled(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_selecting_the_COMPLETED_production_When_clicking_the_action_dropdown_Then_verify_production_delete_option_disabled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and selecting_the_{COMPLETED}_production When clicking_the_action_dropdown Then verify_production_delete_option_disabled");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("status", "COMPLETED");
			context.selecting_the_production(true, dataMap);
			context.clicking_the_action_dropdown(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4129");
			context.verify_production_delete_option_disabled(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_click_productions_grid_view_button_Then_verify_pagination_displayed_for_productions_grid() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When click_productions_grid_view_button Then verify_pagination_displayed_for_productions_grid");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.click_productions_grid_view_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3713");
			context.verify_pagination_displayed_for_productions_grid(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_add_pdf_production_component_with_branding_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_generated_pdf_has_branding_text() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and add_pdf_production_component_with_branding and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_generated_pdf_has_branding_text");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.add_pdf_production_component_with_branding(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_generated_pdf_has_branding_text(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_and_download_the_production_dat_file_and_save_data_from_dat_file_into_datamap_and_navigate_back_to_summary_and_mark_incomplete_complete_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_regenerate_uses_same_bates_numbers() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated and download_the_production_dat_file and save_data_from_dat_file_into_datamap and navigate_back_to_summary_and_mark_incomplete_complete and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_regenerate_uses_same_bates_numbers");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production_dat_file(true, dataMap);
			context.save_data_from_dat_file_into_datamap(true, dataMap);
			context.navigate_back_to_summary_and_mark_incomplete_complete(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_regenerate_uses_same_bates_numbers(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_complete_default_numbering_sorting_Then_verify_total_doc_count_is_shown_after_document_selection_completed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component When complete_default_numbering_sorting Then verify_total_doc_count_is_shown_after_document_selection_completed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.verify_total_doc_count_is_shown_after_document_selection_completed(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_document_selection_with_search_default_security_group_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_the_production_can_be_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_document_selection_with_search_default_security_group and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_the_production_can_be_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_document_selection_with_search_default_security_group(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_the_production_can_be_downloaded(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_all_components_can_be_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_all_components_can_be_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.verify_all_components_can_be_selected(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_bates_range_is_not_blank() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_bates_range_is_not_blank");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.verify_bates_range_is_not_blank(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_and_select_different_documents_on_completed_production_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_new_documents_can_be_regenerated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated and select_different_documents_on_completed_production and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_new_documents_can_be_regenerated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.select_different_documents_on_completed_production(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_new_documents_can_be_regenerated(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_TIFFPageCount_option_does_not_exist_for_doc_field_classification() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page When begin_new_production_process Then verify_TIFFPageCount_option_does_not_exist_for_doc_field_classification");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.verify_TIFFPageCount_option_does_not_exist_for_doc_field_classification(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_and_add_text_component_to_completed_production_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_new_production_components_can_be_regenerated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated and add_text_component_to_completed_production and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_new_production_components_can_be_regenerated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.add_text_component_to_completed_production(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_new_production_components_can_be_regenerated(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_and_remove_pdf_tiff_component_from_completed_production_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_only_dat_component_is_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated and remove_pdf_tiff_component_from_completed_production and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_only_dat_component_is_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.remove_pdf_tiff_component_from_completed_production(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			dataMap.put("TestCase", "3401");
			context.verify_only_dat_component_is_generated(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_add_text_component_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_doc_order_in_dat_match() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and add_text_component and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_doc_order_in_dat_match");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.add_text_component(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_doc_order_in_dat_match(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_add_dat_and_tiff_enabled_tech_issue_docs_placeholder_and_refresh_back_to_production_home_page_and_save_production_as_custom_template_and_begin_new_production_process_and_click_mark_complete_next_button_on_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_production_is_generated_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and add_dat_and_tiff_enabled_tech_issue_docs_placeholder and refresh_back_to_production_home_page and save_production_as_custom_template and begin_new_production_process and click_mark_complete_next_button_on_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_production_is_generated_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "customTemplate");
			context.begin_new_production_process(true, dataMap);
			context.add_dat_and_tiff_enabled_tech_issue_docs_placeholder(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			context.save_production_as_custom_template(true, dataMap);
			dataMap.put("prod_template", "customTemplate");
			context.begin_new_production_process(true, dataMap);
			context.click_mark_complete_next_button_on_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6439|5175");
			context.verify_production_is_generated_successfully(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_When_wait_until_production_is_generated_Then_verify_production_is_generated_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button When wait_until_production_is_generated Then verify_production_is_generated_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6439|5175");
			context.verify_production_is_generated_successfully(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_dat_component_with_email_metadata_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_dat_file_Then_verify_email_metadata_fields_in_dat_file() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_dat_component_with_email_metadata and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production_dat_file Then verify_email_metadata_fields_in_dat_file");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			context.complete_dat_component_with_email_metadata(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production_dat_file(true, dataMap);
			dataMap.put("TestCase", "7976");
			context.verify_email_metadata_fields_in_dat_file(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_pdf_production_component_with_slip_sheets_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_slip_sheet_fields_are_included_in_pdf() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_pdf_production_component_with_slip_sheets and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_slip_sheet_fields_are_included_in_pdf");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			dataMap.put("slip_sheets_metadata", "DocFileSize|DocFileType|DocFileExtension");
			context.complete_pdf_production_component_with_slip_sheets(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_slip_sheet_fields_are_included_in_pdf(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_native_file_is_not_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_native_file_is_not_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("tiff", "true");
			context.complete_complex_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			dataMap.put("TestCase", "6499|6807|6808");
			context.verify_native_file_is_not_generated(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_pdf_component_with_natively_produced_docs_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_placeholder_for_pdf_with_native_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_pdf_component_with_natively_produced_docs and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_placeholder_for_pdf_with_native_docs");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau4@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_as_pau(true, dataMap);
			dataMap.put("url", "http://mtpvtsslwb01.consilio.com/");
			context.on_production_home_page(true, dataMap);
			dataMap.put("prod_template", "false");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("native_file_type", "Other (i.e. Uncategorized, unknown, etc.)");
			context.complete_pdf_component_with_natively_produced_docs(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			context.verify_placeholder_for_pdf_with_native_docs(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_add_tiff_component_with_parameters_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_When_download_the_production_Then_verify_native_file_is_not_generated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and add_tiff_component_with_parameters and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated When download_the_production Then verify_native_file_is_not_generated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			dataMap.put("enable_for_tech_issue_docs", "true");
			dataMap.put("specify_redactions", "All redactions in annotation layer : Default Annotation Layer");
			dataMap.put("burn_redactions", "true");
			dataMap.put("enable_for_privileged_docs", "true");
			context.add_tiff_component_with_parameters(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			context.download_the_production(true, dataMap);
			dataMap.put("TestCase", "6499|6807|6808");
			context.verify_native_file_is_not_generated(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_and_regenerate_the_production_from_RestarttheProductionFromtheBeginning_When_wait_until_production_is_generated_Then_verify_production_is_regenerated_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated and regenerate_the_production_from_{RestarttheProductionFromtheBeginning} When wait_until_production_is_generated Then verify_production_is_regenerated_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			dataMap.put("C", "");
			dataMap.put("regenerate_from", "Restart the Production From the Beginning");
			context.regenerate_the_production_from_(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11645|11646");
			context.verify_production_is_regenerated_successfully(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_sorting_and_complete_default_document_selection_and_complete_blank_priv_guard_check_and_complete_default_production_location_component_and_completed_summary_and_preview_component_and_clicking_the_production_generate_button_and_wait_until_production_is_generated_and_regenerate_the_production_from_Restarttheproductionfromwhereitleftoff_When_wait_until_production_is_generated_Then_verify_production_is_regenerated_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and complete_default_numbering_sorting and complete_default_document_selection and complete_blank_priv_guard_check and complete_default_production_location_component and completed_summary_and_preview_component and clicking_the_production_generate_button and wait_until_production_is_generated and regenerate_the_production_from_{Restarttheproductionfromwhereitleftoff} When wait_until_production_is_generated Then verify_production_is_regenerated_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.begin_new_production_process(true, dataMap);
			context.complete_default_production_component(true, dataMap);
			context.complete_default_numbering_sorting(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_blank_priv_guard_check(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_and_preview_component(true, dataMap);
			context.clicking_the_production_generate_button(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			dataMap.put("C", "");
			dataMap.put("regenerate_from", "Restart the production from where it left off");
			context.regenerate_the_production_from_(true, dataMap);
			context.wait_until_production_is_generated(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11645|11646");
			context.verify_production_is_regenerated_successfully(true, dataMap);
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
} //eof
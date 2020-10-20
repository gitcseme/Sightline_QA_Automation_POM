package testScriptsRegression;

import java.util.HashMap;

import org.testng.annotations.Test;

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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
	

	@Test(groups = {"Production, Positive"})
	public void test_Given_verify_enabling_placeholders_on_priv_guard_saves_the_placeholders_and_complete_default_production_location_component_and_completed_summary_preview_component_and_the_production_is_started_When_refreshing_for_production_to_be_in_progress_Then_verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_enabling_placeholders_on_priv_guard_saves_the_placeholders and complete_default_production_location_component and completed_summary_preview_component and the_production_is_started When refreshing_for_production_to_be_in_progress Then verify_the_privileged_docs_with_placeholder_count_is_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
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


	@Test(groups = {"Production, Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_sorting_is_added_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_view_results_in_doclist_Then_verify_the_result_set_documents_are_displayed_in_DocList() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_sorting_is_added and complete_default_document_selection and complete_default_priv_guard_documents_are_matched When clicking_view_results_in_doclist Then verify_the_result_set_documents_are_displayed_in_DocList");

		dataMap.put("ExtentTest",test);

		try {
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


	@Test(groups = {"Production", "Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_sorting_is_added_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_view_results_in_docview_Then_verify_the_result_set_documents_are_displayed_in_Docview() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_sorting_is_added and complete_default_document_selection and complete_default_priv_guard_documents_are_matched When clicking_view_results_in_docview Then verify_the_result_set_documents_are_displayed_in_Docview");

		dataMap.put("ExtentTest",test);

		try {
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


	@Test(groups = {"Production", "Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_and_custom_number_sorting_is_added_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_the_productions_mark_complete_button_Then_verify_the_priv_guard_component_displays_the_correct_matched_documents_number() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component and custom_number_sorting_is_added and complete_default_document_selection and complete_default_priv_guard_documents_are_matched When clicking_the_productions_mark_complete_button Then verify_the_priv_guard_component_displays_the_correct_matched_documents_number");

		dataMap.put("ExtentTest",test);

		try {
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


	@Test(groups = {"Production, Positive"})
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
			context.complete_default_production_component(true, dataMap);
			context.complete_tiff_production_component_with_branding(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
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
			context.complete_default_production_component(true, dataMap);
			context.complete_tiff_production_component_with_branding(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
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


	@Test(groups = {"Production, Positive"})
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
			context.complete_default_production_component(true, dataMap);
			context.complete_tiff_production_component_with_branding(true, dataMap);
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
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	

	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_the_native_section_with_tags_and_no_file_types_When_clicking_the_productions_mark_complete_button_Then_verify_native_section_with_tags_is_saving_correctly_without_file_types() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_the_native_section_with_tags_and_no_file_types When clicking_the_productions_mark_complete_button Then verify_native_section_with_tags_is_saving_correctly_without_file_types");

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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_tiff_pdf_with_rotation_When_complete_default_production_component_Then_verify_marking_a_pdf_with_rotation_completed_returns_no_error() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_tiff_pdf_with_rotation When complete_default_production_component Then verify_marking_a_pdf_with_rotation_completed_returns_no_error");

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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_tiff_with_empty_natively_produced_documents_When_clicking_the_productions_mark_complete_button_Then_verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_tiff_with_empty_natively_produced_documents When clicking_the_productions_mark_complete_button Then verify_a_warning_message_is_returned_when_leaving_the_natively_produced_documents_blank");

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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_add_a_second_dat_for_classification_production_When_clicking_the_productions_mark_complete_button_Then_verify_adding_a_second_dat_data_mapping_is_retained() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and add_a_second_dat_for_classification_production When clicking_the_productions_mark_complete_button Then verify_adding_a_second_dat_data_mapping_is_retained");

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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_tiff_and_pdf_with_different_tags_When_clicking_the_productions_mark_complete_button_Then_verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_tiff_and_pdf_with_different_tags When clicking_the_productions_mark_complete_button Then verify_the_correct_error_message_is_returned_for_tiff_pdf_tags_not_matching");

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
			context.complete_tiff_and_pdf_with_different_tags(true, dataMap);
			context.clicking_the_productions_mark_complete_button(true, dataMap);
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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_complex_production_component_When_expanding_the_mp3_advanced_section_Then_verify_the_mp3_advanced_option_is_enabled_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_complex_production_component When expanding_the_mp3_advanced_section Then verify_the_mp3_advanced_option_is_enabled_by_default");

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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_refresh_back_to_production_home_page_and_navigated_back_onto_the_production_components_section_and_edit_the_production_component_with_a_tiff_When_refreshing_the_current_page_Then_verify_editing_an_existing_production_saves_the_new_changes() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_default_production_component and refresh_back_to_production_home_page and navigated_back_onto_the_production_components_section and edit_the_production_component_with_a_tiff When refreshing_the_current_page Then verify_editing_an_existing_production_saves_the_new_changes");

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


	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_on_the_tiff_section_select_place_holder_tag_dialog_When_clicking_non_privledge_tags_Then_verify_production_numbering_and_sorting_options_are_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and on_the_tiff_section_select_place_holder_tag_dialog When clicking_non_privledge_tags Then verify_production_numbering_and_sorting_options_are_displayed");

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
			context.verify_production_numbering_and_sorting_options_are_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_the_default_dat_section_and_complete_the_native_section_with_tags_When_clicking_the_productions_mark_complete_button_Then_verify_native_section_with_tags_is_saving_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process and complete_the_default_dat_section and complete_the_native_section_with_tags When clicking_the_productions_mark_complete_button Then verify_native_section_with_tags_is_saving_correctly");

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


	@Test(groups = {"Production, Positive"})
	public void test_Given_verify_a_complex_production_is_able_to_be_generated_and_waiting_for_production_to_be_complete_When_navigating_to_the_vm_production_location_Then_verify_the_generated_files_display_id_as_the_bates_number() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_a_complex_production_is_able_to_be_generated and waiting_for_production_to_be_complete When navigating_to_the_vm_production_location Then verify_the_generated_files_display_id_as_the_bates_number");

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
			context.navigating_to_the_vm_production_location(true, dataMap);
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
			dataMap.put("uid", "qapau1@consilio.com");
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
	@Test(groups = {"Production, Positive"})
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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
			dataMap.put("uid", "qapau1@consilio.com");
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

	
	@Test(groups = {"Production", "Positive", "wip"})
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
			context.on_production_home_page(true, dataMap);
			context.store_the_default_template_values(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			context.adding_branding_to_pdf(true, dataMap);
			context.the_default_template_for_production_components_is_displayed(true, dataMap);
			context.the_default_template_for_numbering_is_displayed(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
			context.complete_default_production_location_component(true, dataMap);
			context.completed_summary_preview_component(true, dataMap);
			context.starting_the_production_generation(true, dataMap);
			context.waiting_for_production_to_be_complete(true, dataMap);
			context.navigating_to_the_vm_production_location(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "wip"})
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
			context.store_the_default_template_values(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("redaction_style", "White with black font");
			context.updating_redaction_style_adding_redaction_text_(true, dataMap);
			context.the_default_template_for_production_components_is_displayed(true, dataMap);
			context.the_default_template_for_numbering_is_displayed(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
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


	@Test(groups = {"Production", "Positive", "wip"})
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
			context.on_production_home_page(true, dataMap);
			context.store_the_default_template_values(true, dataMap);
			context.refresh_back_to_production_home_page(true, dataMap);
			dataMap.put("prod_template", "DefaultAutomationTemplate");
			context.begin_new_production_process(true, dataMap);
			dataMap.put("redaction_style", "Black with white font");
			context.updating_redaction_style_adding_redaction_text_(true, dataMap);
			context.the_default_template_for_production_components_is_displayed(true, dataMap);
			context.the_default_template_for_numbering_is_displayed(true, dataMap);
			context.complete_default_document_selection(true, dataMap);
			context.complete_default_priv_guard_documents_are_matched(true, dataMap);
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
			dataMap.put("prod_template", "DefaultAutomationTemplate");
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
			dataMap.put("prod_template", "DefaultAutomationTemplate");
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
			dataMap.put("prod_template", "DefaultAutomationTemplate");
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
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Production", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_expanding_the_mp3_advanced_section_Then_verify_redaction_tags_configured_in_mp3_productions_are_retained_in_templates() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_production_home_page and begin_new_production_process When expanding_the_mp3_advanced_section Then verify_redaction_tags_configured_in_mp3_productions_are_retained_in_templates");

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
			context.expanding_the_mp3_advanced_section(true, dataMap);
			context.verify_redaction_tags_configured_in_mp3_productions_are_retained_in_templates(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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


	@Test(groups = {"Production", "Positive"})
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


	@Test(groups = {"Production", "Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_marking_complete_the_next_available_bates_number_and_complete_default_document_selection_When_mark_complete_default_priv_guard_Then_verify_production_location_component() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and marking_complete_the_next_available_bates_number and complete_default_document_selection When mark_complete_default_priv_guard Then verify_production_location_component");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "false");
			dataMap.put("pdf", "true");
			dataMap.put("tiff", "false");
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


	@Test(groups = {"Production", "Positive"})
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
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	

	@Test(groups = {"Production", "Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_When_open_production_in_docview_Then_verify_produced_pdf_in_docview() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page When open_production_in_docview Then verify_produced_pdf_in_docview");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
			dataMap.put("dat", "true");
			dataMap.put("native", "true");
			dataMap.put("pdf", "true");
			context.complete_complex_production_component(true, dataMap);
			context.the_production_generation_is_started_with_the_given_production_component(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.open_production_in_docview(true, dataMap);
			context.verify_produced_pdf_in_docview(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_When_open_production_in_docview_Then_verify_produced_pdf_in_docview_with_pdf_only_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page When open_production_in_docview Then verify_produced_pdf_in_docview_with_pdf_only_production");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_new_production(true, dataMap);
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


	@Test(groups = {"Production", "Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_When_open_doc_in_doc_view_Then_verify_doc_view_images_tab_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page When open_doc_in_doc_view Then verify_doc_view_images_tab_displayed");

		dataMap.put("ExtentTest",test);

		try {
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
			test.log(LogStatus.SKIP, e.getMessage());
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


	@Test(groups = {"Production", "Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_on_production_home_page_and_uncommit_last_production_and_navigate_to_session_search_page_When_open_doc_in_doc_view_Then_verify_doc_view_images_tab_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and on_production_home_page and uncommit_last_production and navigate_to_session_search_page When open_doc_in_doc_view Then verify_doc_view_images_tab_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
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


	@Test(groups = {"Production", "Positive"})
	public void test_Given_login_to_new_production_and_complete_complex_production_component_and_the_production_generation_is_started_with_the_given_production_component_and_navigate_to_session_search_page_and_add_allproductionbatesranges_column_to_doc_and_on_production_home_page_and_uncommit_last_production_and_navigate_to_session_search_page_When_open_production_in_doc_list_Then_verify_allproductionbatesranges_not_displayed_on_uncommitted_production() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_new_production and complete_complex_production_component and the_production_generation_is_started_with_the_given_production_component and navigate_to_session_search_page and add_allproductionbatesranges_column_to_doc and on_production_home_page and uncommit_last_production and navigate_to_session_search_page When open_production_in_doc_list Then verify_allproductionbatesranges_not_displayed_on_uncommitted_production");

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
			context.add_allproductionbatesranges_column_to_doc(true, dataMap);
			context.on_production_home_page(true, dataMap);
			context.uncommit_last_production(true, dataMap);
			context.navigate_to_session_search_page(true, dataMap);
			context.open_production_in_doc_list(true, dataMap);
			context.verify_allproductionbatesranges_not_displayed_on_uncommitted_production(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
}//EOF

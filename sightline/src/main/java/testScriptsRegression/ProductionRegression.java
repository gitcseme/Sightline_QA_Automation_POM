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


	
	@Test(groups = {"Production","Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_archive_option_is_only_zip() throws Throwable
	{
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


	@Test(groups = {"Production","Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_When_complete_component_mp3_with_lst_toggled_off_Then_verify_production_error_message_for_mp3_with_lst_off() throws Throwable
	{
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

	
	@Test(groups = {"Production","Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_mp3_redaction_styles() throws Throwable
	{

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


	@Test(groups = {"Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tiff_section_options() throws Throwable
	{
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

	@Test(groups = {"Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tech_issue_placeholder_is_available() throws Throwable
	{
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

	
	@Test(groups = {"Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_dat_component_removed_advanced_option() throws Throwable
	{
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


	@Test(groups = {"Production", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_When_begin_new_production_process_Then_verify_production_tiff_pdf_advanced_options() throws Throwable
	{
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

	@Test(groups = {"Production, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_and_complete_default_numbering_and_sorting_and_complete_default_document_selection_and_complete_default_priv_guard_documents_are_matched_When_clicking_on_the_docview_button_Then_verify_viewing_docview_for_priv_guard() throws Throwable
	{
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

	@Test(groups = {"Production", "Positive"})

	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_production_home_page_and_begin_new_production_process_and_complete_default_production_component_When_clicking_document_as_the_numbering_default_option_Then_verify_the_numbering_and_sorting_component_displays_the_correct_default_options() throws Throwable
	{
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

	@Test(groups = { "Production, Positive" })
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

	@Test(groups = { "Production, Positive" })
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
}

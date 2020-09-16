package testScriptsRegression;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.IngestionContext;

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class IngestionRegression extends RegressionBase {

	IngestionContext context = new IngestionContext();

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_run_ingest_button_Then_Not_verify_new_ingestion_tile_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_run_ingest_button Then [Not] verify_new_ingestion_tile_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_new_ingestion_tile_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_run_ingest_button_Then_Not_verify_new_ingestion_tile_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_run_ingest_button Then [Not] verify_new_ingestion_tile_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_new_ingestion_tile_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_run_ingest_button_Then_Not_verify_new_ingestion_tile_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_run_ingest_button Then [Not] verify_new_ingestion_tile_is_displayed");

		dataMap.put("ExtentTest", test);
		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_new_ingestion_tile_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_When_click_run_ingest_button_Then_Not_verify_new_ingestion_tile_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created When click_run_ingest_button Then [Not] verify_new_ingestion_tile_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.new_ingestion_created(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_new_ingestion_tile_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_run_ingest_button_Then_Not_verify_ingestion_executed_successfully()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_run_ingest_button Then [Not] verify_ingestion_executed_successfully");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_executed_successfully(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_run_ingest_button_Then_Not_verify_ingestion_executed_successfully()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_run_ingest_button Then [Not] verify_ingestion_executed_successfully");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_executed_successfully(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_run_ingest_button_Then_Not_verify_ingestion_executed_successfully()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_run_ingest_button Then [Not] verify_ingestion_executed_successfully");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_executed_successfully(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_When_click_run_ingest_button_Then_Not_verify_ingestion_executed_successfully()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created When click_run_ingest_button Then [Not] verify_ingestion_executed_successfully");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.new_ingestion_created(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_executed_successfully(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Positive" })
	// Passed
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_and_required_fields_are_entered_When_click_next_button_Then_verify_source_doc_id_is_auto_mapped()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and add_a_new_ingestion_btn_is_clicked and required_fields_are_entered When click_next_button Then verify_source_doc_id_is_auto_mapped");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.required_fields_are_entered(true, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_source_doc_id_is_auto_mapped(true, dataMap);
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Positive" })
	// Passed
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_and_required_fields_are_entered_When_click_next_button_Then_verify_expected_fields_are_mandatory()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and add_a_new_ingestion_btn_is_clicked and required_fields_are_entered When click_next_button Then verify_expected_fields_are_mandatory");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.required_fields_are_entered(true, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_expected_fields_are_mandatory(true, dataMap);
			// Maybe we need to check if fields are empty or not, the above function just
			// checks if the asterick elements are present
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Positive" })
	// Passed
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_verify_configure_mapping_page_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then verify_configure_mapping_page_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_configure_mapping_page_is_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_next_button_Then_Not_verify_source_doc_id_is_auto_mapped()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_next_button Then [Not] verify_source_doc_id_is_auto_mapped");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_source_doc_id_is_auto_mapped(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_next_button_Then_Not_verify_source_doc_id_is_auto_mapped()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_next_button Then [Not] verify_source_doc_id_is_auto_mapped");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_source_doc_id_is_auto_mapped(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_next_button_Then_Not_verify_source_doc_id_is_auto_mapped()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_next_button Then [Not] verify_source_doc_id_is_auto_mapped");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_source_doc_id_is_auto_mapped(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_add_a_new_ingestion_btn_is_clicked_When_click_next_button_Then_Not_verify_source_doc_id_is_auto_mapped()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] add_a_new_ingestion_btn_is_clicked When click_next_button Then [Not] verify_source_doc_id_is_auto_mapped");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_source_doc_id_is_auto_mapped(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_and_Not_required_fields_are_entered_When_click_next_button_Then_Not_verify_source_doc_id_is_auto_mapped()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and add_a_new_ingestion_btn_is_clicked and [Not] required_fields_are_entered When click_next_button Then [Not] verify_source_doc_id_is_auto_mapped");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.required_fields_are_entered(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_source_doc_id_is_auto_mapped(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_next_button_Then_Not_verify_expected_fields_are_mandatory()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_next_button Then [Not] verify_expected_fields_are_mandatory");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_expected_fields_are_mandatory(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_next_button_Then_Not_verify_expected_fields_are_mandatory()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_next_button Then [Not] verify_expected_fields_are_mandatory");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_expected_fields_are_mandatory(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_next_button_Then_Not_verify_expected_fields_are_mandatory()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_next_button Then [Not] verify_expected_fields_are_mandatory");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_expected_fields_are_mandatory(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_add_a_new_ingestion_btn_is_clicked_When_click_next_button_Then_Not_verify_expected_fields_are_mandatory()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] add_a_new_ingestion_btn_is_clicked When click_next_button Then [Not] verify_expected_fields_are_mandatory");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_expected_fields_are_mandatory(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_and_Not_required_fields_are_entered_When_click_next_button_Then_Not_verify_expected_fields_are_mandatory()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and add_a_new_ingestion_btn_is_clicked and [Not] required_fields_are_entered When click_next_button Then [Not] verify_expected_fields_are_mandatory");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.required_fields_are_entered(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_expected_fields_are_mandatory(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_configure_mapping_page_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When add_a_new_ingestion_btn_is_clicked Then [Not] verify_configure_mapping_page_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_configure_mapping_page_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_configure_mapping_page_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When add_a_new_ingestion_btn_is_clicked Then [Not] verify_configure_mapping_page_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_configure_mapping_page_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_configure_mapping_page_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then [Not] verify_configure_mapping_page_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_configure_mapping_page_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_When_click_source_system_dropdown_Then_verify_source_system_displays_expected_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and add_a_new_ingestion_btn_is_clicked When click_source_system_dropdown Then verify_source_system_displays_expected_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.click_source_system_dropdown(true, dataMap);
			context.verify_source_system_displays_expected_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_verify_expected_source_fields_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then verify_expected_source_fields_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_expected_source_fields_are_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_source_system_dropdown_Then_Not_verify_source_system_displays_expected_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_source_system_dropdown Then [Not] verify_source_system_displays_expected_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_source_system_dropdown(true, dataMap);
			context.verify_source_system_displays_expected_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_source_system_dropdown_Then_Not_verify_source_system_displays_expected_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_source_system_dropdown Then [Not] verify_source_system_displays_expected_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_source_system_dropdown(true, dataMap);
			context.verify_source_system_displays_expected_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_source_system_dropdown_Then_Not_verify_source_system_displays_expected_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_source_system_dropdown Then [Not] verify_source_system_displays_expected_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_source_system_dropdown(true, dataMap);
			context.verify_source_system_displays_expected_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_add_a_new_ingestion_btn_is_clicked_When_click_source_system_dropdown_Then_Not_verify_source_system_displays_expected_options()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] add_a_new_ingestion_btn_is_clicked When click_source_system_dropdown Then [Not] verify_source_system_displays_expected_options");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(false, dataMap);
			context.click_source_system_dropdown(true, dataMap);
			context.verify_source_system_displays_expected_options(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_expected_source_fields_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When add_a_new_ingestion_btn_is_clicked Then [Not] verify_expected_source_fields_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_expected_source_fields_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_expected_source_fields_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When add_a_new_ingestion_btn_is_clicked Then [Not] verify_expected_source_fields_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_expected_source_fields_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_expected_source_fields_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then [Not] verify_expected_source_fields_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_expected_source_fields_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_and_required_fields_are_entered_When_click_next_button_Then_verify_mandatory_toast_message_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and add_a_new_ingestion_btn_is_clicked and required_fields_are_entered When click_next_button Then verify_mandatory_toast_message_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.required_fields_are_entered(true, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_mandatory_toast_message_is_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_next_button_Then_Not_verify_mandatory_toast_message_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();
		
		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_next_button Then [Not] verify_mandatory_toast_message_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_mandatory_toast_message_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_next_button_Then_Not_verify_mandatory_toast_message_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_next_button Then [Not] verify_mandatory_toast_message_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_mandatory_toast_message_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_next_button_Then_Not_verify_mandatory_toast_message_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_next_button Then [Not] verify_mandatory_toast_message_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_mandatory_toast_message_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_add_a_new_ingestion_btn_is_clicked_When_click_next_button_Then_Not_verify_mandatory_toast_message_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] add_a_new_ingestion_btn_is_clicked When click_next_button Then [Not] verify_mandatory_toast_message_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_mandatory_toast_message_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_and_Not_required_fields_are_entered_When_click_next_button_Then_Not_verify_mandatory_toast_message_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and add_a_new_ingestion_btn_is_clicked and [Not] required_fields_are_entered When click_next_button Then [Not] verify_mandatory_toast_message_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.required_fields_are_entered(false, dataMap);
			context.click_next_button(true, dataMap);
			context.verify_mandatory_toast_message_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//Pass
	@Test(groups = { "Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_saved_draft_ingestion_When_click_open_wizard_option_Then_verify_saved_draft_retains_files_selected()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_saved_draft_ingestion When click_open_wizard_option Then verify_saved_draft_retains_files_selected");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + "\\" + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.on_saved_draft_ingestion(true, dataMap);
			context.click_open_wizard_option(true, dataMap);
			context.verify_saved_draft_retains_files_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_open_wizard_option_Then_Not_verify_saved_draft_retains_files_selected()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_open_wizard_option Then [Not] verify_saved_draft_retains_files_selected");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_open_wizard_option(true, dataMap);
			context.verify_saved_draft_retains_files_selected(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_open_wizard_option_Then_Not_verify_saved_draft_retains_files_selected()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_open_wizard_option Then [Not] verify_saved_draft_retains_files_selected");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_open_wizard_option(true, dataMap);
			context.verify_saved_draft_retains_files_selected(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_saved_draft_ingestion_When_click_open_wizard_option_Then_Not_verify_saved_draft_retains_files_selected()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_saved_draft_ingestion When click_open_wizard_option Then [Not] verify_saved_draft_retains_files_selected");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_saved_draft_ingestion(false, dataMap);
			context.click_open_wizard_option(true, dataMap);
			context.verify_saved_draft_retains_files_selected(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	//Pass
	@Test(groups = { "Ingestion", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_verify_expected_date_time_format_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then verify_expected_date_time_format_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_expected_date_time_format_is_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_expected_date_time_format_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When add_a_new_ingestion_btn_is_clicked Then [Not] verify_expected_date_time_format_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_expected_date_time_format_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_expected_date_time_format_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When add_a_new_ingestion_btn_is_clicked Then [Not] verify_expected_date_time_format_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_expected_date_time_format_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_expected_date_time_format_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then [Not] verify_expected_date_time_format_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_expected_date_time_format_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//Pass
	@Test(groups = { "Ingestion", "Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingest_execution_details_page_When_click_close_button_Then_verify_close_button_redirects_to_ingestion_home_page()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingest_execution_details_page When click_close_button Then verify_close_button_redirects_to_ingestion_home_page");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.on_ingest_execution_details_page(true, dataMap);
			context.click_close_button(true, dataMap);
			context.verify_close_button_redirects_to_ingestion_home_page(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_close_button_Then_Not_verify_close_button_redirects_to_ingestion_home_page()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_close_button Then [Not] verify_close_button_redirects_to_ingestion_home_page");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_close_button(true, dataMap);
			context.verify_close_button_redirects_to_ingestion_home_page(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_close_button_Then_Not_verify_close_button_redirects_to_ingestion_home_page()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_close_button Then [Not] verify_close_button_redirects_to_ingestion_home_page");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_close_button(true, dataMap);
			context.verify_close_button_redirects_to_ingestion_home_page(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingest_execution_details_page_When_click_close_button_Then_Not_verify_close_button_redirects_to_ingestion_home_page()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingest_execution_details_page When click_close_button Then [Not] verify_close_button_redirects_to_ingestion_home_page");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingest_execution_details_page(false, dataMap);
			context.click_close_button(true, dataMap);
			context.verify_close_button_redirects_to_ingestion_home_page(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//Passed
	@Test(groups = { "Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_saved_draft_ingestion_When_click_delete_option_Then_verify_delete_button_is_available_on_tile()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_saved_draft_ingestion When click_delete_option Then verify_delete_button_is_available_on_tile");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + "\\" + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.on_saved_draft_ingestion(true, dataMap);
			context.verify_delete_button_is_available_on_tile(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_delete_option_Then_Not_verify_delete_button_is_available_on_tile()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_delete_option Then [Not] verify_delete_button_is_available_on_tile");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_delete_option(true, dataMap);
			context.verify_delete_button_is_available_on_tile(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_delete_option_Then_Not_verify_delete_button_is_available_on_tile()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_delete_option Then [Not] verify_delete_button_is_available_on_tile");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_delete_option(true, dataMap);
			context.verify_delete_button_is_available_on_tile(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_saved_draft_ingestion_When_click_delete_option_Then_Not_verify_delete_button_is_available_on_tile()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_saved_draft_ingestion When click_delete_option Then [Not] verify_delete_button_is_available_on_tile");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_saved_draft_ingestion(false, dataMap);
			context.click_delete_option(true, dataMap);
			context.verify_delete_button_is_available_on_tile(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//Pass
	@Test(groups = { "/Users/SQAresources/AutomationIngestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_new_ingestion_tile_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button When click_run_ingest_button Then verify_new_ingestion_tile_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + "\\" + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			dataMap.put("actualCount", "1");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_new_ingestion_tile_is_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	/* Duplicate Test of above Test
	 * 
	@Test(groups = { "/Users/SQAresources/AutomationIngestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_executed_successfully()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button When click_run_ingest_button Then verify_ingestion_executed_successfully");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_executed_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	*/

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_new_ingestion_tile_is_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] click_preview_run_button When click_run_ingest_button Then [Not] verify_new_ingestion_tile_is_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_new_ingestion_tile_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_ingestion_executed_successfully()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] click_preview_run_button When click_run_ingest_button Then [Not] verify_ingestion_executed_successfully");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_executed_successfully(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//Passed
	@Test(groups = { "Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_first_50_records_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button When click_run_ingest_button Then verify_first_50_records_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.verify_first_50_records_are_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//Passed
	@Test(groups = { "Ingestion, Positive","smoke" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_verify_source_selection_types_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then verify_source_selection_types_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_source_selection_types_are_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_verify_all_components_are_displayed_on_the_wizard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then verify_all_components_are_displayed_on_the_wizard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_all_components_are_displayed_on_the_wizard(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_Not_sightline_is_launched_When_click_run_ingest_button_Then_Not_verify_first_50_records_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When click_run_ingest_button Then [Not] verify_first_50_records_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_first_50_records_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_run_ingest_button_Then_Not_verify_first_50_records_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When click_run_ingest_button Then [Not] verify_first_50_records_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_first_50_records_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_run_ingest_button_Then_Not_verify_first_50_records_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_run_ingest_button Then [Not] verify_first_50_records_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_first_50_records_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_When_click_run_ingest_button_Then_Not_verify_first_50_records_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created When click_run_ingest_button Then [Not] verify_first_50_records_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.new_ingestion_created(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_first_50_records_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_first_50_records_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] click_preview_run_button When click_run_ingest_button Then [Not] verify_first_50_records_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData" + File.separator + "Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_first_50_records_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_Not_sightline_is_launched_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_source_selection_types_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When add_a_new_ingestion_btn_is_clicked Then [Not] verify_source_selection_types_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_source_selection_types_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_source_selection_types_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When add_a_new_ingestion_btn_is_clicked Then [Not] verify_source_selection_types_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_source_selection_types_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_source_selection_types_are_displayed()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then [Not] verify_source_selection_types_are_displayed");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_source_selection_types_are_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_Not_sightline_is_launched_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_all_components_are_displayed_on_the_wizard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given [Not] sightline_is_launched When add_a_new_ingestion_btn_is_clicked Then [Not] verify_all_components_are_displayed_on_the_wizard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_all_components_are_displayed_on_the_wizard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_all_components_are_displayed_on_the_wizard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and [Not] login_as_pau When add_a_new_ingestion_btn_is_clicked Then [Not] verify_all_components_are_displayed_on_the_wizard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_all_components_are_displayed_on_the_wizard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion, Negative" })
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_all_components_are_displayed_on_the_wizard()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then [Not] verify_all_components_are_displayed_on_the_wizard");

		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_all_components_are_displayed_on_the_wizard(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
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
